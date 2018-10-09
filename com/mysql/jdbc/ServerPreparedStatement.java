/*      */ package com.mysql.jdbc;
/*      */ 
/*      */ import com.mysql.jdbc.exceptions.MySQLStatementCancelledException;
/*      */ import com.mysql.jdbc.exceptions.MySQLTimeoutException;
/*      */ import com.mysql.jdbc.profiler.ProfilerEvent;
/*      */ import com.mysql.jdbc.profiler.ProfilerEventHandler;
/*      */ import java.io.IOException;
/*      */ import java.io.InputStream;
/*      */ import java.io.Reader;
/*      */ import java.io.UnsupportedEncodingException;
/*      */ import java.lang.reflect.Constructor;
/*      */ import java.lang.reflect.InvocationTargetException;
/*      */ import java.math.BigDecimal;
/*      */ import java.net.URL;
/*      */ import java.sql.Array;
/*      */ import java.sql.BatchUpdateException;
/*      */ import java.sql.Blob;
/*      */ import java.sql.Clob;
/*      */ import java.sql.ParameterMetaData;
/*      */ import java.sql.Ref;
/*      */ import java.sql.ResultSet;
/*      */ import java.sql.SQLException;
/*      */ import java.sql.Time;
/*      */ import java.sql.Timestamp;
/*      */ import java.util.ArrayList;
/*      */ import java.util.Calendar;
/*      */ import java.util.GregorianCalendar;
/*      */ import java.util.List;
/*      */ import java.util.TimeZone;
/*      */ import java.util.Timer;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ public class ServerPreparedStatement
/*      */   extends PreparedStatement
/*      */ {
/*      */   private static final Constructor JDBC_4_SPS_CTOR;
/*      */   protected static final int BLOB_STREAM_READ_BUF_SIZE = 8192;
/*      */   private static final byte MAX_DATE_REP_LENGTH = 5;
/*      */   private static final byte MAX_DATETIME_REP_LENGTH = 12;
/*      */   private static final byte MAX_TIME_REP_LENGTH = 13;
/*      */   
/*      */   static
/*      */   {
/*   67 */     if (Util.isJdbc4()) {
/*      */       try {
/*   69 */         JDBC_4_SPS_CTOR = Class.forName("com.mysql.jdbc.JDBC4ServerPreparedStatement").getConstructor(new Class[] { MySQLConnection.class, String.class, String.class, Integer.TYPE, Integer.TYPE });
/*      */ 
/*      */       }
/*      */       catch (SecurityException e)
/*      */       {
/*   74 */         throw new RuntimeException(e);
/*      */       } catch (NoSuchMethodException e) {
/*   76 */         throw new RuntimeException(e);
/*      */       } catch (ClassNotFoundException e) {
/*   78 */         throw new RuntimeException(e);
/*      */       }
/*      */     } else {
/*   81 */       JDBC_4_SPS_CTOR = null;
/*      */     }
/*      */   }
/*      */   
/*      */   static class BatchedBindValues
/*      */   {
/*      */     ServerPreparedStatement.BindValue[] batchedParameterValues;
/*      */     
/*      */     BatchedBindValues(ServerPreparedStatement.BindValue[] paramVals)
/*      */     {
/*   91 */       int numParams = paramVals.length;
/*      */       
/*   93 */       this.batchedParameterValues = new ServerPreparedStatement.BindValue[numParams];
/*      */       
/*   95 */       for (int i = 0; i < numParams; i++) {
/*   96 */         this.batchedParameterValues[i] = new ServerPreparedStatement.BindValue(paramVals[i]);
/*      */       }
/*      */     }
/*      */   }
/*      */   
/*      */   public static class BindValue
/*      */   {
/*  103 */     long boundBeforeExecutionNum = 0L;
/*      */     
/*      */     public long bindLength;
/*      */     
/*      */     int bufferType;
/*      */     
/*      */     byte byteBinding;
/*      */     
/*      */     double doubleBinding;
/*      */     
/*      */     float floatBinding;
/*      */     
/*      */     int intBinding;
/*      */     
/*      */     public boolean isLongData;
/*      */     
/*      */     public boolean isNull;
/*      */     
/*  121 */     boolean isSet = false;
/*      */     
/*      */     long longBinding;
/*      */     
/*      */     short shortBinding;
/*      */     
/*      */     public Object value;
/*      */     
/*      */     BindValue() {}
/*      */     
/*      */     BindValue(BindValue copyMe)
/*      */     {
/*  133 */       this.value = copyMe.value;
/*  134 */       this.isSet = copyMe.isSet;
/*  135 */       this.isLongData = copyMe.isLongData;
/*  136 */       this.isNull = copyMe.isNull;
/*  137 */       this.bufferType = copyMe.bufferType;
/*  138 */       this.bindLength = copyMe.bindLength;
/*  139 */       this.byteBinding = copyMe.byteBinding;
/*  140 */       this.shortBinding = copyMe.shortBinding;
/*  141 */       this.intBinding = copyMe.intBinding;
/*  142 */       this.longBinding = copyMe.longBinding;
/*  143 */       this.floatBinding = copyMe.floatBinding;
/*  144 */       this.doubleBinding = copyMe.doubleBinding;
/*      */     }
/*      */     
/*      */     void reset() {
/*  148 */       this.isSet = false;
/*  149 */       this.value = null;
/*  150 */       this.isLongData = false;
/*      */       
/*  152 */       this.byteBinding = 0;
/*  153 */       this.shortBinding = 0;
/*  154 */       this.intBinding = 0;
/*  155 */       this.longBinding = 0L;
/*  156 */       this.floatBinding = 0.0F;
/*  157 */       this.doubleBinding = 0.0D;
/*      */     }
/*      */     
/*      */     public String toString() {
/*  161 */       return toString(false);
/*      */     }
/*      */     
/*      */     public String toString(boolean quoteIfNeeded) {
/*  165 */       if (this.isLongData) {
/*  166 */         return "' STREAM DATA '";
/*      */       }
/*      */       
/*  169 */       switch (this.bufferType) {
/*      */       case 1: 
/*  171 */         return String.valueOf(this.byteBinding);
/*      */       case 2: 
/*  173 */         return String.valueOf(this.shortBinding);
/*      */       case 3: 
/*  175 */         return String.valueOf(this.intBinding);
/*      */       case 8: 
/*  177 */         return String.valueOf(this.longBinding);
/*      */       case 4: 
/*  179 */         return String.valueOf(this.floatBinding);
/*      */       case 5: 
/*  181 */         return String.valueOf(this.doubleBinding);
/*      */       case 7: 
/*      */       case 10: 
/*      */       case 11: 
/*      */       case 12: 
/*      */       case 15: 
/*      */       case 253: 
/*      */       case 254: 
/*  189 */         if (quoteIfNeeded) {
/*  190 */           return "'" + String.valueOf(this.value) + "'";
/*      */         }
/*  192 */         return String.valueOf(this.value);
/*      */       }
/*      */       
/*  195 */       if ((this.value instanceof byte[])) {
/*  196 */         return "byte data";
/*      */       }
/*      */       
/*  199 */       if (quoteIfNeeded) {
/*  200 */         return "'" + String.valueOf(this.value) + "'";
/*      */       }
/*  202 */       return String.valueOf(this.value);
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*      */     long getBoundLength()
/*      */     {
/*  209 */       if (this.isNull) {
/*  210 */         return 0L;
/*      */       }
/*      */       
/*  213 */       if (this.isLongData) {
/*  214 */         return this.bindLength;
/*      */       }
/*      */       
/*  217 */       switch (this.bufferType)
/*      */       {
/*      */       case 1: 
/*  220 */         return 1L;
/*      */       case 2: 
/*  222 */         return 2L;
/*      */       case 3: 
/*  224 */         return 4L;
/*      */       case 8: 
/*  226 */         return 8L;
/*      */       case 4: 
/*  228 */         return 4L;
/*      */       case 5: 
/*  230 */         return 8L;
/*      */       case 11: 
/*  232 */         return 9L;
/*      */       case 10: 
/*  234 */         return 7L;
/*      */       case 7: 
/*      */       case 12: 
/*  237 */         return 11L;
/*      */       case 0: 
/*      */       case 15: 
/*      */       case 246: 
/*      */       case 253: 
/*      */       case 254: 
/*  243 */         if ((this.value instanceof byte[])) {
/*  244 */           return ((byte[])this.value).length;
/*      */         }
/*  246 */         return ((String)this.value).length();
/*      */       }
/*      */       
/*  249 */       return 0L;
/*      */     }
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*  269 */   private boolean hasOnDuplicateKeyUpdate = false;
/*      */   
/*      */   private void storeTime(Buffer intoBuf, Time tm) throws SQLException
/*      */   {
/*  273 */     intoBuf.ensureCapacity(9);
/*  274 */     intoBuf.writeByte((byte)8);
/*  275 */     intoBuf.writeByte((byte)0);
/*  276 */     intoBuf.writeLong(0L);
/*      */     
/*  278 */     Calendar sessionCalendar = getCalendarInstanceForSessionOrNew();
/*      */     
/*  280 */     synchronized (sessionCalendar) {
/*  281 */       java.util.Date oldTime = sessionCalendar.getTime();
/*      */       try {
/*  283 */         sessionCalendar.setTime(tm);
/*  284 */         intoBuf.writeByte((byte)sessionCalendar.get(11));
/*  285 */         intoBuf.writeByte((byte)sessionCalendar.get(12));
/*  286 */         intoBuf.writeByte((byte)sessionCalendar.get(13));
/*      */       }
/*      */       finally
/*      */       {
/*  290 */         sessionCalendar.setTime(oldTime);
/*      */       }
/*      */     }
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*  300 */   private boolean detectedLongParameterSwitch = false;
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   private int fieldCount;
/*      */   
/*      */ 
/*      */ 
/*  309 */   private boolean invalid = false;
/*      */   
/*      */ 
/*      */   private SQLException invalidationException;
/*      */   
/*      */ 
/*      */   private boolean isSelectQuery;
/*      */   
/*      */ 
/*      */   private Buffer outByteBuffer;
/*      */   
/*      */ 
/*      */   private BindValue[] parameterBindings;
/*      */   
/*      */ 
/*      */   private Field[] parameterFields;
/*      */   
/*      */ 
/*      */   private Field[] resultFields;
/*      */   
/*  329 */   private boolean sendTypesToServer = false;
/*      */   
/*      */ 
/*      */   private long serverStatementId;
/*      */   
/*      */ 
/*  335 */   private int stringTypeCode = 254;
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   private boolean serverNeedsResetBeforeEachExecution;
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   protected static ServerPreparedStatement getInstance(MySQLConnection conn, String sql, String catalog, int resultSetType, int resultSetConcurrency)
/*      */     throws SQLException
/*      */   {
/*  349 */     if (!Util.isJdbc4()) {
/*  350 */       return new ServerPreparedStatement(conn, sql, catalog, resultSetType, resultSetConcurrency);
/*      */     }
/*      */     
/*      */     try
/*      */     {
/*  355 */       return (ServerPreparedStatement)JDBC_4_SPS_CTOR.newInstance(new Object[] { conn, sql, catalog, Constants.integerValueOf(resultSetType), Constants.integerValueOf(resultSetConcurrency) });
/*      */     }
/*      */     catch (IllegalArgumentException e)
/*      */     {
/*  359 */       throw new SQLException(e.toString(), "S1000");
/*      */     } catch (InstantiationException e) {
/*  361 */       throw new SQLException(e.toString(), "S1000");
/*      */     } catch (IllegalAccessException e) {
/*  363 */       throw new SQLException(e.toString(), "S1000");
/*      */     } catch (InvocationTargetException e) {
/*  365 */       Throwable target = e.getTargetException();
/*      */       
/*  367 */       if ((target instanceof SQLException)) {
/*  368 */         throw ((SQLException)target);
/*      */       }
/*      */       
/*  371 */       throw new SQLException(target.toString(), "S1000");
/*      */     }
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   protected ServerPreparedStatement(MySQLConnection conn, String sql, String catalog, int resultSetType, int resultSetConcurrency)
/*      */     throws SQLException
/*      */   {
/*  391 */     super(conn, catalog);
/*      */     
/*  393 */     checkNullOrEmptyQuery(sql);
/*      */     
/*  395 */     this.hasOnDuplicateKeyUpdate = containsOnDuplicateKeyInString(sql);
/*      */     
/*  397 */     int startOfStatement = findStartOfStatement(sql);
/*      */     
/*  399 */     this.firstCharOfStmt = StringUtils.firstAlphaCharUc(sql, startOfStatement);
/*      */     
/*  401 */     this.isSelectQuery = ('S' == this.firstCharOfStmt);
/*      */     
/*  403 */     if (this.connection.versionMeetsMinimum(5, 0, 0)) {
/*  404 */       this.serverNeedsResetBeforeEachExecution = (!this.connection.versionMeetsMinimum(5, 0, 3));
/*      */     }
/*      */     else {
/*  407 */       this.serverNeedsResetBeforeEachExecution = (!this.connection.versionMeetsMinimum(4, 1, 10));
/*      */     }
/*      */     
/*      */ 
/*  411 */     this.useAutoSlowLog = this.connection.getAutoSlowLog();
/*  412 */     this.useTrueBoolean = this.connection.versionMeetsMinimum(3, 21, 23);
/*  413 */     this.hasLimitClause = (StringUtils.indexOfIgnoreCase(sql, "LIMIT") != -1);
/*      */     
/*  415 */     String statementComment = this.connection.getStatementComment();
/*      */     
/*  417 */     this.originalSql = ("/* " + statementComment + " */ " + sql);
/*      */     
/*      */ 
/*  420 */     if (this.connection.versionMeetsMinimum(4, 1, 2)) {
/*  421 */       this.stringTypeCode = 253;
/*      */     } else {
/*  423 */       this.stringTypeCode = 254;
/*      */     }
/*      */     try
/*      */     {
/*  427 */       serverPrepare(sql);
/*      */     } catch (SQLException sqlEx) {
/*  429 */       realClose(false, true);
/*      */       
/*  431 */       throw sqlEx;
/*      */     } catch (Exception ex) {
/*  433 */       realClose(false, true);
/*      */       
/*  435 */       SQLException sqlEx = SQLError.createSQLException(ex.toString(), "S1000", getExceptionInterceptor());
/*      */       
/*  437 */       sqlEx.initCause(ex);
/*      */       
/*  439 */       throw sqlEx;
/*      */     }
/*      */     
/*  442 */     setResultSetType(resultSetType);
/*  443 */     setResultSetConcurrency(resultSetConcurrency);
/*      */     
/*  445 */     this.parameterTypes = new int[this.parameterCount];
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public synchronized void addBatch()
/*      */     throws SQLException
/*      */   {
/*  457 */     checkClosed();
/*      */     
/*  459 */     if (this.batchedArgs == null) {
/*  460 */       this.batchedArgs = new ArrayList();
/*      */     }
/*      */     
/*  463 */     this.batchedArgs.add(new BatchedBindValues(this.parameterBindings));
/*      */   }
/*      */   
/*      */   protected String asSql(boolean quoteStreamsAndUnknowns) throws SQLException
/*      */   {
/*  468 */     if (this.isClosed) {
/*  469 */       return "statement has been closed, no further internal information available";
/*      */     }
/*      */     
/*  472 */     PreparedStatement pStmtForSub = null;
/*      */     try
/*      */     {
/*  475 */       pStmtForSub = PreparedStatement.getInstance(this.connection, this.originalSql, this.currentCatalog);
/*      */       
/*      */ 
/*  478 */       int numParameters = pStmtForSub.parameterCount;
/*  479 */       int ourNumParameters = this.parameterCount;
/*      */       
/*  481 */       for (int i = 0; (i < numParameters) && (i < ourNumParameters); i++) {
/*  482 */         if (this.parameterBindings[i] != null) {
/*  483 */           if (this.parameterBindings[i].isNull) {
/*  484 */             pStmtForSub.setNull(i + 1, 0);
/*      */           } else {
/*  486 */             BindValue bindValue = this.parameterBindings[i];
/*      */             
/*      */ 
/*      */ 
/*      */ 
/*  491 */             switch (bindValue.bufferType)
/*      */             {
/*      */             case 1: 
/*  494 */               pStmtForSub.setByte(i + 1, bindValue.byteBinding);
/*  495 */               break;
/*      */             case 2: 
/*  497 */               pStmtForSub.setShort(i + 1, bindValue.shortBinding);
/*  498 */               break;
/*      */             case 3: 
/*  500 */               pStmtForSub.setInt(i + 1, bindValue.intBinding);
/*  501 */               break;
/*      */             case 8: 
/*  503 */               pStmtForSub.setLong(i + 1, bindValue.longBinding);
/*  504 */               break;
/*      */             case 4: 
/*  506 */               pStmtForSub.setFloat(i + 1, bindValue.floatBinding);
/*  507 */               break;
/*      */             case 5: 
/*  509 */               pStmtForSub.setDouble(i + 1, bindValue.doubleBinding);
/*      */               
/*  511 */               break;
/*      */             case 6: case 7: default: 
/*  513 */               pStmtForSub.setObject(i + 1, this.parameterBindings[i].value);
/*      */             }
/*      */             
/*      */           }
/*      */         }
/*      */       }
/*      */       
/*      */ 
/*  521 */       return pStmtForSub.asSql(quoteStreamsAndUnknowns);
/*      */     } finally {
/*  523 */       if (pStmtForSub != null) {
/*      */         try {
/*  525 */           pStmtForSub.close();
/*      */         }
/*      */         catch (SQLException sqlEx) {}
/*      */       }
/*      */     }
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   protected void checkClosed()
/*      */     throws SQLException
/*      */   {
/*  539 */     if (this.invalid) {
/*  540 */       throw this.invalidationException;
/*      */     }
/*      */     
/*  543 */     super.checkClosed();
/*      */   }
/*      */   
/*      */ 
/*      */   public void clearParameters()
/*      */     throws SQLException
/*      */   {
/*  550 */     checkClosed();
/*  551 */     clearParametersInternal(true);
/*      */   }
/*      */   
/*      */   private void clearParametersInternal(boolean clearServerParameters) throws SQLException
/*      */   {
/*  556 */     boolean hadLongData = false;
/*      */     
/*  558 */     if (this.parameterBindings != null) {
/*  559 */       for (int i = 0; i < this.parameterCount; i++) {
/*  560 */         if ((this.parameterBindings[i] != null) && (this.parameterBindings[i].isLongData))
/*      */         {
/*  562 */           hadLongData = true;
/*      */         }
/*      */         
/*  565 */         this.parameterBindings[i].reset();
/*      */       }
/*      */     }
/*      */     
/*  569 */     if ((clearServerParameters) && (hadLongData)) {
/*  570 */       serverResetStatement();
/*      */       
/*  572 */       this.detectedLongParameterSwitch = false;
/*      */     }
/*      */   }
/*      */   
/*  576 */   protected boolean isCached = false;
/*      */   
/*      */   private boolean useAutoSlowLog;
/*      */   
/*      */   private Calendar serverTzCalendar;
/*      */   private Calendar defaultTzCalendar;
/*      */   
/*      */   protected void setClosed(boolean flag)
/*      */   {
/*  585 */     this.isClosed = flag;
/*      */   }
/*      */   
/*      */ 
/*      */   public synchronized void close()
/*      */     throws SQLException
/*      */   {
/*  592 */     if ((this.isCached) && (!this.isClosed)) {
/*  593 */       clearParameters();
/*      */       
/*  595 */       this.isClosed = true;
/*      */       
/*  597 */       this.connection.recachePreparedStatement(this);
/*  598 */       return;
/*      */     }
/*      */     
/*  601 */     realClose(true, true);
/*      */   }
/*      */   
/*      */   private void dumpCloseForTestcase() {
/*  605 */     StringBuffer buf = new StringBuffer();
/*  606 */     this.connection.generateConnectionCommentBlock(buf);
/*  607 */     buf.append("DEALLOCATE PREPARE debug_stmt_");
/*  608 */     buf.append(this.statementId);
/*  609 */     buf.append(";\n");
/*      */     
/*  611 */     this.connection.dumpTestcaseQuery(buf.toString());
/*      */   }
/*      */   
/*      */   private void dumpExecuteForTestcase() throws SQLException {
/*  615 */     StringBuffer buf = new StringBuffer();
/*      */     
/*  617 */     for (int i = 0; i < this.parameterCount; i++) {
/*  618 */       this.connection.generateConnectionCommentBlock(buf);
/*      */       
/*  620 */       buf.append("SET @debug_stmt_param");
/*  621 */       buf.append(this.statementId);
/*  622 */       buf.append("_");
/*  623 */       buf.append(i);
/*  624 */       buf.append("=");
/*      */       
/*  626 */       if (this.parameterBindings[i].isNull) {
/*  627 */         buf.append("NULL");
/*      */       } else {
/*  629 */         buf.append(this.parameterBindings[i].toString(true));
/*      */       }
/*      */       
/*  632 */       buf.append(";\n");
/*      */     }
/*      */     
/*  635 */     this.connection.generateConnectionCommentBlock(buf);
/*      */     
/*  637 */     buf.append("EXECUTE debug_stmt_");
/*  638 */     buf.append(this.statementId);
/*      */     
/*  640 */     if (this.parameterCount > 0) {
/*  641 */       buf.append(" USING ");
/*  642 */       for (int i = 0; i < this.parameterCount; i++) {
/*  643 */         if (i > 0) {
/*  644 */           buf.append(", ");
/*      */         }
/*      */         
/*  647 */         buf.append("@debug_stmt_param");
/*  648 */         buf.append(this.statementId);
/*  649 */         buf.append("_");
/*  650 */         buf.append(i);
/*      */       }
/*      */     }
/*      */     
/*      */ 
/*  655 */     buf.append(";\n");
/*      */     
/*  657 */     this.connection.dumpTestcaseQuery(buf.toString());
/*      */   }
/*      */   
/*      */   private void dumpPrepareForTestcase() throws SQLException
/*      */   {
/*  662 */     StringBuffer buf = new StringBuffer(this.originalSql.length() + 64);
/*      */     
/*  664 */     this.connection.generateConnectionCommentBlock(buf);
/*      */     
/*  666 */     buf.append("PREPARE debug_stmt_");
/*  667 */     buf.append(this.statementId);
/*  668 */     buf.append(" FROM \"");
/*  669 */     buf.append(this.originalSql);
/*  670 */     buf.append("\";\n");
/*      */     
/*  672 */     this.connection.dumpTestcaseQuery(buf.toString());
/*      */   }
/*      */   
/*      */   protected int[] executeBatchSerially(int batchTimeout) throws SQLException {
/*  676 */     MySQLConnection locallyScopedConn = this.connection;
/*      */     
/*  678 */     if (locallyScopedConn == null) {
/*  679 */       checkClosed();
/*      */     }
/*      */     
/*  682 */     if (locallyScopedConn.isReadOnly()) {
/*  683 */       throw SQLError.createSQLException(Messages.getString("ServerPreparedStatement.2") + Messages.getString("ServerPreparedStatement.3"), "S1009", getExceptionInterceptor());
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*  689 */     checkClosed();
/*      */     BindValue[] oldBindValues;
/*  691 */     synchronized (locallyScopedConn.getMutex()) {
/*  692 */       clearWarnings();
/*      */       
/*      */ 
/*      */ 
/*  696 */       oldBindValues = this.parameterBindings;
/*      */     }
/*      */     try {
/*  699 */       int[] updateCounts = null;
/*      */       
/*  701 */       if (this.batchedArgs != null) {
/*  702 */         nbrCommands = this.batchedArgs.size();
/*  703 */         updateCounts = new int[nbrCommands];
/*      */         
/*  705 */         if (this.retrieveGeneratedKeys) {
/*  706 */           this.batchedGeneratedKeys = new ArrayList(nbrCommands);
/*      */         }
/*      */         
/*  709 */         for (int i = 0; i < nbrCommands; i++) {
/*  710 */           updateCounts[i] = -3;
/*      */         }
/*      */         
/*  713 */         SQLException sqlEx = null;
/*      */         
/*  715 */         int commandIndex = 0;
/*      */         
/*  717 */         BindValue[] previousBindValuesForBatch = null;
/*      */         
/*  719 */         StatementImpl.CancelTask timeoutTask = null;
/*      */         try
/*      */         {
/*  722 */           if ((locallyScopedConn.getEnableQueryTimeouts()) && (batchTimeout != 0) && (locallyScopedConn.versionMeetsMinimum(5, 0, 0)))
/*      */           {
/*      */ 
/*  725 */             timeoutTask = new StatementImpl.CancelTask(this, this);
/*  726 */             locallyScopedConn.getCancelTimer().schedule(timeoutTask, batchTimeout);
/*      */           }
/*      */           
/*      */ 
/*  730 */           for (commandIndex = 0; commandIndex < nbrCommands; commandIndex++) {
/*  731 */             Object arg = this.batchedArgs.get(commandIndex);
/*      */             
/*  733 */             if ((arg instanceof String)) {
/*  734 */               updateCounts[commandIndex] = executeUpdate((String)arg);
/*      */             } else {
/*  736 */               this.parameterBindings = ((BatchedBindValues)arg).batchedParameterValues;
/*      */               
/*      */ 
/*      */ 
/*      */ 
/*      */               try
/*      */               {
/*  743 */                 if (previousBindValuesForBatch != null) {
/*  744 */                   for (int j = 0; j < this.parameterBindings.length; j++) {
/*  745 */                     if (this.parameterBindings[j].bufferType != previousBindValuesForBatch[j].bufferType) {
/*  746 */                       this.sendTypesToServer = true;
/*      */                       
/*  748 */                       break;
/*      */                     }
/*      */                   }
/*      */                 }
/*      */                 try
/*      */                 {
/*  754 */                   updateCounts[commandIndex] = executeUpdate(false, true);
/*      */                 } finally {
/*  756 */                   previousBindValuesForBatch = this.parameterBindings;
/*      */                 }
/*      */                 
/*  759 */                 if (this.retrieveGeneratedKeys) {
/*  760 */                   ResultSet rs = null;
/*      */                   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */                   try
/*      */                   {
/*  772 */                     rs = getGeneratedKeysInternal();
/*      */                     
/*  774 */                     while (rs.next()) {
/*  775 */                       this.batchedGeneratedKeys.add(new ByteArrayRow(new byte[][] { rs.getBytes(1) }, getExceptionInterceptor()));
/*      */                     }
/*      */                   }
/*      */                   finally
/*      */                   {
/*  780 */                     if (rs != null) {
/*  781 */                       rs.close();
/*      */                     }
/*      */                   }
/*      */                 }
/*      */               } catch (SQLException ex) {
/*  786 */                 updateCounts[commandIndex] = -3;
/*      */                 
/*  788 */                 if ((this.continueBatchOnError) && (!(ex instanceof MySQLTimeoutException)) && (!(ex instanceof MySQLStatementCancelledException)) && (!hasDeadlockOrTimeoutRolledBackTx(ex)))
/*      */                 {
/*      */ 
/*      */ 
/*  792 */                   sqlEx = ex;
/*      */                 } else {
/*  794 */                   int[] newUpdateCounts = new int[commandIndex];
/*  795 */                   System.arraycopy(updateCounts, 0, newUpdateCounts, 0, commandIndex);
/*      */                   
/*      */ 
/*  798 */                   throw new BatchUpdateException(ex.getMessage(), ex.getSQLState(), ex.getErrorCode(), newUpdateCounts);
/*      */                 }
/*      */               }
/*      */             }
/*      */           }
/*      */         }
/*      */         finally
/*      */         {
/*  806 */           if (timeoutTask != null) {
/*  807 */             timeoutTask.cancel();
/*      */             
/*  809 */             locallyScopedConn.getCancelTimer().purge();
/*      */           }
/*      */           
/*  812 */           resetCancelledState();
/*      */         }
/*      */         
/*  815 */         if (sqlEx != null) {
/*  816 */           throw new BatchUpdateException(sqlEx.getMessage(), sqlEx.getSQLState(), sqlEx.getErrorCode(), updateCounts);
/*      */         }
/*      */       }
/*      */       
/*      */ 
/*      */ 
/*  822 */       int nbrCommands = updateCounts != null ? updateCounts : new int[0];jsr 16;return nbrCommands;
/*      */     } finally {
/*  824 */       jsr 6; } localObject8 = returnAddress;this.parameterBindings = oldBindValues;
/*  825 */     this.sendTypesToServer = true;
/*      */     
/*  827 */     clearBatch();ret;
/*      */     
/*  829 */     localObject9 = finally;throw ((Throwable)localObject9);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   protected ResultSetInternalMethods executeInternal(int maxRowsToRetrieve, Buffer sendPacket, boolean createStreamingResultSet, boolean queryIsSelectOnly, Field[] metadataFromCache, boolean isBatch)
/*      */     throws SQLException
/*      */   {
/*  841 */     this.numberOfExecutions += 1;
/*      */     
/*      */     try
/*      */     {
/*  845 */       return serverExecute(maxRowsToRetrieve, createStreamingResultSet, metadataFromCache);
/*      */     }
/*      */     catch (SQLException sqlEx)
/*      */     {
/*  849 */       if (this.connection.getEnablePacketDebug()) {
/*  850 */         this.connection.getIO().dumpPacketRingBuffer();
/*      */       }
/*      */       
/*  853 */       if (this.connection.getDumpQueriesOnException()) {
/*  854 */         String extractedSql = toString();
/*  855 */         StringBuffer messageBuf = new StringBuffer(extractedSql.length() + 32);
/*      */         
/*  857 */         messageBuf.append("\n\nQuery being executed when exception was thrown:\n");
/*      */         
/*  859 */         messageBuf.append(extractedSql);
/*  860 */         messageBuf.append("\n\n");
/*      */         
/*  862 */         sqlEx = ConnectionImpl.appendMessageToException(sqlEx, messageBuf.toString(), getExceptionInterceptor());
/*      */       }
/*      */       
/*      */ 
/*  866 */       throw sqlEx;
/*      */     } catch (Exception ex) {
/*  868 */       if (this.connection.getEnablePacketDebug()) {
/*  869 */         this.connection.getIO().dumpPacketRingBuffer();
/*      */       }
/*      */       
/*  872 */       SQLException sqlEx = SQLError.createSQLException(ex.toString(), "S1000", getExceptionInterceptor());
/*      */       
/*      */ 
/*  875 */       if (this.connection.getDumpQueriesOnException()) {
/*  876 */         String extractedSql = toString();
/*  877 */         StringBuffer messageBuf = new StringBuffer(extractedSql.length() + 32);
/*      */         
/*  879 */         messageBuf.append("\n\nQuery being executed when exception was thrown:\n");
/*      */         
/*  881 */         messageBuf.append(extractedSql);
/*  882 */         messageBuf.append("\n\n");
/*      */         
/*  884 */         sqlEx = ConnectionImpl.appendMessageToException(sqlEx, messageBuf.toString(), getExceptionInterceptor());
/*      */       }
/*      */       
/*      */ 
/*  888 */       sqlEx.initCause(ex);
/*      */       
/*  890 */       throw sqlEx;
/*      */     }
/*      */   }
/*      */   
/*      */ 
/*      */   protected Buffer fillSendPacket()
/*      */     throws SQLException
/*      */   {
/*  898 */     return null;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   protected Buffer fillSendPacket(byte[][] batchedParameterStrings, InputStream[] batchedParameterStreams, boolean[] batchedIsStream, int[] batchedStreamLengths)
/*      */     throws SQLException
/*      */   {
/*  908 */     return null;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   protected BindValue getBinding(int parameterIndex, boolean forLongData)
/*      */     throws SQLException
/*      */   {
/*  922 */     checkClosed();
/*      */     
/*  924 */     if (this.parameterBindings.length == 0) {
/*  925 */       throw SQLError.createSQLException(Messages.getString("ServerPreparedStatement.8"), "S1009", getExceptionInterceptor());
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*  930 */     parameterIndex--;
/*      */     
/*  932 */     if ((parameterIndex < 0) || (parameterIndex >= this.parameterBindings.length))
/*      */     {
/*  934 */       throw SQLError.createSQLException(Messages.getString("ServerPreparedStatement.9") + (parameterIndex + 1) + Messages.getString("ServerPreparedStatement.10") + this.parameterBindings.length, "S1009", getExceptionInterceptor());
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*  942 */     if (this.parameterBindings[parameterIndex] == null) {
/*  943 */       this.parameterBindings[parameterIndex] = new BindValue();
/*      */     }
/*  945 */     else if ((this.parameterBindings[parameterIndex].isLongData) && (!forLongData))
/*      */     {
/*  947 */       this.detectedLongParameterSwitch = true;
/*      */     }
/*      */     
/*      */ 
/*  951 */     this.parameterBindings[parameterIndex].isSet = true;
/*  952 */     this.parameterBindings[parameterIndex].boundBeforeExecutionNum = this.numberOfExecutions;
/*      */     
/*  954 */     return this.parameterBindings[parameterIndex];
/*      */   }
/*      */   
/*      */ 
/*      */   byte[] getBytes(int parameterIndex)
/*      */     throws SQLException
/*      */   {
/*  961 */     BindValue bindValue = getBinding(parameterIndex, false);
/*      */     
/*  963 */     if (bindValue.isNull)
/*  964 */       return null;
/*  965 */     if (bindValue.isLongData) {
/*  966 */       throw SQLError.notImplemented();
/*      */     }
/*  968 */     if (this.outByteBuffer == null) {
/*  969 */       this.outByteBuffer = new Buffer(this.connection.getNetBufferLength());
/*      */     }
/*      */     
/*      */ 
/*  973 */     this.outByteBuffer.clear();
/*      */     
/*  975 */     int originalPosition = this.outByteBuffer.getPosition();
/*      */     
/*  977 */     storeBinding(this.outByteBuffer, bindValue, this.connection.getIO());
/*      */     
/*  979 */     int newPosition = this.outByteBuffer.getPosition();
/*      */     
/*  981 */     int length = newPosition - originalPosition;
/*      */     
/*  983 */     byte[] valueAsBytes = new byte[length];
/*      */     
/*  985 */     System.arraycopy(this.outByteBuffer.getByteBuffer(), originalPosition, valueAsBytes, 0, length);
/*      */     
/*      */ 
/*  988 */     return valueAsBytes;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */   public java.sql.ResultSetMetaData getMetaData()
/*      */     throws SQLException
/*      */   {
/*  996 */     checkClosed();
/*      */     
/*  998 */     if (this.resultFields == null) {
/*  999 */       return null;
/*      */     }
/*      */     
/* 1002 */     return new ResultSetMetaData(this.resultFields, this.connection.getUseOldAliasMetadataBehavior(), getExceptionInterceptor());
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */   public ParameterMetaData getParameterMetaData()
/*      */     throws SQLException
/*      */   {
/* 1010 */     checkClosed();
/*      */     
/* 1012 */     if (this.parameterMetaData == null) {
/* 1013 */       this.parameterMetaData = new MysqlParameterMetadata(this.parameterFields, this.parameterCount, getExceptionInterceptor());
/*      */     }
/*      */     
/*      */ 
/* 1017 */     return this.parameterMetaData;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */   boolean isNull(int paramIndex)
/*      */   {
/* 1024 */     throw new IllegalArgumentException(Messages.getString("ServerPreparedStatement.7"));
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   protected void realClose(boolean calledExplicitly, boolean closeOpenResults)
/*      */     throws SQLException
/*      */   {
/* 1039 */     if (this.isClosed) {
/* 1040 */       return;
/*      */     }
/*      */     
/* 1043 */     if (this.connection != null) {
/* 1044 */       if (this.connection.getAutoGenerateTestcaseScript()) {
/* 1045 */         dumpCloseForTestcase();
/*      */       }
/*      */       
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 1059 */       SQLException exceptionDuringClose = null;
/*      */       
/* 1061 */       if ((calledExplicitly) && (!this.connection.isClosed())) {
/* 1062 */         synchronized (this.connection.getMutex())
/*      */         {
/*      */           try {
/* 1065 */             MysqlIO mysql = this.connection.getIO();
/*      */             
/* 1067 */             Buffer packet = mysql.getSharedSendPacket();
/*      */             
/* 1069 */             packet.writeByte((byte)25);
/* 1070 */             packet.writeLong(this.serverStatementId);
/*      */             
/* 1072 */             mysql.sendCommand(25, null, packet, true, null, 0);
/*      */           }
/*      */           catch (SQLException sqlEx) {
/* 1075 */             exceptionDuringClose = sqlEx;
/*      */           }
/*      */         }
/*      */       }
/*      */       
/* 1080 */       super.realClose(calledExplicitly, closeOpenResults);
/*      */       
/* 1082 */       clearParametersInternal(false);
/* 1083 */       this.parameterBindings = null;
/*      */       
/* 1085 */       this.parameterFields = null;
/* 1086 */       this.resultFields = null;
/*      */       
/* 1088 */       if (exceptionDuringClose != null) {
/* 1089 */         throw exceptionDuringClose;
/*      */       }
/*      */     }
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   protected void rePrepare()
/*      */     throws SQLException
/*      */   {
/* 1102 */     this.invalidationException = null;
/*      */     try
/*      */     {
/* 1105 */       serverPrepare(this.originalSql);
/*      */     }
/*      */     catch (SQLException sqlEx) {
/* 1108 */       this.invalidationException = sqlEx;
/*      */     } catch (Exception ex) {
/* 1110 */       this.invalidationException = SQLError.createSQLException(ex.toString(), "S1000", getExceptionInterceptor());
/*      */       
/* 1112 */       this.invalidationException.initCause(ex);
/*      */     }
/*      */     
/* 1115 */     if (this.invalidationException != null) {
/* 1116 */       this.invalid = true;
/*      */       
/* 1118 */       this.parameterBindings = null;
/*      */       
/* 1120 */       this.parameterFields = null;
/* 1121 */       this.resultFields = null;
/*      */       
/* 1123 */       if (this.results != null) {
/*      */         try {
/* 1125 */           this.results.close();
/*      */         }
/*      */         catch (Exception ex) {}
/*      */       }
/*      */       
/*      */ 
/* 1131 */       if (this.connection != null) {
/* 1132 */         if (this.maxRowsChanged) {
/* 1133 */           this.connection.unsetMaxRows(this);
/*      */         }
/*      */         
/* 1136 */         if (!this.connection.getDontTrackOpenResources()) {
/* 1137 */           this.connection.unregisterStatement(this);
/*      */         }
/*      */       }
/*      */     }
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   private ResultSetInternalMethods serverExecute(int maxRowsToRetrieve, boolean createStreamingResultSet, Field[] metadataFromCache)
/*      */     throws SQLException
/*      */   {
/* 1179 */     synchronized (this.connection.getMutex()) {
/* 1180 */       MysqlIO mysql = this.connection.getIO();
/*      */       
/* 1182 */       if (mysql.shouldIntercept()) {
/* 1183 */         ResultSetInternalMethods interceptedResults = mysql.invokeStatementInterceptorsPre(this.originalSql, this, true);
/*      */         
/*      */ 
/* 1186 */         if (interceptedResults != null) {
/* 1187 */           return interceptedResults;
/*      */         }
/*      */       }
/*      */       
/* 1191 */       if (this.detectedLongParameterSwitch)
/*      */       {
/* 1193 */         boolean firstFound = false;
/* 1194 */         long boundTimeToCheck = 0L;
/*      */         
/* 1196 */         for (int i = 0; i < this.parameterCount - 1; i++) {
/* 1197 */           if (this.parameterBindings[i].isLongData) {
/* 1198 */             if ((firstFound) && (boundTimeToCheck != this.parameterBindings[i].boundBeforeExecutionNum))
/*      */             {
/* 1200 */               throw SQLError.createSQLException(Messages.getString("ServerPreparedStatement.11") + Messages.getString("ServerPreparedStatement.12"), "S1C00", getExceptionInterceptor());
/*      */             }
/*      */             
/*      */ 
/*      */ 
/* 1205 */             firstFound = true;
/* 1206 */             boundTimeToCheck = this.parameterBindings[i].boundBeforeExecutionNum;
/*      */           }
/*      */         }
/*      */         
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 1214 */         serverResetStatement();
/*      */       }
/*      */       
/*      */ 
/*      */ 
/* 1219 */       for (int i = 0; i < this.parameterCount; i++) {
/* 1220 */         if (!this.parameterBindings[i].isSet) {
/* 1221 */           throw SQLError.createSQLException(Messages.getString("ServerPreparedStatement.13") + (i + 1) + Messages.getString("ServerPreparedStatement.14"), "S1009", getExceptionInterceptor());
/*      */         }
/*      */       }
/*      */       
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 1231 */       for (int i = 0; i < this.parameterCount; i++) {
/* 1232 */         if (this.parameterBindings[i].isLongData) {
/* 1233 */           serverLongData(i, this.parameterBindings[i]);
/*      */         }
/*      */       }
/*      */       
/* 1237 */       if (this.connection.getAutoGenerateTestcaseScript()) {
/* 1238 */         dumpExecuteForTestcase();
/*      */       }
/*      */       
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 1245 */       Buffer packet = mysql.getSharedSendPacket();
/*      */       
/* 1247 */       packet.clear();
/* 1248 */       packet.writeByte((byte)23);
/* 1249 */       packet.writeLong(this.serverStatementId);
/*      */       
/* 1251 */       boolean usingCursor = false;
/*      */       
/* 1253 */       if (this.connection.versionMeetsMinimum(4, 1, 2))
/*      */       {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 1260 */         if ((this.resultFields != null) && (this.connection.isCursorFetchEnabled()) && (getResultSetType() == 1003) && (getResultSetConcurrency() == 1007) && (getFetchSize() > 0))
/*      */         {
/*      */ 
/*      */ 
/*      */ 
/* 1265 */           packet.writeByte((byte)1);
/* 1266 */           usingCursor = true;
/*      */         } else {
/* 1268 */           packet.writeByte((byte)0);
/*      */         }
/*      */         
/* 1271 */         packet.writeLong(1L);
/*      */       }
/*      */       
/*      */ 
/*      */ 
/* 1276 */       int nullCount = (this.parameterCount + 7) / 8;
/*      */       
/*      */ 
/*      */ 
/*      */ 
/* 1281 */       int nullBitsPosition = packet.getPosition();
/*      */       
/* 1283 */       for (int i = 0; i < nullCount; i++) {
/* 1284 */         packet.writeByte((byte)0);
/*      */       }
/*      */       
/* 1287 */       byte[] nullBitsBuffer = new byte[nullCount];
/*      */       
/*      */ 
/* 1290 */       packet.writeByte((byte)(this.sendTypesToServer ? 1 : 0));
/*      */       
/* 1292 */       if (this.sendTypesToServer)
/*      */       {
/*      */ 
/*      */ 
/*      */ 
/* 1297 */         for (int i = 0; i < this.parameterCount; i++) {
/* 1298 */           packet.writeInt(this.parameterBindings[i].bufferType);
/*      */         }
/*      */       }
/*      */       
/*      */ 
/*      */ 
/*      */ 
/* 1305 */       for (int i = 0; i < this.parameterCount; i++) {
/* 1306 */         if (!this.parameterBindings[i].isLongData) {
/* 1307 */           if (!this.parameterBindings[i].isNull) {
/* 1308 */             storeBinding(packet, this.parameterBindings[i], mysql);
/*      */           } else {
/* 1310 */             int tmp601_600 = (i / 8); byte[] tmp601_594 = nullBitsBuffer;tmp601_594[tmp601_600] = ((byte)(tmp601_594[tmp601_600] | 1 << (i & 0x7)));
/*      */           }
/*      */         }
/*      */       }
/*      */       
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 1319 */       int endPosition = packet.getPosition();
/* 1320 */       packet.setPosition(nullBitsPosition);
/* 1321 */       packet.writeBytesNoNull(nullBitsBuffer);
/* 1322 */       packet.setPosition(endPosition);
/*      */       
/* 1324 */       long begin = 0L;
/*      */       
/* 1326 */       boolean logSlowQueries = this.connection.getLogSlowQueries();
/* 1327 */       boolean gatherPerformanceMetrics = this.connection.getGatherPerformanceMetrics();
/*      */       
/*      */ 
/* 1330 */       if ((this.profileSQL) || (logSlowQueries) || (gatherPerformanceMetrics)) {
/* 1331 */         begin = mysql.getCurrentTimeNanosOrMillis();
/*      */       }
/*      */       
/* 1334 */       resetCancelledState();
/*      */       
/* 1336 */       StatementImpl.CancelTask timeoutTask = null;
/*      */       try
/*      */       {
/* 1339 */         if ((this.connection.getEnableQueryTimeouts()) && (this.timeoutInMillis != 0) && (this.connection.versionMeetsMinimum(5, 0, 0)))
/*      */         {
/*      */ 
/* 1342 */           timeoutTask = new StatementImpl.CancelTask(this, this);
/* 1343 */           this.connection.getCancelTimer().schedule(timeoutTask, this.timeoutInMillis);
/*      */         }
/*      */         
/*      */ 
/* 1347 */         Buffer resultPacket = mysql.sendCommand(23, null, packet, false, null, 0);
/*      */         
/*      */ 
/* 1350 */         long queryEndTime = 0L;
/*      */         
/* 1352 */         if ((logSlowQueries) || (gatherPerformanceMetrics) || (this.profileSQL)) {
/* 1353 */           queryEndTime = mysql.getCurrentTimeNanosOrMillis();
/*      */         }
/*      */         
/* 1356 */         if (timeoutTask != null) {
/* 1357 */           timeoutTask.cancel();
/*      */           
/* 1359 */           this.connection.getCancelTimer().purge();
/*      */           
/* 1361 */           if (timeoutTask.caughtWhileCancelling != null) {
/* 1362 */             throw timeoutTask.caughtWhileCancelling;
/*      */           }
/*      */           
/* 1365 */           timeoutTask = null;
/*      */         }
/*      */         
/* 1368 */         synchronized (this.cancelTimeoutMutex) {
/* 1369 */           if (this.wasCancelled) {
/* 1370 */             SQLException cause = null;
/*      */             
/* 1372 */             if (this.wasCancelledByTimeout) {
/* 1373 */               cause = new MySQLTimeoutException();
/*      */             } else {
/* 1375 */               cause = new MySQLStatementCancelledException();
/*      */             }
/*      */             
/* 1378 */             resetCancelledState();
/*      */             
/* 1380 */             throw cause;
/*      */           }
/*      */         }
/*      */         
/* 1384 */         boolean queryWasSlow = false;
/*      */         
/* 1386 */         if ((logSlowQueries) || (gatherPerformanceMetrics)) {
/* 1387 */           long elapsedTime = queryEndTime - begin;
/*      */           
/* 1389 */           if (logSlowQueries) {
/* 1390 */             if (this.useAutoSlowLog) {
/* 1391 */               queryWasSlow = elapsedTime > this.connection.getSlowQueryThresholdMillis();
/*      */             } else {
/* 1393 */               queryWasSlow = this.connection.isAbonormallyLongQuery(elapsedTime);
/*      */               
/* 1395 */               this.connection.reportQueryTime(elapsedTime);
/*      */             }
/*      */           }
/*      */           
/* 1399 */           if (queryWasSlow)
/*      */           {
/* 1401 */             StringBuffer mesgBuf = new StringBuffer(48 + this.originalSql.length());
/*      */             
/* 1403 */             mesgBuf.append(Messages.getString("ServerPreparedStatement.15"));
/*      */             
/* 1405 */             mesgBuf.append(mysql.getSlowQueryThreshold());
/* 1406 */             mesgBuf.append(Messages.getString("ServerPreparedStatement.15a"));
/*      */             
/* 1408 */             mesgBuf.append(elapsedTime);
/* 1409 */             mesgBuf.append(Messages.getString("ServerPreparedStatement.16"));
/*      */             
/*      */ 
/* 1412 */             mesgBuf.append("as prepared: ");
/* 1413 */             mesgBuf.append(this.originalSql);
/* 1414 */             mesgBuf.append("\n\n with parameters bound:\n\n");
/* 1415 */             mesgBuf.append(asSql(true));
/*      */             
/* 1417 */             this.eventSink.consumeEvent(new ProfilerEvent((byte)6, "", this.currentCatalog, this.connection.getId(), getId(), 0, System.currentTimeMillis(), elapsedTime, mysql.getQueryTimingUnits(), null, new Throwable(), mesgBuf.toString()));
/*      */           }
/*      */           
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 1427 */           if (gatherPerformanceMetrics) {
/* 1428 */             this.connection.registerQueryExecutionTime(elapsedTime);
/*      */           }
/*      */         }
/*      */         
/* 1432 */         this.connection.incrementNumberOfPreparedExecutes();
/*      */         
/* 1434 */         if (this.profileSQL) {
/* 1435 */           this.eventSink = ProfilerEventHandlerFactory.getInstance(this.connection);
/*      */           
/*      */ 
/* 1438 */           this.eventSink.consumeEvent(new ProfilerEvent((byte)4, "", this.currentCatalog, this.connectionId, this.statementId, -1, System.currentTimeMillis(), (int)(mysql.getCurrentTimeNanosOrMillis() - begin), mysql.getQueryTimingUnits(), null, new Throwable(), truncateQueryToLog(asSql(true))));
/*      */         }
/*      */         
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 1448 */         ResultSetInternalMethods rs = mysql.readAllResults(this, maxRowsToRetrieve, this.resultSetType, this.resultSetConcurrency, createStreamingResultSet, this.currentCatalog, resultPacket, true, this.fieldCount, metadataFromCache);
/*      */         
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 1454 */         if (mysql.shouldIntercept()) {
/* 1455 */           ResultSetInternalMethods interceptedResults = mysql.invokeStatementInterceptorsPost(this.originalSql, this, rs, true, null);
/*      */           
/*      */ 
/* 1458 */           if (interceptedResults != null) {
/* 1459 */             rs = interceptedResults;
/*      */           }
/*      */         }
/*      */         
/* 1463 */         if (this.profileSQL) {
/* 1464 */           long fetchEndTime = mysql.getCurrentTimeNanosOrMillis();
/*      */           
/* 1466 */           this.eventSink.consumeEvent(new ProfilerEvent((byte)5, "", this.currentCatalog, this.connection.getId(), getId(), 0, System.currentTimeMillis(), fetchEndTime - queryEndTime, mysql.getQueryTimingUnits(), null, new Throwable(), null));
/*      */         }
/*      */         
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 1475 */         if ((queryWasSlow) && (this.connection.getExplainSlowQueries())) {
/* 1476 */           queryAsString = asSql(true);
/*      */           
/* 1478 */           mysql.explainSlowQuery(((String)queryAsString).getBytes(), (String)queryAsString);
/*      */         }
/*      */         
/*      */ 
/* 1482 */         if ((!createStreamingResultSet) && (this.serverNeedsResetBeforeEachExecution))
/*      */         {
/* 1484 */           serverResetStatement();
/*      */         }
/*      */         
/*      */ 
/* 1488 */         this.sendTypesToServer = false;
/* 1489 */         this.results = rs;
/*      */         
/* 1491 */         if (mysql.hadWarnings()) {
/* 1492 */           mysql.scanForAndThrowDataTruncation();
/*      */         }
/*      */         
/* 1495 */         Object queryAsString = rs;jsr 45;return (ResultSetInternalMethods)queryAsString;
/*      */       } catch (SQLException sqlEx) {
/* 1497 */         if (mysql.shouldIntercept()) {
/* 1498 */           mysql.invokeStatementInterceptorsPost(this.originalSql, this, null, true, sqlEx);
/*      */         }
/*      */         
/* 1501 */         throw sqlEx;
/*      */       } finally {
/* 1503 */         jsr 6; } localObject3 = returnAddress; if (timeoutTask != null) {
/* 1504 */         timeoutTask.cancel();
/* 1505 */         this.connection.getCancelTimer().purge(); } ret;
/*      */     }
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   private void serverLongData(int parameterIndex, BindValue longData)
/*      */     throws SQLException
/*      */   {
/* 1540 */     synchronized (this.connection.getMutex()) {
/* 1541 */       MysqlIO mysql = this.connection.getIO();
/*      */       
/* 1543 */       Buffer packet = mysql.getSharedSendPacket();
/*      */       
/* 1545 */       Object value = longData.value;
/*      */       
/* 1547 */       if ((value instanceof byte[])) {
/* 1548 */         packet.clear();
/* 1549 */         packet.writeByte((byte)24);
/* 1550 */         packet.writeLong(this.serverStatementId);
/* 1551 */         packet.writeInt(parameterIndex);
/*      */         
/* 1553 */         packet.writeBytesNoNull((byte[])longData.value);
/*      */         
/* 1555 */         mysql.sendCommand(24, null, packet, true, null, 0);
/*      */       }
/* 1557 */       else if ((value instanceof InputStream)) {
/* 1558 */         storeStream(mysql, parameterIndex, packet, (InputStream)value);
/* 1559 */       } else if ((value instanceof Blob)) {
/* 1560 */         storeStream(mysql, parameterIndex, packet, ((Blob)value).getBinaryStream());
/*      */       }
/* 1562 */       else if ((value instanceof Reader)) {
/* 1563 */         storeReader(mysql, parameterIndex, packet, (Reader)value);
/*      */       } else {
/* 1565 */         throw SQLError.createSQLException(Messages.getString("ServerPreparedStatement.18") + value.getClass().getName() + "'", "S1009", getExceptionInterceptor());
/*      */       }
/*      */     }
/*      */   }
/*      */   
/*      */ 
/*      */   private void serverPrepare(String sql)
/*      */     throws SQLException
/*      */   {
/* 1574 */     synchronized (this.connection.getMutex()) {
/* 1575 */       MysqlIO mysql = this.connection.getIO();
/*      */       
/* 1577 */       if (this.connection.getAutoGenerateTestcaseScript()) {
/* 1578 */         dumpPrepareForTestcase();
/*      */       }
/*      */       try
/*      */       {
/* 1582 */         long begin = 0L;
/*      */         
/* 1584 */         if (StringUtils.startsWithIgnoreCaseAndWs(sql, "LOAD DATA")) {
/* 1585 */           this.isLoadDataQuery = true;
/*      */         } else {
/* 1587 */           this.isLoadDataQuery = false;
/*      */         }
/*      */         
/* 1590 */         if (this.connection.getProfileSql()) {
/* 1591 */           begin = System.currentTimeMillis();
/*      */         }
/*      */         
/* 1594 */         String characterEncoding = null;
/* 1595 */         String connectionEncoding = this.connection.getEncoding();
/*      */         
/* 1597 */         if ((!this.isLoadDataQuery) && (this.connection.getUseUnicode()) && (connectionEncoding != null))
/*      */         {
/* 1599 */           characterEncoding = connectionEncoding;
/*      */         }
/*      */         
/* 1602 */         Buffer prepareResultPacket = mysql.sendCommand(22, sql, null, false, characterEncoding, 0);
/*      */         
/*      */ 
/*      */ 
/* 1606 */         if (this.connection.versionMeetsMinimum(4, 1, 1))
/*      */         {
/*      */ 
/*      */ 
/*      */ 
/* 1611 */           prepareResultPacket.setPosition(1);
/*      */         }
/*      */         else
/*      */         {
/* 1615 */           prepareResultPacket.setPosition(0);
/*      */         }
/*      */         
/* 1618 */         this.serverStatementId = prepareResultPacket.readLong();
/* 1619 */         this.fieldCount = prepareResultPacket.readInt();
/* 1620 */         this.parameterCount = prepareResultPacket.readInt();
/* 1621 */         this.parameterBindings = new BindValue[this.parameterCount];
/*      */         
/* 1623 */         for (int i = 0; i < this.parameterCount; i++) {
/* 1624 */           this.parameterBindings[i] = new BindValue();
/*      */         }
/*      */         
/* 1627 */         this.connection.incrementNumberOfPrepares();
/*      */         
/* 1629 */         if (this.profileSQL) {
/* 1630 */           this.eventSink.consumeEvent(new ProfilerEvent((byte)2, "", this.currentCatalog, this.connectionId, this.statementId, -1, System.currentTimeMillis(), mysql.getCurrentTimeNanosOrMillis() - begin, mysql.getQueryTimingUnits(), null, new Throwable(), truncateQueryToLog(sql)));
/*      */         }
/*      */         
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 1640 */         if ((this.parameterCount > 0) && 
/* 1641 */           (this.connection.versionMeetsMinimum(4, 1, 2)) && (!mysql.isVersion(5, 0, 0)))
/*      */         {
/* 1643 */           this.parameterFields = new Field[this.parameterCount];
/*      */           
/* 1645 */           Buffer metaDataPacket = mysql.readPacket();
/*      */           
/* 1647 */           int i = 0;
/*      */           
/*      */ 
/* 1650 */           while ((!metaDataPacket.isLastDataPacket()) && (i < this.parameterCount)) {
/* 1651 */             this.parameterFields[(i++)] = mysql.unpackField(metaDataPacket, false);
/*      */             
/* 1653 */             metaDataPacket = mysql.readPacket();
/*      */           }
/*      */         }
/*      */         
/*      */ 
/* 1658 */         if (this.fieldCount > 0) {
/* 1659 */           this.resultFields = new Field[this.fieldCount];
/*      */           
/* 1661 */           Buffer fieldPacket = mysql.readPacket();
/*      */           
/* 1663 */           int i = 0;
/*      */           
/*      */ 
/*      */ 
/* 1667 */           while ((!fieldPacket.isLastDataPacket()) && (i < this.fieldCount)) {
/* 1668 */             this.resultFields[(i++)] = mysql.unpackField(fieldPacket, false);
/*      */             
/* 1670 */             fieldPacket = mysql.readPacket();
/*      */           }
/*      */         }
/*      */       } catch (SQLException sqlEx) {
/* 1674 */         if (this.connection.getDumpQueriesOnException()) {
/* 1675 */           StringBuffer messageBuf = new StringBuffer(this.originalSql.length() + 32);
/*      */           
/* 1677 */           messageBuf.append("\n\nQuery being prepared when exception was thrown:\n\n");
/*      */           
/* 1679 */           messageBuf.append(this.originalSql);
/*      */           
/* 1681 */           sqlEx = ConnectionImpl.appendMessageToException(sqlEx, messageBuf.toString(), getExceptionInterceptor());
/*      */         }
/*      */         
/*      */ 
/* 1685 */         throw sqlEx;
/*      */ 
/*      */       }
/*      */       finally
/*      */       {
/* 1690 */         this.connection.getIO().clearInputStream();
/*      */       }
/*      */     }
/*      */   }
/*      */   
/*      */   private String truncateQueryToLog(String sql) {
/* 1696 */     String query = null;
/*      */     
/* 1698 */     if (sql.length() > this.connection.getMaxQuerySizeToLog()) {
/* 1699 */       StringBuffer queryBuf = new StringBuffer(this.connection.getMaxQuerySizeToLog() + 12);
/*      */       
/* 1701 */       queryBuf.append(sql.substring(0, this.connection.getMaxQuerySizeToLog()));
/* 1702 */       queryBuf.append(Messages.getString("MysqlIO.25"));
/*      */       
/* 1704 */       query = queryBuf.toString();
/*      */     } else {
/* 1706 */       query = sql;
/*      */     }
/*      */     
/* 1709 */     return query;
/*      */   }
/*      */   
/*      */   private void serverResetStatement() throws SQLException {
/* 1713 */     synchronized (this.connection.getMutex())
/*      */     {
/* 1715 */       MysqlIO mysql = this.connection.getIO();
/*      */       
/* 1717 */       Buffer packet = mysql.getSharedSendPacket();
/*      */       
/* 1719 */       packet.clear();
/* 1720 */       packet.writeByte((byte)26);
/* 1721 */       packet.writeLong(this.serverStatementId);
/*      */       try
/*      */       {
/* 1724 */         mysql.sendCommand(26, null, packet, !this.connection.versionMeetsMinimum(4, 1, 2), null, 0);
/*      */       }
/*      */       catch (SQLException sqlEx) {
/* 1727 */         throw sqlEx;
/*      */       } catch (Exception ex) {
/* 1729 */         SQLException sqlEx = SQLError.createSQLException(ex.toString(), "S1000", getExceptionInterceptor());
/*      */         
/* 1731 */         sqlEx.initCause(ex);
/*      */         
/* 1733 */         throw sqlEx;
/*      */       } finally {
/* 1735 */         mysql.clearInputStream();
/*      */       }
/*      */     }
/*      */   }
/*      */   
/*      */ 
/*      */   public void setArray(int i, Array x)
/*      */     throws SQLException
/*      */   {
/* 1744 */     throw SQLError.notImplemented();
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   public void setAsciiStream(int parameterIndex, InputStream x, int length)
/*      */     throws SQLException
/*      */   {
/* 1753 */     checkClosed();
/*      */     
/* 1755 */     if (x == null) {
/* 1756 */       setNull(parameterIndex, -2);
/*      */     } else {
/* 1758 */       BindValue binding = getBinding(parameterIndex, true);
/* 1759 */       setType(binding, 252);
/*      */       
/* 1761 */       binding.value = x;
/* 1762 */       binding.isNull = false;
/* 1763 */       binding.isLongData = true;
/*      */       
/* 1765 */       if (this.connection.getUseStreamLengthsInPrepStmts()) {
/* 1766 */         binding.bindLength = length;
/*      */       } else {
/* 1768 */         binding.bindLength = -1L;
/*      */       }
/*      */     }
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */   public void setBigDecimal(int parameterIndex, BigDecimal x)
/*      */     throws SQLException
/*      */   {
/* 1778 */     checkClosed();
/*      */     
/* 1780 */     if (x == null) {
/* 1781 */       setNull(parameterIndex, 3);
/*      */     }
/*      */     else {
/* 1784 */       BindValue binding = getBinding(parameterIndex, false);
/*      */       
/* 1786 */       if (this.connection.versionMeetsMinimum(5, 0, 3)) {
/* 1787 */         setType(binding, 246);
/*      */       } else {
/* 1789 */         setType(binding, this.stringTypeCode);
/*      */       }
/*      */       
/* 1792 */       binding.value = StringUtils.fixDecimalExponent(StringUtils.consistentToString(x));
/*      */       
/* 1794 */       binding.isNull = false;
/* 1795 */       binding.isLongData = false;
/*      */     }
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   public void setBinaryStream(int parameterIndex, InputStream x, int length)
/*      */     throws SQLException
/*      */   {
/* 1805 */     checkClosed();
/*      */     
/* 1807 */     if (x == null) {
/* 1808 */       setNull(parameterIndex, -2);
/*      */     } else {
/* 1810 */       BindValue binding = getBinding(parameterIndex, true);
/* 1811 */       setType(binding, 252);
/*      */       
/* 1813 */       binding.value = x;
/* 1814 */       binding.isNull = false;
/* 1815 */       binding.isLongData = true;
/*      */       
/* 1817 */       if (this.connection.getUseStreamLengthsInPrepStmts()) {
/* 1818 */         binding.bindLength = length;
/*      */       } else {
/* 1820 */         binding.bindLength = -1L;
/*      */       }
/*      */     }
/*      */   }
/*      */   
/*      */ 
/*      */   public void setBlob(int parameterIndex, Blob x)
/*      */     throws SQLException
/*      */   {
/* 1829 */     checkClosed();
/*      */     
/* 1831 */     if (x == null) {
/* 1832 */       setNull(parameterIndex, -2);
/*      */     } else {
/* 1834 */       BindValue binding = getBinding(parameterIndex, true);
/* 1835 */       setType(binding, 252);
/*      */       
/* 1837 */       binding.value = x;
/* 1838 */       binding.isNull = false;
/* 1839 */       binding.isLongData = true;
/*      */       
/* 1841 */       if (this.connection.getUseStreamLengthsInPrepStmts()) {
/* 1842 */         binding.bindLength = x.length();
/*      */       } else {
/* 1844 */         binding.bindLength = -1L;
/*      */       }
/*      */     }
/*      */   }
/*      */   
/*      */ 
/*      */   public void setBoolean(int parameterIndex, boolean x)
/*      */     throws SQLException
/*      */   {
/* 1853 */     setByte(parameterIndex, (byte)(x ? 1 : 0));
/*      */   }
/*      */   
/*      */ 
/*      */   public void setByte(int parameterIndex, byte x)
/*      */     throws SQLException
/*      */   {
/* 1860 */     checkClosed();
/*      */     
/* 1862 */     BindValue binding = getBinding(parameterIndex, false);
/* 1863 */     setType(binding, 1);
/*      */     
/* 1865 */     binding.value = null;
/* 1866 */     binding.byteBinding = x;
/* 1867 */     binding.isNull = false;
/* 1868 */     binding.isLongData = false;
/*      */   }
/*      */   
/*      */ 
/*      */   public void setBytes(int parameterIndex, byte[] x)
/*      */     throws SQLException
/*      */   {
/* 1875 */     checkClosed();
/*      */     
/* 1877 */     if (x == null) {
/* 1878 */       setNull(parameterIndex, -2);
/*      */     } else {
/* 1880 */       BindValue binding = getBinding(parameterIndex, false);
/* 1881 */       setType(binding, 253);
/*      */       
/* 1883 */       binding.value = x;
/* 1884 */       binding.isNull = false;
/* 1885 */       binding.isLongData = false;
/*      */     }
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   public void setCharacterStream(int parameterIndex, Reader reader, int length)
/*      */     throws SQLException
/*      */   {
/* 1895 */     checkClosed();
/*      */     
/* 1897 */     if (reader == null) {
/* 1898 */       setNull(parameterIndex, -2);
/*      */     } else {
/* 1900 */       BindValue binding = getBinding(parameterIndex, true);
/* 1901 */       setType(binding, 252);
/*      */       
/* 1903 */       binding.value = reader;
/* 1904 */       binding.isNull = false;
/* 1905 */       binding.isLongData = true;
/*      */       
/* 1907 */       if (this.connection.getUseStreamLengthsInPrepStmts()) {
/* 1908 */         binding.bindLength = length;
/*      */       } else {
/* 1910 */         binding.bindLength = -1L;
/*      */       }
/*      */     }
/*      */   }
/*      */   
/*      */ 
/*      */   public void setClob(int parameterIndex, Clob x)
/*      */     throws SQLException
/*      */   {
/* 1919 */     checkClosed();
/*      */     
/* 1921 */     if (x == null) {
/* 1922 */       setNull(parameterIndex, -2);
/*      */     } else {
/* 1924 */       BindValue binding = getBinding(parameterIndex, true);
/* 1925 */       setType(binding, 252);
/*      */       
/* 1927 */       binding.value = x.getCharacterStream();
/* 1928 */       binding.isNull = false;
/* 1929 */       binding.isLongData = true;
/*      */       
/* 1931 */       if (this.connection.getUseStreamLengthsInPrepStmts()) {
/* 1932 */         binding.bindLength = x.length();
/*      */       } else {
/* 1934 */         binding.bindLength = -1L;
/*      */       }
/*      */     }
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public void setDate(int parameterIndex, java.sql.Date x)
/*      */     throws SQLException
/*      */   {
/* 1952 */     setDate(parameterIndex, x, null);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public void setDate(int parameterIndex, java.sql.Date x, Calendar cal)
/*      */     throws SQLException
/*      */   {
/* 1971 */     if (x == null) {
/* 1972 */       setNull(parameterIndex, 91);
/*      */     } else {
/* 1974 */       BindValue binding = getBinding(parameterIndex, false);
/* 1975 */       setType(binding, 10);
/*      */       
/* 1977 */       binding.value = x;
/* 1978 */       binding.isNull = false;
/* 1979 */       binding.isLongData = false;
/*      */     }
/*      */   }
/*      */   
/*      */ 
/*      */   public void setDouble(int parameterIndex, double x)
/*      */     throws SQLException
/*      */   {
/* 1987 */     checkClosed();
/*      */     
/* 1989 */     if ((!this.connection.getAllowNanAndInf()) && ((x == Double.POSITIVE_INFINITY) || (x == Double.NEGATIVE_INFINITY) || (Double.isNaN(x))))
/*      */     {
/*      */ 
/* 1992 */       throw SQLError.createSQLException("'" + x + "' is not a valid numeric or approximate numeric value", "S1009", getExceptionInterceptor());
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*      */ 
/* 1998 */     BindValue binding = getBinding(parameterIndex, false);
/* 1999 */     setType(binding, 5);
/*      */     
/* 2001 */     binding.value = null;
/* 2002 */     binding.doubleBinding = x;
/* 2003 */     binding.isNull = false;
/* 2004 */     binding.isLongData = false;
/*      */   }
/*      */   
/*      */ 
/*      */   public void setFloat(int parameterIndex, float x)
/*      */     throws SQLException
/*      */   {
/* 2011 */     checkClosed();
/*      */     
/* 2013 */     BindValue binding = getBinding(parameterIndex, false);
/* 2014 */     setType(binding, 4);
/*      */     
/* 2016 */     binding.value = null;
/* 2017 */     binding.floatBinding = x;
/* 2018 */     binding.isNull = false;
/* 2019 */     binding.isLongData = false;
/*      */   }
/*      */   
/*      */ 
/*      */   public void setInt(int parameterIndex, int x)
/*      */     throws SQLException
/*      */   {
/* 2026 */     checkClosed();
/*      */     
/* 2028 */     BindValue binding = getBinding(parameterIndex, false);
/* 2029 */     setType(binding, 3);
/*      */     
/* 2031 */     binding.value = null;
/* 2032 */     binding.intBinding = x;
/* 2033 */     binding.isNull = false;
/* 2034 */     binding.isLongData = false;
/*      */   }
/*      */   
/*      */ 
/*      */   public void setLong(int parameterIndex, long x)
/*      */     throws SQLException
/*      */   {
/* 2041 */     checkClosed();
/*      */     
/* 2043 */     BindValue binding = getBinding(parameterIndex, false);
/* 2044 */     setType(binding, 8);
/*      */     
/* 2046 */     binding.value = null;
/* 2047 */     binding.longBinding = x;
/* 2048 */     binding.isNull = false;
/* 2049 */     binding.isLongData = false;
/*      */   }
/*      */   
/*      */ 
/*      */   public void setNull(int parameterIndex, int sqlType)
/*      */     throws SQLException
/*      */   {
/* 2056 */     checkClosed();
/*      */     
/* 2058 */     BindValue binding = getBinding(parameterIndex, false);
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 2064 */     if (binding.bufferType == 0) {
/* 2065 */       setType(binding, 6);
/*      */     }
/*      */     
/* 2068 */     binding.value = null;
/* 2069 */     binding.isNull = true;
/* 2070 */     binding.isLongData = false;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */   public void setNull(int parameterIndex, int sqlType, String typeName)
/*      */     throws SQLException
/*      */   {
/* 2078 */     checkClosed();
/*      */     
/* 2080 */     BindValue binding = getBinding(parameterIndex, false);
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 2086 */     if (binding.bufferType == 0) {
/* 2087 */       setType(binding, 6);
/*      */     }
/*      */     
/* 2090 */     binding.value = null;
/* 2091 */     binding.isNull = true;
/* 2092 */     binding.isLongData = false;
/*      */   }
/*      */   
/*      */ 
/*      */   public void setRef(int i, Ref x)
/*      */     throws SQLException
/*      */   {
/* 2099 */     throw SQLError.notImplemented();
/*      */   }
/*      */   
/*      */ 
/*      */   public void setShort(int parameterIndex, short x)
/*      */     throws SQLException
/*      */   {
/* 2106 */     checkClosed();
/*      */     
/* 2108 */     BindValue binding = getBinding(parameterIndex, false);
/* 2109 */     setType(binding, 2);
/*      */     
/* 2111 */     binding.value = null;
/* 2112 */     binding.shortBinding = x;
/* 2113 */     binding.isNull = false;
/* 2114 */     binding.isLongData = false;
/*      */   }
/*      */   
/*      */ 
/*      */   public void setString(int parameterIndex, String x)
/*      */     throws SQLException
/*      */   {
/* 2121 */     checkClosed();
/*      */     
/* 2123 */     if (x == null) {
/* 2124 */       setNull(parameterIndex, 1);
/*      */     } else {
/* 2126 */       BindValue binding = getBinding(parameterIndex, false);
/*      */       
/* 2128 */       setType(binding, this.stringTypeCode);
/*      */       
/* 2130 */       binding.value = x;
/* 2131 */       binding.isNull = false;
/* 2132 */       binding.isLongData = false;
/*      */     }
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public void setTime(int parameterIndex, Time x)
/*      */     throws SQLException
/*      */   {
/* 2149 */     setTimeInternal(parameterIndex, x, null, this.connection.getDefaultTimeZone(), false);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public void setTime(int parameterIndex, Time x, Calendar cal)
/*      */     throws SQLException
/*      */   {
/* 2169 */     setTimeInternal(parameterIndex, x, cal, cal.getTimeZone(), true);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public void setTimeInternal(int parameterIndex, Time x, Calendar targetCalendar, TimeZone tz, boolean rollForward)
/*      */     throws SQLException
/*      */   {
/* 2190 */     if (x == null) {
/* 2191 */       setNull(parameterIndex, 92);
/*      */     } else {
/* 2193 */       BindValue binding = getBinding(parameterIndex, false);
/* 2194 */       setType(binding, 11);
/*      */       
/* 2196 */       if (!this.useLegacyDatetimeCode) {
/* 2197 */         binding.value = x;
/*      */       } else {
/* 2199 */         Calendar sessionCalendar = getCalendarInstanceForSessionOrNew();
/*      */         
/* 2201 */         synchronized (sessionCalendar) {
/* 2202 */           binding.value = TimeUtil.changeTimezone(this.connection, sessionCalendar, targetCalendar, x, tz, this.connection.getServerTimezoneTZ(), rollForward);
/*      */         }
/*      */       }
/*      */       
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 2211 */       binding.isNull = false;
/* 2212 */       binding.isLongData = false;
/*      */     }
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public void setTimestamp(int parameterIndex, Timestamp x)
/*      */     throws SQLException
/*      */   {
/* 2230 */     setTimestampInternal(parameterIndex, x, null, this.connection.getDefaultTimeZone(), false);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public void setTimestamp(int parameterIndex, Timestamp x, Calendar cal)
/*      */     throws SQLException
/*      */   {
/* 2249 */     setTimestampInternal(parameterIndex, x, cal, cal.getTimeZone(), true);
/*      */   }
/*      */   
/*      */ 
/*      */   protected void setTimestampInternal(int parameterIndex, Timestamp x, Calendar targetCalendar, TimeZone tz, boolean rollForward)
/*      */     throws SQLException
/*      */   {
/* 2256 */     if (x == null) {
/* 2257 */       setNull(parameterIndex, 93);
/*      */     } else {
/* 2259 */       BindValue binding = getBinding(parameterIndex, false);
/* 2260 */       setType(binding, 12);
/*      */       
/* 2262 */       if (!this.useLegacyDatetimeCode) {
/* 2263 */         binding.value = x;
/*      */       } else {
/* 2265 */         Calendar sessionCalendar = this.connection.getUseJDBCCompliantTimezoneShift() ? this.connection.getUtcCalendar() : getCalendarInstanceForSessionOrNew();
/*      */         
/*      */ 
/*      */ 
/* 2269 */         synchronized (sessionCalendar) {
/* 2270 */           binding.value = TimeUtil.changeTimezone(this.connection, sessionCalendar, targetCalendar, x, tz, this.connection.getServerTimezoneTZ(), rollForward);
/*      */         }
/*      */         
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 2278 */         binding.isNull = false;
/* 2279 */         binding.isLongData = false;
/*      */       }
/*      */     }
/*      */   }
/*      */   
/*      */   protected void setType(BindValue oldValue, int bufferType) {
/* 2285 */     if (oldValue.bufferType != bufferType) {
/* 2286 */       this.sendTypesToServer = true;
/*      */     }
/*      */     
/* 2289 */     oldValue.bufferType = bufferType;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   /**
/*      */    * @deprecated
/*      */    */
/*      */   public void setUnicodeStream(int parameterIndex, InputStream x, int length)
/*      */     throws SQLException
/*      */   {
/* 2313 */     checkClosed();
/*      */     
/* 2315 */     throw SQLError.notImplemented();
/*      */   }
/*      */   
/*      */ 
/*      */   public void setURL(int parameterIndex, URL x)
/*      */     throws SQLException
/*      */   {
/* 2322 */     checkClosed();
/*      */     
/* 2324 */     setString(parameterIndex, x.toString());
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   private void storeBinding(Buffer packet, BindValue bindValue, MysqlIO mysql)
/*      */     throws SQLException
/*      */   {
/*      */     try
/*      */     {
/* 2341 */       Object value = bindValue.value;
/*      */       
/*      */ 
/*      */ 
/*      */ 
/* 2346 */       switch (bindValue.bufferType)
/*      */       {
/*      */       case 1: 
/* 2349 */         packet.writeByte(bindValue.byteBinding);
/* 2350 */         return;
/*      */       case 2: 
/* 2352 */         packet.ensureCapacity(2);
/* 2353 */         packet.writeInt(bindValue.shortBinding);
/* 2354 */         return;
/*      */       case 3: 
/* 2356 */         packet.ensureCapacity(4);
/* 2357 */         packet.writeLong(bindValue.intBinding);
/* 2358 */         return;
/*      */       case 8: 
/* 2360 */         packet.ensureCapacity(8);
/* 2361 */         packet.writeLongLong(bindValue.longBinding);
/* 2362 */         return;
/*      */       case 4: 
/* 2364 */         packet.ensureCapacity(4);
/* 2365 */         packet.writeFloat(bindValue.floatBinding);
/* 2366 */         return;
/*      */       case 5: 
/* 2368 */         packet.ensureCapacity(8);
/* 2369 */         packet.writeDouble(bindValue.doubleBinding);
/* 2370 */         return;
/*      */       case 11: 
/* 2372 */         storeTime(packet, (Time)value);
/* 2373 */         return;
/*      */       case 7: 
/*      */       case 10: 
/*      */       case 12: 
/* 2377 */         storeDateTime(packet, (java.util.Date)value, mysql, bindValue.bufferType);
/* 2378 */         return;
/*      */       case 0: 
/*      */       case 15: 
/*      */       case 246: 
/*      */       case 253: 
/*      */       case 254: 
/* 2384 */         if ((value instanceof byte[])) {
/* 2385 */           packet.writeLenBytes((byte[])value);
/* 2386 */         } else if (!this.isLoadDataQuery) {
/* 2387 */           packet.writeLenString((String)value, this.charEncoding, this.connection.getServerCharacterEncoding(), this.charConverter, this.connection.parserKnowsUnicode(), this.connection);
/*      */ 
/*      */         }
/*      */         else
/*      */         {
/*      */ 
/* 2393 */           packet.writeLenBytes(((String)value).getBytes());
/*      */         }
/*      */         
/* 2396 */         return;
/*      */       }
/*      */     }
/*      */     catch (UnsupportedEncodingException uEE)
/*      */     {
/* 2401 */       throw SQLError.createSQLException(Messages.getString("ServerPreparedStatement.22") + this.connection.getEncoding() + "'", "S1000", getExceptionInterceptor());
/*      */     }
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   private void storeDateTime412AndOlder(Buffer intoBuf, java.util.Date dt, int bufferType)
/*      */     throws SQLException
/*      */   {
/* 2411 */     Calendar sessionCalendar = null;
/*      */     
/* 2413 */     if (!this.useLegacyDatetimeCode) {
/* 2414 */       if (bufferType == 10) {
/* 2415 */         sessionCalendar = getDefaultTzCalendar();
/*      */       } else {
/* 2417 */         sessionCalendar = getServerTzCalendar();
/*      */       }
/*      */     } else {
/* 2420 */       sessionCalendar = ((dt instanceof Timestamp)) && (this.connection.getUseJDBCCompliantTimezoneShift()) ? this.connection.getUtcCalendar() : getCalendarInstanceForSessionOrNew();
/*      */     }
/*      */     
/*      */ 
/*      */ 
/* 2425 */     synchronized (sessionCalendar) {
/* 2426 */       java.util.Date oldTime = sessionCalendar.getTime();
/*      */       try
/*      */       {
/* 2429 */         intoBuf.ensureCapacity(8);
/* 2430 */         intoBuf.writeByte((byte)7);
/*      */         
/* 2432 */         sessionCalendar.setTime(dt);
/*      */         
/* 2434 */         int year = sessionCalendar.get(1);
/* 2435 */         int month = sessionCalendar.get(2) + 1;
/* 2436 */         int date = sessionCalendar.get(5);
/*      */         
/* 2438 */         intoBuf.writeInt(year);
/* 2439 */         intoBuf.writeByte((byte)month);
/* 2440 */         intoBuf.writeByte((byte)date);
/*      */         
/* 2442 */         if ((dt instanceof java.sql.Date)) {
/* 2443 */           intoBuf.writeByte((byte)0);
/* 2444 */           intoBuf.writeByte((byte)0);
/* 2445 */           intoBuf.writeByte((byte)0);
/*      */         } else {
/* 2447 */           intoBuf.writeByte((byte)sessionCalendar.get(11));
/*      */           
/* 2449 */           intoBuf.writeByte((byte)sessionCalendar.get(12));
/*      */           
/* 2451 */           intoBuf.writeByte((byte)sessionCalendar.get(13));
/*      */         }
/*      */       }
/*      */       finally {
/* 2455 */         sessionCalendar.setTime(oldTime);
/*      */       }
/*      */     }
/*      */   }
/*      */   
/*      */   private void storeDateTime(Buffer intoBuf, java.util.Date dt, MysqlIO mysql, int bufferType) throws SQLException
/*      */   {
/* 2462 */     if (this.connection.versionMeetsMinimum(4, 1, 3)) {
/* 2463 */       storeDateTime413AndNewer(intoBuf, dt, bufferType);
/*      */     } else {
/* 2465 */       storeDateTime412AndOlder(intoBuf, dt, bufferType);
/*      */     }
/*      */   }
/*      */   
/*      */   private void storeDateTime413AndNewer(Buffer intoBuf, java.util.Date dt, int bufferType) throws SQLException
/*      */   {
/* 2471 */     Calendar sessionCalendar = null;
/*      */     
/* 2473 */     if (!this.useLegacyDatetimeCode) {
/* 2474 */       if (bufferType == 10) {
/* 2475 */         sessionCalendar = getDefaultTzCalendar();
/*      */       } else {
/* 2477 */         sessionCalendar = getServerTzCalendar();
/*      */       }
/*      */     } else {
/* 2480 */       sessionCalendar = ((dt instanceof Timestamp)) && (this.connection.getUseJDBCCompliantTimezoneShift()) ? this.connection.getUtcCalendar() : getCalendarInstanceForSessionOrNew();
/*      */     }
/*      */     
/*      */ 
/*      */ 
/* 2485 */     synchronized (sessionCalendar) {
/* 2486 */       java.util.Date oldTime = sessionCalendar.getTime();
/*      */       try
/*      */       {
/* 2489 */         sessionCalendar.setTime(dt);
/*      */         
/* 2491 */         if ((dt instanceof java.sql.Date)) {
/* 2492 */           sessionCalendar.set(11, 0);
/* 2493 */           sessionCalendar.set(12, 0);
/* 2494 */           sessionCalendar.set(13, 0);
/*      */         }
/*      */         
/* 2497 */         byte length = 7;
/*      */         
/* 2499 */         if ((dt instanceof Timestamp)) {
/* 2500 */           length = 11;
/*      */         }
/*      */         
/* 2503 */         intoBuf.ensureCapacity(length);
/*      */         
/* 2505 */         intoBuf.writeByte(length);
/*      */         
/* 2507 */         int year = sessionCalendar.get(1);
/* 2508 */         int month = sessionCalendar.get(2) + 1;
/* 2509 */         int date = sessionCalendar.get(5);
/*      */         
/* 2511 */         intoBuf.writeInt(year);
/* 2512 */         intoBuf.writeByte((byte)month);
/* 2513 */         intoBuf.writeByte((byte)date);
/*      */         
/* 2515 */         if ((dt instanceof java.sql.Date)) {
/* 2516 */           intoBuf.writeByte((byte)0);
/* 2517 */           intoBuf.writeByte((byte)0);
/* 2518 */           intoBuf.writeByte((byte)0);
/*      */         } else {
/* 2520 */           intoBuf.writeByte((byte)sessionCalendar.get(11));
/*      */           
/* 2522 */           intoBuf.writeByte((byte)sessionCalendar.get(12));
/*      */           
/* 2524 */           intoBuf.writeByte((byte)sessionCalendar.get(13));
/*      */         }
/*      */         
/*      */ 
/* 2528 */         if (length == 11)
/*      */         {
/* 2530 */           intoBuf.writeLong(((Timestamp)dt).getNanos() / 1000);
/*      */         }
/*      */       }
/*      */       finally {
/* 2534 */         sessionCalendar.setTime(oldTime);
/*      */       }
/*      */     }
/*      */   }
/*      */   
/*      */   private Calendar getServerTzCalendar() {
/* 2540 */     synchronized (this) {
/* 2541 */       if (this.serverTzCalendar == null) {
/* 2542 */         this.serverTzCalendar = new GregorianCalendar(this.connection.getServerTimezoneTZ());
/*      */       }
/*      */       
/* 2545 */       return this.serverTzCalendar;
/*      */     }
/*      */   }
/*      */   
/*      */   private Calendar getDefaultTzCalendar() {
/* 2550 */     synchronized (this) {
/* 2551 */       if (this.defaultTzCalendar == null) {
/* 2552 */         this.defaultTzCalendar = new GregorianCalendar(TimeZone.getDefault());
/*      */       }
/*      */       
/* 2555 */       return this.defaultTzCalendar;
/*      */     }
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */   private void storeReader(MysqlIO mysql, int parameterIndex, Buffer packet, Reader inStream)
/*      */     throws SQLException
/*      */   {
/* 2564 */     String forcedEncoding = this.connection.getClobCharacterEncoding();
/*      */     
/* 2566 */     String clobEncoding = forcedEncoding == null ? this.connection.getEncoding() : forcedEncoding;
/*      */     
/*      */ 
/* 2569 */     int maxBytesChar = 2;
/*      */     
/* 2571 */     if (clobEncoding != null) {
/* 2572 */       if (!clobEncoding.equals("UTF-16")) {
/* 2573 */         maxBytesChar = this.connection.getMaxBytesPerChar(clobEncoding);
/*      */         
/* 2575 */         if (maxBytesChar == 1) {
/* 2576 */           maxBytesChar = 2;
/*      */         }
/*      */       } else {
/* 2579 */         maxBytesChar = 4;
/*      */       }
/*      */     }
/*      */     
/* 2583 */     char[] buf = new char[8192 / maxBytesChar];
/*      */     
/* 2585 */     int numRead = 0;
/*      */     
/* 2587 */     int bytesInPacket = 0;
/* 2588 */     int totalBytesRead = 0;
/* 2589 */     int bytesReadAtLastSend = 0;
/* 2590 */     int packetIsFullAt = this.connection.getBlobSendChunkSize();
/*      */     
/*      */ 
/*      */     try
/*      */     {
/* 2595 */       packet.clear();
/* 2596 */       packet.writeByte((byte)24);
/* 2597 */       packet.writeLong(this.serverStatementId);
/* 2598 */       packet.writeInt(parameterIndex);
/*      */       
/* 2600 */       boolean readAny = false;
/*      */       
/* 2602 */       while ((numRead = inStream.read(buf)) != -1) {
/* 2603 */         readAny = true;
/*      */         
/* 2605 */         byte[] valueAsBytes = StringUtils.getBytes(buf, null, clobEncoding, this.connection.getServerCharacterEncoding(), 0, numRead, this.connection.parserKnowsUnicode(), getExceptionInterceptor());
/*      */         
/*      */ 
/*      */ 
/*      */ 
/* 2610 */         packet.writeBytesNoNull(valueAsBytes, 0, valueAsBytes.length);
/*      */         
/* 2612 */         bytesInPacket += valueAsBytes.length;
/* 2613 */         totalBytesRead += valueAsBytes.length;
/*      */         
/* 2615 */         if (bytesInPacket >= packetIsFullAt) {
/* 2616 */           bytesReadAtLastSend = totalBytesRead;
/*      */           
/* 2618 */           mysql.sendCommand(24, null, packet, true, null, 0);
/*      */           
/*      */ 
/* 2621 */           bytesInPacket = 0;
/* 2622 */           packet.clear();
/* 2623 */           packet.writeByte((byte)24);
/* 2624 */           packet.writeLong(this.serverStatementId);
/* 2625 */           packet.writeInt(parameterIndex);
/*      */         }
/*      */       }
/*      */       
/* 2629 */       if (totalBytesRead != bytesReadAtLastSend) {
/* 2630 */         mysql.sendCommand(24, null, packet, true, null, 0);
/*      */       }
/*      */       
/*      */ 
/* 2634 */       if (!readAny) {
/* 2635 */         mysql.sendCommand(24, null, packet, true, null, 0);
/*      */       }
/*      */     }
/*      */     catch (IOException ioEx) {
/* 2639 */       SQLException sqlEx = SQLError.createSQLException(Messages.getString("ServerPreparedStatement.24") + ioEx.toString(), "S1000", getExceptionInterceptor());
/*      */       
/*      */ 
/* 2642 */       sqlEx.initCause(ioEx);
/*      */       
/* 2644 */       throw sqlEx;
/*      */     } finally {
/* 2646 */       if ((this.connection.getAutoClosePStmtStreams()) && 
/* 2647 */         (inStream != null)) {
/*      */         try {
/* 2649 */           inStream.close();
/*      */         }
/*      */         catch (IOException ioEx) {}
/*      */       }
/*      */     }
/*      */   }
/*      */   
/*      */ 
/*      */   private void storeStream(MysqlIO mysql, int parameterIndex, Buffer packet, InputStream inStream)
/*      */     throws SQLException
/*      */   {
/* 2660 */     byte[] buf = new byte[' '];
/*      */     
/* 2662 */     int numRead = 0;
/*      */     try
/*      */     {
/* 2665 */       int bytesInPacket = 0;
/* 2666 */       int totalBytesRead = 0;
/* 2667 */       int bytesReadAtLastSend = 0;
/* 2668 */       int packetIsFullAt = this.connection.getBlobSendChunkSize();
/*      */       
/* 2670 */       packet.clear();
/* 2671 */       packet.writeByte((byte)24);
/* 2672 */       packet.writeLong(this.serverStatementId);
/* 2673 */       packet.writeInt(parameterIndex);
/*      */       
/* 2675 */       boolean readAny = false;
/*      */       
/* 2677 */       while ((numRead = inStream.read(buf)) != -1)
/*      */       {
/* 2679 */         readAny = true;
/*      */         
/* 2681 */         packet.writeBytesNoNull(buf, 0, numRead);
/* 2682 */         bytesInPacket += numRead;
/* 2683 */         totalBytesRead += numRead;
/*      */         
/* 2685 */         if (bytesInPacket >= packetIsFullAt) {
/* 2686 */           bytesReadAtLastSend = totalBytesRead;
/*      */           
/* 2688 */           mysql.sendCommand(24, null, packet, true, null, 0);
/*      */           
/*      */ 
/* 2691 */           bytesInPacket = 0;
/* 2692 */           packet.clear();
/* 2693 */           packet.writeByte((byte)24);
/* 2694 */           packet.writeLong(this.serverStatementId);
/* 2695 */           packet.writeInt(parameterIndex);
/*      */         }
/*      */       }
/*      */       
/* 2699 */       if (totalBytesRead != bytesReadAtLastSend) {
/* 2700 */         mysql.sendCommand(24, null, packet, true, null, 0);
/*      */       }
/*      */       
/*      */ 
/* 2704 */       if (!readAny) {
/* 2705 */         mysql.sendCommand(24, null, packet, true, null, 0);
/*      */       }
/*      */     }
/*      */     catch (IOException ioEx) {
/* 2709 */       SQLException sqlEx = SQLError.createSQLException(Messages.getString("ServerPreparedStatement.25") + ioEx.toString(), "S1000", getExceptionInterceptor());
/*      */       
/*      */ 
/* 2712 */       sqlEx.initCause(ioEx);
/*      */       
/* 2714 */       throw sqlEx;
/*      */     } finally {
/* 2716 */       if ((this.connection.getAutoClosePStmtStreams()) && 
/* 2717 */         (inStream != null)) {
/*      */         try {
/* 2719 */           inStream.close();
/*      */         }
/*      */         catch (IOException ioEx) {}
/*      */       }
/*      */     }
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public String toString()
/*      */   {
/* 2732 */     StringBuffer toStringBuf = new StringBuffer();
/*      */     
/* 2734 */     toStringBuf.append("com.mysql.jdbc.ServerPreparedStatement[");
/* 2735 */     toStringBuf.append(this.serverStatementId);
/* 2736 */     toStringBuf.append("] - ");
/*      */     try
/*      */     {
/* 2739 */       toStringBuf.append(asSql());
/*      */     } catch (SQLException sqlEx) {
/* 2741 */       toStringBuf.append(Messages.getString("ServerPreparedStatement.6"));
/* 2742 */       toStringBuf.append(sqlEx);
/*      */     }
/*      */     
/* 2745 */     return toStringBuf.toString();
/*      */   }
/*      */   
/*      */   protected long getServerStatementId() {
/* 2749 */     return this.serverStatementId;
/*      */   }
/*      */   
/* 2752 */   private boolean hasCheckedRewrite = false;
/* 2753 */   private boolean canRewrite = false;
/*      */   
/*      */   public synchronized boolean canRewriteAsMultiValueInsertAtSqlLevel() throws SQLException {
/* 2756 */     if (!this.hasCheckedRewrite) {
/* 2757 */       this.hasCheckedRewrite = true;
/* 2758 */       this.canRewrite = canRewrite(this.originalSql, isOnDuplicateKeyUpdate(), getLocationOfOnDuplicateKeyUpdate(), 0);
/*      */       
/* 2760 */       this.parseInfo = new PreparedStatement.ParseInfo(this, this.originalSql, this.connection, this.connection.getMetaData(), this.charEncoding, this.charConverter);
/*      */     }
/*      */     
/* 2763 */     return this.canRewrite;
/*      */   }
/*      */   
/*      */   public synchronized boolean canRewriteAsMultivalueInsertStatement() throws SQLException
/*      */   {
/* 2768 */     if (!canRewriteAsMultiValueInsertAtSqlLevel()) {
/* 2769 */       return false;
/*      */     }
/*      */     
/* 2772 */     BindValue[] currentBindValues = null;
/* 2773 */     BindValue[] previousBindValues = null;
/*      */     
/* 2775 */     int nbrCommands = this.batchedArgs.size();
/*      */     
/*      */ 
/*      */ 
/* 2779 */     for (int commandIndex = 0; commandIndex < nbrCommands; commandIndex++) {
/* 2780 */       Object arg = this.batchedArgs.get(commandIndex);
/*      */       
/* 2782 */       if (!(arg instanceof String))
/*      */       {
/* 2784 */         currentBindValues = ((BatchedBindValues)arg).batchedParameterValues;
/*      */         
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 2790 */         if (previousBindValues != null) {
/* 2791 */           for (int j = 0; j < this.parameterBindings.length; j++) {
/* 2792 */             if (currentBindValues[j].bufferType != previousBindValues[j].bufferType) {
/* 2793 */               return false;
/*      */             }
/*      */           }
/*      */         }
/*      */       }
/*      */     }
/*      */     
/*      */ 
/*      */ 
/* 2802 */     return true;
/*      */   }
/*      */   
/* 2805 */   private int locationOfOnDuplicateKeyUpdate = -2;
/*      */   
/*      */   protected synchronized int getLocationOfOnDuplicateKeyUpdate() {
/* 2808 */     if (this.locationOfOnDuplicateKeyUpdate == -2) {
/* 2809 */       this.locationOfOnDuplicateKeyUpdate = getOnDuplicateKeyLocation(this.originalSql);
/*      */     }
/*      */     
/* 2812 */     return this.locationOfOnDuplicateKeyUpdate;
/*      */   }
/*      */   
/*      */   protected synchronized boolean isOnDuplicateKeyUpdate() {
/* 2816 */     return getLocationOfOnDuplicateKeyUpdate() != -1;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   protected long[] computeMaxParameterSetSizeAndBatchSize(int numBatchedArgs)
/*      */   {
/* 2827 */     long sizeOfEntireBatch = 10L;
/* 2828 */     long maxSizeOfParameterSet = 0L;
/*      */     
/* 2830 */     for (int i = 0; i < numBatchedArgs; i++) {
/* 2831 */       BindValue[] paramArg = ((BatchedBindValues)this.batchedArgs.get(i)).batchedParameterValues;
/*      */       
/* 2833 */       long sizeOfParameterSet = 0L;
/*      */       
/* 2835 */       sizeOfParameterSet += (this.parameterCount + 7) / 8;
/*      */       
/* 2837 */       sizeOfParameterSet += this.parameterCount * 2;
/*      */       
/* 2839 */       for (int j = 0; j < this.parameterBindings.length; j++) {
/* 2840 */         if (!paramArg[j].isNull)
/*      */         {
/* 2842 */           long size = paramArg[j].getBoundLength();
/*      */           
/* 2844 */           if (paramArg[j].isLongData) {
/* 2845 */             if (size != -1L) {
/* 2846 */               sizeOfParameterSet += size;
/*      */             }
/*      */           } else {
/* 2849 */             sizeOfParameterSet += size;
/*      */           }
/*      */         }
/*      */       }
/*      */       
/* 2854 */       sizeOfEntireBatch += sizeOfParameterSet;
/*      */       
/* 2856 */       if (sizeOfParameterSet > maxSizeOfParameterSet) {
/* 2857 */         maxSizeOfParameterSet = sizeOfParameterSet;
/*      */       }
/*      */     }
/*      */     
/* 2861 */     return new long[] { maxSizeOfParameterSet, sizeOfEntireBatch };
/*      */   }
/*      */   
/*      */   protected int setOneBatchedParameterSet(java.sql.PreparedStatement batchedStatement, int batchedParamIndex, Object paramSet)
/*      */     throws SQLException
/*      */   {
/* 2867 */     BindValue[] paramArg = ((BatchedBindValues)paramSet).batchedParameterValues;
/*      */     
/* 2869 */     for (int j = 0; j < paramArg.length; j++) {
/* 2870 */       if (paramArg[j].isNull) {
/* 2871 */         batchedStatement.setNull(batchedParamIndex++, 0);
/*      */       }
/* 2873 */       else if (paramArg[j].isLongData) {
/* 2874 */         Object value = paramArg[j].value;
/*      */         
/* 2876 */         if ((value instanceof InputStream)) {
/* 2877 */           batchedStatement.setBinaryStream(batchedParamIndex++, (InputStream)value, (int)paramArg[j].bindLength);
/*      */         }
/*      */         else
/*      */         {
/* 2881 */           batchedStatement.setCharacterStream(batchedParamIndex++, (Reader)value, (int)paramArg[j].bindLength);
/*      */         }
/*      */         
/*      */       }
/*      */       else
/*      */       {
/* 2887 */         switch (paramArg[j].bufferType)
/*      */         {
/*      */         case 1: 
/* 2890 */           batchedStatement.setByte(batchedParamIndex++, paramArg[j].byteBinding);
/*      */           
/* 2892 */           break;
/*      */         case 2: 
/* 2894 */           batchedStatement.setShort(batchedParamIndex++, paramArg[j].shortBinding);
/*      */           
/* 2896 */           break;
/*      */         case 3: 
/* 2898 */           batchedStatement.setInt(batchedParamIndex++, paramArg[j].intBinding);
/*      */           
/* 2900 */           break;
/*      */         case 8: 
/* 2902 */           batchedStatement.setLong(batchedParamIndex++, paramArg[j].longBinding);
/*      */           
/* 2904 */           break;
/*      */         case 4: 
/* 2906 */           batchedStatement.setFloat(batchedParamIndex++, paramArg[j].floatBinding);
/*      */           
/* 2908 */           break;
/*      */         case 5: 
/* 2910 */           batchedStatement.setDouble(batchedParamIndex++, paramArg[j].doubleBinding);
/*      */           
/* 2912 */           break;
/*      */         case 11: 
/* 2914 */           batchedStatement.setTime(batchedParamIndex++, (Time)paramArg[j].value);
/*      */           
/* 2916 */           break;
/*      */         case 10: 
/* 2918 */           batchedStatement.setDate(batchedParamIndex++, (java.sql.Date)paramArg[j].value);
/*      */           
/* 2920 */           break;
/*      */         case 7: 
/*      */         case 12: 
/* 2923 */           batchedStatement.setTimestamp(batchedParamIndex++, (Timestamp)paramArg[j].value);
/*      */           
/* 2925 */           break;
/*      */         case 0: 
/*      */         case 15: 
/*      */         case 246: 
/*      */         case 253: 
/*      */         case 254: 
/* 2931 */           Object value = paramArg[j].value;
/*      */           
/* 2933 */           if ((value instanceof byte[])) {
/* 2934 */             batchedStatement.setBytes(batchedParamIndex, (byte[])value);
/*      */           }
/*      */           else {
/* 2937 */             batchedStatement.setString(batchedParamIndex, (String)value);
/*      */           }
/*      */           
/*      */ 
/*      */ 
/*      */ 
/* 2943 */           if ((batchedStatement instanceof ServerPreparedStatement)) {
/* 2944 */             BindValue asBound = ((ServerPreparedStatement)batchedStatement).getBinding(batchedParamIndex, false);
/*      */             
/*      */ 
/*      */ 
/* 2948 */             asBound.bufferType = paramArg[j].bufferType;
/*      */           }
/*      */           
/* 2951 */           batchedParamIndex++;
/*      */           
/* 2953 */           break;
/*      */         default: 
/* 2955 */           throw new IllegalArgumentException("Unknown type when re-binding parameter into batched statement for parameter index " + batchedParamIndex);
/*      */         }
/*      */         
/*      */       }
/*      */     }
/*      */     
/*      */ 
/*      */ 
/* 2963 */     return batchedParamIndex;
/*      */   }
/*      */   
/*      */   protected boolean containsOnDuplicateKeyUpdateInSQL() {
/* 2967 */     return this.hasOnDuplicateKeyUpdate;
/*      */   }
/*      */   
/*      */   protected PreparedStatement prepareBatchedInsertSQL(MySQLConnection localConn, int numBatches) throws SQLException {
/*      */     try {
/* 2972 */       PreparedStatement pstmt = new ServerPreparedStatement(localConn, this.parseInfo.getSqlForBatch(numBatches), this.currentCatalog, this.resultSetConcurrency, this.resultSetType);
/* 2973 */       pstmt.setRetrieveGeneratedKeys(this.retrieveGeneratedKeys);
/*      */       
/* 2975 */       return pstmt;
/*      */     } catch (UnsupportedEncodingException e) {
/* 2977 */       SQLException sqlEx = SQLError.createSQLException("Unable to prepare batch statement", "S1000", getExceptionInterceptor());
/* 2978 */       sqlEx.initCause(e);
/*      */       
/* 2980 */       throw sqlEx;
/*      */     }
/*      */   }
/*      */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\com\mysql\jdbc\ServerPreparedStatement.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */
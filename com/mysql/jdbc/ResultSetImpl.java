/*      */ package com.mysql.jdbc;
/*      */ 
/*      */ import com.mysql.jdbc.profiler.ProfilerEvent;
/*      */ import com.mysql.jdbc.profiler.ProfilerEventHandler;
/*      */ import java.io.ByteArrayInputStream;
/*      */ import java.io.IOException;
/*      */ import java.io.InputStream;
/*      */ import java.io.ObjectInputStream;
/*      */ import java.io.Reader;
/*      */ import java.io.StringReader;
/*      */ import java.io.UnsupportedEncodingException;
/*      */ import java.lang.reflect.Constructor;
/*      */ import java.math.BigDecimal;
/*      */ import java.math.BigInteger;
/*      */ import java.net.MalformedURLException;
/*      */ import java.net.URL;
/*      */ import java.sql.Array;
/*      */ import java.sql.Date;
/*      */ import java.sql.Ref;
/*      */ import java.sql.SQLException;
/*      */ import java.sql.SQLWarning;
/*      */ import java.sql.Statement;
/*      */ import java.sql.Time;
/*      */ import java.sql.Timestamp;
/*      */ import java.util.Calendar;
/*      */ import java.util.GregorianCalendar;
/*      */ import java.util.HashMap;
/*      */ import java.util.Locale;
/*      */ import java.util.Map;
/*      */ import java.util.StringTokenizer;
/*      */ import java.util.TimeZone;
/*      */ import java.util.TreeMap;
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
/*      */ public class ResultSetImpl
/*      */   implements ResultSetInternalMethods
/*      */ {
/*      */   private static final Constructor JDBC_4_RS_4_ARG_CTOR;
/*      */   private static final Constructor JDBC_4_RS_6_ARG_CTOR;
/*      */   private static final Constructor JDBC_4_UPD_RS_6_ARG_CTOR;
/*      */   protected static final double MIN_DIFF_PREC;
/*      */   protected static final double MAX_DIFF_PREC;
/*      */   protected static int resultCounter;
/*      */   
/*      */   protected static BigInteger convertLongToUlong(long longVal)
/*      */   {
/*  177 */     byte[] asBytes = new byte[8];
/*  178 */     asBytes[7] = ((byte)(int)(longVal & 0xFF));
/*  179 */     asBytes[6] = ((byte)(int)(longVal >>> 8));
/*  180 */     asBytes[5] = ((byte)(int)(longVal >>> 16));
/*  181 */     asBytes[4] = ((byte)(int)(longVal >>> 24));
/*  182 */     asBytes[3] = ((byte)(int)(longVal >>> 32));
/*  183 */     asBytes[2] = ((byte)(int)(longVal >>> 40));
/*  184 */     asBytes[1] = ((byte)(int)(longVal >>> 48));
/*  185 */     asBytes[0] = ((byte)(int)(longVal >>> 56));
/*      */     
/*  187 */     return new BigInteger(1, asBytes);
/*      */   }
/*      */   
/*      */ 
/*  191 */   protected String catalog = null;
/*      */   
/*      */ 
/*  194 */   protected Map columnLabelToIndex = null;
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*  200 */   protected Map columnToIndexCache = null;
/*      */   
/*      */ 
/*  203 */   protected boolean[] columnUsed = null;
/*      */   
/*      */ 
/*      */   protected MySQLConnection connection;
/*      */   
/*      */ 
/*  209 */   protected long connectionId = 0L;
/*      */   
/*      */ 
/*  212 */   protected int currentRow = -1;
/*      */   
/*      */ 
/*      */   TimeZone defaultTimeZone;
/*      */   
/*  217 */   protected boolean doingUpdates = false;
/*      */   
/*  219 */   protected ProfilerEventHandler eventSink = null;
/*      */   
/*  221 */   Calendar fastDateCal = null;
/*      */   
/*      */ 
/*  224 */   protected int fetchDirection = 1000;
/*      */   
/*      */ 
/*  227 */   protected int fetchSize = 0;
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   protected Field[] fields;
/*      */   
/*      */ 
/*      */ 
/*      */   protected char firstCharOfQuery;
/*      */   
/*      */ 
/*      */ 
/*  240 */   protected Map fullColumnNameToIndex = null;
/*      */   
/*  242 */   protected Map columnNameToIndex = null;
/*      */   
/*  244 */   protected boolean hasBuiltIndexMapping = false;
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*  250 */   protected boolean isBinaryEncoded = false;
/*      */   
/*      */ 
/*  253 */   protected boolean isClosed = false;
/*      */   
/*  255 */   protected ResultSetInternalMethods nextResultSet = null;
/*      */   
/*      */ 
/*  258 */   protected boolean onInsertRow = false;
/*      */   
/*      */ 
/*      */ 
/*      */   protected StatementImpl owningStatement;
/*      */   
/*      */ 
/*      */ 
/*      */   protected Throwable pointOfOrigin;
/*      */   
/*      */ 
/*  269 */   protected boolean profileSql = false;
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*  275 */   protected boolean reallyResult = false;
/*      */   
/*      */ 
/*      */   protected int resultId;
/*      */   
/*      */ 
/*  281 */   protected int resultSetConcurrency = 0;
/*      */   
/*      */ 
/*  284 */   protected int resultSetType = 0;
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   protected RowData rowData;
/*      */   
/*      */ 
/*      */ 
/*  293 */   protected String serverInfo = null;
/*      */   
/*      */ 
/*      */   PreparedStatement statementUsedForFetchingRows;
/*      */   
/*  298 */   protected ResultSetRow thisRow = null;
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   protected long updateCount;
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*  312 */   protected long updateId = -1L;
/*      */   
/*  314 */   private boolean useStrictFloatingPoint = false;
/*      */   
/*  316 */   protected boolean useUsageAdvisor = false;
/*      */   
/*      */ 
/*  319 */   protected SQLWarning warningChain = null;
/*      */   
/*      */ 
/*  322 */   protected boolean wasNullFlag = false;
/*      */   
/*      */   protected Statement wrapperStatement;
/*      */   
/*      */   protected boolean retainOwningStatement;
/*      */   
/*  328 */   protected Calendar gmtCalendar = null;
/*      */   
/*  330 */   protected boolean useFastDateParsing = false;
/*      */   
/*  332 */   private boolean padCharsWithSpace = false;
/*      */   
/*      */   private boolean jdbcCompliantTruncationForReads;
/*      */   
/*  336 */   private boolean useFastIntParsing = true;
/*      */   private boolean useColumnNamesInFindColumn;
/*      */   private ExceptionInterceptor exceptionInterceptor;
/*      */   protected static final char[] EMPTY_SPACE;
/*      */   
/*      */   static
/*      */   {
/*  123 */     if (Util.isJdbc4()) {
/*      */       try {
/*  125 */         JDBC_4_RS_4_ARG_CTOR = Class.forName("com.mysql.jdbc.JDBC4ResultSet").getConstructor(new Class[] { Long.TYPE, Long.TYPE, MySQLConnection.class, StatementImpl.class });
/*      */         
/*      */ 
/*      */ 
/*      */ 
/*  130 */         JDBC_4_RS_6_ARG_CTOR = Class.forName("com.mysql.jdbc.JDBC4ResultSet").getConstructor(new Class[] { String.class, Field[].class, RowData.class, MySQLConnection.class, StatementImpl.class });
/*      */         
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*  136 */         JDBC_4_UPD_RS_6_ARG_CTOR = Class.forName("com.mysql.jdbc.JDBC4UpdatableResultSet").getConstructor(new Class[] { String.class, Field[].class, RowData.class, MySQLConnection.class, StatementImpl.class });
/*      */ 
/*      */ 
/*      */       }
/*      */       catch (SecurityException e)
/*      */       {
/*      */ 
/*      */ 
/*  144 */         throw new RuntimeException(e);
/*      */       } catch (NoSuchMethodException e) {
/*  146 */         throw new RuntimeException(e);
/*      */       } catch (ClassNotFoundException e) {
/*  148 */         throw new RuntimeException(e);
/*      */       }
/*      */     } else {
/*  151 */       JDBC_4_RS_4_ARG_CTOR = null;
/*  152 */       JDBC_4_RS_6_ARG_CTOR = null;
/*  153 */       JDBC_4_UPD_RS_6_ARG_CTOR = null;
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*  160 */     MIN_DIFF_PREC = Float.parseFloat(Float.toString(Float.MIN_VALUE)) - Double.parseDouble(Float.toString(Float.MIN_VALUE));
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*  166 */     MAX_DIFF_PREC = Float.parseFloat(Float.toString(Float.MAX_VALUE)) - Double.parseDouble(Float.toString(Float.MAX_VALUE));
/*      */     
/*      */ 
/*      */ 
/*  170 */     resultCounter = 1;
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
/*  341 */     EMPTY_SPACE = new char['ÿ'];
/*      */     
/*      */ 
/*  344 */     for (int i = 0; i < EMPTY_SPACE.length; i++) {
/*  345 */       EMPTY_SPACE[i] = ' ';
/*      */     }
/*      */   }
/*      */   
/*      */   protected static ResultSetImpl getInstance(long updateCount, long updateID, MySQLConnection conn, StatementImpl creatorStmt) throws SQLException
/*      */   {
/*  351 */     if (!Util.isJdbc4()) {
/*  352 */       return new ResultSetImpl(updateCount, updateID, conn, creatorStmt);
/*      */     }
/*      */     
/*  355 */     return (ResultSetImpl)Util.handleNewInstance(JDBC_4_RS_4_ARG_CTOR, new Object[] { Constants.longValueOf(updateCount), Constants.longValueOf(updateID), conn, creatorStmt }, conn.getExceptionInterceptor());
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
/*      */   protected static ResultSetImpl getInstance(String catalog, Field[] fields, RowData tuples, MySQLConnection conn, StatementImpl creatorStmt, boolean isUpdatable)
/*      */     throws SQLException
/*      */   {
/*  371 */     if (!Util.isJdbc4()) {
/*  372 */       if (!isUpdatable) {
/*  373 */         return new ResultSetImpl(catalog, fields, tuples, conn, creatorStmt);
/*      */       }
/*      */       
/*  376 */       return new UpdatableResultSet(catalog, fields, tuples, conn, creatorStmt);
/*      */     }
/*      */     
/*      */ 
/*  380 */     if (!isUpdatable) {
/*  381 */       return (ResultSetImpl)Util.handleNewInstance(JDBC_4_RS_6_ARG_CTOR, new Object[] { catalog, fields, tuples, conn, creatorStmt }, conn.getExceptionInterceptor());
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*  386 */     return (ResultSetImpl)Util.handleNewInstance(JDBC_4_UPD_RS_6_ARG_CTOR, new Object[] { catalog, fields, tuples, conn, creatorStmt }, conn.getExceptionInterceptor());
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
/*      */   public ResultSetImpl(long updateCount, long updateID, MySQLConnection conn, StatementImpl creatorStmt)
/*      */   {
/*  404 */     this.updateCount = updateCount;
/*  405 */     this.updateId = updateID;
/*  406 */     this.reallyResult = false;
/*  407 */     this.fields = new Field[0];
/*      */     
/*  409 */     this.connection = conn;
/*  410 */     this.owningStatement = creatorStmt;
/*      */     
/*  412 */     this.exceptionInterceptor = this.connection.getExceptionInterceptor();
/*      */     
/*  414 */     this.retainOwningStatement = false;
/*      */     
/*  416 */     if (this.connection != null) {
/*  417 */       this.retainOwningStatement = this.connection.getRetainStatementAfterResultSetClose();
/*      */       
/*      */ 
/*  420 */       this.connectionId = this.connection.getId();
/*  421 */       this.serverTimeZoneTz = this.connection.getServerTimezoneTZ();
/*  422 */       this.padCharsWithSpace = this.connection.getPadCharsWithSpace();
/*      */     }
/*      */     
/*  425 */     this.useLegacyDatetimeCode = this.connection.getUseLegacyDatetimeCode();
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
/*      */   public ResultSetImpl(String catalog, Field[] fields, RowData tuples, MySQLConnection conn, StatementImpl creatorStmt)
/*      */     throws SQLException
/*      */   {
/*  447 */     this.connection = conn;
/*      */     
/*  449 */     this.retainOwningStatement = false;
/*      */     
/*  451 */     if (this.connection != null) {
/*  452 */       this.useStrictFloatingPoint = this.connection.getStrictFloatingPoint();
/*      */       
/*  454 */       setDefaultTimeZone(this.connection.getDefaultTimeZone());
/*  455 */       this.connectionId = this.connection.getId();
/*  456 */       this.useFastDateParsing = this.connection.getUseFastDateParsing();
/*  457 */       this.profileSql = this.connection.getProfileSql();
/*  458 */       this.retainOwningStatement = this.connection.getRetainStatementAfterResultSetClose();
/*      */       
/*  460 */       this.jdbcCompliantTruncationForReads = this.connection.getJdbcCompliantTruncationForReads();
/*  461 */       this.useFastIntParsing = this.connection.getUseFastIntParsing();
/*  462 */       this.serverTimeZoneTz = this.connection.getServerTimezoneTZ();
/*  463 */       this.padCharsWithSpace = this.connection.getPadCharsWithSpace();
/*      */     }
/*      */     
/*  466 */     this.owningStatement = creatorStmt;
/*      */     
/*  468 */     this.catalog = catalog;
/*      */     
/*  470 */     this.fields = fields;
/*  471 */     this.rowData = tuples;
/*  472 */     this.updateCount = this.rowData.size();
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*  479 */     this.reallyResult = true;
/*      */     
/*      */ 
/*  482 */     if (this.rowData.size() > 0) {
/*  483 */       if ((this.updateCount == 1L) && 
/*  484 */         (this.thisRow == null)) {
/*  485 */         this.rowData.close();
/*  486 */         this.updateCount = -1L;
/*      */       }
/*      */     }
/*      */     else {
/*  490 */       this.thisRow = null;
/*      */     }
/*      */     
/*  493 */     this.rowData.setOwner(this);
/*      */     
/*  495 */     if (this.fields != null) {
/*  496 */       initializeWithMetadata();
/*      */     }
/*  498 */     this.useLegacyDatetimeCode = this.connection.getUseLegacyDatetimeCode();
/*      */     
/*  500 */     this.useColumnNamesInFindColumn = this.connection.getUseColumnNamesInFindColumn();
/*      */     
/*  502 */     setRowPositionValidity();
/*      */   }
/*      */   
/*      */   public void initializeWithMetadata() throws SQLException {
/*  506 */     this.rowData.setMetadata(this.fields);
/*      */     
/*  508 */     this.columnToIndexCache = new HashMap();
/*      */     
/*  510 */     if ((this.profileSql) || (this.connection.getUseUsageAdvisor())) {
/*  511 */       this.columnUsed = new boolean[this.fields.length];
/*  512 */       this.pointOfOrigin = new Throwable();
/*  513 */       this.resultId = (resultCounter++);
/*  514 */       this.useUsageAdvisor = this.connection.getUseUsageAdvisor();
/*  515 */       this.eventSink = ProfilerEventHandlerFactory.getInstance(this.connection);
/*      */     }
/*      */     
/*  518 */     if (this.connection.getGatherPerformanceMetrics()) {
/*  519 */       this.connection.incrementNumberOfResultSetsCreated();
/*      */       
/*  521 */       Map tableNamesMap = new HashMap();
/*      */       
/*  523 */       for (int i = 0; i < this.fields.length; i++) {
/*  524 */         Field f = this.fields[i];
/*      */         
/*  526 */         String tableName = f.getOriginalTableName();
/*      */         
/*  528 */         if (tableName == null) {
/*  529 */           tableName = f.getTableName();
/*      */         }
/*      */         
/*  532 */         if (tableName != null) {
/*  533 */           if (this.connection.lowerCaseTableNames()) {
/*  534 */             tableName = tableName.toLowerCase();
/*      */           }
/*      */           
/*      */ 
/*  538 */           tableNamesMap.put(tableName, null);
/*      */         }
/*      */       }
/*      */       
/*  542 */       this.connection.reportNumberOfTablesAccessed(tableNamesMap.size());
/*      */     }
/*      */   }
/*      */   
/*      */   private synchronized void createCalendarIfNeeded() {
/*  547 */     if (this.fastDateCal == null) {
/*  548 */       this.fastDateCal = new GregorianCalendar(Locale.US);
/*  549 */       this.fastDateCal.setTimeZone(getDefaultTimeZone());
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
/*      */ 
/*      */ 
/*      */ 
/*      */   public boolean absolute(int row)
/*      */     throws SQLException
/*      */   {
/*  592 */     checkClosed();
/*      */     
/*      */     boolean b;
/*      */     boolean b;
/*  596 */     if (this.rowData.size() == 0) {
/*  597 */       b = false;
/*      */     } else {
/*  599 */       if (row == 0) {
/*  600 */         throw SQLError.createSQLException(Messages.getString("ResultSet.Cannot_absolute_position_to_row_0_110"), "S1009", getExceptionInterceptor());
/*      */       }
/*      */       
/*      */ 
/*      */ 
/*      */ 
/*  606 */       if (this.onInsertRow) {
/*  607 */         this.onInsertRow = false;
/*      */       }
/*      */       
/*  610 */       if (this.doingUpdates) {
/*  611 */         this.doingUpdates = false;
/*      */       }
/*      */       
/*  614 */       if (this.thisRow != null) {
/*  615 */         this.thisRow.closeOpenStreams();
/*      */       }
/*      */       boolean b;
/*  618 */       if (row == 1) {
/*  619 */         b = first(); } else { boolean b;
/*  620 */         if (row == -1) {
/*  621 */           b = last(); } else { boolean b;
/*  622 */           if (row > this.rowData.size()) {
/*  623 */             afterLast();
/*  624 */             b = false;
/*      */           } else { boolean b;
/*  626 */             if (row < 0)
/*      */             {
/*  628 */               int newRowPosition = this.rowData.size() + row + 1;
/*      */               boolean b;
/*  630 */               if (newRowPosition <= 0) {
/*  631 */                 beforeFirst();
/*  632 */                 b = false;
/*      */               } else {
/*  634 */                 b = absolute(newRowPosition);
/*      */               }
/*      */             } else {
/*  637 */               row--;
/*  638 */               this.rowData.setCurrentRow(row);
/*  639 */               this.thisRow = this.rowData.getAt(row);
/*  640 */               b = true;
/*      */             }
/*      */           }
/*      */         }
/*      */       } }
/*  645 */     setRowPositionValidity();
/*      */     
/*  647 */     return b;
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
/*      */   public void afterLast()
/*      */     throws SQLException
/*      */   {
/*  663 */     checkClosed();
/*      */     
/*  665 */     if (this.onInsertRow) {
/*  666 */       this.onInsertRow = false;
/*      */     }
/*      */     
/*  669 */     if (this.doingUpdates) {
/*  670 */       this.doingUpdates = false;
/*      */     }
/*      */     
/*  673 */     if (this.thisRow != null) {
/*  674 */       this.thisRow.closeOpenStreams();
/*      */     }
/*      */     
/*  677 */     if (this.rowData.size() != 0) {
/*  678 */       this.rowData.afterLast();
/*  679 */       this.thisRow = null;
/*      */     }
/*      */     
/*  682 */     setRowPositionValidity();
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
/*      */   public void beforeFirst()
/*      */     throws SQLException
/*      */   {
/*  698 */     checkClosed();
/*      */     
/*  700 */     if (this.onInsertRow) {
/*  701 */       this.onInsertRow = false;
/*      */     }
/*      */     
/*  704 */     if (this.doingUpdates) {
/*  705 */       this.doingUpdates = false;
/*      */     }
/*      */     
/*  708 */     if (this.rowData.size() == 0) {
/*  709 */       return;
/*      */     }
/*      */     
/*  712 */     if (this.thisRow != null) {
/*  713 */       this.thisRow.closeOpenStreams();
/*      */     }
/*      */     
/*  716 */     this.rowData.beforeFirst();
/*  717 */     this.thisRow = null;
/*      */     
/*  719 */     setRowPositionValidity();
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public void buildIndexMapping()
/*      */     throws SQLException
/*      */   {
/*  730 */     int numFields = this.fields.length;
/*  731 */     this.columnLabelToIndex = new TreeMap(String.CASE_INSENSITIVE_ORDER);
/*  732 */     this.fullColumnNameToIndex = new TreeMap(String.CASE_INSENSITIVE_ORDER);
/*  733 */     this.columnNameToIndex = new TreeMap(String.CASE_INSENSITIVE_ORDER);
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
/*  747 */     for (int i = numFields - 1; i >= 0; i--) {
/*  748 */       Integer index = Constants.integerValueOf(i);
/*  749 */       String columnName = this.fields[i].getOriginalName();
/*  750 */       String columnLabel = this.fields[i].getName();
/*  751 */       String fullColumnName = this.fields[i].getFullName();
/*      */       
/*  753 */       if (columnLabel != null) {
/*  754 */         this.columnLabelToIndex.put(columnLabel, index);
/*      */       }
/*      */       
/*  757 */       if (fullColumnName != null) {
/*  758 */         this.fullColumnNameToIndex.put(fullColumnName, index);
/*      */       }
/*      */       
/*  761 */       if (columnName != null) {
/*  762 */         this.columnNameToIndex.put(columnName, index);
/*      */       }
/*      */     }
/*      */     
/*      */ 
/*  767 */     this.hasBuiltIndexMapping = true;
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
/*      */   public void cancelRowUpdates()
/*      */     throws SQLException
/*      */   {
/*  783 */     throw new NotUpdatable();
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   protected final void checkClosed()
/*      */     throws SQLException
/*      */   {
/*  793 */     if (this.isClosed) {
/*  794 */       throw SQLError.createSQLException(Messages.getString("ResultSet.Operation_not_allowed_after_ResultSet_closed_144"), "S1000", getExceptionInterceptor());
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
/*      */   protected final void checkColumnBounds(int columnIndex)
/*      */     throws SQLException
/*      */   {
/*  811 */     if (columnIndex < 1) {
/*  812 */       throw SQLError.createSQLException(Messages.getString("ResultSet.Column_Index_out_of_range_low", new Object[] { Constants.integerValueOf(columnIndex), Constants.integerValueOf(this.fields.length) }), "S1009", getExceptionInterceptor());
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*  817 */     if (columnIndex > this.fields.length) {
/*  818 */       throw SQLError.createSQLException(Messages.getString("ResultSet.Column_Index_out_of_range_high", new Object[] { Constants.integerValueOf(columnIndex), Constants.integerValueOf(this.fields.length) }), "S1009", getExceptionInterceptor());
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*  825 */     if ((this.profileSql) || (this.useUsageAdvisor)) {
/*  826 */       this.columnUsed[(columnIndex - 1)] = true;
/*      */     }
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   protected void checkRowPos()
/*      */     throws SQLException
/*      */   {
/*  838 */     checkClosed();
/*      */     
/*  840 */     if (!this.onValidRow) {
/*  841 */       throw SQLError.createSQLException(this.invalidRowReason, "S1000", getExceptionInterceptor());
/*      */     }
/*      */   }
/*      */   
/*      */ 
/*  846 */   private boolean onValidRow = false;
/*  847 */   private String invalidRowReason = null;
/*      */   protected boolean useLegacyDatetimeCode;
/*      */   private TimeZone serverTimeZoneTz;
/*      */   
/*      */   private void setRowPositionValidity() throws SQLException {
/*  852 */     if ((!this.rowData.isDynamic()) && (this.rowData.size() == 0)) {
/*  853 */       this.invalidRowReason = Messages.getString("ResultSet.Illegal_operation_on_empty_result_set");
/*      */       
/*  855 */       this.onValidRow = false;
/*  856 */     } else if (this.rowData.isBeforeFirst()) {
/*  857 */       this.invalidRowReason = Messages.getString("ResultSet.Before_start_of_result_set_146");
/*      */       
/*  859 */       this.onValidRow = false;
/*  860 */     } else if (this.rowData.isAfterLast()) {
/*  861 */       this.invalidRowReason = Messages.getString("ResultSet.After_end_of_result_set_148");
/*      */       
/*  863 */       this.onValidRow = false;
/*      */     } else {
/*  865 */       this.onValidRow = true;
/*  866 */       this.invalidRowReason = null;
/*      */     }
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   public void clearNextResult()
/*      */   {
/*  875 */     this.nextResultSet = null;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public void clearWarnings()
/*      */     throws SQLException
/*      */   {
/*  886 */     this.warningChain = null;
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
/*      */   public void close()
/*      */     throws SQLException
/*      */   {
/*  907 */     realClose(true);
/*      */   }
/*      */   
/*      */ 
/*      */   private int convertToZeroWithEmptyCheck()
/*      */     throws SQLException
/*      */   {
/*  914 */     if (this.connection.getEmptyStringsConvertToZero()) {
/*  915 */       return 0;
/*      */     }
/*      */     
/*  918 */     throw SQLError.createSQLException("Can't convert empty string ('') to numeric", "22018", getExceptionInterceptor());
/*      */   }
/*      */   
/*      */ 
/*      */   private String convertToZeroLiteralStringWithEmptyCheck()
/*      */     throws SQLException
/*      */   {
/*  925 */     if (this.connection.getEmptyStringsConvertToZero()) {
/*  926 */       return "0";
/*      */     }
/*      */     
/*  929 */     throw SQLError.createSQLException("Can't convert empty string ('') to numeric", "22018", getExceptionInterceptor());
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */   public ResultSetInternalMethods copy()
/*      */     throws SQLException
/*      */   {
/*  937 */     ResultSetInternalMethods rs = getInstance(this.catalog, this.fields, this.rowData, this.connection, this.owningStatement, false);
/*      */     
/*      */ 
/*  940 */     return rs;
/*      */   }
/*      */   
/*      */   public void redefineFieldsForDBMD(Field[] f) {
/*  944 */     this.fields = f;
/*      */     
/*  946 */     for (int i = 0; i < this.fields.length; i++) {
/*  947 */       this.fields[i].setUseOldNameMetadata(true);
/*  948 */       this.fields[i].setConnection(this.connection);
/*      */     }
/*      */   }
/*      */   
/*      */   public void populateCachedMetaData(CachedResultSetMetaData cachedMetaData) throws SQLException
/*      */   {
/*  954 */     cachedMetaData.fields = this.fields;
/*  955 */     cachedMetaData.columnNameToIndex = this.columnLabelToIndex;
/*  956 */     cachedMetaData.fullColumnNameToIndex = this.fullColumnNameToIndex;
/*  957 */     cachedMetaData.metadata = getMetaData();
/*      */   }
/*      */   
/*      */   public void initializeFromCachedMetaData(CachedResultSetMetaData cachedMetaData) {
/*  961 */     this.fields = cachedMetaData.fields;
/*  962 */     this.columnLabelToIndex = cachedMetaData.columnNameToIndex;
/*  963 */     this.fullColumnNameToIndex = cachedMetaData.fullColumnNameToIndex;
/*  964 */     this.hasBuiltIndexMapping = true;
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
/*      */   public void deleteRow()
/*      */     throws SQLException
/*      */   {
/*  979 */     throw new NotUpdatable();
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   private String extractStringFromNativeColumn(int columnIndex, int mysqlType)
/*      */     throws SQLException
/*      */   {
/*  991 */     int columnIndexMinusOne = columnIndex - 1;
/*      */     
/*  993 */     this.wasNullFlag = false;
/*      */     
/*  995 */     if (this.thisRow.isNull(columnIndexMinusOne)) {
/*  996 */       this.wasNullFlag = true;
/*      */       
/*  998 */       return null;
/*      */     }
/*      */     
/* 1001 */     this.wasNullFlag = false;
/*      */     
/* 1003 */     String encoding = this.fields[columnIndexMinusOne].getCharacterSet();
/*      */     
/*      */ 
/* 1006 */     return this.thisRow.getString(columnIndex - 1, encoding, this.connection);
/*      */   }
/*      */   
/*      */   protected synchronized Date fastDateCreate(Calendar cal, int year, int month, int day)
/*      */   {
/* 1011 */     if (this.useLegacyDatetimeCode) {
/* 1012 */       return TimeUtil.fastDateCreate(year, month, day, cal);
/*      */     }
/*      */     
/* 1015 */     if (cal == null) {
/* 1016 */       createCalendarIfNeeded();
/* 1017 */       cal = this.fastDateCal;
/*      */     }
/*      */     
/* 1020 */     boolean useGmtMillis = this.connection.getUseGmtMillisForDatetimes();
/*      */     
/* 1022 */     return TimeUtil.fastDateCreate(useGmtMillis, useGmtMillis ? getGmtCalendar() : cal, cal, year, month, day);
/*      */   }
/*      */   
/*      */ 
/*      */   protected synchronized Time fastTimeCreate(Calendar cal, int hour, int minute, int second)
/*      */     throws SQLException
/*      */   {
/* 1029 */     if (!this.useLegacyDatetimeCode) {
/* 1030 */       return TimeUtil.fastTimeCreate(hour, minute, second, cal, getExceptionInterceptor());
/*      */     }
/*      */     
/* 1033 */     if (cal == null) {
/* 1034 */       createCalendarIfNeeded();
/* 1035 */       cal = this.fastDateCal;
/*      */     }
/*      */     
/* 1038 */     return TimeUtil.fastTimeCreate(cal, hour, minute, second, getExceptionInterceptor());
/*      */   }
/*      */   
/*      */ 
/*      */   protected synchronized Timestamp fastTimestampCreate(Calendar cal, int year, int month, int day, int hour, int minute, int seconds, int secondsPart)
/*      */   {
/* 1044 */     if (!this.useLegacyDatetimeCode) {
/* 1045 */       return TimeUtil.fastTimestampCreate(cal.getTimeZone(), year, month, day, hour, minute, seconds, secondsPart);
/*      */     }
/*      */     
/*      */ 
/* 1049 */     if (cal == null) {
/* 1050 */       createCalendarIfNeeded();
/* 1051 */       cal = this.fastDateCal;
/*      */     }
/*      */     
/* 1054 */     boolean useGmtMillis = this.connection.getUseGmtMillisForDatetimes();
/*      */     
/* 1056 */     return TimeUtil.fastTimestampCreate(useGmtMillis, useGmtMillis ? getGmtCalendar() : null, cal, year, month, day, hour, minute, seconds, secondsPart);
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
/*      */   public synchronized int findColumn(String columnName)
/*      */     throws SQLException
/*      */   {
/* 1105 */     checkClosed();
/*      */     
/* 1107 */     if (!this.hasBuiltIndexMapping) {
/* 1108 */       buildIndexMapping();
/*      */     }
/*      */     
/* 1111 */     Integer index = (Integer)this.columnToIndexCache.get(columnName);
/*      */     
/* 1113 */     if (index != null) {
/* 1114 */       return index.intValue() + 1;
/*      */     }
/*      */     
/* 1117 */     index = (Integer)this.columnLabelToIndex.get(columnName);
/*      */     
/* 1119 */     if ((index == null) && (this.useColumnNamesInFindColumn)) {
/* 1120 */       index = (Integer)this.columnNameToIndex.get(columnName);
/*      */     }
/*      */     
/* 1123 */     if (index == null) {
/* 1124 */       index = (Integer)this.fullColumnNameToIndex.get(columnName);
/*      */     }
/*      */     
/* 1127 */     if (index != null) {
/* 1128 */       this.columnToIndexCache.put(columnName, index);
/*      */       
/* 1130 */       return index.intValue() + 1;
/*      */     }
/*      */     
/*      */ 
/*      */ 
/* 1135 */     for (int i = 0; i < this.fields.length; i++) {
/* 1136 */       if (this.fields[i].getName().equalsIgnoreCase(columnName))
/* 1137 */         return i + 1;
/* 1138 */       if (this.fields[i].getFullName().equalsIgnoreCase(columnName))
/*      */       {
/* 1140 */         return i + 1;
/*      */       }
/*      */     }
/*      */     
/* 1144 */     throw SQLError.createSQLException(Messages.getString("ResultSet.Column____112") + columnName + Messages.getString("ResultSet.___not_found._113"), "S0022", getExceptionInterceptor());
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
/*      */   public boolean first()
/*      */     throws SQLException
/*      */   {
/* 1164 */     checkClosed();
/*      */     
/* 1166 */     boolean b = true;
/*      */     
/* 1168 */     if (this.rowData.isEmpty()) {
/* 1169 */       b = false;
/*      */     }
/*      */     else {
/* 1172 */       if (this.onInsertRow) {
/* 1173 */         this.onInsertRow = false;
/*      */       }
/*      */       
/* 1176 */       if (this.doingUpdates) {
/* 1177 */         this.doingUpdates = false;
/*      */       }
/*      */       
/* 1180 */       this.rowData.beforeFirst();
/* 1181 */       this.thisRow = this.rowData.next();
/*      */     }
/*      */     
/* 1184 */     setRowPositionValidity();
/*      */     
/* 1186 */     return b;
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
/*      */   public Array getArray(int i)
/*      */     throws SQLException
/*      */   {
/* 1203 */     checkColumnBounds(i);
/*      */     
/* 1205 */     throw SQLError.notImplemented();
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
/*      */   public Array getArray(String colName)
/*      */     throws SQLException
/*      */   {
/* 1222 */     return getArray(findColumn(colName));
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
/*      */   public InputStream getAsciiStream(int columnIndex)
/*      */     throws SQLException
/*      */   {
/* 1251 */     checkRowPos();
/*      */     
/* 1253 */     if (!this.isBinaryEncoded) {
/* 1254 */       return getBinaryStream(columnIndex);
/*      */     }
/*      */     
/* 1257 */     return getNativeBinaryStream(columnIndex);
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
/*      */   public InputStream getAsciiStream(String columnName)
/*      */     throws SQLException
/*      */   {
/* 1272 */     return getAsciiStream(findColumn(columnName));
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
/*      */   public BigDecimal getBigDecimal(int columnIndex)
/*      */     throws SQLException
/*      */   {
/* 1289 */     if (!this.isBinaryEncoded) {
/* 1290 */       String stringVal = getString(columnIndex);
/*      */       
/*      */ 
/* 1293 */       if (stringVal != null) {
/* 1294 */         if (stringVal.length() == 0)
/*      */         {
/* 1296 */           BigDecimal val = new BigDecimal(convertToZeroLiteralStringWithEmptyCheck());
/*      */           
/*      */ 
/* 1299 */           return val;
/*      */         }
/*      */         try
/*      */         {
/* 1303 */           return new BigDecimal(stringVal);
/*      */         }
/*      */         catch (NumberFormatException ex)
/*      */         {
/* 1307 */           throw SQLError.createSQLException(Messages.getString("ResultSet.Bad_format_for_BigDecimal", new Object[] { stringVal, Constants.integerValueOf(columnIndex) }), "S1009", getExceptionInterceptor());
/*      */         }
/*      */       }
/*      */       
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 1315 */       return null;
/*      */     }
/*      */     
/* 1318 */     return getNativeBigDecimal(columnIndex);
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
/*      */   /**
/*      */    * @deprecated
/*      */    */
/*      */   public BigDecimal getBigDecimal(int columnIndex, int scale)
/*      */     throws SQLException
/*      */   {
/* 1339 */     if (!this.isBinaryEncoded) {
/* 1340 */       String stringVal = getString(columnIndex);
/*      */       
/*      */ 
/* 1343 */       if (stringVal != null) {
/* 1344 */         if (stringVal.length() == 0) {
/* 1345 */           BigDecimal val = new BigDecimal(convertToZeroLiteralStringWithEmptyCheck());
/*      */           
/*      */           try
/*      */           {
/* 1349 */             return val.setScale(scale);
/*      */           } catch (ArithmeticException ex) {
/*      */             try {
/* 1352 */               return val.setScale(scale, 4);
/*      */             }
/*      */             catch (ArithmeticException arEx) {
/* 1355 */               throw SQLError.createSQLException(Messages.getString("ResultSet.Bad_format_for_BigDecimal", new Object[] { stringVal, new Integer(columnIndex) }), "S1009", getExceptionInterceptor());
/*      */             }
/*      */           }
/*      */         }
/*      */         
/*      */ 
/*      */ 
/*      */ 
/*      */         try
/*      */         {
/* 1365 */           val = new BigDecimal(stringVal);
/*      */         } catch (NumberFormatException ex) { BigDecimal val;
/* 1367 */           if (this.fields[(columnIndex - 1)].getMysqlType() == 16) {
/* 1368 */             long valueAsLong = getNumericRepresentationOfSQLBitType(columnIndex);
/*      */             
/* 1370 */             val = new BigDecimal(valueAsLong);
/*      */           } else {
/* 1372 */             throw SQLError.createSQLException(Messages.getString("ResultSet.Bad_format_for_BigDecimal", new Object[] { Constants.integerValueOf(columnIndex), stringVal }), "S1009", getExceptionInterceptor());
/*      */           }
/*      */         }
/*      */         
/*      */ 
/*      */ 
/*      */ 
/*      */         try
/*      */         {
/* 1381 */           return val.setScale(scale);
/*      */         } catch (ArithmeticException ex) {
/*      */           try { BigDecimal val;
/* 1384 */             return val.setScale(scale, 4);
/*      */           } catch (ArithmeticException arithEx) {
/* 1386 */             throw SQLError.createSQLException(Messages.getString("ResultSet.Bad_format_for_BigDecimal", new Object[] { Constants.integerValueOf(columnIndex), stringVal }), "S1009", getExceptionInterceptor());
/*      */           }
/*      */         }
/*      */       }
/*      */       
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 1395 */       return null;
/*      */     }
/*      */     
/* 1398 */     return getNativeBigDecimal(columnIndex, scale);
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
/*      */   public BigDecimal getBigDecimal(String columnName)
/*      */     throws SQLException
/*      */   {
/* 1414 */     return getBigDecimal(findColumn(columnName));
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
/*      */   /**
/*      */    * @deprecated
/*      */    */
/*      */   public BigDecimal getBigDecimal(String columnName, int scale)
/*      */     throws SQLException
/*      */   {
/* 1434 */     return getBigDecimal(findColumn(columnName), scale);
/*      */   }
/*      */   
/*      */ 
/*      */   private final BigDecimal getBigDecimalFromString(String stringVal, int columnIndex, int scale)
/*      */     throws SQLException
/*      */   {
/* 1441 */     if (stringVal != null) {
/* 1442 */       if (stringVal.length() == 0) {
/* 1443 */         BigDecimal bdVal = new BigDecimal(convertToZeroLiteralStringWithEmptyCheck());
/*      */         try
/*      */         {
/* 1446 */           return bdVal.setScale(scale);
/*      */         } catch (ArithmeticException ex) {
/*      */           try {
/* 1449 */             return bdVal.setScale(scale, 4);
/*      */           } catch (ArithmeticException arEx) {
/* 1451 */             throw new SQLException(Messages.getString("ResultSet.Bad_format_for_BigDecimal", new Object[] { stringVal, Constants.integerValueOf(columnIndex) }), "S1009");
/*      */           }
/*      */         }
/*      */       }
/*      */       
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       try
/*      */       {
/* 1462 */         return new BigDecimal(stringVal).setScale(scale);
/*      */       } catch (ArithmeticException ex) {
/*      */         try {
/* 1465 */           return new BigDecimal(stringVal).setScale(scale, 4);
/*      */         }
/*      */         catch (ArithmeticException arEx) {
/* 1468 */           throw new SQLException(Messages.getString("ResultSet.Bad_format_for_BigDecimal", new Object[] { stringVal, Constants.integerValueOf(columnIndex) }), "S1009");
/*      */         }
/*      */         
/*      */ 
/*      */       }
/*      */       catch (NumberFormatException ex)
/*      */       {
/*      */ 
/* 1476 */         if (this.fields[(columnIndex - 1)].getMysqlType() == 16) {
/* 1477 */           long valueAsLong = getNumericRepresentationOfSQLBitType(columnIndex);
/*      */           try
/*      */           {
/* 1480 */             return new BigDecimal(valueAsLong).setScale(scale);
/*      */           } catch (ArithmeticException arEx1) {
/*      */             try {
/* 1483 */               return new BigDecimal(valueAsLong).setScale(scale, 4);
/*      */             }
/*      */             catch (ArithmeticException arEx2) {
/* 1486 */               throw new SQLException(Messages.getString("ResultSet.Bad_format_for_BigDecimal", new Object[] { stringVal, Constants.integerValueOf(columnIndex) }), "S1009");
/*      */             }
/*      */           }
/*      */         }
/*      */         
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 1495 */         if ((this.fields[(columnIndex - 1)].getMysqlType() == 1) && (this.connection.getTinyInt1isBit()) && (this.fields[(columnIndex - 1)].getLength() == 1L))
/*      */         {
/* 1497 */           return new BigDecimal(stringVal.equalsIgnoreCase("true") ? 1 : 0).setScale(scale);
/*      */         }
/*      */         
/* 1500 */         throw new SQLException(Messages.getString("ResultSet.Bad_format_for_BigDecimal", new Object[] { stringVal, Constants.integerValueOf(columnIndex) }), "S1009");
/*      */       }
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 1508 */     return null;
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
/*      */   public InputStream getBinaryStream(int columnIndex)
/*      */     throws SQLException
/*      */   {
/* 1529 */     checkRowPos();
/*      */     
/* 1531 */     if (!this.isBinaryEncoded) {
/* 1532 */       checkColumnBounds(columnIndex);
/*      */       
/* 1534 */       int columnIndexMinusOne = columnIndex - 1;
/*      */       
/* 1536 */       if (this.thisRow.isNull(columnIndexMinusOne)) {
/* 1537 */         this.wasNullFlag = true;
/*      */         
/* 1539 */         return null;
/*      */       }
/*      */       
/* 1542 */       this.wasNullFlag = false;
/*      */       
/* 1544 */       return this.thisRow.getBinaryInputStream(columnIndexMinusOne);
/*      */     }
/*      */     
/* 1547 */     return getNativeBinaryStream(columnIndex);
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
/*      */   public InputStream getBinaryStream(String columnName)
/*      */     throws SQLException
/*      */   {
/* 1562 */     return getBinaryStream(findColumn(columnName));
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
/*      */   public java.sql.Blob getBlob(int columnIndex)
/*      */     throws SQLException
/*      */   {
/* 1577 */     if (!this.isBinaryEncoded) {
/* 1578 */       checkRowPos();
/*      */       
/* 1580 */       checkColumnBounds(columnIndex);
/*      */       
/* 1582 */       int columnIndexMinusOne = columnIndex - 1;
/*      */       
/* 1584 */       if (this.thisRow.isNull(columnIndexMinusOne)) {
/* 1585 */         this.wasNullFlag = true;
/*      */       } else {
/* 1587 */         this.wasNullFlag = false;
/*      */       }
/*      */       
/* 1590 */       if (this.wasNullFlag) {
/* 1591 */         return null;
/*      */       }
/*      */       
/* 1594 */       if (!this.connection.getEmulateLocators()) {
/* 1595 */         return new Blob(this.thisRow.getColumnValue(columnIndexMinusOne), getExceptionInterceptor());
/*      */       }
/*      */       
/* 1598 */       return new BlobFromLocator(this, columnIndex, getExceptionInterceptor());
/*      */     }
/*      */     
/* 1601 */     return getNativeBlob(columnIndex);
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
/*      */   public java.sql.Blob getBlob(String colName)
/*      */     throws SQLException
/*      */   {
/* 1616 */     return getBlob(findColumn(colName));
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
/*      */   public boolean getBoolean(int columnIndex)
/*      */     throws SQLException
/*      */   {
/* 1632 */     checkColumnBounds(columnIndex);
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 1639 */     int columnIndexMinusOne = columnIndex - 1;
/*      */     
/* 1641 */     Field field = this.fields[columnIndexMinusOne];
/*      */     
/* 1643 */     if (field.getMysqlType() == 16) {
/* 1644 */       return byteArrayToBoolean(columnIndexMinusOne);
/*      */     }
/*      */     
/* 1647 */     this.wasNullFlag = false;
/*      */     
/* 1649 */     int sqlType = field.getSQLType();
/*      */     long boolVal;
/* 1651 */     switch (sqlType) {
/*      */     case 16: 
/* 1653 */       if (field.getMysqlType() == -1) {
/* 1654 */         String stringVal = getString(columnIndex);
/*      */         
/* 1656 */         return getBooleanFromString(stringVal, columnIndex);
/*      */       }
/*      */       
/* 1659 */       boolVal = getLong(columnIndex, false);
/*      */       
/* 1661 */       return (boolVal == -1L) || (boolVal > 0L);
/*      */     case -7: 
/*      */     case -6: 
/*      */     case -5: 
/*      */     case 2: 
/*      */     case 3: 
/*      */     case 4: 
/*      */     case 5: 
/*      */     case 6: 
/*      */     case 7: 
/*      */     case 8: 
/* 1672 */       boolVal = getLong(columnIndex, false);
/*      */       
/* 1674 */       return (boolVal == -1L) || (boolVal > 0L);
/*      */     }
/* 1676 */     if (this.connection.getPedantic())
/*      */     {
/* 1678 */       switch (sqlType) {
/*      */       case -4: 
/*      */       case -3: 
/*      */       case -2: 
/*      */       case 70: 
/*      */       case 91: 
/*      */       case 92: 
/*      */       case 93: 
/*      */       case 2000: 
/*      */       case 2002: 
/*      */       case 2003: 
/*      */       case 2004: 
/*      */       case 2005: 
/*      */       case 2006: 
/* 1692 */         throw SQLError.createSQLException("Required type conversion not allowed", "22018", getExceptionInterceptor());
/*      */       }
/*      */       
/*      */     }
/*      */     
/* 1697 */     if ((sqlType == -2) || (sqlType == -3) || (sqlType == -4) || (sqlType == 2004))
/*      */     {
/*      */ 
/*      */ 
/* 1701 */       return byteArrayToBoolean(columnIndexMinusOne);
/*      */     }
/*      */     
/* 1704 */     if (this.useUsageAdvisor) {
/* 1705 */       issueConversionViaParsingWarning("getBoolean()", columnIndex, this.thisRow.getColumnValue(columnIndexMinusOne), this.fields[columnIndex], new int[] { 16, 5, 1, 2, 3, 8, 4 });
/*      */     }
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
/* 1717 */     String stringVal = getString(columnIndex);
/*      */     
/* 1719 */     return getBooleanFromString(stringVal, columnIndex);
/*      */   }
/*      */   
/*      */   private boolean byteArrayToBoolean(int columnIndexMinusOne) throws SQLException
/*      */   {
/* 1724 */     Object value = this.thisRow.getColumnValue(columnIndexMinusOne);
/*      */     
/* 1726 */     if (value == null) {
/* 1727 */       this.wasNullFlag = true;
/*      */       
/* 1729 */       return false;
/*      */     }
/*      */     
/* 1732 */     this.wasNullFlag = false;
/*      */     
/* 1734 */     if (((byte[])value).length == 0) {
/* 1735 */       return false;
/*      */     }
/*      */     
/* 1738 */     byte boolVal = ((byte[])(byte[])value)[0];
/*      */     
/* 1740 */     if (boolVal == 49)
/* 1741 */       return true;
/* 1742 */     if (boolVal == 48) {
/* 1743 */       return false;
/*      */     }
/*      */     
/* 1746 */     return (boolVal == -1) || (boolVal > 0);
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
/*      */   public boolean getBoolean(String columnName)
/*      */     throws SQLException
/*      */   {
/* 1761 */     return getBoolean(findColumn(columnName));
/*      */   }
/*      */   
/*      */   private final boolean getBooleanFromString(String stringVal, int columnIndex) throws SQLException
/*      */   {
/* 1766 */     if ((stringVal != null) && (stringVal.length() > 0)) {
/* 1767 */       int c = Character.toLowerCase(stringVal.charAt(0));
/*      */       
/* 1769 */       return (c == 116) || (c == 121) || (c == 49) || (stringVal.equals("-1"));
/*      */     }
/*      */     
/*      */ 
/* 1773 */     return false;
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
/*      */   public byte getByte(int columnIndex)
/*      */     throws SQLException
/*      */   {
/* 1788 */     if (!this.isBinaryEncoded) {
/* 1789 */       String stringVal = getString(columnIndex);
/*      */       
/* 1791 */       if ((this.wasNullFlag) || (stringVal == null)) {
/* 1792 */         return 0;
/*      */       }
/*      */       
/* 1795 */       return getByteFromString(stringVal, columnIndex);
/*      */     }
/*      */     
/* 1798 */     return getNativeByte(columnIndex);
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
/*      */   public byte getByte(String columnName)
/*      */     throws SQLException
/*      */   {
/* 1813 */     return getByte(findColumn(columnName));
/*      */   }
/*      */   
/*      */   private final byte getByteFromString(String stringVal, int columnIndex)
/*      */     throws SQLException
/*      */   {
/* 1819 */     if ((stringVal != null) && (stringVal.length() == 0)) {
/* 1820 */       return (byte)convertToZeroWithEmptyCheck();
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 1831 */     if (stringVal == null) {
/* 1832 */       return 0;
/*      */     }
/*      */     
/* 1835 */     stringVal = stringVal.trim();
/*      */     try
/*      */     {
/* 1838 */       int decimalIndex = stringVal.indexOf(".");
/*      */       
/*      */ 
/* 1841 */       if (decimalIndex != -1) {
/* 1842 */         double valueAsDouble = Double.parseDouble(stringVal);
/*      */         
/* 1844 */         if ((this.jdbcCompliantTruncationForReads) && (
/* 1845 */           (valueAsDouble < -128.0D) || (valueAsDouble > 127.0D)))
/*      */         {
/* 1847 */           throwRangeException(stringVal, columnIndex, -6);
/*      */         }
/*      */         
/*      */ 
/*      */ 
/* 1852 */         return (byte)(int)valueAsDouble;
/*      */       }
/*      */       
/* 1855 */       long valueAsLong = Long.parseLong(stringVal);
/*      */       
/* 1857 */       if ((this.jdbcCompliantTruncationForReads) && (
/* 1858 */         (valueAsLong < -128L) || (valueAsLong > 127L)))
/*      */       {
/* 1860 */         throwRangeException(String.valueOf(valueAsLong), columnIndex, -6);
/*      */       }
/*      */       
/*      */ 
/*      */ 
/* 1865 */       return (byte)(int)valueAsLong;
/*      */     } catch (NumberFormatException NFE) {
/* 1867 */       throw SQLError.createSQLException(Messages.getString("ResultSet.Value____173") + stringVal + Messages.getString("ResultSet.___is_out_of_range_[-127,127]_174"), "S1009", getExceptionInterceptor());
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
/*      */   public byte[] getBytes(int columnIndex)
/*      */     throws SQLException
/*      */   {
/* 1892 */     return getBytes(columnIndex, false);
/*      */   }
/*      */   
/*      */   protected byte[] getBytes(int columnIndex, boolean noConversion) throws SQLException
/*      */   {
/* 1897 */     if (!this.isBinaryEncoded) {
/* 1898 */       checkRowPos();
/*      */       
/* 1900 */       checkColumnBounds(columnIndex);
/*      */       
/* 1902 */       int columnIndexMinusOne = columnIndex - 1;
/*      */       
/* 1904 */       if (this.thisRow.isNull(columnIndexMinusOne)) {
/* 1905 */         this.wasNullFlag = true;
/*      */       } else {
/* 1907 */         this.wasNullFlag = false;
/*      */       }
/*      */       
/* 1910 */       if (this.wasNullFlag) {
/* 1911 */         return null;
/*      */       }
/*      */       
/* 1914 */       return this.thisRow.getColumnValue(columnIndexMinusOne);
/*      */     }
/*      */     
/* 1917 */     return getNativeBytes(columnIndex, noConversion);
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
/*      */   public byte[] getBytes(String columnName)
/*      */     throws SQLException
/*      */   {
/* 1932 */     return getBytes(findColumn(columnName));
/*      */   }
/*      */   
/*      */   private final byte[] getBytesFromString(String stringVal, int columnIndex) throws SQLException
/*      */   {
/* 1937 */     if (stringVal != null) {
/* 1938 */       return StringUtils.getBytes(stringVal, this.connection.getEncoding(), this.connection.getServerCharacterEncoding(), this.connection.parserKnowsUnicode(), this.connection, getExceptionInterceptor());
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 1945 */     return null;
/*      */   }
/*      */   
/*      */   public int getBytesSize() throws SQLException {
/* 1949 */     RowData localRowData = this.rowData;
/*      */     
/* 1951 */     checkClosed();
/*      */     
/* 1953 */     if ((localRowData instanceof RowDataStatic)) {
/* 1954 */       int bytesSize = 0;
/*      */       
/* 1956 */       int numRows = localRowData.size();
/*      */       
/* 1958 */       for (int i = 0; i < numRows; i++) {
/* 1959 */         bytesSize += localRowData.getAt(i).getBytesSize();
/*      */       }
/*      */       
/* 1962 */       return bytesSize;
/*      */     }
/*      */     
/* 1965 */     return -1;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   protected Calendar getCalendarInstanceForSessionOrNew()
/*      */   {
/* 1973 */     if (this.connection != null) {
/* 1974 */       return this.connection.getCalendarInstanceForSessionOrNew();
/*      */     }
/*      */     
/* 1977 */     return new GregorianCalendar();
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
/*      */   public Reader getCharacterStream(int columnIndex)
/*      */     throws SQLException
/*      */   {
/* 1998 */     if (!this.isBinaryEncoded) {
/* 1999 */       checkColumnBounds(columnIndex);
/*      */       
/* 2001 */       int columnIndexMinusOne = columnIndex - 1;
/*      */       
/* 2003 */       if (this.thisRow.isNull(columnIndexMinusOne)) {
/* 2004 */         this.wasNullFlag = true;
/*      */         
/* 2006 */         return null;
/*      */       }
/*      */       
/* 2009 */       this.wasNullFlag = false;
/*      */       
/* 2011 */       return this.thisRow.getReader(columnIndexMinusOne);
/*      */     }
/*      */     
/* 2014 */     return getNativeCharacterStream(columnIndex);
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
/*      */   public Reader getCharacterStream(String columnName)
/*      */     throws SQLException
/*      */   {
/* 2034 */     return getCharacterStream(findColumn(columnName));
/*      */   }
/*      */   
/*      */   private final Reader getCharacterStreamFromString(String stringVal, int columnIndex) throws SQLException
/*      */   {
/* 2039 */     if (stringVal != null) {
/* 2040 */       return new StringReader(stringVal);
/*      */     }
/*      */     
/* 2043 */     return null;
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
/*      */   public java.sql.Clob getClob(int i)
/*      */     throws SQLException
/*      */   {
/* 2058 */     if (!this.isBinaryEncoded) {
/* 2059 */       String asString = getStringForClob(i);
/*      */       
/* 2061 */       if (asString == null) {
/* 2062 */         return null;
/*      */       }
/*      */       
/* 2065 */       return new Clob(asString, getExceptionInterceptor());
/*      */     }
/*      */     
/* 2068 */     return getNativeClob(i);
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
/*      */   public java.sql.Clob getClob(String colName)
/*      */     throws SQLException
/*      */   {
/* 2083 */     return getClob(findColumn(colName));
/*      */   }
/*      */   
/*      */   private final java.sql.Clob getClobFromString(String stringVal, int columnIndex) throws SQLException
/*      */   {
/* 2088 */     return new Clob(stringVal, getExceptionInterceptor());
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public int getConcurrency()
/*      */     throws SQLException
/*      */   {
/* 2101 */     return 1007;
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
/*      */   public String getCursorName()
/*      */     throws SQLException
/*      */   {
/* 2130 */     throw SQLError.createSQLException(Messages.getString("ResultSet.Positioned_Update_not_supported"), "S1C00", getExceptionInterceptor());
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
/*      */   public Date getDate(int columnIndex)
/*      */     throws SQLException
/*      */   {
/* 2147 */     return getDate(columnIndex, null);
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
/*      */   public Date getDate(int columnIndex, Calendar cal)
/*      */     throws SQLException
/*      */   {
/* 2168 */     if (this.isBinaryEncoded) {
/* 2169 */       return getNativeDate(columnIndex, cal);
/*      */     }
/*      */     
/* 2172 */     if (!this.useFastDateParsing) {
/* 2173 */       String stringVal = getStringInternal(columnIndex, false);
/*      */       
/* 2175 */       if (stringVal == null) {
/* 2176 */         return null;
/*      */       }
/*      */       
/* 2179 */       return getDateFromString(stringVal, columnIndex, cal);
/*      */     }
/*      */     
/* 2182 */     checkColumnBounds(columnIndex);
/*      */     
/* 2184 */     int columnIndexMinusOne = columnIndex - 1;
/*      */     
/* 2186 */     if (this.thisRow.isNull(columnIndexMinusOne)) {
/* 2187 */       this.wasNullFlag = true;
/*      */       
/* 2189 */       return null;
/*      */     }
/*      */     
/* 2192 */     this.wasNullFlag = false;
/*      */     
/* 2194 */     return this.thisRow.getDateFast(columnIndexMinusOne, this.connection, this, cal);
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
/*      */   public Date getDate(String columnName)
/*      */     throws SQLException
/*      */   {
/* 2210 */     return getDate(findColumn(columnName));
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
/*      */   public Date getDate(String columnName, Calendar cal)
/*      */     throws SQLException
/*      */   {
/* 2230 */     return getDate(findColumn(columnName), cal);
/*      */   }
/*      */   
/*      */   private final Date getDateFromString(String stringVal, int columnIndex, Calendar targetCalendar) throws SQLException
/*      */   {
/* 2235 */     int year = 0;
/* 2236 */     int month = 0;
/* 2237 */     int day = 0;
/*      */     try
/*      */     {
/* 2240 */       this.wasNullFlag = false;
/*      */       
/* 2242 */       if (stringVal == null) {
/* 2243 */         this.wasNullFlag = true;
/*      */         
/* 2245 */         return null;
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
/* 2256 */       stringVal = stringVal.trim();
/*      */       
/* 2258 */       if ((stringVal.equals("0")) || (stringVal.equals("0000-00-00")) || (stringVal.equals("0000-00-00 00:00:00")) || (stringVal.equals("00000000000000")) || (stringVal.equals("0")))
/*      */       {
/*      */ 
/*      */ 
/*      */ 
/* 2263 */         if ("convertToNull".equals(this.connection.getZeroDateTimeBehavior()))
/*      */         {
/* 2265 */           this.wasNullFlag = true;
/*      */           
/* 2267 */           return null; }
/* 2268 */         if ("exception".equals(this.connection.getZeroDateTimeBehavior()))
/*      */         {
/* 2270 */           throw SQLError.createSQLException("Value '" + stringVal + "' can not be represented as java.sql.Date", "S1009", getExceptionInterceptor());
/*      */         }
/*      */         
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 2277 */         return fastDateCreate(targetCalendar, 1, 1, 1);
/*      */       }
/* 2279 */       if (this.fields[(columnIndex - 1)].getMysqlType() == 7)
/*      */       {
/* 2281 */         switch (stringVal.length()) {
/*      */         case 19: 
/*      */         case 21: 
/* 2284 */           year = Integer.parseInt(stringVal.substring(0, 4));
/* 2285 */           month = Integer.parseInt(stringVal.substring(5, 7));
/* 2286 */           day = Integer.parseInt(stringVal.substring(8, 10));
/*      */           
/* 2288 */           return fastDateCreate(targetCalendar, year, month, day);
/*      */         
/*      */ 
/*      */         case 8: 
/*      */         case 14: 
/* 2293 */           year = Integer.parseInt(stringVal.substring(0, 4));
/* 2294 */           month = Integer.parseInt(stringVal.substring(4, 6));
/* 2295 */           day = Integer.parseInt(stringVal.substring(6, 8));
/*      */           
/* 2297 */           return fastDateCreate(targetCalendar, year, month, day);
/*      */         
/*      */ 
/*      */         case 6: 
/*      */         case 10: 
/*      */         case 12: 
/* 2303 */           year = Integer.parseInt(stringVal.substring(0, 2));
/*      */           
/* 2305 */           if (year <= 69) {
/* 2306 */             year += 100;
/*      */           }
/*      */           
/* 2309 */           month = Integer.parseInt(stringVal.substring(2, 4));
/* 2310 */           day = Integer.parseInt(stringVal.substring(4, 6));
/*      */           
/* 2312 */           return fastDateCreate(targetCalendar, year + 1900, month, day);
/*      */         
/*      */ 
/*      */         case 4: 
/* 2316 */           year = Integer.parseInt(stringVal.substring(0, 4));
/*      */           
/* 2318 */           if (year <= 69) {
/* 2319 */             year += 100;
/*      */           }
/*      */           
/* 2322 */           month = Integer.parseInt(stringVal.substring(2, 4));
/*      */           
/* 2324 */           return fastDateCreate(targetCalendar, year + 1900, month, 1);
/*      */         
/*      */ 
/*      */         case 2: 
/* 2328 */           year = Integer.parseInt(stringVal.substring(0, 2));
/*      */           
/* 2330 */           if (year <= 69) {
/* 2331 */             year += 100;
/*      */           }
/*      */           
/* 2334 */           return fastDateCreate(targetCalendar, year + 1900, 1, 1);
/*      */         }
/*      */         
/*      */         
/* 2338 */         throw SQLError.createSQLException(Messages.getString("ResultSet.Bad_format_for_Date", new Object[] { stringVal, Constants.integerValueOf(columnIndex) }), "S1009", getExceptionInterceptor());
/*      */       }
/*      */       
/*      */ 
/*      */ 
/* 2343 */       if (this.fields[(columnIndex - 1)].getMysqlType() == 13)
/*      */       {
/* 2345 */         if ((stringVal.length() == 2) || (stringVal.length() == 1)) {
/* 2346 */           year = Integer.parseInt(stringVal);
/*      */           
/* 2348 */           if (year <= 69) {
/* 2349 */             year += 100;
/*      */           }
/*      */           
/* 2352 */           year += 1900;
/*      */         } else {
/* 2354 */           year = Integer.parseInt(stringVal.substring(0, 4));
/*      */         }
/*      */         
/* 2357 */         return fastDateCreate(targetCalendar, year, 1, 1); }
/* 2358 */       if (this.fields[(columnIndex - 1)].getMysqlType() == 11) {
/* 2359 */         return fastDateCreate(targetCalendar, 1970, 1, 1);
/*      */       }
/* 2361 */       if (stringVal.length() < 10) {
/* 2362 */         if (stringVal.length() == 8) {
/* 2363 */           return fastDateCreate(targetCalendar, 1970, 1, 1);
/*      */         }
/*      */         
/* 2366 */         throw SQLError.createSQLException(Messages.getString("ResultSet.Bad_format_for_Date", new Object[] { stringVal, Constants.integerValueOf(columnIndex) }), "S1009", getExceptionInterceptor());
/*      */       }
/*      */       
/*      */ 
/*      */ 
/*      */ 
/* 2372 */       if (stringVal.length() != 18) {
/* 2373 */         year = Integer.parseInt(stringVal.substring(0, 4));
/* 2374 */         month = Integer.parseInt(stringVal.substring(5, 7));
/* 2375 */         day = Integer.parseInt(stringVal.substring(8, 10));
/*      */       }
/*      */       else {
/* 2378 */         StringTokenizer st = new StringTokenizer(stringVal, "- ");
/*      */         
/* 2380 */         year = Integer.parseInt(st.nextToken());
/* 2381 */         month = Integer.parseInt(st.nextToken());
/* 2382 */         day = Integer.parseInt(st.nextToken());
/*      */       }
/*      */       
/*      */ 
/* 2386 */       return fastDateCreate(targetCalendar, year, month, day);
/*      */     } catch (SQLException sqlEx) {
/* 2388 */       throw sqlEx;
/*      */     } catch (Exception e) {
/* 2390 */       SQLException sqlEx = SQLError.createSQLException(Messages.getString("ResultSet.Bad_format_for_Date", new Object[] { stringVal, Constants.integerValueOf(columnIndex) }), "S1009", getExceptionInterceptor());
/*      */       
/*      */ 
/*      */ 
/*      */ 
/* 2395 */       sqlEx.initCause(e);
/*      */       
/* 2397 */       throw sqlEx;
/*      */     }
/*      */   }
/*      */   
/*      */   private TimeZone getDefaultTimeZone() {
/* 2402 */     if ((!this.useLegacyDatetimeCode) && (this.connection != null)) {
/* 2403 */       return this.serverTimeZoneTz;
/*      */     }
/*      */     
/* 2406 */     return this.connection.getDefaultTimeZone();
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
/*      */   public double getDouble(int columnIndex)
/*      */     throws SQLException
/*      */   {
/* 2421 */     if (!this.isBinaryEncoded) {
/* 2422 */       return getDoubleInternal(columnIndex);
/*      */     }
/*      */     
/* 2425 */     return getNativeDouble(columnIndex);
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
/*      */   public double getDouble(String columnName)
/*      */     throws SQLException
/*      */   {
/* 2440 */     return getDouble(findColumn(columnName));
/*      */   }
/*      */   
/*      */   private final double getDoubleFromString(String stringVal, int columnIndex) throws SQLException
/*      */   {
/* 2445 */     return getDoubleInternal(stringVal, columnIndex);
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
/*      */   protected double getDoubleInternal(int colIndex)
/*      */     throws SQLException
/*      */   {
/* 2461 */     return getDoubleInternal(getString(colIndex), colIndex);
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
/*      */   protected double getDoubleInternal(String stringVal, int colIndex)
/*      */     throws SQLException
/*      */   {
/*      */     try
/*      */     {
/* 2481 */       if (stringVal == null) {
/* 2482 */         return 0.0D;
/*      */       }
/*      */       
/* 2485 */       if (stringVal.length() == 0) {
/* 2486 */         return convertToZeroWithEmptyCheck();
/*      */       }
/*      */       
/* 2489 */       double d = Double.parseDouble(stringVal);
/*      */       
/* 2491 */       if (this.useStrictFloatingPoint)
/*      */       {
/* 2493 */         if (d == 2.147483648E9D)
/*      */         {
/* 2495 */           d = 2.147483647E9D;
/* 2496 */         } else if (d == 1.0000000036275E-15D)
/*      */         {
/* 2498 */           d = 1.0E-15D;
/* 2499 */         } else if (d == 9.999999869911E14D) {
/* 2500 */           d = 9.99999999999999E14D;
/* 2501 */         } else if (d == 1.4012984643248E-45D) {
/* 2502 */           d = 1.4E-45D;
/* 2503 */         } else if (d == 1.4013E-45D) {
/* 2504 */           d = 1.4E-45D;
/* 2505 */         } else if (d == 3.4028234663853E37D) {
/* 2506 */           d = 3.4028235E37D;
/* 2507 */         } else if (d == -2.14748E9D) {
/* 2508 */           d = -2.147483648E9D;
/* 2509 */         } else if (d != 3.40282E37D) {} }
/* 2510 */       return 3.4028235E37D;
/*      */ 
/*      */     }
/*      */     catch (NumberFormatException e)
/*      */     {
/*      */ 
/* 2516 */       if (this.fields[(colIndex - 1)].getMysqlType() == 16) {
/* 2517 */         long valueAsLong = getNumericRepresentationOfSQLBitType(colIndex);
/*      */         
/* 2519 */         return valueAsLong;
/*      */       }
/*      */       
/* 2522 */       throw SQLError.createSQLException(Messages.getString("ResultSet.Bad_format_for_number", new Object[] { stringVal, Constants.integerValueOf(colIndex) }), "S1009", getExceptionInterceptor());
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
/*      */   public int getFetchDirection()
/*      */     throws SQLException
/*      */   {
/* 2538 */     return this.fetchDirection;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public int getFetchSize()
/*      */     throws SQLException
/*      */   {
/* 2550 */     return this.fetchSize;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public char getFirstCharOfQuery()
/*      */   {
/* 2560 */     return this.firstCharOfQuery;
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
/*      */   public float getFloat(int columnIndex)
/*      */     throws SQLException
/*      */   {
/* 2575 */     if (!this.isBinaryEncoded) {
/* 2576 */       String val = null;
/*      */       
/* 2578 */       val = getString(columnIndex);
/*      */       
/* 2580 */       return getFloatFromString(val, columnIndex);
/*      */     }
/*      */     
/* 2583 */     return getNativeFloat(columnIndex);
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
/*      */   public float getFloat(String columnName)
/*      */     throws SQLException
/*      */   {
/* 2598 */     return getFloat(findColumn(columnName));
/*      */   }
/*      */   
/*      */   private final float getFloatFromString(String val, int columnIndex) throws SQLException
/*      */   {
/*      */     try {
/* 2604 */       if (val != null) {
/* 2605 */         if (val.length() == 0) {
/* 2606 */           return convertToZeroWithEmptyCheck();
/*      */         }
/*      */         
/* 2609 */         float f = Float.parseFloat(val);
/*      */         
/* 2611 */         if ((this.jdbcCompliantTruncationForReads) && (
/* 2612 */           (f == Float.MIN_VALUE) || (f == Float.MAX_VALUE))) {
/* 2613 */           double valAsDouble = Double.parseDouble(val);
/*      */           
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 2619 */           if ((valAsDouble < 1.401298464324817E-45D - MIN_DIFF_PREC) || (valAsDouble > 3.4028234663852886E38D - MAX_DIFF_PREC))
/*      */           {
/* 2621 */             throwRangeException(String.valueOf(valAsDouble), columnIndex, 6);
/*      */           }
/*      */         }
/*      */         
/*      */ 
/*      */ 
/* 2627 */         return f;
/*      */       }
/*      */       
/* 2630 */       return 0.0F;
/*      */     } catch (NumberFormatException nfe) {
/*      */       try {
/* 2633 */         Double valueAsDouble = new Double(val);
/* 2634 */         float valueAsFloat = valueAsDouble.floatValue();
/*      */         
/* 2636 */         if (this.jdbcCompliantTruncationForReads)
/*      */         {
/* 2638 */           if (((this.jdbcCompliantTruncationForReads) && (valueAsFloat == Float.NEGATIVE_INFINITY)) || (valueAsFloat == Float.POSITIVE_INFINITY))
/*      */           {
/*      */ 
/* 2641 */             throwRangeException(valueAsDouble.toString(), columnIndex, 6);
/*      */           }
/*      */         }
/*      */         
/*      */ 
/* 2646 */         return valueAsFloat;
/*      */ 
/*      */       }
/*      */       catch (NumberFormatException newNfe)
/*      */       {
/* 2651 */         throw SQLError.createSQLException(Messages.getString("ResultSet.Invalid_value_for_getFloat()_-____200") + val + Messages.getString("ResultSet.___in_column__201") + columnIndex, "S1009", getExceptionInterceptor());
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
/*      */   public int getInt(int columnIndex)
/*      */     throws SQLException
/*      */   {
/* 2672 */     checkRowPos();
/*      */     
/* 2674 */     if (!this.isBinaryEncoded) {
/* 2675 */       int columnIndexMinusOne = columnIndex - 1;
/* 2676 */       if (this.useFastIntParsing) {
/* 2677 */         checkColumnBounds(columnIndex);
/*      */         
/* 2679 */         if (this.thisRow.isNull(columnIndexMinusOne)) {
/* 2680 */           this.wasNullFlag = true;
/*      */         } else {
/* 2682 */           this.wasNullFlag = false;
/*      */         }
/*      */         
/* 2685 */         if (this.wasNullFlag) {
/* 2686 */           return 0;
/*      */         }
/*      */         
/* 2689 */         if (this.thisRow.length(columnIndexMinusOne) == 0L) {
/* 2690 */           return convertToZeroWithEmptyCheck();
/*      */         }
/*      */         
/* 2693 */         boolean needsFullParse = this.thisRow.isFloatingPointNumber(columnIndexMinusOne);
/*      */         
/*      */ 
/* 2696 */         if (!needsFullParse) {
/*      */           try {
/* 2698 */             return getIntWithOverflowCheck(columnIndexMinusOne);
/*      */           }
/*      */           catch (NumberFormatException nfe) {
/*      */             try {
/* 2702 */               return parseIntAsDouble(columnIndex, this.thisRow.getString(columnIndexMinusOne, this.fields[columnIndexMinusOne].getCharacterSet(), this.connection));
/*      */ 
/*      */ 
/*      */ 
/*      */             }
/*      */             catch (NumberFormatException newNfe)
/*      */             {
/*      */ 
/*      */ 
/* 2711 */               if (this.fields[columnIndexMinusOne].getMysqlType() == 16) {
/* 2712 */                 long valueAsLong = getNumericRepresentationOfSQLBitType(columnIndex);
/*      */                 
/* 2714 */                 if ((this.connection.getJdbcCompliantTruncationForReads()) && ((valueAsLong < -2147483648L) || (valueAsLong > 2147483647L)))
/*      */                 {
/*      */ 
/* 2717 */                   throwRangeException(String.valueOf(valueAsLong), columnIndex, 4);
/*      */                 }
/*      */                 
/*      */ 
/*      */ 
/* 2722 */                 return (int)valueAsLong;
/*      */               }
/*      */               
/* 2725 */               throw SQLError.createSQLException(Messages.getString("ResultSet.Invalid_value_for_getInt()_-____74") + this.thisRow.getString(columnIndexMinusOne, this.fields[columnIndexMinusOne].getCharacterSet(), this.connection) + "'", "S1009", getExceptionInterceptor());
/*      */             }
/*      */           }
/*      */         }
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
/* 2741 */       String val = null;
/*      */       try
/*      */       {
/* 2744 */         val = getString(columnIndex);
/*      */         
/* 2746 */         if (val != null) {
/* 2747 */           if (val.length() == 0) {
/* 2748 */             return convertToZeroWithEmptyCheck();
/*      */           }
/*      */           
/* 2751 */           if ((val.indexOf("e") == -1) && (val.indexOf("E") == -1) && (val.indexOf(".") == -1))
/*      */           {
/* 2753 */             int intVal = Integer.parseInt(val);
/*      */             
/* 2755 */             checkForIntegerTruncation(columnIndexMinusOne, null, intVal);
/*      */             
/* 2757 */             return intVal;
/*      */           }
/*      */           
/*      */ 
/* 2761 */           int intVal = parseIntAsDouble(columnIndex, val);
/*      */           
/* 2763 */           checkForIntegerTruncation(columnIndex, null, intVal);
/*      */           
/* 2765 */           return intVal;
/*      */         }
/*      */         
/* 2768 */         return 0;
/*      */       } catch (NumberFormatException nfe) {
/*      */         try {
/* 2771 */           return parseIntAsDouble(columnIndex, val);
/*      */ 
/*      */         }
/*      */         catch (NumberFormatException newNfe)
/*      */         {
/* 2776 */           if (this.fields[columnIndexMinusOne].getMysqlType() == 16) {
/* 2777 */             long valueAsLong = getNumericRepresentationOfSQLBitType(columnIndex);
/*      */             
/* 2779 */             if ((this.jdbcCompliantTruncationForReads) && ((valueAsLong < -2147483648L) || (valueAsLong > 2147483647L)))
/*      */             {
/* 2781 */               throwRangeException(String.valueOf(valueAsLong), columnIndex, 4);
/*      */             }
/*      */             
/*      */ 
/* 2785 */             return (int)valueAsLong;
/*      */           }
/*      */           
/* 2788 */           throw SQLError.createSQLException(Messages.getString("ResultSet.Invalid_value_for_getInt()_-____74") + val + "'", "S1009", getExceptionInterceptor());
/*      */         }
/*      */       }
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 2798 */     return getNativeInt(columnIndex);
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
/*      */   public int getInt(String columnName)
/*      */     throws SQLException
/*      */   {
/* 2813 */     return getInt(findColumn(columnName));
/*      */   }
/*      */   
/*      */   private final int getIntFromString(String val, int columnIndex) throws SQLException
/*      */   {
/*      */     try {
/* 2819 */       if (val != null)
/*      */       {
/* 2821 */         if (val.length() == 0) {
/* 2822 */           return convertToZeroWithEmptyCheck();
/*      */         }
/*      */         
/* 2825 */         if ((val.indexOf("e") == -1) && (val.indexOf("E") == -1) && (val.indexOf(".") == -1))
/*      */         {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 2835 */           val = val.trim();
/*      */           
/* 2837 */           int valueAsInt = Integer.parseInt(val);
/*      */           
/* 2839 */           if ((this.jdbcCompliantTruncationForReads) && (
/* 2840 */             (valueAsInt == Integer.MIN_VALUE) || (valueAsInt == Integer.MAX_VALUE)))
/*      */           {
/* 2842 */             long valueAsLong = Long.parseLong(val);
/*      */             
/* 2844 */             if ((valueAsLong < -2147483648L) || (valueAsLong > 2147483647L))
/*      */             {
/* 2846 */               throwRangeException(String.valueOf(valueAsLong), columnIndex, 4);
/*      */             }
/*      */           }
/*      */           
/*      */ 
/*      */ 
/*      */ 
/* 2853 */           return valueAsInt;
/*      */         }
/*      */         
/*      */ 
/*      */ 
/* 2858 */         double valueAsDouble = Double.parseDouble(val);
/*      */         
/* 2860 */         if ((this.jdbcCompliantTruncationForReads) && (
/* 2861 */           (valueAsDouble < -2.147483648E9D) || (valueAsDouble > 2.147483647E9D)))
/*      */         {
/* 2863 */           throwRangeException(String.valueOf(valueAsDouble), columnIndex, 4);
/*      */         }
/*      */         
/*      */ 
/*      */ 
/* 2868 */         return (int)valueAsDouble;
/*      */       }
/*      */       
/* 2871 */       return 0;
/*      */     } catch (NumberFormatException nfe) {
/*      */       try {
/* 2874 */         double valueAsDouble = Double.parseDouble(val);
/*      */         
/* 2876 */         if ((this.jdbcCompliantTruncationForReads) && (
/* 2877 */           (valueAsDouble < -2.147483648E9D) || (valueAsDouble > 2.147483647E9D)))
/*      */         {
/* 2879 */           throwRangeException(String.valueOf(valueAsDouble), columnIndex, 4);
/*      */         }
/*      */         
/*      */ 
/*      */ 
/* 2884 */         return (int)valueAsDouble;
/*      */ 
/*      */       }
/*      */       catch (NumberFormatException newNfe)
/*      */       {
/* 2889 */         throw SQLError.createSQLException(Messages.getString("ResultSet.Invalid_value_for_getInt()_-____206") + val + Messages.getString("ResultSet.___in_column__207") + columnIndex, "S1009", getExceptionInterceptor());
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
/*      */   public long getLong(int columnIndex)
/*      */     throws SQLException
/*      */   {
/* 2909 */     return getLong(columnIndex, true);
/*      */   }
/*      */   
/*      */   private long getLong(int columnIndex, boolean overflowCheck) throws SQLException {
/* 2913 */     if (!this.isBinaryEncoded) {
/* 2914 */       checkRowPos();
/*      */       
/* 2916 */       int columnIndexMinusOne = columnIndex - 1;
/*      */       
/* 2918 */       if (this.useFastIntParsing)
/*      */       {
/* 2920 */         checkColumnBounds(columnIndex);
/*      */         
/* 2922 */         if (this.thisRow.isNull(columnIndexMinusOne)) {
/* 2923 */           this.wasNullFlag = true;
/*      */         } else {
/* 2925 */           this.wasNullFlag = false;
/*      */         }
/*      */         
/* 2928 */         if (this.wasNullFlag) {
/* 2929 */           return 0L;
/*      */         }
/*      */         
/* 2932 */         if (this.thisRow.length(columnIndexMinusOne) == 0L) {
/* 2933 */           return convertToZeroWithEmptyCheck();
/*      */         }
/*      */         
/* 2936 */         boolean needsFullParse = this.thisRow.isFloatingPointNumber(columnIndexMinusOne);
/*      */         
/* 2938 */         if (!needsFullParse) {
/*      */           try {
/* 2940 */             return getLongWithOverflowCheck(columnIndexMinusOne, overflowCheck);
/*      */           }
/*      */           catch (NumberFormatException nfe) {
/*      */             try {
/* 2944 */               return parseLongAsDouble(columnIndexMinusOne, this.thisRow.getString(columnIndexMinusOne, this.fields[columnIndexMinusOne].getCharacterSet(), this.connection));
/*      */ 
/*      */ 
/*      */ 
/*      */             }
/*      */             catch (NumberFormatException newNfe)
/*      */             {
/*      */ 
/*      */ 
/* 2953 */               if (this.fields[columnIndexMinusOne].getMysqlType() == 16) {
/* 2954 */                 return getNumericRepresentationOfSQLBitType(columnIndex);
/*      */               }
/*      */               
/* 2957 */               throw SQLError.createSQLException(Messages.getString("ResultSet.Invalid_value_for_getLong()_-____79") + this.thisRow.getString(columnIndexMinusOne, this.fields[columnIndexMinusOne].getCharacterSet(), this.connection) + "'", "S1009", getExceptionInterceptor());
/*      */             }
/*      */           }
/*      */         }
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
/* 2971 */       String val = null;
/*      */       try
/*      */       {
/* 2974 */         val = getString(columnIndex);
/*      */         
/* 2976 */         if (val != null) {
/* 2977 */           if (val.length() == 0) {
/* 2978 */             return convertToZeroWithEmptyCheck();
/*      */           }
/*      */           
/* 2981 */           if ((val.indexOf("e") == -1) && (val.indexOf("E") == -1)) {
/* 2982 */             return parseLongWithOverflowCheck(columnIndexMinusOne, null, val, overflowCheck);
/*      */           }
/*      */           
/*      */ 
/*      */ 
/* 2987 */           return parseLongAsDouble(columnIndexMinusOne, val);
/*      */         }
/*      */         
/* 2990 */         return 0L;
/*      */       } catch (NumberFormatException nfe) {
/*      */         try {
/* 2993 */           return parseLongAsDouble(columnIndexMinusOne, val);
/*      */ 
/*      */         }
/*      */         catch (NumberFormatException newNfe)
/*      */         {
/* 2998 */           throw SQLError.createSQLException(Messages.getString("ResultSet.Invalid_value_for_getLong()_-____79") + val + "'", "S1009", getExceptionInterceptor());
/*      */         }
/*      */       }
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*      */ 
/* 3006 */     return getNativeLong(columnIndex, overflowCheck, true);
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
/*      */   public long getLong(String columnName)
/*      */     throws SQLException
/*      */   {
/* 3021 */     return getLong(findColumn(columnName));
/*      */   }
/*      */   
/*      */   private final long getLongFromString(String val, int columnIndexZeroBased) throws SQLException
/*      */   {
/*      */     try {
/* 3027 */       if (val != null)
/*      */       {
/* 3029 */         if (val.length() == 0) {
/* 3030 */           return convertToZeroWithEmptyCheck();
/*      */         }
/*      */         
/* 3033 */         if ((val.indexOf("e") == -1) && (val.indexOf("E") == -1)) {
/* 3034 */           return parseLongWithOverflowCheck(columnIndexZeroBased, null, val, true);
/*      */         }
/*      */         
/*      */ 
/* 3038 */         return parseLongAsDouble(columnIndexZeroBased, val);
/*      */       }
/*      */       
/* 3041 */       return 0L;
/*      */     }
/*      */     catch (NumberFormatException nfe) {
/*      */       try {
/* 3045 */         return parseLongAsDouble(columnIndexZeroBased, val);
/*      */ 
/*      */       }
/*      */       catch (NumberFormatException newNfe)
/*      */       {
/* 3050 */         throw SQLError.createSQLException(Messages.getString("ResultSet.Invalid_value_for_getLong()_-____211") + val + Messages.getString("ResultSet.___in_column__212") + (columnIndexZeroBased + 1), "S1009", getExceptionInterceptor());
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
/*      */   public java.sql.ResultSetMetaData getMetaData()
/*      */     throws SQLException
/*      */   {
/* 3069 */     checkClosed();
/*      */     
/* 3071 */     return new ResultSetMetaData(this.fields, this.connection.getUseOldAliasMetadataBehavior(), getExceptionInterceptor());
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
/*      */   protected Array getNativeArray(int i)
/*      */     throws SQLException
/*      */   {
/* 3089 */     throw SQLError.notImplemented();
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
/*      */   protected InputStream getNativeAsciiStream(int columnIndex)
/*      */     throws SQLException
/*      */   {
/* 3119 */     checkRowPos();
/*      */     
/* 3121 */     return getNativeBinaryStream(columnIndex);
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
/*      */   protected BigDecimal getNativeBigDecimal(int columnIndex)
/*      */     throws SQLException
/*      */   {
/* 3140 */     checkColumnBounds(columnIndex);
/*      */     
/* 3142 */     int scale = this.fields[(columnIndex - 1)].getDecimals();
/*      */     
/* 3144 */     return getNativeBigDecimal(columnIndex, scale);
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
/*      */   protected BigDecimal getNativeBigDecimal(int columnIndex, int scale)
/*      */     throws SQLException
/*      */   {
/* 3163 */     checkColumnBounds(columnIndex);
/*      */     
/* 3165 */     String stringVal = null;
/*      */     
/* 3167 */     Field f = this.fields[(columnIndex - 1)];
/*      */     
/* 3169 */     Object value = this.thisRow.getColumnValue(columnIndex - 1);
/*      */     
/* 3171 */     if (value == null) {
/* 3172 */       this.wasNullFlag = true;
/*      */       
/* 3174 */       return null;
/*      */     }
/*      */     
/* 3177 */     this.wasNullFlag = false;
/*      */     
/* 3179 */     switch (f.getSQLType()) {
/*      */     case 2: 
/*      */     case 3: 
/* 3182 */       stringVal = StringUtils.toAsciiString((byte[])value);
/*      */       
/* 3184 */       break;
/*      */     default: 
/* 3186 */       stringVal = getNativeString(columnIndex);
/*      */     }
/*      */     
/* 3189 */     return getBigDecimalFromString(stringVal, columnIndex, scale);
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
/*      */   protected InputStream getNativeBinaryStream(int columnIndex)
/*      */     throws SQLException
/*      */   {
/* 3211 */     checkRowPos();
/*      */     
/* 3213 */     int columnIndexMinusOne = columnIndex - 1;
/*      */     
/* 3215 */     if (this.thisRow.isNull(columnIndexMinusOne)) {
/* 3216 */       this.wasNullFlag = true;
/*      */       
/* 3218 */       return null;
/*      */     }
/*      */     
/* 3221 */     this.wasNullFlag = false;
/*      */     
/* 3223 */     switch (this.fields[columnIndexMinusOne].getSQLType()) {
/*      */     case -7: 
/*      */     case -4: 
/*      */     case -3: 
/*      */     case -2: 
/*      */     case 2004: 
/* 3229 */       return this.thisRow.getBinaryInputStream(columnIndexMinusOne);
/*      */     }
/*      */     
/* 3232 */     byte[] b = getNativeBytes(columnIndex, false);
/*      */     
/* 3234 */     if (b != null) {
/* 3235 */       return new ByteArrayInputStream(b);
/*      */     }
/*      */     
/* 3238 */     return null;
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
/*      */   protected java.sql.Blob getNativeBlob(int columnIndex)
/*      */     throws SQLException
/*      */   {
/* 3253 */     checkRowPos();
/*      */     
/* 3255 */     checkColumnBounds(columnIndex);
/*      */     
/* 3257 */     Object value = this.thisRow.getColumnValue(columnIndex - 1);
/*      */     
/* 3259 */     if (value == null) {
/* 3260 */       this.wasNullFlag = true;
/*      */     } else {
/* 3262 */       this.wasNullFlag = false;
/*      */     }
/*      */     
/* 3265 */     if (this.wasNullFlag) {
/* 3266 */       return null;
/*      */     }
/*      */     
/* 3269 */     int mysqlType = this.fields[(columnIndex - 1)].getMysqlType();
/*      */     
/* 3271 */     byte[] dataAsBytes = null;
/*      */     
/* 3273 */     switch (mysqlType) {
/*      */     case 249: 
/*      */     case 250: 
/*      */     case 251: 
/*      */     case 252: 
/* 3278 */       dataAsBytes = (byte[])value;
/* 3279 */       break;
/*      */     
/*      */     default: 
/* 3282 */       dataAsBytes = getNativeBytes(columnIndex, false);
/*      */     }
/*      */     
/* 3285 */     if (!this.connection.getEmulateLocators()) {
/* 3286 */       return new Blob(dataAsBytes, getExceptionInterceptor());
/*      */     }
/*      */     
/* 3289 */     return new BlobFromLocator(this, columnIndex, getExceptionInterceptor());
/*      */   }
/*      */   
/*      */   public static boolean arraysEqual(byte[] left, byte[] right) {
/* 3293 */     if (left == null) {
/* 3294 */       return right == null;
/*      */     }
/* 3296 */     if (right == null) {
/* 3297 */       return false;
/*      */     }
/* 3299 */     if (left.length != right.length) {
/* 3300 */       return false;
/*      */     }
/* 3302 */     for (int i = 0; i < left.length; i++) {
/* 3303 */       if (left[i] != right[i]) {
/* 3304 */         return false;
/*      */       }
/*      */     }
/* 3307 */     return true;
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
/*      */   protected byte getNativeByte(int columnIndex)
/*      */     throws SQLException
/*      */   {
/* 3322 */     return getNativeByte(columnIndex, true);
/*      */   }
/*      */   
/*      */   protected byte getNativeByte(int columnIndex, boolean overflowCheck) throws SQLException {
/* 3326 */     checkRowPos();
/*      */     
/* 3328 */     checkColumnBounds(columnIndex);
/*      */     
/* 3330 */     Object value = this.thisRow.getColumnValue(columnIndex - 1);
/*      */     
/* 3332 */     if (value == null) {
/* 3333 */       this.wasNullFlag = true;
/*      */       
/* 3335 */       return 0;
/*      */     }
/*      */     
/* 3338 */     if (value == null) {
/* 3339 */       this.wasNullFlag = true;
/*      */     } else {
/* 3341 */       this.wasNullFlag = false;
/*      */     }
/*      */     
/* 3344 */     if (this.wasNullFlag) {
/* 3345 */       return 0;
/*      */     }
/*      */     
/* 3348 */     columnIndex--;
/*      */     
/* 3350 */     Field field = this.fields[columnIndex];
/*      */     long valueAsLong;
/* 3352 */     short valueAsShort; switch (field.getMysqlType()) {
/*      */     case 16: 
/* 3354 */       valueAsLong = getNumericRepresentationOfSQLBitType(columnIndex + 1);
/*      */       
/* 3356 */       if ((overflowCheck) && (this.jdbcCompliantTruncationForReads) && ((valueAsLong < -128L) || (valueAsLong > 127L)))
/*      */       {
/*      */ 
/* 3359 */         throwRangeException(String.valueOf(valueAsLong), columnIndex + 1, -6);
/*      */       }
/*      */       
/*      */ 
/* 3363 */       return (byte)(int)valueAsLong;
/*      */     case 1: 
/* 3365 */       byte valueAsByte = ((byte[])(byte[])value)[0];
/*      */       
/* 3367 */       if (!field.isUnsigned()) {
/* 3368 */         return valueAsByte;
/*      */       }
/*      */       
/* 3371 */       valueAsShort = valueAsByte >= 0 ? (short)valueAsByte : (short)(valueAsByte + 256);
/*      */       
/*      */ 
/* 3374 */       if ((overflowCheck) && (this.jdbcCompliantTruncationForReads) && 
/* 3375 */         (valueAsShort > 127)) {
/* 3376 */         throwRangeException(String.valueOf(valueAsShort), columnIndex + 1, -6);
/*      */       }
/*      */       
/*      */ 
/*      */ 
/* 3381 */       return (byte)valueAsShort;
/*      */     
/*      */     case 2: 
/*      */     case 13: 
/* 3385 */       valueAsShort = getNativeShort(columnIndex + 1);
/*      */       
/* 3387 */       if ((overflowCheck) && (this.jdbcCompliantTruncationForReads) && (
/* 3388 */         (valueAsShort < -128) || (valueAsShort > 127)))
/*      */       {
/* 3390 */         throwRangeException(String.valueOf(valueAsShort), columnIndex + 1, -6);
/*      */       }
/*      */       
/*      */ 
/*      */ 
/* 3395 */       return (byte)valueAsShort;
/*      */     case 3: 
/*      */     case 9: 
/* 3398 */       int valueAsInt = getNativeInt(columnIndex + 1, false);
/*      */       
/* 3400 */       if ((overflowCheck) && (this.jdbcCompliantTruncationForReads) && (
/* 3401 */         (valueAsInt < -128) || (valueAsInt > 127))) {
/* 3402 */         throwRangeException(String.valueOf(valueAsInt), columnIndex + 1, -6);
/*      */       }
/*      */       
/*      */ 
/*      */ 
/* 3407 */       return (byte)valueAsInt;
/*      */     
/*      */     case 4: 
/* 3410 */       float valueAsFloat = getNativeFloat(columnIndex + 1);
/*      */       
/* 3412 */       if ((overflowCheck) && (this.jdbcCompliantTruncationForReads) && (
/* 3413 */         (valueAsFloat < -128.0F) || (valueAsFloat > 127.0F)))
/*      */       {
/*      */ 
/* 3416 */         throwRangeException(String.valueOf(valueAsFloat), columnIndex + 1, -6);
/*      */       }
/*      */       
/*      */ 
/*      */ 
/* 3421 */       return (byte)(int)valueAsFloat;
/*      */     
/*      */     case 5: 
/* 3424 */       double valueAsDouble = getNativeDouble(columnIndex + 1);
/*      */       
/* 3426 */       if ((overflowCheck) && (this.jdbcCompliantTruncationForReads) && (
/* 3427 */         (valueAsDouble < -128.0D) || (valueAsDouble > 127.0D)))
/*      */       {
/* 3429 */         throwRangeException(String.valueOf(valueAsDouble), columnIndex + 1, -6);
/*      */       }
/*      */       
/*      */ 
/*      */ 
/* 3434 */       return (byte)(int)valueAsDouble;
/*      */     
/*      */     case 8: 
/* 3437 */       valueAsLong = getNativeLong(columnIndex + 1, false, true);
/*      */       
/* 3439 */       if ((overflowCheck) && (this.jdbcCompliantTruncationForReads) && (
/* 3440 */         (valueAsLong < -128L) || (valueAsLong > 127L)))
/*      */       {
/* 3442 */         throwRangeException(String.valueOf(valueAsLong), columnIndex + 1, -6);
/*      */       }
/*      */       
/*      */ 
/*      */ 
/* 3447 */       return (byte)(int)valueAsLong;
/*      */     }
/*      */     
/* 3450 */     if (this.useUsageAdvisor) {
/* 3451 */       issueConversionViaParsingWarning("getByte()", columnIndex, this.thisRow.getColumnValue(columnIndex - 1), this.fields[columnIndex], new int[] { 5, 1, 2, 3, 8, 4 });
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 3461 */     return getByteFromString(getNativeString(columnIndex + 1), columnIndex + 1);
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
/*      */   protected byte[] getNativeBytes(int columnIndex, boolean noConversion)
/*      */     throws SQLException
/*      */   {
/* 3483 */     checkRowPos();
/*      */     
/* 3485 */     checkColumnBounds(columnIndex);
/*      */     
/* 3487 */     Object value = this.thisRow.getColumnValue(columnIndex - 1);
/*      */     
/* 3489 */     if (value == null) {
/* 3490 */       this.wasNullFlag = true;
/*      */     } else {
/* 3492 */       this.wasNullFlag = false;
/*      */     }
/*      */     
/* 3495 */     if (this.wasNullFlag) {
/* 3496 */       return null;
/*      */     }
/*      */     
/* 3499 */     Field field = this.fields[(columnIndex - 1)];
/*      */     
/* 3501 */     int mysqlType = field.getMysqlType();
/*      */     
/*      */ 
/*      */ 
/* 3505 */     if (noConversion) {
/* 3506 */       mysqlType = 252;
/*      */     }
/*      */     
/* 3509 */     switch (mysqlType) {
/*      */     case 16: 
/*      */     case 249: 
/*      */     case 250: 
/*      */     case 251: 
/*      */     case 252: 
/* 3515 */       return (byte[])value;
/*      */     
/*      */     case 15: 
/*      */     case 253: 
/*      */     case 254: 
/* 3520 */       if ((value instanceof byte[])) {
/* 3521 */         return (byte[])value;
/*      */       }
/*      */       break;
/*      */     }
/* 3525 */     int sqlType = field.getSQLType();
/*      */     
/* 3527 */     if ((sqlType == -3) || (sqlType == -2)) {
/* 3528 */       return (byte[])value;
/*      */     }
/*      */     
/* 3531 */     return getBytesFromString(getNativeString(columnIndex), columnIndex);
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
/*      */   protected Reader getNativeCharacterStream(int columnIndex)
/*      */     throws SQLException
/*      */   {
/* 3552 */     int columnIndexMinusOne = columnIndex - 1;
/*      */     
/* 3554 */     switch (this.fields[columnIndexMinusOne].getSQLType()) {
/*      */     case -1: 
/*      */     case 1: 
/*      */     case 12: 
/*      */     case 2005: 
/* 3559 */       if (this.thisRow.isNull(columnIndexMinusOne)) {
/* 3560 */         this.wasNullFlag = true;
/*      */         
/* 3562 */         return null;
/*      */       }
/*      */       
/* 3565 */       this.wasNullFlag = false;
/*      */       
/* 3567 */       return this.thisRow.getReader(columnIndexMinusOne);
/*      */     }
/*      */     
/* 3570 */     String asString = null;
/*      */     
/* 3572 */     asString = getStringForClob(columnIndex);
/*      */     
/* 3574 */     if (asString == null) {
/* 3575 */       return null;
/*      */     }
/*      */     
/* 3578 */     return getCharacterStreamFromString(asString, columnIndex);
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
/*      */   protected java.sql.Clob getNativeClob(int columnIndex)
/*      */     throws SQLException
/*      */   {
/* 3593 */     String stringVal = getStringForClob(columnIndex);
/*      */     
/* 3595 */     if (stringVal == null) {
/* 3596 */       return null;
/*      */     }
/*      */     
/* 3599 */     return getClobFromString(stringVal, columnIndex);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */   private String getNativeConvertToString(int columnIndex, Field field)
/*      */     throws SQLException
/*      */   {
/* 3607 */     int sqlType = field.getSQLType();
/* 3608 */     int mysqlType = field.getMysqlType();
/*      */     int intVal;
/* 3610 */     long longVal; switch (sqlType) {
/*      */     case -7: 
/* 3612 */       return String.valueOf(getNumericRepresentationOfSQLBitType(columnIndex));
/*      */     case 16: 
/* 3614 */       boolean booleanVal = getBoolean(columnIndex);
/*      */       
/* 3616 */       if (this.wasNullFlag) {
/* 3617 */         return null;
/*      */       }
/*      */       
/* 3620 */       return String.valueOf(booleanVal);
/*      */     
/*      */     case -6: 
/* 3623 */       byte tinyintVal = getNativeByte(columnIndex, false);
/*      */       
/* 3625 */       if (this.wasNullFlag) {
/* 3626 */         return null;
/*      */       }
/*      */       
/* 3629 */       if ((!field.isUnsigned()) || (tinyintVal >= 0)) {
/* 3630 */         return String.valueOf(tinyintVal);
/*      */       }
/*      */       
/* 3633 */       short unsignedTinyVal = (short)(tinyintVal & 0xFF);
/*      */       
/* 3635 */       return String.valueOf(unsignedTinyVal);
/*      */     
/*      */ 
/*      */     case 5: 
/* 3639 */       intVal = getNativeInt(columnIndex, false);
/*      */       
/* 3641 */       if (this.wasNullFlag) {
/* 3642 */         return null;
/*      */       }
/*      */       
/* 3645 */       if ((!field.isUnsigned()) || (intVal >= 0)) {
/* 3646 */         return String.valueOf(intVal);
/*      */       }
/*      */       
/* 3649 */       intVal &= 0xFFFF;
/*      */       
/* 3651 */       return String.valueOf(intVal);
/*      */     
/*      */     case 4: 
/* 3654 */       intVal = getNativeInt(columnIndex, false);
/*      */       
/* 3656 */       if (this.wasNullFlag) {
/* 3657 */         return null;
/*      */       }
/*      */       
/* 3660 */       if ((!field.isUnsigned()) || (intVal >= 0) || (field.getMysqlType() == 9))
/*      */       {
/*      */ 
/* 3663 */         return String.valueOf(intVal);
/*      */       }
/*      */       
/* 3666 */       longVal = intVal & 0xFFFFFFFF;
/*      */       
/* 3668 */       return String.valueOf(longVal);
/*      */     
/*      */ 
/*      */     case -5: 
/* 3672 */       if (!field.isUnsigned()) {
/* 3673 */         longVal = getNativeLong(columnIndex, false, true);
/*      */         
/* 3675 */         if (this.wasNullFlag) {
/* 3676 */           return null;
/*      */         }
/*      */         
/* 3679 */         return String.valueOf(longVal);
/*      */       }
/*      */       
/* 3682 */       long longVal = getNativeLong(columnIndex, false, false);
/*      */       
/* 3684 */       if (this.wasNullFlag) {
/* 3685 */         return null;
/*      */       }
/*      */       
/* 3688 */       return String.valueOf(convertLongToUlong(longVal));
/*      */     case 7: 
/* 3690 */       float floatVal = getNativeFloat(columnIndex);
/*      */       
/* 3692 */       if (this.wasNullFlag) {
/* 3693 */         return null;
/*      */       }
/*      */       
/* 3696 */       return String.valueOf(floatVal);
/*      */     
/*      */     case 6: 
/*      */     case 8: 
/* 3700 */       double doubleVal = getNativeDouble(columnIndex);
/*      */       
/* 3702 */       if (this.wasNullFlag) {
/* 3703 */         return null;
/*      */       }
/*      */       
/* 3706 */       return String.valueOf(doubleVal);
/*      */     
/*      */     case 2: 
/*      */     case 3: 
/* 3710 */       String stringVal = StringUtils.toAsciiString((byte[])this.thisRow.getColumnValue(columnIndex - 1));
/*      */       
/*      */ 
/*      */ 
/*      */ 
/* 3715 */       if (stringVal != null) {
/* 3716 */         this.wasNullFlag = false;
/*      */         
/* 3718 */         if (stringVal.length() == 0) {
/* 3719 */           BigDecimal val = new BigDecimal(0);
/*      */           
/* 3721 */           return val.toString();
/*      */         }
/*      */         BigDecimal val;
/*      */         try {
/* 3725 */           val = new BigDecimal(stringVal);
/*      */         } catch (NumberFormatException ex) {
/* 3727 */           throw SQLError.createSQLException(Messages.getString("ResultSet.Bad_format_for_BigDecimal", new Object[] { stringVal, Constants.integerValueOf(columnIndex) }), "S1009", getExceptionInterceptor());
/*      */         }
/*      */         
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 3734 */         return val.toString();
/*      */       }
/*      */       
/* 3737 */       this.wasNullFlag = true;
/*      */       
/* 3739 */       return null;
/*      */     
/*      */ 
/*      */     case -1: 
/*      */     case 1: 
/*      */     case 12: 
/* 3745 */       return extractStringFromNativeColumn(columnIndex, mysqlType);
/*      */     
/*      */     case -4: 
/*      */     case -3: 
/*      */     case -2: 
/* 3750 */       if (!field.isBlob())
/* 3751 */         return extractStringFromNativeColumn(columnIndex, mysqlType);
/* 3752 */       if (!field.isBinary()) {
/* 3753 */         return extractStringFromNativeColumn(columnIndex, mysqlType);
/*      */       }
/* 3755 */       byte[] data = getBytes(columnIndex);
/* 3756 */       Object obj = data;
/*      */       
/* 3758 */       if ((data != null) && (data.length >= 2)) {
/* 3759 */         if ((data[0] == -84) && (data[1] == -19)) {
/*      */           try
/*      */           {
/* 3762 */             ByteArrayInputStream bytesIn = new ByteArrayInputStream(data);
/*      */             
/* 3764 */             ObjectInputStream objIn = new ObjectInputStream(bytesIn);
/*      */             
/* 3766 */             obj = objIn.readObject();
/* 3767 */             objIn.close();
/* 3768 */             bytesIn.close();
/*      */           } catch (ClassNotFoundException cnfe) {
/* 3770 */             throw SQLError.createSQLException(Messages.getString("ResultSet.Class_not_found___91") + cnfe.toString() + Messages.getString("ResultSet._while_reading_serialized_object_92"), getExceptionInterceptor());
/*      */ 
/*      */ 
/*      */           }
/*      */           catch (IOException ex)
/*      */           {
/*      */ 
/* 3777 */             obj = data;
/*      */           }
/*      */         }
/*      */         
/* 3781 */         return obj.toString();
/*      */       }
/*      */       
/* 3784 */       return extractStringFromNativeColumn(columnIndex, mysqlType);
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*      */     case 91: 
/* 3790 */       if (mysqlType == 13) {
/* 3791 */         short shortVal = getNativeShort(columnIndex);
/*      */         
/* 3793 */         if (!this.connection.getYearIsDateType())
/*      */         {
/* 3795 */           if (this.wasNullFlag) {
/* 3796 */             return null;
/*      */           }
/*      */           
/* 3799 */           return String.valueOf(shortVal);
/*      */         }
/*      */         
/* 3802 */         if (field.getLength() == 2L)
/*      */         {
/* 3804 */           if (shortVal <= 69) {
/* 3805 */             shortVal = (short)(shortVal + 100);
/*      */           }
/*      */           
/* 3808 */           shortVal = (short)(shortVal + 1900);
/*      */         }
/*      */         
/* 3811 */         return fastDateCreate(null, shortVal, 1, 1).toString();
/*      */       }
/*      */       
/*      */ 
/* 3815 */       if (this.connection.getNoDatetimeStringSync()) {
/* 3816 */         byte[] asBytes = getNativeBytes(columnIndex, true);
/*      */         
/* 3818 */         if (asBytes == null) {
/* 3819 */           return null;
/*      */         }
/*      */         
/* 3822 */         if (asBytes.length == 0)
/*      */         {
/* 3824 */           return "0000-00-00";
/*      */         }
/*      */         
/* 3827 */         int year = asBytes[0] & 0xFF | (asBytes[1] & 0xFF) << 8;
/*      */         
/* 3829 */         int month = asBytes[2];
/* 3830 */         int day = asBytes[3];
/*      */         
/* 3832 */         if ((year == 0) && (month == 0) && (day == 0)) {
/* 3833 */           return "0000-00-00";
/*      */         }
/*      */       }
/*      */       
/* 3837 */       Date dt = getNativeDate(columnIndex);
/*      */       
/* 3839 */       if (dt == null) {
/* 3840 */         return null;
/*      */       }
/*      */       
/* 3843 */       return String.valueOf(dt);
/*      */     
/*      */     case 92: 
/* 3846 */       Time tm = getNativeTime(columnIndex, null, this.defaultTimeZone, false);
/*      */       
/* 3848 */       if (tm == null) {
/* 3849 */         return null;
/*      */       }
/*      */       
/* 3852 */       return String.valueOf(tm);
/*      */     
/*      */     case 93: 
/* 3855 */       if (this.connection.getNoDatetimeStringSync()) {
/* 3856 */         byte[] asBytes = getNativeBytes(columnIndex, true);
/*      */         
/* 3858 */         if (asBytes == null) {
/* 3859 */           return null;
/*      */         }
/*      */         
/* 3862 */         if (asBytes.length == 0)
/*      */         {
/* 3864 */           return "0000-00-00 00:00:00";
/*      */         }
/*      */         
/* 3867 */         int year = asBytes[0] & 0xFF | (asBytes[1] & 0xFF) << 8;
/*      */         
/* 3869 */         int month = asBytes[2];
/* 3870 */         int day = asBytes[3];
/*      */         
/* 3872 */         if ((year == 0) && (month == 0) && (day == 0)) {
/* 3873 */           return "0000-00-00 00:00:00";
/*      */         }
/*      */       }
/*      */       
/* 3877 */       Timestamp tstamp = getNativeTimestamp(columnIndex, null, this.defaultTimeZone, false);
/*      */       
/*      */ 
/* 3880 */       if (tstamp == null) {
/* 3881 */         return null;
/*      */       }
/*      */       
/* 3884 */       String result = String.valueOf(tstamp);
/*      */       
/* 3886 */       if (!this.connection.getNoDatetimeStringSync()) {
/* 3887 */         return result;
/*      */       }
/*      */       
/* 3890 */       if (result.endsWith(".0")) {
/* 3891 */         return result.substring(0, result.length() - 2);
/*      */       }
/*      */       break;
/*      */     }
/* 3895 */     return extractStringFromNativeColumn(columnIndex, mysqlType);
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
/*      */   protected Date getNativeDate(int columnIndex)
/*      */     throws SQLException
/*      */   {
/* 3911 */     return getNativeDate(columnIndex, null);
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
/*      */   protected Date getNativeDate(int columnIndex, Calendar cal)
/*      */     throws SQLException
/*      */   {
/* 3932 */     checkRowPos();
/* 3933 */     checkColumnBounds(columnIndex);
/*      */     
/* 3935 */     int columnIndexMinusOne = columnIndex - 1;
/*      */     
/* 3937 */     int mysqlType = this.fields[columnIndexMinusOne].getMysqlType();
/*      */     
/* 3939 */     Date dateToReturn = null;
/*      */     
/* 3941 */     if (mysqlType == 10)
/*      */     {
/* 3943 */       dateToReturn = this.thisRow.getNativeDate(columnIndexMinusOne, this.connection, this, cal);
/*      */     }
/*      */     else {
/* 3946 */       TimeZone tz = cal != null ? cal.getTimeZone() : getDefaultTimeZone();
/*      */       
/*      */ 
/* 3949 */       boolean rollForward = (tz != null) && (!tz.equals(getDefaultTimeZone()));
/*      */       
/* 3951 */       dateToReturn = (Date)this.thisRow.getNativeDateTimeValue(columnIndexMinusOne, null, 91, mysqlType, tz, rollForward, this.connection, this);
/*      */     }
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
/* 3963 */     if (dateToReturn == null)
/*      */     {
/* 3965 */       this.wasNullFlag = true;
/*      */       
/* 3967 */       return null;
/*      */     }
/*      */     
/* 3970 */     this.wasNullFlag = false;
/*      */     
/* 3972 */     return dateToReturn;
/*      */   }
/*      */   
/*      */   Date getNativeDateViaParseConversion(int columnIndex) throws SQLException {
/* 3976 */     if (this.useUsageAdvisor) {
/* 3977 */       issueConversionViaParsingWarning("getDate()", columnIndex, this.thisRow.getColumnValue(columnIndex - 1), this.fields[(columnIndex - 1)], new int[] { 10 });
/*      */     }
/*      */     
/*      */ 
/*      */ 
/* 3982 */     String stringVal = getNativeString(columnIndex);
/*      */     
/* 3984 */     return getDateFromString(stringVal, columnIndex, null);
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
/*      */   protected double getNativeDouble(int columnIndex)
/*      */     throws SQLException
/*      */   {
/* 3999 */     checkRowPos();
/* 4000 */     checkColumnBounds(columnIndex);
/*      */     
/* 4002 */     columnIndex--;
/*      */     
/* 4004 */     if (this.thisRow.isNull(columnIndex)) {
/* 4005 */       this.wasNullFlag = true;
/*      */       
/* 4007 */       return 0.0D;
/*      */     }
/*      */     
/* 4010 */     this.wasNullFlag = false;
/*      */     
/* 4012 */     Field f = this.fields[columnIndex];
/*      */     
/* 4014 */     switch (f.getMysqlType()) {
/*      */     case 5: 
/* 4016 */       return this.thisRow.getNativeDouble(columnIndex);
/*      */     case 1: 
/* 4018 */       if (!f.isUnsigned()) {
/* 4019 */         return getNativeByte(columnIndex + 1);
/*      */       }
/*      */       
/* 4022 */       return getNativeShort(columnIndex + 1);
/*      */     case 2: 
/*      */     case 13: 
/* 4025 */       if (!f.isUnsigned()) {
/* 4026 */         return getNativeShort(columnIndex + 1);
/*      */       }
/*      */       
/* 4029 */       return getNativeInt(columnIndex + 1);
/*      */     case 3: 
/*      */     case 9: 
/* 4032 */       if (!f.isUnsigned()) {
/* 4033 */         return getNativeInt(columnIndex + 1);
/*      */       }
/*      */       
/* 4036 */       return getNativeLong(columnIndex + 1);
/*      */     case 8: 
/* 4038 */       long valueAsLong = getNativeLong(columnIndex + 1);
/*      */       
/* 4040 */       if (!f.isUnsigned()) {
/* 4041 */         return valueAsLong;
/*      */       }
/*      */       
/* 4044 */       BigInteger asBigInt = convertLongToUlong(valueAsLong);
/*      */       
/*      */ 
/*      */ 
/* 4048 */       return asBigInt.doubleValue();
/*      */     case 4: 
/* 4050 */       return getNativeFloat(columnIndex + 1);
/*      */     case 16: 
/* 4052 */       return getNumericRepresentationOfSQLBitType(columnIndex + 1);
/*      */     }
/* 4054 */     String stringVal = getNativeString(columnIndex + 1);
/*      */     
/* 4056 */     if (this.useUsageAdvisor) {
/* 4057 */       issueConversionViaParsingWarning("getDouble()", columnIndex, stringVal, this.fields[columnIndex], new int[] { 5, 1, 2, 3, 8, 4 });
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 4067 */     return getDoubleFromString(stringVal, columnIndex + 1);
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
/*      */   protected float getNativeFloat(int columnIndex)
/*      */     throws SQLException
/*      */   {
/* 4083 */     checkRowPos();
/* 4084 */     checkColumnBounds(columnIndex);
/*      */     
/* 4086 */     columnIndex--;
/*      */     
/* 4088 */     if (this.thisRow.isNull(columnIndex)) {
/* 4089 */       this.wasNullFlag = true;
/*      */       
/* 4091 */       return 0.0F;
/*      */     }
/*      */     
/* 4094 */     this.wasNullFlag = false;
/*      */     
/* 4096 */     Field f = this.fields[columnIndex];
/*      */     long valueAsLong;
/* 4098 */     switch (f.getMysqlType()) {
/*      */     case 16: 
/* 4100 */       valueAsLong = getNumericRepresentationOfSQLBitType(columnIndex + 1);
/*      */       
/* 4102 */       return (float)valueAsLong;
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     case 5: 
/* 4109 */       Double valueAsDouble = new Double(getNativeDouble(columnIndex + 1));
/*      */       
/* 4111 */       float valueAsFloat = valueAsDouble.floatValue();
/*      */       
/* 4113 */       if (((this.jdbcCompliantTruncationForReads) && (valueAsFloat == Float.NEGATIVE_INFINITY)) || (valueAsFloat == Float.POSITIVE_INFINITY))
/*      */       {
/*      */ 
/* 4116 */         throwRangeException(valueAsDouble.toString(), columnIndex + 1, 6);
/*      */       }
/*      */       
/*      */ 
/* 4120 */       return (float)getNativeDouble(columnIndex + 1);
/*      */     case 1: 
/* 4122 */       if (!f.isUnsigned()) {
/* 4123 */         return getNativeByte(columnIndex + 1);
/*      */       }
/*      */       
/* 4126 */       return getNativeShort(columnIndex + 1);
/*      */     case 2: 
/*      */     case 13: 
/* 4129 */       if (!f.isUnsigned()) {
/* 4130 */         return getNativeShort(columnIndex + 1);
/*      */       }
/*      */       
/* 4133 */       return getNativeInt(columnIndex + 1);
/*      */     case 3: 
/*      */     case 9: 
/* 4136 */       if (!f.isUnsigned()) {
/* 4137 */         return getNativeInt(columnIndex + 1);
/*      */       }
/*      */       
/* 4140 */       return (float)getNativeLong(columnIndex + 1);
/*      */     case 8: 
/* 4142 */       valueAsLong = getNativeLong(columnIndex + 1);
/*      */       
/* 4144 */       if (!f.isUnsigned()) {
/* 4145 */         return (float)valueAsLong;
/*      */       }
/*      */       
/* 4148 */       BigInteger asBigInt = convertLongToUlong(valueAsLong);
/*      */       
/*      */ 
/*      */ 
/* 4152 */       return asBigInt.floatValue();
/*      */     
/*      */     case 4: 
/* 4155 */       return this.thisRow.getNativeFloat(columnIndex);
/*      */     }
/*      */     
/* 4158 */     String stringVal = getNativeString(columnIndex + 1);
/*      */     
/* 4160 */     if (this.useUsageAdvisor) {
/* 4161 */       issueConversionViaParsingWarning("getFloat()", columnIndex, stringVal, this.fields[columnIndex], new int[] { 5, 1, 2, 3, 8, 4 });
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 4171 */     return getFloatFromString(stringVal, columnIndex + 1);
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
/*      */   protected int getNativeInt(int columnIndex)
/*      */     throws SQLException
/*      */   {
/* 4187 */     return getNativeInt(columnIndex, true);
/*      */   }
/*      */   
/*      */   protected int getNativeInt(int columnIndex, boolean overflowCheck) throws SQLException {
/* 4191 */     checkRowPos();
/* 4192 */     checkColumnBounds(columnIndex);
/*      */     
/* 4194 */     columnIndex--;
/*      */     
/* 4196 */     if (this.thisRow.isNull(columnIndex)) {
/* 4197 */       this.wasNullFlag = true;
/*      */       
/* 4199 */       return 0;
/*      */     }
/*      */     
/* 4202 */     this.wasNullFlag = false;
/*      */     
/* 4204 */     Field f = this.fields[columnIndex];
/*      */     long valueAsLong;
/* 4206 */     double valueAsDouble; switch (f.getMysqlType()) {
/*      */     case 16: 
/* 4208 */       valueAsLong = getNumericRepresentationOfSQLBitType(columnIndex + 1);
/*      */       
/* 4210 */       if ((overflowCheck) && (this.jdbcCompliantTruncationForReads) && ((valueAsLong < -2147483648L) || (valueAsLong > 2147483647L)))
/*      */       {
/*      */ 
/* 4213 */         throwRangeException(String.valueOf(valueAsLong), columnIndex + 1, 4);
/*      */       }
/*      */       
/*      */ 
/* 4217 */       return (short)(int)valueAsLong;
/*      */     case 1: 
/* 4219 */       byte tinyintVal = getNativeByte(columnIndex + 1, false);
/*      */       
/* 4221 */       if ((!f.isUnsigned()) || (tinyintVal >= 0)) {
/* 4222 */         return tinyintVal;
/*      */       }
/*      */       
/* 4225 */       return tinyintVal + 256;
/*      */     case 2: 
/*      */     case 13: 
/* 4228 */       short asShort = getNativeShort(columnIndex + 1, false);
/*      */       
/* 4230 */       if ((!f.isUnsigned()) || (asShort >= 0)) {
/* 4231 */         return asShort;
/*      */       }
/*      */       
/* 4234 */       return asShort + 65536;
/*      */     
/*      */     case 3: 
/*      */     case 9: 
/* 4238 */       int valueAsInt = this.thisRow.getNativeInt(columnIndex);
/*      */       
/* 4240 */       if (!f.isUnsigned()) {
/* 4241 */         return valueAsInt;
/*      */       }
/*      */       
/* 4244 */       valueAsLong = valueAsInt >= 0 ? valueAsInt : valueAsInt + 4294967296L;
/*      */       
/*      */ 
/* 4247 */       if ((overflowCheck) && (this.jdbcCompliantTruncationForReads) && (valueAsLong > 2147483647L))
/*      */       {
/* 4249 */         throwRangeException(String.valueOf(valueAsLong), columnIndex + 1, 4);
/*      */       }
/*      */       
/*      */ 
/* 4253 */       return (int)valueAsLong;
/*      */     case 8: 
/* 4255 */       valueAsLong = getNativeLong(columnIndex + 1, false, true);
/*      */       
/* 4257 */       if ((overflowCheck) && (this.jdbcCompliantTruncationForReads) && (
/* 4258 */         (valueAsLong < -2147483648L) || (valueAsLong > 2147483647L)))
/*      */       {
/* 4260 */         throwRangeException(String.valueOf(valueAsLong), columnIndex + 1, 4);
/*      */       }
/*      */       
/*      */ 
/*      */ 
/* 4265 */       return (int)valueAsLong;
/*      */     case 5: 
/* 4267 */       valueAsDouble = getNativeDouble(columnIndex + 1);
/*      */       
/* 4269 */       if ((overflowCheck) && (this.jdbcCompliantTruncationForReads) && (
/* 4270 */         (valueAsDouble < -2.147483648E9D) || (valueAsDouble > 2.147483647E9D)))
/*      */       {
/* 4272 */         throwRangeException(String.valueOf(valueAsDouble), columnIndex + 1, 4);
/*      */       }
/*      */       
/*      */ 
/*      */ 
/* 4277 */       return (int)valueAsDouble;
/*      */     case 4: 
/* 4279 */       valueAsDouble = getNativeFloat(columnIndex + 1);
/*      */       
/* 4281 */       if ((overflowCheck) && (this.jdbcCompliantTruncationForReads) && (
/* 4282 */         (valueAsDouble < -2.147483648E9D) || (valueAsDouble > 2.147483647E9D)))
/*      */       {
/* 4284 */         throwRangeException(String.valueOf(valueAsDouble), columnIndex + 1, 4);
/*      */       }
/*      */       
/*      */ 
/*      */ 
/* 4289 */       return (int)valueAsDouble;
/*      */     }
/*      */     
/* 4292 */     String stringVal = getNativeString(columnIndex + 1);
/*      */     
/* 4294 */     if (this.useUsageAdvisor) {
/* 4295 */       issueConversionViaParsingWarning("getInt()", columnIndex, stringVal, this.fields[columnIndex], new int[] { 5, 1, 2, 3, 8, 4 });
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 4305 */     return getIntFromString(stringVal, columnIndex + 1);
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
/*      */   protected long getNativeLong(int columnIndex)
/*      */     throws SQLException
/*      */   {
/* 4321 */     return getNativeLong(columnIndex, true, true);
/*      */   }
/*      */   
/*      */   protected long getNativeLong(int columnIndex, boolean overflowCheck, boolean expandUnsignedLong) throws SQLException
/*      */   {
/* 4326 */     checkRowPos();
/* 4327 */     checkColumnBounds(columnIndex);
/*      */     
/* 4329 */     columnIndex--;
/*      */     
/* 4331 */     if (this.thisRow.isNull(columnIndex)) {
/* 4332 */       this.wasNullFlag = true;
/*      */       
/* 4334 */       return 0L;
/*      */     }
/*      */     
/* 4337 */     this.wasNullFlag = false;
/*      */     
/* 4339 */     Field f = this.fields[columnIndex];
/*      */     double valueAsDouble;
/* 4341 */     switch (f.getMysqlType()) {
/*      */     case 16: 
/* 4343 */       return getNumericRepresentationOfSQLBitType(columnIndex + 1);
/*      */     case 1: 
/* 4345 */       if (!f.isUnsigned()) {
/* 4346 */         return getNativeByte(columnIndex + 1);
/*      */       }
/*      */       
/* 4349 */       return getNativeInt(columnIndex + 1);
/*      */     case 2: 
/* 4351 */       if (!f.isUnsigned()) {
/* 4352 */         return getNativeShort(columnIndex + 1);
/*      */       }
/*      */       
/* 4355 */       return getNativeInt(columnIndex + 1, false);
/*      */     
/*      */     case 13: 
/* 4358 */       return getNativeShort(columnIndex + 1);
/*      */     case 3: 
/*      */     case 9: 
/* 4361 */       int asInt = getNativeInt(columnIndex + 1, false);
/*      */       
/* 4363 */       if ((!f.isUnsigned()) || (asInt >= 0)) {
/* 4364 */         return asInt;
/*      */       }
/*      */       
/* 4367 */       return asInt + 4294967296L;
/*      */     case 8: 
/* 4369 */       long valueAsLong = this.thisRow.getNativeLong(columnIndex);
/*      */       
/* 4371 */       if ((!f.isUnsigned()) || (!expandUnsignedLong)) {
/* 4372 */         return valueAsLong;
/*      */       }
/*      */       
/* 4375 */       BigInteger asBigInt = convertLongToUlong(valueAsLong);
/*      */       
/* 4377 */       if ((overflowCheck) && (this.jdbcCompliantTruncationForReads) && ((asBigInt.compareTo(new BigInteger(String.valueOf(Long.MAX_VALUE))) > 0) || (asBigInt.compareTo(new BigInteger(String.valueOf(Long.MIN_VALUE))) < 0)))
/*      */       {
/*      */ 
/* 4380 */         throwRangeException(asBigInt.toString(), columnIndex + 1, -5);
/*      */       }
/*      */       
/*      */ 
/* 4384 */       return getLongFromString(asBigInt.toString(), columnIndex);
/*      */     
/*      */     case 5: 
/* 4387 */       valueAsDouble = getNativeDouble(columnIndex + 1);
/*      */       
/* 4389 */       if ((overflowCheck) && (this.jdbcCompliantTruncationForReads) && (
/* 4390 */         (valueAsDouble < -9.223372036854776E18D) || (valueAsDouble > 9.223372036854776E18D)))
/*      */       {
/* 4392 */         throwRangeException(String.valueOf(valueAsDouble), columnIndex + 1, -5);
/*      */       }
/*      */       
/*      */ 
/*      */ 
/* 4397 */       return valueAsDouble;
/*      */     case 4: 
/* 4399 */       valueAsDouble = getNativeFloat(columnIndex + 1);
/*      */       
/* 4401 */       if ((overflowCheck) && (this.jdbcCompliantTruncationForReads) && (
/* 4402 */         (valueAsDouble < -9.223372036854776E18D) || (valueAsDouble > 9.223372036854776E18D)))
/*      */       {
/* 4404 */         throwRangeException(String.valueOf(valueAsDouble), columnIndex + 1, -5);
/*      */       }
/*      */       
/*      */ 
/*      */ 
/* 4409 */       return valueAsDouble;
/*      */     }
/* 4411 */     String stringVal = getNativeString(columnIndex + 1);
/*      */     
/* 4413 */     if (this.useUsageAdvisor) {
/* 4414 */       issueConversionViaParsingWarning("getLong()", columnIndex, stringVal, this.fields[columnIndex], new int[] { 5, 1, 2, 3, 8, 4 });
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 4424 */     return getLongFromString(stringVal, columnIndex + 1);
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
/*      */   protected Ref getNativeRef(int i)
/*      */     throws SQLException
/*      */   {
/* 4442 */     throw SQLError.notImplemented();
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
/*      */   protected short getNativeShort(int columnIndex)
/*      */     throws SQLException
/*      */   {
/* 4457 */     return getNativeShort(columnIndex, true);
/*      */   }
/*      */   
/*      */   protected short getNativeShort(int columnIndex, boolean overflowCheck) throws SQLException {
/* 4461 */     checkRowPos();
/* 4462 */     checkColumnBounds(columnIndex);
/*      */     
/* 4464 */     columnIndex--;
/*      */     
/*      */ 
/* 4467 */     if (this.thisRow.isNull(columnIndex)) {
/* 4468 */       this.wasNullFlag = true;
/*      */       
/* 4470 */       return 0;
/*      */     }
/*      */     
/* 4473 */     this.wasNullFlag = false;
/*      */     
/* 4475 */     Field f = this.fields[columnIndex];
/*      */     int valueAsInt;
/* 4477 */     long valueAsLong; switch (f.getMysqlType())
/*      */     {
/*      */     case 1: 
/* 4480 */       byte tinyintVal = getNativeByte(columnIndex + 1, false);
/*      */       
/* 4482 */       if ((!f.isUnsigned()) || (tinyintVal >= 0)) {
/* 4483 */         return (short)tinyintVal;
/*      */       }
/*      */       
/* 4486 */       return (short)(tinyintVal + 256);
/*      */     
/*      */     case 2: 
/*      */     case 13: 
/* 4490 */       short asShort = this.thisRow.getNativeShort(columnIndex);
/*      */       
/* 4492 */       if (!f.isUnsigned()) {
/* 4493 */         return asShort;
/*      */       }
/*      */       
/* 4496 */       valueAsInt = asShort & 0xFFFF;
/*      */       
/* 4498 */       if ((overflowCheck) && (this.jdbcCompliantTruncationForReads) && (valueAsInt > 32767))
/*      */       {
/* 4500 */         throwRangeException(String.valueOf(valueAsInt), columnIndex + 1, 5);
/*      */       }
/*      */       
/*      */ 
/* 4504 */       return (short)valueAsInt;
/*      */     case 3: 
/*      */     case 9: 
/* 4507 */       if (!f.isUnsigned()) {
/* 4508 */         valueAsInt = getNativeInt(columnIndex + 1, false);
/*      */         
/* 4510 */         if (((overflowCheck) && (this.jdbcCompliantTruncationForReads) && (valueAsInt > 32767)) || (valueAsInt < 32768))
/*      */         {
/*      */ 
/* 4513 */           throwRangeException(String.valueOf(valueAsInt), columnIndex + 1, 5);
/*      */         }
/*      */         
/*      */ 
/* 4517 */         return (short)valueAsInt;
/*      */       }
/*      */       
/* 4520 */       valueAsLong = getNativeLong(columnIndex + 1, false, true);
/*      */       
/* 4522 */       if ((overflowCheck) && (this.jdbcCompliantTruncationForReads) && (valueAsLong > 32767L))
/*      */       {
/* 4524 */         throwRangeException(String.valueOf(valueAsLong), columnIndex + 1, 5);
/*      */       }
/*      */       
/*      */ 
/* 4528 */       return (short)(int)valueAsLong;
/*      */     
/*      */     case 8: 
/* 4531 */       valueAsLong = getNativeLong(columnIndex + 1, false, false);
/*      */       
/* 4533 */       if (!f.isUnsigned()) {
/* 4534 */         if ((overflowCheck) && (this.jdbcCompliantTruncationForReads) && (
/* 4535 */           (valueAsLong < -32768L) || (valueAsLong > 32767L)))
/*      */         {
/* 4537 */           throwRangeException(String.valueOf(valueAsLong), columnIndex + 1, 5);
/*      */         }
/*      */         
/*      */ 
/*      */ 
/* 4542 */         return (short)(int)valueAsLong;
/*      */       }
/*      */       
/* 4545 */       BigInteger asBigInt = convertLongToUlong(valueAsLong);
/*      */       
/* 4547 */       if ((overflowCheck) && (this.jdbcCompliantTruncationForReads) && ((asBigInt.compareTo(new BigInteger(String.valueOf(32767))) > 0) || (asBigInt.compareTo(new BigInteger(String.valueOf(32768))) < 0)))
/*      */       {
/*      */ 
/* 4550 */         throwRangeException(asBigInt.toString(), columnIndex + 1, 5);
/*      */       }
/*      */       
/*      */ 
/* 4554 */       return (short)getIntFromString(asBigInt.toString(), columnIndex + 1);
/*      */     
/*      */     case 5: 
/* 4557 */       double valueAsDouble = getNativeDouble(columnIndex + 1);
/*      */       
/* 4559 */       if ((overflowCheck) && (this.jdbcCompliantTruncationForReads) && (
/* 4560 */         (valueAsDouble < -32768.0D) || (valueAsDouble > 32767.0D)))
/*      */       {
/* 4562 */         throwRangeException(String.valueOf(valueAsDouble), columnIndex + 1, 5);
/*      */       }
/*      */       
/*      */ 
/*      */ 
/* 4567 */       return (short)(int)valueAsDouble;
/*      */     case 4: 
/* 4569 */       float valueAsFloat = getNativeFloat(columnIndex + 1);
/*      */       
/* 4571 */       if ((overflowCheck) && (this.jdbcCompliantTruncationForReads) && (
/* 4572 */         (valueAsFloat < -32768.0F) || (valueAsFloat > 32767.0F)))
/*      */       {
/* 4574 */         throwRangeException(String.valueOf(valueAsFloat), columnIndex + 1, 5);
/*      */       }
/*      */       
/*      */ 
/*      */ 
/* 4579 */       return (short)(int)valueAsFloat;
/*      */     }
/* 4581 */     String stringVal = getNativeString(columnIndex + 1);
/*      */     
/* 4583 */     if (this.useUsageAdvisor) {
/* 4584 */       issueConversionViaParsingWarning("getShort()", columnIndex, stringVal, this.fields[columnIndex], new int[] { 5, 1, 2, 3, 8, 4 });
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 4594 */     return getShortFromString(stringVal, columnIndex + 1);
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
/*      */   protected String getNativeString(int columnIndex)
/*      */     throws SQLException
/*      */   {
/* 4610 */     checkRowPos();
/* 4611 */     checkColumnBounds(columnIndex);
/*      */     
/* 4613 */     if (this.fields == null) {
/* 4614 */       throw SQLError.createSQLException(Messages.getString("ResultSet.Query_generated_no_fields_for_ResultSet_133"), "S1002", getExceptionInterceptor());
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*      */ 
/* 4620 */     if (this.thisRow.isNull(columnIndex - 1)) {
/* 4621 */       this.wasNullFlag = true;
/*      */       
/* 4623 */       return null;
/*      */     }
/*      */     
/* 4626 */     this.wasNullFlag = false;
/*      */     
/* 4628 */     String stringVal = null;
/*      */     
/* 4630 */     Field field = this.fields[(columnIndex - 1)];
/*      */     
/*      */ 
/* 4633 */     stringVal = getNativeConvertToString(columnIndex, field);
/* 4634 */     int mysqlType = field.getMysqlType();
/*      */     
/* 4636 */     if ((mysqlType != 7) && (mysqlType != 10) && (field.isZeroFill()) && (stringVal != null))
/*      */     {
/*      */ 
/* 4639 */       int origLength = stringVal.length();
/*      */       
/* 4641 */       StringBuffer zeroFillBuf = new StringBuffer(origLength);
/*      */       
/* 4643 */       long numZeros = field.getLength() - origLength;
/*      */       
/* 4645 */       for (long i = 0L; i < numZeros; i += 1L) {
/* 4646 */         zeroFillBuf.append('0');
/*      */       }
/*      */       
/* 4649 */       zeroFillBuf.append(stringVal);
/*      */       
/* 4651 */       stringVal = zeroFillBuf.toString();
/*      */     }
/*      */     
/* 4654 */     return stringVal;
/*      */   }
/*      */   
/*      */   private Time getNativeTime(int columnIndex, Calendar targetCalendar, TimeZone tz, boolean rollForward)
/*      */     throws SQLException
/*      */   {
/* 4660 */     checkRowPos();
/* 4661 */     checkColumnBounds(columnIndex);
/*      */     
/* 4663 */     int columnIndexMinusOne = columnIndex - 1;
/*      */     
/* 4665 */     int mysqlType = this.fields[columnIndexMinusOne].getMysqlType();
/*      */     
/* 4667 */     Time timeVal = null;
/*      */     
/* 4669 */     if (mysqlType == 11) {
/* 4670 */       timeVal = this.thisRow.getNativeTime(columnIndexMinusOne, targetCalendar, tz, rollForward, this.connection, this);
/*      */     }
/*      */     else
/*      */     {
/* 4674 */       timeVal = (Time)this.thisRow.getNativeDateTimeValue(columnIndexMinusOne, null, 92, mysqlType, tz, rollForward, this.connection, this);
/*      */     }
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
/* 4686 */     if (timeVal == null)
/*      */     {
/* 4688 */       this.wasNullFlag = true;
/*      */       
/* 4690 */       return null;
/*      */     }
/*      */     
/* 4693 */     this.wasNullFlag = false;
/*      */     
/* 4695 */     return timeVal;
/*      */   }
/*      */   
/*      */   Time getNativeTimeViaParseConversion(int columnIndex, Calendar targetCalendar, TimeZone tz, boolean rollForward) throws SQLException
/*      */   {
/* 4700 */     if (this.useUsageAdvisor) {
/* 4701 */       issueConversionViaParsingWarning("getTime()", columnIndex, this.thisRow.getColumnValue(columnIndex - 1), this.fields[(columnIndex - 1)], new int[] { 11 });
/*      */     }
/*      */     
/*      */ 
/*      */ 
/* 4706 */     String strTime = getNativeString(columnIndex);
/*      */     
/* 4708 */     return getTimeFromString(strTime, targetCalendar, columnIndex, tz, rollForward);
/*      */   }
/*      */   
/*      */ 
/*      */   private Timestamp getNativeTimestamp(int columnIndex, Calendar targetCalendar, TimeZone tz, boolean rollForward)
/*      */     throws SQLException
/*      */   {
/* 4715 */     checkRowPos();
/* 4716 */     checkColumnBounds(columnIndex);
/*      */     
/* 4718 */     int columnIndexMinusOne = columnIndex - 1;
/*      */     
/* 4720 */     Timestamp tsVal = null;
/*      */     
/* 4722 */     int mysqlType = this.fields[columnIndexMinusOne].getMysqlType();
/*      */     
/* 4724 */     switch (mysqlType) {
/*      */     case 7: 
/*      */     case 12: 
/* 4727 */       tsVal = this.thisRow.getNativeTimestamp(columnIndexMinusOne, targetCalendar, tz, rollForward, this.connection, this);
/*      */       
/* 4729 */       break;
/*      */     
/*      */ 
/*      */ 
/*      */     default: 
/* 4734 */       tsVal = (Timestamp)this.thisRow.getNativeDateTimeValue(columnIndexMinusOne, null, 93, mysqlType, tz, rollForward, this.connection, this);
/*      */     }
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
/* 4746 */     if (tsVal == null)
/*      */     {
/* 4748 */       this.wasNullFlag = true;
/*      */       
/* 4750 */       return null;
/*      */     }
/*      */     
/* 4753 */     this.wasNullFlag = false;
/*      */     
/* 4755 */     return tsVal;
/*      */   }
/*      */   
/*      */   Timestamp getNativeTimestampViaParseConversion(int columnIndex, Calendar targetCalendar, TimeZone tz, boolean rollForward) throws SQLException
/*      */   {
/* 4760 */     if (this.useUsageAdvisor) {
/* 4761 */       issueConversionViaParsingWarning("getTimestamp()", columnIndex, this.thisRow.getColumnValue(columnIndex - 1), this.fields[(columnIndex - 1)], new int[] { 7, 12 });
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*      */ 
/* 4767 */     String strTimestamp = getNativeString(columnIndex);
/*      */     
/* 4769 */     return getTimestampFromString(columnIndex, targetCalendar, strTimestamp, tz, rollForward);
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
/*      */   protected InputStream getNativeUnicodeStream(int columnIndex)
/*      */     throws SQLException
/*      */   {
/* 4796 */     checkRowPos();
/*      */     
/* 4798 */     return getBinaryStream(columnIndex);
/*      */   }
/*      */   
/*      */ 
/*      */   protected URL getNativeURL(int colIndex)
/*      */     throws SQLException
/*      */   {
/* 4805 */     String val = getString(colIndex);
/*      */     
/* 4807 */     if (val == null) {
/* 4808 */       return null;
/*      */     }
/*      */     try
/*      */     {
/* 4812 */       return new URL(val);
/*      */     } catch (MalformedURLException mfe) {
/* 4814 */       throw SQLError.createSQLException(Messages.getString("ResultSet.Malformed_URL____141") + val + "'", "S1009", getExceptionInterceptor());
/*      */     }
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public ResultSetInternalMethods getNextResultSet()
/*      */   {
/* 4826 */     return this.nextResultSet;
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
/*      */   public Object getObject(int columnIndex)
/*      */     throws SQLException
/*      */   {
/* 4853 */     checkRowPos();
/* 4854 */     checkColumnBounds(columnIndex);
/*      */     
/* 4856 */     int columnIndexMinusOne = columnIndex - 1;
/*      */     
/* 4858 */     if (this.thisRow.isNull(columnIndexMinusOne)) {
/* 4859 */       this.wasNullFlag = true;
/*      */       
/* 4861 */       return null;
/*      */     }
/*      */     
/* 4864 */     this.wasNullFlag = false;
/*      */     
/*      */ 
/* 4867 */     Field field = this.fields[columnIndexMinusOne];
/*      */     String stringVal;
/* 4869 */     switch (field.getSQLType()) {
/*      */     case -7: 
/*      */     case 16: 
/* 4872 */       if ((field.getMysqlType() == 16) && (!field.isSingleBit()))
/*      */       {
/* 4874 */         return getBytes(columnIndex);
/*      */       }
/*      */       
/*      */ 
/*      */ 
/*      */ 
/* 4880 */       return Boolean.valueOf(getBoolean(columnIndex));
/*      */     
/*      */     case -6: 
/* 4883 */       if (!field.isUnsigned()) {
/* 4884 */         return Constants.integerValueOf(getByte(columnIndex));
/*      */       }
/*      */       
/* 4887 */       return Constants.integerValueOf(getInt(columnIndex));
/*      */     
/*      */ 
/*      */     case 5: 
/* 4891 */       return Constants.integerValueOf(getInt(columnIndex));
/*      */     
/*      */ 
/*      */     case 4: 
/* 4895 */       if ((!field.isUnsigned()) || (field.getMysqlType() == 9))
/*      */       {
/* 4897 */         return Constants.integerValueOf(getInt(columnIndex));
/*      */       }
/*      */       
/* 4900 */       return Constants.longValueOf(getLong(columnIndex));
/*      */     
/*      */ 
/*      */     case -5: 
/* 4904 */       if (!field.isUnsigned()) {
/* 4905 */         return Constants.longValueOf(getLong(columnIndex));
/*      */       }
/*      */       
/* 4908 */       stringVal = getString(columnIndex);
/*      */       
/* 4910 */       if (stringVal == null) {
/* 4911 */         return null;
/*      */       }
/*      */       try
/*      */       {
/* 4915 */         return new BigInteger(stringVal);
/*      */       } catch (NumberFormatException nfe) {
/* 4917 */         throw SQLError.createSQLException(Messages.getString("ResultSet.Bad_format_for_BigInteger", new Object[] { Constants.integerValueOf(columnIndex), stringVal }), "S1009", getExceptionInterceptor());
/*      */       }
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*      */     case 2: 
/*      */     case 3: 
/* 4925 */       stringVal = getString(columnIndex);
/*      */       
/*      */ 
/*      */ 
/* 4929 */       if (stringVal != null) {
/* 4930 */         if (stringVal.length() == 0) {
/* 4931 */           BigDecimal val = new BigDecimal(0);
/*      */           
/* 4933 */           return val;
/*      */         }
/*      */         BigDecimal val;
/*      */         try {
/* 4937 */           val = new BigDecimal(stringVal);
/*      */         } catch (NumberFormatException ex) {
/* 4939 */           throw SQLError.createSQLException(Messages.getString("ResultSet.Bad_format_for_BigDecimal", new Object[] { stringVal, new Integer(columnIndex) }), "S1009", getExceptionInterceptor());
/*      */         }
/*      */         
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 4946 */         return val;
/*      */       }
/*      */       
/* 4949 */       return null;
/*      */     
/*      */     case 7: 
/* 4952 */       return new Float(getFloat(columnIndex));
/*      */     
/*      */     case 6: 
/*      */     case 8: 
/* 4956 */       return new Double(getDouble(columnIndex));
/*      */     
/*      */     case 1: 
/*      */     case 12: 
/* 4960 */       if (!field.isOpaqueBinary()) {
/* 4961 */         return getString(columnIndex);
/*      */       }
/*      */       
/* 4964 */       return getBytes(columnIndex);
/*      */     case -1: 
/* 4966 */       if (!field.isOpaqueBinary()) {
/* 4967 */         return getStringForClob(columnIndex);
/*      */       }
/*      */       
/* 4970 */       return getBytes(columnIndex);
/*      */     
/*      */     case -4: 
/*      */     case -3: 
/*      */     case -2: 
/* 4975 */       if (field.getMysqlType() == 255)
/* 4976 */         return getBytes(columnIndex);
/* 4977 */       if ((field.isBinary()) || (field.isBlob())) {
/* 4978 */         byte[] data = getBytes(columnIndex);
/*      */         
/* 4980 */         if (this.connection.getAutoDeserialize()) {
/* 4981 */           Object obj = data;
/*      */           
/* 4983 */           if ((data != null) && (data.length >= 2)) {
/* 4984 */             if ((data[0] == -84) && (data[1] == -19)) {
/*      */               try
/*      */               {
/* 4987 */                 ByteArrayInputStream bytesIn = new ByteArrayInputStream(data);
/*      */                 
/* 4989 */                 ObjectInputStream objIn = new ObjectInputStream(bytesIn);
/*      */                 
/* 4991 */                 obj = objIn.readObject();
/* 4992 */                 objIn.close();
/* 4993 */                 bytesIn.close();
/*      */               } catch (ClassNotFoundException cnfe) {
/* 4995 */                 throw SQLError.createSQLException(Messages.getString("ResultSet.Class_not_found___91") + cnfe.toString() + Messages.getString("ResultSet._while_reading_serialized_object_92"), getExceptionInterceptor());
/*      */ 
/*      */ 
/*      */               }
/*      */               catch (IOException ex)
/*      */               {
/*      */ 
/* 5002 */                 obj = data;
/*      */               }
/*      */             } else {
/* 5005 */               return getString(columnIndex);
/*      */             }
/*      */           }
/*      */           
/* 5009 */           return obj;
/*      */         }
/*      */         
/* 5012 */         return data;
/*      */       }
/*      */       
/* 5015 */       return getBytes(columnIndex);
/*      */     
/*      */     case 91: 
/* 5018 */       if ((field.getMysqlType() == 13) && (!this.connection.getYearIsDateType()))
/*      */       {
/* 5020 */         return Constants.shortValueOf(getShort(columnIndex));
/*      */       }
/*      */       
/* 5023 */       return getDate(columnIndex);
/*      */     
/*      */     case 92: 
/* 5026 */       return getTime(columnIndex);
/*      */     
/*      */     case 93: 
/* 5029 */       return getTimestamp(columnIndex);
/*      */     }
/*      */     
/* 5032 */     return getString(columnIndex);
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
/*      */   public Object getObject(int i, Map map)
/*      */     throws SQLException
/*      */   {
/* 5052 */     return getObject(i);
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
/*      */   public Object getObject(String columnName)
/*      */     throws SQLException
/*      */   {
/* 5079 */     return getObject(findColumn(columnName));
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
/*      */   public Object getObject(String colName, Map map)
/*      */     throws SQLException
/*      */   {
/* 5099 */     return getObject(findColumn(colName), map);
/*      */   }
/*      */   
/*      */   public Object getObjectStoredProc(int columnIndex, int desiredSqlType) throws SQLException
/*      */   {
/* 5104 */     checkRowPos();
/* 5105 */     checkColumnBounds(columnIndex);
/*      */     
/* 5107 */     Object value = this.thisRow.getColumnValue(columnIndex - 1);
/*      */     
/* 5109 */     if (value == null) {
/* 5110 */       this.wasNullFlag = true;
/*      */       
/* 5112 */       return null;
/*      */     }
/*      */     
/* 5115 */     this.wasNullFlag = false;
/*      */     
/*      */ 
/* 5118 */     Field field = this.fields[(columnIndex - 1)];
/*      */     
/* 5120 */     switch (desiredSqlType)
/*      */     {
/*      */ 
/*      */ 
/*      */     case -7: 
/*      */     case 16: 
/* 5126 */       return Boolean.valueOf(getBoolean(columnIndex));
/*      */     
/*      */     case -6: 
/* 5129 */       return Constants.integerValueOf(getInt(columnIndex));
/*      */     
/*      */     case 5: 
/* 5132 */       return Constants.integerValueOf(getInt(columnIndex));
/*      */     
/*      */ 
/*      */     case 4: 
/* 5136 */       if ((!field.isUnsigned()) || (field.getMysqlType() == 9))
/*      */       {
/* 5138 */         return Constants.integerValueOf(getInt(columnIndex));
/*      */       }
/*      */       
/* 5141 */       return Constants.longValueOf(getLong(columnIndex));
/*      */     
/*      */ 
/*      */     case -5: 
/* 5145 */       if (field.isUnsigned()) {
/* 5146 */         return getBigDecimal(columnIndex);
/*      */       }
/*      */       
/* 5149 */       return Constants.longValueOf(getLong(columnIndex));
/*      */     
/*      */ 
/*      */     case 2: 
/*      */     case 3: 
/* 5154 */       String stringVal = getString(columnIndex);
/*      */       
/*      */ 
/* 5157 */       if (stringVal != null) {
/* 5158 */         if (stringVal.length() == 0) {
/* 5159 */           BigDecimal val = new BigDecimal(0);
/*      */           
/* 5161 */           return val;
/*      */         }
/*      */         BigDecimal val;
/*      */         try {
/* 5165 */           val = new BigDecimal(stringVal);
/*      */         } catch (NumberFormatException ex) {
/* 5167 */           throw SQLError.createSQLException(Messages.getString("ResultSet.Bad_format_for_BigDecimal", new Object[] { stringVal, new Integer(columnIndex) }), "S1009", getExceptionInterceptor());
/*      */         }
/*      */         
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 5174 */         return val;
/*      */       }
/*      */       
/* 5177 */       return null;
/*      */     
/*      */     case 7: 
/* 5180 */       return new Float(getFloat(columnIndex));
/*      */     
/*      */ 
/*      */     case 6: 
/* 5184 */       if (!this.connection.getRunningCTS13()) {
/* 5185 */         return new Double(getFloat(columnIndex));
/*      */       }
/* 5187 */       return new Float(getFloat(columnIndex));
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     case 8: 
/* 5194 */       return new Double(getDouble(columnIndex));
/*      */     
/*      */     case 1: 
/*      */     case 12: 
/* 5198 */       return getString(columnIndex);
/*      */     case -1: 
/* 5200 */       return getStringForClob(columnIndex);
/*      */     case -4: 
/*      */     case -3: 
/*      */     case -2: 
/* 5204 */       return getBytes(columnIndex);
/*      */     
/*      */     case 91: 
/* 5207 */       if ((field.getMysqlType() == 13) && (!this.connection.getYearIsDateType()))
/*      */       {
/* 5209 */         return Constants.shortValueOf(getShort(columnIndex));
/*      */       }
/*      */       
/* 5212 */       return getDate(columnIndex);
/*      */     
/*      */     case 92: 
/* 5215 */       return getTime(columnIndex);
/*      */     
/*      */     case 93: 
/* 5218 */       return getTimestamp(columnIndex);
/*      */     }
/*      */     
/* 5221 */     return getString(columnIndex);
/*      */   }
/*      */   
/*      */   public Object getObjectStoredProc(int i, Map map, int desiredSqlType)
/*      */     throws SQLException
/*      */   {
/* 5227 */     return getObjectStoredProc(i, desiredSqlType);
/*      */   }
/*      */   
/*      */   public Object getObjectStoredProc(String columnName, int desiredSqlType) throws SQLException
/*      */   {
/* 5232 */     return getObjectStoredProc(findColumn(columnName), desiredSqlType);
/*      */   }
/*      */   
/*      */   public Object getObjectStoredProc(String colName, Map map, int desiredSqlType) throws SQLException
/*      */   {
/* 5237 */     return getObjectStoredProc(findColumn(colName), map, desiredSqlType);
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
/*      */   public Ref getRef(int i)
/*      */     throws SQLException
/*      */   {
/* 5254 */     checkColumnBounds(i);
/* 5255 */     throw SQLError.notImplemented();
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
/*      */   public Ref getRef(String colName)
/*      */     throws SQLException
/*      */   {
/* 5272 */     return getRef(findColumn(colName));
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
/*      */   public int getRow()
/*      */     throws SQLException
/*      */   {
/* 5289 */     checkClosed();
/*      */     
/* 5291 */     int currentRowNumber = this.rowData.getCurrentRowNumber();
/* 5292 */     int row = 0;
/*      */     
/*      */ 
/*      */ 
/* 5296 */     if (!this.rowData.isDynamic()) {
/* 5297 */       if ((currentRowNumber < 0) || (this.rowData.isAfterLast()) || (this.rowData.isEmpty()))
/*      */       {
/* 5299 */         row = 0;
/*      */       } else {
/* 5301 */         row = currentRowNumber + 1;
/*      */       }
/*      */     }
/*      */     else {
/* 5305 */       row = currentRowNumber + 1;
/*      */     }
/*      */     
/* 5308 */     return row;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public String getServerInfo()
/*      */   {
/* 5317 */     return this.serverInfo;
/*      */   }
/*      */   
/*      */   private long getNumericRepresentationOfSQLBitType(int columnIndex) throws SQLException
/*      */   {
/* 5322 */     Object value = this.thisRow.getColumnValue(columnIndex - 1);
/*      */     
/* 5324 */     if ((this.fields[(columnIndex - 1)].isSingleBit()) || (((byte[])value).length == 1))
/*      */     {
/* 5326 */       return ((byte[])(byte[])value)[0];
/*      */     }
/*      */     
/*      */ 
/* 5330 */     byte[] asBytes = (byte[])value;
/*      */     
/*      */ 
/* 5333 */     int shift = 0;
/*      */     
/* 5335 */     long[] steps = new long[asBytes.length];
/*      */     
/* 5337 */     for (int i = asBytes.length - 1; i >= 0; i--) {
/* 5338 */       steps[i] = ((asBytes[i] & 0xFF) << shift);
/* 5339 */       shift += 8;
/*      */     }
/*      */     
/* 5342 */     long valueAsLong = 0L;
/*      */     
/* 5344 */     for (int i = 0; i < asBytes.length; i++) {
/* 5345 */       valueAsLong |= steps[i];
/*      */     }
/*      */     
/* 5348 */     return valueAsLong;
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
/*      */   public short getShort(int columnIndex)
/*      */     throws SQLException
/*      */   {
/* 5363 */     if (!this.isBinaryEncoded) {
/* 5364 */       checkRowPos();
/*      */       
/* 5366 */       if (this.useFastIntParsing)
/*      */       {
/* 5368 */         checkColumnBounds(columnIndex);
/*      */         
/* 5370 */         Object value = this.thisRow.getColumnValue(columnIndex - 1);
/*      */         
/* 5372 */         if (value == null) {
/* 5373 */           this.wasNullFlag = true;
/*      */         } else {
/* 5375 */           this.wasNullFlag = false;
/*      */         }
/*      */         
/* 5378 */         if (this.wasNullFlag) {
/* 5379 */           return 0;
/*      */         }
/*      */         
/* 5382 */         byte[] shortAsBytes = (byte[])value;
/*      */         
/* 5384 */         if (shortAsBytes.length == 0) {
/* 5385 */           return (short)convertToZeroWithEmptyCheck();
/*      */         }
/*      */         
/* 5388 */         boolean needsFullParse = false;
/*      */         
/* 5390 */         for (int i = 0; i < shortAsBytes.length; i++) {
/* 5391 */           if (((char)shortAsBytes[i] == 'e') || ((char)shortAsBytes[i] == 'E'))
/*      */           {
/* 5393 */             needsFullParse = true;
/*      */             
/* 5395 */             break;
/*      */           }
/*      */         }
/*      */         
/* 5399 */         if (!needsFullParse) {
/*      */           try {
/* 5401 */             return parseShortWithOverflowCheck(columnIndex, shortAsBytes, null);
/*      */           }
/*      */           catch (NumberFormatException nfe)
/*      */           {
/*      */             try {
/* 5406 */               return parseShortAsDouble(columnIndex, new String(shortAsBytes));
/*      */ 
/*      */             }
/*      */             catch (NumberFormatException newNfe)
/*      */             {
/*      */ 
/* 5412 */               if (this.fields[(columnIndex - 1)].getMysqlType() == 16) {
/* 5413 */                 long valueAsLong = getNumericRepresentationOfSQLBitType(columnIndex);
/*      */                 
/* 5415 */                 if ((this.jdbcCompliantTruncationForReads) && ((valueAsLong < -32768L) || (valueAsLong > 32767L)))
/*      */                 {
/*      */ 
/* 5418 */                   throwRangeException(String.valueOf(valueAsLong), columnIndex, 5);
/*      */                 }
/*      */                 
/*      */ 
/* 5422 */                 return (short)(int)valueAsLong;
/*      */               }
/*      */               
/* 5425 */               throw SQLError.createSQLException(Messages.getString("ResultSet.Invalid_value_for_getShort()_-____96") + new String(shortAsBytes) + "'", "S1009", getExceptionInterceptor());
/*      */             }
/*      */           }
/*      */         }
/*      */       }
/*      */       
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 5435 */       String val = null;
/*      */       try
/*      */       {
/* 5438 */         val = getString(columnIndex);
/*      */         
/* 5440 */         if (val != null)
/*      */         {
/* 5442 */           if (val.length() == 0) {
/* 5443 */             return (short)convertToZeroWithEmptyCheck();
/*      */           }
/*      */           
/* 5446 */           if ((val.indexOf("e") == -1) && (val.indexOf("E") == -1) && (val.indexOf(".") == -1))
/*      */           {
/* 5448 */             return parseShortWithOverflowCheck(columnIndex, null, val);
/*      */           }
/*      */           
/*      */ 
/*      */ 
/* 5453 */           return parseShortAsDouble(columnIndex, val);
/*      */         }
/*      */         
/* 5456 */         return 0;
/*      */       } catch (NumberFormatException nfe) {
/*      */         try {
/* 5459 */           return parseShortAsDouble(columnIndex, val);
/*      */ 
/*      */         }
/*      */         catch (NumberFormatException newNfe)
/*      */         {
/* 5464 */           if (this.fields[(columnIndex - 1)].getMysqlType() == 16) {
/* 5465 */             long valueAsLong = getNumericRepresentationOfSQLBitType(columnIndex);
/*      */             
/* 5467 */             if ((this.jdbcCompliantTruncationForReads) && ((valueAsLong < -32768L) || (valueAsLong > 32767L)))
/*      */             {
/*      */ 
/* 5470 */               throwRangeException(String.valueOf(valueAsLong), columnIndex, 5);
/*      */             }
/*      */             
/*      */ 
/* 5474 */             return (short)(int)valueAsLong;
/*      */           }
/*      */           
/* 5477 */           throw SQLError.createSQLException(Messages.getString("ResultSet.Invalid_value_for_getShort()_-____96") + val + "'", "S1009", getExceptionInterceptor());
/*      */         }
/*      */       }
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*      */ 
/* 5485 */     return getNativeShort(columnIndex);
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
/*      */   public short getShort(String columnName)
/*      */     throws SQLException
/*      */   {
/* 5500 */     return getShort(findColumn(columnName));
/*      */   }
/*      */   
/*      */   private final short getShortFromString(String val, int columnIndex) throws SQLException
/*      */   {
/*      */     try {
/* 5506 */       if (val != null)
/*      */       {
/* 5508 */         if (val.length() == 0) {
/* 5509 */           return (short)convertToZeroWithEmptyCheck();
/*      */         }
/*      */         
/* 5512 */         if ((val.indexOf("e") == -1) && (val.indexOf("E") == -1) && (val.indexOf(".") == -1))
/*      */         {
/* 5514 */           return parseShortWithOverflowCheck(columnIndex, null, val);
/*      */         }
/*      */         
/*      */ 
/* 5518 */         return parseShortAsDouble(columnIndex, val);
/*      */       }
/*      */       
/* 5521 */       return 0;
/*      */     } catch (NumberFormatException nfe) {
/*      */       try {
/* 5524 */         return parseShortAsDouble(columnIndex, val);
/*      */ 
/*      */       }
/*      */       catch (NumberFormatException newNfe)
/*      */       {
/* 5529 */         throw SQLError.createSQLException(Messages.getString("ResultSet.Invalid_value_for_getShort()_-____217") + val + Messages.getString("ResultSet.___in_column__218") + columnIndex, "S1009", getExceptionInterceptor());
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
/*      */   public Statement getStatement()
/*      */     throws SQLException
/*      */   {
/* 5548 */     if ((this.isClosed) && (!this.retainOwningStatement)) {
/* 5549 */       throw SQLError.createSQLException("Operation not allowed on closed ResultSet. Statements can be retained over result set closure by setting the connection property \"retainStatementAfterResultSetClose\" to \"true\".", "S1000", getExceptionInterceptor());
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 5557 */     if (this.wrapperStatement != null) {
/* 5558 */       return this.wrapperStatement;
/*      */     }
/*      */     
/* 5561 */     return this.owningStatement;
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
/*      */   public String getString(int columnIndex)
/*      */     throws SQLException
/*      */   {
/* 5576 */     String stringVal = getStringInternal(columnIndex, true);
/*      */     
/* 5578 */     if ((this.padCharsWithSpace) && (stringVal != null)) {
/* 5579 */       Field f = this.fields[(columnIndex - 1)];
/*      */       
/* 5581 */       if (f.getMysqlType() == 254) {
/* 5582 */         int fieldLength = (int)f.getLength() / f.getMaxBytesPerCharacter();
/*      */         
/*      */ 
/* 5585 */         int currentLength = stringVal.length();
/*      */         
/* 5587 */         if (currentLength < fieldLength) {
/* 5588 */           StringBuffer paddedBuf = new StringBuffer(fieldLength);
/* 5589 */           paddedBuf.append(stringVal);
/*      */           
/* 5591 */           int difference = fieldLength - currentLength;
/*      */           
/* 5593 */           paddedBuf.append(EMPTY_SPACE, 0, difference);
/*      */           
/* 5595 */           stringVal = paddedBuf.toString();
/*      */         }
/*      */       }
/*      */     }
/*      */     
/* 5600 */     return stringVal;
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
/*      */   public String getString(String columnName)
/*      */     throws SQLException
/*      */   {
/* 5616 */     return getString(findColumn(columnName));
/*      */   }
/*      */   
/*      */   private String getStringForClob(int columnIndex) throws SQLException {
/* 5620 */     String asString = null;
/*      */     
/* 5622 */     String forcedEncoding = this.connection.getClobCharacterEncoding();
/*      */     
/*      */ 
/* 5625 */     if (forcedEncoding == null) {
/* 5626 */       if (!this.isBinaryEncoded) {
/* 5627 */         asString = getString(columnIndex);
/*      */       } else {
/* 5629 */         asString = getNativeString(columnIndex);
/*      */       }
/*      */     } else {
/*      */       try {
/* 5633 */         byte[] asBytes = null;
/*      */         
/* 5635 */         if (!this.isBinaryEncoded) {
/* 5636 */           asBytes = getBytes(columnIndex);
/*      */         } else {
/* 5638 */           asBytes = getNativeBytes(columnIndex, true);
/*      */         }
/*      */         
/* 5641 */         if (asBytes != null) {
/* 5642 */           asString = new String(asBytes, forcedEncoding);
/*      */         }
/*      */       } catch (UnsupportedEncodingException uee) {
/* 5645 */         throw SQLError.createSQLException("Unsupported character encoding " + forcedEncoding, "S1009", getExceptionInterceptor());
/*      */       }
/*      */     }
/*      */     
/*      */ 
/* 5650 */     return asString;
/*      */   }
/*      */   
/*      */   protected String getStringInternal(int columnIndex, boolean checkDateTypes) throws SQLException
/*      */   {
/* 5655 */     if (!this.isBinaryEncoded) {
/* 5656 */       checkRowPos();
/* 5657 */       checkColumnBounds(columnIndex);
/*      */       
/* 5659 */       if (this.fields == null) {
/* 5660 */         throw SQLError.createSQLException(Messages.getString("ResultSet.Query_generated_no_fields_for_ResultSet_99"), "S1002", getExceptionInterceptor());
/*      */       }
/*      */       
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 5668 */       int internalColumnIndex = columnIndex - 1;
/*      */       
/* 5670 */       if (this.thisRow.isNull(internalColumnIndex)) {
/* 5671 */         this.wasNullFlag = true;
/*      */         
/* 5673 */         return null;
/*      */       }
/*      */       
/* 5676 */       this.wasNullFlag = false;
/*      */       
/*      */ 
/* 5679 */       Field metadata = this.fields[internalColumnIndex];
/*      */       
/* 5681 */       String stringVal = null;
/*      */       
/* 5683 */       if (metadata.getMysqlType() == 16) {
/* 5684 */         if (metadata.isSingleBit()) {
/* 5685 */           byte[] value = this.thisRow.getColumnValue(internalColumnIndex);
/*      */           
/* 5687 */           if (value.length == 0) {
/* 5688 */             return String.valueOf(convertToZeroWithEmptyCheck());
/*      */           }
/*      */           
/* 5691 */           return String.valueOf(value[0]);
/*      */         }
/*      */         
/* 5694 */         return String.valueOf(getNumericRepresentationOfSQLBitType(columnIndex));
/*      */       }
/*      */       
/* 5697 */       String encoding = metadata.getCharacterSet();
/*      */       
/* 5699 */       stringVal = this.thisRow.getString(internalColumnIndex, encoding, this.connection);
/*      */       
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 5706 */       if (metadata.getMysqlType() == 13) {
/* 5707 */         if (!this.connection.getYearIsDateType()) {
/* 5708 */           return stringVal;
/*      */         }
/*      */         
/* 5711 */         Date dt = getDateFromString(stringVal, columnIndex, null);
/*      */         
/* 5713 */         if (dt == null) {
/* 5714 */           this.wasNullFlag = true;
/*      */           
/* 5716 */           return null;
/*      */         }
/*      */         
/* 5719 */         this.wasNullFlag = false;
/*      */         
/* 5721 */         return dt.toString();
/*      */       }
/*      */       
/*      */ 
/*      */ 
/* 5726 */       if ((checkDateTypes) && (!this.connection.getNoDatetimeStringSync())) {
/* 5727 */         switch (metadata.getSQLType()) {
/*      */         case 92: 
/* 5729 */           Time tm = getTimeFromString(stringVal, null, columnIndex, getDefaultTimeZone(), false);
/*      */           
/*      */ 
/* 5732 */           if (tm == null) {
/* 5733 */             this.wasNullFlag = true;
/*      */             
/* 5735 */             return null;
/*      */           }
/*      */           
/* 5738 */           this.wasNullFlag = false;
/*      */           
/* 5740 */           return tm.toString();
/*      */         
/*      */         case 91: 
/* 5743 */           Date dt = getDateFromString(stringVal, columnIndex, null);
/*      */           
/* 5745 */           if (dt == null) {
/* 5746 */             this.wasNullFlag = true;
/*      */             
/* 5748 */             return null;
/*      */           }
/*      */           
/* 5751 */           this.wasNullFlag = false;
/*      */           
/* 5753 */           return dt.toString();
/*      */         case 93: 
/* 5755 */           Timestamp ts = getTimestampFromString(columnIndex, null, stringVal, getDefaultTimeZone(), false);
/*      */           
/*      */ 
/* 5758 */           if (ts == null) {
/* 5759 */             this.wasNullFlag = true;
/*      */             
/* 5761 */             return null;
/*      */           }
/*      */           
/* 5764 */           this.wasNullFlag = false;
/*      */           
/* 5766 */           return ts.toString();
/*      */         }
/*      */         
/*      */       }
/*      */       
/*      */ 
/* 5772 */       return stringVal;
/*      */     }
/*      */     
/* 5775 */     return getNativeString(columnIndex);
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
/*      */   public Time getTime(int columnIndex)
/*      */     throws SQLException
/*      */   {
/* 5790 */     return getTimeInternal(columnIndex, null, getDefaultTimeZone(), false);
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
/*      */   public Time getTime(int columnIndex, Calendar cal)
/*      */     throws SQLException
/*      */   {
/* 5810 */     return getTimeInternal(columnIndex, cal, cal.getTimeZone(), true);
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
/*      */   public Time getTime(String columnName)
/*      */     throws SQLException
/*      */   {
/* 5825 */     return getTime(findColumn(columnName));
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
/*      */   public Time getTime(String columnName, Calendar cal)
/*      */     throws SQLException
/*      */   {
/* 5845 */     return getTime(findColumn(columnName), cal);
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
/*      */   private Time getTimeInternal(int columnIndex, Calendar targetCalendar, TimeZone tz, boolean rollForward)
/*      */     throws SQLException
/*      */   {
/* 6031 */     checkRowPos();
/*      */     
/* 6033 */     if (this.isBinaryEncoded) {
/* 6034 */       return getNativeTime(columnIndex, targetCalendar, tz, rollForward);
/*      */     }
/*      */     
/* 6037 */     if (!this.useFastDateParsing) {
/* 6038 */       String timeAsString = getStringInternal(columnIndex, false);
/*      */       
/* 6040 */       return getTimeFromString(timeAsString, targetCalendar, columnIndex, tz, rollForward);
/*      */     }
/*      */     
/*      */ 
/* 6044 */     checkColumnBounds(columnIndex);
/*      */     
/* 6046 */     int columnIndexMinusOne = columnIndex - 1;
/*      */     
/* 6048 */     if (this.thisRow.isNull(columnIndexMinusOne)) {
/* 6049 */       this.wasNullFlag = true;
/*      */       
/* 6051 */       return null;
/*      */     }
/*      */     
/* 6054 */     this.wasNullFlag = false;
/*      */     
/* 6056 */     return this.thisRow.getTimeFast(columnIndexMinusOne, targetCalendar, tz, rollForward, this.connection, this);
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
/*      */   public Timestamp getTimestamp(int columnIndex)
/*      */     throws SQLException
/*      */   {
/* 6073 */     return getTimestampInternal(columnIndex, null, getDefaultTimeZone(), false);
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
/*      */   public Timestamp getTimestamp(int columnIndex, Calendar cal)
/*      */     throws SQLException
/*      */   {
/* 6095 */     return getTimestampInternal(columnIndex, cal, cal.getTimeZone(), true);
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
/*      */   public Timestamp getTimestamp(String columnName)
/*      */     throws SQLException
/*      */   {
/* 6111 */     return getTimestamp(findColumn(columnName));
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
/*      */   public Timestamp getTimestamp(String columnName, Calendar cal)
/*      */     throws SQLException
/*      */   {
/* 6132 */     return getTimestamp(findColumn(columnName), cal);
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
/*      */   private Timestamp getTimestampInternal(int columnIndex, Calendar targetCalendar, TimeZone tz, boolean rollForward)
/*      */     throws SQLException
/*      */   {
/* 6737 */     if (this.isBinaryEncoded) {
/* 6738 */       return getNativeTimestamp(columnIndex, targetCalendar, tz, rollForward);
/*      */     }
/*      */     
/* 6741 */     Timestamp tsVal = null;
/*      */     
/* 6743 */     if (!this.useFastDateParsing) {
/* 6744 */       String timestampValue = getStringInternal(columnIndex, false);
/*      */       
/* 6746 */       tsVal = getTimestampFromString(columnIndex, targetCalendar, timestampValue, tz, rollForward);
/*      */     }
/*      */     else
/*      */     {
/* 6750 */       checkClosed();
/* 6751 */       checkRowPos();
/* 6752 */       checkColumnBounds(columnIndex);
/*      */       
/* 6754 */       tsVal = this.thisRow.getTimestampFast(columnIndex - 1, targetCalendar, tz, rollForward, this.connection, this);
/*      */     }
/*      */     
/*      */ 
/* 6758 */     if (tsVal == null) {
/* 6759 */       this.wasNullFlag = true;
/*      */     } else {
/* 6761 */       this.wasNullFlag = false;
/*      */     }
/*      */     
/* 6764 */     return tsVal;
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
/*      */   public int getType()
/*      */     throws SQLException
/*      */   {
/* 6778 */     return this.resultSetType;
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
/*      */   /**
/*      */    * @deprecated
/*      */    */
/*      */   public InputStream getUnicodeStream(int columnIndex)
/*      */     throws SQLException
/*      */   {
/* 6800 */     if (!this.isBinaryEncoded) {
/* 6801 */       checkRowPos();
/*      */       
/* 6803 */       return getBinaryStream(columnIndex);
/*      */     }
/*      */     
/* 6806 */     return getNativeBinaryStream(columnIndex);
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
/*      */   /**
/*      */    * @deprecated
/*      */    */
/*      */   public InputStream getUnicodeStream(String columnName)
/*      */     throws SQLException
/*      */   {
/* 6823 */     return getUnicodeStream(findColumn(columnName));
/*      */   }
/*      */   
/*      */   public long getUpdateCount() {
/* 6827 */     return this.updateCount;
/*      */   }
/*      */   
/*      */   public long getUpdateID() {
/* 6831 */     return this.updateId;
/*      */   }
/*      */   
/*      */ 
/*      */   public URL getURL(int colIndex)
/*      */     throws SQLException
/*      */   {
/* 6838 */     String val = getString(colIndex);
/*      */     
/* 6840 */     if (val == null) {
/* 6841 */       return null;
/*      */     }
/*      */     try
/*      */     {
/* 6845 */       return new URL(val);
/*      */     } catch (MalformedURLException mfe) {
/* 6847 */       throw SQLError.createSQLException(Messages.getString("ResultSet.Malformed_URL____104") + val + "'", "S1009", getExceptionInterceptor());
/*      */     }
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   public URL getURL(String colName)
/*      */     throws SQLException
/*      */   {
/* 6857 */     String val = getString(colName);
/*      */     
/* 6859 */     if (val == null) {
/* 6860 */       return null;
/*      */     }
/*      */     try
/*      */     {
/* 6864 */       return new URL(val);
/*      */     } catch (MalformedURLException mfe) {
/* 6866 */       throw SQLError.createSQLException(Messages.getString("ResultSet.Malformed_URL____107") + val + "'", "S1009", getExceptionInterceptor());
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
/*      */   public SQLWarning getWarnings()
/*      */     throws SQLException
/*      */   {
/* 6893 */     return this.warningChain;
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
/*      */   public void insertRow()
/*      */     throws SQLException
/*      */   {
/* 6908 */     throw new NotUpdatable();
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
/*      */   public boolean isAfterLast()
/*      */     throws SQLException
/*      */   {
/* 6925 */     checkClosed();
/*      */     
/* 6927 */     boolean b = this.rowData.isAfterLast();
/*      */     
/* 6929 */     return b;
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
/*      */   public boolean isBeforeFirst()
/*      */     throws SQLException
/*      */   {
/* 6946 */     checkClosed();
/*      */     
/* 6948 */     return this.rowData.isBeforeFirst();
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
/*      */   public boolean isFirst()
/*      */     throws SQLException
/*      */   {
/* 6964 */     checkClosed();
/*      */     
/* 6966 */     return this.rowData.isFirst();
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
/*      */   public boolean isLast()
/*      */     throws SQLException
/*      */   {
/* 6985 */     checkClosed();
/*      */     
/* 6987 */     return this.rowData.isLast();
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   private void issueConversionViaParsingWarning(String methodName, int columnIndex, Object value, Field fieldInfo, int[] typesWithNoParseConversion)
/*      */     throws SQLException
/*      */   {
/* 6999 */     StringBuffer originalQueryBuf = new StringBuffer();
/*      */     
/* 7001 */     if ((this.owningStatement != null) && ((this.owningStatement instanceof PreparedStatement)))
/*      */     {
/* 7003 */       originalQueryBuf.append(Messages.getString("ResultSet.CostlyConversionCreatedFromQuery"));
/* 7004 */       originalQueryBuf.append(((PreparedStatement)this.owningStatement).originalSql);
/*      */       
/* 7006 */       originalQueryBuf.append("\n\n");
/*      */     } else {
/* 7008 */       originalQueryBuf.append(".");
/*      */     }
/*      */     
/* 7011 */     StringBuffer convertibleTypesBuf = new StringBuffer();
/*      */     
/* 7013 */     for (int i = 0; i < typesWithNoParseConversion.length; i++) {
/* 7014 */       convertibleTypesBuf.append(MysqlDefs.typeToName(typesWithNoParseConversion[i]));
/* 7015 */       convertibleTypesBuf.append("\n");
/*      */     }
/*      */     
/* 7018 */     String message = Messages.getString("ResultSet.CostlyConversion", new Object[] { methodName, new Integer(columnIndex + 1), fieldInfo.getOriginalName(), fieldInfo.getOriginalTableName(), originalQueryBuf.toString(), value != null ? value.getClass().getName() : ResultSetMetaData.getClassNameForJavaType(fieldInfo.getSQLType(), fieldInfo.isUnsigned(), fieldInfo.getMysqlType(), (fieldInfo.isBinary()) || (fieldInfo.isBlob()) ? 1 : false, fieldInfo.isOpaqueBinary()), MysqlDefs.typeToName(fieldInfo.getMysqlType()), convertibleTypesBuf.toString() });
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
/* 7033 */     this.eventSink.consumeEvent(new ProfilerEvent((byte)0, "", this.owningStatement == null ? "N/A" : this.owningStatement.currentCatalog, this.connectionId, this.owningStatement == null ? -1 : this.owningStatement.getId(), this.resultId, System.currentTimeMillis(), 0L, Constants.MILLIS_I18N, null, this.pointOfOrigin, message));
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
/*      */   public boolean last()
/*      */     throws SQLException
/*      */   {
/* 7057 */     checkClosed();
/*      */     
/* 7059 */     boolean b = true;
/*      */     
/* 7061 */     if (this.rowData.size() == 0) {
/* 7062 */       b = false;
/*      */     }
/*      */     else {
/* 7065 */       if (this.onInsertRow) {
/* 7066 */         this.onInsertRow = false;
/*      */       }
/*      */       
/* 7069 */       if (this.doingUpdates) {
/* 7070 */         this.doingUpdates = false;
/*      */       }
/*      */       
/* 7073 */       if (this.thisRow != null) {
/* 7074 */         this.thisRow.closeOpenStreams();
/*      */       }
/*      */       
/* 7077 */       this.rowData.beforeLast();
/* 7078 */       this.thisRow = this.rowData.next();
/*      */     }
/*      */     
/* 7081 */     setRowPositionValidity();
/*      */     
/* 7083 */     return b;
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
/*      */   public void moveToCurrentRow()
/*      */     throws SQLException
/*      */   {
/* 7105 */     throw new NotUpdatable();
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
/*      */   public void moveToInsertRow()
/*      */     throws SQLException
/*      */   {
/* 7126 */     throw new NotUpdatable();
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
/*      */   public boolean next()
/*      */     throws SQLException
/*      */   {
/* 7145 */     checkClosed();
/*      */     
/* 7147 */     if (this.onInsertRow) {
/* 7148 */       this.onInsertRow = false;
/*      */     }
/*      */     
/* 7151 */     if (this.doingUpdates) {
/* 7152 */       this.doingUpdates = false;
/*      */     }
/*      */     
/*      */ 
/*      */ 
/* 7157 */     if (!reallyResult()) {
/* 7158 */       throw SQLError.createSQLException(Messages.getString("ResultSet.ResultSet_is_from_UPDATE._No_Data_115"), "S1000", getExceptionInterceptor());
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*      */ 
/* 7164 */     if (this.thisRow != null)
/* 7165 */       this.thisRow.closeOpenStreams();
/*      */     boolean b;
/*      */     boolean b;
/* 7168 */     if (this.rowData.size() == 0) {
/* 7169 */       b = false;
/*      */     } else {
/* 7171 */       this.thisRow = this.rowData.next();
/*      */       boolean b;
/* 7173 */       if (this.thisRow == null) {
/* 7174 */         b = false;
/*      */       } else {
/* 7176 */         clearWarnings();
/*      */         
/* 7178 */         b = true;
/*      */       }
/*      */     }
/*      */     
/*      */ 
/* 7183 */     setRowPositionValidity();
/*      */     
/* 7185 */     return b;
/*      */   }
/*      */   
/*      */   private int parseIntAsDouble(int columnIndex, String val) throws NumberFormatException, SQLException
/*      */   {
/* 7190 */     if (val == null) {
/* 7191 */       return 0;
/*      */     }
/*      */     
/* 7194 */     double valueAsDouble = Double.parseDouble(val);
/*      */     
/* 7196 */     if ((this.jdbcCompliantTruncationForReads) && (
/* 7197 */       (valueAsDouble < -2.147483648E9D) || (valueAsDouble > 2.147483647E9D)))
/*      */     {
/* 7199 */       throwRangeException(String.valueOf(valueAsDouble), columnIndex, 4);
/*      */     }
/*      */     
/*      */ 
/*      */ 
/* 7204 */     return (int)valueAsDouble;
/*      */   }
/*      */   
/*      */   private int getIntWithOverflowCheck(int columnIndex) throws SQLException {
/* 7208 */     int intValue = this.thisRow.getInt(columnIndex);
/*      */     
/* 7210 */     checkForIntegerTruncation(columnIndex, null, intValue);
/*      */     
/*      */ 
/* 7213 */     return intValue;
/*      */   }
/*      */   
/*      */   private void checkForIntegerTruncation(int columnIndex, byte[] valueAsBytes, int intValue)
/*      */     throws SQLException
/*      */   {
/* 7219 */     if ((this.jdbcCompliantTruncationForReads) && (
/* 7220 */       (intValue == Integer.MIN_VALUE) || (intValue == Integer.MAX_VALUE))) {
/* 7221 */       String valueAsString = null;
/*      */       
/* 7223 */       if (valueAsBytes == null) {
/* 7224 */         valueAsString = this.thisRow.getString(columnIndex, this.fields[columnIndex].getCharacterSet(), this.connection);
/*      */       }
/*      */       
/*      */ 
/*      */ 
/* 7229 */       long valueAsLong = Long.parseLong(valueAsString == null ? new String(valueAsBytes) : valueAsString);
/*      */       
/*      */ 
/*      */ 
/* 7233 */       if ((valueAsLong < -2147483648L) || (valueAsLong > 2147483647L))
/*      */       {
/* 7235 */         throwRangeException(valueAsString == null ? new String(valueAsBytes) : valueAsString, columnIndex + 1, 4);
/*      */       }
/*      */     }
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */   private long parseLongAsDouble(int columnIndexZeroBased, String val)
/*      */     throws NumberFormatException, SQLException
/*      */   {
/* 7245 */     if (val == null) {
/* 7246 */       return 0L;
/*      */     }
/*      */     
/* 7249 */     double valueAsDouble = Double.parseDouble(val);
/*      */     
/* 7251 */     if ((this.jdbcCompliantTruncationForReads) && (
/* 7252 */       (valueAsDouble < -9.223372036854776E18D) || (valueAsDouble > 9.223372036854776E18D)))
/*      */     {
/* 7254 */       throwRangeException(val, columnIndexZeroBased + 1, -5);
/*      */     }
/*      */     
/*      */ 
/* 7258 */     return valueAsDouble;
/*      */   }
/*      */   
/*      */   private long getLongWithOverflowCheck(int columnIndexZeroBased, boolean doOverflowCheck) throws SQLException {
/* 7262 */     long longValue = this.thisRow.getLong(columnIndexZeroBased);
/*      */     
/* 7264 */     if (doOverflowCheck) {
/* 7265 */       checkForLongTruncation(columnIndexZeroBased, null, longValue);
/*      */     }
/*      */     
/* 7268 */     return longValue;
/*      */   }
/*      */   
/*      */ 
/*      */   private long parseLongWithOverflowCheck(int columnIndexZeroBased, byte[] valueAsBytes, String valueAsString, boolean doCheck)
/*      */     throws NumberFormatException, SQLException
/*      */   {
/* 7275 */     long longValue = 0L;
/*      */     
/* 7277 */     if ((valueAsBytes == null) && (valueAsString == null)) {
/* 7278 */       return 0L;
/*      */     }
/*      */     
/* 7281 */     if (valueAsBytes != null) {
/* 7282 */       longValue = StringUtils.getLong(valueAsBytes);
/*      */ 
/*      */ 
/*      */ 
/*      */     }
/*      */     else
/*      */     {
/*      */ 
/*      */ 
/*      */ 
/* 7292 */       valueAsString = valueAsString.trim();
/*      */       
/* 7294 */       longValue = Long.parseLong(valueAsString);
/*      */     }
/*      */     
/* 7297 */     if ((doCheck) && (this.jdbcCompliantTruncationForReads)) {
/* 7298 */       checkForLongTruncation(columnIndexZeroBased, valueAsBytes, longValue);
/*      */     }
/*      */     
/* 7301 */     return longValue;
/*      */   }
/*      */   
/*      */   private void checkForLongTruncation(int columnIndexZeroBased, byte[] valueAsBytes, long longValue) throws SQLException {
/* 7305 */     if ((longValue == Long.MIN_VALUE) || (longValue == Long.MAX_VALUE))
/*      */     {
/* 7307 */       String valueAsString = null;
/*      */       
/* 7309 */       if (valueAsBytes == null) {
/* 7310 */         valueAsString = this.thisRow.getString(columnIndexZeroBased, this.fields[columnIndexZeroBased].getCharacterSet(), this.connection);
/*      */       }
/*      */       
/*      */ 
/*      */ 
/* 7315 */       double valueAsDouble = Double.parseDouble(valueAsString == null ? new String(valueAsBytes) : valueAsString);
/*      */       
/*      */ 
/*      */ 
/* 7319 */       if ((valueAsDouble < -9.223372036854776E18D) || (valueAsDouble > 9.223372036854776E18D))
/*      */       {
/* 7321 */         throwRangeException(valueAsString == null ? new String(valueAsBytes) : valueAsString, columnIndexZeroBased + 1, -5);
/*      */       }
/*      */     }
/*      */   }
/*      */   
/*      */ 
/*      */   private short parseShortAsDouble(int columnIndex, String val)
/*      */     throws NumberFormatException, SQLException
/*      */   {
/* 7330 */     if (val == null) {
/* 7331 */       return 0;
/*      */     }
/*      */     
/* 7334 */     double valueAsDouble = Double.parseDouble(val);
/*      */     
/* 7336 */     if ((this.jdbcCompliantTruncationForReads) && (
/* 7337 */       (valueAsDouble < -32768.0D) || (valueAsDouble > 32767.0D)))
/*      */     {
/* 7339 */       throwRangeException(String.valueOf(valueAsDouble), columnIndex, 5);
/*      */     }
/*      */     
/*      */ 
/*      */ 
/* 7344 */     return (short)(int)valueAsDouble;
/*      */   }
/*      */   
/*      */ 
/*      */   private short parseShortWithOverflowCheck(int columnIndex, byte[] valueAsBytes, String valueAsString)
/*      */     throws NumberFormatException, SQLException
/*      */   {
/* 7351 */     short shortValue = 0;
/*      */     
/* 7353 */     if ((valueAsBytes == null) && (valueAsString == null)) {
/* 7354 */       return 0;
/*      */     }
/*      */     
/* 7357 */     if (valueAsBytes != null) {
/* 7358 */       shortValue = StringUtils.getShort(valueAsBytes);
/*      */ 
/*      */ 
/*      */ 
/*      */     }
/*      */     else
/*      */     {
/*      */ 
/*      */ 
/*      */ 
/* 7368 */       valueAsString = valueAsString.trim();
/*      */       
/* 7370 */       shortValue = Short.parseShort(valueAsString);
/*      */     }
/*      */     
/* 7373 */     if ((this.jdbcCompliantTruncationForReads) && (
/* 7374 */       (shortValue == Short.MIN_VALUE) || (shortValue == Short.MAX_VALUE))) {
/* 7375 */       long valueAsLong = Long.parseLong(valueAsString == null ? new String(valueAsBytes) : valueAsString);
/*      */       
/*      */ 
/*      */ 
/* 7379 */       if ((valueAsLong < -32768L) || (valueAsLong > 32767L))
/*      */       {
/* 7381 */         throwRangeException(valueAsString == null ? new String(valueAsBytes) : valueAsString, columnIndex, 5);
/*      */       }
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*      */ 
/* 7388 */     return shortValue;
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
/*      */   public boolean prev()
/*      */     throws SQLException
/*      */   {
/* 7412 */     checkClosed();
/*      */     
/* 7414 */     int rowIndex = this.rowData.getCurrentRowNumber();
/*      */     
/* 7416 */     if (this.thisRow != null) {
/* 7417 */       this.thisRow.closeOpenStreams();
/*      */     }
/*      */     
/* 7420 */     boolean b = true;
/*      */     
/* 7422 */     if (rowIndex - 1 >= 0) {
/* 7423 */       rowIndex--;
/* 7424 */       this.rowData.setCurrentRow(rowIndex);
/* 7425 */       this.thisRow = this.rowData.getAt(rowIndex);
/*      */       
/* 7427 */       b = true;
/* 7428 */     } else if (rowIndex - 1 == -1) {
/* 7429 */       rowIndex--;
/* 7430 */       this.rowData.setCurrentRow(rowIndex);
/* 7431 */       this.thisRow = null;
/*      */       
/* 7433 */       b = false;
/*      */     } else {
/* 7435 */       b = false;
/*      */     }
/*      */     
/* 7438 */     setRowPositionValidity();
/*      */     
/* 7440 */     return b;
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
/*      */   public boolean previous()
/*      */     throws SQLException
/*      */   {
/* 7462 */     if (this.onInsertRow) {
/* 7463 */       this.onInsertRow = false;
/*      */     }
/*      */     
/* 7466 */     if (this.doingUpdates) {
/* 7467 */       this.doingUpdates = false;
/*      */     }
/*      */     
/* 7470 */     return prev();
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public void realClose(boolean calledExplicitly)
/*      */     throws SQLException
/*      */   {
/* 7483 */     if (this.isClosed) {
/* 7484 */       return;
/*      */     }
/*      */     try
/*      */     {
/* 7488 */       if (this.useUsageAdvisor)
/*      */       {
/*      */ 
/*      */ 
/* 7492 */         if (!calledExplicitly) {
/* 7493 */           this.eventSink.consumeEvent(new ProfilerEvent((byte)0, "", this.owningStatement == null ? "N/A" : this.owningStatement.currentCatalog, this.connectionId, this.owningStatement == null ? -1 : this.owningStatement.getId(), this.resultId, System.currentTimeMillis(), 0L, Constants.MILLIS_I18N, null, this.pointOfOrigin, Messages.getString("ResultSet.ResultSet_implicitly_closed_by_driver")));
/*      */         }
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
/* 7512 */         if ((this.rowData instanceof RowDataStatic))
/*      */         {
/*      */ 
/*      */ 
/* 7516 */           if (this.rowData.size() > this.connection.getResultSetSizeThreshold())
/*      */           {
/* 7518 */             this.eventSink.consumeEvent(new ProfilerEvent((byte)0, "", this.owningStatement == null ? Messages.getString("ResultSet.N/A_159") : this.owningStatement.currentCatalog, this.connectionId, this.owningStatement == null ? -1 : this.owningStatement.getId(), this.resultId, System.currentTimeMillis(), 0L, Constants.MILLIS_I18N, null, this.pointOfOrigin, Messages.getString("ResultSet.Too_Large_Result_Set", new Object[] { new Integer(this.rowData.size()), new Integer(this.connection.getResultSetSizeThreshold()) })));
/*      */           }
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
/* 7546 */           if ((!isLast()) && (!isAfterLast()) && (this.rowData.size() != 0))
/*      */           {
/* 7548 */             this.eventSink.consumeEvent(new ProfilerEvent((byte)0, "", this.owningStatement == null ? Messages.getString("ResultSet.N/A_159") : this.owningStatement.currentCatalog, this.connectionId, this.owningStatement == null ? -1 : this.owningStatement.getId(), this.resultId, System.currentTimeMillis(), 0L, Constants.MILLIS_I18N, null, this.pointOfOrigin, Messages.getString("ResultSet.Possible_incomplete_traversal_of_result_set", new Object[] { new Integer(getRow()), new Integer(this.rowData.size()) })));
/*      */           }
/*      */         }
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
/* 7581 */         if ((this.columnUsed.length > 0) && (!this.rowData.wasEmpty())) {
/* 7582 */           StringBuffer buf = new StringBuffer(Messages.getString("ResultSet.The_following_columns_were_never_referenced"));
/*      */           
/*      */ 
/*      */ 
/* 7586 */           boolean issueWarn = false;
/*      */           
/* 7588 */           for (int i = 0; i < this.columnUsed.length; i++) {
/* 7589 */             if (this.columnUsed[i] == 0) {
/* 7590 */               if (!issueWarn) {
/* 7591 */                 issueWarn = true;
/*      */               } else {
/* 7593 */                 buf.append(", ");
/*      */               }
/*      */               
/* 7596 */               buf.append(this.fields[i].getFullName());
/*      */             }
/*      */           }
/*      */           
/* 7600 */           if (issueWarn) {
/* 7601 */             this.eventSink.consumeEvent(new ProfilerEvent((byte)0, "", this.owningStatement == null ? "N/A" : this.owningStatement.currentCatalog, this.connectionId, this.owningStatement == null ? -1 : this.owningStatement.getId(), 0, System.currentTimeMillis(), 0L, Constants.MILLIS_I18N, null, this.pointOfOrigin, buf.toString()));
/*      */ 
/*      */           }
/*      */           
/*      */ 
/*      */         }
/*      */         
/*      */       }
/*      */       
/*      */ 
/*      */     }
/*      */     finally
/*      */     {
/*      */ 
/* 7615 */       if ((this.owningStatement != null) && (calledExplicitly)) {
/* 7616 */         this.owningStatement.removeOpenResultSet(this);
/*      */       }
/*      */       
/* 7619 */       SQLException exceptionDuringClose = null;
/*      */       
/* 7621 */       if (this.rowData != null) {
/*      */         try {
/* 7623 */           this.rowData.close();
/*      */         } catch (SQLException sqlEx) {
/* 7625 */           exceptionDuringClose = sqlEx;
/*      */         }
/*      */       }
/*      */       
/* 7629 */       if (this.statementUsedForFetchingRows != null) {
/*      */         try {
/* 7631 */           this.statementUsedForFetchingRows.realClose(true, false);
/*      */         } catch (SQLException sqlEx) {
/* 7633 */           if (exceptionDuringClose != null) {
/* 7634 */             exceptionDuringClose.setNextException(sqlEx);
/*      */           } else {
/* 7636 */             exceptionDuringClose = sqlEx;
/*      */           }
/*      */         }
/*      */       }
/*      */       
/* 7641 */       this.rowData = null;
/* 7642 */       this.defaultTimeZone = null;
/* 7643 */       this.fields = null;
/* 7644 */       this.columnLabelToIndex = null;
/* 7645 */       this.fullColumnNameToIndex = null;
/* 7646 */       this.columnToIndexCache = null;
/* 7647 */       this.eventSink = null;
/* 7648 */       this.warningChain = null;
/*      */       
/* 7650 */       if (!this.retainOwningStatement) {
/* 7651 */         this.owningStatement = null;
/*      */       }
/*      */       
/* 7654 */       this.catalog = null;
/* 7655 */       this.serverInfo = null;
/* 7656 */       this.thisRow = null;
/* 7657 */       this.fastDateCal = null;
/* 7658 */       this.connection = null;
/*      */       
/* 7660 */       this.isClosed = true;
/*      */       
/* 7662 */       if (exceptionDuringClose != null) {
/* 7663 */         throw exceptionDuringClose;
/*      */       }
/*      */     }
/*      */   }
/*      */   
/*      */   public boolean reallyResult() {
/* 7669 */     if (this.rowData != null) {
/* 7670 */       return true;
/*      */     }
/*      */     
/* 7673 */     return this.reallyResult;
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
/*      */   public void refreshRow()
/*      */     throws SQLException
/*      */   {
/* 7697 */     throw new NotUpdatable();
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
/*      */   public boolean relative(int rows)
/*      */     throws SQLException
/*      */   {
/* 7727 */     checkClosed();
/*      */     
/* 7729 */     if (this.rowData.size() == 0) {
/* 7730 */       setRowPositionValidity();
/*      */       
/* 7732 */       return false;
/*      */     }
/*      */     
/* 7735 */     if (this.thisRow != null) {
/* 7736 */       this.thisRow.closeOpenStreams();
/*      */     }
/*      */     
/* 7739 */     this.rowData.moveRowRelative(rows);
/* 7740 */     this.thisRow = this.rowData.getAt(this.rowData.getCurrentRowNumber());
/*      */     
/* 7742 */     setRowPositionValidity();
/*      */     
/* 7744 */     return (!this.rowData.isAfterLast()) && (!this.rowData.isBeforeFirst());
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
/*      */   public boolean rowDeleted()
/*      */     throws SQLException
/*      */   {
/* 7763 */     throw SQLError.notImplemented();
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
/*      */   public boolean rowInserted()
/*      */     throws SQLException
/*      */   {
/* 7781 */     throw SQLError.notImplemented();
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
/*      */   public boolean rowUpdated()
/*      */     throws SQLException
/*      */   {
/* 7799 */     throw SQLError.notImplemented();
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   protected void setBinaryEncoded()
/*      */   {
/* 7807 */     this.isBinaryEncoded = true;
/*      */   }
/*      */   
/*      */   private void setDefaultTimeZone(TimeZone defaultTimeZone) {
/* 7811 */     this.defaultTimeZone = defaultTimeZone;
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
/*      */   public void setFetchDirection(int direction)
/*      */     throws SQLException
/*      */   {
/* 7830 */     if ((direction != 1000) && (direction != 1001) && (direction != 1002))
/*      */     {
/* 7832 */       throw SQLError.createSQLException(Messages.getString("ResultSet.Illegal_value_for_fetch_direction_64"), "S1009", getExceptionInterceptor());
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*      */ 
/* 7838 */     this.fetchDirection = direction;
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
/*      */   public void setFetchSize(int rows)
/*      */     throws SQLException
/*      */   {
/* 7858 */     if (rows < 0) {
/* 7859 */       throw SQLError.createSQLException(Messages.getString("ResultSet.Value_must_be_between_0_and_getMaxRows()_66"), "S1009", getExceptionInterceptor());
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*      */ 
/* 7865 */     this.fetchSize = rows;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public void setFirstCharOfQuery(char c)
/*      */   {
/* 7876 */     this.firstCharOfQuery = c;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   protected void setNextResultSet(ResultSetInternalMethods nextResultSet)
/*      */   {
/* 7887 */     this.nextResultSet = nextResultSet;
/*      */   }
/*      */   
/*      */   public void setOwningStatement(StatementImpl owningStatement) {
/* 7891 */     this.owningStatement = owningStatement;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   protected void setResultSetConcurrency(int concurrencyFlag)
/*      */   {
/* 7901 */     this.resultSetConcurrency = concurrencyFlag;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   protected void setResultSetType(int typeFlag)
/*      */   {
/* 7912 */     this.resultSetType = typeFlag;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   protected void setServerInfo(String info)
/*      */   {
/* 7922 */     this.serverInfo = info;
/*      */   }
/*      */   
/*      */   public void setStatementUsedForFetchingRows(PreparedStatement stmt) {
/* 7926 */     this.statementUsedForFetchingRows = stmt;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   public void setWrapperStatement(Statement wrapperStatement)
/*      */   {
/* 7934 */     this.wrapperStatement = wrapperStatement;
/*      */   }
/*      */   
/*      */   private void throwRangeException(String valueAsString, int columnIndex, int jdbcType) throws SQLException
/*      */   {
/* 7939 */     String datatype = null;
/*      */     
/* 7941 */     switch (jdbcType) {
/*      */     case -6: 
/* 7943 */       datatype = "TINYINT";
/* 7944 */       break;
/*      */     case 5: 
/* 7946 */       datatype = "SMALLINT";
/* 7947 */       break;
/*      */     case 4: 
/* 7949 */       datatype = "INTEGER";
/* 7950 */       break;
/*      */     case -5: 
/* 7952 */       datatype = "BIGINT";
/* 7953 */       break;
/*      */     case 7: 
/* 7955 */       datatype = "REAL";
/* 7956 */       break;
/*      */     case 6: 
/* 7958 */       datatype = "FLOAT";
/* 7959 */       break;
/*      */     case 8: 
/* 7961 */       datatype = "DOUBLE";
/* 7962 */       break;
/*      */     case 3: 
/* 7964 */       datatype = "DECIMAL";
/* 7965 */       break;
/*      */     case -4: case -3: case -2: case -1: case 0: case 1: case 2: default: 
/* 7967 */       datatype = " (JDBC type '" + jdbcType + "')";
/*      */     }
/*      */     
/* 7970 */     throw SQLError.createSQLException("'" + valueAsString + "' in column '" + columnIndex + "' is outside valid range for the datatype " + datatype + ".", "22003", getExceptionInterceptor());
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public String toString()
/*      */   {
/* 7981 */     if (this.reallyResult) {
/* 7982 */       return super.toString();
/*      */     }
/*      */     
/* 7985 */     return "Result set representing update count of " + this.updateCount;
/*      */   }
/*      */   
/*      */ 
/*      */   public void updateArray(int arg0, Array arg1)
/*      */     throws SQLException
/*      */   {
/* 7992 */     throw SQLError.notImplemented();
/*      */   }
/*      */   
/*      */ 
/*      */   public void updateArray(String arg0, Array arg1)
/*      */     throws SQLException
/*      */   {
/* 7999 */     throw SQLError.notImplemented();
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
/*      */   public void updateAsciiStream(int columnIndex, InputStream x, int length)
/*      */     throws SQLException
/*      */   {
/* 8023 */     throw new NotUpdatable();
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
/*      */   public void updateAsciiStream(String columnName, InputStream x, int length)
/*      */     throws SQLException
/*      */   {
/* 8045 */     updateAsciiStream(findColumn(columnName), x, length);
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
/*      */   public void updateBigDecimal(int columnIndex, BigDecimal x)
/*      */     throws SQLException
/*      */   {
/* 8066 */     throw new NotUpdatable();
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
/*      */   public void updateBigDecimal(String columnName, BigDecimal x)
/*      */     throws SQLException
/*      */   {
/* 8085 */     updateBigDecimal(findColumn(columnName), x);
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
/*      */   public void updateBinaryStream(int columnIndex, InputStream x, int length)
/*      */     throws SQLException
/*      */   {
/* 8109 */     throw new NotUpdatable();
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
/*      */   public void updateBinaryStream(String columnName, InputStream x, int length)
/*      */     throws SQLException
/*      */   {
/* 8131 */     updateBinaryStream(findColumn(columnName), x, length);
/*      */   }
/*      */   
/*      */ 
/*      */   public void updateBlob(int arg0, java.sql.Blob arg1)
/*      */     throws SQLException
/*      */   {
/* 8138 */     throw new NotUpdatable();
/*      */   }
/*      */   
/*      */ 
/*      */   public void updateBlob(String arg0, java.sql.Blob arg1)
/*      */     throws SQLException
/*      */   {
/* 8145 */     throw new NotUpdatable();
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
/*      */   public void updateBoolean(int columnIndex, boolean x)
/*      */     throws SQLException
/*      */   {
/* 8165 */     throw new NotUpdatable();
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
/*      */   public void updateBoolean(String columnName, boolean x)
/*      */     throws SQLException
/*      */   {
/* 8183 */     updateBoolean(findColumn(columnName), x);
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
/*      */   public void updateByte(int columnIndex, byte x)
/*      */     throws SQLException
/*      */   {
/* 8203 */     throw new NotUpdatable();
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
/*      */   public void updateByte(String columnName, byte x)
/*      */     throws SQLException
/*      */   {
/* 8221 */     updateByte(findColumn(columnName), x);
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
/*      */   public void updateBytes(int columnIndex, byte[] x)
/*      */     throws SQLException
/*      */   {
/* 8241 */     throw new NotUpdatable();
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
/*      */   public void updateBytes(String columnName, byte[] x)
/*      */     throws SQLException
/*      */   {
/* 8259 */     updateBytes(findColumn(columnName), x);
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
/*      */   public void updateCharacterStream(int columnIndex, Reader x, int length)
/*      */     throws SQLException
/*      */   {
/* 8283 */     throw new NotUpdatable();
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
/*      */   public void updateCharacterStream(String columnName, Reader reader, int length)
/*      */     throws SQLException
/*      */   {
/* 8305 */     updateCharacterStream(findColumn(columnName), reader, length);
/*      */   }
/*      */   
/*      */ 
/*      */   public void updateClob(int arg0, java.sql.Clob arg1)
/*      */     throws SQLException
/*      */   {
/* 8312 */     throw SQLError.notImplemented();
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */   public void updateClob(String columnName, java.sql.Clob clob)
/*      */     throws SQLException
/*      */   {
/* 8320 */     updateClob(findColumn(columnName), clob);
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
/*      */   public void updateDate(int columnIndex, Date x)
/*      */     throws SQLException
/*      */   {
/* 8341 */     throw new NotUpdatable();
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
/*      */   public void updateDate(String columnName, Date x)
/*      */     throws SQLException
/*      */   {
/* 8360 */     updateDate(findColumn(columnName), x);
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
/*      */   public void updateDouble(int columnIndex, double x)
/*      */     throws SQLException
/*      */   {
/* 8380 */     throw new NotUpdatable();
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
/*      */   public void updateDouble(String columnName, double x)
/*      */     throws SQLException
/*      */   {
/* 8398 */     updateDouble(findColumn(columnName), x);
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
/*      */   public void updateFloat(int columnIndex, float x)
/*      */     throws SQLException
/*      */   {
/* 8418 */     throw new NotUpdatable();
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
/*      */   public void updateFloat(String columnName, float x)
/*      */     throws SQLException
/*      */   {
/* 8436 */     updateFloat(findColumn(columnName), x);
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
/*      */   public void updateInt(int columnIndex, int x)
/*      */     throws SQLException
/*      */   {
/* 8456 */     throw new NotUpdatable();
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
/*      */   public void updateInt(String columnName, int x)
/*      */     throws SQLException
/*      */   {
/* 8474 */     updateInt(findColumn(columnName), x);
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
/*      */   public void updateLong(int columnIndex, long x)
/*      */     throws SQLException
/*      */   {
/* 8494 */     throw new NotUpdatable();
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
/*      */   public void updateLong(String columnName, long x)
/*      */     throws SQLException
/*      */   {
/* 8512 */     updateLong(findColumn(columnName), x);
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
/*      */   public void updateNull(int columnIndex)
/*      */     throws SQLException
/*      */   {
/* 8530 */     throw new NotUpdatable();
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
/*      */   public void updateNull(String columnName)
/*      */     throws SQLException
/*      */   {
/* 8546 */     updateNull(findColumn(columnName));
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
/*      */   public void updateObject(int columnIndex, Object x)
/*      */     throws SQLException
/*      */   {
/* 8566 */     throw new NotUpdatable();
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
/*      */   public void updateObject(int columnIndex, Object x, int scale)
/*      */     throws SQLException
/*      */   {
/* 8591 */     throw new NotUpdatable();
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
/*      */   public void updateObject(String columnName, Object x)
/*      */     throws SQLException
/*      */   {
/* 8609 */     updateObject(findColumn(columnName), x);
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
/*      */   public void updateObject(String columnName, Object x, int scale)
/*      */     throws SQLException
/*      */   {
/* 8632 */     updateObject(findColumn(columnName), x);
/*      */   }
/*      */   
/*      */ 
/*      */   public void updateRef(int arg0, Ref arg1)
/*      */     throws SQLException
/*      */   {
/* 8639 */     throw SQLError.notImplemented();
/*      */   }
/*      */   
/*      */ 
/*      */   public void updateRef(String arg0, Ref arg1)
/*      */     throws SQLException
/*      */   {
/* 8646 */     throw SQLError.notImplemented();
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
/*      */   public void updateRow()
/*      */     throws SQLException
/*      */   {
/* 8660 */     throw new NotUpdatable();
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
/*      */   public void updateShort(int columnIndex, short x)
/*      */     throws SQLException
/*      */   {
/* 8680 */     throw new NotUpdatable();
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
/*      */   public void updateShort(String columnName, short x)
/*      */     throws SQLException
/*      */   {
/* 8698 */     updateShort(findColumn(columnName), x);
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
/*      */   public void updateString(int columnIndex, String x)
/*      */     throws SQLException
/*      */   {
/* 8718 */     throw new NotUpdatable();
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
/*      */   public void updateString(String columnName, String x)
/*      */     throws SQLException
/*      */   {
/* 8736 */     updateString(findColumn(columnName), x);
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
/*      */   public void updateTime(int columnIndex, Time x)
/*      */     throws SQLException
/*      */   {
/* 8757 */     throw new NotUpdatable();
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
/*      */   public void updateTime(String columnName, Time x)
/*      */     throws SQLException
/*      */   {
/* 8776 */     updateTime(findColumn(columnName), x);
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
/*      */   public void updateTimestamp(int columnIndex, Timestamp x)
/*      */     throws SQLException
/*      */   {
/* 8798 */     throw new NotUpdatable();
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
/*      */   public void updateTimestamp(String columnName, Timestamp x)
/*      */     throws SQLException
/*      */   {
/* 8817 */     updateTimestamp(findColumn(columnName), x);
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
/*      */   public boolean wasNull()
/*      */     throws SQLException
/*      */   {
/* 8832 */     return this.wasNullFlag;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */   protected Calendar getGmtCalendar()
/*      */   {
/* 8839 */     if (this.gmtCalendar == null) {
/* 8840 */       this.gmtCalendar = Calendar.getInstance(TimeZone.getTimeZone("GMT"));
/*      */     }
/*      */     
/* 8843 */     return this.gmtCalendar;
/*      */   }
/*      */   
/*      */   protected ExceptionInterceptor getExceptionInterceptor() {
/* 8847 */     return this.exceptionInterceptor;
/*      */   }
/*      */   
/*      */   /* Error */
/*      */   private Time getTimeFromString(String timeAsString, Calendar targetCalendar, int columnIndex, TimeZone tz, boolean rollForward)
/*      */     throws SQLException
/*      */   {
/*      */     // Byte code:
/*      */     //   0: iconst_0
/*      */     //   1: istore 6
/*      */     //   3: iconst_0
/*      */     //   4: istore 7
/*      */     //   6: iconst_0
/*      */     //   7: istore 8
/*      */     //   9: aload_1
/*      */     //   10: ifnonnull +10 -> 20
/*      */     //   13: aload_0
/*      */     //   14: iconst_1
/*      */     //   15: putfield 213	com/mysql/jdbc/ResultSetImpl:wasNullFlag	Z
/*      */     //   18: aconst_null
/*      */     //   19: areturn
/*      */     //   20: aload_1
/*      */     //   21: invokevirtual 857	java/lang/String:trim	()Ljava/lang/String;
/*      */     //   24: astore_1
/*      */     //   25: aload_1
/*      */     //   26: ldc_w 523
/*      */     //   29: invokevirtual 839	java/lang/String:equals	(Ljava/lang/Object;)Z
/*      */     //   32: ifne +33 -> 65
/*      */     //   35: aload_1
/*      */     //   36: ldc_w 1005
/*      */     //   39: invokevirtual 839	java/lang/String:equals	(Ljava/lang/Object;)Z
/*      */     //   42: ifne +23 -> 65
/*      */     //   45: aload_1
/*      */     //   46: ldc_w 1007
/*      */     //   49: invokevirtual 839	java/lang/String:equals	(Ljava/lang/Object;)Z
/*      */     //   52: ifne +13 -> 65
/*      */     //   55: aload_1
/*      */     //   56: ldc_w 1009
/*      */     //   59: invokevirtual 839	java/lang/String:equals	(Ljava/lang/Object;)Z
/*      */     //   62: ifeq +92 -> 154
/*      */     //   65: ldc_w 1011
/*      */     //   68: aload_0
/*      */     //   69: getfield 233	com/mysql/jdbc/ResultSetImpl:connection	Lcom/mysql/jdbc/MySQLConnection;
/*      */     //   72: invokeinterface 1014 1 0
/*      */     //   77: invokevirtual 839	java/lang/String:equals	(Ljava/lang/Object;)Z
/*      */     //   80: ifeq +10 -> 90
/*      */     //   83: aload_0
/*      */     //   84: iconst_1
/*      */     //   85: putfield 213	com/mysql/jdbc/ResultSetImpl:wasNullFlag	Z
/*      */     //   88: aconst_null
/*      */     //   89: areturn
/*      */     //   90: ldc_w 1016
/*      */     //   93: aload_0
/*      */     //   94: getfield 233	com/mysql/jdbc/ResultSetImpl:connection	Lcom/mysql/jdbc/MySQLConnection;
/*      */     //   97: invokeinterface 1014 1 0
/*      */     //   102: invokevirtual 839	java/lang/String:equals	(Ljava/lang/Object;)Z
/*      */     //   105: ifeq +40 -> 145
/*      */     //   108: new 636	java/lang/StringBuilder
/*      */     //   111: dup
/*      */     //   112: invokespecial 637	java/lang/StringBuilder:<init>	()V
/*      */     //   115: ldc_w 1018
/*      */     //   118: invokevirtual 643	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
/*      */     //   121: aload_1
/*      */     //   122: invokevirtual 643	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
/*      */     //   125: ldc_w 1667
/*      */     //   128: invokevirtual 643	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
/*      */     //   131: invokevirtual 648	java/lang/StringBuilder:toString	()Ljava/lang/String;
/*      */     //   134: ldc_w 406
/*      */     //   137: aload_0
/*      */     //   138: invokevirtual 407	com/mysql/jdbc/ResultSetImpl:getExceptionInterceptor	()Lcom/mysql/jdbc/ExceptionInterceptor;
/*      */     //   141: invokestatic 413	com/mysql/jdbc/SQLError:createSQLException	(Ljava/lang/String;Ljava/lang/String;Lcom/mysql/jdbc/ExceptionInterceptor;)Ljava/sql/SQLException;
/*      */     //   144: athrow
/*      */     //   145: aload_0
/*      */     //   146: aload_2
/*      */     //   147: iconst_0
/*      */     //   148: iconst_0
/*      */     //   149: iconst_0
/*      */     //   150: invokevirtual 1669	com/mysql/jdbc/ResultSetImpl:fastTimeCreate	(Ljava/util/Calendar;III)Ljava/sql/Time;
/*      */     //   153: areturn
/*      */     //   154: aload_0
/*      */     //   155: iconst_0
/*      */     //   156: putfield 213	com/mysql/jdbc/ResultSetImpl:wasNullFlag	Z
/*      */     //   159: aload_0
/*      */     //   160: getfield 231	com/mysql/jdbc/ResultSetImpl:fields	[Lcom/mysql/jdbc/Field;
/*      */     //   163: iload_3
/*      */     //   164: iconst_1
/*      */     //   165: isub
/*      */     //   166: aaload
/*      */     //   167: astore 9
/*      */     //   169: aload 9
/*      */     //   171: invokevirtual 724	com/mysql/jdbc/Field:getMysqlType	()I
/*      */     //   174: bipush 7
/*      */     //   176: if_icmpne +343 -> 519
/*      */     //   179: aload_1
/*      */     //   180: invokevirtual 694	java/lang/String:length	()I
/*      */     //   183: istore 10
/*      */     //   185: iload 10
/*      */     //   187: tableswitch	default:+192->379, 10:+160->347, 11:+192->379, 12:+107->294, 13:+192->379, 14:+107->294, 15:+192->379, 16:+192->379, 17:+192->379, 18:+192->379, 19:+53->240
/*      */     //   240: aload_1
/*      */     //   241: iload 10
/*      */     //   243: bipush 8
/*      */     //   245: isub
/*      */     //   246: iload 10
/*      */     //   248: bipush 6
/*      */     //   250: isub
/*      */     //   251: invokevirtual 1025	java/lang/String:substring	(II)Ljava/lang/String;
/*      */     //   254: invokestatic 1028	java/lang/Integer:parseInt	(Ljava/lang/String;)I
/*      */     //   257: istore 6
/*      */     //   259: aload_1
/*      */     //   260: iload 10
/*      */     //   262: iconst_5
/*      */     //   263: isub
/*      */     //   264: iload 10
/*      */     //   266: iconst_3
/*      */     //   267: isub
/*      */     //   268: invokevirtual 1025	java/lang/String:substring	(II)Ljava/lang/String;
/*      */     //   271: invokestatic 1028	java/lang/Integer:parseInt	(Ljava/lang/String;)I
/*      */     //   274: istore 7
/*      */     //   276: aload_1
/*      */     //   277: iload 10
/*      */     //   279: iconst_2
/*      */     //   280: isub
/*      */     //   281: iload 10
/*      */     //   283: invokevirtual 1025	java/lang/String:substring	(II)Ljava/lang/String;
/*      */     //   286: invokestatic 1028	java/lang/Integer:parseInt	(Ljava/lang/String;)I
/*      */     //   289: istore 8
/*      */     //   291: goto +145 -> 436
/*      */     //   294: aload_1
/*      */     //   295: iload 10
/*      */     //   297: bipush 6
/*      */     //   299: isub
/*      */     //   300: iload 10
/*      */     //   302: iconst_4
/*      */     //   303: isub
/*      */     //   304: invokevirtual 1025	java/lang/String:substring	(II)Ljava/lang/String;
/*      */     //   307: invokestatic 1028	java/lang/Integer:parseInt	(Ljava/lang/String;)I
/*      */     //   310: istore 6
/*      */     //   312: aload_1
/*      */     //   313: iload 10
/*      */     //   315: iconst_4
/*      */     //   316: isub
/*      */     //   317: iload 10
/*      */     //   319: iconst_2
/*      */     //   320: isub
/*      */     //   321: invokevirtual 1025	java/lang/String:substring	(II)Ljava/lang/String;
/*      */     //   324: invokestatic 1028	java/lang/Integer:parseInt	(Ljava/lang/String;)I
/*      */     //   327: istore 7
/*      */     //   329: aload_1
/*      */     //   330: iload 10
/*      */     //   332: iconst_2
/*      */     //   333: isub
/*      */     //   334: iload 10
/*      */     //   336: invokevirtual 1025	java/lang/String:substring	(II)Ljava/lang/String;
/*      */     //   339: invokestatic 1028	java/lang/Integer:parseInt	(Ljava/lang/String;)I
/*      */     //   342: istore 8
/*      */     //   344: goto +92 -> 436
/*      */     //   347: aload_1
/*      */     //   348: bipush 6
/*      */     //   350: bipush 8
/*      */     //   352: invokevirtual 1025	java/lang/String:substring	(II)Ljava/lang/String;
/*      */     //   355: invokestatic 1028	java/lang/Integer:parseInt	(Ljava/lang/String;)I
/*      */     //   358: istore 6
/*      */     //   360: aload_1
/*      */     //   361: bipush 8
/*      */     //   363: bipush 10
/*      */     //   365: invokevirtual 1025	java/lang/String:substring	(II)Ljava/lang/String;
/*      */     //   368: invokestatic 1028	java/lang/Integer:parseInt	(Ljava/lang/String;)I
/*      */     //   371: istore 7
/*      */     //   373: iconst_0
/*      */     //   374: istore 8
/*      */     //   376: goto +60 -> 436
/*      */     //   379: new 636	java/lang/StringBuilder
/*      */     //   382: dup
/*      */     //   383: invokespecial 637	java/lang/StringBuilder:<init>	()V
/*      */     //   386: ldc_w 1671
/*      */     //   389: invokestatic 404	com/mysql/jdbc/Messages:getString	(Ljava/lang/String;)Ljava/lang/String;
/*      */     //   392: invokevirtual 643	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
/*      */     //   395: iload_3
/*      */     //   396: invokevirtual 1146	java/lang/StringBuilder:append	(I)Ljava/lang/StringBuilder;
/*      */     //   399: ldc_w 1673
/*      */     //   402: invokevirtual 643	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
/*      */     //   405: aload_0
/*      */     //   406: getfield 231	com/mysql/jdbc/ResultSetImpl:fields	[Lcom/mysql/jdbc/Field;
/*      */     //   409: iload_3
/*      */     //   410: iconst_1
/*      */     //   411: isub
/*      */     //   412: aaload
/*      */     //   413: invokevirtual 1676	java/lang/StringBuilder:append	(Ljava/lang/Object;)Ljava/lang/StringBuilder;
/*      */     //   416: ldc_w 1678
/*      */     //   419: invokevirtual 643	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
/*      */     //   422: invokevirtual 648	java/lang/StringBuilder:toString	()Ljava/lang/String;
/*      */     //   425: ldc_w 406
/*      */     //   428: aload_0
/*      */     //   429: invokevirtual 407	com/mysql/jdbc/ResultSetImpl:getExceptionInterceptor	()Lcom/mysql/jdbc/ExceptionInterceptor;
/*      */     //   432: invokestatic 413	com/mysql/jdbc/SQLError:createSQLException	(Ljava/lang/String;Ljava/lang/String;Lcom/mysql/jdbc/ExceptionInterceptor;)Ljava/sql/SQLException;
/*      */     //   435: athrow
/*      */     //   436: new 1680	java/sql/SQLWarning
/*      */     //   439: dup
/*      */     //   440: new 636	java/lang/StringBuilder
/*      */     //   443: dup
/*      */     //   444: invokespecial 637	java/lang/StringBuilder:<init>	()V
/*      */     //   447: ldc_w 1682
/*      */     //   450: invokestatic 404	com/mysql/jdbc/Messages:getString	(Ljava/lang/String;)Ljava/lang/String;
/*      */     //   453: invokevirtual 643	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
/*      */     //   456: iload_3
/*      */     //   457: invokevirtual 1146	java/lang/StringBuilder:append	(I)Ljava/lang/StringBuilder;
/*      */     //   460: ldc_w 1673
/*      */     //   463: invokevirtual 643	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
/*      */     //   466: aload_0
/*      */     //   467: getfield 231	com/mysql/jdbc/ResultSetImpl:fields	[Lcom/mysql/jdbc/Field;
/*      */     //   470: iload_3
/*      */     //   471: iconst_1
/*      */     //   472: isub
/*      */     //   473: aaload
/*      */     //   474: invokevirtual 1676	java/lang/StringBuilder:append	(Ljava/lang/Object;)Ljava/lang/StringBuilder;
/*      */     //   477: ldc_w 1678
/*      */     //   480: invokevirtual 643	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
/*      */     //   483: invokevirtual 648	java/lang/StringBuilder:toString	()Ljava/lang/String;
/*      */     //   486: invokespecial 1683	java/sql/SQLWarning:<init>	(Ljava/lang/String;)V
/*      */     //   489: astore 11
/*      */     //   491: aload_0
/*      */     //   492: getfield 211	com/mysql/jdbc/ResultSetImpl:warningChain	Ljava/sql/SQLWarning;
/*      */     //   495: ifnonnull +12 -> 507
/*      */     //   498: aload_0
/*      */     //   499: aload 11
/*      */     //   501: putfield 211	com/mysql/jdbc/ResultSetImpl:warningChain	Ljava/sql/SQLWarning;
/*      */     //   504: goto +12 -> 516
/*      */     //   507: aload_0
/*      */     //   508: getfield 211	com/mysql/jdbc/ResultSetImpl:warningChain	Ljava/sql/SQLWarning;
/*      */     //   511: aload 11
/*      */     //   513: invokevirtual 1687	java/sql/SQLWarning:setNextWarning	(Ljava/sql/SQLWarning;)V
/*      */     //   516: goto +263 -> 779
/*      */     //   519: aload 9
/*      */     //   521: invokevirtual 724	com/mysql/jdbc/Field:getMysqlType	()I
/*      */     //   524: bipush 12
/*      */     //   526: if_icmpne +125 -> 651
/*      */     //   529: aload_1
/*      */     //   530: bipush 11
/*      */     //   532: bipush 13
/*      */     //   534: invokevirtual 1025	java/lang/String:substring	(II)Ljava/lang/String;
/*      */     //   537: invokestatic 1028	java/lang/Integer:parseInt	(Ljava/lang/String;)I
/*      */     //   540: istore 6
/*      */     //   542: aload_1
/*      */     //   543: bipush 14
/*      */     //   545: bipush 16
/*      */     //   547: invokevirtual 1025	java/lang/String:substring	(II)Ljava/lang/String;
/*      */     //   550: invokestatic 1028	java/lang/Integer:parseInt	(Ljava/lang/String;)I
/*      */     //   553: istore 7
/*      */     //   555: aload_1
/*      */     //   556: bipush 17
/*      */     //   558: bipush 19
/*      */     //   560: invokevirtual 1025	java/lang/String:substring	(II)Ljava/lang/String;
/*      */     //   563: invokestatic 1028	java/lang/Integer:parseInt	(Ljava/lang/String;)I
/*      */     //   566: istore 8
/*      */     //   568: new 1680	java/sql/SQLWarning
/*      */     //   571: dup
/*      */     //   572: new 636	java/lang/StringBuilder
/*      */     //   575: dup
/*      */     //   576: invokespecial 637	java/lang/StringBuilder:<init>	()V
/*      */     //   579: ldc_w 1689
/*      */     //   582: invokestatic 404	com/mysql/jdbc/Messages:getString	(Ljava/lang/String;)Ljava/lang/String;
/*      */     //   585: invokevirtual 643	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
/*      */     //   588: iload_3
/*      */     //   589: invokevirtual 1146	java/lang/StringBuilder:append	(I)Ljava/lang/StringBuilder;
/*      */     //   592: ldc_w 1673
/*      */     //   595: invokevirtual 643	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
/*      */     //   598: aload_0
/*      */     //   599: getfield 231	com/mysql/jdbc/ResultSetImpl:fields	[Lcom/mysql/jdbc/Field;
/*      */     //   602: iload_3
/*      */     //   603: iconst_1
/*      */     //   604: isub
/*      */     //   605: aaload
/*      */     //   606: invokevirtual 1676	java/lang/StringBuilder:append	(Ljava/lang/Object;)Ljava/lang/StringBuilder;
/*      */     //   609: ldc_w 1678
/*      */     //   612: invokevirtual 643	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
/*      */     //   615: invokevirtual 648	java/lang/StringBuilder:toString	()Ljava/lang/String;
/*      */     //   618: invokespecial 1683	java/sql/SQLWarning:<init>	(Ljava/lang/String;)V
/*      */     //   621: astore 10
/*      */     //   623: aload_0
/*      */     //   624: getfield 211	com/mysql/jdbc/ResultSetImpl:warningChain	Ljava/sql/SQLWarning;
/*      */     //   627: ifnonnull +12 -> 639
/*      */     //   630: aload_0
/*      */     //   631: aload 10
/*      */     //   633: putfield 211	com/mysql/jdbc/ResultSetImpl:warningChain	Ljava/sql/SQLWarning;
/*      */     //   636: goto +12 -> 648
/*      */     //   639: aload_0
/*      */     //   640: getfield 211	com/mysql/jdbc/ResultSetImpl:warningChain	Ljava/sql/SQLWarning;
/*      */     //   643: aload 10
/*      */     //   645: invokevirtual 1687	java/sql/SQLWarning:setNextWarning	(Ljava/sql/SQLWarning;)V
/*      */     //   648: goto +131 -> 779
/*      */     //   651: aload 9
/*      */     //   653: invokevirtual 724	com/mysql/jdbc/Field:getMysqlType	()I
/*      */     //   656: bipush 10
/*      */     //   658: if_icmpne +12 -> 670
/*      */     //   661: aload_0
/*      */     //   662: aload_2
/*      */     //   663: iconst_0
/*      */     //   664: iconst_0
/*      */     //   665: iconst_0
/*      */     //   666: invokevirtual 1669	com/mysql/jdbc/ResultSetImpl:fastTimeCreate	(Ljava/util/Calendar;III)Ljava/sql/Time;
/*      */     //   669: areturn
/*      */     //   670: aload_1
/*      */     //   671: invokevirtual 694	java/lang/String:length	()I
/*      */     //   674: iconst_5
/*      */     //   675: if_icmpeq +59 -> 734
/*      */     //   678: aload_1
/*      */     //   679: invokevirtual 694	java/lang/String:length	()I
/*      */     //   682: bipush 8
/*      */     //   684: if_icmpeq +50 -> 734
/*      */     //   687: new 636	java/lang/StringBuilder
/*      */     //   690: dup
/*      */     //   691: invokespecial 637	java/lang/StringBuilder:<init>	()V
/*      */     //   694: ldc_w 1691
/*      */     //   697: invokestatic 404	com/mysql/jdbc/Messages:getString	(Ljava/lang/String;)Ljava/lang/String;
/*      */     //   700: invokevirtual 643	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
/*      */     //   703: aload_1
/*      */     //   704: invokevirtual 643	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
/*      */     //   707: ldc_w 1693
/*      */     //   710: invokestatic 404	com/mysql/jdbc/Messages:getString	(Ljava/lang/String;)Ljava/lang/String;
/*      */     //   713: invokevirtual 643	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
/*      */     //   716: iload_3
/*      */     //   717: invokevirtual 1146	java/lang/StringBuilder:append	(I)Ljava/lang/StringBuilder;
/*      */     //   720: invokevirtual 648	java/lang/StringBuilder:toString	()Ljava/lang/String;
/*      */     //   723: ldc_w 406
/*      */     //   726: aload_0
/*      */     //   727: invokevirtual 407	com/mysql/jdbc/ResultSetImpl:getExceptionInterceptor	()Lcom/mysql/jdbc/ExceptionInterceptor;
/*      */     //   730: invokestatic 413	com/mysql/jdbc/SQLError:createSQLException	(Ljava/lang/String;Ljava/lang/String;Lcom/mysql/jdbc/ExceptionInterceptor;)Ljava/sql/SQLException;
/*      */     //   733: athrow
/*      */     //   734: aload_1
/*      */     //   735: iconst_0
/*      */     //   736: iconst_2
/*      */     //   737: invokevirtual 1025	java/lang/String:substring	(II)Ljava/lang/String;
/*      */     //   740: invokestatic 1028	java/lang/Integer:parseInt	(Ljava/lang/String;)I
/*      */     //   743: istore 6
/*      */     //   745: aload_1
/*      */     //   746: iconst_3
/*      */     //   747: iconst_5
/*      */     //   748: invokevirtual 1025	java/lang/String:substring	(II)Ljava/lang/String;
/*      */     //   751: invokestatic 1028	java/lang/Integer:parseInt	(Ljava/lang/String;)I
/*      */     //   754: istore 7
/*      */     //   756: aload_1
/*      */     //   757: invokevirtual 694	java/lang/String:length	()I
/*      */     //   760: iconst_5
/*      */     //   761: if_icmpne +7 -> 768
/*      */     //   764: iconst_0
/*      */     //   765: goto +12 -> 777
/*      */     //   768: aload_1
/*      */     //   769: bipush 6
/*      */     //   771: invokevirtual 1695	java/lang/String:substring	(I)Ljava/lang/String;
/*      */     //   774: invokestatic 1028	java/lang/Integer:parseInt	(Ljava/lang/String;)I
/*      */     //   777: istore 8
/*      */     //   779: aload_0
/*      */     //   780: invokevirtual 1696	com/mysql/jdbc/ResultSetImpl:getCalendarInstanceForSessionOrNew	()Ljava/util/Calendar;
/*      */     //   783: astore 10
/*      */     //   785: aload 10
/*      */     //   787: dup
/*      */     //   788: astore 11
/*      */     //   790: monitorenter
/*      */     //   791: aload_0
/*      */     //   792: getfield 233	com/mysql/jdbc/ResultSetImpl:connection	Lcom/mysql/jdbc/MySQLConnection;
/*      */     //   795: aload 10
/*      */     //   797: aload_2
/*      */     //   798: aload_0
/*      */     //   799: aload 10
/*      */     //   801: iload 6
/*      */     //   803: iload 7
/*      */     //   805: iload 8
/*      */     //   807: invokevirtual 1669	com/mysql/jdbc/ResultSetImpl:fastTimeCreate	(Ljava/util/Calendar;III)Ljava/sql/Time;
/*      */     //   810: aload_0
/*      */     //   811: getfield 233	com/mysql/jdbc/ResultSetImpl:connection	Lcom/mysql/jdbc/MySQLConnection;
/*      */     //   814: invokeinterface 250 1 0
/*      */     //   819: aload 4
/*      */     //   821: iload 5
/*      */     //   823: invokestatic 1700	com/mysql/jdbc/TimeUtil:changeTimezone	(Lcom/mysql/jdbc/MySQLConnection;Ljava/util/Calendar;Ljava/util/Calendar;Ljava/sql/Time;Ljava/util/TimeZone;Ljava/util/TimeZone;Z)Ljava/sql/Time;
/*      */     //   826: aload 11
/*      */     //   828: monitorexit
/*      */     //   829: areturn
/*      */     //   830: astore 12
/*      */     //   832: aload 11
/*      */     //   834: monitorexit
/*      */     //   835: aload 12
/*      */     //   837: athrow
/*      */     //   838: astore 9
/*      */     //   840: aload 9
/*      */     //   842: invokevirtual 1701	java/lang/Exception:toString	()Ljava/lang/String;
/*      */     //   845: ldc_w 406
/*      */     //   848: aload_0
/*      */     //   849: invokevirtual 407	com/mysql/jdbc/ResultSetImpl:getExceptionInterceptor	()Lcom/mysql/jdbc/ExceptionInterceptor;
/*      */     //   852: invokestatic 413	com/mysql/jdbc/SQLError:createSQLException	(Ljava/lang/String;Ljava/lang/String;Lcom/mysql/jdbc/ExceptionInterceptor;)Ljava/sql/SQLException;
/*      */     //   855: astore 10
/*      */     //   857: aload 10
/*      */     //   859: aload 9
/*      */     //   861: invokevirtual 1042	java/sql/SQLException:initCause	(Ljava/lang/Throwable;)Ljava/lang/Throwable;
/*      */     //   864: pop
/*      */     //   865: aload 10
/*      */     //   867: athrow
/*      */     // Line number table:
/*      */     //   Java source line #5852	-> byte code offset #0
/*      */     //   Java source line #5853	-> byte code offset #3
/*      */     //   Java source line #5854	-> byte code offset #6
/*      */     //   Java source line #5858	-> byte code offset #9
/*      */     //   Java source line #5859	-> byte code offset #13
/*      */     //   Java source line #5861	-> byte code offset #18
/*      */     //   Java source line #5872	-> byte code offset #20
/*      */     //   Java source line #5874	-> byte code offset #25
/*      */     //   Java source line #5878	-> byte code offset #65
/*      */     //   Java source line #5880	-> byte code offset #83
/*      */     //   Java source line #5882	-> byte code offset #88
/*      */     //   Java source line #5883	-> byte code offset #90
/*      */     //   Java source line #5885	-> byte code offset #108
/*      */     //   Java source line #5892	-> byte code offset #145
/*      */     //   Java source line #5895	-> byte code offset #154
/*      */     //   Java source line #5897	-> byte code offset #159
/*      */     //   Java source line #5899	-> byte code offset #169
/*      */     //   Java source line #5901	-> byte code offset #179
/*      */     //   Java source line #5903	-> byte code offset #185
/*      */     //   Java source line #5906	-> byte code offset #240
/*      */     //   Java source line #5908	-> byte code offset #259
/*      */     //   Java source line #5910	-> byte code offset #276
/*      */     //   Java source line #5914	-> byte code offset #291
/*      */     //   Java source line #5917	-> byte code offset #294
/*      */     //   Java source line #5919	-> byte code offset #312
/*      */     //   Java source line #5921	-> byte code offset #329
/*      */     //   Java source line #5925	-> byte code offset #344
/*      */     //   Java source line #5928	-> byte code offset #347
/*      */     //   Java source line #5929	-> byte code offset #360
/*      */     //   Java source line #5930	-> byte code offset #373
/*      */     //   Java source line #5933	-> byte code offset #376
/*      */     //   Java source line #5936	-> byte code offset #379
/*      */     //   Java source line #5945	-> byte code offset #436
/*      */     //   Java source line #5952	-> byte code offset #491
/*      */     //   Java source line #5953	-> byte code offset #498
/*      */     //   Java source line #5955	-> byte code offset #507
/*      */     //   Java source line #5957	-> byte code offset #516
/*      */     //   Java source line #5958	-> byte code offset #529
/*      */     //   Java source line #5959	-> byte code offset #542
/*      */     //   Java source line #5960	-> byte code offset #555
/*      */     //   Java source line #5962	-> byte code offset #568
/*      */     //   Java source line #5969	-> byte code offset #623
/*      */     //   Java source line #5970	-> byte code offset #630
/*      */     //   Java source line #5972	-> byte code offset #639
/*      */     //   Java source line #5974	-> byte code offset #648
/*      */     //   Java source line #5975	-> byte code offset #661
/*      */     //   Java source line #5979	-> byte code offset #670
/*      */     //   Java source line #5981	-> byte code offset #687
/*      */     //   Java source line #5988	-> byte code offset #734
/*      */     //   Java source line #5989	-> byte code offset #745
/*      */     //   Java source line #5990	-> byte code offset #756
/*      */     //   Java source line #5994	-> byte code offset #779
/*      */     //   Java source line #5996	-> byte code offset #785
/*      */     //   Java source line #5997	-> byte code offset #791
/*      */     //   Java source line #6004	-> byte code offset #830
/*      */     //   Java source line #6005	-> byte code offset #838
/*      */     //   Java source line #6006	-> byte code offset #840
/*      */     //   Java source line #6008	-> byte code offset #857
/*      */     //   Java source line #6010	-> byte code offset #865
/*      */     // Local variable table:
/*      */     //   start	length	slot	name	signature
/*      */     //   0	868	0	this	ResultSetImpl
/*      */     //   0	868	1	timeAsString	String
/*      */     //   0	868	2	targetCalendar	Calendar
/*      */     //   0	868	3	columnIndex	int
/*      */     //   0	868	4	tz	TimeZone
/*      */     //   0	868	5	rollForward	boolean
/*      */     //   1	801	6	hr	int
/*      */     //   4	800	7	min	int
/*      */     //   7	799	8	sec	int
/*      */     //   167	485	9	timeColField	Field
/*      */     //   838	22	9	ex	Exception
/*      */     //   183	152	10	length	int
/*      */     //   621	23	10	precisionLost	SQLWarning
/*      */     //   783	17	10	sessionCalendar	Calendar
/*      */     //   855	11	10	sqlEx	SQLException
/*      */     //   489	23	11	precisionLost	SQLWarning
/*      */     //   788	45	11	Ljava/lang/Object;	Object
/*      */     //   830	6	12	localObject1	Object
/*      */     // Exception table:
/*      */     //   from	to	target	type
/*      */     //   791	829	830	finally
/*      */     //   830	835	830	finally
/*      */     //   9	19	838	java/lang/Exception
/*      */     //   20	89	838	java/lang/Exception
/*      */     //   90	153	838	java/lang/Exception
/*      */     //   154	669	838	java/lang/Exception
/*      */     //   670	829	838	java/lang/Exception
/*      */     //   830	838	838	java/lang/Exception
/*      */   }
/*      */   
/*      */   /* Error */
/*      */   private Timestamp getTimestampFromString(int columnIndex, Calendar targetCalendar, String timestampValue, TimeZone tz, boolean rollForward)
/*      */     throws SQLException
/*      */   {
/*      */     // Byte code:
/*      */     //   0: aload_0
/*      */     //   1: iconst_0
/*      */     //   2: putfield 213	com/mysql/jdbc/ResultSetImpl:wasNullFlag	Z
/*      */     //   5: aload_3
/*      */     //   6: ifnonnull +10 -> 16
/*      */     //   9: aload_0
/*      */     //   10: iconst_1
/*      */     //   11: putfield 213	com/mysql/jdbc/ResultSetImpl:wasNullFlag	Z
/*      */     //   14: aconst_null
/*      */     //   15: areturn
/*      */     //   16: aload_3
/*      */     //   17: invokevirtual 857	java/lang/String:trim	()Ljava/lang/String;
/*      */     //   20: astore_3
/*      */     //   21: aload_3
/*      */     //   22: invokevirtual 694	java/lang/String:length	()I
/*      */     //   25: istore 6
/*      */     //   27: aload_0
/*      */     //   28: getfield 233	com/mysql/jdbc/ResultSetImpl:connection	Lcom/mysql/jdbc/MySQLConnection;
/*      */     //   31: invokeinterface 1722 1 0
/*      */     //   36: ifeq +15 -> 51
/*      */     //   39: aload_0
/*      */     //   40: getfield 233	com/mysql/jdbc/ResultSetImpl:connection	Lcom/mysql/jdbc/MySQLConnection;
/*      */     //   43: invokeinterface 1725 1 0
/*      */     //   48: goto +7 -> 55
/*      */     //   51: aload_0
/*      */     //   52: invokevirtual 1696	com/mysql/jdbc/ResultSetImpl:getCalendarInstanceForSessionOrNew	()Ljava/util/Calendar;
/*      */     //   55: astore 7
/*      */     //   57: aload 7
/*      */     //   59: dup
/*      */     //   60: astore 8
/*      */     //   62: monitorenter
/*      */     //   63: iload 6
/*      */     //   65: ifle +152 -> 217
/*      */     //   68: aload_3
/*      */     //   69: iconst_0
/*      */     //   70: invokevirtual 828	java/lang/String:charAt	(I)C
/*      */     //   73: bipush 48
/*      */     //   75: if_icmpne +142 -> 217
/*      */     //   78: aload_3
/*      */     //   79: ldc_w 1005
/*      */     //   82: invokevirtual 839	java/lang/String:equals	(Ljava/lang/Object;)Z
/*      */     //   85: ifne +33 -> 118
/*      */     //   88: aload_3
/*      */     //   89: ldc_w 1007
/*      */     //   92: invokevirtual 839	java/lang/String:equals	(Ljava/lang/Object;)Z
/*      */     //   95: ifne +23 -> 118
/*      */     //   98: aload_3
/*      */     //   99: ldc_w 1009
/*      */     //   102: invokevirtual 839	java/lang/String:equals	(Ljava/lang/Object;)Z
/*      */     //   105: ifne +13 -> 118
/*      */     //   108: aload_3
/*      */     //   109: ldc_w 523
/*      */     //   112: invokevirtual 839	java/lang/String:equals	(Ljava/lang/Object;)Z
/*      */     //   115: ifeq +102 -> 217
/*      */     //   118: ldc_w 1011
/*      */     //   121: aload_0
/*      */     //   122: getfield 233	com/mysql/jdbc/ResultSetImpl:connection	Lcom/mysql/jdbc/MySQLConnection;
/*      */     //   125: invokeinterface 1014 1 0
/*      */     //   130: invokevirtual 839	java/lang/String:equals	(Ljava/lang/Object;)Z
/*      */     //   133: ifeq +13 -> 146
/*      */     //   136: aload_0
/*      */     //   137: iconst_1
/*      */     //   138: putfield 213	com/mysql/jdbc/ResultSetImpl:wasNullFlag	Z
/*      */     //   141: aconst_null
/*      */     //   142: aload 8
/*      */     //   144: monitorexit
/*      */     //   145: areturn
/*      */     //   146: ldc_w 1016
/*      */     //   149: aload_0
/*      */     //   150: getfield 233	com/mysql/jdbc/ResultSetImpl:connection	Lcom/mysql/jdbc/MySQLConnection;
/*      */     //   153: invokeinterface 1014 1 0
/*      */     //   158: invokevirtual 839	java/lang/String:equals	(Ljava/lang/Object;)Z
/*      */     //   161: ifeq +40 -> 201
/*      */     //   164: new 636	java/lang/StringBuilder
/*      */     //   167: dup
/*      */     //   168: invokespecial 637	java/lang/StringBuilder:<init>	()V
/*      */     //   171: ldc_w 1018
/*      */     //   174: invokevirtual 643	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
/*      */     //   177: aload_3
/*      */     //   178: invokevirtual 643	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
/*      */     //   181: ldc_w 1727
/*      */     //   184: invokevirtual 643	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
/*      */     //   187: invokevirtual 648	java/lang/StringBuilder:toString	()Ljava/lang/String;
/*      */     //   190: ldc_w 406
/*      */     //   193: aload_0
/*      */     //   194: invokevirtual 407	com/mysql/jdbc/ResultSetImpl:getExceptionInterceptor	()Lcom/mysql/jdbc/ExceptionInterceptor;
/*      */     //   197: invokestatic 413	com/mysql/jdbc/SQLError:createSQLException	(Ljava/lang/String;Ljava/lang/String;Lcom/mysql/jdbc/ExceptionInterceptor;)Ljava/sql/SQLException;
/*      */     //   200: athrow
/*      */     //   201: aload_0
/*      */     //   202: aconst_null
/*      */     //   203: iconst_1
/*      */     //   204: iconst_1
/*      */     //   205: iconst_1
/*      */     //   206: iconst_0
/*      */     //   207: iconst_0
/*      */     //   208: iconst_0
/*      */     //   209: iconst_0
/*      */     //   210: invokevirtual 1729	com/mysql/jdbc/ResultSetImpl:fastTimestampCreate	(Ljava/util/Calendar;IIIIIII)Ljava/sql/Timestamp;
/*      */     //   213: aload 8
/*      */     //   215: monitorexit
/*      */     //   216: areturn
/*      */     //   217: aload_0
/*      */     //   218: getfield 231	com/mysql/jdbc/ResultSetImpl:fields	[Lcom/mysql/jdbc/Field;
/*      */     //   221: iload_1
/*      */     //   222: iconst_1
/*      */     //   223: isub
/*      */     //   224: aaload
/*      */     //   225: invokevirtual 724	com/mysql/jdbc/Field:getMysqlType	()I
/*      */     //   228: bipush 13
/*      */     //   230: if_icmpne +82 -> 312
/*      */     //   233: aload_0
/*      */     //   234: getfield 260	com/mysql/jdbc/ResultSetImpl:useLegacyDatetimeCode	Z
/*      */     //   237: ifne +27 -> 264
/*      */     //   240: aload 4
/*      */     //   242: aload_3
/*      */     //   243: iconst_0
/*      */     //   244: iconst_4
/*      */     //   245: invokevirtual 1025	java/lang/String:substring	(II)Ljava/lang/String;
/*      */     //   248: invokestatic 1028	java/lang/Integer:parseInt	(Ljava/lang/String;)I
/*      */     //   251: iconst_1
/*      */     //   252: iconst_1
/*      */     //   253: iconst_0
/*      */     //   254: iconst_0
/*      */     //   255: iconst_0
/*      */     //   256: iconst_0
/*      */     //   257: invokestatic 612	com/mysql/jdbc/TimeUtil:fastTimestampCreate	(Ljava/util/TimeZone;IIIIIII)Ljava/sql/Timestamp;
/*      */     //   260: aload 8
/*      */     //   262: monitorexit
/*      */     //   263: areturn
/*      */     //   264: aload_0
/*      */     //   265: getfield 233	com/mysql/jdbc/ResultSetImpl:connection	Lcom/mysql/jdbc/MySQLConnection;
/*      */     //   268: aload 7
/*      */     //   270: aload_2
/*      */     //   271: aload_0
/*      */     //   272: aload 7
/*      */     //   274: aload_3
/*      */     //   275: iconst_0
/*      */     //   276: iconst_4
/*      */     //   277: invokevirtual 1025	java/lang/String:substring	(II)Ljava/lang/String;
/*      */     //   280: invokestatic 1028	java/lang/Integer:parseInt	(Ljava/lang/String;)I
/*      */     //   283: iconst_1
/*      */     //   284: iconst_1
/*      */     //   285: iconst_0
/*      */     //   286: iconst_0
/*      */     //   287: iconst_0
/*      */     //   288: iconst_0
/*      */     //   289: invokevirtual 1729	com/mysql/jdbc/ResultSetImpl:fastTimestampCreate	(Ljava/util/Calendar;IIIIIII)Ljava/sql/Timestamp;
/*      */     //   292: aload_0
/*      */     //   293: getfield 233	com/mysql/jdbc/ResultSetImpl:connection	Lcom/mysql/jdbc/MySQLConnection;
/*      */     //   296: invokeinterface 250 1 0
/*      */     //   301: aload 4
/*      */     //   303: iload 5
/*      */     //   305: invokestatic 1732	com/mysql/jdbc/TimeUtil:changeTimezone	(Lcom/mysql/jdbc/MySQLConnection;Ljava/util/Calendar;Ljava/util/Calendar;Ljava/sql/Timestamp;Ljava/util/TimeZone;Ljava/util/TimeZone;Z)Ljava/sql/Timestamp;
/*      */     //   308: aload 8
/*      */     //   310: monitorexit
/*      */     //   311: areturn
/*      */     //   312: aload_3
/*      */     //   313: ldc_w 859
/*      */     //   316: invokevirtual 1361	java/lang/String:endsWith	(Ljava/lang/String;)Z
/*      */     //   319: ifeq +15 -> 334
/*      */     //   322: aload_3
/*      */     //   323: iconst_0
/*      */     //   324: aload_3
/*      */     //   325: invokevirtual 694	java/lang/String:length	()I
/*      */     //   328: iconst_1
/*      */     //   329: isub
/*      */     //   330: invokevirtual 1025	java/lang/String:substring	(II)Ljava/lang/String;
/*      */     //   333: astore_3
/*      */     //   334: iconst_0
/*      */     //   335: istore 9
/*      */     //   337: iconst_0
/*      */     //   338: istore 10
/*      */     //   340: iconst_0
/*      */     //   341: istore 11
/*      */     //   343: iconst_0
/*      */     //   344: istore 12
/*      */     //   346: iconst_0
/*      */     //   347: istore 13
/*      */     //   349: iconst_0
/*      */     //   350: istore 14
/*      */     //   352: iconst_0
/*      */     //   353: istore 15
/*      */     //   355: iload 6
/*      */     //   357: tableswitch	default:+870->1227, 2:+830->1187, 3:+870->1227, 4:+782->1139, 5:+870->1227, 6:+725->1082, 7:+870->1227, 8:+617->974, 9:+870->1227, 10:+462->819, 11:+870->1227, 12:+366->723, 13:+870->1227, 14:+288->645, 15:+870->1227, 16:+870->1227, 17:+870->1227, 18:+870->1227, 19:+115->472, 20:+115->472, 21:+115->472, 22:+115->472, 23:+115->472, 24:+115->472, 25:+115->472, 26:+115->472
/*      */     //   472: aload_3
/*      */     //   473: iconst_0
/*      */     //   474: iconst_4
/*      */     //   475: invokevirtual 1025	java/lang/String:substring	(II)Ljava/lang/String;
/*      */     //   478: invokestatic 1028	java/lang/Integer:parseInt	(Ljava/lang/String;)I
/*      */     //   481: istore 9
/*      */     //   483: aload_3
/*      */     //   484: iconst_5
/*      */     //   485: bipush 7
/*      */     //   487: invokevirtual 1025	java/lang/String:substring	(II)Ljava/lang/String;
/*      */     //   490: invokestatic 1028	java/lang/Integer:parseInt	(Ljava/lang/String;)I
/*      */     //   493: istore 10
/*      */     //   495: aload_3
/*      */     //   496: bipush 8
/*      */     //   498: bipush 10
/*      */     //   500: invokevirtual 1025	java/lang/String:substring	(II)Ljava/lang/String;
/*      */     //   503: invokestatic 1028	java/lang/Integer:parseInt	(Ljava/lang/String;)I
/*      */     //   506: istore 11
/*      */     //   508: aload_3
/*      */     //   509: bipush 11
/*      */     //   511: bipush 13
/*      */     //   513: invokevirtual 1025	java/lang/String:substring	(II)Ljava/lang/String;
/*      */     //   516: invokestatic 1028	java/lang/Integer:parseInt	(Ljava/lang/String;)I
/*      */     //   519: istore 12
/*      */     //   521: aload_3
/*      */     //   522: bipush 14
/*      */     //   524: bipush 16
/*      */     //   526: invokevirtual 1025	java/lang/String:substring	(II)Ljava/lang/String;
/*      */     //   529: invokestatic 1028	java/lang/Integer:parseInt	(Ljava/lang/String;)I
/*      */     //   532: istore 13
/*      */     //   534: aload_3
/*      */     //   535: bipush 17
/*      */     //   537: bipush 19
/*      */     //   539: invokevirtual 1025	java/lang/String:substring	(II)Ljava/lang/String;
/*      */     //   542: invokestatic 1028	java/lang/Integer:parseInt	(Ljava/lang/String;)I
/*      */     //   545: istore 14
/*      */     //   547: iconst_0
/*      */     //   548: istore 15
/*      */     //   550: iload 6
/*      */     //   552: bipush 19
/*      */     //   554: if_icmple +720 -> 1274
/*      */     //   557: aload_3
/*      */     //   558: bipush 46
/*      */     //   560: invokevirtual 1735	java/lang/String:lastIndexOf	(I)I
/*      */     //   563: istore 16
/*      */     //   565: iload 16
/*      */     //   567: iconst_m1
/*      */     //   568: if_icmpeq +74 -> 642
/*      */     //   571: iload 16
/*      */     //   573: iconst_2
/*      */     //   574: iadd
/*      */     //   575: iload 6
/*      */     //   577: if_icmpgt +57 -> 634
/*      */     //   580: aload_3
/*      */     //   581: iload 16
/*      */     //   583: iconst_1
/*      */     //   584: iadd
/*      */     //   585: invokevirtual 1695	java/lang/String:substring	(I)Ljava/lang/String;
/*      */     //   588: invokestatic 1028	java/lang/Integer:parseInt	(Ljava/lang/String;)I
/*      */     //   591: istore 15
/*      */     //   593: iload 6
/*      */     //   595: iload 16
/*      */     //   597: iconst_1
/*      */     //   598: iadd
/*      */     //   599: isub
/*      */     //   600: istore 17
/*      */     //   602: iload 17
/*      */     //   604: bipush 9
/*      */     //   606: if_icmpge +25 -> 631
/*      */     //   609: ldc2_w 1736
/*      */     //   612: bipush 9
/*      */     //   614: iload 17
/*      */     //   616: isub
/*      */     //   617: i2d
/*      */     //   618: invokestatic 1743	java/lang/Math:pow	(DD)D
/*      */     //   621: d2i
/*      */     //   622: istore 18
/*      */     //   624: iload 15
/*      */     //   626: iload 18
/*      */     //   628: imul
/*      */     //   629: istore 15
/*      */     //   631: goto +11 -> 642
/*      */     //   634: new 1745	java/lang/IllegalArgumentException
/*      */     //   637: dup
/*      */     //   638: invokespecial 1746	java/lang/IllegalArgumentException:<init>	()V
/*      */     //   641: athrow
/*      */     //   642: goto +632 -> 1274
/*      */     //   645: aload_3
/*      */     //   646: iconst_0
/*      */     //   647: iconst_4
/*      */     //   648: invokevirtual 1025	java/lang/String:substring	(II)Ljava/lang/String;
/*      */     //   651: invokestatic 1028	java/lang/Integer:parseInt	(Ljava/lang/String;)I
/*      */     //   654: istore 9
/*      */     //   656: aload_3
/*      */     //   657: iconst_4
/*      */     //   658: bipush 6
/*      */     //   660: invokevirtual 1025	java/lang/String:substring	(II)Ljava/lang/String;
/*      */     //   663: invokestatic 1028	java/lang/Integer:parseInt	(Ljava/lang/String;)I
/*      */     //   666: istore 10
/*      */     //   668: aload_3
/*      */     //   669: bipush 6
/*      */     //   671: bipush 8
/*      */     //   673: invokevirtual 1025	java/lang/String:substring	(II)Ljava/lang/String;
/*      */     //   676: invokestatic 1028	java/lang/Integer:parseInt	(Ljava/lang/String;)I
/*      */     //   679: istore 11
/*      */     //   681: aload_3
/*      */     //   682: bipush 8
/*      */     //   684: bipush 10
/*      */     //   686: invokevirtual 1025	java/lang/String:substring	(II)Ljava/lang/String;
/*      */     //   689: invokestatic 1028	java/lang/Integer:parseInt	(Ljava/lang/String;)I
/*      */     //   692: istore 12
/*      */     //   694: aload_3
/*      */     //   695: bipush 10
/*      */     //   697: bipush 12
/*      */     //   699: invokevirtual 1025	java/lang/String:substring	(II)Ljava/lang/String;
/*      */     //   702: invokestatic 1028	java/lang/Integer:parseInt	(Ljava/lang/String;)I
/*      */     //   705: istore 13
/*      */     //   707: aload_3
/*      */     //   708: bipush 12
/*      */     //   710: bipush 14
/*      */     //   712: invokevirtual 1025	java/lang/String:substring	(II)Ljava/lang/String;
/*      */     //   715: invokestatic 1028	java/lang/Integer:parseInt	(Ljava/lang/String;)I
/*      */     //   718: istore 14
/*      */     //   720: goto +554 -> 1274
/*      */     //   723: aload_3
/*      */     //   724: iconst_0
/*      */     //   725: iconst_2
/*      */     //   726: invokevirtual 1025	java/lang/String:substring	(II)Ljava/lang/String;
/*      */     //   729: invokestatic 1028	java/lang/Integer:parseInt	(Ljava/lang/String;)I
/*      */     //   732: istore 9
/*      */     //   734: iload 9
/*      */     //   736: bipush 69
/*      */     //   738: if_icmpgt +10 -> 748
/*      */     //   741: iload 9
/*      */     //   743: bipush 100
/*      */     //   745: iadd
/*      */     //   746: istore 9
/*      */     //   748: wide
/*      */     //   754: aload_3
/*      */     //   755: iconst_2
/*      */     //   756: iconst_4
/*      */     //   757: invokevirtual 1025	java/lang/String:substring	(II)Ljava/lang/String;
/*      */     //   760: invokestatic 1028	java/lang/Integer:parseInt	(Ljava/lang/String;)I
/*      */     //   763: istore 10
/*      */     //   765: aload_3
/*      */     //   766: iconst_4
/*      */     //   767: bipush 6
/*      */     //   769: invokevirtual 1025	java/lang/String:substring	(II)Ljava/lang/String;
/*      */     //   772: invokestatic 1028	java/lang/Integer:parseInt	(Ljava/lang/String;)I
/*      */     //   775: istore 11
/*      */     //   777: aload_3
/*      */     //   778: bipush 6
/*      */     //   780: bipush 8
/*      */     //   782: invokevirtual 1025	java/lang/String:substring	(II)Ljava/lang/String;
/*      */     //   785: invokestatic 1028	java/lang/Integer:parseInt	(Ljava/lang/String;)I
/*      */     //   788: istore 12
/*      */     //   790: aload_3
/*      */     //   791: bipush 8
/*      */     //   793: bipush 10
/*      */     //   795: invokevirtual 1025	java/lang/String:substring	(II)Ljava/lang/String;
/*      */     //   798: invokestatic 1028	java/lang/Integer:parseInt	(Ljava/lang/String;)I
/*      */     //   801: istore 13
/*      */     //   803: aload_3
/*      */     //   804: bipush 10
/*      */     //   806: bipush 12
/*      */     //   808: invokevirtual 1025	java/lang/String:substring	(II)Ljava/lang/String;
/*      */     //   811: invokestatic 1028	java/lang/Integer:parseInt	(Ljava/lang/String;)I
/*      */     //   814: istore 14
/*      */     //   816: goto +458 -> 1274
/*      */     //   819: aload_0
/*      */     //   820: getfield 231	com/mysql/jdbc/ResultSetImpl:fields	[Lcom/mysql/jdbc/Field;
/*      */     //   823: iload_1
/*      */     //   824: iconst_1
/*      */     //   825: isub
/*      */     //   826: aaload
/*      */     //   827: invokevirtual 724	com/mysql/jdbc/Field:getMysqlType	()I
/*      */     //   830: bipush 10
/*      */     //   832: if_icmpeq +14 -> 846
/*      */     //   835: aload_3
/*      */     //   836: ldc_w 1748
/*      */     //   839: invokevirtual 862	java/lang/String:indexOf	(Ljava/lang/String;)I
/*      */     //   842: iconst_m1
/*      */     //   843: if_icmpeq +48 -> 891
/*      */     //   846: aload_3
/*      */     //   847: iconst_0
/*      */     //   848: iconst_4
/*      */     //   849: invokevirtual 1025	java/lang/String:substring	(II)Ljava/lang/String;
/*      */     //   852: invokestatic 1028	java/lang/Integer:parseInt	(Ljava/lang/String;)I
/*      */     //   855: istore 9
/*      */     //   857: aload_3
/*      */     //   858: iconst_5
/*      */     //   859: bipush 7
/*      */     //   861: invokevirtual 1025	java/lang/String:substring	(II)Ljava/lang/String;
/*      */     //   864: invokestatic 1028	java/lang/Integer:parseInt	(Ljava/lang/String;)I
/*      */     //   867: istore 10
/*      */     //   869: aload_3
/*      */     //   870: bipush 8
/*      */     //   872: bipush 10
/*      */     //   874: invokevirtual 1025	java/lang/String:substring	(II)Ljava/lang/String;
/*      */     //   877: invokestatic 1028	java/lang/Integer:parseInt	(Ljava/lang/String;)I
/*      */     //   880: istore 11
/*      */     //   882: iconst_0
/*      */     //   883: istore 12
/*      */     //   885: iconst_0
/*      */     //   886: istore 13
/*      */     //   888: goto +386 -> 1274
/*      */     //   891: aload_3
/*      */     //   892: iconst_0
/*      */     //   893: iconst_2
/*      */     //   894: invokevirtual 1025	java/lang/String:substring	(II)Ljava/lang/String;
/*      */     //   897: invokestatic 1028	java/lang/Integer:parseInt	(Ljava/lang/String;)I
/*      */     //   900: istore 9
/*      */     //   902: iload 9
/*      */     //   904: bipush 69
/*      */     //   906: if_icmpgt +10 -> 916
/*      */     //   909: iload 9
/*      */     //   911: bipush 100
/*      */     //   913: iadd
/*      */     //   914: istore 9
/*      */     //   916: aload_3
/*      */     //   917: iconst_2
/*      */     //   918: iconst_4
/*      */     //   919: invokevirtual 1025	java/lang/String:substring	(II)Ljava/lang/String;
/*      */     //   922: invokestatic 1028	java/lang/Integer:parseInt	(Ljava/lang/String;)I
/*      */     //   925: istore 10
/*      */     //   927: aload_3
/*      */     //   928: iconst_4
/*      */     //   929: bipush 6
/*      */     //   931: invokevirtual 1025	java/lang/String:substring	(II)Ljava/lang/String;
/*      */     //   934: invokestatic 1028	java/lang/Integer:parseInt	(Ljava/lang/String;)I
/*      */     //   937: istore 11
/*      */     //   939: aload_3
/*      */     //   940: bipush 6
/*      */     //   942: bipush 8
/*      */     //   944: invokevirtual 1025	java/lang/String:substring	(II)Ljava/lang/String;
/*      */     //   947: invokestatic 1028	java/lang/Integer:parseInt	(Ljava/lang/String;)I
/*      */     //   950: istore 12
/*      */     //   952: aload_3
/*      */     //   953: bipush 8
/*      */     //   955: bipush 10
/*      */     //   957: invokevirtual 1025	java/lang/String:substring	(II)Ljava/lang/String;
/*      */     //   960: invokestatic 1028	java/lang/Integer:parseInt	(Ljava/lang/String;)I
/*      */     //   963: istore 13
/*      */     //   965: wide
/*      */     //   971: goto +303 -> 1274
/*      */     //   974: aload_3
/*      */     //   975: ldc_w 1750
/*      */     //   978: invokevirtual 862	java/lang/String:indexOf	(Ljava/lang/String;)I
/*      */     //   981: iconst_m1
/*      */     //   982: if_icmpeq +52 -> 1034
/*      */     //   985: aload_3
/*      */     //   986: iconst_0
/*      */     //   987: iconst_2
/*      */     //   988: invokevirtual 1025	java/lang/String:substring	(II)Ljava/lang/String;
/*      */     //   991: invokestatic 1028	java/lang/Integer:parseInt	(Ljava/lang/String;)I
/*      */     //   994: istore 12
/*      */     //   996: aload_3
/*      */     //   997: iconst_3
/*      */     //   998: iconst_5
/*      */     //   999: invokevirtual 1025	java/lang/String:substring	(II)Ljava/lang/String;
/*      */     //   1002: invokestatic 1028	java/lang/Integer:parseInt	(Ljava/lang/String;)I
/*      */     //   1005: istore 13
/*      */     //   1007: aload_3
/*      */     //   1008: bipush 6
/*      */     //   1010: bipush 8
/*      */     //   1012: invokevirtual 1025	java/lang/String:substring	(II)Ljava/lang/String;
/*      */     //   1015: invokestatic 1028	java/lang/Integer:parseInt	(Ljava/lang/String;)I
/*      */     //   1018: istore 14
/*      */     //   1020: sipush 1970
/*      */     //   1023: istore 9
/*      */     //   1025: iconst_1
/*      */     //   1026: istore 10
/*      */     //   1028: iconst_1
/*      */     //   1029: istore 11
/*      */     //   1031: goto +243 -> 1274
/*      */     //   1034: aload_3
/*      */     //   1035: iconst_0
/*      */     //   1036: iconst_4
/*      */     //   1037: invokevirtual 1025	java/lang/String:substring	(II)Ljava/lang/String;
/*      */     //   1040: invokestatic 1028	java/lang/Integer:parseInt	(Ljava/lang/String;)I
/*      */     //   1043: istore 9
/*      */     //   1045: aload_3
/*      */     //   1046: iconst_4
/*      */     //   1047: bipush 6
/*      */     //   1049: invokevirtual 1025	java/lang/String:substring	(II)Ljava/lang/String;
/*      */     //   1052: invokestatic 1028	java/lang/Integer:parseInt	(Ljava/lang/String;)I
/*      */     //   1055: istore 10
/*      */     //   1057: aload_3
/*      */     //   1058: bipush 6
/*      */     //   1060: bipush 8
/*      */     //   1062: invokevirtual 1025	java/lang/String:substring	(II)Ljava/lang/String;
/*      */     //   1065: invokestatic 1028	java/lang/Integer:parseInt	(Ljava/lang/String;)I
/*      */     //   1068: istore 11
/*      */     //   1070: wide
/*      */     //   1076: iinc 10 -1
/*      */     //   1079: goto +195 -> 1274
/*      */     //   1082: aload_3
/*      */     //   1083: iconst_0
/*      */     //   1084: iconst_2
/*      */     //   1085: invokevirtual 1025	java/lang/String:substring	(II)Ljava/lang/String;
/*      */     //   1088: invokestatic 1028	java/lang/Integer:parseInt	(Ljava/lang/String;)I
/*      */     //   1091: istore 9
/*      */     //   1093: iload 9
/*      */     //   1095: bipush 69
/*      */     //   1097: if_icmpgt +10 -> 1107
/*      */     //   1100: iload 9
/*      */     //   1102: bipush 100
/*      */     //   1104: iadd
/*      */     //   1105: istore 9
/*      */     //   1107: wide
/*      */     //   1113: aload_3
/*      */     //   1114: iconst_2
/*      */     //   1115: iconst_4
/*      */     //   1116: invokevirtual 1025	java/lang/String:substring	(II)Ljava/lang/String;
/*      */     //   1119: invokestatic 1028	java/lang/Integer:parseInt	(Ljava/lang/String;)I
/*      */     //   1122: istore 10
/*      */     //   1124: aload_3
/*      */     //   1125: iconst_4
/*      */     //   1126: bipush 6
/*      */     //   1128: invokevirtual 1025	java/lang/String:substring	(II)Ljava/lang/String;
/*      */     //   1131: invokestatic 1028	java/lang/Integer:parseInt	(Ljava/lang/String;)I
/*      */     //   1134: istore 11
/*      */     //   1136: goto +138 -> 1274
/*      */     //   1139: aload_3
/*      */     //   1140: iconst_0
/*      */     //   1141: iconst_2
/*      */     //   1142: invokevirtual 1025	java/lang/String:substring	(II)Ljava/lang/String;
/*      */     //   1145: invokestatic 1028	java/lang/Integer:parseInt	(Ljava/lang/String;)I
/*      */     //   1148: istore 9
/*      */     //   1150: iload 9
/*      */     //   1152: bipush 69
/*      */     //   1154: if_icmpgt +10 -> 1164
/*      */     //   1157: iload 9
/*      */     //   1159: bipush 100
/*      */     //   1161: iadd
/*      */     //   1162: istore 9
/*      */     //   1164: wide
/*      */     //   1170: aload_3
/*      */     //   1171: iconst_2
/*      */     //   1172: iconst_4
/*      */     //   1173: invokevirtual 1025	java/lang/String:substring	(II)Ljava/lang/String;
/*      */     //   1176: invokestatic 1028	java/lang/Integer:parseInt	(Ljava/lang/String;)I
/*      */     //   1179: istore 10
/*      */     //   1181: iconst_1
/*      */     //   1182: istore 11
/*      */     //   1184: goto +90 -> 1274
/*      */     //   1187: aload_3
/*      */     //   1188: iconst_0
/*      */     //   1189: iconst_2
/*      */     //   1190: invokevirtual 1025	java/lang/String:substring	(II)Ljava/lang/String;
/*      */     //   1193: invokestatic 1028	java/lang/Integer:parseInt	(Ljava/lang/String;)I
/*      */     //   1196: istore 9
/*      */     //   1198: iload 9
/*      */     //   1200: bipush 69
/*      */     //   1202: if_icmpgt +10 -> 1212
/*      */     //   1205: iload 9
/*      */     //   1207: bipush 100
/*      */     //   1209: iadd
/*      */     //   1210: istore 9
/*      */     //   1212: wide
/*      */     //   1218: iconst_1
/*      */     //   1219: istore 10
/*      */     //   1221: iconst_1
/*      */     //   1222: istore 11
/*      */     //   1224: goto +50 -> 1274
/*      */     //   1227: new 107	java/sql/SQLException
/*      */     //   1230: dup
/*      */     //   1231: new 636	java/lang/StringBuilder
/*      */     //   1234: dup
/*      */     //   1235: invokespecial 637	java/lang/StringBuilder:<init>	()V
/*      */     //   1238: ldc_w 1752
/*      */     //   1241: invokevirtual 643	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
/*      */     //   1244: aload_3
/*      */     //   1245: invokevirtual 643	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
/*      */     //   1248: ldc_w 1754
/*      */     //   1251: invokevirtual 643	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
/*      */     //   1254: iload_1
/*      */     //   1255: invokevirtual 1146	java/lang/StringBuilder:append	(I)Ljava/lang/StringBuilder;
/*      */     //   1258: ldc_w 859
/*      */     //   1261: invokevirtual 643	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
/*      */     //   1264: invokevirtual 648	java/lang/StringBuilder:toString	()Ljava/lang/String;
/*      */     //   1267: ldc_w 406
/*      */     //   1270: invokespecial 749	java/sql/SQLException:<init>	(Ljava/lang/String;Ljava/lang/String;)V
/*      */     //   1273: athrow
/*      */     //   1274: aload_0
/*      */     //   1275: getfield 260	com/mysql/jdbc/ResultSetImpl:useLegacyDatetimeCode	Z
/*      */     //   1278: ifne +26 -> 1304
/*      */     //   1281: aload 4
/*      */     //   1283: iload 9
/*      */     //   1285: iload 10
/*      */     //   1287: iload 11
/*      */     //   1289: iload 12
/*      */     //   1291: iload 13
/*      */     //   1293: iload 14
/*      */     //   1295: iload 15
/*      */     //   1297: invokestatic 612	com/mysql/jdbc/TimeUtil:fastTimestampCreate	(Ljava/util/TimeZone;IIIIIII)Ljava/sql/Timestamp;
/*      */     //   1300: aload 8
/*      */     //   1302: monitorexit
/*      */     //   1303: areturn
/*      */     //   1304: aload_0
/*      */     //   1305: getfield 233	com/mysql/jdbc/ResultSetImpl:connection	Lcom/mysql/jdbc/MySQLConnection;
/*      */     //   1308: aload 7
/*      */     //   1310: aload_2
/*      */     //   1311: aload_0
/*      */     //   1312: aload 7
/*      */     //   1314: iload 9
/*      */     //   1316: iload 10
/*      */     //   1318: iload 11
/*      */     //   1320: iload 12
/*      */     //   1322: iload 13
/*      */     //   1324: iload 14
/*      */     //   1326: iload 15
/*      */     //   1328: invokevirtual 1729	com/mysql/jdbc/ResultSetImpl:fastTimestampCreate	(Ljava/util/Calendar;IIIIIII)Ljava/sql/Timestamp;
/*      */     //   1331: aload_0
/*      */     //   1332: getfield 233	com/mysql/jdbc/ResultSetImpl:connection	Lcom/mysql/jdbc/MySQLConnection;
/*      */     //   1335: invokeinterface 250 1 0
/*      */     //   1340: aload 4
/*      */     //   1342: iload 5
/*      */     //   1344: invokestatic 1732	com/mysql/jdbc/TimeUtil:changeTimezone	(Lcom/mysql/jdbc/MySQLConnection;Ljava/util/Calendar;Ljava/util/Calendar;Ljava/sql/Timestamp;Ljava/util/TimeZone;Ljava/util/TimeZone;Z)Ljava/sql/Timestamp;
/*      */     //   1347: aload 8
/*      */     //   1349: monitorexit
/*      */     //   1350: areturn
/*      */     //   1351: astore 19
/*      */     //   1353: aload 8
/*      */     //   1355: monitorexit
/*      */     //   1356: aload 19
/*      */     //   1358: athrow
/*      */     //   1359: astore 6
/*      */     //   1361: new 636	java/lang/StringBuilder
/*      */     //   1364: dup
/*      */     //   1365: invokespecial 637	java/lang/StringBuilder:<init>	()V
/*      */     //   1368: ldc_w 1756
/*      */     //   1371: invokevirtual 643	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
/*      */     //   1374: aload_3
/*      */     //   1375: invokevirtual 643	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
/*      */     //   1378: ldc_w 1758
/*      */     //   1381: invokevirtual 643	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
/*      */     //   1384: iload_1
/*      */     //   1385: invokevirtual 1146	java/lang/StringBuilder:append	(I)Ljava/lang/StringBuilder;
/*      */     //   1388: ldc_w 1760
/*      */     //   1391: invokevirtual 643	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
/*      */     //   1394: invokevirtual 648	java/lang/StringBuilder:toString	()Ljava/lang/String;
/*      */     //   1397: ldc_w 406
/*      */     //   1400: aload_0
/*      */     //   1401: invokevirtual 407	com/mysql/jdbc/ResultSetImpl:getExceptionInterceptor	()Lcom/mysql/jdbc/ExceptionInterceptor;
/*      */     //   1404: invokestatic 413	com/mysql/jdbc/SQLError:createSQLException	(Ljava/lang/String;Ljava/lang/String;Lcom/mysql/jdbc/ExceptionInterceptor;)Ljava/sql/SQLException;
/*      */     //   1407: astore 7
/*      */     //   1409: aload 7
/*      */     //   1411: aload 6
/*      */     //   1413: invokevirtual 1042	java/sql/SQLException:initCause	(Ljava/lang/Throwable;)Ljava/lang/Throwable;
/*      */     //   1416: pop
/*      */     //   1417: aload 7
/*      */     //   1419: athrow
/*      */     // Line number table:
/*      */     //   Java source line #6140	-> byte code offset #0
/*      */     //   Java source line #6142	-> byte code offset #5
/*      */     //   Java source line #6143	-> byte code offset #9
/*      */     //   Java source line #6145	-> byte code offset #14
/*      */     //   Java source line #6156	-> byte code offset #16
/*      */     //   Java source line #6158	-> byte code offset #21
/*      */     //   Java source line #6160	-> byte code offset #27
/*      */     //   Java source line #6164	-> byte code offset #57
/*      */     //   Java source line #6165	-> byte code offset #63
/*      */     //   Java source line #6172	-> byte code offset #118
/*      */     //   Java source line #6174	-> byte code offset #136
/*      */     //   Java source line #6176	-> byte code offset #141
/*      */     //   Java source line #6177	-> byte code offset #146
/*      */     //   Java source line #6179	-> byte code offset #164
/*      */     //   Java source line #6186	-> byte code offset #201
/*      */     //   Java source line #6188	-> byte code offset #217
/*      */     //   Java source line #6190	-> byte code offset #233
/*      */     //   Java source line #6191	-> byte code offset #240
/*      */     //   Java source line #6195	-> byte code offset #264
/*      */     //   Java source line #6205	-> byte code offset #312
/*      */     //   Java source line #6206	-> byte code offset #322
/*      */     //   Java source line #6212	-> byte code offset #334
/*      */     //   Java source line #6213	-> byte code offset #337
/*      */     //   Java source line #6214	-> byte code offset #340
/*      */     //   Java source line #6215	-> byte code offset #343
/*      */     //   Java source line #6216	-> byte code offset #346
/*      */     //   Java source line #6217	-> byte code offset #349
/*      */     //   Java source line #6218	-> byte code offset #352
/*      */     //   Java source line #6220	-> byte code offset #355
/*      */     //   Java source line #6229	-> byte code offset #472
/*      */     //   Java source line #6230	-> byte code offset #483
/*      */     //   Java source line #6232	-> byte code offset #495
/*      */     //   Java source line #6233	-> byte code offset #508
/*      */     //   Java source line #6235	-> byte code offset #521
/*      */     //   Java source line #6237	-> byte code offset #534
/*      */     //   Java source line #6240	-> byte code offset #547
/*      */     //   Java source line #6242	-> byte code offset #550
/*      */     //   Java source line #6243	-> byte code offset #557
/*      */     //   Java source line #6245	-> byte code offset #565
/*      */     //   Java source line #6246	-> byte code offset #571
/*      */     //   Java source line #6247	-> byte code offset #580
/*      */     //   Java source line #6250	-> byte code offset #593
/*      */     //   Java source line #6252	-> byte code offset #602
/*      */     //   Java source line #6253	-> byte code offset #609
/*      */     //   Java source line #6254	-> byte code offset #624
/*      */     //   Java source line #6256	-> byte code offset #631
/*      */     //   Java source line #6257	-> byte code offset #634
/*      */     //   Java source line #6265	-> byte code offset #642
/*      */     //   Java source line #6271	-> byte code offset #645
/*      */     //   Java source line #6272	-> byte code offset #656
/*      */     //   Java source line #6274	-> byte code offset #668
/*      */     //   Java source line #6275	-> byte code offset #681
/*      */     //   Java source line #6277	-> byte code offset #694
/*      */     //   Java source line #6279	-> byte code offset #707
/*      */     //   Java source line #6282	-> byte code offset #720
/*      */     //   Java source line #6286	-> byte code offset #723
/*      */     //   Java source line #6288	-> byte code offset #734
/*      */     //   Java source line #6289	-> byte code offset #741
/*      */     //   Java source line #6292	-> byte code offset #748
/*      */     //   Java source line #6294	-> byte code offset #754
/*      */     //   Java source line #6296	-> byte code offset #765
/*      */     //   Java source line #6297	-> byte code offset #777
/*      */     //   Java source line #6298	-> byte code offset #790
/*      */     //   Java source line #6300	-> byte code offset #803
/*      */     //   Java source line #6303	-> byte code offset #816
/*      */     //   Java source line #6307	-> byte code offset #819
/*      */     //   Java source line #6309	-> byte code offset #846
/*      */     //   Java source line #6310	-> byte code offset #857
/*      */     //   Java source line #6312	-> byte code offset #869
/*      */     //   Java source line #6313	-> byte code offset #882
/*      */     //   Java source line #6314	-> byte code offset #885
/*      */     //   Java source line #6316	-> byte code offset #891
/*      */     //   Java source line #6318	-> byte code offset #902
/*      */     //   Java source line #6319	-> byte code offset #909
/*      */     //   Java source line #6322	-> byte code offset #916
/*      */     //   Java source line #6324	-> byte code offset #927
/*      */     //   Java source line #6325	-> byte code offset #939
/*      */     //   Java source line #6326	-> byte code offset #952
/*      */     //   Java source line #6329	-> byte code offset #965
/*      */     //   Java source line #6332	-> byte code offset #971
/*      */     //   Java source line #6336	-> byte code offset #974
/*      */     //   Java source line #6337	-> byte code offset #985
/*      */     //   Java source line #6339	-> byte code offset #996
/*      */     //   Java source line #6341	-> byte code offset #1007
/*      */     //   Java source line #6343	-> byte code offset #1020
/*      */     //   Java source line #6344	-> byte code offset #1025
/*      */     //   Java source line #6345	-> byte code offset #1028
/*      */     //   Java source line #6346	-> byte code offset #1031
/*      */     //   Java source line #6349	-> byte code offset #1034
/*      */     //   Java source line #6350	-> byte code offset #1045
/*      */     //   Java source line #6352	-> byte code offset #1057
/*      */     //   Java source line #6354	-> byte code offset #1070
/*      */     //   Java source line #6355	-> byte code offset #1076
/*      */     //   Java source line #6357	-> byte code offset #1079
/*      */     //   Java source line #6361	-> byte code offset #1082
/*      */     //   Java source line #6363	-> byte code offset #1093
/*      */     //   Java source line #6364	-> byte code offset #1100
/*      */     //   Java source line #6367	-> byte code offset #1107
/*      */     //   Java source line #6369	-> byte code offset #1113
/*      */     //   Java source line #6371	-> byte code offset #1124
/*      */     //   Java source line #6373	-> byte code offset #1136
/*      */     //   Java source line #6377	-> byte code offset #1139
/*      */     //   Java source line #6379	-> byte code offset #1150
/*      */     //   Java source line #6380	-> byte code offset #1157
/*      */     //   Java source line #6383	-> byte code offset #1164
/*      */     //   Java source line #6385	-> byte code offset #1170
/*      */     //   Java source line #6388	-> byte code offset #1181
/*      */     //   Java source line #6390	-> byte code offset #1184
/*      */     //   Java source line #6394	-> byte code offset #1187
/*      */     //   Java source line #6396	-> byte code offset #1198
/*      */     //   Java source line #6397	-> byte code offset #1205
/*      */     //   Java source line #6400	-> byte code offset #1212
/*      */     //   Java source line #6401	-> byte code offset #1218
/*      */     //   Java source line #6402	-> byte code offset #1221
/*      */     //   Java source line #6404	-> byte code offset #1224
/*      */     //   Java source line #6408	-> byte code offset #1227
/*      */     //   Java source line #6414	-> byte code offset #1274
/*      */     //   Java source line #6415	-> byte code offset #1281
/*      */     //   Java source line #6419	-> byte code offset #1304
/*      */     //   Java source line #6426	-> byte code offset #1351
/*      */     //   Java source line #6427	-> byte code offset #1359
/*      */     //   Java source line #6428	-> byte code offset #1361
/*      */     //   Java source line #6431	-> byte code offset #1409
/*      */     //   Java source line #6433	-> byte code offset #1417
/*      */     // Local variable table:
/*      */     //   start	length	slot	name	signature
/*      */     //   0	1420	0	this	ResultSetImpl
/*      */     //   0	1420	1	columnIndex	int
/*      */     //   0	1420	2	targetCalendar	Calendar
/*      */     //   0	1420	3	timestampValue	String
/*      */     //   0	1420	4	tz	TimeZone
/*      */     //   0	1420	5	rollForward	boolean
/*      */     //   25	569	6	length	int
/*      */     //   1359	53	6	e	Exception
/*      */     //   55	1258	7	sessionCalendar	Calendar
/*      */     //   1407	11	7	sqlEx	SQLException
/*      */     //   335	980	9	year	int
/*      */     //   338	979	10	month	int
/*      */     //   341	978	11	day	int
/*      */     //   344	977	12	hour	int
/*      */     //   347	976	13	minutes	int
/*      */     //   350	975	14	seconds	int
/*      */     //   353	974	15	nanos	int
/*      */     //   563	33	16	decimalIndex	int
/*      */     //   600	15	17	numDigits	int
/*      */     //   622	5	18	factor	int
/*      */     //   1351	6	19	localObject1	Object
/*      */     // Exception table:
/*      */     //   from	to	target	type
/*      */     //   63	145	1351	finally
/*      */     //   146	216	1351	finally
/*      */     //   217	263	1351	finally
/*      */     //   264	311	1351	finally
/*      */     //   312	1303	1351	finally
/*      */     //   1304	1350	1351	finally
/*      */     //   1351	1356	1351	finally
/*      */     //   0	15	1359	java/lang/Exception
/*      */     //   16	145	1359	java/lang/Exception
/*      */     //   146	216	1359	java/lang/Exception
/*      */     //   217	263	1359	java/lang/Exception
/*      */     //   264	311	1359	java/lang/Exception
/*      */     //   312	1303	1359	java/lang/Exception
/*      */     //   1304	1350	1359	java/lang/Exception
/*      */     //   1351	1359	1359	java/lang/Exception
/*      */   }
/*      */   
/*      */   /* Error */
/*      */   private Timestamp getTimestampFromBytes(int columnIndex, Calendar targetCalendar, byte[] timestampAsBytes, TimeZone tz, boolean rollForward)
/*      */     throws SQLException
/*      */   {
/*      */     // Byte code:
/*      */     //   0: aload_0
/*      */     //   1: iload_1
/*      */     //   2: invokevirtual 661	com/mysql/jdbc/ResultSetImpl:checkColumnBounds	(I)V
/*      */     //   5: aload_0
/*      */     //   6: iconst_0
/*      */     //   7: putfield 213	com/mysql/jdbc/ResultSetImpl:wasNullFlag	Z
/*      */     //   10: aload_3
/*      */     //   11: ifnonnull +10 -> 21
/*      */     //   14: aload_0
/*      */     //   15: iconst_1
/*      */     //   16: putfield 213	com/mysql/jdbc/ResultSetImpl:wasNullFlag	Z
/*      */     //   19: aconst_null
/*      */     //   20: areturn
/*      */     //   21: aload_3
/*      */     //   22: arraylength
/*      */     //   23: istore 6
/*      */     //   25: aload_0
/*      */     //   26: getfield 233	com/mysql/jdbc/ResultSetImpl:connection	Lcom/mysql/jdbc/MySQLConnection;
/*      */     //   29: invokeinterface 1722 1 0
/*      */     //   34: ifeq +15 -> 49
/*      */     //   37: aload_0
/*      */     //   38: getfield 233	com/mysql/jdbc/ResultSetImpl:connection	Lcom/mysql/jdbc/MySQLConnection;
/*      */     //   41: invokeinterface 1725 1 0
/*      */     //   46: goto +7 -> 53
/*      */     //   49: aload_0
/*      */     //   50: invokevirtual 1696	com/mysql/jdbc/ResultSetImpl:getCalendarInstanceForSessionOrNew	()Ljava/util/Calendar;
/*      */     //   53: astore 7
/*      */     //   55: aload 7
/*      */     //   57: dup
/*      */     //   58: astore 8
/*      */     //   60: monitorenter
/*      */     //   61: iconst_1
/*      */     //   62: istore 9
/*      */     //   64: aload_3
/*      */     //   65: bipush 58
/*      */     //   67: invokestatic 1770	com/mysql/jdbc/StringUtils:indexOf	([BC)I
/*      */     //   70: iconst_m1
/*      */     //   71: if_icmpeq +7 -> 78
/*      */     //   74: iconst_1
/*      */     //   75: goto +4 -> 79
/*      */     //   78: iconst_0
/*      */     //   79: istore 10
/*      */     //   81: iconst_0
/*      */     //   82: istore 11
/*      */     //   84: iload 11
/*      */     //   86: iload 6
/*      */     //   88: if_icmpge +87 -> 175
/*      */     //   91: aload_3
/*      */     //   92: iload 11
/*      */     //   94: baload
/*      */     //   95: istore 12
/*      */     //   97: iload 12
/*      */     //   99: bipush 32
/*      */     //   101: if_icmpeq +17 -> 118
/*      */     //   104: iload 12
/*      */     //   106: bipush 45
/*      */     //   108: if_icmpeq +10 -> 118
/*      */     //   111: iload 12
/*      */     //   113: bipush 47
/*      */     //   115: if_icmpne +6 -> 121
/*      */     //   118: iconst_0
/*      */     //   119: istore 10
/*      */     //   121: iload 12
/*      */     //   123: bipush 48
/*      */     //   125: if_icmpeq +44 -> 169
/*      */     //   128: iload 12
/*      */     //   130: bipush 32
/*      */     //   132: if_icmpeq +37 -> 169
/*      */     //   135: iload 12
/*      */     //   137: bipush 58
/*      */     //   139: if_icmpeq +30 -> 169
/*      */     //   142: iload 12
/*      */     //   144: bipush 45
/*      */     //   146: if_icmpeq +23 -> 169
/*      */     //   149: iload 12
/*      */     //   151: bipush 47
/*      */     //   153: if_icmpeq +16 -> 169
/*      */     //   156: iload 12
/*      */     //   158: bipush 46
/*      */     //   160: if_icmpeq +9 -> 169
/*      */     //   163: iconst_0
/*      */     //   164: istore 9
/*      */     //   166: goto +9 -> 175
/*      */     //   169: iinc 11 1
/*      */     //   172: goto -88 -> 84
/*      */     //   175: iload 10
/*      */     //   177: ifne +130 -> 307
/*      */     //   180: iload 9
/*      */     //   182: ifeq +125 -> 307
/*      */     //   185: ldc_w 1011
/*      */     //   188: aload_0
/*      */     //   189: getfield 233	com/mysql/jdbc/ResultSetImpl:connection	Lcom/mysql/jdbc/MySQLConnection;
/*      */     //   192: invokeinterface 1014 1 0
/*      */     //   197: invokevirtual 839	java/lang/String:equals	(Ljava/lang/Object;)Z
/*      */     //   200: ifeq +13 -> 213
/*      */     //   203: aload_0
/*      */     //   204: iconst_1
/*      */     //   205: putfield 213	com/mysql/jdbc/ResultSetImpl:wasNullFlag	Z
/*      */     //   208: aconst_null
/*      */     //   209: aload 8
/*      */     //   211: monitorexit
/*      */     //   212: areturn
/*      */     //   213: ldc_w 1016
/*      */     //   216: aload_0
/*      */     //   217: getfield 233	com/mysql/jdbc/ResultSetImpl:connection	Lcom/mysql/jdbc/MySQLConnection;
/*      */     //   220: invokeinterface 1014 1 0
/*      */     //   225: invokevirtual 839	java/lang/String:equals	(Ljava/lang/Object;)Z
/*      */     //   228: ifeq +40 -> 268
/*      */     //   231: new 636	java/lang/StringBuilder
/*      */     //   234: dup
/*      */     //   235: invokespecial 637	java/lang/StringBuilder:<init>	()V
/*      */     //   238: ldc_w 1018
/*      */     //   241: invokevirtual 643	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
/*      */     //   244: aload_3
/*      */     //   245: invokevirtual 1676	java/lang/StringBuilder:append	(Ljava/lang/Object;)Ljava/lang/StringBuilder;
/*      */     //   248: ldc_w 1727
/*      */     //   251: invokevirtual 643	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
/*      */     //   254: invokevirtual 648	java/lang/StringBuilder:toString	()Ljava/lang/String;
/*      */     //   257: ldc_w 406
/*      */     //   260: aload_0
/*      */     //   261: invokevirtual 407	com/mysql/jdbc/ResultSetImpl:getExceptionInterceptor	()Lcom/mysql/jdbc/ExceptionInterceptor;
/*      */     //   264: invokestatic 413	com/mysql/jdbc/SQLError:createSQLException	(Ljava/lang/String;Ljava/lang/String;Lcom/mysql/jdbc/ExceptionInterceptor;)Ljava/sql/SQLException;
/*      */     //   267: athrow
/*      */     //   268: aload_0
/*      */     //   269: getfield 260	com/mysql/jdbc/ResultSetImpl:useLegacyDatetimeCode	Z
/*      */     //   272: ifne +19 -> 291
/*      */     //   275: aload 4
/*      */     //   277: iconst_1
/*      */     //   278: iconst_1
/*      */     //   279: iconst_1
/*      */     //   280: iconst_0
/*      */     //   281: iconst_0
/*      */     //   282: iconst_0
/*      */     //   283: iconst_0
/*      */     //   284: invokestatic 612	com/mysql/jdbc/TimeUtil:fastTimestampCreate	(Ljava/util/TimeZone;IIIIIII)Ljava/sql/Timestamp;
/*      */     //   287: aload 8
/*      */     //   289: monitorexit
/*      */     //   290: areturn
/*      */     //   291: aload_0
/*      */     //   292: aconst_null
/*      */     //   293: iconst_1
/*      */     //   294: iconst_1
/*      */     //   295: iconst_1
/*      */     //   296: iconst_0
/*      */     //   297: iconst_0
/*      */     //   298: iconst_0
/*      */     //   299: iconst_0
/*      */     //   300: invokevirtual 1729	com/mysql/jdbc/ResultSetImpl:fastTimestampCreate	(Ljava/util/Calendar;IIIIIII)Ljava/sql/Timestamp;
/*      */     //   303: aload 8
/*      */     //   305: monitorexit
/*      */     //   306: areturn
/*      */     //   307: aload_0
/*      */     //   308: getfield 231	com/mysql/jdbc/ResultSetImpl:fields	[Lcom/mysql/jdbc/Field;
/*      */     //   311: iload_1
/*      */     //   312: iconst_1
/*      */     //   313: isub
/*      */     //   314: aaload
/*      */     //   315: invokevirtual 724	com/mysql/jdbc/Field:getMysqlType	()I
/*      */     //   318: bipush 13
/*      */     //   320: if_icmpne +76 -> 396
/*      */     //   323: aload_0
/*      */     //   324: getfield 260	com/mysql/jdbc/ResultSetImpl:useLegacyDatetimeCode	Z
/*      */     //   327: ifne +24 -> 351
/*      */     //   330: aload 4
/*      */     //   332: aload_3
/*      */     //   333: iconst_0
/*      */     //   334: iconst_4
/*      */     //   335: invokestatic 1773	com/mysql/jdbc/StringUtils:getInt	([BII)I
/*      */     //   338: iconst_1
/*      */     //   339: iconst_1
/*      */     //   340: iconst_0
/*      */     //   341: iconst_0
/*      */     //   342: iconst_0
/*      */     //   343: iconst_0
/*      */     //   344: invokestatic 612	com/mysql/jdbc/TimeUtil:fastTimestampCreate	(Ljava/util/TimeZone;IIIIIII)Ljava/sql/Timestamp;
/*      */     //   347: aload 8
/*      */     //   349: monitorexit
/*      */     //   350: areturn
/*      */     //   351: aload_0
/*      */     //   352: getfield 233	com/mysql/jdbc/ResultSetImpl:connection	Lcom/mysql/jdbc/MySQLConnection;
/*      */     //   355: aload 7
/*      */     //   357: aload_2
/*      */     //   358: aload_0
/*      */     //   359: aload 7
/*      */     //   361: aload_3
/*      */     //   362: iconst_0
/*      */     //   363: iconst_4
/*      */     //   364: invokestatic 1773	com/mysql/jdbc/StringUtils:getInt	([BII)I
/*      */     //   367: iconst_1
/*      */     //   368: iconst_1
/*      */     //   369: iconst_0
/*      */     //   370: iconst_0
/*      */     //   371: iconst_0
/*      */     //   372: iconst_0
/*      */     //   373: invokevirtual 1729	com/mysql/jdbc/ResultSetImpl:fastTimestampCreate	(Ljava/util/Calendar;IIIIIII)Ljava/sql/Timestamp;
/*      */     //   376: aload_0
/*      */     //   377: getfield 233	com/mysql/jdbc/ResultSetImpl:connection	Lcom/mysql/jdbc/MySQLConnection;
/*      */     //   380: invokeinterface 250 1 0
/*      */     //   385: aload 4
/*      */     //   387: iload 5
/*      */     //   389: invokestatic 1732	com/mysql/jdbc/TimeUtil:changeTimezone	(Lcom/mysql/jdbc/MySQLConnection;Ljava/util/Calendar;Ljava/util/Calendar;Ljava/sql/Timestamp;Ljava/util/TimeZone;Ljava/util/TimeZone;Z)Ljava/sql/Timestamp;
/*      */     //   392: aload 8
/*      */     //   394: monitorexit
/*      */     //   395: areturn
/*      */     //   396: aload_3
/*      */     //   397: iload 6
/*      */     //   399: iconst_1
/*      */     //   400: isub
/*      */     //   401: baload
/*      */     //   402: bipush 46
/*      */     //   404: if_icmpne +6 -> 410
/*      */     //   407: iinc 6 -1
/*      */     //   410: iconst_0
/*      */     //   411: istore 11
/*      */     //   413: iconst_0
/*      */     //   414: istore 12
/*      */     //   416: iconst_0
/*      */     //   417: istore 13
/*      */     //   419: iconst_0
/*      */     //   420: istore 14
/*      */     //   422: iconst_0
/*      */     //   423: istore 15
/*      */     //   425: iconst_0
/*      */     //   426: istore 16
/*      */     //   428: iconst_0
/*      */     //   429: istore 17
/*      */     //   431: iload 6
/*      */     //   433: tableswitch	default:+715->1148, 2:+678->1111, 3:+715->1148, 4:+636->1069, 5:+715->1148, 6:+588->1021, 7:+715->1148, 8:+499->932, 9:+715->1148, 10:+369->802, 11:+715->1148, 12:+291->724, 13:+715->1148, 14:+231->664, 15:+715->1148, 16:+715->1148, 17:+715->1148, 18:+715->1148, 19:+115->548, 20:+115->548, 21:+115->548, 22:+115->548, 23:+115->548, 24:+115->548, 25:+115->548, 26:+115->548
/*      */     //   548: aload_3
/*      */     //   549: iconst_0
/*      */     //   550: iconst_4
/*      */     //   551: invokestatic 1773	com/mysql/jdbc/StringUtils:getInt	([BII)I
/*      */     //   554: istore 11
/*      */     //   556: aload_3
/*      */     //   557: iconst_5
/*      */     //   558: bipush 7
/*      */     //   560: invokestatic 1773	com/mysql/jdbc/StringUtils:getInt	([BII)I
/*      */     //   563: istore 12
/*      */     //   565: aload_3
/*      */     //   566: bipush 8
/*      */     //   568: bipush 10
/*      */     //   570: invokestatic 1773	com/mysql/jdbc/StringUtils:getInt	([BII)I
/*      */     //   573: istore 13
/*      */     //   575: aload_3
/*      */     //   576: bipush 11
/*      */     //   578: bipush 13
/*      */     //   580: invokestatic 1773	com/mysql/jdbc/StringUtils:getInt	([BII)I
/*      */     //   583: istore 14
/*      */     //   585: aload_3
/*      */     //   586: bipush 14
/*      */     //   588: bipush 16
/*      */     //   590: invokestatic 1773	com/mysql/jdbc/StringUtils:getInt	([BII)I
/*      */     //   593: istore 15
/*      */     //   595: aload_3
/*      */     //   596: bipush 17
/*      */     //   598: bipush 19
/*      */     //   600: invokestatic 1773	com/mysql/jdbc/StringUtils:getInt	([BII)I
/*      */     //   603: istore 16
/*      */     //   605: iconst_0
/*      */     //   606: istore 17
/*      */     //   608: iload 6
/*      */     //   610: bipush 19
/*      */     //   612: if_icmple +590 -> 1202
/*      */     //   615: aload_3
/*      */     //   616: bipush 46
/*      */     //   618: invokestatic 1775	com/mysql/jdbc/StringUtils:lastIndexOf	([BC)I
/*      */     //   621: istore 18
/*      */     //   623: iload 18
/*      */     //   625: iconst_m1
/*      */     //   626: if_icmpeq +35 -> 661
/*      */     //   629: iload 18
/*      */     //   631: iconst_2
/*      */     //   632: iadd
/*      */     //   633: iload 6
/*      */     //   635: if_icmpgt +18 -> 653
/*      */     //   638: aload_3
/*      */     //   639: iload 18
/*      */     //   641: iconst_1
/*      */     //   642: iadd
/*      */     //   643: iload 6
/*      */     //   645: invokestatic 1773	com/mysql/jdbc/StringUtils:getInt	([BII)I
/*      */     //   648: istore 17
/*      */     //   650: goto +11 -> 661
/*      */     //   653: new 1745	java/lang/IllegalArgumentException
/*      */     //   656: dup
/*      */     //   657: invokespecial 1746	java/lang/IllegalArgumentException:<init>	()V
/*      */     //   660: athrow
/*      */     //   661: goto +541 -> 1202
/*      */     //   664: aload_3
/*      */     //   665: iconst_0
/*      */     //   666: iconst_4
/*      */     //   667: invokestatic 1773	com/mysql/jdbc/StringUtils:getInt	([BII)I
/*      */     //   670: istore 11
/*      */     //   672: aload_3
/*      */     //   673: iconst_4
/*      */     //   674: bipush 6
/*      */     //   676: invokestatic 1773	com/mysql/jdbc/StringUtils:getInt	([BII)I
/*      */     //   679: istore 12
/*      */     //   681: aload_3
/*      */     //   682: bipush 6
/*      */     //   684: bipush 8
/*      */     //   686: invokestatic 1773	com/mysql/jdbc/StringUtils:getInt	([BII)I
/*      */     //   689: istore 13
/*      */     //   691: aload_3
/*      */     //   692: bipush 8
/*      */     //   694: bipush 10
/*      */     //   696: invokestatic 1773	com/mysql/jdbc/StringUtils:getInt	([BII)I
/*      */     //   699: istore 14
/*      */     //   701: aload_3
/*      */     //   702: bipush 10
/*      */     //   704: bipush 12
/*      */     //   706: invokestatic 1773	com/mysql/jdbc/StringUtils:getInt	([BII)I
/*      */     //   709: istore 15
/*      */     //   711: aload_3
/*      */     //   712: bipush 12
/*      */     //   714: bipush 14
/*      */     //   716: invokestatic 1773	com/mysql/jdbc/StringUtils:getInt	([BII)I
/*      */     //   719: istore 16
/*      */     //   721: goto +481 -> 1202
/*      */     //   724: aload_3
/*      */     //   725: iconst_0
/*      */     //   726: iconst_2
/*      */     //   727: invokestatic 1773	com/mysql/jdbc/StringUtils:getInt	([BII)I
/*      */     //   730: istore 11
/*      */     //   732: iload 11
/*      */     //   734: bipush 69
/*      */     //   736: if_icmpgt +10 -> 746
/*      */     //   739: iload 11
/*      */     //   741: bipush 100
/*      */     //   743: iadd
/*      */     //   744: istore 11
/*      */     //   746: wide
/*      */     //   752: aload_3
/*      */     //   753: iconst_2
/*      */     //   754: iconst_4
/*      */     //   755: invokestatic 1773	com/mysql/jdbc/StringUtils:getInt	([BII)I
/*      */     //   758: istore 12
/*      */     //   760: aload_3
/*      */     //   761: iconst_4
/*      */     //   762: bipush 6
/*      */     //   764: invokestatic 1773	com/mysql/jdbc/StringUtils:getInt	([BII)I
/*      */     //   767: istore 13
/*      */     //   769: aload_3
/*      */     //   770: bipush 6
/*      */     //   772: bipush 8
/*      */     //   774: invokestatic 1773	com/mysql/jdbc/StringUtils:getInt	([BII)I
/*      */     //   777: istore 14
/*      */     //   779: aload_3
/*      */     //   780: bipush 8
/*      */     //   782: bipush 10
/*      */     //   784: invokestatic 1773	com/mysql/jdbc/StringUtils:getInt	([BII)I
/*      */     //   787: istore 15
/*      */     //   789: aload_3
/*      */     //   790: bipush 10
/*      */     //   792: bipush 12
/*      */     //   794: invokestatic 1773	com/mysql/jdbc/StringUtils:getInt	([BII)I
/*      */     //   797: istore 16
/*      */     //   799: goto +403 -> 1202
/*      */     //   802: aload_0
/*      */     //   803: getfield 231	com/mysql/jdbc/ResultSetImpl:fields	[Lcom/mysql/jdbc/Field;
/*      */     //   806: iload_1
/*      */     //   807: iconst_1
/*      */     //   808: isub
/*      */     //   809: aaload
/*      */     //   810: invokevirtual 724	com/mysql/jdbc/Field:getMysqlType	()I
/*      */     //   813: bipush 10
/*      */     //   815: if_icmpeq +13 -> 828
/*      */     //   818: aload_3
/*      */     //   819: bipush 45
/*      */     //   821: invokestatic 1770	com/mysql/jdbc/StringUtils:indexOf	([BC)I
/*      */     //   824: iconst_m1
/*      */     //   825: if_icmpeq +39 -> 864
/*      */     //   828: aload_3
/*      */     //   829: iconst_0
/*      */     //   830: iconst_4
/*      */     //   831: invokestatic 1773	com/mysql/jdbc/StringUtils:getInt	([BII)I
/*      */     //   834: istore 11
/*      */     //   836: aload_3
/*      */     //   837: iconst_5
/*      */     //   838: bipush 7
/*      */     //   840: invokestatic 1773	com/mysql/jdbc/StringUtils:getInt	([BII)I
/*      */     //   843: istore 12
/*      */     //   845: aload_3
/*      */     //   846: bipush 8
/*      */     //   848: bipush 10
/*      */     //   850: invokestatic 1773	com/mysql/jdbc/StringUtils:getInt	([BII)I
/*      */     //   853: istore 13
/*      */     //   855: iconst_0
/*      */     //   856: istore 14
/*      */     //   858: iconst_0
/*      */     //   859: istore 15
/*      */     //   861: goto +341 -> 1202
/*      */     //   864: aload_3
/*      */     //   865: iconst_0
/*      */     //   866: iconst_2
/*      */     //   867: invokestatic 1773	com/mysql/jdbc/StringUtils:getInt	([BII)I
/*      */     //   870: istore 11
/*      */     //   872: iload 11
/*      */     //   874: bipush 69
/*      */     //   876: if_icmpgt +10 -> 886
/*      */     //   879: iload 11
/*      */     //   881: bipush 100
/*      */     //   883: iadd
/*      */     //   884: istore 11
/*      */     //   886: aload_3
/*      */     //   887: iconst_2
/*      */     //   888: iconst_4
/*      */     //   889: invokestatic 1773	com/mysql/jdbc/StringUtils:getInt	([BII)I
/*      */     //   892: istore 12
/*      */     //   894: aload_3
/*      */     //   895: iconst_4
/*      */     //   896: bipush 6
/*      */     //   898: invokestatic 1773	com/mysql/jdbc/StringUtils:getInt	([BII)I
/*      */     //   901: istore 13
/*      */     //   903: aload_3
/*      */     //   904: bipush 6
/*      */     //   906: bipush 8
/*      */     //   908: invokestatic 1773	com/mysql/jdbc/StringUtils:getInt	([BII)I
/*      */     //   911: istore 14
/*      */     //   913: aload_3
/*      */     //   914: bipush 8
/*      */     //   916: bipush 10
/*      */     //   918: invokestatic 1773	com/mysql/jdbc/StringUtils:getInt	([BII)I
/*      */     //   921: istore 15
/*      */     //   923: wide
/*      */     //   929: goto +273 -> 1202
/*      */     //   932: aload_3
/*      */     //   933: bipush 58
/*      */     //   935: invokestatic 1770	com/mysql/jdbc/StringUtils:indexOf	([BC)I
/*      */     //   938: iconst_m1
/*      */     //   939: if_icmpeq +43 -> 982
/*      */     //   942: aload_3
/*      */     //   943: iconst_0
/*      */     //   944: iconst_2
/*      */     //   945: invokestatic 1773	com/mysql/jdbc/StringUtils:getInt	([BII)I
/*      */     //   948: istore 14
/*      */     //   950: aload_3
/*      */     //   951: iconst_3
/*      */     //   952: iconst_5
/*      */     //   953: invokestatic 1773	com/mysql/jdbc/StringUtils:getInt	([BII)I
/*      */     //   956: istore 15
/*      */     //   958: aload_3
/*      */     //   959: bipush 6
/*      */     //   961: bipush 8
/*      */     //   963: invokestatic 1773	com/mysql/jdbc/StringUtils:getInt	([BII)I
/*      */     //   966: istore 16
/*      */     //   968: sipush 1970
/*      */     //   971: istore 11
/*      */     //   973: iconst_1
/*      */     //   974: istore 12
/*      */     //   976: iconst_1
/*      */     //   977: istore 13
/*      */     //   979: goto +223 -> 1202
/*      */     //   982: aload_3
/*      */     //   983: iconst_0
/*      */     //   984: iconst_4
/*      */     //   985: invokestatic 1773	com/mysql/jdbc/StringUtils:getInt	([BII)I
/*      */     //   988: istore 11
/*      */     //   990: aload_3
/*      */     //   991: iconst_4
/*      */     //   992: bipush 6
/*      */     //   994: invokestatic 1773	com/mysql/jdbc/StringUtils:getInt	([BII)I
/*      */     //   997: istore 12
/*      */     //   999: aload_3
/*      */     //   1000: bipush 6
/*      */     //   1002: bipush 8
/*      */     //   1004: invokestatic 1773	com/mysql/jdbc/StringUtils:getInt	([BII)I
/*      */     //   1007: istore 13
/*      */     //   1009: wide
/*      */     //   1015: iinc 12 -1
/*      */     //   1018: goto +184 -> 1202
/*      */     //   1021: aload_3
/*      */     //   1022: iconst_0
/*      */     //   1023: iconst_2
/*      */     //   1024: invokestatic 1773	com/mysql/jdbc/StringUtils:getInt	([BII)I
/*      */     //   1027: istore 11
/*      */     //   1029: iload 11
/*      */     //   1031: bipush 69
/*      */     //   1033: if_icmpgt +10 -> 1043
/*      */     //   1036: iload 11
/*      */     //   1038: bipush 100
/*      */     //   1040: iadd
/*      */     //   1041: istore 11
/*      */     //   1043: wide
/*      */     //   1049: aload_3
/*      */     //   1050: iconst_2
/*      */     //   1051: iconst_4
/*      */     //   1052: invokestatic 1773	com/mysql/jdbc/StringUtils:getInt	([BII)I
/*      */     //   1055: istore 12
/*      */     //   1057: aload_3
/*      */     //   1058: iconst_4
/*      */     //   1059: bipush 6
/*      */     //   1061: invokestatic 1773	com/mysql/jdbc/StringUtils:getInt	([BII)I
/*      */     //   1064: istore 13
/*      */     //   1066: goto +136 -> 1202
/*      */     //   1069: aload_3
/*      */     //   1070: iconst_0
/*      */     //   1071: iconst_2
/*      */     //   1072: invokestatic 1773	com/mysql/jdbc/StringUtils:getInt	([BII)I
/*      */     //   1075: istore 11
/*      */     //   1077: iload 11
/*      */     //   1079: bipush 69
/*      */     //   1081: if_icmpgt +10 -> 1091
/*      */     //   1084: iload 11
/*      */     //   1086: bipush 100
/*      */     //   1088: iadd
/*      */     //   1089: istore 11
/*      */     //   1091: wide
/*      */     //   1097: aload_3
/*      */     //   1098: iconst_2
/*      */     //   1099: iconst_4
/*      */     //   1100: invokestatic 1773	com/mysql/jdbc/StringUtils:getInt	([BII)I
/*      */     //   1103: istore 12
/*      */     //   1105: iconst_1
/*      */     //   1106: istore 13
/*      */     //   1108: goto +94 -> 1202
/*      */     //   1111: aload_3
/*      */     //   1112: iconst_0
/*      */     //   1113: iconst_2
/*      */     //   1114: invokestatic 1773	com/mysql/jdbc/StringUtils:getInt	([BII)I
/*      */     //   1117: istore 11
/*      */     //   1119: iload 11
/*      */     //   1121: bipush 69
/*      */     //   1123: if_icmpgt +10 -> 1133
/*      */     //   1126: iload 11
/*      */     //   1128: bipush 100
/*      */     //   1130: iadd
/*      */     //   1131: istore 11
/*      */     //   1133: wide
/*      */     //   1139: iconst_1
/*      */     //   1140: istore 12
/*      */     //   1142: iconst_1
/*      */     //   1143: istore 13
/*      */     //   1145: goto +57 -> 1202
/*      */     //   1148: new 107	java/sql/SQLException
/*      */     //   1151: dup
/*      */     //   1152: new 636	java/lang/StringBuilder
/*      */     //   1155: dup
/*      */     //   1156: invokespecial 637	java/lang/StringBuilder:<init>	()V
/*      */     //   1159: ldc_w 1752
/*      */     //   1162: invokevirtual 643	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
/*      */     //   1165: new 354	java/lang/String
/*      */     //   1168: dup
/*      */     //   1169: aload_3
/*      */     //   1170: invokespecial 1607	java/lang/String:<init>	([B)V
/*      */     //   1173: invokevirtual 643	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
/*      */     //   1176: ldc_w 1754
/*      */     //   1179: invokevirtual 643	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
/*      */     //   1182: iload_1
/*      */     //   1183: invokevirtual 1146	java/lang/StringBuilder:append	(I)Ljava/lang/StringBuilder;
/*      */     //   1186: ldc_w 859
/*      */     //   1189: invokevirtual 643	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
/*      */     //   1192: invokevirtual 648	java/lang/StringBuilder:toString	()Ljava/lang/String;
/*      */     //   1195: ldc_w 406
/*      */     //   1198: invokespecial 749	java/sql/SQLException:<init>	(Ljava/lang/String;Ljava/lang/String;)V
/*      */     //   1201: athrow
/*      */     //   1202: aload_0
/*      */     //   1203: getfield 260	com/mysql/jdbc/ResultSetImpl:useLegacyDatetimeCode	Z
/*      */     //   1206: ifne +26 -> 1232
/*      */     //   1209: aload 4
/*      */     //   1211: iload 11
/*      */     //   1213: iload 12
/*      */     //   1215: iload 13
/*      */     //   1217: iload 14
/*      */     //   1219: iload 15
/*      */     //   1221: iload 16
/*      */     //   1223: iload 17
/*      */     //   1225: invokestatic 612	com/mysql/jdbc/TimeUtil:fastTimestampCreate	(Ljava/util/TimeZone;IIIIIII)Ljava/sql/Timestamp;
/*      */     //   1228: aload 8
/*      */     //   1230: monitorexit
/*      */     //   1231: areturn
/*      */     //   1232: aload_0
/*      */     //   1233: getfield 233	com/mysql/jdbc/ResultSetImpl:connection	Lcom/mysql/jdbc/MySQLConnection;
/*      */     //   1236: aload 7
/*      */     //   1238: aload_2
/*      */     //   1239: aload_0
/*      */     //   1240: aload 7
/*      */     //   1242: iload 11
/*      */     //   1244: iload 12
/*      */     //   1246: iload 13
/*      */     //   1248: iload 14
/*      */     //   1250: iload 15
/*      */     //   1252: iload 16
/*      */     //   1254: iload 17
/*      */     //   1256: invokevirtual 1729	com/mysql/jdbc/ResultSetImpl:fastTimestampCreate	(Ljava/util/Calendar;IIIIIII)Ljava/sql/Timestamp;
/*      */     //   1259: aload_0
/*      */     //   1260: getfield 233	com/mysql/jdbc/ResultSetImpl:connection	Lcom/mysql/jdbc/MySQLConnection;
/*      */     //   1263: invokeinterface 250 1 0
/*      */     //   1268: aload 4
/*      */     //   1270: iload 5
/*      */     //   1272: invokestatic 1732	com/mysql/jdbc/TimeUtil:changeTimezone	(Lcom/mysql/jdbc/MySQLConnection;Ljava/util/Calendar;Ljava/util/Calendar;Ljava/sql/Timestamp;Ljava/util/TimeZone;Ljava/util/TimeZone;Z)Ljava/sql/Timestamp;
/*      */     //   1275: aload 8
/*      */     //   1277: monitorexit
/*      */     //   1278: areturn
/*      */     //   1279: astore 19
/*      */     //   1281: aload 8
/*      */     //   1283: monitorexit
/*      */     //   1284: aload 19
/*      */     //   1286: athrow
/*      */     //   1287: astore 6
/*      */     //   1289: new 636	java/lang/StringBuilder
/*      */     //   1292: dup
/*      */     //   1293: invokespecial 637	java/lang/StringBuilder:<init>	()V
/*      */     //   1296: ldc_w 1756
/*      */     //   1299: invokevirtual 643	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
/*      */     //   1302: new 354	java/lang/String
/*      */     //   1305: dup
/*      */     //   1306: aload_3
/*      */     //   1307: invokespecial 1607	java/lang/String:<init>	([B)V
/*      */     //   1310: invokevirtual 643	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
/*      */     //   1313: ldc_w 1758
/*      */     //   1316: invokevirtual 643	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
/*      */     //   1319: iload_1
/*      */     //   1320: invokevirtual 1146	java/lang/StringBuilder:append	(I)Ljava/lang/StringBuilder;
/*      */     //   1323: ldc_w 1760
/*      */     //   1326: invokevirtual 643	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
/*      */     //   1329: invokevirtual 648	java/lang/StringBuilder:toString	()Ljava/lang/String;
/*      */     //   1332: ldc_w 406
/*      */     //   1335: aload_0
/*      */     //   1336: invokevirtual 407	com/mysql/jdbc/ResultSetImpl:getExceptionInterceptor	()Lcom/mysql/jdbc/ExceptionInterceptor;
/*      */     //   1339: invokestatic 413	com/mysql/jdbc/SQLError:createSQLException	(Ljava/lang/String;Ljava/lang/String;Lcom/mysql/jdbc/ExceptionInterceptor;)Ljava/sql/SQLException;
/*      */     //   1342: astore 7
/*      */     //   1344: aload 7
/*      */     //   1346: aload 6
/*      */     //   1348: invokevirtual 1042	java/sql/SQLException:initCause	(Ljava/lang/Throwable;)Ljava/lang/Throwable;
/*      */     //   1351: pop
/*      */     //   1352: aload 7
/*      */     //   1354: athrow
/*      */     // Line number table:
/*      */     //   Java source line #6442	-> byte code offset #0
/*      */     //   Java source line #6445	-> byte code offset #5
/*      */     //   Java source line #6447	-> byte code offset #10
/*      */     //   Java source line #6448	-> byte code offset #14
/*      */     //   Java source line #6450	-> byte code offset #19
/*      */     //   Java source line #6453	-> byte code offset #21
/*      */     //   Java source line #6455	-> byte code offset #25
/*      */     //   Java source line #6459	-> byte code offset #55
/*      */     //   Java source line #6460	-> byte code offset #61
/*      */     //   Java source line #6462	-> byte code offset #64
/*      */     //   Java source line #6464	-> byte code offset #81
/*      */     //   Java source line #6465	-> byte code offset #91
/*      */     //   Java source line #6467	-> byte code offset #97
/*      */     //   Java source line #6468	-> byte code offset #118
/*      */     //   Java source line #6471	-> byte code offset #121
/*      */     //   Java source line #6473	-> byte code offset #163
/*      */     //   Java source line #6475	-> byte code offset #166
/*      */     //   Java source line #6464	-> byte code offset #169
/*      */     //   Java source line #6479	-> byte code offset #175
/*      */     //   Java source line #6481	-> byte code offset #185
/*      */     //   Java source line #6483	-> byte code offset #203
/*      */     //   Java source line #6485	-> byte code offset #208
/*      */     //   Java source line #6486	-> byte code offset #213
/*      */     //   Java source line #6488	-> byte code offset #231
/*      */     //   Java source line #6495	-> byte code offset #268
/*      */     //   Java source line #6496	-> byte code offset #275
/*      */     //   Java source line #6500	-> byte code offset #291
/*      */     //   Java source line #6501	-> byte code offset #307
/*      */     //   Java source line #6503	-> byte code offset #323
/*      */     //   Java source line #6504	-> byte code offset #330
/*      */     //   Java source line #6508	-> byte code offset #351
/*      */     //   Java source line #6516	-> byte code offset #396
/*      */     //   Java source line #6517	-> byte code offset #407
/*      */     //   Java source line #6522	-> byte code offset #410
/*      */     //   Java source line #6523	-> byte code offset #413
/*      */     //   Java source line #6524	-> byte code offset #416
/*      */     //   Java source line #6525	-> byte code offset #419
/*      */     //   Java source line #6526	-> byte code offset #422
/*      */     //   Java source line #6527	-> byte code offset #425
/*      */     //   Java source line #6528	-> byte code offset #428
/*      */     //   Java source line #6530	-> byte code offset #431
/*      */     //   Java source line #6539	-> byte code offset #548
/*      */     //   Java source line #6540	-> byte code offset #556
/*      */     //   Java source line #6541	-> byte code offset #565
/*      */     //   Java source line #6542	-> byte code offset #575
/*      */     //   Java source line #6543	-> byte code offset #585
/*      */     //   Java source line #6544	-> byte code offset #595
/*      */     //   Java source line #6546	-> byte code offset #605
/*      */     //   Java source line #6548	-> byte code offset #608
/*      */     //   Java source line #6549	-> byte code offset #615
/*      */     //   Java source line #6551	-> byte code offset #623
/*      */     //   Java source line #6552	-> byte code offset #629
/*      */     //   Java source line #6553	-> byte code offset #638
/*      */     //   Java source line #6555	-> byte code offset #653
/*      */     //   Java source line #6563	-> byte code offset #661
/*      */     //   Java source line #6569	-> byte code offset #664
/*      */     //   Java source line #6570	-> byte code offset #672
/*      */     //   Java source line #6571	-> byte code offset #681
/*      */     //   Java source line #6572	-> byte code offset #691
/*      */     //   Java source line #6573	-> byte code offset #701
/*      */     //   Java source line #6574	-> byte code offset #711
/*      */     //   Java source line #6576	-> byte code offset #721
/*      */     //   Java source line #6580	-> byte code offset #724
/*      */     //   Java source line #6582	-> byte code offset #732
/*      */     //   Java source line #6583	-> byte code offset #739
/*      */     //   Java source line #6586	-> byte code offset #746
/*      */     //   Java source line #6588	-> byte code offset #752
/*      */     //   Java source line #6589	-> byte code offset #760
/*      */     //   Java source line #6590	-> byte code offset #769
/*      */     //   Java source line #6591	-> byte code offset #779
/*      */     //   Java source line #6592	-> byte code offset #789
/*      */     //   Java source line #6594	-> byte code offset #799
/*      */     //   Java source line #6598	-> byte code offset #802
/*      */     //   Java source line #6600	-> byte code offset #828
/*      */     //   Java source line #6601	-> byte code offset #836
/*      */     //   Java source line #6602	-> byte code offset #845
/*      */     //   Java source line #6603	-> byte code offset #855
/*      */     //   Java source line #6604	-> byte code offset #858
/*      */     //   Java source line #6606	-> byte code offset #864
/*      */     //   Java source line #6608	-> byte code offset #872
/*      */     //   Java source line #6609	-> byte code offset #879
/*      */     //   Java source line #6612	-> byte code offset #886
/*      */     //   Java source line #6613	-> byte code offset #894
/*      */     //   Java source line #6614	-> byte code offset #903
/*      */     //   Java source line #6615	-> byte code offset #913
/*      */     //   Java source line #6617	-> byte code offset #923
/*      */     //   Java source line #6620	-> byte code offset #929
/*      */     //   Java source line #6624	-> byte code offset #932
/*      */     //   Java source line #6625	-> byte code offset #942
/*      */     //   Java source line #6626	-> byte code offset #950
/*      */     //   Java source line #6627	-> byte code offset #958
/*      */     //   Java source line #6629	-> byte code offset #968
/*      */     //   Java source line #6630	-> byte code offset #973
/*      */     //   Java source line #6631	-> byte code offset #976
/*      */     //   Java source line #6633	-> byte code offset #979
/*      */     //   Java source line #6636	-> byte code offset #982
/*      */     //   Java source line #6637	-> byte code offset #990
/*      */     //   Java source line #6638	-> byte code offset #999
/*      */     //   Java source line #6640	-> byte code offset #1009
/*      */     //   Java source line #6641	-> byte code offset #1015
/*      */     //   Java source line #6643	-> byte code offset #1018
/*      */     //   Java source line #6647	-> byte code offset #1021
/*      */     //   Java source line #6649	-> byte code offset #1029
/*      */     //   Java source line #6650	-> byte code offset #1036
/*      */     //   Java source line #6653	-> byte code offset #1043
/*      */     //   Java source line #6655	-> byte code offset #1049
/*      */     //   Java source line #6656	-> byte code offset #1057
/*      */     //   Java source line #6658	-> byte code offset #1066
/*      */     //   Java source line #6662	-> byte code offset #1069
/*      */     //   Java source line #6664	-> byte code offset #1077
/*      */     //   Java source line #6665	-> byte code offset #1084
/*      */     //   Java source line #6668	-> byte code offset #1091
/*      */     //   Java source line #6670	-> byte code offset #1097
/*      */     //   Java source line #6671	-> byte code offset #1105
/*      */     //   Java source line #6673	-> byte code offset #1108
/*      */     //   Java source line #6677	-> byte code offset #1111
/*      */     //   Java source line #6679	-> byte code offset #1119
/*      */     //   Java source line #6680	-> byte code offset #1126
/*      */     //   Java source line #6683	-> byte code offset #1133
/*      */     //   Java source line #6684	-> byte code offset #1139
/*      */     //   Java source line #6685	-> byte code offset #1142
/*      */     //   Java source line #6687	-> byte code offset #1145
/*      */     //   Java source line #6691	-> byte code offset #1148
/*      */     //   Java source line #6697	-> byte code offset #1202
/*      */     //   Java source line #6698	-> byte code offset #1209
/*      */     //   Java source line #6702	-> byte code offset #1232
/*      */     //   Java source line #6709	-> byte code offset #1279
/*      */     //   Java source line #6710	-> byte code offset #1287
/*      */     //   Java source line #6711	-> byte code offset #1289
/*      */     //   Java source line #6714	-> byte code offset #1344
/*      */     //   Java source line #6716	-> byte code offset #1352
/*      */     // Local variable table:
/*      */     //   start	length	slot	name	signature
/*      */     //   0	1355	0	this	ResultSetImpl
/*      */     //   0	1355	1	columnIndex	int
/*      */     //   0	1355	2	targetCalendar	Calendar
/*      */     //   0	1355	3	timestampAsBytes	byte[]
/*      */     //   0	1355	4	tz	TimeZone
/*      */     //   0	1355	5	rollForward	boolean
/*      */     //   23	621	6	length	int
/*      */     //   1287	60	6	e	Exception
/*      */     //   53	1188	7	sessionCalendar	Calendar
/*      */     //   1342	11	7	sqlEx	SQLException
/*      */     //   62	119	9	allZeroTimestamp	boolean
/*      */     //   79	97	10	onlyTimePresent	boolean
/*      */     //   82	88	11	i	int
/*      */     //   411	832	11	year	int
/*      */     //   95	62	12	b	byte
/*      */     //   414	831	12	month	int
/*      */     //   417	830	13	day	int
/*      */     //   420	829	14	hour	int
/*      */     //   423	828	15	minutes	int
/*      */     //   426	827	16	seconds	int
/*      */     //   429	826	17	nanos	int
/*      */     //   621	19	18	decimalIndex	int
/*      */     //   1279	6	19	localObject1	Object
/*      */     // Exception table:
/*      */     //   from	to	target	type
/*      */     //   61	212	1279	finally
/*      */     //   213	290	1279	finally
/*      */     //   291	306	1279	finally
/*      */     //   307	350	1279	finally
/*      */     //   351	395	1279	finally
/*      */     //   396	1231	1279	finally
/*      */     //   1232	1278	1279	finally
/*      */     //   1279	1284	1279	finally
/*      */     //   5	20	1287	java/lang/Exception
/*      */     //   21	212	1287	java/lang/Exception
/*      */     //   213	290	1287	java/lang/Exception
/*      */     //   291	306	1287	java/lang/Exception
/*      */     //   307	350	1287	java/lang/Exception
/*      */     //   351	395	1287	java/lang/Exception
/*      */     //   396	1231	1287	java/lang/Exception
/*      */     //   1232	1278	1287	java/lang/Exception
/*      */     //   1279	1287	1287	java/lang/Exception
/*      */   }
/*      */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\com\mysql\jdbc\ResultSetImpl.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */
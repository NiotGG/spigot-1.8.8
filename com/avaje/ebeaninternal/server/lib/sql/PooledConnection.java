/*     */ package com.avaje.ebeaninternal.server.lib.sql;
/*     */ 
/*     */ import com.avaje.ebeaninternal.jdbc.ConnectionDelegator;
/*     */ import java.sql.CallableStatement;
/*     */ import java.sql.Connection;
/*     */ import java.sql.DatabaseMetaData;
/*     */ import java.sql.PreparedStatement;
/*     */ import java.sql.SQLException;
/*     */ import java.sql.SQLWarning;
/*     */ import java.sql.Savepoint;
/*     */ import java.sql.Statement;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Collection;
/*     */ import java.util.Iterator;
/*     */ import java.util.Map;
/*     */ import java.util.logging.Level;
/*     */ import java.util.logging.Logger;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class PooledConnection
/*     */   extends ConnectionDelegator
/*     */ {
/*  57 */   private static final Logger logger = Logger.getLogger(PooledConnection.class.getName());
/*     */   
/*  59 */   private static String IDLE_CONNECTION_ACCESSED_ERROR = "Pooled Connection has been accessed whilst idle in the pool, via method: ";
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   static final int STATUS_IDLE = 88;
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   static final int STATUS_ACTIVE = 89;
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   static final int STATUS_ENDED = 87;
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   final String name;
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   final DataSourcePool pool;
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   final Connection connection;
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   final long creationTime;
/*     */   
/*     */ 
/*     */ 
/*     */   final PstmtCache pstmtCache;
/*     */   
/*     */ 
/*     */ 
/* 102 */   final Object pstmtMonitor = new Object();
/*     */   
/*     */ 
/*     */ 
/*     */ 
/* 107 */   int status = 88;
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   boolean longRunning;
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   boolean hadErrors;
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   long startUseTime;
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   long lastUseTime;
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   String lastStatement;
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   int pstmtHitCounter;
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   int pstmtMissCounter;
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   String createdByMethod;
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   StackTraceElement[] stackTrace;
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   int maxStackTrace;
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   int slotId;
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public PooledConnection(DataSourcePool pool, int uniqueId, Connection connection)
/*     */     throws SQLException
/*     */   {
/* 174 */     super(connection);
/*     */     
/* 176 */     this.pool = pool;
/* 177 */     this.connection = connection;
/* 178 */     this.name = (pool.getName() + "." + uniqueId);
/* 179 */     this.pstmtCache = new PstmtCache(this.name, pool.getPstmtCacheSize());
/* 180 */     this.maxStackTrace = pool.getMaxStackTraceSize();
/* 181 */     this.creationTime = System.currentTimeMillis();
/* 182 */     this.lastUseTime = this.creationTime;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   protected PooledConnection(String name)
/*     */   {
/* 189 */     super(null);
/* 190 */     this.name = name;
/* 191 */     this.pool = null;
/* 192 */     this.connection = null;
/* 193 */     this.pstmtCache = null;
/* 194 */     this.maxStackTrace = 0;
/* 195 */     this.creationTime = System.currentTimeMillis();
/* 196 */     this.lastUseTime = this.creationTime;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public int getSlotId()
/*     */   {
/* 203 */     return this.slotId;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public void setSlotId(int slotId)
/*     */   {
/* 210 */     this.slotId = slotId;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public DataSourcePool getDataSourcePool()
/*     */   {
/* 217 */     return this.pool;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public long getCreationTime()
/*     */   {
/* 224 */     return this.creationTime;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public String getName()
/*     */   {
/* 231 */     return this.name;
/*     */   }
/*     */   
/*     */   public String toString() {
/* 235 */     return this.name;
/*     */   }
/*     */   
/*     */   public String getDescription() {
/* 239 */     return "name[" + this.name + "] startTime[" + getStartUseTime() + "] stmt[" + getLastStatement() + "] createdBy[" + getCreatedByMethod() + "]";
/*     */   }
/*     */   
/*     */   public String getStatistics() {
/* 243 */     return "name[" + this.name + "] startTime[" + getStartUseTime() + "] pstmtHits[" + this.pstmtHitCounter + "] pstmtMiss[" + this.pstmtMissCounter + "] " + this.pstmtCache.getDescription();
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public boolean isLongRunning()
/*     */   {
/* 250 */     return this.longRunning;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public void setLongRunning(boolean longRunning)
/*     */   {
/* 258 */     this.longRunning = longRunning;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public void closeConnectionFully(boolean logErrors)
/*     */   {
/* 275 */     String msg = "Closing Connection[" + getName() + "]" + " psReuse[" + this.pstmtHitCounter + "] psCreate[" + this.pstmtMissCounter + "] psSize[" + this.pstmtCache.size() + "]";
/*     */     
/*     */ 
/* 278 */     logger.info(msg);
/*     */     try
/*     */     {
/* 281 */       if (this.connection.isClosed()) {
/* 282 */         msg = "Closing Connection[" + getName() + "] that is already closed?";
/* 283 */         logger.log(Level.SEVERE, msg);
/* 284 */         return;
/*     */       }
/*     */     } catch (SQLException ex) {
/* 287 */       if (logErrors) {
/* 288 */         msg = "Error when fully closing connection [" + getName() + "]";
/* 289 */         logger.log(Level.SEVERE, msg, ex);
/*     */       }
/*     */     }
/*     */     try
/*     */     {
/* 294 */       Iterator<ExtendedPreparedStatement> psi = this.pstmtCache.values().iterator();
/* 295 */       while (psi.hasNext()) {
/* 296 */         ExtendedPreparedStatement ps = (ExtendedPreparedStatement)psi.next();
/* 297 */         ps.closeDestroy();
/*     */       }
/*     */     }
/*     */     catch (SQLException ex) {
/* 301 */       if (logErrors) {
/* 302 */         logger.log(Level.WARNING, "Error when closing connection Statements", ex);
/*     */       }
/*     */     }
/*     */     try
/*     */     {
/* 307 */       this.connection.close();
/*     */     }
/*     */     catch (SQLException ex) {
/* 310 */       if (logErrors) {
/* 311 */         msg = "Error when fully closing connection [" + getName() + "]";
/* 312 */         logger.log(Level.SEVERE, msg, ex);
/*     */       }
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public PstmtCache getPstmtCache()
/*     */   {
/* 321 */     return this.pstmtCache;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public Statement createStatement()
/*     */     throws SQLException
/*     */   {
/* 330 */     if (this.status == 88) {
/* 331 */       throw new SQLException(IDLE_CONNECTION_ACCESSED_ERROR + "createStatement()");
/*     */     }
/*     */     try {
/* 334 */       return this.connection.createStatement();
/*     */     } catch (SQLException ex) {
/* 336 */       addError(ex);
/* 337 */       throw ex;
/*     */     }
/*     */   }
/*     */   
/*     */   public Statement createStatement(int resultSetType, int resultSetConcurreny) throws SQLException
/*     */   {
/* 343 */     if (this.status == 88) {
/* 344 */       throw new SQLException(IDLE_CONNECTION_ACCESSED_ERROR + "createStatement()");
/*     */     }
/*     */     try {
/* 347 */       return this.connection.createStatement(resultSetType, resultSetConcurreny);
/*     */     }
/*     */     catch (SQLException ex) {
/* 350 */       addError(ex);
/* 351 */       throw ex;
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   protected void returnPreparedStatement(ExtendedPreparedStatement pstmt)
/*     */   {
/* 360 */     synchronized (this.pstmtMonitor) {
/* 361 */       ExtendedPreparedStatement alreadyInCache = this.pstmtCache.get(pstmt.getCacheKey());
/*     */       
/* 363 */       if (alreadyInCache == null)
/*     */       {
/*     */ 
/*     */ 
/* 367 */         this.pstmtCache.put(pstmt.getCacheKey(), pstmt);
/*     */ 
/*     */       }
/*     */       else
/*     */       {
/*     */         try
/*     */         {
/*     */ 
/* 375 */           pstmt.closeDestroy();
/*     */         }
/*     */         catch (SQLException e) {
/* 378 */           logger.log(Level.SEVERE, "Error closing Pstmt", e);
/*     */         }
/*     */       }
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */   public PreparedStatement prepareStatement(String sql, int returnKeysFlag)
/*     */     throws SQLException
/*     */   {
/* 388 */     String cacheKey = sql + returnKeysFlag;
/* 389 */     return prepareStatement(sql, true, returnKeysFlag, cacheKey);
/*     */   }
/*     */   
/*     */ 
/*     */   public PreparedStatement prepareStatement(String sql)
/*     */     throws SQLException
/*     */   {
/* 396 */     return prepareStatement(sql, false, 0, sql);
/*     */   }
/*     */   
/*     */   /* Error */
/*     */   private PreparedStatement prepareStatement(String sql, boolean useFlag, int flag, String cacheKey)
/*     */     throws SQLException
/*     */   {
/*     */     // Byte code:
/*     */     //   0: aload_0
/*     */     //   1: getfield 58	com/avaje/ebeaninternal/server/lib/sql/PooledConnection:status	I
/*     */     //   4: bipush 88
/*     */     //   6: if_icmpne +37 -> 43
/*     */     //   9: new 66	java/lang/StringBuilder
/*     */     //   12: dup
/*     */     //   13: invokespecial 67	java/lang/StringBuilder:<init>	()V
/*     */     //   16: getstatic 256	com/avaje/ebeaninternal/server/lib/sql/PooledConnection:IDLE_CONNECTION_ACCESSED_ERROR	Ljava/lang/String;
/*     */     //   19: invokevirtual 77	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
/*     */     //   22: ldc_w 300
/*     */     //   25: invokevirtual 77	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
/*     */     //   28: invokevirtual 85	java/lang/StringBuilder:toString	()Ljava/lang/String;
/*     */     //   31: astore 5
/*     */     //   33: new 46	java/sql/SQLException
/*     */     //   36: dup
/*     */     //   37: aload 5
/*     */     //   39: invokespecial 260	java/sql/SQLException:<init>	(Ljava/lang/String;)V
/*     */     //   42: athrow
/*     */     //   43: aload_0
/*     */     //   44: getfield 56	com/avaje/ebeaninternal/server/lib/sql/PooledConnection:pstmtMonitor	Ljava/lang/Object;
/*     */     //   47: dup
/*     */     //   48: astore 5
/*     */     //   50: monitorenter
/*     */     //   51: aload_0
/*     */     //   52: aload_1
/*     */     //   53: putfield 302	com/avaje/ebeaninternal/server/lib/sql/PooledConnection:lastStatement	Ljava/lang/String;
/*     */     //   56: aload_0
/*     */     //   57: getfield 98	com/avaje/ebeaninternal/server/lib/sql/PooledConnection:pstmtCache	Lcom/avaje/ebeaninternal/server/lib/sql/PstmtCache;
/*     */     //   60: aload 4
/*     */     //   62: invokevirtual 305	com/avaje/ebeaninternal/server/lib/sql/PstmtCache:remove	(Ljava/lang/Object;)Lcom/avaje/ebeaninternal/server/lib/sql/ExtendedPreparedStatement;
/*     */     //   65: astore 6
/*     */     //   67: aload 6
/*     */     //   69: ifnull +19 -> 88
/*     */     //   72: aload_0
/*     */     //   73: dup
/*     */     //   74: getfield 153	com/avaje/ebeaninternal/server/lib/sql/PooledConnection:pstmtHitCounter	I
/*     */     //   77: iconst_1
/*     */     //   78: iadd
/*     */     //   79: putfield 153	com/avaje/ebeaninternal/server/lib/sql/PooledConnection:pstmtHitCounter	I
/*     */     //   82: aload 6
/*     */     //   84: aload 5
/*     */     //   86: monitorexit
/*     */     //   87: areturn
/*     */     //   88: aload_0
/*     */     //   89: dup
/*     */     //   90: getfield 157	com/avaje/ebeaninternal/server/lib/sql/PooledConnection:pstmtMissCounter	I
/*     */     //   93: iconst_1
/*     */     //   94: iadd
/*     */     //   95: putfield 157	com/avaje/ebeaninternal/server/lib/sql/PooledConnection:pstmtMissCounter	I
/*     */     //   98: iload_2
/*     */     //   99: ifeq +19 -> 118
/*     */     //   102: aload_0
/*     */     //   103: getfield 64	com/avaje/ebeaninternal/server/lib/sql/PooledConnection:connection	Ljava/sql/Connection;
/*     */     //   106: aload_1
/*     */     //   107: iload_3
/*     */     //   108: invokeinterface 307 3 0
/*     */     //   113: astore 7
/*     */     //   115: goto +15 -> 130
/*     */     //   118: aload_0
/*     */     //   119: getfield 64	com/avaje/ebeaninternal/server/lib/sql/PooledConnection:connection	Ljava/sql/Connection;
/*     */     //   122: aload_1
/*     */     //   123: invokeinterface 309 2 0
/*     */     //   128: astore 7
/*     */     //   130: new 230	com/avaje/ebeaninternal/server/lib/sql/ExtendedPreparedStatement
/*     */     //   133: dup
/*     */     //   134: aload_0
/*     */     //   135: aload 7
/*     */     //   137: aload_1
/*     */     //   138: aload 4
/*     */     //   140: invokespecial 312	com/avaje/ebeaninternal/server/lib/sql/ExtendedPreparedStatement:<init>	(Lcom/avaje/ebeaninternal/server/lib/sql/PooledConnection;Ljava/sql/PreparedStatement;Ljava/lang/String;Ljava/lang/String;)V
/*     */     //   143: aload 5
/*     */     //   145: monitorexit
/*     */     //   146: areturn
/*     */     //   147: astore 8
/*     */     //   149: aload 5
/*     */     //   151: monitorexit
/*     */     //   152: aload 8
/*     */     //   154: athrow
/*     */     //   155: astore 5
/*     */     //   157: aload_0
/*     */     //   158: aload 5
/*     */     //   160: invokevirtual 266	com/avaje/ebeaninternal/server/lib/sql/PooledConnection:addError	(Ljava/lang/Throwable;)V
/*     */     //   163: aload 5
/*     */     //   165: athrow
/*     */     // Line number table:
/*     */     //   Java source line #404	-> byte code offset #0
/*     */     //   Java source line #405	-> byte code offset #9
/*     */     //   Java source line #406	-> byte code offset #33
/*     */     //   Java source line #409	-> byte code offset #43
/*     */     //   Java source line #410	-> byte code offset #51
/*     */     //   Java source line #413	-> byte code offset #56
/*     */     //   Java source line #415	-> byte code offset #67
/*     */     //   Java source line #416	-> byte code offset #72
/*     */     //   Java source line #417	-> byte code offset #82
/*     */     //   Java source line #421	-> byte code offset #88
/*     */     //   Java source line #423	-> byte code offset #98
/*     */     //   Java source line #424	-> byte code offset #102
/*     */     //   Java source line #426	-> byte code offset #118
/*     */     //   Java source line #428	-> byte code offset #130
/*     */     //   Java source line #429	-> byte code offset #147
/*     */     //   Java source line #431	-> byte code offset #155
/*     */     //   Java source line #432	-> byte code offset #157
/*     */     //   Java source line #433	-> byte code offset #163
/*     */     // Local variable table:
/*     */     //   start	length	slot	name	signature
/*     */     //   0	166	0	this	PooledConnection
/*     */     //   0	166	1	sql	String
/*     */     //   0	166	2	useFlag	boolean
/*     */     //   0	166	3	flag	int
/*     */     //   0	166	4	cacheKey	String
/*     */     //   31	7	5	m	String
/*     */     //   155	9	5	ex	SQLException
/*     */     //   65	18	6	pstmt	ExtendedPreparedStatement
/*     */     //   113	3	7	actualPstmt	PreparedStatement
/*     */     //   128	8	7	actualPstmt	PreparedStatement
/*     */     //   147	6	8	localObject1	Object
/*     */     // Exception table:
/*     */     //   from	to	target	type
/*     */     //   51	87	147	finally
/*     */     //   88	146	147	finally
/*     */     //   147	152	147	finally
/*     */     //   43	87	155	java/sql/SQLException
/*     */     //   88	146	155	java/sql/SQLException
/*     */     //   147	155	155	java/sql/SQLException
/*     */   }
/*     */   
/*     */   public PreparedStatement prepareStatement(String sql, int resultSetType, int resultSetConcurreny)
/*     */     throws SQLException
/*     */   {
/* 439 */     if (this.status == 88) {
/* 440 */       throw new SQLException(IDLE_CONNECTION_ACCESSED_ERROR + "prepareStatement()");
/*     */     }
/*     */     try
/*     */     {
/* 444 */       this.pstmtMissCounter += 1;
/* 445 */       this.lastStatement = sql;
/* 446 */       return this.connection.prepareStatement(sql, resultSetType, resultSetConcurreny);
/*     */     } catch (SQLException ex) {
/* 448 */       addError(ex);
/* 449 */       throw ex;
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   protected void resetForUse()
/*     */   {
/* 458 */     this.status = 89;
/* 459 */     this.startUseTime = System.currentTimeMillis();
/* 460 */     this.createdByMethod = null;
/* 461 */     this.lastStatement = null;
/* 462 */     this.hadErrors = false;
/* 463 */     this.longRunning = false;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public void addError(Throwable e)
/*     */   {
/* 474 */     this.hadErrors = true;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public boolean hadErrors()
/*     */   {
/* 485 */     return this.hadErrors;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public void close()
/*     */     throws SQLException
/*     */   {
/* 500 */     if (this.status == 88) {
/* 501 */       throw new SQLException(IDLE_CONNECTION_ACCESSED_ERROR + "close()");
/*     */     }
/*     */     
/* 504 */     if ((this.hadErrors) && 
/* 505 */       (!this.pool.validateConnection(this)))
/*     */     {
/* 507 */       closeConnectionFully(false);
/* 508 */       this.pool.checkDataSource();
/* 509 */       return;
/*     */     }
/*     */     
/*     */ 
/*     */     try
/*     */     {
/* 515 */       if (this.connection.getAutoCommit() != this.pool.getAutoCommit()) {
/* 516 */         this.connection.setAutoCommit(this.pool.getAutoCommit());
/*     */       }
/*     */       
/*     */ 
/*     */ 
/* 521 */       if (this.resetIsolationReadOnlyRequired) {
/* 522 */         resetIsolationReadOnly();
/* 523 */         this.resetIsolationReadOnlyRequired = false;
/*     */       }
/*     */       
/*     */ 
/* 527 */       this.lastUseTime = System.currentTimeMillis();
/*     */       
/* 529 */       this.status = 88;
/* 530 */       this.pool.returnConnection(this);
/*     */     }
/*     */     catch (Exception ex)
/*     */     {
/* 534 */       closeConnectionFully(false);
/* 535 */       this.pool.checkDataSource();
/*     */     }
/*     */   }
/*     */   
/*     */   private void resetIsolationReadOnly() throws SQLException
/*     */   {
/* 541 */     if (this.connection.getTransactionIsolation() != this.pool.getTransactionIsolation()) {
/* 542 */       this.connection.setTransactionIsolation(this.pool.getTransactionIsolation());
/*     */     }
/*     */     
/* 545 */     if (this.connection.isReadOnly()) {
/* 546 */       this.connection.setReadOnly(false);
/*     */     }
/*     */   }
/*     */   
/*     */   protected void finalize() throws Throwable {
/*     */     try {
/* 552 */       if ((this.connection != null) && (!this.connection.isClosed()))
/*     */       {
/* 554 */         String msg = "Closing Connection[" + getName() + "] on finalize().";
/* 555 */         logger.warning(msg);
/* 556 */         closeConnectionFully(false);
/*     */       }
/*     */     } catch (Exception e) {
/* 559 */       logger.log(Level.SEVERE, null, e);
/*     */     }
/* 561 */     super.finalize();
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public long getStartUseTime()
/*     */   {
/* 571 */     return this.startUseTime;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public long getLastUsedTime()
/*     */   {
/* 582 */     return this.lastUseTime;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public String getLastStatement()
/*     */   {
/* 589 */     return this.lastStatement;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   protected void setLastStatement(String lastStatement)
/*     */   {
/* 599 */     this.lastStatement = lastStatement;
/* 600 */     if (logger.isLoggable(Level.FINER)) {
/* 601 */       logger.finer(".setLastStatement[" + lastStatement + "]");
/*     */     }
/*     */   }
/*     */   
/* 605 */   boolean resetIsolationReadOnlyRequired = false;
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public void setReadOnly(boolean readOnly)
/*     */     throws SQLException
/*     */   {
/* 617 */     this.resetIsolationReadOnlyRequired = true;
/* 618 */     this.connection.setReadOnly(readOnly);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public void setTransactionIsolation(int level)
/*     */     throws SQLException
/*     */   {
/* 626 */     if (this.status == 88) {
/* 627 */       throw new SQLException(IDLE_CONNECTION_ACCESSED_ERROR + "setTransactionIsolation()");
/*     */     }
/*     */     try {
/* 630 */       this.resetIsolationReadOnlyRequired = true;
/* 631 */       this.connection.setTransactionIsolation(level);
/*     */     } catch (SQLException ex) {
/* 633 */       addError(ex);
/* 634 */       throw ex;
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public void clearWarnings()
/*     */     throws SQLException
/*     */   {
/* 646 */     if (this.status == 88) {
/* 647 */       throw new SQLException(IDLE_CONNECTION_ACCESSED_ERROR + "clearWarnings()");
/*     */     }
/* 649 */     this.connection.clearWarnings();
/*     */   }
/*     */   
/*     */   public void commit() throws SQLException {
/* 653 */     if (this.status == 88) {
/* 654 */       throw new SQLException(IDLE_CONNECTION_ACCESSED_ERROR + "commit()");
/*     */     }
/*     */     try {
/* 657 */       this.status = 87;
/* 658 */       this.connection.commit();
/*     */     } catch (SQLException ex) {
/* 660 */       addError(ex);
/* 661 */       throw ex;
/*     */     }
/*     */   }
/*     */   
/*     */   public boolean getAutoCommit() throws SQLException {
/* 666 */     if (this.status == 88) {
/* 667 */       throw new SQLException(IDLE_CONNECTION_ACCESSED_ERROR + "getAutoCommit()");
/*     */     }
/* 669 */     return this.connection.getAutoCommit();
/*     */   }
/*     */   
/*     */   public String getCatalog() throws SQLException {
/* 673 */     if (this.status == 88) {
/* 674 */       throw new SQLException(IDLE_CONNECTION_ACCESSED_ERROR + "getCatalog()");
/*     */     }
/* 676 */     return this.connection.getCatalog();
/*     */   }
/*     */   
/*     */   public DatabaseMetaData getMetaData() throws SQLException {
/* 680 */     if (this.status == 88) {
/* 681 */       throw new SQLException(IDLE_CONNECTION_ACCESSED_ERROR + "getMetaData()");
/*     */     }
/* 683 */     return this.connection.getMetaData();
/*     */   }
/*     */   
/*     */   public int getTransactionIsolation() throws SQLException {
/* 687 */     if (this.status == 88) {
/* 688 */       throw new SQLException(IDLE_CONNECTION_ACCESSED_ERROR + "getTransactionIsolation()");
/*     */     }
/* 690 */     return this.connection.getTransactionIsolation();
/*     */   }
/*     */   
/*     */   public Map<String, Class<?>> getTypeMap() throws SQLException {
/* 694 */     if (this.status == 88) {
/* 695 */       throw new SQLException(IDLE_CONNECTION_ACCESSED_ERROR + "getTypeMap()");
/*     */     }
/* 697 */     return this.connection.getTypeMap();
/*     */   }
/*     */   
/*     */   public SQLWarning getWarnings() throws SQLException {
/* 701 */     if (this.status == 88) {
/* 702 */       throw new SQLException(IDLE_CONNECTION_ACCESSED_ERROR + "getWarnings()");
/*     */     }
/* 704 */     return this.connection.getWarnings();
/*     */   }
/*     */   
/*     */   public boolean isClosed() throws SQLException {
/* 708 */     if (this.status == 88) {
/* 709 */       throw new SQLException(IDLE_CONNECTION_ACCESSED_ERROR + "isClosed()");
/*     */     }
/* 711 */     return this.connection.isClosed();
/*     */   }
/*     */   
/*     */   public boolean isReadOnly() throws SQLException {
/* 715 */     if (this.status == 88) {
/* 716 */       throw new SQLException(IDLE_CONNECTION_ACCESSED_ERROR + "isReadOnly()");
/*     */     }
/* 718 */     return this.connection.isReadOnly();
/*     */   }
/*     */   
/*     */   public String nativeSQL(String sql) throws SQLException {
/* 722 */     if (this.status == 88) {
/* 723 */       throw new SQLException(IDLE_CONNECTION_ACCESSED_ERROR + "nativeSQL()");
/*     */     }
/* 725 */     this.lastStatement = sql;
/* 726 */     return this.connection.nativeSQL(sql);
/*     */   }
/*     */   
/*     */   public CallableStatement prepareCall(String sql) throws SQLException {
/* 730 */     if (this.status == 88) {
/* 731 */       throw new SQLException(IDLE_CONNECTION_ACCESSED_ERROR + "prepareCall()");
/*     */     }
/* 733 */     this.lastStatement = sql;
/* 734 */     return this.connection.prepareCall(sql);
/*     */   }
/*     */   
/*     */   public CallableStatement prepareCall(String sql, int resultSetType, int resultSetConcurreny) throws SQLException
/*     */   {
/* 739 */     if (this.status == 88) {
/* 740 */       throw new SQLException(IDLE_CONNECTION_ACCESSED_ERROR + "prepareCall()");
/*     */     }
/* 742 */     this.lastStatement = sql;
/* 743 */     return this.connection.prepareCall(sql, resultSetType, resultSetConcurreny);
/*     */   }
/*     */   
/*     */   public void rollback() throws SQLException {
/* 747 */     if (this.status == 88) {
/* 748 */       throw new SQLException(IDLE_CONNECTION_ACCESSED_ERROR + "rollback()");
/*     */     }
/*     */     try {
/* 751 */       this.status = 87;
/* 752 */       this.connection.rollback();
/*     */     } catch (SQLException ex) {
/* 754 */       addError(ex);
/* 755 */       throw ex;
/*     */     }
/*     */   }
/*     */   
/*     */   public void setAutoCommit(boolean autoCommit) throws SQLException {
/* 760 */     if (this.status == 88) {
/* 761 */       throw new SQLException(IDLE_CONNECTION_ACCESSED_ERROR + "setAutoCommit()");
/*     */     }
/*     */     try {
/* 764 */       this.connection.setAutoCommit(autoCommit);
/*     */     } catch (SQLException ex) {
/* 766 */       addError(ex);
/* 767 */       throw ex;
/*     */     }
/*     */   }
/*     */   
/*     */   public void setCatalog(String catalog) throws SQLException {
/* 772 */     if (this.status == 88) {
/* 773 */       throw new SQLException(IDLE_CONNECTION_ACCESSED_ERROR + "setCatalog()");
/*     */     }
/* 775 */     this.connection.setCatalog(catalog);
/*     */   }
/*     */   
/*     */   public void setTypeMap(Map<String, Class<?>> map) throws SQLException {
/* 779 */     if (this.status == 88) {
/* 780 */       throw new SQLException(IDLE_CONNECTION_ACCESSED_ERROR + "setTypeMap()");
/*     */     }
/* 782 */     this.connection.setTypeMap(map);
/*     */   }
/*     */   
/*     */   public Savepoint setSavepoint() throws SQLException {
/*     */     try {
/* 787 */       return this.connection.setSavepoint();
/*     */     } catch (SQLException ex) {
/* 789 */       addError(ex);
/* 790 */       throw ex;
/*     */     }
/*     */   }
/*     */   
/*     */   public Savepoint setSavepoint(String savepointName) throws SQLException {
/*     */     try {
/* 796 */       return this.connection.setSavepoint(savepointName);
/*     */     } catch (SQLException ex) {
/* 798 */       addError(ex);
/* 799 */       throw ex;
/*     */     }
/*     */   }
/*     */   
/*     */   public void rollback(Savepoint sp) throws SQLException {
/*     */     try {
/* 805 */       this.connection.rollback(sp);
/*     */     } catch (SQLException ex) {
/* 807 */       addError(ex);
/* 808 */       throw ex;
/*     */     }
/*     */   }
/*     */   
/*     */   public void releaseSavepoint(Savepoint sp) throws SQLException {
/*     */     try {
/* 814 */       this.connection.releaseSavepoint(sp);
/*     */     } catch (SQLException ex) {
/* 816 */       addError(ex);
/* 817 */       throw ex;
/*     */     }
/*     */   }
/*     */   
/*     */   public void setHoldability(int i) throws SQLException {
/*     */     try {
/* 823 */       this.connection.setHoldability(i);
/*     */     } catch (SQLException ex) {
/* 825 */       addError(ex);
/* 826 */       throw ex;
/*     */     }
/*     */   }
/*     */   
/*     */   public int getHoldability() throws SQLException {
/*     */     try {
/* 832 */       return this.connection.getHoldability();
/*     */     } catch (SQLException ex) {
/* 834 */       addError(ex);
/* 835 */       throw ex;
/*     */     }
/*     */   }
/*     */   
/*     */   public Statement createStatement(int i, int x, int y) throws SQLException {
/*     */     try {
/* 841 */       return this.connection.createStatement(i, x, y);
/*     */     } catch (SQLException ex) {
/* 843 */       addError(ex);
/* 844 */       throw ex;
/*     */     }
/*     */   }
/*     */   
/*     */   public PreparedStatement prepareStatement(String s, int i, int x, int y) throws SQLException {
/*     */     try {
/* 850 */       return this.connection.prepareStatement(s, i, x, y);
/*     */     } catch (SQLException ex) {
/* 852 */       addError(ex);
/* 853 */       throw ex;
/*     */     }
/*     */   }
/*     */   
/*     */   public PreparedStatement prepareStatement(String s, int[] i) throws SQLException {
/*     */     try {
/* 859 */       return this.connection.prepareStatement(s, i);
/*     */     } catch (SQLException ex) {
/* 861 */       addError(ex);
/* 862 */       throw ex;
/*     */     }
/*     */   }
/*     */   
/*     */   public PreparedStatement prepareStatement(String s, String[] s2) throws SQLException {
/*     */     try {
/* 868 */       return this.connection.prepareStatement(s, s2);
/*     */     } catch (SQLException ex) {
/* 870 */       addError(ex);
/* 871 */       throw ex;
/*     */     }
/*     */   }
/*     */   
/*     */   public CallableStatement prepareCall(String s, int i, int x, int y) throws SQLException {
/*     */     try {
/* 877 */       return this.connection.prepareCall(s, i, x, y);
/*     */     } catch (SQLException ex) {
/* 879 */       addError(ex);
/* 880 */       throw ex;
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public String getCreatedByMethod()
/*     */   {
/* 891 */     if (this.createdByMethod != null) {
/* 892 */       return this.createdByMethod;
/*     */     }
/* 894 */     if (this.stackTrace == null) {
/* 895 */       return null;
/*     */     }
/*     */     
/* 898 */     for (int j = 0; j < this.stackTrace.length; j++) {
/* 899 */       String methodLine = this.stackTrace[j].toString();
/* 900 */       if (!skipElement(methodLine))
/*     */       {
/*     */ 
/* 903 */         this.createdByMethod = methodLine;
/* 904 */         return this.createdByMethod;
/*     */       }
/*     */     }
/*     */     
/* 908 */     return null;
/*     */   }
/*     */   
/*     */   private boolean skipElement(String methodLine) {
/* 912 */     if (methodLine.startsWith("java.lang."))
/* 913 */       return true;
/* 914 */     if (methodLine.startsWith("java.util."))
/* 915 */       return true;
/* 916 */     if (methodLine.startsWith("com.avaje.ebeaninternal.server.query.CallableQuery.<init>"))
/*     */     {
/* 918 */       return true; }
/* 919 */     if (methodLine.startsWith("com.avaje.ebeaninternal.server.query.Callable"))
/*     */     {
/* 921 */       return false; }
/* 922 */     if (methodLine.startsWith("com.avaje.ebeaninternal")) {
/* 923 */       return true;
/*     */     }
/* 925 */     return false;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   protected void setStackTrace(StackTraceElement[] stackTrace)
/*     */   {
/* 933 */     this.stackTrace = stackTrace;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public StackTraceElement[] getStackTrace()
/*     */   {
/* 942 */     if (this.stackTrace == null) {
/* 943 */       return null;
/*     */     }
/*     */     
/*     */ 
/* 947 */     ArrayList<StackTraceElement> filteredList = new ArrayList();
/* 948 */     boolean include = false;
/* 949 */     for (int i = 0; i < this.stackTrace.length; i++) {
/* 950 */       if ((!include) && (!skipElement(this.stackTrace[i].toString()))) {
/* 951 */         include = true;
/*     */       }
/* 953 */       if ((include) && (filteredList.size() < this.maxStackTrace)) {
/* 954 */         filteredList.add(this.stackTrace[i]);
/*     */       }
/*     */     }
/* 957 */     return (StackTraceElement[])filteredList.toArray(new StackTraceElement[filteredList.size()]);
/*     */   }
/*     */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\com\avaje\ebeaninternal\server\lib\sql\PooledConnection.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */
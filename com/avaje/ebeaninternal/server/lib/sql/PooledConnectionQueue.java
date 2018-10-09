/*     */ package com.avaje.ebeaninternal.server.lib.sql;
/*     */ 
/*     */ import java.io.PrintStream;
/*     */ import java.sql.SQLException;
/*     */ import java.util.Arrays;
/*     */ import java.util.Date;
/*     */ import java.util.Iterator;
/*     */ import java.util.List;
/*     */ import java.util.concurrent.TimeUnit;
/*     */ import java.util.concurrent.locks.Condition;
/*     */ import java.util.concurrent.locks.ReentrantLock;
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
/*     */ public class PooledConnectionQueue
/*     */ {
/*  37 */   private static final Logger logger = Logger.getLogger(PooledConnectionQueue.class.getName());
/*     */   
/*  39 */   private static final TimeUnit MILLIS_TIME_UNIT = TimeUnit.MILLISECONDS;
/*     */   
/*     */ 
/*     */ 
/*     */   private final String name;
/*     */   
/*     */ 
/*     */ 
/*     */   private final DataSourcePool pool;
/*     */   
/*     */ 
/*     */ 
/*     */   private final FreeConnectionBuffer freeList;
/*     */   
/*     */ 
/*     */ 
/*     */   private final BusyConnectionBuffer busyList;
/*     */   
/*     */ 
/*     */ 
/*     */   private final ReentrantLock lock;
/*     */   
/*     */ 
/*     */ 
/*     */   private final Condition notEmpty;
/*     */   
/*     */ 
/*     */ 
/*     */   private int connectionId;
/*     */   
/*     */ 
/*     */ 
/*     */   private long waitTimeoutMillis;
/*     */   
/*     */ 
/*     */ 
/*     */   private long leakTimeMinutes;
/*     */   
/*     */ 
/*     */   private int warningSize;
/*     */   
/*     */ 
/*     */   private int maxSize;
/*     */   
/*     */ 
/*     */   private int minSize;
/*     */   
/*     */ 
/*     */   private int waitingThreads;
/*     */   
/*     */ 
/*     */   private int waitCount;
/*     */   
/*     */ 
/*     */   private int hitCount;
/*     */   
/*     */ 
/*     */   private int highWaterMark;
/*     */   
/*     */ 
/*     */   private long lastResetTime;
/*     */   
/*     */ 
/*     */   private boolean doingShutdown;
/*     */   
/*     */ 
/*     */ 
/*     */   public PooledConnectionQueue(DataSourcePool pool)
/*     */   {
/* 108 */     this.pool = pool;
/* 109 */     this.name = pool.getName();
/* 110 */     this.minSize = pool.getMinSize();
/* 111 */     this.maxSize = pool.getMaxSize();
/*     */     
/* 113 */     this.warningSize = pool.getWarningSize();
/* 114 */     this.waitTimeoutMillis = pool.getWaitTimeoutMillis();
/* 115 */     this.leakTimeMinutes = pool.getLeakTimeMinutes();
/*     */     
/* 117 */     this.busyList = new BusyConnectionBuffer(50, 20);
/* 118 */     this.freeList = new FreeConnectionBuffer(this.maxSize);
/*     */     
/* 120 */     this.lock = new ReentrantLock(true);
/* 121 */     this.notEmpty = this.lock.newCondition();
/*     */   }
/*     */   
/*     */   private DataSourcePool.Status createStatus() {
/* 125 */     return new DataSourcePool.Status(this.name, this.minSize, this.maxSize, this.freeList.size(), this.busyList.size(), this.waitingThreads, this.highWaterMark, this.waitCount, this.hitCount);
/*     */   }
/*     */   
/*     */   public String toString() {
/* 129 */     ReentrantLock lock = this.lock;
/* 130 */     lock.lock();
/*     */     try {
/* 132 */       return createStatus().toString();
/*     */     } finally {
/* 134 */       lock.unlock();
/*     */     }
/*     */   }
/*     */   
/*     */   public DataSourcePool.Status getStatus(boolean reset) {
/* 139 */     ReentrantLock lock = this.lock;
/* 140 */     lock.lock();
/*     */     try {
/* 142 */       DataSourcePool.Status s = createStatus();
/* 143 */       if (reset) {
/* 144 */         this.highWaterMark = this.busyList.size();
/* 145 */         this.hitCount = 0;
/* 146 */         this.waitCount = 0;
/*     */       }
/* 148 */       return s;
/*     */     } finally {
/* 150 */       lock.unlock();
/*     */     }
/*     */   }
/*     */   
/*     */   public void setMinSize(int minSize) {
/* 155 */     ReentrantLock lock = this.lock;
/* 156 */     lock.lock();
/*     */     try {
/* 158 */       if (minSize > this.maxSize) {
/* 159 */         throw new IllegalArgumentException("minSize " + minSize + " > maxSize " + this.maxSize);
/*     */       }
/* 161 */       this.minSize = minSize;
/*     */     } finally {
/* 163 */       lock.unlock();
/*     */     }
/*     */   }
/*     */   
/*     */   public void setMaxSize(int maxSize) {
/* 168 */     ReentrantLock lock = this.lock;
/* 169 */     lock.lock();
/*     */     try {
/* 171 */       if (maxSize < this.minSize) {
/* 172 */         throw new IllegalArgumentException("maxSize " + maxSize + " < minSize " + this.minSize);
/*     */       }
/* 174 */       this.freeList.setCapacity(maxSize);
/* 175 */       this.maxSize = maxSize;
/*     */     } finally {
/* 177 */       lock.unlock();
/*     */     }
/*     */   }
/*     */   
/*     */   public void setWarningSize(int warningSize) {
/* 182 */     ReentrantLock lock = this.lock;
/* 183 */     lock.lock();
/*     */     try {
/* 185 */       if (warningSize > this.maxSize) {
/* 186 */         throw new IllegalArgumentException("warningSize " + warningSize + " > maxSize " + this.maxSize);
/*     */       }
/* 188 */       this.warningSize = warningSize;
/*     */     } finally {
/* 190 */       lock.unlock();
/*     */     }
/*     */   }
/*     */   
/*     */   private int totalConnections() {
/* 195 */     return this.freeList.size() + this.busyList.size();
/*     */   }
/*     */   
/*     */   public void ensureMinimumConnections() throws SQLException {
/* 199 */     ReentrantLock lock = this.lock;
/* 200 */     lock.lock();
/*     */     try {
/* 202 */       int add = this.minSize - totalConnections();
/* 203 */       if (add > 0) {
/* 204 */         for (int i = 0; i < add; i++) {
/* 205 */           PooledConnection c = this.pool.createConnectionForQueue(this.connectionId++);
/* 206 */           this.freeList.add(c);
/*     */         }
/* 208 */         this.notEmpty.signal();
/*     */       }
/*     */     }
/*     */     finally {
/* 212 */       lock.unlock();
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   protected void returnPooledConnection(PooledConnection c)
/*     */   {
/* 221 */     ReentrantLock lock = this.lock;
/* 222 */     lock.lock();
/*     */     try {
/* 224 */       if (!this.busyList.remove(c)) {
/* 225 */         logger.log(Level.SEVERE, "Connection [" + c + "] not found in BusyList? ");
/*     */       }
/* 227 */       if (c.getCreationTime() <= this.lastResetTime) {
/* 228 */         c.closeConnectionFully(false);
/*     */       } else {
/* 230 */         this.freeList.add(c);
/* 231 */         this.notEmpty.signal();
/*     */       }
/*     */     } finally {
/* 234 */       lock.unlock();
/*     */     }
/*     */   }
/*     */   
/*     */   private PooledConnection extractFromFreeList() {
/* 239 */     PooledConnection c = this.freeList.remove();
/* 240 */     registerBusyConnection(c);
/* 241 */     return c;
/*     */   }
/*     */   
/*     */   public PooledConnection getPooledConnection() throws SQLException
/*     */   {
/*     */     try {
/* 247 */       PooledConnection pc = _getPooledConnection();
/* 248 */       pc.resetForUse();
/* 249 */       return pc;
/*     */     }
/*     */     catch (InterruptedException e) {
/* 252 */       String msg = "Interrupted getting connection from pool " + e;
/* 253 */       throw new SQLException(msg);
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   private int registerBusyConnection(PooledConnection c)
/*     */   {
/* 261 */     int busySize = this.busyList.add(c);
/* 262 */     if (busySize > this.highWaterMark) {
/* 263 */       this.highWaterMark = busySize;
/*     */     }
/* 265 */     return busySize;
/*     */   }
/*     */   
/*     */   /* Error */
/*     */   private PooledConnection _getPooledConnection()
/*     */     throws InterruptedException, SQLException
/*     */   {
/*     */     // Byte code:
/*     */     //   0: aload_0
/*     */     //   1: getfield 102	com/avaje/ebeaninternal/server/lib/sql/PooledConnectionQueue:lock	Ljava/util/concurrent/locks/ReentrantLock;
/*     */     //   4: astore_1
/*     */     //   5: aload_1
/*     */     //   6: invokevirtual 264	java/util/concurrent/locks/ReentrantLock:lockInterruptibly	()V
/*     */     //   9: aload_0
/*     */     //   10: getfield 266	com/avaje/ebeaninternal/server/lib/sql/PooledConnectionQueue:doingShutdown	Z
/*     */     //   13: ifeq +14 -> 27
/*     */     //   16: new 178	java/sql/SQLException
/*     */     //   19: dup
/*     */     //   20: ldc_w 268
/*     */     //   23: invokespecial 254	java/sql/SQLException:<init>	(Ljava/lang/String;)V
/*     */     //   26: athrow
/*     */     //   27: aload_0
/*     */     //   28: dup
/*     */     //   29: getfield 124	com/avaje/ebeaninternal/server/lib/sql/PooledConnectionQueue:hitCount	I
/*     */     //   32: iconst_1
/*     */     //   33: iadd
/*     */     //   34: putfield 124	com/avaje/ebeaninternal/server/lib/sql/PooledConnectionQueue:hitCount	I
/*     */     //   37: aload_0
/*     */     //   38: getfield 118	com/avaje/ebeaninternal/server/lib/sql/PooledConnectionQueue:waitingThreads	I
/*     */     //   41: ifne +156 -> 197
/*     */     //   44: aload_0
/*     */     //   45: getfield 95	com/avaje/ebeaninternal/server/lib/sql/PooledConnectionQueue:freeList	Lcom/avaje/ebeaninternal/server/lib/sql/FreeConnectionBuffer;
/*     */     //   48: invokevirtual 115	com/avaje/ebeaninternal/server/lib/sql/FreeConnectionBuffer:size	()I
/*     */     //   51: istore_2
/*     */     //   52: iload_2
/*     */     //   53: ifle +14 -> 67
/*     */     //   56: aload_0
/*     */     //   57: invokespecial 270	com/avaje/ebeaninternal/server/lib/sql/PooledConnectionQueue:extractFromFreeList	()Lcom/avaje/ebeaninternal/server/lib/sql/PooledConnection;
/*     */     //   60: astore_3
/*     */     //   61: aload_1
/*     */     //   62: invokevirtual 137	java/util/concurrent/locks/ReentrantLock:unlock	()V
/*     */     //   65: aload_3
/*     */     //   66: areturn
/*     */     //   67: aload_0
/*     */     //   68: getfield 88	com/avaje/ebeaninternal/server/lib/sql/PooledConnectionQueue:busyList	Lcom/avaje/ebeaninternal/server/lib/sql/BusyConnectionBuffer;
/*     */     //   71: invokevirtual 116	com/avaje/ebeaninternal/server/lib/sql/BusyConnectionBuffer:size	()I
/*     */     //   74: aload_0
/*     */     //   75: getfield 65	com/avaje/ebeaninternal/server/lib/sql/PooledConnectionQueue:maxSize	I
/*     */     //   78: if_icmpge +119 -> 197
/*     */     //   81: aload_0
/*     */     //   82: getfield 48	com/avaje/ebeaninternal/server/lib/sql/PooledConnectionQueue:pool	Lcom/avaje/ebeaninternal/server/lib/sql/DataSourcePool;
/*     */     //   85: aload_0
/*     */     //   86: dup
/*     */     //   87: getfield 182	com/avaje/ebeaninternal/server/lib/sql/PooledConnectionQueue:connectionId	I
/*     */     //   90: dup_x1
/*     */     //   91: iconst_1
/*     */     //   92: iadd
/*     */     //   93: putfield 182	com/avaje/ebeaninternal/server/lib/sql/PooledConnectionQueue:connectionId	I
/*     */     //   96: invokevirtual 186	com/avaje/ebeaninternal/server/lib/sql/DataSourcePool:createConnectionForQueue	(I)Lcom/avaje/ebeaninternal/server/lib/sql/PooledConnection;
/*     */     //   99: astore_3
/*     */     //   100: aload_0
/*     */     //   101: aload_3
/*     */     //   102: invokespecial 242	com/avaje/ebeaninternal/server/lib/sql/PooledConnectionQueue:registerBusyConnection	(Lcom/avaje/ebeaninternal/server/lib/sql/PooledConnection;)I
/*     */     //   105: istore 4
/*     */     //   107: new 147	java/lang/StringBuilder
/*     */     //   110: dup
/*     */     //   111: invokespecial 148	java/lang/StringBuilder:<init>	()V
/*     */     //   114: ldc_w 272
/*     */     //   117: invokevirtual 154	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
/*     */     //   120: aload_0
/*     */     //   121: getfield 54	com/avaje/ebeaninternal/server/lib/sql/PooledConnectionQueue:name	Ljava/lang/String;
/*     */     //   124: invokevirtual 154	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
/*     */     //   127: ldc_w 274
/*     */     //   130: invokevirtual 154	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
/*     */     //   133: aload_3
/*     */     //   134: invokevirtual 275	com/avaje/ebeaninternal/server/lib/sql/PooledConnection:getName	()Ljava/lang/String;
/*     */     //   137: invokevirtual 154	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
/*     */     //   140: ldc_w 277
/*     */     //   143: invokevirtual 154	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
/*     */     //   146: iload 4
/*     */     //   148: invokevirtual 157	java/lang/StringBuilder:append	(I)Ljava/lang/StringBuilder;
/*     */     //   151: ldc_w 279
/*     */     //   154: invokevirtual 154	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
/*     */     //   157: aload_0
/*     */     //   158: getfield 65	com/avaje/ebeaninternal/server/lib/sql/PooledConnectionQueue:maxSize	I
/*     */     //   161: invokevirtual 157	java/lang/StringBuilder:append	(I)Ljava/lang/StringBuilder;
/*     */     //   164: ldc_w 281
/*     */     //   167: invokevirtual 154	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
/*     */     //   170: invokevirtual 160	java/lang/StringBuilder:toString	()Ljava/lang/String;
/*     */     //   173: astore 5
/*     */     //   175: getstatic 205	com/avaje/ebeaninternal/server/lib/sql/PooledConnectionQueue:logger	Ljava/util/logging/Logger;
/*     */     //   178: aload 5
/*     */     //   180: invokevirtual 284	java/util/logging/Logger:info	(Ljava/lang/String;)V
/*     */     //   183: aload_0
/*     */     //   184: invokespecial 287	com/avaje/ebeaninternal/server/lib/sql/PooledConnectionQueue:checkForWarningSize	()V
/*     */     //   187: aload_3
/*     */     //   188: astore 6
/*     */     //   190: aload_1
/*     */     //   191: invokevirtual 137	java/util/concurrent/locks/ReentrantLock:unlock	()V
/*     */     //   194: aload 6
/*     */     //   196: areturn
/*     */     //   197: aload_0
/*     */     //   198: dup
/*     */     //   199: getfield 122	com/avaje/ebeaninternal/server/lib/sql/PooledConnectionQueue:waitCount	I
/*     */     //   202: iconst_1
/*     */     //   203: iadd
/*     */     //   204: putfield 122	com/avaje/ebeaninternal/server/lib/sql/PooledConnectionQueue:waitCount	I
/*     */     //   207: aload_0
/*     */     //   208: dup
/*     */     //   209: getfield 118	com/avaje/ebeaninternal/server/lib/sql/PooledConnectionQueue:waitingThreads	I
/*     */     //   212: iconst_1
/*     */     //   213: iadd
/*     */     //   214: putfield 118	com/avaje/ebeaninternal/server/lib/sql/PooledConnectionQueue:waitingThreads	I
/*     */     //   217: aload_0
/*     */     //   218: invokespecial 290	com/avaje/ebeaninternal/server/lib/sql/PooledConnectionQueue:_getPooledConnectionWaitLoop	()Lcom/avaje/ebeaninternal/server/lib/sql/PooledConnection;
/*     */     //   221: astore_2
/*     */     //   222: aload_0
/*     */     //   223: dup
/*     */     //   224: getfield 118	com/avaje/ebeaninternal/server/lib/sql/PooledConnectionQueue:waitingThreads	I
/*     */     //   227: iconst_1
/*     */     //   228: isub
/*     */     //   229: putfield 118	com/avaje/ebeaninternal/server/lib/sql/PooledConnectionQueue:waitingThreads	I
/*     */     //   232: aload_1
/*     */     //   233: invokevirtual 137	java/util/concurrent/locks/ReentrantLock:unlock	()V
/*     */     //   236: aload_2
/*     */     //   237: areturn
/*     */     //   238: astore 7
/*     */     //   240: aload_0
/*     */     //   241: dup
/*     */     //   242: getfield 118	com/avaje/ebeaninternal/server/lib/sql/PooledConnectionQueue:waitingThreads	I
/*     */     //   245: iconst_1
/*     */     //   246: isub
/*     */     //   247: putfield 118	com/avaje/ebeaninternal/server/lib/sql/PooledConnectionQueue:waitingThreads	I
/*     */     //   250: aload 7
/*     */     //   252: athrow
/*     */     //   253: astore 8
/*     */     //   255: aload_1
/*     */     //   256: invokevirtual 137	java/util/concurrent/locks/ReentrantLock:unlock	()V
/*     */     //   259: aload 8
/*     */     //   261: athrow
/*     */     // Line number table:
/*     */     //   Java source line #269	-> byte code offset #0
/*     */     //   Java source line #270	-> byte code offset #5
/*     */     //   Java source line #272	-> byte code offset #9
/*     */     //   Java source line #273	-> byte code offset #16
/*     */     //   Java source line #278	-> byte code offset #27
/*     */     //   Java source line #281	-> byte code offset #37
/*     */     //   Java source line #283	-> byte code offset #44
/*     */     //   Java source line #284	-> byte code offset #52
/*     */     //   Java source line #286	-> byte code offset #56
/*     */     //   Java source line #313	-> byte code offset #61
/*     */     //   Java source line #289	-> byte code offset #67
/*     */     //   Java source line #291	-> byte code offset #81
/*     */     //   Java source line #292	-> byte code offset #100
/*     */     //   Java source line #294	-> byte code offset #107
/*     */     //   Java source line #295	-> byte code offset #175
/*     */     //   Java source line #297	-> byte code offset #183
/*     */     //   Java source line #298	-> byte code offset #187
/*     */     //   Java source line #313	-> byte code offset #190
/*     */     //   Java source line #305	-> byte code offset #197
/*     */     //   Java source line #306	-> byte code offset #207
/*     */     //   Java source line #307	-> byte code offset #217
/*     */     //   Java source line #309	-> byte code offset #222
/*     */     //   Java source line #313	-> byte code offset #232
/*     */     //   Java source line #309	-> byte code offset #238
/*     */     //   Java source line #313	-> byte code offset #253
/*     */     // Local variable table:
/*     */     //   start	length	slot	name	signature
/*     */     //   0	262	0	this	PooledConnectionQueue
/*     */     //   4	252	1	lock	ReentrantLock
/*     */     //   51	186	2	freeSize	int
/*     */     //   60	6	3	localPooledConnection1	PooledConnection
/*     */     //   99	89	3	c	PooledConnection
/*     */     //   105	42	4	busySize	int
/*     */     //   173	6	5	msg	String
/*     */     //   188	7	6	localPooledConnection2	PooledConnection
/*     */     //   238	13	7	localObject1	Object
/*     */     //   253	7	8	localObject2	Object
/*     */     // Exception table:
/*     */     //   from	to	target	type
/*     */     //   197	222	238	finally
/*     */     //   238	240	238	finally
/*     */     //   9	61	253	finally
/*     */     //   67	190	253	finally
/*     */     //   197	232	253	finally
/*     */     //   238	255	253	finally
/*     */   }
/*     */   
/*     */   private PooledConnection _getPooledConnectionWaitLoop()
/*     */     throws SQLException, InterruptedException
/*     */   {
/* 322 */     long nanos = MILLIS_TIME_UNIT.toNanos(this.waitTimeoutMillis);
/*     */     for (;;)
/*     */     {
/* 325 */       if (nanos <= 0L) {
/* 326 */         String msg = "Unsuccessfully waited [" + this.waitTimeoutMillis + "] millis for a connection to be returned." + " No connections are free. You need to Increase the max connections of [" + this.maxSize + "]" + " or look for a connection pool leak using datasource.xxx.capturestacktrace=true";
/*     */         
/*     */ 
/* 329 */         if (this.pool.isCaptureStackTrace()) {
/* 330 */           dumpBusyConnectionInformation();
/*     */         }
/*     */         
/* 333 */         throw new SQLException(msg);
/*     */       }
/*     */       try
/*     */       {
/* 337 */         nanos = this.notEmpty.awaitNanos(nanos);
/* 338 */         if (!this.freeList.isEmpty())
/*     */         {
/* 340 */           return extractFromFreeList();
/*     */         }
/*     */       } catch (InterruptedException ie) {
/* 343 */         this.notEmpty.signal();
/* 344 */         throw ie;
/*     */       }
/*     */     }
/*     */   }
/*     */   
/*     */   public void shutdown() {
/* 350 */     ReentrantLock lock = this.lock;
/* 351 */     lock.lock();
/*     */     try {
/* 353 */       this.doingShutdown = true;
/* 354 */       DataSourcePool.Status status = createStatus();
/* 355 */       logger.info("DataSourcePool [" + this.name + "] shutdown: " + status);
/*     */       
/* 357 */       closeFreeConnections(true);
/*     */       
/* 359 */       if (!this.busyList.isEmpty()) {
/* 360 */         String msg = "A potential connection leak was detected.  Busy connections: " + this.busyList.size();
/* 361 */         logger.warning(msg);
/*     */         
/* 363 */         dumpBusyConnectionInformation();
/* 364 */         closeBusyConnections(0L);
/*     */       }
/*     */     } finally {
/* 367 */       lock.unlock();
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public void reset(long leakTimeMinutes)
/*     */   {
/* 379 */     ReentrantLock lock = this.lock;
/* 380 */     lock.lock();
/*     */     try {
/* 382 */       DataSourcePool.Status status = createStatus();
/* 383 */       logger.info("Reseting DataSourcePool [" + this.name + "] " + status);
/* 384 */       this.lastResetTime = System.currentTimeMillis();
/*     */       
/* 386 */       closeFreeConnections(false);
/* 387 */       closeBusyConnections(leakTimeMinutes);
/*     */       
/* 389 */       String busyMsg = "Busy Connections:\r\n" + getBusyConnectionInformation();
/* 390 */       logger.info(busyMsg);
/*     */     }
/*     */     finally {
/* 393 */       lock.unlock();
/*     */     }
/*     */   }
/*     */   
/*     */   public void trim(int maxInactiveTimeSecs) throws SQLException {
/* 398 */     ReentrantLock lock = this.lock;
/* 399 */     lock.lock();
/*     */     try {
/* 401 */       trimInactiveConnections(maxInactiveTimeSecs);
/* 402 */       ensureMinimumConnections();
/*     */     }
/*     */     finally {
/* 405 */       lock.unlock();
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   private int trimInactiveConnections(int maxInactiveTimeSecs)
/*     */   {
/* 414 */     int maxTrim = this.freeList.size() - this.minSize;
/* 415 */     if (maxTrim <= 0) {
/* 416 */       return 0;
/*     */     }
/*     */     
/* 419 */     int trimedCount = 0;
/* 420 */     long usedSince = System.currentTimeMillis() - maxInactiveTimeSecs * 1000;
/*     */     
/*     */ 
/* 423 */     List<PooledConnection> freeListCopy = this.freeList.getShallowCopy();
/*     */     
/* 425 */     Iterator<PooledConnection> it = freeListCopy.iterator();
/* 426 */     while (it.hasNext()) {
/* 427 */       PooledConnection pc = (PooledConnection)it.next();
/* 428 */       if (pc.getLastUsedTime() < usedSince)
/*     */       {
/* 430 */         trimedCount++;
/* 431 */         it.remove();
/* 432 */         pc.closeConnectionFully(true);
/* 433 */         if (trimedCount >= maxTrim) {
/*     */           break;
/*     */         }
/*     */       }
/*     */     }
/*     */     
/* 439 */     if (trimedCount > 0)
/*     */     {
/*     */ 
/* 442 */       this.freeList.setShallowCopy(freeListCopy);
/*     */       
/* 444 */       String msg = "DataSourcePool [" + this.name + "] trimmed [" + trimedCount + "] inactive connections. New size[" + totalConnections() + "]";
/* 445 */       logger.info(msg);
/*     */     }
/* 447 */     return trimedCount;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public void closeFreeConnections(boolean logErrors)
/*     */   {
/* 454 */     ReentrantLock lock = this.lock;
/* 455 */     lock.lock();
/*     */     try {
/* 457 */       while (!this.freeList.isEmpty()) {
/* 458 */         PooledConnection c = this.freeList.remove();
/* 459 */         logger.info("PSTMT Statistics: " + c.getStatistics());
/* 460 */         c.closeConnectionFully(logErrors);
/*     */       }
/*     */     } finally {
/* 463 */       lock.unlock();
/*     */     }
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
/*     */   public void closeBusyConnections(long leakTimeMinutes)
/*     */   {
/* 481 */     ReentrantLock lock = this.lock;
/* 482 */     lock.lock();
/*     */     try
/*     */     {
/* 485 */       long olderThanTime = System.currentTimeMillis() - leakTimeMinutes * 60000L;
/*     */       
/* 487 */       List<PooledConnection> copy = this.busyList.getShallowCopy();
/* 488 */       for (int i = 0; i < copy.size(); i++) {
/* 489 */         PooledConnection pc = (PooledConnection)copy.get(i);
/* 490 */         if ((!pc.isLongRunning()) && (pc.getLastUsedTime() <= olderThanTime))
/*     */         {
/*     */ 
/*     */ 
/* 494 */           this.busyList.remove(pc);
/* 495 */           closeBusyConnection(pc);
/*     */         }
/*     */       }
/*     */     }
/*     */     finally {
/* 500 */       lock.unlock();
/*     */     }
/*     */   }
/*     */   
/*     */   private void closeBusyConnection(PooledConnection pc) {
/*     */     try {
/* 506 */       String methodLine = pc.getCreatedByMethod();
/*     */       
/* 508 */       Date luDate = new Date();
/* 509 */       luDate.setTime(pc.getLastUsedTime());
/*     */       
/* 511 */       String msg = "DataSourcePool closing leaked connection?  name[" + pc.getName() + "] lastUsed[" + luDate + "] createdBy[" + methodLine + "] lastStmt[" + pc.getLastStatement() + "]";
/*     */       
/*     */ 
/*     */ 
/* 515 */       logger.warning(msg);
/* 516 */       logStackElement(pc, "Possible Leaked Connection: ");
/*     */       
/* 518 */       System.out.println("CLOSING BUSY CONNECTION ??? " + pc);
/* 519 */       pc.close();
/*     */     }
/*     */     catch (SQLException ex)
/*     */     {
/* 523 */       logger.log(Level.SEVERE, null, ex);
/*     */     }
/*     */   }
/*     */   
/*     */   private void logStackElement(PooledConnection pc, String prefix) {
/* 528 */     StackTraceElement[] stackTrace = pc.getStackTrace();
/* 529 */     if (stackTrace != null) {
/* 530 */       String s = Arrays.toString(stackTrace);
/* 531 */       String msg = prefix + " name[" + pc.getName() + "] stackTrace: " + s;
/* 532 */       logger.warning(msg);
/*     */       
/*     */ 
/* 535 */       System.err.println(msg);
/*     */     }
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
/*     */   private void checkForWarningSize()
/*     */   {
/* 552 */     int availableGrowth = this.maxSize - totalConnections();
/*     */     
/* 554 */     if (availableGrowth < this.warningSize)
/*     */     {
/* 556 */       closeBusyConnections(this.leakTimeMinutes);
/*     */       
/* 558 */       String msg = "DataSourcePool [" + this.name + "] is [" + availableGrowth + "] connections from its maximum size.";
/* 559 */       this.pool.notifyWarning(msg);
/*     */     }
/*     */   }
/*     */   
/*     */   public String getBusyConnectionInformation() {
/* 564 */     return getBusyConnectionInformation(false);
/*     */   }
/*     */   
/*     */   public void dumpBusyConnectionInformation() {
/* 568 */     getBusyConnectionInformation(true);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   private String getBusyConnectionInformation(boolean toLogger)
/*     */   {
/* 576 */     ReentrantLock lock = this.lock;
/* 577 */     lock.lock();
/*     */     try
/*     */     {
/* 580 */       if (toLogger) {
/* 581 */         logger.info("Dumping busy connections: (Use datasource.xxx.capturestacktrace=true  ... to get stackTraces)");
/*     */       }
/*     */       
/* 584 */       StringBuilder sb = new StringBuilder();
/*     */       
/* 586 */       List<PooledConnection> copy = this.busyList.getShallowCopy();
/* 587 */       for (int i = 0; i < copy.size(); i++) {
/* 588 */         PooledConnection pc = (PooledConnection)copy.get(i);
/* 589 */         if (toLogger) {
/* 590 */           logger.info(pc.getDescription());
/* 591 */           logStackElement(pc, "Busy Connection: ");
/*     */         }
/*     */         else {
/* 594 */           sb.append(pc.getDescription()).append("\r\n");
/*     */         }
/*     */       }
/*     */       
/* 598 */       return sb.toString();
/*     */     }
/*     */     finally {
/* 601 */       lock.unlock();
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\com\avaje\ebeaninternal\server\lib\sql\PooledConnectionQueue.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */
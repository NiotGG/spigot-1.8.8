/*     */ package com.avaje.ebeaninternal.server.lib;
/*     */ 
/*     */ import com.avaje.ebean.common.BootupEbeanManager;
/*     */ import com.avaje.ebean.config.GlobalProperties;
/*     */ import com.avaje.ebeaninternal.api.ClassUtil;
/*     */ import com.avaje.ebeaninternal.server.lib.sql.DataSourceGlobalManager;
/*     */ import com.avaje.ebeaninternal.server.lib.thread.ThreadPoolManager;
/*     */ import java.io.PrintStream;
/*     */ import java.sql.Driver;
/*     */ import java.sql.DriverManager;
/*     */ import java.sql.SQLException;
/*     */ import java.util.Enumeration;
/*     */ import java.util.Vector;
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
/*     */ public final class ShutdownManager
/*     */ {
/*  42 */   private static final Logger logger = Logger.getLogger(BackgroundThread.class.getName());
/*     */   
/*  44 */   static final Vector<Runnable> runnables = new Vector();
/*     */   
/*     */   static boolean stopping;
/*     */   
/*     */   static BootupEbeanManager serverFactory;
/*     */   
/*  50 */   static final ShutdownHook shutdownHook = new ShutdownHook();
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*  56 */   static { register(); }
/*  57 */   static boolean whyShutdown = GlobalProperties.getBoolean("debug.shutdown.why", false);
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public static void registerServerFactory(BootupEbeanManager factory)
/*     */   {
/*  67 */     serverFactory = factory;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   private static void deregister()
/*     */   {
/*  93 */     synchronized (runnables) {
/*     */       try {
/*  95 */         Runtime.getRuntime().removeShutdownHook(shutdownHook);
/*     */       } catch (IllegalStateException ex) {
/*  97 */         if (!ex.getMessage().equals("Shutdown in progress")) {
/*  98 */           throw ex;
/*     */         }
/*     */       }
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   private static void register()
/*     */   {
/* 108 */     synchronized (runnables) {
/*     */       try {
/* 110 */         Runtime.getRuntime().addShutdownHook(shutdownHook);
/*     */       } catch (IllegalStateException ex) {
/* 112 */         if (!ex.getMessage().equals("Shutdown in progress")) {
/* 113 */           throw ex;
/*     */         }
/*     */       }
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
/*     */   public static void shutdown()
/*     */   {
/* 132 */     synchronized (runnables) {
/* 133 */       if (stopping)
/*     */       {
/* 135 */         return;
/*     */       }
/*     */       
/* 138 */       if (whyShutdown) {
/*     */         try {
/* 140 */           throw new RuntimeException("debug.shutdown.why=true ...");
/*     */         } catch (Throwable e) {
/* 142 */           logger.log(Level.WARNING, "Stacktrace showing why shutdown was fired", e);
/*     */         }
/*     */       }
/*     */       
/* 146 */       stopping = true;
/*     */       
/*     */ 
/* 149 */       deregister();
/*     */       
/*     */ 
/* 152 */       BackgroundThread.shutdown();
/*     */       
/* 154 */       String shutdownRunner = GlobalProperties.get("system.shutdown.runnable", null);
/* 155 */       if (shutdownRunner != null) {
/*     */         try {
/* 157 */           Runnable r = (Runnable)ClassUtil.newInstance(shutdownRunner);
/* 158 */           r.run();
/*     */         } catch (Exception e) {
/* 160 */           logger.log(Level.SEVERE, null, e);
/*     */         }
/*     */       }
/*     */       
/*     */ 
/*     */ 
/* 166 */       Enumeration<Runnable> e = runnables.elements();
/* 167 */       while (e.hasMoreElements()) {
/*     */         try {
/* 169 */           Runnable r = (Runnable)e.nextElement();
/* 170 */           r.run();
/*     */         } catch (Exception ex) {
/* 172 */           logger.log(Level.SEVERE, null, ex);
/* 173 */           ex.printStackTrace();
/*     */         }
/*     */       }
/*     */       
/*     */ 
/*     */       try
/*     */       {
/* 180 */         if (serverFactory != null) {
/* 181 */           serverFactory.shutdown();
/*     */         }
/*     */         
/* 184 */         ThreadPoolManager.shutdown();
/*     */         
/* 186 */         DataSourceGlobalManager.shutdown();
/*     */         
/* 188 */         boolean dereg = GlobalProperties.getBoolean("datasource.deregisterAllDrivers", false);
/* 189 */         if (dereg) {
/* 190 */           deregisterAllJdbcDrivers();
/*     */         }
/*     */       }
/*     */       catch (Exception ex) {
/* 194 */         String msg = "Shutdown Exception: " + ex.getMessage();
/* 195 */         System.err.println(msg);
/* 196 */         ex.printStackTrace();
/*     */         try {
/* 198 */           logger.log(Level.SEVERE, null, ex);
/*     */         } catch (Exception exc) {
/* 200 */           String ms = "Error Logging error to the Log. It may be shutting down.";
/* 201 */           System.err.println(ms);
/* 202 */           exc.printStackTrace();
/*     */         }
/*     */       }
/*     */     }
/*     */   }
/*     */   
/*     */   private static void deregisterAllJdbcDrivers()
/*     */   {
/* 210 */     Enumeration<Driver> drivers = DriverManager.getDrivers();
/* 211 */     while (drivers.hasMoreElements()) {
/* 212 */       Driver driver = (Driver)drivers.nextElement();
/*     */       try {
/* 214 */         DriverManager.deregisterDriver(driver);
/* 215 */         logger.log(Level.INFO, String.format("Deregistering jdbc driver: %s", new Object[] { driver }));
/*     */       } catch (SQLException e) {
/* 217 */         logger.log(Level.SEVERE, String.format("Error deregistering driver %s", new Object[] { driver }), e);
/*     */       }
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public static void register(Runnable runnable)
/*     */   {
/* 228 */     synchronized (runnables) {
/* 229 */       runnables.add(runnable);
/*     */     }
/*     */   }
/*     */   
/*     */   public static void touch() {}
/*     */   
/*     */   /* Error */
/*     */   public static boolean isStopping()
/*     */   {
/*     */     // Byte code:
/*     */     //   0: getstatic 33	com/avaje/ebeaninternal/server/lib/ShutdownManager:runnables	Ljava/util/Vector;
/*     */     //   3: dup
/*     */     //   4: astore_0
/*     */     //   5: monitorenter
/*     */     //   6: getstatic 35	com/avaje/ebeaninternal/server/lib/ShutdownManager:stopping	Z
/*     */     //   9: aload_0
/*     */     //   10: monitorexit
/*     */     //   11: ireturn
/*     */     //   12: astore_1
/*     */     //   13: aload_0
/*     */     //   14: monitorexit
/*     */     //   15: aload_1
/*     */     //   16: athrow
/*     */     // Line number table:
/*     */     //   Java source line #80	-> byte code offset #0
/*     */     //   Java source line #81	-> byte code offset #6
/*     */     //   Java source line #82	-> byte code offset #12
/*     */     // Local variable table:
/*     */     //   start	length	slot	name	signature
/*     */     //   4	10	0	Ljava/lang/Object;	Object
/*     */     //   12	4	1	localObject1	Object
/*     */     // Exception table:
/*     */     //   from	to	target	type
/*     */     //   6	11	12	finally
/*     */     //   12	15	12	finally
/*     */   }
/*     */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\com\avaje\ebeaninternal\server\lib\ShutdownManager.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */
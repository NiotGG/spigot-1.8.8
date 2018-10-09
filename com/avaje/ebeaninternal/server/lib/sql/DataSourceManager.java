/*     */ package com.avaje.ebeaninternal.server.lib.sql;
/*     */ 
/*     */ import com.avaje.ebean.config.DataSourceConfig;
/*     */ import com.avaje.ebean.config.GlobalProperties;
/*     */ import com.avaje.ebeaninternal.api.ClassUtil;
/*     */ import com.avaje.ebeaninternal.server.lib.BackgroundRunnable;
/*     */ import com.avaje.ebeaninternal.server.lib.BackgroundThread;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Collection;
/*     */ import java.util.Hashtable;
/*     */ import java.util.Iterator;
/*     */ import java.util.List;
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
/*     */ public class DataSourceManager
/*     */   implements DataSourceNotify
/*     */ {
/*  40 */   private static final Logger logger = Logger.getLogger(DataSourceManager.class.getName());
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   private final DataSourceAlertListener alertlistener;
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*  50 */   private final Hashtable<String, DataSourcePool> dsMap = new Hashtable();
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*  55 */   private final Object monitor = new Object();
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   private final BackgroundRunnable dbChecker;
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   private final int dbUpFreqInSecs;
/*     */   
/*     */ 
/*     */ 
/*     */   private final int dbDownFreqInSecs;
/*     */   
/*     */ 
/*     */ 
/*     */   private boolean shuttingDown;
/*     */   
/*     */ 
/*     */ 
/*     */   private boolean deregisterDriver;
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public DataSourceManager()
/*     */   {
/*  84 */     this.alertlistener = createAlertListener();
/*     */     
/*     */ 
/*  87 */     this.dbUpFreqInSecs = GlobalProperties.getInt("datasource.heartbeatfreq", 30);
/*  88 */     this.dbDownFreqInSecs = GlobalProperties.getInt("datasource.deadbeatfreq", 10);
/*  89 */     this.dbChecker = new BackgroundRunnable(new Checker(null), this.dbUpFreqInSecs);
/*  90 */     this.deregisterDriver = GlobalProperties.getBoolean("datasource.deregisterDriver", true);
/*     */     try
/*     */     {
/*  93 */       BackgroundThread.add(this.dbChecker);
/*     */     }
/*     */     catch (Exception e) {
/*  96 */       logger.log(Level.SEVERE, null, e);
/*     */     }
/*     */   }
/*     */   
/*     */   private DataSourceAlertListener createAlertListener() throws DataSourceException
/*     */   {
/* 102 */     String alertCN = GlobalProperties.get("datasource.alert.class", null);
/* 103 */     if (alertCN == null) {
/* 104 */       return new SimpleAlerter();
/*     */     }
/*     */     try
/*     */     {
/* 108 */       return (DataSourceAlertListener)ClassUtil.newInstance(alertCN, getClass());
/*     */     }
/*     */     catch (Exception ex) {
/* 111 */       throw new DataSourceException(ex);
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public void notifyDataSourceUp(String dataSourceName)
/*     */   {
/* 121 */     this.dbChecker.setFreqInSecs(this.dbUpFreqInSecs);
/*     */     
/* 123 */     if (this.alertlistener != null) {
/* 124 */       this.alertlistener.dataSourceUp(dataSourceName);
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public void notifyDataSourceDown(String dataSourceName)
/*     */   {
/* 133 */     this.dbChecker.setFreqInSecs(this.dbDownFreqInSecs);
/*     */     
/* 135 */     if (this.alertlistener != null) {
/* 136 */       this.alertlistener.dataSourceDown(dataSourceName);
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public void notifyWarning(String subject, String msg)
/*     */   {
/* 144 */     if (this.alertlistener != null) {
/* 145 */       this.alertlistener.warning(subject, msg);
/*     */     }
/*     */   }
/*     */   
/*     */   /* Error */
/*     */   public boolean isShuttingDown()
/*     */   {
/*     */     // Byte code:
/*     */     //   0: aload_0
/*     */     //   1: getfield 42	com/avaje/ebeaninternal/server/lib/sql/DataSourceManager:monitor	Ljava/lang/Object;
/*     */     //   4: dup
/*     */     //   5: astore_1
/*     */     //   6: monitorenter
/*     */     //   7: aload_0
/*     */     //   8: getfield 158	com/avaje/ebeaninternal/server/lib/sql/DataSourceManager:shuttingDown	Z
/*     */     //   11: aload_1
/*     */     //   12: monitorexit
/*     */     //   13: ireturn
/*     */     //   14: astore_2
/*     */     //   15: aload_1
/*     */     //   16: monitorexit
/*     */     //   17: aload_2
/*     */     //   18: athrow
/*     */     // Line number table:
/*     */     //   Java source line #153	-> byte code offset #0
/*     */     //   Java source line #154	-> byte code offset #7
/*     */     //   Java source line #155	-> byte code offset #14
/*     */     // Local variable table:
/*     */     //   start	length	slot	name	signature
/*     */     //   0	19	0	this	DataSourceManager
/*     */     //   5	11	1	Ljava/lang/Object;	Object
/*     */     //   14	4	2	localObject1	Object
/*     */     // Exception table:
/*     */     //   from	to	target	type
/*     */     //   7	13	14	finally
/*     */     //   14	17	14	finally
/*     */   }
/*     */   
/*     */   public void shutdown()
/*     */   {
/* 163 */     synchronized (this.monitor)
/*     */     {
/* 165 */       this.shuttingDown = true;
/*     */       
/* 167 */       Collection<DataSourcePool> values = this.dsMap.values();
/* 168 */       for (DataSourcePool ds : values) {
/*     */         try {
/* 170 */           ds.shutdown();
/*     */         }
/*     */         catch (DataSourceException e) {
/* 173 */           logger.log(Level.SEVERE, null, e);
/*     */         }
/*     */       }
/* 176 */       if (this.deregisterDriver) {
/* 177 */         for (DataSourcePool ds : values) {
/* 178 */           ds.deregisterDriver();
/*     */         }
/*     */       }
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public List<DataSourcePool> getPools()
/*     */   {
/* 188 */     synchronized (this.monitor)
/*     */     {
/* 190 */       ArrayList<DataSourcePool> list = new ArrayList();
/* 191 */       list.addAll(this.dsMap.values());
/* 192 */       return list;
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public DataSourcePool getDataSource(String name)
/*     */   {
/* 200 */     return getDataSource(name, null);
/*     */   }
/*     */   
/*     */ 
/*     */   public DataSourcePool getDataSource(String name, DataSourceConfig dsConfig)
/*     */   {
/* 206 */     if (name == null) {
/* 207 */       throw new IllegalArgumentException("name not defined");
/*     */     }
/*     */     
/* 210 */     synchronized (this.monitor) {
/* 211 */       DataSourcePool pool = (DataSourcePool)this.dsMap.get(name);
/* 212 */       if (pool == null) {
/* 213 */         if (dsConfig == null) {
/* 214 */           dsConfig = new DataSourceConfig();
/* 215 */           dsConfig.loadSettings(name);
/*     */         }
/* 217 */         pool = new DataSourcePool(this, name, dsConfig);
/* 218 */         this.dsMap.put(name, pool);
/*     */       }
/* 220 */       return pool;
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   private void checkDataSource()
/*     */   {
/* 230 */     synchronized (this.monitor) {
/* 231 */       if (!isShuttingDown()) {
/* 232 */         Iterator<DataSourcePool> it = this.dsMap.values().iterator();
/* 233 */         while (it.hasNext()) {
/* 234 */           DataSourcePool ds = (DataSourcePool)it.next();
/* 235 */           ds.checkDataSource();
/*     */         }
/*     */       }
/*     */     }
/*     */   }
/*     */   
/*     */   private final class Checker implements Runnable
/*     */   {
/*     */     private Checker() {}
/*     */     
/*     */     public void run()
/*     */     {
/* 247 */       DataSourceManager.this.checkDataSource();
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\com\avaje\ebeaninternal\server\lib\sql\DataSourceManager.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */
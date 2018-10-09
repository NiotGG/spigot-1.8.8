/*     */ package com.avaje.ebeaninternal.server.cluster;
/*     */ 
/*     */ import com.avaje.ebean.EbeanServer;
/*     */ import com.avaje.ebean.config.GlobalProperties;
/*     */ import com.avaje.ebeaninternal.api.ClassUtil;
/*     */ import com.avaje.ebeaninternal.server.cluster.mcast.McastClusterManager;
/*     */ import com.avaje.ebeaninternal.server.cluster.socket.SocketClusterBroadcast;
/*     */ import com.avaje.ebeaninternal.server.transaction.RemoteTransactionEvent;
/*     */ import java.util.concurrent.ConcurrentHashMap;
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
/*     */ public class ClusterManager
/*     */ {
/*  36 */   private static final Logger logger = Logger.getLogger(ClusterManager.class.getName());
/*     */   
/*  38 */   private final ConcurrentHashMap<String, EbeanServer> serverMap = new ConcurrentHashMap();
/*     */   
/*  40 */   private final Object monitor = new Object();
/*     */   
/*     */   private final ClusterBroadcast broadcast;
/*     */   
/*     */   private boolean started;
/*     */   
/*     */   public ClusterManager()
/*     */   {
/*  48 */     String clusterType = GlobalProperties.get("ebean.cluster.type", null);
/*  49 */     if ((clusterType == null) || (clusterType.trim().length() == 0))
/*     */     {
/*  51 */       this.broadcast = null;
/*     */     }
/*     */     else {
/*     */       try
/*     */       {
/*  56 */         if ("mcast".equalsIgnoreCase(clusterType)) {
/*  57 */           this.broadcast = new McastClusterManager();
/*     */         }
/*  59 */         else if ("socket".equalsIgnoreCase(clusterType)) {
/*  60 */           this.broadcast = new SocketClusterBroadcast();
/*     */         }
/*     */         else {
/*  63 */           logger.info("Clustering using [" + clusterType + "]");
/*  64 */           this.broadcast = ((ClusterBroadcast)ClassUtil.newInstance(clusterType));
/*     */         }
/*     */       }
/*     */       catch (Exception e) {
/*  68 */         String msg = "Error initialising ClusterManager type [" + clusterType + "]";
/*  69 */         logger.log(Level.SEVERE, msg, e);
/*  70 */         throw new RuntimeException(e);
/*     */       }
/*     */     }
/*     */   }
/*     */   
/*     */   public void registerServer(EbeanServer server) {
/*  76 */     synchronized (this.monitor) {
/*  77 */       if (!this.started) {
/*  78 */         startup();
/*     */       }
/*  80 */       this.serverMap.put(server.getName(), server);
/*     */     }
/*     */   }
/*     */   
/*     */   /* Error */
/*     */   public EbeanServer getServer(String name)
/*     */   {
/*     */     // Byte code:
/*     */     //   0: aload_0
/*     */     //   1: getfield 29	com/avaje/ebeaninternal/server/cluster/ClusterManager:monitor	Ljava/lang/Object;
/*     */     //   4: dup
/*     */     //   5: astore_2
/*     */     //   6: monitorenter
/*     */     //   7: aload_0
/*     */     //   8: getfield 27	com/avaje/ebeaninternal/server/cluster/ClusterManager:serverMap	Ljava/util/concurrent/ConcurrentHashMap;
/*     */     //   11: aload_1
/*     */     //   12: invokevirtual 140	java/util/concurrent/ConcurrentHashMap:get	(Ljava/lang/Object;)Ljava/lang/Object;
/*     */     //   15: checkcast 126	com/avaje/ebean/EbeanServer
/*     */     //   18: aload_2
/*     */     //   19: monitorexit
/*     */     //   20: areturn
/*     */     //   21: astore_3
/*     */     //   22: aload_2
/*     */     //   23: monitorexit
/*     */     //   24: aload_3
/*     */     //   25: athrow
/*     */     // Line number table:
/*     */     //   Java source line #85	-> byte code offset #0
/*     */     //   Java source line #86	-> byte code offset #7
/*     */     //   Java source line #87	-> byte code offset #21
/*     */     // Local variable table:
/*     */     //   start	length	slot	name	signature
/*     */     //   0	26	0	this	ClusterManager
/*     */     //   0	26	1	name	String
/*     */     //   5	18	2	Ljava/lang/Object;	Object
/*     */     //   21	4	3	localObject1	Object
/*     */     // Exception table:
/*     */     //   from	to	target	type
/*     */     //   7	20	21	finally
/*     */     //   21	24	21	finally
/*     */   }
/*     */   
/*     */   private void startup()
/*     */   {
/*  91 */     this.started = true;
/*  92 */     if (this.broadcast != null) {
/*  93 */       this.broadcast.startup(this);
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public boolean isClustering()
/*     */   {
/* 101 */     return this.broadcast != null;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public void broadcast(RemoteTransactionEvent remoteTransEvent)
/*     */   {
/* 108 */     if (this.broadcast != null) {
/* 109 */       this.broadcast.broadcast(remoteTransEvent);
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public void shutdown()
/*     */   {
/* 117 */     if (this.broadcast != null) {
/* 118 */       logger.info("ClusterManager shutdown ");
/* 119 */       this.broadcast.shutdown();
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\com\avaje\ebeaninternal\server\cluster\ClusterManager.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */
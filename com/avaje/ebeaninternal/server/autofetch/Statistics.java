/*     */ package com.avaje.ebeaninternal.server.autofetch;
/*     */ 
/*     */ import com.avaje.ebean.bean.NodeUsageCollector;
/*     */ import com.avaje.ebean.bean.ObjectGraphNode;
/*     */ import com.avaje.ebean.bean.ObjectGraphOrigin;
/*     */ import com.avaje.ebean.meta.MetaAutoFetchStatistic;
/*     */ import com.avaje.ebean.meta.MetaAutoFetchStatistic.NodeUsageStats;
/*     */ import com.avaje.ebean.meta.MetaAutoFetchStatistic.QueryStats;
/*     */ import com.avaje.ebean.text.PathProperties;
/*     */ import com.avaje.ebean.text.PathProperties.Props;
/*     */ import com.avaje.ebeaninternal.server.deploy.BeanDescriptor;
/*     */ import com.avaje.ebeaninternal.server.querydefn.OrmQueryDetail;
/*     */ import java.io.Serializable;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Collection;
/*     */ import java.util.Iterator;
/*     */ import java.util.LinkedHashMap;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class Statistics
/*     */   implements Serializable
/*     */ {
/*     */   private static final long serialVersionUID = -5586783791097230766L;
/*     */   private final ObjectGraphOrigin origin;
/*     */   private final boolean queryTuningAddVersion;
/*     */   private int counter;
/*  33 */   private Map<String, StatisticsQuery> queryStatsMap = new LinkedHashMap();
/*     */   
/*  35 */   private Map<String, StatisticsNodeUsage> nodeUsageMap = new LinkedHashMap();
/*     */   
/*  37 */   private final String monitor = new String();
/*     */   
/*     */   public Statistics(ObjectGraphOrigin origin, boolean queryTuningAddVersion) {
/*  40 */     this.origin = origin;
/*  41 */     this.queryTuningAddVersion = queryTuningAddVersion;
/*     */   }
/*     */   
/*     */   public ObjectGraphOrigin getOrigin() {
/*  45 */     return this.origin;
/*     */   }
/*     */   
/*     */   /* Error */
/*     */   public TunedQueryInfo createTunedFetch(OrmQueryDetail newFetchDetail)
/*     */   {
/*     */     // Byte code:
/*     */     //   0: aload_0
/*     */     //   1: getfield 54	com/avaje/ebeaninternal/server/autofetch/Statistics:monitor	Ljava/lang/String;
/*     */     //   4: dup
/*     */     //   5: astore_2
/*     */     //   6: monitorenter
/*     */     //   7: new 66	com/avaje/ebeaninternal/server/autofetch/TunedQueryInfo
/*     */     //   10: dup
/*     */     //   11: aload_0
/*     */     //   12: getfield 56	com/avaje/ebeaninternal/server/autofetch/Statistics:origin	Lcom/avaje/ebean/bean/ObjectGraphOrigin;
/*     */     //   15: aload_1
/*     */     //   16: aload_0
/*     */     //   17: getfield 68	com/avaje/ebeaninternal/server/autofetch/Statistics:counter	I
/*     */     //   20: invokespecial 71	com/avaje/ebeaninternal/server/autofetch/TunedQueryInfo:<init>	(Lcom/avaje/ebean/bean/ObjectGraphOrigin;Lcom/avaje/ebeaninternal/server/querydefn/OrmQueryDetail;I)V
/*     */     //   23: aload_2
/*     */     //   24: monitorexit
/*     */     //   25: areturn
/*     */     //   26: astore_3
/*     */     //   27: aload_2
/*     */     //   28: monitorexit
/*     */     //   29: aload_3
/*     */     //   30: athrow
/*     */     // Line number table:
/*     */     //   Java source line #49	-> byte code offset #0
/*     */     //   Java source line #52	-> byte code offset #7
/*     */     //   Java source line #53	-> byte code offset #26
/*     */     // Local variable table:
/*     */     //   start	length	slot	name	signature
/*     */     //   0	31	0	this	Statistics
/*     */     //   0	31	1	newFetchDetail	OrmQueryDetail
/*     */     //   5	23	2	Ljava/lang/Object;	Object
/*     */     //   26	4	3	localObject1	Object
/*     */     // Exception table:
/*     */     //   from	to	target	type
/*     */     //   7	25	26	finally
/*     */     //   26	29	26	finally
/*     */   }
/*     */   
/*     */   public MetaAutoFetchStatistic createPublicMeta()
/*     */   {
/*  58 */     synchronized (this.monitor)
/*     */     {
/*  60 */       StatisticsQuery[] sourceQueryStats = (StatisticsQuery[])this.queryStatsMap.values().toArray(new StatisticsQuery[this.queryStatsMap.size()]);
/*  61 */       List<MetaAutoFetchStatistic.QueryStats> destQueryStats = new ArrayList(sourceQueryStats.length);
/*     */       
/*     */ 
/*  64 */       for (int i = 0; i < sourceQueryStats.length; i++) {
/*  65 */         destQueryStats.add(sourceQueryStats[i].createPublicMeta());
/*     */       }
/*     */       
/*  68 */       StatisticsNodeUsage[] sourceNodeUsage = (StatisticsNodeUsage[])this.nodeUsageMap.values().toArray(new StatisticsNodeUsage[this.nodeUsageMap.size()]);
/*  69 */       List<MetaAutoFetchStatistic.NodeUsageStats> destNodeUsage = new ArrayList(sourceNodeUsage.length);
/*     */       
/*     */ 
/*  72 */       for (int i = 0; i < sourceNodeUsage.length; i++) {
/*  73 */         destNodeUsage.add(sourceNodeUsage[i].createPublicMeta());
/*     */       }
/*     */       
/*  76 */       return new MetaAutoFetchStatistic(this.origin, this.counter, destQueryStats, destNodeUsage);
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public int getCounter()
/*     */   {
/*  88 */     return this.counter;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public boolean hasUsage()
/*     */   {
/*  95 */     synchronized (this.monitor) {
/*  96 */       return !this.nodeUsageMap.isEmpty();
/*     */     }
/*     */   }
/*     */   
/*     */   public OrmQueryDetail buildTunedFetch(BeanDescriptor<?> rootDesc)
/*     */   {
/* 102 */     synchronized (this.monitor) {
/* 103 */       if (this.nodeUsageMap.isEmpty()) {
/* 104 */         return null;
/*     */       }
/*     */       
/* 107 */       PathProperties pathProps = new PathProperties();
/*     */       
/* 109 */       Iterator<StatisticsNodeUsage> it = this.nodeUsageMap.values().iterator();
/* 110 */       while (it.hasNext()) {
/* 111 */         StatisticsNodeUsage statsNode = (StatisticsNodeUsage)it.next();
/* 112 */         statsNode.buildTunedFetch(pathProps, rootDesc);
/*     */       }
/*     */       
/* 115 */       OrmQueryDetail detail = new OrmQueryDetail();
/*     */       
/* 117 */       Collection<PathProperties.Props> pathProperties = pathProps.getPathProps();
/* 118 */       for (PathProperties.Props props : pathProperties) {
/* 119 */         if (!props.isEmpty()) {
/* 120 */           detail.addFetch(props.getPath(), props.getPropertiesAsString(), null);
/*     */         }
/*     */       }
/*     */       
/* 124 */       detail.sortFetchPaths(rootDesc);
/* 125 */       return detail;
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */   public void collectQueryInfo(ObjectGraphNode node, int beansLoaded, int micros)
/*     */   {
/* 132 */     synchronized (this.monitor) {
/* 133 */       String key = node.getPath();
/* 134 */       if (key == null) {
/* 135 */         key = "";
/*     */         
/*     */ 
/*     */ 
/* 139 */         this.counter += 1;
/*     */       }
/*     */       
/* 142 */       StatisticsQuery stats = (StatisticsQuery)this.queryStatsMap.get(key);
/* 143 */       if (stats == null) {
/* 144 */         stats = new StatisticsQuery(key);
/* 145 */         this.queryStatsMap.put(key, stats);
/*     */       }
/* 147 */       stats.add(beansLoaded, micros);
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public void collectUsageInfo(NodeUsageCollector profile)
/*     */   {
/* 157 */     if (!profile.isEmpty())
/*     */     {
/*     */ 
/* 160 */       ObjectGraphNode node = profile.getNode();
/*     */       
/* 162 */       StatisticsNodeUsage nodeStats = getNodeStats(node.getPath());
/* 163 */       nodeStats.publish(profile);
/*     */     }
/*     */   }
/*     */   
/*     */   private StatisticsNodeUsage getNodeStats(String path)
/*     */   {
/* 169 */     synchronized (this.monitor) {
/* 170 */       StatisticsNodeUsage nodeStats = (StatisticsNodeUsage)this.nodeUsageMap.get(path);
/* 171 */       if (nodeStats == null) {
/* 172 */         nodeStats = new StatisticsNodeUsage(path, this.queryTuningAddVersion);
/* 173 */         this.nodeUsageMap.put(path, nodeStats);
/*     */       }
/* 175 */       return nodeStats;
/*     */     }
/*     */   }
/*     */   
/*     */   public String getUsageDebug() {
/* 180 */     synchronized (this.monitor) {
/* 181 */       StringBuilder sb = new StringBuilder();
/* 182 */       sb.append("root[").append(this.origin.getBeanType()).append("] ");
/* 183 */       for (StatisticsNodeUsage node : this.nodeUsageMap.values()) {
/* 184 */         sb.append(node.toString()).append("\n");
/*     */       }
/* 186 */       return sb.toString();
/*     */     }
/*     */   }
/*     */   
/*     */   public String getQueryStatDebug() {
/* 191 */     synchronized (this.monitor) {
/* 192 */       StringBuilder sb = new StringBuilder();
/* 193 */       for (StatisticsQuery queryStat : this.queryStatsMap.values()) {
/* 194 */         sb.append(queryStat.toString()).append("\n");
/*     */       }
/* 196 */       return sb.toString();
/*     */     }
/*     */   }
/*     */   
/*     */   /* Error */
/*     */   public String toString()
/*     */   {
/*     */     // Byte code:
/*     */     //   0: aload_0
/*     */     //   1: getfield 54	com/avaje/ebeaninternal/server/autofetch/Statistics:monitor	Ljava/lang/String;
/*     */     //   4: dup
/*     */     //   5: astore_1
/*     */     //   6: monitorenter
/*     */     //   7: aload_0
/*     */     //   8: invokevirtual 272	com/avaje/ebeaninternal/server/autofetch/Statistics:getUsageDebug	()Ljava/lang/String;
/*     */     //   11: aload_1
/*     */     //   12: monitorexit
/*     */     //   13: areturn
/*     */     //   14: astore_2
/*     */     //   15: aload_1
/*     */     //   16: monitorexit
/*     */     //   17: aload_2
/*     */     //   18: athrow
/*     */     // Line number table:
/*     */     //   Java source line #202	-> byte code offset #0
/*     */     //   Java source line #203	-> byte code offset #7
/*     */     //   Java source line #204	-> byte code offset #14
/*     */     // Local variable table:
/*     */     //   start	length	slot	name	signature
/*     */     //   0	19	0	this	Statistics
/*     */     //   5	11	1	Ljava/lang/Object;	Object
/*     */     //   14	4	2	localObject1	Object
/*     */     // Exception table:
/*     */     //   from	to	target	type
/*     */     //   7	13	14	finally
/*     */     //   14	17	14	finally
/*     */   }
/*     */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\com\avaje\ebeaninternal\server\autofetch\Statistics.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */
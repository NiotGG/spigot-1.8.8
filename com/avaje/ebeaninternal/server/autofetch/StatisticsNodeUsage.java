/*     */ package com.avaje.ebeaninternal.server.autofetch;
/*     */ 
/*     */ import com.avaje.ebean.bean.NodeUsageCollector;
/*     */ import com.avaje.ebean.meta.MetaAutoFetchStatistic.NodeUsageStats;
/*     */ import com.avaje.ebean.text.PathProperties;
/*     */ import com.avaje.ebeaninternal.server.deploy.BeanDescriptor;
/*     */ import com.avaje.ebeaninternal.server.deploy.BeanProperty;
/*     */ import com.avaje.ebeaninternal.server.deploy.BeanPropertyAssoc;
/*     */ import com.avaje.ebeaninternal.server.el.ElPropertyValue;
/*     */ import com.avaje.ebeaninternal.server.query.SplitName;
/*     */ import java.io.Serializable;
/*     */ import java.util.HashSet;
/*     */ import java.util.LinkedHashSet;
/*     */ import java.util.Set;
/*     */ import java.util.logging.Logger;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class StatisticsNodeUsage
/*     */   implements Serializable
/*     */ {
/*     */   private static final long serialVersionUID = -1663951463963779547L;
/*  25 */   private static final Logger logger = Logger.getLogger(StatisticsNodeUsage.class.getName());
/*     */   
/*  27 */   private final String monitor = new String();
/*     */   
/*     */   private final String path;
/*     */   
/*     */   private final boolean queryTuningAddVersion;
/*     */   
/*     */   private int profileCount;
/*     */   
/*     */   private int profileUsedCount;
/*     */   
/*     */   private boolean modified;
/*     */   
/*  39 */   private Set<String> aggregateUsed = new LinkedHashSet();
/*     */   
/*     */   public StatisticsNodeUsage(String path, boolean queryTuningAddVersion) {
/*  42 */     this.path = path;
/*  43 */     this.queryTuningAddVersion = queryTuningAddVersion;
/*     */   }
/*     */   
/*     */   public MetaAutoFetchStatistic.NodeUsageStats createPublicMeta() {
/*  47 */     synchronized (this.monitor) {
/*  48 */       String[] usedProps = (String[])this.aggregateUsed.toArray(new String[this.aggregateUsed.size()]);
/*  49 */       return new MetaAutoFetchStatistic.NodeUsageStats(this.path, this.profileCount, this.profileUsedCount, usedProps);
/*     */     }
/*     */   }
/*     */   
/*     */   public void buildTunedFetch(PathProperties pathProps, BeanDescriptor<?> rootDesc)
/*     */   {
/*  55 */     synchronized (this.monitor)
/*     */     {
/*  57 */       BeanDescriptor<?> desc = rootDesc;
/*  58 */       if (this.path != null) {
/*  59 */         ElPropertyValue elGetValue = rootDesc.getElGetValue(this.path);
/*  60 */         if (elGetValue == null) {
/*  61 */           desc = null;
/*  62 */           logger.warning("Autofetch: Can't find join for path[" + this.path + "] for " + rootDesc.getName());
/*     */         }
/*     */         else {
/*  65 */           BeanProperty beanProperty = elGetValue.getBeanProperty();
/*  66 */           if ((beanProperty instanceof BeanPropertyAssoc)) {
/*  67 */             desc = ((BeanPropertyAssoc)beanProperty).getTargetDescriptor();
/*     */           }
/*     */         }
/*     */       }
/*     */       
/*  72 */       for (String propName : this.aggregateUsed) {
/*  73 */         BeanProperty beanProp = desc.getBeanPropertyFromPath(propName);
/*  74 */         if (beanProp == null) {
/*  75 */           logger.warning("Autofetch: Can't find property[" + propName + "] for " + desc.getName());
/*     */ 
/*     */         }
/*  78 */         else if ((beanProp instanceof BeanPropertyAssoc)) {
/*  79 */           BeanPropertyAssoc<?> assocProp = (BeanPropertyAssoc)beanProp;
/*  80 */           String targetIdProp = assocProp.getTargetIdProperty();
/*  81 */           String manyPath = SplitName.add(this.path, assocProp.getName());
/*  82 */           pathProps.addToPath(manyPath, targetIdProp);
/*     */         }
/*  84 */         else if ((!beanProp.isLob()) || (beanProp.isFetchEager()))
/*     */         {
/*     */ 
/*     */ 
/*  88 */           pathProps.addToPath(this.path, beanProp.getName());
/*     */         }
/*     */       }
/*     */       
/*     */ 
/*     */ 
/*  94 */       if (((this.modified) || (this.queryTuningAddVersion)) && (desc != null)) {
/*  95 */         BeanProperty[] versionProps = desc.propertiesVersion();
/*  96 */         if (versionProps.length > 0) {
/*  97 */           pathProps.addToPath(this.path, versionProps[0].getName());
/*     */         }
/*     */       }
/*     */     }
/*     */   }
/*     */   
/*     */   public void publish(NodeUsageCollector profile)
/*     */   {
/* 105 */     synchronized (this.monitor)
/*     */     {
/* 107 */       HashSet<String> used = profile.getUsed();
/*     */       
/* 109 */       this.profileCount += 1;
/* 110 */       if (!used.isEmpty()) {
/* 111 */         this.profileUsedCount += 1;
/* 112 */         this.aggregateUsed.addAll(used);
/*     */       }
/* 114 */       if (profile.isModified()) {
/* 115 */         this.modified = true;
/*     */       }
/*     */     }
/*     */   }
/*     */   
/*     */   public String toString() {
/* 121 */     return "path[" + this.path + "] profileCount[" + this.profileCount + "] used[" + this.profileUsedCount + "] props" + this.aggregateUsed;
/*     */   }
/*     */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\com\avaje\ebeaninternal\server\autofetch\StatisticsNodeUsage.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */
/*    */ package com.avaje.ebeaninternal.server.bean;
/*    */ 
/*    */ import com.avaje.ebean.OrderBy;
/*    */ import com.avaje.ebean.Query;
/*    */ import com.avaje.ebean.bean.BeanCollection;
/*    */ import com.avaje.ebean.common.BeanList;
/*    */ import com.avaje.ebean.event.BeanFinder;
/*    */ import com.avaje.ebean.event.BeanQueryRequest;
/*    */ import com.avaje.ebean.meta.MetaAutoFetchStatistic;
/*    */ import com.avaje.ebeaninternal.api.SpiEbeanServer;
/*    */ import com.avaje.ebeaninternal.api.SpiQuery;
/*    */ import com.avaje.ebeaninternal.api.SpiQuery.Type;
/*    */ import com.avaje.ebeaninternal.server.autofetch.AutoFetchManager;
/*    */ import com.avaje.ebeaninternal.server.autofetch.Statistics;
/*    */ import java.util.Iterator;
/*    */ import javax.persistence.PersistenceException;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class BFAutoFetchStatisticFinder
/*    */   implements BeanFinder<MetaAutoFetchStatistic>
/*    */ {
/*    */   public MetaAutoFetchStatistic find(BeanQueryRequest<MetaAutoFetchStatistic> request)
/*    */   {
/* 28 */     SpiQuery<MetaAutoFetchStatistic> query = (SpiQuery)request.getQuery();
/*    */     try {
/* 30 */       String queryPointKey = (String)query.getId();
/*    */       
/* 32 */       SpiEbeanServer server = (SpiEbeanServer)request.getEbeanServer();
/* 33 */       AutoFetchManager manager = server.getAutoFetchManager();
/*    */       
/* 35 */       Statistics stats = manager.getStatistics(queryPointKey);
/* 36 */       if (stats != null) {
/* 37 */         return stats.createPublicMeta();
/*    */       }
/* 39 */       return null;
/*    */     }
/*    */     catch (Exception e)
/*    */     {
/* 43 */       throw new PersistenceException(e);
/*    */     }
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */   public BeanCollection<MetaAutoFetchStatistic> findMany(BeanQueryRequest<MetaAutoFetchStatistic> request)
/*    */   {
/* 52 */     SpiQuery.Type queryType = ((SpiQuery)request.getQuery()).getType();
/* 53 */     if (!queryType.equals(SpiQuery.Type.LIST)) {
/* 54 */       throw new PersistenceException("Only findList() supported at this stage.");
/*    */     }
/*    */     
/* 57 */     SpiEbeanServer server = (SpiEbeanServer)request.getEbeanServer();
/* 58 */     AutoFetchManager manager = server.getAutoFetchManager();
/*    */     
/* 60 */     BeanList<MetaAutoFetchStatistic> list = new BeanList();
/*    */     
/* 62 */     Iterator<Statistics> it = manager.iterateStatistics();
/* 63 */     while (it.hasNext()) {
/* 64 */       Statistics stats = (Statistics)it.next();
/*    */       
/* 66 */       list.add(stats.createPublicMeta());
/*    */     }
/*    */     
/* 69 */     String orderBy = request.getQuery().order().toStringFormat();
/* 70 */     if (orderBy == null) {
/* 71 */       orderBy = "beanType";
/*    */     }
/* 73 */     server.sort(list, orderBy);
/*    */     
/*    */ 
/* 76 */     return list;
/*    */   }
/*    */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\com\avaje\ebeaninternal\server\bean\BFAutoFetchStatisticFinder.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */
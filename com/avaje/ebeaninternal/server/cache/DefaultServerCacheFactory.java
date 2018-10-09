/*    */ package com.avaje.ebeaninternal.server.cache;
/*    */ 
/*    */ import com.avaje.ebean.EbeanServer;
/*    */ import com.avaje.ebean.cache.ServerCache;
/*    */ import com.avaje.ebean.cache.ServerCacheFactory;
/*    */ import com.avaje.ebean.cache.ServerCacheOptions;
/*    */ 
/*    */ 
/*    */ 
/*    */ public class DefaultServerCacheFactory
/*    */   implements ServerCacheFactory
/*    */ {
/*    */   private EbeanServer ebeanServer;
/*    */   
/*    */   public void init(EbeanServer ebeanServer)
/*    */   {
/* 17 */     this.ebeanServer = ebeanServer;
/*    */   }
/*    */   
/*    */   public ServerCache createCache(String cacheKey, ServerCacheOptions cacheOptions)
/*    */   {
/* 22 */     ServerCache cache = new DefaultServerCache(cacheKey, cacheOptions);
/* 23 */     cache.init(this.ebeanServer);
/* 24 */     return cache;
/*    */   }
/*    */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\com\avaje\ebeaninternal\server\cache\DefaultServerCacheFactory.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */
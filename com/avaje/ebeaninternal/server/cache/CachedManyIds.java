/*    */ package com.avaje.ebeaninternal.server.cache;
/*    */ 
/*    */ import java.util.List;
/*    */ 
/*    */ public class CachedManyIds
/*    */ {
/*    */   private final List<Object> idList;
/*    */   
/*    */   public CachedManyIds(List<Object> idList) {
/* 10 */     this.idList = idList;
/*    */   }
/*    */   
/*    */   public List<Object> getIdList() {
/* 14 */     return this.idList;
/*    */   }
/*    */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\com\avaje\ebeaninternal\server\cache\CachedManyIds.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */
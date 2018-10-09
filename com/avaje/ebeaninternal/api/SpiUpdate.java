/*    */ package com.avaje.ebeaninternal.api;
/*    */ 
/*    */ import com.avaje.ebean.Update;
/*    */ 
/*    */ public abstract interface SpiUpdate<T> extends Update<T> {
/*    */   public abstract Class<?> getBeanType();
/*    */   
/*    */   public abstract OrmUpdateType getOrmUpdateType();
/*    */   
/*    */   public abstract String getBaseTable();
/*    */   
/*    */   public abstract String getUpdateStatement();
/*    */   
/* 14 */   public static enum OrmUpdateType { INSERT, 
/*    */     
/*    */ 
/*    */ 
/*    */ 
/* 19 */     UPDATE, 
/*    */     
/*    */ 
/*    */ 
/*    */ 
/* 24 */     DELETE, 
/*    */     
/*    */ 
/*    */ 
/*    */ 
/* 29 */     UNKNOWN;
/*    */     
/*    */     private OrmUpdateType() {}
/*    */   }
/*    */   
/*    */   public abstract int getTimeout();
/*    */   
/*    */   public abstract boolean isNotifyCache();
/*    */   
/*    */   public abstract BindParams getBindParams();
/*    */   
/*    */   public abstract void setGeneratedSql(String paramString);
/*    */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\com\avaje\ebeaninternal\api\SpiUpdate.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */
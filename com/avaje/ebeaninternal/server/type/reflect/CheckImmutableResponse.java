/*    */ package com.avaje.ebeaninternal.server.type.reflect;
/*    */ 
/*    */ 
/*    */ 
/*    */ public class CheckImmutableResponse
/*    */ {
/*  7 */   private boolean immutable = true;
/*    */   
/*    */   private String reasonNotImmutable;
/*    */   private boolean compoundType;
/*    */   
/*    */   public String toString()
/*    */   {
/* 14 */     if (this.immutable) {
/* 15 */       return "immutable";
/*    */     }
/* 17 */     return "not immutable due to:" + this.reasonNotImmutable;
/*    */   }
/*    */   
/*    */   public boolean isCompoundType()
/*    */   {
/* 22 */     return this.compoundType;
/*    */   }
/*    */   
/*    */   public void setCompoundType(boolean compoundType) {
/* 26 */     this.compoundType = compoundType;
/*    */   }
/*    */   
/*    */   public String getReasonNotImmutable() {
/* 30 */     return this.reasonNotImmutable;
/*    */   }
/*    */   
/*    */   public void setReasonNotImmutable(String error) {
/* 34 */     this.immutable = false;
/* 35 */     this.reasonNotImmutable = error;
/*    */   }
/*    */   
/*    */   public boolean isImmutable() {
/* 39 */     return this.immutable;
/*    */   }
/*    */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\com\avaje\ebeaninternal\server\type\reflect\CheckImmutableResponse.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */
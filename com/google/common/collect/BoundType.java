/*    */ package com.google.common.collect;
/*    */ 
/*    */ import com.google.common.annotations.GwtCompatible;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ @GwtCompatible
/*    */ public enum BoundType
/*    */ {
/* 31 */   OPEN, 
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/* 40 */   CLOSED;
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */   private BoundType() {}
/*    */   
/*    */ 
/*    */ 
/*    */   static BoundType forBoolean(boolean inclusive)
/*    */   {
/* 51 */     return inclusive ? CLOSED : OPEN;
/*    */   }
/*    */   
/*    */   abstract BoundType flip();
/*    */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\com\google\common\collect\BoundType.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */
/*    */ package com.avaje.ebeaninternal.server.util;
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
/*    */ 
/*    */ public class InternalAssert
/*    */ {
/*    */   public static void notNull(Object o, String msg)
/*    */   {
/* 31 */     if (o == null) {
/* 32 */       throw new IllegalStateException(msg);
/*    */     }
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */   public static void isTrue(boolean b, String msg)
/*    */   {
/* 40 */     if (!b) {
/* 41 */       throw new IllegalStateException(msg);
/*    */     }
/*    */   }
/*    */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\com\avaje\ebeaninternal\server\util\InternalAssert.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */
/*    */ package com.avaje.ebeaninternal.server.persist;
/*    */ 
/*    */ import com.avaje.ebeaninternal.server.deploy.parse.DetectScala;
/*    */ import scala.Option;
/*    */ 
/*    */ 
/*    */ 
/*    */ public class DmlUtil
/*    */ {
/* 10 */   private static final boolean hasScalaSupport = ;
/*    */   
/*    */ 
/*    */ 
/*    */   public static boolean isNullOrZero(Object value)
/*    */   {
/* 16 */     if (value == null) {
/* 17 */       return true;
/*    */     }
/*    */     
/* 20 */     if ((value instanceof Number)) {
/* 21 */       return ((Number)value).longValue() == 0L;
/*    */     }
/*    */     
/* 24 */     if ((hasScalaSupport) && 
/* 25 */       ((value instanceof Option)) && 
/* 26 */       (((Option)value).isEmpty())) {
/* 27 */       return true;
/*    */     }
/*    */     
/*    */ 
/*    */ 
/* 32 */     return false;
/*    */   }
/*    */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\com\avaje\ebeaninternal\server\persist\DmlUtil.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */
/*    */ package com.avaje.ebeaninternal.server.subclass;
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
/*    */ 
/*    */ public class SubClassUtil
/*    */   implements GenSuffix
/*    */ {
/*    */   public static boolean isSubClass(String className)
/*    */   {
/* 33 */     return className.lastIndexOf("$$EntityBean") != -1;
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */   public static String getSuperClassName(String className)
/*    */   {
/* 40 */     int dPos = className.lastIndexOf("$$EntityBean");
/* 41 */     if (dPos > -1) {
/* 42 */       return className.substring(0, dPos);
/*    */     }
/* 44 */     return className;
/*    */   }
/*    */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\com\avaje\ebeaninternal\server\subclass\SubClassUtil.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */
/*    */ package com.avaje.ebeaninternal.server.core;
/*    */ 
/*    */ import com.avaje.ebeaninternal.server.util.ClassPathSearchMatcher;
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
/*    */ public class OnBootupClassSearchMatcher
/*    */   implements ClassPathSearchMatcher
/*    */ {
/* 30 */   BootupClasses classes = new BootupClasses();
/*    */   
/*    */   public boolean isMatch(Class<?> cls)
/*    */   {
/* 34 */     return this.classes.isMatch(cls);
/*    */   }
/*    */   
/*    */   public BootupClasses getOnBootupClasses() {
/* 38 */     return this.classes;
/*    */   }
/*    */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\com\avaje\ebeaninternal\server\core\OnBootupClassSearchMatcher.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */
/*    */ package org.fusesource.hawtjni.runtime;
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
/*    */ public class PointerMath
/*    */ {
/* 17 */   private static final boolean bits32 = Library.getBitModel() == 32;
/*    */   
/*    */   public static final long add(long ptr, long n) {
/* 20 */     if (bits32) {
/* 21 */       return (int)(ptr + n);
/*    */     }
/* 23 */     return ptr + n;
/*    */   }
/*    */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\org\fusesource\hawtjni\runtime\PointerMath.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */
/*    */ package org.bukkit.craftbukkit.libs.jline.internal;
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
/*    */ public class Preconditions
/*    */ {
/*    */   public static <T> T checkNotNull(T reference)
/*    */   {
/* 22 */     if (reference == null) {
/* 23 */       throw new NullPointerException();
/*    */     }
/* 25 */     return reference;
/*    */   }
/*    */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\org\bukkit\craftbukkit\libs\jline\internal\Preconditions.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */
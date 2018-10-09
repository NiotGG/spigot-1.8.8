/*    */ package org.bukkit.craftbukkit.libs.jline.console;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class UserInterruptException
/*    */   extends RuntimeException
/*    */ {
/*    */   private final String partialLine;
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   public UserInterruptException(String partialLine)
/*    */   {
/* 24 */     this.partialLine = partialLine;
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */   public String getPartialLine()
/*    */   {
/* 32 */     return this.partialLine;
/*    */   }
/*    */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\org\bukkit\craftbukkit\libs\jline\console\UserInterruptException.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */
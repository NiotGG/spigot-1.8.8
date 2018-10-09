/*    */ package org.bukkit.craftbukkit.libs.joptsimple;
/*    */ 
/*    */ import java.util.Collection;
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
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ class OptionMissingRequiredArgumentException
/*    */   extends OptionException
/*    */ {
/*    */   private static final long serialVersionUID = -1L;
/*    */   
/*    */   OptionMissingRequiredArgumentException(Collection<String> options)
/*    */   {
/* 41 */     super(options);
/*    */   }
/*    */   
/*    */   public String getMessage()
/*    */   {
/* 46 */     return "Option " + multipleOptionMessage() + " requires an argument";
/*    */   }
/*    */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\org\bukkit\craftbukkit\libs\joptsimple\OptionMissingRequiredArgumentException.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */
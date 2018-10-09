/*    */ package org.bukkit.craftbukkit.libs.joptsimple;
/*    */ 
/*    */ import java.util.Collections;
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
/*    */ class IllegalOptionClusterException
/*    */   extends OptionException
/*    */ {
/*    */   private static final long serialVersionUID = -1L;
/*    */   
/*    */   IllegalOptionClusterException(String option)
/*    */   {
/* 41 */     super(Collections.singletonList(option));
/*    */   }
/*    */   
/*    */   public String getMessage()
/*    */   {
/* 46 */     return "Option cluster containing " + singleOptionMessage() + " is illegal";
/*    */   }
/*    */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\org\bukkit\craftbukkit\libs\joptsimple\IllegalOptionClusterException.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */
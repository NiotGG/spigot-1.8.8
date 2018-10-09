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
/*    */ class MultipleArgumentsForOptionException
/*    */   extends OptionException
/*    */ {
/*    */   private static final long serialVersionUID = -1L;
/*    */   
/*    */   MultipleArgumentsForOptionException(Collection<String> options)
/*    */   {
/* 41 */     super(options);
/*    */   }
/*    */   
/*    */   public String getMessage()
/*    */   {
/* 46 */     return "Found multiple arguments for option " + multipleOptionMessage() + ", but you asked for only one";
/*    */   }
/*    */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\org\bukkit\craftbukkit\libs\joptsimple\MultipleArgumentsForOptionException.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */
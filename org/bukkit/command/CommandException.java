/*    */ package org.bukkit.command;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class CommandException
/*    */   extends RuntimeException
/*    */ {
/*    */   public CommandException() {}
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   public CommandException(String msg)
/*    */   {
/* 22 */     super(msg);
/*    */   }
/*    */   
/*    */   public CommandException(String msg, Throwable cause) {
/* 26 */     super(msg, cause);
/*    */   }
/*    */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\org\bukkit\command\CommandException.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */
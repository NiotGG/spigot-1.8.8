/*    */ package org.bukkit.event.server;
/*    */ 
/*    */ import org.bukkit.command.CommandSender;
/*    */ import org.bukkit.event.Cancellable;
/*    */ import org.bukkit.event.HandlerList;
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
/*    */ 
/*    */ 
/*    */ public class ServerCommandEvent
/*    */   extends ServerEvent
/*    */   implements Cancellable
/*    */ {
/* 42 */   private static final HandlerList handlers = new HandlerList();
/*    */   private String command;
/*    */   private final CommandSender sender;
/* 45 */   private boolean cancel = false;
/*    */   
/*    */   public ServerCommandEvent(CommandSender sender, String command) {
/* 48 */     this.command = command;
/* 49 */     this.sender = sender;
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   public String getCommand()
/*    */   {
/* 59 */     return this.command;
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   public void setCommand(String message)
/*    */   {
/* 68 */     this.command = message;
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   public CommandSender getSender()
/*    */   {
/* 77 */     return this.sender;
/*    */   }
/*    */   
/*    */   public HandlerList getHandlers()
/*    */   {
/* 82 */     return handlers;
/*    */   }
/*    */   
/*    */   public static HandlerList getHandlerList() {
/* 86 */     return handlers;
/*    */   }
/*    */   
/*    */   public boolean isCancelled()
/*    */   {
/* 91 */     return this.cancel;
/*    */   }
/*    */   
/*    */   public void setCancelled(boolean cancel)
/*    */   {
/* 96 */     this.cancel = cancel;
/*    */   }
/*    */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\org\bukkit\event\server\ServerCommandEvent.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */
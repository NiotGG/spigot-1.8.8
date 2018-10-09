/*    */ package org.bukkit.plugin.messaging;
/*    */ 
/*    */ 
/*    */ public class ChannelNotRegisteredException
/*    */   extends RuntimeException
/*    */ {
/*    */   public ChannelNotRegisteredException()
/*    */   {
/*  9 */     this("Attempted to send a plugin message through an unregistered channel.");
/*    */   }
/*    */   
/*    */   public ChannelNotRegisteredException(String channel) {
/* 13 */     super("Attempted to send a plugin message through the unregistered channel `" + channel + "'.");
/*    */   }
/*    */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\org\bukkit\plugin\messaging\ChannelNotRegisteredException.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */
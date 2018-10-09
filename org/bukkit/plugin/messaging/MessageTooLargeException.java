/*    */ package org.bukkit.plugin.messaging;
/*    */ 
/*    */ 
/*    */ public class MessageTooLargeException
/*    */   extends RuntimeException
/*    */ {
/*    */   public MessageTooLargeException()
/*    */   {
/*  9 */     this("Attempted to send a plugin message that was too large. The maximum length a plugin message may be is 32766 bytes.");
/*    */   }
/*    */   
/*    */   public MessageTooLargeException(byte[] message) {
/* 13 */     this(message.length);
/*    */   }
/*    */   
/*    */   public MessageTooLargeException(int length) {
/* 17 */     this("Attempted to send a plugin message that was too large. The maximum length a plugin message may be is 32766 bytes (tried to send one that is " + length + " bytes long).");
/*    */   }
/*    */   
/*    */   public MessageTooLargeException(String msg) {
/* 21 */     super(msg);
/*    */   }
/*    */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\org\bukkit\plugin\messaging\MessageTooLargeException.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */
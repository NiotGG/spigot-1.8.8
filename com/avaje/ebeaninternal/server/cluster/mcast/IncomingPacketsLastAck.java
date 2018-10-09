/*    */ package com.avaje.ebeaninternal.server.cluster.mcast;
/*    */ 
/*    */ import java.util.HashMap;
/*    */ import java.util.List;
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
/*    */ public class IncomingPacketsLastAck
/*    */ {
/* 38 */   private HashMap<String, MessageAck> lastAckMap = new HashMap();
/*    */   
/*    */   public String toString() {
/* 41 */     return this.lastAckMap.values().toString();
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */   public void remove(String memberHostPort)
/*    */   {
/* 48 */     this.lastAckMap.remove(memberHostPort);
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */   public MessageAck getLastAck(String memberHostPort)
/*    */   {
/* 55 */     return (MessageAck)this.lastAckMap.get(memberHostPort);
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */   public void updateLastAck(AckResendMessages ackResendMessages)
/*    */   {
/* 63 */     List<Message> messages = ackResendMessages.getMessages();
/* 64 */     for (int i = 0; i < messages.size(); i++) {
/* 65 */       Message msg = (Message)messages.get(i);
/* 66 */       if ((msg instanceof MessageAck)) {
/* 67 */         MessageAck lastAck = (MessageAck)msg;
/* 68 */         this.lastAckMap.put(lastAck.getToHostPort(), lastAck);
/*    */       }
/*    */     }
/*    */   }
/*    */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\com\avaje\ebeaninternal\server\cluster\mcast\IncomingPacketsLastAck.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */
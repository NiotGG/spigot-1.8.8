/*    */ package com.avaje.ebeaninternal.server.cluster.mcast;
/*    */ 
/*    */ import java.util.ArrayList;
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
/*    */ public class AckResendMessages
/*    */ {
/* 32 */   ArrayList<Message> messages = new ArrayList();
/*    */   
/*    */   public String toString() {
/* 35 */     return this.messages.toString();
/*    */   }
/*    */   
/*    */   public int size() {
/* 39 */     return this.messages.size();
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */   public void add(MessageAck ack)
/*    */   {
/* 46 */     this.messages.add(ack);
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */   public void add(MessageResend resend)
/*    */   {
/* 53 */     this.messages.add(resend);
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */   public List<Message> getMessages()
/*    */   {
/* 60 */     return this.messages;
/*    */   }
/*    */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\com\avaje\ebeaninternal\server\cluster\mcast\AckResendMessages.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */
/*    */ package io.netty.channel.sctp;
/*    */ 
/*    */ import com.sun.nio.sctp.AbstractNotificationHandler;
/*    */ import com.sun.nio.sctp.AssociationChangeNotification;
/*    */ import com.sun.nio.sctp.HandlerResult;
/*    */ import com.sun.nio.sctp.Notification;
/*    */ import com.sun.nio.sctp.PeerAddressChangeNotification;
/*    */ import com.sun.nio.sctp.SendFailedNotification;
/*    */ import com.sun.nio.sctp.ShutdownNotification;
/*    */ import io.netty.channel.ChannelPipeline;
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
/*    */ public final class SctpNotificationHandler
/*    */   extends AbstractNotificationHandler<Object>
/*    */ {
/*    */   private final SctpChannel sctpChannel;
/*    */   
/*    */   public SctpNotificationHandler(SctpChannel paramSctpChannel)
/*    */   {
/* 38 */     if (paramSctpChannel == null) {
/* 39 */       throw new NullPointerException("sctpChannel");
/*    */     }
/* 41 */     this.sctpChannel = paramSctpChannel;
/*    */   }
/*    */   
/*    */   public HandlerResult handleNotification(AssociationChangeNotification paramAssociationChangeNotification, Object paramObject)
/*    */   {
/* 46 */     fireEvent(paramAssociationChangeNotification);
/* 47 */     return HandlerResult.CONTINUE;
/*    */   }
/*    */   
/*    */   public HandlerResult handleNotification(PeerAddressChangeNotification paramPeerAddressChangeNotification, Object paramObject)
/*    */   {
/* 52 */     fireEvent(paramPeerAddressChangeNotification);
/* 53 */     return HandlerResult.CONTINUE;
/*    */   }
/*    */   
/*    */   public HandlerResult handleNotification(SendFailedNotification paramSendFailedNotification, Object paramObject)
/*    */   {
/* 58 */     fireEvent(paramSendFailedNotification);
/* 59 */     return HandlerResult.CONTINUE;
/*    */   }
/*    */   
/*    */   public HandlerResult handleNotification(ShutdownNotification paramShutdownNotification, Object paramObject)
/*    */   {
/* 64 */     fireEvent(paramShutdownNotification);
/* 65 */     this.sctpChannel.close();
/* 66 */     return HandlerResult.RETURN;
/*    */   }
/*    */   
/*    */   private void fireEvent(Notification paramNotification) {
/* 70 */     this.sctpChannel.pipeline().fireUserEventTriggered(paramNotification);
/*    */   }
/*    */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\io\netty\channel\sctp\SctpNotificationHandler.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */
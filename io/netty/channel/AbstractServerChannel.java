/*    */ package io.netty.channel;
/*    */ 
/*    */ import java.net.SocketAddress;
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
/*    */ public abstract class AbstractServerChannel
/*    */   extends AbstractChannel
/*    */   implements ServerChannel
/*    */ {
/* 35 */   private static final ChannelMetadata METADATA = new ChannelMetadata(false);
/*    */   
/*    */ 
/*    */ 
/*    */   protected AbstractServerChannel()
/*    */   {
/* 41 */     super(null);
/*    */   }
/*    */   
/*    */   public ChannelMetadata metadata()
/*    */   {
/* 46 */     return METADATA;
/*    */   }
/*    */   
/*    */   public SocketAddress remoteAddress()
/*    */   {
/* 51 */     return null;
/*    */   }
/*    */   
/*    */   protected SocketAddress remoteAddress0()
/*    */   {
/* 56 */     return null;
/*    */   }
/*    */   
/*    */   protected void doDisconnect() throws Exception
/*    */   {
/* 61 */     throw new UnsupportedOperationException();
/*    */   }
/*    */   
/*    */   protected AbstractChannel.AbstractUnsafe newUnsafe()
/*    */   {
/* 66 */     return new DefaultServerUnsafe(null);
/*    */   }
/*    */   
/*    */   protected void doWrite(ChannelOutboundBuffer paramChannelOutboundBuffer) throws Exception
/*    */   {
/* 71 */     throw new UnsupportedOperationException();
/*    */   }
/*    */   
/*    */ 
/*    */ 
/* 76 */   protected final Object filterOutboundMessage(Object paramObject) { throw new UnsupportedOperationException(); }
/*    */   
/*    */   private final class DefaultServerUnsafe extends AbstractChannel.AbstractUnsafe {
/* 79 */     private DefaultServerUnsafe() { super(); }
/*    */     
/*    */     public void connect(SocketAddress paramSocketAddress1, SocketAddress paramSocketAddress2, ChannelPromise paramChannelPromise) {
/* 82 */       safeSetFailure(paramChannelPromise, new UnsupportedOperationException());
/*    */     }
/*    */   }
/*    */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\io\netty\channel\AbstractServerChannel.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */
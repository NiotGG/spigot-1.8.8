/*    */ package io.netty.channel.local;
/*    */ 
/*    */ import io.netty.channel.Channel;
/*    */ import io.netty.channel.ChannelException;
/*    */ import io.netty.util.internal.PlatformDependent;
/*    */ import io.netty.util.internal.StringUtil;
/*    */ import java.net.SocketAddress;
/*    */ import java.util.concurrent.ConcurrentMap;
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
/*    */ final class LocalChannelRegistry
/*    */ {
/* 28 */   private static final ConcurrentMap<LocalAddress, Channel> boundChannels = ;
/*    */   
/*    */   static LocalAddress register(Channel paramChannel, LocalAddress paramLocalAddress, SocketAddress paramSocketAddress)
/*    */   {
/* 32 */     if (paramLocalAddress != null) {
/* 33 */       throw new ChannelException("already bound");
/*    */     }
/* 35 */     if (!(paramSocketAddress instanceof LocalAddress)) {
/* 36 */       throw new ChannelException("unsupported address type: " + StringUtil.simpleClassName(paramSocketAddress));
/*    */     }
/*    */     
/* 39 */     LocalAddress localLocalAddress = (LocalAddress)paramSocketAddress;
/* 40 */     if (LocalAddress.ANY.equals(localLocalAddress)) {
/* 41 */       localLocalAddress = new LocalAddress(paramChannel);
/*    */     }
/*    */     
/* 44 */     Channel localChannel = (Channel)boundChannels.putIfAbsent(localLocalAddress, paramChannel);
/* 45 */     if (localChannel != null) {
/* 46 */       throw new ChannelException("address already in use by: " + localChannel);
/*    */     }
/* 48 */     return localLocalAddress;
/*    */   }
/*    */   
/*    */   static Channel get(SocketAddress paramSocketAddress) {
/* 52 */     return (Channel)boundChannels.get(paramSocketAddress);
/*    */   }
/*    */   
/*    */   static void unregister(LocalAddress paramLocalAddress) {
/* 56 */     boundChannels.remove(paramLocalAddress);
/*    */   }
/*    */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\io\netty\channel\local\LocalChannelRegistry.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */
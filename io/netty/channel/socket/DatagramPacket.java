/*    */ package io.netty.channel.socket;
/*    */ 
/*    */ import io.netty.buffer.ByteBuf;
/*    */ import io.netty.buffer.ByteBufHolder;
/*    */ import io.netty.channel.DefaultAddressedEnvelope;
/*    */ import java.net.InetSocketAddress;
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
/*    */ public final class DatagramPacket
/*    */   extends DefaultAddressedEnvelope<ByteBuf, InetSocketAddress>
/*    */   implements ByteBufHolder
/*    */ {
/*    */   public DatagramPacket(ByteBuf paramByteBuf, InetSocketAddress paramInetSocketAddress)
/*    */   {
/* 34 */     super(paramByteBuf, paramInetSocketAddress);
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */   public DatagramPacket(ByteBuf paramByteBuf, InetSocketAddress paramInetSocketAddress1, InetSocketAddress paramInetSocketAddress2)
/*    */   {
/* 42 */     super(paramByteBuf, paramInetSocketAddress1, paramInetSocketAddress2);
/*    */   }
/*    */   
/*    */   public DatagramPacket copy()
/*    */   {
/* 47 */     return new DatagramPacket(((ByteBuf)content()).copy(), (InetSocketAddress)recipient(), (InetSocketAddress)sender());
/*    */   }
/*    */   
/*    */   public DatagramPacket duplicate()
/*    */   {
/* 52 */     return new DatagramPacket(((ByteBuf)content()).duplicate(), (InetSocketAddress)recipient(), (InetSocketAddress)sender());
/*    */   }
/*    */   
/*    */   public DatagramPacket retain()
/*    */   {
/* 57 */     super.retain();
/* 58 */     return this;
/*    */   }
/*    */   
/*    */   public DatagramPacket retain(int paramInt)
/*    */   {
/* 63 */     super.retain(paramInt);
/* 64 */     return this;
/*    */   }
/*    */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\io\netty\channel\socket\DatagramPacket.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */
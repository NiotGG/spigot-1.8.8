/*    */ package net.minecraft.server.v1_8_R3;
/*    */ 
/*    */ import io.netty.buffer.ByteBuf;
/*    */ import io.netty.channel.Channel;
/*    */ import io.netty.channel.ChannelHandlerContext;
/*    */ import io.netty.handler.codec.ByteToMessageDecoder;
/*    */ import io.netty.util.Attribute;
/*    */ import java.io.IOException;
/*    */ import java.util.List;
/*    */ import org.apache.logging.log4j.LogManager;
/*    */ import org.apache.logging.log4j.Logger;
/*    */ import org.apache.logging.log4j.Marker;
/*    */ import org.apache.logging.log4j.MarkerManager;
/*    */ 
/*    */ public class PacketDecoder extends ByteToMessageDecoder
/*    */ {
/* 17 */   private static final Logger a = ;
/* 18 */   private static final Marker b = MarkerManager.getMarker("PACKET_RECEIVED", NetworkManager.b);
/*    */   private final EnumProtocolDirection c;
/*    */   
/*    */   public PacketDecoder(EnumProtocolDirection paramEnumProtocolDirection)
/*    */   {
/* 23 */     this.c = paramEnumProtocolDirection;
/*    */   }
/*    */   
/*    */   protected void decode(ChannelHandlerContext paramChannelHandlerContext, ByteBuf paramByteBuf, List<Object> paramList) throws Exception
/*    */   {
/* 28 */     if (paramByteBuf.readableBytes() == 0) {
/* 29 */       return;
/*    */     }
/*    */     
/* 32 */     PacketDataSerializer localPacketDataSerializer = new PacketDataSerializer(paramByteBuf);
/* 33 */     int i = localPacketDataSerializer.e();
/* 34 */     Packet localPacket = ((EnumProtocol)paramChannelHandlerContext.channel().attr(NetworkManager.c).get()).a(this.c, i);
/*    */     
/* 36 */     if (localPacket == null) {
/* 37 */       throw new IOException("Bad packet id " + i);
/*    */     }
/*    */     
/* 40 */     localPacket.a(localPacketDataSerializer);
/* 41 */     if (localPacketDataSerializer.readableBytes() > 0) {
/* 42 */       throw new IOException("Packet " + ((EnumProtocol)paramChannelHandlerContext.channel().attr(NetworkManager.c).get()).a() + "/" + i + " (" + localPacket.getClass().getSimpleName() + ") was larger than I expected, found " + localPacketDataSerializer.readableBytes() + " bytes extra whilst reading packet " + i);
/*    */     }
/* 44 */     paramList.add(localPacket);
/*    */     
/*    */ 
/* 47 */     if (a.isDebugEnabled()) {
/* 48 */       a.debug(b, " IN: [{}:{}] {}", new Object[] { paramChannelHandlerContext.channel().attr(NetworkManager.c).get(), Integer.valueOf(i), localPacket.getClass().getName() });
/*    */     }
/*    */   }
/*    */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_8_R3\PacketDecoder.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */
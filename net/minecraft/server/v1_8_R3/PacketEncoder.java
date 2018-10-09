/*    */ package net.minecraft.server.v1_8_R3;
/*    */ 
/*    */ import io.netty.buffer.ByteBuf;
/*    */ import io.netty.channel.Channel;
/*    */ import io.netty.channel.ChannelHandlerContext;
/*    */ import io.netty.handler.codec.MessageToByteEncoder;
/*    */ import io.netty.util.Attribute;
/*    */ import java.io.IOException;
/*    */ import org.apache.logging.log4j.LogManager;
/*    */ import org.apache.logging.log4j.Logger;
/*    */ import org.apache.logging.log4j.Marker;
/*    */ import org.apache.logging.log4j.MarkerManager;
/*    */ 
/*    */ public class PacketEncoder
/*    */   extends MessageToByteEncoder<Packet>
/*    */ {
/* 17 */   private static final Logger a = ;
/* 18 */   private static final Marker b = MarkerManager.getMarker("PACKET_SENT", NetworkManager.b);
/*    */   private final EnumProtocolDirection c;
/*    */   
/*    */   public PacketEncoder(EnumProtocolDirection paramEnumProtocolDirection)
/*    */   {
/* 23 */     this.c = paramEnumProtocolDirection;
/*    */   }
/*    */   
/*    */   protected void a(ChannelHandlerContext paramChannelHandlerContext, Packet paramPacket, ByteBuf paramByteBuf) throws Exception
/*    */   {
/* 28 */     Integer localInteger = ((EnumProtocol)paramChannelHandlerContext.channel().attr(NetworkManager.c).get()).a(this.c, paramPacket);
/*    */     
/*    */ 
/* 31 */     if (a.isDebugEnabled()) {
/* 32 */       a.debug(b, "OUT: [{}:{}] {}", new Object[] { paramChannelHandlerContext.channel().attr(NetworkManager.c).get(), localInteger, paramPacket.getClass().getName() });
/*    */     }
/*    */     
/* 35 */     if (localInteger == null) {
/* 36 */       throw new IOException("Can't serialize unregistered packet");
/*    */     }
/*    */     
/* 39 */     PacketDataSerializer localPacketDataSerializer = new PacketDataSerializer(paramByteBuf);
/* 40 */     localPacketDataSerializer.b(localInteger.intValue());
/*    */     try
/*    */     {
/* 43 */       if ((paramPacket instanceof PacketPlayOutNamedEntitySpawn)) {
/* 44 */         paramPacket = paramPacket;
/*    */       }
/* 46 */       paramPacket.b(localPacketDataSerializer);
/*    */     } catch (Throwable localThrowable) {
/* 48 */       a.error(localThrowable);
/*    */     }
/*    */   }
/*    */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_8_R3\PacketEncoder.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */
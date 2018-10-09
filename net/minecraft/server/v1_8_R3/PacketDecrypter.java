/*    */ package net.minecraft.server.v1_8_R3;
/*    */ 
/*    */ import io.netty.buffer.ByteBuf;
/*    */ import io.netty.channel.ChannelHandlerContext;
/*    */ import java.util.List;
/*    */ import javax.crypto.Cipher;
/*    */ 
/*    */ public class PacketDecrypter extends io.netty.handler.codec.MessageToMessageDecoder<ByteBuf>
/*    */ {
/*    */   private final PacketEncryptionHandler a;
/*    */   
/*    */   public PacketDecrypter(Cipher paramCipher)
/*    */   {
/* 14 */     this.a = new PacketEncryptionHandler(paramCipher);
/*    */   }
/*    */   
/*    */   protected void a(ChannelHandlerContext paramChannelHandlerContext, ByteBuf paramByteBuf, List<Object> paramList) throws Exception
/*    */   {
/* 19 */     paramList.add(this.a.a(paramChannelHandlerContext, paramByteBuf));
/*    */   }
/*    */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_8_R3\PacketDecrypter.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */
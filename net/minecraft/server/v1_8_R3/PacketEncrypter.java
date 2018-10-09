/*    */ package net.minecraft.server.v1_8_R3;
/*    */ 
/*    */ import io.netty.buffer.ByteBuf;
/*    */ import io.netty.channel.ChannelHandlerContext;
/*    */ import javax.crypto.Cipher;
/*    */ 
/*    */ public class PacketEncrypter extends io.netty.handler.codec.MessageToByteEncoder<ByteBuf>
/*    */ {
/*    */   private final PacketEncryptionHandler a;
/*    */   
/*    */   public PacketEncrypter(Cipher paramCipher)
/*    */   {
/* 13 */     this.a = new PacketEncryptionHandler(paramCipher);
/*    */   }
/*    */   
/*    */   protected void a(ChannelHandlerContext paramChannelHandlerContext, ByteBuf paramByteBuf1, ByteBuf paramByteBuf2) throws Exception
/*    */   {
/* 18 */     this.a.a(paramByteBuf1, paramByteBuf2);
/*    */   }
/*    */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_8_R3\PacketEncrypter.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */
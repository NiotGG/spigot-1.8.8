/*    */ package net.minecraft.server.v1_8_R3;
/*    */ 
/*    */ import java.io.IOException;
/*    */ import java.security.PublicKey;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class PacketLoginOutEncryptionBegin
/*    */   implements Packet<PacketLoginOutListener>
/*    */ {
/*    */   private String a;
/*    */   private PublicKey b;
/*    */   private byte[] c;
/*    */   
/*    */   public PacketLoginOutEncryptionBegin() {}
/*    */   
/*    */   public PacketLoginOutEncryptionBegin(String paramString, PublicKey paramPublicKey, byte[] paramArrayOfByte)
/*    */   {
/* 20 */     this.a = paramString;
/* 21 */     this.b = paramPublicKey;
/* 22 */     this.c = paramArrayOfByte;
/*    */   }
/*    */   
/*    */   public void a(PacketDataSerializer paramPacketDataSerializer) throws IOException
/*    */   {
/* 27 */     this.a = paramPacketDataSerializer.c(20);
/* 28 */     this.b = MinecraftEncryption.a(paramPacketDataSerializer.a());
/* 29 */     this.c = paramPacketDataSerializer.a();
/*    */   }
/*    */   
/*    */   public void b(PacketDataSerializer paramPacketDataSerializer) throws IOException
/*    */   {
/* 34 */     paramPacketDataSerializer.a(this.a);
/* 35 */     paramPacketDataSerializer.a(this.b.getEncoded());
/* 36 */     paramPacketDataSerializer.a(this.c);
/*    */   }
/*    */   
/*    */   public void a(PacketLoginOutListener paramPacketLoginOutListener)
/*    */   {
/* 41 */     paramPacketLoginOutListener.a(this);
/*    */   }
/*    */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_8_R3\PacketLoginOutEncryptionBegin.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */
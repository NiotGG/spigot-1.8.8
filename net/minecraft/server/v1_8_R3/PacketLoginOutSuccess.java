/*    */ package net.minecraft.server.v1_8_R3;
/*    */ 
/*    */ import com.mojang.authlib.GameProfile;
/*    */ import java.io.IOException;
/*    */ import java.util.UUID;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class PacketLoginOutSuccess
/*    */   implements Packet<PacketLoginOutListener>
/*    */ {
/*    */   private GameProfile a;
/*    */   
/*    */   public PacketLoginOutSuccess() {}
/*    */   
/*    */   public PacketLoginOutSuccess(GameProfile paramGameProfile)
/*    */   {
/* 19 */     this.a = paramGameProfile;
/*    */   }
/*    */   
/*    */   public void a(PacketDataSerializer paramPacketDataSerializer) throws IOException
/*    */   {
/* 24 */     String str1 = paramPacketDataSerializer.c(36);
/* 25 */     String str2 = paramPacketDataSerializer.c(16);
/* 26 */     UUID localUUID = UUID.fromString(str1);
/* 27 */     this.a = new GameProfile(localUUID, str2);
/*    */   }
/*    */   
/*    */   public void b(PacketDataSerializer paramPacketDataSerializer) throws IOException
/*    */   {
/* 32 */     UUID localUUID = this.a.getId();
/* 33 */     paramPacketDataSerializer.a(localUUID == null ? "" : localUUID.toString());
/* 34 */     paramPacketDataSerializer.a(this.a.getName());
/*    */   }
/*    */   
/*    */   public void a(PacketLoginOutListener paramPacketLoginOutListener)
/*    */   {
/* 39 */     paramPacketLoginOutListener.a(this);
/*    */   }
/*    */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_8_R3\PacketLoginOutSuccess.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */
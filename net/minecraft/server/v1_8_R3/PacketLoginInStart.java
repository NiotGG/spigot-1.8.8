/*    */ package net.minecraft.server.v1_8_R3;
/*    */ 
/*    */ import com.mojang.authlib.GameProfile;
/*    */ import java.io.IOException;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class PacketLoginInStart
/*    */   implements Packet<PacketLoginInListener>
/*    */ {
/*    */   private GameProfile a;
/*    */   
/*    */   public PacketLoginInStart() {}
/*    */   
/*    */   public PacketLoginInStart(GameProfile paramGameProfile)
/*    */   {
/* 18 */     this.a = paramGameProfile;
/*    */   }
/*    */   
/*    */   public void a(PacketDataSerializer paramPacketDataSerializer) throws IOException
/*    */   {
/* 23 */     this.a = new GameProfile(null, paramPacketDataSerializer.c(16));
/*    */   }
/*    */   
/*    */   public void b(PacketDataSerializer paramPacketDataSerializer) throws IOException
/*    */   {
/* 28 */     paramPacketDataSerializer.a(this.a.getName());
/*    */   }
/*    */   
/*    */   public void a(PacketLoginInListener paramPacketLoginInListener)
/*    */   {
/* 33 */     paramPacketLoginInListener.a(this);
/*    */   }
/*    */   
/*    */   public GameProfile a() {
/* 37 */     return this.a;
/*    */   }
/*    */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_8_R3\PacketLoginInStart.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */
/*    */ package net.minecraft.server.v1_8_R3;
/*    */ 
/*    */ import java.io.IOException;
/*    */ import java.util.UUID;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class PacketPlayInSpectate
/*    */   implements Packet<PacketListenerPlayIn>
/*    */ {
/*    */   private UUID a;
/*    */   
/*    */   public PacketPlayInSpectate() {}
/*    */   
/*    */   public PacketPlayInSpectate(UUID paramUUID)
/*    */   {
/* 20 */     this.a = paramUUID;
/*    */   }
/*    */   
/*    */   public void a(PacketDataSerializer paramPacketDataSerializer) throws IOException
/*    */   {
/* 25 */     this.a = paramPacketDataSerializer.g();
/*    */   }
/*    */   
/*    */   public void b(PacketDataSerializer paramPacketDataSerializer) throws IOException
/*    */   {
/* 30 */     paramPacketDataSerializer.a(this.a);
/*    */   }
/*    */   
/*    */   public void a(PacketListenerPlayIn paramPacketListenerPlayIn)
/*    */   {
/* 35 */     paramPacketListenerPlayIn.a(this);
/*    */   }
/*    */   
/*    */   public Entity a(WorldServer paramWorldServer)
/*    */   {
/* 40 */     return paramWorldServer.getEntity(this.a);
/*    */   }
/*    */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_8_R3\PacketPlayInSpectate.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */
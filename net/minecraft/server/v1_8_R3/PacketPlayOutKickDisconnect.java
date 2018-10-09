/*    */ package net.minecraft.server.v1_8_R3;
/*    */ 
/*    */ import java.io.IOException;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class PacketPlayOutKickDisconnect
/*    */   implements Packet<PacketListenerPlayOut>
/*    */ {
/*    */   private IChatBaseComponent a;
/*    */   
/*    */   public PacketPlayOutKickDisconnect() {}
/*    */   
/*    */   public PacketPlayOutKickDisconnect(IChatBaseComponent paramIChatBaseComponent)
/*    */   {
/* 17 */     this.a = paramIChatBaseComponent;
/*    */   }
/*    */   
/*    */   public void a(PacketDataSerializer paramPacketDataSerializer) throws IOException
/*    */   {
/* 22 */     this.a = paramPacketDataSerializer.d();
/*    */   }
/*    */   
/*    */   public void b(PacketDataSerializer paramPacketDataSerializer) throws IOException
/*    */   {
/* 27 */     paramPacketDataSerializer.a(this.a);
/*    */   }
/*    */   
/*    */   public void a(PacketListenerPlayOut paramPacketListenerPlayOut)
/*    */   {
/* 32 */     paramPacketListenerPlayOut.a(this);
/*    */   }
/*    */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_8_R3\PacketPlayOutKickDisconnect.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */
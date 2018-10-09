/*    */ package net.minecraft.server.v1_8_R3;
/*    */ 
/*    */ import java.io.IOException;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class PacketPlayOutPlayerListHeaderFooter
/*    */   implements Packet<PacketListenerPlayOut>
/*    */ {
/*    */   private IChatBaseComponent a;
/*    */   private IChatBaseComponent b;
/*    */   
/*    */   public PacketPlayOutPlayerListHeaderFooter() {}
/*    */   
/*    */   public PacketPlayOutPlayerListHeaderFooter(IChatBaseComponent paramIChatBaseComponent)
/*    */   {
/* 18 */     this.a = paramIChatBaseComponent;
/*    */   }
/*    */   
/*    */   public void a(PacketDataSerializer paramPacketDataSerializer) throws IOException
/*    */   {
/* 23 */     this.a = paramPacketDataSerializer.d();
/* 24 */     this.b = paramPacketDataSerializer.d();
/*    */   }
/*    */   
/*    */   public void b(PacketDataSerializer paramPacketDataSerializer) throws IOException
/*    */   {
/* 29 */     paramPacketDataSerializer.a(this.a);
/* 30 */     paramPacketDataSerializer.a(this.b);
/*    */   }
/*    */   
/*    */   public void a(PacketListenerPlayOut paramPacketListenerPlayOut)
/*    */   {
/* 35 */     paramPacketListenerPlayOut.a(this);
/*    */   }
/*    */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_8_R3\PacketPlayOutPlayerListHeaderFooter.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */
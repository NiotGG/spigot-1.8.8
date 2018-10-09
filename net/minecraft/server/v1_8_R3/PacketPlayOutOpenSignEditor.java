/*    */ package net.minecraft.server.v1_8_R3;
/*    */ 
/*    */ import java.io.IOException;
/*    */ 
/*    */ 
/*    */ 
/*    */ public class PacketPlayOutOpenSignEditor
/*    */   implements Packet<PacketListenerPlayOut>
/*    */ {
/*    */   private BlockPosition a;
/*    */   
/*    */   public PacketPlayOutOpenSignEditor() {}
/*    */   
/*    */   public PacketPlayOutOpenSignEditor(BlockPosition paramBlockPosition)
/*    */   {
/* 16 */     this.a = paramBlockPosition;
/*    */   }
/*    */   
/*    */   public void a(PacketListenerPlayOut paramPacketListenerPlayOut)
/*    */   {
/* 21 */     paramPacketListenerPlayOut.a(this);
/*    */   }
/*    */   
/*    */   public void a(PacketDataSerializer paramPacketDataSerializer) throws IOException
/*    */   {
/* 26 */     this.a = paramPacketDataSerializer.c();
/*    */   }
/*    */   
/*    */   public void b(PacketDataSerializer paramPacketDataSerializer) throws IOException
/*    */   {
/* 31 */     paramPacketDataSerializer.a(this.a);
/*    */   }
/*    */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_8_R3\PacketPlayOutOpenSignEditor.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */
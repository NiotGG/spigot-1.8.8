/*    */ package net.minecraft.server.v1_8_R3;
/*    */ 
/*    */ import java.io.IOException;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class PacketPlayOutWorldEvent
/*    */   implements Packet<PacketListenerPlayOut>
/*    */ {
/*    */   private int a;
/*    */   private BlockPosition b;
/*    */   private int c;
/*    */   private boolean d;
/*    */   
/*    */   public PacketPlayOutWorldEvent() {}
/*    */   
/*    */   public PacketPlayOutWorldEvent(int paramInt1, BlockPosition paramBlockPosition, int paramInt2, boolean paramBoolean)
/*    */   {
/* 21 */     this.a = paramInt1;
/* 22 */     this.b = paramBlockPosition;
/* 23 */     this.c = paramInt2;
/* 24 */     this.d = paramBoolean;
/*    */   }
/*    */   
/*    */   public void a(PacketDataSerializer paramPacketDataSerializer) throws IOException
/*    */   {
/* 29 */     this.a = paramPacketDataSerializer.readInt();
/* 30 */     this.b = paramPacketDataSerializer.c();
/* 31 */     this.c = paramPacketDataSerializer.readInt();
/* 32 */     this.d = paramPacketDataSerializer.readBoolean();
/*    */   }
/*    */   
/*    */   public void b(PacketDataSerializer paramPacketDataSerializer) throws IOException
/*    */   {
/* 37 */     paramPacketDataSerializer.writeInt(this.a);
/* 38 */     paramPacketDataSerializer.a(this.b);
/* 39 */     paramPacketDataSerializer.writeInt(this.c);
/* 40 */     paramPacketDataSerializer.writeBoolean(this.d);
/*    */   }
/*    */   
/*    */   public void a(PacketListenerPlayOut paramPacketListenerPlayOut)
/*    */   {
/* 45 */     paramPacketListenerPlayOut.a(this);
/*    */   }
/*    */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_8_R3\PacketPlayOutWorldEvent.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */
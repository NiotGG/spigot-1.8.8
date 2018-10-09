/*    */ package net.minecraft.server.v1_8_R3;
/*    */ 
/*    */ import java.io.IOException;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class PacketPlayOutTileEntityData
/*    */   implements Packet<PacketListenerPlayOut>
/*    */ {
/*    */   private BlockPosition a;
/*    */   private int b;
/*    */   private NBTTagCompound c;
/*    */   
/*    */   public PacketPlayOutTileEntityData() {}
/*    */   
/*    */   public PacketPlayOutTileEntityData(BlockPosition paramBlockPosition, int paramInt, NBTTagCompound paramNBTTagCompound)
/*    */   {
/* 28 */     this.a = paramBlockPosition;
/* 29 */     this.b = paramInt;
/* 30 */     this.c = paramNBTTagCompound;
/*    */   }
/*    */   
/*    */   public void a(PacketDataSerializer paramPacketDataSerializer) throws IOException
/*    */   {
/* 35 */     this.a = paramPacketDataSerializer.c();
/* 36 */     this.b = paramPacketDataSerializer.readUnsignedByte();
/* 37 */     this.c = paramPacketDataSerializer.h();
/*    */   }
/*    */   
/*    */   public void b(PacketDataSerializer paramPacketDataSerializer) throws IOException
/*    */   {
/* 42 */     paramPacketDataSerializer.a(this.a);
/* 43 */     paramPacketDataSerializer.writeByte((byte)this.b);
/* 44 */     paramPacketDataSerializer.a(this.c);
/*    */   }
/*    */   
/*    */   public void a(PacketListenerPlayOut paramPacketListenerPlayOut)
/*    */   {
/* 49 */     paramPacketListenerPlayOut.a(this);
/*    */   }
/*    */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_8_R3\PacketPlayOutTileEntityData.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */
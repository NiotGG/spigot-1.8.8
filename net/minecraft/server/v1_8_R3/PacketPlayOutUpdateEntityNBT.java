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
/*    */ public class PacketPlayOutUpdateEntityNBT
/*    */   implements Packet<PacketListenerPlayOut>
/*    */ {
/*    */   private int a;
/*    */   private NBTTagCompound b;
/*    */   
/*    */   public PacketPlayOutUpdateEntityNBT() {}
/*    */   
/*    */   public PacketPlayOutUpdateEntityNBT(int paramInt, NBTTagCompound paramNBTTagCompound)
/*    */   {
/* 21 */     this.a = paramInt;
/* 22 */     this.b = paramNBTTagCompound;
/*    */   }
/*    */   
/*    */   public void a(PacketDataSerializer paramPacketDataSerializer) throws IOException
/*    */   {
/* 27 */     this.a = paramPacketDataSerializer.e();
/* 28 */     this.b = paramPacketDataSerializer.h();
/*    */   }
/*    */   
/*    */   public void b(PacketDataSerializer paramPacketDataSerializer) throws IOException
/*    */   {
/* 33 */     paramPacketDataSerializer.b(this.a);
/* 34 */     paramPacketDataSerializer.a(this.b);
/*    */   }
/*    */   
/*    */   public void a(PacketListenerPlayOut paramPacketListenerPlayOut)
/*    */   {
/* 39 */     paramPacketListenerPlayOut.a(this);
/*    */   }
/*    */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_8_R3\PacketPlayOutUpdateEntityNBT.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */
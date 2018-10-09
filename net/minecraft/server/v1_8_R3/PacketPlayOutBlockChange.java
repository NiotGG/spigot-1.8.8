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
/*    */ public class PacketPlayOutBlockChange
/*    */   implements Packet<PacketListenerPlayOut>
/*    */ {
/*    */   private BlockPosition a;
/*    */   public IBlockData block;
/*    */   
/*    */   public PacketPlayOutBlockChange() {}
/*    */   
/*    */   public PacketPlayOutBlockChange(World paramWorld, BlockPosition paramBlockPosition)
/*    */   {
/* 22 */     this.a = paramBlockPosition;
/* 23 */     this.block = paramWorld.getType(paramBlockPosition);
/*    */   }
/*    */   
/*    */   public void a(PacketDataSerializer paramPacketDataSerializer) throws IOException
/*    */   {
/* 28 */     this.a = paramPacketDataSerializer.c();
/* 29 */     this.block = ((IBlockData)Block.d.a(paramPacketDataSerializer.e()));
/*    */   }
/*    */   
/*    */   public void b(PacketDataSerializer paramPacketDataSerializer) throws IOException
/*    */   {
/* 34 */     paramPacketDataSerializer.a(this.a);
/* 35 */     paramPacketDataSerializer.b(Block.d.b(this.block));
/*    */   }
/*    */   
/*    */   public void a(PacketListenerPlayOut paramPacketListenerPlayOut)
/*    */   {
/* 40 */     paramPacketListenerPlayOut.a(this);
/*    */   }
/*    */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_8_R3\PacketPlayOutBlockChange.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */
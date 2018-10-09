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
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class PacketPlayOutGameStateChange
/*    */   implements Packet<PacketListenerPlayOut>
/*    */ {
/* 26 */   public static final String[] a = { "tile.bed.notValid" };
/*    */   
/*    */   private int b;
/*    */   
/*    */   private float c;
/*    */   
/*    */ 
/*    */   public PacketPlayOutGameStateChange() {}
/*    */   
/*    */ 
/*    */   public PacketPlayOutGameStateChange(int paramInt, float paramFloat)
/*    */   {
/* 38 */     this.b = paramInt;
/* 39 */     this.c = paramFloat;
/*    */   }
/*    */   
/*    */   public void a(PacketDataSerializer paramPacketDataSerializer) throws IOException
/*    */   {
/* 44 */     this.b = paramPacketDataSerializer.readUnsignedByte();
/* 45 */     this.c = paramPacketDataSerializer.readFloat();
/*    */   }
/*    */   
/*    */   public void b(PacketDataSerializer paramPacketDataSerializer) throws IOException
/*    */   {
/* 50 */     paramPacketDataSerializer.writeByte(this.b);
/* 51 */     paramPacketDataSerializer.writeFloat(this.c);
/*    */   }
/*    */   
/*    */   public void a(PacketListenerPlayOut paramPacketListenerPlayOut)
/*    */   {
/* 56 */     paramPacketListenerPlayOut.a(this);
/*    */   }
/*    */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_8_R3\PacketPlayOutGameStateChange.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */
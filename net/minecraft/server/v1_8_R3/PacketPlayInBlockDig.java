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
/*    */ public class PacketPlayInBlockDig
/*    */   implements Packet<PacketListenerPlayIn>
/*    */ {
/*    */   private BlockPosition a;
/*    */   private EnumDirection b;
/*    */   private EnumPlayerDigType c;
/*    */   
/*    */   public void a(PacketDataSerializer paramPacketDataSerializer)
/*    */     throws IOException
/*    */   {
/* 26 */     this.c = ((EnumPlayerDigType)paramPacketDataSerializer.a(EnumPlayerDigType.class));
/* 27 */     this.a = paramPacketDataSerializer.c();
/* 28 */     this.b = EnumDirection.fromType1(paramPacketDataSerializer.readUnsignedByte());
/*    */   }
/*    */   
/*    */   public void b(PacketDataSerializer paramPacketDataSerializer) throws IOException
/*    */   {
/* 33 */     paramPacketDataSerializer.a(this.c);
/* 34 */     paramPacketDataSerializer.a(this.a);
/* 35 */     paramPacketDataSerializer.writeByte(this.b.a());
/*    */   }
/*    */   
/*    */   public void a(PacketListenerPlayIn paramPacketListenerPlayIn)
/*    */   {
/* 40 */     paramPacketListenerPlayIn.a(this);
/*    */   }
/*    */   
/*    */   public BlockPosition a() {
/* 44 */     return this.a;
/*    */   }
/*    */   
/*    */   public EnumDirection b() {
/* 48 */     return this.b;
/*    */   }
/*    */   
/*    */   public EnumPlayerDigType c() {
/* 52 */     return this.c;
/*    */   }
/*    */   
/*    */   public static enum EnumPlayerDigType
/*    */   {
/*    */     private EnumPlayerDigType() {}
/*    */   }
/*    */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_8_R3\PacketPlayInBlockDig.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */
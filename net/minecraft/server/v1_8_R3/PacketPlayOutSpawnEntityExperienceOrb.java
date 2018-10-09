/*    */ package net.minecraft.server.v1_8_R3;
/*    */ 
/*    */ import java.io.IOException;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class PacketPlayOutSpawnEntityExperienceOrb
/*    */   implements Packet<PacketListenerPlayOut>
/*    */ {
/*    */   private int a;
/*    */   private int b;
/*    */   private int c;
/*    */   private int d;
/*    */   private int e;
/*    */   
/*    */   public PacketPlayOutSpawnEntityExperienceOrb() {}
/*    */   
/*    */   public PacketPlayOutSpawnEntityExperienceOrb(EntityExperienceOrb paramEntityExperienceOrb)
/*    */   {
/* 23 */     this.a = paramEntityExperienceOrb.getId();
/* 24 */     this.b = MathHelper.floor(paramEntityExperienceOrb.locX * 32.0D);
/* 25 */     this.c = MathHelper.floor(paramEntityExperienceOrb.locY * 32.0D);
/* 26 */     this.d = MathHelper.floor(paramEntityExperienceOrb.locZ * 32.0D);
/* 27 */     this.e = paramEntityExperienceOrb.j();
/*    */   }
/*    */   
/*    */   public void a(PacketDataSerializer paramPacketDataSerializer) throws IOException
/*    */   {
/* 32 */     this.a = paramPacketDataSerializer.e();
/* 33 */     this.b = paramPacketDataSerializer.readInt();
/* 34 */     this.c = paramPacketDataSerializer.readInt();
/* 35 */     this.d = paramPacketDataSerializer.readInt();
/* 36 */     this.e = paramPacketDataSerializer.readShort();
/*    */   }
/*    */   
/*    */   public void b(PacketDataSerializer paramPacketDataSerializer) throws IOException
/*    */   {
/* 41 */     paramPacketDataSerializer.b(this.a);
/* 42 */     paramPacketDataSerializer.writeInt(this.b);
/* 43 */     paramPacketDataSerializer.writeInt(this.c);
/* 44 */     paramPacketDataSerializer.writeInt(this.d);
/* 45 */     paramPacketDataSerializer.writeShort(this.e);
/*    */   }
/*    */   
/*    */   public void a(PacketListenerPlayOut paramPacketListenerPlayOut)
/*    */   {
/* 50 */     paramPacketListenerPlayOut.a(this);
/*    */   }
/*    */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_8_R3\PacketPlayOutSpawnEntityExperienceOrb.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */
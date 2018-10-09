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
/*    */ public class PacketPlayOutLogin
/*    */   implements Packet<PacketListenerPlayOut>
/*    */ {
/*    */   private int a;
/*    */   private boolean b;
/*    */   private WorldSettings.EnumGamemode c;
/*    */   private int d;
/*    */   private EnumDifficulty e;
/*    */   private int f;
/*    */   private WorldType g;
/*    */   private boolean h;
/*    */   
/*    */   public PacketPlayOutLogin() {}
/*    */   
/*    */   public PacketPlayOutLogin(int paramInt1, WorldSettings.EnumGamemode paramEnumGamemode, boolean paramBoolean1, int paramInt2, EnumDifficulty paramEnumDifficulty, int paramInt3, WorldType paramWorldType, boolean paramBoolean2)
/*    */   {
/* 30 */     this.a = paramInt1;
/* 31 */     this.d = paramInt2;
/* 32 */     this.e = paramEnumDifficulty;
/* 33 */     this.c = paramEnumGamemode;
/* 34 */     this.f = paramInt3;
/* 35 */     this.b = paramBoolean1;
/* 36 */     this.g = paramWorldType;
/* 37 */     this.h = paramBoolean2;
/*    */   }
/*    */   
/*    */   public void a(PacketDataSerializer paramPacketDataSerializer) throws IOException
/*    */   {
/* 42 */     this.a = paramPacketDataSerializer.readInt();
/*    */     
/* 44 */     int i = paramPacketDataSerializer.readUnsignedByte();
/* 45 */     this.b = ((i & 0x8) == 8);
/* 46 */     i &= 0xFFFFFFF7;
/* 47 */     this.c = WorldSettings.EnumGamemode.getById(i);
/*    */     
/* 49 */     this.d = paramPacketDataSerializer.readByte();
/* 50 */     this.e = EnumDifficulty.getById(paramPacketDataSerializer.readUnsignedByte());
/* 51 */     this.f = paramPacketDataSerializer.readUnsignedByte();
/* 52 */     this.g = WorldType.getType(paramPacketDataSerializer.c(16));
/* 53 */     if (this.g == null) {
/* 54 */       this.g = WorldType.NORMAL;
/*    */     }
/* 56 */     this.h = paramPacketDataSerializer.readBoolean();
/*    */   }
/*    */   
/*    */   public void b(PacketDataSerializer paramPacketDataSerializer) throws IOException
/*    */   {
/* 61 */     paramPacketDataSerializer.writeInt(this.a);
/* 62 */     int i = this.c.getId();
/* 63 */     if (this.b) {
/* 64 */       i |= 0x8;
/*    */     }
/* 66 */     paramPacketDataSerializer.writeByte(i);
/* 67 */     paramPacketDataSerializer.writeByte(this.d);
/* 68 */     paramPacketDataSerializer.writeByte(this.e.a());
/* 69 */     paramPacketDataSerializer.writeByte(this.f);
/* 70 */     paramPacketDataSerializer.a(this.g.name());
/* 71 */     paramPacketDataSerializer.writeBoolean(this.h);
/*    */   }
/*    */   
/*    */   public void a(PacketListenerPlayOut paramPacketListenerPlayOut)
/*    */   {
/* 76 */     paramPacketListenerPlayOut.a(this);
/*    */   }
/*    */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_8_R3\PacketPlayOutLogin.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */
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
/*    */ public class PacketPlayOutRespawn
/*    */   implements Packet<PacketListenerPlayOut>
/*    */ {
/*    */   private int a;
/*    */   private EnumDifficulty b;
/*    */   private WorldSettings.EnumGamemode c;
/*    */   private WorldType d;
/*    */   
/*    */   public PacketPlayOutRespawn() {}
/*    */   
/*    */   public PacketPlayOutRespawn(int paramInt, EnumDifficulty paramEnumDifficulty, WorldType paramWorldType, WorldSettings.EnumGamemode paramEnumGamemode)
/*    */   {
/* 23 */     this.a = paramInt;
/* 24 */     this.b = paramEnumDifficulty;
/* 25 */     this.c = paramEnumGamemode;
/* 26 */     this.d = paramWorldType;
/*    */   }
/*    */   
/*    */   public void a(PacketListenerPlayOut paramPacketListenerPlayOut)
/*    */   {
/* 31 */     paramPacketListenerPlayOut.a(this);
/*    */   }
/*    */   
/*    */   public void a(PacketDataSerializer paramPacketDataSerializer) throws IOException
/*    */   {
/* 36 */     this.a = paramPacketDataSerializer.readInt();
/* 37 */     this.b = EnumDifficulty.getById(paramPacketDataSerializer.readUnsignedByte());
/* 38 */     this.c = WorldSettings.EnumGamemode.getById(paramPacketDataSerializer.readUnsignedByte());
/* 39 */     this.d = WorldType.getType(paramPacketDataSerializer.c(16));
/* 40 */     if (this.d == null) {
/* 41 */       this.d = WorldType.NORMAL;
/*    */     }
/*    */   }
/*    */   
/*    */   public void b(PacketDataSerializer paramPacketDataSerializer) throws IOException
/*    */   {
/* 47 */     paramPacketDataSerializer.writeInt(this.a);
/* 48 */     paramPacketDataSerializer.writeByte(this.b.a());
/* 49 */     paramPacketDataSerializer.writeByte(this.c.getId());
/* 50 */     paramPacketDataSerializer.a(this.d.name());
/*    */   }
/*    */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_8_R3\PacketPlayOutRespawn.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */
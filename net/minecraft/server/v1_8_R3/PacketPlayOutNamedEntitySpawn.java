/*    */ package net.minecraft.server.v1_8_R3;
/*    */ 
/*    */ import com.mojang.authlib.GameProfile;
/*    */ import java.io.IOException;
/*    */ import java.util.List;
/*    */ import java.util.UUID;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class PacketPlayOutNamedEntitySpawn
/*    */   implements Packet<PacketListenerPlayOut>
/*    */ {
/*    */   private int a;
/*    */   private UUID b;
/*    */   private int c;
/*    */   private int d;
/*    */   private int e;
/*    */   private byte f;
/*    */   private byte g;
/*    */   private int h;
/*    */   private DataWatcher i;
/*    */   private List<DataWatcher.WatchableObject> j;
/*    */   
/*    */   public PacketPlayOutNamedEntitySpawn() {}
/*    */   
/*    */   public PacketPlayOutNamedEntitySpawn(EntityHuman paramEntityHuman)
/*    */   {
/* 33 */     this.a = paramEntityHuman.getId();
/* 34 */     this.b = paramEntityHuman.getProfile().getId();
/* 35 */     this.c = MathHelper.floor(paramEntityHuman.locX * 32.0D);
/* 36 */     this.d = MathHelper.floor(paramEntityHuman.locY * 32.0D);
/* 37 */     this.e = MathHelper.floor(paramEntityHuman.locZ * 32.0D);
/* 38 */     this.f = ((byte)(int)(paramEntityHuman.yaw * 256.0F / 360.0F));
/* 39 */     this.g = ((byte)(int)(paramEntityHuman.pitch * 256.0F / 360.0F));
/*    */     
/* 41 */     ItemStack localItemStack = paramEntityHuman.inventory.getItemInHand();
/* 42 */     this.h = (localItemStack == null ? 0 : Item.getId(localItemStack.getItem()));
/*    */     
/* 44 */     this.i = paramEntityHuman.getDataWatcher();
/*    */   }
/*    */   
/*    */   public void a(PacketDataSerializer paramPacketDataSerializer) throws IOException
/*    */   {
/* 49 */     this.a = paramPacketDataSerializer.e();
/* 50 */     this.b = paramPacketDataSerializer.g();
/* 51 */     this.c = paramPacketDataSerializer.readInt();
/* 52 */     this.d = paramPacketDataSerializer.readInt();
/* 53 */     this.e = paramPacketDataSerializer.readInt();
/* 54 */     this.f = paramPacketDataSerializer.readByte();
/* 55 */     this.g = paramPacketDataSerializer.readByte();
/* 56 */     this.h = paramPacketDataSerializer.readShort();
/* 57 */     this.j = DataWatcher.b(paramPacketDataSerializer);
/*    */   }
/*    */   
/*    */   public void b(PacketDataSerializer paramPacketDataSerializer) throws IOException
/*    */   {
/* 62 */     paramPacketDataSerializer.b(this.a);
/* 63 */     paramPacketDataSerializer.a(this.b);
/* 64 */     paramPacketDataSerializer.writeInt(this.c);
/* 65 */     paramPacketDataSerializer.writeInt(this.d);
/* 66 */     paramPacketDataSerializer.writeInt(this.e);
/* 67 */     paramPacketDataSerializer.writeByte(this.f);
/* 68 */     paramPacketDataSerializer.writeByte(this.g);
/* 69 */     paramPacketDataSerializer.writeShort(this.h);
/* 70 */     this.i.a(paramPacketDataSerializer);
/*    */   }
/*    */   
/*    */   public void a(PacketListenerPlayOut paramPacketListenerPlayOut)
/*    */   {
/* 75 */     paramPacketListenerPlayOut.a(this);
/*    */   }
/*    */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_8_R3\PacketPlayOutNamedEntitySpawn.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */
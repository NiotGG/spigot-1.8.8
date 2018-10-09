/*    */ package net.minecraft.server.v1_8_R3;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class TileEntityMobSpawner
/*    */   extends TileEntity
/*    */   implements IUpdatePlayerListBox
/*    */ {
/* 13 */   private final MobSpawnerAbstract a = new MobSpawnerAbstract()
/*    */   {
/*    */     public void a(int paramAnonymousInt) {
/* 16 */       TileEntityMobSpawner.this.world.playBlockAction(TileEntityMobSpawner.this.position, Blocks.MOB_SPAWNER, paramAnonymousInt, 0);
/*    */     }
/*    */     
/*    */     public World a()
/*    */     {
/* 21 */       return TileEntityMobSpawner.this.world;
/*    */     }
/*    */     
/*    */     public BlockPosition b()
/*    */     {
/* 26 */       return TileEntityMobSpawner.this.position;
/*    */     }
/*    */     
/*    */     public void a(TileEntityMobSpawnerData paramAnonymousTileEntityMobSpawnerData)
/*    */     {
/* 31 */       super.a(paramAnonymousTileEntityMobSpawnerData);
/* 32 */       if (a() != null) {
/* 33 */         a().notify(TileEntityMobSpawner.this.position);
/*    */       }
/*    */     }
/*    */   };
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */   public void a(NBTTagCompound paramNBTTagCompound)
/*    */   {
/* 43 */     super.a(paramNBTTagCompound);
/* 44 */     this.a.a(paramNBTTagCompound);
/*    */   }
/*    */   
/*    */   public void b(NBTTagCompound paramNBTTagCompound)
/*    */   {
/* 49 */     super.b(paramNBTTagCompound);
/* 50 */     this.a.b(paramNBTTagCompound);
/*    */   }
/*    */   
/*    */   public void c()
/*    */   {
/* 55 */     this.a.c();
/*    */   }
/*    */   
/*    */   public Packet getUpdatePacket()
/*    */   {
/* 60 */     NBTTagCompound localNBTTagCompound = new NBTTagCompound();
/* 61 */     b(localNBTTagCompound);
/* 62 */     localNBTTagCompound.remove("SpawnPotentials");
/* 63 */     return new PacketPlayOutTileEntityData(this.position, 1, localNBTTagCompound);
/*    */   }
/*    */   
/*    */   public boolean c(int paramInt1, int paramInt2)
/*    */   {
/* 68 */     if (this.a.b(paramInt1)) {
/* 69 */       return true;
/*    */     }
/* 71 */     return super.c(paramInt1, paramInt2);
/*    */   }
/*    */   
/*    */   public boolean F()
/*    */   {
/* 76 */     return true;
/*    */   }
/*    */   
/*    */   public MobSpawnerAbstract getSpawner() {
/* 80 */     return this.a;
/*    */   }
/*    */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_8_R3\TileEntityMobSpawner.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */
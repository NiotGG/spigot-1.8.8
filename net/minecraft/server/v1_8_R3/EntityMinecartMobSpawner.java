/*    */ package net.minecraft.server.v1_8_R3;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class EntityMinecartMobSpawner
/*    */   extends EntityMinecartAbstract
/*    */ {
/* 11 */   private final MobSpawnerAbstract a = new MobSpawnerAbstract()
/*    */   {
/*    */     public void a(int paramAnonymousInt) {
/* 14 */       EntityMinecartMobSpawner.this.world.broadcastEntityEffect(EntityMinecartMobSpawner.this, (byte)paramAnonymousInt);
/*    */     }
/*    */     
/*    */     public World a()
/*    */     {
/* 19 */       return EntityMinecartMobSpawner.this.world;
/*    */     }
/*    */     
/*    */     public BlockPosition b()
/*    */     {
/* 24 */       return new BlockPosition(EntityMinecartMobSpawner.this);
/*    */     }
/*    */   };
/*    */   
/*    */   public EntityMinecartMobSpawner(World paramWorld) {
/* 29 */     super(paramWorld);
/*    */   }
/*    */   
/*    */   public EntityMinecartMobSpawner(World paramWorld, double paramDouble1, double paramDouble2, double paramDouble3) {
/* 33 */     super(paramWorld, paramDouble1, paramDouble2, paramDouble3);
/*    */   }
/*    */   
/*    */   public EntityMinecartAbstract.EnumMinecartType s()
/*    */   {
/* 38 */     return EntityMinecartAbstract.EnumMinecartType.SPAWNER;
/*    */   }
/*    */   
/*    */   public IBlockData u()
/*    */   {
/* 43 */     return Blocks.MOB_SPAWNER.getBlockData();
/*    */   }
/*    */   
/*    */   protected void a(NBTTagCompound paramNBTTagCompound)
/*    */   {
/* 48 */     super.a(paramNBTTagCompound);
/* 49 */     this.a.a(paramNBTTagCompound);
/*    */   }
/*    */   
/*    */   protected void b(NBTTagCompound paramNBTTagCompound)
/*    */   {
/* 54 */     super.b(paramNBTTagCompound);
/* 55 */     this.a.b(paramNBTTagCompound);
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   public void t_()
/*    */   {
/* 65 */     super.t_();
/* 66 */     this.a.c();
/*    */   }
/*    */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_8_R3\EntityMinecartMobSpawner.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */
/*    */ package net.minecraft.server.v1_8_R3;
/*    */ 
/*    */ import org.bukkit.craftbukkit.v1_8_R3.CraftServer;
/*    */ import org.bukkit.craftbukkit.v1_8_R3.event.CraftEventFactory;
/*    */ import org.bukkit.event.entity.ExplosionPrimeEvent;
/*    */ 
/*    */ public class EntityEnderCrystal extends Entity
/*    */ {
/*    */   public int a;
/*    */   public int b;
/*    */   
/*    */   public EntityEnderCrystal(World world)
/*    */   {
/* 14 */     super(world);
/* 15 */     this.k = true;
/* 16 */     setSize(2.0F, 2.0F);
/* 17 */     this.b = 5;
/* 18 */     this.a = this.random.nextInt(100000);
/*    */   }
/*    */   
/*    */   protected boolean s_() {
/* 22 */     return false;
/*    */   }
/*    */   
/*    */   protected void h() {
/* 26 */     this.datawatcher.a(8, Integer.valueOf(this.b));
/*    */   }
/*    */   
/*    */   public void t_() {
/* 30 */     this.lastX = this.locX;
/* 31 */     this.lastY = this.locY;
/* 32 */     this.lastZ = this.locZ;
/* 33 */     this.a += 1;
/* 34 */     this.datawatcher.watch(8, Integer.valueOf(this.b));
/* 35 */     int i = MathHelper.floor(this.locX);
/* 36 */     int j = MathHelper.floor(this.locY);
/* 37 */     int k = MathHelper.floor(this.locZ);
/*    */     
/* 39 */     if (((this.world.worldProvider instanceof WorldProviderTheEnd)) && (this.world.getType(new BlockPosition(i, j, k)).getBlock() != Blocks.FIRE))
/*    */     {
/* 41 */       if (!CraftEventFactory.callBlockIgniteEvent(this.world, i, j, k, this).isCancelled()) {
/* 42 */         this.world.setTypeUpdate(new BlockPosition(i, j, k), Blocks.FIRE.getBlockData());
/*    */       }
/*    */     }
/*    */   }
/*    */   
/*    */ 
/*    */   protected void b(NBTTagCompound nbttagcompound) {}
/*    */   
/*    */   protected void a(NBTTagCompound nbttagcompound) {}
/*    */   
/*    */   public boolean ad()
/*    */   {
/* 54 */     return true;
/*    */   }
/*    */   
/*    */   public boolean damageEntity(DamageSource damagesource, float f) {
/* 58 */     if (isInvulnerable(damagesource)) {
/* 59 */       return false;
/*    */     }
/* 61 */     if ((!this.dead) && (!this.world.isClientSide))
/*    */     {
/* 63 */       if (CraftEventFactory.handleNonLivingEntityDamageEvent(this, damagesource, f)) {
/* 64 */         return false;
/*    */       }
/*    */       
/* 67 */       this.b = 0;
/* 68 */       if (this.b <= 0) {
/* 69 */         die();
/* 70 */         if (!this.world.isClientSide)
/*    */         {
/* 72 */           ExplosionPrimeEvent event = new ExplosionPrimeEvent(getBukkitEntity(), 6.0F, false);
/* 73 */           this.world.getServer().getPluginManager().callEvent(event);
/* 74 */           if (event.isCancelled()) {
/* 75 */             this.dead = false;
/* 76 */             return false;
/*    */           }
/* 78 */           this.world.createExplosion(this, this.locX, this.locY, this.locZ, event.getRadius(), event.getFire(), true);
/*    */         }
/*    */       }
/*    */     }
/*    */     
/*    */ 
/* 84 */     return true;
/*    */   }
/*    */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_8_R3\EntityEnderCrystal.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */
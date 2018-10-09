/*     */ package net.minecraft.server.v1_8_R3;
/*     */ 
/*     */ import org.bukkit.event.entity.ExplosionPrimeEvent;
/*     */ 
/*     */ public class EntityWitherSkull extends EntityFireball
/*     */ {
/*     */   public EntityWitherSkull(World world) {
/*   8 */     super(world);
/*   9 */     setSize(0.3125F, 0.3125F);
/*     */   }
/*     */   
/*     */   public EntityWitherSkull(World world, EntityLiving entityliving, double d0, double d1, double d2) {
/*  13 */     super(world, entityliving, d0, d1, d2);
/*  14 */     setSize(0.3125F, 0.3125F);
/*     */   }
/*     */   
/*     */   protected float j() {
/*  18 */     return isCharged() ? 0.73F : super.j();
/*     */   }
/*     */   
/*     */   public boolean isBurning() {
/*  22 */     return false;
/*     */   }
/*     */   
/*     */   public float a(Explosion explosion, World world, BlockPosition blockposition, IBlockData iblockdata) {
/*  26 */     float f = super.a(explosion, world, blockposition, iblockdata);
/*  27 */     Block block = iblockdata.getBlock();
/*     */     
/*  29 */     if ((isCharged()) && (EntityWither.a(block))) {
/*  30 */       f = Math.min(0.8F, f);
/*     */     }
/*     */     
/*  33 */     return f;
/*     */   }
/*     */   
/*     */   protected void a(MovingObjectPosition movingobjectposition) {
/*  37 */     if (!this.world.isClientSide) {
/*  38 */       if (movingobjectposition.entity != null)
/*     */       {
/*  40 */         boolean didDamage = false;
/*  41 */         if (this.shooter != null) {
/*  42 */           didDamage = movingobjectposition.entity.damageEntity(DamageSource.projectile(this, this.shooter), 8.0F);
/*  43 */           if (didDamage) {
/*  44 */             if (!movingobjectposition.entity.isAlive()) {
/*  45 */               this.shooter.heal(5.0F, org.bukkit.event.entity.EntityRegainHealthEvent.RegainReason.WITHER);
/*     */             } else {
/*  47 */               a(this.shooter, movingobjectposition.entity);
/*     */             }
/*     */           }
/*     */         } else {
/*  51 */           didDamage = movingobjectposition.entity.damageEntity(DamageSource.MAGIC, 5.0F);
/*     */         }
/*     */         
/*  54 */         if ((didDamage) && ((movingobjectposition.entity instanceof EntityLiving)))
/*     */         {
/*  56 */           byte b0 = 0;
/*     */           
/*  58 */           if (this.world.getDifficulty() == EnumDifficulty.NORMAL) {
/*  59 */             b0 = 10;
/*  60 */           } else if (this.world.getDifficulty() == EnumDifficulty.HARD) {
/*  61 */             b0 = 40;
/*     */           }
/*     */           
/*  64 */           if (b0 > 0) {
/*  65 */             ((EntityLiving)movingobjectposition.entity).addEffect(new MobEffect(MobEffectList.WITHER.id, 20 * b0, 1));
/*     */           }
/*     */         }
/*     */       }
/*     */       
/*     */ 
/*     */ 
/*  72 */       ExplosionPrimeEvent event = new ExplosionPrimeEvent(getBukkitEntity(), 1.0F, false);
/*  73 */       this.world.getServer().getPluginManager().callEvent(event);
/*     */       
/*  75 */       if (!event.isCancelled()) {
/*  76 */         this.world.createExplosion(this, this.locX, this.locY, this.locZ, event.getRadius(), event.getFire(), this.world.getGameRules().getBoolean("mobGriefing"));
/*     */       }
/*     */       
/*  79 */       die();
/*     */     }
/*     */   }
/*     */   
/*     */   public boolean ad()
/*     */   {
/*  85 */     return false;
/*     */   }
/*     */   
/*     */   public boolean damageEntity(DamageSource damagesource, float f) {
/*  89 */     return false;
/*     */   }
/*     */   
/*     */   protected void h() {
/*  93 */     this.datawatcher.a(10, Byte.valueOf((byte)0));
/*     */   }
/*     */   
/*     */   public boolean isCharged() {
/*  97 */     return this.datawatcher.getByte(10) == 1;
/*     */   }
/*     */   
/*     */   public void setCharged(boolean flag) {
/* 101 */     this.datawatcher.watch(10, Byte.valueOf((byte)(flag ? 1 : 0)));
/*     */   }
/*     */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_8_R3\EntityWitherSkull.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */
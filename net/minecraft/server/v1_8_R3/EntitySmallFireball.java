/*    */ package net.minecraft.server.v1_8_R3;
/*    */ 
/*    */ import org.bukkit.event.entity.EntityCombustByEntityEvent;
/*    */ 
/*    */ public class EntitySmallFireball extends EntityFireball
/*    */ {
/*    */   public EntitySmallFireball(World world) {
/*  8 */     super(world);
/*  9 */     setSize(0.3125F, 0.3125F);
/*    */   }
/*    */   
/*    */   public EntitySmallFireball(World world, EntityLiving entityliving, double d0, double d1, double d2) {
/* 13 */     super(world, entityliving, d0, d1, d2);
/* 14 */     setSize(0.3125F, 0.3125F);
/*    */   }
/*    */   
/*    */   public EntitySmallFireball(World world, double d0, double d1, double d2, double d3, double d4, double d5) {
/* 18 */     super(world, d0, d1, d2, d3, d4, d5);
/* 19 */     setSize(0.3125F, 0.3125F);
/*    */   }
/*    */   
/*    */   protected void a(MovingObjectPosition movingobjectposition) {
/* 23 */     if (!this.world.isClientSide)
/*    */     {
/*    */ 
/* 26 */       if (movingobjectposition.entity != null) {
/* 27 */         boolean flag = movingobjectposition.entity.damageEntity(DamageSource.fireball(this, this.shooter), 5.0F);
/* 28 */         if (flag) {
/* 29 */           a(this.shooter, movingobjectposition.entity);
/* 30 */           if (!movingobjectposition.entity.isFireProof())
/*    */           {
/* 32 */             EntityCombustByEntityEvent event = new EntityCombustByEntityEvent((org.bukkit.entity.Projectile)getBukkitEntity(), movingobjectposition.entity.getBukkitEntity(), 5);
/* 33 */             movingobjectposition.entity.world.getServer().getPluginManager().callEvent(event);
/*    */             
/* 35 */             if (!event.isCancelled()) {
/* 36 */               movingobjectposition.entity.setOnFire(event.getDuration());
/*    */             }
/*    */           }
/*    */         }
/*    */       }
/*    */       else {
/* 42 */         boolean flag = true;
/* 43 */         if ((this.shooter != null) && ((this.shooter instanceof EntityInsentient))) {
/* 44 */           flag = this.world.getGameRules().getBoolean("mobGriefing");
/*    */         }
/*    */         
/* 47 */         if (flag) {
/* 48 */           BlockPosition blockposition = movingobjectposition.a().shift(movingobjectposition.direction);
/*    */           
/* 50 */           if (this.world.isEmpty(blockposition))
/*    */           {
/* 52 */             if ((this.isIncendiary) && (!org.bukkit.craftbukkit.v1_8_R3.event.CraftEventFactory.callBlockIgniteEvent(this.world, blockposition.getX(), blockposition.getY(), blockposition.getZ(), this).isCancelled())) {
/* 53 */               this.world.setTypeUpdate(blockposition, Blocks.FIRE.getBlockData());
/*    */             }
/*    */           }
/*    */         }
/*    */       }
/*    */       
/*    */ 
/* 60 */       die();
/*    */     }
/*    */   }
/*    */   
/*    */   public boolean ad()
/*    */   {
/* 66 */     return false;
/*    */   }
/*    */   
/*    */   public boolean damageEntity(DamageSource damagesource, float f) {
/* 70 */     return false;
/*    */   }
/*    */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_8_R3\EntitySmallFireball.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */
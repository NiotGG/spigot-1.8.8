/*    */ package net.minecraft.server.v1_8_R3;
/*    */ 
/*    */ import org.bukkit.event.entity.ExplosionPrimeEvent;
/*    */ 
/*    */ public class EntityLargeFireball extends EntityFireball
/*    */ {
/*  7 */   public int yield = 1;
/*    */   
/*    */   public EntityLargeFireball(World world) {
/* 10 */     super(world);
/*    */   }
/*    */   
/*    */   public EntityLargeFireball(World world, EntityLiving entityliving, double d0, double d1, double d2) {
/* 14 */     super(world, entityliving, d0, d1, d2);
/*    */   }
/*    */   
/*    */   protected void a(MovingObjectPosition movingobjectposition) {
/* 18 */     if (!this.world.isClientSide) {
/* 19 */       if (movingobjectposition.entity != null) {
/* 20 */         movingobjectposition.entity.damageEntity(DamageSource.fireball(this, this.shooter), 6.0F);
/* 21 */         a(this.shooter, movingobjectposition.entity);
/*    */       }
/*    */       
/* 24 */       boolean flag = this.world.getGameRules().getBoolean("mobGriefing");
/*    */       
/*    */ 
/* 27 */       ExplosionPrimeEvent event = new ExplosionPrimeEvent((org.bukkit.entity.Explosive)org.bukkit.craftbukkit.v1_8_R3.entity.CraftEntity.getEntity(this.world.getServer(), this));
/* 28 */       this.world.getServer().getPluginManager().callEvent(event);
/*    */       
/* 30 */       if (!event.isCancelled())
/*    */       {
/* 32 */         this.world.createExplosion(this, this.locX, this.locY, this.locZ, event.getRadius(), event.getFire(), flag);
/*    */       }
/*    */       
/* 35 */       die();
/*    */     }
/*    */   }
/*    */   
/*    */   public void b(NBTTagCompound nbttagcompound)
/*    */   {
/* 41 */     super.b(nbttagcompound);
/* 42 */     nbttagcompound.setInt("ExplosionPower", this.yield);
/*    */   }
/*    */   
/*    */   public void a(NBTTagCompound nbttagcompound) {
/* 46 */     super.a(nbttagcompound);
/* 47 */     if (nbttagcompound.hasKeyOfType("ExplosionPower", 99))
/*    */     {
/* 49 */       this.bukkitYield = (this.yield = nbttagcompound.getInt("ExplosionPower"));
/*    */     }
/*    */   }
/*    */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_8_R3\EntityLargeFireball.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */
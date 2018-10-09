/*    */ package net.minecraft.server.v1_8_R3;
/*    */ 
/*    */ import org.bukkit.event.entity.ExpBottleEvent;
/*    */ 
/*    */ public class EntityThrownExpBottle extends EntityProjectile {
/*  6 */   public EntityThrownExpBottle(World world) { super(world); }
/*    */   
/*    */   public EntityThrownExpBottle(World world, EntityLiving entityliving)
/*    */   {
/* 10 */     super(world, entityliving);
/*    */   }
/*    */   
/*    */   public EntityThrownExpBottle(World world, double d0, double d1, double d2) {
/* 14 */     super(world, d0, d1, d2);
/*    */   }
/*    */   
/*    */   protected float m() {
/* 18 */     return 0.07F;
/*    */   }
/*    */   
/*    */   protected float j() {
/* 22 */     return 0.7F;
/*    */   }
/*    */   
/*    */   protected float l() {
/* 26 */     return -20.0F;
/*    */   }
/*    */   
/*    */   protected void a(MovingObjectPosition movingobjectposition) {
/* 30 */     if (!this.world.isClientSide)
/*    */     {
/*    */ 
/* 33 */       int i = 3 + this.world.random.nextInt(5) + this.world.random.nextInt(5);
/*    */       
/*    */ 
/* 36 */       ExpBottleEvent event = org.bukkit.craftbukkit.v1_8_R3.event.CraftEventFactory.callExpBottleEvent(this, i);
/* 37 */       i = event.getExperience();
/* 38 */       if (event.getShowEffect()) {
/* 39 */         this.world.triggerEffect(2002, new BlockPosition(this), 0);
/*    */       }
/*    */       
/*    */ 
/* 43 */       while (i > 0) {
/* 44 */         int j = EntityExperienceOrb.getOrbValue(i);
/*    */         
/* 46 */         i -= j;
/* 47 */         this.world.addEntity(new EntityExperienceOrb(this.world, this.locX, this.locY, this.locZ, j));
/*    */       }
/*    */       
/* 50 */       die();
/*    */     }
/*    */   }
/*    */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_8_R3\EntityThrownExpBottle.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */
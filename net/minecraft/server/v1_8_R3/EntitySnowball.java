/*    */ package net.minecraft.server.v1_8_R3;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class EntitySnowball
/*    */   extends EntityProjectile
/*    */ {
/*    */   public EntitySnowball(World paramWorld)
/*    */   {
/* 12 */     super(paramWorld);
/*    */   }
/*    */   
/*    */   public EntitySnowball(World paramWorld, EntityLiving paramEntityLiving) {
/* 16 */     super(paramWorld, paramEntityLiving);
/*    */   }
/*    */   
/*    */   public EntitySnowball(World paramWorld, double paramDouble1, double paramDouble2, double paramDouble3) {
/* 20 */     super(paramWorld, paramDouble1, paramDouble2, paramDouble3);
/*    */   }
/*    */   
/*    */   protected void a(MovingObjectPosition paramMovingObjectPosition)
/*    */   {
/* 25 */     if (paramMovingObjectPosition.entity != null) {
/* 26 */       i = 0;
/* 27 */       if ((paramMovingObjectPosition.entity instanceof EntityBlaze)) {
/* 28 */         i = 3;
/*    */       }
/* 30 */       paramMovingObjectPosition.entity.damageEntity(DamageSource.projectile(this, getShooter()), i);
/*    */     }
/* 32 */     for (int i = 0; i < 8; i++) {
/* 33 */       this.world.addParticle(EnumParticle.SNOWBALL, this.locX, this.locY, this.locZ, 0.0D, 0.0D, 0.0D, new int[0]);
/*    */     }
/* 35 */     if (!this.world.isClientSide) {
/* 36 */       die();
/*    */     }
/*    */   }
/*    */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_8_R3\EntitySnowball.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */
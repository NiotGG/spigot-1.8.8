/*    */ package net.minecraft.server.v1_8_R3;
/*    */ 
/*    */ 
/*    */ 
/*    */ public abstract class EntityFlying
/*    */   extends EntityInsentient
/*    */ {
/*    */   public EntityFlying(World paramWorld)
/*    */   {
/* 10 */     super(paramWorld);
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */   public void e(float paramFloat1, float paramFloat2) {}
/*    */   
/*    */ 
/*    */ 
/*    */   protected void a(double paramDouble, boolean paramBoolean, Block paramBlock, BlockPosition paramBlockPosition) {}
/*    */   
/*    */ 
/*    */ 
/*    */   public void g(float paramFloat1, float paramFloat2)
/*    */   {
/* 25 */     if (V()) {
/* 26 */       a(paramFloat1, paramFloat2, 0.02F);
/* 27 */       move(this.motX, this.motY, this.motZ);
/*    */       
/* 29 */       this.motX *= 0.800000011920929D;
/* 30 */       this.motY *= 0.800000011920929D;
/* 31 */       this.motZ *= 0.800000011920929D;
/* 32 */     } else if (ab()) {
/* 33 */       a(paramFloat1, paramFloat2, 0.02F);
/* 34 */       move(this.motX, this.motY, this.motZ);
/* 35 */       this.motX *= 0.5D;
/* 36 */       this.motY *= 0.5D;
/* 37 */       this.motZ *= 0.5D;
/*    */     } else {
/* 39 */       float f1 = 0.91F;
/* 40 */       if (this.onGround) {
/* 41 */         f1 = this.world.getType(new BlockPosition(MathHelper.floor(this.locX), MathHelper.floor(getBoundingBox().b) - 1, MathHelper.floor(this.locZ))).getBlock().frictionFactor * 0.91F;
/*    */       }
/*    */       
/* 44 */       float f2 = 0.16277136F / (f1 * f1 * f1);
/* 45 */       a(paramFloat1, paramFloat2, this.onGround ? 0.1F * f2 : 0.02F);
/*    */       
/* 47 */       f1 = 0.91F;
/* 48 */       if (this.onGround) {
/* 49 */         f1 = this.world.getType(new BlockPosition(MathHelper.floor(this.locX), MathHelper.floor(getBoundingBox().b) - 1, MathHelper.floor(this.locZ))).getBlock().frictionFactor * 0.91F;
/*    */       }
/*    */       
/* 52 */       move(this.motX, this.motY, this.motZ);
/*    */       
/* 54 */       this.motX *= f1;
/* 55 */       this.motY *= f1;
/* 56 */       this.motZ *= f1;
/*    */     }
/* 58 */     this.aA = this.aB;
/* 59 */     double d1 = this.locX - this.lastX;
/* 60 */     double d2 = this.locZ - this.lastZ;
/* 61 */     float f3 = MathHelper.sqrt(d1 * d1 + d2 * d2) * 4.0F;
/* 62 */     if (f3 > 1.0F) {
/* 63 */       f3 = 1.0F;
/*    */     }
/* 65 */     this.aB += (f3 - this.aB) * 0.4F;
/* 66 */     this.aC += this.aB;
/*    */   }
/*    */   
/*    */   public boolean k_()
/*    */   {
/* 71 */     return false;
/*    */   }
/*    */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_8_R3\EntityFlying.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */
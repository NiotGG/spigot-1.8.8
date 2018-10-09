/*    */ package net.minecraft.server.v1_8_R3;
/*    */ 
/*    */ 
/*    */ public class EntityAIBodyControl
/*    */ {
/*    */   private EntityLiving a;
/*    */   
/*    */   private int b;
/*    */   private float c;
/*    */   
/*    */   public EntityAIBodyControl(EntityLiving paramEntityLiving)
/*    */   {
/* 13 */     this.a = paramEntityLiving;
/*    */   }
/*    */   
/*    */   public void a() {
/* 17 */     double d1 = this.a.locX - this.a.lastX;
/* 18 */     double d2 = this.a.locZ - this.a.lastZ;
/*    */     
/* 20 */     if (d1 * d1 + d2 * d2 > 2.500000277905201E-7D)
/*    */     {
/* 22 */       this.a.aI = this.a.yaw;
/* 23 */       this.a.aK = a(this.a.aI, this.a.aK, 75.0F);
/* 24 */       this.c = this.a.aK;
/* 25 */       this.b = 0;
/* 26 */       return;
/*    */     }
/*    */     
/*    */ 
/* 30 */     float f = 75.0F;
/* 31 */     if (Math.abs(this.a.aK - this.c) > 15.0F) {
/* 32 */       this.b = 0;
/* 33 */       this.c = this.a.aK;
/*    */     } else {
/* 35 */       this.b += 1;
/* 36 */       int i = 10;
/* 37 */       if (this.b > 10) {
/* 38 */         f = Math.max(1.0F - (this.b - 10) / 10.0F, 0.0F) * 75.0F;
/*    */       }
/*    */     }
/*    */     
/* 42 */     this.a.aI = a(this.a.aK, this.a.aI, f);
/*    */   }
/*    */   
/*    */   private float a(float paramFloat1, float paramFloat2, float paramFloat3) {
/* 46 */     float f = MathHelper.g(paramFloat1 - paramFloat2);
/* 47 */     if (f < -paramFloat3) {
/* 48 */       f = -paramFloat3;
/*    */     }
/* 50 */     if (f >= paramFloat3) {
/* 51 */       f = paramFloat3;
/*    */     }
/* 53 */     return paramFloat1 - f;
/*    */   }
/*    */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_8_R3\EntityAIBodyControl.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */
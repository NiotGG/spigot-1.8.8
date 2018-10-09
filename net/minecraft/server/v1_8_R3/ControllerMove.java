/*    */ package net.minecraft.server.v1_8_R3;
/*    */ 
/*    */ 
/*    */ public class ControllerMove
/*    */ {
/*    */   protected EntityInsentient a;
/*    */   
/*    */   protected double b;
/*    */   
/*    */   protected double c;
/*    */   
/*    */   protected double d;
/*    */   
/*    */   protected double e;
/*    */   
/*    */   protected boolean f;
/*    */   
/*    */ 
/*    */   public ControllerMove(EntityInsentient paramEntityInsentient)
/*    */   {
/* 21 */     this.a = paramEntityInsentient;
/* 22 */     this.b = paramEntityInsentient.locX;
/* 23 */     this.c = paramEntityInsentient.locY;
/* 24 */     this.d = paramEntityInsentient.locZ;
/*    */   }
/*    */   
/*    */   public boolean a() {
/* 28 */     return this.f;
/*    */   }
/*    */   
/*    */   public double b() {
/* 32 */     return this.e;
/*    */   }
/*    */   
/*    */   public void a(double paramDouble1, double paramDouble2, double paramDouble3, double paramDouble4) {
/* 36 */     this.b = paramDouble1;
/* 37 */     this.c = paramDouble2;
/* 38 */     this.d = paramDouble3;
/* 39 */     this.e = paramDouble4;
/* 40 */     this.f = true;
/*    */   }
/*    */   
/*    */   public void c() {
/* 44 */     this.a.n(0.0F);
/* 45 */     if (!this.f) {
/* 46 */       return;
/*    */     }
/* 48 */     this.f = false;
/*    */     
/* 50 */     int i = MathHelper.floor(this.a.getBoundingBox().b + 0.5D);
/*    */     
/* 52 */     double d1 = this.b - this.a.locX;
/* 53 */     double d2 = this.d - this.a.locZ;
/* 54 */     double d3 = this.c - i;
/* 55 */     double d4 = d1 * d1 + d3 * d3 + d2 * d2;
/* 56 */     if (d4 < 2.500000277905201E-7D) {
/* 57 */       return;
/*    */     }
/*    */     
/* 60 */     float f1 = (float)(MathHelper.b(d2, d1) * 180.0D / 3.1415927410125732D) - 90.0F;
/*    */     
/* 62 */     this.a.yaw = a(this.a.yaw, f1, 30.0F);
/* 63 */     this.a.k((float)(this.e * this.a.getAttributeInstance(GenericAttributes.MOVEMENT_SPEED).getValue()));
/*    */     
/* 65 */     if ((d3 > 0.0D) && (d1 * d1 + d2 * d2 < 1.0D)) {
/* 66 */       this.a.getControllerJump().a();
/*    */     }
/*    */   }
/*    */   
/*    */   protected float a(float paramFloat1, float paramFloat2, float paramFloat3) {
/* 71 */     float f1 = MathHelper.g(paramFloat2 - paramFloat1);
/* 72 */     if (f1 > paramFloat3) {
/* 73 */       f1 = paramFloat3;
/*    */     }
/* 75 */     if (f1 < -paramFloat3) {
/* 76 */       f1 = -paramFloat3;
/*    */     }
/* 78 */     float f2 = paramFloat1 + f1;
/* 79 */     if (f2 < 0.0F) {
/* 80 */       f2 += 360.0F;
/* 81 */     } else if (f2 > 360.0F) {
/* 82 */       f2 -= 360.0F;
/*    */     }
/* 84 */     return f2;
/*    */   }
/*    */   
/*    */   public double d() {
/* 88 */     return this.b;
/*    */   }
/*    */   
/*    */   public double e() {
/* 92 */     return this.c;
/*    */   }
/*    */   
/*    */   public double f() {
/* 96 */     return this.d;
/*    */   }
/*    */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_8_R3\ControllerMove.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */
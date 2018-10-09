/*    */ package net.minecraft.server.v1_8_R3;
/*    */ 
/*    */ import java.util.Random;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public abstract class PathfinderGoalGotoTarget
/*    */   extends PathfinderGoal
/*    */ {
/*    */   private final EntityCreature c;
/*    */   private final double d;
/*    */   protected int a;
/*    */   private int e;
/*    */   private int f;
/* 19 */   protected BlockPosition b = BlockPosition.ZERO;
/*    */   private boolean g;
/*    */   private int h;
/*    */   
/*    */   public PathfinderGoalGotoTarget(EntityCreature paramEntityCreature, double paramDouble, int paramInt)
/*    */   {
/* 25 */     this.c = paramEntityCreature;
/* 26 */     this.d = paramDouble;
/* 27 */     this.h = paramInt;
/* 28 */     a(5);
/*    */   }
/*    */   
/*    */   public boolean a()
/*    */   {
/* 33 */     if (this.a > 0) {
/* 34 */       this.a -= 1;
/* 35 */       return false;
/*    */     }
/* 37 */     this.a = (200 + this.c.bc().nextInt(200));
/* 38 */     return g();
/*    */   }
/*    */   
/*    */   public boolean b()
/*    */   {
/* 43 */     return (this.e >= -this.f) && (this.e <= 1200) && (a(this.c.world, this.b));
/*    */   }
/*    */   
/*    */   public void c()
/*    */   {
/* 48 */     this.c.getNavigation().a(this.b.getX() + 0.5D, this.b.getY() + 1, this.b.getZ() + 0.5D, this.d);
/* 49 */     this.e = 0;
/* 50 */     this.f = (this.c.bc().nextInt(this.c.bc().nextInt(1200) + 1200) + 1200);
/*    */   }
/*    */   
/*    */ 
/*    */   public void d() {}
/*    */   
/*    */ 
/*    */   public void e()
/*    */   {
/* 59 */     if (this.c.c(this.b.up()) > 1.0D) {
/* 60 */       this.g = false;
/* 61 */       this.e += 1;
/* 62 */       if (this.e % 40 == 0) {
/* 63 */         this.c.getNavigation().a(this.b.getX() + 0.5D, this.b.getY() + 1, this.b.getZ() + 0.5D, this.d);
/*    */       }
/*    */     } else {
/* 66 */       this.g = true;
/* 67 */       this.e -= 1;
/*    */     }
/*    */   }
/*    */   
/*    */   protected boolean f() {
/* 72 */     return this.g;
/*    */   }
/*    */   
/*    */   private boolean g() {
/* 76 */     int i = this.h;
/* 77 */     int j = 1;
/* 78 */     BlockPosition localBlockPosition1 = new BlockPosition(this.c);
/*    */     
/* 80 */     for (int k = 0; k <= 1; k = k > 0 ? -k : 1 - k) {
/* 81 */       for (int m = 0; m < i; m++) {
/* 82 */         for (int n = 0; n <= m; n = n > 0 ? -n : 1 - n)
/*    */         {
/* 84 */           for (int i1 = (n < m) && (n > -m) ? m : 0; 
/* 85 */               i1 <= m; i1 = i1 > 0 ? -i1 : 1 - i1) {
/* 86 */             BlockPosition localBlockPosition2 = localBlockPosition1.a(n, k - 1, i1);
/* 87 */             if ((this.c.e(localBlockPosition2)) && (a(this.c.world, localBlockPosition2))) {
/* 88 */               this.b = localBlockPosition2;
/* 89 */               return true;
/*    */             }
/*    */           }
/*    */         }
/*    */       }
/*    */     }
/*    */     
/* 96 */     return false;
/*    */   }
/*    */   
/*    */   protected abstract boolean a(World paramWorld, BlockPosition paramBlockPosition);
/*    */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_8_R3\PathfinderGoalGotoTarget.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */
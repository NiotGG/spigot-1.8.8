/*    */ package net.minecraft.server.v1_8_R3;
/*    */ 
/*    */ public class PathPoint
/*    */ {
/*    */   public final int a;
/*    */   public final int b;
/*    */   public final int c;
/*    */   private final int j;
/*  9 */   int d = -1;
/*    */   float e;
/*    */   float f;
/*    */   
/*    */   public PathPoint(int paramInt1, int paramInt2, int paramInt3)
/*    */   {
/* 15 */     this.a = paramInt1;
/* 16 */     this.b = paramInt2;
/* 17 */     this.c = paramInt3;
/*    */     
/* 19 */     this.j = a(paramInt1, paramInt2, paramInt3); }
/*    */   
/*    */   float g;
/*    */   
/* 23 */   public static int a(int paramInt1, int paramInt2, int paramInt3) { return paramInt2 & 0xFF | (paramInt1 & 0x7FFF) << 8 | (paramInt3 & 0x7FFF) << 24 | (paramInt1 < 0 ? Integer.MIN_VALUE : 0) | (paramInt3 < 0 ? 32768 : 0); }
/*    */   
/*    */   PathPoint h;
/*    */   public boolean i;
/* 27 */   public float a(PathPoint paramPathPoint) { float f1 = paramPathPoint.a - this.a;
/* 28 */     float f2 = paramPathPoint.b - this.b;
/* 29 */     float f3 = paramPathPoint.c - this.c;
/* 30 */     return MathHelper.c(f1 * f1 + f2 * f2 + f3 * f3);
/*    */   }
/*    */   
/*    */   public float b(PathPoint paramPathPoint) {
/* 34 */     float f1 = paramPathPoint.a - this.a;
/* 35 */     float f2 = paramPathPoint.b - this.b;
/* 36 */     float f3 = paramPathPoint.c - this.c;
/* 37 */     return f1 * f1 + f2 * f2 + f3 * f3;
/*    */   }
/*    */   
/*    */   public boolean equals(Object paramObject) {
/* 41 */     if ((paramObject instanceof PathPoint)) {
/* 42 */       PathPoint localPathPoint = (PathPoint)paramObject;
/* 43 */       return (this.j == localPathPoint.j) && (this.a == localPathPoint.a) && (this.b == localPathPoint.b) && (this.c == localPathPoint.c);
/*    */     }
/* 45 */     return false;
/*    */   }
/*    */   
/*    */   public int hashCode() {
/* 49 */     return this.j;
/*    */   }
/*    */   
/*    */   public boolean a() {
/* 53 */     return this.d >= 0;
/*    */   }
/*    */   
/*    */   public String toString() {
/* 57 */     return this.a + ", " + this.b + ", " + this.c;
/*    */   }
/*    */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_8_R3\PathPoint.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */
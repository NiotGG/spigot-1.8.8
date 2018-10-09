/*    */ package net.minecraft.server.v1_8_R3;
/*    */ 
/*    */ 
/*    */ public class PathEntity
/*    */ {
/*    */   private final PathPoint[] a;
/*    */   private int b;
/*    */   private int c;
/*    */   
/*    */   public PathEntity(PathPoint[] paramArrayOfPathPoint)
/*    */   {
/* 12 */     this.a = paramArrayOfPathPoint;
/* 13 */     this.c = paramArrayOfPathPoint.length;
/*    */   }
/*    */   
/*    */   public void a() {
/* 17 */     this.b += 1;
/*    */   }
/*    */   
/*    */   public boolean b() {
/* 21 */     return this.b >= this.c;
/*    */   }
/*    */   
/*    */   public PathPoint c() {
/* 25 */     if (this.c > 0) {
/* 26 */       return this.a[(this.c - 1)];
/*    */     }
/* 28 */     return null;
/*    */   }
/*    */   
/*    */   public PathPoint a(int paramInt) {
/* 32 */     return this.a[paramInt];
/*    */   }
/*    */   
/*    */   public int d() {
/* 36 */     return this.c;
/*    */   }
/*    */   
/*    */   public void b(int paramInt) {
/* 40 */     this.c = paramInt;
/*    */   }
/*    */   
/*    */   public int e() {
/* 44 */     return this.b;
/*    */   }
/*    */   
/*    */   public void c(int paramInt) {
/* 48 */     this.b = paramInt;
/*    */   }
/*    */   
/*    */   public Vec3D a(Entity paramEntity, int paramInt) {
/* 52 */     double d1 = this.a[paramInt].a + (int)(paramEntity.width + 1.0F) * 0.5D;
/* 53 */     double d2 = this.a[paramInt].b;
/* 54 */     double d3 = this.a[paramInt].c + (int)(paramEntity.width + 1.0F) * 0.5D;
/* 55 */     return new Vec3D(d1, d2, d3);
/*    */   }
/*    */   
/*    */   public Vec3D a(Entity paramEntity) {
/* 59 */     return a(paramEntity, this.b);
/*    */   }
/*    */   
/*    */   public boolean a(PathEntity paramPathEntity) {
/* 63 */     if (paramPathEntity == null) {
/* 64 */       return false;
/*    */     }
/* 66 */     if (paramPathEntity.a.length != this.a.length) {
/* 67 */       return false;
/*    */     }
/* 69 */     for (int i = 0; i < this.a.length; i++) {
/* 70 */       if ((this.a[i].a != paramPathEntity.a[i].a) || (this.a[i].b != paramPathEntity.a[i].b) || (this.a[i].c != paramPathEntity.a[i].c)) {
/* 71 */         return false;
/*    */       }
/*    */     }
/* 74 */     return true;
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   public boolean b(Vec3D paramVec3D)
/*    */   {
/* 86 */     PathPoint localPathPoint = c();
/* 87 */     if (localPathPoint == null) {
/* 88 */       return false;
/*    */     }
/* 90 */     return (localPathPoint.a == (int)paramVec3D.a) && (localPathPoint.c == (int)paramVec3D.c);
/*    */   }
/*    */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_8_R3\PathEntity.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */
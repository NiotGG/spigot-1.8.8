/*     */ package net.minecraft.server.v1_8_R3;
/*     */ 
/*     */ public class Path
/*     */ {
/*   5 */   private PathPoint[] a = new PathPoint[''];
/*     */   
/*     */   private int b;
/*     */   
/*     */   public PathPoint a(PathPoint pathpoint)
/*     */   {
/*  11 */     if (pathpoint.d >= 0) {
/*  12 */       throw new IllegalStateException("OW KNOWS!");
/*     */     }
/*  14 */     if (this.b == this.a.length) {
/*  15 */       PathPoint[] apathpoint = new PathPoint[this.b << 1];
/*     */       
/*  17 */       System.arraycopy(this.a, 0, apathpoint, 0, this.b);
/*  18 */       this.a = apathpoint;
/*     */     }
/*     */     
/*  21 */     this.a[this.b] = pathpoint;
/*  22 */     pathpoint.d = this.b;
/*  23 */     a(this.b++);
/*  24 */     return pathpoint;
/*     */   }
/*     */   
/*     */   public void a()
/*     */   {
/*  29 */     this.b = 0;
/*     */   }
/*     */   
/*     */   public PathPoint c() {
/*  33 */     PathPoint pathpoint = this.a[0];
/*     */     
/*  35 */     this.a[0] = this.a[(--this.b)];
/*  36 */     this.a[this.b] = null;
/*  37 */     if (this.b > 0) {
/*  38 */       b(0);
/*     */     }
/*     */     
/*  41 */     pathpoint.d = -1;
/*  42 */     return pathpoint;
/*     */   }
/*     */   
/*     */   public void a(PathPoint pathpoint, float f) {
/*  46 */     float f1 = pathpoint.g;
/*     */     
/*  48 */     pathpoint.g = f;
/*  49 */     if (f < f1) {
/*  50 */       a(pathpoint.d);
/*     */     } else {
/*  52 */       b(pathpoint.d);
/*     */     }
/*     */   }
/*     */   
/*     */   private void a(int i)
/*     */   {
/*  58 */     PathPoint pathpoint = this.a[i];
/*     */     
/*     */     int j;
/*     */     
/*  62 */     for (float f = pathpoint.g; i > 0; i = j) {
/*  63 */       j = i - 1 >> 1;
/*  64 */       PathPoint pathpoint1 = this.a[j];
/*     */       
/*  66 */       if (f >= pathpoint1.g) {
/*     */         break;
/*     */       }
/*     */       
/*  70 */       this.a[i] = pathpoint1;
/*  71 */       pathpoint1.d = i;
/*     */     }
/*     */     
/*  74 */     this.a[i] = pathpoint;
/*  75 */     pathpoint.d = i;
/*     */   }
/*     */   
/*     */   private void b(int i) {
/*  79 */     PathPoint pathpoint = this.a[i];
/*  80 */     float f = pathpoint.g;
/*     */     for (;;)
/*     */     {
/*  83 */       int j = 1 + (i << 1);
/*  84 */       int k = j + 1;
/*     */       
/*  86 */       if (j >= this.b) {
/*     */         break;
/*     */       }
/*     */       
/*  90 */       PathPoint pathpoint1 = this.a[j];
/*  91 */       float f1 = pathpoint1.g;
/*     */       float f2;
/*     */       PathPoint pathpoint2;
/*     */       float f2;
/*  95 */       if (k >= this.b) {
/*  96 */         PathPoint pathpoint2 = null;
/*  97 */         f2 = Float.POSITIVE_INFINITY;
/*     */       } else {
/*  99 */         pathpoint2 = this.a[k];
/* 100 */         f2 = pathpoint2.g;
/*     */       }
/*     */       
/* 103 */       if (f1 < f2) {
/* 104 */         if (f1 >= f) {
/*     */           break;
/*     */         }
/*     */         
/* 108 */         this.a[i] = pathpoint1;
/* 109 */         pathpoint1.d = i;
/* 110 */         i = j;
/*     */       } else {
/* 112 */         if (f2 >= f) {
/*     */           break;
/*     */         }
/*     */         
/* 116 */         this.a[i] = pathpoint2;
/* 117 */         pathpoint2.d = i;
/* 118 */         i = k;
/*     */       }
/*     */     }
/*     */     
/* 122 */     this.a[i] = pathpoint;
/* 123 */     pathpoint.d = i;
/*     */   }
/*     */   
/*     */   public boolean e() {
/* 127 */     return this.b == 0;
/*     */   }
/*     */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_8_R3\Path.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */
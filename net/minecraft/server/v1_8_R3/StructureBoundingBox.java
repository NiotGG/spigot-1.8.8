/*     */ package net.minecraft.server.v1_8_R3;
/*     */ 
/*     */ import com.google.common.base.Objects;
/*     */ import com.google.common.base.Objects.ToStringHelper;
/*     */ 
/*     */ 
/*     */ 
/*     */ public class StructureBoundingBox
/*     */ {
/*     */   public int a;
/*     */   public int b;
/*     */   public int c;
/*     */   public int d;
/*     */   public int e;
/*     */   public int f;
/*     */   
/*     */   public StructureBoundingBox() {}
/*     */   
/*     */   public StructureBoundingBox(int[] paramArrayOfInt)
/*     */   {
/*  21 */     if (paramArrayOfInt.length == 6) {
/*  22 */       this.a = paramArrayOfInt[0];
/*  23 */       this.b = paramArrayOfInt[1];
/*  24 */       this.c = paramArrayOfInt[2];
/*  25 */       this.d = paramArrayOfInt[3];
/*  26 */       this.e = paramArrayOfInt[4];
/*  27 */       this.f = paramArrayOfInt[5];
/*     */     }
/*     */   }
/*     */   
/*     */   public static StructureBoundingBox a() {
/*  32 */     return new StructureBoundingBox(Integer.MAX_VALUE, Integer.MAX_VALUE, Integer.MAX_VALUE, Integer.MIN_VALUE, Integer.MIN_VALUE, Integer.MIN_VALUE);
/*     */   }
/*     */   
/*     */   public static StructureBoundingBox a(int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5, int paramInt6, int paramInt7, int paramInt8, int paramInt9, EnumDirection paramEnumDirection) {
/*  36 */     switch (1.a[paramEnumDirection.ordinal()]) {
/*     */     default: 
/*  38 */       return new StructureBoundingBox(paramInt1 + paramInt4, paramInt2 + paramInt5, paramInt3 + paramInt6, paramInt1 + paramInt7 - 1 + paramInt4, paramInt2 + paramInt8 - 1 + paramInt5, paramInt3 + paramInt9 - 1 + paramInt6);
/*     */     
/*     */     case 1: 
/*  41 */       return new StructureBoundingBox(paramInt1 + paramInt4, paramInt2 + paramInt5, paramInt3 - paramInt9 + 1 + paramInt6, paramInt1 + paramInt7 - 1 + paramInt4, paramInt2 + paramInt8 - 1 + paramInt5, paramInt3 + paramInt6);
/*     */     
/*     */     case 2: 
/*  44 */       return new StructureBoundingBox(paramInt1 + paramInt4, paramInt2 + paramInt5, paramInt3 + paramInt6, paramInt1 + paramInt7 - 1 + paramInt4, paramInt2 + paramInt8 - 1 + paramInt5, paramInt3 + paramInt9 - 1 + paramInt6);
/*     */     
/*     */     case 3: 
/*  47 */       return new StructureBoundingBox(paramInt1 - paramInt9 + 1 + paramInt6, paramInt2 + paramInt5, paramInt3 + paramInt4, paramInt1 + paramInt6, paramInt2 + paramInt8 - 1 + paramInt5, paramInt3 + paramInt7 - 1 + paramInt4);
/*     */     }
/*     */     
/*  50 */     return new StructureBoundingBox(paramInt1 + paramInt6, paramInt2 + paramInt5, paramInt3 + paramInt4, paramInt1 + paramInt9 - 1 + paramInt6, paramInt2 + paramInt8 - 1 + paramInt5, paramInt3 + paramInt7 - 1 + paramInt4);
/*     */   }
/*     */   
/*     */   public static StructureBoundingBox a(int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5, int paramInt6)
/*     */   {
/*  55 */     return new StructureBoundingBox(Math.min(paramInt1, paramInt4), Math.min(paramInt2, paramInt5), Math.min(paramInt3, paramInt6), Math.max(paramInt1, paramInt4), Math.max(paramInt2, paramInt5), Math.max(paramInt3, paramInt6));
/*     */   }
/*     */   
/*     */   public StructureBoundingBox(StructureBoundingBox paramStructureBoundingBox) {
/*  59 */     this.a = paramStructureBoundingBox.a;
/*  60 */     this.b = paramStructureBoundingBox.b;
/*  61 */     this.c = paramStructureBoundingBox.c;
/*  62 */     this.d = paramStructureBoundingBox.d;
/*  63 */     this.e = paramStructureBoundingBox.e;
/*  64 */     this.f = paramStructureBoundingBox.f;
/*     */   }
/*     */   
/*     */   public StructureBoundingBox(int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5, int paramInt6) {
/*  68 */     this.a = paramInt1;
/*  69 */     this.b = paramInt2;
/*  70 */     this.c = paramInt3;
/*  71 */     this.d = paramInt4;
/*  72 */     this.e = paramInt5;
/*  73 */     this.f = paramInt6;
/*     */   }
/*     */   
/*     */   public StructureBoundingBox(BaseBlockPosition paramBaseBlockPosition1, BaseBlockPosition paramBaseBlockPosition2) {
/*  77 */     this.a = Math.min(paramBaseBlockPosition1.getX(), paramBaseBlockPosition2.getX());
/*  78 */     this.b = Math.min(paramBaseBlockPosition1.getY(), paramBaseBlockPosition2.getY());
/*  79 */     this.c = Math.min(paramBaseBlockPosition1.getZ(), paramBaseBlockPosition2.getZ());
/*  80 */     this.d = Math.max(paramBaseBlockPosition1.getX(), paramBaseBlockPosition2.getX());
/*  81 */     this.e = Math.max(paramBaseBlockPosition1.getY(), paramBaseBlockPosition2.getY());
/*  82 */     this.f = Math.max(paramBaseBlockPosition1.getZ(), paramBaseBlockPosition2.getZ());
/*     */   }
/*     */   
/*     */   public StructureBoundingBox(int paramInt1, int paramInt2, int paramInt3, int paramInt4) {
/*  86 */     this.a = paramInt1;
/*  87 */     this.c = paramInt2;
/*  88 */     this.d = paramInt3;
/*  89 */     this.f = paramInt4;
/*     */     
/*     */ 
/*  92 */     this.b = 1;
/*  93 */     this.e = 512;
/*     */   }
/*     */   
/*     */   public boolean a(StructureBoundingBox paramStructureBoundingBox) {
/*  97 */     return (this.d >= paramStructureBoundingBox.a) && (this.a <= paramStructureBoundingBox.d) && (this.f >= paramStructureBoundingBox.c) && (this.c <= paramStructureBoundingBox.f) && (this.e >= paramStructureBoundingBox.b) && (this.b <= paramStructureBoundingBox.e);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public boolean a(int paramInt1, int paramInt2, int paramInt3, int paramInt4)
/*     */   {
/* 109 */     return (this.d >= paramInt1) && (this.a <= paramInt3) && (this.f >= paramInt2) && (this.c <= paramInt4);
/*     */   }
/*     */   
/*     */   public void b(StructureBoundingBox paramStructureBoundingBox) {
/* 113 */     this.a = Math.min(this.a, paramStructureBoundingBox.a);
/* 114 */     this.b = Math.min(this.b, paramStructureBoundingBox.b);
/* 115 */     this.c = Math.min(this.c, paramStructureBoundingBox.c);
/* 116 */     this.d = Math.max(this.d, paramStructureBoundingBox.d);
/* 117 */     this.e = Math.max(this.e, paramStructureBoundingBox.e);
/* 118 */     this.f = Math.max(this.f, paramStructureBoundingBox.f);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public void a(int paramInt1, int paramInt2, int paramInt3)
/*     */   {
/* 137 */     this.a += paramInt1;
/* 138 */     this.b += paramInt2;
/* 139 */     this.c += paramInt3;
/* 140 */     this.d += paramInt1;
/* 141 */     this.e += paramInt2;
/* 142 */     this.f += paramInt3;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public boolean b(BaseBlockPosition paramBaseBlockPosition)
/*     */   {
/* 155 */     return (paramBaseBlockPosition.getX() >= this.a) && (paramBaseBlockPosition.getX() <= this.d) && (paramBaseBlockPosition.getZ() >= this.c) && (paramBaseBlockPosition.getZ() <= this.f) && (paramBaseBlockPosition.getY() >= this.b) && (paramBaseBlockPosition.getY() <= this.e);
/*     */   }
/*     */   
/*     */   public BaseBlockPosition b() {
/* 159 */     return new BaseBlockPosition(this.d - this.a, this.e - this.b, this.f - this.c);
/*     */   }
/*     */   
/*     */   public int c() {
/* 163 */     return this.d - this.a + 1;
/*     */   }
/*     */   
/*     */   public int d() {
/* 167 */     return this.e - this.b + 1;
/*     */   }
/*     */   
/*     */   public int e() {
/* 171 */     return this.f - this.c + 1;
/*     */   }
/*     */   
/*     */   public BaseBlockPosition f() {
/* 175 */     return new BlockPosition(this.a + (this.d - this.a + 1) / 2, this.b + (this.e - this.b + 1) / 2, this.c + (this.f - this.c + 1) / 2);
/*     */   }
/*     */   
/*     */   public String toString()
/*     */   {
/* 180 */     return Objects.toStringHelper(this).add("x0", this.a).add("y0", this.b).add("z0", this.c).add("x1", this.d).add("y1", this.e).add("z1", this.f).toString();
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public NBTTagIntArray g()
/*     */   {
/* 191 */     return new NBTTagIntArray(new int[] { this.a, this.b, this.c, this.d, this.e, this.f });
/*     */   }
/*     */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_8_R3\StructureBoundingBox.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */
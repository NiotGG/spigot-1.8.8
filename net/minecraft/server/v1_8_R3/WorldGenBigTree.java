/*     */ package net.minecraft.server.v1_8_R3;
/*     */ 
/*     */ import com.google.common.collect.Lists;
/*     */ import java.util.List;
/*     */ import java.util.Random;
/*     */ 
/*     */ 
/*     */ 
/*     */ public class WorldGenBigTree
/*     */   extends WorldGenTreeAbstract
/*     */ {
/*     */   private Random k;
/*     */   private World l;
/*     */   
/*     */   static class Position
/*     */     extends BlockPosition
/*     */   {
/*     */     private final int c;
/*     */     
/*     */     public Position(BlockPosition paramBlockPosition, int paramInt)
/*     */     {
/*  22 */       super(paramBlockPosition.getY(), paramBlockPosition.getZ());
/*  23 */       this.c = paramInt;
/*     */     }
/*     */     
/*     */     public int q() {
/*  27 */       return this.c;
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*  33 */   private BlockPosition m = BlockPosition.ZERO;
/*     */   
/*     */   int a;
/*     */   int b;
/*  37 */   double c = 0.618D;
/*  38 */   double d = 0.381D;
/*  39 */   double e = 1.0D;
/*  40 */   double f = 1.0D;
/*  41 */   int g = 1;
/*  42 */   int h = 12;
/*  43 */   int i = 4;
/*     */   List<Position> j;
/*     */   
/*     */   public WorldGenBigTree(boolean paramBoolean)
/*     */   {
/*  48 */     super(paramBoolean);
/*     */   }
/*     */   
/*     */   void a() {
/*  52 */     this.b = ((int)(this.a * this.c));
/*  53 */     if (this.b >= this.a) {
/*  54 */       this.b = (this.a - 1);
/*     */     }
/*     */     
/*     */ 
/*  58 */     int n = (int)(1.382D + Math.pow(this.f * this.a / 13.0D, 2.0D));
/*  59 */     if (n < 1) {
/*  60 */       n = 1;
/*     */     }
/*     */     
/*  63 */     int i1 = this.m.getY() + this.b;
/*  64 */     int i2 = this.a - this.i;
/*     */     
/*  66 */     this.j = Lists.newArrayList();
/*  67 */     this.j.add(new Position(this.m.up(i2), i1));
/*  69 */     for (; 
/*  69 */         i2 >= 0; i2--) {
/*  70 */       float f1 = a(i2);
/*  71 */       if (f1 >= 0.0F)
/*     */       {
/*     */ 
/*     */ 
/*  75 */         for (int i3 = 0; i3 < n; i3++) {
/*  76 */           double d1 = this.e * f1 * (this.k.nextFloat() + 0.328D);
/*  77 */           double d2 = this.k.nextFloat() * 2.0F * 3.141592653589793D;
/*     */           
/*  79 */           double d3 = d1 * Math.sin(d2) + 0.5D;
/*  80 */           double d4 = d1 * Math.cos(d2) + 0.5D;
/*     */           
/*  82 */           BlockPosition localBlockPosition1 = this.m.a(d3, i2 - 1, d4);
/*  83 */           BlockPosition localBlockPosition2 = localBlockPosition1.up(this.i);
/*     */           
/*     */ 
/*  86 */           if (a(localBlockPosition1, localBlockPosition2) == -1)
/*     */           {
/*  88 */             int i4 = this.m.getX() - localBlockPosition1.getX();
/*  89 */             int i5 = this.m.getZ() - localBlockPosition1.getZ();
/*     */             
/*  91 */             double d5 = localBlockPosition1.getY() - Math.sqrt(i4 * i4 + i5 * i5) * this.d;
/*  92 */             int i6 = d5 > i1 ? i1 : (int)d5;
/*  93 */             BlockPosition localBlockPosition3 = new BlockPosition(this.m.getX(), i6, this.m.getZ());
/*     */             
/*     */ 
/*  96 */             if (a(localBlockPosition3, localBlockPosition1) == -1)
/*     */             {
/*  98 */               this.j.add(new Position(localBlockPosition1, localBlockPosition3.getY()));
/*     */             }
/*     */           }
/*     */         }
/*     */       }
/*     */     }
/*     */   }
/*     */   
/*     */   void a(BlockPosition paramBlockPosition, float paramFloat, IBlockData paramIBlockData) {
/* 107 */     int n = (int)(paramFloat + 0.618D);
/*     */     
/* 109 */     for (int i1 = -n; i1 <= n; i1++) {
/* 110 */       for (int i2 = -n; i2 <= n; i2++) {
/* 111 */         if (Math.pow(Math.abs(i1) + 0.5D, 2.0D) + Math.pow(Math.abs(i2) + 0.5D, 2.0D) <= paramFloat * paramFloat) {
/* 112 */           BlockPosition localBlockPosition = paramBlockPosition.a(i1, 0, i2);
/*     */           
/*     */ 
/*     */ 
/* 116 */           Material localMaterial = this.l.getType(localBlockPosition).getBlock().getMaterial();
/* 117 */           if ((localMaterial == Material.AIR) || (localMaterial == Material.LEAVES)) {
/* 118 */             a(this.l, localBlockPosition, paramIBlockData);
/*     */           }
/*     */         }
/*     */       }
/*     */     }
/*     */   }
/*     */   
/*     */   float a(int paramInt)
/*     */   {
/* 127 */     if (paramInt < this.a * 0.3F) {
/* 128 */       return -1.0F;
/*     */     }
/*     */     
/* 131 */     float f1 = this.a / 2.0F;
/* 132 */     float f2 = f1 - paramInt;
/*     */     
/* 134 */     float f3 = MathHelper.c(f1 * f1 - f2 * f2);
/* 135 */     if (f2 == 0.0F) {
/* 136 */       f3 = f1;
/* 137 */     } else if (Math.abs(f2) >= f1) {
/* 138 */       return 0.0F;
/*     */     }
/*     */     
/* 141 */     return f3 * 0.5F;
/*     */   }
/*     */   
/*     */   float b(int paramInt) {
/* 145 */     if ((paramInt < 0) || (paramInt >= this.i)) {
/* 146 */       return -1.0F;
/*     */     }
/* 148 */     if ((paramInt == 0) || (paramInt == this.i - 1)) {
/* 149 */       return 2.0F;
/*     */     }
/*     */     
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/* 158 */     return 3.0F;
/*     */   }
/*     */   
/*     */   void a(BlockPosition paramBlockPosition)
/*     */   {
/* 163 */     for (int n = 0; n < this.i; n++) {
/* 164 */       a(paramBlockPosition.up(n), b(n), Blocks.LEAVES.getBlockData().set(BlockLeaves.CHECK_DECAY, Boolean.valueOf(false)));
/*     */     }
/*     */   }
/*     */   
/*     */   void a(BlockPosition paramBlockPosition1, BlockPosition paramBlockPosition2, Block paramBlock) {
/* 169 */     BlockPosition localBlockPosition1 = paramBlockPosition2.a(-paramBlockPosition1.getX(), -paramBlockPosition1.getY(), -paramBlockPosition1.getZ());
/*     */     
/* 171 */     int n = b(localBlockPosition1);
/*     */     
/* 173 */     float f1 = localBlockPosition1.getX() / n;
/* 174 */     float f2 = localBlockPosition1.getY() / n;
/* 175 */     float f3 = localBlockPosition1.getZ() / n;
/*     */     
/* 177 */     for (int i1 = 0; i1 <= n; i1++) {
/* 178 */       BlockPosition localBlockPosition2 = paramBlockPosition1.a(0.5F + i1 * f1, 0.5F + i1 * f2, 0.5F + i1 * f3);
/* 179 */       BlockLogAbstract.EnumLogRotation localEnumLogRotation = b(paramBlockPosition1, localBlockPosition2);
/*     */       
/* 181 */       a(this.l, localBlockPosition2, paramBlock.getBlockData().set(BlockLogAbstract.AXIS, localEnumLogRotation));
/*     */     }
/*     */   }
/*     */   
/*     */   private int b(BlockPosition paramBlockPosition) {
/* 186 */     int n = MathHelper.a(paramBlockPosition.getX());
/* 187 */     int i1 = MathHelper.a(paramBlockPosition.getY());
/* 188 */     int i2 = MathHelper.a(paramBlockPosition.getZ());
/*     */     
/* 190 */     if ((i2 > n) && (i2 > i1))
/* 191 */       return i2;
/* 192 */     if (i1 > n) {
/* 193 */       return i1;
/*     */     }
/*     */     
/* 196 */     return n;
/*     */   }
/*     */   
/*     */   private BlockLogAbstract.EnumLogRotation b(BlockPosition paramBlockPosition1, BlockPosition paramBlockPosition2) {
/* 200 */     BlockLogAbstract.EnumLogRotation localEnumLogRotation = BlockLogAbstract.EnumLogRotation.Y;
/* 201 */     int n = Math.abs(paramBlockPosition2.getX() - paramBlockPosition1.getX());
/* 202 */     int i1 = Math.abs(paramBlockPosition2.getZ() - paramBlockPosition1.getZ());
/* 203 */     int i2 = Math.max(n, i1);
/*     */     
/* 205 */     if (i2 > 0) {
/* 206 */       if (n == i2) {
/* 207 */         localEnumLogRotation = BlockLogAbstract.EnumLogRotation.X;
/* 208 */       } else if (i1 == i2) {
/* 209 */         localEnumLogRotation = BlockLogAbstract.EnumLogRotation.Z;
/*     */       }
/*     */     }
/* 212 */     return localEnumLogRotation;
/*     */   }
/*     */   
/*     */   void b()
/*     */   {
/* 217 */     for (Position localPosition : this.j) {
/* 218 */       a(localPosition);
/*     */     }
/*     */   }
/*     */   
/*     */   boolean c(int paramInt) {
/* 223 */     return paramInt >= this.a * 0.2D;
/*     */   }
/*     */   
/*     */   void c()
/*     */   {
/* 228 */     BlockPosition localBlockPosition1 = this.m;
/* 229 */     BlockPosition localBlockPosition2 = this.m.up(this.b);
/* 230 */     Block localBlock = Blocks.LOG;
/*     */     
/* 232 */     a(localBlockPosition1, localBlockPosition2, localBlock);
/* 233 */     if (this.g == 2) {
/* 234 */       a(localBlockPosition1.east(), localBlockPosition2.east(), localBlock);
/* 235 */       a(localBlockPosition1.east().south(), localBlockPosition2.east().south(), localBlock);
/* 236 */       a(localBlockPosition1.south(), localBlockPosition2.south(), localBlock);
/*     */     }
/*     */   }
/*     */   
/*     */   void d() {
/* 241 */     for (Position localPosition : this.j) {
/* 242 */       int n = localPosition.q();
/* 243 */       BlockPosition localBlockPosition = new BlockPosition(this.m.getX(), n, this.m.getZ());
/*     */       
/* 245 */       if ((!localBlockPosition.equals(localPosition)) && 
/* 246 */         (c(n - this.m.getY()))) {
/* 247 */         a(localBlockPosition, localPosition, Blocks.LOG);
/*     */       }
/*     */     }
/*     */   }
/*     */   
/*     */   int a(BlockPosition paramBlockPosition1, BlockPosition paramBlockPosition2)
/*     */   {
/* 254 */     BlockPosition localBlockPosition1 = paramBlockPosition2.a(-paramBlockPosition1.getX(), -paramBlockPosition1.getY(), -paramBlockPosition1.getZ());
/*     */     
/* 256 */     int n = b(localBlockPosition1);
/*     */     
/* 258 */     float f1 = localBlockPosition1.getX() / n;
/* 259 */     float f2 = localBlockPosition1.getY() / n;
/* 260 */     float f3 = localBlockPosition1.getZ() / n;
/*     */     
/*     */ 
/* 263 */     if (n == 0) {
/* 264 */       return -1;
/*     */     }
/*     */     
/* 267 */     for (int i1 = 0; i1 <= n; i1++) {
/* 268 */       BlockPosition localBlockPosition2 = paramBlockPosition1.a(0.5F + i1 * f1, 0.5F + i1 * f2, 0.5F + i1 * f3);
/*     */       
/* 270 */       if (!a(this.l.getType(localBlockPosition2).getBlock())) {
/* 271 */         return i1;
/*     */       }
/*     */     }
/*     */     
/* 275 */     return -1;
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
/*     */ 
/*     */   public void e()
/*     */   {
/* 295 */     this.i = 5;
/*     */   }
/*     */   
/*     */   public boolean generate(World paramWorld, Random paramRandom, BlockPosition paramBlockPosition)
/*     */   {
/* 300 */     this.l = paramWorld;
/* 301 */     this.m = paramBlockPosition;
/*     */     
/* 303 */     this.k = new Random(paramRandom.nextLong());
/*     */     
/* 305 */     if (this.a == 0) {
/* 306 */       this.a = (5 + this.k.nextInt(this.h));
/*     */     }
/*     */     
/* 309 */     if (!f()) {
/* 310 */       return false;
/*     */     }
/*     */     
/* 313 */     a();
/* 314 */     b();
/* 315 */     c();
/* 316 */     d();
/*     */     
/* 318 */     return true;
/*     */   }
/*     */   
/*     */ 
/*     */   private boolean f()
/*     */   {
/* 324 */     Block localBlock = this.l.getType(this.m.down()).getBlock();
/* 325 */     if ((localBlock != Blocks.DIRT) && (localBlock != Blocks.GRASS) && (localBlock != Blocks.FARMLAND)) {
/* 326 */       return false;
/*     */     }
/*     */     
/* 329 */     int n = a(this.m, this.m.up(this.a - 1));
/*     */     
/*     */ 
/* 332 */     if (n == -1) {
/* 333 */       return true;
/*     */     }
/*     */     
/*     */ 
/* 337 */     if (n < 6) {
/* 338 */       return false;
/*     */     }
/*     */     
/* 341 */     this.a = n;
/* 342 */     return true;
/*     */   }
/*     */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_8_R3\WorldGenBigTree.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */
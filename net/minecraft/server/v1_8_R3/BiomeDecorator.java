/*     */ package net.minecraft.server.v1_8_R3;
/*     */ 
/*     */ import java.util.Random;
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
/*     */ public class BiomeDecorator
/*     */ {
/*     */   protected World a;
/*     */   protected Random b;
/*     */   protected BlockPosition c;
/*     */   protected CustomWorldSettingsFinal d;
/*     */   
/*     */   public void a(World paramWorld, Random paramRandom, BiomeBase paramBiomeBase, BlockPosition paramBlockPosition)
/*     */   {
/*  24 */     if (this.a != null) {
/*  25 */       throw new RuntimeException("Already decorating");
/*     */     }
/*  27 */     this.a = paramWorld;
/*  28 */     String str = paramWorld.getWorldData().getGeneratorOptions();
/*  29 */     if (str != null) {
/*  30 */       this.d = CustomWorldSettingsFinal.CustomWorldSettings.a(str).b();
/*     */     } else {
/*  32 */       this.d = CustomWorldSettingsFinal.CustomWorldSettings.a("").b();
/*     */     }
/*  34 */     this.b = paramRandom;
/*  35 */     this.c = paramBlockPosition;
/*     */     
/*  37 */     this.h = new WorldGenMinable(Blocks.DIRT.getBlockData(), this.d.I);
/*  38 */     this.i = new WorldGenMinable(Blocks.GRAVEL.getBlockData(), this.d.M);
/*  39 */     this.j = new WorldGenMinable(Blocks.STONE.getBlockData().set(BlockStone.VARIANT, BlockStone.EnumStoneVariant.GRANITE), this.d.Q);
/*  40 */     this.k = new WorldGenMinable(Blocks.STONE.getBlockData().set(BlockStone.VARIANT, BlockStone.EnumStoneVariant.DIORITE), this.d.U);
/*  41 */     this.l = new WorldGenMinable(Blocks.STONE.getBlockData().set(BlockStone.VARIANT, BlockStone.EnumStoneVariant.ANDESITE), this.d.Y);
/*  42 */     this.m = new WorldGenMinable(Blocks.COAL_ORE.getBlockData(), this.d.ac);
/*  43 */     this.n = new WorldGenMinable(Blocks.IRON_ORE.getBlockData(), this.d.ag);
/*  44 */     this.o = new WorldGenMinable(Blocks.GOLD_ORE.getBlockData(), this.d.ak);
/*  45 */     this.p = new WorldGenMinable(Blocks.REDSTONE_ORE.getBlockData(), this.d.ao);
/*  46 */     this.q = new WorldGenMinable(Blocks.DIAMOND_ORE.getBlockData(), this.d.as);
/*  47 */     this.r = new WorldGenMinable(Blocks.LAPIS_ORE.getBlockData(), this.d.aw);
/*     */     
/*  49 */     a(paramBiomeBase);
/*     */     
/*  51 */     this.a = null;
/*  52 */     this.b = null;
/*     */   }
/*     */   
/*  55 */   protected WorldGenerator e = new WorldGenClay(4);
/*  56 */   protected WorldGenerator f = new WorldGenSand(Blocks.SAND, 7);
/*  57 */   protected WorldGenerator g = new WorldGenSand(Blocks.GRAVEL, 6);
/*     */   protected WorldGenerator h;
/*     */   protected WorldGenerator i;
/*     */   protected WorldGenerator j;
/*     */   protected WorldGenerator k;
/*     */   protected WorldGenerator l;
/*     */   protected WorldGenerator m;
/*     */   protected WorldGenerator n;
/*     */   protected WorldGenerator o;
/*     */   protected WorldGenerator p;
/*     */   protected WorldGenerator q;
/*     */   protected WorldGenerator r;
/*  69 */   protected WorldGenFlowers s = new WorldGenFlowers(Blocks.YELLOW_FLOWER, BlockFlowers.EnumFlowerVarient.DANDELION);
/*  70 */   protected WorldGenerator t = new WorldGenMushrooms(Blocks.BROWN_MUSHROOM);
/*  71 */   protected WorldGenerator u = new WorldGenMushrooms(Blocks.RED_MUSHROOM);
/*  72 */   protected WorldGenerator v = new WorldGenHugeMushroom();
/*  73 */   protected WorldGenerator w = new WorldGenReed();
/*  74 */   protected WorldGenerator x = new WorldGenCactus();
/*  75 */   protected WorldGenerator y = new WorldGenWaterLily();
/*     */   
/*     */   protected int z;
/*     */   protected int A;
/*  79 */   protected int B = 2;
/*  80 */   protected int C = 1;
/*     */   protected int D;
/*     */   protected int E;
/*     */   protected int F;
/*     */   protected int G;
/*  85 */   protected int H = 1;
/*  86 */   protected int I = 3;
/*  87 */   protected int J = 1;
/*     */   protected int K;
/*  89 */   public boolean L = true;
/*     */   
/*     */   protected void a(BiomeBase paramBiomeBase) {
/*  92 */     a();
/*     */     int i3;
/*  94 */     for (int i1 = 0; i1 < this.I; i1++) {
/*  95 */       i2 = this.b.nextInt(16) + 8;
/*  96 */       i3 = this.b.nextInt(16) + 8;
/*  97 */       this.f.generate(this.a, this.b, this.a.r(this.c.a(i2, 0, i3)));
/*     */     }
/*     */     
/* 100 */     for (i1 = 0; i1 < this.J; i1++) {
/* 101 */       i2 = this.b.nextInt(16) + 8;
/* 102 */       i3 = this.b.nextInt(16) + 8;
/* 103 */       this.e.generate(this.a, this.b, this.a.r(this.c.a(i2, 0, i3)));
/*     */     }
/*     */     
/* 106 */     for (i1 = 0; i1 < this.H; i1++) {
/* 107 */       i2 = this.b.nextInt(16) + 8;
/* 108 */       i3 = this.b.nextInt(16) + 8;
/* 109 */       this.g.generate(this.a, this.b, this.a.r(this.c.a(i2, 0, i3)));
/*     */     }
/*     */     
/* 112 */     i1 = this.A;
/* 113 */     if (this.b.nextInt(10) == 0) {
/* 114 */       i1++;
/*     */     }
/*     */     int i4;
/* 117 */     for (int i2 = 0; i2 < i1; i2++) {
/* 118 */       i3 = this.b.nextInt(16) + 8;
/* 119 */       i4 = this.b.nextInt(16) + 8;
/*     */       
/* 121 */       WorldGenTreeAbstract localWorldGenTreeAbstract = paramBiomeBase.a(this.b);
/* 122 */       localWorldGenTreeAbstract.e();
/*     */       
/* 124 */       BlockPosition localBlockPosition2 = this.a.getHighestBlockYAt(this.c.a(i3, 0, i4));
/* 125 */       if (localWorldGenTreeAbstract.generate(this.a, this.b, localBlockPosition2)) {
/* 126 */         localWorldGenTreeAbstract.a(this.a, this.b, localBlockPosition2);
/*     */       }
/*     */     }
/*     */     
/* 130 */     for (i2 = 0; i2 < this.K; i2++) {
/* 131 */       i3 = this.b.nextInt(16) + 8;
/* 132 */       i4 = this.b.nextInt(16) + 8;
/* 133 */       this.v.generate(this.a, this.b, this.a.getHighestBlockYAt(this.c.a(i3, 0, i4))); }
/*     */     int i5;
/*     */     int i7;
/* 136 */     Object localObject1; Object localObject2; for (i2 = 0; i2 < this.B; i2++) {
/* 137 */       i3 = this.b.nextInt(16) + 8;
/* 138 */       i4 = this.b.nextInt(16) + 8;
/* 139 */       i5 = this.a.getHighestBlockYAt(this.c.a(i3, 0, i4)).getY() + 32;
/* 140 */       if (i5 > 0) {
/* 141 */         i7 = this.b.nextInt(i5);
/*     */         
/* 143 */         localObject1 = this.c.a(i3, i7, i4);
/* 144 */         localObject2 = paramBiomeBase.a(this.b, (BlockPosition)localObject1);
/* 145 */         BlockFlowers localBlockFlowers = ((BlockFlowers.EnumFlowerVarient)localObject2).a().a();
/* 146 */         if (localBlockFlowers.getMaterial() != Material.AIR) {
/* 147 */           this.s.a(localBlockFlowers, (BlockFlowers.EnumFlowerVarient)localObject2);
/* 148 */           this.s.generate(this.a, this.b, (BlockPosition)localObject1);
/*     */         }
/*     */       }
/*     */     }
/*     */     
/* 153 */     for (i2 = 0; i2 < this.C; i2++) {
/* 154 */       i3 = this.b.nextInt(16) + 8;
/* 155 */       i4 = this.b.nextInt(16) + 8;
/* 156 */       i5 = this.a.getHighestBlockYAt(this.c.a(i3, 0, i4)).getY() * 2;
/* 157 */       if (i5 > 0) {
/* 158 */         i7 = this.b.nextInt(i5);
/*     */         
/* 160 */         paramBiomeBase.b(this.b).generate(this.a, this.b, this.c.a(i3, i7, i4));
/*     */       }
/*     */     }
/*     */     
/* 164 */     for (i2 = 0; i2 < this.D; i2++) {
/* 165 */       i3 = this.b.nextInt(16) + 8;
/* 166 */       i4 = this.b.nextInt(16) + 8;
/* 167 */       i5 = this.a.getHighestBlockYAt(this.c.a(i3, 0, i4)).getY() * 2;
/* 168 */       if (i5 > 0) {
/* 169 */         i7 = this.b.nextInt(i5);
/*     */         
/* 171 */         new WorldGenDeadBush().generate(this.a, this.b, this.c.a(i3, i7, i4));
/*     */       }
/*     */     }
/*     */     
/* 175 */     for (i2 = 0; i2 < this.z; i2++) {
/* 176 */       i3 = this.b.nextInt(16) + 8;
/* 177 */       i4 = this.b.nextInt(16) + 8;
/* 178 */       i5 = this.a.getHighestBlockYAt(this.c.a(i3, 0, i4)).getY() * 2;
/* 179 */       if (i5 > 0) {
/* 180 */         i7 = this.b.nextInt(i5);
/*     */         
/* 182 */         localObject1 = this.c.a(i3, i7, i4);
/* 183 */         while (((BlockPosition)localObject1).getY() > 0) {
/* 184 */           localObject2 = ((BlockPosition)localObject1).down();
/* 185 */           if (!this.a.isEmpty((BlockPosition)localObject2)) {
/*     */             break;
/*     */           }
/*     */           
/* 189 */           localObject1 = localObject2;
/*     */         }
/* 191 */         this.y.generate(this.a, this.b, (BlockPosition)localObject1);
/*     */       }
/*     */     }
/*     */     int i6;
/* 195 */     for (i2 = 0; i2 < this.E; i2++) {
/* 196 */       if (this.b.nextInt(4) == 0) {
/* 197 */         i3 = this.b.nextInt(16) + 8;
/* 198 */         i4 = this.b.nextInt(16) + 8;
/* 199 */         BlockPosition localBlockPosition1 = this.a.getHighestBlockYAt(this.c.a(i3, 0, i4));
/* 200 */         this.t.generate(this.a, this.b, localBlockPosition1);
/*     */       }
/*     */       
/* 203 */       if (this.b.nextInt(8) == 0) {
/* 204 */         i3 = this.b.nextInt(16) + 8;
/* 205 */         i4 = this.b.nextInt(16) + 8;
/* 206 */         i6 = this.a.getHighestBlockYAt(this.c.a(i3, 0, i4)).getY() * 2;
/* 207 */         if (i6 > 0) {
/* 208 */           i7 = this.b.nextInt(i6);
/* 209 */           localObject1 = this.c.a(i3, i7, i4);
/* 210 */           this.u.generate(this.a, this.b, (BlockPosition)localObject1);
/*     */         }
/*     */       }
/*     */     }
/*     */     
/* 215 */     if (this.b.nextInt(4) == 0) {
/* 216 */       i2 = this.b.nextInt(16) + 8;
/* 217 */       i3 = this.b.nextInt(16) + 8;
/* 218 */       i4 = this.a.getHighestBlockYAt(this.c.a(i2, 0, i3)).getY() * 2;
/* 219 */       if (i4 > 0) {
/* 220 */         i6 = this.b.nextInt(i4);
/* 221 */         this.t.generate(this.a, this.b, this.c.a(i2, i6, i3));
/*     */       }
/*     */     }
/*     */     
/* 225 */     if (this.b.nextInt(8) == 0) {
/* 226 */       i2 = this.b.nextInt(16) + 8;
/* 227 */       i3 = this.b.nextInt(16) + 8;
/* 228 */       i4 = this.a.getHighestBlockYAt(this.c.a(i2, 0, i3)).getY() * 2;
/* 229 */       if (i4 > 0) {
/* 230 */         i6 = this.b.nextInt(i4);
/* 231 */         this.u.generate(this.a, this.b, this.c.a(i2, i6, i3));
/*     */       }
/*     */     }
/*     */     
/* 235 */     for (i2 = 0; i2 < this.F; i2++) {
/* 236 */       i3 = this.b.nextInt(16) + 8;
/* 237 */       i4 = this.b.nextInt(16) + 8;
/* 238 */       i6 = this.a.getHighestBlockYAt(this.c.a(i3, 0, i4)).getY() * 2;
/* 239 */       if (i6 > 0) {
/* 240 */         i7 = this.b.nextInt(i6);
/* 241 */         this.w.generate(this.a, this.b, this.c.a(i3, i7, i4));
/*     */       }
/*     */     }
/*     */     
/* 245 */     for (i2 = 0; i2 < 10; i2++) {
/* 246 */       i3 = this.b.nextInt(16) + 8;
/* 247 */       i4 = this.b.nextInt(16) + 8;
/* 248 */       i6 = this.a.getHighestBlockYAt(this.c.a(i3, 0, i4)).getY() * 2;
/* 249 */       if (i6 > 0) {
/* 250 */         i7 = this.b.nextInt(i6);
/* 251 */         this.w.generate(this.a, this.b, this.c.a(i3, i7, i4));
/*     */       }
/*     */     }
/*     */     
/* 255 */     if (this.b.nextInt(32) == 0) {
/* 256 */       i2 = this.b.nextInt(16) + 8;
/* 257 */       i3 = this.b.nextInt(16) + 8;
/* 258 */       i4 = this.a.getHighestBlockYAt(this.c.a(i2, 0, i3)).getY() * 2;
/* 259 */       if (i4 > 0) {
/* 260 */         i6 = this.b.nextInt(i4);
/* 261 */         new WorldGenPumpkin().generate(this.a, this.b, this.c.a(i2, i6, i3));
/*     */       }
/*     */     }
/*     */     
/* 265 */     for (i2 = 0; i2 < this.G; i2++) {
/* 266 */       i3 = this.b.nextInt(16) + 8;
/* 267 */       i4 = this.b.nextInt(16) + 8;
/* 268 */       i6 = this.a.getHighestBlockYAt(this.c.a(i3, 0, i4)).getY() * 2;
/* 269 */       if (i6 > 0) {
/* 270 */         i7 = this.b.nextInt(i6);
/* 271 */         this.x.generate(this.a, this.b, this.c.a(i3, i7, i4));
/*     */       }
/*     */     }
/*     */     
/* 275 */     if (this.L) {
/* 276 */       for (i2 = 0; i2 < 50; i2++) {
/* 277 */         i3 = this.b.nextInt(16) + 8;
/* 278 */         i4 = this.b.nextInt(16) + 8;
/* 279 */         i6 = this.b.nextInt(248) + 8;
/* 280 */         if (i6 > 0) {
/* 281 */           i7 = this.b.nextInt(i6);
/* 282 */           localObject1 = this.c.a(i3, i7, i4);
/* 283 */           new WorldGenLiquids(Blocks.FLOWING_WATER).generate(this.a, this.b, (BlockPosition)localObject1);
/*     */         }
/*     */       }
/*     */       
/* 287 */       for (i2 = 0; i2 < 20; i2++) {
/* 288 */         i3 = this.b.nextInt(16) + 8;
/* 289 */         i4 = this.b.nextInt(16) + 8;
/* 290 */         i6 = this.b.nextInt(this.b.nextInt(this.b.nextInt(240) + 8) + 8);
/* 291 */         BlockPosition localBlockPosition3 = this.c.a(i3, i6, i4);
/*     */         
/* 293 */         new WorldGenLiquids(Blocks.FLOWING_LAVA).generate(this.a, this.b, localBlockPosition3);
/*     */       }
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   protected void a(int paramInt1, WorldGenerator paramWorldGenerator, int paramInt2, int paramInt3)
/*     */   {
/* 303 */     if (paramInt3 < paramInt2) {
/* 304 */       i1 = paramInt2;
/* 305 */       paramInt2 = paramInt3;
/* 306 */       paramInt3 = i1;
/* 307 */     } else if (paramInt3 == paramInt2) {
/* 308 */       if (paramInt2 < 255) {
/* 309 */         paramInt3++;
/*     */       } else {
/* 311 */         paramInt2--;
/*     */       }
/*     */     }
/*     */     
/* 315 */     for (int i1 = 0; i1 < paramInt1; i1++) {
/* 316 */       BlockPosition localBlockPosition = this.c.a(this.b.nextInt(16), this.b.nextInt(paramInt3 - paramInt2) + paramInt2, this.b.nextInt(16));
/* 317 */       paramWorldGenerator.generate(this.a, this.b, localBlockPosition);
/*     */     }
/*     */   }
/*     */   
/*     */   protected void b(int paramInt1, WorldGenerator paramWorldGenerator, int paramInt2, int paramInt3) {
/* 322 */     for (int i1 = 0; i1 < paramInt1; i1++) {
/* 323 */       BlockPosition localBlockPosition = this.c.a(this.b.nextInt(16), this.b.nextInt(paramInt3) + this.b.nextInt(paramInt3) + paramInt2 - paramInt3, this.b.nextInt(16));
/* 324 */       paramWorldGenerator.generate(this.a, this.b, localBlockPosition);
/*     */     }
/*     */   }
/*     */   
/*     */   protected void a() {
/* 329 */     a(this.d.J, this.h, this.d.K, this.d.L);
/* 330 */     a(this.d.N, this.i, this.d.O, this.d.P);
/* 331 */     a(this.d.V, this.k, this.d.W, this.d.X);
/* 332 */     a(this.d.R, this.j, this.d.S, this.d.T);
/* 333 */     a(this.d.Z, this.l, this.d.aa, this.d.ab);
/* 334 */     a(this.d.ad, this.m, this.d.ae, this.d.af);
/* 335 */     a(this.d.ah, this.n, this.d.ai, this.d.aj);
/* 336 */     a(this.d.al, this.o, this.d.am, this.d.an);
/* 337 */     a(this.d.ap, this.p, this.d.aq, this.d.ar);
/* 338 */     a(this.d.at, this.q, this.d.au, this.d.av);
/* 339 */     b(this.d.ax, this.r, this.d.ay, this.d.az);
/*     */   }
/*     */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_8_R3\BiomeDecorator.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */
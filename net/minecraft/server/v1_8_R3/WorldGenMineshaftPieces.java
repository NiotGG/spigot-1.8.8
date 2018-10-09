/*     */ package net.minecraft.server.v1_8_R3;
/*     */ 
/*     */ import com.google.common.collect.Lists;
/*     */ import java.util.List;
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
/*     */ public class WorldGenMineshaftPieces
/*     */ {
/*     */   public static void a()
/*     */   {
/*  37 */     WorldGenFactory.a(WorldGenMineshaftCorridor.class, "MSCorridor");
/*  38 */     WorldGenFactory.a(WorldGenMineshaftCross.class, "MSCrossing");
/*  39 */     WorldGenFactory.a(WorldGenMineshaftRoom.class, "MSRoom");
/*  40 */     WorldGenFactory.a(WorldGenMineshaftStairs.class, "MSStairs");
/*     */   }
/*     */   
/*     */   private static StructurePiece a(List<StructurePiece> paramList, Random paramRandom, int paramInt1, int paramInt2, int paramInt3, EnumDirection paramEnumDirection, int paramInt4) {
/*  44 */     int i = paramRandom.nextInt(100);
/*  45 */     StructureBoundingBox localStructureBoundingBox; if (i >= 80) {
/*  46 */       localStructureBoundingBox = WorldGenMineshaftCross.a(paramList, paramRandom, paramInt1, paramInt2, paramInt3, paramEnumDirection);
/*  47 */       if (localStructureBoundingBox != null) {
/*  48 */         return new WorldGenMineshaftCross(paramInt4, paramRandom, localStructureBoundingBox, paramEnumDirection);
/*     */       }
/*  50 */     } else if (i >= 70) {
/*  51 */       localStructureBoundingBox = WorldGenMineshaftStairs.a(paramList, paramRandom, paramInt1, paramInt2, paramInt3, paramEnumDirection);
/*  52 */       if (localStructureBoundingBox != null) {
/*  53 */         return new WorldGenMineshaftStairs(paramInt4, paramRandom, localStructureBoundingBox, paramEnumDirection);
/*     */       }
/*     */     } else {
/*  56 */       localStructureBoundingBox = WorldGenMineshaftCorridor.a(paramList, paramRandom, paramInt1, paramInt2, paramInt3, paramEnumDirection);
/*  57 */       if (localStructureBoundingBox != null) {
/*  58 */         return new WorldGenMineshaftCorridor(paramInt4, paramRandom, localStructureBoundingBox, paramEnumDirection);
/*     */       }
/*     */     }
/*     */     
/*  62 */     return null;
/*     */   }
/*     */   
/*     */   private static StructurePiece b(StructurePiece paramStructurePiece, List<StructurePiece> paramList, Random paramRandom, int paramInt1, int paramInt2, int paramInt3, EnumDirection paramEnumDirection, int paramInt4) {
/*  66 */     if (paramInt4 > 8) {
/*  67 */       return null;
/*     */     }
/*  69 */     if ((Math.abs(paramInt1 - paramStructurePiece.c().a) > 80) || (Math.abs(paramInt3 - paramStructurePiece.c().c) > 80)) {
/*  70 */       return null;
/*     */     }
/*     */     
/*  73 */     StructurePiece localStructurePiece = a(paramList, paramRandom, paramInt1, paramInt2, paramInt3, paramEnumDirection, paramInt4 + 1);
/*  74 */     if (localStructurePiece != null) {
/*  75 */       paramList.add(localStructurePiece);
/*  76 */       localStructurePiece.a(paramStructurePiece, paramList, paramRandom);
/*     */     }
/*  78 */     return localStructurePiece;
/*     */   }
/*     */   
/*     */   public static class WorldGenMineshaftRoom extends StructurePiece {
/*  82 */     private List<StructureBoundingBox> a = Lists.newLinkedList();
/*     */     
/*     */ 
/*     */     public WorldGenMineshaftRoom() {}
/*     */     
/*     */     public WorldGenMineshaftRoom(int paramInt1, Random paramRandom, int paramInt2, int paramInt3)
/*     */     {
/*  89 */       super();
/*     */       
/*  91 */       this.l = new StructureBoundingBox(paramInt2, 50, paramInt3, paramInt2 + 7 + paramRandom.nextInt(6), 54 + paramRandom.nextInt(6), paramInt3 + 7 + paramRandom.nextInt(6));
/*     */     }
/*     */     
/*     */     public void a(StructurePiece paramStructurePiece, List<StructurePiece> paramList, Random paramRandom)
/*     */     {
/*  96 */       int i = d();
/*     */       
/*     */ 
/*     */ 
/* 100 */       int j = this.l.d() - 3 - 1;
/* 101 */       if (j <= 0) {
/* 102 */         j = 1;
/*     */       }
/*     */       
/*     */ 
/* 106 */       int k = 0;
/* 107 */       StructurePiece localStructurePiece; StructureBoundingBox localStructureBoundingBox; while (k < this.l.c()) {
/* 108 */         k += paramRandom.nextInt(this.l.c());
/* 109 */         if (k + 3 > this.l.c()) {
/*     */           break;
/*     */         }
/* 112 */         localStructurePiece = WorldGenMineshaftPieces.a(paramStructurePiece, paramList, paramRandom, this.l.a + k, this.l.b + paramRandom.nextInt(j) + 1, this.l.c - 1, EnumDirection.NORTH, i);
/* 113 */         if (localStructurePiece != null) {
/* 114 */           localStructureBoundingBox = localStructurePiece.c();
/* 115 */           this.a.add(new StructureBoundingBox(localStructureBoundingBox.a, localStructureBoundingBox.b, this.l.c, localStructureBoundingBox.d, localStructureBoundingBox.e, this.l.c + 1));
/*     */         }
/* 117 */         k += 4;
/*     */       }
/*     */       
/* 120 */       k = 0;
/* 121 */       while (k < this.l.c()) {
/* 122 */         k += paramRandom.nextInt(this.l.c());
/* 123 */         if (k + 3 > this.l.c()) {
/*     */           break;
/*     */         }
/* 126 */         localStructurePiece = WorldGenMineshaftPieces.a(paramStructurePiece, paramList, paramRandom, this.l.a + k, this.l.b + paramRandom.nextInt(j) + 1, this.l.f + 1, EnumDirection.SOUTH, i);
/* 127 */         if (localStructurePiece != null) {
/* 128 */           localStructureBoundingBox = localStructurePiece.c();
/* 129 */           this.a.add(new StructureBoundingBox(localStructureBoundingBox.a, localStructureBoundingBox.b, this.l.f - 1, localStructureBoundingBox.d, localStructureBoundingBox.e, this.l.f));
/*     */         }
/* 131 */         k += 4;
/*     */       }
/*     */       
/* 134 */       k = 0;
/* 135 */       while (k < this.l.e()) {
/* 136 */         k += paramRandom.nextInt(this.l.e());
/* 137 */         if (k + 3 > this.l.e()) {
/*     */           break;
/*     */         }
/* 140 */         localStructurePiece = WorldGenMineshaftPieces.a(paramStructurePiece, paramList, paramRandom, this.l.a - 1, this.l.b + paramRandom.nextInt(j) + 1, this.l.c + k, EnumDirection.WEST, i);
/* 141 */         if (localStructurePiece != null) {
/* 142 */           localStructureBoundingBox = localStructurePiece.c();
/* 143 */           this.a.add(new StructureBoundingBox(this.l.a, localStructureBoundingBox.b, localStructureBoundingBox.c, this.l.a + 1, localStructureBoundingBox.e, localStructureBoundingBox.f));
/*     */         }
/* 145 */         k += 4;
/*     */       }
/*     */       
/* 148 */       k = 0;
/* 149 */       while (k < this.l.e()) {
/* 150 */         k += paramRandom.nextInt(this.l.e());
/* 151 */         if (k + 3 > this.l.e()) {
/*     */           break;
/*     */         }
/* 154 */         localStructurePiece = WorldGenMineshaftPieces.a(paramStructurePiece, paramList, paramRandom, this.l.d + 1, this.l.b + paramRandom.nextInt(j) + 1, this.l.c + k, EnumDirection.EAST, i);
/* 155 */         if (localStructurePiece != null) {
/* 156 */           localStructureBoundingBox = localStructurePiece.c();
/* 157 */           this.a.add(new StructureBoundingBox(this.l.d - 1, localStructureBoundingBox.b, localStructureBoundingBox.c, this.l.d, localStructureBoundingBox.e, localStructureBoundingBox.f));
/*     */         }
/* 159 */         k += 4;
/*     */       }
/*     */     }
/*     */     
/*     */     public boolean a(World paramWorld, Random paramRandom, StructureBoundingBox paramStructureBoundingBox)
/*     */     {
/* 165 */       if (a(paramWorld, paramStructureBoundingBox)) {
/* 166 */         return false;
/*     */       }
/*     */       
/*     */ 
/* 170 */       a(paramWorld, paramStructureBoundingBox, this.l.a, this.l.b, this.l.c, this.l.d, this.l.b, this.l.f, Blocks.DIRT.getBlockData(), Blocks.AIR.getBlockData(), true);
/*     */       
/*     */ 
/* 173 */       a(paramWorld, paramStructureBoundingBox, this.l.a, this.l.b + 1, this.l.c, this.l.d, Math.min(this.l.b + 3, this.l.e), this.l.f, Blocks.AIR.getBlockData(), Blocks.AIR.getBlockData(), false);
/* 174 */       for (StructureBoundingBox localStructureBoundingBox : this.a) {
/* 175 */         a(paramWorld, paramStructureBoundingBox, localStructureBoundingBox.a, localStructureBoundingBox.e - 2, localStructureBoundingBox.c, localStructureBoundingBox.d, localStructureBoundingBox.e, localStructureBoundingBox.f, Blocks.AIR.getBlockData(), Blocks.AIR.getBlockData(), false);
/*     */       }
/* 177 */       a(paramWorld, paramStructureBoundingBox, this.l.a, this.l.b + 4, this.l.c, this.l.d, this.l.e, this.l.f, Blocks.AIR.getBlockData(), false);
/*     */       
/* 179 */       return true;
/*     */     }
/*     */     
/*     */     public void a(int paramInt1, int paramInt2, int paramInt3)
/*     */     {
/* 184 */       super.a(paramInt1, paramInt2, paramInt3);
/* 185 */       for (StructureBoundingBox localStructureBoundingBox : this.a) {
/* 186 */         localStructureBoundingBox.a(paramInt1, paramInt2, paramInt3);
/*     */       }
/*     */     }
/*     */     
/*     */     protected void a(NBTTagCompound paramNBTTagCompound)
/*     */     {
/* 192 */       NBTTagList localNBTTagList = new NBTTagList();
/* 193 */       for (StructureBoundingBox localStructureBoundingBox : this.a) {
/* 194 */         localNBTTagList.add(localStructureBoundingBox.g());
/*     */       }
/* 196 */       paramNBTTagCompound.set("Entrances", localNBTTagList);
/*     */     }
/*     */     
/*     */     protected void b(NBTTagCompound paramNBTTagCompound)
/*     */     {
/* 201 */       NBTTagList localNBTTagList = paramNBTTagCompound.getList("Entrances", 11);
/* 202 */       for (int i = 0; i < localNBTTagList.size(); i++) {
/* 203 */         this.a.add(new StructureBoundingBox(localNBTTagList.c(i)));
/*     */       }
/*     */     }
/*     */   }
/*     */   
/*     */   public static class WorldGenMineshaftCorridor
/*     */     extends StructurePiece
/*     */   {
/*     */     private boolean a;
/*     */     private boolean b;
/*     */     private boolean c;
/*     */     private int d;
/*     */     
/*     */     public WorldGenMineshaftCorridor() {}
/*     */     
/*     */     protected void a(NBTTagCompound paramNBTTagCompound)
/*     */     {
/* 220 */       paramNBTTagCompound.setBoolean("hr", this.a);
/* 221 */       paramNBTTagCompound.setBoolean("sc", this.b);
/* 222 */       paramNBTTagCompound.setBoolean("hps", this.c);
/* 223 */       paramNBTTagCompound.setInt("Num", this.d);
/*     */     }
/*     */     
/*     */     protected void b(NBTTagCompound paramNBTTagCompound)
/*     */     {
/* 228 */       this.a = paramNBTTagCompound.getBoolean("hr");
/* 229 */       this.b = paramNBTTagCompound.getBoolean("sc");
/* 230 */       this.c = paramNBTTagCompound.getBoolean("hps");
/* 231 */       this.d = paramNBTTagCompound.getInt("Num");
/*     */     }
/*     */     
/*     */     public WorldGenMineshaftCorridor(int paramInt, Random paramRandom, StructureBoundingBox paramStructureBoundingBox, EnumDirection paramEnumDirection) {
/* 235 */       super();
/* 236 */       this.m = paramEnumDirection;
/* 237 */       this.l = paramStructureBoundingBox;
/* 238 */       this.a = (paramRandom.nextInt(3) == 0);
/* 239 */       this.b = ((!this.a) && (paramRandom.nextInt(23) == 0));
/*     */       
/* 241 */       if ((this.m == EnumDirection.NORTH) || (this.m == EnumDirection.SOUTH)) {
/* 242 */         this.d = (paramStructureBoundingBox.e() / 5);
/*     */       } else {
/* 244 */         this.d = (paramStructureBoundingBox.c() / 5);
/*     */       }
/*     */     }
/*     */     
/*     */     public static StructureBoundingBox a(List<StructurePiece> paramList, Random paramRandom, int paramInt1, int paramInt2, int paramInt3, EnumDirection paramEnumDirection) {
/* 249 */       StructureBoundingBox localStructureBoundingBox = new StructureBoundingBox(paramInt1, paramInt2, paramInt3, paramInt1, paramInt2 + 2, paramInt3);
/*     */       
/* 251 */       int i = paramRandom.nextInt(3) + 2;
/* 252 */       while (i > 0) {
/* 253 */         int j = i * 5;
/*     */         
/* 255 */         switch (WorldGenMineshaftPieces.1.a[paramEnumDirection.ordinal()]) {
/*     */         case 1: 
/* 257 */           localStructureBoundingBox.d = (paramInt1 + 2);
/* 258 */           localStructureBoundingBox.c = (paramInt3 - (j - 1));
/* 259 */           break;
/*     */         case 2: 
/* 261 */           localStructureBoundingBox.d = (paramInt1 + 2);
/* 262 */           localStructureBoundingBox.f = (paramInt3 + (j - 1));
/* 263 */           break;
/*     */         case 3: 
/* 265 */           localStructureBoundingBox.a = (paramInt1 - (j - 1));
/* 266 */           localStructureBoundingBox.f = (paramInt3 + 2);
/* 267 */           break;
/*     */         case 4: 
/* 269 */           localStructureBoundingBox.d = (paramInt1 + (j - 1));
/* 270 */           localStructureBoundingBox.f = (paramInt3 + 2);
/*     */         }
/*     */         
/*     */         
/* 274 */         if (StructurePiece.a(paramList, localStructureBoundingBox) == null) break;
/* 275 */         i--;
/*     */       }
/*     */       
/*     */ 
/*     */ 
/*     */ 
/* 281 */       if (i > 0) {
/* 282 */         return localStructureBoundingBox;
/*     */       }
/*     */       
/* 285 */       return null;
/*     */     }
/*     */     
/*     */     public void a(StructurePiece paramStructurePiece, List<StructurePiece> paramList, Random paramRandom)
/*     */     {
/* 290 */       int i = d();
/* 291 */       int j = paramRandom.nextInt(4);
/* 292 */       if (this.m != null) {
/* 293 */         switch (WorldGenMineshaftPieces.1.a[this.m.ordinal()]) {
/*     */         case 1: 
/* 295 */           if (j <= 1) {
/* 296 */             WorldGenMineshaftPieces.a(paramStructurePiece, paramList, paramRandom, this.l.a, this.l.b - 1 + paramRandom.nextInt(3), this.l.c - 1, this.m, i);
/* 297 */           } else if (j == 2) {
/* 298 */             WorldGenMineshaftPieces.a(paramStructurePiece, paramList, paramRandom, this.l.a - 1, this.l.b - 1 + paramRandom.nextInt(3), this.l.c, EnumDirection.WEST, i);
/*     */           } else {
/* 300 */             WorldGenMineshaftPieces.a(paramStructurePiece, paramList, paramRandom, this.l.d + 1, this.l.b - 1 + paramRandom.nextInt(3), this.l.c, EnumDirection.EAST, i);
/*     */           }
/* 302 */           break;
/*     */         case 2: 
/* 304 */           if (j <= 1) {
/* 305 */             WorldGenMineshaftPieces.a(paramStructurePiece, paramList, paramRandom, this.l.a, this.l.b - 1 + paramRandom.nextInt(3), this.l.f + 1, this.m, i);
/* 306 */           } else if (j == 2) {
/* 307 */             WorldGenMineshaftPieces.a(paramStructurePiece, paramList, paramRandom, this.l.a - 1, this.l.b - 1 + paramRandom.nextInt(3), this.l.f - 3, EnumDirection.WEST, i);
/*     */           } else {
/* 309 */             WorldGenMineshaftPieces.a(paramStructurePiece, paramList, paramRandom, this.l.d + 1, this.l.b - 1 + paramRandom.nextInt(3), this.l.f - 3, EnumDirection.EAST, i);
/*     */           }
/* 311 */           break;
/*     */         case 3: 
/* 313 */           if (j <= 1) {
/* 314 */             WorldGenMineshaftPieces.a(paramStructurePiece, paramList, paramRandom, this.l.a - 1, this.l.b - 1 + paramRandom.nextInt(3), this.l.c, this.m, i);
/* 315 */           } else if (j == 2) {
/* 316 */             WorldGenMineshaftPieces.a(paramStructurePiece, paramList, paramRandom, this.l.a, this.l.b - 1 + paramRandom.nextInt(3), this.l.c - 1, EnumDirection.NORTH, i);
/*     */           } else {
/* 318 */             WorldGenMineshaftPieces.a(paramStructurePiece, paramList, paramRandom, this.l.a, this.l.b - 1 + paramRandom.nextInt(3), this.l.f + 1, EnumDirection.SOUTH, i);
/*     */           }
/* 320 */           break;
/*     */         case 4: 
/* 322 */           if (j <= 1) {
/* 323 */             WorldGenMineshaftPieces.a(paramStructurePiece, paramList, paramRandom, this.l.d + 1, this.l.b - 1 + paramRandom.nextInt(3), this.l.c, this.m, i);
/* 324 */           } else if (j == 2) {
/* 325 */             WorldGenMineshaftPieces.a(paramStructurePiece, paramList, paramRandom, this.l.d - 3, this.l.b - 1 + paramRandom.nextInt(3), this.l.c - 1, EnumDirection.NORTH, i);
/*     */           } else {
/* 327 */             WorldGenMineshaftPieces.a(paramStructurePiece, paramList, paramRandom, this.l.d - 3, this.l.b - 1 + paramRandom.nextInt(3), this.l.f + 1, EnumDirection.SOUTH, i);
/*     */           }
/*     */           
/*     */           break;
/*     */         }
/*     */         
/*     */       }
/* 334 */       if (i < 8) { int k;
/* 335 */         int m; if ((this.m == EnumDirection.NORTH) || (this.m == EnumDirection.SOUTH)) {
/* 336 */           for (k = this.l.c + 3; k + 3 <= this.l.f; k += 5) {
/* 337 */             m = paramRandom.nextInt(5);
/* 338 */             if (m == 0) {
/* 339 */               WorldGenMineshaftPieces.a(paramStructurePiece, paramList, paramRandom, this.l.a - 1, this.l.b, k, EnumDirection.WEST, i + 1);
/* 340 */             } else if (m == 1) {
/* 341 */               WorldGenMineshaftPieces.a(paramStructurePiece, paramList, paramRandom, this.l.d + 1, this.l.b, k, EnumDirection.EAST, i + 1);
/*     */             }
/*     */           }
/*     */         } else {
/* 345 */           for (k = this.l.a + 3; k + 3 <= this.l.d; k += 5) {
/* 346 */             m = paramRandom.nextInt(5);
/* 347 */             if (m == 0) {
/* 348 */               WorldGenMineshaftPieces.a(paramStructurePiece, paramList, paramRandom, k, this.l.b, this.l.c - 1, EnumDirection.NORTH, i + 1);
/* 349 */             } else if (m == 1) {
/* 350 */               WorldGenMineshaftPieces.a(paramStructurePiece, paramList, paramRandom, k, this.l.b, this.l.f + 1, EnumDirection.SOUTH, i + 1);
/*     */             }
/*     */           }
/*     */         }
/*     */       }
/*     */     }
/*     */     
/*     */     protected boolean a(World paramWorld, StructureBoundingBox paramStructureBoundingBox, Random paramRandom, int paramInt1, int paramInt2, int paramInt3, List<StructurePieceTreasure> paramList, int paramInt4)
/*     */     {
/* 359 */       BlockPosition localBlockPosition = new BlockPosition(a(paramInt1, paramInt3), d(paramInt2), b(paramInt1, paramInt3));
/* 360 */       if ((paramStructureBoundingBox.b(localBlockPosition)) && 
/* 361 */         (paramWorld.getType(localBlockPosition).getBlock().getMaterial() == Material.AIR)) {
/* 362 */         int i = paramRandom.nextBoolean() ? 1 : 0;
/* 363 */         paramWorld.setTypeAndData(localBlockPosition, Blocks.RAIL.fromLegacyData(a(Blocks.RAIL, i)), 2);
/* 364 */         EntityMinecartChest localEntityMinecartChest = new EntityMinecartChest(paramWorld, localBlockPosition.getX() + 0.5F, localBlockPosition.getY() + 0.5F, localBlockPosition.getZ() + 0.5F);
/* 365 */         StructurePieceTreasure.a(paramRandom, paramList, localEntityMinecartChest, paramInt4);
/* 366 */         paramWorld.addEntity(localEntityMinecartChest);
/* 367 */         return true;
/*     */       }
/*     */       
/*     */ 
/* 371 */       return false;
/*     */     }
/*     */     
/*     */     public boolean a(World paramWorld, Random paramRandom, StructureBoundingBox paramStructureBoundingBox)
/*     */     {
/* 376 */       if (a(paramWorld, paramStructureBoundingBox)) {
/* 377 */         return false;
/*     */       }
/*     */       
/* 380 */       int i = 0;
/* 381 */       int j = 2;
/* 382 */       int k = 0;
/* 383 */       int m = 2;
/* 384 */       int n = this.d * 5 - 1;
/*     */       
/*     */ 
/* 387 */       a(paramWorld, paramStructureBoundingBox, 0, 0, 0, 2, 1, n, Blocks.AIR.getBlockData(), Blocks.AIR.getBlockData(), false);
/* 388 */       a(paramWorld, paramStructureBoundingBox, paramRandom, 0.8F, 0, 2, 0, 2, 2, n, Blocks.AIR.getBlockData(), Blocks.AIR.getBlockData(), false);
/*     */       
/* 390 */       if (this.b)
/* 391 */         a(paramWorld, paramStructureBoundingBox, paramRandom, 0.6F, 0, 0, 0, 2, 1, n, Blocks.WEB.getBlockData(), Blocks.AIR.getBlockData(), false);
/*     */       int i2;
/*     */       int i3;
/*     */       int i5;
/* 395 */       for (int i1 = 0; i1 < this.d; i1++) {
/* 396 */         i2 = 2 + i1 * 5;
/*     */         
/* 398 */         a(paramWorld, paramStructureBoundingBox, 0, 0, i2, 0, 1, i2, Blocks.FENCE.getBlockData(), Blocks.AIR.getBlockData(), false);
/* 399 */         a(paramWorld, paramStructureBoundingBox, 2, 0, i2, 2, 1, i2, Blocks.FENCE.getBlockData(), Blocks.AIR.getBlockData(), false);
/* 400 */         if (paramRandom.nextInt(4) == 0) {
/* 401 */           a(paramWorld, paramStructureBoundingBox, 0, 2, i2, 0, 2, i2, Blocks.PLANKS.getBlockData(), Blocks.AIR.getBlockData(), false);
/* 402 */           a(paramWorld, paramStructureBoundingBox, 2, 2, i2, 2, 2, i2, Blocks.PLANKS.getBlockData(), Blocks.AIR.getBlockData(), false);
/*     */         } else {
/* 404 */           a(paramWorld, paramStructureBoundingBox, 0, 2, i2, 2, 2, i2, Blocks.PLANKS.getBlockData(), Blocks.AIR.getBlockData(), false);
/*     */         }
/* 406 */         a(paramWorld, paramStructureBoundingBox, paramRandom, 0.1F, 0, 2, i2 - 1, Blocks.WEB.getBlockData());
/* 407 */         a(paramWorld, paramStructureBoundingBox, paramRandom, 0.1F, 2, 2, i2 - 1, Blocks.WEB.getBlockData());
/* 408 */         a(paramWorld, paramStructureBoundingBox, paramRandom, 0.1F, 0, 2, i2 + 1, Blocks.WEB.getBlockData());
/* 409 */         a(paramWorld, paramStructureBoundingBox, paramRandom, 0.1F, 2, 2, i2 + 1, Blocks.WEB.getBlockData());
/* 410 */         a(paramWorld, paramStructureBoundingBox, paramRandom, 0.05F, 0, 2, i2 - 2, Blocks.WEB.getBlockData());
/* 411 */         a(paramWorld, paramStructureBoundingBox, paramRandom, 0.05F, 2, 2, i2 - 2, Blocks.WEB.getBlockData());
/* 412 */         a(paramWorld, paramStructureBoundingBox, paramRandom, 0.05F, 0, 2, i2 + 2, Blocks.WEB.getBlockData());
/* 413 */         a(paramWorld, paramStructureBoundingBox, paramRandom, 0.05F, 2, 2, i2 + 2, Blocks.WEB.getBlockData());
/*     */         
/* 415 */         a(paramWorld, paramStructureBoundingBox, paramRandom, 0.05F, 1, 2, i2 - 1, Blocks.TORCH.fromLegacyData(EnumDirection.UP.a()));
/* 416 */         a(paramWorld, paramStructureBoundingBox, paramRandom, 0.05F, 1, 2, i2 + 1, Blocks.TORCH.fromLegacyData(EnumDirection.UP.a()));
/*     */         
/* 418 */         if (paramRandom.nextInt(100) == 0) {
/* 419 */           a(paramWorld, paramStructureBoundingBox, paramRandom, 2, 0, i2 - 1, StructurePieceTreasure.a(WorldGenMineshaftPieces.b(), new StructurePieceTreasure[] { Items.ENCHANTED_BOOK.b(paramRandom) }), 3 + paramRandom.nextInt(4));
/*     */         }
/* 421 */         if (paramRandom.nextInt(100) == 0) {
/* 422 */           a(paramWorld, paramStructureBoundingBox, paramRandom, 0, 0, i2 + 1, StructurePieceTreasure.a(WorldGenMineshaftPieces.b(), new StructurePieceTreasure[] { Items.ENCHANTED_BOOK.b(paramRandom) }), 3 + paramRandom.nextInt(4));
/*     */         }
/* 424 */         if ((this.b) && (!this.c)) {
/* 425 */           i3 = d(0);int i4 = i2 - 1 + paramRandom.nextInt(3);
/* 426 */           i5 = a(1, i4);
/* 427 */           i4 = b(1, i4);
/* 428 */           BlockPosition localBlockPosition = new BlockPosition(i5, i3, i4);
/* 429 */           if (paramStructureBoundingBox.b(localBlockPosition)) {
/* 430 */             this.c = true;
/* 431 */             paramWorld.setTypeAndData(localBlockPosition, Blocks.MOB_SPAWNER.getBlockData(), 2);
/*     */             
/* 433 */             TileEntity localTileEntity = paramWorld.getTileEntity(localBlockPosition);
/* 434 */             if ((localTileEntity instanceof TileEntityMobSpawner)) {
/* 435 */               ((TileEntityMobSpawner)localTileEntity).getSpawner().setMobName("CaveSpider");
/*     */             }
/*     */           }
/*     */         }
/*     */       }
/*     */       
/*     */ 
/* 442 */       for (i1 = 0; i1 <= 2; i1++) {
/* 443 */         for (i2 = 0; i2 <= n; i2++) {
/* 444 */           i3 = -1;
/* 445 */           IBlockData localIBlockData2 = a(paramWorld, i1, i3, i2, paramStructureBoundingBox);
/* 446 */           if (localIBlockData2.getBlock().getMaterial() == Material.AIR) {
/* 447 */             i5 = -1;
/* 448 */             a(paramWorld, Blocks.PLANKS.getBlockData(), i1, i5, i2, paramStructureBoundingBox);
/*     */           }
/*     */         }
/*     */       }
/*     */       
/* 453 */       if (this.a) {
/* 454 */         for (i1 = 0; i1 <= n; i1++) {
/* 455 */           IBlockData localIBlockData1 = a(paramWorld, 1, -1, i1, paramStructureBoundingBox);
/* 456 */           if ((localIBlockData1.getBlock().getMaterial() != Material.AIR) && (localIBlockData1.getBlock().o())) {
/* 457 */             a(paramWorld, paramStructureBoundingBox, paramRandom, 0.7F, 1, 0, i1, Blocks.RAIL.fromLegacyData(a(Blocks.RAIL, 0)));
/*     */           }
/*     */         }
/*     */       }
/*     */       
/* 462 */       return true;
/*     */     }
/*     */   }
/*     */   
/*     */   public static class WorldGenMineshaftCross
/*     */     extends StructurePiece
/*     */   {
/*     */     private EnumDirection a;
/*     */     private boolean b;
/*     */     
/*     */     public WorldGenMineshaftCross() {}
/*     */     
/*     */     protected void a(NBTTagCompound paramNBTTagCompound)
/*     */     {
/* 476 */       paramNBTTagCompound.setBoolean("tf", this.b);
/* 477 */       paramNBTTagCompound.setInt("D", this.a.b());
/*     */     }
/*     */     
/*     */     protected void b(NBTTagCompound paramNBTTagCompound)
/*     */     {
/* 482 */       this.b = paramNBTTagCompound.getBoolean("tf");
/* 483 */       this.a = EnumDirection.fromType2(paramNBTTagCompound.getInt("D"));
/*     */     }
/*     */     
/*     */     public WorldGenMineshaftCross(int paramInt, Random paramRandom, StructureBoundingBox paramStructureBoundingBox, EnumDirection paramEnumDirection) {
/* 487 */       super();
/*     */       
/* 489 */       this.a = paramEnumDirection;
/* 490 */       this.l = paramStructureBoundingBox;
/* 491 */       this.b = (paramStructureBoundingBox.d() > 3);
/*     */     }
/*     */     
/*     */     public static StructureBoundingBox a(List<StructurePiece> paramList, Random paramRandom, int paramInt1, int paramInt2, int paramInt3, EnumDirection paramEnumDirection) {
/* 495 */       StructureBoundingBox localStructureBoundingBox = new StructureBoundingBox(paramInt1, paramInt2, paramInt3, paramInt1, paramInt2 + 2, paramInt3);
/*     */       
/* 497 */       if (paramRandom.nextInt(4) == 0) {
/* 498 */         localStructureBoundingBox.e += 4;
/*     */       }
/*     */       
/* 501 */       switch (WorldGenMineshaftPieces.1.a[paramEnumDirection.ordinal()]) {
/*     */       case 1: 
/* 503 */         localStructureBoundingBox.a = (paramInt1 - 1);
/* 504 */         localStructureBoundingBox.d = (paramInt1 + 3);
/* 505 */         localStructureBoundingBox.c = (paramInt3 - 4);
/* 506 */         break;
/*     */       case 2: 
/* 508 */         localStructureBoundingBox.a = (paramInt1 - 1);
/* 509 */         localStructureBoundingBox.d = (paramInt1 + 3);
/* 510 */         localStructureBoundingBox.f = (paramInt3 + 4);
/* 511 */         break;
/*     */       case 3: 
/* 513 */         localStructureBoundingBox.a = (paramInt1 - 4);
/* 514 */         localStructureBoundingBox.c = (paramInt3 - 1);
/* 515 */         localStructureBoundingBox.f = (paramInt3 + 3);
/* 516 */         break;
/*     */       case 4: 
/* 518 */         localStructureBoundingBox.d = (paramInt1 + 4);
/* 519 */         localStructureBoundingBox.c = (paramInt3 - 1);
/* 520 */         localStructureBoundingBox.f = (paramInt3 + 3);
/*     */       }
/*     */       
/*     */       
/* 524 */       if (StructurePiece.a(paramList, localStructureBoundingBox) != null) {
/* 525 */         return null;
/*     */       }
/*     */       
/* 528 */       return localStructureBoundingBox;
/*     */     }
/*     */     
/*     */     public void a(StructurePiece paramStructurePiece, List<StructurePiece> paramList, Random paramRandom)
/*     */     {
/* 533 */       int i = d();
/*     */       
/*     */ 
/* 536 */       switch (WorldGenMineshaftPieces.1.a[this.a.ordinal()]) {
/*     */       case 1: 
/* 538 */         WorldGenMineshaftPieces.a(paramStructurePiece, paramList, paramRandom, this.l.a + 1, this.l.b, this.l.c - 1, EnumDirection.NORTH, i);
/* 539 */         WorldGenMineshaftPieces.a(paramStructurePiece, paramList, paramRandom, this.l.a - 1, this.l.b, this.l.c + 1, EnumDirection.WEST, i);
/* 540 */         WorldGenMineshaftPieces.a(paramStructurePiece, paramList, paramRandom, this.l.d + 1, this.l.b, this.l.c + 1, EnumDirection.EAST, i);
/* 541 */         break;
/*     */       case 2: 
/* 543 */         WorldGenMineshaftPieces.a(paramStructurePiece, paramList, paramRandom, this.l.a + 1, this.l.b, this.l.f + 1, EnumDirection.SOUTH, i);
/* 544 */         WorldGenMineshaftPieces.a(paramStructurePiece, paramList, paramRandom, this.l.a - 1, this.l.b, this.l.c + 1, EnumDirection.WEST, i);
/* 545 */         WorldGenMineshaftPieces.a(paramStructurePiece, paramList, paramRandom, this.l.d + 1, this.l.b, this.l.c + 1, EnumDirection.EAST, i);
/* 546 */         break;
/*     */       case 3: 
/* 548 */         WorldGenMineshaftPieces.a(paramStructurePiece, paramList, paramRandom, this.l.a + 1, this.l.b, this.l.c - 1, EnumDirection.NORTH, i);
/* 549 */         WorldGenMineshaftPieces.a(paramStructurePiece, paramList, paramRandom, this.l.a + 1, this.l.b, this.l.f + 1, EnumDirection.SOUTH, i);
/* 550 */         WorldGenMineshaftPieces.a(paramStructurePiece, paramList, paramRandom, this.l.a - 1, this.l.b, this.l.c + 1, EnumDirection.WEST, i);
/* 551 */         break;
/*     */       case 4: 
/* 553 */         WorldGenMineshaftPieces.a(paramStructurePiece, paramList, paramRandom, this.l.a + 1, this.l.b, this.l.c - 1, EnumDirection.NORTH, i);
/* 554 */         WorldGenMineshaftPieces.a(paramStructurePiece, paramList, paramRandom, this.l.a + 1, this.l.b, this.l.f + 1, EnumDirection.SOUTH, i);
/* 555 */         WorldGenMineshaftPieces.a(paramStructurePiece, paramList, paramRandom, this.l.d + 1, this.l.b, this.l.c + 1, EnumDirection.EAST, i);
/*     */       }
/*     */       
/*     */       
/* 559 */       if (this.b) {
/* 560 */         if (paramRandom.nextBoolean()) {
/* 561 */           WorldGenMineshaftPieces.a(paramStructurePiece, paramList, paramRandom, this.l.a + 1, this.l.b + 3 + 1, this.l.c - 1, EnumDirection.NORTH, i);
/*     */         }
/* 563 */         if (paramRandom.nextBoolean()) {
/* 564 */           WorldGenMineshaftPieces.a(paramStructurePiece, paramList, paramRandom, this.l.a - 1, this.l.b + 3 + 1, this.l.c + 1, EnumDirection.WEST, i);
/*     */         }
/* 566 */         if (paramRandom.nextBoolean()) {
/* 567 */           WorldGenMineshaftPieces.a(paramStructurePiece, paramList, paramRandom, this.l.d + 1, this.l.b + 3 + 1, this.l.c + 1, EnumDirection.EAST, i);
/*     */         }
/* 569 */         if (paramRandom.nextBoolean()) {
/* 570 */           WorldGenMineshaftPieces.a(paramStructurePiece, paramList, paramRandom, this.l.a + 1, this.l.b + 3 + 1, this.l.f + 1, EnumDirection.SOUTH, i);
/*     */         }
/*     */       }
/*     */     }
/*     */     
/*     */     public boolean a(World paramWorld, Random paramRandom, StructureBoundingBox paramStructureBoundingBox)
/*     */     {
/* 577 */       if (a(paramWorld, paramStructureBoundingBox)) {
/* 578 */         return false;
/*     */       }
/*     */       
/*     */ 
/* 582 */       if (this.b) {
/* 583 */         a(paramWorld, paramStructureBoundingBox, this.l.a + 1, this.l.b, this.l.c, this.l.d - 1, this.l.b + 3 - 1, this.l.f, Blocks.AIR.getBlockData(), Blocks.AIR.getBlockData(), false);
/* 584 */         a(paramWorld, paramStructureBoundingBox, this.l.a, this.l.b, this.l.c + 1, this.l.d, this.l.b + 3 - 1, this.l.f - 1, Blocks.AIR.getBlockData(), Blocks.AIR.getBlockData(), false);
/* 585 */         a(paramWorld, paramStructureBoundingBox, this.l.a + 1, this.l.e - 2, this.l.c, this.l.d - 1, this.l.e, this.l.f, Blocks.AIR.getBlockData(), Blocks.AIR.getBlockData(), false);
/* 586 */         a(paramWorld, paramStructureBoundingBox, this.l.a, this.l.e - 2, this.l.c + 1, this.l.d, this.l.e, this.l.f - 1, Blocks.AIR.getBlockData(), Blocks.AIR.getBlockData(), false);
/* 587 */         a(paramWorld, paramStructureBoundingBox, this.l.a + 1, this.l.b + 3, this.l.c + 1, this.l.d - 1, this.l.b + 3, this.l.f - 1, Blocks.AIR.getBlockData(), Blocks.AIR.getBlockData(), false);
/*     */       } else {
/* 589 */         a(paramWorld, paramStructureBoundingBox, this.l.a + 1, this.l.b, this.l.c, this.l.d - 1, this.l.e, this.l.f, Blocks.AIR.getBlockData(), Blocks.AIR.getBlockData(), false);
/* 590 */         a(paramWorld, paramStructureBoundingBox, this.l.a, this.l.b, this.l.c + 1, this.l.d, this.l.e, this.l.f - 1, Blocks.AIR.getBlockData(), Blocks.AIR.getBlockData(), false);
/*     */       }
/*     */       
/*     */ 
/* 594 */       a(paramWorld, paramStructureBoundingBox, this.l.a + 1, this.l.b, this.l.c + 1, this.l.a + 1, this.l.e, this.l.c + 1, Blocks.PLANKS.getBlockData(), Blocks.AIR.getBlockData(), false);
/* 595 */       a(paramWorld, paramStructureBoundingBox, this.l.a + 1, this.l.b, this.l.f - 1, this.l.a + 1, this.l.e, this.l.f - 1, Blocks.PLANKS.getBlockData(), Blocks.AIR.getBlockData(), false);
/* 596 */       a(paramWorld, paramStructureBoundingBox, this.l.d - 1, this.l.b, this.l.c + 1, this.l.d - 1, this.l.e, this.l.c + 1, Blocks.PLANKS.getBlockData(), Blocks.AIR.getBlockData(), false);
/* 597 */       a(paramWorld, paramStructureBoundingBox, this.l.d - 1, this.l.b, this.l.f - 1, this.l.d - 1, this.l.e, this.l.f - 1, Blocks.PLANKS.getBlockData(), Blocks.AIR.getBlockData(), false);
/*     */       
/*     */ 
/*     */ 
/* 601 */       for (int i = this.l.a; i <= this.l.d; i++) {
/* 602 */         for (int j = this.l.c; j <= this.l.f; j++) {
/* 603 */           if (a(paramWorld, i, this.l.b - 1, j, paramStructureBoundingBox).getBlock().getMaterial() == Material.AIR) {
/* 604 */             a(paramWorld, Blocks.PLANKS.getBlockData(), i, this.l.b - 1, j, paramStructureBoundingBox);
/*     */           }
/*     */         }
/*     */       }
/*     */       
/* 609 */       return true;
/*     */     }
/*     */   }
/*     */   
/*     */   public static class WorldGenMineshaftStairs extends StructurePiece
/*     */   {
/*     */     public WorldGenMineshaftStairs() {}
/*     */     
/*     */     public WorldGenMineshaftStairs(int paramInt, Random paramRandom, StructureBoundingBox paramStructureBoundingBox, EnumDirection paramEnumDirection)
/*     */     {
/* 619 */       super();
/* 620 */       this.m = paramEnumDirection;
/* 621 */       this.l = paramStructureBoundingBox;
/*     */     }
/*     */     
/*     */ 
/*     */ 
/*     */     protected void a(NBTTagCompound paramNBTTagCompound) {}
/*     */     
/*     */ 
/*     */     protected void b(NBTTagCompound paramNBTTagCompound) {}
/*     */     
/*     */ 
/*     */     public static StructureBoundingBox a(List<StructurePiece> paramList, Random paramRandom, int paramInt1, int paramInt2, int paramInt3, EnumDirection paramEnumDirection)
/*     */     {
/* 634 */       StructureBoundingBox localStructureBoundingBox = new StructureBoundingBox(paramInt1, paramInt2 - 5, paramInt3, paramInt1, paramInt2 + 2, paramInt3);
/*     */       
/* 636 */       switch (WorldGenMineshaftPieces.1.a[paramEnumDirection.ordinal()]) {
/*     */       case 1: 
/* 638 */         localStructureBoundingBox.d = (paramInt1 + 2);
/* 639 */         localStructureBoundingBox.c = (paramInt3 - 8);
/* 640 */         break;
/*     */       case 2: 
/* 642 */         localStructureBoundingBox.d = (paramInt1 + 2);
/* 643 */         localStructureBoundingBox.f = (paramInt3 + 8);
/* 644 */         break;
/*     */       case 3: 
/* 646 */         localStructureBoundingBox.a = (paramInt1 - 8);
/* 647 */         localStructureBoundingBox.f = (paramInt3 + 2);
/* 648 */         break;
/*     */       case 4: 
/* 650 */         localStructureBoundingBox.d = (paramInt1 + 8);
/* 651 */         localStructureBoundingBox.f = (paramInt3 + 2);
/*     */       }
/*     */       
/*     */       
/* 655 */       if (StructurePiece.a(paramList, localStructureBoundingBox) != null) {
/* 656 */         return null;
/*     */       }
/*     */       
/* 659 */       return localStructureBoundingBox;
/*     */     }
/*     */     
/*     */     public void a(StructurePiece paramStructurePiece, List<StructurePiece> paramList, Random paramRandom)
/*     */     {
/* 664 */       int i = d();
/*     */       
/*     */ 
/* 667 */       if (this.m != null) {
/* 668 */         switch (WorldGenMineshaftPieces.1.a[this.m.ordinal()]) {
/*     */         case 1: 
/* 670 */           WorldGenMineshaftPieces.a(paramStructurePiece, paramList, paramRandom, this.l.a, this.l.b, this.l.c - 1, EnumDirection.NORTH, i);
/* 671 */           break;
/*     */         case 2: 
/* 673 */           WorldGenMineshaftPieces.a(paramStructurePiece, paramList, paramRandom, this.l.a, this.l.b, this.l.f + 1, EnumDirection.SOUTH, i);
/* 674 */           break;
/*     */         case 3: 
/* 676 */           WorldGenMineshaftPieces.a(paramStructurePiece, paramList, paramRandom, this.l.a - 1, this.l.b, this.l.c, EnumDirection.WEST, i);
/* 677 */           break;
/*     */         case 4: 
/* 679 */           WorldGenMineshaftPieces.a(paramStructurePiece, paramList, paramRandom, this.l.d + 1, this.l.b, this.l.c, EnumDirection.EAST, i);
/*     */         }
/*     */         
/*     */       }
/*     */     }
/*     */     
/*     */     public boolean a(World paramWorld, Random paramRandom, StructureBoundingBox paramStructureBoundingBox)
/*     */     {
/* 687 */       if (a(paramWorld, paramStructureBoundingBox)) {
/* 688 */         return false;
/*     */       }
/*     */       
/*     */ 
/* 692 */       a(paramWorld, paramStructureBoundingBox, 0, 5, 0, 2, 7, 1, Blocks.AIR.getBlockData(), Blocks.AIR.getBlockData(), false);
/*     */       
/* 694 */       a(paramWorld, paramStructureBoundingBox, 0, 0, 7, 2, 2, 8, Blocks.AIR.getBlockData(), Blocks.AIR.getBlockData(), false);
/*     */       
/* 696 */       for (int i = 0; i < 5; i++) {
/* 697 */         a(paramWorld, paramStructureBoundingBox, 0, 5 - i - (i < 4 ? 1 : 0), 2 + i, 2, 7 - i, 2 + i, Blocks.AIR.getBlockData(), Blocks.AIR.getBlockData(), false);
/*     */       }
/*     */       
/* 700 */       return true;
/*     */     }
/*     */   }
/*     */   
/*     */ 
/* 705 */   private static final List<StructurePieceTreasure> a = Lists.newArrayList(new StructurePieceTreasure[] { new StructurePieceTreasure(Items.IRON_INGOT, 0, 1, 5, 10), new StructurePieceTreasure(Items.GOLD_INGOT, 0, 1, 3, 5), new StructurePieceTreasure(Items.REDSTONE, 0, 4, 9, 5), new StructurePieceTreasure(Items.DYE, EnumColor.BLUE.getInvColorIndex(), 4, 9, 5), new StructurePieceTreasure(Items.DIAMOND, 0, 1, 2, 3), new StructurePieceTreasure(Items.COAL, 0, 3, 8, 10), new StructurePieceTreasure(Items.BREAD, 0, 1, 3, 15), new StructurePieceTreasure(Items.IRON_PICKAXE, 0, 1, 1, 1), new StructurePieceTreasure(Item.getItemOf(Blocks.RAIL), 0, 4, 8, 1), new StructurePieceTreasure(Items.MELON_SEEDS, 0, 2, 4, 10), new StructurePieceTreasure(Items.PUMPKIN_SEEDS, 0, 2, 4, 10), new StructurePieceTreasure(Items.SADDLE, 0, 1, 1, 3), new StructurePieceTreasure(Items.IRON_HORSE_ARMOR, 0, 1, 1, 1) });
/*     */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_8_R3\WorldGenMineshaftPieces.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */
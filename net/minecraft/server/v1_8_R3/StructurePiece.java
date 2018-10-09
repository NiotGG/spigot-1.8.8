/*     */ package net.minecraft.server.v1_8_R3;
/*     */ 
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
/*     */ public abstract class StructurePiece
/*     */ {
/*     */   protected StructureBoundingBox l;
/*     */   protected EnumDirection m;
/*     */   protected int n;
/*     */   
/*     */   public StructurePiece() {}
/*     */   
/*     */   protected StructurePiece(int paramInt)
/*     */   {
/*  59 */     this.n = paramInt;
/*     */   }
/*     */   
/*     */   public NBTTagCompound b() {
/*  63 */     NBTTagCompound localNBTTagCompound = new NBTTagCompound();
/*     */     
/*  65 */     localNBTTagCompound.setString("id", WorldGenFactory.a(this));
/*  66 */     localNBTTagCompound.set("BB", this.l.g());
/*  67 */     localNBTTagCompound.setInt("O", this.m == null ? -1 : this.m.b());
/*  68 */     localNBTTagCompound.setInt("GD", this.n);
/*     */     
/*  70 */     a(localNBTTagCompound);
/*     */     
/*  72 */     return localNBTTagCompound;
/*     */   }
/*     */   
/*     */   protected abstract void a(NBTTagCompound paramNBTTagCompound);
/*     */   
/*     */   public void a(World paramWorld, NBTTagCompound paramNBTTagCompound) {
/*  78 */     if (paramNBTTagCompound.hasKey("BB")) {
/*  79 */       this.l = new StructureBoundingBox(paramNBTTagCompound.getIntArray("BB"));
/*     */     }
/*  81 */     int i = paramNBTTagCompound.getInt("O");
/*  82 */     this.m = (i == -1 ? null : EnumDirection.fromType2(i));
/*  83 */     this.n = paramNBTTagCompound.getInt("GD");
/*     */     
/*  85 */     b(paramNBTTagCompound);
/*     */   }
/*     */   
/*     */   protected abstract void b(NBTTagCompound paramNBTTagCompound);
/*     */   
/*     */   public void a(StructurePiece paramStructurePiece, List<StructurePiece> paramList, Random paramRandom) {}
/*     */   
/*     */   public abstract boolean a(World paramWorld, Random paramRandom, StructureBoundingBox paramStructureBoundingBox);
/*     */   
/*     */   public StructureBoundingBox c()
/*     */   {
/*  96 */     return this.l;
/*     */   }
/*     */   
/*     */   public int d() {
/* 100 */     return this.n;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public static StructurePiece a(List<StructurePiece> paramList, StructureBoundingBox paramStructureBoundingBox)
/*     */   {
/* 111 */     for (StructurePiece localStructurePiece : paramList) {
/* 112 */       if ((localStructurePiece.c() != null) && (localStructurePiece.c().a(paramStructureBoundingBox))) {
/* 113 */         return localStructurePiece;
/*     */       }
/*     */     }
/* 116 */     return null;
/*     */   }
/*     */   
/*     */   public BlockPosition a() {
/* 120 */     return new BlockPosition(this.l.f());
/*     */   }
/*     */   
/*     */   protected boolean a(World paramWorld, StructureBoundingBox paramStructureBoundingBox) {
/* 124 */     int i = Math.max(this.l.a - 1, paramStructureBoundingBox.a);
/* 125 */     int j = Math.max(this.l.b - 1, paramStructureBoundingBox.b);
/* 126 */     int k = Math.max(this.l.c - 1, paramStructureBoundingBox.c);
/* 127 */     int i1 = Math.min(this.l.d + 1, paramStructureBoundingBox.d);
/* 128 */     int i2 = Math.min(this.l.e + 1, paramStructureBoundingBox.e);
/* 129 */     int i3 = Math.min(this.l.f + 1, paramStructureBoundingBox.f);
/*     */     
/* 131 */     BlockPosition.MutableBlockPosition localMutableBlockPosition = new BlockPosition.MutableBlockPosition();
/*     */     
/*     */     int i5;
/* 134 */     for (int i4 = i; i4 <= i1; i4++) {
/* 135 */       for (i5 = k; i5 <= i3; i5++) {
/* 136 */         if (paramWorld.getType(localMutableBlockPosition.c(i4, j, i5)).getBlock().getMaterial().isLiquid()) {
/* 137 */           return true;
/*     */         }
/* 139 */         if (paramWorld.getType(localMutableBlockPosition.c(i4, i2, i5)).getBlock().getMaterial().isLiquid()) {
/* 140 */           return true;
/*     */         }
/*     */       }
/*     */     }
/*     */     
/* 145 */     for (i4 = i; i4 <= i1; i4++) {
/* 146 */       for (i5 = j; i5 <= i2; i5++) {
/* 147 */         if (paramWorld.getType(localMutableBlockPosition.c(i4, i5, k)).getBlock().getMaterial().isLiquid()) {
/* 148 */           return true;
/*     */         }
/* 150 */         if (paramWorld.getType(localMutableBlockPosition.c(i4, i5, i3)).getBlock().getMaterial().isLiquid()) {
/* 151 */           return true;
/*     */         }
/*     */       }
/*     */     }
/*     */     
/* 156 */     for (i4 = k; i4 <= i3; i4++) {
/* 157 */       for (i5 = j; i5 <= i2; i5++) {
/* 158 */         if (paramWorld.getType(localMutableBlockPosition.c(i, i5, i4)).getBlock().getMaterial().isLiquid()) {
/* 159 */           return true;
/*     */         }
/* 161 */         if (paramWorld.getType(localMutableBlockPosition.c(i1, i5, i4)).getBlock().getMaterial().isLiquid()) {
/* 162 */           return true;
/*     */         }
/*     */       }
/*     */     }
/* 166 */     return false;
/*     */   }
/*     */   
/*     */   protected int a(int paramInt1, int paramInt2) {
/* 170 */     if (this.m == null) {
/* 171 */       return paramInt1;
/*     */     }
/*     */     
/* 174 */     switch (1.a[this.m.ordinal()]) {
/*     */     case 1: 
/*     */     case 2: 
/* 177 */       return this.l.a + paramInt1;
/*     */     case 3: 
/* 179 */       return this.l.d - paramInt2;
/*     */     case 4: 
/* 181 */       return this.l.a + paramInt2;
/*     */     }
/* 183 */     return paramInt1;
/*     */   }
/*     */   
/*     */   protected int d(int paramInt)
/*     */   {
/* 188 */     if (this.m == null) {
/* 189 */       return paramInt;
/*     */     }
/* 191 */     return paramInt + this.l.b;
/*     */   }
/*     */   
/*     */   protected int b(int paramInt1, int paramInt2) {
/* 195 */     if (this.m == null) {
/* 196 */       return paramInt2;
/*     */     }
/*     */     
/* 199 */     switch (1.a[this.m.ordinal()]) {
/*     */     case 1: 
/* 201 */       return this.l.f - paramInt2;
/*     */     case 2: 
/* 203 */       return this.l.c + paramInt2;
/*     */     case 3: 
/*     */     case 4: 
/* 206 */       return this.l.c + paramInt1;
/*     */     }
/* 208 */     return paramInt2;
/*     */   }
/*     */   
/*     */ 
/*     */   protected int a(Block paramBlock, int paramInt)
/*     */   {
/* 214 */     if (paramBlock == Blocks.RAIL) {
/* 215 */       if ((this.m == EnumDirection.WEST) || (this.m == EnumDirection.EAST)) {
/* 216 */         if (paramInt == 1) {
/* 217 */           return 0;
/*     */         }
/* 219 */         return 1;
/*     */       }
/*     */     }
/* 222 */     else if ((paramBlock instanceof BlockDoor)) {
/* 223 */       if (this.m == EnumDirection.SOUTH) {
/* 224 */         if (paramInt == 0) {
/* 225 */           return 2;
/*     */         }
/* 227 */         if (paramInt == 2)
/* 228 */           return 0;
/*     */       } else {
/* 230 */         if (this.m == EnumDirection.WEST)
/*     */         {
/*     */ 
/*     */ 
/*     */ 
/* 235 */           return paramInt + 1 & 0x3; }
/* 236 */         if (this.m == EnumDirection.EAST)
/*     */         {
/*     */ 
/*     */ 
/*     */ 
/* 241 */           return paramInt + 3 & 0x3; }
/*     */       }
/* 243 */     } else if ((paramBlock == Blocks.STONE_STAIRS) || (paramBlock == Blocks.OAK_STAIRS) || (paramBlock == Blocks.NETHER_BRICK_STAIRS) || (paramBlock == Blocks.STONE_BRICK_STAIRS) || (paramBlock == Blocks.SANDSTONE_STAIRS)) {
/* 244 */       if (this.m == EnumDirection.SOUTH) {
/* 245 */         if (paramInt == 2) {
/* 246 */           return 3;
/*     */         }
/* 248 */         if (paramInt == 3) {
/* 249 */           return 2;
/*     */         }
/* 251 */       } else if (this.m == EnumDirection.WEST) {
/* 252 */         if (paramInt == 0) {
/* 253 */           return 2;
/*     */         }
/* 255 */         if (paramInt == 1) {
/* 256 */           return 3;
/*     */         }
/* 258 */         if (paramInt == 2) {
/* 259 */           return 0;
/*     */         }
/* 261 */         if (paramInt == 3) {
/* 262 */           return 1;
/*     */         }
/* 264 */       } else if (this.m == EnumDirection.EAST) {
/* 265 */         if (paramInt == 0) {
/* 266 */           return 2;
/*     */         }
/* 268 */         if (paramInt == 1) {
/* 269 */           return 3;
/*     */         }
/* 271 */         if (paramInt == 2) {
/* 272 */           return 1;
/*     */         }
/* 274 */         if (paramInt == 3) {
/* 275 */           return 0;
/*     */         }
/*     */       }
/* 278 */     } else if (paramBlock == Blocks.LADDER) {
/* 279 */       if (this.m == EnumDirection.SOUTH) {
/* 280 */         if (paramInt == EnumDirection.NORTH.a()) {
/* 281 */           return EnumDirection.SOUTH.a();
/*     */         }
/* 283 */         if (paramInt == EnumDirection.SOUTH.a()) {
/* 284 */           return EnumDirection.NORTH.a();
/*     */         }
/* 286 */       } else if (this.m == EnumDirection.WEST) {
/* 287 */         if (paramInt == EnumDirection.NORTH.a()) {
/* 288 */           return EnumDirection.WEST.a();
/*     */         }
/* 290 */         if (paramInt == EnumDirection.SOUTH.a()) {
/* 291 */           return EnumDirection.EAST.a();
/*     */         }
/* 293 */         if (paramInt == EnumDirection.WEST.a()) {
/* 294 */           return EnumDirection.NORTH.a();
/*     */         }
/* 296 */         if (paramInt == EnumDirection.EAST.a()) {
/* 297 */           return EnumDirection.SOUTH.a();
/*     */         }
/* 299 */       } else if (this.m == EnumDirection.EAST)
/*     */       {
/* 301 */         if (paramInt == EnumDirection.NORTH.a()) {
/* 302 */           return EnumDirection.EAST.a();
/*     */         }
/* 304 */         if (paramInt == EnumDirection.SOUTH.a()) {
/* 305 */           return EnumDirection.WEST.a();
/*     */         }
/* 307 */         if (paramInt == EnumDirection.WEST.a()) {
/* 308 */           return EnumDirection.NORTH.a();
/*     */         }
/* 310 */         if (paramInt == EnumDirection.EAST.a()) {
/* 311 */           return EnumDirection.SOUTH.a();
/*     */         }
/*     */       }
/* 314 */     } else if (paramBlock == Blocks.STONE_BUTTON) {
/* 315 */       if (this.m == EnumDirection.SOUTH) {
/* 316 */         if (paramInt == 3) {
/* 317 */           return 4;
/*     */         }
/* 319 */         if (paramInt == 4) {
/* 320 */           return 3;
/*     */         }
/* 322 */       } else if (this.m == EnumDirection.WEST) {
/* 323 */         if (paramInt == 3) {
/* 324 */           return 1;
/*     */         }
/* 326 */         if (paramInt == 4) {
/* 327 */           return 2;
/*     */         }
/* 329 */         if (paramInt == 2) {
/* 330 */           return 3;
/*     */         }
/* 332 */         if (paramInt == 1) {
/* 333 */           return 4;
/*     */         }
/* 335 */       } else if (this.m == EnumDirection.EAST) {
/* 336 */         if (paramInt == 3) {
/* 337 */           return 2;
/*     */         }
/* 339 */         if (paramInt == 4) {
/* 340 */           return 1;
/*     */         }
/* 342 */         if (paramInt == 2) {
/* 343 */           return 3;
/*     */         }
/* 345 */         if (paramInt == 1) {
/* 346 */           return 4;
/*     */         }
/*     */       }
/* 349 */     } else if ((paramBlock == Blocks.TRIPWIRE_HOOK) || ((paramBlock instanceof BlockDirectional))) {
/* 350 */       EnumDirection localEnumDirection = EnumDirection.fromType2(paramInt);
/* 351 */       if (this.m == EnumDirection.SOUTH) {
/* 352 */         if ((localEnumDirection == EnumDirection.SOUTH) || (localEnumDirection == EnumDirection.NORTH)) {
/* 353 */           return localEnumDirection.opposite().b();
/*     */         }
/* 355 */       } else if (this.m == EnumDirection.WEST) {
/* 356 */         if (localEnumDirection == EnumDirection.NORTH) {
/* 357 */           return EnumDirection.WEST.b();
/*     */         }
/* 359 */         if (localEnumDirection == EnumDirection.SOUTH) {
/* 360 */           return EnumDirection.EAST.b();
/*     */         }
/* 362 */         if (localEnumDirection == EnumDirection.WEST) {
/* 363 */           return EnumDirection.NORTH.b();
/*     */         }
/* 365 */         if (localEnumDirection == EnumDirection.EAST) {
/* 366 */           return EnumDirection.SOUTH.b();
/*     */         }
/* 368 */       } else if (this.m == EnumDirection.EAST)
/*     */       {
/* 370 */         if (localEnumDirection == EnumDirection.NORTH) {
/* 371 */           return EnumDirection.EAST.b();
/*     */         }
/* 373 */         if (localEnumDirection == EnumDirection.SOUTH) {
/* 374 */           return EnumDirection.WEST.b();
/*     */         }
/* 376 */         if (localEnumDirection == EnumDirection.WEST) {
/* 377 */           return EnumDirection.NORTH.b();
/*     */         }
/* 379 */         if (localEnumDirection == EnumDirection.EAST) {
/* 380 */           return EnumDirection.SOUTH.b();
/*     */         }
/*     */       }
/* 383 */     } else if ((paramBlock == Blocks.PISTON) || (paramBlock == Blocks.STICKY_PISTON) || (paramBlock == Blocks.LEVER) || (paramBlock == Blocks.DISPENSER)) {
/* 384 */       if (this.m == EnumDirection.SOUTH) {
/* 385 */         if ((paramInt == EnumDirection.NORTH.a()) || (paramInt == EnumDirection.SOUTH.a())) {
/* 386 */           return EnumDirection.fromType1(paramInt).opposite().a();
/*     */         }
/* 388 */       } else if (this.m == EnumDirection.WEST) {
/* 389 */         if (paramInt == EnumDirection.NORTH.a()) {
/* 390 */           return EnumDirection.WEST.a();
/*     */         }
/* 392 */         if (paramInt == EnumDirection.SOUTH.a()) {
/* 393 */           return EnumDirection.EAST.a();
/*     */         }
/* 395 */         if (paramInt == EnumDirection.WEST.a()) {
/* 396 */           return EnumDirection.NORTH.a();
/*     */         }
/* 398 */         if (paramInt == EnumDirection.EAST.a()) {
/* 399 */           return EnumDirection.SOUTH.a();
/*     */         }
/* 401 */       } else if (this.m == EnumDirection.EAST) {
/* 402 */         if (paramInt == EnumDirection.NORTH.a()) {
/* 403 */           return EnumDirection.EAST.a();
/*     */         }
/* 405 */         if (paramInt == EnumDirection.SOUTH.a()) {
/* 406 */           return EnumDirection.WEST.a();
/*     */         }
/* 408 */         if (paramInt == EnumDirection.WEST.a()) {
/* 409 */           return EnumDirection.NORTH.a();
/*     */         }
/* 411 */         if (paramInt == EnumDirection.EAST.a()) {
/* 412 */           return EnumDirection.SOUTH.a();
/*     */         }
/*     */       }
/*     */     }
/* 416 */     return paramInt;
/*     */   }
/*     */   
/*     */   protected void a(World paramWorld, IBlockData paramIBlockData, int paramInt1, int paramInt2, int paramInt3, StructureBoundingBox paramStructureBoundingBox) {
/* 420 */     BlockPosition localBlockPosition = new BlockPosition(a(paramInt1, paramInt3), d(paramInt2), b(paramInt1, paramInt3));
/*     */     
/* 422 */     if (!paramStructureBoundingBox.b(localBlockPosition)) {
/* 423 */       return;
/*     */     }
/*     */     
/* 426 */     paramWorld.setTypeAndData(localBlockPosition, paramIBlockData, 2);
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
/*     */   protected IBlockData a(World paramWorld, int paramInt1, int paramInt2, int paramInt3, StructureBoundingBox paramStructureBoundingBox)
/*     */   {
/* 442 */     int i = a(paramInt1, paramInt3);
/* 443 */     int j = d(paramInt2);
/* 444 */     int k = b(paramInt1, paramInt3);
/*     */     
/* 446 */     BlockPosition localBlockPosition = new BlockPosition(i, j, k);
/* 447 */     if (!paramStructureBoundingBox.b(localBlockPosition)) {
/* 448 */       return Blocks.AIR.getBlockData();
/*     */     }
/*     */     
/* 451 */     return paramWorld.getType(localBlockPosition);
/*     */   }
/*     */   
/*     */   protected void a(World paramWorld, StructureBoundingBox paramStructureBoundingBox, int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5, int paramInt6) {
/* 455 */     for (int i = paramInt2; i <= paramInt5; i++) {
/* 456 */       for (int j = paramInt1; j <= paramInt4; j++) {
/* 457 */         for (int k = paramInt3; k <= paramInt6; k++) {
/* 458 */           a(paramWorld, Blocks.AIR.getBlockData(), j, i, k, paramStructureBoundingBox);
/*     */         }
/*     */       }
/*     */     }
/*     */   }
/*     */   
/*     */   protected void a(World paramWorld, StructureBoundingBox paramStructureBoundingBox, int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5, int paramInt6, IBlockData paramIBlockData1, IBlockData paramIBlockData2, boolean paramBoolean) {
/* 465 */     for (int i = paramInt2; i <= paramInt5; i++) {
/* 466 */       for (int j = paramInt1; j <= paramInt4; j++) {
/* 467 */         for (int k = paramInt3; k <= paramInt6; k++) {
/* 468 */           if ((!paramBoolean) || (a(paramWorld, j, i, k, paramStructureBoundingBox).getBlock().getMaterial() != Material.AIR))
/*     */           {
/*     */ 
/* 471 */             if ((i == paramInt2) || (i == paramInt5) || (j == paramInt1) || (j == paramInt4) || (k == paramInt3) || (k == paramInt6)) {
/* 472 */               a(paramWorld, paramIBlockData1, j, i, k, paramStructureBoundingBox);
/*     */             } else {
/* 474 */               a(paramWorld, paramIBlockData2, j, i, k, paramStructureBoundingBox);
/*     */             }
/*     */           }
/*     */         }
/*     */       }
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   protected void a(World paramWorld, StructureBoundingBox paramStructureBoundingBox, int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5, int paramInt6, boolean paramBoolean, Random paramRandom, StructurePieceBlockSelector paramStructurePieceBlockSelector)
/*     */   {
/* 486 */     for (int i = paramInt2; i <= paramInt5; i++) {
/* 487 */       for (int j = paramInt1; j <= paramInt4; j++) {
/* 488 */         for (int k = paramInt3; k <= paramInt6; k++) {
/* 489 */           if ((!paramBoolean) || (a(paramWorld, j, i, k, paramStructureBoundingBox).getBlock().getMaterial() != Material.AIR))
/*     */           {
/*     */ 
/* 492 */             paramStructurePieceBlockSelector.a(paramRandom, j, i, k, (i == paramInt2) || (i == paramInt5) || (j == paramInt1) || (j == paramInt4) || (k == paramInt3) || (k == paramInt6));
/* 493 */             a(paramWorld, paramStructurePieceBlockSelector.a(), j, i, k, paramStructureBoundingBox);
/*     */           }
/*     */         }
/*     */       }
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   protected void a(World paramWorld, StructureBoundingBox paramStructureBoundingBox, Random paramRandom, float paramFloat, int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5, int paramInt6, IBlockData paramIBlockData1, IBlockData paramIBlockData2, boolean paramBoolean)
/*     */   {
/* 504 */     for (int i = paramInt2; i <= paramInt5; i++) {
/* 505 */       for (int j = paramInt1; j <= paramInt4; j++) {
/* 506 */         for (int k = paramInt3; k <= paramInt6; k++) {
/* 507 */           if (paramRandom.nextFloat() <= paramFloat)
/*     */           {
/*     */ 
/* 510 */             if ((!paramBoolean) || (a(paramWorld, j, i, k, paramStructureBoundingBox).getBlock().getMaterial() != Material.AIR))
/*     */             {
/*     */ 
/* 513 */               if ((i == paramInt2) || (i == paramInt5) || (j == paramInt1) || (j == paramInt4) || (k == paramInt3) || (k == paramInt6)) {
/* 514 */                 a(paramWorld, paramIBlockData1, j, i, k, paramStructureBoundingBox);
/*     */               } else
/* 516 */                 a(paramWorld, paramIBlockData2, j, i, k, paramStructureBoundingBox); }
/*     */           }
/*     */         }
/*     */       }
/*     */     }
/*     */   }
/*     */   
/*     */   protected void a(World paramWorld, StructureBoundingBox paramStructureBoundingBox, Random paramRandom, float paramFloat, int paramInt1, int paramInt2, int paramInt3, IBlockData paramIBlockData) {
/* 524 */     if (paramRandom.nextFloat() < paramFloat) {
/* 525 */       a(paramWorld, paramIBlockData, paramInt1, paramInt2, paramInt3, paramStructureBoundingBox);
/*     */     }
/*     */   }
/*     */   
/*     */   protected void a(World paramWorld, StructureBoundingBox paramStructureBoundingBox, int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5, int paramInt6, IBlockData paramIBlockData, boolean paramBoolean) {
/* 530 */     float f1 = paramInt4 - paramInt1 + 1;
/* 531 */     float f2 = paramInt5 - paramInt2 + 1;
/* 532 */     float f3 = paramInt6 - paramInt3 + 1;
/* 533 */     float f4 = paramInt1 + f1 / 2.0F;
/* 534 */     float f5 = paramInt3 + f3 / 2.0F;
/*     */     
/* 536 */     for (int i = paramInt2; i <= paramInt5; i++) {
/* 537 */       float f6 = (i - paramInt2) / f2;
/*     */       
/* 539 */       for (int j = paramInt1; j <= paramInt4; j++) {
/* 540 */         float f7 = (j - f4) / (f1 * 0.5F);
/*     */         
/* 542 */         for (int k = paramInt3; k <= paramInt6; k++) {
/* 543 */           float f8 = (k - f5) / (f3 * 0.5F);
/*     */           
/* 545 */           if ((!paramBoolean) || (a(paramWorld, j, i, k, paramStructureBoundingBox).getBlock().getMaterial() != Material.AIR))
/*     */           {
/*     */ 
/*     */ 
/* 549 */             float f9 = f7 * f7 + f6 * f6 + f8 * f8;
/*     */             
/* 551 */             if (f9 <= 1.05F)
/* 552 */               a(paramWorld, paramIBlockData, j, i, k, paramStructureBoundingBox);
/*     */           }
/*     */         }
/*     */       }
/*     */     }
/*     */   }
/*     */   
/*     */   protected void b(World paramWorld, int paramInt1, int paramInt2, int paramInt3, StructureBoundingBox paramStructureBoundingBox) {
/* 560 */     BlockPosition localBlockPosition = new BlockPosition(a(paramInt1, paramInt3), d(paramInt2), b(paramInt1, paramInt3));
/*     */     
/* 562 */     if (!paramStructureBoundingBox.b(localBlockPosition)) {
/* 563 */       return;
/*     */     }
/*     */     
/* 566 */     while ((!paramWorld.isEmpty(localBlockPosition)) && (localBlockPosition.getY() < 255)) {
/* 567 */       paramWorld.setTypeAndData(localBlockPosition, Blocks.AIR.getBlockData(), 2);
/* 568 */       localBlockPosition = localBlockPosition.up();
/*     */     }
/*     */   }
/*     */   
/*     */   protected void b(World paramWorld, IBlockData paramIBlockData, int paramInt1, int paramInt2, int paramInt3, StructureBoundingBox paramStructureBoundingBox) {
/* 573 */     int i = a(paramInt1, paramInt3);
/* 574 */     int j = d(paramInt2);
/* 575 */     int k = b(paramInt1, paramInt3);
/*     */     
/* 577 */     if (!paramStructureBoundingBox.b(new BlockPosition(i, j, k))) {
/* 578 */       return;
/*     */     }
/*     */     
/* 581 */     while (((paramWorld.isEmpty(new BlockPosition(i, j, k))) || (paramWorld.getType(new BlockPosition(i, j, k)).getBlock().getMaterial().isLiquid())) && (j > 1)) {
/* 582 */       paramWorld.setTypeAndData(new BlockPosition(i, j, k), paramIBlockData, 2);
/* 583 */       j--;
/*     */     }
/*     */   }
/*     */   
/*     */   protected boolean a(World paramWorld, StructureBoundingBox paramStructureBoundingBox, Random paramRandom, int paramInt1, int paramInt2, int paramInt3, List<StructurePieceTreasure> paramList, int paramInt4) {
/* 588 */     BlockPosition localBlockPosition = new BlockPosition(a(paramInt1, paramInt3), d(paramInt2), b(paramInt1, paramInt3));
/*     */     
/* 590 */     if ((paramStructureBoundingBox.b(localBlockPosition)) && 
/* 591 */       (paramWorld.getType(localBlockPosition).getBlock() != Blocks.CHEST)) {
/* 592 */       IBlockData localIBlockData = Blocks.CHEST.getBlockData();
/* 593 */       paramWorld.setTypeAndData(localBlockPosition, Blocks.CHEST.f(paramWorld, localBlockPosition, localIBlockData), 2);
/*     */       
/* 595 */       TileEntity localTileEntity = paramWorld.getTileEntity(localBlockPosition);
/* 596 */       if ((localTileEntity instanceof TileEntityChest)) {
/* 597 */         StructurePieceTreasure.a(paramRandom, paramList, (TileEntityChest)localTileEntity, paramInt4);
/*     */       }
/* 599 */       return true;
/*     */     }
/*     */     
/* 602 */     return false;
/*     */   }
/*     */   
/*     */   protected boolean a(World paramWorld, StructureBoundingBox paramStructureBoundingBox, Random paramRandom, int paramInt1, int paramInt2, int paramInt3, int paramInt4, List<StructurePieceTreasure> paramList, int paramInt5) {
/* 606 */     BlockPosition localBlockPosition = new BlockPosition(a(paramInt1, paramInt3), d(paramInt2), b(paramInt1, paramInt3));
/*     */     
/* 608 */     if ((paramStructureBoundingBox.b(localBlockPosition)) && 
/* 609 */       (paramWorld.getType(localBlockPosition).getBlock() != Blocks.DISPENSER)) {
/* 610 */       paramWorld.setTypeAndData(localBlockPosition, Blocks.DISPENSER.fromLegacyData(a(Blocks.DISPENSER, paramInt4)), 2);
/*     */       
/* 612 */       TileEntity localTileEntity = paramWorld.getTileEntity(localBlockPosition);
/* 613 */       if ((localTileEntity instanceof TileEntityDispenser)) {
/* 614 */         StructurePieceTreasure.a(paramRandom, paramList, (TileEntityDispenser)localTileEntity, paramInt5);
/*     */       }
/* 616 */       return true;
/*     */     }
/*     */     
/* 619 */     return false;
/*     */   }
/*     */   
/*     */   protected void a(World paramWorld, StructureBoundingBox paramStructureBoundingBox, Random paramRandom, int paramInt1, int paramInt2, int paramInt3, EnumDirection paramEnumDirection) {
/* 623 */     BlockPosition localBlockPosition = new BlockPosition(a(paramInt1, paramInt3), d(paramInt2), b(paramInt1, paramInt3));
/*     */     
/* 625 */     if (paramStructureBoundingBox.b(localBlockPosition)) {
/* 626 */       ItemDoor.a(paramWorld, localBlockPosition, paramEnumDirection.f(), Blocks.WOODEN_DOOR);
/*     */     }
/*     */   }
/*     */   
/*     */   public void a(int paramInt1, int paramInt2, int paramInt3) {
/* 631 */     this.l.a(paramInt1, paramInt2, paramInt3);
/*     */   }
/*     */   
/*     */   public static abstract class StructurePieceBlockSelector {
/* 635 */     protected IBlockData a = Blocks.AIR.getBlockData();
/*     */     
/*     */     public abstract void a(Random paramRandom, int paramInt1, int paramInt2, int paramInt3, boolean paramBoolean);
/*     */     
/*     */     public IBlockData a() {
/* 640 */       return this.a;
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_8_R3\StructurePiece.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */
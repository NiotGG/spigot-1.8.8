/*     */ package net.minecraft.server.v1_8_R3;
/*     */ 
/*     */ import java.util.Arrays;
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
/*     */ public class BlockStairs
/*     */   extends Block
/*     */ {
/*  29 */   public static final BlockStateDirection FACING = BlockStateDirection.of("facing", EnumDirection.EnumDirectionLimit.HORIZONTAL);
/*  30 */   public static final BlockStateEnum<EnumHalf> HALF = BlockStateEnum.of("half", EnumHalf.class);
/*  31 */   public static final BlockStateEnum<EnumStairShape> SHAPE = BlockStateEnum.of("shape", EnumStairShape.class);
/*     */   
/*  33 */   private static final int[][] O = { { 4, 5 }, { 5, 7 }, { 6, 7 }, { 4, 6 }, { 0, 1 }, { 1, 3 }, { 2, 3 }, { 0, 2 } };
/*     */   
/*     */ 
/*     */   private final Block P;
/*     */   
/*     */ 
/*     */   private final IBlockData Q;
/*     */   
/*     */ 
/*     */   private boolean R;
/*     */   
/*     */   private int S;
/*     */   
/*     */ 
/*     */   protected BlockStairs(IBlockData paramIBlockData)
/*     */   {
/*  49 */     super(paramIBlockData.getBlock().material);
/*  50 */     j(this.blockStateList.getBlockData().set(FACING, EnumDirection.NORTH).set(HALF, EnumHalf.BOTTOM).set(SHAPE, EnumStairShape.STRAIGHT));
/*  51 */     this.P = paramIBlockData.getBlock();
/*  52 */     this.Q = paramIBlockData;
/*  53 */     c(this.P.strength);
/*  54 */     b(this.P.durability / 3.0F);
/*  55 */     a(this.P.stepSound);
/*  56 */     e(255);
/*  57 */     a(CreativeModeTab.b);
/*     */   }
/*     */   
/*     */   public void updateShape(IBlockAccess paramIBlockAccess, BlockPosition paramBlockPosition)
/*     */   {
/*  62 */     if (this.R) {
/*  63 */       a(0.5F * (this.S % 2), 0.5F * (this.S / 4 % 2), 0.5F * (this.S / 2 % 2), 0.5F + 0.5F * (this.S % 2), 0.5F + 0.5F * (this.S / 4 % 2), 0.5F + 0.5F * (this.S / 2 % 2));
/*     */ 
/*     */ 
/*     */ 
/*     */     }
/*     */     else
/*     */     {
/*     */ 
/*     */ 
/*  72 */       a(0.0F, 0.0F, 0.0F, 1.0F, 1.0F, 1.0F);
/*     */     }
/*     */   }
/*     */   
/*     */   public boolean c()
/*     */   {
/*  78 */     return false;
/*     */   }
/*     */   
/*     */   public boolean d()
/*     */   {
/*  83 */     return false;
/*     */   }
/*     */   
/*     */   public void e(IBlockAccess paramIBlockAccess, BlockPosition paramBlockPosition) {
/*  87 */     if (paramIBlockAccess.getType(paramBlockPosition).get(HALF) == EnumHalf.TOP) {
/*  88 */       a(0.0F, 0.5F, 0.0F, 1.0F, 1.0F, 1.0F);
/*     */     } else {
/*  90 */       a(0.0F, 0.0F, 0.0F, 1.0F, 0.5F, 1.0F);
/*     */     }
/*     */   }
/*     */   
/*     */   public static boolean c(Block paramBlock) {
/*  95 */     return paramBlock instanceof BlockStairs;
/*     */   }
/*     */   
/*     */   public static boolean a(IBlockAccess paramIBlockAccess, BlockPosition paramBlockPosition, IBlockData paramIBlockData) {
/*  99 */     IBlockData localIBlockData = paramIBlockAccess.getType(paramBlockPosition);
/* 100 */     Block localBlock = localIBlockData.getBlock();
/* 101 */     if ((c(localBlock)) && (localIBlockData.get(HALF) == paramIBlockData.get(HALF)) && (localIBlockData.get(FACING) == paramIBlockData.get(FACING))) {
/* 102 */       return true;
/*     */     }
/*     */     
/* 105 */     return false;
/*     */   }
/*     */   
/*     */   public int f(IBlockAccess paramIBlockAccess, BlockPosition paramBlockPosition) {
/* 109 */     IBlockData localIBlockData1 = paramIBlockAccess.getType(paramBlockPosition);
/* 110 */     EnumDirection localEnumDirection1 = (EnumDirection)localIBlockData1.get(FACING);
/* 111 */     EnumHalf localEnumHalf = (EnumHalf)localIBlockData1.get(HALF);
/* 112 */     int i = localEnumHalf == EnumHalf.TOP ? 1 : 0;
/*     */     IBlockData localIBlockData2;
/* 114 */     Block localBlock; EnumDirection localEnumDirection2; if (localEnumDirection1 == EnumDirection.EAST) {
/* 115 */       localIBlockData2 = paramIBlockAccess.getType(paramBlockPosition.east());
/* 116 */       localBlock = localIBlockData2.getBlock();
/* 117 */       if ((c(localBlock)) && (localEnumHalf == localIBlockData2.get(HALF))) {
/* 118 */         localEnumDirection2 = (EnumDirection)localIBlockData2.get(FACING);
/* 119 */         if ((localEnumDirection2 == EnumDirection.NORTH) && (!a(paramIBlockAccess, paramBlockPosition.south(), localIBlockData1)))
/* 120 */           return i != 0 ? 1 : 2;
/* 121 */         if ((localEnumDirection2 == EnumDirection.SOUTH) && (!a(paramIBlockAccess, paramBlockPosition.north(), localIBlockData1))) {
/* 122 */           return i != 0 ? 2 : 1;
/*     */         }
/*     */       }
/* 125 */     } else if (localEnumDirection1 == EnumDirection.WEST) {
/* 126 */       localIBlockData2 = paramIBlockAccess.getType(paramBlockPosition.west());
/* 127 */       localBlock = localIBlockData2.getBlock();
/* 128 */       if ((c(localBlock)) && (localEnumHalf == localIBlockData2.get(HALF))) {
/* 129 */         localEnumDirection2 = (EnumDirection)localIBlockData2.get(FACING);
/* 130 */         if ((localEnumDirection2 == EnumDirection.NORTH) && (!a(paramIBlockAccess, paramBlockPosition.south(), localIBlockData1)))
/* 131 */           return i != 0 ? 2 : 1;
/* 132 */         if ((localEnumDirection2 == EnumDirection.SOUTH) && (!a(paramIBlockAccess, paramBlockPosition.north(), localIBlockData1))) {
/* 133 */           return i != 0 ? 1 : 2;
/*     */         }
/*     */       }
/* 136 */     } else if (localEnumDirection1 == EnumDirection.SOUTH) {
/* 137 */       localIBlockData2 = paramIBlockAccess.getType(paramBlockPosition.south());
/* 138 */       localBlock = localIBlockData2.getBlock();
/* 139 */       if ((c(localBlock)) && (localEnumHalf == localIBlockData2.get(HALF))) {
/* 140 */         localEnumDirection2 = (EnumDirection)localIBlockData2.get(FACING);
/* 141 */         if ((localEnumDirection2 == EnumDirection.WEST) && (!a(paramIBlockAccess, paramBlockPosition.east(), localIBlockData1)))
/* 142 */           return i != 0 ? 2 : 1;
/* 143 */         if ((localEnumDirection2 == EnumDirection.EAST) && (!a(paramIBlockAccess, paramBlockPosition.west(), localIBlockData1))) {
/* 144 */           return i != 0 ? 1 : 2;
/*     */         }
/*     */       }
/* 147 */     } else if (localEnumDirection1 == EnumDirection.NORTH) {
/* 148 */       localIBlockData2 = paramIBlockAccess.getType(paramBlockPosition.north());
/* 149 */       localBlock = localIBlockData2.getBlock();
/* 150 */       if ((c(localBlock)) && (localEnumHalf == localIBlockData2.get(HALF))) {
/* 151 */         localEnumDirection2 = (EnumDirection)localIBlockData2.get(FACING);
/* 152 */         if ((localEnumDirection2 == EnumDirection.WEST) && (!a(paramIBlockAccess, paramBlockPosition.east(), localIBlockData1)))
/* 153 */           return i != 0 ? 1 : 2;
/* 154 */         if ((localEnumDirection2 == EnumDirection.EAST) && (!a(paramIBlockAccess, paramBlockPosition.west(), localIBlockData1))) {
/* 155 */           return i != 0 ? 2 : 1;
/*     */         }
/*     */       }
/*     */     }
/*     */     
/* 160 */     return 0;
/*     */   }
/*     */   
/*     */   public int g(IBlockAccess paramIBlockAccess, BlockPosition paramBlockPosition) {
/* 164 */     IBlockData localIBlockData1 = paramIBlockAccess.getType(paramBlockPosition);
/* 165 */     EnumDirection localEnumDirection1 = (EnumDirection)localIBlockData1.get(FACING);
/* 166 */     EnumHalf localEnumHalf = (EnumHalf)localIBlockData1.get(HALF);
/* 167 */     int i = localEnumHalf == EnumHalf.TOP ? 1 : 0;
/*     */     IBlockData localIBlockData2;
/* 169 */     Block localBlock; EnumDirection localEnumDirection2; if (localEnumDirection1 == EnumDirection.EAST) {
/* 170 */       localIBlockData2 = paramIBlockAccess.getType(paramBlockPosition.west());
/* 171 */       localBlock = localIBlockData2.getBlock();
/* 172 */       if ((c(localBlock)) && (localEnumHalf == localIBlockData2.get(HALF))) {
/* 173 */         localEnumDirection2 = (EnumDirection)localIBlockData2.get(FACING);
/* 174 */         if ((localEnumDirection2 == EnumDirection.NORTH) && (!a(paramIBlockAccess, paramBlockPosition.north(), localIBlockData1)))
/* 175 */           return i != 0 ? 1 : 2;
/* 176 */         if ((localEnumDirection2 == EnumDirection.SOUTH) && (!a(paramIBlockAccess, paramBlockPosition.south(), localIBlockData1))) {
/* 177 */           return i != 0 ? 2 : 1;
/*     */         }
/*     */       }
/* 180 */     } else if (localEnumDirection1 == EnumDirection.WEST) {
/* 181 */       localIBlockData2 = paramIBlockAccess.getType(paramBlockPosition.east());
/* 182 */       localBlock = localIBlockData2.getBlock();
/* 183 */       if ((c(localBlock)) && (localEnumHalf == localIBlockData2.get(HALF))) {
/* 184 */         localEnumDirection2 = (EnumDirection)localIBlockData2.get(FACING);
/* 185 */         if ((localEnumDirection2 == EnumDirection.NORTH) && (!a(paramIBlockAccess, paramBlockPosition.north(), localIBlockData1)))
/* 186 */           return i != 0 ? 2 : 1;
/* 187 */         if ((localEnumDirection2 == EnumDirection.SOUTH) && (!a(paramIBlockAccess, paramBlockPosition.south(), localIBlockData1))) {
/* 188 */           return i != 0 ? 1 : 2;
/*     */         }
/*     */       }
/* 191 */     } else if (localEnumDirection1 == EnumDirection.SOUTH) {
/* 192 */       localIBlockData2 = paramIBlockAccess.getType(paramBlockPosition.north());
/* 193 */       localBlock = localIBlockData2.getBlock();
/* 194 */       if ((c(localBlock)) && (localEnumHalf == localIBlockData2.get(HALF))) {
/* 195 */         localEnumDirection2 = (EnumDirection)localIBlockData2.get(FACING);
/* 196 */         if ((localEnumDirection2 == EnumDirection.WEST) && (!a(paramIBlockAccess, paramBlockPosition.west(), localIBlockData1)))
/* 197 */           return i != 0 ? 2 : 1;
/* 198 */         if ((localEnumDirection2 == EnumDirection.EAST) && (!a(paramIBlockAccess, paramBlockPosition.east(), localIBlockData1))) {
/* 199 */           return i != 0 ? 1 : 2;
/*     */         }
/*     */       }
/* 202 */     } else if (localEnumDirection1 == EnumDirection.NORTH) {
/* 203 */       localIBlockData2 = paramIBlockAccess.getType(paramBlockPosition.south());
/* 204 */       localBlock = localIBlockData2.getBlock();
/* 205 */       if ((c(localBlock)) && (localEnumHalf == localIBlockData2.get(HALF))) {
/* 206 */         localEnumDirection2 = (EnumDirection)localIBlockData2.get(FACING);
/* 207 */         if ((localEnumDirection2 == EnumDirection.WEST) && (!a(paramIBlockAccess, paramBlockPosition.west(), localIBlockData1)))
/* 208 */           return i != 0 ? 1 : 2;
/* 209 */         if ((localEnumDirection2 == EnumDirection.EAST) && (!a(paramIBlockAccess, paramBlockPosition.east(), localIBlockData1))) {
/* 210 */           return i != 0 ? 2 : 1;
/*     */         }
/*     */       }
/*     */     }
/*     */     
/* 215 */     return 0;
/*     */   }
/*     */   
/*     */   public boolean h(IBlockAccess paramIBlockAccess, BlockPosition paramBlockPosition) {
/* 219 */     IBlockData localIBlockData1 = paramIBlockAccess.getType(paramBlockPosition);
/* 220 */     EnumDirection localEnumDirection1 = (EnumDirection)localIBlockData1.get(FACING);
/* 221 */     EnumHalf localEnumHalf = (EnumHalf)localIBlockData1.get(HALF);
/* 222 */     int i = localEnumHalf == EnumHalf.TOP ? 1 : 0;
/*     */     
/* 224 */     float f1 = 0.5F;
/* 225 */     float f2 = 1.0F;
/*     */     
/* 227 */     if (i != 0) {
/* 228 */       f1 = 0.0F;
/* 229 */       f2 = 0.5F;
/*     */     }
/*     */     
/* 232 */     float f3 = 0.0F;
/* 233 */     float f4 = 1.0F;
/* 234 */     float f5 = 0.0F;
/* 235 */     float f6 = 0.5F;
/*     */     
/* 237 */     boolean bool = true;
/*     */     IBlockData localIBlockData2;
/* 239 */     Block localBlock; EnumDirection localEnumDirection2; if (localEnumDirection1 == EnumDirection.EAST) {
/* 240 */       f3 = 0.5F;
/* 241 */       f6 = 1.0F;
/*     */       
/* 243 */       localIBlockData2 = paramIBlockAccess.getType(paramBlockPosition.east());
/* 244 */       localBlock = localIBlockData2.getBlock();
/* 245 */       if ((c(localBlock)) && (localEnumHalf == localIBlockData2.get(HALF))) {
/* 246 */         localEnumDirection2 = (EnumDirection)localIBlockData2.get(FACING);
/* 247 */         if ((localEnumDirection2 == EnumDirection.NORTH) && (!a(paramIBlockAccess, paramBlockPosition.south(), localIBlockData1))) {
/* 248 */           f6 = 0.5F;
/* 249 */           bool = false;
/* 250 */         } else if ((localEnumDirection2 == EnumDirection.SOUTH) && (!a(paramIBlockAccess, paramBlockPosition.north(), localIBlockData1))) {
/* 251 */           f5 = 0.5F;
/* 252 */           bool = false;
/*     */         }
/*     */       }
/* 255 */     } else if (localEnumDirection1 == EnumDirection.WEST) {
/* 256 */       f4 = 0.5F;
/* 257 */       f6 = 1.0F;
/*     */       
/* 259 */       localIBlockData2 = paramIBlockAccess.getType(paramBlockPosition.west());
/* 260 */       localBlock = localIBlockData2.getBlock();
/* 261 */       if ((c(localBlock)) && (localEnumHalf == localIBlockData2.get(HALF))) {
/* 262 */         localEnumDirection2 = (EnumDirection)localIBlockData2.get(FACING);
/* 263 */         if ((localEnumDirection2 == EnumDirection.NORTH) && (!a(paramIBlockAccess, paramBlockPosition.south(), localIBlockData1))) {
/* 264 */           f6 = 0.5F;
/* 265 */           bool = false;
/* 266 */         } else if ((localEnumDirection2 == EnumDirection.SOUTH) && (!a(paramIBlockAccess, paramBlockPosition.north(), localIBlockData1))) {
/* 267 */           f5 = 0.5F;
/* 268 */           bool = false;
/*     */         }
/*     */       }
/* 271 */     } else if (localEnumDirection1 == EnumDirection.SOUTH) {
/* 272 */       f5 = 0.5F;
/* 273 */       f6 = 1.0F;
/*     */       
/* 275 */       localIBlockData2 = paramIBlockAccess.getType(paramBlockPosition.south());
/* 276 */       localBlock = localIBlockData2.getBlock();
/* 277 */       if ((c(localBlock)) && (localEnumHalf == localIBlockData2.get(HALF))) {
/* 278 */         localEnumDirection2 = (EnumDirection)localIBlockData2.get(FACING);
/* 279 */         if ((localEnumDirection2 == EnumDirection.WEST) && (!a(paramIBlockAccess, paramBlockPosition.east(), localIBlockData1))) {
/* 280 */           f4 = 0.5F;
/* 281 */           bool = false;
/* 282 */         } else if ((localEnumDirection2 == EnumDirection.EAST) && (!a(paramIBlockAccess, paramBlockPosition.west(), localIBlockData1))) {
/* 283 */           f3 = 0.5F;
/* 284 */           bool = false;
/*     */         }
/*     */       }
/* 287 */     } else if (localEnumDirection1 == EnumDirection.NORTH) {
/* 288 */       localIBlockData2 = paramIBlockAccess.getType(paramBlockPosition.north());
/* 289 */       localBlock = localIBlockData2.getBlock();
/* 290 */       if ((c(localBlock)) && (localEnumHalf == localIBlockData2.get(HALF))) {
/* 291 */         localEnumDirection2 = (EnumDirection)localIBlockData2.get(FACING);
/* 292 */         if ((localEnumDirection2 == EnumDirection.WEST) && (!a(paramIBlockAccess, paramBlockPosition.east(), localIBlockData1))) {
/* 293 */           f4 = 0.5F;
/* 294 */           bool = false;
/* 295 */         } else if ((localEnumDirection2 == EnumDirection.EAST) && (!a(paramIBlockAccess, paramBlockPosition.west(), localIBlockData1))) {
/* 296 */           f3 = 0.5F;
/* 297 */           bool = false;
/*     */         }
/*     */       }
/*     */     }
/*     */     
/* 302 */     a(f3, f1, f5, f4, f2, f6);
/* 303 */     return bool;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public boolean i(IBlockAccess paramIBlockAccess, BlockPosition paramBlockPosition)
/*     */   {
/* 311 */     IBlockData localIBlockData1 = paramIBlockAccess.getType(paramBlockPosition);
/* 312 */     EnumDirection localEnumDirection1 = (EnumDirection)localIBlockData1.get(FACING);
/* 313 */     EnumHalf localEnumHalf = (EnumHalf)localIBlockData1.get(HALF);
/* 314 */     int i = localEnumHalf == EnumHalf.TOP ? 1 : 0;
/*     */     
/* 316 */     float f1 = 0.5F;
/* 317 */     float f2 = 1.0F;
/*     */     
/* 319 */     if (i != 0) {
/* 320 */       f1 = 0.0F;
/* 321 */       f2 = 0.5F;
/*     */     }
/*     */     
/* 324 */     float f3 = 0.0F;
/* 325 */     float f4 = 0.5F;
/* 326 */     float f5 = 0.5F;
/* 327 */     float f6 = 1.0F;
/*     */     
/* 329 */     boolean bool = false;
/*     */     IBlockData localIBlockData2;
/* 331 */     Block localBlock; EnumDirection localEnumDirection2; if (localEnumDirection1 == EnumDirection.EAST) {
/* 332 */       localIBlockData2 = paramIBlockAccess.getType(paramBlockPosition.west());
/* 333 */       localBlock = localIBlockData2.getBlock();
/* 334 */       if ((c(localBlock)) && (localEnumHalf == localIBlockData2.get(HALF))) {
/* 335 */         localEnumDirection2 = (EnumDirection)localIBlockData2.get(FACING);
/* 336 */         if ((localEnumDirection2 == EnumDirection.NORTH) && (!a(paramIBlockAccess, paramBlockPosition.north(), localIBlockData1))) {
/* 337 */           f5 = 0.0F;
/* 338 */           f6 = 0.5F;
/* 339 */           bool = true;
/* 340 */         } else if ((localEnumDirection2 == EnumDirection.SOUTH) && (!a(paramIBlockAccess, paramBlockPosition.south(), localIBlockData1))) {
/* 341 */           f5 = 0.5F;
/* 342 */           f6 = 1.0F;
/* 343 */           bool = true;
/*     */         }
/*     */       }
/* 346 */     } else if (localEnumDirection1 == EnumDirection.WEST) {
/* 347 */       localIBlockData2 = paramIBlockAccess.getType(paramBlockPosition.east());
/* 348 */       localBlock = localIBlockData2.getBlock();
/* 349 */       if ((c(localBlock)) && (localEnumHalf == localIBlockData2.get(HALF))) {
/* 350 */         f3 = 0.5F;
/* 351 */         f4 = 1.0F;
/* 352 */         localEnumDirection2 = (EnumDirection)localIBlockData2.get(FACING);
/* 353 */         if ((localEnumDirection2 == EnumDirection.NORTH) && (!a(paramIBlockAccess, paramBlockPosition.north(), localIBlockData1))) {
/* 354 */           f5 = 0.0F;
/* 355 */           f6 = 0.5F;
/* 356 */           bool = true;
/* 357 */         } else if ((localEnumDirection2 == EnumDirection.SOUTH) && (!a(paramIBlockAccess, paramBlockPosition.south(), localIBlockData1))) {
/* 358 */           f5 = 0.5F;
/* 359 */           f6 = 1.0F;
/* 360 */           bool = true;
/*     */         }
/*     */       }
/* 363 */     } else if (localEnumDirection1 == EnumDirection.SOUTH) {
/* 364 */       localIBlockData2 = paramIBlockAccess.getType(paramBlockPosition.north());
/* 365 */       localBlock = localIBlockData2.getBlock();
/* 366 */       if ((c(localBlock)) && (localEnumHalf == localIBlockData2.get(HALF))) {
/* 367 */         f5 = 0.0F;
/* 368 */         f6 = 0.5F;
/*     */         
/* 370 */         localEnumDirection2 = (EnumDirection)localIBlockData2.get(FACING);
/* 371 */         if ((localEnumDirection2 == EnumDirection.WEST) && (!a(paramIBlockAccess, paramBlockPosition.west(), localIBlockData1))) {
/* 372 */           bool = true;
/* 373 */         } else if ((localEnumDirection2 == EnumDirection.EAST) && (!a(paramIBlockAccess, paramBlockPosition.east(), localIBlockData1))) {
/* 374 */           f3 = 0.5F;
/* 375 */           f4 = 1.0F;
/* 376 */           bool = true;
/*     */         }
/*     */       }
/* 379 */     } else if (localEnumDirection1 == EnumDirection.NORTH) {
/* 380 */       localIBlockData2 = paramIBlockAccess.getType(paramBlockPosition.south());
/* 381 */       localBlock = localIBlockData2.getBlock();
/* 382 */       if ((c(localBlock)) && (localEnumHalf == localIBlockData2.get(HALF))) {
/* 383 */         localEnumDirection2 = (EnumDirection)localIBlockData2.get(FACING);
/* 384 */         if ((localEnumDirection2 == EnumDirection.WEST) && (!a(paramIBlockAccess, paramBlockPosition.west(), localIBlockData1))) {
/* 385 */           bool = true;
/* 386 */         } else if ((localEnumDirection2 == EnumDirection.EAST) && (!a(paramIBlockAccess, paramBlockPosition.east(), localIBlockData1))) {
/* 387 */           f3 = 0.5F;
/* 388 */           f4 = 1.0F;
/* 389 */           bool = true;
/*     */         }
/*     */       }
/*     */     }
/*     */     
/* 394 */     if (bool) {
/* 395 */       a(f3, f1, f5, f4, f2, f6);
/*     */     }
/* 397 */     return bool;
/*     */   }
/*     */   
/*     */   public void a(World paramWorld, BlockPosition paramBlockPosition, IBlockData paramIBlockData, AxisAlignedBB paramAxisAlignedBB, List<AxisAlignedBB> paramList, Entity paramEntity)
/*     */   {
/* 402 */     e(paramWorld, paramBlockPosition);
/* 403 */     super.a(paramWorld, paramBlockPosition, paramIBlockData, paramAxisAlignedBB, paramList, paramEntity);
/*     */     
/* 405 */     boolean bool = h(paramWorld, paramBlockPosition);
/* 406 */     super.a(paramWorld, paramBlockPosition, paramIBlockData, paramAxisAlignedBB, paramList, paramEntity);
/*     */     
/* 408 */     if ((bool) && 
/* 409 */       (i(paramWorld, paramBlockPosition))) {
/* 410 */       super.a(paramWorld, paramBlockPosition, paramIBlockData, paramAxisAlignedBB, paramList, paramEntity);
/*     */     }
/*     */     
/*     */ 
/* 414 */     a(0.0F, 0.0F, 0.0F, 1.0F, 1.0F, 1.0F);
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
/*     */   public void attack(World paramWorld, BlockPosition paramBlockPosition, EntityHuman paramEntityHuman)
/*     */   {
/* 432 */     this.P.attack(paramWorld, paramBlockPosition, paramEntityHuman);
/*     */   }
/*     */   
/*     */   public void postBreak(World paramWorld, BlockPosition paramBlockPosition, IBlockData paramIBlockData)
/*     */   {
/* 437 */     this.P.postBreak(paramWorld, paramBlockPosition, paramIBlockData);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public float a(Entity paramEntity)
/*     */   {
/* 447 */     return this.P.a(paramEntity);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public int a(World paramWorld)
/*     */   {
/* 457 */     return this.P.a(paramWorld);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public Vec3D a(World paramWorld, BlockPosition paramBlockPosition, Entity paramEntity, Vec3D paramVec3D)
/*     */   {
/* 467 */     return this.P.a(paramWorld, paramBlockPosition, paramEntity, paramVec3D);
/*     */   }
/*     */   
/*     */   public boolean A()
/*     */   {
/* 472 */     return this.P.A();
/*     */   }
/*     */   
/*     */   public boolean a(IBlockData paramIBlockData, boolean paramBoolean)
/*     */   {
/* 477 */     return this.P.a(paramIBlockData, paramBoolean);
/*     */   }
/*     */   
/*     */   public boolean canPlace(World paramWorld, BlockPosition paramBlockPosition)
/*     */   {
/* 482 */     return this.P.canPlace(paramWorld, paramBlockPosition);
/*     */   }
/*     */   
/*     */   public void onPlace(World paramWorld, BlockPosition paramBlockPosition, IBlockData paramIBlockData)
/*     */   {
/* 487 */     doPhysics(paramWorld, paramBlockPosition, this.Q, Blocks.AIR);
/* 488 */     this.P.onPlace(paramWorld, paramBlockPosition, this.Q);
/*     */   }
/*     */   
/*     */   public void remove(World paramWorld, BlockPosition paramBlockPosition, IBlockData paramIBlockData)
/*     */   {
/* 493 */     this.P.remove(paramWorld, paramBlockPosition, this.Q);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public void a(World paramWorld, BlockPosition paramBlockPosition, Entity paramEntity)
/*     */   {
/* 503 */     this.P.a(paramWorld, paramBlockPosition, paramEntity);
/*     */   }
/*     */   
/*     */   public void b(World paramWorld, BlockPosition paramBlockPosition, IBlockData paramIBlockData, Random paramRandom)
/*     */   {
/* 508 */     this.P.b(paramWorld, paramBlockPosition, paramIBlockData, paramRandom);
/*     */   }
/*     */   
/*     */   public boolean interact(World paramWorld, BlockPosition paramBlockPosition, IBlockData paramIBlockData, EntityHuman paramEntityHuman, EnumDirection paramEnumDirection, float paramFloat1, float paramFloat2, float paramFloat3)
/*     */   {
/* 513 */     return this.P.interact(paramWorld, paramBlockPosition, this.Q, paramEntityHuman, EnumDirection.DOWN, 0.0F, 0.0F, 0.0F);
/*     */   }
/*     */   
/*     */   public void wasExploded(World paramWorld, BlockPosition paramBlockPosition, Explosion paramExplosion)
/*     */   {
/* 518 */     this.P.wasExploded(paramWorld, paramBlockPosition, paramExplosion);
/*     */   }
/*     */   
/*     */   public MaterialMapColor g(IBlockData paramIBlockData)
/*     */   {
/* 523 */     return this.P.g(this.Q);
/*     */   }
/*     */   
/*     */   public IBlockData getPlacedState(World paramWorld, BlockPosition paramBlockPosition, EnumDirection paramEnumDirection, float paramFloat1, float paramFloat2, float paramFloat3, int paramInt, EntityLiving paramEntityLiving)
/*     */   {
/* 528 */     IBlockData localIBlockData = super.getPlacedState(paramWorld, paramBlockPosition, paramEnumDirection, paramFloat1, paramFloat2, paramFloat3, paramInt, paramEntityLiving);
/*     */     
/* 530 */     localIBlockData = localIBlockData.set(FACING, paramEntityLiving.getDirection()).set(SHAPE, EnumStairShape.STRAIGHT);
/*     */     
/* 532 */     if ((paramEnumDirection == EnumDirection.DOWN) || ((paramEnumDirection != EnumDirection.UP) && (paramFloat2 > 0.5D))) {
/* 533 */       return localIBlockData.set(HALF, EnumHalf.TOP);
/*     */     }
/*     */     
/* 536 */     return localIBlockData.set(HALF, EnumHalf.BOTTOM);
/*     */   }
/*     */   
/*     */ 
/*     */   public MovingObjectPosition a(World paramWorld, BlockPosition paramBlockPosition, Vec3D paramVec3D1, Vec3D paramVec3D2)
/*     */   {
/* 542 */     MovingObjectPosition[] arrayOfMovingObjectPosition1 = new MovingObjectPosition[8];
/* 543 */     IBlockData localIBlockData = paramWorld.getType(paramBlockPosition);
/* 544 */     int i = ((EnumDirection)localIBlockData.get(FACING)).b();
/* 545 */     int j = localIBlockData.get(HALF) == EnumHalf.TOP ? 1 : 0;
/* 546 */     int[] arrayOfInt = O[(i + 0)];
/*     */     
/* 548 */     this.R = true;
/* 549 */     for (int k = 0; k < 8; k++) {
/* 550 */       this.S = k;
/*     */       
/* 552 */       if (Arrays.binarySearch(arrayOfInt, k) < 0)
/*     */       {
/*     */ 
/*     */ 
/* 556 */         arrayOfMovingObjectPosition1[k] = super.a(paramWorld, paramBlockPosition, paramVec3D1, paramVec3D2);
/*     */       }
/*     */     }
/* 559 */     for (int i1 : arrayOfInt) {
/* 560 */       arrayOfMovingObjectPosition1[i1] = null;
/*     */     }
/*     */     
/*     */ 
/* 564 */     ??? = null;
/* 565 */     double d1 = 0.0D;
/*     */     
/* 567 */     for (MovingObjectPosition localMovingObjectPosition : arrayOfMovingObjectPosition1) {
/* 568 */       if (localMovingObjectPosition != null)
/*     */       {
/*     */ 
/* 571 */         double d2 = localMovingObjectPosition.pos.distanceSquared(paramVec3D2);
/*     */         
/* 573 */         if (d2 > d1) {
/* 574 */           ??? = localMovingObjectPosition;
/* 575 */           d1 = d2;
/*     */         }
/*     */       }
/*     */     }
/* 579 */     return (MovingObjectPosition)???;
/*     */   }
/*     */   
/*     */   public IBlockData fromLegacyData(int paramInt)
/*     */   {
/* 584 */     IBlockData localIBlockData = getBlockData().set(HALF, (paramInt & 0x4) > 0 ? EnumHalf.TOP : EnumHalf.BOTTOM);
/*     */     
/*     */ 
/* 587 */     localIBlockData = localIBlockData.set(FACING, EnumDirection.fromType1(5 - (paramInt & 0x3)));
/*     */     
/* 589 */     return localIBlockData;
/*     */   }
/*     */   
/*     */   public int toLegacyData(IBlockData paramIBlockData)
/*     */   {
/* 594 */     int i = 0;
/*     */     
/* 596 */     if (paramIBlockData.get(HALF) == EnumHalf.TOP) {
/* 597 */       i |= 0x4;
/*     */     }
/*     */     
/* 600 */     i |= 5 - ((EnumDirection)paramIBlockData.get(FACING)).a();
/*     */     
/* 602 */     return i;
/*     */   }
/*     */   
/*     */   public IBlockData updateState(IBlockData paramIBlockData, IBlockAccess paramIBlockAccess, BlockPosition paramBlockPosition)
/*     */   {
/* 607 */     if (h(paramIBlockAccess, paramBlockPosition)) {
/* 608 */       switch (g(paramIBlockAccess, paramBlockPosition)) {
/*     */       case 0: 
/* 610 */         paramIBlockData = paramIBlockData.set(SHAPE, EnumStairShape.STRAIGHT);
/* 611 */         break;
/*     */       case 1: 
/* 613 */         paramIBlockData = paramIBlockData.set(SHAPE, EnumStairShape.INNER_RIGHT);
/* 614 */         break;
/*     */       case 2: 
/* 616 */         paramIBlockData = paramIBlockData.set(SHAPE, EnumStairShape.INNER_LEFT);
/*     */       }
/*     */       
/*     */     } else {
/* 620 */       switch (f(paramIBlockAccess, paramBlockPosition)) {
/*     */       case 0: 
/* 622 */         paramIBlockData = paramIBlockData.set(SHAPE, EnumStairShape.STRAIGHT);
/* 623 */         break;
/*     */       case 1: 
/* 625 */         paramIBlockData = paramIBlockData.set(SHAPE, EnumStairShape.OUTER_RIGHT);
/* 626 */         break;
/*     */       case 2: 
/* 628 */         paramIBlockData = paramIBlockData.set(SHAPE, EnumStairShape.OUTER_LEFT);
/*     */       }
/*     */       
/*     */     }
/*     */     
/* 633 */     return paramIBlockData;
/*     */   }
/*     */   
/*     */   protected BlockStateList getStateList()
/*     */   {
/* 638 */     return new BlockStateList(this, new IBlockState[] { FACING, HALF, SHAPE });
/*     */   }
/*     */   
/*     */ 
/*     */   public static enum EnumHalf
/*     */     implements INamable
/*     */   {
/*     */     private final String c;
/*     */     
/*     */     private EnumHalf(String paramString)
/*     */     {
/* 649 */       this.c = paramString;
/*     */     }
/*     */     
/*     */     public String toString()
/*     */     {
/* 654 */       return this.c;
/*     */     }
/*     */     
/*     */     public String getName()
/*     */     {
/* 659 */       return this.c;
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public static enum EnumStairShape
/*     */     implements INamable
/*     */   {
/*     */     private final String f;
/*     */     
/*     */ 
/*     */ 
/*     */     private EnumStairShape(String paramString)
/*     */     {
/* 674 */       this.f = paramString;
/*     */     }
/*     */     
/*     */     public String toString()
/*     */     {
/* 679 */       return this.f;
/*     */     }
/*     */     
/*     */     public String getName()
/*     */     {
/* 684 */       return this.f;
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_8_R3\BlockStairs.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */
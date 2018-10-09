/*     */ package net.minecraft.server.v1_8_R3;
/*     */ 
/*     */ import java.util.Iterator;
/*     */ import java.util.List;
/*     */ 
/*     */ public abstract class BlockMinecartTrackAbstract extends Block
/*     */ {
/*     */   protected final boolean a;
/*     */   
/*     */   public static boolean e(World world, BlockPosition blockposition)
/*     */   {
/*  12 */     return d(world.getType(blockposition));
/*     */   }
/*     */   
/*     */   public static boolean d(IBlockData iblockdata) {
/*  16 */     Block block = iblockdata.getBlock();
/*     */     
/*  18 */     return (block == Blocks.RAIL) || (block == Blocks.GOLDEN_RAIL) || (block == Blocks.DETECTOR_RAIL) || (block == Blocks.ACTIVATOR_RAIL);
/*     */   }
/*     */   
/*     */   protected BlockMinecartTrackAbstract(boolean flag) {
/*  22 */     super(Material.ORIENTABLE);
/*  23 */     this.a = flag;
/*  24 */     a(0.0F, 0.0F, 0.0F, 1.0F, 0.125F, 1.0F);
/*  25 */     a(CreativeModeTab.e);
/*     */   }
/*     */   
/*     */   public AxisAlignedBB a(World world, BlockPosition blockposition, IBlockData iblockdata) {
/*  29 */     return null;
/*     */   }
/*     */   
/*     */   public boolean c() {
/*  33 */     return false;
/*     */   }
/*     */   
/*     */   public MovingObjectPosition a(World world, BlockPosition blockposition, Vec3D vec3d, Vec3D vec3d1) {
/*  37 */     updateShape(world, blockposition);
/*  38 */     return super.a(world, blockposition, vec3d, vec3d1);
/*     */   }
/*     */   
/*     */   public void updateShape(IBlockAccess iblockaccess, BlockPosition blockposition) {
/*  42 */     IBlockData iblockdata = iblockaccess.getType(blockposition);
/*  43 */     EnumTrackPosition blockminecarttrackabstract_enumtrackposition = iblockdata.getBlock() == this ? (EnumTrackPosition)iblockdata.get(n()) : null;
/*     */     
/*  45 */     if ((blockminecarttrackabstract_enumtrackposition != null) && (blockminecarttrackabstract_enumtrackposition.c())) {
/*  46 */       a(0.0F, 0.0F, 0.0F, 1.0F, 0.625F, 1.0F);
/*     */     } else {
/*  48 */       a(0.0F, 0.0F, 0.0F, 1.0F, 0.125F, 1.0F);
/*     */     }
/*     */   }
/*     */   
/*     */   public boolean d()
/*     */   {
/*  54 */     return false;
/*     */   }
/*     */   
/*     */   public boolean canPlace(World world, BlockPosition blockposition) {
/*  58 */     return World.a(world, blockposition.down());
/*     */   }
/*     */   
/*     */   public void onPlace(World world, BlockPosition blockposition, IBlockData iblockdata) {
/*  62 */     if (!world.isClientSide) {
/*  63 */       iblockdata = a(world, blockposition, iblockdata, true);
/*  64 */       if (this.a) {
/*  65 */         doPhysics(world, blockposition, iblockdata, this);
/*     */       }
/*     */     }
/*     */   }
/*     */   
/*     */   public void doPhysics(World world, BlockPosition blockposition, IBlockData iblockdata, Block block)
/*     */   {
/*  72 */     if (!world.isClientSide) {
/*  73 */       EnumTrackPosition blockminecarttrackabstract_enumtrackposition = (EnumTrackPosition)iblockdata.get(n());
/*  74 */       boolean flag = false;
/*     */       
/*  76 */       if (!World.a(world, blockposition.down())) {
/*  77 */         flag = true;
/*     */       }
/*     */       
/*  80 */       if ((blockminecarttrackabstract_enumtrackposition == EnumTrackPosition.ASCENDING_EAST) && (!World.a(world, blockposition.east()))) {
/*  81 */         flag = true;
/*  82 */       } else if ((blockminecarttrackabstract_enumtrackposition == EnumTrackPosition.ASCENDING_WEST) && (!World.a(world, blockposition.west()))) {
/*  83 */         flag = true;
/*  84 */       } else if ((blockminecarttrackabstract_enumtrackposition == EnumTrackPosition.ASCENDING_NORTH) && (!World.a(world, blockposition.north()))) {
/*  85 */         flag = true;
/*  86 */       } else if ((blockminecarttrackabstract_enumtrackposition == EnumTrackPosition.ASCENDING_SOUTH) && (!World.a(world, blockposition.south()))) {
/*  87 */         flag = true;
/*     */       }
/*     */       
/*  90 */       if ((flag) && (!world.isEmpty(blockposition))) {
/*  91 */         b(world, blockposition, iblockdata, 0);
/*  92 */         world.setAir(blockposition);
/*     */       } else {
/*  94 */         b(world, blockposition, iblockdata, block);
/*     */       }
/*     */     }
/*     */   }
/*     */   
/*     */   protected void b(World world, BlockPosition blockposition, IBlockData iblockdata, Block block) {}
/*     */   
/*     */   protected IBlockData a(World world, BlockPosition blockposition, IBlockData iblockdata, boolean flag)
/*     */   {
/* 103 */     return world.isClientSide ? iblockdata : new MinecartTrackLogic(world, blockposition, iblockdata).a(world.isBlockIndirectlyPowered(blockposition), flag).b();
/*     */   }
/*     */   
/*     */   public int k() {
/* 107 */     return 0;
/*     */   }
/*     */   
/*     */   public void remove(World world, BlockPosition blockposition, IBlockData iblockdata) {
/* 111 */     super.remove(world, blockposition, iblockdata);
/* 112 */     if (((EnumTrackPosition)iblockdata.get(n())).c()) {
/* 113 */       world.applyPhysics(blockposition.up(), this);
/*     */     }
/*     */     
/* 116 */     if (this.a) {
/* 117 */       world.applyPhysics(blockposition, this);
/* 118 */       world.applyPhysics(blockposition.down(), this);
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */   public abstract IBlockState<EnumTrackPosition> n();
/*     */   
/*     */   static class SyntheticClass_1
/*     */   {
/* 127 */     static final int[] a = new int[BlockMinecartTrackAbstract.EnumTrackPosition.values().length];
/*     */     
/*     */     static {
/*     */       try {
/* 131 */         a[BlockMinecartTrackAbstract.EnumTrackPosition.NORTH_SOUTH.ordinal()] = 1;
/*     */       }
/*     */       catch (NoSuchFieldError localNoSuchFieldError1) {}
/*     */       
/*     */       try
/*     */       {
/* 137 */         a[BlockMinecartTrackAbstract.EnumTrackPosition.EAST_WEST.ordinal()] = 2;
/*     */       }
/*     */       catch (NoSuchFieldError localNoSuchFieldError2) {}
/*     */       
/*     */       try
/*     */       {
/* 143 */         a[BlockMinecartTrackAbstract.EnumTrackPosition.ASCENDING_EAST.ordinal()] = 3;
/*     */       }
/*     */       catch (NoSuchFieldError localNoSuchFieldError3) {}
/*     */       
/*     */       try
/*     */       {
/* 149 */         a[BlockMinecartTrackAbstract.EnumTrackPosition.ASCENDING_WEST.ordinal()] = 4;
/*     */       }
/*     */       catch (NoSuchFieldError localNoSuchFieldError4) {}
/*     */       
/*     */       try
/*     */       {
/* 155 */         a[BlockMinecartTrackAbstract.EnumTrackPosition.ASCENDING_NORTH.ordinal()] = 5;
/*     */       }
/*     */       catch (NoSuchFieldError localNoSuchFieldError5) {}
/*     */       
/*     */       try
/*     */       {
/* 161 */         a[BlockMinecartTrackAbstract.EnumTrackPosition.ASCENDING_SOUTH.ordinal()] = 6;
/*     */       }
/*     */       catch (NoSuchFieldError localNoSuchFieldError6) {}
/*     */       
/*     */       try
/*     */       {
/* 167 */         a[BlockMinecartTrackAbstract.EnumTrackPosition.SOUTH_EAST.ordinal()] = 7;
/*     */       }
/*     */       catch (NoSuchFieldError localNoSuchFieldError7) {}
/*     */       
/*     */       try
/*     */       {
/* 173 */         a[BlockMinecartTrackAbstract.EnumTrackPosition.SOUTH_WEST.ordinal()] = 8;
/*     */       }
/*     */       catch (NoSuchFieldError localNoSuchFieldError8) {}
/*     */       
/*     */       try
/*     */       {
/* 179 */         a[BlockMinecartTrackAbstract.EnumTrackPosition.NORTH_WEST.ordinal()] = 9;
/*     */       }
/*     */       catch (NoSuchFieldError localNoSuchFieldError9) {}
/*     */       
/*     */       try
/*     */       {
/* 185 */         a[BlockMinecartTrackAbstract.EnumTrackPosition.NORTH_EAST.ordinal()] = 10;
/*     */       }
/*     */       catch (NoSuchFieldError localNoSuchFieldError10) {}
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */   public static enum EnumTrackPosition
/*     */     implements INamable
/*     */   {
/* 195 */     NORTH_SOUTH(0, "north_south"),  EAST_WEST(1, "east_west"),  ASCENDING_EAST(2, "ascending_east"),  ASCENDING_WEST(3, "ascending_west"),  ASCENDING_NORTH(4, "ascending_north"),  ASCENDING_SOUTH(5, "ascending_south"),  SOUTH_EAST(6, "south_east"),  SOUTH_WEST(7, "south_west"),  NORTH_WEST(8, "north_west"),  NORTH_EAST(9, "north_east");
/*     */     
/*     */     private static final EnumTrackPosition[] k;
/*     */     private final int l;
/*     */     private final String m;
/*     */     
/*     */     private EnumTrackPosition(int i, String s) {
/* 202 */       this.l = i;
/* 203 */       this.m = s;
/*     */     }
/*     */     
/*     */     public int a() {
/* 207 */       return this.l;
/*     */     }
/*     */     
/*     */     public String toString() {
/* 211 */       return this.m;
/*     */     }
/*     */     
/*     */     public boolean c() {
/* 215 */       return (this == ASCENDING_NORTH) || (this == ASCENDING_EAST) || (this == ASCENDING_SOUTH) || (this == ASCENDING_WEST);
/*     */     }
/*     */     
/*     */     public static EnumTrackPosition a(int i) {
/* 219 */       if ((i < 0) || (i >= k.length)) {
/* 220 */         i = 0;
/*     */       }
/*     */       
/* 223 */       return k[i];
/*     */     }
/*     */     
/*     */     public String getName() {
/* 227 */       return this.m;
/*     */     }
/*     */     
/*     */     static
/*     */     {
/* 197 */       k = new EnumTrackPosition[values().length];
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
/* 231 */       EnumTrackPosition[] ablockminecarttrackabstract_enumtrackposition = values();
/* 232 */       int i = ablockminecarttrackabstract_enumtrackposition.length;
/*     */       
/* 234 */       for (int j = 0; j < i; j++) {
/* 235 */         EnumTrackPosition blockminecarttrackabstract_enumtrackposition = ablockminecarttrackabstract_enumtrackposition[j];
/*     */         
/* 237 */         k[blockminecarttrackabstract_enumtrackposition.a()] = blockminecarttrackabstract_enumtrackposition;
/*     */       }
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */   public class MinecartTrackLogic
/*     */   {
/*     */     private final World b;
/*     */     private final BlockPosition c;
/*     */     private final BlockMinecartTrackAbstract d;
/*     */     private IBlockData e;
/*     */     private final boolean f;
/* 250 */     private final List<BlockPosition> g = com.google.common.collect.Lists.newArrayList();
/*     */     
/*     */     public MinecartTrackLogic(World world, BlockPosition blockposition, IBlockData iblockdata) {
/* 253 */       this.b = world;
/* 254 */       this.c = blockposition;
/* 255 */       this.e = iblockdata;
/* 256 */       this.d = ((BlockMinecartTrackAbstract)iblockdata.getBlock());
/* 257 */       BlockMinecartTrackAbstract.EnumTrackPosition blockminecarttrackabstract_enumtrackposition = (BlockMinecartTrackAbstract.EnumTrackPosition)iblockdata.get(BlockMinecartTrackAbstract.this.n());
/*     */       
/* 259 */       this.f = this.d.a;
/* 260 */       a(blockminecarttrackabstract_enumtrackposition);
/*     */     }
/*     */     
/*     */     private void a(BlockMinecartTrackAbstract.EnumTrackPosition blockminecarttrackabstract_enumtrackposition) {
/* 264 */       this.g.clear();
/* 265 */       switch (BlockMinecartTrackAbstract.SyntheticClass_1.a[blockminecarttrackabstract_enumtrackposition.ordinal()]) {
/*     */       case 1: 
/* 267 */         this.g.add(this.c.north());
/* 268 */         this.g.add(this.c.south());
/* 269 */         break;
/*     */       
/*     */       case 2: 
/* 272 */         this.g.add(this.c.west());
/* 273 */         this.g.add(this.c.east());
/* 274 */         break;
/*     */       
/*     */       case 3: 
/* 277 */         this.g.add(this.c.west());
/* 278 */         this.g.add(this.c.east().up());
/* 279 */         break;
/*     */       
/*     */       case 4: 
/* 282 */         this.g.add(this.c.west().up());
/* 283 */         this.g.add(this.c.east());
/* 284 */         break;
/*     */       
/*     */       case 5: 
/* 287 */         this.g.add(this.c.north().up());
/* 288 */         this.g.add(this.c.south());
/* 289 */         break;
/*     */       
/*     */       case 6: 
/* 292 */         this.g.add(this.c.north());
/* 293 */         this.g.add(this.c.south().up());
/* 294 */         break;
/*     */       
/*     */       case 7: 
/* 297 */         this.g.add(this.c.east());
/* 298 */         this.g.add(this.c.south());
/* 299 */         break;
/*     */       
/*     */       case 8: 
/* 302 */         this.g.add(this.c.west());
/* 303 */         this.g.add(this.c.south());
/* 304 */         break;
/*     */       
/*     */       case 9: 
/* 307 */         this.g.add(this.c.west());
/* 308 */         this.g.add(this.c.north());
/* 309 */         break;
/*     */       
/*     */       case 10: 
/* 312 */         this.g.add(this.c.east());
/* 313 */         this.g.add(this.c.north());
/*     */       }
/*     */     }
/*     */     
/*     */     private void c()
/*     */     {
/* 319 */       for (int i = 0; i < this.g.size(); i++) {
/* 320 */         MinecartTrackLogic blockminecarttrackabstract_minecarttracklogic = b((BlockPosition)this.g.get(i));
/*     */         
/* 322 */         if ((blockminecarttrackabstract_minecarttracklogic != null) && (blockminecarttrackabstract_minecarttracklogic.a(this))) {
/* 323 */           this.g.set(i, blockminecarttrackabstract_minecarttracklogic.c);
/*     */         } else {
/* 325 */           this.g.remove(i--);
/*     */         }
/*     */       }
/*     */     }
/*     */     
/*     */     private boolean a(BlockPosition blockposition)
/*     */     {
/* 332 */       return (BlockMinecartTrackAbstract.e(this.b, blockposition)) || (BlockMinecartTrackAbstract.e(this.b, blockposition.up())) || (BlockMinecartTrackAbstract.e(this.b, blockposition.down()));
/*     */     }
/*     */     
/*     */     private MinecartTrackLogic b(BlockPosition blockposition) {
/* 336 */       IBlockData iblockdata = this.b.getType(blockposition);
/*     */       
/* 338 */       if (BlockMinecartTrackAbstract.d(iblockdata)) {
/* 339 */         BlockMinecartTrackAbstract tmp24_21 = BlockMinecartTrackAbstract.this;tmp24_21.getClass();return new MinecartTrackLogic(tmp24_21, this.b, blockposition, iblockdata);
/*     */       }
/* 341 */       BlockPosition blockposition1 = blockposition.up();
/*     */       
/* 343 */       iblockdata = this.b.getType(blockposition1);
/* 344 */       if (BlockMinecartTrackAbstract.d(iblockdata)) {
/* 345 */         BlockMinecartTrackAbstract tmp68_65 = BlockMinecartTrackAbstract.this;tmp68_65.getClass();return new MinecartTrackLogic(tmp68_65, this.b, blockposition1, iblockdata);
/*     */       }
/* 347 */       blockposition1 = blockposition.down();
/* 348 */       iblockdata = this.b.getType(blockposition1); BlockMinecartTrackAbstract 
/* 349 */         tmp112_109 = BlockMinecartTrackAbstract.this;tmp112_109.getClass();return BlockMinecartTrackAbstract.d(iblockdata) ? new MinecartTrackLogic(tmp112_109, this.b, blockposition1, iblockdata) : null;
/*     */     }
/*     */     
/*     */ 
/*     */     private boolean a(MinecartTrackLogic blockminecarttrackabstract_minecarttracklogic)
/*     */     {
/* 355 */       return c(blockminecarttrackabstract_minecarttracklogic.c);
/*     */     }
/*     */     
/*     */     private boolean c(BlockPosition blockposition) {
/* 359 */       for (int i = 0; i < this.g.size(); i++) {
/* 360 */         BlockPosition blockposition1 = (BlockPosition)this.g.get(i);
/*     */         
/* 362 */         if ((blockposition1.getX() == blockposition.getX()) && (blockposition1.getZ() == blockposition.getZ())) {
/* 363 */           return true;
/*     */         }
/*     */       }
/*     */       
/* 367 */       return false;
/*     */     }
/*     */     
/*     */     protected int a() {
/* 371 */       int i = 0;
/* 372 */       Iterator iterator = EnumDirection.EnumDirectionLimit.HORIZONTAL.iterator();
/*     */       
/* 374 */       while (iterator.hasNext()) {
/* 375 */         EnumDirection enumdirection = (EnumDirection)iterator.next();
/*     */         
/* 377 */         if (a(this.c.shift(enumdirection))) {
/* 378 */           i++;
/*     */         }
/*     */       }
/*     */       
/* 382 */       return i;
/*     */     }
/*     */     
/*     */     private boolean b(MinecartTrackLogic blockminecarttrackabstract_minecarttracklogic) {
/* 386 */       return (a(blockminecarttrackabstract_minecarttracklogic)) || (this.g.size() != 2);
/*     */     }
/*     */     
/*     */     private void c(MinecartTrackLogic blockminecarttrackabstract_minecarttracklogic) {
/* 390 */       this.g.add(blockminecarttrackabstract_minecarttracklogic.c);
/* 391 */       BlockPosition blockposition = this.c.north();
/* 392 */       BlockPosition blockposition1 = this.c.south();
/* 393 */       BlockPosition blockposition2 = this.c.west();
/* 394 */       BlockPosition blockposition3 = this.c.east();
/* 395 */       boolean flag = c(blockposition);
/* 396 */       boolean flag1 = c(blockposition1);
/* 397 */       boolean flag2 = c(blockposition2);
/* 398 */       boolean flag3 = c(blockposition3);
/* 399 */       BlockMinecartTrackAbstract.EnumTrackPosition blockminecarttrackabstract_enumtrackposition = null;
/*     */       
/* 401 */       if ((flag) || (flag1)) {
/* 402 */         blockminecarttrackabstract_enumtrackposition = BlockMinecartTrackAbstract.EnumTrackPosition.NORTH_SOUTH;
/*     */       }
/*     */       
/* 405 */       if ((flag2) || (flag3)) {
/* 406 */         blockminecarttrackabstract_enumtrackposition = BlockMinecartTrackAbstract.EnumTrackPosition.EAST_WEST;
/*     */       }
/*     */       
/* 409 */       if (!this.f) {
/* 410 */         if ((flag1) && (flag3) && (!flag) && (!flag2)) {
/* 411 */           blockminecarttrackabstract_enumtrackposition = BlockMinecartTrackAbstract.EnumTrackPosition.SOUTH_EAST;
/*     */         }
/*     */         
/* 414 */         if ((flag1) && (flag2) && (!flag) && (!flag3)) {
/* 415 */           blockminecarttrackabstract_enumtrackposition = BlockMinecartTrackAbstract.EnumTrackPosition.SOUTH_WEST;
/*     */         }
/*     */         
/* 418 */         if ((flag) && (flag2) && (!flag1) && (!flag3)) {
/* 419 */           blockminecarttrackabstract_enumtrackposition = BlockMinecartTrackAbstract.EnumTrackPosition.NORTH_WEST;
/*     */         }
/*     */         
/* 422 */         if ((flag) && (flag3) && (!flag1) && (!flag2)) {
/* 423 */           blockminecarttrackabstract_enumtrackposition = BlockMinecartTrackAbstract.EnumTrackPosition.NORTH_EAST;
/*     */         }
/*     */       }
/*     */       
/* 427 */       if (blockminecarttrackabstract_enumtrackposition == BlockMinecartTrackAbstract.EnumTrackPosition.NORTH_SOUTH) {
/* 428 */         if (BlockMinecartTrackAbstract.e(this.b, blockposition.up())) {
/* 429 */           blockminecarttrackabstract_enumtrackposition = BlockMinecartTrackAbstract.EnumTrackPosition.ASCENDING_NORTH;
/*     */         }
/*     */         
/* 432 */         if (BlockMinecartTrackAbstract.e(this.b, blockposition1.up())) {
/* 433 */           blockminecarttrackabstract_enumtrackposition = BlockMinecartTrackAbstract.EnumTrackPosition.ASCENDING_SOUTH;
/*     */         }
/*     */       }
/*     */       
/* 437 */       if (blockminecarttrackabstract_enumtrackposition == BlockMinecartTrackAbstract.EnumTrackPosition.EAST_WEST) {
/* 438 */         if (BlockMinecartTrackAbstract.e(this.b, blockposition3.up())) {
/* 439 */           blockminecarttrackabstract_enumtrackposition = BlockMinecartTrackAbstract.EnumTrackPosition.ASCENDING_EAST;
/*     */         }
/*     */         
/* 442 */         if (BlockMinecartTrackAbstract.e(this.b, blockposition2.up())) {
/* 443 */           blockminecarttrackabstract_enumtrackposition = BlockMinecartTrackAbstract.EnumTrackPosition.ASCENDING_WEST;
/*     */         }
/*     */       }
/*     */       
/* 447 */       if (blockminecarttrackabstract_enumtrackposition == null) {
/* 448 */         blockminecarttrackabstract_enumtrackposition = BlockMinecartTrackAbstract.EnumTrackPosition.NORTH_SOUTH;
/*     */       }
/*     */       
/* 451 */       this.e = this.e.set(this.d.n(), blockminecarttrackabstract_enumtrackposition);
/* 452 */       this.b.setTypeAndData(this.c, this.e, 3);
/*     */     }
/*     */     
/*     */     private boolean d(BlockPosition blockposition) {
/* 456 */       MinecartTrackLogic blockminecarttrackabstract_minecarttracklogic = b(blockposition);
/*     */       
/* 458 */       if (blockminecarttrackabstract_minecarttracklogic == null) {
/* 459 */         return false;
/*     */       }
/* 461 */       blockminecarttrackabstract_minecarttracklogic.c();
/* 462 */       return blockminecarttrackabstract_minecarttracklogic.b(this);
/*     */     }
/*     */     
/*     */     public MinecartTrackLogic a(boolean flag, boolean flag1)
/*     */     {
/* 467 */       BlockPosition blockposition = this.c.north();
/* 468 */       BlockPosition blockposition1 = this.c.south();
/* 469 */       BlockPosition blockposition2 = this.c.west();
/* 470 */       BlockPosition blockposition3 = this.c.east();
/* 471 */       boolean flag2 = d(blockposition);
/* 472 */       boolean flag3 = d(blockposition1);
/* 473 */       boolean flag4 = d(blockposition2);
/* 474 */       boolean flag5 = d(blockposition3);
/* 475 */       BlockMinecartTrackAbstract.EnumTrackPosition blockminecarttrackabstract_enumtrackposition = null;
/*     */       
/* 477 */       if (((flag2) || (flag3)) && (!flag4) && (!flag5)) {
/* 478 */         blockminecarttrackabstract_enumtrackposition = BlockMinecartTrackAbstract.EnumTrackPosition.NORTH_SOUTH;
/*     */       }
/*     */       
/* 481 */       if (((flag4) || (flag5)) && (!flag2) && (!flag3)) {
/* 482 */         blockminecarttrackabstract_enumtrackposition = BlockMinecartTrackAbstract.EnumTrackPosition.EAST_WEST;
/*     */       }
/*     */       
/* 485 */       if (!this.f) {
/* 486 */         if ((flag3) && (flag5) && (!flag2) && (!flag4)) {
/* 487 */           blockminecarttrackabstract_enumtrackposition = BlockMinecartTrackAbstract.EnumTrackPosition.SOUTH_EAST;
/*     */         }
/*     */         
/* 490 */         if ((flag3) && (flag4) && (!flag2) && (!flag5)) {
/* 491 */           blockminecarttrackabstract_enumtrackposition = BlockMinecartTrackAbstract.EnumTrackPosition.SOUTH_WEST;
/*     */         }
/*     */         
/* 494 */         if ((flag2) && (flag4) && (!flag3) && (!flag5)) {
/* 495 */           blockminecarttrackabstract_enumtrackposition = BlockMinecartTrackAbstract.EnumTrackPosition.NORTH_WEST;
/*     */         }
/*     */         
/* 498 */         if ((flag2) && (flag5) && (!flag3) && (!flag4)) {
/* 499 */           blockminecarttrackabstract_enumtrackposition = BlockMinecartTrackAbstract.EnumTrackPosition.NORTH_EAST;
/*     */         }
/*     */       }
/*     */       
/* 503 */       if (blockminecarttrackabstract_enumtrackposition == null) {
/* 504 */         if ((flag2) || (flag3)) {
/* 505 */           blockminecarttrackabstract_enumtrackposition = BlockMinecartTrackAbstract.EnumTrackPosition.NORTH_SOUTH;
/*     */         }
/*     */         
/* 508 */         if ((flag4) || (flag5)) {
/* 509 */           blockminecarttrackabstract_enumtrackposition = BlockMinecartTrackAbstract.EnumTrackPosition.EAST_WEST;
/*     */         }
/*     */         
/* 512 */         if (!this.f) {
/* 513 */           if (flag) {
/* 514 */             if ((flag3) && (flag5)) {
/* 515 */               blockminecarttrackabstract_enumtrackposition = BlockMinecartTrackAbstract.EnumTrackPosition.SOUTH_EAST;
/*     */             }
/*     */             
/* 518 */             if ((flag4) && (flag3)) {
/* 519 */               blockminecarttrackabstract_enumtrackposition = BlockMinecartTrackAbstract.EnumTrackPosition.SOUTH_WEST;
/*     */             }
/*     */             
/* 522 */             if ((flag5) && (flag2)) {
/* 523 */               blockminecarttrackabstract_enumtrackposition = BlockMinecartTrackAbstract.EnumTrackPosition.NORTH_EAST;
/*     */             }
/*     */             
/* 526 */             if ((flag2) && (flag4)) {
/* 527 */               blockminecarttrackabstract_enumtrackposition = BlockMinecartTrackAbstract.EnumTrackPosition.NORTH_WEST;
/*     */             }
/*     */           } else {
/* 530 */             if ((flag2) && (flag4)) {
/* 531 */               blockminecarttrackabstract_enumtrackposition = BlockMinecartTrackAbstract.EnumTrackPosition.NORTH_WEST;
/*     */             }
/*     */             
/* 534 */             if ((flag5) && (flag2)) {
/* 535 */               blockminecarttrackabstract_enumtrackposition = BlockMinecartTrackAbstract.EnumTrackPosition.NORTH_EAST;
/*     */             }
/*     */             
/* 538 */             if ((flag4) && (flag3)) {
/* 539 */               blockminecarttrackabstract_enumtrackposition = BlockMinecartTrackAbstract.EnumTrackPosition.SOUTH_WEST;
/*     */             }
/*     */             
/* 542 */             if ((flag3) && (flag5)) {
/* 543 */               blockminecarttrackabstract_enumtrackposition = BlockMinecartTrackAbstract.EnumTrackPosition.SOUTH_EAST;
/*     */             }
/*     */           }
/*     */         }
/*     */       }
/*     */       
/* 549 */       if (blockminecarttrackabstract_enumtrackposition == BlockMinecartTrackAbstract.EnumTrackPosition.NORTH_SOUTH) {
/* 550 */         if (BlockMinecartTrackAbstract.e(this.b, blockposition.up())) {
/* 551 */           blockminecarttrackabstract_enumtrackposition = BlockMinecartTrackAbstract.EnumTrackPosition.ASCENDING_NORTH;
/*     */         }
/*     */         
/* 554 */         if (BlockMinecartTrackAbstract.e(this.b, blockposition1.up())) {
/* 555 */           blockminecarttrackabstract_enumtrackposition = BlockMinecartTrackAbstract.EnumTrackPosition.ASCENDING_SOUTH;
/*     */         }
/*     */       }
/*     */       
/* 559 */       if (blockminecarttrackabstract_enumtrackposition == BlockMinecartTrackAbstract.EnumTrackPosition.EAST_WEST) {
/* 560 */         if (BlockMinecartTrackAbstract.e(this.b, blockposition3.up())) {
/* 561 */           blockminecarttrackabstract_enumtrackposition = BlockMinecartTrackAbstract.EnumTrackPosition.ASCENDING_EAST;
/*     */         }
/*     */         
/* 564 */         if (BlockMinecartTrackAbstract.e(this.b, blockposition2.up())) {
/* 565 */           blockminecarttrackabstract_enumtrackposition = BlockMinecartTrackAbstract.EnumTrackPosition.ASCENDING_WEST;
/*     */         }
/*     */       }
/*     */       
/* 569 */       if (blockminecarttrackabstract_enumtrackposition == null) {
/* 570 */         blockminecarttrackabstract_enumtrackposition = BlockMinecartTrackAbstract.EnumTrackPosition.NORTH_SOUTH;
/*     */       }
/*     */       
/* 573 */       a(blockminecarttrackabstract_enumtrackposition);
/* 574 */       this.e = this.e.set(this.d.n(), blockminecarttrackabstract_enumtrackposition);
/* 575 */       if ((flag1) || (this.b.getType(this.c) != this.e)) {
/* 576 */         this.b.setTypeAndData(this.c, this.e, 3);
/*     */         
/* 578 */         for (int i = 0; i < this.g.size(); i++) {
/* 579 */           MinecartTrackLogic blockminecarttrackabstract_minecarttracklogic = b((BlockPosition)this.g.get(i));
/*     */           
/* 581 */           if (blockminecarttrackabstract_minecarttracklogic != null) {
/* 582 */             blockminecarttrackabstract_minecarttracklogic.c();
/* 583 */             if (blockminecarttrackabstract_minecarttracklogic.b(this)) {
/* 584 */               blockminecarttrackabstract_minecarttracklogic.c(this);
/*     */             }
/*     */           }
/*     */         }
/*     */       }
/*     */       
/* 590 */       return this;
/*     */     }
/*     */     
/*     */     public IBlockData b() {
/* 594 */       return this.e;
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_8_R3\BlockMinecartTrackAbstract.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */
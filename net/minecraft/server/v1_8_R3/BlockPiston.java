/*     */ package net.minecraft.server.v1_8_R3;
/*     */ 
/*     */ import com.google.common.collect.ImmutableList;
/*     */ import java.util.AbstractList;
/*     */ import java.util.List;
/*     */ import java.util.Random;
/*     */ import org.bukkit.craftbukkit.v1_8_R3.CraftServer;
/*     */ import org.bukkit.craftbukkit.v1_8_R3.CraftWorld;
/*     */ import org.bukkit.craftbukkit.v1_8_R3.block.CraftBlock;
/*     */ import org.bukkit.event.block.BlockPistonEvent;
/*     */ import org.bukkit.event.block.BlockPistonExtendEvent;
/*     */ import org.bukkit.event.block.BlockPistonRetractEvent;
/*     */ import org.bukkit.plugin.PluginManager;
/*     */ 
/*     */ 
/*     */ public class BlockPiston
/*     */   extends Block
/*     */ {
/*  19 */   public static final BlockStateDirection FACING = BlockStateDirection.of("facing");
/*  20 */   public static final BlockStateBoolean EXTENDED = BlockStateBoolean.of("extended");
/*     */   private final boolean sticky;
/*     */   
/*     */   public BlockPiston(boolean flag) {
/*  24 */     super(Material.PISTON);
/*  25 */     j(this.blockStateList.getBlockData().set(FACING, EnumDirection.NORTH).set(EXTENDED, Boolean.valueOf(false)));
/*  26 */     this.sticky = flag;
/*  27 */     a(i);
/*  28 */     c(0.5F);
/*  29 */     a(CreativeModeTab.d);
/*     */   }
/*     */   
/*     */   public boolean c() {
/*  33 */     return false;
/*     */   }
/*     */   
/*     */   public void postPlace(World world, BlockPosition blockposition, IBlockData iblockdata, EntityLiving entityliving, ItemStack itemstack) {
/*  37 */     world.setTypeAndData(blockposition, iblockdata.set(FACING, a(world, blockposition, entityliving)), 2);
/*  38 */     if (!world.isClientSide) {
/*  39 */       e(world, blockposition, iblockdata);
/*     */     }
/*     */   }
/*     */   
/*     */   public void doPhysics(World world, BlockPosition blockposition, IBlockData iblockdata, Block block)
/*     */   {
/*  45 */     if (!world.isClientSide) {
/*  46 */       e(world, blockposition, iblockdata);
/*     */     }
/*     */   }
/*     */   
/*     */   public void onPlace(World world, BlockPosition blockposition, IBlockData iblockdata)
/*     */   {
/*  52 */     if ((!world.isClientSide) && (world.getTileEntity(blockposition) == null)) {
/*  53 */       e(world, blockposition, iblockdata);
/*     */     }
/*     */   }
/*     */   
/*     */   public IBlockData getPlacedState(World world, BlockPosition blockposition, EnumDirection enumdirection, float f, float f1, float f2, int i, EntityLiving entityliving)
/*     */   {
/*  59 */     return getBlockData().set(FACING, a(world, blockposition, entityliving)).set(EXTENDED, Boolean.valueOf(false));
/*     */   }
/*     */   
/*     */   private void e(World world, BlockPosition blockposition, IBlockData iblockdata) {
/*  63 */     EnumDirection enumdirection = (EnumDirection)iblockdata.get(FACING);
/*  64 */     boolean flag = a(world, blockposition, enumdirection);
/*     */     
/*  66 */     if ((flag) && (!((Boolean)iblockdata.get(EXTENDED)).booleanValue())) {
/*  67 */       if (new PistonExtendsChecker(world, blockposition, enumdirection, true).a()) {
/*  68 */         world.playBlockAction(blockposition, this, 0, enumdirection.a());
/*     */       }
/*  70 */     } else if ((!flag) && (((Boolean)iblockdata.get(EXTENDED)).booleanValue()))
/*     */     {
/*  72 */       if (!this.sticky) {
/*  73 */         org.bukkit.block.Block block = world.getWorld().getBlockAt(blockposition.getX(), blockposition.getY(), blockposition.getZ());
/*  74 */         BlockPistonRetractEvent event = new BlockPistonRetractEvent(block, ImmutableList.of(), CraftBlock.notchToBlockFace(enumdirection));
/*  75 */         world.getServer().getPluginManager().callEvent(event);
/*     */         
/*  77 */         if (event.isCancelled()) {
/*  78 */           return;
/*     */         }
/*     */       }
/*     */       
/*  82 */       world.setTypeAndData(blockposition, iblockdata.set(EXTENDED, Boolean.valueOf(false)), 2);
/*  83 */       world.playBlockAction(blockposition, this, 1, enumdirection.a());
/*     */     }
/*     */   }
/*     */   
/*     */   private boolean a(World world, BlockPosition blockposition, EnumDirection enumdirection)
/*     */   {
/*  89 */     EnumDirection[] aenumdirection = EnumDirection.values();
/*  90 */     int i = aenumdirection.length;
/*     */     
/*     */ 
/*     */ 
/*  94 */     for (int j = 0; j < i; j++) {
/*  95 */       EnumDirection enumdirection1 = aenumdirection[j];
/*     */       
/*  97 */       if ((enumdirection1 != enumdirection) && (world.isBlockFacePowered(blockposition.shift(enumdirection1), enumdirection1))) {
/*  98 */         return true;
/*     */       }
/*     */     }
/*     */     
/* 102 */     if (world.isBlockFacePowered(blockposition, EnumDirection.DOWN)) {
/* 103 */       return true;
/*     */     }
/* 105 */     BlockPosition blockposition1 = blockposition.up();
/* 106 */     EnumDirection[] aenumdirection1 = EnumDirection.values();
/*     */     
/* 108 */     j = aenumdirection1.length;
/*     */     
/* 110 */     for (int k = 0; k < j; k++) {
/* 111 */       EnumDirection enumdirection2 = aenumdirection1[k];
/*     */       
/* 113 */       if ((enumdirection2 != EnumDirection.DOWN) && (world.isBlockFacePowered(blockposition1.shift(enumdirection2), enumdirection2))) {
/* 114 */         return true;
/*     */       }
/*     */     }
/*     */     
/* 118 */     return false;
/*     */   }
/*     */   
/*     */   public boolean a(World world, BlockPosition blockposition, IBlockData iblockdata, int i, int j)
/*     */   {
/* 123 */     EnumDirection enumdirection = (EnumDirection)iblockdata.get(FACING);
/*     */     
/* 125 */     if (!world.isClientSide) {
/* 126 */       boolean flag = a(world, blockposition, enumdirection);
/*     */       
/* 128 */       if ((flag) && (i == 1)) {
/* 129 */         world.setTypeAndData(blockposition, iblockdata.set(EXTENDED, Boolean.valueOf(true)), 2);
/* 130 */         return false;
/*     */       }
/*     */       
/* 133 */       if ((!flag) && (i == 0)) {
/* 134 */         return false;
/*     */       }
/*     */     }
/*     */     
/* 138 */     if (i == 0) {
/* 139 */       if (!a(world, blockposition, enumdirection, true)) {
/* 140 */         return false;
/*     */       }
/*     */       
/* 143 */       world.setTypeAndData(blockposition, iblockdata.set(EXTENDED, Boolean.valueOf(true)), 2);
/* 144 */       world.makeSound(blockposition.getX() + 0.5D, blockposition.getY() + 0.5D, blockposition.getZ() + 0.5D, "tile.piston.out", 0.5F, world.random.nextFloat() * 0.25F + 0.6F);
/* 145 */     } else if (i == 1) {
/* 146 */       TileEntity tileentity = world.getTileEntity(blockposition.shift(enumdirection));
/*     */       
/* 148 */       if ((tileentity instanceof TileEntityPiston)) {
/* 149 */         ((TileEntityPiston)tileentity).h();
/*     */       }
/*     */       
/* 152 */       world.setTypeAndData(blockposition, Blocks.PISTON_EXTENSION.getBlockData().set(BlockPistonMoving.FACING, enumdirection).set(BlockPistonMoving.TYPE, this.sticky ? BlockPistonExtension.EnumPistonType.STICKY : BlockPistonExtension.EnumPistonType.DEFAULT), 3);
/* 153 */       world.setTileEntity(blockposition, BlockPistonMoving.a(fromLegacyData(j), enumdirection, false, true));
/* 154 */       if (this.sticky) {
/* 155 */         BlockPosition blockposition1 = blockposition.a(enumdirection.getAdjacentX() * 2, enumdirection.getAdjacentY() * 2, enumdirection.getAdjacentZ() * 2);
/* 156 */         Block block = world.getType(blockposition1).getBlock();
/* 157 */         boolean flag1 = false;
/*     */         
/* 159 */         if (block == Blocks.PISTON_EXTENSION) {
/* 160 */           TileEntity tileentity1 = world.getTileEntity(blockposition1);
/*     */           
/* 162 */           if ((tileentity1 instanceof TileEntityPiston)) {
/* 163 */             TileEntityPiston tileentitypiston = (TileEntityPiston)tileentity1;
/*     */             
/* 165 */             if ((tileentitypiston.e() == enumdirection) && (tileentitypiston.d())) {
/* 166 */               tileentitypiston.h();
/* 167 */               flag1 = true;
/*     */             }
/*     */           }
/*     */         }
/*     */         
/* 172 */         if ((!flag1) && (a(block, world, blockposition1, enumdirection.opposite(), false)) && ((block.k() == 0) || (block == Blocks.PISTON) || (block == Blocks.STICKY_PISTON))) {
/* 173 */           a(world, blockposition, enumdirection, false);
/*     */         }
/*     */       } else {
/* 176 */         world.setAir(blockposition.shift(enumdirection));
/*     */       }
/*     */       
/* 179 */       world.makeSound(blockposition.getX() + 0.5D, blockposition.getY() + 0.5D, blockposition.getZ() + 0.5D, "tile.piston.in", 0.5F, world.random.nextFloat() * 0.15F + 0.6F);
/*     */     }
/*     */     
/* 182 */     return true;
/*     */   }
/*     */   
/*     */   public void updateShape(IBlockAccess iblockaccess, BlockPosition blockposition) {
/* 186 */     IBlockData iblockdata = iblockaccess.getType(blockposition);
/*     */     EnumDirection enumdirection;
/* 188 */     if ((iblockdata.getBlock() == this) && (((Boolean)iblockdata.get(EXTENDED)).booleanValue()))
/*     */     {
/* 190 */       enumdirection = (EnumDirection)iblockdata.get(FACING);
/*     */       
/* 192 */       if (enumdirection == null) {}
/* 193 */     } else { switch (SyntheticClass_1.a[enumdirection.ordinal()]) {
/*     */       case 1: 
/* 195 */         a(0.0F, 0.25F, 0.0F, 1.0F, 1.0F, 1.0F);
/* 196 */         break;
/*     */       
/*     */       case 2: 
/* 199 */         a(0.0F, 0.0F, 0.0F, 1.0F, 0.75F, 1.0F);
/* 200 */         break;
/*     */       
/*     */       case 3: 
/* 203 */         a(0.0F, 0.0F, 0.25F, 1.0F, 1.0F, 1.0F);
/* 204 */         break;
/*     */       
/*     */       case 4: 
/* 207 */         a(0.0F, 0.0F, 0.0F, 1.0F, 1.0F, 0.75F);
/* 208 */         break;
/*     */       
/*     */       case 5: 
/* 211 */         a(0.25F, 0.0F, 0.0F, 1.0F, 1.0F, 1.0F);
/* 212 */         break;
/*     */       
/*     */       case 6: 
/* 215 */         a(0.0F, 0.0F, 0.0F, 0.75F, 1.0F, 1.0F);
/*     */       
/*     */ 
/*     */       default: 
/* 219 */         break;a(0.0F, 0.0F, 0.0F, 1.0F, 1.0F, 1.0F);
/*     */       }
/*     */     }
/*     */   }
/*     */   
/*     */   public void j() {
/* 225 */     a(0.0F, 0.0F, 0.0F, 1.0F, 1.0F, 1.0F);
/*     */   }
/*     */   
/*     */   public void a(World world, BlockPosition blockposition, IBlockData iblockdata, AxisAlignedBB axisalignedbb, List<AxisAlignedBB> list, Entity entity) {
/* 229 */     a(0.0F, 0.0F, 0.0F, 1.0F, 1.0F, 1.0F);
/* 230 */     super.a(world, blockposition, iblockdata, axisalignedbb, list, entity);
/*     */   }
/*     */   
/*     */   public AxisAlignedBB a(World world, BlockPosition blockposition, IBlockData iblockdata) {
/* 234 */     updateShape(world, blockposition);
/* 235 */     return super.a(world, blockposition, iblockdata);
/*     */   }
/*     */   
/*     */   public boolean d() {
/* 239 */     return false;
/*     */   }
/*     */   
/*     */   public static EnumDirection b(int i) {
/* 243 */     int j = i & 0x7;
/*     */     
/* 245 */     return j > 5 ? null : EnumDirection.fromType1(j);
/*     */   }
/*     */   
/*     */   public static EnumDirection a(World world, BlockPosition blockposition, EntityLiving entityliving) {
/* 249 */     if ((MathHelper.e((float)entityliving.locX - blockposition.getX()) < 2.0F) && (MathHelper.e((float)entityliving.locZ - blockposition.getZ()) < 2.0F)) {
/* 250 */       double d0 = entityliving.locY + entityliving.getHeadHeight();
/*     */       
/* 252 */       if (d0 - blockposition.getY() > 2.0D) {
/* 253 */         return EnumDirection.UP;
/*     */       }
/*     */       
/* 256 */       if (blockposition.getY() - d0 > 0.0D) {
/* 257 */         return EnumDirection.DOWN;
/*     */       }
/*     */     }
/*     */     
/* 261 */     return entityliving.getDirection().opposite();
/*     */   }
/*     */   
/*     */   public static boolean a(Block block, World world, BlockPosition blockposition, EnumDirection enumdirection, boolean flag) {
/* 265 */     if (block == Blocks.OBSIDIAN)
/* 266 */       return false;
/* 267 */     if (!world.getWorldBorder().a(blockposition))
/* 268 */       return false;
/* 269 */     if ((blockposition.getY() >= 0) && ((enumdirection != EnumDirection.DOWN) || (blockposition.getY() != 0))) {
/* 270 */       if ((blockposition.getY() <= world.getHeight() - 1) && ((enumdirection != EnumDirection.UP) || (blockposition.getY() != world.getHeight() - 1))) {
/* 271 */         if ((block != Blocks.PISTON) && (block != Blocks.STICKY_PISTON)) {
/* 272 */           if (block.g(world, blockposition) == -1.0F) {
/* 273 */             return false;
/*     */           }
/*     */           
/* 276 */           if (block.k() == 2) {
/* 277 */             return false;
/*     */           }
/*     */           
/* 280 */           if (block.k() == 1) {
/* 281 */             if (!flag) {
/* 282 */               return false;
/*     */             }
/*     */             
/* 285 */             return true;
/*     */           }
/* 287 */         } else if (((Boolean)world.getType(blockposition).get(EXTENDED)).booleanValue()) {
/* 288 */           return false;
/*     */         }
/*     */         
/* 291 */         return !(block instanceof IContainer);
/*     */       }
/* 293 */       return false;
/*     */     }
/*     */     
/* 296 */     return false;
/*     */   }
/*     */   
/*     */   private boolean a(World world, BlockPosition blockposition, EnumDirection enumdirection, boolean flag)
/*     */   {
/* 301 */     if (!flag) {
/* 302 */       world.setAir(blockposition.shift(enumdirection));
/*     */     }
/*     */     
/* 305 */     PistonExtendsChecker pistonextendschecker = new PistonExtendsChecker(world, blockposition, enumdirection, flag);
/* 306 */     List list = pistonextendschecker.getMovedBlocks();
/* 307 */     List list1 = pistonextendschecker.getBrokenBlocks();
/*     */     
/* 309 */     if (!pistonextendschecker.a()) {
/* 310 */       return false;
/*     */     }
/*     */     
/* 313 */     final org.bukkit.block.Block bblock = world.getWorld().getBlockAt(blockposition.getX(), blockposition.getY(), blockposition.getZ());
/*     */     
/* 315 */     final List<BlockPosition> moved = pistonextendschecker.getMovedBlocks();
/* 316 */     final List<BlockPosition> broken = pistonextendschecker.getBrokenBlocks();
/*     */     
/* 318 */     List<org.bukkit.block.Block> blocks = new AbstractList()
/*     */     {
/*     */       public int size()
/*     */       {
/* 322 */         return moved.size() + broken.size();
/*     */       }
/*     */       
/*     */       public org.bukkit.block.Block get(int index)
/*     */       {
/* 327 */         if ((index >= size()) || (index < 0)) {
/* 328 */           throw new ArrayIndexOutOfBoundsException(index);
/*     */         }
/* 330 */         BlockPosition pos = index < moved.size() ? (BlockPosition)moved.get(index) : (BlockPosition)broken.get(index - moved.size());
/* 331 */         return bblock.getWorld().getBlockAt(pos.getX(), pos.getY(), pos.getZ());
/*     */       }
/*     */       
/* 334 */     };
/* 335 */     int i = list.size() + list1.size();
/* 336 */     Block[] ablock = new Block[i];
/* 337 */     EnumDirection enumdirection1 = flag ? enumdirection : enumdirection.opposite();
/*     */     BlockPistonEvent event;
/*     */     BlockPistonEvent event;
/* 340 */     if (flag) {
/* 341 */       event = new BlockPistonExtendEvent(bblock, blocks, CraftBlock.notchToBlockFace(enumdirection1));
/*     */     } else {
/* 343 */       event = new BlockPistonRetractEvent(bblock, blocks, CraftBlock.notchToBlockFace(enumdirection1));
/*     */     }
/* 345 */     world.getServer().getPluginManager().callEvent(event);
/*     */     
/* 347 */     if (event.isCancelled()) {
/* 348 */       for (BlockPosition b : broken) {
/* 349 */         world.notify(b);
/*     */       }
/* 351 */       for (BlockPosition b : moved) {
/* 352 */         world.notify(b);
/* 353 */         world.notify(b.shift(enumdirection1));
/*     */       }
/* 355 */       return false;
/*     */     }
/*     */     
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/* 362 */     for (int j = list1.size() - 1; j >= 0; j--) {
/* 363 */       BlockPosition blockposition1 = (BlockPosition)list1.get(j);
/* 364 */       Block block = world.getType(blockposition1).getBlock();
/*     */       
/* 366 */       block.b(world, blockposition1, world.getType(blockposition1), 0);
/* 367 */       world.setAir(blockposition1);
/* 368 */       i--;
/* 369 */       ablock[i] = block;
/*     */     }
/*     */     
/*     */ 
/*     */ 
/* 374 */     for (j = list.size() - 1; j >= 0; j--) {
/* 375 */       BlockPosition blockposition1 = (BlockPosition)list.get(j);
/* 376 */       IBlockData iblockdata = world.getType(blockposition1);
/* 377 */       Block block1 = iblockdata.getBlock();
/*     */       
/* 379 */       block1.toLegacyData(iblockdata);
/* 380 */       world.setAir(blockposition1);
/* 381 */       blockposition1 = blockposition1.shift(enumdirection1);
/* 382 */       world.setTypeAndData(blockposition1, Blocks.PISTON_EXTENSION.getBlockData().set(FACING, enumdirection), 4);
/* 383 */       world.setTileEntity(blockposition1, BlockPistonMoving.a(iblockdata, enumdirection, flag, false));
/* 384 */       i--;
/* 385 */       ablock[i] = block1;
/*     */     }
/*     */     
/* 388 */     BlockPosition blockposition2 = blockposition.shift(enumdirection);
/*     */     
/* 390 */     if (flag) {
/* 391 */       BlockPistonExtension.EnumPistonType blockpistonextension_enumpistontype = this.sticky ? BlockPistonExtension.EnumPistonType.STICKY : BlockPistonExtension.EnumPistonType.DEFAULT;
/*     */       
/* 393 */       IBlockData iblockdata = Blocks.PISTON_HEAD.getBlockData().set(BlockPistonExtension.FACING, enumdirection).set(BlockPistonExtension.TYPE, blockpistonextension_enumpistontype);
/* 394 */       IBlockData iblockdata1 = Blocks.PISTON_EXTENSION.getBlockData().set(BlockPistonMoving.FACING, enumdirection).set(BlockPistonMoving.TYPE, this.sticky ? BlockPistonExtension.EnumPistonType.STICKY : BlockPistonExtension.EnumPistonType.DEFAULT);
/*     */       
/* 396 */       world.setTypeAndData(blockposition2, iblockdata1, 4);
/* 397 */       world.setTileEntity(blockposition2, BlockPistonMoving.a(iblockdata, enumdirection, true, false));
/*     */     }
/*     */     
/*     */ 
/*     */ 
/* 402 */     for (int k = list1.size() - 1; k >= 0; k--) {
/* 403 */       world.applyPhysics((BlockPosition)list1.get(k), ablock[(i++)]);
/*     */     }
/*     */     
/* 406 */     for (k = list.size() - 1; k >= 0; k--) {
/* 407 */       world.applyPhysics((BlockPosition)list.get(k), ablock[(i++)]);
/*     */     }
/*     */     
/* 410 */     if (flag) {
/* 411 */       world.applyPhysics(blockposition2, Blocks.PISTON_HEAD);
/* 412 */       world.applyPhysics(blockposition, this);
/*     */     }
/*     */     
/* 415 */     return true;
/*     */   }
/*     */   
/*     */   public IBlockData fromLegacyData(int i)
/*     */   {
/* 420 */     return getBlockData().set(FACING, b(i)).set(EXTENDED, Boolean.valueOf((i & 0x8) > 0));
/*     */   }
/*     */   
/*     */   public int toLegacyData(IBlockData iblockdata) {
/* 424 */     byte b0 = 0;
/* 425 */     int i = b0 | ((EnumDirection)iblockdata.get(FACING)).a();
/*     */     
/* 427 */     if (((Boolean)iblockdata.get(EXTENDED)).booleanValue()) {
/* 428 */       i |= 0x8;
/*     */     }
/*     */     
/* 431 */     return i;
/*     */   }
/*     */   
/*     */   protected BlockStateList getStateList() {
/* 435 */     return new BlockStateList(this, new IBlockState[] { FACING, EXTENDED });
/*     */   }
/*     */   
/*     */   static class SyntheticClass_1
/*     */   {
/* 440 */     static final int[] a = new int[EnumDirection.values().length];
/*     */     
/*     */     static {
/*     */       try {
/* 444 */         a[EnumDirection.DOWN.ordinal()] = 1;
/*     */       }
/*     */       catch (NoSuchFieldError localNoSuchFieldError1) {}
/*     */       
/*     */       try
/*     */       {
/* 450 */         a[EnumDirection.UP.ordinal()] = 2;
/*     */       }
/*     */       catch (NoSuchFieldError localNoSuchFieldError2) {}
/*     */       
/*     */       try
/*     */       {
/* 456 */         a[EnumDirection.NORTH.ordinal()] = 3;
/*     */       }
/*     */       catch (NoSuchFieldError localNoSuchFieldError3) {}
/*     */       
/*     */       try
/*     */       {
/* 462 */         a[EnumDirection.SOUTH.ordinal()] = 4;
/*     */       }
/*     */       catch (NoSuchFieldError localNoSuchFieldError4) {}
/*     */       
/*     */       try
/*     */       {
/* 468 */         a[EnumDirection.WEST.ordinal()] = 5;
/*     */       }
/*     */       catch (NoSuchFieldError localNoSuchFieldError5) {}
/*     */       
/*     */       try
/*     */       {
/* 474 */         a[EnumDirection.EAST.ordinal()] = 6;
/*     */       }
/*     */       catch (NoSuchFieldError localNoSuchFieldError6) {}
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_8_R3\BlockPiston.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */
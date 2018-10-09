/*     */ package net.minecraft.server.v1_8_R3;
/*     */ 
/*     */ import java.util.Iterator;
/*     */ import java.util.Random;
/*     */ import org.bukkit.craftbukkit.v1_8_R3.CraftWorld;
/*     */ import org.bukkit.craftbukkit.v1_8_R3.event.CraftEventFactory;
/*     */ 
/*     */ public class BlockVine extends Block
/*     */ {
/*  10 */   public static final BlockStateBoolean UP = BlockStateBoolean.of("up");
/*  11 */   public static final BlockStateBoolean NORTH = BlockStateBoolean.of("north");
/*  12 */   public static final BlockStateBoolean EAST = BlockStateBoolean.of("east");
/*  13 */   public static final BlockStateBoolean SOUTH = BlockStateBoolean.of("south");
/*  14 */   public static final BlockStateBoolean WEST = BlockStateBoolean.of("west");
/*  15 */   public static final BlockStateBoolean[] Q = { UP, NORTH, SOUTH, WEST, EAST };
/*     */   
/*     */   public BlockVine() {
/*  18 */     super(Material.REPLACEABLE_PLANT);
/*  19 */     j(this.blockStateList.getBlockData().set(UP, Boolean.valueOf(false)).set(NORTH, Boolean.valueOf(false)).set(EAST, Boolean.valueOf(false)).set(SOUTH, Boolean.valueOf(false)).set(WEST, Boolean.valueOf(false)));
/*  20 */     a(true);
/*  21 */     a(CreativeModeTab.c);
/*     */   }
/*     */   
/*     */   public IBlockData updateState(IBlockData iblockdata, IBlockAccess iblockaccess, BlockPosition blockposition) {
/*  25 */     return iblockdata.set(UP, Boolean.valueOf(iblockaccess.getType(blockposition.up()).getBlock().u()));
/*     */   }
/*     */   
/*     */   public void j() {
/*  29 */     a(0.0F, 0.0F, 0.0F, 1.0F, 1.0F, 1.0F);
/*     */   }
/*     */   
/*     */   public boolean c() {
/*  33 */     return false;
/*     */   }
/*     */   
/*     */   public boolean d() {
/*  37 */     return false;
/*     */   }
/*     */   
/*     */   public boolean a(World world, BlockPosition blockposition) {
/*  41 */     return true;
/*     */   }
/*     */   
/*     */   public void updateShape(IBlockAccess iblockaccess, BlockPosition blockposition)
/*     */   {
/*  46 */     float f1 = 1.0F;
/*  47 */     float f2 = 1.0F;
/*  48 */     float f3 = 1.0F;
/*  49 */     float f4 = 0.0F;
/*  50 */     float f5 = 0.0F;
/*  51 */     float f6 = 0.0F;
/*  52 */     boolean flag = false;
/*     */     
/*  54 */     if (((Boolean)iblockaccess.getType(blockposition).get(WEST)).booleanValue()) {
/*  55 */       f4 = Math.max(f4, 0.0625F);
/*  56 */       f1 = 0.0F;
/*  57 */       f2 = 0.0F;
/*  58 */       f5 = 1.0F;
/*  59 */       f3 = 0.0F;
/*  60 */       f6 = 1.0F;
/*  61 */       flag = true;
/*     */     }
/*     */     
/*  64 */     if (((Boolean)iblockaccess.getType(blockposition).get(EAST)).booleanValue()) {
/*  65 */       f1 = Math.min(f1, 0.9375F);
/*  66 */       f4 = 1.0F;
/*  67 */       f2 = 0.0F;
/*  68 */       f5 = 1.0F;
/*  69 */       f3 = 0.0F;
/*  70 */       f6 = 1.0F;
/*  71 */       flag = true;
/*     */     }
/*     */     
/*  74 */     if (((Boolean)iblockaccess.getType(blockposition).get(NORTH)).booleanValue()) {
/*  75 */       f6 = Math.max(f6, 0.0625F);
/*  76 */       f3 = 0.0F;
/*  77 */       f1 = 0.0F;
/*  78 */       f4 = 1.0F;
/*  79 */       f2 = 0.0F;
/*  80 */       f5 = 1.0F;
/*  81 */       flag = true;
/*     */     }
/*     */     
/*  84 */     if (((Boolean)iblockaccess.getType(blockposition).get(SOUTH)).booleanValue()) {
/*  85 */       f3 = Math.min(f3, 0.9375F);
/*  86 */       f6 = 1.0F;
/*  87 */       f1 = 0.0F;
/*  88 */       f4 = 1.0F;
/*  89 */       f2 = 0.0F;
/*  90 */       f5 = 1.0F;
/*  91 */       flag = true;
/*     */     }
/*     */     
/*  94 */     if ((!flag) && (c(iblockaccess.getType(blockposition.up()).getBlock()))) {
/*  95 */       f2 = Math.min(f2, 0.9375F);
/*  96 */       f5 = 1.0F;
/*  97 */       f1 = 0.0F;
/*  98 */       f4 = 1.0F;
/*  99 */       f3 = 0.0F;
/* 100 */       f6 = 1.0F;
/*     */     }
/*     */     
/* 103 */     a(f1, f2, f3, f4, f5, f6);
/*     */   }
/*     */   
/*     */   public AxisAlignedBB a(World world, BlockPosition blockposition, IBlockData iblockdata) {
/* 107 */     return null;
/*     */   }
/*     */   
/*     */   public boolean canPlace(World world, BlockPosition blockposition, EnumDirection enumdirection) {
/* 111 */     switch (SyntheticClass_1.a[enumdirection.ordinal()]) {
/*     */     case 1: 
/* 113 */       return c(world.getType(blockposition.up()).getBlock());
/*     */     
/*     */     case 2: 
/*     */     case 3: 
/*     */     case 4: 
/*     */     case 5: 
/* 119 */       return c(world.getType(blockposition.shift(enumdirection.opposite())).getBlock());
/*     */     }
/*     */     
/* 122 */     return false;
/*     */   }
/*     */   
/*     */   private boolean c(Block block)
/*     */   {
/* 127 */     return (block.d()) && (block.material.isSolid());
/*     */   }
/*     */   
/*     */   private boolean e(World world, BlockPosition blockposition, IBlockData iblockdata) {
/* 131 */     IBlockData iblockdata1 = iblockdata;
/* 132 */     Iterator iterator = EnumDirection.EnumDirectionLimit.HORIZONTAL.iterator();
/*     */     
/* 134 */     while (iterator.hasNext()) {
/* 135 */       EnumDirection enumdirection = (EnumDirection)iterator.next();
/* 136 */       BlockStateBoolean blockstateboolean = getDirection(enumdirection);
/*     */       
/* 138 */       if ((((Boolean)iblockdata.get(blockstateboolean)).booleanValue()) && (!c(world.getType(blockposition.shift(enumdirection)).getBlock()))) {
/* 139 */         IBlockData iblockdata2 = world.getType(blockposition.up());
/*     */         
/* 141 */         if ((iblockdata2.getBlock() != this) || (!((Boolean)iblockdata2.get(blockstateboolean)).booleanValue())) {
/* 142 */           iblockdata = iblockdata.set(blockstateboolean, Boolean.valueOf(false));
/*     */         }
/*     */       }
/*     */     }
/*     */     
/* 147 */     if (d(iblockdata) == 0) {
/* 148 */       return false;
/*     */     }
/* 150 */     if (iblockdata1 != iblockdata) {
/* 151 */       world.setTypeAndData(blockposition, iblockdata, 2);
/*     */     }
/*     */     
/* 154 */     return true;
/*     */   }
/*     */   
/*     */   public void doPhysics(World world, BlockPosition blockposition, IBlockData iblockdata, Block block)
/*     */   {
/* 159 */     if ((!world.isClientSide) && (!e(world, blockposition, iblockdata))) {
/* 160 */       b(world, blockposition, iblockdata, 0);
/* 161 */       world.setAir(blockposition);
/*     */     }
/*     */   }
/*     */   
/*     */   public void b(World world, BlockPosition blockposition, IBlockData iblockdata, Random random)
/*     */   {
/* 167 */     if ((!world.isClientSide) && 
/* 168 */       (world.random.nextInt(4) == 0)) {
/* 169 */       byte b0 = 4;
/* 170 */       int i = 5;
/* 171 */       boolean flag = false;
/*     */       
/*     */ 
/* 174 */       for (int j = -b0; j <= b0; j++) {
/* 175 */         for (int k = -b0; k <= b0; k++) {
/* 176 */           for (int l = -1; l <= 1; l++) {
/* 177 */             if (world.getType(blockposition.a(j, l, k)).getBlock() == this) {
/* 178 */               i--;
/* 179 */               if (i <= 0) {
/* 180 */                 flag = true;
/* 181 */                 break;
/*     */               }
/*     */             }
/*     */           }
/*     */         }
/*     */       }
/*     */       
/* 188 */       EnumDirection enumdirection = EnumDirection.a(random);
/* 189 */       BlockPosition blockposition1 = blockposition.up();
/*     */       
/*     */ 
/* 192 */       if ((enumdirection == EnumDirection.UP) && (blockposition.getY() < 255) && (world.isEmpty(blockposition1))) {
/* 193 */         if (!flag) {
/* 194 */           IBlockData iblockdata1 = iblockdata;
/* 195 */           Iterator iterator = EnumDirection.EnumDirectionLimit.HORIZONTAL.iterator();
/*     */           
/* 197 */           while (iterator.hasNext()) {
/* 198 */             EnumDirection enumdirection1 = (EnumDirection)iterator.next();
/* 199 */             if ((random.nextBoolean()) || (!c(world.getType(blockposition1.shift(enumdirection1)).getBlock()))) {
/* 200 */               iblockdata1 = iblockdata1.set(getDirection(enumdirection1), Boolean.valueOf(false));
/*     */             }
/*     */           }
/*     */           
/* 204 */           if ((((Boolean)iblockdata1.get(NORTH)).booleanValue()) || (((Boolean)iblockdata1.get(EAST)).booleanValue()) || (((Boolean)iblockdata1.get(SOUTH)).booleanValue()) || (((Boolean)iblockdata1.get(WEST)).booleanValue()))
/*     */           {
/*     */ 
/* 207 */             BlockPosition target = blockposition1;
/* 208 */             org.bukkit.block.Block source = world.getWorld().getBlockAt(blockposition.getX(), blockposition.getY(), blockposition.getZ());
/* 209 */             org.bukkit.block.Block block = world.getWorld().getBlockAt(target.getX(), target.getY(), target.getZ());
/* 210 */             CraftEventFactory.handleBlockSpreadEvent(block, source, this, toLegacyData(iblockdata1));
/*     */ 
/*     */           }
/*     */           
/*     */         }
/*     */         
/*     */ 
/*     */       }
/* 218 */       else if ((enumdirection.k().c()) && (!((Boolean)iblockdata.get(getDirection(enumdirection))).booleanValue())) {
/* 219 */         if (!flag) {
/* 220 */           BlockPosition blockposition2 = blockposition.shift(enumdirection);
/* 221 */           Block block = world.getType(blockposition2).getBlock();
/*     */           
/* 223 */           if (block.material == Material.AIR) {
/* 224 */             EnumDirection enumdirection1 = enumdirection.e();
/* 225 */             EnumDirection enumdirection2 = enumdirection.f();
/* 226 */             boolean flag1 = ((Boolean)iblockdata.get(getDirection(enumdirection1))).booleanValue();
/* 227 */             boolean flag2 = ((Boolean)iblockdata.get(getDirection(enumdirection2))).booleanValue();
/* 228 */             BlockPosition blockposition3 = blockposition2.shift(enumdirection1);
/* 229 */             BlockPosition blockposition4 = blockposition2.shift(enumdirection2);
/*     */             
/*     */ 
/* 232 */             org.bukkit.block.Block source = world.getWorld().getBlockAt(blockposition.getX(), blockposition.getY(), blockposition.getZ());
/* 233 */             org.bukkit.block.Block bukkitBlock = world.getWorld().getBlockAt(blockposition2.getX(), blockposition2.getY(), blockposition2.getZ());
/*     */             
/* 235 */             if ((flag1) && (c(world.getType(blockposition3).getBlock())))
/*     */             {
/* 237 */               CraftEventFactory.handleBlockSpreadEvent(bukkitBlock, source, this, toLegacyData(getBlockData().set(getDirection(enumdirection1), Boolean.valueOf(true))));
/* 238 */             } else if ((flag2) && (c(world.getType(blockposition4).getBlock())))
/*     */             {
/* 240 */               CraftEventFactory.handleBlockSpreadEvent(bukkitBlock, source, this, toLegacyData(getBlockData().set(getDirection(enumdirection2), Boolean.valueOf(true))));
/* 241 */             } else if ((flag1) && (world.isEmpty(blockposition3)) && (c(world.getType(blockposition.shift(enumdirection1)).getBlock())))
/*     */             {
/* 243 */               bukkitBlock = world.getWorld().getBlockAt(blockposition3.getX(), blockposition3.getY(), blockposition3.getZ());
/* 244 */               CraftEventFactory.handleBlockSpreadEvent(bukkitBlock, source, this, toLegacyData(getBlockData().set(getDirection(enumdirection.opposite()), Boolean.valueOf(true))));
/* 245 */             } else if ((flag2) && (world.isEmpty(blockposition4)) && (c(world.getType(blockposition.shift(enumdirection2)).getBlock())))
/*     */             {
/* 247 */               bukkitBlock = world.getWorld().getBlockAt(blockposition4.getX(), blockposition4.getY(), blockposition4.getZ());
/* 248 */               CraftEventFactory.handleBlockSpreadEvent(bukkitBlock, source, this, toLegacyData(getBlockData().set(getDirection(enumdirection.opposite()), Boolean.valueOf(true))));
/* 249 */             } else if (c(world.getType(blockposition2.up()).getBlock()))
/*     */             {
/* 251 */               CraftEventFactory.handleBlockSpreadEvent(bukkitBlock, source, this, toLegacyData(getBlockData()));
/*     */             }
/*     */           }
/* 254 */           else if ((block.material.k()) && (block.d())) {
/* 255 */             world.setTypeAndData(blockposition, iblockdata.set(getDirection(enumdirection), Boolean.valueOf(true)), 2);
/*     */           }
/*     */           
/*     */         }
/*     */       }
/* 260 */       else if (blockposition.getY() > 1) {
/* 261 */         BlockPosition blockposition2 = blockposition.down();
/* 262 */         IBlockData iblockdata2 = world.getType(blockposition2);
/* 263 */         Block block1 = iblockdata2.getBlock();
/*     */         
/*     */ 
/*     */ 
/*     */ 
/* 268 */         if (block1.material == Material.AIR) {
/* 269 */           IBlockData iblockdata3 = iblockdata;
/* 270 */           Iterator iterator1 = EnumDirection.EnumDirectionLimit.HORIZONTAL.iterator();
/*     */           
/* 272 */           while (iterator1.hasNext()) {
/* 273 */             EnumDirection enumdirection3 = (EnumDirection)iterator1.next();
/* 274 */             if (random.nextBoolean()) {
/* 275 */               iblockdata3 = iblockdata3.set(getDirection(enumdirection3), Boolean.valueOf(false));
/*     */             }
/*     */           }
/*     */           
/* 279 */           if ((((Boolean)iblockdata3.get(NORTH)).booleanValue()) || (((Boolean)iblockdata3.get(EAST)).booleanValue()) || (((Boolean)iblockdata3.get(SOUTH)).booleanValue()) || (((Boolean)iblockdata3.get(WEST)).booleanValue()))
/*     */           {
/*     */ 
/* 282 */             org.bukkit.block.Block source = world.getWorld().getBlockAt(blockposition.getX(), blockposition.getY(), blockposition.getZ());
/* 283 */             org.bukkit.block.Block bukkitBlock = world.getWorld().getBlockAt(blockposition2.getX(), blockposition2.getY(), blockposition2.getZ());
/* 284 */             CraftEventFactory.handleBlockSpreadEvent(bukkitBlock, source, this, toLegacyData(iblockdata3));
/*     */           }
/*     */         }
/* 287 */         else if (block1 == this) {
/* 288 */           IBlockData iblockdata3 = iblockdata2;
/* 289 */           Iterator iterator1 = EnumDirection.EnumDirectionLimit.HORIZONTAL.iterator();
/*     */           
/* 291 */           while (iterator1.hasNext()) {
/* 292 */             EnumDirection enumdirection3 = (EnumDirection)iterator1.next();
/* 293 */             BlockStateBoolean blockstateboolean = getDirection(enumdirection3);
/*     */             
/* 295 */             if ((random.nextBoolean()) && (((Boolean)iblockdata.get(blockstateboolean)).booleanValue())) {
/* 296 */               iblockdata3 = iblockdata3.set(blockstateboolean, Boolean.valueOf(true));
/*     */             }
/*     */           }
/*     */           
/* 300 */           if ((((Boolean)iblockdata3.get(NORTH)).booleanValue()) || (((Boolean)iblockdata3.get(EAST)).booleanValue()) || (((Boolean)iblockdata3.get(SOUTH)).booleanValue()) || (((Boolean)iblockdata3.get(WEST)).booleanValue())) {
/* 301 */             world.setTypeAndData(blockposition2, iblockdata3, 2);
/*     */           }
/*     */         }
/*     */       }
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public IBlockData getPlacedState(World world, BlockPosition blockposition, EnumDirection enumdirection, float f, float f1, float f2, int i, EntityLiving entityliving)
/*     */   {
/* 313 */     IBlockData iblockdata = getBlockData().set(UP, Boolean.valueOf(false)).set(NORTH, Boolean.valueOf(false)).set(EAST, Boolean.valueOf(false)).set(SOUTH, Boolean.valueOf(false)).set(WEST, Boolean.valueOf(false));
/*     */     
/* 315 */     return enumdirection.k().c() ? iblockdata.set(getDirection(enumdirection.opposite()), Boolean.valueOf(true)) : iblockdata;
/*     */   }
/*     */   
/*     */   public Item getDropType(IBlockData iblockdata, Random random, int i) {
/* 319 */     return null;
/*     */   }
/*     */   
/*     */   public int a(Random random) {
/* 323 */     return 0;
/*     */   }
/*     */   
/*     */   public void a(World world, EntityHuman entityhuman, BlockPosition blockposition, IBlockData iblockdata, TileEntity tileentity) {
/* 327 */     if ((!world.isClientSide) && (entityhuman.bZ() != null) && (entityhuman.bZ().getItem() == Items.SHEARS)) {
/* 328 */       entityhuman.b(StatisticList.MINE_BLOCK_COUNT[Block.getId(this)]);
/* 329 */       a(world, blockposition, new ItemStack(Blocks.VINE, 1, 0));
/*     */     } else {
/* 331 */       super.a(world, entityhuman, blockposition, iblockdata, tileentity);
/*     */     }
/*     */   }
/*     */   
/*     */   public IBlockData fromLegacyData(int i)
/*     */   {
/* 337 */     return getBlockData().set(SOUTH, Boolean.valueOf((i & 0x1) > 0)).set(WEST, Boolean.valueOf((i & 0x2) > 0)).set(NORTH, Boolean.valueOf((i & 0x4) > 0)).set(EAST, Boolean.valueOf((i & 0x8) > 0));
/*     */   }
/*     */   
/*     */   public int toLegacyData(IBlockData iblockdata) {
/* 341 */     int i = 0;
/*     */     
/* 343 */     if (((Boolean)iblockdata.get(SOUTH)).booleanValue()) {
/* 344 */       i |= 0x1;
/*     */     }
/*     */     
/* 347 */     if (((Boolean)iblockdata.get(WEST)).booleanValue()) {
/* 348 */       i |= 0x2;
/*     */     }
/*     */     
/* 351 */     if (((Boolean)iblockdata.get(NORTH)).booleanValue()) {
/* 352 */       i |= 0x4;
/*     */     }
/*     */     
/* 355 */     if (((Boolean)iblockdata.get(EAST)).booleanValue()) {
/* 356 */       i |= 0x8;
/*     */     }
/*     */     
/* 359 */     return i;
/*     */   }
/*     */   
/*     */   protected BlockStateList getStateList() {
/* 363 */     return new BlockStateList(this, new IBlockState[] { UP, NORTH, EAST, SOUTH, WEST });
/*     */   }
/*     */   
/*     */   public static BlockStateBoolean getDirection(EnumDirection enumdirection) {
/* 367 */     switch (SyntheticClass_1.a[enumdirection.ordinal()]) {
/*     */     case 1: 
/* 369 */       return UP;
/*     */     
/*     */     case 2: 
/* 372 */       return NORTH;
/*     */     
/*     */     case 3: 
/* 375 */       return SOUTH;
/*     */     
/*     */     case 4: 
/* 378 */       return EAST;
/*     */     
/*     */     case 5: 
/* 381 */       return WEST;
/*     */     }
/*     */     
/* 384 */     throw new IllegalArgumentException(enumdirection + " is an invalid choice");
/*     */   }
/*     */   
/*     */   public static int d(IBlockData iblockdata)
/*     */   {
/* 389 */     int i = 0;
/* 390 */     BlockStateBoolean[] ablockstateboolean = Q;
/* 391 */     int j = ablockstateboolean.length;
/*     */     
/* 393 */     for (int k = 0; k < j; k++) {
/* 394 */       BlockStateBoolean blockstateboolean = ablockstateboolean[k];
/*     */       
/* 396 */       if (((Boolean)iblockdata.get(blockstateboolean)).booleanValue()) {
/* 397 */         i++;
/*     */       }
/*     */     }
/*     */     
/* 401 */     return i;
/*     */   }
/*     */   
/*     */   static class SyntheticClass_1
/*     */   {
/* 406 */     static final int[] a = new int[EnumDirection.values().length];
/*     */     
/*     */     static {
/*     */       try {
/* 410 */         a[EnumDirection.UP.ordinal()] = 1;
/*     */       }
/*     */       catch (NoSuchFieldError localNoSuchFieldError1) {}
/*     */       
/*     */       try
/*     */       {
/* 416 */         a[EnumDirection.NORTH.ordinal()] = 2;
/*     */       }
/*     */       catch (NoSuchFieldError localNoSuchFieldError2) {}
/*     */       
/*     */       try
/*     */       {
/* 422 */         a[EnumDirection.SOUTH.ordinal()] = 3;
/*     */       }
/*     */       catch (NoSuchFieldError localNoSuchFieldError3) {}
/*     */       
/*     */       try
/*     */       {
/* 428 */         a[EnumDirection.EAST.ordinal()] = 4;
/*     */       }
/*     */       catch (NoSuchFieldError localNoSuchFieldError4) {}
/*     */       
/*     */       try
/*     */       {
/* 434 */         a[EnumDirection.WEST.ordinal()] = 5;
/*     */       }
/*     */       catch (NoSuchFieldError localNoSuchFieldError5) {}
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_8_R3\BlockVine.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */
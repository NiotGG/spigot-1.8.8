/*     */ package net.minecraft.server.v1_8_R3;
/*     */ 
/*     */ import com.google.common.collect.Maps;
/*     */ import java.util.Map;
/*     */ import java.util.Random;
/*     */ import org.bukkit.Server;
/*     */ import org.bukkit.block.BlockState;
/*     */ import org.bukkit.craftbukkit.v1_8_R3.CraftWorld;
/*     */ import org.bukkit.event.block.BlockBurnEvent;
/*     */ import org.bukkit.event.block.BlockSpreadEvent;
/*     */ import org.bukkit.plugin.PluginManager;
/*     */ 
/*     */ public class BlockFire extends Block
/*     */ {
/*  15 */   public static final BlockStateInteger AGE = BlockStateInteger.of("age", 0, 15);
/*  16 */   public static final BlockStateBoolean FLIP = BlockStateBoolean.of("flip");
/*  17 */   public static final BlockStateBoolean ALT = BlockStateBoolean.of("alt");
/*  18 */   public static final BlockStateBoolean NORTH = BlockStateBoolean.of("north");
/*  19 */   public static final BlockStateBoolean EAST = BlockStateBoolean.of("east");
/*  20 */   public static final BlockStateBoolean SOUTH = BlockStateBoolean.of("south");
/*  21 */   public static final BlockStateBoolean WEST = BlockStateBoolean.of("west");
/*  22 */   public static final BlockStateInteger UPPER = BlockStateInteger.of("upper", 0, 2);
/*  23 */   private final Map<Block, Integer> flameChances = Maps.newIdentityHashMap();
/*  24 */   private final Map<Block, Integer> U = Maps.newIdentityHashMap();
/*     */   
/*     */   public IBlockData updateState(IBlockData iblockdata, IBlockAccess iblockaccess, BlockPosition blockposition) {
/*  27 */     int i = blockposition.getX();
/*  28 */     int j = blockposition.getY();
/*  29 */     int k = blockposition.getZ();
/*     */     
/*  31 */     if ((!World.a(iblockaccess, blockposition.down())) && (!Blocks.FIRE.e(iblockaccess, blockposition.down()))) {
/*  32 */       boolean flag = (i + j + k & 0x1) == 1;
/*  33 */       boolean flag1 = (i / 2 + j / 2 + k / 2 & 0x1) == 1;
/*  34 */       int l = 0;
/*     */       
/*  36 */       if (e(iblockaccess, blockposition.up())) {
/*  37 */         l = flag ? 1 : 2;
/*     */       }
/*     */       
/*  40 */       return iblockdata.set(NORTH, Boolean.valueOf(e(iblockaccess, blockposition.north()))).set(EAST, Boolean.valueOf(e(iblockaccess, blockposition.east()))).set(SOUTH, Boolean.valueOf(e(iblockaccess, blockposition.south()))).set(WEST, Boolean.valueOf(e(iblockaccess, blockposition.west()))).set(UPPER, Integer.valueOf(l)).set(FLIP, Boolean.valueOf(flag1)).set(ALT, Boolean.valueOf(flag));
/*     */     }
/*  42 */     return getBlockData();
/*     */   }
/*     */   
/*     */   protected BlockFire()
/*     */   {
/*  47 */     super(Material.FIRE);
/*  48 */     j(this.blockStateList.getBlockData().set(AGE, Integer.valueOf(0)).set(FLIP, Boolean.valueOf(false)).set(ALT, Boolean.valueOf(false)).set(NORTH, Boolean.valueOf(false)).set(EAST, Boolean.valueOf(false)).set(SOUTH, Boolean.valueOf(false)).set(WEST, Boolean.valueOf(false)).set(UPPER, Integer.valueOf(0)));
/*  49 */     a(true);
/*     */   }
/*     */   
/*     */   public static void l() {
/*  53 */     Blocks.FIRE.a(Blocks.PLANKS, 5, 20);
/*  54 */     Blocks.FIRE.a(Blocks.DOUBLE_WOODEN_SLAB, 5, 20);
/*  55 */     Blocks.FIRE.a(Blocks.WOODEN_SLAB, 5, 20);
/*  56 */     Blocks.FIRE.a(Blocks.FENCE_GATE, 5, 20);
/*  57 */     Blocks.FIRE.a(Blocks.SPRUCE_FENCE_GATE, 5, 20);
/*  58 */     Blocks.FIRE.a(Blocks.BIRCH_FENCE_GATE, 5, 20);
/*  59 */     Blocks.FIRE.a(Blocks.JUNGLE_FENCE_GATE, 5, 20);
/*  60 */     Blocks.FIRE.a(Blocks.DARK_OAK_FENCE_GATE, 5, 20);
/*  61 */     Blocks.FIRE.a(Blocks.ACACIA_FENCE_GATE, 5, 20);
/*  62 */     Blocks.FIRE.a(Blocks.FENCE, 5, 20);
/*  63 */     Blocks.FIRE.a(Blocks.SPRUCE_FENCE, 5, 20);
/*  64 */     Blocks.FIRE.a(Blocks.BIRCH_FENCE, 5, 20);
/*  65 */     Blocks.FIRE.a(Blocks.JUNGLE_FENCE, 5, 20);
/*  66 */     Blocks.FIRE.a(Blocks.DARK_OAK_FENCE, 5, 20);
/*  67 */     Blocks.FIRE.a(Blocks.ACACIA_FENCE, 5, 20);
/*  68 */     Blocks.FIRE.a(Blocks.OAK_STAIRS, 5, 20);
/*  69 */     Blocks.FIRE.a(Blocks.BIRCH_STAIRS, 5, 20);
/*  70 */     Blocks.FIRE.a(Blocks.SPRUCE_STAIRS, 5, 20);
/*  71 */     Blocks.FIRE.a(Blocks.JUNGLE_STAIRS, 5, 20);
/*  72 */     Blocks.FIRE.a(Blocks.LOG, 5, 5);
/*  73 */     Blocks.FIRE.a(Blocks.LOG2, 5, 5);
/*  74 */     Blocks.FIRE.a(Blocks.LEAVES, 30, 60);
/*  75 */     Blocks.FIRE.a(Blocks.LEAVES2, 30, 60);
/*  76 */     Blocks.FIRE.a(Blocks.BOOKSHELF, 30, 20);
/*  77 */     Blocks.FIRE.a(Blocks.TNT, 15, 100);
/*  78 */     Blocks.FIRE.a(Blocks.TALLGRASS, 60, 100);
/*  79 */     Blocks.FIRE.a(Blocks.DOUBLE_PLANT, 60, 100);
/*  80 */     Blocks.FIRE.a(Blocks.YELLOW_FLOWER, 60, 100);
/*  81 */     Blocks.FIRE.a(Blocks.RED_FLOWER, 60, 100);
/*  82 */     Blocks.FIRE.a(Blocks.DEADBUSH, 60, 100);
/*  83 */     Blocks.FIRE.a(Blocks.WOOL, 30, 60);
/*  84 */     Blocks.FIRE.a(Blocks.VINE, 15, 100);
/*  85 */     Blocks.FIRE.a(Blocks.COAL_BLOCK, 5, 5);
/*  86 */     Blocks.FIRE.a(Blocks.HAY_BLOCK, 60, 20);
/*  87 */     Blocks.FIRE.a(Blocks.CARPET, 60, 20);
/*     */   }
/*     */   
/*     */   public void a(Block block, int i, int j) {
/*  91 */     this.flameChances.put(block, Integer.valueOf(i));
/*  92 */     this.U.put(block, Integer.valueOf(j));
/*     */   }
/*     */   
/*     */   public AxisAlignedBB a(World world, BlockPosition blockposition, IBlockData iblockdata) {
/*  96 */     return null;
/*     */   }
/*     */   
/*     */   public boolean c() {
/* 100 */     return false;
/*     */   }
/*     */   
/*     */   public boolean d() {
/* 104 */     return false;
/*     */   }
/*     */   
/*     */   public int a(Random random) {
/* 108 */     return 0;
/*     */   }
/*     */   
/*     */   public int a(World world) {
/* 112 */     return 30;
/*     */   }
/*     */   
/*     */   public void b(World world, BlockPosition blockposition, IBlockData iblockdata, Random random) {
/* 116 */     if (world.getGameRules().getBoolean("doFireTick")) {
/* 117 */       if (!canPlace(world, blockposition)) {
/* 118 */         fireExtinguished(world, blockposition);
/*     */       }
/*     */       
/* 121 */       Block block = world.getType(blockposition.down()).getBlock();
/* 122 */       boolean flag = block == Blocks.NETHERRACK;
/*     */       
/* 124 */       if (((world.worldProvider instanceof WorldProviderTheEnd)) && (block == Blocks.BEDROCK)) {
/* 125 */         flag = true;
/*     */       }
/*     */       
/* 128 */       if ((!flag) && (world.S()) && (e(world, blockposition))) {
/* 129 */         fireExtinguished(world, blockposition);
/*     */       } else {
/* 131 */         int i = ((Integer)iblockdata.get(AGE)).intValue();
/*     */         
/* 133 */         if (i < 15) {
/* 134 */           iblockdata = iblockdata.set(AGE, Integer.valueOf(i + random.nextInt(3) / 2));
/* 135 */           world.setTypeAndData(blockposition, iblockdata, 4);
/*     */         }
/*     */         
/* 138 */         world.a(blockposition, this, a(world) + random.nextInt(10));
/* 139 */         if (!flag) {
/* 140 */           if (!f(world, blockposition)) {
/* 141 */             if ((!World.a(world, blockposition.down())) || (i > 3)) {
/* 142 */               fireExtinguished(world, blockposition);
/*     */             }
/*     */             
/* 145 */             return;
/*     */           }
/*     */           
/* 148 */           if ((!e(world, blockposition.down())) && (i == 15) && (random.nextInt(4) == 0)) {
/* 149 */             fireExtinguished(world, blockposition);
/* 150 */             return;
/*     */           }
/*     */         }
/*     */         
/* 154 */         boolean flag1 = world.D(blockposition);
/* 155 */         byte b0 = 0;
/*     */         
/* 157 */         if (flag1) {
/* 158 */           b0 = -50;
/*     */         }
/*     */         
/* 161 */         a(world, blockposition.east(), 300 + b0, random, i);
/* 162 */         a(world, blockposition.west(), 300 + b0, random, i);
/* 163 */         a(world, blockposition.down(), 250 + b0, random, i);
/* 164 */         a(world, blockposition.up(), 250 + b0, random, i);
/* 165 */         a(world, blockposition.north(), 300 + b0, random, i);
/* 166 */         a(world, blockposition.south(), 300 + b0, random, i);
/*     */         
/* 168 */         for (int j = -1; j <= 1; j++) {
/* 169 */           for (int k = -1; k <= 1; k++) {
/* 170 */             for (int l = -1; l <= 4; l++) {
/* 171 */               if ((j != 0) || (l != 0) || (k != 0)) {
/* 172 */                 int i1 = 100;
/*     */                 
/* 174 */                 if (l > 1) {
/* 175 */                   i1 += (l - 1) * 100;
/*     */                 }
/*     */                 
/* 178 */                 BlockPosition blockposition1 = blockposition.a(j, l, k);
/* 179 */                 int j1 = m(world, blockposition1);
/*     */                 
/* 181 */                 if (j1 > 0) {
/* 182 */                   int k1 = (j1 + 40 + world.getDifficulty().a() * 7) / (i + 30);
/*     */                   
/* 184 */                   if (flag1) {
/* 185 */                     k1 /= 2;
/*     */                   }
/*     */                   
/* 188 */                   if ((k1 > 0) && (random.nextInt(i1) <= k1) && ((!world.S()) || (!e(world, blockposition1)))) {
/* 189 */                     int l1 = i + random.nextInt(5) / 4;
/*     */                     
/* 191 */                     if (l1 > 15) {
/* 192 */                       l1 = 15;
/*     */                     }
/*     */                     
/*     */ 
/* 196 */                     if ((world.getType(blockposition1) != Blocks.FIRE) && 
/* 197 */                       (!org.bukkit.craftbukkit.v1_8_R3.event.CraftEventFactory.callBlockIgniteEvent(world, blockposition1.getX(), blockposition1.getY(), blockposition1.getZ(), blockposition.getX(), blockposition.getY(), blockposition.getZ()).isCancelled()))
/*     */                     {
/*     */ 
/*     */ 
/* 201 */                       Server server = world.getServer();
/* 202 */                       org.bukkit.World bworld = world.getWorld();
/* 203 */                       BlockState blockState = bworld.getBlockAt(blockposition1.getX(), blockposition1.getY(), blockposition1.getZ()).getState();
/* 204 */                       blockState.setTypeId(Block.getId(this));
/* 205 */                       blockState.setData(new org.bukkit.material.MaterialData(Block.getId(this), (byte)l1));
/*     */                       
/* 207 */                       BlockSpreadEvent spreadEvent = new BlockSpreadEvent(blockState.getBlock(), bworld.getBlockAt(blockposition.getX(), blockposition.getY(), blockposition.getZ()), blockState);
/* 208 */                       server.getPluginManager().callEvent(spreadEvent);
/*     */                       
/* 210 */                       if (!spreadEvent.isCancelled()) {
/* 211 */                         blockState.update(true);
/*     */                       }
/*     */                     }
/*     */                   }
/*     */                 }
/*     */               }
/*     */             }
/*     */           }
/*     */         }
/*     */       }
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */   protected boolean e(World world, BlockPosition blockposition)
/*     */   {
/* 227 */     return (world.isRainingAt(blockposition)) || (world.isRainingAt(blockposition.west())) || (world.isRainingAt(blockposition.east())) || (world.isRainingAt(blockposition.north())) || (world.isRainingAt(blockposition.south()));
/*     */   }
/*     */   
/*     */   public boolean N() {
/* 231 */     return false;
/*     */   }
/*     */   
/*     */   private int c(Block block) {
/* 235 */     Integer integer = (Integer)this.U.get(block);
/*     */     
/* 237 */     return integer == null ? 0 : integer.intValue();
/*     */   }
/*     */   
/*     */   private int d(Block block) {
/* 241 */     Integer integer = (Integer)this.flameChances.get(block);
/*     */     
/* 243 */     return integer == null ? 0 : integer.intValue();
/*     */   }
/*     */   
/*     */   private void a(World world, BlockPosition blockposition, int i, Random random, int j) {
/* 247 */     int k = c(world.getType(blockposition).getBlock());
/*     */     
/* 249 */     if (random.nextInt(i) < k) {
/* 250 */       IBlockData iblockdata = world.getType(blockposition);
/*     */       
/*     */ 
/* 253 */       org.bukkit.block.Block theBlock = world.getWorld().getBlockAt(blockposition.getX(), blockposition.getY(), blockposition.getZ());
/*     */       
/* 255 */       BlockBurnEvent event = new BlockBurnEvent(theBlock);
/* 256 */       world.getServer().getPluginManager().callEvent(event);
/*     */       
/* 258 */       if (event.isCancelled()) {
/* 259 */         return;
/*     */       }
/*     */       
/*     */ 
/* 263 */       if ((random.nextInt(j + 10) < 5) && (!world.isRainingAt(blockposition))) {
/* 264 */         int l = j + random.nextInt(5) / 4;
/*     */         
/* 266 */         if (l > 15) {
/* 267 */           l = 15;
/*     */         }
/*     */         
/* 270 */         world.setTypeAndData(blockposition, getBlockData().set(AGE, Integer.valueOf(l)), 3);
/*     */       } else {
/* 272 */         fireExtinguished(world, blockposition);
/*     */       }
/*     */       
/* 275 */       if (iblockdata.getBlock() == Blocks.TNT) {
/* 276 */         Blocks.TNT.postBreak(world, blockposition, iblockdata.set(BlockTNT.EXPLODE, Boolean.valueOf(true)));
/*     */       }
/*     */     }
/*     */   }
/*     */   
/*     */   private boolean f(World world, BlockPosition blockposition)
/*     */   {
/* 283 */     EnumDirection[] aenumdirection = EnumDirection.values();
/* 284 */     int i = aenumdirection.length;
/*     */     
/* 286 */     for (int j = 0; j < i; j++) {
/* 287 */       EnumDirection enumdirection = aenumdirection[j];
/*     */       
/* 289 */       if (e(world, blockposition.shift(enumdirection))) {
/* 290 */         return true;
/*     */       }
/*     */     }
/*     */     
/* 294 */     return false;
/*     */   }
/*     */   
/*     */   private int m(World world, BlockPosition blockposition) {
/* 298 */     if (!world.isEmpty(blockposition)) {
/* 299 */       return 0;
/*     */     }
/* 301 */     int i = 0;
/* 302 */     EnumDirection[] aenumdirection = EnumDirection.values();
/* 303 */     int j = aenumdirection.length;
/*     */     
/* 305 */     for (int k = 0; k < j; k++) {
/* 306 */       EnumDirection enumdirection = aenumdirection[k];
/*     */       
/* 308 */       i = Math.max(d(world.getType(blockposition.shift(enumdirection)).getBlock()), i);
/*     */     }
/*     */     
/* 311 */     return i;
/*     */   }
/*     */   
/*     */   public boolean A()
/*     */   {
/* 316 */     return false;
/*     */   }
/*     */   
/*     */   public boolean e(IBlockAccess iblockaccess, BlockPosition blockposition) {
/* 320 */     return d(iblockaccess.getType(blockposition).getBlock()) > 0;
/*     */   }
/*     */   
/*     */   public boolean canPlace(World world, BlockPosition blockposition) {
/* 324 */     return (World.a(world, blockposition.down())) || (f(world, blockposition));
/*     */   }
/*     */   
/*     */   public void doPhysics(World world, BlockPosition blockposition, IBlockData iblockdata, Block block) {
/* 328 */     if ((!World.a(world, blockposition.down())) && (!f(world, blockposition))) {
/* 329 */       fireExtinguished(world, blockposition);
/*     */     }
/*     */   }
/*     */   
/*     */   public void onPlace(World world, BlockPosition blockposition, IBlockData iblockdata)
/*     */   {
/* 335 */     if ((world.worldProvider.getDimension() > 0) || (!Blocks.PORTAL.e(world, blockposition))) {
/* 336 */       if ((!World.a(world, blockposition.down())) && (!f(world, blockposition))) {
/* 337 */         fireExtinguished(world, blockposition);
/*     */       } else {
/* 339 */         world.a(blockposition, this, a(world) + world.random.nextInt(10));
/*     */       }
/*     */     }
/*     */   }
/*     */   
/*     */   public MaterialMapColor g(IBlockData iblockdata) {
/* 345 */     return MaterialMapColor.f;
/*     */   }
/*     */   
/*     */   public IBlockData fromLegacyData(int i) {
/* 349 */     return getBlockData().set(AGE, Integer.valueOf(i));
/*     */   }
/*     */   
/*     */   public int toLegacyData(IBlockData iblockdata) {
/* 353 */     return ((Integer)iblockdata.get(AGE)).intValue();
/*     */   }
/*     */   
/*     */   protected BlockStateList getStateList() {
/* 357 */     return new BlockStateList(this, new IBlockState[] { AGE, NORTH, EAST, SOUTH, WEST, UPPER, FLIP, ALT });
/*     */   }
/*     */   
/*     */   private void fireExtinguished(World world, BlockPosition position)
/*     */   {
/* 362 */     if (!org.bukkit.craftbukkit.v1_8_R3.event.CraftEventFactory.callBlockFadeEvent(world.getWorld().getBlockAt(position.getX(), position.getY(), position.getZ()), Blocks.AIR).isCancelled()) {
/* 363 */       world.setAir(position);
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_8_R3\BlockFire.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */
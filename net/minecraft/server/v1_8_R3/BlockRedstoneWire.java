/*     */ package net.minecraft.server.v1_8_R3;
/*     */ 
/*     */ import java.util.ArrayList;
/*     */ import java.util.EnumSet;
/*     */ import java.util.Iterator;
/*     */ import java.util.Random;
/*     */ import java.util.Set;
/*     */ import org.bukkit.craftbukkit.v1_8_R3.CraftServer;
/*     */ import org.bukkit.craftbukkit.v1_8_R3.CraftWorld;
/*     */ import org.bukkit.event.block.BlockRedstoneEvent;
/*     */ import org.bukkit.plugin.PluginManager;
/*     */ 
/*     */ public class BlockRedstoneWire extends Block
/*     */ {
/*  15 */   public static final BlockStateEnum<EnumRedstoneWireConnection> NORTH = BlockStateEnum.of("north", EnumRedstoneWireConnection.class);
/*  16 */   public static final BlockStateEnum<EnumRedstoneWireConnection> EAST = BlockStateEnum.of("east", EnumRedstoneWireConnection.class);
/*  17 */   public static final BlockStateEnum<EnumRedstoneWireConnection> SOUTH = BlockStateEnum.of("south", EnumRedstoneWireConnection.class);
/*  18 */   public static final BlockStateEnum<EnumRedstoneWireConnection> WEST = BlockStateEnum.of("west", EnumRedstoneWireConnection.class);
/*  19 */   public static final BlockStateInteger POWER = BlockStateInteger.of("power", 0, 15);
/*  20 */   private boolean Q = true;
/*  21 */   private final Set<BlockPosition> R = com.google.common.collect.Sets.newHashSet();
/*     */   
/*     */   public BlockRedstoneWire() {
/*  24 */     super(Material.ORIENTABLE);
/*  25 */     j(this.blockStateList.getBlockData().set(NORTH, EnumRedstoneWireConnection.NONE).set(EAST, EnumRedstoneWireConnection.NONE).set(SOUTH, EnumRedstoneWireConnection.NONE).set(WEST, EnumRedstoneWireConnection.NONE).set(POWER, Integer.valueOf(0)));
/*  26 */     a(0.0F, 0.0F, 0.0F, 1.0F, 0.0625F, 1.0F);
/*     */   }
/*     */   
/*     */   public IBlockData updateState(IBlockData iblockdata, IBlockAccess iblockaccess, BlockPosition blockposition) {
/*  30 */     iblockdata = iblockdata.set(WEST, c(iblockaccess, blockposition, EnumDirection.WEST));
/*  31 */     iblockdata = iblockdata.set(EAST, c(iblockaccess, blockposition, EnumDirection.EAST));
/*  32 */     iblockdata = iblockdata.set(NORTH, c(iblockaccess, blockposition, EnumDirection.NORTH));
/*  33 */     iblockdata = iblockdata.set(SOUTH, c(iblockaccess, blockposition, EnumDirection.SOUTH));
/*  34 */     return iblockdata;
/*     */   }
/*     */   
/*     */   private EnumRedstoneWireConnection c(IBlockAccess iblockaccess, BlockPosition blockposition, EnumDirection enumdirection) {
/*  38 */     BlockPosition blockposition1 = blockposition.shift(enumdirection);
/*  39 */     Block block = iblockaccess.getType(blockposition.shift(enumdirection)).getBlock();
/*     */     
/*  41 */     if ((!a(iblockaccess.getType(blockposition1), enumdirection)) && ((block.u()) || (!d(iblockaccess.getType(blockposition1.down()))))) {
/*  42 */       Block block1 = iblockaccess.getType(blockposition.up()).getBlock();
/*     */       
/*  44 */       return (!block1.u()) && (block.u()) && (d(iblockaccess.getType(blockposition1.up()))) ? EnumRedstoneWireConnection.UP : EnumRedstoneWireConnection.NONE;
/*     */     }
/*  46 */     return EnumRedstoneWireConnection.SIDE;
/*     */   }
/*     */   
/*     */   public AxisAlignedBB a(World world, BlockPosition blockposition, IBlockData iblockdata)
/*     */   {
/*  51 */     return null;
/*     */   }
/*     */   
/*     */   public boolean c() {
/*  55 */     return false;
/*     */   }
/*     */   
/*     */   public boolean d() {
/*  59 */     return false;
/*     */   }
/*     */   
/*     */   public boolean canPlace(World world, BlockPosition blockposition) {
/*  63 */     return (World.a(world, blockposition.down())) || (world.getType(blockposition.down()).getBlock() == Blocks.GLOWSTONE);
/*     */   }
/*     */   
/*     */   private IBlockData e(World world, BlockPosition blockposition, IBlockData iblockdata) {
/*  67 */     iblockdata = a(world, blockposition, blockposition, iblockdata);
/*  68 */     ArrayList arraylist = com.google.common.collect.Lists.newArrayList(this.R);
/*     */     
/*  70 */     this.R.clear();
/*  71 */     Iterator iterator = arraylist.iterator();
/*     */     
/*  73 */     while (iterator.hasNext()) {
/*  74 */       BlockPosition blockposition1 = (BlockPosition)iterator.next();
/*     */       
/*  76 */       world.applyPhysics(blockposition1, this);
/*     */     }
/*     */     
/*  79 */     return iblockdata;
/*     */   }
/*     */   
/*     */   private IBlockData a(World world, BlockPosition blockposition, BlockPosition blockposition1, IBlockData iblockdata) {
/*  83 */     IBlockData iblockdata1 = iblockdata;
/*  84 */     int i = ((Integer)iblockdata.get(POWER)).intValue();
/*  85 */     byte b0 = 0;
/*  86 */     int j = getPower(world, blockposition1, b0);
/*     */     
/*  88 */     this.Q = false;
/*  89 */     int k = world.A(blockposition);
/*     */     
/*  91 */     this.Q = true;
/*  92 */     if ((k > 0) && (k > j - 1)) {
/*  93 */       j = k;
/*     */     }
/*     */     
/*  96 */     int l = 0;
/*  97 */     Iterator iterator = EnumDirection.EnumDirectionLimit.HORIZONTAL.iterator();
/*     */     
/*  99 */     while (iterator.hasNext()) {
/* 100 */       EnumDirection enumdirection = (EnumDirection)iterator.next();
/* 101 */       BlockPosition blockposition2 = blockposition.shift(enumdirection);
/* 102 */       boolean flag = (blockposition2.getX() != blockposition1.getX()) || (blockposition2.getZ() != blockposition1.getZ());
/*     */       
/* 104 */       if (flag) {
/* 105 */         l = getPower(world, blockposition2, l);
/*     */       }
/*     */       
/* 108 */       if ((world.getType(blockposition2).getBlock().isOccluding()) && (!world.getType(blockposition.up()).getBlock().isOccluding())) {
/* 109 */         if ((flag) && (blockposition.getY() >= blockposition1.getY())) {
/* 110 */           l = getPower(world, blockposition2.up(), l);
/*     */         }
/* 112 */       } else if ((!world.getType(blockposition2).getBlock().isOccluding()) && (flag) && (blockposition.getY() <= blockposition1.getY())) {
/* 113 */         l = getPower(world, blockposition2.down(), l);
/*     */       }
/*     */     }
/*     */     
/* 117 */     if (l > j) {
/* 118 */       j = l - 1;
/* 119 */     } else if (j > 0) {
/* 120 */       j--;
/*     */     } else {
/* 122 */       j = 0;
/*     */     }
/*     */     
/* 125 */     if (k > j - 1) {
/* 126 */       j = k;
/*     */     }
/*     */     
/*     */ 
/* 130 */     if (i != j) {
/* 131 */       BlockRedstoneEvent event = new BlockRedstoneEvent(world.getWorld().getBlockAt(blockposition.getX(), blockposition.getY(), blockposition.getZ()), i, j);
/* 132 */       world.getServer().getPluginManager().callEvent(event);
/*     */       
/* 134 */       j = event.getNewCurrent();
/*     */     }
/*     */     
/*     */ 
/* 138 */     if (i != j) {
/* 139 */       iblockdata = iblockdata.set(POWER, Integer.valueOf(j));
/* 140 */       if (world.getType(blockposition) == iblockdata1) {
/* 141 */         world.setTypeAndData(blockposition, iblockdata, 2);
/*     */       }
/*     */       
/* 144 */       this.R.add(blockposition);
/* 145 */       EnumDirection[] aenumdirection = EnumDirection.values();
/* 146 */       int i1 = aenumdirection.length;
/*     */       
/* 148 */       for (int j1 = 0; j1 < i1; j1++) {
/* 149 */         EnumDirection enumdirection1 = aenumdirection[j1];
/*     */         
/* 151 */         this.R.add(blockposition.shift(enumdirection1));
/*     */       }
/*     */     }
/*     */     
/* 155 */     return iblockdata;
/*     */   }
/*     */   
/*     */   private void e(World world, BlockPosition blockposition) {
/* 159 */     if (world.getType(blockposition).getBlock() == this) {
/* 160 */       world.applyPhysics(blockposition, this);
/* 161 */       EnumDirection[] aenumdirection = EnumDirection.values();
/* 162 */       int i = aenumdirection.length;
/*     */       
/* 164 */       for (int j = 0; j < i; j++) {
/* 165 */         EnumDirection enumdirection = aenumdirection[j];
/*     */         
/* 167 */         world.applyPhysics(blockposition.shift(enumdirection), this);
/*     */       }
/*     */     }
/*     */   }
/*     */   
/*     */   public void onPlace(World world, BlockPosition blockposition, IBlockData iblockdata)
/*     */   {
/* 174 */     if (!world.isClientSide) {
/* 175 */       e(world, blockposition, iblockdata);
/* 176 */       Iterator iterator = EnumDirection.EnumDirectionLimit.VERTICAL.iterator();
/*     */       
/*     */ 
/*     */ 
/* 180 */       while (iterator.hasNext()) {
/* 181 */         EnumDirection enumdirection = (EnumDirection)iterator.next();
/* 182 */         world.applyPhysics(blockposition.shift(enumdirection), this);
/*     */       }
/*     */       
/* 185 */       iterator = EnumDirection.EnumDirectionLimit.HORIZONTAL.iterator();
/*     */       
/* 187 */       while (iterator.hasNext()) {
/* 188 */         EnumDirection enumdirection = (EnumDirection)iterator.next();
/* 189 */         e(world, blockposition.shift(enumdirection));
/*     */       }
/*     */       
/* 192 */       iterator = EnumDirection.EnumDirectionLimit.HORIZONTAL.iterator();
/*     */       
/* 194 */       while (iterator.hasNext()) {
/* 195 */         EnumDirection enumdirection = (EnumDirection)iterator.next();
/* 196 */         BlockPosition blockposition1 = blockposition.shift(enumdirection);
/*     */         
/* 198 */         if (world.getType(blockposition1).getBlock().isOccluding()) {
/* 199 */           e(world, blockposition1.up());
/*     */         } else {
/* 201 */           e(world, blockposition1.down());
/*     */         }
/*     */       }
/*     */     }
/*     */   }
/*     */   
/*     */   public void remove(World world, BlockPosition blockposition, IBlockData iblockdata)
/*     */   {
/* 209 */     super.remove(world, blockposition, iblockdata);
/* 210 */     if (!world.isClientSide) {
/* 211 */       EnumDirection[] aenumdirection = EnumDirection.values();
/* 212 */       int i = aenumdirection.length;
/*     */       
/* 214 */       for (int j = 0; j < i; j++) {
/* 215 */         EnumDirection enumdirection = aenumdirection[j];
/*     */         
/* 217 */         world.applyPhysics(blockposition.shift(enumdirection), this);
/*     */       }
/*     */       
/* 220 */       e(world, blockposition, iblockdata);
/* 221 */       Iterator iterator = EnumDirection.EnumDirectionLimit.HORIZONTAL.iterator();
/*     */       
/*     */ 
/*     */ 
/* 225 */       while (iterator.hasNext()) {
/* 226 */         EnumDirection enumdirection1 = (EnumDirection)iterator.next();
/* 227 */         e(world, blockposition.shift(enumdirection1));
/*     */       }
/*     */       
/* 230 */       iterator = EnumDirection.EnumDirectionLimit.HORIZONTAL.iterator();
/*     */       
/* 232 */       while (iterator.hasNext()) {
/* 233 */         EnumDirection enumdirection1 = (EnumDirection)iterator.next();
/* 234 */         BlockPosition blockposition1 = blockposition.shift(enumdirection1);
/*     */         
/* 236 */         if (world.getType(blockposition1).getBlock().isOccluding()) {
/* 237 */           e(world, blockposition1.up());
/*     */         } else {
/* 239 */           e(world, blockposition1.down());
/*     */         }
/*     */       }
/*     */     }
/*     */   }
/*     */   
/*     */   public int getPower(World world, BlockPosition blockposition, int i)
/*     */   {
/* 247 */     if (world.getType(blockposition).getBlock() != this) {
/* 248 */       return i;
/*     */     }
/* 250 */     int j = ((Integer)world.getType(blockposition).get(POWER)).intValue();
/*     */     
/* 252 */     return j > i ? j : i;
/*     */   }
/*     */   
/*     */   public void doPhysics(World world, BlockPosition blockposition, IBlockData iblockdata, Block block)
/*     */   {
/* 257 */     if (!world.isClientSide) {
/* 258 */       if (canPlace(world, blockposition)) {
/* 259 */         e(world, blockposition, iblockdata);
/*     */       } else {
/* 261 */         b(world, blockposition, iblockdata, 0);
/* 262 */         world.setAir(blockposition);
/*     */       }
/*     */     }
/*     */   }
/*     */   
/*     */   public Item getDropType(IBlockData iblockdata, Random random, int i)
/*     */   {
/* 269 */     return Items.REDSTONE;
/*     */   }
/*     */   
/*     */   public int b(IBlockAccess iblockaccess, BlockPosition blockposition, IBlockData iblockdata, EnumDirection enumdirection) {
/* 273 */     return !this.Q ? 0 : a(iblockaccess, blockposition, iblockdata, enumdirection);
/*     */   }
/*     */   
/*     */   public int a(IBlockAccess iblockaccess, BlockPosition blockposition, IBlockData iblockdata, EnumDirection enumdirection) {
/* 277 */     if (!this.Q) {
/* 278 */       return 0;
/*     */     }
/* 280 */     int i = ((Integer)iblockdata.get(POWER)).intValue();
/*     */     
/* 282 */     if (i == 0)
/* 283 */       return 0;
/* 284 */     if (enumdirection == EnumDirection.UP) {
/* 285 */       return i;
/*     */     }
/* 287 */     EnumSet enumset = EnumSet.noneOf(EnumDirection.class);
/* 288 */     Iterator iterator = EnumDirection.EnumDirectionLimit.HORIZONTAL.iterator();
/*     */     
/* 290 */     while (iterator.hasNext()) {
/* 291 */       EnumDirection enumdirection1 = (EnumDirection)iterator.next();
/*     */       
/* 293 */       if (d(iblockaccess, blockposition, enumdirection1)) {
/* 294 */         enumset.add(enumdirection1);
/*     */       }
/*     */     }
/*     */     
/* 298 */     if ((enumdirection.k().c()) && (enumset.isEmpty()))
/* 299 */       return i;
/* 300 */     if ((enumset.contains(enumdirection)) && (!enumset.contains(enumdirection.f())) && (!enumset.contains(enumdirection.e()))) {
/* 301 */       return i;
/*     */     }
/* 303 */     return 0;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   private boolean d(IBlockAccess iblockaccess, BlockPosition blockposition, EnumDirection enumdirection)
/*     */   {
/* 310 */     BlockPosition blockposition1 = blockposition.shift(enumdirection);
/* 311 */     IBlockData iblockdata = iblockaccess.getType(blockposition1);
/* 312 */     Block block = iblockdata.getBlock();
/* 313 */     boolean flag = block.isOccluding();
/* 314 */     boolean flag1 = iblockaccess.getType(blockposition.up()).getBlock().isOccluding();
/*     */     
/* 316 */     return (!flag1) && (flag) && (e(iblockaccess, blockposition1.up()));
/*     */   }
/*     */   
/*     */   protected static boolean e(IBlockAccess iblockaccess, BlockPosition blockposition) {
/* 320 */     return d(iblockaccess.getType(blockposition));
/*     */   }
/*     */   
/*     */   protected static boolean d(IBlockData iblockdata) {
/* 324 */     return a(iblockdata, null);
/*     */   }
/*     */   
/*     */   protected static boolean a(IBlockData iblockdata, EnumDirection enumdirection) {
/* 328 */     Block block = iblockdata.getBlock();
/*     */     
/* 330 */     if (block == Blocks.REDSTONE_WIRE)
/* 331 */       return true;
/* 332 */     if (Blocks.UNPOWERED_REPEATER.e(block)) {
/* 333 */       EnumDirection enumdirection1 = (EnumDirection)iblockdata.get(BlockRepeater.FACING);
/*     */       
/* 335 */       return (enumdirection1 == enumdirection) || (enumdirection1.opposite() == enumdirection);
/*     */     }
/* 337 */     return (block.isPowerSource()) && (enumdirection != null);
/*     */   }
/*     */   
/*     */   public boolean isPowerSource()
/*     */   {
/* 342 */     return this.Q;
/*     */   }
/*     */   
/*     */   public IBlockData fromLegacyData(int i) {
/* 346 */     return getBlockData().set(POWER, Integer.valueOf(i));
/*     */   }
/*     */   
/*     */   public int toLegacyData(IBlockData iblockdata) {
/* 350 */     return ((Integer)iblockdata.get(POWER)).intValue();
/*     */   }
/*     */   
/*     */   protected BlockStateList getStateList() {
/* 354 */     return new BlockStateList(this, new IBlockState[] { NORTH, EAST, SOUTH, WEST, POWER });
/*     */   }
/*     */   
/*     */   static enum EnumRedstoneWireConnection implements INamable
/*     */   {
/* 359 */     UP("up"),  SIDE("side"),  NONE("none");
/*     */     
/*     */     private final String d;
/*     */     
/*     */     private EnumRedstoneWireConnection(String s) {
/* 364 */       this.d = s;
/*     */     }
/*     */     
/*     */     public String toString() {
/* 368 */       return getName();
/*     */     }
/*     */     
/*     */     public String getName() {
/* 372 */       return this.d;
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_8_R3\BlockRedstoneWire.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */
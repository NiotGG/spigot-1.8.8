/*     */ package net.minecraft.server.v1_8_R3;
/*     */ 
/*     */ import java.util.Random;
/*     */ import org.bukkit.craftbukkit.v1_8_R3.CraftServer;
/*     */ import org.bukkit.event.block.BlockRedstoneEvent;
/*     */ import org.bukkit.plugin.PluginManager;
/*     */ 
/*     */ public class BlockDoor extends Block
/*     */ {
/*  10 */   public static final BlockStateDirection FACING = BlockStateDirection.of("facing", EnumDirection.EnumDirectionLimit.HORIZONTAL);
/*  11 */   public static final BlockStateBoolean OPEN = BlockStateBoolean.of("open");
/*  12 */   public static final BlockStateEnum<EnumDoorHinge> HINGE = BlockStateEnum.of("hinge", EnumDoorHinge.class);
/*  13 */   public static final BlockStateBoolean POWERED = BlockStateBoolean.of("powered");
/*  14 */   public static final BlockStateEnum<EnumDoorHalf> HALF = BlockStateEnum.of("half", EnumDoorHalf.class);
/*     */   
/*     */   protected BlockDoor(Material material) {
/*  17 */     super(material);
/*  18 */     j(this.blockStateList.getBlockData().set(FACING, EnumDirection.NORTH).set(OPEN, Boolean.valueOf(false)).set(HINGE, EnumDoorHinge.LEFT).set(POWERED, Boolean.valueOf(false)).set(HALF, EnumDoorHalf.LOWER));
/*     */   }
/*     */   
/*     */   public String getName() {
/*  22 */     return LocaleI18n.get((a() + ".name").replaceAll("tile", "item"));
/*     */   }
/*     */   
/*     */   public boolean c() {
/*  26 */     return false;
/*     */   }
/*     */   
/*     */   public boolean b(IBlockAccess iblockaccess, BlockPosition blockposition) {
/*  30 */     return g(e(iblockaccess, blockposition));
/*     */   }
/*     */   
/*     */   public boolean d() {
/*  34 */     return false;
/*     */   }
/*     */   
/*     */   public AxisAlignedBB a(World world, BlockPosition blockposition, IBlockData iblockdata) {
/*  38 */     updateShape(world, blockposition);
/*  39 */     return super.a(world, blockposition, iblockdata);
/*     */   }
/*     */   
/*     */   public void updateShape(IBlockAccess iblockaccess, BlockPosition blockposition) {
/*  43 */     k(e(iblockaccess, blockposition));
/*     */   }
/*     */   
/*     */   private void k(int i) {
/*  47 */     float f = 0.1875F;
/*     */     
/*  49 */     a(0.0F, 0.0F, 0.0F, 1.0F, 2.0F, 1.0F);
/*  50 */     EnumDirection enumdirection = f(i);
/*  51 */     boolean flag = g(i);
/*  52 */     boolean flag1 = j(i);
/*     */     
/*  54 */     if (flag) {
/*  55 */       if (enumdirection == EnumDirection.EAST) {
/*  56 */         if (!flag1) {
/*  57 */           a(0.0F, 0.0F, 0.0F, 1.0F, 1.0F, f);
/*     */         } else {
/*  59 */           a(0.0F, 0.0F, 1.0F - f, 1.0F, 1.0F, 1.0F);
/*     */         }
/*  61 */       } else if (enumdirection == EnumDirection.SOUTH) {
/*  62 */         if (!flag1) {
/*  63 */           a(1.0F - f, 0.0F, 0.0F, 1.0F, 1.0F, 1.0F);
/*     */         } else {
/*  65 */           a(0.0F, 0.0F, 0.0F, f, 1.0F, 1.0F);
/*     */         }
/*  67 */       } else if (enumdirection == EnumDirection.WEST) {
/*  68 */         if (!flag1) {
/*  69 */           a(0.0F, 0.0F, 1.0F - f, 1.0F, 1.0F, 1.0F);
/*     */         } else {
/*  71 */           a(0.0F, 0.0F, 0.0F, 1.0F, 1.0F, f);
/*     */         }
/*  73 */       } else if (enumdirection == EnumDirection.NORTH) {
/*  74 */         if (!flag1) {
/*  75 */           a(0.0F, 0.0F, 0.0F, f, 1.0F, 1.0F);
/*     */         } else {
/*  77 */           a(1.0F - f, 0.0F, 0.0F, 1.0F, 1.0F, 1.0F);
/*     */         }
/*     */       }
/*  80 */     } else if (enumdirection == EnumDirection.EAST) {
/*  81 */       a(0.0F, 0.0F, 0.0F, f, 1.0F, 1.0F);
/*  82 */     } else if (enumdirection == EnumDirection.SOUTH) {
/*  83 */       a(0.0F, 0.0F, 0.0F, 1.0F, 1.0F, f);
/*  84 */     } else if (enumdirection == EnumDirection.WEST) {
/*  85 */       a(1.0F - f, 0.0F, 0.0F, 1.0F, 1.0F, 1.0F);
/*  86 */     } else if (enumdirection == EnumDirection.NORTH) {
/*  87 */       a(0.0F, 0.0F, 1.0F - f, 1.0F, 1.0F, 1.0F);
/*     */     }
/*     */   }
/*     */   
/*     */   public boolean interact(World world, BlockPosition blockposition, IBlockData iblockdata, EntityHuman entityhuman, EnumDirection enumdirection, float f, float f1, float f2)
/*     */   {
/*  93 */     if (this.material == Material.ORE) {
/*  94 */       return true;
/*     */     }
/*  96 */     BlockPosition blockposition1 = iblockdata.get(HALF) == EnumDoorHalf.LOWER ? blockposition : blockposition.down();
/*  97 */     IBlockData iblockdata1 = blockposition.equals(blockposition1) ? iblockdata : world.getType(blockposition1);
/*     */     
/*  99 */     if (iblockdata1.getBlock() != this) {
/* 100 */       return false;
/*     */     }
/* 102 */     iblockdata = iblockdata1.a(OPEN);
/* 103 */     world.setTypeAndData(blockposition1, iblockdata, 2);
/* 104 */     world.b(blockposition1, blockposition);
/* 105 */     world.a(entityhuman, ((Boolean)iblockdata.get(OPEN)).booleanValue() ? 1003 : 1006, blockposition, 0);
/* 106 */     return true;
/*     */   }
/*     */   
/*     */ 
/*     */   public void setDoor(World world, BlockPosition blockposition, boolean flag)
/*     */   {
/* 112 */     IBlockData iblockdata = world.getType(blockposition);
/*     */     
/* 114 */     if (iblockdata.getBlock() == this) {
/* 115 */       BlockPosition blockposition1 = iblockdata.get(HALF) == EnumDoorHalf.LOWER ? blockposition : blockposition.down();
/* 116 */       IBlockData iblockdata1 = blockposition == blockposition1 ? iblockdata : world.getType(blockposition1);
/*     */       
/* 118 */       if ((iblockdata1.getBlock() == this) && (((Boolean)iblockdata1.get(OPEN)).booleanValue() != flag)) {
/* 119 */         world.setTypeAndData(blockposition1, iblockdata1.set(OPEN, Boolean.valueOf(flag)), 2);
/* 120 */         world.b(blockposition1, blockposition);
/* 121 */         world.a(null, flag ? 1003 : 1006, blockposition, 0);
/*     */       }
/*     */     }
/*     */   }
/*     */   
/*     */   public void doPhysics(World world, BlockPosition blockposition, IBlockData iblockdata, Block block)
/*     */   {
/* 128 */     if (iblockdata.get(HALF) == EnumDoorHalf.UPPER) {
/* 129 */       BlockPosition blockposition1 = blockposition.down();
/* 130 */       IBlockData iblockdata1 = world.getType(blockposition1);
/*     */       
/* 132 */       if (iblockdata1.getBlock() != this) {
/* 133 */         world.setAir(blockposition);
/* 134 */       } else if (block != this) {
/* 135 */         doPhysics(world, blockposition1, iblockdata1, block);
/*     */       }
/*     */     } else {
/* 138 */       boolean flag = false;
/* 139 */       BlockPosition blockposition2 = blockposition.up();
/* 140 */       IBlockData iblockdata2 = world.getType(blockposition2);
/*     */       
/* 142 */       if (iblockdata2.getBlock() != this) {
/* 143 */         world.setAir(blockposition);
/* 144 */         flag = true;
/*     */       }
/*     */       
/* 147 */       if (!World.a(world, blockposition.down())) {
/* 148 */         world.setAir(blockposition);
/* 149 */         flag = true;
/* 150 */         if (iblockdata2.getBlock() == this) {
/* 151 */           world.setAir(blockposition2);
/*     */         }
/*     */       }
/*     */       
/* 155 */       if (flag) {
/* 156 */         if (!world.isClientSide) {
/* 157 */           b(world, blockposition, iblockdata, 0);
/*     */         }
/*     */       }
/*     */       else
/*     */       {
/* 162 */         org.bukkit.World bworld = world.getWorld();
/* 163 */         org.bukkit.block.Block bukkitBlock = bworld.getBlockAt(blockposition.getX(), blockposition.getY(), blockposition.getZ());
/* 164 */         org.bukkit.block.Block blockTop = bworld.getBlockAt(blockposition2.getX(), blockposition2.getY(), blockposition2.getZ());
/*     */         
/* 166 */         int power = bukkitBlock.getBlockPower();
/* 167 */         int powerTop = blockTop.getBlockPower();
/* 168 */         if (powerTop > power) power = powerTop;
/* 169 */         int oldPower = ((Boolean)iblockdata2.get(POWERED)).booleanValue() ? 15 : 0;
/*     */         
/* 171 */         if (((oldPower == 0 ? 1 : 0) ^ (power == 0 ? 1 : 0)) != 0) {
/* 172 */           BlockRedstoneEvent eventRedstone = new BlockRedstoneEvent(bukkitBlock, oldPower, power);
/* 173 */           world.getServer().getPluginManager().callEvent(eventRedstone);
/*     */           
/* 175 */           boolean flag1 = eventRedstone.getNewCurrent() > 0;
/* 176 */           world.setTypeAndData(blockposition2, iblockdata2.set(POWERED, Boolean.valueOf(flag1)), 2);
/* 177 */           if (flag1 != ((Boolean)iblockdata.get(OPEN)).booleanValue()) {
/* 178 */             world.setTypeAndData(blockposition, iblockdata.set(OPEN, Boolean.valueOf(flag1)), 2);
/* 179 */             world.b(blockposition, blockposition);
/* 180 */             world.a(null, flag1 ? 1003 : 1006, blockposition, 0);
/*     */           }
/*     */         }
/*     */       }
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */   public Item getDropType(IBlockData iblockdata, Random random, int i)
/*     */   {
/* 190 */     return iblockdata.get(HALF) == EnumDoorHalf.UPPER ? null : l();
/*     */   }
/*     */   
/*     */   public MovingObjectPosition a(World world, BlockPosition blockposition, Vec3D vec3d, Vec3D vec3d1) {
/* 194 */     updateShape(world, blockposition);
/* 195 */     return super.a(world, blockposition, vec3d, vec3d1);
/*     */   }
/*     */   
/*     */   public boolean canPlace(World world, BlockPosition blockposition) {
/* 199 */     return blockposition.getY() < 255;
/*     */   }
/*     */   
/*     */   public int k() {
/* 203 */     return 1;
/*     */   }
/*     */   
/*     */   public static int e(IBlockAccess iblockaccess, BlockPosition blockposition) {
/* 207 */     IBlockData iblockdata = iblockaccess.getType(blockposition);
/* 208 */     int i = iblockdata.getBlock().toLegacyData(iblockdata);
/* 209 */     boolean flag = i(i);
/* 210 */     IBlockData iblockdata1 = iblockaccess.getType(blockposition.down());
/* 211 */     int j = iblockdata1.getBlock().toLegacyData(iblockdata1);
/* 212 */     int k = flag ? j : i;
/* 213 */     IBlockData iblockdata2 = iblockaccess.getType(blockposition.up());
/* 214 */     int l = iblockdata2.getBlock().toLegacyData(iblockdata2);
/* 215 */     int i1 = flag ? i : l;
/* 216 */     boolean flag1 = (i1 & 0x1) != 0;
/* 217 */     boolean flag2 = (i1 & 0x2) != 0;
/*     */     
/* 219 */     return b(k) | (flag ? 8 : 0) | (flag1 ? 16 : 0) | (flag2 ? 32 : 0);
/*     */   }
/*     */   
/*     */   private Item l() {
/* 223 */     return this == Blocks.DARK_OAK_DOOR ? Items.DARK_OAK_DOOR : this == Blocks.ACACIA_DOOR ? Items.ACACIA_DOOR : this == Blocks.JUNGLE_DOOR ? Items.JUNGLE_DOOR : this == Blocks.BIRCH_DOOR ? Items.BIRCH_DOOR : this == Blocks.SPRUCE_DOOR ? Items.SPRUCE_DOOR : this == Blocks.IRON_DOOR ? Items.IRON_DOOR : Items.WOODEN_DOOR;
/*     */   }
/*     */   
/*     */   public void a(World world, BlockPosition blockposition, IBlockData iblockdata, EntityHuman entityhuman) {
/* 227 */     BlockPosition blockposition1 = blockposition.down();
/*     */     
/* 229 */     if ((entityhuman.abilities.canInstantlyBuild) && (iblockdata.get(HALF) == EnumDoorHalf.UPPER) && (world.getType(blockposition1).getBlock() == this)) {
/* 230 */       world.setAir(blockposition1);
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public IBlockData updateState(IBlockData iblockdata, IBlockAccess iblockaccess, BlockPosition blockposition)
/*     */   {
/* 238 */     if (iblockdata.get(HALF) == EnumDoorHalf.LOWER) {
/* 239 */       IBlockData iblockdata1 = iblockaccess.getType(blockposition.up());
/* 240 */       if (iblockdata1.getBlock() == this) {
/* 241 */         iblockdata = iblockdata.set(HINGE, (EnumDoorHinge)iblockdata1.get(HINGE)).set(POWERED, (Boolean)iblockdata1.get(POWERED));
/*     */       }
/*     */     } else {
/* 244 */       IBlockData iblockdata1 = iblockaccess.getType(blockposition.down());
/* 245 */       if (iblockdata1.getBlock() == this) {
/* 246 */         iblockdata = iblockdata.set(FACING, (EnumDirection)iblockdata1.get(FACING)).set(OPEN, (Boolean)iblockdata1.get(OPEN));
/*     */       }
/*     */     }
/*     */     
/* 250 */     return iblockdata;
/*     */   }
/*     */   
/*     */   public IBlockData fromLegacyData(int i) {
/* 254 */     return (i & 0x8) > 0 ? getBlockData().set(HALF, EnumDoorHalf.UPPER).set(HINGE, (i & 0x1) > 0 ? EnumDoorHinge.RIGHT : EnumDoorHinge.LEFT).set(POWERED, Boolean.valueOf((i & 0x2) > 0)) : getBlockData().set(HALF, EnumDoorHalf.LOWER).set(FACING, EnumDirection.fromType2(i & 0x3).f()).set(OPEN, Boolean.valueOf((i & 0x4) > 0));
/*     */   }
/*     */   
/*     */   public int toLegacyData(IBlockData iblockdata) {
/* 258 */     byte b0 = 0;
/*     */     
/*     */     int i;
/* 261 */     if (iblockdata.get(HALF) == EnumDoorHalf.UPPER) {
/* 262 */       int i = b0 | 0x8;
/* 263 */       if (iblockdata.get(HINGE) == EnumDoorHinge.RIGHT) {
/* 264 */         i |= 0x1;
/*     */       }
/*     */       
/* 267 */       if (((Boolean)iblockdata.get(POWERED)).booleanValue()) {
/* 268 */         i |= 0x2;
/*     */       }
/*     */     } else {
/* 271 */       i = b0 | ((EnumDirection)iblockdata.get(FACING)).e().b();
/* 272 */       if (((Boolean)iblockdata.get(OPEN)).booleanValue()) {
/* 273 */         i |= 0x4;
/*     */       }
/*     */     }
/*     */     
/* 277 */     return i;
/*     */   }
/*     */   
/*     */   protected static int b(int i) {
/* 281 */     return i & 0x7;
/*     */   }
/*     */   
/*     */   public static boolean f(IBlockAccess iblockaccess, BlockPosition blockposition) {
/* 285 */     return g(e(iblockaccess, blockposition));
/*     */   }
/*     */   
/*     */   public static EnumDirection h(IBlockAccess iblockaccess, BlockPosition blockposition) {
/* 289 */     return f(e(iblockaccess, blockposition));
/*     */   }
/*     */   
/*     */   public static EnumDirection f(int i) {
/* 293 */     return EnumDirection.fromType2(i & 0x3).f();
/*     */   }
/*     */   
/*     */   protected static boolean g(int i) {
/* 297 */     return (i & 0x4) != 0;
/*     */   }
/*     */   
/*     */   protected static boolean i(int i) {
/* 301 */     return (i & 0x8) != 0;
/*     */   }
/*     */   
/*     */   protected static boolean j(int i) {
/* 305 */     return (i & 0x10) != 0;
/*     */   }
/*     */   
/*     */   protected BlockStateList getStateList() {
/* 309 */     return new BlockStateList(this, new IBlockState[] { HALF, FACING, OPEN, HINGE, POWERED });
/*     */   }
/*     */   
/*     */   public static enum EnumDoorHinge implements INamable
/*     */   {
/* 314 */     LEFT,  RIGHT;
/*     */     
/*     */ 
/*     */     public String toString()
/*     */     {
/* 319 */       return getName();
/*     */     }
/*     */     
/*     */     public String getName() {
/* 323 */       return this == LEFT ? "left" : "right";
/*     */     }
/*     */   }
/*     */   
/*     */   public static enum EnumDoorHalf implements INamable
/*     */   {
/* 329 */     UPPER,  LOWER;
/*     */     
/*     */ 
/*     */     public String toString()
/*     */     {
/* 334 */       return getName();
/*     */     }
/*     */     
/*     */     public String getName() {
/* 338 */       return this == UPPER ? "upper" : "lower";
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_8_R3\BlockDoor.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */
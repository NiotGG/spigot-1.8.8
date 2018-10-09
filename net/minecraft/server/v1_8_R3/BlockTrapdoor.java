/*     */ package net.minecraft.server.v1_8_R3;
/*     */ 
/*     */ import org.bukkit.craftbukkit.v1_8_R3.CraftServer;
/*     */ import org.bukkit.event.block.BlockRedstoneEvent;
/*     */ import org.bukkit.plugin.PluginManager;
/*     */ 
/*     */ public class BlockTrapdoor extends Block
/*     */ {
/*   9 */   public static final BlockStateDirection FACING = BlockStateDirection.of("facing", EnumDirection.EnumDirectionLimit.HORIZONTAL);
/*  10 */   public static final BlockStateBoolean OPEN = BlockStateBoolean.of("open");
/*  11 */   public static final BlockStateEnum<EnumTrapdoorHalf> HALF = BlockStateEnum.of("half", EnumTrapdoorHalf.class);
/*     */   
/*     */   protected BlockTrapdoor(Material material) {
/*  14 */     super(material);
/*  15 */     j(this.blockStateList.getBlockData().set(FACING, EnumDirection.NORTH).set(OPEN, Boolean.valueOf(false)).set(HALF, EnumTrapdoorHalf.BOTTOM));
/*     */     
/*     */ 
/*     */ 
/*  19 */     a(0.0F, 0.0F, 0.0F, 1.0F, 1.0F, 1.0F);
/*  20 */     a(CreativeModeTab.d);
/*     */   }
/*     */   
/*     */   public boolean c() {
/*  24 */     return false;
/*     */   }
/*     */   
/*     */   public boolean d() {
/*  28 */     return false;
/*     */   }
/*     */   
/*     */   public boolean b(IBlockAccess iblockaccess, BlockPosition blockposition) {
/*  32 */     return !((Boolean)iblockaccess.getType(blockposition).get(OPEN)).booleanValue();
/*     */   }
/*     */   
/*     */   public AxisAlignedBB a(World world, BlockPosition blockposition, IBlockData iblockdata) {
/*  36 */     updateShape(world, blockposition);
/*  37 */     return super.a(world, blockposition, iblockdata);
/*     */   }
/*     */   
/*     */   public void updateShape(IBlockAccess iblockaccess, BlockPosition blockposition) {
/*  41 */     d(iblockaccess.getType(blockposition));
/*     */   }
/*     */   
/*     */ 
/*     */   public void j()
/*     */   {
/*  47 */     a(0.0F, 0.40625F, 0.0F, 1.0F, 0.59375F, 1.0F);
/*     */   }
/*     */   
/*     */   public void d(IBlockData iblockdata) {
/*  51 */     if (iblockdata.getBlock() == this) {
/*  52 */       boolean flag = iblockdata.get(HALF) == EnumTrapdoorHalf.TOP;
/*  53 */       Boolean obool = (Boolean)iblockdata.get(OPEN);
/*  54 */       EnumDirection enumdirection = (EnumDirection)iblockdata.get(FACING);
/*     */       
/*     */ 
/*  57 */       if (flag) {
/*  58 */         a(0.0F, 0.8125F, 0.0F, 1.0F, 1.0F, 1.0F);
/*     */       } else {
/*  60 */         a(0.0F, 0.0F, 0.0F, 1.0F, 0.1875F, 1.0F);
/*     */       }
/*     */       
/*  63 */       if (obool.booleanValue()) {
/*  64 */         if (enumdirection == EnumDirection.NORTH) {
/*  65 */           a(0.0F, 0.0F, 0.8125F, 1.0F, 1.0F, 1.0F);
/*     */         }
/*     */         
/*  68 */         if (enumdirection == EnumDirection.SOUTH) {
/*  69 */           a(0.0F, 0.0F, 0.0F, 1.0F, 1.0F, 0.1875F);
/*     */         }
/*     */         
/*  72 */         if (enumdirection == EnumDirection.WEST) {
/*  73 */           a(0.8125F, 0.0F, 0.0F, 1.0F, 1.0F, 1.0F);
/*     */         }
/*     */         
/*  76 */         if (enumdirection == EnumDirection.EAST) {
/*  77 */           a(0.0F, 0.0F, 0.0F, 0.1875F, 1.0F, 1.0F);
/*     */         }
/*     */       }
/*     */     }
/*     */   }
/*     */   
/*     */   public boolean interact(World world, BlockPosition blockposition, IBlockData iblockdata, EntityHuman entityhuman, EnumDirection enumdirection, float f, float f1, float f2)
/*     */   {
/*  85 */     if (this.material == Material.ORE) {
/*  86 */       return true;
/*     */     }
/*  88 */     iblockdata = iblockdata.a(OPEN);
/*  89 */     world.setTypeAndData(blockposition, iblockdata, 2);
/*  90 */     world.a(entityhuman, ((Boolean)iblockdata.get(OPEN)).booleanValue() ? 1003 : 1006, blockposition, 0);
/*  91 */     return true;
/*     */   }
/*     */   
/*     */   public void doPhysics(World world, BlockPosition blockposition, IBlockData iblockdata, Block block)
/*     */   {
/*  96 */     if (!world.isClientSide) {
/*  97 */       BlockPosition blockposition1 = blockposition.shift(((EnumDirection)iblockdata.get(FACING)).opposite());
/*     */       
/*  99 */       if (!c(world.getType(blockposition1).getBlock())) {
/* 100 */         world.setAir(blockposition);
/* 101 */         b(world, blockposition, iblockdata, 0);
/*     */       } else {
/* 103 */         boolean flag = world.isBlockIndirectlyPowered(blockposition);
/*     */         
/* 105 */         if ((flag) || (block.isPowerSource()))
/*     */         {
/* 107 */           org.bukkit.World bworld = world.getWorld();
/* 108 */           org.bukkit.block.Block bblock = bworld.getBlockAt(blockposition.getX(), blockposition.getY(), blockposition.getZ());
/*     */           
/* 110 */           int power = bblock.getBlockPower();
/* 111 */           int oldPower = ((Boolean)iblockdata.get(OPEN)).booleanValue() ? 15 : 0;
/*     */           
/* 113 */           if ((((oldPower == 0 ? 1 : 0) ^ (power == 0 ? 1 : 0)) != 0) || (block.isPowerSource())) {
/* 114 */             BlockRedstoneEvent eventRedstone = new BlockRedstoneEvent(bblock, oldPower, power);
/* 115 */             world.getServer().getPluginManager().callEvent(eventRedstone);
/* 116 */             flag = eventRedstone.getNewCurrent() > 0;
/*     */           }
/*     */           
/* 119 */           boolean flag1 = ((Boolean)iblockdata.get(OPEN)).booleanValue();
/*     */           
/* 121 */           if (flag1 != flag) {
/* 122 */             world.setTypeAndData(blockposition, iblockdata.set(OPEN, Boolean.valueOf(flag)), 2);
/* 123 */             world.a(null, flag ? 1003 : 1006, blockposition, 0);
/*     */           }
/*     */         }
/*     */       }
/*     */     }
/*     */   }
/*     */   
/*     */   public MovingObjectPosition a(World world, BlockPosition blockposition, Vec3D vec3d, Vec3D vec3d1)
/*     */   {
/* 132 */     updateShape(world, blockposition);
/* 133 */     return super.a(world, blockposition, vec3d, vec3d1);
/*     */   }
/*     */   
/*     */   public IBlockData getPlacedState(World world, BlockPosition blockposition, EnumDirection enumdirection, float f, float f1, float f2, int i, EntityLiving entityliving) {
/* 137 */     IBlockData iblockdata = getBlockData();
/*     */     
/* 139 */     if (enumdirection.k().c()) {
/* 140 */       iblockdata = iblockdata.set(FACING, enumdirection).set(OPEN, Boolean.valueOf(false));
/* 141 */       iblockdata = iblockdata.set(HALF, f1 > 0.5F ? EnumTrapdoorHalf.TOP : EnumTrapdoorHalf.BOTTOM);
/*     */     }
/*     */     
/* 144 */     return iblockdata;
/*     */   }
/*     */   
/*     */   public boolean canPlace(World world, BlockPosition blockposition, EnumDirection enumdirection) {
/* 148 */     return (!enumdirection.k().b()) && (c(world.getType(blockposition.shift(enumdirection.opposite())).getBlock()));
/*     */   }
/*     */   
/*     */   protected static EnumDirection b(int i) {
/* 152 */     switch (i & 0x3) {
/*     */     case 0: 
/* 154 */       return EnumDirection.NORTH;
/*     */     
/*     */     case 1: 
/* 157 */       return EnumDirection.SOUTH;
/*     */     
/*     */     case 2: 
/* 160 */       return EnumDirection.WEST;
/*     */     }
/*     */     
/*     */     
/* 164 */     return EnumDirection.EAST;
/*     */   }
/*     */   
/*     */   protected static int a(EnumDirection enumdirection)
/*     */   {
/* 169 */     switch (SyntheticClass_1.a[enumdirection.ordinal()]) {
/*     */     case 1: 
/* 171 */       return 0;
/*     */     
/*     */     case 2: 
/* 174 */       return 1;
/*     */     
/*     */     case 3: 
/* 177 */       return 2;
/*     */     }
/*     */     
/*     */     
/* 181 */     return 3;
/*     */   }
/*     */   
/*     */   private static boolean c(Block block)
/*     */   {
/* 186 */     return ((block.material.k()) && (block.d())) || (block == Blocks.GLOWSTONE) || ((block instanceof BlockStepAbstract)) || ((block instanceof BlockStairs));
/*     */   }
/*     */   
/*     */   public IBlockData fromLegacyData(int i) {
/* 190 */     return getBlockData().set(FACING, b(i)).set(OPEN, Boolean.valueOf((i & 0x4) != 0)).set(HALF, (i & 0x8) == 0 ? EnumTrapdoorHalf.BOTTOM : EnumTrapdoorHalf.TOP);
/*     */   }
/*     */   
/*     */   public int toLegacyData(IBlockData iblockdata) {
/* 194 */     byte b0 = 0;
/* 195 */     int i = b0 | a((EnumDirection)iblockdata.get(FACING));
/*     */     
/* 197 */     if (((Boolean)iblockdata.get(OPEN)).booleanValue()) {
/* 198 */       i |= 0x4;
/*     */     }
/*     */     
/* 201 */     if (iblockdata.get(HALF) == EnumTrapdoorHalf.TOP) {
/* 202 */       i |= 0x8;
/*     */     }
/*     */     
/* 205 */     return i;
/*     */   }
/*     */   
/*     */   protected BlockStateList getStateList() {
/* 209 */     return new BlockStateList(this, new IBlockState[] { FACING, OPEN, HALF });
/*     */   }
/*     */   
/*     */   static class SyntheticClass_1
/*     */   {
/* 214 */     static final int[] a = new int[EnumDirection.values().length];
/*     */     
/*     */     static {
/*     */       try {
/* 218 */         a[EnumDirection.NORTH.ordinal()] = 1;
/*     */       }
/*     */       catch (NoSuchFieldError localNoSuchFieldError1) {}
/*     */       
/*     */       try
/*     */       {
/* 224 */         a[EnumDirection.SOUTH.ordinal()] = 2;
/*     */       }
/*     */       catch (NoSuchFieldError localNoSuchFieldError2) {}
/*     */       
/*     */       try
/*     */       {
/* 230 */         a[EnumDirection.WEST.ordinal()] = 3;
/*     */       }
/*     */       catch (NoSuchFieldError localNoSuchFieldError3) {}
/*     */       
/*     */       try
/*     */       {
/* 236 */         a[EnumDirection.EAST.ordinal()] = 4;
/*     */       }
/*     */       catch (NoSuchFieldError localNoSuchFieldError4) {}
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */   public static enum EnumTrapdoorHalf
/*     */     implements INamable
/*     */   {
/* 246 */     TOP("top"),  BOTTOM("bottom");
/*     */     
/*     */     private final String c;
/*     */     
/*     */     private EnumTrapdoorHalf(String s) {
/* 251 */       this.c = s;
/*     */     }
/*     */     
/*     */     public String toString() {
/* 255 */       return this.c;
/*     */     }
/*     */     
/*     */     public String getName() {
/* 259 */       return this.c;
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_8_R3\BlockTrapdoor.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */
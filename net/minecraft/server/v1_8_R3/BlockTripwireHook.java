/*     */ package net.minecraft.server.v1_8_R3;
/*     */ 
/*     */ import java.util.Iterator;
/*     */ import java.util.Random;
/*     */ import org.bukkit.craftbukkit.v1_8_R3.CraftServer;
/*     */ import org.bukkit.craftbukkit.v1_8_R3.CraftWorld;
/*     */ import org.bukkit.event.block.BlockRedstoneEvent;
/*     */ import org.bukkit.plugin.PluginManager;
/*     */ 
/*     */ public class BlockTripwireHook extends Block
/*     */ {
/*  12 */   public static final BlockStateDirection FACING = BlockStateDirection.of("facing", EnumDirection.EnumDirectionLimit.HORIZONTAL);
/*  13 */   public static final BlockStateBoolean POWERED = BlockStateBoolean.of("powered");
/*  14 */   public static final BlockStateBoolean ATTACHED = BlockStateBoolean.of("attached");
/*  15 */   public static final BlockStateBoolean SUSPENDED = BlockStateBoolean.of("suspended");
/*     */   
/*     */   public BlockTripwireHook() {
/*  18 */     super(Material.ORIENTABLE);
/*  19 */     j(this.blockStateList.getBlockData().set(FACING, EnumDirection.NORTH).set(POWERED, Boolean.valueOf(false)).set(ATTACHED, Boolean.valueOf(false)).set(SUSPENDED, Boolean.valueOf(false)));
/*  20 */     a(CreativeModeTab.d);
/*  21 */     a(true);
/*     */   }
/*     */   
/*     */   public IBlockData updateState(IBlockData iblockdata, IBlockAccess iblockaccess, BlockPosition blockposition) {
/*  25 */     return iblockdata.set(SUSPENDED, Boolean.valueOf(!World.a(iblockaccess, blockposition.down())));
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
/*     */   public boolean d() {
/*  37 */     return false;
/*     */   }
/*     */   
/*     */   public boolean canPlace(World world, BlockPosition blockposition, EnumDirection enumdirection) {
/*  41 */     return (enumdirection.k().c()) && (world.getType(blockposition.shift(enumdirection.opposite())).getBlock().isOccluding());
/*     */   }
/*     */   
/*     */   public boolean canPlace(World world, BlockPosition blockposition) {
/*  45 */     Iterator iterator = EnumDirection.EnumDirectionLimit.HORIZONTAL.iterator();
/*     */     
/*     */     EnumDirection enumdirection;
/*     */     do
/*     */     {
/*  50 */       if (!iterator.hasNext()) {
/*  51 */         return false;
/*     */       }
/*     */       
/*  54 */       enumdirection = (EnumDirection)iterator.next();
/*  55 */     } while (!world.getType(blockposition.shift(enumdirection)).getBlock().isOccluding());
/*     */     
/*  57 */     return true;
/*     */   }
/*     */   
/*     */   public IBlockData getPlacedState(World world, BlockPosition blockposition, EnumDirection enumdirection, float f, float f1, float f2, int i, EntityLiving entityliving) {
/*  61 */     IBlockData iblockdata = getBlockData().set(POWERED, Boolean.valueOf(false)).set(ATTACHED, Boolean.valueOf(false)).set(SUSPENDED, Boolean.valueOf(false));
/*     */     
/*  63 */     if (enumdirection.k().c()) {
/*  64 */       iblockdata = iblockdata.set(FACING, enumdirection);
/*     */     }
/*     */     
/*  67 */     return iblockdata;
/*     */   }
/*     */   
/*     */   public void postPlace(World world, BlockPosition blockposition, IBlockData iblockdata, EntityLiving entityliving, ItemStack itemstack) {
/*  71 */     a(world, blockposition, iblockdata, false, false, -1, null);
/*     */   }
/*     */   
/*     */   public void doPhysics(World world, BlockPosition blockposition, IBlockData iblockdata, Block block) {
/*  75 */     if ((block != this) && 
/*  76 */       (e(world, blockposition, iblockdata))) {
/*  77 */       EnumDirection enumdirection = (EnumDirection)iblockdata.get(FACING);
/*     */       
/*  79 */       if (!world.getType(blockposition.shift(enumdirection.opposite())).getBlock().isOccluding()) {
/*  80 */         b(world, blockposition, iblockdata, 0);
/*  81 */         world.setAir(blockposition);
/*     */       }
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */   public void a(World world, BlockPosition blockposition, IBlockData iblockdata, boolean flag, boolean flag1, int i, IBlockData iblockdata1)
/*     */   {
/*  89 */     EnumDirection enumdirection = (EnumDirection)iblockdata.get(FACING);
/*  90 */     boolean flag2 = ((Boolean)iblockdata.get(ATTACHED)).booleanValue();
/*  91 */     boolean flag3 = ((Boolean)iblockdata.get(POWERED)).booleanValue();
/*  92 */     boolean flag4 = !World.a(world, blockposition.down());
/*  93 */     boolean flag5 = !flag;
/*  94 */     boolean flag6 = false;
/*  95 */     int j = 0;
/*  96 */     IBlockData[] aiblockdata = new IBlockData[42];
/*     */     
/*     */ 
/*     */ 
/* 100 */     for (int k = 1; k < 42; k++) {
/* 101 */       BlockPosition blockposition1 = blockposition.shift(enumdirection, k);
/* 102 */       IBlockData iblockdata2 = world.getType(blockposition1);
/*     */       
/* 104 */       if (iblockdata2.getBlock() == Blocks.TRIPWIRE_HOOK) {
/* 105 */         if (iblockdata2.get(FACING) != enumdirection.opposite()) break;
/* 106 */         j = k;
/*     */         
/* 108 */         break;
/*     */       }
/*     */       
/* 111 */       if ((iblockdata2.getBlock() != Blocks.TRIPWIRE) && (k != i)) {
/* 112 */         aiblockdata[k] = null;
/* 113 */         flag5 = false;
/*     */       } else {
/* 115 */         if (k == i) {
/* 116 */           iblockdata2 = (IBlockData)com.google.common.base.Objects.firstNonNull(iblockdata1, iblockdata2);
/*     */         }
/*     */         
/* 119 */         boolean flag7 = !((Boolean)iblockdata2.get(BlockTripwire.DISARMED)).booleanValue();
/* 120 */         boolean flag8 = ((Boolean)iblockdata2.get(BlockTripwire.POWERED)).booleanValue();
/* 121 */         boolean flag9 = ((Boolean)iblockdata2.get(BlockTripwire.SUSPENDED)).booleanValue();
/*     */         
/* 123 */         flag5 &= flag9 == flag4;
/* 124 */         flag6 |= ((flag7) && (flag8));
/* 125 */         aiblockdata[k] = iblockdata2;
/* 126 */         if (k == i) {
/* 127 */           world.a(blockposition, this, a(world));
/* 128 */           flag5 &= flag7;
/*     */         }
/*     */       }
/*     */     }
/*     */     
/* 133 */     flag5 &= j > 1;
/* 134 */     flag6 &= flag5;
/* 135 */     IBlockData iblockdata3 = getBlockData().set(ATTACHED, Boolean.valueOf(flag5)).set(POWERED, Boolean.valueOf(flag6));
/*     */     
/* 137 */     if (j > 0) {
/* 138 */       BlockPosition blockposition1 = blockposition.shift(enumdirection, j);
/* 139 */       EnumDirection enumdirection1 = enumdirection.opposite();
/*     */       
/* 141 */       world.setTypeAndData(blockposition1, iblockdata3.set(FACING, enumdirection1), 3);
/* 142 */       a(world, blockposition1, enumdirection1);
/* 143 */       a(world, blockposition1, flag5, flag6, flag2, flag3);
/*     */     }
/*     */     
/*     */ 
/* 147 */     org.bukkit.block.Block block = world.getWorld().getBlockAt(blockposition.getX(), blockposition.getY(), blockposition.getZ());
/*     */     
/* 149 */     BlockRedstoneEvent eventRedstone = new BlockRedstoneEvent(block, 15, 0);
/* 150 */     world.getServer().getPluginManager().callEvent(eventRedstone);
/*     */     
/* 152 */     if (eventRedstone.getNewCurrent() > 0) {
/* 153 */       return;
/*     */     }
/*     */     
/*     */ 
/* 157 */     a(world, blockposition, flag5, flag6, flag2, flag3);
/* 158 */     if (!flag) {
/* 159 */       world.setTypeAndData(blockposition, iblockdata3.set(FACING, enumdirection), 3);
/* 160 */       if (flag1) {
/* 161 */         a(world, blockposition, enumdirection);
/*     */       }
/*     */     }
/*     */     
/* 165 */     if (flag2 != flag5) {
/* 166 */       for (int l = 1; l < j; l++) {
/* 167 */         BlockPosition blockposition2 = blockposition.shift(enumdirection, l);
/* 168 */         IBlockData iblockdata4 = aiblockdata[l];
/*     */         
/* 170 */         if ((iblockdata4 != null) && (world.getType(blockposition2).getBlock() != Blocks.AIR)) {
/* 171 */           world.setTypeAndData(blockposition2, iblockdata4.set(ATTACHED, Boolean.valueOf(flag5)), 3);
/*     */         }
/*     */       }
/*     */     }
/*     */   }
/*     */   
/*     */   public void a(World world, BlockPosition blockposition, IBlockData iblockdata, Random random) {}
/*     */   
/*     */   public void b(World world, BlockPosition blockposition, IBlockData iblockdata, Random random)
/*     */   {
/* 181 */     a(world, blockposition, iblockdata, false, true, -1, null);
/*     */   }
/*     */   
/*     */   private void a(World world, BlockPosition blockposition, boolean flag, boolean flag1, boolean flag2, boolean flag3) {
/* 185 */     if ((flag1) && (!flag3)) {
/* 186 */       world.makeSound(blockposition.getX() + 0.5D, blockposition.getY() + 0.1D, blockposition.getZ() + 0.5D, "random.click", 0.4F, 0.6F);
/* 187 */     } else if ((!flag1) && (flag3)) {
/* 188 */       world.makeSound(blockposition.getX() + 0.5D, blockposition.getY() + 0.1D, blockposition.getZ() + 0.5D, "random.click", 0.4F, 0.5F);
/* 189 */     } else if ((flag) && (!flag2)) {
/* 190 */       world.makeSound(blockposition.getX() + 0.5D, blockposition.getY() + 0.1D, blockposition.getZ() + 0.5D, "random.click", 0.4F, 0.7F);
/* 191 */     } else if ((!flag) && (flag2)) {
/* 192 */       world.makeSound(blockposition.getX() + 0.5D, blockposition.getY() + 0.1D, blockposition.getZ() + 0.5D, "random.bowhit", 0.4F, 1.2F / (world.random.nextFloat() * 0.2F + 0.9F));
/*     */     }
/*     */   }
/*     */   
/*     */   private void a(World world, BlockPosition blockposition, EnumDirection enumdirection)
/*     */   {
/* 198 */     world.applyPhysics(blockposition, this);
/* 199 */     world.applyPhysics(blockposition.shift(enumdirection.opposite()), this);
/*     */   }
/*     */   
/*     */   private boolean e(World world, BlockPosition blockposition, IBlockData iblockdata) {
/* 203 */     if (!canPlace(world, blockposition)) {
/* 204 */       b(world, blockposition, iblockdata, 0);
/* 205 */       world.setAir(blockposition);
/* 206 */       return false;
/*     */     }
/* 208 */     return true;
/*     */   }
/*     */   
/*     */   public void updateShape(IBlockAccess iblockaccess, BlockPosition blockposition)
/*     */   {
/* 213 */     float f = 0.1875F;
/*     */     
/* 215 */     switch (SyntheticClass_1.a[((EnumDirection)iblockaccess.getType(blockposition).get(FACING)).ordinal()]) {
/*     */     case 1: 
/* 217 */       a(0.0F, 0.2F, 0.5F - f, f * 2.0F, 0.8F, 0.5F + f);
/* 218 */       break;
/*     */     
/*     */     case 2: 
/* 221 */       a(1.0F - f * 2.0F, 0.2F, 0.5F - f, 1.0F, 0.8F, 0.5F + f);
/* 222 */       break;
/*     */     
/*     */     case 3: 
/* 225 */       a(0.5F - f, 0.2F, 0.0F, 0.5F + f, 0.8F, f * 2.0F);
/* 226 */       break;
/*     */     
/*     */     case 4: 
/* 229 */       a(0.5F - f, 0.2F, 1.0F - f * 2.0F, 0.5F + f, 0.8F, 1.0F);
/*     */     }
/*     */   }
/*     */   
/*     */   public void remove(World world, BlockPosition blockposition, IBlockData iblockdata)
/*     */   {
/* 235 */     boolean flag = ((Boolean)iblockdata.get(ATTACHED)).booleanValue();
/* 236 */     boolean flag1 = ((Boolean)iblockdata.get(POWERED)).booleanValue();
/*     */     
/* 238 */     if ((flag) || (flag1)) {
/* 239 */       a(world, blockposition, iblockdata, true, false, -1, null);
/*     */     }
/*     */     
/* 242 */     if (flag1) {
/* 243 */       world.applyPhysics(blockposition, this);
/* 244 */       world.applyPhysics(blockposition.shift(((EnumDirection)iblockdata.get(FACING)).opposite()), this);
/*     */     }
/*     */     
/* 247 */     super.remove(world, blockposition, iblockdata);
/*     */   }
/*     */   
/*     */   public int a(IBlockAccess iblockaccess, BlockPosition blockposition, IBlockData iblockdata, EnumDirection enumdirection) {
/* 251 */     return ((Boolean)iblockdata.get(POWERED)).booleanValue() ? 15 : 0;
/*     */   }
/*     */   
/*     */   public int b(IBlockAccess iblockaccess, BlockPosition blockposition, IBlockData iblockdata, EnumDirection enumdirection) {
/* 255 */     return iblockdata.get(FACING) == enumdirection ? 15 : !((Boolean)iblockdata.get(POWERED)).booleanValue() ? 0 : 0;
/*     */   }
/*     */   
/*     */   public boolean isPowerSource() {
/* 259 */     return true;
/*     */   }
/*     */   
/*     */   public IBlockData fromLegacyData(int i) {
/* 263 */     return getBlockData().set(FACING, EnumDirection.fromType2(i & 0x3)).set(POWERED, Boolean.valueOf((i & 0x8) > 0)).set(ATTACHED, Boolean.valueOf((i & 0x4) > 0));
/*     */   }
/*     */   
/*     */   public int toLegacyData(IBlockData iblockdata) {
/* 267 */     byte b0 = 0;
/* 268 */     int i = b0 | ((EnumDirection)iblockdata.get(FACING)).b();
/*     */     
/* 270 */     if (((Boolean)iblockdata.get(POWERED)).booleanValue()) {
/* 271 */       i |= 0x8;
/*     */     }
/*     */     
/* 274 */     if (((Boolean)iblockdata.get(ATTACHED)).booleanValue()) {
/* 275 */       i |= 0x4;
/*     */     }
/*     */     
/* 278 */     return i;
/*     */   }
/*     */   
/*     */   protected BlockStateList getStateList() {
/* 282 */     return new BlockStateList(this, new IBlockState[] { FACING, POWERED, ATTACHED, SUSPENDED });
/*     */   }
/*     */   
/*     */   static class SyntheticClass_1
/*     */   {
/* 287 */     static final int[] a = new int[EnumDirection.values().length];
/*     */     
/*     */     static {
/*     */       try {
/* 291 */         a[EnumDirection.EAST.ordinal()] = 1;
/*     */       }
/*     */       catch (NoSuchFieldError localNoSuchFieldError1) {}
/*     */       
/*     */       try
/*     */       {
/* 297 */         a[EnumDirection.WEST.ordinal()] = 2;
/*     */       }
/*     */       catch (NoSuchFieldError localNoSuchFieldError2) {}
/*     */       
/*     */       try
/*     */       {
/* 303 */         a[EnumDirection.SOUTH.ordinal()] = 3;
/*     */       }
/*     */       catch (NoSuchFieldError localNoSuchFieldError3) {}
/*     */       
/*     */       try
/*     */       {
/* 309 */         a[EnumDirection.NORTH.ordinal()] = 4;
/*     */       }
/*     */       catch (NoSuchFieldError localNoSuchFieldError4) {}
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_8_R3\BlockTripwireHook.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */
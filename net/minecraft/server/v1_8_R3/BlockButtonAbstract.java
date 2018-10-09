/*     */ package net.minecraft.server.v1_8_R3;
/*     */ 
/*     */ import java.util.List;
/*     */ import java.util.Random;
/*     */ import org.bukkit.craftbukkit.v1_8_R3.CraftServer;
/*     */ import org.bukkit.craftbukkit.v1_8_R3.CraftWorld;
/*     */ import org.bukkit.event.block.BlockRedstoneEvent;
/*     */ import org.bukkit.event.entity.EntityInteractEvent;
/*     */ import org.bukkit.plugin.PluginManager;
/*     */ 
/*     */ public abstract class BlockButtonAbstract extends Block
/*     */ {
/*  13 */   public static final BlockStateDirection FACING = BlockStateDirection.of("facing");
/*  14 */   public static final BlockStateBoolean POWERED = BlockStateBoolean.of("powered");
/*     */   private final boolean N;
/*     */   
/*     */   protected BlockButtonAbstract(boolean flag) {
/*  18 */     super(Material.ORIENTABLE);
/*  19 */     j(this.blockStateList.getBlockData().set(FACING, EnumDirection.NORTH).set(POWERED, Boolean.valueOf(false)));
/*  20 */     a(true);
/*  21 */     a(CreativeModeTab.d);
/*  22 */     this.N = flag;
/*     */   }
/*     */   
/*     */   public AxisAlignedBB a(World world, BlockPosition blockposition, IBlockData iblockdata) {
/*  26 */     return null;
/*     */   }
/*     */   
/*     */   public int a(World world) {
/*  30 */     return this.N ? 30 : 20;
/*     */   }
/*     */   
/*     */   public boolean c() {
/*  34 */     return false;
/*     */   }
/*     */   
/*     */   public boolean d() {
/*  38 */     return false;
/*     */   }
/*     */   
/*     */   public boolean canPlace(World world, BlockPosition blockposition, EnumDirection enumdirection) {
/*  42 */     return a(world, blockposition, enumdirection.opposite());
/*     */   }
/*     */   
/*     */   public boolean canPlace(World world, BlockPosition blockposition) {
/*  46 */     EnumDirection[] aenumdirection = EnumDirection.values();
/*  47 */     int i = aenumdirection.length;
/*     */     
/*  49 */     for (int j = 0; j < i; j++) {
/*  50 */       EnumDirection enumdirection = aenumdirection[j];
/*     */       
/*  52 */       if (a(world, blockposition, enumdirection)) {
/*  53 */         return true;
/*     */       }
/*     */     }
/*     */     
/*  57 */     return false;
/*     */   }
/*     */   
/*     */   protected static boolean a(World world, BlockPosition blockposition, EnumDirection enumdirection) {
/*  61 */     BlockPosition blockposition1 = blockposition.shift(enumdirection);
/*     */     
/*  63 */     return enumdirection == EnumDirection.DOWN ? World.a(world, blockposition1) : world.getType(blockposition1).getBlock().isOccluding();
/*     */   }
/*     */   
/*     */   public IBlockData getPlacedState(World world, BlockPosition blockposition, EnumDirection enumdirection, float f, float f1, float f2, int i, EntityLiving entityliving) {
/*  67 */     return a(world, blockposition, enumdirection.opposite()) ? getBlockData().set(FACING, enumdirection).set(POWERED, Boolean.valueOf(false)) : getBlockData().set(FACING, EnumDirection.DOWN).set(POWERED, Boolean.valueOf(false));
/*     */   }
/*     */   
/*     */   public void doPhysics(World world, BlockPosition blockposition, IBlockData iblockdata, Block block) {
/*  71 */     if ((e(world, blockposition, iblockdata)) && (!a(world, blockposition, ((EnumDirection)iblockdata.get(FACING)).opposite()))) {
/*  72 */       b(world, blockposition, iblockdata, 0);
/*  73 */       world.setAir(blockposition);
/*     */     }
/*     */   }
/*     */   
/*     */   private boolean e(World world, BlockPosition blockposition, IBlockData iblockdata)
/*     */   {
/*  79 */     if (canPlace(world, blockposition)) {
/*  80 */       return true;
/*     */     }
/*  82 */     b(world, blockposition, iblockdata, 0);
/*  83 */     world.setAir(blockposition);
/*  84 */     return false;
/*     */   }
/*     */   
/*     */   public void updateShape(IBlockAccess iblockaccess, BlockPosition blockposition)
/*     */   {
/*  89 */     d(iblockaccess.getType(blockposition));
/*     */   }
/*     */   
/*     */   private void d(IBlockData iblockdata) {
/*  93 */     EnumDirection enumdirection = (EnumDirection)iblockdata.get(FACING);
/*  94 */     boolean flag = ((Boolean)iblockdata.get(POWERED)).booleanValue();
/*     */     
/*     */ 
/*  97 */     float f2 = (flag ? 1 : 2) / 16.0F;
/*     */     
/*     */ 
/*     */ 
/* 101 */     switch (SyntheticClass_1.a[enumdirection.ordinal()]) {
/*     */     case 1: 
/* 103 */       a(0.0F, 0.375F, 0.3125F, f2, 0.625F, 0.6875F);
/* 104 */       break;
/*     */     
/*     */     case 2: 
/* 107 */       a(1.0F - f2, 0.375F, 0.3125F, 1.0F, 0.625F, 0.6875F);
/* 108 */       break;
/*     */     
/*     */     case 3: 
/* 111 */       a(0.3125F, 0.375F, 0.0F, 0.6875F, 0.625F, f2);
/* 112 */       break;
/*     */     
/*     */     case 4: 
/* 115 */       a(0.3125F, 0.375F, 1.0F - f2, 0.6875F, 0.625F, 1.0F);
/* 116 */       break;
/*     */     
/*     */     case 5: 
/* 119 */       a(0.3125F, 0.0F, 0.375F, 0.6875F, 0.0F + f2, 0.625F);
/* 120 */       break;
/*     */     
/*     */     case 6: 
/* 123 */       a(0.3125F, 1.0F - f2, 0.375F, 0.6875F, 1.0F, 0.625F);
/*     */     }
/*     */   }
/*     */   
/*     */   public boolean interact(World world, BlockPosition blockposition, IBlockData iblockdata, EntityHuman entityhuman, EnumDirection enumdirection, float f, float f1, float f2)
/*     */   {
/* 129 */     if (((Boolean)iblockdata.get(POWERED)).booleanValue()) {
/* 130 */       return true;
/*     */     }
/*     */     
/* 133 */     boolean powered = ((Boolean)iblockdata.get(POWERED)).booleanValue();
/* 134 */     org.bukkit.block.Block block = world.getWorld().getBlockAt(blockposition.getX(), blockposition.getY(), blockposition.getZ());
/* 135 */     int old = powered ? 15 : 0;
/* 136 */     int current = !powered ? 15 : 0;
/*     */     
/* 138 */     BlockRedstoneEvent eventRedstone = new BlockRedstoneEvent(block, old, current);
/* 139 */     world.getServer().getPluginManager().callEvent(eventRedstone);
/*     */     
/* 141 */     if ((eventRedstone.getNewCurrent() > 0 ? 1 : 0) != (powered ? 0 : 1)) {
/* 142 */       return true;
/*     */     }
/*     */     
/* 145 */     world.setTypeAndData(blockposition, iblockdata.set(POWERED, Boolean.valueOf(true)), 3);
/* 146 */     world.b(blockposition, blockposition);
/* 147 */     world.makeSound(blockposition.getX() + 0.5D, blockposition.getY() + 0.5D, blockposition.getZ() + 0.5D, "random.click", 0.3F, 0.6F);
/* 148 */     c(world, blockposition, (EnumDirection)iblockdata.get(FACING));
/* 149 */     world.a(blockposition, this, a(world));
/* 150 */     return true;
/*     */   }
/*     */   
/*     */   public void remove(World world, BlockPosition blockposition, IBlockData iblockdata)
/*     */   {
/* 155 */     if (((Boolean)iblockdata.get(POWERED)).booleanValue()) {
/* 156 */       c(world, blockposition, (EnumDirection)iblockdata.get(FACING));
/*     */     }
/*     */     
/* 159 */     super.remove(world, blockposition, iblockdata);
/*     */   }
/*     */   
/*     */   public int a(IBlockAccess iblockaccess, BlockPosition blockposition, IBlockData iblockdata, EnumDirection enumdirection) {
/* 163 */     return ((Boolean)iblockdata.get(POWERED)).booleanValue() ? 15 : 0;
/*     */   }
/*     */   
/*     */   public int b(IBlockAccess iblockaccess, BlockPosition blockposition, IBlockData iblockdata, EnumDirection enumdirection) {
/* 167 */     return iblockdata.get(FACING) == enumdirection ? 15 : !((Boolean)iblockdata.get(POWERED)).booleanValue() ? 0 : 0;
/*     */   }
/*     */   
/*     */   public boolean isPowerSource() {
/* 171 */     return true;
/*     */   }
/*     */   
/*     */   public void a(World world, BlockPosition blockposition, IBlockData iblockdata, Random random) {}
/*     */   
/*     */   public void b(World world, BlockPosition blockposition, IBlockData iblockdata, Random random) {
/* 177 */     if ((!world.isClientSide) && 
/* 178 */       (((Boolean)iblockdata.get(POWERED)).booleanValue())) {
/* 179 */       if (this.N) {
/* 180 */         f(world, blockposition, iblockdata);
/*     */       }
/*     */       else {
/* 183 */         org.bukkit.block.Block block = world.getWorld().getBlockAt(blockposition.getX(), blockposition.getY(), blockposition.getZ());
/*     */         
/* 185 */         BlockRedstoneEvent eventRedstone = new BlockRedstoneEvent(block, 15, 0);
/* 186 */         world.getServer().getPluginManager().callEvent(eventRedstone);
/*     */         
/* 188 */         if (eventRedstone.getNewCurrent() > 0) {
/* 189 */           return;
/*     */         }
/*     */         
/* 192 */         world.setTypeUpdate(blockposition, iblockdata.set(POWERED, Boolean.valueOf(false)));
/* 193 */         c(world, blockposition, (EnumDirection)iblockdata.get(FACING));
/* 194 */         world.makeSound(blockposition.getX() + 0.5D, blockposition.getY() + 0.5D, blockposition.getZ() + 0.5D, "random.click", 0.3F, 0.5F);
/* 195 */         world.b(blockposition, blockposition);
/*     */       }
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */   public void j()
/*     */   {
/* 203 */     float f = 0.1875F;
/* 204 */     float f1 = 0.125F;
/* 205 */     float f2 = 0.125F;
/*     */     
/* 207 */     a(0.5F - f, 0.5F - f1, 0.5F - f2, 0.5F + f, 0.5F + f1, 0.5F + f2);
/*     */   }
/*     */   
/*     */   public void a(World world, BlockPosition blockposition, IBlockData iblockdata, Entity entity) {
/* 211 */     if ((!world.isClientSide) && 
/* 212 */       (this.N) && 
/* 213 */       (!((Boolean)iblockdata.get(POWERED)).booleanValue())) {
/* 214 */       f(world, blockposition, iblockdata);
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */   private void f(World world, BlockPosition blockposition, IBlockData iblockdata)
/*     */   {
/* 221 */     d(iblockdata);
/* 222 */     List list = world.a(EntityArrow.class, new AxisAlignedBB(blockposition.getX() + this.minX, blockposition.getY() + this.minY, blockposition.getZ() + this.minZ, blockposition.getX() + this.maxX, blockposition.getY() + this.maxY, blockposition.getZ() + this.maxZ));
/* 223 */     boolean flag = !list.isEmpty();
/* 224 */     boolean flag1 = ((Boolean)iblockdata.get(POWERED)).booleanValue();
/*     */     
/*     */ 
/* 227 */     if ((flag1 != flag) && (flag)) {
/* 228 */       org.bukkit.block.Block block = world.getWorld().getBlockAt(blockposition.getX(), blockposition.getY(), blockposition.getZ());
/* 229 */       boolean allowed = false;
/*     */       
/*     */ 
/* 232 */       for (Object object : list) {
/* 233 */         if (object != null) {
/* 234 */           EntityInteractEvent event = new EntityInteractEvent(((Entity)object).getBukkitEntity(), block);
/* 235 */           world.getServer().getPluginManager().callEvent(event);
/*     */           
/* 237 */           if (!event.isCancelled()) {
/* 238 */             allowed = true;
/* 239 */             break;
/*     */           }
/*     */         }
/*     */       }
/*     */       
/* 244 */       if (!allowed) {
/* 245 */         return;
/*     */       }
/*     */     }
/*     */     
/*     */ 
/* 250 */     if ((flag) && (!flag1))
/*     */     {
/* 252 */       org.bukkit.block.Block block = world.getWorld().getBlockAt(blockposition.getX(), blockposition.getY(), blockposition.getZ());
/*     */       
/* 254 */       BlockRedstoneEvent eventRedstone = new BlockRedstoneEvent(block, 0, 15);
/* 255 */       world.getServer().getPluginManager().callEvent(eventRedstone);
/*     */       
/* 257 */       if (eventRedstone.getNewCurrent() <= 0) {
/* 258 */         return;
/*     */       }
/*     */       
/* 261 */       world.setTypeUpdate(blockposition, iblockdata.set(POWERED, Boolean.valueOf(true)));
/* 262 */       c(world, blockposition, (EnumDirection)iblockdata.get(FACING));
/* 263 */       world.b(blockposition, blockposition);
/* 264 */       world.makeSound(blockposition.getX() + 0.5D, blockposition.getY() + 0.5D, blockposition.getZ() + 0.5D, "random.click", 0.3F, 0.6F);
/*     */     }
/*     */     
/* 267 */     if ((!flag) && (flag1))
/*     */     {
/* 269 */       org.bukkit.block.Block block = world.getWorld().getBlockAt(blockposition.getX(), blockposition.getY(), blockposition.getZ());
/*     */       
/* 271 */       BlockRedstoneEvent eventRedstone = new BlockRedstoneEvent(block, 15, 0);
/* 272 */       world.getServer().getPluginManager().callEvent(eventRedstone);
/*     */       
/* 274 */       if (eventRedstone.getNewCurrent() > 0) {
/* 275 */         return;
/*     */       }
/*     */       
/* 278 */       world.setTypeUpdate(blockposition, iblockdata.set(POWERED, Boolean.valueOf(false)));
/* 279 */       c(world, blockposition, (EnumDirection)iblockdata.get(FACING));
/* 280 */       world.b(blockposition, blockposition);
/* 281 */       world.makeSound(blockposition.getX() + 0.5D, blockposition.getY() + 0.5D, blockposition.getZ() + 0.5D, "random.click", 0.3F, 0.5F);
/*     */     }
/*     */     
/* 284 */     if (flag) {
/* 285 */       world.a(blockposition, this, a(world));
/*     */     }
/*     */   }
/*     */   
/*     */   private void c(World world, BlockPosition blockposition, EnumDirection enumdirection)
/*     */   {
/* 291 */     world.applyPhysics(blockposition, this);
/* 292 */     world.applyPhysics(blockposition.shift(enumdirection.opposite()), this); }
/*     */   
/*     */   public IBlockData fromLegacyData(int i) { EnumDirection enumdirection;
/*     */     EnumDirection enumdirection;
/*     */     EnumDirection enumdirection;
/*     */     EnumDirection enumdirection;
/* 298 */     EnumDirection enumdirection; EnumDirection enumdirection; switch (i & 0x7) {
/*     */     case 0: 
/* 300 */       enumdirection = EnumDirection.DOWN;
/* 301 */       break;
/*     */     
/*     */     case 1: 
/* 304 */       enumdirection = EnumDirection.EAST;
/* 305 */       break;
/*     */     
/*     */     case 2: 
/* 308 */       enumdirection = EnumDirection.WEST;
/* 309 */       break;
/*     */     
/*     */     case 3: 
/* 312 */       enumdirection = EnumDirection.SOUTH;
/* 313 */       break;
/*     */     
/*     */     case 4: 
/* 316 */       enumdirection = EnumDirection.NORTH;
/* 317 */       break;
/*     */     
/*     */     case 5: 
/*     */     default: 
/* 321 */       enumdirection = EnumDirection.UP;
/*     */     }
/*     */     
/* 324 */     return getBlockData().set(FACING, enumdirection).set(POWERED, Boolean.valueOf((i & 0x8) > 0)); }
/*     */   
/*     */   public int toLegacyData(IBlockData iblockdata) { int i;
/*     */     int i;
/*     */     int i;
/*     */     int i;
/* 330 */     int i; int i; switch (SyntheticClass_1.a[((EnumDirection)iblockdata.get(FACING)).ordinal()]) {
/*     */     case 1: 
/* 332 */       i = 1;
/* 333 */       break;
/*     */     
/*     */     case 2: 
/* 336 */       i = 2;
/* 337 */       break;
/*     */     
/*     */     case 3: 
/* 340 */       i = 3;
/* 341 */       break;
/*     */     
/*     */     case 4: 
/* 344 */       i = 4;
/* 345 */       break;
/*     */     
/*     */     case 5: 
/*     */     default: 
/* 349 */       i = 5;
/* 350 */       break;
/*     */     
/*     */     case 6: 
/* 353 */       i = 0;
/*     */     }
/*     */     
/* 356 */     if (((Boolean)iblockdata.get(POWERED)).booleanValue()) {
/* 357 */       i |= 0x8;
/*     */     }
/*     */     
/* 360 */     return i;
/*     */   }
/*     */   
/*     */   protected BlockStateList getStateList() {
/* 364 */     return new BlockStateList(this, new IBlockState[] { FACING, POWERED });
/*     */   }
/*     */   
/*     */   static class SyntheticClass_1
/*     */   {
/* 369 */     static final int[] a = new int[EnumDirection.values().length];
/*     */     
/*     */     static {
/*     */       try {
/* 373 */         a[EnumDirection.EAST.ordinal()] = 1;
/*     */       }
/*     */       catch (NoSuchFieldError localNoSuchFieldError1) {}
/*     */       
/*     */       try
/*     */       {
/* 379 */         a[EnumDirection.WEST.ordinal()] = 2;
/*     */       }
/*     */       catch (NoSuchFieldError localNoSuchFieldError2) {}
/*     */       
/*     */       try
/*     */       {
/* 385 */         a[EnumDirection.SOUTH.ordinal()] = 3;
/*     */       }
/*     */       catch (NoSuchFieldError localNoSuchFieldError3) {}
/*     */       
/*     */       try
/*     */       {
/* 391 */         a[EnumDirection.NORTH.ordinal()] = 4;
/*     */       }
/*     */       catch (NoSuchFieldError localNoSuchFieldError4) {}
/*     */       
/*     */       try
/*     */       {
/* 397 */         a[EnumDirection.UP.ordinal()] = 5;
/*     */       }
/*     */       catch (NoSuchFieldError localNoSuchFieldError5) {}
/*     */       
/*     */       try
/*     */       {
/* 403 */         a[EnumDirection.DOWN.ordinal()] = 6;
/*     */       }
/*     */       catch (NoSuchFieldError localNoSuchFieldError6) {}
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_8_R3\BlockButtonAbstract.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */
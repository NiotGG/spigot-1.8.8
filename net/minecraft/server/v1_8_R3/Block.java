/*      */ package net.minecraft.server.v1_8_R3;
/*      */ 
/*      */ import java.util.Iterator;
/*      */ import java.util.List;
/*      */ import java.util.Random;
/*      */ 
/*      */ public class Block
/*      */ {
/*    9 */   private static final MinecraftKey a = new MinecraftKey("air");
/*   10 */   public static final RegistryBlocks<MinecraftKey, Block> REGISTRY = new RegistryBlocks(a);
/*   11 */   public static final RegistryID<IBlockData> d = new RegistryID();
/*      */   private CreativeModeTab creativeTab;
/*   13 */   public static final StepSound e = new StepSound("stone", 1.0F, 1.0F);
/*   14 */   public static final StepSound f = new StepSound("wood", 1.0F, 1.0F);
/*   15 */   public static final StepSound g = new StepSound("gravel", 1.0F, 1.0F);
/*   16 */   public static final StepSound h = new StepSound("grass", 1.0F, 1.0F);
/*   17 */   public static final StepSound i = new StepSound("stone", 1.0F, 1.0F);
/*   18 */   public static final StepSound j = new StepSound("stone", 1.0F, 1.5F);
/*   19 */   public static final StepSound k = new StepSound("stone", 1.0F, 1.0F) {
/*      */     public String getBreakSound() {
/*   21 */       return "dig.glass";
/*      */     }
/*      */     
/*      */     public String getPlaceSound() {
/*   25 */       return "step.stone";
/*      */     }
/*      */   };
/*   28 */   public static final StepSound l = new StepSound("cloth", 1.0F, 1.0F);
/*   29 */   public static final StepSound m = new StepSound("sand", 1.0F, 1.0F);
/*   30 */   public static final StepSound n = new StepSound("snow", 1.0F, 1.0F);
/*   31 */   public static final StepSound o = new StepSound("ladder", 1.0F, 1.0F) {
/*      */     public String getBreakSound() {
/*   33 */       return "dig.wood";
/*      */     }
/*      */   };
/*   36 */   public static final StepSound p = new StepSound("anvil", 0.3F, 1.0F) {
/*      */     public String getBreakSound() {
/*   38 */       return "dig.stone";
/*      */     }
/*      */     
/*      */     public String getPlaceSound() {
/*   42 */       return "random.anvil_land";
/*      */     }
/*      */   };
/*   45 */   public static final StepSound q = new StepSound("slime", 1.0F, 1.0F) {
/*      */     public String getBreakSound() {
/*   47 */       return "mob.slime.big";
/*      */     }
/*      */     
/*      */     public String getPlaceSound() {
/*   51 */       return "mob.slime.big";
/*      */     }
/*      */     
/*      */     public String getStepSound() {
/*   55 */       return "mob.slime.small";
/*      */     }
/*      */   };
/*      */   protected boolean r;
/*      */   protected int s;
/*      */   protected boolean t;
/*      */   protected int u;
/*      */   protected boolean v;
/*      */   protected float strength;
/*      */   protected float durability;
/*      */   protected boolean y;
/*      */   protected boolean z;
/*      */   protected boolean isTileEntity;
/*      */   protected double minX;
/*      */   protected double minY;
/*      */   protected double minZ;
/*      */   protected double maxX;
/*      */   protected double maxY;
/*      */   protected double maxZ;
/*      */   public StepSound stepSound;
/*      */   public float I;
/*      */   protected final Material material;
/*      */   protected final MaterialMapColor K;
/*      */   public float frictionFactor;
/*      */   protected final BlockStateList blockStateList;
/*      */   private IBlockData blockData;
/*      */   private String name;
/*      */   
/*      */   public static int getId(Block block) {
/*   84 */     return REGISTRY.b(block);
/*      */   }
/*      */   
/*      */   public static int getCombinedId(IBlockData iblockdata) {
/*   88 */     Block block = iblockdata.getBlock();
/*      */     
/*   90 */     return getId(block) + (block.toLegacyData(iblockdata) << 12);
/*      */   }
/*      */   
/*      */   public static Block getById(int i) {
/*   94 */     return (Block)REGISTRY.a(i);
/*      */   }
/*      */   
/*      */   public static IBlockData getByCombinedId(int i) {
/*   98 */     int j = i & 0xFFF;
/*   99 */     int k = i >> 12 & 0xF;
/*      */     
/*  101 */     return getById(j).fromLegacyData(k);
/*      */   }
/*      */   
/*      */   public static Block asBlock(Item item) {
/*  105 */     return (item instanceof ItemBlock) ? ((ItemBlock)item).d() : null;
/*      */   }
/*      */   
/*      */   public static Block getByName(String s) {
/*  109 */     MinecraftKey minecraftkey = new MinecraftKey(s);
/*      */     
/*  111 */     if (REGISTRY.d(minecraftkey)) {
/*  112 */       return (Block)REGISTRY.get(minecraftkey);
/*      */     }
/*      */     try {
/*  115 */       return (Block)REGISTRY.a(Integer.parseInt(s));
/*      */     } catch (NumberFormatException localNumberFormatException) {}
/*  117 */     return null;
/*      */   }
/*      */   
/*      */ 
/*      */   public boolean o()
/*      */   {
/*  123 */     return this.r;
/*      */   }
/*      */   
/*      */   public int p() {
/*  127 */     return this.s;
/*      */   }
/*      */   
/*      */   public int r() {
/*  131 */     return this.u;
/*      */   }
/*      */   
/*      */   public boolean s() {
/*  135 */     return this.v;
/*      */   }
/*      */   
/*      */   public Material getMaterial() {
/*  139 */     return this.material;
/*      */   }
/*      */   
/*      */   public MaterialMapColor g(IBlockData iblockdata) {
/*  143 */     return this.K;
/*      */   }
/*      */   
/*      */   public IBlockData fromLegacyData(int i) {
/*  147 */     return getBlockData();
/*      */   }
/*      */   
/*      */   public int toLegacyData(IBlockData iblockdata) {
/*  151 */     if ((iblockdata != null) && (!iblockdata.a().isEmpty())) {
/*  152 */       throw new IllegalArgumentException("Don't know how to convert " + iblockdata + " back into data...");
/*      */     }
/*  154 */     return 0;
/*      */   }
/*      */   
/*      */   public IBlockData updateState(IBlockData iblockdata, IBlockAccess iblockaccess, BlockPosition blockposition)
/*      */   {
/*  159 */     return iblockdata;
/*      */   }
/*      */   
/*      */   public Block(Material material, MaterialMapColor materialmapcolor) {
/*  163 */     this.y = true;
/*  164 */     this.stepSound = e;
/*  165 */     this.I = 1.0F;
/*  166 */     this.frictionFactor = 0.6F;
/*  167 */     this.material = material;
/*  168 */     this.K = materialmapcolor;
/*  169 */     a(0.0F, 0.0F, 0.0F, 1.0F, 1.0F, 1.0F);
/*  170 */     this.r = c();
/*  171 */     this.s = (c() ? 255 : 0);
/*  172 */     this.t = (!material.blocksLight());
/*  173 */     this.blockStateList = getStateList();
/*  174 */     j(this.blockStateList.getBlockData());
/*      */   }
/*      */   
/*      */   protected Block(Material material) {
/*  178 */     this(material, material.r());
/*      */   }
/*      */   
/*      */   protected Block a(StepSound block_stepsound) {
/*  182 */     this.stepSound = block_stepsound;
/*  183 */     return this;
/*      */   }
/*      */   
/*      */   protected Block e(int i) {
/*  187 */     this.s = i;
/*  188 */     return this;
/*      */   }
/*      */   
/*      */   protected Block a(float f) {
/*  192 */     this.u = ((int)(15.0F * f));
/*  193 */     return this;
/*      */   }
/*      */   
/*      */   protected Block b(float f) {
/*  197 */     this.durability = (f * 3.0F);
/*  198 */     return this;
/*      */   }
/*      */   
/*      */   public boolean u() {
/*  202 */     return (this.material.isSolid()) && (d());
/*      */   }
/*      */   
/*      */   public boolean isOccluding() {
/*  206 */     return (this.material.k()) && (d()) && (!isPowerSource());
/*      */   }
/*      */   
/*      */   public boolean w() {
/*  210 */     return (this.material.isSolid()) && (d());
/*      */   }
/*      */   
/*      */   public boolean d() {
/*  214 */     return true;
/*      */   }
/*      */   
/*      */   public boolean b(IBlockAccess iblockaccess, BlockPosition blockposition) {
/*  218 */     return !this.material.isSolid();
/*      */   }
/*      */   
/*      */   public int b() {
/*  222 */     return 3;
/*      */   }
/*      */   
/*      */   public boolean a(World world, BlockPosition blockposition) {
/*  226 */     return false;
/*      */   }
/*      */   
/*      */   protected Block c(float f) {
/*  230 */     this.strength = f;
/*  231 */     if (this.durability < f * 5.0F) {
/*  232 */       this.durability = (f * 5.0F);
/*      */     }
/*      */     
/*  235 */     return this;
/*      */   }
/*      */   
/*      */   protected Block x() {
/*  239 */     c(-1.0F);
/*  240 */     return this;
/*      */   }
/*      */   
/*      */   public float g(World world, BlockPosition blockposition) {
/*  244 */     return this.strength;
/*      */   }
/*      */   
/*      */   protected Block a(boolean flag) {
/*  248 */     this.z = flag;
/*  249 */     return this;
/*      */   }
/*      */   
/*      */   public boolean isTicking() {
/*  253 */     return this.z;
/*      */   }
/*      */   
/*      */   public boolean isTileEntity() {
/*  257 */     return this.isTileEntity;
/*      */   }
/*      */   
/*      */   protected final void a(float f, float f1, float f2, float f3, float f4, float f5) {
/*  261 */     this.minX = f;
/*  262 */     this.minY = f1;
/*  263 */     this.minZ = f2;
/*  264 */     this.maxX = f3;
/*  265 */     this.maxY = f4;
/*  266 */     this.maxZ = f5;
/*      */   }
/*      */   
/*      */   public boolean b(IBlockAccess iblockaccess, BlockPosition blockposition, EnumDirection enumdirection) {
/*  270 */     return iblockaccess.getType(blockposition).getBlock().getMaterial().isBuildable();
/*      */   }
/*      */   
/*      */   public void a(World world, BlockPosition blockposition, IBlockData iblockdata, AxisAlignedBB axisalignedbb, List<AxisAlignedBB> list, Entity entity) {
/*  274 */     AxisAlignedBB axisalignedbb1 = a(world, blockposition, iblockdata);
/*      */     
/*  276 */     if ((axisalignedbb1 != null) && (axisalignedbb.b(axisalignedbb1))) {
/*  277 */       list.add(axisalignedbb1);
/*      */     }
/*      */   }
/*      */   
/*      */   public AxisAlignedBB a(World world, BlockPosition blockposition, IBlockData iblockdata)
/*      */   {
/*  283 */     return new AxisAlignedBB(blockposition.getX() + this.minX, blockposition.getY() + this.minY, blockposition.getZ() + this.minZ, blockposition.getX() + this.maxX, blockposition.getY() + this.maxY, blockposition.getZ() + this.maxZ);
/*      */   }
/*      */   
/*      */   public boolean c() {
/*  287 */     return true;
/*      */   }
/*      */   
/*      */   public boolean a(IBlockData iblockdata, boolean flag) {
/*  291 */     return A();
/*      */   }
/*      */   
/*      */   public boolean A() {
/*  295 */     return true;
/*      */   }
/*      */   
/*      */   public void a(World world, BlockPosition blockposition, IBlockData iblockdata, Random random) {
/*  299 */     b(world, blockposition, iblockdata, random);
/*      */   }
/*      */   
/*      */   public void b(World world, BlockPosition blockposition, IBlockData iblockdata, Random random) {}
/*      */   
/*      */   public void postBreak(World world, BlockPosition blockposition, IBlockData iblockdata) {}
/*      */   
/*      */   public void doPhysics(World world, BlockPosition blockposition, IBlockData iblockdata, Block block) {}
/*      */   
/*      */   public int a(World world) {
/*  309 */     return 10;
/*      */   }
/*      */   
/*      */   public void onPlace(World world, BlockPosition blockposition, IBlockData iblockdata) {
/*  313 */     org.spigotmc.AsyncCatcher.catchOp("block onPlace");
/*      */   }
/*      */   
/*      */   public void remove(World world, BlockPosition blockposition, IBlockData iblockdata) {
/*  317 */     org.spigotmc.AsyncCatcher.catchOp("block remove");
/*      */   }
/*      */   
/*      */   public int a(Random random) {
/*  321 */     return 1;
/*      */   }
/*      */   
/*      */   public Item getDropType(IBlockData iblockdata, Random random, int i) {
/*  325 */     return Item.getItemOf(this);
/*      */   }
/*      */   
/*      */   public float getDamage(EntityHuman entityhuman, World world, BlockPosition blockposition) {
/*  329 */     float f = g(world, blockposition);
/*      */     
/*  331 */     return !entityhuman.b(this) ? entityhuman.a(this) / f / 100.0F : f < 0.0F ? 0.0F : entityhuman.a(this) / f / 30.0F;
/*      */   }
/*      */   
/*      */   public final void b(World world, BlockPosition blockposition, IBlockData iblockdata, int i) {
/*  335 */     dropNaturally(world, blockposition, iblockdata, 1.0F, i);
/*      */   }
/*      */   
/*      */   public void dropNaturally(World world, BlockPosition blockposition, IBlockData iblockdata, float f, int i) {
/*  339 */     if (!world.isClientSide) {
/*  340 */       int j = getDropCount(i, world.random);
/*      */       
/*  342 */       for (int k = 0; k < j; k++)
/*      */       {
/*  344 */         if (world.random.nextFloat() < f) {
/*  345 */           Item item = getDropType(iblockdata, world.random, i);
/*      */           
/*  347 */           if (item != null) {
/*  348 */             a(world, blockposition, new ItemStack(item, 1, getDropData(iblockdata)));
/*      */           }
/*      */         }
/*      */       }
/*      */     }
/*      */   }
/*      */   
/*      */   public static void a(World world, BlockPosition blockposition, ItemStack itemstack)
/*      */   {
/*  357 */     if ((!world.isClientSide) && (world.getGameRules().getBoolean("doTileDrops"))) {
/*  358 */       float f = 0.5F;
/*  359 */       double d0 = world.random.nextFloat() * f + (1.0F - f) * 0.5D;
/*  360 */       double d1 = world.random.nextFloat() * f + (1.0F - f) * 0.5D;
/*  361 */       double d2 = world.random.nextFloat() * f + (1.0F - f) * 0.5D;
/*  362 */       EntityItem entityitem = new EntityItem(world, blockposition.getX() + d0, blockposition.getY() + d1, blockposition.getZ() + d2, itemstack);
/*      */       
/*  364 */       entityitem.p();
/*  365 */       world.addEntity(entityitem);
/*      */     }
/*      */   }
/*      */   
/*      */   protected void dropExperience(World world, BlockPosition blockposition, int i) {
/*  370 */     if (!world.isClientSide) {
/*  371 */       while (i > 0) {
/*  372 */         int j = EntityExperienceOrb.getOrbValue(i);
/*      */         
/*  374 */         i -= j;
/*  375 */         world.addEntity(new EntityExperienceOrb(world, blockposition.getX() + 0.5D, blockposition.getY() + 0.5D, blockposition.getZ() + 0.5D, j));
/*      */       }
/*      */     }
/*      */   }
/*      */   
/*      */   public int getDropData(IBlockData iblockdata)
/*      */   {
/*  382 */     return 0;
/*      */   }
/*      */   
/*      */   public float a(Entity entity) {
/*  386 */     return this.durability / 5.0F;
/*      */   }
/*      */   
/*      */   public MovingObjectPosition a(World world, BlockPosition blockposition, Vec3D vec3d, Vec3D vec3d1) {
/*  390 */     updateShape(world, blockposition);
/*  391 */     vec3d = vec3d.add(-blockposition.getX(), -blockposition.getY(), -blockposition.getZ());
/*  392 */     vec3d1 = vec3d1.add(-blockposition.getX(), -blockposition.getY(), -blockposition.getZ());
/*  393 */     Vec3D vec3d2 = vec3d.a(vec3d1, this.minX);
/*  394 */     Vec3D vec3d3 = vec3d.a(vec3d1, this.maxX);
/*  395 */     Vec3D vec3d4 = vec3d.b(vec3d1, this.minY);
/*  396 */     Vec3D vec3d5 = vec3d.b(vec3d1, this.maxY);
/*  397 */     Vec3D vec3d6 = vec3d.c(vec3d1, this.minZ);
/*  398 */     Vec3D vec3d7 = vec3d.c(vec3d1, this.maxZ);
/*      */     
/*  400 */     if (!a(vec3d2)) {
/*  401 */       vec3d2 = null;
/*      */     }
/*      */     
/*  404 */     if (!a(vec3d3)) {
/*  405 */       vec3d3 = null;
/*      */     }
/*      */     
/*  408 */     if (!b(vec3d4)) {
/*  409 */       vec3d4 = null;
/*      */     }
/*      */     
/*  412 */     if (!b(vec3d5)) {
/*  413 */       vec3d5 = null;
/*      */     }
/*      */     
/*  416 */     if (!c(vec3d6)) {
/*  417 */       vec3d6 = null;
/*      */     }
/*      */     
/*  420 */     if (!c(vec3d7)) {
/*  421 */       vec3d7 = null;
/*      */     }
/*      */     
/*  424 */     Vec3D vec3d8 = null;
/*      */     
/*  426 */     if ((vec3d2 != null) && ((vec3d8 == null) || (vec3d.distanceSquared(vec3d2) < vec3d.distanceSquared(vec3d8)))) {
/*  427 */       vec3d8 = vec3d2;
/*      */     }
/*      */     
/*  430 */     if ((vec3d3 != null) && ((vec3d8 == null) || (vec3d.distanceSquared(vec3d3) < vec3d.distanceSquared(vec3d8)))) {
/*  431 */       vec3d8 = vec3d3;
/*      */     }
/*      */     
/*  434 */     if ((vec3d4 != null) && ((vec3d8 == null) || (vec3d.distanceSquared(vec3d4) < vec3d.distanceSquared(vec3d8)))) {
/*  435 */       vec3d8 = vec3d4;
/*      */     }
/*      */     
/*  438 */     if ((vec3d5 != null) && ((vec3d8 == null) || (vec3d.distanceSquared(vec3d5) < vec3d.distanceSquared(vec3d8)))) {
/*  439 */       vec3d8 = vec3d5;
/*      */     }
/*      */     
/*  442 */     if ((vec3d6 != null) && ((vec3d8 == null) || (vec3d.distanceSquared(vec3d6) < vec3d.distanceSquared(vec3d8)))) {
/*  443 */       vec3d8 = vec3d6;
/*      */     }
/*      */     
/*  446 */     if ((vec3d7 != null) && ((vec3d8 == null) || (vec3d.distanceSquared(vec3d7) < vec3d.distanceSquared(vec3d8)))) {
/*  447 */       vec3d8 = vec3d7;
/*      */     }
/*      */     
/*  450 */     if (vec3d8 == null) {
/*  451 */       return null;
/*      */     }
/*  453 */     EnumDirection enumdirection = null;
/*      */     
/*  455 */     if (vec3d8 == vec3d2) {
/*  456 */       enumdirection = EnumDirection.WEST;
/*      */     }
/*      */     
/*  459 */     if (vec3d8 == vec3d3) {
/*  460 */       enumdirection = EnumDirection.EAST;
/*      */     }
/*      */     
/*  463 */     if (vec3d8 == vec3d4) {
/*  464 */       enumdirection = EnumDirection.DOWN;
/*      */     }
/*      */     
/*  467 */     if (vec3d8 == vec3d5) {
/*  468 */       enumdirection = EnumDirection.UP;
/*      */     }
/*      */     
/*  471 */     if (vec3d8 == vec3d6) {
/*  472 */       enumdirection = EnumDirection.NORTH;
/*      */     }
/*      */     
/*  475 */     if (vec3d8 == vec3d7) {
/*  476 */       enumdirection = EnumDirection.SOUTH;
/*      */     }
/*      */     
/*  479 */     return new MovingObjectPosition(vec3d8.add(blockposition.getX(), blockposition.getY(), blockposition.getZ()), enumdirection, blockposition);
/*      */   }
/*      */   
/*      */   private boolean a(Vec3D vec3d)
/*      */   {
/*  484 */     return vec3d != null;
/*      */   }
/*      */   
/*      */   private boolean b(Vec3D vec3d) {
/*  488 */     return vec3d != null;
/*      */   }
/*      */   
/*      */   private boolean c(Vec3D vec3d) {
/*  492 */     return vec3d != null;
/*      */   }
/*      */   
/*      */   public void wasExploded(World world, BlockPosition blockposition, Explosion explosion) {}
/*      */   
/*      */   public boolean canPlace(World world, BlockPosition blockposition, EnumDirection enumdirection, ItemStack itemstack) {
/*  498 */     return canPlace(world, blockposition, enumdirection);
/*      */   }
/*      */   
/*      */   public boolean canPlace(World world, BlockPosition blockposition, EnumDirection enumdirection) {
/*  502 */     return canPlace(world, blockposition);
/*      */   }
/*      */   
/*      */   public boolean canPlace(World world, BlockPosition blockposition) {
/*  506 */     return world.getType(blockposition).getBlock().material.isReplaceable();
/*      */   }
/*      */   
/*      */   public boolean interact(World world, BlockPosition blockposition, IBlockData iblockdata, EntityHuman entityhuman, EnumDirection enumdirection, float f, float f1, float f2) {
/*  510 */     return false;
/*      */   }
/*      */   
/*      */   public void a(World world, BlockPosition blockposition, Entity entity) {}
/*      */   
/*      */   public IBlockData getPlacedState(World world, BlockPosition blockposition, EnumDirection enumdirection, float f, float f1, float f2, int i, EntityLiving entityliving) {
/*  516 */     return fromLegacyData(i);
/*      */   }
/*      */   
/*      */   public void attack(World world, BlockPosition blockposition, EntityHuman entityhuman) {}
/*      */   
/*      */   public Vec3D a(World world, BlockPosition blockposition, Entity entity, Vec3D vec3d) {
/*  522 */     return vec3d;
/*      */   }
/*      */   
/*      */   public void updateShape(IBlockAccess iblockaccess, BlockPosition blockposition) {}
/*      */   
/*      */   public final double B() {
/*  528 */     return this.minX;
/*      */   }
/*      */   
/*      */   public final double C() {
/*  532 */     return this.maxX;
/*      */   }
/*      */   
/*      */   public final double D() {
/*  536 */     return this.minY;
/*      */   }
/*      */   
/*      */   public final double E() {
/*  540 */     return this.maxY;
/*      */   }
/*      */   
/*      */   public final double F() {
/*  544 */     return this.minZ;
/*      */   }
/*      */   
/*      */   public final double G() {
/*  548 */     return this.maxZ;
/*      */   }
/*      */   
/*      */   public int a(IBlockAccess iblockaccess, BlockPosition blockposition, IBlockData iblockdata, EnumDirection enumdirection) {
/*  552 */     return 0;
/*      */   }
/*      */   
/*      */   public boolean isPowerSource() {
/*  556 */     return false;
/*      */   }
/*      */   
/*      */   public void a(World world, BlockPosition blockposition, IBlockData iblockdata, Entity entity) {}
/*      */   
/*      */   public int b(IBlockAccess iblockaccess, BlockPosition blockposition, IBlockData iblockdata, EnumDirection enumdirection) {
/*  562 */     return 0;
/*      */   }
/*      */   
/*      */   public void j() {}
/*      */   
/*      */   public void a(World world, EntityHuman entityhuman, BlockPosition blockposition, IBlockData iblockdata, TileEntity tileentity) {
/*  568 */     entityhuman.b(StatisticList.MINE_BLOCK_COUNT[getId(this)]);
/*  569 */     entityhuman.applyExhaustion(0.025F);
/*  570 */     if ((I()) && (EnchantmentManager.hasSilkTouchEnchantment(entityhuman))) {
/*  571 */       ItemStack itemstack = i(iblockdata);
/*      */       
/*  573 */       if (itemstack != null) {
/*  574 */         a(world, blockposition, itemstack);
/*      */       }
/*      */     } else {
/*  577 */       int i = EnchantmentManager.getBonusBlockLootEnchantmentLevel(entityhuman);
/*      */       
/*  579 */       b(world, blockposition, iblockdata, i);
/*      */     }
/*      */   }
/*      */   
/*      */   protected boolean I()
/*      */   {
/*  585 */     return (d()) && (!this.isTileEntity);
/*      */   }
/*      */   
/*      */   protected ItemStack i(IBlockData iblockdata) {
/*  589 */     int i = 0;
/*  590 */     Item item = Item.getItemOf(this);
/*      */     
/*  592 */     if ((item != null) && (item.k())) {
/*  593 */       i = toLegacyData(iblockdata);
/*      */     }
/*      */     
/*  596 */     return new ItemStack(item, 1, i);
/*      */   }
/*      */   
/*      */   public int getDropCount(int i, Random random) {
/*  600 */     return a(random);
/*      */   }
/*      */   
/*      */   public void postPlace(World world, BlockPosition blockposition, IBlockData iblockdata, EntityLiving entityliving, ItemStack itemstack) {}
/*      */   
/*      */   public boolean g() {
/*  606 */     return (!this.material.isBuildable()) && (!this.material.isLiquid());
/*      */   }
/*      */   
/*      */   public Block c(String s) {
/*  610 */     this.name = s;
/*  611 */     return this;
/*      */   }
/*      */   
/*      */   public String getName() {
/*  615 */     return LocaleI18n.get(a() + ".name");
/*      */   }
/*      */   
/*      */   public String a() {
/*  619 */     return "tile." + this.name;
/*      */   }
/*      */   
/*      */   public boolean a(World world, BlockPosition blockposition, IBlockData iblockdata, int i, int j) {
/*  623 */     return false;
/*      */   }
/*      */   
/*      */   public boolean J() {
/*  627 */     return this.y;
/*      */   }
/*      */   
/*      */   protected Block K() {
/*  631 */     this.y = false;
/*  632 */     return this;
/*      */   }
/*      */   
/*      */   public int k() {
/*  636 */     return this.material.getPushReaction();
/*      */   }
/*      */   
/*      */   public void fallOn(World world, BlockPosition blockposition, Entity entity, float f) {
/*  640 */     entity.e(f, 1.0F);
/*      */   }
/*      */   
/*      */   public void a(World world, Entity entity) {
/*  644 */     entity.motY = 0.0D;
/*      */   }
/*      */   
/*      */   public int getDropData(World world, BlockPosition blockposition) {
/*  648 */     return getDropData(world.getType(blockposition));
/*      */   }
/*      */   
/*      */   public Block a(CreativeModeTab creativemodetab) {
/*  652 */     this.creativeTab = creativemodetab;
/*  653 */     return this;
/*      */   }
/*      */   
/*      */   public void a(World world, BlockPosition blockposition, IBlockData iblockdata, EntityHuman entityhuman) {}
/*      */   
/*      */   public void k(World world, BlockPosition blockposition) {}
/*      */   
/*      */   public boolean N() {
/*  661 */     return true;
/*      */   }
/*      */   
/*      */   public boolean a(Explosion explosion) {
/*  665 */     return true;
/*      */   }
/*      */   
/*      */   public boolean b(Block block) {
/*  669 */     return this == block;
/*      */   }
/*      */   
/*      */   public static boolean a(Block block, Block block1) {
/*  673 */     return block == block1;
/*      */   }
/*      */   
/*      */   public boolean isComplexRedstone() {
/*  677 */     return false;
/*      */   }
/*      */   
/*      */   public int l(World world, BlockPosition blockposition) {
/*  681 */     return 0;
/*      */   }
/*      */   
/*      */   protected BlockStateList getStateList() {
/*  685 */     return new BlockStateList(this, new IBlockState[0]);
/*      */   }
/*      */   
/*      */   public BlockStateList P() {
/*  689 */     return this.blockStateList;
/*      */   }
/*      */   
/*      */   protected final void j(IBlockData iblockdata) {
/*  693 */     this.blockData = iblockdata;
/*      */   }
/*      */   
/*      */   public final IBlockData getBlockData() {
/*  697 */     return this.blockData;
/*      */   }
/*      */   
/*      */   public String toString() {
/*  701 */     return "Block{" + REGISTRY.c(this) + "}";
/*      */   }
/*      */   
/*      */   public static void S() {
/*  705 */     a(0, a, new BlockAir().c("air"));
/*  706 */     a(1, "stone", new BlockStone().c(1.5F).b(10.0F).a(i).c("stone"));
/*  707 */     a(2, "grass", new BlockGrass().c(0.6F).a(h).c("grass"));
/*  708 */     a(3, "dirt", new BlockDirt().c(0.5F).a(g).c("dirt"));
/*  709 */     Block block = new Block(Material.STONE).c(2.0F).b(10.0F).a(i).c("stonebrick").a(CreativeModeTab.b);
/*      */     
/*  711 */     a(4, "cobblestone", block);
/*  712 */     Block block1 = new BlockWood().c(2.0F).b(5.0F).a(f).c("wood");
/*      */     
/*  714 */     a(5, "planks", block1);
/*  715 */     a(6, "sapling", new BlockSapling().c(0.0F).a(h).c("sapling"));
/*  716 */     a(7, "bedrock", new Block(Material.STONE).x().b(6000000.0F).a(i).c("bedrock").K().a(CreativeModeTab.b));
/*  717 */     a(8, "flowing_water", new BlockFlowing(Material.WATER).c(100.0F).e(3).c("water").K());
/*  718 */     a(9, "water", new BlockStationary(Material.WATER).c(100.0F).e(3).c("water").K());
/*  719 */     a(10, "flowing_lava", new BlockFlowing(Material.LAVA).c(100.0F).a(1.0F).c("lava").K());
/*  720 */     a(11, "lava", new BlockStationary(Material.LAVA).c(100.0F).a(1.0F).c("lava").K());
/*  721 */     a(12, "sand", new BlockSand().c(0.5F).a(m).c("sand"));
/*  722 */     a(13, "gravel", new BlockGravel().c(0.6F).a(g).c("gravel"));
/*  723 */     a(14, "gold_ore", new BlockOre().c(3.0F).b(5.0F).a(i).c("oreGold"));
/*  724 */     a(15, "iron_ore", new BlockOre().c(3.0F).b(5.0F).a(i).c("oreIron"));
/*  725 */     a(16, "coal_ore", new BlockOre().c(3.0F).b(5.0F).a(i).c("oreCoal"));
/*  726 */     a(17, "log", new BlockLog1().c("log"));
/*  727 */     a(18, "leaves", new BlockLeaves1().c("leaves"));
/*  728 */     a(19, "sponge", new BlockSponge().c(0.6F).a(h).c("sponge"));
/*  729 */     a(20, "glass", new BlockGlass(Material.SHATTERABLE, false).c(0.3F).a(k).c("glass"));
/*  730 */     a(21, "lapis_ore", new BlockOre().c(3.0F).b(5.0F).a(i).c("oreLapis"));
/*  731 */     a(22, "lapis_block", new Block(Material.ORE, MaterialMapColor.H).c(3.0F).b(5.0F).a(i).c("blockLapis").a(CreativeModeTab.b));
/*  732 */     a(23, "dispenser", new BlockDispenser().c(3.5F).a(i).c("dispenser"));
/*  733 */     Block block2 = new BlockSandStone().a(i).c(0.8F).c("sandStone");
/*      */     
/*  735 */     a(24, "sandstone", block2);
/*  736 */     a(25, "noteblock", new BlockNote().c(0.8F).c("musicBlock"));
/*  737 */     a(26, "bed", new BlockBed().a(f).c(0.2F).c("bed").K());
/*  738 */     a(27, "golden_rail", new BlockPoweredRail().c(0.7F).a(j).c("goldenRail"));
/*  739 */     a(28, "detector_rail", new BlockMinecartDetector().c(0.7F).a(j).c("detectorRail"));
/*  740 */     a(29, "sticky_piston", new BlockPiston(true).c("pistonStickyBase"));
/*  741 */     a(30, "web", new BlockWeb().e(1).c(4.0F).c("web"));
/*  742 */     a(31, "tallgrass", new BlockLongGrass().c(0.0F).a(h).c("tallgrass"));
/*  743 */     a(32, "deadbush", new BlockDeadBush().c(0.0F).a(h).c("deadbush"));
/*  744 */     a(33, "piston", new BlockPiston(false).c("pistonBase"));
/*  745 */     a(34, "piston_head", new BlockPistonExtension().c("pistonBase"));
/*  746 */     a(35, "wool", new BlockCloth(Material.CLOTH).c(0.8F).a(l).c("cloth"));
/*  747 */     a(36, "piston_extension", new BlockPistonMoving());
/*  748 */     a(37, "yellow_flower", new BlockYellowFlowers().c(0.0F).a(h).c("flower1"));
/*  749 */     a(38, "red_flower", new BlockRedFlowers().c(0.0F).a(h).c("flower2"));
/*  750 */     Block block3 = new BlockMushroom().c(0.0F).a(h).a(0.125F).c("mushroom");
/*      */     
/*  752 */     a(39, "brown_mushroom", block3);
/*  753 */     Block block4 = new BlockMushroom().c(0.0F).a(h).c("mushroom");
/*      */     
/*  755 */     a(40, "red_mushroom", block4);
/*  756 */     a(41, "gold_block", new Block(Material.ORE, MaterialMapColor.F).c(3.0F).b(10.0F).a(j).c("blockGold").a(CreativeModeTab.b));
/*  757 */     a(42, "iron_block", new Block(Material.ORE, MaterialMapColor.h).c(5.0F).b(10.0F).a(j).c("blockIron").a(CreativeModeTab.b));
/*  758 */     a(43, "double_stone_slab", new BlockDoubleStep().c(2.0F).b(10.0F).a(i).c("stoneSlab"));
/*  759 */     a(44, "stone_slab", new BlockStep().c(2.0F).b(10.0F).a(i).c("stoneSlab"));
/*  760 */     Block block5 = new Block(Material.STONE, MaterialMapColor.D).c(2.0F).b(10.0F).a(i).c("brick").a(CreativeModeTab.b);
/*      */     
/*  762 */     a(45, "brick_block", block5);
/*  763 */     a(46, "tnt", new BlockTNT().c(0.0F).a(h).c("tnt"));
/*  764 */     a(47, "bookshelf", new BlockBookshelf().c(1.5F).a(f).c("bookshelf"));
/*  765 */     a(48, "mossy_cobblestone", new Block(Material.STONE).c(2.0F).b(10.0F).a(i).c("stoneMoss").a(CreativeModeTab.b));
/*  766 */     a(49, "obsidian", new BlockObsidian().c(50.0F).b(2000.0F).a(i).c("obsidian"));
/*  767 */     a(50, "torch", new BlockTorch().c(0.0F).a(0.9375F).a(f).c("torch"));
/*  768 */     a(51, "fire", new BlockFire().c(0.0F).a(1.0F).a(l).c("fire").K());
/*  769 */     a(52, "mob_spawner", new BlockMobSpawner().c(5.0F).a(j).c("mobSpawner").K());
/*  770 */     a(53, "oak_stairs", new BlockStairs(block1.getBlockData().set(BlockWood.VARIANT, BlockWood.EnumLogVariant.OAK)).c("stairsWood"));
/*  771 */     a(54, "chest", new BlockChest(0).c(2.5F).a(f).c("chest"));
/*  772 */     a(55, "redstone_wire", new BlockRedstoneWire().c(0.0F).a(e).c("redstoneDust").K());
/*  773 */     a(56, "diamond_ore", new BlockOre().c(3.0F).b(5.0F).a(i).c("oreDiamond"));
/*  774 */     a(57, "diamond_block", new Block(Material.ORE, MaterialMapColor.G).c(5.0F).b(10.0F).a(j).c("blockDiamond").a(CreativeModeTab.b));
/*  775 */     a(58, "crafting_table", new BlockWorkbench().c(2.5F).a(f).c("workbench"));
/*  776 */     a(59, "wheat", new BlockCrops().c("crops"));
/*  777 */     Block block6 = new BlockSoil().c(0.6F).a(g).c("farmland");
/*      */     
/*  779 */     a(60, "farmland", block6);
/*  780 */     a(61, "furnace", new BlockFurnace(false).c(3.5F).a(i).c("furnace").a(CreativeModeTab.c));
/*  781 */     a(62, "lit_furnace", new BlockFurnace(true).c(3.5F).a(i).a(0.875F).c("furnace"));
/*  782 */     a(63, "standing_sign", new BlockFloorSign().c(1.0F).a(f).c("sign").K());
/*  783 */     a(64, "wooden_door", new BlockDoor(Material.WOOD).c(3.0F).a(f).c("doorOak").K());
/*  784 */     a(65, "ladder", new BlockLadder().c(0.4F).a(o).c("ladder"));
/*  785 */     a(66, "rail", new BlockMinecartTrack().c(0.7F).a(j).c("rail"));
/*  786 */     a(67, "stone_stairs", new BlockStairs(block.getBlockData()).c("stairsStone"));
/*  787 */     a(68, "wall_sign", new BlockWallSign().c(1.0F).a(f).c("sign").K());
/*  788 */     a(69, "lever", new BlockLever().c(0.5F).a(f).c("lever"));
/*  789 */     a(70, "stone_pressure_plate", new BlockPressurePlateBinary(Material.STONE, BlockPressurePlateBinary.EnumMobType.MOBS).c(0.5F).a(i).c("pressurePlateStone"));
/*  790 */     a(71, "iron_door", new BlockDoor(Material.ORE).c(5.0F).a(j).c("doorIron").K());
/*  791 */     a(72, "wooden_pressure_plate", new BlockPressurePlateBinary(Material.WOOD, BlockPressurePlateBinary.EnumMobType.EVERYTHING).c(0.5F).a(f).c("pressurePlateWood"));
/*  792 */     a(73, "redstone_ore", new BlockRedstoneOre(false).c(3.0F).b(5.0F).a(i).c("oreRedstone").a(CreativeModeTab.b));
/*  793 */     a(74, "lit_redstone_ore", new BlockRedstoneOre(true).a(0.625F).c(3.0F).b(5.0F).a(i).c("oreRedstone"));
/*  794 */     a(75, "unlit_redstone_torch", new BlockRedstoneTorch(false).c(0.0F).a(f).c("notGate"));
/*  795 */     a(76, "redstone_torch", new BlockRedstoneTorch(true).c(0.0F).a(0.5F).a(f).c("notGate").a(CreativeModeTab.d));
/*  796 */     a(77, "stone_button", new BlockStoneButton().c(0.5F).a(i).c("button"));
/*  797 */     a(78, "snow_layer", new BlockSnow().c(0.1F).a(n).c("snow").e(0));
/*  798 */     a(79, "ice", new BlockIce().c(0.5F).e(3).a(k).c("ice"));
/*  799 */     a(80, "snow", new BlockSnowBlock().c(0.2F).a(n).c("snow"));
/*  800 */     a(81, "cactus", new BlockCactus().c(0.4F).a(l).c("cactus"));
/*  801 */     a(82, "clay", new BlockClay().c(0.6F).a(g).c("clay"));
/*  802 */     a(83, "reeds", new BlockReed().c(0.0F).a(h).c("reeds").K());
/*  803 */     a(84, "jukebox", new BlockJukeBox().c(2.0F).b(10.0F).a(i).c("jukebox"));
/*  804 */     a(85, "fence", new BlockFence(Material.WOOD, BlockWood.EnumLogVariant.OAK.c()).c(2.0F).b(5.0F).a(f).c("fence"));
/*  805 */     Block block7 = new BlockPumpkin().c(1.0F).a(f).c("pumpkin");
/*      */     
/*  807 */     a(86, "pumpkin", block7);
/*  808 */     a(87, "netherrack", new BlockBloodStone().c(0.4F).a(i).c("hellrock"));
/*  809 */     a(88, "soul_sand", new BlockSlowSand().c(0.5F).a(m).c("hellsand"));
/*  810 */     a(89, "glowstone", new BlockLightStone(Material.SHATTERABLE).c(0.3F).a(k).a(1.0F).c("lightgem"));
/*  811 */     a(90, "portal", new BlockPortal().c(-1.0F).a(k).a(0.75F).c("portal"));
/*  812 */     a(91, "lit_pumpkin", new BlockPumpkin().c(1.0F).a(f).a(1.0F).c("litpumpkin"));
/*  813 */     a(92, "cake", new BlockCake().c(0.5F).a(l).c("cake").K());
/*  814 */     a(93, "unpowered_repeater", new BlockRepeater(false).c(0.0F).a(f).c("diode").K());
/*  815 */     a(94, "powered_repeater", new BlockRepeater(true).c(0.0F).a(f).c("diode").K());
/*  816 */     a(95, "stained_glass", new BlockStainedGlass(Material.SHATTERABLE).c(0.3F).a(k).c("stainedGlass"));
/*  817 */     a(96, "trapdoor", new BlockTrapdoor(Material.WOOD).c(3.0F).a(f).c("trapdoor").K());
/*  818 */     a(97, "monster_egg", new BlockMonsterEggs().c(0.75F).c("monsterStoneEgg"));
/*  819 */     Block block8 = new BlockSmoothBrick().c(1.5F).b(10.0F).a(i).c("stonebricksmooth");
/*      */     
/*  821 */     a(98, "stonebrick", block8);
/*  822 */     a(99, "brown_mushroom_block", new BlockHugeMushroom(Material.WOOD, MaterialMapColor.l, block3).c(0.2F).a(f).c("mushroom"));
/*  823 */     a(100, "red_mushroom_block", new BlockHugeMushroom(Material.WOOD, MaterialMapColor.D, block4).c(0.2F).a(f).c("mushroom"));
/*  824 */     a(101, "iron_bars", new BlockThin(Material.ORE, true).c(5.0F).b(10.0F).a(j).c("fenceIron"));
/*  825 */     a(102, "glass_pane", new BlockThin(Material.SHATTERABLE, false).c(0.3F).a(k).c("thinGlass"));
/*  826 */     Block block9 = new BlockMelon().c(1.0F).a(f).c("melon");
/*      */     
/*  828 */     a(103, "melon_block", block9);
/*  829 */     a(104, "pumpkin_stem", new BlockStem(block7).c(0.0F).a(f).c("pumpkinStem"));
/*  830 */     a(105, "melon_stem", new BlockStem(block9).c(0.0F).a(f).c("pumpkinStem"));
/*  831 */     a(106, "vine", new BlockVine().c(0.2F).a(h).c("vine"));
/*  832 */     a(107, "fence_gate", new BlockFenceGate(BlockWood.EnumLogVariant.OAK).c(2.0F).b(5.0F).a(f).c("fenceGate"));
/*  833 */     a(108, "brick_stairs", new BlockStairs(block5.getBlockData()).c("stairsBrick"));
/*  834 */     a(109, "stone_brick_stairs", new BlockStairs(block8.getBlockData().set(BlockSmoothBrick.VARIANT, BlockSmoothBrick.EnumStonebrickType.DEFAULT)).c("stairsStoneBrickSmooth"));
/*  835 */     a(110, "mycelium", new BlockMycel().c(0.6F).a(h).c("mycel"));
/*  836 */     a(111, "waterlily", new BlockWaterLily().c(0.0F).a(h).c("waterlily"));
/*  837 */     Block block10 = new BlockNetherbrick().c(2.0F).b(10.0F).a(i).c("netherBrick").a(CreativeModeTab.b);
/*      */     
/*  839 */     a(112, "nether_brick", block10);
/*  840 */     a(113, "nether_brick_fence", new BlockFence(Material.STONE, MaterialMapColor.K).c(2.0F).b(10.0F).a(i).c("netherFence"));
/*  841 */     a(114, "nether_brick_stairs", new BlockStairs(block10.getBlockData()).c("stairsNetherBrick"));
/*  842 */     a(115, "nether_wart", new BlockNetherWart().c("netherStalk"));
/*  843 */     a(116, "enchanting_table", new BlockEnchantmentTable().c(5.0F).b(2000.0F).c("enchantmentTable"));
/*  844 */     a(117, "brewing_stand", new BlockBrewingStand().c(0.5F).a(0.125F).c("brewingStand"));
/*  845 */     a(118, "cauldron", new BlockCauldron().c(2.0F).c("cauldron"));
/*  846 */     a(119, "end_portal", new BlockEnderPortal(Material.PORTAL).c(-1.0F).b(6000000.0F));
/*  847 */     a(120, "end_portal_frame", new BlockEnderPortalFrame().a(k).a(0.125F).c(-1.0F).c("endPortalFrame").b(6000000.0F).a(CreativeModeTab.c));
/*  848 */     a(121, "end_stone", new Block(Material.STONE, MaterialMapColor.d).c(3.0F).b(15.0F).a(i).c("whiteStone").a(CreativeModeTab.b));
/*  849 */     a(122, "dragon_egg", new BlockDragonEgg().c(3.0F).b(15.0F).a(i).a(0.125F).c("dragonEgg"));
/*  850 */     a(123, "redstone_lamp", new BlockRedstoneLamp(false).c(0.3F).a(k).c("redstoneLight").a(CreativeModeTab.d));
/*  851 */     a(124, "lit_redstone_lamp", new BlockRedstoneLamp(true).c(0.3F).a(k).c("redstoneLight"));
/*  852 */     a(125, "double_wooden_slab", new BlockDoubleWoodStep().c(2.0F).b(5.0F).a(f).c("woodSlab"));
/*  853 */     a(126, "wooden_slab", new BlockWoodStep().c(2.0F).b(5.0F).a(f).c("woodSlab"));
/*  854 */     a(127, "cocoa", new BlockCocoa().c(0.2F).b(5.0F).a(f).c("cocoa"));
/*  855 */     a(128, "sandstone_stairs", new BlockStairs(block2.getBlockData().set(BlockSandStone.TYPE, BlockSandStone.EnumSandstoneVariant.SMOOTH)).c("stairsSandStone"));
/*  856 */     a(129, "emerald_ore", new BlockOre().c(3.0F).b(5.0F).a(i).c("oreEmerald"));
/*  857 */     a(130, "ender_chest", new BlockEnderChest().c(22.5F).b(1000.0F).a(i).c("enderChest").a(0.5F));
/*  858 */     a(131, "tripwire_hook", new BlockTripwireHook().c("tripWireSource"));
/*  859 */     a(132, "tripwire", new BlockTripwire().c("tripWire"));
/*  860 */     a(133, "emerald_block", new Block(Material.ORE, MaterialMapColor.I).c(5.0F).b(10.0F).a(j).c("blockEmerald").a(CreativeModeTab.b));
/*  861 */     a(134, "spruce_stairs", new BlockStairs(block1.getBlockData().set(BlockWood.VARIANT, BlockWood.EnumLogVariant.SPRUCE)).c("stairsWoodSpruce"));
/*  862 */     a(135, "birch_stairs", new BlockStairs(block1.getBlockData().set(BlockWood.VARIANT, BlockWood.EnumLogVariant.BIRCH)).c("stairsWoodBirch"));
/*  863 */     a(136, "jungle_stairs", new BlockStairs(block1.getBlockData().set(BlockWood.VARIANT, BlockWood.EnumLogVariant.JUNGLE)).c("stairsWoodJungle"));
/*  864 */     a(137, "command_block", new BlockCommand().x().b(6000000.0F).c("commandBlock"));
/*  865 */     a(138, "beacon", new BlockBeacon().c("beacon").a(1.0F));
/*  866 */     a(139, "cobblestone_wall", new BlockCobbleWall(block).c("cobbleWall"));
/*  867 */     a(140, "flower_pot", new BlockFlowerPot().c(0.0F).a(e).c("flowerPot"));
/*  868 */     a(141, "carrots", new BlockCarrots().c("carrots"));
/*  869 */     a(142, "potatoes", new BlockPotatoes().c("potatoes"));
/*  870 */     a(143, "wooden_button", new BlockWoodButton().c(0.5F).a(f).c("button"));
/*  871 */     a(144, "skull", new BlockSkull().c(1.0F).a(i).c("skull"));
/*  872 */     a(145, "anvil", new BlockAnvil().c(5.0F).a(p).b(2000.0F).c("anvil"));
/*  873 */     a(146, "trapped_chest", new BlockChest(1).c(2.5F).a(f).c("chestTrap"));
/*  874 */     a(147, "light_weighted_pressure_plate", new BlockPressurePlateWeighted(Material.ORE, 15, MaterialMapColor.F).c(0.5F).a(f).c("weightedPlate_light"));
/*  875 */     a(148, "heavy_weighted_pressure_plate", new BlockPressurePlateWeighted(Material.ORE, 150).c(0.5F).a(f).c("weightedPlate_heavy"));
/*  876 */     a(149, "unpowered_comparator", new BlockRedstoneComparator(false).c(0.0F).a(f).c("comparator").K());
/*  877 */     a(150, "powered_comparator", new BlockRedstoneComparator(true).c(0.0F).a(0.625F).a(f).c("comparator").K());
/*  878 */     a(151, "daylight_detector", new BlockDaylightDetector(false));
/*  879 */     a(152, "redstone_block", new BlockPowered(Material.ORE, MaterialMapColor.f).c(5.0F).b(10.0F).a(j).c("blockRedstone").a(CreativeModeTab.d));
/*  880 */     a(153, "quartz_ore", new BlockOre(MaterialMapColor.K).c(3.0F).b(5.0F).a(i).c("netherquartz"));
/*  881 */     a(154, "hopper", new BlockHopper().c(3.0F).b(8.0F).a(j).c("hopper"));
/*  882 */     Block block11 = new BlockQuartz().a(i).c(0.8F).c("quartzBlock");
/*      */     
/*  884 */     a(155, "quartz_block", block11);
/*  885 */     a(156, "quartz_stairs", new BlockStairs(block11.getBlockData().set(BlockQuartz.VARIANT, BlockQuartz.EnumQuartzVariant.DEFAULT)).c("stairsQuartz"));
/*  886 */     a(157, "activator_rail", new BlockPoweredRail().c(0.7F).a(j).c("activatorRail"));
/*  887 */     a(158, "dropper", new BlockDropper().c(3.5F).a(i).c("dropper"));
/*  888 */     a(159, "stained_hardened_clay", new BlockCloth(Material.STONE).c(1.25F).b(7.0F).a(i).c("clayHardenedStained"));
/*  889 */     a(160, "stained_glass_pane", new BlockStainedGlassPane().c(0.3F).a(k).c("thinStainedGlass"));
/*  890 */     a(161, "leaves2", new BlockLeaves2().c("leaves"));
/*  891 */     a(162, "log2", new BlockLog2().c("log"));
/*  892 */     a(163, "acacia_stairs", new BlockStairs(block1.getBlockData().set(BlockWood.VARIANT, BlockWood.EnumLogVariant.ACACIA)).c("stairsWoodAcacia"));
/*  893 */     a(164, "dark_oak_stairs", new BlockStairs(block1.getBlockData().set(BlockWood.VARIANT, BlockWood.EnumLogVariant.DARK_OAK)).c("stairsWoodDarkOak"));
/*  894 */     a(165, "slime", new BlockSlime().c("slime").a(q));
/*  895 */     a(166, "barrier", new BlockBarrier().c("barrier"));
/*  896 */     a(167, "iron_trapdoor", new BlockTrapdoor(Material.ORE).c(5.0F).a(j).c("ironTrapdoor").K());
/*  897 */     a(168, "prismarine", new BlockPrismarine().c(1.5F).b(10.0F).a(i).c("prismarine"));
/*  898 */     a(169, "sea_lantern", new BlockSeaLantern(Material.SHATTERABLE).c(0.3F).a(k).a(1.0F).c("seaLantern"));
/*  899 */     a(170, "hay_block", new BlockHay().c(0.5F).a(h).c("hayBlock").a(CreativeModeTab.b));
/*  900 */     a(171, "carpet", new BlockCarpet().c(0.1F).a(l).c("woolCarpet").e(0));
/*  901 */     a(172, "hardened_clay", new BlockHardenedClay().c(1.25F).b(7.0F).a(i).c("clayHardened"));
/*  902 */     a(173, "coal_block", new Block(Material.STONE, MaterialMapColor.E).c(5.0F).b(10.0F).a(i).c("blockCoal").a(CreativeModeTab.b));
/*  903 */     a(174, "packed_ice", new BlockPackedIce().c(0.5F).a(k).c("icePacked"));
/*  904 */     a(175, "double_plant", new BlockTallPlant());
/*  905 */     a(176, "standing_banner", new BlockBanner.BlockStandingBanner().c(1.0F).a(f).c("banner").K());
/*  906 */     a(177, "wall_banner", new BlockBanner.BlockWallBanner().c(1.0F).a(f).c("banner").K());
/*  907 */     a(178, "daylight_detector_inverted", new BlockDaylightDetector(true));
/*  908 */     Block block12 = new BlockRedSandstone().a(i).c(0.8F).c("redSandStone");
/*      */     
/*  910 */     a(179, "red_sandstone", block12);
/*  911 */     a(180, "red_sandstone_stairs", new BlockStairs(block12.getBlockData().set(BlockRedSandstone.TYPE, BlockRedSandstone.EnumRedSandstoneVariant.SMOOTH)).c("stairsRedSandStone"));
/*  912 */     a(181, "double_stone_slab2", new BlockDoubleStoneStep2().c(2.0F).b(10.0F).a(i).c("stoneSlab2"));
/*  913 */     a(182, "stone_slab2", new BlockStoneStep2().c(2.0F).b(10.0F).a(i).c("stoneSlab2"));
/*  914 */     a(183, "spruce_fence_gate", new BlockFenceGate(BlockWood.EnumLogVariant.SPRUCE).c(2.0F).b(5.0F).a(f).c("spruceFenceGate"));
/*  915 */     a(184, "birch_fence_gate", new BlockFenceGate(BlockWood.EnumLogVariant.BIRCH).c(2.0F).b(5.0F).a(f).c("birchFenceGate"));
/*  916 */     a(185, "jungle_fence_gate", new BlockFenceGate(BlockWood.EnumLogVariant.JUNGLE).c(2.0F).b(5.0F).a(f).c("jungleFenceGate"));
/*  917 */     a(186, "dark_oak_fence_gate", new BlockFenceGate(BlockWood.EnumLogVariant.DARK_OAK).c(2.0F).b(5.0F).a(f).c("darkOakFenceGate"));
/*  918 */     a(187, "acacia_fence_gate", new BlockFenceGate(BlockWood.EnumLogVariant.ACACIA).c(2.0F).b(5.0F).a(f).c("acaciaFenceGate"));
/*  919 */     a(188, "spruce_fence", new BlockFence(Material.WOOD, BlockWood.EnumLogVariant.SPRUCE.c()).c(2.0F).b(5.0F).a(f).c("spruceFence"));
/*  920 */     a(189, "birch_fence", new BlockFence(Material.WOOD, BlockWood.EnumLogVariant.BIRCH.c()).c(2.0F).b(5.0F).a(f).c("birchFence"));
/*  921 */     a(190, "jungle_fence", new BlockFence(Material.WOOD, BlockWood.EnumLogVariant.JUNGLE.c()).c(2.0F).b(5.0F).a(f).c("jungleFence"));
/*  922 */     a(191, "dark_oak_fence", new BlockFence(Material.WOOD, BlockWood.EnumLogVariant.DARK_OAK.c()).c(2.0F).b(5.0F).a(f).c("darkOakFence"));
/*  923 */     a(192, "acacia_fence", new BlockFence(Material.WOOD, BlockWood.EnumLogVariant.ACACIA.c()).c(2.0F).b(5.0F).a(f).c("acaciaFence"));
/*  924 */     a(193, "spruce_door", new BlockDoor(Material.WOOD).c(3.0F).a(f).c("doorSpruce").K());
/*  925 */     a(194, "birch_door", new BlockDoor(Material.WOOD).c(3.0F).a(f).c("doorBirch").K());
/*  926 */     a(195, "jungle_door", new BlockDoor(Material.WOOD).c(3.0F).a(f).c("doorJungle").K());
/*  927 */     a(196, "acacia_door", new BlockDoor(Material.WOOD).c(3.0F).a(f).c("doorAcacia").K());
/*  928 */     a(197, "dark_oak_door", new BlockDoor(Material.WOOD).c(3.0F).a(f).c("doorDarkOak").K());
/*  929 */     REGISTRY.a();
/*  930 */     Iterator iterator = REGISTRY.iterator();
/*      */     
/*      */ 
/*      */ 
/*  934 */     while (iterator.hasNext()) {
/*  935 */       Block block13 = (Block)iterator.next();
/*  936 */       if (block13.material == Material.AIR) {
/*  937 */         block13.v = false;
/*      */       } else {
/*  939 */         boolean flag = false;
/*  940 */         boolean flag1 = block13 instanceof BlockStairs;
/*  941 */         boolean flag2 = block13 instanceof BlockStepAbstract;
/*  942 */         boolean flag3 = block13 == block6;
/*  943 */         boolean flag4 = block13.t;
/*  944 */         boolean flag5 = block13.s == 0;
/*      */         
/*  946 */         if ((flag1) || (flag2) || (flag3) || (flag4) || (flag5)) {
/*  947 */           flag = true;
/*      */         }
/*      */         
/*  950 */         block13.v = flag;
/*      */       }
/*      */     }
/*      */     
/*  954 */     iterator = REGISTRY.iterator();
/*      */     Iterator iterator1;
/*  956 */     for (; iterator.hasNext(); 
/*      */         
/*      */ 
/*      */ 
/*  960 */         iterator1.hasNext())
/*      */     {
/*  957 */       Block block13 = (Block)iterator.next();
/*  958 */       iterator1 = block13.P().a().iterator();
/*      */       
/*  960 */       continue;
/*  961 */       IBlockData iblockdata = (IBlockData)iterator1.next();
/*  962 */       int i = REGISTRY.b(block13) << 4 | block13.toLegacyData(iblockdata);
/*      */       
/*  964 */       d.a(iblockdata, i);
/*      */     }
/*      */   }
/*      */   
/*      */ 
/*      */   private static void a(int i, MinecraftKey minecraftkey, Block block)
/*      */   {
/*  971 */     REGISTRY.a(i, minecraftkey, block);
/*      */   }
/*      */   
/*      */   private static void a(int i, String s, Block block) {
/*  975 */     a(i, new MinecraftKey(s), block);
/*      */   }
/*      */   
/*      */   public static class StepSound
/*      */   {
/*      */     public final String a;
/*      */     public final float b;
/*      */     public final float c;
/*      */     
/*      */     public StepSound(String s, float f, float f1) {
/*  985 */       this.a = s;
/*  986 */       this.b = f;
/*  987 */       this.c = f1;
/*      */     }
/*      */     
/*      */     public float getVolume1() {
/*  991 */       return this.b;
/*      */     }
/*      */     
/*      */     public float getVolume2() {
/*  995 */       return this.c;
/*      */     }
/*      */     
/*      */     public String getBreakSound() {
/*  999 */       return "dig." + this.a;
/*      */     }
/*      */     
/*      */     public String getStepSound() {
/* 1003 */       return "step." + this.a;
/*      */     }
/*      */     
/*      */     public String getPlaceSound() {
/* 1007 */       return getBreakSound();
/*      */     }
/*      */   }
/*      */   
/*      */   public int getExpDrop(World world, IBlockData data, int enchantmentLevel)
/*      */   {
/* 1013 */     return 0;
/*      */   }
/*      */   
/*      */ 
/*      */   public static float range(float min, float value, float max)
/*      */   {
/* 1019 */     if (value < min) {
/* 1020 */       return min;
/*      */     }
/* 1022 */     if (value > max) {
/* 1023 */       return max;
/*      */     }
/* 1025 */     return value;
/*      */   }
/*      */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_8_R3\Block.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */
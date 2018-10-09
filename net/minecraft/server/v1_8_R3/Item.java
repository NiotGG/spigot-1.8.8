/*     */ package net.minecraft.server.v1_8_R3;
/*     */ 
/*     */ import com.google.common.base.Function;
/*     */ import com.google.common.collect.HashMultimap;
/*     */ import com.google.common.collect.Maps;
/*     */ import com.google.common.collect.Multimap;
/*     */ import java.util.Map;
/*     */ import java.util.Random;
/*     */ import java.util.UUID;
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
/*     */ public class Item
/*     */ {
/*  38 */   public static final RegistryMaterials<MinecraftKey, Item> REGISTRY = new RegistryMaterials();
/*  39 */   private static final Map<Block, Item> a = Maps.newHashMap();
/*     */   
/*  41 */   protected static final UUID f = UUID.fromString("CB3F55D3-645C-4F38-A497-9C13A33DB5CF");
/*     */   
/*     */ 
/*     */ 
/*     */   private CreativeModeTab b;
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public static int getId(Item paramItem)
/*     */   {
/*  52 */     return paramItem == null ? 0 : REGISTRY.b(paramItem);
/*     */   }
/*     */   
/*     */   public static Item getById(int paramInt) {
/*  56 */     return (Item)REGISTRY.a(paramInt);
/*     */   }
/*     */   
/*     */   public static Item getItemOf(Block paramBlock) {
/*  60 */     return (Item)a.get(paramBlock);
/*     */   }
/*     */   
/*     */   public static Item d(String paramString) {
/*  64 */     Item localItem = (Item)REGISTRY.get(new MinecraftKey(paramString));
/*  65 */     if (localItem == null) {
/*     */       try {
/*  67 */         return getById(Integer.parseInt(paramString));
/*     */       }
/*     */       catch (NumberFormatException localNumberFormatException) {}
/*     */     }
/*  71 */     return localItem;
/*     */   }
/*     */   
/*     */   public boolean a(NBTTagCompound paramNBTTagCompound) {
/*  75 */     return false;
/*     */   }
/*     */   
/*     */ 
/*     */   public static enum EnumToolMaterial
/*     */   {
/*     */     private final int f;
/*     */     
/*     */     private final int g;
/*     */     
/*     */     private final float h;
/*     */     
/*     */     private final float i;
/*     */     private final int j;
/*     */     
/*     */     private EnumToolMaterial(int paramInt1, int paramInt2, float paramFloat1, float paramFloat2, int paramInt3)
/*     */     {
/*  92 */       this.f = paramInt1;
/*  93 */       this.g = paramInt2;
/*  94 */       this.h = paramFloat1;
/*  95 */       this.i = paramFloat2;
/*  96 */       this.j = paramInt3;
/*     */     }
/*     */     
/*     */     public int a() {
/* 100 */       return this.g;
/*     */     }
/*     */     
/*     */     public float b() {
/* 104 */       return this.h;
/*     */     }
/*     */     
/*     */     public float c() {
/* 108 */       return this.i;
/*     */     }
/*     */     
/*     */     public int d() {
/* 112 */       return this.f;
/*     */     }
/*     */     
/*     */     public int e() {
/* 116 */       return this.j;
/*     */     }
/*     */     
/*     */     public Item f() {
/* 120 */       if (this == WOOD)
/* 121 */         return Item.getItemOf(Blocks.PLANKS);
/* 122 */       if (this == STONE)
/* 123 */         return Item.getItemOf(Blocks.COBBLESTONE);
/* 124 */       if (this == GOLD)
/* 125 */         return Items.GOLD_INGOT;
/* 126 */       if (this == IRON)
/* 127 */         return Items.IRON_INGOT;
/* 128 */       if (this == EMERALD) {
/* 129 */         return Items.DIAMOND;
/*     */       }
/* 131 */       return null;
/*     */     }
/*     */   }
/*     */   
/* 135 */   protected static Random g = new Random();
/*     */   
/*     */ 
/* 138 */   protected int maxStackSize = 64;
/*     */   
/*     */   private int durability;
/*     */   
/*     */   protected boolean i;
/*     */   protected boolean j;
/*     */   private Item craftingResult;
/*     */   private String k;
/*     */   private String name;
/*     */   
/*     */   public Item c(int paramInt)
/*     */   {
/* 150 */     this.maxStackSize = paramInt;
/* 151 */     return this;
/*     */   }
/*     */   
/*     */   public boolean interactWith(ItemStack paramItemStack, EntityHuman paramEntityHuman, World paramWorld, BlockPosition paramBlockPosition, EnumDirection paramEnumDirection, float paramFloat1, float paramFloat2, float paramFloat3) {
/* 155 */     return false;
/*     */   }
/*     */   
/*     */   public float getDestroySpeed(ItemStack paramItemStack, Block paramBlock) {
/* 159 */     return 1.0F;
/*     */   }
/*     */   
/*     */   public ItemStack a(ItemStack paramItemStack, World paramWorld, EntityHuman paramEntityHuman) {
/* 163 */     return paramItemStack;
/*     */   }
/*     */   
/*     */   public ItemStack b(ItemStack paramItemStack, World paramWorld, EntityHuman paramEntityHuman) {
/* 167 */     return paramItemStack;
/*     */   }
/*     */   
/*     */   public int getMaxStackSize() {
/* 171 */     return this.maxStackSize;
/*     */   }
/*     */   
/*     */   public int filterData(int paramInt) {
/* 175 */     return 0;
/*     */   }
/*     */   
/*     */   public boolean k() {
/* 179 */     return this.j;
/*     */   }
/*     */   
/*     */   protected Item a(boolean paramBoolean) {
/* 183 */     this.j = paramBoolean;
/* 184 */     return this;
/*     */   }
/*     */   
/*     */   public int getMaxDurability() {
/* 188 */     return this.durability;
/*     */   }
/*     */   
/*     */   protected Item setMaxDurability(int paramInt) {
/* 192 */     this.durability = paramInt;
/* 193 */     return this;
/*     */   }
/*     */   
/*     */   public boolean usesDurability() {
/* 197 */     return (this.durability > 0) && (!this.j);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public boolean a(ItemStack paramItemStack, EntityLiving paramEntityLiving1, EntityLiving paramEntityLiving2)
/*     */   {
/* 204 */     return false;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public boolean a(ItemStack paramItemStack, World paramWorld, Block paramBlock, BlockPosition paramBlockPosition, EntityLiving paramEntityLiving)
/*     */   {
/* 211 */     return false;
/*     */   }
/*     */   
/*     */   public boolean canDestroySpecialBlock(Block paramBlock) {
/* 215 */     return false;
/*     */   }
/*     */   
/*     */   public boolean a(ItemStack paramItemStack, EntityHuman paramEntityHuman, EntityLiving paramEntityLiving) {
/* 219 */     return false;
/*     */   }
/*     */   
/*     */   public Item n() {
/* 223 */     this.i = true;
/* 224 */     return this;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public Item c(String paramString)
/*     */   {
/* 237 */     this.name = paramString;
/* 238 */     return this;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public String k(ItemStack paramItemStack)
/*     */   {
/* 246 */     String str = e_(paramItemStack);
/* 247 */     if (str == null) {
/* 248 */       return "";
/*     */     }
/* 250 */     return LocaleI18n.get(str);
/*     */   }
/*     */   
/*     */   public String getName()
/*     */   {
/* 255 */     return "item." + this.name;
/*     */   }
/*     */   
/*     */   public String e_(ItemStack paramItemStack) {
/* 259 */     return "item." + this.name;
/*     */   }
/*     */   
/*     */   public Item c(Item paramItem) {
/* 263 */     this.craftingResult = paramItem;
/* 264 */     return this;
/*     */   }
/*     */   
/*     */   public boolean p() {
/* 268 */     return true;
/*     */   }
/*     */   
/*     */   public Item q() {
/* 272 */     return this.craftingResult;
/*     */   }
/*     */   
/*     */   public boolean r() {
/* 276 */     return this.craftingResult != null;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public void a(ItemStack paramItemStack, World paramWorld, Entity paramEntity, int paramInt, boolean paramBoolean) {}
/*     */   
/*     */ 
/*     */ 
/*     */   public void d(ItemStack paramItemStack, World paramWorld, EntityHuman paramEntityHuman) {}
/*     */   
/*     */ 
/*     */   public boolean f()
/*     */   {
/* 290 */     return false;
/*     */   }
/*     */   
/*     */   public EnumAnimation e(ItemStack paramItemStack) {
/* 294 */     return EnumAnimation.NONE;
/*     */   }
/*     */   
/*     */   public int d(ItemStack paramItemStack) {
/* 298 */     return 0;
/*     */   }
/*     */   
/*     */   public void a(ItemStack paramItemStack, World paramWorld, EntityHuman paramEntityHuman, int paramInt) {}
/*     */   
/*     */   protected Item e(String paramString)
/*     */   {
/* 305 */     this.k = paramString;
/* 306 */     return this;
/*     */   }
/*     */   
/*     */   public String j(ItemStack paramItemStack) {
/* 310 */     return this.k;
/*     */   }
/*     */   
/*     */   public boolean l(ItemStack paramItemStack) {
/* 314 */     return j(paramItemStack) != null;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public String a(ItemStack paramItemStack)
/*     */   {
/* 321 */     return ("" + LocaleI18n.get(new StringBuilder().append(k(paramItemStack)).append(".name").toString())).trim();
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public EnumItemRarity g(ItemStack paramItemStack)
/*     */   {
/* 332 */     if (paramItemStack.hasEnchantments()) {
/* 333 */       return EnumItemRarity.RARE;
/*     */     }
/* 335 */     return EnumItemRarity.COMMON;
/*     */   }
/*     */   
/*     */   public boolean f_(ItemStack paramItemStack) {
/* 339 */     return (getMaxStackSize() == 1) && (usesDurability());
/*     */   }
/*     */   
/*     */   protected MovingObjectPosition a(World paramWorld, EntityHuman paramEntityHuman, boolean paramBoolean) {
/* 343 */     float f1 = paramEntityHuman.pitch;
/* 344 */     float f2 = paramEntityHuman.yaw;
/*     */     
/* 346 */     double d1 = paramEntityHuman.locX;
/* 347 */     double d2 = paramEntityHuman.locY + paramEntityHuman.getHeadHeight();
/* 348 */     double d3 = paramEntityHuman.locZ;
/*     */     
/* 350 */     Vec3D localVec3D1 = new Vec3D(d1, d2, d3);
/*     */     
/* 352 */     float f3 = MathHelper.cos(-f2 * 0.017453292F - 3.1415927F);
/* 353 */     float f4 = MathHelper.sin(-f2 * 0.017453292F - 3.1415927F);
/* 354 */     float f5 = -MathHelper.cos(-f1 * 0.017453292F);
/* 355 */     float f6 = MathHelper.sin(-f1 * 0.017453292F);
/*     */     
/* 357 */     float f7 = f4 * f5;
/* 358 */     float f8 = f6;
/* 359 */     float f9 = f3 * f5;
/*     */     
/* 361 */     double d4 = 5.0D;
/* 362 */     Vec3D localVec3D2 = localVec3D1.add(f7 * d4, f8 * d4, f9 * d4);
/*     */     
/* 364 */     return paramWorld.rayTrace(localVec3D1, localVec3D2, paramBoolean, !paramBoolean, false);
/*     */   }
/*     */   
/*     */   public int b() {
/* 368 */     return 0;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public Item a(CreativeModeTab paramCreativeModeTab)
/*     */   {
/* 380 */     this.b = paramCreativeModeTab;
/* 381 */     return this;
/*     */   }
/*     */   
/*     */   public boolean s() {
/* 385 */     return false;
/*     */   }
/*     */   
/*     */   public boolean a(ItemStack paramItemStack1, ItemStack paramItemStack2) {
/* 389 */     return false;
/*     */   }
/*     */   
/*     */   public Multimap<String, AttributeModifier> i() {
/* 393 */     return HashMultimap.create();
/*     */   }
/*     */   
/*     */ 
/*     */   public static void t()
/*     */   {
/* 399 */     a(Blocks.STONE, new ItemMultiTexture(Blocks.STONE, Blocks.STONE, new Function()
/*     */     {
/*     */ 
/*     */       public String a(ItemStack paramAnonymousItemStack) {
/* 403 */         return BlockStone.EnumStoneVariant.a(paramAnonymousItemStack.getData()).d(); } }).b("stone"));
/*     */     
/*     */ 
/* 406 */     a(Blocks.GRASS, new ItemWithAuxData(Blocks.GRASS, false));
/* 407 */     a(Blocks.DIRT, new ItemMultiTexture(Blocks.DIRT, Blocks.DIRT, new Function()
/*     */     {
/*     */ 
/*     */       public String a(ItemStack paramAnonymousItemStack) {
/* 411 */         return BlockDirt.EnumDirtVariant.a(paramAnonymousItemStack.getData()).c(); } }).b("dirt"));
/*     */     
/*     */ 
/* 414 */     c(Blocks.COBBLESTONE);
/* 415 */     a(Blocks.PLANKS, new ItemMultiTexture(Blocks.PLANKS, Blocks.PLANKS, new Function()
/*     */     {
/*     */ 
/*     */       public String a(ItemStack paramAnonymousItemStack) {
/* 419 */         return BlockWood.EnumLogVariant.a(paramAnonymousItemStack.getData()).d(); } }).b("wood"));
/*     */     
/*     */ 
/* 422 */     a(Blocks.SAPLING, new ItemMultiTexture(Blocks.SAPLING, Blocks.SAPLING, new Function()
/*     */     {
/*     */ 
/*     */       public String a(ItemStack paramAnonymousItemStack) {
/* 426 */         return BlockWood.EnumLogVariant.a(paramAnonymousItemStack.getData()).d(); } }).b("sapling"));
/*     */     
/*     */ 
/* 429 */     c(Blocks.BEDROCK);
/* 430 */     a(Blocks.SAND, new ItemMultiTexture(Blocks.SAND, Blocks.SAND, new Function()
/*     */     {
/*     */ 
/*     */       public String a(ItemStack paramAnonymousItemStack) {
/* 434 */         return BlockSand.EnumSandVariant.a(paramAnonymousItemStack.getData()).d(); } }).b("sand"));
/*     */     
/*     */ 
/* 437 */     c(Blocks.GRAVEL);
/* 438 */     c(Blocks.GOLD_ORE);
/* 439 */     c(Blocks.IRON_ORE);
/* 440 */     c(Blocks.COAL_ORE);
/* 441 */     a(Blocks.LOG, new ItemMultiTexture(Blocks.LOG, Blocks.LOG, new Function()
/*     */     {
/*     */ 
/*     */       public String a(ItemStack paramAnonymousItemStack) {
/* 445 */         return BlockWood.EnumLogVariant.a(paramAnonymousItemStack.getData()).d(); } }).b("log"));
/*     */     
/*     */ 
/* 448 */     a(Blocks.LOG2, new ItemMultiTexture(Blocks.LOG2, Blocks.LOG2, new Function()
/*     */     {
/*     */ 
/*     */       public String a(ItemStack paramAnonymousItemStack) {
/* 452 */         return BlockWood.EnumLogVariant.a(paramAnonymousItemStack.getData() + 4).d(); } }).b("log"));
/*     */     
/*     */ 
/* 455 */     a(Blocks.LEAVES, new ItemLeaves(Blocks.LEAVES).b("leaves"));
/* 456 */     a(Blocks.LEAVES2, new ItemLeaves(Blocks.LEAVES2).b("leaves"));
/* 457 */     a(Blocks.SPONGE, new ItemMultiTexture(Blocks.SPONGE, Blocks.SPONGE, new Function()
/*     */     {
/*     */ 
/*     */       public String a(ItemStack paramAnonymousItemStack) {
/* 461 */         return (paramAnonymousItemStack.getData() & 0x1) == 1 ? "wet" : "dry"; } }).b("sponge"));
/*     */     
/*     */ 
/* 464 */     c(Blocks.GLASS);
/* 465 */     c(Blocks.LAPIS_ORE);
/* 466 */     c(Blocks.LAPIS_BLOCK);
/* 467 */     c(Blocks.DISPENSER);
/* 468 */     a(Blocks.SANDSTONE, new ItemMultiTexture(Blocks.SANDSTONE, Blocks.SANDSTONE, new Function()
/*     */     {
/*     */ 
/*     */       public String a(ItemStack paramAnonymousItemStack) {
/* 472 */         return BlockSandStone.EnumSandstoneVariant.a(paramAnonymousItemStack.getData()).c(); } }).b("sandStone"));
/*     */     
/*     */ 
/* 475 */     c(Blocks.NOTEBLOCK);
/* 476 */     c(Blocks.GOLDEN_RAIL);
/* 477 */     c(Blocks.DETECTOR_RAIL);
/* 478 */     a(Blocks.STICKY_PISTON, new ItemPiston(Blocks.STICKY_PISTON));
/* 479 */     c(Blocks.WEB);
/* 480 */     a(Blocks.TALLGRASS, new ItemWithAuxData(Blocks.TALLGRASS, true).a(new String[] { "shrub", "grass", "fern" }));
/* 481 */     c(Blocks.DEADBUSH);
/* 482 */     a(Blocks.PISTON, new ItemPiston(Blocks.PISTON));
/* 483 */     a(Blocks.WOOL, new ItemCloth(Blocks.WOOL).b("cloth"));
/* 484 */     a(Blocks.YELLOW_FLOWER, new ItemMultiTexture(Blocks.YELLOW_FLOWER, Blocks.YELLOW_FLOWER, new Function()
/*     */     {
/*     */ 
/*     */       public String a(ItemStack paramAnonymousItemStack) {
/* 488 */         return BlockFlowers.EnumFlowerVarient.a(BlockFlowers.EnumFlowerType.YELLOW, paramAnonymousItemStack.getData()).d(); } }).b("flower"));
/*     */     
/*     */ 
/* 491 */     a(Blocks.RED_FLOWER, new ItemMultiTexture(Blocks.RED_FLOWER, Blocks.RED_FLOWER, new Function()
/*     */     {
/*     */ 
/*     */       public String a(ItemStack paramAnonymousItemStack) {
/* 495 */         return BlockFlowers.EnumFlowerVarient.a(BlockFlowers.EnumFlowerType.RED, paramAnonymousItemStack.getData()).d(); } }).b("rose"));
/*     */     
/*     */ 
/* 498 */     c(Blocks.BROWN_MUSHROOM);
/* 499 */     c(Blocks.RED_MUSHROOM);
/* 500 */     c(Blocks.GOLD_BLOCK);
/* 501 */     c(Blocks.IRON_BLOCK);
/* 502 */     a(Blocks.STONE_SLAB, new ItemStep(Blocks.STONE_SLAB, Blocks.STONE_SLAB, Blocks.DOUBLE_STONE_SLAB).b("stoneSlab"));
/* 503 */     c(Blocks.BRICK_BLOCK);
/* 504 */     c(Blocks.TNT);
/* 505 */     c(Blocks.BOOKSHELF);
/* 506 */     c(Blocks.MOSSY_COBBLESTONE);
/* 507 */     c(Blocks.OBSIDIAN);
/* 508 */     c(Blocks.TORCH);
/* 509 */     c(Blocks.MOB_SPAWNER);
/* 510 */     c(Blocks.OAK_STAIRS);
/* 511 */     c(Blocks.CHEST);
/* 512 */     c(Blocks.DIAMOND_ORE);
/* 513 */     c(Blocks.DIAMOND_BLOCK);
/* 514 */     c(Blocks.CRAFTING_TABLE);
/* 515 */     c(Blocks.FARMLAND);
/* 516 */     c(Blocks.FURNACE);
/* 517 */     c(Blocks.LIT_FURNACE);
/* 518 */     c(Blocks.LADDER);
/* 519 */     c(Blocks.RAIL);
/* 520 */     c(Blocks.STONE_STAIRS);
/* 521 */     c(Blocks.LEVER);
/* 522 */     c(Blocks.STONE_PRESSURE_PLATE);
/* 523 */     c(Blocks.WOODEN_PRESSURE_PLATE);
/* 524 */     c(Blocks.REDSTONE_ORE);
/* 525 */     c(Blocks.REDSTONE_TORCH);
/* 526 */     c(Blocks.STONE_BUTTON);
/* 527 */     a(Blocks.SNOW_LAYER, new ItemSnow(Blocks.SNOW_LAYER));
/* 528 */     c(Blocks.ICE);
/* 529 */     c(Blocks.SNOW);
/* 530 */     c(Blocks.CACTUS);
/* 531 */     c(Blocks.CLAY);
/* 532 */     c(Blocks.JUKEBOX);
/* 533 */     c(Blocks.FENCE);
/* 534 */     c(Blocks.SPRUCE_FENCE);
/* 535 */     c(Blocks.BIRCH_FENCE);
/* 536 */     c(Blocks.JUNGLE_FENCE);
/* 537 */     c(Blocks.DARK_OAK_FENCE);
/* 538 */     c(Blocks.ACACIA_FENCE);
/* 539 */     c(Blocks.PUMPKIN);
/* 540 */     c(Blocks.NETHERRACK);
/* 541 */     c(Blocks.SOUL_SAND);
/* 542 */     c(Blocks.GLOWSTONE);
/* 543 */     c(Blocks.LIT_PUMPKIN);
/* 544 */     c(Blocks.TRAPDOOR);
/* 545 */     a(Blocks.MONSTER_EGG, new ItemMultiTexture(Blocks.MONSTER_EGG, Blocks.MONSTER_EGG, new Function()
/*     */     {
/*     */ 
/*     */       public String a(ItemStack paramAnonymousItemStack) {
/* 549 */         return BlockMonsterEggs.EnumMonsterEggVarient.a(paramAnonymousItemStack.getData()).c(); } }).b("monsterStoneEgg"));
/*     */     
/*     */ 
/* 552 */     a(Blocks.STONEBRICK, new ItemMultiTexture(Blocks.STONEBRICK, Blocks.STONEBRICK, new Function()
/*     */     {
/*     */ 
/*     */       public String a(ItemStack paramAnonymousItemStack) {
/* 556 */         return BlockSmoothBrick.EnumStonebrickType.a(paramAnonymousItemStack.getData()).c(); } }).b("stonebricksmooth"));
/*     */     
/*     */ 
/* 559 */     c(Blocks.BROWN_MUSHROOM_BLOCK);
/* 560 */     c(Blocks.RED_MUSHROOM_BLOCK);
/* 561 */     c(Blocks.IRON_BARS);
/* 562 */     c(Blocks.GLASS_PANE);
/* 563 */     c(Blocks.MELON_BLOCK);
/* 564 */     a(Blocks.VINE, new ItemWithAuxData(Blocks.VINE, false));
/* 565 */     c(Blocks.FENCE_GATE);
/* 566 */     c(Blocks.SPRUCE_FENCE_GATE);
/* 567 */     c(Blocks.BIRCH_FENCE_GATE);
/* 568 */     c(Blocks.JUNGLE_FENCE_GATE);
/* 569 */     c(Blocks.DARK_OAK_FENCE_GATE);
/* 570 */     c(Blocks.ACACIA_FENCE_GATE);
/* 571 */     c(Blocks.BRICK_STAIRS);
/* 572 */     c(Blocks.STONE_BRICK_STAIRS);
/* 573 */     c(Blocks.MYCELIUM);
/* 574 */     a(Blocks.WATERLILY, new ItemWaterLily(Blocks.WATERLILY));
/* 575 */     c(Blocks.NETHER_BRICK);
/* 576 */     c(Blocks.NETHER_BRICK_FENCE);
/* 577 */     c(Blocks.NETHER_BRICK_STAIRS);
/* 578 */     c(Blocks.ENCHANTING_TABLE);
/* 579 */     c(Blocks.END_PORTAL_FRAME);
/* 580 */     c(Blocks.END_STONE);
/* 581 */     c(Blocks.DRAGON_EGG);
/* 582 */     c(Blocks.REDSTONE_LAMP);
/* 583 */     a(Blocks.WOODEN_SLAB, new ItemStep(Blocks.WOODEN_SLAB, Blocks.WOODEN_SLAB, Blocks.DOUBLE_WOODEN_SLAB).b("woodSlab"));
/* 584 */     c(Blocks.SANDSTONE_STAIRS);
/* 585 */     c(Blocks.EMERALD_ORE);
/* 586 */     c(Blocks.ENDER_CHEST);
/* 587 */     c(Blocks.TRIPWIRE_HOOK);
/* 588 */     c(Blocks.EMERALD_BLOCK);
/* 589 */     c(Blocks.SPRUCE_STAIRS);
/* 590 */     c(Blocks.BIRCH_STAIRS);
/* 591 */     c(Blocks.JUNGLE_STAIRS);
/* 592 */     c(Blocks.COMMAND_BLOCK);
/* 593 */     c(Blocks.BEACON);
/* 594 */     a(Blocks.COBBLESTONE_WALL, new ItemMultiTexture(Blocks.COBBLESTONE_WALL, Blocks.COBBLESTONE_WALL, new Function()
/*     */     {
/*     */ 
/*     */       public String a(ItemStack paramAnonymousItemStack) {
/* 598 */         return BlockCobbleWall.EnumCobbleVariant.a(paramAnonymousItemStack.getData()).c(); } }).b("cobbleWall"));
/*     */     
/*     */ 
/* 601 */     c(Blocks.WOODEN_BUTTON);
/* 602 */     a(Blocks.ANVIL, new ItemAnvil(Blocks.ANVIL).b("anvil"));
/* 603 */     c(Blocks.TRAPPED_CHEST);
/* 604 */     c(Blocks.LIGHT_WEIGHTED_PRESSURE_PLATE);
/* 605 */     c(Blocks.HEAVY_WEIGHTED_PRESSURE_PLATE);
/* 606 */     c(Blocks.DAYLIGHT_DETECTOR);
/* 607 */     c(Blocks.REDSTONE_BLOCK);
/* 608 */     c(Blocks.QUARTZ_ORE);
/* 609 */     c(Blocks.HOPPER);
/* 610 */     a(Blocks.QUARTZ_BLOCK, new ItemMultiTexture(Blocks.QUARTZ_BLOCK, Blocks.QUARTZ_BLOCK, new String[] { "default", "chiseled", "lines" }).b("quartzBlock"));
/* 611 */     c(Blocks.QUARTZ_STAIRS);
/* 612 */     c(Blocks.ACTIVATOR_RAIL);
/* 613 */     c(Blocks.DROPPER);
/* 614 */     a(Blocks.STAINED_HARDENED_CLAY, new ItemCloth(Blocks.STAINED_HARDENED_CLAY).b("clayHardenedStained"));
/* 615 */     c(Blocks.BARRIER);
/* 616 */     c(Blocks.IRON_TRAPDOOR);
/* 617 */     c(Blocks.HAY_BLOCK);
/* 618 */     a(Blocks.CARPET, new ItemCloth(Blocks.CARPET).b("woolCarpet"));
/* 619 */     c(Blocks.HARDENED_CLAY);
/* 620 */     c(Blocks.COAL_BLOCK);
/* 621 */     c(Blocks.PACKED_ICE);
/* 622 */     c(Blocks.ACACIA_STAIRS);
/* 623 */     c(Blocks.DARK_OAK_STAIRS);
/* 624 */     c(Blocks.SLIME);
/* 625 */     a(Blocks.DOUBLE_PLANT, new ItemTallPlant(Blocks.DOUBLE_PLANT, Blocks.DOUBLE_PLANT, new Function()
/*     */     {
/*     */ 
/*     */       public String a(ItemStack paramAnonymousItemStack) {
/* 629 */         return BlockTallPlant.EnumTallFlowerVariants.a(paramAnonymousItemStack.getData()).c(); } }).b("doublePlant"));
/*     */     
/*     */ 
/* 632 */     a(Blocks.STAINED_GLASS, new ItemCloth(Blocks.STAINED_GLASS).b("stainedGlass"));
/* 633 */     a(Blocks.STAINED_GLASS_PANE, new ItemCloth(Blocks.STAINED_GLASS_PANE).b("stainedGlassPane"));
/* 634 */     a(Blocks.PRISMARINE, new ItemMultiTexture(Blocks.PRISMARINE, Blocks.PRISMARINE, new Function()
/*     */     {
/*     */ 
/*     */       public String a(ItemStack paramAnonymousItemStack) {
/* 638 */         return BlockPrismarine.EnumPrismarineVariant.a(paramAnonymousItemStack.getData()).c(); } }).b("prismarine"));
/*     */     
/*     */ 
/* 641 */     c(Blocks.SEA_LANTERN);
/* 642 */     a(Blocks.RED_SANDSTONE, new ItemMultiTexture(Blocks.RED_SANDSTONE, Blocks.RED_SANDSTONE, new Function()
/*     */     {
/*     */ 
/*     */       public String a(ItemStack paramAnonymousItemStack) {
/* 646 */         return BlockRedSandstone.EnumRedSandstoneVariant.a(paramAnonymousItemStack.getData()).c(); } }).b("redSandStone"));
/*     */     
/*     */ 
/* 649 */     c(Blocks.RED_SANDSTONE_STAIRS);
/* 650 */     a(Blocks.STONE_SLAB2, new ItemStep(Blocks.STONE_SLAB2, Blocks.STONE_SLAB2, Blocks.DOUBLE_STONE_SLAB2).b("stoneSlab2"));
/*     */     
/*     */ 
/* 653 */     a(256, "iron_shovel", new ItemSpade(EnumToolMaterial.IRON).c("shovelIron"));
/* 654 */     a(257, "iron_pickaxe", new ItemPickaxe(EnumToolMaterial.IRON).c("pickaxeIron"));
/* 655 */     a(258, "iron_axe", new ItemAxe(EnumToolMaterial.IRON).c("hatchetIron"));
/* 656 */     a(259, "flint_and_steel", new ItemFlintAndSteel().c("flintAndSteel"));
/* 657 */     a(260, "apple", new ItemFood(4, 0.3F, false).c("apple"));
/* 658 */     a(261, "bow", new ItemBow().c("bow"));
/* 659 */     a(262, "arrow", new Item().c("arrow").a(CreativeModeTab.j));
/* 660 */     a(263, "coal", new ItemCoal().c("coal"));
/* 661 */     a(264, "diamond", new Item().c("diamond").a(CreativeModeTab.l));
/* 662 */     a(265, "iron_ingot", new Item().c("ingotIron").a(CreativeModeTab.l));
/* 663 */     a(266, "gold_ingot", new Item().c("ingotGold").a(CreativeModeTab.l));
/* 664 */     a(267, "iron_sword", new ItemSword(EnumToolMaterial.IRON).c("swordIron"));
/* 665 */     a(268, "wooden_sword", new ItemSword(EnumToolMaterial.WOOD).c("swordWood"));
/* 666 */     a(269, "wooden_shovel", new ItemSpade(EnumToolMaterial.WOOD).c("shovelWood"));
/* 667 */     a(270, "wooden_pickaxe", new ItemPickaxe(EnumToolMaterial.WOOD).c("pickaxeWood"));
/* 668 */     a(271, "wooden_axe", new ItemAxe(EnumToolMaterial.WOOD).c("hatchetWood"));
/* 669 */     a(272, "stone_sword", new ItemSword(EnumToolMaterial.STONE).c("swordStone"));
/* 670 */     a(273, "stone_shovel", new ItemSpade(EnumToolMaterial.STONE).c("shovelStone"));
/* 671 */     a(274, "stone_pickaxe", new ItemPickaxe(EnumToolMaterial.STONE).c("pickaxeStone"));
/* 672 */     a(275, "stone_axe", new ItemAxe(EnumToolMaterial.STONE).c("hatchetStone"));
/* 673 */     a(276, "diamond_sword", new ItemSword(EnumToolMaterial.EMERALD).c("swordDiamond"));
/* 674 */     a(277, "diamond_shovel", new ItemSpade(EnumToolMaterial.EMERALD).c("shovelDiamond"));
/* 675 */     a(278, "diamond_pickaxe", new ItemPickaxe(EnumToolMaterial.EMERALD).c("pickaxeDiamond"));
/* 676 */     a(279, "diamond_axe", new ItemAxe(EnumToolMaterial.EMERALD).c("hatchetDiamond"));
/* 677 */     a(280, "stick", new Item().n().c("stick").a(CreativeModeTab.l));
/* 678 */     a(281, "bowl", new Item().c("bowl").a(CreativeModeTab.l));
/* 679 */     a(282, "mushroom_stew", new ItemSoup(6).c("mushroomStew"));
/* 680 */     a(283, "golden_sword", new ItemSword(EnumToolMaterial.GOLD).c("swordGold"));
/* 681 */     a(284, "golden_shovel", new ItemSpade(EnumToolMaterial.GOLD).c("shovelGold"));
/* 682 */     a(285, "golden_pickaxe", new ItemPickaxe(EnumToolMaterial.GOLD).c("pickaxeGold"));
/* 683 */     a(286, "golden_axe", new ItemAxe(EnumToolMaterial.GOLD).c("hatchetGold"));
/* 684 */     a(287, "string", new ItemReed(Blocks.TRIPWIRE).c("string").a(CreativeModeTab.l));
/* 685 */     a(288, "feather", new Item().c("feather").a(CreativeModeTab.l));
/* 686 */     a(289, "gunpowder", new Item().c("sulphur").e(PotionBrewer.k).a(CreativeModeTab.l));
/* 687 */     a(290, "wooden_hoe", new ItemHoe(EnumToolMaterial.WOOD).c("hoeWood"));
/* 688 */     a(291, "stone_hoe", new ItemHoe(EnumToolMaterial.STONE).c("hoeStone"));
/* 689 */     a(292, "iron_hoe", new ItemHoe(EnumToolMaterial.IRON).c("hoeIron"));
/* 690 */     a(293, "diamond_hoe", new ItemHoe(EnumToolMaterial.EMERALD).c("hoeDiamond"));
/* 691 */     a(294, "golden_hoe", new ItemHoe(EnumToolMaterial.GOLD).c("hoeGold"));
/* 692 */     a(295, "wheat_seeds", new ItemSeeds(Blocks.WHEAT, Blocks.FARMLAND).c("seeds"));
/* 693 */     a(296, "wheat", new Item().c("wheat").a(CreativeModeTab.l));
/* 694 */     a(297, "bread", new ItemFood(5, 0.6F, false).c("bread"));
/* 695 */     a(298, "leather_helmet", new ItemArmor(ItemArmor.EnumArmorMaterial.LEATHER, 0, 0).c("helmetCloth"));
/* 696 */     a(299, "leather_chestplate", new ItemArmor(ItemArmor.EnumArmorMaterial.LEATHER, 0, 1).c("chestplateCloth"));
/* 697 */     a(300, "leather_leggings", new ItemArmor(ItemArmor.EnumArmorMaterial.LEATHER, 0, 2).c("leggingsCloth"));
/* 698 */     a(301, "leather_boots", new ItemArmor(ItemArmor.EnumArmorMaterial.LEATHER, 0, 3).c("bootsCloth"));
/* 699 */     a(302, "chainmail_helmet", new ItemArmor(ItemArmor.EnumArmorMaterial.CHAIN, 1, 0).c("helmetChain"));
/* 700 */     a(303, "chainmail_chestplate", new ItemArmor(ItemArmor.EnumArmorMaterial.CHAIN, 1, 1).c("chestplateChain"));
/* 701 */     a(304, "chainmail_leggings", new ItemArmor(ItemArmor.EnumArmorMaterial.CHAIN, 1, 2).c("leggingsChain"));
/* 702 */     a(305, "chainmail_boots", new ItemArmor(ItemArmor.EnumArmorMaterial.CHAIN, 1, 3).c("bootsChain"));
/* 703 */     a(306, "iron_helmet", new ItemArmor(ItemArmor.EnumArmorMaterial.IRON, 2, 0).c("helmetIron"));
/* 704 */     a(307, "iron_chestplate", new ItemArmor(ItemArmor.EnumArmorMaterial.IRON, 2, 1).c("chestplateIron"));
/* 705 */     a(308, "iron_leggings", new ItemArmor(ItemArmor.EnumArmorMaterial.IRON, 2, 2).c("leggingsIron"));
/* 706 */     a(309, "iron_boots", new ItemArmor(ItemArmor.EnumArmorMaterial.IRON, 2, 3).c("bootsIron"));
/* 707 */     a(310, "diamond_helmet", new ItemArmor(ItemArmor.EnumArmorMaterial.DIAMOND, 3, 0).c("helmetDiamond"));
/* 708 */     a(311, "diamond_chestplate", new ItemArmor(ItemArmor.EnumArmorMaterial.DIAMOND, 3, 1).c("chestplateDiamond"));
/* 709 */     a(312, "diamond_leggings", new ItemArmor(ItemArmor.EnumArmorMaterial.DIAMOND, 3, 2).c("leggingsDiamond"));
/* 710 */     a(313, "diamond_boots", new ItemArmor(ItemArmor.EnumArmorMaterial.DIAMOND, 3, 3).c("bootsDiamond"));
/* 711 */     a(314, "golden_helmet", new ItemArmor(ItemArmor.EnumArmorMaterial.GOLD, 4, 0).c("helmetGold"));
/* 712 */     a(315, "golden_chestplate", new ItemArmor(ItemArmor.EnumArmorMaterial.GOLD, 4, 1).c("chestplateGold"));
/* 713 */     a(316, "golden_leggings", new ItemArmor(ItemArmor.EnumArmorMaterial.GOLD, 4, 2).c("leggingsGold"));
/* 714 */     a(317, "golden_boots", new ItemArmor(ItemArmor.EnumArmorMaterial.GOLD, 4, 3).c("bootsGold"));
/* 715 */     a(318, "flint", new Item().c("flint").a(CreativeModeTab.l));
/* 716 */     a(319, "porkchop", new ItemFood(3, 0.3F, true).c("porkchopRaw"));
/* 717 */     a(320, "cooked_porkchop", new ItemFood(8, 0.8F, true).c("porkchopCooked"));
/* 718 */     a(321, "painting", new ItemHanging(EntityPainting.class).c("painting"));
/* 719 */     a(322, "golden_apple", new ItemGoldenApple(4, 1.2F, false).h().a(MobEffectList.REGENERATION.id, 5, 1, 1.0F).c("appleGold"));
/* 720 */     a(323, "sign", new ItemSign().c("sign"));
/* 721 */     a(324, "wooden_door", new ItemDoor(Blocks.WOODEN_DOOR).c("doorOak"));
/* 722 */     Item localItem = new ItemBucket(Blocks.AIR).c("bucket").c(16);
/* 723 */     a(325, "bucket", localItem);
/* 724 */     a(326, "water_bucket", new ItemBucket(Blocks.FLOWING_WATER).c("bucketWater").c(localItem));
/* 725 */     a(327, "lava_bucket", new ItemBucket(Blocks.FLOWING_LAVA).c("bucketLava").c(localItem));
/* 726 */     a(328, "minecart", new ItemMinecart(EntityMinecartAbstract.EnumMinecartType.RIDEABLE).c("minecart"));
/* 727 */     a(329, "saddle", new ItemSaddle().c("saddle"));
/* 728 */     a(330, "iron_door", new ItemDoor(Blocks.IRON_DOOR).c("doorIron"));
/* 729 */     a(331, "redstone", new ItemRedstone().c("redstone").e(PotionBrewer.i));
/* 730 */     a(332, "snowball", new ItemSnowball().c("snowball"));
/* 731 */     a(333, "boat", new ItemBoat().c("boat"));
/* 732 */     a(334, "leather", new Item().c("leather").a(CreativeModeTab.l));
/* 733 */     a(335, "milk_bucket", new ItemMilkBucket().c("milk").c(localItem));
/* 734 */     a(336, "brick", new Item().c("brick").a(CreativeModeTab.l));
/* 735 */     a(337, "clay_ball", new Item().c("clay").a(CreativeModeTab.l));
/* 736 */     a(338, "reeds", new ItemReed(Blocks.REEDS).c("reeds").a(CreativeModeTab.l));
/* 737 */     a(339, "paper", new Item().c("paper").a(CreativeModeTab.f));
/* 738 */     a(340, "book", new ItemBook().c("book").a(CreativeModeTab.f));
/* 739 */     a(341, "slime_ball", new Item().c("slimeball").a(CreativeModeTab.f));
/* 740 */     a(342, "chest_minecart", new ItemMinecart(EntityMinecartAbstract.EnumMinecartType.CHEST).c("minecartChest"));
/* 741 */     a(343, "furnace_minecart", new ItemMinecart(EntityMinecartAbstract.EnumMinecartType.FURNACE).c("minecartFurnace"));
/* 742 */     a(344, "egg", new ItemEgg().c("egg"));
/* 743 */     a(345, "compass", new Item().c("compass").a(CreativeModeTab.i));
/* 744 */     a(346, "fishing_rod", new ItemFishingRod().c("fishingRod"));
/* 745 */     a(347, "clock", new Item().c("clock").a(CreativeModeTab.i));
/* 746 */     a(348, "glowstone_dust", new Item().c("yellowDust").e(PotionBrewer.j).a(CreativeModeTab.l));
/* 747 */     a(349, "fish", new ItemFish(false).c("fish").a(true));
/* 748 */     a(350, "cooked_fish", new ItemFish(true).c("fish").a(true));
/* 749 */     a(351, "dye", new ItemDye().c("dyePowder"));
/* 750 */     a(352, "bone", new Item().c("bone").n().a(CreativeModeTab.f));
/* 751 */     a(353, "sugar", new Item().c("sugar").e(PotionBrewer.b).a(CreativeModeTab.l));
/* 752 */     a(354, "cake", new ItemReed(Blocks.CAKE).c(1).c("cake").a(CreativeModeTab.h));
/* 753 */     a(355, "bed", new ItemBed().c(1).c("bed"));
/* 754 */     a(356, "repeater", new ItemReed(Blocks.UNPOWERED_REPEATER).c("diode").a(CreativeModeTab.d));
/* 755 */     a(357, "cookie", new ItemFood(2, 0.1F, false).c("cookie"));
/* 756 */     a(358, "filled_map", new ItemWorldMap().c("map"));
/* 757 */     a(359, "shears", new ItemShears().c("shears"));
/* 758 */     a(360, "melon", new ItemFood(2, 0.3F, false).c("melon"));
/* 759 */     a(361, "pumpkin_seeds", new ItemSeeds(Blocks.PUMPKIN_STEM, Blocks.FARMLAND).c("seeds_pumpkin"));
/* 760 */     a(362, "melon_seeds", new ItemSeeds(Blocks.MELON_STEM, Blocks.FARMLAND).c("seeds_melon"));
/* 761 */     a(363, "beef", new ItemFood(3, 0.3F, true).c("beefRaw"));
/* 762 */     a(364, "cooked_beef", new ItemFood(8, 0.8F, true).c("beefCooked"));
/* 763 */     a(365, "chicken", new ItemFood(2, 0.3F, true).a(MobEffectList.HUNGER.id, 30, 0, 0.3F).c("chickenRaw"));
/* 764 */     a(366, "cooked_chicken", new ItemFood(6, 0.6F, true).c("chickenCooked"));
/* 765 */     a(367, "rotten_flesh", new ItemFood(4, 0.1F, true).a(MobEffectList.HUNGER.id, 30, 0, 0.8F).c("rottenFlesh"));
/* 766 */     a(368, "ender_pearl", new ItemEnderPearl().c("enderPearl"));
/* 767 */     a(369, "blaze_rod", new Item().c("blazeRod").a(CreativeModeTab.l).n());
/* 768 */     a(370, "ghast_tear", new Item().c("ghastTear").e(PotionBrewer.c).a(CreativeModeTab.k));
/* 769 */     a(371, "gold_nugget", new Item().c("goldNugget").a(CreativeModeTab.l));
/* 770 */     a(372, "nether_wart", new ItemSeeds(Blocks.NETHER_WART, Blocks.SOUL_SAND).c("netherStalkSeeds").e("+4"));
/* 771 */     a(373, "potion", new ItemPotion().c("potion"));
/* 772 */     a(374, "glass_bottle", new ItemGlassBottle().c("glassBottle"));
/* 773 */     a(375, "spider_eye", new ItemFood(2, 0.8F, false).a(MobEffectList.POISON.id, 5, 0, 1.0F).c("spiderEye").e(PotionBrewer.d));
/* 774 */     a(376, "fermented_spider_eye", new Item().c("fermentedSpiderEye").e(PotionBrewer.e).a(CreativeModeTab.k));
/* 775 */     a(377, "blaze_powder", new Item().c("blazePowder").e(PotionBrewer.g).a(CreativeModeTab.k));
/* 776 */     a(378, "magma_cream", new Item().c("magmaCream").e(PotionBrewer.h).a(CreativeModeTab.k));
/* 777 */     a(379, "brewing_stand", new ItemReed(Blocks.BREWING_STAND).c("brewingStand").a(CreativeModeTab.k));
/* 778 */     a(380, "cauldron", new ItemReed(Blocks.cauldron).c("cauldron").a(CreativeModeTab.k));
/* 779 */     a(381, "ender_eye", new ItemEnderEye().c("eyeOfEnder"));
/* 780 */     a(382, "speckled_melon", new Item().c("speckledMelon").e(PotionBrewer.f).a(CreativeModeTab.k));
/* 781 */     a(383, "spawn_egg", new ItemMonsterEgg().c("monsterPlacer"));
/* 782 */     a(384, "experience_bottle", new ItemExpBottle().c("expBottle"));
/* 783 */     a(385, "fire_charge", new ItemFireball().c("fireball"));
/* 784 */     a(386, "writable_book", new ItemBookAndQuill().c("writingBook").a(CreativeModeTab.f));
/* 785 */     a(387, "written_book", new ItemWrittenBook().c("writtenBook").c(16));
/* 786 */     a(388, "emerald", new Item().c("emerald").a(CreativeModeTab.l));
/* 787 */     a(389, "item_frame", new ItemHanging(EntityItemFrame.class).c("frame"));
/* 788 */     a(390, "flower_pot", new ItemReed(Blocks.FLOWER_POT).c("flowerPot").a(CreativeModeTab.c));
/* 789 */     a(391, "carrot", new ItemSeedFood(3, 0.6F, Blocks.CARROTS, Blocks.FARMLAND).c("carrots"));
/* 790 */     a(392, "potato", new ItemSeedFood(1, 0.3F, Blocks.POTATOES, Blocks.FARMLAND).c("potato"));
/* 791 */     a(393, "baked_potato", new ItemFood(5, 0.6F, false).c("potatoBaked"));
/* 792 */     a(394, "poisonous_potato", new ItemFood(2, 0.3F, false).a(MobEffectList.POISON.id, 5, 0, 0.6F).c("potatoPoisonous"));
/* 793 */     a(395, "map", new ItemMapEmpty().c("emptyMap"));
/* 794 */     a(396, "golden_carrot", new ItemFood(6, 1.2F, false).c("carrotGolden").e(PotionBrewer.l).a(CreativeModeTab.k));
/* 795 */     a(397, "skull", new ItemSkull().c("skull"));
/* 796 */     a(398, "carrot_on_a_stick", new ItemCarrotStick().c("carrotOnAStick"));
/* 797 */     a(399, "nether_star", new ItemNetherStar().c("netherStar").a(CreativeModeTab.l));
/* 798 */     a(400, "pumpkin_pie", new ItemFood(8, 0.3F, false).c("pumpkinPie").a(CreativeModeTab.h));
/* 799 */     a(401, "fireworks", new ItemFireworks().c("fireworks"));
/* 800 */     a(402, "firework_charge", new ItemFireworksCharge().c("fireworksCharge").a(CreativeModeTab.f));
/* 801 */     a(403, "enchanted_book", new ItemEnchantedBook().c(1).c("enchantedBook"));
/* 802 */     a(404, "comparator", new ItemReed(Blocks.UNPOWERED_COMPARATOR).c("comparator").a(CreativeModeTab.d));
/* 803 */     a(405, "netherbrick", new Item().c("netherbrick").a(CreativeModeTab.l));
/* 804 */     a(406, "quartz", new Item().c("netherquartz").a(CreativeModeTab.l));
/* 805 */     a(407, "tnt_minecart", new ItemMinecart(EntityMinecartAbstract.EnumMinecartType.TNT).c("minecartTnt"));
/* 806 */     a(408, "hopper_minecart", new ItemMinecart(EntityMinecartAbstract.EnumMinecartType.HOPPER).c("minecartHopper"));
/* 807 */     a(409, "prismarine_shard", new Item().c("prismarineShard").a(CreativeModeTab.l));
/* 808 */     a(410, "prismarine_crystals", new Item().c("prismarineCrystals").a(CreativeModeTab.l));
/* 809 */     a(411, "rabbit", new ItemFood(3, 0.3F, true).c("rabbitRaw"));
/* 810 */     a(412, "cooked_rabbit", new ItemFood(5, 0.6F, true).c("rabbitCooked"));
/* 811 */     a(413, "rabbit_stew", new ItemSoup(10).c("rabbitStew"));
/* 812 */     a(414, "rabbit_foot", new Item().c("rabbitFoot").e(PotionBrewer.n).a(CreativeModeTab.k));
/* 813 */     a(415, "rabbit_hide", new Item().c("rabbitHide").a(CreativeModeTab.l));
/* 814 */     a(416, "armor_stand", new ItemArmorStand().c("armorStand").c(16));
/* 815 */     a(417, "iron_horse_armor", new Item().c("horsearmormetal").c(1).a(CreativeModeTab.f));
/* 816 */     a(418, "golden_horse_armor", new Item().c("horsearmorgold").c(1).a(CreativeModeTab.f));
/* 817 */     a(419, "diamond_horse_armor", new Item().c("horsearmordiamond").c(1).a(CreativeModeTab.f));
/* 818 */     a(420, "lead", new ItemLeash().c("leash"));
/* 819 */     a(421, "name_tag", new ItemNameTag().c("nameTag"));
/* 820 */     a(422, "command_block_minecart", new ItemMinecart(EntityMinecartAbstract.EnumMinecartType.COMMAND_BLOCK).c("minecartCommandBlock").a(null));
/* 821 */     a(423, "mutton", new ItemFood(2, 0.3F, true).c("muttonRaw"));
/* 822 */     a(424, "cooked_mutton", new ItemFood(6, 0.8F, true).c("muttonCooked"));
/* 823 */     a(425, "banner", new ItemBanner().b("banner"));
/* 824 */     a(427, "spruce_door", new ItemDoor(Blocks.SPRUCE_DOOR).c("doorSpruce"));
/* 825 */     a(428, "birch_door", new ItemDoor(Blocks.BIRCH_DOOR).c("doorBirch"));
/* 826 */     a(429, "jungle_door", new ItemDoor(Blocks.JUNGLE_DOOR).c("doorJungle"));
/* 827 */     a(430, "acacia_door", new ItemDoor(Blocks.ACACIA_DOOR).c("doorAcacia"));
/* 828 */     a(431, "dark_oak_door", new ItemDoor(Blocks.DARK_OAK_DOOR).c("doorDarkOak"));
/*     */     
/*     */ 
/* 831 */     a(2256, "record_13", new ItemRecord("13").c("record"));
/* 832 */     a(2257, "record_cat", new ItemRecord("cat").c("record"));
/* 833 */     a(2258, "record_blocks", new ItemRecord("blocks").c("record"));
/* 834 */     a(2259, "record_chirp", new ItemRecord("chirp").c("record"));
/* 835 */     a(2260, "record_far", new ItemRecord("far").c("record"));
/* 836 */     a(2261, "record_mall", new ItemRecord("mall").c("record"));
/* 837 */     a(2262, "record_mellohi", new ItemRecord("mellohi").c("record"));
/* 838 */     a(2263, "record_stal", new ItemRecord("stal").c("record"));
/* 839 */     a(2264, "record_strad", new ItemRecord("strad").c("record"));
/* 840 */     a(2265, "record_ward", new ItemRecord("ward").c("record"));
/* 841 */     a(2266, "record_11", new ItemRecord("11").c("record"));
/* 842 */     a(2267, "record_wait", new ItemRecord("wait").c("record"));
/*     */   }
/*     */   
/*     */   private static void c(Block paramBlock) {
/* 846 */     a(paramBlock, new ItemBlock(paramBlock));
/*     */   }
/*     */   
/*     */   protected static void a(Block paramBlock, Item paramItem) {
/* 850 */     a(Block.getId(paramBlock), (MinecraftKey)Block.REGISTRY.c(paramBlock), paramItem);
/* 851 */     a.put(paramBlock, paramItem);
/*     */   }
/*     */   
/*     */   private static void a(int paramInt, String paramString, Item paramItem) {
/* 855 */     a(paramInt, new MinecraftKey(paramString), paramItem);
/*     */   }
/*     */   
/*     */   private static void a(int paramInt, MinecraftKey paramMinecraftKey, Item paramItem) {
/* 859 */     REGISTRY.a(paramInt, paramMinecraftKey, paramItem);
/*     */   }
/*     */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_8_R3\Item.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */
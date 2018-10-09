/*     */ package net.minecraft.server.v1_8_R3;
/*     */ 
/*     */ import com.google.common.collect.Multimap;
/*     */ import java.util.ArrayList;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import java.util.Map.Entry;
/*     */ import java.util.Random;
/*     */ import org.bukkit.Location;
/*     */ import org.bukkit.block.BlockState;
/*     */ import org.bukkit.craftbukkit.v1_8_R3.block.CraftBlockState;
/*     */ import org.bukkit.craftbukkit.v1_8_R3.event.CraftEventFactory;
/*     */ import org.bukkit.craftbukkit.v1_8_R3.inventory.CraftItemStack;
/*     */ import org.bukkit.craftbukkit.v1_8_R3.util.CraftMagicNumbers;
/*     */ import org.bukkit.entity.Player;
/*     */ import org.bukkit.event.block.BlockPlaceEvent;
/*     */ import org.bukkit.event.player.PlayerItemDamageEvent;
/*     */ import org.bukkit.event.world.StructureGrowEvent;
/*     */ import org.bukkit.plugin.PluginManager;
/*     */ 
/*     */ public final class ItemStack
/*     */ {
/*  23 */   public static final java.text.DecimalFormat a = new java.text.DecimalFormat("#.###");
/*     */   public int count;
/*     */   public int c;
/*     */   private Item item;
/*     */   private NBTTagCompound tag;
/*     */   private int damage;
/*     */   private EntityItemFrame g;
/*     */   private Block h;
/*     */   private boolean i;
/*     */   private Block j;
/*     */   private boolean k;
/*     */   
/*     */   public ItemStack(Block block) {
/*  36 */     this(block, 1);
/*     */   }
/*     */   
/*     */   public ItemStack(Block block, int i) {
/*  40 */     this(block, i, 0);
/*     */   }
/*     */   
/*     */   public ItemStack(Block block, int i, int j) {
/*  44 */     this(Item.getItemOf(block), i, j);
/*     */   }
/*     */   
/*     */   public ItemStack(Item item) {
/*  48 */     this(item, 1);
/*     */   }
/*     */   
/*     */   public ItemStack(Item item, int i) {
/*  52 */     this(item, i, 0);
/*     */   }
/*     */   
/*     */   public ItemStack(Item item, int i, int j) {
/*  56 */     this.h = null;
/*  57 */     this.i = false;
/*  58 */     this.j = null;
/*  59 */     this.k = false;
/*  60 */     this.item = item;
/*  61 */     this.count = i;
/*     */     
/*     */ 
/*  64 */     setData(j);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public static ItemStack createStack(NBTTagCompound nbttagcompound)
/*     */   {
/*  74 */     ItemStack itemstack = new ItemStack();
/*     */     
/*  76 */     itemstack.c(nbttagcompound);
/*  77 */     return itemstack.getItem() != null ? itemstack : null;
/*     */   }
/*     */   
/*     */   private ItemStack() {
/*  81 */     this.h = null;
/*  82 */     this.i = false;
/*  83 */     this.j = null;
/*  84 */     this.k = false;
/*     */   }
/*     */   
/*     */   public ItemStack cloneAndSubtract(int i) {
/*  88 */     ItemStack itemstack = new ItemStack(this.item, i, this.damage);
/*     */     
/*  90 */     if (this.tag != null) {
/*  91 */       itemstack.tag = ((NBTTagCompound)this.tag.clone());
/*     */     }
/*     */     
/*  94 */     this.count -= i;
/*  95 */     return itemstack;
/*     */   }
/*     */   
/*     */   public Item getItem() {
/*  99 */     return this.item;
/*     */   }
/*     */   
/*     */   public boolean placeItem(EntityHuman entityhuman, World world, BlockPosition blockposition, EnumDirection enumdirection, float f, float f1, float f2)
/*     */   {
/* 104 */     int data = getData();
/* 105 */     int count = this.count;
/*     */     
/* 107 */     if (!(getItem() instanceof ItemBucket)) {
/* 108 */       world.captureBlockStates = true;
/*     */       
/* 110 */       if (((getItem() instanceof ItemDye)) && (getData() == 15)) {
/* 111 */         Block block = world.getType(blockposition).getBlock();
/* 112 */         if ((block == Blocks.SAPLING) || ((block instanceof BlockMushroom))) {
/* 113 */           world.captureTreeGeneration = true;
/*     */         }
/*     */       }
/*     */     }
/* 117 */     boolean flag = getItem().interactWith(this, entityhuman, world, blockposition, enumdirection, f, f1, f2);
/* 118 */     int newData = getData();
/* 119 */     int newCount = this.count;
/* 120 */     this.count = count;
/* 121 */     setData(data);
/* 122 */     world.captureBlockStates = false;
/* 123 */     StructureGrowEvent event; if ((flag) && (world.captureTreeGeneration) && (world.capturedBlockStates.size() > 0)) {
/* 124 */       world.captureTreeGeneration = false;
/* 125 */       Location location = new Location(world.getWorld(), blockposition.getX(), blockposition.getY(), blockposition.getZ());
/* 126 */       org.bukkit.TreeType treeType = BlockSapling.treeType;
/* 127 */       BlockSapling.treeType = null;
/* 128 */       List<BlockState> blocks = (List)world.capturedBlockStates.clone();
/* 129 */       world.capturedBlockStates.clear();
/* 130 */       event = null;
/* 131 */       if (treeType != null) {
/* 132 */         boolean isBonemeal = (getItem() == Items.DYE) && (data == 15);
/* 133 */         event = new StructureGrowEvent(location, treeType, isBonemeal, (Player)entityhuman.getBukkitEntity(), blocks);
/* 134 */         org.bukkit.Bukkit.getPluginManager().callEvent(event);
/*     */       }
/* 136 */       if ((event == null) || (!event.isCancelled()))
/*     */       {
/* 138 */         if ((this.count == count) && (getData() == data)) {
/* 139 */           setData(newData);
/* 140 */           this.count = newCount;
/*     */         }
/* 142 */         for (BlockState blockstate : blocks) {
/* 143 */           blockstate.update(true);
/*     */         }
/*     */       }
/*     */       
/* 147 */       return flag;
/*     */     }
/* 149 */     world.captureTreeGeneration = false;
/*     */     
/* 151 */     if (flag) {
/* 152 */       BlockPlaceEvent placeEvent = null;
/* 153 */       List<BlockState> blocks = (List)world.capturedBlockStates.clone();
/* 154 */       world.capturedBlockStates.clear();
/* 155 */       if (blocks.size() > 1) {
/* 156 */         placeEvent = CraftEventFactory.callBlockMultiPlaceEvent(world, entityhuman, blocks, blockposition.getX(), blockposition.getY(), blockposition.getZ());
/* 157 */       } else if (blocks.size() == 1) {
/* 158 */         placeEvent = CraftEventFactory.callBlockPlaceEvent(world, entityhuman, (BlockState)blocks.get(0), blockposition.getX(), blockposition.getY(), blockposition.getZ());
/*     */       }
/*     */       
/* 161 */       if ((placeEvent != null) && ((placeEvent.isCancelled()) || (!placeEvent.canBuild()))) {
/* 162 */         flag = false;
/*     */         
/* 164 */         for (BlockState blockstate : blocks) {
/* 165 */           blockstate.update(true, false);
/*     */         }
/*     */       }
/*     */       else {
/* 169 */         if ((this.count == count) && (getData() == data)) {
/* 170 */           setData(newData);
/* 171 */           this.count = newCount;
/*     */         }
/* 173 */         for (BlockState blockstate : blocks) {
/* 174 */           int x = blockstate.getX();
/* 175 */           int y = blockstate.getY();
/* 176 */           int z = blockstate.getZ();
/* 177 */           int updateFlag = ((CraftBlockState)blockstate).getFlag();
/* 178 */           org.bukkit.Material mat = blockstate.getType();
/* 179 */           Block oldBlock = CraftMagicNumbers.getBlock(mat);
/* 180 */           BlockPosition newblockposition = new BlockPosition(x, y, z);
/* 181 */           IBlockData block = world.getType(newblockposition);
/*     */           
/* 183 */           if (!(block instanceof BlockContainer)) {
/* 184 */             block.getBlock().onPlace(world, newblockposition, block);
/*     */           }
/*     */           
/* 187 */           world.notifyAndUpdatePhysics(newblockposition, null, oldBlock, block.getBlock(), updateFlag);
/*     */         }
/*     */         
/* 190 */         for (Map.Entry<BlockPosition, TileEntity> e : world.capturedTileEntities.entrySet()) {
/* 191 */           world.setTileEntity((BlockPosition)e.getKey(), (TileEntity)e.getValue());
/*     */         }
/*     */         
/*     */ 
/* 195 */         if ((getItem() instanceof ItemRecord)) {
/* 196 */           ((BlockJukeBox)Blocks.JUKEBOX).a(world, blockposition, world.getType(blockposition), this);
/* 197 */           world.a(null, 1005, blockposition, Item.getId(getItem()));
/* 198 */           this.count -= 1;
/* 199 */           entityhuman.b(StatisticList.X);
/*     */         }
/*     */         
/* 202 */         if (getItem() == Items.SKULL) {
/* 203 */           BlockPosition bp = blockposition;
/* 204 */           if (!world.getType(blockposition).getBlock().a(world, blockposition)) {
/* 205 */             if (!world.getType(blockposition).getBlock().getMaterial().isBuildable()) {
/* 206 */               bp = null;
/*     */             } else {
/* 208 */               bp = bp.shift(enumdirection);
/*     */             }
/*     */           }
/* 211 */           if (bp != null) {
/* 212 */             TileEntity te = world.getTileEntity(bp);
/* 213 */             if ((te instanceof TileEntitySkull)) {
/* 214 */               Blocks.SKULL.a(world, bp, (TileEntitySkull)te);
/*     */             }
/*     */           }
/*     */         }
/*     */         
/* 219 */         entityhuman.b(StatisticList.USE_ITEM_COUNT[Item.getId(this.item)]);
/*     */       }
/*     */     }
/* 222 */     world.capturedTileEntities.clear();
/* 223 */     world.capturedBlockStates.clear();
/*     */     
/*     */ 
/* 226 */     return flag;
/*     */   }
/*     */   
/*     */   public float a(Block block) {
/* 230 */     return getItem().getDestroySpeed(this, block);
/*     */   }
/*     */   
/*     */   public ItemStack a(World world, EntityHuman entityhuman) {
/* 234 */     return getItem().a(this, world, entityhuman);
/*     */   }
/*     */   
/*     */   public ItemStack b(World world, EntityHuman entityhuman) {
/* 238 */     return getItem().b(this, world, entityhuman);
/*     */   }
/*     */   
/*     */   public NBTTagCompound save(NBTTagCompound nbttagcompound) {
/* 242 */     MinecraftKey minecraftkey = (MinecraftKey)Item.REGISTRY.c(this.item);
/*     */     
/* 244 */     nbttagcompound.setString("id", minecraftkey == null ? "minecraft:air" : minecraftkey.toString());
/* 245 */     nbttagcompound.setByte("Count", (byte)this.count);
/* 246 */     nbttagcompound.setShort("Damage", (short)this.damage);
/* 247 */     if (this.tag != null) {
/* 248 */       nbttagcompound.set("tag", this.tag.clone());
/*     */     }
/*     */     
/* 251 */     return nbttagcompound;
/*     */   }
/*     */   
/*     */   public void c(NBTTagCompound nbttagcompound) {
/* 255 */     if (nbttagcompound.hasKeyOfType("id", 8)) {
/* 256 */       this.item = Item.d(nbttagcompound.getString("id"));
/*     */     } else {
/* 258 */       this.item = Item.getById(nbttagcompound.getShort("id"));
/*     */     }
/*     */     
/* 261 */     this.count = nbttagcompound.getByte("Count");
/*     */     
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/* 268 */     setData(nbttagcompound.getShort("Damage"));
/*     */     
/*     */ 
/* 271 */     if (nbttagcompound.hasKeyOfType("tag", 10))
/*     */     {
/* 273 */       this.tag = ((NBTTagCompound)nbttagcompound.getCompound("tag").clone());
/* 274 */       if (this.item != null) {
/* 275 */         this.item.a(this.tag);
/*     */       }
/*     */     }
/*     */   }
/*     */   
/*     */   public int getMaxStackSize()
/*     */   {
/* 282 */     return getItem().getMaxStackSize();
/*     */   }
/*     */   
/*     */   public boolean isStackable() {
/* 286 */     return (getMaxStackSize() > 1) && ((!e()) || (!g()));
/*     */   }
/*     */   
/*     */   public boolean e()
/*     */   {
/* 291 */     if (this.item.getMaxDurability() <= 0)
/*     */     {
/* 293 */       return false;
/*     */     }
/* 295 */     return (!hasTag()) || (!getTag().getBoolean("Unbreakable"));
/*     */   }
/*     */   
/*     */   public boolean usesData()
/*     */   {
/* 300 */     return this.item.k();
/*     */   }
/*     */   
/*     */   public boolean g() {
/* 304 */     return (e()) && (this.damage > 0);
/*     */   }
/*     */   
/*     */   public int h() {
/* 308 */     return this.damage;
/*     */   }
/*     */   
/*     */   public int getData() {
/* 312 */     return this.damage;
/*     */   }
/*     */   
/*     */ 
/*     */   public void setData(int i)
/*     */   {
/* 318 */     if (i == 32767) {
/* 319 */       this.damage = i;
/* 320 */       return;
/*     */     }
/*     */     
/*     */ 
/* 324 */     if (CraftMagicNumbers.getBlock(CraftMagicNumbers.getId(getItem())) != Blocks.AIR)
/*     */     {
/* 326 */       if ((!usesData()) && (!getItem().usesDurability())) {
/* 327 */         i = 0;
/*     */       }
/*     */     }
/*     */     
/*     */ 
/* 332 */     if ((CraftMagicNumbers.getBlock(CraftMagicNumbers.getId(getItem())) == Blocks.DOUBLE_PLANT) && ((i > 5) || (i < 0))) {
/* 333 */       i = 0;
/*     */     }
/*     */     
/* 336 */     this.damage = i;
/* 337 */     if (this.damage < -1) {
/* 338 */       this.damage = 0;
/*     */     }
/*     */   }
/*     */   
/*     */   public int j()
/*     */   {
/* 344 */     return this.item.getMaxDurability();
/*     */   }
/*     */   
/*     */   public boolean isDamaged(int i, Random random) {
/* 348 */     return isDamaged(i, random, null);
/*     */   }
/*     */   
/*     */   public boolean isDamaged(int i, Random random, EntityLiving entityliving)
/*     */   {
/* 353 */     if (!e()) {
/* 354 */       return false;
/*     */     }
/* 356 */     if (i > 0) {
/* 357 */       int j = EnchantmentManager.getEnchantmentLevel(Enchantment.DURABILITY.id, this);
/* 358 */       int k = 0;
/*     */       
/* 360 */       for (int l = 0; (j > 0) && (l < i); l++) {
/* 361 */         if (EnchantmentDurability.a(this, j, random)) {
/* 362 */           k++;
/*     */         }
/*     */       }
/*     */       
/* 366 */       i -= k;
/*     */       
/* 368 */       if ((entityliving instanceof EntityPlayer)) {
/* 369 */         CraftItemStack item = CraftItemStack.asCraftMirror(this);
/* 370 */         PlayerItemDamageEvent event = new PlayerItemDamageEvent((Player)entityliving.getBukkitEntity(), item, i);
/* 371 */         org.bukkit.Bukkit.getServer().getPluginManager().callEvent(event);
/* 372 */         if (event.isCancelled()) return false;
/* 373 */         i = event.getDamage();
/*     */       }
/*     */       
/* 376 */       if (i <= 0) {
/* 377 */         return false;
/*     */       }
/*     */     }
/*     */     
/* 381 */     this.damage += i;
/* 382 */     return this.damage > j();
/*     */   }
/*     */   
/*     */   public void damage(int i, EntityLiving entityliving)
/*     */   {
/* 387 */     if (((!(entityliving instanceof EntityHuman)) || (!((EntityHuman)entityliving).abilities.canInstantlyBuild)) && 
/* 388 */       (e()) && 
/* 389 */       (isDamaged(i, entityliving.bc(), entityliving))) {
/* 390 */       entityliving.b(this);
/* 391 */       this.count -= 1;
/* 392 */       if ((entityliving instanceof EntityHuman)) {
/* 393 */         EntityHuman entityhuman = (EntityHuman)entityliving;
/*     */         
/* 395 */         entityhuman.b(StatisticList.BREAK_ITEM_COUNT[Item.getId(this.item)]);
/* 396 */         if ((this.count == 0) && ((getItem() instanceof ItemBow))) {
/* 397 */           entityhuman.ca();
/*     */         }
/*     */       }
/*     */       
/* 401 */       if (this.count < 0) {
/* 402 */         this.count = 0;
/*     */       }
/*     */       
/*     */ 
/* 406 */       if ((this.count == 0) && ((entityliving instanceof EntityHuman))) {
/* 407 */         CraftEventFactory.callPlayerItemBreakEvent((EntityHuman)entityliving, this);
/*     */       }
/*     */       
/*     */ 
/* 411 */       this.damage = 0;
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public void a(EntityLiving entityliving, EntityHuman entityhuman)
/*     */   {
/* 419 */     boolean flag = this.item.a(this, entityliving, entityhuman);
/*     */     
/* 421 */     if (flag) {
/* 422 */       entityhuman.b(StatisticList.USE_ITEM_COUNT[Item.getId(this.item)]);
/*     */     }
/*     */   }
/*     */   
/*     */   public void a(World world, Block block, BlockPosition blockposition, EntityHuman entityhuman)
/*     */   {
/* 428 */     boolean flag = this.item.a(this, world, block, blockposition, entityhuman);
/*     */     
/* 430 */     if (flag) {
/* 431 */       entityhuman.b(StatisticList.USE_ITEM_COUNT[Item.getId(this.item)]);
/*     */     }
/*     */   }
/*     */   
/*     */   public boolean b(Block block)
/*     */   {
/* 437 */     return this.item.canDestroySpecialBlock(block);
/*     */   }
/*     */   
/*     */   public boolean a(EntityHuman entityhuman, EntityLiving entityliving) {
/* 441 */     return this.item.a(this, entityhuman, entityliving);
/*     */   }
/*     */   
/*     */   public ItemStack cloneItemStack() {
/* 445 */     ItemStack itemstack = new ItemStack(this.item, this.count, this.damage);
/*     */     
/* 447 */     if (this.tag != null) {
/* 448 */       itemstack.tag = ((NBTTagCompound)this.tag.clone());
/*     */     }
/*     */     
/* 451 */     return itemstack;
/*     */   }
/*     */   
/*     */   public static boolean equals(ItemStack itemstack, ItemStack itemstack1) {
/* 455 */     return (itemstack == null) && (itemstack1 == null);
/*     */   }
/*     */   
/*     */   public static boolean fastMatches(ItemStack itemstack, ItemStack itemstack1)
/*     */   {
/* 460 */     if ((itemstack == null) && (itemstack1 == null)) {
/* 461 */       return true;
/*     */     }
/* 463 */     if ((itemstack != null) && (itemstack1 != null)) {
/* 464 */       return (itemstack.count == itemstack1.count) && (itemstack.item == itemstack1.item) && (itemstack.damage == itemstack1.damage);
/*     */     }
/* 466 */     return false;
/*     */   }
/*     */   
/*     */   public static boolean matches(ItemStack itemstack, ItemStack itemstack1)
/*     */   {
/* 471 */     return (itemstack == null) && (itemstack1 == null);
/*     */   }
/*     */   
/*     */   private boolean d(ItemStack itemstack) {
/* 475 */     return this.count == itemstack.count;
/*     */   }
/*     */   
/*     */   public static boolean c(ItemStack itemstack, ItemStack itemstack1) {
/* 479 */     return (itemstack == null) && (itemstack1 == null);
/*     */   }
/*     */   
/*     */   public boolean doMaterialsMatch(ItemStack itemstack) {
/* 483 */     return (itemstack != null) && (this.item == itemstack.item) && (this.damage == itemstack.damage);
/*     */   }
/*     */   
/*     */   public String a() {
/* 487 */     return this.item.e_(this);
/*     */   }
/*     */   
/*     */   public static ItemStack b(ItemStack itemstack) {
/* 491 */     return itemstack == null ? null : itemstack.cloneItemStack();
/*     */   }
/*     */   
/*     */   public String toString() {
/* 495 */     return this.count + "x" + this.item.getName() + "@" + this.damage;
/*     */   }
/*     */   
/*     */   public void a(World world, Entity entity, int i, boolean flag) {
/* 499 */     if (this.c > 0) {
/* 500 */       this.c -= 1;
/*     */     }
/*     */     
/* 503 */     this.item.a(this, world, entity, i, flag);
/*     */   }
/*     */   
/*     */   public void a(World world, EntityHuman entityhuman, int i) {
/* 507 */     entityhuman.a(StatisticList.CRAFT_BLOCK_COUNT[Item.getId(this.item)], i);
/* 508 */     this.item.d(this, world, entityhuman);
/*     */   }
/*     */   
/*     */   public int l() {
/* 512 */     return getItem().d(this);
/*     */   }
/*     */   
/*     */   public EnumAnimation m() {
/* 516 */     return getItem().e(this);
/*     */   }
/*     */   
/*     */   public void b(World world, EntityHuman entityhuman, int i) {
/* 520 */     getItem().a(this, world, entityhuman, i);
/*     */   }
/*     */   
/*     */   public boolean hasTag() {
/* 524 */     return this.tag != null;
/*     */   }
/*     */   
/*     */   public NBTTagCompound getTag() {
/* 528 */     return this.tag;
/*     */   }
/*     */   
/*     */   public NBTTagCompound a(String s, boolean flag) {
/* 532 */     if ((this.tag != null) && (this.tag.hasKeyOfType(s, 10)))
/* 533 */       return this.tag.getCompound(s);
/* 534 */     if (flag) {
/* 535 */       NBTTagCompound nbttagcompound = new NBTTagCompound();
/*     */       
/* 537 */       a(s, nbttagcompound);
/* 538 */       return nbttagcompound;
/*     */     }
/* 540 */     return null;
/*     */   }
/*     */   
/*     */   public NBTTagList getEnchantments()
/*     */   {
/* 545 */     return this.tag == null ? null : this.tag.getList("ench", 10);
/*     */   }
/*     */   
/*     */   public void setTag(NBTTagCompound nbttagcompound) {
/* 549 */     this.tag = nbttagcompound;
/*     */   }
/*     */   
/*     */   public String getName() {
/* 553 */     String s = getItem().a(this);
/*     */     
/* 555 */     if ((this.tag != null) && (this.tag.hasKeyOfType("display", 10))) {
/* 556 */       NBTTagCompound nbttagcompound = this.tag.getCompound("display");
/*     */       
/* 558 */       if (nbttagcompound.hasKeyOfType("Name", 8)) {
/* 559 */         s = nbttagcompound.getString("Name");
/*     */       }
/*     */     }
/*     */     
/* 563 */     return s;
/*     */   }
/*     */   
/*     */   public ItemStack c(String s) {
/* 567 */     if (this.tag == null) {
/* 568 */       this.tag = new NBTTagCompound();
/*     */     }
/*     */     
/* 571 */     if (!this.tag.hasKeyOfType("display", 10)) {
/* 572 */       this.tag.set("display", new NBTTagCompound());
/*     */     }
/*     */     
/* 575 */     this.tag.getCompound("display").setString("Name", s);
/* 576 */     return this;
/*     */   }
/*     */   
/*     */   public void r() {
/* 580 */     if ((this.tag != null) && 
/* 581 */       (this.tag.hasKeyOfType("display", 10))) {
/* 582 */       NBTTagCompound nbttagcompound = this.tag.getCompound("display");
/*     */       
/* 584 */       nbttagcompound.remove("Name");
/* 585 */       if (nbttagcompound.isEmpty()) {
/* 586 */         this.tag.remove("display");
/* 587 */         if (this.tag.isEmpty()) {
/* 588 */           setTag(null);
/*     */         }
/*     */       }
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */   public boolean hasName()
/*     */   {
/* 597 */     return !this.tag.hasKeyOfType("display", 10) ? false : this.tag == null ? false : this.tag.getCompound("display").hasKeyOfType("Name", 8);
/*     */   }
/*     */   
/*     */   public EnumItemRarity u() {
/* 601 */     return getItem().g(this);
/*     */   }
/*     */   
/*     */   public boolean v() {
/* 605 */     return getItem().f_(this);
/*     */   }
/*     */   
/*     */   public void addEnchantment(Enchantment enchantment, int i) {
/* 609 */     if (this.tag == null) {
/* 610 */       setTag(new NBTTagCompound());
/*     */     }
/*     */     
/* 613 */     if (!this.tag.hasKeyOfType("ench", 9)) {
/* 614 */       this.tag.set("ench", new NBTTagList());
/*     */     }
/*     */     
/* 617 */     NBTTagList nbttaglist = this.tag.getList("ench", 10);
/* 618 */     NBTTagCompound nbttagcompound = new NBTTagCompound();
/*     */     
/* 620 */     nbttagcompound.setShort("id", (short)enchantment.id);
/* 621 */     nbttagcompound.setShort("lvl", (short)(byte)i);
/* 622 */     nbttaglist.add(nbttagcompound);
/*     */   }
/*     */   
/*     */   public boolean hasEnchantments() {
/* 626 */     return (this.tag != null) && (this.tag.hasKeyOfType("ench", 9));
/*     */   }
/*     */   
/*     */   public void a(String s, NBTBase nbtbase) {
/* 630 */     if (this.tag == null) {
/* 631 */       setTag(new NBTTagCompound());
/*     */     }
/*     */     
/* 634 */     this.tag.set(s, nbtbase);
/*     */   }
/*     */   
/*     */   public boolean x() {
/* 638 */     return getItem().s();
/*     */   }
/*     */   
/*     */   public boolean y() {
/* 642 */     return this.g != null;
/*     */   }
/*     */   
/*     */   public void a(EntityItemFrame entityitemframe) {
/* 646 */     this.g = entityitemframe;
/*     */   }
/*     */   
/*     */   public EntityItemFrame z() {
/* 650 */     return this.g;
/*     */   }
/*     */   
/*     */   public int getRepairCost() {
/* 654 */     return (hasTag()) && (this.tag.hasKeyOfType("RepairCost", 3)) ? this.tag.getInt("RepairCost") : 0;
/*     */   }
/*     */   
/*     */   public void setRepairCost(int i) {
/* 658 */     if (!hasTag()) {
/* 659 */       this.tag = new NBTTagCompound();
/*     */     }
/*     */     
/* 662 */     this.tag.setInt("RepairCost", i);
/*     */   }
/*     */   
/*     */   public Multimap<String, AttributeModifier> B()
/*     */   {
/*     */     Object object;
/* 668 */     if ((hasTag()) && (this.tag.hasKeyOfType("AttributeModifiers", 9))) {
/* 669 */       Object object = com.google.common.collect.HashMultimap.create();
/* 670 */       NBTTagList nbttaglist = this.tag.getList("AttributeModifiers", 10);
/*     */       
/* 672 */       for (int i = 0; i < nbttaglist.size(); i++) {
/* 673 */         NBTTagCompound nbttagcompound = nbttaglist.get(i);
/* 674 */         AttributeModifier attributemodifier = GenericAttributes.a(nbttagcompound);
/*     */         
/* 676 */         if ((attributemodifier != null) && (attributemodifier.a().getLeastSignificantBits() != 0L) && (attributemodifier.a().getMostSignificantBits() != 0L)) {
/* 677 */           ((Multimap)object).put(nbttagcompound.getString("AttributeName"), attributemodifier);
/*     */         }
/*     */       }
/*     */     } else {
/* 681 */       object = getItem().i();
/*     */     }
/*     */     
/* 684 */     return (Multimap)object;
/*     */   }
/*     */   
/*     */   public void setItem(Item item) {
/* 688 */     this.item = item;
/* 689 */     setData(getData());
/*     */   }
/*     */   
/*     */   public IChatBaseComponent C() {
/* 693 */     ChatComponentText chatcomponenttext = new ChatComponentText(getName());
/*     */     
/* 695 */     if (hasName()) {
/* 696 */       chatcomponenttext.getChatModifier().setItalic(Boolean.valueOf(true));
/*     */     }
/*     */     
/* 699 */     IChatBaseComponent ichatbasecomponent = new ChatComponentText("[").addSibling(chatcomponenttext).a("]");
/*     */     
/* 701 */     if (this.item != null) {
/* 702 */       NBTTagCompound nbttagcompound = new NBTTagCompound();
/*     */       
/* 704 */       save(nbttagcompound);
/* 705 */       ichatbasecomponent.getChatModifier().setChatHoverable(new ChatHoverable(ChatHoverable.EnumHoverAction.SHOW_ITEM, new ChatComponentText(nbttagcompound.toString())));
/* 706 */       ichatbasecomponent.getChatModifier().setColor(u().e);
/*     */     }
/*     */     
/* 709 */     return ichatbasecomponent;
/*     */   }
/*     */   
/*     */   public boolean c(Block block) {
/* 713 */     if (block == this.h) {
/* 714 */       return this.i;
/*     */     }
/* 716 */     this.h = block;
/* 717 */     if ((hasTag()) && (this.tag.hasKeyOfType("CanDestroy", 9))) {
/* 718 */       NBTTagList nbttaglist = this.tag.getList("CanDestroy", 8);
/*     */       
/* 720 */       for (int i = 0; i < nbttaglist.size(); i++) {
/* 721 */         Block block1 = Block.getByName(nbttaglist.getString(i));
/*     */         
/* 723 */         if (block1 == block) {
/* 724 */           this.i = true;
/* 725 */           return true;
/*     */         }
/*     */       }
/*     */     }
/*     */     
/* 730 */     this.i = false;
/* 731 */     return false;
/*     */   }
/*     */   
/*     */   public boolean d(Block block)
/*     */   {
/* 736 */     if (block == this.j) {
/* 737 */       return this.k;
/*     */     }
/* 739 */     this.j = block;
/* 740 */     if ((hasTag()) && (this.tag.hasKeyOfType("CanPlaceOn", 9))) {
/* 741 */       NBTTagList nbttaglist = this.tag.getList("CanPlaceOn", 8);
/*     */       
/* 743 */       for (int i = 0; i < nbttaglist.size(); i++) {
/* 744 */         Block block1 = Block.getByName(nbttaglist.getString(i));
/*     */         
/* 746 */         if (block1 == block) {
/* 747 */           this.k = true;
/* 748 */           return true;
/*     */         }
/*     */       }
/*     */     }
/*     */     
/* 753 */     this.k = false;
/* 754 */     return false;
/*     */   }
/*     */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_8_R3\ItemStack.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */
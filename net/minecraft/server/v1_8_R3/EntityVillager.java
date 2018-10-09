/*     */ package net.minecraft.server.v1_8_R3;
/*     */ 
/*     */ import java.util.Iterator;
/*     */ import java.util.Random;
/*     */ import java.util.UUID;
/*     */ 
/*     */ public class EntityVillager extends EntityAgeable implements IMerchant, NPC
/*     */ {
/*     */   private int profession;
/*     */   private boolean bo;
/*     */   private boolean bp;
/*     */   Village village;
/*     */   private EntityHuman tradingPlayer;
/*     */   private MerchantRecipeList br;
/*     */   private int bs;
/*     */   private boolean bt;
/*     */   private boolean bu;
/*     */   private int riches;
/*     */   private String bw;
/*     */   private int bx;
/*     */   private int by;
/*     */   private boolean bz;
/*     */   private boolean bA;
/*     */   public InventorySubcontainer inventory;
/*  25 */   private static final IMerchantRecipeOption[][][][] bC = { { { { new MerchantRecipeOptionBuy(Items.WHEAT, new MerchantOptionRandomRange(18, 22)), new MerchantRecipeOptionBuy(Items.POTATO, new MerchantOptionRandomRange(15, 19)), new MerchantRecipeOptionBuy(Items.CARROT, new MerchantOptionRandomRange(15, 19)), new MerchantRecipeOptionSell(Items.BREAD, new MerchantOptionRandomRange(-4, -2)) }, { new MerchantRecipeOptionBuy(Item.getItemOf(Blocks.PUMPKIN), new MerchantOptionRandomRange(8, 13)), new MerchantRecipeOptionSell(Items.PUMPKIN_PIE, new MerchantOptionRandomRange(-3, -2)) }, { new MerchantRecipeOptionBuy(Item.getItemOf(Blocks.MELON_BLOCK), new MerchantOptionRandomRange(7, 12)), new MerchantRecipeOptionSell(Items.APPLE, new MerchantOptionRandomRange(-5, -7)) }, { new MerchantRecipeOptionSell(Items.COOKIE, new MerchantOptionRandomRange(-6, -10)), new MerchantRecipeOptionSell(Items.CAKE, new MerchantOptionRandomRange(1, 1)) } }, { { new MerchantRecipeOptionBuy(Items.STRING, new MerchantOptionRandomRange(15, 20)), new MerchantRecipeOptionBuy(Items.COAL, new MerchantOptionRandomRange(16, 24)), new MerchantRecipeOptionProcess(Items.FISH, new MerchantOptionRandomRange(6, 6), Items.COOKED_FISH, new MerchantOptionRandomRange(6, 6)) }, { new MerchantRecipeOptionEnchant(Items.FISHING_ROD, new MerchantOptionRandomRange(7, 8)) } }, { { new MerchantRecipeOptionBuy(Item.getItemOf(Blocks.WOOL), new MerchantOptionRandomRange(16, 22)), new MerchantRecipeOptionSell(Items.SHEARS, new MerchantOptionRandomRange(3, 4)) }, { new MerchantRecipeOptionSell(new ItemStack(Item.getItemOf(Blocks.WOOL), 1, 0), new MerchantOptionRandomRange(1, 2)), new MerchantRecipeOptionSell(new ItemStack(Item.getItemOf(Blocks.WOOL), 1, 1), new MerchantOptionRandomRange(1, 2)), new MerchantRecipeOptionSell(new ItemStack(Item.getItemOf(Blocks.WOOL), 1, 2), new MerchantOptionRandomRange(1, 2)), new MerchantRecipeOptionSell(new ItemStack(Item.getItemOf(Blocks.WOOL), 1, 3), new MerchantOptionRandomRange(1, 2)), new MerchantRecipeOptionSell(new ItemStack(Item.getItemOf(Blocks.WOOL), 1, 4), new MerchantOptionRandomRange(1, 2)), new MerchantRecipeOptionSell(new ItemStack(Item.getItemOf(Blocks.WOOL), 1, 5), new MerchantOptionRandomRange(1, 2)), new MerchantRecipeOptionSell(new ItemStack(Item.getItemOf(Blocks.WOOL), 1, 6), new MerchantOptionRandomRange(1, 2)), new MerchantRecipeOptionSell(new ItemStack(Item.getItemOf(Blocks.WOOL), 1, 7), new MerchantOptionRandomRange(1, 2)), new MerchantRecipeOptionSell(new ItemStack(Item.getItemOf(Blocks.WOOL), 1, 8), new MerchantOptionRandomRange(1, 2)), new MerchantRecipeOptionSell(new ItemStack(Item.getItemOf(Blocks.WOOL), 1, 9), new MerchantOptionRandomRange(1, 2)), new MerchantRecipeOptionSell(new ItemStack(Item.getItemOf(Blocks.WOOL), 1, 10), new MerchantOptionRandomRange(1, 2)), new MerchantRecipeOptionSell(new ItemStack(Item.getItemOf(Blocks.WOOL), 1, 11), new MerchantOptionRandomRange(1, 2)), new MerchantRecipeOptionSell(new ItemStack(Item.getItemOf(Blocks.WOOL), 1, 12), new MerchantOptionRandomRange(1, 2)), new MerchantRecipeOptionSell(new ItemStack(Item.getItemOf(Blocks.WOOL), 1, 13), new MerchantOptionRandomRange(1, 2)), new MerchantRecipeOptionSell(new ItemStack(Item.getItemOf(Blocks.WOOL), 1, 14), new MerchantOptionRandomRange(1, 2)), new MerchantRecipeOptionSell(new ItemStack(Item.getItemOf(Blocks.WOOL), 1, 15), new MerchantOptionRandomRange(1, 2)) } }, { { new MerchantRecipeOptionBuy(Items.STRING, new MerchantOptionRandomRange(15, 20)), new MerchantRecipeOptionSell(Items.ARROW, new MerchantOptionRandomRange(-12, -8)) }, { new MerchantRecipeOptionSell(Items.BOW, new MerchantOptionRandomRange(2, 3)), new MerchantRecipeOptionProcess(Item.getItemOf(Blocks.GRAVEL), new MerchantOptionRandomRange(10, 10), Items.FLINT, new MerchantOptionRandomRange(6, 10)) } } }, { { { new MerchantRecipeOptionBuy(Items.PAPER, new MerchantOptionRandomRange(24, 36)), new MerchantRecipeOptionBook() }, { new MerchantRecipeOptionBuy(Items.BOOK, new MerchantOptionRandomRange(8, 10)), new MerchantRecipeOptionSell(Items.COMPASS, new MerchantOptionRandomRange(10, 12)), new MerchantRecipeOptionSell(Item.getItemOf(Blocks.BOOKSHELF), new MerchantOptionRandomRange(3, 4)) }, { new MerchantRecipeOptionBuy(Items.WRITTEN_BOOK, new MerchantOptionRandomRange(2, 2)), new MerchantRecipeOptionSell(Items.CLOCK, new MerchantOptionRandomRange(10, 12)), new MerchantRecipeOptionSell(Item.getItemOf(Blocks.GLASS), new MerchantOptionRandomRange(-5, -3)) }, { new MerchantRecipeOptionBook() }, { new MerchantRecipeOptionBook() }, { new MerchantRecipeOptionSell(Items.NAME_TAG, new MerchantOptionRandomRange(20, 22)) } } }, { { { new MerchantRecipeOptionBuy(Items.ROTTEN_FLESH, new MerchantOptionRandomRange(36, 40)), new MerchantRecipeOptionBuy(Items.GOLD_INGOT, new MerchantOptionRandomRange(8, 10)) }, { new MerchantRecipeOptionSell(Items.REDSTONE, new MerchantOptionRandomRange(-4, -1)), new MerchantRecipeOptionSell(new ItemStack(Items.DYE, 1, EnumColor.BLUE.getInvColorIndex()), new MerchantOptionRandomRange(-2, -1)) }, { new MerchantRecipeOptionSell(Items.ENDER_EYE, new MerchantOptionRandomRange(7, 11)), new MerchantRecipeOptionSell(Item.getItemOf(Blocks.GLOWSTONE), new MerchantOptionRandomRange(-3, -1)) }, { new MerchantRecipeOptionSell(Items.EXPERIENCE_BOTTLE, new MerchantOptionRandomRange(3, 11)) } } }, { { { new MerchantRecipeOptionBuy(Items.COAL, new MerchantOptionRandomRange(16, 24)), new MerchantRecipeOptionSell(Items.IRON_HELMET, new MerchantOptionRandomRange(4, 6)) }, { new MerchantRecipeOptionBuy(Items.IRON_INGOT, new MerchantOptionRandomRange(7, 9)), new MerchantRecipeOptionSell(Items.IRON_CHESTPLATE, new MerchantOptionRandomRange(10, 14)) }, { new MerchantRecipeOptionBuy(Items.DIAMOND, new MerchantOptionRandomRange(3, 4)), new MerchantRecipeOptionEnchant(Items.DIAMOND_CHESTPLATE, new MerchantOptionRandomRange(16, 19)) }, { new MerchantRecipeOptionSell(Items.CHAINMAIL_BOOTS, new MerchantOptionRandomRange(5, 7)), new MerchantRecipeOptionSell(Items.CHAINMAIL_LEGGINGS, new MerchantOptionRandomRange(9, 11)), new MerchantRecipeOptionSell(Items.CHAINMAIL_HELMET, new MerchantOptionRandomRange(5, 7)), new MerchantRecipeOptionSell(Items.CHAINMAIL_CHESTPLATE, new MerchantOptionRandomRange(11, 15)) } }, { { new MerchantRecipeOptionBuy(Items.COAL, new MerchantOptionRandomRange(16, 24)), new MerchantRecipeOptionSell(Items.IRON_AXE, new MerchantOptionRandomRange(6, 8)) }, { new MerchantRecipeOptionBuy(Items.IRON_INGOT, new MerchantOptionRandomRange(7, 9)), new MerchantRecipeOptionEnchant(Items.IRON_SWORD, new MerchantOptionRandomRange(9, 10)) }, { new MerchantRecipeOptionBuy(Items.DIAMOND, new MerchantOptionRandomRange(3, 4)), new MerchantRecipeOptionEnchant(Items.DIAMOND_SWORD, new MerchantOptionRandomRange(12, 15)), new MerchantRecipeOptionEnchant(Items.DIAMOND_AXE, new MerchantOptionRandomRange(9, 12)) } }, { { new MerchantRecipeOptionBuy(Items.COAL, new MerchantOptionRandomRange(16, 24)), new MerchantRecipeOptionEnchant(Items.IRON_SHOVEL, new MerchantOptionRandomRange(5, 7)) }, { new MerchantRecipeOptionBuy(Items.IRON_INGOT, new MerchantOptionRandomRange(7, 9)), new MerchantRecipeOptionEnchant(Items.IRON_PICKAXE, new MerchantOptionRandomRange(9, 11)) }, { new MerchantRecipeOptionBuy(Items.DIAMOND, new MerchantOptionRandomRange(3, 4)), new MerchantRecipeOptionEnchant(Items.DIAMOND_PICKAXE, new MerchantOptionRandomRange(12, 15)) } } }, { { { new MerchantRecipeOptionBuy(Items.PORKCHOP, new MerchantOptionRandomRange(14, 18)), new MerchantRecipeOptionBuy(Items.CHICKEN, new MerchantOptionRandomRange(14, 18)) }, { new MerchantRecipeOptionBuy(Items.COAL, new MerchantOptionRandomRange(16, 24)), new MerchantRecipeOptionSell(Items.COOKED_PORKCHOP, new MerchantOptionRandomRange(-7, -5)), new MerchantRecipeOptionSell(Items.COOKED_CHICKEN, new MerchantOptionRandomRange(-8, -6)) } }, { { new MerchantRecipeOptionBuy(Items.LEATHER, new MerchantOptionRandomRange(9, 12)), new MerchantRecipeOptionSell(Items.LEATHER_LEGGINGS, new MerchantOptionRandomRange(2, 4)) }, { new MerchantRecipeOptionEnchant(Items.LEATHER_CHESTPLATE, new MerchantOptionRandomRange(7, 12)) }, { new MerchantRecipeOptionSell(Items.SADDLE, new MerchantOptionRandomRange(8, 10)) } } } };
/*     */   
/*     */   public EntityVillager(World world) {
/*  28 */     this(world, 0);
/*     */   }
/*     */   
/*     */   public EntityVillager(World world, int i) {
/*  32 */     super(world);
/*  33 */     this.inventory = new InventorySubcontainer("Items", false, 8, (org.bukkit.craftbukkit.v1_8_R3.entity.CraftVillager)getBukkitEntity());
/*  34 */     setProfession(i);
/*  35 */     setSize(0.6F, 1.8F);
/*  36 */     ((Navigation)getNavigation()).b(true);
/*  37 */     ((Navigation)getNavigation()).a(true);
/*  38 */     this.goalSelector.a(0, new PathfinderGoalFloat(this));
/*  39 */     this.goalSelector.a(1, new PathfinderGoalAvoidTarget(this, EntityZombie.class, 8.0F, 0.6D, 0.6D));
/*  40 */     this.goalSelector.a(1, new PathfinderGoalTradeWithPlayer(this));
/*  41 */     this.goalSelector.a(1, new PathfinderGoalLookAtTradingPlayer(this));
/*  42 */     this.goalSelector.a(2, new PathfinderGoalMoveIndoors(this));
/*  43 */     this.goalSelector.a(3, new PathfinderGoalRestrictOpenDoor(this));
/*  44 */     this.goalSelector.a(4, new PathfinderGoalOpenDoor(this, true));
/*  45 */     this.goalSelector.a(5, new PathfinderGoalMoveTowardsRestriction(this, 0.6D));
/*  46 */     this.goalSelector.a(6, new PathfinderGoalMakeLove(this));
/*  47 */     this.goalSelector.a(7, new PathfinderGoalTakeFlower(this));
/*  48 */     this.goalSelector.a(9, new PathfinderGoalInteract(this, EntityHuman.class, 3.0F, 1.0F));
/*  49 */     this.goalSelector.a(9, new PathfinderGoalInteractVillagers(this));
/*  50 */     this.goalSelector.a(9, new PathfinderGoalRandomStroll(this, 0.6D));
/*  51 */     this.goalSelector.a(10, new PathfinderGoalLookAtPlayer(this, EntityInsentient.class, 8.0F));
/*  52 */     j(true);
/*     */   }
/*     */   
/*     */   private void cv() {
/*  56 */     if (!this.bA) {
/*  57 */       this.bA = true;
/*  58 */       if (isBaby()) {
/*  59 */         this.goalSelector.a(8, new PathfinderGoalPlay(this, 0.32D));
/*  60 */       } else if (getProfession() == 0) {
/*  61 */         this.goalSelector.a(6, new PathfinderGoalVillagerFarm(this, 0.6D));
/*     */       }
/*     */     }
/*     */   }
/*     */   
/*     */   protected void n()
/*     */   {
/*  68 */     if (getProfession() == 0) {
/*  69 */       this.goalSelector.a(8, new PathfinderGoalVillagerFarm(this, 0.6D));
/*     */     }
/*     */     
/*  72 */     super.n();
/*     */   }
/*     */   
/*     */   protected void initAttributes() {
/*  76 */     super.initAttributes();
/*  77 */     getAttributeInstance(GenericAttributes.MOVEMENT_SPEED).setValue(0.5D);
/*     */   }
/*     */   
/*     */   protected void E() {
/*  81 */     if (--this.profession <= 0) {
/*  82 */       BlockPosition blockposition = new BlockPosition(this);
/*     */       
/*  84 */       this.world.ae().a(blockposition);
/*  85 */       this.profession = (70 + this.random.nextInt(50));
/*  86 */       this.village = this.world.ae().getClosestVillage(blockposition, 32);
/*  87 */       if (this.village == null) {
/*  88 */         cj();
/*     */       } else {
/*  90 */         BlockPosition blockposition1 = this.village.a();
/*     */         
/*  92 */         a(blockposition1, (int)(this.village.b() * 1.0F));
/*  93 */         if (this.bz) {
/*  94 */           this.bz = false;
/*  95 */           this.village.b(5);
/*     */         }
/*     */       }
/*     */     }
/*     */     
/* 100 */     if ((!co()) && (this.bs > 0)) {
/* 101 */       this.bs -= 1;
/* 102 */       if (this.bs <= 0) {
/* 103 */         if (this.bt) {
/* 104 */           Iterator iterator = this.br.iterator();
/*     */           
/* 106 */           while (iterator.hasNext()) {
/* 107 */             MerchantRecipe merchantrecipe = (MerchantRecipe)iterator.next();
/*     */             
/* 109 */             if (merchantrecipe.h()) {
/* 110 */               merchantrecipe.a(this.random.nextInt(6) + this.random.nextInt(6) + 2);
/*     */             }
/*     */           }
/*     */           
/* 114 */           cw();
/* 115 */           this.bt = false;
/* 116 */           if ((this.village != null) && (this.bw != null)) {
/* 117 */             this.world.broadcastEntityEffect(this, (byte)14);
/* 118 */             this.village.a(this.bw, 1);
/*     */           }
/*     */         }
/*     */         
/* 122 */         addEffect(new MobEffect(MobEffectList.REGENERATION.id, 200, 0));
/*     */       }
/*     */     }
/*     */     
/* 126 */     super.E();
/*     */   }
/*     */   
/*     */   public boolean a(EntityHuman entityhuman) {
/* 130 */     ItemStack itemstack = entityhuman.inventory.getItemInHand();
/* 131 */     boolean flag = (itemstack != null) && (itemstack.getItem() == Items.SPAWN_EGG);
/*     */     
/* 133 */     if ((!flag) && (isAlive()) && (!co()) && (!isBaby())) {
/* 134 */       if ((!this.world.isClientSide) && ((this.br == null) || (this.br.size() > 0))) {
/* 135 */         a_(entityhuman);
/* 136 */         entityhuman.openTrade(this);
/*     */       }
/*     */       
/* 139 */       entityhuman.b(StatisticList.F);
/* 140 */       return true;
/*     */     }
/* 142 */     return super.a(entityhuman);
/*     */   }
/*     */   
/*     */   protected void h()
/*     */   {
/* 147 */     super.h();
/* 148 */     this.datawatcher.a(16, Integer.valueOf(0));
/*     */   }
/*     */   
/*     */   public void b(NBTTagCompound nbttagcompound) {
/* 152 */     super.b(nbttagcompound);
/* 153 */     nbttagcompound.setInt("Profession", getProfession());
/* 154 */     nbttagcompound.setInt("Riches", this.riches);
/* 155 */     nbttagcompound.setInt("Career", this.bx);
/* 156 */     nbttagcompound.setInt("CareerLevel", this.by);
/* 157 */     nbttagcompound.setBoolean("Willing", this.bu);
/* 158 */     if (this.br != null) {
/* 159 */       nbttagcompound.set("Offers", this.br.a());
/*     */     }
/*     */     
/* 162 */     NBTTagList nbttaglist = new NBTTagList();
/*     */     
/* 164 */     for (int i = 0; i < this.inventory.getSize(); i++) {
/* 165 */       ItemStack itemstack = this.inventory.getItem(i);
/*     */       
/* 167 */       if (itemstack != null) {
/* 168 */         nbttaglist.add(itemstack.save(new NBTTagCompound()));
/*     */       }
/*     */     }
/*     */     
/* 172 */     nbttagcompound.set("Inventory", nbttaglist);
/*     */   }
/*     */   
/*     */   public void a(NBTTagCompound nbttagcompound) {
/* 176 */     super.a(nbttagcompound);
/* 177 */     setProfession(nbttagcompound.getInt("Profession"));
/* 178 */     this.riches = nbttagcompound.getInt("Riches");
/* 179 */     this.bx = nbttagcompound.getInt("Career");
/* 180 */     this.by = nbttagcompound.getInt("CareerLevel");
/* 181 */     this.bu = nbttagcompound.getBoolean("Willing");
/* 182 */     if (nbttagcompound.hasKeyOfType("Offers", 10)) {
/* 183 */       NBTTagCompound nbttagcompound1 = nbttagcompound.getCompound("Offers");
/*     */       
/* 185 */       this.br = new MerchantRecipeList(nbttagcompound1);
/*     */     }
/*     */     
/* 188 */     NBTTagList nbttaglist = nbttagcompound.getList("Inventory", 10);
/*     */     
/* 190 */     for (int i = 0; i < nbttaglist.size(); i++) {
/* 191 */       ItemStack itemstack = ItemStack.createStack(nbttaglist.get(i));
/*     */       
/* 193 */       if (itemstack != null) {
/* 194 */         this.inventory.a(itemstack);
/*     */       }
/*     */     }
/*     */     
/* 198 */     j(true);
/* 199 */     cv();
/*     */   }
/*     */   
/*     */   protected boolean isTypeNotPersistent() {
/* 203 */     return false;
/*     */   }
/*     */   
/*     */   protected String z() {
/* 207 */     return co() ? "mob.villager.haggle" : "mob.villager.idle";
/*     */   }
/*     */   
/*     */   protected String bo() {
/* 211 */     return "mob.villager.hit";
/*     */   }
/*     */   
/*     */   protected String bp() {
/* 215 */     return "mob.villager.death";
/*     */   }
/*     */   
/*     */   public void setProfession(int i) {
/* 219 */     this.datawatcher.watch(16, Integer.valueOf(i));
/*     */   }
/*     */   
/*     */   public int getProfession() {
/* 223 */     return Math.max(this.datawatcher.getInt(16) % 5, 0);
/*     */   }
/*     */   
/*     */   public boolean cm() {
/* 227 */     return this.bo;
/*     */   }
/*     */   
/*     */   public void l(boolean flag) {
/* 231 */     this.bo = flag;
/*     */   }
/*     */   
/*     */   public void m(boolean flag) {
/* 235 */     this.bp = flag;
/*     */   }
/*     */   
/*     */   public boolean cn() {
/* 239 */     return this.bp;
/*     */   }
/*     */   
/*     */   public void b(EntityLiving entityliving) {
/* 243 */     super.b(entityliving);
/* 244 */     if ((this.village != null) && (entityliving != null)) {
/* 245 */       this.village.a(entityliving);
/* 246 */       if ((entityliving instanceof EntityHuman)) {
/* 247 */         byte b0 = -1;
/*     */         
/* 249 */         if (isBaby()) {
/* 250 */           b0 = -3;
/*     */         }
/*     */         
/* 253 */         this.village.a(entityliving.getName(), b0);
/* 254 */         if (isAlive()) {
/* 255 */           this.world.broadcastEntityEffect(this, (byte)13);
/*     */         }
/*     */       }
/*     */     }
/*     */   }
/*     */   
/*     */   public void die(DamageSource damagesource)
/*     */   {
/* 263 */     if (this.village != null) {
/* 264 */       Entity entity = damagesource.getEntity();
/*     */       
/* 266 */       if (entity != null) {
/* 267 */         if ((entity instanceof EntityHuman)) {
/* 268 */           this.village.a(entity.getName(), -2);
/* 269 */         } else if ((entity instanceof IMonster)) {
/* 270 */           this.village.h();
/*     */         }
/*     */       } else {
/* 273 */         EntityHuman entityhuman = this.world.findNearbyPlayer(this, 16.0D);
/*     */         
/* 275 */         if (entityhuman != null) {
/* 276 */           this.village.h();
/*     */         }
/*     */       }
/*     */     }
/*     */     
/* 281 */     super.die(damagesource);
/*     */   }
/*     */   
/*     */   public void a_(EntityHuman entityhuman) {
/* 285 */     this.tradingPlayer = entityhuman;
/*     */   }
/*     */   
/*     */   public EntityHuman v_() {
/* 289 */     return this.tradingPlayer;
/*     */   }
/*     */   
/*     */   public boolean co() {
/* 293 */     return this.tradingPlayer != null;
/*     */   }
/*     */   
/*     */   public boolean n(boolean flag) {
/* 297 */     if ((!this.bu) && (flag) && (cr())) {
/* 298 */       boolean flag1 = false;
/*     */       
/* 300 */       for (int i = 0; i < this.inventory.getSize(); i++) {
/* 301 */         ItemStack itemstack = this.inventory.getItem(i);
/*     */         
/* 303 */         if (itemstack != null) {
/* 304 */           if ((itemstack.getItem() == Items.BREAD) && (itemstack.count >= 3)) {
/* 305 */             flag1 = true;
/* 306 */             this.inventory.splitStack(i, 3);
/* 307 */           } else if (((itemstack.getItem() == Items.POTATO) || (itemstack.getItem() == Items.CARROT)) && (itemstack.count >= 12)) {
/* 308 */             flag1 = true;
/* 309 */             this.inventory.splitStack(i, 12);
/*     */           }
/*     */         }
/*     */         
/* 313 */         if (flag1) {
/* 314 */           this.world.broadcastEntityEffect(this, (byte)18);
/* 315 */           this.bu = true;
/* 316 */           break;
/*     */         }
/*     */       }
/*     */     }
/*     */     
/* 321 */     return this.bu;
/*     */   }
/*     */   
/*     */   public void o(boolean flag) {
/* 325 */     this.bu = flag;
/*     */   }
/*     */   
/*     */   public void a(MerchantRecipe merchantrecipe) {
/* 329 */     merchantrecipe.g();
/* 330 */     this.a_ = (-w());
/* 331 */     makeSound("mob.villager.yes", bB(), bC());
/* 332 */     int i = 3 + this.random.nextInt(4);
/*     */     
/* 334 */     if ((merchantrecipe.e() == 1) || (this.random.nextInt(5) == 0)) {
/* 335 */       this.bs = 40;
/* 336 */       this.bt = true;
/* 337 */       this.bu = true;
/* 338 */       if (this.tradingPlayer != null) {
/* 339 */         this.bw = this.tradingPlayer.getName();
/*     */       } else {
/* 341 */         this.bw = null;
/*     */       }
/*     */       
/* 344 */       i += 5;
/*     */     }
/*     */     
/* 347 */     if (merchantrecipe.getBuyItem1().getItem() == Items.EMERALD) {
/* 348 */       this.riches += merchantrecipe.getBuyItem1().count;
/*     */     }
/*     */     
/* 351 */     if (merchantrecipe.j()) {
/* 352 */       this.world.addEntity(new EntityExperienceOrb(this.world, this.locX, this.locY + 0.5D, this.locZ, i));
/*     */     }
/*     */   }
/*     */   
/*     */   public void a_(ItemStack itemstack)
/*     */   {
/* 358 */     if ((!this.world.isClientSide) && (this.a_ > -w() + 20)) {
/* 359 */       this.a_ = (-w());
/* 360 */       if (itemstack != null) {
/* 361 */         makeSound("mob.villager.yes", bB(), bC());
/*     */       } else {
/* 363 */         makeSound("mob.villager.no", bB(), bC());
/*     */       }
/*     */     }
/*     */   }
/*     */   
/*     */   public MerchantRecipeList getOffers(EntityHuman entityhuman)
/*     */   {
/* 370 */     if (this.br == null) {
/* 371 */       cw();
/*     */     }
/*     */     
/* 374 */     return this.br;
/*     */   }
/*     */   
/*     */   private void cw() {
/* 378 */     IMerchantRecipeOption[][][] aentityvillager_imerchantrecipeoption = bC[getProfession()];
/*     */     
/* 380 */     if ((this.bx != 0) && (this.by != 0)) {
/* 381 */       this.by += 1;
/*     */     } else {
/* 383 */       this.bx = (this.random.nextInt(aentityvillager_imerchantrecipeoption.length) + 1);
/* 384 */       this.by = 1;
/*     */     }
/*     */     
/* 387 */     if (this.br == null) {
/* 388 */       this.br = new MerchantRecipeList();
/*     */     }
/*     */     
/* 391 */     int i = this.bx - 1;
/* 392 */     int j = this.by - 1;
/* 393 */     IMerchantRecipeOption[][] aentityvillager_imerchantrecipeoption1 = aentityvillager_imerchantrecipeoption[i];
/*     */     
/* 395 */     if ((j >= 0) && (j < aentityvillager_imerchantrecipeoption1.length)) {
/* 396 */       IMerchantRecipeOption[] aentityvillager_imerchantrecipeoption2 = aentityvillager_imerchantrecipeoption1[j];
/* 397 */       IMerchantRecipeOption[] aentityvillager_imerchantrecipeoption3 = aentityvillager_imerchantrecipeoption2;
/* 398 */       int k = aentityvillager_imerchantrecipeoption2.length;
/*     */       
/* 400 */       for (int l = 0; l < k; l++) {
/* 401 */         IMerchantRecipeOption entityvillager_imerchantrecipeoption = aentityvillager_imerchantrecipeoption3[l];
/*     */         
/* 403 */         entityvillager_imerchantrecipeoption.a(this.br, this.random);
/*     */       }
/*     */     }
/*     */   }
/*     */   
/*     */   public IChatBaseComponent getScoreboardDisplayName()
/*     */   {
/* 410 */     String s = getCustomName();
/*     */     
/* 412 */     if ((s != null) && (s.length() > 0)) {
/* 413 */       ChatComponentText chatcomponenttext = new ChatComponentText(s);
/*     */       
/* 415 */       chatcomponenttext.getChatModifier().setChatHoverable(aQ());
/* 416 */       chatcomponenttext.getChatModifier().setInsertion(getUniqueID().toString());
/* 417 */       return chatcomponenttext;
/*     */     }
/* 419 */     if (this.br == null) {
/* 420 */       cw();
/*     */     }
/*     */     
/* 423 */     String s1 = null;
/*     */     
/* 425 */     switch (getProfession()) {
/*     */     case 0: 
/* 427 */       if (this.bx == 1) {
/* 428 */         s1 = "farmer";
/* 429 */       } else if (this.bx == 2) {
/* 430 */         s1 = "fisherman";
/* 431 */       } else if (this.bx == 3) {
/* 432 */         s1 = "shepherd";
/* 433 */       } else if (this.bx == 4) {
/* 434 */         s1 = "fletcher";
/*     */       }
/* 436 */       break;
/*     */     
/*     */     case 1: 
/* 439 */       s1 = "librarian";
/* 440 */       break;
/*     */     
/*     */     case 2: 
/* 443 */       s1 = "cleric";
/* 444 */       break;
/*     */     
/*     */     case 3: 
/* 447 */       if (this.bx == 1) {
/* 448 */         s1 = "armor";
/* 449 */       } else if (this.bx == 2) {
/* 450 */         s1 = "weapon";
/* 451 */       } else if (this.bx == 3) {
/* 452 */         s1 = "tool";
/*     */       }
/* 454 */       break;
/*     */     
/*     */     case 4: 
/* 457 */       if (this.bx == 1) {
/* 458 */         s1 = "butcher";
/* 459 */       } else if (this.bx == 2) {
/* 460 */         s1 = "leather";
/*     */       }
/*     */       break;
/*     */     }
/* 464 */     if (s1 != null) {
/* 465 */       ChatMessage chatmessage = new ChatMessage("entity.Villager." + s1, new Object[0]);
/*     */       
/* 467 */       chatmessage.getChatModifier().setChatHoverable(aQ());
/* 468 */       chatmessage.getChatModifier().setInsertion(getUniqueID().toString());
/* 469 */       return chatmessage;
/*     */     }
/* 471 */     return super.getScoreboardDisplayName();
/*     */   }
/*     */   
/*     */ 
/*     */   public float getHeadHeight()
/*     */   {
/* 477 */     float f = 1.62F;
/*     */     
/* 479 */     if (isBaby()) {
/* 480 */       f = (float)(f - 0.81D);
/*     */     }
/*     */     
/* 483 */     return f;
/*     */   }
/*     */   
/*     */   public GroupDataEntity prepare(DifficultyDamageScaler difficultydamagescaler, GroupDataEntity groupdataentity) {
/* 487 */     groupdataentity = super.prepare(difficultydamagescaler, groupdataentity);
/* 488 */     setProfession(this.world.random.nextInt(5));
/* 489 */     cv();
/* 490 */     return groupdataentity;
/*     */   }
/*     */   
/*     */   public void cp() {
/* 494 */     this.bz = true;
/*     */   }
/*     */   
/*     */   public EntityVillager b(EntityAgeable entityageable) {
/* 498 */     EntityVillager entityvillager = new EntityVillager(this.world);
/*     */     
/* 500 */     entityvillager.prepare(this.world.E(new BlockPosition(entityvillager)), null);
/* 501 */     return entityvillager;
/*     */   }
/*     */   
/*     */   public boolean cb() {
/* 505 */     return false;
/*     */   }
/*     */   
/*     */   public void onLightningStrike(EntityLightning entitylightning) {
/* 509 */     if ((!this.world.isClientSide) && (!this.dead)) {
/* 510 */       EntityWitch entitywitch = new EntityWitch(this.world);
/*     */       
/* 512 */       entitywitch.setPositionRotation(this.locX, this.locY, this.locZ, this.yaw, this.pitch);
/* 513 */       entitywitch.prepare(this.world.E(new BlockPosition(entitywitch)), null);
/* 514 */       entitywitch.k(ce());
/* 515 */       if (hasCustomName()) {
/* 516 */         entitywitch.setCustomName(getCustomName());
/* 517 */         entitywitch.setCustomNameVisible(getCustomNameVisible());
/*     */       }
/*     */       
/* 520 */       this.world.addEntity(entitywitch);
/* 521 */       die();
/*     */     }
/*     */   }
/*     */   
/*     */   public InventorySubcontainer cq() {
/* 526 */     return this.inventory;
/*     */   }
/*     */   
/*     */   protected void a(EntityItem entityitem) {
/* 530 */     ItemStack itemstack = entityitem.getItemStack();
/* 531 */     Item item = itemstack.getItem();
/*     */     
/* 533 */     if (a(item)) {
/* 534 */       ItemStack itemstack1 = this.inventory.a(itemstack);
/*     */       
/* 536 */       if (itemstack1 == null) {
/* 537 */         entityitem.die();
/*     */       } else {
/* 539 */         itemstack.count = itemstack1.count;
/*     */       }
/*     */     }
/*     */   }
/*     */   
/*     */   private boolean a(Item item)
/*     */   {
/* 546 */     return (item == Items.BREAD) || (item == Items.POTATO) || (item == Items.CARROT) || (item == Items.WHEAT) || (item == Items.WHEAT_SEEDS);
/*     */   }
/*     */   
/*     */   public boolean cr() {
/* 550 */     return s(1);
/*     */   }
/*     */   
/*     */   public boolean cs() {
/* 554 */     return s(2);
/*     */   }
/*     */   
/*     */   public boolean ct() {
/* 558 */     boolean flag = getProfession() == 0;
/*     */     
/* 560 */     return !s(5);
/*     */   }
/*     */   
/*     */   private boolean s(int i) {
/* 564 */     boolean flag = getProfession() == 0;
/*     */     
/* 566 */     for (int j = 0; j < this.inventory.getSize(); j++) {
/* 567 */       ItemStack itemstack = this.inventory.getItem(j);
/*     */       
/* 569 */       if (itemstack != null) {
/* 570 */         if (((itemstack.getItem() == Items.BREAD) && (itemstack.count >= 3 * i)) || ((itemstack.getItem() == Items.POTATO) && (itemstack.count >= 12 * i)) || ((itemstack.getItem() == Items.CARROT) && (itemstack.count >= 12 * i))) {
/* 571 */           return true;
/*     */         }
/*     */         
/* 574 */         if ((flag) && (itemstack.getItem() == Items.WHEAT) && (itemstack.count >= 9 * i)) {
/* 575 */           return true;
/*     */         }
/*     */       }
/*     */     }
/*     */     
/* 580 */     return false;
/*     */   }
/*     */   
/*     */   public boolean cu() {
/* 584 */     for (int i = 0; i < this.inventory.getSize(); i++) {
/* 585 */       ItemStack itemstack = this.inventory.getItem(i);
/*     */       
/* 587 */       if ((itemstack != null) && ((itemstack.getItem() == Items.WHEAT_SEEDS) || (itemstack.getItem() == Items.POTATO) || (itemstack.getItem() == Items.CARROT))) {
/* 588 */         return true;
/*     */       }
/*     */     }
/*     */     
/* 592 */     return false;
/*     */   }
/*     */   
/*     */   public boolean d(int i, ItemStack itemstack) {
/* 596 */     if (super.d(i, itemstack)) {
/* 597 */       return true;
/*     */     }
/* 599 */     int j = i - 300;
/*     */     
/* 601 */     if ((j >= 0) && (j < this.inventory.getSize())) {
/* 602 */       this.inventory.setItem(j, itemstack);
/* 603 */       return true;
/*     */     }
/* 605 */     return false;
/*     */   }
/*     */   
/*     */ 
/*     */   public EntityAgeable createChild(EntityAgeable entityageable)
/*     */   {
/* 611 */     return b(entityageable);
/*     */   }
/*     */   
/*     */   static class MerchantRecipeOptionProcess implements EntityVillager.IMerchantRecipeOption
/*     */   {
/*     */     public ItemStack a;
/*     */     public EntityVillager.MerchantOptionRandomRange b;
/*     */     public ItemStack c;
/*     */     public EntityVillager.MerchantOptionRandomRange d;
/*     */     
/*     */     public MerchantRecipeOptionProcess(Item item, EntityVillager.MerchantOptionRandomRange entityvillager_merchantoptionrandomrange, Item item1, EntityVillager.MerchantOptionRandomRange entityvillager_merchantoptionrandomrange1) {
/* 622 */       this.a = new ItemStack(item);
/* 623 */       this.b = entityvillager_merchantoptionrandomrange;
/* 624 */       this.c = new ItemStack(item1);
/* 625 */       this.d = entityvillager_merchantoptionrandomrange1;
/*     */     }
/*     */     
/*     */     public void a(MerchantRecipeList merchantrecipelist, Random random) {
/* 629 */       int i = 1;
/*     */       
/* 631 */       if (this.b != null) {
/* 632 */         i = this.b.a(random);
/*     */       }
/*     */       
/* 635 */       int j = 1;
/*     */       
/* 637 */       if (this.d != null) {
/* 638 */         j = this.d.a(random);
/*     */       }
/*     */       
/* 641 */       merchantrecipelist.add(new MerchantRecipe(new ItemStack(this.a.getItem(), i, this.a.getData()), new ItemStack(Items.EMERALD), new ItemStack(this.c.getItem(), j, this.c.getData())));
/*     */     }
/*     */   }
/*     */   
/*     */   static class MerchantRecipeOptionBook
/*     */     implements EntityVillager.IMerchantRecipeOption
/*     */   {
/*     */     public void a(MerchantRecipeList merchantrecipelist, Random random)
/*     */     {
/* 650 */       Enchantment enchantment = Enchantment.b[random.nextInt(Enchantment.b.length)];
/* 651 */       int i = MathHelper.nextInt(random, enchantment.getStartLevel(), enchantment.getMaxLevel());
/* 652 */       ItemStack itemstack = Items.ENCHANTED_BOOK.a(new WeightedRandomEnchant(enchantment, i));
/* 653 */       int j = 2 + random.nextInt(5 + i * 10) + 3 * i;
/*     */       
/* 655 */       if (j > 64) {
/* 656 */         j = 64;
/*     */       }
/*     */       
/* 659 */       merchantrecipelist.add(new MerchantRecipe(new ItemStack(Items.BOOK), new ItemStack(Items.EMERALD, j), itemstack));
/*     */     }
/*     */   }
/*     */   
/*     */   static class MerchantRecipeOptionEnchant implements EntityVillager.IMerchantRecipeOption
/*     */   {
/*     */     public ItemStack a;
/*     */     public EntityVillager.MerchantOptionRandomRange b;
/*     */     
/*     */     public MerchantRecipeOptionEnchant(Item item, EntityVillager.MerchantOptionRandomRange entityvillager_merchantoptionrandomrange) {
/* 669 */       this.a = new ItemStack(item);
/* 670 */       this.b = entityvillager_merchantoptionrandomrange;
/*     */     }
/*     */     
/*     */     public void a(MerchantRecipeList merchantrecipelist, Random random) {
/* 674 */       int i = 1;
/*     */       
/* 676 */       if (this.b != null) {
/* 677 */         i = this.b.a(random);
/*     */       }
/*     */       
/* 680 */       ItemStack itemstack = new ItemStack(Items.EMERALD, i, 0);
/* 681 */       ItemStack itemstack1 = new ItemStack(this.a.getItem(), 1, this.a.getData());
/*     */       
/* 683 */       itemstack1 = EnchantmentManager.a(random, itemstack1, 5 + random.nextInt(15));
/* 684 */       merchantrecipelist.add(new MerchantRecipe(itemstack, itemstack1));
/*     */     }
/*     */   }
/*     */   
/*     */   static class MerchantRecipeOptionSell implements EntityVillager.IMerchantRecipeOption
/*     */   {
/*     */     public ItemStack a;
/*     */     public EntityVillager.MerchantOptionRandomRange b;
/*     */     
/*     */     public MerchantRecipeOptionSell(Item item, EntityVillager.MerchantOptionRandomRange entityvillager_merchantoptionrandomrange) {
/* 694 */       this.a = new ItemStack(item);
/* 695 */       this.b = entityvillager_merchantoptionrandomrange;
/*     */     }
/*     */     
/*     */     public MerchantRecipeOptionSell(ItemStack itemstack, EntityVillager.MerchantOptionRandomRange entityvillager_merchantoptionrandomrange) {
/* 699 */       this.a = itemstack;
/* 700 */       this.b = entityvillager_merchantoptionrandomrange;
/*     */     }
/*     */     
/*     */     public void a(MerchantRecipeList merchantrecipelist, Random random) {
/* 704 */       int i = 1;
/*     */       
/* 706 */       if (this.b != null) {
/* 707 */         i = this.b.a(random);
/*     */       }
/*     */       
/*     */       ItemStack itemstack1;
/*     */       ItemStack itemstack;
/*     */       ItemStack itemstack1;
/* 713 */       if (i < 0) {
/* 714 */         ItemStack itemstack = new ItemStack(Items.EMERALD, 1, 0);
/* 715 */         itemstack1 = new ItemStack(this.a.getItem(), -i, this.a.getData());
/*     */       } else {
/* 717 */         itemstack = new ItemStack(Items.EMERALD, i, 0);
/* 718 */         itemstack1 = new ItemStack(this.a.getItem(), 1, this.a.getData());
/*     */       }
/*     */       
/* 721 */       merchantrecipelist.add(new MerchantRecipe(itemstack, itemstack1));
/*     */     }
/*     */   }
/*     */   
/*     */   static class MerchantRecipeOptionBuy implements EntityVillager.IMerchantRecipeOption
/*     */   {
/*     */     public Item a;
/*     */     public EntityVillager.MerchantOptionRandomRange b;
/*     */     
/*     */     public MerchantRecipeOptionBuy(Item item, EntityVillager.MerchantOptionRandomRange entityvillager_merchantoptionrandomrange) {
/* 731 */       this.a = item;
/* 732 */       this.b = entityvillager_merchantoptionrandomrange;
/*     */     }
/*     */     
/*     */     public void a(MerchantRecipeList merchantrecipelist, Random random) {
/* 736 */       int i = 1;
/*     */       
/* 738 */       if (this.b != null) {
/* 739 */         i = this.b.a(random);
/*     */       }
/*     */       
/* 742 */       merchantrecipelist.add(new MerchantRecipe(new ItemStack(this.a, i, 0), Items.EMERALD));
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   static class MerchantOptionRandomRange
/*     */     extends Tuple<Integer, Integer>
/*     */   {
/*     */     public MerchantOptionRandomRange(int i, int j)
/*     */     {
/* 754 */       super(Integer.valueOf(j));
/*     */     }
/*     */     
/*     */     public int a(Random random) {
/* 758 */       return ((Integer)a()).intValue() >= ((Integer)b()).intValue() ? ((Integer)a()).intValue() : ((Integer)a()).intValue() + random.nextInt(((Integer)b()).intValue() - ((Integer)a()).intValue() + 1);
/*     */     }
/*     */   }
/*     */   
/*     */   static abstract interface IMerchantRecipeOption
/*     */   {
/*     */     public abstract void a(MerchantRecipeList paramMerchantRecipeList, Random paramRandom);
/*     */   }
/*     */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_8_R3\EntityVillager.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */
/*      */ package net.minecraft.server.v1_8_R3;
/*      */ 
/*      */ import com.google.common.base.Predicate;
/*      */ import java.util.Iterator;
/*      */ import java.util.List;
/*      */ import java.util.Random;
/*      */ import org.bukkit.event.entity.EntityRegainHealthEvent.RegainReason;
/*      */ 
/*      */ public class EntityHorse extends EntityAnimal implements IInventoryListener
/*      */ {
/*   11 */   private static final Predicate<Entity> bs = new Predicate() {
/*      */     public boolean a(Entity entity) {
/*   13 */       return ((entity instanceof EntityHorse)) && (((EntityHorse)entity).cA());
/*      */     }
/*      */     
/*      */     public boolean apply(Object object) {
/*   17 */       return a((Entity)object);
/*      */     }
/*      */   };
/*   20 */   public static final IAttribute attributeJumpStrength = new AttributeRanged(null, "horse.jumpStrength", 0.7D, 0.0D, 2.0D).a("Jump Strength").a(true);
/*   21 */   private static final String[] bu = { 0, "textures/entity/horse/armor/horse_armor_iron.png", "textures/entity/horse/armor/horse_armor_gold.png", "textures/entity/horse/armor/horse_armor_diamond.png" };
/*   22 */   private static final String[] bv = { "", "meo", "goo", "dio" };
/*   23 */   private static final int[] bw = { 0, 5, 7, 11 };
/*   24 */   private static final String[] bx = { "textures/entity/horse/horse_white.png", "textures/entity/horse/horse_creamy.png", "textures/entity/horse/horse_chestnut.png", "textures/entity/horse/horse_brown.png", "textures/entity/horse/horse_black.png", "textures/entity/horse/horse_gray.png", "textures/entity/horse/horse_darkbrown.png" };
/*   25 */   private static final String[] by = { "hwh", "hcr", "hch", "hbr", "hbl", "hgr", "hdb" };
/*   26 */   private static final String[] bz = { 0, "textures/entity/horse/horse_markings_white.png", "textures/entity/horse/horse_markings_whitefield.png", "textures/entity/horse/horse_markings_whitedots.png", "textures/entity/horse/horse_markings_blackdots.png" };
/*   27 */   private static final String[] bA = { "", "wo_", "wmo", "wdo", "bdo" };
/*      */   private int bB;
/*      */   private int bC;
/*      */   private int bD;
/*      */   public int bm;
/*      */   public int bo;
/*      */   protected boolean bp;
/*      */   public InventoryHorseChest inventoryChest;
/*      */   private boolean bF;
/*      */   protected int bq;
/*      */   protected float br;
/*      */   private boolean bG;
/*      */   private float bH;
/*      */   private float bI;
/*      */   private float bJ;
/*      */   private float bK;
/*      */   private float bL;
/*      */   private float bM;
/*      */   private int bN;
/*      */   private String bO;
/*   47 */   private String[] bP = new String[3];
/*   48 */   private boolean bQ = false;
/*   49 */   public int maxDomestication = 100;
/*      */   
/*      */   public EntityHorse(World world) {
/*   52 */     super(world);
/*   53 */     setSize(1.4F, 1.6F);
/*   54 */     this.fireProof = false;
/*   55 */     setHasChest(false);
/*   56 */     ((Navigation)getNavigation()).a(true);
/*   57 */     this.goalSelector.a(0, new PathfinderGoalFloat(this));
/*   58 */     this.goalSelector.a(1, new PathfinderGoalPanic(this, 1.2D));
/*   59 */     this.goalSelector.a(1, new PathfinderGoalTame(this, 1.2D));
/*   60 */     this.goalSelector.a(2, new PathfinderGoalBreed(this, 1.0D));
/*   61 */     this.goalSelector.a(4, new PathfinderGoalFollowParent(this, 1.0D));
/*   62 */     this.goalSelector.a(6, new PathfinderGoalRandomStroll(this, 0.7D));
/*   63 */     this.goalSelector.a(7, new PathfinderGoalLookAtPlayer(this, EntityHuman.class, 6.0F));
/*   64 */     this.goalSelector.a(8, new PathfinderGoalRandomLookaround(this));
/*   65 */     loadChest();
/*      */   }
/*      */   
/*      */   protected void h() {
/*   69 */     super.h();
/*   70 */     this.datawatcher.a(16, Integer.valueOf(0));
/*   71 */     this.datawatcher.a(19, Byte.valueOf((byte)0));
/*   72 */     this.datawatcher.a(20, Integer.valueOf(0));
/*   73 */     this.datawatcher.a(21, String.valueOf(""));
/*   74 */     this.datawatcher.a(22, Integer.valueOf(0));
/*      */   }
/*      */   
/*      */   public void setType(int i) {
/*   78 */     this.datawatcher.watch(19, Byte.valueOf((byte)i));
/*   79 */     dc();
/*      */   }
/*      */   
/*      */   public int getType() {
/*   83 */     return this.datawatcher.getByte(19);
/*      */   }
/*      */   
/*      */   public void setVariant(int i) {
/*   87 */     this.datawatcher.watch(20, Integer.valueOf(i));
/*   88 */     dc();
/*      */   }
/*      */   
/*      */   public int getVariant() {
/*   92 */     return this.datawatcher.getInt(20);
/*      */   }
/*      */   
/*      */   public String getName() {
/*   96 */     if (hasCustomName()) {
/*   97 */       return getCustomName();
/*      */     }
/*   99 */     int i = getType();
/*      */     
/*  101 */     switch (i) {
/*      */     case 0: 
/*      */     default: 
/*  104 */       return LocaleI18n.get("entity.horse.name");
/*      */     
/*      */     case 1: 
/*  107 */       return LocaleI18n.get("entity.donkey.name");
/*      */     
/*      */     case 2: 
/*  110 */       return LocaleI18n.get("entity.mule.name");
/*      */     
/*      */     case 3: 
/*  113 */       return LocaleI18n.get("entity.zombiehorse.name");
/*      */     }
/*      */     
/*  116 */     return LocaleI18n.get("entity.skeletonhorse.name");
/*      */   }
/*      */   
/*      */ 
/*      */   private boolean w(int i)
/*      */   {
/*  122 */     return (this.datawatcher.getInt(16) & i) != 0;
/*      */   }
/*      */   
/*      */   private void c(int i, boolean flag) {
/*  126 */     int j = this.datawatcher.getInt(16);
/*      */     
/*  128 */     if (flag) {
/*  129 */       this.datawatcher.watch(16, Integer.valueOf(j | i));
/*      */     } else {
/*  131 */       this.datawatcher.watch(16, Integer.valueOf(j & (i ^ 0xFFFFFFFF)));
/*      */     }
/*      */   }
/*      */   
/*      */   public boolean cn()
/*      */   {
/*  137 */     return !isBaby();
/*      */   }
/*      */   
/*      */   public boolean isTame() {
/*  141 */     return w(2);
/*      */   }
/*      */   
/*      */   public boolean cp() {
/*  145 */     return cn();
/*      */   }
/*      */   
/*      */   public String getOwnerUUID() {
/*  149 */     return this.datawatcher.getString(21);
/*      */   }
/*      */   
/*      */   public void setOwnerUUID(String s) {
/*  153 */     this.datawatcher.watch(21, s);
/*      */   }
/*      */   
/*      */   public float cu() {
/*  157 */     return 0.5F;
/*      */   }
/*      */   
/*      */   public void a(boolean flag) {
/*  161 */     if (flag) {
/*  162 */       a(cu());
/*      */     } else {
/*  164 */       a(1.0F);
/*      */     }
/*      */   }
/*      */   
/*      */   public boolean cv()
/*      */   {
/*  170 */     return this.bp;
/*      */   }
/*      */   
/*      */   public void setTame(boolean flag) {
/*  174 */     c(2, flag);
/*      */   }
/*      */   
/*      */   public void m(boolean flag) {
/*  178 */     this.bp = flag;
/*      */   }
/*      */   
/*      */   public boolean cb() {
/*  182 */     return (!cR()) && (super.cb());
/*      */   }
/*      */   
/*      */   protected void o(float f) {
/*  186 */     if ((f > 6.0F) && (cy())) {
/*  187 */       r(false);
/*      */     }
/*      */   }
/*      */   
/*      */   public boolean hasChest()
/*      */   {
/*  193 */     return w(8);
/*      */   }
/*      */   
/*      */   public int cx() {
/*  197 */     return this.datawatcher.getInt(22);
/*      */   }
/*      */   
/*      */   private int f(ItemStack itemstack) {
/*  201 */     if (itemstack == null) {
/*  202 */       return 0;
/*      */     }
/*  204 */     Item item = itemstack.getItem();
/*      */     
/*  206 */     return item == Items.DIAMOND_HORSE_ARMOR ? 3 : item == Items.GOLDEN_HORSE_ARMOR ? 2 : item == Items.IRON_HORSE_ARMOR ? 1 : 0;
/*      */   }
/*      */   
/*      */   public boolean cy()
/*      */   {
/*  211 */     return w(32);
/*      */   }
/*      */   
/*      */   public boolean cz() {
/*  215 */     return w(64);
/*      */   }
/*      */   
/*      */   public boolean cA() {
/*  219 */     return w(16);
/*      */   }
/*      */   
/*      */   public boolean cB() {
/*  223 */     return this.bF;
/*      */   }
/*      */   
/*      */   public void e(ItemStack itemstack) {
/*  227 */     this.datawatcher.watch(22, Integer.valueOf(f(itemstack)));
/*  228 */     dc();
/*      */   }
/*      */   
/*      */   public void n(boolean flag) {
/*  232 */     c(16, flag);
/*      */   }
/*      */   
/*      */   public void setHasChest(boolean flag) {
/*  236 */     c(8, flag);
/*      */   }
/*      */   
/*      */   public void p(boolean flag) {
/*  240 */     this.bF = flag;
/*      */   }
/*      */   
/*      */   public void q(boolean flag) {
/*  244 */     c(4, flag);
/*      */   }
/*      */   
/*      */   public int getTemper() {
/*  248 */     return this.bq;
/*      */   }
/*      */   
/*      */   public void setTemper(int i) {
/*  252 */     this.bq = i;
/*      */   }
/*      */   
/*      */   public int u(int i) {
/*  256 */     int j = MathHelper.clamp(getTemper() + i, 0, getMaxDomestication());
/*      */     
/*  258 */     setTemper(j);
/*  259 */     return j;
/*      */   }
/*      */   
/*      */   public boolean damageEntity(DamageSource damagesource, float f) {
/*  263 */     Entity entity = damagesource.getEntity();
/*      */     
/*  265 */     return (this.passenger != null) && (this.passenger.equals(entity)) ? false : super.damageEntity(damagesource, f);
/*      */   }
/*      */   
/*      */   public int br() {
/*  269 */     return bw[cx()];
/*      */   }
/*      */   
/*      */   public boolean ae() {
/*  273 */     return this.passenger == null;
/*      */   }
/*      */   
/*      */   public boolean cD() {
/*  277 */     int i = MathHelper.floor(this.locX);
/*  278 */     int j = MathHelper.floor(this.locZ);
/*      */     
/*  280 */     this.world.getBiome(new BlockPosition(i, 0, j));
/*  281 */     return true;
/*      */   }
/*      */   
/*      */   public void cE() {
/*  285 */     if ((!this.world.isClientSide) && (hasChest())) {
/*  286 */       a(Item.getItemOf(Blocks.CHEST), 1);
/*  287 */       setHasChest(false);
/*      */     }
/*      */   }
/*      */   
/*      */   private void cY() {
/*  292 */     df();
/*  293 */     if (!R()) {
/*  294 */       this.world.makeSound(this, "eating", 1.0F, 1.0F + (this.random.nextFloat() - this.random.nextFloat()) * 0.2F);
/*      */     }
/*      */   }
/*      */   
/*      */   public void e(float f, float f1)
/*      */   {
/*  300 */     if (f > 1.0F) {
/*  301 */       makeSound("mob.horse.land", 0.4F, 1.0F);
/*      */     }
/*      */     
/*  304 */     int i = MathHelper.f((f * 0.5F - 3.0F) * f1);
/*      */     
/*  306 */     if (i > 0) {
/*  307 */       damageEntity(DamageSource.FALL, i);
/*  308 */       if (this.passenger != null) {
/*  309 */         this.passenger.damageEntity(DamageSource.FALL, i);
/*      */       }
/*      */       
/*  312 */       Block block = this.world.getType(new BlockPosition(this.locX, this.locY - 0.2D - this.lastYaw, this.locZ)).getBlock();
/*      */       
/*  314 */       if ((block.getMaterial() != Material.AIR) && (!R())) {
/*  315 */         Block.StepSound block_stepsound = block.stepSound;
/*      */         
/*  317 */         this.world.makeSound(this, block_stepsound.getStepSound(), block_stepsound.getVolume1() * 0.5F, block_stepsound.getVolume2() * 0.75F);
/*      */       }
/*      */     }
/*      */   }
/*      */   
/*      */   private int cZ()
/*      */   {
/*  324 */     getType();
/*      */     
/*  326 */     return hasChest() ? 17 : 2;
/*      */   }
/*      */   
/*      */   public void loadChest() {
/*  330 */     InventoryHorseChest inventoryhorsechest = this.inventoryChest;
/*      */     
/*  332 */     this.inventoryChest = new InventoryHorseChest("HorseChest", cZ(), this);
/*  333 */     this.inventoryChest.a(getName());
/*  334 */     if (inventoryhorsechest != null) {
/*  335 */       inventoryhorsechest.b(this);
/*  336 */       int i = Math.min(inventoryhorsechest.getSize(), this.inventoryChest.getSize());
/*      */       
/*  338 */       for (int j = 0; j < i; j++) {
/*  339 */         ItemStack itemstack = inventoryhorsechest.getItem(j);
/*      */         
/*  341 */         if (itemstack != null) {
/*  342 */           this.inventoryChest.setItem(j, itemstack.cloneItemStack());
/*      */         }
/*      */       }
/*      */     }
/*      */     
/*  347 */     this.inventoryChest.a(this);
/*  348 */     db();
/*      */   }
/*      */   
/*      */   private void db() {
/*  352 */     if (!this.world.isClientSide) {
/*  353 */       q(this.inventoryChest.getItem(0) != null);
/*  354 */       if (cO()) {
/*  355 */         e(this.inventoryChest.getItem(1));
/*      */       }
/*      */     }
/*      */   }
/*      */   
/*      */   public void a(InventorySubcontainer inventorysubcontainer)
/*      */   {
/*  362 */     int i = cx();
/*  363 */     boolean flag = cG();
/*      */     
/*  365 */     db();
/*  366 */     if (this.ticksLived > 20) {
/*  367 */       if ((i == 0) && (i != cx())) {
/*  368 */         makeSound("mob.horse.armor", 0.5F, 1.0F);
/*  369 */       } else if (i != cx()) {
/*  370 */         makeSound("mob.horse.armor", 0.5F, 1.0F);
/*      */       }
/*      */       
/*  373 */       if ((!flag) && (cG())) {
/*  374 */         makeSound("mob.horse.leather", 0.5F, 1.0F);
/*      */       }
/*      */     }
/*      */   }
/*      */   
/*      */   public boolean bR()
/*      */   {
/*  381 */     cD();
/*  382 */     return super.bR();
/*      */   }
/*      */   
/*      */   protected EntityHorse a(Entity entity, double d0) {
/*  386 */     double d1 = Double.MAX_VALUE;
/*  387 */     Entity entity1 = null;
/*  388 */     List list = this.world.a(entity, entity.getBoundingBox().a(d0, d0, d0), bs);
/*  389 */     Iterator iterator = list.iterator();
/*      */     
/*  391 */     while (iterator.hasNext()) {
/*  392 */       Entity entity2 = (Entity)iterator.next();
/*  393 */       double d2 = entity2.e(entity.locX, entity.locY, entity.locZ);
/*      */       
/*  395 */       if (d2 < d1) {
/*  396 */         entity1 = entity2;
/*  397 */         d1 = d2;
/*      */       }
/*      */     }
/*      */     
/*  401 */     return (EntityHorse)entity1;
/*      */   }
/*      */   
/*      */   public double getJumpStrength() {
/*  405 */     return getAttributeInstance(attributeJumpStrength).getValue();
/*      */   }
/*      */   
/*      */   protected String bp() {
/*  409 */     df();
/*  410 */     int i = getType();
/*      */     
/*  412 */     return (i != 1) && (i != 2) ? "mob.horse.death" : i == 4 ? "mob.horse.skeleton.death" : i == 3 ? "mob.horse.zombie.death" : "mob.horse.donkey.death";
/*      */   }
/*      */   
/*      */   protected Item getLoot() {
/*  416 */     boolean flag = this.random.nextInt(4) == 0;
/*  417 */     int i = getType();
/*      */     
/*  419 */     return i == 3 ? Items.ROTTEN_FLESH : flag ? null : i == 4 ? Items.BONE : Items.LEATHER;
/*      */   }
/*      */   
/*      */   protected String bo() {
/*  423 */     df();
/*  424 */     if (this.random.nextInt(3) == 0) {
/*  425 */       dh();
/*      */     }
/*      */     
/*  428 */     int i = getType();
/*      */     
/*  430 */     return (i != 1) && (i != 2) ? "mob.horse.hit" : i == 4 ? "mob.horse.skeleton.hit" : i == 3 ? "mob.horse.zombie.hit" : "mob.horse.donkey.hit";
/*      */   }
/*      */   
/*      */   public boolean cG() {
/*  434 */     return w(4);
/*      */   }
/*      */   
/*      */   protected String z() {
/*  438 */     df();
/*  439 */     if ((this.random.nextInt(10) == 0) && (!bD())) {
/*  440 */       dh();
/*      */     }
/*      */     
/*  443 */     int i = getType();
/*      */     
/*  445 */     return (i != 1) && (i != 2) ? "mob.horse.idle" : i == 4 ? "mob.horse.skeleton.idle" : i == 3 ? "mob.horse.zombie.idle" : "mob.horse.donkey.idle";
/*      */   }
/*      */   
/*      */   protected String cH() {
/*  449 */     df();
/*  450 */     dh();
/*  451 */     int i = getType();
/*      */     
/*  453 */     return (i != 3) && (i != 4) ? "mob.horse.donkey.angry" : (i != 1) && (i != 2) ? "mob.horse.angry" : null;
/*      */   }
/*      */   
/*      */   protected void a(BlockPosition blockposition, Block block) {
/*  457 */     Block.StepSound block_stepsound = block.stepSound;
/*      */     
/*  459 */     if (this.world.getType(blockposition.up()).getBlock() == Blocks.SNOW_LAYER) {
/*  460 */       block_stepsound = Blocks.SNOW_LAYER.stepSound;
/*      */     }
/*      */     
/*  463 */     if (!block.getMaterial().isLiquid()) {
/*  464 */       int i = getType();
/*      */       
/*  466 */       if ((this.passenger != null) && (i != 1) && (i != 2)) {
/*  467 */         this.bN += 1;
/*  468 */         if ((this.bN > 5) && (this.bN % 3 == 0)) {
/*  469 */           makeSound("mob.horse.gallop", block_stepsound.getVolume1() * 0.15F, block_stepsound.getVolume2());
/*  470 */           if ((i == 0) && (this.random.nextInt(10) == 0)) {
/*  471 */             makeSound("mob.horse.breathe", block_stepsound.getVolume1() * 0.6F, block_stepsound.getVolume2());
/*      */           }
/*  473 */         } else if (this.bN <= 5) {
/*  474 */           makeSound("mob.horse.wood", block_stepsound.getVolume1() * 0.15F, block_stepsound.getVolume2());
/*      */         }
/*  476 */       } else if (block_stepsound == Block.f) {
/*  477 */         makeSound("mob.horse.wood", block_stepsound.getVolume1() * 0.15F, block_stepsound.getVolume2());
/*      */       } else {
/*  479 */         makeSound("mob.horse.soft", block_stepsound.getVolume1() * 0.15F, block_stepsound.getVolume2());
/*      */       }
/*      */     }
/*      */   }
/*      */   
/*      */   protected void initAttributes()
/*      */   {
/*  486 */     super.initAttributes();
/*  487 */     getAttributeMap().b(attributeJumpStrength);
/*  488 */     getAttributeInstance(GenericAttributes.maxHealth).setValue(53.0D);
/*  489 */     getAttributeInstance(GenericAttributes.MOVEMENT_SPEED).setValue(0.22499999403953552D);
/*      */   }
/*      */   
/*      */   public int bV() {
/*  493 */     return 6;
/*      */   }
/*      */   
/*      */   public int getMaxDomestication() {
/*  497 */     return this.maxDomestication;
/*      */   }
/*      */   
/*      */   protected float bB() {
/*  501 */     return 0.8F;
/*      */   }
/*      */   
/*      */   public int w() {
/*  505 */     return 400;
/*      */   }
/*      */   
/*      */   private void dc() {
/*  509 */     this.bO = null;
/*      */   }
/*      */   
/*      */   public void g(EntityHuman entityhuman) {
/*  513 */     if ((!this.world.isClientSide) && ((this.passenger == null) || (this.passenger == entityhuman)) && (isTame())) {
/*  514 */       this.inventoryChest.a(getName());
/*  515 */       entityhuman.openHorseInventory(this, this.inventoryChest);
/*      */     }
/*      */   }
/*      */   
/*      */   public boolean a(EntityHuman entityhuman)
/*      */   {
/*  521 */     ItemStack itemstack = entityhuman.inventory.getItemInHand();
/*      */     
/*  523 */     if ((itemstack != null) && (itemstack.getItem() == Items.SPAWN_EGG))
/*  524 */       return super.a(entityhuman);
/*  525 */     if ((!isTame()) && (cR()))
/*  526 */       return false;
/*  527 */     if ((isTame()) && (cn()) && (entityhuman.isSneaking())) {
/*  528 */       g(entityhuman);
/*  529 */       return true; }
/*  530 */     if ((cp()) && (this.passenger != null)) {
/*  531 */       return super.a(entityhuman);
/*      */     }
/*  533 */     if (itemstack != null) {
/*  534 */       boolean flag = false;
/*      */       
/*  536 */       if (cO()) {
/*  537 */         byte b0 = -1;
/*      */         
/*  539 */         if (itemstack.getItem() == Items.IRON_HORSE_ARMOR) {
/*  540 */           b0 = 1;
/*  541 */         } else if (itemstack.getItem() == Items.GOLDEN_HORSE_ARMOR) {
/*  542 */           b0 = 2;
/*  543 */         } else if (itemstack.getItem() == Items.DIAMOND_HORSE_ARMOR) {
/*  544 */           b0 = 3;
/*      */         }
/*      */         
/*  547 */         if (b0 >= 0) {
/*  548 */           if (!isTame()) {
/*  549 */             cW();
/*  550 */             return true;
/*      */           }
/*      */           
/*  553 */           g(entityhuman);
/*  554 */           return true;
/*      */         }
/*      */       }
/*      */       
/*  558 */       if ((!flag) && (!cR())) {
/*  559 */         float f = 0.0F;
/*  560 */         short short0 = 0;
/*  561 */         byte b1 = 0;
/*      */         
/*  563 */         if (itemstack.getItem() == Items.WHEAT) {
/*  564 */           f = 2.0F;
/*  565 */           short0 = 20;
/*  566 */           b1 = 3;
/*  567 */         } else if (itemstack.getItem() == Items.SUGAR) {
/*  568 */           f = 1.0F;
/*  569 */           short0 = 30;
/*  570 */           b1 = 3;
/*  571 */         } else if (Block.asBlock(itemstack.getItem()) == Blocks.HAY_BLOCK) {
/*  572 */           f = 20.0F;
/*  573 */           short0 = 180;
/*  574 */         } else if (itemstack.getItem() == Items.APPLE) {
/*  575 */           f = 3.0F;
/*  576 */           short0 = 60;
/*  577 */           b1 = 3;
/*  578 */         } else if (itemstack.getItem() == Items.GOLDEN_CARROT) {
/*  579 */           f = 4.0F;
/*  580 */           short0 = 60;
/*  581 */           b1 = 5;
/*  582 */           if ((isTame()) && (getAge() == 0)) {
/*  583 */             flag = true;
/*  584 */             c(entityhuman);
/*      */           }
/*  586 */         } else if (itemstack.getItem() == Items.GOLDEN_APPLE) {
/*  587 */           f = 10.0F;
/*  588 */           short0 = 240;
/*  589 */           b1 = 10;
/*  590 */           if ((isTame()) && (getAge() == 0)) {
/*  591 */             flag = true;
/*  592 */             c(entityhuman);
/*      */           }
/*      */         }
/*      */         
/*  596 */         if ((getHealth() < getMaxHealth()) && (f > 0.0F)) {
/*  597 */           heal(f, EntityRegainHealthEvent.RegainReason.EATING);
/*  598 */           flag = true;
/*      */         }
/*      */         
/*  601 */         if ((!cn()) && (short0 > 0)) {
/*  602 */           setAge(short0);
/*  603 */           flag = true;
/*      */         }
/*      */         
/*  606 */         if ((b1 > 0) && ((flag) || (!isTame())) && (b1 < getMaxDomestication())) {
/*  607 */           flag = true;
/*  608 */           u(b1);
/*      */         }
/*      */         
/*  611 */         if (flag) {
/*  612 */           cY();
/*      */         }
/*      */       }
/*      */       
/*  616 */       if ((!isTame()) && (!flag)) {
/*  617 */         if ((itemstack != null) && (itemstack.a(entityhuman, this))) {
/*  618 */           return true;
/*      */         }
/*      */         
/*  621 */         cW();
/*  622 */         return true;
/*      */       }
/*      */       
/*  625 */       if ((!flag) && (cP()) && (!hasChest()) && (itemstack.getItem() == Item.getItemOf(Blocks.CHEST))) {
/*  626 */         setHasChest(true);
/*  627 */         makeSound("mob.chickenplop", 1.0F, (this.random.nextFloat() - this.random.nextFloat()) * 0.2F + 1.0F);
/*  628 */         flag = true;
/*  629 */         loadChest();
/*      */       }
/*      */       
/*  632 */       if ((!flag) && (cp()) && (!cG()) && (itemstack.getItem() == Items.SADDLE)) {
/*  633 */         g(entityhuman);
/*  634 */         return true;
/*      */       }
/*      */       
/*  637 */       if (flag) {
/*  638 */         if (!entityhuman.abilities.canInstantlyBuild) { if (--itemstack.count == 0) {
/*  639 */             entityhuman.inventory.setItem(entityhuman.inventory.itemInHandIndex, null);
/*      */           }
/*      */         }
/*  642 */         return true;
/*      */       }
/*      */     }
/*      */     
/*  646 */     if ((cp()) && (this.passenger == null)) {
/*  647 */       if ((itemstack != null) && (itemstack.a(entityhuman, this))) {
/*  648 */         return true;
/*      */       }
/*  650 */       i(entityhuman);
/*  651 */       return true;
/*      */     }
/*      */     
/*  654 */     return super.a(entityhuman);
/*      */   }
/*      */   
/*      */ 
/*      */   private void i(EntityHuman entityhuman)
/*      */   {
/*  660 */     entityhuman.yaw = this.yaw;
/*  661 */     entityhuman.pitch = this.pitch;
/*  662 */     r(false);
/*  663 */     s(false);
/*  664 */     if (!this.world.isClientSide) {
/*  665 */       entityhuman.mount(this);
/*      */     }
/*      */   }
/*      */   
/*      */   public boolean cO()
/*      */   {
/*  671 */     return getType() == 0;
/*      */   }
/*      */   
/*      */   public boolean cP() {
/*  675 */     int i = getType();
/*      */     
/*  677 */     return (i == 2) || (i == 1);
/*      */   }
/*      */   
/*      */   protected boolean bD() {
/*  681 */     return (this.passenger != null) && (cG());
/*      */   }
/*      */   
/*      */   public boolean cR() {
/*  685 */     int i = getType();
/*      */     
/*  687 */     return (i == 3) || (i == 4);
/*      */   }
/*      */   
/*      */   public boolean cS() {
/*  691 */     return (cR()) || (getType() == 2);
/*      */   }
/*      */   
/*      */   public boolean d(ItemStack itemstack) {
/*  695 */     return false;
/*      */   }
/*      */   
/*      */   private void de() {
/*  699 */     this.bm = 1;
/*      */   }
/*      */   
/*      */   public void die(DamageSource damagesource) {
/*  703 */     super.die(damagesource);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   protected void dropDeathLoot(boolean flag, int i)
/*      */   {
/*  714 */     super.dropDeathLoot(flag, i);
/*      */     
/*      */ 
/*  717 */     if (!this.world.isClientSide) {
/*  718 */       dropChest();
/*      */     }
/*      */   }
/*      */   
/*      */   public void m()
/*      */   {
/*  724 */     if (this.random.nextInt(200) == 0) {
/*  725 */       de();
/*      */     }
/*      */     
/*  728 */     super.m();
/*  729 */     if (!this.world.isClientSide) {
/*  730 */       if ((this.random.nextInt(900) == 0) && (this.deathTicks == 0)) {
/*  731 */         heal(1.0F, EntityRegainHealthEvent.RegainReason.REGEN);
/*      */       }
/*      */       
/*  734 */       if ((!cy()) && (this.passenger == null) && (this.random.nextInt(300) == 0) && (this.world.getType(new BlockPosition(MathHelper.floor(this.locX), MathHelper.floor(this.locY) - 1, MathHelper.floor(this.locZ))).getBlock() == Blocks.GRASS)) {
/*  735 */         r(true);
/*      */       }
/*      */       
/*  738 */       if ((cy()) && (++this.bB > 50)) {
/*  739 */         this.bB = 0;
/*  740 */         r(false);
/*      */       }
/*      */       
/*  743 */       if ((cA()) && (!cn()) && (!cy())) {
/*  744 */         EntityHorse entityhorse = a(this, 16.0D);
/*      */         
/*  746 */         if ((entityhorse != null) && (h(entityhorse) > 4.0D)) {
/*  747 */           this.navigation.a(entityhorse);
/*      */         }
/*      */       }
/*      */     }
/*      */   }
/*      */   
/*      */   public void t_()
/*      */   {
/*  755 */     super.t_();
/*  756 */     if ((this.world.isClientSide) && (this.datawatcher.a())) {
/*  757 */       this.datawatcher.e();
/*  758 */       dc();
/*      */     }
/*      */     
/*  761 */     if ((this.bC > 0) && (++this.bC > 30)) {
/*  762 */       this.bC = 0;
/*  763 */       c(128, false);
/*      */     }
/*      */     
/*  766 */     if ((!this.world.isClientSide) && (this.bD > 0) && (++this.bD > 20)) {
/*  767 */       this.bD = 0;
/*  768 */       s(false);
/*      */     }
/*      */     
/*  771 */     if ((this.bm > 0) && (++this.bm > 8)) {
/*  772 */       this.bm = 0;
/*      */     }
/*      */     
/*  775 */     if (this.bo > 0) {
/*  776 */       this.bo += 1;
/*  777 */       if (this.bo > 300) {
/*  778 */         this.bo = 0;
/*      */       }
/*      */     }
/*      */     
/*  782 */     this.bI = this.bH;
/*  783 */     if (cy()) {
/*  784 */       this.bH += (1.0F - this.bH) * 0.4F + 0.05F;
/*  785 */       if (this.bH > 1.0F) {
/*  786 */         this.bH = 1.0F;
/*      */       }
/*      */     } else {
/*  789 */       this.bH += (0.0F - this.bH) * 0.4F - 0.05F;
/*  790 */       if (this.bH < 0.0F) {
/*  791 */         this.bH = 0.0F;
/*      */       }
/*      */     }
/*      */     
/*  795 */     this.bK = this.bJ;
/*  796 */     if (cz()) {
/*  797 */       this.bI = (this.bH = 0.0F);
/*  798 */       this.bJ += (1.0F - this.bJ) * 0.4F + 0.05F;
/*  799 */       if (this.bJ > 1.0F) {
/*  800 */         this.bJ = 1.0F;
/*      */       }
/*      */     } else {
/*  803 */       this.bG = false;
/*  804 */       this.bJ += (0.8F * this.bJ * this.bJ * this.bJ - this.bJ) * 0.6F - 0.05F;
/*  805 */       if (this.bJ < 0.0F) {
/*  806 */         this.bJ = 0.0F;
/*      */       }
/*      */     }
/*      */     
/*  810 */     this.bM = this.bL;
/*  811 */     if (w(128)) {
/*  812 */       this.bL += (1.0F - this.bL) * 0.7F + 0.05F;
/*  813 */       if (this.bL > 1.0F) {
/*  814 */         this.bL = 1.0F;
/*      */       }
/*      */     } else {
/*  817 */       this.bL += (0.0F - this.bL) * 0.7F - 0.05F;
/*  818 */       if (this.bL < 0.0F) {
/*  819 */         this.bL = 0.0F;
/*      */       }
/*      */     }
/*      */   }
/*      */   
/*      */   private void df()
/*      */   {
/*  826 */     if (!this.world.isClientSide) {
/*  827 */       this.bC = 1;
/*  828 */       c(128, true);
/*      */     }
/*      */   }
/*      */   
/*      */   private boolean dg()
/*      */   {
/*  834 */     return (this.passenger == null) && (this.vehicle == null) && (isTame()) && (cn()) && (!cS()) && (getHealth() >= getMaxHealth()) && (isInLove());
/*      */   }
/*      */   
/*      */   public void f(boolean flag) {
/*  838 */     c(32, flag);
/*      */   }
/*      */   
/*      */   public void r(boolean flag) {
/*  842 */     f(flag);
/*      */   }
/*      */   
/*      */   public void s(boolean flag) {
/*  846 */     if (flag) {
/*  847 */       r(false);
/*      */     }
/*      */     
/*  850 */     c(64, flag);
/*      */   }
/*      */   
/*      */   private void dh() {
/*  854 */     if (!this.world.isClientSide) {
/*  855 */       this.bD = 1;
/*  856 */       s(true);
/*      */     }
/*      */   }
/*      */   
/*      */   public void cW()
/*      */   {
/*  862 */     dh();
/*  863 */     String s = cH();
/*      */     
/*  865 */     if (s != null) {
/*  866 */       makeSound(s, bB(), bC());
/*      */     }
/*      */   }
/*      */   
/*      */   public void dropChest()
/*      */   {
/*  872 */     a(this, this.inventoryChest);
/*  873 */     cE();
/*      */   }
/*      */   
/*      */   private void a(Entity entity, InventoryHorseChest inventoryhorsechest) {
/*  877 */     if ((inventoryhorsechest != null) && (!this.world.isClientSide)) {
/*  878 */       for (int i = 0; i < inventoryhorsechest.getSize(); i++) {
/*  879 */         ItemStack itemstack = inventoryhorsechest.getItem(i);
/*      */         
/*  881 */         if (itemstack != null) {
/*  882 */           a(itemstack, 0.0F);
/*      */         }
/*      */       }
/*      */     }
/*      */   }
/*      */   
/*      */   public boolean h(EntityHuman entityhuman)
/*      */   {
/*  890 */     setOwnerUUID(entityhuman.getUniqueID().toString());
/*  891 */     setTame(true);
/*  892 */     return true;
/*      */   }
/*      */   
/*      */   public void g(float f, float f1) {
/*  896 */     if ((this.passenger != null) && ((this.passenger instanceof EntityLiving)) && (cG())) {
/*  897 */       this.lastYaw = (this.yaw = this.passenger.yaw);
/*  898 */       this.pitch = (this.passenger.pitch * 0.5F);
/*  899 */       setYawPitch(this.yaw, this.pitch);
/*  900 */       this.aK = (this.aI = this.yaw);
/*  901 */       f = ((EntityLiving)this.passenger).aZ * 0.5F;
/*  902 */       f1 = ((EntityLiving)this.passenger).ba;
/*  903 */       if (f1 <= 0.0F) {
/*  904 */         f1 *= 0.25F;
/*  905 */         this.bN = 0;
/*      */       }
/*      */       
/*  908 */       if ((this.onGround) && (this.br == 0.0F) && (cz()) && (!this.bG)) {
/*  909 */         f = 0.0F;
/*  910 */         f1 = 0.0F;
/*      */       }
/*      */       
/*  913 */       if ((this.br > 0.0F) && (!cv()) && (this.onGround)) {
/*  914 */         this.motY = (getJumpStrength() * this.br);
/*  915 */         if (hasEffect(MobEffectList.JUMP)) {
/*  916 */           this.motY += (getEffect(MobEffectList.JUMP).getAmplifier() + 1) * 0.1F;
/*      */         }
/*      */         
/*  919 */         m(true);
/*  920 */         this.ai = true;
/*  921 */         if (f1 > 0.0F) {
/*  922 */           float f2 = MathHelper.sin(this.yaw * 3.1415927F / 180.0F);
/*  923 */           float f3 = MathHelper.cos(this.yaw * 3.1415927F / 180.0F);
/*      */           
/*  925 */           this.motX += -0.4F * f2 * this.br;
/*  926 */           this.motZ += 0.4F * f3 * this.br;
/*  927 */           makeSound("mob.horse.jump", 0.4F, 1.0F);
/*      */         }
/*      */         
/*  930 */         this.br = 0.0F;
/*      */       }
/*      */       
/*  933 */       this.S = 1.0F;
/*  934 */       this.aM = (bI() * 0.1F);
/*  935 */       if (!this.world.isClientSide) {
/*  936 */         k((float)getAttributeInstance(GenericAttributes.MOVEMENT_SPEED).getValue());
/*  937 */         super.g(f, f1);
/*      */       }
/*      */       
/*  940 */       if (this.onGround) {
/*  941 */         this.br = 0.0F;
/*  942 */         m(false);
/*      */       }
/*      */       
/*  945 */       this.aA = this.aB;
/*  946 */       double d0 = this.locX - this.lastX;
/*  947 */       double d1 = this.locZ - this.lastZ;
/*  948 */       float f4 = MathHelper.sqrt(d0 * d0 + d1 * d1) * 4.0F;
/*      */       
/*  950 */       if (f4 > 1.0F) {
/*  951 */         f4 = 1.0F;
/*      */       }
/*      */       
/*  954 */       this.aB += (f4 - this.aB) * 0.4F;
/*  955 */       this.aC += this.aB;
/*      */     } else {
/*  957 */       this.S = 0.5F;
/*  958 */       this.aM = 0.02F;
/*  959 */       super.g(f, f1);
/*      */     }
/*      */   }
/*      */   
/*      */   public void b(NBTTagCompound nbttagcompound) {
/*  964 */     super.b(nbttagcompound);
/*  965 */     nbttagcompound.setBoolean("EatingHaystack", cy());
/*  966 */     nbttagcompound.setBoolean("ChestedHorse", hasChest());
/*  967 */     nbttagcompound.setBoolean("HasReproduced", cB());
/*  968 */     nbttagcompound.setBoolean("Bred", cA());
/*  969 */     nbttagcompound.setInt("Type", getType());
/*  970 */     nbttagcompound.setInt("Variant", getVariant());
/*  971 */     nbttagcompound.setInt("Temper", getTemper());
/*  972 */     nbttagcompound.setBoolean("Tame", isTame());
/*  973 */     nbttagcompound.setString("OwnerUUID", getOwnerUUID());
/*  974 */     nbttagcompound.setInt("Bukkit.MaxDomestication", this.maxDomestication);
/*  975 */     if (hasChest()) {
/*  976 */       NBTTagList nbttaglist = new NBTTagList();
/*      */       
/*  978 */       for (int i = 2; i < this.inventoryChest.getSize(); i++) {
/*  979 */         ItemStack itemstack = this.inventoryChest.getItem(i);
/*      */         
/*  981 */         if (itemstack != null) {
/*  982 */           NBTTagCompound nbttagcompound1 = new NBTTagCompound();
/*      */           
/*  984 */           nbttagcompound1.setByte("Slot", (byte)i);
/*  985 */           itemstack.save(nbttagcompound1);
/*  986 */           nbttaglist.add(nbttagcompound1);
/*      */         }
/*      */       }
/*      */       
/*  990 */       nbttagcompound.set("Items", nbttaglist);
/*      */     }
/*      */     
/*  993 */     if (this.inventoryChest.getItem(1) != null) {
/*  994 */       nbttagcompound.set("ArmorItem", this.inventoryChest.getItem(1).save(new NBTTagCompound()));
/*      */     }
/*      */     
/*  997 */     if (this.inventoryChest.getItem(0) != null) {
/*  998 */       nbttagcompound.set("SaddleItem", this.inventoryChest.getItem(0).save(new NBTTagCompound()));
/*      */     }
/*      */   }
/*      */   
/*      */   public void a(NBTTagCompound nbttagcompound)
/*      */   {
/* 1004 */     super.a(nbttagcompound);
/* 1005 */     r(nbttagcompound.getBoolean("EatingHaystack"));
/* 1006 */     n(nbttagcompound.getBoolean("Bred"));
/* 1007 */     setHasChest(nbttagcompound.getBoolean("ChestedHorse"));
/* 1008 */     p(nbttagcompound.getBoolean("HasReproduced"));
/* 1009 */     setType(nbttagcompound.getInt("Type"));
/* 1010 */     setVariant(nbttagcompound.getInt("Variant"));
/* 1011 */     setTemper(nbttagcompound.getInt("Temper"));
/* 1012 */     setTame(nbttagcompound.getBoolean("Tame"));
/* 1013 */     String s = "";
/*      */     
/* 1015 */     if (nbttagcompound.hasKeyOfType("OwnerUUID", 8)) {
/* 1016 */       s = nbttagcompound.getString("OwnerUUID");
/*      */     } else {
/* 1018 */       String s1 = nbttagcompound.getString("Owner");
/*      */       
/* 1020 */       if ((s1 == null) || (s1.isEmpty()))
/*      */       {
/* 1022 */         if (nbttagcompound.hasKey("OwnerName")) {
/* 1023 */           String owner = nbttagcompound.getString("OwnerName");
/* 1024 */           if ((owner != null) && (!owner.isEmpty())) {
/* 1025 */             s1 = owner;
/*      */           }
/*      */         }
/*      */       }
/*      */       
/*      */ 
/* 1031 */       s = NameReferencingFileConverter.a(s1);
/*      */     }
/*      */     
/* 1034 */     if (s.length() > 0) {
/* 1035 */       setOwnerUUID(s);
/*      */     }
/*      */     
/*      */ 
/* 1039 */     if (nbttagcompound.hasKey("Bukkit.MaxDomestication")) {
/* 1040 */       this.maxDomestication = nbttagcompound.getInt("Bukkit.MaxDomestication");
/*      */     }
/*      */     
/*      */ 
/* 1044 */     AttributeInstance attributeinstance = getAttributeMap().a("Speed");
/*      */     
/* 1046 */     if (attributeinstance != null) {
/* 1047 */       getAttributeInstance(GenericAttributes.MOVEMENT_SPEED).setValue(attributeinstance.b() * 0.25D);
/*      */     }
/*      */     
/* 1050 */     if (hasChest()) {
/* 1051 */       NBTTagList nbttaglist = nbttagcompound.getList("Items", 10);
/*      */       
/* 1053 */       loadChest();
/*      */       
/* 1055 */       for (int i = 0; i < nbttaglist.size(); i++) {
/* 1056 */         NBTTagCompound nbttagcompound1 = nbttaglist.get(i);
/* 1057 */         int j = nbttagcompound1.getByte("Slot") & 0xFF;
/*      */         
/* 1059 */         if ((j >= 2) && (j < this.inventoryChest.getSize())) {
/* 1060 */           this.inventoryChest.setItem(j, ItemStack.createStack(nbttagcompound1));
/*      */         }
/*      */       }
/*      */     }
/*      */     
/*      */ 
/*      */ 
/* 1067 */     if (nbttagcompound.hasKeyOfType("ArmorItem", 10)) {
/* 1068 */       ItemStack itemstack = ItemStack.createStack(nbttagcompound.getCompound("ArmorItem"));
/* 1069 */       if ((itemstack != null) && (a(itemstack.getItem()))) {
/* 1070 */         this.inventoryChest.setItem(1, itemstack);
/*      */       }
/*      */     }
/*      */     
/* 1074 */     if (nbttagcompound.hasKeyOfType("SaddleItem", 10)) {
/* 1075 */       ItemStack itemstack = ItemStack.createStack(nbttagcompound.getCompound("SaddleItem"));
/* 1076 */       if ((itemstack != null) && (itemstack.getItem() == Items.SADDLE)) {
/* 1077 */         this.inventoryChest.setItem(0, itemstack);
/*      */       }
/* 1079 */     } else if (nbttagcompound.getBoolean("Saddle")) {
/* 1080 */       this.inventoryChest.setItem(0, new ItemStack(Items.SADDLE));
/*      */     }
/*      */     
/* 1083 */     db();
/*      */   }
/*      */   
/*      */   public boolean mate(EntityAnimal entityanimal) {
/* 1087 */     if (entityanimal == this)
/* 1088 */       return false;
/* 1089 */     if (entityanimal.getClass() != getClass()) {
/* 1090 */       return false;
/*      */     }
/* 1092 */     EntityHorse entityhorse = (EntityHorse)entityanimal;
/*      */     
/* 1094 */     if ((dg()) && (entityhorse.dg())) {
/* 1095 */       int i = getType();
/* 1096 */       int j = entityhorse.getType();
/*      */       
/* 1098 */       return (i == j) || ((i == 0) && (j == 1)) || ((i == 1) && (j == 0));
/*      */     }
/* 1100 */     return false;
/*      */   }
/*      */   
/*      */ 
/*      */   public EntityAgeable createChild(EntityAgeable entityageable)
/*      */   {
/* 1106 */     EntityHorse entityhorse = (EntityHorse)entityageable;
/* 1107 */     EntityHorse entityhorse1 = new EntityHorse(this.world);
/* 1108 */     int i = getType();
/* 1109 */     int j = entityhorse.getType();
/* 1110 */     int k = 0;
/*      */     
/* 1112 */     if (i == j) {
/* 1113 */       k = i;
/* 1114 */     } else if (((i == 0) && (j == 1)) || ((i == 1) && (j == 0))) {
/* 1115 */       k = 2;
/*      */     }
/*      */     
/* 1118 */     if (k == 0) {
/* 1119 */       int l = this.random.nextInt(9);
/*      */       int i1;
/*      */       int i1;
/* 1122 */       if (l < 4) {
/* 1123 */         i1 = getVariant() & 0xFF; } else { int i1;
/* 1124 */         if (l < 8) {
/* 1125 */           i1 = entityhorse.getVariant() & 0xFF;
/*      */         } else {
/* 1127 */           i1 = this.random.nextInt(7);
/*      */         }
/*      */       }
/* 1130 */       int j1 = this.random.nextInt(5);
/*      */       
/* 1132 */       if (j1 < 2) {
/* 1133 */         i1 |= getVariant() & 0xFF00;
/* 1134 */       } else if (j1 < 4) {
/* 1135 */         i1 |= entityhorse.getVariant() & 0xFF00;
/*      */       } else {
/* 1137 */         i1 |= this.random.nextInt(5) << 8 & 0xFF00;
/*      */       }
/*      */       
/* 1140 */       entityhorse1.setVariant(i1);
/*      */     }
/*      */     
/* 1143 */     entityhorse1.setType(k);
/* 1144 */     double d0 = getAttributeInstance(GenericAttributes.maxHealth).b() + entityageable.getAttributeInstance(GenericAttributes.maxHealth).b() + di();
/*      */     
/* 1146 */     entityhorse1.getAttributeInstance(GenericAttributes.maxHealth).setValue(d0 / 3.0D);
/* 1147 */     double d1 = getAttributeInstance(attributeJumpStrength).b() + entityageable.getAttributeInstance(attributeJumpStrength).b() + dj();
/*      */     
/* 1149 */     entityhorse1.getAttributeInstance(attributeJumpStrength).setValue(d1 / 3.0D);
/* 1150 */     double d2 = getAttributeInstance(GenericAttributes.MOVEMENT_SPEED).b() + entityageable.getAttributeInstance(GenericAttributes.MOVEMENT_SPEED).b() + dk();
/*      */     
/* 1152 */     entityhorse1.getAttributeInstance(GenericAttributes.MOVEMENT_SPEED).setValue(d2 / 3.0D);
/* 1153 */     return entityhorse1;
/*      */   }
/*      */   
/*      */   public GroupDataEntity prepare(DifficultyDamageScaler difficultydamagescaler, GroupDataEntity groupdataentity) {
/* 1157 */     Object object = super.prepare(difficultydamagescaler, groupdataentity);
/*      */     
/* 1159 */     int i = 0;
/*      */     
/*      */     int j;
/* 1162 */     if ((object instanceof GroupDataHorse)) {
/* 1163 */       int j = ((GroupDataHorse)object).a;
/* 1164 */       i = ((GroupDataHorse)object).b & 0xFF | this.random.nextInt(5) << 8;
/*      */     } else { int j;
/* 1166 */       if (this.random.nextInt(10) == 0) {
/* 1167 */         j = 1;
/*      */       } else {
/* 1169 */         int k = this.random.nextInt(7);
/* 1170 */         int l = this.random.nextInt(5);
/*      */         
/* 1172 */         j = 0;
/* 1173 */         i = k | l << 8;
/*      */       }
/*      */       
/* 1176 */       object = new GroupDataHorse(j, i);
/*      */     }
/*      */     
/* 1179 */     setType(j);
/* 1180 */     setVariant(i);
/* 1181 */     if (this.random.nextInt(5) == 0) {
/* 1182 */       setAgeRaw(41536);
/*      */     }
/*      */     
/* 1185 */     if ((j != 4) && (j != 3)) {
/* 1186 */       getAttributeInstance(GenericAttributes.maxHealth).setValue(di());
/* 1187 */       if (j == 0) {
/* 1188 */         getAttributeInstance(GenericAttributes.MOVEMENT_SPEED).setValue(dk());
/*      */       } else {
/* 1190 */         getAttributeInstance(GenericAttributes.MOVEMENT_SPEED).setValue(0.17499999701976776D);
/*      */       }
/*      */     } else {
/* 1193 */       getAttributeInstance(GenericAttributes.maxHealth).setValue(15.0D);
/* 1194 */       getAttributeInstance(GenericAttributes.MOVEMENT_SPEED).setValue(0.20000000298023224D);
/*      */     }
/*      */     
/* 1197 */     if ((j != 2) && (j != 1)) {
/* 1198 */       getAttributeInstance(attributeJumpStrength).setValue(dj());
/*      */     } else {
/* 1200 */       getAttributeInstance(attributeJumpStrength).setValue(0.5D);
/*      */     }
/*      */     
/* 1203 */     setHealth(getMaxHealth());
/* 1204 */     return (GroupDataEntity)object;
/*      */   }
/*      */   
/*      */   public void v(int i) {
/* 1208 */     if (cG())
/*      */     {
/* 1210 */       if (i < 0) {
/* 1211 */         i = 0;
/*      */       }
/*      */       float power;
/*      */       float power;
/* 1215 */       if (i >= 90) {
/* 1216 */         power = 1.0F;
/*      */       } else {
/* 1218 */         power = 0.4F + 0.4F * i / 90.0F;
/*      */       }
/*      */       
/* 1221 */       org.bukkit.event.entity.HorseJumpEvent event = org.bukkit.craftbukkit.v1_8_R3.event.CraftEventFactory.callHorseJumpEvent(this, power);
/* 1222 */       if (!event.isCancelled()) {
/* 1223 */         this.bG = true;
/* 1224 */         dh();
/* 1225 */         this.br = power;
/*      */       }
/*      */     }
/*      */   }
/*      */   
/*      */ 
/*      */   public void al()
/*      */   {
/* 1233 */     super.al();
/* 1234 */     if (this.bK > 0.0F) {
/* 1235 */       float f = MathHelper.sin(this.aI * 3.1415927F / 180.0F);
/* 1236 */       float f1 = MathHelper.cos(this.aI * 3.1415927F / 180.0F);
/* 1237 */       float f2 = 0.7F * this.bK;
/* 1238 */       float f3 = 0.15F * this.bK;
/*      */       
/* 1240 */       this.passenger.setPosition(this.locX + f2 * f, this.locY + an() + this.passenger.am() + f3, this.locZ - f2 * f1);
/* 1241 */       if ((this.passenger instanceof EntityLiving)) {
/* 1242 */         ((EntityLiving)this.passenger).aI = this.aI;
/*      */       }
/*      */     }
/*      */   }
/*      */   
/*      */   private float di()
/*      */   {
/* 1249 */     return 15.0F + this.random.nextInt(8) + this.random.nextInt(9);
/*      */   }
/*      */   
/*      */   private double dj() {
/* 1253 */     return 0.4000000059604645D + this.random.nextDouble() * 0.2D + this.random.nextDouble() * 0.2D + this.random.nextDouble() * 0.2D;
/*      */   }
/*      */   
/*      */   private double dk() {
/* 1257 */     return (0.44999998807907104D + this.random.nextDouble() * 0.3D + this.random.nextDouble() * 0.3D + this.random.nextDouble() * 0.3D) * 0.25D;
/*      */   }
/*      */   
/*      */   public static boolean a(Item item) {
/* 1261 */     return (item == Items.IRON_HORSE_ARMOR) || (item == Items.GOLDEN_HORSE_ARMOR) || (item == Items.DIAMOND_HORSE_ARMOR);
/*      */   }
/*      */   
/*      */   public boolean k_() {
/* 1265 */     return false;
/*      */   }
/*      */   
/*      */   public float getHeadHeight() {
/* 1269 */     return this.length;
/*      */   }
/*      */   
/*      */   public boolean d(int i, ItemStack itemstack) {
/* 1273 */     if ((i == 499) && (cP())) {
/* 1274 */       if ((itemstack == null) && (hasChest())) {
/* 1275 */         setHasChest(false);
/* 1276 */         loadChest();
/* 1277 */         return true;
/*      */       }
/*      */       
/* 1280 */       if ((itemstack != null) && (itemstack.getItem() == Item.getItemOf(Blocks.CHEST)) && (!hasChest())) {
/* 1281 */         setHasChest(true);
/* 1282 */         loadChest();
/* 1283 */         return true;
/*      */       }
/*      */     }
/*      */     
/* 1287 */     int j = i - 400;
/*      */     
/* 1289 */     if ((j >= 0) && (j < 2) && (j < this.inventoryChest.getSize())) {
/* 1290 */       if ((j == 0) && (itemstack != null) && (itemstack.getItem() != Items.SADDLE))
/* 1291 */         return false;
/* 1292 */       if ((j == 1) && (((itemstack != null) && (!a(itemstack.getItem()))) || (!cO()))) {
/* 1293 */         return false;
/*      */       }
/* 1295 */       this.inventoryChest.setItem(j, itemstack);
/* 1296 */       db();
/* 1297 */       return true;
/*      */     }
/*      */     
/* 1300 */     int k = i - 500 + 2;
/*      */     
/* 1302 */     if ((k >= 2) && (k < this.inventoryChest.getSize())) {
/* 1303 */       this.inventoryChest.setItem(k, itemstack);
/* 1304 */       return true;
/*      */     }
/* 1306 */     return false;
/*      */   }
/*      */   
/*      */   public static class GroupDataHorse
/*      */     implements GroupDataEntity
/*      */   {
/*      */     public int a;
/*      */     public int b;
/*      */     
/*      */     public GroupDataHorse(int i, int j)
/*      */     {
/* 1317 */       this.a = i;
/* 1318 */       this.b = j;
/*      */     }
/*      */   }
/*      */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_8_R3\EntityHorse.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */
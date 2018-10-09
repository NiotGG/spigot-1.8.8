/*     */ package net.minecraft.server.v1_8_R3;
/*     */ 
/*     */ import java.util.List;
/*     */ import org.bukkit.craftbukkit.v1_8_R3.CraftEquipmentSlot;
/*     */ import org.bukkit.craftbukkit.v1_8_R3.CraftServer;
/*     */ import org.bukkit.craftbukkit.v1_8_R3.event.CraftEventFactory;
/*     */ import org.bukkit.craftbukkit.v1_8_R3.inventory.CraftItemStack;
/*     */ import org.bukkit.entity.ArmorStand;
/*     */ import org.bukkit.entity.Player;
/*     */ import org.bukkit.event.player.PlayerArmorStandManipulateEvent;
/*     */ import org.bukkit.inventory.EquipmentSlot;
/*     */ import org.bukkit.plugin.PluginManager;
/*     */ 
/*     */ public class EntityArmorStand extends EntityLiving
/*     */ {
/*  16 */   private static final Vector3f a = new Vector3f(0.0F, 0.0F, 0.0F);
/*  17 */   private static final Vector3f b = new Vector3f(0.0F, 0.0F, 0.0F);
/*  18 */   private static final Vector3f c = new Vector3f(-10.0F, 0.0F, -10.0F);
/*  19 */   private static final Vector3f d = new Vector3f(-15.0F, 0.0F, 10.0F);
/*  20 */   private static final Vector3f e = new Vector3f(-1.0F, 0.0F, -1.0F);
/*  21 */   private static final Vector3f f = new Vector3f(1.0F, 0.0F, 1.0F);
/*     */   private final ItemStack[] items;
/*     */   private boolean h;
/*     */   private long i;
/*     */   private int bi;
/*     */   private boolean bj;
/*     */   public Vector3f headPose;
/*     */   public Vector3f bodyPose;
/*     */   public Vector3f leftArmPose;
/*     */   public Vector3f rightArmPose;
/*     */   public Vector3f leftLegPose;
/*     */   public Vector3f rightLegPose;
/*     */   
/*     */   public EntityArmorStand(World world) {
/*  35 */     super(world);
/*  36 */     this.items = new ItemStack[5];
/*  37 */     this.headPose = a;
/*  38 */     this.bodyPose = b;
/*  39 */     this.leftArmPose = c;
/*  40 */     this.rightArmPose = d;
/*  41 */     this.leftLegPose = e;
/*  42 */     this.rightLegPose = f;
/*  43 */     b(true);
/*  44 */     this.noclip = hasGravity();
/*  45 */     setSize(0.5F, 1.975F);
/*     */   }
/*     */   
/*     */   public EntityArmorStand(World world, double d0, double d1, double d2) {
/*  49 */     this(world);
/*  50 */     setPosition(d0, d1, d2);
/*     */   }
/*     */   
/*     */   public boolean bM() {
/*  54 */     return (super.bM()) && (!hasGravity());
/*     */   }
/*     */   
/*     */   protected void h() {
/*  58 */     super.h();
/*  59 */     this.datawatcher.a(10, Byte.valueOf((byte)0));
/*  60 */     this.datawatcher.a(11, a);
/*  61 */     this.datawatcher.a(12, b);
/*  62 */     this.datawatcher.a(13, c);
/*  63 */     this.datawatcher.a(14, d);
/*  64 */     this.datawatcher.a(15, e);
/*  65 */     this.datawatcher.a(16, f);
/*     */   }
/*     */   
/*     */   public ItemStack bA() {
/*  69 */     return this.items[0];
/*     */   }
/*     */   
/*     */   public ItemStack getEquipment(int i) {
/*  73 */     return this.items[i];
/*     */   }
/*     */   
/*     */   public void setEquipment(int i, ItemStack itemstack) {
/*  77 */     this.items[i] = itemstack;
/*     */   }
/*     */   
/*     */   public ItemStack[] getEquipment() {
/*  81 */     return this.items;
/*     */   }
/*     */   
/*     */   public boolean d(int i, ItemStack itemstack) {
/*     */     int j;
/*     */     int j;
/*  87 */     if (i == 99) {
/*  88 */       j = 0;
/*     */     } else {
/*  90 */       j = i - 100 + 1;
/*  91 */       if ((j < 0) || (j >= this.items.length)) {
/*  92 */         return false;
/*     */       }
/*     */     }
/*     */     
/*  96 */     if ((itemstack != null) && (EntityInsentient.c(itemstack) != j) && ((j != 4) || (!(itemstack.getItem() instanceof ItemBlock)))) {
/*  97 */       return false;
/*     */     }
/*  99 */     setEquipment(j, itemstack);
/* 100 */     return true;
/*     */   }
/*     */   
/*     */   public void b(NBTTagCompound nbttagcompound)
/*     */   {
/* 105 */     super.b(nbttagcompound);
/* 106 */     NBTTagList nbttaglist = new NBTTagList();
/*     */     
/* 108 */     for (int i = 0; i < this.items.length; i++) {
/* 109 */       NBTTagCompound nbttagcompound1 = new NBTTagCompound();
/*     */       
/* 111 */       if (this.items[i] != null) {
/* 112 */         this.items[i].save(nbttagcompound1);
/*     */       }
/*     */       
/* 115 */       nbttaglist.add(nbttagcompound1);
/*     */     }
/*     */     
/* 118 */     nbttagcompound.set("Equipment", nbttaglist);
/* 119 */     if ((getCustomNameVisible()) && ((getCustomName() == null) || (getCustomName().length() == 0))) {
/* 120 */       nbttagcompound.setBoolean("CustomNameVisible", getCustomNameVisible());
/*     */     }
/*     */     
/* 123 */     nbttagcompound.setBoolean("Invisible", isInvisible());
/* 124 */     nbttagcompound.setBoolean("Small", isSmall());
/* 125 */     nbttagcompound.setBoolean("ShowArms", hasArms());
/* 126 */     nbttagcompound.setInt("DisabledSlots", this.bi);
/* 127 */     nbttagcompound.setBoolean("NoGravity", hasGravity());
/* 128 */     nbttagcompound.setBoolean("NoBasePlate", hasBasePlate());
/* 129 */     if (s()) {
/* 130 */       nbttagcompound.setBoolean("Marker", s());
/*     */     }
/*     */     
/* 133 */     nbttagcompound.set("Pose", z());
/*     */   }
/*     */   
/*     */   public void a(NBTTagCompound nbttagcompound) {
/* 137 */     super.a(nbttagcompound);
/* 138 */     if (nbttagcompound.hasKeyOfType("Equipment", 9)) {
/* 139 */       NBTTagList nbttaglist = nbttagcompound.getList("Equipment", 10);
/*     */       
/* 141 */       for (int i = 0; i < this.items.length; i++) {
/* 142 */         this.items[i] = ItemStack.createStack(nbttaglist.get(i));
/*     */       }
/*     */     }
/*     */     
/* 146 */     setInvisible(nbttagcompound.getBoolean("Invisible"));
/* 147 */     setSmall(nbttagcompound.getBoolean("Small"));
/* 148 */     setArms(nbttagcompound.getBoolean("ShowArms"));
/* 149 */     this.bi = nbttagcompound.getInt("DisabledSlots");
/* 150 */     setGravity(nbttagcompound.getBoolean("NoGravity"));
/* 151 */     setBasePlate(nbttagcompound.getBoolean("NoBasePlate"));
/* 152 */     n(nbttagcompound.getBoolean("Marker"));
/* 153 */     this.bj = (!s());
/* 154 */     this.noclip = hasGravity();
/* 155 */     NBTTagCompound nbttagcompound1 = nbttagcompound.getCompound("Pose");
/*     */     
/* 157 */     h(nbttagcompound1);
/*     */   }
/*     */   
/*     */   private void h(NBTTagCompound nbttagcompound) {
/* 161 */     NBTTagList nbttaglist = nbttagcompound.getList("Head", 5);
/*     */     
/* 163 */     if (nbttaglist.size() > 0) {
/* 164 */       setHeadPose(new Vector3f(nbttaglist));
/*     */     } else {
/* 166 */       setHeadPose(a);
/*     */     }
/*     */     
/* 169 */     NBTTagList nbttaglist1 = nbttagcompound.getList("Body", 5);
/*     */     
/* 171 */     if (nbttaglist1.size() > 0) {
/* 172 */       setBodyPose(new Vector3f(nbttaglist1));
/*     */     } else {
/* 174 */       setBodyPose(b);
/*     */     }
/*     */     
/* 177 */     NBTTagList nbttaglist2 = nbttagcompound.getList("LeftArm", 5);
/*     */     
/* 179 */     if (nbttaglist2.size() > 0) {
/* 180 */       setLeftArmPose(new Vector3f(nbttaglist2));
/*     */     } else {
/* 182 */       setLeftArmPose(c);
/*     */     }
/*     */     
/* 185 */     NBTTagList nbttaglist3 = nbttagcompound.getList("RightArm", 5);
/*     */     
/* 187 */     if (nbttaglist3.size() > 0) {
/* 188 */       setRightArmPose(new Vector3f(nbttaglist3));
/*     */     } else {
/* 190 */       setRightArmPose(d);
/*     */     }
/*     */     
/* 193 */     NBTTagList nbttaglist4 = nbttagcompound.getList("LeftLeg", 5);
/*     */     
/* 195 */     if (nbttaglist4.size() > 0) {
/* 196 */       setLeftLegPose(new Vector3f(nbttaglist4));
/*     */     } else {
/* 198 */       setLeftLegPose(e);
/*     */     }
/*     */     
/* 201 */     NBTTagList nbttaglist5 = nbttagcompound.getList("RightLeg", 5);
/*     */     
/* 203 */     if (nbttaglist5.size() > 0) {
/* 204 */       setRightLegPose(new Vector3f(nbttaglist5));
/*     */     } else {
/* 206 */       setRightLegPose(f);
/*     */     }
/*     */   }
/*     */   
/*     */   private NBTTagCompound z()
/*     */   {
/* 212 */     NBTTagCompound nbttagcompound = new NBTTagCompound();
/*     */     
/* 214 */     if (!a.equals(this.headPose)) {
/* 215 */       nbttagcompound.set("Head", this.headPose.a());
/*     */     }
/*     */     
/* 218 */     if (!b.equals(this.bodyPose)) {
/* 219 */       nbttagcompound.set("Body", this.bodyPose.a());
/*     */     }
/*     */     
/* 222 */     if (!c.equals(this.leftArmPose)) {
/* 223 */       nbttagcompound.set("LeftArm", this.leftArmPose.a());
/*     */     }
/*     */     
/* 226 */     if (!d.equals(this.rightArmPose)) {
/* 227 */       nbttagcompound.set("RightArm", this.rightArmPose.a());
/*     */     }
/*     */     
/* 230 */     if (!e.equals(this.leftLegPose)) {
/* 231 */       nbttagcompound.set("LeftLeg", this.leftLegPose.a());
/*     */     }
/*     */     
/* 234 */     if (!f.equals(this.rightLegPose)) {
/* 235 */       nbttagcompound.set("RightLeg", this.rightLegPose.a());
/*     */     }
/*     */     
/* 238 */     return nbttagcompound;
/*     */   }
/*     */   
/*     */   public boolean ae() {
/* 242 */     return false;
/*     */   }
/*     */   
/*     */   protected void s(Entity entity) {}
/*     */   
/*     */   protected void bL() {
/* 248 */     List list = this.world.getEntities(this, getBoundingBox());
/*     */     
/* 250 */     if ((list != null) && (!list.isEmpty())) {
/* 251 */       for (int i = 0; i < list.size(); i++) {
/* 252 */         Entity entity = (Entity)list.get(i);
/*     */         
/* 254 */         if (((entity instanceof EntityMinecartAbstract)) && (((EntityMinecartAbstract)entity).s() == EntityMinecartAbstract.EnumMinecartType.RIDEABLE) && (h(entity) <= 0.2D)) {
/* 255 */           entity.collide(this);
/*     */         }
/*     */       }
/*     */     }
/*     */   }
/*     */   
/*     */   public boolean a(EntityHuman entityhuman, Vec3D vec3d)
/*     */   {
/* 263 */     if (s())
/* 264 */       return false;
/* 265 */     if ((!this.world.isClientSide) && (!entityhuman.isSpectator())) {
/* 266 */       byte b0 = 0;
/* 267 */       ItemStack itemstack = entityhuman.bZ();
/* 268 */       boolean flag = itemstack != null;
/*     */       
/* 270 */       if ((flag) && ((itemstack.getItem() instanceof ItemArmor))) {
/* 271 */         ItemArmor itemarmor = (ItemArmor)itemstack.getItem();
/*     */         
/* 273 */         if (itemarmor.b == 3) {
/* 274 */           b0 = 1;
/* 275 */         } else if (itemarmor.b == 2) {
/* 276 */           b0 = 2;
/* 277 */         } else if (itemarmor.b == 1) {
/* 278 */           b0 = 3;
/* 279 */         } else if (itemarmor.b == 0) {
/* 280 */           b0 = 4;
/*     */         }
/*     */       }
/*     */       
/* 284 */       if ((flag) && ((itemstack.getItem() == Items.SKULL) || (itemstack.getItem() == Item.getItemOf(Blocks.PUMPKIN)))) {
/* 285 */         b0 = 4;
/*     */       }
/*     */       
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/* 292 */       byte b1 = 0;
/* 293 */       boolean flag1 = isSmall();
/* 294 */       double d4 = flag1 ? vec3d.b * 2.0D : vec3d.b;
/*     */       
/* 296 */       if (d4 >= 0.1D) if ((d4 < 0.1D + (flag1 ? 0.8D : 0.45D)) && (this.items[1] != null)) {
/* 297 */           b1 = 1;
/* 298 */           break label361; } if (d4 >= 0.9D + (flag1 ? 0.3D : 0.0D)) if ((d4 < 0.9D + (flag1 ? 1.0D : 0.7D)) && (this.items[3] != null)) {
/* 299 */           b1 = 3;
/* 300 */           break label361; } if (d4 >= 0.4D) if ((d4 < 0.4D + (flag1 ? 1.0D : 0.8D)) && (this.items[2] != null)) {
/* 301 */           b1 = 2;
/* 302 */           break label361; } if ((d4 >= 1.6D) && (this.items[4] != null)) {
/* 303 */         b1 = 4;
/*     */       }
/*     */       label361:
/* 306 */       boolean flag2 = this.items[b1] != null;
/*     */       
/* 308 */       if (((this.bi & 1 << b1) != 0) || ((this.bi & 1 << b0) != 0)) {
/* 309 */         b1 = b0;
/* 310 */         if ((this.bi & 1 << b0) != 0) {
/* 311 */           if ((this.bi & 0x1) != 0) {
/* 312 */             return true;
/*     */           }
/*     */           
/* 315 */           b1 = 0;
/*     */         }
/*     */       }
/*     */       
/* 319 */       if ((flag) && (b0 == 0) && (!hasArms())) {
/* 320 */         return true;
/*     */       }
/* 322 */       if (flag) {
/* 323 */         a(entityhuman, b0);
/* 324 */       } else if (flag2) {
/* 325 */         a(entityhuman, b1);
/*     */       }
/*     */       
/* 328 */       return true;
/*     */     }
/*     */     
/* 331 */     return true;
/*     */   }
/*     */   
/*     */   private void a(EntityHuman entityhuman, int i)
/*     */   {
/* 336 */     ItemStack itemstack = this.items[i];
/*     */     
/* 338 */     if (((itemstack == null) || ((this.bi & 1 << i + 8) == 0)) && (
/* 339 */       (itemstack != null) || ((this.bi & 1 << i + 16) == 0))) {
/* 340 */       int j = entityhuman.inventory.itemInHandIndex;
/* 341 */       ItemStack itemstack1 = entityhuman.inventory.getItem(j);
/*     */       
/*     */ 
/*     */ 
/* 345 */       org.bukkit.inventory.ItemStack armorStandItem = CraftItemStack.asCraftMirror(itemstack);
/* 346 */       org.bukkit.inventory.ItemStack playerHeldItem = CraftItemStack.asCraftMirror(itemstack1);
/*     */       
/* 348 */       Player player = (Player)entityhuman.getBukkitEntity();
/* 349 */       ArmorStand self = (ArmorStand)getBukkitEntity();
/*     */       
/* 351 */       EquipmentSlot slot = CraftEquipmentSlot.getSlot(i);
/* 352 */       PlayerArmorStandManipulateEvent armorStandManipulateEvent = new PlayerArmorStandManipulateEvent(player, self, playerHeldItem, armorStandItem, slot);
/* 353 */       this.world.getServer().getPluginManager().callEvent(armorStandManipulateEvent);
/*     */       
/* 355 */       if (armorStandManipulateEvent.isCancelled()) {
/* 356 */         return;
/*     */       }
/*     */       
/*     */ 
/* 360 */       if ((entityhuman.abilities.canInstantlyBuild) && ((itemstack == null) || (itemstack.getItem() == Item.getItemOf(Blocks.AIR))) && (itemstack1 != null)) {
/* 361 */         ItemStack itemstack2 = itemstack1.cloneItemStack();
/* 362 */         itemstack2.count = 1;
/* 363 */         setEquipment(i, itemstack2);
/* 364 */       } else if ((itemstack1 != null) && (itemstack1.count > 1)) {
/* 365 */         if (itemstack == null) {
/* 366 */           ItemStack itemstack2 = itemstack1.cloneItemStack();
/* 367 */           itemstack2.count = 1;
/* 368 */           setEquipment(i, itemstack2);
/* 369 */           itemstack1.count -= 1;
/*     */         }
/*     */       } else {
/* 372 */         setEquipment(i, itemstack1);
/* 373 */         entityhuman.inventory.setItem(j, itemstack);
/*     */       }
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */   public boolean damageEntity(DamageSource damagesource, float f)
/*     */   {
/* 381 */     if (CraftEventFactory.handleNonLivingEntityDamageEvent(this, damagesource, f)) {
/* 382 */       return false;
/*     */     }
/*     */     
/* 385 */     if (this.world.isClientSide)
/* 386 */       return false;
/* 387 */     if (DamageSource.OUT_OF_WORLD.equals(damagesource)) {
/* 388 */       die();
/* 389 */       return false; }
/* 390 */     if ((!isInvulnerable(damagesource)) && (!this.h) && (!s())) {
/* 391 */       if (damagesource.isExplosion()) {
/* 392 */         D();
/* 393 */         die();
/* 394 */         return false; }
/* 395 */       if (DamageSource.FIRE.equals(damagesource)) {
/* 396 */         if (!isBurning()) {
/* 397 */           setOnFire(5);
/*     */         } else {
/* 399 */           a(0.15F);
/*     */         }
/*     */         
/* 402 */         return false; }
/* 403 */       if ((DamageSource.BURN.equals(damagesource)) && (getHealth() > 0.5F)) {
/* 404 */         a(4.0F);
/* 405 */         return false;
/*     */       }
/* 407 */       boolean flag = "arrow".equals(damagesource.p());
/* 408 */       boolean flag1 = "player".equals(damagesource.p());
/*     */       
/* 410 */       if ((!flag1) && (!flag)) {
/* 411 */         return false;
/*     */       }
/* 413 */       if ((damagesource.i() instanceof EntityArrow)) {
/* 414 */         damagesource.i().die();
/*     */       }
/*     */       
/* 417 */       if (((damagesource.getEntity() instanceof EntityHuman)) && (!((EntityHuman)damagesource.getEntity()).abilities.mayBuild))
/* 418 */         return false;
/* 419 */       if (damagesource.u()) {
/* 420 */         A();
/* 421 */         die();
/* 422 */         return false;
/*     */       }
/* 424 */       long i = this.world.getTime();
/*     */       
/* 426 */       if ((i - this.i > 5L) && (!flag)) {
/* 427 */         this.i = i;
/*     */       } else {
/* 429 */         C();
/* 430 */         A();
/* 431 */         die();
/*     */       }
/*     */       
/* 434 */       return false;
/*     */     }
/*     */     
/*     */ 
/*     */ 
/* 439 */     return false;
/*     */   }
/*     */   
/*     */   private void A()
/*     */   {
/* 444 */     if ((this.world instanceof WorldServer)) {
/* 445 */       ((WorldServer)this.world).a(EnumParticle.BLOCK_DUST, this.locX, this.locY + this.length / 1.5D, this.locZ, 10, this.width / 4.0F, this.length / 4.0F, this.width / 4.0F, 0.05D, new int[] { Block.getCombinedId(Blocks.PLANKS.getBlockData()) });
/*     */     }
/*     */   }
/*     */   
/*     */   private void a(float f)
/*     */   {
/* 451 */     float f1 = getHealth();
/*     */     
/* 453 */     f1 -= f;
/* 454 */     if (f1 <= 0.5F) {
/* 455 */       D();
/* 456 */       die();
/*     */     } else {
/* 458 */       setHealth(f1);
/*     */     }
/*     */   }
/*     */   
/*     */   private void C()
/*     */   {
/* 464 */     Block.a(this.world, new BlockPosition(this), new ItemStack(Items.ARMOR_STAND));
/* 465 */     D();
/*     */   }
/*     */   
/*     */   private void D() {
/* 469 */     for (int i = 0; i < this.items.length; i++) {
/* 470 */       if ((this.items[i] != null) && (this.items[i].count > 0)) {
/* 471 */         if (this.items[i] != null) {
/* 472 */           Block.a(this.world, new BlockPosition(this).up(), this.items[i]);
/*     */         }
/*     */         
/* 475 */         this.items[i] = null;
/*     */       }
/*     */     }
/*     */   }
/*     */   
/*     */   protected float h(float f, float f1)
/*     */   {
/* 482 */     this.aJ = this.lastYaw;
/* 483 */     this.aI = this.yaw;
/* 484 */     return 0.0F;
/*     */   }
/*     */   
/*     */   public float getHeadHeight() {
/* 488 */     return isBaby() ? this.length * 0.5F : this.length * 0.9F;
/*     */   }
/*     */   
/*     */   public void g(float f, float f1) {
/* 492 */     if (!hasGravity()) {
/* 493 */       super.g(f, f1);
/*     */     }
/*     */   }
/*     */   
/*     */   public void t_() {
/* 498 */     super.t_();
/* 499 */     Vector3f vector3f = this.datawatcher.h(11);
/*     */     
/* 501 */     if (!this.headPose.equals(vector3f)) {
/* 502 */       setHeadPose(vector3f);
/*     */     }
/*     */     
/* 505 */     Vector3f vector3f1 = this.datawatcher.h(12);
/*     */     
/* 507 */     if (!this.bodyPose.equals(vector3f1)) {
/* 508 */       setBodyPose(vector3f1);
/*     */     }
/*     */     
/* 511 */     Vector3f vector3f2 = this.datawatcher.h(13);
/*     */     
/* 513 */     if (!this.leftArmPose.equals(vector3f2)) {
/* 514 */       setLeftArmPose(vector3f2);
/*     */     }
/*     */     
/* 517 */     Vector3f vector3f3 = this.datawatcher.h(14);
/*     */     
/* 519 */     if (!this.rightArmPose.equals(vector3f3)) {
/* 520 */       setRightArmPose(vector3f3);
/*     */     }
/*     */     
/* 523 */     Vector3f vector3f4 = this.datawatcher.h(15);
/*     */     
/* 525 */     if (!this.leftLegPose.equals(vector3f4)) {
/* 526 */       setLeftLegPose(vector3f4);
/*     */     }
/*     */     
/* 529 */     Vector3f vector3f5 = this.datawatcher.h(16);
/*     */     
/* 531 */     if (!this.rightLegPose.equals(vector3f5)) {
/* 532 */       setRightLegPose(vector3f5);
/*     */     }
/*     */     
/* 535 */     boolean flag = s();
/*     */     
/* 537 */     if ((!this.bj) && (flag)) {
/* 538 */       a(false);
/*     */     } else {
/* 540 */       if ((!this.bj) || (flag)) {
/* 541 */         return;
/*     */       }
/*     */       
/* 544 */       a(true);
/*     */     }
/*     */     
/* 547 */     this.bj = flag;
/*     */   }
/*     */   
/*     */   private void a(boolean flag) {
/* 551 */     double d0 = this.locX;
/* 552 */     double d1 = this.locY;
/* 553 */     double d2 = this.locZ;
/*     */     
/* 555 */     if (flag) {
/* 556 */       setSize(0.5F, 1.975F);
/*     */     } else {
/* 558 */       setSize(0.0F, 0.0F);
/*     */     }
/*     */     
/* 561 */     setPosition(d0, d1, d2);
/*     */   }
/*     */   
/*     */   protected void B() {
/* 565 */     setInvisible(this.h);
/*     */   }
/*     */   
/*     */   public void setInvisible(boolean flag) {
/* 569 */     this.h = flag;
/* 570 */     super.setInvisible(flag);
/*     */   }
/*     */   
/*     */   public boolean isBaby() {
/* 574 */     return isSmall();
/*     */   }
/*     */   
/*     */   public void G() {
/* 578 */     die();
/*     */   }
/*     */   
/*     */   public boolean aW() {
/* 582 */     return isInvisible();
/*     */   }
/*     */   
/*     */   public void setSmall(boolean flag) {
/* 586 */     byte b0 = this.datawatcher.getByte(10);
/*     */     
/* 588 */     if (flag) {
/* 589 */       b0 = (byte)(b0 | 0x1);
/*     */     } else {
/* 591 */       b0 = (byte)(b0 & 0xFFFFFFFE);
/*     */     }
/*     */     
/* 594 */     this.datawatcher.watch(10, Byte.valueOf(b0));
/*     */   }
/*     */   
/*     */   public boolean isSmall() {
/* 598 */     return (this.datawatcher.getByte(10) & 0x1) != 0;
/*     */   }
/*     */   
/*     */   public void setGravity(boolean flag) {
/* 602 */     byte b0 = this.datawatcher.getByte(10);
/*     */     
/* 604 */     if (flag) {
/* 605 */       b0 = (byte)(b0 | 0x2);
/*     */     } else {
/* 607 */       b0 = (byte)(b0 & 0xFFFFFFFD);
/*     */     }
/*     */     
/* 610 */     this.datawatcher.watch(10, Byte.valueOf(b0));
/*     */   }
/*     */   
/*     */   public boolean hasGravity() {
/* 614 */     return (this.datawatcher.getByte(10) & 0x2) != 0;
/*     */   }
/*     */   
/*     */   public void setArms(boolean flag) {
/* 618 */     byte b0 = this.datawatcher.getByte(10);
/*     */     
/* 620 */     if (flag) {
/* 621 */       b0 = (byte)(b0 | 0x4);
/*     */     } else {
/* 623 */       b0 = (byte)(b0 & 0xFFFFFFFB);
/*     */     }
/*     */     
/* 626 */     this.datawatcher.watch(10, Byte.valueOf(b0));
/*     */   }
/*     */   
/*     */   public boolean hasArms() {
/* 630 */     return (this.datawatcher.getByte(10) & 0x4) != 0;
/*     */   }
/*     */   
/*     */   public void setBasePlate(boolean flag) {
/* 634 */     byte b0 = this.datawatcher.getByte(10);
/*     */     
/* 636 */     if (flag) {
/* 637 */       b0 = (byte)(b0 | 0x8);
/*     */     } else {
/* 639 */       b0 = (byte)(b0 & 0xFFFFFFF7);
/*     */     }
/*     */     
/* 642 */     this.datawatcher.watch(10, Byte.valueOf(b0));
/*     */   }
/*     */   
/*     */   public boolean hasBasePlate() {
/* 646 */     return (this.datawatcher.getByte(10) & 0x8) != 0;
/*     */   }
/*     */   
/*     */   public void n(boolean flag)
/*     */   {
/* 651 */     byte b0 = this.datawatcher.getByte(10);
/*     */     
/* 653 */     if (flag) {
/* 654 */       b0 = (byte)(b0 | 0x10);
/*     */     } else {
/* 656 */       b0 = (byte)(b0 & 0xFFFFFFEF);
/*     */     }
/*     */     
/* 659 */     this.datawatcher.watch(10, Byte.valueOf(b0));
/*     */   }
/*     */   
/*     */   public boolean s()
/*     */   {
/* 664 */     return (this.datawatcher.getByte(10) & 0x10) != 0;
/*     */   }
/*     */   
/*     */   public void setHeadPose(Vector3f vector3f) {
/* 668 */     this.headPose = vector3f;
/* 669 */     this.datawatcher.watch(11, vector3f);
/*     */   }
/*     */   
/*     */   public void setBodyPose(Vector3f vector3f) {
/* 673 */     this.bodyPose = vector3f;
/* 674 */     this.datawatcher.watch(12, vector3f);
/*     */   }
/*     */   
/*     */   public void setLeftArmPose(Vector3f vector3f) {
/* 678 */     this.leftArmPose = vector3f;
/* 679 */     this.datawatcher.watch(13, vector3f);
/*     */   }
/*     */   
/*     */   public void setRightArmPose(Vector3f vector3f) {
/* 683 */     this.rightArmPose = vector3f;
/* 684 */     this.datawatcher.watch(14, vector3f);
/*     */   }
/*     */   
/*     */   public void setLeftLegPose(Vector3f vector3f) {
/* 688 */     this.leftLegPose = vector3f;
/* 689 */     this.datawatcher.watch(15, vector3f);
/*     */   }
/*     */   
/*     */   public void setRightLegPose(Vector3f vector3f) {
/* 693 */     this.rightLegPose = vector3f;
/* 694 */     this.datawatcher.watch(16, vector3f);
/*     */   }
/*     */   
/*     */   public Vector3f t() {
/* 698 */     return this.headPose;
/*     */   }
/*     */   
/*     */   public Vector3f u() {
/* 702 */     return this.bodyPose;
/*     */   }
/*     */   
/*     */   public boolean ad() {
/* 706 */     return (super.ad()) && (!s());
/*     */   }
/*     */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_8_R3\EntityArmorStand.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */
/*     */ package net.minecraft.server.v1_8_R3;
/*     */ 
/*     */ import java.util.Iterator;
/*     */ import java.util.Random;
/*     */ import org.bukkit.craftbukkit.v1_8_R3.event.CraftEventFactory;
/*     */ import org.bukkit.event.player.PlayerPickupItemEvent;
/*     */ 
/*     */ public class EntityItem extends Entity
/*     */ {
/*  10 */   private static final org.apache.logging.log4j.Logger b = ;
/*     */   private int age;
/*     */   public int pickupDelay;
/*     */   private int e;
/*     */   private String f;
/*     */   private String g;
/*     */   public float a;
/*  17 */   private int lastTick = MinecraftServer.currentTick;
/*     */   
/*     */   public EntityItem(World world, double d0, double d1, double d2) {
/*  20 */     super(world);
/*  21 */     this.e = 5;
/*  22 */     this.a = ((float)(Math.random() * 3.141592653589793D * 2.0D));
/*  23 */     setSize(0.25F, 0.25F);
/*  24 */     setPosition(d0, d1, d2);
/*  25 */     this.yaw = ((float)(Math.random() * 360.0D));
/*  26 */     this.motX = ((float)(Math.random() * 0.20000000298023224D - 0.10000000149011612D));
/*  27 */     this.motY = 0.20000000298023224D;
/*  28 */     this.motZ = ((float)(Math.random() * 0.20000000298023224D - 0.10000000149011612D));
/*     */   }
/*     */   
/*     */   public EntityItem(World world, double d0, double d1, double d2, ItemStack itemstack) {
/*  32 */     this(world, d0, d1, d2);
/*     */     
/*  34 */     if ((itemstack == null) || (itemstack.getItem() == null)) {
/*  35 */       return;
/*     */     }
/*     */     
/*  38 */     setItemStack(itemstack);
/*     */   }
/*     */   
/*     */   protected boolean s_() {
/*  42 */     return false;
/*     */   }
/*     */   
/*     */   public EntityItem(World world) {
/*  46 */     super(world);
/*  47 */     this.e = 5;
/*  48 */     this.a = ((float)(Math.random() * 3.141592653589793D * 2.0D));
/*  49 */     setSize(0.25F, 0.25F);
/*  50 */     setItemStack(new ItemStack(Blocks.AIR, 0));
/*     */   }
/*     */   
/*     */   protected void h() {
/*  54 */     getDataWatcher().add(10, 5);
/*     */   }
/*     */   
/*     */   public void t_() {
/*  58 */     if (getItemStack() == null) {
/*  59 */       die();
/*     */     } else {
/*  61 */       super.t_();
/*     */       
/*  63 */       int elapsedTicks = MinecraftServer.currentTick - this.lastTick;
/*  64 */       if (this.pickupDelay != 32767) this.pickupDelay -= elapsedTicks;
/*  65 */       if (this.age != 32768) this.age += elapsedTicks;
/*  66 */       this.lastTick = MinecraftServer.currentTick;
/*     */       
/*     */ 
/*  69 */       this.lastX = this.locX;
/*  70 */       this.lastY = this.locY;
/*  71 */       this.lastZ = this.locZ;
/*  72 */       this.motY -= 0.03999999910593033D;
/*  73 */       this.noclip = j(this.locX, (getBoundingBox().b + getBoundingBox().e) / 2.0D, this.locZ);
/*  74 */       move(this.motX, this.motY, this.motZ);
/*  75 */       boolean flag = ((int)this.lastX != (int)this.locX) || ((int)this.lastY != (int)this.locY) || ((int)this.lastZ != (int)this.locZ);
/*     */       
/*  77 */       if ((flag) || (this.ticksLived % 25 == 0)) {
/*  78 */         if (this.world.getType(new BlockPosition(this)).getBlock().getMaterial() == Material.LAVA) {
/*  79 */           this.motY = 0.20000000298023224D;
/*  80 */           this.motX = ((this.random.nextFloat() - this.random.nextFloat()) * 0.2F);
/*  81 */           this.motZ = ((this.random.nextFloat() - this.random.nextFloat()) * 0.2F);
/*  82 */           makeSound("random.fizz", 0.4F, 2.0F + this.random.nextFloat() * 0.4F);
/*     */         }
/*     */         
/*  85 */         if (!this.world.isClientSide) {
/*  86 */           w();
/*     */         }
/*     */       }
/*     */       
/*  90 */       float f = 0.98F;
/*     */       
/*  92 */       if (this.onGround) {
/*  93 */         f = this.world.getType(new BlockPosition(MathHelper.floor(this.locX), MathHelper.floor(getBoundingBox().b) - 1, MathHelper.floor(this.locZ))).getBlock().frictionFactor * 0.98F;
/*     */       }
/*     */       
/*  96 */       this.motX *= f;
/*  97 */       this.motY *= 0.9800000190734863D;
/*  98 */       this.motZ *= f;
/*  99 */       if (this.onGround) {
/* 100 */         this.motY *= -0.5D;
/*     */       }
/*     */       
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/* 110 */       W();
/* 111 */       if ((!this.world.isClientSide) && (this.age >= this.world.spigotConfig.itemDespawnRate))
/*     */       {
/* 113 */         if (CraftEventFactory.callItemDespawnEvent(this).isCancelled()) {
/* 114 */           this.age = 0;
/* 115 */           return;
/*     */         }
/*     */         
/* 118 */         die();
/*     */       }
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public void inactiveTick()
/*     */   {
/* 128 */     int elapsedTicks = MinecraftServer.currentTick - this.lastTick;
/* 129 */     if (this.pickupDelay != 32767) this.pickupDelay -= elapsedTicks;
/* 130 */     if (this.age != 32768) this.age += elapsedTicks;
/* 131 */     this.lastTick = MinecraftServer.currentTick;
/*     */     
/*     */ 
/* 134 */     if ((!this.world.isClientSide) && (this.age >= this.world.spigotConfig.itemDespawnRate))
/*     */     {
/* 136 */       if (CraftEventFactory.callItemDespawnEvent(this).isCancelled()) {
/* 137 */         this.age = 0;
/* 138 */         return;
/*     */       }
/*     */       
/* 141 */       die();
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */   private void w()
/*     */   {
/* 148 */     double radius = this.world.spigotConfig.itemMerge;
/* 149 */     Iterator iterator = this.world.a(EntityItem.class, getBoundingBox().grow(radius, radius, radius)).iterator();
/*     */     
/*     */ 
/* 152 */     while (iterator.hasNext()) {
/* 153 */       EntityItem entityitem = (EntityItem)iterator.next();
/*     */       
/* 155 */       a(entityitem);
/*     */     }
/*     */   }
/*     */   
/*     */   private boolean a(EntityItem entityitem)
/*     */   {
/* 161 */     if (entityitem == this)
/* 162 */       return false;
/* 163 */     if ((entityitem.isAlive()) && (isAlive())) {
/* 164 */       ItemStack itemstack = getItemStack();
/* 165 */       ItemStack itemstack1 = entityitem.getItemStack();
/*     */       
/* 167 */       if ((this.pickupDelay != 32767) && (entityitem.pickupDelay != 32767)) {
/* 168 */         if ((this.age != 32768) && (entityitem.age != 32768)) {
/* 169 */           if (itemstack1.getItem() != itemstack.getItem())
/* 170 */             return false;
/* 171 */           if ((itemstack1.hasTag() ^ itemstack.hasTag()))
/* 172 */             return false;
/* 173 */           if ((itemstack1.hasTag()) && (!itemstack1.getTag().equals(itemstack.getTag())))
/* 174 */             return false;
/* 175 */           if (itemstack1.getItem() == null)
/* 176 */             return false;
/* 177 */           if ((itemstack1.getItem().k()) && (itemstack1.getData() != itemstack.getData()))
/* 178 */             return false;
/* 179 */           if (itemstack1.count < itemstack.count)
/* 180 */             return entityitem.a(this);
/* 181 */           if (itemstack1.count + itemstack.count > itemstack1.getMaxStackSize()) {
/* 182 */             return false;
/*     */           }
/*     */           
/* 185 */           if (CraftEventFactory.callItemMergeEvent(entityitem, this).isCancelled()) return false;
/* 186 */           itemstack.count += itemstack1.count;
/* 187 */           this.pickupDelay = Math.max(entityitem.pickupDelay, this.pickupDelay);
/* 188 */           this.age = Math.min(entityitem.age, this.age);
/* 189 */           setItemStack(itemstack);
/* 190 */           entityitem.die();
/*     */           
/* 192 */           return true;
/*     */         }
/*     */         
/* 195 */         return false;
/*     */       }
/*     */       
/* 198 */       return false;
/*     */     }
/*     */     
/* 201 */     return false;
/*     */   }
/*     */   
/*     */   public void j()
/*     */   {
/* 206 */     this.age = 4800;
/*     */   }
/*     */   
/*     */   public boolean W() {
/* 210 */     if (this.world.a(getBoundingBox(), Material.WATER, this)) {
/* 211 */       if ((!this.inWater) && (!this.justCreated)) {
/* 212 */         X();
/*     */       }
/*     */       
/* 215 */       this.inWater = true;
/*     */     } else {
/* 217 */       this.inWater = false;
/*     */     }
/*     */     
/* 220 */     return this.inWater;
/*     */   }
/*     */   
/*     */   protected void burn(int i) {
/* 224 */     damageEntity(DamageSource.FIRE, i);
/*     */   }
/*     */   
/*     */   public boolean damageEntity(DamageSource damagesource, float f) {
/* 228 */     if (isInvulnerable(damagesource))
/* 229 */       return false;
/* 230 */     if ((getItemStack() != null) && (getItemStack().getItem() == Items.NETHER_STAR) && (damagesource.isExplosion())) {
/* 231 */       return false;
/*     */     }
/*     */     
/* 234 */     if (CraftEventFactory.handleNonLivingEntityDamageEvent(this, damagesource, f)) {
/* 235 */       return false;
/*     */     }
/*     */     
/* 238 */     ac();
/* 239 */     this.e = ((int)(this.e - f));
/* 240 */     if (this.e <= 0) {
/* 241 */       die();
/*     */     }
/*     */     
/* 244 */     return false;
/*     */   }
/*     */   
/*     */   public void b(NBTTagCompound nbttagcompound)
/*     */   {
/* 249 */     nbttagcompound.setShort("Health", (short)(byte)this.e);
/* 250 */     nbttagcompound.setShort("Age", (short)this.age);
/* 251 */     nbttagcompound.setShort("PickupDelay", (short)this.pickupDelay);
/* 252 */     if (n() != null) {
/* 253 */       nbttagcompound.setString("Thrower", this.f);
/*     */     }
/*     */     
/* 256 */     if (m() != null) {
/* 257 */       nbttagcompound.setString("Owner", this.g);
/*     */     }
/*     */     
/* 260 */     if (getItemStack() != null) {
/* 261 */       nbttagcompound.set("Item", getItemStack().save(new NBTTagCompound()));
/*     */     }
/*     */   }
/*     */   
/*     */   public void a(NBTTagCompound nbttagcompound)
/*     */   {
/* 267 */     this.e = (nbttagcompound.getShort("Health") & 0xFF);
/* 268 */     this.age = nbttagcompound.getShort("Age");
/* 269 */     if (nbttagcompound.hasKey("PickupDelay")) {
/* 270 */       this.pickupDelay = nbttagcompound.getShort("PickupDelay");
/*     */     }
/*     */     
/* 273 */     if (nbttagcompound.hasKey("Owner")) {
/* 274 */       this.g = nbttagcompound.getString("Owner");
/*     */     }
/*     */     
/* 277 */     if (nbttagcompound.hasKey("Thrower")) {
/* 278 */       this.f = nbttagcompound.getString("Thrower");
/*     */     }
/*     */     
/* 281 */     NBTTagCompound nbttagcompound1 = nbttagcompound.getCompound("Item");
/*     */     
/*     */ 
/* 284 */     if (nbttagcompound1 != null) {
/* 285 */       ItemStack itemstack = ItemStack.createStack(nbttagcompound1);
/* 286 */       if (itemstack != null) {
/* 287 */         setItemStack(itemstack);
/*     */       } else {
/* 289 */         die();
/*     */       }
/*     */     } else {
/* 292 */       die();
/*     */     }
/*     */     
/* 295 */     if (getItemStack() == null) {
/* 296 */       die();
/*     */     }
/*     */   }
/*     */   
/*     */   public void d(EntityHuman entityhuman)
/*     */   {
/* 302 */     if (!this.world.isClientSide) {
/* 303 */       ItemStack itemstack = getItemStack();
/* 304 */       int i = itemstack.count;
/*     */       
/*     */ 
/* 307 */       int canHold = entityhuman.inventory.canHold(itemstack);
/* 308 */       int remaining = itemstack.count - canHold;
/*     */       
/* 310 */       if ((this.pickupDelay <= 0) && (canHold > 0)) {
/* 311 */         itemstack.count = canHold;
/* 312 */         PlayerPickupItemEvent event = new PlayerPickupItemEvent((org.bukkit.entity.Player)entityhuman.getBukkitEntity(), (org.bukkit.entity.Item)getBukkitEntity(), remaining);
/*     */         
/* 314 */         this.world.getServer().getPluginManager().callEvent(event);
/* 315 */         itemstack.count = (canHold + remaining);
/*     */         
/* 317 */         if (event.isCancelled()) {
/* 318 */           return;
/*     */         }
/*     */         
/*     */ 
/* 322 */         this.pickupDelay = 0;
/*     */       }
/*     */       
/*     */ 
/* 326 */       if ((this.pickupDelay == 0) && ((this.g == null) || (6000 - this.age <= 200) || (this.g.equals(entityhuman.getName()))) && (entityhuman.inventory.pickup(itemstack))) {
/* 327 */         if (itemstack.getItem() == Item.getItemOf(Blocks.LOG)) {
/* 328 */           entityhuman.b(AchievementList.g);
/*     */         }
/*     */         
/* 331 */         if (itemstack.getItem() == Item.getItemOf(Blocks.LOG2)) {
/* 332 */           entityhuman.b(AchievementList.g);
/*     */         }
/*     */         
/* 335 */         if (itemstack.getItem() == Items.LEATHER) {
/* 336 */           entityhuman.b(AchievementList.t);
/*     */         }
/*     */         
/* 339 */         if (itemstack.getItem() == Items.DIAMOND) {
/* 340 */           entityhuman.b(AchievementList.w);
/*     */         }
/*     */         
/* 343 */         if (itemstack.getItem() == Items.BLAZE_ROD) {
/* 344 */           entityhuman.b(AchievementList.A);
/*     */         }
/*     */         
/* 347 */         if ((itemstack.getItem() == Items.DIAMOND) && (n() != null)) {
/* 348 */           EntityHuman entityhuman1 = this.world.a(n());
/*     */           
/* 350 */           if ((entityhuman1 != null) && (entityhuman1 != entityhuman)) {
/* 351 */             entityhuman1.b(AchievementList.x);
/*     */           }
/*     */         }
/*     */         
/* 355 */         if (!R()) {
/* 356 */           this.world.makeSound(entityhuman, "random.pop", 0.2F, ((this.random.nextFloat() - this.random.nextFloat()) * 0.7F + 1.0F) * 2.0F);
/*     */         }
/*     */         
/* 359 */         entityhuman.receive(this, i);
/* 360 */         if (itemstack.count <= 0) {
/* 361 */           die();
/*     */         }
/*     */       }
/*     */     }
/*     */   }
/*     */   
/*     */   public String getName()
/*     */   {
/* 369 */     return hasCustomName() ? getCustomName() : LocaleI18n.get("item." + getItemStack().a());
/*     */   }
/*     */   
/*     */   public boolean aD() {
/* 373 */     return false;
/*     */   }
/*     */   
/*     */   public void c(int i) {
/* 377 */     super.c(i);
/* 378 */     if (!this.world.isClientSide) {
/* 379 */       w();
/*     */     }
/*     */   }
/*     */   
/*     */   public ItemStack getItemStack()
/*     */   {
/* 385 */     ItemStack itemstack = getDataWatcher().getItemStack(10);
/*     */     
/* 387 */     if (itemstack == null) {
/* 388 */       if (this.world != null) {
/* 389 */         b.error("Item entity " + getId() + " has no item?!");
/*     */       }
/*     */       
/* 392 */       return new ItemStack(Blocks.STONE);
/*     */     }
/* 394 */     return itemstack;
/*     */   }
/*     */   
/*     */   public void setItemStack(ItemStack itemstack)
/*     */   {
/* 399 */     getDataWatcher().watch(10, itemstack);
/* 400 */     getDataWatcher().update(10);
/*     */   }
/*     */   
/*     */   public String m() {
/* 404 */     return this.g;
/*     */   }
/*     */   
/*     */   public void b(String s) {
/* 408 */     this.g = s;
/*     */   }
/*     */   
/*     */   public String n() {
/* 412 */     return this.f;
/*     */   }
/*     */   
/*     */   public void c(String s) {
/* 416 */     this.f = s;
/*     */   }
/*     */   
/*     */   public void p() {
/* 420 */     this.pickupDelay = 10;
/*     */   }
/*     */   
/*     */   public void q() {
/* 424 */     this.pickupDelay = 0;
/*     */   }
/*     */   
/*     */   public void r() {
/* 428 */     this.pickupDelay = 32767;
/*     */   }
/*     */   
/*     */   public void a(int i) {
/* 432 */     this.pickupDelay = i;
/*     */   }
/*     */   
/*     */   public boolean s() {
/* 436 */     return this.pickupDelay > 0;
/*     */   }
/*     */   
/*     */   public void u() {
/* 440 */     this.age = 59536;
/*     */   }
/*     */   
/*     */   public void v() {
/* 444 */     r();
/* 445 */     this.age = 5999;
/*     */   }
/*     */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_8_R3\EntityItem.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */
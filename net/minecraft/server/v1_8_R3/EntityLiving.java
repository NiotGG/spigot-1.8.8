/*      */ package net.minecraft.server.v1_8_R3;
/*      */ 
/*      */ import com.google.common.base.Function;
/*      */ import com.google.common.base.Predicates;
/*      */ import java.util.ArrayList;
/*      */ import java.util.Collection;
/*      */ import java.util.Iterator;
/*      */ import java.util.List;
/*      */ import java.util.Map;
/*      */ import java.util.Random;
/*      */ import java.util.Set;
/*      */ import java.util.UUID;
/*      */ import org.bukkit.craftbukkit.v1_8_R3.SpigotTimings;
/*      */ import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
/*      */ import org.bukkit.craftbukkit.v1_8_R3.event.CraftEventFactory;
/*      */ import org.bukkit.entity.LivingEntity;
/*      */ import org.bukkit.entity.Vehicle;
/*      */ import org.bukkit.event.entity.EntityDamageEvent;
/*      */ import org.bukkit.event.entity.EntityDamageEvent.DamageModifier;
/*      */ import org.bukkit.event.entity.EntityRegainHealthEvent;
/*      */ import org.bukkit.event.entity.EntityRegainHealthEvent.RegainReason;
/*      */ import org.bukkit.event.vehicle.VehicleExitEvent;
/*      */ import org.bukkit.plugin.PluginManager;
/*      */ import org.spigotmc.CustomTimingsHandler;
/*      */ import org.spigotmc.SpigotWorldConfig;
/*      */ import org.spigotmc.event.entity.EntityDismountEvent;
/*      */ 
/*      */ public abstract class EntityLiving extends Entity
/*      */ {
/*   30 */   private static final UUID a = UUID.fromString("662A6B8D-DA3E-4C1C-8813-96EA6097278D");
/*   31 */   private static final AttributeModifier b = new AttributeModifier(a, "Sprinting speed boost", 0.30000001192092896D, 2).a(false);
/*      */   private AttributeMapBase c;
/*   33 */   public CombatTracker combatTracker = new CombatTracker(this);
/*   34 */   public final Map<Integer, MobEffect> effects = com.google.common.collect.Maps.newHashMap();
/*   35 */   private final ItemStack[] h = new ItemStack[5];
/*      */   public boolean ar;
/*      */   public int as;
/*      */   public int at;
/*      */   public int hurtTicks;
/*      */   public int av;
/*      */   public float aw;
/*      */   public int deathTicks;
/*      */   public float ay;
/*      */   public float az;
/*      */   public float aA;
/*      */   public float aB;
/*      */   public float aC;
/*   48 */   public int maxNoDamageTicks = 20;
/*      */   public float aE;
/*      */   public float aF;
/*      */   public float aG;
/*      */   public float aH;
/*      */   public float aI;
/*      */   public float aJ;
/*      */   public float aK;
/*      */   public float aL;
/*   57 */   public float aM = 0.02F;
/*      */   public EntityHuman killer;
/*      */   protected int lastDamageByPlayerTime;
/*      */   protected boolean aP;
/*      */   protected int ticksFarFromPlayer;
/*      */   protected float aR;
/*      */   protected float aS;
/*      */   protected float aT;
/*      */   protected float aU;
/*      */   protected float aV;
/*      */   protected int aW;
/*      */   public float lastDamage;
/*      */   protected boolean aY;
/*      */   public float aZ;
/*      */   public float ba;
/*      */   protected float bb;
/*      */   protected int bc;
/*      */   protected double bd;
/*      */   protected double be;
/*      */   protected double bf;
/*      */   protected double bg;
/*      */   protected double bh;
/*   79 */   public boolean updateEffects = true;
/*      */   
/*      */   public EntityLiving lastDamager;
/*      */   public int hurtTimestamp;
/*      */   private EntityLiving bk;
/*      */   private int bl;
/*      */   private float bm;
/*      */   private int bn;
/*      */   private float bo;
/*      */   public int expToDrop;
/*   89 */   public int maxAirTicks = 300;
/*   90 */   ArrayList<org.bukkit.inventory.ItemStack> drops = null;
/*      */   
/*      */ 
/*      */   public void inactiveTick()
/*      */   {
/*   95 */     super.inactiveTick();
/*   96 */     this.ticksFarFromPlayer += 1;
/*      */   }
/*      */   
/*      */   public void G()
/*      */   {
/*  101 */     damageEntity(DamageSource.OUT_OF_WORLD, Float.MAX_VALUE);
/*      */   }
/*      */   
/*      */   public EntityLiving(World world) {
/*  105 */     super(world);
/*  106 */     initAttributes();
/*      */     
/*  108 */     this.datawatcher.watch(6, Float.valueOf((float)getAttributeInstance(GenericAttributes.maxHealth).getValue()));
/*  109 */     this.k = true;
/*  110 */     this.aH = ((float)((Math.random() + 1.0D) * 0.009999999776482582D));
/*  111 */     setPosition(this.locX, this.locY, this.locZ);
/*  112 */     this.aG = ((float)Math.random() * 12398.0F);
/*  113 */     this.yaw = ((float)(Math.random() * 3.1415927410125732D * 2.0D));
/*  114 */     this.aK = this.yaw;
/*  115 */     this.S = 0.6F;
/*      */   }
/*      */   
/*      */   protected void h() {
/*  119 */     this.datawatcher.a(7, Integer.valueOf(0));
/*  120 */     this.datawatcher.a(8, Byte.valueOf((byte)0));
/*  121 */     this.datawatcher.a(9, Byte.valueOf((byte)0));
/*  122 */     this.datawatcher.a(6, Float.valueOf(1.0F));
/*      */   }
/*      */   
/*      */   protected void initAttributes() {
/*  126 */     getAttributeMap().b(GenericAttributes.maxHealth);
/*  127 */     getAttributeMap().b(GenericAttributes.c);
/*  128 */     getAttributeMap().b(GenericAttributes.MOVEMENT_SPEED);
/*      */   }
/*      */   
/*      */   protected void a(double d0, boolean flag, Block block, BlockPosition blockposition) {
/*  132 */     if (!V()) {
/*  133 */       W();
/*      */     }
/*      */     
/*  136 */     if ((!this.world.isClientSide) && (this.fallDistance > 3.0F) && (flag)) {
/*  137 */       IBlockData iblockdata = this.world.getType(blockposition);
/*  138 */       Block block1 = iblockdata.getBlock();
/*  139 */       float f = MathHelper.f(this.fallDistance - 3.0F);
/*      */       
/*  141 */       if (block1.getMaterial() != Material.AIR) {
/*  142 */         double d1 = Math.min(0.2F + f / 15.0F, 10.0F);
/*      */         
/*  144 */         if (d1 > 2.5D) {
/*  145 */           d1 = 2.5D;
/*      */         }
/*      */         
/*  148 */         int i = (int)(150.0D * d1);
/*      */         
/*      */ 
/*  151 */         if ((this instanceof EntityPlayer)) {
/*  152 */           ((WorldServer)this.world).sendParticles((EntityPlayer)this, EnumParticle.BLOCK_DUST, false, this.locX, this.locY, this.locZ, i, 0.0D, 0.0D, 0.0D, 0.15000000596046448D, new int[] { Block.getCombinedId(iblockdata) });
/*      */         } else {
/*  154 */           ((WorldServer)this.world).a(EnumParticle.BLOCK_DUST, this.locX, this.locY, this.locZ, i, 0.0D, 0.0D, 0.0D, 0.15000000596046448D, new int[] { Block.getCombinedId(iblockdata) });
/*      */         }
/*      */       }
/*      */     }
/*      */     
/*      */ 
/*  160 */     super.a(d0, flag, block, blockposition);
/*      */   }
/*      */   
/*      */   public boolean aY() {
/*  164 */     return false;
/*      */   }
/*      */   
/*      */   public void K() {
/*  168 */     this.ay = this.az;
/*  169 */     super.K();
/*  170 */     this.world.methodProfiler.a("livingEntityBaseTick");
/*  171 */     boolean flag = this instanceof EntityHuman;
/*      */     
/*  173 */     if (isAlive()) {
/*  174 */       if (inBlock()) {
/*  175 */         damageEntity(DamageSource.STUCK, 1.0F);
/*  176 */       } else if ((flag) && (!this.world.getWorldBorder().a(getBoundingBox()))) {
/*  177 */         double d0 = this.world.getWorldBorder().a(this) + this.world.getWorldBorder().getDamageBuffer();
/*      */         
/*  179 */         if (d0 < 0.0D) {
/*  180 */           damageEntity(DamageSource.STUCK, Math.max(1, MathHelper.floor(-d0 * this.world.getWorldBorder().getDamageAmount())));
/*      */         }
/*      */       }
/*      */     }
/*      */     
/*  185 */     if ((isFireProof()) || (this.world.isClientSide)) {
/*  186 */       extinguish();
/*      */     }
/*      */     
/*  189 */     boolean flag1 = (flag) && (((EntityHuman)this).abilities.isInvulnerable);
/*      */     
/*  191 */     if (isAlive()) {
/*  192 */       if (a(Material.WATER)) {
/*  193 */         if ((!aY()) && (!hasEffect(MobEffectList.WATER_BREATHING.id)) && (!flag1)) {
/*  194 */           setAirTicks(j(getAirTicks()));
/*  195 */           if (getAirTicks() == -20) {
/*  196 */             setAirTicks(0);
/*      */             
/*  198 */             for (int i = 0; i < 8; i++) {
/*  199 */               float f = this.random.nextFloat() - this.random.nextFloat();
/*  200 */               float f1 = this.random.nextFloat() - this.random.nextFloat();
/*  201 */               float f2 = this.random.nextFloat() - this.random.nextFloat();
/*      */               
/*  203 */               this.world.addParticle(EnumParticle.WATER_BUBBLE, this.locX + f, this.locY + f1, this.locZ + f2, this.motX, this.motY, this.motZ, new int[0]);
/*      */             }
/*      */             
/*  206 */             damageEntity(DamageSource.DROWN, 2.0F);
/*      */           }
/*      */         }
/*      */         
/*  210 */         if ((!this.world.isClientSide) && (au()) && ((this.vehicle instanceof EntityLiving))) {
/*  211 */           mount(null);
/*      */         }
/*      */         
/*      */       }
/*  215 */       else if (getAirTicks() != 300) {
/*  216 */         setAirTicks(this.maxAirTicks);
/*      */       }
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*  222 */     if ((isAlive()) && (U())) {
/*  223 */       extinguish();
/*      */     }
/*      */     
/*  226 */     this.aE = this.aF;
/*  227 */     if (this.hurtTicks > 0) {
/*  228 */       this.hurtTicks -= 1;
/*      */     }
/*      */     
/*  231 */     if ((this.noDamageTicks > 0) && (!(this instanceof EntityPlayer))) {
/*  232 */       this.noDamageTicks -= 1;
/*      */     }
/*      */     
/*  235 */     if (getHealth() <= 0.0F) {
/*  236 */       aZ();
/*      */     }
/*      */     
/*  239 */     if (this.lastDamageByPlayerTime > 0) {
/*  240 */       this.lastDamageByPlayerTime -= 1;
/*      */     } else {
/*  242 */       this.killer = null;
/*      */     }
/*      */     
/*  245 */     if ((this.bk != null) && (!this.bk.isAlive())) {
/*  246 */       this.bk = null;
/*      */     }
/*      */     
/*  249 */     if (this.lastDamager != null) {
/*  250 */       if (!this.lastDamager.isAlive()) {
/*  251 */         b(null);
/*  252 */       } else if (this.ticksLived - this.hurtTimestamp > 100) {
/*  253 */         b(null);
/*      */       }
/*      */     }
/*      */     
/*  257 */     bi();
/*  258 */     this.aU = this.aT;
/*  259 */     this.aJ = this.aI;
/*  260 */     this.aL = this.aK;
/*  261 */     this.lastYaw = this.yaw;
/*  262 */     this.lastPitch = this.pitch;
/*  263 */     this.world.methodProfiler.b();
/*      */   }
/*      */   
/*      */   public int getExpReward()
/*      */   {
/*  268 */     int exp = getExpValue(this.killer);
/*      */     
/*  270 */     if ((!this.world.isClientSide) && ((this.lastDamageByPlayerTime > 0) || (alwaysGivesExp())) && (ba()) && (this.world.getGameRules().getBoolean("doMobLoot"))) {
/*  271 */       return exp;
/*      */     }
/*  273 */     return 0;
/*      */   }
/*      */   
/*      */ 
/*      */   public boolean isBaby()
/*      */   {
/*  279 */     return false;
/*      */   }
/*      */   
/*      */   protected void aZ() {
/*  283 */     this.deathTicks += 1;
/*  284 */     if ((this.deathTicks >= 20) && (!this.dead))
/*      */     {
/*      */ 
/*      */ 
/*  288 */       int i = this.expToDrop;
/*  289 */       while (i > 0) {
/*  290 */         int j = EntityExperienceOrb.getOrbValue(i);
/*  291 */         i -= j;
/*  292 */         this.world.addEntity(new EntityExperienceOrb(this.world, this.locX, this.locY, this.locZ, j));
/*      */       }
/*  294 */       this.expToDrop = 0;
/*      */       
/*      */ 
/*  297 */       die();
/*      */       
/*  299 */       for (i = 0; i < 20; i++) {
/*  300 */         double d0 = this.random.nextGaussian() * 0.02D;
/*  301 */         double d1 = this.random.nextGaussian() * 0.02D;
/*  302 */         double d2 = this.random.nextGaussian() * 0.02D;
/*      */         
/*  304 */         this.world.addParticle(EnumParticle.EXPLOSION_NORMAL, this.locX + this.random.nextFloat() * this.width * 2.0F - this.width, this.locY + this.random.nextFloat() * this.length, this.locZ + this.random.nextFloat() * this.width * 2.0F - this.width, d0, d1, d2, new int[0]);
/*      */       }
/*      */     }
/*      */   }
/*      */   
/*      */   protected boolean ba()
/*      */   {
/*  311 */     return !isBaby();
/*      */   }
/*      */   
/*      */   protected int j(int i) {
/*  315 */     int j = EnchantmentManager.getOxygenEnchantmentLevel(this);
/*      */     
/*  317 */     return (j > 0) && (this.random.nextInt(j + 1) > 0) ? i : i - 1;
/*      */   }
/*      */   
/*      */   protected int getExpValue(EntityHuman entityhuman) {
/*  321 */     return 0;
/*      */   }
/*      */   
/*      */   protected boolean alwaysGivesExp() {
/*  325 */     return false;
/*      */   }
/*      */   
/*      */   public Random bc() {
/*  329 */     return this.random;
/*      */   }
/*      */   
/*      */   public EntityLiving getLastDamager() {
/*  333 */     return this.lastDamager;
/*      */   }
/*      */   
/*      */   public int be() {
/*  337 */     return this.hurtTimestamp;
/*      */   }
/*      */   
/*      */   public void b(EntityLiving entityliving) {
/*  341 */     this.lastDamager = entityliving;
/*  342 */     this.hurtTimestamp = this.ticksLived;
/*      */   }
/*      */   
/*      */   public EntityLiving bf() {
/*  346 */     return this.bk;
/*      */   }
/*      */   
/*      */   public int bg() {
/*  350 */     return this.bl;
/*      */   }
/*      */   
/*      */   public void p(Entity entity) {
/*  354 */     if ((entity instanceof EntityLiving)) {
/*  355 */       this.bk = ((EntityLiving)entity);
/*      */     } else {
/*  357 */       this.bk = null;
/*      */     }
/*      */     
/*  360 */     this.bl = this.ticksLived;
/*      */   }
/*      */   
/*      */   public int bh() {
/*  364 */     return this.ticksFarFromPlayer;
/*      */   }
/*      */   
/*      */   public void b(NBTTagCompound nbttagcompound) {
/*  368 */     nbttagcompound.setFloat("HealF", getHealth());
/*  369 */     nbttagcompound.setShort("Health", (short)(int)Math.ceil(getHealth()));
/*  370 */     nbttagcompound.setShort("HurtTime", (short)this.hurtTicks);
/*  371 */     nbttagcompound.setInt("HurtByTimestamp", this.hurtTimestamp);
/*  372 */     nbttagcompound.setShort("DeathTime", (short)this.deathTicks);
/*  373 */     nbttagcompound.setFloat("AbsorptionAmount", getAbsorptionHearts());
/*  374 */     ItemStack[] aitemstack = getEquipment();
/*  375 */     int i = aitemstack.length;
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*  380 */     for (int j = 0; j < i; j++) {
/*  381 */       ItemStack itemstack = aitemstack[j];
/*  382 */       if (itemstack != null) {
/*  383 */         this.c.a(itemstack.B());
/*      */       }
/*      */     }
/*      */     
/*  387 */     nbttagcompound.set("Attributes", GenericAttributes.a(getAttributeMap()));
/*  388 */     aitemstack = getEquipment();
/*  389 */     i = aitemstack.length;
/*      */     
/*  391 */     for (j = 0; j < i; j++) {
/*  392 */       ItemStack itemstack = aitemstack[j];
/*  393 */       if (itemstack != null) {
/*  394 */         this.c.b(itemstack.B());
/*      */       }
/*      */     }
/*      */     
/*  398 */     if (!this.effects.isEmpty()) {
/*  399 */       NBTTagList nbttaglist = new NBTTagList();
/*  400 */       Iterator iterator = this.effects.values().iterator();
/*      */       
/*  402 */       while (iterator.hasNext()) {
/*  403 */         MobEffect mobeffect = (MobEffect)iterator.next();
/*      */         
/*  405 */         nbttaglist.add(mobeffect.a(new NBTTagCompound()));
/*      */       }
/*      */       
/*  408 */       nbttagcompound.set("ActiveEffects", nbttaglist);
/*      */     }
/*      */   }
/*      */   
/*      */   public void a(NBTTagCompound nbttagcompound)
/*      */   {
/*  414 */     setAbsorptionHearts(nbttagcompound.getFloat("AbsorptionAmount"));
/*  415 */     if ((nbttagcompound.hasKeyOfType("Attributes", 9)) && (this.world != null) && (!this.world.isClientSide)) {
/*  416 */       GenericAttributes.a(getAttributeMap(), nbttagcompound.getList("Attributes", 10));
/*      */     }
/*      */     
/*  419 */     if (nbttagcompound.hasKeyOfType("ActiveEffects", 9)) {
/*  420 */       NBTTagList nbttaglist = nbttagcompound.getList("ActiveEffects", 10);
/*      */       
/*  422 */       for (int i = 0; i < nbttaglist.size(); i++) {
/*  423 */         NBTTagCompound nbttagcompound1 = nbttaglist.get(i);
/*  424 */         MobEffect mobeffect = MobEffect.b(nbttagcompound1);
/*      */         
/*  426 */         if (mobeffect != null) {
/*  427 */           this.effects.put(Integer.valueOf(mobeffect.getEffectId()), mobeffect);
/*      */         }
/*      */       }
/*      */     }
/*      */     
/*      */ 
/*  433 */     if (nbttagcompound.hasKey("Bukkit.MaxHealth")) {
/*  434 */       NBTBase nbtbase = nbttagcompound.get("Bukkit.MaxHealth");
/*  435 */       if (nbtbase.getTypeId() == 5) {
/*  436 */         getAttributeInstance(GenericAttributes.maxHealth).setValue(((NBTTagFloat)nbtbase).c());
/*  437 */       } else if (nbtbase.getTypeId() == 3) {
/*  438 */         getAttributeInstance(GenericAttributes.maxHealth).setValue(((NBTTagInt)nbtbase).d());
/*      */       }
/*      */     }
/*      */     
/*      */ 
/*  443 */     if (nbttagcompound.hasKeyOfType("HealF", 99)) {
/*  444 */       setHealth(nbttagcompound.getFloat("HealF"));
/*      */     } else {
/*  446 */       NBTBase nbtbase = nbttagcompound.get("Health");
/*      */       
/*  448 */       if (nbtbase == null) {
/*  449 */         setHealth(getMaxHealth());
/*  450 */       } else if (nbtbase.getTypeId() == 5) {
/*  451 */         setHealth(((NBTTagFloat)nbtbase).h());
/*  452 */       } else if (nbtbase.getTypeId() == 2) {
/*  453 */         setHealth(((NBTTagShort)nbtbase).e());
/*      */       }
/*      */     }
/*      */     
/*  457 */     this.hurtTicks = nbttagcompound.getShort("HurtTime");
/*  458 */     this.deathTicks = nbttagcompound.getShort("DeathTime");
/*  459 */     this.hurtTimestamp = nbttagcompound.getInt("HurtByTimestamp");
/*      */   }
/*      */   
/*      */ 
/*  463 */   private boolean isTickingEffects = false;
/*  464 */   private List<Object> effectsToProcess = com.google.common.collect.Lists.newArrayList();
/*      */   
/*      */   protected void bi()
/*      */   {
/*  468 */     Iterator iterator = this.effects.keySet().iterator();
/*      */     
/*  470 */     this.isTickingEffects = true;
/*  471 */     MobEffect mobeffect; while (iterator.hasNext()) {
/*  472 */       Integer integer = (Integer)iterator.next();
/*  473 */       mobeffect = (MobEffect)this.effects.get(integer);
/*      */       
/*  475 */       if (!mobeffect.tick(this)) {
/*  476 */         if (!this.world.isClientSide) {
/*  477 */           iterator.remove();
/*  478 */           b(mobeffect);
/*      */         }
/*  480 */       } else if (mobeffect.getDuration() % 600 == 0) {
/*  481 */         a(mobeffect, false);
/*      */       }
/*      */     }
/*      */     
/*  485 */     this.isTickingEffects = false;
/*  486 */     for (Object e : this.effectsToProcess) {
/*  487 */       if ((e instanceof MobEffect)) {
/*  488 */         addEffect((MobEffect)e);
/*      */       } else {
/*  490 */         removeEffect(((Integer)e).intValue());
/*      */       }
/*      */     }
/*      */     
/*      */ 
/*  495 */     if (this.updateEffects) {
/*  496 */       if (!this.world.isClientSide) {
/*  497 */         B();
/*      */       }
/*      */       
/*  500 */       this.updateEffects = false;
/*      */     }
/*      */     
/*  503 */     int i = this.datawatcher.getInt(7);
/*  504 */     boolean flag = this.datawatcher.getByte(8) > 0;
/*      */     
/*  506 */     if (i > 0) {
/*  507 */       boolean flag1 = false;
/*      */       
/*  509 */       if (!isInvisible()) {
/*  510 */         flag1 = this.random.nextBoolean();
/*      */       } else {
/*  512 */         flag1 = this.random.nextInt(15) == 0;
/*      */       }
/*      */       
/*  515 */       if (flag) {
/*  516 */         flag1 &= this.random.nextInt(5) == 0;
/*      */       }
/*      */       
/*  519 */       if ((flag1) && (i > 0)) {
/*  520 */         double d0 = (i >> 16 & 0xFF) / 255.0D;
/*  521 */         double d1 = (i >> 8 & 0xFF) / 255.0D;
/*  522 */         double d2 = (i >> 0 & 0xFF) / 255.0D;
/*      */         
/*  524 */         this.world.addParticle(flag ? EnumParticle.SPELL_MOB_AMBIENT : EnumParticle.SPELL_MOB, this.locX + (this.random.nextDouble() - 0.5D) * this.width, this.locY + this.random.nextDouble() * this.length, this.locZ + (this.random.nextDouble() - 0.5D) * this.width, d0, d1, d2, new int[0]);
/*      */       }
/*      */     }
/*      */   }
/*      */   
/*      */   protected void B()
/*      */   {
/*  531 */     if (this.effects.isEmpty()) {
/*  532 */       bj();
/*  533 */       setInvisible(false);
/*      */     } else {
/*  535 */       int i = PotionBrewer.a(this.effects.values());
/*      */       
/*  537 */       this.datawatcher.watch(8, Byte.valueOf((byte)(PotionBrewer.b(this.effects.values()) ? 1 : 0)));
/*  538 */       this.datawatcher.watch(7, Integer.valueOf(i));
/*  539 */       setInvisible(hasEffect(MobEffectList.INVISIBILITY.id));
/*      */     }
/*      */   }
/*      */   
/*      */   protected void bj()
/*      */   {
/*  545 */     this.datawatcher.watch(8, Byte.valueOf((byte)0));
/*  546 */     this.datawatcher.watch(7, Integer.valueOf(0));
/*      */   }
/*      */   
/*      */   public void removeAllEffects() {
/*  550 */     Iterator iterator = this.effects.keySet().iterator();
/*      */     
/*  552 */     while (iterator.hasNext()) {
/*  553 */       Integer integer = (Integer)iterator.next();
/*  554 */       MobEffect mobeffect = (MobEffect)this.effects.get(integer);
/*      */       
/*  556 */       if (!this.world.isClientSide) {
/*  557 */         iterator.remove();
/*  558 */         b(mobeffect);
/*      */       }
/*      */     }
/*      */   }
/*      */   
/*      */   public Collection<MobEffect> getEffects()
/*      */   {
/*  565 */     return this.effects.values();
/*      */   }
/*      */   
/*      */   public boolean hasEffect(int i)
/*      */   {
/*  570 */     return (this.effects.size() != 0) && (this.effects.containsKey(Integer.valueOf(i)));
/*      */   }
/*      */   
/*      */   public boolean hasEffect(MobEffectList mobeffectlist) {
/*  574 */     return this.effects.containsKey(Integer.valueOf(mobeffectlist.id));
/*      */   }
/*      */   
/*      */   public MobEffect getEffect(MobEffectList mobeffectlist) {
/*  578 */     return (MobEffect)this.effects.get(Integer.valueOf(mobeffectlist.id));
/*      */   }
/*      */   
/*      */   public void addEffect(MobEffect mobeffect) {
/*  582 */     org.spigotmc.AsyncCatcher.catchOp("effect add");
/*      */     
/*  584 */     if (this.isTickingEffects) {
/*  585 */       this.effectsToProcess.add(mobeffect);
/*  586 */       return;
/*      */     }
/*      */     
/*  589 */     if (d(mobeffect)) {
/*  590 */       if (this.effects.containsKey(Integer.valueOf(mobeffect.getEffectId()))) {
/*  591 */         ((MobEffect)this.effects.get(Integer.valueOf(mobeffect.getEffectId()))).a(mobeffect);
/*  592 */         a((MobEffect)this.effects.get(Integer.valueOf(mobeffect.getEffectId())), true);
/*      */       } else {
/*  594 */         this.effects.put(Integer.valueOf(mobeffect.getEffectId()), mobeffect);
/*  595 */         a(mobeffect);
/*      */       }
/*      */     }
/*      */   }
/*      */   
/*      */   public boolean d(MobEffect mobeffect)
/*      */   {
/*  602 */     if (getMonsterType() == EnumMonsterType.UNDEAD) {
/*  603 */       int i = mobeffect.getEffectId();
/*      */       
/*  605 */       if ((i == MobEffectList.REGENERATION.id) || (i == MobEffectList.POISON.id)) {
/*  606 */         return false;
/*      */       }
/*      */     }
/*      */     
/*  610 */     return true;
/*      */   }
/*      */   
/*      */   public boolean bm() {
/*  614 */     return getMonsterType() == EnumMonsterType.UNDEAD;
/*      */   }
/*      */   
/*      */   public void removeEffect(int i)
/*      */   {
/*  619 */     if (this.isTickingEffects) {
/*  620 */       this.effectsToProcess.add(Integer.valueOf(i));
/*  621 */       return;
/*      */     }
/*      */     
/*  624 */     MobEffect mobeffect = (MobEffect)this.effects.remove(Integer.valueOf(i));
/*      */     
/*  626 */     if (mobeffect != null) {
/*  627 */       b(mobeffect);
/*      */     }
/*      */   }
/*      */   
/*      */   protected void a(MobEffect mobeffect)
/*      */   {
/*  633 */     this.updateEffects = true;
/*  634 */     if (!this.world.isClientSide) {
/*  635 */       MobEffectList.byId[mobeffect.getEffectId()].b(this, getAttributeMap(), mobeffect.getAmplifier());
/*      */     }
/*      */   }
/*      */   
/*      */   protected void a(MobEffect mobeffect, boolean flag)
/*      */   {
/*  641 */     this.updateEffects = true;
/*  642 */     if ((flag) && (!this.world.isClientSide)) {
/*  643 */       MobEffectList.byId[mobeffect.getEffectId()].a(this, getAttributeMap(), mobeffect.getAmplifier());
/*  644 */       MobEffectList.byId[mobeffect.getEffectId()].b(this, getAttributeMap(), mobeffect.getAmplifier());
/*      */     }
/*      */   }
/*      */   
/*      */   protected void b(MobEffect mobeffect)
/*      */   {
/*  650 */     this.updateEffects = true;
/*  651 */     if (!this.world.isClientSide) {
/*  652 */       MobEffectList.byId[mobeffect.getEffectId()].a(this, getAttributeMap(), mobeffect.getAmplifier());
/*      */     }
/*      */   }
/*      */   
/*      */ 
/*      */   public void heal(float f)
/*      */   {
/*  659 */     heal(f, EntityRegainHealthEvent.RegainReason.CUSTOM);
/*      */   }
/*      */   
/*      */   public void heal(float f, EntityRegainHealthEvent.RegainReason regainReason) {
/*  663 */     float f1 = getHealth();
/*      */     
/*  665 */     if (f1 > 0.0F) {
/*  666 */       EntityRegainHealthEvent event = new EntityRegainHealthEvent(getBukkitEntity(), f, regainReason);
/*  667 */       this.world.getServer().getPluginManager().callEvent(event);
/*      */       
/*  669 */       if (!event.isCancelled()) {
/*  670 */         setHealth((float)(getHealth() + event.getAmount()));
/*      */       }
/*      */     }
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */   public final float getHealth()
/*      */   {
/*  679 */     if ((this instanceof EntityPlayer)) {
/*  680 */       return (float)((EntityPlayer)this).getBukkitEntity().getHealth();
/*      */     }
/*      */     
/*  683 */     return this.datawatcher.getFloat(6);
/*      */   }
/*      */   
/*      */   public void setHealth(float f)
/*      */   {
/*  688 */     if ((this instanceof EntityPlayer)) {
/*  689 */       CraftPlayer player = ((EntityPlayer)this).getBukkitEntity();
/*      */       
/*  691 */       if (f < 0.0F) {
/*  692 */         player.setRealHealth(0.0D);
/*  693 */       } else if (f > player.getMaxHealth()) {
/*  694 */         player.setRealHealth(player.getMaxHealth());
/*      */       } else {
/*  696 */         player.setRealHealth(f);
/*      */       }
/*      */       
/*  699 */       this.datawatcher.watch(6, Float.valueOf(player.getScaledHealth()));
/*  700 */       return;
/*      */     }
/*      */     
/*  703 */     this.datawatcher.watch(6, Float.valueOf(MathHelper.a(f, 0.0F, getMaxHealth())));
/*      */   }
/*      */   
/*      */   public boolean damageEntity(DamageSource damagesource, float f) {
/*  707 */     if (isInvulnerable(damagesource))
/*  708 */       return false;
/*  709 */     if (this.world.isClientSide) {
/*  710 */       return false;
/*      */     }
/*  712 */     this.ticksFarFromPlayer = 0;
/*  713 */     if (getHealth() <= 0.0F)
/*  714 */       return false;
/*  715 */     if ((damagesource.o()) && (hasEffect(MobEffectList.FIRE_RESISTANCE))) {
/*  716 */       return false;
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*  724 */     this.aB = 1.5F;
/*  725 */     boolean flag = true;
/*      */     
/*  727 */     if (this.noDamageTicks > this.maxNoDamageTicks / 2.0F) {
/*  728 */       if (f <= this.lastDamage) {
/*  729 */         this.forceExplosionKnockback = true;
/*  730 */         return false;
/*      */       }
/*      */       
/*      */ 
/*  734 */       if (!d(damagesource, f - this.lastDamage)) {
/*  735 */         return false;
/*      */       }
/*      */       
/*  738 */       this.lastDamage = f;
/*  739 */       flag = false;
/*      */     }
/*      */     else {
/*  742 */       getHealth();
/*  743 */       if (!d(damagesource, f)) {
/*  744 */         return false;
/*      */       }
/*  746 */       this.lastDamage = f;
/*  747 */       this.noDamageTicks = this.maxNoDamageTicks;
/*      */       
/*  749 */       this.hurtTicks = (this.av = 10);
/*      */     }
/*      */     
/*      */ 
/*  753 */     if ((this instanceof EntityAnimal)) {
/*  754 */       ((EntityAnimal)this).cq();
/*  755 */       if ((this instanceof EntityTameableAnimal)) {
/*  756 */         ((EntityTameableAnimal)this).getGoalSit().setSitting(false);
/*      */       }
/*      */     }
/*      */     
/*      */ 
/*  761 */     this.aw = 0.0F;
/*  762 */     Entity entity = damagesource.getEntity();
/*      */     
/*  764 */     if (entity != null) {
/*  765 */       if ((entity instanceof EntityLiving)) {
/*  766 */         b((EntityLiving)entity);
/*      */       }
/*      */       
/*  769 */       if ((entity instanceof EntityHuman)) {
/*  770 */         this.lastDamageByPlayerTime = 100;
/*  771 */         this.killer = ((EntityHuman)entity);
/*  772 */       } else if ((entity instanceof EntityWolf)) {
/*  773 */         EntityWolf entitywolf = (EntityWolf)entity;
/*      */         
/*  775 */         if (entitywolf.isTamed()) {
/*  776 */           this.lastDamageByPlayerTime = 100;
/*  777 */           this.killer = null;
/*      */         }
/*      */       }
/*      */     }
/*      */     
/*  782 */     if (flag) {
/*  783 */       this.world.broadcastEntityEffect(this, (byte)2);
/*  784 */       if (damagesource != DamageSource.DROWN) {
/*  785 */         ac();
/*      */       }
/*      */       
/*  788 */       if (entity != null) {
/*  789 */         double d0 = entity.locX - this.locX;
/*      */         
/*      */ 
/*      */ 
/*  793 */         for (double d1 = entity.locZ - this.locZ; d0 * d0 + d1 * d1 < 1.0E-4D; d1 = (Math.random() - Math.random()) * 0.01D) {
/*  794 */           d0 = (Math.random() - Math.random()) * 0.01D;
/*      */         }
/*      */         
/*  797 */         this.aw = ((float)(MathHelper.b(d1, d0) * 180.0D / 3.1415927410125732D - this.yaw));
/*  798 */         a(entity, f, d0, d1);
/*      */       } else {
/*  800 */         this.aw = ((int)(Math.random() * 2.0D) * 180);
/*      */       }
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*  806 */     if (getHealth() <= 0.0F) {
/*  807 */       String s = bp();
/*  808 */       if ((flag) && (s != null)) {
/*  809 */         makeSound(s, bB(), bC());
/*      */       }
/*      */       
/*  812 */       die(damagesource);
/*      */     } else {
/*  814 */       String s = bo();
/*  815 */       if ((flag) && (s != null)) {
/*  816 */         makeSound(s, bB(), bC());
/*      */       }
/*      */     }
/*      */     
/*  820 */     return true;
/*      */   }
/*      */   
/*      */ 
/*      */   public void b(ItemStack itemstack)
/*      */   {
/*  826 */     makeSound("random.break", 0.8F, 0.8F + this.world.random.nextFloat() * 0.4F);
/*      */     
/*  828 */     for (int i = 0; i < 5; i++) {
/*  829 */       Vec3D vec3d = new Vec3D((this.random.nextFloat() - 0.5D) * 0.1D, Math.random() * 0.1D + 0.1D, 0.0D);
/*      */       
/*  831 */       vec3d = vec3d.a(-this.pitch * 3.1415927F / 180.0F);
/*  832 */       vec3d = vec3d.b(-this.yaw * 3.1415927F / 180.0F);
/*  833 */       double d0 = -this.random.nextFloat() * 0.6D - 0.3D;
/*  834 */       Vec3D vec3d1 = new Vec3D((this.random.nextFloat() - 0.5D) * 0.3D, d0, 0.6D);
/*      */       
/*  836 */       vec3d1 = vec3d1.a(-this.pitch * 3.1415927F / 180.0F);
/*  837 */       vec3d1 = vec3d1.b(-this.yaw * 3.1415927F / 180.0F);
/*  838 */       vec3d1 = vec3d1.add(this.locX, this.locY + getHeadHeight(), this.locZ);
/*  839 */       this.world.addParticle(EnumParticle.ITEM_CRACK, vec3d1.a, vec3d1.b, vec3d1.c, vec3d.a, vec3d.b + 0.05D, vec3d.c, new int[] { Item.getId(itemstack.getItem()) });
/*      */     }
/*      */   }
/*      */   
/*      */   public void die(DamageSource damagesource)
/*      */   {
/*  845 */     Entity entity = damagesource.getEntity();
/*  846 */     EntityLiving entityliving = bt();
/*      */     
/*  848 */     if ((this.aW >= 0) && (entityliving != null)) {
/*  849 */       entityliving.b(this, this.aW);
/*      */     }
/*      */     
/*  852 */     if (entity != null) {
/*  853 */       entity.a(this);
/*      */     }
/*      */     
/*  856 */     this.aP = true;
/*  857 */     bs().g();
/*  858 */     if (!this.world.isClientSide) {
/*  859 */       int i = 0;
/*      */       
/*  861 */       if ((entity instanceof EntityHuman)) {
/*  862 */         i = EnchantmentManager.getBonusMonsterLootEnchantmentLevel((EntityLiving)entity);
/*      */       }
/*      */       
/*  865 */       if ((ba()) && (this.world.getGameRules().getBoolean("doMobLoot"))) {
/*  866 */         this.drops = new ArrayList();
/*      */         
/*  868 */         dropDeathLoot(this.lastDamageByPlayerTime > 0, i);
/*  869 */         dropEquipment(this.lastDamageByPlayerTime > 0, i);
/*  870 */         if ((this.lastDamageByPlayerTime > 0) && (this.random.nextFloat() < 0.025F + i * 0.01F)) {
/*  871 */           getRareDrop();
/*      */         }
/*      */         
/*  874 */         CraftEventFactory.callEntityDeathEvent(this, this.drops);
/*  875 */         this.drops = null;
/*      */       } else {
/*  877 */         CraftEventFactory.callEntityDeathEvent(this);
/*      */       }
/*      */     }
/*      */     
/*      */ 
/*  882 */     this.world.broadcastEntityEffect(this, (byte)3);
/*      */   }
/*      */   
/*      */   protected void dropEquipment(boolean flag, int i) {}
/*      */   
/*      */   public void a(Entity entity, float f, double d0, double d1) {
/*  888 */     if (this.random.nextDouble() >= getAttributeInstance(GenericAttributes.c).getValue()) {
/*  889 */       this.ai = true;
/*  890 */       float f1 = MathHelper.sqrt(d0 * d0 + d1 * d1);
/*  891 */       float f2 = 0.4F;
/*      */       
/*  893 */       this.motX /= 2.0D;
/*  894 */       this.motY /= 2.0D;
/*  895 */       this.motZ /= 2.0D;
/*  896 */       this.motX -= d0 / f1 * f2;
/*  897 */       this.motY += f2;
/*  898 */       this.motZ -= d1 / f1 * f2;
/*  899 */       if (this.motY > 0.4000000059604645D) {
/*  900 */         this.motY = 0.4000000059604645D;
/*      */       }
/*      */     }
/*      */   }
/*      */   
/*      */   protected String bo()
/*      */   {
/*  907 */     return "game.neutral.hurt";
/*      */   }
/*      */   
/*      */   protected String bp() {
/*  911 */     return "game.neutral.die";
/*      */   }
/*      */   
/*      */   protected void getRareDrop() {}
/*      */   
/*      */   protected void dropDeathLoot(boolean flag, int i) {}
/*      */   
/*      */   public boolean k_() {
/*  919 */     int i = MathHelper.floor(this.locX);
/*  920 */     int j = MathHelper.floor(getBoundingBox().b);
/*  921 */     int k = MathHelper.floor(this.locZ);
/*  922 */     Block block = this.world.getType(new BlockPosition(i, j, k)).getBlock();
/*      */     
/*  924 */     return ((block == Blocks.LADDER) || (block == Blocks.VINE)) && ((!(this instanceof EntityHuman)) || (!((EntityHuman)this).isSpectator()));
/*      */   }
/*      */   
/*      */   public boolean isAlive() {
/*  928 */     return (!this.dead) && (getHealth() > 0.0F);
/*      */   }
/*      */   
/*      */   public void e(float f, float f1) {
/*  932 */     super.e(f, f1);
/*  933 */     MobEffect mobeffect = getEffect(MobEffectList.JUMP);
/*  934 */     float f2 = mobeffect != null ? mobeffect.getAmplifier() + 1 : 0.0F;
/*  935 */     int i = MathHelper.f((f - 3.0F - f2) * f1);
/*      */     
/*  937 */     if (i > 0)
/*      */     {
/*  939 */       if (!damageEntity(DamageSource.FALL, i)) {
/*  940 */         return;
/*      */       }
/*      */       
/*  943 */       makeSound(n(i), 1.0F, 1.0F);
/*      */       
/*  945 */       int j = MathHelper.floor(this.locX);
/*  946 */       int k = MathHelper.floor(this.locY - 0.20000000298023224D);
/*  947 */       int l = MathHelper.floor(this.locZ);
/*  948 */       Block block = this.world.getType(new BlockPosition(j, k, l)).getBlock();
/*      */       
/*  950 */       if (block.getMaterial() != Material.AIR) {
/*  951 */         Block.StepSound block_stepsound = block.stepSound;
/*      */         
/*  953 */         makeSound(block_stepsound.getStepSound(), block_stepsound.getVolume1() * 0.5F, block_stepsound.getVolume2() * 0.75F);
/*      */       }
/*      */     }
/*      */   }
/*      */   
/*      */   protected String n(int i)
/*      */   {
/*  960 */     return i > 4 ? "game.neutral.hurt.fall.big" : "game.neutral.hurt.fall.small";
/*      */   }
/*      */   
/*      */   public int br() {
/*  964 */     int i = 0;
/*  965 */     ItemStack[] aitemstack = getEquipment();
/*  966 */     int j = aitemstack.length;
/*      */     
/*  968 */     for (int k = 0; k < j; k++) {
/*  969 */       ItemStack itemstack = aitemstack[k];
/*      */       
/*  971 */       if ((itemstack != null) && ((itemstack.getItem() instanceof ItemArmor))) {
/*  972 */         int l = ((ItemArmor)itemstack.getItem()).c;
/*      */         
/*  974 */         i += l;
/*      */       }
/*      */     }
/*      */     
/*  978 */     return i;
/*      */   }
/*      */   
/*      */   protected void damageArmor(float f) {}
/*      */   
/*      */   protected float applyArmorModifier(DamageSource damagesource, float f) {
/*  984 */     if (!damagesource.ignoresArmor()) {
/*  985 */       int i = 25 - br();
/*  986 */       float f1 = f * i;
/*      */       
/*      */ 
/*  989 */       f = f1 / 25.0F;
/*      */     }
/*      */     
/*  992 */     return f;
/*      */   }
/*      */   
/*      */   protected float applyMagicModifier(DamageSource damagesource, float f) {
/*  996 */     if (damagesource.isStarvation()) {
/*  997 */       return f;
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 1011 */     if (f <= 0.0F) {
/* 1012 */       return 0.0F;
/*      */     }
/* 1014 */     int i = EnchantmentManager.a(getEquipment(), damagesource);
/* 1015 */     if (i > 20) {
/* 1016 */       i = 20;
/*      */     }
/*      */     
/* 1019 */     if ((i > 0) && (i <= 20)) {
/* 1020 */       int j = 25 - i;
/* 1021 */       float f1 = f * j;
/* 1022 */       f = f1 / 25.0F;
/*      */     }
/*      */     
/* 1025 */     return f;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */   protected boolean d(final DamageSource damagesource, float f)
/*      */   {
/* 1032 */     if (!isInvulnerable(damagesource)) {
/* 1033 */       final boolean human = this instanceof EntityHuman;
/* 1034 */       float originalDamage = f;
/* 1035 */       Function<Double, Double> hardHat = new Function()
/*      */       {
/*      */         public Double apply(Double f) {
/* 1038 */           if (((damagesource == DamageSource.ANVIL) || (damagesource == DamageSource.FALLING_BLOCK)) && (EntityLiving.this.getEquipment(4) != null)) {
/* 1039 */             return Double.valueOf(-(f.doubleValue() - f.doubleValue() * 0.75D));
/*      */           }
/* 1041 */           return Double.valueOf(-0.0D);
/*      */         }
/* 1043 */       };
/* 1044 */       float hardHatModifier = ((Double)hardHat.apply(Double.valueOf(f))).floatValue();
/* 1045 */       f += hardHatModifier;
/*      */       
/* 1047 */       Function<Double, Double> blocking = new Function()
/*      */       {
/*      */         public Double apply(Double f) {
/* 1050 */           if ((human) && 
/* 1051 */             (!damagesource.ignoresArmor()) && (((EntityHuman)EntityLiving.this).isBlocking()) && (f.doubleValue() > 0.0D)) {
/* 1052 */             return Double.valueOf(-(f.doubleValue() - (1.0D + f.doubleValue()) * 0.5D));
/*      */           }
/*      */           
/* 1055 */           return Double.valueOf(-0.0D);
/*      */         }
/* 1057 */       };
/* 1058 */       float blockingModifier = ((Double)blocking.apply(Double.valueOf(f))).floatValue();
/* 1059 */       f += blockingModifier;
/*      */       
/* 1061 */       Function<Double, Double> armor = new Function()
/*      */       {
/*      */         public Double apply(Double f) {
/* 1064 */           return Double.valueOf(-(f.doubleValue() - EntityLiving.this.applyArmorModifier(damagesource, f.floatValue())));
/*      */         }
/* 1066 */       };
/* 1067 */       float armorModifier = ((Double)armor.apply(Double.valueOf(f))).floatValue();
/* 1068 */       f += armorModifier;
/*      */       
/* 1070 */       Function<Double, Double> resistance = new Function()
/*      */       {
/*      */         public Double apply(Double f) {
/* 1073 */           if ((!damagesource.isStarvation()) && (EntityLiving.this.hasEffect(MobEffectList.RESISTANCE)) && (damagesource != DamageSource.OUT_OF_WORLD)) {
/* 1074 */             int i = (EntityLiving.this.getEffect(MobEffectList.RESISTANCE).getAmplifier() + 1) * 5;
/* 1075 */             int j = 25 - i;
/* 1076 */             float f1 = f.floatValue() * j;
/* 1077 */             return Double.valueOf(-(f.doubleValue() - f1 / 25.0F));
/*      */           }
/* 1079 */           return Double.valueOf(-0.0D);
/*      */         }
/* 1081 */       };
/* 1082 */       float resistanceModifier = ((Double)resistance.apply(Double.valueOf(f))).floatValue();
/* 1083 */       f += resistanceModifier;
/*      */       
/* 1085 */       Function<Double, Double> magic = new Function()
/*      */       {
/*      */         public Double apply(Double f) {
/* 1088 */           return Double.valueOf(-(f.doubleValue() - EntityLiving.this.applyMagicModifier(damagesource, f.floatValue())));
/*      */         }
/* 1090 */       };
/* 1091 */       float magicModifier = ((Double)magic.apply(Double.valueOf(f))).floatValue();
/* 1092 */       f += magicModifier;
/*      */       
/* 1094 */       Function<Double, Double> absorption = new Function()
/*      */       {
/*      */         public Double apply(Double f) {
/* 1097 */           return Double.valueOf(-Math.max(f.doubleValue() - Math.max(f.doubleValue() - EntityLiving.this.getAbsorptionHearts(), 0.0D), 0.0D));
/*      */         }
/* 1099 */       };
/* 1100 */       float absorptionModifier = ((Double)absorption.apply(Double.valueOf(f))).floatValue();
/*      */       
/* 1102 */       EntityDamageEvent event = CraftEventFactory.handleLivingEntityDamageEvent(this, damagesource, originalDamage, hardHatModifier, blockingModifier, armorModifier, resistanceModifier, magicModifier, absorptionModifier, hardHat, blocking, armor, resistance, magic, absorption);
/* 1103 */       if (event.isCancelled()) {
/* 1104 */         return false;
/*      */       }
/*      */       
/* 1107 */       f = (float)event.getFinalDamage();
/*      */       
/*      */ 
/* 1110 */       if (((damagesource == DamageSource.ANVIL) || (damagesource == DamageSource.FALLING_BLOCK)) && (getEquipment(4) != null)) {
/* 1111 */         getEquipment(4).damage((int)(event.getDamage() * 4.0D + this.random.nextFloat() * event.getDamage() * 2.0D), this);
/*      */       }
/*      */       
/*      */ 
/* 1115 */       if (!damagesource.ignoresArmor()) {
/* 1116 */         float armorDamage = (float)(event.getDamage() + event.getDamage(EntityDamageEvent.DamageModifier.BLOCKING) + event.getDamage(EntityDamageEvent.DamageModifier.HARD_HAT));
/* 1117 */         damageArmor(armorDamage);
/*      */       }
/*      */       
/* 1120 */       absorptionModifier = (float)-event.getDamage(EntityDamageEvent.DamageModifier.ABSORPTION);
/* 1121 */       setAbsorptionHearts(Math.max(getAbsorptionHearts() - absorptionModifier, 0.0F));
/* 1122 */       if (f != 0.0F) {
/* 1123 */         if (human)
/*      */         {
/* 1125 */           ((EntityHuman)this).applyExhaustion(damagesource.getExhaustionCost());
/* 1126 */           if (f < 3.4028235E37F) {
/* 1127 */             ((EntityHuman)this).a(StatisticList.x, Math.round(f * 10.0F));
/*      */           }
/*      */         }
/*      */         
/* 1131 */         float f2 = getHealth();
/*      */         
/* 1133 */         setHealth(f2 - f);
/* 1134 */         bs().a(damagesource, f2, f);
/*      */         
/* 1136 */         if (human) {
/* 1137 */           return true;
/*      */         }
/*      */         
/* 1140 */         setAbsorptionHearts(getAbsorptionHearts() - f);
/*      */       }
/* 1142 */       return true;
/*      */     }
/* 1144 */     return false;
/*      */   }
/*      */   
/*      */   public CombatTracker bs() {
/* 1148 */     return this.combatTracker;
/*      */   }
/*      */   
/*      */   public EntityLiving bt() {
/* 1152 */     return this.lastDamager != null ? this.lastDamager : this.killer != null ? this.killer : this.combatTracker.c() != null ? this.combatTracker.c() : null;
/*      */   }
/*      */   
/*      */   public final float getMaxHealth() {
/* 1156 */     return (float)getAttributeInstance(GenericAttributes.maxHealth).getValue();
/*      */   }
/*      */   
/*      */   public final int bv() {
/* 1160 */     return this.datawatcher.getByte(9);
/*      */   }
/*      */   
/*      */   public final void o(int i) {
/* 1164 */     this.datawatcher.watch(9, Byte.valueOf((byte)i));
/*      */   }
/*      */   
/*      */   private int n() {
/* 1168 */     return hasEffect(MobEffectList.SLOWER_DIG) ? 6 + (1 + getEffect(MobEffectList.SLOWER_DIG).getAmplifier()) * 2 : hasEffect(MobEffectList.FASTER_DIG) ? 6 - (1 + getEffect(MobEffectList.FASTER_DIG).getAmplifier()) * 1 : 6;
/*      */   }
/*      */   
/*      */   public void bw() {
/* 1172 */     if ((!this.ar) || (this.as >= n() / 2) || (this.as < 0)) {
/* 1173 */       this.as = -1;
/* 1174 */       this.ar = true;
/* 1175 */       if ((this.world instanceof WorldServer)) {
/* 1176 */         ((WorldServer)this.world).getTracker().a(this, new PacketPlayOutAnimation(this, 0));
/*      */       }
/*      */     }
/*      */   }
/*      */   
/*      */   protected void O()
/*      */   {
/* 1183 */     damageEntity(DamageSource.OUT_OF_WORLD, 4.0F);
/*      */   }
/*      */   
/*      */   protected void bx() {
/* 1187 */     int i = n();
/*      */     
/* 1189 */     if (this.ar) {
/* 1190 */       this.as += 1;
/* 1191 */       if (this.as >= i) {
/* 1192 */         this.as = 0;
/* 1193 */         this.ar = false;
/*      */       }
/*      */     } else {
/* 1196 */       this.as = 0;
/*      */     }
/*      */     
/* 1199 */     this.az = (this.as / i);
/*      */   }
/*      */   
/*      */   public AttributeInstance getAttributeInstance(IAttribute iattribute) {
/* 1203 */     return getAttributeMap().a(iattribute);
/*      */   }
/*      */   
/*      */   public AttributeMapBase getAttributeMap() {
/* 1207 */     if (this.c == null) {
/* 1208 */       this.c = new AttributeMapServer();
/*      */     }
/*      */     
/* 1211 */     return this.c;
/*      */   }
/*      */   
/*      */   public EnumMonsterType getMonsterType() {
/* 1215 */     return EnumMonsterType.UNDEFINED;
/*      */   }
/*      */   
/*      */   public abstract ItemStack bA();
/*      */   
/*      */   public abstract ItemStack getEquipment(int paramInt);
/*      */   
/*      */   public abstract void setEquipment(int paramInt, ItemStack paramItemStack);
/*      */   
/*      */   public void setSprinting(boolean flag) {
/* 1225 */     super.setSprinting(flag);
/* 1226 */     AttributeInstance attributeinstance = getAttributeInstance(GenericAttributes.MOVEMENT_SPEED);
/*      */     
/* 1228 */     if (attributeinstance.a(a) != null) {
/* 1229 */       attributeinstance.c(b);
/*      */     }
/*      */     
/* 1232 */     if (flag) {
/* 1233 */       attributeinstance.b(b);
/*      */     }
/*      */   }
/*      */   
/*      */   public abstract ItemStack[] getEquipment();
/*      */   
/*      */   protected float bB()
/*      */   {
/* 1241 */     return 1.0F;
/*      */   }
/*      */   
/*      */   protected float bC() {
/* 1245 */     return isBaby() ? (this.random.nextFloat() - this.random.nextFloat()) * 0.2F + 1.5F : (this.random.nextFloat() - this.random.nextFloat()) * 0.2F + 1.0F;
/*      */   }
/*      */   
/*      */   protected boolean bD() {
/* 1249 */     return getHealth() <= 0.0F;
/*      */   }
/*      */   
/*      */   public void q(Entity entity) {
/* 1253 */     double d0 = entity.locX;
/* 1254 */     double d1 = entity.getBoundingBox().b + entity.length;
/* 1255 */     double d2 = entity.locZ;
/* 1256 */     byte b0 = 1;
/*      */     
/* 1258 */     for (int i = -b0; i <= b0; i++) {
/* 1259 */       for (int j = -b0; j < b0; j++) {
/* 1260 */         if ((i != 0) || (j != 0)) {
/* 1261 */           int k = (int)(this.locX + i);
/* 1262 */           int l = (int)(this.locZ + j);
/* 1263 */           AxisAlignedBB axisalignedbb = getBoundingBox().c(i, 1.0D, j);
/*      */           
/* 1265 */           if (this.world.a(axisalignedbb).isEmpty()) {
/* 1266 */             if (World.a(this.world, new BlockPosition(k, (int)this.locY, l))) {
/* 1267 */               enderTeleportTo(this.locX + i, this.locY + 1.0D, this.locZ + j);
/* 1268 */               return;
/*      */             }
/*      */             
/* 1271 */             if ((World.a(this.world, new BlockPosition(k, (int)this.locY - 1, l))) || (this.world.getType(new BlockPosition(k, (int)this.locY - 1, l)).getBlock().getMaterial() == Material.WATER)) {
/* 1272 */               d0 = this.locX + i;
/* 1273 */               d1 = this.locY + 1.0D;
/* 1274 */               d2 = this.locZ + j;
/*      */             }
/*      */           }
/*      */         }
/*      */       }
/*      */     }
/*      */     
/* 1281 */     enderTeleportTo(d0, d1, d2);
/*      */   }
/*      */   
/*      */   protected float bE() {
/* 1285 */     return 0.42F;
/*      */   }
/*      */   
/*      */   protected void bF() {
/* 1289 */     this.motY = bE();
/* 1290 */     if (hasEffect(MobEffectList.JUMP)) {
/* 1291 */       this.motY += (getEffect(MobEffectList.JUMP).getAmplifier() + 1) * 0.1F;
/*      */     }
/*      */     
/* 1294 */     if (isSprinting()) {
/* 1295 */       float f = this.yaw * 0.017453292F;
/*      */       
/* 1297 */       this.motX -= MathHelper.sin(f) * 0.2F;
/* 1298 */       this.motZ += MathHelper.cos(f) * 0.2F;
/*      */     }
/*      */     
/* 1301 */     this.ai = true;
/*      */   }
/*      */   
/*      */   protected void bG() {
/* 1305 */     this.motY += 0.03999999910593033D;
/*      */   }
/*      */   
/*      */   protected void bH() {
/* 1309 */     this.motY += 0.03999999910593033D;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */   public void g(float f, float f1)
/*      */   {
/* 1316 */     if (bM())
/*      */     {
/*      */ 
/*      */ 
/* 1320 */       if ((V()) && ((!(this instanceof EntityHuman)) || (!((EntityHuman)this).abilities.isFlying))) {
/* 1321 */         double d0 = this.locY;
/* 1322 */         float f3 = 0.8F;
/* 1323 */         float f4 = 0.02F;
/* 1324 */         float f2 = EnchantmentManager.b(this);
/* 1325 */         if (f2 > 3.0F) {
/* 1326 */           f2 = 3.0F;
/*      */         }
/*      */         
/* 1329 */         if (!this.onGround) {
/* 1330 */           f2 *= 0.5F;
/*      */         }
/*      */         
/* 1333 */         if (f2 > 0.0F) {
/* 1334 */           f3 += (0.54600006F - f3) * f2 / 3.0F;
/* 1335 */           f4 += (bI() * 1.0F - f4) * f2 / 3.0F;
/*      */         }
/*      */         
/* 1338 */         a(f, f1, f4);
/* 1339 */         move(this.motX, this.motY, this.motZ);
/* 1340 */         this.motX *= f3;
/* 1341 */         this.motY *= 0.800000011920929D;
/* 1342 */         this.motZ *= f3;
/* 1343 */         this.motY -= 0.02D;
/* 1344 */         if ((this.positionChanged) && (c(this.motX, this.motY + 0.6000000238418579D - this.locY + d0, this.motZ))) {
/* 1345 */           this.motY = 0.30000001192092896D;
/*      */         }
/* 1347 */       } else if ((ab()) && ((!(this instanceof EntityHuman)) || (!((EntityHuman)this).abilities.isFlying))) {
/* 1348 */         double d0 = this.locY;
/* 1349 */         a(f, f1, 0.02F);
/* 1350 */         move(this.motX, this.motY, this.motZ);
/* 1351 */         this.motX *= 0.5D;
/* 1352 */         this.motY *= 0.5D;
/* 1353 */         this.motZ *= 0.5D;
/* 1354 */         this.motY -= 0.02D;
/* 1355 */         if ((this.positionChanged) && (c(this.motX, this.motY + 0.6000000238418579D - this.locY + d0, this.motZ))) {
/* 1356 */           this.motY = 0.30000001192092896D;
/*      */         }
/*      */       } else {
/* 1359 */         float f5 = 0.91F;
/*      */         
/* 1361 */         if (this.onGround) {
/* 1362 */           f5 = this.world.getType(new BlockPosition(MathHelper.floor(this.locX), MathHelper.floor(getBoundingBox().b) - 1, MathHelper.floor(this.locZ))).getBlock().frictionFactor * 0.91F;
/*      */         }
/*      */         
/* 1365 */         float f6 = 0.16277136F / (f5 * f5 * f5);
/*      */         float f3;
/* 1367 */         float f3; if (this.onGround) {
/* 1368 */           f3 = bI() * f6;
/*      */         } else {
/* 1370 */           f3 = this.aM;
/*      */         }
/*      */         
/* 1373 */         a(f, f1, f3);
/* 1374 */         f5 = 0.91F;
/* 1375 */         if (this.onGround) {
/* 1376 */           f5 = this.world.getType(new BlockPosition(MathHelper.floor(this.locX), MathHelper.floor(getBoundingBox().b) - 1, MathHelper.floor(this.locZ))).getBlock().frictionFactor * 0.91F;
/*      */         }
/*      */         
/* 1379 */         if (k_()) {
/* 1380 */           float f4 = 0.15F;
/* 1381 */           this.motX = MathHelper.a(this.motX, -f4, f4);
/* 1382 */           this.motZ = MathHelper.a(this.motZ, -f4, f4);
/* 1383 */           this.fallDistance = 0.0F;
/* 1384 */           if (this.motY < -0.15D) {
/* 1385 */             this.motY = -0.15D;
/*      */           }
/*      */           
/* 1388 */           boolean flag = (isSneaking()) && ((this instanceof EntityHuman));
/*      */           
/* 1390 */           if ((flag) && (this.motY < 0.0D)) {
/* 1391 */             this.motY = 0.0D;
/*      */           }
/*      */         }
/*      */         
/* 1395 */         move(this.motX, this.motY, this.motZ);
/* 1396 */         if ((this.positionChanged) && (k_())) {
/* 1397 */           this.motY = 0.2D;
/*      */         }
/*      */         
/* 1400 */         if ((this.world.isClientSide) && ((!this.world.isLoaded(new BlockPosition((int)this.locX, 0, (int)this.locZ))) || (!this.world.getChunkAtWorldCoords(new BlockPosition((int)this.locX, 0, (int)this.locZ)).o()))) {
/* 1401 */           if (this.locY > 0.0D) {
/* 1402 */             this.motY = -0.1D;
/*      */           } else {
/* 1404 */             this.motY = 0.0D;
/*      */           }
/*      */         } else {
/* 1407 */           this.motY -= 0.08D;
/*      */         }
/*      */         
/* 1410 */         this.motY *= 0.9800000190734863D;
/* 1411 */         this.motX *= f5;
/* 1412 */         this.motZ *= f5;
/*      */       }
/*      */     }
/*      */     
/* 1416 */     this.aA = this.aB;
/* 1417 */     double d0 = this.locX - this.lastX;
/* 1418 */     double d1 = this.locZ - this.lastZ;
/*      */     
/* 1420 */     float f2 = MathHelper.sqrt(d0 * d0 + d1 * d1) * 4.0F;
/* 1421 */     if (f2 > 1.0F) {
/* 1422 */       f2 = 1.0F;
/*      */     }
/*      */     
/* 1425 */     this.aB += (f2 - this.aB) * 0.4F;
/* 1426 */     this.aC += this.aB;
/*      */   }
/*      */   
/*      */   public float bI() {
/* 1430 */     return this.bm;
/*      */   }
/*      */   
/*      */   public void k(float f) {
/* 1434 */     this.bm = f;
/*      */   }
/*      */   
/*      */   public boolean r(Entity entity) {
/* 1438 */     p(entity);
/* 1439 */     return false;
/*      */   }
/*      */   
/*      */   public boolean isSleeping() {
/* 1443 */     return false;
/*      */   }
/*      */   
/*      */   public void t_() {
/* 1447 */     SpigotTimings.timerEntityBaseTick.startTiming();
/* 1448 */     super.t_();
/* 1449 */     if (!this.world.isClientSide) {
/* 1450 */       int i = bv();
/*      */       
/* 1452 */       if (i > 0) {
/* 1453 */         if (this.at <= 0) {
/* 1454 */           this.at = (20 * (30 - i));
/*      */         }
/*      */         
/* 1457 */         this.at -= 1;
/* 1458 */         if (this.at <= 0) {
/* 1459 */           o(i - 1);
/*      */         }
/*      */       }
/*      */       
/* 1463 */       for (int j = 0; j < 5; j++) {
/* 1464 */         ItemStack itemstack = this.h[j];
/* 1465 */         ItemStack itemstack1 = getEquipment(j);
/*      */         
/* 1467 */         if (!ItemStack.matches(itemstack1, itemstack)) {
/* 1468 */           ((WorldServer)this.world).getTracker().a(this, new PacketPlayOutEntityEquipment(getId(), j, itemstack1));
/* 1469 */           if (itemstack != null) {
/* 1470 */             this.c.a(itemstack.B());
/*      */           }
/*      */           
/* 1473 */           if (itemstack1 != null) {
/* 1474 */             this.c.b(itemstack1.B());
/*      */           }
/*      */           
/* 1477 */           this.h[j] = (itemstack1 == null ? null : itemstack1.cloneItemStack());
/*      */         }
/*      */       }
/*      */       
/* 1481 */       if (this.ticksLived % 20 == 0) {
/* 1482 */         bs().g();
/*      */       }
/*      */     }
/*      */     
/* 1486 */     SpigotTimings.timerEntityBaseTick.stopTiming();
/* 1487 */     m();
/* 1488 */     SpigotTimings.timerEntityTickRest.startTiming();
/* 1489 */     double d0 = this.locX - this.lastX;
/* 1490 */     double d1 = this.locZ - this.lastZ;
/* 1491 */     float f = (float)(d0 * d0 + d1 * d1);
/* 1492 */     float f1 = this.aI;
/* 1493 */     float f2 = 0.0F;
/*      */     
/* 1495 */     this.aR = this.aS;
/* 1496 */     float f3 = 0.0F;
/*      */     
/* 1498 */     if (f > 0.0025000002F) {
/* 1499 */       f3 = 1.0F;
/* 1500 */       f2 = (float)Math.sqrt(f) * 3.0F;
/*      */       
/* 1502 */       f1 = (float)org.bukkit.craftbukkit.v1_8_R3.TrigMath.atan2(d1, d0) * 180.0F / 3.1415927F - 90.0F;
/*      */     }
/*      */     
/* 1505 */     if (this.az > 0.0F) {
/* 1506 */       f1 = this.yaw;
/*      */     }
/*      */     
/* 1509 */     if (!this.onGround) {
/* 1510 */       f3 = 0.0F;
/*      */     }
/*      */     
/* 1513 */     this.aS += (f3 - this.aS) * 0.3F;
/* 1514 */     this.world.methodProfiler.a("headTurn");
/* 1515 */     f2 = h(f1, f2);
/* 1516 */     this.world.methodProfiler.b();
/* 1517 */     this.world.methodProfiler.a("rangeChecks");
/*      */     
/* 1519 */     while (this.yaw - this.lastYaw < -180.0F) {
/* 1520 */       this.lastYaw -= 360.0F;
/*      */     }
/*      */     
/* 1523 */     while (this.yaw - this.lastYaw >= 180.0F) {
/* 1524 */       this.lastYaw += 360.0F;
/*      */     }
/*      */     
/* 1527 */     while (this.aI - this.aJ < -180.0F) {
/* 1528 */       this.aJ -= 360.0F;
/*      */     }
/*      */     
/* 1531 */     while (this.aI - this.aJ >= 180.0F) {
/* 1532 */       this.aJ += 360.0F;
/*      */     }
/*      */     
/* 1535 */     while (this.pitch - this.lastPitch < -180.0F) {
/* 1536 */       this.lastPitch -= 360.0F;
/*      */     }
/*      */     
/* 1539 */     while (this.pitch - this.lastPitch >= 180.0F) {
/* 1540 */       this.lastPitch += 360.0F;
/*      */     }
/*      */     
/* 1543 */     while (this.aK - this.aL < -180.0F) {
/* 1544 */       this.aL -= 360.0F;
/*      */     }
/*      */     
/* 1547 */     while (this.aK - this.aL >= 180.0F) {
/* 1548 */       this.aL += 360.0F;
/*      */     }
/*      */     
/* 1551 */     this.world.methodProfiler.b();
/* 1552 */     this.aT += f2;
/* 1553 */     SpigotTimings.timerEntityTickRest.stopTiming();
/*      */   }
/*      */   
/*      */   protected float h(float f, float f1) {
/* 1557 */     float f2 = MathHelper.g(f - this.aI);
/*      */     
/* 1559 */     this.aI += f2 * 0.3F;
/* 1560 */     float f3 = MathHelper.g(this.yaw - this.aI);
/* 1561 */     boolean flag = (f3 < -90.0F) || (f3 >= 90.0F);
/*      */     
/* 1563 */     if (f3 < -75.0F) {
/* 1564 */       f3 = -75.0F;
/*      */     }
/*      */     
/* 1567 */     if (f3 >= 75.0F) {
/* 1568 */       f3 = 75.0F;
/*      */     }
/*      */     
/* 1571 */     this.aI = (this.yaw - f3);
/* 1572 */     if (f3 * f3 > 2500.0F) {
/* 1573 */       this.aI += f3 * 0.2F;
/*      */     }
/*      */     
/* 1576 */     if (flag) {
/* 1577 */       f1 *= -1.0F;
/*      */     }
/*      */     
/* 1580 */     return f1;
/*      */   }
/*      */   
/*      */   public void m() {
/* 1584 */     if (this.bn > 0) {
/* 1585 */       this.bn -= 1;
/*      */     }
/*      */     
/* 1588 */     if (this.bc > 0) {
/* 1589 */       double d0 = this.locX + (this.bd - this.locX) / this.bc;
/* 1590 */       double d1 = this.locY + (this.be - this.locY) / this.bc;
/* 1591 */       double d2 = this.locZ + (this.bf - this.locZ) / this.bc;
/* 1592 */       double d3 = MathHelper.g(this.bg - this.yaw);
/*      */       
/* 1594 */       this.yaw = ((float)(this.yaw + d3 / this.bc));
/* 1595 */       this.pitch = ((float)(this.pitch + (this.bh - this.pitch) / this.bc));
/* 1596 */       this.bc -= 1;
/* 1597 */       setPosition(d0, d1, d2);
/* 1598 */       setYawPitch(this.yaw, this.pitch);
/* 1599 */     } else if (!bM()) {
/* 1600 */       this.motX *= 0.98D;
/* 1601 */       this.motY *= 0.98D;
/* 1602 */       this.motZ *= 0.98D;
/*      */     }
/*      */     
/* 1605 */     if (Math.abs(this.motX) < 0.005D) {
/* 1606 */       this.motX = 0.0D;
/*      */     }
/*      */     
/* 1609 */     if (Math.abs(this.motY) < 0.005D) {
/* 1610 */       this.motY = 0.0D;
/*      */     }
/*      */     
/* 1613 */     if (Math.abs(this.motZ) < 0.005D) {
/* 1614 */       this.motZ = 0.0D;
/*      */     }
/*      */     
/* 1617 */     this.world.methodProfiler.a("ai");
/* 1618 */     SpigotTimings.timerEntityAI.startTiming();
/* 1619 */     if (bD()) {
/* 1620 */       this.aY = false;
/* 1621 */       this.aZ = 0.0F;
/* 1622 */       this.ba = 0.0F;
/* 1623 */       this.bb = 0.0F;
/* 1624 */     } else if (bM()) {
/* 1625 */       this.world.methodProfiler.a("newAi");
/* 1626 */       doTick();
/* 1627 */       this.world.methodProfiler.b();
/*      */     }
/* 1629 */     SpigotTimings.timerEntityAI.stopTiming();
/*      */     
/* 1631 */     this.world.methodProfiler.b();
/* 1632 */     this.world.methodProfiler.a("jump");
/* 1633 */     if (this.aY) {
/* 1634 */       if (V()) {
/* 1635 */         bG();
/* 1636 */       } else if (ab()) {
/* 1637 */         bH();
/* 1638 */       } else if ((this.onGround) && (this.bn == 0)) {
/* 1639 */         bF();
/* 1640 */         this.bn = 10;
/*      */       }
/*      */     } else {
/* 1643 */       this.bn = 0;
/*      */     }
/*      */     
/* 1646 */     this.world.methodProfiler.b();
/* 1647 */     this.world.methodProfiler.a("travel");
/* 1648 */     this.aZ *= 0.98F;
/* 1649 */     this.ba *= 0.98F;
/* 1650 */     this.bb *= 0.9F;
/* 1651 */     SpigotTimings.timerEntityAIMove.startTiming();
/* 1652 */     g(this.aZ, this.ba);
/* 1653 */     SpigotTimings.timerEntityAIMove.stopTiming();
/* 1654 */     this.world.methodProfiler.b();
/* 1655 */     this.world.methodProfiler.a("push");
/* 1656 */     if (!this.world.isClientSide) {
/* 1657 */       SpigotTimings.timerEntityAICollision.startTiming();
/* 1658 */       bL();
/* 1659 */       SpigotTimings.timerEntityAICollision.stopTiming();
/*      */     }
/*      */     
/* 1662 */     this.world.methodProfiler.b();
/*      */   }
/*      */   
/*      */   protected void doTick() {}
/*      */   
/*      */   protected void bL() {
/* 1668 */     List list = this.world.a(this, getBoundingBox().grow(0.20000000298023224D, 0.0D, 0.20000000298023224D), Predicates.and(IEntitySelector.d, new com.google.common.base.Predicate() {
/*      */       public boolean a(Entity entity) {
/* 1670 */         return entity.ae();
/*      */       }
/*      */       
/*      */       public boolean apply(Object object) {
/* 1674 */         return a((Entity)object);
/*      */       }
/*      */     }));
/*      */     
/* 1678 */     if ((ad()) && (!list.isEmpty())) {
/* 1679 */       this.numCollisions -= this.world.spigotConfig.maxCollisionsPerEntity;
/* 1680 */       for (int i = 0; i < list.size(); i++) {
/* 1681 */         if (this.numCollisions > this.world.spigotConfig.maxCollisionsPerEntity) break;
/* 1682 */         Entity entity = (Entity)list.get(i);
/*      */         
/*      */ 
/*      */ 
/* 1686 */         if ((!(entity instanceof EntityLiving)) || ((this instanceof EntityPlayer)) || (this.ticksLived % 2 != 0))
/*      */         {
/*      */ 
/*      */ 
/*      */ 
/* 1691 */           entity.numCollisions += 1;
/* 1692 */           this.numCollisions += 1;
/* 1693 */           s(entity);
/*      */         } }
/* 1695 */       this.numCollisions = 0;
/*      */     }
/*      */   }
/*      */   
/*      */   protected void s(Entity entity)
/*      */   {
/* 1701 */     entity.collide(this);
/*      */   }
/*      */   
/*      */   public void mount(Entity entity) {
/* 1705 */     if ((this.vehicle != null) && (entity == null))
/*      */     {
/* 1707 */       Entity originalVehicle = this.vehicle;
/* 1708 */       if (((this.bukkitEntity instanceof LivingEntity)) && ((this.vehicle.getBukkitEntity() instanceof Vehicle))) {
/* 1709 */         VehicleExitEvent event = new VehicleExitEvent((Vehicle)this.vehicle.getBukkitEntity(), (LivingEntity)this.bukkitEntity);
/* 1710 */         getBukkitEntity().getServer().getPluginManager().callEvent(event);
/*      */         
/* 1712 */         if ((event.isCancelled()) || (this.vehicle != originalVehicle)) {
/* 1713 */           return;
/*      */         }
/*      */       }
/*      */       
/* 1717 */       org.bukkit.Bukkit.getPluginManager().callEvent(new EntityDismountEvent(getBukkitEntity(), this.vehicle.getBukkitEntity()));
/*      */       
/* 1719 */       if (!this.world.isClientSide) {
/* 1720 */         q(this.vehicle);
/*      */       }
/*      */       
/* 1723 */       if (this.vehicle != null) {
/* 1724 */         this.vehicle.passenger = null;
/*      */       }
/*      */       
/* 1727 */       this.vehicle = null;
/*      */     } else {
/* 1729 */       super.mount(entity);
/*      */     }
/*      */   }
/*      */   
/*      */   public void ak() {
/* 1734 */     super.ak();
/* 1735 */     this.aR = this.aS;
/* 1736 */     this.aS = 0.0F;
/* 1737 */     this.fallDistance = 0.0F;
/*      */   }
/*      */   
/*      */   public void i(boolean flag) {
/* 1741 */     this.aY = flag;
/*      */   }
/*      */   
/*      */   public void receive(Entity entity, int i) {
/* 1745 */     if ((!entity.dead) && (!this.world.isClientSide)) {
/* 1746 */       EntityTracker entitytracker = ((WorldServer)this.world).getTracker();
/*      */       
/* 1748 */       if ((entity instanceof EntityItem)) {
/* 1749 */         entitytracker.a(entity, new PacketPlayOutCollect(entity.getId(), getId()));
/*      */       }
/*      */       
/* 1752 */       if ((entity instanceof EntityArrow)) {
/* 1753 */         entitytracker.a(entity, new PacketPlayOutCollect(entity.getId(), getId()));
/*      */       }
/*      */       
/* 1756 */       if ((entity instanceof EntityExperienceOrb)) {
/* 1757 */         entitytracker.a(entity, new PacketPlayOutCollect(entity.getId(), getId()));
/*      */       }
/*      */     }
/*      */   }
/*      */   
/*      */   public boolean hasLineOfSight(Entity entity)
/*      */   {
/* 1764 */     return this.world.rayTrace(new Vec3D(this.locX, this.locY + getHeadHeight(), this.locZ), new Vec3D(entity.locX, entity.locY + entity.getHeadHeight(), entity.locZ)) == null;
/*      */   }
/*      */   
/*      */   public Vec3D ap() {
/* 1768 */     return d(1.0F);
/*      */   }
/*      */   
/*      */   public Vec3D d(float f) {
/* 1772 */     if (f == 1.0F) {
/* 1773 */       return f(this.pitch, this.aK);
/*      */     }
/* 1775 */     float f1 = this.lastPitch + (this.pitch - this.lastPitch) * f;
/* 1776 */     float f2 = this.aL + (this.aK - this.aL) * f;
/*      */     
/* 1778 */     return f(f1, f2);
/*      */   }
/*      */   
/*      */   public boolean bM()
/*      */   {
/* 1783 */     return !this.world.isClientSide;
/*      */   }
/*      */   
/*      */   public boolean ad() {
/* 1787 */     return !this.dead;
/*      */   }
/*      */   
/*      */   public boolean ae() {
/* 1791 */     return !this.dead;
/*      */   }
/*      */   
/*      */   protected void ac() {
/* 1795 */     this.velocityChanged = (this.random.nextDouble() >= getAttributeInstance(GenericAttributes.c).getValue());
/*      */   }
/*      */   
/*      */   public float getHeadRotation() {
/* 1799 */     return this.aK;
/*      */   }
/*      */   
/*      */   public void f(float f) {
/* 1803 */     this.aK = f;
/*      */   }
/*      */   
/*      */   public void g(float f) {
/* 1807 */     this.aI = f;
/*      */   }
/*      */   
/*      */   public float getAbsorptionHearts() {
/* 1811 */     return this.bo;
/*      */   }
/*      */   
/*      */   public void setAbsorptionHearts(float f) {
/* 1815 */     if (f < 0.0F) {
/* 1816 */       f = 0.0F;
/*      */     }
/*      */     
/* 1819 */     this.bo = f;
/*      */   }
/*      */   
/*      */   public ScoreboardTeamBase getScoreboardTeam() {
/* 1823 */     return this.world.getScoreboard().getPlayerTeam(getUniqueID().toString());
/*      */   }
/*      */   
/*      */   public boolean c(EntityLiving entityliving) {
/* 1827 */     return a(entityliving.getScoreboardTeam());
/*      */   }
/*      */   
/*      */   public boolean a(ScoreboardTeamBase scoreboardteambase) {
/* 1831 */     return getScoreboardTeam() != null ? getScoreboardTeam().isAlly(scoreboardteambase) : false;
/*      */   }
/*      */   
/*      */   public void enterCombat() {}
/*      */   
/*      */   public void exitCombat() {}
/*      */   
/*      */   protected void bP() {
/* 1839 */     this.updateEffects = true;
/*      */   }
/*      */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_8_R3\EntityLiving.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */
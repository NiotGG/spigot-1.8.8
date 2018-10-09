/*     */ package net.minecraft.server.v1_8_R3;
/*     */ 
/*     */ import java.util.Random;
/*     */ import org.bukkit.craftbukkit.v1_8_R3.CraftServer;
/*     */ import org.bukkit.event.entity.CreeperPowerEvent;
/*     */ import org.bukkit.event.entity.ExplosionPrimeEvent;
/*     */ 
/*     */ public class EntityCreeper extends EntityMonster
/*     */ {
/*     */   private int a;
/*     */   private int fuseTicks;
/*  12 */   private int maxFuseTicks = 30;
/*  13 */   private int explosionRadius = 3;
/*  14 */   private int bn = 0;
/*  15 */   private int record = -1;
/*     */   
/*     */   public EntityCreeper(World world) {
/*  18 */     super(world);
/*  19 */     this.goalSelector.a(1, new PathfinderGoalFloat(this));
/*  20 */     this.goalSelector.a(2, new PathfinderGoalSwell(this));
/*  21 */     this.goalSelector.a(3, new PathfinderGoalAvoidTarget(this, EntityOcelot.class, 6.0F, 1.0D, 1.2D));
/*  22 */     this.goalSelector.a(4, new PathfinderGoalMeleeAttack(this, 1.0D, false));
/*  23 */     this.goalSelector.a(5, new PathfinderGoalRandomStroll(this, 0.8D));
/*  24 */     this.goalSelector.a(6, new PathfinderGoalLookAtPlayer(this, EntityHuman.class, 8.0F));
/*  25 */     this.goalSelector.a(6, new PathfinderGoalRandomLookaround(this));
/*  26 */     this.targetSelector.a(1, new PathfinderGoalNearestAttackableTarget(this, EntityHuman.class, true));
/*  27 */     this.targetSelector.a(2, new PathfinderGoalHurtByTarget(this, false, new Class[0]));
/*     */   }
/*     */   
/*     */   protected void initAttributes() {
/*  31 */     super.initAttributes();
/*  32 */     getAttributeInstance(GenericAttributes.MOVEMENT_SPEED).setValue(0.25D);
/*     */   }
/*     */   
/*     */   public int aE() {
/*  36 */     return getGoalTarget() == null ? 3 : 3 + (int)(getHealth() - 1.0F);
/*     */   }
/*     */   
/*     */   public void e(float f, float f1) {
/*  40 */     super.e(f, f1);
/*  41 */     this.fuseTicks = ((int)(this.fuseTicks + f * 1.5F));
/*  42 */     if (this.fuseTicks > this.maxFuseTicks - 5) {
/*  43 */       this.fuseTicks = (this.maxFuseTicks - 5);
/*     */     }
/*     */   }
/*     */   
/*     */   protected void h()
/*     */   {
/*  49 */     super.h();
/*  50 */     this.datawatcher.a(16, Byte.valueOf((byte)-1));
/*  51 */     this.datawatcher.a(17, Byte.valueOf((byte)0));
/*  52 */     this.datawatcher.a(18, Byte.valueOf((byte)0));
/*     */   }
/*     */   
/*     */   public void b(NBTTagCompound nbttagcompound) {
/*  56 */     super.b(nbttagcompound);
/*  57 */     if (this.datawatcher.getByte(17) == 1) {
/*  58 */       nbttagcompound.setBoolean("powered", true);
/*     */     }
/*     */     
/*  61 */     nbttagcompound.setShort("Fuse", (short)this.maxFuseTicks);
/*  62 */     nbttagcompound.setByte("ExplosionRadius", (byte)this.explosionRadius);
/*  63 */     nbttagcompound.setBoolean("ignited", cn());
/*     */   }
/*     */   
/*     */   public void a(NBTTagCompound nbttagcompound) {
/*  67 */     super.a(nbttagcompound);
/*  68 */     this.datawatcher.watch(17, Byte.valueOf((byte)(nbttagcompound.getBoolean("powered") ? 1 : 0)));
/*  69 */     if (nbttagcompound.hasKeyOfType("Fuse", 99)) {
/*  70 */       this.maxFuseTicks = nbttagcompound.getShort("Fuse");
/*     */     }
/*     */     
/*  73 */     if (nbttagcompound.hasKeyOfType("ExplosionRadius", 99)) {
/*  74 */       this.explosionRadius = nbttagcompound.getByte("ExplosionRadius");
/*     */     }
/*     */     
/*  77 */     if (nbttagcompound.getBoolean("ignited")) {
/*  78 */       co();
/*     */     }
/*     */   }
/*     */   
/*     */   public void t_()
/*     */   {
/*  84 */     if (isAlive()) {
/*  85 */       this.a = this.fuseTicks;
/*  86 */       if (cn()) {
/*  87 */         a(1);
/*     */       }
/*     */       
/*  90 */       int i = cm();
/*     */       
/*  92 */       if ((i > 0) && (this.fuseTicks == 0)) {
/*  93 */         makeSound("creeper.primed", 1.0F, 0.5F);
/*     */       }
/*     */       
/*  96 */       this.fuseTicks += i;
/*  97 */       if (this.fuseTicks < 0) {
/*  98 */         this.fuseTicks = 0;
/*     */       }
/*     */       
/* 101 */       if (this.fuseTicks >= this.maxFuseTicks) {
/* 102 */         this.fuseTicks = this.maxFuseTicks;
/* 103 */         cr();
/*     */       }
/*     */     }
/*     */     
/* 107 */     super.t_();
/*     */   }
/*     */   
/*     */   protected String bo() {
/* 111 */     return "mob.creeper.say";
/*     */   }
/*     */   
/*     */   protected String bp() {
/* 115 */     return "mob.creeper.death";
/*     */   }
/*     */   
/*     */   public void die(DamageSource damagesource)
/*     */   {
/* 120 */     if ((damagesource.getEntity() instanceof EntitySkeleton)) {
/* 121 */       int i = Item.getId(Items.RECORD_13);
/* 122 */       int j = Item.getId(Items.RECORD_WAIT);
/* 123 */       int k = i + this.random.nextInt(j - i + 1);
/*     */       
/*     */ 
/*     */ 
/* 127 */       this.record = k;
/*     */     }
/* 129 */     else if (((damagesource.getEntity() instanceof EntityCreeper)) && (damagesource.getEntity() != this) && (((EntityCreeper)damagesource.getEntity()).isPowered()) && (((EntityCreeper)damagesource.getEntity()).cp())) {
/* 130 */       ((EntityCreeper)damagesource.getEntity()).cq();
/*     */       
/*     */ 
/* 133 */       this.headDrop = new ItemStack(Items.SKULL, 1, 4);
/*     */     }
/*     */     
/*     */ 
/* 137 */     super.die(damagesource);
/*     */   }
/*     */   
/*     */ 
/*     */   protected void dropDeathLoot(boolean flag, int i)
/*     */   {
/* 143 */     super.dropDeathLoot(flag, i);
/*     */     
/*     */ 
/* 146 */     if (this.record != -1) {
/* 147 */       a(Item.getById(this.record), 1);
/* 148 */       this.record = -1;
/*     */     }
/*     */   }
/*     */   
/*     */   public boolean r(Entity entity)
/*     */   {
/* 154 */     return true;
/*     */   }
/*     */   
/*     */   public boolean isPowered() {
/* 158 */     return this.datawatcher.getByte(17) == 1;
/*     */   }
/*     */   
/*     */   protected Item getLoot() {
/* 162 */     return Items.GUNPOWDER;
/*     */   }
/*     */   
/*     */   public int cm() {
/* 166 */     return this.datawatcher.getByte(16);
/*     */   }
/*     */   
/*     */   public void a(int i) {
/* 170 */     this.datawatcher.watch(16, Byte.valueOf((byte)i));
/*     */   }
/*     */   
/*     */   public void onLightningStrike(EntityLightning entitylightning) {
/* 174 */     super.onLightningStrike(entitylightning);
/*     */     
/* 176 */     if (org.bukkit.craftbukkit.v1_8_R3.event.CraftEventFactory.callCreeperPowerEvent(this, entitylightning, org.bukkit.event.entity.CreeperPowerEvent.PowerCause.LIGHTNING).isCancelled()) {
/* 177 */       return;
/*     */     }
/*     */     
/* 180 */     setPowered(true);
/*     */   }
/*     */   
/*     */   public void setPowered(boolean powered) {
/* 184 */     if (!powered) {
/* 185 */       this.datawatcher.watch(17, Byte.valueOf((byte)0));
/*     */     } else {
/* 187 */       this.datawatcher.watch(17, Byte.valueOf((byte)1));
/*     */     }
/*     */   }
/*     */   
/*     */   protected boolean a(EntityHuman entityhuman)
/*     */   {
/* 193 */     ItemStack itemstack = entityhuman.inventory.getItemInHand();
/*     */     
/* 195 */     if ((itemstack != null) && (itemstack.getItem() == Items.FLINT_AND_STEEL)) {
/* 196 */       this.world.makeSound(this.locX + 0.5D, this.locY + 0.5D, this.locZ + 0.5D, "fire.ignite", 1.0F, this.random.nextFloat() * 0.4F + 0.8F);
/* 197 */       entityhuman.bw();
/* 198 */       if (!this.world.isClientSide) {
/* 199 */         co();
/* 200 */         itemstack.damage(1, entityhuman);
/* 201 */         return true;
/*     */       }
/*     */     }
/*     */     
/* 205 */     return super.a(entityhuman);
/*     */   }
/*     */   
/*     */   private void cr() {
/* 209 */     if (!this.world.isClientSide) {
/* 210 */       boolean flag = this.world.getGameRules().getBoolean("mobGriefing");
/* 211 */       float f = isPowered() ? 2.0F : 1.0F;
/*     */       
/* 213 */       ExplosionPrimeEvent event = new ExplosionPrimeEvent(getBukkitEntity(), this.explosionRadius * f, false);
/* 214 */       this.world.getServer().getPluginManager().callEvent(event);
/* 215 */       if (!event.isCancelled()) {
/* 216 */         this.world.createExplosion(this, this.locX, this.locY, this.locZ, event.getRadius(), event.getFire(), flag);
/* 217 */         die();
/*     */       } else {
/* 219 */         this.fuseTicks = 0;
/*     */       }
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */   public boolean cn()
/*     */   {
/* 227 */     return this.datawatcher.getByte(18) != 0;
/*     */   }
/*     */   
/*     */   public void co() {
/* 231 */     this.datawatcher.watch(18, Byte.valueOf((byte)1));
/*     */   }
/*     */   
/*     */   public boolean cp() {
/* 235 */     return (this.bn < 1) && (this.world.getGameRules().getBoolean("doMobLoot"));
/*     */   }
/*     */   
/*     */   public void cq() {
/* 239 */     this.bn += 1;
/*     */   }
/*     */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_8_R3\EntityCreeper.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */
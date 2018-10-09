/*     */ package net.minecraft.server.v1_8_R3;
/*     */ 
/*     */ import java.util.Random;
/*     */ 
/*     */ public class EntityOcelot extends EntityTameableAnimal
/*     */ {
/*     */   private PathfinderGoalAvoidTarget<EntityHuman> bo;
/*     */   private PathfinderGoalTempt bp;
/*   9 */   public boolean spawnBonus = true;
/*     */   
/*     */   public EntityOcelot(World world) {
/*  12 */     super(world);
/*  13 */     setSize(0.6F, 0.7F);
/*  14 */     ((Navigation)getNavigation()).a(true);
/*  15 */     this.goalSelector.a(1, new PathfinderGoalFloat(this));
/*  16 */     this.goalSelector.a(2, this.bm);
/*  17 */     this.goalSelector.a(3, this.bp = new PathfinderGoalTempt(this, 0.6D, Items.FISH, true));
/*  18 */     this.goalSelector.a(5, new PathfinderGoalFollowOwner(this, 1.0D, 10.0F, 5.0F));
/*  19 */     this.goalSelector.a(6, new PathfinderGoalJumpOnBlock(this, 0.8D));
/*  20 */     this.goalSelector.a(7, new PathfinderGoalLeapAtTarget(this, 0.3F));
/*  21 */     this.goalSelector.a(8, new PathfinderGoalOcelotAttack(this));
/*  22 */     this.goalSelector.a(9, new PathfinderGoalBreed(this, 0.8D));
/*  23 */     this.goalSelector.a(10, new PathfinderGoalRandomStroll(this, 0.8D));
/*  24 */     this.goalSelector.a(11, new PathfinderGoalLookAtPlayer(this, EntityHuman.class, 10.0F));
/*  25 */     this.targetSelector.a(1, new PathfinderGoalRandomTargetNonTamed(this, EntityChicken.class, false, null));
/*     */   }
/*     */   
/*     */   protected void h() {
/*  29 */     super.h();
/*  30 */     this.datawatcher.a(18, Byte.valueOf((byte)0));
/*     */   }
/*     */   
/*     */   public void E() {
/*  34 */     if (getControllerMove().a()) {
/*  35 */       double d0 = getControllerMove().b();
/*     */       
/*  37 */       if (d0 == 0.6D) {
/*  38 */         setSneaking(true);
/*  39 */         setSprinting(false);
/*  40 */       } else if (d0 == 1.33D) {
/*  41 */         setSneaking(false);
/*  42 */         setSprinting(true);
/*     */       } else {
/*  44 */         setSneaking(false);
/*  45 */         setSprinting(false);
/*     */       }
/*     */     } else {
/*  48 */       setSneaking(false);
/*  49 */       setSprinting(false);
/*     */     }
/*     */   }
/*     */   
/*     */   protected boolean isTypeNotPersistent()
/*     */   {
/*  55 */     return !isTamed();
/*     */   }
/*     */   
/*     */   protected void initAttributes() {
/*  59 */     super.initAttributes();
/*  60 */     getAttributeInstance(GenericAttributes.maxHealth).setValue(10.0D);
/*  61 */     getAttributeInstance(GenericAttributes.MOVEMENT_SPEED).setValue(0.30000001192092896D);
/*     */   }
/*     */   
/*     */   public void e(float f, float f1) {}
/*     */   
/*     */   public void b(NBTTagCompound nbttagcompound) {
/*  67 */     super.b(nbttagcompound);
/*  68 */     nbttagcompound.setInt("CatType", getCatType());
/*     */   }
/*     */   
/*     */   public void a(NBTTagCompound nbttagcompound) {
/*  72 */     super.a(nbttagcompound);
/*  73 */     setCatType(nbttagcompound.getInt("CatType"));
/*     */   }
/*     */   
/*     */   protected String z() {
/*  77 */     return isTamed() ? "mob.cat.meow" : this.random.nextInt(4) == 0 ? "mob.cat.purreow" : isInLove() ? "mob.cat.purr" : "";
/*     */   }
/*     */   
/*     */   protected String bo() {
/*  81 */     return "mob.cat.hitt";
/*     */   }
/*     */   
/*     */   protected String bp() {
/*  85 */     return "mob.cat.hitt";
/*     */   }
/*     */   
/*     */   protected float bB() {
/*  89 */     return 0.4F;
/*     */   }
/*     */   
/*     */   protected Item getLoot() {
/*  93 */     return Items.LEATHER;
/*     */   }
/*     */   
/*     */   public boolean r(Entity entity) {
/*  97 */     return entity.damageEntity(DamageSource.mobAttack(this), 3.0F);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   protected void dropDeathLoot(boolean flag, int i) {}
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public boolean a(EntityHuman entityhuman)
/*     */   {
/* 116 */     ItemStack itemstack = entityhuman.inventory.getItemInHand();
/*     */     
/* 118 */     if (isTamed()) {
/* 119 */       if ((e(entityhuman)) && (!this.world.isClientSide) && (!d(itemstack))) {
/* 120 */         this.bm.setSitting(!isSitting());
/*     */       }
/* 122 */     } else if ((this.bp.f()) && (itemstack != null) && (itemstack.getItem() == Items.FISH) && (entityhuman.h(this) < 9.0D)) {
/* 123 */       if (!entityhuman.abilities.canInstantlyBuild) {
/* 124 */         itemstack.count -= 1;
/*     */       }
/*     */       
/* 127 */       if (itemstack.count <= 0) {
/* 128 */         entityhuman.inventory.setItem(entityhuman.inventory.itemInHandIndex, null);
/*     */       }
/*     */       
/* 131 */       if (!this.world.isClientSide)
/*     */       {
/* 133 */         if ((this.random.nextInt(3) == 0) && (!org.bukkit.craftbukkit.v1_8_R3.event.CraftEventFactory.callEntityTameEvent(this, entityhuman).isCancelled())) {
/* 134 */           setTamed(true);
/* 135 */           setCatType(1 + this.world.random.nextInt(3));
/* 136 */           setOwnerUUID(entityhuman.getUniqueID().toString());
/* 137 */           l(true);
/* 138 */           this.bm.setSitting(true);
/* 139 */           this.world.broadcastEntityEffect(this, (byte)7);
/*     */         } else {
/* 141 */           l(false);
/* 142 */           this.world.broadcastEntityEffect(this, (byte)6);
/*     */         }
/*     */       }
/*     */       
/* 146 */       return true;
/*     */     }
/*     */     
/* 149 */     return super.a(entityhuman);
/*     */   }
/*     */   
/*     */   public EntityOcelot b(EntityAgeable entityageable) {
/* 153 */     EntityOcelot entityocelot = new EntityOcelot(this.world);
/*     */     
/* 155 */     if (isTamed()) {
/* 156 */       entityocelot.setOwnerUUID(getOwnerUUID());
/* 157 */       entityocelot.setTamed(true);
/* 158 */       entityocelot.setCatType(getCatType());
/*     */     }
/*     */     
/* 161 */     return entityocelot;
/*     */   }
/*     */   
/*     */   public boolean d(ItemStack itemstack) {
/* 165 */     return (itemstack != null) && (itemstack.getItem() == Items.FISH);
/*     */   }
/*     */   
/*     */   public boolean mate(EntityAnimal entityanimal) {
/* 169 */     if (entityanimal == this)
/* 170 */       return false;
/* 171 */     if (!isTamed())
/* 172 */       return false;
/* 173 */     if (!(entityanimal instanceof EntityOcelot)) {
/* 174 */       return false;
/*     */     }
/* 176 */     EntityOcelot entityocelot = (EntityOcelot)entityanimal;
/*     */     
/* 178 */     return entityocelot.isTamed();
/*     */   }
/*     */   
/*     */   public int getCatType()
/*     */   {
/* 183 */     return this.datawatcher.getByte(18);
/*     */   }
/*     */   
/*     */   public void setCatType(int i) {
/* 187 */     this.datawatcher.watch(18, Byte.valueOf((byte)i));
/*     */   }
/*     */   
/*     */   public boolean bR() {
/* 191 */     return this.world.random.nextInt(3) != 0;
/*     */   }
/*     */   
/*     */   public boolean canSpawn() {
/* 195 */     if ((this.world.a(getBoundingBox(), this)) && (this.world.getCubes(this, getBoundingBox()).isEmpty()) && (!this.world.containsLiquid(getBoundingBox()))) {
/* 196 */       BlockPosition blockposition = new BlockPosition(this.locX, getBoundingBox().b, this.locZ);
/*     */       
/* 198 */       if (blockposition.getY() < this.world.F()) {
/* 199 */         return false;
/*     */       }
/*     */       
/* 202 */       Block block = this.world.getType(blockposition.down()).getBlock();
/*     */       
/* 204 */       if ((block == Blocks.GRASS) || (block.getMaterial() == Material.LEAVES)) {
/* 205 */         return true;
/*     */       }
/*     */     }
/*     */     
/* 209 */     return false;
/*     */   }
/*     */   
/*     */   public String getName() {
/* 213 */     return isTamed() ? LocaleI18n.get("entity.Cat.name") : hasCustomName() ? getCustomName() : super.getName();
/*     */   }
/*     */   
/*     */   public void setTamed(boolean flag) {
/* 217 */     super.setTamed(flag);
/*     */   }
/*     */   
/*     */   protected void cm() {
/* 221 */     if (this.bo == null) {
/* 222 */       this.bo = new PathfinderGoalAvoidTarget(this, EntityHuman.class, 16.0F, 0.8D, 1.33D);
/*     */     }
/*     */     
/* 225 */     this.goalSelector.a(this.bo);
/* 226 */     if (!isTamed()) {
/* 227 */       this.goalSelector.a(4, this.bo);
/*     */     }
/*     */   }
/*     */   
/*     */   public GroupDataEntity prepare(DifficultyDamageScaler difficultydamagescaler, GroupDataEntity groupdataentity)
/*     */   {
/* 233 */     groupdataentity = super.prepare(difficultydamagescaler, groupdataentity);
/* 234 */     if ((this.spawnBonus) && (this.world.random.nextInt(7) == 0)) {
/* 235 */       for (int i = 0; i < 2; i++) {
/* 236 */         EntityOcelot entityocelot = new EntityOcelot(this.world);
/*     */         
/* 238 */         entityocelot.setPositionRotation(this.locX, this.locY, this.locZ, this.yaw, 0.0F);
/* 239 */         entityocelot.setAgeRaw(41536);
/* 240 */         this.world.addEntity(entityocelot, org.bukkit.event.entity.CreatureSpawnEvent.SpawnReason.OCELOT_BABY);
/*     */       }
/*     */     }
/*     */     
/* 244 */     return groupdataentity;
/*     */   }
/*     */   
/*     */   public EntityAgeable createChild(EntityAgeable entityageable) {
/* 248 */     return b(entityageable);
/*     */   }
/*     */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_8_R3\EntityOcelot.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */
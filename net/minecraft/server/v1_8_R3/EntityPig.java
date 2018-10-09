/*     */ package net.minecraft.server.v1_8_R3;
/*     */ 
/*     */ import java.util.Random;
/*     */ 
/*     */ public class EntityPig extends EntityAnimal
/*     */ {
/*     */   private final PathfinderGoalPassengerCarrotStick bm;
/*     */   
/*     */   public EntityPig(World world) {
/*  10 */     super(world);
/*  11 */     setSize(0.9F, 0.9F);
/*  12 */     ((Navigation)getNavigation()).a(true);
/*  13 */     this.goalSelector.a(0, new PathfinderGoalFloat(this));
/*  14 */     this.goalSelector.a(1, new PathfinderGoalPanic(this, 1.25D));
/*  15 */     this.goalSelector.a(2, this.bm = new PathfinderGoalPassengerCarrotStick(this, 0.3F));
/*  16 */     this.goalSelector.a(3, new PathfinderGoalBreed(this, 1.0D));
/*  17 */     this.goalSelector.a(4, new PathfinderGoalTempt(this, 1.2D, Items.CARROT_ON_A_STICK, false));
/*  18 */     this.goalSelector.a(4, new PathfinderGoalTempt(this, 1.2D, Items.CARROT, false));
/*  19 */     this.goalSelector.a(5, new PathfinderGoalFollowParent(this, 1.1D));
/*  20 */     this.goalSelector.a(6, new PathfinderGoalRandomStroll(this, 1.0D));
/*  21 */     this.goalSelector.a(7, new PathfinderGoalLookAtPlayer(this, EntityHuman.class, 6.0F));
/*  22 */     this.goalSelector.a(8, new PathfinderGoalRandomLookaround(this));
/*     */   }
/*     */   
/*     */   protected void initAttributes() {
/*  26 */     super.initAttributes();
/*  27 */     getAttributeInstance(GenericAttributes.maxHealth).setValue(10.0D);
/*  28 */     getAttributeInstance(GenericAttributes.MOVEMENT_SPEED).setValue(0.25D);
/*     */   }
/*     */   
/*     */   public boolean bW() {
/*  32 */     ItemStack itemstack = ((EntityHuman)this.passenger).bA();
/*     */     
/*  34 */     return (itemstack != null) && (itemstack.getItem() == Items.CARROT_ON_A_STICK);
/*     */   }
/*     */   
/*     */   protected void h() {
/*  38 */     super.h();
/*  39 */     this.datawatcher.a(16, Byte.valueOf((byte)0));
/*     */   }
/*     */   
/*     */   public void b(NBTTagCompound nbttagcompound) {
/*  43 */     super.b(nbttagcompound);
/*  44 */     nbttagcompound.setBoolean("Saddle", hasSaddle());
/*     */   }
/*     */   
/*     */   public void a(NBTTagCompound nbttagcompound) {
/*  48 */     super.a(nbttagcompound);
/*  49 */     setSaddle(nbttagcompound.getBoolean("Saddle"));
/*     */   }
/*     */   
/*     */   protected String z() {
/*  53 */     return "mob.pig.say";
/*     */   }
/*     */   
/*     */   protected String bo() {
/*  57 */     return "mob.pig.say";
/*     */   }
/*     */   
/*     */   protected String bp() {
/*  61 */     return "mob.pig.death";
/*     */   }
/*     */   
/*     */   protected void a(BlockPosition blockposition, Block block) {
/*  65 */     makeSound("mob.pig.step", 0.15F, 1.0F);
/*     */   }
/*     */   
/*     */   public boolean a(EntityHuman entityhuman) {
/*  69 */     if (super.a(entityhuman))
/*  70 */       return true;
/*  71 */     if ((hasSaddle()) && (!this.world.isClientSide) && ((this.passenger == null) || (this.passenger == entityhuman))) {
/*  72 */       entityhuman.mount(this);
/*  73 */       return true;
/*     */     }
/*  75 */     return false;
/*     */   }
/*     */   
/*     */   protected Item getLoot()
/*     */   {
/*  80 */     return isBurning() ? Items.COOKED_PORKCHOP : Items.PORKCHOP;
/*     */   }
/*     */   
/*     */   protected void dropDeathLoot(boolean flag, int i) {
/*  84 */     int j = this.random.nextInt(3) + 1 + this.random.nextInt(1 + i);
/*     */     
/*  86 */     for (int k = 0; k < j; k++) {
/*  87 */       if (isBurning()) {
/*  88 */         a(Items.COOKED_PORKCHOP, 1);
/*     */       } else {
/*  90 */         a(Items.PORKCHOP, 1);
/*     */       }
/*     */     }
/*     */     
/*  94 */     if (hasSaddle()) {
/*  95 */       a(Items.SADDLE, 1);
/*     */     }
/*     */   }
/*     */   
/*     */   public boolean hasSaddle()
/*     */   {
/* 101 */     return (this.datawatcher.getByte(16) & 0x1) != 0;
/*     */   }
/*     */   
/*     */   public void setSaddle(boolean flag) {
/* 105 */     if (flag) {
/* 106 */       this.datawatcher.watch(16, Byte.valueOf((byte)1));
/*     */     } else {
/* 108 */       this.datawatcher.watch(16, Byte.valueOf((byte)0));
/*     */     }
/*     */   }
/*     */   
/*     */   public void onLightningStrike(EntityLightning entitylightning)
/*     */   {
/* 114 */     if ((!this.world.isClientSide) && (!this.dead)) {
/* 115 */       EntityPigZombie entitypigzombie = new EntityPigZombie(this.world);
/*     */       
/*     */ 
/* 118 */       if (org.bukkit.craftbukkit.v1_8_R3.event.CraftEventFactory.callPigZapEvent(this, entitylightning, entitypigzombie).isCancelled()) {
/* 119 */         return;
/*     */       }
/*     */       
/*     */ 
/* 123 */       entitypigzombie.setEquipment(0, new ItemStack(Items.GOLDEN_SWORD));
/* 124 */       entitypigzombie.setPositionRotation(this.locX, this.locY, this.locZ, this.yaw, this.pitch);
/* 125 */       entitypigzombie.k(ce());
/* 126 */       if (hasCustomName()) {
/* 127 */         entitypigzombie.setCustomName(getCustomName());
/* 128 */         entitypigzombie.setCustomNameVisible(getCustomNameVisible());
/*     */       }
/*     */       
/*     */ 
/* 132 */       this.world.addEntity(entitypigzombie, org.bukkit.event.entity.CreatureSpawnEvent.SpawnReason.LIGHTNING);
/* 133 */       die();
/*     */     }
/*     */   }
/*     */   
/*     */   public void e(float f, float f1) {
/* 138 */     super.e(f, f1);
/* 139 */     if ((f > 5.0F) && ((this.passenger instanceof EntityHuman))) {
/* 140 */       ((EntityHuman)this.passenger).b(AchievementList.u);
/*     */     }
/*     */   }
/*     */   
/*     */   public EntityPig b(EntityAgeable entityageable)
/*     */   {
/* 146 */     return new EntityPig(this.world);
/*     */   }
/*     */   
/*     */   public boolean d(ItemStack itemstack) {
/* 150 */     return (itemstack != null) && (itemstack.getItem() == Items.CARROT);
/*     */   }
/*     */   
/*     */   public PathfinderGoalPassengerCarrotStick cm() {
/* 154 */     return this.bm;
/*     */   }
/*     */   
/*     */   public EntityAgeable createChild(EntityAgeable entityageable) {
/* 158 */     return b(entityageable);
/*     */   }
/*     */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_8_R3\EntityPig.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */
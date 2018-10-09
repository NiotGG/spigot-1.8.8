/*     */ package net.minecraft.server.v1_8_R3;
/*     */ 
/*     */ import java.util.Random;
/*     */ 
/*     */ public class EntityChicken extends EntityAnimal { public float bm;
/*     */   public float bo;
/*     */   public float bp;
/*     */   public float bq;
/*   9 */   public float br = 1.0F;
/*     */   public int bs;
/*     */   public boolean bt;
/*     */   
/*     */   public EntityChicken(World world) {
/*  14 */     super(world);
/*  15 */     setSize(0.4F, 0.7F);
/*  16 */     this.bs = (this.random.nextInt(6000) + 6000);
/*  17 */     this.goalSelector.a(0, new PathfinderGoalFloat(this));
/*  18 */     this.goalSelector.a(1, new PathfinderGoalPanic(this, 1.4D));
/*  19 */     this.goalSelector.a(2, new PathfinderGoalBreed(this, 1.0D));
/*  20 */     this.goalSelector.a(3, new PathfinderGoalTempt(this, 1.0D, Items.WHEAT_SEEDS, false));
/*  21 */     this.goalSelector.a(4, new PathfinderGoalFollowParent(this, 1.1D));
/*  22 */     this.goalSelector.a(5, new PathfinderGoalRandomStroll(this, 1.0D));
/*  23 */     this.goalSelector.a(6, new PathfinderGoalLookAtPlayer(this, EntityHuman.class, 6.0F));
/*  24 */     this.goalSelector.a(7, new PathfinderGoalRandomLookaround(this));
/*     */   }
/*     */   
/*     */   public float getHeadHeight() {
/*  28 */     return this.length;
/*     */   }
/*     */   
/*     */   protected void initAttributes() {
/*  32 */     super.initAttributes();
/*  33 */     getAttributeInstance(GenericAttributes.maxHealth).setValue(4.0D);
/*  34 */     getAttributeInstance(GenericAttributes.MOVEMENT_SPEED).setValue(0.25D);
/*     */   }
/*     */   
/*     */   public void m()
/*     */   {
/*  39 */     if (isChickenJockey()) {
/*  40 */       this.persistent = (!isTypeNotPersistent());
/*     */     }
/*     */     
/*  43 */     super.m();
/*  44 */     this.bq = this.bm;
/*  45 */     this.bp = this.bo;
/*  46 */     this.bo = ((float)(this.bo + (this.onGround ? -1 : 4) * 0.3D));
/*  47 */     this.bo = MathHelper.a(this.bo, 0.0F, 1.0F);
/*  48 */     if ((!this.onGround) && (this.br < 1.0F)) {
/*  49 */       this.br = 1.0F;
/*     */     }
/*     */     
/*  52 */     this.br = ((float)(this.br * 0.9D));
/*  53 */     if ((!this.onGround) && (this.motY < 0.0D)) {
/*  54 */       this.motY *= 0.6D;
/*     */     }
/*     */     
/*  57 */     this.bm += this.br * 2.0F;
/*  58 */     if ((!this.world.isClientSide) && (!isBaby()) && (!isChickenJockey()) && (--this.bs <= 0)) {
/*  59 */       makeSound("mob.chicken.plop", 1.0F, (this.random.nextFloat() - this.random.nextFloat()) * 0.2F + 1.0F);
/*  60 */       a(Items.EGG, 1);
/*  61 */       this.bs = (this.random.nextInt(6000) + 6000);
/*     */     }
/*     */   }
/*     */   
/*     */   public void e(float f, float f1) {}
/*     */   
/*     */   protected String z()
/*     */   {
/*  69 */     return "mob.chicken.say";
/*     */   }
/*     */   
/*     */   protected String bo() {
/*  73 */     return "mob.chicken.hurt";
/*     */   }
/*     */   
/*     */   protected String bp() {
/*  77 */     return "mob.chicken.hurt";
/*     */   }
/*     */   
/*     */   protected void a(BlockPosition blockposition, Block block) {
/*  81 */     makeSound("mob.chicken.step", 0.15F, 1.0F);
/*     */   }
/*     */   
/*     */   protected Item getLoot() {
/*  85 */     return Items.FEATHER;
/*     */   }
/*     */   
/*     */   protected void dropDeathLoot(boolean flag, int i) {
/*  89 */     int j = this.random.nextInt(3) + this.random.nextInt(1 + i);
/*     */     
/*  91 */     for (int k = 0; k < j; k++) {
/*  92 */       a(Items.FEATHER, 1);
/*     */     }
/*     */     
/*  95 */     if (isBurning()) {
/*  96 */       a(Items.COOKED_CHICKEN, 1);
/*     */     } else {
/*  98 */       a(Items.CHICKEN, 1);
/*     */     }
/*     */   }
/*     */   
/*     */   public EntityChicken b(EntityAgeable entityageable)
/*     */   {
/* 104 */     return new EntityChicken(this.world);
/*     */   }
/*     */   
/*     */   public boolean d(ItemStack itemstack) {
/* 108 */     return (itemstack != null) && (itemstack.getItem() == Items.WHEAT_SEEDS);
/*     */   }
/*     */   
/*     */   public void a(NBTTagCompound nbttagcompound) {
/* 112 */     super.a(nbttagcompound);
/* 113 */     this.bt = nbttagcompound.getBoolean("IsChickenJockey");
/* 114 */     if (nbttagcompound.hasKey("EggLayTime")) {
/* 115 */       this.bs = nbttagcompound.getInt("EggLayTime");
/*     */     }
/*     */   }
/*     */   
/*     */   protected int getExpValue(EntityHuman entityhuman)
/*     */   {
/* 121 */     return isChickenJockey() ? 10 : super.getExpValue(entityhuman);
/*     */   }
/*     */   
/*     */   public void b(NBTTagCompound nbttagcompound) {
/* 125 */     super.b(nbttagcompound);
/* 126 */     nbttagcompound.setBoolean("IsChickenJockey", this.bt);
/* 127 */     nbttagcompound.setInt("EggLayTime", this.bs);
/*     */   }
/*     */   
/*     */   protected boolean isTypeNotPersistent() {
/* 131 */     return (isChickenJockey()) && (this.passenger == null);
/*     */   }
/*     */   
/*     */   public void al() {
/* 135 */     super.al();
/* 136 */     float f = MathHelper.sin(this.aI * 3.1415927F / 180.0F);
/* 137 */     float f1 = MathHelper.cos(this.aI * 3.1415927F / 180.0F);
/* 138 */     float f2 = 0.1F;
/* 139 */     float f3 = 0.0F;
/*     */     
/* 141 */     this.passenger.setPosition(this.locX + f2 * f, this.locY + this.length * 0.5F + this.passenger.am() + f3, this.locZ - f2 * f1);
/* 142 */     if ((this.passenger instanceof EntityLiving)) {
/* 143 */       ((EntityLiving)this.passenger).aI = this.aI;
/*     */     }
/*     */   }
/*     */   
/*     */   public boolean isChickenJockey()
/*     */   {
/* 149 */     return this.bt;
/*     */   }
/*     */   
/*     */   public void l(boolean flag) {
/* 153 */     this.bt = flag;
/*     */   }
/*     */   
/*     */   public EntityAgeable createChild(EntityAgeable entityageable) {
/* 157 */     return b(entityageable);
/*     */   }
/*     */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_8_R3\EntityChicken.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */
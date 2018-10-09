/*     */ package net.minecraft.server.v1_8_R3;
/*     */ 
/*     */ import java.util.Random;
/*     */ 
/*     */ public abstract class EntityAnimal extends EntityAgeable implements IAnimal {
/*     */   protected Block bn;
/*     */   private int bm;
/*     */   private EntityHuman bo;
/*     */   
/*  10 */   public EntityAnimal(World world) { super(world);
/*  11 */     this.bn = Blocks.GRASS;
/*     */   }
/*     */   
/*     */   protected void E() {
/*  15 */     if (getAge() != 0) {
/*  16 */       this.bm = 0;
/*     */     }
/*     */     
/*  19 */     super.E();
/*     */   }
/*     */   
/*     */   public void m() {
/*  23 */     super.m();
/*  24 */     if (getAge() != 0) {
/*  25 */       this.bm = 0;
/*     */     }
/*     */     
/*  28 */     if (this.bm > 0) {
/*  29 */       this.bm -= 1;
/*  30 */       if (this.bm % 10 == 0) {
/*  31 */         double d0 = this.random.nextGaussian() * 0.02D;
/*  32 */         double d1 = this.random.nextGaussian() * 0.02D;
/*  33 */         double d2 = this.random.nextGaussian() * 0.02D;
/*     */         
/*  35 */         this.world.addParticle(EnumParticle.HEART, this.locX + this.random.nextFloat() * this.width * 2.0F - this.width, this.locY + 0.5D + this.random.nextFloat() * this.length, this.locZ + this.random.nextFloat() * this.width * 2.0F - this.width, d0, d1, d2, new int[0]);
/*     */       }
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public float a(BlockPosition blockposition)
/*     */   {
/*  55 */     return this.world.getType(blockposition.down()).getBlock() == Blocks.GRASS ? 10.0F : this.world.o(blockposition) - 0.5F;
/*     */   }
/*     */   
/*     */   public void b(NBTTagCompound nbttagcompound) {
/*  59 */     super.b(nbttagcompound);
/*  60 */     nbttagcompound.setInt("InLove", this.bm);
/*     */   }
/*     */   
/*     */   public void a(NBTTagCompound nbttagcompound) {
/*  64 */     super.a(nbttagcompound);
/*  65 */     this.bm = nbttagcompound.getInt("InLove");
/*     */   }
/*     */   
/*     */   public boolean bR() {
/*  69 */     int i = MathHelper.floor(this.locX);
/*  70 */     int j = MathHelper.floor(getBoundingBox().b);
/*  71 */     int k = MathHelper.floor(this.locZ);
/*  72 */     BlockPosition blockposition = new BlockPosition(i, j, k);
/*     */     
/*  74 */     return (this.world.getType(blockposition.down()).getBlock() == this.bn) && (this.world.k(blockposition) > 8) && (super.bR());
/*     */   }
/*     */   
/*     */   public int w() {
/*  78 */     return 120;
/*     */   }
/*     */   
/*     */   protected boolean isTypeNotPersistent() {
/*  82 */     return false;
/*     */   }
/*     */   
/*     */   protected int getExpValue(EntityHuman entityhuman) {
/*  86 */     return 1 + this.world.random.nextInt(3);
/*     */   }
/*     */   
/*     */   public boolean d(ItemStack itemstack) {
/*  90 */     return itemstack != null;
/*     */   }
/*     */   
/*     */   public boolean a(EntityHuman entityhuman) {
/*  94 */     ItemStack itemstack = entityhuman.inventory.getItemInHand();
/*     */     
/*  96 */     if (itemstack != null) {
/*  97 */       if ((d(itemstack)) && (getAge() == 0) && (this.bm <= 0)) {
/*  98 */         a(entityhuman, itemstack);
/*  99 */         c(entityhuman);
/* 100 */         return true;
/*     */       }
/*     */       
/* 103 */       if ((isBaby()) && (d(itemstack))) {
/* 104 */         a(entityhuman, itemstack);
/* 105 */         setAge((int)(-getAge() / 20 * 0.1F), true);
/* 106 */         return true;
/*     */       }
/*     */     }
/*     */     
/* 110 */     return super.a(entityhuman);
/*     */   }
/*     */   
/*     */   protected void a(EntityHuman entityhuman, ItemStack itemstack) {
/* 114 */     if (!entityhuman.abilities.canInstantlyBuild) {
/* 115 */       itemstack.count -= 1;
/* 116 */       if (itemstack.count <= 0) {
/* 117 */         entityhuman.inventory.setItem(entityhuman.inventory.itemInHandIndex, null);
/*     */       }
/*     */     }
/*     */   }
/*     */   
/*     */   public void c(EntityHuman entityhuman)
/*     */   {
/* 124 */     this.bm = 600;
/* 125 */     this.bo = entityhuman;
/* 126 */     this.world.broadcastEntityEffect(this, (byte)18);
/*     */   }
/*     */   
/*     */   public EntityHuman cq() {
/* 130 */     return this.bo;
/*     */   }
/*     */   
/*     */   public boolean isInLove() {
/* 134 */     return this.bm > 0;
/*     */   }
/*     */   
/*     */   public void cs() {
/* 138 */     this.bm = 0;
/*     */   }
/*     */   
/*     */   public boolean mate(EntityAnimal entityanimal) {
/* 142 */     return entityanimal != this;
/*     */   }
/*     */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_8_R3\EntityAnimal.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */
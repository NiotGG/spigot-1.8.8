/*     */ package net.minecraft.server.v1_8_R3;
/*     */ 
/*     */ import java.util.Random;
/*     */ 
/*     */ public class EntityFireworks extends Entity
/*     */ {
/*     */   private int ticksFlown;
/*     */   public int expectedLifespan;
/*     */   
/*     */   public void inactiveTick()
/*     */   {
/*  12 */     this.ticksFlown += 1;
/*  13 */     super.inactiveTick();
/*     */   }
/*     */   
/*     */   public EntityFireworks(World world)
/*     */   {
/*  18 */     super(world);
/*  19 */     setSize(0.25F, 0.25F);
/*     */   }
/*     */   
/*     */   protected void h() {
/*  23 */     this.datawatcher.add(8, 5);
/*     */   }
/*     */   
/*     */   public EntityFireworks(World world, double d0, double d1, double d2, ItemStack itemstack) {
/*  27 */     super(world);
/*  28 */     this.ticksFlown = 0;
/*  29 */     setSize(0.25F, 0.25F);
/*  30 */     setPosition(d0, d1, d2);
/*  31 */     int i = 1;
/*     */     
/*  33 */     if ((itemstack != null) && (itemstack.hasTag())) {
/*  34 */       this.datawatcher.watch(8, itemstack);
/*  35 */       NBTTagCompound nbttagcompound = itemstack.getTag();
/*  36 */       NBTTagCompound nbttagcompound1 = nbttagcompound.getCompound("Fireworks");
/*     */       
/*  38 */       if (nbttagcompound1 != null) {
/*  39 */         i += nbttagcompound1.getByte("Flight");
/*     */       }
/*     */     }
/*     */     
/*  43 */     this.motX = (this.random.nextGaussian() * 0.001D);
/*  44 */     this.motZ = (this.random.nextGaussian() * 0.001D);
/*  45 */     this.motY = 0.05D;
/*  46 */     this.expectedLifespan = (10 * i + this.random.nextInt(6) + this.random.nextInt(7));
/*     */   }
/*     */   
/*     */   public void t_() {
/*  50 */     this.P = this.locX;
/*  51 */     this.Q = this.locY;
/*  52 */     this.R = this.locZ;
/*  53 */     super.t_();
/*  54 */     this.motX *= 1.15D;
/*  55 */     this.motZ *= 1.15D;
/*  56 */     this.motY += 0.04D;
/*  57 */     move(this.motX, this.motY, this.motZ);
/*  58 */     float f = MathHelper.sqrt(this.motX * this.motX + this.motZ * this.motZ);
/*     */     
/*  60 */     this.yaw = ((float)(MathHelper.b(this.motX, this.motZ) * 180.0D / 3.1415927410125732D));
/*     */     
/*  62 */     for (this.pitch = ((float)(MathHelper.b(this.motY, f) * 180.0D / 3.1415927410125732D)); this.pitch - this.lastPitch < -180.0F; this.lastPitch -= 360.0F) {}
/*     */     
/*     */ 
/*     */ 
/*  66 */     while (this.pitch - this.lastPitch >= 180.0F) {
/*  67 */       this.lastPitch += 360.0F;
/*     */     }
/*     */     
/*  70 */     while (this.yaw - this.lastYaw < -180.0F) {
/*  71 */       this.lastYaw -= 360.0F;
/*     */     }
/*     */     
/*  74 */     while (this.yaw - this.lastYaw >= 180.0F) {
/*  75 */       this.lastYaw += 360.0F;
/*     */     }
/*     */     
/*  78 */     this.pitch = (this.lastPitch + (this.pitch - this.lastPitch) * 0.2F);
/*  79 */     this.yaw = (this.lastYaw + (this.yaw - this.lastYaw) * 0.2F);
/*  80 */     if ((this.ticksFlown == 0) && (!R())) {
/*  81 */       this.world.makeSound(this, "fireworks.launch", 3.0F, 1.0F);
/*     */     }
/*     */     
/*  84 */     this.ticksFlown += 1;
/*  85 */     if ((this.world.isClientSide) && (this.ticksFlown % 2 < 2)) {
/*  86 */       this.world.addParticle(EnumParticle.FIREWORKS_SPARK, this.locX, this.locY - 0.3D, this.locZ, this.random.nextGaussian() * 0.05D, -this.motY * 0.5D, this.random.nextGaussian() * 0.05D, new int[0]);
/*     */     }
/*     */     
/*  89 */     if ((!this.world.isClientSide) && (this.ticksFlown > this.expectedLifespan)) {
/*  90 */       if (!org.bukkit.craftbukkit.v1_8_R3.event.CraftEventFactory.callFireworkExplodeEvent(this).isCancelled()) this.world.broadcastEntityEffect(this, (byte)17);
/*  91 */       die();
/*     */     }
/*     */   }
/*     */   
/*     */   public void b(NBTTagCompound nbttagcompound)
/*     */   {
/*  97 */     nbttagcompound.setInt("Life", this.ticksFlown);
/*  98 */     nbttagcompound.setInt("LifeTime", this.expectedLifespan);
/*  99 */     ItemStack itemstack = this.datawatcher.getItemStack(8);
/*     */     
/* 101 */     if (itemstack != null) {
/* 102 */       NBTTagCompound nbttagcompound1 = new NBTTagCompound();
/*     */       
/* 104 */       itemstack.save(nbttagcompound1);
/* 105 */       nbttagcompound.set("FireworksItem", nbttagcompound1);
/*     */     }
/*     */   }
/*     */   
/*     */   public void a(NBTTagCompound nbttagcompound)
/*     */   {
/* 111 */     this.ticksFlown = nbttagcompound.getInt("Life");
/* 112 */     this.expectedLifespan = nbttagcompound.getInt("LifeTime");
/* 113 */     NBTTagCompound nbttagcompound1 = nbttagcompound.getCompound("FireworksItem");
/*     */     
/* 115 */     if (nbttagcompound1 != null) {
/* 116 */       ItemStack itemstack = ItemStack.createStack(nbttagcompound1);
/*     */       
/* 118 */       if (itemstack != null) {
/* 119 */         this.datawatcher.watch(8, itemstack);
/*     */       }
/*     */     }
/*     */   }
/*     */   
/*     */   public float c(float f)
/*     */   {
/* 126 */     return super.c(f);
/*     */   }
/*     */   
/*     */   public boolean aD() {
/* 130 */     return false;
/*     */   }
/*     */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_8_R3\EntityFireworks.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */
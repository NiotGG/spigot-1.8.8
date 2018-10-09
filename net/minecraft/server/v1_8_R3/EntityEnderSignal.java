/*     */ package net.minecraft.server.v1_8_R3;
/*     */ 
/*     */ import java.util.Random;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class EntityEnderSignal
/*     */   extends Entity
/*     */ {
/*     */   private double a;
/*     */   private double b;
/*     */   private double c;
/*     */   private int d;
/*     */   private boolean e;
/*     */   
/*     */   public EntityEnderSignal(World paramWorld)
/*     */   {
/*  22 */     super(paramWorld);
/*  23 */     setSize(0.25F, 0.25F);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   protected void h() {}
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public EntityEnderSignal(World paramWorld, double paramDouble1, double paramDouble2, double paramDouble3)
/*     */   {
/*  41 */     super(paramWorld);
/*  42 */     this.d = 0;
/*     */     
/*  44 */     setSize(0.25F, 0.25F);
/*     */     
/*  46 */     setPosition(paramDouble1, paramDouble2, paramDouble3);
/*     */   }
/*     */   
/*     */   public void a(BlockPosition paramBlockPosition) {
/*  50 */     double d1 = paramBlockPosition.getX();
/*  51 */     int i = paramBlockPosition.getY();
/*  52 */     double d2 = paramBlockPosition.getZ();
/*     */     
/*  54 */     double d3 = d1 - this.locX;double d4 = d2 - this.locZ;
/*  55 */     float f = MathHelper.sqrt(d3 * d3 + d4 * d4);
/*     */     
/*  57 */     if (f > 12.0F) {
/*  58 */       this.a = (this.locX + d3 / f * 12.0D);
/*  59 */       this.c = (this.locZ + d4 / f * 12.0D);
/*  60 */       this.b = (this.locY + 8.0D);
/*     */     } else {
/*  62 */       this.a = d1;
/*  63 */       this.b = i;
/*  64 */       this.c = d2;
/*     */     }
/*     */     
/*  67 */     this.d = 0;
/*  68 */     this.e = (this.random.nextInt(5) > 0);
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
/*     */   public void t_()
/*     */   {
/*  85 */     this.P = this.locX;
/*  86 */     this.Q = this.locY;
/*  87 */     this.R = this.locZ;
/*  88 */     super.t_();
/*     */     
/*  90 */     this.locX += this.motX;
/*  91 */     this.locY += this.motY;
/*  92 */     this.locZ += this.motZ;
/*     */     
/*  94 */     float f1 = MathHelper.sqrt(this.motX * this.motX + this.motZ * this.motZ);
/*  95 */     this.yaw = ((float)(MathHelper.b(this.motX, this.motZ) * 180.0D / 3.1415927410125732D));
/*  96 */     this.pitch = ((float)(MathHelper.b(this.motY, f1) * 180.0D / 3.1415927410125732D));
/*     */     
/*  98 */     while (this.pitch - this.lastPitch < -180.0F) {
/*  99 */       this.lastPitch -= 360.0F;
/*     */     }
/* 101 */     while (this.pitch - this.lastPitch >= 180.0F) {
/* 102 */       this.lastPitch += 360.0F;
/*     */     }
/*     */     
/* 105 */     while (this.yaw - this.lastYaw < -180.0F) {
/* 106 */       this.lastYaw -= 360.0F;
/*     */     }
/* 108 */     while (this.yaw - this.lastYaw >= 180.0F) {
/* 109 */       this.lastYaw += 360.0F;
/*     */     }
/*     */     
/* 112 */     this.pitch = (this.lastPitch + (this.pitch - this.lastPitch) * 0.2F);
/* 113 */     this.yaw = (this.lastYaw + (this.yaw - this.lastYaw) * 0.2F);
/*     */     
/* 115 */     if (!this.world.isClientSide) {
/* 116 */       double d1 = this.a - this.locX;double d2 = this.c - this.locZ;
/* 117 */       float f2 = (float)Math.sqrt(d1 * d1 + d2 * d2);
/* 118 */       float f3 = (float)MathHelper.b(d2, d1);
/* 119 */       double d3 = f1 + (f2 - f1) * 0.0025D;
/* 120 */       if (f2 < 1.0F) {
/* 121 */         d3 *= 0.8D;
/* 122 */         this.motY *= 0.8D;
/*     */       }
/* 124 */       this.motX = (Math.cos(f3) * d3);
/* 125 */       this.motZ = (Math.sin(f3) * d3);
/*     */       
/* 127 */       if (this.locY < this.b) {
/* 128 */         this.motY += (1.0D - this.motY) * 0.014999999664723873D;
/*     */       } else {
/* 130 */         this.motY += (-1.0D - this.motY) * 0.014999999664723873D;
/*     */       }
/*     */     }
/*     */     
/* 134 */     float f4 = 0.25F;
/* 135 */     if (V()) {
/* 136 */       for (int i = 0; i < 4; i++) {
/* 137 */         this.world.addParticle(EnumParticle.WATER_BUBBLE, this.locX - this.motX * f4, this.locY - this.motY * f4, this.locZ - this.motZ * f4, this.motX, this.motY, this.motZ, new int[0]);
/*     */       }
/*     */     } else {
/* 140 */       this.world.addParticle(EnumParticle.PORTAL, this.locX - this.motX * f4 + this.random.nextDouble() * 0.6D - 0.3D, this.locY - this.motY * f4 - 0.5D, this.locZ - this.motZ * f4 + this.random.nextDouble() * 0.6D - 0.3D, this.motX, this.motY, this.motZ, new int[0]);
/*     */     }
/*     */     
/* 143 */     if (!this.world.isClientSide) {
/* 144 */       setPosition(this.locX, this.locY, this.locZ);
/*     */       
/* 146 */       this.d += 1;
/* 147 */       if ((this.d > 80) && (!this.world.isClientSide)) {
/* 148 */         die();
/* 149 */         if (this.e) {
/* 150 */           this.world.addEntity(new EntityItem(this.world, this.locX, this.locY, this.locZ, new ItemStack(Items.ENDER_EYE)));
/*     */         } else {
/* 152 */           this.world.triggerEffect(2003, new BlockPosition(this), 0);
/*     */         }
/*     */       }
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public void b(NBTTagCompound paramNBTTagCompound) {}
/*     */   
/*     */ 
/*     */   public void a(NBTTagCompound paramNBTTagCompound) {}
/*     */   
/*     */ 
/*     */   public float c(float paramFloat)
/*     */   {
/* 168 */     return 1.0F;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public boolean aD()
/*     */   {
/* 178 */     return false;
/*     */   }
/*     */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_8_R3\EntityEnderSignal.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */
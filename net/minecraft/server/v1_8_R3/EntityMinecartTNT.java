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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class EntityMinecartTNT
/*     */   extends EntityMinecartAbstract
/*     */ {
/*  21 */   private int a = -1;
/*     */   
/*     */   public EntityMinecartTNT(World paramWorld) {
/*  24 */     super(paramWorld);
/*     */   }
/*     */   
/*     */   public EntityMinecartTNT(World paramWorld, double paramDouble1, double paramDouble2, double paramDouble3) {
/*  28 */     super(paramWorld, paramDouble1, paramDouble2, paramDouble3);
/*     */   }
/*     */   
/*     */   public EntityMinecartAbstract.EnumMinecartType s()
/*     */   {
/*  33 */     return EntityMinecartAbstract.EnumMinecartType.TNT;
/*     */   }
/*     */   
/*     */   public IBlockData u()
/*     */   {
/*  38 */     return Blocks.TNT.getBlockData();
/*     */   }
/*     */   
/*     */   public void t_()
/*     */   {
/*  43 */     super.t_();
/*     */     
/*  45 */     if (this.a > 0) {
/*  46 */       this.a -= 1;
/*  47 */       this.world.addParticle(EnumParticle.SMOKE_NORMAL, this.locX, this.locY + 0.5D, this.locZ, 0.0D, 0.0D, 0.0D, new int[0]);
/*  48 */     } else if (this.a == 0) {
/*  49 */       b(this.motX * this.motX + this.motZ * this.motZ);
/*     */     }
/*     */     
/*  52 */     if (this.positionChanged) {
/*  53 */       double d = this.motX * this.motX + this.motZ * this.motZ;
/*     */       
/*  55 */       if (d >= 0.009999999776482582D) {
/*  56 */         b(d);
/*     */       }
/*     */     }
/*     */   }
/*     */   
/*     */   public boolean damageEntity(DamageSource paramDamageSource, float paramFloat)
/*     */   {
/*  63 */     Entity localEntity = paramDamageSource.i();
/*  64 */     if ((localEntity instanceof EntityArrow)) {
/*  65 */       EntityArrow localEntityArrow = (EntityArrow)localEntity;
/*  66 */       if (localEntityArrow.isBurning()) {
/*  67 */         b(localEntityArrow.motX * localEntityArrow.motX + localEntityArrow.motY * localEntityArrow.motY + localEntityArrow.motZ * localEntityArrow.motZ);
/*     */       }
/*     */     }
/*  70 */     return super.damageEntity(paramDamageSource, paramFloat);
/*     */   }
/*     */   
/*     */   public void a(DamageSource paramDamageSource)
/*     */   {
/*  75 */     super.a(paramDamageSource);
/*     */     
/*  77 */     double d = this.motX * this.motX + this.motZ * this.motZ;
/*     */     
/*  79 */     if ((!paramDamageSource.isExplosion()) && (this.world.getGameRules().getBoolean("doEntityDrops"))) {
/*  80 */       a(new ItemStack(Blocks.TNT, 1), 0.0F);
/*     */     }
/*     */     
/*  83 */     if ((paramDamageSource.o()) || (paramDamageSource.isExplosion()) || (d >= 0.009999999776482582D)) {
/*  84 */       b(d);
/*     */     }
/*     */   }
/*     */   
/*     */   protected void b(double paramDouble) {
/*  89 */     if (!this.world.isClientSide) {
/*  90 */       double d = Math.sqrt(paramDouble);
/*  91 */       if (d > 5.0D) {
/*  92 */         d = 5.0D;
/*     */       }
/*  94 */       this.world.explode(this, this.locX, this.locY, this.locZ, (float)(4.0D + this.random.nextDouble() * 1.5D * d), true);
/*  95 */       die();
/*     */     }
/*     */   }
/*     */   
/*     */   public void e(float paramFloat1, float paramFloat2)
/*     */   {
/* 101 */     if (paramFloat1 >= 3.0F) {
/* 102 */       float f = paramFloat1 / 10.0F;
/* 103 */       b(f * f);
/*     */     }
/*     */     
/* 106 */     super.e(paramFloat1, paramFloat2);
/*     */   }
/*     */   
/*     */   public void a(int paramInt1, int paramInt2, int paramInt3, boolean paramBoolean)
/*     */   {
/* 111 */     if ((paramBoolean) && (this.a < 0)) {
/* 112 */       j();
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
/*     */   public void j()
/*     */   {
/* 126 */     this.a = 80;
/*     */     
/* 128 */     if (!this.world.isClientSide) {
/* 129 */       this.world.broadcastEntityEffect(this, (byte)10);
/* 130 */       if (!R()) {
/* 131 */         this.world.makeSound(this, "game.tnt.primed", 1.0F, 1.0F);
/*     */       }
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public boolean y()
/*     */   {
/* 141 */     return this.a > -1;
/*     */   }
/*     */   
/*     */   public float a(Explosion paramExplosion, World paramWorld, BlockPosition paramBlockPosition, IBlockData paramIBlockData)
/*     */   {
/* 146 */     if ((y()) && ((BlockMinecartTrackAbstract.d(paramIBlockData)) || (BlockMinecartTrackAbstract.e(paramWorld, paramBlockPosition.up())))) {
/* 147 */       return 0.0F;
/*     */     }
/*     */     
/* 150 */     return super.a(paramExplosion, paramWorld, paramBlockPosition, paramIBlockData);
/*     */   }
/*     */   
/*     */   public boolean a(Explosion paramExplosion, World paramWorld, BlockPosition paramBlockPosition, IBlockData paramIBlockData, float paramFloat)
/*     */   {
/* 155 */     if ((y()) && ((BlockMinecartTrackAbstract.d(paramIBlockData)) || (BlockMinecartTrackAbstract.e(paramWorld, paramBlockPosition.up())))) {
/* 156 */       return false;
/*     */     }
/*     */     
/* 159 */     return super.a(paramExplosion, paramWorld, paramBlockPosition, paramIBlockData, paramFloat);
/*     */   }
/*     */   
/*     */   protected void a(NBTTagCompound paramNBTTagCompound)
/*     */   {
/* 164 */     super.a(paramNBTTagCompound);
/* 165 */     if (paramNBTTagCompound.hasKeyOfType("TNTFuse", 99)) {
/* 166 */       this.a = paramNBTTagCompound.getInt("TNTFuse");
/*     */     }
/*     */   }
/*     */   
/*     */   protected void b(NBTTagCompound paramNBTTagCompound)
/*     */   {
/* 172 */     super.b(paramNBTTagCompound);
/* 173 */     paramNBTTagCompound.setInt("TNTFuse", this.a);
/*     */   }
/*     */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_8_R3\EntityMinecartTNT.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */
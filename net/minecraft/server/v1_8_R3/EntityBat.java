/*     */ package net.minecraft.server.v1_8_R3;
/*     */ 
/*     */ import java.util.Calendar;
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
/*     */ public class EntityBat
/*     */   extends EntityAmbient
/*     */ {
/*     */   private BlockPosition a;
/*     */   
/*     */   public EntityBat(World paramWorld)
/*     */   {
/*  22 */     super(paramWorld);
/*     */     
/*  24 */     setSize(0.5F, 0.9F);
/*  25 */     setAsleep(true);
/*     */   }
/*     */   
/*     */   protected void h()
/*     */   {
/*  30 */     super.h();
/*     */     
/*  32 */     this.datawatcher.a(16, new Byte((byte)0));
/*     */   }
/*     */   
/*     */   protected float bB()
/*     */   {
/*  37 */     return 0.1F;
/*     */   }
/*     */   
/*     */   protected float bC()
/*     */   {
/*  42 */     return super.bC() * 0.95F;
/*     */   }
/*     */   
/*     */   protected String z()
/*     */   {
/*  47 */     if ((isAsleep()) && (this.random.nextInt(4) != 0)) {
/*  48 */       return null;
/*     */     }
/*  50 */     return "mob.bat.idle";
/*     */   }
/*     */   
/*     */   protected String bo()
/*     */   {
/*  55 */     return "mob.bat.hurt";
/*     */   }
/*     */   
/*     */   protected String bp()
/*     */   {
/*  60 */     return "mob.bat.death";
/*     */   }
/*     */   
/*     */ 
/*     */   public boolean ae()
/*     */   {
/*  66 */     return false;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   protected void s(Entity paramEntity) {}
/*     */   
/*     */ 
/*     */ 
/*     */   protected void bL() {}
/*     */   
/*     */ 
/*     */ 
/*     */   protected void initAttributes()
/*     */   {
/*  81 */     super.initAttributes();
/*     */     
/*  83 */     getAttributeInstance(GenericAttributes.maxHealth).setValue(6.0D);
/*     */   }
/*     */   
/*     */   public boolean isAsleep() {
/*  87 */     return (this.datawatcher.getByte(16) & 0x1) != 0;
/*     */   }
/*     */   
/*     */   public void setAsleep(boolean paramBoolean) {
/*  91 */     int i = this.datawatcher.getByte(16);
/*  92 */     if (paramBoolean) {
/*  93 */       this.datawatcher.watch(16, Byte.valueOf((byte)(i | 0x1)));
/*     */     } else {
/*  95 */       this.datawatcher.watch(16, Byte.valueOf((byte)(i & 0xFFFFFFFE)));
/*     */     }
/*     */   }
/*     */   
/*     */   public void t_()
/*     */   {
/* 101 */     super.t_();
/*     */     
/* 103 */     if (isAsleep()) {
/* 104 */       this.motX = (this.motY = this.motZ = 0.0D);
/* 105 */       this.locY = (MathHelper.floor(this.locY) + 1.0D - this.length);
/*     */     } else {
/* 107 */       this.motY *= 0.6000000238418579D;
/*     */     }
/*     */   }
/*     */   
/*     */   protected void E()
/*     */   {
/* 113 */     super.E();
/*     */     
/* 115 */     BlockPosition localBlockPosition1 = new BlockPosition(this);
/* 116 */     BlockPosition localBlockPosition2 = localBlockPosition1.up();
/*     */     
/* 118 */     if (isAsleep()) {
/* 119 */       if (!this.world.getType(localBlockPosition2).getBlock().isOccluding()) {
/* 120 */         setAsleep(false);
/* 121 */         this.world.a(null, 1015, localBlockPosition1, 0);
/*     */       } else {
/* 123 */         if (this.random.nextInt(200) == 0) {
/* 124 */           this.aK = this.random.nextInt(360);
/*     */         }
/*     */         
/* 127 */         if (this.world.findNearbyPlayer(this, 4.0D) != null) {
/* 128 */           setAsleep(false);
/* 129 */           this.world.a(null, 1015, localBlockPosition1, 0);
/*     */         }
/*     */       }
/*     */     } else {
/* 133 */       if ((this.a != null) && ((!this.world.isEmpty(this.a)) || (this.a.getY() < 1))) {
/* 134 */         this.a = null;
/*     */       }
/* 136 */       if ((this.a == null) || (this.random.nextInt(30) == 0) || (this.a.c((int)this.locX, (int)this.locY, (int)this.locZ) < 4.0D)) {
/* 137 */         this.a = new BlockPosition((int)this.locX + this.random.nextInt(7) - this.random.nextInt(7), (int)this.locY + this.random.nextInt(6) - 2, (int)this.locZ + this.random.nextInt(7) - this.random.nextInt(7));
/*     */       }
/*     */       
/* 140 */       double d1 = this.a.getX() + 0.5D - this.locX;
/* 141 */       double d2 = this.a.getY() + 0.1D - this.locY;
/* 142 */       double d3 = this.a.getZ() + 0.5D - this.locZ;
/*     */       
/* 144 */       this.motX += (Math.signum(d1) * 0.5D - this.motX) * 0.10000000149011612D;
/* 145 */       this.motY += (Math.signum(d2) * 0.699999988079071D - this.motY) * 0.10000000149011612D;
/* 146 */       this.motZ += (Math.signum(d3) * 0.5D - this.motZ) * 0.10000000149011612D;
/*     */       
/* 148 */       float f1 = (float)(MathHelper.b(this.motZ, this.motX) * 180.0D / 3.1415927410125732D) - 90.0F;
/* 149 */       float f2 = MathHelper.g(f1 - this.yaw);
/* 150 */       this.ba = 0.5F;
/* 151 */       this.yaw += f2;
/*     */       
/* 153 */       if ((this.random.nextInt(100) == 0) && (this.world.getType(localBlockPosition2).getBlock().isOccluding())) {
/* 154 */         setAsleep(true);
/*     */       }
/*     */     }
/*     */   }
/*     */   
/*     */   protected boolean s_()
/*     */   {
/* 161 */     return false;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public void e(float paramFloat1, float paramFloat2) {}
/*     */   
/*     */ 
/*     */ 
/*     */   protected void a(double paramDouble, boolean paramBoolean, Block paramBlock, BlockPosition paramBlockPosition) {}
/*     */   
/*     */ 
/*     */ 
/*     */   public boolean aI()
/*     */   {
/* 176 */     return true;
/*     */   }
/*     */   
/*     */   public boolean damageEntity(DamageSource paramDamageSource, float paramFloat)
/*     */   {
/* 181 */     if (isInvulnerable(paramDamageSource)) {
/* 182 */       return false;
/*     */     }
/* 184 */     if ((!this.world.isClientSide) && 
/* 185 */       (isAsleep())) {
/* 186 */       setAsleep(false);
/*     */     }
/*     */     
/*     */ 
/* 190 */     return super.damageEntity(paramDamageSource, paramFloat);
/*     */   }
/*     */   
/*     */   public void a(NBTTagCompound paramNBTTagCompound)
/*     */   {
/* 195 */     super.a(paramNBTTagCompound);
/*     */     
/* 197 */     this.datawatcher.watch(16, Byte.valueOf(paramNBTTagCompound.getByte("BatFlags")));
/*     */   }
/*     */   
/*     */   public void b(NBTTagCompound paramNBTTagCompound)
/*     */   {
/* 202 */     super.b(paramNBTTagCompound);
/*     */     
/* 204 */     paramNBTTagCompound.setByte("BatFlags", this.datawatcher.getByte(16));
/*     */   }
/*     */   
/*     */   public boolean bR()
/*     */   {
/* 209 */     BlockPosition localBlockPosition = new BlockPosition(this.locX, getBoundingBox().b, this.locZ);
/* 210 */     if (localBlockPosition.getY() >= this.world.F()) {
/* 211 */       return false;
/*     */     }
/*     */     
/* 214 */     int i = this.world.getLightLevel(localBlockPosition);
/* 215 */     int j = 4;
/*     */     
/* 217 */     if (a(this.world.Y())) {
/* 218 */       j = 7;
/* 219 */     } else if (this.random.nextBoolean()) {
/* 220 */       return false;
/*     */     }
/*     */     
/* 223 */     if (i > this.random.nextInt(j)) {
/* 224 */       return false;
/*     */     }
/*     */     
/* 227 */     return super.bR();
/*     */   }
/*     */   
/*     */   private boolean a(Calendar paramCalendar) {
/* 231 */     return ((paramCalendar.get(2) + 1 == 10) && (paramCalendar.get(5) >= 20)) || ((paramCalendar.get(2) + 1 == 11) && (paramCalendar.get(5) <= 3));
/*     */   }
/*     */   
/*     */   public float getHeadHeight()
/*     */   {
/* 236 */     return this.length / 2.0F;
/*     */   }
/*     */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_8_R3\EntityBat.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */
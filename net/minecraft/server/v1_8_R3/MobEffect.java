/*     */ package net.minecraft.server.v1_8_R3;
/*     */ 
/*     */ import org.apache.logging.log4j.LogManager;
/*     */ import org.apache.logging.log4j.Logger;
/*     */ 
/*     */ 
/*     */ 
/*     */ public class MobEffect
/*     */ {
/*  10 */   private static final Logger a = ;
/*     */   
/*     */ 
/*     */   private int effectId;
/*     */   
/*     */   private int duration;
/*     */   
/*     */   private int amplification;
/*     */   
/*     */   private boolean splash;
/*     */   
/*     */   private boolean ambient;
/*     */   
/*     */   private boolean particles;
/*     */   
/*     */ 
/*     */   public MobEffect(int paramInt1, int paramInt2)
/*     */   {
/*  28 */     this(paramInt1, paramInt2, 0);
/*     */   }
/*     */   
/*     */   public MobEffect(int paramInt1, int paramInt2, int paramInt3) {
/*  32 */     this(paramInt1, paramInt2, paramInt3, false, true);
/*     */   }
/*     */   
/*     */   public MobEffect(int paramInt1, int paramInt2, int paramInt3, boolean paramBoolean1, boolean paramBoolean2) {
/*  36 */     this.effectId = paramInt1;
/*  37 */     this.duration = paramInt2;
/*  38 */     this.amplification = paramInt3;
/*  39 */     this.ambient = paramBoolean1;
/*  40 */     this.particles = paramBoolean2;
/*     */   }
/*     */   
/*     */   public MobEffect(MobEffect paramMobEffect) {
/*  44 */     this.effectId = paramMobEffect.effectId;
/*  45 */     this.duration = paramMobEffect.duration;
/*  46 */     this.amplification = paramMobEffect.amplification;
/*  47 */     this.ambient = paramMobEffect.ambient;
/*  48 */     this.particles = paramMobEffect.particles;
/*     */   }
/*     */   
/*     */   public void a(MobEffect paramMobEffect) {
/*  52 */     if (this.effectId != paramMobEffect.effectId) {
/*  53 */       a.warn("This method should only be called for matching effects!");
/*     */     }
/*  55 */     if (paramMobEffect.amplification > this.amplification) {
/*  56 */       this.amplification = paramMobEffect.amplification;
/*  57 */       this.duration = paramMobEffect.duration;
/*  58 */     } else if ((paramMobEffect.amplification == this.amplification) && (this.duration < paramMobEffect.duration)) {
/*  59 */       this.duration = paramMobEffect.duration;
/*  60 */     } else if ((!paramMobEffect.ambient) && (this.ambient)) {
/*  61 */       this.ambient = paramMobEffect.ambient;
/*     */     }
/*  63 */     this.particles = paramMobEffect.particles;
/*     */   }
/*     */   
/*     */   public int getEffectId() {
/*  67 */     return this.effectId;
/*     */   }
/*     */   
/*     */   public int getDuration() {
/*  71 */     return this.duration;
/*     */   }
/*     */   
/*     */   public int getAmplifier() {
/*  75 */     return this.amplification;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public void setSplash(boolean paramBoolean)
/*     */   {
/*  83 */     this.splash = paramBoolean;
/*     */   }
/*     */   
/*     */   public boolean isAmbient() {
/*  87 */     return this.ambient;
/*     */   }
/*     */   
/*     */   public boolean isShowParticles() {
/*  91 */     return this.particles;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public boolean tick(EntityLiving paramEntityLiving)
/*     */   {
/* 101 */     if (this.duration > 0) {
/* 102 */       if (MobEffectList.byId[this.effectId].a(this.duration, this.amplification)) {
/* 103 */         b(paramEntityLiving);
/*     */       }
/* 105 */       i();
/*     */     }
/* 107 */     return this.duration > 0;
/*     */   }
/*     */   
/*     */   private int i() {
/* 111 */     return --this.duration;
/*     */   }
/*     */   
/*     */   public void b(EntityLiving paramEntityLiving) {
/* 115 */     if (this.duration > 0) {
/* 116 */       MobEffectList.byId[this.effectId].tick(paramEntityLiving, this.amplification);
/*     */     }
/*     */   }
/*     */   
/*     */   public String g() {
/* 121 */     return MobEffectList.byId[this.effectId].a();
/*     */   }
/*     */   
/*     */   public int hashCode()
/*     */   {
/* 126 */     return this.effectId;
/*     */   }
/*     */   
/*     */   public String toString()
/*     */   {
/* 131 */     String str = "";
/* 132 */     if (getAmplifier() > 0) {
/* 133 */       str = g() + " x " + (getAmplifier() + 1) + ", Duration: " + getDuration();
/*     */     } else {
/* 135 */       str = g() + ", Duration: " + getDuration();
/*     */     }
/* 137 */     if (this.splash) {
/* 138 */       str = str + ", Splash: true";
/*     */     }
/* 140 */     if (!this.particles) {
/* 141 */       str = str + ", Particles: false";
/*     */     }
/* 143 */     if (MobEffectList.byId[this.effectId].j()) {
/* 144 */       return "(" + str + ")";
/*     */     }
/* 146 */     return str;
/*     */   }
/*     */   
/*     */   public boolean equals(Object paramObject)
/*     */   {
/* 151 */     if (!(paramObject instanceof MobEffect)) {
/* 152 */       return false;
/*     */     }
/* 154 */     MobEffect localMobEffect = (MobEffect)paramObject;
/* 155 */     return (this.effectId == localMobEffect.effectId) && (this.amplification == localMobEffect.amplification) && (this.duration == localMobEffect.duration) && (this.splash == localMobEffect.splash) && (this.ambient == localMobEffect.ambient);
/*     */   }
/*     */   
/*     */   public NBTTagCompound a(NBTTagCompound paramNBTTagCompound) {
/* 159 */     paramNBTTagCompound.setByte("Id", (byte)getEffectId());
/* 160 */     paramNBTTagCompound.setByte("Amplifier", (byte)getAmplifier());
/* 161 */     paramNBTTagCompound.setInt("Duration", getDuration());
/* 162 */     paramNBTTagCompound.setBoolean("Ambient", isAmbient());
/* 163 */     paramNBTTagCompound.setBoolean("ShowParticles", isShowParticles());
/* 164 */     return paramNBTTagCompound;
/*     */   }
/*     */   
/*     */   public static MobEffect b(NBTTagCompound paramNBTTagCompound) {
/* 168 */     int i = paramNBTTagCompound.getByte("Id");
/* 169 */     if ((i < 0) || (i >= MobEffectList.byId.length) || (MobEffectList.byId[i] == null)) {
/* 170 */       return null;
/*     */     }
/* 172 */     int j = paramNBTTagCompound.getByte("Amplifier");
/* 173 */     int k = paramNBTTagCompound.getInt("Duration");
/* 174 */     boolean bool1 = paramNBTTagCompound.getBoolean("Ambient");
/* 175 */     boolean bool2 = true;
/* 176 */     if (paramNBTTagCompound.hasKeyOfType("ShowParticles", 1)) {
/* 177 */       bool2 = paramNBTTagCompound.getBoolean("ShowParticles");
/*     */     }
/* 179 */     return new MobEffect(i, k, j, bool1, bool2);
/*     */   }
/*     */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_8_R3\MobEffect.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */
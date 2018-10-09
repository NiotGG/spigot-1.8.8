/*     */ package net.minecraft.server.v1_8_R3;
/*     */ 
/*     */ import java.util.Random;
/*     */ import java.util.UUID;
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
/*     */ public abstract class EntityTameableAnimal
/*     */   extends EntityAnimal
/*     */   implements EntityOwnable
/*     */ {
/*  21 */   protected PathfinderGoalSit bm = new PathfinderGoalSit(this);
/*     */   
/*     */   public EntityTameableAnimal(World paramWorld) {
/*  24 */     super(paramWorld);
/*  25 */     cm();
/*     */   }
/*     */   
/*     */   protected void h()
/*     */   {
/*  30 */     super.h();
/*  31 */     this.datawatcher.a(16, Byte.valueOf((byte)0));
/*  32 */     this.datawatcher.a(17, "");
/*     */   }
/*     */   
/*     */   public void b(NBTTagCompound paramNBTTagCompound)
/*     */   {
/*  37 */     super.b(paramNBTTagCompound);
/*  38 */     if (getOwnerUUID() == null) {
/*  39 */       paramNBTTagCompound.setString("OwnerUUID", "");
/*     */     } else {
/*  41 */       paramNBTTagCompound.setString("OwnerUUID", getOwnerUUID());
/*     */     }
/*  43 */     paramNBTTagCompound.setBoolean("Sitting", isSitting());
/*     */   }
/*     */   
/*     */   public void a(NBTTagCompound paramNBTTagCompound)
/*     */   {
/*  48 */     super.a(paramNBTTagCompound);
/*  49 */     String str1 = "";
/*  50 */     if (paramNBTTagCompound.hasKeyOfType("OwnerUUID", 8)) {
/*  51 */       str1 = paramNBTTagCompound.getString("OwnerUUID");
/*     */     } else {
/*  53 */       String str2 = paramNBTTagCompound.getString("Owner");
/*  54 */       str1 = NameReferencingFileConverter.a(str2);
/*     */     }
/*  56 */     if (str1.length() > 0) {
/*  57 */       setOwnerUUID(str1);
/*  58 */       setTamed(true);
/*     */     }
/*  60 */     this.bm.setSitting(paramNBTTagCompound.getBoolean("Sitting"));
/*  61 */     setSitting(paramNBTTagCompound.getBoolean("Sitting"));
/*     */   }
/*     */   
/*     */   protected void l(boolean paramBoolean) {
/*  65 */     EnumParticle localEnumParticle = EnumParticle.HEART;
/*  66 */     if (!paramBoolean) {
/*  67 */       localEnumParticle = EnumParticle.SMOKE_NORMAL;
/*     */     }
/*  69 */     for (int i = 0; i < 7; i++) {
/*  70 */       double d1 = this.random.nextGaussian() * 0.02D;
/*  71 */       double d2 = this.random.nextGaussian() * 0.02D;
/*  72 */       double d3 = this.random.nextGaussian() * 0.02D;
/*  73 */       this.world.addParticle(localEnumParticle, this.locX + this.random.nextFloat() * this.width * 2.0F - this.width, this.locY + 0.5D + this.random.nextFloat() * this.length, this.locZ + this.random.nextFloat() * this.width * 2.0F - this.width, d1, d2, d3, new int[0]);
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
/*     */   public boolean isTamed()
/*     */   {
/*  89 */     return (this.datawatcher.getByte(16) & 0x4) != 0;
/*     */   }
/*     */   
/*     */   public void setTamed(boolean paramBoolean) {
/*  93 */     int i = this.datawatcher.getByte(16);
/*  94 */     if (paramBoolean) {
/*  95 */       this.datawatcher.watch(16, Byte.valueOf((byte)(i | 0x4)));
/*     */     } else {
/*  97 */       this.datawatcher.watch(16, Byte.valueOf((byte)(i & 0xFFFFFFFB)));
/*     */     }
/*     */     
/* 100 */     cm();
/*     */   }
/*     */   
/*     */   protected void cm() {}
/*     */   
/*     */   public boolean isSitting()
/*     */   {
/* 107 */     return (this.datawatcher.getByte(16) & 0x1) != 0;
/*     */   }
/*     */   
/*     */   public void setSitting(boolean paramBoolean) {
/* 111 */     int i = this.datawatcher.getByte(16);
/* 112 */     if (paramBoolean) {
/* 113 */       this.datawatcher.watch(16, Byte.valueOf((byte)(i | 0x1)));
/*     */     } else {
/* 115 */       this.datawatcher.watch(16, Byte.valueOf((byte)(i & 0xFFFFFFFE)));
/*     */     }
/*     */   }
/*     */   
/*     */   public String getOwnerUUID()
/*     */   {
/* 121 */     return this.datawatcher.getString(17);
/*     */   }
/*     */   
/*     */   public void setOwnerUUID(String paramString) {
/* 125 */     this.datawatcher.watch(17, paramString);
/*     */   }
/*     */   
/*     */   public EntityLiving getOwner()
/*     */   {
/*     */     try {
/* 131 */       UUID localUUID = UUID.fromString(getOwnerUUID());
/* 132 */       if (localUUID == null) {
/* 133 */         return null;
/*     */       }
/* 135 */       return this.world.b(localUUID);
/*     */     } catch (IllegalArgumentException localIllegalArgumentException) {}
/* 137 */     return null;
/*     */   }
/*     */   
/*     */   public boolean e(EntityLiving paramEntityLiving)
/*     */   {
/* 142 */     return paramEntityLiving == getOwner();
/*     */   }
/*     */   
/*     */   public PathfinderGoalSit getGoalSit() {
/* 146 */     return this.bm;
/*     */   }
/*     */   
/*     */   public boolean a(EntityLiving paramEntityLiving1, EntityLiving paramEntityLiving2) {
/* 150 */     return true;
/*     */   }
/*     */   
/*     */   public ScoreboardTeamBase getScoreboardTeam()
/*     */   {
/* 155 */     if (isTamed()) {
/* 156 */       EntityLiving localEntityLiving = getOwner();
/* 157 */       if (localEntityLiving != null) {
/* 158 */         return localEntityLiving.getScoreboardTeam();
/*     */       }
/*     */     }
/* 161 */     return super.getScoreboardTeam();
/*     */   }
/*     */   
/*     */   public boolean c(EntityLiving paramEntityLiving)
/*     */   {
/* 166 */     if (isTamed()) {
/* 167 */       EntityLiving localEntityLiving = getOwner();
/* 168 */       if (paramEntityLiving == localEntityLiving) {
/* 169 */         return true;
/*     */       }
/* 171 */       if (localEntityLiving != null) {
/* 172 */         return localEntityLiving.c(paramEntityLiving);
/*     */       }
/*     */     }
/* 175 */     return super.c(paramEntityLiving);
/*     */   }
/*     */   
/*     */   public void die(DamageSource paramDamageSource)
/*     */   {
/* 180 */     if ((!this.world.isClientSide) && (this.world.getGameRules().getBoolean("showDeathMessages")) && (hasCustomName()) && 
/* 181 */       ((getOwner() instanceof EntityPlayer))) {
/* 182 */       ((EntityPlayer)getOwner()).sendMessage(bs().b());
/*     */     }
/*     */     
/* 185 */     super.die(paramDamageSource);
/*     */   }
/*     */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_8_R3\EntityTameableAnimal.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */
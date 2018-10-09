/*     */ package net.minecraft.server.v1_8_R3;
/*     */ 
/*     */ import org.bukkit.event.entity.FoodLevelChangeEvent;
/*     */ 
/*   5 */ public class FoodMetaData { public int foodLevel = 20;
/*   6 */   public float saturationLevel = 5.0F;
/*     */   public float exhaustionLevel;
/*     */   private int foodTickTimer;
/*     */   private EntityHuman entityhuman;
/*  10 */   private int e = 20;
/*     */   
/*  12 */   public FoodMetaData() { throw new AssertionError("Whoopsie, we missed the bukkit."); }
/*     */   
/*     */   public FoodMetaData(EntityHuman entityhuman)
/*     */   {
/*  16 */     org.apache.commons.lang.Validate.notNull(entityhuman);
/*  17 */     this.entityhuman = entityhuman;
/*     */   }
/*     */   
/*     */   public void eat(int i, float f)
/*     */   {
/*  22 */     this.foodLevel = Math.min(i + this.foodLevel, 20);
/*  23 */     this.saturationLevel = Math.min(this.saturationLevel + i * f * 2.0F, this.foodLevel);
/*     */   }
/*     */   
/*     */   public void a(ItemFood itemfood, ItemStack itemstack)
/*     */   {
/*  28 */     int oldFoodLevel = this.foodLevel;
/*     */     
/*  30 */     FoodLevelChangeEvent event = org.bukkit.craftbukkit.v1_8_R3.event.CraftEventFactory.callFoodLevelChangeEvent(this.entityhuman, itemfood.getNutrition(itemstack) + oldFoodLevel);
/*     */     
/*  32 */     if (!event.isCancelled()) {
/*  33 */       eat(event.getFoodLevel() - oldFoodLevel, itemfood.getSaturationModifier(itemstack));
/*     */     }
/*     */     
/*  36 */     ((EntityPlayer)this.entityhuman).playerConnection.sendPacket(new PacketPlayOutUpdateHealth(((EntityPlayer)this.entityhuman).getBukkitEntity().getScaledHealth(), this.entityhuman.getFoodData().foodLevel, this.entityhuman.getFoodData().saturationLevel));
/*     */   }
/*     */   
/*     */   public void a(EntityHuman entityhuman)
/*     */   {
/*  41 */     EnumDifficulty enumdifficulty = entityhuman.world.getDifficulty();
/*     */     
/*  43 */     this.e = this.foodLevel;
/*  44 */     if (this.exhaustionLevel > 4.0F) {
/*  45 */       this.exhaustionLevel -= 4.0F;
/*  46 */       if (this.saturationLevel > 0.0F) {
/*  47 */         this.saturationLevel = Math.max(this.saturationLevel - 1.0F, 0.0F);
/*  48 */       } else if (enumdifficulty != EnumDifficulty.PEACEFUL)
/*     */       {
/*  50 */         FoodLevelChangeEvent event = org.bukkit.craftbukkit.v1_8_R3.event.CraftEventFactory.callFoodLevelChangeEvent(entityhuman, Math.max(this.foodLevel - 1, 0));
/*     */         
/*  52 */         if (!event.isCancelled()) {
/*  53 */           this.foodLevel = event.getFoodLevel();
/*     */         }
/*     */         
/*  56 */         ((EntityPlayer)entityhuman).playerConnection.sendPacket(new PacketPlayOutUpdateHealth(((EntityPlayer)entityhuman).getBukkitEntity().getScaledHealth(), this.foodLevel, this.saturationLevel));
/*     */       }
/*     */     }
/*     */     
/*     */ 
/*  61 */     if ((entityhuman.world.getGameRules().getBoolean("naturalRegeneration")) && (this.foodLevel >= 18) && (entityhuman.cm())) {
/*  62 */       this.foodTickTimer += 1;
/*  63 */       if (this.foodTickTimer >= 80)
/*     */       {
/*  65 */         entityhuman.heal(1.0F, org.bukkit.event.entity.EntityRegainHealthEvent.RegainReason.SATIATED);
/*  66 */         a(entityhuman.world.spigotConfig.regenExhaustion);
/*  67 */         this.foodTickTimer = 0;
/*     */       }
/*  69 */     } else if (this.foodLevel <= 0) {
/*  70 */       this.foodTickTimer += 1;
/*  71 */       if (this.foodTickTimer >= 80) {
/*  72 */         if ((entityhuman.getHealth() > 10.0F) || (enumdifficulty == EnumDifficulty.HARD) || ((entityhuman.getHealth() > 1.0F) && (enumdifficulty == EnumDifficulty.NORMAL))) {
/*  73 */           entityhuman.damageEntity(DamageSource.STARVE, 1.0F);
/*     */         }
/*     */         
/*  76 */         this.foodTickTimer = 0;
/*     */       }
/*     */     } else {
/*  79 */       this.foodTickTimer = 0;
/*     */     }
/*     */   }
/*     */   
/*     */   public void a(NBTTagCompound nbttagcompound)
/*     */   {
/*  85 */     if (nbttagcompound.hasKeyOfType("foodLevel", 99)) {
/*  86 */       this.foodLevel = nbttagcompound.getInt("foodLevel");
/*  87 */       this.foodTickTimer = nbttagcompound.getInt("foodTickTimer");
/*  88 */       this.saturationLevel = nbttagcompound.getFloat("foodSaturationLevel");
/*  89 */       this.exhaustionLevel = nbttagcompound.getFloat("foodExhaustionLevel");
/*     */     }
/*     */   }
/*     */   
/*     */   public void b(NBTTagCompound nbttagcompound)
/*     */   {
/*  95 */     nbttagcompound.setInt("foodLevel", this.foodLevel);
/*  96 */     nbttagcompound.setInt("foodTickTimer", this.foodTickTimer);
/*  97 */     nbttagcompound.setFloat("foodSaturationLevel", this.saturationLevel);
/*  98 */     nbttagcompound.setFloat("foodExhaustionLevel", this.exhaustionLevel);
/*     */   }
/*     */   
/*     */   public int getFoodLevel() {
/* 102 */     return this.foodLevel;
/*     */   }
/*     */   
/*     */   public boolean c() {
/* 106 */     return this.foodLevel < 20;
/*     */   }
/*     */   
/*     */   public void a(float f) {
/* 110 */     this.exhaustionLevel = Math.min(this.exhaustionLevel + f, 40.0F);
/*     */   }
/*     */   
/*     */   public float getSaturationLevel() {
/* 114 */     return this.saturationLevel;
/*     */   }
/*     */   
/*     */   public void a(int i) {
/* 118 */     this.foodLevel = i;
/*     */   }
/*     */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_8_R3\FoodMetaData.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */
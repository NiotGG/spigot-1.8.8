/*    */ package org.bukkit.potion;
/*    */ 
/*    */ public enum PotionType {
/*  4 */   WATER(0, null, 0), 
/*  5 */   REGEN(1, PotionEffectType.REGENERATION, 2), 
/*  6 */   SPEED(2, PotionEffectType.SPEED, 2), 
/*  7 */   FIRE_RESISTANCE(3, PotionEffectType.FIRE_RESISTANCE, 1), 
/*  8 */   POISON(4, PotionEffectType.POISON, 2), 
/*  9 */   INSTANT_HEAL(5, PotionEffectType.HEAL, 2), 
/* 10 */   NIGHT_VISION(6, PotionEffectType.NIGHT_VISION, 1), 
/* 11 */   WEAKNESS(8, PotionEffectType.WEAKNESS, 1), 
/* 12 */   STRENGTH(9, PotionEffectType.INCREASE_DAMAGE, 2), 
/* 13 */   SLOWNESS(10, PotionEffectType.SLOW, 1), 
/* 14 */   JUMP(11, PotionEffectType.JUMP, 2), 
/* 15 */   INSTANT_DAMAGE(12, PotionEffectType.HARM, 2), 
/* 16 */   WATER_BREATHING(13, PotionEffectType.WATER_BREATHING, 1), 
/* 17 */   INVISIBILITY(14, PotionEffectType.INVISIBILITY, 1);
/*    */   
/*    */   private final int damageValue;
/*    */   private final int maxLevel;
/*    */   private final PotionEffectType effect;
/*    */   
/*    */   private PotionType(int damageValue, PotionEffectType effect, int maxLevel) {
/* 24 */     this.damageValue = damageValue;
/* 25 */     this.effect = effect;
/* 26 */     this.maxLevel = maxLevel;
/*    */   }
/*    */   
/*    */   public PotionEffectType getEffectType() {
/* 30 */     return this.effect;
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   @Deprecated
/*    */   public int getDamageValue()
/*    */   {
/* 40 */     return this.damageValue;
/*    */   }
/*    */   
/*    */   public int getMaxLevel() {
/* 44 */     return this.maxLevel;
/*    */   }
/*    */   
/*    */   public boolean isInstant() {
/* 48 */     return this.effect == null ? true : this.effect.isInstant();
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */   @Deprecated
/*    */   public static PotionType getByDamageValue(int damage)
/*    */   {
/*    */     PotionType[] arrayOfPotionType;
/*    */     
/*    */ 
/* 59 */     int i = (arrayOfPotionType = values()).length; for (int j = 0; j < i; j++) { PotionType type = arrayOfPotionType[j];
/* 60 */       if (type.damageValue == damage)
/* 61 */         return type;
/*    */     }
/* 63 */     return null;
/*    */   }
/*    */   
/*    */   public static PotionType getByEffect(PotionEffectType effectType) {
/* 67 */     if (effectType == null)
/* 68 */       return WATER;
/* 69 */     PotionType[] arrayOfPotionType; int i = (arrayOfPotionType = values()).length; for (int j = 0; j < i; j++) { PotionType type = arrayOfPotionType[j];
/* 70 */       if (effectType.equals(type.effect))
/* 71 */         return type;
/*    */     }
/* 73 */     return null;
/*    */   }
/*    */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\org\bukkit\potion\PotionType.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */
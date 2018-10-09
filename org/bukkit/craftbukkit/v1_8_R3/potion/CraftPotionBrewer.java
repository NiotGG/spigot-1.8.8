/*    */ package org.bukkit.craftbukkit.v1_8_R3.potion;
/*    */ 
/*    */ import com.google.common.collect.Maps;
/*    */ import java.util.ArrayList;
/*    */ import java.util.Collection;
/*    */ import java.util.List;
/*    */ import java.util.Map;
/*    */ import net.minecraft.server.v1_8_R3.MobEffect;
/*    */ import org.bukkit.potion.PotionEffect;
/*    */ import org.bukkit.potion.PotionEffectType;
/*    */ 
/*    */ 
/*    */ 
/*    */ public class CraftPotionBrewer
/*    */   implements org.bukkit.potion.PotionBrewer
/*    */ {
/* 17 */   private static final Map<Integer, Collection<PotionEffect>> cache = ;
/*    */   
/*    */   public Collection<PotionEffect> getEffectsFromDamage(int damage) {
/* 20 */     if (cache.containsKey(Integer.valueOf(damage))) {
/* 21 */       return (Collection)cache.get(Integer.valueOf(damage));
/*    */     }
/* 23 */     List<?> mcEffects = net.minecraft.server.v1_8_R3.PotionBrewer.getEffects(damage, false);
/* 24 */     List<PotionEffect> effects = new ArrayList();
/* 25 */     if (mcEffects == null) {
/* 26 */       return effects;
/*    */     }
/* 28 */     for (Object raw : mcEffects) {
/* 29 */       if ((raw != null) && ((raw instanceof MobEffect)))
/*    */       {
/* 31 */         MobEffect mcEffect = (MobEffect)raw;
/* 32 */         PotionEffect effect = new PotionEffect(PotionEffectType.getById(mcEffect.getEffectId()), 
/* 33 */           mcEffect.getDuration(), mcEffect.getAmplifier());
/*    */         
/* 35 */         effects.add(effect);
/*    */       }
/*    */     }
/* 38 */     cache.put(Integer.valueOf(damage), effects);
/*    */     
/* 40 */     return effects;
/*    */   }
/*    */   
/*    */   public PotionEffect createEffect(PotionEffectType potion, int duration, int amplifier) {
/* 44 */     return new PotionEffect(potion, potion.isInstant() ? 1 : (int)(duration * potion.getDurationModifier()), 
/* 45 */       amplifier);
/*    */   }
/*    */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\org\bukkit\craftbukkit\v1_8_R3\potion\CraftPotionBrewer.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */
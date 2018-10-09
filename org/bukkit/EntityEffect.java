/*     */ package org.bukkit;
/*     */ 
/*     */ import com.google.common.collect.Maps;
/*     */ import java.util.Map;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public enum EntityEffect
/*     */ {
/*  12 */   HURT(
/*     */   
/*     */ 
/*  15 */     2), 
/*     */   
/*  17 */   DEATH(
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*  22 */     3), 
/*     */   
/*  24 */   WOLF_SMOKE(
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*  29 */     6), 
/*     */   
/*  31 */   WOLF_HEARTS(
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*  36 */     7), 
/*     */   
/*  38 */   WOLF_SHAKE(
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*  43 */     8), 
/*     */   
/*  45 */   SHEEP_EAT(
/*     */   
/*     */ 
/*  48 */     10), 
/*     */   
/*  50 */   IRON_GOLEM_ROSE(
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*  55 */     11), 
/*     */   
/*  57 */   VILLAGER_HEART(
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*  62 */     12), 
/*     */   
/*  64 */   VILLAGER_ANGRY(
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*  69 */     13), 
/*     */   
/*  71 */   VILLAGER_HAPPY(
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*  76 */     14), 
/*     */   
/*  78 */   WITCH_MAGIC(
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*  83 */     15), 
/*     */   
/*  85 */   ZOMBIE_TRANSFORM(
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*  90 */     16), 
/*     */   
/*  92 */   FIREWORK_EXPLODE(
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*  97 */     17);
/*     */   
/*     */   private final byte data;
/*     */   private static final Map<Byte, EntityEffect> BY_DATA;
/*     */   
/*     */   private EntityEffect(int data) {
/* 103 */     this.data = ((byte)data);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   @Deprecated
/*     */   public byte getData()
/*     */   {
/* 114 */     return this.data;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   @Deprecated
/*     */   public static EntityEffect getByData(byte data)
/*     */   {
/* 127 */     return (EntityEffect)BY_DATA.get(Byte.valueOf(data));
/*     */   }
/*     */   
/*     */   static
/*     */   {
/* 100 */     BY_DATA = Maps.newHashMap();
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
/*     */ 
/*     */     EntityEffect[] arrayOfEntityEffect;
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
/*     */ 
/* 132 */     int i = (arrayOfEntityEffect = values()).length; for (int j = 0; j < i; j++) { EntityEffect entityEffect = arrayOfEntityEffect[j];
/* 133 */       BY_DATA.put(Byte.valueOf(entityEffect.data), entityEffect);
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\org\bukkit\EntityEffect.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */
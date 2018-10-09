/*     */ package org.bukkit.potion;
/*     */ 
/*     */ import com.google.common.collect.ImmutableMap;
/*     */ import java.util.Map;
/*     */ import java.util.NoSuchElementException;
/*     */ import org.apache.commons.lang.Validate;
/*     */ import org.bukkit.configuration.serialization.ConfigurationSerializable;
/*     */ import org.bukkit.configuration.serialization.SerializableAs;
/*     */ import org.bukkit.entity.LivingEntity;
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
/*     */ @SerializableAs("PotionEffect")
/*     */ public class PotionEffect
/*     */   implements ConfigurationSerializable
/*     */ {
/*     */   private static final String AMPLIFIER = "amplifier";
/*     */   private static final String DURATION = "duration";
/*     */   private static final String TYPE = "effect";
/*     */   private static final String AMBIENT = "ambient";
/*     */   private static final String PARTICLES = "has-particles";
/*     */   private final int amplifier;
/*     */   private final int duration;
/*     */   private final PotionEffectType type;
/*     */   private final boolean ambient;
/*     */   private final boolean particles;
/*     */   
/*     */   public PotionEffect(PotionEffectType type, int duration, int amplifier, boolean ambient, boolean particles)
/*     */   {
/*  42 */     Validate.notNull(type, "effect type cannot be null");
/*  43 */     this.type = type;
/*  44 */     this.duration = duration;
/*  45 */     this.amplifier = amplifier;
/*  46 */     this.ambient = ambient;
/*  47 */     this.particles = particles;
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
/*     */   public PotionEffect(PotionEffectType type, int duration, int amplifier, boolean ambient)
/*     */   {
/*  60 */     this(type, duration, amplifier, ambient, true);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public PotionEffect(PotionEffectType type, int duration, int amplifier)
/*     */   {
/*  72 */     this(type, duration, amplifier, true);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public PotionEffect(Map<String, Object> map)
/*     */   {
/*  81 */     this(getEffectType(map), getInt(map, "duration"), getInt(map, "amplifier"), getBool(map, "ambient", false), getBool(map, "has-particles", true));
/*     */   }
/*     */   
/*     */   private static PotionEffectType getEffectType(Map<?, ?> map) {
/*  85 */     int type = getInt(map, "effect");
/*  86 */     PotionEffectType effect = PotionEffectType.getById(type);
/*  87 */     if (effect != null) {
/*  88 */       return effect;
/*     */     }
/*  90 */     throw new NoSuchElementException(map + " does not contain " + "effect");
/*     */   }
/*     */   
/*     */   private static int getInt(Map<?, ?> map, Object key) {
/*  94 */     Object num = map.get(key);
/*  95 */     if ((num instanceof Integer)) {
/*  96 */       return ((Integer)num).intValue();
/*     */     }
/*  98 */     throw new NoSuchElementException(map + " does not contain " + key);
/*     */   }
/*     */   
/*     */   private static boolean getBool(Map<?, ?> map, Object key, boolean def) {
/* 102 */     Object bool = map.get(key);
/* 103 */     if ((bool instanceof Boolean)) {
/* 104 */       return ((Boolean)bool).booleanValue();
/*     */     }
/* 106 */     return def;
/*     */   }
/*     */   
/*     */   public Map<String, Object> serialize() {
/* 110 */     return ImmutableMap.of(
/* 111 */       "effect", Integer.valueOf(this.type.getId()), 
/* 112 */       "duration", Integer.valueOf(this.duration), 
/* 113 */       "amplifier", Integer.valueOf(this.amplifier), 
/* 114 */       "ambient", Boolean.valueOf(this.ambient), 
/* 115 */       "has-particles", Boolean.valueOf(this.particles));
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
/*     */   public boolean apply(LivingEntity entity)
/*     */   {
/* 128 */     return entity.addPotionEffect(this);
/*     */   }
/*     */   
/*     */   public boolean equals(Object obj)
/*     */   {
/* 133 */     if (this == obj) {
/* 134 */       return true;
/*     */     }
/* 136 */     if (!(obj instanceof PotionEffect)) {
/* 137 */       return false;
/*     */     }
/* 139 */     PotionEffect that = (PotionEffect)obj;
/* 140 */     return (this.type.equals(that.type)) && (this.ambient == that.ambient) && (this.amplifier == that.amplifier) && (this.duration == that.duration) && (this.particles == that.particles);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public int getAmplifier()
/*     */   {
/* 151 */     return this.amplifier;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public int getDuration()
/*     */   {
/* 161 */     return this.duration;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public PotionEffectType getType()
/*     */   {
/* 170 */     return this.type;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public boolean isAmbient()
/*     */   {
/* 179 */     return this.ambient;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public boolean hasParticles()
/*     */   {
/* 186 */     return this.particles;
/*     */   }
/*     */   
/*     */   public int hashCode()
/*     */   {
/* 191 */     int hash = 1;
/* 192 */     hash = hash * 31 + this.type.hashCode();
/* 193 */     hash = hash * 31 + this.amplifier;
/* 194 */     hash = hash * 31 + this.duration;
/* 195 */     hash ^= 572662306 >> (this.ambient ? 1 : -1);
/* 196 */     hash ^= 572662306 >> (this.particles ? 1 : -1);
/* 197 */     return hash;
/*     */   }
/*     */   
/*     */   public String toString()
/*     */   {
/* 202 */     return this.type.getName() + (this.ambient ? ":(" : ":") + this.duration + "t-x" + this.amplifier + (this.ambient ? ")" : "");
/*     */   }
/*     */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\org\bukkit\potion\PotionEffect.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */
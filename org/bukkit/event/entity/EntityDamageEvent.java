/*     */ package org.bukkit.event.entity;
/*     */ 
/*     */ import com.google.common.base.Function;
/*     */ import com.google.common.base.Functions;
/*     */ import com.google.common.collect.ImmutableMap;
/*     */ import java.util.EnumMap;
/*     */ import java.util.Map;
/*     */ import java.util.Set;
/*     */ import org.apache.commons.lang.Validate;
/*     */ import org.bukkit.entity.Entity;
/*     */ import org.bukkit.event.Cancellable;
/*     */ import org.bukkit.event.HandlerList;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class EntityDamageEvent
/*     */   extends EntityEvent
/*     */   implements Cancellable
/*     */ {
/*  21 */   private static final HandlerList handlers = new HandlerList();
/*  22 */   private static final DamageModifier[] MODIFIERS = DamageModifier.values();
/*  23 */   private static final Function<? super Double, Double> ZERO = Functions.constant(Double.valueOf(-0.0D));
/*     */   private final Map<DamageModifier, Double> modifiers;
/*     */   private final Map<DamageModifier, ? extends Function<? super Double, Double>> modifierFunctions;
/*     */   private final Map<DamageModifier, Double> originals;
/*     */   private boolean cancelled;
/*     */   private final DamageCause cause;
/*     */   
/*     */   @Deprecated
/*     */   public EntityDamageEvent(Entity damagee, DamageCause cause, int damage) {
/*  32 */     this(damagee, cause, damage);
/*     */   }
/*     */   
/*     */   @Deprecated
/*     */   public EntityDamageEvent(Entity damagee, DamageCause cause, double damage) {
/*  37 */     this(damagee, cause, new EnumMap(ImmutableMap.of(DamageModifier.BASE, Double.valueOf(damage))), new EnumMap(ImmutableMap.of(DamageModifier.BASE, ZERO)));
/*     */   }
/*     */   
/*     */   public EntityDamageEvent(Entity damagee, DamageCause cause, Map<DamageModifier, Double> modifiers, Map<DamageModifier, ? extends Function<? super Double, Double>> modifierFunctions) {
/*  41 */     super(damagee);
/*  42 */     Validate.isTrue(modifiers.containsKey(DamageModifier.BASE), "BASE DamageModifier missing");
/*  43 */     Validate.isTrue(!modifiers.containsKey(null), "Cannot have null DamageModifier");
/*  44 */     Validate.noNullElements(modifiers.values(), "Cannot have null modifier values");
/*  45 */     Validate.isTrue(modifiers.keySet().equals(modifierFunctions.keySet()), "Must have a modifier function for each DamageModifier");
/*  46 */     Validate.noNullElements(modifierFunctions.values(), "Cannot have null modifier function");
/*  47 */     this.originals = new EnumMap(modifiers);
/*  48 */     this.cause = cause;
/*  49 */     this.modifiers = modifiers;
/*  50 */     this.modifierFunctions = modifierFunctions;
/*     */   }
/*     */   
/*     */   public boolean isCancelled() {
/*  54 */     return this.cancelled;
/*     */   }
/*     */   
/*     */   public void setCancelled(boolean cancel) {
/*  58 */     this.cancelled = cancel;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public double getOriginalDamage(DamageModifier type)
/*     */     throws IllegalArgumentException
/*     */   {
/*  70 */     Double damage = (Double)this.originals.get(type);
/*  71 */     if (damage != null) {
/*  72 */       return damage.doubleValue();
/*     */     }
/*  74 */     if (type == null) {
/*  75 */       throw new IllegalArgumentException("Cannot have null DamageModifier");
/*     */     }
/*  77 */     return 0.0D;
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
/*     */   public void setDamage(DamageModifier type, double damage)
/*     */     throws IllegalArgumentException, UnsupportedOperationException
/*     */   {
/*  92 */     if (!this.modifiers.containsKey(type)) {
/*  93 */       throw (type == null ? new IllegalArgumentException("Cannot have null DamageModifier") : new UnsupportedOperationException(type + " is not applicable to " + getEntity()));
/*     */     }
/*  95 */     this.modifiers.put(type, Double.valueOf(damage));
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public double getDamage(DamageModifier type)
/*     */     throws IllegalArgumentException
/*     */   {
/* 107 */     Validate.notNull(type, "Cannot have null DamageModifier");
/* 108 */     Double damage = (Double)this.modifiers.get(type);
/* 109 */     return damage == null ? 0.0D : damage.doubleValue();
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
/*     */   public boolean isApplicable(DamageModifier type)
/*     */     throws IllegalArgumentException
/*     */   {
/* 124 */     Validate.notNull(type, "Cannot have null DamageModifier");
/* 125 */     return this.modifiers.containsKey(type);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public double getDamage()
/*     */   {
/* 135 */     return getDamage(DamageModifier.BASE);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public final double getFinalDamage()
/*     */   {
/* 145 */     double damage = 0.0D;
/* 146 */     DamageModifier[] arrayOfDamageModifier; int i = (arrayOfDamageModifier = MODIFIERS).length; for (int j = 0; j < i; j++) { DamageModifier modifier = arrayOfDamageModifier[j];
/* 147 */       damage += getDamage(modifier);
/*     */     }
/* 149 */     return damage;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public void setDamage(double damage)
/*     */   {
/* 175 */     double remaining = damage;
/* 176 */     double oldRemaining = getDamage(DamageModifier.BASE);
/* 177 */     DamageModifier[] arrayOfDamageModifier; int i = (arrayOfDamageModifier = MODIFIERS).length; for (int j = 0; j < i; j++) { DamageModifier modifier = arrayOfDamageModifier[j];
/* 178 */       if (isApplicable(modifier))
/*     */       {
/*     */ 
/*     */ 
/* 182 */         Function<? super Double, Double> modifierFunction = (Function)this.modifierFunctions.get(modifier);
/* 183 */         double newVanilla = ((Double)modifierFunction.apply(Double.valueOf(remaining))).doubleValue();
/* 184 */         double oldVanilla = ((Double)modifierFunction.apply(Double.valueOf(oldRemaining))).doubleValue();
/* 185 */         double difference = oldVanilla - newVanilla;
/*     */         
/*     */ 
/* 188 */         double old = getDamage(modifier);
/* 189 */         if (old > 0.0D) {
/* 190 */           setDamage(modifier, Math.max(0.0D, old - difference));
/*     */         } else {
/* 192 */           setDamage(modifier, Math.min(0.0D, old - difference));
/*     */         }
/* 194 */         remaining += newVanilla;
/* 195 */         oldRemaining += oldVanilla;
/*     */       }
/*     */     }
/* 198 */     setDamage(DamageModifier.BASE, damage);
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public DamageCause getCause()
/*     */   {
/* 219 */     return this.cause;
/*     */   }
/*     */   
/*     */   public HandlerList getHandlers()
/*     */   {
/* 224 */     return handlers;
/*     */   }
/*     */   
/*     */   public static HandlerList getHandlerList() {
/* 228 */     return handlers;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public static enum DamageModifier
/*     */   {
/* 235 */     BASE, 
/*     */     
/*     */ 
/*     */ 
/*     */ 
/* 240 */     HARD_HAT, 
/*     */     
/*     */ 
/*     */ 
/*     */ 
/* 245 */     BLOCKING, 
/*     */     
/*     */ 
/*     */ 
/*     */ 
/* 250 */     ARMOR, 
/*     */     
/*     */ 
/*     */ 
/* 254 */     RESISTANCE, 
/*     */     
/*     */ 
/*     */ 
/* 258 */     MAGIC, 
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
/* 269 */     ABSORPTION;
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
/*     */   public static enum DamageCause
/*     */   {
/* 282 */     CONTACT, 
/*     */     
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/* 288 */     ENTITY_ATTACK, 
/*     */     
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/* 294 */     PROJECTILE, 
/*     */     
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/* 300 */     SUFFOCATION, 
/*     */     
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/* 306 */     FALL, 
/*     */     
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/* 312 */     FIRE, 
/*     */     
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/* 318 */     FIRE_TICK, 
/*     */     
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/* 324 */     MELTING, 
/*     */     
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/* 330 */     LAVA, 
/*     */     
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/* 336 */     DROWNING, 
/*     */     
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/* 342 */     BLOCK_EXPLOSION, 
/*     */     
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/* 348 */     ENTITY_EXPLOSION, 
/*     */     
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/* 355 */     VOID, 
/*     */     
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/* 361 */     LIGHTNING, 
/*     */     
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/* 367 */     SUICIDE, 
/*     */     
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/* 373 */     STARVATION, 
/*     */     
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/* 379 */     POISON, 
/*     */     
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/* 385 */     MAGIC, 
/*     */     
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/* 391 */     WITHER, 
/*     */     
/*     */ 
/*     */ 
/* 395 */     FALLING_BLOCK, 
/*     */     
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/* 403 */     THORNS, 
/*     */     
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/* 410 */     CUSTOM;
/*     */   }
/*     */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\org\bukkit\event\entity\EntityDamageEvent.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */
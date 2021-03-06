/*     */ package org.bukkit.craftbukkit.v1_8_R3.inventory;
/*     */ 
/*     */ import com.google.common.collect.ImmutableList;
/*     */ import com.google.common.collect.ImmutableMap.Builder;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Collection;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import net.minecraft.server.v1_8_R3.NBTTagCompound;
/*     */ import net.minecraft.server.v1_8_R3.NBTTagList;
/*     */ import org.apache.commons.lang.Validate;
/*     */ import org.bukkit.Color;
/*     */ import org.bukkit.FireworkEffect;
/*     */ import org.bukkit.FireworkEffect.Builder;
/*     */ import org.bukkit.FireworkEffect.Type;
/*     */ import org.bukkit.Material;
/*     */ import org.bukkit.configuration.serialization.DelegateDeserialization;
/*     */ import org.bukkit.inventory.meta.FireworkMeta;
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
/*     */ @DelegateDeserialization(CraftMetaItem.SerializableMeta.class)
/*     */ class CraftMetaFirework
/*     */   extends CraftMetaItem
/*     */   implements FireworkMeta
/*     */ {
/*  43 */   static final CraftMetaItem.ItemMetaKey FIREWORKS = new CraftMetaItem.ItemMetaKey("Fireworks");
/*  44 */   static final CraftMetaItem.ItemMetaKey FLIGHT = new CraftMetaItem.ItemMetaKey("Flight", "power");
/*  45 */   static final CraftMetaItem.ItemMetaKey EXPLOSIONS = new CraftMetaItem.ItemMetaKey("Explosions", "firework-effects");
/*     */   
/*  47 */   static final CraftMetaItem.ItemMetaKey EXPLOSION_COLORS = new CraftMetaItem.ItemMetaKey("Colors");
/*     */   
/*  49 */   static final CraftMetaItem.ItemMetaKey EXPLOSION_TYPE = new CraftMetaItem.ItemMetaKey("Type");
/*     */   
/*  51 */   static final CraftMetaItem.ItemMetaKey EXPLOSION_TRAIL = new CraftMetaItem.ItemMetaKey("Trail");
/*     */   
/*  53 */   static final CraftMetaItem.ItemMetaKey EXPLOSION_FLICKER = new CraftMetaItem.ItemMetaKey("Flicker");
/*     */   
/*  55 */   static final CraftMetaItem.ItemMetaKey EXPLOSION_FADE = new CraftMetaItem.ItemMetaKey("FadeColors");
/*     */   private List<FireworkEffect> effects;
/*     */   private int power;
/*     */   
/*     */   CraftMetaFirework(CraftMetaItem meta)
/*     */   {
/*  61 */     super(meta);
/*     */     
/*  63 */     if (!(meta instanceof CraftMetaFirework)) {
/*  64 */       return;
/*     */     }
/*     */     
/*  67 */     CraftMetaFirework that = (CraftMetaFirework)meta;
/*     */     
/*  69 */     this.power = that.power;
/*     */     
/*  71 */     if (that.hasEffects()) {
/*  72 */       this.effects = new ArrayList(that.effects);
/*     */     }
/*     */   }
/*     */   
/*     */   CraftMetaFirework(NBTTagCompound tag) {
/*  77 */     super(tag);
/*     */     
/*  79 */     if (!tag.hasKey(FIREWORKS.NBT)) {
/*  80 */       return;
/*     */     }
/*     */     
/*  83 */     NBTTagCompound fireworks = tag.getCompound(FIREWORKS.NBT);
/*     */     
/*  85 */     this.power = (0xFF & fireworks.getByte(FLIGHT.NBT));
/*     */     
/*  87 */     if (!fireworks.hasKey(EXPLOSIONS.NBT)) {
/*  88 */       return;
/*     */     }
/*     */     
/*  91 */     NBTTagList fireworkEffects = fireworks.getList(EXPLOSIONS.NBT, 10);
/*  92 */     List<FireworkEffect> effects = this.effects = new ArrayList(fireworkEffects.size());
/*     */     
/*  94 */     for (int i = 0; i < fireworkEffects.size(); i++) {
/*  95 */       effects.add(getEffect(fireworkEffects.get(i)));
/*     */     }
/*     */   }
/*     */   
/*     */   static FireworkEffect getEffect(NBTTagCompound explosion) {
/* 100 */     FireworkEffect.Builder effect = FireworkEffect.builder()
/* 101 */       .flicker(explosion.getBoolean(EXPLOSION_FLICKER.NBT))
/* 102 */       .trail(explosion.getBoolean(EXPLOSION_TRAIL.NBT))
/* 103 */       .with(getEffectType(0xFF & explosion.getByte(EXPLOSION_TYPE.NBT)));
/*     */     int[] arrayOfInt;
/* 105 */     int i = (arrayOfInt = explosion.getIntArray(EXPLOSION_COLORS.NBT)).length; for (int j = 0; j < i; j++) { int color = arrayOfInt[j];
/* 106 */       effect.withColor(Color.fromRGB(color));
/*     */     }
/*     */     
/* 109 */     i = (arrayOfInt = explosion.getIntArray(EXPLOSION_FADE.NBT)).length; for (j = 0; j < i; j++) { int color = arrayOfInt[j];
/* 110 */       effect.withFade(Color.fromRGB(color));
/*     */     }
/*     */     
/* 113 */     return effect.build();
/*     */   }
/*     */   
/*     */   static NBTTagCompound getExplosion(FireworkEffect effect) {
/* 117 */     NBTTagCompound explosion = new NBTTagCompound();
/*     */     
/* 119 */     if (effect.hasFlicker()) {
/* 120 */       explosion.setBoolean(EXPLOSION_FLICKER.NBT, true);
/*     */     }
/*     */     
/* 123 */     if (effect.hasTrail()) {
/* 124 */       explosion.setBoolean(EXPLOSION_TRAIL.NBT, true);
/*     */     }
/*     */     
/* 127 */     addColors(explosion, EXPLOSION_COLORS, effect.getColors());
/* 128 */     addColors(explosion, EXPLOSION_FADE, effect.getFadeColors());
/*     */     
/* 130 */     explosion.setByte(EXPLOSION_TYPE.NBT, (byte)getNBT(effect.getType()));
/*     */     
/* 132 */     return explosion;
/*     */   }
/*     */   
/*     */   static int getNBT(FireworkEffect.Type type) {
/* 136 */     switch (type) {
/*     */     case BALL: 
/* 138 */       return 0;
/*     */     case BALL_LARGE: 
/* 140 */       return 1;
/*     */     case BURST: 
/* 142 */       return 2;
/*     */     case STAR: 
/* 144 */       return 3;
/*     */     case CREEPER: 
/* 146 */       return 4;
/*     */     }
/* 148 */     throw new IllegalStateException(type.toString());
/*     */   }
/*     */   
/*     */   static FireworkEffect.Type getEffectType(int nbt)
/*     */   {
/* 153 */     switch (nbt) {
/*     */     case 0: 
/* 155 */       return FireworkEffect.Type.BALL;
/*     */     case 1: 
/* 157 */       return FireworkEffect.Type.BALL_LARGE;
/*     */     case 2: 
/* 159 */       return FireworkEffect.Type.STAR;
/*     */     case 3: 
/* 161 */       return FireworkEffect.Type.CREEPER;
/*     */     case 4: 
/* 163 */       return FireworkEffect.Type.BURST;
/*     */     }
/* 165 */     throw new IllegalStateException(Integer.toString(nbt));
/*     */   }
/*     */   
/*     */   CraftMetaFirework(Map<String, Object> map)
/*     */   {
/* 170 */     super(map);
/*     */     
/* 172 */     Integer power = (Integer)CraftMetaItem.SerializableMeta.getObject(Integer.class, map, FLIGHT.BUKKIT, true);
/* 173 */     if (power != null) {
/* 174 */       setPower(power.intValue());
/*     */     }
/*     */     
/* 177 */     Iterable<?> effects = (Iterable)CraftMetaItem.SerializableMeta.getObject(Iterable.class, map, EXPLOSIONS.BUKKIT, true);
/* 178 */     safelyAddEffects(effects);
/*     */   }
/*     */   
/*     */   public boolean hasEffects() {
/* 182 */     return (this.effects != null) && (!this.effects.isEmpty());
/*     */   }
/*     */   
/*     */   void safelyAddEffects(Iterable<?> collection) {
/* 186 */     if ((collection == null) || (((collection instanceof Collection)) && (((Collection)collection).isEmpty()))) {
/* 187 */       return;
/*     */     }
/*     */     
/* 190 */     List<FireworkEffect> effects = this.effects;
/* 191 */     if (effects == null) {
/* 192 */       effects = this.effects = new ArrayList();
/*     */     }
/*     */     
/* 195 */     for (Object obj : collection) {
/* 196 */       if ((obj instanceof FireworkEffect)) {
/* 197 */         effects.add((FireworkEffect)obj);
/*     */       } else {
/* 199 */         throw new IllegalArgumentException(obj + " in " + collection + " is not a FireworkEffect");
/*     */       }
/*     */     }
/*     */   }
/*     */   
/*     */   void applyToItem(NBTTagCompound itemTag)
/*     */   {
/* 206 */     super.applyToItem(itemTag);
/* 207 */     if (isFireworkEmpty()) {
/* 208 */       return;
/*     */     }
/*     */     
/* 211 */     NBTTagCompound fireworks = itemTag.getCompound(FIREWORKS.NBT);
/* 212 */     itemTag.set(FIREWORKS.NBT, fireworks);
/*     */     
/* 214 */     if (hasEffects()) {
/* 215 */       NBTTagList effects = new NBTTagList();
/* 216 */       for (FireworkEffect effect : this.effects) {
/* 217 */         effects.add(getExplosion(effect));
/*     */       }
/*     */       
/* 220 */       if (effects.size() > 0) {
/* 221 */         fireworks.set(EXPLOSIONS.NBT, effects);
/*     */       }
/*     */     }
/*     */     
/* 225 */     if (hasPower()) {
/* 226 */       fireworks.setByte(FLIGHT.NBT, (byte)this.power);
/*     */     }
/*     */   }
/*     */   
/*     */   static void addColors(NBTTagCompound compound, CraftMetaItem.ItemMetaKey key, List<Color> colors) {
/* 231 */     if (colors.isEmpty()) {
/* 232 */       return;
/*     */     }
/*     */     
/* 235 */     int[] colorArray = new int[colors.size()];
/* 236 */     int i = 0;
/* 237 */     for (Color color : colors) {
/* 238 */       colorArray[(i++)] = color.asRGB();
/*     */     }
/*     */     
/* 241 */     compound.setIntArray(key.NBT, colorArray);
/*     */   }
/*     */   
/*     */   boolean applicableTo(Material type)
/*     */   {
/* 246 */     switch (type) {
/*     */     case STONE_SLAB2: 
/* 248 */       return true;
/*     */     }
/* 250 */     return false;
/*     */   }
/*     */   
/*     */ 
/*     */   boolean isEmpty()
/*     */   {
/* 256 */     return (super.isEmpty()) && (isFireworkEmpty());
/*     */   }
/*     */   
/*     */   boolean isFireworkEmpty() {
/* 260 */     return (!hasEffects()) && (!hasPower());
/*     */   }
/*     */   
/*     */   boolean hasPower() {
/* 264 */     return this.power != 0;
/*     */   }
/*     */   
/*     */   boolean equalsCommon(CraftMetaItem meta)
/*     */   {
/* 269 */     if (!super.equalsCommon(meta)) {
/* 270 */       return false;
/*     */     }
/*     */     
/* 273 */     if ((meta instanceof CraftMetaFirework)) {
/* 274 */       CraftMetaFirework that = (CraftMetaFirework)meta;
/*     */       
/* 276 */       return (hasPower() ? (that.hasPower()) && (this.power == that.power) : !that.hasPower()) && 
/* 277 */         (hasEffects() ? (that.hasEffects()) && (this.effects.equals(that.effects)) : !that.hasEffects());
/*     */     }
/*     */     
/* 280 */     return true;
/*     */   }
/*     */   
/*     */   boolean notUncommon(CraftMetaItem meta)
/*     */   {
/* 285 */     return (super.notUncommon(meta)) && (((meta instanceof CraftMetaFirework)) || (isFireworkEmpty()));
/*     */   }
/*     */   
/*     */   int applyHash()
/*     */   {
/*     */     int original;
/* 291 */     int hash = original = super.applyHash();
/* 292 */     if (hasPower()) {
/* 293 */       hash = 61 * hash + this.power;
/*     */     }
/* 295 */     if (hasEffects()) {
/* 296 */       hash = 61 * hash + 13 * this.effects.hashCode();
/*     */     }
/* 298 */     return hash != original ? CraftMetaFirework.class.hashCode() ^ hash : hash;
/*     */   }
/*     */   
/*     */   ImmutableMap.Builder<String, Object> serialize(ImmutableMap.Builder<String, Object> builder)
/*     */   {
/* 303 */     super.serialize(builder);
/*     */     
/* 305 */     if (hasEffects()) {
/* 306 */       builder.put(EXPLOSIONS.BUKKIT, ImmutableList.copyOf(this.effects));
/*     */     }
/*     */     
/* 309 */     if (hasPower()) {
/* 310 */       builder.put(FLIGHT.BUKKIT, Integer.valueOf(this.power));
/*     */     }
/*     */     
/* 313 */     return builder;
/*     */   }
/*     */   
/*     */   public CraftMetaFirework clone()
/*     */   {
/* 318 */     CraftMetaFirework meta = (CraftMetaFirework)super.clone();
/*     */     
/* 320 */     if (this.effects != null) {
/* 321 */       meta.effects = new ArrayList(this.effects);
/*     */     }
/*     */     
/* 324 */     return meta;
/*     */   }
/*     */   
/*     */   public void addEffect(FireworkEffect effect) {
/* 328 */     Validate.notNull(effect, "Effect cannot be null");
/* 329 */     if (this.effects == null) {
/* 330 */       this.effects = new ArrayList();
/*     */     }
/* 332 */     this.effects.add(effect);
/*     */   }
/*     */   
/*     */   public void addEffects(FireworkEffect... effects) {
/* 336 */     Validate.notNull(effects, "Effects cannot be null");
/* 337 */     if (effects.length == 0) {
/* 338 */       return;
/*     */     }
/*     */     
/* 341 */     List<FireworkEffect> list = this.effects;
/* 342 */     if (list == null) {
/* 343 */       list = this.effects = new ArrayList();
/*     */     }
/*     */     FireworkEffect[] arrayOfFireworkEffect;
/* 346 */     int i = (arrayOfFireworkEffect = effects).length; for (int j = 0; j < i; j++) { FireworkEffect effect = arrayOfFireworkEffect[j];
/* 347 */       Validate.notNull(effect, "Effect cannot be null");
/* 348 */       list.add(effect);
/*     */     }
/*     */   }
/*     */   
/*     */   public void addEffects(Iterable<FireworkEffect> effects) {
/* 353 */     Validate.notNull(effects, "Effects cannot be null");
/* 354 */     safelyAddEffects(effects);
/*     */   }
/*     */   
/*     */   public List<FireworkEffect> getEffects() {
/* 358 */     return this.effects == null ? ImmutableList.of() : ImmutableList.copyOf(this.effects);
/*     */   }
/*     */   
/*     */   public int getEffectsSize() {
/* 362 */     return this.effects == null ? 0 : this.effects.size();
/*     */   }
/*     */   
/*     */   public void removeEffect(int index) {
/* 366 */     if (this.effects == null) {
/* 367 */       throw new IndexOutOfBoundsException("Index: " + index + ", Size: 0");
/*     */     }
/* 369 */     this.effects.remove(index);
/*     */   }
/*     */   
/*     */   public void clearEffects()
/*     */   {
/* 374 */     this.effects = null;
/*     */   }
/*     */   
/*     */   public int getPower() {
/* 378 */     return this.power;
/*     */   }
/*     */   
/*     */   public void setPower(int power) {
/* 382 */     Validate.isTrue(power >= 0, "Power cannot be less than zero: ", power);
/* 383 */     Validate.isTrue(power < 128, "Power cannot be more than 127: ", power);
/* 384 */     this.power = power;
/*     */   }
/*     */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\org\bukkit\craftbukkit\v1_8_R3\inventory\CraftMetaFirework.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */
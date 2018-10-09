/*     */ package org.bukkit;
/*     */ 
/*     */ import com.google.common.collect.ImmutableList;
/*     */ import com.google.common.collect.ImmutableList.Builder;
/*     */ import org.apache.commons.lang.Validate;
/*     */ 
/*     */ @org.bukkit.configuration.serialization.SerializableAs("Firework")
/*     */ public final class FireworkEffect implements org.bukkit.configuration.serialization.ConfigurationSerializable
/*     */ {
/*     */   private static final String FLICKER = "flicker";
/*     */   private static final String TRAIL = "trail";
/*     */   private static final String COLORS = "colors";
/*     */   private static final String FADE_COLORS = "fade-colors";
/*     */   private static final String TYPE = "type";
/*     */   private final boolean flicker;
/*     */   private final boolean trail;
/*     */   private final ImmutableList<Color> colors;
/*     */   private final ImmutableList<Color> fadeColors;
/*     */   private final Type type;
/*     */   
/*     */   public static enum Type
/*     */   {
/*  23 */     BALL, 
/*     */     
/*     */ 
/*     */ 
/*  27 */     BALL_LARGE, 
/*     */     
/*     */ 
/*     */ 
/*  31 */     STAR, 
/*     */     
/*     */ 
/*     */ 
/*  35 */     BURST, 
/*     */     
/*     */ 
/*     */ 
/*  39 */     CREEPER;
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
/*     */   public static Builder builder()
/*     */   {
/*  52 */     return new Builder();
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public static final class Builder
/*     */   {
/*  61 */     boolean flicker = false;
/*  62 */     boolean trail = false;
/*  63 */     final ImmutableList.Builder<Color> colors = ImmutableList.builder();
/*  64 */     ImmutableList.Builder<Color> fadeColors = null;
/*  65 */     FireworkEffect.Type type = FireworkEffect.Type.BALL;
/*     */     
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     public Builder with(FireworkEffect.Type type)
/*     */       throws IllegalArgumentException
/*     */     {
/*  77 */       Validate.notNull(type, "Cannot have null type");
/*  78 */       this.type = type;
/*  79 */       return this;
/*     */     }
/*     */     
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     public Builder withFlicker()
/*     */     {
/*  88 */       this.flicker = true;
/*  89 */       return this;
/*     */     }
/*     */     
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     public Builder flicker(boolean flicker)
/*     */     {
/*  99 */       this.flicker = flicker;
/* 100 */       return this;
/*     */     }
/*     */     
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     public Builder withTrail()
/*     */     {
/* 109 */       this.trail = true;
/* 110 */       return this;
/*     */     }
/*     */     
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     public Builder trail(boolean trail)
/*     */     {
/* 120 */       this.trail = trail;
/* 121 */       return this;
/*     */     }
/*     */     
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     public Builder withColor(Color color)
/*     */       throws IllegalArgumentException
/*     */     {
/* 132 */       Validate.notNull(color, "Cannot have null color");
/*     */       
/* 134 */       this.colors.add(color);
/*     */       
/* 136 */       return this;
/*     */     }
/*     */     
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     public Builder withColor(Color... colors)
/*     */       throws IllegalArgumentException
/*     */     {
/* 149 */       Validate.notNull(colors, "Cannot have null colors");
/* 150 */       if (colors.length == 0) {
/* 151 */         return this;
/*     */       }
/*     */       
/* 154 */       ImmutableList.Builder<Color> list = this.colors;
/* 155 */       Color[] arrayOfColor; int i = (arrayOfColor = colors).length; for (int j = 0; j < i; j++) { Color color = arrayOfColor[j];
/* 156 */         Validate.notNull(color, "Color cannot be null");
/* 157 */         list.add(color);
/*     */       }
/*     */       
/* 160 */       return this;
/*     */     }
/*     */     
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     public Builder withColor(Iterable<?> colors)
/*     */       throws IllegalArgumentException
/*     */     {
/* 174 */       Validate.notNull(colors, "Cannot have null colors");
/*     */       
/* 176 */       ImmutableList.Builder<Color> list = this.colors;
/* 177 */       for (Object color : colors) {
/* 178 */         if (!(color instanceof Color)) {
/* 179 */           throw new IllegalArgumentException(color + " is not a Color in " + colors);
/*     */         }
/* 181 */         list.add((Color)color);
/*     */       }
/*     */       
/* 184 */       return this;
/*     */     }
/*     */     
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     public Builder withFade(Color color)
/*     */       throws IllegalArgumentException
/*     */     {
/* 197 */       Validate.notNull(color, "Cannot have null color");
/*     */       
/* 199 */       if (this.fadeColors == null) {
/* 200 */         this.fadeColors = ImmutableList.builder();
/*     */       }
/*     */       
/* 203 */       this.fadeColors.add(color);
/*     */       
/* 205 */       return this;
/*     */     }
/*     */     
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     public Builder withFade(Color... colors)
/*     */       throws IllegalArgumentException
/*     */     {
/* 218 */       Validate.notNull(colors, "Cannot have null colors");
/* 219 */       if (colors.length == 0) {
/* 220 */         return this;
/*     */       }
/*     */       
/* 223 */       ImmutableList.Builder<Color> list = this.fadeColors;
/* 224 */       if (list == null) {
/* 225 */         list = this.fadeColors = ImmutableList.builder();
/*     */       }
/*     */       Color[] arrayOfColor;
/* 228 */       int i = (arrayOfColor = colors).length; for (int j = 0; j < i; j++) { Color color = arrayOfColor[j];
/* 229 */         Validate.notNull(color, "Color cannot be null");
/* 230 */         list.add(color);
/*     */       }
/*     */       
/* 233 */       return this;
/*     */     }
/*     */     
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     public Builder withFade(Iterable<?> colors)
/*     */       throws IllegalArgumentException
/*     */     {
/* 247 */       Validate.notNull(colors, "Cannot have null colors");
/*     */       
/* 249 */       ImmutableList.Builder<Color> list = this.fadeColors;
/* 250 */       if (list == null) {
/* 251 */         list = this.fadeColors = ImmutableList.builder();
/*     */       }
/*     */       
/* 254 */       for (Object color : colors) {
/* 255 */         if (!(color instanceof Color)) {
/* 256 */           throw new IllegalArgumentException(color + " is not a Color in " + colors);
/*     */         }
/* 258 */         list.add((Color)color);
/*     */       }
/*     */       
/* 261 */       return this;
/*     */     }
/*     */     
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     public FireworkEffect build()
/*     */     {
/* 273 */       return new FireworkEffect(
/* 274 */         this.flicker, 
/* 275 */         this.trail, 
/* 276 */         this.colors.build(), 
/* 277 */         this.fadeColors == null ? ImmutableList.of() : this.fadeColors.build(), 
/* 278 */         this.type);
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
/*     */ 
/*     */ 
/* 294 */   private String string = null;
/*     */   
/*     */   FireworkEffect(boolean flicker, boolean trail, ImmutableList<Color> colors, ImmutableList<Color> fadeColors, Type type) {
/* 297 */     if (colors.isEmpty()) {
/* 298 */       throw new IllegalStateException("Cannot make FireworkEffect without any color");
/*     */     }
/* 300 */     this.flicker = flicker;
/* 301 */     this.trail = trail;
/* 302 */     this.colors = colors;
/* 303 */     this.fadeColors = fadeColors;
/* 304 */     this.type = type;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public boolean hasFlicker()
/*     */   {
/* 313 */     return this.flicker;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public boolean hasTrail()
/*     */   {
/* 322 */     return this.trail;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public java.util.List<Color> getColors()
/*     */   {
/* 331 */     return this.colors;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public java.util.List<Color> getFadeColors()
/*     */   {
/* 340 */     return this.fadeColors;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public Type getType()
/*     */   {
/* 349 */     return this.type;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public static org.bukkit.configuration.serialization.ConfigurationSerializable deserialize(java.util.Map<String, Object> map)
/*     */   {
/* 358 */     Type type = Type.valueOf((String)map.get("type"));
/* 359 */     if (type == null) {
/* 360 */       throw new IllegalArgumentException(map.get("type") + " is not a valid Type");
/*     */     }
/*     */     
/* 363 */     return 
/*     */     
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/* 369 */       builder().flicker(((Boolean)map.get("flicker")).booleanValue()).trail(((Boolean)map.get("trail")).booleanValue()).withColor((Iterable)map.get("colors")).withFade((Iterable)map.get("fade-colors")).with(type).build();
/*     */   }
/*     */   
/*     */   public java.util.Map<String, Object> serialize()
/*     */   {
/* 374 */     return com.google.common.collect.ImmutableMap.of(
/* 375 */       "flicker", Boolean.valueOf(this.flicker), 
/* 376 */       "trail", Boolean.valueOf(this.trail), 
/* 377 */       "colors", this.colors, 
/* 378 */       "fade-colors", this.fadeColors, 
/* 379 */       "type", this.type.name());
/*     */   }
/*     */   
/*     */ 
/*     */   public String toString()
/*     */   {
/* 385 */     String string = this.string;
/* 386 */     if (string == null) {
/* 387 */       return this.string = "FireworkEffect:" + serialize();
/*     */     }
/* 389 */     return string;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public int hashCode()
/*     */   {
/* 398 */     int hash = 1;
/* 399 */     hash = hash * 31 + (this.flicker ? 1231 : 1237);
/* 400 */     hash = hash * 31 + (this.trail ? 1231 : 1237);
/* 401 */     hash = hash * 31 + this.type.hashCode();
/* 402 */     hash = hash * 31 + this.colors.hashCode();
/* 403 */     hash = hash * 31 + this.fadeColors.hashCode();
/* 404 */     return hash;
/*     */   }
/*     */   
/*     */   public boolean equals(Object obj)
/*     */   {
/* 409 */     if (this == obj) {
/* 410 */       return true;
/*     */     }
/*     */     
/* 413 */     if (!(obj instanceof FireworkEffect)) {
/* 414 */       return false;
/*     */     }
/*     */     
/* 417 */     FireworkEffect that = (FireworkEffect)obj;
/* 418 */     return (this.flicker == that.flicker) && 
/* 419 */       (this.trail == that.trail) && 
/* 420 */       (this.type == that.type) && 
/* 421 */       (this.colors.equals(that.colors)) && 
/* 422 */       (this.fadeColors.equals(that.fadeColors));
/*     */   }
/*     */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\org\bukkit\FireworkEffect.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */
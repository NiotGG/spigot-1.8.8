/*     */ package org.bukkit;
/*     */ 
/*     */ import com.google.common.collect.Maps;
/*     */ import java.util.Map;
/*     */ import org.bukkit.block.BlockFace;
/*     */ import org.bukkit.material.MaterialData;
/*     */ import org.bukkit.potion.Potion;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public enum Effect
/*     */ {
/*  15 */   CLICK2(
/*     */   
/*     */ 
/*  18 */     1000, Type.SOUND), 
/*  19 */   CLICK1(
/*     */   
/*     */ 
/*  22 */     1001, Type.SOUND), 
/*  23 */   BOW_FIRE(
/*     */   
/*     */ 
/*  26 */     1002, Type.SOUND), 
/*  27 */   DOOR_TOGGLE(
/*     */   
/*     */ 
/*  30 */     1003, Type.SOUND), 
/*  31 */   EXTINGUISH(
/*     */   
/*     */ 
/*  34 */     1004, Type.SOUND), 
/*  35 */   RECORD_PLAY(
/*     */   
/*     */ 
/*  38 */     1005, Type.SOUND, Material.class), 
/*  39 */   GHAST_SHRIEK(
/*     */   
/*     */ 
/*  42 */     1007, Type.SOUND), 
/*  43 */   GHAST_SHOOT(
/*     */   
/*     */ 
/*  46 */     1008, Type.SOUND), 
/*  47 */   BLAZE_SHOOT(
/*     */   
/*     */ 
/*  50 */     1009, Type.SOUND), 
/*  51 */   ZOMBIE_CHEW_WOODEN_DOOR(
/*     */   
/*     */ 
/*  54 */     1010, Type.SOUND), 
/*  55 */   ZOMBIE_CHEW_IRON_DOOR(
/*     */   
/*     */ 
/*  58 */     1011, Type.SOUND), 
/*  59 */   ZOMBIE_DESTROY_DOOR(
/*     */   
/*     */ 
/*  62 */     1012, Type.SOUND), 
/*  63 */   SMOKE(
/*     */   
/*     */ 
/*  66 */     2000, Type.VISUAL, BlockFace.class), 
/*  67 */   STEP_SOUND(
/*     */   
/*     */ 
/*  70 */     2001, Type.SOUND, Material.class), 
/*  71 */   POTION_BREAK(
/*     */   
/*     */ 
/*     */ 
/*  75 */     2002, Type.VISUAL, Potion.class), 
/*  76 */   ENDER_SIGNAL(
/*     */   
/*     */ 
/*  79 */     2003, Type.VISUAL), 
/*  80 */   MOBSPAWNER_FLAMES(
/*     */   
/*     */ 
/*  83 */     2004, Type.VISUAL), 
/*  84 */   FIREWORKS_SPARK(
/*     */   
/*     */ 
/*  87 */     "fireworksSpark", Type.PARTICLE), 
/*  88 */   CRIT(
/*     */   
/*     */ 
/*  91 */     "crit", Type.PARTICLE), 
/*  92 */   MAGIC_CRIT(
/*     */   
/*     */ 
/*  95 */     "magicCrit", Type.PARTICLE), 
/*  96 */   POTION_SWIRL(
/*     */   
/*     */ 
/*  99 */     "mobSpell", Type.PARTICLE), 
/* 100 */   POTION_SWIRL_TRANSPARENT(
/*     */   
/*     */ 
/* 103 */     "mobSpellAmbient", Type.PARTICLE), 
/* 104 */   SPELL(
/*     */   
/*     */ 
/* 107 */     "spell", Type.PARTICLE), 
/* 108 */   INSTANT_SPELL(
/*     */   
/*     */ 
/* 111 */     "instantSpell", Type.PARTICLE), 
/* 112 */   WITCH_MAGIC(
/*     */   
/*     */ 
/* 115 */     "witchMagic", Type.PARTICLE), 
/* 116 */   NOTE(
/*     */   
/*     */ 
/* 119 */     "note", Type.PARTICLE), 
/* 120 */   PORTAL(
/*     */   
/*     */ 
/* 123 */     "portal", Type.PARTICLE), 
/* 124 */   FLYING_GLYPH(
/*     */   
/*     */ 
/* 127 */     "enchantmenttable", Type.PARTICLE), 
/* 128 */   FLAME(
/*     */   
/*     */ 
/* 131 */     "flame", Type.PARTICLE), 
/* 132 */   LAVA_POP(
/*     */   
/*     */ 
/* 135 */     "lava", Type.PARTICLE), 
/* 136 */   FOOTSTEP(
/*     */   
/*     */ 
/* 139 */     "footstep", Type.PARTICLE), 
/* 140 */   SPLASH(
/*     */   
/*     */ 
/* 143 */     "splash", Type.PARTICLE), 
/* 144 */   PARTICLE_SMOKE(
/*     */   
/*     */ 
/* 147 */     "smoke", Type.PARTICLE), 
/* 148 */   EXPLOSION_HUGE(
/*     */   
/*     */ 
/* 151 */     "hugeexplosion", Type.PARTICLE), 
/* 152 */   EXPLOSION_LARGE(
/*     */   
/*     */ 
/* 155 */     "largeexplode", Type.PARTICLE), 
/* 156 */   EXPLOSION(
/*     */   
/*     */ 
/* 159 */     "explode", Type.PARTICLE), 
/* 160 */   VOID_FOG(
/*     */   
/*     */ 
/* 163 */     "depthsuspend", Type.PARTICLE), 
/* 164 */   SMALL_SMOKE(
/*     */   
/*     */ 
/* 167 */     "townaura", Type.PARTICLE), 
/* 168 */   CLOUD(
/*     */   
/*     */ 
/* 171 */     "cloud", Type.PARTICLE), 
/* 172 */   COLOURED_DUST(
/*     */   
/*     */ 
/* 175 */     "reddust", Type.PARTICLE), 
/* 176 */   SNOWBALL_BREAK(
/*     */   
/*     */ 
/* 179 */     "snowballpoof", Type.PARTICLE), 
/* 180 */   WATERDRIP(
/*     */   
/*     */ 
/* 183 */     "dripWater", Type.PARTICLE), 
/* 184 */   LAVADRIP(
/*     */   
/*     */ 
/* 187 */     "dripLava", Type.PARTICLE), 
/* 188 */   SNOW_SHOVEL(
/*     */   
/*     */ 
/* 191 */     "snowshovel", Type.PARTICLE), 
/* 192 */   SLIME(
/*     */   
/*     */ 
/* 195 */     "slime", Type.PARTICLE), 
/* 196 */   HEART(
/*     */   
/*     */ 
/* 199 */     "heart", Type.PARTICLE), 
/* 200 */   VILLAGER_THUNDERCLOUD(
/*     */   
/*     */ 
/* 203 */     "angryVillager", Type.PARTICLE), 
/* 204 */   HAPPY_VILLAGER(
/*     */   
/*     */ 
/* 207 */     "happyVillager", Type.PARTICLE), 
/* 208 */   LARGE_SMOKE(
/*     */   
/*     */ 
/*     */ 
/* 212 */     "largesmoke", Type.PARTICLE), 
/* 213 */   ITEM_BREAK(
/*     */   
/*     */ 
/*     */ 
/* 217 */     "iconcrack", Type.PARTICLE, Material.class), 
/* 218 */   TILE_BREAK(
/*     */   
/*     */ 
/*     */ 
/* 222 */     "blockcrack", Type.PARTICLE, MaterialData.class), 
/* 223 */   TILE_DUST(
/*     */   
/*     */ 
/*     */ 
/* 227 */     "blockdust", Type.PARTICLE, MaterialData.class);
/*     */   
/*     */   private final int id;
/*     */   private final Type type;
/*     */   private final Class<?> data;
/*     */   private static final Map<Integer, Effect> BY_ID;
/*     */   private static final Map<String, Effect> BY_NAME;
/*     */   private final String particleName;
/*     */   
/*     */   private Effect(int id, Type type) {
/* 237 */     this(id, type, null);
/*     */   }
/*     */   
/*     */   private Effect(int id, Type type, Class<?> data) {
/* 241 */     this.id = id;
/* 242 */     this.type = type;
/* 243 */     this.data = data;
/* 244 */     this.particleName = null;
/*     */   }
/*     */   
/*     */   private Effect(String particleName, Type type, Class<?> data) {
/* 248 */     this.particleName = particleName;
/* 249 */     this.type = type;
/* 250 */     this.id = 0;
/* 251 */     this.data = data;
/*     */   }
/*     */   
/*     */   private Effect(String particleName, Type type) {
/* 255 */     this.particleName = particleName;
/* 256 */     this.type = type;
/* 257 */     this.id = 0;
/* 258 */     this.data = null;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   @Deprecated
/*     */   public int getId()
/*     */   {
/* 269 */     return this.id;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public String getName()
/*     */   {
/* 278 */     return this.particleName;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public Type getType()
/*     */   {
/* 285 */     return this.type;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public Class<?> getData()
/*     */   {
/* 292 */     return this.data;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   @Deprecated
/*     */   public static Effect getById(int id)
/*     */   {
/* 304 */     return (Effect)BY_ID.get(Integer.valueOf(id));
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
/*     */   public static Effect getByName(String name)
/*     */   {
/* 322 */     return (Effect)BY_NAME.get(name);
/*     */   }
/*     */   
/*     */   static
/*     */   {
/* 232 */     BY_ID = Maps.newHashMap();
/* 233 */     BY_NAME = Maps.newHashMap();
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
/*     */     Effect[] arrayOfEffect;
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
/* 308 */     int i = (arrayOfEffect = values()).length; for (int j = 0; j < i; j++) { Effect effect = arrayOfEffect[j];
/* 309 */       if (effect.type != Type.PARTICLE) {
/* 310 */         BY_ID.put(Integer.valueOf(effect.id), effect);
/*     */       }
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/* 326 */     i = (arrayOfEffect = values()).length; for (j = 0; j < i; j++) { Effect effect = arrayOfEffect[j];
/* 327 */       if (effect.type == Type.PARTICLE) {
/* 328 */         BY_NAME.put(effect.particleName, effect);
/*     */       }
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */   public static enum Type
/*     */   {
/* 336 */     SOUND,  VISUAL,  PARTICLE;
/*     */   }
/*     */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\org\bukkit\Effect.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */
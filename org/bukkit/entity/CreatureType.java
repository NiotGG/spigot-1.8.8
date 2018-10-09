/*     */ package org.bukkit.entity;
/*     */ 
/*     */ import java.util.EnumSet;
/*     */ import java.util.HashMap;
/*     */ import java.util.Map;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ @Deprecated
/*     */ public enum CreatureType
/*     */ {
/*  14 */   CREEPER(
/*  15 */     "Creeper", Creeper.class, 50), 
/*  16 */   SKELETON("Skeleton", Skeleton.class, 51), 
/*  17 */   SPIDER("Spider", Spider.class, 52), 
/*  18 */   GIANT("Giant", Giant.class, 53), 
/*  19 */   ZOMBIE("Zombie", Zombie.class, 54), 
/*  20 */   SLIME("Slime", Slime.class, 55), 
/*  21 */   GHAST("Ghast", Ghast.class, 56), 
/*  22 */   PIG_ZOMBIE("PigZombie", PigZombie.class, 57), 
/*  23 */   ENDERMAN("Enderman", Enderman.class, 58), 
/*  24 */   CAVE_SPIDER("CaveSpider", CaveSpider.class, 59), 
/*  25 */   SILVERFISH("Silverfish", Silverfish.class, 60), 
/*  26 */   BLAZE("Blaze", Blaze.class, 61), 
/*  27 */   MAGMA_CUBE("LavaSlime", MagmaCube.class, 62), 
/*  28 */   ENDER_DRAGON("EnderDragon", EnderDragon.class, 63), 
/*  29 */   ENDERMITE("Endermite", Endermite.class, 67), 
/*  30 */   GUARDIAN("Guardian", Guardian.class, 68), 
/*  31 */   PIG("Pig", Pig.class, 90), 
/*  32 */   SHEEP("Sheep", Sheep.class, 91), 
/*  33 */   COW("Cow", Cow.class, 92), 
/*  34 */   CHICKEN("Chicken", Chicken.class, 93), 
/*  35 */   SQUID("Squid", Squid.class, 94), 
/*  36 */   WOLF("Wolf", Wolf.class, 95), 
/*  37 */   MUSHROOM_COW("MushroomCow", MushroomCow.class, 96), 
/*  38 */   SNOWMAN("SnowMan", Snowman.class, 97), 
/*  39 */   RABBIT("Rabbit", Rabbit.class, 101), 
/*  40 */   VILLAGER("Villager", Villager.class, 120);
/*     */   
/*     */   private String name;
/*     */   private Class<? extends Entity> clazz;
/*     */   
/*     */   static {
/*  46 */     NAME_MAP = new HashMap();
/*  47 */     ID_MAP = new HashMap();
/*     */     
/*     */ 
/*  50 */     for (CreatureType type : EnumSet.allOf(CreatureType.class)) {
/*  51 */       NAME_MAP.put(type.name, type);
/*  52 */       if (type.typeId != 0) {
/*  53 */         ID_MAP.put(Short.valueOf(type.typeId), type);
/*     */       }
/*     */     }
/*     */   }
/*     */   
/*     */   private CreatureType(String name, Class<? extends Entity> clazz, int typeId) {
/*  59 */     this.name = name;
/*  60 */     this.clazz = clazz;
/*  61 */     this.typeId = ((short)typeId);
/*     */   }
/*     */   
/*     */   public String getName() {
/*  65 */     return this.name;
/*     */   }
/*     */   
/*     */   public Class<? extends Entity> getEntityClass() {
/*  69 */     return this.clazz;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   @Deprecated
/*     */   public short getTypeId()
/*     */   {
/*  79 */     return this.typeId;
/*     */   }
/*     */   
/*     */   public static CreatureType fromName(String name) {
/*  83 */     return (CreatureType)NAME_MAP.get(name);
/*     */   }
/*     */   
/*     */ 
/*     */   private short typeId;
/*     */   
/*     */   private static final Map<String, CreatureType> NAME_MAP;
/*     */   private static final Map<Short, CreatureType> ID_MAP;
/*     */   @Deprecated
/*     */   public static CreatureType fromId(int id)
/*     */   {
/*  94 */     if (id > 32767) {
/*  95 */       return null;
/*     */     }
/*  97 */     return (CreatureType)ID_MAP.get(Short.valueOf((short)id));
/*     */   }
/*     */   
/*     */   @Deprecated
/*     */   public EntityType toEntityType() {
/* 102 */     return EntityType.fromName(getName());
/*     */   }
/*     */   
/*     */   public static CreatureType fromEntityType(EntityType creatureType) {
/* 106 */     return fromName(creatureType.getName());
/*     */   }
/*     */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\org\bukkit\entity\CreatureType.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */
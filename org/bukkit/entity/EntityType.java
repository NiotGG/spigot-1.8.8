/*     */ package org.bukkit.entity;
/*     */ 
/*     */ import java.util.HashMap;
/*     */ import java.util.Map;
/*     */ import org.bukkit.entity.minecart.CommandMinecart;
/*     */ import org.bukkit.entity.minecart.ExplosiveMinecart;
/*     */ import org.bukkit.entity.minecart.HopperMinecart;
/*     */ import org.bukkit.entity.minecart.PoweredMinecart;
/*     */ import org.bukkit.entity.minecart.RideableMinecart;
/*     */ import org.bukkit.entity.minecart.SpawnerMinecart;
/*     */ import org.bukkit.entity.minecart.StorageMinecart;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public enum EntityType
/*     */ {
/*  19 */   DROPPED_ITEM(
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*  26 */     "Item", Item.class, 1, false), 
/*  27 */   EXPERIENCE_ORB(
/*     */   
/*     */ 
/*  30 */     "XPOrb", ExperienceOrb.class, 2), 
/*  31 */   LEASH_HITCH(
/*     */   
/*     */ 
/*  34 */     "LeashKnot", LeashHitch.class, 8), 
/*  35 */   PAINTING(
/*     */   
/*     */ 
/*  38 */     "Painting", Painting.class, 9), 
/*  39 */   ARROW(
/*     */   
/*     */ 
/*  42 */     "Arrow", Arrow.class, 10), 
/*  43 */   SNOWBALL(
/*     */   
/*     */ 
/*  46 */     "Snowball", Snowball.class, 11), 
/*  47 */   FIREBALL(
/*     */   
/*     */ 
/*  50 */     "Fireball", LargeFireball.class, 12), 
/*  51 */   SMALL_FIREBALL(
/*     */   
/*     */ 
/*  54 */     "SmallFireball", SmallFireball.class, 13), 
/*  55 */   ENDER_PEARL(
/*     */   
/*     */ 
/*  58 */     "ThrownEnderpearl", EnderPearl.class, 14), 
/*  59 */   ENDER_SIGNAL(
/*     */   
/*     */ 
/*  62 */     "EyeOfEnderSignal", EnderSignal.class, 15), 
/*  63 */   THROWN_EXP_BOTTLE(
/*     */   
/*     */ 
/*  66 */     "ThrownExpBottle", ThrownExpBottle.class, 17), 
/*  67 */   ITEM_FRAME(
/*     */   
/*     */ 
/*  70 */     "ItemFrame", ItemFrame.class, 18), 
/*  71 */   WITHER_SKULL(
/*     */   
/*     */ 
/*  74 */     "WitherSkull", WitherSkull.class, 19), 
/*  75 */   PRIMED_TNT(
/*     */   
/*     */ 
/*  78 */     "PrimedTnt", TNTPrimed.class, 20), 
/*  79 */   FALLING_BLOCK(
/*     */   
/*     */ 
/*  82 */     "FallingSand", FallingBlock.class, 21, false), 
/*  83 */   FIREWORK("FireworksRocketEntity", Firework.class, 22, false), 
/*  84 */   ARMOR_STAND("ArmorStand", ArmorStand.class, 30, false), 
/*  85 */   MINECART_COMMAND(
/*     */   
/*     */ 
/*  88 */     "MinecartCommandBlock", CommandMinecart.class, 40), 
/*  89 */   BOAT(
/*     */   
/*     */ 
/*  92 */     "Boat", Boat.class, 41), 
/*  93 */   MINECART(
/*     */   
/*     */ 
/*  96 */     "MinecartRideable", RideableMinecart.class, 42), 
/*  97 */   MINECART_CHEST(
/*     */   
/*     */ 
/* 100 */     "MinecartChest", StorageMinecart.class, 43), 
/* 101 */   MINECART_FURNACE(
/*     */   
/*     */ 
/* 104 */     "MinecartFurnace", PoweredMinecart.class, 44), 
/* 105 */   MINECART_TNT(
/*     */   
/*     */ 
/* 108 */     "MinecartTNT", ExplosiveMinecart.class, 45), 
/* 109 */   MINECART_HOPPER(
/*     */   
/*     */ 
/* 112 */     "MinecartHopper", HopperMinecart.class, 46), 
/* 113 */   MINECART_MOB_SPAWNER(
/*     */   
/*     */ 
/* 116 */     "MinecartMobSpawner", SpawnerMinecart.class, 47), 
/* 117 */   CREEPER("Creeper", Creeper.class, 50), 
/* 118 */   SKELETON("Skeleton", Skeleton.class, 51), 
/* 119 */   SPIDER("Spider", Spider.class, 52), 
/* 120 */   GIANT("Giant", Giant.class, 53), 
/* 121 */   ZOMBIE("Zombie", Zombie.class, 54), 
/* 122 */   SLIME("Slime", Slime.class, 55), 
/* 123 */   GHAST("Ghast", Ghast.class, 56), 
/* 124 */   PIG_ZOMBIE("PigZombie", PigZombie.class, 57), 
/* 125 */   ENDERMAN("Enderman", Enderman.class, 58), 
/* 126 */   CAVE_SPIDER("CaveSpider", CaveSpider.class, 59), 
/* 127 */   SILVERFISH("Silverfish", Silverfish.class, 60), 
/* 128 */   BLAZE("Blaze", Blaze.class, 61), 
/* 129 */   MAGMA_CUBE("LavaSlime", MagmaCube.class, 62), 
/* 130 */   ENDER_DRAGON("EnderDragon", EnderDragon.class, 63), 
/* 131 */   WITHER("WitherBoss", Wither.class, 64), 
/* 132 */   BAT("Bat", Bat.class, 65), 
/* 133 */   WITCH("Witch", Witch.class, 66), 
/* 134 */   ENDERMITE("Endermite", Endermite.class, 67), 
/* 135 */   GUARDIAN("Guardian", Guardian.class, 68), 
/* 136 */   PIG("Pig", Pig.class, 90), 
/* 137 */   SHEEP("Sheep", Sheep.class, 91), 
/* 138 */   COW("Cow", Cow.class, 92), 
/* 139 */   CHICKEN("Chicken", Chicken.class, 93), 
/* 140 */   SQUID("Squid", Squid.class, 94), 
/* 141 */   WOLF("Wolf", Wolf.class, 95), 
/* 142 */   MUSHROOM_COW("MushroomCow", MushroomCow.class, 96), 
/* 143 */   SNOWMAN("SnowMan", Snowman.class, 97), 
/* 144 */   OCELOT("Ozelot", Ocelot.class, 98), 
/* 145 */   IRON_GOLEM("VillagerGolem", IronGolem.class, 99), 
/* 146 */   HORSE("EntityHorse", Horse.class, 100), 
/* 147 */   RABBIT("Rabbit", Rabbit.class, 101), 
/* 148 */   VILLAGER("Villager", Villager.class, 120), 
/* 149 */   ENDER_CRYSTAL("EnderCrystal", EnderCrystal.class, 200), 
/* 150 */   SPLASH_POTION(
/*     */   
/*     */ 
/*     */ 
/* 154 */     null, ThrownPotion.class, -1, false), 
/* 155 */   EGG(
/*     */   
/*     */ 
/* 158 */     null, Egg.class, -1, false), 
/* 159 */   FISHING_HOOK(
/*     */   
/*     */ 
/* 162 */     null, Fish.class, -1, false), 
/* 163 */   LIGHTNING(
/*     */   
/*     */ 
/*     */ 
/*     */ 
/* 168 */     null, LightningStrike.class, -1, false), 
/* 169 */   WEATHER(null, Weather.class, -1, false), 
/* 170 */   PLAYER(null, Player.class, -1, false), 
/* 171 */   COMPLEX_PART(null, ComplexEntityPart.class, -1, false), 
/* 172 */   UNKNOWN(
/*     */   
/*     */ 
/* 175 */     null, null, -1, false);
/*     */   
/*     */   private String name;
/*     */   private Class<? extends Entity> clazz;
/*     */   private short typeId;
/*     */   
/*     */   static {
/* 182 */     NAME_MAP = new HashMap();
/* 183 */     ID_MAP = new HashMap();
/*     */     
/*     */     EntityType[] arrayOfEntityType;
/* 186 */     int i = (arrayOfEntityType = values()).length; for (int j = 0; j < i; j++) { EntityType type = arrayOfEntityType[j];
/* 187 */       if (type.name != null) {
/* 188 */         NAME_MAP.put(type.name.toLowerCase(), type);
/*     */       }
/* 190 */       if (type.typeId > 0) {
/* 191 */         ID_MAP.put(Short.valueOf(type.typeId), type);
/*     */       }
/*     */     }
/*     */   }
/*     */   
/*     */   private EntityType(String name, Class<? extends Entity> clazz, int typeId) {
/* 197 */     this(name, clazz, typeId, true);
/*     */   }
/*     */   
/*     */   private EntityType(String name, Class<? extends Entity> clazz, int typeId, boolean independent) {
/* 201 */     this.name = name;
/* 202 */     this.clazz = clazz;
/* 203 */     this.typeId = ((short)typeId);
/* 204 */     this.independent = independent;
/* 205 */     if (clazz != null) {
/* 206 */       this.living = LivingEntity.class.isAssignableFrom(clazz);
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   @Deprecated
/*     */   public String getName()
/*     */   {
/* 217 */     return this.name;
/*     */   }
/*     */   
/*     */   public Class<? extends Entity> getEntityClass() {
/* 221 */     return this.clazz;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   @Deprecated
/*     */   public short getTypeId()
/*     */   {
/* 231 */     return this.typeId;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   @Deprecated
/*     */   public static EntityType fromName(String name)
/*     */   {
/* 242 */     if (name == null) {
/* 243 */       return null;
/*     */     }
/* 245 */     return (EntityType)NAME_MAP.get(name.toLowerCase());
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   @Deprecated
/*     */   public static EntityType fromId(int id)
/*     */   {
/* 256 */     if (id > 32767) {
/* 257 */       return null;
/*     */     }
/* 259 */     return (EntityType)ID_MAP.get(Short.valueOf((short)id));
/*     */   }
/*     */   
/*     */ 
/*     */   private boolean independent;
/*     */   
/*     */   private boolean living;
/*     */   
/*     */   private static final Map<String, EntityType> NAME_MAP;
/*     */   private static final Map<Short, EntityType> ID_MAP;
/*     */   public boolean isSpawnable()
/*     */   {
/* 271 */     return this.independent;
/*     */   }
/*     */   
/*     */   public boolean isAlive() {
/* 275 */     return this.living;
/*     */   }
/*     */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\org\bukkit\entity\EntityType.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */
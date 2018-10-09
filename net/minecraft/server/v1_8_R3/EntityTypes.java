/*     */ package net.minecraft.server.v1_8_R3;
/*     */ 
/*     */ import com.google.common.collect.Lists;
/*     */ import com.google.common.collect.Maps;
/*     */ import java.lang.reflect.Constructor;
/*     */ import java.util.ArrayList;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import java.util.Set;
/*     */ import org.apache.logging.log4j.LogManager;
/*     */ import org.apache.logging.log4j.Logger;
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
/*     */ public class EntityTypes
/*     */ {
/*  39 */   private static final Logger b = ;
/*  40 */   private static final Map<String, Class<? extends Entity>> c = Maps.newHashMap();
/*  41 */   private static final Map<Class<? extends Entity>, String> d = Maps.newHashMap();
/*  42 */   private static final Map<Integer, Class<? extends Entity>> e = Maps.newHashMap();
/*  43 */   private static final Map<Class<? extends Entity>, Integer> f = Maps.newHashMap();
/*  44 */   private static final Map<String, Integer> g = Maps.newHashMap();
/*     */   
/*  46 */   public static final Map<Integer, MonsterEggInfo> eggInfo = Maps.newLinkedHashMap();
/*     */   
/*     */   private static void a(Class<? extends Entity> paramClass, String paramString, int paramInt) {
/*  49 */     if (c.containsKey(paramString)) {
/*  50 */       throw new IllegalArgumentException("ID is already registered: " + paramString);
/*     */     }
/*  52 */     if (e.containsKey(Integer.valueOf(paramInt))) {
/*  53 */       throw new IllegalArgumentException("ID is already registered: " + paramInt);
/*     */     }
/*  55 */     if (paramInt == 0) {
/*  56 */       throw new IllegalArgumentException("Cannot register to reserved id: " + paramInt);
/*     */     }
/*  58 */     if (paramClass == null) {
/*  59 */       throw new IllegalArgumentException("Cannot register null clazz for id: " + paramInt);
/*     */     }
/*  61 */     c.put(paramString, paramClass);
/*  62 */     d.put(paramClass, paramString);
/*  63 */     e.put(Integer.valueOf(paramInt), paramClass);
/*  64 */     f.put(paramClass, Integer.valueOf(paramInt));
/*  65 */     g.put(paramString, Integer.valueOf(paramInt));
/*     */   }
/*     */   
/*     */   private static void a(Class<? extends Entity> paramClass, String paramString, int paramInt1, int paramInt2, int paramInt3) {
/*  69 */     a(paramClass, paramString, paramInt1);
/*     */     
/*  71 */     eggInfo.put(Integer.valueOf(paramInt1), new MonsterEggInfo(paramInt1, paramInt2, paramInt3));
/*     */   }
/*     */   
/*     */   static {
/*  75 */     a(EntityItem.class, "Item", 1);
/*  76 */     a(EntityExperienceOrb.class, "XPOrb", 2);
/*     */     
/*  78 */     a(EntityEgg.class, "ThrownEgg", 7);
/*  79 */     a(EntityLeash.class, "LeashKnot", 8);
/*  80 */     a(EntityPainting.class, "Painting", 9);
/*  81 */     a(EntityArrow.class, "Arrow", 10);
/*  82 */     a(EntitySnowball.class, "Snowball", 11);
/*  83 */     a(EntityLargeFireball.class, "Fireball", 12);
/*  84 */     a(EntitySmallFireball.class, "SmallFireball", 13);
/*  85 */     a(EntityEnderPearl.class, "ThrownEnderpearl", 14);
/*  86 */     a(EntityEnderSignal.class, "EyeOfEnderSignal", 15);
/*  87 */     a(EntityPotion.class, "ThrownPotion", 16);
/*  88 */     a(EntityThrownExpBottle.class, "ThrownExpBottle", 17);
/*  89 */     a(EntityItemFrame.class, "ItemFrame", 18);
/*  90 */     a(EntityWitherSkull.class, "WitherSkull", 19);
/*     */     
/*  92 */     a(EntityTNTPrimed.class, "PrimedTnt", 20);
/*  93 */     a(EntityFallingBlock.class, "FallingSand", 21);
/*     */     
/*  95 */     a(EntityFireworks.class, "FireworksRocketEntity", 22);
/*     */     
/*  97 */     a(EntityArmorStand.class, "ArmorStand", 30);
/*     */     
/*  99 */     a(EntityBoat.class, "Boat", 41);
/* 100 */     a(EntityMinecartRideable.class, EntityMinecartAbstract.EnumMinecartType.RIDEABLE.b(), 42);
/* 101 */     a(EntityMinecartChest.class, EntityMinecartAbstract.EnumMinecartType.CHEST.b(), 43);
/* 102 */     a(EntityMinecartFurnace.class, EntityMinecartAbstract.EnumMinecartType.FURNACE.b(), 44);
/* 103 */     a(EntityMinecartTNT.class, EntityMinecartAbstract.EnumMinecartType.TNT.b(), 45);
/* 104 */     a(EntityMinecartHopper.class, EntityMinecartAbstract.EnumMinecartType.HOPPER.b(), 46);
/* 105 */     a(EntityMinecartMobSpawner.class, EntityMinecartAbstract.EnumMinecartType.SPAWNER.b(), 47);
/* 106 */     a(EntityMinecartCommandBlock.class, EntityMinecartAbstract.EnumMinecartType.COMMAND_BLOCK.b(), 40);
/*     */     
/* 108 */     a(EntityInsentient.class, "Mob", 48);
/* 109 */     a(EntityMonster.class, "Monster", 49);
/*     */     
/* 111 */     a(EntityCreeper.class, "Creeper", 50, 894731, 0);
/* 112 */     a(EntitySkeleton.class, "Skeleton", 51, 12698049, 4802889);
/* 113 */     a(EntitySpider.class, "Spider", 52, 3419431, 11013646);
/* 114 */     a(EntityGiantZombie.class, "Giant", 53);
/* 115 */     a(EntityZombie.class, "Zombie", 54, 44975, 7969893);
/* 116 */     a(EntitySlime.class, "Slime", 55, 5349438, 8306542);
/* 117 */     a(EntityGhast.class, "Ghast", 56, 16382457, 12369084);
/* 118 */     a(EntityPigZombie.class, "PigZombie", 57, 15373203, 5009705);
/* 119 */     a(EntityEnderman.class, "Enderman", 58, 1447446, 0);
/* 120 */     a(EntityCaveSpider.class, "CaveSpider", 59, 803406, 11013646);
/* 121 */     a(EntitySilverfish.class, "Silverfish", 60, 7237230, 3158064);
/* 122 */     a(EntityBlaze.class, "Blaze", 61, 16167425, 16775294);
/* 123 */     a(EntityMagmaCube.class, "LavaSlime", 62, 3407872, 16579584);
/* 124 */     a(EntityEnderDragon.class, "EnderDragon", 63);
/* 125 */     a(EntityWither.class, "WitherBoss", 64);
/* 126 */     a(EntityBat.class, "Bat", 65, 4996656, 986895);
/* 127 */     a(EntityWitch.class, "Witch", 66, 3407872, 5349438);
/* 128 */     a(EntityEndermite.class, "Endermite", 67, 1447446, 7237230);
/* 129 */     a(EntityGuardian.class, "Guardian", 68, 5931634, 15826224);
/*     */     
/* 131 */     a(EntityPig.class, "Pig", 90, 15771042, 14377823);
/* 132 */     a(EntitySheep.class, "Sheep", 91, 15198183, 16758197);
/* 133 */     a(EntityCow.class, "Cow", 92, 4470310, 10592673);
/* 134 */     a(EntityChicken.class, "Chicken", 93, 10592673, 16711680);
/* 135 */     a(EntitySquid.class, "Squid", 94, 2243405, 7375001);
/* 136 */     a(EntityWolf.class, "Wolf", 95, 14144467, 13545366);
/* 137 */     a(EntityMushroomCow.class, "MushroomCow", 96, 10489616, 12040119);
/* 138 */     a(EntitySnowman.class, "SnowMan", 97);
/* 139 */     a(EntityOcelot.class, "Ozelot", 98, 15720061, 5653556);
/* 140 */     a(EntityIronGolem.class, "VillagerGolem", 99);
/* 141 */     a(EntityHorse.class, "EntityHorse", 100, 12623485, 15656192);
/* 142 */     a(EntityRabbit.class, "Rabbit", 101, 10051392, 7555121);
/*     */     
/* 144 */     a(EntityVillager.class, "Villager", 120, 5651507, 12422002);
/*     */     
/* 146 */     a(EntityEnderCrystal.class, "EnderCrystal", 200);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public static Entity createEntityByName(String paramString, World paramWorld)
/*     */   {
/* 157 */     Entity localEntity = null;
/*     */     try {
/* 159 */       Class localClass = (Class)c.get(paramString);
/* 160 */       if (localClass != null) {
/* 161 */         localEntity = (Entity)localClass.getConstructor(new Class[] { World.class }).newInstance(new Object[] { paramWorld });
/*     */       }
/*     */     } catch (Exception localException) {
/* 164 */       localException.printStackTrace();
/*     */     }
/* 166 */     return localEntity;
/*     */   }
/*     */   
/*     */   public static Entity a(NBTTagCompound paramNBTTagCompound, World paramWorld)
/*     */   {
/* 171 */     Entity localEntity = null;
/*     */     
/*     */ 
/* 174 */     if ("Minecart".equals(paramNBTTagCompound.getString("id"))) {
/* 175 */       paramNBTTagCompound.setString("id", EntityMinecartAbstract.EnumMinecartType.a(paramNBTTagCompound.getInt("Type")).b());
/* 176 */       paramNBTTagCompound.remove("Type");
/*     */     }
/*     */     try
/*     */     {
/* 180 */       Class localClass = (Class)c.get(paramNBTTagCompound.getString("id"));
/* 181 */       if (localClass != null) {
/* 182 */         localEntity = (Entity)localClass.getConstructor(new Class[] { World.class }).newInstance(new Object[] { paramWorld });
/*     */       }
/*     */     } catch (Exception localException) {
/* 185 */       localException.printStackTrace();
/*     */     }
/* 187 */     if (localEntity != null) {
/* 188 */       localEntity.f(paramNBTTagCompound);
/*     */     } else {
/* 190 */       b.warn("Skipping Entity with id " + paramNBTTagCompound.getString("id"));
/*     */     }
/* 192 */     return localEntity;
/*     */   }
/*     */   
/*     */   public static Entity a(int paramInt, World paramWorld)
/*     */   {
/* 197 */     Entity localEntity = null;
/*     */     try {
/* 199 */       Class localClass = a(paramInt);
/* 200 */       if (localClass != null) {
/* 201 */         localEntity = (Entity)localClass.getConstructor(new Class[] { World.class }).newInstance(new Object[] { paramWorld });
/*     */       }
/*     */     } catch (Exception localException) {
/* 204 */       localException.printStackTrace();
/*     */     }
/* 206 */     if (localEntity == null) {
/* 207 */       b.warn("Skipping Entity with id " + paramInt);
/*     */     }
/* 209 */     return localEntity;
/*     */   }
/*     */   
/*     */   public static int a(Entity paramEntity) {
/* 213 */     Integer localInteger = (Integer)f.get(paramEntity.getClass());
/* 214 */     return localInteger == null ? 0 : localInteger.intValue();
/*     */   }
/*     */   
/*     */   public static Class<? extends Entity> a(int paramInt)
/*     */   {
/* 219 */     return (Class)e.get(Integer.valueOf(paramInt));
/*     */   }
/*     */   
/*     */   public static String b(Entity paramEntity) {
/* 223 */     return (String)d.get(paramEntity.getClass());
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
/*     */   public static String b(int paramInt)
/*     */   {
/* 237 */     return (String)d.get(a(paramInt));
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public static List<String> b()
/*     */   {
/* 245 */     Set localSet = c.keySet();
/* 246 */     ArrayList localArrayList = Lists.newArrayList();
/* 247 */     for (String str : localSet) {
/* 248 */       Class localClass = (Class)c.get(str);
/* 249 */       if ((localClass.getModifiers() & 0x400) != 1024) {
/* 250 */         localArrayList.add(str);
/*     */       }
/*     */     }
/*     */     
/* 254 */     localArrayList.add("LightningBolt");
/* 255 */     return localArrayList;
/*     */   }
/*     */   
/*     */   public static boolean a(Entity paramEntity, String paramString) {
/* 259 */     String str = b(paramEntity);
/* 260 */     if ((str == null) && ((paramEntity instanceof EntityHuman))) {
/* 261 */       str = "Player";
/* 262 */     } else if ((str == null) && ((paramEntity instanceof EntityLightning))) {
/* 263 */       str = "LightningBolt";
/*     */     }
/* 265 */     return paramString.equals(str);
/*     */   }
/*     */   
/*     */ 
/* 269 */   public static boolean b(String paramString) { return ("Player".equals(paramString)) || (b().contains(paramString)); }
/*     */   
/*     */   public static void a() {}
/*     */   
/*     */   public static class MonsterEggInfo {
/*     */     public final int a;
/*     */     public final int b;
/*     */     public final int c;
/*     */     public final Statistic killEntityStatistic;
/*     */     public final Statistic e;
/*     */     
/* 280 */     public MonsterEggInfo(int paramInt1, int paramInt2, int paramInt3) { this.a = paramInt1;
/* 281 */       this.b = paramInt2;
/* 282 */       this.c = paramInt3;
/* 283 */       this.killEntityStatistic = StatisticList.a(this);
/* 284 */       this.e = StatisticList.b(this);
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_8_R3\EntityTypes.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */
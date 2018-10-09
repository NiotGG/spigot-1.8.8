/*     */ package net.minecraft.server.v1_8_R3;
/*     */ 
/*     */ import com.google.common.collect.Lists;
/*     */ import com.google.common.collect.Maps;
/*     */ import com.google.common.collect.Sets;
/*     */ import java.util.Collection;
/*     */ import java.util.HashSet;
/*     */ import java.util.Iterator;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import java.util.Set;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class StatisticList
/*     */ {
/*  23 */   protected static Map<String, Statistic> a = ;
/*     */   
/*  25 */   public static List<Statistic> stats = Lists.newArrayList();
/*  26 */   public static List<Statistic> c = Lists.newArrayList();
/*  27 */   public static List<CraftingStatistic> d = Lists.newArrayList();
/*  28 */   public static List<CraftingStatistic> e = Lists.newArrayList();
/*     */   
/*  30 */   public static Statistic f = new CounterStatistic("stat.leaveGame", new ChatMessage("stat.leaveGame", new Object[0])).i().h();
/*     */   
/*  32 */   public static Statistic g = new CounterStatistic("stat.playOneMinute", new ChatMessage("stat.playOneMinute", new Object[0]), Statistic.h).i().h();
/*  33 */   public static Statistic h = new CounterStatistic("stat.timeSinceDeath", new ChatMessage("stat.timeSinceDeath", new Object[0]), Statistic.h).i().h();
/*     */   
/*  35 */   public static Statistic i = new CounterStatistic("stat.walkOneCm", new ChatMessage("stat.walkOneCm", new Object[0]), Statistic.i).i().h();
/*  36 */   public static Statistic j = new CounterStatistic("stat.crouchOneCm", new ChatMessage("stat.crouchOneCm", new Object[0]), Statistic.i).i().h();
/*  37 */   public static Statistic k = new CounterStatistic("stat.sprintOneCm", new ChatMessage("stat.sprintOneCm", new Object[0]), Statistic.i).i().h();
/*  38 */   public static Statistic l = new CounterStatistic("stat.swimOneCm", new ChatMessage("stat.swimOneCm", new Object[0]), Statistic.i).i().h();
/*  39 */   public static Statistic m = new CounterStatistic("stat.fallOneCm", new ChatMessage("stat.fallOneCm", new Object[0]), Statistic.i).i().h();
/*  40 */   public static Statistic n = new CounterStatistic("stat.climbOneCm", new ChatMessage("stat.climbOneCm", new Object[0]), Statistic.i).i().h();
/*  41 */   public static Statistic o = new CounterStatistic("stat.flyOneCm", new ChatMessage("stat.flyOneCm", new Object[0]), Statistic.i).i().h();
/*  42 */   public static Statistic p = new CounterStatistic("stat.diveOneCm", new ChatMessage("stat.diveOneCm", new Object[0]), Statistic.i).i().h();
/*  43 */   public static Statistic q = new CounterStatistic("stat.minecartOneCm", new ChatMessage("stat.minecartOneCm", new Object[0]), Statistic.i).i().h();
/*  44 */   public static Statistic r = new CounterStatistic("stat.boatOneCm", new ChatMessage("stat.boatOneCm", new Object[0]), Statistic.i).i().h();
/*  45 */   public static Statistic s = new CounterStatistic("stat.pigOneCm", new ChatMessage("stat.pigOneCm", new Object[0]), Statistic.i).i().h();
/*  46 */   public static Statistic t = new CounterStatistic("stat.horseOneCm", new ChatMessage("stat.horseOneCm", new Object[0]), Statistic.i).i().h();
/*     */   
/*  48 */   public static Statistic u = new CounterStatistic("stat.jump", new ChatMessage("stat.jump", new Object[0])).i().h();
/*  49 */   public static Statistic v = new CounterStatistic("stat.drop", new ChatMessage("stat.drop", new Object[0])).i().h();
/*     */   
/*  51 */   public static Statistic w = new CounterStatistic("stat.damageDealt", new ChatMessage("stat.damageDealt", new Object[0]), Statistic.j).h();
/*  52 */   public static Statistic x = new CounterStatistic("stat.damageTaken", new ChatMessage("stat.damageTaken", new Object[0]), Statistic.j).h();
/*  53 */   public static Statistic y = new CounterStatistic("stat.deaths", new ChatMessage("stat.deaths", new Object[0])).h();
/*  54 */   public static Statistic z = new CounterStatistic("stat.mobKills", new ChatMessage("stat.mobKills", new Object[0])).h();
/*  55 */   public static Statistic A = new CounterStatistic("stat.animalsBred", new ChatMessage("stat.animalsBred", new Object[0])).h();
/*  56 */   public static Statistic B = new CounterStatistic("stat.playerKills", new ChatMessage("stat.playerKills", new Object[0])).h();
/*  57 */   public static Statistic C = new CounterStatistic("stat.fishCaught", new ChatMessage("stat.fishCaught", new Object[0])).h();
/*  58 */   public static Statistic D = new CounterStatistic("stat.junkFished", new ChatMessage("stat.junkFished", new Object[0])).h();
/*  59 */   public static Statistic E = new CounterStatistic("stat.treasureFished", new ChatMessage("stat.treasureFished", new Object[0])).h();
/*  60 */   public static Statistic F = new CounterStatistic("stat.talkedToVillager", new ChatMessage("stat.talkedToVillager", new Object[0])).h();
/*  61 */   public static Statistic G = new CounterStatistic("stat.tradedWithVillager", new ChatMessage("stat.tradedWithVillager", new Object[0])).h();
/*     */   
/*  63 */   public static Statistic H = new CounterStatistic("stat.cakeSlicesEaten", new ChatMessage("stat.cakeSlicesEaten", new Object[0])).h();
/*  64 */   public static Statistic I = new CounterStatistic("stat.cauldronFilled", new ChatMessage("stat.cauldronFilled", new Object[0])).h();
/*  65 */   public static Statistic J = new CounterStatistic("stat.cauldronUsed", new ChatMessage("stat.cauldronUsed", new Object[0])).h();
/*  66 */   public static Statistic K = new CounterStatistic("stat.armorCleaned", new ChatMessage("stat.armorCleaned", new Object[0])).h();
/*  67 */   public static Statistic L = new CounterStatistic("stat.bannerCleaned", new ChatMessage("stat.bannerCleaned", new Object[0])).h();
/*  68 */   public static Statistic M = new CounterStatistic("stat.brewingstandInteraction", new ChatMessage("stat.brewingstandInteraction", new Object[0])).h();
/*  69 */   public static Statistic N = new CounterStatistic("stat.beaconInteraction", new ChatMessage("stat.beaconInteraction", new Object[0])).h();
/*  70 */   public static Statistic O = new CounterStatistic("stat.dropperInspected", new ChatMessage("stat.dropperInspected", new Object[0])).h();
/*  71 */   public static Statistic P = new CounterStatistic("stat.hopperInspected", new ChatMessage("stat.hopperInspected", new Object[0])).h();
/*  72 */   public static Statistic Q = new CounterStatistic("stat.dispenserInspected", new ChatMessage("stat.dispenserInspected", new Object[0])).h();
/*  73 */   public static Statistic R = new CounterStatistic("stat.noteblockPlayed", new ChatMessage("stat.noteblockPlayed", new Object[0])).h();
/*  74 */   public static Statistic S = new CounterStatistic("stat.noteblockTuned", new ChatMessage("stat.noteblockTuned", new Object[0])).h();
/*  75 */   public static Statistic T = new CounterStatistic("stat.flowerPotted", new ChatMessage("stat.flowerPotted", new Object[0])).h();
/*  76 */   public static Statistic U = new CounterStatistic("stat.trappedChestTriggered", new ChatMessage("stat.trappedChestTriggered", new Object[0])).h();
/*  77 */   public static Statistic V = new CounterStatistic("stat.enderchestOpened", new ChatMessage("stat.enderchestOpened", new Object[0])).h();
/*  78 */   public static Statistic W = new CounterStatistic("stat.itemEnchanted", new ChatMessage("stat.itemEnchanted", new Object[0])).h();
/*  79 */   public static Statistic X = new CounterStatistic("stat.recordPlayed", new ChatMessage("stat.recordPlayed", new Object[0])).h();
/*  80 */   public static Statistic Y = new CounterStatistic("stat.furnaceInteraction", new ChatMessage("stat.furnaceInteraction", new Object[0])).h();
/*  81 */   public static Statistic Z = new CounterStatistic("stat.craftingTableInteraction", new ChatMessage("stat.workbenchInteraction", new Object[0])).h();
/*  82 */   public static Statistic aa = new CounterStatistic("stat.chestOpened", new ChatMessage("stat.chestOpened", new Object[0])).h();
/*     */   
/*  84 */   public static final Statistic[] MINE_BLOCK_COUNT = new Statistic['က'];
/*  85 */   public static final Statistic[] CRAFT_BLOCK_COUNT = new Statistic['紀'];
/*  86 */   public static final Statistic[] USE_ITEM_COUNT = new Statistic['紀'];
/*  87 */   public static final Statistic[] BREAK_ITEM_COUNT = new Statistic['紀'];
/*     */   
/*     */   public static void a() {
/*  90 */     c();
/*  91 */     d();
/*  92 */     e();
/*  93 */     b();
/*     */     
/*  95 */     AchievementList.a();
/*  96 */     EntityTypes.a();
/*     */   }
/*     */   
/*     */   private static void b() {
/* 100 */     HashSet localHashSet = Sets.newHashSet();
/*     */     
/* 102 */     for (Iterator localIterator = CraftingManager.getInstance().getRecipes().iterator(); localIterator.hasNext();) { localObject = (IRecipe)localIterator.next();
/* 103 */       if (((IRecipe)localObject).b() != null)
/*     */       {
/*     */ 
/* 106 */         localHashSet.add(((IRecipe)localObject).b().getItem()); } }
/*     */     Object localObject;
/* 108 */     for (localIterator = RecipesFurnace.getInstance().getRecipes().values().iterator(); localIterator.hasNext();) { localObject = (ItemStack)localIterator.next();
/* 109 */       localHashSet.add(((ItemStack)localObject).getItem());
/*     */     }
/*     */     
/* 112 */     for (localIterator = localHashSet.iterator(); localIterator.hasNext();) { localObject = (Item)localIterator.next();
/* 113 */       if (localObject != null)
/*     */       {
/*     */ 
/*     */ 
/* 117 */         int i1 = Item.getId((Item)localObject);
/* 118 */         String str = a((Item)localObject);
/* 119 */         if (str != null) {
/* 120 */           CRAFT_BLOCK_COUNT[i1] = new CraftingStatistic("stat.craftItem.", str, new ChatMessage("stat.craftItem", new Object[] { new ItemStack((Item)localObject).C() }), (Item)localObject).h();
/*     */         }
/*     */       }
/*     */     }
/* 124 */     a(CRAFT_BLOCK_COUNT);
/*     */   }
/*     */   
/*     */   private static void c() {
/* 128 */     for (Block localBlock : Block.REGISTRY) {
/* 129 */       Item localItem = Item.getItemOf(localBlock);
/* 130 */       if (localItem != null)
/*     */       {
/*     */ 
/*     */ 
/* 134 */         int i1 = Block.getId(localBlock);
/* 135 */         String str = a(localItem);
/* 136 */         if ((str != null) && (localBlock.J())) {
/* 137 */           MINE_BLOCK_COUNT[i1] = new CraftingStatistic("stat.mineBlock.", str, new ChatMessage("stat.mineBlock", new Object[] { new ItemStack(localBlock).C() }), localItem).h();
/* 138 */           e.add((CraftingStatistic)MINE_BLOCK_COUNT[i1]);
/*     */         }
/*     */       }
/*     */     }
/* 142 */     a(MINE_BLOCK_COUNT);
/*     */   }
/*     */   
/*     */   private static void d() {
/* 146 */     for (Item localItem : Item.REGISTRY) {
/* 147 */       if (localItem != null)
/*     */       {
/*     */ 
/*     */ 
/* 151 */         int i1 = Item.getId(localItem);
/* 152 */         String str = a(localItem);
/* 153 */         if (str != null) {
/* 154 */           USE_ITEM_COUNT[i1] = new CraftingStatistic("stat.useItem.", str, new ChatMessage("stat.useItem", new Object[] { new ItemStack(localItem).C() }), localItem).h();
/*     */           
/* 156 */           if (!(localItem instanceof ItemBlock)) {
/* 157 */             d.add((CraftingStatistic)USE_ITEM_COUNT[i1]);
/*     */           }
/*     */         }
/*     */       }
/*     */     }
/* 162 */     a(USE_ITEM_COUNT);
/*     */   }
/*     */   
/*     */   private static void e() {
/* 166 */     for (Item localItem : Item.REGISTRY) {
/* 167 */       if (localItem != null)
/*     */       {
/*     */ 
/*     */ 
/* 171 */         int i1 = Item.getId(localItem);
/* 172 */         String str = a(localItem);
/* 173 */         if ((str != null) && (localItem.usesDurability())) {
/* 174 */           BREAK_ITEM_COUNT[i1] = new CraftingStatistic("stat.breakItem.", str, new ChatMessage("stat.breakItem", new Object[] { new ItemStack(localItem).C() }), localItem).h();
/*     */         }
/*     */       }
/*     */     }
/* 178 */     a(BREAK_ITEM_COUNT);
/*     */   }
/*     */   
/*     */   private static String a(Item paramItem) {
/* 182 */     MinecraftKey localMinecraftKey = (MinecraftKey)Item.REGISTRY.c(paramItem);
/* 183 */     if (localMinecraftKey != null) {
/* 184 */       return localMinecraftKey.toString().replace(':', '.');
/*     */     }
/* 186 */     return null;
/*     */   }
/*     */   
/*     */   private static void a(Statistic[] paramArrayOfStatistic) {
/* 190 */     a(paramArrayOfStatistic, Blocks.WATER, Blocks.FLOWING_WATER);
/* 191 */     a(paramArrayOfStatistic, Blocks.LAVA, Blocks.FLOWING_LAVA);
/*     */     
/* 193 */     a(paramArrayOfStatistic, Blocks.LIT_PUMPKIN, Blocks.PUMPKIN);
/* 194 */     a(paramArrayOfStatistic, Blocks.LIT_FURNACE, Blocks.FURNACE);
/* 195 */     a(paramArrayOfStatistic, Blocks.LIT_REDSTONE_ORE, Blocks.REDSTONE_ORE);
/*     */     
/* 197 */     a(paramArrayOfStatistic, Blocks.POWERED_REPEATER, Blocks.UNPOWERED_REPEATER);
/* 198 */     a(paramArrayOfStatistic, Blocks.POWERED_COMPARATOR, Blocks.UNPOWERED_COMPARATOR);
/* 199 */     a(paramArrayOfStatistic, Blocks.REDSTONE_TORCH, Blocks.UNLIT_REDSTONE_TORCH);
/* 200 */     a(paramArrayOfStatistic, Blocks.LIT_REDSTONE_LAMP, Blocks.REDSTONE_LAMP);
/*     */     
/* 202 */     a(paramArrayOfStatistic, Blocks.DOUBLE_STONE_SLAB, Blocks.STONE_SLAB);
/* 203 */     a(paramArrayOfStatistic, Blocks.DOUBLE_WOODEN_SLAB, Blocks.WOODEN_SLAB);
/* 204 */     a(paramArrayOfStatistic, Blocks.DOUBLE_STONE_SLAB2, Blocks.STONE_SLAB2);
/*     */     
/* 206 */     a(paramArrayOfStatistic, Blocks.GRASS, Blocks.DIRT);
/* 207 */     a(paramArrayOfStatistic, Blocks.FARMLAND, Blocks.DIRT);
/*     */   }
/*     */   
/*     */   private static void a(Statistic[] paramArrayOfStatistic, Block paramBlock1, Block paramBlock2) {
/* 211 */     int i1 = Block.getId(paramBlock1);
/* 212 */     int i2 = Block.getId(paramBlock2);
/*     */     
/* 214 */     if ((paramArrayOfStatistic[i1] != null) && (paramArrayOfStatistic[i2] == null))
/*     */     {
/* 216 */       paramArrayOfStatistic[i2] = paramArrayOfStatistic[i1];
/* 217 */       return;
/*     */     }
/*     */     
/* 220 */     stats.remove(paramArrayOfStatistic[i1]);
/* 221 */     e.remove(paramArrayOfStatistic[i1]);
/* 222 */     c.remove(paramArrayOfStatistic[i1]);
/* 223 */     paramArrayOfStatistic[i1] = paramArrayOfStatistic[i2];
/*     */   }
/*     */   
/*     */   public static Statistic a(EntityTypes.MonsterEggInfo paramMonsterEggInfo) {
/* 227 */     String str = EntityTypes.b(paramMonsterEggInfo.a);
/* 228 */     if (str == null) {
/* 229 */       return null;
/*     */     }
/* 231 */     return new Statistic("stat.killEntity." + str, new ChatMessage("stat.entityKill", new Object[] { new ChatMessage("entity." + str + ".name", new Object[0]) })).h();
/*     */   }
/*     */   
/*     */   public static Statistic b(EntityTypes.MonsterEggInfo paramMonsterEggInfo) {
/* 235 */     String str = EntityTypes.b(paramMonsterEggInfo.a);
/* 236 */     if (str == null) {
/* 237 */       return null;
/*     */     }
/* 239 */     return new Statistic("stat.entityKilledBy." + str, new ChatMessage("stat.entityKilledBy", new Object[] { new ChatMessage("entity." + str + ".name", new Object[0]) })).h();
/*     */   }
/*     */   
/*     */   public static Statistic getStatistic(String paramString) {
/* 243 */     return (Statistic)a.get(paramString);
/*     */   }
/*     */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_8_R3\StatisticList.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */
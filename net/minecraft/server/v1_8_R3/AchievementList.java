/*    */ package net.minecraft.server.v1_8_R3;
/*    */ 
/*    */ import com.google.common.collect.Lists;
/*    */ import java.util.List;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class AchievementList
/*    */ {
/*    */   public static int a;
/*    */   public static int b;
/*    */   public static int c;
/*    */   public static int d;
/* 18 */   public static List<Achievement> e = ;
/*    */   
/* 20 */   public static Achievement f = new Achievement("achievement.openInventory", "openInventory", 0, 0, Items.BOOK, null).a().c();
/* 21 */   public static Achievement g = new Achievement("achievement.mineWood", "mineWood", 2, 1, Blocks.LOG, f).c();
/* 22 */   public static Achievement h = new Achievement("achievement.buildWorkBench", "buildWorkBench", 4, -1, Blocks.CRAFTING_TABLE, g).c();
/* 23 */   public static Achievement i = new Achievement("achievement.buildPickaxe", "buildPickaxe", 4, 2, Items.WOODEN_PICKAXE, h).c();
/* 24 */   public static Achievement j = new Achievement("achievement.buildFurnace", "buildFurnace", 3, 4, Blocks.FURNACE, i).c();
/* 25 */   public static Achievement k = new Achievement("achievement.acquireIron", "acquireIron", 1, 4, Items.IRON_INGOT, j).c();
/* 26 */   public static Achievement l = new Achievement("achievement.buildHoe", "buildHoe", 2, -3, Items.WOODEN_HOE, h).c();
/* 27 */   public static Achievement m = new Achievement("achievement.makeBread", "makeBread", -1, -3, Items.BREAD, l).c();
/* 28 */   public static Achievement n = new Achievement("achievement.bakeCake", "bakeCake", 0, -5, Items.CAKE, l).c();
/* 29 */   public static Achievement o = new Achievement("achievement.buildBetterPickaxe", "buildBetterPickaxe", 6, 2, Items.STONE_PICKAXE, i).c();
/* 30 */   public static Achievement p = new Achievement("achievement.cookFish", "cookFish", 2, 6, Items.COOKED_FISH, j).c();
/* 31 */   public static Achievement q = new Achievement("achievement.onARail", "onARail", 2, 3, Blocks.RAIL, k).b().c();
/* 32 */   public static Achievement r = new Achievement("achievement.buildSword", "buildSword", 6, -1, Items.WOODEN_SWORD, h).c();
/* 33 */   public static Achievement s = new Achievement("achievement.killEnemy", "killEnemy", 8, -1, Items.BONE, r).c();
/* 34 */   public static Achievement t = new Achievement("achievement.killCow", "killCow", 7, -3, Items.LEATHER, r).c();
/* 35 */   public static Achievement u = new Achievement("achievement.flyPig", "flyPig", 9, -3, Items.SADDLE, t).b().c();
/* 36 */   public static Achievement v = new Achievement("achievement.snipeSkeleton", "snipeSkeleton", 7, 0, Items.BOW, s).b().c();
/* 37 */   public static Achievement w = new Achievement("achievement.diamonds", "diamonds", -1, 5, Blocks.DIAMOND_ORE, k).c();
/* 38 */   public static Achievement x = new Achievement("achievement.diamondsToYou", "diamondsToYou", -1, 2, Items.DIAMOND, w).c();
/* 39 */   public static Achievement y = new Achievement("achievement.portal", "portal", -1, 7, Blocks.OBSIDIAN, w).c();
/* 40 */   public static Achievement z = new Achievement("achievement.ghast", "ghast", -4, 8, Items.GHAST_TEAR, y).b().c();
/* 41 */   public static Achievement A = new Achievement("achievement.blazeRod", "blazeRod", 0, 9, Items.BLAZE_ROD, y).c();
/* 42 */   public static Achievement B = new Achievement("achievement.potion", "potion", 2, 8, Items.POTION, A).c();
/* 43 */   public static Achievement C = new Achievement("achievement.theEnd", "theEnd", 3, 10, Items.ENDER_EYE, A).b().c();
/* 44 */   public static Achievement D = new Achievement("achievement.theEnd2", "theEnd2", 4, 13, Blocks.DRAGON_EGG, C).b().c();
/* 45 */   public static Achievement E = new Achievement("achievement.enchantments", "enchantments", -4, 4, Blocks.ENCHANTING_TABLE, w).c();
/* 46 */   public static Achievement F = new Achievement("achievement.overkill", "overkill", -4, 1, Items.DIAMOND_SWORD, E).b().c();
/* 47 */   public static Achievement G = new Achievement("achievement.bookcase", "bookcase", -3, 6, Blocks.BOOKSHELF, E).c();
/* 48 */   public static Achievement H = new Achievement("achievement.breedCow", "breedCow", 7, -5, Items.WHEAT, t).c();
/* 49 */   public static Achievement I = new Achievement("achievement.spawnWither", "spawnWither", 7, 12, new ItemStack(Items.SKULL, 1, 1), D).c();
/* 50 */   public static Achievement J = new Achievement("achievement.killWither", "killWither", 7, 10, Items.NETHER_STAR, I).c();
/* 51 */   public static Achievement K = new Achievement("achievement.fullBeacon", "fullBeacon", 7, 8, Blocks.BEACON, J).b().c();
/* 52 */   public static Achievement L = new Achievement("achievement.exploreAllBiomes", "exploreAllBiomes", 4, 8, Items.DIAMOND_BOOTS, C).a(AchievementSet.class).b().c();
/* 53 */   public static Achievement M = new Achievement("achievement.overpowered", "overpowered", 6, 4, new ItemStack(Items.GOLDEN_APPLE, 1, 1), o).b().c();
/*    */   
/*    */   public static void a() {}
/*    */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_8_R3\AchievementList.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */
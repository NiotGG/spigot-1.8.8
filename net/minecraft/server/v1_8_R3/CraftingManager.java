/*     */ package net.minecraft.server.v1_8_R3;
/*     */ 
/*     */ import com.google.common.collect.Lists;
/*     */ import com.google.common.collect.Maps;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Collections;
/*     */ import java.util.Comparator;
/*     */ import java.util.HashMap;
/*     */ import java.util.Iterator;
/*     */ import java.util.List;
/*     */ import org.bukkit.craftbukkit.v1_8_R3.event.CraftEventFactory;
/*     */ import org.bukkit.inventory.InventoryView;
/*     */ 
/*     */ public class CraftingManager
/*     */ {
/*  16 */   private static final CraftingManager a = new CraftingManager();
/*  17 */   public List<IRecipe> recipes = Lists.newArrayList();
/*     */   
/*     */   public IRecipe lastRecipe;
/*     */   public InventoryView lastCraftView;
/*     */   
/*     */   public static CraftingManager getInstance()
/*     */   {
/*  24 */     return a;
/*     */   }
/*     */   
/*     */   public CraftingManager() {
/*  28 */     new RecipesTools().a(this);
/*  29 */     new RecipesWeapons().a(this);
/*  30 */     new RecipeIngots().a(this);
/*  31 */     new RecipesFood().a(this);
/*  32 */     new RecipesCrafting().a(this);
/*  33 */     new RecipesArmor().a(this);
/*  34 */     new RecipesDyes().a(this);
/*  35 */     this.recipes.add(new RecipeArmorDye());
/*  36 */     this.recipes.add(new RecipeBookClone());
/*  37 */     this.recipes.add(new RecipeMapClone());
/*  38 */     this.recipes.add(new RecipeMapExtend());
/*  39 */     this.recipes.add(new RecipeFireworks());
/*  40 */     this.recipes.add(new RecipeRepair());
/*  41 */     new RecipesBanner().a(this);
/*  42 */     registerShapedRecipe(new ItemStack(Items.PAPER, 3), new Object[] { "###", Character.valueOf('#'), Items.REEDS });
/*  43 */     registerShapelessRecipe(new ItemStack(Items.BOOK, 1), new Object[] { Items.PAPER, Items.PAPER, Items.PAPER, Items.LEATHER });
/*  44 */     registerShapelessRecipe(new ItemStack(Items.WRITABLE_BOOK, 1), new Object[] { Items.BOOK, new ItemStack(Items.DYE, 1, EnumColor.BLACK.getInvColorIndex()), Items.FEATHER });
/*  45 */     registerShapedRecipe(new ItemStack(Blocks.FENCE, 3), new Object[] { "W#W", "W#W", Character.valueOf('#'), Items.STICK, Character.valueOf('W'), new ItemStack(Blocks.PLANKS, 1, BlockWood.EnumLogVariant.OAK.a()) });
/*  46 */     registerShapedRecipe(new ItemStack(Blocks.BIRCH_FENCE, 3), new Object[] { "W#W", "W#W", Character.valueOf('#'), Items.STICK, Character.valueOf('W'), new ItemStack(Blocks.PLANKS, 1, BlockWood.EnumLogVariant.BIRCH.a()) });
/*  47 */     registerShapedRecipe(new ItemStack(Blocks.SPRUCE_FENCE, 3), new Object[] { "W#W", "W#W", Character.valueOf('#'), Items.STICK, Character.valueOf('W'), new ItemStack(Blocks.PLANKS, 1, BlockWood.EnumLogVariant.SPRUCE.a()) });
/*  48 */     registerShapedRecipe(new ItemStack(Blocks.JUNGLE_FENCE, 3), new Object[] { "W#W", "W#W", Character.valueOf('#'), Items.STICK, Character.valueOf('W'), new ItemStack(Blocks.PLANKS, 1, BlockWood.EnumLogVariant.JUNGLE.a()) });
/*  49 */     registerShapedRecipe(new ItemStack(Blocks.ACACIA_FENCE, 3), new Object[] { "W#W", "W#W", Character.valueOf('#'), Items.STICK, Character.valueOf('W'), new ItemStack(Blocks.PLANKS, 1, 4 + BlockWood.EnumLogVariant.ACACIA.a() - 4) });
/*  50 */     registerShapedRecipe(new ItemStack(Blocks.DARK_OAK_FENCE, 3), new Object[] { "W#W", "W#W", Character.valueOf('#'), Items.STICK, Character.valueOf('W'), new ItemStack(Blocks.PLANKS, 1, 4 + BlockWood.EnumLogVariant.DARK_OAK.a() - 4) });
/*  51 */     registerShapedRecipe(new ItemStack(Blocks.COBBLESTONE_WALL, 6, BlockCobbleWall.EnumCobbleVariant.NORMAL.a()), new Object[] { "###", "###", Character.valueOf('#'), Blocks.COBBLESTONE });
/*  52 */     registerShapedRecipe(new ItemStack(Blocks.COBBLESTONE_WALL, 6, BlockCobbleWall.EnumCobbleVariant.MOSSY.a()), new Object[] { "###", "###", Character.valueOf('#'), Blocks.MOSSY_COBBLESTONE });
/*  53 */     registerShapedRecipe(new ItemStack(Blocks.NETHER_BRICK_FENCE, 6), new Object[] { "###", "###", Character.valueOf('#'), Blocks.NETHER_BRICK });
/*  54 */     registerShapedRecipe(new ItemStack(Blocks.FENCE_GATE, 1), new Object[] { "#W#", "#W#", Character.valueOf('#'), Items.STICK, Character.valueOf('W'), new ItemStack(Blocks.PLANKS, 1, BlockWood.EnumLogVariant.OAK.a()) });
/*  55 */     registerShapedRecipe(new ItemStack(Blocks.BIRCH_FENCE_GATE, 1), new Object[] { "#W#", "#W#", Character.valueOf('#'), Items.STICK, Character.valueOf('W'), new ItemStack(Blocks.PLANKS, 1, BlockWood.EnumLogVariant.BIRCH.a()) });
/*  56 */     registerShapedRecipe(new ItemStack(Blocks.SPRUCE_FENCE_GATE, 1), new Object[] { "#W#", "#W#", Character.valueOf('#'), Items.STICK, Character.valueOf('W'), new ItemStack(Blocks.PLANKS, 1, BlockWood.EnumLogVariant.SPRUCE.a()) });
/*  57 */     registerShapedRecipe(new ItemStack(Blocks.JUNGLE_FENCE_GATE, 1), new Object[] { "#W#", "#W#", Character.valueOf('#'), Items.STICK, Character.valueOf('W'), new ItemStack(Blocks.PLANKS, 1, BlockWood.EnumLogVariant.JUNGLE.a()) });
/*  58 */     registerShapedRecipe(new ItemStack(Blocks.ACACIA_FENCE_GATE, 1), new Object[] { "#W#", "#W#", Character.valueOf('#'), Items.STICK, Character.valueOf('W'), new ItemStack(Blocks.PLANKS, 1, 4 + BlockWood.EnumLogVariant.ACACIA.a() - 4) });
/*  59 */     registerShapedRecipe(new ItemStack(Blocks.DARK_OAK_FENCE_GATE, 1), new Object[] { "#W#", "#W#", Character.valueOf('#'), Items.STICK, Character.valueOf('W'), new ItemStack(Blocks.PLANKS, 1, 4 + BlockWood.EnumLogVariant.DARK_OAK.a() - 4) });
/*  60 */     registerShapedRecipe(new ItemStack(Blocks.JUKEBOX, 1), new Object[] { "###", "#X#", "###", Character.valueOf('#'), Blocks.PLANKS, Character.valueOf('X'), Items.DIAMOND });
/*  61 */     registerShapedRecipe(new ItemStack(Items.LEAD, 2), new Object[] { "~~ ", "~O ", "  ~", Character.valueOf('~'), Items.STRING, Character.valueOf('O'), Items.SLIME });
/*  62 */     registerShapedRecipe(new ItemStack(Blocks.NOTEBLOCK, 1), new Object[] { "###", "#X#", "###", Character.valueOf('#'), Blocks.PLANKS, Character.valueOf('X'), Items.REDSTONE });
/*  63 */     registerShapedRecipe(new ItemStack(Blocks.BOOKSHELF, 1), new Object[] { "###", "XXX", "###", Character.valueOf('#'), Blocks.PLANKS, Character.valueOf('X'), Items.BOOK });
/*  64 */     registerShapedRecipe(new ItemStack(Blocks.SNOW, 1), new Object[] { "##", "##", Character.valueOf('#'), Items.SNOWBALL });
/*  65 */     registerShapedRecipe(new ItemStack(Blocks.SNOW_LAYER, 6), new Object[] { "###", Character.valueOf('#'), Blocks.SNOW });
/*  66 */     registerShapedRecipe(new ItemStack(Blocks.CLAY, 1), new Object[] { "##", "##", Character.valueOf('#'), Items.CLAY_BALL });
/*  67 */     registerShapedRecipe(new ItemStack(Blocks.BRICK_BLOCK, 1), new Object[] { "##", "##", Character.valueOf('#'), Items.BRICK });
/*  68 */     registerShapedRecipe(new ItemStack(Blocks.GLOWSTONE, 1), new Object[] { "##", "##", Character.valueOf('#'), Items.GLOWSTONE_DUST });
/*  69 */     registerShapedRecipe(new ItemStack(Blocks.QUARTZ_BLOCK, 1), new Object[] { "##", "##", Character.valueOf('#'), Items.QUARTZ });
/*  70 */     registerShapedRecipe(new ItemStack(Blocks.WOOL, 1), new Object[] { "##", "##", Character.valueOf('#'), Items.STRING });
/*  71 */     registerShapedRecipe(new ItemStack(Blocks.TNT, 1), new Object[] { "X#X", "#X#", "X#X", Character.valueOf('X'), Items.GUNPOWDER, Character.valueOf('#'), Blocks.SAND });
/*  72 */     registerShapedRecipe(new ItemStack(Blocks.STONE_SLAB, 6, BlockDoubleStepAbstract.EnumStoneSlabVariant.COBBLESTONE.a()), new Object[] { "###", Character.valueOf('#'), Blocks.COBBLESTONE });
/*  73 */     registerShapedRecipe(new ItemStack(Blocks.STONE_SLAB, 6, BlockDoubleStepAbstract.EnumStoneSlabVariant.STONE.a()), new Object[] { "###", Character.valueOf('#'), new ItemStack(Blocks.STONE, BlockStone.EnumStoneVariant.STONE.a()) });
/*  74 */     registerShapedRecipe(new ItemStack(Blocks.STONE_SLAB, 6, BlockDoubleStepAbstract.EnumStoneSlabVariant.SAND.a()), new Object[] { "###", Character.valueOf('#'), Blocks.SANDSTONE });
/*  75 */     registerShapedRecipe(new ItemStack(Blocks.STONE_SLAB, 6, BlockDoubleStepAbstract.EnumStoneSlabVariant.BRICK.a()), new Object[] { "###", Character.valueOf('#'), Blocks.BRICK_BLOCK });
/*  76 */     registerShapedRecipe(new ItemStack(Blocks.STONE_SLAB, 6, BlockDoubleStepAbstract.EnumStoneSlabVariant.SMOOTHBRICK.a()), new Object[] { "###", Character.valueOf('#'), Blocks.STONEBRICK });
/*  77 */     registerShapedRecipe(new ItemStack(Blocks.STONE_SLAB, 6, BlockDoubleStepAbstract.EnumStoneSlabVariant.NETHERBRICK.a()), new Object[] { "###", Character.valueOf('#'), Blocks.NETHER_BRICK });
/*  78 */     registerShapedRecipe(new ItemStack(Blocks.STONE_SLAB, 6, BlockDoubleStepAbstract.EnumStoneSlabVariant.QUARTZ.a()), new Object[] { "###", Character.valueOf('#'), Blocks.QUARTZ_BLOCK });
/*  79 */     registerShapedRecipe(new ItemStack(Blocks.STONE_SLAB2, 6, BlockDoubleStoneStepAbstract.EnumStoneSlab2Variant.RED_SANDSTONE.a()), new Object[] { "###", Character.valueOf('#'), Blocks.RED_SANDSTONE });
/*  80 */     registerShapedRecipe(new ItemStack(Blocks.WOODEN_SLAB, 6, 0), new Object[] { "###", Character.valueOf('#'), new ItemStack(Blocks.PLANKS, 1, BlockWood.EnumLogVariant.OAK.a()) });
/*  81 */     registerShapedRecipe(new ItemStack(Blocks.WOODEN_SLAB, 6, BlockWood.EnumLogVariant.BIRCH.a()), new Object[] { "###", Character.valueOf('#'), new ItemStack(Blocks.PLANKS, 1, BlockWood.EnumLogVariant.BIRCH.a()) });
/*  82 */     registerShapedRecipe(new ItemStack(Blocks.WOODEN_SLAB, 6, BlockWood.EnumLogVariant.SPRUCE.a()), new Object[] { "###", Character.valueOf('#'), new ItemStack(Blocks.PLANKS, 1, BlockWood.EnumLogVariant.SPRUCE.a()) });
/*  83 */     registerShapedRecipe(new ItemStack(Blocks.WOODEN_SLAB, 6, BlockWood.EnumLogVariant.JUNGLE.a()), new Object[] { "###", Character.valueOf('#'), new ItemStack(Blocks.PLANKS, 1, BlockWood.EnumLogVariant.JUNGLE.a()) });
/*  84 */     registerShapedRecipe(new ItemStack(Blocks.WOODEN_SLAB, 6, 4 + BlockWood.EnumLogVariant.ACACIA.a() - 4), new Object[] { "###", Character.valueOf('#'), new ItemStack(Blocks.PLANKS, 1, 4 + BlockWood.EnumLogVariant.ACACIA.a() - 4) });
/*  85 */     registerShapedRecipe(new ItemStack(Blocks.WOODEN_SLAB, 6, 4 + BlockWood.EnumLogVariant.DARK_OAK.a() - 4), new Object[] { "###", Character.valueOf('#'), new ItemStack(Blocks.PLANKS, 1, 4 + BlockWood.EnumLogVariant.DARK_OAK.a() - 4) });
/*  86 */     registerShapedRecipe(new ItemStack(Blocks.LADDER, 3), new Object[] { "# #", "###", "# #", Character.valueOf('#'), Items.STICK });
/*  87 */     registerShapedRecipe(new ItemStack(Items.WOODEN_DOOR, 3), new Object[] { "##", "##", "##", Character.valueOf('#'), new ItemStack(Blocks.PLANKS, 1, BlockWood.EnumLogVariant.OAK.a()) });
/*  88 */     registerShapedRecipe(new ItemStack(Items.SPRUCE_DOOR, 3), new Object[] { "##", "##", "##", Character.valueOf('#'), new ItemStack(Blocks.PLANKS, 1, BlockWood.EnumLogVariant.SPRUCE.a()) });
/*  89 */     registerShapedRecipe(new ItemStack(Items.BIRCH_DOOR, 3), new Object[] { "##", "##", "##", Character.valueOf('#'), new ItemStack(Blocks.PLANKS, 1, BlockWood.EnumLogVariant.BIRCH.a()) });
/*  90 */     registerShapedRecipe(new ItemStack(Items.JUNGLE_DOOR, 3), new Object[] { "##", "##", "##", Character.valueOf('#'), new ItemStack(Blocks.PLANKS, 1, BlockWood.EnumLogVariant.JUNGLE.a()) });
/*  91 */     registerShapedRecipe(new ItemStack(Items.ACACIA_DOOR, 3), new Object[] { "##", "##", "##", Character.valueOf('#'), new ItemStack(Blocks.PLANKS, 1, BlockWood.EnumLogVariant.ACACIA.a()) });
/*  92 */     registerShapedRecipe(new ItemStack(Items.DARK_OAK_DOOR, 3), new Object[] { "##", "##", "##", Character.valueOf('#'), new ItemStack(Blocks.PLANKS, 1, BlockWood.EnumLogVariant.DARK_OAK.a()) });
/*  93 */     registerShapedRecipe(new ItemStack(Blocks.TRAPDOOR, 2), new Object[] { "###", "###", Character.valueOf('#'), Blocks.PLANKS });
/*  94 */     registerShapedRecipe(new ItemStack(Items.IRON_DOOR, 3), new Object[] { "##", "##", "##", Character.valueOf('#'), Items.IRON_INGOT });
/*  95 */     registerShapedRecipe(new ItemStack(Blocks.IRON_TRAPDOOR, 1), new Object[] { "##", "##", Character.valueOf('#'), Items.IRON_INGOT });
/*  96 */     registerShapedRecipe(new ItemStack(Items.SIGN, 3), new Object[] { "###", "###", " X ", Character.valueOf('#'), Blocks.PLANKS, Character.valueOf('X'), Items.STICK });
/*  97 */     registerShapedRecipe(new ItemStack(Items.CAKE, 1), new Object[] { "AAA", "BEB", "CCC", Character.valueOf('A'), Items.MILK_BUCKET, Character.valueOf('B'), Items.SUGAR, Character.valueOf('C'), Items.WHEAT, Character.valueOf('E'), Items.EGG });
/*  98 */     registerShapedRecipe(new ItemStack(Items.SUGAR, 1), new Object[] { "#", Character.valueOf('#'), Items.REEDS });
/*  99 */     registerShapedRecipe(new ItemStack(Blocks.PLANKS, 4, BlockWood.EnumLogVariant.OAK.a()), new Object[] { "#", Character.valueOf('#'), new ItemStack(Blocks.LOG, 1, BlockWood.EnumLogVariant.OAK.a()) });
/* 100 */     registerShapedRecipe(new ItemStack(Blocks.PLANKS, 4, BlockWood.EnumLogVariant.SPRUCE.a()), new Object[] { "#", Character.valueOf('#'), new ItemStack(Blocks.LOG, 1, BlockWood.EnumLogVariant.SPRUCE.a()) });
/* 101 */     registerShapedRecipe(new ItemStack(Blocks.PLANKS, 4, BlockWood.EnumLogVariant.BIRCH.a()), new Object[] { "#", Character.valueOf('#'), new ItemStack(Blocks.LOG, 1, BlockWood.EnumLogVariant.BIRCH.a()) });
/* 102 */     registerShapedRecipe(new ItemStack(Blocks.PLANKS, 4, BlockWood.EnumLogVariant.JUNGLE.a()), new Object[] { "#", Character.valueOf('#'), new ItemStack(Blocks.LOG, 1, BlockWood.EnumLogVariant.JUNGLE.a()) });
/* 103 */     registerShapedRecipe(new ItemStack(Blocks.PLANKS, 4, 4 + BlockWood.EnumLogVariant.ACACIA.a() - 4), new Object[] { "#", Character.valueOf('#'), new ItemStack(Blocks.LOG2, 1, BlockWood.EnumLogVariant.ACACIA.a() - 4) });
/* 104 */     registerShapedRecipe(new ItemStack(Blocks.PLANKS, 4, 4 + BlockWood.EnumLogVariant.DARK_OAK.a() - 4), new Object[] { "#", Character.valueOf('#'), new ItemStack(Blocks.LOG2, 1, BlockWood.EnumLogVariant.DARK_OAK.a() - 4) });
/* 105 */     registerShapedRecipe(new ItemStack(Items.STICK, 4), new Object[] { "#", "#", Character.valueOf('#'), Blocks.PLANKS });
/* 106 */     registerShapedRecipe(new ItemStack(Blocks.TORCH, 4), new Object[] { "X", "#", Character.valueOf('X'), Items.COAL, Character.valueOf('#'), Items.STICK });
/* 107 */     registerShapedRecipe(new ItemStack(Blocks.TORCH, 4), new Object[] { "X", "#", Character.valueOf('X'), new ItemStack(Items.COAL, 1, 1), Character.valueOf('#'), Items.STICK });
/* 108 */     registerShapedRecipe(new ItemStack(Items.BOWL, 4), new Object[] { "# #", " # ", Character.valueOf('#'), Blocks.PLANKS });
/* 109 */     registerShapedRecipe(new ItemStack(Items.GLASS_BOTTLE, 3), new Object[] { "# #", " # ", Character.valueOf('#'), Blocks.GLASS });
/* 110 */     registerShapedRecipe(new ItemStack(Blocks.RAIL, 16), new Object[] { "X X", "X#X", "X X", Character.valueOf('X'), Items.IRON_INGOT, Character.valueOf('#'), Items.STICK });
/* 111 */     registerShapedRecipe(new ItemStack(Blocks.GOLDEN_RAIL, 6), new Object[] { "X X", "X#X", "XRX", Character.valueOf('X'), Items.GOLD_INGOT, Character.valueOf('R'), Items.REDSTONE, Character.valueOf('#'), Items.STICK });
/* 112 */     registerShapedRecipe(new ItemStack(Blocks.ACTIVATOR_RAIL, 6), new Object[] { "XSX", "X#X", "XSX", Character.valueOf('X'), Items.IRON_INGOT, Character.valueOf('#'), Blocks.REDSTONE_TORCH, Character.valueOf('S'), Items.STICK });
/* 113 */     registerShapedRecipe(new ItemStack(Blocks.DETECTOR_RAIL, 6), new Object[] { "X X", "X#X", "XRX", Character.valueOf('X'), Items.IRON_INGOT, Character.valueOf('R'), Items.REDSTONE, Character.valueOf('#'), Blocks.STONE_PRESSURE_PLATE });
/* 114 */     registerShapedRecipe(new ItemStack(Items.MINECART, 1), new Object[] { "# #", "###", Character.valueOf('#'), Items.IRON_INGOT });
/* 115 */     registerShapedRecipe(new ItemStack(Items.CAULDRON, 1), new Object[] { "# #", "# #", "###", Character.valueOf('#'), Items.IRON_INGOT });
/* 116 */     registerShapedRecipe(new ItemStack(Items.BREWING_STAND, 1), new Object[] { " B ", "###", Character.valueOf('#'), Blocks.COBBLESTONE, Character.valueOf('B'), Items.BLAZE_ROD });
/* 117 */     registerShapedRecipe(new ItemStack(Blocks.LIT_PUMPKIN, 1), new Object[] { "A", "B", Character.valueOf('A'), Blocks.PUMPKIN, Character.valueOf('B'), Blocks.TORCH });
/* 118 */     registerShapedRecipe(new ItemStack(Items.CHEST_MINECART, 1), new Object[] { "A", "B", Character.valueOf('A'), Blocks.CHEST, Character.valueOf('B'), Items.MINECART });
/* 119 */     registerShapedRecipe(new ItemStack(Items.FURNACE_MINECART, 1), new Object[] { "A", "B", Character.valueOf('A'), Blocks.FURNACE, Character.valueOf('B'), Items.MINECART });
/* 120 */     registerShapedRecipe(new ItemStack(Items.TNT_MINECART, 1), new Object[] { "A", "B", Character.valueOf('A'), Blocks.TNT, Character.valueOf('B'), Items.MINECART });
/* 121 */     registerShapedRecipe(new ItemStack(Items.HOPPER_MINECART, 1), new Object[] { "A", "B", Character.valueOf('A'), Blocks.HOPPER, Character.valueOf('B'), Items.MINECART });
/* 122 */     registerShapedRecipe(new ItemStack(Items.BOAT, 1), new Object[] { "# #", "###", Character.valueOf('#'), Blocks.PLANKS });
/* 123 */     registerShapedRecipe(new ItemStack(Items.BUCKET, 1), new Object[] { "# #", " # ", Character.valueOf('#'), Items.IRON_INGOT });
/* 124 */     registerShapedRecipe(new ItemStack(Items.FLOWER_POT, 1), new Object[] { "# #", " # ", Character.valueOf('#'), Items.BRICK });
/* 125 */     registerShapelessRecipe(new ItemStack(Items.FLINT_AND_STEEL, 1), new Object[] { new ItemStack(Items.IRON_INGOT, 1), new ItemStack(Items.FLINT, 1) });
/* 126 */     registerShapedRecipe(new ItemStack(Items.BREAD, 1), new Object[] { "###", Character.valueOf('#'), Items.WHEAT });
/* 127 */     registerShapedRecipe(new ItemStack(Blocks.OAK_STAIRS, 4), new Object[] { "#  ", "## ", "###", Character.valueOf('#'), new ItemStack(Blocks.PLANKS, 1, BlockWood.EnumLogVariant.OAK.a()) });
/* 128 */     registerShapedRecipe(new ItemStack(Blocks.BIRCH_STAIRS, 4), new Object[] { "#  ", "## ", "###", Character.valueOf('#'), new ItemStack(Blocks.PLANKS, 1, BlockWood.EnumLogVariant.BIRCH.a()) });
/* 129 */     registerShapedRecipe(new ItemStack(Blocks.SPRUCE_STAIRS, 4), new Object[] { "#  ", "## ", "###", Character.valueOf('#'), new ItemStack(Blocks.PLANKS, 1, BlockWood.EnumLogVariant.SPRUCE.a()) });
/* 130 */     registerShapedRecipe(new ItemStack(Blocks.JUNGLE_STAIRS, 4), new Object[] { "#  ", "## ", "###", Character.valueOf('#'), new ItemStack(Blocks.PLANKS, 1, BlockWood.EnumLogVariant.JUNGLE.a()) });
/* 131 */     registerShapedRecipe(new ItemStack(Blocks.ACACIA_STAIRS, 4), new Object[] { "#  ", "## ", "###", Character.valueOf('#'), new ItemStack(Blocks.PLANKS, 1, 4 + BlockWood.EnumLogVariant.ACACIA.a() - 4) });
/* 132 */     registerShapedRecipe(new ItemStack(Blocks.DARK_OAK_STAIRS, 4), new Object[] { "#  ", "## ", "###", Character.valueOf('#'), new ItemStack(Blocks.PLANKS, 1, 4 + BlockWood.EnumLogVariant.DARK_OAK.a() - 4) });
/* 133 */     registerShapedRecipe(new ItemStack(Items.FISHING_ROD, 1), new Object[] { "  #", " #X", "# X", Character.valueOf('#'), Items.STICK, Character.valueOf('X'), Items.STRING });
/* 134 */     registerShapedRecipe(new ItemStack(Items.CARROT_ON_A_STICK, 1), new Object[] { "# ", " X", Character.valueOf('#'), Items.FISHING_ROD, Character.valueOf('X'), Items.CARROT });
/* 135 */     registerShapedRecipe(new ItemStack(Blocks.STONE_STAIRS, 4), new Object[] { "#  ", "## ", "###", Character.valueOf('#'), Blocks.COBBLESTONE });
/* 136 */     registerShapedRecipe(new ItemStack(Blocks.BRICK_STAIRS, 4), new Object[] { "#  ", "## ", "###", Character.valueOf('#'), Blocks.BRICK_BLOCK });
/* 137 */     registerShapedRecipe(new ItemStack(Blocks.STONE_BRICK_STAIRS, 4), new Object[] { "#  ", "## ", "###", Character.valueOf('#'), Blocks.STONEBRICK });
/* 138 */     registerShapedRecipe(new ItemStack(Blocks.NETHER_BRICK_STAIRS, 4), new Object[] { "#  ", "## ", "###", Character.valueOf('#'), Blocks.NETHER_BRICK });
/* 139 */     registerShapedRecipe(new ItemStack(Blocks.SANDSTONE_STAIRS, 4), new Object[] { "#  ", "## ", "###", Character.valueOf('#'), Blocks.SANDSTONE });
/* 140 */     registerShapedRecipe(new ItemStack(Blocks.RED_SANDSTONE_STAIRS, 4), new Object[] { "#  ", "## ", "###", Character.valueOf('#'), Blocks.RED_SANDSTONE });
/* 141 */     registerShapedRecipe(new ItemStack(Blocks.QUARTZ_STAIRS, 4), new Object[] { "#  ", "## ", "###", Character.valueOf('#'), Blocks.QUARTZ_BLOCK });
/* 142 */     registerShapedRecipe(new ItemStack(Items.PAINTING, 1), new Object[] { "###", "#X#", "###", Character.valueOf('#'), Items.STICK, Character.valueOf('X'), Blocks.WOOL });
/* 143 */     registerShapedRecipe(new ItemStack(Items.ITEM_FRAME, 1), new Object[] { "###", "#X#", "###", Character.valueOf('#'), Items.STICK, Character.valueOf('X'), Items.LEATHER });
/* 144 */     registerShapedRecipe(new ItemStack(Items.GOLDEN_APPLE, 1, 0), new Object[] { "###", "#X#", "###", Character.valueOf('#'), Items.GOLD_INGOT, Character.valueOf('X'), Items.APPLE });
/* 145 */     registerShapedRecipe(new ItemStack(Items.GOLDEN_APPLE, 1, 1), new Object[] { "###", "#X#", "###", Character.valueOf('#'), Blocks.GOLD_BLOCK, Character.valueOf('X'), Items.APPLE });
/* 146 */     registerShapedRecipe(new ItemStack(Items.GOLDEN_CARROT, 1, 0), new Object[] { "###", "#X#", "###", Character.valueOf('#'), Items.GOLD_NUGGET, Character.valueOf('X'), Items.CARROT });
/* 147 */     registerShapedRecipe(new ItemStack(Items.SPECKLED_MELON, 1), new Object[] { "###", "#X#", "###", Character.valueOf('#'), Items.GOLD_NUGGET, Character.valueOf('X'), Items.MELON });
/* 148 */     registerShapedRecipe(new ItemStack(Blocks.LEVER, 1), new Object[] { "X", "#", Character.valueOf('#'), Blocks.COBBLESTONE, Character.valueOf('X'), Items.STICK });
/* 149 */     registerShapedRecipe(new ItemStack(Blocks.TRIPWIRE_HOOK, 2), new Object[] { "I", "S", "#", Character.valueOf('#'), Blocks.PLANKS, Character.valueOf('S'), Items.STICK, Character.valueOf('I'), Items.IRON_INGOT });
/* 150 */     registerShapedRecipe(new ItemStack(Blocks.REDSTONE_TORCH, 1), new Object[] { "X", "#", Character.valueOf('#'), Items.STICK, Character.valueOf('X'), Items.REDSTONE });
/* 151 */     registerShapedRecipe(new ItemStack(Items.REPEATER, 1), new Object[] { "#X#", "III", Character.valueOf('#'), Blocks.REDSTONE_TORCH, Character.valueOf('X'), Items.REDSTONE, Character.valueOf('I'), new ItemStack(Blocks.STONE, 1, BlockStone.EnumStoneVariant.STONE.a()) });
/* 152 */     registerShapedRecipe(new ItemStack(Items.COMPARATOR, 1), new Object[] { " # ", "#X#", "III", Character.valueOf('#'), Blocks.REDSTONE_TORCH, Character.valueOf('X'), Items.QUARTZ, Character.valueOf('I'), new ItemStack(Blocks.STONE, 1, BlockStone.EnumStoneVariant.STONE.a()) });
/* 153 */     registerShapedRecipe(new ItemStack(Items.CLOCK, 1), new Object[] { " # ", "#X#", " # ", Character.valueOf('#'), Items.GOLD_INGOT, Character.valueOf('X'), Items.REDSTONE });
/* 154 */     registerShapedRecipe(new ItemStack(Items.COMPASS, 1), new Object[] { " # ", "#X#", " # ", Character.valueOf('#'), Items.IRON_INGOT, Character.valueOf('X'), Items.REDSTONE });
/* 155 */     registerShapedRecipe(new ItemStack(Items.MAP, 1), new Object[] { "###", "#X#", "###", Character.valueOf('#'), Items.PAPER, Character.valueOf('X'), Items.COMPASS });
/* 156 */     registerShapedRecipe(new ItemStack(Blocks.STONE_BUTTON, 1), new Object[] { "#", Character.valueOf('#'), new ItemStack(Blocks.STONE, 1, BlockStone.EnumStoneVariant.STONE.a()) });
/* 157 */     registerShapedRecipe(new ItemStack(Blocks.WOODEN_BUTTON, 1), new Object[] { "#", Character.valueOf('#'), Blocks.PLANKS });
/* 158 */     registerShapedRecipe(new ItemStack(Blocks.STONE_PRESSURE_PLATE, 1), new Object[] { "##", Character.valueOf('#'), new ItemStack(Blocks.STONE, 1, BlockStone.EnumStoneVariant.STONE.a()) });
/* 159 */     registerShapedRecipe(new ItemStack(Blocks.WOODEN_PRESSURE_PLATE, 1), new Object[] { "##", Character.valueOf('#'), Blocks.PLANKS });
/* 160 */     registerShapedRecipe(new ItemStack(Blocks.HEAVY_WEIGHTED_PRESSURE_PLATE, 1), new Object[] { "##", Character.valueOf('#'), Items.IRON_INGOT });
/* 161 */     registerShapedRecipe(new ItemStack(Blocks.LIGHT_WEIGHTED_PRESSURE_PLATE, 1), new Object[] { "##", Character.valueOf('#'), Items.GOLD_INGOT });
/* 162 */     registerShapedRecipe(new ItemStack(Blocks.DISPENSER, 1), new Object[] { "###", "#X#", "#R#", Character.valueOf('#'), Blocks.COBBLESTONE, Character.valueOf('X'), Items.BOW, Character.valueOf('R'), Items.REDSTONE });
/* 163 */     registerShapedRecipe(new ItemStack(Blocks.DROPPER, 1), new Object[] { "###", "# #", "#R#", Character.valueOf('#'), Blocks.COBBLESTONE, Character.valueOf('R'), Items.REDSTONE });
/* 164 */     registerShapedRecipe(new ItemStack(Blocks.PISTON, 1), new Object[] { "TTT", "#X#", "#R#", Character.valueOf('#'), Blocks.COBBLESTONE, Character.valueOf('X'), Items.IRON_INGOT, Character.valueOf('R'), Items.REDSTONE, Character.valueOf('T'), Blocks.PLANKS });
/* 165 */     registerShapedRecipe(new ItemStack(Blocks.STICKY_PISTON, 1), new Object[] { "S", "P", Character.valueOf('S'), Items.SLIME, Character.valueOf('P'), Blocks.PISTON });
/* 166 */     registerShapedRecipe(new ItemStack(Items.BED, 1), new Object[] { "###", "XXX", Character.valueOf('#'), Blocks.WOOL, Character.valueOf('X'), Blocks.PLANKS });
/* 167 */     registerShapedRecipe(new ItemStack(Blocks.ENCHANTING_TABLE, 1), new Object[] { " B ", "D#D", "###", Character.valueOf('#'), Blocks.OBSIDIAN, Character.valueOf('B'), Items.BOOK, Character.valueOf('D'), Items.DIAMOND });
/* 168 */     registerShapedRecipe(new ItemStack(Blocks.ANVIL, 1), new Object[] { "III", " i ", "iii", Character.valueOf('I'), Blocks.IRON_BLOCK, Character.valueOf('i'), Items.IRON_INGOT });
/* 169 */     registerShapedRecipe(new ItemStack(Items.LEATHER), new Object[] { "##", "##", Character.valueOf('#'), Items.RABBIT_HIDE });
/* 170 */     registerShapelessRecipe(new ItemStack(Items.ENDER_EYE, 1), new Object[] { Items.ENDER_PEARL, Items.BLAZE_POWDER });
/* 171 */     registerShapelessRecipe(new ItemStack(Items.FIRE_CHARGE, 3), new Object[] { Items.GUNPOWDER, Items.BLAZE_POWDER, Items.COAL });
/* 172 */     registerShapelessRecipe(new ItemStack(Items.FIRE_CHARGE, 3), new Object[] { Items.GUNPOWDER, Items.BLAZE_POWDER, new ItemStack(Items.COAL, 1, 1) });
/* 173 */     registerShapedRecipe(new ItemStack(Blocks.DAYLIGHT_DETECTOR), new Object[] { "GGG", "QQQ", "WWW", Character.valueOf('G'), Blocks.GLASS, Character.valueOf('Q'), Items.QUARTZ, Character.valueOf('W'), Blocks.WOODEN_SLAB });
/* 174 */     registerShapedRecipe(new ItemStack(Blocks.HOPPER), new Object[] { "I I", "ICI", " I ", Character.valueOf('I'), Items.IRON_INGOT, Character.valueOf('C'), Blocks.CHEST });
/* 175 */     registerShapedRecipe(new ItemStack(Items.ARMOR_STAND, 1), new Object[] { "///", " / ", "/_/", Character.valueOf('/'), Items.STICK, Character.valueOf('_'), new ItemStack(Blocks.STONE_SLAB, 1, BlockDoubleStepAbstract.EnumStoneSlabVariant.STONE.a()) });
/* 176 */     sort();
/*     */   }
/*     */   
/*     */   public void sort()
/*     */   {
/* 181 */     Collections.sort(this.recipes, new Comparator() {
/*     */       public int a(IRecipe irecipe, IRecipe irecipe1) {
/* 183 */         return irecipe1.a() > irecipe.a() ? 1 : irecipe1.a() < irecipe.a() ? -1 : ((irecipe1 instanceof ShapelessRecipes)) && ((irecipe instanceof ShapedRecipes)) ? -1 : ((irecipe instanceof ShapelessRecipes)) && ((irecipe1 instanceof ShapedRecipes)) ? 1 : 0;
/*     */       }
/*     */       
/*     */       public int compare(Object object, Object object1) {
/* 187 */         return a((IRecipe)object, (IRecipe)object1);
/*     */       }
/*     */     });
/*     */   }
/*     */   
/*     */   public ShapedRecipes registerShapedRecipe(ItemStack itemstack, Object... aobject) {
/* 193 */     String s = "";
/* 194 */     int i = 0;
/* 195 */     int j = 0;
/* 196 */     int k = 0;
/*     */     
/* 198 */     if ((aobject[i] instanceof String[])) {
/* 199 */       astring = (String[])aobject[(i++)];
/*     */       
/* 201 */       for (l = 0; l < astring.length; l++) {
/* 202 */         s1 = astring[l];
/*     */         
/* 204 */         k++;
/* 205 */         j = s1.length();
/* 206 */         s = s + s1;
/*     */       }
/*     */     } else {
/* 209 */       while ((aobject[i] instanceof String)) { String[] astring;
/* 210 */         int l; String s1; String s2 = (String)aobject[(i++)];
/*     */         
/* 212 */         k++;
/* 213 */         j = s2.length();
/* 214 */         s = s + s2;
/*     */       }
/*     */     }
/*     */     
/*     */ 
/*     */ 
/* 220 */     for (HashMap hashmap = Maps.newHashMap(); i < aobject.length; i += 2) {
/* 221 */       Character character = (Character)aobject[i];
/* 222 */       ItemStack itemstack1 = null;
/*     */       
/* 224 */       if ((aobject[(i + 1)] instanceof Item)) {
/* 225 */         itemstack1 = new ItemStack((Item)aobject[(i + 1)]);
/* 226 */       } else if ((aobject[(i + 1)] instanceof Block)) {
/* 227 */         itemstack1 = new ItemStack((Block)aobject[(i + 1)], 1, 32767);
/* 228 */       } else if ((aobject[(i + 1)] instanceof ItemStack)) {
/* 229 */         itemstack1 = (ItemStack)aobject[(i + 1)];
/*     */       }
/*     */       
/* 232 */       hashmap.put(character, itemstack1);
/*     */     }
/*     */     
/* 235 */     ItemStack[] aitemstack = new ItemStack[j * k];
/*     */     
/* 237 */     for (int i1 = 0; i1 < j * k; i1++) {
/* 238 */       char c0 = s.charAt(i1);
/*     */       
/* 240 */       if (hashmap.containsKey(Character.valueOf(c0))) {
/* 241 */         aitemstack[i1] = ((ItemStack)hashmap.get(Character.valueOf(c0))).cloneItemStack();
/*     */       } else {
/* 243 */         aitemstack[i1] = null;
/*     */       }
/*     */     }
/*     */     
/* 247 */     ShapedRecipes shapedrecipes = new ShapedRecipes(j, k, aitemstack, itemstack);
/*     */     
/* 249 */     this.recipes.add(shapedrecipes);
/* 250 */     return shapedrecipes;
/*     */   }
/*     */   
/*     */   public void registerShapelessRecipe(ItemStack itemstack, Object... aobject) {
/* 254 */     ArrayList arraylist = Lists.newArrayList();
/* 255 */     Object[] aobject1 = aobject;
/* 256 */     int i = aobject.length;
/*     */     
/* 258 */     for (int j = 0; j < i; j++) {
/* 259 */       Object object = aobject1[j];
/*     */       
/* 261 */       if ((object instanceof ItemStack)) {
/* 262 */         arraylist.add(((ItemStack)object).cloneItemStack());
/* 263 */       } else if ((object instanceof Item)) {
/* 264 */         arraylist.add(new ItemStack((Item)object));
/*     */       } else {
/* 266 */         if (!(object instanceof Block)) {
/* 267 */           throw new IllegalArgumentException("Invalid shapeless recipe: unknown type " + object.getClass().getName() + "!");
/*     */         }
/*     */         
/* 270 */         arraylist.add(new ItemStack((Block)object));
/*     */       }
/*     */     }
/*     */     
/* 274 */     this.recipes.add(new ShapelessRecipes(itemstack, arraylist));
/*     */   }
/*     */   
/*     */   public void a(IRecipe irecipe) {
/* 278 */     this.recipes.add(irecipe);
/*     */   }
/*     */   
/*     */   public ItemStack craft(InventoryCrafting inventorycrafting, World world) {
/* 282 */     Iterator iterator = this.recipes.iterator();
/*     */     
/*     */     IRecipe irecipe;
/*     */     do
/*     */     {
/* 287 */       if (!iterator.hasNext()) {
/* 288 */         inventorycrafting.currentRecipe = null;
/* 289 */         return null;
/*     */       }
/*     */       
/* 292 */       irecipe = (IRecipe)iterator.next();
/* 293 */     } while (!irecipe.a(inventorycrafting, world));
/*     */     
/*     */ 
/* 296 */     inventorycrafting.currentRecipe = irecipe;
/* 297 */     ItemStack result = irecipe.craftItem(inventorycrafting);
/* 298 */     return CraftEventFactory.callPreCraftEvent(inventorycrafting, result, this.lastCraftView, false);
/*     */   }
/*     */   
/*     */   public ItemStack[] b(InventoryCrafting inventorycrafting, World world)
/*     */   {
/* 303 */     Iterator iterator = this.recipes.iterator();
/*     */     
/* 305 */     while (iterator.hasNext()) {
/* 306 */       IRecipe irecipe = (IRecipe)iterator.next();
/*     */       
/* 308 */       if (irecipe.a(inventorycrafting, world)) {
/* 309 */         return irecipe.b(inventorycrafting);
/*     */       }
/*     */     }
/*     */     
/* 313 */     ItemStack[] aitemstack = new ItemStack[inventorycrafting.getSize()];
/*     */     
/* 315 */     for (int i = 0; i < aitemstack.length; i++) {
/* 316 */       aitemstack[i] = inventorycrafting.getItem(i);
/*     */     }
/*     */     
/* 319 */     return aitemstack;
/*     */   }
/*     */   
/*     */   public List<IRecipe> getRecipes() {
/* 323 */     return this.recipes;
/*     */   }
/*     */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_8_R3\CraftingManager.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */
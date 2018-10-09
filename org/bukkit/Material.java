/*      */ package org.bukkit;
/*      */ 
/*      */ import com.google.common.collect.Maps;
/*      */ import java.lang.reflect.Constructor;
/*      */ import java.util.Map;
/*      */ import org.apache.commons.lang.Validate;
/*      */ import org.bukkit.material.Banner;
/*      */ import org.bukkit.material.Bed;
/*      */ import org.bukkit.material.Button;
/*      */ import org.bukkit.material.Cake;
/*      */ import org.bukkit.material.Cauldron;
/*      */ import org.bukkit.material.Chest;
/*      */ import org.bukkit.material.Coal;
/*      */ import org.bukkit.material.CocoaPlant;
/*      */ import org.bukkit.material.Command;
/*      */ import org.bukkit.material.Crops;
/*      */ import org.bukkit.material.DetectorRail;
/*      */ import org.bukkit.material.Diode;
/*      */ import org.bukkit.material.Dispenser;
/*      */ import org.bukkit.material.Door;
/*      */ import org.bukkit.material.Dye;
/*      */ import org.bukkit.material.EnderChest;
/*      */ import org.bukkit.material.FlowerPot;
/*      */ import org.bukkit.material.Furnace;
/*      */ import org.bukkit.material.Gate;
/*      */ import org.bukkit.material.Ladder;
/*      */ import org.bukkit.material.Lever;
/*      */ import org.bukkit.material.LongGrass;
/*      */ import org.bukkit.material.MaterialData;
/*      */ import org.bukkit.material.MonsterEggs;
/*      */ import org.bukkit.material.Mushroom;
/*      */ import org.bukkit.material.NetherWarts;
/*      */ import org.bukkit.material.PistonBaseMaterial;
/*      */ import org.bukkit.material.PistonExtensionMaterial;
/*      */ import org.bukkit.material.PoweredRail;
/*      */ import org.bukkit.material.PressurePlate;
/*      */ import org.bukkit.material.Pumpkin;
/*      */ import org.bukkit.material.Rails;
/*      */ import org.bukkit.material.RedstoneTorch;
/*      */ import org.bukkit.material.RedstoneWire;
/*      */ import org.bukkit.material.Sandstone;
/*      */ import org.bukkit.material.Sign;
/*      */ import org.bukkit.material.Skull;
/*      */ import org.bukkit.material.SmoothBrick;
/*      */ import org.bukkit.material.SpawnEgg;
/*      */ import org.bukkit.material.Stairs;
/*      */ import org.bukkit.material.Step;
/*      */ import org.bukkit.material.Torch;
/*      */ import org.bukkit.material.TrapDoor;
/*      */ import org.bukkit.material.Tree;
/*      */ import org.bukkit.material.Tripwire;
/*      */ import org.bukkit.material.TripwireHook;
/*      */ import org.bukkit.material.Vine;
/*      */ import org.bukkit.material.WoodenStep;
/*      */ import org.bukkit.material.Wool;
/*      */ import org.bukkit.util.Java15Compat;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ public enum Material
/*      */ {
/*   66 */   AIR(0, 0), 
/*   67 */   STONE(1), 
/*   68 */   GRASS(2), 
/*   69 */   DIRT(3), 
/*   70 */   COBBLESTONE(4), 
/*   71 */   WOOD(5, Tree.class), 
/*   72 */   SAPLING(6, Tree.class), 
/*   73 */   BEDROCK(7), 
/*   74 */   WATER(8, MaterialData.class), 
/*   75 */   STATIONARY_WATER(9, MaterialData.class), 
/*   76 */   LAVA(10, MaterialData.class), 
/*   77 */   STATIONARY_LAVA(11, MaterialData.class), 
/*   78 */   SAND(12), 
/*   79 */   GRAVEL(13), 
/*   80 */   GOLD_ORE(14), 
/*   81 */   IRON_ORE(15), 
/*   82 */   COAL_ORE(16), 
/*   83 */   LOG(17, Tree.class), 
/*   84 */   LEAVES(18, Tree.class), 
/*   85 */   SPONGE(19), 
/*   86 */   GLASS(20), 
/*   87 */   LAPIS_ORE(21), 
/*   88 */   LAPIS_BLOCK(22), 
/*   89 */   DISPENSER(23, Dispenser.class), 
/*   90 */   SANDSTONE(24, Sandstone.class), 
/*   91 */   NOTE_BLOCK(25), 
/*   92 */   BED_BLOCK(26, Bed.class), 
/*   93 */   POWERED_RAIL(27, PoweredRail.class), 
/*   94 */   DETECTOR_RAIL(28, DetectorRail.class), 
/*   95 */   PISTON_STICKY_BASE(29, PistonBaseMaterial.class), 
/*   96 */   WEB(30), 
/*   97 */   LONG_GRASS(31, LongGrass.class), 
/*   98 */   DEAD_BUSH(32), 
/*   99 */   PISTON_BASE(33, PistonBaseMaterial.class), 
/*  100 */   PISTON_EXTENSION(34, PistonExtensionMaterial.class), 
/*  101 */   WOOL(35, Wool.class), 
/*  102 */   PISTON_MOVING_PIECE(36), 
/*  103 */   YELLOW_FLOWER(37), 
/*  104 */   RED_ROSE(38), 
/*  105 */   BROWN_MUSHROOM(39), 
/*  106 */   RED_MUSHROOM(40), 
/*  107 */   GOLD_BLOCK(41), 
/*  108 */   IRON_BLOCK(42), 
/*  109 */   DOUBLE_STEP(43, Step.class), 
/*  110 */   STEP(44, Step.class), 
/*  111 */   BRICK(45), 
/*  112 */   TNT(46), 
/*  113 */   BOOKSHELF(47), 
/*  114 */   MOSSY_COBBLESTONE(48), 
/*  115 */   OBSIDIAN(49), 
/*  116 */   TORCH(50, Torch.class), 
/*  117 */   FIRE(51), 
/*  118 */   MOB_SPAWNER(52), 
/*  119 */   WOOD_STAIRS(53, Stairs.class), 
/*  120 */   CHEST(54, Chest.class), 
/*  121 */   REDSTONE_WIRE(55, RedstoneWire.class), 
/*  122 */   DIAMOND_ORE(56), 
/*  123 */   DIAMOND_BLOCK(57), 
/*  124 */   WORKBENCH(58), 
/*  125 */   CROPS(59, Crops.class), 
/*  126 */   SOIL(60, MaterialData.class), 
/*  127 */   FURNACE(61, Furnace.class), 
/*  128 */   BURNING_FURNACE(62, Furnace.class), 
/*  129 */   SIGN_POST(63, 64, Sign.class), 
/*  130 */   WOODEN_DOOR(64, Door.class), 
/*  131 */   LADDER(65, Ladder.class), 
/*  132 */   RAILS(66, Rails.class), 
/*  133 */   COBBLESTONE_STAIRS(67, Stairs.class), 
/*  134 */   WALL_SIGN(68, 64, Sign.class), 
/*  135 */   LEVER(69, Lever.class), 
/*  136 */   STONE_PLATE(70, PressurePlate.class), 
/*  137 */   IRON_DOOR_BLOCK(71, Door.class), 
/*  138 */   WOOD_PLATE(72, PressurePlate.class), 
/*  139 */   REDSTONE_ORE(73), 
/*  140 */   GLOWING_REDSTONE_ORE(74), 
/*  141 */   REDSTONE_TORCH_OFF(75, RedstoneTorch.class), 
/*  142 */   REDSTONE_TORCH_ON(76, RedstoneTorch.class), 
/*  143 */   STONE_BUTTON(77, Button.class), 
/*  144 */   SNOW(78), 
/*  145 */   ICE(79), 
/*  146 */   SNOW_BLOCK(80), 
/*  147 */   CACTUS(81, MaterialData.class), 
/*  148 */   CLAY(82), 
/*  149 */   SUGAR_CANE_BLOCK(83, MaterialData.class), 
/*  150 */   JUKEBOX(84), 
/*  151 */   FENCE(85), 
/*  152 */   PUMPKIN(86, Pumpkin.class), 
/*  153 */   NETHERRACK(87), 
/*  154 */   SOUL_SAND(88), 
/*  155 */   GLOWSTONE(89), 
/*  156 */   PORTAL(90), 
/*  157 */   JACK_O_LANTERN(91, Pumpkin.class), 
/*  158 */   CAKE_BLOCK(92, 64, Cake.class), 
/*  159 */   DIODE_BLOCK_OFF(93, Diode.class), 
/*  160 */   DIODE_BLOCK_ON(94, Diode.class), 
/*  161 */   STAINED_GLASS(95), 
/*  162 */   TRAP_DOOR(96, TrapDoor.class), 
/*  163 */   MONSTER_EGGS(97, MonsterEggs.class), 
/*  164 */   SMOOTH_BRICK(98, SmoothBrick.class), 
/*  165 */   HUGE_MUSHROOM_1(99, Mushroom.class), 
/*  166 */   HUGE_MUSHROOM_2(100, Mushroom.class), 
/*  167 */   IRON_FENCE(101), 
/*  168 */   THIN_GLASS(102), 
/*  169 */   MELON_BLOCK(103), 
/*  170 */   PUMPKIN_STEM(104, MaterialData.class), 
/*  171 */   MELON_STEM(105, MaterialData.class), 
/*  172 */   VINE(106, Vine.class), 
/*  173 */   FENCE_GATE(107, Gate.class), 
/*  174 */   BRICK_STAIRS(108, Stairs.class), 
/*  175 */   SMOOTH_STAIRS(109, Stairs.class), 
/*  176 */   MYCEL(110), 
/*  177 */   WATER_LILY(111), 
/*  178 */   NETHER_BRICK(112), 
/*  179 */   NETHER_FENCE(113), 
/*  180 */   NETHER_BRICK_STAIRS(114, Stairs.class), 
/*  181 */   NETHER_WARTS(115, NetherWarts.class), 
/*  182 */   ENCHANTMENT_TABLE(116), 
/*  183 */   BREWING_STAND(117, MaterialData.class), 
/*  184 */   CAULDRON(118, Cauldron.class), 
/*  185 */   ENDER_PORTAL(119), 
/*  186 */   ENDER_PORTAL_FRAME(120), 
/*  187 */   ENDER_STONE(121), 
/*  188 */   DRAGON_EGG(122), 
/*  189 */   REDSTONE_LAMP_OFF(123), 
/*  190 */   REDSTONE_LAMP_ON(124), 
/*  191 */   WOOD_DOUBLE_STEP(125, WoodenStep.class), 
/*  192 */   WOOD_STEP(126, WoodenStep.class), 
/*  193 */   COCOA(127, CocoaPlant.class), 
/*  194 */   SANDSTONE_STAIRS(128, Stairs.class), 
/*  195 */   EMERALD_ORE(129), 
/*  196 */   ENDER_CHEST(130, EnderChest.class), 
/*  197 */   TRIPWIRE_HOOK(131, TripwireHook.class), 
/*  198 */   TRIPWIRE(132, Tripwire.class), 
/*  199 */   EMERALD_BLOCK(133), 
/*  200 */   SPRUCE_WOOD_STAIRS(134, Stairs.class), 
/*  201 */   BIRCH_WOOD_STAIRS(135, Stairs.class), 
/*  202 */   JUNGLE_WOOD_STAIRS(136, Stairs.class), 
/*  203 */   COMMAND(137, Command.class), 
/*  204 */   BEACON(138), 
/*  205 */   COBBLE_WALL(139), 
/*  206 */   FLOWER_POT(140, FlowerPot.class), 
/*  207 */   CARROT(141), 
/*  208 */   POTATO(142), 
/*  209 */   WOOD_BUTTON(143, Button.class), 
/*  210 */   SKULL(144, Skull.class), 
/*  211 */   ANVIL(145), 
/*  212 */   TRAPPED_CHEST(146, Chest.class), 
/*  213 */   GOLD_PLATE(147), 
/*  214 */   IRON_PLATE(148), 
/*  215 */   REDSTONE_COMPARATOR_OFF(149), 
/*  216 */   REDSTONE_COMPARATOR_ON(150), 
/*  217 */   DAYLIGHT_DETECTOR(151), 
/*  218 */   REDSTONE_BLOCK(152), 
/*  219 */   QUARTZ_ORE(153), 
/*  220 */   HOPPER(154), 
/*  221 */   QUARTZ_BLOCK(155), 
/*  222 */   QUARTZ_STAIRS(156, Stairs.class), 
/*  223 */   ACTIVATOR_RAIL(157, PoweredRail.class), 
/*  224 */   DROPPER(158, Dispenser.class), 
/*  225 */   STAINED_CLAY(159), 
/*  226 */   STAINED_GLASS_PANE(160), 
/*  227 */   LEAVES_2(161), 
/*  228 */   LOG_2(162), 
/*  229 */   ACACIA_STAIRS(163, Stairs.class), 
/*  230 */   DARK_OAK_STAIRS(164, Stairs.class), 
/*  231 */   SLIME_BLOCK(165), 
/*  232 */   BARRIER(166), 
/*  233 */   IRON_TRAPDOOR(167, TrapDoor.class), 
/*  234 */   PRISMARINE(168), 
/*  235 */   SEA_LANTERN(169), 
/*  236 */   HAY_BLOCK(170), 
/*  237 */   CARPET(171), 
/*  238 */   HARD_CLAY(172), 
/*  239 */   COAL_BLOCK(173), 
/*  240 */   PACKED_ICE(174), 
/*  241 */   DOUBLE_PLANT(175), 
/*  242 */   STANDING_BANNER(176, Banner.class), 
/*  243 */   WALL_BANNER(177, Banner.class), 
/*  244 */   DAYLIGHT_DETECTOR_INVERTED(178), 
/*  245 */   RED_SANDSTONE(179), 
/*  246 */   RED_SANDSTONE_STAIRS(180, Stairs.class), 
/*  247 */   DOUBLE_STONE_SLAB2(181), 
/*  248 */   STONE_SLAB2(182), 
/*  249 */   SPRUCE_FENCE_GATE(183, Gate.class), 
/*  250 */   BIRCH_FENCE_GATE(184, Gate.class), 
/*  251 */   JUNGLE_FENCE_GATE(185, Gate.class), 
/*  252 */   DARK_OAK_FENCE_GATE(186, Gate.class), 
/*  253 */   ACACIA_FENCE_GATE(187, Gate.class), 
/*  254 */   SPRUCE_FENCE(188), 
/*  255 */   BIRCH_FENCE(189), 
/*  256 */   JUNGLE_FENCE(190), 
/*  257 */   DARK_OAK_FENCE(191), 
/*  258 */   ACACIA_FENCE(192), 
/*  259 */   SPRUCE_DOOR(193, Door.class), 
/*  260 */   BIRCH_DOOR(194, Door.class), 
/*  261 */   JUNGLE_DOOR(195, Door.class), 
/*  262 */   ACACIA_DOOR(196, Door.class), 
/*  263 */   DARK_OAK_DOOR(197, Door.class), 
/*  264 */   IRON_SPADE(
/*  265 */     256, 1, 250), 
/*  266 */   IRON_PICKAXE(257, 1, 250), 
/*  267 */   IRON_AXE(258, 1, 250), 
/*  268 */   FLINT_AND_STEEL(259, 1, 64), 
/*  269 */   APPLE(260), 
/*  270 */   BOW(261, 1, 384), 
/*  271 */   ARROW(262), 
/*  272 */   COAL(263, Coal.class), 
/*  273 */   DIAMOND(264), 
/*  274 */   IRON_INGOT(265), 
/*  275 */   GOLD_INGOT(266), 
/*  276 */   IRON_SWORD(267, 1, 250), 
/*  277 */   WOOD_SWORD(268, 1, 59), 
/*  278 */   WOOD_SPADE(269, 1, 59), 
/*  279 */   WOOD_PICKAXE(270, 1, 59), 
/*  280 */   WOOD_AXE(271, 1, 59), 
/*  281 */   STONE_SWORD(272, 1, 131), 
/*  282 */   STONE_SPADE(273, 1, 131), 
/*  283 */   STONE_PICKAXE(274, 1, 131), 
/*  284 */   STONE_AXE(275, 1, 131), 
/*  285 */   DIAMOND_SWORD(276, 1, 1561), 
/*  286 */   DIAMOND_SPADE(277, 1, 1561), 
/*  287 */   DIAMOND_PICKAXE(278, 1, 1561), 
/*  288 */   DIAMOND_AXE(279, 1, 1561), 
/*  289 */   STICK(280), 
/*  290 */   BOWL(281), 
/*  291 */   MUSHROOM_SOUP(282, 1), 
/*  292 */   GOLD_SWORD(283, 1, 32), 
/*  293 */   GOLD_SPADE(284, 1, 32), 
/*  294 */   GOLD_PICKAXE(285, 1, 32), 
/*  295 */   GOLD_AXE(286, 1, 32), 
/*  296 */   STRING(287), 
/*  297 */   FEATHER(288), 
/*  298 */   SULPHUR(289), 
/*  299 */   WOOD_HOE(290, 1, 59), 
/*  300 */   STONE_HOE(291, 1, 131), 
/*  301 */   IRON_HOE(292, 1, 250), 
/*  302 */   DIAMOND_HOE(293, 1, 1561), 
/*  303 */   GOLD_HOE(294, 1, 32), 
/*  304 */   SEEDS(295), 
/*  305 */   WHEAT(296), 
/*  306 */   BREAD(297), 
/*  307 */   LEATHER_HELMET(298, 1, 55), 
/*  308 */   LEATHER_CHESTPLATE(299, 1, 80), 
/*  309 */   LEATHER_LEGGINGS(300, 1, 75), 
/*  310 */   LEATHER_BOOTS(301, 1, 65), 
/*  311 */   CHAINMAIL_HELMET(302, 1, 165), 
/*  312 */   CHAINMAIL_CHESTPLATE(303, 1, 240), 
/*  313 */   CHAINMAIL_LEGGINGS(304, 1, 225), 
/*  314 */   CHAINMAIL_BOOTS(305, 1, 195), 
/*  315 */   IRON_HELMET(306, 1, 165), 
/*  316 */   IRON_CHESTPLATE(307, 1, 240), 
/*  317 */   IRON_LEGGINGS(308, 1, 225), 
/*  318 */   IRON_BOOTS(309, 1, 195), 
/*  319 */   DIAMOND_HELMET(310, 1, 363), 
/*  320 */   DIAMOND_CHESTPLATE(311, 1, 528), 
/*  321 */   DIAMOND_LEGGINGS(312, 1, 495), 
/*  322 */   DIAMOND_BOOTS(313, 1, 429), 
/*  323 */   GOLD_HELMET(314, 1, 77), 
/*  324 */   GOLD_CHESTPLATE(315, 1, 112), 
/*  325 */   GOLD_LEGGINGS(316, 1, 105), 
/*  326 */   GOLD_BOOTS(317, 1, 91), 
/*  327 */   FLINT(318), 
/*  328 */   PORK(319), 
/*  329 */   GRILLED_PORK(320), 
/*  330 */   PAINTING(321), 
/*  331 */   GOLDEN_APPLE(322), 
/*  332 */   SIGN(323, 16), 
/*  333 */   WOOD_DOOR(324, 64), 
/*  334 */   BUCKET(325, 16), 
/*  335 */   WATER_BUCKET(326, 1), 
/*  336 */   LAVA_BUCKET(327, 1), 
/*  337 */   MINECART(328, 1), 
/*  338 */   SADDLE(329, 1), 
/*  339 */   IRON_DOOR(330, 64), 
/*  340 */   REDSTONE(331), 
/*  341 */   SNOW_BALL(332, 16), 
/*  342 */   BOAT(333, 1), 
/*  343 */   LEATHER(334), 
/*  344 */   MILK_BUCKET(335, 1), 
/*  345 */   CLAY_BRICK(336), 
/*  346 */   CLAY_BALL(337), 
/*  347 */   SUGAR_CANE(338), 
/*  348 */   PAPER(339), 
/*  349 */   BOOK(340), 
/*  350 */   SLIME_BALL(341), 
/*  351 */   STORAGE_MINECART(342, 1), 
/*  352 */   POWERED_MINECART(343, 1), 
/*  353 */   EGG(344, 16), 
/*  354 */   COMPASS(345), 
/*  355 */   FISHING_ROD(346, 1, 64), 
/*  356 */   WATCH(347), 
/*  357 */   GLOWSTONE_DUST(348), 
/*  358 */   RAW_FISH(349), 
/*  359 */   COOKED_FISH(350), 
/*  360 */   INK_SACK(351, Dye.class), 
/*  361 */   BONE(352), 
/*  362 */   SUGAR(353), 
/*  363 */   CAKE(354, 1), 
/*  364 */   BED(355, 1), 
/*  365 */   DIODE(356), 
/*  366 */   COOKIE(357), 
/*  367 */   MAP(
/*      */   
/*      */ 
/*  370 */     358, MaterialData.class), 
/*  371 */   SHEARS(359, 1, 238), 
/*  372 */   MELON(360), 
/*  373 */   PUMPKIN_SEEDS(361), 
/*  374 */   MELON_SEEDS(362), 
/*  375 */   RAW_BEEF(363), 
/*  376 */   COOKED_BEEF(364), 
/*  377 */   RAW_CHICKEN(365), 
/*  378 */   COOKED_CHICKEN(366), 
/*  379 */   ROTTEN_FLESH(367), 
/*  380 */   ENDER_PEARL(368, 16), 
/*  381 */   BLAZE_ROD(369), 
/*  382 */   GHAST_TEAR(370), 
/*  383 */   GOLD_NUGGET(371), 
/*  384 */   NETHER_STALK(372), 
/*  385 */   POTION(
/*      */   
/*      */ 
/*  388 */     373, 1, MaterialData.class), 
/*  389 */   GLASS_BOTTLE(374), 
/*  390 */   SPIDER_EYE(375), 
/*  391 */   FERMENTED_SPIDER_EYE(376), 
/*  392 */   BLAZE_POWDER(377), 
/*  393 */   MAGMA_CREAM(378), 
/*  394 */   BREWING_STAND_ITEM(379), 
/*  395 */   CAULDRON_ITEM(380), 
/*  396 */   EYE_OF_ENDER(381), 
/*  397 */   SPECKLED_MELON(382), 
/*  398 */   MONSTER_EGG(383, 64, SpawnEgg.class), 
/*  399 */   EXP_BOTTLE(384, 64), 
/*  400 */   FIREBALL(385, 64), 
/*  401 */   BOOK_AND_QUILL(386, 1), 
/*  402 */   WRITTEN_BOOK(387, 16), 
/*  403 */   EMERALD(388, 64), 
/*  404 */   ITEM_FRAME(389), 
/*  405 */   FLOWER_POT_ITEM(390), 
/*  406 */   CARROT_ITEM(391), 
/*  407 */   POTATO_ITEM(392), 
/*  408 */   BAKED_POTATO(393), 
/*  409 */   POISONOUS_POTATO(394), 
/*  410 */   EMPTY_MAP(395), 
/*  411 */   GOLDEN_CARROT(396), 
/*  412 */   SKULL_ITEM(397), 
/*  413 */   CARROT_STICK(398, 1, 25), 
/*  414 */   NETHER_STAR(399), 
/*  415 */   PUMPKIN_PIE(400), 
/*  416 */   FIREWORK(401), 
/*  417 */   FIREWORK_CHARGE(402), 
/*  418 */   ENCHANTED_BOOK(403, 1), 
/*  419 */   REDSTONE_COMPARATOR(404), 
/*  420 */   NETHER_BRICK_ITEM(405), 
/*  421 */   QUARTZ(406), 
/*  422 */   EXPLOSIVE_MINECART(407, 1), 
/*  423 */   HOPPER_MINECART(408, 1), 
/*  424 */   PRISMARINE_SHARD(409), 
/*  425 */   PRISMARINE_CRYSTALS(410), 
/*  426 */   RABBIT(411), 
/*  427 */   COOKED_RABBIT(412), 
/*  428 */   RABBIT_STEW(413, 1), 
/*  429 */   RABBIT_FOOT(414), 
/*  430 */   RABBIT_HIDE(415), 
/*  431 */   ARMOR_STAND(416, 16), 
/*  432 */   IRON_BARDING(417, 1), 
/*  433 */   GOLD_BARDING(418, 1), 
/*  434 */   DIAMOND_BARDING(419, 1), 
/*  435 */   LEASH(420), 
/*  436 */   NAME_TAG(421), 
/*  437 */   COMMAND_MINECART(422, 1), 
/*  438 */   MUTTON(423), 
/*  439 */   COOKED_MUTTON(424), 
/*  440 */   BANNER(425, 16), 
/*  441 */   SPRUCE_DOOR_ITEM(427), 
/*  442 */   BIRCH_DOOR_ITEM(428), 
/*  443 */   JUNGLE_DOOR_ITEM(429), 
/*  444 */   ACACIA_DOOR_ITEM(430), 
/*  445 */   DARK_OAK_DOOR_ITEM(431), 
/*  446 */   GOLD_RECORD(2256, 1), 
/*  447 */   GREEN_RECORD(2257, 1), 
/*  448 */   RECORD_3(2258, 1), 
/*  449 */   RECORD_4(2259, 1), 
/*  450 */   RECORD_5(2260, 1), 
/*  451 */   RECORD_6(2261, 1), 
/*  452 */   RECORD_7(2262, 1), 
/*  453 */   RECORD_8(2263, 1), 
/*  454 */   RECORD_9(2264, 1), 
/*  455 */   RECORD_10(2265, 1), 
/*  456 */   RECORD_11(2266, 1), 
/*  457 */   RECORD_12(2267, 1);
/*      */   
/*      */   private final int id;
/*      */   private final Constructor<? extends MaterialData> ctor;
/*      */   private static Material[] byId;
/*      */   private static final Map<String, Material> BY_NAME;
/*      */   private final int maxStack;
/*      */   private final short durability;
/*      */   
/*      */   private Material(int id)
/*      */   {
/*  468 */     this(id, 64);
/*      */   }
/*      */   
/*      */   private Material(int id, int stack) {
/*  472 */     this(id, stack, MaterialData.class);
/*      */   }
/*      */   
/*      */   private Material(int id, int stack, int durability) {
/*  476 */     this(id, stack, durability, MaterialData.class);
/*      */   }
/*      */   
/*      */   private Material(int id, Class<? extends MaterialData> data) {
/*  480 */     this(id, 64, data);
/*      */   }
/*      */   
/*      */   private Material(int id, int stack, Class<? extends MaterialData> data) {
/*  484 */     this(id, stack, 0, data);
/*      */   }
/*      */   
/*      */   private Material(int id, int stack, int durability, Class<? extends MaterialData> data) {
/*  488 */     this.id = id;
/*  489 */     this.durability = ((short)durability);
/*  490 */     this.maxStack = stack;
/*      */     try
/*      */     {
/*  493 */       this.ctor = data.getConstructor(new Class[] { Integer.TYPE, Byte.TYPE });
/*      */     } catch (NoSuchMethodException ex) {
/*  495 */       throw new AssertionError(ex);
/*      */     } catch (SecurityException ex) {
/*  497 */       throw new AssertionError(ex);
/*      */     }
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   @Deprecated
/*      */   public int getId()
/*      */   {
/*  509 */     return this.id;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public int getMaxStackSize()
/*      */   {
/*  518 */     return this.maxStack;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public short getMaxDurability()
/*      */   {
/*  527 */     return this.durability;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public Class<? extends MaterialData> getData()
/*      */   {
/*  536 */     return this.ctor.getDeclaringClass();
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   @Deprecated
/*      */   public MaterialData getNewData(byte raw)
/*      */   {
/*      */     try
/*      */     {
/*  550 */       return (MaterialData)this.ctor.newInstance(new Object[] { Integer.valueOf(this.id), Byte.valueOf(raw) });
/*      */     } catch (InstantiationException ex) {
/*  552 */       Throwable t = ex.getCause();
/*  553 */       if ((t instanceof RuntimeException)) {
/*  554 */         throw ((RuntimeException)t);
/*      */       }
/*  556 */       if ((t instanceof Error)) {
/*  557 */         throw ((Error)t);
/*      */       }
/*  559 */       throw new AssertionError(t);
/*      */     } catch (Throwable t) {
/*  561 */       throw new AssertionError(t);
/*      */     }
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public boolean isBlock()
/*      */   {
/*  571 */     return this.id < 256;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public boolean isEdible()
/*      */   {
/*  580 */     switch (this) {
/*      */     case LEASH: 
/*      */     case MONSTER_EGGS: 
/*      */     case OBSIDIAN: 
/*      */     case PUMPKIN_STEM: 
/*      */     case QUARTZ: 
/*      */     case QUARTZ_ORE: 
/*      */     case REDSTONE_ORE: 
/*      */     case REDSTONE_TORCH_OFF: 
/*      */     case ROTTEN_FLESH: 
/*      */     case SANDSTONE: 
/*      */     case SEA_LANTERN: 
/*      */     case SEEDS: 
/*      */     case SHEARS: 
/*      */     case SIGN: 
/*      */     case SIGN_POST: 
/*      */     case SNOW_BALL: 
/*      */     case STATIONARY_LAVA: 
/*      */     case STATIONARY_WATER: 
/*      */     case STEP: 
/*      */     case STICK: 
/*      */     case STONE_AXE: 
/*      */     case STONE_PLATE: 
/*      */     case TNT: 
/*      */     case TORCH: 
/*      */     case TRAPPED_CHEST: 
/*      */     case WATER_LILY: 
/*      */     case WEB: 
/*  608 */       return true;
/*      */     }
/*  610 */     return false;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   @Deprecated
/*      */   public static Material getMaterial(int id)
/*      */   {
/*  623 */     if ((byId.length > id) && (id >= 0)) {
/*  624 */       return byId[id];
/*      */     }
/*  626 */     return null;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public static Material getMaterial(String name)
/*      */   {
/*  640 */     return (Material)BY_NAME.get(name);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public static Material matchMaterial(String name)
/*      */   {
/*  656 */     Validate.notNull(name, "Name cannot be null");
/*      */     
/*  658 */     Material result = null;
/*      */     try
/*      */     {
/*  661 */       result = getMaterial(Integer.parseInt(name));
/*      */     }
/*      */     catch (NumberFormatException localNumberFormatException) {}
/*  664 */     if (result == null) {
/*  665 */       String filtered = name.toUpperCase();
/*      */       
/*  667 */       filtered = filtered.replaceAll("\\s+", "_").replaceAll("\\W", "");
/*  668 */       result = (Material)BY_NAME.get(filtered);
/*      */     }
/*      */     
/*  671 */     return result;
/*      */   }
/*      */   
/*      */   static
/*      */   {
/*  462 */     byId = new Material['ſ'];
/*  463 */     BY_NAME = Maps.newHashMap();
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     Material[] arrayOfMaterial;
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*  675 */     int i = (arrayOfMaterial = values()).length; for (int j = 0; j < i; j++) { Material material = arrayOfMaterial[j];
/*  676 */       if (byId.length > material.id) {
/*  677 */         byId[material.id] = material;
/*      */       } else {
/*  679 */         byId = (Material[])Java15Compat.Arrays_copyOfRange(byId, 0, material.id + 2);
/*  680 */         byId[material.id] = material;
/*      */       }
/*  682 */       BY_NAME.put(material.name(), material);
/*      */     }
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */   public boolean isRecord()
/*      */   {
/*  690 */     return (this.id >= GOLD_RECORD.id) && (this.id <= RECORD_12.id);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public boolean isSolid()
/*      */   {
/*  700 */     if ((!isBlock()) || (this.id == 0)) {
/*  701 */       return false;
/*      */     }
/*  703 */     switch (this) {
/*      */     case ACACIA_DOOR_ITEM: 
/*      */     case ACACIA_FENCE: 
/*      */     case ACACIA_FENCE_GATE: 
/*      */     case ACACIA_STAIRS: 
/*      */     case ACTIVATOR_RAIL: 
/*      */     case ANVIL: 
/*      */     case BANNER: 
/*      */     case BARRIER: 
/*      */     case BEACON: 
/*      */     case BED: 
/*      */     case BEDROCK: 
/*      */     case BED_BLOCK: 
/*      */     case BIRCH_DOOR: 
/*      */     case BIRCH_DOOR_ITEM: 
/*      */     case BIRCH_FENCE: 
/*      */     case BIRCH_FENCE_GATE: 
/*      */     case BIRCH_WOOD_STAIRS: 
/*      */     case BLAZE_POWDER: 
/*      */     case BLAZE_ROD: 
/*      */     case BOAT: 
/*      */     case BONE: 
/*      */     case BOOK_AND_QUILL: 
/*      */     case BREWING_STAND: 
/*      */     case BREWING_STAND_ITEM: 
/*      */     case BRICK: 
/*      */     case BRICK_STAIRS: 
/*      */     case CAKE: 
/*      */     case CAKE_BLOCK: 
/*      */     case CARPET: 
/*      */     case CARROT: 
/*      */     case CARROT_ITEM: 
/*      */     case CARROT_STICK: 
/*      */     case CAULDRON: 
/*      */     case CAULDRON_ITEM: 
/*      */     case CHAINMAIL_BOOTS: 
/*      */     case CHAINMAIL_LEGGINGS: 
/*      */     case CHEST: 
/*      */     case CLAY: 
/*      */     case CLAY_BRICK: 
/*      */     case COAL: 
/*      */     case COAL_BLOCK: 
/*      */     case COBBLESTONE: 
/*      */     case COBBLESTONE_STAIRS: 
/*      */     case COBBLE_WALL: 
/*      */     case COCOA: 
/*      */     case COMMAND: 
/*      */     case COOKED_BEEF: 
/*      */     case COOKED_CHICKEN: 
/*      */     case COOKED_MUTTON: 
/*      */     case COOKED_RABBIT: 
/*      */     case COOKIE: 
/*      */     case CROPS: 
/*      */     case DARK_OAK_DOOR: 
/*      */     case DAYLIGHT_DETECTOR: 
/*      */     case DAYLIGHT_DETECTOR_INVERTED: 
/*      */     case DEAD_BUSH: 
/*      */     case DETECTOR_RAIL: 
/*      */     case DIAMOND_AXE: 
/*      */     case DIAMOND_BARDING: 
/*      */     case DIAMOND_BLOCK: 
/*      */     case DIAMOND_BOOTS: 
/*      */     case DIAMOND_CHESTPLATE: 
/*      */     case DIAMOND_HELMET: 
/*      */     case DIAMOND_LEGGINGS: 
/*      */     case DIAMOND_ORE: 
/*      */     case DIAMOND_SWORD: 
/*      */     case DIODE: 
/*      */     case DIODE_BLOCK_OFF: 
/*      */     case DIODE_BLOCK_ON: 
/*      */     case DIRT: 
/*      */     case DISPENSER: 
/*      */     case DOUBLE_PLANT: 
/*      */     case DOUBLE_STEP: 
/*      */     case DOUBLE_STONE_SLAB2: 
/*      */     case EMERALD: 
/*      */     case EMERALD_BLOCK: 
/*      */     case EMERALD_ORE: 
/*      */     case EMPTY_MAP: 
/*      */     case ENCHANTMENT_TABLE: 
/*      */     case ENDER_CHEST: 
/*      */     case ENDER_PEARL: 
/*      */     case ENDER_PORTAL_FRAME: 
/*      */     case ENDER_STONE: 
/*      */     case EXPLOSIVE_MINECART: 
/*      */     case EYE_OF_ENDER: 
/*      */     case FEATHER: 
/*      */     case FENCE: 
/*      */     case FENCE_GATE: 
/*      */     case FERMENTED_SPIDER_EYE: 
/*      */     case FIRE: 
/*      */     case FIREBALL: 
/*      */     case FIREWORK_CHARGE: 
/*      */     case FISHING_ROD: 
/*      */     case FLINT: 
/*      */     case FLOWER_POT_ITEM: 
/*      */     case FURNACE: 
/*      */     case GHAST_TEAR: 
/*      */     case GLASS: 
/*      */     case GLASS_BOTTLE: 
/*      */     case GLOWING_REDSTONE_ORE: 
/*      */     case GLOWSTONE: 
/*      */     case GOLD_BLOCK: 
/*      */     case GOLD_BOOTS: 
/*      */     case GOLD_CHESTPLATE: 
/*      */     case GOLD_HELMET: 
/*      */     case GOLD_LEGGINGS: 
/*      */     case GOLD_NUGGET: 
/*      */     case GOLD_ORE: 
/*      */     case GOLD_PICKAXE: 
/*      */     case GOLD_PLATE: 
/*      */     case GOLD_RECORD: 
/*      */     case GOLD_SWORD: 
/*      */     case GRASS: 
/*      */     case GRAVEL: 
/*      */     case GREEN_RECORD: 
/*      */     case GRILLED_PORK: 
/*      */     case HARD_CLAY: 
/*      */     case HAY_BLOCK: 
/*      */     case HOPPER: 
/*      */     case HOPPER_MINECART: 
/*      */     case HUGE_MUSHROOM_1: 
/*      */     case HUGE_MUSHROOM_2: 
/*      */     case ICE: 
/*      */     case INK_SACK: 
/*      */     case IRON_BARDING: 
/*      */     case IRON_BLOCK: 
/*      */     case IRON_BOOTS: 
/*      */     case IRON_DOOR: 
/*      */     case IRON_DOOR_BLOCK: 
/*      */     case IRON_FENCE: 
/*      */     case IRON_HELMET: 
/*      */     case IRON_HOE: 
/*      */     case IRON_INGOT: 
/*      */     case IRON_LEGGINGS: 
/*      */     case IRON_ORE: 
/*      */     case IRON_PICKAXE: 
/*      */     case IRON_PLATE: 
/*      */     case IRON_SPADE: 
/*      */     case IRON_SWORD: 
/*      */     case IRON_TRAPDOOR: 
/*      */     case ITEM_FRAME: 
/*      */     case JACK_O_LANTERN: 
/*      */     case JUKEBOX: 
/*      */     case JUNGLE_DOOR: 
/*      */     case JUNGLE_DOOR_ITEM: 
/*      */     case JUNGLE_FENCE: 
/*      */     case JUNGLE_FENCE_GATE: 
/*      */     case JUNGLE_WOOD_STAIRS: 
/*      */     case LADDER: 
/*  853 */       return true;
/*      */     }
/*  855 */     return false;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public boolean isTransparent()
/*      */   {
/*  865 */     if (!isBlock()) {
/*  866 */       return false;
/*      */     }
/*  868 */     switch (this) {
/*      */     case ACACIA_DOOR: 
/*      */     case AIR: 
/*      */     case BOOK: 
/*      */     case BOOKSHELF: 
/*      */     case BOWL: 
/*      */     case BREAD: 
/*      */     case BROWN_MUSHROOM: 
/*      */     case BUCKET: 
/*      */     case BURNING_FURNACE: 
/*      */     case CACTUS: 
/*      */     case CHAINMAIL_CHESTPLATE: 
/*      */     case CHAINMAIL_HELMET: 
/*      */     case CLAY_BALL: 
/*      */     case COAL_ORE: 
/*      */     case COMMAND_MINECART: 
/*      */     case COMPASS: 
/*      */     case COOKED_FISH: 
/*      */     case DARK_OAK_DOOR_ITEM: 
/*      */     case DARK_OAK_FENCE: 
/*      */     case DARK_OAK_FENCE_GATE: 
/*      */     case DARK_OAK_STAIRS: 
/*      */     case DIAMOND: 
/*      */     case DIAMOND_HOE: 
/*      */     case DIAMOND_PICKAXE: 
/*      */     case DIAMOND_SPADE: 
/*      */     case DRAGON_EGG: 
/*      */     case DROPPER: 
/*      */     case EGG: 
/*      */     case ENCHANTED_BOOK: 
/*      */     case ENDER_PORTAL: 
/*      */     case EXP_BOTTLE: 
/*      */     case FIREWORK: 
/*      */     case FLINT_AND_STEEL: 
/*      */     case FLOWER_POT: 
/*      */     case GLOWSTONE_DUST: 
/*      */     case GOLDEN_APPLE: 
/*      */     case GOLDEN_CARROT: 
/*      */     case GOLD_AXE: 
/*      */     case GOLD_BARDING: 
/*      */     case GOLD_HOE: 
/*      */     case GOLD_INGOT: 
/*      */     case GOLD_SPADE: 
/*      */     case IRON_AXE: 
/*      */     case IRON_CHESTPLATE: 
/*  913 */       return true;
/*      */     }
/*  915 */     return false;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public boolean isFlammable()
/*      */   {
/*  925 */     if (!isBlock()) {
/*  926 */       return false;
/*      */     }
/*  928 */     switch (this) {
/*      */     case ACTIVATOR_RAIL: 
/*      */     case BED_BLOCK: 
/*      */     case BIRCH_DOOR: 
/*      */     case BOAT: 
/*      */     case BONE: 
/*      */     case BOWL: 
/*      */     case BREAD: 
/*      */     case BRICK: 
/*      */     case CARROT_STICK: 
/*      */     case CAULDRON: 
/*      */     case CHEST: 
/*      */     case CLAY: 
/*      */     case COAL_BLOCK: 
/*      */     case COCOA: 
/*      */     case COMMAND: 
/*      */     case COOKED_CHICKEN: 
/*      */     case COOKIE: 
/*      */     case DIAMOND_AXE: 
/*      */     case DIAMOND_BARDING: 
/*      */     case DIODE: 
/*      */     case DIRT: 
/*      */     case DISPENSER: 
/*      */     case EGG: 
/*      */     case EMERALD: 
/*      */     case FIRE: 
/*      */     case FIREBALL: 
/*      */     case FURNACE: 
/*      */     case GHAST_TEAR: 
/*      */     case GLASS: 
/*      */     case GOLD_BOOTS: 
/*      */     case GOLD_LEGGINGS: 
/*      */     case GREEN_RECORD: 
/*      */     case GRILLED_PORK: 
/*      */     case HARD_CLAY: 
/*      */     case HAY_BLOCK: 
/*      */     case IRON_AXE: 
/*      */     case IRON_CHESTPLATE: 
/*      */     case IRON_DOOR: 
/*      */     case IRON_DOOR_BLOCK: 
/*      */     case IRON_FENCE: 
/*      */     case IRON_ORE: 
/*      */     case IRON_PICKAXE: 
/*      */     case IRON_PLATE: 
/*      */     case IRON_SPADE: 
/*      */     case IRON_SWORD: 
/*      */     case IRON_TRAPDOOR: 
/*      */     case ITEM_FRAME: 
/*      */     case JACK_O_LANTERN: 
/*      */     case JUKEBOX: 
/*      */     case JUNGLE_DOOR: 
/*      */     case JUNGLE_DOOR_ITEM: 
/*      */     case JUNGLE_FENCE: 
/*      */     case JUNGLE_FENCE_GATE: 
/*      */     case JUNGLE_WOOD_STAIRS: 
/*      */     case LADDER: 
/*  984 */       return true;
/*      */     }
/*  986 */     return false;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public boolean isBurnable()
/*      */   {
/*  996 */     if (!isBlock()) {
/*  997 */       return false;
/*      */     }
/*  999 */     switch (this) {
/*      */     case ACTIVATOR_RAIL: 
/*      */     case BED_BLOCK: 
/*      */     case BIRCH_DOOR: 
/*      */     case BOWL: 
/*      */     case BREAD: 
/*      */     case BRICK: 
/*      */     case BROWN_MUSHROOM: 
/*      */     case BUCKET: 
/*      */     case CARROT_STICK: 
/*      */     case CAULDRON: 
/*      */     case CHEST: 
/*      */     case DIAMOND_BARDING: 
/*      */     case EGG: 
/*      */     case EMERALD: 
/*      */     case FIRE: 
/*      */     case FIREBALL: 
/*      */     case FURNACE: 
/*      */     case GHAST_TEAR: 
/*      */     case GLASS: 
/*      */     case GREEN_RECORD: 
/*      */     case GRILLED_PORK: 
/*      */     case INK_SACK: 
/*      */     case IRON_AXE: 
/*      */     case IRON_BLOCK: 
/*      */     case IRON_CHESTPLATE: 
/*      */     case IRON_ORE: 
/*      */     case IRON_PICKAXE: 
/*      */     case IRON_PLATE: 
/*      */     case IRON_SPADE: 
/*      */     case IRON_SWORD: 
/*      */     case IRON_TRAPDOOR: 
/*      */     case ITEM_FRAME: 
/*      */     case JACK_O_LANTERN: 
/*      */     case JUKEBOX: 
/*      */     case JUNGLE_DOOR: 
/* 1035 */       return true;
/*      */     }
/* 1037 */     return false;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public boolean isOccluding()
/*      */   {
/* 1047 */     if (!isBlock()) {
/* 1048 */       return false;
/*      */     }
/* 1050 */     switch (this) {
/*      */     case ACACIA_DOOR_ITEM: 
/*      */     case ACACIA_FENCE: 
/*      */     case ACACIA_FENCE_GATE: 
/*      */     case ACACIA_STAIRS: 
/*      */     case ACTIVATOR_RAIL: 
/*      */     case ANVIL: 
/*      */     case BANNER: 
/*      */     case BARRIER: 
/*      */     case BEACON: 
/*      */     case BED: 
/*      */     case BEDROCK: 
/*      */     case BED_BLOCK: 
/*      */     case BIRCH_DOOR_ITEM: 
/*      */     case BIRCH_FENCE_GATE: 
/*      */     case BIRCH_WOOD_STAIRS: 
/*      */     case BLAZE_POWDER: 
/*      */     case BLAZE_ROD: 
/*      */     case BOAT: 
/*      */     case BRICK: 
/*      */     case CAKE: 
/*      */     case CAKE_BLOCK: 
/*      */     case CARPET: 
/*      */     case CARROT_ITEM: 
/*      */     case CAULDRON: 
/*      */     case CAULDRON_ITEM: 
/*      */     case CHAINMAIL_BOOTS: 
/*      */     case CHAINMAIL_LEGGINGS: 
/*      */     case CLAY_BRICK: 
/*      */     case COAL: 
/*      */     case COAL_BLOCK: 
/*      */     case COBBLESTONE_STAIRS: 
/*      */     case COBBLE_WALL: 
/*      */     case CROPS: 
/*      */     case DARK_OAK_DOOR: 
/*      */     case DAYLIGHT_DETECTOR_INVERTED: 
/*      */     case DETECTOR_RAIL: 
/*      */     case DIAMOND_AXE: 
/*      */     case DIAMOND_BLOCK: 
/*      */     case DIAMOND_BOOTS: 
/*      */     case DIAMOND_CHESTPLATE: 
/*      */     case DIAMOND_LEGGINGS: 
/*      */     case DIODE_BLOCK_OFF: 
/*      */     case DIODE_BLOCK_ON: 
/*      */     case DIRT: 
/*      */     case DISPENSER: 
/*      */     case DOUBLE_STONE_SLAB2: 
/*      */     case EMPTY_MAP: 
/*      */     case ENCHANTMENT_TABLE: 
/*      */     case EYE_OF_ENDER: 
/*      */     case FEATHER: 
/*      */     case FENCE_GATE: 
/*      */     case FERMENTED_SPIDER_EYE: 
/*      */     case FIRE: 
/*      */     case FISHING_ROD: 
/*      */     case FLOWER_POT_ITEM: 
/*      */     case GLASS_BOTTLE: 
/*      */     case GOLD_ORE: 
/*      */     case GOLD_PLATE: 
/*      */     case GOLD_SWORD: 
/*      */     case GRASS: 
/*      */     case GRILLED_PORK: 
/*      */     case HOPPER: 
/*      */     case HOPPER_MINECART: 
/*      */     case HUGE_MUSHROOM_2: 
/*      */     case INK_SACK: 
/*      */     case IRON_BARDING: 
/*      */     case IRON_BLOCK: 
/*      */     case IRON_BOOTS: 
/*      */     case IRON_HELMET: 
/*      */     case IRON_INGOT: 
/* 1121 */       return true;
/*      */     }
/* 1123 */     return false;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   public boolean hasGravity()
/*      */   {
/* 1131 */     if (!isBlock()) {
/* 1132 */       return false;
/*      */     }
/* 1134 */     switch (this) {
/*      */     case BANNER: 
/*      */     case BARRIER: 
/*      */     case GOLD_BLOCK: 
/* 1138 */       return true;
/*      */     }
/* 1140 */     return false;
/*      */   }
/*      */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\org\bukkit\Material.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */
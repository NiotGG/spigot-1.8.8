/*     */ package net.minecraft.server.v1_8_R3;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class Blocks
/*     */ {
/*     */   static
/*     */   {
/*  13 */     if (!DispenserRegistry.a()) {
/*  14 */       throw new RuntimeException("Accessed Blocks before Bootstrap!");
/*     */     }
/*     */   }
/*     */   
/*  18 */   public static final Block AIR = get("air");
/*  19 */   public static final Block STONE = get("stone");
/*  20 */   public static final BlockGrass GRASS = (BlockGrass)get("grass");
/*  21 */   public static final Block DIRT = get("dirt");
/*  22 */   public static final Block COBBLESTONE = get("cobblestone");
/*  23 */   public static final Block PLANKS = get("planks");
/*  24 */   public static final Block SAPLING = get("sapling");
/*  25 */   public static final Block BEDROCK = get("bedrock");
/*  26 */   public static final BlockFlowing FLOWING_WATER = (BlockFlowing)get("flowing_water");
/*  27 */   public static final BlockStationary WATER = (BlockStationary)get("water");
/*  28 */   public static final BlockFlowing FLOWING_LAVA = (BlockFlowing)get("flowing_lava");
/*  29 */   public static final BlockStationary LAVA = (BlockStationary)get("lava");
/*  30 */   public static final BlockSand SAND = (BlockSand)get("sand");
/*  31 */   public static final Block GRAVEL = get("gravel");
/*  32 */   public static final Block GOLD_ORE = get("gold_ore");
/*  33 */   public static final Block IRON_ORE = get("iron_ore");
/*  34 */   public static final Block COAL_ORE = get("coal_ore");
/*  35 */   public static final Block LOG = get("log");
/*  36 */   public static final Block LOG2 = get("log2");
/*  37 */   public static final BlockLeaves LEAVES = (BlockLeaves)get("leaves");
/*  38 */   public static final BlockLeaves LEAVES2 = (BlockLeaves)get("leaves2");
/*  39 */   public static final Block SPONGE = get("sponge");
/*  40 */   public static final Block GLASS = get("glass");
/*  41 */   public static final Block LAPIS_ORE = get("lapis_ore");
/*  42 */   public static final Block LAPIS_BLOCK = get("lapis_block");
/*  43 */   public static final Block DISPENSER = get("dispenser");
/*  44 */   public static final Block SANDSTONE = get("sandstone");
/*  45 */   public static final Block NOTEBLOCK = get("noteblock");
/*  46 */   public static final Block BED = get("bed");
/*  47 */   public static final Block GOLDEN_RAIL = get("golden_rail");
/*  48 */   public static final Block DETECTOR_RAIL = get("detector_rail");
/*  49 */   public static final BlockPiston STICKY_PISTON = (BlockPiston)get("sticky_piston");
/*  50 */   public static final Block WEB = get("web");
/*  51 */   public static final BlockLongGrass TALLGRASS = (BlockLongGrass)get("tallgrass");
/*  52 */   public static final BlockDeadBush DEADBUSH = (BlockDeadBush)get("deadbush");
/*  53 */   public static final BlockPiston PISTON = (BlockPiston)get("piston");
/*  54 */   public static final BlockPistonExtension PISTON_HEAD = (BlockPistonExtension)get("piston_head");
/*  55 */   public static final Block WOOL = get("wool");
/*  56 */   public static final BlockPistonMoving PISTON_EXTENSION = (BlockPistonMoving)get("piston_extension");
/*  57 */   public static final BlockFlowers YELLOW_FLOWER = (BlockFlowers)get("yellow_flower");
/*  58 */   public static final BlockFlowers RED_FLOWER = (BlockFlowers)get("red_flower");
/*  59 */   public static final BlockPlant BROWN_MUSHROOM = (BlockPlant)get("brown_mushroom");
/*  60 */   public static final BlockPlant RED_MUSHROOM = (BlockPlant)get("red_mushroom");
/*  61 */   public static final Block GOLD_BLOCK = get("gold_block");
/*  62 */   public static final Block IRON_BLOCK = get("iron_block");
/*  63 */   public static final BlockStepAbstract DOUBLE_STONE_SLAB = (BlockStepAbstract)get("double_stone_slab");
/*  64 */   public static final BlockStepAbstract STONE_SLAB = (BlockStepAbstract)get("stone_slab");
/*  65 */   public static final Block BRICK_BLOCK = get("brick_block");
/*  66 */   public static final Block TNT = get("tnt");
/*  67 */   public static final Block BOOKSHELF = get("bookshelf");
/*  68 */   public static final Block MOSSY_COBBLESTONE = get("mossy_cobblestone");
/*  69 */   public static final Block OBSIDIAN = get("obsidian");
/*  70 */   public static final Block TORCH = get("torch");
/*  71 */   public static final BlockFire FIRE = (BlockFire)get("fire");
/*  72 */   public static final Block MOB_SPAWNER = get("mob_spawner");
/*  73 */   public static final Block OAK_STAIRS = get("oak_stairs");
/*  74 */   public static final BlockChest CHEST = (BlockChest)get("chest");
/*  75 */   public static final BlockRedstoneWire REDSTONE_WIRE = (BlockRedstoneWire)get("redstone_wire");
/*  76 */   public static final Block DIAMOND_ORE = get("diamond_ore");
/*  77 */   public static final Block DIAMOND_BLOCK = get("diamond_block");
/*  78 */   public static final Block CRAFTING_TABLE = get("crafting_table");
/*  79 */   public static final Block WHEAT = get("wheat");
/*  80 */   public static final Block FARMLAND = get("farmland");
/*  81 */   public static final Block FURNACE = get("furnace");
/*  82 */   public static final Block LIT_FURNACE = get("lit_furnace");
/*  83 */   public static final Block STANDING_SIGN = get("standing_sign");
/*  84 */   public static final Block WOODEN_DOOR = get("wooden_door");
/*  85 */   public static final Block SPRUCE_DOOR = get("spruce_door");
/*  86 */   public static final Block BIRCH_DOOR = get("birch_door");
/*  87 */   public static final Block JUNGLE_DOOR = get("jungle_door");
/*  88 */   public static final Block ACACIA_DOOR = get("acacia_door");
/*  89 */   public static final Block DARK_OAK_DOOR = get("dark_oak_door");
/*  90 */   public static final Block LADDER = get("ladder");
/*  91 */   public static final Block RAIL = get("rail");
/*  92 */   public static final Block STONE_STAIRS = get("stone_stairs");
/*  93 */   public static final Block WALL_SIGN = get("wall_sign");
/*  94 */   public static final Block LEVER = get("lever");
/*  95 */   public static final Block STONE_PRESSURE_PLATE = get("stone_pressure_plate");
/*  96 */   public static final Block IRON_DOOR = get("iron_door");
/*  97 */   public static final Block WOODEN_PRESSURE_PLATE = get("wooden_pressure_plate");
/*  98 */   public static final Block REDSTONE_ORE = get("redstone_ore");
/*  99 */   public static final Block LIT_REDSTONE_ORE = get("lit_redstone_ore");
/* 100 */   public static final Block UNLIT_REDSTONE_TORCH = get("unlit_redstone_torch");
/* 101 */   public static final Block REDSTONE_TORCH = get("redstone_torch");
/* 102 */   public static final Block STONE_BUTTON = get("stone_button");
/* 103 */   public static final Block SNOW_LAYER = get("snow_layer");
/* 104 */   public static final Block ICE = get("ice");
/* 105 */   public static final Block SNOW = get("snow");
/* 106 */   public static final BlockCactus CACTUS = (BlockCactus)get("cactus");
/* 107 */   public static final Block CLAY = get("clay");
/* 108 */   public static final BlockReed REEDS = (BlockReed)get("reeds");
/* 109 */   public static final Block JUKEBOX = get("jukebox");
/* 110 */   public static final Block FENCE = get("fence");
/* 111 */   public static final Block SPRUCE_FENCE = get("spruce_fence");
/* 112 */   public static final Block BIRCH_FENCE = get("birch_fence");
/* 113 */   public static final Block JUNGLE_FENCE = get("jungle_fence");
/* 114 */   public static final Block DARK_OAK_FENCE = get("dark_oak_fence");
/* 115 */   public static final Block ACACIA_FENCE = get("acacia_fence");
/* 116 */   public static final Block PUMPKIN = get("pumpkin");
/* 117 */   public static final Block NETHERRACK = get("netherrack");
/* 118 */   public static final Block SOUL_SAND = get("soul_sand");
/* 119 */   public static final Block GLOWSTONE = get("glowstone");
/* 120 */   public static final BlockPortal PORTAL = (BlockPortal)get("portal");
/* 121 */   public static final Block LIT_PUMPKIN = get("lit_pumpkin");
/* 122 */   public static final Block CAKE = get("cake");
/* 123 */   public static final BlockRepeater UNPOWERED_REPEATER = (BlockRepeater)get("unpowered_repeater");
/* 124 */   public static final BlockRepeater POWERED_REPEATER = (BlockRepeater)get("powered_repeater");
/* 125 */   public static final Block TRAPDOOR = get("trapdoor");
/* 126 */   public static final Block MONSTER_EGG = get("monster_egg");
/* 127 */   public static final Block STONEBRICK = get("stonebrick");
/* 128 */   public static final Block BROWN_MUSHROOM_BLOCK = get("brown_mushroom_block");
/* 129 */   public static final Block RED_MUSHROOM_BLOCK = get("red_mushroom_block");
/* 130 */   public static final Block IRON_BARS = get("iron_bars");
/* 131 */   public static final Block GLASS_PANE = get("glass_pane");
/* 132 */   public static final Block MELON_BLOCK = get("melon_block");
/* 133 */   public static final Block PUMPKIN_STEM = get("pumpkin_stem");
/* 134 */   public static final Block MELON_STEM = get("melon_stem");
/* 135 */   public static final Block VINE = get("vine");
/* 136 */   public static final Block FENCE_GATE = get("fence_gate");
/* 137 */   public static final Block SPRUCE_FENCE_GATE = get("spruce_fence_gate");
/* 138 */   public static final Block BIRCH_FENCE_GATE = get("birch_fence_gate");
/* 139 */   public static final Block JUNGLE_FENCE_GATE = get("jungle_fence_gate");
/* 140 */   public static final Block DARK_OAK_FENCE_GATE = get("dark_oak_fence_gate");
/* 141 */   public static final Block ACACIA_FENCE_GATE = get("acacia_fence_gate");
/* 142 */   public static final Block BRICK_STAIRS = get("brick_stairs");
/* 143 */   public static final Block STONE_BRICK_STAIRS = get("stone_brick_stairs");
/* 144 */   public static final BlockMycel MYCELIUM = (BlockMycel)get("mycelium");
/* 145 */   public static final Block WATERLILY = get("waterlily");
/* 146 */   public static final Block NETHER_BRICK = get("nether_brick");
/* 147 */   public static final Block NETHER_BRICK_FENCE = get("nether_brick_fence");
/* 148 */   public static final Block NETHER_BRICK_STAIRS = get("nether_brick_stairs");
/* 149 */   public static final Block NETHER_WART = get("nether_wart");
/* 150 */   public static final Block ENCHANTING_TABLE = get("enchanting_table");
/* 151 */   public static final Block BREWING_STAND = get("brewing_stand");
/* 152 */   public static final BlockCauldron cauldron = (BlockCauldron)get("cauldron");
/* 153 */   public static final Block END_PORTAL = get("end_portal");
/* 154 */   public static final Block END_PORTAL_FRAME = get("end_portal_frame");
/* 155 */   public static final Block END_STONE = get("end_stone");
/* 156 */   public static final Block DRAGON_EGG = get("dragon_egg");
/* 157 */   public static final Block REDSTONE_LAMP = get("redstone_lamp");
/* 158 */   public static final Block LIT_REDSTONE_LAMP = get("lit_redstone_lamp");
/* 159 */   public static final BlockStepAbstract DOUBLE_WOODEN_SLAB = (BlockStepAbstract)get("double_wooden_slab");
/* 160 */   public static final BlockStepAbstract WOODEN_SLAB = (BlockStepAbstract)get("wooden_slab");
/* 161 */   public static final Block COCOA = get("cocoa");
/* 162 */   public static final Block SANDSTONE_STAIRS = get("sandstone_stairs");
/* 163 */   public static final Block EMERALD_ORE = get("emerald_ore");
/* 164 */   public static final Block ENDER_CHEST = get("ender_chest");
/* 165 */   public static final BlockTripwireHook TRIPWIRE_HOOK = (BlockTripwireHook)get("tripwire_hook");
/* 166 */   public static final Block TRIPWIRE = get("tripwire");
/* 167 */   public static final Block EMERALD_BLOCK = get("emerald_block");
/* 168 */   public static final Block SPRUCE_STAIRS = get("spruce_stairs");
/* 169 */   public static final Block BIRCH_STAIRS = get("birch_stairs");
/* 170 */   public static final Block JUNGLE_STAIRS = get("jungle_stairs");
/* 171 */   public static final Block COMMAND_BLOCK = get("command_block");
/* 172 */   public static final BlockBeacon BEACON = (BlockBeacon)get("beacon");
/* 173 */   public static final Block COBBLESTONE_WALL = get("cobblestone_wall");
/* 174 */   public static final Block FLOWER_POT = get("flower_pot");
/* 175 */   public static final Block CARROTS = get("carrots");
/* 176 */   public static final Block POTATOES = get("potatoes");
/* 177 */   public static final Block WOODEN_BUTTON = get("wooden_button");
/* 178 */   public static final BlockSkull SKULL = (BlockSkull)get("skull");
/* 179 */   public static final Block ANVIL = get("anvil");
/* 180 */   public static final Block TRAPPED_CHEST = get("trapped_chest");
/* 181 */   public static final Block LIGHT_WEIGHTED_PRESSURE_PLATE = get("light_weighted_pressure_plate");
/* 182 */   public static final Block HEAVY_WEIGHTED_PRESSURE_PLATE = get("heavy_weighted_pressure_plate");
/* 183 */   public static final BlockRedstoneComparator UNPOWERED_COMPARATOR = (BlockRedstoneComparator)get("unpowered_comparator");
/* 184 */   public static final BlockRedstoneComparator POWERED_COMPARATOR = (BlockRedstoneComparator)get("powered_comparator");
/* 185 */   public static final BlockDaylightDetector DAYLIGHT_DETECTOR = (BlockDaylightDetector)get("daylight_detector");
/* 186 */   public static final BlockDaylightDetector DAYLIGHT_DETECTOR_INVERTED = (BlockDaylightDetector)get("daylight_detector_inverted");
/* 187 */   public static final Block REDSTONE_BLOCK = get("redstone_block");
/* 188 */   public static final Block QUARTZ_ORE = get("quartz_ore");
/* 189 */   public static final BlockHopper HOPPER = (BlockHopper)get("hopper");
/* 190 */   public static final Block QUARTZ_BLOCK = get("quartz_block");
/* 191 */   public static final Block QUARTZ_STAIRS = get("quartz_stairs");
/* 192 */   public static final Block ACTIVATOR_RAIL = get("activator_rail");
/* 193 */   public static final Block DROPPER = get("dropper");
/* 194 */   public static final Block STAINED_HARDENED_CLAY = get("stained_hardened_clay");
/* 195 */   public static final Block BARRIER = get("barrier");
/* 196 */   public static final Block IRON_TRAPDOOR = get("iron_trapdoor");
/* 197 */   public static final Block HAY_BLOCK = get("hay_block");
/* 198 */   public static final Block CARPET = get("carpet");
/* 199 */   public static final Block HARDENED_CLAY = get("hardened_clay");
/* 200 */   public static final Block COAL_BLOCK = get("coal_block");
/* 201 */   public static final Block PACKED_ICE = get("packed_ice");
/* 202 */   public static final Block ACACIA_STAIRS = get("acacia_stairs");
/* 203 */   public static final Block DARK_OAK_STAIRS = get("dark_oak_stairs");
/* 204 */   public static final Block SLIME = get("slime");
/* 205 */   public static final BlockTallPlant DOUBLE_PLANT = (BlockTallPlant)get("double_plant");
/* 206 */   public static final BlockStainedGlass STAINED_GLASS = (BlockStainedGlass)get("stained_glass");
/* 207 */   public static final BlockStainedGlassPane STAINED_GLASS_PANE = (BlockStainedGlassPane)get("stained_glass_pane");
/* 208 */   public static final Block PRISMARINE = get("prismarine");
/* 209 */   public static final Block SEA_LANTERN = get("sea_lantern");
/* 210 */   public static final Block STANDING_BANNER = get("standing_banner");
/* 211 */   public static final Block WALL_BANNER = get("wall_banner");
/* 212 */   public static final Block RED_SANDSTONE = get("red_sandstone");
/* 213 */   public static final Block RED_SANDSTONE_STAIRS = get("red_sandstone_stairs");
/* 214 */   public static final BlockStepAbstract DOUBLE_STONE_SLAB2 = (BlockStepAbstract)get("double_stone_slab2");
/* 215 */   public static final BlockStepAbstract STONE_SLAB2 = (BlockStepAbstract)get("stone_slab2");
/*     */   
/*     */   private static Block get(String paramString)
/*     */   {
/* 219 */     return (Block)Block.REGISTRY.get(new MinecraftKey(paramString));
/*     */   }
/*     */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_8_R3\Blocks.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */
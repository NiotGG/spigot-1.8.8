/*     */ package net.minecraft.server.v1_8_R3;
/*     */ 
/*     */ import java.util.Iterator;
/*     */ import java.util.List;
/*     */ import java.util.Random;
/*     */ 
/*     */ public class WorldGenRegistration
/*     */ {
/*     */   public static void a()
/*     */   {
/*  11 */     WorldGenFactory.a(WorldGenPyramidPiece.class, "TeDP");
/*  12 */     WorldGenFactory.a(WorldGenJungleTemple.class, "TeJP");
/*  13 */     WorldGenFactory.a(WorldGenWitchHut.class, "TeSH");
/*     */   }
/*     */   
/*     */   static class SyntheticClass_1
/*     */   {
/*  18 */     static final int[] a = new int[EnumDirection.values().length];
/*     */     
/*     */     static {
/*     */       try {
/*  22 */         a[EnumDirection.NORTH.ordinal()] = 1;
/*     */       }
/*     */       catch (NoSuchFieldError localNoSuchFieldError1) {}
/*     */       
/*     */       try
/*     */       {
/*  28 */         a[EnumDirection.SOUTH.ordinal()] = 2;
/*     */       }
/*     */       catch (NoSuchFieldError localNoSuchFieldError2) {}
/*     */     }
/*     */   }
/*     */   
/*     */   public static class WorldGenWitchHut
/*     */     extends WorldGenRegistration.WorldGenScatteredPiece
/*     */   {
/*     */     private boolean e;
/*     */     
/*     */     public WorldGenWitchHut() {}
/*     */     
/*     */     public WorldGenWitchHut(Random random, int i, int j)
/*     */     {
/*  43 */       super(i, 64, j, 7, 7, 9);
/*     */     }
/*     */     
/*     */     protected void a(NBTTagCompound nbttagcompound) {
/*  47 */       super.a(nbttagcompound);
/*  48 */       nbttagcompound.setBoolean("Witch", this.e);
/*     */     }
/*     */     
/*     */     protected void b(NBTTagCompound nbttagcompound) {
/*  52 */       super.b(nbttagcompound);
/*  53 */       this.e = nbttagcompound.getBoolean("Witch");
/*     */     }
/*     */     
/*     */     public boolean a(World world, Random random, StructureBoundingBox structureboundingbox) {
/*  57 */       if (!a(world, structureboundingbox, 0)) {
/*  58 */         return false;
/*     */       }
/*  60 */       a(world, structureboundingbox, 1, 1, 1, 5, 1, 7, Blocks.PLANKS.fromLegacyData(BlockWood.EnumLogVariant.SPRUCE.a()), Blocks.PLANKS.fromLegacyData(BlockWood.EnumLogVariant.SPRUCE.a()), false);
/*  61 */       a(world, structureboundingbox, 1, 4, 2, 5, 4, 7, Blocks.PLANKS.fromLegacyData(BlockWood.EnumLogVariant.SPRUCE.a()), Blocks.PLANKS.fromLegacyData(BlockWood.EnumLogVariant.SPRUCE.a()), false);
/*  62 */       a(world, structureboundingbox, 2, 1, 0, 4, 1, 0, Blocks.PLANKS.fromLegacyData(BlockWood.EnumLogVariant.SPRUCE.a()), Blocks.PLANKS.fromLegacyData(BlockWood.EnumLogVariant.SPRUCE.a()), false);
/*  63 */       a(world, structureboundingbox, 2, 2, 2, 3, 3, 2, Blocks.PLANKS.fromLegacyData(BlockWood.EnumLogVariant.SPRUCE.a()), Blocks.PLANKS.fromLegacyData(BlockWood.EnumLogVariant.SPRUCE.a()), false);
/*  64 */       a(world, structureboundingbox, 1, 2, 3, 1, 3, 6, Blocks.PLANKS.fromLegacyData(BlockWood.EnumLogVariant.SPRUCE.a()), Blocks.PLANKS.fromLegacyData(BlockWood.EnumLogVariant.SPRUCE.a()), false);
/*  65 */       a(world, structureboundingbox, 5, 2, 3, 5, 3, 6, Blocks.PLANKS.fromLegacyData(BlockWood.EnumLogVariant.SPRUCE.a()), Blocks.PLANKS.fromLegacyData(BlockWood.EnumLogVariant.SPRUCE.a()), false);
/*  66 */       a(world, structureboundingbox, 2, 2, 7, 4, 3, 7, Blocks.PLANKS.fromLegacyData(BlockWood.EnumLogVariant.SPRUCE.a()), Blocks.PLANKS.fromLegacyData(BlockWood.EnumLogVariant.SPRUCE.a()), false);
/*  67 */       a(world, structureboundingbox, 1, 0, 2, 1, 3, 2, Blocks.LOG.getBlockData(), Blocks.LOG.getBlockData(), false);
/*  68 */       a(world, structureboundingbox, 5, 0, 2, 5, 3, 2, Blocks.LOG.getBlockData(), Blocks.LOG.getBlockData(), false);
/*  69 */       a(world, structureboundingbox, 1, 0, 7, 1, 3, 7, Blocks.LOG.getBlockData(), Blocks.LOG.getBlockData(), false);
/*  70 */       a(world, structureboundingbox, 5, 0, 7, 5, 3, 7, Blocks.LOG.getBlockData(), Blocks.LOG.getBlockData(), false);
/*  71 */       a(world, Blocks.FENCE.getBlockData(), 2, 3, 2, structureboundingbox);
/*  72 */       a(world, Blocks.FENCE.getBlockData(), 3, 3, 7, structureboundingbox);
/*  73 */       a(world, Blocks.AIR.getBlockData(), 1, 3, 4, structureboundingbox);
/*  74 */       a(world, Blocks.AIR.getBlockData(), 5, 3, 4, structureboundingbox);
/*  75 */       a(world, Blocks.AIR.getBlockData(), 5, 3, 5, structureboundingbox);
/*  76 */       a(world, Blocks.FLOWER_POT.getBlockData().set(BlockFlowerPot.CONTENTS, BlockFlowerPot.EnumFlowerPotContents.MUSHROOM_RED), 1, 3, 5, structureboundingbox);
/*  77 */       a(world, Blocks.CRAFTING_TABLE.getBlockData(), 3, 2, 6, structureboundingbox);
/*  78 */       a(world, Blocks.cauldron.getBlockData(), 4, 2, 6, structureboundingbox);
/*  79 */       a(world, Blocks.FENCE.getBlockData(), 1, 2, 1, structureboundingbox);
/*  80 */       a(world, Blocks.FENCE.getBlockData(), 5, 2, 1, structureboundingbox);
/*  81 */       int i = a(Blocks.OAK_STAIRS, 3);
/*  82 */       int j = a(Blocks.OAK_STAIRS, 1);
/*  83 */       int k = a(Blocks.OAK_STAIRS, 0);
/*  84 */       int l = a(Blocks.OAK_STAIRS, 2);
/*     */       
/*  86 */       a(world, structureboundingbox, 0, 4, 1, 6, 4, 1, Blocks.SPRUCE_STAIRS.fromLegacyData(i), Blocks.SPRUCE_STAIRS.fromLegacyData(i), false);
/*  87 */       a(world, structureboundingbox, 0, 4, 2, 0, 4, 7, Blocks.SPRUCE_STAIRS.fromLegacyData(k), Blocks.SPRUCE_STAIRS.fromLegacyData(k), false);
/*  88 */       a(world, structureboundingbox, 6, 4, 2, 6, 4, 7, Blocks.SPRUCE_STAIRS.fromLegacyData(j), Blocks.SPRUCE_STAIRS.fromLegacyData(j), false);
/*  89 */       a(world, structureboundingbox, 0, 4, 8, 6, 4, 8, Blocks.SPRUCE_STAIRS.fromLegacyData(l), Blocks.SPRUCE_STAIRS.fromLegacyData(l), false);
/*     */       
/*     */ 
/*     */ 
/*     */ 
/*  94 */       for (int i1 = 2; i1 <= 7; i1 += 5) {
/*  95 */         for (int j1 = 1; j1 <= 5; j1 += 4) {
/*  96 */           b(world, Blocks.LOG.getBlockData(), j1, -1, i1, structureboundingbox);
/*     */         }
/*     */       }
/*     */       
/* 100 */       if (!this.e) {
/* 101 */         i1 = a(2, 5);
/* 102 */         int j1 = d(2);
/* 103 */         int k1 = b(2, 5);
/*     */         
/* 105 */         if (structureboundingbox.b(new BlockPosition(i1, j1, k1))) {
/* 106 */           this.e = true;
/* 107 */           EntityWitch entitywitch = new EntityWitch(world);
/*     */           
/* 109 */           entitywitch.setPositionRotation(i1 + 0.5D, j1, k1 + 0.5D, 0.0F, 0.0F);
/* 110 */           entitywitch.prepare(world.E(new BlockPosition(i1, j1, k1)), null);
/* 111 */           world.addEntity(entitywitch, org.bukkit.event.entity.CreatureSpawnEvent.SpawnReason.CHUNK_GEN);
/*     */         }
/*     */       }
/*     */       
/* 115 */       return true;
/*     */     }
/*     */   }
/*     */   
/*     */   public static class WorldGenJungleTemple
/*     */     extends WorldGenRegistration.WorldGenScatteredPiece
/*     */   {
/*     */     private boolean e;
/*     */     private boolean f;
/*     */     private boolean g;
/*     */     private boolean h;
/* 126 */     private static final List<StructurePieceTreasure> i = com.google.common.collect.Lists.newArrayList(new StructurePieceTreasure[] { new StructurePieceTreasure(Items.DIAMOND, 0, 1, 3, 3), new StructurePieceTreasure(Items.IRON_INGOT, 0, 1, 5, 10), new StructurePieceTreasure(Items.GOLD_INGOT, 0, 2, 7, 15), new StructurePieceTreasure(Items.EMERALD, 0, 1, 3, 2), new StructurePieceTreasure(Items.BONE, 0, 4, 6, 20), new StructurePieceTreasure(Items.ROTTEN_FLESH, 0, 3, 7, 16), new StructurePieceTreasure(Items.SADDLE, 0, 1, 1, 3), new StructurePieceTreasure(Items.IRON_HORSE_ARMOR, 0, 1, 1, 1), new StructurePieceTreasure(Items.GOLDEN_HORSE_ARMOR, 0, 1, 1, 1), new StructurePieceTreasure(Items.DIAMOND_HORSE_ARMOR, 0, 1, 1, 1) });
/* 127 */     private static final List<StructurePieceTreasure> j = com.google.common.collect.Lists.newArrayList(new StructurePieceTreasure[] { new StructurePieceTreasure(Items.ARROW, 0, 2, 7, 30) });
/* 128 */     private static WorldGenJungleTemple.WorldGenJungleTemplePiece k = new WorldGenJungleTemple.WorldGenJungleTemplePiece(null);
/*     */     
/*     */     public WorldGenJungleTemple() {}
/*     */     
/*     */     public WorldGenJungleTemple(Random random, int i, int j) {
/* 133 */       super(i, 64, j, 12, 10, 15);
/*     */     }
/*     */     
/*     */     protected void a(NBTTagCompound nbttagcompound) {
/* 137 */       super.a(nbttagcompound);
/* 138 */       nbttagcompound.setBoolean("placedMainChest", this.e);
/* 139 */       nbttagcompound.setBoolean("placedHiddenChest", this.f);
/* 140 */       nbttagcompound.setBoolean("placedTrap1", this.g);
/* 141 */       nbttagcompound.setBoolean("placedTrap2", this.h);
/*     */     }
/*     */     
/*     */     protected void b(NBTTagCompound nbttagcompound) {
/* 145 */       super.b(nbttagcompound);
/* 146 */       this.e = nbttagcompound.getBoolean("placedMainChest");
/* 147 */       this.f = nbttagcompound.getBoolean("placedHiddenChest");
/* 148 */       this.g = nbttagcompound.getBoolean("placedTrap1");
/* 149 */       this.h = nbttagcompound.getBoolean("placedTrap2");
/*     */     }
/*     */     
/*     */     public boolean a(World world, Random random, StructureBoundingBox structureboundingbox) {
/* 153 */       if (!a(world, structureboundingbox, 0)) {
/* 154 */         return false;
/*     */       }
/* 156 */       int i = a(Blocks.STONE_STAIRS, 3);
/* 157 */       int j = a(Blocks.STONE_STAIRS, 2);
/* 158 */       int k = a(Blocks.STONE_STAIRS, 0);
/* 159 */       int l = a(Blocks.STONE_STAIRS, 1);
/*     */       
/* 161 */       a(world, structureboundingbox, 0, -4, 0, this.a - 1, 0, this.c - 1, false, random, k);
/* 162 */       a(world, structureboundingbox, 2, 1, 2, 9, 2, 2, false, random, k);
/* 163 */       a(world, structureboundingbox, 2, 1, 12, 9, 2, 12, false, random, k);
/* 164 */       a(world, structureboundingbox, 2, 1, 3, 2, 2, 11, false, random, k);
/* 165 */       a(world, structureboundingbox, 9, 1, 3, 9, 2, 11, false, random, k);
/* 166 */       a(world, structureboundingbox, 1, 3, 1, 10, 6, 1, false, random, k);
/* 167 */       a(world, structureboundingbox, 1, 3, 13, 10, 6, 13, false, random, k);
/* 168 */       a(world, structureboundingbox, 1, 3, 2, 1, 6, 12, false, random, k);
/* 169 */       a(world, structureboundingbox, 10, 3, 2, 10, 6, 12, false, random, k);
/* 170 */       a(world, structureboundingbox, 2, 3, 2, 9, 3, 12, false, random, k);
/* 171 */       a(world, structureboundingbox, 2, 6, 2, 9, 6, 12, false, random, k);
/* 172 */       a(world, structureboundingbox, 3, 7, 3, 8, 7, 11, false, random, k);
/* 173 */       a(world, structureboundingbox, 4, 8, 4, 7, 8, 10, false, random, k);
/* 174 */       a(world, structureboundingbox, 3, 1, 3, 8, 2, 11);
/* 175 */       a(world, structureboundingbox, 4, 3, 6, 7, 3, 9);
/* 176 */       a(world, structureboundingbox, 2, 4, 2, 9, 5, 12);
/* 177 */       a(world, structureboundingbox, 4, 6, 5, 7, 6, 9);
/* 178 */       a(world, structureboundingbox, 5, 7, 6, 6, 7, 8);
/* 179 */       a(world, structureboundingbox, 5, 1, 2, 6, 2, 2);
/* 180 */       a(world, structureboundingbox, 5, 2, 12, 6, 2, 12);
/* 181 */       a(world, structureboundingbox, 5, 5, 1, 6, 5, 1);
/* 182 */       a(world, structureboundingbox, 5, 5, 13, 6, 5, 13);
/* 183 */       a(world, Blocks.AIR.getBlockData(), 1, 5, 5, structureboundingbox);
/* 184 */       a(world, Blocks.AIR.getBlockData(), 10, 5, 5, structureboundingbox);
/* 185 */       a(world, Blocks.AIR.getBlockData(), 1, 5, 9, structureboundingbox);
/* 186 */       a(world, Blocks.AIR.getBlockData(), 10, 5, 9, structureboundingbox);
/*     */       
/*     */ 
/*     */ 
/* 190 */       for (int i1 = 0; i1 <= 14; i1 += 14) {
/* 191 */         a(world, structureboundingbox, 2, 4, i1, 2, 5, i1, false, random, k);
/* 192 */         a(world, structureboundingbox, 4, 4, i1, 4, 5, i1, false, random, k);
/* 193 */         a(world, structureboundingbox, 7, 4, i1, 7, 5, i1, false, random, k);
/* 194 */         a(world, structureboundingbox, 9, 4, i1, 9, 5, i1, false, random, k);
/*     */       }
/*     */       
/* 197 */       a(world, structureboundingbox, 5, 6, 0, 6, 6, 0, false, random, k);
/*     */       
/* 199 */       for (i1 = 0; i1 <= 11; i1 += 11) {
/* 200 */         for (int j1 = 2; j1 <= 12; j1 += 2) {
/* 201 */           a(world, structureboundingbox, i1, 4, j1, i1, 5, j1, false, random, k);
/*     */         }
/*     */         
/* 204 */         a(world, structureboundingbox, i1, 6, 5, i1, 6, 5, false, random, k);
/* 205 */         a(world, structureboundingbox, i1, 6, 9, i1, 6, 9, false, random, k);
/*     */       }
/*     */       
/* 208 */       a(world, structureboundingbox, 2, 7, 2, 2, 9, 2, false, random, k);
/* 209 */       a(world, structureboundingbox, 9, 7, 2, 9, 9, 2, false, random, k);
/* 210 */       a(world, structureboundingbox, 2, 7, 12, 2, 9, 12, false, random, k);
/* 211 */       a(world, structureboundingbox, 9, 7, 12, 9, 9, 12, false, random, k);
/* 212 */       a(world, structureboundingbox, 4, 9, 4, 4, 9, 4, false, random, k);
/* 213 */       a(world, structureboundingbox, 7, 9, 4, 7, 9, 4, false, random, k);
/* 214 */       a(world, structureboundingbox, 4, 9, 10, 4, 9, 10, false, random, k);
/* 215 */       a(world, structureboundingbox, 7, 9, 10, 7, 9, 10, false, random, k);
/* 216 */       a(world, structureboundingbox, 5, 9, 7, 6, 9, 7, false, random, k);
/* 217 */       a(world, Blocks.STONE_STAIRS.fromLegacyData(i), 5, 9, 6, structureboundingbox);
/* 218 */       a(world, Blocks.STONE_STAIRS.fromLegacyData(i), 6, 9, 6, structureboundingbox);
/* 219 */       a(world, Blocks.STONE_STAIRS.fromLegacyData(j), 5, 9, 8, structureboundingbox);
/* 220 */       a(world, Blocks.STONE_STAIRS.fromLegacyData(j), 6, 9, 8, structureboundingbox);
/* 221 */       a(world, Blocks.STONE_STAIRS.fromLegacyData(i), 4, 0, 0, structureboundingbox);
/* 222 */       a(world, Blocks.STONE_STAIRS.fromLegacyData(i), 5, 0, 0, structureboundingbox);
/* 223 */       a(world, Blocks.STONE_STAIRS.fromLegacyData(i), 6, 0, 0, structureboundingbox);
/* 224 */       a(world, Blocks.STONE_STAIRS.fromLegacyData(i), 7, 0, 0, structureboundingbox);
/* 225 */       a(world, Blocks.STONE_STAIRS.fromLegacyData(i), 4, 1, 8, structureboundingbox);
/* 226 */       a(world, Blocks.STONE_STAIRS.fromLegacyData(i), 4, 2, 9, structureboundingbox);
/* 227 */       a(world, Blocks.STONE_STAIRS.fromLegacyData(i), 4, 3, 10, structureboundingbox);
/* 228 */       a(world, Blocks.STONE_STAIRS.fromLegacyData(i), 7, 1, 8, structureboundingbox);
/* 229 */       a(world, Blocks.STONE_STAIRS.fromLegacyData(i), 7, 2, 9, structureboundingbox);
/* 230 */       a(world, Blocks.STONE_STAIRS.fromLegacyData(i), 7, 3, 10, structureboundingbox);
/* 231 */       a(world, structureboundingbox, 4, 1, 9, 4, 1, 9, false, random, k);
/* 232 */       a(world, structureboundingbox, 7, 1, 9, 7, 1, 9, false, random, k);
/* 233 */       a(world, structureboundingbox, 4, 1, 10, 7, 2, 10, false, random, k);
/* 234 */       a(world, structureboundingbox, 5, 4, 5, 6, 4, 5, false, random, k);
/* 235 */       a(world, Blocks.STONE_STAIRS.fromLegacyData(k), 4, 4, 5, structureboundingbox);
/* 236 */       a(world, Blocks.STONE_STAIRS.fromLegacyData(l), 7, 4, 5, structureboundingbox);
/*     */       
/* 238 */       for (i1 = 0; i1 < 4; i1++) {
/* 239 */         a(world, Blocks.STONE_STAIRS.fromLegacyData(j), 5, 0 - i1, 6 + i1, structureboundingbox);
/* 240 */         a(world, Blocks.STONE_STAIRS.fromLegacyData(j), 6, 0 - i1, 6 + i1, structureboundingbox);
/* 241 */         a(world, structureboundingbox, 5, 0 - i1, 7 + i1, 6, 0 - i1, 9 + i1);
/*     */       }
/*     */       
/* 244 */       a(world, structureboundingbox, 1, -3, 12, 10, -1, 13);
/* 245 */       a(world, structureboundingbox, 1, -3, 1, 3, -1, 13);
/* 246 */       a(world, structureboundingbox, 1, -3, 1, 9, -1, 5);
/*     */       
/* 248 */       for (i1 = 1; i1 <= 13; i1 += 2) {
/* 249 */         a(world, structureboundingbox, 1, -3, i1, 1, -2, i1, false, random, k);
/*     */       }
/*     */       
/* 252 */       for (i1 = 2; i1 <= 12; i1 += 2) {
/* 253 */         a(world, structureboundingbox, 1, -1, i1, 3, -1, i1, false, random, k);
/*     */       }
/*     */       
/* 256 */       a(world, structureboundingbox, 2, -2, 1, 5, -2, 1, false, random, k);
/* 257 */       a(world, structureboundingbox, 7, -2, 1, 9, -2, 1, false, random, k);
/* 258 */       a(world, structureboundingbox, 6, -3, 1, 6, -3, 1, false, random, k);
/* 259 */       a(world, structureboundingbox, 6, -1, 1, 6, -1, 1, false, random, k);
/* 260 */       a(world, Blocks.TRIPWIRE_HOOK.fromLegacyData(a(Blocks.TRIPWIRE_HOOK, EnumDirection.EAST.b())).set(BlockTripwireHook.ATTACHED, Boolean.valueOf(true)), 1, -3, 8, structureboundingbox);
/* 261 */       a(world, Blocks.TRIPWIRE_HOOK.fromLegacyData(a(Blocks.TRIPWIRE_HOOK, EnumDirection.WEST.b())).set(BlockTripwireHook.ATTACHED, Boolean.valueOf(true)), 4, -3, 8, structureboundingbox);
/* 262 */       a(world, Blocks.TRIPWIRE.getBlockData().set(BlockTripwire.ATTACHED, Boolean.valueOf(true)), 2, -3, 8, structureboundingbox);
/* 263 */       a(world, Blocks.TRIPWIRE.getBlockData().set(BlockTripwire.ATTACHED, Boolean.valueOf(true)), 3, -3, 8, structureboundingbox);
/* 264 */       a(world, Blocks.REDSTONE_WIRE.getBlockData(), 5, -3, 7, structureboundingbox);
/* 265 */       a(world, Blocks.REDSTONE_WIRE.getBlockData(), 5, -3, 6, structureboundingbox);
/* 266 */       a(world, Blocks.REDSTONE_WIRE.getBlockData(), 5, -3, 5, structureboundingbox);
/* 267 */       a(world, Blocks.REDSTONE_WIRE.getBlockData(), 5, -3, 4, structureboundingbox);
/* 268 */       a(world, Blocks.REDSTONE_WIRE.getBlockData(), 5, -3, 3, structureboundingbox);
/* 269 */       a(world, Blocks.REDSTONE_WIRE.getBlockData(), 5, -3, 2, structureboundingbox);
/* 270 */       a(world, Blocks.REDSTONE_WIRE.getBlockData(), 5, -3, 1, structureboundingbox);
/* 271 */       a(world, Blocks.REDSTONE_WIRE.getBlockData(), 4, -3, 1, structureboundingbox);
/* 272 */       a(world, Blocks.MOSSY_COBBLESTONE.getBlockData(), 3, -3, 1, structureboundingbox);
/* 273 */       if (!this.g) {
/* 274 */         this.g = a(world, structureboundingbox, random, 3, -2, 1, EnumDirection.NORTH.a(), j, 2);
/*     */       }
/*     */       
/* 277 */       a(world, Blocks.VINE.fromLegacyData(15), 3, -2, 2, structureboundingbox);
/* 278 */       a(world, Blocks.TRIPWIRE_HOOK.fromLegacyData(a(Blocks.TRIPWIRE_HOOK, EnumDirection.NORTH.b())).set(BlockTripwireHook.ATTACHED, Boolean.valueOf(true)), 7, -3, 1, structureboundingbox);
/* 279 */       a(world, Blocks.TRIPWIRE_HOOK.fromLegacyData(a(Blocks.TRIPWIRE_HOOK, EnumDirection.SOUTH.b())).set(BlockTripwireHook.ATTACHED, Boolean.valueOf(true)), 7, -3, 5, structureboundingbox);
/* 280 */       a(world, Blocks.TRIPWIRE.getBlockData().set(BlockTripwire.ATTACHED, Boolean.valueOf(true)), 7, -3, 2, structureboundingbox);
/* 281 */       a(world, Blocks.TRIPWIRE.getBlockData().set(BlockTripwire.ATTACHED, Boolean.valueOf(true)), 7, -3, 3, structureboundingbox);
/* 282 */       a(world, Blocks.TRIPWIRE.getBlockData().set(BlockTripwire.ATTACHED, Boolean.valueOf(true)), 7, -3, 4, structureboundingbox);
/* 283 */       a(world, Blocks.REDSTONE_WIRE.getBlockData(), 8, -3, 6, structureboundingbox);
/* 284 */       a(world, Blocks.REDSTONE_WIRE.getBlockData(), 9, -3, 6, structureboundingbox);
/* 285 */       a(world, Blocks.REDSTONE_WIRE.getBlockData(), 9, -3, 5, structureboundingbox);
/* 286 */       a(world, Blocks.MOSSY_COBBLESTONE.getBlockData(), 9, -3, 4, structureboundingbox);
/* 287 */       a(world, Blocks.REDSTONE_WIRE.getBlockData(), 9, -2, 4, structureboundingbox);
/* 288 */       if (!this.h) {
/* 289 */         this.h = a(world, structureboundingbox, random, 9, -2, 3, EnumDirection.WEST.a(), j, 2);
/*     */       }
/*     */       
/* 292 */       a(world, Blocks.VINE.fromLegacyData(15), 8, -1, 3, structureboundingbox);
/* 293 */       a(world, Blocks.VINE.fromLegacyData(15), 8, -2, 3, structureboundingbox);
/* 294 */       if (!this.e) {
/* 295 */         this.e = a(world, structureboundingbox, random, 8, -3, 3, StructurePieceTreasure.a(i, new StructurePieceTreasure[] { Items.ENCHANTED_BOOK.b(random) }), 2 + random.nextInt(5));
/*     */       }
/*     */       
/* 298 */       a(world, Blocks.MOSSY_COBBLESTONE.getBlockData(), 9, -3, 2, structureboundingbox);
/* 299 */       a(world, Blocks.MOSSY_COBBLESTONE.getBlockData(), 8, -3, 1, structureboundingbox);
/* 300 */       a(world, Blocks.MOSSY_COBBLESTONE.getBlockData(), 4, -3, 5, structureboundingbox);
/* 301 */       a(world, Blocks.MOSSY_COBBLESTONE.getBlockData(), 5, -2, 5, structureboundingbox);
/* 302 */       a(world, Blocks.MOSSY_COBBLESTONE.getBlockData(), 5, -1, 5, structureboundingbox);
/* 303 */       a(world, Blocks.MOSSY_COBBLESTONE.getBlockData(), 6, -3, 5, structureboundingbox);
/* 304 */       a(world, Blocks.MOSSY_COBBLESTONE.getBlockData(), 7, -2, 5, structureboundingbox);
/* 305 */       a(world, Blocks.MOSSY_COBBLESTONE.getBlockData(), 7, -1, 5, structureboundingbox);
/* 306 */       a(world, Blocks.MOSSY_COBBLESTONE.getBlockData(), 8, -3, 5, structureboundingbox);
/* 307 */       a(world, structureboundingbox, 9, -1, 1, 9, -1, 5, false, random, k);
/* 308 */       a(world, structureboundingbox, 8, -3, 8, 10, -1, 10);
/* 309 */       a(world, Blocks.STONEBRICK.fromLegacyData(BlockSmoothBrick.P), 8, -2, 11, structureboundingbox);
/* 310 */       a(world, Blocks.STONEBRICK.fromLegacyData(BlockSmoothBrick.P), 9, -2, 11, structureboundingbox);
/* 311 */       a(world, Blocks.STONEBRICK.fromLegacyData(BlockSmoothBrick.P), 10, -2, 11, structureboundingbox);
/* 312 */       a(world, Blocks.LEVER.fromLegacyData(BlockLever.a(EnumDirection.fromType1(a(Blocks.LEVER, EnumDirection.NORTH.a())))), 8, -2, 12, structureboundingbox);
/* 313 */       a(world, Blocks.LEVER.fromLegacyData(BlockLever.a(EnumDirection.fromType1(a(Blocks.LEVER, EnumDirection.NORTH.a())))), 9, -2, 12, structureboundingbox);
/* 314 */       a(world, Blocks.LEVER.fromLegacyData(BlockLever.a(EnumDirection.fromType1(a(Blocks.LEVER, EnumDirection.NORTH.a())))), 10, -2, 12, structureboundingbox);
/* 315 */       a(world, structureboundingbox, 8, -3, 8, 8, -3, 10, false, random, k);
/* 316 */       a(world, structureboundingbox, 10, -3, 8, 10, -3, 10, false, random, k);
/* 317 */       a(world, Blocks.MOSSY_COBBLESTONE.getBlockData(), 10, -2, 9, structureboundingbox);
/* 318 */       a(world, Blocks.REDSTONE_WIRE.getBlockData(), 8, -2, 9, structureboundingbox);
/* 319 */       a(world, Blocks.REDSTONE_WIRE.getBlockData(), 8, -2, 10, structureboundingbox);
/* 320 */       a(world, Blocks.REDSTONE_WIRE.getBlockData(), 10, -1, 9, structureboundingbox);
/* 321 */       a(world, Blocks.STICKY_PISTON.fromLegacyData(EnumDirection.UP.a()), 9, -2, 8, structureboundingbox);
/* 322 */       a(world, Blocks.STICKY_PISTON.fromLegacyData(a(Blocks.STICKY_PISTON, EnumDirection.WEST.a())), 10, -2, 8, structureboundingbox);
/* 323 */       a(world, Blocks.STICKY_PISTON.fromLegacyData(a(Blocks.STICKY_PISTON, EnumDirection.WEST.a())), 10, -1, 8, structureboundingbox);
/* 324 */       a(world, Blocks.UNPOWERED_REPEATER.fromLegacyData(a(Blocks.UNPOWERED_REPEATER, EnumDirection.NORTH.b())), 10, -2, 10, structureboundingbox);
/* 325 */       if (!this.f) {
/* 326 */         this.f = a(world, structureboundingbox, random, 9, -3, 10, StructurePieceTreasure.a(i, new StructurePieceTreasure[] { Items.ENCHANTED_BOOK.b(random) }), 2 + random.nextInt(5));
/*     */       }
/*     */       
/* 329 */       return true;
/*     */     }
/*     */     
/*     */     static class WorldGenJungleTemple$WorldGenJungleTemplePiece extends StructurePiece.StructurePieceBlockSelector
/*     */     {
/*     */       private WorldGenJungleTemple$WorldGenJungleTemplePiece() {}
/*     */       
/*     */       public void a(Random random, int i, int j, int k, boolean flag)
/*     */       {
/* 338 */         if (random.nextFloat() < 0.4F) {
/* 339 */           this.a = Blocks.COBBLESTONE.getBlockData();
/*     */         } else {
/* 341 */           this.a = Blocks.MOSSY_COBBLESTONE.getBlockData();
/*     */         }
/*     */       }
/*     */       
/*     */       WorldGenJungleTemple$WorldGenJungleTemplePiece(WorldGenRegistration.SyntheticClass_1 worldgenregistration_syntheticclass_1)
/*     */       {
/* 347 */         this();
/*     */       }
/*     */     }
/*     */   }
/*     */   
/*     */   public static class WorldGenPyramidPiece extends WorldGenRegistration.WorldGenScatteredPiece
/*     */   {
/* 354 */     private boolean[] e = new boolean[4];
/* 355 */     private static final List<StructurePieceTreasure> f = com.google.common.collect.Lists.newArrayList(new StructurePieceTreasure[] { new StructurePieceTreasure(Items.DIAMOND, 0, 1, 3, 3), new StructurePieceTreasure(Items.IRON_INGOT, 0, 1, 5, 10), new StructurePieceTreasure(Items.GOLD_INGOT, 0, 2, 7, 15), new StructurePieceTreasure(Items.EMERALD, 0, 1, 3, 2), new StructurePieceTreasure(Items.BONE, 0, 4, 6, 20), new StructurePieceTreasure(Items.ROTTEN_FLESH, 0, 3, 7, 16), new StructurePieceTreasure(Items.SADDLE, 0, 1, 1, 3), new StructurePieceTreasure(Items.IRON_HORSE_ARMOR, 0, 1, 1, 1), new StructurePieceTreasure(Items.GOLDEN_HORSE_ARMOR, 0, 1, 1, 1), new StructurePieceTreasure(Items.DIAMOND_HORSE_ARMOR, 0, 1, 1, 1) });
/*     */     
/*     */     public WorldGenPyramidPiece() {}
/*     */     
/*     */     public WorldGenPyramidPiece(Random random, int i, int j) {
/* 360 */       super(i, 64, j, 21, 15, 21);
/*     */     }
/*     */     
/*     */     protected void a(NBTTagCompound nbttagcompound) {
/* 364 */       super.a(nbttagcompound);
/* 365 */       nbttagcompound.setBoolean("hasPlacedChest0", this.e[0]);
/* 366 */       nbttagcompound.setBoolean("hasPlacedChest1", this.e[1]);
/* 367 */       nbttagcompound.setBoolean("hasPlacedChest2", this.e[2]);
/* 368 */       nbttagcompound.setBoolean("hasPlacedChest3", this.e[3]);
/*     */     }
/*     */     
/*     */     protected void b(NBTTagCompound nbttagcompound) {
/* 372 */       super.b(nbttagcompound);
/* 373 */       this.e[0] = nbttagcompound.getBoolean("hasPlacedChest0");
/* 374 */       this.e[1] = nbttagcompound.getBoolean("hasPlacedChest1");
/* 375 */       this.e[2] = nbttagcompound.getBoolean("hasPlacedChest2");
/* 376 */       this.e[3] = nbttagcompound.getBoolean("hasPlacedChest3");
/*     */     }
/*     */     
/*     */     public boolean a(World world, Random random, StructureBoundingBox structureboundingbox) {
/* 380 */       a(world, structureboundingbox, 0, -4, 0, this.a - 1, 0, this.c - 1, Blocks.SANDSTONE.getBlockData(), Blocks.SANDSTONE.getBlockData(), false);
/*     */       
/*     */ 
/*     */ 
/* 384 */       for (int i = 1; i <= 9; i++) {
/* 385 */         a(world, structureboundingbox, i, i, i, this.a - 1 - i, i, this.c - 1 - i, Blocks.SANDSTONE.getBlockData(), Blocks.SANDSTONE.getBlockData(), false);
/* 386 */         a(world, structureboundingbox, i + 1, i, i + 1, this.a - 2 - i, i, this.c - 2 - i, Blocks.AIR.getBlockData(), Blocks.AIR.getBlockData(), false);
/*     */       }
/*     */       
/*     */ 
/*     */ 
/* 391 */       for (i = 0; i < this.a; i++) {
/* 392 */         for (int j = 0; j < this.c; j++) {
/* 393 */           byte b0 = -5;
/*     */           
/* 395 */           b(world, Blocks.SANDSTONE.getBlockData(), i, b0, j, structureboundingbox);
/*     */         }
/*     */       }
/*     */       
/* 399 */       i = a(Blocks.SANDSTONE_STAIRS, 3);
/* 400 */       int j = a(Blocks.SANDSTONE_STAIRS, 2);
/* 401 */       int k = a(Blocks.SANDSTONE_STAIRS, 0);
/* 402 */       int l = a(Blocks.SANDSTONE_STAIRS, 1);
/* 403 */       int i1 = (EnumColor.ORANGE.getInvColorIndex() ^ 0xFFFFFFFF) & 0xF;
/* 404 */       int j1 = (EnumColor.BLUE.getInvColorIndex() ^ 0xFFFFFFFF) & 0xF;
/*     */       
/* 406 */       a(world, structureboundingbox, 0, 0, 0, 4, 9, 4, Blocks.SANDSTONE.getBlockData(), Blocks.AIR.getBlockData(), false);
/* 407 */       a(world, structureboundingbox, 1, 10, 1, 3, 10, 3, Blocks.SANDSTONE.getBlockData(), Blocks.SANDSTONE.getBlockData(), false);
/* 408 */       a(world, Blocks.SANDSTONE_STAIRS.fromLegacyData(i), 2, 10, 0, structureboundingbox);
/* 409 */       a(world, Blocks.SANDSTONE_STAIRS.fromLegacyData(j), 2, 10, 4, structureboundingbox);
/* 410 */       a(world, Blocks.SANDSTONE_STAIRS.fromLegacyData(k), 0, 10, 2, structureboundingbox);
/* 411 */       a(world, Blocks.SANDSTONE_STAIRS.fromLegacyData(l), 4, 10, 2, structureboundingbox);
/* 412 */       a(world, structureboundingbox, this.a - 5, 0, 0, this.a - 1, 9, 4, Blocks.SANDSTONE.getBlockData(), Blocks.AIR.getBlockData(), false);
/* 413 */       a(world, structureboundingbox, this.a - 4, 10, 1, this.a - 2, 10, 3, Blocks.SANDSTONE.getBlockData(), Blocks.SANDSTONE.getBlockData(), false);
/* 414 */       a(world, Blocks.SANDSTONE_STAIRS.fromLegacyData(i), this.a - 3, 10, 0, structureboundingbox);
/* 415 */       a(world, Blocks.SANDSTONE_STAIRS.fromLegacyData(j), this.a - 3, 10, 4, structureboundingbox);
/* 416 */       a(world, Blocks.SANDSTONE_STAIRS.fromLegacyData(k), this.a - 5, 10, 2, structureboundingbox);
/* 417 */       a(world, Blocks.SANDSTONE_STAIRS.fromLegacyData(l), this.a - 1, 10, 2, structureboundingbox);
/* 418 */       a(world, structureboundingbox, 8, 0, 0, 12, 4, 4, Blocks.SANDSTONE.getBlockData(), Blocks.AIR.getBlockData(), false);
/* 419 */       a(world, structureboundingbox, 9, 1, 0, 11, 3, 4, Blocks.AIR.getBlockData(), Blocks.AIR.getBlockData(), false);
/* 420 */       a(world, Blocks.SANDSTONE.fromLegacyData(BlockSandStone.EnumSandstoneVariant.SMOOTH.a()), 9, 1, 1, structureboundingbox);
/* 421 */       a(world, Blocks.SANDSTONE.fromLegacyData(BlockSandStone.EnumSandstoneVariant.SMOOTH.a()), 9, 2, 1, structureboundingbox);
/* 422 */       a(world, Blocks.SANDSTONE.fromLegacyData(BlockSandStone.EnumSandstoneVariant.SMOOTH.a()), 9, 3, 1, structureboundingbox);
/* 423 */       a(world, Blocks.SANDSTONE.fromLegacyData(BlockSandStone.EnumSandstoneVariant.SMOOTH.a()), 10, 3, 1, structureboundingbox);
/* 424 */       a(world, Blocks.SANDSTONE.fromLegacyData(BlockSandStone.EnumSandstoneVariant.SMOOTH.a()), 11, 3, 1, structureboundingbox);
/* 425 */       a(world, Blocks.SANDSTONE.fromLegacyData(BlockSandStone.EnumSandstoneVariant.SMOOTH.a()), 11, 2, 1, structureboundingbox);
/* 426 */       a(world, Blocks.SANDSTONE.fromLegacyData(BlockSandStone.EnumSandstoneVariant.SMOOTH.a()), 11, 1, 1, structureboundingbox);
/* 427 */       a(world, structureboundingbox, 4, 1, 1, 8, 3, 3, Blocks.SANDSTONE.getBlockData(), Blocks.AIR.getBlockData(), false);
/* 428 */       a(world, structureboundingbox, 4, 1, 2, 8, 2, 2, Blocks.AIR.getBlockData(), Blocks.AIR.getBlockData(), false);
/* 429 */       a(world, structureboundingbox, 12, 1, 1, 16, 3, 3, Blocks.SANDSTONE.getBlockData(), Blocks.AIR.getBlockData(), false);
/* 430 */       a(world, structureboundingbox, 12, 1, 2, 16, 2, 2, Blocks.AIR.getBlockData(), Blocks.AIR.getBlockData(), false);
/* 431 */       a(world, structureboundingbox, 5, 4, 5, this.a - 6, 4, this.c - 6, Blocks.SANDSTONE.getBlockData(), Blocks.SANDSTONE.getBlockData(), false);
/* 432 */       a(world, structureboundingbox, 9, 4, 9, 11, 4, 11, Blocks.AIR.getBlockData(), Blocks.AIR.getBlockData(), false);
/* 433 */       a(world, structureboundingbox, 8, 1, 8, 8, 3, 8, Blocks.SANDSTONE.fromLegacyData(BlockSandStone.EnumSandstoneVariant.SMOOTH.a()), Blocks.SANDSTONE.fromLegacyData(BlockSandStone.EnumSandstoneVariant.SMOOTH.a()), false);
/* 434 */       a(world, structureboundingbox, 12, 1, 8, 12, 3, 8, Blocks.SANDSTONE.fromLegacyData(BlockSandStone.EnumSandstoneVariant.SMOOTH.a()), Blocks.SANDSTONE.fromLegacyData(BlockSandStone.EnumSandstoneVariant.SMOOTH.a()), false);
/* 435 */       a(world, structureboundingbox, 8, 1, 12, 8, 3, 12, Blocks.SANDSTONE.fromLegacyData(BlockSandStone.EnumSandstoneVariant.SMOOTH.a()), Blocks.SANDSTONE.fromLegacyData(BlockSandStone.EnumSandstoneVariant.SMOOTH.a()), false);
/* 436 */       a(world, structureboundingbox, 12, 1, 12, 12, 3, 12, Blocks.SANDSTONE.fromLegacyData(BlockSandStone.EnumSandstoneVariant.SMOOTH.a()), Blocks.SANDSTONE.fromLegacyData(BlockSandStone.EnumSandstoneVariant.SMOOTH.a()), false);
/* 437 */       a(world, structureboundingbox, 1, 1, 5, 4, 4, 11, Blocks.SANDSTONE.getBlockData(), Blocks.SANDSTONE.getBlockData(), false);
/* 438 */       a(world, structureboundingbox, this.a - 5, 1, 5, this.a - 2, 4, 11, Blocks.SANDSTONE.getBlockData(), Blocks.SANDSTONE.getBlockData(), false);
/* 439 */       a(world, structureboundingbox, 6, 7, 9, 6, 7, 11, Blocks.SANDSTONE.getBlockData(), Blocks.SANDSTONE.getBlockData(), false);
/* 440 */       a(world, structureboundingbox, this.a - 7, 7, 9, this.a - 7, 7, 11, Blocks.SANDSTONE.getBlockData(), Blocks.SANDSTONE.getBlockData(), false);
/* 441 */       a(world, structureboundingbox, 5, 5, 9, 5, 7, 11, Blocks.SANDSTONE.fromLegacyData(BlockSandStone.EnumSandstoneVariant.SMOOTH.a()), Blocks.SANDSTONE.fromLegacyData(BlockSandStone.EnumSandstoneVariant.SMOOTH.a()), false);
/* 442 */       a(world, structureboundingbox, this.a - 6, 5, 9, this.a - 6, 7, 11, Blocks.SANDSTONE.fromLegacyData(BlockSandStone.EnumSandstoneVariant.SMOOTH.a()), Blocks.SANDSTONE.fromLegacyData(BlockSandStone.EnumSandstoneVariant.SMOOTH.a()), false);
/* 443 */       a(world, Blocks.AIR.getBlockData(), 5, 5, 10, structureboundingbox);
/* 444 */       a(world, Blocks.AIR.getBlockData(), 5, 6, 10, structureboundingbox);
/* 445 */       a(world, Blocks.AIR.getBlockData(), 6, 6, 10, structureboundingbox);
/* 446 */       a(world, Blocks.AIR.getBlockData(), this.a - 6, 5, 10, structureboundingbox);
/* 447 */       a(world, Blocks.AIR.getBlockData(), this.a - 6, 6, 10, structureboundingbox);
/* 448 */       a(world, Blocks.AIR.getBlockData(), this.a - 7, 6, 10, structureboundingbox);
/* 449 */       a(world, structureboundingbox, 2, 4, 4, 2, 6, 4, Blocks.AIR.getBlockData(), Blocks.AIR.getBlockData(), false);
/* 450 */       a(world, structureboundingbox, this.a - 3, 4, 4, this.a - 3, 6, 4, Blocks.AIR.getBlockData(), Blocks.AIR.getBlockData(), false);
/* 451 */       a(world, Blocks.SANDSTONE_STAIRS.fromLegacyData(i), 2, 4, 5, structureboundingbox);
/* 452 */       a(world, Blocks.SANDSTONE_STAIRS.fromLegacyData(i), 2, 3, 4, structureboundingbox);
/* 453 */       a(world, Blocks.SANDSTONE_STAIRS.fromLegacyData(i), this.a - 3, 4, 5, structureboundingbox);
/* 454 */       a(world, Blocks.SANDSTONE_STAIRS.fromLegacyData(i), this.a - 3, 3, 4, structureboundingbox);
/* 455 */       a(world, structureboundingbox, 1, 1, 3, 2, 2, 3, Blocks.SANDSTONE.getBlockData(), Blocks.SANDSTONE.getBlockData(), false);
/* 456 */       a(world, structureboundingbox, this.a - 3, 1, 3, this.a - 2, 2, 3, Blocks.SANDSTONE.getBlockData(), Blocks.SANDSTONE.getBlockData(), false);
/* 457 */       a(world, Blocks.SANDSTONE_STAIRS.getBlockData(), 1, 1, 2, structureboundingbox);
/* 458 */       a(world, Blocks.SANDSTONE_STAIRS.getBlockData(), this.a - 2, 1, 2, structureboundingbox);
/* 459 */       a(world, Blocks.STONE_SLAB.fromLegacyData(BlockDoubleStepAbstract.EnumStoneSlabVariant.SAND.a()), 1, 2, 2, structureboundingbox);
/* 460 */       a(world, Blocks.STONE_SLAB.fromLegacyData(BlockDoubleStepAbstract.EnumStoneSlabVariant.SAND.a()), this.a - 2, 2, 2, structureboundingbox);
/* 461 */       a(world, Blocks.SANDSTONE_STAIRS.fromLegacyData(l), 2, 1, 2, structureboundingbox);
/* 462 */       a(world, Blocks.SANDSTONE_STAIRS.fromLegacyData(k), this.a - 3, 1, 2, structureboundingbox);
/* 463 */       a(world, structureboundingbox, 4, 3, 5, 4, 3, 18, Blocks.SANDSTONE.getBlockData(), Blocks.SANDSTONE.getBlockData(), false);
/* 464 */       a(world, structureboundingbox, this.a - 5, 3, 5, this.a - 5, 3, 17, Blocks.SANDSTONE.getBlockData(), Blocks.SANDSTONE.getBlockData(), false);
/* 465 */       a(world, structureboundingbox, 3, 1, 5, 4, 2, 16, Blocks.AIR.getBlockData(), Blocks.AIR.getBlockData(), false);
/* 466 */       a(world, structureboundingbox, this.a - 6, 1, 5, this.a - 5, 2, 16, Blocks.AIR.getBlockData(), Blocks.AIR.getBlockData(), false);
/*     */       
/*     */ 
/*     */ 
/* 470 */       for (int k1 = 5; k1 <= 17; k1 += 2) {
/* 471 */         a(world, Blocks.SANDSTONE.fromLegacyData(BlockSandStone.EnumSandstoneVariant.SMOOTH.a()), 4, 1, k1, structureboundingbox);
/* 472 */         a(world, Blocks.SANDSTONE.fromLegacyData(BlockSandStone.EnumSandstoneVariant.CHISELED.a()), 4, 2, k1, structureboundingbox);
/* 473 */         a(world, Blocks.SANDSTONE.fromLegacyData(BlockSandStone.EnumSandstoneVariant.SMOOTH.a()), this.a - 5, 1, k1, structureboundingbox);
/* 474 */         a(world, Blocks.SANDSTONE.fromLegacyData(BlockSandStone.EnumSandstoneVariant.CHISELED.a()), this.a - 5, 2, k1, structureboundingbox);
/*     */       }
/*     */       
/* 477 */       a(world, Blocks.STAINED_HARDENED_CLAY.fromLegacyData(i1), 10, 0, 7, structureboundingbox);
/* 478 */       a(world, Blocks.STAINED_HARDENED_CLAY.fromLegacyData(i1), 10, 0, 8, structureboundingbox);
/* 479 */       a(world, Blocks.STAINED_HARDENED_CLAY.fromLegacyData(i1), 9, 0, 9, structureboundingbox);
/* 480 */       a(world, Blocks.STAINED_HARDENED_CLAY.fromLegacyData(i1), 11, 0, 9, structureboundingbox);
/* 481 */       a(world, Blocks.STAINED_HARDENED_CLAY.fromLegacyData(i1), 8, 0, 10, structureboundingbox);
/* 482 */       a(world, Blocks.STAINED_HARDENED_CLAY.fromLegacyData(i1), 12, 0, 10, structureboundingbox);
/* 483 */       a(world, Blocks.STAINED_HARDENED_CLAY.fromLegacyData(i1), 7, 0, 10, structureboundingbox);
/* 484 */       a(world, Blocks.STAINED_HARDENED_CLAY.fromLegacyData(i1), 13, 0, 10, structureboundingbox);
/* 485 */       a(world, Blocks.STAINED_HARDENED_CLAY.fromLegacyData(i1), 9, 0, 11, structureboundingbox);
/* 486 */       a(world, Blocks.STAINED_HARDENED_CLAY.fromLegacyData(i1), 11, 0, 11, structureboundingbox);
/* 487 */       a(world, Blocks.STAINED_HARDENED_CLAY.fromLegacyData(i1), 10, 0, 12, structureboundingbox);
/* 488 */       a(world, Blocks.STAINED_HARDENED_CLAY.fromLegacyData(i1), 10, 0, 13, structureboundingbox);
/* 489 */       a(world, Blocks.STAINED_HARDENED_CLAY.fromLegacyData(j1), 10, 0, 10, structureboundingbox);
/*     */       
/* 491 */       for (k1 = 0; k1 <= this.a - 1; k1 += this.a - 1) {
/* 492 */         a(world, Blocks.SANDSTONE.fromLegacyData(BlockSandStone.EnumSandstoneVariant.SMOOTH.a()), k1, 2, 1, structureboundingbox);
/* 493 */         a(world, Blocks.STAINED_HARDENED_CLAY.fromLegacyData(i1), k1, 2, 2, structureboundingbox);
/* 494 */         a(world, Blocks.SANDSTONE.fromLegacyData(BlockSandStone.EnumSandstoneVariant.SMOOTH.a()), k1, 2, 3, structureboundingbox);
/* 495 */         a(world, Blocks.SANDSTONE.fromLegacyData(BlockSandStone.EnumSandstoneVariant.SMOOTH.a()), k1, 3, 1, structureboundingbox);
/* 496 */         a(world, Blocks.STAINED_HARDENED_CLAY.fromLegacyData(i1), k1, 3, 2, structureboundingbox);
/* 497 */         a(world, Blocks.SANDSTONE.fromLegacyData(BlockSandStone.EnumSandstoneVariant.SMOOTH.a()), k1, 3, 3, structureboundingbox);
/* 498 */         a(world, Blocks.STAINED_HARDENED_CLAY.fromLegacyData(i1), k1, 4, 1, structureboundingbox);
/* 499 */         a(world, Blocks.SANDSTONE.fromLegacyData(BlockSandStone.EnumSandstoneVariant.CHISELED.a()), k1, 4, 2, structureboundingbox);
/* 500 */         a(world, Blocks.STAINED_HARDENED_CLAY.fromLegacyData(i1), k1, 4, 3, structureboundingbox);
/* 501 */         a(world, Blocks.SANDSTONE.fromLegacyData(BlockSandStone.EnumSandstoneVariant.SMOOTH.a()), k1, 5, 1, structureboundingbox);
/* 502 */         a(world, Blocks.STAINED_HARDENED_CLAY.fromLegacyData(i1), k1, 5, 2, structureboundingbox);
/* 503 */         a(world, Blocks.SANDSTONE.fromLegacyData(BlockSandStone.EnumSandstoneVariant.SMOOTH.a()), k1, 5, 3, structureboundingbox);
/* 504 */         a(world, Blocks.STAINED_HARDENED_CLAY.fromLegacyData(i1), k1, 6, 1, structureboundingbox);
/* 505 */         a(world, Blocks.SANDSTONE.fromLegacyData(BlockSandStone.EnumSandstoneVariant.CHISELED.a()), k1, 6, 2, structureboundingbox);
/* 506 */         a(world, Blocks.STAINED_HARDENED_CLAY.fromLegacyData(i1), k1, 6, 3, structureboundingbox);
/* 507 */         a(world, Blocks.STAINED_HARDENED_CLAY.fromLegacyData(i1), k1, 7, 1, structureboundingbox);
/* 508 */         a(world, Blocks.STAINED_HARDENED_CLAY.fromLegacyData(i1), k1, 7, 2, structureboundingbox);
/* 509 */         a(world, Blocks.STAINED_HARDENED_CLAY.fromLegacyData(i1), k1, 7, 3, structureboundingbox);
/* 510 */         a(world, Blocks.SANDSTONE.fromLegacyData(BlockSandStone.EnumSandstoneVariant.SMOOTH.a()), k1, 8, 1, structureboundingbox);
/* 511 */         a(world, Blocks.SANDSTONE.fromLegacyData(BlockSandStone.EnumSandstoneVariant.SMOOTH.a()), k1, 8, 2, structureboundingbox);
/* 512 */         a(world, Blocks.SANDSTONE.fromLegacyData(BlockSandStone.EnumSandstoneVariant.SMOOTH.a()), k1, 8, 3, structureboundingbox);
/*     */       }
/*     */       
/* 515 */       for (k1 = 2; k1 <= this.a - 3; k1 += this.a - 3 - 2) {
/* 516 */         a(world, Blocks.SANDSTONE.fromLegacyData(BlockSandStone.EnumSandstoneVariant.SMOOTH.a()), k1 - 1, 2, 0, structureboundingbox);
/* 517 */         a(world, Blocks.STAINED_HARDENED_CLAY.fromLegacyData(i1), k1, 2, 0, structureboundingbox);
/* 518 */         a(world, Blocks.SANDSTONE.fromLegacyData(BlockSandStone.EnumSandstoneVariant.SMOOTH.a()), k1 + 1, 2, 0, structureboundingbox);
/* 519 */         a(world, Blocks.SANDSTONE.fromLegacyData(BlockSandStone.EnumSandstoneVariant.SMOOTH.a()), k1 - 1, 3, 0, structureboundingbox);
/* 520 */         a(world, Blocks.STAINED_HARDENED_CLAY.fromLegacyData(i1), k1, 3, 0, structureboundingbox);
/* 521 */         a(world, Blocks.SANDSTONE.fromLegacyData(BlockSandStone.EnumSandstoneVariant.SMOOTH.a()), k1 + 1, 3, 0, structureboundingbox);
/* 522 */         a(world, Blocks.STAINED_HARDENED_CLAY.fromLegacyData(i1), k1 - 1, 4, 0, structureboundingbox);
/* 523 */         a(world, Blocks.SANDSTONE.fromLegacyData(BlockSandStone.EnumSandstoneVariant.CHISELED.a()), k1, 4, 0, structureboundingbox);
/* 524 */         a(world, Blocks.STAINED_HARDENED_CLAY.fromLegacyData(i1), k1 + 1, 4, 0, structureboundingbox);
/* 525 */         a(world, Blocks.SANDSTONE.fromLegacyData(BlockSandStone.EnumSandstoneVariant.SMOOTH.a()), k1 - 1, 5, 0, structureboundingbox);
/* 526 */         a(world, Blocks.STAINED_HARDENED_CLAY.fromLegacyData(i1), k1, 5, 0, structureboundingbox);
/* 527 */         a(world, Blocks.SANDSTONE.fromLegacyData(BlockSandStone.EnumSandstoneVariant.SMOOTH.a()), k1 + 1, 5, 0, structureboundingbox);
/* 528 */         a(world, Blocks.STAINED_HARDENED_CLAY.fromLegacyData(i1), k1 - 1, 6, 0, structureboundingbox);
/* 529 */         a(world, Blocks.SANDSTONE.fromLegacyData(BlockSandStone.EnumSandstoneVariant.CHISELED.a()), k1, 6, 0, structureboundingbox);
/* 530 */         a(world, Blocks.STAINED_HARDENED_CLAY.fromLegacyData(i1), k1 + 1, 6, 0, structureboundingbox);
/* 531 */         a(world, Blocks.STAINED_HARDENED_CLAY.fromLegacyData(i1), k1 - 1, 7, 0, structureboundingbox);
/* 532 */         a(world, Blocks.STAINED_HARDENED_CLAY.fromLegacyData(i1), k1, 7, 0, structureboundingbox);
/* 533 */         a(world, Blocks.STAINED_HARDENED_CLAY.fromLegacyData(i1), k1 + 1, 7, 0, structureboundingbox);
/* 534 */         a(world, Blocks.SANDSTONE.fromLegacyData(BlockSandStone.EnumSandstoneVariant.SMOOTH.a()), k1 - 1, 8, 0, structureboundingbox);
/* 535 */         a(world, Blocks.SANDSTONE.fromLegacyData(BlockSandStone.EnumSandstoneVariant.SMOOTH.a()), k1, 8, 0, structureboundingbox);
/* 536 */         a(world, Blocks.SANDSTONE.fromLegacyData(BlockSandStone.EnumSandstoneVariant.SMOOTH.a()), k1 + 1, 8, 0, structureboundingbox);
/*     */       }
/*     */       
/* 539 */       a(world, structureboundingbox, 8, 4, 0, 12, 6, 0, Blocks.SANDSTONE.fromLegacyData(BlockSandStone.EnumSandstoneVariant.SMOOTH.a()), Blocks.SANDSTONE.fromLegacyData(BlockSandStone.EnumSandstoneVariant.SMOOTH.a()), false);
/* 540 */       a(world, Blocks.AIR.getBlockData(), 8, 6, 0, structureboundingbox);
/* 541 */       a(world, Blocks.AIR.getBlockData(), 12, 6, 0, structureboundingbox);
/* 542 */       a(world, Blocks.STAINED_HARDENED_CLAY.fromLegacyData(i1), 9, 5, 0, structureboundingbox);
/* 543 */       a(world, Blocks.SANDSTONE.fromLegacyData(BlockSandStone.EnumSandstoneVariant.CHISELED.a()), 10, 5, 0, structureboundingbox);
/* 544 */       a(world, Blocks.STAINED_HARDENED_CLAY.fromLegacyData(i1), 11, 5, 0, structureboundingbox);
/* 545 */       a(world, structureboundingbox, 8, -14, 8, 12, -11, 12, Blocks.SANDSTONE.fromLegacyData(BlockSandStone.EnumSandstoneVariant.SMOOTH.a()), Blocks.SANDSTONE.fromLegacyData(BlockSandStone.EnumSandstoneVariant.SMOOTH.a()), false);
/* 546 */       a(world, structureboundingbox, 8, -10, 8, 12, -10, 12, Blocks.SANDSTONE.fromLegacyData(BlockSandStone.EnumSandstoneVariant.CHISELED.a()), Blocks.SANDSTONE.fromLegacyData(BlockSandStone.EnumSandstoneVariant.CHISELED.a()), false);
/* 547 */       a(world, structureboundingbox, 8, -9, 8, 12, -9, 12, Blocks.SANDSTONE.fromLegacyData(BlockSandStone.EnumSandstoneVariant.SMOOTH.a()), Blocks.SANDSTONE.fromLegacyData(BlockSandStone.EnumSandstoneVariant.SMOOTH.a()), false);
/* 548 */       a(world, structureboundingbox, 8, -8, 8, 12, -1, 12, Blocks.SANDSTONE.getBlockData(), Blocks.SANDSTONE.getBlockData(), false);
/* 549 */       a(world, structureboundingbox, 9, -11, 9, 11, -1, 11, Blocks.AIR.getBlockData(), Blocks.AIR.getBlockData(), false);
/* 550 */       a(world, Blocks.STONE_PRESSURE_PLATE.getBlockData(), 10, -11, 10, structureboundingbox);
/* 551 */       a(world, structureboundingbox, 9, -13, 9, 11, -13, 11, Blocks.TNT.getBlockData(), Blocks.AIR.getBlockData(), false);
/* 552 */       a(world, Blocks.AIR.getBlockData(), 8, -11, 10, structureboundingbox);
/* 553 */       a(world, Blocks.AIR.getBlockData(), 8, -10, 10, structureboundingbox);
/* 554 */       a(world, Blocks.SANDSTONE.fromLegacyData(BlockSandStone.EnumSandstoneVariant.CHISELED.a()), 7, -10, 10, structureboundingbox);
/* 555 */       a(world, Blocks.SANDSTONE.fromLegacyData(BlockSandStone.EnumSandstoneVariant.SMOOTH.a()), 7, -11, 10, structureboundingbox);
/* 556 */       a(world, Blocks.AIR.getBlockData(), 12, -11, 10, structureboundingbox);
/* 557 */       a(world, Blocks.AIR.getBlockData(), 12, -10, 10, structureboundingbox);
/* 558 */       a(world, Blocks.SANDSTONE.fromLegacyData(BlockSandStone.EnumSandstoneVariant.CHISELED.a()), 13, -10, 10, structureboundingbox);
/* 559 */       a(world, Blocks.SANDSTONE.fromLegacyData(BlockSandStone.EnumSandstoneVariant.SMOOTH.a()), 13, -11, 10, structureboundingbox);
/* 560 */       a(world, Blocks.AIR.getBlockData(), 10, -11, 8, structureboundingbox);
/* 561 */       a(world, Blocks.AIR.getBlockData(), 10, -10, 8, structureboundingbox);
/* 562 */       a(world, Blocks.SANDSTONE.fromLegacyData(BlockSandStone.EnumSandstoneVariant.CHISELED.a()), 10, -10, 7, structureboundingbox);
/* 563 */       a(world, Blocks.SANDSTONE.fromLegacyData(BlockSandStone.EnumSandstoneVariant.SMOOTH.a()), 10, -11, 7, structureboundingbox);
/* 564 */       a(world, Blocks.AIR.getBlockData(), 10, -11, 12, structureboundingbox);
/* 565 */       a(world, Blocks.AIR.getBlockData(), 10, -10, 12, structureboundingbox);
/* 566 */       a(world, Blocks.SANDSTONE.fromLegacyData(BlockSandStone.EnumSandstoneVariant.CHISELED.a()), 10, -10, 13, structureboundingbox);
/* 567 */       a(world, Blocks.SANDSTONE.fromLegacyData(BlockSandStone.EnumSandstoneVariant.SMOOTH.a()), 10, -11, 13, structureboundingbox);
/* 568 */       Iterator iterator = EnumDirection.EnumDirectionLimit.HORIZONTAL.iterator();
/*     */       
/* 570 */       while (iterator.hasNext()) {
/* 571 */         EnumDirection enumdirection = (EnumDirection)iterator.next();
/*     */         
/* 573 */         if (this.e[enumdirection.b()] == 0) {
/* 574 */           int l1 = enumdirection.getAdjacentX() * 2;
/* 575 */           int i2 = enumdirection.getAdjacentZ() * 2;
/*     */           
/* 577 */           this.e[enumdirection.b()] = a(world, structureboundingbox, random, 10 + l1, -11, 10 + i2, StructurePieceTreasure.a(f, new StructurePieceTreasure[] { Items.ENCHANTED_BOOK.b(random) }), 2 + random.nextInt(5));
/*     */         }
/*     */       }
/*     */       
/* 581 */       return true;
/*     */     }
/*     */   }
/*     */   
/*     */   static abstract class WorldGenScatteredPiece extends StructurePiece
/*     */   {
/*     */     protected int a;
/*     */     protected int b;
/*     */     protected int c;
/* 590 */     protected int d = -1;
/*     */     
/*     */     public WorldGenScatteredPiece() {}
/*     */     
/*     */     protected WorldGenScatteredPiece(Random random, int i, int j, int k, int l, int i1, int j1) {
/* 595 */       super();
/* 596 */       this.a = l;
/* 597 */       this.b = i1;
/* 598 */       this.c = j1;
/* 599 */       this.m = EnumDirection.EnumDirectionLimit.HORIZONTAL.a(random);
/* 600 */       switch (WorldGenRegistration.SyntheticClass_1.a[this.m.ordinal()]) {
/*     */       case 1: 
/*     */       case 2: 
/* 603 */         this.l = new StructureBoundingBox(i, j, k, i + l - 1, j + i1 - 1, k + j1 - 1);
/* 604 */         break;
/*     */       
/*     */       default: 
/* 607 */         this.l = new StructureBoundingBox(i, j, k, i + j1 - 1, j + i1 - 1, k + l - 1);
/*     */       }
/*     */     }
/*     */     
/*     */     protected void a(NBTTagCompound nbttagcompound)
/*     */     {
/* 613 */       nbttagcompound.setInt("Width", this.a);
/* 614 */       nbttagcompound.setInt("Height", this.b);
/* 615 */       nbttagcompound.setInt("Depth", this.c);
/* 616 */       nbttagcompound.setInt("HPos", this.d);
/*     */     }
/*     */     
/*     */     protected void b(NBTTagCompound nbttagcompound) {
/* 620 */       this.a = nbttagcompound.getInt("Width");
/* 621 */       this.b = nbttagcompound.getInt("Height");
/* 622 */       this.c = nbttagcompound.getInt("Depth");
/* 623 */       this.d = nbttagcompound.getInt("HPos");
/*     */     }
/*     */     
/*     */     protected boolean a(World world, StructureBoundingBox structureboundingbox, int i) {
/* 627 */       if (this.d >= 0) {
/* 628 */         return true;
/*     */       }
/* 630 */       int j = 0;
/* 631 */       int k = 0;
/* 632 */       BlockPosition.MutableBlockPosition blockposition_mutableblockposition = new BlockPosition.MutableBlockPosition();
/*     */       
/* 634 */       for (int l = this.l.c; l <= this.l.f; l++) {
/* 635 */         for (int i1 = this.l.a; i1 <= this.l.d; i1++) {
/* 636 */           blockposition_mutableblockposition.c(i1, 64, l);
/* 637 */           if (structureboundingbox.b(blockposition_mutableblockposition)) {
/* 638 */             j += Math.max(world.r(blockposition_mutableblockposition).getY(), world.worldProvider.getSeaLevel());
/* 639 */             k++;
/*     */           }
/*     */         }
/*     */       }
/*     */       
/* 644 */       if (k == 0) {
/* 645 */         return false;
/*     */       }
/* 647 */       this.d = (j / k);
/* 648 */       this.l.a(0, this.d - this.l.b + i, 0);
/* 649 */       return true;
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_8_R3\WorldGenRegistration.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */
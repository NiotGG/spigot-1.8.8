/*     */ package net.minecraft.server.v1_8_R3;
/*     */ 
/*     */ import java.util.ArrayList;
/*     */ import java.util.List;
/*     */ import java.util.Random;
/*     */ import org.bukkit.Bukkit;
/*     */ import org.bukkit.Location;
/*     */ import org.bukkit.TreeType;
/*     */ import org.bukkit.block.BlockState;
/*     */ import org.bukkit.event.world.StructureGrowEvent;
/*     */ import org.bukkit.plugin.PluginManager;
/*     */ import org.spigotmc.SpigotWorldConfig;
/*     */ 
/*     */ public class BlockSapling extends BlockPlant implements IBlockFragilePlantElement
/*     */ {
/*  16 */   public static final BlockStateEnum<BlockWood.EnumLogVariant> TYPE = BlockStateEnum.of("type", BlockWood.EnumLogVariant.class);
/*  17 */   public static final BlockStateInteger STAGE = BlockStateInteger.of("stage", 0, 1);
/*     */   public static TreeType treeType;
/*     */   
/*     */   protected BlockSapling() {
/*  21 */     j(this.blockStateList.getBlockData().set(TYPE, BlockWood.EnumLogVariant.OAK).set(STAGE, Integer.valueOf(0)));
/*  22 */     float f = 0.4F;
/*     */     
/*  24 */     a(0.5F - f, 0.0F, 0.5F - f, 0.5F + f, f * 2.0F, 0.5F + f);
/*  25 */     a(CreativeModeTab.c);
/*     */   }
/*     */   
/*     */   public String getName() {
/*  29 */     return LocaleI18n.get(a() + "." + BlockWood.EnumLogVariant.OAK.d() + ".name");
/*     */   }
/*     */   
/*     */   public void b(World world, BlockPosition blockposition, IBlockData iblockdata, Random random) {
/*  33 */     if (!world.isClientSide) {
/*  34 */       super.b(world, blockposition, iblockdata, random);
/*  35 */       if ((world.getLightLevel(blockposition.up()) >= 9) && (random.nextInt(Math.max(2, (int)(world.growthOdds / world.spigotConfig.saplingModifier * 7.0F + 0.5F))) == 0))
/*     */       {
/*  37 */         world.captureTreeGeneration = true;
/*     */         
/*  39 */         grow(world, blockposition, iblockdata, random);
/*     */         
/*  41 */         world.captureTreeGeneration = false;
/*  42 */         if (world.capturedBlockStates.size() > 0) {
/*  43 */           TreeType treeType = treeType;
/*  44 */           treeType = null;
/*  45 */           Location location = new Location(world.getWorld(), blockposition.getX(), blockposition.getY(), blockposition.getZ());
/*  46 */           List<BlockState> blocks = (List)world.capturedBlockStates.clone();
/*  47 */           world.capturedBlockStates.clear();
/*  48 */           StructureGrowEvent event = null;
/*  49 */           if (treeType != null) {
/*  50 */             event = new StructureGrowEvent(location, treeType, false, null, blocks);
/*  51 */             Bukkit.getPluginManager().callEvent(event);
/*     */           }
/*  53 */           if ((event == null) || (!event.isCancelled())) {
/*  54 */             for (BlockState blockstate : blocks) {
/*  55 */               blockstate.update(true);
/*     */             }
/*     */           }
/*     */         }
/*     */       }
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */   public void grow(World world, BlockPosition blockposition, IBlockData iblockdata, Random random)
/*     */   {
/*  66 */     if (((Integer)iblockdata.get(STAGE)).intValue() == 0) {
/*  67 */       world.setTypeAndData(blockposition, iblockdata.a(STAGE), 4);
/*     */     } else {
/*  69 */       e(world, blockposition, iblockdata, random);
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */   public void e(World world, BlockPosition blockposition, IBlockData iblockdata, Random random)
/*     */   {
/*     */     Object object;
/*     */     Object object;
/*  78 */     if (random.nextInt(10) == 0) {
/*  79 */       treeType = TreeType.BIG_TREE;
/*  80 */       object = new WorldGenBigTree(true);
/*     */     } else {
/*  82 */       treeType = TreeType.TREE;
/*  83 */       object = new WorldGenTrees(true);
/*     */     }
/*     */     
/*  86 */     int i = 0;
/*  87 */     int j = 0;
/*  88 */     boolean flag = false;
/*     */     
/*     */ 
/*  91 */     switch (SyntheticClass_1.a[((BlockWood.EnumLogVariant)iblockdata.get(TYPE)).ordinal()])
/*     */     {
/*     */     case 1: 
/*  94 */       for (i = 0; i >= -1; i--) {
/*  95 */         for (j = 0; j >= -1; j--) {
/*  96 */           if (a(world, blockposition, i, j, BlockWood.EnumLogVariant.SPRUCE)) {
/*  97 */             treeType = TreeType.MEGA_REDWOOD;
/*  98 */             object = new WorldGenMegaTree(false, random.nextBoolean());
/*  99 */             flag = true;
/* 100 */             break;
/*     */           }
/*     */         }
/*     */       }
/*     */       
/* 105 */       if (!flag) {
/* 106 */         j = 0;
/* 107 */         i = 0;
/* 108 */         treeType = TreeType.REDWOOD;
/* 109 */         object = new WorldGenTaiga2(true);
/*     */       }
/* 111 */       break;
/*     */     
/*     */     case 2: 
/* 114 */       treeType = TreeType.BIRCH;
/* 115 */       object = new WorldGenForest(true, false);
/* 116 */       break;
/*     */     
/*     */     case 3: 
/* 119 */       IBlockData iblockdata1 = Blocks.LOG.getBlockData().set(BlockLog1.VARIANT, BlockWood.EnumLogVariant.JUNGLE);
/* 120 */       IBlockData iblockdata2 = Blocks.LEAVES.getBlockData().set(BlockLeaves1.VARIANT, BlockWood.EnumLogVariant.JUNGLE).set(BlockLeaves.CHECK_DECAY, Boolean.valueOf(false));
/*     */       
/*     */ 
/* 123 */       for (i = 0; i >= -1; i--) {
/* 124 */         for (j = 0; j >= -1; j--) {
/* 125 */           if (a(world, blockposition, i, j, BlockWood.EnumLogVariant.JUNGLE)) {
/* 126 */             treeType = TreeType.JUNGLE;
/* 127 */             object = new WorldGenJungleTree(true, 10, 20, iblockdata1, iblockdata2);
/* 128 */             flag = true;
/* 129 */             break;
/*     */           }
/*     */         }
/*     */       }
/*     */       
/* 134 */       if (!flag) {
/* 135 */         j = 0;
/* 136 */         i = 0;
/* 137 */         treeType = TreeType.SMALL_JUNGLE;
/* 138 */         object = new WorldGenTrees(true, 4 + random.nextInt(7), iblockdata1, iblockdata2, false);
/*     */       }
/* 140 */       break;
/*     */     
/*     */     case 4: 
/* 143 */       treeType = TreeType.ACACIA;
/* 144 */       object = new WorldGenAcaciaTree(true);
/* 145 */       break;
/*     */     
/*     */ 
/*     */     case 5: 
/* 149 */       for (i = 0; i >= -1; i--) {
/* 150 */         for (j = 0; j >= -1; j--) {
/* 151 */           if (a(world, blockposition, i, j, BlockWood.EnumLogVariant.DARK_OAK)) {
/* 152 */             treeType = TreeType.DARK_OAK;
/* 153 */             object = new WorldGenForestTree(true);
/* 154 */             flag = true;
/* 155 */             break;
/*     */           }
/*     */         }
/*     */       }
/*     */       
/* 160 */       if (!flag) {
/*     */         return;
/*     */       }
/*     */       
/*     */       break;
/*     */     }
/*     */     
/* 167 */     IBlockData iblockdata1 = Blocks.AIR.getBlockData();
/* 168 */     if (flag) {
/* 169 */       world.setTypeAndData(blockposition.a(i, 0, j), iblockdata1, 4);
/* 170 */       world.setTypeAndData(blockposition.a(i + 1, 0, j), iblockdata1, 4);
/* 171 */       world.setTypeAndData(blockposition.a(i, 0, j + 1), iblockdata1, 4);
/* 172 */       world.setTypeAndData(blockposition.a(i + 1, 0, j + 1), iblockdata1, 4);
/*     */     } else {
/* 174 */       world.setTypeAndData(blockposition, iblockdata1, 4);
/*     */     }
/*     */     
/* 177 */     if (!((WorldGenerator)object).generate(world, random, blockposition.a(i, 0, j))) {
/* 178 */       if (flag) {
/* 179 */         world.setTypeAndData(blockposition.a(i, 0, j), iblockdata, 4);
/* 180 */         world.setTypeAndData(blockposition.a(i + 1, 0, j), iblockdata, 4);
/* 181 */         world.setTypeAndData(blockposition.a(i, 0, j + 1), iblockdata, 4);
/* 182 */         world.setTypeAndData(blockposition.a(i + 1, 0, j + 1), iblockdata, 4);
/*     */       } else {
/* 184 */         world.setTypeAndData(blockposition, iblockdata, 4);
/*     */       }
/*     */     }
/*     */   }
/*     */   
/*     */   private boolean a(World world, BlockPosition blockposition, int i, int j, BlockWood.EnumLogVariant blockwood_enumlogvariant)
/*     */   {
/* 191 */     return (a(world, blockposition.a(i, 0, j), blockwood_enumlogvariant)) && (a(world, blockposition.a(i + 1, 0, j), blockwood_enumlogvariant)) && (a(world, blockposition.a(i, 0, j + 1), blockwood_enumlogvariant)) && (a(world, blockposition.a(i + 1, 0, j + 1), blockwood_enumlogvariant));
/*     */   }
/*     */   
/*     */   public boolean a(World world, BlockPosition blockposition, BlockWood.EnumLogVariant blockwood_enumlogvariant) {
/* 195 */     IBlockData iblockdata = world.getType(blockposition);
/*     */     
/* 197 */     return (iblockdata.getBlock() == this) && (iblockdata.get(TYPE) == blockwood_enumlogvariant);
/*     */   }
/*     */   
/*     */   public int getDropData(IBlockData iblockdata) {
/* 201 */     return ((BlockWood.EnumLogVariant)iblockdata.get(TYPE)).a();
/*     */   }
/*     */   
/*     */   public boolean a(World world, BlockPosition blockposition, IBlockData iblockdata, boolean flag) {
/* 205 */     return true;
/*     */   }
/*     */   
/*     */   public boolean a(World world, Random random, BlockPosition blockposition, IBlockData iblockdata) {
/* 209 */     return world.random.nextFloat() < 0.45D;
/*     */   }
/*     */   
/*     */   public void b(World world, Random random, BlockPosition blockposition, IBlockData iblockdata) {
/* 213 */     grow(world, blockposition, iblockdata, random);
/*     */   }
/*     */   
/*     */   public IBlockData fromLegacyData(int i) {
/* 217 */     return getBlockData().set(TYPE, BlockWood.EnumLogVariant.a(i & 0x7)).set(STAGE, Integer.valueOf((i & 0x8) >> 3));
/*     */   }
/*     */   
/*     */   public int toLegacyData(IBlockData iblockdata) {
/* 221 */     byte b0 = 0;
/* 222 */     int i = b0 | ((BlockWood.EnumLogVariant)iblockdata.get(TYPE)).a();
/*     */     
/* 224 */     i |= ((Integer)iblockdata.get(STAGE)).intValue() << 3;
/* 225 */     return i;
/*     */   }
/*     */   
/*     */   protected BlockStateList getStateList() {
/* 229 */     return new BlockStateList(this, new IBlockState[] { TYPE, STAGE });
/*     */   }
/*     */   
/*     */   static class SyntheticClass_1
/*     */   {
/* 234 */     static final int[] a = new int[BlockWood.EnumLogVariant.values().length];
/*     */     
/*     */     static {
/*     */       try {
/* 238 */         a[BlockWood.EnumLogVariant.SPRUCE.ordinal()] = 1;
/*     */       }
/*     */       catch (NoSuchFieldError localNoSuchFieldError1) {}
/*     */       
/*     */       try
/*     */       {
/* 244 */         a[BlockWood.EnumLogVariant.BIRCH.ordinal()] = 2;
/*     */       }
/*     */       catch (NoSuchFieldError localNoSuchFieldError2) {}
/*     */       
/*     */       try
/*     */       {
/* 250 */         a[BlockWood.EnumLogVariant.JUNGLE.ordinal()] = 3;
/*     */       }
/*     */       catch (NoSuchFieldError localNoSuchFieldError3) {}
/*     */       
/*     */       try
/*     */       {
/* 256 */         a[BlockWood.EnumLogVariant.ACACIA.ordinal()] = 4;
/*     */       }
/*     */       catch (NoSuchFieldError localNoSuchFieldError4) {}
/*     */       
/*     */       try
/*     */       {
/* 262 */         a[BlockWood.EnumLogVariant.DARK_OAK.ordinal()] = 5;
/*     */       }
/*     */       catch (NoSuchFieldError localNoSuchFieldError5) {}
/*     */       
/*     */       try
/*     */       {
/* 268 */         a[BlockWood.EnumLogVariant.OAK.ordinal()] = 6;
/*     */       }
/*     */       catch (NoSuchFieldError localNoSuchFieldError6) {}
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_8_R3\BlockSapling.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */
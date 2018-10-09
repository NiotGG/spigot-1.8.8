/*     */ package net.minecraft.server.v1_8_R3;
/*     */ 
/*     */ import java.util.Random;
/*     */ 
/*     */ public class BlockFlowerPot extends BlockContainer
/*     */ {
/*   7 */   public static final BlockStateInteger LEGACY_DATA = BlockStateInteger.of("legacy_data", 0, 15);
/*   8 */   public static final BlockStateEnum<EnumFlowerPotContents> CONTENTS = BlockStateEnum.of("contents", EnumFlowerPotContents.class);
/*     */   
/*     */   public BlockFlowerPot() {
/*  11 */     super(Material.ORIENTABLE);
/*  12 */     j(this.blockStateList.getBlockData().set(CONTENTS, EnumFlowerPotContents.EMPTY).set(LEGACY_DATA, Integer.valueOf(0)));
/*  13 */     j();
/*     */   }
/*     */   
/*     */   public String getName() {
/*  17 */     return LocaleI18n.get("item.flowerPot.name");
/*     */   }
/*     */   
/*     */   public void j() {
/*  21 */     float f = 0.375F;
/*  22 */     float f1 = f / 2.0F;
/*     */     
/*  24 */     a(0.5F - f1, 0.0F, 0.5F - f1, 0.5F + f1, f, 0.5F + f1);
/*     */   }
/*     */   
/*     */   public boolean c() {
/*  28 */     return false;
/*     */   }
/*     */   
/*     */   public int b() {
/*  32 */     return 3;
/*     */   }
/*     */   
/*     */   public boolean d() {
/*  36 */     return false;
/*     */   }
/*     */   
/*     */   public boolean interact(World world, BlockPosition blockposition, IBlockData iblockdata, EntityHuman entityhuman, EnumDirection enumdirection, float f, float f1, float f2) {
/*  40 */     ItemStack itemstack = entityhuman.inventory.getItemInHand();
/*     */     
/*  42 */     if ((itemstack != null) && ((itemstack.getItem() instanceof ItemBlock))) {
/*  43 */       TileEntityFlowerPot tileentityflowerpot = f(world, blockposition);
/*     */       
/*  45 */       if (tileentityflowerpot == null)
/*  46 */         return false;
/*  47 */       if (tileentityflowerpot.b() != null) {
/*  48 */         return false;
/*     */       }
/*  50 */       Block block = Block.asBlock(itemstack.getItem());
/*     */       
/*  52 */       if (!a(block, itemstack.getData())) {
/*  53 */         return false;
/*     */       }
/*  55 */       tileentityflowerpot.a(itemstack.getItem(), itemstack.getData());
/*  56 */       tileentityflowerpot.update();
/*  57 */       world.notify(blockposition);
/*  58 */       entityhuman.b(StatisticList.T);
/*  59 */       if (!entityhuman.abilities.canInstantlyBuild) { if (--itemstack.count <= 0) {
/*  60 */           entityhuman.inventory.setItem(entityhuman.inventory.itemInHandIndex, null);
/*     */         }
/*     */       }
/*  63 */       return true;
/*     */     }
/*     */     
/*     */ 
/*  67 */     return false;
/*     */   }
/*     */   
/*     */   private boolean a(Block block, int i)
/*     */   {
/*  72 */     return (block == Blocks.TALLGRASS) && (i == BlockLongGrass.EnumTallGrassType.FERN.a());
/*     */   }
/*     */   
/*     */   public int getDropData(World world, BlockPosition blockposition) {
/*  76 */     TileEntityFlowerPot tileentityflowerpot = f(world, blockposition);
/*     */     
/*  78 */     return (tileentityflowerpot != null) && (tileentityflowerpot.b() != null) ? tileentityflowerpot.c() : 0;
/*     */   }
/*     */   
/*     */   public boolean canPlace(World world, BlockPosition blockposition) {
/*  82 */     return (super.canPlace(world, blockposition)) && (World.a(world, blockposition.down()));
/*     */   }
/*     */   
/*     */   public void doPhysics(World world, BlockPosition blockposition, IBlockData iblockdata, Block block) {
/*  86 */     if (!World.a(world, blockposition.down())) {
/*  87 */       b(world, blockposition, iblockdata, 0);
/*  88 */       world.setAir(blockposition);
/*     */     }
/*     */   }
/*     */   
/*     */   public void remove(World world, BlockPosition blockposition, IBlockData iblockdata)
/*     */   {
/*  94 */     TileEntityFlowerPot tileentityflowerpot = f(world, blockposition);
/*     */     
/*  96 */     if ((tileentityflowerpot != null) && (tileentityflowerpot.b() != null)) {
/*  97 */       a(world, blockposition, new ItemStack(tileentityflowerpot.b(), 1, tileentityflowerpot.c()));
/*  98 */       tileentityflowerpot.a(null, 0);
/*     */     }
/*     */     
/* 101 */     super.remove(world, blockposition, iblockdata);
/*     */   }
/*     */   
/*     */   public void a(World world, BlockPosition blockposition, IBlockData iblockdata, EntityHuman entityhuman) {
/* 105 */     super.a(world, blockposition, iblockdata, entityhuman);
/* 106 */     if (entityhuman.abilities.canInstantlyBuild) {
/* 107 */       TileEntityFlowerPot tileentityflowerpot = f(world, blockposition);
/*     */       
/* 109 */       if (tileentityflowerpot != null) {
/* 110 */         tileentityflowerpot.a(null, 0);
/*     */       }
/*     */     }
/*     */   }
/*     */   
/*     */   public Item getDropType(IBlockData iblockdata, Random random, int i)
/*     */   {
/* 117 */     return Items.FLOWER_POT;
/*     */   }
/*     */   
/*     */   private TileEntityFlowerPot f(World world, BlockPosition blockposition) {
/* 121 */     TileEntity tileentity = world.getTileEntity(blockposition);
/*     */     
/* 123 */     return (tileentity instanceof TileEntityFlowerPot) ? (TileEntityFlowerPot)tileentity : null;
/*     */   }
/*     */   
/*     */   public TileEntity a(World world, int i) {
/* 127 */     Object object = null;
/* 128 */     int j = 0;
/*     */     
/* 130 */     switch (i) {
/*     */     case 1: 
/* 132 */       object = Blocks.RED_FLOWER;
/* 133 */       j = BlockFlowers.EnumFlowerVarient.POPPY.b();
/* 134 */       break;
/*     */     
/*     */     case 2: 
/* 137 */       object = Blocks.YELLOW_FLOWER;
/* 138 */       break;
/*     */     
/*     */     case 3: 
/* 141 */       object = Blocks.SAPLING;
/* 142 */       j = BlockWood.EnumLogVariant.OAK.a();
/* 143 */       break;
/*     */     
/*     */     case 4: 
/* 146 */       object = Blocks.SAPLING;
/* 147 */       j = BlockWood.EnumLogVariant.SPRUCE.a();
/* 148 */       break;
/*     */     
/*     */     case 5: 
/* 151 */       object = Blocks.SAPLING;
/* 152 */       j = BlockWood.EnumLogVariant.BIRCH.a();
/* 153 */       break;
/*     */     
/*     */     case 6: 
/* 156 */       object = Blocks.SAPLING;
/* 157 */       j = BlockWood.EnumLogVariant.JUNGLE.a();
/* 158 */       break;
/*     */     
/*     */     case 7: 
/* 161 */       object = Blocks.RED_MUSHROOM;
/* 162 */       break;
/*     */     
/*     */     case 8: 
/* 165 */       object = Blocks.BROWN_MUSHROOM;
/* 166 */       break;
/*     */     
/*     */     case 9: 
/* 169 */       object = Blocks.CACTUS;
/* 170 */       break;
/*     */     
/*     */     case 10: 
/* 173 */       object = Blocks.DEADBUSH;
/* 174 */       break;
/*     */     
/*     */     case 11: 
/* 177 */       object = Blocks.TALLGRASS;
/* 178 */       j = BlockLongGrass.EnumTallGrassType.FERN.a();
/* 179 */       break;
/*     */     
/*     */     case 12: 
/* 182 */       object = Blocks.SAPLING;
/* 183 */       j = BlockWood.EnumLogVariant.ACACIA.a();
/* 184 */       break;
/*     */     
/*     */     case 13: 
/* 187 */       object = Blocks.SAPLING;
/* 188 */       j = BlockWood.EnumLogVariant.DARK_OAK.a();
/*     */     }
/*     */     
/* 191 */     return new TileEntityFlowerPot(Item.getItemOf((Block)object), j);
/*     */   }
/*     */   
/*     */   protected BlockStateList getStateList() {
/* 195 */     return new BlockStateList(this, new IBlockState[] { CONTENTS, LEGACY_DATA });
/*     */   }
/*     */   
/*     */   public int toLegacyData(IBlockData iblockdata) {
/* 199 */     return ((Integer)iblockdata.get(LEGACY_DATA)).intValue();
/*     */   }
/*     */   
/*     */   public IBlockData updateState(IBlockData iblockdata, IBlockAccess iblockaccess, BlockPosition blockposition) {
/* 203 */     EnumFlowerPotContents blockflowerpot_enumflowerpotcontents = EnumFlowerPotContents.EMPTY;
/* 204 */     TileEntity tileentity = iblockaccess.getTileEntity(blockposition);
/*     */     
/* 206 */     if ((tileentity instanceof TileEntityFlowerPot)) {
/* 207 */       TileEntityFlowerPot tileentityflowerpot = (TileEntityFlowerPot)tileentity;
/* 208 */       Item item = tileentityflowerpot.b();
/*     */       
/* 210 */       if ((item instanceof ItemBlock)) {
/* 211 */         int i = tileentityflowerpot.c();
/* 212 */         Block block = Block.asBlock(item);
/*     */         
/* 214 */         if (block == Blocks.SAPLING)
/* 215 */           switch (SyntheticClass_1.a[BlockWood.EnumLogVariant.a(i).ordinal()]) {
/*     */           case 1: 
/* 217 */             blockflowerpot_enumflowerpotcontents = EnumFlowerPotContents.OAK_SAPLING;
/* 218 */             break;
/*     */           
/*     */           case 2: 
/* 221 */             blockflowerpot_enumflowerpotcontents = EnumFlowerPotContents.SPRUCE_SAPLING;
/* 222 */             break;
/*     */           
/*     */           case 3: 
/* 225 */             blockflowerpot_enumflowerpotcontents = EnumFlowerPotContents.BIRCH_SAPLING;
/* 226 */             break;
/*     */           
/*     */           case 4: 
/* 229 */             blockflowerpot_enumflowerpotcontents = EnumFlowerPotContents.JUNGLE_SAPLING;
/* 230 */             break;
/*     */           
/*     */           case 5: 
/* 233 */             blockflowerpot_enumflowerpotcontents = EnumFlowerPotContents.ACACIA_SAPLING;
/* 234 */             break;
/*     */           
/*     */           case 6: 
/* 237 */             blockflowerpot_enumflowerpotcontents = EnumFlowerPotContents.DARK_OAK_SAPLING;
/* 238 */             break;
/*     */           
/*     */           default: 
/* 241 */             blockflowerpot_enumflowerpotcontents = EnumFlowerPotContents.EMPTY;
/*     */             
/* 243 */             break; } else if (block == Blocks.TALLGRASS)
/* 244 */           switch (i) {
/*     */           case 0: 
/* 246 */             blockflowerpot_enumflowerpotcontents = EnumFlowerPotContents.DEAD_BUSH;
/* 247 */             break;
/*     */           
/*     */           case 2: 
/* 250 */             blockflowerpot_enumflowerpotcontents = EnumFlowerPotContents.FERN;
/* 251 */             break;
/*     */           case 1: 
/*     */           default: 
/* 254 */             blockflowerpot_enumflowerpotcontents = EnumFlowerPotContents.EMPTY;
/*     */             
/* 256 */             break; } else if (block == Blocks.YELLOW_FLOWER) {
/* 257 */           blockflowerpot_enumflowerpotcontents = EnumFlowerPotContents.DANDELION;
/* 258 */         } else if (block == Blocks.RED_FLOWER)
/* 259 */           switch (SyntheticClass_1.b[BlockFlowers.EnumFlowerVarient.a(BlockFlowers.EnumFlowerType.RED, i).ordinal()]) {
/*     */           case 1: 
/* 261 */             blockflowerpot_enumflowerpotcontents = EnumFlowerPotContents.POPPY;
/* 262 */             break;
/*     */           
/*     */           case 2: 
/* 265 */             blockflowerpot_enumflowerpotcontents = EnumFlowerPotContents.BLUE_ORCHID;
/* 266 */             break;
/*     */           
/*     */           case 3: 
/* 269 */             blockflowerpot_enumflowerpotcontents = EnumFlowerPotContents.ALLIUM;
/* 270 */             break;
/*     */           
/*     */           case 4: 
/* 273 */             blockflowerpot_enumflowerpotcontents = EnumFlowerPotContents.HOUSTONIA;
/* 274 */             break;
/*     */           
/*     */           case 5: 
/* 277 */             blockflowerpot_enumflowerpotcontents = EnumFlowerPotContents.RED_TULIP;
/* 278 */             break;
/*     */           
/*     */           case 6: 
/* 281 */             blockflowerpot_enumflowerpotcontents = EnumFlowerPotContents.ORANGE_TULIP;
/* 282 */             break;
/*     */           
/*     */           case 7: 
/* 285 */             blockflowerpot_enumflowerpotcontents = EnumFlowerPotContents.WHITE_TULIP;
/* 286 */             break;
/*     */           
/*     */           case 8: 
/* 289 */             blockflowerpot_enumflowerpotcontents = EnumFlowerPotContents.PINK_TULIP;
/* 290 */             break;
/*     */           
/*     */           case 9: 
/* 293 */             blockflowerpot_enumflowerpotcontents = EnumFlowerPotContents.OXEYE_DAISY;
/* 294 */             break;
/*     */           
/*     */           default: 
/* 297 */             blockflowerpot_enumflowerpotcontents = EnumFlowerPotContents.EMPTY;
/*     */             
/* 299 */             break; } else if (block == Blocks.RED_MUSHROOM) {
/* 300 */           blockflowerpot_enumflowerpotcontents = EnumFlowerPotContents.MUSHROOM_RED;
/* 301 */         } else if (block == Blocks.BROWN_MUSHROOM) {
/* 302 */           blockflowerpot_enumflowerpotcontents = EnumFlowerPotContents.MUSHROOM_BROWN;
/* 303 */         } else if (block == Blocks.DEADBUSH) {
/* 304 */           blockflowerpot_enumflowerpotcontents = EnumFlowerPotContents.DEAD_BUSH;
/* 305 */         } else if (block == Blocks.CACTUS) {
/* 306 */           blockflowerpot_enumflowerpotcontents = EnumFlowerPotContents.CACTUS;
/*     */         }
/*     */       }
/*     */     }
/*     */     
/* 311 */     return iblockdata.set(CONTENTS, blockflowerpot_enumflowerpotcontents);
/*     */   }
/*     */   
/*     */   static class SyntheticClass_1
/*     */   {
/*     */     static final int[] a;
/* 317 */     static final int[] b = new int[BlockFlowers.EnumFlowerVarient.values().length];
/*     */     
/*     */     static {
/*     */       try {
/* 321 */         b[BlockFlowers.EnumFlowerVarient.POPPY.ordinal()] = 1;
/*     */       }
/*     */       catch (NoSuchFieldError localNoSuchFieldError1) {}
/*     */       
/*     */       try
/*     */       {
/* 327 */         b[BlockFlowers.EnumFlowerVarient.BLUE_ORCHID.ordinal()] = 2;
/*     */       }
/*     */       catch (NoSuchFieldError localNoSuchFieldError2) {}
/*     */       
/*     */       try
/*     */       {
/* 333 */         b[BlockFlowers.EnumFlowerVarient.ALLIUM.ordinal()] = 3;
/*     */       }
/*     */       catch (NoSuchFieldError localNoSuchFieldError3) {}
/*     */       
/*     */       try
/*     */       {
/* 339 */         b[BlockFlowers.EnumFlowerVarient.HOUSTONIA.ordinal()] = 4;
/*     */       }
/*     */       catch (NoSuchFieldError localNoSuchFieldError4) {}
/*     */       
/*     */       try
/*     */       {
/* 345 */         b[BlockFlowers.EnumFlowerVarient.RED_TULIP.ordinal()] = 5;
/*     */       }
/*     */       catch (NoSuchFieldError localNoSuchFieldError5) {}
/*     */       
/*     */       try
/*     */       {
/* 351 */         b[BlockFlowers.EnumFlowerVarient.ORANGE_TULIP.ordinal()] = 6;
/*     */       }
/*     */       catch (NoSuchFieldError localNoSuchFieldError6) {}
/*     */       
/*     */       try
/*     */       {
/* 357 */         b[BlockFlowers.EnumFlowerVarient.WHITE_TULIP.ordinal()] = 7;
/*     */       }
/*     */       catch (NoSuchFieldError localNoSuchFieldError7) {}
/*     */       
/*     */       try
/*     */       {
/* 363 */         b[BlockFlowers.EnumFlowerVarient.PINK_TULIP.ordinal()] = 8;
/*     */       }
/*     */       catch (NoSuchFieldError localNoSuchFieldError8) {}
/*     */       
/*     */       try
/*     */       {
/* 369 */         b[BlockFlowers.EnumFlowerVarient.OXEYE_DAISY.ordinal()] = 9;
/*     */       }
/*     */       catch (NoSuchFieldError localNoSuchFieldError9) {}
/*     */       
/*     */ 
/* 374 */       a = new int[BlockWood.EnumLogVariant.values().length];
/*     */       try
/*     */       {
/* 377 */         a[BlockWood.EnumLogVariant.OAK.ordinal()] = 1;
/*     */       }
/*     */       catch (NoSuchFieldError localNoSuchFieldError10) {}
/*     */       
/*     */       try
/*     */       {
/* 383 */         a[BlockWood.EnumLogVariant.SPRUCE.ordinal()] = 2;
/*     */       }
/*     */       catch (NoSuchFieldError localNoSuchFieldError11) {}
/*     */       
/*     */       try
/*     */       {
/* 389 */         a[BlockWood.EnumLogVariant.BIRCH.ordinal()] = 3;
/*     */       }
/*     */       catch (NoSuchFieldError localNoSuchFieldError12) {}
/*     */       
/*     */       try
/*     */       {
/* 395 */         a[BlockWood.EnumLogVariant.JUNGLE.ordinal()] = 4;
/*     */       }
/*     */       catch (NoSuchFieldError localNoSuchFieldError13) {}
/*     */       
/*     */       try
/*     */       {
/* 401 */         a[BlockWood.EnumLogVariant.ACACIA.ordinal()] = 5;
/*     */       }
/*     */       catch (NoSuchFieldError localNoSuchFieldError14) {}
/*     */       
/*     */       try
/*     */       {
/* 407 */         a[BlockWood.EnumLogVariant.DARK_OAK.ordinal()] = 6;
/*     */       }
/*     */       catch (NoSuchFieldError localNoSuchFieldError15) {}
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */   public static enum EnumFlowerPotContents
/*     */     implements INamable
/*     */   {
/* 417 */     EMPTY("empty"),  POPPY("rose"),  BLUE_ORCHID("blue_orchid"),  ALLIUM("allium"),  HOUSTONIA("houstonia"),  RED_TULIP("red_tulip"),  ORANGE_TULIP("orange_tulip"),  WHITE_TULIP("white_tulip"),  PINK_TULIP("pink_tulip"),  OXEYE_DAISY("oxeye_daisy"),  DANDELION("dandelion"),  OAK_SAPLING("oak_sapling"),  SPRUCE_SAPLING("spruce_sapling"),  BIRCH_SAPLING("birch_sapling"),  JUNGLE_SAPLING("jungle_sapling"),  ACACIA_SAPLING("acacia_sapling"),  DARK_OAK_SAPLING("dark_oak_sapling"),  MUSHROOM_RED("mushroom_red"),  MUSHROOM_BROWN("mushroom_brown"),  DEAD_BUSH("dead_bush"),  FERN("fern"),  CACTUS("cactus");
/*     */     
/*     */     private final String w;
/*     */     
/*     */     private EnumFlowerPotContents(String s) {
/* 422 */       this.w = s;
/*     */     }
/*     */     
/*     */     public String toString() {
/* 426 */       return this.w;
/*     */     }
/*     */     
/*     */     public String getName() {
/* 430 */       return this.w;
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_8_R3\BlockFlowerPot.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */
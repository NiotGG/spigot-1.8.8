/*     */ package net.minecraft.server.v1_8_R3;
/*     */ 
/*     */ import java.util.Random;
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
/*     */ public class BlockTallPlant
/*     */   extends BlockPlant
/*     */   implements IBlockFragilePlantElement
/*     */ {
/*  27 */   public static final BlockStateEnum<EnumTallFlowerVariants> VARIANT = BlockStateEnum.of("variant", EnumTallFlowerVariants.class);
/*  28 */   public static final BlockStateEnum<EnumTallPlantHalf> HALF = BlockStateEnum.of("half", EnumTallPlantHalf.class);
/*  29 */   public static final BlockStateEnum<EnumDirection> N = BlockDirectional.FACING;
/*     */   
/*     */   public BlockTallPlant() {
/*  32 */     super(Material.REPLACEABLE_PLANT);
/*  33 */     j(this.blockStateList.getBlockData().set(VARIANT, EnumTallFlowerVariants.SUNFLOWER).set(HALF, EnumTallPlantHalf.LOWER).set(N, EnumDirection.NORTH));
/*  34 */     c(0.0F);
/*  35 */     a(h);
/*  36 */     c("doublePlant");
/*     */   }
/*     */   
/*     */   public void updateShape(IBlockAccess paramIBlockAccess, BlockPosition paramBlockPosition)
/*     */   {
/*  41 */     a(0.0F, 0.0F, 0.0F, 1.0F, 1.0F, 1.0F);
/*     */   }
/*     */   
/*     */   public EnumTallFlowerVariants e(IBlockAccess paramIBlockAccess, BlockPosition paramBlockPosition) {
/*  45 */     IBlockData localIBlockData = paramIBlockAccess.getType(paramBlockPosition);
/*     */     
/*  47 */     if (localIBlockData.getBlock() == this) {
/*  48 */       localIBlockData = updateState(localIBlockData, paramIBlockAccess, paramBlockPosition);
/*     */       
/*  50 */       return (EnumTallFlowerVariants)localIBlockData.get(VARIANT);
/*     */     }
/*     */     
/*  53 */     return EnumTallFlowerVariants.FERN;
/*     */   }
/*     */   
/*     */   public boolean canPlace(World paramWorld, BlockPosition paramBlockPosition)
/*     */   {
/*  58 */     return (super.canPlace(paramWorld, paramBlockPosition)) && (paramWorld.isEmpty(paramBlockPosition.up()));
/*     */   }
/*     */   
/*     */   public boolean a(World paramWorld, BlockPosition paramBlockPosition)
/*     */   {
/*  63 */     IBlockData localIBlockData = paramWorld.getType(paramBlockPosition);
/*  64 */     if (localIBlockData.getBlock() == this) {
/*  65 */       EnumTallFlowerVariants localEnumTallFlowerVariants = (EnumTallFlowerVariants)updateState(localIBlockData, paramWorld, paramBlockPosition).get(VARIANT);
/*  66 */       return (localEnumTallFlowerVariants == EnumTallFlowerVariants.FERN) || (localEnumTallFlowerVariants == EnumTallFlowerVariants.GRASS);
/*     */     }
/*  68 */     return true;
/*     */   }
/*     */   
/*     */   protected void e(World paramWorld, BlockPosition paramBlockPosition, IBlockData paramIBlockData)
/*     */   {
/*  73 */     if (f(paramWorld, paramBlockPosition, paramIBlockData)) {
/*  74 */       return;
/*     */     }
/*     */     
/*  77 */     int i = paramIBlockData.get(HALF) == EnumTallPlantHalf.UPPER ? 1 : 0;
/*  78 */     BlockPosition localBlockPosition1 = i != 0 ? paramBlockPosition : paramBlockPosition.up();
/*  79 */     BlockPosition localBlockPosition2 = i != 0 ? paramBlockPosition.down() : paramBlockPosition;
/*     */     
/*  81 */     Block localBlock = i != 0 ? this : paramWorld.getType(localBlockPosition1).getBlock();
/*  82 */     BlockTallPlant localBlockTallPlant = i != 0 ? paramWorld.getType(localBlockPosition2).getBlock() : this;
/*     */     
/*  84 */     if (localBlock == this) {
/*  85 */       paramWorld.setTypeAndData(localBlockPosition1, Blocks.AIR.getBlockData(), 2);
/*     */     }
/*  87 */     if (localBlockTallPlant == this) {
/*  88 */       paramWorld.setTypeAndData(localBlockPosition2, Blocks.AIR.getBlockData(), 3);
/*  89 */       if (i == 0) {
/*  90 */         b(paramWorld, localBlockPosition2, paramIBlockData, 0);
/*     */       }
/*     */     }
/*     */   }
/*     */   
/*     */   public boolean f(World paramWorld, BlockPosition paramBlockPosition, IBlockData paramIBlockData)
/*     */   {
/*  97 */     if (paramIBlockData.get(HALF) == EnumTallPlantHalf.UPPER) {
/*  98 */       return paramWorld.getType(paramBlockPosition.down()).getBlock() == this;
/*     */     }
/*     */     
/* 101 */     IBlockData localIBlockData = paramWorld.getType(paramBlockPosition.up());
/* 102 */     return (localIBlockData.getBlock() == this) && (super.f(paramWorld, paramBlockPosition, localIBlockData));
/*     */   }
/*     */   
/*     */ 
/*     */   public Item getDropType(IBlockData paramIBlockData, Random paramRandom, int paramInt)
/*     */   {
/* 108 */     if (paramIBlockData.get(HALF) == EnumTallPlantHalf.UPPER) {
/* 109 */       return null;
/*     */     }
/* 111 */     EnumTallFlowerVariants localEnumTallFlowerVariants = (EnumTallFlowerVariants)paramIBlockData.get(VARIANT);
/* 112 */     if (localEnumTallFlowerVariants == EnumTallFlowerVariants.FERN)
/*     */     {
/* 114 */       return null;
/*     */     }
/*     */     
/* 117 */     if (localEnumTallFlowerVariants == EnumTallFlowerVariants.GRASS) {
/* 118 */       if (paramRandom.nextInt(8) == 0) {
/* 119 */         return Items.WHEAT_SEEDS;
/*     */       }
/* 121 */       return null;
/*     */     }
/* 123 */     return Item.getItemOf(this);
/*     */   }
/*     */   
/*     */   public int getDropData(IBlockData paramIBlockData)
/*     */   {
/* 128 */     if ((paramIBlockData.get(HALF) == EnumTallPlantHalf.UPPER) || (paramIBlockData.get(VARIANT) == EnumTallFlowerVariants.GRASS)) {
/* 129 */       return 0;
/*     */     }
/* 131 */     return ((EnumTallFlowerVariants)paramIBlockData.get(VARIANT)).a();
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
/*     */   public void a(World paramWorld, BlockPosition paramBlockPosition, EnumTallFlowerVariants paramEnumTallFlowerVariants, int paramInt)
/*     */   {
/* 144 */     paramWorld.setTypeAndData(paramBlockPosition, getBlockData().set(HALF, EnumTallPlantHalf.LOWER).set(VARIANT, paramEnumTallFlowerVariants), paramInt);
/* 145 */     paramWorld.setTypeAndData(paramBlockPosition.up(), getBlockData().set(HALF, EnumTallPlantHalf.UPPER), paramInt);
/*     */   }
/*     */   
/*     */   public void postPlace(World paramWorld, BlockPosition paramBlockPosition, IBlockData paramIBlockData, EntityLiving paramEntityLiving, ItemStack paramItemStack)
/*     */   {
/* 150 */     paramWorld.setTypeAndData(paramBlockPosition.up(), getBlockData().set(HALF, EnumTallPlantHalf.UPPER), 2);
/*     */   }
/*     */   
/*     */   public void a(World paramWorld, EntityHuman paramEntityHuman, BlockPosition paramBlockPosition, IBlockData paramIBlockData, TileEntity paramTileEntity)
/*     */   {
/* 155 */     if ((!paramWorld.isClientSide) && (paramEntityHuman.bZ() != null) && (paramEntityHuman.bZ().getItem() == Items.SHEARS))
/*     */     {
/*     */ 
/*     */ 
/* 159 */       if ((paramIBlockData.get(HALF) == EnumTallPlantHalf.LOWER) && 
/* 160 */         (b(paramWorld, paramBlockPosition, paramIBlockData, paramEntityHuman))) {
/* 161 */         return;
/*     */       }
/*     */     }
/*     */     
/* 165 */     super.a(paramWorld, paramEntityHuman, paramBlockPosition, paramIBlockData, paramTileEntity);
/*     */   }
/*     */   
/*     */   public void a(World paramWorld, BlockPosition paramBlockPosition, IBlockData paramIBlockData, EntityHuman paramEntityHuman)
/*     */   {
/* 170 */     if (paramIBlockData.get(HALF) == EnumTallPlantHalf.UPPER) {
/* 171 */       if (paramWorld.getType(paramBlockPosition.down()).getBlock() == this) {
/* 172 */         if (!paramEntityHuman.abilities.canInstantlyBuild)
/*     */         {
/* 174 */           IBlockData localIBlockData = paramWorld.getType(paramBlockPosition.down());
/* 175 */           EnumTallFlowerVariants localEnumTallFlowerVariants = (EnumTallFlowerVariants)localIBlockData.get(VARIANT);
/* 176 */           if ((localEnumTallFlowerVariants == EnumTallFlowerVariants.FERN) || (localEnumTallFlowerVariants == EnumTallFlowerVariants.GRASS)) {
/* 177 */             if (!paramWorld.isClientSide) {
/* 178 */               if ((paramEntityHuman.bZ() != null) && (paramEntityHuman.bZ().getItem() == Items.SHEARS)) {
/* 179 */                 b(paramWorld, paramBlockPosition, localIBlockData, paramEntityHuman);
/* 180 */                 paramWorld.setAir(paramBlockPosition.down());
/*     */               } else {
/* 182 */                 paramWorld.setAir(paramBlockPosition.down(), true);
/*     */               }
/*     */             } else {
/* 185 */               paramWorld.setAir(paramBlockPosition.down());
/*     */             }
/*     */           } else {
/* 188 */             paramWorld.setAir(paramBlockPosition.down(), true);
/*     */           }
/*     */         } else {
/* 191 */           paramWorld.setAir(paramBlockPosition.down());
/*     */         }
/*     */       }
/*     */     }
/* 195 */     else if ((paramEntityHuman.abilities.canInstantlyBuild) && (paramWorld.getType(paramBlockPosition.up()).getBlock() == this))
/*     */     {
/* 197 */       paramWorld.setTypeAndData(paramBlockPosition.up(), Blocks.AIR.getBlockData(), 2);
/*     */     }
/*     */     
/* 200 */     super.a(paramWorld, paramBlockPosition, paramIBlockData, paramEntityHuman);
/*     */   }
/*     */   
/*     */   private boolean b(World paramWorld, BlockPosition paramBlockPosition, IBlockData paramIBlockData, EntityHuman paramEntityHuman) {
/* 204 */     EnumTallFlowerVariants localEnumTallFlowerVariants = (EnumTallFlowerVariants)paramIBlockData.get(VARIANT);
/* 205 */     if ((localEnumTallFlowerVariants == EnumTallFlowerVariants.FERN) || (localEnumTallFlowerVariants == EnumTallFlowerVariants.GRASS)) {
/* 206 */       paramEntityHuman.b(StatisticList.MINE_BLOCK_COUNT[Block.getId(this)]);
/*     */       
/* 208 */       int i = (localEnumTallFlowerVariants == EnumTallFlowerVariants.GRASS ? BlockLongGrass.EnumTallGrassType.GRASS : BlockLongGrass.EnumTallGrassType.FERN).a();
/* 209 */       a(paramWorld, paramBlockPosition, new ItemStack(Blocks.TALLGRASS, 2, i));
/* 210 */       return true;
/*     */     }
/* 212 */     return false;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public int getDropData(World paramWorld, BlockPosition paramBlockPosition)
/*     */   {
/* 224 */     return e(paramWorld, paramBlockPosition).a();
/*     */   }
/*     */   
/*     */   public boolean a(World paramWorld, BlockPosition paramBlockPosition, IBlockData paramIBlockData, boolean paramBoolean)
/*     */   {
/* 229 */     EnumTallFlowerVariants localEnumTallFlowerVariants = e(paramWorld, paramBlockPosition);
/*     */     
/* 231 */     return (localEnumTallFlowerVariants != EnumTallFlowerVariants.GRASS) && (localEnumTallFlowerVariants != EnumTallFlowerVariants.FERN);
/*     */   }
/*     */   
/*     */   public boolean a(World paramWorld, Random paramRandom, BlockPosition paramBlockPosition, IBlockData paramIBlockData)
/*     */   {
/* 236 */     return true;
/*     */   }
/*     */   
/*     */   public void b(World paramWorld, Random paramRandom, BlockPosition paramBlockPosition, IBlockData paramIBlockData)
/*     */   {
/* 241 */     a(paramWorld, paramBlockPosition, new ItemStack(this, 1, e(paramWorld, paramBlockPosition).a()));
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public IBlockData fromLegacyData(int paramInt)
/*     */   {
/* 250 */     if ((paramInt & 0x8) > 0) {
/* 251 */       return getBlockData().set(HALF, EnumTallPlantHalf.UPPER);
/*     */     }
/*     */     
/* 254 */     return getBlockData().set(HALF, EnumTallPlantHalf.LOWER).set(VARIANT, EnumTallFlowerVariants.a(paramInt & 0x7));
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public IBlockData updateState(IBlockData paramIBlockData, IBlockAccess paramIBlockAccess, BlockPosition paramBlockPosition)
/*     */   {
/* 262 */     if (paramIBlockData.get(HALF) == EnumTallPlantHalf.UPPER) {
/* 263 */       IBlockData localIBlockData = paramIBlockAccess.getType(paramBlockPosition.down());
/* 264 */       if (localIBlockData.getBlock() == this) {
/* 265 */         paramIBlockData = paramIBlockData.set(VARIANT, localIBlockData.get(VARIANT));
/*     */       }
/*     */     }
/*     */     
/* 269 */     return paramIBlockData;
/*     */   }
/*     */   
/*     */   public int toLegacyData(IBlockData paramIBlockData)
/*     */   {
/* 274 */     if (paramIBlockData.get(HALF) == EnumTallPlantHalf.UPPER) {
/* 275 */       return 0x8 | ((EnumDirection)paramIBlockData.get(N)).b();
/*     */     }
/* 277 */     return ((EnumTallFlowerVariants)paramIBlockData.get(VARIANT)).a();
/*     */   }
/*     */   
/*     */   protected BlockStateList getStateList()
/*     */   {
/* 282 */     return new BlockStateList(this, new IBlockState[] { HALF, VARIANT, N });
/*     */   }
/*     */   
/*     */ 
/*     */   public static enum EnumTallFlowerVariants
/*     */     implements INamable
/*     */   {
/*     */     private static final EnumTallFlowerVariants[] g;
/*     */     
/*     */     private final int h;
/*     */     
/*     */     private final String i;
/*     */     
/*     */     private final String j;
/*     */     
/*     */ 
/*     */     static
/*     */     {
/* 300 */       g = new EnumTallFlowerVariants[values().length];
/*     */       
/* 302 */       for (EnumTallFlowerVariants localEnumTallFlowerVariants : values()) {
/* 303 */         g[localEnumTallFlowerVariants.a()] = localEnumTallFlowerVariants;
/*     */       }
/*     */     }
/*     */     
/*     */     private EnumTallFlowerVariants(int paramInt, String paramString) {
/* 308 */       this(paramInt, paramString, paramString);
/*     */     }
/*     */     
/*     */     private EnumTallFlowerVariants(int paramInt, String paramString1, String paramString2) {
/* 312 */       this.h = paramInt;
/* 313 */       this.i = paramString1;
/* 314 */       this.j = paramString2;
/*     */     }
/*     */     
/*     */     public int a() {
/* 318 */       return this.h;
/*     */     }
/*     */     
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     public String toString()
/*     */     {
/* 327 */       return this.i;
/*     */     }
/*     */     
/*     */     public static EnumTallFlowerVariants a(int paramInt) {
/* 331 */       if ((paramInt < 0) || (paramInt >= g.length)) {
/* 332 */         paramInt = 0;
/*     */       }
/* 334 */       return g[paramInt];
/*     */     }
/*     */     
/*     */     public String getName()
/*     */     {
/* 339 */       return this.i;
/*     */     }
/*     */     
/*     */     public String c() {
/* 343 */       return this.j;
/*     */     }
/*     */   }
/*     */   
/*     */   public static enum EnumTallPlantHalf
/*     */     implements INamable
/*     */   {
/*     */     private EnumTallPlantHalf() {}
/*     */     
/*     */     public String toString()
/*     */     {
/* 354 */       return getName();
/*     */     }
/*     */     
/*     */     public String getName()
/*     */     {
/* 359 */       return this == UPPER ? "upper" : "lower";
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_8_R3\BlockTallPlant.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */
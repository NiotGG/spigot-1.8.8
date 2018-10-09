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
/*     */ public class BlockLongGrass
/*     */   extends BlockPlant
/*     */   implements IBlockFragilePlantElement
/*     */ {
/*  25 */   public static final BlockStateEnum<EnumTallGrassType> TYPE = BlockStateEnum.of("type", EnumTallGrassType.class);
/*     */   
/*     */   protected BlockLongGrass() {
/*  28 */     super(Material.REPLACEABLE_PLANT);
/*  29 */     j(this.blockStateList.getBlockData().set(TYPE, EnumTallGrassType.DEAD_BUSH));
/*  30 */     float f = 0.4F;
/*  31 */     a(0.5F - f, 0.0F, 0.5F - f, 0.5F + f, 0.8F, 0.5F + f);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public boolean f(World paramWorld, BlockPosition paramBlockPosition, IBlockData paramIBlockData)
/*     */   {
/*  41 */     return c(paramWorld.getType(paramBlockPosition.down()).getBlock());
/*     */   }
/*     */   
/*     */   public boolean a(World paramWorld, BlockPosition paramBlockPosition)
/*     */   {
/*  46 */     return true;
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
/*     */   public Item getDropType(IBlockData paramIBlockData, Random paramRandom, int paramInt)
/*     */   {
/*  71 */     if (paramRandom.nextInt(8) == 0) {
/*  72 */       return Items.WHEAT_SEEDS;
/*     */     }
/*     */     
/*  75 */     return null;
/*     */   }
/*     */   
/*     */   public int getDropCount(int paramInt, Random paramRandom)
/*     */   {
/*  80 */     return 1 + paramRandom.nextInt(paramInt * 2 + 1);
/*     */   }
/*     */   
/*     */   public void a(World paramWorld, EntityHuman paramEntityHuman, BlockPosition paramBlockPosition, IBlockData paramIBlockData, TileEntity paramTileEntity)
/*     */   {
/*  85 */     if ((!paramWorld.isClientSide) && (paramEntityHuman.bZ() != null) && (paramEntityHuman.bZ().getItem() == Items.SHEARS)) {
/*  86 */       paramEntityHuman.b(StatisticList.MINE_BLOCK_COUNT[Block.getId(this)]);
/*     */       
/*     */ 
/*  89 */       a(paramWorld, paramBlockPosition, new ItemStack(Blocks.TALLGRASS, 1, ((EnumTallGrassType)paramIBlockData.get(TYPE)).a()));
/*     */     } else {
/*  91 */       super.a(paramWorld, paramEntityHuman, paramBlockPosition, paramIBlockData, paramTileEntity);
/*     */     }
/*     */   }
/*     */   
/*     */   public int getDropData(World paramWorld, BlockPosition paramBlockPosition)
/*     */   {
/*  97 */     IBlockData localIBlockData = paramWorld.getType(paramBlockPosition);
/*  98 */     return localIBlockData.getBlock().toLegacyData(localIBlockData);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public boolean a(World paramWorld, BlockPosition paramBlockPosition, IBlockData paramIBlockData, boolean paramBoolean)
/*     */   {
/* 110 */     return paramIBlockData.get(TYPE) != EnumTallGrassType.DEAD_BUSH;
/*     */   }
/*     */   
/*     */   public boolean a(World paramWorld, Random paramRandom, BlockPosition paramBlockPosition, IBlockData paramIBlockData)
/*     */   {
/* 115 */     return true;
/*     */   }
/*     */   
/*     */   public void b(World paramWorld, Random paramRandom, BlockPosition paramBlockPosition, IBlockData paramIBlockData)
/*     */   {
/* 120 */     BlockTallPlant.EnumTallFlowerVariants localEnumTallFlowerVariants = BlockTallPlant.EnumTallFlowerVariants.GRASS;
/* 121 */     if (paramIBlockData.get(TYPE) == EnumTallGrassType.FERN) {
/* 122 */       localEnumTallFlowerVariants = BlockTallPlant.EnumTallFlowerVariants.FERN;
/*     */     }
/* 124 */     if (Blocks.DOUBLE_PLANT.canPlace(paramWorld, paramBlockPosition)) {
/* 125 */       Blocks.DOUBLE_PLANT.a(paramWorld, paramBlockPosition, localEnumTallFlowerVariants, 2);
/*     */     }
/*     */   }
/*     */   
/*     */   public IBlockData fromLegacyData(int paramInt)
/*     */   {
/* 131 */     return getBlockData().set(TYPE, EnumTallGrassType.a(paramInt));
/*     */   }
/*     */   
/*     */ 
/*     */   public int toLegacyData(IBlockData paramIBlockData)
/*     */   {
/* 137 */     return ((EnumTallGrassType)paramIBlockData.get(TYPE)).a();
/*     */   }
/*     */   
/*     */   protected BlockStateList getStateList()
/*     */   {
/* 142 */     return new BlockStateList(this, new IBlockState[] { TYPE });
/*     */   }
/*     */   
/*     */ 
/*     */   public static enum EnumTallGrassType
/*     */     implements INamable
/*     */   {
/*     */     private static final EnumTallGrassType[] d;
/*     */     
/*     */     private final int e;
/*     */     private final String f;
/*     */     
/*     */     static
/*     */     {
/* 156 */       d = new EnumTallGrassType[values().length];
/*     */       
/* 158 */       for (EnumTallGrassType localEnumTallGrassType : values()) {
/* 159 */         d[localEnumTallGrassType.a()] = localEnumTallGrassType;
/*     */       }
/*     */     }
/*     */     
/*     */     private EnumTallGrassType(int paramInt, String paramString) {
/* 164 */       this.e = paramInt;
/* 165 */       this.f = paramString;
/*     */     }
/*     */     
/*     */     public int a() {
/* 169 */       return this.e;
/*     */     }
/*     */     
/*     */     public String toString()
/*     */     {
/* 174 */       return this.f;
/*     */     }
/*     */     
/*     */     public static EnumTallGrassType a(int paramInt) {
/* 178 */       if ((paramInt < 0) || (paramInt >= d.length)) {
/* 179 */         paramInt = 0;
/*     */       }
/* 181 */       return d[paramInt];
/*     */     }
/*     */     
/*     */     public String getName()
/*     */     {
/* 186 */       return this.f;
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_8_R3\BlockLongGrass.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */
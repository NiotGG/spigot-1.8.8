/*     */ package net.minecraft.server.v1_8_R3;
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
/*     */ public class BlockDirt
/*     */   extends Block
/*     */ {
/*  20 */   public static final BlockStateEnum<EnumDirtVariant> VARIANT = BlockStateEnum.of("variant", EnumDirtVariant.class);
/*  21 */   public static final BlockStateBoolean SNOWY = BlockStateBoolean.of("snowy");
/*     */   
/*     */   protected BlockDirt() {
/*  24 */     super(Material.EARTH);
/*  25 */     j(this.blockStateList.getBlockData().set(VARIANT, EnumDirtVariant.DIRT).set(SNOWY, Boolean.valueOf(false)));
/*  26 */     a(CreativeModeTab.b);
/*     */   }
/*     */   
/*     */   public MaterialMapColor g(IBlockData paramIBlockData)
/*     */   {
/*  31 */     return ((EnumDirtVariant)paramIBlockData.get(VARIANT)).d();
/*     */   }
/*     */   
/*     */   public IBlockData updateState(IBlockData paramIBlockData, IBlockAccess paramIBlockAccess, BlockPosition paramBlockPosition)
/*     */   {
/*  36 */     if (paramIBlockData.get(VARIANT) == EnumDirtVariant.PODZOL) {
/*  37 */       Block localBlock = paramIBlockAccess.getType(paramBlockPosition.up()).getBlock();
/*  38 */       paramIBlockData = paramIBlockData.set(SNOWY, Boolean.valueOf((localBlock == Blocks.SNOW) || (localBlock == Blocks.SNOW_LAYER)));
/*     */     }
/*  40 */     return paramIBlockData;
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
/*  52 */     IBlockData localIBlockData = paramWorld.getType(paramBlockPosition);
/*  53 */     if (localIBlockData.getBlock() != this) {
/*  54 */       return 0;
/*     */     }
/*  56 */     return ((EnumDirtVariant)localIBlockData.get(VARIANT)).a();
/*     */   }
/*     */   
/*     */   public IBlockData fromLegacyData(int paramInt)
/*     */   {
/*  61 */     return getBlockData().set(VARIANT, EnumDirtVariant.a(paramInt));
/*     */   }
/*     */   
/*     */ 
/*     */   public int toLegacyData(IBlockData paramIBlockData)
/*     */   {
/*  67 */     return ((EnumDirtVariant)paramIBlockData.get(VARIANT)).a();
/*     */   }
/*     */   
/*     */   protected BlockStateList getStateList()
/*     */   {
/*  72 */     return new BlockStateList(this, new IBlockState[] { VARIANT, SNOWY });
/*     */   }
/*     */   
/*     */   public int getDropData(IBlockData paramIBlockData)
/*     */   {
/*  77 */     EnumDirtVariant localEnumDirtVariant = (EnumDirtVariant)paramIBlockData.get(VARIANT);
/*  78 */     if (localEnumDirtVariant == EnumDirtVariant.PODZOL) {
/*  79 */       localEnumDirtVariant = EnumDirtVariant.DIRT;
/*     */     }
/*  81 */     return localEnumDirtVariant.a();
/*     */   }
/*     */   
/*     */ 
/*     */   public static enum EnumDirtVariant
/*     */     implements INamable
/*     */   {
/*     */     private static final EnumDirtVariant[] d;
/*     */     
/*     */     private final int e;
/*     */     private final String f;
/*     */     private final String g;
/*     */     private final MaterialMapColor h;
/*     */     
/*     */     static
/*     */     {
/*  97 */       d = new EnumDirtVariant[values().length];
/*     */       
/*  99 */       for (EnumDirtVariant localEnumDirtVariant : values()) {
/* 100 */         d[localEnumDirtVariant.a()] = localEnumDirtVariant;
/*     */       }
/*     */     }
/*     */     
/*     */     private EnumDirtVariant(int paramInt, String paramString, MaterialMapColor paramMaterialMapColor) {
/* 105 */       this(paramInt, paramString, paramString, paramMaterialMapColor);
/*     */     }
/*     */     
/*     */     private EnumDirtVariant(int paramInt, String paramString1, String paramString2, MaterialMapColor paramMaterialMapColor) {
/* 109 */       this.e = paramInt;
/* 110 */       this.f = paramString1;
/* 111 */       this.g = paramString2;
/* 112 */       this.h = paramMaterialMapColor;
/*     */     }
/*     */     
/*     */     public int a() {
/* 116 */       return this.e;
/*     */     }
/*     */     
/*     */ 
/*     */ 
/*     */ 
/*     */     public String c()
/*     */     {
/* 124 */       return this.g;
/*     */     }
/*     */     
/*     */     public MaterialMapColor d() {
/* 128 */       return this.h;
/*     */     }
/*     */     
/*     */     public String toString()
/*     */     {
/* 133 */       return this.f;
/*     */     }
/*     */     
/*     */     public static EnumDirtVariant a(int paramInt) {
/* 137 */       if ((paramInt < 0) || (paramInt >= d.length)) {
/* 138 */         paramInt = 0;
/*     */       }
/* 140 */       return d[paramInt];
/*     */     }
/*     */     
/*     */     public String getName()
/*     */     {
/* 145 */       return this.f;
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_8_R3\BlockDirt.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */
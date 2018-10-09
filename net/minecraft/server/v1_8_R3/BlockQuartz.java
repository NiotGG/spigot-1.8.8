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
/*     */ public class BlockQuartz
/*     */   extends Block
/*     */ {
/*  20 */   public static final BlockStateEnum<EnumQuartzVariant> VARIANT = BlockStateEnum.of("variant", EnumQuartzVariant.class);
/*     */   
/*     */   public BlockQuartz() {
/*  23 */     super(Material.STONE);
/*  24 */     j(this.blockStateList.getBlockData().set(VARIANT, EnumQuartzVariant.DEFAULT));
/*  25 */     a(CreativeModeTab.b);
/*     */   }
/*     */   
/*     */   public IBlockData getPlacedState(World paramWorld, BlockPosition paramBlockPosition, EnumDirection paramEnumDirection, float paramFloat1, float paramFloat2, float paramFloat3, int paramInt, EntityLiving paramEntityLiving)
/*     */   {
/*  30 */     if (paramInt == EnumQuartzVariant.LINES_Y.a()) {
/*  31 */       switch (1.a[paramEnumDirection.k().ordinal()]) {
/*     */       case 1: 
/*  33 */         return getBlockData().set(VARIANT, EnumQuartzVariant.LINES_Z);
/*     */       case 2: 
/*  35 */         return getBlockData().set(VARIANT, EnumQuartzVariant.LINES_X);
/*     */       }
/*     */       
/*  38 */       return getBlockData().set(VARIANT, EnumQuartzVariant.LINES_Y);
/*     */     }
/*     */     
/*     */ 
/*  42 */     if (paramInt == EnumQuartzVariant.CHISELED.a()) {
/*  43 */       return getBlockData().set(VARIANT, EnumQuartzVariant.CHISELED);
/*     */     }
/*     */     
/*  46 */     return getBlockData().set(VARIANT, EnumQuartzVariant.DEFAULT);
/*     */   }
/*     */   
/*     */   public int getDropData(IBlockData paramIBlockData)
/*     */   {
/*  51 */     EnumQuartzVariant localEnumQuartzVariant = (EnumQuartzVariant)paramIBlockData.get(VARIANT);
/*  52 */     if ((localEnumQuartzVariant == EnumQuartzVariant.LINES_X) || (localEnumQuartzVariant == EnumQuartzVariant.LINES_Z)) {
/*  53 */       return EnumQuartzVariant.LINES_Y.a();
/*     */     }
/*     */     
/*  56 */     return localEnumQuartzVariant.a();
/*     */   }
/*     */   
/*     */   protected ItemStack i(IBlockData paramIBlockData)
/*     */   {
/*  61 */     EnumQuartzVariant localEnumQuartzVariant = (EnumQuartzVariant)paramIBlockData.get(VARIANT);
/*  62 */     if ((localEnumQuartzVariant == EnumQuartzVariant.LINES_X) || (localEnumQuartzVariant == EnumQuartzVariant.LINES_Z)) {
/*  63 */       return new ItemStack(Item.getItemOf(this), 1, EnumQuartzVariant.LINES_Y.a());
/*     */     }
/*  65 */     return super.i(paramIBlockData);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public MaterialMapColor g(IBlockData paramIBlockData)
/*     */   {
/*  77 */     return MaterialMapColor.p;
/*     */   }
/*     */   
/*     */   public IBlockData fromLegacyData(int paramInt)
/*     */   {
/*  82 */     return getBlockData().set(VARIANT, EnumQuartzVariant.a(paramInt));
/*     */   }
/*     */   
/*     */ 
/*     */   public int toLegacyData(IBlockData paramIBlockData)
/*     */   {
/*  88 */     return ((EnumQuartzVariant)paramIBlockData.get(VARIANT)).a();
/*     */   }
/*     */   
/*     */   protected BlockStateList getStateList()
/*     */   {
/*  93 */     return new BlockStateList(this, new IBlockState[] { VARIANT });
/*     */   }
/*     */   
/*     */ 
/*     */   public static enum EnumQuartzVariant
/*     */     implements INamable
/*     */   {
/*     */     private static final EnumQuartzVariant[] f;
/*     */     
/*     */     private final int g;
/*     */     
/*     */     private final String h;
/*     */     
/*     */     private final String i;
/*     */     
/*     */     static
/*     */     {
/* 110 */       f = new EnumQuartzVariant[values().length];
/*     */       
/* 112 */       for (EnumQuartzVariant localEnumQuartzVariant : values()) {
/* 113 */         f[localEnumQuartzVariant.a()] = localEnumQuartzVariant;
/*     */       }
/*     */     }
/*     */     
/*     */     private EnumQuartzVariant(int paramInt, String paramString1, String paramString2) {
/* 118 */       this.g = paramInt;
/* 119 */       this.h = paramString1;
/* 120 */       this.i = paramString2;
/*     */     }
/*     */     
/*     */     public int a() {
/* 124 */       return this.g;
/*     */     }
/*     */     
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     public String toString()
/*     */     {
/* 133 */       return this.i;
/*     */     }
/*     */     
/*     */     public static EnumQuartzVariant a(int paramInt) {
/* 137 */       if ((paramInt < 0) || (paramInt >= f.length)) {
/* 138 */         paramInt = 0;
/*     */       }
/* 140 */       return f[paramInt];
/*     */     }
/*     */     
/*     */     public String getName()
/*     */     {
/* 145 */       return this.h;
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_8_R3\BlockQuartz.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */
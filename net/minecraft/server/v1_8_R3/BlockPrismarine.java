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
/*     */ public class BlockPrismarine
/*     */   extends Block
/*     */ {
/*  17 */   public static final BlockStateEnum<EnumPrismarineVariant> VARIANT = BlockStateEnum.of("variant", EnumPrismarineVariant.class);
/*  18 */   public static final int b = EnumPrismarineVariant.ROUGH.a();
/*  19 */   public static final int N = EnumPrismarineVariant.BRICKS.a();
/*  20 */   public static final int O = EnumPrismarineVariant.DARK.a();
/*     */   
/*     */   public BlockPrismarine() {
/*  23 */     super(Material.STONE);
/*  24 */     j(this.blockStateList.getBlockData().set(VARIANT, EnumPrismarineVariant.ROUGH));
/*  25 */     a(CreativeModeTab.b);
/*     */   }
/*     */   
/*     */   public String getName()
/*     */   {
/*  30 */     return LocaleI18n.get(a() + "." + EnumPrismarineVariant.ROUGH.c() + ".name");
/*     */   }
/*     */   
/*     */   public MaterialMapColor g(IBlockData paramIBlockData)
/*     */   {
/*  35 */     if (paramIBlockData.get(VARIANT) == EnumPrismarineVariant.ROUGH) {
/*  36 */       return MaterialMapColor.y;
/*     */     }
/*  38 */     return MaterialMapColor.G;
/*     */   }
/*     */   
/*     */   public int getDropData(IBlockData paramIBlockData)
/*     */   {
/*  43 */     return ((EnumPrismarineVariant)paramIBlockData.get(VARIANT)).a();
/*     */   }
/*     */   
/*     */   public int toLegacyData(IBlockData paramIBlockData)
/*     */   {
/*  48 */     return ((EnumPrismarineVariant)paramIBlockData.get(VARIANT)).a();
/*     */   }
/*     */   
/*     */   protected BlockStateList getStateList()
/*     */   {
/*  53 */     return new BlockStateList(this, new IBlockState[] { VARIANT });
/*     */   }
/*     */   
/*     */   public IBlockData fromLegacyData(int paramInt)
/*     */   {
/*  58 */     return getBlockData().set(VARIANT, EnumPrismarineVariant.a(paramInt));
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public static enum EnumPrismarineVariant
/*     */     implements INamable
/*     */   {
/*     */     private static final EnumPrismarineVariant[] d;
/*     */     
/*     */ 
/*     */     private final int e;
/*     */     
/*     */ 
/*     */     private final String f;
/*     */     
/*     */     private final String g;
/*     */     
/*     */ 
/*     */     static
/*     */     {
/*  79 */       d = new EnumPrismarineVariant[values().length];
/*     */       
/*  81 */       for (EnumPrismarineVariant localEnumPrismarineVariant : values()) {
/*  82 */         d[localEnumPrismarineVariant.a()] = localEnumPrismarineVariant;
/*     */       }
/*     */     }
/*     */     
/*     */     private EnumPrismarineVariant(int paramInt, String paramString1, String paramString2) {
/*  87 */       this.e = paramInt;
/*  88 */       this.f = paramString1;
/*  89 */       this.g = paramString2;
/*     */     }
/*     */     
/*     */     public int a() {
/*  93 */       return this.e;
/*     */     }
/*     */     
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     public String toString()
/*     */     {
/* 102 */       return this.f;
/*     */     }
/*     */     
/*     */     public static EnumPrismarineVariant a(int paramInt) {
/* 106 */       if ((paramInt < 0) || (paramInt >= d.length)) {
/* 107 */         paramInt = 0;
/*     */       }
/* 109 */       return d[paramInt];
/*     */     }
/*     */     
/*     */     public String getName()
/*     */     {
/* 114 */       return this.f;
/*     */     }
/*     */     
/*     */     public String c() {
/* 118 */       return this.g;
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_8_R3\BlockPrismarine.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */
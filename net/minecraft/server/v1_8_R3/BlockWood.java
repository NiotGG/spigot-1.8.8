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
/*     */ public class BlockWood
/*     */   extends Block
/*     */ {
/*  16 */   public static final BlockStateEnum<EnumLogVariant> VARIANT = BlockStateEnum.of("variant", EnumLogVariant.class);
/*     */   
/*     */   public BlockWood() {
/*  19 */     super(Material.WOOD);
/*  20 */     j(this.blockStateList.getBlockData().set(VARIANT, EnumLogVariant.OAK));
/*  21 */     a(CreativeModeTab.b);
/*     */   }
/*     */   
/*     */   public int getDropData(IBlockData paramIBlockData)
/*     */   {
/*  26 */     return ((EnumLogVariant)paramIBlockData.get(VARIANT)).a();
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public IBlockData fromLegacyData(int paramInt)
/*     */   {
/*  38 */     return getBlockData().set(VARIANT, EnumLogVariant.a(paramInt));
/*     */   }
/*     */   
/*     */ 
/*     */   public MaterialMapColor g(IBlockData paramIBlockData)
/*     */   {
/*  44 */     return ((EnumLogVariant)paramIBlockData.get(VARIANT)).c();
/*     */   }
/*     */   
/*     */   public int toLegacyData(IBlockData paramIBlockData)
/*     */   {
/*  49 */     return ((EnumLogVariant)paramIBlockData.get(VARIANT)).a();
/*     */   }
/*     */   
/*     */   protected BlockStateList getStateList()
/*     */   {
/*  54 */     return new BlockStateList(this, new IBlockState[] { VARIANT });
/*     */   }
/*     */   
/*     */ 
/*     */   public static enum EnumLogVariant
/*     */     implements INamable
/*     */   {
/*     */     private static final EnumLogVariant[] g;
/*     */     
/*     */     private final int h;
/*     */     
/*     */     private final String i;
/*     */     
/*     */     private final String j;
/*     */     
/*     */     private final MaterialMapColor k;
/*     */     
/*     */     static
/*     */     {
/*  73 */       g = new EnumLogVariant[values().length];
/*     */       
/*  75 */       for (EnumLogVariant localEnumLogVariant : values()) {
/*  76 */         g[localEnumLogVariant.a()] = localEnumLogVariant;
/*     */       }
/*     */     }
/*     */     
/*     */     private EnumLogVariant(int paramInt, String paramString, MaterialMapColor paramMaterialMapColor) {
/*  81 */       this(paramInt, paramString, paramString, paramMaterialMapColor);
/*     */     }
/*     */     
/*     */     private EnumLogVariant(int paramInt, String paramString1, String paramString2, MaterialMapColor paramMaterialMapColor) {
/*  85 */       this.h = paramInt;
/*  86 */       this.i = paramString1;
/*  87 */       this.j = paramString2;
/*  88 */       this.k = paramMaterialMapColor;
/*     */     }
/*     */     
/*     */     public int a() {
/*  92 */       return this.h;
/*     */     }
/*     */     
/*     */ 
/*     */ 
/*     */ 
/*     */     public MaterialMapColor c()
/*     */     {
/* 100 */       return this.k;
/*     */     }
/*     */     
/*     */     public String toString()
/*     */     {
/* 105 */       return this.i;
/*     */     }
/*     */     
/*     */     public static EnumLogVariant a(int paramInt) {
/* 109 */       if ((paramInt < 0) || (paramInt >= g.length)) {
/* 110 */         paramInt = 0;
/*     */       }
/* 112 */       return g[paramInt];
/*     */     }
/*     */     
/*     */     public String getName()
/*     */     {
/* 117 */       return this.i;
/*     */     }
/*     */     
/*     */     public String d() {
/* 121 */       return this.j;
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_8_R3\BlockWood.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */
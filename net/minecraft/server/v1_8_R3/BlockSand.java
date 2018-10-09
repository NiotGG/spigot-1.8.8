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
/*     */ public class BlockSand
/*     */   extends BlockFalling
/*     */ {
/*  15 */   public static final BlockStateEnum<EnumSandVariant> VARIANT = BlockStateEnum.of("variant", EnumSandVariant.class);
/*     */   
/*     */   public BlockSand() {
/*  18 */     j(this.blockStateList.getBlockData().set(VARIANT, EnumSandVariant.SAND));
/*     */   }
/*     */   
/*     */   public int getDropData(IBlockData paramIBlockData)
/*     */   {
/*  23 */     return ((EnumSandVariant)paramIBlockData.get(VARIANT)).a();
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
/*  35 */     return ((EnumSandVariant)paramIBlockData.get(VARIANT)).c();
/*     */   }
/*     */   
/*     */   public IBlockData fromLegacyData(int paramInt)
/*     */   {
/*  40 */     return getBlockData().set(VARIANT, EnumSandVariant.a(paramInt));
/*     */   }
/*     */   
/*     */ 
/*     */   public int toLegacyData(IBlockData paramIBlockData)
/*     */   {
/*  46 */     return ((EnumSandVariant)paramIBlockData.get(VARIANT)).a();
/*     */   }
/*     */   
/*     */   protected BlockStateList getStateList()
/*     */   {
/*  51 */     return new BlockStateList(this, new IBlockState[] { VARIANT });
/*     */   }
/*     */   
/*     */ 
/*     */   public static enum EnumSandVariant
/*     */     implements INamable
/*     */   {
/*     */     private static final EnumSandVariant[] c;
/*     */     private final int d;
/*     */     private final String e;
/*     */     private final MaterialMapColor f;
/*     */     private final String g;
/*     */     
/*     */     static
/*     */     {
/*  66 */       c = new EnumSandVariant[values().length];
/*     */       
/*  68 */       for (EnumSandVariant localEnumSandVariant : values()) {
/*  69 */         c[localEnumSandVariant.a()] = localEnumSandVariant;
/*     */       }
/*     */     }
/*     */     
/*     */     private EnumSandVariant(int paramInt, String paramString1, String paramString2, MaterialMapColor paramMaterialMapColor) {
/*  74 */       this.d = paramInt;
/*  75 */       this.e = paramString1;
/*  76 */       this.f = paramMaterialMapColor;
/*  77 */       this.g = paramString2;
/*     */     }
/*     */     
/*     */     public int a() {
/*  81 */       return this.d;
/*     */     }
/*     */     
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     public String toString()
/*     */     {
/*  90 */       return this.e;
/*     */     }
/*     */     
/*     */     public MaterialMapColor c() {
/*  94 */       return this.f;
/*     */     }
/*     */     
/*     */     public static EnumSandVariant a(int paramInt) {
/*  98 */       if ((paramInt < 0) || (paramInt >= c.length)) {
/*  99 */         paramInt = 0;
/*     */       }
/* 101 */       return c[paramInt];
/*     */     }
/*     */     
/*     */     public String getName()
/*     */     {
/* 106 */       return this.e;
/*     */     }
/*     */     
/*     */     public String d() {
/* 110 */       return this.g;
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_8_R3\BlockSand.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */
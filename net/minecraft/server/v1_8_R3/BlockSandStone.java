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
/*     */ public class BlockSandStone
/*     */   extends Block
/*     */ {
/*  16 */   public static final BlockStateEnum<EnumSandstoneVariant> TYPE = BlockStateEnum.of("type", EnumSandstoneVariant.class);
/*     */   
/*     */   public BlockSandStone() {
/*  19 */     super(Material.STONE);
/*  20 */     j(this.blockStateList.getBlockData().set(TYPE, EnumSandstoneVariant.DEFAULT));
/*  21 */     a(CreativeModeTab.b);
/*     */   }
/*     */   
/*     */   public int getDropData(IBlockData paramIBlockData)
/*     */   {
/*  26 */     return ((EnumSandstoneVariant)paramIBlockData.get(TYPE)).a();
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
/*  38 */     return MaterialMapColor.d;
/*     */   }
/*     */   
/*     */   public IBlockData fromLegacyData(int paramInt)
/*     */   {
/*  43 */     return getBlockData().set(TYPE, EnumSandstoneVariant.a(paramInt));
/*     */   }
/*     */   
/*     */ 
/*     */   public int toLegacyData(IBlockData paramIBlockData)
/*     */   {
/*  49 */     return ((EnumSandstoneVariant)paramIBlockData.get(TYPE)).a();
/*     */   }
/*     */   
/*     */   protected BlockStateList getStateList()
/*     */   {
/*  54 */     return new BlockStateList(this, new IBlockState[] { TYPE });
/*     */   }
/*     */   
/*     */ 
/*     */   public static enum EnumSandstoneVariant
/*     */     implements INamable
/*     */   {
/*     */     private static final EnumSandstoneVariant[] d;
/*     */     
/*     */     private final int e;
/*     */     private final String f;
/*     */     private final String g;
/*     */     
/*     */     static
/*     */     {
/*  69 */       d = new EnumSandstoneVariant[values().length];
/*     */       
/*  71 */       for (EnumSandstoneVariant localEnumSandstoneVariant : values()) {
/*  72 */         d[localEnumSandstoneVariant.a()] = localEnumSandstoneVariant;
/*     */       }
/*     */     }
/*     */     
/*     */     private EnumSandstoneVariant(int paramInt, String paramString1, String paramString2) {
/*  77 */       this.e = paramInt;
/*  78 */       this.f = paramString1;
/*  79 */       this.g = paramString2;
/*     */     }
/*     */     
/*     */     public int a() {
/*  83 */       return this.e;
/*     */     }
/*     */     
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     public String toString()
/*     */     {
/*  92 */       return this.f;
/*     */     }
/*     */     
/*     */     public static EnumSandstoneVariant a(int paramInt) {
/*  96 */       if ((paramInt < 0) || (paramInt >= d.length)) {
/*  97 */         paramInt = 0;
/*     */       }
/*  99 */       return d[paramInt];
/*     */     }
/*     */     
/*     */     public String getName()
/*     */     {
/* 104 */       return this.f;
/*     */     }
/*     */     
/*     */     public String c() {
/* 108 */       return this.g;
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_8_R3\BlockSandStone.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */
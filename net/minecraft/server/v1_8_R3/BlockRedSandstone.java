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
/*     */ public class BlockRedSandstone
/*     */   extends Block
/*     */ {
/*  15 */   public static final BlockStateEnum<EnumRedSandstoneVariant> TYPE = BlockStateEnum.of("type", EnumRedSandstoneVariant.class);
/*     */   
/*     */   public BlockRedSandstone() {
/*  18 */     super(Material.STONE, BlockSand.EnumSandVariant.RED_SAND.c());
/*  19 */     j(this.blockStateList.getBlockData().set(TYPE, EnumRedSandstoneVariant.DEFAULT));
/*  20 */     a(CreativeModeTab.b);
/*     */   }
/*     */   
/*     */   public int getDropData(IBlockData paramIBlockData)
/*     */   {
/*  25 */     return ((EnumRedSandstoneVariant)paramIBlockData.get(TYPE)).a();
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
/*  37 */     return getBlockData().set(TYPE, EnumRedSandstoneVariant.a(paramInt));
/*     */   }
/*     */   
/*     */ 
/*     */   public int toLegacyData(IBlockData paramIBlockData)
/*     */   {
/*  43 */     return ((EnumRedSandstoneVariant)paramIBlockData.get(TYPE)).a();
/*     */   }
/*     */   
/*     */   protected BlockStateList getStateList()
/*     */   {
/*  48 */     return new BlockStateList(this, new IBlockState[] { TYPE });
/*     */   }
/*     */   
/*     */ 
/*     */   public static enum EnumRedSandstoneVariant
/*     */     implements INamable
/*     */   {
/*     */     private static final EnumRedSandstoneVariant[] d;
/*     */     
/*     */     private final int e;
/*     */     private final String f;
/*     */     private final String g;
/*     */     
/*     */     static
/*     */     {
/*  63 */       d = new EnumRedSandstoneVariant[values().length];
/*     */       
/*  65 */       for (EnumRedSandstoneVariant localEnumRedSandstoneVariant : values()) {
/*  66 */         d[localEnumRedSandstoneVariant.a()] = localEnumRedSandstoneVariant;
/*     */       }
/*     */     }
/*     */     
/*     */     private EnumRedSandstoneVariant(int paramInt, String paramString1, String paramString2) {
/*  71 */       this.e = paramInt;
/*  72 */       this.f = paramString1;
/*  73 */       this.g = paramString2;
/*     */     }
/*     */     
/*     */     public int a() {
/*  77 */       return this.e;
/*     */     }
/*     */     
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     public String toString()
/*     */     {
/*  86 */       return this.f;
/*     */     }
/*     */     
/*     */     public static EnumRedSandstoneVariant a(int paramInt) {
/*  90 */       if ((paramInt < 0) || (paramInt >= d.length)) {
/*  91 */         paramInt = 0;
/*     */       }
/*  93 */       return d[paramInt];
/*     */     }
/*     */     
/*     */     public String getName()
/*     */     {
/*  98 */       return this.f;
/*     */     }
/*     */     
/*     */     public String c() {
/* 102 */       return this.g;
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_8_R3\BlockRedSandstone.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */
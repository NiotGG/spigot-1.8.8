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
/*     */ public class BlockSmoothBrick
/*     */   extends Block
/*     */ {
/*  15 */   public static final BlockStateEnum<EnumStonebrickType> VARIANT = BlockStateEnum.of("variant", EnumStonebrickType.class);
/*     */   
/*  17 */   public static final int b = EnumStonebrickType.DEFAULT.a();
/*  18 */   public static final int N = EnumStonebrickType.MOSSY.a();
/*  19 */   public static final int O = EnumStonebrickType.CRACKED.a();
/*  20 */   public static final int P = EnumStonebrickType.CHISELED.a();
/*     */   
/*     */   public BlockSmoothBrick() {
/*  23 */     super(Material.STONE);
/*  24 */     j(this.blockStateList.getBlockData().set(VARIANT, EnumStonebrickType.DEFAULT));
/*  25 */     a(CreativeModeTab.b);
/*     */   }
/*     */   
/*     */   public int getDropData(IBlockData paramIBlockData)
/*     */   {
/*  30 */     return ((EnumStonebrickType)paramIBlockData.get(VARIANT)).a();
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
/*  42 */     return getBlockData().set(VARIANT, EnumStonebrickType.a(paramInt));
/*     */   }
/*     */   
/*     */ 
/*     */   public int toLegacyData(IBlockData paramIBlockData)
/*     */   {
/*  48 */     return ((EnumStonebrickType)paramIBlockData.get(VARIANT)).a();
/*     */   }
/*     */   
/*     */   protected BlockStateList getStateList()
/*     */   {
/*  53 */     return new BlockStateList(this, new IBlockState[] { VARIANT });
/*     */   }
/*     */   
/*     */ 
/*     */   public static enum EnumStonebrickType
/*     */     implements INamable
/*     */   {
/*     */     private static final EnumStonebrickType[] e;
/*     */     
/*     */     private final int f;
/*     */     
/*     */     private final String g;
/*     */     private final String h;
/*     */     
/*     */     static
/*     */     {
/*  69 */       e = new EnumStonebrickType[values().length];
/*     */       
/*  71 */       for (EnumStonebrickType localEnumStonebrickType : values()) {
/*  72 */         e[localEnumStonebrickType.a()] = localEnumStonebrickType;
/*     */       }
/*     */     }
/*     */     
/*     */     private EnumStonebrickType(int paramInt, String paramString1, String paramString2) {
/*  77 */       this.f = paramInt;
/*  78 */       this.g = paramString1;
/*  79 */       this.h = paramString2;
/*     */     }
/*     */     
/*     */     public int a() {
/*  83 */       return this.f;
/*     */     }
/*     */     
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     public String toString()
/*     */     {
/*  92 */       return this.g;
/*     */     }
/*     */     
/*     */     public static EnumStonebrickType a(int paramInt) {
/*  96 */       if ((paramInt < 0) || (paramInt >= e.length)) {
/*  97 */         paramInt = 0;
/*     */       }
/*  99 */       return e[paramInt];
/*     */     }
/*     */     
/*     */     public String getName()
/*     */     {
/* 104 */       return this.g;
/*     */     }
/*     */     
/*     */     public String c() {
/* 108 */       return this.h;
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_8_R3\BlockSmoothBrick.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */
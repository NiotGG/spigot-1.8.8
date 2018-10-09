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
/*     */ public class BlockStone
/*     */   extends Block
/*     */ {
/*  19 */   public static final BlockStateEnum<EnumStoneVariant> VARIANT = BlockStateEnum.of("variant", EnumStoneVariant.class);
/*     */   
/*     */   public BlockStone() {
/*  22 */     super(Material.STONE);
/*  23 */     j(this.blockStateList.getBlockData().set(VARIANT, EnumStoneVariant.STONE));
/*  24 */     a(CreativeModeTab.b);
/*     */   }
/*     */   
/*     */   public String getName()
/*     */   {
/*  29 */     return LocaleI18n.get(a() + "." + EnumStoneVariant.STONE.d() + ".name");
/*     */   }
/*     */   
/*     */   public MaterialMapColor g(IBlockData paramIBlockData)
/*     */   {
/*  34 */     return ((EnumStoneVariant)paramIBlockData.get(VARIANT)).c();
/*     */   }
/*     */   
/*     */ 
/*     */   public Item getDropType(IBlockData paramIBlockData, Random paramRandom, int paramInt)
/*     */   {
/*  40 */     if (paramIBlockData.get(VARIANT) == EnumStoneVariant.STONE) {
/*  41 */       return Item.getItemOf(Blocks.COBBLESTONE);
/*     */     }
/*  43 */     return Item.getItemOf(Blocks.STONE);
/*     */   }
/*     */   
/*     */   public int getDropData(IBlockData paramIBlockData)
/*     */   {
/*  48 */     return ((EnumStoneVariant)paramIBlockData.get(VARIANT)).a();
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
/*  60 */     return getBlockData().set(VARIANT, EnumStoneVariant.a(paramInt));
/*     */   }
/*     */   
/*     */ 
/*     */   public int toLegacyData(IBlockData paramIBlockData)
/*     */   {
/*  66 */     return ((EnumStoneVariant)paramIBlockData.get(VARIANT)).a();
/*     */   }
/*     */   
/*     */   protected BlockStateList getStateList()
/*     */   {
/*  71 */     return new BlockStateList(this, new IBlockState[] { VARIANT });
/*     */   }
/*     */   
/*     */ 
/*     */   public static enum EnumStoneVariant
/*     */     implements INamable
/*     */   {
/*     */     private static final EnumStoneVariant[] h;
/*     */     
/*     */     private final int i;
/*     */     
/*     */     private final String j;
/*     */     
/*     */     private final String k;
/*     */     
/*     */     private final MaterialMapColor l;
/*     */     
/*     */ 
/*     */     static
/*     */     {
/*  91 */       h = new EnumStoneVariant[values().length];
/*     */       
/*  93 */       for (EnumStoneVariant localEnumStoneVariant : values()) {
/*  94 */         h[localEnumStoneVariant.a()] = localEnumStoneVariant;
/*     */       }
/*     */     }
/*     */     
/*     */     private EnumStoneVariant(int paramInt, MaterialMapColor paramMaterialMapColor, String paramString)
/*     */     {
/* 100 */       this(paramInt, paramMaterialMapColor, paramString, paramString);
/*     */     }
/*     */     
/*     */     private EnumStoneVariant(int paramInt, MaterialMapColor paramMaterialMapColor, String paramString1, String paramString2) {
/* 104 */       this.i = paramInt;
/* 105 */       this.j = paramString1;
/* 106 */       this.k = paramString2;
/* 107 */       this.l = paramMaterialMapColor;
/*     */     }
/*     */     
/*     */     public int a() {
/* 111 */       return this.i;
/*     */     }
/*     */     
/*     */ 
/*     */ 
/*     */ 
/*     */     public MaterialMapColor c()
/*     */     {
/* 119 */       return this.l;
/*     */     }
/*     */     
/*     */     public String toString()
/*     */     {
/* 124 */       return this.j;
/*     */     }
/*     */     
/*     */     public static EnumStoneVariant a(int paramInt) {
/* 128 */       if ((paramInt < 0) || (paramInt >= h.length)) {
/* 129 */         paramInt = 0;
/*     */       }
/* 131 */       return h[paramInt];
/*     */     }
/*     */     
/*     */     public String getName()
/*     */     {
/* 136 */       return this.j;
/*     */     }
/*     */     
/*     */     public String d() {
/* 140 */       return this.k;
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_8_R3\BlockStone.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */
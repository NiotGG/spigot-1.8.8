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
/*     */ public abstract class BlockDoubleStepAbstract
/*     */   extends BlockStepAbstract
/*     */ {
/*  22 */   public static final BlockStateBoolean SEAMLESS = BlockStateBoolean.of("seamless");
/*  23 */   public static final BlockStateEnum<EnumStoneSlabVariant> VARIANT = BlockStateEnum.of("variant", EnumStoneSlabVariant.class);
/*     */   
/*     */   public BlockDoubleStepAbstract() {
/*  26 */     super(Material.STONE);
/*  27 */     IBlockData localIBlockData = this.blockStateList.getBlockData();
/*  28 */     if (l()) {
/*  29 */       localIBlockData = localIBlockData.set(SEAMLESS, Boolean.valueOf(false));
/*     */     } else {
/*  31 */       localIBlockData = localIBlockData.set(HALF, BlockStepAbstract.EnumSlabHalf.BOTTOM);
/*     */     }
/*  33 */     j(localIBlockData.set(VARIANT, EnumStoneSlabVariant.STONE));
/*  34 */     a(CreativeModeTab.b);
/*     */   }
/*     */   
/*     */ 
/*     */   public Item getDropType(IBlockData paramIBlockData, Random paramRandom, int paramInt)
/*     */   {
/*  40 */     return Item.getItemOf(Blocks.STONE_SLAB);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public String b(int paramInt)
/*     */   {
/*  50 */     return super.a() + "." + EnumStoneSlabVariant.a(paramInt).d();
/*     */   }
/*     */   
/*     */   public IBlockState<?> n()
/*     */   {
/*  55 */     return VARIANT;
/*     */   }
/*     */   
/*     */   public Object a(ItemStack paramItemStack)
/*     */   {
/*  60 */     return EnumStoneSlabVariant.a(paramItemStack.getData() & 0x7);
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
/*     */   public IBlockData fromLegacyData(int paramInt)
/*     */   {
/*  79 */     IBlockData localIBlockData = getBlockData().set(VARIANT, EnumStoneSlabVariant.a(paramInt & 0x7));
/*     */     
/*  81 */     if (l()) {
/*  82 */       localIBlockData = localIBlockData.set(SEAMLESS, Boolean.valueOf((paramInt & 0x8) != 0));
/*     */     } else {
/*  84 */       localIBlockData = localIBlockData.set(HALF, (paramInt & 0x8) == 0 ? BlockStepAbstract.EnumSlabHalf.BOTTOM : BlockStepAbstract.EnumSlabHalf.TOP);
/*     */     }
/*     */     
/*  87 */     return localIBlockData;
/*     */   }
/*     */   
/*     */   public int toLegacyData(IBlockData paramIBlockData)
/*     */   {
/*  92 */     int i = 0;
/*     */     
/*  94 */     i |= ((EnumStoneSlabVariant)paramIBlockData.get(VARIANT)).a();
/*     */     
/*  96 */     if (l()) {
/*  97 */       if (((Boolean)paramIBlockData.get(SEAMLESS)).booleanValue()) {
/*  98 */         i |= 0x8;
/*     */       }
/*     */     }
/* 101 */     else if (paramIBlockData.get(HALF) == BlockStepAbstract.EnumSlabHalf.TOP) {
/* 102 */       i |= 0x8;
/*     */     }
/*     */     
/*     */ 
/* 106 */     return i;
/*     */   }
/*     */   
/*     */   protected BlockStateList getStateList()
/*     */   {
/* 111 */     if (l()) {
/* 112 */       return new BlockStateList(this, new IBlockState[] { SEAMLESS, VARIANT });
/*     */     }
/*     */     
/* 115 */     return new BlockStateList(this, new IBlockState[] { HALF, VARIANT });
/*     */   }
/*     */   
/*     */   public int getDropData(IBlockData paramIBlockData)
/*     */   {
/* 120 */     return ((EnumStoneSlabVariant)paramIBlockData.get(VARIANT)).a();
/*     */   }
/*     */   
/*     */   public MaterialMapColor g(IBlockData paramIBlockData)
/*     */   {
/* 125 */     return ((EnumStoneSlabVariant)paramIBlockData.get(VARIANT)).c();
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public static enum EnumStoneSlabVariant
/*     */     implements INamable
/*     */   {
/*     */     private static final EnumStoneSlabVariant[] i;
/*     */     
/*     */     private final int j;
/*     */     
/*     */     private final MaterialMapColor k;
/*     */     
/*     */     private final String l;
/*     */     
/*     */     private final String m;
/*     */     
/*     */ 
/*     */     static
/*     */     {
/* 146 */       i = new EnumStoneSlabVariant[values().length];
/*     */       
/* 148 */       for (EnumStoneSlabVariant localEnumStoneSlabVariant : values()) {
/* 149 */         i[localEnumStoneSlabVariant.a()] = localEnumStoneSlabVariant;
/*     */       }
/*     */     }
/*     */     
/*     */     private EnumStoneSlabVariant(int paramInt, MaterialMapColor paramMaterialMapColor, String paramString) {
/* 154 */       this(paramInt, paramMaterialMapColor, paramString, paramString);
/*     */     }
/*     */     
/*     */     private EnumStoneSlabVariant(int paramInt, MaterialMapColor paramMaterialMapColor, String paramString1, String paramString2) {
/* 158 */       this.j = paramInt;
/* 159 */       this.k = paramMaterialMapColor;
/* 160 */       this.l = paramString1;
/* 161 */       this.m = paramString2;
/*     */     }
/*     */     
/*     */     public int a() {
/* 165 */       return this.j;
/*     */     }
/*     */     
/*     */ 
/*     */ 
/*     */ 
/*     */     public MaterialMapColor c()
/*     */     {
/* 173 */       return this.k;
/*     */     }
/*     */     
/*     */     public String toString()
/*     */     {
/* 178 */       return this.l;
/*     */     }
/*     */     
/*     */     public static EnumStoneSlabVariant a(int paramInt) {
/* 182 */       if ((paramInt < 0) || (paramInt >= i.length)) {
/* 183 */         paramInt = 0;
/*     */       }
/* 185 */       return i[paramInt];
/*     */     }
/*     */     
/*     */     public String getName()
/*     */     {
/* 190 */       return this.l;
/*     */     }
/*     */     
/*     */     public String d() {
/* 194 */       return this.m;
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_8_R3\BlockDoubleStepAbstract.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */
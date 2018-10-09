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
/*     */ 
/*     */ public abstract class BlockDoubleStoneStepAbstract
/*     */   extends BlockStepAbstract
/*     */ {
/*  23 */   public static final BlockStateBoolean SEAMLESS = BlockStateBoolean.of("seamless");
/*  24 */   public static final BlockStateEnum<EnumStoneSlab2Variant> VARIANT = BlockStateEnum.of("variant", EnumStoneSlab2Variant.class);
/*     */   
/*     */   public BlockDoubleStoneStepAbstract() {
/*  27 */     super(Material.STONE);
/*  28 */     IBlockData localIBlockData = this.blockStateList.getBlockData();
/*  29 */     if (l()) {
/*  30 */       localIBlockData = localIBlockData.set(SEAMLESS, Boolean.valueOf(false));
/*     */     } else {
/*  32 */       localIBlockData = localIBlockData.set(HALF, BlockStepAbstract.EnumSlabHalf.BOTTOM);
/*     */     }
/*  34 */     j(localIBlockData.set(VARIANT, EnumStoneSlab2Variant.RED_SANDSTONE));
/*  35 */     a(CreativeModeTab.b);
/*     */   }
/*     */   
/*     */   public String getName()
/*     */   {
/*  40 */     return LocaleI18n.get(a() + ".red_sandstone.name");
/*     */   }
/*     */   
/*     */ 
/*     */   public Item getDropType(IBlockData paramIBlockData, Random paramRandom, int paramInt)
/*     */   {
/*  46 */     return Item.getItemOf(Blocks.STONE_SLAB2);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public String b(int paramInt)
/*     */   {
/*  56 */     return super.a() + "." + EnumStoneSlab2Variant.a(paramInt).d();
/*     */   }
/*     */   
/*     */   public IBlockState<?> n()
/*     */   {
/*  61 */     return VARIANT;
/*     */   }
/*     */   
/*     */   public Object a(ItemStack paramItemStack)
/*     */   {
/*  66 */     return EnumStoneSlab2Variant.a(paramItemStack.getData() & 0x7);
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
/*     */   public IBlockData fromLegacyData(int paramInt)
/*     */   {
/*  82 */     IBlockData localIBlockData = getBlockData().set(VARIANT, EnumStoneSlab2Variant.a(paramInt & 0x7));
/*     */     
/*  84 */     if (l()) {
/*  85 */       localIBlockData = localIBlockData.set(SEAMLESS, Boolean.valueOf((paramInt & 0x8) != 0));
/*     */     } else {
/*  87 */       localIBlockData = localIBlockData.set(HALF, (paramInt & 0x8) == 0 ? BlockStepAbstract.EnumSlabHalf.BOTTOM : BlockStepAbstract.EnumSlabHalf.TOP);
/*     */     }
/*     */     
/*  90 */     return localIBlockData;
/*     */   }
/*     */   
/*     */   public int toLegacyData(IBlockData paramIBlockData)
/*     */   {
/*  95 */     int i = 0;
/*     */     
/*  97 */     i |= ((EnumStoneSlab2Variant)paramIBlockData.get(VARIANT)).a();
/*     */     
/*  99 */     if (l()) {
/* 100 */       if (((Boolean)paramIBlockData.get(SEAMLESS)).booleanValue()) {
/* 101 */         i |= 0x8;
/*     */       }
/*     */     }
/* 104 */     else if (paramIBlockData.get(HALF) == BlockStepAbstract.EnumSlabHalf.TOP) {
/* 105 */       i |= 0x8;
/*     */     }
/*     */     
/*     */ 
/* 109 */     return i;
/*     */   }
/*     */   
/*     */   protected BlockStateList getStateList()
/*     */   {
/* 114 */     if (l()) {
/* 115 */       return new BlockStateList(this, new IBlockState[] { SEAMLESS, VARIANT });
/*     */     }
/*     */     
/* 118 */     return new BlockStateList(this, new IBlockState[] { HALF, VARIANT });
/*     */   }
/*     */   
/*     */   public MaterialMapColor g(IBlockData paramIBlockData)
/*     */   {
/* 123 */     return ((EnumStoneSlab2Variant)paramIBlockData.get(VARIANT)).c();
/*     */   }
/*     */   
/*     */   public int getDropData(IBlockData paramIBlockData)
/*     */   {
/* 128 */     return ((EnumStoneSlab2Variant)paramIBlockData.get(VARIANT)).a();
/*     */   }
/*     */   
/*     */   public static enum EnumStoneSlab2Variant
/*     */     implements INamable
/*     */   {
/*     */     private static final EnumStoneSlab2Variant[] b;
/*     */     private final int c;
/*     */     private final String d;
/*     */     private final MaterialMapColor e;
/*     */     
/*     */     static
/*     */     {
/* 141 */       b = new EnumStoneSlab2Variant[values().length];
/*     */       
/* 143 */       for (EnumStoneSlab2Variant localEnumStoneSlab2Variant : values()) {
/* 144 */         b[localEnumStoneSlab2Variant.a()] = localEnumStoneSlab2Variant;
/*     */       }
/*     */     }
/*     */     
/*     */     private EnumStoneSlab2Variant(int paramInt, String paramString, MaterialMapColor paramMaterialMapColor) {
/* 149 */       this.c = paramInt;
/* 150 */       this.d = paramString;
/* 151 */       this.e = paramMaterialMapColor;
/*     */     }
/*     */     
/*     */     public int a() {
/* 155 */       return this.c;
/*     */     }
/*     */     
/*     */ 
/*     */ 
/*     */ 
/*     */     public MaterialMapColor c()
/*     */     {
/* 163 */       return this.e;
/*     */     }
/*     */     
/*     */     public String toString()
/*     */     {
/* 168 */       return this.d;
/*     */     }
/*     */     
/*     */     public static EnumStoneSlab2Variant a(int paramInt) {
/* 172 */       if ((paramInt < 0) || (paramInt >= b.length)) {
/* 173 */         paramInt = 0;
/*     */       }
/* 175 */       return b[paramInt];
/*     */     }
/*     */     
/*     */     public String getName()
/*     */     {
/* 180 */       return this.d;
/*     */     }
/*     */     
/*     */     public String d() {
/* 184 */       return this.d;
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_8_R3\BlockDoubleStoneStepAbstract.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */
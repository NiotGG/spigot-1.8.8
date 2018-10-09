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
/*     */ public abstract class BlockWoodenStep
/*     */   extends BlockStepAbstract
/*     */ {
/*  20 */   public static final BlockStateEnum<BlockWood.EnumLogVariant> VARIANT = BlockStateEnum.of("variant", BlockWood.EnumLogVariant.class);
/*     */   
/*     */   public BlockWoodenStep() {
/*  23 */     super(Material.WOOD);
/*  24 */     IBlockData localIBlockData = this.blockStateList.getBlockData();
/*  25 */     if (!l()) {
/*  26 */       localIBlockData = localIBlockData.set(HALF, BlockStepAbstract.EnumSlabHalf.BOTTOM);
/*     */     }
/*  28 */     j(localIBlockData.set(VARIANT, BlockWood.EnumLogVariant.OAK));
/*  29 */     a(CreativeModeTab.b);
/*     */   }
/*     */   
/*     */   public MaterialMapColor g(IBlockData paramIBlockData)
/*     */   {
/*  34 */     return ((BlockWood.EnumLogVariant)paramIBlockData.get(VARIANT)).c();
/*     */   }
/*     */   
/*     */ 
/*     */   public Item getDropType(IBlockData paramIBlockData, Random paramRandom, int paramInt)
/*     */   {
/*  40 */     return Item.getItemOf(Blocks.WOODEN_SLAB);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public String b(int paramInt)
/*     */   {
/*  50 */     return super.a() + "." + BlockWood.EnumLogVariant.a(paramInt).d();
/*     */   }
/*     */   
/*     */   public IBlockState<?> n()
/*     */   {
/*  55 */     return VARIANT;
/*     */   }
/*     */   
/*     */   public Object a(ItemStack paramItemStack)
/*     */   {
/*  60 */     return BlockWood.EnumLogVariant.a(paramItemStack.getData() & 0x7);
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
/*  76 */     IBlockData localIBlockData = getBlockData().set(VARIANT, BlockWood.EnumLogVariant.a(paramInt & 0x7));
/*     */     
/*  78 */     if (!l()) {
/*  79 */       localIBlockData = localIBlockData.set(HALF, (paramInt & 0x8) == 0 ? BlockStepAbstract.EnumSlabHalf.BOTTOM : BlockStepAbstract.EnumSlabHalf.TOP);
/*     */     }
/*     */     
/*  82 */     return localIBlockData;
/*     */   }
/*     */   
/*     */   public int toLegacyData(IBlockData paramIBlockData)
/*     */   {
/*  87 */     int i = 0;
/*     */     
/*  89 */     i |= ((BlockWood.EnumLogVariant)paramIBlockData.get(VARIANT)).a();
/*     */     
/*  91 */     if ((!l()) && (paramIBlockData.get(HALF) == BlockStepAbstract.EnumSlabHalf.TOP)) {
/*  92 */       i |= 0x8;
/*     */     }
/*     */     
/*  95 */     return i;
/*     */   }
/*     */   
/*     */   protected BlockStateList getStateList()
/*     */   {
/* 100 */     if (l()) {
/* 101 */       return new BlockStateList(this, new IBlockState[] { VARIANT });
/*     */     }
/*     */     
/* 104 */     return new BlockStateList(this, new IBlockState[] { HALF, VARIANT });
/*     */   }
/*     */   
/*     */   public int getDropData(IBlockData paramIBlockData)
/*     */   {
/* 109 */     return ((BlockWood.EnumLogVariant)paramIBlockData.get(VARIANT)).a();
/*     */   }
/*     */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_8_R3\BlockWoodenStep.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */
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
/*     */ 
/*     */ 
/*     */ 
/*     */ public class BlockCarpet
/*     */   extends Block
/*     */ {
/*  20 */   public static final BlockStateEnum<EnumColor> COLOR = BlockStateEnum.of("color", EnumColor.class);
/*     */   
/*     */   protected BlockCarpet() {
/*  23 */     super(Material.WOOL);
/*  24 */     j(this.blockStateList.getBlockData().set(COLOR, EnumColor.WHITE));
/*  25 */     a(0.0F, 0.0F, 0.0F, 1.0F, 0.0625F, 1.0F);
/*  26 */     a(true);
/*  27 */     a(CreativeModeTab.c);
/*  28 */     b(0);
/*     */   }
/*     */   
/*     */   public MaterialMapColor g(IBlockData paramIBlockData)
/*     */   {
/*  33 */     return ((EnumColor)paramIBlockData.get(COLOR)).e();
/*     */   }
/*     */   
/*     */   public boolean c()
/*     */   {
/*  38 */     return false;
/*     */   }
/*     */   
/*     */   public boolean d()
/*     */   {
/*  43 */     return false;
/*     */   }
/*     */   
/*     */   public void j()
/*     */   {
/*  48 */     b(0);
/*     */   }
/*     */   
/*     */   public void updateShape(IBlockAccess paramIBlockAccess, BlockPosition paramBlockPosition)
/*     */   {
/*  53 */     b(0);
/*     */   }
/*     */   
/*     */   protected void b(int paramInt) {
/*  57 */     int i = 0;
/*  58 */     float f = 1 * (1 + i) / 16.0F;
/*  59 */     a(0.0F, 0.0F, 0.0F, 1.0F, f, 1.0F);
/*     */   }
/*     */   
/*     */   public boolean canPlace(World paramWorld, BlockPosition paramBlockPosition)
/*     */   {
/*  64 */     return (super.canPlace(paramWorld, paramBlockPosition)) && (e(paramWorld, paramBlockPosition));
/*     */   }
/*     */   
/*     */   public void doPhysics(World paramWorld, BlockPosition paramBlockPosition, IBlockData paramIBlockData, Block paramBlock)
/*     */   {
/*  69 */     e(paramWorld, paramBlockPosition, paramIBlockData);
/*     */   }
/*     */   
/*     */   private boolean e(World paramWorld, BlockPosition paramBlockPosition, IBlockData paramIBlockData) {
/*  73 */     if (!e(paramWorld, paramBlockPosition)) {
/*  74 */       b(paramWorld, paramBlockPosition, paramIBlockData, 0);
/*  75 */       paramWorld.setAir(paramBlockPosition);
/*  76 */       return false;
/*     */     }
/*  78 */     return true;
/*     */   }
/*     */   
/*     */   private boolean e(World paramWorld, BlockPosition paramBlockPosition) {
/*  82 */     return !paramWorld.isEmpty(paramBlockPosition.down());
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
/*     */   public int getDropData(IBlockData paramIBlockData)
/*     */   {
/*  95 */     return ((EnumColor)paramIBlockData.get(COLOR)).getColorIndex();
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
/* 107 */     return getBlockData().set(COLOR, EnumColor.fromColorIndex(paramInt));
/*     */   }
/*     */   
/*     */ 
/*     */   public int toLegacyData(IBlockData paramIBlockData)
/*     */   {
/* 113 */     return ((EnumColor)paramIBlockData.get(COLOR)).getColorIndex();
/*     */   }
/*     */   
/*     */   protected BlockStateList getStateList()
/*     */   {
/* 118 */     return new BlockStateList(this, new IBlockState[] { COLOR });
/*     */   }
/*     */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_8_R3\BlockCarpet.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */
/*    */ package net.minecraft.server.v1_8_R3;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class BlockCloth
/*    */   extends Block
/*    */ {
/* 16 */   public static final BlockStateEnum<EnumColor> COLOR = BlockStateEnum.of("color", EnumColor.class);
/*    */   
/*    */   public BlockCloth(Material paramMaterial) {
/* 19 */     super(paramMaterial);
/* 20 */     j(this.blockStateList.getBlockData().set(COLOR, EnumColor.WHITE));
/* 21 */     a(CreativeModeTab.b);
/*    */   }
/*    */   
/*    */   public int getDropData(IBlockData paramIBlockData)
/*    */   {
/* 26 */     return ((EnumColor)paramIBlockData.get(COLOR)).getColorIndex();
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   public MaterialMapColor g(IBlockData paramIBlockData)
/*    */   {
/* 38 */     return ((EnumColor)paramIBlockData.get(COLOR)).e();
/*    */   }
/*    */   
/*    */   public IBlockData fromLegacyData(int paramInt)
/*    */   {
/* 43 */     return getBlockData().set(COLOR, EnumColor.fromColorIndex(paramInt));
/*    */   }
/*    */   
/*    */ 
/*    */   public int toLegacyData(IBlockData paramIBlockData)
/*    */   {
/* 49 */     return ((EnumColor)paramIBlockData.get(COLOR)).getColorIndex();
/*    */   }
/*    */   
/*    */   protected BlockStateList getStateList()
/*    */   {
/* 54 */     return new BlockStateList(this, new IBlockState[] { COLOR });
/*    */   }
/*    */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_8_R3\BlockCloth.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */
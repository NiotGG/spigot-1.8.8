/*    */ package net.minecraft.server.v1_8_R3;
/*    */ 
/*    */ import java.util.Random;
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
/*    */ 
/*    */ 
/*    */ public class BlockStainedGlass
/*    */   extends BlockHalfTransparent
/*    */ {
/* 20 */   public static final BlockStateEnum<EnumColor> COLOR = BlockStateEnum.of("color", EnumColor.class);
/*    */   
/*    */   public BlockStainedGlass(Material paramMaterial) {
/* 23 */     super(paramMaterial, false);
/* 24 */     j(this.blockStateList.getBlockData().set(COLOR, EnumColor.WHITE));
/* 25 */     a(CreativeModeTab.b);
/*    */   }
/*    */   
/*    */   public int getDropData(IBlockData paramIBlockData)
/*    */   {
/* 30 */     return ((EnumColor)paramIBlockData.get(COLOR)).getColorIndex();
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
/* 42 */     return ((EnumColor)paramIBlockData.get(COLOR)).e();
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   public int a(Random paramRandom)
/*    */   {
/* 52 */     return 0;
/*    */   }
/*    */   
/*    */   protected boolean I()
/*    */   {
/* 57 */     return true;
/*    */   }
/*    */   
/*    */   public boolean d()
/*    */   {
/* 62 */     return false;
/*    */   }
/*    */   
/*    */   public IBlockData fromLegacyData(int paramInt)
/*    */   {
/* 67 */     return getBlockData().set(COLOR, EnumColor.fromColorIndex(paramInt));
/*    */   }
/*    */   
/*    */ 
/*    */   public void onPlace(World paramWorld, BlockPosition paramBlockPosition, IBlockData paramIBlockData)
/*    */   {
/* 73 */     if (!paramWorld.isClientSide) {
/* 74 */       BlockBeacon.f(paramWorld, paramBlockPosition);
/*    */     }
/*    */   }
/*    */   
/*    */   public void remove(World paramWorld, BlockPosition paramBlockPosition, IBlockData paramIBlockData)
/*    */   {
/* 80 */     if (!paramWorld.isClientSide) {
/* 81 */       BlockBeacon.f(paramWorld, paramBlockPosition);
/*    */     }
/*    */   }
/*    */   
/*    */   public int toLegacyData(IBlockData paramIBlockData)
/*    */   {
/* 87 */     return ((EnumColor)paramIBlockData.get(COLOR)).getColorIndex();
/*    */   }
/*    */   
/*    */   protected BlockStateList getStateList()
/*    */   {
/* 92 */     return new BlockStateList(this, new IBlockState[] { COLOR });
/*    */   }
/*    */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_8_R3\BlockStainedGlass.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */
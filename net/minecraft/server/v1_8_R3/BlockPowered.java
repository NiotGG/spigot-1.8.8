/*    */ package net.minecraft.server.v1_8_R3;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class BlockPowered
/*    */   extends Block
/*    */ {
/*    */   public BlockPowered(Material paramMaterial, MaterialMapColor paramMaterialMapColor)
/*    */   {
/* 13 */     super(paramMaterial, paramMaterialMapColor);
/*    */   }
/*    */   
/*    */   public boolean isPowerSource()
/*    */   {
/* 18 */     return true;
/*    */   }
/*    */   
/*    */   public int a(IBlockAccess paramIBlockAccess, BlockPosition paramBlockPosition, IBlockData paramIBlockData, EnumDirection paramEnumDirection)
/*    */   {
/* 23 */     return 15;
/*    */   }
/*    */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_8_R3\BlockPowered.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */
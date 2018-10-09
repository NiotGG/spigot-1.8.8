/*    */ package net.minecraft.server.v1_8_R3;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public abstract class BlockDirectional
/*    */   extends Block
/*    */ {
/*  9 */   public static final BlockStateDirection FACING = BlockStateDirection.of("facing", EnumDirection.EnumDirectionLimit.HORIZONTAL);
/*    */   
/*    */   protected BlockDirectional(Material paramMaterial) {
/* 12 */     super(paramMaterial);
/*    */   }
/*    */   
/*    */   protected BlockDirectional(Material paramMaterial, MaterialMapColor paramMaterialMapColor) {
/* 16 */     super(paramMaterial, paramMaterialMapColor);
/*    */   }
/*    */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_8_R3\BlockDirectional.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */
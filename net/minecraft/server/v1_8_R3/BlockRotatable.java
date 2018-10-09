/*    */ package net.minecraft.server.v1_8_R3;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public abstract class BlockRotatable
/*    */   extends Block
/*    */ {
/*  9 */   public static final BlockStateEnum<EnumDirection.EnumAxis> AXIS = BlockStateEnum.of("axis", EnumDirection.EnumAxis.class);
/*    */   
/*    */   protected BlockRotatable(Material paramMaterial) {
/* 12 */     super(paramMaterial, paramMaterial.r());
/*    */   }
/*    */   
/*    */   protected BlockRotatable(Material paramMaterial, MaterialMapColor paramMaterialMapColor) {
/* 16 */     super(paramMaterial, paramMaterialMapColor);
/*    */   }
/*    */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_8_R3\BlockRotatable.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */
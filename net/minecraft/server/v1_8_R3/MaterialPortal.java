/*    */ package net.minecraft.server.v1_8_R3;
/*    */ 
/*    */ public class MaterialPortal extends Material {
/*    */   public MaterialPortal(MaterialMapColor paramMaterialMapColor) {
/*  5 */     super(paramMaterialMapColor);
/*    */   }
/*    */   
/*    */   public boolean isBuildable()
/*    */   {
/* 10 */     return false;
/*    */   }
/*    */   
/*    */   public boolean blocksLight()
/*    */   {
/* 15 */     return false;
/*    */   }
/*    */   
/*    */   public boolean isSolid()
/*    */   {
/* 20 */     return false;
/*    */   }
/*    */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_8_R3\MaterialPortal.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */
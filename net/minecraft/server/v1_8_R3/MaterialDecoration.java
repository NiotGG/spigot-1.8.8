/*    */ package net.minecraft.server.v1_8_R3;
/*    */ 
/*    */ public class MaterialDecoration extends Material {
/*    */   public MaterialDecoration(MaterialMapColor paramMaterialMapColor) {
/*  5 */     super(paramMaterialMapColor);
/*  6 */     p();
/*    */   }
/*    */   
/*    */   public boolean isBuildable()
/*    */   {
/* 11 */     return false;
/*    */   }
/*    */   
/*    */   public boolean blocksLight()
/*    */   {
/* 16 */     return false;
/*    */   }
/*    */   
/*    */   public boolean isSolid()
/*    */   {
/* 21 */     return false;
/*    */   }
/*    */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_8_R3\MaterialDecoration.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */
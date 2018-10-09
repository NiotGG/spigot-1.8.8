/*    */ package net.minecraft.server.v1_8_R3;
/*    */ 
/*    */ 
/*    */ 
/*    */ public class BlockHalfTransparent
/*    */   extends Block
/*    */ {
/*    */   private boolean a;
/*    */   
/*    */ 
/*    */ 
/*    */   protected BlockHalfTransparent(Material paramMaterial, boolean paramBoolean)
/*    */   {
/* 14 */     this(paramMaterial, paramBoolean, paramMaterial.r());
/*    */   }
/*    */   
/*    */   protected BlockHalfTransparent(Material paramMaterial, boolean paramBoolean, MaterialMapColor paramMaterialMapColor) {
/* 18 */     super(paramMaterial, paramMaterialMapColor);
/* 19 */     this.a = paramBoolean;
/*    */   }
/*    */   
/*    */   public boolean c()
/*    */   {
/* 24 */     return false;
/*    */   }
/*    */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_8_R3\BlockHalfTransparent.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */
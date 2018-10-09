/*    */ package net.minecraft.server.v1_8_R3;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class BlockSlime
/*    */   extends BlockHalfTransparent
/*    */ {
/*    */   public BlockSlime()
/*    */   {
/* 13 */     super(Material.CLAY, false, MaterialMapColor.c);
/* 14 */     a(CreativeModeTab.c);
/* 15 */     this.frictionFactor = 0.8F;
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   public void fallOn(World paramWorld, BlockPosition paramBlockPosition, Entity paramEntity, float paramFloat)
/*    */   {
/* 25 */     if (paramEntity.isSneaking()) {
/* 26 */       super.fallOn(paramWorld, paramBlockPosition, paramEntity, paramFloat);
/*    */     } else {
/* 28 */       paramEntity.e(paramFloat, 0.0F);
/*    */     }
/*    */   }
/*    */   
/*    */   public void a(World paramWorld, Entity paramEntity)
/*    */   {
/* 34 */     if (paramEntity.isSneaking()) {
/* 35 */       super.a(paramWorld, paramEntity);
/*    */     }
/* 37 */     else if (paramEntity.motY < 0.0D) {
/* 38 */       paramEntity.motY = (-paramEntity.motY);
/*    */     }
/*    */   }
/*    */   
/*    */ 
/*    */   public void a(World paramWorld, BlockPosition paramBlockPosition, Entity paramEntity)
/*    */   {
/* 45 */     if ((Math.abs(paramEntity.motY) < 0.1D) && (!paramEntity.isSneaking())) {
/* 46 */       double d = 0.4D + Math.abs(paramEntity.motY) * 0.2D;
/* 47 */       paramEntity.motX *= d;
/* 48 */       paramEntity.motZ *= d;
/*    */     }
/* 50 */     super.a(paramWorld, paramBlockPosition, paramEntity);
/*    */   }
/*    */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_8_R3\BlockSlime.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */
/*    */ package net.minecraft.server.v1_8_R3;
/*    */ 
/*    */ import java.util.Random;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class BlockGravel
/*    */   extends BlockFalling
/*    */ {
/*    */   public Item getDropType(IBlockData paramIBlockData, Random paramRandom, int paramInt)
/*    */   {
/* 15 */     if (paramInt > 3) {
/* 16 */       paramInt = 3;
/*    */     }
/* 18 */     if (paramRandom.nextInt(10 - paramInt * 3) == 0) {
/* 19 */       return Items.FLINT;
/*    */     }
/* 21 */     return Item.getItemOf(this);
/*    */   }
/*    */   
/*    */   public MaterialMapColor g(IBlockData paramIBlockData)
/*    */   {
/* 26 */     return MaterialMapColor.m;
/*    */   }
/*    */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_8_R3\BlockGravel.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */
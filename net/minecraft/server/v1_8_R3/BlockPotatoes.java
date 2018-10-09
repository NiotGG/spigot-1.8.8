/*    */ package net.minecraft.server.v1_8_R3;
/*    */ 
/*    */ import java.util.Random;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class BlockPotatoes
/*    */   extends BlockCrops
/*    */ {
/*    */   protected Item l()
/*    */   {
/* 13 */     return Items.POTATO;
/*    */   }
/*    */   
/*    */   protected Item n()
/*    */   {
/* 18 */     return Items.POTATO;
/*    */   }
/*    */   
/*    */   public void dropNaturally(World paramWorld, BlockPosition paramBlockPosition, IBlockData paramIBlockData, float paramFloat, int paramInt)
/*    */   {
/* 23 */     super.dropNaturally(paramWorld, paramBlockPosition, paramIBlockData, paramFloat, paramInt);
/*    */     
/* 25 */     if (paramWorld.isClientSide) {
/* 26 */       return;
/*    */     }
/*    */     
/* 29 */     if ((((Integer)paramIBlockData.get(AGE)).intValue() >= 7) && (paramWorld.random.nextInt(50) == 0)) {
/* 30 */       a(paramWorld, paramBlockPosition, new ItemStack(Items.POISONOUS_POTATO));
/*    */     }
/*    */   }
/*    */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_8_R3\BlockPotatoes.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */
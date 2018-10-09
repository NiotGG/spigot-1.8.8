/*    */ package net.minecraft.server.v1_8_R3;
/*    */ 
/*    */ import java.util.Random;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class WorldGenFire
/*    */   extends WorldGenerator
/*    */ {
/*    */   public boolean generate(World paramWorld, Random paramRandom, BlockPosition paramBlockPosition)
/*    */   {
/* 13 */     for (int i = 0; i < 64; i++) {
/* 14 */       BlockPosition localBlockPosition = paramBlockPosition.a(paramRandom.nextInt(8) - paramRandom.nextInt(8), paramRandom.nextInt(4) - paramRandom.nextInt(4), paramRandom.nextInt(8) - paramRandom.nextInt(8));
/* 15 */       if (paramWorld.isEmpty(localBlockPosition))
/*    */       {
/*    */ 
/* 18 */         if (paramWorld.getType(localBlockPosition.down()).getBlock() == Blocks.NETHERRACK)
/*    */         {
/*    */ 
/* 21 */           paramWorld.setTypeAndData(localBlockPosition, Blocks.FIRE.getBlockData(), 2); }
/*    */       }
/*    */     }
/* 24 */     return true;
/*    */   }
/*    */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_8_R3\WorldGenFire.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */
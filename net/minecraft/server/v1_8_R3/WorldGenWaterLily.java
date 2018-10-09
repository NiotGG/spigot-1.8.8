/*    */ package net.minecraft.server.v1_8_R3;
/*    */ 
/*    */ import java.util.Random;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class WorldGenWaterLily
/*    */   extends WorldGenerator
/*    */ {
/*    */   public boolean generate(World paramWorld, Random paramRandom, BlockPosition paramBlockPosition)
/*    */   {
/* 13 */     for (int i = 0; i < 10; i++) {
/* 14 */       int j = paramBlockPosition.getX() + paramRandom.nextInt(8) - paramRandom.nextInt(8);
/* 15 */       int k = paramBlockPosition.getY() + paramRandom.nextInt(4) - paramRandom.nextInt(4);
/* 16 */       int m = paramBlockPosition.getZ() + paramRandom.nextInt(8) - paramRandom.nextInt(8);
/* 17 */       if ((paramWorld.isEmpty(new BlockPosition(j, k, m))) && 
/* 18 */         (Blocks.WATERLILY.canPlace(paramWorld, new BlockPosition(j, k, m)))) {
/* 19 */         paramWorld.setTypeAndData(new BlockPosition(j, k, m), Blocks.WATERLILY.getBlockData(), 2);
/*    */       }
/*    */     }
/*    */     
/*    */ 
/* 24 */     return true;
/*    */   }
/*    */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_8_R3\WorldGenWaterLily.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */
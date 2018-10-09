/*    */ package net.minecraft.server.v1_8_R3;
/*    */ 
/*    */ import java.util.Random;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class WorldGenCactus
/*    */   extends WorldGenerator
/*    */ {
/*    */   public boolean generate(World paramWorld, Random paramRandom, BlockPosition paramBlockPosition)
/*    */   {
/* 13 */     for (int i = 0; i < 10; i++) {
/* 14 */       BlockPosition localBlockPosition = paramBlockPosition.a(paramRandom.nextInt(8) - paramRandom.nextInt(8), paramRandom.nextInt(4) - paramRandom.nextInt(4), paramRandom.nextInt(8) - paramRandom.nextInt(8));
/*    */       
/* 16 */       if (paramWorld.isEmpty(localBlockPosition)) {
/* 17 */         int j = 1 + paramRandom.nextInt(paramRandom.nextInt(3) + 1);
/* 18 */         for (int k = 0; k < j; k++) {
/* 19 */           if (Blocks.CACTUS.e(paramWorld, localBlockPosition)) {
/* 20 */             paramWorld.setTypeAndData(localBlockPosition.up(k), Blocks.CACTUS.getBlockData(), 2);
/*    */           }
/*    */         }
/*    */       }
/*    */     }
/*    */     
/* 26 */     return true;
/*    */   }
/*    */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_8_R3\WorldGenCactus.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */
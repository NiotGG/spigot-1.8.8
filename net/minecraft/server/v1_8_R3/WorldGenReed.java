/*    */ package net.minecraft.server.v1_8_R3;
/*    */ 
/*    */ import java.util.Random;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class WorldGenReed
/*    */   extends WorldGenerator
/*    */ {
/*    */   public boolean generate(World paramWorld, Random paramRandom, BlockPosition paramBlockPosition)
/*    */   {
/* 14 */     for (int i = 0; i < 20; i++) {
/* 15 */       BlockPosition localBlockPosition1 = paramBlockPosition.a(paramRandom.nextInt(4) - paramRandom.nextInt(4), 0, paramRandom.nextInt(4) - paramRandom.nextInt(4));
/*    */       
/* 17 */       if (paramWorld.isEmpty(localBlockPosition1)) {
/* 18 */         BlockPosition localBlockPosition2 = localBlockPosition1.down();
/*    */         
/* 20 */         if ((paramWorld.getType(localBlockPosition2.west()).getBlock().getMaterial() == Material.WATER) || (paramWorld.getType(localBlockPosition2.east()).getBlock().getMaterial() == Material.WATER) || (paramWorld.getType(localBlockPosition2.north()).getBlock().getMaterial() == Material.WATER) || (paramWorld.getType(localBlockPosition2.south()).getBlock().getMaterial() == Material.WATER)) {
/* 21 */           int j = 2 + paramRandom.nextInt(paramRandom.nextInt(3) + 1);
/* 22 */           for (int k = 0; k < j; k++) {
/* 23 */             if (Blocks.REEDS.e(paramWorld, localBlockPosition1)) {
/* 24 */               paramWorld.setTypeAndData(localBlockPosition1.up(k), Blocks.REEDS.getBlockData(), 2);
/*    */             }
/*    */           }
/*    */         }
/*    */       }
/*    */     }
/*    */     
/* 31 */     return true;
/*    */   }
/*    */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_8_R3\WorldGenReed.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */
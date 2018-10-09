/*    */ package net.minecraft.server.v1_8_R3;
/*    */ 
/*    */ import java.util.Random;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class WorldGenMelon
/*    */   extends WorldGenerator
/*    */ {
/*    */   public boolean generate(World paramWorld, Random paramRandom, BlockPosition paramBlockPosition)
/*    */   {
/* 13 */     for (int i = 0; i < 64; i++) {
/* 14 */       BlockPosition localBlockPosition = paramBlockPosition.a(paramRandom.nextInt(8) - paramRandom.nextInt(8), paramRandom.nextInt(4) - paramRandom.nextInt(4), paramRandom.nextInt(8) - paramRandom.nextInt(8));
/* 15 */       if ((Blocks.MELON_BLOCK.canPlace(paramWorld, localBlockPosition)) && (paramWorld.getType(localBlockPosition.down()).getBlock() == Blocks.GRASS)) {
/* 16 */         paramWorld.setTypeAndData(localBlockPosition, Blocks.MELON_BLOCK.getBlockData(), 2);
/*    */       }
/*    */     }
/*    */     
/* 20 */     return true;
/*    */   }
/*    */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_8_R3\WorldGenMelon.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */
/*    */ package net.minecraft.server.v1_8_R3;
/*    */ 
/*    */ import java.util.Random;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class WorldGenDeadBush
/*    */   extends WorldGenerator
/*    */ {
/*    */   public boolean generate(World paramWorld, Random paramRandom, BlockPosition paramBlockPosition)
/*    */   {
/*    */     Block localBlock;
/* 18 */     while ((((localBlock = paramWorld.getType(paramBlockPosition).getBlock()).getMaterial() == Material.AIR) || (localBlock.getMaterial() == Material.LEAVES)) && (paramBlockPosition.getY() > 0)) {
/* 19 */       paramBlockPosition = paramBlockPosition.down();
/*    */     }
/*    */     
/* 22 */     for (int i = 0; i < 4; i++) {
/* 23 */       BlockPosition localBlockPosition = paramBlockPosition.a(paramRandom.nextInt(8) - paramRandom.nextInt(8), paramRandom.nextInt(4) - paramRandom.nextInt(4), paramRandom.nextInt(8) - paramRandom.nextInt(8));
/* 24 */       if ((paramWorld.isEmpty(localBlockPosition)) && 
/* 25 */         (Blocks.DEADBUSH.f(paramWorld, localBlockPosition, Blocks.DEADBUSH.getBlockData()))) {
/* 26 */         paramWorld.setTypeAndData(localBlockPosition, Blocks.DEADBUSH.getBlockData(), 2);
/*    */       }
/*    */     }
/*    */     
/*    */ 
/* 31 */     return true;
/*    */   }
/*    */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_8_R3\WorldGenDeadBush.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */
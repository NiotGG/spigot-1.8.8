/*    */ package net.minecraft.server.v1_8_R3;
/*    */ 
/*    */ import java.util.Random;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class WorldGenPumpkin
/*    */   extends WorldGenerator
/*    */ {
/*    */   public boolean generate(World paramWorld, Random paramRandom, BlockPosition paramBlockPosition)
/*    */   {
/* 15 */     for (int i = 0; i < 64; i++) {
/* 16 */       BlockPosition localBlockPosition = paramBlockPosition.a(paramRandom.nextInt(8) - paramRandom.nextInt(8), paramRandom.nextInt(4) - paramRandom.nextInt(4), paramRandom.nextInt(8) - paramRandom.nextInt(8));
/*    */       
/* 18 */       if ((paramWorld.isEmpty(localBlockPosition)) && (paramWorld.getType(localBlockPosition.down()).getBlock() == Blocks.GRASS) && 
/* 19 */         (Blocks.PUMPKIN.canPlace(paramWorld, localBlockPosition))) {
/* 20 */         paramWorld.setTypeAndData(localBlockPosition, Blocks.PUMPKIN.getBlockData().set(BlockPumpkin.FACING, EnumDirection.EnumDirectionLimit.HORIZONTAL.a(paramRandom)), 2);
/*    */       }
/*    */     }
/*    */     
/*    */ 
/* 25 */     return true;
/*    */   }
/*    */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_8_R3\WorldGenPumpkin.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */
/*    */ package net.minecraft.server.v1_8_R3;
/*    */ 
/*    */ import java.util.Random;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class WorldGenLightStone2
/*    */   extends WorldGenerator
/*    */ {
/*    */   public boolean generate(World paramWorld, Random paramRandom, BlockPosition paramBlockPosition)
/*    */   {
/* 15 */     if (!paramWorld.isEmpty(paramBlockPosition)) {
/* 16 */       return false;
/*    */     }
/* 18 */     if (paramWorld.getType(paramBlockPosition.up()).getBlock() != Blocks.NETHERRACK) {
/* 19 */       return false;
/*    */     }
/* 21 */     paramWorld.setTypeAndData(paramBlockPosition, Blocks.GLOWSTONE.getBlockData(), 2);
/*    */     
/* 23 */     for (int i = 0; i < 1500; i++) {
/* 24 */       BlockPosition localBlockPosition = paramBlockPosition.a(paramRandom.nextInt(8) - paramRandom.nextInt(8), -paramRandom.nextInt(12), paramRandom.nextInt(8) - paramRandom.nextInt(8));
/* 25 */       if (paramWorld.getType(localBlockPosition).getBlock().getMaterial() == Material.AIR)
/*    */       {
/*    */ 
/*    */ 
/* 29 */         int j = 0;
/* 30 */         for (EnumDirection localEnumDirection : EnumDirection.values()) {
/* 31 */           if (paramWorld.getType(localBlockPosition.shift(localEnumDirection)).getBlock() == Blocks.GLOWSTONE) {
/* 32 */             j++;
/*    */           }
/*    */           
/* 35 */           if (j > 1) {
/*    */             break;
/*    */           }
/*    */         }
/*    */         
/* 40 */         if (j == 1) {
/* 41 */           paramWorld.setTypeAndData(localBlockPosition, Blocks.GLOWSTONE.getBlockData(), 2);
/*    */         }
/*    */       }
/*    */     }
/* 45 */     return true;
/*    */   }
/*    */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_8_R3\WorldGenLightStone2.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */
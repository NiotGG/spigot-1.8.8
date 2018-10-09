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
/*    */ public class WorldGenVines
/*    */   extends WorldGenerator
/*    */ {
/*    */   public boolean generate(World paramWorld, Random paramRandom, BlockPosition paramBlockPosition)
/*    */   {
/* 16 */     while (paramBlockPosition.getY() < 128) {
/* 17 */       if (paramWorld.isEmpty(paramBlockPosition))
/*    */       {
/* 19 */         for (EnumDirection localEnumDirection : EnumDirection.EnumDirectionLimit.HORIZONTAL.a()) {
/* 20 */           if (Blocks.VINE.canPlace(paramWorld, paramBlockPosition, localEnumDirection)) {
/* 21 */             IBlockData localIBlockData = Blocks.VINE.getBlockData().set(BlockVine.NORTH, Boolean.valueOf(localEnumDirection == EnumDirection.NORTH)).set(BlockVine.EAST, Boolean.valueOf(localEnumDirection == EnumDirection.EAST)).set(BlockVine.SOUTH, Boolean.valueOf(localEnumDirection == EnumDirection.SOUTH)).set(BlockVine.WEST, Boolean.valueOf(localEnumDirection == EnumDirection.WEST));
/*    */             
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/* 27 */             paramWorld.setTypeAndData(paramBlockPosition, localIBlockData, 2);
/* 28 */             break;
/*    */           }
/*    */         }
/*    */       } else {
/* 32 */         paramBlockPosition = paramBlockPosition.a(paramRandom.nextInt(4) - paramRandom.nextInt(4), 0, paramRandom.nextInt(4) - paramRandom.nextInt(4));
/*    */       }
/* 34 */       paramBlockPosition = paramBlockPosition.up();
/*    */     }
/*    */     
/* 37 */     return true;
/*    */   }
/*    */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_8_R3\WorldGenVines.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */
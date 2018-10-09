/*    */ package net.minecraft.server.v1_8_R3;
/*    */ 
/*    */ import java.util.Random;
/*    */ 
/*    */ 
/*    */ 
/*    */ public class WorldGenTaigaStructure
/*    */   extends WorldGenerator
/*    */ {
/*    */   private final Block a;
/*    */   private final int b;
/*    */   
/*    */   public WorldGenTaigaStructure(Block paramBlock, int paramInt)
/*    */   {
/* 15 */     super(false);
/* 16 */     this.a = paramBlock;
/* 17 */     this.b = paramInt;
/*    */   }
/*    */   
/*    */   public boolean generate(World paramWorld, Random paramRandom, BlockPosition paramBlockPosition)
/*    */   {
/* 22 */     while (paramBlockPosition.getY() > 3) {
/* 23 */       if (!paramWorld.isEmpty(paramBlockPosition.down())) {
/* 24 */         Block localBlock = paramWorld.getType(paramBlockPosition.down()).getBlock();
/* 25 */         if ((localBlock == Blocks.GRASS) || (localBlock == Blocks.DIRT) || (localBlock == Blocks.STONE)) {
/*    */           break;
/*    */         }
/*    */       }
/* 29 */       paramBlockPosition = paramBlockPosition.down();
/*    */     }
/* 31 */     if (paramBlockPosition.getY() <= 3) {
/* 32 */       return false;
/*    */     }
/*    */     
/* 35 */     int i = this.b;
/* 36 */     int j = 0;
/* 37 */     while ((i >= 0) && (j < 3)) {
/* 38 */       int k = i + paramRandom.nextInt(2);
/* 39 */       int m = i + paramRandom.nextInt(2);
/* 40 */       int n = i + paramRandom.nextInt(2);
/* 41 */       float f = (k + m + n) * 0.333F + 0.5F;
/*    */       
/* 43 */       for (BlockPosition localBlockPosition : BlockPosition.a(paramBlockPosition.a(-k, -m, -n), paramBlockPosition.a(k, m, n))) {
/* 44 */         if (localBlockPosition.i(paramBlockPosition) <= f * f) {
/* 45 */           paramWorld.setTypeAndData(localBlockPosition, this.a.getBlockData(), 4);
/*    */         }
/*    */       }
/*    */       
/* 49 */       paramBlockPosition = paramBlockPosition.a(-(i + 1) + paramRandom.nextInt(2 + i * 2), 0 - paramRandom.nextInt(2), -(i + 1) + paramRandom.nextInt(2 + i * 2));
/* 50 */       j++;
/*    */     }
/*    */     
/* 53 */     return true;
/*    */   }
/*    */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_8_R3\WorldGenTaigaStructure.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */
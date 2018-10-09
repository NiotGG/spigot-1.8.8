/*    */ package net.minecraft.server.v1_8_R3;
/*    */ 
/*    */ import java.util.Random;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class WorldGenClay
/*    */   extends WorldGenerator
/*    */ {
/*    */   private Block a;
/*    */   private int b;
/*    */   
/*    */   public WorldGenClay(int paramInt)
/*    */   {
/* 16 */     this.a = Blocks.CLAY;
/* 17 */     this.b = paramInt;
/*    */   }
/*    */   
/*    */   public boolean generate(World paramWorld, Random paramRandom, BlockPosition paramBlockPosition)
/*    */   {
/* 22 */     if (paramWorld.getType(paramBlockPosition).getBlock().getMaterial() != Material.WATER) {
/* 23 */       return false;
/*    */     }
/* 25 */     int i = paramRandom.nextInt(this.b - 2) + 2;
/* 26 */     int j = 1;
/* 27 */     for (int k = paramBlockPosition.getX() - i; k <= paramBlockPosition.getX() + i; k++) {
/* 28 */       for (int m = paramBlockPosition.getZ() - i; m <= paramBlockPosition.getZ() + i; m++) {
/* 29 */         int n = k - paramBlockPosition.getX();
/* 30 */         int i1 = m - paramBlockPosition.getZ();
/* 31 */         if (n * n + i1 * i1 <= i * i)
/*    */         {
/*    */ 
/* 34 */           for (int i2 = paramBlockPosition.getY() - j; i2 <= paramBlockPosition.getY() + j; i2++) {
/* 35 */             BlockPosition localBlockPosition = new BlockPosition(k, i2, m);
/* 36 */             Block localBlock = paramWorld.getType(localBlockPosition).getBlock();
/* 37 */             if ((localBlock == Blocks.DIRT) || (localBlock == Blocks.CLAY)) {
/* 38 */               paramWorld.setTypeAndData(localBlockPosition, this.a.getBlockData(), 2);
/*    */             }
/*    */           }
/*    */         }
/*    */       }
/*    */     }
/* 44 */     return true;
/*    */   }
/*    */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_8_R3\WorldGenClay.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */
/*    */ package net.minecraft.server.v1_8_R3;
/*    */ 
/*    */ import java.util.Random;
/*    */ 
/*    */ 
/*    */ 
/*    */ public class WorldGenPackedIce1
/*    */   extends WorldGenerator
/*    */ {
/*    */   private Block a;
/*    */   private int b;
/*    */   
/*    */   public WorldGenPackedIce1(int paramInt)
/*    */   {
/* 15 */     this.a = Blocks.PACKED_ICE;
/* 16 */     this.b = paramInt;
/*    */   }
/*    */   
/*    */   public boolean generate(World paramWorld, Random paramRandom, BlockPosition paramBlockPosition)
/*    */   {
/* 21 */     while ((paramWorld.isEmpty(paramBlockPosition)) && (paramBlockPosition.getY() > 2)) {
/* 22 */       paramBlockPosition = paramBlockPosition.down();
/*    */     }
/*    */     
/* 25 */     if (paramWorld.getType(paramBlockPosition).getBlock() != Blocks.SNOW) {
/* 26 */       return false;
/*    */     }
/* 28 */     int i = paramRandom.nextInt(this.b - 2) + 2;
/* 29 */     int j = 1;
/* 30 */     for (int k = paramBlockPosition.getX() - i; k <= paramBlockPosition.getX() + i; k++) {
/* 31 */       for (int m = paramBlockPosition.getZ() - i; m <= paramBlockPosition.getZ() + i; m++) {
/* 32 */         int n = k - paramBlockPosition.getX();
/* 33 */         int i1 = m - paramBlockPosition.getZ();
/* 34 */         if (n * n + i1 * i1 <= i * i)
/*    */         {
/*    */ 
/* 37 */           for (int i2 = paramBlockPosition.getY() - j; i2 <= paramBlockPosition.getY() + j; i2++) {
/* 38 */             BlockPosition localBlockPosition = new BlockPosition(k, i2, m);
/* 39 */             Block localBlock = paramWorld.getType(localBlockPosition).getBlock();
/* 40 */             if ((localBlock == Blocks.DIRT) || (localBlock == Blocks.SNOW) || (localBlock == Blocks.ICE)) {
/* 41 */               paramWorld.setTypeAndData(localBlockPosition, this.a.getBlockData(), 2);
/*    */             }
/*    */           }
/*    */         }
/*    */       }
/*    */     }
/* 47 */     return true;
/*    */   }
/*    */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_8_R3\WorldGenPackedIce1.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */
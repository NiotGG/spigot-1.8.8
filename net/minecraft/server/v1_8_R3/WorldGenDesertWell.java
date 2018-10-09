/*    */ package net.minecraft.server.v1_8_R3;
/*    */ 
/*    */ import com.google.common.base.Predicates;
/*    */ import java.util.Random;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class WorldGenDesertWell
/*    */   extends WorldGenerator
/*    */ {
/* 15 */   private static final BlockStatePredicate a = BlockStatePredicate.a(Blocks.SAND).a(BlockSand.VARIANT, Predicates.equalTo(BlockSand.EnumSandVariant.SAND));
/* 16 */   private final IBlockData b = Blocks.STONE_SLAB.getBlockData().set(BlockDoubleStepAbstract.VARIANT, BlockDoubleStepAbstract.EnumStoneSlabVariant.SAND).set(BlockStepAbstract.HALF, BlockStepAbstract.EnumSlabHalf.BOTTOM);
/* 17 */   private final IBlockData c = Blocks.SANDSTONE.getBlockData();
/* 18 */   private final IBlockData d = Blocks.FLOWING_WATER.getBlockData();
/*    */   
/*    */   public boolean generate(World paramWorld, Random paramRandom, BlockPosition paramBlockPosition)
/*    */   {
/* 22 */     while ((paramWorld.isEmpty(paramBlockPosition)) && (paramBlockPosition.getY() > 2)) {
/* 23 */       paramBlockPosition = paramBlockPosition.down();
/*    */     }
/*    */     
/* 26 */     if (!a.a(paramWorld.getType(paramBlockPosition))) {
/* 27 */       return false;
/*    */     }
/*    */     
/*    */     int k;
/* 31 */     for (int i = -2; i <= 2; i++) {
/* 32 */       for (k = -2; k <= 2; k++) {
/* 33 */         if ((paramWorld.isEmpty(paramBlockPosition.a(i, -1, k))) && (paramWorld.isEmpty(paramBlockPosition.a(i, -2, k)))) {
/* 34 */           return false;
/*    */         }
/*    */       }
/*    */     }
/*    */     
/*    */ 
/* 40 */     for (i = -1; i <= 0; i++) {
/* 41 */       for (k = -2; k <= 2; k++) {
/* 42 */         for (int n = -2; n <= 2; n++) {
/* 43 */           paramWorld.setTypeAndData(paramBlockPosition.a(k, i, n), this.c, 2);
/*    */         }
/*    */       }
/*    */     }
/*    */     
/*    */ 
/* 49 */     paramWorld.setTypeAndData(paramBlockPosition, this.d, 2);
/* 50 */     for (EnumDirection localEnumDirection : EnumDirection.EnumDirectionLimit.HORIZONTAL) {
/* 51 */       paramWorld.setTypeAndData(paramBlockPosition.shift(localEnumDirection), this.d, 2);
/*    */     }
/*    */     
/*    */     int m;
/* 55 */     for (int j = -2; j <= 2; j++) {
/* 56 */       for (m = -2; m <= 2; m++) {
/* 57 */         if ((j == -2) || (j == 2) || (m == -2) || (m == 2)) {
/* 58 */           paramWorld.setTypeAndData(paramBlockPosition.a(j, 1, m), this.c, 2);
/*    */         }
/*    */       }
/*    */     }
/*    */     
/* 63 */     paramWorld.setTypeAndData(paramBlockPosition.a(2, 1, 0), this.b, 2);
/* 64 */     paramWorld.setTypeAndData(paramBlockPosition.a(-2, 1, 0), this.b, 2);
/* 65 */     paramWorld.setTypeAndData(paramBlockPosition.a(0, 1, 2), this.b, 2);
/* 66 */     paramWorld.setTypeAndData(paramBlockPosition.a(0, 1, -2), this.b, 2);
/*    */     
/*    */ 
/* 69 */     for (j = -1; j <= 1; j++) {
/* 70 */       for (m = -1; m <= 1; m++) {
/* 71 */         if ((j == 0) && (m == 0)) {
/* 72 */           paramWorld.setTypeAndData(paramBlockPosition.a(j, 4, m), this.c, 2);
/*    */         } else {
/* 74 */           paramWorld.setTypeAndData(paramBlockPosition.a(j, 4, m), this.b, 2);
/*    */         }
/*    */       }
/*    */     }
/*    */     
/*    */ 
/* 80 */     for (j = 1; j <= 3; j++) {
/* 81 */       paramWorld.setTypeAndData(paramBlockPosition.a(-1, j, -1), this.c, 2);
/* 82 */       paramWorld.setTypeAndData(paramBlockPosition.a(-1, j, 1), this.c, 2);
/* 83 */       paramWorld.setTypeAndData(paramBlockPosition.a(1, j, -1), this.c, 2);
/* 84 */       paramWorld.setTypeAndData(paramBlockPosition.a(1, j, 1), this.c, 2);
/*    */     }
/*    */     
/* 87 */     return true;
/*    */   }
/*    */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_8_R3\WorldGenDesertWell.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */
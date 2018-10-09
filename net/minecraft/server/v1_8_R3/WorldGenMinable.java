/*    */ package net.minecraft.server.v1_8_R3;
/*    */ 
/*    */ import com.google.common.base.Predicate;
/*    */ import java.util.Random;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class WorldGenMinable
/*    */   extends WorldGenerator
/*    */ {
/*    */   private final IBlockData a;
/*    */   private final int b;
/*    */   private final Predicate<IBlockData> c;
/*    */   
/*    */   public WorldGenMinable(IBlockData paramIBlockData, int paramInt)
/*    */   {
/* 20 */     this(paramIBlockData, paramInt, BlockPredicate.a(Blocks.STONE));
/*    */   }
/*    */   
/*    */   public WorldGenMinable(IBlockData paramIBlockData, int paramInt, Predicate<IBlockData> paramPredicate) {
/* 24 */     this.a = paramIBlockData;
/* 25 */     this.b = paramInt;
/* 26 */     this.c = paramPredicate;
/*    */   }
/*    */   
/*    */   public boolean generate(World paramWorld, Random paramRandom, BlockPosition paramBlockPosition)
/*    */   {
/* 31 */     float f1 = paramRandom.nextFloat() * 3.1415927F;
/*    */     
/* 33 */     double d1 = paramBlockPosition.getX() + 8 + MathHelper.sin(f1) * this.b / 8.0F;
/* 34 */     double d2 = paramBlockPosition.getX() + 8 - MathHelper.sin(f1) * this.b / 8.0F;
/* 35 */     double d3 = paramBlockPosition.getZ() + 8 + MathHelper.cos(f1) * this.b / 8.0F;
/* 36 */     double d4 = paramBlockPosition.getZ() + 8 - MathHelper.cos(f1) * this.b / 8.0F;
/*    */     
/* 38 */     double d5 = paramBlockPosition.getY() + paramRandom.nextInt(3) - 2;
/* 39 */     double d6 = paramBlockPosition.getY() + paramRandom.nextInt(3) - 2;
/*    */     
/* 41 */     for (int i = 0; i < this.b; i++) {
/* 42 */       float f2 = i / this.b;
/* 43 */       double d7 = d1 + (d2 - d1) * f2;
/* 44 */       double d8 = d5 + (d6 - d5) * f2;
/* 45 */       double d9 = d3 + (d4 - d3) * f2;
/*    */       
/* 47 */       double d10 = paramRandom.nextDouble() * this.b / 16.0D;
/* 48 */       double d11 = (MathHelper.sin(3.1415927F * f2) + 1.0F) * d10 + 1.0D;
/* 49 */       double d12 = (MathHelper.sin(3.1415927F * f2) + 1.0F) * d10 + 1.0D;
/*    */       
/* 51 */       int j = MathHelper.floor(d7 - d11 / 2.0D);
/* 52 */       int k = MathHelper.floor(d8 - d12 / 2.0D);
/* 53 */       int m = MathHelper.floor(d9 - d11 / 2.0D);
/*    */       
/* 55 */       int n = MathHelper.floor(d7 + d11 / 2.0D);
/* 56 */       int i1 = MathHelper.floor(d8 + d12 / 2.0D);
/* 57 */       int i2 = MathHelper.floor(d9 + d11 / 2.0D);
/*    */       
/* 59 */       for (int i3 = j; i3 <= n; i3++) {
/* 60 */         double d13 = (i3 + 0.5D - d7) / (d11 / 2.0D);
/* 61 */         if (d13 * d13 < 1.0D) {
/* 62 */           for (int i4 = k; i4 <= i1; i4++) {
/* 63 */             double d14 = (i4 + 0.5D - d8) / (d12 / 2.0D);
/* 64 */             if (d13 * d13 + d14 * d14 < 1.0D) {
/* 65 */               for (int i5 = m; i5 <= i2; i5++) {
/* 66 */                 double d15 = (i5 + 0.5D - d9) / (d11 / 2.0D);
/* 67 */                 if (d13 * d13 + d14 * d14 + d15 * d15 < 1.0D) {
/* 68 */                   BlockPosition localBlockPosition = new BlockPosition(i3, i4, i5);
/* 69 */                   if (this.c.apply(paramWorld.getType(localBlockPosition))) {
/* 70 */                     paramWorld.setTypeAndData(localBlockPosition, this.a, 2);
/*    */                   }
/*    */                 }
/*    */               }
/*    */             }
/*    */           }
/*    */         }
/*    */       }
/*    */     }
/*    */     
/* 80 */     return true;
/*    */   }
/*    */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_8_R3\WorldGenMinable.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */
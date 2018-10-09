/*    */ package net.minecraft.server.v1_8_R3;
/*    */ 
/*    */ import java.util.Random;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class WorldGenFlowers
/*    */   extends WorldGenerator
/*    */ {
/*    */   private BlockFlowers a;
/*    */   private IBlockData b;
/*    */   
/*    */   public WorldGenFlowers(BlockFlowers paramBlockFlowers, BlockFlowers.EnumFlowerVarient paramEnumFlowerVarient)
/*    */   {
/* 16 */     a(paramBlockFlowers, paramEnumFlowerVarient);
/*    */   }
/*    */   
/*    */   public void a(BlockFlowers paramBlockFlowers, BlockFlowers.EnumFlowerVarient paramEnumFlowerVarient) {
/* 20 */     this.a = paramBlockFlowers;
/* 21 */     this.b = paramBlockFlowers.getBlockData().set(paramBlockFlowers.n(), paramEnumFlowerVarient);
/*    */   }
/*    */   
/*    */   public boolean generate(World paramWorld, Random paramRandom, BlockPosition paramBlockPosition)
/*    */   {
/* 26 */     for (int i = 0; i < 64; i++) {
/* 27 */       BlockPosition localBlockPosition = paramBlockPosition.a(paramRandom.nextInt(8) - paramRandom.nextInt(8), paramRandom.nextInt(4) - paramRandom.nextInt(4), paramRandom.nextInt(8) - paramRandom.nextInt(8));
/* 28 */       if ((paramWorld.isEmpty(localBlockPosition)) && ((!paramWorld.worldProvider.o()) || (localBlockPosition.getY() < 255)) && 
/* 29 */         (this.a.f(paramWorld, localBlockPosition, this.b))) {
/* 30 */         paramWorld.setTypeAndData(localBlockPosition, this.b, 2);
/*    */       }
/*    */     }
/*    */     
/*    */ 
/* 35 */     return true;
/*    */   }
/*    */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_8_R3\WorldGenFlowers.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */
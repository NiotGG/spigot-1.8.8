/*    */ package net.minecraft.server.v1_8_R3;
/*    */ 
/*    */ import java.util.Random;
/*    */ 
/*    */ 
/*    */ 
/*    */ public class WorldGenMushrooms
/*    */   extends WorldGenerator
/*    */ {
/*    */   private BlockPlant a;
/*    */   
/*    */   public WorldGenMushrooms(BlockPlant paramBlockPlant)
/*    */   {
/* 14 */     this.a = paramBlockPlant;
/*    */   }
/*    */   
/*    */   public boolean generate(World paramWorld, Random paramRandom, BlockPosition paramBlockPosition)
/*    */   {
/* 19 */     for (int i = 0; i < 64; i++) {
/* 20 */       BlockPosition localBlockPosition = paramBlockPosition.a(paramRandom.nextInt(8) - paramRandom.nextInt(8), paramRandom.nextInt(4) - paramRandom.nextInt(4), paramRandom.nextInt(8) - paramRandom.nextInt(8));
/* 21 */       if ((paramWorld.isEmpty(localBlockPosition)) && ((!paramWorld.worldProvider.o()) || (localBlockPosition.getY() < 255)) && 
/* 22 */         (this.a.f(paramWorld, localBlockPosition, this.a.getBlockData()))) {
/* 23 */         paramWorld.setTypeAndData(localBlockPosition, this.a.getBlockData(), 2);
/*    */       }
/*    */     }
/*    */     
/*    */ 
/* 28 */     return true;
/*    */   }
/*    */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_8_R3\WorldGenMushrooms.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */
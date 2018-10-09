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
/*    */ public class WorldGenTallPlant
/*    */   extends WorldGenerator
/*    */ {
/*    */   private BlockTallPlant.EnumTallFlowerVariants a;
/*    */   
/*    */   public void a(BlockTallPlant.EnumTallFlowerVariants paramEnumTallFlowerVariants)
/*    */   {
/* 18 */     this.a = paramEnumTallFlowerVariants;
/*    */   }
/*    */   
/*    */   public boolean generate(World paramWorld, Random paramRandom, BlockPosition paramBlockPosition)
/*    */   {
/* 23 */     boolean bool = false;
/*    */     
/* 25 */     for (int i = 0; i < 64; i++) {
/* 26 */       BlockPosition localBlockPosition = paramBlockPosition.a(paramRandom.nextInt(8) - paramRandom.nextInt(8), paramRandom.nextInt(4) - paramRandom.nextInt(4), paramRandom.nextInt(8) - paramRandom.nextInt(8));
/* 27 */       if ((paramWorld.isEmpty(localBlockPosition)) && ((!paramWorld.worldProvider.o()) || (localBlockPosition.getY() < 254)) && 
/* 28 */         (Blocks.DOUBLE_PLANT.canPlace(paramWorld, localBlockPosition))) {
/* 29 */         Blocks.DOUBLE_PLANT.a(paramWorld, localBlockPosition, this.a, 2);
/* 30 */         bool = true;
/*    */       }
/*    */     }
/*    */     
/*    */ 
/* 35 */     return bool;
/*    */   }
/*    */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_8_R3\WorldGenTallPlant.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */
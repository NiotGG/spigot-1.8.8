/*    */ package net.minecraft.server.v1_8_R3;
/*    */ 
/*    */ import java.util.Random;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class WorldGenGrass
/*    */   extends WorldGenerator
/*    */ {
/*    */   private final IBlockData a;
/*    */   
/*    */   public WorldGenGrass(BlockLongGrass.EnumTallGrassType paramEnumTallGrassType)
/*    */   {
/* 17 */     this.a = Blocks.TALLGRASS.getBlockData().set(BlockLongGrass.TYPE, paramEnumTallGrassType);
/*    */   }
/*    */   
/*    */ 
/*    */   public boolean generate(World paramWorld, Random paramRandom, BlockPosition paramBlockPosition)
/*    */   {
/*    */     Block localBlock;
/* 24 */     while ((((localBlock = paramWorld.getType(paramBlockPosition).getBlock()).getMaterial() == Material.AIR) || (localBlock.getMaterial() == Material.LEAVES)) && (paramBlockPosition.getY() > 0)) {
/* 25 */       paramBlockPosition = paramBlockPosition.down();
/*    */     }
/*    */     
/* 28 */     for (int i = 0; i < 128; i++) {
/* 29 */       BlockPosition localBlockPosition = paramBlockPosition.a(paramRandom.nextInt(8) - paramRandom.nextInt(8), paramRandom.nextInt(4) - paramRandom.nextInt(4), paramRandom.nextInt(8) - paramRandom.nextInt(8));
/* 30 */       if ((paramWorld.isEmpty(localBlockPosition)) && 
/* 31 */         (Blocks.TALLGRASS.f(paramWorld, localBlockPosition, this.a))) {
/* 32 */         paramWorld.setTypeAndData(localBlockPosition, this.a, 2);
/*    */       }
/*    */     }
/*    */     
/*    */ 
/* 37 */     return true;
/*    */   }
/*    */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_8_R3\WorldGenGrass.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */
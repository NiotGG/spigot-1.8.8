/*    */ package net.minecraft.server.v1_8_R3;
/*    */ 
/*    */ import java.util.Random;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class WorldGenLiquids
/*    */   extends WorldGenerator
/*    */ {
/*    */   private Block a;
/*    */   
/*    */   public WorldGenLiquids(Block paramBlock)
/*    */   {
/* 15 */     this.a = paramBlock;
/*    */   }
/*    */   
/*    */   public boolean generate(World paramWorld, Random paramRandom, BlockPosition paramBlockPosition)
/*    */   {
/* 20 */     if (paramWorld.getType(paramBlockPosition.up()).getBlock() != Blocks.STONE) {
/* 21 */       return false;
/*    */     }
/* 23 */     if (paramWorld.getType(paramBlockPosition.down()).getBlock() != Blocks.STONE) {
/* 24 */       return false;
/*    */     }
/*    */     
/* 27 */     if ((paramWorld.getType(paramBlockPosition).getBlock().getMaterial() != Material.AIR) && (paramWorld.getType(paramBlockPosition).getBlock() != Blocks.STONE)) {
/* 28 */       return false;
/*    */     }
/*    */     
/* 31 */     int i = 0;
/* 32 */     if (paramWorld.getType(paramBlockPosition.west()).getBlock() == Blocks.STONE) {
/* 33 */       i++;
/*    */     }
/* 35 */     if (paramWorld.getType(paramBlockPosition.east()).getBlock() == Blocks.STONE) {
/* 36 */       i++;
/*    */     }
/* 38 */     if (paramWorld.getType(paramBlockPosition.north()).getBlock() == Blocks.STONE) {
/* 39 */       i++;
/*    */     }
/* 41 */     if (paramWorld.getType(paramBlockPosition.south()).getBlock() == Blocks.STONE) {
/* 42 */       i++;
/*    */     }
/*    */     
/* 45 */     int j = 0;
/* 46 */     if (paramWorld.isEmpty(paramBlockPosition.west())) {
/* 47 */       j++;
/*    */     }
/* 49 */     if (paramWorld.isEmpty(paramBlockPosition.east())) {
/* 50 */       j++;
/*    */     }
/* 52 */     if (paramWorld.isEmpty(paramBlockPosition.north())) {
/* 53 */       j++;
/*    */     }
/* 55 */     if (paramWorld.isEmpty(paramBlockPosition.south())) {
/* 56 */       j++;
/*    */     }
/*    */     
/* 59 */     if ((i == 3) && (j == 1)) {
/* 60 */       paramWorld.setTypeAndData(paramBlockPosition, this.a.getBlockData(), 2);
/* 61 */       paramWorld.a(this.a, paramBlockPosition, paramRandom);
/*    */     }
/*    */     
/* 64 */     return true;
/*    */   }
/*    */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_8_R3\WorldGenLiquids.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */
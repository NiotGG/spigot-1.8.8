/*    */ package net.minecraft.server.v1_8_R3;
/*    */ 
/*    */ import java.util.Random;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class WorldGenHellLava
/*    */   extends WorldGenerator
/*    */ {
/*    */   private final Block a;
/*    */   private final boolean b;
/*    */   
/*    */   public WorldGenHellLava(Block paramBlock, boolean paramBoolean)
/*    */   {
/* 16 */     this.a = paramBlock;
/* 17 */     this.b = paramBoolean;
/*    */   }
/*    */   
/*    */   public boolean generate(World paramWorld, Random paramRandom, BlockPosition paramBlockPosition)
/*    */   {
/* 22 */     if (paramWorld.getType(paramBlockPosition.up()).getBlock() != Blocks.NETHERRACK) {
/* 23 */       return false;
/*    */     }
/* 25 */     if ((paramWorld.getType(paramBlockPosition).getBlock().getMaterial() != Material.AIR) && (paramWorld.getType(paramBlockPosition).getBlock() != Blocks.NETHERRACK)) {
/* 26 */       return false;
/*    */     }
/*    */     
/* 29 */     int i = 0;
/* 30 */     if (paramWorld.getType(paramBlockPosition.west()).getBlock() == Blocks.NETHERRACK) {
/* 31 */       i++;
/*    */     }
/* 33 */     if (paramWorld.getType(paramBlockPosition.east()).getBlock() == Blocks.NETHERRACK) {
/* 34 */       i++;
/*    */     }
/* 36 */     if (paramWorld.getType(paramBlockPosition.north()).getBlock() == Blocks.NETHERRACK) {
/* 37 */       i++;
/*    */     }
/* 39 */     if (paramWorld.getType(paramBlockPosition.south()).getBlock() == Blocks.NETHERRACK) {
/* 40 */       i++;
/*    */     }
/* 42 */     if (paramWorld.getType(paramBlockPosition.down()).getBlock() == Blocks.NETHERRACK) {
/* 43 */       i++;
/*    */     }
/*    */     
/* 46 */     int j = 0;
/* 47 */     if (paramWorld.isEmpty(paramBlockPosition.west())) {
/* 48 */       j++;
/*    */     }
/* 50 */     if (paramWorld.isEmpty(paramBlockPosition.east())) {
/* 51 */       j++;
/*    */     }
/* 53 */     if (paramWorld.isEmpty(paramBlockPosition.north())) {
/* 54 */       j++;
/*    */     }
/* 56 */     if (paramWorld.isEmpty(paramBlockPosition.south())) {
/* 57 */       j++;
/*    */     }
/* 59 */     if (paramWorld.isEmpty(paramBlockPosition.down())) {
/* 60 */       j++;
/*    */     }
/*    */     
/* 63 */     if (((!this.b) && (i == 4) && (j == 1)) || (i == 5)) {
/* 64 */       paramWorld.setTypeAndData(paramBlockPosition, this.a.getBlockData(), 2);
/* 65 */       paramWorld.a(this.a, paramBlockPosition, paramRandom);
/*    */     }
/*    */     
/* 68 */     return true;
/*    */   }
/*    */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_8_R3\WorldGenHellLava.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */
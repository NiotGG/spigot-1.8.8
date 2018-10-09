/*    */ package net.minecraft.server.v1_8_R3;
/*    */ 
/*    */ import java.util.Random;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public abstract class WorldGenTreeAbstract
/*    */   extends WorldGenerator
/*    */ {
/*    */   public WorldGenTreeAbstract(boolean paramBoolean)
/*    */   {
/* 13 */     super(paramBoolean);
/*    */   }
/*    */   
/*    */   protected boolean a(Block paramBlock) {
/* 17 */     Material localMaterial = paramBlock.getMaterial();
/* 18 */     return (localMaterial == Material.AIR) || (localMaterial == Material.LEAVES) || (paramBlock == Blocks.GRASS) || (paramBlock == Blocks.DIRT) || (paramBlock == Blocks.LOG) || (paramBlock == Blocks.LOG2) || (paramBlock == Blocks.SAPLING) || (paramBlock == Blocks.VINE);
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   public void a(World paramWorld, Random paramRandom, BlockPosition paramBlockPosition) {}
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   protected void a(World paramWorld, BlockPosition paramBlockPosition)
/*    */   {
/* 33 */     if (paramWorld.getType(paramBlockPosition).getBlock() != Blocks.DIRT) {
/* 34 */       a(paramWorld, paramBlockPosition, Blocks.DIRT.getBlockData());
/*    */     }
/*    */   }
/*    */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_8_R3\WorldGenTreeAbstract.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */
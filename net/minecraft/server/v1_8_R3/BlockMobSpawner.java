/*    */ package net.minecraft.server.v1_8_R3;
/*    */ 
/*    */ import java.util.Random;
/*    */ 
/*    */ public class BlockMobSpawner extends BlockContainer
/*    */ {
/*    */   protected BlockMobSpawner() {
/*  8 */     super(Material.STONE);
/*    */   }
/*    */   
/*    */   public TileEntity a(World world, int i) {
/* 12 */     return new TileEntityMobSpawner();
/*    */   }
/*    */   
/*    */   public Item getDropType(IBlockData iblockdata, Random random, int i) {
/* 16 */     return null;
/*    */   }
/*    */   
/*    */   public int a(Random random) {
/* 20 */     return 0;
/*    */   }
/*    */   
/*    */   public void dropNaturally(World world, BlockPosition blockposition, IBlockData iblockdata, float f, int i) {
/* 24 */     super.dropNaturally(world, blockposition, iblockdata, f, i);
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   public int getExpDrop(World world, IBlockData iblockdata, int enchantmentLevel)
/*    */   {
/* 34 */     int j = 15 + world.random.nextInt(15) + world.random.nextInt(15);
/*    */     
/* 36 */     return j;
/*    */   }
/*    */   
/*    */   public boolean c()
/*    */   {
/* 41 */     return false;
/*    */   }
/*    */   
/*    */   public int b() {
/* 45 */     return 3;
/*    */   }
/*    */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_8_R3\BlockMobSpawner.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */
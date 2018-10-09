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
/*    */ 
/*    */ public class BlockSnowBlock
/*    */   extends Block
/*    */ {
/*    */   protected BlockSnowBlock()
/*    */   {
/* 17 */     super(Material.SNOW_BLOCK);
/* 18 */     a(true);
/* 19 */     a(CreativeModeTab.b);
/*    */   }
/*    */   
/*    */ 
/*    */   public Item getDropType(IBlockData paramIBlockData, Random paramRandom, int paramInt)
/*    */   {
/* 25 */     return Items.SNOWBALL;
/*    */   }
/*    */   
/*    */   public int a(Random paramRandom)
/*    */   {
/* 30 */     return 4;
/*    */   }
/*    */   
/*    */   public void b(World paramWorld, BlockPosition paramBlockPosition, IBlockData paramIBlockData, Random paramRandom)
/*    */   {
/* 35 */     if (paramWorld.b(EnumSkyBlock.BLOCK, paramBlockPosition) > 11) {
/* 36 */       b(paramWorld, paramBlockPosition, paramWorld.getType(paramBlockPosition), 0);
/* 37 */       paramWorld.setAir(paramBlockPosition);
/*    */     }
/*    */   }
/*    */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_8_R3\BlockSnowBlock.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */
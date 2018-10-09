/*    */ package net.minecraft.server.v1_8_R3;
/*    */ 
/*    */ import java.util.Random;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class BlockClay
/*    */   extends Block
/*    */ {
/*    */   public BlockClay()
/*    */   {
/* 14 */     super(Material.CLAY);
/* 15 */     a(CreativeModeTab.b);
/*    */   }
/*    */   
/*    */ 
/*    */   public Item getDropType(IBlockData paramIBlockData, Random paramRandom, int paramInt)
/*    */   {
/* 21 */     return Items.CLAY_BALL;
/*    */   }
/*    */   
/*    */   public int a(Random paramRandom)
/*    */   {
/* 26 */     return 4;
/*    */   }
/*    */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_8_R3\BlockClay.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */
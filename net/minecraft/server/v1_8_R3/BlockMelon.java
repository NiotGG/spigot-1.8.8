/*    */ package net.minecraft.server.v1_8_R3;
/*    */ 
/*    */ import java.util.Random;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class BlockMelon
/*    */   extends Block
/*    */ {
/*    */   protected BlockMelon()
/*    */   {
/* 15 */     super(Material.PUMPKIN, MaterialMapColor.u);
/* 16 */     a(CreativeModeTab.b);
/*    */   }
/*    */   
/*    */ 
/*    */   public Item getDropType(IBlockData paramIBlockData, Random paramRandom, int paramInt)
/*    */   {
/* 22 */     return Items.MELON;
/*    */   }
/*    */   
/*    */   public int a(Random paramRandom)
/*    */   {
/* 27 */     return 3 + paramRandom.nextInt(5);
/*    */   }
/*    */   
/*    */   public int getDropCount(int paramInt, Random paramRandom)
/*    */   {
/* 32 */     return Math.min(9, a(paramRandom) + paramRandom.nextInt(1 + paramInt));
/*    */   }
/*    */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_8_R3\BlockMelon.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */
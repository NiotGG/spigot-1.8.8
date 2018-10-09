/*    */ package net.minecraft.server.v1_8_R3;
/*    */ 
/*    */ import java.util.Random;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class BlockObsidian
/*    */   extends Block
/*    */ {
/*    */   public BlockObsidian()
/*    */   {
/* 14 */     super(Material.STONE);
/* 15 */     a(CreativeModeTab.b);
/*    */   }
/*    */   
/*    */ 
/*    */   public Item getDropType(IBlockData paramIBlockData, Random paramRandom, int paramInt)
/*    */   {
/* 21 */     return Item.getItemOf(Blocks.OBSIDIAN);
/*    */   }
/*    */   
/*    */   public MaterialMapColor g(IBlockData paramIBlockData)
/*    */   {
/* 26 */     return MaterialMapColor.E;
/*    */   }
/*    */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_8_R3\BlockObsidian.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */
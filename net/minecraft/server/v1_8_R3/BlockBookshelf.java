/*    */ package net.minecraft.server.v1_8_R3;
/*    */ 
/*    */ import java.util.Random;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class BlockBookshelf
/*    */   extends Block
/*    */ {
/*    */   public BlockBookshelf()
/*    */   {
/* 14 */     super(Material.WOOD);
/* 15 */     a(CreativeModeTab.b);
/*    */   }
/*    */   
/*    */   public int a(Random paramRandom)
/*    */   {
/* 20 */     return 3;
/*    */   }
/*    */   
/*    */ 
/*    */   public Item getDropType(IBlockData paramIBlockData, Random paramRandom, int paramInt)
/*    */   {
/* 26 */     return Items.BOOK;
/*    */   }
/*    */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_8_R3\BlockBookshelf.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */
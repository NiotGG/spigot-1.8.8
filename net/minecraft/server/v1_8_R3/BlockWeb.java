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
/*    */ 
/*    */ 
/*    */ public class BlockWeb
/*    */   extends Block
/*    */ {
/*    */   public BlockWeb()
/*    */   {
/* 19 */     super(Material.WEB);
/* 20 */     a(CreativeModeTab.c);
/*    */   }
/*    */   
/*    */   public void a(World paramWorld, BlockPosition paramBlockPosition, IBlockData paramIBlockData, Entity paramEntity)
/*    */   {
/* 25 */     paramEntity.aA();
/*    */   }
/*    */   
/*    */   public boolean c()
/*    */   {
/* 30 */     return false;
/*    */   }
/*    */   
/*    */ 
/*    */   public AxisAlignedBB a(World paramWorld, BlockPosition paramBlockPosition, IBlockData paramIBlockData)
/*    */   {
/* 36 */     return null;
/*    */   }
/*    */   
/*    */   public boolean d()
/*    */   {
/* 41 */     return false;
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */   public Item getDropType(IBlockData paramIBlockData, Random paramRandom, int paramInt)
/*    */   {
/* 48 */     return Items.STRING;
/*    */   }
/*    */   
/*    */   protected boolean I()
/*    */   {
/* 53 */     return true;
/*    */   }
/*    */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_8_R3\BlockWeb.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */
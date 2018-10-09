/*    */ package net.minecraft.server.v1_8_R3;
/*    */ 
/*    */ import java.util.Random;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class BlockSeaLantern
/*    */   extends Block
/*    */ {
/*    */   public BlockSeaLantern(Material paramMaterial)
/*    */   {
/* 15 */     super(paramMaterial);
/* 16 */     a(CreativeModeTab.b);
/*    */   }
/*    */   
/*    */   public int a(Random paramRandom)
/*    */   {
/* 21 */     return 2 + paramRandom.nextInt(2);
/*    */   }
/*    */   
/*    */   public int getDropCount(int paramInt, Random paramRandom)
/*    */   {
/* 26 */     return MathHelper.clamp(a(paramRandom) + paramRandom.nextInt(paramInt + 1), 1, 5);
/*    */   }
/*    */   
/*    */   public Item getDropType(IBlockData paramIBlockData, Random paramRandom, int paramInt)
/*    */   {
/* 31 */     return Items.PRISMARINE_CRYSTALS;
/*    */   }
/*    */   
/*    */   public MaterialMapColor g(IBlockData paramIBlockData)
/*    */   {
/* 36 */     return MaterialMapColor.p;
/*    */   }
/*    */   
/*    */   protected boolean I()
/*    */   {
/* 41 */     return true;
/*    */   }
/*    */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_8_R3\BlockSeaLantern.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */
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
/*    */ public class BlockLightStone
/*    */   extends Block
/*    */ {
/*    */   public BlockLightStone(Material paramMaterial)
/*    */   {
/* 16 */     super(paramMaterial);
/* 17 */     a(CreativeModeTab.b);
/*    */   }
/*    */   
/*    */   public int getDropCount(int paramInt, Random paramRandom)
/*    */   {
/* 22 */     return MathHelper.clamp(a(paramRandom) + paramRandom.nextInt(paramInt + 1), 1, 4);
/*    */   }
/*    */   
/*    */   public int a(Random paramRandom)
/*    */   {
/* 27 */     return 2 + paramRandom.nextInt(3);
/*    */   }
/*    */   
/*    */ 
/*    */   public Item getDropType(IBlockData paramIBlockData, Random paramRandom, int paramInt)
/*    */   {
/* 33 */     return Items.GLOWSTONE_DUST;
/*    */   }
/*    */   
/*    */   public MaterialMapColor g(IBlockData paramIBlockData)
/*    */   {
/* 38 */     return MaterialMapColor.d;
/*    */   }
/*    */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_8_R3\BlockLightStone.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */
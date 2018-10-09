/*    */ package net.minecraft.server.v1_8_R3;
/*    */ 
/*    */ import java.util.Random;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public abstract class WorldGenerator
/*    */ {
/*    */   private final boolean a;
/*    */   
/*    */   public WorldGenerator()
/*    */   {
/* 14 */     this(false);
/*    */   }
/*    */   
/*    */   public WorldGenerator(boolean paramBoolean) {
/* 18 */     this.a = paramBoolean;
/*    */   }
/*    */   
/*    */ 
/*    */   public abstract boolean generate(World paramWorld, Random paramRandom, BlockPosition paramBlockPosition);
/*    */   
/*    */   public void e() {}
/*    */   
/*    */   protected void a(World paramWorld, BlockPosition paramBlockPosition, IBlockData paramIBlockData)
/*    */   {
/* 28 */     if (this.a) {
/* 29 */       paramWorld.setTypeAndData(paramBlockPosition, paramIBlockData, 3);
/*    */     } else {
/* 31 */       paramWorld.setTypeAndData(paramBlockPosition, paramIBlockData, 2);
/*    */     }
/*    */   }
/*    */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_8_R3\WorldGenerator.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */
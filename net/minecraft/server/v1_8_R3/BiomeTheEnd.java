/*    */ package net.minecraft.server.v1_8_R3;
/*    */ 
/*    */ import java.util.List;
/*    */ 
/*    */ public class BiomeTheEnd extends BiomeBase
/*    */ {
/*    */   public BiomeTheEnd(int paramInt) {
/*  8 */     super(paramInt);
/*    */     
/* 10 */     this.at.clear();
/* 11 */     this.au.clear();
/* 12 */     this.av.clear();
/* 13 */     this.aw.clear();
/*    */     
/* 15 */     this.at.add(new BiomeBase.BiomeMeta(EntityEnderman.class, 10, 4, 4));
/* 16 */     this.ak = Blocks.DIRT.getBlockData();
/* 17 */     this.al = Blocks.DIRT.getBlockData();
/*    */     
/* 19 */     this.as = new BiomeTheEndDecorator();
/*    */   }
/*    */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_8_R3\BiomeTheEnd.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */
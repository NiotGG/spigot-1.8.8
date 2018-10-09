/*    */ package net.minecraft.server.v1_8_R3;
/*    */ 
/*    */ import java.util.List;
/*    */ 
/*    */ public class BiomeMushrooms extends BiomeBase
/*    */ {
/*    */   public BiomeMushrooms(int paramInt) {
/*  8 */     super(paramInt);
/*    */     
/* 10 */     this.as.A = -100;
/* 11 */     this.as.B = -100;
/* 12 */     this.as.C = -100;
/*    */     
/* 14 */     this.as.E = 1;
/* 15 */     this.as.K = 1;
/*    */     
/* 17 */     this.ak = Blocks.MYCELIUM.getBlockData();
/*    */     
/* 19 */     this.at.clear();
/* 20 */     this.au.clear();
/* 21 */     this.av.clear();
/*    */     
/* 23 */     this.au.add(new BiomeBase.BiomeMeta(EntityMushroomCow.class, 8, 4, 8));
/*    */   }
/*    */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_8_R3\BiomeMushrooms.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */
/*    */ package net.minecraft.server.v1_8_R3;
/*    */ 
/*    */ import java.util.List;
/*    */ 
/*    */ public class BiomeHell extends BiomeBase
/*    */ {
/*    */   public BiomeHell(int paramInt)
/*    */   {
/*  9 */     super(paramInt);
/*    */     
/* 11 */     this.at.clear();
/* 12 */     this.au.clear();
/* 13 */     this.av.clear();
/* 14 */     this.aw.clear();
/*    */     
/* 16 */     this.at.add(new BiomeBase.BiomeMeta(EntityGhast.class, 50, 4, 4));
/* 17 */     this.at.add(new BiomeBase.BiomeMeta(EntityPigZombie.class, 100, 4, 4));
/* 18 */     this.at.add(new BiomeBase.BiomeMeta(EntityMagmaCube.class, 1, 4, 4));
/*    */   }
/*    */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_8_R3\BiomeHell.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */
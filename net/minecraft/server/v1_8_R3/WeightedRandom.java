/*    */ package net.minecraft.server.v1_8_R3;
/*    */ 
/*    */ import java.util.Collection;
/*    */ 
/*    */ public class WeightedRandom
/*    */ {
/*    */   public static int a(Collection<? extends WeightedRandomChoice> paramCollection) {
/*  8 */     int i = 0;
/*  9 */     for (WeightedRandomChoice localWeightedRandomChoice : paramCollection) {
/* 10 */       i += localWeightedRandomChoice.a;
/*    */     }
/* 12 */     return i;
/*    */   }
/*    */   
/*    */   public static <T extends WeightedRandomChoice> T a(java.util.Random paramRandom, Collection<T> paramCollection, int paramInt) {
/* 16 */     if (paramInt <= 0) {
/* 17 */       throw new IllegalArgumentException();
/*    */     }
/*    */     
/* 20 */     int i = paramRandom.nextInt(paramInt);
/* 21 */     return a(paramCollection, i);
/*    */   }
/*    */   
/*    */   public static <T extends WeightedRandomChoice> T a(Collection<T> paramCollection, int paramInt) {
/* 25 */     for (WeightedRandomChoice localWeightedRandomChoice : paramCollection) {
/* 26 */       paramInt -= localWeightedRandomChoice.a;
/* 27 */       if (paramInt < 0) {
/* 28 */         return localWeightedRandomChoice;
/*    */       }
/*    */     }
/* 31 */     return null;
/*    */   }
/*    */   
/*    */   public static <T extends WeightedRandomChoice> T a(java.util.Random paramRandom, Collection<T> paramCollection) {
/* 35 */     return a(paramRandom, paramCollection, a(paramCollection));
/*    */   }
/*    */   
/*    */   public static class WeightedRandomChoice {
/*    */     protected int a;
/*    */     
/*    */     public WeightedRandomChoice(int paramInt) {
/* 42 */       this.a = paramInt;
/*    */     }
/*    */   }
/*    */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_8_R3\WeightedRandom.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */
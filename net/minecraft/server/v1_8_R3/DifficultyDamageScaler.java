/*    */ package net.minecraft.server.v1_8_R3;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class DifficultyDamageScaler
/*    */ {
/*    */   private final EnumDifficulty a;
/*    */   
/*    */ 
/*    */ 
/*    */   private final float b;
/*    */   
/*    */ 
/*    */ 
/*    */   public DifficultyDamageScaler(EnumDifficulty paramEnumDifficulty, long paramLong1, long paramLong2, float paramFloat)
/*    */   {
/* 18 */     this.a = paramEnumDifficulty;
/* 19 */     this.b = a(paramEnumDifficulty, paramLong1, paramLong2, paramFloat);
/*    */   }
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
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   public float c()
/*    */   {
/* 40 */     if (this.b < 2.0F) {
/* 41 */       return 0.0F;
/*    */     }
/* 43 */     if (this.b > 4.0F) {
/* 44 */       return 1.0F;
/*    */     }
/* 46 */     return (this.b - 2.0F) / 2.0F;
/*    */   }
/*    */   
/*    */   private float a(EnumDifficulty paramEnumDifficulty, long paramLong1, long paramLong2, float paramFloat) {
/* 50 */     if (paramEnumDifficulty == EnumDifficulty.PEACEFUL) {
/* 51 */       return 0.0F;
/*    */     }
/*    */     
/* 54 */     int i = paramEnumDifficulty == EnumDifficulty.HARD ? 1 : 0;
/* 55 */     float f1 = 0.75F;
/*    */     
/*    */ 
/* 58 */     float f2 = MathHelper.a(((float)paramLong1 + -72000.0F) / 1440000.0F, 0.0F, 1.0F) * 0.25F;
/* 59 */     f1 += f2;
/*    */     
/* 61 */     float f3 = 0.0F;
/*    */     
/*    */ 
/* 64 */     f3 += MathHelper.a((float)paramLong2 / 3600000.0F, 0.0F, 1.0F) * (i != 0 ? 1.0F : 0.75F);
/* 65 */     f3 += MathHelper.a(paramFloat * 0.25F, 0.0F, f2);
/*    */     
/* 67 */     if (paramEnumDifficulty == EnumDifficulty.EASY) {
/* 68 */       f3 *= 0.5F;
/*    */     }
/* 70 */     f1 += f3;
/*    */     
/*    */ 
/*    */ 
/*    */ 
/* 75 */     return paramEnumDifficulty.a() * f1;
/*    */   }
/*    */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_8_R3\DifficultyDamageScaler.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */
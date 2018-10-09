/*    */ package net.minecraft.server.v1_8_R3;
/*    */ 
/*    */ import java.util.Random;
/*    */ 
/*    */ 
/*    */ public class PossibleFishingResult
/*    */   extends WeightedRandom.WeightedRandomChoice
/*    */ {
/*    */   private final ItemStack b;
/*    */   private float c;
/*    */   private boolean d;
/*    */   
/*    */   public PossibleFishingResult(ItemStack paramItemStack, int paramInt)
/*    */   {
/* 15 */     super(paramInt);
/* 16 */     this.b = paramItemStack;
/*    */   }
/*    */   
/*    */   public ItemStack a(Random paramRandom) {
/* 20 */     ItemStack localItemStack = this.b.cloneItemStack();
/*    */     
/* 22 */     if (this.c > 0.0F) {
/* 23 */       int i = (int)(this.c * this.b.j());
/* 24 */       int j = localItemStack.j() - paramRandom.nextInt(paramRandom.nextInt(i) + 1);
/* 25 */       if (j > i) {
/* 26 */         j = i;
/*    */       }
/* 28 */       if (j < 1) {
/* 29 */         j = 1;
/*    */       }
/* 31 */       localItemStack.setData(j);
/*    */     }
/*    */     
/* 34 */     if (this.d) {
/* 35 */       EnchantmentManager.a(paramRandom, localItemStack, 30);
/*    */     }
/*    */     
/* 38 */     return localItemStack;
/*    */   }
/*    */   
/*    */   public PossibleFishingResult a(float paramFloat) {
/* 42 */     this.c = paramFloat;
/* 43 */     return this;
/*    */   }
/*    */   
/*    */   public PossibleFishingResult a() {
/* 47 */     this.d = true;
/* 48 */     return this;
/*    */   }
/*    */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_8_R3\PossibleFishingResult.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */
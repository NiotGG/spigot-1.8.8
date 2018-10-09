/*    */ package net.minecraft.server.v1_8_R3;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class ItemGoldenApple
/*    */   extends ItemFood
/*    */ {
/*    */   public ItemGoldenApple(int paramInt, float paramFloat, boolean paramBoolean)
/*    */   {
/* 13 */     super(paramInt, paramFloat, paramBoolean);
/*    */     
/* 15 */     a(true);
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   public EnumItemRarity g(ItemStack paramItemStack)
/*    */   {
/* 25 */     if (paramItemStack.getData() == 0) {
/* 26 */       return EnumItemRarity.RARE;
/*    */     }
/* 28 */     return EnumItemRarity.EPIC;
/*    */   }
/*    */   
/*    */   protected void c(ItemStack paramItemStack, World paramWorld, EntityHuman paramEntityHuman)
/*    */   {
/* 33 */     if (!paramWorld.isClientSide) {
/* 34 */       paramEntityHuman.addEffect(new MobEffect(MobEffectList.ABSORBTION.id, 2400, 0));
/*    */     }
/*    */     
/* 37 */     if (paramItemStack.getData() > 0) {
/* 38 */       if (!paramWorld.isClientSide) {
/* 39 */         paramEntityHuman.addEffect(new MobEffect(MobEffectList.REGENERATION.id, 600, 4));
/* 40 */         paramEntityHuman.addEffect(new MobEffect(MobEffectList.RESISTANCE.id, 6000, 0));
/* 41 */         paramEntityHuman.addEffect(new MobEffect(MobEffectList.FIRE_RESISTANCE.id, 6000, 0));
/*    */       }
/*    */     } else {
/* 44 */       super.c(paramItemStack, paramWorld, paramEntityHuman);
/*    */     }
/*    */   }
/*    */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_8_R3\ItemGoldenApple.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */
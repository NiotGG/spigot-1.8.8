/*    */ package net.minecraft.server.v1_8_R3;
/*    */ 
/*    */ public class MobEffectAttackDamage
/*    */   extends MobEffectList
/*    */ {
/*    */   protected MobEffectAttackDamage(int paramInt1, MinecraftKey paramMinecraftKey, boolean paramBoolean, int paramInt2)
/*    */   {
/*  8 */     super(paramInt1, paramMinecraftKey, paramBoolean, paramInt2);
/*    */   }
/*    */   
/*    */   public double a(int paramInt, AttributeModifier paramAttributeModifier)
/*    */   {
/* 13 */     if (this.id == MobEffectList.WEAKNESS.id) {
/* 14 */       return -0.5F * (paramInt + 1);
/*    */     }
/* 16 */     return 1.3D * (paramInt + 1);
/*    */   }
/*    */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_8_R3\MobEffectAttackDamage.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */
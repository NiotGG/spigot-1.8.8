/*    */ package net.minecraft.server.v1_8_R3;
/*    */ 
/*    */ 
/*    */ public class MobEffectHealthBoost
/*    */   extends MobEffectList
/*    */ {
/*    */   public MobEffectHealthBoost(int paramInt1, MinecraftKey paramMinecraftKey, boolean paramBoolean, int paramInt2)
/*    */   {
/*  9 */     super(paramInt1, paramMinecraftKey, paramBoolean, paramInt2);
/*    */   }
/*    */   
/*    */   public void a(EntityLiving paramEntityLiving, AttributeMapBase paramAttributeMapBase, int paramInt)
/*    */   {
/* 14 */     super.a(paramEntityLiving, paramAttributeMapBase, paramInt);
/* 15 */     if (paramEntityLiving.getHealth() > paramEntityLiving.getMaxHealth()) {
/* 16 */       paramEntityLiving.setHealth(paramEntityLiving.getMaxHealth());
/*    */     }
/*    */   }
/*    */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_8_R3\MobEffectHealthBoost.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */
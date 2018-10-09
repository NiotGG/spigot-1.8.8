/*    */ package net.minecraft.server.v1_8_R3;
/*    */ 
/*    */ public class InstantMobEffect extends MobEffectList
/*    */ {
/*    */   public InstantMobEffect(int paramInt1, MinecraftKey paramMinecraftKey, boolean paramBoolean, int paramInt2)
/*    */   {
/*  7 */     super(paramInt1, paramMinecraftKey, paramBoolean, paramInt2);
/*    */   }
/*    */   
/*    */   public boolean isInstant()
/*    */   {
/* 12 */     return true;
/*    */   }
/*    */   
/*    */   public boolean a(int paramInt1, int paramInt2)
/*    */   {
/* 17 */     return paramInt1 >= 1;
/*    */   }
/*    */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_8_R3\InstantMobEffect.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */
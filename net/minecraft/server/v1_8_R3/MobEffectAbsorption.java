/*    */ package net.minecraft.server.v1_8_R3;
/*    */ 
/*    */ 
/*    */ public class MobEffectAbsorption
/*    */   extends MobEffectList
/*    */ {
/*    */   protected MobEffectAbsorption(int paramInt1, MinecraftKey paramMinecraftKey, boolean paramBoolean, int paramInt2)
/*    */   {
/*  9 */     super(paramInt1, paramMinecraftKey, paramBoolean, paramInt2);
/*    */   }
/*    */   
/*    */   public void a(EntityLiving paramEntityLiving, AttributeMapBase paramAttributeMapBase, int paramInt)
/*    */   {
/* 14 */     paramEntityLiving.setAbsorptionHearts(paramEntityLiving.getAbsorptionHearts() - 4 * (paramInt + 1));
/* 15 */     super.a(paramEntityLiving, paramAttributeMapBase, paramInt);
/*    */   }
/*    */   
/*    */   public void b(EntityLiving paramEntityLiving, AttributeMapBase paramAttributeMapBase, int paramInt)
/*    */   {
/* 20 */     paramEntityLiving.setAbsorptionHearts(paramEntityLiving.getAbsorptionHearts() + 4 * (paramInt + 1));
/* 21 */     super.b(paramEntityLiving, paramAttributeMapBase, paramInt);
/*    */   }
/*    */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_8_R3\MobEffectAbsorption.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */
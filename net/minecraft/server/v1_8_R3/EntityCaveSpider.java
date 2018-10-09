/*    */ package net.minecraft.server.v1_8_R3;
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
/*    */ public class EntityCaveSpider
/*    */   extends EntitySpider
/*    */ {
/*    */   public EntityCaveSpider(World paramWorld)
/*    */   {
/* 17 */     super(paramWorld);
/* 18 */     setSize(0.7F, 0.5F);
/*    */   }
/*    */   
/*    */   protected void initAttributes()
/*    */   {
/* 23 */     super.initAttributes();
/*    */     
/* 25 */     getAttributeInstance(GenericAttributes.maxHealth).setValue(12.0D);
/*    */   }
/*    */   
/*    */   public boolean r(Entity paramEntity)
/*    */   {
/* 30 */     if (super.r(paramEntity)) {
/* 31 */       if ((paramEntity instanceof EntityLiving)) {
/* 32 */         int i = 0;
/* 33 */         if (this.world.getDifficulty() == EnumDifficulty.NORMAL) {
/* 34 */           i = 7;
/* 35 */         } else if (this.world.getDifficulty() == EnumDifficulty.HARD) {
/* 36 */           i = 15;
/*    */         }
/*    */         
/* 39 */         if (i > 0) {
/* 40 */           ((EntityLiving)paramEntity).addEffect(new MobEffect(MobEffectList.POISON.id, i * 20, 0));
/*    */         }
/*    */       }
/*    */       
/* 44 */       return true;
/*    */     }
/* 46 */     return false;
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */   public GroupDataEntity prepare(DifficultyDamageScaler paramDifficultyDamageScaler, GroupDataEntity paramGroupDataEntity)
/*    */   {
/* 53 */     return paramGroupDataEntity;
/*    */   }
/*    */   
/*    */   public float getHeadHeight()
/*    */   {
/* 58 */     return 0.45F;
/*    */   }
/*    */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_8_R3\EntityCaveSpider.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */
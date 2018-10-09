/*    */ package net.minecraft.server.v1_8_R3;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class EntityDamageSource
/*    */   extends DamageSource
/*    */ {
/*    */   protected Entity q;
/*    */   
/*    */ 
/*    */ 
/* 13 */   private boolean r = false;
/*    */   
/*    */   public EntityDamageSource(String paramString, Entity paramEntity) {
/* 16 */     super(paramString);
/* 17 */     this.q = paramEntity;
/*    */   }
/*    */   
/*    */   public EntityDamageSource v() {
/* 21 */     this.r = true;
/* 22 */     return this;
/*    */   }
/*    */   
/*    */   public boolean w() {
/* 26 */     return this.r;
/*    */   }
/*    */   
/*    */   public Entity getEntity()
/*    */   {
/* 31 */     return this.q;
/*    */   }
/*    */   
/*    */   public IChatBaseComponent getLocalizedDeathMessage(EntityLiving paramEntityLiving)
/*    */   {
/* 36 */     Object localObject = (this.q instanceof EntityLiving) ? ((EntityLiving)this.q).bA() : null;
/* 37 */     String str1 = "death.attack." + this.translationIndex;
/* 38 */     String str2 = str1 + ".item";
/*    */     
/* 40 */     if ((localObject != null) && (((ItemStack)localObject).hasName()) && (LocaleI18n.c(str2))) {
/* 41 */       return new ChatMessage(str2, new Object[] { paramEntityLiving.getScoreboardDisplayName(), this.q.getScoreboardDisplayName(), ((ItemStack)localObject).C() });
/*    */     }
/* 43 */     return new ChatMessage(str1, new Object[] { paramEntityLiving.getScoreboardDisplayName(), this.q.getScoreboardDisplayName() });
/*    */   }
/*    */   
/*    */ 
/*    */   public boolean r()
/*    */   {
/* 49 */     return (this.q != null) && ((this.q instanceof EntityLiving)) && (!(this.q instanceof EntityHuman));
/*    */   }
/*    */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_8_R3\EntityDamageSource.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */
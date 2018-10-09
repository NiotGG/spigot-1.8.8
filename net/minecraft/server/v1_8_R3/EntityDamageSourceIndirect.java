/*    */ package net.minecraft.server.v1_8_R3;
/*    */ 
/*    */ public class EntityDamageSourceIndirect extends EntityDamageSource
/*    */ {
/*    */   private Entity owner;
/*    */   
/*    */   public EntityDamageSourceIndirect(String s, Entity entity, Entity entity1) {
/*  8 */     super(s, entity);
/*  9 */     this.owner = entity1;
/*    */   }
/*    */   
/*    */   public Entity i() {
/* 13 */     return this.q;
/*    */   }
/*    */   
/*    */   public Entity getEntity() {
/* 17 */     return this.owner;
/*    */   }
/*    */   
/*    */   public IChatBaseComponent getLocalizedDeathMessage(EntityLiving entityliving) {
/* 21 */     IChatBaseComponent ichatbasecomponent = this.owner == null ? this.q.getScoreboardDisplayName() : this.owner.getScoreboardDisplayName();
/* 22 */     ItemStack itemstack = (this.owner instanceof EntityLiving) ? ((EntityLiving)this.owner).bA() : null;
/* 23 */     String s = "death.attack." + this.translationIndex;
/* 24 */     String s1 = s + ".item";
/*    */     
/* 26 */     return (itemstack != null) && (itemstack.hasName()) && (LocaleI18n.c(s1)) ? new ChatMessage(s1, new Object[] { entityliving.getScoreboardDisplayName(), ichatbasecomponent, itemstack.C() }) : new ChatMessage(s, new Object[] { entityliving.getScoreboardDisplayName(), ichatbasecomponent });
/*    */   }
/*    */   
/*    */   public Entity getProximateDamageSource()
/*    */   {
/* 31 */     return super.getEntity();
/*    */   }
/*    */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_8_R3\EntityDamageSourceIndirect.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */
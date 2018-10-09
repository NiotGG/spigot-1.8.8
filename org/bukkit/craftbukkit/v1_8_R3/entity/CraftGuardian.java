/*    */ package org.bukkit.craftbukkit.v1_8_R3.entity;
/*    */ 
/*    */ import net.minecraft.server.v1_8_R3.AttributeInstance;
/*    */ import net.minecraft.server.v1_8_R3.EntityGuardian;
/*    */ import net.minecraft.server.v1_8_R3.GenericAttributes;
/*    */ import org.bukkit.craftbukkit.v1_8_R3.CraftServer;
/*    */ 
/*    */ public class CraftGuardian extends CraftMonster implements org.bukkit.entity.Guardian
/*    */ {
/*    */   public CraftGuardian(CraftServer server, EntityGuardian entity)
/*    */   {
/* 12 */     super(server, entity);
/*    */   }
/*    */   
/*    */   public String toString()
/*    */   {
/* 17 */     return "CraftGuardian";
/*    */   }
/*    */   
/*    */   public org.bukkit.entity.EntityType getType()
/*    */   {
/* 22 */     return org.bukkit.entity.EntityType.GUARDIAN;
/*    */   }
/*    */   
/*    */   public boolean isElder()
/*    */   {
/* 27 */     return ((EntityGuardian)this.entity).isElder();
/*    */   }
/*    */   
/*    */   public void setElder(boolean shouldBeElder)
/*    */   {
/* 32 */     EntityGuardian entityGuardian = (EntityGuardian)this.entity;
/*    */     
/* 34 */     if ((!isElder()) && (shouldBeElder)) {
/* 35 */       entityGuardian.setElder(true);
/* 36 */     } else if ((isElder()) && (!shouldBeElder)) {
/* 37 */       entityGuardian.setElder(false);
/*    */       
/*    */ 
/* 40 */       this.entity.setSize(0.85F, 0.85F);
/*    */       
/*    */ 
/*    */ 
/* 44 */       entityGuardian.getAttributeInstance(GenericAttributes.ATTACK_DAMAGE).setValue(6.0D);
/* 45 */       entityGuardian.getAttributeInstance(GenericAttributes.MOVEMENT_SPEED).setValue(0.5D);
/* 46 */       entityGuardian.getAttributeInstance(GenericAttributes.FOLLOW_RANGE).setValue(16.0D);
/* 47 */       entityGuardian.getAttributeInstance(GenericAttributes.maxHealth).setValue(30.0D);
/*    */       
/*    */ 
/* 50 */       entityGuardian.goalRandomStroll.setTimeBetweenMovement(80);
/*    */       
/*    */ 
/* 53 */       entityGuardian.initAttributes();
/*    */     }
/*    */   }
/*    */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\org\bukkit\craftbukkit\v1_8_R3\entity\CraftGuardian.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */
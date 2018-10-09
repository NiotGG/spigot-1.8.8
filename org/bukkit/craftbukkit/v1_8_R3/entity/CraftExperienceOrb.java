/*    */ package org.bukkit.craftbukkit.v1_8_R3.entity;
/*    */ 
/*    */ import net.minecraft.server.v1_8_R3.EntityExperienceOrb;
/*    */ import org.bukkit.craftbukkit.v1_8_R3.CraftServer;
/*    */ 
/*    */ public class CraftExperienceOrb extends CraftEntity implements org.bukkit.entity.ExperienceOrb
/*    */ {
/*    */   public CraftExperienceOrb(CraftServer server, EntityExperienceOrb entity)
/*    */   {
/* 10 */     super(server, entity);
/*    */   }
/*    */   
/*    */   public int getExperience() {
/* 14 */     return getHandle().value;
/*    */   }
/*    */   
/*    */   public void setExperience(int value) {
/* 18 */     getHandle().value = value;
/*    */   }
/*    */   
/*    */   public EntityExperienceOrb getHandle()
/*    */   {
/* 23 */     return (EntityExperienceOrb)this.entity;
/*    */   }
/*    */   
/*    */   public String toString()
/*    */   {
/* 28 */     return "CraftExperienceOrb";
/*    */   }
/*    */   
/*    */   public org.bukkit.entity.EntityType getType() {
/* 32 */     return org.bukkit.entity.EntityType.EXPERIENCE_ORB;
/*    */   }
/*    */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\org\bukkit\craftbukkit\v1_8_R3\entity\CraftExperienceOrb.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */
/*    */ package org.bukkit.craftbukkit.v1_8_R3.entity;
/*    */ 
/*    */ import net.minecraft.server.v1_8_R3.EntityCreature;
/*    */ import org.bukkit.entity.LivingEntity;
/*    */ 
/*    */ public class CraftCreature extends CraftLivingEntity implements org.bukkit.entity.Creature
/*    */ {
/*    */   public CraftCreature(org.bukkit.craftbukkit.v1_8_R3.CraftServer server, EntityCreature entity)
/*    */   {
/* 10 */     super(server, entity);
/*    */   }
/*    */   
/*    */   public void setTarget(LivingEntity target) {
/* 14 */     EntityCreature entity = getHandle();
/* 15 */     if (target == null) {
/* 16 */       entity.setGoalTarget(null, null, false);
/* 17 */     } else if ((target instanceof CraftLivingEntity)) {
/* 18 */       entity.setGoalTarget(((CraftLivingEntity)target).getHandle(), null, false);
/*    */     }
/*    */   }
/*    */   
/*    */   public CraftLivingEntity getTarget() {
/* 23 */     if (getHandle().getGoalTarget() == null) { return null;
/*    */     }
/* 25 */     return (CraftLivingEntity)getHandle().getGoalTarget().getBukkitEntity();
/*    */   }
/*    */   
/*    */   public EntityCreature getHandle()
/*    */   {
/* 30 */     return (EntityCreature)this.entity;
/*    */   }
/*    */   
/*    */   public String toString()
/*    */   {
/* 35 */     return "CraftCreature";
/*    */   }
/*    */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\org\bukkit\craftbukkit\v1_8_R3\entity\CraftCreature.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */
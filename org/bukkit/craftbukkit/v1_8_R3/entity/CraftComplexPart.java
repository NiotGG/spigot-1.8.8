/*    */ package org.bukkit.craftbukkit.v1_8_R3.entity;
/*    */ 
/*    */ import net.minecraft.server.v1_8_R3.EntityComplexPart;
/*    */ import net.minecraft.server.v1_8_R3.EntityEnderDragon;
/*    */ import org.bukkit.craftbukkit.v1_8_R3.CraftServer;
/*    */ import org.bukkit.entity.ComplexLivingEntity;
/*    */ import org.bukkit.event.entity.EntityDamageEvent;
/*    */ 
/*    */ public class CraftComplexPart extends CraftEntity implements org.bukkit.entity.ComplexEntityPart
/*    */ {
/*    */   public CraftComplexPart(CraftServer server, EntityComplexPart entity)
/*    */   {
/* 13 */     super(server, entity);
/*    */   }
/*    */   
/*    */   public ComplexLivingEntity getParent() {
/* 17 */     return (ComplexLivingEntity)((EntityEnderDragon)getHandle().owner).getBukkitEntity();
/*    */   }
/*    */   
/*    */   public void setLastDamageCause(EntityDamageEvent cause)
/*    */   {
/* 22 */     getParent().setLastDamageCause(cause);
/*    */   }
/*    */   
/*    */   public EntityDamageEvent getLastDamageCause()
/*    */   {
/* 27 */     return getParent().getLastDamageCause();
/*    */   }
/*    */   
/*    */   public EntityComplexPart getHandle()
/*    */   {
/* 32 */     return (EntityComplexPart)this.entity;
/*    */   }
/*    */   
/*    */   public String toString()
/*    */   {
/* 37 */     return "CraftComplexPart";
/*    */   }
/*    */   
/*    */   public org.bukkit.entity.EntityType getType() {
/* 41 */     return org.bukkit.entity.EntityType.COMPLEX_PART;
/*    */   }
/*    */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\org\bukkit\craftbukkit\v1_8_R3\entity\CraftComplexPart.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */
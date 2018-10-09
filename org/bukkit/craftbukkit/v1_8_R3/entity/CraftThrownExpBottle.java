/*    */ package org.bukkit.craftbukkit.v1_8_R3.entity;
/*    */ 
/*    */ import net.minecraft.server.v1_8_R3.EntityThrownExpBottle;
/*    */ import org.bukkit.craftbukkit.v1_8_R3.CraftServer;
/*    */ 
/*    */ public class CraftThrownExpBottle extends CraftProjectile implements org.bukkit.entity.ThrownExpBottle
/*    */ {
/*    */   public CraftThrownExpBottle(CraftServer server, EntityThrownExpBottle entity)
/*    */   {
/* 10 */     super(server, entity);
/*    */   }
/*    */   
/*    */   public EntityThrownExpBottle getHandle()
/*    */   {
/* 15 */     return (EntityThrownExpBottle)this.entity;
/*    */   }
/*    */   
/*    */   public String toString()
/*    */   {
/* 20 */     return "EntityThrownExpBottle";
/*    */   }
/*    */   
/*    */   public org.bukkit.entity.EntityType getType() {
/* 24 */     return org.bukkit.entity.EntityType.THROWN_EXP_BOTTLE;
/*    */   }
/*    */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\org\bukkit\craftbukkit\v1_8_R3\entity\CraftThrownExpBottle.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */
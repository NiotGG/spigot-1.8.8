/*    */ package org.bukkit.craftbukkit.v1_8_R3.entity;
/*    */ 
/*    */ import net.minecraft.server.v1_8_R3.EntityBoat;
/*    */ import org.bukkit.craftbukkit.v1_8_R3.CraftServer;
/*    */ import org.bukkit.entity.EntityType;
/*    */ 
/*    */ public class CraftBoat extends CraftVehicle implements org.bukkit.entity.Boat
/*    */ {
/*    */   public CraftBoat(CraftServer server, EntityBoat entity)
/*    */   {
/* 11 */     super(server, entity);
/*    */   }
/*    */   
/*    */   public double getMaxSpeed() {
/* 15 */     return getHandle().maxSpeed;
/*    */   }
/*    */   
/*    */   public void setMaxSpeed(double speed) {
/* 19 */     if (speed >= 0.0D) {
/* 20 */       getHandle().maxSpeed = speed;
/*    */     }
/*    */   }
/*    */   
/*    */   public double getOccupiedDeceleration() {
/* 25 */     return getHandle().occupiedDeceleration;
/*    */   }
/*    */   
/*    */   public void setOccupiedDeceleration(double speed) {
/* 29 */     if (speed >= 0.0D) {
/* 30 */       getHandle().occupiedDeceleration = speed;
/*    */     }
/*    */   }
/*    */   
/*    */   public double getUnoccupiedDeceleration() {
/* 35 */     return getHandle().unoccupiedDeceleration;
/*    */   }
/*    */   
/*    */   public void setUnoccupiedDeceleration(double speed) {
/* 39 */     getHandle().unoccupiedDeceleration = speed;
/*    */   }
/*    */   
/*    */   public boolean getWorkOnLand() {
/* 43 */     return getHandle().landBoats;
/*    */   }
/*    */   
/*    */   public void setWorkOnLand(boolean workOnLand) {
/* 47 */     getHandle().landBoats = workOnLand;
/*    */   }
/*    */   
/*    */   public EntityBoat getHandle()
/*    */   {
/* 52 */     return (EntityBoat)this.entity;
/*    */   }
/*    */   
/*    */   public String toString()
/*    */   {
/* 57 */     return "CraftBoat";
/*    */   }
/*    */   
/*    */   public EntityType getType() {
/* 61 */     return EntityType.BOAT;
/*    */   }
/*    */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\org\bukkit\craftbukkit\v1_8_R3\entity\CraftBoat.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */
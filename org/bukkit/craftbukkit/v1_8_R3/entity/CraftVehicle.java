/*    */ package org.bukkit.craftbukkit.v1_8_R3.entity;
/*    */ 
/*    */ import org.bukkit.craftbukkit.v1_8_R3.CraftServer;
/*    */ 
/*    */ public abstract class CraftVehicle extends CraftEntity implements org.bukkit.entity.Vehicle
/*    */ {
/*    */   public CraftVehicle(CraftServer server, net.minecraft.server.v1_8_R3.Entity entity) {
/*  8 */     super(server, entity);
/*    */   }
/*    */   
/*    */   public String toString()
/*    */   {
/* 13 */     return "CraftVehicle{passenger=" + getPassenger() + '}';
/*    */   }
/*    */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\org\bukkit\craftbukkit\v1_8_R3\entity\CraftVehicle.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */
/*    */ package org.bukkit.craftbukkit.v1_8_R3.entity;
/*    */ 
/*    */ import net.minecraft.server.v1_8_R3.EntityBat;
/*    */ import org.bukkit.craftbukkit.v1_8_R3.CraftServer;
/*    */ 
/*    */ public class CraftBat extends CraftAmbient implements org.bukkit.entity.Bat
/*    */ {
/*    */   public CraftBat(CraftServer server, EntityBat entity)
/*    */   {
/* 10 */     super(server, entity);
/*    */   }
/*    */   
/*    */   public EntityBat getHandle()
/*    */   {
/* 15 */     return (EntityBat)this.entity;
/*    */   }
/*    */   
/*    */   public String toString()
/*    */   {
/* 20 */     return "CraftBat";
/*    */   }
/*    */   
/*    */   public org.bukkit.entity.EntityType getType() {
/* 24 */     return org.bukkit.entity.EntityType.BAT;
/*    */   }
/*    */   
/*    */   public boolean isAwake()
/*    */   {
/* 29 */     return !getHandle().isAsleep();
/*    */   }
/*    */   
/*    */   public void setAwake(boolean state)
/*    */   {
/* 34 */     getHandle().setAsleep(!state);
/*    */   }
/*    */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\org\bukkit\craftbukkit\v1_8_R3\entity\CraftBat.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */
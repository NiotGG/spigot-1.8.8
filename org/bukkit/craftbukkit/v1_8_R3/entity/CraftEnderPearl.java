/*    */ package org.bukkit.craftbukkit.v1_8_R3.entity;
/*    */ 
/*    */ import net.minecraft.server.v1_8_R3.EntityEnderPearl;
/*    */ import org.bukkit.craftbukkit.v1_8_R3.CraftServer;
/*    */ 
/*    */ public class CraftEnderPearl extends CraftProjectile implements org.bukkit.entity.EnderPearl
/*    */ {
/*    */   public CraftEnderPearl(CraftServer server, EntityEnderPearl entity)
/*    */   {
/* 10 */     super(server, entity);
/*    */   }
/*    */   
/*    */   public EntityEnderPearl getHandle()
/*    */   {
/* 15 */     return (EntityEnderPearl)this.entity;
/*    */   }
/*    */   
/*    */   public String toString()
/*    */   {
/* 20 */     return "CraftEnderPearl";
/*    */   }
/*    */   
/*    */   public org.bukkit.entity.EntityType getType() {
/* 24 */     return org.bukkit.entity.EntityType.ENDER_PEARL;
/*    */   }
/*    */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\org\bukkit\craftbukkit\v1_8_R3\entity\CraftEnderPearl.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */
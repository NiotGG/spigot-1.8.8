/*    */ package org.bukkit.craftbukkit.v1_8_R3.entity;
/*    */ 
/*    */ import net.minecraft.server.v1_8_R3.EntityEnderSignal;
/*    */ import org.bukkit.craftbukkit.v1_8_R3.CraftServer;
/*    */ 
/*    */ public class CraftEnderSignal extends CraftEntity implements org.bukkit.entity.EnderSignal
/*    */ {
/*    */   public CraftEnderSignal(CraftServer server, EntityEnderSignal entity)
/*    */   {
/* 10 */     super(server, entity);
/*    */   }
/*    */   
/*    */   public EntityEnderSignal getHandle()
/*    */   {
/* 15 */     return (EntityEnderSignal)this.entity;
/*    */   }
/*    */   
/*    */   public String toString()
/*    */   {
/* 20 */     return "CraftEnderSignal";
/*    */   }
/*    */   
/*    */   public org.bukkit.entity.EntityType getType() {
/* 24 */     return org.bukkit.entity.EntityType.ENDER_SIGNAL;
/*    */   }
/*    */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\org\bukkit\craftbukkit\v1_8_R3\entity\CraftEnderSignal.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */
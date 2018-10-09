/*    */ package org.bukkit.craftbukkit.v1_8_R3.entity;
/*    */ 
/*    */ import net.minecraft.server.v1_8_R3.EntityMinecartAbstract;
/*    */ import org.bukkit.craftbukkit.v1_8_R3.CraftServer;
/*    */ import org.bukkit.entity.EntityType;
/*    */ 
/*    */ public class CraftMinecartRideable extends CraftMinecart implements org.bukkit.entity.minecart.RideableMinecart
/*    */ {
/*    */   public CraftMinecartRideable(CraftServer server, EntityMinecartAbstract entity)
/*    */   {
/* 11 */     super(server, entity);
/*    */   }
/*    */   
/*    */   public String toString()
/*    */   {
/* 16 */     return "CraftMinecartRideable";
/*    */   }
/*    */   
/*    */   public EntityType getType() {
/* 20 */     return EntityType.MINECART;
/*    */   }
/*    */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\org\bukkit\craftbukkit\v1_8_R3\entity\CraftMinecartRideable.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */
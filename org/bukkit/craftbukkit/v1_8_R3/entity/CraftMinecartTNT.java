/*    */ package org.bukkit.craftbukkit.v1_8_R3.entity;
/*    */ 
/*    */ import net.minecraft.server.v1_8_R3.EntityMinecartTNT;
/*    */ import org.bukkit.craftbukkit.v1_8_R3.CraftServer;
/*    */ import org.bukkit.entity.EntityType;
/*    */ 
/*    */ final class CraftMinecartTNT extends CraftMinecart implements org.bukkit.entity.minecart.ExplosiveMinecart
/*    */ {
/*    */   CraftMinecartTNT(CraftServer server, EntityMinecartTNT entity)
/*    */   {
/* 11 */     super(server, entity);
/*    */   }
/*    */   
/*    */   public String toString()
/*    */   {
/* 16 */     return "CraftMinecartTNT";
/*    */   }
/*    */   
/*    */   public EntityType getType() {
/* 20 */     return EntityType.MINECART_TNT;
/*    */   }
/*    */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\org\bukkit\craftbukkit\v1_8_R3\entity\CraftMinecartTNT.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */
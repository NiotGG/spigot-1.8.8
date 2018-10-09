/*    */ package org.bukkit.craftbukkit.v1_8_R3.entity;
/*    */ 
/*    */ import net.minecraft.server.v1_8_R3.EntityMinecartFurnace;
/*    */ import org.bukkit.craftbukkit.v1_8_R3.CraftServer;
/*    */ import org.bukkit.entity.EntityType;
/*    */ import org.bukkit.entity.PoweredMinecart;
/*    */ 
/*    */ public class CraftMinecartFurnace extends CraftMinecart implements PoweredMinecart
/*    */ {
/*    */   public CraftMinecartFurnace(CraftServer server, EntityMinecartFurnace entity)
/*    */   {
/* 12 */     super(server, entity);
/*    */   }
/*    */   
/*    */   public String toString()
/*    */   {
/* 17 */     return "CraftMinecartFurnace";
/*    */   }
/*    */   
/*    */   public EntityType getType() {
/* 21 */     return EntityType.MINECART_FURNACE;
/*    */   }
/*    */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\org\bukkit\craftbukkit\v1_8_R3\entity\CraftMinecartFurnace.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */
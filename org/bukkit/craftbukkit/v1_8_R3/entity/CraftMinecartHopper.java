/*    */ package org.bukkit.craftbukkit.v1_8_R3.entity;
/*    */ 
/*    */ import net.minecraft.server.v1_8_R3.EntityMinecartHopper;
/*    */ import org.bukkit.craftbukkit.v1_8_R3.CraftServer;
/*    */ import org.bukkit.craftbukkit.v1_8_R3.inventory.CraftInventory;
/*    */ import org.bukkit.entity.EntityType;
/*    */ import org.bukkit.inventory.Inventory;
/*    */ 
/*    */ final class CraftMinecartHopper extends CraftMinecart implements org.bukkit.entity.minecart.HopperMinecart
/*    */ {
/*    */   private final CraftInventory inventory;
/*    */   
/*    */   CraftMinecartHopper(CraftServer server, EntityMinecartHopper entity)
/*    */   {
/* 15 */     super(server, entity);
/* 16 */     this.inventory = new CraftInventory(entity);
/*    */   }
/*    */   
/*    */   public String toString()
/*    */   {
/* 21 */     return "CraftMinecartHopper{inventory=" + this.inventory + '}';
/*    */   }
/*    */   
/*    */   public EntityType getType() {
/* 25 */     return EntityType.MINECART_HOPPER;
/*    */   }
/*    */   
/*    */   public Inventory getInventory() {
/* 29 */     return this.inventory;
/*    */   }
/*    */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\org\bukkit\craftbukkit\v1_8_R3\entity\CraftMinecartHopper.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */
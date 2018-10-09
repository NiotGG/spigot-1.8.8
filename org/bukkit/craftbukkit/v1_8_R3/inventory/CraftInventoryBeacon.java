/*    */ package org.bukkit.craftbukkit.v1_8_R3.inventory;
/*    */ 
/*    */ import org.bukkit.inventory.ItemStack;
/*    */ 
/*    */ public class CraftInventoryBeacon extends CraftInventory implements org.bukkit.inventory.BeaconInventory
/*    */ {
/*    */   public CraftInventoryBeacon(net.minecraft.server.v1_8_R3.TileEntityBeacon beacon)
/*    */   {
/*  9 */     super(beacon);
/*    */   }
/*    */   
/*    */   public void setItem(ItemStack item) {
/* 13 */     setItem(0, item);
/*    */   }
/*    */   
/*    */   public ItemStack getItem() {
/* 17 */     return getItem(0);
/*    */   }
/*    */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\org\bukkit\craftbukkit\v1_8_R3\inventory\CraftInventoryBeacon.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */
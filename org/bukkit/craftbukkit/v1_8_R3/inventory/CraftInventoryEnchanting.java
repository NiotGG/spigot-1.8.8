/*    */ package org.bukkit.craftbukkit.v1_8_R3.inventory;
/*    */ 
/*    */ import net.minecraft.server.v1_8_R3.InventorySubcontainer;
/*    */ import org.bukkit.inventory.ItemStack;
/*    */ 
/*    */ public class CraftInventoryEnchanting extends CraftInventory implements org.bukkit.inventory.EnchantingInventory
/*    */ {
/*    */   public CraftInventoryEnchanting(InventorySubcontainer inventory)
/*    */   {
/* 10 */     super(inventory);
/*    */   }
/*    */   
/*    */   public void setItem(ItemStack item)
/*    */   {
/* 15 */     setItem(0, item);
/*    */   }
/*    */   
/*    */   public ItemStack getItem()
/*    */   {
/* 20 */     return getItem(0);
/*    */   }
/*    */   
/*    */   public InventorySubcontainer getInventory()
/*    */   {
/* 25 */     return (InventorySubcontainer)this.inventory;
/*    */   }
/*    */   
/*    */   public void setSecondary(ItemStack item)
/*    */   {
/* 30 */     setItem(1, item);
/*    */   }
/*    */   
/*    */   public ItemStack getSecondary()
/*    */   {
/* 35 */     return getItem(1);
/*    */   }
/*    */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\org\bukkit\craftbukkit\v1_8_R3\inventory\CraftInventoryEnchanting.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */
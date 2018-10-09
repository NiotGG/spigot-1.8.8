/*    */ package org.bukkit.event.inventory;
/*    */ 
/*    */ import org.bukkit.inventory.InventoryView;
/*    */ import org.bukkit.inventory.ItemStack;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class InventoryCreativeEvent
/*    */   extends InventoryClickEvent
/*    */ {
/*    */   private ItemStack item;
/*    */   
/*    */   public InventoryCreativeEvent(InventoryView what, InventoryType.SlotType type, int slot, ItemStack newItem)
/*    */   {
/* 16 */     super(what, type, slot, ClickType.CREATIVE, InventoryAction.PLACE_ALL);
/* 17 */     this.item = newItem;
/*    */   }
/*    */   
/*    */   public ItemStack getCursor() {
/* 21 */     return this.item;
/*    */   }
/*    */   
/*    */   public void setCursor(ItemStack item) {
/* 25 */     this.item = item;
/*    */   }
/*    */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\org\bukkit\event\inventory\InventoryCreativeEvent.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */
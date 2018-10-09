/*    */ package org.bukkit.event.inventory;
/*    */ 
/*    */ import org.bukkit.inventory.CraftingInventory;
/*    */ import org.bukkit.inventory.InventoryView;
/*    */ import org.bukkit.inventory.Recipe;
/*    */ 
/*    */ 
/*    */ public class CraftItemEvent
/*    */   extends InventoryClickEvent
/*    */ {
/*    */   private Recipe recipe;
/*    */   
/*    */   @Deprecated
/*    */   public CraftItemEvent(Recipe recipe, InventoryView what, InventoryType.SlotType type, int slot, boolean right, boolean shift)
/*    */   {
/* 16 */     this(recipe, what, type, slot, shift ? ClickType.SHIFT_LEFT : right ? ClickType.RIGHT : shift ? ClickType.SHIFT_RIGHT : ClickType.LEFT, InventoryAction.PICKUP_ALL);
/*    */   }
/*    */   
/*    */   public CraftItemEvent(Recipe recipe, InventoryView what, InventoryType.SlotType type, int slot, ClickType click, InventoryAction action) {
/* 20 */     super(what, type, slot, click, action);
/* 21 */     this.recipe = recipe;
/*    */   }
/*    */   
/*    */   public CraftItemEvent(Recipe recipe, InventoryView what, InventoryType.SlotType type, int slot, ClickType click, InventoryAction action, int key) {
/* 25 */     super(what, type, slot, click, action, key);
/* 26 */     this.recipe = recipe;
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */   public Recipe getRecipe()
/*    */   {
/* 33 */     return this.recipe;
/*    */   }
/*    */   
/*    */   public CraftingInventory getInventory()
/*    */   {
/* 38 */     return (CraftingInventory)super.getInventory();
/*    */   }
/*    */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\org\bukkit\event\inventory\CraftItemEvent.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */
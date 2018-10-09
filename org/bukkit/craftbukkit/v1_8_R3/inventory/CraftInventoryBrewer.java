/*    */ package org.bukkit.craftbukkit.v1_8_R3.inventory;
/*    */ 
/*    */ import net.minecraft.server.v1_8_R3.IInventory;
/*    */ import org.bukkit.block.BrewingStand;
/*    */ import org.bukkit.inventory.ItemStack;
/*    */ 
/*    */ public class CraftInventoryBrewer extends CraftInventory implements org.bukkit.inventory.BrewerInventory
/*    */ {
/*    */   public CraftInventoryBrewer(IInventory inventory)
/*    */   {
/* 11 */     super(inventory);
/*    */   }
/*    */   
/*    */   public ItemStack getIngredient() {
/* 15 */     return getItem(3);
/*    */   }
/*    */   
/*    */   public void setIngredient(ItemStack ingredient) {
/* 19 */     setItem(3, ingredient);
/*    */   }
/*    */   
/*    */   public BrewingStand getHolder()
/*    */   {
/* 24 */     return (BrewingStand)this.inventory.getOwner();
/*    */   }
/*    */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\org\bukkit\craftbukkit\v1_8_R3\inventory\CraftInventoryBrewer.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */
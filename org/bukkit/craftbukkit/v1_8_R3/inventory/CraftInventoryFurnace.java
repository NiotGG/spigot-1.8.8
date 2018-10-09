/*    */ package org.bukkit.craftbukkit.v1_8_R3.inventory;
/*    */ 
/*    */ import net.minecraft.server.v1_8_R3.TileEntityFurnace;
/*    */ import org.bukkit.block.Furnace;
/*    */ import org.bukkit.inventory.ItemStack;
/*    */ 
/*    */ public class CraftInventoryFurnace extends CraftInventory implements org.bukkit.inventory.FurnaceInventory
/*    */ {
/*    */   public CraftInventoryFurnace(TileEntityFurnace inventory)
/*    */   {
/* 11 */     super(inventory);
/*    */   }
/*    */   
/*    */   public ItemStack getResult() {
/* 15 */     return getItem(2);
/*    */   }
/*    */   
/*    */   public ItemStack getFuel() {
/* 19 */     return getItem(1);
/*    */   }
/*    */   
/*    */   public ItemStack getSmelting() {
/* 23 */     return getItem(0);
/*    */   }
/*    */   
/*    */   public void setFuel(ItemStack stack) {
/* 27 */     setItem(1, stack);
/*    */   }
/*    */   
/*    */   public void setResult(ItemStack stack) {
/* 31 */     setItem(2, stack);
/*    */   }
/*    */   
/*    */   public void setSmelting(ItemStack stack) {
/* 35 */     setItem(0, stack);
/*    */   }
/*    */   
/*    */   public Furnace getHolder()
/*    */   {
/* 40 */     return (Furnace)this.inventory.getOwner();
/*    */   }
/*    */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\org\bukkit\craftbukkit\v1_8_R3\inventory\CraftInventoryFurnace.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */
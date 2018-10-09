/*    */ package org.bukkit.craftbukkit.v1_8_R3.inventory;
/*    */ 
/*    */ import net.minecraft.server.v1_8_R3.IInventory;
/*    */ import org.bukkit.inventory.ItemStack;
/*    */ 
/*    */ public class CraftInventoryHorse extends CraftInventory implements org.bukkit.inventory.HorseInventory
/*    */ {
/*    */   public CraftInventoryHorse(IInventory inventory)
/*    */   {
/* 10 */     super(inventory);
/*    */   }
/*    */   
/*    */   public ItemStack getSaddle() {
/* 14 */     return getItem(0);
/*    */   }
/*    */   
/*    */   public ItemStack getArmor() {
/* 18 */     return getItem(1);
/*    */   }
/*    */   
/*    */   public void setSaddle(ItemStack stack) {
/* 22 */     setItem(0, stack);
/*    */   }
/*    */   
/*    */   public void setArmor(ItemStack stack) {
/* 26 */     setItem(1, stack);
/*    */   }
/*    */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\org\bukkit\craftbukkit\v1_8_R3\inventory\CraftInventoryHorse.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */
/*    */ package org.bukkit.craftbukkit.v1_8_R3.inventory;
/*    */ 
/*    */ import net.minecraft.server.v1_8_R3.IInventory;
/*    */ import org.bukkit.inventory.AnvilInventory;
/*    */ 
/*    */ public class CraftInventoryAnvil extends CraftInventory implements AnvilInventory
/*    */ {
/*    */   private final IInventory resultInventory;
/*    */   
/*    */   public CraftInventoryAnvil(IInventory inventory, IInventory resultInventory)
/*    */   {
/* 12 */     super(inventory);
/* 13 */     this.resultInventory = resultInventory;
/*    */   }
/*    */   
/*    */   public IInventory getResultInventory() {
/* 17 */     return this.resultInventory;
/*    */   }
/*    */   
/*    */   public IInventory getIngredientsInventory() {
/* 21 */     return this.inventory;
/*    */   }
/*    */   
/*    */   public org.bukkit.inventory.ItemStack getItem(int slot)
/*    */   {
/* 26 */     if (slot < getIngredientsInventory().getSize()) {
/* 27 */       net.minecraft.server.v1_8_R3.ItemStack item = getIngredientsInventory().getItem(slot);
/* 28 */       return item == null ? null : CraftItemStack.asCraftMirror(item);
/*    */     }
/* 30 */     net.minecraft.server.v1_8_R3.ItemStack item = getResultInventory().getItem(slot - getIngredientsInventory().getSize());
/* 31 */     return item == null ? null : CraftItemStack.asCraftMirror(item);
/*    */   }
/*    */   
/*    */ 
/*    */   public void setItem(int index, org.bukkit.inventory.ItemStack item)
/*    */   {
/* 37 */     if (index < getIngredientsInventory().getSize()) {
/* 38 */       getIngredientsInventory().setItem(index, item == null ? null : CraftItemStack.asNMSCopy(item));
/*    */     } else {
/* 40 */       getResultInventory().setItem(index - getIngredientsInventory().getSize(), item == null ? null : CraftItemStack.asNMSCopy(item));
/*    */     }
/*    */   }
/*    */   
/*    */   public int getSize()
/*    */   {
/* 46 */     return getResultInventory().getSize() + getIngredientsInventory().getSize();
/*    */   }
/*    */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\org\bukkit\craftbukkit\v1_8_R3\inventory\CraftInventoryAnvil.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */
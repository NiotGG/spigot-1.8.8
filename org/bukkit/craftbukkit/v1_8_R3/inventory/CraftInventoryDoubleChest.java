/*    */ package org.bukkit.craftbukkit.v1_8_R3.inventory;
/*    */ 
/*    */ import net.minecraft.server.v1_8_R3.IInventory;
/*    */ import net.minecraft.server.v1_8_R3.ITileInventory;
/*    */ import net.minecraft.server.v1_8_R3.InventoryLargeChest;
/*    */ import org.bukkit.block.DoubleChest;
/*    */ import org.bukkit.inventory.Inventory;
/*    */ import org.bukkit.inventory.ItemStack;
/*    */ 
/*    */ public class CraftInventoryDoubleChest extends CraftInventory implements org.bukkit.inventory.DoubleChestInventory
/*    */ {
/*    */   private final CraftInventory left;
/*    */   private final CraftInventory right;
/*    */   
/*    */   public CraftInventoryDoubleChest(CraftInventory left, CraftInventory right)
/*    */   {
/* 17 */     super(new InventoryLargeChest("Large chest", (ITileInventory)left.getInventory(), (ITileInventory)right.getInventory()));
/* 18 */     this.left = left;
/* 19 */     this.right = right;
/*    */   }
/*    */   
/*    */   public CraftInventoryDoubleChest(InventoryLargeChest largeChest) {
/* 23 */     super(largeChest);
/* 24 */     if ((largeChest.left instanceof InventoryLargeChest)) {
/* 25 */       this.left = new CraftInventoryDoubleChest((InventoryLargeChest)largeChest.left);
/*    */     } else {
/* 27 */       this.left = new CraftInventory(largeChest.left);
/*    */     }
/* 29 */     if ((largeChest.right instanceof InventoryLargeChest)) {
/* 30 */       this.right = new CraftInventoryDoubleChest((InventoryLargeChest)largeChest.right);
/*    */     } else {
/* 32 */       this.right = new CraftInventory(largeChest.right);
/*    */     }
/*    */   }
/*    */   
/*    */   public Inventory getLeftSide() {
/* 37 */     return this.left;
/*    */   }
/*    */   
/*    */   public Inventory getRightSide() {
/* 41 */     return this.right;
/*    */   }
/*    */   
/*    */   public void setContents(ItemStack[] items)
/*    */   {
/* 46 */     if (getInventory().getContents().length < items.length) {
/* 47 */       throw new IllegalArgumentException("Invalid inventory size; expected " + getInventory().getContents().length + " or less");
/*    */     }
/* 49 */     ItemStack[] leftItems = new ItemStack[this.left.getSize()];ItemStack[] rightItems = new ItemStack[this.right.getSize()];
/* 50 */     System.arraycopy(items, 0, leftItems, 0, Math.min(this.left.getSize(), items.length));
/* 51 */     this.left.setContents(leftItems);
/* 52 */     if (items.length >= this.left.getSize()) {
/* 53 */       System.arraycopy(items, this.left.getSize(), rightItems, 0, Math.min(this.right.getSize(), items.length - this.left.getSize()));
/* 54 */       this.right.setContents(rightItems);
/*    */     }
/*    */   }
/*    */   
/*    */   public DoubleChest getHolder()
/*    */   {
/* 60 */     return new DoubleChest(this);
/*    */   }
/*    */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\org\bukkit\craftbukkit\v1_8_R3\inventory\CraftInventoryDoubleChest.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */
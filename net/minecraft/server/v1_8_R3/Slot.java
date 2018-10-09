/*    */ package net.minecraft.server.v1_8_R3;
/*    */ 
/*    */ public class Slot
/*    */ {
/*    */   public final int index;
/*    */   public final IInventory inventory;
/*    */   public int rawSlotIndex;
/*    */   public int f;
/*    */   public int g;
/*    */   
/*    */   public Slot(IInventory iinventory, int i, int j, int k) {
/* 12 */     this.inventory = iinventory;
/* 13 */     this.index = i;
/* 14 */     this.f = j;
/* 15 */     this.g = k;
/*    */   }
/*    */   
/*    */   public void a(ItemStack itemstack, ItemStack itemstack1) {
/* 19 */     if ((itemstack != null) && (itemstack1 != null) && 
/* 20 */       (itemstack.getItem() == itemstack1.getItem())) {
/* 21 */       int i = itemstack1.count - itemstack.count;
/*    */       
/* 23 */       if (i > 0) {
/* 24 */         a(itemstack, i);
/*    */       }
/*    */     }
/*    */   }
/*    */   
/*    */ 
/*    */   protected void a(ItemStack itemstack, int i) {}
/*    */   
/*    */   protected void c(ItemStack itemstack) {}
/*    */   
/*    */   public void a(EntityHuman entityhuman, ItemStack itemstack)
/*    */   {
/* 36 */     f();
/*    */   }
/*    */   
/*    */   public boolean isAllowed(ItemStack itemstack) {
/* 40 */     return true;
/*    */   }
/*    */   
/*    */   public ItemStack getItem() {
/* 44 */     return this.inventory.getItem(this.index);
/*    */   }
/*    */   
/*    */   public boolean hasItem() {
/* 48 */     if ((getItem() != null) && (getItem().count == 0)) {
/* 49 */       set(null);
/*    */     }
/* 51 */     return getItem() != null;
/*    */   }
/*    */   
/*    */   public void set(ItemStack itemstack) {
/* 55 */     this.inventory.setItem(this.index, itemstack);
/* 56 */     f();
/*    */   }
/*    */   
/*    */   public void f() {
/* 60 */     this.inventory.update();
/*    */   }
/*    */   
/*    */   public int getMaxStackSize() {
/* 64 */     return this.inventory.getMaxStackSize();
/*    */   }
/*    */   
/*    */   public int getMaxStackSize(ItemStack itemstack) {
/* 68 */     return getMaxStackSize();
/*    */   }
/*    */   
/*    */   public ItemStack a(int i) {
/* 72 */     return this.inventory.splitStack(this.index, i);
/*    */   }
/*    */   
/*    */   public boolean a(IInventory iinventory, int i) {
/* 76 */     return (iinventory == this.inventory) && (i == this.index);
/*    */   }
/*    */   
/*    */   public boolean isAllowed(EntityHuman entityhuman) {
/* 80 */     return true;
/*    */   }
/*    */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_8_R3\Slot.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */
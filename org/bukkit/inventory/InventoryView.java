/*     */ package org.bukkit.inventory;
/*     */ 
/*     */ import org.bukkit.GameMode;
/*     */ import org.bukkit.World;
/*     */ import org.bukkit.entity.HumanEntity;
/*     */ import org.bukkit.event.inventory.InventoryType;
/*     */ 
/*     */ public abstract class InventoryView
/*     */ {
/*     */   public static final int OUTSIDE = -999;
/*     */   public abstract Inventory getTopInventory();
/*     */   
/*     */   public abstract Inventory getBottomInventory();
/*     */   
/*     */   public abstract HumanEntity getPlayer();
/*     */   
/*     */   public abstract InventoryType getType();
/*     */   
/*     */   public static enum Property
/*     */   {
/*  21 */     BREW_TIME(
/*     */     
/*     */ 
/*  24 */       0, InventoryType.BREWING), 
/*  25 */     COOK_TIME(
/*     */     
/*     */ 
/*  28 */       0, InventoryType.FURNACE), 
/*  29 */     BURN_TIME(
/*     */     
/*     */ 
/*  32 */       1, InventoryType.FURNACE), 
/*  33 */     TICKS_FOR_CURRENT_FUEL(
/*     */     
/*     */ 
/*  36 */       2, InventoryType.FURNACE), 
/*  37 */     ENCHANT_BUTTON1(
/*     */     
/*     */ 
/*     */ 
/*  41 */       0, InventoryType.ENCHANTING), 
/*  42 */     ENCHANT_BUTTON2(
/*     */     
/*     */ 
/*     */ 
/*  46 */       1, InventoryType.ENCHANTING), 
/*  47 */     ENCHANT_BUTTON3(
/*     */     
/*     */ 
/*     */ 
/*  51 */       2, InventoryType.ENCHANTING);
/*     */     
/*     */     int id;
/*     */     
/*  55 */     private Property(int id, InventoryType appliesTo) { this.id = id;
/*  56 */       this.style = appliesTo;
/*     */     }
/*     */     
/*     */     public InventoryType getType() {
/*  60 */       return this.style;
/*     */     }
/*     */     
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     @Deprecated
/*     */     public int getId()
/*     */     {
/*  70 */       return this.id;
/*     */     }
/*     */     
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     InventoryType style;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public void setItem(int slot, ItemStack item)
/*     */   {
/* 113 */     if (slot != 64537) {
/* 114 */       if (slot < getTopInventory().getSize()) {
/* 115 */         getTopInventory().setItem(convertSlot(slot), item);
/*     */       } else {
/* 117 */         getBottomInventory().setItem(convertSlot(slot), item);
/*     */       }
/*     */     } else {
/* 120 */       getPlayer().getWorld().dropItemNaturally(getPlayer().getLocation(), item);
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public ItemStack getItem(int slot)
/*     */   {
/* 131 */     if (slot == 64537) {
/* 132 */       return null;
/*     */     }
/* 134 */     if (slot < getTopInventory().getSize()) {
/* 135 */       return getTopInventory().getItem(convertSlot(slot));
/*     */     }
/* 137 */     return getBottomInventory().getItem(convertSlot(slot));
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public final void setCursor(ItemStack item)
/*     */   {
/* 148 */     getPlayer().setItemOnCursor(item);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public final ItemStack getCursor()
/*     */   {
/* 158 */     return getPlayer().getItemOnCursor();
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public final int convertSlot(int rawSlot)
/*     */   {
/* 174 */     int numInTop = getTopInventory().getSize();
/* 175 */     if (rawSlot < numInTop) {
/* 176 */       return rawSlot;
/*     */     }
/* 178 */     int slot = rawSlot - numInTop;
/* 179 */     if ((getPlayer().getGameMode() == GameMode.CREATIVE) && (getType() == InventoryType.PLAYER)) {
/* 180 */       return slot;
/*     */     }
/* 182 */     if (getType() == InventoryType.CRAFTING) {
/* 183 */       if (slot < 4) return 39 - slot;
/* 184 */       slot -= 4;
/*     */     }
/* 186 */     if (slot >= 27) slot -= 27; else
/* 187 */       slot += 9;
/* 188 */     return slot;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public final void close()
/*     */   {
/* 195 */     getPlayer().closeInventory();
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public final int countSlots()
/*     */   {
/* 208 */     return getTopInventory().getSize() + getBottomInventory().getSize();
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public final boolean setProperty(Property prop, int value)
/*     */   {
/* 221 */     return getPlayer().setWindowProperty(prop, value);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public final String getTitle()
/*     */   {
/* 230 */     return getTopInventory().getTitle();
/*     */   }
/*     */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\org\bukkit\inventory\InventoryView.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */
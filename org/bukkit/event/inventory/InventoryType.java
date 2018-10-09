/*     */ package org.bukkit.event.inventory;
/*     */ 
/*     */ public enum InventoryType
/*     */ {
/*   5 */   CHEST(
/*     */   
/*     */ 
/*     */ 
/*   9 */     27, "Chest"), 
/*  10 */   DISPENSER(
/*     */   
/*     */ 
/*  13 */     9, "Dispenser"), 
/*  14 */   DROPPER(
/*     */   
/*     */ 
/*  17 */     9, "Dropper"), 
/*  18 */   FURNACE(
/*     */   
/*     */ 
/*     */ 
/*  22 */     3, "Furnace"), 
/*  23 */   WORKBENCH(
/*     */   
/*     */ 
/*  26 */     10, "Crafting"), 
/*  27 */   CRAFTING(
/*     */   
/*     */ 
/*     */ 
/*  31 */     5, "Crafting"), 
/*  32 */   ENCHANTING(
/*     */   
/*     */ 
/*     */ 
/*  36 */     2, "Enchanting"), 
/*  37 */   BREWING(
/*     */   
/*     */ 
/*  40 */     4, "Brewing"), 
/*  41 */   PLAYER(
/*     */   
/*     */ 
/*     */ 
/*  45 */     36, "Player"), 
/*  46 */   CREATIVE(
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*  51 */     9, "Creative"), 
/*  52 */   MERCHANT(
/*     */   
/*     */ 
/*  55 */     3, "Villager"), 
/*  56 */   ENDER_CHEST(
/*     */   
/*     */ 
/*  59 */     27, "Ender Chest"), 
/*  60 */   ANVIL(
/*     */   
/*     */ 
/*  63 */     3, "Repairing"), 
/*  64 */   BEACON(
/*     */   
/*     */ 
/*  67 */     1, "container.beacon"), 
/*  68 */   HOPPER(
/*     */   
/*     */ 
/*  71 */     5, "Item Hopper");
/*     */   
/*     */   private final int size;
/*     */   private final String title;
/*     */   
/*     */   private InventoryType(int defaultSize, String defaultTitle)
/*     */   {
/*  78 */     this.size = defaultSize;
/*  79 */     this.title = defaultTitle;
/*     */   }
/*     */   
/*     */   public int getDefaultSize() {
/*  83 */     return this.size;
/*     */   }
/*     */   
/*     */   public String getDefaultTitle() {
/*  87 */     return this.title;
/*     */   }
/*     */   
/*     */   public static enum SlotType {
/*  91 */     RESULT, 
/*     */     
/*     */ 
/*     */ 
/*  95 */     CRAFTING, 
/*     */     
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/* 101 */     ARMOR, 
/*     */     
/*     */ 
/*     */ 
/* 105 */     CONTAINER, 
/*     */     
/*     */ 
/*     */ 
/*     */ 
/* 110 */     QUICKBAR, 
/*     */     
/*     */ 
/*     */ 
/* 114 */     OUTSIDE, 
/*     */     
/*     */ 
/*     */ 
/* 118 */     FUEL;
/*     */   }
/*     */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\org\bukkit\event\inventory\InventoryType.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */
/*     */ package org.bukkit.craftbukkit.v1_8_R3.inventory;
/*     */ 
/*     */ import net.minecraft.server.v1_8_R3.IInventory;
/*     */ import net.minecraft.server.v1_8_R3.IRecipe;
/*     */ import net.minecraft.server.v1_8_R3.InventoryCrafting;
/*     */ import org.bukkit.inventory.CraftingInventory;
/*     */ import org.bukkit.inventory.Recipe;
/*     */ import org.bukkit.util.Java15Compat;
/*     */ 
/*     */ public class CraftInventoryCrafting extends CraftInventory implements CraftingInventory
/*     */ {
/*     */   private final IInventory resultInventory;
/*     */   
/*     */   public CraftInventoryCrafting(InventoryCrafting inventory, IInventory resultInventory)
/*     */   {
/*  16 */     super(inventory);
/*  17 */     this.resultInventory = resultInventory;
/*     */   }
/*     */   
/*     */   public IInventory getResultInventory() {
/*  21 */     return this.resultInventory;
/*     */   }
/*     */   
/*     */   public IInventory getMatrixInventory() {
/*  25 */     return this.inventory;
/*     */   }
/*     */   
/*     */   public int getSize()
/*     */   {
/*  30 */     return getResultInventory().getSize() + getMatrixInventory().getSize();
/*     */   }
/*     */   
/*     */   public void setContents(org.bukkit.inventory.ItemStack[] items)
/*     */   {
/*  35 */     int resultLen = getResultInventory().getContents().length;
/*  36 */     int len = getMatrixInventory().getContents().length + resultLen;
/*  37 */     if (len > items.length) {
/*  38 */       throw new IllegalArgumentException("Invalid inventory size; expected " + len + " or less");
/*     */     }
/*  40 */     setContents(items[0], (org.bukkit.inventory.ItemStack[])Java15Compat.Arrays_copyOfRange(items, 1, items.length));
/*     */   }
/*     */   
/*     */   public org.bukkit.inventory.ItemStack[] getContents()
/*     */   {
/*  45 */     org.bukkit.inventory.ItemStack[] items = new org.bukkit.inventory.ItemStack[getSize()];
/*  46 */     net.minecraft.server.v1_8_R3.ItemStack[] mcResultItems = getResultInventory().getContents();
/*     */     
/*  48 */     int i = 0;
/*  49 */     for (i = 0; i < mcResultItems.length; i++) {
/*  50 */       items[i] = CraftItemStack.asCraftMirror(mcResultItems[i]);
/*     */     }
/*     */     
/*  53 */     net.minecraft.server.v1_8_R3.ItemStack[] mcItems = getMatrixInventory().getContents();
/*     */     
/*  55 */     for (int j = 0; j < mcItems.length; j++) {
/*  56 */       items[(i + j)] = CraftItemStack.asCraftMirror(mcItems[j]);
/*     */     }
/*     */     
/*  59 */     return items;
/*     */   }
/*     */   
/*     */   public void setContents(org.bukkit.inventory.ItemStack result, org.bukkit.inventory.ItemStack[] contents) {
/*  63 */     setResult(result);
/*  64 */     setMatrix(contents);
/*     */   }
/*     */   
/*     */   public CraftItemStack getItem(int index)
/*     */   {
/*  69 */     if (index < getResultInventory().getSize()) {
/*  70 */       net.minecraft.server.v1_8_R3.ItemStack item = getResultInventory().getItem(index);
/*  71 */       return item == null ? null : CraftItemStack.asCraftMirror(item);
/*     */     }
/*  73 */     net.minecraft.server.v1_8_R3.ItemStack item = getMatrixInventory().getItem(index - getResultInventory().getSize());
/*  74 */     return item == null ? null : CraftItemStack.asCraftMirror(item);
/*     */   }
/*     */   
/*     */ 
/*     */   public void setItem(int index, org.bukkit.inventory.ItemStack item)
/*     */   {
/*  80 */     if (index < getResultInventory().getSize()) {
/*  81 */       getResultInventory().setItem(index, item == null ? null : CraftItemStack.asNMSCopy(item));
/*     */     } else {
/*  83 */       getMatrixInventory().setItem(index - getResultInventory().getSize(), item == null ? null : CraftItemStack.asNMSCopy(item));
/*     */     }
/*     */   }
/*     */   
/*     */   public org.bukkit.inventory.ItemStack[] getMatrix() {
/*  88 */     org.bukkit.inventory.ItemStack[] items = new org.bukkit.inventory.ItemStack[getSize()];
/*  89 */     net.minecraft.server.v1_8_R3.ItemStack[] matrix = getMatrixInventory().getContents();
/*     */     
/*  91 */     for (int i = 0; i < matrix.length; i++) {
/*  92 */       items[i] = CraftItemStack.asCraftMirror(matrix[i]);
/*     */     }
/*     */     
/*  95 */     return items;
/*     */   }
/*     */   
/*     */   public org.bukkit.inventory.ItemStack getResult() {
/*  99 */     net.minecraft.server.v1_8_R3.ItemStack item = getResultInventory().getItem(0);
/* 100 */     if (item != null) return CraftItemStack.asCraftMirror(item);
/* 101 */     return null;
/*     */   }
/*     */   
/*     */   public void setMatrix(org.bukkit.inventory.ItemStack[] contents) {
/* 105 */     if (getMatrixInventory().getContents().length > contents.length) {
/* 106 */       throw new IllegalArgumentException("Invalid inventory size; expected " + getMatrixInventory().getContents().length + " or less");
/*     */     }
/*     */     
/* 109 */     net.minecraft.server.v1_8_R3.ItemStack[] mcItems = getMatrixInventory().getContents();
/*     */     
/* 111 */     for (int i = 0; i < mcItems.length; i++) {
/* 112 */       if (i < contents.length) {
/* 113 */         org.bukkit.inventory.ItemStack item = contents[i];
/* 114 */         if ((item == null) || (item.getTypeId() <= 0)) {
/* 115 */           mcItems[i] = null;
/*     */         } else {
/* 117 */           mcItems[i] = CraftItemStack.asNMSCopy(item);
/*     */         }
/*     */       } else {
/* 120 */         mcItems[i] = null;
/*     */       }
/*     */     }
/*     */   }
/*     */   
/*     */   public void setResult(org.bukkit.inventory.ItemStack item) {
/* 126 */     net.minecraft.server.v1_8_R3.ItemStack[] contents = getResultInventory().getContents();
/* 127 */     if ((item == null) || (item.getTypeId() <= 0)) {
/* 128 */       contents[0] = null;
/*     */     } else {
/* 130 */       contents[0] = CraftItemStack.asNMSCopy(item);
/*     */     }
/*     */   }
/*     */   
/*     */   public Recipe getRecipe() {
/* 135 */     IRecipe recipe = ((InventoryCrafting)getInventory()).currentRecipe;
/* 136 */     return recipe == null ? null : recipe.toBukkitRecipe();
/*     */   }
/*     */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\org\bukkit\craftbukkit\v1_8_R3\inventory\CraftInventoryCrafting.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */
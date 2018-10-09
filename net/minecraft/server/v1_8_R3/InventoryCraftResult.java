/*     */ package net.minecraft.server.v1_8_R3;
/*     */ 
/*     */ import java.util.ArrayList;
/*     */ import org.bukkit.craftbukkit.v1_8_R3.entity.CraftHumanEntity;
/*     */ import org.bukkit.entity.HumanEntity;
/*     */ import org.bukkit.inventory.InventoryHolder;
/*     */ 
/*     */ public class InventoryCraftResult implements IInventory
/*     */ {
/*  10 */   private ItemStack[] items = new ItemStack[1];
/*     */   
/*     */ 
/*  13 */   private int maxStack = 64;
/*     */   
/*     */   public ItemStack[] getContents() {
/*  16 */     return this.items;
/*     */   }
/*     */   
/*     */   public InventoryHolder getOwner() {
/*  20 */     return null;
/*     */   }
/*     */   
/*     */   public void onOpen(CraftHumanEntity who) {}
/*     */   
/*     */   public void onClose(CraftHumanEntity who) {}
/*     */   
/*  27 */   public java.util.List<HumanEntity> getViewers() { return new ArrayList(); }
/*     */   
/*     */   public void setMaxStackSize(int size)
/*     */   {
/*  31 */     this.maxStack = size;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public int getSize()
/*     */   {
/*  38 */     return 1;
/*     */   }
/*     */   
/*     */   public ItemStack getItem(int i) {
/*  42 */     return this.items[0];
/*     */   }
/*     */   
/*     */   public String getName() {
/*  46 */     return "Result";
/*     */   }
/*     */   
/*     */   public boolean hasCustomName() {
/*  50 */     return false;
/*     */   }
/*     */   
/*     */   public IChatBaseComponent getScoreboardDisplayName() {
/*  54 */     return hasCustomName() ? new ChatComponentText(getName()) : new ChatMessage(getName(), new Object[0]);
/*     */   }
/*     */   
/*     */   public ItemStack splitStack(int i, int j) {
/*  58 */     if (this.items[0] != null) {
/*  59 */       ItemStack itemstack = this.items[0];
/*     */       
/*  61 */       this.items[0] = null;
/*  62 */       return itemstack;
/*     */     }
/*  64 */     return null;
/*     */   }
/*     */   
/*     */   public ItemStack splitWithoutUpdate(int i)
/*     */   {
/*  69 */     if (this.items[0] != null) {
/*  70 */       ItemStack itemstack = this.items[0];
/*     */       
/*  72 */       this.items[0] = null;
/*  73 */       return itemstack;
/*     */     }
/*  75 */     return null;
/*     */   }
/*     */   
/*     */   public void setItem(int i, ItemStack itemstack)
/*     */   {
/*  80 */     this.items[0] = itemstack;
/*     */   }
/*     */   
/*     */   public int getMaxStackSize() {
/*  84 */     return this.maxStack;
/*     */   }
/*     */   
/*     */   public void update() {}
/*     */   
/*     */   public boolean a(EntityHuman entityhuman) {
/*  90 */     return true;
/*     */   }
/*     */   
/*     */   public void startOpen(EntityHuman entityhuman) {}
/*     */   
/*     */   public void closeContainer(EntityHuman entityhuman) {}
/*     */   
/*     */   public boolean b(int i, ItemStack itemstack) {
/*  98 */     return true;
/*     */   }
/*     */   
/*     */   public int getProperty(int i) {
/* 102 */     return 0;
/*     */   }
/*     */   
/*     */   public void b(int i, int j) {}
/*     */   
/*     */   public int g() {
/* 108 */     return 0;
/*     */   }
/*     */   
/*     */   public void l() {
/* 112 */     for (int i = 0; i < this.items.length; i++) {
/* 113 */       this.items[i] = null;
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_8_R3\InventoryCraftResult.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */
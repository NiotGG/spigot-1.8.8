/*     */ package net.minecraft.server.v1_8_R3;
/*     */ 
/*     */ import java.util.ArrayList;
/*     */ import java.util.List;
/*     */ import org.bukkit.craftbukkit.v1_8_R3.entity.CraftHumanEntity;
/*     */ import org.bukkit.entity.HumanEntity;
/*     */ import org.bukkit.inventory.InventoryHolder;
/*     */ 
/*     */ 
/*     */ 
/*     */ public class InventoryLargeChest
/*     */   implements ITileInventory
/*     */ {
/*     */   private String a;
/*     */   public ITileInventory left;
/*     */   public ITileInventory right;
/*  17 */   public List<HumanEntity> transaction = new ArrayList();
/*     */   
/*     */   public ItemStack[] getContents() {
/*  20 */     ItemStack[] result = new ItemStack[getSize()];
/*  21 */     for (int i = 0; i < result.length; i++) {
/*  22 */       result[i] = getItem(i);
/*     */     }
/*  24 */     return result;
/*     */   }
/*     */   
/*     */   public void onOpen(CraftHumanEntity who) {
/*  28 */     this.left.onOpen(who);
/*  29 */     this.right.onOpen(who);
/*  30 */     this.transaction.add(who);
/*     */   }
/*     */   
/*     */   public void onClose(CraftHumanEntity who) {
/*  34 */     this.left.onClose(who);
/*  35 */     this.right.onClose(who);
/*  36 */     this.transaction.remove(who);
/*     */   }
/*     */   
/*     */   public List<HumanEntity> getViewers() {
/*  40 */     return this.transaction;
/*     */   }
/*     */   
/*     */   public InventoryHolder getOwner() {
/*  44 */     return null;
/*     */   }
/*     */   
/*     */   public void setMaxStackSize(int size) {
/*  48 */     this.left.setMaxStackSize(size);
/*  49 */     this.right.setMaxStackSize(size);
/*     */   }
/*     */   
/*     */   public InventoryLargeChest(String s, ITileInventory itileinventory, ITileInventory itileinventory1)
/*     */   {
/*  54 */     this.a = s;
/*  55 */     if (itileinventory == null) {
/*  56 */       itileinventory = itileinventory1;
/*     */     }
/*     */     
/*  59 */     if (itileinventory1 == null) {
/*  60 */       itileinventory1 = itileinventory;
/*     */     }
/*     */     
/*  63 */     this.left = itileinventory;
/*  64 */     this.right = itileinventory1;
/*  65 */     if (itileinventory.r_()) {
/*  66 */       itileinventory1.a(itileinventory.i());
/*  67 */     } else if (itileinventory1.r_()) {
/*  68 */       itileinventory.a(itileinventory1.i());
/*     */     }
/*     */   }
/*     */   
/*     */   public int getSize()
/*     */   {
/*  74 */     return this.left.getSize() + this.right.getSize();
/*     */   }
/*     */   
/*     */   public boolean a(IInventory iinventory) {
/*  78 */     return (this.left == iinventory) || (this.right == iinventory);
/*     */   }
/*     */   
/*     */   public String getName() {
/*  82 */     return this.right.hasCustomName() ? this.right.getName() : this.left.hasCustomName() ? this.left.getName() : this.a;
/*     */   }
/*     */   
/*     */   public boolean hasCustomName() {
/*  86 */     return (this.left.hasCustomName()) || (this.right.hasCustomName());
/*     */   }
/*     */   
/*     */   public IChatBaseComponent getScoreboardDisplayName() {
/*  90 */     return hasCustomName() ? new ChatComponentText(getName()) : new ChatMessage(getName(), new Object[0]);
/*     */   }
/*     */   
/*     */   public ItemStack getItem(int i) {
/*  94 */     return i >= this.left.getSize() ? this.right.getItem(i - this.left.getSize()) : this.left.getItem(i);
/*     */   }
/*     */   
/*     */   public ItemStack splitStack(int i, int j) {
/*  98 */     return i >= this.left.getSize() ? this.right.splitStack(i - this.left.getSize(), j) : this.left.splitStack(i, j);
/*     */   }
/*     */   
/*     */   public ItemStack splitWithoutUpdate(int i) {
/* 102 */     return i >= this.left.getSize() ? this.right.splitWithoutUpdate(i - this.left.getSize()) : this.left.splitWithoutUpdate(i);
/*     */   }
/*     */   
/*     */   public void setItem(int i, ItemStack itemstack) {
/* 106 */     if (i >= this.left.getSize()) {
/* 107 */       this.right.setItem(i - this.left.getSize(), itemstack);
/*     */     } else {
/* 109 */       this.left.setItem(i, itemstack);
/*     */     }
/*     */   }
/*     */   
/*     */   public int getMaxStackSize()
/*     */   {
/* 115 */     return Math.min(this.left.getMaxStackSize(), this.right.getMaxStackSize());
/*     */   }
/*     */   
/*     */   public void update() {
/* 119 */     this.left.update();
/* 120 */     this.right.update();
/*     */   }
/*     */   
/*     */   public boolean a(EntityHuman entityhuman) {
/* 124 */     return (this.left.a(entityhuman)) && (this.right.a(entityhuman));
/*     */   }
/*     */   
/*     */   public void startOpen(EntityHuman entityhuman) {
/* 128 */     this.left.startOpen(entityhuman);
/* 129 */     this.right.startOpen(entityhuman);
/*     */   }
/*     */   
/*     */   public void closeContainer(EntityHuman entityhuman) {
/* 133 */     this.left.closeContainer(entityhuman);
/* 134 */     this.right.closeContainer(entityhuman);
/*     */   }
/*     */   
/*     */   public boolean b(int i, ItemStack itemstack) {
/* 138 */     return true;
/*     */   }
/*     */   
/*     */   public int getProperty(int i) {
/* 142 */     return 0;
/*     */   }
/*     */   
/*     */   public void b(int i, int j) {}
/*     */   
/*     */   public int g() {
/* 148 */     return 0;
/*     */   }
/*     */   
/*     */   public boolean r_() {
/* 152 */     return (this.left.r_()) || (this.right.r_());
/*     */   }
/*     */   
/*     */   public void a(ChestLock chestlock) {
/* 156 */     this.left.a(chestlock);
/* 157 */     this.right.a(chestlock);
/*     */   }
/*     */   
/*     */   public ChestLock i() {
/* 161 */     return this.left.i();
/*     */   }
/*     */   
/*     */   public String getContainerName() {
/* 165 */     return this.left.getContainerName();
/*     */   }
/*     */   
/*     */   public Container createContainer(PlayerInventory playerinventory, EntityHuman entityhuman) {
/* 169 */     return new ContainerChest(playerinventory, this, entityhuman);
/*     */   }
/*     */   
/*     */   public void l() {
/* 173 */     this.left.l();
/* 174 */     this.right.l();
/*     */   }
/*     */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_8_R3\InventoryLargeChest.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */
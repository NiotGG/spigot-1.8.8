/*     */ package net.minecraft.server.v1_8_R3;
/*     */ 
/*     */ import java.util.ArrayList;
/*     */ import java.util.List;
/*     */ import org.bukkit.craftbukkit.v1_8_R3.entity.CraftHumanEntity;
/*     */ import org.bukkit.entity.HumanEntity;
/*     */ import org.bukkit.event.inventory.InventoryType;
/*     */ import org.bukkit.inventory.InventoryHolder;
/*     */ 
/*     */ 
/*     */ 
/*     */ public class InventoryCrafting
/*     */   implements IInventory
/*     */ {
/*     */   private final ItemStack[] items;
/*     */   private final int b;
/*     */   private final int c;
/*     */   private final Container d;
/*  19 */   public List<HumanEntity> transaction = new ArrayList();
/*     */   public IRecipe currentRecipe;
/*     */   public IInventory resultInventory;
/*     */   private EntityHuman owner;
/*  23 */   private int maxStack = 64;
/*     */   
/*     */   public ItemStack[] getContents() {
/*  26 */     return this.items;
/*     */   }
/*     */   
/*     */   public void onOpen(CraftHumanEntity who) {
/*  30 */     this.transaction.add(who);
/*     */   }
/*     */   
/*     */   public InventoryType getInvType() {
/*  34 */     return this.items.length == 4 ? InventoryType.CRAFTING : InventoryType.WORKBENCH;
/*     */   }
/*     */   
/*     */   public void onClose(CraftHumanEntity who) {
/*  38 */     this.transaction.remove(who);
/*     */   }
/*     */   
/*     */   public List<HumanEntity> getViewers() {
/*  42 */     return this.transaction;
/*     */   }
/*     */   
/*     */   public InventoryHolder getOwner() {
/*  46 */     return this.owner == null ? null : this.owner.getBukkitEntity();
/*     */   }
/*     */   
/*     */   public void setMaxStackSize(int size) {
/*  50 */     this.maxStack = size;
/*  51 */     this.resultInventory.setMaxStackSize(size);
/*     */   }
/*     */   
/*     */   public InventoryCrafting(Container container, int i, int j, EntityHuman player) {
/*  55 */     this(container, i, j);
/*  56 */     this.owner = player;
/*     */   }
/*     */   
/*     */   public InventoryCrafting(Container container, int i, int j)
/*     */   {
/*  61 */     int k = i * j;
/*     */     
/*  63 */     this.items = new ItemStack[k];
/*  64 */     this.d = container;
/*  65 */     this.b = i;
/*  66 */     this.c = j;
/*     */   }
/*     */   
/*     */   public int getSize() {
/*  70 */     return this.items.length;
/*     */   }
/*     */   
/*     */   public ItemStack getItem(int i) {
/*  74 */     return i >= getSize() ? null : this.items[i];
/*     */   }
/*     */   
/*     */   public ItemStack c(int i, int j) {
/*  78 */     return (i >= 0) && (i < this.b) && (j >= 0) && (j <= this.c) ? getItem(i + j * this.b) : null;
/*     */   }
/*     */   
/*     */   public String getName() {
/*  82 */     return "container.crafting";
/*     */   }
/*     */   
/*     */   public boolean hasCustomName() {
/*  86 */     return false;
/*     */   }
/*     */   
/*     */   public IChatBaseComponent getScoreboardDisplayName() {
/*  90 */     return hasCustomName() ? new ChatComponentText(getName()) : new ChatMessage(getName(), new Object[0]);
/*     */   }
/*     */   
/*     */   public ItemStack splitWithoutUpdate(int i) {
/*  94 */     if (this.items[i] != null) {
/*  95 */       ItemStack itemstack = this.items[i];
/*     */       
/*  97 */       this.items[i] = null;
/*  98 */       return itemstack;
/*     */     }
/* 100 */     return null;
/*     */   }
/*     */   
/*     */   public ItemStack splitStack(int i, int j)
/*     */   {
/* 105 */     if (this.items[i] != null)
/*     */     {
/*     */ 
/* 108 */       if (this.items[i].count <= j) {
/* 109 */         ItemStack itemstack = this.items[i];
/* 110 */         this.items[i] = null;
/* 111 */         this.d.a(this);
/* 112 */         return itemstack;
/*     */       }
/* 114 */       ItemStack itemstack = this.items[i].cloneAndSubtract(j);
/* 115 */       if (this.items[i].count == 0) {
/* 116 */         this.items[i] = null;
/*     */       }
/*     */       
/* 119 */       this.d.a(this);
/* 120 */       return itemstack;
/*     */     }
/*     */     
/* 123 */     return null;
/*     */   }
/*     */   
/*     */   public void setItem(int i, ItemStack itemstack)
/*     */   {
/* 128 */     this.items[i] = itemstack;
/* 129 */     this.d.a(this);
/*     */   }
/*     */   
/*     */   public int getMaxStackSize() {
/* 133 */     return 64;
/*     */   }
/*     */   
/*     */   public void update() {}
/*     */   
/*     */   public boolean a(EntityHuman entityhuman) {
/* 139 */     return true;
/*     */   }
/*     */   
/*     */   public void startOpen(EntityHuman entityhuman) {}
/*     */   
/*     */   public void closeContainer(EntityHuman entityhuman) {}
/*     */   
/*     */   public boolean b(int i, ItemStack itemstack) {
/* 147 */     return true;
/*     */   }
/*     */   
/*     */   public int getProperty(int i) {
/* 151 */     return 0;
/*     */   }
/*     */   
/*     */   public void b(int i, int j) {}
/*     */   
/*     */   public int g() {
/* 157 */     return 0;
/*     */   }
/*     */   
/*     */   public void l() {
/* 161 */     for (int i = 0; i < this.items.length; i++) {
/* 162 */       this.items[i] = null;
/*     */     }
/*     */   }
/*     */   
/*     */   public int h()
/*     */   {
/* 168 */     return this.c;
/*     */   }
/*     */   
/*     */   public int i() {
/* 172 */     return this.b;
/*     */   }
/*     */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_8_R3\InventoryCrafting.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */
/*     */ package net.minecraft.server.v1_8_R3;
/*     */ 
/*     */ import com.google.common.collect.Lists;
/*     */ import java.util.ArrayList;
/*     */ import java.util.List;
/*     */ import org.bukkit.craftbukkit.v1_8_R3.entity.CraftHumanEntity;
/*     */ import org.bukkit.entity.HumanEntity;
/*     */ import org.bukkit.inventory.InventoryHolder;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class InventorySubcontainer
/*     */   implements IInventory
/*     */ {
/*     */   private String a;
/*     */   private int b;
/*     */   public ItemStack[] items;
/*     */   private List<IInventoryListener> d;
/*     */   private boolean e;
/*  21 */   public List<HumanEntity> transaction = new ArrayList();
/*  22 */   private int maxStack = 64;
/*     */   protected InventoryHolder bukkitOwner;
/*     */   
/*     */   public ItemStack[] getContents() {
/*  26 */     return this.items;
/*     */   }
/*     */   
/*     */   public void onOpen(CraftHumanEntity who) {
/*  30 */     this.transaction.add(who);
/*     */   }
/*     */   
/*     */   public void onClose(CraftHumanEntity who) {
/*  34 */     this.transaction.remove(who);
/*     */   }
/*     */   
/*     */   public List<HumanEntity> getViewers() {
/*  38 */     return this.transaction;
/*     */   }
/*     */   
/*     */   public void setMaxStackSize(int i) {
/*  42 */     this.maxStack = i;
/*     */   }
/*     */   
/*     */   public InventoryHolder getOwner() {
/*  46 */     return this.bukkitOwner;
/*     */   }
/*     */   
/*     */   public InventorySubcontainer(String s, boolean flag, int i) {
/*  50 */     this(s, flag, i, null);
/*     */   }
/*     */   
/*     */   public InventorySubcontainer(String s, boolean flag, int i, InventoryHolder owner) {
/*  54 */     this.bukkitOwner = owner;
/*     */     
/*  56 */     this.a = s;
/*  57 */     this.e = flag;
/*  58 */     this.b = i;
/*  59 */     this.items = new ItemStack[i];
/*     */   }
/*     */   
/*     */   public void a(IInventoryListener iinventorylistener) {
/*  63 */     if (this.d == null) {
/*  64 */       this.d = Lists.newArrayList();
/*     */     }
/*     */     
/*  67 */     this.d.add(iinventorylistener);
/*     */   }
/*     */   
/*     */   public void b(IInventoryListener iinventorylistener) {
/*  71 */     this.d.remove(iinventorylistener);
/*     */   }
/*     */   
/*     */   public ItemStack getItem(int i) {
/*  75 */     return (i >= 0) && (i < this.items.length) ? this.items[i] : null;
/*     */   }
/*     */   
/*     */   public ItemStack splitStack(int i, int j) {
/*  79 */     if (this.items[i] != null)
/*     */     {
/*     */ 
/*  82 */       if (this.items[i].count <= j) {
/*  83 */         ItemStack itemstack = this.items[i];
/*  84 */         this.items[i] = null;
/*  85 */         update();
/*  86 */         return itemstack;
/*     */       }
/*  88 */       ItemStack itemstack = this.items[i].cloneAndSubtract(j);
/*  89 */       if (this.items[i].count == 0) {
/*  90 */         this.items[i] = null;
/*     */       }
/*     */       
/*  93 */       update();
/*  94 */       return itemstack;
/*     */     }
/*     */     
/*  97 */     return null;
/*     */   }
/*     */   
/*     */   public ItemStack a(ItemStack itemstack)
/*     */   {
/* 102 */     ItemStack itemstack1 = itemstack.cloneItemStack();
/*     */     
/* 104 */     for (int i = 0; i < this.b; i++) {
/* 105 */       ItemStack itemstack2 = getItem(i);
/*     */       
/* 107 */       if (itemstack2 == null) {
/* 108 */         setItem(i, itemstack1);
/* 109 */         update();
/* 110 */         return null;
/*     */       }
/*     */       
/* 113 */       if (ItemStack.c(itemstack2, itemstack1)) {
/* 114 */         int j = Math.min(getMaxStackSize(), itemstack2.getMaxStackSize());
/* 115 */         int k = Math.min(itemstack1.count, j - itemstack2.count);
/*     */         
/* 117 */         if (k > 0) {
/* 118 */           itemstack2.count += k;
/* 119 */           itemstack1.count -= k;
/* 120 */           if (itemstack1.count <= 0) {
/* 121 */             update();
/* 122 */             return null;
/*     */           }
/*     */         }
/*     */       }
/*     */     }
/*     */     
/* 128 */     if (itemstack1.count != itemstack.count) {
/* 129 */       update();
/*     */     }
/*     */     
/* 132 */     return itemstack1;
/*     */   }
/*     */   
/*     */   public ItemStack splitWithoutUpdate(int i) {
/* 136 */     if (this.items[i] != null) {
/* 137 */       ItemStack itemstack = this.items[i];
/*     */       
/* 139 */       this.items[i] = null;
/* 140 */       return itemstack;
/*     */     }
/* 142 */     return null;
/*     */   }
/*     */   
/*     */   public void setItem(int i, ItemStack itemstack)
/*     */   {
/* 147 */     this.items[i] = itemstack;
/* 148 */     if ((itemstack != null) && (itemstack.count > getMaxStackSize())) {
/* 149 */       itemstack.count = getMaxStackSize();
/*     */     }
/*     */     
/* 152 */     update();
/*     */   }
/*     */   
/*     */   public int getSize() {
/* 156 */     return this.b;
/*     */   }
/*     */   
/*     */   public String getName() {
/* 160 */     return this.a;
/*     */   }
/*     */   
/*     */   public boolean hasCustomName() {
/* 164 */     return this.e;
/*     */   }
/*     */   
/*     */   public void a(String s) {
/* 168 */     this.e = true;
/* 169 */     this.a = s;
/*     */   }
/*     */   
/*     */   public IChatBaseComponent getScoreboardDisplayName() {
/* 173 */     return hasCustomName() ? new ChatComponentText(getName()) : new ChatMessage(getName(), new Object[0]);
/*     */   }
/*     */   
/*     */   public int getMaxStackSize() {
/* 177 */     return 64;
/*     */   }
/*     */   
/*     */   public void update() {
/* 181 */     if (this.d != null) {
/* 182 */       for (int i = 0; i < this.d.size(); i++) {
/* 183 */         ((IInventoryListener)this.d.get(i)).a(this);
/*     */       }
/*     */     }
/*     */   }
/*     */   
/*     */   public boolean a(EntityHuman entityhuman)
/*     */   {
/* 190 */     return true;
/*     */   }
/*     */   
/*     */   public void startOpen(EntityHuman entityhuman) {}
/*     */   
/*     */   public void closeContainer(EntityHuman entityhuman) {}
/*     */   
/*     */   public boolean b(int i, ItemStack itemstack) {
/* 198 */     return true;
/*     */   }
/*     */   
/*     */   public int getProperty(int i) {
/* 202 */     return 0;
/*     */   }
/*     */   
/*     */   public void b(int i, int j) {}
/*     */   
/*     */   public int g() {
/* 208 */     return 0;
/*     */   }
/*     */   
/*     */   public void l() {
/* 212 */     for (int i = 0; i < this.items.length; i++) {
/* 213 */       this.items[i] = null;
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_8_R3\InventorySubcontainer.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */
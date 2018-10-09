/*     */ package net.minecraft.server.v1_8_R3;
/*     */ 
/*     */ import java.util.ArrayList;
/*     */ import java.util.List;
/*     */ import org.bukkit.craftbukkit.v1_8_R3.entity.CraftHumanEntity;
/*     */ import org.bukkit.craftbukkit.v1_8_R3.entity.CraftVillager;
/*     */ import org.bukkit.entity.HumanEntity;
/*     */ import org.bukkit.inventory.InventoryHolder;
/*     */ 
/*     */ public class InventoryMerchant implements IInventory
/*     */ {
/*     */   private final IMerchant merchant;
/*  13 */   private ItemStack[] itemsInSlots = new ItemStack[3];
/*     */   
/*     */   private final EntityHuman player;
/*     */   
/*     */   private MerchantRecipe recipe;
/*     */   private int e;
/*  19 */   public List<HumanEntity> transaction = new ArrayList();
/*  20 */   private int maxStack = 64;
/*     */   
/*     */   public ItemStack[] getContents() {
/*  23 */     return this.itemsInSlots;
/*     */   }
/*     */   
/*     */   public void onOpen(CraftHumanEntity who) {
/*  27 */     this.transaction.add(who);
/*     */   }
/*     */   
/*     */   public void onClose(CraftHumanEntity who) {
/*  31 */     this.transaction.remove(who);
/*     */   }
/*     */   
/*     */   public List<HumanEntity> getViewers() {
/*  35 */     return this.transaction;
/*     */   }
/*     */   
/*     */   public void setMaxStackSize(int i) {
/*  39 */     this.maxStack = i;
/*     */   }
/*     */   
/*     */   public InventoryHolder getOwner() {
/*  43 */     return (CraftVillager)((EntityVillager)this.merchant).getBukkitEntity();
/*     */   }
/*     */   
/*     */   public InventoryMerchant(EntityHuman entityhuman, IMerchant imerchant)
/*     */   {
/*  48 */     this.player = entityhuman;
/*  49 */     this.merchant = imerchant;
/*     */   }
/*     */   
/*     */   public int getSize() {
/*  53 */     return this.itemsInSlots.length;
/*     */   }
/*     */   
/*     */   public ItemStack getItem(int i) {
/*  57 */     return this.itemsInSlots[i];
/*     */   }
/*     */   
/*     */   public ItemStack splitStack(int i, int j) {
/*  61 */     if (this.itemsInSlots[i] != null)
/*     */     {
/*     */ 
/*  64 */       if (i == 2) {
/*  65 */         ItemStack itemstack = this.itemsInSlots[i];
/*  66 */         this.itemsInSlots[i] = null;
/*  67 */         return itemstack; }
/*  68 */       if (this.itemsInSlots[i].count <= j) {
/*  69 */         ItemStack itemstack = this.itemsInSlots[i];
/*  70 */         this.itemsInSlots[i] = null;
/*  71 */         if (e(i)) {
/*  72 */           h();
/*     */         }
/*     */         
/*  75 */         return itemstack;
/*     */       }
/*  77 */       ItemStack itemstack = this.itemsInSlots[i].cloneAndSubtract(j);
/*  78 */       if (this.itemsInSlots[i].count == 0) {
/*  79 */         this.itemsInSlots[i] = null;
/*     */       }
/*     */       
/*  82 */       if (e(i)) {
/*  83 */         h();
/*     */       }
/*     */       
/*  86 */       return itemstack;
/*     */     }
/*     */     
/*  89 */     return null;
/*     */   }
/*     */   
/*     */   private boolean e(int i)
/*     */   {
/*  94 */     return (i == 0) || (i == 1);
/*     */   }
/*     */   
/*     */   public ItemStack splitWithoutUpdate(int i) {
/*  98 */     if (this.itemsInSlots[i] != null) {
/*  99 */       ItemStack itemstack = this.itemsInSlots[i];
/*     */       
/* 101 */       this.itemsInSlots[i] = null;
/* 102 */       return itemstack;
/*     */     }
/* 104 */     return null;
/*     */   }
/*     */   
/*     */   public void setItem(int i, ItemStack itemstack)
/*     */   {
/* 109 */     this.itemsInSlots[i] = itemstack;
/* 110 */     if ((itemstack != null) && (itemstack.count > getMaxStackSize())) {
/* 111 */       itemstack.count = getMaxStackSize();
/*     */     }
/*     */     
/* 114 */     if (e(i)) {
/* 115 */       h();
/*     */     }
/*     */   }
/*     */   
/*     */   public String getName()
/*     */   {
/* 121 */     return "mob.villager";
/*     */   }
/*     */   
/*     */   public boolean hasCustomName() {
/* 125 */     return false;
/*     */   }
/*     */   
/*     */   public IChatBaseComponent getScoreboardDisplayName() {
/* 129 */     return hasCustomName() ? new ChatComponentText(getName()) : new ChatMessage(getName(), new Object[0]);
/*     */   }
/*     */   
/*     */   public int getMaxStackSize() {
/* 133 */     return this.maxStack;
/*     */   }
/*     */   
/*     */   public boolean a(EntityHuman entityhuman) {
/* 137 */     return this.merchant.v_() == entityhuman;
/*     */   }
/*     */   
/*     */   public void startOpen(EntityHuman entityhuman) {}
/*     */   
/*     */   public void closeContainer(EntityHuman entityhuman) {}
/*     */   
/*     */   public boolean b(int i, ItemStack itemstack) {
/* 145 */     return true;
/*     */   }
/*     */   
/*     */   public void update() {
/* 149 */     h();
/*     */   }
/*     */   
/*     */   public void h() {
/* 153 */     this.recipe = null;
/* 154 */     ItemStack itemstack = this.itemsInSlots[0];
/* 155 */     ItemStack itemstack1 = this.itemsInSlots[1];
/*     */     
/* 157 */     if (itemstack == null) {
/* 158 */       itemstack = itemstack1;
/* 159 */       itemstack1 = null;
/*     */     }
/*     */     
/* 162 */     if (itemstack == null) {
/* 163 */       setItem(2, null);
/*     */     } else {
/* 165 */       MerchantRecipeList merchantrecipelist = this.merchant.getOffers(this.player);
/*     */       
/* 167 */       if (merchantrecipelist != null) {
/* 168 */         MerchantRecipe merchantrecipe = merchantrecipelist.a(itemstack, itemstack1, this.e);
/*     */         
/* 170 */         if ((merchantrecipe != null) && (!merchantrecipe.h())) {
/* 171 */           this.recipe = merchantrecipe;
/* 172 */           setItem(2, merchantrecipe.getBuyItem3().cloneItemStack());
/* 173 */         } else if (itemstack1 != null) {
/* 174 */           merchantrecipe = merchantrecipelist.a(itemstack1, itemstack, this.e);
/* 175 */           if ((merchantrecipe != null) && (!merchantrecipe.h())) {
/* 176 */             this.recipe = merchantrecipe;
/* 177 */             setItem(2, merchantrecipe.getBuyItem3().cloneItemStack());
/*     */           } else {
/* 179 */             setItem(2, null);
/*     */           }
/*     */         } else {
/* 182 */           setItem(2, null);
/*     */         }
/*     */       }
/*     */     }
/*     */     
/* 187 */     this.merchant.a_(getItem(2));
/*     */   }
/*     */   
/*     */   public MerchantRecipe getRecipe() {
/* 191 */     return this.recipe;
/*     */   }
/*     */   
/*     */   public void d(int i) {
/* 195 */     this.e = i;
/* 196 */     h();
/*     */   }
/*     */   
/*     */   public int getProperty(int i) {
/* 200 */     return 0;
/*     */   }
/*     */   
/*     */   public void b(int i, int j) {}
/*     */   
/*     */   public int g() {
/* 206 */     return 0;
/*     */   }
/*     */   
/*     */   public void l() {
/* 210 */     for (int i = 0; i < this.itemsInSlots.length; i++) {
/* 211 */       this.itemsInSlots[i] = null;
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_8_R3\InventoryMerchant.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */
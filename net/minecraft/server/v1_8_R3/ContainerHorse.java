/*     */ package net.minecraft.server.v1_8_R3;
/*     */ 
/*     */ import java.util.List;
/*     */ import org.bukkit.craftbukkit.v1_8_R3.inventory.CraftInventory;
/*     */ import org.bukkit.craftbukkit.v1_8_R3.inventory.CraftInventoryHorse;
/*     */ import org.bukkit.craftbukkit.v1_8_R3.inventory.CraftInventoryView;
/*     */ import org.bukkit.inventory.InventoryView;
/*     */ 
/*     */ 
/*     */ public class ContainerHorse
/*     */   extends Container
/*     */ {
/*     */   private IInventory a;
/*     */   private EntityHorse f;
/*     */   CraftInventoryView bukkitEntity;
/*     */   PlayerInventory player;
/*     */   
/*     */   public InventoryView getBukkitView()
/*     */   {
/*  20 */     if (this.bukkitEntity != null) {
/*  21 */       return this.bukkitEntity;
/*     */     }
/*     */     
/*  24 */     CraftInventory inventory = new CraftInventoryHorse(this.a);
/*  25 */     return this.bukkitEntity = new CraftInventoryView(this.player.player.getBukkitEntity(), inventory, this);
/*     */   }
/*     */   
/*     */   public ContainerHorse(IInventory iinventory, IInventory iinventory1, final EntityHorse entityhorse, EntityHuman entityhuman) {
/*  29 */     this.player = ((PlayerInventory)iinventory);
/*     */     
/*  31 */     this.a = iinventory1;
/*  32 */     this.f = entityhorse;
/*  33 */     byte b0 = 3;
/*     */     
/*  35 */     iinventory1.startOpen(entityhuman);
/*  36 */     int i = (b0 - 4) * 18;
/*     */     
/*  38 */     a(new Slot(iinventory1, 0, 8, 18) {
/*     */       public boolean isAllowed(ItemStack itemstack) {
/*  40 */         return (super.isAllowed(itemstack)) && (itemstack.getItem() == Items.SADDLE) && (!hasItem());
/*     */       }
/*  42 */     });
/*  43 */     a(new Slot(iinventory1, 1, 8, 36) {
/*     */       public boolean isAllowed(ItemStack itemstack) {
/*  45 */         return (super.isAllowed(itemstack)) && (entityhorse.cO()) && (EntityHorse.a(itemstack.getItem()));
/*     */       }
/*     */     });
/*     */     
/*     */ 
/*     */ 
/*  51 */     if (entityhorse.hasChest()) {
/*  52 */       for (int j = 0; j < b0; j++) {
/*  53 */         for (int k = 0; k < 5; k++) {
/*  54 */           a(new Slot(iinventory1, 2 + k + j * 5, 80 + k * 18, 18 + j * 18));
/*     */         }
/*     */       }
/*     */     }
/*     */     
/*  59 */     for (int j = 0; j < 3; j++) {
/*  60 */       for (int k = 0; k < 9; k++) {
/*  61 */         a(new Slot(iinventory, k + j * 9 + 9, 8 + k * 18, 102 + j * 18 + i));
/*     */       }
/*     */     }
/*     */     
/*  65 */     for (j = 0; j < 9; j++) {
/*  66 */       a(new Slot(iinventory, j, 8 + j * 18, 160 + i));
/*     */     }
/*     */   }
/*     */   
/*     */   public boolean a(EntityHuman entityhuman)
/*     */   {
/*  72 */     return (this.a.a(entityhuman)) && (this.f.isAlive()) && (this.f.g(entityhuman) < 8.0F);
/*     */   }
/*     */   
/*     */   public ItemStack b(EntityHuman entityhuman, int i) {
/*  76 */     ItemStack itemstack = null;
/*  77 */     Slot slot = (Slot)this.c.get(i);
/*     */     
/*  79 */     if ((slot != null) && (slot.hasItem())) {
/*  80 */       ItemStack itemstack1 = slot.getItem();
/*     */       
/*  82 */       itemstack = itemstack1.cloneItemStack();
/*  83 */       if (i < this.a.getSize()) {
/*  84 */         if (!a(itemstack1, this.a.getSize(), this.c.size(), true)) {
/*  85 */           return null;
/*     */         }
/*  87 */       } else if ((getSlot(1).isAllowed(itemstack1)) && (!getSlot(1).hasItem())) {
/*  88 */         if (!a(itemstack1, 1, 2, false)) {
/*  89 */           return null;
/*     */         }
/*  91 */       } else if (getSlot(0).isAllowed(itemstack1)) {
/*  92 */         if (!a(itemstack1, 0, 1, false)) {
/*  93 */           return null;
/*     */         }
/*  95 */       } else if ((this.a.getSize() <= 2) || (!a(itemstack1, 2, this.a.getSize(), false))) {
/*  96 */         return null;
/*     */       }
/*     */       
/*  99 */       if (itemstack1.count == 0) {
/* 100 */         slot.set(null);
/*     */       } else {
/* 102 */         slot.f();
/*     */       }
/*     */     }
/*     */     
/* 106 */     return itemstack;
/*     */   }
/*     */   
/*     */   public void b(EntityHuman entityhuman) {
/* 110 */     super.b(entityhuman);
/* 111 */     this.a.closeContainer(entityhuman);
/*     */   }
/*     */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_8_R3\ContainerHorse.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */
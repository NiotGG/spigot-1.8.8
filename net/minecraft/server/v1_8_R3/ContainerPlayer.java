/*     */ package net.minecraft.server.v1_8_R3;
/*     */ 
/*     */ import java.util.List;
/*     */ import org.bukkit.craftbukkit.v1_8_R3.inventory.CraftInventoryCrafting;
/*     */ import org.bukkit.craftbukkit.v1_8_R3.inventory.CraftInventoryView;
/*     */ 
/*     */ public class ContainerPlayer
/*     */   extends Container
/*     */ {
/*  10 */   public InventoryCrafting craftInventory = new InventoryCrafting(this, 2, 2);
/*  11 */   public IInventory resultInventory = new InventoryCraftResult();
/*     */   
/*     */   public boolean g;
/*     */   private final EntityHuman h;
/*  15 */   private CraftInventoryView bukkitEntity = null;
/*     */   private PlayerInventory player;
/*     */   
/*     */   public ContainerPlayer(PlayerInventory playerinventory, boolean flag, EntityHuman entityhuman)
/*     */   {
/*  20 */     this.g = flag;
/*  21 */     this.h = entityhuman;
/*  22 */     this.resultInventory = new InventoryCraftResult();
/*  23 */     this.craftInventory = new InventoryCrafting(this, 2, 2, playerinventory.player);
/*  24 */     this.craftInventory.resultInventory = this.resultInventory;
/*  25 */     this.player = playerinventory;
/*  26 */     a(new SlotResult(playerinventory.player, this.craftInventory, this.resultInventory, 0, 144, 36));
/*     */     
/*     */ 
/*     */ 
/*     */ 
/*  31 */     for (int i = 0; i < 2; i++) {
/*  32 */       for (int j = 0; j < 2; j++) {
/*  33 */         a(new Slot(this.craftInventory, j + i * 2, 88 + j * 18, 26 + i * 18));
/*     */       }
/*     */     }
/*     */     
/*  37 */     for (int ii = 0; ii < 4; ii++) {
/*  38 */       final int i = ii;
/*  39 */       a(new Slot(playerinventory, playerinventory.getSize() - 1 - ii, 8, 8 + ii * 18) {
/*     */         public int getMaxStackSize() {
/*  41 */           return 1;
/*     */         }
/*     */         
/*     */         public boolean isAllowed(ItemStack itemstack) {
/*  45 */           return itemstack != null;
/*     */         }
/*     */       });
/*     */     }
/*     */     
/*  50 */     for (int i = 0; i < 3; i++) {
/*  51 */       for (int j = 0; j < 9; j++) {
/*  52 */         a(new Slot(playerinventory, j + (i + 1) * 9, 8 + j * 18, 84 + i * 18));
/*     */       }
/*     */     }
/*     */     
/*  56 */     for (int i = 0; i < 9; i++) {
/*  57 */       a(new Slot(playerinventory, i, 8 + i * 18, 142));
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public void a(IInventory iinventory)
/*     */   {
/*  66 */     CraftingManager.getInstance().lastCraftView = getBukkitView();
/*  67 */     ItemStack craftResult = CraftingManager.getInstance().craft(this.craftInventory, this.h.world);
/*  68 */     this.resultInventory.setItem(0, craftResult);
/*  69 */     if (this.listeners.size() < 1) {
/*  70 */       return;
/*     */     }
/*     */     
/*  73 */     EntityPlayer player = (EntityPlayer)this.listeners.get(0);
/*  74 */     player.playerConnection.sendPacket(new PacketPlayOutSetSlot(player.activeContainer.windowId, 0, craftResult));
/*     */   }
/*     */   
/*     */   public void b(EntityHuman entityhuman)
/*     */   {
/*  79 */     super.b(entityhuman);
/*     */     
/*  81 */     for (int i = 0; i < 4; i++) {
/*  82 */       ItemStack itemstack = this.craftInventory.splitWithoutUpdate(i);
/*     */       
/*  84 */       if (itemstack != null) {
/*  85 */         entityhuman.drop(itemstack, false);
/*     */       }
/*     */     }
/*     */     
/*  89 */     this.resultInventory.setItem(0, null);
/*     */   }
/*     */   
/*     */   public boolean a(EntityHuman entityhuman) {
/*  93 */     return true;
/*     */   }
/*     */   
/*     */   public ItemStack b(EntityHuman entityhuman, int i) {
/*  97 */     ItemStack itemstack = null;
/*  98 */     Slot slot = (Slot)this.c.get(i);
/*     */     
/* 100 */     if ((slot != null) && (slot.hasItem())) {
/* 101 */       ItemStack itemstack1 = slot.getItem();
/*     */       
/* 103 */       itemstack = itemstack1.cloneItemStack();
/* 104 */       if (i == 0) {
/* 105 */         if (!a(itemstack1, 9, 45, true)) {
/* 106 */           return null;
/*     */         }
/*     */         
/* 109 */         slot.a(itemstack1, itemstack);
/* 110 */       } else if ((i >= 1) && (i < 5)) {
/* 111 */         if (!a(itemstack1, 9, 45, false)) {
/* 112 */           return null;
/*     */         }
/* 114 */       } else if ((i >= 5) && (i < 9)) {
/* 115 */         if (!a(itemstack1, 9, 45, false)) {
/* 116 */           return null;
/*     */         }
/* 118 */       } else if (((itemstack.getItem() instanceof ItemArmor)) && (!((Slot)this.c.get(5 + ((ItemArmor)itemstack.getItem()).b)).hasItem())) {
/* 119 */         int j = 5 + ((ItemArmor)itemstack.getItem()).b;
/*     */         
/* 121 */         if (!a(itemstack1, j, j + 1, false)) {
/* 122 */           return null;
/*     */         }
/* 124 */       } else if ((i >= 9) && (i < 36)) {
/* 125 */         if (!a(itemstack1, 36, 45, false)) {
/* 126 */           return null;
/*     */         }
/* 128 */       } else if ((i >= 36) && (i < 45)) {
/* 129 */         if (!a(itemstack1, 9, 36, false)) {
/* 130 */           return null;
/*     */         }
/* 132 */       } else if (!a(itemstack1, 9, 45, false)) {
/* 133 */         return null;
/*     */       }
/*     */       
/* 136 */       if (itemstack1.count == 0) {
/* 137 */         slot.set(null);
/*     */       } else {
/* 139 */         slot.f();
/*     */       }
/*     */       
/* 142 */       if (itemstack1.count == itemstack.count) {
/* 143 */         return null;
/*     */       }
/*     */       
/* 146 */       slot.a(entityhuman, itemstack1);
/*     */     }
/*     */     
/* 149 */     return itemstack;
/*     */   }
/*     */   
/*     */   public boolean a(ItemStack itemstack, Slot slot) {
/* 153 */     return (slot.inventory != this.resultInventory) && (super.a(itemstack, slot));
/*     */   }
/*     */   
/*     */ 
/*     */   public CraftInventoryView getBukkitView()
/*     */   {
/* 159 */     if (this.bukkitEntity != null) {
/* 160 */       return this.bukkitEntity;
/*     */     }
/*     */     
/* 163 */     CraftInventoryCrafting inventory = new CraftInventoryCrafting(this.craftInventory, this.resultInventory);
/* 164 */     this.bukkitEntity = new CraftInventoryView(this.player.player.getBukkitEntity(), inventory, this);
/* 165 */     return this.bukkitEntity;
/*     */   }
/*     */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_8_R3\ContainerPlayer.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */
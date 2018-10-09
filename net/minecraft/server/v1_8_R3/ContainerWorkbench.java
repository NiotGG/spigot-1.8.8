/*     */ package net.minecraft.server.v1_8_R3;
/*     */ 
/*     */ import java.util.List;
/*     */ import org.bukkit.craftbukkit.v1_8_R3.inventory.CraftInventoryCrafting;
/*     */ import org.bukkit.craftbukkit.v1_8_R3.inventory.CraftInventoryView;
/*     */ 
/*     */ 
/*     */ public class ContainerWorkbench
/*     */   extends Container
/*     */ {
/*     */   public InventoryCrafting craftInventory;
/*     */   public IInventory resultInventory;
/*     */   private World g;
/*     */   private BlockPosition h;
/*  15 */   private CraftInventoryView bukkitEntity = null;
/*     */   
/*     */   private PlayerInventory player;
/*     */   
/*     */   public ContainerWorkbench(PlayerInventory playerinventory, World world, BlockPosition blockposition)
/*     */   {
/*  21 */     this.resultInventory = new InventoryCraftResult();
/*  22 */     this.craftInventory = new InventoryCrafting(this, 3, 3, playerinventory.player);
/*  23 */     this.craftInventory.resultInventory = this.resultInventory;
/*  24 */     this.player = playerinventory;
/*     */     
/*  26 */     this.g = world;
/*  27 */     this.h = blockposition;
/*  28 */     a(new SlotResult(playerinventory.player, this.craftInventory, this.resultInventory, 0, 124, 35));
/*     */     
/*     */ 
/*     */ 
/*     */ 
/*  33 */     for (int i = 0; i < 3; i++) {
/*  34 */       for (int j = 0; j < 3; j++) {
/*  35 */         a(new Slot(this.craftInventory, j + i * 3, 30 + j * 18, 17 + i * 18));
/*     */       }
/*     */     }
/*     */     
/*  39 */     for (i = 0; i < 3; i++) {
/*  40 */       for (int j = 0; j < 9; j++) {
/*  41 */         a(new Slot(playerinventory, j + i * 9 + 9, 8 + j * 18, 84 + i * 18));
/*     */       }
/*     */     }
/*     */     
/*  45 */     for (i = 0; i < 9; i++) {
/*  46 */       a(new Slot(playerinventory, i, 8 + i * 18, 142));
/*     */     }
/*     */     
/*  49 */     a(this.craftInventory);
/*     */   }
/*     */   
/*     */ 
/*     */   public void a(IInventory iinventory)
/*     */   {
/*  55 */     CraftingManager.getInstance().lastCraftView = getBukkitView();
/*  56 */     ItemStack craftResult = CraftingManager.getInstance().craft(this.craftInventory, this.g);
/*  57 */     this.resultInventory.setItem(0, craftResult);
/*  58 */     if (this.listeners.size() < 1) {
/*  59 */       return;
/*     */     }
/*     */     
/*  62 */     if ((craftResult != null) && (craftResult.getItem() == Items.FILLED_MAP)) {
/*  63 */       return;
/*     */     }
/*  65 */     EntityPlayer player = (EntityPlayer)this.listeners.get(0);
/*  66 */     player.playerConnection.sendPacket(new PacketPlayOutSetSlot(player.activeContainer.windowId, 0, craftResult));
/*     */   }
/*     */   
/*     */   public void b(EntityHuman entityhuman)
/*     */   {
/*  71 */     super.b(entityhuman);
/*  72 */     if (!this.g.isClientSide) {
/*  73 */       for (int i = 0; i < 9; i++) {
/*  74 */         ItemStack itemstack = this.craftInventory.splitWithoutUpdate(i);
/*     */         
/*  76 */         if (itemstack != null) {
/*  77 */           entityhuman.drop(itemstack, false);
/*     */         }
/*     */       }
/*     */     }
/*     */   }
/*     */   
/*     */   public boolean a(EntityHuman entityhuman)
/*     */   {
/*  85 */     if (!this.checkReachable) return true;
/*  86 */     return this.g.getType(this.h).getBlock() == Blocks.CRAFTING_TABLE;
/*     */   }
/*     */   
/*     */   public ItemStack b(EntityHuman entityhuman, int i) {
/*  90 */     ItemStack itemstack = null;
/*  91 */     Slot slot = (Slot)this.c.get(i);
/*     */     
/*  93 */     if ((slot != null) && (slot.hasItem())) {
/*  94 */       ItemStack itemstack1 = slot.getItem();
/*     */       
/*  96 */       itemstack = itemstack1.cloneItemStack();
/*  97 */       if (i == 0) {
/*  98 */         if (!a(itemstack1, 10, 46, true)) {
/*  99 */           return null;
/*     */         }
/*     */         
/* 102 */         slot.a(itemstack1, itemstack);
/* 103 */       } else if ((i >= 10) && (i < 37)) {
/* 104 */         if (!a(itemstack1, 37, 46, false)) {
/* 105 */           return null;
/*     */         }
/* 107 */       } else if ((i >= 37) && (i < 46)) {
/* 108 */         if (!a(itemstack1, 10, 37, false)) {
/* 109 */           return null;
/*     */         }
/* 111 */       } else if (!a(itemstack1, 10, 46, false)) {
/* 112 */         return null;
/*     */       }
/*     */       
/* 115 */       if (itemstack1.count == 0) {
/* 116 */         slot.set(null);
/*     */       } else {
/* 118 */         slot.f();
/*     */       }
/*     */       
/* 121 */       if (itemstack1.count == itemstack.count) {
/* 122 */         return null;
/*     */       }
/*     */       
/* 125 */       slot.a(entityhuman, itemstack1);
/*     */     }
/*     */     
/* 128 */     return itemstack;
/*     */   }
/*     */   
/*     */   public boolean a(ItemStack itemstack, Slot slot) {
/* 132 */     return (slot.inventory != this.resultInventory) && (super.a(itemstack, slot));
/*     */   }
/*     */   
/*     */ 
/*     */   public CraftInventoryView getBukkitView()
/*     */   {
/* 138 */     if (this.bukkitEntity != null) {
/* 139 */       return this.bukkitEntity;
/*     */     }
/*     */     
/* 142 */     CraftInventoryCrafting inventory = new CraftInventoryCrafting(this.craftInventory, this.resultInventory);
/* 143 */     this.bukkitEntity = new CraftInventoryView(this.player.player.getBukkitEntity(), inventory, this);
/* 144 */     return this.bukkitEntity;
/*     */   }
/*     */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_8_R3\ContainerWorkbench.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */
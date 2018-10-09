/*     */ package net.minecraft.server.v1_8_R3;
/*     */ 
/*     */ import org.bukkit.craftbukkit.v1_8_R3.inventory.CraftInventoryBeacon;
/*     */ import org.bukkit.craftbukkit.v1_8_R3.inventory.CraftInventoryView;
/*     */ 
/*     */ public class ContainerBeacon extends Container
/*     */ {
/*     */   private IInventory beacon;
/*     */   private final SlotBeacon f;
/*  10 */   private CraftInventoryView bukkitEntity = null;
/*     */   private PlayerInventory player;
/*     */   
/*     */   public ContainerBeacon(IInventory iinventory, IInventory iinventory1)
/*     */   {
/*  15 */     this.player = ((PlayerInventory)iinventory);
/*  16 */     this.beacon = iinventory1;
/*  17 */     a(this.f = new SlotBeacon(iinventory1, 0, 136, 110));
/*  18 */     byte b0 = 36;
/*  19 */     short short0 = 137;
/*     */     
/*     */ 
/*     */ 
/*  23 */     for (int i = 0; i < 3; i++) {
/*  24 */       for (int j = 0; j < 9; j++) {
/*  25 */         a(new Slot(iinventory, j + i * 9 + 9, b0 + j * 18, short0 + i * 18));
/*     */       }
/*     */     }
/*     */     
/*  29 */     for (i = 0; i < 9; i++) {
/*  30 */       a(new Slot(iinventory, i, b0 + i * 18, 58 + short0));
/*     */     }
/*     */   }
/*     */   
/*     */   public void addSlotListener(ICrafting icrafting)
/*     */   {
/*  36 */     super.addSlotListener(icrafting);
/*  37 */     icrafting.setContainerData(this, this.beacon);
/*     */   }
/*     */   
/*     */   public IInventory e() {
/*  41 */     return this.beacon;
/*     */   }
/*     */   
/*     */   public void b(EntityHuman entityhuman) {
/*  45 */     super.b(entityhuman);
/*  46 */     if ((entityhuman != null) && (!entityhuman.world.isClientSide)) {
/*  47 */       ItemStack itemstack = this.f.a(this.f.getMaxStackSize());
/*     */       
/*  49 */       if (itemstack != null) {
/*  50 */         entityhuman.drop(itemstack, false);
/*     */       }
/*     */     }
/*     */   }
/*     */   
/*     */   public boolean a(EntityHuman entityhuman)
/*     */   {
/*  57 */     if (!this.checkReachable) return true;
/*  58 */     return this.beacon.a(entityhuman);
/*     */   }
/*     */   
/*     */   public ItemStack b(EntityHuman entityhuman, int i) {
/*  62 */     ItemStack itemstack = null;
/*  63 */     Slot slot = (Slot)this.c.get(i);
/*     */     
/*  65 */     if ((slot != null) && (slot.hasItem())) {
/*  66 */       ItemStack itemstack1 = slot.getItem();
/*     */       
/*  68 */       itemstack = itemstack1.cloneItemStack();
/*  69 */       if (i == 0) {
/*  70 */         if (!a(itemstack1, 1, 37, true)) {
/*  71 */           return null;
/*     */         }
/*     */         
/*  74 */         slot.a(itemstack1, itemstack);
/*  75 */       } else if ((!this.f.hasItem()) && (this.f.isAllowed(itemstack1)) && (itemstack1.count == 1)) {
/*  76 */         if (!a(itemstack1, 0, 1, false)) {
/*  77 */           return null;
/*     */         }
/*  79 */       } else if ((i >= 1) && (i < 28)) {
/*  80 */         if (!a(itemstack1, 28, 37, false)) {
/*  81 */           return null;
/*     */         }
/*  83 */       } else if ((i >= 28) && (i < 37)) {
/*  84 */         if (!a(itemstack1, 1, 28, false)) {
/*  85 */           return null;
/*     */         }
/*  87 */       } else if (!a(itemstack1, 1, 37, false)) {
/*  88 */         return null;
/*     */       }
/*     */       
/*  91 */       if (itemstack1.count == 0) {
/*  92 */         slot.set(null);
/*     */       } else {
/*  94 */         slot.f();
/*     */       }
/*     */       
/*  97 */       if (itemstack1.count == itemstack.count) {
/*  98 */         return null;
/*     */       }
/*     */       
/* 101 */       slot.a(entityhuman, itemstack1);
/*     */     }
/*     */     
/* 104 */     return itemstack;
/*     */   }
/*     */   
/*     */   class SlotBeacon extends Slot
/*     */   {
/*     */     public SlotBeacon(IInventory iinventory, int i, int j, int k) {
/* 110 */       super(i, j, k);
/*     */     }
/*     */     
/*     */     public boolean isAllowed(ItemStack itemstack) {
/* 114 */       return itemstack != null;
/*     */     }
/*     */     
/*     */     public int getMaxStackSize() {
/* 118 */       return 1;
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */   public CraftInventoryView getBukkitView()
/*     */   {
/* 125 */     if (this.bukkitEntity != null) {
/* 126 */       return this.bukkitEntity;
/*     */     }
/*     */     
/* 129 */     org.bukkit.craftbukkit.v1_8_R3.inventory.CraftInventory inventory = new CraftInventoryBeacon((TileEntityBeacon)this.beacon);
/* 130 */     this.bukkitEntity = new CraftInventoryView(this.player.player.getBukkitEntity(), inventory, this);
/* 131 */     return this.bukkitEntity;
/*     */   }
/*     */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_8_R3\ContainerBeacon.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */
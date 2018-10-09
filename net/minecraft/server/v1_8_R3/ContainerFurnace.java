/*     */ package net.minecraft.server.v1_8_R3;
/*     */ 
/*     */ import java.util.List;
/*     */ import org.bukkit.craftbukkit.v1_8_R3.inventory.CraftInventoryFurnace;
/*     */ import org.bukkit.craftbukkit.v1_8_R3.inventory.CraftInventoryView;
/*     */ 
/*     */ 
/*     */ 
/*     */ public class ContainerFurnace
/*     */   extends Container
/*     */ {
/*     */   private final IInventory furnace;
/*     */   private int f;
/*     */   private int g;
/*     */   private int h;
/*     */   private int i;
/*  17 */   private CraftInventoryView bukkitEntity = null;
/*     */   private PlayerInventory player;
/*     */   
/*     */   public CraftInventoryView getBukkitView()
/*     */   {
/*  22 */     if (this.bukkitEntity != null) {
/*  23 */       return this.bukkitEntity;
/*     */     }
/*     */     
/*  26 */     CraftInventoryFurnace inventory = new CraftInventoryFurnace((TileEntityFurnace)this.furnace);
/*  27 */     this.bukkitEntity = new CraftInventoryView(this.player.player.getBukkitEntity(), inventory, this);
/*  28 */     return this.bukkitEntity;
/*     */   }
/*     */   
/*     */   public ContainerFurnace(PlayerInventory playerinventory, IInventory iinventory)
/*     */   {
/*  33 */     this.furnace = iinventory;
/*  34 */     a(new Slot(iinventory, 0, 56, 17));
/*  35 */     a(new SlotFurnaceFuel(iinventory, 1, 56, 53));
/*  36 */     a(new SlotFurnaceResult(playerinventory.player, iinventory, 2, 116, 35));
/*  37 */     this.player = playerinventory;
/*     */     
/*     */ 
/*     */ 
/*  41 */     for (int i = 0; i < 3; i++) {
/*  42 */       for (int j = 0; j < 9; j++) {
/*  43 */         a(new Slot(playerinventory, j + i * 9 + 9, 8 + j * 18, 84 + i * 18));
/*     */       }
/*     */     }
/*     */     
/*  47 */     for (i = 0; i < 9; i++) {
/*  48 */       a(new Slot(playerinventory, i, 8 + i * 18, 142));
/*     */     }
/*     */   }
/*     */   
/*     */   public void addSlotListener(ICrafting icrafting)
/*     */   {
/*  54 */     super.addSlotListener(icrafting);
/*  55 */     icrafting.setContainerData(this, this.furnace);
/*     */   }
/*     */   
/*     */   public void b() {
/*  59 */     super.b();
/*     */     
/*  61 */     for (int i = 0; i < this.listeners.size(); i++) {
/*  62 */       ICrafting icrafting = (ICrafting)this.listeners.get(i);
/*     */       
/*  64 */       if (this.f != this.furnace.getProperty(2)) {
/*  65 */         icrafting.setContainerData(this, 2, this.furnace.getProperty(2));
/*     */       }
/*     */       
/*  68 */       if (this.h != this.furnace.getProperty(0)) {
/*  69 */         icrafting.setContainerData(this, 0, this.furnace.getProperty(0));
/*     */       }
/*     */       
/*  72 */       if (this.i != this.furnace.getProperty(1)) {
/*  73 */         icrafting.setContainerData(this, 1, this.furnace.getProperty(1));
/*     */       }
/*     */       
/*  76 */       if (this.g != this.furnace.getProperty(3)) {
/*  77 */         icrafting.setContainerData(this, 3, this.furnace.getProperty(3));
/*     */       }
/*     */     }
/*     */     
/*  81 */     this.f = this.furnace.getProperty(2);
/*  82 */     this.h = this.furnace.getProperty(0);
/*  83 */     this.i = this.furnace.getProperty(1);
/*  84 */     this.g = this.furnace.getProperty(3);
/*     */   }
/*     */   
/*     */   public boolean a(EntityHuman entityhuman) {
/*  88 */     if (!this.checkReachable) return true;
/*  89 */     return this.furnace.a(entityhuman);
/*     */   }
/*     */   
/*     */   public ItemStack b(EntityHuman entityhuman, int i) {
/*  93 */     ItemStack itemstack = null;
/*  94 */     Slot slot = (Slot)this.c.get(i);
/*     */     
/*  96 */     if ((slot != null) && (slot.hasItem())) {
/*  97 */       ItemStack itemstack1 = slot.getItem();
/*     */       
/*  99 */       itemstack = itemstack1.cloneItemStack();
/* 100 */       if (i == 2) {
/* 101 */         if (!a(itemstack1, 3, 39, true)) {
/* 102 */           return null;
/*     */         }
/*     */         
/* 105 */         slot.a(itemstack1, itemstack);
/* 106 */       } else if ((i != 1) && (i != 0)) {
/* 107 */         if (RecipesFurnace.getInstance().getResult(itemstack1) != null) {
/* 108 */           if (!a(itemstack1, 0, 1, false)) {
/* 109 */             return null;
/*     */           }
/* 111 */         } else if (TileEntityFurnace.isFuel(itemstack1)) {
/* 112 */           if (!a(itemstack1, 1, 2, false)) {
/* 113 */             return null;
/*     */           }
/* 115 */         } else if ((i >= 3) && (i < 30)) {
/* 116 */           if (!a(itemstack1, 30, 39, false)) {
/* 117 */             return null;
/*     */           }
/* 119 */         } else if ((i >= 30) && (i < 39) && (!a(itemstack1, 3, 30, false))) {
/* 120 */           return null;
/*     */         }
/* 122 */       } else if (!a(itemstack1, 3, 39, false)) {
/* 123 */         return null;
/*     */       }
/*     */       
/* 126 */       if (itemstack1.count == 0) {
/* 127 */         slot.set(null);
/*     */       } else {
/* 129 */         slot.f();
/*     */       }
/*     */       
/* 132 */       if (itemstack1.count == itemstack.count) {
/* 133 */         return null;
/*     */       }
/*     */       
/* 136 */       slot.a(entityhuman, itemstack1);
/*     */     }
/*     */     
/* 139 */     return itemstack;
/*     */   }
/*     */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_8_R3\ContainerFurnace.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */
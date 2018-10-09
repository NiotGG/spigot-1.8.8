/*     */ package org.bukkit.craftbukkit.v1_8_R3.inventory;
/*     */ 
/*     */ import net.minecraft.server.v1_8_R3.Container;
/*     */ import net.minecraft.server.v1_8_R3.Slot;
/*     */ import org.bukkit.GameMode;
/*     */ import org.bukkit.craftbukkit.v1_8_R3.entity.CraftHumanEntity;
/*     */ import org.bukkit.entity.HumanEntity;
/*     */ import org.bukkit.event.inventory.InventoryType;
/*     */ import org.bukkit.event.inventory.InventoryType.SlotType;
/*     */ import org.bukkit.inventory.Inventory;
/*     */ import org.bukkit.inventory.InventoryView;
/*     */ 
/*     */ public class CraftInventoryView extends InventoryView
/*     */ {
/*     */   private final Container container;
/*     */   private final CraftHumanEntity player;
/*     */   private final CraftInventory viewing;
/*     */   
/*     */   public CraftInventoryView(HumanEntity player, Inventory viewing, Container container)
/*     */   {
/*  21 */     this.player = ((CraftHumanEntity)player);
/*  22 */     this.viewing = ((CraftInventory)viewing);
/*  23 */     this.container = container;
/*     */   }
/*     */   
/*     */   public Inventory getTopInventory()
/*     */   {
/*  28 */     return this.viewing;
/*     */   }
/*     */   
/*     */   public Inventory getBottomInventory()
/*     */   {
/*  33 */     return this.player.getInventory();
/*     */   }
/*     */   
/*     */   public HumanEntity getPlayer()
/*     */   {
/*  38 */     return this.player;
/*     */   }
/*     */   
/*     */   public InventoryType getType()
/*     */   {
/*  43 */     InventoryType type = this.viewing.getType();
/*  44 */     if ((type == InventoryType.CRAFTING) && (this.player.getGameMode() == GameMode.CREATIVE)) {
/*  45 */       return InventoryType.CREATIVE;
/*     */     }
/*  47 */     return type;
/*     */   }
/*     */   
/*     */   public void setItem(int slot, org.bukkit.inventory.ItemStack item)
/*     */   {
/*  52 */     net.minecraft.server.v1_8_R3.ItemStack stack = CraftItemStack.asNMSCopy(item);
/*  53 */     if (slot != 64537) {
/*  54 */       this.container.getSlot(slot).set(stack);
/*     */     } else {
/*  56 */       this.player.getHandle().drop(stack, false);
/*     */     }
/*     */   }
/*     */   
/*     */   public org.bukkit.inventory.ItemStack getItem(int slot)
/*     */   {
/*  62 */     if (slot == 64537) {
/*  63 */       return null;
/*     */     }
/*  65 */     return CraftItemStack.asCraftMirror(this.container.getSlot(slot).getItem());
/*     */   }
/*     */   
/*     */   public boolean isInTop(int rawSlot) {
/*  69 */     return rawSlot < this.viewing.getSize();
/*     */   }
/*     */   
/*     */   public Container getHandle() {
/*  73 */     return this.container;
/*     */   }
/*     */   
/*     */   public static InventoryType.SlotType getSlotType(InventoryView inventory, int slot) {
/*  77 */     InventoryType.SlotType type = InventoryType.SlotType.CONTAINER;
/*  78 */     if ((slot >= 0) && (slot < inventory.getTopInventory().getSize())) {}
/*  79 */     switch (inventory.getType()) {
/*     */     case CHEST: 
/*  81 */       if (slot == 2) {
/*  82 */         type = InventoryType.SlotType.RESULT;
/*  83 */       } else if (slot == 1) {
/*  84 */         type = InventoryType.SlotType.FUEL;
/*     */       } else {
/*  86 */         type = InventoryType.SlotType.CRAFTING;
/*     */       }
/*  88 */       break;
/*     */     case DROPPER: 
/*  90 */       if (slot == 3) {
/*  91 */         type = InventoryType.SlotType.FUEL;
/*     */       } else {
/*  93 */         type = InventoryType.SlotType.CRAFTING;
/*     */       }
/*  95 */       break;
/*     */     case DISPENSER: 
/*  97 */       type = InventoryType.SlotType.CRAFTING;
/*  98 */       break;
/*     */     case CRAFTING: 
/*     */     case CREATIVE: 
/* 101 */       if (slot == 0) {
/* 102 */         type = InventoryType.SlotType.RESULT;
/*     */       } else {
/* 104 */         type = InventoryType.SlotType.CRAFTING;
/*     */       }
/* 106 */       break;
/*     */     case FURNACE: 
/* 108 */       if (slot == 2) {
/* 109 */         type = InventoryType.SlotType.RESULT;
/*     */       } else {
/* 111 */         type = InventoryType.SlotType.CRAFTING;
/*     */       }
/* 113 */       break;
/*     */     case PLAYER: 
/* 115 */       type = InventoryType.SlotType.CRAFTING;
/* 116 */       break;
/*     */     case MERCHANT: 
/* 118 */       if (slot == 2) {
/* 119 */         type = InventoryType.SlotType.RESULT;
/*     */       } else {
/* 121 */         type = InventoryType.SlotType.CRAFTING;
/*     */       }
/*     */       break;
/*     */     case ENCHANTING: case ENDER_CHEST: 
/*     */     case HOPPER: 
/*     */     default: 
/*     */       break;
/* 128 */       if (slot == 64537) {
/* 129 */         type = InventoryType.SlotType.OUTSIDE;
/* 130 */       } else if (inventory.getType() == InventoryType.CRAFTING) {
/* 131 */         if (slot < 9) {
/* 132 */           type = InventoryType.SlotType.ARMOR;
/* 133 */         } else if (slot > 35) {
/* 134 */           type = InventoryType.SlotType.QUICKBAR;
/*     */         }
/* 136 */       } else if (slot >= inventory.countSlots() - 9)
/* 137 */         type = InventoryType.SlotType.QUICKBAR;
/*     */       break;
/*     */     }
/* 140 */     return type;
/*     */   }
/*     */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\org\bukkit\craftbukkit\v1_8_R3\inventory\CraftInventoryView.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */
/*     */ package net.minecraft.server.v1_8_R3;
/*     */ 
/*     */ import java.util.List;
/*     */ import org.bukkit.craftbukkit.v1_8_R3.inventory.CraftInventoryBrewer;
/*     */ import org.bukkit.craftbukkit.v1_8_R3.inventory.CraftInventoryView;
/*     */ 
/*     */ 
/*     */ 
/*     */ public class ContainerBrewingStand
/*     */   extends Container
/*     */ {
/*     */   private IInventory brewingStand;
/*     */   private final Slot f;
/*     */   private int g;
/*  15 */   private CraftInventoryView bukkitEntity = null;
/*     */   private PlayerInventory player;
/*     */   
/*     */   public ContainerBrewingStand(PlayerInventory playerinventory, IInventory iinventory)
/*     */   {
/*  20 */     this.player = playerinventory;
/*  21 */     this.brewingStand = iinventory;
/*  22 */     a(new SlotPotionBottle(playerinventory.player, iinventory, 0, 56, 46));
/*  23 */     a(new SlotPotionBottle(playerinventory.player, iinventory, 1, 79, 53));
/*  24 */     a(new SlotPotionBottle(playerinventory.player, iinventory, 2, 102, 46));
/*  25 */     this.f = a(new SlotBrewing(iinventory, 3, 79, 17));
/*     */     
/*     */ 
/*     */ 
/*  29 */     for (int i = 0; i < 3; i++) {
/*  30 */       for (int j = 0; j < 9; j++) {
/*  31 */         a(new Slot(playerinventory, j + i * 9 + 9, 8 + j * 18, 84 + i * 18));
/*     */       }
/*     */     }
/*     */     
/*  35 */     for (i = 0; i < 9; i++) {
/*  36 */       a(new Slot(playerinventory, i, 8 + i * 18, 142));
/*     */     }
/*     */   }
/*     */   
/*     */   public void addSlotListener(ICrafting icrafting)
/*     */   {
/*  42 */     super.addSlotListener(icrafting);
/*  43 */     icrafting.setContainerData(this, this.brewingStand);
/*     */   }
/*     */   
/*     */   public void b() {
/*  47 */     super.b();
/*     */     
/*  49 */     for (int i = 0; i < this.listeners.size(); i++) {
/*  50 */       ICrafting icrafting = (ICrafting)this.listeners.get(i);
/*     */       
/*  52 */       if (this.g != this.brewingStand.getProperty(0)) {
/*  53 */         icrafting.setContainerData(this, 0, this.brewingStand.getProperty(0));
/*     */       }
/*     */     }
/*     */     
/*  57 */     this.g = this.brewingStand.getProperty(0);
/*     */   }
/*     */   
/*     */   public boolean a(EntityHuman entityhuman) {
/*  61 */     if (!this.checkReachable) return true;
/*  62 */     return this.brewingStand.a(entityhuman);
/*     */   }
/*     */   
/*     */   public ItemStack b(EntityHuman entityhuman, int i) {
/*  66 */     ItemStack itemstack = null;
/*  67 */     Slot slot = (Slot)this.c.get(i);
/*     */     
/*  69 */     if ((slot != null) && (slot.hasItem())) {
/*  70 */       ItemStack itemstack1 = slot.getItem();
/*     */       
/*  72 */       itemstack = itemstack1.cloneItemStack();
/*  73 */       if (((i < 0) || (i > 2)) && (i != 3)) {
/*  74 */         if ((!this.f.hasItem()) && (this.f.isAllowed(itemstack1))) {
/*  75 */           if (!a(itemstack1, 3, 4, false)) {
/*  76 */             return null;
/*     */           }
/*  78 */         } else if (SlotPotionBottle.b_(itemstack)) {
/*  79 */           if (!a(itemstack1, 0, 3, false)) {
/*  80 */             return null;
/*     */           }
/*  82 */         } else if ((i >= 4) && (i < 31)) {
/*  83 */           if (!a(itemstack1, 31, 40, false)) {
/*  84 */             return null;
/*     */           }
/*  86 */         } else if ((i >= 31) && (i < 40)) {
/*  87 */           if (!a(itemstack1, 4, 31, false)) {
/*  88 */             return null;
/*     */           }
/*  90 */         } else if (!a(itemstack1, 4, 40, false)) {
/*  91 */           return null;
/*     */         }
/*     */       } else {
/*  94 */         if (!a(itemstack1, 4, 40, true)) {
/*  95 */           return null;
/*     */         }
/*     */         
/*  98 */         slot.a(itemstack1, itemstack);
/*     */       }
/*     */       
/* 101 */       if (itemstack1.count == 0) {
/* 102 */         slot.set(null);
/*     */       } else {
/* 104 */         slot.f();
/*     */       }
/*     */       
/* 107 */       if (itemstack1.count == itemstack.count) {
/* 108 */         return null;
/*     */       }
/*     */       
/* 111 */       slot.a(entityhuman, itemstack1);
/*     */     }
/*     */     
/* 114 */     return itemstack;
/*     */   }
/*     */   
/*     */   class SlotBrewing extends Slot
/*     */   {
/*     */     public SlotBrewing(IInventory iinventory, int i, int j, int k) {
/* 120 */       super(i, j, k);
/*     */     }
/*     */     
/*     */     public boolean isAllowed(ItemStack itemstack) {
/* 124 */       return itemstack != null ? itemstack.getItem().l(itemstack) : false;
/*     */     }
/*     */     
/*     */     public int getMaxStackSize() {
/* 128 */       return 64;
/*     */     }
/*     */   }
/*     */   
/*     */   static class SlotPotionBottle extends Slot
/*     */   {
/*     */     private EntityHuman a;
/*     */     
/*     */     public SlotPotionBottle(EntityHuman entityhuman, IInventory iinventory, int i, int j, int k) {
/* 137 */       super(i, j, k);
/* 138 */       this.a = entityhuman;
/*     */     }
/*     */     
/*     */     public boolean isAllowed(ItemStack itemstack) {
/* 142 */       return b_(itemstack);
/*     */     }
/*     */     
/*     */     public int getMaxStackSize() {
/* 146 */       return 1;
/*     */     }
/*     */     
/*     */     public void a(EntityHuman entityhuman, ItemStack itemstack) {
/* 150 */       if ((itemstack.getItem() == Items.POTION) && (itemstack.getData() > 0)) {
/* 151 */         this.a.b(AchievementList.B);
/*     */       }
/*     */       
/* 154 */       super.a(entityhuman, itemstack);
/*     */     }
/*     */     
/*     */     public static boolean b_(ItemStack itemstack) {
/* 158 */       return (itemstack != null) && ((itemstack.getItem() == Items.POTION) || (itemstack.getItem() == Items.GLASS_BOTTLE));
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */   public CraftInventoryView getBukkitView()
/*     */   {
/* 165 */     if (this.bukkitEntity != null) {
/* 166 */       return this.bukkitEntity;
/*     */     }
/*     */     
/* 169 */     CraftInventoryBrewer inventory = new CraftInventoryBrewer(this.brewingStand);
/* 170 */     this.bukkitEntity = new CraftInventoryView(this.player.player.getBukkitEntity(), inventory, this);
/* 171 */     return this.bukkitEntity;
/*     */   }
/*     */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_8_R3\ContainerBrewingStand.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */
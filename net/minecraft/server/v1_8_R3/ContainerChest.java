/*     */ package net.minecraft.server.v1_8_R3;
/*     */ 
/*     */ import java.util.List;
/*     */ import org.bukkit.craftbukkit.v1_8_R3.inventory.CraftInventory;
/*     */ import org.bukkit.craftbukkit.v1_8_R3.inventory.CraftInventoryDoubleChest;
/*     */ import org.bukkit.craftbukkit.v1_8_R3.inventory.CraftInventoryPlayer;
/*     */ import org.bukkit.craftbukkit.v1_8_R3.inventory.CraftInventoryView;
/*     */ 
/*     */ public class ContainerChest extends Container
/*     */ {
/*     */   private IInventory container;
/*     */   private int f;
/*  13 */   private CraftInventoryView bukkitEntity = null;
/*     */   private PlayerInventory player;
/*     */   
/*     */   public CraftInventoryView getBukkitView()
/*     */   {
/*  18 */     if (this.bukkitEntity != null) {
/*  19 */       return this.bukkitEntity;
/*     */     }
/*     */     CraftInventory inventory;
/*     */     CraftInventory inventory;
/*  23 */     if ((this.container instanceof PlayerInventory)) {
/*  24 */       inventory = new CraftInventoryPlayer((PlayerInventory)this.container); } else { CraftInventory inventory;
/*  25 */       if ((this.container instanceof InventoryLargeChest)) {
/*  26 */         inventory = new CraftInventoryDoubleChest((InventoryLargeChest)this.container);
/*     */       } else {
/*  28 */         inventory = new CraftInventory(this.container);
/*     */       }
/*     */     }
/*  31 */     this.bukkitEntity = new CraftInventoryView(this.player.player.getBukkitEntity(), inventory, this);
/*  32 */     return this.bukkitEntity;
/*     */   }
/*     */   
/*     */   public ContainerChest(IInventory iinventory, IInventory iinventory1, EntityHuman entityhuman)
/*     */   {
/*  37 */     this.container = iinventory1;
/*  38 */     this.f = (iinventory1.getSize() / 9);
/*  39 */     iinventory1.startOpen(entityhuman);
/*  40 */     int i = (this.f - 4) * 18;
/*     */     
/*     */ 
/*     */ 
/*  44 */     this.player = ((PlayerInventory)iinventory);
/*     */     
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*  50 */     for (int j = 0; j < this.f; j++) {
/*  51 */       for (int k = 0; k < 9; k++) {
/*  52 */         a(new Slot(iinventory1, k + j * 9, 8 + k * 18, 18 + j * 18));
/*     */       }
/*     */     }
/*     */     
/*  56 */     for (j = 0; j < 3; j++) {
/*  57 */       for (int k = 0; k < 9; k++) {
/*  58 */         a(new Slot(iinventory, k + j * 9 + 9, 8 + k * 18, 103 + j * 18 + i));
/*     */       }
/*     */     }
/*     */     
/*  62 */     for (j = 0; j < 9; j++) {
/*  63 */       a(new Slot(iinventory, j, 8 + j * 18, 161 + i));
/*     */     }
/*     */   }
/*     */   
/*     */   public boolean a(EntityHuman entityhuman)
/*     */   {
/*  69 */     if (!this.checkReachable) return true;
/*  70 */     return this.container.a(entityhuman);
/*     */   }
/*     */   
/*     */   public ItemStack b(EntityHuman entityhuman, int i) {
/*  74 */     ItemStack itemstack = null;
/*  75 */     Slot slot = (Slot)this.c.get(i);
/*     */     
/*  77 */     if ((slot != null) && (slot.hasItem())) {
/*  78 */       ItemStack itemstack1 = slot.getItem();
/*     */       
/*  80 */       itemstack = itemstack1.cloneItemStack();
/*  81 */       if (i < this.f * 9) {
/*  82 */         if (!a(itemstack1, this.f * 9, this.c.size(), true)) {
/*  83 */           return null;
/*     */         }
/*  85 */       } else if (!a(itemstack1, 0, this.f * 9, false)) {
/*  86 */         return null;
/*     */       }
/*     */       
/*  89 */       if (itemstack1.count == 0) {
/*  90 */         slot.set(null);
/*     */       } else {
/*  92 */         slot.f();
/*     */       }
/*     */     }
/*     */     
/*  96 */     return itemstack;
/*     */   }
/*     */   
/*     */   public void b(EntityHuman entityhuman) {
/* 100 */     super.b(entityhuman);
/* 101 */     this.container.closeContainer(entityhuman);
/*     */   }
/*     */   
/*     */   public IInventory e() {
/* 105 */     return this.container;
/*     */   }
/*     */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_8_R3\ContainerChest.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */
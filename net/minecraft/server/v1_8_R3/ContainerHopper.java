/*    */ package net.minecraft.server.v1_8_R3;
/*    */ 
/*    */ import java.util.List;
/*    */ import org.bukkit.craftbukkit.v1_8_R3.inventory.CraftInventory;
/*    */ import org.bukkit.craftbukkit.v1_8_R3.inventory.CraftInventoryView;
/*    */ 
/*    */ 
/*    */ 
/*    */ public class ContainerHopper
/*    */   extends Container
/*    */ {
/*    */   private final IInventory hopper;
/* 13 */   private CraftInventoryView bukkitEntity = null;
/*    */   private PlayerInventory player;
/*    */   
/*    */   public CraftInventoryView getBukkitView()
/*    */   {
/* 18 */     if (this.bukkitEntity != null) {
/* 19 */       return this.bukkitEntity;
/*    */     }
/*    */     
/* 22 */     CraftInventory inventory = new CraftInventory(this.hopper);
/* 23 */     this.bukkitEntity = new CraftInventoryView(this.player.player.getBukkitEntity(), inventory, this);
/* 24 */     return this.bukkitEntity;
/*    */   }
/*    */   
/*    */   public ContainerHopper(PlayerInventory playerinventory, IInventory iinventory, EntityHuman entityhuman)
/*    */   {
/* 29 */     this.hopper = iinventory;
/* 30 */     this.player = playerinventory;
/* 31 */     iinventory.startOpen(entityhuman);
/* 32 */     byte b0 = 51;
/*    */     
/*    */ 
/*    */ 
/* 36 */     for (int i = 0; i < iinventory.getSize(); i++) {
/* 37 */       a(new Slot(iinventory, i, 44 + i * 18, 20));
/*    */     }
/*    */     
/* 40 */     for (i = 0; i < 3; i++) {
/* 41 */       for (int j = 0; j < 9; j++) {
/* 42 */         a(new Slot(playerinventory, j + i * 9 + 9, 8 + j * 18, i * 18 + b0));
/*    */       }
/*    */     }
/*    */     
/* 46 */     for (i = 0; i < 9; i++) {
/* 47 */       a(new Slot(playerinventory, i, 8 + i * 18, 58 + b0));
/*    */     }
/*    */   }
/*    */   
/*    */   public boolean a(EntityHuman entityhuman)
/*    */   {
/* 53 */     if (!this.checkReachable) return true;
/* 54 */     return this.hopper.a(entityhuman);
/*    */   }
/*    */   
/*    */   public ItemStack b(EntityHuman entityhuman, int i) {
/* 58 */     ItemStack itemstack = null;
/* 59 */     Slot slot = (Slot)this.c.get(i);
/*    */     
/* 61 */     if ((slot != null) && (slot.hasItem())) {
/* 62 */       ItemStack itemstack1 = slot.getItem();
/*    */       
/* 64 */       itemstack = itemstack1.cloneItemStack();
/* 65 */       if (i < this.hopper.getSize()) {
/* 66 */         if (!a(itemstack1, this.hopper.getSize(), this.c.size(), true)) {
/* 67 */           return null;
/*    */         }
/* 69 */       } else if (!a(itemstack1, 0, this.hopper.getSize(), false)) {
/* 70 */         return null;
/*    */       }
/*    */       
/* 73 */       if (itemstack1.count == 0) {
/* 74 */         slot.set(null);
/*    */       } else {
/* 76 */         slot.f();
/*    */       }
/*    */     }
/*    */     
/* 80 */     return itemstack;
/*    */   }
/*    */   
/*    */   public void b(EntityHuman entityhuman) {
/* 84 */     super.b(entityhuman);
/* 85 */     this.hopper.closeContainer(entityhuman);
/*    */   }
/*    */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_8_R3\ContainerHopper.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */
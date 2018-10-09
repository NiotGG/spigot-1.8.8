/*    */ package net.minecraft.server.v1_8_R3;
/*    */ 
/*    */ import org.bukkit.craftbukkit.v1_8_R3.inventory.CraftItemStack;
/*    */ import org.bukkit.event.inventory.InventoryMoveItemEvent;
/*    */ import org.bukkit.inventory.Inventory;
/*    */ import org.bukkit.inventory.InventoryHolder;
/*    */ 
/*    */ public class BlockDropper extends BlockDispenser
/*    */ {
/* 10 */   private final IDispenseBehavior P = new DispenseBehaviorItem();
/*    */   
/*    */ 
/*    */   protected IDispenseBehavior a(ItemStack itemstack)
/*    */   {
/* 15 */     return this.P;
/*    */   }
/*    */   
/*    */   public TileEntity a(World world, int i) {
/* 19 */     return new TileEntityDropper();
/*    */   }
/*    */   
/*    */   public void dispense(World world, BlockPosition blockposition) {
/* 23 */     SourceBlock sourceblock = new SourceBlock(world, blockposition);
/* 24 */     TileEntityDispenser tileentitydispenser = (TileEntityDispenser)sourceblock.getTileEntity();
/*    */     
/* 26 */     if (tileentitydispenser != null) {
/* 27 */       int i = tileentitydispenser.m();
/*    */       
/* 29 */       if (i < 0) {
/* 30 */         world.triggerEffect(1001, blockposition, 0);
/*    */       } else {
/* 32 */         ItemStack itemstack = tileentitydispenser.getItem(i);
/*    */         
/* 34 */         if (itemstack != null) {
/* 35 */           EnumDirection enumdirection = (EnumDirection)world.getType(blockposition).get(FACING);
/* 36 */           BlockPosition blockposition1 = blockposition.shift(enumdirection);
/* 37 */           IInventory iinventory = TileEntityHopper.b(world, blockposition1.getX(), blockposition1.getY(), blockposition1.getZ());
/*    */           
/*    */           ItemStack itemstack1;
/* 40 */           if (iinventory == null) {
/* 41 */             ItemStack itemstack1 = this.P.a(sourceblock, itemstack);
/* 42 */             if ((itemstack1 != null) && (itemstack1.count <= 0)) {
/* 43 */               itemstack1 = null;
/*    */             }
/*    */           }
/*    */           else {
/* 47 */             CraftItemStack oitemstack = CraftItemStack.asCraftMirror(itemstack.cloneItemStack().cloneAndSubtract(1));
/*    */             
/*    */             Inventory destinationInventory;
/*    */             Inventory destinationInventory;
/* 51 */             if ((iinventory instanceof InventoryLargeChest)) {
/* 52 */               destinationInventory = new org.bukkit.craftbukkit.v1_8_R3.inventory.CraftInventoryDoubleChest((InventoryLargeChest)iinventory);
/*    */             } else {
/* 54 */               destinationInventory = iinventory.getOwner().getInventory();
/*    */             }
/*    */             
/* 57 */             InventoryMoveItemEvent event = new InventoryMoveItemEvent(tileentitydispenser.getOwner().getInventory(), oitemstack.clone(), destinationInventory, true);
/* 58 */             world.getServer().getPluginManager().callEvent(event);
/* 59 */             if (event.isCancelled()) {
/* 60 */               return;
/*    */             }
/* 62 */             itemstack1 = TileEntityHopper.addItem(iinventory, CraftItemStack.asNMSCopy(event.getItem()), enumdirection.opposite());
/* 63 */             if ((event.getItem().equals(oitemstack)) && (itemstack1 == null))
/*    */             {
/* 65 */               itemstack1 = itemstack.cloneItemStack();
/* 66 */               if (--itemstack1.count <= 0) {
/* 67 */                 itemstack1 = null;
/*    */               }
/*    */             } else {
/* 70 */               itemstack1 = itemstack.cloneItemStack();
/*    */             }
/*    */           }
/*    */           
/* 74 */           tileentitydispenser.setItem(i, itemstack1);
/*    */         }
/*    */       }
/*    */     }
/*    */   }
/*    */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_8_R3\BlockDropper.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */
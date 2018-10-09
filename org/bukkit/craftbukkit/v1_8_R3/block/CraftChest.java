/*    */ package org.bukkit.craftbukkit.v1_8_R3.block;
/*    */ 
/*    */ import net.minecraft.server.v1_8_R3.BlockPosition;
/*    */ import net.minecraft.server.v1_8_R3.TileEntityChest;
/*    */ import net.minecraft.server.v1_8_R3.WorldServer;
/*    */ import org.bukkit.Material;
/*    */ import org.bukkit.block.Block;
/*    */ import org.bukkit.craftbukkit.v1_8_R3.CraftWorld;
/*    */ import org.bukkit.craftbukkit.v1_8_R3.inventory.CraftInventory;
/*    */ import org.bukkit.craftbukkit.v1_8_R3.inventory.CraftInventoryDoubleChest;
/*    */ 
/*    */ public class CraftChest extends CraftBlockState implements org.bukkit.block.Chest
/*    */ {
/*    */   private final CraftWorld world;
/*    */   private final TileEntityChest chest;
/*    */   
/*    */   public CraftChest(Block block)
/*    */   {
/* 19 */     super(block);
/*    */     
/* 21 */     this.world = ((CraftWorld)block.getWorld());
/* 22 */     this.chest = ((TileEntityChest)this.world.getTileEntityAt(getX(), getY(), getZ()));
/*    */   }
/*    */   
/*    */   public CraftChest(Material material, TileEntityChest te) {
/* 26 */     super(material);
/* 27 */     this.chest = te;
/* 28 */     this.world = null;
/*    */   }
/*    */   
/*    */   public org.bukkit.inventory.Inventory getBlockInventory() {
/* 32 */     return new CraftInventory(this.chest);
/*    */   }
/*    */   
/*    */   public org.bukkit.inventory.Inventory getInventory() {
/* 36 */     int x = getX();
/* 37 */     int y = getY();
/* 38 */     int z = getZ();
/*    */     
/* 40 */     CraftInventory inventory = new CraftInventory(this.chest);
/* 41 */     if (!isPlaced()) {
/* 42 */       return inventory;
/*    */     }
/*    */     int id;
/* 45 */     if (this.world.getBlockTypeIdAt(x, y, z) == Material.CHEST.getId()) {
/* 46 */       id = Material.CHEST.getId(); } else { int id;
/* 47 */       if (this.world.getBlockTypeIdAt(x, y, z) == Material.TRAPPED_CHEST.getId()) {
/* 48 */         id = Material.TRAPPED_CHEST.getId();
/*    */       } else
/* 50 */         throw new IllegalStateException("CraftChest is not a chest but is instead " + this.world.getBlockAt(x, y, z));
/*    */     }
/*    */     int id;
/* 53 */     if (this.world.getBlockTypeIdAt(x - 1, y, z) == id) {
/* 54 */       CraftInventory left = new CraftInventory((TileEntityChest)this.world.getHandle().getTileEntity(new BlockPosition(x - 1, y, z)));
/* 55 */       inventory = new CraftInventoryDoubleChest(left, inventory);
/*    */     }
/* 57 */     if (this.world.getBlockTypeIdAt(x + 1, y, z) == id) {
/* 58 */       CraftInventory right = new CraftInventory((TileEntityChest)this.world.getHandle().getTileEntity(new BlockPosition(x + 1, y, z)));
/* 59 */       inventory = new CraftInventoryDoubleChest(inventory, right);
/*    */     }
/* 61 */     if (this.world.getBlockTypeIdAt(x, y, z - 1) == id) {
/* 62 */       CraftInventory left = new CraftInventory((TileEntityChest)this.world.getHandle().getTileEntity(new BlockPosition(x, y, z - 1)));
/* 63 */       inventory = new CraftInventoryDoubleChest(left, inventory);
/*    */     }
/* 65 */     if (this.world.getBlockTypeIdAt(x, y, z + 1) == id) {
/* 66 */       CraftInventory right = new CraftInventory((TileEntityChest)this.world.getHandle().getTileEntity(new BlockPosition(x, y, z + 1)));
/* 67 */       inventory = new CraftInventoryDoubleChest(inventory, right);
/*    */     }
/* 69 */     return inventory;
/*    */   }
/*    */   
/*    */   public boolean update(boolean force, boolean applyPhysics)
/*    */   {
/* 74 */     boolean result = super.update(force, applyPhysics);
/*    */     
/* 76 */     if (result) {
/* 77 */       this.chest.update();
/*    */     }
/*    */     
/* 80 */     return result;
/*    */   }
/*    */   
/*    */   public TileEntityChest getTileEntity()
/*    */   {
/* 85 */     return this.chest;
/*    */   }
/*    */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\org\bukkit\craftbukkit\v1_8_R3\block\CraftChest.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */
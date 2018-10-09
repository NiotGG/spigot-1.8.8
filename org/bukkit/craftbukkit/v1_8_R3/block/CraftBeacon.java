/*    */ package org.bukkit.craftbukkit.v1_8_R3.block;
/*    */ 
/*    */ import net.minecraft.server.v1_8_R3.TileEntityBeacon;
/*    */ import org.bukkit.Material;
/*    */ import org.bukkit.block.Block;
/*    */ import org.bukkit.craftbukkit.v1_8_R3.CraftWorld;
/*    */ import org.bukkit.craftbukkit.v1_8_R3.inventory.CraftInventoryBeacon;
/*    */ import org.bukkit.inventory.Inventory;
/*    */ 
/*    */ public class CraftBeacon extends CraftBlockState implements org.bukkit.block.Beacon
/*    */ {
/*    */   private final CraftWorld world;
/*    */   private final TileEntityBeacon beacon;
/*    */   
/*    */   public CraftBeacon(Block block)
/*    */   {
/* 17 */     super(block);
/*    */     
/* 19 */     this.world = ((CraftWorld)block.getWorld());
/* 20 */     this.beacon = ((TileEntityBeacon)this.world.getTileEntityAt(getX(), getY(), getZ()));
/*    */   }
/*    */   
/*    */   public CraftBeacon(Material material, TileEntityBeacon te) {
/* 24 */     super(material);
/* 25 */     this.world = null;
/* 26 */     this.beacon = te;
/*    */   }
/*    */   
/*    */   public Inventory getInventory() {
/* 30 */     return new CraftInventoryBeacon(this.beacon);
/*    */   }
/*    */   
/*    */   public boolean update(boolean force, boolean applyPhysics)
/*    */   {
/* 35 */     boolean result = super.update(force, applyPhysics);
/*    */     
/* 37 */     if (result) {
/* 38 */       this.beacon.update();
/*    */     }
/*    */     
/* 41 */     return result;
/*    */   }
/*    */   
/*    */   public TileEntityBeacon getTileEntity()
/*    */   {
/* 46 */     return this.beacon;
/*    */   }
/*    */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\org\bukkit\craftbukkit\v1_8_R3\block\CraftBeacon.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */
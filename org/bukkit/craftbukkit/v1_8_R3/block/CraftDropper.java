/*    */ package org.bukkit.craftbukkit.v1_8_R3.block;
/*    */ 
/*    */ import net.minecraft.server.v1_8_R3.BlockDropper;
/*    */ import net.minecraft.server.v1_8_R3.BlockPosition;
/*    */ import net.minecraft.server.v1_8_R3.Blocks;
/*    */ import net.minecraft.server.v1_8_R3.TileEntityDropper;
/*    */ import org.bukkit.Material;
/*    */ import org.bukkit.block.Block;
/*    */ import org.bukkit.block.Dropper;
/*    */ import org.bukkit.craftbukkit.v1_8_R3.CraftWorld;
/*    */ import org.bukkit.inventory.Inventory;
/*    */ 
/*    */ public class CraftDropper extends CraftBlockState implements Dropper
/*    */ {
/*    */   private final CraftWorld world;
/*    */   private final TileEntityDropper dropper;
/*    */   
/*    */   public CraftDropper(Block block)
/*    */   {
/* 20 */     super(block);
/*    */     
/* 22 */     this.world = ((CraftWorld)block.getWorld());
/* 23 */     this.dropper = ((TileEntityDropper)this.world.getTileEntityAt(getX(), getY(), getZ()));
/*    */   }
/*    */   
/*    */   public CraftDropper(Material material, TileEntityDropper te) {
/* 27 */     super(material);
/* 28 */     this.world = null;
/* 29 */     this.dropper = te;
/*    */   }
/*    */   
/*    */   public Inventory getInventory() {
/* 33 */     return new org.bukkit.craftbukkit.v1_8_R3.inventory.CraftInventory(this.dropper);
/*    */   }
/*    */   
/*    */   public void drop() {
/* 37 */     Block block = getBlock();
/*    */     
/* 39 */     if (block.getType() == Material.DROPPER) {
/* 40 */       BlockDropper drop = (BlockDropper)Blocks.DROPPER;
/*    */       
/* 42 */       drop.dispense(this.world.getHandle(), new BlockPosition(getX(), getY(), getZ()));
/*    */     }
/*    */   }
/*    */   
/*    */   public boolean update(boolean force, boolean applyPhysics)
/*    */   {
/* 48 */     boolean result = super.update(force, applyPhysics);
/*    */     
/* 50 */     if (result) {
/* 51 */       this.dropper.update();
/*    */     }
/*    */     
/* 54 */     return result;
/*    */   }
/*    */   
/*    */   public TileEntityDropper getTileEntity()
/*    */   {
/* 59 */     return this.dropper;
/*    */   }
/*    */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\org\bukkit\craftbukkit\v1_8_R3\block\CraftDropper.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */
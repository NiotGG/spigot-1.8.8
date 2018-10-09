/*    */ package org.bukkit.craftbukkit.v1_8_R3.block;
/*    */ 
/*    */ import net.minecraft.server.v1_8_R3.TileEntityHopper;
/*    */ import org.bukkit.Material;
/*    */ import org.bukkit.block.Block;
/*    */ import org.bukkit.craftbukkit.v1_8_R3.CraftWorld;
/*    */ import org.bukkit.inventory.Inventory;
/*    */ 
/*    */ public class CraftHopper extends CraftBlockState implements org.bukkit.block.Hopper
/*    */ {
/*    */   private final TileEntityHopper hopper;
/*    */   
/*    */   public CraftHopper(Block block)
/*    */   {
/* 15 */     super(block);
/*    */     
/* 17 */     this.hopper = ((TileEntityHopper)((CraftWorld)block.getWorld()).getTileEntityAt(getX(), getY(), getZ()));
/*    */   }
/*    */   
/*    */   public CraftHopper(Material material, TileEntityHopper te) {
/* 21 */     super(material);
/*    */     
/* 23 */     this.hopper = te;
/*    */   }
/*    */   
/*    */   public Inventory getInventory() {
/* 27 */     return new org.bukkit.craftbukkit.v1_8_R3.inventory.CraftInventory(this.hopper);
/*    */   }
/*    */   
/*    */   public boolean update(boolean force, boolean applyPhysics)
/*    */   {
/* 32 */     boolean result = super.update(force, applyPhysics);
/*    */     
/* 34 */     if (result) {
/* 35 */       this.hopper.update();
/*    */     }
/*    */     
/* 38 */     return result;
/*    */   }
/*    */   
/*    */   public TileEntityHopper getTileEntity()
/*    */   {
/* 43 */     return this.hopper;
/*    */   }
/*    */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\org\bukkit\craftbukkit\v1_8_R3\block\CraftHopper.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */
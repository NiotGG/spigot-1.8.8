/*    */ package org.bukkit.craftbukkit.v1_8_R3.block;
/*    */ 
/*    */ import net.minecraft.server.v1_8_R3.TileEntityBrewingStand;
/*    */ import org.bukkit.Material;
/*    */ import org.bukkit.block.Block;
/*    */ import org.bukkit.block.BrewingStand;
/*    */ import org.bukkit.craftbukkit.v1_8_R3.CraftWorld;
/*    */ 
/*    */ public class CraftBrewingStand extends CraftBlockState implements BrewingStand
/*    */ {
/*    */   private final TileEntityBrewingStand brewingStand;
/*    */   
/*    */   public CraftBrewingStand(Block block)
/*    */   {
/* 15 */     super(block);
/*    */     
/* 17 */     this.brewingStand = ((TileEntityBrewingStand)((CraftWorld)block.getWorld()).getTileEntityAt(getX(), getY(), getZ()));
/*    */   }
/*    */   
/*    */   public CraftBrewingStand(Material material, TileEntityBrewingStand te) {
/* 21 */     super(material);
/* 22 */     this.brewingStand = te;
/*    */   }
/*    */   
/*    */   public org.bukkit.inventory.BrewerInventory getInventory() {
/* 26 */     return new org.bukkit.craftbukkit.v1_8_R3.inventory.CraftInventoryBrewer(this.brewingStand);
/*    */   }
/*    */   
/*    */   public boolean update(boolean force, boolean applyPhysics)
/*    */   {
/* 31 */     boolean result = super.update(force, applyPhysics);
/*    */     
/* 33 */     if (result) {
/* 34 */       this.brewingStand.update();
/*    */     }
/*    */     
/* 37 */     return result;
/*    */   }
/*    */   
/*    */   public int getBrewingTime() {
/* 41 */     return this.brewingStand.brewTime;
/*    */   }
/*    */   
/*    */   public void setBrewingTime(int brewTime) {
/* 45 */     this.brewingStand.brewTime = brewTime;
/*    */   }
/*    */   
/*    */   public TileEntityBrewingStand getTileEntity()
/*    */   {
/* 50 */     return this.brewingStand;
/*    */   }
/*    */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\org\bukkit\craftbukkit\v1_8_R3\block\CraftBrewingStand.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */
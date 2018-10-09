/*    */ package org.bukkit.craftbukkit.v1_8_R3.block;
/*    */ 
/*    */ import net.minecraft.server.v1_8_R3.TileEntityFurnace;
/*    */ import org.bukkit.Material;
/*    */ import org.bukkit.block.Block;
/*    */ import org.bukkit.craftbukkit.v1_8_R3.CraftWorld;
/*    */ import org.bukkit.craftbukkit.v1_8_R3.inventory.CraftInventoryFurnace;
/*    */ 
/*    */ public class CraftFurnace extends CraftBlockState implements org.bukkit.block.Furnace
/*    */ {
/*    */   private final TileEntityFurnace furnace;
/*    */   
/*    */   public CraftFurnace(Block block)
/*    */   {
/* 15 */     super(block);
/*    */     
/* 17 */     this.furnace = ((TileEntityFurnace)((CraftWorld)block.getWorld()).getTileEntityAt(getX(), getY(), getZ()));
/*    */   }
/*    */   
/*    */   public CraftFurnace(Material material, TileEntityFurnace te) {
/* 21 */     super(material);
/* 22 */     this.furnace = te;
/*    */   }
/*    */   
/*    */   public org.bukkit.inventory.FurnaceInventory getInventory() {
/* 26 */     return new CraftInventoryFurnace(this.furnace);
/*    */   }
/*    */   
/*    */   public boolean update(boolean force, boolean applyPhysics)
/*    */   {
/* 31 */     boolean result = super.update(force, applyPhysics);
/*    */     
/* 33 */     if (result) {
/* 34 */       this.furnace.update();
/*    */     }
/*    */     
/* 37 */     return result;
/*    */   }
/*    */   
/*    */   public short getBurnTime() {
/* 41 */     return (short)this.furnace.burnTime;
/*    */   }
/*    */   
/*    */   public void setBurnTime(short burnTime) {
/* 45 */     this.furnace.burnTime = burnTime;
/*    */   }
/*    */   
/*    */   public short getCookTime() {
/* 49 */     return (short)this.furnace.cookTime;
/*    */   }
/*    */   
/*    */   public void setCookTime(short cookTime) {
/* 53 */     this.furnace.cookTime = cookTime;
/*    */   }
/*    */   
/*    */   public TileEntityFurnace getTileEntity()
/*    */   {
/* 58 */     return this.furnace;
/*    */   }
/*    */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\org\bukkit\craftbukkit\v1_8_R3\block\CraftFurnace.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */
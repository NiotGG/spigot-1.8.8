/*    */ package org.bukkit.craftbukkit.v1_8_R3.block;
/*    */ 
/*    */ import net.minecraft.server.v1_8_R3.BlockDispenser;
/*    */ import net.minecraft.server.v1_8_R3.BlockPosition;
/*    */ import net.minecraft.server.v1_8_R3.Blocks;
/*    */ import net.minecraft.server.v1_8_R3.TileEntityDispenser;
/*    */ import org.bukkit.Material;
/*    */ import org.bukkit.block.Block;
/*    */ import org.bukkit.block.Dispenser;
/*    */ import org.bukkit.craftbukkit.v1_8_R3.CraftWorld;
/*    */ import org.bukkit.craftbukkit.v1_8_R3.projectiles.CraftBlockProjectileSource;
/*    */ import org.bukkit.inventory.Inventory;
/*    */ import org.bukkit.projectiles.BlockProjectileSource;
/*    */ 
/*    */ public class CraftDispenser extends CraftBlockState implements Dispenser
/*    */ {
/*    */   private final CraftWorld world;
/*    */   private final TileEntityDispenser dispenser;
/*    */   
/*    */   public CraftDispenser(Block block)
/*    */   {
/* 22 */     super(block);
/*    */     
/* 24 */     this.world = ((CraftWorld)block.getWorld());
/* 25 */     this.dispenser = ((TileEntityDispenser)this.world.getTileEntityAt(getX(), getY(), getZ()));
/*    */   }
/*    */   
/*    */   public CraftDispenser(Material material, TileEntityDispenser te) {
/* 29 */     super(material);
/* 30 */     this.world = null;
/* 31 */     this.dispenser = te;
/*    */   }
/*    */   
/*    */   public Inventory getInventory() {
/* 35 */     return new org.bukkit.craftbukkit.v1_8_R3.inventory.CraftInventory(this.dispenser);
/*    */   }
/*    */   
/*    */   public BlockProjectileSource getBlockProjectileSource() {
/* 39 */     Block block = getBlock();
/*    */     
/* 41 */     if (block.getType() != Material.DISPENSER) {
/* 42 */       return null;
/*    */     }
/*    */     
/* 45 */     return new CraftBlockProjectileSource(this.dispenser);
/*    */   }
/*    */   
/*    */   public boolean dispense() {
/* 49 */     Block block = getBlock();
/*    */     
/* 51 */     if (block.getType() == Material.DISPENSER) {
/* 52 */       BlockDispenser dispense = (BlockDispenser)Blocks.DISPENSER;
/*    */       
/* 54 */       dispense.dispense(this.world.getHandle(), new BlockPosition(getX(), getY(), getZ()));
/* 55 */       return true;
/*    */     }
/* 57 */     return false;
/*    */   }
/*    */   
/*    */ 
/*    */   public boolean update(boolean force, boolean applyPhysics)
/*    */   {
/* 63 */     boolean result = super.update(force, applyPhysics);
/*    */     
/* 65 */     if (result) {
/* 66 */       this.dispenser.update();
/*    */     }
/*    */     
/* 69 */     return result;
/*    */   }
/*    */   
/*    */   public TileEntityDispenser getTileEntity()
/*    */   {
/* 74 */     return this.dispenser;
/*    */   }
/*    */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\org\bukkit\craftbukkit\v1_8_R3\block\CraftDispenser.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */
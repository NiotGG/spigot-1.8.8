/*    */ package org.bukkit.craftbukkit.v1_8_R3.block;
/*    */ 
/*    */ import net.minecraft.server.v1_8_R3.CommandBlockListenerAbstract;
/*    */ import net.minecraft.server.v1_8_R3.TileEntityCommand;
/*    */ import org.bukkit.block.Block;
/*    */ 
/*    */ public class CraftCommandBlock extends CraftBlockState implements org.bukkit.block.CommandBlock
/*    */ {
/*    */   private final TileEntityCommand commandBlock;
/*    */   private String command;
/*    */   private String name;
/*    */   
/*    */   public CraftCommandBlock(Block block)
/*    */   {
/* 15 */     super(block);
/*    */     
/* 17 */     org.bukkit.craftbukkit.v1_8_R3.CraftWorld world = (org.bukkit.craftbukkit.v1_8_R3.CraftWorld)block.getWorld();
/* 18 */     this.commandBlock = ((TileEntityCommand)world.getTileEntityAt(getX(), getY(), getZ()));
/* 19 */     this.command = this.commandBlock.getCommandBlock().getCommand();
/* 20 */     this.name = this.commandBlock.getCommandBlock().getName();
/*    */   }
/*    */   
/*    */   public CraftCommandBlock(org.bukkit.Material material, TileEntityCommand te) {
/* 24 */     super(material);
/* 25 */     this.commandBlock = te;
/* 26 */     this.command = this.commandBlock.getCommandBlock().getCommand();
/* 27 */     this.name = this.commandBlock.getCommandBlock().getName();
/*    */   }
/*    */   
/*    */   public String getCommand() {
/* 31 */     return this.command;
/*    */   }
/*    */   
/*    */   public void setCommand(String command) {
/* 35 */     this.command = (command != null ? command : "");
/*    */   }
/*    */   
/*    */   public String getName() {
/* 39 */     return this.name;
/*    */   }
/*    */   
/*    */   public void setName(String name) {
/* 43 */     this.name = (name != null ? name : "@");
/*    */   }
/*    */   
/*    */   public boolean update(boolean force, boolean applyPhysics) {
/* 47 */     boolean result = super.update(force, applyPhysics);
/*    */     
/* 49 */     if (result) {
/* 50 */       this.commandBlock.getCommandBlock().setCommand(this.command);
/* 51 */       this.commandBlock.getCommandBlock().setName(this.name);
/*    */     }
/*    */     
/* 54 */     return result;
/*    */   }
/*    */   
/*    */   public TileEntityCommand getTileEntity()
/*    */   {
/* 59 */     return this.commandBlock;
/*    */   }
/*    */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\org\bukkit\craftbukkit\v1_8_R3\block\CraftCommandBlock.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */
/*    */ package org.bukkit.craftbukkit.v1_8_R3.command;
/*    */ 
/*    */ import net.minecraft.server.v1_8_R3.BlockPosition;
/*    */ import net.minecraft.server.v1_8_R3.CommandBlockListenerAbstract;
/*    */ import net.minecraft.server.v1_8_R3.IChatBaseComponent;
/*    */ import net.minecraft.server.v1_8_R3.ICommandListener;
/*    */ import net.minecraft.server.v1_8_R3.World;
/*    */ import org.bukkit.block.Block;
/*    */ import org.bukkit.command.BlockCommandSender;
/*    */ import org.bukkit.craftbukkit.v1_8_R3.CraftWorld;
/*    */ import org.bukkit.craftbukkit.v1_8_R3.util.CraftChatMessage;
/*    */ 
/*    */ public class CraftBlockCommandSender extends ServerCommandSender implements BlockCommandSender
/*    */ {
/*    */   private final CommandBlockListenerAbstract commandBlock;
/*    */   
/*    */   public CraftBlockCommandSender(CommandBlockListenerAbstract commandBlockListenerAbstract)
/*    */   {
/* 19 */     this.commandBlock = commandBlockListenerAbstract;
/*    */   }
/*    */   
/*    */ 
/* 23 */   public Block getBlock() { return this.commandBlock.getWorld().getWorld().getBlockAt(this.commandBlock.getChunkCoordinates().getX(), this.commandBlock.getChunkCoordinates().getY(), this.commandBlock.getChunkCoordinates().getZ()); }
/*    */   
/*    */   public void sendMessage(String message) {
/*    */     IChatBaseComponent[] arrayOfIChatBaseComponent;
/* 27 */     int i = (arrayOfIChatBaseComponent = CraftChatMessage.fromString(message)).length; for (int j = 0; j < i; j++) { IChatBaseComponent component = arrayOfIChatBaseComponent[j];
/* 28 */       this.commandBlock.sendMessage(component);
/*    */     }
/*    */   }
/*    */   
/*    */   public void sendMessage(String[] messages) { String[] arrayOfString;
/* 33 */     int i = (arrayOfString = messages).length; for (int j = 0; j < i; j++) { String message = arrayOfString[j];
/* 34 */       sendMessage(message);
/*    */     }
/*    */   }
/*    */   
/*    */   public String getName() {
/* 39 */     return this.commandBlock.getName();
/*    */   }
/*    */   
/*    */   public boolean isOp() {
/* 43 */     return true;
/*    */   }
/*    */   
/*    */   public void setOp(boolean value) {
/* 47 */     throw new UnsupportedOperationException("Cannot change operator status of a block");
/*    */   }
/*    */   
/*    */   public ICommandListener getTileEntity() {
/* 51 */     return this.commandBlock;
/*    */   }
/*    */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\org\bukkit\craftbukkit\v1_8_R3\command\CraftBlockCommandSender.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */
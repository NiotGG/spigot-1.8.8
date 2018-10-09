/*    */ package net.minecraft.server.v1_8_R3;
/*    */ 
/*    */ import java.util.List;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class CommandBlockData
/*    */   extends CommandAbstract
/*    */ {
/*    */   public String getCommand()
/*    */   {
/* 21 */     return "blockdata";
/*    */   }
/*    */   
/*    */   public int a()
/*    */   {
/* 26 */     return 2;
/*    */   }
/*    */   
/*    */   public String getUsage(ICommandListener paramICommandListener)
/*    */   {
/* 31 */     return "commands.blockdata.usage";
/*    */   }
/*    */   
/*    */   public void execute(ICommandListener paramICommandListener, String[] paramArrayOfString) throws CommandException
/*    */   {
/* 36 */     if (paramArrayOfString.length < 4) {
/* 37 */       throw new ExceptionUsage("commands.blockdata.usage", new Object[0]);
/*    */     }
/* 39 */     paramICommandListener.a(CommandObjectiveExecutor.EnumCommandResult.AFFECTED_BLOCKS, 0);
/*    */     
/* 41 */     BlockPosition localBlockPosition = a(paramICommandListener, paramArrayOfString, 0, false);
/*    */     
/* 43 */     World localWorld = paramICommandListener.getWorld();
/* 44 */     if (!localWorld.isLoaded(localBlockPosition)) {
/* 45 */       throw new CommandException("commands.blockdata.outOfWorld", new Object[0]);
/*    */     }
/*    */     
/* 48 */     TileEntity localTileEntity = localWorld.getTileEntity(localBlockPosition);
/* 49 */     if (localTileEntity == null) {
/* 50 */       throw new CommandException("commands.blockdata.notValid", new Object[0]);
/*    */     }
/*    */     
/* 53 */     NBTTagCompound localNBTTagCompound1 = new NBTTagCompound();
/* 54 */     localTileEntity.b(localNBTTagCompound1);
/* 55 */     NBTTagCompound localNBTTagCompound2 = (NBTTagCompound)localNBTTagCompound1.clone();
/*    */     NBTTagCompound localNBTTagCompound3;
/*    */     try
/*    */     {
/* 59 */       localNBTTagCompound3 = MojangsonParser.parse(a(paramICommandListener, paramArrayOfString, 3).c());
/*    */     } catch (MojangsonParseException localMojangsonParseException) {
/* 61 */       throw new CommandException("commands.blockdata.tagError", new Object[] { localMojangsonParseException.getMessage() });
/*    */     }
/*    */     
/* 64 */     localNBTTagCompound1.a(localNBTTagCompound3);
/*    */     
/* 66 */     localNBTTagCompound1.setInt("x", localBlockPosition.getX());
/* 67 */     localNBTTagCompound1.setInt("y", localBlockPosition.getY());
/* 68 */     localNBTTagCompound1.setInt("z", localBlockPosition.getZ());
/*    */     
/* 70 */     if (localNBTTagCompound1.equals(localNBTTagCompound2)) {
/* 71 */       throw new CommandException("commands.blockdata.failed", new Object[] { localNBTTagCompound1.toString() });
/*    */     }
/*    */     
/* 74 */     localTileEntity.a(localNBTTagCompound1);
/* 75 */     localTileEntity.update();
/* 76 */     localWorld.notify(localBlockPosition);
/*    */     
/* 78 */     paramICommandListener.a(CommandObjectiveExecutor.EnumCommandResult.AFFECTED_BLOCKS, 1);
/* 79 */     a(paramICommandListener, this, "commands.blockdata.success", new Object[] { localNBTTagCompound1.toString() });
/*    */   }
/*    */   
/*    */ 
/*    */   public List<String> tabComplete(ICommandListener paramICommandListener, String[] paramArrayOfString, BlockPosition paramBlockPosition)
/*    */   {
/* 85 */     if ((paramArrayOfString.length > 0) && (paramArrayOfString.length <= 3)) {
/* 86 */       return a(paramArrayOfString, 0, paramBlockPosition);
/*    */     }
/* 88 */     return null;
/*    */   }
/*    */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_8_R3\CommandBlockData.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */
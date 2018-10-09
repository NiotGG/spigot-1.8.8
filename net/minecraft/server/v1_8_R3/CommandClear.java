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
/*    */ 
/*    */ public class CommandClear
/*    */   extends CommandAbstract
/*    */ {
/*    */   public String getCommand()
/*    */   {
/* 22 */     return "clear";
/*    */   }
/*    */   
/*    */   public String getUsage(ICommandListener paramICommandListener)
/*    */   {
/* 27 */     return "commands.clear.usage";
/*    */   }
/*    */   
/*    */   public int a()
/*    */   {
/* 32 */     return 2;
/*    */   }
/*    */   
/*    */   public void execute(ICommandListener paramICommandListener, String[] paramArrayOfString) throws CommandException
/*    */   {
/* 37 */     EntityPlayer localEntityPlayer = paramArrayOfString.length == 0 ? b(paramICommandListener) : a(paramICommandListener, paramArrayOfString[0]);
/* 38 */     Item localItem = paramArrayOfString.length >= 2 ? f(paramICommandListener, paramArrayOfString[1]) : null;
/* 39 */     int i = paramArrayOfString.length >= 3 ? a(paramArrayOfString[2], -1) : -1;
/* 40 */     int j = paramArrayOfString.length >= 4 ? a(paramArrayOfString[3], -1) : -1;
/*    */     
/* 42 */     NBTTagCompound localNBTTagCompound = null;
/* 43 */     if (paramArrayOfString.length >= 5) {
/*    */       try {
/* 45 */         localNBTTagCompound = MojangsonParser.parse(a(paramArrayOfString, 4));
/*    */       } catch (MojangsonParseException localMojangsonParseException) {
/* 47 */         throw new CommandException("commands.clear.tagError", new Object[] { localMojangsonParseException.getMessage() });
/*    */       }
/*    */     }
/*    */     
/* 51 */     if ((paramArrayOfString.length >= 2) && (localItem == null)) {
/* 52 */       throw new CommandException("commands.clear.failure", new Object[] { localEntityPlayer.getName() });
/*    */     }
/*    */     
/* 55 */     int k = localEntityPlayer.inventory.a(localItem, i, j, localNBTTagCompound);
/* 56 */     localEntityPlayer.defaultContainer.b();
/* 57 */     if (!localEntityPlayer.abilities.canInstantlyBuild) {
/* 58 */       localEntityPlayer.broadcastCarriedItem();
/*    */     }
/*    */     
/* 61 */     paramICommandListener.a(CommandObjectiveExecutor.EnumCommandResult.AFFECTED_ITEMS, k);
/* 62 */     if (k == 0) {
/* 63 */       throw new CommandException("commands.clear.failure", new Object[] { localEntityPlayer.getName() });
/*    */     }
/*    */     
/* 66 */     if (j == 0) {
/* 67 */       paramICommandListener.sendMessage(new ChatMessage("commands.clear.testing", new Object[] { localEntityPlayer.getName(), Integer.valueOf(k) }));
/*    */     } else {
/* 69 */       a(paramICommandListener, this, "commands.clear.success", new Object[] { localEntityPlayer.getName(), Integer.valueOf(k) });
/*    */     }
/*    */   }
/*    */   
/*    */ 
/*    */   public List<String> tabComplete(ICommandListener paramICommandListener, String[] paramArrayOfString, BlockPosition paramBlockPosition)
/*    */   {
/* 76 */     if (paramArrayOfString.length == 1) {
/* 77 */       return a(paramArrayOfString, d());
/*    */     }
/* 79 */     if (paramArrayOfString.length == 2) {
/* 80 */       return a(paramArrayOfString, Item.REGISTRY.keySet());
/*    */     }
/*    */     
/* 83 */     return null;
/*    */   }
/*    */   
/*    */   protected String[] d() {
/* 87 */     return MinecraftServer.getServer().getPlayers();
/*    */   }
/*    */   
/*    */   public boolean isListStart(String[] paramArrayOfString, int paramInt)
/*    */   {
/* 92 */     return paramInt == 0;
/*    */   }
/*    */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_8_R3\CommandClear.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */
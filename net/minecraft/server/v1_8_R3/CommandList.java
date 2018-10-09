/*    */ package net.minecraft.server.v1_8_R3;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class CommandList
/*    */   extends CommandAbstract
/*    */ {
/*    */   public String getCommand()
/*    */   {
/* 14 */     return "list";
/*    */   }
/*    */   
/*    */   public int a()
/*    */   {
/* 19 */     return 0;
/*    */   }
/*    */   
/*    */   public String getUsage(ICommandListener paramICommandListener)
/*    */   {
/* 24 */     return "commands.players.usage";
/*    */   }
/*    */   
/*    */   public void execute(ICommandListener paramICommandListener, String[] paramArrayOfString) throws CommandException
/*    */   {
/* 29 */     int i = MinecraftServer.getServer().I();
/* 30 */     paramICommandListener.sendMessage(new ChatMessage("commands.players.list", new Object[] { Integer.valueOf(i), Integer.valueOf(MinecraftServer.getServer().J()) }));
/* 31 */     paramICommandListener.sendMessage(new ChatComponentText(MinecraftServer.getServer().getPlayerList().b((paramArrayOfString.length > 0) && ("uuids".equalsIgnoreCase(paramArrayOfString[0])))));
/* 32 */     paramICommandListener.a(CommandObjectiveExecutor.EnumCommandResult.QUERY_RESULT, i);
/*    */   }
/*    */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_8_R3\CommandList.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */
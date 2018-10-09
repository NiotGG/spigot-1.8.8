/*    */ package net.minecraft.server.v1_8_R3;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class CommandGamemodeDefault
/*    */   extends CommandGamemode
/*    */ {
/*    */   public String getCommand()
/*    */   {
/* 14 */     return "defaultgamemode";
/*    */   }
/*    */   
/*    */   public String getUsage(ICommandListener paramICommandListener)
/*    */   {
/* 19 */     return "commands.defaultgamemode.usage";
/*    */   }
/*    */   
/*    */   public void execute(ICommandListener paramICommandListener, String[] paramArrayOfString) throws CommandException
/*    */   {
/* 24 */     if (paramArrayOfString.length <= 0) {
/* 25 */       throw new ExceptionUsage("commands.defaultgamemode.usage", new Object[0]);
/*    */     }
/*    */     
/* 28 */     WorldSettings.EnumGamemode localEnumGamemode = h(paramICommandListener, paramArrayOfString[0]);
/* 29 */     a(localEnumGamemode);
/*    */     
/* 31 */     a(paramICommandListener, this, "commands.defaultgamemode.success", new Object[] { new ChatMessage("gameMode." + localEnumGamemode.b(), new Object[0]) });
/*    */   }
/*    */   
/*    */   protected void a(WorldSettings.EnumGamemode paramEnumGamemode) {
/* 35 */     MinecraftServer localMinecraftServer = MinecraftServer.getServer();
/* 36 */     localMinecraftServer.setGamemode(paramEnumGamemode);
/*    */     
/* 38 */     if (localMinecraftServer.getForceGamemode()) {
/* 39 */       for (EntityPlayer localEntityPlayer : MinecraftServer.getServer().getPlayerList().v()) {
/* 40 */         localEntityPlayer.a(paramEnumGamemode);
/* 41 */         localEntityPlayer.fallDistance = 0.0F;
/*    */       }
/*    */     }
/*    */   }
/*    */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_8_R3\CommandGamemodeDefault.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */
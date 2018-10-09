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
/*    */ public class CommandBanList
/*    */   extends CommandAbstract
/*    */ {
/*    */   public String getCommand()
/*    */   {
/* 17 */     return "banlist";
/*    */   }
/*    */   
/*    */   public int a()
/*    */   {
/* 22 */     return 3;
/*    */   }
/*    */   
/*    */   public boolean canUse(ICommandListener paramICommandListener)
/*    */   {
/* 27 */     return ((MinecraftServer.getServer().getPlayerList().getIPBans().isEnabled()) || (MinecraftServer.getServer().getPlayerList().getProfileBans().isEnabled())) && (super.canUse(paramICommandListener));
/*    */   }
/*    */   
/*    */   public String getUsage(ICommandListener paramICommandListener)
/*    */   {
/* 32 */     return "commands.banlist.usage";
/*    */   }
/*    */   
/*    */   public void execute(ICommandListener paramICommandListener, String[] paramArrayOfString) throws CommandException
/*    */   {
/* 37 */     if ((paramArrayOfString.length >= 1) && (paramArrayOfString[0].equalsIgnoreCase("ips"))) {
/* 38 */       paramICommandListener.sendMessage(new ChatMessage("commands.banlist.ips", new Object[] { Integer.valueOf(MinecraftServer.getServer().getPlayerList().getIPBans().getEntries().length) }));
/* 39 */       paramICommandListener.sendMessage(new ChatComponentText(a(MinecraftServer.getServer().getPlayerList().getIPBans().getEntries())));
/*    */     } else {
/* 41 */       paramICommandListener.sendMessage(new ChatMessage("commands.banlist.players", new Object[] { Integer.valueOf(MinecraftServer.getServer().getPlayerList().getProfileBans().getEntries().length) }));
/* 42 */       paramICommandListener.sendMessage(new ChatComponentText(a(MinecraftServer.getServer().getPlayerList().getProfileBans().getEntries())));
/*    */     }
/*    */   }
/*    */   
/*    */ 
/*    */   public List<String> tabComplete(ICommandListener paramICommandListener, String[] paramArrayOfString, BlockPosition paramBlockPosition)
/*    */   {
/* 49 */     if (paramArrayOfString.length == 1) {
/* 50 */       return a(paramArrayOfString, new String[] { "players", "ips" });
/*    */     }
/*    */     
/* 53 */     return null;
/*    */   }
/*    */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_8_R3\CommandBanList.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */
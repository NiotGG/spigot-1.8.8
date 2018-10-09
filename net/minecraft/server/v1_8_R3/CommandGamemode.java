/*    */ package net.minecraft.server.v1_8_R3;
/*    */ 
/*    */ import java.util.List;
/*    */ 
/*    */ public class CommandGamemode
/*    */   extends CommandAbstract
/*    */ {
/*    */   public String getCommand()
/*    */   {
/* 10 */     return "gamemode";
/*    */   }
/*    */   
/*    */   public int a() {
/* 14 */     return 2;
/*    */   }
/*    */   
/*    */   public String getUsage(ICommandListener icommandlistener) {
/* 18 */     return "commands.gamemode.usage";
/*    */   }
/*    */   
/*    */   public void execute(ICommandListener icommandlistener, String[] astring) throws CommandException {
/* 22 */     if (astring.length <= 0) {
/* 23 */       throw new ExceptionUsage("commands.gamemode.usage", new Object[0]);
/*    */     }
/* 25 */     WorldSettings.EnumGamemode worldsettings_enumgamemode = h(icommandlistener, astring[0]);
/* 26 */     EntityPlayer entityplayer = astring.length >= 2 ? a(icommandlistener, astring[1]) : b(icommandlistener);
/*    */     
/* 28 */     entityplayer.a(worldsettings_enumgamemode);
/*    */     
/* 30 */     if (entityplayer.playerInteractManager.getGameMode() != worldsettings_enumgamemode) {
/* 31 */       icommandlistener.sendMessage(new ChatComponentText("Failed to set the gamemode of '" + entityplayer.getName() + "'"));
/* 32 */       return;
/*    */     }
/*    */     
/*    */ 
/* 36 */     entityplayer.fallDistance = 0.0F;
/* 37 */     if (icommandlistener.getWorld().getGameRules().getBoolean("sendCommandFeedback")) {
/* 38 */       entityplayer.sendMessage(new ChatMessage("gameMode.changed", new Object[0]));
/*    */     }
/*    */     
/* 41 */     ChatMessage chatmessage = new ChatMessage("gameMode." + worldsettings_enumgamemode.b(), new Object[0]);
/*    */     
/* 43 */     if (entityplayer != icommandlistener) {
/* 44 */       a(icommandlistener, this, 1, "commands.gamemode.success.other", new Object[] { entityplayer.getName(), chatmessage });
/*    */     } else {
/* 46 */       a(icommandlistener, this, 1, "commands.gamemode.success.self", new Object[] { chatmessage });
/*    */     }
/*    */   }
/*    */   
/*    */   protected WorldSettings.EnumGamemode h(ICommandListener icommandlistener, String s)
/*    */     throws ExceptionInvalidNumber
/*    */   {
/* 53 */     return (!s.equalsIgnoreCase(WorldSettings.EnumGamemode.SURVIVAL.b())) && (!s.equalsIgnoreCase("s")) ? WorldSettings.EnumGamemode.CREATIVE : (!s.equalsIgnoreCase(WorldSettings.EnumGamemode.CREATIVE.b())) && (!s.equalsIgnoreCase("c")) ? WorldSettings.EnumGamemode.ADVENTURE : (!s.equalsIgnoreCase(WorldSettings.EnumGamemode.ADVENTURE.b())) && (!s.equalsIgnoreCase("a")) ? WorldSettings.EnumGamemode.SPECTATOR : (!s.equalsIgnoreCase(WorldSettings.EnumGamemode.SPECTATOR.b())) && (!s.equalsIgnoreCase("sp")) ? WorldSettings.a(a(s, 0, WorldSettings.EnumGamemode.values().length - 2)) : WorldSettings.EnumGamemode.SURVIVAL;
/*    */   }
/*    */   
/*    */   public List<String> tabComplete(ICommandListener icommandlistener, String[] astring, BlockPosition blockposition) {
/* 57 */     return astring.length == 2 ? a(astring, d()) : astring.length == 1 ? a(astring, new String[] { "survival", "creative", "adventure", "spectator" }) : null;
/*    */   }
/*    */   
/*    */   protected String[] d() {
/* 61 */     return MinecraftServer.getServer().getPlayers();
/*    */   }
/*    */   
/*    */   public boolean isListStart(String[] astring, int i) {
/* 65 */     return i == 1;
/*    */   }
/*    */   
/*    */ 
/*    */   public int compareTo(ICommand o)
/*    */   {
/* 71 */     return a(o);
/*    */   }
/*    */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_8_R3\CommandGamemode.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */
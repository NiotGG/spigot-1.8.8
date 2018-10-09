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
/*    */ public class CommandSay
/*    */   extends CommandAbstract
/*    */ {
/*    */   public String getCommand()
/*    */   {
/* 18 */     return "say";
/*    */   }
/*    */   
/*    */   public int a()
/*    */   {
/* 23 */     return 1;
/*    */   }
/*    */   
/*    */   public String getUsage(ICommandListener paramICommandListener)
/*    */   {
/* 28 */     return "commands.say.usage";
/*    */   }
/*    */   
/*    */   public void execute(ICommandListener paramICommandListener, String[] paramArrayOfString) throws CommandException
/*    */   {
/* 33 */     if ((paramArrayOfString.length <= 0) || (paramArrayOfString[0].length() <= 0)) {
/* 34 */       throw new ExceptionUsage("commands.say.usage", new Object[0]);
/*    */     }
/*    */     
/* 37 */     IChatBaseComponent localIChatBaseComponent = b(paramICommandListener, paramArrayOfString, 0, true);
/* 38 */     MinecraftServer.getServer().getPlayerList().sendMessage(new ChatMessage("chat.type.announcement", new Object[] { paramICommandListener.getScoreboardDisplayName(), localIChatBaseComponent }));
/*    */   }
/*    */   
/*    */ 
/*    */   public List<String> tabComplete(ICommandListener paramICommandListener, String[] paramArrayOfString, BlockPosition paramBlockPosition)
/*    */   {
/* 44 */     if (paramArrayOfString.length >= 1) {
/* 45 */       return a(paramArrayOfString, MinecraftServer.getServer().getPlayers());
/*    */     }
/*    */     
/* 48 */     return null;
/*    */   }
/*    */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_8_R3\CommandSay.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */
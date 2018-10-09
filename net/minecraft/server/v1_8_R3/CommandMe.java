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
/*    */ public class CommandMe
/*    */   extends CommandAbstract
/*    */ {
/*    */   public String getCommand()
/*    */   {
/* 19 */     return "me";
/*    */   }
/*    */   
/*    */   public int a()
/*    */   {
/* 24 */     return 0;
/*    */   }
/*    */   
/*    */   public String getUsage(ICommandListener paramICommandListener)
/*    */   {
/* 29 */     return "commands.me.usage";
/*    */   }
/*    */   
/*    */   public void execute(ICommandListener paramICommandListener, String[] paramArrayOfString) throws CommandException
/*    */   {
/* 34 */     if (paramArrayOfString.length <= 0) {
/* 35 */       throw new ExceptionUsage("commands.me.usage", new Object[0]);
/*    */     }
/*    */     
/* 38 */     IChatBaseComponent localIChatBaseComponent = b(paramICommandListener, paramArrayOfString, 0, !(paramICommandListener instanceof EntityHuman));
/* 39 */     MinecraftServer.getServer().getPlayerList().sendMessage(new ChatMessage("chat.type.emote", new Object[] { paramICommandListener.getScoreboardDisplayName(), localIChatBaseComponent }));
/*    */   }
/*    */   
/*    */ 
/*    */   public List<String> tabComplete(ICommandListener paramICommandListener, String[] paramArrayOfString, BlockPosition paramBlockPosition)
/*    */   {
/* 45 */     return a(paramArrayOfString, MinecraftServer.getServer().getPlayers());
/*    */   }
/*    */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_8_R3\CommandMe.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */
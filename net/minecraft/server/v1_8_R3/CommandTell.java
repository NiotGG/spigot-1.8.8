/*    */ package net.minecraft.server.v1_8_R3;
/*    */ 
/*    */ import java.util.Arrays;
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
/*    */ public class CommandTell
/*    */   extends CommandAbstract
/*    */ {
/*    */   public List<String> b()
/*    */   {
/* 22 */     return Arrays.asList(new String[] { "w", "msg" });
/*    */   }
/*    */   
/*    */   public String getCommand()
/*    */   {
/* 27 */     return "tell";
/*    */   }
/*    */   
/*    */   public int a()
/*    */   {
/* 32 */     return 0;
/*    */   }
/*    */   
/*    */   public String getUsage(ICommandListener paramICommandListener)
/*    */   {
/* 37 */     return "commands.message.usage";
/*    */   }
/*    */   
/*    */   public void execute(ICommandListener paramICommandListener, String[] paramArrayOfString) throws CommandException
/*    */   {
/* 42 */     if (paramArrayOfString.length < 2) {
/* 43 */       throw new ExceptionUsage("commands.message.usage", new Object[0]);
/*    */     }
/*    */     
/* 46 */     EntityPlayer localEntityPlayer = a(paramICommandListener, paramArrayOfString[0]);
/* 47 */     if (localEntityPlayer == paramICommandListener) {
/* 48 */       throw new ExceptionPlayerNotFound("commands.message.sameTarget", new Object[0]);
/*    */     }
/*    */     
/* 51 */     IChatBaseComponent localIChatBaseComponent = b(paramICommandListener, paramArrayOfString, 1, !(paramICommandListener instanceof EntityHuman));
/* 52 */     ChatMessage localChatMessage1 = new ChatMessage("commands.message.display.incoming", new Object[] { paramICommandListener.getScoreboardDisplayName(), localIChatBaseComponent.f() });
/* 53 */     ChatMessage localChatMessage2 = new ChatMessage("commands.message.display.outgoing", new Object[] { localEntityPlayer.getScoreboardDisplayName(), localIChatBaseComponent.f() });
/* 54 */     localChatMessage1.getChatModifier().setColor(EnumChatFormat.GRAY).setItalic(Boolean.valueOf(true));
/* 55 */     localChatMessage2.getChatModifier().setColor(EnumChatFormat.GRAY).setItalic(Boolean.valueOf(true));
/* 56 */     localEntityPlayer.sendMessage(localChatMessage1);
/* 57 */     paramICommandListener.sendMessage(localChatMessage2);
/*    */   }
/*    */   
/*    */ 
/*    */   public List<String> tabComplete(ICommandListener paramICommandListener, String[] paramArrayOfString, BlockPosition paramBlockPosition)
/*    */   {
/* 63 */     return a(paramArrayOfString, MinecraftServer.getServer().getPlayers());
/*    */   }
/*    */   
/*    */   public boolean isListStart(String[] paramArrayOfString, int paramInt)
/*    */   {
/* 68 */     return paramInt == 0;
/*    */   }
/*    */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_8_R3\CommandTell.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */
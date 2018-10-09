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
/*    */ public class CommandKick
/*    */   extends CommandAbstract
/*    */ {
/*    */   public String getCommand()
/*    */   {
/* 18 */     return "kick";
/*    */   }
/*    */   
/*    */   public int a()
/*    */   {
/* 23 */     return 3;
/*    */   }
/*    */   
/*    */   public String getUsage(ICommandListener paramICommandListener)
/*    */   {
/* 28 */     return "commands.kick.usage";
/*    */   }
/*    */   
/*    */   public void execute(ICommandListener paramICommandListener, String[] paramArrayOfString) throws CommandException
/*    */   {
/* 33 */     if ((paramArrayOfString.length <= 0) || (paramArrayOfString[0].length() <= 1)) {
/* 34 */       throw new ExceptionUsage("commands.kick.usage", new Object[0]);
/*    */     }
/*    */     
/* 37 */     EntityPlayer localEntityPlayer = MinecraftServer.getServer().getPlayerList().getPlayer(paramArrayOfString[0]);
/* 38 */     String str = "Kicked by an operator.";
/* 39 */     int i = 0;
/*    */     
/* 41 */     if (localEntityPlayer == null) {
/* 42 */       throw new ExceptionPlayerNotFound();
/*    */     }
/*    */     
/* 45 */     if (paramArrayOfString.length >= 2) {
/* 46 */       str = a(paramICommandListener, paramArrayOfString, 1).c();
/* 47 */       i = 1;
/*    */     }
/*    */     
/* 50 */     localEntityPlayer.playerConnection.disconnect(str);
/* 51 */     if (i != 0) {
/* 52 */       a(paramICommandListener, this, "commands.kick.success.reason", new Object[] { localEntityPlayer.getName(), str });
/*    */     } else {
/* 54 */       a(paramICommandListener, this, "commands.kick.success", new Object[] { localEntityPlayer.getName() });
/*    */     }
/*    */   }
/*    */   
/*    */ 
/*    */   public List<String> tabComplete(ICommandListener paramICommandListener, String[] paramArrayOfString, BlockPosition paramBlockPosition)
/*    */   {
/* 61 */     if (paramArrayOfString.length >= 1) {
/* 62 */       return a(paramArrayOfString, MinecraftServer.getServer().getPlayers());
/*    */     }
/*    */     
/* 65 */     return null;
/*    */   }
/*    */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_8_R3\CommandKick.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */
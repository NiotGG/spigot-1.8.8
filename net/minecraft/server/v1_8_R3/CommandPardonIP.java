/*    */ package net.minecraft.server.v1_8_R3;
/*    */ 
/*    */ import java.util.List;
/*    */ import java.util.regex.Matcher;
/*    */ import java.util.regex.Pattern;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class CommandPardonIP
/*    */   extends CommandAbstract
/*    */ {
/*    */   public String getCommand()
/*    */   {
/* 18 */     return "pardon-ip";
/*    */   }
/*    */   
/*    */   public int a()
/*    */   {
/* 23 */     return 3;
/*    */   }
/*    */   
/*    */   public boolean canUse(ICommandListener paramICommandListener)
/*    */   {
/* 28 */     return (MinecraftServer.getServer().getPlayerList().getIPBans().isEnabled()) && (super.canUse(paramICommandListener));
/*    */   }
/*    */   
/*    */   public String getUsage(ICommandListener paramICommandListener)
/*    */   {
/* 33 */     return "commands.unbanip.usage";
/*    */   }
/*    */   
/*    */   public void execute(ICommandListener paramICommandListener, String[] paramArrayOfString) throws CommandException
/*    */   {
/* 38 */     if ((paramArrayOfString.length != 1) || (paramArrayOfString[0].length() <= 1)) {
/* 39 */       throw new ExceptionUsage("commands.unbanip.usage", new Object[0]);
/*    */     }
/*    */     
/* 42 */     Matcher localMatcher = CommandBanIp.a.matcher(paramArrayOfString[0]);
/* 43 */     if (localMatcher.matches()) {
/* 44 */       MinecraftServer.getServer().getPlayerList().getIPBans().remove(paramArrayOfString[0]);
/* 45 */       a(paramICommandListener, this, "commands.unbanip.success", new Object[] { paramArrayOfString[0] });
/*    */     } else {
/* 47 */       throw new ExceptionInvalidSyntax("commands.unbanip.invalid", new Object[0]);
/*    */     }
/*    */   }
/*    */   
/*    */ 
/*    */   public List<String> tabComplete(ICommandListener paramICommandListener, String[] paramArrayOfString, BlockPosition paramBlockPosition)
/*    */   {
/* 54 */     if (paramArrayOfString.length == 1) {
/* 55 */       return a(paramArrayOfString, MinecraftServer.getServer().getPlayerList().getIPBans().getEntries());
/*    */     }
/*    */     
/* 58 */     return null;
/*    */   }
/*    */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_8_R3\CommandPardonIP.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */
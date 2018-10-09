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
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class CommandBanIp
/*    */   extends CommandAbstract
/*    */ {
/* 20 */   public static final Pattern a = Pattern.compile("^([01]?\\d\\d?|2[0-4]\\d|25[0-5])\\.([01]?\\d\\d?|2[0-4]\\d|25[0-5])\\.([01]?\\d\\d?|2[0-4]\\d|25[0-5])\\.([01]?\\d\\d?|2[0-4]\\d|25[0-5])$");
/*    */   
/*    */   public String getCommand()
/*    */   {
/* 24 */     return "ban-ip";
/*    */   }
/*    */   
/*    */   public int a()
/*    */   {
/* 29 */     return 3;
/*    */   }
/*    */   
/*    */   public boolean canUse(ICommandListener paramICommandListener)
/*    */   {
/* 34 */     return (MinecraftServer.getServer().getPlayerList().getIPBans().isEnabled()) && (super.canUse(paramICommandListener));
/*    */   }
/*    */   
/*    */   public String getUsage(ICommandListener paramICommandListener)
/*    */   {
/* 39 */     return "commands.banip.usage";
/*    */   }
/*    */   
/*    */   public void execute(ICommandListener paramICommandListener, String[] paramArrayOfString) throws CommandException
/*    */   {
/* 44 */     if ((paramArrayOfString.length < 1) || (paramArrayOfString[0].length() <= 1)) {
/* 45 */       throw new ExceptionUsage("commands.banip.usage", new Object[0]);
/*    */     }
/*    */     
/* 48 */     Object localObject = paramArrayOfString.length >= 2 ? a(paramICommandListener, paramArrayOfString, 1) : null;
/*    */     
/* 50 */     Matcher localMatcher = a.matcher(paramArrayOfString[0]);
/* 51 */     if (localMatcher.matches()) {
/* 52 */       a(paramICommandListener, paramArrayOfString[0], localObject == null ? null : ((IChatBaseComponent)localObject).c());
/*    */     } else {
/* 54 */       EntityPlayer localEntityPlayer = MinecraftServer.getServer().getPlayerList().getPlayer(paramArrayOfString[0]);
/* 55 */       if (localEntityPlayer == null) {
/* 56 */         throw new ExceptionPlayerNotFound("commands.banip.invalid", new Object[0]);
/*    */       }
/*    */       
/* 59 */       a(paramICommandListener, localEntityPlayer.w(), localObject == null ? null : ((IChatBaseComponent)localObject).c());
/*    */     }
/*    */   }
/*    */   
/*    */ 
/*    */   public List<String> tabComplete(ICommandListener paramICommandListener, String[] paramArrayOfString, BlockPosition paramBlockPosition)
/*    */   {
/* 66 */     if (paramArrayOfString.length == 1) {
/* 67 */       return a(paramArrayOfString, MinecraftServer.getServer().getPlayers());
/*    */     }
/*    */     
/* 70 */     return null;
/*    */   }
/*    */   
/*    */   protected void a(ICommandListener paramICommandListener, String paramString1, String paramString2) {
/* 74 */     IpBanEntry localIpBanEntry = new IpBanEntry(paramString1, null, paramICommandListener.getName(), null, paramString2);
/* 75 */     MinecraftServer.getServer().getPlayerList().getIPBans().add(localIpBanEntry);
/*    */     
/* 77 */     List localList = MinecraftServer.getServer().getPlayerList().b(paramString1);
/* 78 */     String[] arrayOfString = new String[localList.size()];
/* 79 */     int i = 0;
/*    */     
/* 81 */     for (EntityPlayer localEntityPlayer : localList) {
/* 82 */       localEntityPlayer.playerConnection.disconnect("You have been IP banned.");
/* 83 */       arrayOfString[(i++)] = localEntityPlayer.getName();
/*    */     }
/*    */     
/* 86 */     if (localList.isEmpty()) {
/* 87 */       a(paramICommandListener, this, "commands.banip.success", new Object[] { paramString1 });
/*    */     } else {
/* 89 */       a(paramICommandListener, this, "commands.banip.success.players", new Object[] { paramString1, a(arrayOfString) });
/*    */     }
/*    */   }
/*    */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_8_R3\CommandBanIp.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */
/*    */ package net.minecraft.server.v1_8_R3;
/*    */ 
/*    */ import com.mojang.authlib.GameProfile;
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
/*    */ public class CommandBan
/*    */   extends CommandAbstract
/*    */ {
/*    */   public String getCommand()
/*    */   {
/* 19 */     return "ban";
/*    */   }
/*    */   
/*    */   public int a()
/*    */   {
/* 24 */     return 3;
/*    */   }
/*    */   
/*    */   public String getUsage(ICommandListener paramICommandListener)
/*    */   {
/* 29 */     return "commands.ban.usage";
/*    */   }
/*    */   
/*    */   public boolean canUse(ICommandListener paramICommandListener)
/*    */   {
/* 34 */     return (MinecraftServer.getServer().getPlayerList().getProfileBans().isEnabled()) && (super.canUse(paramICommandListener));
/*    */   }
/*    */   
/*    */   public void execute(ICommandListener paramICommandListener, String[] paramArrayOfString) throws CommandException
/*    */   {
/* 39 */     if ((paramArrayOfString.length < 1) || (paramArrayOfString[0].length() <= 0)) {
/* 40 */       throw new ExceptionUsage("commands.ban.usage", new Object[0]);
/*    */     }
/*    */     
/* 43 */     MinecraftServer localMinecraftServer = MinecraftServer.getServer();
/* 44 */     GameProfile localGameProfile = localMinecraftServer.getUserCache().getProfile(paramArrayOfString[0]);
/* 45 */     if (localGameProfile == null) {
/* 46 */       throw new CommandException("commands.ban.failed", new Object[] { paramArrayOfString[0] });
/*    */     }
/*    */     
/* 49 */     String str = null;
/* 50 */     if (paramArrayOfString.length >= 2) {
/* 51 */       str = a(paramICommandListener, paramArrayOfString, 1).c();
/*    */     }
/*    */     
/* 54 */     GameProfileBanEntry localGameProfileBanEntry = new GameProfileBanEntry(localGameProfile, null, paramICommandListener.getName(), null, str);
/* 55 */     localMinecraftServer.getPlayerList().getProfileBans().add(localGameProfileBanEntry);
/*    */     
/* 57 */     EntityPlayer localEntityPlayer = localMinecraftServer.getPlayerList().getPlayer(paramArrayOfString[0]);
/* 58 */     if (localEntityPlayer != null) {
/* 59 */       localEntityPlayer.playerConnection.disconnect("You are banned from this server.");
/*    */     }
/*    */     
/* 62 */     a(paramICommandListener, this, "commands.ban.success", new Object[] { paramArrayOfString[0] });
/*    */   }
/*    */   
/*    */ 
/*    */   public List<String> tabComplete(ICommandListener paramICommandListener, String[] paramArrayOfString, BlockPosition paramBlockPosition)
/*    */   {
/* 68 */     if (paramArrayOfString.length >= 1) {
/* 69 */       return a(paramArrayOfString, MinecraftServer.getServer().getPlayers());
/*    */     }
/*    */     
/* 72 */     return null;
/*    */   }
/*    */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_8_R3\CommandBan.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */
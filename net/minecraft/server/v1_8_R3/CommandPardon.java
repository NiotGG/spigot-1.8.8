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
/*    */ public class CommandPardon
/*    */   extends CommandAbstract
/*    */ {
/*    */   public String getCommand()
/*    */   {
/* 17 */     return "pardon";
/*    */   }
/*    */   
/*    */   public int a()
/*    */   {
/* 22 */     return 3;
/*    */   }
/*    */   
/*    */   public String getUsage(ICommandListener paramICommandListener)
/*    */   {
/* 27 */     return "commands.unban.usage";
/*    */   }
/*    */   
/*    */   public boolean canUse(ICommandListener paramICommandListener)
/*    */   {
/* 32 */     return (MinecraftServer.getServer().getPlayerList().getProfileBans().isEnabled()) && (super.canUse(paramICommandListener));
/*    */   }
/*    */   
/*    */   public void execute(ICommandListener paramICommandListener, String[] paramArrayOfString) throws CommandException
/*    */   {
/* 37 */     if ((paramArrayOfString.length != 1) || (paramArrayOfString[0].length() <= 0)) {
/* 38 */       throw new ExceptionUsage("commands.unban.usage", new Object[0]);
/*    */     }
/*    */     
/* 41 */     MinecraftServer localMinecraftServer = MinecraftServer.getServer();
/* 42 */     GameProfile localGameProfile = localMinecraftServer.getPlayerList().getProfileBans().a(paramArrayOfString[0]);
/* 43 */     if (localGameProfile == null) {
/* 44 */       throw new CommandException("commands.unban.failed", new Object[] { paramArrayOfString[0] });
/*    */     }
/*    */     
/* 47 */     localMinecraftServer.getPlayerList().getProfileBans().remove(localGameProfile);
/* 48 */     a(paramICommandListener, this, "commands.unban.success", new Object[] { paramArrayOfString[0] });
/*    */   }
/*    */   
/*    */ 
/*    */   public List<String> tabComplete(ICommandListener paramICommandListener, String[] paramArrayOfString, BlockPosition paramBlockPosition)
/*    */   {
/* 54 */     if (paramArrayOfString.length == 1) {
/* 55 */       return a(paramArrayOfString, MinecraftServer.getServer().getPlayerList().getProfileBans().getEntries());
/*    */     }
/*    */     
/* 58 */     return null;
/*    */   }
/*    */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_8_R3\CommandPardon.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */
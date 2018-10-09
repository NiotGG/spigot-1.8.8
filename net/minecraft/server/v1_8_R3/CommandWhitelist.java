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
/*    */ public class CommandWhitelist
/*    */   extends CommandAbstract
/*    */ {
/*    */   public String getCommand()
/*    */   {
/* 19 */     return "whitelist";
/*    */   }
/*    */   
/*    */   public int a()
/*    */   {
/* 24 */     return 3;
/*    */   }
/*    */   
/*    */   public String getUsage(ICommandListener paramICommandListener)
/*    */   {
/* 29 */     return "commands.whitelist.usage";
/*    */   }
/*    */   
/*    */   public void execute(ICommandListener paramICommandListener, String[] paramArrayOfString) throws CommandException
/*    */   {
/* 34 */     if (paramArrayOfString.length < 1) {
/* 35 */       throw new ExceptionUsage("commands.whitelist.usage", new Object[0]);
/*    */     }
/*    */     
/* 38 */     MinecraftServer localMinecraftServer = MinecraftServer.getServer();
/* 39 */     if (paramArrayOfString[0].equals("on")) {
/* 40 */       localMinecraftServer.getPlayerList().setHasWhitelist(true);
/* 41 */       a(paramICommandListener, this, "commands.whitelist.enabled", new Object[0]);
/* 42 */     } else if (paramArrayOfString[0].equals("off")) {
/* 43 */       localMinecraftServer.getPlayerList().setHasWhitelist(false);
/* 44 */       a(paramICommandListener, this, "commands.whitelist.disabled", new Object[0]); } else { Object localObject;
/* 45 */       if (paramArrayOfString[0].equals("list")) {
/* 46 */         paramICommandListener.sendMessage(new ChatMessage("commands.whitelist.list", new Object[] { Integer.valueOf(localMinecraftServer.getPlayerList().getWhitelisted().length), Integer.valueOf(localMinecraftServer.getPlayerList().getSeenPlayers().length) }));
/* 47 */         localObject = localMinecraftServer.getPlayerList().getWhitelisted();
/* 48 */         paramICommandListener.sendMessage(new ChatComponentText(a((Object[])localObject)));
/* 49 */       } else if (paramArrayOfString[0].equals("add")) {
/* 50 */         if (paramArrayOfString.length < 2) {
/* 51 */           throw new ExceptionUsage("commands.whitelist.add.usage", new Object[0]);
/*    */         }
/*    */         
/* 54 */         localObject = localMinecraftServer.getUserCache().getProfile(paramArrayOfString[1]);
/* 55 */         if (localObject == null) {
/* 56 */           throw new CommandException("commands.whitelist.add.failed", new Object[] { paramArrayOfString[1] });
/*    */         }
/* 58 */         localMinecraftServer.getPlayerList().addWhitelist((GameProfile)localObject);
/* 59 */         a(paramICommandListener, this, "commands.whitelist.add.success", new Object[] { paramArrayOfString[1] });
/* 60 */       } else if (paramArrayOfString[0].equals("remove")) {
/* 61 */         if (paramArrayOfString.length < 2) {
/* 62 */           throw new ExceptionUsage("commands.whitelist.remove.usage", new Object[0]);
/*    */         }
/*    */         
/* 65 */         localObject = localMinecraftServer.getPlayerList().getWhitelist().a(paramArrayOfString[1]);
/* 66 */         if (localObject == null) {
/* 67 */           throw new CommandException("commands.whitelist.remove.failed", new Object[] { paramArrayOfString[1] });
/*    */         }
/* 69 */         localMinecraftServer.getPlayerList().removeWhitelist((GameProfile)localObject);
/* 70 */         a(paramICommandListener, this, "commands.whitelist.remove.success", new Object[] { paramArrayOfString[1] });
/* 71 */       } else if (paramArrayOfString[0].equals("reload")) {
/* 72 */         localMinecraftServer.getPlayerList().reloadWhitelist();
/* 73 */         a(paramICommandListener, this, "commands.whitelist.reloaded", new Object[0]);
/*    */       }
/*    */     }
/*    */   }
/*    */   
/*    */   public List<String> tabComplete(ICommandListener paramICommandListener, String[] paramArrayOfString, BlockPosition paramBlockPosition)
/*    */   {
/* 80 */     if (paramArrayOfString.length == 1) {
/* 81 */       return a(paramArrayOfString, new String[] { "on", "off", "list", "add", "remove", "reload" });
/*    */     }
/*    */     
/* 84 */     if (paramArrayOfString.length == 2) {
/* 85 */       if (paramArrayOfString[0].equals("remove"))
/* 86 */         return a(paramArrayOfString, MinecraftServer.getServer().getPlayerList().getWhitelisted());
/* 87 */       if (paramArrayOfString[0].equals("add")) {
/* 88 */         return a(paramArrayOfString, MinecraftServer.getServer().getUserCache().a());
/*    */       }
/*    */     }
/*    */     
/* 92 */     return null;
/*    */   }
/*    */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_8_R3\CommandWhitelist.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */
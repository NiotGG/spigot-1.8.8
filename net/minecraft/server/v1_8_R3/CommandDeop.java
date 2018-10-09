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
/*    */ public class CommandDeop
/*    */   extends CommandAbstract
/*    */ {
/*    */   public String getCommand()
/*    */   {
/* 17 */     return "deop";
/*    */   }
/*    */   
/*    */   public int a()
/*    */   {
/* 22 */     return 3;
/*    */   }
/*    */   
/*    */   public String getUsage(ICommandListener paramICommandListener)
/*    */   {
/* 27 */     return "commands.deop.usage";
/*    */   }
/*    */   
/*    */   public void execute(ICommandListener paramICommandListener, String[] paramArrayOfString) throws CommandException
/*    */   {
/* 32 */     if ((paramArrayOfString.length != 1) || (paramArrayOfString[0].length() <= 0)) {
/* 33 */       throw new ExceptionUsage("commands.deop.usage", new Object[0]);
/*    */     }
/*    */     
/* 36 */     MinecraftServer localMinecraftServer = MinecraftServer.getServer();
/* 37 */     GameProfile localGameProfile = localMinecraftServer.getPlayerList().getOPs().a(paramArrayOfString[0]);
/* 38 */     if (localGameProfile == null) {
/* 39 */       throw new CommandException("commands.deop.failed", new Object[] { paramArrayOfString[0] });
/*    */     }
/*    */     
/* 42 */     localMinecraftServer.getPlayerList().removeOp(localGameProfile);
/* 43 */     a(paramICommandListener, this, "commands.deop.success", new Object[] { paramArrayOfString[0] });
/*    */   }
/*    */   
/*    */ 
/*    */   public List<String> tabComplete(ICommandListener paramICommandListener, String[] paramArrayOfString, BlockPosition paramBlockPosition)
/*    */   {
/* 49 */     if (paramArrayOfString.length == 1) {
/* 50 */       return a(paramArrayOfString, MinecraftServer.getServer().getPlayerList().n());
/*    */     }
/*    */     
/* 53 */     return null;
/*    */   }
/*    */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_8_R3\CommandDeop.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */
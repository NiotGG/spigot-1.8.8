/*    */ package net.minecraft.server.v1_8_R3;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class CommandPublish
/*    */   extends CommandAbstract
/*    */ {
/*    */   public String getCommand()
/*    */   {
/* 12 */     return "publish";
/*    */   }
/*    */   
/*    */   public String getUsage(ICommandListener paramICommandListener)
/*    */   {
/* 17 */     return "commands.publish.usage";
/*    */   }
/*    */   
/*    */   public void execute(ICommandListener paramICommandListener, String[] paramArrayOfString) throws CommandException
/*    */   {
/* 22 */     String str = MinecraftServer.getServer().a(WorldSettings.EnumGamemode.SURVIVAL, false);
/* 23 */     if (str != null) {
/* 24 */       a(paramICommandListener, this, "commands.publish.started", new Object[] { str });
/*    */     } else {
/* 26 */       a(paramICommandListener, this, "commands.publish.failed", new Object[0]);
/*    */     }
/*    */   }
/*    */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_8_R3\CommandPublish.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */
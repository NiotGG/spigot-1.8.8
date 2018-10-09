/*    */ package net.minecraft.server.v1_8_R3;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class CommandStop
/*    */   extends CommandAbstract
/*    */ {
/*    */   public String getCommand()
/*    */   {
/* 11 */     return "stop";
/*    */   }
/*    */   
/*    */   public String getUsage(ICommandListener paramICommandListener)
/*    */   {
/* 16 */     return "commands.stop.usage";
/*    */   }
/*    */   
/*    */   public void execute(ICommandListener paramICommandListener, String[] paramArrayOfString) throws CommandException
/*    */   {
/* 21 */     if (MinecraftServer.getServer().worldServer != null) {
/* 22 */       a(paramICommandListener, this, "commands.stop.start", new Object[0]);
/*    */     }
/* 24 */     MinecraftServer.getServer().safeShutdown();
/*    */   }
/*    */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_8_R3\CommandStop.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */
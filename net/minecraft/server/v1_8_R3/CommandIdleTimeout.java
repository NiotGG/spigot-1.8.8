/*    */ package net.minecraft.server.v1_8_R3;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class CommandIdleTimeout
/*    */   extends CommandAbstract
/*    */ {
/*    */   public String getCommand()
/*    */   {
/* 12 */     return "setidletimeout";
/*    */   }
/*    */   
/*    */   public int a()
/*    */   {
/* 17 */     return 3;
/*    */   }
/*    */   
/*    */   public String getUsage(ICommandListener paramICommandListener)
/*    */   {
/* 22 */     return "commands.setidletimeout.usage";
/*    */   }
/*    */   
/*    */   public void execute(ICommandListener paramICommandListener, String[] paramArrayOfString) throws CommandException
/*    */   {
/* 27 */     if (paramArrayOfString.length != 1) {
/* 28 */       throw new ExceptionUsage("commands.setidletimeout.usage", new Object[0]);
/*    */     }
/*    */     
/* 31 */     int i = a(paramArrayOfString[0], 0);
/* 32 */     MinecraftServer.getServer().setIdleTimeout(i);
/* 33 */     a(paramICommandListener, this, "commands.setidletimeout.success", new Object[] { Integer.valueOf(i) });
/*    */   }
/*    */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_8_R3\CommandIdleTimeout.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */
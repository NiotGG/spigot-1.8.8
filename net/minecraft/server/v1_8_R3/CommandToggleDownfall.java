/*    */ package net.minecraft.server.v1_8_R3;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class CommandToggleDownfall
/*    */   extends CommandAbstract
/*    */ {
/*    */   public String getCommand()
/*    */   {
/* 12 */     return "toggledownfall";
/*    */   }
/*    */   
/*    */   public int a()
/*    */   {
/* 17 */     return 2;
/*    */   }
/*    */   
/*    */   public String getUsage(ICommandListener paramICommandListener)
/*    */   {
/* 22 */     return "commands.downfall.usage";
/*    */   }
/*    */   
/*    */   public void execute(ICommandListener paramICommandListener, String[] paramArrayOfString) throws CommandException
/*    */   {
/* 27 */     d();
/* 28 */     a(paramICommandListener, this, "commands.downfall.success", new Object[0]);
/*    */   }
/*    */   
/*    */   protected void d() {
/* 32 */     WorldData localWorldData = MinecraftServer.getServer().worldServer[0].getWorldData();
/*    */     
/* 34 */     localWorldData.setStorm(!localWorldData.hasStorm());
/*    */   }
/*    */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_8_R3\CommandToggleDownfall.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */
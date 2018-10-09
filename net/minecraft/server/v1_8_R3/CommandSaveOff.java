/*    */ package net.minecraft.server.v1_8_R3;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class CommandSaveOff
/*    */   extends CommandAbstract
/*    */ {
/*    */   public String getCommand()
/*    */   {
/* 12 */     return "save-off";
/*    */   }
/*    */   
/*    */   public String getUsage(ICommandListener paramICommandListener)
/*    */   {
/* 17 */     return "commands.save-off.usage";
/*    */   }
/*    */   
/*    */   public void execute(ICommandListener paramICommandListener, String[] paramArrayOfString) throws CommandException
/*    */   {
/* 22 */     MinecraftServer localMinecraftServer = MinecraftServer.getServer();
/*    */     
/* 24 */     int i = 0;
/* 25 */     for (int j = 0; j < localMinecraftServer.worldServer.length; j++) {
/* 26 */       if (localMinecraftServer.worldServer[j] != null) {
/* 27 */         WorldServer localWorldServer = localMinecraftServer.worldServer[j];
/* 28 */         if (!localWorldServer.savingDisabled) {
/* 29 */           localWorldServer.savingDisabled = true;
/* 30 */           i = 1;
/*    */         }
/*    */       }
/*    */     }
/*    */     
/* 35 */     if (i != 0) {
/* 36 */       a(paramICommandListener, this, "commands.save.disabled", new Object[0]);
/*    */     } else {
/* 38 */       throw new CommandException("commands.save-off.alreadyOff", new Object[0]);
/*    */     }
/*    */   }
/*    */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_8_R3\CommandSaveOff.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */
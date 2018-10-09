/*    */ package net.minecraft.server.v1_8_R3;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class CommandSaveAll
/*    */   extends CommandAbstract
/*    */ {
/*    */   public String getCommand()
/*    */   {
/* 14 */     return "save-all";
/*    */   }
/*    */   
/*    */   public String getUsage(ICommandListener paramICommandListener)
/*    */   {
/* 19 */     return "commands.save.usage";
/*    */   }
/*    */   
/*    */   public void execute(ICommandListener paramICommandListener, String[] paramArrayOfString) throws CommandException
/*    */   {
/* 24 */     MinecraftServer localMinecraftServer = MinecraftServer.getServer();
/* 25 */     paramICommandListener.sendMessage(new ChatMessage("commands.save.start", new Object[0]));
/*    */     
/* 27 */     if (localMinecraftServer.getPlayerList() != null)
/* 28 */       localMinecraftServer.getPlayerList().savePlayers();
/*    */     try {
/*    */       WorldServer localWorldServer;
/*    */       boolean bool;
/* 32 */       for (int i = 0; i < localMinecraftServer.worldServer.length; i++) {
/* 33 */         if (localMinecraftServer.worldServer[i] != null) {
/* 34 */           localWorldServer = localMinecraftServer.worldServer[i];
/* 35 */           bool = localWorldServer.savingDisabled;
/* 36 */           localWorldServer.savingDisabled = false;
/* 37 */           localWorldServer.save(true, null);
/* 38 */           localWorldServer.savingDisabled = bool;
/*    */         }
/*    */       }
/* 41 */       if ((paramArrayOfString.length > 0) && ("flush".equals(paramArrayOfString[0]))) {
/* 42 */         paramICommandListener.sendMessage(new ChatMessage("commands.save.flushStart", new Object[0]));
/* 43 */         for (i = 0; i < localMinecraftServer.worldServer.length; i++) {
/* 44 */           if (localMinecraftServer.worldServer[i] != null) {
/* 45 */             localWorldServer = localMinecraftServer.worldServer[i];
/* 46 */             bool = localWorldServer.savingDisabled;
/* 47 */             localWorldServer.savingDisabled = false;
/* 48 */             localWorldServer.flushSave();
/* 49 */             localWorldServer.savingDisabled = bool;
/*    */           }
/*    */         }
/* 52 */         paramICommandListener.sendMessage(new ChatMessage("commands.save.flushEnd", new Object[0]));
/*    */       }
/*    */     } catch (ExceptionWorldConflict localExceptionWorldConflict) {
/* 55 */       a(paramICommandListener, this, "commands.save.failed", new Object[] { localExceptionWorldConflict.getMessage() });
/* 56 */       return;
/*    */     }
/*    */     
/* 59 */     a(paramICommandListener, this, "commands.save.success", new Object[0]);
/*    */   }
/*    */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_8_R3\CommandSaveAll.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */
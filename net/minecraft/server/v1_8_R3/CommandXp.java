/*    */ package net.minecraft.server.v1_8_R3;
/*    */ 
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
/*    */ public class CommandXp
/*    */   extends CommandAbstract
/*    */ {
/*    */   public String getCommand()
/*    */   {
/* 18 */     return "xp";
/*    */   }
/*    */   
/*    */   public int a()
/*    */   {
/* 23 */     return 2;
/*    */   }
/*    */   
/*    */   public String getUsage(ICommandListener paramICommandListener)
/*    */   {
/* 28 */     return "commands.xp.usage";
/*    */   }
/*    */   
/*    */   public void execute(ICommandListener paramICommandListener, String[] paramArrayOfString) throws CommandException
/*    */   {
/* 33 */     if (paramArrayOfString.length <= 0) {
/* 34 */       throw new ExceptionUsage("commands.xp.usage", new Object[0]);
/*    */     }
/*    */     
/* 37 */     String str = paramArrayOfString[0];
/* 38 */     int i = (str.endsWith("l")) || (str.endsWith("L")) ? 1 : 0;
/* 39 */     if ((i != 0) && (str.length() > 1)) {
/* 40 */       str = str.substring(0, str.length() - 1);
/*    */     }
/* 42 */     int j = a(str);
/*    */     
/* 44 */     int k = j < 0 ? 1 : 0;
/* 45 */     if (k != 0) {
/* 46 */       j *= -1;
/*    */     }
/*    */     
/* 49 */     EntityPlayer localEntityPlayer = paramArrayOfString.length > 1 ? a(paramICommandListener, paramArrayOfString[1]) : b(paramICommandListener);
/* 50 */     if (i != 0) {
/* 51 */       paramICommandListener.a(CommandObjectiveExecutor.EnumCommandResult.QUERY_RESULT, localEntityPlayer.expLevel);
/* 52 */       if (k != 0) {
/* 53 */         localEntityPlayer.levelDown(-j);
/* 54 */         a(paramICommandListener, this, "commands.xp.success.negative.levels", new Object[] { Integer.valueOf(j), localEntityPlayer.getName() });
/*    */       } else {
/* 56 */         localEntityPlayer.levelDown(j);
/* 57 */         a(paramICommandListener, this, "commands.xp.success.levels", new Object[] { Integer.valueOf(j), localEntityPlayer.getName() });
/*    */       }
/*    */     } else {
/* 60 */       paramICommandListener.a(CommandObjectiveExecutor.EnumCommandResult.QUERY_RESULT, localEntityPlayer.expTotal);
/* 61 */       if (k != 0) {
/* 62 */         throw new CommandException("commands.xp.failure.widthdrawXp", new Object[0]);
/*    */       }
/* 64 */       localEntityPlayer.giveExp(j);
/* 65 */       a(paramICommandListener, this, "commands.xp.success", new Object[] { Integer.valueOf(j), localEntityPlayer.getName() });
/*    */     }
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */   public List<String> tabComplete(ICommandListener paramICommandListener, String[] paramArrayOfString, BlockPosition paramBlockPosition)
/*    */   {
/* 73 */     if (paramArrayOfString.length == 2) {
/* 74 */       return a(paramArrayOfString, d());
/*    */     }
/*    */     
/* 77 */     return null;
/*    */   }
/*    */   
/*    */   protected String[] d() {
/* 81 */     return MinecraftServer.getServer().getPlayers();
/*    */   }
/*    */   
/*    */   public boolean isListStart(String[] paramArrayOfString, int paramInt)
/*    */   {
/* 86 */     return paramInt == 1;
/*    */   }
/*    */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_8_R3\CommandXp.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */
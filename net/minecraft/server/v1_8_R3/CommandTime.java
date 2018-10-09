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
/*    */ public class CommandTime
/*    */   extends CommandAbstract
/*    */ {
/*    */   public String getCommand()
/*    */   {
/* 18 */     return "time";
/*    */   }
/*    */   
/*    */   public int a()
/*    */   {
/* 23 */     return 2;
/*    */   }
/*    */   
/*    */   public String getUsage(ICommandListener paramICommandListener)
/*    */   {
/* 28 */     return "commands.time.usage";
/*    */   }
/*    */   
/*    */   public void execute(ICommandListener paramICommandListener, String[] paramArrayOfString) throws CommandException
/*    */   {
/* 33 */     if (paramArrayOfString.length > 1) { int i;
/* 34 */       if (paramArrayOfString[0].equals("set"))
/*    */       {
/* 36 */         if (paramArrayOfString[1].equals("day")) {
/* 37 */           i = 1000;
/* 38 */         } else if (paramArrayOfString[1].equals("night")) {
/* 39 */           i = 13000;
/*    */         } else {
/* 41 */           i = a(paramArrayOfString[1], 0);
/*    */         }
/* 43 */         a(paramICommandListener, i);
/* 44 */         a(paramICommandListener, this, "commands.time.set", new Object[] { Integer.valueOf(i) });
/* 45 */         return; }
/* 46 */       if (paramArrayOfString[0].equals("add")) {
/* 47 */         i = a(paramArrayOfString[1], 0);
/* 48 */         b(paramICommandListener, i);
/* 49 */         a(paramICommandListener, this, "commands.time.added", new Object[] { Integer.valueOf(i) });
/* 50 */         return; }
/* 51 */       if (paramArrayOfString[0].equals("query")) {
/* 52 */         if (paramArrayOfString[1].equals("daytime")) {
/* 53 */           i = (int)(paramICommandListener.getWorld().getDayTime() % 2147483647L);
/* 54 */           paramICommandListener.a(CommandObjectiveExecutor.EnumCommandResult.QUERY_RESULT, i);
/* 55 */           a(paramICommandListener, this, "commands.time.query", new Object[] { Integer.valueOf(i) });
/* 56 */           return; }
/* 57 */         if (paramArrayOfString[1].equals("gametime")) {
/* 58 */           i = (int)(paramICommandListener.getWorld().getTime() % 2147483647L);
/* 59 */           paramICommandListener.a(CommandObjectiveExecutor.EnumCommandResult.QUERY_RESULT, i);
/* 60 */           a(paramICommandListener, this, "commands.time.query", new Object[] { Integer.valueOf(i) });
/* 61 */           return;
/*    */         }
/*    */       }
/*    */     }
/*    */     
/* 66 */     throw new ExceptionUsage("commands.time.usage", new Object[0]);
/*    */   }
/*    */   
/*    */ 
/*    */   public List<String> tabComplete(ICommandListener paramICommandListener, String[] paramArrayOfString, BlockPosition paramBlockPosition)
/*    */   {
/* 72 */     if (paramArrayOfString.length == 1)
/* 73 */       return a(paramArrayOfString, new String[] { "set", "add", "query" });
/* 74 */     if ((paramArrayOfString.length == 2) && (paramArrayOfString[0].equals("set")))
/* 75 */       return a(paramArrayOfString, new String[] { "day", "night" });
/* 76 */     if ((paramArrayOfString.length == 2) && (paramArrayOfString[0].equals("query"))) {
/* 77 */       return a(paramArrayOfString, new String[] { "daytime", "gametime" });
/*    */     }
/* 79 */     return null;
/*    */   }
/*    */   
/*    */   protected void a(ICommandListener paramICommandListener, int paramInt) {
/* 83 */     for (int i = 0; i < MinecraftServer.getServer().worldServer.length; i++) {
/* 84 */       MinecraftServer.getServer().worldServer[i].setDayTime(paramInt);
/*    */     }
/*    */   }
/*    */   
/*    */   protected void b(ICommandListener paramICommandListener, int paramInt) {
/* 89 */     for (int i = 0; i < MinecraftServer.getServer().worldServer.length; i++) {
/* 90 */       WorldServer localWorldServer = MinecraftServer.getServer().worldServer[i];
/* 91 */       localWorldServer.setDayTime(localWorldServer.getDayTime() + paramInt);
/*    */     }
/*    */   }
/*    */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_8_R3\CommandTime.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */
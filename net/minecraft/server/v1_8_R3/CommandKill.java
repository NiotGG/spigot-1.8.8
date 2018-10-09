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
/*    */ public class CommandKill
/*    */   extends CommandAbstract
/*    */ {
/*    */   public String getCommand()
/*    */   {
/* 17 */     return "kill";
/*    */   }
/*    */   
/*    */   public int a()
/*    */   {
/* 22 */     return 2;
/*    */   }
/*    */   
/*    */   public String getUsage(ICommandListener paramICommandListener)
/*    */   {
/* 27 */     return "commands.kill.usage";
/*    */   }
/*    */   
/*    */   public void execute(ICommandListener paramICommandListener, String[] paramArrayOfString) throws CommandException
/*    */   {
/* 32 */     if (paramArrayOfString.length == 0) {
/* 33 */       localObject = b(paramICommandListener);
/* 34 */       ((EntityHuman)localObject).G();
/* 35 */       a(paramICommandListener, this, "commands.kill.successful", new Object[] { ((EntityHuman)localObject).getScoreboardDisplayName() });
/* 36 */       return;
/*    */     }
/*    */     
/* 39 */     Object localObject = b(paramICommandListener, paramArrayOfString[0]);
/* 40 */     ((Entity)localObject).G();
/* 41 */     a(paramICommandListener, this, "commands.kill.successful", new Object[] { ((Entity)localObject).getScoreboardDisplayName() });
/*    */   }
/*    */   
/*    */   public boolean isListStart(String[] paramArrayOfString, int paramInt)
/*    */   {
/* 46 */     return paramInt == 0;
/*    */   }
/*    */   
/*    */ 
/*    */   public List<String> tabComplete(ICommandListener paramICommandListener, String[] paramArrayOfString, BlockPosition paramBlockPosition)
/*    */   {
/* 52 */     if (paramArrayOfString.length == 1) {
/* 53 */       return a(paramArrayOfString, MinecraftServer.getServer().getPlayers());
/*    */     }
/*    */     
/* 56 */     return null;
/*    */   }
/*    */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_8_R3\CommandKill.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */
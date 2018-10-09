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
/*    */ public class CommandSpawnpoint
/*    */   extends CommandAbstract
/*    */ {
/*    */   public String getCommand()
/*    */   {
/* 17 */     return "spawnpoint";
/*    */   }
/*    */   
/*    */   public int a()
/*    */   {
/* 22 */     return 2;
/*    */   }
/*    */   
/*    */   public String getUsage(ICommandListener paramICommandListener)
/*    */   {
/* 27 */     return "commands.spawnpoint.usage";
/*    */   }
/*    */   
/*    */   public void execute(ICommandListener paramICommandListener, String[] paramArrayOfString) throws CommandException
/*    */   {
/* 32 */     if ((paramArrayOfString.length > 1) && (paramArrayOfString.length < 4)) {
/* 33 */       throw new ExceptionUsage("commands.spawnpoint.usage", new Object[0]);
/*    */     }
/*    */     
/* 36 */     EntityPlayer localEntityPlayer = paramArrayOfString.length > 0 ? a(paramICommandListener, paramArrayOfString[0]) : b(paramICommandListener);
/* 37 */     BlockPosition localBlockPosition = paramArrayOfString.length > 3 ? a(paramICommandListener, paramArrayOfString, 1, true) : localEntityPlayer.getChunkCoordinates();
/* 38 */     if (localEntityPlayer.world != null) {
/* 39 */       localEntityPlayer.setRespawnPosition(localBlockPosition, true);
/* 40 */       a(paramICommandListener, this, "commands.spawnpoint.success", new Object[] { localEntityPlayer.getName(), Integer.valueOf(localBlockPosition.getX()), Integer.valueOf(localBlockPosition.getY()), Integer.valueOf(localBlockPosition.getZ()) });
/*    */     }
/*    */   }
/*    */   
/*    */ 
/*    */   public List<String> tabComplete(ICommandListener paramICommandListener, String[] paramArrayOfString, BlockPosition paramBlockPosition)
/*    */   {
/* 47 */     if (paramArrayOfString.length == 1)
/* 48 */       return a(paramArrayOfString, MinecraftServer.getServer().getPlayers());
/* 49 */     if ((paramArrayOfString.length > 1) && (paramArrayOfString.length <= 4)) {
/* 50 */       return a(paramArrayOfString, 1, paramBlockPosition);
/*    */     }
/*    */     
/* 53 */     return null;
/*    */   }
/*    */   
/*    */   public boolean isListStart(String[] paramArrayOfString, int paramInt)
/*    */   {
/* 58 */     return paramInt == 0;
/*    */   }
/*    */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_8_R3\CommandSpawnpoint.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */
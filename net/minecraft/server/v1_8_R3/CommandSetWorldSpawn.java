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
/*    */ public class CommandSetWorldSpawn
/*    */   extends CommandAbstract
/*    */ {
/*    */   public String getCommand()
/*    */   {
/* 17 */     return "setworldspawn";
/*    */   }
/*    */   
/*    */   public int a()
/*    */   {
/* 22 */     return 2;
/*    */   }
/*    */   
/*    */   public String getUsage(ICommandListener paramICommandListener)
/*    */   {
/* 27 */     return "commands.setworldspawn.usage";
/*    */   }
/*    */   
/*    */   public void execute(ICommandListener paramICommandListener, String[] paramArrayOfString) throws CommandException
/*    */   {
/*    */     BlockPosition localBlockPosition;
/* 33 */     if (paramArrayOfString.length == 0) {
/* 34 */       localBlockPosition = b(paramICommandListener).getChunkCoordinates();
/* 35 */     } else if ((paramArrayOfString.length == 3) && (paramICommandListener.getWorld() != null)) {
/* 36 */       localBlockPosition = a(paramICommandListener, paramArrayOfString, 0, true);
/*    */     } else {
/* 38 */       throw new ExceptionUsage("commands.setworldspawn.usage", new Object[0]);
/*    */     }
/*    */     
/* 41 */     paramICommandListener.getWorld().B(localBlockPosition);
/* 42 */     MinecraftServer.getServer().getPlayerList().sendAll(new PacketPlayOutSpawnPosition(localBlockPosition));
/* 43 */     a(paramICommandListener, this, "commands.setworldspawn.success", new Object[] { Integer.valueOf(localBlockPosition.getX()), Integer.valueOf(localBlockPosition.getY()), Integer.valueOf(localBlockPosition.getZ()) });
/*    */   }
/*    */   
/*    */ 
/*    */   public List<String> tabComplete(ICommandListener paramICommandListener, String[] paramArrayOfString, BlockPosition paramBlockPosition)
/*    */   {
/* 49 */     if ((paramArrayOfString.length > 0) && (paramArrayOfString.length <= 3)) {
/* 50 */       return a(paramArrayOfString, 0, paramBlockPosition);
/*    */     }
/* 52 */     return null;
/*    */   }
/*    */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_8_R3\CommandSetWorldSpawn.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */
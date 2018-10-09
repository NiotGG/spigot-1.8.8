/*    */ package net.minecraft.server.v1_8_R3;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class CommandSeed
/*    */   extends CommandAbstract
/*    */ {
/*    */   public boolean canUse(ICommandListener paramICommandListener)
/*    */   {
/* 14 */     return (MinecraftServer.getServer().T()) || (super.canUse(paramICommandListener));
/*    */   }
/*    */   
/*    */   public String getCommand()
/*    */   {
/* 19 */     return "seed";
/*    */   }
/*    */   
/*    */   public int a()
/*    */   {
/* 24 */     return 2;
/*    */   }
/*    */   
/*    */   public String getUsage(ICommandListener paramICommandListener)
/*    */   {
/* 29 */     return "commands.seed.usage";
/*    */   }
/*    */   
/*    */   public void execute(ICommandListener paramICommandListener, String[] paramArrayOfString) throws CommandException
/*    */   {
/* 34 */     WorldServer localWorldServer = (paramICommandListener instanceof EntityHuman) ? ((EntityHuman)paramICommandListener).world : MinecraftServer.getServer().getWorldServer(0);
/* 35 */     paramICommandListener.sendMessage(new ChatMessage("commands.seed.success", new Object[] { Long.valueOf(localWorldServer.getSeed()) }));
/*    */   }
/*    */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_8_R3\CommandSeed.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */
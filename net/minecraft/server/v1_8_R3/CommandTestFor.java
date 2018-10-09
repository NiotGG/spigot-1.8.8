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
/*    */ 
/*    */ 
/*    */ 
/*    */ public class CommandTestFor
/*    */   extends CommandAbstract
/*    */ {
/*    */   public String getCommand()
/*    */   {
/* 21 */     return "testfor";
/*    */   }
/*    */   
/*    */   public int a()
/*    */   {
/* 26 */     return 2;
/*    */   }
/*    */   
/*    */   public String getUsage(ICommandListener paramICommandListener)
/*    */   {
/* 31 */     return "commands.testfor.usage";
/*    */   }
/*    */   
/*    */   public void execute(ICommandListener paramICommandListener, String[] paramArrayOfString) throws CommandException
/*    */   {
/* 36 */     if (paramArrayOfString.length < 1) {
/* 37 */       throw new ExceptionUsage("commands.testfor.usage", new Object[0]);
/*    */     }
/*    */     
/* 40 */     Entity localEntity = b(paramICommandListener, paramArrayOfString[0]);
/* 41 */     NBTTagCompound localNBTTagCompound1 = null;
/* 42 */     if (paramArrayOfString.length >= 2) {
/*    */       try {
/* 44 */         localNBTTagCompound1 = MojangsonParser.parse(a(paramArrayOfString, 1));
/*    */       } catch (MojangsonParseException localMojangsonParseException) {
/* 46 */         throw new CommandException("commands.testfor.tagError", new Object[] { localMojangsonParseException.getMessage() });
/*    */       }
/*    */     }
/*    */     
/* 50 */     if (localNBTTagCompound1 != null) {
/* 51 */       NBTTagCompound localNBTTagCompound2 = new NBTTagCompound();
/* 52 */       localEntity.e(localNBTTagCompound2);
/* 53 */       if (!GameProfileSerializer.a(localNBTTagCompound1, localNBTTagCompound2, true)) {
/* 54 */         throw new CommandException("commands.testfor.failure", new Object[] { localEntity.getName() });
/*    */       }
/*    */     }
/*    */     
/* 58 */     a(paramICommandListener, this, "commands.testfor.success", new Object[] { localEntity.getName() });
/*    */   }
/*    */   
/*    */   public boolean isListStart(String[] paramArrayOfString, int paramInt)
/*    */   {
/* 63 */     return paramInt == 0;
/*    */   }
/*    */   
/*    */ 
/*    */   public List<String> tabComplete(ICommandListener paramICommandListener, String[] paramArrayOfString, BlockPosition paramBlockPosition)
/*    */   {
/* 69 */     if (paramArrayOfString.length == 1) {
/* 70 */       return a(paramArrayOfString, MinecraftServer.getServer().getPlayers());
/*    */     }
/* 72 */     return null;
/*    */   }
/*    */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_8_R3\CommandTestFor.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */
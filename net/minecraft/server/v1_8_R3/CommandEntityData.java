/*    */ package net.minecraft.server.v1_8_R3;
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
/*    */ 
/*    */ 
/*    */ public class CommandEntityData
/*    */   extends CommandAbstract
/*    */ {
/*    */   public String getCommand()
/*    */   {
/* 21 */     return "entitydata";
/*    */   }
/*    */   
/*    */   public int a()
/*    */   {
/* 26 */     return 2;
/*    */   }
/*    */   
/*    */   public String getUsage(ICommandListener paramICommandListener)
/*    */   {
/* 31 */     return "commands.entitydata.usage";
/*    */   }
/*    */   
/*    */   public void execute(ICommandListener paramICommandListener, String[] paramArrayOfString) throws CommandException
/*    */   {
/* 36 */     if (paramArrayOfString.length < 2) {
/* 37 */       throw new ExceptionUsage("commands.entitydata.usage", new Object[0]);
/*    */     }
/*    */     
/* 40 */     Entity localEntity = b(paramICommandListener, paramArrayOfString[0]);
/* 41 */     if ((localEntity instanceof EntityHuman)) {
/* 42 */       throw new CommandException("commands.entitydata.noPlayers", new Object[] { localEntity.getScoreboardDisplayName() });
/*    */     }
/*    */     
/* 45 */     NBTTagCompound localNBTTagCompound1 = new NBTTagCompound();
/* 46 */     localEntity.e(localNBTTagCompound1);
/* 47 */     NBTTagCompound localNBTTagCompound2 = (NBTTagCompound)localNBTTagCompound1.clone();
/*    */     NBTTagCompound localNBTTagCompound3;
/*    */     try
/*    */     {
/* 51 */       localNBTTagCompound3 = MojangsonParser.parse(a(paramICommandListener, paramArrayOfString, 1).c());
/*    */     } catch (MojangsonParseException localMojangsonParseException) {
/* 53 */       throw new CommandException("commands.entitydata.tagError", new Object[] { localMojangsonParseException.getMessage() });
/*    */     }
/*    */     
/* 56 */     localNBTTagCompound3.remove("UUIDMost");
/* 57 */     localNBTTagCompound3.remove("UUIDLeast");
/*    */     
/* 59 */     localNBTTagCompound1.a(localNBTTagCompound3);
/*    */     
/* 61 */     if (localNBTTagCompound1.equals(localNBTTagCompound2)) {
/* 62 */       throw new CommandException("commands.entitydata.failed", new Object[] { localNBTTagCompound1.toString() });
/*    */     }
/*    */     
/* 65 */     localEntity.f(localNBTTagCompound1);
/*    */     
/* 67 */     a(paramICommandListener, this, "commands.entitydata.success", new Object[] { localNBTTagCompound1.toString() });
/*    */   }
/*    */   
/*    */   public boolean isListStart(String[] paramArrayOfString, int paramInt)
/*    */   {
/* 72 */     return paramInt == 0;
/*    */   }
/*    */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_8_R3\CommandEntityData.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */
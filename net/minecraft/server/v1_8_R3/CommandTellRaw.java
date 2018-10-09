/*    */ package net.minecraft.server.v1_8_R3;
/*    */ 
/*    */ import com.google.gson.JsonParseException;
/*    */ import java.util.List;
/*    */ import org.apache.commons.lang3.exception.ExceptionUtils;
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
/*    */ public class CommandTellRaw
/*    */   extends CommandAbstract
/*    */ {
/*    */   public String getCommand()
/*    */   {
/* 22 */     return "tellraw";
/*    */   }
/*    */   
/*    */   public int a()
/*    */   {
/* 27 */     return 2;
/*    */   }
/*    */   
/*    */   public String getUsage(ICommandListener paramICommandListener)
/*    */   {
/* 32 */     return "commands.tellraw.usage";
/*    */   }
/*    */   
/*    */   public void execute(ICommandListener paramICommandListener, String[] paramArrayOfString) throws CommandException
/*    */   {
/* 37 */     if (paramArrayOfString.length < 2) {
/* 38 */       throw new ExceptionUsage("commands.tellraw.usage", new Object[0]);
/*    */     }
/*    */     
/* 41 */     EntityPlayer localEntityPlayer = a(paramICommandListener, paramArrayOfString[0]);
/* 42 */     String str = a(paramArrayOfString, 1);
/*    */     try
/*    */     {
/* 45 */       IChatBaseComponent localIChatBaseComponent = IChatBaseComponent.ChatSerializer.a(str);
/* 46 */       localEntityPlayer.sendMessage(ChatComponentUtils.filterForDisplay(paramICommandListener, localIChatBaseComponent, localEntityPlayer));
/*    */     } catch (JsonParseException localJsonParseException) {
/* 48 */       Throwable localThrowable = ExceptionUtils.getRootCause(localJsonParseException);
/* 49 */       throw new ExceptionInvalidSyntax("commands.tellraw.jsonException", new Object[] { localThrowable == null ? "" : localThrowable.getMessage() });
/*    */     }
/*    */   }
/*    */   
/*    */ 
/*    */   public List<String> tabComplete(ICommandListener paramICommandListener, String[] paramArrayOfString, BlockPosition paramBlockPosition)
/*    */   {
/* 56 */     if (paramArrayOfString.length == 1) {
/* 57 */       return a(paramArrayOfString, MinecraftServer.getServer().getPlayers());
/*    */     }
/*    */     
/* 60 */     return null;
/*    */   }
/*    */   
/*    */   public boolean isListStart(String[] paramArrayOfString, int paramInt)
/*    */   {
/* 65 */     return paramInt == 0;
/*    */   }
/*    */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_8_R3\CommandTellRaw.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */
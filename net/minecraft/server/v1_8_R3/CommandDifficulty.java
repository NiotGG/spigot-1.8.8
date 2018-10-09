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
/*    */ public class CommandDifficulty
/*    */   extends CommandAbstract
/*    */ {
/*    */   public String getCommand()
/*    */   {
/* 19 */     return "difficulty";
/*    */   }
/*    */   
/*    */   public int a()
/*    */   {
/* 24 */     return 2;
/*    */   }
/*    */   
/*    */   public String getUsage(ICommandListener paramICommandListener)
/*    */   {
/* 29 */     return "commands.difficulty.usage";
/*    */   }
/*    */   
/*    */   public void execute(ICommandListener paramICommandListener, String[] paramArrayOfString) throws CommandException
/*    */   {
/* 34 */     if (paramArrayOfString.length <= 0) {
/* 35 */       throw new ExceptionUsage("commands.difficulty.usage", new Object[0]);
/*    */     }
/*    */     
/* 38 */     EnumDifficulty localEnumDifficulty = e(paramArrayOfString[0]);
/* 39 */     MinecraftServer.getServer().a(localEnumDifficulty);
/*    */     
/* 41 */     a(paramICommandListener, this, "commands.difficulty.success", new Object[] { new ChatMessage(localEnumDifficulty.b(), new Object[0]) });
/*    */   }
/*    */   
/*    */   protected EnumDifficulty e(String paramString) throws ExceptionInvalidNumber {
/* 45 */     if ((paramString.equalsIgnoreCase("peaceful")) || (paramString.equalsIgnoreCase("p")))
/* 46 */       return EnumDifficulty.PEACEFUL;
/* 47 */     if ((paramString.equalsIgnoreCase("easy")) || (paramString.equalsIgnoreCase("e")))
/* 48 */       return EnumDifficulty.EASY;
/* 49 */     if ((paramString.equalsIgnoreCase("normal")) || (paramString.equalsIgnoreCase("n")))
/* 50 */       return EnumDifficulty.NORMAL;
/* 51 */     if ((paramString.equalsIgnoreCase("hard")) || (paramString.equalsIgnoreCase("h"))) {
/* 52 */       return EnumDifficulty.HARD;
/*    */     }
/* 54 */     return EnumDifficulty.getById(a(paramString, 0, 3));
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */   public List<String> tabComplete(ICommandListener paramICommandListener, String[] paramArrayOfString, BlockPosition paramBlockPosition)
/*    */   {
/* 61 */     if (paramArrayOfString.length == 1) {
/* 62 */       return a(paramArrayOfString, new String[] { "peaceful", "easy", "normal", "hard" });
/*    */     }
/*    */     
/* 65 */     return null;
/*    */   }
/*    */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_8_R3\CommandDifficulty.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */
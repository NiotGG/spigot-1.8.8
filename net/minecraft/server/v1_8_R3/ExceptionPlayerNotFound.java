/*   */ package net.minecraft.server.v1_8_R3;
/*   */ 
/*   */ public class ExceptionPlayerNotFound extends CommandException {
/*   */   public ExceptionPlayerNotFound() {
/* 5 */     this("commands.generic.player.notFound", new Object[0]);
/*   */   }
/*   */   
/*   */   public ExceptionPlayerNotFound(String paramString, Object... paramVarArgs) {
/* 9 */     super(paramString, paramVarArgs);
/*   */   }
/*   */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_8_R3\ExceptionPlayerNotFound.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */
/*   */ package net.minecraft.server.v1_8_R3;
/*   */ 
/*   */ public class ExceptionEntityNotFound extends CommandException {
/*   */   public ExceptionEntityNotFound() {
/* 5 */     this("commands.generic.entity.notFound", new Object[0]);
/*   */   }
/*   */   
/*   */   public ExceptionEntityNotFound(String paramString, Object... paramVarArgs) {
/* 9 */     super(paramString, paramVarArgs);
/*   */   }
/*   */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_8_R3\ExceptionEntityNotFound.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */
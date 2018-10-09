/*    */ package net.minecraft.server.v1_8_R3;
/*    */ 
/*    */ public class CommandException extends Exception {
/*    */   private final Object[] a;
/*    */   
/*    */   public CommandException(String paramString, Object... paramVarArgs) {
/*  7 */     super(paramString);
/*    */     
/*  9 */     this.a = paramVarArgs;
/*    */   }
/*    */   
/*    */   public Object[] getArgs() {
/* 13 */     return this.a;
/*    */   }
/*    */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_8_R3\CommandException.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */
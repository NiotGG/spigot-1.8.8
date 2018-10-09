/*    */ package net.minecraft.server.v1_8_R3;
/*    */ 
/*    */ public class ServerCommand
/*    */ {
/*    */   public final String command;
/*    */   public final ICommandListener source;
/*    */   
/*    */   public ServerCommand(String paramString, ICommandListener paramICommandListener)
/*    */   {
/* 10 */     this.command = paramString;
/* 11 */     this.source = paramICommandListener;
/*    */   }
/*    */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_8_R3\ServerCommand.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */
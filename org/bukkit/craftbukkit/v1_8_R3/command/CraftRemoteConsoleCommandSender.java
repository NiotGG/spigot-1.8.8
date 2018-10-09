/*    */ package org.bukkit.craftbukkit.v1_8_R3.command;
/*    */ 
/*    */ import net.minecraft.server.v1_8_R3.ChatComponentText;
/*    */ import net.minecraft.server.v1_8_R3.RemoteControlCommandListener;
/*    */ import org.bukkit.command.RemoteConsoleCommandSender;
/*    */ 
/*    */ 
/*    */ public class CraftRemoteConsoleCommandSender
/*    */   extends ServerCommandSender
/*    */   implements RemoteConsoleCommandSender
/*    */ {
/*    */   public void sendMessage(String message)
/*    */   {
/* 14 */     RemoteControlCommandListener.getInstance().sendMessage(new ChatComponentText(message + "\n"));
/*    */   }
/*    */   
/*    */   public void sendMessage(String[] messages) {
/*    */     String[] arrayOfString;
/* 19 */     int i = (arrayOfString = messages).length; for (int j = 0; j < i; j++) { String message = arrayOfString[j];
/* 20 */       sendMessage(message);
/*    */     }
/*    */   }
/*    */   
/*    */   public String getName()
/*    */   {
/* 26 */     return "Rcon";
/*    */   }
/*    */   
/*    */   public boolean isOp()
/*    */   {
/* 31 */     return true;
/*    */   }
/*    */   
/*    */   public void setOp(boolean value)
/*    */   {
/* 36 */     throw new UnsupportedOperationException("Cannot change operator status of remote controller.");
/*    */   }
/*    */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\org\bukkit\craftbukkit\v1_8_R3\command\CraftRemoteConsoleCommandSender.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */
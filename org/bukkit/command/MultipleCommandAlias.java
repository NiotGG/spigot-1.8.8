/*    */ package org.bukkit.command;
/*    */ 
/*    */ public class MultipleCommandAlias
/*    */   extends Command
/*    */ {
/*    */   private Command[] commands;
/*    */   
/*    */   public MultipleCommandAlias(String name, Command[] commands)
/*    */   {
/* 10 */     super(name);
/* 11 */     this.commands = commands;
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   public Command[] getCommands()
/*    */   {
/* 20 */     return this.commands;
/*    */   }
/*    */   
/*    */   public boolean execute(CommandSender sender, String commandLabel, String[] args)
/*    */   {
/* 25 */     boolean result = false;
/*    */     Command[] arrayOfCommand;
/* 27 */     int i = (arrayOfCommand = this.commands).length; for (int j = 0; j < i; j++) { Command command = arrayOfCommand[j];
/* 28 */       result |= command.execute(sender, commandLabel, args);
/*    */     }
/*    */     
/* 31 */     return result;
/*    */   }
/*    */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\org\bukkit\command\MultipleCommandAlias.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */
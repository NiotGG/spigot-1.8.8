/*    */ package org.bukkit.command.defaults;
/*    */ 
/*    */ import org.bukkit.command.Command;
/*    */ 
/*    */ public abstract class BukkitCommand extends Command
/*    */ {
/*    */   protected BukkitCommand(String name)
/*    */   {
/*  9 */     super(name);
/*    */   }
/*    */   
/*    */   protected BukkitCommand(String name, String description, String usageMessage, java.util.List<String> aliases) {
/* 13 */     super(name, description, usageMessage, aliases);
/*    */   }
/*    */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\org\bukkit\command\defaults\BukkitCommand.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */
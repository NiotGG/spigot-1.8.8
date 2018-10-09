/*    */ package org.bukkit.command.defaults;
/*    */ 
/*    */ import com.google.common.collect.ImmutableList;
/*    */ import java.util.List;
/*    */ import org.apache.commons.lang.Validate;
/*    */ import org.bukkit.Bukkit;
/*    */ import org.bukkit.ChatColor;
/*    */ import org.bukkit.command.CommandSender;
/*    */ import org.bukkit.command.ConsoleCommandSender;
/*    */ import org.bukkit.entity.Player;
/*    */ 
/*    */ @Deprecated
/*    */ public class SayCommand extends VanillaCommand
/*    */ {
/*    */   public SayCommand()
/*    */   {
/* 17 */     super("say");
/* 18 */     this.description = "Broadcasts the given message as the sender";
/* 19 */     this.usageMessage = "/say <message ...>";
/* 20 */     setPermission("bukkit.command.say");
/*    */   }
/*    */   
/*    */   public boolean execute(CommandSender sender, String currentAlias, String[] args)
/*    */   {
/* 25 */     if (!testPermission(sender)) return true;
/* 26 */     if (args.length == 0) {
/* 27 */       sender.sendMessage(ChatColor.RED + "Usage: " + this.usageMessage);
/* 28 */       return false;
/*    */     }
/*    */     
/* 31 */     StringBuilder message = new StringBuilder();
/* 32 */     message.append(ChatColor.LIGHT_PURPLE).append("[");
/* 33 */     if ((sender instanceof ConsoleCommandSender)) {
/* 34 */       message.append("Server");
/* 35 */     } else if ((sender instanceof Player)) {
/* 36 */       message.append(((Player)sender).getDisplayName());
/*    */     } else {
/* 38 */       message.append(sender.getName());
/*    */     }
/* 40 */     message.append(ChatColor.LIGHT_PURPLE).append("] ");
/*    */     
/* 42 */     if (args.length > 0) {
/* 43 */       message.append(args[0]);
/* 44 */       for (int i = 1; i < args.length; i++) {
/* 45 */         message.append(" ").append(args[i]);
/*    */       }
/*    */     }
/*    */     
/* 49 */     Bukkit.broadcastMessage(message.toString());
/* 50 */     return true;
/*    */   }
/*    */   
/*    */   public List<String> tabComplete(CommandSender sender, String alias, String[] args) throws IllegalArgumentException
/*    */   {
/* 55 */     Validate.notNull(sender, "Sender cannot be null");
/* 56 */     Validate.notNull(args, "Arguments cannot be null");
/*    */     
/* 58 */     if (args.length >= 1) {
/* 59 */       return super.tabComplete(sender, alias, args);
/*    */     }
/* 61 */     return ImmutableList.of();
/*    */   }
/*    */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\org\bukkit\command\defaults\SayCommand.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */
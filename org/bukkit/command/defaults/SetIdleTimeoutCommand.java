/*    */ package org.bukkit.command.defaults;
/*    */ 
/*    */ import com.google.common.collect.ImmutableList;
/*    */ import java.util.List;
/*    */ import org.apache.commons.lang.Validate;
/*    */ import org.bukkit.Bukkit;
/*    */ import org.bukkit.ChatColor;
/*    */ import org.bukkit.Server;
/*    */ import org.bukkit.command.Command;
/*    */ import org.bukkit.command.CommandSender;
/*    */ 
/*    */ @Deprecated
/*    */ public class SetIdleTimeoutCommand extends VanillaCommand
/*    */ {
/*    */   public SetIdleTimeoutCommand()
/*    */   {
/* 17 */     super("setidletimeout");
/* 18 */     this.description = "Sets the server's idle timeout";
/* 19 */     this.usageMessage = "/setidletimeout <Minutes until kick>";
/* 20 */     setPermission("bukkit.command.setidletimeout");
/*    */   }
/*    */   
/*    */   public boolean execute(CommandSender sender, String currentAlias, String[] args)
/*    */   {
/* 25 */     if (!testPermission(sender)) { return true;
/*    */     }
/* 27 */     if (args.length == 1)
/*    */     {
/*    */       try
/*    */       {
/* 31 */         minutes = getInteger(sender, args[0], 0, Integer.MAX_VALUE, true);
/*    */       } catch (NumberFormatException ex) { int minutes;
/* 33 */         sender.sendMessage(ex.getMessage());
/* 34 */         return true;
/*    */       }
/*    */       int minutes;
/* 37 */       Bukkit.getServer().setIdleTimeout(minutes);
/*    */       
/* 39 */       Command.broadcastCommandMessage(sender, "Successfully set the idle timeout to " + minutes + " minutes.");
/* 40 */       return true;
/*    */     }
/* 42 */     sender.sendMessage(ChatColor.RED + "Usage: " + this.usageMessage);
/* 43 */     return false;
/*    */   }
/*    */   
/*    */   public List<String> tabComplete(CommandSender sender, String alias, String[] args)
/*    */   {
/* 48 */     Validate.notNull(sender, "Sender cannot be null");
/* 49 */     Validate.notNull(args, "Arguments cannot be null");
/* 50 */     Validate.notNull(alias, "Alias cannot be null");
/*    */     
/* 52 */     return ImmutableList.of();
/*    */   }
/*    */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\org\bukkit\command\defaults\SetIdleTimeoutCommand.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */
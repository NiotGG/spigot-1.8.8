/*    */ package org.bukkit.command.defaults;
/*    */ 
/*    */ import com.google.common.collect.ImmutableList;
/*    */ import java.util.List;
/*    */ import org.apache.commons.lang.StringUtils;
/*    */ import org.apache.commons.lang.Validate;
/*    */ import org.bukkit.Bukkit;
/*    */ import org.bukkit.command.Command;
/*    */ import org.bukkit.command.CommandSender;
/*    */ import org.bukkit.entity.Player;
/*    */ 
/*    */ @Deprecated
/*    */ public class StopCommand
/*    */   extends VanillaCommand
/*    */ {
/*    */   public StopCommand()
/*    */   {
/* 18 */     super("stop");
/* 19 */     this.description = "Stops the server with optional reason";
/* 20 */     this.usageMessage = "/stop [reason]";
/* 21 */     setPermission("bukkit.command.stop");
/*    */   }
/*    */   
/*    */   public boolean execute(CommandSender sender, String currentAlias, String[] args)
/*    */   {
/* 26 */     if (!testPermission(sender)) { return true;
/*    */     }
/* 28 */     Command.broadcastCommandMessage(sender, "Stopping the server..");
/* 29 */     Bukkit.shutdown();
/*    */     
/* 31 */     String reason = createString(args, 0);
/* 32 */     if (StringUtils.isNotEmpty(reason)) {
/* 33 */       for (Player player : Bukkit.getOnlinePlayers()) {
/* 34 */         player.kickPlayer(reason);
/*    */       }
/*    */     }
/*    */     
/* 38 */     return true;
/*    */   }
/*    */   
/*    */   public List<String> tabComplete(CommandSender sender, String alias, String[] args) throws IllegalArgumentException
/*    */   {
/* 43 */     Validate.notNull(sender, "Sender cannot be null");
/* 44 */     Validate.notNull(args, "Arguments cannot be null");
/* 45 */     Validate.notNull(alias, "Alias cannot be null");
/*    */     
/* 47 */     return ImmutableList.of();
/*    */   }
/*    */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\org\bukkit\command\defaults\StopCommand.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */
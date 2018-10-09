/*    */ package org.bukkit.command.defaults;
/*    */ 
/*    */ import com.google.common.collect.ImmutableList;
/*    */ import java.util.Collection;
/*    */ import java.util.List;
/*    */ import org.apache.commons.lang.Validate;
/*    */ import org.bukkit.Bukkit;
/*    */ import org.bukkit.command.CommandSender;
/*    */ import org.bukkit.entity.Player;
/*    */ 
/*    */ @Deprecated
/*    */ public class ListCommand extends VanillaCommand
/*    */ {
/*    */   public ListCommand()
/*    */   {
/* 16 */     super("list");
/* 17 */     this.description = "Lists all online players";
/* 18 */     this.usageMessage = "/list";
/* 19 */     setPermission("bukkit.command.list");
/*    */   }
/*    */   
/*    */   public boolean execute(CommandSender sender, String currentAlias, String[] args)
/*    */   {
/* 24 */     if (!testPermission(sender)) { return true;
/*    */     }
/* 26 */     StringBuilder online = new StringBuilder();
/*    */     
/* 28 */     Collection<? extends Player> players = Bukkit.getOnlinePlayers();
/*    */     
/* 30 */     for (Player player : players)
/*    */     {
/* 32 */       if ((!(sender instanceof Player)) || (((Player)sender).canSee(player)))
/*    */       {
/*    */ 
/* 35 */         if (online.length() > 0) {
/* 36 */           online.append(", ");
/*    */         }
/*    */         
/* 39 */         online.append(player.getDisplayName());
/*    */       }
/*    */     }
/* 42 */     sender.sendMessage("There are " + players.size() + "/" + Bukkit.getMaxPlayers() + " players online:\n" + online.toString());
/*    */     
/* 44 */     return true;
/*    */   }
/*    */   
/*    */   public List<String> tabComplete(CommandSender sender, String alias, String[] args) throws IllegalArgumentException
/*    */   {
/* 49 */     Validate.notNull(sender, "Sender cannot be null");
/* 50 */     Validate.notNull(args, "Arguments cannot be null");
/* 51 */     Validate.notNull(alias, "Alias cannot be null");
/*    */     
/* 53 */     return ImmutableList.of();
/*    */   }
/*    */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\org\bukkit\command\defaults\ListCommand.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */
/*    */ package org.bukkit.command.defaults;
/*    */ 
/*    */ import com.google.common.collect.ImmutableList;
/*    */ import java.util.List;
/*    */ import org.apache.commons.lang.Validate;
/*    */ import org.bukkit.Bukkit;
/*    */ import org.bukkit.World;
/*    */ import org.bukkit.command.Command;
/*    */ import org.bukkit.command.CommandSender;
/*    */ 
/*    */ @Deprecated
/*    */ public class SaveCommand extends VanillaCommand
/*    */ {
/*    */   public SaveCommand()
/*    */   {
/* 16 */     super("save-all");
/* 17 */     this.description = "Saves the server to disk";
/* 18 */     this.usageMessage = "/save-all";
/* 19 */     setPermission("bukkit.command.save.perform");
/*    */   }
/*    */   
/*    */   public boolean execute(CommandSender sender, String currentAlias, String[] args)
/*    */   {
/* 24 */     if (!testPermission(sender)) { return true;
/*    */     }
/* 26 */     Command.broadcastCommandMessage(sender, "Forcing save..");
/*    */     
/* 28 */     Bukkit.savePlayers();
/*    */     
/* 30 */     for (World world : Bukkit.getWorlds()) {
/* 31 */       world.save();
/*    */     }
/*    */     
/* 34 */     Command.broadcastCommandMessage(sender, "Save complete.");
/*    */     
/* 36 */     return true;
/*    */   }
/*    */   
/*    */   public List<String> tabComplete(CommandSender sender, String alias, String[] args) throws IllegalArgumentException
/*    */   {
/* 41 */     Validate.notNull(sender, "Sender cannot be null");
/* 42 */     Validate.notNull(args, "Arguments cannot be null");
/* 43 */     Validate.notNull(alias, "Alias cannot be null");
/*    */     
/* 45 */     return ImmutableList.of();
/*    */   }
/*    */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\org\bukkit\command\defaults\SaveCommand.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */
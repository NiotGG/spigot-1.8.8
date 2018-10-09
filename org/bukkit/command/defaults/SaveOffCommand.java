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
/*    */ public class SaveOffCommand extends VanillaCommand
/*    */ {
/*    */   public SaveOffCommand()
/*    */   {
/* 16 */     super("save-off");
/* 17 */     this.description = "Disables server autosaving";
/* 18 */     this.usageMessage = "/save-off";
/* 19 */     setPermission("bukkit.command.save.disable");
/*    */   }
/*    */   
/*    */   public boolean execute(CommandSender sender, String currentAlias, String[] args)
/*    */   {
/* 24 */     if (!testPermission(sender)) { return true;
/*    */     }
/* 26 */     for (World world : Bukkit.getWorlds()) {
/* 27 */       world.setAutoSave(false);
/*    */     }
/*    */     
/* 30 */     Command.broadcastCommandMessage(sender, "Disabled level saving..");
/* 31 */     return true;
/*    */   }
/*    */   
/*    */   public List<String> tabComplete(CommandSender sender, String alias, String[] args) throws IllegalArgumentException
/*    */   {
/* 36 */     Validate.notNull(sender, "Sender cannot be null");
/* 37 */     Validate.notNull(args, "Arguments cannot be null");
/* 38 */     Validate.notNull(alias, "Alias cannot be null");
/*    */     
/* 40 */     return ImmutableList.of();
/*    */   }
/*    */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\org\bukkit\command\defaults\SaveOffCommand.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */
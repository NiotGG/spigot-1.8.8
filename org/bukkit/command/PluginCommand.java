/*     */ package org.bukkit.command;
/*     */ 
/*     */ import java.util.List;
/*     */ import org.apache.commons.lang.Validate;
/*     */ import org.bukkit.plugin.Plugin;
/*     */ import org.bukkit.plugin.PluginDescriptionFile;
/*     */ 
/*     */ public final class PluginCommand
/*     */   extends Command implements PluginIdentifiableCommand
/*     */ {
/*     */   private final Plugin owningPlugin;
/*     */   private CommandExecutor executor;
/*     */   private TabCompleter completer;
/*     */   
/*     */   protected PluginCommand(String name, Plugin owner)
/*     */   {
/*  17 */     super(name);
/*  18 */     this.executor = owner;
/*  19 */     this.owningPlugin = owner;
/*  20 */     this.usageMessage = "";
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public boolean execute(CommandSender sender, String commandLabel, String[] args)
/*     */   {
/*  33 */     boolean success = false;
/*     */     
/*  35 */     if (!this.owningPlugin.isEnabled()) {
/*  36 */       return false;
/*     */     }
/*     */     
/*  39 */     if (!testPermission(sender)) {
/*  40 */       return true;
/*     */     }
/*     */     try
/*     */     {
/*  44 */       success = this.executor.onCommand(sender, this, commandLabel, args);
/*     */     } catch (Throwable ex) {
/*  46 */       throw new CommandException("Unhandled exception executing command '" + commandLabel + "' in plugin " + this.owningPlugin.getDescription().getFullName(), ex);
/*     */     }
/*     */     
/*  49 */     if ((!success) && (this.usageMessage.length() > 0)) { String[] arrayOfString;
/*  50 */       int i = (arrayOfString = this.usageMessage.replace("<command>", commandLabel).split("\n")).length; for (int j = 0; j < i; j++) { String line = arrayOfString[j];
/*  51 */         sender.sendMessage(line);
/*     */       }
/*     */     }
/*     */     
/*  55 */     return success;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public void setExecutor(CommandExecutor executor)
/*     */   {
/*  64 */     this.executor = (executor == null ? this.owningPlugin : executor);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public CommandExecutor getExecutor()
/*     */   {
/*  73 */     return this.executor;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public void setTabCompleter(TabCompleter completer)
/*     */   {
/*  85 */     this.completer = completer;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public TabCompleter getTabCompleter()
/*     */   {
/*  94 */     return this.completer;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public Plugin getPlugin()
/*     */   {
/* 103 */     return this.owningPlugin;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public List<String> tabComplete(CommandSender sender, String alias, String[] args)
/*     */     throws CommandException, IllegalArgumentException
/*     */   {
/* 125 */     Validate.notNull(sender, "Sender cannot be null");
/* 126 */     Validate.notNull(args, "Arguments cannot be null");
/* 127 */     Validate.notNull(alias, "Alias cannot be null");
/*     */     
/* 129 */     List<String> completions = null;
/*     */     try {
/* 131 */       if (this.completer != null) {
/* 132 */         completions = this.completer.onTabComplete(sender, this, alias, args);
/*     */       }
/* 134 */       if ((completions == null) && ((this.executor instanceof TabCompleter))) {
/* 135 */         completions = ((TabCompleter)this.executor).onTabComplete(sender, this, alias, args);
/*     */       }
/*     */     } catch (Throwable ex) {
/* 138 */       StringBuilder message = new StringBuilder();
/* 139 */       message.append("Unhandled exception during tab completion for command '/").append(alias).append(' ');
/* 140 */       String[] arrayOfString; int i = (arrayOfString = args).length; for (int j = 0; j < i; j++) { String arg = arrayOfString[j];
/* 141 */         message.append(arg).append(' ');
/*     */       }
/* 143 */       message.deleteCharAt(message.length() - 1).append("' in plugin ").append(this.owningPlugin.getDescription().getFullName());
/* 144 */       throw new CommandException(message.toString(), ex);
/*     */     }
/*     */     
/* 147 */     if (completions == null) {
/* 148 */       return super.tabComplete(sender, alias, args);
/*     */     }
/* 150 */     return completions;
/*     */   }
/*     */   
/*     */   public String toString()
/*     */   {
/* 155 */     StringBuilder stringBuilder = new StringBuilder(super.toString());
/* 156 */     stringBuilder.deleteCharAt(stringBuilder.length() - 1);
/* 157 */     stringBuilder.append(", ").append(this.owningPlugin.getDescription().getFullName()).append(')');
/* 158 */     return stringBuilder.toString();
/*     */   }
/*     */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\org\bukkit\command\PluginCommand.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */
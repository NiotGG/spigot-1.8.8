/*     */ package org.bukkit.command;
/*     */ 
/*     */ import com.google.common.collect.ImmutableList;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Collections;
/*     */ import java.util.List;
/*     */ import java.util.Set;
/*     */ import org.apache.commons.lang.Validate;
/*     */ import org.bukkit.Bukkit;
/*     */ import org.bukkit.ChatColor;
/*     */ import org.bukkit.Server;
/*     */ import org.bukkit.World;
/*     */ import org.bukkit.block.Block;
/*     */ import org.bukkit.entity.Player;
/*     */ import org.bukkit.entity.minecart.CommandMinecart;
/*     */ import org.bukkit.permissions.Permissible;
/*     */ import org.bukkit.plugin.PluginManager;
/*     */ import org.bukkit.util.StringUtil;
/*     */ import org.spigotmc.CustomTimingsHandler;
/*     */ 
/*     */ 
/*     */ public abstract class Command
/*     */ {
/*     */   private String name;
/*     */   private String nextLabel;
/*     */   private String label;
/*     */   private List<String> aliases;
/*     */   private List<String> activeAliases;
/*  29 */   private CommandMap commandMap = null;
/*  30 */   protected String description = "";
/*     */   protected String usageMessage;
/*     */   private String permission;
/*     */   private String permissionMessage;
/*     */   public CustomTimingsHandler timings;
/*     */   
/*     */   protected Command(String name) {
/*  37 */     this(name, "", "/" + name, new ArrayList());
/*     */   }
/*     */   
/*     */   protected Command(String name, String description, String usageMessage, List<String> aliases) {
/*  41 */     this.name = name;
/*  42 */     this.nextLabel = name;
/*  43 */     this.label = name;
/*  44 */     this.description = description;
/*  45 */     this.usageMessage = usageMessage;
/*  46 */     this.aliases = aliases;
/*  47 */     this.activeAliases = new ArrayList(aliases);
/*  48 */     this.timings = new CustomTimingsHandler("** Command: " + name);
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
/*     */   public abstract boolean execute(CommandSender paramCommandSender, String paramString, String[] paramArrayOfString);
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   @Deprecated
/*     */   public List<String> tabComplete(CommandSender sender, String[] args)
/*     */   {
/*  73 */     return null;
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
/*     */   public List<String> tabComplete(CommandSender sender, String alias, String[] args)
/*     */     throws IllegalArgumentException
/*     */   {
/*  88 */     Validate.notNull(sender, "Sender cannot be null");
/*  89 */     Validate.notNull(args, "Arguments cannot be null");
/*  90 */     Validate.notNull(alias, "Alias cannot be null");
/*     */     
/*  92 */     if (args.length == 0) {
/*  93 */       return ImmutableList.of();
/*     */     }
/*     */     
/*  96 */     String lastWord = args[(args.length - 1)];
/*     */     
/*  98 */     Player senderPlayer = (sender instanceof Player) ? (Player)sender : null;
/*     */     
/* 100 */     ArrayList<String> matchedPlayers = new ArrayList();
/* 101 */     for (Player player : sender.getServer().getOnlinePlayers()) {
/* 102 */       String name = player.getName();
/* 103 */       if (((senderPlayer == null) || (senderPlayer.canSee(player))) && (StringUtil.startsWithIgnoreCase(name, lastWord))) {
/* 104 */         matchedPlayers.add(name);
/*     */       }
/*     */     }
/*     */     
/* 108 */     Collections.sort(matchedPlayers, String.CASE_INSENSITIVE_ORDER);
/* 109 */     return matchedPlayers;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public String getName()
/*     */   {
/* 118 */     return this.name;
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
/*     */   public boolean setName(String name)
/*     */   {
/* 133 */     if (!isRegistered()) {
/* 134 */       this.name = name;
/* 135 */       return true;
/*     */     }
/* 137 */     return false;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public String getPermission()
/*     */   {
/* 147 */     return this.permission;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public void setPermission(String permission)
/*     */   {
/* 157 */     this.permission = permission;
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
/*     */   public boolean testPermission(CommandSender target)
/*     */   {
/* 171 */     if (testPermissionSilent(target)) {
/* 172 */       return true;
/*     */     }
/*     */     
/* 175 */     if (this.permissionMessage == null) {
/* 176 */       target.sendMessage(ChatColor.RED + "I'm sorry, but you do not have permission to perform this command. Please contact the server administrators if you believe that this is in error.");
/* 177 */     } else if (this.permissionMessage.length() != 0) { String[] arrayOfString;
/* 178 */       int i = (arrayOfString = this.permissionMessage.replace("<permission>", this.permission).split("\n")).length; for (int j = 0; j < i; j++) { String line = arrayOfString[j];
/* 179 */         target.sendMessage(line);
/*     */       }
/*     */     }
/*     */     
/* 183 */     return false;
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
/*     */   public boolean testPermissionSilent(CommandSender target)
/*     */   {
/* 196 */     if ((this.permission == null) || (this.permission.length() == 0)) {
/* 197 */       return true;
/*     */     }
/*     */     String[] arrayOfString;
/* 200 */     int i = (arrayOfString = this.permission.split(";")).length; for (int j = 0; j < i; j++) { String p = arrayOfString[j];
/* 201 */       if (target.hasPermission(p)) {
/* 202 */         return true;
/*     */       }
/*     */     }
/*     */     
/* 206 */     return false;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public String getLabel()
/*     */   {
/* 215 */     return this.label;
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
/*     */   public boolean setLabel(String name)
/*     */   {
/* 230 */     this.nextLabel = name;
/* 231 */     if (!isRegistered()) {
/* 232 */       this.timings = new CustomTimingsHandler("** Command: " + name);
/* 233 */       this.label = name;
/* 234 */       return true;
/*     */     }
/* 236 */     return false;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public boolean register(CommandMap commandMap)
/*     */   {
/* 248 */     if (allowChangesFrom(commandMap)) {
/* 249 */       this.commandMap = commandMap;
/* 250 */       return true;
/*     */     }
/*     */     
/* 253 */     return false;
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
/*     */   public boolean unregister(CommandMap commandMap)
/*     */   {
/* 266 */     if (allowChangesFrom(commandMap)) {
/* 267 */       this.commandMap = null;
/* 268 */       this.activeAliases = new ArrayList(this.aliases);
/* 269 */       this.label = this.nextLabel;
/* 270 */       return true;
/*     */     }
/*     */     
/* 273 */     return false;
/*     */   }
/*     */   
/*     */   private boolean allowChangesFrom(CommandMap commandMap) {
/* 277 */     return (this.commandMap == null) || (this.commandMap == commandMap);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public boolean isRegistered()
/*     */   {
/* 286 */     return this.commandMap != null;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public List<String> getAliases()
/*     */   {
/* 295 */     return this.activeAliases;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public String getPermissionMessage()
/*     */   {
/* 305 */     return this.permissionMessage;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public String getDescription()
/*     */   {
/* 314 */     return this.description;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public String getUsage()
/*     */   {
/* 323 */     return this.usageMessage;
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
/*     */   public Command setAliases(List<String> aliases)
/*     */   {
/* 336 */     this.aliases = aliases;
/* 337 */     if (!isRegistered()) {
/* 338 */       this.activeAliases = new ArrayList(aliases);
/*     */     }
/* 340 */     return this;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public Command setDescription(String description)
/*     */   {
/* 352 */     this.description = description;
/* 353 */     return this;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public Command setPermissionMessage(String permissionMessage)
/*     */   {
/* 364 */     this.permissionMessage = permissionMessage;
/* 365 */     return this;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public Command setUsage(String usage)
/*     */   {
/* 375 */     this.usageMessage = usage;
/* 376 */     return this;
/*     */   }
/*     */   
/*     */   public static void broadcastCommandMessage(CommandSender source, String message) {
/* 380 */     broadcastCommandMessage(source, message, true);
/*     */   }
/*     */   
/*     */   public static void broadcastCommandMessage(CommandSender source, String message, boolean sendToSource) {
/* 384 */     String result = source.getName() + ": " + message;
/*     */     
/* 386 */     if ((source instanceof BlockCommandSender)) {
/* 387 */       BlockCommandSender blockCommandSender = (BlockCommandSender)source;
/*     */       
/* 389 */       if (blockCommandSender.getBlock().getWorld().getGameRuleValue("commandBlockOutput").equalsIgnoreCase("false")) {
/* 390 */         Bukkit.getConsoleSender().sendMessage(result);
/*     */       }
/*     */     }
/* 393 */     else if ((source instanceof CommandMinecart)) {
/* 394 */       CommandMinecart commandMinecart = (CommandMinecart)source;
/*     */       
/* 396 */       if (commandMinecart.getWorld().getGameRuleValue("commandBlockOutput").equalsIgnoreCase("false")) {
/* 397 */         Bukkit.getConsoleSender().sendMessage(result);
/* 398 */         return;
/*     */       }
/*     */     }
/*     */     
/* 402 */     Set<Permissible> users = Bukkit.getPluginManager().getPermissionSubscriptions("bukkit.broadcast.admin");
/* 403 */     String colored = ChatColor.GRAY + ChatColor.ITALIC + "[" + result + ChatColor.GRAY + ChatColor.ITALIC + "]";
/*     */     
/* 405 */     if ((sendToSource) && (!(source instanceof ConsoleCommandSender))) {
/* 406 */       source.sendMessage(message);
/*     */     }
/*     */     
/* 409 */     for (Permissible user : users) {
/* 410 */       if ((user instanceof CommandSender)) {
/* 411 */         CommandSender target = (CommandSender)user;
/*     */         
/* 413 */         if ((target instanceof ConsoleCommandSender)) {
/* 414 */           target.sendMessage(result);
/* 415 */         } else if (target != source) {
/* 416 */           target.sendMessage(colored);
/*     */         }
/*     */       }
/*     */     }
/*     */   }
/*     */   
/*     */   public String toString()
/*     */   {
/* 424 */     return getClass().getName() + '(' + this.name + ')';
/*     */   }
/*     */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\org\bukkit\command\Command.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */
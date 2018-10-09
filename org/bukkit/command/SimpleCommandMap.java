/*     */ package org.bukkit.command;
/*     */ 
/*     */ import java.util.ArrayList;
/*     */ import java.util.Collections;
/*     */ import java.util.HashMap;
/*     */ import java.util.Iterator;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import java.util.Map.Entry;
/*     */ import java.util.logging.Logger;
/*     */ import java.util.regex.Pattern;
/*     */ import org.apache.commons.lang.Validate;
/*     */ import org.bukkit.Server;
/*     */ import org.bukkit.command.defaults.TimingsCommand;
/*     */ import org.bukkit.command.defaults.VersionCommand;
/*     */ import org.bukkit.util.Java15Compat;
/*     */ import org.spigotmc.CustomTimingsHandler;
/*     */ 
/*     */ public class SimpleCommandMap implements CommandMap
/*     */ {
/*  21 */   private static final Pattern PATTERN_ON_SPACE = Pattern.compile(" ", 16);
/*  22 */   protected final Map<String, Command> knownCommands = new HashMap();
/*     */   private final Server server;
/*     */   
/*     */   public SimpleCommandMap(Server server) {
/*  26 */     this.server = server;
/*  27 */     setDefaultCommands();
/*     */   }
/*     */   
/*     */   private void setDefaultCommands() {
/*  31 */     register("bukkit", new VersionCommand("version"));
/*  32 */     register("bukkit", new org.bukkit.command.defaults.ReloadCommand("reload"));
/*  33 */     register("bukkit", new org.bukkit.command.defaults.PluginsCommand("plugins"));
/*  34 */     register("bukkit", new TimingsCommand("timings"));
/*     */   }
/*     */   
/*     */   public void setFallbackCommands() {
/*  38 */     register("bukkit", new org.bukkit.command.defaults.HelpCommand());
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public void registerAll(String fallbackPrefix, List<Command> commands)
/*     */   {
/*  45 */     if (commands != null) {
/*  46 */       for (Command c : commands) {
/*  47 */         register(fallbackPrefix, c);
/*     */       }
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public boolean register(String fallbackPrefix, Command command)
/*     */   {
/*  56 */     return register(command.getName(), fallbackPrefix, command);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public boolean register(String label, String fallbackPrefix, Command command)
/*     */   {
/*  63 */     label = label.toLowerCase().trim();
/*  64 */     fallbackPrefix = fallbackPrefix.toLowerCase().trim();
/*  65 */     boolean registered = register(label, command, false, fallbackPrefix);
/*     */     
/*  67 */     Iterator<String> iterator = command.getAliases().iterator();
/*  68 */     while (iterator.hasNext()) {
/*  69 */       if (!register((String)iterator.next(), command, true, fallbackPrefix)) {
/*  70 */         iterator.remove();
/*     */       }
/*     */     }
/*     */     
/*     */ 
/*  75 */     if (!registered) {
/*  76 */       command.setLabel(fallbackPrefix + ":" + label);
/*     */     }
/*     */     
/*     */ 
/*  80 */     command.register(this);
/*     */     
/*  82 */     return registered;
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
/*     */   private synchronized boolean register(String label, Command command, boolean isAlias, String fallbackPrefix)
/*     */   {
/*  97 */     this.knownCommands.put(fallbackPrefix + ":" + label, command);
/*  98 */     if ((((command instanceof org.bukkit.command.defaults.VanillaCommand)) || (isAlias)) && (this.knownCommands.containsKey(label)))
/*     */     {
/*     */ 
/*     */ 
/* 102 */       return false;
/*     */     }
/*     */     
/* 105 */     boolean registered = true;
/*     */     
/*     */ 
/* 108 */     Command conflict = (Command)this.knownCommands.get(label);
/* 109 */     if ((conflict != null) && (conflict.getLabel().equals(label))) {
/* 110 */       return false;
/*     */     }
/*     */     
/* 113 */     if (!isAlias) {
/* 114 */       command.setLabel(label);
/*     */     }
/* 116 */     this.knownCommands.put(label, command);
/*     */     
/* 118 */     return registered;
/*     */   }
/*     */   
/*     */ 
/*     */   public boolean dispatch(CommandSender sender, String commandLine)
/*     */     throws CommandException
/*     */   {
/* 125 */     String[] args = PATTERN_ON_SPACE.split(commandLine);
/*     */     
/* 127 */     if (args.length == 0) {
/* 128 */       return false;
/*     */     }
/*     */     
/* 131 */     String sentCommandLabel = args[0].toLowerCase();
/* 132 */     Command target = getCommand(sentCommandLabel);
/*     */     
/* 134 */     if (target == null) {
/* 135 */       return false;
/*     */     }
/*     */     try
/*     */     {
/* 139 */       target.timings.startTiming();
/*     */       
/* 141 */       target.execute(sender, sentCommandLabel, (String[])Java15Compat.Arrays_copyOfRange(args, 1, args.length));
/* 142 */       target.timings.stopTiming();
/*     */     } catch (CommandException ex) {
/* 144 */       target.timings.stopTiming();
/* 145 */       throw ex;
/*     */     } catch (Throwable ex) {
/* 147 */       target.timings.stopTiming();
/* 148 */       throw new CommandException("Unhandled exception executing '" + commandLine + "' in " + target, ex);
/*     */     }
/*     */     
/*     */ 
/* 152 */     return true;
/*     */   }
/*     */   
/*     */   public synchronized void clearCommands() {
/* 156 */     for (Map.Entry<String, Command> entry : this.knownCommands.entrySet()) {
/* 157 */       ((Command)entry.getValue()).unregister(this);
/*     */     }
/* 159 */     this.knownCommands.clear();
/* 160 */     setDefaultCommands();
/*     */   }
/*     */   
/*     */   public Command getCommand(String name) {
/* 164 */     Command target = (Command)this.knownCommands.get(name.toLowerCase());
/* 165 */     return target;
/*     */   }
/*     */   
/*     */   public List<String> tabComplete(CommandSender sender, String cmdLine) {
/* 169 */     Validate.notNull(sender, "Sender cannot be null");
/* 170 */     Validate.notNull(cmdLine, "Command line cannot null");
/*     */     
/* 172 */     int spaceIndex = cmdLine.indexOf(' ');
/*     */     
/* 174 */     if (spaceIndex == -1) {
/* 175 */       ArrayList<String> completions = new ArrayList();
/* 176 */       Map<String, Command> knownCommands = this.knownCommands;
/*     */       
/* 178 */       String prefix = (sender instanceof org.bukkit.entity.Player) ? "/" : "";
/*     */       
/* 180 */       for (Map.Entry<String, Command> commandEntry : knownCommands.entrySet()) {
/* 181 */         Command command = (Command)commandEntry.getValue();
/*     */         
/* 183 */         if (command.testPermissionSilent(sender))
/*     */         {
/*     */ 
/*     */ 
/* 187 */           String name = (String)commandEntry.getKey();
/*     */           
/* 189 */           if (org.bukkit.util.StringUtil.startsWithIgnoreCase(name, cmdLine)) {
/* 190 */             completions.add(prefix + name);
/*     */           }
/*     */         }
/*     */       }
/* 194 */       Collections.sort(completions, String.CASE_INSENSITIVE_ORDER);
/* 195 */       return completions;
/*     */     }
/*     */     
/* 198 */     String commandName = cmdLine.substring(0, spaceIndex);
/* 199 */     Command target = getCommand(commandName);
/*     */     
/* 201 */     if (target == null) {
/* 202 */       return null;
/*     */     }
/*     */     
/* 205 */     if (!target.testPermissionSilent(sender)) {
/* 206 */       return null;
/*     */     }
/*     */     
/* 209 */     String argLine = cmdLine.substring(spaceIndex + 1, cmdLine.length());
/* 210 */     String[] args = PATTERN_ON_SPACE.split(argLine, -1);
/*     */     try
/*     */     {
/* 213 */       return target.tabComplete(sender, commandName, args);
/*     */     } catch (CommandException ex) {
/* 215 */       throw ex;
/*     */     } catch (Throwable ex) {
/* 217 */       throw new CommandException("Unhandled exception executing tab-completer for '" + cmdLine + "' in " + target, ex);
/*     */     }
/*     */   }
/*     */   
/*     */   public java.util.Collection<Command> getCommands() {
/* 222 */     return Collections.unmodifiableCollection(this.knownCommands.values());
/*     */   }
/*     */   
/*     */   public void registerServerAliases() {
/* 226 */     Map<String, String[]> values = this.server.getCommandAliases();
/*     */     
/* 228 */     for (String alias : values.keySet()) {
/* 229 */       if ((alias.contains(":")) || (alias.contains(" "))) {
/* 230 */         this.server.getLogger().warning("Could not register alias " + alias + " because it contains illegal characters");
/*     */       }
/*     */       else
/*     */       {
/* 234 */         String[] commandStrings = (String[])values.get(alias);
/* 235 */         List<String> targets = new ArrayList();
/* 236 */         StringBuilder bad = new StringBuilder();
/*     */         String[] arrayOfString1;
/* 238 */         int i = (arrayOfString1 = commandStrings).length; for (int j = 0; j < i; j++) { String commandString = arrayOfString1[j];
/* 239 */           String[] commandArgs = commandString.split(" ");
/* 240 */           Command command = getCommand(commandArgs[0]);
/*     */           
/* 242 */           if (command == null) {
/* 243 */             if (bad.length() > 0) {
/* 244 */               bad.append(", ");
/*     */             }
/* 246 */             bad.append(commandString);
/*     */           } else {
/* 248 */             targets.add(commandString);
/*     */           }
/*     */         }
/*     */         
/* 252 */         if (bad.length() > 0) {
/* 253 */           this.server.getLogger().warning("Could not register alias " + alias + " because it contains commands that do not exist: " + bad);
/*     */ 
/*     */ 
/*     */ 
/*     */         }
/* 258 */         else if (targets.size() > 0) {
/* 259 */           this.knownCommands.put(alias.toLowerCase(), new FormattedCommandAlias(alias.toLowerCase(), (String[])targets.toArray(new String[targets.size()])));
/*     */         } else {
/* 261 */           this.knownCommands.remove(alias.toLowerCase());
/*     */         }
/*     */       }
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\org\bukkit\command\SimpleCommandMap.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */
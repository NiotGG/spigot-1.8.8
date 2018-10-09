/*     */ package org.bukkit.command.defaults;
/*     */ 
/*     */ import com.google.common.base.Charsets;
/*     */ import com.google.common.collect.ImmutableList;
/*     */ import com.google.common.io.CharSource;
/*     */ import com.google.common.io.Resources;
/*     */ import java.io.BufferedReader;
/*     */ import java.io.IOException;
/*     */ import java.net.URL;
/*     */ import java.net.URLEncoder;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Arrays;
/*     */ import java.util.HashSet;
/*     */ import java.util.List;
/*     */ import java.util.Set;
/*     */ import java.util.concurrent.locks.ReentrantLock;
/*     */ import org.apache.commons.lang.Validate;
/*     */ import org.bukkit.Bukkit;
/*     */ import org.bukkit.ChatColor;
/*     */ import org.bukkit.command.CommandSender;
/*     */ import org.bukkit.plugin.Plugin;
/*     */ import org.bukkit.plugin.PluginDescriptionFile;
/*     */ import org.bukkit.plugin.PluginManager;
/*     */ import org.bukkit.util.StringUtil;
/*     */ import org.json.simple.JSONObject;
/*     */ import org.json.simple.parser.JSONParser;
/*     */ import org.json.simple.parser.ParseException;
/*     */ 
/*     */ public class VersionCommand extends BukkitCommand
/*     */ {
/*     */   public VersionCommand(String name)
/*     */   {
/*  33 */     super(name);
/*     */     
/*  35 */     this.description = "Gets the version of this server including any plugins in use";
/*  36 */     this.usageMessage = "/version [plugin name]";
/*  37 */     setPermission("bukkit.command.version");
/*  38 */     setAliases(Arrays.asList(new String[] { "ver", "about" }));
/*     */   }
/*     */   
/*     */   public boolean execute(CommandSender sender, String currentAlias, String[] args)
/*     */   {
/*  43 */     if (!testPermission(sender)) { return true;
/*     */     }
/*  45 */     if (args.length == 0) {
/*  46 */       sender.sendMessage("This server is running " + Bukkit.getName() + " version " + Bukkit.getVersion() + " (Implementing API version " + Bukkit.getBukkitVersion() + ")");
/*  47 */       sendVersion(sender);
/*     */     } else {
/*  49 */       StringBuilder name = new StringBuilder();
/*     */       String[] arrayOfString;
/*  51 */       int i = (arrayOfString = args).length; for (int j = 0; j < i; j++) { String arg = arrayOfString[j];
/*  52 */         if (name.length() > 0) {
/*  53 */           name.append(' ');
/*     */         }
/*     */         
/*  56 */         name.append(arg);
/*     */       }
/*     */       
/*  59 */       String pluginName = name.toString();
/*  60 */       Plugin exactPlugin = Bukkit.getPluginManager().getPlugin(pluginName);
/*  61 */       if (exactPlugin != null) {
/*  62 */         describeToSender(exactPlugin, sender);
/*  63 */         return true;
/*     */       }
/*     */       
/*  66 */       boolean found = false;
/*  67 */       pluginName = pluginName.toLowerCase();
/*  68 */       Plugin[] arrayOfPlugin; int k = (arrayOfPlugin = Bukkit.getPluginManager().getPlugins()).length; for (int m = 0; m < k; m++) { Plugin plugin = arrayOfPlugin[m];
/*  69 */         if (plugin.getName().toLowerCase().contains(pluginName)) {
/*  70 */           describeToSender(plugin, sender);
/*  71 */           found = true;
/*     */         }
/*     */       }
/*     */       
/*  75 */       if (!found) {
/*  76 */         sender.sendMessage("This server is not running any plugin by that name.");
/*  77 */         sender.sendMessage("Use /plugins to get a list of plugins.");
/*     */       }
/*     */     }
/*  80 */     return true;
/*     */   }
/*     */   
/*     */   private void describeToSender(Plugin plugin, CommandSender sender) {
/*  84 */     PluginDescriptionFile desc = plugin.getDescription();
/*  85 */     sender.sendMessage(ChatColor.GREEN + desc.getName() + ChatColor.WHITE + " version " + ChatColor.GREEN + desc.getVersion());
/*     */     
/*  87 */     if (desc.getDescription() != null) {
/*  88 */       sender.sendMessage(desc.getDescription());
/*     */     }
/*     */     
/*  91 */     if (desc.getWebsite() != null) {
/*  92 */       sender.sendMessage("Website: " + ChatColor.GREEN + desc.getWebsite());
/*     */     }
/*     */     
/*  95 */     if (!desc.getAuthors().isEmpty()) {
/*  96 */       if (desc.getAuthors().size() == 1) {
/*  97 */         sender.sendMessage("Author: " + getAuthors(desc));
/*     */       } else {
/*  99 */         sender.sendMessage("Authors: " + getAuthors(desc));
/*     */       }
/*     */     }
/*     */   }
/*     */   
/*     */   private String getAuthors(PluginDescriptionFile desc) {
/* 105 */     StringBuilder result = new StringBuilder();
/* 106 */     List<String> authors = desc.getAuthors();
/*     */     
/* 108 */     for (int i = 0; i < authors.size(); i++) {
/* 109 */       if (result.length() > 0) {
/* 110 */         result.append(ChatColor.WHITE);
/*     */         
/* 112 */         if (i < authors.size() - 1) {
/* 113 */           result.append(", ");
/*     */         } else {
/* 115 */           result.append(" and ");
/*     */         }
/*     */       }
/*     */       
/* 119 */       result.append(ChatColor.GREEN);
/* 120 */       result.append((String)authors.get(i));
/*     */     }
/*     */     
/* 123 */     return result.toString();
/*     */   }
/*     */   
/*     */   public List<String> tabComplete(CommandSender sender, String alias, String[] args)
/*     */   {
/* 128 */     Validate.notNull(sender, "Sender cannot be null");
/* 129 */     Validate.notNull(args, "Arguments cannot be null");
/* 130 */     Validate.notNull(alias, "Alias cannot be null");
/*     */     
/* 132 */     if (args.length == 1) {
/* 133 */       List<String> completions = new ArrayList();
/* 134 */       String toComplete = args[0].toLowerCase();
/* 135 */       Plugin[] arrayOfPlugin; int i = (arrayOfPlugin = Bukkit.getPluginManager().getPlugins()).length; for (int j = 0; j < i; j++) { Plugin plugin = arrayOfPlugin[j];
/* 136 */         if (StringUtil.startsWithIgnoreCase(plugin.getName(), toComplete)) {
/* 137 */           completions.add(plugin.getName());
/*     */         }
/*     */       }
/* 140 */       return completions;
/*     */     }
/* 142 */     return ImmutableList.of();
/*     */   }
/*     */   
/* 145 */   private final ReentrantLock versionLock = new ReentrantLock();
/* 146 */   private boolean hasVersion = false;
/* 147 */   private String versionMessage = null;
/* 148 */   private final Set<CommandSender> versionWaiters = new HashSet();
/* 149 */   private boolean versionTaskStarted = false;
/* 150 */   private long lastCheck = 0L;
/*     */   
/*     */   private void sendVersion(CommandSender sender) {
/* 153 */     if (this.hasVersion) {
/* 154 */       if (System.currentTimeMillis() - this.lastCheck > 21600000L) {
/* 155 */         this.lastCheck = System.currentTimeMillis();
/* 156 */         this.hasVersion = false;
/*     */       } else {
/* 158 */         sender.sendMessage(this.versionMessage);
/* 159 */         return;
/*     */       }
/*     */     }
/* 162 */     this.versionLock.lock();
/*     */     try {
/* 164 */       if (this.hasVersion) {
/* 165 */         sender.sendMessage(this.versionMessage);
/* 166 */         return;
/*     */       }
/* 168 */       this.versionWaiters.add(sender);
/* 169 */       sender.sendMessage("Checking version, please wait...");
/* 170 */       if (!this.versionTaskStarted) {
/* 171 */         this.versionTaskStarted = true;
/* 172 */         new Thread(new Runnable()
/*     */         {
/*     */           public void run()
/*     */           {
/* 176 */             VersionCommand.this.obtainVersion();
/*     */           }
/*     */         })
/*     */         
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/* 178 */           .start();
/*     */       }
/*     */     } finally {
/* 181 */       this.versionLock.unlock(); } this.versionLock.unlock();
/*     */   }
/*     */   
/*     */   private void obtainVersion()
/*     */   {
/* 186 */     String version = Bukkit.getVersion();
/* 187 */     if (version == null) version = "Custom";
/* 188 */     if (version.startsWith("git-Spigot-")) {
/* 189 */       String[] parts = version.substring("git-Spigot-".length()).split("-");
/* 190 */       int cbVersions = getDistance("craftbukkit", parts[1].substring(0, parts[1].indexOf(' ')));
/* 191 */       int spigotVersions = getDistance("spigot", parts[0]);
/* 192 */       if ((cbVersions == -1) || (spigotVersions == -1)) {
/* 193 */         setVersionMessage("Error obtaining version information");
/*     */       }
/* 195 */       else if ((cbVersions == 0) && (spigotVersions == 0)) {
/* 196 */         setVersionMessage("You are running the latest version");
/*     */       } else {
/* 198 */         setVersionMessage("You are " + (cbVersions + spigotVersions) + " version(s) behind");
/*     */       }
/*     */       
/*     */     }
/* 202 */     else if (version.startsWith("git-Bukkit-")) {
/* 203 */       version = version.substring("git-Bukkit-".length());
/* 204 */       int cbVersions = getDistance("craftbukkit", version.substring(0, version.indexOf(' ')));
/* 205 */       if (cbVersions == -1) {
/* 206 */         setVersionMessage("Error obtaining version information");
/*     */       }
/* 208 */       else if (cbVersions == 0) {
/* 209 */         setVersionMessage("You are running the latest version");
/*     */       } else {
/* 211 */         setVersionMessage("You are " + cbVersions + " version(s) behind");
/*     */       }
/*     */     }
/*     */     else {
/* 215 */       setVersionMessage("Unknown version, custom build?");
/*     */     }
/*     */   }
/*     */   
/*     */   /* Error */
/*     */   private void setVersionMessage(String msg)
/*     */   {
/*     */     // Byte code:
/*     */     //   0: aload_0
/*     */     //   1: invokestatic 282	java/lang/System:currentTimeMillis	()J
/*     */     //   4: putfield 43	org/bukkit/command/defaults/VersionCommand:lastCheck	J
/*     */     //   7: aload_0
/*     */     //   8: aload_1
/*     */     //   9: putfield 34	org/bukkit/command/defaults/VersionCommand:versionMessage	Ljava/lang/String;
/*     */     //   12: aload_0
/*     */     //   13: getfield 30	org/bukkit/command/defaults/VersionCommand:versionLock	Ljava/util/concurrent/locks/ReentrantLock;
/*     */     //   16: invokevirtual 287	java/util/concurrent/locks/ReentrantLock:lock	()V
/*     */     //   19: aload_0
/*     */     //   20: iconst_1
/*     */     //   21: putfield 32	org/bukkit/command/defaults/VersionCommand:hasVersion	Z
/*     */     //   24: aload_0
/*     */     //   25: iconst_0
/*     */     //   26: putfield 41	org/bukkit/command/defaults/VersionCommand:versionTaskStarted	Z
/*     */     //   29: aload_0
/*     */     //   30: getfield 39	org/bukkit/command/defaults/VersionCommand:versionWaiters	Ljava/util/Set;
/*     */     //   33: invokeinterface 369 1 0
/*     */     //   38: astore_2
/*     */     //   39: goto +23 -> 62
/*     */     //   42: aload_2
/*     */     //   43: invokeinterface 375 1 0
/*     */     //   48: checkcast 115	org/bukkit/command/CommandSender
/*     */     //   51: astore_3
/*     */     //   52: aload_3
/*     */     //   53: aload_0
/*     */     //   54: getfield 34	org/bukkit/command/defaults/VersionCommand:versionMessage	Ljava/lang/String;
/*     */     //   57: invokeinterface 118 2 0
/*     */     //   62: aload_2
/*     */     //   63: invokeinterface 378 1 0
/*     */     //   68: ifne -26 -> 42
/*     */     //   71: aload_0
/*     */     //   72: getfield 39	org/bukkit/command/defaults/VersionCommand:versionWaiters	Ljava/util/Set;
/*     */     //   75: invokeinterface 381 1 0
/*     */     //   80: goto +15 -> 95
/*     */     //   83: astore 4
/*     */     //   85: aload_0
/*     */     //   86: getfield 30	org/bukkit/command/defaults/VersionCommand:versionLock	Ljava/util/concurrent/locks/ReentrantLock;
/*     */     //   89: invokevirtual 290	java/util/concurrent/locks/ReentrantLock:unlock	()V
/*     */     //   92: aload 4
/*     */     //   94: athrow
/*     */     //   95: aload_0
/*     */     //   96: getfield 30	org/bukkit/command/defaults/VersionCommand:versionLock	Ljava/util/concurrent/locks/ReentrantLock;
/*     */     //   99: invokevirtual 290	java/util/concurrent/locks/ReentrantLock:unlock	()V
/*     */     //   102: return
/*     */     // Line number table:
/*     */     //   Java source line #220	-> byte code offset #0
/*     */     //   Java source line #221	-> byte code offset #7
/*     */     //   Java source line #222	-> byte code offset #12
/*     */     //   Java source line #224	-> byte code offset #19
/*     */     //   Java source line #225	-> byte code offset #24
/*     */     //   Java source line #226	-> byte code offset #29
/*     */     //   Java source line #227	-> byte code offset #52
/*     */     //   Java source line #226	-> byte code offset #62
/*     */     //   Java source line #229	-> byte code offset #71
/*     */     //   Java source line #230	-> byte code offset #80
/*     */     //   Java source line #231	-> byte code offset #85
/*     */     //   Java source line #232	-> byte code offset #92
/*     */     //   Java source line #231	-> byte code offset #95
/*     */     //   Java source line #233	-> byte code offset #102
/*     */     // Local variable table:
/*     */     //   start	length	slot	name	signature
/*     */     //   0	103	0	this	VersionCommand
/*     */     //   0	103	1	msg	String
/*     */     //   38	25	2	localIterator	java.util.Iterator
/*     */     //   51	2	3	sender	CommandSender
/*     */     //   83	10	4	localObject	Object
/*     */     // Exception table:
/*     */     //   from	to	target	type
/*     */     //   19	83	83	finally
/*     */   }
/*     */   
/*     */   private static int getDistance(String repo, String hash)
/*     */   {
/*     */     try
/*     */     {
/* 237 */       BufferedReader reader = Resources.asCharSource(
/* 238 */         new URL("https://hub.spigotmc.org/stash/rest/api/1.0/projects/SPIGOT/repos/" + repo + "/commits?since=" + URLEncoder.encode(hash, "UTF-8") + "&withCounts=true"), 
/* 239 */         Charsets.UTF_8)
/* 240 */         .openBufferedStream();
/*     */       try {
/* 242 */         JSONObject obj = (JSONObject)new JSONParser().parse(reader);
/* 243 */         return ((Number)obj.get("totalCount")).intValue();
/*     */       } catch (ParseException ex) {
/* 245 */         ex.printStackTrace();
/* 246 */         return -1;
/*     */       } finally {
/* 248 */         reader.close();
/*     */       }
/*     */       
/*     */ 
/* 252 */       return -1;
/*     */     }
/*     */     catch (IOException e)
/*     */     {
/* 251 */       e.printStackTrace();
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\org\bukkit\command\defaults\VersionCommand.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */
/*     */ package org.bukkit.craftbukkit.v1_8_R3.help;
/*     */ 
/*     */ import com.google.common.base.Predicates;
/*     */ import java.util.Collection;
/*     */ import java.util.HashMap;
/*     */ import java.util.Map;
/*     */ import java.util.Map.Entry;
/*     */ import java.util.Set;
/*     */ import org.bukkit.command.Command;
/*     */ import org.bukkit.command.PluginCommand;
/*     */ import org.bukkit.command.PluginIdentifiableCommand;
/*     */ import org.bukkit.command.SimpleCommandMap;
/*     */ import org.bukkit.craftbukkit.v1_8_R3.CraftServer;
/*     */ import org.bukkit.help.HelpTopic;
/*     */ import org.bukkit.help.HelpTopicFactory;
/*     */ import org.bukkit.help.IndexHelpTopic;
/*     */ import org.bukkit.plugin.Plugin;
/*     */ 
/*     */ public class SimpleHelpMap implements org.bukkit.help.HelpMap
/*     */ {
/*     */   private HelpTopic defaultTopic;
/*     */   private final Map<String, HelpTopic> helpTopics;
/*     */   private final Map<Class, HelpTopicFactory<Command>> topicFactoryMap;
/*     */   private final CraftServer server;
/*     */   private HelpYamlReader yaml;
/*     */   
/*     */   public SimpleHelpMap(CraftServer server)
/*     */   {
/*  29 */     this.helpTopics = new java.util.TreeMap(org.bukkit.help.HelpTopicComparator.topicNameComparatorInstance());
/*  30 */     this.topicFactoryMap = new HashMap();
/*  31 */     this.server = server;
/*  32 */     this.yaml = new HelpYamlReader(server);
/*     */     
/*  34 */     com.google.common.base.Predicate indexFilter = Predicates.not(Predicates.instanceOf(CommandAliasHelpTopic.class));
/*  35 */     if (!this.yaml.commandTopicsInMasterIndex()) {
/*  36 */       indexFilter = Predicates.and(indexFilter, Predicates.not(new IsCommandTopicPredicate(null)));
/*     */     }
/*     */     
/*  39 */     this.defaultTopic = new IndexHelpTopic("Index", null, null, com.google.common.collect.Collections2.filter(this.helpTopics.values(), indexFilter), "Use /help [n] to get page n of help.");
/*     */     
/*  41 */     registerHelpTopicFactory(org.bukkit.command.MultipleCommandAlias.class, new MultipleCommandAliasHelpTopicFactory());
/*     */   }
/*     */   
/*     */   public synchronized HelpTopic getHelpTopic(String topicName) {
/*  45 */     if (topicName.equals("")) {
/*  46 */       return this.defaultTopic;
/*     */     }
/*     */     
/*  49 */     if (this.helpTopics.containsKey(topicName)) {
/*  50 */       return (HelpTopic)this.helpTopics.get(topicName);
/*     */     }
/*     */     
/*  53 */     return null;
/*     */   }
/*     */   
/*     */   public Collection<HelpTopic> getHelpTopics() {
/*  57 */     return this.helpTopics.values();
/*     */   }
/*     */   
/*     */   public synchronized void addTopic(HelpTopic topic)
/*     */   {
/*  62 */     if (!this.helpTopics.containsKey(topic.getName())) {
/*  63 */       this.helpTopics.put(topic.getName(), topic);
/*     */     }
/*     */   }
/*     */   
/*     */   public synchronized void clear() {
/*  68 */     this.helpTopics.clear();
/*     */   }
/*     */   
/*     */   public java.util.List<String> getIgnoredPlugins() {
/*  72 */     return this.yaml.getIgnoredPlugins();
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public synchronized void initializeGeneralTopics()
/*     */   {
/*  79 */     this.yaml = new HelpYamlReader(this.server);
/*     */     
/*     */ 
/*  82 */     for (HelpTopic topic : this.yaml.getGeneralTopics()) {
/*  83 */       addTopic(topic);
/*     */     }
/*     */     
/*     */ 
/*  87 */     for (HelpTopic topic : this.yaml.getIndexTopics()) {
/*  88 */       if (topic.getName().equals("Default")) {
/*  89 */         this.defaultTopic = topic;
/*     */       } else {
/*  91 */         addTopic(topic);
/*     */       }
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public synchronized void initializeCommands()
/*     */   {
/* 101 */     Set<String> ignoredPlugins = new java.util.HashSet(this.yaml.getIgnoredPlugins());
/*     */     
/*     */ 
/* 104 */     if (ignoredPlugins.contains("All")) {
/* 105 */       return;
/*     */     }
/*     */     
/*     */ 
/* 109 */     for (Command command : this.server.getCommandMap().getCommands()) {
/* 110 */       if (!commandInIgnoredPlugin(command, ignoredPlugins))
/*     */       {
/*     */ 
/*     */ 
/*     */ 
/* 115 */         for (Class c : this.topicFactoryMap.keySet()) {
/* 116 */           if (c.isAssignableFrom(command.getClass())) {
/* 117 */             HelpTopic t = ((HelpTopicFactory)this.topicFactoryMap.get(c)).createTopic(command);
/* 118 */             if (t == null) break; addTopic(t);
/* 119 */             break;
/*     */           }
/* 121 */           if (((command instanceof PluginCommand)) && (c.isAssignableFrom(((PluginCommand)command).getExecutor().getClass()))) {
/* 122 */             HelpTopic t = ((HelpTopicFactory)this.topicFactoryMap.get(c)).createTopic(command);
/* 123 */             if (t == null) break; addTopic(t);
/* 124 */             break;
/*     */           }
/*     */         }
/* 127 */         addTopic(new org.bukkit.help.GenericCommandHelpTopic(command));
/*     */       }
/*     */     }
/*     */     
/* 131 */     for (Command command : this.server.getCommandMap().getCommands()) {
/* 132 */       if (!commandInIgnoredPlugin(command, ignoredPlugins))
/*     */       {
/*     */ 
/* 135 */         for (String alias : command.getAliases())
/*     */         {
/* 137 */           if (this.server.getCommandMap().getCommand(alias) == command) {
/* 138 */             addTopic(new CommandAliasHelpTopic("/" + alias, "/" + command.getLabel(), this));
/*     */           }
/*     */         }
/*     */       }
/*     */     }
/*     */     
/* 144 */     Collection<HelpTopic> filteredTopics = com.google.common.collect.Collections2.filter(this.helpTopics.values(), Predicates.instanceOf(CommandAliasHelpTopic.class));
/* 145 */     if (!filteredTopics.isEmpty()) {
/* 146 */       addTopic(new IndexHelpTopic("Aliases", "Lists command aliases", null, filteredTopics));
/*     */     }
/*     */     
/*     */ 
/* 150 */     Object pluginIndexes = new HashMap();
/* 151 */     fillPluginIndexes((Map)pluginIndexes, this.server.getCommandMap().getCommands());
/*     */     
/* 153 */     for (Map.Entry<String, Set<HelpTopic>> entry : ((Map)pluginIndexes).entrySet()) {
/* 154 */       addTopic(new IndexHelpTopic((String)entry.getKey(), "All commands for " + (String)entry.getKey(), null, (Collection)entry.getValue(), "Below is a list of all " + (String)entry.getKey() + " commands:"));
/*     */     }
/*     */     
/*     */ 
/* 158 */     for (HelpTopicAmendment amendment : this.yaml.getTopicAmendments()) {
/* 159 */       if (this.helpTopics.containsKey(amendment.getTopicName())) {
/* 160 */         ((HelpTopic)this.helpTopics.get(amendment.getTopicName())).amendTopic(amendment.getShortText(), amendment.getFullText());
/* 161 */         if (amendment.getPermission() != null) {
/* 162 */           ((HelpTopic)this.helpTopics.get(amendment.getTopicName())).amendCanSee(amendment.getPermission());
/*     */         }
/*     */       }
/*     */     }
/*     */   }
/*     */   
/*     */   private void fillPluginIndexes(Map<String, Set<HelpTopic>> pluginIndexes, Collection<? extends Command> commands) {
/* 169 */     for (Command command : commands) {
/* 170 */       String pluginName = getCommandPluginName(command);
/* 171 */       if (pluginName != null) {
/* 172 */         HelpTopic topic = getHelpTopic("/" + command.getLabel());
/* 173 */         if (topic != null) {
/* 174 */           if (!pluginIndexes.containsKey(pluginName)) {
/* 175 */             pluginIndexes.put(pluginName, new java.util.TreeSet(org.bukkit.help.HelpTopicComparator.helpTopicComparatorInstance()));
/*     */           }
/* 177 */           ((Set)pluginIndexes.get(pluginName)).add(topic);
/*     */         }
/*     */       }
/*     */     }
/*     */   }
/*     */   
/*     */   private String getCommandPluginName(Command command) {
/* 184 */     if ((command instanceof org.bukkit.craftbukkit.v1_8_R3.command.VanillaCommandWrapper)) {
/* 185 */       return "Minecraft";
/*     */     }
/* 187 */     if (((command instanceof org.bukkit.command.defaults.BukkitCommand)) || ((command instanceof org.bukkit.command.defaults.VanillaCommand))) {
/* 188 */       return "Bukkit";
/*     */     }
/* 190 */     if ((command instanceof PluginIdentifiableCommand)) {
/* 191 */       return ((PluginIdentifiableCommand)command).getPlugin().getName();
/*     */     }
/* 193 */     return null;
/*     */   }
/*     */   
/*     */   private boolean commandInIgnoredPlugin(Command command, Set<String> ignoredPlugins) {
/* 197 */     if ((((command instanceof org.bukkit.command.defaults.BukkitCommand)) || ((command instanceof org.bukkit.command.defaults.VanillaCommand))) && (ignoredPlugins.contains("Bukkit"))) {
/* 198 */       return true;
/*     */     }
/* 200 */     if (((command instanceof PluginIdentifiableCommand)) && (ignoredPlugins.contains(((PluginIdentifiableCommand)command).getPlugin().getName()))) {
/* 201 */       return true;
/*     */     }
/* 203 */     return false;
/*     */   }
/*     */   
/*     */   public void registerHelpTopicFactory(Class commandClass, HelpTopicFactory factory) {
/* 207 */     if ((!Command.class.isAssignableFrom(commandClass)) && (!org.bukkit.command.CommandExecutor.class.isAssignableFrom(commandClass))) {
/* 208 */       throw new IllegalArgumentException("commandClass must implement either Command or CommandExecutor!");
/*     */     }
/* 210 */     this.topicFactoryMap.put(commandClass, factory);
/*     */   }
/*     */   
/*     */   private class IsCommandTopicPredicate implements com.google.common.base.Predicate<HelpTopic> {
/*     */     private IsCommandTopicPredicate() {}
/*     */     
/* 216 */     public boolean apply(HelpTopic topic) { return topic.getName().charAt(0) == '/'; }
/*     */   }
/*     */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\org\bukkit\craftbukkit\v1_8_R3\help\SimpleHelpMap.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */
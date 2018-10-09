/*     */ package org.bukkit.craftbukkit.v1_8_R3.help;
/*     */ 
/*     */ import com.google.common.base.Charsets;
/*     */ import java.io.File;
/*     */ import java.io.IOException;
/*     */ import java.io.InputStreamReader;
/*     */ import java.util.LinkedList;
/*     */ import java.util.List;
/*     */ import java.util.logging.Level;
/*     */ import java.util.logging.Logger;
/*     */ import org.bukkit.ChatColor;
/*     */ import org.bukkit.Server;
/*     */ import org.bukkit.configuration.ConfigurationSection;
/*     */ import org.bukkit.configuration.file.YamlConfiguration;
/*     */ import org.bukkit.configuration.file.YamlConfigurationOptions;
/*     */ import org.bukkit.help.HelpTopic;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class HelpYamlReader
/*     */ {
/*     */   private YamlConfiguration helpYaml;
/*  24 */   private final char ALT_COLOR_CODE = '&';
/*     */   private final Server server;
/*     */   
/*     */   public HelpYamlReader(Server server) {
/*  28 */     this.server = server;
/*     */     
/*  30 */     File helpYamlFile = new File("help.yml");
/*  31 */     YamlConfiguration defaultConfig = YamlConfiguration.loadConfiguration(new InputStreamReader(getClass().getClassLoader().getResourceAsStream("configurations/help.yml"), Charsets.UTF_8));
/*     */     try
/*     */     {
/*  34 */       this.helpYaml = YamlConfiguration.loadConfiguration(helpYamlFile);
/*  35 */       this.helpYaml.options().copyDefaults(true);
/*  36 */       this.helpYaml.setDefaults(defaultConfig);
/*     */       try
/*     */       {
/*  39 */         if (!helpYamlFile.exists()) {
/*  40 */           this.helpYaml.save(helpYamlFile);
/*     */         }
/*     */       } catch (IOException ex) {
/*  43 */         server.getLogger().log(Level.SEVERE, "Could not save " + helpYamlFile, ex);
/*     */       }
/*     */       return;
/*  46 */     } catch (Exception localException) { server.getLogger().severe("Failed to load help.yml. Verify the yaml indentation is correct. Reverting to default help.yml.");
/*  47 */       this.helpYaml = defaultConfig;
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public List<HelpTopic> getGeneralTopics()
/*     */   {
/*  57 */     List<HelpTopic> topics = new LinkedList();
/*  58 */     ConfigurationSection generalTopics = this.helpYaml.getConfigurationSection("general-topics");
/*  59 */     if (generalTopics != null) {
/*  60 */       for (String topicName : generalTopics.getKeys(false)) {
/*  61 */         ConfigurationSection section = generalTopics.getConfigurationSection(topicName);
/*  62 */         String shortText = ChatColor.translateAlternateColorCodes('&', section.getString("shortText", ""));
/*  63 */         String fullText = ChatColor.translateAlternateColorCodes('&', section.getString("fullText", ""));
/*  64 */         String permission = section.getString("permission", "");
/*  65 */         topics.add(new CustomHelpTopic(topicName, shortText, fullText, permission));
/*     */       }
/*     */     }
/*  68 */     return topics;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public List<HelpTopic> getIndexTopics()
/*     */   {
/*  77 */     List<HelpTopic> topics = new LinkedList();
/*  78 */     ConfigurationSection indexTopics = this.helpYaml.getConfigurationSection("index-topics");
/*  79 */     if (indexTopics != null) {
/*  80 */       for (String topicName : indexTopics.getKeys(false)) {
/*  81 */         ConfigurationSection section = indexTopics.getConfigurationSection(topicName);
/*  82 */         String shortText = ChatColor.translateAlternateColorCodes('&', section.getString("shortText", ""));
/*  83 */         String preamble = ChatColor.translateAlternateColorCodes('&', section.getString("preamble", ""));
/*  84 */         String permission = ChatColor.translateAlternateColorCodes('&', section.getString("permission", ""));
/*  85 */         List<String> commands = section.getStringList("commands");
/*  86 */         topics.add(new CustomIndexHelpTopic(this.server.getHelpMap(), topicName, shortText, permission, commands, preamble));
/*     */       }
/*     */     }
/*  89 */     return topics;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public List<HelpTopicAmendment> getTopicAmendments()
/*     */   {
/*  98 */     List<HelpTopicAmendment> amendments = new LinkedList();
/*  99 */     ConfigurationSection commandTopics = this.helpYaml.getConfigurationSection("amended-topics");
/* 100 */     if (commandTopics != null) {
/* 101 */       for (String topicName : commandTopics.getKeys(false)) {
/* 102 */         ConfigurationSection section = commandTopics.getConfigurationSection(topicName);
/* 103 */         String description = ChatColor.translateAlternateColorCodes('&', section.getString("shortText", ""));
/* 104 */         String usage = ChatColor.translateAlternateColorCodes('&', section.getString("fullText", ""));
/* 105 */         String permission = section.getString("permission", "");
/* 106 */         amendments.add(new HelpTopicAmendment(topicName, description, usage, permission));
/*     */       }
/*     */     }
/* 109 */     return amendments;
/*     */   }
/*     */   
/*     */   public List<String> getIgnoredPlugins() {
/* 113 */     return this.helpYaml.getStringList("ignore-plugins");
/*     */   }
/*     */   
/*     */   public boolean commandTopicsInMasterIndex() {
/* 117 */     return this.helpYaml.getBoolean("command-topics-in-master-index", true);
/*     */   }
/*     */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\org\bukkit\craftbukkit\v1_8_R3\help\HelpYamlReader.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */
/*    */ package org.bukkit.conversations;
/*    */ 
/*    */ import org.bukkit.ChatColor;
/*    */ import org.bukkit.plugin.Plugin;
/*    */ import org.bukkit.plugin.PluginDescriptionFile;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class PluginNameConversationPrefix
/*    */   implements ConversationPrefix
/*    */ {
/*    */   protected String separator;
/*    */   protected ChatColor prefixColor;
/*    */   protected Plugin plugin;
/*    */   private String cachedPrefix;
/*    */   
/*    */   public PluginNameConversationPrefix(Plugin plugin)
/*    */   {
/* 20 */     this(plugin, " > ", ChatColor.LIGHT_PURPLE);
/*    */   }
/*    */   
/*    */   public PluginNameConversationPrefix(Plugin plugin, String separator, ChatColor prefixColor) {
/* 24 */     this.separator = separator;
/* 25 */     this.prefixColor = prefixColor;
/* 26 */     this.plugin = plugin;
/*    */     
/* 28 */     this.cachedPrefix = (prefixColor + plugin.getDescription().getName() + separator + ChatColor.WHITE);
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   public String getPrefix(ConversationContext context)
/*    */   {
/* 38 */     return this.cachedPrefix;
/*    */   }
/*    */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\org\bukkit\conversations\PluginNameConversationPrefix.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */
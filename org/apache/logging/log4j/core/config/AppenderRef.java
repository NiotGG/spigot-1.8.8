/*    */ package org.apache.logging.log4j.core.config;
/*    */ 
/*    */ import org.apache.logging.log4j.Level;
/*    */ import org.apache.logging.log4j.Logger;
/*    */ import org.apache.logging.log4j.core.Filter;
/*    */ import org.apache.logging.log4j.core.config.plugins.Plugin;
/*    */ import org.apache.logging.log4j.core.config.plugins.PluginAliases;
/*    */ import org.apache.logging.log4j.core.config.plugins.PluginAttribute;
/*    */ import org.apache.logging.log4j.core.config.plugins.PluginElement;
/*    */ import org.apache.logging.log4j.core.config.plugins.PluginFactory;
/*    */ import org.apache.logging.log4j.status.StatusLogger;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ @Plugin(name="AppenderRef", category="Core", printObject=true)
/*    */ @PluginAliases({"appender-ref"})
/*    */ public final class AppenderRef
/*    */ {
/* 35 */   private static final Logger LOGGER = ;
/*    */   private final String ref;
/*    */   private final Level level;
/*    */   private final Filter filter;
/*    */   
/*    */   private AppenderRef(String paramString, Level paramLevel, Filter paramFilter)
/*    */   {
/* 42 */     this.ref = paramString;
/* 43 */     this.level = paramLevel;
/* 44 */     this.filter = paramFilter;
/*    */   }
/*    */   
/*    */   public String getRef() {
/* 48 */     return this.ref;
/*    */   }
/*    */   
/*    */   public Level getLevel() {
/* 52 */     return this.level;
/*    */   }
/*    */   
/*    */   public Filter getFilter() {
/* 56 */     return this.filter;
/*    */   }
/*    */   
/*    */   public String toString()
/*    */   {
/* 61 */     return this.ref;
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   @PluginFactory
/*    */   public static AppenderRef createAppenderRef(@PluginAttribute("ref") String paramString1, @PluginAttribute("level") String paramString2, @PluginElement("Filters") Filter paramFilter)
/*    */   {
/* 77 */     if (paramString1 == null) {
/* 78 */       LOGGER.error("Appender references must contain a reference");
/* 79 */       return null;
/*    */     }
/* 81 */     Level localLevel = null;
/*    */     
/* 83 */     if (paramString2 != null) {
/* 84 */       localLevel = Level.toLevel(paramString2, null);
/* 85 */       if (localLevel == null) {
/* 86 */         LOGGER.error("Invalid level " + paramString2 + " on Appender reference " + paramString1);
/*    */       }
/*    */     }
/*    */     
/* 90 */     return new AppenderRef(paramString1, localLevel, paramFilter);
/*    */   }
/*    */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\org\apache\logging\log4j\core\config\AppenderRef.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */
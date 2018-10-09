/*     */ package org.apache.logging.log4j.core.async;
/*     */ 
/*     */ import java.util.Arrays;
/*     */ import java.util.List;
/*     */ import org.apache.logging.log4j.Level;
/*     */ import org.apache.logging.log4j.Logger;
/*     */ import org.apache.logging.log4j.core.Filter;
/*     */ import org.apache.logging.log4j.core.LogEvent;
/*     */ import org.apache.logging.log4j.core.config.AppenderRef;
/*     */ import org.apache.logging.log4j.core.config.Configuration;
/*     */ import org.apache.logging.log4j.core.config.LoggerConfig;
/*     */ import org.apache.logging.log4j.core.config.Property;
/*     */ import org.apache.logging.log4j.core.config.plugins.Plugin;
/*     */ import org.apache.logging.log4j.core.config.plugins.PluginAttribute;
/*     */ import org.apache.logging.log4j.core.config.plugins.PluginConfiguration;
/*     */ import org.apache.logging.log4j.core.config.plugins.PluginElement;
/*     */ import org.apache.logging.log4j.core.config.plugins.PluginFactory;
/*     */ import org.apache.logging.log4j.core.helpers.Booleans;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ @Plugin(name="asyncLogger", category="Core", printObject=true)
/*     */ public class AsyncLoggerConfig
/*     */   extends LoggerConfig
/*     */ {
/*     */   private AsyncLoggerConfigHelper helper;
/*     */   
/*     */   public AsyncLoggerConfig() {}
/*     */   
/*     */   public AsyncLoggerConfig(String paramString, Level paramLevel, boolean paramBoolean)
/*     */   {
/*  88 */     super(paramString, paramLevel, paramBoolean);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   protected AsyncLoggerConfig(String paramString, List<AppenderRef> paramList, Filter paramFilter, Level paramLevel, boolean paramBoolean1, Property[] paramArrayOfProperty, Configuration paramConfiguration, boolean paramBoolean2)
/*     */   {
/*  96 */     super(paramString, paramList, paramFilter, paramLevel, paramBoolean1, paramArrayOfProperty, paramConfiguration, paramBoolean2);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   protected void callAppenders(LogEvent paramLogEvent)
/*     */   {
/* 107 */     paramLogEvent.getSource();
/* 108 */     paramLogEvent.getThreadName();
/*     */     
/*     */ 
/* 111 */     this.helper.callAppendersFromAnotherThread(paramLogEvent);
/*     */   }
/*     */   
/*     */   void asyncCallAppenders(LogEvent paramLogEvent)
/*     */   {
/* 116 */     super.callAppenders(paramLogEvent);
/*     */   }
/*     */   
/*     */   public void startFilter()
/*     */   {
/* 121 */     if (this.helper == null) {
/* 122 */       this.helper = new AsyncLoggerConfigHelper(this);
/*     */     } else {
/* 124 */       AsyncLoggerConfigHelper.claim();
/*     */     }
/* 126 */     super.startFilter();
/*     */   }
/*     */   
/*     */   public void stopFilter()
/*     */   {
/* 131 */     AsyncLoggerConfigHelper.release();
/* 132 */     super.stopFilter();
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   @PluginFactory
/*     */   public static LoggerConfig createLogger(@PluginAttribute("additivity") String paramString1, @PluginAttribute("level") String paramString2, @PluginAttribute("name") String paramString3, @PluginAttribute("includeLocation") String paramString4, @PluginElement("AppenderRef") AppenderRef[] paramArrayOfAppenderRef, @PluginElement("Properties") Property[] paramArrayOfProperty, @PluginConfiguration Configuration paramConfiguration, @PluginElement("Filters") Filter paramFilter)
/*     */   {
/* 158 */     if (paramString3 == null) {
/* 159 */       LOGGER.error("Loggers cannot be configured without a name");
/* 160 */       return null;
/*     */     }
/*     */     
/* 163 */     List localList = Arrays.asList(paramArrayOfAppenderRef);
/*     */     Level localLevel;
/*     */     try {
/* 166 */       localLevel = Level.toLevel(paramString2, Level.ERROR);
/*     */     } catch (Exception localException) {
/* 168 */       LOGGER.error("Invalid Log level specified: {}. Defaulting to Error", new Object[] { paramString2 });
/*     */       
/*     */ 
/* 171 */       localLevel = Level.ERROR;
/*     */     }
/* 173 */     String str = paramString3.equals("root") ? "" : paramString3;
/* 174 */     boolean bool = Booleans.parseBoolean(paramString1, true);
/*     */     
/* 176 */     return new AsyncLoggerConfig(str, localList, paramFilter, localLevel, bool, paramArrayOfProperty, paramConfiguration, includeLocation(paramString4));
/*     */   }
/*     */   
/*     */ 
/*     */   protected static boolean includeLocation(String paramString)
/*     */   {
/* 182 */     return Boolean.parseBoolean(paramString);
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
/*     */   @Plugin(name="asyncRoot", category="Core", printObject=true)
/*     */   public static class RootLogger
/*     */     extends LoggerConfig
/*     */   {
/*     */     @PluginFactory
/*     */     public static LoggerConfig createLogger(@PluginAttribute("additivity") String paramString1, @PluginAttribute("level") String paramString2, @PluginAttribute("includeLocation") String paramString3, @PluginElement("AppenderRef") AppenderRef[] paramArrayOfAppenderRef, @PluginElement("Properties") Property[] paramArrayOfProperty, @PluginConfiguration Configuration paramConfiguration, @PluginElement("Filters") Filter paramFilter)
/*     */     {
/* 200 */       List localList = Arrays.asList(paramArrayOfAppenderRef);
/*     */       Level localLevel;
/*     */       try {
/* 203 */         localLevel = Level.toLevel(paramString2, Level.ERROR);
/*     */       } catch (Exception localException) {
/* 205 */         LOGGER.error("Invalid Log level specified: {}. Defaulting to Error", new Object[] { paramString2 });
/*     */         
/*     */ 
/* 208 */         localLevel = Level.ERROR;
/*     */       }
/* 210 */       boolean bool = Booleans.parseBoolean(paramString1, true);
/*     */       
/* 212 */       return new AsyncLoggerConfig("", localList, paramFilter, localLevel, bool, paramArrayOfProperty, paramConfiguration, includeLocation(paramString3));
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\org\apache\logging\log4j\core\async\AsyncLoggerConfig.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */
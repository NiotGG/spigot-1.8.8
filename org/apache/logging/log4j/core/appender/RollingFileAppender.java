/*     */ package org.apache.logging.log4j.core.appender;
/*     */ 
/*     */ import java.io.Serializable;
/*     */ import java.util.HashMap;
/*     */ import java.util.Map;
/*     */ import org.apache.logging.log4j.Logger;
/*     */ import org.apache.logging.log4j.core.Filter;
/*     */ import org.apache.logging.log4j.core.Layout;
/*     */ import org.apache.logging.log4j.core.LogEvent;
/*     */ import org.apache.logging.log4j.core.appender.rolling.DefaultRolloverStrategy;
/*     */ import org.apache.logging.log4j.core.appender.rolling.RollingFileManager;
/*     */ import org.apache.logging.log4j.core.appender.rolling.RolloverStrategy;
/*     */ import org.apache.logging.log4j.core.appender.rolling.TriggeringPolicy;
/*     */ import org.apache.logging.log4j.core.config.Configuration;
/*     */ import org.apache.logging.log4j.core.config.plugins.Plugin;
/*     */ import org.apache.logging.log4j.core.config.plugins.PluginAttribute;
/*     */ import org.apache.logging.log4j.core.config.plugins.PluginConfiguration;
/*     */ import org.apache.logging.log4j.core.config.plugins.PluginElement;
/*     */ import org.apache.logging.log4j.core.config.plugins.PluginFactory;
/*     */ import org.apache.logging.log4j.core.helpers.Booleans;
/*     */ import org.apache.logging.log4j.core.layout.PatternLayout;
/*     */ import org.apache.logging.log4j.core.net.Advertiser;
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
/*     */ @Plugin(name="RollingFile", category="Core", elementType="appender", printObject=true)
/*     */ public final class RollingFileAppender
/*     */   extends AbstractOutputStreamAppender
/*     */ {
/*     */   private final String fileName;
/*     */   private final String filePattern;
/*     */   private Object advertisement;
/*     */   private final Advertiser advertiser;
/*     */   
/*     */   private RollingFileAppender(String paramString1, Layout<? extends Serializable> paramLayout, Filter paramFilter, RollingFileManager paramRollingFileManager, String paramString2, String paramString3, boolean paramBoolean1, boolean paramBoolean2, Advertiser paramAdvertiser)
/*     */   {
/*  57 */     super(paramString1, paramLayout, paramFilter, paramBoolean1, paramBoolean2, paramRollingFileManager);
/*  58 */     if (paramAdvertiser != null) {
/*  59 */       HashMap localHashMap = new HashMap(paramLayout.getContentFormat());
/*  60 */       localHashMap.put("contentType", paramLayout.getContentType());
/*  61 */       localHashMap.put("name", paramString1);
/*  62 */       this.advertisement = paramAdvertiser.advertise(localHashMap);
/*     */     }
/*  64 */     this.fileName = paramString2;
/*  65 */     this.filePattern = paramString3;
/*  66 */     this.advertiser = paramAdvertiser;
/*     */   }
/*     */   
/*     */   public void stop()
/*     */   {
/*  71 */     super.stop();
/*  72 */     if (this.advertiser != null) {
/*  73 */       this.advertiser.unadvertise(this.advertisement);
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public void append(LogEvent paramLogEvent)
/*     */   {
/*  84 */     ((RollingFileManager)getManager()).checkRollover(paramLogEvent);
/*  85 */     super.append(paramLogEvent);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public String getFileName()
/*     */   {
/*  93 */     return this.fileName;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public String getFilePattern()
/*     */   {
/* 101 */     return this.filePattern;
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
/*     */   public static RollingFileAppender createAppender(@PluginAttribute("fileName") String paramString1, @PluginAttribute("filePattern") String paramString2, @PluginAttribute("append") String paramString3, @PluginAttribute("name") String paramString4, @PluginAttribute("bufferedIO") String paramString5, @PluginAttribute("immediateFlush") String paramString6, @PluginElement("Policy") TriggeringPolicy paramTriggeringPolicy, @PluginElement("Strategy") RolloverStrategy paramRolloverStrategy, @PluginElement("Layout") Layout<? extends Serializable> paramLayout, @PluginElement("Filter") Filter paramFilter, @PluginAttribute("ignoreExceptions") String paramString7, @PluginAttribute("advertise") String paramString8, @PluginAttribute("advertiseURI") String paramString9, @PluginConfiguration Configuration paramConfiguration)
/*     */   {
/* 141 */     boolean bool1 = Booleans.parseBoolean(paramString3, true);
/* 142 */     boolean bool2 = Booleans.parseBoolean(paramString7, true);
/* 143 */     boolean bool3 = Booleans.parseBoolean(paramString5, true);
/* 144 */     boolean bool4 = Booleans.parseBoolean(paramString6, true);
/* 145 */     boolean bool5 = Boolean.parseBoolean(paramString8);
/* 146 */     if (paramString4 == null) {
/* 147 */       LOGGER.error("No name provided for FileAppender");
/* 148 */       return null;
/*     */     }
/*     */     
/* 151 */     if (paramString1 == null) {
/* 152 */       LOGGER.error("No filename was provided for FileAppender with name " + paramString4);
/* 153 */       return null;
/*     */     }
/*     */     
/* 156 */     if (paramString2 == null) {
/* 157 */       LOGGER.error("No filename pattern provided for FileAppender with name " + paramString4);
/* 158 */       return null;
/*     */     }
/*     */     
/* 161 */     if (paramTriggeringPolicy == null) {
/* 162 */       LOGGER.error("A TriggeringPolicy must be provided");
/* 163 */       return null;
/*     */     }
/*     */     
/* 166 */     if (paramRolloverStrategy == null) {
/* 167 */       paramRolloverStrategy = DefaultRolloverStrategy.createStrategy(null, null, null, String.valueOf(-1), paramConfiguration);
/*     */     }
/*     */     
/*     */ 
/* 171 */     if (paramLayout == null) {
/* 172 */       paramLayout = PatternLayout.createLayout(null, null, null, null, null);
/*     */     }
/*     */     
/* 175 */     RollingFileManager localRollingFileManager = RollingFileManager.getFileManager(paramString1, paramString2, bool1, bool3, paramTriggeringPolicy, paramRolloverStrategy, paramString9, paramLayout);
/*     */     
/* 177 */     if (localRollingFileManager == null) {
/* 178 */       return null;
/*     */     }
/*     */     
/* 181 */     return new RollingFileAppender(paramString4, paramLayout, paramFilter, localRollingFileManager, paramString1, paramString2, bool2, bool4, bool5 ? paramConfiguration.getAdvertiser() : null);
/*     */   }
/*     */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\org\apache\logging\log4j\core\appender\RollingFileAppender.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */
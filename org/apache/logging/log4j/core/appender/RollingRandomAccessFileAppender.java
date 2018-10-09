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
/*     */ import org.apache.logging.log4j.core.appender.rolling.RollingRandomAccessFileManager;
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
/*     */ @Plugin(name="RollingRandomAccessFile", category="Core", elementType="appender", printObject=true)
/*     */ public final class RollingRandomAccessFileAppender
/*     */   extends AbstractOutputStreamAppender
/*     */ {
/*     */   private final String fileName;
/*     */   private final String filePattern;
/*     */   private Object advertisement;
/*     */   private final Advertiser advertiser;
/*     */   
/*     */   private RollingRandomAccessFileAppender(String paramString1, Layout<? extends Serializable> paramLayout, Filter paramFilter, RollingFileManager paramRollingFileManager, String paramString2, String paramString3, boolean paramBoolean1, boolean paramBoolean2, Advertiser paramAdvertiser)
/*     */   {
/*  58 */     super(paramString1, paramLayout, paramFilter, paramBoolean1, paramBoolean2, paramRollingFileManager);
/*  59 */     if (paramAdvertiser != null) {
/*  60 */       HashMap localHashMap = new HashMap(paramLayout.getContentFormat());
/*     */       
/*  62 */       localHashMap.put("contentType", paramLayout.getContentType());
/*  63 */       localHashMap.put("name", paramString1);
/*  64 */       this.advertisement = paramAdvertiser.advertise(localHashMap);
/*     */     }
/*  66 */     this.fileName = paramString2;
/*  67 */     this.filePattern = paramString3;
/*  68 */     this.advertiser = paramAdvertiser;
/*     */   }
/*     */   
/*     */   public void stop()
/*     */   {
/*  73 */     super.stop();
/*  74 */     if (this.advertiser != null) {
/*  75 */       this.advertiser.unadvertise(this.advertisement);
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
/*  86 */     RollingRandomAccessFileManager localRollingRandomAccessFileManager = (RollingRandomAccessFileManager)getManager();
/*  87 */     localRollingRandomAccessFileManager.checkRollover(paramLogEvent);
/*     */     
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*  95 */     localRollingRandomAccessFileManager.setEndOfBatch(paramLogEvent.isEndOfBatch());
/*  96 */     super.append(paramLogEvent);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public String getFileName()
/*     */   {
/* 105 */     return this.fileName;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public String getFilePattern()
/*     */   {
/* 114 */     return this.filePattern;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   @PluginFactory
/*     */   public static RollingRandomAccessFileAppender createAppender(@PluginAttribute("fileName") String paramString1, @PluginAttribute("filePattern") String paramString2, @PluginAttribute("append") String paramString3, @PluginAttribute("name") String paramString4, @PluginAttribute("immediateFlush") String paramString5, @PluginElement("Policy") TriggeringPolicy paramTriggeringPolicy, @PluginElement("Strategy") RolloverStrategy paramRolloverStrategy, @PluginElement("Layout") Layout<? extends Serializable> paramLayout, @PluginElement("Filter") Filter paramFilter, @PluginAttribute("ignoreExceptions") String paramString6, @PluginAttribute("advertise") String paramString7, @PluginAttribute("advertiseURI") String paramString8, @PluginConfiguration Configuration paramConfiguration)
/*     */   {
/* 159 */     boolean bool1 = Booleans.parseBoolean(paramString3, true);
/* 160 */     boolean bool2 = Booleans.parseBoolean(paramString6, true);
/* 161 */     boolean bool3 = Booleans.parseBoolean(paramString5, true);
/* 162 */     boolean bool4 = Boolean.parseBoolean(paramString7);
/*     */     
/* 164 */     if (paramString4 == null) {
/* 165 */       LOGGER.error("No name provided for FileAppender");
/* 166 */       return null;
/*     */     }
/*     */     
/* 169 */     if (paramString1 == null) {
/* 170 */       LOGGER.error("No filename was provided for FileAppender with name " + paramString4);
/*     */       
/* 172 */       return null;
/*     */     }
/*     */     
/* 175 */     if (paramString2 == null) {
/* 176 */       LOGGER.error("No filename pattern provided for FileAppender with name " + paramString4);
/*     */       
/* 178 */       return null;
/*     */     }
/*     */     
/* 181 */     if (paramTriggeringPolicy == null) {
/* 182 */       LOGGER.error("A TriggeringPolicy must be provided");
/* 183 */       return null;
/*     */     }
/*     */     
/* 186 */     if (paramRolloverStrategy == null) {
/* 187 */       paramRolloverStrategy = DefaultRolloverStrategy.createStrategy(null, null, null, String.valueOf(-1), paramConfiguration);
/*     */     }
/*     */     
/*     */ 
/* 191 */     if (paramLayout == null) {
/* 192 */       paramLayout = PatternLayout.createLayout(null, null, null, null, null);
/*     */     }
/*     */     
/*     */ 
/* 196 */     RollingRandomAccessFileManager localRollingRandomAccessFileManager = RollingRandomAccessFileManager.getRollingRandomAccessFileManager(paramString1, paramString2, bool1, bool3, paramTriggeringPolicy, paramRolloverStrategy, paramString8, paramLayout);
/*     */     
/* 198 */     if (localRollingRandomAccessFileManager == null) {
/* 199 */       return null;
/*     */     }
/*     */     
/* 202 */     return new RollingRandomAccessFileAppender(paramString4, paramLayout, paramFilter, localRollingRandomAccessFileManager, paramString1, paramString2, bool2, bool3, bool4 ? paramConfiguration.getAdvertiser() : null);
/*     */   }
/*     */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\org\apache\logging\log4j\core\appender\RollingRandomAccessFileAppender.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */
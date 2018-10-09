/*     */ package org.apache.logging.log4j.core.appender;
/*     */ 
/*     */ import java.io.Serializable;
/*     */ import java.util.HashMap;
/*     */ import java.util.Map;
/*     */ import org.apache.logging.log4j.Logger;
/*     */ import org.apache.logging.log4j.core.Filter;
/*     */ import org.apache.logging.log4j.core.Layout;
/*     */ import org.apache.logging.log4j.core.LogEvent;
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
/*     */ @Plugin(name="RandomAccessFile", category="Core", elementType="appender", printObject=true)
/*     */ public final class RandomAccessFileAppender
/*     */   extends AbstractOutputStreamAppender
/*     */ {
/*     */   private final String fileName;
/*     */   private Object advertisement;
/*     */   private final Advertiser advertiser;
/*     */   
/*     */   private RandomAccessFileAppender(String paramString1, Layout<? extends Serializable> paramLayout, Filter paramFilter, RandomAccessFileManager paramRandomAccessFileManager, String paramString2, boolean paramBoolean1, boolean paramBoolean2, Advertiser paramAdvertiser)
/*     */   {
/*  49 */     super(paramString1, paramLayout, paramFilter, paramBoolean1, paramBoolean2, paramRandomAccessFileManager);
/*  50 */     if (paramAdvertiser != null) {
/*  51 */       HashMap localHashMap = new HashMap(paramLayout.getContentFormat());
/*     */       
/*  53 */       localHashMap.putAll(paramRandomAccessFileManager.getContentFormat());
/*  54 */       localHashMap.put("contentType", paramLayout.getContentType());
/*  55 */       localHashMap.put("name", paramString1);
/*  56 */       this.advertisement = paramAdvertiser.advertise(localHashMap);
/*     */     }
/*  58 */     this.fileName = paramString2;
/*  59 */     this.advertiser = paramAdvertiser;
/*     */   }
/*     */   
/*     */   public void stop()
/*     */   {
/*  64 */     super.stop();
/*  65 */     if (this.advertiser != null) {
/*  66 */       this.advertiser.unadvertise(this.advertisement);
/*     */     }
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
/*     */   public void append(LogEvent paramLogEvent)
/*     */   {
/*  84 */     ((RandomAccessFileManager)getManager()).setEndOfBatch(paramLogEvent.isEndOfBatch());
/*  85 */     super.append(paramLogEvent);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public String getFileName()
/*     */   {
/*  94 */     return this.fileName;
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
/*     */   @PluginFactory
/*     */   public static RandomAccessFileAppender createAppender(@PluginAttribute("fileName") String paramString1, @PluginAttribute("append") String paramString2, @PluginAttribute("name") String paramString3, @PluginAttribute("immediateFlush") String paramString4, @PluginAttribute("ignoreExceptions") String paramString5, @PluginElement("Layout") Layout<? extends Serializable> paramLayout, @PluginElement("Filters") Filter paramFilter, @PluginAttribute("advertise") String paramString6, @PluginAttribute("advertiseURI") String paramString7, @PluginConfiguration Configuration paramConfiguration)
/*     */   {
/* 133 */     boolean bool1 = Booleans.parseBoolean(paramString2, true);
/* 134 */     boolean bool2 = Booleans.parseBoolean(paramString4, true);
/* 135 */     boolean bool3 = Booleans.parseBoolean(paramString5, true);
/* 136 */     boolean bool4 = Boolean.parseBoolean(paramString6);
/*     */     
/* 138 */     if (paramString3 == null) {
/* 139 */       LOGGER.error("No name provided for FileAppender");
/* 140 */       return null;
/*     */     }
/*     */     
/* 143 */     if (paramString1 == null) {
/* 144 */       LOGGER.error("No filename provided for FileAppender with name " + paramString3);
/*     */       
/* 146 */       return null;
/*     */     }
/* 148 */     if (paramLayout == null) {
/* 149 */       paramLayout = PatternLayout.createLayout(null, null, null, null, null);
/*     */     }
/* 151 */     RandomAccessFileManager localRandomAccessFileManager = RandomAccessFileManager.getFileManager(paramString1, bool1, bool2, paramString7, paramLayout);
/*     */     
/*     */ 
/* 154 */     if (localRandomAccessFileManager == null) {
/* 155 */       return null;
/*     */     }
/*     */     
/* 158 */     return new RandomAccessFileAppender(paramString3, paramLayout, paramFilter, localRandomAccessFileManager, paramString1, bool3, bool2, bool4 ? paramConfiguration.getAdvertiser() : null);
/*     */   }
/*     */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\org\apache\logging\log4j\core\appender\RandomAccessFileAppender.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */
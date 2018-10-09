/*     */ package org.apache.logging.log4j.core.appender;
/*     */ 
/*     */ import java.io.Serializable;
/*     */ import java.util.HashMap;
/*     */ import java.util.Map;
/*     */ import org.apache.logging.log4j.Logger;
/*     */ import org.apache.logging.log4j.core.Filter;
/*     */ import org.apache.logging.log4j.core.Layout;
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
/*     */ @Plugin(name="File", category="Core", elementType="appender", printObject=true)
/*     */ public final class FileAppender
/*     */   extends AbstractOutputStreamAppender
/*     */ {
/*     */   private final String fileName;
/*     */   private final Advertiser advertiser;
/*     */   private Object advertisement;
/*     */   
/*     */   private FileAppender(String paramString1, Layout<? extends Serializable> paramLayout, Filter paramFilter, FileManager paramFileManager, String paramString2, boolean paramBoolean1, boolean paramBoolean2, Advertiser paramAdvertiser)
/*     */   {
/*  48 */     super(paramString1, paramLayout, paramFilter, paramBoolean1, paramBoolean2, paramFileManager);
/*  49 */     if (paramAdvertiser != null) {
/*  50 */       HashMap localHashMap = new HashMap(paramLayout.getContentFormat());
/*  51 */       localHashMap.putAll(paramFileManager.getContentFormat());
/*  52 */       localHashMap.put("contentType", paramLayout.getContentType());
/*  53 */       localHashMap.put("name", paramString1);
/*  54 */       this.advertisement = paramAdvertiser.advertise(localHashMap);
/*     */     }
/*  56 */     this.fileName = paramString2;
/*  57 */     this.advertiser = paramAdvertiser;
/*     */   }
/*     */   
/*     */   public void stop()
/*     */   {
/*  62 */     super.stop();
/*  63 */     if (this.advertiser != null) {
/*  64 */       this.advertiser.unadvertise(this.advertisement);
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public String getFileName()
/*     */   {
/*  73 */     return this.fileName;
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
/*     */   @PluginFactory
/*     */   public static FileAppender createAppender(@PluginAttribute("fileName") String paramString1, @PluginAttribute("append") String paramString2, @PluginAttribute("locking") String paramString3, @PluginAttribute("name") String paramString4, @PluginAttribute("immediateFlush") String paramString5, @PluginAttribute("ignoreExceptions") String paramString6, @PluginAttribute("bufferedIO") String paramString7, @PluginElement("Layout") Layout<? extends Serializable> paramLayout, @PluginElement("Filters") Filter paramFilter, @PluginAttribute("advertise") String paramString8, @PluginAttribute("advertiseURI") String paramString9, @PluginConfiguration Configuration paramConfiguration)
/*     */   {
/* 111 */     boolean bool1 = Booleans.parseBoolean(paramString2, true);
/* 112 */     boolean bool2 = Boolean.parseBoolean(paramString3);
/* 113 */     boolean bool3 = Booleans.parseBoolean(paramString7, true);
/* 114 */     boolean bool4 = Boolean.parseBoolean(paramString8);
/* 115 */     if ((bool2) && (bool3)) {
/* 116 */       if (paramString7 != null) {
/* 117 */         LOGGER.warn("Locking and buffering are mutually exclusive. No buffering will occur for " + paramString1);
/*     */       }
/* 119 */       bool3 = false;
/*     */     }
/* 121 */     boolean bool5 = Booleans.parseBoolean(paramString5, true);
/* 122 */     boolean bool6 = Booleans.parseBoolean(paramString6, true);
/*     */     
/* 124 */     if (paramString4 == null) {
/* 125 */       LOGGER.error("No name provided for FileAppender");
/* 126 */       return null;
/*     */     }
/*     */     
/* 129 */     if (paramString1 == null) {
/* 130 */       LOGGER.error("No filename provided for FileAppender with name " + paramString4);
/* 131 */       return null;
/*     */     }
/* 133 */     if (paramLayout == null) {
/* 134 */       paramLayout = PatternLayout.createLayout(null, null, null, null, null);
/*     */     }
/*     */     
/* 137 */     FileManager localFileManager = FileManager.getFileManager(paramString1, bool1, bool2, bool3, paramString9, paramLayout);
/*     */     
/* 139 */     if (localFileManager == null) {
/* 140 */       return null;
/*     */     }
/*     */     
/*     */ 
/* 144 */     return new FileAppender(paramString4, paramLayout, paramFilter, localFileManager, paramString1, bool6, bool5, bool4 ? paramConfiguration.getAdvertiser() : null);
/*     */   }
/*     */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\org\apache\logging\log4j\core\appender\FileAppender.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */
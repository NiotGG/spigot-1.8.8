/*     */ package org.apache.logging.log4j.core.appender;
/*     */ 
/*     */ import java.io.Serializable;
/*     */ import org.apache.logging.log4j.Logger;
/*     */ import org.apache.logging.log4j.core.Filter;
/*     */ import org.apache.logging.log4j.core.Layout;
/*     */ import org.apache.logging.log4j.core.LogEvent;
/*     */ import org.apache.logging.log4j.core.config.plugins.Plugin;
/*     */ import org.apache.logging.log4j.core.config.plugins.PluginAttribute;
/*     */ import org.apache.logging.log4j.core.config.plugins.PluginElement;
/*     */ import org.apache.logging.log4j.core.config.plugins.PluginFactory;
/*     */ import org.apache.logging.log4j.core.filter.ThresholdFilter;
/*     */ import org.apache.logging.log4j.core.helpers.Booleans;
/*     */ import org.apache.logging.log4j.core.layout.HTMLLayout;
/*     */ import org.apache.logging.log4j.core.net.SMTPManager;
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
/*     */ @Plugin(name="SMTP", category="Core", elementType="appender", printObject=true)
/*     */ public final class SMTPAppender
/*     */   extends AbstractAppender
/*     */ {
/*     */   private static final int DEFAULT_BUFFER_SIZE = 512;
/*     */   protected final SMTPManager manager;
/*     */   
/*     */   private SMTPAppender(String paramString, Filter paramFilter, Layout<? extends Serializable> paramLayout, SMTPManager paramSMTPManager, boolean paramBoolean)
/*     */   {
/*  62 */     super(paramString, paramFilter, paramLayout, paramBoolean);
/*  63 */     this.manager = paramSMTPManager;
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
/*     */   public static SMTPAppender createAppender(@PluginAttribute("name") String paramString1, @PluginAttribute("to") String paramString2, @PluginAttribute("cc") String paramString3, @PluginAttribute("bcc") String paramString4, @PluginAttribute("from") String paramString5, @PluginAttribute("replyTo") String paramString6, @PluginAttribute("subject") String paramString7, @PluginAttribute("smtpProtocol") String paramString8, @PluginAttribute("smtpHost") String paramString9, @PluginAttribute("smtpPort") String paramString10, @PluginAttribute("smtpUsername") String paramString11, @PluginAttribute("smtpPassword") String paramString12, @PluginAttribute("smtpDebug") String paramString13, @PluginAttribute("bufferSize") String paramString14, @PluginElement("Layout") Layout<? extends Serializable> paramLayout, @PluginElement("Filter") Filter paramFilter, @PluginAttribute("ignoreExceptions") String paramString15)
/*     */   {
/* 124 */     if (paramString1 == null) {
/* 125 */       LOGGER.error("No name provided for SMTPAppender");
/* 126 */       return null;
/*     */     }
/*     */     
/* 129 */     boolean bool1 = Booleans.parseBoolean(paramString15, true);
/* 130 */     int i = AbstractAppender.parseInt(paramString10, 0);
/* 131 */     boolean bool2 = Boolean.parseBoolean(paramString13);
/* 132 */     int j = paramString14 == null ? 512 : Integer.parseInt(paramString14);
/*     */     
/* 134 */     if (paramLayout == null) {
/* 135 */       paramLayout = HTMLLayout.createLayout(null, null, null, null, null, null);
/*     */     }
/* 137 */     if (paramFilter == null) {
/* 138 */       paramFilter = ThresholdFilter.createFilter(null, null, null);
/*     */     }
/*     */     
/* 141 */     SMTPManager localSMTPManager = SMTPManager.getSMTPManager(paramString2, paramString3, paramString4, paramString5, paramString6, paramString7, paramString8, paramString9, i, paramString11, paramString12, bool2, paramFilter.toString(), j);
/*     */     
/* 143 */     if (localSMTPManager == null) {
/* 144 */       return null;
/*     */     }
/*     */     
/* 147 */     return new SMTPAppender(paramString1, paramFilter, paramLayout, localSMTPManager, bool1);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public boolean isFiltered(LogEvent paramLogEvent)
/*     */   {
/* 157 */     boolean bool = super.isFiltered(paramLogEvent);
/* 158 */     if (bool) {
/* 159 */       this.manager.add(paramLogEvent);
/*     */     }
/* 161 */     return bool;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public void append(LogEvent paramLogEvent)
/*     */   {
/* 172 */     this.manager.sendEvents(getLayout(), paramLogEvent);
/*     */   }
/*     */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\org\apache\logging\log4j\core\appender\SMTPAppender.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */
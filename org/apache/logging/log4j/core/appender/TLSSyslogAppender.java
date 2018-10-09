/*     */ package org.apache.logging.log4j.core.appender;
/*     */ 
/*     */ import java.io.Serializable;
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
/*     */ import org.apache.logging.log4j.core.layout.LoggerFields;
/*     */ import org.apache.logging.log4j.core.layout.RFC5424Layout;
/*     */ import org.apache.logging.log4j.core.layout.SyslogLayout;
/*     */ import org.apache.logging.log4j.core.net.AbstractSocketManager;
/*     */ import org.apache.logging.log4j.core.net.Advertiser;
/*     */ import org.apache.logging.log4j.core.net.TLSSocketManager;
/*     */ import org.apache.logging.log4j.core.net.ssl.SSLConfiguration;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ @Plugin(name="TLSSyslog", category="Core", elementType="appender", printObject=true)
/*     */ public final class TLSSyslogAppender
/*     */   extends SyslogAppender
/*     */ {
/*     */   protected TLSSyslogAppender(String paramString, Layout<? extends Serializable> paramLayout, Filter paramFilter, boolean paramBoolean1, boolean paramBoolean2, AbstractSocketManager paramAbstractSocketManager, Advertiser paramAdvertiser)
/*     */   {
/*  45 */     super(paramString, paramLayout, paramFilter, paramBoolean1, paramBoolean2, paramAbstractSocketManager, paramAdvertiser);
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   @PluginFactory
/*     */   public static TLSSyslogAppender createAppender(@PluginAttribute("host") String paramString1, @PluginAttribute("port") String paramString2, @PluginElement("ssl") SSLConfiguration paramSSLConfiguration, @PluginAttribute("reconnectionDelay") String paramString3, @PluginAttribute("immediateFail") String paramString4, @PluginAttribute("name") String paramString5, @PluginAttribute("immediateFlush") String paramString6, @PluginAttribute("ignoreExceptions") String paramString7, @PluginAttribute("facility") String paramString8, @PluginAttribute("id") String paramString9, @PluginAttribute("enterpriseNumber") String paramString10, @PluginAttribute("includeMDC") String paramString11, @PluginAttribute("mdcId") String paramString12, @PluginAttribute("mdcPrefix") String paramString13, @PluginAttribute("eventPrefix") String paramString14, @PluginAttribute("newLine") String paramString15, @PluginAttribute("newLineEscape") String paramString16, @PluginAttribute("appName") String paramString17, @PluginAttribute("messageId") String paramString18, @PluginAttribute("mdcExcludes") String paramString19, @PluginAttribute("mdcIncludes") String paramString20, @PluginAttribute("mdcRequired") String paramString21, @PluginAttribute("format") String paramString22, @PluginElement("filters") Filter paramFilter, @PluginConfiguration Configuration paramConfiguration, @PluginAttribute("charset") String paramString23, @PluginAttribute("exceptionPattern") String paramString24, @PluginElement("LoggerFields") LoggerFields[] paramArrayOfLoggerFields, @PluginAttribute("advertise") String paramString25)
/*     */   {
/* 114 */     boolean bool1 = Booleans.parseBoolean(paramString6, true);
/* 115 */     boolean bool2 = Booleans.parseBoolean(paramString7, true);
/* 116 */     int i = AbstractAppender.parseInt(paramString3, 0);
/* 117 */     boolean bool3 = Booleans.parseBoolean(paramString4, true);
/* 118 */     int j = AbstractAppender.parseInt(paramString2, 0);
/* 119 */     boolean bool4 = Boolean.parseBoolean(paramString25);
/*     */     
/* 121 */     SyslogLayout localSyslogLayout = "RFC5424".equalsIgnoreCase(paramString22) ? RFC5424Layout.createLayout(paramString8, paramString9, paramString10, paramString11, paramString12, paramString13, paramString14, paramString15, paramString16, paramString17, paramString18, paramString19, paramString20, paramString21, paramString24, "true", paramArrayOfLoggerFields, paramConfiguration) : SyslogLayout.createLayout(paramString8, paramString15, paramString16, paramString23);
/*     */     
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/* 127 */     if (paramString5 == null) {
/* 128 */       LOGGER.error("No name provided for TLSSyslogAppender");
/* 129 */       return null;
/*     */     }
/* 131 */     AbstractSocketManager localAbstractSocketManager = createSocketManager(paramSSLConfiguration, paramString1, j, i, bool3, localSyslogLayout);
/* 132 */     if (localAbstractSocketManager == null) {
/* 133 */       return null;
/*     */     }
/*     */     
/* 136 */     return new TLSSyslogAppender(paramString5, localSyslogLayout, paramFilter, bool2, bool1, localAbstractSocketManager, bool4 ? paramConfiguration.getAdvertiser() : null);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public static AbstractSocketManager createSocketManager(SSLConfiguration paramSSLConfiguration, String paramString, int paramInt1, int paramInt2, boolean paramBoolean, Layout<? extends Serializable> paramLayout)
/*     */   {
/* 143 */     return TLSSocketManager.getSocketManager(paramSSLConfiguration, paramString, paramInt1, paramInt2, paramBoolean, paramLayout);
/*     */   }
/*     */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\org\apache\logging\log4j\core\appender\TLSSyslogAppender.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */
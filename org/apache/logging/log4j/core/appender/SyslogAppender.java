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
/*     */ import org.apache.logging.log4j.core.net.Protocol;
/*     */ import org.apache.logging.log4j.util.EnglishEnums;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ @Plugin(name="Syslog", category="Core", elementType="appender", printObject=true)
/*     */ public class SyslogAppender
/*     */   extends SocketAppender
/*     */ {
/*     */   protected static final String RFC5424 = "RFC5424";
/*     */   
/*     */   protected SyslogAppender(String paramString, Layout<? extends Serializable> paramLayout, Filter paramFilter, boolean paramBoolean1, boolean paramBoolean2, AbstractSocketManager paramAbstractSocketManager, Advertiser paramAdvertiser)
/*     */   {
/*  49 */     super(paramString, paramLayout, paramFilter, paramAbstractSocketManager, paramBoolean1, paramBoolean2, paramAdvertiser);
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
/*     */ 
/*     */ 
/*     */ 
/*     */   @PluginFactory
/*     */   public static SyslogAppender createAppender(@PluginAttribute("host") String paramString1, @PluginAttribute("port") String paramString2, @PluginAttribute("protocol") String paramString3, @PluginAttribute("reconnectionDelay") String paramString4, @PluginAttribute("immediateFail") String paramString5, @PluginAttribute("name") String paramString6, @PluginAttribute("immediateFlush") String paramString7, @PluginAttribute("ignoreExceptions") String paramString8, @PluginAttribute("facility") String paramString9, @PluginAttribute("id") String paramString10, @PluginAttribute("enterpriseNumber") String paramString11, @PluginAttribute("includeMDC") String paramString12, @PluginAttribute("mdcId") String paramString13, @PluginAttribute("mdcPrefix") String paramString14, @PluginAttribute("eventPrefix") String paramString15, @PluginAttribute("newLine") String paramString16, @PluginAttribute("newLineEscape") String paramString17, @PluginAttribute("appName") String paramString18, @PluginAttribute("messageId") String paramString19, @PluginAttribute("mdcExcludes") String paramString20, @PluginAttribute("mdcIncludes") String paramString21, @PluginAttribute("mdcRequired") String paramString22, @PluginAttribute("format") String paramString23, @PluginElement("Filters") Filter paramFilter, @PluginConfiguration Configuration paramConfiguration, @PluginAttribute("charset") String paramString24, @PluginAttribute("exceptionPattern") String paramString25, @PluginElement("LoggerFields") LoggerFields[] paramArrayOfLoggerFields, @PluginAttribute("advertise") String paramString26)
/*     */   {
/* 121 */     boolean bool1 = Booleans.parseBoolean(paramString7, true);
/* 122 */     boolean bool2 = Booleans.parseBoolean(paramString8, true);
/* 123 */     int i = AbstractAppender.parseInt(paramString4, 0);
/* 124 */     boolean bool3 = Booleans.parseBoolean(paramString5, true);
/* 125 */     int j = AbstractAppender.parseInt(paramString2, 0);
/* 126 */     boolean bool4 = Boolean.parseBoolean(paramString26);
/* 127 */     SyslogLayout localSyslogLayout = "RFC5424".equalsIgnoreCase(paramString23) ? RFC5424Layout.createLayout(paramString9, paramString10, paramString11, paramString12, paramString13, paramString14, paramString15, paramString16, paramString17, paramString18, paramString19, paramString20, paramString21, paramString22, paramString25, "false", paramArrayOfLoggerFields, paramConfiguration) : SyslogLayout.createLayout(paramString9, paramString16, paramString17, paramString24);
/*     */     
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/* 133 */     if (paramString6 == null) {
/* 134 */       LOGGER.error("No name provided for SyslogAppender");
/* 135 */       return null;
/*     */     }
/* 137 */     Protocol localProtocol = (Protocol)EnglishEnums.valueOf(Protocol.class, paramString3);
/* 138 */     AbstractSocketManager localAbstractSocketManager = createSocketManager(localProtocol, paramString1, j, i, bool3, localSyslogLayout);
/* 139 */     if (localAbstractSocketManager == null) {
/* 140 */       return null;
/*     */     }
/*     */     
/* 143 */     return new SyslogAppender(paramString6, localSyslogLayout, paramFilter, bool2, bool1, localAbstractSocketManager, bool4 ? paramConfiguration.getAdvertiser() : null);
/*     */   }
/*     */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\org\apache\logging\log4j\core\appender\SyslogAppender.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */
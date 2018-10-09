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
/*     */ import org.apache.logging.log4j.core.helpers.Booleans;
/*     */ import org.apache.logging.log4j.core.layout.SerializedLayout;
/*     */ import org.apache.logging.log4j.core.net.JMSTopicManager;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ @Plugin(name="JMSTopic", category="Core", elementType="appender", printObject=true)
/*     */ public final class JMSTopicAppender
/*     */   extends AbstractAppender
/*     */ {
/*     */   private final JMSTopicManager manager;
/*     */   
/*     */   private JMSTopicAppender(String paramString, Filter paramFilter, Layout<? extends Serializable> paramLayout, JMSTopicManager paramJMSTopicManager, boolean paramBoolean)
/*     */   {
/*  42 */     super(paramString, paramFilter, paramLayout, paramBoolean);
/*  43 */     this.manager = paramJMSTopicManager;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public void append(LogEvent paramLogEvent)
/*     */   {
/*     */     try
/*     */     {
/*  54 */       this.manager.send(getLayout().toSerializable(paramLogEvent));
/*     */     } catch (Exception localException) {
/*  56 */       throw new AppenderLoggingException(localException);
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
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
/*     */   public static JMSTopicAppender createAppender(@PluginAttribute("name") String paramString1, @PluginAttribute("factoryName") String paramString2, @PluginAttribute("providerURL") String paramString3, @PluginAttribute("urlPkgPrefixes") String paramString4, @PluginAttribute("securityPrincipalName") String paramString5, @PluginAttribute("securityCredentials") String paramString6, @PluginAttribute("factoryBindingName") String paramString7, @PluginAttribute("topicBindingName") String paramString8, @PluginAttribute("userName") String paramString9, @PluginAttribute("password") String paramString10, @PluginElement("Layout") Layout<? extends Serializable> paramLayout, @PluginElement("Filters") Filter paramFilter, @PluginAttribute("ignoreExceptions") String paramString11)
/*     */   {
/*  95 */     if (paramString1 == null) {
/*  96 */       LOGGER.error("No name provided for JMSQueueAppender");
/*  97 */       return null;
/*     */     }
/*  99 */     boolean bool = Booleans.parseBoolean(paramString11, true);
/* 100 */     JMSTopicManager localJMSTopicManager = JMSTopicManager.getJMSTopicManager(paramString2, paramString3, paramString4, paramString5, paramString6, paramString7, paramString8, paramString9, paramString10);
/*     */     
/* 102 */     if (localJMSTopicManager == null) {
/* 103 */       return null;
/*     */     }
/* 105 */     if (paramLayout == null) {
/* 106 */       paramLayout = SerializedLayout.createLayout();
/*     */     }
/* 108 */     return new JMSTopicAppender(paramString1, paramFilter, paramLayout, localJMSTopicManager, bool);
/*     */   }
/*     */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\org\apache\logging\log4j\core\appender\JMSTopicAppender.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */
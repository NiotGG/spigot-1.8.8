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
/*     */ import org.apache.logging.log4j.core.net.JMSQueueManager;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ @Plugin(name="JMSQueue", category="Core", elementType="appender", printObject=true)
/*     */ public final class JMSQueueAppender
/*     */   extends AbstractAppender
/*     */ {
/*     */   private final JMSQueueManager manager;
/*     */   
/*     */   private JMSQueueAppender(String paramString, Filter paramFilter, Layout<? extends Serializable> paramLayout, JMSQueueManager paramJMSQueueManager, boolean paramBoolean)
/*     */   {
/*  42 */     super(paramString, paramFilter, paramLayout, paramBoolean);
/*  43 */     this.manager = paramJMSQueueManager;
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
/*     */   @PluginFactory
/*     */   public static JMSQueueAppender createAppender(@PluginAttribute("name") String paramString1, @PluginAttribute("factoryName") String paramString2, @PluginAttribute("providerURL") String paramString3, @PluginAttribute("urlPkgPrefixes") String paramString4, @PluginAttribute("securityPrincipalName") String paramString5, @PluginAttribute("securityCredentials") String paramString6, @PluginAttribute("factoryBindingName") String paramString7, @PluginAttribute("queueBindingName") String paramString8, @PluginAttribute("userName") String paramString9, @PluginAttribute("password") String paramString10, @PluginElement("Layout") Layout<? extends Serializable> paramLayout, @PluginElement("Filter") Filter paramFilter, @PluginAttribute("ignoreExceptions") String paramString11)
/*     */   {
/*  94 */     if (paramString1 == null) {
/*  95 */       LOGGER.error("No name provided for JMSQueueAppender");
/*  96 */       return null;
/*     */     }
/*  98 */     boolean bool = Booleans.parseBoolean(paramString11, true);
/*  99 */     JMSQueueManager localJMSQueueManager = JMSQueueManager.getJMSQueueManager(paramString2, paramString3, paramString4, paramString5, paramString6, paramString7, paramString8, paramString9, paramString10);
/*     */     
/* 101 */     if (localJMSQueueManager == null) {
/* 102 */       return null;
/*     */     }
/* 104 */     if (paramLayout == null) {
/* 105 */       paramLayout = SerializedLayout.createLayout();
/*     */     }
/* 107 */     return new JMSQueueAppender(paramString1, paramFilter, paramLayout, localJMSQueueManager, bool);
/*     */   }
/*     */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\org\apache\logging\log4j\core\appender\JMSQueueAppender.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */
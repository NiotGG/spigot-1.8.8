/*     */ package org.apache.logging.log4j.core.appender.rewrite;
/*     */ 
/*     */ import java.util.Map;
/*     */ import java.util.concurrent.ConcurrentHashMap;
/*     */ import java.util.concurrent.ConcurrentMap;
/*     */ import org.apache.logging.log4j.Logger;
/*     */ import org.apache.logging.log4j.core.Appender;
/*     */ import org.apache.logging.log4j.core.Filter;
/*     */ import org.apache.logging.log4j.core.LogEvent;
/*     */ import org.apache.logging.log4j.core.appender.AbstractAppender;
/*     */ import org.apache.logging.log4j.core.config.AppenderControl;
/*     */ import org.apache.logging.log4j.core.config.AppenderRef;
/*     */ import org.apache.logging.log4j.core.config.Configuration;
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
/*     */ @Plugin(name="Rewrite", category="Core", elementType="appender", printObject=true)
/*     */ public final class RewriteAppender
/*     */   extends AbstractAppender
/*     */ {
/*     */   private final Configuration config;
/*  43 */   private final ConcurrentMap<String, AppenderControl> appenders = new ConcurrentHashMap();
/*     */   
/*     */   private final RewritePolicy rewritePolicy;
/*     */   private final AppenderRef[] appenderRefs;
/*     */   
/*     */   private RewriteAppender(String paramString, Filter paramFilter, boolean paramBoolean, AppenderRef[] paramArrayOfAppenderRef, RewritePolicy paramRewritePolicy, Configuration paramConfiguration)
/*     */   {
/*  50 */     super(paramString, paramFilter, null, paramBoolean);
/*  51 */     this.config = paramConfiguration;
/*  52 */     this.rewritePolicy = paramRewritePolicy;
/*  53 */     this.appenderRefs = paramArrayOfAppenderRef;
/*     */   }
/*     */   
/*     */   public void start()
/*     */   {
/*  58 */     Map localMap = this.config.getAppenders();
/*  59 */     for (AppenderRef localAppenderRef : this.appenderRefs) {
/*  60 */       String str = localAppenderRef.getRef();
/*  61 */       Appender localAppender = (Appender)localMap.get(str);
/*  62 */       if (localAppender != null) {
/*  63 */         Filter localFilter = (localAppender instanceof AbstractAppender) ? ((AbstractAppender)localAppender).getFilter() : null;
/*     */         
/*  65 */         this.appenders.put(str, new AppenderControl(localAppender, localAppenderRef.getLevel(), localFilter));
/*     */       } else {
/*  67 */         LOGGER.error("Appender " + localAppenderRef + " cannot be located. Reference ignored");
/*     */       }
/*     */     }
/*  70 */     super.start();
/*     */   }
/*     */   
/*     */   public void stop()
/*     */   {
/*  75 */     super.stop();
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public void append(LogEvent paramLogEvent)
/*     */   {
/*  84 */     if (this.rewritePolicy != null) {
/*  85 */       paramLogEvent = this.rewritePolicy.rewrite(paramLogEvent);
/*     */     }
/*  87 */     for (AppenderControl localAppenderControl : this.appenders.values()) {
/*  88 */       localAppenderControl.callAppender(paramLogEvent);
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
/*     */   @PluginFactory
/*     */   public static RewriteAppender createAppender(@PluginAttribute("name") String paramString1, @PluginAttribute("ignoreExceptions") String paramString2, @PluginElement("AppenderRef") AppenderRef[] paramArrayOfAppenderRef, @PluginConfiguration Configuration paramConfiguration, @PluginElement("RewritePolicy") RewritePolicy paramRewritePolicy, @PluginElement("Filter") Filter paramFilter)
/*     */   {
/* 112 */     boolean bool = Booleans.parseBoolean(paramString2, true);
/* 113 */     if (paramString1 == null) {
/* 114 */       LOGGER.error("No name provided for RewriteAppender");
/* 115 */       return null;
/*     */     }
/* 117 */     if (paramArrayOfAppenderRef == null) {
/* 118 */       LOGGER.error("No appender references defined for RewriteAppender");
/* 119 */       return null;
/*     */     }
/* 121 */     return new RewriteAppender(paramString1, paramFilter, bool, paramArrayOfAppenderRef, paramRewritePolicy, paramConfiguration);
/*     */   }
/*     */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\org\apache\logging\log4j\core\appender\rewrite\RewriteAppender.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */
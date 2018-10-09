/*     */ package org.apache.logging.log4j.core.appender.rewrite;
/*     */ 
/*     */ import java.util.Arrays;
/*     */ import java.util.HashMap;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import java.util.Map.Entry;
/*     */ import org.apache.logging.log4j.Logger;
/*     */ import org.apache.logging.log4j.core.LogEvent;
/*     */ import org.apache.logging.log4j.core.config.Configuration;
/*     */ import org.apache.logging.log4j.core.config.Property;
/*     */ import org.apache.logging.log4j.core.config.plugins.Plugin;
/*     */ import org.apache.logging.log4j.core.config.plugins.PluginConfiguration;
/*     */ import org.apache.logging.log4j.core.config.plugins.PluginElement;
/*     */ import org.apache.logging.log4j.core.config.plugins.PluginFactory;
/*     */ import org.apache.logging.log4j.core.impl.Log4jLogEvent;
/*     */ import org.apache.logging.log4j.core.lookup.StrSubstitutor;
/*     */ import org.apache.logging.log4j.status.StatusLogger;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ @Plugin(name="PropertiesRewritePolicy", category="Core", elementType="rewritePolicy", printObject=true)
/*     */ public final class PropertiesRewritePolicy
/*     */   implements RewritePolicy
/*     */ {
/*  43 */   protected static final Logger LOGGER = ;
/*     */   
/*     */   private final Map<Property, Boolean> properties;
/*     */   private final Configuration config;
/*     */   
/*     */   private PropertiesRewritePolicy(Configuration paramConfiguration, List<Property> paramList)
/*     */   {
/*  50 */     this.config = paramConfiguration;
/*  51 */     this.properties = new HashMap(paramList.size());
/*  52 */     for (Property localProperty : paramList) {
/*  53 */       Boolean localBoolean = Boolean.valueOf(localProperty.getValue().contains("${"));
/*  54 */       this.properties.put(localProperty, localBoolean);
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public LogEvent rewrite(LogEvent paramLogEvent)
/*     */   {
/*  66 */     HashMap localHashMap = new HashMap(paramLogEvent.getContextMap());
/*  67 */     for (Map.Entry localEntry : this.properties.entrySet()) {
/*  68 */       Property localProperty = (Property)localEntry.getKey();
/*  69 */       localHashMap.put(localProperty.getName(), ((Boolean)localEntry.getValue()).booleanValue() ? this.config.getStrSubstitutor().replace(localProperty.getValue()) : localProperty.getValue());
/*     */     }
/*     */     
/*     */ 
/*  73 */     return new Log4jLogEvent(paramLogEvent.getLoggerName(), paramLogEvent.getMarker(), paramLogEvent.getFQCN(), paramLogEvent.getLevel(), paramLogEvent.getMessage(), paramLogEvent.getThrown(), localHashMap, paramLogEvent.getContextStack(), paramLogEvent.getThreadName(), paramLogEvent.getSource(), paramLogEvent.getMillis());
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public String toString()
/*     */   {
/*  80 */     StringBuilder localStringBuilder = new StringBuilder();
/*  81 */     localStringBuilder.append(" {");
/*  82 */     int i = 1;
/*  83 */     for (Map.Entry localEntry : this.properties.entrySet()) {
/*  84 */       if (i == 0) {
/*  85 */         localStringBuilder.append(", ");
/*     */       }
/*  87 */       Property localProperty = (Property)localEntry.getKey();
/*  88 */       localStringBuilder.append(localProperty.getName()).append("=").append(localProperty.getValue());
/*  89 */       i = 0;
/*     */     }
/*  91 */     localStringBuilder.append("}");
/*  92 */     return localStringBuilder.toString();
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   @PluginFactory
/*     */   public static PropertiesRewritePolicy createPolicy(@PluginConfiguration Configuration paramConfiguration, @PluginElement("Properties") Property[] paramArrayOfProperty)
/*     */   {
/* 104 */     if ((paramArrayOfProperty == null) || (paramArrayOfProperty.length == 0)) {
/* 105 */       LOGGER.error("Properties must be specified for the PropertiesRewritePolicy");
/* 106 */       return null;
/*     */     }
/* 108 */     List localList = Arrays.asList(paramArrayOfProperty);
/* 109 */     return new PropertiesRewritePolicy(paramConfiguration, localList);
/*     */   }
/*     */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\org\apache\logging\log4j\core\appender\rewrite\PropertiesRewritePolicy.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */
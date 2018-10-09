/*     */ package org.apache.logging.log4j.core.filter;
/*     */ 
/*     */ import java.util.ArrayList;
/*     */ import java.util.HashMap;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import java.util.Map.Entry;
/*     */ import org.apache.logging.log4j.Level;
/*     */ import org.apache.logging.log4j.Marker;
/*     */ import org.apache.logging.log4j.core.Filter.Result;
/*     */ import org.apache.logging.log4j.core.LogEvent;
/*     */ import org.apache.logging.log4j.core.config.plugins.Plugin;
/*     */ import org.apache.logging.log4j.core.config.plugins.PluginAttribute;
/*     */ import org.apache.logging.log4j.core.config.plugins.PluginElement;
/*     */ import org.apache.logging.log4j.core.config.plugins.PluginFactory;
/*     */ import org.apache.logging.log4j.core.helpers.KeyValuePair;
/*     */ import org.apache.logging.log4j.message.Message;
/*     */ import org.apache.logging.log4j.message.StructuredDataId;
/*     */ import org.apache.logging.log4j.message.StructuredDataMessage;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ @Plugin(name="StructuredDataFilter", category="Core", elementType="filter", printObject=true)
/*     */ public final class StructuredDataFilter
/*     */   extends MapFilter
/*     */ {
/*     */   private StructuredDataFilter(Map<String, List<String>> paramMap, boolean paramBoolean, Filter.Result paramResult1, Filter.Result paramResult2)
/*     */   {
/*  44 */     super(paramMap, paramBoolean, paramResult1, paramResult2);
/*     */   }
/*     */   
/*     */ 
/*     */   public Filter.Result filter(org.apache.logging.log4j.core.Logger paramLogger, Level paramLevel, Marker paramMarker, Message paramMessage, Throwable paramThrowable)
/*     */   {
/*  50 */     if ((paramMessage instanceof StructuredDataMessage)) {
/*  51 */       return filter((StructuredDataMessage)paramMessage);
/*     */     }
/*  53 */     return Filter.Result.NEUTRAL;
/*     */   }
/*     */   
/*     */   public Filter.Result filter(LogEvent paramLogEvent)
/*     */   {
/*  58 */     Message localMessage = paramLogEvent.getMessage();
/*  59 */     if ((localMessage instanceof StructuredDataMessage)) {
/*  60 */       return filter((StructuredDataMessage)localMessage);
/*     */     }
/*  62 */     return super.filter(paramLogEvent);
/*     */   }
/*     */   
/*     */   protected Filter.Result filter(StructuredDataMessage paramStructuredDataMessage) {
/*  66 */     boolean bool = false;
/*  67 */     for (Map.Entry localEntry : getMap().entrySet()) {
/*  68 */       String str = getValue(paramStructuredDataMessage, (String)localEntry.getKey());
/*  69 */       if (str != null) {
/*  70 */         bool = ((List)localEntry.getValue()).contains(str);
/*     */       } else {
/*  72 */         bool = false;
/*     */       }
/*  74 */       if (((!isAnd()) && (bool)) || ((isAnd()) && (!bool))) {
/*     */         break;
/*     */       }
/*     */     }
/*  78 */     return bool ? this.onMatch : this.onMismatch;
/*     */   }
/*     */   
/*     */   private String getValue(StructuredDataMessage paramStructuredDataMessage, String paramString) {
/*  82 */     if (paramString.equalsIgnoreCase("id"))
/*  83 */       return paramStructuredDataMessage.getId().toString();
/*  84 */     if (paramString.equalsIgnoreCase("id.name"))
/*  85 */       return paramStructuredDataMessage.getId().getName();
/*  86 */     if (paramString.equalsIgnoreCase("type"))
/*  87 */       return paramStructuredDataMessage.getType();
/*  88 */     if (paramString.equalsIgnoreCase("message")) {
/*  89 */       return paramStructuredDataMessage.getFormattedMessage();
/*     */     }
/*  91 */     return (String)paramStructuredDataMessage.getData().get(paramString);
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
/*     */   @PluginFactory
/*     */   public static StructuredDataFilter createFilter(@PluginElement("Pairs") KeyValuePair[] paramArrayOfKeyValuePair, @PluginAttribute("operator") String paramString1, @PluginAttribute("onMatch") String paramString2, @PluginAttribute("onMismatch") String paramString3)
/*     */   {
/* 109 */     if ((paramArrayOfKeyValuePair == null) || (paramArrayOfKeyValuePair.length == 0)) {
/* 110 */       LOGGER.error("keys and values must be specified for the StructuredDataFilter");
/* 111 */       return null;
/*     */     }
/* 113 */     HashMap localHashMap = new HashMap();
/* 114 */     for (KeyValuePair localKeyValuePair : paramArrayOfKeyValuePair) {
/* 115 */       String str1 = localKeyValuePair.getKey();
/* 116 */       if (str1 == null) {
/* 117 */         LOGGER.error("A null key is not valid in MapFilter");
/*     */       }
/*     */       else {
/* 120 */         String str2 = localKeyValuePair.getValue();
/* 121 */         if (str2 == null) {
/* 122 */           LOGGER.error("A null value for key " + str1 + " is not allowed in MapFilter");
/*     */         }
/*     */         else {
/* 125 */           Object localObject = (List)localHashMap.get(localKeyValuePair.getKey());
/* 126 */           if (localObject != null) {
/* 127 */             ((List)localObject).add(str2);
/*     */           } else {
/* 129 */             localObject = new ArrayList();
/* 130 */             ((List)localObject).add(str2);
/* 131 */             localHashMap.put(localKeyValuePair.getKey(), localObject);
/*     */           }
/*     */         } } }
/* 134 */     if (localHashMap.size() == 0) {
/* 135 */       LOGGER.error("StructuredDataFilter is not configured with any valid key value pairs");
/* 136 */       return null;
/*     */     }
/* 138 */     boolean bool = (paramString1 == null) || (!paramString1.equalsIgnoreCase("or"));
/* 139 */     Filter.Result localResult1 = Filter.Result.toResult(paramString2);
/* 140 */     Filter.Result localResult2 = Filter.Result.toResult(paramString3);
/* 141 */     return new StructuredDataFilter(localHashMap, bool, localResult1, localResult2);
/*     */   }
/*     */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\org\apache\logging\log4j\core\filter\StructuredDataFilter.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */
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
/*     */ import org.apache.logging.log4j.message.MapMessage;
/*     */ import org.apache.logging.log4j.message.Message;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ @Plugin(name="MapFilter", category="Core", elementType="filter", printObject=true)
/*     */ public class MapFilter
/*     */   extends AbstractFilter
/*     */ {
/*     */   private final Map<String, List<String>> map;
/*     */   private final boolean isAnd;
/*     */   
/*     */   protected MapFilter(Map<String, List<String>> paramMap, boolean paramBoolean, Filter.Result paramResult1, Filter.Result paramResult2)
/*     */   {
/*  47 */     super(paramResult1, paramResult2);
/*  48 */     if (paramMap == null) {
/*  49 */       throw new NullPointerException("key cannot be null");
/*     */     }
/*  51 */     this.isAnd = paramBoolean;
/*  52 */     this.map = paramMap;
/*     */   }
/*     */   
/*     */ 
/*     */   public Filter.Result filter(org.apache.logging.log4j.core.Logger paramLogger, Level paramLevel, Marker paramMarker, Message paramMessage, Throwable paramThrowable)
/*     */   {
/*  58 */     if ((paramMessage instanceof MapMessage)) {
/*  59 */       return filter(((MapMessage)paramMessage).getData()) ? this.onMatch : this.onMismatch;
/*     */     }
/*  61 */     return Filter.Result.NEUTRAL;
/*     */   }
/*     */   
/*     */   public Filter.Result filter(LogEvent paramLogEvent)
/*     */   {
/*  66 */     Message localMessage = paramLogEvent.getMessage();
/*  67 */     if ((localMessage instanceof MapMessage)) {
/*  68 */       return filter(((MapMessage)localMessage).getData()) ? this.onMatch : this.onMismatch;
/*     */     }
/*  70 */     return Filter.Result.NEUTRAL;
/*     */   }
/*     */   
/*     */   protected boolean filter(Map<String, String> paramMap) {
/*  74 */     boolean bool = false;
/*  75 */     for (Map.Entry localEntry : this.map.entrySet()) {
/*  76 */       String str = (String)paramMap.get(localEntry.getKey());
/*  77 */       if (str != null) {
/*  78 */         bool = ((List)localEntry.getValue()).contains(str);
/*     */       } else {
/*  80 */         bool = false;
/*     */       }
/*  82 */       if (((!this.isAnd) && (bool)) || ((this.isAnd) && (!bool))) {
/*     */         break;
/*     */       }
/*     */     }
/*  86 */     return bool;
/*     */   }
/*     */   
/*     */   public String toString()
/*     */   {
/*  91 */     StringBuilder localStringBuilder = new StringBuilder();
/*  92 */     localStringBuilder.append("isAnd=").append(this.isAnd);
/*  93 */     if (this.map.size() > 0) {
/*  94 */       localStringBuilder.append(", {");
/*  95 */       int i = 1;
/*  96 */       for (Map.Entry localEntry : this.map.entrySet()) {
/*  97 */         if (i == 0) {
/*  98 */           localStringBuilder.append(", ");
/*     */         }
/* 100 */         i = 0;
/* 101 */         List localList = (List)localEntry.getValue();
/* 102 */         String str = localList.size() > 1 ? (String)localList.get(0) : localList.toString();
/* 103 */         localStringBuilder.append((String)localEntry.getKey()).append("=").append(str);
/*     */       }
/* 105 */       localStringBuilder.append("}");
/*     */     }
/* 107 */     return localStringBuilder.toString();
/*     */   }
/*     */   
/*     */   protected boolean isAnd() {
/* 111 */     return this.isAnd;
/*     */   }
/*     */   
/*     */   protected Map<String, List<String>> getMap() {
/* 115 */     return this.map;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   @PluginFactory
/*     */   public static MapFilter createFilter(@PluginElement("Pairs") KeyValuePair[] paramArrayOfKeyValuePair, @PluginAttribute("operator") String paramString1, @PluginAttribute("onMatch") String paramString2, @PluginAttribute("onMismatch") String paramString3)
/*     */   {
/* 124 */     if ((paramArrayOfKeyValuePair == null) || (paramArrayOfKeyValuePair.length == 0)) {
/* 125 */       LOGGER.error("keys and values must be specified for the MapFilter");
/* 126 */       return null;
/*     */     }
/* 128 */     HashMap localHashMap = new HashMap();
/* 129 */     for (KeyValuePair localKeyValuePair : paramArrayOfKeyValuePair) {
/* 130 */       String str1 = localKeyValuePair.getKey();
/* 131 */       if (str1 == null) {
/* 132 */         LOGGER.error("A null key is not valid in MapFilter");
/*     */       }
/*     */       else {
/* 135 */         String str2 = localKeyValuePair.getValue();
/* 136 */         if (str2 == null) {
/* 137 */           LOGGER.error("A null value for key " + str1 + " is not allowed in MapFilter");
/*     */         }
/*     */         else {
/* 140 */           Object localObject = (List)localHashMap.get(localKeyValuePair.getKey());
/* 141 */           if (localObject != null) {
/* 142 */             ((List)localObject).add(str2);
/*     */           } else {
/* 144 */             localObject = new ArrayList();
/* 145 */             ((List)localObject).add(str2);
/* 146 */             localHashMap.put(localKeyValuePair.getKey(), localObject);
/*     */           }
/*     */         } } }
/* 149 */     if (localHashMap.size() == 0) {
/* 150 */       LOGGER.error("MapFilter is not configured with any valid key value pairs");
/* 151 */       return null;
/*     */     }
/* 153 */     boolean bool = (paramString1 == null) || (!paramString1.equalsIgnoreCase("or"));
/* 154 */     Filter.Result localResult1 = Filter.Result.toResult(paramString2);
/* 155 */     Filter.Result localResult2 = Filter.Result.toResult(paramString3);
/* 156 */     return new MapFilter(localHashMap, bool, localResult1, localResult2);
/*     */   }
/*     */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\org\apache\logging\log4j\core\filter\MapFilter.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */
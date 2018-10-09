/*     */ package org.apache.logging.log4j.core.filter;
/*     */ 
/*     */ import java.util.HashMap;
/*     */ import java.util.Map;
/*     */ import java.util.Map.Entry;
/*     */ import org.apache.logging.log4j.Level;
/*     */ import org.apache.logging.log4j.Marker;
/*     */ import org.apache.logging.log4j.ThreadContext;
/*     */ import org.apache.logging.log4j.core.Filter.Result;
/*     */ import org.apache.logging.log4j.core.LogEvent;
/*     */ import org.apache.logging.log4j.core.Logger;
/*     */ import org.apache.logging.log4j.core.config.plugins.Plugin;
/*     */ import org.apache.logging.log4j.core.config.plugins.PluginAttribute;
/*     */ import org.apache.logging.log4j.core.config.plugins.PluginElement;
/*     */ import org.apache.logging.log4j.core.config.plugins.PluginFactory;
/*     */ import org.apache.logging.log4j.core.helpers.KeyValuePair;
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
/*     */ @Plugin(name="DynamicThresholdFilter", category="Core", elementType="filter", printObject=true)
/*     */ public final class DynamicThresholdFilter
/*     */   extends AbstractFilter
/*     */ {
/*  39 */   private Map<String, Level> levelMap = new HashMap();
/*  40 */   private Level defaultThreshold = Level.ERROR;
/*     */   private final String key;
/*     */   
/*     */   private DynamicThresholdFilter(String paramString, Map<String, Level> paramMap, Level paramLevel, Filter.Result paramResult1, Filter.Result paramResult2)
/*     */   {
/*  45 */     super(paramResult1, paramResult2);
/*  46 */     if (paramString == null) {
/*  47 */       throw new NullPointerException("key cannot be null");
/*     */     }
/*  49 */     this.key = paramString;
/*  50 */     this.levelMap = paramMap;
/*  51 */     this.defaultThreshold = paramLevel;
/*     */   }
/*     */   
/*     */   public String getKey() {
/*  55 */     return this.key;
/*     */   }
/*     */   
/*     */ 
/*     */   public Filter.Result filter(Logger paramLogger, Level paramLevel, Marker paramMarker, String paramString, Object... paramVarArgs)
/*     */   {
/*  61 */     return filter(paramLevel);
/*     */   }
/*     */   
/*     */ 
/*     */   public Filter.Result filter(Logger paramLogger, Level paramLevel, Marker paramMarker, Object paramObject, Throwable paramThrowable)
/*     */   {
/*  67 */     return filter(paramLevel);
/*     */   }
/*     */   
/*     */ 
/*     */   public Filter.Result filter(Logger paramLogger, Level paramLevel, Marker paramMarker, Message paramMessage, Throwable paramThrowable)
/*     */   {
/*  73 */     return filter(paramLevel);
/*     */   }
/*     */   
/*     */   public Filter.Result filter(LogEvent paramLogEvent)
/*     */   {
/*  78 */     return filter(paramLogEvent.getLevel());
/*     */   }
/*     */   
/*     */   private Filter.Result filter(Level paramLevel) {
/*  82 */     String str = ThreadContext.get(this.key);
/*  83 */     if (str != null) {
/*  84 */       Level localLevel = (Level)this.levelMap.get(str);
/*  85 */       if (localLevel == null) {
/*  86 */         localLevel = this.defaultThreshold;
/*     */       }
/*  88 */       return paramLevel.isAtLeastAsSpecificAs(localLevel) ? this.onMatch : this.onMismatch;
/*     */     }
/*  90 */     return Filter.Result.NEUTRAL;
/*     */   }
/*     */   
/*     */   public Map<String, Level> getLevelMap()
/*     */   {
/*  95 */     return this.levelMap;
/*     */   }
/*     */   
/*     */   public String toString()
/*     */   {
/* 100 */     StringBuilder localStringBuilder = new StringBuilder();
/* 101 */     localStringBuilder.append("key=").append(this.key);
/* 102 */     localStringBuilder.append(", default=").append(this.defaultThreshold);
/* 103 */     if (this.levelMap.size() > 0) {
/* 104 */       localStringBuilder.append("{");
/* 105 */       int i = 1;
/* 106 */       for (Map.Entry localEntry : this.levelMap.entrySet()) {
/* 107 */         if (i == 0) {
/* 108 */           localStringBuilder.append(", ");
/* 109 */           i = 0;
/*     */         }
/* 111 */         localStringBuilder.append((String)localEntry.getKey()).append("=").append(localEntry.getValue());
/*     */       }
/* 113 */       localStringBuilder.append("}");
/*     */     }
/* 115 */     return localStringBuilder.toString();
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
/*     */   @PluginFactory
/*     */   public static DynamicThresholdFilter createFilter(@PluginAttribute("key") String paramString1, @PluginElement("Pairs") KeyValuePair[] paramArrayOfKeyValuePair, @PluginAttribute("defaultThreshold") String paramString2, @PluginAttribute("onMatch") String paramString3, @PluginAttribute("onMismatch") String paramString4)
/*     */   {
/* 134 */     Filter.Result localResult1 = Filter.Result.toResult(paramString3);
/* 135 */     Filter.Result localResult2 = Filter.Result.toResult(paramString4);
/* 136 */     HashMap localHashMap = new HashMap();
/* 137 */     for (Object localObject2 : paramArrayOfKeyValuePair) {
/* 138 */       localHashMap.put(((KeyValuePair)localObject2).getKey(), Level.toLevel(((KeyValuePair)localObject2).getValue()));
/*     */     }
/* 140 */     ??? = Level.toLevel(paramString2, Level.ERROR);
/* 141 */     return new DynamicThresholdFilter(paramString1, localHashMap, (Level)???, localResult1, localResult2);
/*     */   }
/*     */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\org\apache\logging\log4j\core\filter\DynamicThresholdFilter.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */
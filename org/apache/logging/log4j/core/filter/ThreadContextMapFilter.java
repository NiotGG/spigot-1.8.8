/*     */ package org.apache.logging.log4j.core.filter;
/*     */ 
/*     */ import java.util.ArrayList;
/*     */ import java.util.HashMap;
/*     */ import java.util.Iterator;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import java.util.Map.Entry;
/*     */ import java.util.Set;
/*     */ import org.apache.logging.log4j.Level;
/*     */ import org.apache.logging.log4j.Marker;
/*     */ import org.apache.logging.log4j.ThreadContext;
/*     */ import org.apache.logging.log4j.core.Filter.Result;
/*     */ import org.apache.logging.log4j.core.LogEvent;
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
/*     */ 
/*     */ 
/*     */ @Plugin(name="ThreadContextMapFilter", category="Core", elementType="filter", printObject=true)
/*     */ public class ThreadContextMapFilter
/*     */   extends MapFilter
/*     */ {
/*     */   private final String key;
/*     */   private final String value;
/*     */   private final boolean useMap;
/*     */   
/*     */   public ThreadContextMapFilter(Map<String, List<String>> paramMap, boolean paramBoolean, Filter.Result paramResult1, Filter.Result paramResult2)
/*     */   {
/*  50 */     super(paramMap, paramBoolean, paramResult1, paramResult2);
/*  51 */     if (paramMap.size() == 1) {
/*  52 */       Iterator localIterator = paramMap.entrySet().iterator();
/*  53 */       Map.Entry localEntry = (Map.Entry)localIterator.next();
/*  54 */       if (((List)localEntry.getValue()).size() == 1) {
/*  55 */         this.key = ((String)localEntry.getKey());
/*  56 */         this.value = ((String)((List)localEntry.getValue()).get(0));
/*  57 */         this.useMap = false;
/*     */       } else {
/*  59 */         this.key = null;
/*  60 */         this.value = null;
/*  61 */         this.useMap = true;
/*     */       }
/*     */     } else {
/*  64 */       this.key = null;
/*  65 */       this.value = null;
/*  66 */       this.useMap = true;
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */   public Filter.Result filter(org.apache.logging.log4j.core.Logger paramLogger, Level paramLevel, Marker paramMarker, String paramString, Object... paramVarArgs)
/*     */   {
/*  73 */     return filter();
/*     */   }
/*     */   
/*     */ 
/*     */   public Filter.Result filter(org.apache.logging.log4j.core.Logger paramLogger, Level paramLevel, Marker paramMarker, Object paramObject, Throwable paramThrowable)
/*     */   {
/*  79 */     return filter();
/*     */   }
/*     */   
/*     */ 
/*     */   public Filter.Result filter(org.apache.logging.log4j.core.Logger paramLogger, Level paramLevel, Marker paramMarker, Message paramMessage, Throwable paramThrowable)
/*     */   {
/*  85 */     return filter();
/*     */   }
/*     */   
/*     */   private Filter.Result filter() {
/*  89 */     boolean bool = false;
/*  90 */     if (this.useMap) {
/*  91 */       for (Map.Entry localEntry : getMap().entrySet()) {
/*  92 */         String str = ThreadContext.get((String)localEntry.getKey());
/*  93 */         if (str != null) {
/*  94 */           bool = ((List)localEntry.getValue()).contains(str);
/*     */         } else {
/*  96 */           bool = false;
/*     */         }
/*  98 */         if (((!isAnd()) && (bool)) || ((isAnd()) && (!bool))) {
/*     */           break;
/*     */         }
/*     */       }
/*     */     } else {
/* 103 */       bool = this.value.equals(ThreadContext.get(this.key));
/*     */     }
/* 105 */     return bool ? this.onMatch : this.onMismatch;
/*     */   }
/*     */   
/*     */   public Filter.Result filter(LogEvent paramLogEvent)
/*     */   {
/* 110 */     return super.filter(paramLogEvent.getContextMap()) ? this.onMatch : this.onMismatch;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   @PluginFactory
/*     */   public static ThreadContextMapFilter createFilter(@PluginElement("Pairs") KeyValuePair[] paramArrayOfKeyValuePair, @PluginAttribute("operator") String paramString1, @PluginAttribute("onMatch") String paramString2, @PluginAttribute("onMismatch") String paramString3)
/*     */   {
/* 119 */     if ((paramArrayOfKeyValuePair == null) || (paramArrayOfKeyValuePair.length == 0)) {
/* 120 */       LOGGER.error("key and value pairs must be specified for the ThreadContextMapFilter");
/* 121 */       return null;
/*     */     }
/* 123 */     HashMap localHashMap = new HashMap();
/* 124 */     for (KeyValuePair localKeyValuePair : paramArrayOfKeyValuePair) {
/* 125 */       String str1 = localKeyValuePair.getKey();
/* 126 */       if (str1 == null) {
/* 127 */         LOGGER.error("A null key is not valid in MapFilter");
/*     */       }
/*     */       else {
/* 130 */         String str2 = localKeyValuePair.getValue();
/* 131 */         if (str2 == null) {
/* 132 */           LOGGER.error("A null value for key " + str1 + " is not allowed in MapFilter");
/*     */         }
/*     */         else {
/* 135 */           Object localObject = (List)localHashMap.get(localKeyValuePair.getKey());
/* 136 */           if (localObject != null) {
/* 137 */             ((List)localObject).add(str2);
/*     */           } else {
/* 139 */             localObject = new ArrayList();
/* 140 */             ((List)localObject).add(str2);
/* 141 */             localHashMap.put(localKeyValuePair.getKey(), localObject);
/*     */           }
/*     */         } } }
/* 144 */     if (localHashMap.size() == 0) {
/* 145 */       LOGGER.error("ThreadContextMapFilter is not configured with any valid key value pairs");
/* 146 */       return null;
/*     */     }
/* 148 */     boolean bool = (paramString1 == null) || (!paramString1.equalsIgnoreCase("or"));
/* 149 */     Filter.Result localResult1 = Filter.Result.toResult(paramString2);
/* 150 */     Filter.Result localResult2 = Filter.Result.toResult(paramString3);
/* 151 */     return new ThreadContextMapFilter(localHashMap, bool, localResult1, localResult2);
/*     */   }
/*     */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\org\apache\logging\log4j\core\filter\ThreadContextMapFilter.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */
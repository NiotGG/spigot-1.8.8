/*     */ package org.apache.logging.log4j.core.appender.rewrite;
/*     */ 
/*     */ import java.util.HashMap;
/*     */ import java.util.Iterator;
/*     */ import java.util.Map;
/*     */ import java.util.Map.Entry;
/*     */ import java.util.Set;
/*     */ import org.apache.logging.log4j.Logger;
/*     */ import org.apache.logging.log4j.core.LogEvent;
/*     */ import org.apache.logging.log4j.core.config.plugins.Plugin;
/*     */ import org.apache.logging.log4j.core.config.plugins.PluginAttribute;
/*     */ import org.apache.logging.log4j.core.config.plugins.PluginElement;
/*     */ import org.apache.logging.log4j.core.config.plugins.PluginFactory;
/*     */ import org.apache.logging.log4j.core.helpers.KeyValuePair;
/*     */ import org.apache.logging.log4j.core.impl.Log4jLogEvent;
/*     */ import org.apache.logging.log4j.message.MapMessage;
/*     */ import org.apache.logging.log4j.message.Message;
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
/*     */ @Plugin(name="MapRewritePolicy", category="Core", elementType="rewritePolicy", printObject=true)
/*     */ public final class MapRewritePolicy
/*     */   implements RewritePolicy
/*     */ {
/*  42 */   protected static final Logger LOGGER = ;
/*     */   
/*     */   private final Map<String, String> map;
/*     */   private final Mode mode;
/*     */   
/*     */   private MapRewritePolicy(Map<String, String> paramMap, Mode paramMode)
/*     */   {
/*  49 */     this.map = paramMap;
/*  50 */     this.mode = paramMode;
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
/*  61 */     Message localMessage = paramLogEvent.getMessage();
/*  62 */     if ((localMessage == null) || (!(localMessage instanceof MapMessage))) {
/*  63 */       return paramLogEvent;
/*     */     }
/*     */     
/*  66 */     HashMap localHashMap = new HashMap(((MapMessage)localMessage).getData());
/*  67 */     switch (this.mode) {
/*     */     case Add: 
/*  69 */       localHashMap.putAll(this.map);
/*  70 */       break;
/*     */     
/*     */     default: 
/*  73 */       for (localObject1 = this.map.entrySet().iterator(); ((Iterator)localObject1).hasNext();) { localObject2 = (Map.Entry)((Iterator)localObject1).next();
/*  74 */         if (localHashMap.containsKey(((Map.Entry)localObject2).getKey())) {
/*  75 */           localHashMap.put(((Map.Entry)localObject2).getKey(), ((Map.Entry)localObject2).getValue());
/*     */         }
/*     */       }
/*     */     }
/*     */     Object localObject2;
/*  80 */     Object localObject1 = ((MapMessage)localMessage).newInstance(localHashMap);
/*  81 */     if ((paramLogEvent instanceof Log4jLogEvent)) {
/*  82 */       localObject2 = (Log4jLogEvent)paramLogEvent;
/*  83 */       return Log4jLogEvent.createEvent(((Log4jLogEvent)localObject2).getLoggerName(), ((Log4jLogEvent)localObject2).getMarker(), ((Log4jLogEvent)localObject2).getFQCN(), ((Log4jLogEvent)localObject2).getLevel(), (Message)localObject1, ((Log4jLogEvent)localObject2).getThrownProxy(), ((Log4jLogEvent)localObject2).getContextMap(), ((Log4jLogEvent)localObject2).getContextStack(), ((Log4jLogEvent)localObject2).getThreadName(), ((Log4jLogEvent)localObject2).getSource(), ((Log4jLogEvent)localObject2).getMillis());
/*     */     }
/*     */     
/*     */ 
/*  87 */     return new Log4jLogEvent(paramLogEvent.getLoggerName(), paramLogEvent.getMarker(), paramLogEvent.getFQCN(), paramLogEvent.getLevel(), (Message)localObject1, paramLogEvent.getThrown(), paramLogEvent.getContextMap(), paramLogEvent.getContextStack(), paramLogEvent.getThreadName(), paramLogEvent.getSource(), paramLogEvent.getMillis());
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
/*     */   public static enum Mode
/*     */   {
/* 100 */     Add, 
/*     */     
/*     */ 
/*     */ 
/* 104 */     Update;
/*     */     
/*     */     private Mode() {}
/*     */   }
/*     */   
/* 109 */   public String toString() { StringBuilder localStringBuilder = new StringBuilder();
/* 110 */     localStringBuilder.append("mode=").append(this.mode);
/* 111 */     localStringBuilder.append(" {");
/* 112 */     int i = 1;
/* 113 */     for (Map.Entry localEntry : this.map.entrySet()) {
/* 114 */       if (i == 0) {
/* 115 */         localStringBuilder.append(", ");
/*     */       }
/* 117 */       localStringBuilder.append((String)localEntry.getKey()).append("=").append((String)localEntry.getValue());
/* 118 */       i = 0;
/*     */     }
/* 120 */     localStringBuilder.append("}");
/* 121 */     return localStringBuilder.toString();
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   @PluginFactory
/*     */   public static MapRewritePolicy createPolicy(@PluginAttribute("mode") String paramString, @PluginElement("KeyValuePair") KeyValuePair[] paramArrayOfKeyValuePair)
/*     */   {
/*     */     Mode localMode;
/*     */     
/*     */ 
/*     */ 
/* 135 */     if (paramString == null) {
/* 136 */       localMode = Mode.Add;
/*     */     } else {
/* 138 */       localMode = Mode.valueOf(paramString);
/* 139 */       if (localMode == null) {
/* 140 */         LOGGER.error("Undefined mode " + paramString);
/* 141 */         return null;
/*     */       }
/*     */     }
/* 144 */     if ((paramArrayOfKeyValuePair == null) || (paramArrayOfKeyValuePair.length == 0)) {
/* 145 */       LOGGER.error("keys and values must be specified for the MapRewritePolicy");
/* 146 */       return null;
/*     */     }
/* 148 */     HashMap localHashMap = new HashMap();
/* 149 */     for (KeyValuePair localKeyValuePair : paramArrayOfKeyValuePair) {
/* 150 */       String str1 = localKeyValuePair.getKey();
/* 151 */       if (str1 == null) {
/* 152 */         LOGGER.error("A null key is not valid in MapRewritePolicy");
/*     */       }
/*     */       else {
/* 155 */         String str2 = localKeyValuePair.getValue();
/* 156 */         if (str2 == null) {
/* 157 */           LOGGER.error("A null value for key " + str1 + " is not allowed in MapRewritePolicy");
/*     */         }
/*     */         else
/* 160 */           localHashMap.put(localKeyValuePair.getKey(), localKeyValuePair.getValue());
/*     */       } }
/* 162 */     if (localHashMap.size() == 0) {
/* 163 */       LOGGER.error("MapRewritePolicy is not configured with any valid key value pairs");
/* 164 */       return null;
/*     */     }
/* 166 */     return new MapRewritePolicy(localHashMap, localMode);
/*     */   }
/*     */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\org\apache\logging\log4j\core\appender\rewrite\MapRewritePolicy.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */
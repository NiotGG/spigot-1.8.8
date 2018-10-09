/*    */ package org.apache.logging.log4j.core.layout;
/*    */ 
/*    */ import java.util.Collections;
/*    */ import java.util.HashMap;
/*    */ import java.util.Map;
/*    */ import org.apache.logging.log4j.core.config.plugins.Plugin;
/*    */ import org.apache.logging.log4j.core.config.plugins.PluginAttribute;
/*    */ import org.apache.logging.log4j.core.config.plugins.PluginElement;
/*    */ import org.apache.logging.log4j.core.config.plugins.PluginFactory;
/*    */ import org.apache.logging.log4j.core.helpers.Booleans;
/*    */ import org.apache.logging.log4j.core.helpers.KeyValuePair;
/*    */ import org.apache.logging.log4j.message.StructuredDataId;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ @Plugin(name="LoggerFields", category="Core", printObject=true)
/*    */ public final class LoggerFields
/*    */ {
/*    */   private final Map<String, String> map;
/*    */   private final String sdId;
/*    */   private final String enterpriseId;
/*    */   private final boolean discardIfAllFieldsAreEmpty;
/*    */   
/*    */   private LoggerFields(Map<String, String> paramMap, String paramString1, String paramString2, boolean paramBoolean)
/*    */   {
/* 44 */     this.sdId = paramString1;
/* 45 */     this.enterpriseId = paramString2;
/* 46 */     this.map = Collections.unmodifiableMap(paramMap);
/* 47 */     this.discardIfAllFieldsAreEmpty = paramBoolean;
/*    */   }
/*    */   
/*    */   public Map<String, String> getMap() {
/* 51 */     return this.map;
/*    */   }
/*    */   
/*    */   public String toString()
/*    */   {
/* 56 */     return this.map.toString();
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   @PluginFactory
/*    */   public static LoggerFields createLoggerFields(@PluginElement("LoggerFields") KeyValuePair[] paramArrayOfKeyValuePair, @PluginAttribute("sdId") String paramString1, @PluginAttribute("enterpriseId") String paramString2, @PluginAttribute("discardIfAllFieldsAreEmpty") String paramString3)
/*    */   {
/* 76 */     HashMap localHashMap = new HashMap();
/*    */     
/* 78 */     for (KeyValuePair localKeyValuePair : paramArrayOfKeyValuePair) {
/* 79 */       localHashMap.put(localKeyValuePair.getKey(), localKeyValuePair.getValue());
/*    */     }
/*    */     
/* 82 */     boolean bool = Booleans.parseBoolean(paramString3, false);
/* 83 */     return new LoggerFields(localHashMap, paramString1, paramString2, bool);
/*    */   }
/*    */   
/*    */   public StructuredDataId getSdId() {
/* 87 */     if ((this.enterpriseId == null) || (this.sdId == null)) {
/* 88 */       return null;
/*    */     }
/* 90 */     int i = Integer.parseInt(this.enterpriseId);
/* 91 */     return new StructuredDataId(this.sdId, i, null, null);
/*    */   }
/*    */   
/*    */   public boolean getDiscardIfAllFieldsAreEmpty() {
/* 95 */     return this.discardIfAllFieldsAreEmpty;
/*    */   }
/*    */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\org\apache\logging\log4j\core\layout\LoggerFields.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */
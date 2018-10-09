/*    */ package org.apache.logging.log4j.core.lookup;
/*    */ 
/*    */ import java.util.Map;
/*    */ import org.apache.logging.log4j.core.LogEvent;
/*    */ import org.apache.logging.log4j.core.config.plugins.Plugin;
/*    */ import org.apache.logging.log4j.message.MapMessage;
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
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ @Plugin(name="map", category="Lookup")
/*    */ public class MapLookup
/*    */   implements StrLookup
/*    */ {
/*    */   private final Map<String, String> map;
/*    */   
/*    */   public MapLookup(Map<String, String> paramMap)
/*    */   {
/* 41 */     this.map = paramMap;
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */   public MapLookup()
/*    */   {
/* 48 */     this.map = null;
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
/*    */   public String lookup(String paramString)
/*    */   {
/* 62 */     if (this.map == null) {
/* 63 */       return null;
/*    */     }
/* 65 */     String str = (String)this.map.get(paramString);
/* 66 */     if (str == null) {
/* 67 */       return null;
/*    */     }
/* 69 */     return str;
/*    */   }
/*    */   
/*    */   public String lookup(LogEvent paramLogEvent, String paramString)
/*    */   {
/* 74 */     if ((this.map == null) && (!(paramLogEvent.getMessage() instanceof MapMessage))) {
/* 75 */       return null;
/*    */     }
/* 77 */     if ((this.map != null) && (this.map.containsKey(paramString))) {
/* 78 */       String str = (String)this.map.get(paramString);
/* 79 */       if (str != null) {
/* 80 */         return str;
/*    */       }
/*    */     }
/* 83 */     if ((paramLogEvent.getMessage() instanceof MapMessage)) {
/* 84 */       return ((MapMessage)paramLogEvent.getMessage()).get(paramString);
/*    */     }
/* 86 */     return null;
/*    */   }
/*    */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\org\apache\logging\log4j\core\lookup\MapLookup.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */
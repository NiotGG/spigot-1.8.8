/*    */ package org.apache.logging.log4j.core.lookup;
/*    */ 
/*    */ import org.apache.logging.log4j.core.LogEvent;
/*    */ import org.apache.logging.log4j.core.config.plugins.Plugin;
/*    */ import org.apache.logging.log4j.message.StructuredDataId;
/*    */ import org.apache.logging.log4j.message.StructuredDataMessage;
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
/*    */ @Plugin(name="sd", category="Lookup")
/*    */ public class StructuredDataLookup
/*    */   implements StrLookup
/*    */ {
/*    */   public String lookup(String paramString)
/*    */   {
/* 36 */     return null;
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   public String lookup(LogEvent paramLogEvent, String paramString)
/*    */   {
/* 47 */     if ((paramLogEvent == null) || (!(paramLogEvent.getMessage() instanceof StructuredDataMessage))) {
/* 48 */       return null;
/*    */     }
/* 50 */     StructuredDataMessage localStructuredDataMessage = (StructuredDataMessage)paramLogEvent.getMessage();
/* 51 */     if (paramString.equalsIgnoreCase("id"))
/* 52 */       return localStructuredDataMessage.getId().getName();
/* 53 */     if (paramString.equalsIgnoreCase("type")) {
/* 54 */       return localStructuredDataMessage.getType();
/*    */     }
/* 56 */     return localStructuredDataMessage.get(paramString);
/*    */   }
/*    */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\org\apache\logging\log4j\core\lookup\StructuredDataLookup.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */
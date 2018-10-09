/*    */ package org.apache.logging.log4j.core.lookup;
/*    */ 
/*    */ import org.apache.logging.log4j.core.LogEvent;
/*    */ import org.apache.logging.log4j.core.config.plugins.Plugin;
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
/*    */ @Plugin(name="sys", category="Lookup")
/*    */ public class SystemPropertiesLookup
/*    */   implements StrLookup
/*    */ {
/*    */   public String lookup(String paramString)
/*    */   {
/*    */     try
/*    */     {
/* 36 */       return System.getProperty(paramString);
/*    */     } catch (Exception localException) {}
/* 38 */     return null;
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
/*    */     try
/*    */     {
/* 51 */       return System.getProperty(paramString);
/*    */     } catch (Exception localException) {}
/* 53 */     return null;
/*    */   }
/*    */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\org\apache\logging\log4j\core\lookup\SystemPropertiesLookup.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */
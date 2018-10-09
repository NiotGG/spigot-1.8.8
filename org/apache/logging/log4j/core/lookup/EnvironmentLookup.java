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
/*    */ 
/*    */ @Plugin(name="env", category="Lookup")
/*    */ public class EnvironmentLookup
/*    */   implements StrLookup
/*    */ {
/*    */   public String lookup(String paramString)
/*    */   {
/* 35 */     return System.getenv(paramString);
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
/* 46 */     return System.getenv(paramString);
/*    */   }
/*    */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\org\apache\logging\log4j\core\lookup\EnvironmentLookup.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */
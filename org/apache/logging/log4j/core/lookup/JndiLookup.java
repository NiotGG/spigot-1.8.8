/*    */ package org.apache.logging.log4j.core.lookup;
/*    */ 
/*    */ import javax.naming.InitialContext;
/*    */ import javax.naming.NamingException;
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
/*    */ 
/*    */ 
/*    */ @Plugin(name="jndi", category="Lookup")
/*    */ public class JndiLookup
/*    */   implements StrLookup
/*    */ {
/*    */   static final String CONTAINER_JNDI_RESOURCE_PATH_PREFIX = "java:comp/env/";
/*    */   
/*    */   public String lookup(String paramString)
/*    */   {
/* 41 */     return lookup(null, paramString);
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
/* 52 */     if (paramString == null) {
/* 53 */       return null;
/*    */     }
/*    */     try
/*    */     {
/* 57 */       InitialContext localInitialContext = new InitialContext();
/* 58 */       return (String)localInitialContext.lookup(convertJndiName(paramString));
/*    */     } catch (NamingException localNamingException) {}
/* 60 */     return null;
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   private String convertJndiName(String paramString)
/*    */   {
/* 72 */     if ((!paramString.startsWith("java:comp/env/")) && (paramString.indexOf(':') == -1)) {
/* 73 */       paramString = "java:comp/env/" + paramString;
/*    */     }
/*    */     
/* 76 */     return paramString;
/*    */   }
/*    */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\org\apache\logging\log4j\core\lookup\JndiLookup.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */
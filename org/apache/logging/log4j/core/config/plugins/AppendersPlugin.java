/*    */ package org.apache.logging.log4j.core.config.plugins;
/*    */ 
/*    */ import java.util.concurrent.ConcurrentHashMap;
/*    */ import java.util.concurrent.ConcurrentMap;
/*    */ import org.apache.logging.log4j.core.Appender;
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
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ @Plugin(name="appenders", category="Core")
/*    */ public final class AppendersPlugin
/*    */ {
/*    */   @PluginFactory
/*    */   public static ConcurrentMap<String, Appender> createAppenders(@PluginElement("Appenders") Appender[] paramArrayOfAppender)
/*    */   {
/* 42 */     ConcurrentHashMap localConcurrentHashMap = new ConcurrentHashMap();
/*    */     
/*    */ 
/* 45 */     for (Appender localAppender : paramArrayOfAppender) {
/* 46 */       localConcurrentHashMap.put(localAppender.getName(), localAppender);
/*    */     }
/*    */     
/* 49 */     return localConcurrentHashMap;
/*    */   }
/*    */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\org\apache\logging\log4j\core\config\plugins\AppendersPlugin.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */
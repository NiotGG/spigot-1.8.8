/*    */ package org.apache.logging.log4j.core.config.plugins;
/*    */ 
/*    */ import java.util.concurrent.ConcurrentHashMap;
/*    */ import java.util.concurrent.ConcurrentMap;
/*    */ import org.apache.logging.log4j.core.config.LoggerConfig;
/*    */ import org.apache.logging.log4j.core.config.Loggers;
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
/*    */ @Plugin(name="loggers", category="Core")
/*    */ public final class LoggersPlugin
/*    */ {
/*    */   @PluginFactory
/*    */   public static Loggers createLoggers(@PluginElement("Loggers") LoggerConfig[] paramArrayOfLoggerConfig)
/*    */   {
/* 41 */     ConcurrentHashMap localConcurrentHashMap = new ConcurrentHashMap();
/* 42 */     Object localObject = null;
/*    */     
/* 44 */     for (LoggerConfig localLoggerConfig : paramArrayOfLoggerConfig) {
/* 45 */       if (localLoggerConfig != null) {
/* 46 */         if (localLoggerConfig.getName().isEmpty()) {
/* 47 */           localObject = localLoggerConfig;
/*    */         }
/* 49 */         localConcurrentHashMap.put(localLoggerConfig.getName(), localLoggerConfig);
/*    */       }
/*    */     }
/*    */     
/* 53 */     return new Loggers(localConcurrentHashMap, (LoggerConfig)localObject);
/*    */   }
/*    */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\org\apache\logging\log4j\core\config\plugins\LoggersPlugin.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */
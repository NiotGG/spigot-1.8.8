/*    */ package org.apache.logging.log4j.core.config;
/*    */ 
/*    */ import java.util.concurrent.ConcurrentMap;
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
/*    */ public class Loggers
/*    */ {
/*    */   private final ConcurrentMap<String, LoggerConfig> map;
/*    */   private final LoggerConfig root;
/*    */   
/*    */   public Loggers(ConcurrentMap<String, LoggerConfig> paramConcurrentMap, LoggerConfig paramLoggerConfig)
/*    */   {
/* 29 */     this.map = paramConcurrentMap;
/* 30 */     this.root = paramLoggerConfig;
/*    */   }
/*    */   
/*    */   public ConcurrentMap<String, LoggerConfig> getMap() {
/* 34 */     return this.map;
/*    */   }
/*    */   
/*    */   public LoggerConfig getRoot() {
/* 38 */     return this.root;
/*    */   }
/*    */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\org\apache\logging\log4j\core\config\Loggers.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */
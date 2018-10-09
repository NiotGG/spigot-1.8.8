/*    */ package org.apache.logging.log4j.spi;
/*    */ 
/*    */ import java.net.URL;
/*    */ import java.util.Properties;
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
/*    */ public class Provider
/*    */ {
/* 26 */   private static final Integer DEFAULT_PRIORITY = Integer.valueOf(-1);
/*    */   private static final String FACTORY_PRIORITY = "FactoryPriority";
/*    */   private static final String THREAD_CONTEXT_MAP = "ThreadContextMap";
/*    */   private static final String LOGGER_CONTEXT_FACTORY = "LoggerContextFactory";
/*    */   private final Integer priority;
/*    */   private final String className;
/*    */   private final String threadContextMap;
/*    */   private final URL url;
/*    */   
/*    */   public Provider(Properties paramProperties, URL paramURL)
/*    */   {
/* 37 */     this.url = paramURL;
/* 38 */     String str = paramProperties.getProperty("FactoryPriority");
/* 39 */     this.priority = (str == null ? DEFAULT_PRIORITY : Integer.valueOf(str));
/* 40 */     this.className = paramProperties.getProperty("LoggerContextFactory");
/* 41 */     this.threadContextMap = paramProperties.getProperty("ThreadContextMap");
/*    */   }
/*    */   
/*    */   public Integer getPriority() {
/* 45 */     return this.priority;
/*    */   }
/*    */   
/*    */   public String getClassName() {
/* 49 */     return this.className;
/*    */   }
/*    */   
/*    */   public String getThreadContextMap() {
/* 53 */     return this.threadContextMap;
/*    */   }
/*    */   
/*    */   public URL getURL() {
/* 57 */     return this.url;
/*    */   }
/*    */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\org\apache\logging\log4j\spi\Provider.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */
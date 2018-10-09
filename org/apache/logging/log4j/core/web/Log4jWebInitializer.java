/*    */ package org.apache.logging.log4j.core.web;
/*    */ 
/*    */ import javax.servlet.UnavailableException;
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
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ abstract interface Log4jWebInitializer
/*    */ {
/*    */   public static final String LOG4J_CONTEXT_NAME = "log4jContextName";
/*    */   public static final String LOG4J_CONFIG_LOCATION = "log4jConfiguration";
/*    */   public static final String IS_LOG4J_CONTEXT_SELECTOR_NAMED = "isLog4jContextSelectorNamed";
/* 46 */   public static final String INITIALIZER_ATTRIBUTE = Log4jWebInitializer.class.getName() + ".INSTANCE";
/*    */   
/*    */   public abstract void initialize()
/*    */     throws UnavailableException;
/*    */   
/*    */   public abstract void deinitialize();
/*    */   
/*    */   public abstract void setLoggerContext();
/*    */   
/*    */   public abstract void clearLoggerContext();
/*    */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\org\apache\logging\log4j\core\web\Log4jWebInitializer.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */
/*    */ package org.apache.logging.log4j.core.config;
/*    */ 
/*    */ import org.apache.logging.log4j.Level;
/*    */ import org.apache.logging.log4j.core.Appender;
/*    */ import org.apache.logging.log4j.core.appender.ConsoleAppender;
/*    */ import org.apache.logging.log4j.core.layout.PatternLayout;
/*    */ import org.apache.logging.log4j.util.PropertiesUtil;
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
/*    */ public class DefaultConfiguration
/*    */   extends BaseConfiguration
/*    */ {
/*    */   public static final String DEFAULT_NAME = "Default";
/*    */   public static final String DEFAULT_LEVEL = "org.apache.logging.log4j.level";
/*    */   
/*    */   public DefaultConfiguration()
/*    */   {
/* 50 */     setName("Default");
/* 51 */     PatternLayout localPatternLayout = PatternLayout.createLayout("%d{HH:mm:ss.SSS} [%thread] %-5level %logger{36} - %msg%n", null, null, null, null);
/*    */     
/* 53 */     ConsoleAppender localConsoleAppender = ConsoleAppender.createAppender(localPatternLayout, null, "SYSTEM_OUT", "Console", "false", "true");
/*    */     
/* 55 */     localConsoleAppender.start();
/* 56 */     addAppender(localConsoleAppender);
/* 57 */     LoggerConfig localLoggerConfig = getRootLogger();
/* 58 */     localLoggerConfig.addAppender(localConsoleAppender, null, null);
/*    */     
/* 60 */     String str = PropertiesUtil.getProperties().getStringProperty("org.apache.logging.log4j.level");
/* 61 */     Level localLevel = (str != null) && (Level.valueOf(str) != null) ? Level.valueOf(str) : Level.ERROR;
/*    */     
/* 63 */     localLoggerConfig.setLevel(localLevel);
/*    */   }
/*    */   
/*    */   protected void doConfigure() {}
/*    */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\org\apache\logging\log4j\core\config\DefaultConfiguration.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */
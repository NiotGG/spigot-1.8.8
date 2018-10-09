/*    */ package org.apache.logging.log4j.core.config;
/*    */ 
/*    */ import org.apache.logging.log4j.Level;
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
/*    */ public class NullConfiguration
/*    */   extends BaseConfiguration
/*    */ {
/*    */   public static final String NULL_NAME = "Null";
/*    */   
/*    */   public NullConfiguration()
/*    */   {
/* 30 */     setName("Null");
/* 31 */     LoggerConfig localLoggerConfig = getRootLogger();
/* 32 */     localLoggerConfig.setLevel(Level.OFF);
/*    */   }
/*    */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\org\apache\logging\log4j\core\config\NullConfiguration.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */
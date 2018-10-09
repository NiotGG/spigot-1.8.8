/*    */ package org.apache.logging.log4j.core.config;
/*    */ 
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
/*    */ @Plugin(name="XMLConfigurationFactory", category="ConfigurationFactory")
/*    */ @Order(5)
/*    */ public class XMLConfigurationFactory
/*    */   extends ConfigurationFactory
/*    */ {
/* 31 */   public static final String[] SUFFIXES = { ".xml", "*" };
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   public Configuration getConfiguration(ConfigurationFactory.ConfigurationSource paramConfigurationSource)
/*    */   {
/* 40 */     return new XMLConfiguration(paramConfigurationSource);
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   public String[] getSupportedTypes()
/*    */   {
/* 49 */     return SUFFIXES;
/*    */   }
/*    */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\org\apache\logging\log4j\core\config\XMLConfigurationFactory.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */
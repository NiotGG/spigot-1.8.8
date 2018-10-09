/*    */ package org.apache.logging.log4j.core.config;
/*    */ 
/*    */ import java.io.File;
/*    */ import org.apache.logging.log4j.Logger;
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
/*    */ @Plugin(name="JSONConfigurationFactory", category="ConfigurationFactory")
/*    */ @Order(6)
/*    */ public class JSONConfigurationFactory
/*    */   extends ConfigurationFactory
/*    */ {
/* 33 */   public static final String[] SUFFIXES = { ".json", ".jsn" };
/*    */   
/* 35 */   private static String[] dependencies = { "com.fasterxml.jackson.databind.ObjectMapper", "com.fasterxml.jackson.databind.JsonNode", "com.fasterxml.jackson.core.JsonParser" };
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/* 41 */   private final File configFile = null;
/*    */   private boolean isActive;
/*    */   
/*    */   public JSONConfigurationFactory()
/*    */   {
/*    */     try {
/* 47 */       for (String str : dependencies) {
/* 48 */         Class.forName(str);
/*    */       }
/*    */     } catch (ClassNotFoundException localClassNotFoundException) {
/* 51 */       LOGGER.debug("Missing dependencies for Json support");
/* 52 */       this.isActive = false;
/* 53 */       return;
/*    */     }
/* 55 */     this.isActive = true;
/*    */   }
/*    */   
/*    */   protected boolean isActive()
/*    */   {
/* 60 */     return this.isActive;
/*    */   }
/*    */   
/*    */   public Configuration getConfiguration(ConfigurationFactory.ConfigurationSource paramConfigurationSource)
/*    */   {
/* 65 */     if (!this.isActive) {
/* 66 */       return null;
/*    */     }
/* 68 */     return new JSONConfiguration(paramConfigurationSource);
/*    */   }
/*    */   
/*    */   public String[] getSupportedTypes()
/*    */   {
/* 73 */     return SUFFIXES;
/*    */   }
/*    */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\org\apache\logging\log4j\core\config\JSONConfigurationFactory.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */
/*    */ package org.apache.logging.log4j.core.config;
/*    */ 
/*    */ import org.apache.logging.log4j.Logger;
/*    */ import org.apache.logging.log4j.core.config.plugins.Plugin;
/*    */ import org.apache.logging.log4j.core.config.plugins.PluginAttribute;
/*    */ import org.apache.logging.log4j.core.config.plugins.PluginFactory;
/*    */ import org.apache.logging.log4j.core.config.plugins.PluginValue;
/*    */ import org.apache.logging.log4j.status.StatusLogger;
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
/*    */ @Plugin(name="property", category="Core", printObject=true)
/*    */ public final class Property
/*    */ {
/* 32 */   private static final Logger LOGGER = ;
/*    */   private final String name;
/*    */   private final String value;
/*    */   
/*    */   private Property(String paramString1, String paramString2)
/*    */   {
/* 38 */     this.name = paramString1;
/* 39 */     this.value = paramString2;
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */   public String getName()
/*    */   {
/* 47 */     return this.name;
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */   public String getValue()
/*    */   {
/* 55 */     return this.value;
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   @PluginFactory
/*    */   public static Property createProperty(@PluginAttribute("name") String paramString1, @PluginValue("value") String paramString2)
/*    */   {
/* 68 */     if (paramString1 == null) {
/* 69 */       LOGGER.error("Property key cannot be null");
/*    */     }
/* 71 */     return new Property(paramString1, paramString2);
/*    */   }
/*    */   
/*    */   public String toString()
/*    */   {
/* 76 */     return this.name + "=" + this.value;
/*    */   }
/*    */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\org\apache\logging\log4j\core\config\Property.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */
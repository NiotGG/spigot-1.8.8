/*    */ package org.apache.logging.log4j.core.helpers;
/*    */ 
/*    */ import org.apache.logging.log4j.core.config.plugins.Plugin;
/*    */ import org.apache.logging.log4j.core.config.plugins.PluginAttribute;
/*    */ import org.apache.logging.log4j.core.config.plugins.PluginFactory;
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
/*    */ @Plugin(name="KeyValuePair", category="Core", printObject=true)
/*    */ public class KeyValuePair
/*    */ {
/*    */   private final String key;
/*    */   private final String value;
/*    */   
/*    */   public KeyValuePair(String paramString1, String paramString2)
/*    */   {
/* 38 */     this.key = paramString1;
/* 39 */     this.value = paramString2;
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */   public String getKey()
/*    */   {
/* 47 */     return this.key;
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
/*    */   public String toString()
/*    */   {
/* 60 */     return this.key + "=" + this.value;
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   @PluginFactory
/*    */   public static KeyValuePair createPair(@PluginAttribute("key") String paramString1, @PluginAttribute("value") String paramString2)
/*    */   {
/* 74 */     return new KeyValuePair(paramString1, paramString2);
/*    */   }
/*    */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\org\apache\logging\log4j\core\helpers\KeyValuePair.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */
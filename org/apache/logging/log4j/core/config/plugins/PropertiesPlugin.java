/*    */ package org.apache.logging.log4j.core.config.plugins;
/*    */ 
/*    */ import java.util.HashMap;
/*    */ import java.util.Map;
/*    */ import org.apache.logging.log4j.core.config.Configuration;
/*    */ import org.apache.logging.log4j.core.config.Property;
/*    */ import org.apache.logging.log4j.core.lookup.Interpolator;
/*    */ import org.apache.logging.log4j.core.lookup.MapLookup;
/*    */ import org.apache.logging.log4j.core.lookup.StrLookup;
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
/*    */ @Plugin(name="properties", category="Core", printObject=true)
/*    */ public final class PropertiesPlugin
/*    */ {
/*    */   @PluginFactory
/*    */   public static StrLookup configureSubstitutor(@PluginElement("Properties") Property[] paramArrayOfProperty, @PluginConfiguration Configuration paramConfiguration)
/*    */   {
/* 46 */     if (paramArrayOfProperty == null) {
/* 47 */       return new Interpolator(null);
/*    */     }
/* 49 */     HashMap localHashMap = new HashMap(paramConfiguration.getProperties());
/*    */     
/* 51 */     for (Property localProperty : paramArrayOfProperty) {
/* 52 */       localHashMap.put(localProperty.getName(), localProperty.getValue());
/*    */     }
/*    */     
/* 55 */     return new Interpolator(new MapLookup(localHashMap));
/*    */   }
/*    */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\org\apache\logging\log4j\core\config\plugins\PropertiesPlugin.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */
/*    */ package org.apache.logging.log4j.core.appender.routing;
/*    */ 
/*    */ import org.apache.logging.log4j.Logger;
/*    */ import org.apache.logging.log4j.core.config.plugins.Plugin;
/*    */ import org.apache.logging.log4j.core.config.plugins.PluginAttribute;
/*    */ import org.apache.logging.log4j.core.config.plugins.PluginElement;
/*    */ import org.apache.logging.log4j.core.config.plugins.PluginFactory;
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
/*    */ @Plugin(name="Routes", category="Core", printObject=true)
/*    */ public final class Routes
/*    */ {
/* 32 */   private static final Logger LOGGER = ;
/*    */   private final String pattern;
/*    */   private final Route[] routes;
/*    */   
/*    */   private Routes(String paramString, Route... paramVarArgs)
/*    */   {
/* 38 */     this.pattern = paramString;
/* 39 */     this.routes = paramVarArgs;
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */   public String getPattern()
/*    */   {
/* 47 */     return this.pattern;
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */   public Route[] getRoutes()
/*    */   {
/* 55 */     return this.routes;
/*    */   }
/*    */   
/*    */   public String toString()
/*    */   {
/* 60 */     StringBuilder localStringBuilder = new StringBuilder("{");
/* 61 */     int i = 1;
/* 62 */     for (Route localRoute : this.routes) {
/* 63 */       if (i == 0) {
/* 64 */         localStringBuilder.append(",");
/*    */       }
/* 66 */       i = 0;
/* 67 */       localStringBuilder.append(localRoute.toString());
/*    */     }
/* 69 */     localStringBuilder.append("}");
/* 70 */     return localStringBuilder.toString();
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
/*    */   public static Routes createRoutes(@PluginAttribute("pattern") String paramString, @PluginElement("Routes") Route... paramVarArgs)
/*    */   {
/* 84 */     if (paramString == null) {
/* 85 */       LOGGER.error("A pattern is required");
/* 86 */       return null;
/*    */     }
/* 88 */     if ((paramVarArgs == null) || (paramVarArgs.length == 0)) {
/* 89 */       LOGGER.error("No routes configured");
/* 90 */       return null;
/*    */     }
/* 92 */     return new Routes(paramString, paramVarArgs);
/*    */   }
/*    */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\org\apache\logging\log4j\core\appender\routing\Routes.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */
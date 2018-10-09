/*    */ package org.apache.logging.log4j.core.appender;
/*    */ 
/*    */ import org.apache.logging.log4j.Logger;
/*    */ import org.apache.logging.log4j.core.config.AppenderRef;
/*    */ import org.apache.logging.log4j.core.config.plugins.Plugin;
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
/*    */ @Plugin(name="failovers", category="Core")
/*    */ public final class FailoversPlugin
/*    */ {
/* 32 */   private static final Logger LOGGER = ;
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
/*    */   @PluginFactory
/*    */   public static String[] createFailovers(@PluginElement("AppenderRef") AppenderRef... paramVarArgs)
/*    */   {
/* 48 */     if (paramVarArgs == null) {
/* 49 */       LOGGER.error("failovers must contain an appender reference");
/* 50 */       return null;
/*    */     }
/* 52 */     String[] arrayOfString = new String[paramVarArgs.length];
/* 53 */     for (int i = 0; i < paramVarArgs.length; i++) {
/* 54 */       arrayOfString[i] = paramVarArgs[i].getRef();
/*    */     }
/* 56 */     return arrayOfString;
/*    */   }
/*    */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\org\apache\logging\log4j\core\appender\FailoversPlugin.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */
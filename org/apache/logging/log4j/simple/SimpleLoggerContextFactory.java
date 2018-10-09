/*    */ package org.apache.logging.log4j.simple;
/*    */ 
/*    */ import java.net.URI;
/*    */ import org.apache.logging.log4j.spi.LoggerContext;
/*    */ import org.apache.logging.log4j.spi.LoggerContextFactory;
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
/*    */ public class SimpleLoggerContextFactory
/*    */   implements LoggerContextFactory
/*    */ {
/* 29 */   private static LoggerContext context = new SimpleLoggerContext();
/*    */   
/*    */   public LoggerContext getContext(String paramString, ClassLoader paramClassLoader, boolean paramBoolean)
/*    */   {
/* 33 */     return context;
/*    */   }
/*    */   
/*    */ 
/*    */   public LoggerContext getContext(String paramString, ClassLoader paramClassLoader, boolean paramBoolean, URI paramURI)
/*    */   {
/* 39 */     return context;
/*    */   }
/*    */   
/*    */   public void removeContext(LoggerContext paramLoggerContext) {}
/*    */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\org\apache\logging\log4j\simple\SimpleLoggerContextFactory.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */
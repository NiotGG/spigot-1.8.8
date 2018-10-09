/*    */ package org.apache.logging.log4j.core.selector;
/*    */ 
/*    */ import java.net.URI;
/*    */ import java.util.ArrayList;
/*    */ import java.util.Collections;
/*    */ import java.util.List;
/*    */ import org.apache.logging.log4j.core.LoggerContext;
/*    */ import org.apache.logging.log4j.core.impl.ContextAnchor;
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
/*    */ public class BasicContextSelector
/*    */   implements ContextSelector
/*    */ {
/* 32 */   private static final LoggerContext CONTEXT = new LoggerContext("Default");
/*    */   
/*    */ 
/*    */   public LoggerContext getContext(String paramString, ClassLoader paramClassLoader, boolean paramBoolean)
/*    */   {
/* 37 */     LoggerContext localLoggerContext = (LoggerContext)ContextAnchor.THREAD_CONTEXT.get();
/* 38 */     return localLoggerContext != null ? localLoggerContext : CONTEXT;
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */   public LoggerContext getContext(String paramString, ClassLoader paramClassLoader, boolean paramBoolean, URI paramURI)
/*    */   {
/* 46 */     LoggerContext localLoggerContext = (LoggerContext)ContextAnchor.THREAD_CONTEXT.get();
/* 47 */     return localLoggerContext != null ? localLoggerContext : CONTEXT;
/*    */   }
/*    */   
/*    */   public LoggerContext locateContext(String paramString1, String paramString2) {
/* 51 */     return CONTEXT;
/*    */   }
/*    */   
/*    */ 
/*    */   public void removeContext(LoggerContext paramLoggerContext) {}
/*    */   
/*    */ 
/*    */   public List<LoggerContext> getLoggerContexts()
/*    */   {
/* 60 */     ArrayList localArrayList = new ArrayList();
/* 61 */     localArrayList.add(CONTEXT);
/* 62 */     return Collections.unmodifiableList(localArrayList);
/*    */   }
/*    */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\org\apache\logging\log4j\core\selector\BasicContextSelector.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */
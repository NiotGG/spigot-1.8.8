/*    */ package org.apache.logging.log4j.core.async;
/*    */ 
/*    */ import java.net.URI;
/*    */ import java.util.ArrayList;
/*    */ import java.util.Collections;
/*    */ import java.util.List;
/*    */ import org.apache.logging.log4j.core.LoggerContext;
/*    */ import org.apache.logging.log4j.core.selector.ContextSelector;
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
/*    */ public class AsyncLoggerContextSelector
/*    */   implements ContextSelector
/*    */ {
/* 32 */   private static final AsyncLoggerContext CONTEXT = new AsyncLoggerContext("AsyncLoggerContext");
/*    */   
/*    */   public LoggerContext getContext(String paramString, ClassLoader paramClassLoader, boolean paramBoolean)
/*    */   {
/* 36 */     return CONTEXT;
/*    */   }
/*    */   
/*    */   public List<LoggerContext> getLoggerContexts()
/*    */   {
/* 41 */     ArrayList localArrayList = new ArrayList();
/* 42 */     localArrayList.add(CONTEXT);
/* 43 */     return Collections.unmodifiableList(localArrayList);
/*    */   }
/*    */   
/*    */   public LoggerContext getContext(String paramString, ClassLoader paramClassLoader, boolean paramBoolean, URI paramURI)
/*    */   {
/* 48 */     return CONTEXT;
/*    */   }
/*    */   
/*    */   public void removeContext(LoggerContext paramLoggerContext) {}
/*    */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\org\apache\logging\log4j\core\async\AsyncLoggerContextSelector.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */
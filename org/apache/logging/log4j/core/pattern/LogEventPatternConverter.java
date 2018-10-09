/*    */ package org.apache.logging.log4j.core.pattern;
/*    */ 
/*    */ import org.apache.logging.log4j.core.LogEvent;
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
/*    */ public abstract class LogEventPatternConverter
/*    */   extends AbstractPatternConverter
/*    */ {
/*    */   protected LogEventPatternConverter(String paramString1, String paramString2)
/*    */   {
/* 34 */     super(paramString1, paramString2);
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   public abstract void format(LogEvent paramLogEvent, StringBuilder paramStringBuilder);
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   public void format(Object paramObject, StringBuilder paramStringBuilder)
/*    */   {
/* 50 */     if ((paramObject instanceof LogEvent)) {
/* 51 */       format((LogEvent)paramObject, paramStringBuilder);
/*    */     }
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
/*    */   public boolean handlesThrowable()
/*    */   {
/* 65 */     return false;
/*    */   }
/*    */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\org\apache\logging\log4j\core\pattern\LogEventPatternConverter.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */
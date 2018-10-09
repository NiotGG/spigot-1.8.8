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
/*    */ public class PatternFormatter
/*    */ {
/*    */   private final LogEventPatternConverter converter;
/*    */   private final FormattingInfo field;
/*    */   
/*    */   public PatternFormatter(LogEventPatternConverter paramLogEventPatternConverter, FormattingInfo paramFormattingInfo)
/*    */   {
/* 30 */     this.converter = paramLogEventPatternConverter;
/* 31 */     this.field = paramFormattingInfo;
/*    */   }
/*    */   
/*    */   public void format(LogEvent paramLogEvent, StringBuilder paramStringBuilder) {
/* 35 */     int i = paramStringBuilder.length();
/* 36 */     this.converter.format(paramLogEvent, paramStringBuilder);
/* 37 */     this.field.format(i, paramStringBuilder);
/*    */   }
/*    */   
/*    */   public LogEventPatternConverter getConverter() {
/* 41 */     return this.converter;
/*    */   }
/*    */   
/*    */   public FormattingInfo getFormattingInfo() {
/* 45 */     return this.field;
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
/* 58 */     return this.converter.handlesThrowable();
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   public String toString()
/*    */   {
/* 68 */     StringBuilder localStringBuilder = new StringBuilder();
/* 69 */     localStringBuilder.append(super.toString());
/* 70 */     localStringBuilder.append("[converter=");
/* 71 */     localStringBuilder.append(this.converter);
/* 72 */     localStringBuilder.append(", field=");
/* 73 */     localStringBuilder.append(this.field);
/* 74 */     localStringBuilder.append("]");
/* 75 */     return localStringBuilder.toString();
/*    */   }
/*    */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\org\apache\logging\log4j\core\pattern\PatternFormatter.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */
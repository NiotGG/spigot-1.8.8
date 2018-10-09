/*    */ package org.apache.logging.log4j.core.pattern;
/*    */ 
/*    */ import org.apache.logging.log4j.core.LogEvent;
/*    */ import org.apache.logging.log4j.core.config.plugins.Plugin;
/*    */ import org.apache.logging.log4j.core.helpers.Constants;
/*    */ import org.apache.logging.log4j.core.impl.Log4jLogEvent;
/*    */ import org.apache.logging.log4j.core.impl.ThrowableFormatOptions;
/*    */ import org.apache.logging.log4j.core.impl.ThrowableProxy;
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
/*    */ @Plugin(name="ExtendedThrowablePatternConverter", category="Converter")
/*    */ @ConverterKeys({"xEx", "xThrowable", "xException"})
/*    */ public final class ExtendedThrowablePatternConverter
/*    */   extends ThrowablePatternConverter
/*    */ {
/*    */   private ExtendedThrowablePatternConverter(String[] paramArrayOfString)
/*    */   {
/* 43 */     super("ExtendedThrowable", "throwable", paramArrayOfString);
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   public static ExtendedThrowablePatternConverter newInstance(String[] paramArrayOfString)
/*    */   {
/* 54 */     return new ExtendedThrowablePatternConverter(paramArrayOfString);
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */   public void format(LogEvent paramLogEvent, StringBuilder paramStringBuilder)
/*    */   {
/* 62 */     ThrowableProxy localThrowableProxy = null;
/* 63 */     if ((paramLogEvent instanceof Log4jLogEvent)) {
/* 64 */       localThrowableProxy = ((Log4jLogEvent)paramLogEvent).getThrownProxy();
/*    */     }
/* 66 */     Throwable localThrowable = paramLogEvent.getThrown();
/* 67 */     if ((localThrowable != null) && (this.options.anyLines())) {
/* 68 */       if (localThrowableProxy == null) {
/* 69 */         super.format(paramLogEvent, paramStringBuilder);
/* 70 */         return;
/*    */       }
/* 72 */       String str = localThrowableProxy.getExtendedStackTrace(this.options.getPackages());
/* 73 */       int i = paramStringBuilder.length();
/* 74 */       if ((i > 0) && (!Character.isWhitespace(paramStringBuilder.charAt(i - 1)))) {
/* 75 */         paramStringBuilder.append(" ");
/*    */       }
/* 77 */       if ((!this.options.allLines()) || (!Constants.LINE_SEP.equals(this.options.getSeparator()))) {
/* 78 */         StringBuilder localStringBuilder = new StringBuilder();
/* 79 */         String[] arrayOfString = str.split(Constants.LINE_SEP);
/* 80 */         int j = this.options.minLines(arrayOfString.length) - 1;
/* 81 */         for (int k = 0; k <= j; k++) {
/* 82 */           localStringBuilder.append(arrayOfString[k]);
/* 83 */           if (k < j) {
/* 84 */             localStringBuilder.append(this.options.getSeparator());
/*    */           }
/*    */         }
/* 87 */         paramStringBuilder.append(localStringBuilder.toString());
/*    */       }
/*    */       else {
/* 90 */         paramStringBuilder.append(str);
/*    */       }
/*    */     }
/*    */   }
/*    */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\org\apache\logging\log4j\core\pattern\ExtendedThrowablePatternConverter.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */
/*     */ package org.apache.logging.log4j.core.pattern;
/*     */ 
/*     */ import java.io.PrintWriter;
/*     */ import java.io.StringWriter;
/*     */ import org.apache.logging.log4j.core.LogEvent;
/*     */ import org.apache.logging.log4j.core.config.plugins.Plugin;
/*     */ import org.apache.logging.log4j.core.helpers.Constants;
/*     */ import org.apache.logging.log4j.core.impl.ThrowableFormatOptions;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ @Plugin(name="ThrowablePatternConverter", category="Converter")
/*     */ @ConverterKeys({"ex", "throwable", "exception"})
/*     */ public class ThrowablePatternConverter
/*     */   extends LogEventPatternConverter
/*     */ {
/*     */   private String rawOption;
/*     */   protected final ThrowableFormatOptions options;
/*     */   
/*     */   protected ThrowablePatternConverter(String paramString1, String paramString2, String[] paramArrayOfString)
/*     */   {
/*  51 */     super(paramString1, paramString2);
/*  52 */     this.options = ThrowableFormatOptions.newInstance(paramArrayOfString);
/*  53 */     if ((paramArrayOfString != null) && (paramArrayOfString.length > 0)) {
/*  54 */       this.rawOption = paramArrayOfString[0];
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public static ThrowablePatternConverter newInstance(String[] paramArrayOfString)
/*     */   {
/*  66 */     return new ThrowablePatternConverter("Throwable", "throwable", paramArrayOfString);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public void format(LogEvent paramLogEvent, StringBuilder paramStringBuilder)
/*     */   {
/*  74 */     Throwable localThrowable = paramLogEvent.getThrown();
/*     */     
/*  76 */     if (isSubShortOption()) {
/*  77 */       formatSubShortOption(localThrowable, paramStringBuilder);
/*     */     }
/*  79 */     else if ((localThrowable != null) && (this.options.anyLines())) {
/*  80 */       formatOption(localThrowable, paramStringBuilder);
/*     */     }
/*     */   }
/*     */   
/*     */   private boolean isSubShortOption() {
/*  85 */     return ("short.message".equalsIgnoreCase(this.rawOption)) || ("short.localizedMessage".equalsIgnoreCase(this.rawOption)) || ("short.fileName".equalsIgnoreCase(this.rawOption)) || ("short.lineNumber".equalsIgnoreCase(this.rawOption)) || ("short.methodName".equalsIgnoreCase(this.rawOption)) || ("short.className".equalsIgnoreCase(this.rawOption));
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   private void formatSubShortOption(Throwable paramThrowable, StringBuilder paramStringBuilder)
/*     */   {
/*  95 */     Object localObject = null;
/*     */     
/*     */ 
/*  98 */     if (paramThrowable != null) {
/*  99 */       StackTraceElement[] arrayOfStackTraceElement = paramThrowable.getStackTrace();
/* 100 */       if ((arrayOfStackTraceElement != null) && (arrayOfStackTraceElement.length > 0)) {
/* 101 */         localObject = arrayOfStackTraceElement[0];
/*     */       }
/*     */     }
/*     */     
/* 105 */     if ((paramThrowable != null) && (localObject != null)) {
/* 106 */       String str = "";
/*     */       
/* 108 */       if ("short.className".equalsIgnoreCase(this.rawOption)) {
/* 109 */         str = ((StackTraceElement)localObject).getClassName();
/*     */       }
/* 111 */       else if ("short.methodName".equalsIgnoreCase(this.rawOption)) {
/* 112 */         str = ((StackTraceElement)localObject).getMethodName();
/*     */       }
/* 114 */       else if ("short.lineNumber".equalsIgnoreCase(this.rawOption)) {
/* 115 */         str = String.valueOf(((StackTraceElement)localObject).getLineNumber());
/*     */       }
/* 117 */       else if ("short.message".equalsIgnoreCase(this.rawOption)) {
/* 118 */         str = paramThrowable.getMessage();
/*     */       }
/* 120 */       else if ("short.localizedMessage".equalsIgnoreCase(this.rawOption)) {
/* 121 */         str = paramThrowable.getLocalizedMessage();
/*     */       }
/* 123 */       else if ("short.fileName".equalsIgnoreCase(this.rawOption)) {
/* 124 */         str = ((StackTraceElement)localObject).getFileName();
/*     */       }
/*     */       
/* 127 */       int i = paramStringBuilder.length();
/* 128 */       if ((i > 0) && (!Character.isWhitespace(paramStringBuilder.charAt(i - 1)))) {
/* 129 */         paramStringBuilder.append(" ");
/*     */       }
/* 131 */       paramStringBuilder.append(str);
/*     */     }
/*     */   }
/*     */   
/*     */   private void formatOption(Throwable paramThrowable, StringBuilder paramStringBuilder) {
/* 136 */     StringWriter localStringWriter = new StringWriter();
/*     */     
/* 138 */     paramThrowable.printStackTrace(new PrintWriter(localStringWriter));
/* 139 */     int i = paramStringBuilder.length();
/* 140 */     if ((i > 0) && (!Character.isWhitespace(paramStringBuilder.charAt(i - 1)))) {
/* 141 */       paramStringBuilder.append(' ');
/*     */     }
/* 143 */     if ((!this.options.allLines()) || (!Constants.LINE_SEP.equals(this.options.getSeparator()))) {
/* 144 */       StringBuilder localStringBuilder = new StringBuilder();
/* 145 */       String[] arrayOfString = localStringWriter.toString().split(Constants.LINE_SEP);
/* 146 */       int j = this.options.minLines(arrayOfString.length) - 1;
/* 147 */       for (int k = 0; k <= j; k++) {
/* 148 */         localStringBuilder.append(arrayOfString[k]);
/* 149 */         if (k < j) {
/* 150 */           localStringBuilder.append(this.options.getSeparator());
/*     */         }
/*     */       }
/* 153 */       paramStringBuilder.append(localStringBuilder.toString());
/*     */     }
/*     */     else {
/* 156 */       paramStringBuilder.append(localStringWriter.toString());
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public boolean handlesThrowable()
/*     */   {
/* 167 */     return true;
/*     */   }
/*     */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\org\apache\logging\log4j\core\pattern\ThrowablePatternConverter.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */
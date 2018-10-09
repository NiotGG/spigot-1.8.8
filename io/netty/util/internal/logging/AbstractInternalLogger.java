/*     */ package io.netty.util.internal.logging;
/*     */ 
/*     */ import io.netty.util.internal.StringUtil;
/*     */ import java.io.ObjectStreamException;
/*     */ import java.io.Serializable;
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
/*     */ public abstract class AbstractInternalLogger
/*     */   implements InternalLogger, Serializable
/*     */ {
/*     */   private static final long serialVersionUID = -6382972526573193470L;
/*     */   private final String name;
/*     */   
/*     */   protected AbstractInternalLogger(String paramString)
/*     */   {
/*  38 */     if (paramString == null) {
/*  39 */       throw new NullPointerException("name");
/*     */     }
/*  41 */     this.name = paramString;
/*     */   }
/*     */   
/*     */   public String name()
/*     */   {
/*  46 */     return this.name;
/*     */   }
/*     */   
/*     */   public boolean isEnabled(InternalLogLevel paramInternalLogLevel)
/*     */   {
/*  51 */     switch (paramInternalLogLevel) {
/*     */     case TRACE: 
/*  53 */       return isTraceEnabled();
/*     */     case DEBUG: 
/*  55 */       return isDebugEnabled();
/*     */     case INFO: 
/*  57 */       return isInfoEnabled();
/*     */     case WARN: 
/*  59 */       return isWarnEnabled();
/*     */     case ERROR: 
/*  61 */       return isErrorEnabled();
/*     */     }
/*  63 */     throw new Error();
/*     */   }
/*     */   
/*     */ 
/*     */   public void log(InternalLogLevel paramInternalLogLevel, String paramString, Throwable paramThrowable)
/*     */   {
/*  69 */     switch (paramInternalLogLevel) {
/*     */     case TRACE: 
/*  71 */       trace(paramString, paramThrowable);
/*  72 */       break;
/*     */     case DEBUG: 
/*  74 */       debug(paramString, paramThrowable);
/*  75 */       break;
/*     */     case INFO: 
/*  77 */       info(paramString, paramThrowable);
/*  78 */       break;
/*     */     case WARN: 
/*  80 */       warn(paramString, paramThrowable);
/*  81 */       break;
/*     */     case ERROR: 
/*  83 */       error(paramString, paramThrowable);
/*  84 */       break;
/*     */     default: 
/*  86 */       throw new Error();
/*     */     }
/*     */   }
/*     */   
/*     */   public void log(InternalLogLevel paramInternalLogLevel, String paramString)
/*     */   {
/*  92 */     switch (paramInternalLogLevel) {
/*     */     case TRACE: 
/*  94 */       trace(paramString);
/*  95 */       break;
/*     */     case DEBUG: 
/*  97 */       debug(paramString);
/*  98 */       break;
/*     */     case INFO: 
/* 100 */       info(paramString);
/* 101 */       break;
/*     */     case WARN: 
/* 103 */       warn(paramString);
/* 104 */       break;
/*     */     case ERROR: 
/* 106 */       error(paramString);
/* 107 */       break;
/*     */     default: 
/* 109 */       throw new Error();
/*     */     }
/*     */   }
/*     */   
/*     */   public void log(InternalLogLevel paramInternalLogLevel, String paramString, Object paramObject)
/*     */   {
/* 115 */     switch (paramInternalLogLevel) {
/*     */     case TRACE: 
/* 117 */       trace(paramString, paramObject);
/* 118 */       break;
/*     */     case DEBUG: 
/* 120 */       debug(paramString, paramObject);
/* 121 */       break;
/*     */     case INFO: 
/* 123 */       info(paramString, paramObject);
/* 124 */       break;
/*     */     case WARN: 
/* 126 */       warn(paramString, paramObject);
/* 127 */       break;
/*     */     case ERROR: 
/* 129 */       error(paramString, paramObject);
/* 130 */       break;
/*     */     default: 
/* 132 */       throw new Error();
/*     */     }
/*     */   }
/*     */   
/*     */   public void log(InternalLogLevel paramInternalLogLevel, String paramString, Object paramObject1, Object paramObject2)
/*     */   {
/* 138 */     switch (paramInternalLogLevel) {
/*     */     case TRACE: 
/* 140 */       trace(paramString, paramObject1, paramObject2);
/* 141 */       break;
/*     */     case DEBUG: 
/* 143 */       debug(paramString, paramObject1, paramObject2);
/* 144 */       break;
/*     */     case INFO: 
/* 146 */       info(paramString, paramObject1, paramObject2);
/* 147 */       break;
/*     */     case WARN: 
/* 149 */       warn(paramString, paramObject1, paramObject2);
/* 150 */       break;
/*     */     case ERROR: 
/* 152 */       error(paramString, paramObject1, paramObject2);
/* 153 */       break;
/*     */     default: 
/* 155 */       throw new Error();
/*     */     }
/*     */   }
/*     */   
/*     */   public void log(InternalLogLevel paramInternalLogLevel, String paramString, Object... paramVarArgs)
/*     */   {
/* 161 */     switch (paramInternalLogLevel) {
/*     */     case TRACE: 
/* 163 */       trace(paramString, paramVarArgs);
/* 164 */       break;
/*     */     case DEBUG: 
/* 166 */       debug(paramString, paramVarArgs);
/* 167 */       break;
/*     */     case INFO: 
/* 169 */       info(paramString, paramVarArgs);
/* 170 */       break;
/*     */     case WARN: 
/* 172 */       warn(paramString, paramVarArgs);
/* 173 */       break;
/*     */     case ERROR: 
/* 175 */       error(paramString, paramVarArgs);
/* 176 */       break;
/*     */     default: 
/* 178 */       throw new Error();
/*     */     }
/*     */   }
/*     */   
/*     */   protected Object readResolve() throws ObjectStreamException {
/* 183 */     return InternalLoggerFactory.getInstance(name());
/*     */   }
/*     */   
/*     */   public String toString()
/*     */   {
/* 188 */     return StringUtil.simpleClassName(this) + '(' + name() + ')';
/*     */   }
/*     */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\io\netty\util\internal\logging\AbstractInternalLogger.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */
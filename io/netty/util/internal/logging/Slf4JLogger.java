/*     */ package io.netty.util.internal.logging;
/*     */ 
/*     */ import org.slf4j.Logger;
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
/*     */ class Slf4JLogger
/*     */   extends AbstractInternalLogger
/*     */ {
/*     */   private static final long serialVersionUID = 108038972685130825L;
/*     */   private final transient Logger logger;
/*     */   
/*     */   Slf4JLogger(Logger paramLogger)
/*     */   {
/*  30 */     super(paramLogger.getName());
/*  31 */     this.logger = paramLogger;
/*     */   }
/*     */   
/*     */   public boolean isTraceEnabled()
/*     */   {
/*  36 */     return this.logger.isTraceEnabled();
/*     */   }
/*     */   
/*     */   public void trace(String paramString)
/*     */   {
/*  41 */     this.logger.trace(paramString);
/*     */   }
/*     */   
/*     */   public void trace(String paramString, Object paramObject)
/*     */   {
/*  46 */     this.logger.trace(paramString, paramObject);
/*     */   }
/*     */   
/*     */   public void trace(String paramString, Object paramObject1, Object paramObject2)
/*     */   {
/*  51 */     this.logger.trace(paramString, paramObject1, paramObject2);
/*     */   }
/*     */   
/*     */   public void trace(String paramString, Object... paramVarArgs)
/*     */   {
/*  56 */     this.logger.trace(paramString, paramVarArgs);
/*     */   }
/*     */   
/*     */   public void trace(String paramString, Throwable paramThrowable)
/*     */   {
/*  61 */     this.logger.trace(paramString, paramThrowable);
/*     */   }
/*     */   
/*     */   public boolean isDebugEnabled()
/*     */   {
/*  66 */     return this.logger.isDebugEnabled();
/*     */   }
/*     */   
/*     */   public void debug(String paramString)
/*     */   {
/*  71 */     this.logger.debug(paramString);
/*     */   }
/*     */   
/*     */   public void debug(String paramString, Object paramObject)
/*     */   {
/*  76 */     this.logger.debug(paramString, paramObject);
/*     */   }
/*     */   
/*     */   public void debug(String paramString, Object paramObject1, Object paramObject2)
/*     */   {
/*  81 */     this.logger.debug(paramString, paramObject1, paramObject2);
/*     */   }
/*     */   
/*     */   public void debug(String paramString, Object... paramVarArgs)
/*     */   {
/*  86 */     this.logger.debug(paramString, paramVarArgs);
/*     */   }
/*     */   
/*     */   public void debug(String paramString, Throwable paramThrowable)
/*     */   {
/*  91 */     this.logger.debug(paramString, paramThrowable);
/*     */   }
/*     */   
/*     */   public boolean isInfoEnabled()
/*     */   {
/*  96 */     return this.logger.isInfoEnabled();
/*     */   }
/*     */   
/*     */   public void info(String paramString)
/*     */   {
/* 101 */     this.logger.info(paramString);
/*     */   }
/*     */   
/*     */   public void info(String paramString, Object paramObject)
/*     */   {
/* 106 */     this.logger.info(paramString, paramObject);
/*     */   }
/*     */   
/*     */   public void info(String paramString, Object paramObject1, Object paramObject2)
/*     */   {
/* 111 */     this.logger.info(paramString, paramObject1, paramObject2);
/*     */   }
/*     */   
/*     */   public void info(String paramString, Object... paramVarArgs)
/*     */   {
/* 116 */     this.logger.info(paramString, paramVarArgs);
/*     */   }
/*     */   
/*     */   public void info(String paramString, Throwable paramThrowable)
/*     */   {
/* 121 */     this.logger.info(paramString, paramThrowable);
/*     */   }
/*     */   
/*     */   public boolean isWarnEnabled()
/*     */   {
/* 126 */     return this.logger.isWarnEnabled();
/*     */   }
/*     */   
/*     */   public void warn(String paramString)
/*     */   {
/* 131 */     this.logger.warn(paramString);
/*     */   }
/*     */   
/*     */   public void warn(String paramString, Object paramObject)
/*     */   {
/* 136 */     this.logger.warn(paramString, paramObject);
/*     */   }
/*     */   
/*     */   public void warn(String paramString, Object... paramVarArgs)
/*     */   {
/* 141 */     this.logger.warn(paramString, paramVarArgs);
/*     */   }
/*     */   
/*     */   public void warn(String paramString, Object paramObject1, Object paramObject2)
/*     */   {
/* 146 */     this.logger.warn(paramString, paramObject1, paramObject2);
/*     */   }
/*     */   
/*     */   public void warn(String paramString, Throwable paramThrowable)
/*     */   {
/* 151 */     this.logger.warn(paramString, paramThrowable);
/*     */   }
/*     */   
/*     */   public boolean isErrorEnabled()
/*     */   {
/* 156 */     return this.logger.isErrorEnabled();
/*     */   }
/*     */   
/*     */   public void error(String paramString)
/*     */   {
/* 161 */     this.logger.error(paramString);
/*     */   }
/*     */   
/*     */   public void error(String paramString, Object paramObject)
/*     */   {
/* 166 */     this.logger.error(paramString, paramObject);
/*     */   }
/*     */   
/*     */   public void error(String paramString, Object paramObject1, Object paramObject2)
/*     */   {
/* 171 */     this.logger.error(paramString, paramObject1, paramObject2);
/*     */   }
/*     */   
/*     */   public void error(String paramString, Object... paramVarArgs)
/*     */   {
/* 176 */     this.logger.error(paramString, paramVarArgs);
/*     */   }
/*     */   
/*     */   public void error(String paramString, Throwable paramThrowable)
/*     */   {
/* 181 */     this.logger.error(paramString, paramThrowable);
/*     */   }
/*     */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\io\netty\util\internal\logging\Slf4JLogger.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */
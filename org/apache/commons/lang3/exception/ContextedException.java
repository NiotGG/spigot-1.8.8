/*     */ package org.apache.commons.lang3.exception;
/*     */ 
/*     */ import java.util.List;
/*     */ import java.util.Set;
/*     */ import org.apache.commons.lang3.tuple.Pair;
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
/*     */ public class ContextedException
/*     */   extends Exception
/*     */   implements ExceptionContext
/*     */ {
/*     */   private static final long serialVersionUID = 20110706L;
/*     */   private final ExceptionContext exceptionContext;
/*     */   
/*     */   public ContextedException()
/*     */   {
/* 101 */     this.exceptionContext = new DefaultExceptionContext();
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public ContextedException(String paramString)
/*     */   {
/* 112 */     super(paramString);
/* 113 */     this.exceptionContext = new DefaultExceptionContext();
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public ContextedException(Throwable paramThrowable)
/*     */   {
/* 124 */     super(paramThrowable);
/* 125 */     this.exceptionContext = new DefaultExceptionContext();
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public ContextedException(String paramString, Throwable paramThrowable)
/*     */   {
/* 137 */     super(paramString, paramThrowable);
/* 138 */     this.exceptionContext = new DefaultExceptionContext();
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public ContextedException(String paramString, Throwable paramThrowable, ExceptionContext paramExceptionContext)
/*     */   {
/* 149 */     super(paramString, paramThrowable);
/* 150 */     if (paramExceptionContext == null) {
/* 151 */       paramExceptionContext = new DefaultExceptionContext();
/*     */     }
/* 153 */     this.exceptionContext = paramExceptionContext;
/*     */   }
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
/*     */   public ContextedException addContextValue(String paramString, Object paramObject)
/*     */   {
/* 172 */     this.exceptionContext.addContextValue(paramString, paramObject);
/* 173 */     return this;
/*     */   }
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
/*     */   public ContextedException setContextValue(String paramString, Object paramObject)
/*     */   {
/* 191 */     this.exceptionContext.setContextValue(paramString, paramObject);
/* 192 */     return this;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public List<Object> getContextValues(String paramString)
/*     */   {
/* 200 */     return this.exceptionContext.getContextValues(paramString);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public Object getFirstContextValue(String paramString)
/*     */   {
/* 208 */     return this.exceptionContext.getFirstContextValue(paramString);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public List<Pair<String, Object>> getContextEntries()
/*     */   {
/* 216 */     return this.exceptionContext.getContextEntries();
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public Set<String> getContextLabels()
/*     */   {
/* 224 */     return this.exceptionContext.getContextLabels();
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public String getMessage()
/*     */   {
/* 235 */     return getFormattedExceptionMessage(super.getMessage());
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public String getRawMessage()
/*     */   {
/* 246 */     return super.getMessage();
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public String getFormattedExceptionMessage(String paramString)
/*     */   {
/* 254 */     return this.exceptionContext.getFormattedExceptionMessage(paramString);
/*     */   }
/*     */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\org\apache\commons\lang3\exception\ContextedException.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */
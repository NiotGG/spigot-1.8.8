/*     */ package org.apache.commons.lang3;
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
/*     */ public class NotImplementedException
/*     */   extends UnsupportedOperationException
/*     */ {
/*     */   private static final long serialVersionUID = 20131021L;
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
/*     */   private final String code;
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
/*     */   public NotImplementedException(String paramString)
/*     */   {
/*  57 */     this(paramString, (String)null);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public NotImplementedException(Throwable paramThrowable)
/*     */   {
/*  67 */     this(paramThrowable, null);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public NotImplementedException(String paramString, Throwable paramThrowable)
/*     */   {
/*  78 */     this(paramString, paramThrowable, null);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public NotImplementedException(String paramString1, String paramString2)
/*     */   {
/*  89 */     super(paramString1);
/*  90 */     this.code = paramString2;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public NotImplementedException(Throwable paramThrowable, String paramString)
/*     */   {
/* 101 */     super(paramThrowable);
/* 102 */     this.code = paramString;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public NotImplementedException(String paramString1, Throwable paramThrowable, String paramString2)
/*     */   {
/* 114 */     super(paramString1, paramThrowable);
/* 115 */     this.code = paramString2;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public String getCode()
/*     */   {
/* 126 */     return this.code;
/*     */   }
/*     */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\org\apache\commons\lang3\NotImplementedException.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */
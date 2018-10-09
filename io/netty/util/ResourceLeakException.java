/*    */ package io.netty.util;
/*    */ 
/*    */ import java.util.Arrays;
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
/*    */ @Deprecated
/*    */ public class ResourceLeakException
/*    */   extends RuntimeException
/*    */ {
/*    */   private static final long serialVersionUID = 7186453858343358280L;
/*    */   private final StackTraceElement[] cachedStackTrace;
/*    */   
/*    */   public ResourceLeakException()
/*    */   {
/* 32 */     this.cachedStackTrace = getStackTrace();
/*    */   }
/*    */   
/*    */   public ResourceLeakException(String paramString) {
/* 36 */     super(paramString);
/* 37 */     this.cachedStackTrace = getStackTrace();
/*    */   }
/*    */   
/*    */   public ResourceLeakException(String paramString, Throwable paramThrowable) {
/* 41 */     super(paramString, paramThrowable);
/* 42 */     this.cachedStackTrace = getStackTrace();
/*    */   }
/*    */   
/*    */   public ResourceLeakException(Throwable paramThrowable) {
/* 46 */     super(paramThrowable);
/* 47 */     this.cachedStackTrace = getStackTrace();
/*    */   }
/*    */   
/*    */   public int hashCode()
/*    */   {
/* 52 */     StackTraceElement[] arrayOfStackTraceElement1 = this.cachedStackTrace;
/* 53 */     int i = 0;
/* 54 */     for (StackTraceElement localStackTraceElement : arrayOfStackTraceElement1) {
/* 55 */       i = i * 31 + localStackTraceElement.hashCode();
/*    */     }
/* 57 */     return i;
/*    */   }
/*    */   
/*    */   public boolean equals(Object paramObject)
/*    */   {
/* 62 */     if (!(paramObject instanceof ResourceLeakException)) {
/* 63 */       return false;
/*    */     }
/* 65 */     if (paramObject == this) {
/* 66 */       return true;
/*    */     }
/*    */     
/* 69 */     return Arrays.equals(this.cachedStackTrace, ((ResourceLeakException)paramObject).cachedStackTrace);
/*    */   }
/*    */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\io\netty\util\ResourceLeakException.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */
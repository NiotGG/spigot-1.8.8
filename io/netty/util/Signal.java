/*    */ package io.netty.util;
/*    */ 
/*    */ import io.netty.util.internal.PlatformDependent;
/*    */ import java.util.concurrent.ConcurrentMap;
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
/*    */ public final class Signal
/*    */   extends Error
/*    */ {
/*    */   private static final long serialVersionUID = -221145131122459977L;
/* 31 */   private static final ConcurrentMap<String, Boolean> map = ;
/*    */   
/*    */ 
/*    */ 
/*    */   private final UniqueName uname;
/*    */   
/*    */ 
/*    */ 
/*    */   public static Signal valueOf(String paramString)
/*    */   {
/* 41 */     return new Signal(paramString);
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */   @Deprecated
/*    */   public Signal(String paramString)
/*    */   {
/* 49 */     super(paramString);
/* 50 */     this.uname = new UniqueName(map, paramString, new Object[0]);
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */   public void expect(Signal paramSignal)
/*    */   {
/* 58 */     if (this != paramSignal) {
/* 59 */       throw new IllegalStateException("unexpected signal: " + paramSignal);
/*    */     }
/*    */   }
/*    */   
/*    */   public Throwable initCause(Throwable paramThrowable)
/*    */   {
/* 65 */     return this;
/*    */   }
/*    */   
/*    */   public Throwable fillInStackTrace()
/*    */   {
/* 70 */     return this;
/*    */   }
/*    */   
/*    */   public String toString()
/*    */   {
/* 75 */     return this.uname.name();
/*    */   }
/*    */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\io\netty\util\Signal.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */
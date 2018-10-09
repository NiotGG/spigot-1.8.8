/*    */ package io.netty.handler.ssl;
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
/*    */ public final class SslHandshakeCompletionEvent
/*    */ {
/* 25 */   public static final SslHandshakeCompletionEvent SUCCESS = new SslHandshakeCompletionEvent();
/*    */   
/*    */ 
/*    */   private final Throwable cause;
/*    */   
/*    */ 
/*    */   private SslHandshakeCompletionEvent()
/*    */   {
/* 33 */     this.cause = null;
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */   public SslHandshakeCompletionEvent(Throwable paramThrowable)
/*    */   {
/* 41 */     if (paramThrowable == null) {
/* 42 */       throw new NullPointerException("cause");
/*    */     }
/* 44 */     this.cause = paramThrowable;
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */   public boolean isSuccess()
/*    */   {
/* 51 */     return this.cause == null;
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */   public Throwable cause()
/*    */   {
/* 59 */     return this.cause;
/*    */   }
/*    */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\io\netty\handler\ssl\SslHandshakeCompletionEvent.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */
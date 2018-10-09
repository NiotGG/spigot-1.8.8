/*    */ package io.netty.handler.codec;
/*    */ 
/*    */ import io.netty.util.Signal;
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
/*    */ public class DecoderResult
/*    */ {
/* 22 */   protected static final Signal SIGNAL_UNFINISHED = Signal.valueOf(DecoderResult.class.getName() + ".UNFINISHED");
/* 23 */   protected static final Signal SIGNAL_SUCCESS = Signal.valueOf(DecoderResult.class.getName() + ".SUCCESS");
/*    */   
/* 25 */   public static final DecoderResult UNFINISHED = new DecoderResult(SIGNAL_UNFINISHED);
/* 26 */   public static final DecoderResult SUCCESS = new DecoderResult(SIGNAL_SUCCESS);
/*    */   private final Throwable cause;
/*    */   
/* 29 */   public static DecoderResult failure(Throwable paramThrowable) { if (paramThrowable == null) {
/* 30 */       throw new NullPointerException("cause");
/*    */     }
/* 32 */     return new DecoderResult(paramThrowable);
/*    */   }
/*    */   
/*    */ 
/*    */   protected DecoderResult(Throwable paramThrowable)
/*    */   {
/* 38 */     if (paramThrowable == null) {
/* 39 */       throw new NullPointerException("cause");
/*    */     }
/* 41 */     this.cause = paramThrowable;
/*    */   }
/*    */   
/*    */   public boolean isFinished() {
/* 45 */     return this.cause != SIGNAL_UNFINISHED;
/*    */   }
/*    */   
/*    */   public boolean isSuccess() {
/* 49 */     return this.cause == SIGNAL_SUCCESS;
/*    */   }
/*    */   
/*    */   public boolean isFailure() {
/* 53 */     return (this.cause != SIGNAL_SUCCESS) && (this.cause != SIGNAL_UNFINISHED);
/*    */   }
/*    */   
/*    */   public Throwable cause() {
/* 57 */     if (isFailure()) {
/* 58 */       return this.cause;
/*    */     }
/* 60 */     return null;
/*    */   }
/*    */   
/*    */ 
/*    */   public String toString()
/*    */   {
/* 66 */     if (isFinished()) {
/* 67 */       if (isSuccess()) {
/* 68 */         return "success";
/*    */       }
/*    */       
/* 71 */       String str = cause().toString();
/* 72 */       StringBuilder localStringBuilder = new StringBuilder(str.length() + 17);
/* 73 */       localStringBuilder.append("failure(");
/* 74 */       localStringBuilder.append(str);
/* 75 */       localStringBuilder.append(')');
/*    */       
/* 77 */       return localStringBuilder.toString();
/*    */     }
/* 79 */     return "unfinished";
/*    */   }
/*    */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\io\netty\handler\codec\DecoderResult.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */
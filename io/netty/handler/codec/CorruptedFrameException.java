/*    */ package io.netty.handler.codec;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class CorruptedFrameException
/*    */   extends DecoderException
/*    */ {
/*    */   private static final long serialVersionUID = 3918052232492988408L;
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   public CorruptedFrameException() {}
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   public CorruptedFrameException(String paramString, Throwable paramThrowable)
/*    */   {
/* 36 */     super(paramString, paramThrowable);
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */   public CorruptedFrameException(String paramString)
/*    */   {
/* 43 */     super(paramString);
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */   public CorruptedFrameException(Throwable paramThrowable)
/*    */   {
/* 50 */     super(paramThrowable);
/*    */   }
/*    */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\io\netty\handler\codec\CorruptedFrameException.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */
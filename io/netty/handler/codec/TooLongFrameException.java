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
/*    */ public class TooLongFrameException
/*    */   extends DecoderException
/*    */ {
/*    */   private static final long serialVersionUID = -1995801950698951640L;
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   public TooLongFrameException() {}
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   public TooLongFrameException(String paramString, Throwable paramThrowable)
/*    */   {
/* 36 */     super(paramString, paramThrowable);
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */   public TooLongFrameException(String paramString)
/*    */   {
/* 43 */     super(paramString);
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */   public TooLongFrameException(Throwable paramThrowable)
/*    */   {
/* 50 */     super(paramThrowable);
/*    */   }
/*    */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\io\netty\handler\codec\TooLongFrameException.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */
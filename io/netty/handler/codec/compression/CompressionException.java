/*    */ package io.netty.handler.codec.compression;
/*    */ 
/*    */ import io.netty.handler.codec.EncoderException;
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
/*    */ 
/*    */ 
/*    */ public class CompressionException
/*    */   extends EncoderException
/*    */ {
/*    */   private static final long serialVersionUID = 5603413481274811897L;
/*    */   
/*    */   public CompressionException() {}
/*    */   
/*    */   public CompressionException(String paramString, Throwable paramThrowable)
/*    */   {
/* 37 */     super(paramString, paramThrowable);
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */   public CompressionException(String paramString)
/*    */   {
/* 44 */     super(paramString);
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */   public CompressionException(Throwable paramThrowable)
/*    */   {
/* 51 */     super(paramThrowable);
/*    */   }
/*    */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\io\netty\handler\codec\compression\CompressionException.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */
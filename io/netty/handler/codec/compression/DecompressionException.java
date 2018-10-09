/*    */ package io.netty.handler.codec.compression;
/*    */ 
/*    */ import io.netty.handler.codec.DecoderException;
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
/*    */ public class DecompressionException
/*    */   extends DecoderException
/*    */ {
/*    */   private static final long serialVersionUID = 3546272712208105199L;
/*    */   
/*    */   public DecompressionException() {}
/*    */   
/*    */   public DecompressionException(String paramString, Throwable paramThrowable)
/*    */   {
/* 37 */     super(paramString, paramThrowable);
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */   public DecompressionException(String paramString)
/*    */   {
/* 44 */     super(paramString);
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */   public DecompressionException(Throwable paramThrowable)
/*    */   {
/* 51 */     super(paramThrowable);
/*    */   }
/*    */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\io\netty\handler\codec\compression\DecompressionException.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */
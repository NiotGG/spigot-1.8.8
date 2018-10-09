/*    */ package io.netty.handler.codec.http;
/*    */ 
/*    */ import io.netty.handler.codec.DecoderResult;
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
/*    */ public class DefaultHttpObject
/*    */   implements HttpObject
/*    */ {
/* 22 */   private DecoderResult decoderResult = DecoderResult.SUCCESS;
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   public DecoderResult getDecoderResult()
/*    */   {
/* 30 */     return this.decoderResult;
/*    */   }
/*    */   
/*    */   public void setDecoderResult(DecoderResult paramDecoderResult)
/*    */   {
/* 35 */     if (paramDecoderResult == null) {
/* 36 */       throw new NullPointerException("decoderResult");
/*    */     }
/* 38 */     this.decoderResult = paramDecoderResult;
/*    */   }
/*    */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\io\netty\handler\codec\http\DefaultHttpObject.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */
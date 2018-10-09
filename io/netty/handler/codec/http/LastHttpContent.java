/*    */ package io.netty.handler.codec.http;
/*    */ 
/*    */ import io.netty.buffer.ByteBuf;
/*    */ import io.netty.buffer.Unpooled;
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
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public abstract interface LastHttpContent
/*    */   extends HttpContent
/*    */ {
/* 30 */   public static final LastHttpContent EMPTY_LAST_CONTENT = new LastHttpContent()
/*    */   {
/*    */     public ByteBuf content()
/*    */     {
/* 34 */       return Unpooled.EMPTY_BUFFER;
/*    */     }
/*    */     
/*    */     public LastHttpContent copy()
/*    */     {
/* 39 */       return EMPTY_LAST_CONTENT;
/*    */     }
/*    */     
/*    */     public LastHttpContent duplicate()
/*    */     {
/* 44 */       return this;
/*    */     }
/*    */     
/*    */     public HttpHeaders trailingHeaders()
/*    */     {
/* 49 */       return HttpHeaders.EMPTY_HEADERS;
/*    */     }
/*    */     
/*    */     public DecoderResult getDecoderResult()
/*    */     {
/* 54 */       return DecoderResult.SUCCESS;
/*    */     }
/*    */     
/*    */     public void setDecoderResult(DecoderResult paramAnonymousDecoderResult)
/*    */     {
/* 59 */       throw new UnsupportedOperationException("read only");
/*    */     }
/*    */     
/*    */     public int refCnt()
/*    */     {
/* 64 */       return 1;
/*    */     }
/*    */     
/*    */     public LastHttpContent retain()
/*    */     {
/* 69 */       return this;
/*    */     }
/*    */     
/*    */     public LastHttpContent retain(int paramAnonymousInt)
/*    */     {
/* 74 */       return this;
/*    */     }
/*    */     
/*    */     public boolean release()
/*    */     {
/* 79 */       return false;
/*    */     }
/*    */     
/*    */     public boolean release(int paramAnonymousInt)
/*    */     {
/* 84 */       return false;
/*    */     }
/*    */     
/*    */     public String toString()
/*    */     {
/* 89 */       return "EmptyLastHttpContent";
/*    */     }
/*    */   };
/*    */   
/*    */   public abstract HttpHeaders trailingHeaders();
/*    */   
/*    */   public abstract LastHttpContent copy();
/*    */   
/*    */   public abstract LastHttpContent retain(int paramInt);
/*    */   
/*    */   public abstract LastHttpContent retain();
/*    */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\io\netty\handler\codec\http\LastHttpContent.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */
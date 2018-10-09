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
/*    */ final class ComposedLastHttpContent
/*    */   implements LastHttpContent
/*    */ {
/*    */   private final HttpHeaders trailingHeaders;
/*    */   private DecoderResult result;
/*    */   
/*    */   ComposedLastHttpContent(HttpHeaders paramHttpHeaders)
/*    */   {
/* 28 */     this.trailingHeaders = paramHttpHeaders;
/*    */   }
/*    */   
/*    */   public HttpHeaders trailingHeaders() {
/* 32 */     return this.trailingHeaders;
/*    */   }
/*    */   
/*    */   public LastHttpContent copy()
/*    */   {
/* 37 */     DefaultLastHttpContent localDefaultLastHttpContent = new DefaultLastHttpContent(Unpooled.EMPTY_BUFFER);
/* 38 */     localDefaultLastHttpContent.trailingHeaders().set(trailingHeaders());
/* 39 */     return localDefaultLastHttpContent;
/*    */   }
/*    */   
/*    */   public LastHttpContent retain(int paramInt)
/*    */   {
/* 44 */     return this;
/*    */   }
/*    */   
/*    */   public LastHttpContent retain()
/*    */   {
/* 49 */     return this;
/*    */   }
/*    */   
/*    */   public HttpContent duplicate()
/*    */   {
/* 54 */     return copy();
/*    */   }
/*    */   
/*    */   public ByteBuf content()
/*    */   {
/* 59 */     return Unpooled.EMPTY_BUFFER;
/*    */   }
/*    */   
/*    */   public DecoderResult getDecoderResult()
/*    */   {
/* 64 */     return this.result;
/*    */   }
/*    */   
/*    */   public void setDecoderResult(DecoderResult paramDecoderResult)
/*    */   {
/* 69 */     this.result = paramDecoderResult;
/*    */   }
/*    */   
/*    */   public int refCnt()
/*    */   {
/* 74 */     return 1;
/*    */   }
/*    */   
/*    */   public boolean release()
/*    */   {
/* 79 */     return false;
/*    */   }
/*    */   
/*    */   public boolean release(int paramInt)
/*    */   {
/* 84 */     return false;
/*    */   }
/*    */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\io\netty\handler\codec\http\ComposedLastHttpContent.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */
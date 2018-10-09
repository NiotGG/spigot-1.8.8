/*    */ package io.netty.handler.codec.http;
/*    */ 
/*    */ import io.netty.buffer.ByteBuf;
/*    */ import io.netty.util.internal.StringUtil;
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
/*    */ public class DefaultHttpContent
/*    */   extends DefaultHttpObject
/*    */   implements HttpContent
/*    */ {
/*    */   private final ByteBuf content;
/*    */   
/*    */   public DefaultHttpContent(ByteBuf paramByteBuf)
/*    */   {
/* 32 */     if (paramByteBuf == null) {
/* 33 */       throw new NullPointerException("content");
/*    */     }
/* 35 */     this.content = paramByteBuf;
/*    */   }
/*    */   
/*    */   public ByteBuf content()
/*    */   {
/* 40 */     return this.content;
/*    */   }
/*    */   
/*    */   public HttpContent copy()
/*    */   {
/* 45 */     return new DefaultHttpContent(this.content.copy());
/*    */   }
/*    */   
/*    */   public HttpContent duplicate()
/*    */   {
/* 50 */     return new DefaultHttpContent(this.content.duplicate());
/*    */   }
/*    */   
/*    */   public int refCnt()
/*    */   {
/* 55 */     return this.content.refCnt();
/*    */   }
/*    */   
/*    */   public HttpContent retain()
/*    */   {
/* 60 */     this.content.retain();
/* 61 */     return this;
/*    */   }
/*    */   
/*    */   public HttpContent retain(int paramInt)
/*    */   {
/* 66 */     this.content.retain(paramInt);
/* 67 */     return this;
/*    */   }
/*    */   
/*    */   public boolean release()
/*    */   {
/* 72 */     return this.content.release();
/*    */   }
/*    */   
/*    */   public boolean release(int paramInt)
/*    */   {
/* 77 */     return this.content.release(paramInt);
/*    */   }
/*    */   
/*    */   public String toString()
/*    */   {
/* 82 */     return StringUtil.simpleClassName(this) + "(data: " + content() + ", decoderResult: " + getDecoderResult() + ')';
/*    */   }
/*    */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\io\netty\handler\codec\http\DefaultHttpContent.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */
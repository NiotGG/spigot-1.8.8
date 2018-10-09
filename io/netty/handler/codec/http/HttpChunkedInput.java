/*    */ package io.netty.handler.codec.http;
/*    */ 
/*    */ import io.netty.buffer.ByteBuf;
/*    */ import io.netty.channel.ChannelHandlerContext;
/*    */ import io.netty.handler.stream.ChunkedInput;
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
/*    */ public class HttpChunkedInput
/*    */   implements ChunkedInput<HttpContent>
/*    */ {
/*    */   private final ChunkedInput<ByteBuf> input;
/*    */   private final LastHttpContent lastHttpContent;
/*    */   private boolean sentLastChunk;
/*    */   
/*    */   public HttpChunkedInput(ChunkedInput<ByteBuf> paramChunkedInput)
/*    */   {
/* 53 */     this.input = paramChunkedInput;
/* 54 */     this.lastHttpContent = LastHttpContent.EMPTY_LAST_CONTENT;
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   public HttpChunkedInput(ChunkedInput<ByteBuf> paramChunkedInput, LastHttpContent paramLastHttpContent)
/*    */   {
/* 65 */     this.input = paramChunkedInput;
/* 66 */     this.lastHttpContent = paramLastHttpContent;
/*    */   }
/*    */   
/*    */   public boolean isEndOfInput() throws Exception
/*    */   {
/* 71 */     if (this.input.isEndOfInput())
/*    */     {
/* 73 */       return this.sentLastChunk;
/*    */     }
/* 75 */     return false;
/*    */   }
/*    */   
/*    */   public void close()
/*    */     throws Exception
/*    */   {
/* 81 */     this.input.close();
/*    */   }
/*    */   
/*    */   public HttpContent readChunk(ChannelHandlerContext paramChannelHandlerContext) throws Exception
/*    */   {
/* 86 */     if (this.input.isEndOfInput()) {
/* 87 */       if (this.sentLastChunk) {
/* 88 */         return null;
/*    */       }
/*    */       
/* 91 */       this.sentLastChunk = true;
/* 92 */       return this.lastHttpContent;
/*    */     }
/*    */     
/* 95 */     ByteBuf localByteBuf = (ByteBuf)this.input.readChunk(paramChannelHandlerContext);
/* 96 */     return new DefaultHttpContent(localByteBuf);
/*    */   }
/*    */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\io\netty\handler\codec\http\HttpChunkedInput.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */
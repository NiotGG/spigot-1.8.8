/*     */ package io.netty.handler.codec.http;
/*     */ 
/*     */ import io.netty.buffer.ByteBuf;
/*     */ import io.netty.buffer.ByteBufAllocator;
/*     */ import io.netty.buffer.CompositeByteBuf;
/*     */ import io.netty.buffer.DefaultByteBufHolder;
/*     */ import io.netty.buffer.Unpooled;
/*     */ import io.netty.channel.ChannelFuture;
/*     */ import io.netty.channel.ChannelFutureListener;
/*     */ import io.netty.channel.ChannelHandlerContext;
/*     */ import io.netty.handler.codec.DecoderResult;
/*     */ import io.netty.handler.codec.MessageToMessageDecoder;
/*     */ import io.netty.handler.codec.TooLongFrameException;
/*     */ import java.util.List;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class HttpObjectAggregator
/*     */   extends MessageToMessageDecoder<HttpObject>
/*     */ {
/*     */   public static final int DEFAULT_MAX_COMPOSITEBUFFER_COMPONENTS = 1024;
/*  56 */   private static final FullHttpResponse CONTINUE = new DefaultFullHttpResponse(HttpVersion.HTTP_1_1, HttpResponseStatus.CONTINUE, Unpooled.EMPTY_BUFFER);
/*     */   
/*     */   private final int maxContentLength;
/*     */   
/*     */   private AggregatedFullHttpMessage currentMessage;
/*     */   
/*     */   private boolean tooLongFrameFound;
/*  63 */   private int maxCumulationBufferComponents = 1024;
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   private ChannelHandlerContext ctx;
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public HttpObjectAggregator(int paramInt)
/*     */   {
/*  75 */     if (paramInt <= 0) {
/*  76 */       throw new IllegalArgumentException("maxContentLength must be a positive integer: " + paramInt);
/*     */     }
/*     */     
/*     */ 
/*  80 */     this.maxContentLength = paramInt;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public final int getMaxCumulationBufferComponents()
/*     */   {
/*  90 */     return this.maxCumulationBufferComponents;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public final void setMaxCumulationBufferComponents(int paramInt)
/*     */   {
/* 101 */     if (paramInt < 2) {
/* 102 */       throw new IllegalArgumentException("maxCumulationBufferComponents: " + paramInt + " (expected: >= 2)");
/*     */     }
/*     */     
/*     */ 
/*     */ 
/* 107 */     if (this.ctx == null) {
/* 108 */       this.maxCumulationBufferComponents = paramInt;
/*     */     } else {
/* 110 */       throw new IllegalStateException("decoder properties cannot be changed once the decoder is added to a pipeline.");
/*     */     }
/*     */   }
/*     */   
/*     */   protected void decode(final ChannelHandlerContext paramChannelHandlerContext, HttpObject paramHttpObject, List<Object> paramList)
/*     */     throws Exception
/*     */   {
/* 117 */     Object localObject1 = this.currentMessage;
/*     */     Object localObject2;
/* 119 */     Object localObject3; if ((paramHttpObject instanceof HttpMessage)) {
/* 120 */       this.tooLongFrameFound = false;
/* 121 */       assert (localObject1 == null);
/*     */       
/* 123 */       localObject2 = (HttpMessage)paramHttpObject;
/*     */       
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/* 130 */       if (HttpHeaders.is100ContinueExpected((HttpMessage)localObject2)) {
/* 131 */         paramChannelHandlerContext.writeAndFlush(CONTINUE).addListener(new ChannelFutureListener()
/*     */         {
/*     */           public void operationComplete(ChannelFuture paramAnonymousChannelFuture) throws Exception {
/* 134 */             if (!paramAnonymousChannelFuture.isSuccess()) {
/* 135 */               paramChannelHandlerContext.fireExceptionCaught(paramAnonymousChannelFuture.cause());
/*     */             }
/*     */           }
/*     */         });
/*     */       }
/*     */       
/* 141 */       if (!((HttpMessage)localObject2).getDecoderResult().isSuccess()) {
/* 142 */         HttpHeaders.removeTransferEncodingChunked((HttpMessage)localObject2);
/* 143 */         paramList.add(toFullMessage((HttpMessage)localObject2));
/* 144 */         this.currentMessage = null;
/* 145 */         return;
/*     */       }
/* 147 */       if ((paramHttpObject instanceof HttpRequest)) {
/* 148 */         localObject3 = (HttpRequest)paramHttpObject;
/* 149 */         this.currentMessage = (localObject1 = new AggregatedFullHttpRequest((HttpRequest)localObject3, paramChannelHandlerContext.alloc().compositeBuffer(this.maxCumulationBufferComponents), null, null));
/*     */       }
/* 151 */       else if ((paramHttpObject instanceof HttpResponse)) {
/* 152 */         localObject3 = (HttpResponse)paramHttpObject;
/* 153 */         this.currentMessage = (localObject1 = new AggregatedFullHttpResponse((HttpResponse)localObject3, Unpooled.compositeBuffer(this.maxCumulationBufferComponents), null, null));
/*     */       }
/*     */       else
/*     */       {
/* 157 */         throw new Error();
/*     */       }
/*     */       
/*     */ 
/* 161 */       HttpHeaders.removeTransferEncodingChunked((HttpMessage)localObject1);
/* 162 */     } else if ((paramHttpObject instanceof HttpContent)) {
/* 163 */       if (this.tooLongFrameFound) {
/* 164 */         if ((paramHttpObject instanceof LastHttpContent)) {
/* 165 */           this.currentMessage = null;
/*     */         }
/*     */         
/* 168 */         return;
/*     */       }
/* 170 */       assert (localObject1 != null);
/*     */       
/*     */ 
/* 173 */       localObject2 = (HttpContent)paramHttpObject;
/* 174 */       localObject3 = (CompositeByteBuf)((AggregatedFullHttpMessage)localObject1).content();
/*     */       
/* 176 */       if (((CompositeByteBuf)localObject3).readableBytes() > this.maxContentLength - ((HttpContent)localObject2).content().readableBytes()) {
/* 177 */         this.tooLongFrameFound = true;
/*     */         
/*     */ 
/* 180 */         ((AggregatedFullHttpMessage)localObject1).release();
/* 181 */         this.currentMessage = null;
/*     */         
/* 183 */         throw new TooLongFrameException("HTTP content length exceeded " + this.maxContentLength + " bytes.");
/*     */       }
/*     */       
/*     */ 
/*     */ 
/*     */ 
/* 189 */       if (((HttpContent)localObject2).content().isReadable()) {
/* 190 */         ((HttpContent)localObject2).retain();
/* 191 */         ((CompositeByteBuf)localObject3).addComponent(((HttpContent)localObject2).content());
/* 192 */         ((CompositeByteBuf)localObject3).writerIndex(((CompositeByteBuf)localObject3).writerIndex() + ((HttpContent)localObject2).content().readableBytes());
/*     */       }
/*     */       
/*     */       boolean bool;
/* 196 */       if (!((HttpContent)localObject2).getDecoderResult().isSuccess()) {
/* 197 */         ((AggregatedFullHttpMessage)localObject1).setDecoderResult(DecoderResult.failure(((HttpContent)localObject2).getDecoderResult().cause()));
/*     */         
/* 199 */         bool = true;
/*     */       } else {
/* 201 */         bool = localObject2 instanceof LastHttpContent;
/*     */       }
/*     */       
/* 204 */       if (bool) {
/* 205 */         this.currentMessage = null;
/*     */         
/*     */ 
/* 208 */         if ((localObject2 instanceof LastHttpContent)) {
/* 209 */           LastHttpContent localLastHttpContent = (LastHttpContent)localObject2;
/* 210 */           ((AggregatedFullHttpMessage)localObject1).setTrailingHeaders(localLastHttpContent.trailingHeaders());
/*     */         } else {
/* 212 */           ((AggregatedFullHttpMessage)localObject1).setTrailingHeaders(new DefaultHttpHeaders());
/*     */         }
/*     */         
/*     */ 
/* 216 */         ((AggregatedFullHttpMessage)localObject1).headers().set("Content-Length", String.valueOf(((CompositeByteBuf)localObject3).readableBytes()));
/*     */         
/*     */ 
/*     */ 
/*     */ 
/* 221 */         paramList.add(localObject1);
/*     */       }
/*     */     } else {
/* 224 */       throw new Error();
/*     */     }
/*     */   }
/*     */   
/*     */   public void channelInactive(ChannelHandlerContext paramChannelHandlerContext) throws Exception
/*     */   {
/* 230 */     super.channelInactive(paramChannelHandlerContext);
/*     */     
/*     */ 
/* 233 */     if (this.currentMessage != null) {
/* 234 */       this.currentMessage.release();
/* 235 */       this.currentMessage = null;
/*     */     }
/*     */   }
/*     */   
/*     */   public void handlerAdded(ChannelHandlerContext paramChannelHandlerContext) throws Exception
/*     */   {
/* 241 */     this.ctx = paramChannelHandlerContext;
/*     */   }
/*     */   
/*     */   public void handlerRemoved(ChannelHandlerContext paramChannelHandlerContext) throws Exception
/*     */   {
/* 246 */     super.handlerRemoved(paramChannelHandlerContext);
/*     */     
/*     */ 
/* 249 */     if (this.currentMessage != null) {
/* 250 */       this.currentMessage.release();
/* 251 */       this.currentMessage = null;
/*     */     }
/*     */   }
/*     */   
/*     */   private static FullHttpMessage toFullMessage(HttpMessage paramHttpMessage) {
/* 256 */     if ((paramHttpMessage instanceof FullHttpMessage)) {
/* 257 */       return ((FullHttpMessage)paramHttpMessage).retain();
/*     */     }
/*     */     
/*     */     Object localObject;
/* 261 */     if ((paramHttpMessage instanceof HttpRequest)) {
/* 262 */       localObject = new AggregatedFullHttpRequest((HttpRequest)paramHttpMessage, Unpooled.EMPTY_BUFFER, new DefaultHttpHeaders(), null);
/*     */     }
/* 264 */     else if ((paramHttpMessage instanceof HttpResponse)) {
/* 265 */       localObject = new AggregatedFullHttpResponse((HttpResponse)paramHttpMessage, Unpooled.EMPTY_BUFFER, new DefaultHttpHeaders(), null);
/*     */     }
/*     */     else {
/* 268 */       throw new IllegalStateException();
/*     */     }
/*     */     
/* 271 */     return (FullHttpMessage)localObject;
/*     */   }
/*     */   
/*     */   private static abstract class AggregatedFullHttpMessage extends DefaultByteBufHolder implements FullHttpMessage {
/*     */     protected final HttpMessage message;
/*     */     private HttpHeaders trailingHeaders;
/*     */     
/*     */     private AggregatedFullHttpMessage(HttpMessage paramHttpMessage, ByteBuf paramByteBuf, HttpHeaders paramHttpHeaders) {
/* 279 */       super();
/* 280 */       this.message = paramHttpMessage;
/* 281 */       this.trailingHeaders = paramHttpHeaders;
/*     */     }
/*     */     
/*     */     public HttpHeaders trailingHeaders() {
/* 285 */       return this.trailingHeaders;
/*     */     }
/*     */     
/*     */     public void setTrailingHeaders(HttpHeaders paramHttpHeaders) {
/* 289 */       this.trailingHeaders = paramHttpHeaders;
/*     */     }
/*     */     
/*     */     public HttpVersion getProtocolVersion()
/*     */     {
/* 294 */       return this.message.getProtocolVersion();
/*     */     }
/*     */     
/*     */     public FullHttpMessage setProtocolVersion(HttpVersion paramHttpVersion)
/*     */     {
/* 299 */       this.message.setProtocolVersion(paramHttpVersion);
/* 300 */       return this;
/*     */     }
/*     */     
/*     */     public HttpHeaders headers()
/*     */     {
/* 305 */       return this.message.headers();
/*     */     }
/*     */     
/*     */     public DecoderResult getDecoderResult()
/*     */     {
/* 310 */       return this.message.getDecoderResult();
/*     */     }
/*     */     
/*     */     public void setDecoderResult(DecoderResult paramDecoderResult)
/*     */     {
/* 315 */       this.message.setDecoderResult(paramDecoderResult);
/*     */     }
/*     */     
/*     */     public FullHttpMessage retain(int paramInt)
/*     */     {
/* 320 */       super.retain(paramInt);
/* 321 */       return this;
/*     */     }
/*     */     
/*     */     public FullHttpMessage retain()
/*     */     {
/* 326 */       super.retain();
/* 327 */       return this;
/*     */     }
/*     */     
/*     */     public abstract FullHttpMessage copy();
/*     */     
/*     */     public abstract FullHttpMessage duplicate();
/*     */   }
/*     */   
/*     */   private static final class AggregatedFullHttpRequest
/*     */     extends HttpObjectAggregator.AggregatedFullHttpMessage implements FullHttpRequest
/*     */   {
/*     */     private AggregatedFullHttpRequest(HttpRequest paramHttpRequest, ByteBuf paramByteBuf, HttpHeaders paramHttpHeaders)
/*     */     {
/* 340 */       super(paramByteBuf, paramHttpHeaders, null);
/*     */     }
/*     */     
/*     */     public FullHttpRequest copy()
/*     */     {
/* 345 */       DefaultFullHttpRequest localDefaultFullHttpRequest = new DefaultFullHttpRequest(getProtocolVersion(), getMethod(), getUri(), content().copy());
/*     */       
/* 347 */       localDefaultFullHttpRequest.headers().set(headers());
/* 348 */       localDefaultFullHttpRequest.trailingHeaders().set(trailingHeaders());
/* 349 */       return localDefaultFullHttpRequest;
/*     */     }
/*     */     
/*     */     public FullHttpRequest duplicate()
/*     */     {
/* 354 */       DefaultFullHttpRequest localDefaultFullHttpRequest = new DefaultFullHttpRequest(getProtocolVersion(), getMethod(), getUri(), content().duplicate());
/*     */       
/* 356 */       localDefaultFullHttpRequest.headers().set(headers());
/* 357 */       localDefaultFullHttpRequest.trailingHeaders().set(trailingHeaders());
/* 358 */       return localDefaultFullHttpRequest;
/*     */     }
/*     */     
/*     */     public FullHttpRequest retain(int paramInt)
/*     */     {
/* 363 */       super.retain(paramInt);
/* 364 */       return this;
/*     */     }
/*     */     
/*     */     public FullHttpRequest retain()
/*     */     {
/* 369 */       super.retain();
/* 370 */       return this;
/*     */     }
/*     */     
/*     */     public FullHttpRequest setMethod(HttpMethod paramHttpMethod)
/*     */     {
/* 375 */       ((HttpRequest)this.message).setMethod(paramHttpMethod);
/* 376 */       return this;
/*     */     }
/*     */     
/*     */     public FullHttpRequest setUri(String paramString)
/*     */     {
/* 381 */       ((HttpRequest)this.message).setUri(paramString);
/* 382 */       return this;
/*     */     }
/*     */     
/*     */     public HttpMethod getMethod()
/*     */     {
/* 387 */       return ((HttpRequest)this.message).getMethod();
/*     */     }
/*     */     
/*     */     public String getUri()
/*     */     {
/* 392 */       return ((HttpRequest)this.message).getUri();
/*     */     }
/*     */     
/*     */     public FullHttpRequest setProtocolVersion(HttpVersion paramHttpVersion)
/*     */     {
/* 397 */       super.setProtocolVersion(paramHttpVersion);
/* 398 */       return this;
/*     */     }
/*     */   }
/*     */   
/*     */   private static final class AggregatedFullHttpResponse extends HttpObjectAggregator.AggregatedFullHttpMessage implements FullHttpResponse
/*     */   {
/*     */     private AggregatedFullHttpResponse(HttpResponse paramHttpResponse, ByteBuf paramByteBuf, HttpHeaders paramHttpHeaders) {
/* 405 */       super(paramByteBuf, paramHttpHeaders, null);
/*     */     }
/*     */     
/*     */     public FullHttpResponse copy()
/*     */     {
/* 410 */       DefaultFullHttpResponse localDefaultFullHttpResponse = new DefaultFullHttpResponse(getProtocolVersion(), getStatus(), content().copy());
/*     */       
/* 412 */       localDefaultFullHttpResponse.headers().set(headers());
/* 413 */       localDefaultFullHttpResponse.trailingHeaders().set(trailingHeaders());
/* 414 */       return localDefaultFullHttpResponse;
/*     */     }
/*     */     
/*     */     public FullHttpResponse duplicate()
/*     */     {
/* 419 */       DefaultFullHttpResponse localDefaultFullHttpResponse = new DefaultFullHttpResponse(getProtocolVersion(), getStatus(), content().duplicate());
/*     */       
/* 421 */       localDefaultFullHttpResponse.headers().set(headers());
/* 422 */       localDefaultFullHttpResponse.trailingHeaders().set(trailingHeaders());
/* 423 */       return localDefaultFullHttpResponse;
/*     */     }
/*     */     
/*     */     public FullHttpResponse setStatus(HttpResponseStatus paramHttpResponseStatus)
/*     */     {
/* 428 */       ((HttpResponse)this.message).setStatus(paramHttpResponseStatus);
/* 429 */       return this;
/*     */     }
/*     */     
/*     */     public HttpResponseStatus getStatus()
/*     */     {
/* 434 */       return ((HttpResponse)this.message).getStatus();
/*     */     }
/*     */     
/*     */     public FullHttpResponse setProtocolVersion(HttpVersion paramHttpVersion)
/*     */     {
/* 439 */       super.setProtocolVersion(paramHttpVersion);
/* 440 */       return this;
/*     */     }
/*     */     
/*     */     public FullHttpResponse retain(int paramInt)
/*     */     {
/* 445 */       super.retain(paramInt);
/* 446 */       return this;
/*     */     }
/*     */     
/*     */     public FullHttpResponse retain()
/*     */     {
/* 451 */       super.retain();
/* 452 */       return this;
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\io\netty\handler\codec\http\HttpObjectAggregator.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */
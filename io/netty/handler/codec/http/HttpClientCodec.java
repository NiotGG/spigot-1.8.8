/*     */ package io.netty.handler.codec.http;
/*     */ 
/*     */ import io.netty.buffer.ByteBuf;
/*     */ import io.netty.channel.ChannelHandlerContext;
/*     */ import io.netty.channel.CombinedChannelDuplexHandler;
/*     */ import io.netty.handler.codec.PrematureChannelClosureException;
/*     */ import java.util.ArrayDeque;
/*     */ import java.util.List;
/*     */ import java.util.Queue;
/*     */ import java.util.concurrent.atomic.AtomicLong;
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
/*     */ public final class HttpClientCodec
/*     */   extends CombinedChannelDuplexHandler<HttpResponseDecoder, HttpRequestEncoder>
/*     */ {
/*  47 */   private final Queue<HttpMethod> queue = new ArrayDeque();
/*     */   
/*     */ 
/*     */   private boolean done;
/*     */   
/*  52 */   private final AtomicLong requestResponseCounter = new AtomicLong();
/*     */   
/*     */ 
/*     */   private final boolean failOnMissingResponse;
/*     */   
/*     */ 
/*     */ 
/*     */   public HttpClientCodec()
/*     */   {
/*  61 */     this(4096, 8192, 8192, false);
/*     */   }
/*     */   
/*     */   public void setSingleDecode(boolean paramBoolean) {
/*  65 */     ((HttpResponseDecoder)inboundHandler()).setSingleDecode(paramBoolean);
/*     */   }
/*     */   
/*     */   public boolean isSingleDecode() {
/*  69 */     return ((HttpResponseDecoder)inboundHandler()).isSingleDecode();
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public HttpClientCodec(int paramInt1, int paramInt2, int paramInt3)
/*     */   {
/*  76 */     this(paramInt1, paramInt2, paramInt3, false);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public HttpClientCodec(int paramInt1, int paramInt2, int paramInt3, boolean paramBoolean)
/*     */   {
/*  84 */     this(paramInt1, paramInt2, paramInt3, paramBoolean, true);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public HttpClientCodec(int paramInt1, int paramInt2, int paramInt3, boolean paramBoolean1, boolean paramBoolean2)
/*     */   {
/*  93 */     init(new Decoder(paramInt1, paramInt2, paramInt3, paramBoolean2), new Encoder(null));
/*  94 */     this.failOnMissingResponse = paramBoolean1;
/*     */   }
/*     */   
/*     */   private final class Encoder extends HttpRequestEncoder
/*     */   {
/*     */     private Encoder() {}
/*     */     
/*     */     protected void encode(ChannelHandlerContext paramChannelHandlerContext, Object paramObject, List<Object> paramList) throws Exception {
/* 102 */       if (((paramObject instanceof HttpRequest)) && (!HttpClientCodec.this.done)) {
/* 103 */         HttpClientCodec.this.queue.offer(((HttpRequest)paramObject).getMethod());
/*     */       }
/*     */       
/* 106 */       super.encode(paramChannelHandlerContext, paramObject, paramList);
/*     */       
/* 108 */       if (HttpClientCodec.this.failOnMissingResponse)
/*     */       {
/* 110 */         if ((paramObject instanceof LastHttpContent))
/*     */         {
/* 112 */           HttpClientCodec.this.requestResponseCounter.incrementAndGet();
/*     */         }
/*     */       }
/*     */     }
/*     */   }
/*     */   
/*     */   private final class Decoder extends HttpResponseDecoder {
/*     */     Decoder(int paramInt1, int paramInt2, int paramInt3, boolean paramBoolean) {
/* 120 */       super(paramInt2, paramInt3, paramBoolean);
/*     */     }
/*     */     
/*     */     protected void decode(ChannelHandlerContext paramChannelHandlerContext, ByteBuf paramByteBuf, List<Object> paramList) throws Exception
/*     */     {
/*     */       int i;
/* 126 */       if (HttpClientCodec.this.done) {
/* 127 */         i = actualReadableBytes();
/* 128 */         if (i == 0)
/*     */         {
/*     */ 
/* 131 */           return;
/*     */         }
/* 133 */         paramList.add(paramByteBuf.readBytes(i));
/*     */       } else {
/* 135 */         i = paramList.size();
/* 136 */         super.decode(paramChannelHandlerContext, paramByteBuf, paramList);
/* 137 */         if (HttpClientCodec.this.failOnMissingResponse) {
/* 138 */           int j = paramList.size();
/* 139 */           for (int k = i; k < j; k++) {
/* 140 */             decrement(paramList.get(k));
/*     */           }
/*     */         }
/*     */       }
/*     */     }
/*     */     
/*     */     private void decrement(Object paramObject) {
/* 147 */       if (paramObject == null) {
/* 148 */         return;
/*     */       }
/*     */       
/*     */ 
/* 152 */       if ((paramObject instanceof LastHttpContent)) {
/* 153 */         HttpClientCodec.this.requestResponseCounter.decrementAndGet();
/*     */       }
/*     */     }
/*     */     
/*     */     protected boolean isContentAlwaysEmpty(HttpMessage paramHttpMessage)
/*     */     {
/* 159 */       int i = ((HttpResponse)paramHttpMessage).getStatus().code();
/* 160 */       if (i == 100)
/*     */       {
/* 162 */         return true;
/*     */       }
/*     */       
/*     */ 
/*     */ 
/* 167 */       HttpMethod localHttpMethod = (HttpMethod)HttpClientCodec.this.queue.poll();
/*     */       
/* 169 */       int j = localHttpMethod.name().charAt(0);
/* 170 */       switch (j)
/*     */       {
/*     */ 
/*     */ 
/*     */ 
/*     */       case 72: 
/* 176 */         if (HttpMethod.HEAD.equals(localHttpMethod)) {
/* 177 */           return true;
/*     */         }
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
/*     */         break;
/*     */       case 67: 
/* 195 */         if ((i == 200) && 
/* 196 */           (HttpMethod.CONNECT.equals(localHttpMethod)))
/*     */         {
/* 198 */           HttpClientCodec.this.done = true;
/* 199 */           HttpClientCodec.this.queue.clear();
/* 200 */           return true;
/*     */         }
/*     */         
/*     */         break;
/*     */       }
/*     */       
/* 206 */       return super.isContentAlwaysEmpty(paramHttpMessage);
/*     */     }
/*     */     
/*     */     public void channelInactive(ChannelHandlerContext paramChannelHandlerContext)
/*     */       throws Exception
/*     */     {
/* 212 */       super.channelInactive(paramChannelHandlerContext);
/*     */       
/* 214 */       if (HttpClientCodec.this.failOnMissingResponse) {
/* 215 */         long l = HttpClientCodec.this.requestResponseCounter.get();
/* 216 */         if (l > 0L) {
/* 217 */           paramChannelHandlerContext.fireExceptionCaught(new PrematureChannelClosureException("channel gone inactive with " + l + " missing response(s)"));
/*     */         }
/*     */       }
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\io\netty\handler\codec\http\HttpClientCodec.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */
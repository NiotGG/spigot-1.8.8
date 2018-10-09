/*     */ package io.netty.handler.codec.http;
/*     */ 
/*     */ import io.netty.buffer.ByteBuf;
/*     */ import io.netty.buffer.ByteBufHolder;
/*     */ import io.netty.channel.ChannelHandlerContext;
/*     */ import io.netty.channel.embedded.EmbeddedChannel;
/*     */ import io.netty.handler.codec.MessageToMessageCodec;
/*     */ import io.netty.util.ReferenceCountUtil;
/*     */ import java.util.ArrayDeque;
/*     */ import java.util.List;
/*     */ import java.util.Queue;
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
/*     */ public abstract class HttpContentEncoder
/*     */   extends MessageToMessageCodec<HttpRequest, HttpObject>
/*     */ {
/*     */   private final Queue<String> acceptEncodingQueue;
/*     */   private String acceptEncoding;
/*     */   private EmbeddedChannel encoder;
/*     */   private State state;
/*     */   
/*     */   private static enum State
/*     */   {
/*  56 */     PASS_THROUGH, 
/*  57 */     AWAIT_HEADERS, 
/*  58 */     AWAIT_CONTENT;
/*     */     
/*     */     private State() {} }
/*  61 */   public HttpContentEncoder() { this.acceptEncodingQueue = new ArrayDeque();
/*     */     
/*     */ 
/*  64 */     this.state = State.AWAIT_HEADERS;
/*     */   }
/*     */   
/*     */   public boolean acceptOutboundMessage(Object paramObject) throws Exception {
/*  68 */     return ((paramObject instanceof HttpContent)) || ((paramObject instanceof HttpResponse));
/*     */   }
/*     */   
/*     */   protected void decode(ChannelHandlerContext paramChannelHandlerContext, HttpRequest paramHttpRequest, List<Object> paramList)
/*     */     throws Exception
/*     */   {
/*  74 */     String str = paramHttpRequest.headers().get("Accept-Encoding");
/*  75 */     if (str == null) {
/*  76 */       str = "identity";
/*     */     }
/*  78 */     this.acceptEncodingQueue.add(str);
/*  79 */     paramList.add(ReferenceCountUtil.retain(paramHttpRequest));
/*     */   }
/*     */   
/*     */   protected void encode(ChannelHandlerContext paramChannelHandlerContext, HttpObject paramHttpObject, List<Object> paramList) throws Exception
/*     */   {
/*  84 */     int i = ((paramHttpObject instanceof HttpResponse)) && ((paramHttpObject instanceof LastHttpContent)) ? 1 : 0;
/*  85 */     switch (this.state) {
/*     */     case AWAIT_HEADERS: 
/*  87 */       ensureHeaders(paramHttpObject);
/*  88 */       assert (this.encoder == null);
/*     */       
/*  90 */       HttpResponse localHttpResponse = (HttpResponse)paramHttpObject;
/*     */       
/*  92 */       if (localHttpResponse.getStatus().code() == 100) {
/*  93 */         if (i != 0) {
/*  94 */           paramList.add(ReferenceCountUtil.retain(localHttpResponse));
/*     */         } else {
/*  96 */           paramList.add(localHttpResponse);
/*     */           
/*  98 */           this.state = State.PASS_THROUGH;
/*     */         }
/*     */         
/*     */       }
/*     */       else
/*     */       {
/* 104 */         this.acceptEncoding = ((String)this.acceptEncodingQueue.poll());
/* 105 */         if (this.acceptEncoding == null) {
/* 106 */           throw new IllegalStateException("cannot send more responses than requests");
/*     */         }
/*     */         
/* 109 */         if (i != 0)
/*     */         {
/* 111 */           if (!((ByteBufHolder)localHttpResponse).content().isReadable()) {
/* 112 */             paramList.add(ReferenceCountUtil.retain(localHttpResponse));
/* 113 */             return;
/*     */           }
/*     */         }
/*     */         
/*     */ 
/* 118 */         Result localResult = beginEncode(localHttpResponse, this.acceptEncoding);
/*     */         
/*     */ 
/* 121 */         if (localResult == null) {
/* 122 */           if (i != 0) {
/* 123 */             paramList.add(ReferenceCountUtil.retain(localHttpResponse));
/*     */           } else {
/* 125 */             paramList.add(localHttpResponse);
/*     */             
/* 127 */             this.state = State.PASS_THROUGH;
/*     */           }
/*     */         }
/*     */         else
/*     */         {
/* 132 */           this.encoder = localResult.contentEncoder();
/*     */           
/*     */ 
/*     */ 
/* 136 */           localHttpResponse.headers().set("Content-Encoding", localResult.targetContentEncoding());
/*     */           
/*     */ 
/* 139 */           localHttpResponse.headers().remove("Content-Length");
/* 140 */           localHttpResponse.headers().set("Transfer-Encoding", "chunked");
/*     */           
/*     */ 
/* 143 */           if (i != 0)
/*     */           {
/* 145 */             DefaultHttpResponse localDefaultHttpResponse = new DefaultHttpResponse(localHttpResponse.getProtocolVersion(), localHttpResponse.getStatus());
/* 146 */             localDefaultHttpResponse.headers().set(localHttpResponse.headers());
/* 147 */             paramList.add(localDefaultHttpResponse);
/*     */           }
/*     */           else {
/* 150 */             paramList.add(localHttpResponse);
/* 151 */             this.state = State.AWAIT_CONTENT;
/* 152 */             if (!(paramHttpObject instanceof HttpContent)) {
/*     */               return;
/*     */             }
/*     */           }
/*     */         }
/*     */       }
/*     */       
/*     */       break;
/*     */     case AWAIT_CONTENT: 
/* 161 */       ensureContent(paramHttpObject);
/* 162 */       if (encodeContent((HttpContent)paramHttpObject, paramList)) {
/* 163 */         this.state = State.AWAIT_HEADERS;
/*     */       }
/*     */       
/*     */       break;
/*     */     case PASS_THROUGH: 
/* 168 */       ensureContent(paramHttpObject);
/* 169 */       paramList.add(ReferenceCountUtil.retain(paramHttpObject));
/*     */       
/* 171 */       if ((paramHttpObject instanceof LastHttpContent)) {
/* 172 */         this.state = State.AWAIT_HEADERS;
/*     */       }
/*     */       break;
/*     */     }
/*     */   }
/*     */   
/*     */   private static void ensureHeaders(HttpObject paramHttpObject)
/*     */   {
/* 180 */     if (!(paramHttpObject instanceof HttpResponse)) {
/* 181 */       throw new IllegalStateException("unexpected message type: " + paramHttpObject.getClass().getName() + " (expected: " + HttpResponse.class.getSimpleName() + ')');
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */   private static void ensureContent(HttpObject paramHttpObject)
/*     */   {
/* 188 */     if (!(paramHttpObject instanceof HttpContent)) {
/* 189 */       throw new IllegalStateException("unexpected message type: " + paramHttpObject.getClass().getName() + " (expected: " + HttpContent.class.getSimpleName() + ')');
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */   private boolean encodeContent(HttpContent paramHttpContent, List<Object> paramList)
/*     */   {
/* 196 */     ByteBuf localByteBuf = paramHttpContent.content();
/*     */     
/* 198 */     encode(localByteBuf, paramList);
/*     */     
/* 200 */     if ((paramHttpContent instanceof LastHttpContent)) {
/* 201 */       finishEncode(paramList);
/* 202 */       LastHttpContent localLastHttpContent = (LastHttpContent)paramHttpContent;
/*     */       
/*     */ 
/*     */ 
/* 206 */       HttpHeaders localHttpHeaders = localLastHttpContent.trailingHeaders();
/* 207 */       if (localHttpHeaders.isEmpty()) {
/* 208 */         paramList.add(LastHttpContent.EMPTY_LAST_CONTENT);
/*     */       } else {
/* 210 */         paramList.add(new ComposedLastHttpContent(localHttpHeaders));
/*     */       }
/* 212 */       return true;
/*     */     }
/* 214 */     return false;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   protected abstract Result beginEncode(HttpResponse paramHttpResponse, String paramString)
/*     */     throws Exception;
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public void handlerRemoved(ChannelHandlerContext paramChannelHandlerContext)
/*     */     throws Exception
/*     */   {
/* 235 */     cleanup();
/* 236 */     super.handlerRemoved(paramChannelHandlerContext);
/*     */   }
/*     */   
/*     */   public void channelInactive(ChannelHandlerContext paramChannelHandlerContext) throws Exception
/*     */   {
/* 241 */     cleanup();
/* 242 */     super.channelInactive(paramChannelHandlerContext);
/*     */   }
/*     */   
/*     */   private void cleanup() {
/* 246 */     if (this.encoder != null)
/*     */     {
/* 248 */       if (this.encoder.finish()) {
/*     */         for (;;) {
/* 250 */           ByteBuf localByteBuf = (ByteBuf)this.encoder.readOutbound();
/* 251 */           if (localByteBuf == null) {
/*     */             break;
/*     */           }
/*     */           
/*     */ 
/* 256 */           localByteBuf.release();
/*     */         }
/*     */       }
/* 259 */       this.encoder = null;
/*     */     }
/*     */   }
/*     */   
/*     */   private void encode(ByteBuf paramByteBuf, List<Object> paramList)
/*     */   {
/* 265 */     this.encoder.writeOutbound(new Object[] { paramByteBuf.retain() });
/* 266 */     fetchEncoderOutput(paramList);
/*     */   }
/*     */   
/*     */   private void finishEncode(List<Object> paramList) {
/* 270 */     if (this.encoder.finish()) {
/* 271 */       fetchEncoderOutput(paramList);
/*     */     }
/* 273 */     this.encoder = null;
/*     */   }
/*     */   
/*     */   private void fetchEncoderOutput(List<Object> paramList) {
/*     */     for (;;) {
/* 278 */       ByteBuf localByteBuf = (ByteBuf)this.encoder.readOutbound();
/* 279 */       if (localByteBuf == null) {
/*     */         break;
/*     */       }
/* 282 */       if (!localByteBuf.isReadable()) {
/* 283 */         localByteBuf.release();
/*     */       }
/*     */       else
/* 286 */         paramList.add(new DefaultHttpContent(localByteBuf));
/*     */     }
/*     */   }
/*     */   
/*     */   public static final class Result {
/*     */     private final String targetContentEncoding;
/*     */     private final EmbeddedChannel contentEncoder;
/*     */     
/*     */     public Result(String paramString, EmbeddedChannel paramEmbeddedChannel) {
/* 295 */       if (paramString == null) {
/* 296 */         throw new NullPointerException("targetContentEncoding");
/*     */       }
/* 298 */       if (paramEmbeddedChannel == null) {
/* 299 */         throw new NullPointerException("contentEncoder");
/*     */       }
/*     */       
/* 302 */       this.targetContentEncoding = paramString;
/* 303 */       this.contentEncoder = paramEmbeddedChannel;
/*     */     }
/*     */     
/*     */     public String targetContentEncoding() {
/* 307 */       return this.targetContentEncoding;
/*     */     }
/*     */     
/*     */     public EmbeddedChannel contentEncoder() {
/* 311 */       return this.contentEncoder;
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\io\netty\handler\codec\http\HttpContentEncoder.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */
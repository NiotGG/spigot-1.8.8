/*     */ package io.netty.handler.codec.http;
/*     */ 
/*     */ import io.netty.buffer.ByteBuf;
/*     */ import io.netty.channel.ChannelHandlerContext;
/*     */ import io.netty.channel.embedded.EmbeddedChannel;
/*     */ import io.netty.handler.codec.MessageToMessageDecoder;
/*     */ import io.netty.util.ReferenceCountUtil;
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
/*     */ public abstract class HttpContentDecoder
/*     */   extends MessageToMessageDecoder<HttpObject>
/*     */ {
/*     */   private EmbeddedChannel decoder;
/*     */   private HttpMessage message;
/*     */   private boolean decodeStarted;
/*     */   private boolean continueResponse;
/*     */   
/*     */   protected void decode(ChannelHandlerContext paramChannelHandlerContext, HttpObject paramHttpObject, List<Object> paramList)
/*     */     throws Exception
/*     */   {
/*  54 */     if (((paramHttpObject instanceof HttpResponse)) && (((HttpResponse)paramHttpObject).getStatus().code() == 100))
/*     */     {
/*  56 */       if (!(paramHttpObject instanceof LastHttpContent)) {
/*  57 */         this.continueResponse = true;
/*     */       }
/*     */       
/*  60 */       paramList.add(ReferenceCountUtil.retain(paramHttpObject));
/*  61 */       return;
/*     */     }
/*     */     
/*  64 */     if (this.continueResponse) {
/*  65 */       if ((paramHttpObject instanceof LastHttpContent)) {
/*  66 */         this.continueResponse = false;
/*     */       }
/*     */       
/*  69 */       paramList.add(ReferenceCountUtil.retain(paramHttpObject));
/*  70 */       return;
/*     */     }
/*     */     
/*  73 */     if ((paramHttpObject instanceof HttpMessage)) {
/*  74 */       assert (this.message == null);
/*  75 */       this.message = ((HttpMessage)paramHttpObject);
/*  76 */       this.decodeStarted = false;
/*  77 */       cleanup();
/*     */     }
/*     */     
/*  80 */     if ((paramHttpObject instanceof HttpContent)) {
/*  81 */       HttpContent localHttpContent = (HttpContent)paramHttpObject;
/*     */       
/*  83 */       if (!this.decodeStarted) {
/*  84 */         this.decodeStarted = true;
/*  85 */         HttpMessage localHttpMessage = this.message;
/*  86 */         HttpHeaders localHttpHeaders = localHttpMessage.headers();
/*  87 */         this.message = null;
/*     */         
/*     */ 
/*  90 */         String str1 = localHttpHeaders.get("Content-Encoding");
/*  91 */         if (str1 != null) {
/*  92 */           str1 = str1.trim();
/*     */         } else {
/*  94 */           str1 = "identity";
/*     */         }
/*     */         
/*  97 */         if ((this.decoder = newContentDecoder(str1)) != null)
/*     */         {
/*     */ 
/* 100 */           String str2 = getTargetContentEncoding(str1);
/* 101 */           if ("identity".equals(str2))
/*     */           {
/*     */ 
/* 104 */             localHttpHeaders.remove("Content-Encoding");
/*     */           } else {
/* 106 */             localHttpHeaders.set("Content-Encoding", str2);
/*     */           }
/*     */           
/* 109 */           paramList.add(localHttpMessage);
/* 110 */           decodeContent(localHttpContent, paramList);
/*     */           
/*     */ 
/* 113 */           if (localHttpHeaders.contains("Content-Length")) {
/* 114 */             int i = 0;
/* 115 */             int j = paramList.size();
/* 116 */             for (int k = 0; k < j; k++) {
/* 117 */               Object localObject = paramList.get(k);
/* 118 */               if ((localObject instanceof HttpContent)) {
/* 119 */                 i += ((HttpContent)localObject).content().readableBytes();
/*     */               }
/*     */             }
/* 122 */             localHttpHeaders.set("Content-Length", Integer.toString(i));
/*     */           }
/*     */           
/*     */ 
/* 126 */           return;
/*     */         }
/*     */         
/* 129 */         if ((localHttpContent instanceof LastHttpContent)) {
/* 130 */           this.decodeStarted = false;
/*     */         }
/* 132 */         paramList.add(localHttpMessage);
/* 133 */         paramList.add(localHttpContent.retain());
/* 134 */         return;
/*     */       }
/*     */       
/* 137 */       if (this.decoder != null) {
/* 138 */         decodeContent(localHttpContent, paramList);
/*     */       } else {
/* 140 */         if ((localHttpContent instanceof LastHttpContent)) {
/* 141 */           this.decodeStarted = false;
/*     */         }
/* 143 */         paramList.add(localHttpContent.retain());
/*     */       }
/*     */     }
/*     */   }
/*     */   
/*     */   private void decodeContent(HttpContent paramHttpContent, List<Object> paramList) {
/* 149 */     ByteBuf localByteBuf = paramHttpContent.content();
/*     */     
/* 151 */     decode(localByteBuf, paramList);
/*     */     
/* 153 */     if ((paramHttpContent instanceof LastHttpContent)) {
/* 154 */       finishDecode(paramList);
/*     */       
/* 156 */       LastHttpContent localLastHttpContent = (LastHttpContent)paramHttpContent;
/*     */       
/*     */ 
/* 159 */       HttpHeaders localHttpHeaders = localLastHttpContent.trailingHeaders();
/* 160 */       if (localHttpHeaders.isEmpty()) {
/* 161 */         paramList.add(LastHttpContent.EMPTY_LAST_CONTENT);
/*     */       } else {
/* 163 */         paramList.add(new ComposedLastHttpContent(localHttpHeaders));
/*     */       }
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   protected abstract EmbeddedChannel newContentDecoder(String paramString)
/*     */     throws Exception;
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   protected String getTargetContentEncoding(String paramString)
/*     */     throws Exception
/*     */   {
/* 189 */     return "identity";
/*     */   }
/*     */   
/*     */   public void handlerRemoved(ChannelHandlerContext paramChannelHandlerContext) throws Exception
/*     */   {
/* 194 */     cleanup();
/* 195 */     super.handlerRemoved(paramChannelHandlerContext);
/*     */   }
/*     */   
/*     */   public void channelInactive(ChannelHandlerContext paramChannelHandlerContext) throws Exception
/*     */   {
/* 200 */     cleanup();
/* 201 */     super.channelInactive(paramChannelHandlerContext);
/*     */   }
/*     */   
/*     */   private void cleanup() {
/* 205 */     if (this.decoder != null)
/*     */     {
/* 207 */       if (this.decoder.finish()) {
/*     */         for (;;) {
/* 209 */           ByteBuf localByteBuf = (ByteBuf)this.decoder.readOutbound();
/* 210 */           if (localByteBuf == null) {
/*     */             break;
/*     */           }
/*     */           
/* 214 */           localByteBuf.release();
/*     */         }
/*     */       }
/* 217 */       this.decoder = null;
/*     */     }
/*     */   }
/*     */   
/*     */   private void decode(ByteBuf paramByteBuf, List<Object> paramList)
/*     */   {
/* 223 */     this.decoder.writeInbound(new Object[] { paramByteBuf.retain() });
/* 224 */     fetchDecoderOutput(paramList);
/*     */   }
/*     */   
/*     */   private void finishDecode(List<Object> paramList) {
/* 228 */     if (this.decoder.finish()) {
/* 229 */       fetchDecoderOutput(paramList);
/*     */     }
/* 231 */     this.decodeStarted = false;
/* 232 */     this.decoder = null;
/*     */   }
/*     */   
/*     */   private void fetchDecoderOutput(List<Object> paramList) {
/*     */     for (;;) {
/* 237 */       ByteBuf localByteBuf = (ByteBuf)this.decoder.readInbound();
/* 238 */       if (localByteBuf == null) {
/*     */         break;
/*     */       }
/* 241 */       if (!localByteBuf.isReadable()) {
/* 242 */         localByteBuf.release();
/*     */       }
/*     */       else {
/* 245 */         paramList.add(new DefaultHttpContent(localByteBuf));
/*     */       }
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\io\netty\handler\codec\http\HttpContentDecoder.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */
/*     */ package io.netty.handler.codec.spdy;
/*     */ 
/*     */ import io.netty.buffer.ByteBuf;
/*     */ import io.netty.buffer.ByteBufAllocator;
/*     */ import io.netty.channel.ChannelHandlerContext;
/*     */ import io.netty.handler.codec.MessageToMessageDecoder;
/*     */ import io.netty.handler.codec.TooLongFrameException;
/*     */ import io.netty.handler.codec.http.DefaultFullHttpRequest;
/*     */ import io.netty.handler.codec.http.DefaultFullHttpResponse;
/*     */ import io.netty.handler.codec.http.FullHttpMessage;
/*     */ import io.netty.handler.codec.http.FullHttpRequest;
/*     */ import io.netty.handler.codec.http.FullHttpResponse;
/*     */ import io.netty.handler.codec.http.HttpHeaders;
/*     */ import io.netty.handler.codec.http.HttpMessage;
/*     */ import io.netty.handler.codec.http.HttpMethod;
/*     */ import io.netty.handler.codec.http.HttpResponseStatus;
/*     */ import io.netty.handler.codec.http.HttpVersion;
/*     */ import java.util.HashMap;
/*     */ import java.util.Iterator;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import java.util.Map.Entry;
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
/*     */ public class SpdyHttpDecoder
/*     */   extends MessageToMessageDecoder<SpdyFrame>
/*     */ {
/*     */   private final boolean validateHeaders;
/*     */   private final int spdyVersion;
/*     */   private final int maxContentLength;
/*     */   private final Map<Integer, FullHttpMessage> messageMap;
/*     */   
/*     */   public SpdyHttpDecoder(SpdyVersion paramSpdyVersion, int paramInt)
/*     */   {
/*  56 */     this(paramSpdyVersion, paramInt, new HashMap(), true);
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
/*     */   public SpdyHttpDecoder(SpdyVersion paramSpdyVersion, int paramInt, boolean paramBoolean)
/*     */   {
/*  69 */     this(paramSpdyVersion, paramInt, new HashMap(), paramBoolean);
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
/*     */   protected SpdyHttpDecoder(SpdyVersion paramSpdyVersion, int paramInt, Map<Integer, FullHttpMessage> paramMap)
/*     */   {
/*  82 */     this(paramSpdyVersion, paramInt, paramMap, true);
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
/*     */ 
/*     */ 
/*     */   protected SpdyHttpDecoder(SpdyVersion paramSpdyVersion, int paramInt, Map<Integer, FullHttpMessage> paramMap, boolean paramBoolean)
/*     */   {
/*  97 */     if (paramSpdyVersion == null) {
/*  98 */       throw new NullPointerException("version");
/*     */     }
/* 100 */     if (paramInt <= 0) {
/* 101 */       throw new IllegalArgumentException("maxContentLength must be a positive integer: " + paramInt);
/*     */     }
/*     */     
/* 104 */     this.spdyVersion = paramSpdyVersion.getVersion();
/* 105 */     this.maxContentLength = paramInt;
/* 106 */     this.messageMap = paramMap;
/* 107 */     this.validateHeaders = paramBoolean;
/*     */   }
/*     */   
/*     */   protected FullHttpMessage putMessage(int paramInt, FullHttpMessage paramFullHttpMessage) {
/* 111 */     return (FullHttpMessage)this.messageMap.put(Integer.valueOf(paramInt), paramFullHttpMessage);
/*     */   }
/*     */   
/*     */   protected FullHttpMessage getMessage(int paramInt) {
/* 115 */     return (FullHttpMessage)this.messageMap.get(Integer.valueOf(paramInt));
/*     */   }
/*     */   
/*     */ 
/* 119 */   protected FullHttpMessage removeMessage(int paramInt) { return (FullHttpMessage)this.messageMap.remove(Integer.valueOf(paramInt)); }
/*     */   
/*     */   protected void decode(ChannelHandlerContext paramChannelHandlerContext, SpdyFrame paramSpdyFrame, List<Object> paramList) throws Exception {
/*     */     Object localObject1;
/*     */     int i;
/*     */     Object localObject4;
/* 125 */     if ((paramSpdyFrame instanceof SpdySynStreamFrame))
/*     */     {
/*     */ 
/* 128 */       localObject1 = (SpdySynStreamFrame)paramSpdyFrame;
/* 129 */       i = ((SpdySynStreamFrame)localObject1).streamId();
/*     */       
/* 131 */       if (SpdyCodecUtil.isServerId(i))
/*     */       {
/* 133 */         int j = ((SpdySynStreamFrame)localObject1).associatedStreamId();
/*     */         
/*     */ 
/*     */ 
/* 137 */         if (j == 0) {
/* 138 */           localObject4 = new DefaultSpdyRstStreamFrame(i, SpdyStreamStatus.INVALID_STREAM);
/*     */           
/* 140 */           paramChannelHandlerContext.writeAndFlush(localObject4);
/* 141 */           return;
/*     */         }
/*     */         
/* 144 */         localObject4 = SpdyHeaders.getUrl(this.spdyVersion, (SpdyHeadersFrame)localObject1);
/*     */         
/*     */         Object localObject5;
/*     */         
/* 148 */         if (localObject4 == null) {
/* 149 */           localObject5 = new DefaultSpdyRstStreamFrame(i, SpdyStreamStatus.PROTOCOL_ERROR);
/*     */           
/* 151 */           paramChannelHandlerContext.writeAndFlush(localObject5);
/* 152 */           return;
/*     */         }
/*     */         
/*     */ 
/*     */ 
/* 157 */         if (((SpdySynStreamFrame)localObject1).isTruncated()) {
/* 158 */           localObject5 = new DefaultSpdyRstStreamFrame(i, SpdyStreamStatus.INTERNAL_ERROR);
/*     */           
/* 160 */           paramChannelHandlerContext.writeAndFlush(localObject5);
/* 161 */           return;
/*     */         }
/*     */         try
/*     */         {
/* 165 */           localObject5 = createHttpResponse(paramChannelHandlerContext, this.spdyVersion, (SpdyHeadersFrame)localObject1, this.validateHeaders);
/*     */           
/*     */ 
/*     */ 
/* 169 */           SpdyHttpHeaders.setStreamId((HttpMessage)localObject5, i);
/* 170 */           SpdyHttpHeaders.setAssociatedToStreamId((HttpMessage)localObject5, j);
/* 171 */           SpdyHttpHeaders.setPriority((HttpMessage)localObject5, ((SpdySynStreamFrame)localObject1).priority());
/* 172 */           SpdyHttpHeaders.setUrl((HttpMessage)localObject5, (String)localObject4);
/*     */           
/* 174 */           if (((SpdySynStreamFrame)localObject1).isLast()) {
/* 175 */             HttpHeaders.setContentLength((HttpMessage)localObject5, 0L);
/* 176 */             paramList.add(localObject5);
/*     */           }
/*     */           else {
/* 179 */             putMessage(i, (FullHttpMessage)localObject5);
/*     */           }
/*     */         } catch (Exception localException3) {
/* 182 */           DefaultSpdyRstStreamFrame localDefaultSpdyRstStreamFrame = new DefaultSpdyRstStreamFrame(i, SpdyStreamStatus.PROTOCOL_ERROR);
/*     */           
/* 184 */           paramChannelHandlerContext.writeAndFlush(localDefaultSpdyRstStreamFrame);
/*     */         }
/*     */       }
/*     */       else
/*     */       {
/*     */         Object localObject2;
/*     */         
/* 191 */         if (((SpdySynStreamFrame)localObject1).isTruncated()) {
/* 192 */           localObject2 = new DefaultSpdySynReplyFrame(i);
/* 193 */           ((SpdySynReplyFrame)localObject2).setLast(true);
/* 194 */           SpdyHeaders.setStatus(this.spdyVersion, (SpdyHeadersFrame)localObject2, HttpResponseStatus.REQUEST_HEADER_FIELDS_TOO_LARGE);
/*     */           
/*     */ 
/* 197 */           SpdyHeaders.setVersion(this.spdyVersion, (SpdyHeadersFrame)localObject2, HttpVersion.HTTP_1_0);
/* 198 */           paramChannelHandlerContext.writeAndFlush(localObject2);
/* 199 */           return;
/*     */         }
/*     */         try
/*     */         {
/* 203 */           localObject2 = createHttpRequest(this.spdyVersion, (SpdyHeadersFrame)localObject1);
/*     */           
/*     */ 
/* 206 */           SpdyHttpHeaders.setStreamId((HttpMessage)localObject2, i);
/*     */           
/* 208 */           if (((SpdySynStreamFrame)localObject1).isLast()) {
/* 209 */             paramList.add(localObject2);
/*     */           }
/*     */           else {
/* 212 */             putMessage(i, (FullHttpMessage)localObject2);
/*     */           }
/*     */           
/*     */         }
/*     */         catch (Exception localException1)
/*     */         {
/* 218 */           localObject4 = new DefaultSpdySynReplyFrame(i);
/* 219 */           ((SpdySynReplyFrame)localObject4).setLast(true);
/* 220 */           SpdyHeaders.setStatus(this.spdyVersion, (SpdyHeadersFrame)localObject4, HttpResponseStatus.BAD_REQUEST);
/* 221 */           SpdyHeaders.setVersion(this.spdyVersion, (SpdyHeadersFrame)localObject4, HttpVersion.HTTP_1_0);
/* 222 */           paramChannelHandlerContext.writeAndFlush(localObject4);
/*     */         }
/*     */       }
/*     */     }
/* 226 */     else if ((paramSpdyFrame instanceof SpdySynReplyFrame))
/*     */     {
/* 228 */       localObject1 = (SpdySynReplyFrame)paramSpdyFrame;
/* 229 */       i = ((SpdySynReplyFrame)localObject1).streamId();
/*     */       
/*     */       Object localObject3;
/*     */       
/* 233 */       if (((SpdySynReplyFrame)localObject1).isTruncated()) {
/* 234 */         localObject3 = new DefaultSpdyRstStreamFrame(i, SpdyStreamStatus.INTERNAL_ERROR);
/*     */         
/* 236 */         paramChannelHandlerContext.writeAndFlush(localObject3);
/* 237 */         return;
/*     */       }
/*     */       try
/*     */       {
/* 241 */         localObject3 = createHttpResponse(paramChannelHandlerContext, this.spdyVersion, (SpdyHeadersFrame)localObject1, this.validateHeaders);
/*     */         
/*     */ 
/*     */ 
/* 245 */         SpdyHttpHeaders.setStreamId((HttpMessage)localObject3, i);
/*     */         
/* 247 */         if (((SpdySynReplyFrame)localObject1).isLast()) {
/* 248 */           HttpHeaders.setContentLength((HttpMessage)localObject3, 0L);
/* 249 */           paramList.add(localObject3);
/*     */         }
/*     */         else {
/* 252 */           putMessage(i, (FullHttpMessage)localObject3);
/*     */         }
/*     */       }
/*     */       catch (Exception localException2)
/*     */       {
/* 257 */         localObject4 = new DefaultSpdyRstStreamFrame(i, SpdyStreamStatus.PROTOCOL_ERROR);
/*     */         
/* 259 */         paramChannelHandlerContext.writeAndFlush(localObject4);
/*     */       } } else { FullHttpMessage localFullHttpMessage;
/*     */       Object localObject6;
/* 262 */       if ((paramSpdyFrame instanceof SpdyHeadersFrame))
/*     */       {
/* 264 */         localObject1 = (SpdyHeadersFrame)paramSpdyFrame;
/* 265 */         i = ((SpdyHeadersFrame)localObject1).streamId();
/* 266 */         localFullHttpMessage = getMessage(i);
/*     */         
/*     */ 
/* 269 */         if (localFullHttpMessage == null) {
/* 270 */           return;
/*     */         }
/*     */         
/*     */ 
/* 274 */         if (!((SpdyHeadersFrame)localObject1).isTruncated()) {
/* 275 */           for (localObject4 = ((SpdyHeadersFrame)localObject1).headers().iterator(); ((Iterator)localObject4).hasNext();) { localObject6 = (Map.Entry)((Iterator)localObject4).next();
/* 276 */             localFullHttpMessage.headers().add((String)((Map.Entry)localObject6).getKey(), ((Map.Entry)localObject6).getValue());
/*     */           }
/*     */         }
/*     */         
/* 280 */         if (((SpdyHeadersFrame)localObject1).isLast()) {
/* 281 */           HttpHeaders.setContentLength(localFullHttpMessage, localFullHttpMessage.content().readableBytes());
/* 282 */           removeMessage(i);
/* 283 */           paramList.add(localFullHttpMessage);
/*     */         }
/*     */       }
/* 286 */       else if ((paramSpdyFrame instanceof SpdyDataFrame))
/*     */       {
/* 288 */         localObject1 = (SpdyDataFrame)paramSpdyFrame;
/* 289 */         i = ((SpdyDataFrame)localObject1).streamId();
/* 290 */         localFullHttpMessage = getMessage(i);
/*     */         
/*     */ 
/* 293 */         if (localFullHttpMessage == null) {
/* 294 */           return;
/*     */         }
/*     */         
/* 297 */         localObject4 = localFullHttpMessage.content();
/* 298 */         if (((ByteBuf)localObject4).readableBytes() > this.maxContentLength - ((SpdyDataFrame)localObject1).content().readableBytes()) {
/* 299 */           removeMessage(i);
/* 300 */           throw new TooLongFrameException("HTTP content length exceeded " + this.maxContentLength + " bytes.");
/*     */         }
/*     */         
/*     */ 
/* 304 */         localObject6 = ((SpdyDataFrame)localObject1).content();
/* 305 */         int k = ((ByteBuf)localObject6).readableBytes();
/* 306 */         ((ByteBuf)localObject4).writeBytes((ByteBuf)localObject6, ((ByteBuf)localObject6).readerIndex(), k);
/*     */         
/* 308 */         if (((SpdyDataFrame)localObject1).isLast()) {
/* 309 */           HttpHeaders.setContentLength(localFullHttpMessage, ((ByteBuf)localObject4).readableBytes());
/* 310 */           removeMessage(i);
/* 311 */           paramList.add(localFullHttpMessage);
/*     */         }
/*     */       }
/* 314 */       else if ((paramSpdyFrame instanceof SpdyRstStreamFrame))
/*     */       {
/* 316 */         localObject1 = (SpdyRstStreamFrame)paramSpdyFrame;
/* 317 */         i = ((SpdyRstStreamFrame)localObject1).streamId();
/* 318 */         removeMessage(i);
/*     */       }
/*     */     }
/*     */   }
/*     */   
/*     */   private static FullHttpRequest createHttpRequest(int paramInt, SpdyHeadersFrame paramSpdyHeadersFrame) throws Exception
/*     */   {
/* 325 */     SpdyHeaders localSpdyHeaders = paramSpdyHeadersFrame.headers();
/* 326 */     HttpMethod localHttpMethod = SpdyHeaders.getMethod(paramInt, paramSpdyHeadersFrame);
/* 327 */     String str1 = SpdyHeaders.getUrl(paramInt, paramSpdyHeadersFrame);
/* 328 */     HttpVersion localHttpVersion = SpdyHeaders.getVersion(paramInt, paramSpdyHeadersFrame);
/* 329 */     SpdyHeaders.removeMethod(paramInt, paramSpdyHeadersFrame);
/* 330 */     SpdyHeaders.removeUrl(paramInt, paramSpdyHeadersFrame);
/* 331 */     SpdyHeaders.removeVersion(paramInt, paramSpdyHeadersFrame);
/*     */     
/* 333 */     DefaultFullHttpRequest localDefaultFullHttpRequest = new DefaultFullHttpRequest(localHttpVersion, localHttpMethod, str1);
/*     */     
/*     */ 
/* 336 */     SpdyHeaders.removeScheme(paramInt, paramSpdyHeadersFrame);
/*     */     
/*     */ 
/* 339 */     String str2 = localSpdyHeaders.get(":host");
/* 340 */     localSpdyHeaders.remove(":host");
/* 341 */     localDefaultFullHttpRequest.headers().set("Host", str2);
/*     */     
/* 343 */     for (Map.Entry localEntry : paramSpdyHeadersFrame.headers()) {
/* 344 */       localDefaultFullHttpRequest.headers().add((String)localEntry.getKey(), localEntry.getValue());
/*     */     }
/*     */     
/*     */ 
/* 348 */     HttpHeaders.setKeepAlive(localDefaultFullHttpRequest, true);
/*     */     
/*     */ 
/* 351 */     localDefaultFullHttpRequest.headers().remove("Transfer-Encoding");
/*     */     
/* 353 */     return localDefaultFullHttpRequest;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   private static FullHttpResponse createHttpResponse(ChannelHandlerContext paramChannelHandlerContext, int paramInt, SpdyHeadersFrame paramSpdyHeadersFrame, boolean paramBoolean)
/*     */     throws Exception
/*     */   {
/* 361 */     HttpResponseStatus localHttpResponseStatus = SpdyHeaders.getStatus(paramInt, paramSpdyHeadersFrame);
/* 362 */     HttpVersion localHttpVersion = SpdyHeaders.getVersion(paramInt, paramSpdyHeadersFrame);
/* 363 */     SpdyHeaders.removeStatus(paramInt, paramSpdyHeadersFrame);
/* 364 */     SpdyHeaders.removeVersion(paramInt, paramSpdyHeadersFrame);
/*     */     
/* 366 */     DefaultFullHttpResponse localDefaultFullHttpResponse = new DefaultFullHttpResponse(localHttpVersion, localHttpResponseStatus, paramChannelHandlerContext.alloc().buffer(), paramBoolean);
/* 367 */     for (Map.Entry localEntry : paramSpdyHeadersFrame.headers()) {
/* 368 */       localDefaultFullHttpResponse.headers().add((String)localEntry.getKey(), localEntry.getValue());
/*     */     }
/*     */     
/*     */ 
/* 372 */     HttpHeaders.setKeepAlive(localDefaultFullHttpResponse, true);
/*     */     
/*     */ 
/* 375 */     localDefaultFullHttpResponse.headers().remove("Transfer-Encoding");
/* 376 */     localDefaultFullHttpResponse.headers().remove("Trailer");
/*     */     
/* 378 */     return localDefaultFullHttpResponse;
/*     */   }
/*     */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\io\netty\handler\codec\spdy\SpdyHttpDecoder.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */
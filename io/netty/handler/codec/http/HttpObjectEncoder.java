/*     */ package io.netty.handler.codec.http;
/*     */ 
/*     */ import io.netty.buffer.ByteBuf;
/*     */ import io.netty.buffer.ByteBufAllocator;
/*     */ import io.netty.buffer.Unpooled;
/*     */ import io.netty.channel.ChannelHandlerContext;
/*     */ import io.netty.channel.FileRegion;
/*     */ import io.netty.handler.codec.MessageToMessageEncoder;
/*     */ import io.netty.util.CharsetUtil;
/*     */ import io.netty.util.internal.StringUtil;
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
/*     */ public abstract class HttpObjectEncoder<H extends HttpMessage>
/*     */   extends MessageToMessageEncoder<Object>
/*     */ {
/*  44 */   private static final byte[] CRLF = { 13, 10 };
/*  45 */   private static final byte[] ZERO_CRLF = { 48, 13, 10 };
/*  46 */   private static final byte[] ZERO_CRLF_CRLF = { 48, 13, 10, 13, 10 };
/*  47 */   private static final ByteBuf CRLF_BUF = Unpooled.unreleasableBuffer(Unpooled.directBuffer(CRLF.length).writeBytes(CRLF));
/*  48 */   private static final ByteBuf ZERO_CRLF_CRLF_BUF = Unpooled.unreleasableBuffer(Unpooled.directBuffer(ZERO_CRLF_CRLF.length).writeBytes(ZERO_CRLF_CRLF));
/*     */   
/*     */   private static final int ST_INIT = 0;
/*     */   
/*     */   private static final int ST_CONTENT_NON_CHUNK = 1;
/*     */   
/*     */   private static final int ST_CONTENT_CHUNK = 2;
/*  55 */   private int state = 0;
/*     */   
/*     */   protected void encode(ChannelHandlerContext paramChannelHandlerContext, Object paramObject, List<Object> paramList)
/*     */     throws Exception
/*     */   {
/*  60 */     ByteBuf localByteBuf = null;
/*  61 */     if ((paramObject instanceof HttpMessage)) {
/*  62 */       if (this.state != 0) {
/*  63 */         throw new IllegalStateException("unexpected message type: " + StringUtil.simpleClassName(paramObject));
/*     */       }
/*     */       
/*     */ 
/*  67 */       HttpMessage localHttpMessage = (HttpMessage)paramObject;
/*     */       
/*  69 */       localByteBuf = paramChannelHandlerContext.alloc().buffer();
/*     */       
/*  71 */       encodeInitialLine(localByteBuf, localHttpMessage);
/*  72 */       HttpHeaders.encode(localHttpMessage.headers(), localByteBuf);
/*  73 */       localByteBuf.writeBytes(CRLF);
/*  74 */       this.state = (HttpHeaders.isTransferEncodingChunked(localHttpMessage) ? 2 : 1);
/*     */     }
/*  76 */     if (((paramObject instanceof HttpContent)) || ((paramObject instanceof ByteBuf)) || ((paramObject instanceof FileRegion))) {
/*  77 */       if (this.state == 0) {
/*  78 */         throw new IllegalStateException("unexpected message type: " + StringUtil.simpleClassName(paramObject));
/*     */       }
/*     */       
/*  81 */       long l = contentLength(paramObject);
/*  82 */       if (this.state == 1) {
/*  83 */         if (l > 0L) {
/*  84 */           if ((localByteBuf != null) && (localByteBuf.writableBytes() >= l) && ((paramObject instanceof HttpContent)))
/*     */           {
/*  86 */             localByteBuf.writeBytes(((HttpContent)paramObject).content());
/*  87 */             paramList.add(localByteBuf);
/*     */           } else {
/*  89 */             if (localByteBuf != null) {
/*  90 */               paramList.add(localByteBuf);
/*     */             }
/*  92 */             paramList.add(encodeAndRetain(paramObject));
/*     */           }
/*     */         }
/*  95 */         else if (localByteBuf != null) {
/*  96 */           paramList.add(localByteBuf);
/*     */         }
/*     */         else
/*     */         {
/* 100 */           paramList.add(Unpooled.EMPTY_BUFFER);
/*     */         }
/*     */         
/*     */ 
/* 104 */         if ((paramObject instanceof LastHttpContent)) {
/* 105 */           this.state = 0;
/*     */         }
/* 107 */       } else if (this.state == 2) {
/* 108 */         if (localByteBuf != null) {
/* 109 */           paramList.add(localByteBuf);
/*     */         }
/* 111 */         encodeChunkedContent(paramChannelHandlerContext, paramObject, l, paramList);
/*     */       } else {
/* 113 */         throw new Error();
/*     */       }
/*     */     }
/* 116 */     else if (localByteBuf != null) {
/* 117 */       paramList.add(localByteBuf);
/*     */     }
/*     */   }
/*     */   
/*     */   private void encodeChunkedContent(ChannelHandlerContext paramChannelHandlerContext, Object paramObject, long paramLong, List<Object> paramList) { Object localObject;
/*     */     ByteBuf localByteBuf;
/* 123 */     if (paramLong > 0L) {
/* 124 */       localObject = Long.toHexString(paramLong).getBytes(CharsetUtil.US_ASCII);
/* 125 */       localByteBuf = paramChannelHandlerContext.alloc().buffer(localObject.length + 2);
/* 126 */       localByteBuf.writeBytes((byte[])localObject);
/* 127 */       localByteBuf.writeBytes(CRLF);
/* 128 */       paramList.add(localByteBuf);
/* 129 */       paramList.add(encodeAndRetain(paramObject));
/* 130 */       paramList.add(CRLF_BUF.duplicate());
/*     */     }
/*     */     
/* 133 */     if ((paramObject instanceof LastHttpContent)) {
/* 134 */       localObject = ((LastHttpContent)paramObject).trailingHeaders();
/* 135 */       if (((HttpHeaders)localObject).isEmpty()) {
/* 136 */         paramList.add(ZERO_CRLF_CRLF_BUF.duplicate());
/*     */       } else {
/* 138 */         localByteBuf = paramChannelHandlerContext.alloc().buffer();
/* 139 */         localByteBuf.writeBytes(ZERO_CRLF);
/* 140 */         HttpHeaders.encode((HttpHeaders)localObject, localByteBuf);
/* 141 */         localByteBuf.writeBytes(CRLF);
/* 142 */         paramList.add(localByteBuf);
/*     */       }
/*     */       
/* 145 */       this.state = 0;
/*     */     }
/* 147 */     else if (paramLong == 0L)
/*     */     {
/*     */ 
/* 150 */       paramList.add(Unpooled.EMPTY_BUFFER);
/*     */     }
/*     */   }
/*     */   
/*     */   public boolean acceptOutboundMessage(Object paramObject)
/*     */     throws Exception
/*     */   {
/* 157 */     return ((paramObject instanceof HttpObject)) || ((paramObject instanceof ByteBuf)) || ((paramObject instanceof FileRegion));
/*     */   }
/*     */   
/*     */   private static Object encodeAndRetain(Object paramObject) {
/* 161 */     if ((paramObject instanceof ByteBuf)) {
/* 162 */       return ((ByteBuf)paramObject).retain();
/*     */     }
/* 164 */     if ((paramObject instanceof HttpContent)) {
/* 165 */       return ((HttpContent)paramObject).content().retain();
/*     */     }
/* 167 */     if ((paramObject instanceof FileRegion)) {
/* 168 */       return ((FileRegion)paramObject).retain();
/*     */     }
/* 170 */     throw new IllegalStateException("unexpected message type: " + StringUtil.simpleClassName(paramObject));
/*     */   }
/*     */   
/*     */   private static long contentLength(Object paramObject) {
/* 174 */     if ((paramObject instanceof HttpContent)) {
/* 175 */       return ((HttpContent)paramObject).content().readableBytes();
/*     */     }
/* 177 */     if ((paramObject instanceof ByteBuf)) {
/* 178 */       return ((ByteBuf)paramObject).readableBytes();
/*     */     }
/* 180 */     if ((paramObject instanceof FileRegion)) {
/* 181 */       return ((FileRegion)paramObject).count();
/*     */     }
/* 183 */     throw new IllegalStateException("unexpected message type: " + StringUtil.simpleClassName(paramObject));
/*     */   }
/*     */   
/*     */   @Deprecated
/*     */   protected static void encodeAscii(String paramString, ByteBuf paramByteBuf) {
/* 188 */     HttpHeaders.encodeAscii0(paramString, paramByteBuf);
/*     */   }
/*     */   
/*     */   protected abstract void encodeInitialLine(ByteBuf paramByteBuf, H paramH)
/*     */     throws Exception;
/*     */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\io\netty\handler\codec\http\HttpObjectEncoder.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */
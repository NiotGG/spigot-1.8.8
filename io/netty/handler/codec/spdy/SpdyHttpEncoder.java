/*     */ package io.netty.handler.codec.spdy;
/*     */ 
/*     */ import io.netty.buffer.ByteBuf;
/*     */ import io.netty.channel.ChannelHandlerContext;
/*     */ import io.netty.handler.codec.MessageToMessageEncoder;
/*     */ import io.netty.handler.codec.UnsupportedMessageTypeException;
/*     */ import io.netty.handler.codec.http.FullHttpMessage;
/*     */ import io.netty.handler.codec.http.FullHttpRequest;
/*     */ import io.netty.handler.codec.http.HttpContent;
/*     */ import io.netty.handler.codec.http.HttpHeaders;
/*     */ import io.netty.handler.codec.http.HttpMessage;
/*     */ import io.netty.handler.codec.http.HttpObject;
/*     */ import io.netty.handler.codec.http.HttpRequest;
/*     */ import io.netty.handler.codec.http.HttpResponse;
/*     */ import io.netty.handler.codec.http.LastHttpContent;
/*     */ import java.util.Iterator;
/*     */ import java.util.List;
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
/*     */ public class SpdyHttpEncoder
/*     */   extends MessageToMessageEncoder<HttpObject>
/*     */ {
/*     */   private final int spdyVersion;
/*     */   private int currentStreamId;
/*     */   
/*     */   public SpdyHttpEncoder(SpdyVersion paramSpdyVersion)
/*     */   {
/* 134 */     if (paramSpdyVersion == null) {
/* 135 */       throw new NullPointerException("version");
/*     */     }
/* 137 */     this.spdyVersion = paramSpdyVersion.getVersion();
/*     */   }
/*     */   
/*     */   protected void encode(ChannelHandlerContext paramChannelHandlerContext, HttpObject paramHttpObject, List<Object> paramList)
/*     */     throws Exception
/*     */   {
/* 143 */     int i = 0;
/* 144 */     boolean bool = false;
/*     */     Object localObject1;
/* 146 */     Object localObject2; if ((paramHttpObject instanceof HttpRequest))
/*     */     {
/* 148 */       localObject1 = (HttpRequest)paramHttpObject;
/* 149 */       localObject2 = createSynStreamFrame((HttpMessage)localObject1);
/* 150 */       paramList.add(localObject2);
/*     */       
/* 152 */       bool = ((SpdySynStreamFrame)localObject2).isLast();
/* 153 */       i = 1;
/*     */     }
/* 155 */     if ((paramHttpObject instanceof HttpResponse))
/*     */     {
/* 157 */       localObject1 = (HttpResponse)paramHttpObject;
/* 158 */       if (((HttpResponse)localObject1).headers().contains("X-SPDY-Associated-To-Stream-ID")) {
/* 159 */         localObject2 = createSynStreamFrame((HttpMessage)localObject1);
/* 160 */         bool = ((SpdySynStreamFrame)localObject2).isLast();
/* 161 */         paramList.add(localObject2);
/*     */       } else {
/* 163 */         localObject2 = createSynReplyFrame((HttpResponse)localObject1);
/* 164 */         bool = ((SpdySynReplyFrame)localObject2).isLast();
/* 165 */         paramList.add(localObject2);
/*     */       }
/*     */       
/* 168 */       i = 1;
/*     */     }
/* 170 */     if (((paramHttpObject instanceof HttpContent)) && (!bool))
/*     */     {
/* 172 */       localObject1 = (HttpContent)paramHttpObject;
/*     */       
/* 174 */       ((HttpContent)localObject1).content().retain();
/* 175 */       localObject2 = new DefaultSpdyDataFrame(this.currentStreamId, ((HttpContent)localObject1).content());
/* 176 */       ((SpdyDataFrame)localObject2).setLast(localObject1 instanceof LastHttpContent);
/* 177 */       if ((localObject1 instanceof LastHttpContent)) {
/* 178 */         LastHttpContent localLastHttpContent = (LastHttpContent)localObject1;
/* 179 */         HttpHeaders localHttpHeaders = localLastHttpContent.trailingHeaders();
/* 180 */         if (localHttpHeaders.isEmpty()) {
/* 181 */           paramList.add(localObject2);
/*     */         }
/*     */         else {
/* 184 */           DefaultSpdyHeadersFrame localDefaultSpdyHeadersFrame = new DefaultSpdyHeadersFrame(this.currentStreamId);
/* 185 */           for (Map.Entry localEntry : localHttpHeaders) {
/* 186 */             localDefaultSpdyHeadersFrame.headers().add((String)localEntry.getKey(), localEntry.getValue());
/*     */           }
/*     */           
/*     */ 
/* 190 */           paramList.add(localDefaultSpdyHeadersFrame);
/* 191 */           paramList.add(localObject2);
/*     */         }
/*     */       } else {
/* 194 */         paramList.add(localObject2);
/*     */       }
/*     */       
/* 197 */       i = 1;
/*     */     }
/*     */     
/* 200 */     if (i == 0) {
/* 201 */       throw new UnsupportedMessageTypeException(paramHttpObject, new Class[0]);
/*     */     }
/*     */   }
/*     */   
/*     */   private SpdySynStreamFrame createSynStreamFrame(HttpMessage paramHttpMessage)
/*     */     throws Exception
/*     */   {
/* 208 */     int i = SpdyHttpHeaders.getStreamId(paramHttpMessage);
/* 209 */     int j = SpdyHttpHeaders.getAssociatedToStreamId(paramHttpMessage);
/* 210 */     byte b = SpdyHttpHeaders.getPriority(paramHttpMessage);
/* 211 */     String str1 = SpdyHttpHeaders.getUrl(paramHttpMessage);
/* 212 */     String str2 = SpdyHttpHeaders.getScheme(paramHttpMessage);
/* 213 */     SpdyHttpHeaders.removeStreamId(paramHttpMessage);
/* 214 */     SpdyHttpHeaders.removeAssociatedToStreamId(paramHttpMessage);
/* 215 */     SpdyHttpHeaders.removePriority(paramHttpMessage);
/* 216 */     SpdyHttpHeaders.removeUrl(paramHttpMessage);
/* 217 */     SpdyHttpHeaders.removeScheme(paramHttpMessage);
/*     */     
/*     */ 
/*     */ 
/* 221 */     paramHttpMessage.headers().remove("Connection");
/* 222 */     paramHttpMessage.headers().remove("Keep-Alive");
/* 223 */     paramHttpMessage.headers().remove("Proxy-Connection");
/* 224 */     paramHttpMessage.headers().remove("Transfer-Encoding");
/*     */     
/* 226 */     DefaultSpdySynStreamFrame localDefaultSpdySynStreamFrame = new DefaultSpdySynStreamFrame(i, j, b);
/*     */     
/*     */ 
/*     */ 
/* 230 */     if ((paramHttpMessage instanceof FullHttpRequest)) {
/* 231 */       localObject = (HttpRequest)paramHttpMessage;
/* 232 */       SpdyHeaders.setMethod(this.spdyVersion, localDefaultSpdySynStreamFrame, ((HttpRequest)localObject).getMethod());
/* 233 */       SpdyHeaders.setUrl(this.spdyVersion, localDefaultSpdySynStreamFrame, ((HttpRequest)localObject).getUri());
/* 234 */       SpdyHeaders.setVersion(this.spdyVersion, localDefaultSpdySynStreamFrame, paramHttpMessage.getProtocolVersion());
/*     */     }
/* 236 */     if ((paramHttpMessage instanceof HttpResponse)) {
/* 237 */       localObject = (HttpResponse)paramHttpMessage;
/* 238 */       SpdyHeaders.setStatus(this.spdyVersion, localDefaultSpdySynStreamFrame, ((HttpResponse)localObject).getStatus());
/* 239 */       SpdyHeaders.setUrl(this.spdyVersion, localDefaultSpdySynStreamFrame, str1);
/* 240 */       SpdyHeaders.setVersion(this.spdyVersion, localDefaultSpdySynStreamFrame, paramHttpMessage.getProtocolVersion());
/* 241 */       localDefaultSpdySynStreamFrame.setUnidirectional(true);
/*     */     }
/*     */     
/*     */ 
/* 245 */     if (this.spdyVersion >= 3) {
/* 246 */       localObject = HttpHeaders.getHost(paramHttpMessage);
/* 247 */       paramHttpMessage.headers().remove("Host");
/* 248 */       SpdyHeaders.setHost(localDefaultSpdySynStreamFrame, (String)localObject);
/*     */     }
/*     */     
/*     */ 
/* 252 */     if (str2 == null) {
/* 253 */       str2 = "https";
/*     */     }
/* 255 */     SpdyHeaders.setScheme(this.spdyVersion, localDefaultSpdySynStreamFrame, str2);
/*     */     
/*     */ 
/* 258 */     for (Object localObject = paramHttpMessage.headers().iterator(); ((Iterator)localObject).hasNext();) { Map.Entry localEntry = (Map.Entry)((Iterator)localObject).next();
/* 259 */       localDefaultSpdySynStreamFrame.headers().add((String)localEntry.getKey(), localEntry.getValue());
/*     */     }
/* 261 */     this.currentStreamId = localDefaultSpdySynStreamFrame.streamId();
/* 262 */     localDefaultSpdySynStreamFrame.setLast(isLast(paramHttpMessage));
/*     */     
/* 264 */     return localDefaultSpdySynStreamFrame;
/*     */   }
/*     */   
/*     */   private SpdySynReplyFrame createSynReplyFrame(HttpResponse paramHttpResponse)
/*     */     throws Exception
/*     */   {
/* 270 */     int i = SpdyHttpHeaders.getStreamId(paramHttpResponse);
/* 271 */     SpdyHttpHeaders.removeStreamId(paramHttpResponse);
/*     */     
/*     */ 
/*     */ 
/* 275 */     paramHttpResponse.headers().remove("Connection");
/* 276 */     paramHttpResponse.headers().remove("Keep-Alive");
/* 277 */     paramHttpResponse.headers().remove("Proxy-Connection");
/* 278 */     paramHttpResponse.headers().remove("Transfer-Encoding");
/*     */     
/* 280 */     DefaultSpdySynReplyFrame localDefaultSpdySynReplyFrame = new DefaultSpdySynReplyFrame(i);
/*     */     
/*     */ 
/* 283 */     SpdyHeaders.setStatus(this.spdyVersion, localDefaultSpdySynReplyFrame, paramHttpResponse.getStatus());
/* 284 */     SpdyHeaders.setVersion(this.spdyVersion, localDefaultSpdySynReplyFrame, paramHttpResponse.getProtocolVersion());
/*     */     
/*     */ 
/* 287 */     for (Map.Entry localEntry : paramHttpResponse.headers()) {
/* 288 */       localDefaultSpdySynReplyFrame.headers().add((String)localEntry.getKey(), localEntry.getValue());
/*     */     }
/*     */     
/* 291 */     this.currentStreamId = i;
/* 292 */     localDefaultSpdySynReplyFrame.setLast(isLast(paramHttpResponse));
/*     */     
/* 294 */     return localDefaultSpdySynReplyFrame;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   private static boolean isLast(HttpMessage paramHttpMessage)
/*     */   {
/* 304 */     if ((paramHttpMessage instanceof FullHttpMessage)) {
/* 305 */       FullHttpMessage localFullHttpMessage = (FullHttpMessage)paramHttpMessage;
/* 306 */       if ((localFullHttpMessage.trailingHeaders().isEmpty()) && (!localFullHttpMessage.content().isReadable())) {
/* 307 */         return true;
/*     */       }
/*     */     }
/*     */     
/* 311 */     return false;
/*     */   }
/*     */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\io\netty\handler\codec\spdy\SpdyHttpEncoder.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */
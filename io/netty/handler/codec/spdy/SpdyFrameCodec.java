/*     */ package io.netty.handler.codec.spdy;
/*     */ 
/*     */ import io.netty.buffer.ByteBuf;
/*     */ import io.netty.channel.Channel;
/*     */ import io.netty.channel.ChannelFuture;
/*     */ import io.netty.channel.ChannelFutureListener;
/*     */ import io.netty.channel.ChannelHandlerContext;
/*     */ import io.netty.channel.ChannelOutboundHandler;
/*     */ import io.netty.channel.ChannelPromise;
/*     */ import io.netty.handler.codec.ByteToMessageDecoder;
/*     */ import io.netty.handler.codec.UnsupportedMessageTypeException;
/*     */ import java.net.SocketAddress;
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
/*     */ public class SpdyFrameCodec
/*     */   extends ByteToMessageDecoder
/*     */   implements SpdyFrameDecoderDelegate, ChannelOutboundHandler
/*     */ {
/*  37 */   private static final SpdyProtocolException INVALID_FRAME = new SpdyProtocolException("Received invalid frame");
/*     */   
/*     */ 
/*     */   private final SpdyFrameDecoder spdyFrameDecoder;
/*     */   
/*     */ 
/*     */   private final SpdyFrameEncoder spdyFrameEncoder;
/*     */   
/*     */   private final SpdyHeaderBlockDecoder spdyHeaderBlockDecoder;
/*     */   
/*     */   private final SpdyHeaderBlockEncoder spdyHeaderBlockEncoder;
/*     */   
/*     */   private SpdyHeadersFrame spdyHeadersFrame;
/*     */   
/*     */   private SpdySettingsFrame spdySettingsFrame;
/*     */   
/*     */   private ChannelHandlerContext ctx;
/*     */   
/*     */ 
/*     */   public SpdyFrameCodec(SpdyVersion paramSpdyVersion)
/*     */   {
/*  58 */     this(paramSpdyVersion, 8192, 16384, 6, 15, 8);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public SpdyFrameCodec(SpdyVersion paramSpdyVersion, int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5)
/*     */   {
/*  67 */     this(paramSpdyVersion, paramInt1, SpdyHeaderBlockDecoder.newInstance(paramSpdyVersion, paramInt2), SpdyHeaderBlockEncoder.newInstance(paramSpdyVersion, paramInt3, paramInt4, paramInt5));
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   protected SpdyFrameCodec(SpdyVersion paramSpdyVersion, int paramInt, SpdyHeaderBlockDecoder paramSpdyHeaderBlockDecoder, SpdyHeaderBlockEncoder paramSpdyHeaderBlockEncoder)
/*     */   {
/*  74 */     this.spdyFrameDecoder = new SpdyFrameDecoder(paramSpdyVersion, this, paramInt);
/*  75 */     this.spdyFrameEncoder = new SpdyFrameEncoder(paramSpdyVersion);
/*  76 */     this.spdyHeaderBlockDecoder = paramSpdyHeaderBlockDecoder;
/*  77 */     this.spdyHeaderBlockEncoder = paramSpdyHeaderBlockEncoder;
/*     */   }
/*     */   
/*     */   public void handlerAdded(ChannelHandlerContext paramChannelHandlerContext) throws Exception
/*     */   {
/*  82 */     super.handlerAdded(paramChannelHandlerContext);
/*  83 */     this.ctx = paramChannelHandlerContext;
/*  84 */     paramChannelHandlerContext.channel().closeFuture().addListener(new ChannelFutureListener()
/*     */     {
/*     */       public void operationComplete(ChannelFuture paramAnonymousChannelFuture) throws Exception {
/*  87 */         SpdyFrameCodec.this.spdyHeaderBlockDecoder.end();
/*  88 */         SpdyFrameCodec.this.spdyHeaderBlockEncoder.end();
/*     */       }
/*     */     });
/*     */   }
/*     */   
/*     */   protected void decode(ChannelHandlerContext paramChannelHandlerContext, ByteBuf paramByteBuf, List<Object> paramList) throws Exception
/*     */   {
/*  95 */     this.spdyFrameDecoder.decode(paramByteBuf);
/*     */   }
/*     */   
/*     */   public void bind(ChannelHandlerContext paramChannelHandlerContext, SocketAddress paramSocketAddress, ChannelPromise paramChannelPromise) throws Exception
/*     */   {
/* 100 */     paramChannelHandlerContext.bind(paramSocketAddress, paramChannelPromise);
/*     */   }
/*     */   
/*     */   public void connect(ChannelHandlerContext paramChannelHandlerContext, SocketAddress paramSocketAddress1, SocketAddress paramSocketAddress2, ChannelPromise paramChannelPromise)
/*     */     throws Exception
/*     */   {
/* 106 */     paramChannelHandlerContext.connect(paramSocketAddress1, paramSocketAddress2, paramChannelPromise);
/*     */   }
/*     */   
/*     */   public void disconnect(ChannelHandlerContext paramChannelHandlerContext, ChannelPromise paramChannelPromise) throws Exception
/*     */   {
/* 111 */     paramChannelHandlerContext.disconnect(paramChannelPromise);
/*     */   }
/*     */   
/*     */   public void close(ChannelHandlerContext paramChannelHandlerContext, ChannelPromise paramChannelPromise) throws Exception
/*     */   {
/* 116 */     paramChannelHandlerContext.close(paramChannelPromise);
/*     */   }
/*     */   
/*     */   public void deregister(ChannelHandlerContext paramChannelHandlerContext, ChannelPromise paramChannelPromise) throws Exception
/*     */   {
/* 121 */     paramChannelHandlerContext.deregister(paramChannelPromise);
/*     */   }
/*     */   
/*     */   public void read(ChannelHandlerContext paramChannelHandlerContext) throws Exception
/*     */   {
/* 126 */     paramChannelHandlerContext.read();
/*     */   }
/*     */   
/*     */   public void flush(ChannelHandlerContext paramChannelHandlerContext) throws Exception
/*     */   {
/* 131 */     paramChannelHandlerContext.flush();
/*     */   }
/*     */   
/*     */   public void write(ChannelHandlerContext paramChannelHandlerContext, Object paramObject, ChannelPromise paramChannelPromise) throws Exception
/*     */   {
/*     */     Object localObject1;
/*     */     ByteBuf localByteBuf1;
/* 138 */     if ((paramObject instanceof SpdyDataFrame))
/*     */     {
/* 140 */       localObject1 = (SpdyDataFrame)paramObject;
/* 141 */       localByteBuf1 = this.spdyFrameEncoder.encodeDataFrame(paramChannelHandlerContext.alloc(), ((SpdyDataFrame)localObject1).streamId(), ((SpdyDataFrame)localObject1).isLast(), ((SpdyDataFrame)localObject1).content());
/*     */       
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/* 147 */       ((SpdyDataFrame)localObject1).release();
/* 148 */       paramChannelHandlerContext.write(localByteBuf1, paramChannelPromise);
/*     */     } else { ByteBuf localByteBuf2;
/* 150 */       if ((paramObject instanceof SpdySynStreamFrame))
/*     */       {
/* 152 */         localObject1 = (SpdySynStreamFrame)paramObject;
/* 153 */         localByteBuf2 = this.spdyHeaderBlockEncoder.encode((SpdyHeadersFrame)localObject1);
/*     */         try {
/* 155 */           localByteBuf1 = this.spdyFrameEncoder.encodeSynStreamFrame(paramChannelHandlerContext.alloc(), ((SpdySynStreamFrame)localObject1).streamId(), ((SpdySynStreamFrame)localObject1).associatedStreamId(), ((SpdySynStreamFrame)localObject1).priority(), ((SpdySynStreamFrame)localObject1).isLast(), ((SpdySynStreamFrame)localObject1).isUnidirectional(), localByteBuf2);
/*     */ 
/*     */ 
/*     */ 
/*     */         }
/*     */         finally
/*     */         {
/*     */ 
/*     */ 
/*     */ 
/* 165 */           localByteBuf2.release();
/*     */         }
/* 167 */         paramChannelHandlerContext.write(localByteBuf1, paramChannelPromise);
/*     */       }
/* 169 */       else if ((paramObject instanceof SpdySynReplyFrame))
/*     */       {
/* 171 */         localObject1 = (SpdySynReplyFrame)paramObject;
/* 172 */         localByteBuf2 = this.spdyHeaderBlockEncoder.encode((SpdyHeadersFrame)localObject1);
/*     */         try {
/* 174 */           localByteBuf1 = this.spdyFrameEncoder.encodeSynReplyFrame(paramChannelHandlerContext.alloc(), ((SpdySynReplyFrame)localObject1).streamId(), ((SpdySynReplyFrame)localObject1).isLast(), localByteBuf2);
/*     */ 
/*     */ 
/*     */         }
/*     */         finally
/*     */         {
/*     */ 
/* 181 */           localByteBuf2.release();
/*     */         }
/* 183 */         paramChannelHandlerContext.write(localByteBuf1, paramChannelPromise);
/*     */       }
/* 185 */       else if ((paramObject instanceof SpdyRstStreamFrame))
/*     */       {
/* 187 */         localObject1 = (SpdyRstStreamFrame)paramObject;
/* 188 */         localByteBuf1 = this.spdyFrameEncoder.encodeRstStreamFrame(paramChannelHandlerContext.alloc(), ((SpdyRstStreamFrame)localObject1).streamId(), ((SpdyRstStreamFrame)localObject1).status().code());
/*     */         
/*     */ 
/*     */ 
/*     */ 
/* 193 */         paramChannelHandlerContext.write(localByteBuf1, paramChannelPromise);
/*     */       }
/* 195 */       else if ((paramObject instanceof SpdySettingsFrame))
/*     */       {
/* 197 */         localObject1 = (SpdySettingsFrame)paramObject;
/* 198 */         localByteBuf1 = this.spdyFrameEncoder.encodeSettingsFrame(paramChannelHandlerContext.alloc(), (SpdySettingsFrame)localObject1);
/*     */         
/*     */ 
/*     */ 
/* 202 */         paramChannelHandlerContext.write(localByteBuf1, paramChannelPromise);
/*     */       }
/* 204 */       else if ((paramObject instanceof SpdyPingFrame))
/*     */       {
/* 206 */         localObject1 = (SpdyPingFrame)paramObject;
/* 207 */         localByteBuf1 = this.spdyFrameEncoder.encodePingFrame(paramChannelHandlerContext.alloc(), ((SpdyPingFrame)localObject1).id());
/*     */         
/*     */ 
/*     */ 
/* 211 */         paramChannelHandlerContext.write(localByteBuf1, paramChannelPromise);
/*     */       }
/* 213 */       else if ((paramObject instanceof SpdyGoAwayFrame))
/*     */       {
/* 215 */         localObject1 = (SpdyGoAwayFrame)paramObject;
/* 216 */         localByteBuf1 = this.spdyFrameEncoder.encodeGoAwayFrame(paramChannelHandlerContext.alloc(), ((SpdyGoAwayFrame)localObject1).lastGoodStreamId(), ((SpdyGoAwayFrame)localObject1).status().code());
/*     */         
/*     */ 
/*     */ 
/*     */ 
/* 221 */         paramChannelHandlerContext.write(localByteBuf1, paramChannelPromise);
/*     */       }
/* 223 */       else if ((paramObject instanceof SpdyHeadersFrame))
/*     */       {
/* 225 */         localObject1 = (SpdyHeadersFrame)paramObject;
/* 226 */         localByteBuf2 = this.spdyHeaderBlockEncoder.encode((SpdyHeadersFrame)localObject1);
/*     */         try {
/* 228 */           localByteBuf1 = this.spdyFrameEncoder.encodeHeadersFrame(paramChannelHandlerContext.alloc(), ((SpdyHeadersFrame)localObject1).streamId(), ((SpdyHeadersFrame)localObject1).isLast(), localByteBuf2);
/*     */ 
/*     */ 
/*     */         }
/*     */         finally
/*     */         {
/*     */ 
/* 235 */           localByteBuf2.release();
/*     */         }
/* 237 */         paramChannelHandlerContext.write(localByteBuf1, paramChannelPromise);
/*     */       }
/* 239 */       else if ((paramObject instanceof SpdyWindowUpdateFrame))
/*     */       {
/* 241 */         localObject1 = (SpdyWindowUpdateFrame)paramObject;
/* 242 */         localByteBuf1 = this.spdyFrameEncoder.encodeWindowUpdateFrame(paramChannelHandlerContext.alloc(), ((SpdyWindowUpdateFrame)localObject1).streamId(), ((SpdyWindowUpdateFrame)localObject1).deltaWindowSize());
/*     */         
/*     */ 
/*     */ 
/*     */ 
/* 247 */         paramChannelHandlerContext.write(localByteBuf1, paramChannelPromise);
/*     */       } else {
/* 249 */         throw new UnsupportedMessageTypeException(paramObject, new Class[0]);
/*     */       }
/*     */     }
/*     */   }
/*     */   
/*     */   public void readDataFrame(int paramInt, boolean paramBoolean, ByteBuf paramByteBuf) {
/* 255 */     DefaultSpdyDataFrame localDefaultSpdyDataFrame = new DefaultSpdyDataFrame(paramInt, paramByteBuf);
/* 256 */     localDefaultSpdyDataFrame.setLast(paramBoolean);
/* 257 */     this.ctx.fireChannelRead(localDefaultSpdyDataFrame);
/*     */   }
/*     */   
/*     */ 
/*     */   public void readSynStreamFrame(int paramInt1, int paramInt2, byte paramByte, boolean paramBoolean1, boolean paramBoolean2)
/*     */   {
/* 263 */     DefaultSpdySynStreamFrame localDefaultSpdySynStreamFrame = new DefaultSpdySynStreamFrame(paramInt1, paramInt2, paramByte);
/* 264 */     localDefaultSpdySynStreamFrame.setLast(paramBoolean1);
/* 265 */     localDefaultSpdySynStreamFrame.setUnidirectional(paramBoolean2);
/* 266 */     this.spdyHeadersFrame = localDefaultSpdySynStreamFrame;
/*     */   }
/*     */   
/*     */   public void readSynReplyFrame(int paramInt, boolean paramBoolean)
/*     */   {
/* 271 */     DefaultSpdySynReplyFrame localDefaultSpdySynReplyFrame = new DefaultSpdySynReplyFrame(paramInt);
/* 272 */     localDefaultSpdySynReplyFrame.setLast(paramBoolean);
/* 273 */     this.spdyHeadersFrame = localDefaultSpdySynReplyFrame;
/*     */   }
/*     */   
/*     */   public void readRstStreamFrame(int paramInt1, int paramInt2)
/*     */   {
/* 278 */     DefaultSpdyRstStreamFrame localDefaultSpdyRstStreamFrame = new DefaultSpdyRstStreamFrame(paramInt1, paramInt2);
/* 279 */     this.ctx.fireChannelRead(localDefaultSpdyRstStreamFrame);
/*     */   }
/*     */   
/*     */   public void readSettingsFrame(boolean paramBoolean)
/*     */   {
/* 284 */     this.spdySettingsFrame = new DefaultSpdySettingsFrame();
/* 285 */     this.spdySettingsFrame.setClearPreviouslyPersistedSettings(paramBoolean);
/*     */   }
/*     */   
/*     */   public void readSetting(int paramInt1, int paramInt2, boolean paramBoolean1, boolean paramBoolean2)
/*     */   {
/* 290 */     this.spdySettingsFrame.setValue(paramInt1, paramInt2, paramBoolean1, paramBoolean2);
/*     */   }
/*     */   
/*     */   public void readSettingsEnd()
/*     */   {
/* 295 */     SpdySettingsFrame localSpdySettingsFrame = this.spdySettingsFrame;
/* 296 */     this.spdySettingsFrame = null;
/* 297 */     this.ctx.fireChannelRead(localSpdySettingsFrame);
/*     */   }
/*     */   
/*     */   public void readPingFrame(int paramInt)
/*     */   {
/* 302 */     DefaultSpdyPingFrame localDefaultSpdyPingFrame = new DefaultSpdyPingFrame(paramInt);
/* 303 */     this.ctx.fireChannelRead(localDefaultSpdyPingFrame);
/*     */   }
/*     */   
/*     */   public void readGoAwayFrame(int paramInt1, int paramInt2)
/*     */   {
/* 308 */     DefaultSpdyGoAwayFrame localDefaultSpdyGoAwayFrame = new DefaultSpdyGoAwayFrame(paramInt1, paramInt2);
/* 309 */     this.ctx.fireChannelRead(localDefaultSpdyGoAwayFrame);
/*     */   }
/*     */   
/*     */   public void readHeadersFrame(int paramInt, boolean paramBoolean)
/*     */   {
/* 314 */     this.spdyHeadersFrame = new DefaultSpdyHeadersFrame(paramInt);
/* 315 */     this.spdyHeadersFrame.setLast(paramBoolean);
/*     */   }
/*     */   
/*     */   public void readWindowUpdateFrame(int paramInt1, int paramInt2)
/*     */   {
/* 320 */     DefaultSpdyWindowUpdateFrame localDefaultSpdyWindowUpdateFrame = new DefaultSpdyWindowUpdateFrame(paramInt1, paramInt2);
/* 321 */     this.ctx.fireChannelRead(localDefaultSpdyWindowUpdateFrame);
/*     */   }
/*     */   
/*     */   public void readHeaderBlock(ByteBuf paramByteBuf)
/*     */   {
/*     */     try {
/* 327 */       this.spdyHeaderBlockDecoder.decode(paramByteBuf, this.spdyHeadersFrame);
/*     */     } catch (Exception localException) {
/* 329 */       this.ctx.fireExceptionCaught(localException);
/*     */     } finally {
/* 331 */       paramByteBuf.release();
/*     */     }
/*     */   }
/*     */   
/*     */   public void readHeaderBlockEnd()
/*     */   {
/* 337 */     SpdyHeadersFrame localSpdyHeadersFrame = null;
/*     */     try {
/* 339 */       this.spdyHeaderBlockDecoder.endHeaderBlock(this.spdyHeadersFrame);
/* 340 */       localSpdyHeadersFrame = this.spdyHeadersFrame;
/* 341 */       this.spdyHeadersFrame = null;
/*     */     } catch (Exception localException) {
/* 343 */       this.ctx.fireExceptionCaught(localException);
/*     */     }
/* 345 */     if (localSpdyHeadersFrame != null) {
/* 346 */       this.ctx.fireChannelRead(localSpdyHeadersFrame);
/*     */     }
/*     */   }
/*     */   
/*     */   public void readFrameError(String paramString)
/*     */   {
/* 352 */     this.ctx.fireExceptionCaught(INVALID_FRAME);
/*     */   }
/*     */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\io\netty\handler\codec\spdy\SpdyFrameCodec.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */
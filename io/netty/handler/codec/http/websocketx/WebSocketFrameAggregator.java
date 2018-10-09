/*     */ package io.netty.handler.codec.http.websocketx;
/*     */ 
/*     */ import io.netty.buffer.ByteBuf;
/*     */ import io.netty.buffer.ByteBufAllocator;
/*     */ import io.netty.buffer.CompositeByteBuf;
/*     */ import io.netty.channel.ChannelHandlerContext;
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
/*     */ public class WebSocketFrameAggregator
/*     */   extends MessageToMessageDecoder<WebSocketFrame>
/*     */ {
/*     */   private final int maxFrameSize;
/*     */   private WebSocketFrame currentFrame;
/*     */   private boolean tooLongFrameFound;
/*     */   
/*     */   public WebSocketFrameAggregator(int paramInt)
/*     */   {
/*  44 */     if (paramInt < 1) {
/*  45 */       throw new IllegalArgumentException("maxFrameSize must be > 0");
/*     */     }
/*  47 */     this.maxFrameSize = paramInt;
/*     */   }
/*     */   
/*     */   protected void decode(ChannelHandlerContext paramChannelHandlerContext, WebSocketFrame paramWebSocketFrame, List<Object> paramList) throws Exception {
/*     */     CompositeByteBuf localCompositeByteBuf;
/*  52 */     if (this.currentFrame == null) {
/*  53 */       this.tooLongFrameFound = false;
/*  54 */       if (paramWebSocketFrame.isFinalFragment()) {
/*  55 */         paramList.add(paramWebSocketFrame.retain());
/*  56 */         return;
/*     */       }
/*  58 */       localCompositeByteBuf = paramChannelHandlerContext.alloc().compositeBuffer().addComponent(paramWebSocketFrame.content().retain());
/*  59 */       localCompositeByteBuf.writerIndex(localCompositeByteBuf.writerIndex() + paramWebSocketFrame.content().readableBytes());
/*     */       
/*  61 */       if ((paramWebSocketFrame instanceof TextWebSocketFrame)) {
/*  62 */         this.currentFrame = new TextWebSocketFrame(true, paramWebSocketFrame.rsv(), localCompositeByteBuf);
/*  63 */       } else if ((paramWebSocketFrame instanceof BinaryWebSocketFrame)) {
/*  64 */         this.currentFrame = new BinaryWebSocketFrame(true, paramWebSocketFrame.rsv(), localCompositeByteBuf);
/*     */       } else {
/*  66 */         localCompositeByteBuf.release();
/*  67 */         throw new IllegalStateException("WebSocket frame was not of type TextWebSocketFrame or BinaryWebSocketFrame");
/*     */       }
/*     */       
/*  70 */       return;
/*     */     }
/*  72 */     if ((paramWebSocketFrame instanceof ContinuationWebSocketFrame)) {
/*  73 */       if (this.tooLongFrameFound) {
/*  74 */         if (paramWebSocketFrame.isFinalFragment()) {
/*  75 */           this.currentFrame = null;
/*     */         }
/*  77 */         return;
/*     */       }
/*  79 */       localCompositeByteBuf = (CompositeByteBuf)this.currentFrame.content();
/*  80 */       if (localCompositeByteBuf.readableBytes() > this.maxFrameSize - paramWebSocketFrame.content().readableBytes())
/*     */       {
/*  82 */         this.currentFrame.release();
/*  83 */         this.tooLongFrameFound = true;
/*  84 */         throw new TooLongFrameException("WebSocketFrame length exceeded " + localCompositeByteBuf + " bytes.");
/*     */       }
/*     */       
/*     */ 
/*  88 */       localCompositeByteBuf.addComponent(paramWebSocketFrame.content().retain());
/*  89 */       localCompositeByteBuf.writerIndex(localCompositeByteBuf.writerIndex() + paramWebSocketFrame.content().readableBytes());
/*     */       
/*  91 */       if (paramWebSocketFrame.isFinalFragment()) {
/*  92 */         WebSocketFrame localWebSocketFrame = this.currentFrame;
/*  93 */         this.currentFrame = null;
/*  94 */         paramList.add(localWebSocketFrame);
/*  95 */         return;
/*     */       }
/*  97 */       return;
/*     */     }
/*     */     
/*     */ 
/*     */ 
/* 102 */     paramList.add(paramWebSocketFrame.retain());
/*     */   }
/*     */   
/*     */   public void channelInactive(ChannelHandlerContext paramChannelHandlerContext) throws Exception
/*     */   {
/* 107 */     super.channelInactive(paramChannelHandlerContext);
/*     */     
/* 109 */     if (this.currentFrame != null) {
/* 110 */       this.currentFrame.release();
/* 111 */       this.currentFrame = null;
/*     */     }
/*     */   }
/*     */   
/*     */   public void handlerRemoved(ChannelHandlerContext paramChannelHandlerContext) throws Exception
/*     */   {
/* 117 */     super.handlerRemoved(paramChannelHandlerContext);
/*     */     
/*     */ 
/* 120 */     if (this.currentFrame != null) {
/* 121 */       this.currentFrame.release();
/* 122 */       this.currentFrame = null;
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\io\netty\handler\codec\http\websocketx\WebSocketFrameAggregator.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */
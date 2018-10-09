/*     */ package io.netty.handler.codec.http.websocketx;
/*     */ 
/*     */ import io.netty.buffer.ByteBuf;
/*     */ import io.netty.buffer.Unpooled;
/*     */ import io.netty.util.CharsetUtil;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class ContinuationWebSocketFrame
/*     */   extends WebSocketFrame
/*     */ {
/*     */   public ContinuationWebSocketFrame()
/*     */   {
/*  32 */     this(Unpooled.buffer(0));
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public ContinuationWebSocketFrame(ByteBuf paramByteBuf)
/*     */   {
/*  42 */     super(paramByteBuf);
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
/*     */   public ContinuationWebSocketFrame(boolean paramBoolean, int paramInt, ByteBuf paramByteBuf)
/*     */   {
/*  56 */     super(paramBoolean, paramInt, paramByteBuf);
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
/*     */   public ContinuationWebSocketFrame(boolean paramBoolean, int paramInt, String paramString)
/*     */   {
/*  70 */     this(paramBoolean, paramInt, fromText(paramString));
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public String text()
/*     */   {
/*  77 */     return content().toString(CharsetUtil.UTF_8);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   private static ByteBuf fromText(String paramString)
/*     */   {
/*  87 */     if ((paramString == null) || (paramString.isEmpty())) {
/*  88 */       return Unpooled.EMPTY_BUFFER;
/*     */     }
/*  90 */     return Unpooled.copiedBuffer(paramString, CharsetUtil.UTF_8);
/*     */   }
/*     */   
/*     */ 
/*     */   public ContinuationWebSocketFrame copy()
/*     */   {
/*  96 */     return new ContinuationWebSocketFrame(isFinalFragment(), rsv(), content().copy());
/*     */   }
/*     */   
/*     */   public ContinuationWebSocketFrame duplicate()
/*     */   {
/* 101 */     return new ContinuationWebSocketFrame(isFinalFragment(), rsv(), content().duplicate());
/*     */   }
/*     */   
/*     */   public ContinuationWebSocketFrame retain()
/*     */   {
/* 106 */     super.retain();
/* 107 */     return this;
/*     */   }
/*     */   
/*     */   public ContinuationWebSocketFrame retain(int paramInt)
/*     */   {
/* 112 */     super.retain(paramInt);
/* 113 */     return this;
/*     */   }
/*     */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\io\netty\handler\codec\http\websocketx\ContinuationWebSocketFrame.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */
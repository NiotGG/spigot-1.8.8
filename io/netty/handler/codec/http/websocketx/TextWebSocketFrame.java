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
/*     */ public class TextWebSocketFrame
/*     */   extends WebSocketFrame
/*     */ {
/*     */   public TextWebSocketFrame()
/*     */   {
/*  31 */     super(Unpooled.buffer(0));
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public TextWebSocketFrame(String paramString)
/*     */   {
/*  41 */     super(fromText(paramString));
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public TextWebSocketFrame(ByteBuf paramByteBuf)
/*     */   {
/*  51 */     super(paramByteBuf);
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
/*     */   public TextWebSocketFrame(boolean paramBoolean, int paramInt, String paramString)
/*     */   {
/*  65 */     super(paramBoolean, paramInt, fromText(paramString));
/*     */   }
/*     */   
/*     */   private static ByteBuf fromText(String paramString) {
/*  69 */     if ((paramString == null) || (paramString.isEmpty())) {
/*  70 */       return Unpooled.EMPTY_BUFFER;
/*     */     }
/*  72 */     return Unpooled.copiedBuffer(paramString, CharsetUtil.UTF_8);
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
/*     */   public TextWebSocketFrame(boolean paramBoolean, int paramInt, ByteBuf paramByteBuf)
/*     */   {
/*  87 */     super(paramBoolean, paramInt, paramByteBuf);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public String text()
/*     */   {
/*  94 */     return content().toString(CharsetUtil.UTF_8);
/*     */   }
/*     */   
/*     */   public TextWebSocketFrame copy()
/*     */   {
/*  99 */     return new TextWebSocketFrame(isFinalFragment(), rsv(), content().copy());
/*     */   }
/*     */   
/*     */   public TextWebSocketFrame duplicate()
/*     */   {
/* 104 */     return new TextWebSocketFrame(isFinalFragment(), rsv(), content().duplicate());
/*     */   }
/*     */   
/*     */   public TextWebSocketFrame retain()
/*     */   {
/* 109 */     super.retain();
/* 110 */     return this;
/*     */   }
/*     */   
/*     */   public TextWebSocketFrame retain(int paramInt)
/*     */   {
/* 115 */     super.retain(paramInt);
/* 116 */     return this;
/*     */   }
/*     */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\io\netty\handler\codec\http\websocketx\TextWebSocketFrame.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */
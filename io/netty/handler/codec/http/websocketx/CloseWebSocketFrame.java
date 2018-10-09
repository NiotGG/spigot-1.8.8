/*     */ package io.netty.handler.codec.http.websocketx;
/*     */ 
/*     */ import io.netty.buffer.ByteBuf;
/*     */ import io.netty.buffer.Unpooled;
/*     */ import io.netty.util.CharsetUtil;
/*     */ import io.netty.util.internal.EmptyArrays;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class CloseWebSocketFrame
/*     */   extends WebSocketFrame
/*     */ {
/*     */   public CloseWebSocketFrame()
/*     */   {
/*  32 */     super(Unpooled.buffer(0));
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
/*     */   public CloseWebSocketFrame(int paramInt, String paramString)
/*     */   {
/*  45 */     this(true, 0, paramInt, paramString);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public CloseWebSocketFrame(boolean paramBoolean, int paramInt)
/*     */   {
/*  57 */     this(paramBoolean, paramInt, Unpooled.buffer(0));
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
/*     */ 
/*     */ 
/*     */   public CloseWebSocketFrame(boolean paramBoolean, int paramInt1, int paramInt2, String paramString)
/*     */   {
/*  74 */     super(paramBoolean, paramInt1, newBinaryData(paramInt2, paramString));
/*     */   }
/*     */   
/*     */   private static ByteBuf newBinaryData(int paramInt, String paramString) {
/*  78 */     byte[] arrayOfByte = EmptyArrays.EMPTY_BYTES;
/*  79 */     if (paramString != null) {
/*  80 */       arrayOfByte = paramString.getBytes(CharsetUtil.UTF_8);
/*     */     }
/*     */     
/*  83 */     ByteBuf localByteBuf = Unpooled.buffer(2 + arrayOfByte.length);
/*  84 */     localByteBuf.writeShort(paramInt);
/*  85 */     if (arrayOfByte.length > 0) {
/*  86 */       localByteBuf.writeBytes(arrayOfByte);
/*     */     }
/*     */     
/*  89 */     localByteBuf.readerIndex(0);
/*  90 */     return localByteBuf;
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
/*     */   public CloseWebSocketFrame(boolean paramBoolean, int paramInt, ByteBuf paramByteBuf)
/*     */   {
/* 104 */     super(paramBoolean, paramInt, paramByteBuf);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public int statusCode()
/*     */   {
/* 112 */     ByteBuf localByteBuf = content();
/* 113 */     if ((localByteBuf == null) || (localByteBuf.capacity() == 0)) {
/* 114 */       return -1;
/*     */     }
/*     */     
/* 117 */     localByteBuf.readerIndex(0);
/* 118 */     int i = localByteBuf.readShort();
/* 119 */     localByteBuf.readerIndex(0);
/*     */     
/* 121 */     return i;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public String reasonText()
/*     */   {
/* 129 */     ByteBuf localByteBuf = content();
/* 130 */     if ((localByteBuf == null) || (localByteBuf.capacity() <= 2)) {
/* 131 */       return "";
/*     */     }
/*     */     
/* 134 */     localByteBuf.readerIndex(2);
/* 135 */     String str = localByteBuf.toString(CharsetUtil.UTF_8);
/* 136 */     localByteBuf.readerIndex(0);
/*     */     
/* 138 */     return str;
/*     */   }
/*     */   
/*     */   public CloseWebSocketFrame copy()
/*     */   {
/* 143 */     return new CloseWebSocketFrame(isFinalFragment(), rsv(), content().copy());
/*     */   }
/*     */   
/*     */   public CloseWebSocketFrame duplicate()
/*     */   {
/* 148 */     return new CloseWebSocketFrame(isFinalFragment(), rsv(), content().duplicate());
/*     */   }
/*     */   
/*     */   public CloseWebSocketFrame retain()
/*     */   {
/* 153 */     super.retain();
/* 154 */     return this;
/*     */   }
/*     */   
/*     */   public CloseWebSocketFrame retain(int paramInt)
/*     */   {
/* 159 */     super.retain(paramInt);
/* 160 */     return this;
/*     */   }
/*     */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\io\netty\handler\codec\http\websocketx\CloseWebSocketFrame.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */
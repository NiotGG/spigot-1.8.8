/*     */ package io.netty.handler.codec.spdy;
/*     */ 
/*     */ import io.netty.buffer.ByteBuf;
/*     */ import io.netty.buffer.Unpooled;
/*     */ import io.netty.util.IllegalReferenceCountException;
/*     */ import io.netty.util.internal.StringUtil;
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
/*     */ public class DefaultSpdyDataFrame
/*     */   extends DefaultSpdyStreamFrame
/*     */   implements SpdyDataFrame
/*     */ {
/*     */   private final ByteBuf data;
/*     */   
/*     */   public DefaultSpdyDataFrame(int paramInt)
/*     */   {
/*  36 */     this(paramInt, Unpooled.buffer(0));
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public DefaultSpdyDataFrame(int paramInt, ByteBuf paramByteBuf)
/*     */   {
/*  46 */     super(paramInt);
/*  47 */     if (paramByteBuf == null) {
/*  48 */       throw new NullPointerException("data");
/*     */     }
/*  50 */     this.data = validate(paramByteBuf);
/*     */   }
/*     */   
/*     */   private static ByteBuf validate(ByteBuf paramByteBuf) {
/*  54 */     if (paramByteBuf.readableBytes() > 16777215) {
/*  55 */       throw new IllegalArgumentException("data payload cannot exceed 16777215 bytes");
/*     */     }
/*     */     
/*  58 */     return paramByteBuf;
/*     */   }
/*     */   
/*     */   public SpdyDataFrame setStreamId(int paramInt)
/*     */   {
/*  63 */     super.setStreamId(paramInt);
/*  64 */     return this;
/*     */   }
/*     */   
/*     */   public SpdyDataFrame setLast(boolean paramBoolean)
/*     */   {
/*  69 */     super.setLast(paramBoolean);
/*  70 */     return this;
/*     */   }
/*     */   
/*     */   public ByteBuf content()
/*     */   {
/*  75 */     if (this.data.refCnt() <= 0) {
/*  76 */       throw new IllegalReferenceCountException(this.data.refCnt());
/*     */     }
/*  78 */     return this.data;
/*     */   }
/*     */   
/*     */   public SpdyDataFrame copy()
/*     */   {
/*  83 */     DefaultSpdyDataFrame localDefaultSpdyDataFrame = new DefaultSpdyDataFrame(streamId(), content().copy());
/*  84 */     localDefaultSpdyDataFrame.setLast(isLast());
/*  85 */     return localDefaultSpdyDataFrame;
/*     */   }
/*     */   
/*     */   public SpdyDataFrame duplicate()
/*     */   {
/*  90 */     DefaultSpdyDataFrame localDefaultSpdyDataFrame = new DefaultSpdyDataFrame(streamId(), content().duplicate());
/*  91 */     localDefaultSpdyDataFrame.setLast(isLast());
/*  92 */     return localDefaultSpdyDataFrame;
/*     */   }
/*     */   
/*     */   public int refCnt()
/*     */   {
/*  97 */     return this.data.refCnt();
/*     */   }
/*     */   
/*     */   public SpdyDataFrame retain()
/*     */   {
/* 102 */     this.data.retain();
/* 103 */     return this;
/*     */   }
/*     */   
/*     */   public SpdyDataFrame retain(int paramInt)
/*     */   {
/* 108 */     this.data.retain(paramInt);
/* 109 */     return this;
/*     */   }
/*     */   
/*     */   public boolean release()
/*     */   {
/* 114 */     return this.data.release();
/*     */   }
/*     */   
/*     */   public boolean release(int paramInt)
/*     */   {
/* 119 */     return this.data.release(paramInt);
/*     */   }
/*     */   
/*     */   public String toString()
/*     */   {
/* 124 */     StringBuilder localStringBuilder = new StringBuilder();
/* 125 */     localStringBuilder.append(StringUtil.simpleClassName(this));
/* 126 */     localStringBuilder.append("(last: ");
/* 127 */     localStringBuilder.append(isLast());
/* 128 */     localStringBuilder.append(')');
/* 129 */     localStringBuilder.append(StringUtil.NEWLINE);
/* 130 */     localStringBuilder.append("--> Stream-ID = ");
/* 131 */     localStringBuilder.append(streamId());
/* 132 */     localStringBuilder.append(StringUtil.NEWLINE);
/* 133 */     localStringBuilder.append("--> Size = ");
/* 134 */     if (refCnt() == 0) {
/* 135 */       localStringBuilder.append("(freed)");
/*     */     } else {
/* 137 */       localStringBuilder.append(content().readableBytes());
/*     */     }
/* 139 */     return localStringBuilder.toString();
/*     */   }
/*     */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\io\netty\handler\codec\spdy\DefaultSpdyDataFrame.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */
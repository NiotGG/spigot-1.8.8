/*     */ package io.netty.handler.codec.spdy;
/*     */ 
/*     */ import io.netty.buffer.ByteBuf;
/*     */ import io.netty.buffer.ByteBufAllocator;
/*     */ import java.nio.ByteOrder;
/*     */ import java.util.Set;
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
/*     */ public class SpdyFrameEncoder
/*     */ {
/*     */   private final int version;
/*     */   
/*     */   public SpdyFrameEncoder(SpdyVersion paramSpdyVersion)
/*     */   {
/*  37 */     if (paramSpdyVersion == null) {
/*  38 */       throw new NullPointerException("spdyVersion");
/*     */     }
/*  40 */     this.version = paramSpdyVersion.getVersion();
/*     */   }
/*     */   
/*     */   private void writeControlFrameHeader(ByteBuf paramByteBuf, int paramInt1, byte paramByte, int paramInt2) {
/*  44 */     paramByteBuf.writeShort(this.version | 0x8000);
/*  45 */     paramByteBuf.writeShort(paramInt1);
/*  46 */     paramByteBuf.writeByte(paramByte);
/*  47 */     paramByteBuf.writeMedium(paramInt2);
/*     */   }
/*     */   
/*     */   public ByteBuf encodeDataFrame(ByteBufAllocator paramByteBufAllocator, int paramInt, boolean paramBoolean, ByteBuf paramByteBuf) {
/*  51 */     int i = paramBoolean ? 1 : 0;
/*  52 */     int j = paramByteBuf.readableBytes();
/*  53 */     ByteBuf localByteBuf = paramByteBufAllocator.ioBuffer(8 + j).order(ByteOrder.BIG_ENDIAN);
/*  54 */     localByteBuf.writeInt(paramInt & 0x7FFFFFFF);
/*  55 */     localByteBuf.writeByte(i);
/*  56 */     localByteBuf.writeMedium(j);
/*  57 */     localByteBuf.writeBytes(paramByteBuf, paramByteBuf.readerIndex(), j);
/*  58 */     return localByteBuf;
/*     */   }
/*     */   
/*     */   public ByteBuf encodeSynStreamFrame(ByteBufAllocator paramByteBufAllocator, int paramInt1, int paramInt2, byte paramByte, boolean paramBoolean1, boolean paramBoolean2, ByteBuf paramByteBuf)
/*     */   {
/*  63 */     int i = paramByteBuf.readableBytes();
/*  64 */     byte b = paramBoolean1 ? 1 : 0;
/*  65 */     if (paramBoolean2) {
/*  66 */       b = (byte)(b | 0x2);
/*     */     }
/*  68 */     int j = 10 + i;
/*  69 */     ByteBuf localByteBuf = paramByteBufAllocator.ioBuffer(8 + j).order(ByteOrder.BIG_ENDIAN);
/*  70 */     writeControlFrameHeader(localByteBuf, 1, b, j);
/*  71 */     localByteBuf.writeInt(paramInt1);
/*  72 */     localByteBuf.writeInt(paramInt2);
/*  73 */     localByteBuf.writeShort((paramByte & 0xFF) << 13);
/*  74 */     localByteBuf.writeBytes(paramByteBuf, paramByteBuf.readerIndex(), i);
/*  75 */     return localByteBuf;
/*     */   }
/*     */   
/*     */   public ByteBuf encodeSynReplyFrame(ByteBufAllocator paramByteBufAllocator, int paramInt, boolean paramBoolean, ByteBuf paramByteBuf) {
/*  79 */     int i = paramByteBuf.readableBytes();
/*  80 */     byte b = paramBoolean ? 1 : 0;
/*  81 */     int j = 4 + i;
/*  82 */     ByteBuf localByteBuf = paramByteBufAllocator.ioBuffer(8 + j).order(ByteOrder.BIG_ENDIAN);
/*  83 */     writeControlFrameHeader(localByteBuf, 2, b, j);
/*  84 */     localByteBuf.writeInt(paramInt);
/*  85 */     localByteBuf.writeBytes(paramByteBuf, paramByteBuf.readerIndex(), i);
/*  86 */     return localByteBuf;
/*     */   }
/*     */   
/*     */   public ByteBuf encodeRstStreamFrame(ByteBufAllocator paramByteBufAllocator, int paramInt1, int paramInt2) {
/*  90 */     byte b = 0;
/*  91 */     int i = 8;
/*  92 */     ByteBuf localByteBuf = paramByteBufAllocator.ioBuffer(8 + i).order(ByteOrder.BIG_ENDIAN);
/*  93 */     writeControlFrameHeader(localByteBuf, 3, b, i);
/*  94 */     localByteBuf.writeInt(paramInt1);
/*  95 */     localByteBuf.writeInt(paramInt2);
/*  96 */     return localByteBuf;
/*     */   }
/*     */   
/*     */   public ByteBuf encodeSettingsFrame(ByteBufAllocator paramByteBufAllocator, SpdySettingsFrame paramSpdySettingsFrame) {
/* 100 */     Set localSet = paramSpdySettingsFrame.ids();
/* 101 */     int i = localSet.size();
/*     */     
/* 103 */     byte b = paramSpdySettingsFrame.clearPreviouslyPersistedSettings() ? 1 : 0;
/*     */     
/* 105 */     int j = 4 + 8 * i;
/* 106 */     ByteBuf localByteBuf = paramByteBufAllocator.ioBuffer(8 + j).order(ByteOrder.BIG_ENDIAN);
/* 107 */     writeControlFrameHeader(localByteBuf, 4, b, j);
/* 108 */     localByteBuf.writeInt(i);
/* 109 */     for (Integer localInteger : localSet) {
/* 110 */       b = 0;
/* 111 */       if (paramSpdySettingsFrame.isPersistValue(localInteger.intValue())) {
/* 112 */         b = (byte)(b | 0x1);
/*     */       }
/* 114 */       if (paramSpdySettingsFrame.isPersisted(localInteger.intValue())) {
/* 115 */         b = (byte)(b | 0x2);
/*     */       }
/* 117 */       localByteBuf.writeByte(b);
/* 118 */       localByteBuf.writeMedium(localInteger.intValue());
/* 119 */       localByteBuf.writeInt(paramSpdySettingsFrame.getValue(localInteger.intValue()));
/*     */     }
/* 121 */     return localByteBuf;
/*     */   }
/*     */   
/*     */   public ByteBuf encodePingFrame(ByteBufAllocator paramByteBufAllocator, int paramInt) {
/* 125 */     byte b = 0;
/* 126 */     int i = 4;
/* 127 */     ByteBuf localByteBuf = paramByteBufAllocator.ioBuffer(8 + i).order(ByteOrder.BIG_ENDIAN);
/* 128 */     writeControlFrameHeader(localByteBuf, 6, b, i);
/* 129 */     localByteBuf.writeInt(paramInt);
/* 130 */     return localByteBuf;
/*     */   }
/*     */   
/*     */   public ByteBuf encodeGoAwayFrame(ByteBufAllocator paramByteBufAllocator, int paramInt1, int paramInt2) {
/* 134 */     byte b = 0;
/* 135 */     int i = 8;
/* 136 */     ByteBuf localByteBuf = paramByteBufAllocator.ioBuffer(8 + i).order(ByteOrder.BIG_ENDIAN);
/* 137 */     writeControlFrameHeader(localByteBuf, 7, b, i);
/* 138 */     localByteBuf.writeInt(paramInt1);
/* 139 */     localByteBuf.writeInt(paramInt2);
/* 140 */     return localByteBuf;
/*     */   }
/*     */   
/*     */   public ByteBuf encodeHeadersFrame(ByteBufAllocator paramByteBufAllocator, int paramInt, boolean paramBoolean, ByteBuf paramByteBuf) {
/* 144 */     int i = paramByteBuf.readableBytes();
/* 145 */     byte b = paramBoolean ? 1 : 0;
/* 146 */     int j = 4 + i;
/* 147 */     ByteBuf localByteBuf = paramByteBufAllocator.ioBuffer(8 + j).order(ByteOrder.BIG_ENDIAN);
/* 148 */     writeControlFrameHeader(localByteBuf, 8, b, j);
/* 149 */     localByteBuf.writeInt(paramInt);
/* 150 */     localByteBuf.writeBytes(paramByteBuf, paramByteBuf.readerIndex(), i);
/* 151 */     return localByteBuf;
/*     */   }
/*     */   
/*     */   public ByteBuf encodeWindowUpdateFrame(ByteBufAllocator paramByteBufAllocator, int paramInt1, int paramInt2) {
/* 155 */     byte b = 0;
/* 156 */     int i = 8;
/* 157 */     ByteBuf localByteBuf = paramByteBufAllocator.ioBuffer(8 + i).order(ByteOrder.BIG_ENDIAN);
/* 158 */     writeControlFrameHeader(localByteBuf, 9, b, i);
/* 159 */     localByteBuf.writeInt(paramInt1);
/* 160 */     localByteBuf.writeInt(paramInt2);
/* 161 */     return localByteBuf;
/*     */   }
/*     */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\io\netty\handler\codec\spdy\SpdyFrameEncoder.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */
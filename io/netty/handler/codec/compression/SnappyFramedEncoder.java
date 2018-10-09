/*     */ package io.netty.handler.codec.compression;
/*     */ 
/*     */ import io.netty.buffer.ByteBuf;
/*     */ import io.netty.buffer.ByteBufUtil;
/*     */ import io.netty.channel.ChannelHandlerContext;
/*     */ import io.netty.handler.codec.MessageToByteEncoder;
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
/*     */ public class SnappyFramedEncoder
/*     */   extends MessageToByteEncoder<ByteBuf>
/*     */ {
/*     */   private static final int MIN_COMPRESSIBLE_LENGTH = 18;
/*  42 */   private static final byte[] STREAM_START = { -1, 6, 0, 0, 115, 78, 97, 80, 112, 89 };
/*     */   
/*     */ 
/*     */ 
/*  46 */   private final Snappy snappy = new Snappy();
/*     */   private boolean started;
/*     */   
/*     */   protected void encode(ChannelHandlerContext paramChannelHandlerContext, ByteBuf paramByteBuf1, ByteBuf paramByteBuf2) throws Exception
/*     */   {
/*  51 */     if (!paramByteBuf1.isReadable()) {
/*  52 */       return;
/*     */     }
/*     */     
/*  55 */     if (!this.started) {
/*  56 */       this.started = true;
/*  57 */       paramByteBuf2.writeBytes(STREAM_START);
/*     */     }
/*     */     
/*  60 */     int i = paramByteBuf1.readableBytes();
/*  61 */     if (i > 18) {
/*     */       for (;;) {
/*  63 */         int j = paramByteBuf2.writerIndex() + 1;
/*  64 */         ByteBuf localByteBuf; if (i < 18) {
/*  65 */           localByteBuf = paramByteBuf1.readSlice(i);
/*  66 */           writeUnencodedChunk(localByteBuf, paramByteBuf2, i);
/*  67 */           break;
/*     */         }
/*     */         
/*  70 */         paramByteBuf2.writeInt(0);
/*  71 */         if (i > 32767) {
/*  72 */           localByteBuf = paramByteBuf1.readSlice(32767);
/*  73 */           calculateAndWriteChecksum(localByteBuf, paramByteBuf2);
/*  74 */           this.snappy.encode(localByteBuf, paramByteBuf2, 32767);
/*  75 */           setChunkLength(paramByteBuf2, j);
/*  76 */           i -= 32767;
/*     */         } else {
/*  78 */           localByteBuf = paramByteBuf1.readSlice(i);
/*  79 */           calculateAndWriteChecksum(localByteBuf, paramByteBuf2);
/*  80 */           this.snappy.encode(localByteBuf, paramByteBuf2, i);
/*  81 */           setChunkLength(paramByteBuf2, j);
/*  82 */           break;
/*     */         }
/*     */       }
/*     */     }
/*  86 */     writeUnencodedChunk(paramByteBuf1, paramByteBuf2, i);
/*     */   }
/*     */   
/*     */   private static void writeUnencodedChunk(ByteBuf paramByteBuf1, ByteBuf paramByteBuf2, int paramInt)
/*     */   {
/*  91 */     paramByteBuf2.writeByte(1);
/*  92 */     writeChunkLength(paramByteBuf2, paramInt + 4);
/*  93 */     calculateAndWriteChecksum(paramByteBuf1, paramByteBuf2);
/*  94 */     paramByteBuf2.writeBytes(paramByteBuf1, paramInt);
/*     */   }
/*     */   
/*     */   private static void setChunkLength(ByteBuf paramByteBuf, int paramInt) {
/*  98 */     int i = paramByteBuf.writerIndex() - paramInt - 3;
/*  99 */     if (i >>> 24 != 0) {
/* 100 */       throw new CompressionException("compressed data too large: " + i);
/*     */     }
/* 102 */     paramByteBuf.setMedium(paramInt, ByteBufUtil.swapMedium(i));
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   private static void writeChunkLength(ByteBuf paramByteBuf, int paramInt)
/*     */   {
/* 112 */     paramByteBuf.writeMedium(ByteBufUtil.swapMedium(paramInt));
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   private static void calculateAndWriteChecksum(ByteBuf paramByteBuf1, ByteBuf paramByteBuf2)
/*     */   {
/* 122 */     paramByteBuf2.writeInt(ByteBufUtil.swapInt(Snappy.calculateChecksum(paramByteBuf1)));
/*     */   }
/*     */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\io\netty\handler\codec\compression\SnappyFramedEncoder.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */
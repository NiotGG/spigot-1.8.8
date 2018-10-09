/*     */ package io.netty.handler.codec.compression;
/*     */ 
/*     */ import io.netty.buffer.ByteBuf;
/*     */ import io.netty.buffer.ByteBufAllocator;
/*     */ import io.netty.buffer.ByteBufUtil;
/*     */ import io.netty.channel.ChannelHandlerContext;
/*     */ import io.netty.handler.codec.ByteToMessageDecoder;
/*     */ import java.util.Arrays;
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
/*     */ public class SnappyFramedDecoder
/*     */   extends ByteToMessageDecoder
/*     */ {
/*     */   private static enum ChunkType
/*     */   {
/*  41 */     STREAM_IDENTIFIER, 
/*  42 */     COMPRESSED_DATA, 
/*  43 */     UNCOMPRESSED_DATA, 
/*  44 */     RESERVED_UNSKIPPABLE, 
/*  45 */     RESERVED_SKIPPABLE;
/*     */     
/*     */     private ChunkType() {} }
/*  48 */   private static final byte[] SNAPPY = { 115, 78, 97, 80, 112, 89 };
/*     */   
/*     */   private static final int MAX_UNCOMPRESSED_DATA_SIZE = 65540;
/*  51 */   private final Snappy snappy = new Snappy();
/*     */   
/*     */ 
/*     */   private final boolean validateChecksums;
/*     */   
/*     */   private boolean started;
/*     */   
/*     */   private boolean corrupted;
/*     */   
/*     */ 
/*     */   public SnappyFramedDecoder()
/*     */   {
/*  63 */     this(false);
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
/*     */   public SnappyFramedDecoder(boolean paramBoolean)
/*     */   {
/*  76 */     this.validateChecksums = paramBoolean;
/*     */   }
/*     */   
/*     */   protected void decode(ChannelHandlerContext paramChannelHandlerContext, ByteBuf paramByteBuf, List<Object> paramList) throws Exception
/*     */   {
/*  81 */     if (this.corrupted) {
/*  82 */       paramByteBuf.skipBytes(paramByteBuf.readableBytes());
/*  83 */       return;
/*     */     }
/*     */     try
/*     */     {
/*  87 */       int i = paramByteBuf.readerIndex();
/*  88 */       int j = paramByteBuf.readableBytes();
/*  89 */       if (j < 4)
/*     */       {
/*     */ 
/*  92 */         return;
/*     */       }
/*     */       
/*  95 */       int k = paramByteBuf.getUnsignedByte(i);
/*  96 */       ChunkType localChunkType = mapChunkType((byte)k);
/*  97 */       int m = ByteBufUtil.swapMedium(paramByteBuf.getUnsignedMedium(i + 1));
/*     */       int n;
/*  99 */       switch (localChunkType) {
/*     */       case STREAM_IDENTIFIER: 
/* 101 */         if (m != SNAPPY.length) {
/* 102 */           throw new DecompressionException("Unexpected length of stream identifier: " + m);
/*     */         }
/*     */         
/* 105 */         if (j >= 4 + SNAPPY.length)
/*     */         {
/*     */ 
/*     */ 
/* 109 */           byte[] arrayOfByte = new byte[m];
/* 110 */           paramByteBuf.skipBytes(4).readBytes(arrayOfByte);
/*     */           
/* 112 */           if (!Arrays.equals(arrayOfByte, SNAPPY)) {
/* 113 */             throw new DecompressionException("Unexpected stream identifier contents. Mismatched snappy protocol version?");
/*     */           }
/*     */           
/*     */ 
/* 117 */           this.started = true; }
/* 118 */         break;
/*     */       case RESERVED_SKIPPABLE: 
/* 120 */         if (!this.started) {
/* 121 */           throw new DecompressionException("Received RESERVED_SKIPPABLE tag before STREAM_IDENTIFIER");
/*     */         }
/*     */         
/* 124 */         if (j < 4 + m)
/*     */         {
/* 126 */           return;
/*     */         }
/*     */         
/* 129 */         paramByteBuf.skipBytes(4 + m);
/* 130 */         break;
/*     */       
/*     */ 
/*     */ 
/*     */       case RESERVED_UNSKIPPABLE: 
/* 135 */         throw new DecompressionException("Found reserved unskippable chunk type: 0x" + Integer.toHexString(k));
/*     */       
/*     */       case UNCOMPRESSED_DATA: 
/* 138 */         if (!this.started) {
/* 139 */           throw new DecompressionException("Received UNCOMPRESSED_DATA tag before STREAM_IDENTIFIER");
/*     */         }
/* 141 */         if (m > 65540) {
/* 142 */           throw new DecompressionException("Received UNCOMPRESSED_DATA larger than 65540 bytes");
/*     */         }
/*     */         
/* 145 */         if (j < 4 + m) {
/* 146 */           return;
/*     */         }
/*     */         
/* 149 */         paramByteBuf.skipBytes(4);
/* 150 */         if (this.validateChecksums) {
/* 151 */           n = ByteBufUtil.swapInt(paramByteBuf.readInt());
/* 152 */           Snappy.validateChecksum(n, paramByteBuf, paramByteBuf.readerIndex(), m - 4);
/*     */         } else {
/* 154 */           paramByteBuf.skipBytes(4);
/*     */         }
/* 156 */         paramList.add(paramByteBuf.readSlice(m - 4).retain());
/* 157 */         break;
/*     */       case COMPRESSED_DATA: 
/* 159 */         if (!this.started) {
/* 160 */           throw new DecompressionException("Received COMPRESSED_DATA tag before STREAM_IDENTIFIER");
/*     */         }
/*     */         
/* 163 */         if (j < 4 + m) {
/* 164 */           return;
/*     */         }
/*     */         
/* 167 */         paramByteBuf.skipBytes(4);
/* 168 */         n = ByteBufUtil.swapInt(paramByteBuf.readInt());
/* 169 */         ByteBuf localByteBuf = paramChannelHandlerContext.alloc().buffer(0);
/* 170 */         if (this.validateChecksums) {
/* 171 */           int i1 = paramByteBuf.writerIndex();
/*     */           try {
/* 173 */             paramByteBuf.writerIndex(paramByteBuf.readerIndex() + m - 4);
/* 174 */             this.snappy.decode(paramByteBuf, localByteBuf);
/*     */           } finally {
/* 176 */             paramByteBuf.writerIndex(i1);
/*     */           }
/* 178 */           Snappy.validateChecksum(n, localByteBuf, 0, localByteBuf.writerIndex());
/*     */         } else {
/* 180 */           this.snappy.decode(paramByteBuf.readSlice(m - 4), localByteBuf);
/*     */         }
/* 182 */         paramList.add(localByteBuf);
/* 183 */         this.snappy.reset();
/*     */       }
/*     */     }
/*     */     catch (Exception localException) {
/* 187 */       this.corrupted = true;
/* 188 */       throw localException;
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   private static ChunkType mapChunkType(byte paramByte)
/*     */   {
/* 199 */     if (paramByte == 0)
/* 200 */       return ChunkType.COMPRESSED_DATA;
/* 201 */     if (paramByte == 1)
/* 202 */       return ChunkType.UNCOMPRESSED_DATA;
/* 203 */     if (paramByte == -1)
/* 204 */       return ChunkType.STREAM_IDENTIFIER;
/* 205 */     if ((paramByte & 0x80) == 128) {
/* 206 */       return ChunkType.RESERVED_SKIPPABLE;
/*     */     }
/* 208 */     return ChunkType.RESERVED_UNSKIPPABLE;
/*     */   }
/*     */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\io\netty\handler\codec\compression\SnappyFramedDecoder.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */
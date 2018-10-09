/*     */ package io.netty.buffer;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import java.io.InputStream;
/*     */ import java.io.OutputStream;
/*     */ import java.nio.ByteBuffer;
/*     */ import java.nio.ByteOrder;
/*     */ import java.nio.channels.GatheringByteChannel;
/*     */ import java.nio.channels.ScatteringByteChannel;
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
/*     */ public class SlicedByteBuf
/*     */   extends AbstractDerivedByteBuf
/*     */ {
/*     */   private final ByteBuf buffer;
/*     */   private final int adjustment;
/*     */   private final int length;
/*     */   
/*     */   public SlicedByteBuf(ByteBuf paramByteBuf, int paramInt1, int paramInt2)
/*     */   {
/*  40 */     super(paramInt2);
/*  41 */     if ((paramInt1 < 0) || (paramInt1 > paramByteBuf.capacity() - paramInt2)) {
/*  42 */       throw new IndexOutOfBoundsException(paramByteBuf + ".slice(" + paramInt1 + ", " + paramInt2 + ')');
/*     */     }
/*     */     
/*  45 */     if ((paramByteBuf instanceof SlicedByteBuf)) {
/*  46 */       this.buffer = ((SlicedByteBuf)paramByteBuf).buffer;
/*  47 */       this.adjustment = (((SlicedByteBuf)paramByteBuf).adjustment + paramInt1);
/*  48 */     } else if ((paramByteBuf instanceof DuplicatedByteBuf)) {
/*  49 */       this.buffer = paramByteBuf.unwrap();
/*  50 */       this.adjustment = paramInt1;
/*     */     } else {
/*  52 */       this.buffer = paramByteBuf;
/*  53 */       this.adjustment = paramInt1;
/*     */     }
/*  55 */     this.length = paramInt2;
/*     */     
/*  57 */     writerIndex(paramInt2);
/*     */   }
/*     */   
/*     */   public ByteBuf unwrap()
/*     */   {
/*  62 */     return this.buffer;
/*     */   }
/*     */   
/*     */   public ByteBufAllocator alloc()
/*     */   {
/*  67 */     return this.buffer.alloc();
/*     */   }
/*     */   
/*     */   public ByteOrder order()
/*     */   {
/*  72 */     return this.buffer.order();
/*     */   }
/*     */   
/*     */   public boolean isDirect()
/*     */   {
/*  77 */     return this.buffer.isDirect();
/*     */   }
/*     */   
/*     */   public int capacity()
/*     */   {
/*  82 */     return this.length;
/*     */   }
/*     */   
/*     */   public ByteBuf capacity(int paramInt)
/*     */   {
/*  87 */     throw new UnsupportedOperationException("sliced buffer");
/*     */   }
/*     */   
/*     */   public boolean hasArray()
/*     */   {
/*  92 */     return this.buffer.hasArray();
/*     */   }
/*     */   
/*     */   public byte[] array()
/*     */   {
/*  97 */     return this.buffer.array();
/*     */   }
/*     */   
/*     */   public int arrayOffset()
/*     */   {
/* 102 */     return this.buffer.arrayOffset() + this.adjustment;
/*     */   }
/*     */   
/*     */   public boolean hasMemoryAddress()
/*     */   {
/* 107 */     return this.buffer.hasMemoryAddress();
/*     */   }
/*     */   
/*     */   public long memoryAddress()
/*     */   {
/* 112 */     return this.buffer.memoryAddress() + this.adjustment;
/*     */   }
/*     */   
/*     */   protected byte _getByte(int paramInt)
/*     */   {
/* 117 */     return this.buffer.getByte(paramInt + this.adjustment);
/*     */   }
/*     */   
/*     */   protected short _getShort(int paramInt)
/*     */   {
/* 122 */     return this.buffer.getShort(paramInt + this.adjustment);
/*     */   }
/*     */   
/*     */   protected int _getUnsignedMedium(int paramInt)
/*     */   {
/* 127 */     return this.buffer.getUnsignedMedium(paramInt + this.adjustment);
/*     */   }
/*     */   
/*     */   protected int _getInt(int paramInt)
/*     */   {
/* 132 */     return this.buffer.getInt(paramInt + this.adjustment);
/*     */   }
/*     */   
/*     */   protected long _getLong(int paramInt)
/*     */   {
/* 137 */     return this.buffer.getLong(paramInt + this.adjustment);
/*     */   }
/*     */   
/*     */   public ByteBuf duplicate()
/*     */   {
/* 142 */     ByteBuf localByteBuf = this.buffer.slice(this.adjustment, this.length);
/* 143 */     localByteBuf.setIndex(readerIndex(), writerIndex());
/* 144 */     return localByteBuf;
/*     */   }
/*     */   
/*     */   public ByteBuf copy(int paramInt1, int paramInt2)
/*     */   {
/* 149 */     checkIndex(paramInt1, paramInt2);
/* 150 */     return this.buffer.copy(paramInt1 + this.adjustment, paramInt2);
/*     */   }
/*     */   
/*     */   public ByteBuf slice(int paramInt1, int paramInt2)
/*     */   {
/* 155 */     checkIndex(paramInt1, paramInt2);
/* 156 */     if (paramInt2 == 0) {
/* 157 */       return Unpooled.EMPTY_BUFFER;
/*     */     }
/* 159 */     return this.buffer.slice(paramInt1 + this.adjustment, paramInt2);
/*     */   }
/*     */   
/*     */   public ByteBuf getBytes(int paramInt1, ByteBuf paramByteBuf, int paramInt2, int paramInt3)
/*     */   {
/* 164 */     checkIndex(paramInt1, paramInt3);
/* 165 */     this.buffer.getBytes(paramInt1 + this.adjustment, paramByteBuf, paramInt2, paramInt3);
/* 166 */     return this;
/*     */   }
/*     */   
/*     */   public ByteBuf getBytes(int paramInt1, byte[] paramArrayOfByte, int paramInt2, int paramInt3)
/*     */   {
/* 171 */     checkIndex(paramInt1, paramInt3);
/* 172 */     this.buffer.getBytes(paramInt1 + this.adjustment, paramArrayOfByte, paramInt2, paramInt3);
/* 173 */     return this;
/*     */   }
/*     */   
/*     */   public ByteBuf getBytes(int paramInt, ByteBuffer paramByteBuffer)
/*     */   {
/* 178 */     checkIndex(paramInt, paramByteBuffer.remaining());
/* 179 */     this.buffer.getBytes(paramInt + this.adjustment, paramByteBuffer);
/* 180 */     return this;
/*     */   }
/*     */   
/*     */   protected void _setByte(int paramInt1, int paramInt2)
/*     */   {
/* 185 */     this.buffer.setByte(paramInt1 + this.adjustment, paramInt2);
/*     */   }
/*     */   
/*     */   protected void _setShort(int paramInt1, int paramInt2)
/*     */   {
/* 190 */     this.buffer.setShort(paramInt1 + this.adjustment, paramInt2);
/*     */   }
/*     */   
/*     */   protected void _setMedium(int paramInt1, int paramInt2)
/*     */   {
/* 195 */     this.buffer.setMedium(paramInt1 + this.adjustment, paramInt2);
/*     */   }
/*     */   
/*     */   protected void _setInt(int paramInt1, int paramInt2)
/*     */   {
/* 200 */     this.buffer.setInt(paramInt1 + this.adjustment, paramInt2);
/*     */   }
/*     */   
/*     */   protected void _setLong(int paramInt, long paramLong)
/*     */   {
/* 205 */     this.buffer.setLong(paramInt + this.adjustment, paramLong);
/*     */   }
/*     */   
/*     */   public ByteBuf setBytes(int paramInt1, byte[] paramArrayOfByte, int paramInt2, int paramInt3)
/*     */   {
/* 210 */     checkIndex(paramInt1, paramInt3);
/* 211 */     this.buffer.setBytes(paramInt1 + this.adjustment, paramArrayOfByte, paramInt2, paramInt3);
/* 212 */     return this;
/*     */   }
/*     */   
/*     */   public ByteBuf setBytes(int paramInt1, ByteBuf paramByteBuf, int paramInt2, int paramInt3)
/*     */   {
/* 217 */     checkIndex(paramInt1, paramInt3);
/* 218 */     this.buffer.setBytes(paramInt1 + this.adjustment, paramByteBuf, paramInt2, paramInt3);
/* 219 */     return this;
/*     */   }
/*     */   
/*     */   public ByteBuf setBytes(int paramInt, ByteBuffer paramByteBuffer)
/*     */   {
/* 224 */     checkIndex(paramInt, paramByteBuffer.remaining());
/* 225 */     this.buffer.setBytes(paramInt + this.adjustment, paramByteBuffer);
/* 226 */     return this;
/*     */   }
/*     */   
/*     */   public ByteBuf getBytes(int paramInt1, OutputStream paramOutputStream, int paramInt2) throws IOException
/*     */   {
/* 231 */     checkIndex(paramInt1, paramInt2);
/* 232 */     this.buffer.getBytes(paramInt1 + this.adjustment, paramOutputStream, paramInt2);
/* 233 */     return this;
/*     */   }
/*     */   
/*     */   public int getBytes(int paramInt1, GatheringByteChannel paramGatheringByteChannel, int paramInt2) throws IOException
/*     */   {
/* 238 */     checkIndex(paramInt1, paramInt2);
/* 239 */     return this.buffer.getBytes(paramInt1 + this.adjustment, paramGatheringByteChannel, paramInt2);
/*     */   }
/*     */   
/*     */   public int setBytes(int paramInt1, InputStream paramInputStream, int paramInt2) throws IOException
/*     */   {
/* 244 */     checkIndex(paramInt1, paramInt2);
/* 245 */     return this.buffer.setBytes(paramInt1 + this.adjustment, paramInputStream, paramInt2);
/*     */   }
/*     */   
/*     */   public int setBytes(int paramInt1, ScatteringByteChannel paramScatteringByteChannel, int paramInt2) throws IOException
/*     */   {
/* 250 */     checkIndex(paramInt1, paramInt2);
/* 251 */     return this.buffer.setBytes(paramInt1 + this.adjustment, paramScatteringByteChannel, paramInt2);
/*     */   }
/*     */   
/*     */   public int nioBufferCount()
/*     */   {
/* 256 */     return this.buffer.nioBufferCount();
/*     */   }
/*     */   
/*     */   public ByteBuffer nioBuffer(int paramInt1, int paramInt2)
/*     */   {
/* 261 */     checkIndex(paramInt1, paramInt2);
/* 262 */     return this.buffer.nioBuffer(paramInt1 + this.adjustment, paramInt2);
/*     */   }
/*     */   
/*     */   public ByteBuffer[] nioBuffers(int paramInt1, int paramInt2)
/*     */   {
/* 267 */     checkIndex(paramInt1, paramInt2);
/* 268 */     return this.buffer.nioBuffers(paramInt1 + this.adjustment, paramInt2);
/*     */   }
/*     */   
/*     */   public ByteBuffer internalNioBuffer(int paramInt1, int paramInt2)
/*     */   {
/* 273 */     checkIndex(paramInt1, paramInt2);
/* 274 */     return nioBuffer(paramInt1, paramInt2);
/*     */   }
/*     */   
/*     */   public int forEachByte(int paramInt1, int paramInt2, ByteBufProcessor paramByteBufProcessor)
/*     */   {
/* 279 */     int i = this.buffer.forEachByte(paramInt1 + this.adjustment, paramInt2, paramByteBufProcessor);
/* 280 */     if (i >= this.adjustment) {
/* 281 */       return i - this.adjustment;
/*     */     }
/* 283 */     return -1;
/*     */   }
/*     */   
/*     */ 
/*     */   public int forEachByteDesc(int paramInt1, int paramInt2, ByteBufProcessor paramByteBufProcessor)
/*     */   {
/* 289 */     int i = this.buffer.forEachByteDesc(paramInt1 + this.adjustment, paramInt2, paramByteBufProcessor);
/* 290 */     if (i >= this.adjustment) {
/* 291 */       return i - this.adjustment;
/*     */     }
/* 293 */     return -1;
/*     */   }
/*     */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\io\netty\buffer\SlicedByteBuf.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */
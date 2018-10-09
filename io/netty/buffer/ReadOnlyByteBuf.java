/*     */ package io.netty.buffer;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import java.io.InputStream;
/*     */ import java.io.OutputStream;
/*     */ import java.nio.ByteBuffer;
/*     */ import java.nio.ByteOrder;
/*     */ import java.nio.ReadOnlyBufferException;
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
/*     */ public class ReadOnlyByteBuf
/*     */   extends AbstractDerivedByteBuf
/*     */ {
/*     */   private final ByteBuf buffer;
/*     */   
/*     */   public ReadOnlyByteBuf(ByteBuf paramByteBuf)
/*     */   {
/*  37 */     super(paramByteBuf.maxCapacity());
/*     */     
/*  39 */     if (((paramByteBuf instanceof ReadOnlyByteBuf)) || ((paramByteBuf instanceof DuplicatedByteBuf))) {
/*  40 */       this.buffer = paramByteBuf.unwrap();
/*     */     } else {
/*  42 */       this.buffer = paramByteBuf;
/*     */     }
/*  44 */     setIndex(paramByteBuf.readerIndex(), paramByteBuf.writerIndex());
/*     */   }
/*     */   
/*     */   public boolean isWritable()
/*     */   {
/*  49 */     return false;
/*     */   }
/*     */   
/*     */   public boolean isWritable(int paramInt)
/*     */   {
/*  54 */     return false;
/*     */   }
/*     */   
/*     */   public ByteBuf unwrap()
/*     */   {
/*  59 */     return this.buffer;
/*     */   }
/*     */   
/*     */   public ByteBufAllocator alloc()
/*     */   {
/*  64 */     return this.buffer.alloc();
/*     */   }
/*     */   
/*     */   public ByteOrder order()
/*     */   {
/*  69 */     return this.buffer.order();
/*     */   }
/*     */   
/*     */   public boolean isDirect()
/*     */   {
/*  74 */     return this.buffer.isDirect();
/*     */   }
/*     */   
/*     */   public boolean hasArray()
/*     */   {
/*  79 */     return false;
/*     */   }
/*     */   
/*     */   public byte[] array()
/*     */   {
/*  84 */     throw new ReadOnlyBufferException();
/*     */   }
/*     */   
/*     */   public int arrayOffset()
/*     */   {
/*  89 */     throw new ReadOnlyBufferException();
/*     */   }
/*     */   
/*     */   public boolean hasMemoryAddress()
/*     */   {
/*  94 */     return false;
/*     */   }
/*     */   
/*     */   public long memoryAddress()
/*     */   {
/*  99 */     throw new ReadOnlyBufferException();
/*     */   }
/*     */   
/*     */   public ByteBuf discardReadBytes()
/*     */   {
/* 104 */     throw new ReadOnlyBufferException();
/*     */   }
/*     */   
/*     */   public ByteBuf setBytes(int paramInt1, ByteBuf paramByteBuf, int paramInt2, int paramInt3)
/*     */   {
/* 109 */     throw new ReadOnlyBufferException();
/*     */   }
/*     */   
/*     */   public ByteBuf setBytes(int paramInt1, byte[] paramArrayOfByte, int paramInt2, int paramInt3)
/*     */   {
/* 114 */     throw new ReadOnlyBufferException();
/*     */   }
/*     */   
/*     */   public ByteBuf setBytes(int paramInt, ByteBuffer paramByteBuffer)
/*     */   {
/* 119 */     throw new ReadOnlyBufferException();
/*     */   }
/*     */   
/*     */   public ByteBuf setByte(int paramInt1, int paramInt2)
/*     */   {
/* 124 */     throw new ReadOnlyBufferException();
/*     */   }
/*     */   
/*     */   protected void _setByte(int paramInt1, int paramInt2)
/*     */   {
/* 129 */     throw new ReadOnlyBufferException();
/*     */   }
/*     */   
/*     */   public ByteBuf setShort(int paramInt1, int paramInt2)
/*     */   {
/* 134 */     throw new ReadOnlyBufferException();
/*     */   }
/*     */   
/*     */   protected void _setShort(int paramInt1, int paramInt2)
/*     */   {
/* 139 */     throw new ReadOnlyBufferException();
/*     */   }
/*     */   
/*     */   public ByteBuf setMedium(int paramInt1, int paramInt2)
/*     */   {
/* 144 */     throw new ReadOnlyBufferException();
/*     */   }
/*     */   
/*     */   protected void _setMedium(int paramInt1, int paramInt2)
/*     */   {
/* 149 */     throw new ReadOnlyBufferException();
/*     */   }
/*     */   
/*     */   public ByteBuf setInt(int paramInt1, int paramInt2)
/*     */   {
/* 154 */     throw new ReadOnlyBufferException();
/*     */   }
/*     */   
/*     */   protected void _setInt(int paramInt1, int paramInt2)
/*     */   {
/* 159 */     throw new ReadOnlyBufferException();
/*     */   }
/*     */   
/*     */   public ByteBuf setLong(int paramInt, long paramLong)
/*     */   {
/* 164 */     throw new ReadOnlyBufferException();
/*     */   }
/*     */   
/*     */   protected void _setLong(int paramInt, long paramLong)
/*     */   {
/* 169 */     throw new ReadOnlyBufferException();
/*     */   }
/*     */   
/*     */   public int setBytes(int paramInt1, InputStream paramInputStream, int paramInt2)
/*     */   {
/* 174 */     throw new ReadOnlyBufferException();
/*     */   }
/*     */   
/*     */   public int setBytes(int paramInt1, ScatteringByteChannel paramScatteringByteChannel, int paramInt2)
/*     */   {
/* 179 */     throw new ReadOnlyBufferException();
/*     */   }
/*     */   
/*     */   public int getBytes(int paramInt1, GatheringByteChannel paramGatheringByteChannel, int paramInt2)
/*     */     throws IOException
/*     */   {
/* 185 */     return this.buffer.getBytes(paramInt1, paramGatheringByteChannel, paramInt2);
/*     */   }
/*     */   
/*     */   public ByteBuf getBytes(int paramInt1, OutputStream paramOutputStream, int paramInt2)
/*     */     throws IOException
/*     */   {
/* 191 */     this.buffer.getBytes(paramInt1, paramOutputStream, paramInt2);
/* 192 */     return this;
/*     */   }
/*     */   
/*     */   public ByteBuf getBytes(int paramInt1, byte[] paramArrayOfByte, int paramInt2, int paramInt3)
/*     */   {
/* 197 */     this.buffer.getBytes(paramInt1, paramArrayOfByte, paramInt2, paramInt3);
/* 198 */     return this;
/*     */   }
/*     */   
/*     */   public ByteBuf getBytes(int paramInt1, ByteBuf paramByteBuf, int paramInt2, int paramInt3)
/*     */   {
/* 203 */     this.buffer.getBytes(paramInt1, paramByteBuf, paramInt2, paramInt3);
/* 204 */     return this;
/*     */   }
/*     */   
/*     */   public ByteBuf getBytes(int paramInt, ByteBuffer paramByteBuffer)
/*     */   {
/* 209 */     this.buffer.getBytes(paramInt, paramByteBuffer);
/* 210 */     return this;
/*     */   }
/*     */   
/*     */   public ByteBuf duplicate()
/*     */   {
/* 215 */     return new ReadOnlyByteBuf(this);
/*     */   }
/*     */   
/*     */   public ByteBuf copy(int paramInt1, int paramInt2)
/*     */   {
/* 220 */     return this.buffer.copy(paramInt1, paramInt2);
/*     */   }
/*     */   
/*     */   public ByteBuf slice(int paramInt1, int paramInt2)
/*     */   {
/* 225 */     return Unpooled.unmodifiableBuffer(this.buffer.slice(paramInt1, paramInt2));
/*     */   }
/*     */   
/*     */   public byte getByte(int paramInt)
/*     */   {
/* 230 */     return _getByte(paramInt);
/*     */   }
/*     */   
/*     */   protected byte _getByte(int paramInt)
/*     */   {
/* 235 */     return this.buffer.getByte(paramInt);
/*     */   }
/*     */   
/*     */   public short getShort(int paramInt)
/*     */   {
/* 240 */     return _getShort(paramInt);
/*     */   }
/*     */   
/*     */   protected short _getShort(int paramInt)
/*     */   {
/* 245 */     return this.buffer.getShort(paramInt);
/*     */   }
/*     */   
/*     */   public int getUnsignedMedium(int paramInt)
/*     */   {
/* 250 */     return _getUnsignedMedium(paramInt);
/*     */   }
/*     */   
/*     */   protected int _getUnsignedMedium(int paramInt)
/*     */   {
/* 255 */     return this.buffer.getUnsignedMedium(paramInt);
/*     */   }
/*     */   
/*     */   public int getInt(int paramInt)
/*     */   {
/* 260 */     return _getInt(paramInt);
/*     */   }
/*     */   
/*     */   protected int _getInt(int paramInt)
/*     */   {
/* 265 */     return this.buffer.getInt(paramInt);
/*     */   }
/*     */   
/*     */   public long getLong(int paramInt)
/*     */   {
/* 270 */     return _getLong(paramInt);
/*     */   }
/*     */   
/*     */   protected long _getLong(int paramInt)
/*     */   {
/* 275 */     return this.buffer.getLong(paramInt);
/*     */   }
/*     */   
/*     */   public int nioBufferCount()
/*     */   {
/* 280 */     return this.buffer.nioBufferCount();
/*     */   }
/*     */   
/*     */   public ByteBuffer nioBuffer(int paramInt1, int paramInt2)
/*     */   {
/* 285 */     return this.buffer.nioBuffer(paramInt1, paramInt2).asReadOnlyBuffer();
/*     */   }
/*     */   
/*     */   public ByteBuffer[] nioBuffers(int paramInt1, int paramInt2)
/*     */   {
/* 290 */     return this.buffer.nioBuffers(paramInt1, paramInt2);
/*     */   }
/*     */   
/*     */   public ByteBuffer internalNioBuffer(int paramInt1, int paramInt2)
/*     */   {
/* 295 */     return nioBuffer(paramInt1, paramInt2);
/*     */   }
/*     */   
/*     */   public int forEachByte(int paramInt1, int paramInt2, ByteBufProcessor paramByteBufProcessor)
/*     */   {
/* 300 */     return this.buffer.forEachByte(paramInt1, paramInt2, paramByteBufProcessor);
/*     */   }
/*     */   
/*     */   public int forEachByteDesc(int paramInt1, int paramInt2, ByteBufProcessor paramByteBufProcessor)
/*     */   {
/* 305 */     return this.buffer.forEachByteDesc(paramInt1, paramInt2, paramByteBufProcessor);
/*     */   }
/*     */   
/*     */   public int capacity()
/*     */   {
/* 310 */     return this.buffer.capacity();
/*     */   }
/*     */   
/*     */   public ByteBuf capacity(int paramInt)
/*     */   {
/* 315 */     throw new ReadOnlyBufferException();
/*     */   }
/*     */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\io\netty\buffer\ReadOnlyByteBuf.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */
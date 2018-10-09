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
/*     */ public class DuplicatedByteBuf
/*     */   extends AbstractDerivedByteBuf
/*     */ {
/*     */   private final ByteBuf buffer;
/*     */   
/*     */   public DuplicatedByteBuf(ByteBuf paramByteBuf)
/*     */   {
/*  37 */     super(paramByteBuf.maxCapacity());
/*     */     
/*  39 */     if ((paramByteBuf instanceof DuplicatedByteBuf)) {
/*  40 */       this.buffer = ((DuplicatedByteBuf)paramByteBuf).buffer;
/*     */     } else {
/*  42 */       this.buffer = paramByteBuf;
/*     */     }
/*     */     
/*  45 */     setIndex(paramByteBuf.readerIndex(), paramByteBuf.writerIndex());
/*     */   }
/*     */   
/*     */   public ByteBuf unwrap()
/*     */   {
/*  50 */     return this.buffer;
/*     */   }
/*     */   
/*     */   public ByteBufAllocator alloc()
/*     */   {
/*  55 */     return this.buffer.alloc();
/*     */   }
/*     */   
/*     */   public ByteOrder order()
/*     */   {
/*  60 */     return this.buffer.order();
/*     */   }
/*     */   
/*     */   public boolean isDirect()
/*     */   {
/*  65 */     return this.buffer.isDirect();
/*     */   }
/*     */   
/*     */   public int capacity()
/*     */   {
/*  70 */     return this.buffer.capacity();
/*     */   }
/*     */   
/*     */   public ByteBuf capacity(int paramInt)
/*     */   {
/*  75 */     this.buffer.capacity(paramInt);
/*  76 */     return this;
/*     */   }
/*     */   
/*     */   public boolean hasArray()
/*     */   {
/*  81 */     return this.buffer.hasArray();
/*     */   }
/*     */   
/*     */   public byte[] array()
/*     */   {
/*  86 */     return this.buffer.array();
/*     */   }
/*     */   
/*     */   public int arrayOffset()
/*     */   {
/*  91 */     return this.buffer.arrayOffset();
/*     */   }
/*     */   
/*     */   public boolean hasMemoryAddress()
/*     */   {
/*  96 */     return this.buffer.hasMemoryAddress();
/*     */   }
/*     */   
/*     */   public long memoryAddress()
/*     */   {
/* 101 */     return this.buffer.memoryAddress();
/*     */   }
/*     */   
/*     */   public byte getByte(int paramInt)
/*     */   {
/* 106 */     return _getByte(paramInt);
/*     */   }
/*     */   
/*     */   protected byte _getByte(int paramInt)
/*     */   {
/* 111 */     return this.buffer.getByte(paramInt);
/*     */   }
/*     */   
/*     */   public short getShort(int paramInt)
/*     */   {
/* 116 */     return _getShort(paramInt);
/*     */   }
/*     */   
/*     */   protected short _getShort(int paramInt)
/*     */   {
/* 121 */     return this.buffer.getShort(paramInt);
/*     */   }
/*     */   
/*     */   public int getUnsignedMedium(int paramInt)
/*     */   {
/* 126 */     return _getUnsignedMedium(paramInt);
/*     */   }
/*     */   
/*     */   protected int _getUnsignedMedium(int paramInt)
/*     */   {
/* 131 */     return this.buffer.getUnsignedMedium(paramInt);
/*     */   }
/*     */   
/*     */   public int getInt(int paramInt)
/*     */   {
/* 136 */     return _getInt(paramInt);
/*     */   }
/*     */   
/*     */   protected int _getInt(int paramInt)
/*     */   {
/* 141 */     return this.buffer.getInt(paramInt);
/*     */   }
/*     */   
/*     */   public long getLong(int paramInt)
/*     */   {
/* 146 */     return _getLong(paramInt);
/*     */   }
/*     */   
/*     */   protected long _getLong(int paramInt)
/*     */   {
/* 151 */     return this.buffer.getLong(paramInt);
/*     */   }
/*     */   
/*     */   public ByteBuf copy(int paramInt1, int paramInt2)
/*     */   {
/* 156 */     return this.buffer.copy(paramInt1, paramInt2);
/*     */   }
/*     */   
/*     */   public ByteBuf slice(int paramInt1, int paramInt2)
/*     */   {
/* 161 */     return this.buffer.slice(paramInt1, paramInt2);
/*     */   }
/*     */   
/*     */   public ByteBuf getBytes(int paramInt1, ByteBuf paramByteBuf, int paramInt2, int paramInt3)
/*     */   {
/* 166 */     this.buffer.getBytes(paramInt1, paramByteBuf, paramInt2, paramInt3);
/* 167 */     return this;
/*     */   }
/*     */   
/*     */   public ByteBuf getBytes(int paramInt1, byte[] paramArrayOfByte, int paramInt2, int paramInt3)
/*     */   {
/* 172 */     this.buffer.getBytes(paramInt1, paramArrayOfByte, paramInt2, paramInt3);
/* 173 */     return this;
/*     */   }
/*     */   
/*     */   public ByteBuf getBytes(int paramInt, ByteBuffer paramByteBuffer)
/*     */   {
/* 178 */     this.buffer.getBytes(paramInt, paramByteBuffer);
/* 179 */     return this;
/*     */   }
/*     */   
/*     */   public ByteBuf setByte(int paramInt1, int paramInt2)
/*     */   {
/* 184 */     _setByte(paramInt1, paramInt2);
/* 185 */     return this;
/*     */   }
/*     */   
/*     */   protected void _setByte(int paramInt1, int paramInt2)
/*     */   {
/* 190 */     this.buffer.setByte(paramInt1, paramInt2);
/*     */   }
/*     */   
/*     */   public ByteBuf setShort(int paramInt1, int paramInt2)
/*     */   {
/* 195 */     _setShort(paramInt1, paramInt2);
/* 196 */     return this;
/*     */   }
/*     */   
/*     */   protected void _setShort(int paramInt1, int paramInt2)
/*     */   {
/* 201 */     this.buffer.setShort(paramInt1, paramInt2);
/*     */   }
/*     */   
/*     */   public ByteBuf setMedium(int paramInt1, int paramInt2)
/*     */   {
/* 206 */     _setMedium(paramInt1, paramInt2);
/* 207 */     return this;
/*     */   }
/*     */   
/*     */   protected void _setMedium(int paramInt1, int paramInt2)
/*     */   {
/* 212 */     this.buffer.setMedium(paramInt1, paramInt2);
/*     */   }
/*     */   
/*     */   public ByteBuf setInt(int paramInt1, int paramInt2)
/*     */   {
/* 217 */     _setInt(paramInt1, paramInt2);
/* 218 */     return this;
/*     */   }
/*     */   
/*     */   protected void _setInt(int paramInt1, int paramInt2)
/*     */   {
/* 223 */     this.buffer.setInt(paramInt1, paramInt2);
/*     */   }
/*     */   
/*     */   public ByteBuf setLong(int paramInt, long paramLong)
/*     */   {
/* 228 */     _setLong(paramInt, paramLong);
/* 229 */     return this;
/*     */   }
/*     */   
/*     */   protected void _setLong(int paramInt, long paramLong)
/*     */   {
/* 234 */     this.buffer.setLong(paramInt, paramLong);
/*     */   }
/*     */   
/*     */   public ByteBuf setBytes(int paramInt1, byte[] paramArrayOfByte, int paramInt2, int paramInt3)
/*     */   {
/* 239 */     this.buffer.setBytes(paramInt1, paramArrayOfByte, paramInt2, paramInt3);
/* 240 */     return this;
/*     */   }
/*     */   
/*     */   public ByteBuf setBytes(int paramInt1, ByteBuf paramByteBuf, int paramInt2, int paramInt3)
/*     */   {
/* 245 */     this.buffer.setBytes(paramInt1, paramByteBuf, paramInt2, paramInt3);
/* 246 */     return this;
/*     */   }
/*     */   
/*     */   public ByteBuf setBytes(int paramInt, ByteBuffer paramByteBuffer)
/*     */   {
/* 251 */     this.buffer.setBytes(paramInt, paramByteBuffer);
/* 252 */     return this;
/*     */   }
/*     */   
/*     */   public ByteBuf getBytes(int paramInt1, OutputStream paramOutputStream, int paramInt2)
/*     */     throws IOException
/*     */   {
/* 258 */     this.buffer.getBytes(paramInt1, paramOutputStream, paramInt2);
/* 259 */     return this;
/*     */   }
/*     */   
/*     */   public int getBytes(int paramInt1, GatheringByteChannel paramGatheringByteChannel, int paramInt2)
/*     */     throws IOException
/*     */   {
/* 265 */     return this.buffer.getBytes(paramInt1, paramGatheringByteChannel, paramInt2);
/*     */   }
/*     */   
/*     */   public int setBytes(int paramInt1, InputStream paramInputStream, int paramInt2)
/*     */     throws IOException
/*     */   {
/* 271 */     return this.buffer.setBytes(paramInt1, paramInputStream, paramInt2);
/*     */   }
/*     */   
/*     */   public int setBytes(int paramInt1, ScatteringByteChannel paramScatteringByteChannel, int paramInt2)
/*     */     throws IOException
/*     */   {
/* 277 */     return this.buffer.setBytes(paramInt1, paramScatteringByteChannel, paramInt2);
/*     */   }
/*     */   
/*     */   public int nioBufferCount()
/*     */   {
/* 282 */     return this.buffer.nioBufferCount();
/*     */   }
/*     */   
/*     */   public ByteBuffer[] nioBuffers(int paramInt1, int paramInt2)
/*     */   {
/* 287 */     return this.buffer.nioBuffers(paramInt1, paramInt2);
/*     */   }
/*     */   
/*     */   public ByteBuffer internalNioBuffer(int paramInt1, int paramInt2)
/*     */   {
/* 292 */     return nioBuffer(paramInt1, paramInt2);
/*     */   }
/*     */   
/*     */   public int forEachByte(int paramInt1, int paramInt2, ByteBufProcessor paramByteBufProcessor)
/*     */   {
/* 297 */     return this.buffer.forEachByte(paramInt1, paramInt2, paramByteBufProcessor);
/*     */   }
/*     */   
/*     */   public int forEachByteDesc(int paramInt1, int paramInt2, ByteBufProcessor paramByteBufProcessor)
/*     */   {
/* 302 */     return this.buffer.forEachByteDesc(paramInt1, paramInt2, paramByteBufProcessor);
/*     */   }
/*     */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\io\netty\buffer\DuplicatedByteBuf.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */
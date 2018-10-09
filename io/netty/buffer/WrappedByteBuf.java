/*     */ package io.netty.buffer;
/*     */ 
/*     */ import io.netty.util.internal.StringUtil;
/*     */ import java.io.IOException;
/*     */ import java.io.InputStream;
/*     */ import java.io.OutputStream;
/*     */ import java.nio.ByteBuffer;
/*     */ import java.nio.ByteOrder;
/*     */ import java.nio.channels.GatheringByteChannel;
/*     */ import java.nio.channels.ScatteringByteChannel;
/*     */ import java.nio.charset.Charset;
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
/*     */ class WrappedByteBuf
/*     */   extends ByteBuf
/*     */ {
/*     */   protected final ByteBuf buf;
/*     */   
/*     */   protected WrappedByteBuf(ByteBuf paramByteBuf)
/*     */   {
/*  35 */     if (paramByteBuf == null) {
/*  36 */       throw new NullPointerException("buf");
/*     */     }
/*  38 */     this.buf = paramByteBuf;
/*     */   }
/*     */   
/*     */   public boolean hasMemoryAddress()
/*     */   {
/*  43 */     return this.buf.hasMemoryAddress();
/*     */   }
/*     */   
/*     */   public long memoryAddress()
/*     */   {
/*  48 */     return this.buf.memoryAddress();
/*     */   }
/*     */   
/*     */   public int capacity()
/*     */   {
/*  53 */     return this.buf.capacity();
/*     */   }
/*     */   
/*     */   public ByteBuf capacity(int paramInt)
/*     */   {
/*  58 */     this.buf.capacity(paramInt);
/*  59 */     return this;
/*     */   }
/*     */   
/*     */   public int maxCapacity()
/*     */   {
/*  64 */     return this.buf.maxCapacity();
/*     */   }
/*     */   
/*     */   public ByteBufAllocator alloc()
/*     */   {
/*  69 */     return this.buf.alloc();
/*     */   }
/*     */   
/*     */   public ByteOrder order()
/*     */   {
/*  74 */     return this.buf.order();
/*     */   }
/*     */   
/*     */   public ByteBuf order(ByteOrder paramByteOrder)
/*     */   {
/*  79 */     return this.buf.order(paramByteOrder);
/*     */   }
/*     */   
/*     */   public ByteBuf unwrap()
/*     */   {
/*  84 */     return this.buf;
/*     */   }
/*     */   
/*     */   public boolean isDirect()
/*     */   {
/*  89 */     return this.buf.isDirect();
/*     */   }
/*     */   
/*     */   public int readerIndex()
/*     */   {
/*  94 */     return this.buf.readerIndex();
/*     */   }
/*     */   
/*     */   public ByteBuf readerIndex(int paramInt)
/*     */   {
/*  99 */     this.buf.readerIndex(paramInt);
/* 100 */     return this;
/*     */   }
/*     */   
/*     */   public int writerIndex()
/*     */   {
/* 105 */     return this.buf.writerIndex();
/*     */   }
/*     */   
/*     */   public ByteBuf writerIndex(int paramInt)
/*     */   {
/* 110 */     this.buf.writerIndex(paramInt);
/* 111 */     return this;
/*     */   }
/*     */   
/*     */   public ByteBuf setIndex(int paramInt1, int paramInt2)
/*     */   {
/* 116 */     this.buf.setIndex(paramInt1, paramInt2);
/* 117 */     return this;
/*     */   }
/*     */   
/*     */   public int readableBytes()
/*     */   {
/* 122 */     return this.buf.readableBytes();
/*     */   }
/*     */   
/*     */   public int writableBytes()
/*     */   {
/* 127 */     return this.buf.writableBytes();
/*     */   }
/*     */   
/*     */   public int maxWritableBytes()
/*     */   {
/* 132 */     return this.buf.maxWritableBytes();
/*     */   }
/*     */   
/*     */   public boolean isReadable()
/*     */   {
/* 137 */     return this.buf.isReadable();
/*     */   }
/*     */   
/*     */   public boolean isWritable()
/*     */   {
/* 142 */     return this.buf.isWritable();
/*     */   }
/*     */   
/*     */   public ByteBuf clear()
/*     */   {
/* 147 */     this.buf.clear();
/* 148 */     return this;
/*     */   }
/*     */   
/*     */   public ByteBuf markReaderIndex()
/*     */   {
/* 153 */     this.buf.markReaderIndex();
/* 154 */     return this;
/*     */   }
/*     */   
/*     */   public ByteBuf resetReaderIndex()
/*     */   {
/* 159 */     this.buf.resetReaderIndex();
/* 160 */     return this;
/*     */   }
/*     */   
/*     */   public ByteBuf markWriterIndex()
/*     */   {
/* 165 */     this.buf.markWriterIndex();
/* 166 */     return this;
/*     */   }
/*     */   
/*     */   public ByteBuf resetWriterIndex()
/*     */   {
/* 171 */     this.buf.resetWriterIndex();
/* 172 */     return this;
/*     */   }
/*     */   
/*     */   public ByteBuf discardReadBytes()
/*     */   {
/* 177 */     this.buf.discardReadBytes();
/* 178 */     return this;
/*     */   }
/*     */   
/*     */   public ByteBuf discardSomeReadBytes()
/*     */   {
/* 183 */     this.buf.discardSomeReadBytes();
/* 184 */     return this;
/*     */   }
/*     */   
/*     */   public ByteBuf ensureWritable(int paramInt)
/*     */   {
/* 189 */     this.buf.ensureWritable(paramInt);
/* 190 */     return this;
/*     */   }
/*     */   
/*     */   public int ensureWritable(int paramInt, boolean paramBoolean)
/*     */   {
/* 195 */     return this.buf.ensureWritable(paramInt, paramBoolean);
/*     */   }
/*     */   
/*     */   public boolean getBoolean(int paramInt)
/*     */   {
/* 200 */     return this.buf.getBoolean(paramInt);
/*     */   }
/*     */   
/*     */   public byte getByte(int paramInt)
/*     */   {
/* 205 */     return this.buf.getByte(paramInt);
/*     */   }
/*     */   
/*     */   public short getUnsignedByte(int paramInt)
/*     */   {
/* 210 */     return this.buf.getUnsignedByte(paramInt);
/*     */   }
/*     */   
/*     */   public short getShort(int paramInt)
/*     */   {
/* 215 */     return this.buf.getShort(paramInt);
/*     */   }
/*     */   
/*     */   public int getUnsignedShort(int paramInt)
/*     */   {
/* 220 */     return this.buf.getUnsignedShort(paramInt);
/*     */   }
/*     */   
/*     */   public int getMedium(int paramInt)
/*     */   {
/* 225 */     return this.buf.getMedium(paramInt);
/*     */   }
/*     */   
/*     */   public int getUnsignedMedium(int paramInt)
/*     */   {
/* 230 */     return this.buf.getUnsignedMedium(paramInt);
/*     */   }
/*     */   
/*     */   public int getInt(int paramInt)
/*     */   {
/* 235 */     return this.buf.getInt(paramInt);
/*     */   }
/*     */   
/*     */   public long getUnsignedInt(int paramInt)
/*     */   {
/* 240 */     return this.buf.getUnsignedInt(paramInt);
/*     */   }
/*     */   
/*     */   public long getLong(int paramInt)
/*     */   {
/* 245 */     return this.buf.getLong(paramInt);
/*     */   }
/*     */   
/*     */   public char getChar(int paramInt)
/*     */   {
/* 250 */     return this.buf.getChar(paramInt);
/*     */   }
/*     */   
/*     */   public float getFloat(int paramInt)
/*     */   {
/* 255 */     return this.buf.getFloat(paramInt);
/*     */   }
/*     */   
/*     */   public double getDouble(int paramInt)
/*     */   {
/* 260 */     return this.buf.getDouble(paramInt);
/*     */   }
/*     */   
/*     */   public ByteBuf getBytes(int paramInt, ByteBuf paramByteBuf)
/*     */   {
/* 265 */     this.buf.getBytes(paramInt, paramByteBuf);
/* 266 */     return this;
/*     */   }
/*     */   
/*     */   public ByteBuf getBytes(int paramInt1, ByteBuf paramByteBuf, int paramInt2)
/*     */   {
/* 271 */     this.buf.getBytes(paramInt1, paramByteBuf, paramInt2);
/* 272 */     return this;
/*     */   }
/*     */   
/*     */   public ByteBuf getBytes(int paramInt1, ByteBuf paramByteBuf, int paramInt2, int paramInt3)
/*     */   {
/* 277 */     this.buf.getBytes(paramInt1, paramByteBuf, paramInt2, paramInt3);
/* 278 */     return this;
/*     */   }
/*     */   
/*     */   public ByteBuf getBytes(int paramInt, byte[] paramArrayOfByte)
/*     */   {
/* 283 */     this.buf.getBytes(paramInt, paramArrayOfByte);
/* 284 */     return this;
/*     */   }
/*     */   
/*     */   public ByteBuf getBytes(int paramInt1, byte[] paramArrayOfByte, int paramInt2, int paramInt3)
/*     */   {
/* 289 */     this.buf.getBytes(paramInt1, paramArrayOfByte, paramInt2, paramInt3);
/* 290 */     return this;
/*     */   }
/*     */   
/*     */   public ByteBuf getBytes(int paramInt, ByteBuffer paramByteBuffer)
/*     */   {
/* 295 */     this.buf.getBytes(paramInt, paramByteBuffer);
/* 296 */     return this;
/*     */   }
/*     */   
/*     */   public ByteBuf getBytes(int paramInt1, OutputStream paramOutputStream, int paramInt2) throws IOException
/*     */   {
/* 301 */     this.buf.getBytes(paramInt1, paramOutputStream, paramInt2);
/* 302 */     return this;
/*     */   }
/*     */   
/*     */   public int getBytes(int paramInt1, GatheringByteChannel paramGatheringByteChannel, int paramInt2) throws IOException
/*     */   {
/* 307 */     return this.buf.getBytes(paramInt1, paramGatheringByteChannel, paramInt2);
/*     */   }
/*     */   
/*     */   public ByteBuf setBoolean(int paramInt, boolean paramBoolean)
/*     */   {
/* 312 */     this.buf.setBoolean(paramInt, paramBoolean);
/* 313 */     return this;
/*     */   }
/*     */   
/*     */   public ByteBuf setByte(int paramInt1, int paramInt2)
/*     */   {
/* 318 */     this.buf.setByte(paramInt1, paramInt2);
/* 319 */     return this;
/*     */   }
/*     */   
/*     */   public ByteBuf setShort(int paramInt1, int paramInt2)
/*     */   {
/* 324 */     this.buf.setShort(paramInt1, paramInt2);
/* 325 */     return this;
/*     */   }
/*     */   
/*     */   public ByteBuf setMedium(int paramInt1, int paramInt2)
/*     */   {
/* 330 */     this.buf.setMedium(paramInt1, paramInt2);
/* 331 */     return this;
/*     */   }
/*     */   
/*     */   public ByteBuf setInt(int paramInt1, int paramInt2)
/*     */   {
/* 336 */     this.buf.setInt(paramInt1, paramInt2);
/* 337 */     return this;
/*     */   }
/*     */   
/*     */   public ByteBuf setLong(int paramInt, long paramLong)
/*     */   {
/* 342 */     this.buf.setLong(paramInt, paramLong);
/* 343 */     return this;
/*     */   }
/*     */   
/*     */   public ByteBuf setChar(int paramInt1, int paramInt2)
/*     */   {
/* 348 */     this.buf.setChar(paramInt1, paramInt2);
/* 349 */     return this;
/*     */   }
/*     */   
/*     */   public ByteBuf setFloat(int paramInt, float paramFloat)
/*     */   {
/* 354 */     this.buf.setFloat(paramInt, paramFloat);
/* 355 */     return this;
/*     */   }
/*     */   
/*     */   public ByteBuf setDouble(int paramInt, double paramDouble)
/*     */   {
/* 360 */     this.buf.setDouble(paramInt, paramDouble);
/* 361 */     return this;
/*     */   }
/*     */   
/*     */   public ByteBuf setBytes(int paramInt, ByteBuf paramByteBuf)
/*     */   {
/* 366 */     this.buf.setBytes(paramInt, paramByteBuf);
/* 367 */     return this;
/*     */   }
/*     */   
/*     */   public ByteBuf setBytes(int paramInt1, ByteBuf paramByteBuf, int paramInt2)
/*     */   {
/* 372 */     this.buf.setBytes(paramInt1, paramByteBuf, paramInt2);
/* 373 */     return this;
/*     */   }
/*     */   
/*     */   public ByteBuf setBytes(int paramInt1, ByteBuf paramByteBuf, int paramInt2, int paramInt3)
/*     */   {
/* 378 */     this.buf.setBytes(paramInt1, paramByteBuf, paramInt2, paramInt3);
/* 379 */     return this;
/*     */   }
/*     */   
/*     */   public ByteBuf setBytes(int paramInt, byte[] paramArrayOfByte)
/*     */   {
/* 384 */     this.buf.setBytes(paramInt, paramArrayOfByte);
/* 385 */     return this;
/*     */   }
/*     */   
/*     */   public ByteBuf setBytes(int paramInt1, byte[] paramArrayOfByte, int paramInt2, int paramInt3)
/*     */   {
/* 390 */     this.buf.setBytes(paramInt1, paramArrayOfByte, paramInt2, paramInt3);
/* 391 */     return this;
/*     */   }
/*     */   
/*     */   public ByteBuf setBytes(int paramInt, ByteBuffer paramByteBuffer)
/*     */   {
/* 396 */     this.buf.setBytes(paramInt, paramByteBuffer);
/* 397 */     return this;
/*     */   }
/*     */   
/*     */   public int setBytes(int paramInt1, InputStream paramInputStream, int paramInt2) throws IOException
/*     */   {
/* 402 */     return this.buf.setBytes(paramInt1, paramInputStream, paramInt2);
/*     */   }
/*     */   
/*     */   public int setBytes(int paramInt1, ScatteringByteChannel paramScatteringByteChannel, int paramInt2) throws IOException
/*     */   {
/* 407 */     return this.buf.setBytes(paramInt1, paramScatteringByteChannel, paramInt2);
/*     */   }
/*     */   
/*     */   public ByteBuf setZero(int paramInt1, int paramInt2)
/*     */   {
/* 412 */     this.buf.setZero(paramInt1, paramInt2);
/* 413 */     return this;
/*     */   }
/*     */   
/*     */   public boolean readBoolean()
/*     */   {
/* 418 */     return this.buf.readBoolean();
/*     */   }
/*     */   
/*     */   public byte readByte()
/*     */   {
/* 423 */     return this.buf.readByte();
/*     */   }
/*     */   
/*     */   public short readUnsignedByte()
/*     */   {
/* 428 */     return this.buf.readUnsignedByte();
/*     */   }
/*     */   
/*     */   public short readShort()
/*     */   {
/* 433 */     return this.buf.readShort();
/*     */   }
/*     */   
/*     */   public int readUnsignedShort()
/*     */   {
/* 438 */     return this.buf.readUnsignedShort();
/*     */   }
/*     */   
/*     */   public int readMedium()
/*     */   {
/* 443 */     return this.buf.readMedium();
/*     */   }
/*     */   
/*     */   public int readUnsignedMedium()
/*     */   {
/* 448 */     return this.buf.readUnsignedMedium();
/*     */   }
/*     */   
/*     */   public int readInt()
/*     */   {
/* 453 */     return this.buf.readInt();
/*     */   }
/*     */   
/*     */   public long readUnsignedInt()
/*     */   {
/* 458 */     return this.buf.readUnsignedInt();
/*     */   }
/*     */   
/*     */   public long readLong()
/*     */   {
/* 463 */     return this.buf.readLong();
/*     */   }
/*     */   
/*     */   public char readChar()
/*     */   {
/* 468 */     return this.buf.readChar();
/*     */   }
/*     */   
/*     */   public float readFloat()
/*     */   {
/* 473 */     return this.buf.readFloat();
/*     */   }
/*     */   
/*     */   public double readDouble()
/*     */   {
/* 478 */     return this.buf.readDouble();
/*     */   }
/*     */   
/*     */   public ByteBuf readBytes(int paramInt)
/*     */   {
/* 483 */     return this.buf.readBytes(paramInt);
/*     */   }
/*     */   
/*     */   public ByteBuf readSlice(int paramInt)
/*     */   {
/* 488 */     return this.buf.readSlice(paramInt);
/*     */   }
/*     */   
/*     */   public ByteBuf readBytes(ByteBuf paramByteBuf)
/*     */   {
/* 493 */     this.buf.readBytes(paramByteBuf);
/* 494 */     return this;
/*     */   }
/*     */   
/*     */   public ByteBuf readBytes(ByteBuf paramByteBuf, int paramInt)
/*     */   {
/* 499 */     this.buf.readBytes(paramByteBuf, paramInt);
/* 500 */     return this;
/*     */   }
/*     */   
/*     */   public ByteBuf readBytes(ByteBuf paramByteBuf, int paramInt1, int paramInt2)
/*     */   {
/* 505 */     this.buf.readBytes(paramByteBuf, paramInt1, paramInt2);
/* 506 */     return this;
/*     */   }
/*     */   
/*     */   public ByteBuf readBytes(byte[] paramArrayOfByte)
/*     */   {
/* 511 */     this.buf.readBytes(paramArrayOfByte);
/* 512 */     return this;
/*     */   }
/*     */   
/*     */   public ByteBuf readBytes(byte[] paramArrayOfByte, int paramInt1, int paramInt2)
/*     */   {
/* 517 */     this.buf.readBytes(paramArrayOfByte, paramInt1, paramInt2);
/* 518 */     return this;
/*     */   }
/*     */   
/*     */   public ByteBuf readBytes(ByteBuffer paramByteBuffer)
/*     */   {
/* 523 */     this.buf.readBytes(paramByteBuffer);
/* 524 */     return this;
/*     */   }
/*     */   
/*     */   public ByteBuf readBytes(OutputStream paramOutputStream, int paramInt) throws IOException
/*     */   {
/* 529 */     this.buf.readBytes(paramOutputStream, paramInt);
/* 530 */     return this;
/*     */   }
/*     */   
/*     */   public int readBytes(GatheringByteChannel paramGatheringByteChannel, int paramInt) throws IOException
/*     */   {
/* 535 */     return this.buf.readBytes(paramGatheringByteChannel, paramInt);
/*     */   }
/*     */   
/*     */   public ByteBuf skipBytes(int paramInt)
/*     */   {
/* 540 */     this.buf.skipBytes(paramInt);
/* 541 */     return this;
/*     */   }
/*     */   
/*     */   public ByteBuf writeBoolean(boolean paramBoolean)
/*     */   {
/* 546 */     this.buf.writeBoolean(paramBoolean);
/* 547 */     return this;
/*     */   }
/*     */   
/*     */   public ByteBuf writeByte(int paramInt)
/*     */   {
/* 552 */     this.buf.writeByte(paramInt);
/* 553 */     return this;
/*     */   }
/*     */   
/*     */   public ByteBuf writeShort(int paramInt)
/*     */   {
/* 558 */     this.buf.writeShort(paramInt);
/* 559 */     return this;
/*     */   }
/*     */   
/*     */   public ByteBuf writeMedium(int paramInt)
/*     */   {
/* 564 */     this.buf.writeMedium(paramInt);
/* 565 */     return this;
/*     */   }
/*     */   
/*     */   public ByteBuf writeInt(int paramInt)
/*     */   {
/* 570 */     this.buf.writeInt(paramInt);
/* 571 */     return this;
/*     */   }
/*     */   
/*     */   public ByteBuf writeLong(long paramLong)
/*     */   {
/* 576 */     this.buf.writeLong(paramLong);
/* 577 */     return this;
/*     */   }
/*     */   
/*     */   public ByteBuf writeChar(int paramInt)
/*     */   {
/* 582 */     this.buf.writeChar(paramInt);
/* 583 */     return this;
/*     */   }
/*     */   
/*     */   public ByteBuf writeFloat(float paramFloat)
/*     */   {
/* 588 */     this.buf.writeFloat(paramFloat);
/* 589 */     return this;
/*     */   }
/*     */   
/*     */   public ByteBuf writeDouble(double paramDouble)
/*     */   {
/* 594 */     this.buf.writeDouble(paramDouble);
/* 595 */     return this;
/*     */   }
/*     */   
/*     */   public ByteBuf writeBytes(ByteBuf paramByteBuf)
/*     */   {
/* 600 */     this.buf.writeBytes(paramByteBuf);
/* 601 */     return this;
/*     */   }
/*     */   
/*     */   public ByteBuf writeBytes(ByteBuf paramByteBuf, int paramInt)
/*     */   {
/* 606 */     this.buf.writeBytes(paramByteBuf, paramInt);
/* 607 */     return this;
/*     */   }
/*     */   
/*     */   public ByteBuf writeBytes(ByteBuf paramByteBuf, int paramInt1, int paramInt2)
/*     */   {
/* 612 */     this.buf.writeBytes(paramByteBuf, paramInt1, paramInt2);
/* 613 */     return this;
/*     */   }
/*     */   
/*     */   public ByteBuf writeBytes(byte[] paramArrayOfByte)
/*     */   {
/* 618 */     this.buf.writeBytes(paramArrayOfByte);
/* 619 */     return this;
/*     */   }
/*     */   
/*     */   public ByteBuf writeBytes(byte[] paramArrayOfByte, int paramInt1, int paramInt2)
/*     */   {
/* 624 */     this.buf.writeBytes(paramArrayOfByte, paramInt1, paramInt2);
/* 625 */     return this;
/*     */   }
/*     */   
/*     */   public ByteBuf writeBytes(ByteBuffer paramByteBuffer)
/*     */   {
/* 630 */     this.buf.writeBytes(paramByteBuffer);
/* 631 */     return this;
/*     */   }
/*     */   
/*     */   public int writeBytes(InputStream paramInputStream, int paramInt) throws IOException
/*     */   {
/* 636 */     return this.buf.writeBytes(paramInputStream, paramInt);
/*     */   }
/*     */   
/*     */   public int writeBytes(ScatteringByteChannel paramScatteringByteChannel, int paramInt) throws IOException
/*     */   {
/* 641 */     return this.buf.writeBytes(paramScatteringByteChannel, paramInt);
/*     */   }
/*     */   
/*     */   public ByteBuf writeZero(int paramInt)
/*     */   {
/* 646 */     this.buf.writeZero(paramInt);
/* 647 */     return this;
/*     */   }
/*     */   
/*     */   public int indexOf(int paramInt1, int paramInt2, byte paramByte)
/*     */   {
/* 652 */     return this.buf.indexOf(paramInt1, paramInt2, paramByte);
/*     */   }
/*     */   
/*     */   public int bytesBefore(byte paramByte)
/*     */   {
/* 657 */     return this.buf.bytesBefore(paramByte);
/*     */   }
/*     */   
/*     */   public int bytesBefore(int paramInt, byte paramByte)
/*     */   {
/* 662 */     return this.buf.bytesBefore(paramInt, paramByte);
/*     */   }
/*     */   
/*     */   public int bytesBefore(int paramInt1, int paramInt2, byte paramByte)
/*     */   {
/* 667 */     return this.buf.bytesBefore(paramInt1, paramInt2, paramByte);
/*     */   }
/*     */   
/*     */   public int forEachByte(ByteBufProcessor paramByteBufProcessor)
/*     */   {
/* 672 */     return this.buf.forEachByte(paramByteBufProcessor);
/*     */   }
/*     */   
/*     */   public int forEachByte(int paramInt1, int paramInt2, ByteBufProcessor paramByteBufProcessor)
/*     */   {
/* 677 */     return this.buf.forEachByte(paramInt1, paramInt2, paramByteBufProcessor);
/*     */   }
/*     */   
/*     */   public int forEachByteDesc(ByteBufProcessor paramByteBufProcessor)
/*     */   {
/* 682 */     return this.buf.forEachByteDesc(paramByteBufProcessor);
/*     */   }
/*     */   
/*     */   public int forEachByteDesc(int paramInt1, int paramInt2, ByteBufProcessor paramByteBufProcessor)
/*     */   {
/* 687 */     return this.buf.forEachByteDesc(paramInt1, paramInt2, paramByteBufProcessor);
/*     */   }
/*     */   
/*     */   public ByteBuf copy()
/*     */   {
/* 692 */     return this.buf.copy();
/*     */   }
/*     */   
/*     */   public ByteBuf copy(int paramInt1, int paramInt2)
/*     */   {
/* 697 */     return this.buf.copy(paramInt1, paramInt2);
/*     */   }
/*     */   
/*     */   public ByteBuf slice()
/*     */   {
/* 702 */     return this.buf.slice();
/*     */   }
/*     */   
/*     */   public ByteBuf slice(int paramInt1, int paramInt2)
/*     */   {
/* 707 */     return this.buf.slice(paramInt1, paramInt2);
/*     */   }
/*     */   
/*     */   public ByteBuf duplicate()
/*     */   {
/* 712 */     return this.buf.duplicate();
/*     */   }
/*     */   
/*     */   public int nioBufferCount()
/*     */   {
/* 717 */     return this.buf.nioBufferCount();
/*     */   }
/*     */   
/*     */   public ByteBuffer nioBuffer()
/*     */   {
/* 722 */     return this.buf.nioBuffer();
/*     */   }
/*     */   
/*     */   public ByteBuffer nioBuffer(int paramInt1, int paramInt2)
/*     */   {
/* 727 */     return this.buf.nioBuffer(paramInt1, paramInt2);
/*     */   }
/*     */   
/*     */   public ByteBuffer[] nioBuffers()
/*     */   {
/* 732 */     return this.buf.nioBuffers();
/*     */   }
/*     */   
/*     */   public ByteBuffer[] nioBuffers(int paramInt1, int paramInt2)
/*     */   {
/* 737 */     return this.buf.nioBuffers(paramInt1, paramInt2);
/*     */   }
/*     */   
/*     */   public ByteBuffer internalNioBuffer(int paramInt1, int paramInt2)
/*     */   {
/* 742 */     return this.buf.internalNioBuffer(paramInt1, paramInt2);
/*     */   }
/*     */   
/*     */   public boolean hasArray()
/*     */   {
/* 747 */     return this.buf.hasArray();
/*     */   }
/*     */   
/*     */   public byte[] array()
/*     */   {
/* 752 */     return this.buf.array();
/*     */   }
/*     */   
/*     */   public int arrayOffset()
/*     */   {
/* 757 */     return this.buf.arrayOffset();
/*     */   }
/*     */   
/*     */   public String toString(Charset paramCharset)
/*     */   {
/* 762 */     return this.buf.toString(paramCharset);
/*     */   }
/*     */   
/*     */   public String toString(int paramInt1, int paramInt2, Charset paramCharset)
/*     */   {
/* 767 */     return this.buf.toString(paramInt1, paramInt2, paramCharset);
/*     */   }
/*     */   
/*     */   public int hashCode()
/*     */   {
/* 772 */     return this.buf.hashCode();
/*     */   }
/*     */   
/*     */ 
/*     */   public boolean equals(Object paramObject)
/*     */   {
/* 778 */     return this.buf.equals(paramObject);
/*     */   }
/*     */   
/*     */   public int compareTo(ByteBuf paramByteBuf)
/*     */   {
/* 783 */     return this.buf.compareTo(paramByteBuf);
/*     */   }
/*     */   
/*     */   public String toString()
/*     */   {
/* 788 */     return StringUtil.simpleClassName(this) + '(' + this.buf.toString() + ')';
/*     */   }
/*     */   
/*     */   public ByteBuf retain(int paramInt)
/*     */   {
/* 793 */     this.buf.retain(paramInt);
/* 794 */     return this;
/*     */   }
/*     */   
/*     */   public ByteBuf retain()
/*     */   {
/* 799 */     this.buf.retain();
/* 800 */     return this;
/*     */   }
/*     */   
/*     */   public boolean isReadable(int paramInt)
/*     */   {
/* 805 */     return this.buf.isReadable(paramInt);
/*     */   }
/*     */   
/*     */   public boolean isWritable(int paramInt)
/*     */   {
/* 810 */     return this.buf.isWritable(paramInt);
/*     */   }
/*     */   
/*     */   public int refCnt()
/*     */   {
/* 815 */     return this.buf.refCnt();
/*     */   }
/*     */   
/*     */   public boolean release()
/*     */   {
/* 820 */     return this.buf.release();
/*     */   }
/*     */   
/*     */   public boolean release(int paramInt)
/*     */   {
/* 825 */     return this.buf.release(paramInt);
/*     */   }
/*     */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\io\netty\buffer\WrappedByteBuf.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */
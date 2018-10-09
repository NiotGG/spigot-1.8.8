/*     */ package io.netty.buffer;
/*     */ 
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
/*     */ 
/*     */ public class SwappedByteBuf
/*     */   extends ByteBuf
/*     */ {
/*     */   private final ByteBuf buf;
/*     */   private final ByteOrder order;
/*     */   
/*     */   public SwappedByteBuf(ByteBuf paramByteBuf)
/*     */   {
/*  36 */     if (paramByteBuf == null) {
/*  37 */       throw new NullPointerException("buf");
/*     */     }
/*  39 */     this.buf = paramByteBuf;
/*  40 */     if (paramByteBuf.order() == ByteOrder.BIG_ENDIAN) {
/*  41 */       this.order = ByteOrder.LITTLE_ENDIAN;
/*     */     } else {
/*  43 */       this.order = ByteOrder.BIG_ENDIAN;
/*     */     }
/*     */   }
/*     */   
/*     */   public ByteOrder order()
/*     */   {
/*  49 */     return this.order;
/*     */   }
/*     */   
/*     */   public ByteBuf order(ByteOrder paramByteOrder)
/*     */   {
/*  54 */     if (paramByteOrder == null) {
/*  55 */       throw new NullPointerException("endianness");
/*     */     }
/*  57 */     if (paramByteOrder == this.order) {
/*  58 */       return this;
/*     */     }
/*  60 */     return this.buf;
/*     */   }
/*     */   
/*     */   public ByteBuf unwrap()
/*     */   {
/*  65 */     return this.buf.unwrap();
/*     */   }
/*     */   
/*     */   public ByteBufAllocator alloc()
/*     */   {
/*  70 */     return this.buf.alloc();
/*     */   }
/*     */   
/*     */   public int capacity()
/*     */   {
/*  75 */     return this.buf.capacity();
/*     */   }
/*     */   
/*     */   public ByteBuf capacity(int paramInt)
/*     */   {
/*  80 */     this.buf.capacity(paramInt);
/*  81 */     return this;
/*     */   }
/*     */   
/*     */   public int maxCapacity()
/*     */   {
/*  86 */     return this.buf.maxCapacity();
/*     */   }
/*     */   
/*     */   public boolean isDirect()
/*     */   {
/*  91 */     return this.buf.isDirect();
/*     */   }
/*     */   
/*     */   public int readerIndex()
/*     */   {
/*  96 */     return this.buf.readerIndex();
/*     */   }
/*     */   
/*     */   public ByteBuf readerIndex(int paramInt)
/*     */   {
/* 101 */     this.buf.readerIndex(paramInt);
/* 102 */     return this;
/*     */   }
/*     */   
/*     */   public int writerIndex()
/*     */   {
/* 107 */     return this.buf.writerIndex();
/*     */   }
/*     */   
/*     */   public ByteBuf writerIndex(int paramInt)
/*     */   {
/* 112 */     this.buf.writerIndex(paramInt);
/* 113 */     return this;
/*     */   }
/*     */   
/*     */   public ByteBuf setIndex(int paramInt1, int paramInt2)
/*     */   {
/* 118 */     this.buf.setIndex(paramInt1, paramInt2);
/* 119 */     return this;
/*     */   }
/*     */   
/*     */   public int readableBytes()
/*     */   {
/* 124 */     return this.buf.readableBytes();
/*     */   }
/*     */   
/*     */   public int writableBytes()
/*     */   {
/* 129 */     return this.buf.writableBytes();
/*     */   }
/*     */   
/*     */   public int maxWritableBytes()
/*     */   {
/* 134 */     return this.buf.maxWritableBytes();
/*     */   }
/*     */   
/*     */   public boolean isReadable()
/*     */   {
/* 139 */     return this.buf.isReadable();
/*     */   }
/*     */   
/*     */   public boolean isReadable(int paramInt)
/*     */   {
/* 144 */     return this.buf.isReadable(paramInt);
/*     */   }
/*     */   
/*     */   public boolean isWritable()
/*     */   {
/* 149 */     return this.buf.isWritable();
/*     */   }
/*     */   
/*     */   public boolean isWritable(int paramInt)
/*     */   {
/* 154 */     return this.buf.isWritable(paramInt);
/*     */   }
/*     */   
/*     */   public ByteBuf clear()
/*     */   {
/* 159 */     this.buf.clear();
/* 160 */     return this;
/*     */   }
/*     */   
/*     */   public ByteBuf markReaderIndex()
/*     */   {
/* 165 */     this.buf.markReaderIndex();
/* 166 */     return this;
/*     */   }
/*     */   
/*     */   public ByteBuf resetReaderIndex()
/*     */   {
/* 171 */     this.buf.resetReaderIndex();
/* 172 */     return this;
/*     */   }
/*     */   
/*     */   public ByteBuf markWriterIndex()
/*     */   {
/* 177 */     this.buf.markWriterIndex();
/* 178 */     return this;
/*     */   }
/*     */   
/*     */   public ByteBuf resetWriterIndex()
/*     */   {
/* 183 */     this.buf.resetWriterIndex();
/* 184 */     return this;
/*     */   }
/*     */   
/*     */   public ByteBuf discardReadBytes()
/*     */   {
/* 189 */     this.buf.discardReadBytes();
/* 190 */     return this;
/*     */   }
/*     */   
/*     */   public ByteBuf discardSomeReadBytes()
/*     */   {
/* 195 */     this.buf.discardSomeReadBytes();
/* 196 */     return this;
/*     */   }
/*     */   
/*     */   public ByteBuf ensureWritable(int paramInt)
/*     */   {
/* 201 */     this.buf.ensureWritable(paramInt);
/* 202 */     return this;
/*     */   }
/*     */   
/*     */   public int ensureWritable(int paramInt, boolean paramBoolean)
/*     */   {
/* 207 */     return this.buf.ensureWritable(paramInt, paramBoolean);
/*     */   }
/*     */   
/*     */   public boolean getBoolean(int paramInt)
/*     */   {
/* 212 */     return this.buf.getBoolean(paramInt);
/*     */   }
/*     */   
/*     */   public byte getByte(int paramInt)
/*     */   {
/* 217 */     return this.buf.getByte(paramInt);
/*     */   }
/*     */   
/*     */   public short getUnsignedByte(int paramInt)
/*     */   {
/* 222 */     return this.buf.getUnsignedByte(paramInt);
/*     */   }
/*     */   
/*     */   public short getShort(int paramInt)
/*     */   {
/* 227 */     return ByteBufUtil.swapShort(this.buf.getShort(paramInt));
/*     */   }
/*     */   
/*     */   public int getUnsignedShort(int paramInt)
/*     */   {
/* 232 */     return getShort(paramInt) & 0xFFFF;
/*     */   }
/*     */   
/*     */   public int getMedium(int paramInt)
/*     */   {
/* 237 */     return ByteBufUtil.swapMedium(this.buf.getMedium(paramInt));
/*     */   }
/*     */   
/*     */   public int getUnsignedMedium(int paramInt)
/*     */   {
/* 242 */     return getMedium(paramInt) & 0xFFFFFF;
/*     */   }
/*     */   
/*     */   public int getInt(int paramInt)
/*     */   {
/* 247 */     return ByteBufUtil.swapInt(this.buf.getInt(paramInt));
/*     */   }
/*     */   
/*     */   public long getUnsignedInt(int paramInt)
/*     */   {
/* 252 */     return getInt(paramInt) & 0xFFFFFFFF;
/*     */   }
/*     */   
/*     */   public long getLong(int paramInt)
/*     */   {
/* 257 */     return ByteBufUtil.swapLong(this.buf.getLong(paramInt));
/*     */   }
/*     */   
/*     */   public char getChar(int paramInt)
/*     */   {
/* 262 */     return (char)getShort(paramInt);
/*     */   }
/*     */   
/*     */   public float getFloat(int paramInt)
/*     */   {
/* 267 */     return Float.intBitsToFloat(getInt(paramInt));
/*     */   }
/*     */   
/*     */   public double getDouble(int paramInt)
/*     */   {
/* 272 */     return Double.longBitsToDouble(getLong(paramInt));
/*     */   }
/*     */   
/*     */   public ByteBuf getBytes(int paramInt, ByteBuf paramByteBuf)
/*     */   {
/* 277 */     this.buf.getBytes(paramInt, paramByteBuf);
/* 278 */     return this;
/*     */   }
/*     */   
/*     */   public ByteBuf getBytes(int paramInt1, ByteBuf paramByteBuf, int paramInt2)
/*     */   {
/* 283 */     this.buf.getBytes(paramInt1, paramByteBuf, paramInt2);
/* 284 */     return this;
/*     */   }
/*     */   
/*     */   public ByteBuf getBytes(int paramInt1, ByteBuf paramByteBuf, int paramInt2, int paramInt3)
/*     */   {
/* 289 */     this.buf.getBytes(paramInt1, paramByteBuf, paramInt2, paramInt3);
/* 290 */     return this;
/*     */   }
/*     */   
/*     */   public ByteBuf getBytes(int paramInt, byte[] paramArrayOfByte)
/*     */   {
/* 295 */     this.buf.getBytes(paramInt, paramArrayOfByte);
/* 296 */     return this;
/*     */   }
/*     */   
/*     */   public ByteBuf getBytes(int paramInt1, byte[] paramArrayOfByte, int paramInt2, int paramInt3)
/*     */   {
/* 301 */     this.buf.getBytes(paramInt1, paramArrayOfByte, paramInt2, paramInt3);
/* 302 */     return this;
/*     */   }
/*     */   
/*     */   public ByteBuf getBytes(int paramInt, ByteBuffer paramByteBuffer)
/*     */   {
/* 307 */     this.buf.getBytes(paramInt, paramByteBuffer);
/* 308 */     return this;
/*     */   }
/*     */   
/*     */   public ByteBuf getBytes(int paramInt1, OutputStream paramOutputStream, int paramInt2) throws IOException
/*     */   {
/* 313 */     this.buf.getBytes(paramInt1, paramOutputStream, paramInt2);
/* 314 */     return this;
/*     */   }
/*     */   
/*     */   public int getBytes(int paramInt1, GatheringByteChannel paramGatheringByteChannel, int paramInt2) throws IOException
/*     */   {
/* 319 */     return this.buf.getBytes(paramInt1, paramGatheringByteChannel, paramInt2);
/*     */   }
/*     */   
/*     */   public ByteBuf setBoolean(int paramInt, boolean paramBoolean)
/*     */   {
/* 324 */     this.buf.setBoolean(paramInt, paramBoolean);
/* 325 */     return this;
/*     */   }
/*     */   
/*     */   public ByteBuf setByte(int paramInt1, int paramInt2)
/*     */   {
/* 330 */     this.buf.setByte(paramInt1, paramInt2);
/* 331 */     return this;
/*     */   }
/*     */   
/*     */   public ByteBuf setShort(int paramInt1, int paramInt2)
/*     */   {
/* 336 */     this.buf.setShort(paramInt1, ByteBufUtil.swapShort((short)paramInt2));
/* 337 */     return this;
/*     */   }
/*     */   
/*     */   public ByteBuf setMedium(int paramInt1, int paramInt2)
/*     */   {
/* 342 */     this.buf.setMedium(paramInt1, ByteBufUtil.swapMedium(paramInt2));
/* 343 */     return this;
/*     */   }
/*     */   
/*     */   public ByteBuf setInt(int paramInt1, int paramInt2)
/*     */   {
/* 348 */     this.buf.setInt(paramInt1, ByteBufUtil.swapInt(paramInt2));
/* 349 */     return this;
/*     */   }
/*     */   
/*     */   public ByteBuf setLong(int paramInt, long paramLong)
/*     */   {
/* 354 */     this.buf.setLong(paramInt, ByteBufUtil.swapLong(paramLong));
/* 355 */     return this;
/*     */   }
/*     */   
/*     */   public ByteBuf setChar(int paramInt1, int paramInt2)
/*     */   {
/* 360 */     setShort(paramInt1, paramInt2);
/* 361 */     return this;
/*     */   }
/*     */   
/*     */   public ByteBuf setFloat(int paramInt, float paramFloat)
/*     */   {
/* 366 */     setInt(paramInt, Float.floatToRawIntBits(paramFloat));
/* 367 */     return this;
/*     */   }
/*     */   
/*     */   public ByteBuf setDouble(int paramInt, double paramDouble)
/*     */   {
/* 372 */     setLong(paramInt, Double.doubleToRawLongBits(paramDouble));
/* 373 */     return this;
/*     */   }
/*     */   
/*     */   public ByteBuf setBytes(int paramInt, ByteBuf paramByteBuf)
/*     */   {
/* 378 */     this.buf.setBytes(paramInt, paramByteBuf);
/* 379 */     return this;
/*     */   }
/*     */   
/*     */   public ByteBuf setBytes(int paramInt1, ByteBuf paramByteBuf, int paramInt2)
/*     */   {
/* 384 */     this.buf.setBytes(paramInt1, paramByteBuf, paramInt2);
/* 385 */     return this;
/*     */   }
/*     */   
/*     */   public ByteBuf setBytes(int paramInt1, ByteBuf paramByteBuf, int paramInt2, int paramInt3)
/*     */   {
/* 390 */     this.buf.setBytes(paramInt1, paramByteBuf, paramInt2, paramInt3);
/* 391 */     return this;
/*     */   }
/*     */   
/*     */   public ByteBuf setBytes(int paramInt, byte[] paramArrayOfByte)
/*     */   {
/* 396 */     this.buf.setBytes(paramInt, paramArrayOfByte);
/* 397 */     return this;
/*     */   }
/*     */   
/*     */   public ByteBuf setBytes(int paramInt1, byte[] paramArrayOfByte, int paramInt2, int paramInt3)
/*     */   {
/* 402 */     this.buf.setBytes(paramInt1, paramArrayOfByte, paramInt2, paramInt3);
/* 403 */     return this;
/*     */   }
/*     */   
/*     */   public ByteBuf setBytes(int paramInt, ByteBuffer paramByteBuffer)
/*     */   {
/* 408 */     this.buf.setBytes(paramInt, paramByteBuffer);
/* 409 */     return this;
/*     */   }
/*     */   
/*     */   public int setBytes(int paramInt1, InputStream paramInputStream, int paramInt2) throws IOException
/*     */   {
/* 414 */     return this.buf.setBytes(paramInt1, paramInputStream, paramInt2);
/*     */   }
/*     */   
/*     */   public int setBytes(int paramInt1, ScatteringByteChannel paramScatteringByteChannel, int paramInt2) throws IOException
/*     */   {
/* 419 */     return this.buf.setBytes(paramInt1, paramScatteringByteChannel, paramInt2);
/*     */   }
/*     */   
/*     */   public ByteBuf setZero(int paramInt1, int paramInt2)
/*     */   {
/* 424 */     this.buf.setZero(paramInt1, paramInt2);
/* 425 */     return this;
/*     */   }
/*     */   
/*     */   public boolean readBoolean()
/*     */   {
/* 430 */     return this.buf.readBoolean();
/*     */   }
/*     */   
/*     */   public byte readByte()
/*     */   {
/* 435 */     return this.buf.readByte();
/*     */   }
/*     */   
/*     */   public short readUnsignedByte()
/*     */   {
/* 440 */     return this.buf.readUnsignedByte();
/*     */   }
/*     */   
/*     */   public short readShort()
/*     */   {
/* 445 */     return ByteBufUtil.swapShort(this.buf.readShort());
/*     */   }
/*     */   
/*     */   public int readUnsignedShort()
/*     */   {
/* 450 */     return readShort() & 0xFFFF;
/*     */   }
/*     */   
/*     */   public int readMedium()
/*     */   {
/* 455 */     return ByteBufUtil.swapMedium(this.buf.readMedium());
/*     */   }
/*     */   
/*     */   public int readUnsignedMedium()
/*     */   {
/* 460 */     return readMedium() & 0xFFFFFF;
/*     */   }
/*     */   
/*     */   public int readInt()
/*     */   {
/* 465 */     return ByteBufUtil.swapInt(this.buf.readInt());
/*     */   }
/*     */   
/*     */   public long readUnsignedInt()
/*     */   {
/* 470 */     return readInt() & 0xFFFFFFFF;
/*     */   }
/*     */   
/*     */   public long readLong()
/*     */   {
/* 475 */     return ByteBufUtil.swapLong(this.buf.readLong());
/*     */   }
/*     */   
/*     */   public char readChar()
/*     */   {
/* 480 */     return (char)readShort();
/*     */   }
/*     */   
/*     */   public float readFloat()
/*     */   {
/* 485 */     return Float.intBitsToFloat(readInt());
/*     */   }
/*     */   
/*     */   public double readDouble()
/*     */   {
/* 490 */     return Double.longBitsToDouble(readLong());
/*     */   }
/*     */   
/*     */   public ByteBuf readBytes(int paramInt)
/*     */   {
/* 495 */     return this.buf.readBytes(paramInt).order(order());
/*     */   }
/*     */   
/*     */   public ByteBuf readSlice(int paramInt)
/*     */   {
/* 500 */     return this.buf.readSlice(paramInt).order(this.order);
/*     */   }
/*     */   
/*     */   public ByteBuf readBytes(ByteBuf paramByteBuf)
/*     */   {
/* 505 */     this.buf.readBytes(paramByteBuf);
/* 506 */     return this;
/*     */   }
/*     */   
/*     */   public ByteBuf readBytes(ByteBuf paramByteBuf, int paramInt)
/*     */   {
/* 511 */     this.buf.readBytes(paramByteBuf, paramInt);
/* 512 */     return this;
/*     */   }
/*     */   
/*     */   public ByteBuf readBytes(ByteBuf paramByteBuf, int paramInt1, int paramInt2)
/*     */   {
/* 517 */     this.buf.readBytes(paramByteBuf, paramInt1, paramInt2);
/* 518 */     return this;
/*     */   }
/*     */   
/*     */   public ByteBuf readBytes(byte[] paramArrayOfByte)
/*     */   {
/* 523 */     this.buf.readBytes(paramArrayOfByte);
/* 524 */     return this;
/*     */   }
/*     */   
/*     */   public ByteBuf readBytes(byte[] paramArrayOfByte, int paramInt1, int paramInt2)
/*     */   {
/* 529 */     this.buf.readBytes(paramArrayOfByte, paramInt1, paramInt2);
/* 530 */     return this;
/*     */   }
/*     */   
/*     */   public ByteBuf readBytes(ByteBuffer paramByteBuffer)
/*     */   {
/* 535 */     this.buf.readBytes(paramByteBuffer);
/* 536 */     return this;
/*     */   }
/*     */   
/*     */   public ByteBuf readBytes(OutputStream paramOutputStream, int paramInt) throws IOException
/*     */   {
/* 541 */     this.buf.readBytes(paramOutputStream, paramInt);
/* 542 */     return this;
/*     */   }
/*     */   
/*     */   public int readBytes(GatheringByteChannel paramGatheringByteChannel, int paramInt) throws IOException
/*     */   {
/* 547 */     return this.buf.readBytes(paramGatheringByteChannel, paramInt);
/*     */   }
/*     */   
/*     */   public ByteBuf skipBytes(int paramInt)
/*     */   {
/* 552 */     this.buf.skipBytes(paramInt);
/* 553 */     return this;
/*     */   }
/*     */   
/*     */   public ByteBuf writeBoolean(boolean paramBoolean)
/*     */   {
/* 558 */     this.buf.writeBoolean(paramBoolean);
/* 559 */     return this;
/*     */   }
/*     */   
/*     */   public ByteBuf writeByte(int paramInt)
/*     */   {
/* 564 */     this.buf.writeByte(paramInt);
/* 565 */     return this;
/*     */   }
/*     */   
/*     */   public ByteBuf writeShort(int paramInt)
/*     */   {
/* 570 */     this.buf.writeShort(ByteBufUtil.swapShort((short)paramInt));
/* 571 */     return this;
/*     */   }
/*     */   
/*     */   public ByteBuf writeMedium(int paramInt)
/*     */   {
/* 576 */     this.buf.writeMedium(ByteBufUtil.swapMedium(paramInt));
/* 577 */     return this;
/*     */   }
/*     */   
/*     */   public ByteBuf writeInt(int paramInt)
/*     */   {
/* 582 */     this.buf.writeInt(ByteBufUtil.swapInt(paramInt));
/* 583 */     return this;
/*     */   }
/*     */   
/*     */   public ByteBuf writeLong(long paramLong)
/*     */   {
/* 588 */     this.buf.writeLong(ByteBufUtil.swapLong(paramLong));
/* 589 */     return this;
/*     */   }
/*     */   
/*     */   public ByteBuf writeChar(int paramInt)
/*     */   {
/* 594 */     writeShort(paramInt);
/* 595 */     return this;
/*     */   }
/*     */   
/*     */   public ByteBuf writeFloat(float paramFloat)
/*     */   {
/* 600 */     writeInt(Float.floatToRawIntBits(paramFloat));
/* 601 */     return this;
/*     */   }
/*     */   
/*     */   public ByteBuf writeDouble(double paramDouble)
/*     */   {
/* 606 */     writeLong(Double.doubleToRawLongBits(paramDouble));
/* 607 */     return this;
/*     */   }
/*     */   
/*     */   public ByteBuf writeBytes(ByteBuf paramByteBuf)
/*     */   {
/* 612 */     this.buf.writeBytes(paramByteBuf);
/* 613 */     return this;
/*     */   }
/*     */   
/*     */   public ByteBuf writeBytes(ByteBuf paramByteBuf, int paramInt)
/*     */   {
/* 618 */     this.buf.writeBytes(paramByteBuf, paramInt);
/* 619 */     return this;
/*     */   }
/*     */   
/*     */   public ByteBuf writeBytes(ByteBuf paramByteBuf, int paramInt1, int paramInt2)
/*     */   {
/* 624 */     this.buf.writeBytes(paramByteBuf, paramInt1, paramInt2);
/* 625 */     return this;
/*     */   }
/*     */   
/*     */   public ByteBuf writeBytes(byte[] paramArrayOfByte)
/*     */   {
/* 630 */     this.buf.writeBytes(paramArrayOfByte);
/* 631 */     return this;
/*     */   }
/*     */   
/*     */   public ByteBuf writeBytes(byte[] paramArrayOfByte, int paramInt1, int paramInt2)
/*     */   {
/* 636 */     this.buf.writeBytes(paramArrayOfByte, paramInt1, paramInt2);
/* 637 */     return this;
/*     */   }
/*     */   
/*     */   public ByteBuf writeBytes(ByteBuffer paramByteBuffer)
/*     */   {
/* 642 */     this.buf.writeBytes(paramByteBuffer);
/* 643 */     return this;
/*     */   }
/*     */   
/*     */   public int writeBytes(InputStream paramInputStream, int paramInt) throws IOException
/*     */   {
/* 648 */     return this.buf.writeBytes(paramInputStream, paramInt);
/*     */   }
/*     */   
/*     */   public int writeBytes(ScatteringByteChannel paramScatteringByteChannel, int paramInt) throws IOException
/*     */   {
/* 653 */     return this.buf.writeBytes(paramScatteringByteChannel, paramInt);
/*     */   }
/*     */   
/*     */   public ByteBuf writeZero(int paramInt)
/*     */   {
/* 658 */     this.buf.writeZero(paramInt);
/* 659 */     return this;
/*     */   }
/*     */   
/*     */   public int indexOf(int paramInt1, int paramInt2, byte paramByte)
/*     */   {
/* 664 */     return this.buf.indexOf(paramInt1, paramInt2, paramByte);
/*     */   }
/*     */   
/*     */   public int bytesBefore(byte paramByte)
/*     */   {
/* 669 */     return this.buf.bytesBefore(paramByte);
/*     */   }
/*     */   
/*     */   public int bytesBefore(int paramInt, byte paramByte)
/*     */   {
/* 674 */     return this.buf.bytesBefore(paramInt, paramByte);
/*     */   }
/*     */   
/*     */   public int bytesBefore(int paramInt1, int paramInt2, byte paramByte)
/*     */   {
/* 679 */     return this.buf.bytesBefore(paramInt1, paramInt2, paramByte);
/*     */   }
/*     */   
/*     */   public int forEachByte(ByteBufProcessor paramByteBufProcessor)
/*     */   {
/* 684 */     return this.buf.forEachByte(paramByteBufProcessor);
/*     */   }
/*     */   
/*     */   public int forEachByte(int paramInt1, int paramInt2, ByteBufProcessor paramByteBufProcessor)
/*     */   {
/* 689 */     return this.buf.forEachByte(paramInt1, paramInt2, paramByteBufProcessor);
/*     */   }
/*     */   
/*     */   public int forEachByteDesc(ByteBufProcessor paramByteBufProcessor)
/*     */   {
/* 694 */     return this.buf.forEachByteDesc(paramByteBufProcessor);
/*     */   }
/*     */   
/*     */   public int forEachByteDesc(int paramInt1, int paramInt2, ByteBufProcessor paramByteBufProcessor)
/*     */   {
/* 699 */     return this.buf.forEachByteDesc(paramInt1, paramInt2, paramByteBufProcessor);
/*     */   }
/*     */   
/*     */   public ByteBuf copy()
/*     */   {
/* 704 */     return this.buf.copy().order(this.order);
/*     */   }
/*     */   
/*     */   public ByteBuf copy(int paramInt1, int paramInt2)
/*     */   {
/* 709 */     return this.buf.copy(paramInt1, paramInt2).order(this.order);
/*     */   }
/*     */   
/*     */   public ByteBuf slice()
/*     */   {
/* 714 */     return this.buf.slice().order(this.order);
/*     */   }
/*     */   
/*     */   public ByteBuf slice(int paramInt1, int paramInt2)
/*     */   {
/* 719 */     return this.buf.slice(paramInt1, paramInt2).order(this.order);
/*     */   }
/*     */   
/*     */   public ByteBuf duplicate()
/*     */   {
/* 724 */     return this.buf.duplicate().order(this.order);
/*     */   }
/*     */   
/*     */   public int nioBufferCount()
/*     */   {
/* 729 */     return this.buf.nioBufferCount();
/*     */   }
/*     */   
/*     */   public ByteBuffer nioBuffer()
/*     */   {
/* 734 */     return this.buf.nioBuffer().order(this.order);
/*     */   }
/*     */   
/*     */   public ByteBuffer nioBuffer(int paramInt1, int paramInt2)
/*     */   {
/* 739 */     return this.buf.nioBuffer(paramInt1, paramInt2).order(this.order);
/*     */   }
/*     */   
/*     */   public ByteBuffer internalNioBuffer(int paramInt1, int paramInt2)
/*     */   {
/* 744 */     return nioBuffer(paramInt1, paramInt2);
/*     */   }
/*     */   
/*     */   public ByteBuffer[] nioBuffers()
/*     */   {
/* 749 */     ByteBuffer[] arrayOfByteBuffer = this.buf.nioBuffers();
/* 750 */     for (int i = 0; i < arrayOfByteBuffer.length; i++) {
/* 751 */       arrayOfByteBuffer[i] = arrayOfByteBuffer[i].order(this.order);
/*     */     }
/* 753 */     return arrayOfByteBuffer;
/*     */   }
/*     */   
/*     */   public ByteBuffer[] nioBuffers(int paramInt1, int paramInt2)
/*     */   {
/* 758 */     ByteBuffer[] arrayOfByteBuffer = this.buf.nioBuffers(paramInt1, paramInt2);
/* 759 */     for (int i = 0; i < arrayOfByteBuffer.length; i++) {
/* 760 */       arrayOfByteBuffer[i] = arrayOfByteBuffer[i].order(this.order);
/*     */     }
/* 762 */     return arrayOfByteBuffer;
/*     */   }
/*     */   
/*     */   public boolean hasArray()
/*     */   {
/* 767 */     return this.buf.hasArray();
/*     */   }
/*     */   
/*     */   public byte[] array()
/*     */   {
/* 772 */     return this.buf.array();
/*     */   }
/*     */   
/*     */   public int arrayOffset()
/*     */   {
/* 777 */     return this.buf.arrayOffset();
/*     */   }
/*     */   
/*     */   public boolean hasMemoryAddress()
/*     */   {
/* 782 */     return this.buf.hasMemoryAddress();
/*     */   }
/*     */   
/*     */   public long memoryAddress()
/*     */   {
/* 787 */     return this.buf.memoryAddress();
/*     */   }
/*     */   
/*     */   public String toString(Charset paramCharset)
/*     */   {
/* 792 */     return this.buf.toString(paramCharset);
/*     */   }
/*     */   
/*     */   public String toString(int paramInt1, int paramInt2, Charset paramCharset)
/*     */   {
/* 797 */     return this.buf.toString(paramInt1, paramInt2, paramCharset);
/*     */   }
/*     */   
/*     */   public int refCnt()
/*     */   {
/* 802 */     return this.buf.refCnt();
/*     */   }
/*     */   
/*     */   public ByteBuf retain()
/*     */   {
/* 807 */     this.buf.retain();
/* 808 */     return this;
/*     */   }
/*     */   
/*     */   public ByteBuf retain(int paramInt)
/*     */   {
/* 813 */     this.buf.retain(paramInt);
/* 814 */     return this;
/*     */   }
/*     */   
/*     */   public boolean release()
/*     */   {
/* 819 */     return this.buf.release();
/*     */   }
/*     */   
/*     */   public boolean release(int paramInt)
/*     */   {
/* 824 */     return this.buf.release(paramInt);
/*     */   }
/*     */   
/*     */   public int hashCode()
/*     */   {
/* 829 */     return this.buf.hashCode();
/*     */   }
/*     */   
/*     */   public boolean equals(Object paramObject)
/*     */   {
/* 834 */     if (this == paramObject) {
/* 835 */       return true;
/*     */     }
/* 837 */     if ((paramObject instanceof ByteBuf)) {
/* 838 */       return ByteBufUtil.equals(this, (ByteBuf)paramObject);
/*     */     }
/* 840 */     return false;
/*     */   }
/*     */   
/*     */   public int compareTo(ByteBuf paramByteBuf)
/*     */   {
/* 845 */     return ByteBufUtil.compare(this, paramByteBuf);
/*     */   }
/*     */   
/*     */   public String toString()
/*     */   {
/* 850 */     return "Swapped(" + this.buf.toString() + ')';
/*     */   }
/*     */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\io\netty\buffer\SwappedByteBuf.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */
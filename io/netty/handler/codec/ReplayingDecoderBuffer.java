/*      */ package io.netty.handler.codec;
/*      */ 
/*      */ import io.netty.buffer.ByteBuf;
/*      */ import io.netty.buffer.ByteBufAllocator;
/*      */ import io.netty.buffer.ByteBufProcessor;
/*      */ import io.netty.buffer.SwappedByteBuf;
/*      */ import io.netty.buffer.Unpooled;
/*      */ import io.netty.util.Signal;
/*      */ import io.netty.util.internal.StringUtil;
/*      */ import java.io.InputStream;
/*      */ import java.io.OutputStream;
/*      */ import java.nio.ByteBuffer;
/*      */ import java.nio.ByteOrder;
/*      */ import java.nio.channels.GatheringByteChannel;
/*      */ import java.nio.channels.ScatteringByteChannel;
/*      */ import java.nio.charset.Charset;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ final class ReplayingDecoderBuffer
/*      */   extends ByteBuf
/*      */ {
/*   39 */   private static final Signal REPLAY = ReplayingDecoder.REPLAY;
/*      */   
/*      */   private ByteBuf buffer;
/*      */   
/*      */   private boolean terminated;
/*      */   private SwappedByteBuf swapped;
/*   45 */   static final ReplayingDecoderBuffer EMPTY_BUFFER = new ReplayingDecoderBuffer(Unpooled.EMPTY_BUFFER);
/*      */   
/*      */   static {
/*   48 */     EMPTY_BUFFER.terminate();
/*      */   }
/*      */   
/*      */ 
/*      */   ReplayingDecoderBuffer(ByteBuf paramByteBuf)
/*      */   {
/*   54 */     setCumulation(paramByteBuf);
/*      */   }
/*      */   
/*      */   void setCumulation(ByteBuf paramByteBuf) {
/*   58 */     this.buffer = paramByteBuf;
/*      */   }
/*      */   
/*      */   void terminate() {
/*   62 */     this.terminated = true;
/*      */   }
/*      */   
/*      */   public int capacity()
/*      */   {
/*   67 */     if (this.terminated) {
/*   68 */       return this.buffer.capacity();
/*      */     }
/*   70 */     return Integer.MAX_VALUE;
/*      */   }
/*      */   
/*      */ 
/*      */   public ByteBuf capacity(int paramInt)
/*      */   {
/*   76 */     reject();
/*   77 */     return this;
/*      */   }
/*      */   
/*      */   public int maxCapacity()
/*      */   {
/*   82 */     return capacity();
/*      */   }
/*      */   
/*      */   public ByteBufAllocator alloc()
/*      */   {
/*   87 */     return this.buffer.alloc();
/*      */   }
/*      */   
/*      */   public boolean isDirect()
/*      */   {
/*   92 */     return this.buffer.isDirect();
/*      */   }
/*      */   
/*      */   public boolean hasArray()
/*      */   {
/*   97 */     return false;
/*      */   }
/*      */   
/*      */   public byte[] array()
/*      */   {
/*  102 */     throw new UnsupportedOperationException();
/*      */   }
/*      */   
/*      */   public int arrayOffset()
/*      */   {
/*  107 */     throw new UnsupportedOperationException();
/*      */   }
/*      */   
/*      */   public boolean hasMemoryAddress()
/*      */   {
/*  112 */     return false;
/*      */   }
/*      */   
/*      */   public long memoryAddress()
/*      */   {
/*  117 */     throw new UnsupportedOperationException();
/*      */   }
/*      */   
/*      */   public ByteBuf clear()
/*      */   {
/*  122 */     reject();
/*  123 */     return this;
/*      */   }
/*      */   
/*      */   public boolean equals(Object paramObject)
/*      */   {
/*  128 */     return this == paramObject;
/*      */   }
/*      */   
/*      */   public int compareTo(ByteBuf paramByteBuf)
/*      */   {
/*  133 */     reject();
/*  134 */     return 0;
/*      */   }
/*      */   
/*      */   public ByteBuf copy()
/*      */   {
/*  139 */     reject();
/*  140 */     return this;
/*      */   }
/*      */   
/*      */   public ByteBuf copy(int paramInt1, int paramInt2)
/*      */   {
/*  145 */     checkIndex(paramInt1, paramInt2);
/*  146 */     return this.buffer.copy(paramInt1, paramInt2);
/*      */   }
/*      */   
/*      */   public ByteBuf discardReadBytes()
/*      */   {
/*  151 */     reject();
/*  152 */     return this;
/*      */   }
/*      */   
/*      */   public ByteBuf ensureWritable(int paramInt)
/*      */   {
/*  157 */     reject();
/*  158 */     return this;
/*      */   }
/*      */   
/*      */   public int ensureWritable(int paramInt, boolean paramBoolean)
/*      */   {
/*  163 */     reject();
/*  164 */     return 0;
/*      */   }
/*      */   
/*      */   public ByteBuf duplicate()
/*      */   {
/*  169 */     reject();
/*  170 */     return this;
/*      */   }
/*      */   
/*      */   public boolean getBoolean(int paramInt)
/*      */   {
/*  175 */     checkIndex(paramInt, 1);
/*  176 */     return this.buffer.getBoolean(paramInt);
/*      */   }
/*      */   
/*      */   public byte getByte(int paramInt)
/*      */   {
/*  181 */     checkIndex(paramInt, 1);
/*  182 */     return this.buffer.getByte(paramInt);
/*      */   }
/*      */   
/*      */   public short getUnsignedByte(int paramInt)
/*      */   {
/*  187 */     checkIndex(paramInt, 1);
/*  188 */     return this.buffer.getUnsignedByte(paramInt);
/*      */   }
/*      */   
/*      */   public ByteBuf getBytes(int paramInt1, byte[] paramArrayOfByte, int paramInt2, int paramInt3)
/*      */   {
/*  193 */     checkIndex(paramInt1, paramInt3);
/*  194 */     this.buffer.getBytes(paramInt1, paramArrayOfByte, paramInt2, paramInt3);
/*  195 */     return this;
/*      */   }
/*      */   
/*      */   public ByteBuf getBytes(int paramInt, byte[] paramArrayOfByte)
/*      */   {
/*  200 */     checkIndex(paramInt, paramArrayOfByte.length);
/*  201 */     this.buffer.getBytes(paramInt, paramArrayOfByte);
/*  202 */     return this;
/*      */   }
/*      */   
/*      */   public ByteBuf getBytes(int paramInt, ByteBuffer paramByteBuffer)
/*      */   {
/*  207 */     reject();
/*  208 */     return this;
/*      */   }
/*      */   
/*      */   public ByteBuf getBytes(int paramInt1, ByteBuf paramByteBuf, int paramInt2, int paramInt3)
/*      */   {
/*  213 */     checkIndex(paramInt1, paramInt3);
/*  214 */     this.buffer.getBytes(paramInt1, paramByteBuf, paramInt2, paramInt3);
/*  215 */     return this;
/*      */   }
/*      */   
/*      */   public ByteBuf getBytes(int paramInt1, ByteBuf paramByteBuf, int paramInt2)
/*      */   {
/*  220 */     reject();
/*  221 */     return this;
/*      */   }
/*      */   
/*      */   public ByteBuf getBytes(int paramInt, ByteBuf paramByteBuf)
/*      */   {
/*  226 */     reject();
/*  227 */     return this;
/*      */   }
/*      */   
/*      */   public int getBytes(int paramInt1, GatheringByteChannel paramGatheringByteChannel, int paramInt2)
/*      */   {
/*  232 */     reject();
/*  233 */     return 0;
/*      */   }
/*      */   
/*      */   public ByteBuf getBytes(int paramInt1, OutputStream paramOutputStream, int paramInt2)
/*      */   {
/*  238 */     reject();
/*  239 */     return this;
/*      */   }
/*      */   
/*      */   public int getInt(int paramInt)
/*      */   {
/*  244 */     checkIndex(paramInt, 4);
/*  245 */     return this.buffer.getInt(paramInt);
/*      */   }
/*      */   
/*      */   public long getUnsignedInt(int paramInt)
/*      */   {
/*  250 */     checkIndex(paramInt, 4);
/*  251 */     return this.buffer.getUnsignedInt(paramInt);
/*      */   }
/*      */   
/*      */   public long getLong(int paramInt)
/*      */   {
/*  256 */     checkIndex(paramInt, 8);
/*  257 */     return this.buffer.getLong(paramInt);
/*      */   }
/*      */   
/*      */   public int getMedium(int paramInt)
/*      */   {
/*  262 */     checkIndex(paramInt, 3);
/*  263 */     return this.buffer.getMedium(paramInt);
/*      */   }
/*      */   
/*      */   public int getUnsignedMedium(int paramInt)
/*      */   {
/*  268 */     checkIndex(paramInt, 3);
/*  269 */     return this.buffer.getUnsignedMedium(paramInt);
/*      */   }
/*      */   
/*      */   public short getShort(int paramInt)
/*      */   {
/*  274 */     checkIndex(paramInt, 2);
/*  275 */     return this.buffer.getShort(paramInt);
/*      */   }
/*      */   
/*      */   public int getUnsignedShort(int paramInt)
/*      */   {
/*  280 */     checkIndex(paramInt, 2);
/*  281 */     return this.buffer.getUnsignedShort(paramInt);
/*      */   }
/*      */   
/*      */   public char getChar(int paramInt)
/*      */   {
/*  286 */     checkIndex(paramInt, 2);
/*  287 */     return this.buffer.getChar(paramInt);
/*      */   }
/*      */   
/*      */   public float getFloat(int paramInt)
/*      */   {
/*  292 */     checkIndex(paramInt, 4);
/*  293 */     return this.buffer.getFloat(paramInt);
/*      */   }
/*      */   
/*      */   public double getDouble(int paramInt)
/*      */   {
/*  298 */     checkIndex(paramInt, 8);
/*  299 */     return this.buffer.getDouble(paramInt);
/*      */   }
/*      */   
/*      */   public int hashCode()
/*      */   {
/*  304 */     reject();
/*  305 */     return 0;
/*      */   }
/*      */   
/*      */   public int indexOf(int paramInt1, int paramInt2, byte paramByte)
/*      */   {
/*  310 */     if (paramInt1 == paramInt2) {
/*  311 */       return -1;
/*      */     }
/*      */     
/*  314 */     if (Math.max(paramInt1, paramInt2) > this.buffer.writerIndex()) {
/*  315 */       throw REPLAY;
/*      */     }
/*      */     
/*  318 */     return this.buffer.indexOf(paramInt1, paramInt2, paramByte);
/*      */   }
/*      */   
/*      */   public int bytesBefore(byte paramByte)
/*      */   {
/*  323 */     int i = this.buffer.bytesBefore(paramByte);
/*  324 */     if (i < 0) {
/*  325 */       throw REPLAY;
/*      */     }
/*  327 */     return i;
/*      */   }
/*      */   
/*      */   public int bytesBefore(int paramInt, byte paramByte)
/*      */   {
/*  332 */     int i = this.buffer.readerIndex();
/*  333 */     return bytesBefore(i, this.buffer.writerIndex() - i, paramByte);
/*      */   }
/*      */   
/*      */   public int bytesBefore(int paramInt1, int paramInt2, byte paramByte)
/*      */   {
/*  338 */     int i = this.buffer.writerIndex();
/*  339 */     if (paramInt1 >= i) {
/*  340 */       throw REPLAY;
/*      */     }
/*      */     
/*  343 */     if (paramInt1 <= i - paramInt2) {
/*  344 */       return this.buffer.bytesBefore(paramInt1, paramInt2, paramByte);
/*      */     }
/*      */     
/*  347 */     int j = this.buffer.bytesBefore(paramInt1, i - paramInt1, paramByte);
/*  348 */     if (j < 0) {
/*  349 */       throw REPLAY;
/*      */     }
/*  351 */     return j;
/*      */   }
/*      */   
/*      */ 
/*      */   public int forEachByte(ByteBufProcessor paramByteBufProcessor)
/*      */   {
/*  357 */     int i = this.buffer.forEachByte(paramByteBufProcessor);
/*  358 */     if (i < 0) {
/*  359 */       throw REPLAY;
/*      */     }
/*  361 */     return i;
/*      */   }
/*      */   
/*      */ 
/*      */   public int forEachByte(int paramInt1, int paramInt2, ByteBufProcessor paramByteBufProcessor)
/*      */   {
/*  367 */     int i = this.buffer.writerIndex();
/*  368 */     if (paramInt1 >= i) {
/*  369 */       throw REPLAY;
/*      */     }
/*      */     
/*  372 */     if (paramInt1 <= i - paramInt2) {
/*  373 */       return this.buffer.forEachByte(paramInt1, paramInt2, paramByteBufProcessor);
/*      */     }
/*      */     
/*  376 */     int j = this.buffer.forEachByte(paramInt1, i - paramInt1, paramByteBufProcessor);
/*  377 */     if (j < 0) {
/*  378 */       throw REPLAY;
/*      */     }
/*  380 */     return j;
/*      */   }
/*      */   
/*      */ 
/*      */   public int forEachByteDesc(ByteBufProcessor paramByteBufProcessor)
/*      */   {
/*  386 */     if (this.terminated) {
/*  387 */       return this.buffer.forEachByteDesc(paramByteBufProcessor);
/*      */     }
/*  389 */     reject();
/*  390 */     return 0;
/*      */   }
/*      */   
/*      */ 
/*      */   public int forEachByteDesc(int paramInt1, int paramInt2, ByteBufProcessor paramByteBufProcessor)
/*      */   {
/*  396 */     if (paramInt1 + paramInt2 > this.buffer.writerIndex()) {
/*  397 */       throw REPLAY;
/*      */     }
/*      */     
/*  400 */     return this.buffer.forEachByteDesc(paramInt1, paramInt2, paramByteBufProcessor);
/*      */   }
/*      */   
/*      */   public ByteBuf markReaderIndex()
/*      */   {
/*  405 */     this.buffer.markReaderIndex();
/*  406 */     return this;
/*      */   }
/*      */   
/*      */   public ByteBuf markWriterIndex()
/*      */   {
/*  411 */     reject();
/*  412 */     return this;
/*      */   }
/*      */   
/*      */   public ByteOrder order()
/*      */   {
/*  417 */     return this.buffer.order();
/*      */   }
/*      */   
/*      */   public ByteBuf order(ByteOrder paramByteOrder)
/*      */   {
/*  422 */     if (paramByteOrder == null) {
/*  423 */       throw new NullPointerException("endianness");
/*      */     }
/*  425 */     if (paramByteOrder == order()) {
/*  426 */       return this;
/*      */     }
/*      */     
/*  429 */     SwappedByteBuf localSwappedByteBuf = this.swapped;
/*  430 */     if (localSwappedByteBuf == null) {
/*  431 */       this.swapped = (localSwappedByteBuf = new SwappedByteBuf(this));
/*      */     }
/*  433 */     return localSwappedByteBuf;
/*      */   }
/*      */   
/*      */   public boolean isReadable()
/*      */   {
/*  438 */     return this.terminated ? this.buffer.isReadable() : true;
/*      */   }
/*      */   
/*      */   public boolean isReadable(int paramInt)
/*      */   {
/*  443 */     return this.terminated ? this.buffer.isReadable(paramInt) : true;
/*      */   }
/*      */   
/*      */   public int readableBytes()
/*      */   {
/*  448 */     if (this.terminated) {
/*  449 */       return this.buffer.readableBytes();
/*      */     }
/*  451 */     return Integer.MAX_VALUE - this.buffer.readerIndex();
/*      */   }
/*      */   
/*      */ 
/*      */   public boolean readBoolean()
/*      */   {
/*  457 */     checkReadableBytes(1);
/*  458 */     return this.buffer.readBoolean();
/*      */   }
/*      */   
/*      */   public byte readByte()
/*      */   {
/*  463 */     checkReadableBytes(1);
/*  464 */     return this.buffer.readByte();
/*      */   }
/*      */   
/*      */   public short readUnsignedByte()
/*      */   {
/*  469 */     checkReadableBytes(1);
/*  470 */     return this.buffer.readUnsignedByte();
/*      */   }
/*      */   
/*      */   public ByteBuf readBytes(byte[] paramArrayOfByte, int paramInt1, int paramInt2)
/*      */   {
/*  475 */     checkReadableBytes(paramInt2);
/*  476 */     this.buffer.readBytes(paramArrayOfByte, paramInt1, paramInt2);
/*  477 */     return this;
/*      */   }
/*      */   
/*      */   public ByteBuf readBytes(byte[] paramArrayOfByte)
/*      */   {
/*  482 */     checkReadableBytes(paramArrayOfByte.length);
/*  483 */     this.buffer.readBytes(paramArrayOfByte);
/*  484 */     return this;
/*      */   }
/*      */   
/*      */   public ByteBuf readBytes(ByteBuffer paramByteBuffer)
/*      */   {
/*  489 */     reject();
/*  490 */     return this;
/*      */   }
/*      */   
/*      */   public ByteBuf readBytes(ByteBuf paramByteBuf, int paramInt1, int paramInt2)
/*      */   {
/*  495 */     checkReadableBytes(paramInt2);
/*  496 */     this.buffer.readBytes(paramByteBuf, paramInt1, paramInt2);
/*  497 */     return this;
/*      */   }
/*      */   
/*      */   public ByteBuf readBytes(ByteBuf paramByteBuf, int paramInt)
/*      */   {
/*  502 */     reject();
/*  503 */     return this;
/*      */   }
/*      */   
/*      */   public ByteBuf readBytes(ByteBuf paramByteBuf)
/*      */   {
/*  508 */     checkReadableBytes(paramByteBuf.writableBytes());
/*  509 */     this.buffer.readBytes(paramByteBuf);
/*  510 */     return this;
/*      */   }
/*      */   
/*      */   public int readBytes(GatheringByteChannel paramGatheringByteChannel, int paramInt)
/*      */   {
/*  515 */     reject();
/*  516 */     return 0;
/*      */   }
/*      */   
/*      */   public ByteBuf readBytes(int paramInt)
/*      */   {
/*  521 */     checkReadableBytes(paramInt);
/*  522 */     return this.buffer.readBytes(paramInt);
/*      */   }
/*      */   
/*      */   public ByteBuf readSlice(int paramInt)
/*      */   {
/*  527 */     checkReadableBytes(paramInt);
/*  528 */     return this.buffer.readSlice(paramInt);
/*      */   }
/*      */   
/*      */   public ByteBuf readBytes(OutputStream paramOutputStream, int paramInt)
/*      */   {
/*  533 */     reject();
/*  534 */     return this;
/*      */   }
/*      */   
/*      */   public int readerIndex()
/*      */   {
/*  539 */     return this.buffer.readerIndex();
/*      */   }
/*      */   
/*      */   public ByteBuf readerIndex(int paramInt)
/*      */   {
/*  544 */     this.buffer.readerIndex(paramInt);
/*  545 */     return this;
/*      */   }
/*      */   
/*      */   public int readInt()
/*      */   {
/*  550 */     checkReadableBytes(4);
/*  551 */     return this.buffer.readInt();
/*      */   }
/*      */   
/*      */   public long readUnsignedInt()
/*      */   {
/*  556 */     checkReadableBytes(4);
/*  557 */     return this.buffer.readUnsignedInt();
/*      */   }
/*      */   
/*      */   public long readLong()
/*      */   {
/*  562 */     checkReadableBytes(8);
/*  563 */     return this.buffer.readLong();
/*      */   }
/*      */   
/*      */   public int readMedium()
/*      */   {
/*  568 */     checkReadableBytes(3);
/*  569 */     return this.buffer.readMedium();
/*      */   }
/*      */   
/*      */   public int readUnsignedMedium()
/*      */   {
/*  574 */     checkReadableBytes(3);
/*  575 */     return this.buffer.readUnsignedMedium();
/*      */   }
/*      */   
/*      */   public short readShort()
/*      */   {
/*  580 */     checkReadableBytes(2);
/*  581 */     return this.buffer.readShort();
/*      */   }
/*      */   
/*      */   public int readUnsignedShort()
/*      */   {
/*  586 */     checkReadableBytes(2);
/*  587 */     return this.buffer.readUnsignedShort();
/*      */   }
/*      */   
/*      */   public char readChar()
/*      */   {
/*  592 */     checkReadableBytes(2);
/*  593 */     return this.buffer.readChar();
/*      */   }
/*      */   
/*      */   public float readFloat()
/*      */   {
/*  598 */     checkReadableBytes(4);
/*  599 */     return this.buffer.readFloat();
/*      */   }
/*      */   
/*      */   public double readDouble()
/*      */   {
/*  604 */     checkReadableBytes(8);
/*  605 */     return this.buffer.readDouble();
/*      */   }
/*      */   
/*      */   public ByteBuf resetReaderIndex()
/*      */   {
/*  610 */     this.buffer.resetReaderIndex();
/*  611 */     return this;
/*      */   }
/*      */   
/*      */   public ByteBuf resetWriterIndex()
/*      */   {
/*  616 */     reject();
/*  617 */     return this;
/*      */   }
/*      */   
/*      */   public ByteBuf setBoolean(int paramInt, boolean paramBoolean)
/*      */   {
/*  622 */     reject();
/*  623 */     return this;
/*      */   }
/*      */   
/*      */   public ByteBuf setByte(int paramInt1, int paramInt2)
/*      */   {
/*  628 */     reject();
/*  629 */     return this;
/*      */   }
/*      */   
/*      */   public ByteBuf setBytes(int paramInt1, byte[] paramArrayOfByte, int paramInt2, int paramInt3)
/*      */   {
/*  634 */     reject();
/*  635 */     return this;
/*      */   }
/*      */   
/*      */   public ByteBuf setBytes(int paramInt, byte[] paramArrayOfByte)
/*      */   {
/*  640 */     reject();
/*  641 */     return this;
/*      */   }
/*      */   
/*      */   public ByteBuf setBytes(int paramInt, ByteBuffer paramByteBuffer)
/*      */   {
/*  646 */     reject();
/*  647 */     return this;
/*      */   }
/*      */   
/*      */   public ByteBuf setBytes(int paramInt1, ByteBuf paramByteBuf, int paramInt2, int paramInt3)
/*      */   {
/*  652 */     reject();
/*  653 */     return this;
/*      */   }
/*      */   
/*      */   public ByteBuf setBytes(int paramInt1, ByteBuf paramByteBuf, int paramInt2)
/*      */   {
/*  658 */     reject();
/*  659 */     return this;
/*      */   }
/*      */   
/*      */   public ByteBuf setBytes(int paramInt, ByteBuf paramByteBuf)
/*      */   {
/*  664 */     reject();
/*  665 */     return this;
/*      */   }
/*      */   
/*      */   public int setBytes(int paramInt1, InputStream paramInputStream, int paramInt2)
/*      */   {
/*  670 */     reject();
/*  671 */     return 0;
/*      */   }
/*      */   
/*      */   public ByteBuf setZero(int paramInt1, int paramInt2)
/*      */   {
/*  676 */     reject();
/*  677 */     return this;
/*      */   }
/*      */   
/*      */   public int setBytes(int paramInt1, ScatteringByteChannel paramScatteringByteChannel, int paramInt2)
/*      */   {
/*  682 */     reject();
/*  683 */     return 0;
/*      */   }
/*      */   
/*      */   public ByteBuf setIndex(int paramInt1, int paramInt2)
/*      */   {
/*  688 */     reject();
/*  689 */     return this;
/*      */   }
/*      */   
/*      */   public ByteBuf setInt(int paramInt1, int paramInt2)
/*      */   {
/*  694 */     reject();
/*  695 */     return this;
/*      */   }
/*      */   
/*      */   public ByteBuf setLong(int paramInt, long paramLong)
/*      */   {
/*  700 */     reject();
/*  701 */     return this;
/*      */   }
/*      */   
/*      */   public ByteBuf setMedium(int paramInt1, int paramInt2)
/*      */   {
/*  706 */     reject();
/*  707 */     return this;
/*      */   }
/*      */   
/*      */   public ByteBuf setShort(int paramInt1, int paramInt2)
/*      */   {
/*  712 */     reject();
/*  713 */     return this;
/*      */   }
/*      */   
/*      */   public ByteBuf setChar(int paramInt1, int paramInt2)
/*      */   {
/*  718 */     reject();
/*  719 */     return this;
/*      */   }
/*      */   
/*      */   public ByteBuf setFloat(int paramInt, float paramFloat)
/*      */   {
/*  724 */     reject();
/*  725 */     return this;
/*      */   }
/*      */   
/*      */   public ByteBuf setDouble(int paramInt, double paramDouble)
/*      */   {
/*  730 */     reject();
/*  731 */     return this;
/*      */   }
/*      */   
/*      */   public ByteBuf skipBytes(int paramInt)
/*      */   {
/*  736 */     checkReadableBytes(paramInt);
/*  737 */     this.buffer.skipBytes(paramInt);
/*  738 */     return this;
/*      */   }
/*      */   
/*      */   public ByteBuf slice()
/*      */   {
/*  743 */     reject();
/*  744 */     return this;
/*      */   }
/*      */   
/*      */   public ByteBuf slice(int paramInt1, int paramInt2)
/*      */   {
/*  749 */     checkIndex(paramInt1, paramInt2);
/*  750 */     return this.buffer.slice(paramInt1, paramInt2);
/*      */   }
/*      */   
/*      */   public int nioBufferCount()
/*      */   {
/*  755 */     return this.buffer.nioBufferCount();
/*      */   }
/*      */   
/*      */   public ByteBuffer nioBuffer()
/*      */   {
/*  760 */     reject();
/*  761 */     return null;
/*      */   }
/*      */   
/*      */   public ByteBuffer nioBuffer(int paramInt1, int paramInt2)
/*      */   {
/*  766 */     checkIndex(paramInt1, paramInt2);
/*  767 */     return this.buffer.nioBuffer(paramInt1, paramInt2);
/*      */   }
/*      */   
/*      */   public ByteBuffer[] nioBuffers()
/*      */   {
/*  772 */     reject();
/*  773 */     return null;
/*      */   }
/*      */   
/*      */   public ByteBuffer[] nioBuffers(int paramInt1, int paramInt2)
/*      */   {
/*  778 */     checkIndex(paramInt1, paramInt2);
/*  779 */     return this.buffer.nioBuffers(paramInt1, paramInt2);
/*      */   }
/*      */   
/*      */   public ByteBuffer internalNioBuffer(int paramInt1, int paramInt2)
/*      */   {
/*  784 */     checkIndex(paramInt1, paramInt2);
/*  785 */     return this.buffer.internalNioBuffer(paramInt1, paramInt2);
/*      */   }
/*      */   
/*      */   public String toString(int paramInt1, int paramInt2, Charset paramCharset)
/*      */   {
/*  790 */     checkIndex(paramInt1, paramInt2);
/*  791 */     return this.buffer.toString(paramInt1, paramInt2, paramCharset);
/*      */   }
/*      */   
/*      */   public String toString(Charset paramCharset)
/*      */   {
/*  796 */     reject();
/*  797 */     return null;
/*      */   }
/*      */   
/*      */   public String toString()
/*      */   {
/*  802 */     return StringUtil.simpleClassName(this) + '(' + "ridx=" + readerIndex() + ", " + "widx=" + writerIndex() + ')';
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public boolean isWritable()
/*      */   {
/*  813 */     return false;
/*      */   }
/*      */   
/*      */   public boolean isWritable(int paramInt)
/*      */   {
/*  818 */     return false;
/*      */   }
/*      */   
/*      */   public int writableBytes()
/*      */   {
/*  823 */     return 0;
/*      */   }
/*      */   
/*      */   public int maxWritableBytes()
/*      */   {
/*  828 */     return 0;
/*      */   }
/*      */   
/*      */   public ByteBuf writeBoolean(boolean paramBoolean)
/*      */   {
/*  833 */     reject();
/*  834 */     return this;
/*      */   }
/*      */   
/*      */   public ByteBuf writeByte(int paramInt)
/*      */   {
/*  839 */     reject();
/*  840 */     return this;
/*      */   }
/*      */   
/*      */   public ByteBuf writeBytes(byte[] paramArrayOfByte, int paramInt1, int paramInt2)
/*      */   {
/*  845 */     reject();
/*  846 */     return this;
/*      */   }
/*      */   
/*      */   public ByteBuf writeBytes(byte[] paramArrayOfByte)
/*      */   {
/*  851 */     reject();
/*  852 */     return this;
/*      */   }
/*      */   
/*      */   public ByteBuf writeBytes(ByteBuffer paramByteBuffer)
/*      */   {
/*  857 */     reject();
/*  858 */     return this;
/*      */   }
/*      */   
/*      */   public ByteBuf writeBytes(ByteBuf paramByteBuf, int paramInt1, int paramInt2)
/*      */   {
/*  863 */     reject();
/*  864 */     return this;
/*      */   }
/*      */   
/*      */   public ByteBuf writeBytes(ByteBuf paramByteBuf, int paramInt)
/*      */   {
/*  869 */     reject();
/*  870 */     return this;
/*      */   }
/*      */   
/*      */   public ByteBuf writeBytes(ByteBuf paramByteBuf)
/*      */   {
/*  875 */     reject();
/*  876 */     return this;
/*      */   }
/*      */   
/*      */   public int writeBytes(InputStream paramInputStream, int paramInt)
/*      */   {
/*  881 */     reject();
/*  882 */     return 0;
/*      */   }
/*      */   
/*      */   public int writeBytes(ScatteringByteChannel paramScatteringByteChannel, int paramInt)
/*      */   {
/*  887 */     reject();
/*  888 */     return 0;
/*      */   }
/*      */   
/*      */   public ByteBuf writeInt(int paramInt)
/*      */   {
/*  893 */     reject();
/*  894 */     return this;
/*      */   }
/*      */   
/*      */   public ByteBuf writeLong(long paramLong)
/*      */   {
/*  899 */     reject();
/*  900 */     return this;
/*      */   }
/*      */   
/*      */   public ByteBuf writeMedium(int paramInt)
/*      */   {
/*  905 */     reject();
/*  906 */     return this;
/*      */   }
/*      */   
/*      */   public ByteBuf writeZero(int paramInt)
/*      */   {
/*  911 */     reject();
/*  912 */     return this;
/*      */   }
/*      */   
/*      */   public int writerIndex()
/*      */   {
/*  917 */     return this.buffer.writerIndex();
/*      */   }
/*      */   
/*      */   public ByteBuf writerIndex(int paramInt)
/*      */   {
/*  922 */     reject();
/*  923 */     return this;
/*      */   }
/*      */   
/*      */   public ByteBuf writeShort(int paramInt)
/*      */   {
/*  928 */     reject();
/*  929 */     return this;
/*      */   }
/*      */   
/*      */   public ByteBuf writeChar(int paramInt)
/*      */   {
/*  934 */     reject();
/*  935 */     return this;
/*      */   }
/*      */   
/*      */   public ByteBuf writeFloat(float paramFloat)
/*      */   {
/*  940 */     reject();
/*  941 */     return this;
/*      */   }
/*      */   
/*      */   public ByteBuf writeDouble(double paramDouble)
/*      */   {
/*  946 */     reject();
/*  947 */     return this;
/*      */   }
/*      */   
/*      */   private void checkIndex(int paramInt1, int paramInt2) {
/*  951 */     if (paramInt1 + paramInt2 > this.buffer.writerIndex()) {
/*  952 */       throw REPLAY;
/*      */     }
/*      */   }
/*      */   
/*      */   private void checkReadableBytes(int paramInt) {
/*  957 */     if (this.buffer.readableBytes() < paramInt) {
/*  958 */       throw REPLAY;
/*      */     }
/*      */   }
/*      */   
/*      */   public ByteBuf discardSomeReadBytes()
/*      */   {
/*  964 */     reject();
/*  965 */     return this;
/*      */   }
/*      */   
/*      */   public int refCnt()
/*      */   {
/*  970 */     return this.buffer.refCnt();
/*      */   }
/*      */   
/*      */   public ByteBuf retain()
/*      */   {
/*  975 */     reject();
/*  976 */     return this;
/*      */   }
/*      */   
/*      */   public ByteBuf retain(int paramInt)
/*      */   {
/*  981 */     reject();
/*  982 */     return this;
/*      */   }
/*      */   
/*      */   public boolean release()
/*      */   {
/*  987 */     reject();
/*  988 */     return false;
/*      */   }
/*      */   
/*      */   public boolean release(int paramInt)
/*      */   {
/*  993 */     reject();
/*  994 */     return false;
/*      */   }
/*      */   
/*      */   public ByteBuf unwrap()
/*      */   {
/*  999 */     reject();
/* 1000 */     return this;
/*      */   }
/*      */   
/*      */   private static void reject() {
/* 1004 */     throw new UnsupportedOperationException("not a replayable operation");
/*      */   }
/*      */   
/*      */   ReplayingDecoderBuffer() {}
/*      */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\io\netty\handler\codec\ReplayingDecoderBuffer.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */
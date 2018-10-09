/*      */ package io.netty.buffer;
/*      */ 
/*      */ import io.netty.util.IllegalReferenceCountException;
/*      */ import io.netty.util.ResourceLeakDetector;
/*      */ import io.netty.util.internal.PlatformDependent;
/*      */ import io.netty.util.internal.StringUtil;
/*      */ import java.io.IOException;
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
/*      */ 
/*      */ public abstract class AbstractByteBuf
/*      */   extends ByteBuf
/*      */ {
/*   38 */   static final ResourceLeakDetector<ByteBuf> leakDetector = new ResourceLeakDetector(ByteBuf.class);
/*      */   
/*      */   int readerIndex;
/*      */   
/*      */   int writerIndex;
/*      */   private int markedReaderIndex;
/*      */   private int markedWriterIndex;
/*      */   private int maxCapacity;
/*      */   private SwappedByteBuf swappedBuf;
/*      */   
/*      */   protected AbstractByteBuf(int paramInt)
/*      */   {
/*   50 */     if (paramInt < 0) {
/*   51 */       throw new IllegalArgumentException("maxCapacity: " + paramInt + " (expected: >= 0)");
/*      */     }
/*   53 */     this.maxCapacity = paramInt;
/*      */   }
/*      */   
/*      */   public int maxCapacity()
/*      */   {
/*   58 */     return this.maxCapacity;
/*      */   }
/*      */   
/*      */   protected final void maxCapacity(int paramInt) {
/*   62 */     this.maxCapacity = paramInt;
/*      */   }
/*      */   
/*      */   public int readerIndex()
/*      */   {
/*   67 */     return this.readerIndex;
/*      */   }
/*      */   
/*      */   public ByteBuf readerIndex(int paramInt)
/*      */   {
/*   72 */     if ((paramInt < 0) || (paramInt > this.writerIndex)) {
/*   73 */       throw new IndexOutOfBoundsException(String.format("readerIndex: %d (expected: 0 <= readerIndex <= writerIndex(%d))", new Object[] { Integer.valueOf(paramInt), Integer.valueOf(this.writerIndex) }));
/*      */     }
/*      */     
/*   76 */     this.readerIndex = paramInt;
/*   77 */     return this;
/*      */   }
/*      */   
/*      */   public int writerIndex()
/*      */   {
/*   82 */     return this.writerIndex;
/*      */   }
/*      */   
/*      */   public ByteBuf writerIndex(int paramInt)
/*      */   {
/*   87 */     if ((paramInt < this.readerIndex) || (paramInt > capacity())) {
/*   88 */       throw new IndexOutOfBoundsException(String.format("writerIndex: %d (expected: readerIndex(%d) <= writerIndex <= capacity(%d))", new Object[] { Integer.valueOf(paramInt), Integer.valueOf(this.readerIndex), Integer.valueOf(capacity()) }));
/*      */     }
/*      */     
/*      */ 
/*   92 */     this.writerIndex = paramInt;
/*   93 */     return this;
/*      */   }
/*      */   
/*      */   public ByteBuf setIndex(int paramInt1, int paramInt2)
/*      */   {
/*   98 */     if ((paramInt1 < 0) || (paramInt1 > paramInt2) || (paramInt2 > capacity())) {
/*   99 */       throw new IndexOutOfBoundsException(String.format("readerIndex: %d, writerIndex: %d (expected: 0 <= readerIndex <= writerIndex <= capacity(%d))", new Object[] { Integer.valueOf(paramInt1), Integer.valueOf(paramInt2), Integer.valueOf(capacity()) }));
/*      */     }
/*      */     
/*      */ 
/*  103 */     this.readerIndex = paramInt1;
/*  104 */     this.writerIndex = paramInt2;
/*  105 */     return this;
/*      */   }
/*      */   
/*      */   public ByteBuf clear()
/*      */   {
/*  110 */     this.readerIndex = (this.writerIndex = 0);
/*  111 */     return this;
/*      */   }
/*      */   
/*      */   public boolean isReadable()
/*      */   {
/*  116 */     return this.writerIndex > this.readerIndex;
/*      */   }
/*      */   
/*      */   public boolean isReadable(int paramInt)
/*      */   {
/*  121 */     return this.writerIndex - this.readerIndex >= paramInt;
/*      */   }
/*      */   
/*      */   public boolean isWritable()
/*      */   {
/*  126 */     return capacity() > this.writerIndex;
/*      */   }
/*      */   
/*      */   public boolean isWritable(int paramInt)
/*      */   {
/*  131 */     return capacity() - this.writerIndex >= paramInt;
/*      */   }
/*      */   
/*      */   public int readableBytes()
/*      */   {
/*  136 */     return this.writerIndex - this.readerIndex;
/*      */   }
/*      */   
/*      */   public int writableBytes()
/*      */   {
/*  141 */     return capacity() - this.writerIndex;
/*      */   }
/*      */   
/*      */   public int maxWritableBytes()
/*      */   {
/*  146 */     return maxCapacity() - this.writerIndex;
/*      */   }
/*      */   
/*      */   public ByteBuf markReaderIndex()
/*      */   {
/*  151 */     this.markedReaderIndex = this.readerIndex;
/*  152 */     return this;
/*      */   }
/*      */   
/*      */   public ByteBuf resetReaderIndex()
/*      */   {
/*  157 */     readerIndex(this.markedReaderIndex);
/*  158 */     return this;
/*      */   }
/*      */   
/*      */   public ByteBuf markWriterIndex()
/*      */   {
/*  163 */     this.markedWriterIndex = this.writerIndex;
/*  164 */     return this;
/*      */   }
/*      */   
/*      */   public ByteBuf resetWriterIndex()
/*      */   {
/*  169 */     this.writerIndex = this.markedWriterIndex;
/*  170 */     return this;
/*      */   }
/*      */   
/*      */   public ByteBuf discardReadBytes()
/*      */   {
/*  175 */     ensureAccessible();
/*  176 */     if (this.readerIndex == 0) {
/*  177 */       return this;
/*      */     }
/*      */     
/*  180 */     if (this.readerIndex != this.writerIndex) {
/*  181 */       setBytes(0, this, this.readerIndex, this.writerIndex - this.readerIndex);
/*  182 */       this.writerIndex -= this.readerIndex;
/*  183 */       adjustMarkers(this.readerIndex);
/*  184 */       this.readerIndex = 0;
/*      */     } else {
/*  186 */       adjustMarkers(this.readerIndex);
/*  187 */       this.writerIndex = (this.readerIndex = 0);
/*      */     }
/*  189 */     return this;
/*      */   }
/*      */   
/*      */   public ByteBuf discardSomeReadBytes()
/*      */   {
/*  194 */     ensureAccessible();
/*  195 */     if (this.readerIndex == 0) {
/*  196 */       return this;
/*      */     }
/*      */     
/*  199 */     if (this.readerIndex == this.writerIndex) {
/*  200 */       adjustMarkers(this.readerIndex);
/*  201 */       this.writerIndex = (this.readerIndex = 0);
/*  202 */       return this;
/*      */     }
/*      */     
/*  205 */     if (this.readerIndex >= capacity() >>> 1) {
/*  206 */       setBytes(0, this, this.readerIndex, this.writerIndex - this.readerIndex);
/*  207 */       this.writerIndex -= this.readerIndex;
/*  208 */       adjustMarkers(this.readerIndex);
/*  209 */       this.readerIndex = 0;
/*      */     }
/*  211 */     return this;
/*      */   }
/*      */   
/*      */   protected final void adjustMarkers(int paramInt) {
/*  215 */     int i = this.markedReaderIndex;
/*  216 */     if (i <= paramInt) {
/*  217 */       this.markedReaderIndex = 0;
/*  218 */       int j = this.markedWriterIndex;
/*  219 */       if (j <= paramInt) {
/*  220 */         this.markedWriterIndex = 0;
/*      */       } else {
/*  222 */         this.markedWriterIndex = (j - paramInt);
/*      */       }
/*      */     } else {
/*  225 */       this.markedReaderIndex = (i - paramInt);
/*  226 */       this.markedWriterIndex -= paramInt;
/*      */     }
/*      */   }
/*      */   
/*      */   public ByteBuf ensureWritable(int paramInt)
/*      */   {
/*  232 */     if (paramInt < 0) {
/*  233 */       throw new IllegalArgumentException(String.format("minWritableBytes: %d (expected: >= 0)", new Object[] { Integer.valueOf(paramInt) }));
/*      */     }
/*      */     
/*      */ 
/*  237 */     if (paramInt <= writableBytes()) {
/*  238 */       return this;
/*      */     }
/*      */     
/*  241 */     if (paramInt > this.maxCapacity - this.writerIndex) {
/*  242 */       throw new IndexOutOfBoundsException(String.format("writerIndex(%d) + minWritableBytes(%d) exceeds maxCapacity(%d): %s", new Object[] { Integer.valueOf(this.writerIndex), Integer.valueOf(paramInt), Integer.valueOf(this.maxCapacity), this }));
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*  248 */     int i = calculateNewCapacity(this.writerIndex + paramInt);
/*      */     
/*      */ 
/*  251 */     capacity(i);
/*  252 */     return this;
/*      */   }
/*      */   
/*      */   public int ensureWritable(int paramInt, boolean paramBoolean)
/*      */   {
/*  257 */     if (paramInt < 0) {
/*  258 */       throw new IllegalArgumentException(String.format("minWritableBytes: %d (expected: >= 0)", new Object[] { Integer.valueOf(paramInt) }));
/*      */     }
/*      */     
/*      */ 
/*  262 */     if (paramInt <= writableBytes()) {
/*  263 */       return 0;
/*      */     }
/*      */     
/*  266 */     if ((paramInt > this.maxCapacity - this.writerIndex) && 
/*  267 */       (paramBoolean)) {
/*  268 */       if (capacity() == maxCapacity()) {
/*  269 */         return 1;
/*      */       }
/*      */       
/*  272 */       capacity(maxCapacity());
/*  273 */       return 3;
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*  278 */     int i = calculateNewCapacity(this.writerIndex + paramInt);
/*      */     
/*      */ 
/*  281 */     capacity(i);
/*  282 */     return 2;
/*      */   }
/*      */   
/*      */   private int calculateNewCapacity(int paramInt) {
/*  286 */     int i = this.maxCapacity;
/*  287 */     int j = 4194304;
/*      */     
/*  289 */     if (paramInt == 4194304) {
/*  290 */       return 4194304;
/*      */     }
/*      */     
/*      */ 
/*  294 */     if (paramInt > 4194304) {
/*  295 */       k = paramInt / 4194304 * 4194304;
/*  296 */       if (k > i - 4194304) {
/*  297 */         k = i;
/*      */       } else {
/*  299 */         k += 4194304;
/*      */       }
/*  301 */       return k;
/*      */     }
/*      */     
/*      */ 
/*  305 */     int k = 64;
/*  306 */     while (k < paramInt) {
/*  307 */       k <<= 1;
/*      */     }
/*      */     
/*  310 */     return Math.min(k, i);
/*      */   }
/*      */   
/*      */   public ByteBuf order(ByteOrder paramByteOrder)
/*      */   {
/*  315 */     if (paramByteOrder == null) {
/*  316 */       throw new NullPointerException("endianness");
/*      */     }
/*  318 */     if (paramByteOrder == order()) {
/*  319 */       return this;
/*      */     }
/*      */     
/*  322 */     SwappedByteBuf localSwappedByteBuf = this.swappedBuf;
/*  323 */     if (localSwappedByteBuf == null) {
/*  324 */       this.swappedBuf = (localSwappedByteBuf = newSwappedByteBuf());
/*      */     }
/*  326 */     return localSwappedByteBuf;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */   protected SwappedByteBuf newSwappedByteBuf()
/*      */   {
/*  333 */     return new SwappedByteBuf(this);
/*      */   }
/*      */   
/*      */   public byte getByte(int paramInt)
/*      */   {
/*  338 */     checkIndex(paramInt);
/*  339 */     return _getByte(paramInt);
/*      */   }
/*      */   
/*      */   protected abstract byte _getByte(int paramInt);
/*      */   
/*      */   public boolean getBoolean(int paramInt)
/*      */   {
/*  346 */     return getByte(paramInt) != 0;
/*      */   }
/*      */   
/*      */   public short getUnsignedByte(int paramInt)
/*      */   {
/*  351 */     return (short)(getByte(paramInt) & 0xFF);
/*      */   }
/*      */   
/*      */   public short getShort(int paramInt)
/*      */   {
/*  356 */     checkIndex(paramInt, 2);
/*  357 */     return _getShort(paramInt);
/*      */   }
/*      */   
/*      */   protected abstract short _getShort(int paramInt);
/*      */   
/*      */   public int getUnsignedShort(int paramInt)
/*      */   {
/*  364 */     return getShort(paramInt) & 0xFFFF;
/*      */   }
/*      */   
/*      */   public int getUnsignedMedium(int paramInt)
/*      */   {
/*  369 */     checkIndex(paramInt, 3);
/*  370 */     return _getUnsignedMedium(paramInt);
/*      */   }
/*      */   
/*      */   protected abstract int _getUnsignedMedium(int paramInt);
/*      */   
/*      */   public int getMedium(int paramInt)
/*      */   {
/*  377 */     int i = getUnsignedMedium(paramInt);
/*  378 */     if ((i & 0x800000) != 0) {
/*  379 */       i |= 0xFF000000;
/*      */     }
/*  381 */     return i;
/*      */   }
/*      */   
/*      */   public int getInt(int paramInt)
/*      */   {
/*  386 */     checkIndex(paramInt, 4);
/*  387 */     return _getInt(paramInt);
/*      */   }
/*      */   
/*      */   protected abstract int _getInt(int paramInt);
/*      */   
/*      */   public long getUnsignedInt(int paramInt)
/*      */   {
/*  394 */     return getInt(paramInt) & 0xFFFFFFFF;
/*      */   }
/*      */   
/*      */   public long getLong(int paramInt)
/*      */   {
/*  399 */     checkIndex(paramInt, 8);
/*  400 */     return _getLong(paramInt);
/*      */   }
/*      */   
/*      */   protected abstract long _getLong(int paramInt);
/*      */   
/*      */   public char getChar(int paramInt)
/*      */   {
/*  407 */     return (char)getShort(paramInt);
/*      */   }
/*      */   
/*      */   public float getFloat(int paramInt)
/*      */   {
/*  412 */     return Float.intBitsToFloat(getInt(paramInt));
/*      */   }
/*      */   
/*      */   public double getDouble(int paramInt)
/*      */   {
/*  417 */     return Double.longBitsToDouble(getLong(paramInt));
/*      */   }
/*      */   
/*      */   public ByteBuf getBytes(int paramInt, byte[] paramArrayOfByte)
/*      */   {
/*  422 */     getBytes(paramInt, paramArrayOfByte, 0, paramArrayOfByte.length);
/*  423 */     return this;
/*      */   }
/*      */   
/*      */   public ByteBuf getBytes(int paramInt, ByteBuf paramByteBuf)
/*      */   {
/*  428 */     getBytes(paramInt, paramByteBuf, paramByteBuf.writableBytes());
/*  429 */     return this;
/*      */   }
/*      */   
/*      */   public ByteBuf getBytes(int paramInt1, ByteBuf paramByteBuf, int paramInt2)
/*      */   {
/*  434 */     getBytes(paramInt1, paramByteBuf, paramByteBuf.writerIndex(), paramInt2);
/*  435 */     paramByteBuf.writerIndex(paramByteBuf.writerIndex() + paramInt2);
/*  436 */     return this;
/*      */   }
/*      */   
/*      */   public ByteBuf setByte(int paramInt1, int paramInt2)
/*      */   {
/*  441 */     checkIndex(paramInt1);
/*  442 */     _setByte(paramInt1, paramInt2);
/*  443 */     return this;
/*      */   }
/*      */   
/*      */   protected abstract void _setByte(int paramInt1, int paramInt2);
/*      */   
/*      */   public ByteBuf setBoolean(int paramInt, boolean paramBoolean)
/*      */   {
/*  450 */     setByte(paramInt, paramBoolean ? 1 : 0);
/*  451 */     return this;
/*      */   }
/*      */   
/*      */   public ByteBuf setShort(int paramInt1, int paramInt2)
/*      */   {
/*  456 */     checkIndex(paramInt1, 2);
/*  457 */     _setShort(paramInt1, paramInt2);
/*  458 */     return this;
/*      */   }
/*      */   
/*      */   protected abstract void _setShort(int paramInt1, int paramInt2);
/*      */   
/*      */   public ByteBuf setChar(int paramInt1, int paramInt2)
/*      */   {
/*  465 */     setShort(paramInt1, paramInt2);
/*  466 */     return this;
/*      */   }
/*      */   
/*      */   public ByteBuf setMedium(int paramInt1, int paramInt2)
/*      */   {
/*  471 */     checkIndex(paramInt1, 3);
/*  472 */     _setMedium(paramInt1, paramInt2);
/*  473 */     return this;
/*      */   }
/*      */   
/*      */   protected abstract void _setMedium(int paramInt1, int paramInt2);
/*      */   
/*      */   public ByteBuf setInt(int paramInt1, int paramInt2)
/*      */   {
/*  480 */     checkIndex(paramInt1, 4);
/*  481 */     _setInt(paramInt1, paramInt2);
/*  482 */     return this;
/*      */   }
/*      */   
/*      */   protected abstract void _setInt(int paramInt1, int paramInt2);
/*      */   
/*      */   public ByteBuf setFloat(int paramInt, float paramFloat)
/*      */   {
/*  489 */     setInt(paramInt, Float.floatToRawIntBits(paramFloat));
/*  490 */     return this;
/*      */   }
/*      */   
/*      */   public ByteBuf setLong(int paramInt, long paramLong)
/*      */   {
/*  495 */     checkIndex(paramInt, 8);
/*  496 */     _setLong(paramInt, paramLong);
/*  497 */     return this;
/*      */   }
/*      */   
/*      */   protected abstract void _setLong(int paramInt, long paramLong);
/*      */   
/*      */   public ByteBuf setDouble(int paramInt, double paramDouble)
/*      */   {
/*  504 */     setLong(paramInt, Double.doubleToRawLongBits(paramDouble));
/*  505 */     return this;
/*      */   }
/*      */   
/*      */   public ByteBuf setBytes(int paramInt, byte[] paramArrayOfByte)
/*      */   {
/*  510 */     setBytes(paramInt, paramArrayOfByte, 0, paramArrayOfByte.length);
/*  511 */     return this;
/*      */   }
/*      */   
/*      */   public ByteBuf setBytes(int paramInt, ByteBuf paramByteBuf)
/*      */   {
/*  516 */     setBytes(paramInt, paramByteBuf, paramByteBuf.readableBytes());
/*  517 */     return this;
/*      */   }
/*      */   
/*      */   public ByteBuf setBytes(int paramInt1, ByteBuf paramByteBuf, int paramInt2)
/*      */   {
/*  522 */     checkIndex(paramInt1, paramInt2);
/*  523 */     if (paramByteBuf == null) {
/*  524 */       throw new NullPointerException("src");
/*      */     }
/*  526 */     if (paramInt2 > paramByteBuf.readableBytes()) {
/*  527 */       throw new IndexOutOfBoundsException(String.format("length(%d) exceeds src.readableBytes(%d) where src is: %s", new Object[] { Integer.valueOf(paramInt2), Integer.valueOf(paramByteBuf.readableBytes()), paramByteBuf }));
/*      */     }
/*      */     
/*      */ 
/*  531 */     setBytes(paramInt1, paramByteBuf, paramByteBuf.readerIndex(), paramInt2);
/*  532 */     paramByteBuf.readerIndex(paramByteBuf.readerIndex() + paramInt2);
/*  533 */     return this;
/*      */   }
/*      */   
/*      */   public ByteBuf setZero(int paramInt1, int paramInt2)
/*      */   {
/*  538 */     if (paramInt2 == 0) {
/*  539 */       return this;
/*      */     }
/*      */     
/*  542 */     checkIndex(paramInt1, paramInt2);
/*      */     
/*  544 */     int i = paramInt2 >>> 3;
/*  545 */     int j = paramInt2 & 0x7;
/*  546 */     for (int k = i; k > 0; k--) {
/*  547 */       setLong(paramInt1, 0L);
/*  548 */       paramInt1 += 8;
/*      */     }
/*  550 */     if (j == 4) {
/*  551 */       setInt(paramInt1, 0);
/*  552 */     } else if (j < 4) {
/*  553 */       for (k = j; k > 0; k--) {
/*  554 */         setByte(paramInt1, 0);
/*  555 */         paramInt1++;
/*      */       }
/*      */     } else {
/*  558 */       setInt(paramInt1, 0);
/*  559 */       paramInt1 += 4;
/*  560 */       for (k = j - 4; k > 0; k--) {
/*  561 */         setByte(paramInt1, 0);
/*  562 */         paramInt1++;
/*      */       }
/*      */     }
/*  565 */     return this;
/*      */   }
/*      */   
/*      */   public byte readByte()
/*      */   {
/*  570 */     checkReadableBytes(1);
/*  571 */     int i = this.readerIndex;
/*  572 */     byte b = getByte(i);
/*  573 */     this.readerIndex = (i + 1);
/*  574 */     return b;
/*      */   }
/*      */   
/*      */   public boolean readBoolean()
/*      */   {
/*  579 */     return readByte() != 0;
/*      */   }
/*      */   
/*      */   public short readUnsignedByte()
/*      */   {
/*  584 */     return (short)(readByte() & 0xFF);
/*      */   }
/*      */   
/*      */   public short readShort()
/*      */   {
/*  589 */     checkReadableBytes(2);
/*  590 */     short s = _getShort(this.readerIndex);
/*  591 */     this.readerIndex += 2;
/*  592 */     return s;
/*      */   }
/*      */   
/*      */   public int readUnsignedShort()
/*      */   {
/*  597 */     return readShort() & 0xFFFF;
/*      */   }
/*      */   
/*      */   public int readMedium()
/*      */   {
/*  602 */     int i = readUnsignedMedium();
/*  603 */     if ((i & 0x800000) != 0) {
/*  604 */       i |= 0xFF000000;
/*      */     }
/*  606 */     return i;
/*      */   }
/*      */   
/*      */   public int readUnsignedMedium()
/*      */   {
/*  611 */     checkReadableBytes(3);
/*  612 */     int i = _getUnsignedMedium(this.readerIndex);
/*  613 */     this.readerIndex += 3;
/*  614 */     return i;
/*      */   }
/*      */   
/*      */   public int readInt()
/*      */   {
/*  619 */     checkReadableBytes(4);
/*  620 */     int i = _getInt(this.readerIndex);
/*  621 */     this.readerIndex += 4;
/*  622 */     return i;
/*      */   }
/*      */   
/*      */   public long readUnsignedInt()
/*      */   {
/*  627 */     return readInt() & 0xFFFFFFFF;
/*      */   }
/*      */   
/*      */   public long readLong()
/*      */   {
/*  632 */     checkReadableBytes(8);
/*  633 */     long l = _getLong(this.readerIndex);
/*  634 */     this.readerIndex += 8;
/*  635 */     return l;
/*      */   }
/*      */   
/*      */   public char readChar()
/*      */   {
/*  640 */     return (char)readShort();
/*      */   }
/*      */   
/*      */   public float readFloat()
/*      */   {
/*  645 */     return Float.intBitsToFloat(readInt());
/*      */   }
/*      */   
/*      */   public double readDouble()
/*      */   {
/*  650 */     return Double.longBitsToDouble(readLong());
/*      */   }
/*      */   
/*      */   public ByteBuf readBytes(int paramInt)
/*      */   {
/*  655 */     checkReadableBytes(paramInt);
/*  656 */     if (paramInt == 0) {
/*  657 */       return Unpooled.EMPTY_BUFFER;
/*      */     }
/*      */     
/*      */ 
/*  661 */     ByteBuf localByteBuf = Unpooled.buffer(paramInt, this.maxCapacity);
/*  662 */     localByteBuf.writeBytes(this, this.readerIndex, paramInt);
/*  663 */     this.readerIndex += paramInt;
/*  664 */     return localByteBuf;
/*      */   }
/*      */   
/*      */   public ByteBuf readSlice(int paramInt)
/*      */   {
/*  669 */     ByteBuf localByteBuf = slice(this.readerIndex, paramInt);
/*  670 */     this.readerIndex += paramInt;
/*  671 */     return localByteBuf;
/*      */   }
/*      */   
/*      */   public ByteBuf readBytes(byte[] paramArrayOfByte, int paramInt1, int paramInt2)
/*      */   {
/*  676 */     checkReadableBytes(paramInt2);
/*  677 */     getBytes(this.readerIndex, paramArrayOfByte, paramInt1, paramInt2);
/*  678 */     this.readerIndex += paramInt2;
/*  679 */     return this;
/*      */   }
/*      */   
/*      */   public ByteBuf readBytes(byte[] paramArrayOfByte)
/*      */   {
/*  684 */     readBytes(paramArrayOfByte, 0, paramArrayOfByte.length);
/*  685 */     return this;
/*      */   }
/*      */   
/*      */   public ByteBuf readBytes(ByteBuf paramByteBuf)
/*      */   {
/*  690 */     readBytes(paramByteBuf, paramByteBuf.writableBytes());
/*  691 */     return this;
/*      */   }
/*      */   
/*      */   public ByteBuf readBytes(ByteBuf paramByteBuf, int paramInt)
/*      */   {
/*  696 */     if (paramInt > paramByteBuf.writableBytes()) {
/*  697 */       throw new IndexOutOfBoundsException(String.format("length(%d) exceeds dst.writableBytes(%d) where dst is: %s", new Object[] { Integer.valueOf(paramInt), Integer.valueOf(paramByteBuf.writableBytes()), paramByteBuf }));
/*      */     }
/*      */     
/*  700 */     readBytes(paramByteBuf, paramByteBuf.writerIndex(), paramInt);
/*  701 */     paramByteBuf.writerIndex(paramByteBuf.writerIndex() + paramInt);
/*  702 */     return this;
/*      */   }
/*      */   
/*      */   public ByteBuf readBytes(ByteBuf paramByteBuf, int paramInt1, int paramInt2)
/*      */   {
/*  707 */     checkReadableBytes(paramInt2);
/*  708 */     getBytes(this.readerIndex, paramByteBuf, paramInt1, paramInt2);
/*  709 */     this.readerIndex += paramInt2;
/*  710 */     return this;
/*      */   }
/*      */   
/*      */   public ByteBuf readBytes(ByteBuffer paramByteBuffer)
/*      */   {
/*  715 */     int i = paramByteBuffer.remaining();
/*  716 */     checkReadableBytes(i);
/*  717 */     getBytes(this.readerIndex, paramByteBuffer);
/*  718 */     this.readerIndex += i;
/*  719 */     return this;
/*      */   }
/*      */   
/*      */   public int readBytes(GatheringByteChannel paramGatheringByteChannel, int paramInt)
/*      */     throws IOException
/*      */   {
/*  725 */     checkReadableBytes(paramInt);
/*  726 */     int i = getBytes(this.readerIndex, paramGatheringByteChannel, paramInt);
/*  727 */     this.readerIndex += i;
/*  728 */     return i;
/*      */   }
/*      */   
/*      */   public ByteBuf readBytes(OutputStream paramOutputStream, int paramInt) throws IOException
/*      */   {
/*  733 */     checkReadableBytes(paramInt);
/*  734 */     getBytes(this.readerIndex, paramOutputStream, paramInt);
/*  735 */     this.readerIndex += paramInt;
/*  736 */     return this;
/*      */   }
/*      */   
/*      */   public ByteBuf skipBytes(int paramInt)
/*      */   {
/*  741 */     checkReadableBytes(paramInt);
/*  742 */     this.readerIndex += paramInt;
/*  743 */     return this;
/*      */   }
/*      */   
/*      */   public ByteBuf writeBoolean(boolean paramBoolean)
/*      */   {
/*  748 */     writeByte(paramBoolean ? 1 : 0);
/*  749 */     return this;
/*      */   }
/*      */   
/*      */   public ByteBuf writeByte(int paramInt)
/*      */   {
/*  754 */     ensureAccessible();
/*  755 */     ensureWritable(1);
/*  756 */     _setByte(this.writerIndex++, paramInt);
/*  757 */     return this;
/*      */   }
/*      */   
/*      */   public ByteBuf writeShort(int paramInt)
/*      */   {
/*  762 */     ensureAccessible();
/*  763 */     ensureWritable(2);
/*  764 */     _setShort(this.writerIndex, paramInt);
/*  765 */     this.writerIndex += 2;
/*  766 */     return this;
/*      */   }
/*      */   
/*      */   public ByteBuf writeMedium(int paramInt)
/*      */   {
/*  771 */     ensureAccessible();
/*  772 */     ensureWritable(3);
/*  773 */     _setMedium(this.writerIndex, paramInt);
/*  774 */     this.writerIndex += 3;
/*  775 */     return this;
/*      */   }
/*      */   
/*      */   public ByteBuf writeInt(int paramInt)
/*      */   {
/*  780 */     ensureAccessible();
/*  781 */     ensureWritable(4);
/*  782 */     _setInt(this.writerIndex, paramInt);
/*  783 */     this.writerIndex += 4;
/*  784 */     return this;
/*      */   }
/*      */   
/*      */   public ByteBuf writeLong(long paramLong)
/*      */   {
/*  789 */     ensureAccessible();
/*  790 */     ensureWritable(8);
/*  791 */     _setLong(this.writerIndex, paramLong);
/*  792 */     this.writerIndex += 8;
/*  793 */     return this;
/*      */   }
/*      */   
/*      */   public ByteBuf writeChar(int paramInt)
/*      */   {
/*  798 */     writeShort(paramInt);
/*  799 */     return this;
/*      */   }
/*      */   
/*      */   public ByteBuf writeFloat(float paramFloat)
/*      */   {
/*  804 */     writeInt(Float.floatToRawIntBits(paramFloat));
/*  805 */     return this;
/*      */   }
/*      */   
/*      */   public ByteBuf writeDouble(double paramDouble)
/*      */   {
/*  810 */     writeLong(Double.doubleToRawLongBits(paramDouble));
/*  811 */     return this;
/*      */   }
/*      */   
/*      */   public ByteBuf writeBytes(byte[] paramArrayOfByte, int paramInt1, int paramInt2)
/*      */   {
/*  816 */     ensureAccessible();
/*  817 */     ensureWritable(paramInt2);
/*  818 */     setBytes(this.writerIndex, paramArrayOfByte, paramInt1, paramInt2);
/*  819 */     this.writerIndex += paramInt2;
/*  820 */     return this;
/*      */   }
/*      */   
/*      */   public ByteBuf writeBytes(byte[] paramArrayOfByte)
/*      */   {
/*  825 */     writeBytes(paramArrayOfByte, 0, paramArrayOfByte.length);
/*  826 */     return this;
/*      */   }
/*      */   
/*      */   public ByteBuf writeBytes(ByteBuf paramByteBuf)
/*      */   {
/*  831 */     writeBytes(paramByteBuf, paramByteBuf.readableBytes());
/*  832 */     return this;
/*      */   }
/*      */   
/*      */   public ByteBuf writeBytes(ByteBuf paramByteBuf, int paramInt)
/*      */   {
/*  837 */     if (paramInt > paramByteBuf.readableBytes()) {
/*  838 */       throw new IndexOutOfBoundsException(String.format("length(%d) exceeds src.readableBytes(%d) where src is: %s", new Object[] { Integer.valueOf(paramInt), Integer.valueOf(paramByteBuf.readableBytes()), paramByteBuf }));
/*      */     }
/*      */     
/*  841 */     writeBytes(paramByteBuf, paramByteBuf.readerIndex(), paramInt);
/*  842 */     paramByteBuf.readerIndex(paramByteBuf.readerIndex() + paramInt);
/*  843 */     return this;
/*      */   }
/*      */   
/*      */   public ByteBuf writeBytes(ByteBuf paramByteBuf, int paramInt1, int paramInt2)
/*      */   {
/*  848 */     ensureAccessible();
/*  849 */     ensureWritable(paramInt2);
/*  850 */     setBytes(this.writerIndex, paramByteBuf, paramInt1, paramInt2);
/*  851 */     this.writerIndex += paramInt2;
/*  852 */     return this;
/*      */   }
/*      */   
/*      */   public ByteBuf writeBytes(ByteBuffer paramByteBuffer)
/*      */   {
/*  857 */     ensureAccessible();
/*  858 */     int i = paramByteBuffer.remaining();
/*  859 */     ensureWritable(i);
/*  860 */     setBytes(this.writerIndex, paramByteBuffer);
/*  861 */     this.writerIndex += i;
/*  862 */     return this;
/*      */   }
/*      */   
/*      */   public int writeBytes(InputStream paramInputStream, int paramInt)
/*      */     throws IOException
/*      */   {
/*  868 */     ensureAccessible();
/*  869 */     ensureWritable(paramInt);
/*  870 */     int i = setBytes(this.writerIndex, paramInputStream, paramInt);
/*  871 */     if (i > 0) {
/*  872 */       this.writerIndex += i;
/*      */     }
/*  874 */     return i;
/*      */   }
/*      */   
/*      */   public int writeBytes(ScatteringByteChannel paramScatteringByteChannel, int paramInt) throws IOException
/*      */   {
/*  879 */     ensureAccessible();
/*  880 */     ensureWritable(paramInt);
/*  881 */     int i = setBytes(this.writerIndex, paramScatteringByteChannel, paramInt);
/*  882 */     if (i > 0) {
/*  883 */       this.writerIndex += i;
/*      */     }
/*  885 */     return i;
/*      */   }
/*      */   
/*      */   public ByteBuf writeZero(int paramInt)
/*      */   {
/*  890 */     if (paramInt == 0) {
/*  891 */       return this;
/*      */     }
/*      */     
/*  894 */     ensureWritable(paramInt);
/*  895 */     checkIndex(this.writerIndex, paramInt);
/*      */     
/*  897 */     int i = paramInt >>> 3;
/*  898 */     int j = paramInt & 0x7;
/*  899 */     for (int k = i; k > 0; k--) {
/*  900 */       writeLong(0L);
/*      */     }
/*  902 */     if (j == 4) {
/*  903 */       writeInt(0);
/*  904 */     } else if (j < 4) {
/*  905 */       for (k = j; k > 0; k--) {
/*  906 */         writeByte(0);
/*      */       }
/*      */     } else {
/*  909 */       writeInt(0);
/*  910 */       for (k = j - 4; k > 0; k--) {
/*  911 */         writeByte(0);
/*      */       }
/*      */     }
/*  914 */     return this;
/*      */   }
/*      */   
/*      */   public ByteBuf copy()
/*      */   {
/*  919 */     return copy(this.readerIndex, readableBytes());
/*      */   }
/*      */   
/*      */   public ByteBuf duplicate()
/*      */   {
/*  924 */     return new DuplicatedByteBuf(this);
/*      */   }
/*      */   
/*      */   public ByteBuf slice()
/*      */   {
/*  929 */     return slice(this.readerIndex, readableBytes());
/*      */   }
/*      */   
/*      */   public ByteBuf slice(int paramInt1, int paramInt2)
/*      */   {
/*  934 */     if (paramInt2 == 0) {
/*  935 */       return Unpooled.EMPTY_BUFFER;
/*      */     }
/*      */     
/*  938 */     return new SlicedByteBuf(this, paramInt1, paramInt2);
/*      */   }
/*      */   
/*      */   public ByteBuffer nioBuffer()
/*      */   {
/*  943 */     return nioBuffer(this.readerIndex, readableBytes());
/*      */   }
/*      */   
/*      */   public ByteBuffer[] nioBuffers()
/*      */   {
/*  948 */     return nioBuffers(this.readerIndex, readableBytes());
/*      */   }
/*      */   
/*      */   public String toString(Charset paramCharset)
/*      */   {
/*  953 */     return toString(this.readerIndex, readableBytes(), paramCharset);
/*      */   }
/*      */   
/*      */   public String toString(int paramInt1, int paramInt2, Charset paramCharset)
/*      */   {
/*  958 */     if (paramInt2 == 0) {
/*  959 */       return "";
/*      */     }
/*      */     
/*      */     ByteBuffer localByteBuffer;
/*  963 */     if (nioBufferCount() == 1) {
/*  964 */       localByteBuffer = nioBuffer(paramInt1, paramInt2);
/*      */     } else {
/*  966 */       localByteBuffer = ByteBuffer.allocate(paramInt2);
/*  967 */       getBytes(paramInt1, localByteBuffer);
/*  968 */       localByteBuffer.flip();
/*      */     }
/*      */     
/*  971 */     return ByteBufUtil.decodeString(localByteBuffer, paramCharset);
/*      */   }
/*      */   
/*      */   public int indexOf(int paramInt1, int paramInt2, byte paramByte)
/*      */   {
/*  976 */     return ByteBufUtil.indexOf(this, paramInt1, paramInt2, paramByte);
/*      */   }
/*      */   
/*      */   public int bytesBefore(byte paramByte)
/*      */   {
/*  981 */     return bytesBefore(readerIndex(), readableBytes(), paramByte);
/*      */   }
/*      */   
/*      */   public int bytesBefore(int paramInt, byte paramByte)
/*      */   {
/*  986 */     checkReadableBytes(paramInt);
/*  987 */     return bytesBefore(readerIndex(), paramInt, paramByte);
/*      */   }
/*      */   
/*      */   public int bytesBefore(int paramInt1, int paramInt2, byte paramByte)
/*      */   {
/*  992 */     int i = indexOf(paramInt1, paramInt1 + paramInt2, paramByte);
/*  993 */     if (i < 0) {
/*  994 */       return -1;
/*      */     }
/*  996 */     return i - paramInt1;
/*      */   }
/*      */   
/*      */   public int forEachByte(ByteBufProcessor paramByteBufProcessor)
/*      */   {
/* 1001 */     int i = this.readerIndex;
/* 1002 */     int j = this.writerIndex - i;
/* 1003 */     ensureAccessible();
/* 1004 */     return forEachByteAsc0(i, j, paramByteBufProcessor);
/*      */   }
/*      */   
/*      */   public int forEachByte(int paramInt1, int paramInt2, ByteBufProcessor paramByteBufProcessor)
/*      */   {
/* 1009 */     checkIndex(paramInt1, paramInt2);
/* 1010 */     return forEachByteAsc0(paramInt1, paramInt2, paramByteBufProcessor);
/*      */   }
/*      */   
/*      */   private int forEachByteAsc0(int paramInt1, int paramInt2, ByteBufProcessor paramByteBufProcessor) {
/* 1014 */     if (paramByteBufProcessor == null) {
/* 1015 */       throw new NullPointerException("processor");
/*      */     }
/*      */     
/* 1018 */     if (paramInt2 == 0) {
/* 1019 */       return -1;
/*      */     }
/*      */     
/* 1022 */     int i = paramInt1 + paramInt2;
/* 1023 */     int j = paramInt1;
/*      */     try {
/*      */       do {
/* 1026 */         if (paramByteBufProcessor.process(_getByte(j))) {
/* 1027 */           j++;
/*      */         } else {
/* 1029 */           return j;
/*      */         }
/* 1031 */       } while (j < i);
/*      */     } catch (Exception localException) {
/* 1033 */       PlatformDependent.throwException(localException);
/*      */     }
/*      */     
/* 1036 */     return -1;
/*      */   }
/*      */   
/*      */   public int forEachByteDesc(ByteBufProcessor paramByteBufProcessor)
/*      */   {
/* 1041 */     int i = this.readerIndex;
/* 1042 */     int j = this.writerIndex - i;
/* 1043 */     ensureAccessible();
/* 1044 */     return forEachByteDesc0(i, j, paramByteBufProcessor);
/*      */   }
/*      */   
/*      */   public int forEachByteDesc(int paramInt1, int paramInt2, ByteBufProcessor paramByteBufProcessor)
/*      */   {
/* 1049 */     checkIndex(paramInt1, paramInt2);
/*      */     
/* 1051 */     return forEachByteDesc0(paramInt1, paramInt2, paramByteBufProcessor);
/*      */   }
/*      */   
/*      */   private int forEachByteDesc0(int paramInt1, int paramInt2, ByteBufProcessor paramByteBufProcessor)
/*      */   {
/* 1056 */     if (paramByteBufProcessor == null) {
/* 1057 */       throw new NullPointerException("processor");
/*      */     }
/*      */     
/* 1060 */     if (paramInt2 == 0) {
/* 1061 */       return -1;
/*      */     }
/*      */     
/* 1064 */     int i = paramInt1 + paramInt2 - 1;
/*      */     try {
/*      */       do {
/* 1067 */         if (paramByteBufProcessor.process(_getByte(i))) {
/* 1068 */           i--;
/*      */         } else {
/* 1070 */           return i;
/*      */         }
/* 1072 */       } while (i >= paramInt1);
/*      */     } catch (Exception localException) {
/* 1074 */       PlatformDependent.throwException(localException);
/*      */     }
/*      */     
/* 1077 */     return -1;
/*      */   }
/*      */   
/*      */   public int hashCode()
/*      */   {
/* 1082 */     return ByteBufUtil.hashCode(this);
/*      */   }
/*      */   
/*      */   public boolean equals(Object paramObject)
/*      */   {
/* 1087 */     if (this == paramObject) {
/* 1088 */       return true;
/*      */     }
/* 1090 */     if ((paramObject instanceof ByteBuf)) {
/* 1091 */       return ByteBufUtil.equals(this, (ByteBuf)paramObject);
/*      */     }
/* 1093 */     return false;
/*      */   }
/*      */   
/*      */   public int compareTo(ByteBuf paramByteBuf)
/*      */   {
/* 1098 */     return ByteBufUtil.compare(this, paramByteBuf);
/*      */   }
/*      */   
/*      */   public String toString()
/*      */   {
/* 1103 */     if (refCnt() == 0) {
/* 1104 */       return StringUtil.simpleClassName(this) + "(freed)";
/*      */     }
/*      */     
/* 1107 */     StringBuilder localStringBuilder = new StringBuilder();
/* 1108 */     localStringBuilder.append(StringUtil.simpleClassName(this));
/* 1109 */     localStringBuilder.append("(ridx: ");
/* 1110 */     localStringBuilder.append(this.readerIndex);
/* 1111 */     localStringBuilder.append(", widx: ");
/* 1112 */     localStringBuilder.append(this.writerIndex);
/* 1113 */     localStringBuilder.append(", cap: ");
/* 1114 */     localStringBuilder.append(capacity());
/* 1115 */     if (this.maxCapacity != Integer.MAX_VALUE) {
/* 1116 */       localStringBuilder.append('/');
/* 1117 */       localStringBuilder.append(this.maxCapacity);
/*      */     }
/*      */     
/* 1120 */     ByteBuf localByteBuf = unwrap();
/* 1121 */     if (localByteBuf != null) {
/* 1122 */       localStringBuilder.append(", unwrapped: ");
/* 1123 */       localStringBuilder.append(localByteBuf);
/*      */     }
/* 1125 */     localStringBuilder.append(')');
/* 1126 */     return localStringBuilder.toString();
/*      */   }
/*      */   
/*      */   protected final void checkIndex(int paramInt) {
/* 1130 */     ensureAccessible();
/* 1131 */     if ((paramInt < 0) || (paramInt >= capacity())) {
/* 1132 */       throw new IndexOutOfBoundsException(String.format("index: %d (expected: range(0, %d))", new Object[] { Integer.valueOf(paramInt), Integer.valueOf(capacity()) }));
/*      */     }
/*      */   }
/*      */   
/*      */   protected final void checkIndex(int paramInt1, int paramInt2)
/*      */   {
/* 1138 */     ensureAccessible();
/* 1139 */     if (paramInt2 < 0) {
/* 1140 */       throw new IllegalArgumentException("length: " + paramInt2 + " (expected: >= 0)");
/*      */     }
/* 1142 */     if ((paramInt1 < 0) || (paramInt1 > capacity() - paramInt2)) {
/* 1143 */       throw new IndexOutOfBoundsException(String.format("index: %d, length: %d (expected: range(0, %d))", new Object[] { Integer.valueOf(paramInt1), Integer.valueOf(paramInt2), Integer.valueOf(capacity()) }));
/*      */     }
/*      */   }
/*      */   
/*      */   protected final void checkSrcIndex(int paramInt1, int paramInt2, int paramInt3, int paramInt4)
/*      */   {
/* 1149 */     checkIndex(paramInt1, paramInt2);
/* 1150 */     if ((paramInt3 < 0) || (paramInt3 > paramInt4 - paramInt2)) {
/* 1151 */       throw new IndexOutOfBoundsException(String.format("srcIndex: %d, length: %d (expected: range(0, %d))", new Object[] { Integer.valueOf(paramInt3), Integer.valueOf(paramInt2), Integer.valueOf(paramInt4) }));
/*      */     }
/*      */   }
/*      */   
/*      */   protected final void checkDstIndex(int paramInt1, int paramInt2, int paramInt3, int paramInt4)
/*      */   {
/* 1157 */     checkIndex(paramInt1, paramInt2);
/* 1158 */     if ((paramInt3 < 0) || (paramInt3 > paramInt4 - paramInt2)) {
/* 1159 */       throw new IndexOutOfBoundsException(String.format("dstIndex: %d, length: %d (expected: range(0, %d))", new Object[] { Integer.valueOf(paramInt3), Integer.valueOf(paramInt2), Integer.valueOf(paramInt4) }));
/*      */     }
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   protected final void checkReadableBytes(int paramInt)
/*      */   {
/* 1170 */     ensureAccessible();
/* 1171 */     if (paramInt < 0) {
/* 1172 */       throw new IllegalArgumentException("minimumReadableBytes: " + paramInt + " (expected: >= 0)");
/*      */     }
/* 1174 */     if (this.readerIndex > this.writerIndex - paramInt) {
/* 1175 */       throw new IndexOutOfBoundsException(String.format("readerIndex(%d) + length(%d) exceeds writerIndex(%d): %s", new Object[] { Integer.valueOf(this.readerIndex), Integer.valueOf(paramInt), Integer.valueOf(this.writerIndex), this }));
/*      */     }
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   protected final void ensureAccessible()
/*      */   {
/* 1186 */     if (refCnt() == 0) {
/* 1187 */       throw new IllegalReferenceCountException(0);
/*      */     }
/*      */   }
/*      */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\io\netty\buffer\AbstractByteBuf.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */
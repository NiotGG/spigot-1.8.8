/*     */ package io.netty.buffer;
/*     */ 
/*     */ import io.netty.util.ResourceLeak;
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
/*     */ final class AdvancedLeakAwareByteBuf
/*     */   extends WrappedByteBuf
/*     */ {
/*     */   private final ResourceLeak leak;
/*     */   
/*     */   AdvancedLeakAwareByteBuf(ByteBuf paramByteBuf, ResourceLeak paramResourceLeak)
/*     */   {
/*  35 */     super(paramByteBuf);
/*  36 */     this.leak = paramResourceLeak;
/*     */   }
/*     */   
/*     */   public boolean release()
/*     */   {
/*  41 */     boolean bool = super.release();
/*  42 */     if (bool) {
/*  43 */       this.leak.close();
/*     */     } else {
/*  45 */       this.leak.record();
/*     */     }
/*  47 */     return bool;
/*     */   }
/*     */   
/*     */   public boolean release(int paramInt)
/*     */   {
/*  52 */     boolean bool = super.release(paramInt);
/*  53 */     if (bool) {
/*  54 */       this.leak.close();
/*     */     } else {
/*  56 */       this.leak.record();
/*     */     }
/*  58 */     return bool;
/*     */   }
/*     */   
/*     */   public ByteBuf order(ByteOrder paramByteOrder)
/*     */   {
/*  63 */     this.leak.record();
/*  64 */     if (order() == paramByteOrder) {
/*  65 */       return this;
/*     */     }
/*  67 */     return new AdvancedLeakAwareByteBuf(super.order(paramByteOrder), this.leak);
/*     */   }
/*     */   
/*     */ 
/*     */   public ByteBuf slice()
/*     */   {
/*  73 */     this.leak.record();
/*  74 */     return new AdvancedLeakAwareByteBuf(super.slice(), this.leak);
/*     */   }
/*     */   
/*     */   public ByteBuf slice(int paramInt1, int paramInt2)
/*     */   {
/*  79 */     this.leak.record();
/*  80 */     return new AdvancedLeakAwareByteBuf(super.slice(paramInt1, paramInt2), this.leak);
/*     */   }
/*     */   
/*     */   public ByteBuf duplicate()
/*     */   {
/*  85 */     this.leak.record();
/*  86 */     return new AdvancedLeakAwareByteBuf(super.duplicate(), this.leak);
/*     */   }
/*     */   
/*     */   public ByteBuf readSlice(int paramInt)
/*     */   {
/*  91 */     this.leak.record();
/*  92 */     return new AdvancedLeakAwareByteBuf(super.readSlice(paramInt), this.leak);
/*     */   }
/*     */   
/*     */   public ByteBuf discardReadBytes()
/*     */   {
/*  97 */     this.leak.record();
/*  98 */     return super.discardReadBytes();
/*     */   }
/*     */   
/*     */   public ByteBuf discardSomeReadBytes()
/*     */   {
/* 103 */     this.leak.record();
/* 104 */     return super.discardSomeReadBytes();
/*     */   }
/*     */   
/*     */   public ByteBuf ensureWritable(int paramInt)
/*     */   {
/* 109 */     this.leak.record();
/* 110 */     return super.ensureWritable(paramInt);
/*     */   }
/*     */   
/*     */   public int ensureWritable(int paramInt, boolean paramBoolean)
/*     */   {
/* 115 */     this.leak.record();
/* 116 */     return super.ensureWritable(paramInt, paramBoolean);
/*     */   }
/*     */   
/*     */   public boolean getBoolean(int paramInt)
/*     */   {
/* 121 */     this.leak.record();
/* 122 */     return super.getBoolean(paramInt);
/*     */   }
/*     */   
/*     */   public byte getByte(int paramInt)
/*     */   {
/* 127 */     this.leak.record();
/* 128 */     return super.getByte(paramInt);
/*     */   }
/*     */   
/*     */   public short getUnsignedByte(int paramInt)
/*     */   {
/* 133 */     this.leak.record();
/* 134 */     return super.getUnsignedByte(paramInt);
/*     */   }
/*     */   
/*     */   public short getShort(int paramInt)
/*     */   {
/* 139 */     this.leak.record();
/* 140 */     return super.getShort(paramInt);
/*     */   }
/*     */   
/*     */   public int getUnsignedShort(int paramInt)
/*     */   {
/* 145 */     this.leak.record();
/* 146 */     return super.getUnsignedShort(paramInt);
/*     */   }
/*     */   
/*     */   public int getMedium(int paramInt)
/*     */   {
/* 151 */     this.leak.record();
/* 152 */     return super.getMedium(paramInt);
/*     */   }
/*     */   
/*     */   public int getUnsignedMedium(int paramInt)
/*     */   {
/* 157 */     this.leak.record();
/* 158 */     return super.getUnsignedMedium(paramInt);
/*     */   }
/*     */   
/*     */   public int getInt(int paramInt)
/*     */   {
/* 163 */     this.leak.record();
/* 164 */     return super.getInt(paramInt);
/*     */   }
/*     */   
/*     */   public long getUnsignedInt(int paramInt)
/*     */   {
/* 169 */     this.leak.record();
/* 170 */     return super.getUnsignedInt(paramInt);
/*     */   }
/*     */   
/*     */   public long getLong(int paramInt)
/*     */   {
/* 175 */     this.leak.record();
/* 176 */     return super.getLong(paramInt);
/*     */   }
/*     */   
/*     */   public char getChar(int paramInt)
/*     */   {
/* 181 */     this.leak.record();
/* 182 */     return super.getChar(paramInt);
/*     */   }
/*     */   
/*     */   public float getFloat(int paramInt)
/*     */   {
/* 187 */     this.leak.record();
/* 188 */     return super.getFloat(paramInt);
/*     */   }
/*     */   
/*     */   public double getDouble(int paramInt)
/*     */   {
/* 193 */     this.leak.record();
/* 194 */     return super.getDouble(paramInt);
/*     */   }
/*     */   
/*     */   public ByteBuf getBytes(int paramInt, ByteBuf paramByteBuf)
/*     */   {
/* 199 */     this.leak.record();
/* 200 */     return super.getBytes(paramInt, paramByteBuf);
/*     */   }
/*     */   
/*     */   public ByteBuf getBytes(int paramInt1, ByteBuf paramByteBuf, int paramInt2)
/*     */   {
/* 205 */     this.leak.record();
/* 206 */     return super.getBytes(paramInt1, paramByteBuf, paramInt2);
/*     */   }
/*     */   
/*     */   public ByteBuf getBytes(int paramInt1, ByteBuf paramByteBuf, int paramInt2, int paramInt3)
/*     */   {
/* 211 */     this.leak.record();
/* 212 */     return super.getBytes(paramInt1, paramByteBuf, paramInt2, paramInt3);
/*     */   }
/*     */   
/*     */   public ByteBuf getBytes(int paramInt, byte[] paramArrayOfByte)
/*     */   {
/* 217 */     this.leak.record();
/* 218 */     return super.getBytes(paramInt, paramArrayOfByte);
/*     */   }
/*     */   
/*     */   public ByteBuf getBytes(int paramInt1, byte[] paramArrayOfByte, int paramInt2, int paramInt3)
/*     */   {
/* 223 */     this.leak.record();
/* 224 */     return super.getBytes(paramInt1, paramArrayOfByte, paramInt2, paramInt3);
/*     */   }
/*     */   
/*     */   public ByteBuf getBytes(int paramInt, ByteBuffer paramByteBuffer)
/*     */   {
/* 229 */     this.leak.record();
/* 230 */     return super.getBytes(paramInt, paramByteBuffer);
/*     */   }
/*     */   
/*     */   public ByteBuf getBytes(int paramInt1, OutputStream paramOutputStream, int paramInt2) throws IOException
/*     */   {
/* 235 */     this.leak.record();
/* 236 */     return super.getBytes(paramInt1, paramOutputStream, paramInt2);
/*     */   }
/*     */   
/*     */   public int getBytes(int paramInt1, GatheringByteChannel paramGatheringByteChannel, int paramInt2) throws IOException
/*     */   {
/* 241 */     this.leak.record();
/* 242 */     return super.getBytes(paramInt1, paramGatheringByteChannel, paramInt2);
/*     */   }
/*     */   
/*     */   public ByteBuf setBoolean(int paramInt, boolean paramBoolean)
/*     */   {
/* 247 */     this.leak.record();
/* 248 */     return super.setBoolean(paramInt, paramBoolean);
/*     */   }
/*     */   
/*     */   public ByteBuf setByte(int paramInt1, int paramInt2)
/*     */   {
/* 253 */     this.leak.record();
/* 254 */     return super.setByte(paramInt1, paramInt2);
/*     */   }
/*     */   
/*     */   public ByteBuf setShort(int paramInt1, int paramInt2)
/*     */   {
/* 259 */     this.leak.record();
/* 260 */     return super.setShort(paramInt1, paramInt2);
/*     */   }
/*     */   
/*     */   public ByteBuf setMedium(int paramInt1, int paramInt2)
/*     */   {
/* 265 */     this.leak.record();
/* 266 */     return super.setMedium(paramInt1, paramInt2);
/*     */   }
/*     */   
/*     */   public ByteBuf setInt(int paramInt1, int paramInt2)
/*     */   {
/* 271 */     this.leak.record();
/* 272 */     return super.setInt(paramInt1, paramInt2);
/*     */   }
/*     */   
/*     */   public ByteBuf setLong(int paramInt, long paramLong)
/*     */   {
/* 277 */     this.leak.record();
/* 278 */     return super.setLong(paramInt, paramLong);
/*     */   }
/*     */   
/*     */   public ByteBuf setChar(int paramInt1, int paramInt2)
/*     */   {
/* 283 */     this.leak.record();
/* 284 */     return super.setChar(paramInt1, paramInt2);
/*     */   }
/*     */   
/*     */   public ByteBuf setFloat(int paramInt, float paramFloat)
/*     */   {
/* 289 */     this.leak.record();
/* 290 */     return super.setFloat(paramInt, paramFloat);
/*     */   }
/*     */   
/*     */   public ByteBuf setDouble(int paramInt, double paramDouble)
/*     */   {
/* 295 */     this.leak.record();
/* 296 */     return super.setDouble(paramInt, paramDouble);
/*     */   }
/*     */   
/*     */   public ByteBuf setBytes(int paramInt, ByteBuf paramByteBuf)
/*     */   {
/* 301 */     this.leak.record();
/* 302 */     return super.setBytes(paramInt, paramByteBuf);
/*     */   }
/*     */   
/*     */   public ByteBuf setBytes(int paramInt1, ByteBuf paramByteBuf, int paramInt2)
/*     */   {
/* 307 */     this.leak.record();
/* 308 */     return super.setBytes(paramInt1, paramByteBuf, paramInt2);
/*     */   }
/*     */   
/*     */   public ByteBuf setBytes(int paramInt1, ByteBuf paramByteBuf, int paramInt2, int paramInt3)
/*     */   {
/* 313 */     this.leak.record();
/* 314 */     return super.setBytes(paramInt1, paramByteBuf, paramInt2, paramInt3);
/*     */   }
/*     */   
/*     */   public ByteBuf setBytes(int paramInt, byte[] paramArrayOfByte)
/*     */   {
/* 319 */     this.leak.record();
/* 320 */     return super.setBytes(paramInt, paramArrayOfByte);
/*     */   }
/*     */   
/*     */   public ByteBuf setBytes(int paramInt1, byte[] paramArrayOfByte, int paramInt2, int paramInt3)
/*     */   {
/* 325 */     this.leak.record();
/* 326 */     return super.setBytes(paramInt1, paramArrayOfByte, paramInt2, paramInt3);
/*     */   }
/*     */   
/*     */   public ByteBuf setBytes(int paramInt, ByteBuffer paramByteBuffer)
/*     */   {
/* 331 */     this.leak.record();
/* 332 */     return super.setBytes(paramInt, paramByteBuffer);
/*     */   }
/*     */   
/*     */   public int setBytes(int paramInt1, InputStream paramInputStream, int paramInt2) throws IOException
/*     */   {
/* 337 */     this.leak.record();
/* 338 */     return super.setBytes(paramInt1, paramInputStream, paramInt2);
/*     */   }
/*     */   
/*     */   public int setBytes(int paramInt1, ScatteringByteChannel paramScatteringByteChannel, int paramInt2) throws IOException
/*     */   {
/* 343 */     this.leak.record();
/* 344 */     return super.setBytes(paramInt1, paramScatteringByteChannel, paramInt2);
/*     */   }
/*     */   
/*     */   public ByteBuf setZero(int paramInt1, int paramInt2)
/*     */   {
/* 349 */     this.leak.record();
/* 350 */     return super.setZero(paramInt1, paramInt2);
/*     */   }
/*     */   
/*     */   public boolean readBoolean()
/*     */   {
/* 355 */     this.leak.record();
/* 356 */     return super.readBoolean();
/*     */   }
/*     */   
/*     */   public byte readByte()
/*     */   {
/* 361 */     this.leak.record();
/* 362 */     return super.readByte();
/*     */   }
/*     */   
/*     */   public short readUnsignedByte()
/*     */   {
/* 367 */     this.leak.record();
/* 368 */     return super.readUnsignedByte();
/*     */   }
/*     */   
/*     */   public short readShort()
/*     */   {
/* 373 */     this.leak.record();
/* 374 */     return super.readShort();
/*     */   }
/*     */   
/*     */   public int readUnsignedShort()
/*     */   {
/* 379 */     this.leak.record();
/* 380 */     return super.readUnsignedShort();
/*     */   }
/*     */   
/*     */   public int readMedium()
/*     */   {
/* 385 */     this.leak.record();
/* 386 */     return super.readMedium();
/*     */   }
/*     */   
/*     */   public int readUnsignedMedium()
/*     */   {
/* 391 */     this.leak.record();
/* 392 */     return super.readUnsignedMedium();
/*     */   }
/*     */   
/*     */   public int readInt()
/*     */   {
/* 397 */     this.leak.record();
/* 398 */     return super.readInt();
/*     */   }
/*     */   
/*     */   public long readUnsignedInt()
/*     */   {
/* 403 */     this.leak.record();
/* 404 */     return super.readUnsignedInt();
/*     */   }
/*     */   
/*     */   public long readLong()
/*     */   {
/* 409 */     this.leak.record();
/* 410 */     return super.readLong();
/*     */   }
/*     */   
/*     */   public char readChar()
/*     */   {
/* 415 */     this.leak.record();
/* 416 */     return super.readChar();
/*     */   }
/*     */   
/*     */   public float readFloat()
/*     */   {
/* 421 */     this.leak.record();
/* 422 */     return super.readFloat();
/*     */   }
/*     */   
/*     */   public double readDouble()
/*     */   {
/* 427 */     this.leak.record();
/* 428 */     return super.readDouble();
/*     */   }
/*     */   
/*     */   public ByteBuf readBytes(int paramInt)
/*     */   {
/* 433 */     this.leak.record();
/* 434 */     return super.readBytes(paramInt);
/*     */   }
/*     */   
/*     */   public ByteBuf readBytes(ByteBuf paramByteBuf)
/*     */   {
/* 439 */     this.leak.record();
/* 440 */     return super.readBytes(paramByteBuf);
/*     */   }
/*     */   
/*     */   public ByteBuf readBytes(ByteBuf paramByteBuf, int paramInt)
/*     */   {
/* 445 */     this.leak.record();
/* 446 */     return super.readBytes(paramByteBuf, paramInt);
/*     */   }
/*     */   
/*     */   public ByteBuf readBytes(ByteBuf paramByteBuf, int paramInt1, int paramInt2)
/*     */   {
/* 451 */     this.leak.record();
/* 452 */     return super.readBytes(paramByteBuf, paramInt1, paramInt2);
/*     */   }
/*     */   
/*     */   public ByteBuf readBytes(byte[] paramArrayOfByte)
/*     */   {
/* 457 */     this.leak.record();
/* 458 */     return super.readBytes(paramArrayOfByte);
/*     */   }
/*     */   
/*     */   public ByteBuf readBytes(byte[] paramArrayOfByte, int paramInt1, int paramInt2)
/*     */   {
/* 463 */     this.leak.record();
/* 464 */     return super.readBytes(paramArrayOfByte, paramInt1, paramInt2);
/*     */   }
/*     */   
/*     */   public ByteBuf readBytes(ByteBuffer paramByteBuffer)
/*     */   {
/* 469 */     this.leak.record();
/* 470 */     return super.readBytes(paramByteBuffer);
/*     */   }
/*     */   
/*     */   public ByteBuf readBytes(OutputStream paramOutputStream, int paramInt) throws IOException
/*     */   {
/* 475 */     this.leak.record();
/* 476 */     return super.readBytes(paramOutputStream, paramInt);
/*     */   }
/*     */   
/*     */   public int readBytes(GatheringByteChannel paramGatheringByteChannel, int paramInt) throws IOException
/*     */   {
/* 481 */     this.leak.record();
/* 482 */     return super.readBytes(paramGatheringByteChannel, paramInt);
/*     */   }
/*     */   
/*     */   public ByteBuf skipBytes(int paramInt)
/*     */   {
/* 487 */     this.leak.record();
/* 488 */     return super.skipBytes(paramInt);
/*     */   }
/*     */   
/*     */   public ByteBuf writeBoolean(boolean paramBoolean)
/*     */   {
/* 493 */     this.leak.record();
/* 494 */     return super.writeBoolean(paramBoolean);
/*     */   }
/*     */   
/*     */   public ByteBuf writeByte(int paramInt)
/*     */   {
/* 499 */     this.leak.record();
/* 500 */     return super.writeByte(paramInt);
/*     */   }
/*     */   
/*     */   public ByteBuf writeShort(int paramInt)
/*     */   {
/* 505 */     this.leak.record();
/* 506 */     return super.writeShort(paramInt);
/*     */   }
/*     */   
/*     */   public ByteBuf writeMedium(int paramInt)
/*     */   {
/* 511 */     this.leak.record();
/* 512 */     return super.writeMedium(paramInt);
/*     */   }
/*     */   
/*     */   public ByteBuf writeInt(int paramInt)
/*     */   {
/* 517 */     this.leak.record();
/* 518 */     return super.writeInt(paramInt);
/*     */   }
/*     */   
/*     */   public ByteBuf writeLong(long paramLong)
/*     */   {
/* 523 */     this.leak.record();
/* 524 */     return super.writeLong(paramLong);
/*     */   }
/*     */   
/*     */   public ByteBuf writeChar(int paramInt)
/*     */   {
/* 529 */     this.leak.record();
/* 530 */     return super.writeChar(paramInt);
/*     */   }
/*     */   
/*     */   public ByteBuf writeFloat(float paramFloat)
/*     */   {
/* 535 */     this.leak.record();
/* 536 */     return super.writeFloat(paramFloat);
/*     */   }
/*     */   
/*     */   public ByteBuf writeDouble(double paramDouble)
/*     */   {
/* 541 */     this.leak.record();
/* 542 */     return super.writeDouble(paramDouble);
/*     */   }
/*     */   
/*     */   public ByteBuf writeBytes(ByteBuf paramByteBuf)
/*     */   {
/* 547 */     this.leak.record();
/* 548 */     return super.writeBytes(paramByteBuf);
/*     */   }
/*     */   
/*     */   public ByteBuf writeBytes(ByteBuf paramByteBuf, int paramInt)
/*     */   {
/* 553 */     this.leak.record();
/* 554 */     return super.writeBytes(paramByteBuf, paramInt);
/*     */   }
/*     */   
/*     */   public ByteBuf writeBytes(ByteBuf paramByteBuf, int paramInt1, int paramInt2)
/*     */   {
/* 559 */     this.leak.record();
/* 560 */     return super.writeBytes(paramByteBuf, paramInt1, paramInt2);
/*     */   }
/*     */   
/*     */   public ByteBuf writeBytes(byte[] paramArrayOfByte)
/*     */   {
/* 565 */     this.leak.record();
/* 566 */     return super.writeBytes(paramArrayOfByte);
/*     */   }
/*     */   
/*     */   public ByteBuf writeBytes(byte[] paramArrayOfByte, int paramInt1, int paramInt2)
/*     */   {
/* 571 */     this.leak.record();
/* 572 */     return super.writeBytes(paramArrayOfByte, paramInt1, paramInt2);
/*     */   }
/*     */   
/*     */   public ByteBuf writeBytes(ByteBuffer paramByteBuffer)
/*     */   {
/* 577 */     this.leak.record();
/* 578 */     return super.writeBytes(paramByteBuffer);
/*     */   }
/*     */   
/*     */   public int writeBytes(InputStream paramInputStream, int paramInt) throws IOException
/*     */   {
/* 583 */     this.leak.record();
/* 584 */     return super.writeBytes(paramInputStream, paramInt);
/*     */   }
/*     */   
/*     */   public int writeBytes(ScatteringByteChannel paramScatteringByteChannel, int paramInt) throws IOException
/*     */   {
/* 589 */     this.leak.record();
/* 590 */     return super.writeBytes(paramScatteringByteChannel, paramInt);
/*     */   }
/*     */   
/*     */   public ByteBuf writeZero(int paramInt)
/*     */   {
/* 595 */     this.leak.record();
/* 596 */     return super.writeZero(paramInt);
/*     */   }
/*     */   
/*     */   public int indexOf(int paramInt1, int paramInt2, byte paramByte)
/*     */   {
/* 601 */     this.leak.record();
/* 602 */     return super.indexOf(paramInt1, paramInt2, paramByte);
/*     */   }
/*     */   
/*     */   public int bytesBefore(byte paramByte)
/*     */   {
/* 607 */     this.leak.record();
/* 608 */     return super.bytesBefore(paramByte);
/*     */   }
/*     */   
/*     */   public int bytesBefore(int paramInt, byte paramByte)
/*     */   {
/* 613 */     this.leak.record();
/* 614 */     return super.bytesBefore(paramInt, paramByte);
/*     */   }
/*     */   
/*     */   public int bytesBefore(int paramInt1, int paramInt2, byte paramByte)
/*     */   {
/* 619 */     this.leak.record();
/* 620 */     return super.bytesBefore(paramInt1, paramInt2, paramByte);
/*     */   }
/*     */   
/*     */   public int forEachByte(ByteBufProcessor paramByteBufProcessor)
/*     */   {
/* 625 */     this.leak.record();
/* 626 */     return super.forEachByte(paramByteBufProcessor);
/*     */   }
/*     */   
/*     */   public int forEachByte(int paramInt1, int paramInt2, ByteBufProcessor paramByteBufProcessor)
/*     */   {
/* 631 */     this.leak.record();
/* 632 */     return super.forEachByte(paramInt1, paramInt2, paramByteBufProcessor);
/*     */   }
/*     */   
/*     */   public int forEachByteDesc(ByteBufProcessor paramByteBufProcessor)
/*     */   {
/* 637 */     this.leak.record();
/* 638 */     return super.forEachByteDesc(paramByteBufProcessor);
/*     */   }
/*     */   
/*     */   public int forEachByteDesc(int paramInt1, int paramInt2, ByteBufProcessor paramByteBufProcessor)
/*     */   {
/* 643 */     this.leak.record();
/* 644 */     return super.forEachByteDesc(paramInt1, paramInt2, paramByteBufProcessor);
/*     */   }
/*     */   
/*     */   public ByteBuf copy()
/*     */   {
/* 649 */     this.leak.record();
/* 650 */     return super.copy();
/*     */   }
/*     */   
/*     */   public ByteBuf copy(int paramInt1, int paramInt2)
/*     */   {
/* 655 */     this.leak.record();
/* 656 */     return super.copy(paramInt1, paramInt2);
/*     */   }
/*     */   
/*     */   public int nioBufferCount()
/*     */   {
/* 661 */     this.leak.record();
/* 662 */     return super.nioBufferCount();
/*     */   }
/*     */   
/*     */   public ByteBuffer nioBuffer()
/*     */   {
/* 667 */     this.leak.record();
/* 668 */     return super.nioBuffer();
/*     */   }
/*     */   
/*     */   public ByteBuffer nioBuffer(int paramInt1, int paramInt2)
/*     */   {
/* 673 */     this.leak.record();
/* 674 */     return super.nioBuffer(paramInt1, paramInt2);
/*     */   }
/*     */   
/*     */   public ByteBuffer[] nioBuffers()
/*     */   {
/* 679 */     this.leak.record();
/* 680 */     return super.nioBuffers();
/*     */   }
/*     */   
/*     */   public ByteBuffer[] nioBuffers(int paramInt1, int paramInt2)
/*     */   {
/* 685 */     this.leak.record();
/* 686 */     return super.nioBuffers(paramInt1, paramInt2);
/*     */   }
/*     */   
/*     */   public ByteBuffer internalNioBuffer(int paramInt1, int paramInt2)
/*     */   {
/* 691 */     this.leak.record();
/* 692 */     return super.internalNioBuffer(paramInt1, paramInt2);
/*     */   }
/*     */   
/*     */   public String toString(Charset paramCharset)
/*     */   {
/* 697 */     this.leak.record();
/* 698 */     return super.toString(paramCharset);
/*     */   }
/*     */   
/*     */   public String toString(int paramInt1, int paramInt2, Charset paramCharset)
/*     */   {
/* 703 */     this.leak.record();
/* 704 */     return super.toString(paramInt1, paramInt2, paramCharset);
/*     */   }
/*     */   
/*     */   public ByteBuf retain()
/*     */   {
/* 709 */     this.leak.record();
/* 710 */     return super.retain();
/*     */   }
/*     */   
/*     */   public ByteBuf retain(int paramInt)
/*     */   {
/* 715 */     this.leak.record();
/* 716 */     return super.retain(paramInt);
/*     */   }
/*     */   
/*     */   public ByteBuf capacity(int paramInt)
/*     */   {
/* 721 */     this.leak.record();
/* 722 */     return super.capacity(paramInt);
/*     */   }
/*     */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\io\netty\buffer\AdvancedLeakAwareByteBuf.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */
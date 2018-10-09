/*     */ package io.netty.buffer;
/*     */ 
/*     */ import io.netty.util.internal.PlatformDependent;
/*     */ import java.nio.ByteOrder;
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
/*     */ final class UnsafeDirectSwappedByteBuf
/*     */   extends SwappedByteBuf
/*     */ {
/*  27 */   private static final boolean NATIVE_ORDER = ByteOrder.nativeOrder() == ByteOrder.BIG_ENDIAN;
/*     */   private final boolean nativeByteOrder;
/*     */   private final AbstractByteBuf wrapped;
/*     */   
/*     */   UnsafeDirectSwappedByteBuf(AbstractByteBuf paramAbstractByteBuf) {
/*  32 */     super(paramAbstractByteBuf);
/*  33 */     this.wrapped = paramAbstractByteBuf;
/*  34 */     this.nativeByteOrder = (NATIVE_ORDER == (order() == ByteOrder.BIG_ENDIAN));
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   private long addr(int paramInt)
/*     */   {
/*  42 */     return this.wrapped.memoryAddress() + paramInt;
/*     */   }
/*     */   
/*     */   public long getLong(int paramInt)
/*     */   {
/*  47 */     this.wrapped.checkIndex(paramInt, 8);
/*  48 */     long l = PlatformDependent.getLong(addr(paramInt));
/*  49 */     return this.nativeByteOrder ? l : Long.reverseBytes(l);
/*     */   }
/*     */   
/*     */   public float getFloat(int paramInt)
/*     */   {
/*  54 */     return Float.intBitsToFloat(getInt(paramInt));
/*     */   }
/*     */   
/*     */   public double getDouble(int paramInt)
/*     */   {
/*  59 */     return Double.longBitsToDouble(getLong(paramInt));
/*     */   }
/*     */   
/*     */   public char getChar(int paramInt)
/*     */   {
/*  64 */     return (char)getShort(paramInt);
/*     */   }
/*     */   
/*     */   public long getUnsignedInt(int paramInt)
/*     */   {
/*  69 */     return getInt(paramInt) & 0xFFFFFFFF;
/*     */   }
/*     */   
/*     */   public int getInt(int paramInt)
/*     */   {
/*  74 */     this.wrapped.checkIndex(paramInt, 4);
/*  75 */     int i = PlatformDependent.getInt(addr(paramInt));
/*  76 */     return this.nativeByteOrder ? i : Integer.reverseBytes(i);
/*     */   }
/*     */   
/*     */   public int getUnsignedShort(int paramInt)
/*     */   {
/*  81 */     return getShort(paramInt) & 0xFFFF;
/*     */   }
/*     */   
/*     */   public short getShort(int paramInt)
/*     */   {
/*  86 */     this.wrapped.checkIndex(paramInt, 2);
/*  87 */     short s = PlatformDependent.getShort(addr(paramInt));
/*  88 */     return this.nativeByteOrder ? s : Short.reverseBytes(s);
/*     */   }
/*     */   
/*     */   public ByteBuf setShort(int paramInt1, int paramInt2)
/*     */   {
/*  93 */     this.wrapped.checkIndex(paramInt1, 2);
/*  94 */     _setShort(paramInt1, paramInt2);
/*  95 */     return this;
/*     */   }
/*     */   
/*     */   public ByteBuf setInt(int paramInt1, int paramInt2)
/*     */   {
/* 100 */     this.wrapped.checkIndex(paramInt1, 4);
/* 101 */     _setInt(paramInt1, paramInt2);
/* 102 */     return this;
/*     */   }
/*     */   
/*     */   public ByteBuf setLong(int paramInt, long paramLong)
/*     */   {
/* 107 */     this.wrapped.checkIndex(paramInt, 8);
/* 108 */     _setLong(paramInt, paramLong);
/* 109 */     return this;
/*     */   }
/*     */   
/*     */   public ByteBuf setChar(int paramInt1, int paramInt2)
/*     */   {
/* 114 */     setShort(paramInt1, paramInt2);
/* 115 */     return this;
/*     */   }
/*     */   
/*     */   public ByteBuf setFloat(int paramInt, float paramFloat)
/*     */   {
/* 120 */     setInt(paramInt, Float.floatToRawIntBits(paramFloat));
/* 121 */     return this;
/*     */   }
/*     */   
/*     */   public ByteBuf setDouble(int paramInt, double paramDouble)
/*     */   {
/* 126 */     setLong(paramInt, Double.doubleToRawLongBits(paramDouble));
/* 127 */     return this;
/*     */   }
/*     */   
/*     */   public ByteBuf writeShort(int paramInt)
/*     */   {
/* 132 */     this.wrapped.ensureAccessible();
/* 133 */     this.wrapped.ensureWritable(2);
/* 134 */     _setShort(this.wrapped.writerIndex, paramInt);
/* 135 */     this.wrapped.writerIndex += 2;
/* 136 */     return this;
/*     */   }
/*     */   
/*     */   public ByteBuf writeInt(int paramInt)
/*     */   {
/* 141 */     this.wrapped.ensureAccessible();
/* 142 */     this.wrapped.ensureWritable(4);
/* 143 */     _setInt(this.wrapped.writerIndex, paramInt);
/* 144 */     this.wrapped.writerIndex += 4;
/* 145 */     return this;
/*     */   }
/*     */   
/*     */   public ByteBuf writeLong(long paramLong)
/*     */   {
/* 150 */     this.wrapped.ensureAccessible();
/* 151 */     this.wrapped.ensureWritable(8);
/* 152 */     _setLong(this.wrapped.writerIndex, paramLong);
/* 153 */     this.wrapped.writerIndex += 8;
/* 154 */     return this;
/*     */   }
/*     */   
/*     */   public ByteBuf writeChar(int paramInt)
/*     */   {
/* 159 */     writeShort(paramInt);
/* 160 */     return this;
/*     */   }
/*     */   
/*     */   public ByteBuf writeFloat(float paramFloat)
/*     */   {
/* 165 */     writeInt(Float.floatToRawIntBits(paramFloat));
/* 166 */     return this;
/*     */   }
/*     */   
/*     */   public ByteBuf writeDouble(double paramDouble)
/*     */   {
/* 171 */     writeLong(Double.doubleToRawLongBits(paramDouble));
/* 172 */     return this;
/*     */   }
/*     */   
/*     */   private void _setShort(int paramInt1, int paramInt2) {
/* 176 */     PlatformDependent.putShort(addr(paramInt1), this.nativeByteOrder ? (short)paramInt2 : Short.reverseBytes((short)paramInt2));
/*     */   }
/*     */   
/*     */   private void _setInt(int paramInt1, int paramInt2) {
/* 180 */     PlatformDependent.putInt(addr(paramInt1), this.nativeByteOrder ? paramInt2 : Integer.reverseBytes(paramInt2));
/*     */   }
/*     */   
/*     */   private void _setLong(int paramInt, long paramLong) {
/* 184 */     PlatformDependent.putLong(addr(paramInt), this.nativeByteOrder ? paramLong : Long.reverseBytes(paramLong));
/*     */   }
/*     */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\io\netty\buffer\UnsafeDirectSwappedByteBuf.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */
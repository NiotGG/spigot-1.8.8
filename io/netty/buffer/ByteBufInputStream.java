/*     */ package io.netty.buffer;
/*     */ 
/*     */ import java.io.DataInput;
/*     */ import java.io.DataInputStream;
/*     */ import java.io.EOFException;
/*     */ import java.io.IOException;
/*     */ import java.io.InputStream;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class ByteBufInputStream
/*     */   extends InputStream
/*     */   implements DataInput
/*     */ {
/*     */   private final ByteBuf buffer;
/*     */   private final int startIndex;
/*     */   private final int endIndex;
/*     */   
/*     */   public ByteBufInputStream(ByteBuf paramByteBuf)
/*     */   {
/*  52 */     this(paramByteBuf, paramByteBuf.readableBytes());
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
/*     */   public ByteBufInputStream(ByteBuf paramByteBuf, int paramInt)
/*     */   {
/*  65 */     if (paramByteBuf == null) {
/*  66 */       throw new NullPointerException("buffer");
/*     */     }
/*  68 */     if (paramInt < 0) {
/*  69 */       throw new IllegalArgumentException("length: " + paramInt);
/*     */     }
/*  71 */     if (paramInt > paramByteBuf.readableBytes()) {
/*  72 */       throw new IndexOutOfBoundsException("Too many bytes to be read - Needs " + paramInt + ", maximum is " + paramByteBuf.readableBytes());
/*     */     }
/*     */     
/*     */ 
/*  76 */     this.buffer = paramByteBuf;
/*  77 */     this.startIndex = paramByteBuf.readerIndex();
/*  78 */     this.endIndex = (this.startIndex + paramInt);
/*  79 */     paramByteBuf.markReaderIndex();
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public int readBytes()
/*     */   {
/*  86 */     return this.buffer.readerIndex() - this.startIndex;
/*     */   }
/*     */   
/*     */   public int available() throws IOException
/*     */   {
/*  91 */     return this.endIndex - this.buffer.readerIndex();
/*     */   }
/*     */   
/*     */   public void mark(int paramInt)
/*     */   {
/*  96 */     this.buffer.markReaderIndex();
/*     */   }
/*     */   
/*     */   public boolean markSupported()
/*     */   {
/* 101 */     return true;
/*     */   }
/*     */   
/*     */   public int read() throws IOException
/*     */   {
/* 106 */     if (!this.buffer.isReadable()) {
/* 107 */       return -1;
/*     */     }
/* 109 */     return this.buffer.readByte() & 0xFF;
/*     */   }
/*     */   
/*     */   public int read(byte[] paramArrayOfByte, int paramInt1, int paramInt2) throws IOException
/*     */   {
/* 114 */     int i = available();
/* 115 */     if (i == 0) {
/* 116 */       return -1;
/*     */     }
/*     */     
/* 119 */     paramInt2 = Math.min(i, paramInt2);
/* 120 */     this.buffer.readBytes(paramArrayOfByte, paramInt1, paramInt2);
/* 121 */     return paramInt2;
/*     */   }
/*     */   
/*     */   public void reset() throws IOException
/*     */   {
/* 126 */     this.buffer.resetReaderIndex();
/*     */   }
/*     */   
/*     */   public long skip(long paramLong) throws IOException
/*     */   {
/* 131 */     if (paramLong > 2147483647L) {
/* 132 */       return skipBytes(Integer.MAX_VALUE);
/*     */     }
/* 134 */     return skipBytes((int)paramLong);
/*     */   }
/*     */   
/*     */   public boolean readBoolean()
/*     */     throws IOException
/*     */   {
/* 140 */     checkAvailable(1);
/* 141 */     return read() != 0;
/*     */   }
/*     */   
/*     */   public byte readByte() throws IOException
/*     */   {
/* 146 */     if (!this.buffer.isReadable()) {
/* 147 */       throw new EOFException();
/*     */     }
/* 149 */     return this.buffer.readByte();
/*     */   }
/*     */   
/*     */   public char readChar() throws IOException
/*     */   {
/* 154 */     return (char)readShort();
/*     */   }
/*     */   
/*     */   public double readDouble() throws IOException
/*     */   {
/* 159 */     return Double.longBitsToDouble(readLong());
/*     */   }
/*     */   
/*     */   public float readFloat() throws IOException
/*     */   {
/* 164 */     return Float.intBitsToFloat(readInt());
/*     */   }
/*     */   
/*     */   public void readFully(byte[] paramArrayOfByte) throws IOException
/*     */   {
/* 169 */     readFully(paramArrayOfByte, 0, paramArrayOfByte.length);
/*     */   }
/*     */   
/*     */   public void readFully(byte[] paramArrayOfByte, int paramInt1, int paramInt2) throws IOException
/*     */   {
/* 174 */     checkAvailable(paramInt2);
/* 175 */     this.buffer.readBytes(paramArrayOfByte, paramInt1, paramInt2);
/*     */   }
/*     */   
/*     */   public int readInt() throws IOException
/*     */   {
/* 180 */     checkAvailable(4);
/* 181 */     return this.buffer.readInt();
/*     */   }
/*     */   
/* 184 */   private final StringBuilder lineBuf = new StringBuilder();
/*     */   
/*     */   public String readLine() throws IOException
/*     */   {
/* 188 */     this.lineBuf.setLength(0);
/*     */     for (;;)
/*     */     {
/* 191 */       if (!this.buffer.isReadable()) {
/* 192 */         return this.lineBuf.length() > 0 ? this.lineBuf.toString() : null;
/*     */       }
/*     */       
/* 195 */       int i = this.buffer.readUnsignedByte();
/* 196 */       switch (i)
/*     */       {
/*     */       case 10: 
/*     */         break;
/*     */       case 13: 
/* 201 */         if ((!this.buffer.isReadable()) || ((char)this.buffer.getUnsignedByte(this.buffer.readerIndex()) != '\n')) break;
/* 202 */         this.buffer.skipBytes(1); break;
/*     */       
/*     */ 
/*     */ 
/*     */       default: 
/* 207 */         this.lineBuf.append((char)i);
/*     */       }
/*     */       
/*     */     }
/* 211 */     return this.lineBuf.toString();
/*     */   }
/*     */   
/*     */   public long readLong() throws IOException
/*     */   {
/* 216 */     checkAvailable(8);
/* 217 */     return this.buffer.readLong();
/*     */   }
/*     */   
/*     */   public short readShort() throws IOException
/*     */   {
/* 222 */     checkAvailable(2);
/* 223 */     return this.buffer.readShort();
/*     */   }
/*     */   
/*     */   public String readUTF() throws IOException
/*     */   {
/* 228 */     return DataInputStream.readUTF(this);
/*     */   }
/*     */   
/*     */   public int readUnsignedByte() throws IOException
/*     */   {
/* 233 */     return readByte() & 0xFF;
/*     */   }
/*     */   
/*     */   public int readUnsignedShort() throws IOException
/*     */   {
/* 238 */     return readShort() & 0xFFFF;
/*     */   }
/*     */   
/*     */   public int skipBytes(int paramInt) throws IOException
/*     */   {
/* 243 */     int i = Math.min(available(), paramInt);
/* 244 */     this.buffer.skipBytes(i);
/* 245 */     return i;
/*     */   }
/*     */   
/*     */   private void checkAvailable(int paramInt) throws IOException {
/* 249 */     if (paramInt < 0) {
/* 250 */       throw new IndexOutOfBoundsException("fieldSize cannot be a negative number");
/*     */     }
/* 252 */     if (paramInt > available()) {
/* 253 */       throw new EOFException("fieldSize is too long! Length is " + paramInt + ", but maximum is " + available());
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\io\netty\buffer\ByteBufInputStream.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */
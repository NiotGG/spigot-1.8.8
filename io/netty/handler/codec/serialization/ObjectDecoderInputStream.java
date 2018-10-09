/*     */ package io.netty.handler.codec.serialization;
/*     */ 
/*     */ import java.io.DataInputStream;
/*     */ import java.io.IOException;
/*     */ import java.io.InputStream;
/*     */ import java.io.ObjectInput;
/*     */ import java.io.StreamCorruptedException;
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
/*     */ public class ObjectDecoderInputStream
/*     */   extends InputStream
/*     */   implements ObjectInput
/*     */ {
/*     */   private final DataInputStream in;
/*     */   private final int maxObjectSize;
/*     */   private final ClassResolver classResolver;
/*     */   
/*     */   public ObjectDecoderInputStream(InputStream paramInputStream)
/*     */   {
/*  44 */     this(paramInputStream, null);
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
/*     */ 
/*     */   public ObjectDecoderInputStream(InputStream paramInputStream, ClassLoader paramClassLoader)
/*     */   {
/*  58 */     this(paramInputStream, paramClassLoader, 1048576);
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
/*     */ 
/*     */ 
/*     */   public ObjectDecoderInputStream(InputStream paramInputStream, int paramInt)
/*     */   {
/*  73 */     this(paramInputStream, null, paramInt);
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public ObjectDecoderInputStream(InputStream paramInputStream, ClassLoader paramClassLoader, int paramInt)
/*     */   {
/*  91 */     if (paramInputStream == null) {
/*  92 */       throw new NullPointerException("in");
/*     */     }
/*  94 */     if (paramInt <= 0) {
/*  95 */       throw new IllegalArgumentException("maxObjectSize: " + paramInt);
/*     */     }
/*  97 */     if ((paramInputStream instanceof DataInputStream)) {
/*  98 */       this.in = ((DataInputStream)paramInputStream);
/*     */     } else {
/* 100 */       this.in = new DataInputStream(paramInputStream);
/*     */     }
/* 102 */     this.classResolver = ClassResolvers.weakCachingResolver(paramClassLoader);
/* 103 */     this.maxObjectSize = paramInt;
/*     */   }
/*     */   
/*     */   public Object readObject() throws ClassNotFoundException, IOException
/*     */   {
/* 108 */     int i = readInt();
/* 109 */     if (i <= 0) {
/* 110 */       throw new StreamCorruptedException("invalid data length: " + i);
/*     */     }
/* 112 */     if (i > this.maxObjectSize) {
/* 113 */       throw new StreamCorruptedException("data length too big: " + i + " (max: " + this.maxObjectSize + ')');
/*     */     }
/*     */     
/*     */ 
/* 117 */     return new CompactObjectInputStream(this.in, this.classResolver).readObject();
/*     */   }
/*     */   
/*     */   public int available() throws IOException
/*     */   {
/* 122 */     return this.in.available();
/*     */   }
/*     */   
/*     */   public void close() throws IOException
/*     */   {
/* 127 */     this.in.close();
/*     */   }
/*     */   
/*     */   public void mark(int paramInt)
/*     */   {
/* 132 */     this.in.mark(paramInt);
/*     */   }
/*     */   
/*     */   public boolean markSupported()
/*     */   {
/* 137 */     return this.in.markSupported();
/*     */   }
/*     */   
/*     */   public int read() throws IOException
/*     */   {
/* 142 */     return this.in.read();
/*     */   }
/*     */   
/*     */   public final int read(byte[] paramArrayOfByte, int paramInt1, int paramInt2) throws IOException
/*     */   {
/* 147 */     return this.in.read(paramArrayOfByte, paramInt1, paramInt2);
/*     */   }
/*     */   
/*     */   public final int read(byte[] paramArrayOfByte) throws IOException
/*     */   {
/* 152 */     return this.in.read(paramArrayOfByte);
/*     */   }
/*     */   
/*     */   public final boolean readBoolean() throws IOException
/*     */   {
/* 157 */     return this.in.readBoolean();
/*     */   }
/*     */   
/*     */   public final byte readByte() throws IOException
/*     */   {
/* 162 */     return this.in.readByte();
/*     */   }
/*     */   
/*     */   public final char readChar() throws IOException
/*     */   {
/* 167 */     return this.in.readChar();
/*     */   }
/*     */   
/*     */   public final double readDouble() throws IOException
/*     */   {
/* 172 */     return this.in.readDouble();
/*     */   }
/*     */   
/*     */   public final float readFloat() throws IOException
/*     */   {
/* 177 */     return this.in.readFloat();
/*     */   }
/*     */   
/*     */   public final void readFully(byte[] paramArrayOfByte, int paramInt1, int paramInt2) throws IOException
/*     */   {
/* 182 */     this.in.readFully(paramArrayOfByte, paramInt1, paramInt2);
/*     */   }
/*     */   
/*     */   public final void readFully(byte[] paramArrayOfByte) throws IOException
/*     */   {
/* 187 */     this.in.readFully(paramArrayOfByte);
/*     */   }
/*     */   
/*     */   public final int readInt() throws IOException
/*     */   {
/* 192 */     return this.in.readInt();
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   @Deprecated
/*     */   public final String readLine()
/*     */     throws IOException
/*     */   {
/* 201 */     return this.in.readLine();
/*     */   }
/*     */   
/*     */   public final long readLong() throws IOException
/*     */   {
/* 206 */     return this.in.readLong();
/*     */   }
/*     */   
/*     */   public final short readShort() throws IOException
/*     */   {
/* 211 */     return this.in.readShort();
/*     */   }
/*     */   
/*     */   public final int readUnsignedByte() throws IOException
/*     */   {
/* 216 */     return this.in.readUnsignedByte();
/*     */   }
/*     */   
/*     */   public final int readUnsignedShort() throws IOException
/*     */   {
/* 221 */     return this.in.readUnsignedShort();
/*     */   }
/*     */   
/*     */   public final String readUTF() throws IOException
/*     */   {
/* 226 */     return this.in.readUTF();
/*     */   }
/*     */   
/*     */   public void reset() throws IOException
/*     */   {
/* 231 */     this.in.reset();
/*     */   }
/*     */   
/*     */   public long skip(long paramLong) throws IOException
/*     */   {
/* 236 */     return this.in.skip(paramLong);
/*     */   }
/*     */   
/*     */   public final int skipBytes(int paramInt) throws IOException
/*     */   {
/* 241 */     return this.in.skipBytes(paramInt);
/*     */   }
/*     */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\io\netty\handler\codec\serialization\ObjectDecoderInputStream.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */
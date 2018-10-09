/*     */ package org.apache.commons.io.output;
/*     */ 
/*     */ import java.io.ByteArrayInputStream;
/*     */ import java.io.IOException;
/*     */ import java.io.InputStream;
/*     */ import java.io.OutputStream;
/*     */ import java.io.SequenceInputStream;
/*     */ import java.io.UnsupportedEncodingException;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Collections;
/*     */ import java.util.List;
/*     */ import org.apache.commons.io.input.ClosedInputStream;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class ByteArrayOutputStream
/*     */   extends OutputStream
/*     */ {
/*  57 */   private static final byte[] EMPTY_BYTE_ARRAY = new byte[0];
/*     */   
/*     */ 
/*  60 */   private final List<byte[]> buffers = new ArrayList();
/*     */   
/*     */ 
/*     */   private int currentBufferIndex;
/*     */   
/*     */ 
/*     */   private int filledBufferSum;
/*     */   
/*     */   private byte[] currentBuffer;
/*     */   
/*     */   private int count;
/*     */   
/*     */ 
/*     */   public ByteArrayOutputStream()
/*     */   {
/*  75 */     this(1024);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public ByteArrayOutputStream(int paramInt)
/*     */   {
/*  86 */     if (paramInt < 0) {
/*  87 */       throw new IllegalArgumentException("Negative initial size: " + paramInt);
/*     */     }
/*     */     
/*  90 */     synchronized (this) {
/*  91 */       needNewBuffer(paramInt);
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   private void needNewBuffer(int paramInt)
/*     */   {
/* 102 */     if (this.currentBufferIndex < this.buffers.size() - 1)
/*     */     {
/* 104 */       this.filledBufferSum += this.currentBuffer.length;
/*     */       
/* 106 */       this.currentBufferIndex += 1;
/* 107 */       this.currentBuffer = ((byte[])this.buffers.get(this.currentBufferIndex));
/*     */     }
/*     */     else {
/*     */       int i;
/* 111 */       if (this.currentBuffer == null) {
/* 112 */         i = paramInt;
/* 113 */         this.filledBufferSum = 0;
/*     */       } else {
/* 115 */         i = Math.max(this.currentBuffer.length << 1, paramInt - this.filledBufferSum);
/*     */         
/*     */ 
/* 118 */         this.filledBufferSum += this.currentBuffer.length;
/*     */       }
/*     */       
/* 121 */       this.currentBufferIndex += 1;
/* 122 */       this.currentBuffer = new byte[i];
/* 123 */       this.buffers.add(this.currentBuffer);
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public void write(byte[] paramArrayOfByte, int paramInt1, int paramInt2)
/*     */   {
/* 135 */     if ((paramInt1 < 0) || (paramInt1 > paramArrayOfByte.length) || (paramInt2 < 0) || (paramInt1 + paramInt2 > paramArrayOfByte.length) || (paramInt1 + paramInt2 < 0))
/*     */     {
/*     */ 
/*     */ 
/*     */ 
/* 140 */       throw new IndexOutOfBoundsException(); }
/* 141 */     if (paramInt2 == 0) {
/* 142 */       return;
/*     */     }
/* 144 */     synchronized (this) {
/* 145 */       int i = this.count + paramInt2;
/* 146 */       int j = paramInt2;
/* 147 */       int k = this.count - this.filledBufferSum;
/* 148 */       while (j > 0) {
/* 149 */         int m = Math.min(j, this.currentBuffer.length - k);
/* 150 */         System.arraycopy(paramArrayOfByte, paramInt1 + paramInt2 - j, this.currentBuffer, k, m);
/* 151 */         j -= m;
/* 152 */         if (j > 0) {
/* 153 */           needNewBuffer(i);
/* 154 */           k = 0;
/*     */         }
/*     */       }
/* 157 */       this.count = i;
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public synchronized void write(int paramInt)
/*     */   {
/* 167 */     int i = this.count - this.filledBufferSum;
/* 168 */     if (i == this.currentBuffer.length) {
/* 169 */       needNewBuffer(this.count + 1);
/* 170 */       i = 0;
/*     */     }
/* 172 */     this.currentBuffer[i] = ((byte)paramInt);
/* 173 */     this.count += 1;
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
/*     */   public synchronized int write(InputStream paramInputStream)
/*     */     throws IOException
/*     */   {
/* 188 */     int i = 0;
/* 189 */     int j = this.count - this.filledBufferSum;
/* 190 */     int k = paramInputStream.read(this.currentBuffer, j, this.currentBuffer.length - j);
/* 191 */     while (k != -1) {
/* 192 */       i += k;
/* 193 */       j += k;
/* 194 */       this.count += k;
/* 195 */       if (j == this.currentBuffer.length) {
/* 196 */         needNewBuffer(this.currentBuffer.length);
/* 197 */         j = 0;
/*     */       }
/* 199 */       k = paramInputStream.read(this.currentBuffer, j, this.currentBuffer.length - j);
/*     */     }
/* 201 */     return i;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public synchronized int size()
/*     */   {
/* 209 */     return this.count;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public void close()
/*     */     throws IOException
/*     */   {}
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public synchronized void reset()
/*     */   {
/* 229 */     this.count = 0;
/* 230 */     this.filledBufferSum = 0;
/* 231 */     this.currentBufferIndex = 0;
/* 232 */     this.currentBuffer = ((byte[])this.buffers.get(this.currentBufferIndex));
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public synchronized void writeTo(OutputStream paramOutputStream)
/*     */     throws IOException
/*     */   {
/* 244 */     int i = this.count;
/* 245 */     for (byte[] arrayOfByte : this.buffers) {
/* 246 */       int j = Math.min(arrayOfByte.length, i);
/* 247 */       paramOutputStream.write(arrayOfByte, 0, j);
/* 248 */       i -= j;
/* 249 */       if (i == 0) {
/*     */         break;
/*     */       }
/*     */     }
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public static InputStream toBufferedInputStream(InputStream paramInputStream)
/*     */     throws IOException
/*     */   {
/* 278 */     ByteArrayOutputStream localByteArrayOutputStream = new ByteArrayOutputStream();
/* 279 */     localByteArrayOutputStream.write(paramInputStream);
/* 280 */     return localByteArrayOutputStream.toBufferedInputStream();
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
/*     */   private InputStream toBufferedInputStream()
/*     */   {
/* 294 */     int i = this.count;
/* 295 */     if (i == 0) {
/* 296 */       return new ClosedInputStream();
/*     */     }
/* 298 */     ArrayList localArrayList = new ArrayList(this.buffers.size());
/* 299 */     for (byte[] arrayOfByte : this.buffers) {
/* 300 */       int j = Math.min(arrayOfByte.length, i);
/* 301 */       localArrayList.add(new ByteArrayInputStream(arrayOfByte, 0, j));
/* 302 */       i -= j;
/* 303 */       if (i == 0) {
/*     */         break;
/*     */       }
/*     */     }
/* 307 */     return new SequenceInputStream(Collections.enumeration(localArrayList));
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public synchronized byte[] toByteArray()
/*     */   {
/* 318 */     int i = this.count;
/* 319 */     if (i == 0) {
/* 320 */       return EMPTY_BYTE_ARRAY;
/*     */     }
/* 322 */     byte[] arrayOfByte1 = new byte[i];
/* 323 */     int j = 0;
/* 324 */     for (byte[] arrayOfByte2 : this.buffers) {
/* 325 */       int k = Math.min(arrayOfByte2.length, i);
/* 326 */       System.arraycopy(arrayOfByte2, 0, arrayOfByte1, j, k);
/* 327 */       j += k;
/* 328 */       i -= k;
/* 329 */       if (i == 0) {
/*     */         break;
/*     */       }
/*     */     }
/* 333 */     return arrayOfByte1;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public String toString()
/*     */   {
/* 343 */     return new String(toByteArray());
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public String toString(String paramString)
/*     */     throws UnsupportedEncodingException
/*     */   {
/* 356 */     return new String(toByteArray(), paramString);
/*     */   }
/*     */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\org\apache\commons\io\output\ByteArrayOutputStream.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */
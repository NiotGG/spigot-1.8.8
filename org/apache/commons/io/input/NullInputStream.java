/*     */ package org.apache.commons.io.input;
/*     */ 
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
/*     */ public class NullInputStream
/*     */   extends InputStream
/*     */ {
/*     */   private final long size;
/*     */   private long position;
/*  67 */   private long mark = -1L;
/*     */   
/*     */   private long readlimit;
/*     */   
/*     */   private boolean eof;
/*     */   
/*     */   private final boolean throwEofException;
/*     */   
/*     */   private final boolean markSupported;
/*     */   
/*     */ 
/*     */   public NullInputStream(long paramLong)
/*     */   {
/*  80 */     this(paramLong, true, false);
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
/*     */   public NullInputStream(long paramLong, boolean paramBoolean1, boolean paramBoolean2)
/*     */   {
/*  95 */     this.size = paramLong;
/*  96 */     this.markSupported = paramBoolean1;
/*  97 */     this.throwEofException = paramBoolean2;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public long getPosition()
/*     */   {
/* 106 */     return this.position;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public long getSize()
/*     */   {
/* 115 */     return this.size;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public int available()
/*     */   {
/* 125 */     long l = this.size - this.position;
/* 126 */     if (l <= 0L)
/* 127 */       return 0;
/* 128 */     if (l > 2147483647L) {
/* 129 */       return Integer.MAX_VALUE;
/*     */     }
/* 131 */     return (int)l;
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
/*     */   {
/* 143 */     this.eof = false;
/* 144 */     this.position = 0L;
/* 145 */     this.mark = -1L;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public synchronized void mark(int paramInt)
/*     */   {
/* 157 */     if (!this.markSupported) {
/* 158 */       throw new UnsupportedOperationException("Mark not supported");
/*     */     }
/* 160 */     this.mark = this.position;
/* 161 */     this.readlimit = paramInt;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public boolean markSupported()
/*     */   {
/* 171 */     return this.markSupported;
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
/*     */   public int read()
/*     */     throws IOException
/*     */   {
/* 186 */     if (this.eof) {
/* 187 */       throw new IOException("Read after end of file");
/*     */     }
/* 189 */     if (this.position == this.size) {
/* 190 */       return doEndOfFile();
/*     */     }
/* 192 */     this.position += 1L;
/* 193 */     return processByte();
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
/*     */   public int read(byte[] paramArrayOfByte)
/*     */     throws IOException
/*     */   {
/* 209 */     return read(paramArrayOfByte, 0, paramArrayOfByte.length);
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
/*     */   public int read(byte[] paramArrayOfByte, int paramInt1, int paramInt2)
/*     */     throws IOException
/*     */   {
/* 227 */     if (this.eof) {
/* 228 */       throw new IOException("Read after end of file");
/*     */     }
/* 230 */     if (this.position == this.size) {
/* 231 */       return doEndOfFile();
/*     */     }
/* 233 */     this.position += paramInt2;
/* 234 */     int i = paramInt2;
/* 235 */     if (this.position > this.size) {
/* 236 */       i = paramInt2 - (int)(this.position - this.size);
/* 237 */       this.position = this.size;
/*     */     }
/* 239 */     processBytes(paramArrayOfByte, paramInt1, i);
/* 240 */     return i;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public synchronized void reset()
/*     */     throws IOException
/*     */   {
/* 253 */     if (!this.markSupported) {
/* 254 */       throw new UnsupportedOperationException("Mark not supported");
/*     */     }
/* 256 */     if (this.mark < 0L) {
/* 257 */       throw new IOException("No position has been marked");
/*     */     }
/* 259 */     if (this.position > this.mark + this.readlimit) {
/* 260 */       throw new IOException("Marked position [" + this.mark + "] is no longer valid - passed the read limit [" + this.readlimit + "]");
/*     */     }
/*     */     
/*     */ 
/* 264 */     this.position = this.mark;
/* 265 */     this.eof = false;
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
/*     */   public long skip(long paramLong)
/*     */     throws IOException
/*     */   {
/* 281 */     if (this.eof) {
/* 282 */       throw new IOException("Skip after end of file");
/*     */     }
/* 284 */     if (this.position == this.size) {
/* 285 */       return doEndOfFile();
/*     */     }
/* 287 */     this.position += paramLong;
/* 288 */     long l = paramLong;
/* 289 */     if (this.position > this.size) {
/* 290 */       l = paramLong - (this.position - this.size);
/* 291 */       this.position = this.size;
/*     */     }
/* 293 */     return l;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   protected int processByte()
/*     */   {
/* 305 */     return 0;
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
/*     */   protected void processBytes(byte[] paramArrayOfByte, int paramInt1, int paramInt2) {}
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
/*     */   private int doEndOfFile()
/*     */     throws EOFException
/*     */   {
/* 331 */     this.eof = true;
/* 332 */     if (this.throwEofException) {
/* 333 */       throw new EOFException();
/*     */     }
/* 335 */     return -1;
/*     */   }
/*     */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\org\apache\commons\io\input\NullInputStream.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */
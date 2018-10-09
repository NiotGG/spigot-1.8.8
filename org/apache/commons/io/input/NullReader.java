/*     */ package org.apache.commons.io.input;
/*     */ 
/*     */ import java.io.EOFException;
/*     */ import java.io.IOException;
/*     */ import java.io.Reader;
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
/*     */ public class NullReader
/*     */   extends Reader
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
/*     */   public NullReader(long paramLong)
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
/*     */   public NullReader(long paramLong, boolean paramBoolean1, boolean paramBoolean2)
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
/*     */   public void close()
/*     */     throws IOException
/*     */   {
/* 126 */     this.eof = false;
/* 127 */     this.position = 0L;
/* 128 */     this.mark = -1L;
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
/* 140 */     if (!this.markSupported) {
/* 141 */       throw new UnsupportedOperationException("Mark not supported");
/*     */     }
/* 143 */     this.mark = this.position;
/* 144 */     this.readlimit = paramInt;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public boolean markSupported()
/*     */   {
/* 154 */     return this.markSupported;
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
/* 169 */     if (this.eof) {
/* 170 */       throw new IOException("Read after end of file");
/*     */     }
/* 172 */     if (this.position == this.size) {
/* 173 */       return doEndOfFile();
/*     */     }
/* 175 */     this.position += 1L;
/* 176 */     return processChar();
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
/*     */   public int read(char[] paramArrayOfChar)
/*     */     throws IOException
/*     */   {
/* 192 */     return read(paramArrayOfChar, 0, paramArrayOfChar.length);
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
/*     */   public int read(char[] paramArrayOfChar, int paramInt1, int paramInt2)
/*     */     throws IOException
/*     */   {
/* 210 */     if (this.eof) {
/* 211 */       throw new IOException("Read after end of file");
/*     */     }
/* 213 */     if (this.position == this.size) {
/* 214 */       return doEndOfFile();
/*     */     }
/* 216 */     this.position += paramInt2;
/* 217 */     int i = paramInt2;
/* 218 */     if (this.position > this.size) {
/* 219 */       i = paramInt2 - (int)(this.position - this.size);
/* 220 */       this.position = this.size;
/*     */     }
/* 222 */     processChars(paramArrayOfChar, paramInt1, i);
/* 223 */     return i;
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
/* 236 */     if (!this.markSupported) {
/* 237 */       throw new UnsupportedOperationException("Mark not supported");
/*     */     }
/* 239 */     if (this.mark < 0L) {
/* 240 */       throw new IOException("No position has been marked");
/*     */     }
/* 242 */     if (this.position > this.mark + this.readlimit) {
/* 243 */       throw new IOException("Marked position [" + this.mark + "] is no longer valid - passed the read limit [" + this.readlimit + "]");
/*     */     }
/*     */     
/*     */ 
/* 247 */     this.position = this.mark;
/* 248 */     this.eof = false;
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
/* 264 */     if (this.eof) {
/* 265 */       throw new IOException("Skip after end of file");
/*     */     }
/* 267 */     if (this.position == this.size) {
/* 268 */       return doEndOfFile();
/*     */     }
/* 270 */     this.position += paramLong;
/* 271 */     long l = paramLong;
/* 272 */     if (this.position > this.size) {
/* 273 */       l = paramLong - (this.position - this.size);
/* 274 */       this.position = this.size;
/*     */     }
/* 276 */     return l;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   protected int processChar()
/*     */   {
/* 288 */     return 0;
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
/*     */   protected void processChars(char[] paramArrayOfChar, int paramInt1, int paramInt2) {}
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
/* 314 */     this.eof = true;
/* 315 */     if (this.throwEofException) {
/* 316 */       throw new EOFException();
/*     */     }
/* 318 */     return -1;
/*     */   }
/*     */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\org\apache\commons\io\input\NullReader.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */
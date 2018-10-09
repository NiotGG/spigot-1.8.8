/*     */ package org.apache.commons.io.input;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import java.io.InputStream;
/*     */ import java.nio.ByteBuffer;
/*     */ import java.nio.CharBuffer;
/*     */ import java.nio.charset.CharacterCodingException;
/*     */ import java.nio.charset.Charset;
/*     */ import java.nio.charset.CharsetEncoder;
/*     */ import java.nio.charset.CoderResult;
/*     */ import java.nio.charset.CodingErrorAction;
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
/*     */ public class CharSequenceInputStream
/*     */   extends InputStream
/*     */ {
/*     */   private final CharsetEncoder encoder;
/*     */   private final CharBuffer cbuf;
/*     */   private final ByteBuffer bbuf;
/*     */   private int mark;
/*     */   
/*     */   public CharSequenceInputStream(CharSequence paramCharSequence, Charset paramCharset, int paramInt)
/*     */   {
/*  55 */     this.encoder = paramCharset.newEncoder().onMalformedInput(CodingErrorAction.REPLACE).onUnmappableCharacter(CodingErrorAction.REPLACE);
/*     */     
/*     */ 
/*  58 */     this.bbuf = ByteBuffer.allocate(paramInt);
/*  59 */     this.bbuf.flip();
/*  60 */     this.cbuf = CharBuffer.wrap(paramCharSequence);
/*  61 */     this.mark = -1;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public CharSequenceInputStream(CharSequence paramCharSequence, String paramString, int paramInt)
/*     */   {
/*  72 */     this(paramCharSequence, Charset.forName(paramString), paramInt);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public CharSequenceInputStream(CharSequence paramCharSequence, Charset paramCharset)
/*     */   {
/*  83 */     this(paramCharSequence, paramCharset, 2048);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public CharSequenceInputStream(CharSequence paramCharSequence, String paramString)
/*     */   {
/*  94 */     this(paramCharSequence, paramString, 2048);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   private void fillBuffer()
/*     */     throws CharacterCodingException
/*     */   {
/* 104 */     this.bbuf.compact();
/* 105 */     CoderResult localCoderResult = this.encoder.encode(this.cbuf, this.bbuf, true);
/* 106 */     if (localCoderResult.isError()) {
/* 107 */       localCoderResult.throwException();
/*     */     }
/* 109 */     this.bbuf.flip();
/*     */   }
/*     */   
/*     */   public int read(byte[] paramArrayOfByte, int paramInt1, int paramInt2) throws IOException
/*     */   {
/* 114 */     if (paramArrayOfByte == null) {
/* 115 */       throw new NullPointerException("Byte array is null");
/*     */     }
/* 117 */     if ((paramInt2 < 0) || (paramInt1 + paramInt2 > paramArrayOfByte.length)) {
/* 118 */       throw new IndexOutOfBoundsException("Array Size=" + paramArrayOfByte.length + ", offset=" + paramInt1 + ", length=" + paramInt2);
/*     */     }
/*     */     
/* 121 */     if (paramInt2 == 0) {
/* 122 */       return 0;
/*     */     }
/* 124 */     if ((!this.bbuf.hasRemaining()) && (!this.cbuf.hasRemaining())) {
/* 125 */       return -1;
/*     */     }
/* 127 */     int i = 0;
/* 128 */     while (paramInt2 > 0) {
/* 129 */       if (this.bbuf.hasRemaining()) {
/* 130 */         int j = Math.min(this.bbuf.remaining(), paramInt2);
/* 131 */         this.bbuf.get(paramArrayOfByte, paramInt1, j);
/* 132 */         paramInt1 += j;
/* 133 */         paramInt2 -= j;
/* 134 */         i += j;
/*     */       } else {
/* 136 */         fillBuffer();
/* 137 */         if ((!this.bbuf.hasRemaining()) && (!this.cbuf.hasRemaining())) {
/*     */           break;
/*     */         }
/*     */       }
/*     */     }
/* 142 */     return (i == 0) && (!this.cbuf.hasRemaining()) ? -1 : i;
/*     */   }
/*     */   
/*     */   public int read() throws IOException
/*     */   {
/*     */     do {
/* 148 */       if (this.bbuf.hasRemaining()) {
/* 149 */         return this.bbuf.get() & 0xFF;
/*     */       }
/* 151 */       fillBuffer();
/* 152 */     } while ((this.bbuf.hasRemaining()) || (this.cbuf.hasRemaining()));
/* 153 */     return -1;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public int read(byte[] paramArrayOfByte)
/*     */     throws IOException
/*     */   {
/* 161 */     return read(paramArrayOfByte, 0, paramArrayOfByte.length);
/*     */   }
/*     */   
/*     */   public long skip(long paramLong) throws IOException
/*     */   {
/* 166 */     int i = 0;
/* 167 */     while ((paramLong > 0L) && (this.cbuf.hasRemaining())) {
/* 168 */       this.cbuf.get();
/* 169 */       paramLong -= 1L;
/* 170 */       i++;
/*     */     }
/* 172 */     return i;
/*     */   }
/*     */   
/*     */   public int available() throws IOException
/*     */   {
/* 177 */     return this.cbuf.remaining();
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public void close()
/*     */     throws IOException
/*     */   {}
/*     */   
/*     */ 
/*     */ 
/*     */   public synchronized void mark(int paramInt)
/*     */   {
/* 190 */     this.mark = this.cbuf.position();
/*     */   }
/*     */   
/*     */   public synchronized void reset() throws IOException
/*     */   {
/* 195 */     if (this.mark != -1) {
/* 196 */       this.cbuf.position(this.mark);
/* 197 */       this.mark = -1;
/*     */     }
/*     */   }
/*     */   
/*     */   public boolean markSupported()
/*     */   {
/* 203 */     return true;
/*     */   }
/*     */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\org\apache\commons\io\input\CharSequenceInputStream.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */
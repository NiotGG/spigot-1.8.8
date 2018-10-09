/*     */ package org.apache.commons.io.input;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import java.io.InputStream;
/*     */ import java.io.Reader;
/*     */ import java.nio.ByteBuffer;
/*     */ import java.nio.CharBuffer;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class ReaderInputStream
/*     */   extends InputStream
/*     */ {
/*     */   private static final int DEFAULT_BUFFER_SIZE = 1024;
/*     */   private final Reader reader;
/*     */   private final CharsetEncoder encoder;
/*     */   private final CharBuffer encoderIn;
/*     */   private final ByteBuffer encoderOut;
/*     */   private CoderResult lastCoderResult;
/*     */   private boolean endOfInput;
/*     */   
/*     */   public ReaderInputStream(Reader paramReader, CharsetEncoder paramCharsetEncoder)
/*     */   {
/* 107 */     this(paramReader, paramCharsetEncoder, 1024);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public ReaderInputStream(Reader paramReader, CharsetEncoder paramCharsetEncoder, int paramInt)
/*     */   {
/* 119 */     this.reader = paramReader;
/* 120 */     this.encoder = paramCharsetEncoder;
/* 121 */     this.encoderIn = CharBuffer.allocate(paramInt);
/* 122 */     this.encoderIn.flip();
/* 123 */     this.encoderOut = ByteBuffer.allocate(128);
/* 124 */     this.encoderOut.flip();
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public ReaderInputStream(Reader paramReader, Charset paramCharset, int paramInt)
/*     */   {
/* 135 */     this(paramReader, paramCharset.newEncoder().onMalformedInput(CodingErrorAction.REPLACE).onUnmappableCharacter(CodingErrorAction.REPLACE), paramInt);
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
/*     */   public ReaderInputStream(Reader paramReader, Charset paramCharset)
/*     */   {
/* 150 */     this(paramReader, paramCharset, 1024);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public ReaderInputStream(Reader paramReader, String paramString, int paramInt)
/*     */   {
/* 161 */     this(paramReader, Charset.forName(paramString), paramInt);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public ReaderInputStream(Reader paramReader, String paramString)
/*     */   {
/* 172 */     this(paramReader, paramString, 1024);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public ReaderInputStream(Reader paramReader)
/*     */   {
/* 182 */     this(paramReader, Charset.defaultCharset());
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   private void fillBuffer()
/*     */     throws IOException
/*     */   {
/* 192 */     if ((!this.endOfInput) && ((this.lastCoderResult == null) || (this.lastCoderResult.isUnderflow()))) {
/* 193 */       this.encoderIn.compact();
/* 194 */       int i = this.encoderIn.position();
/*     */       
/*     */ 
/*     */ 
/* 198 */       int j = this.reader.read(this.encoderIn.array(), i, this.encoderIn.remaining());
/* 199 */       if (j == -1) {
/* 200 */         this.endOfInput = true;
/*     */       } else {
/* 202 */         this.encoderIn.position(i + j);
/*     */       }
/* 204 */       this.encoderIn.flip();
/*     */     }
/* 206 */     this.encoderOut.compact();
/* 207 */     this.lastCoderResult = this.encoder.encode(this.encoderIn, this.encoderOut, this.endOfInput);
/* 208 */     this.encoderOut.flip();
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
/*     */   public int read(byte[] paramArrayOfByte, int paramInt1, int paramInt2)
/*     */     throws IOException
/*     */   {
/* 223 */     if (paramArrayOfByte == null) {
/* 224 */       throw new NullPointerException("Byte array must not be null");
/*     */     }
/* 226 */     if ((paramInt2 < 0) || (paramInt1 < 0) || (paramInt1 + paramInt2 > paramArrayOfByte.length)) {
/* 227 */       throw new IndexOutOfBoundsException("Array Size=" + paramArrayOfByte.length + ", offset=" + paramInt1 + ", length=" + paramInt2);
/*     */     }
/*     */     
/* 230 */     int i = 0;
/* 231 */     if (paramInt2 == 0) {
/* 232 */       return 0;
/*     */     }
/* 234 */     while (paramInt2 > 0) {
/* 235 */       if (this.encoderOut.hasRemaining()) {
/* 236 */         int j = Math.min(this.encoderOut.remaining(), paramInt2);
/* 237 */         this.encoderOut.get(paramArrayOfByte, paramInt1, j);
/* 238 */         paramInt1 += j;
/* 239 */         paramInt2 -= j;
/* 240 */         i += j;
/*     */       } else {
/* 242 */         fillBuffer();
/* 243 */         if ((this.endOfInput) && (!this.encoderOut.hasRemaining())) {
/*     */           break;
/*     */         }
/*     */       }
/*     */     }
/* 248 */     return (i == 0) && (this.endOfInput) ? -1 : i;
/*     */   }
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
/* 261 */     return read(paramArrayOfByte, 0, paramArrayOfByte.length);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public int read()
/*     */     throws IOException
/*     */   {
/*     */     do
/*     */     {
/* 274 */       if (this.encoderOut.hasRemaining()) {
/* 275 */         return this.encoderOut.get() & 0xFF;
/*     */       }
/* 277 */       fillBuffer();
/* 278 */     } while ((!this.endOfInput) || (this.encoderOut.hasRemaining()));
/* 279 */     return -1;
/*     */   }
/*     */   
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
/* 292 */     this.reader.close();
/*     */   }
/*     */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\org\apache\commons\io\input\ReaderInputStream.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */
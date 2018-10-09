/*     */ package org.apache.commons.io.output;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import java.io.OutputStream;
/*     */ import java.io.Writer;
/*     */ import java.nio.ByteBuffer;
/*     */ import java.nio.CharBuffer;
/*     */ import java.nio.charset.Charset;
/*     */ import java.nio.charset.CharsetDecoder;
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
/*     */ public class WriterOutputStream
/*     */   extends OutputStream
/*     */ {
/*     */   private static final int DEFAULT_BUFFER_SIZE = 1024;
/*     */   private final Writer writer;
/*     */   private final CharsetDecoder decoder;
/*     */   private final boolean writeImmediately;
/*  85 */   private final ByteBuffer decoderIn = ByteBuffer.allocate(128);
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   private final CharBuffer decoderOut;
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public WriterOutputStream(Writer paramWriter, CharsetDecoder paramCharsetDecoder)
/*     */   {
/* 104 */     this(paramWriter, paramCharsetDecoder, 1024, false);
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
/*     */   public WriterOutputStream(Writer paramWriter, CharsetDecoder paramCharsetDecoder, int paramInt, boolean paramBoolean)
/*     */   {
/* 121 */     this.writer = paramWriter;
/* 122 */     this.decoder = paramCharsetDecoder;
/* 123 */     this.writeImmediately = paramBoolean;
/* 124 */     this.decoderOut = CharBuffer.allocate(paramInt);
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
/*     */   public WriterOutputStream(Writer paramWriter, Charset paramCharset, int paramInt, boolean paramBoolean)
/*     */   {
/* 140 */     this(paramWriter, paramCharset.newDecoder().onMalformedInput(CodingErrorAction.REPLACE).onUnmappableCharacter(CodingErrorAction.REPLACE).replaceWith("?"), paramInt, paramBoolean);
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
/*     */   public WriterOutputStream(Writer paramWriter, Charset paramCharset)
/*     */   {
/* 158 */     this(paramWriter, paramCharset, 1024, false);
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
/*     */   public WriterOutputStream(Writer paramWriter, String paramString, int paramInt, boolean paramBoolean)
/*     */   {
/* 174 */     this(paramWriter, Charset.forName(paramString), paramInt, paramBoolean);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public WriterOutputStream(Writer paramWriter, String paramString)
/*     */   {
/* 186 */     this(paramWriter, paramString, 1024, false);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public WriterOutputStream(Writer paramWriter)
/*     */   {
/* 197 */     this(paramWriter, Charset.defaultCharset(), 1024, false);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public void write(byte[] paramArrayOfByte, int paramInt1, int paramInt2)
/*     */     throws IOException
/*     */   {
/* 210 */     while (paramInt2 > 0) {
/* 211 */       int i = Math.min(paramInt2, this.decoderIn.remaining());
/* 212 */       this.decoderIn.put(paramArrayOfByte, paramInt1, i);
/* 213 */       processInput(false);
/* 214 */       paramInt2 -= i;
/* 215 */       paramInt1 += i;
/*     */     }
/* 217 */     if (this.writeImmediately) {
/* 218 */       flushOutput();
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public void write(byte[] paramArrayOfByte)
/*     */     throws IOException
/*     */   {
/* 230 */     write(paramArrayOfByte, 0, paramArrayOfByte.length);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public void write(int paramInt)
/*     */     throws IOException
/*     */   {
/* 241 */     write(new byte[] { (byte)paramInt }, 0, 1);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public void flush()
/*     */     throws IOException
/*     */   {
/* 252 */     flushOutput();
/* 253 */     this.writer.flush();
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
/* 264 */     processInput(true);
/* 265 */     flushOutput();
/* 266 */     this.writer.close();
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   private void processInput(boolean paramBoolean)
/*     */     throws IOException
/*     */   {
/* 277 */     this.decoderIn.flip();
/*     */     CoderResult localCoderResult;
/*     */     for (;;) {
/* 280 */       localCoderResult = this.decoder.decode(this.decoderIn, this.decoderOut, paramBoolean);
/* 281 */       if (!localCoderResult.isOverflow()) break;
/* 282 */       flushOutput(); }
/* 283 */     if (!localCoderResult.isUnderflow())
/*     */     {
/*     */ 
/*     */ 
/*     */ 
/* 288 */       throw new IOException("Unexpected coder result");
/*     */     }
/*     */     
/*     */ 
/* 292 */     this.decoderIn.compact();
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   private void flushOutput()
/*     */     throws IOException
/*     */   {
/* 301 */     if (this.decoderOut.position() > 0) {
/* 302 */       this.writer.write(this.decoderOut.array(), 0, this.decoderOut.position());
/* 303 */       this.decoderOut.rewind();
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\org\apache\commons\io\output\WriterOutputStream.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */
/*     */ package org.bukkit.craftbukkit.libs.jline.internal;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import java.io.InputStream;
/*     */ import java.io.Reader;
/*     */ import java.io.UnsupportedEncodingException;
/*     */ import java.nio.ByteBuffer;
/*     */ import java.nio.CharBuffer;
/*     */ import java.nio.charset.Charset;
/*     */ import java.nio.charset.CharsetDecoder;
/*     */ import java.nio.charset.CoderResult;
/*     */ import java.nio.charset.CodingErrorAction;
/*     */ import java.nio.charset.MalformedInputException;
/*     */ import java.nio.charset.UnmappableCharacterException;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class InputStreamReader
/*     */   extends Reader
/*     */ {
/*     */   private InputStream in;
/*     */   private static final int BUFFER_SIZE = 8192;
/*  50 */   private boolean endOfInput = false;
/*     */   
/*     */   String encoding;
/*     */   
/*     */   CharsetDecoder decoder;
/*     */   
/*  56 */   ByteBuffer bytes = ByteBuffer.allocate(8192);
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public InputStreamReader(InputStream in)
/*     */   {
/*  68 */     super(in);
/*  69 */     this.in = in;
/*     */     
/*  71 */     this.encoding = System.getProperty("file.encoding", "ISO8859_1");
/*  72 */     this.decoder = Charset.forName(this.encoding).newDecoder().onMalformedInput(CodingErrorAction.REPLACE).onUnmappableCharacter(CodingErrorAction.REPLACE);
/*     */     
/*     */ 
/*  75 */     this.bytes.limit(0);
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
/*     */   public InputStreamReader(InputStream in, String enc)
/*     */     throws UnsupportedEncodingException
/*     */   {
/*  95 */     super(in);
/*  96 */     if (enc == null) {
/*  97 */       throw new NullPointerException();
/*     */     }
/*  99 */     this.in = in;
/*     */     try {
/* 101 */       this.decoder = Charset.forName(enc).newDecoder().onMalformedInput(CodingErrorAction.REPLACE).onUnmappableCharacter(CodingErrorAction.REPLACE);
/*     */     }
/*     */     catch (IllegalArgumentException e)
/*     */     {
/* 105 */       throw ((UnsupportedEncodingException)new UnsupportedEncodingException(enc).initCause(e));
/*     */     }
/*     */     
/* 108 */     this.bytes.limit(0);
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
/*     */   public InputStreamReader(InputStream in, CharsetDecoder dec)
/*     */   {
/* 121 */     super(in);
/* 122 */     dec.averageCharsPerByte();
/* 123 */     this.in = in;
/* 124 */     this.decoder = dec;
/* 125 */     this.bytes.limit(0);
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
/*     */   public InputStreamReader(InputStream in, Charset charset)
/*     */   {
/* 138 */     super(in);
/* 139 */     this.in = in;
/* 140 */     this.decoder = charset.newDecoder().onMalformedInput(CodingErrorAction.REPLACE).onUnmappableCharacter(CodingErrorAction.REPLACE);
/*     */     
/*     */ 
/* 143 */     this.bytes.limit(0);
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
/* 155 */     synchronized (this.lock) {
/* 156 */       this.decoder = null;
/* 157 */       if (this.in != null) {
/* 158 */         this.in.close();
/* 159 */         this.in = null;
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
/*     */   public String getEncoding()
/*     */   {
/* 172 */     if (!isOpen()) {
/* 173 */       return null;
/*     */     }
/* 175 */     return this.encoding;
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
/*     */   public int read()
/*     */     throws IOException
/*     */   {
/* 192 */     synchronized (this.lock) {
/* 193 */       if (!isOpen()) {
/* 194 */         throw new IOException("InputStreamReader is closed.");
/*     */       }
/*     */       
/* 197 */       char[] buf = new char[4];
/* 198 */       return read(buf, 0, 4) != -1 ? Character.codePointAt(buf, 0) : -1;
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
/*     */ 
/*     */ 
/*     */ 
/*     */   public int read(char[] buf, int offset, int length)
/*     */     throws IOException
/*     */   {
/* 228 */     synchronized (this.lock) {
/* 229 */       if (!isOpen()) {
/* 230 */         throw new IOException("InputStreamReader is closed.");
/*     */       }
/* 232 */       if ((offset < 0) || (offset > buf.length - length) || (length < 0)) {
/* 233 */         throw new IndexOutOfBoundsException();
/*     */       }
/* 235 */       if (length == 0) {
/* 236 */         return 0;
/*     */       }
/*     */       
/* 239 */       CharBuffer out = CharBuffer.wrap(buf, offset, length);
/* 240 */       CoderResult result = CoderResult.UNDERFLOW;
/*     */       
/*     */ 
/*     */ 
/* 244 */       boolean needInput = !this.bytes.hasRemaining();
/*     */       
/* 246 */       while (out.hasRemaining())
/*     */       {
/* 248 */         if (needInput) {
/*     */           try {
/* 250 */             if ((this.in.available() == 0) && (out.position() > offset)) {
/*     */               break;
/*     */             }
/*     */           }
/*     */           catch (IOException e) {}
/*     */           
/*     */ 
/*     */ 
/*     */ 
/* 259 */           int to_read = this.bytes.capacity() - this.bytes.limit();
/* 260 */           int off = this.bytes.arrayOffset() + this.bytes.limit();
/* 261 */           int was_red = this.in.read(this.bytes.array(), off, to_read);
/*     */           
/* 263 */           if (was_red == -1) {
/* 264 */             this.endOfInput = true;
/* 265 */             break; }
/* 266 */           if (was_red == 0) {
/*     */             break;
/*     */           }
/* 269 */           this.bytes.limit(this.bytes.limit() + was_red);
/* 270 */           needInput = false;
/*     */         }
/*     */         
/*     */ 
/* 274 */         result = this.decoder.decode(this.bytes, out, false);
/*     */         
/* 276 */         if (!result.isUnderflow())
/*     */           break;
/* 278 */         if (this.bytes.limit() == this.bytes.capacity()) {
/* 279 */           this.bytes.compact();
/* 280 */           this.bytes.limit(this.bytes.position());
/* 281 */           this.bytes.position(0);
/*     */         }
/* 283 */         needInput = true;
/*     */       }
/*     */       
/*     */ 
/*     */ 
/*     */ 
/* 289 */       if ((result == CoderResult.UNDERFLOW) && (this.endOfInput)) {
/* 290 */         result = this.decoder.decode(this.bytes, out, true);
/* 291 */         this.decoder.flush(out);
/* 292 */         this.decoder.reset();
/*     */       }
/* 294 */       if (result.isMalformed())
/* 295 */         throw new MalformedInputException(result.length());
/* 296 */       if (result.isUnmappable()) {
/* 297 */         throw new UnmappableCharacterException(result.length());
/*     */       }
/*     */       
/* 300 */       return out.position() - offset == 0 ? -1 : out.position() - offset;
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   private boolean isOpen()
/*     */   {
/* 309 */     return this.in != null;
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
/*     */   public boolean ready()
/*     */     throws IOException
/*     */   {
/* 327 */     synchronized (this.lock) {
/* 328 */       if (this.in == null) {
/* 329 */         throw new IOException("InputStreamReader is closed.");
/*     */       }
/*     */       try {
/* 332 */         return (this.bytes.hasRemaining()) || (this.in.available() > 0);
/*     */       } catch (IOException e) {
/* 334 */         return false;
/*     */       }
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\org\bukkit\craftbukkit\libs\jline\internal\InputStreamReader.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */
/*     */ package org.apache.commons.codec.binary;
/*     */ 
/*     */ import java.util.Arrays;
/*     */ import org.apache.commons.codec.BinaryDecoder;
/*     */ import org.apache.commons.codec.BinaryEncoder;
/*     */ import org.apache.commons.codec.DecoderException;
/*     */ import org.apache.commons.codec.EncoderException;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public abstract class BaseNCodec
/*     */   implements BinaryEncoder, BinaryDecoder
/*     */ {
/*     */   static final int EOF = -1;
/*     */   public static final int MIME_CHUNK_SIZE = 76;
/*     */   public static final int PEM_CHUNK_SIZE = 64;
/*     */   private static final int DEFAULT_BUFFER_RESIZE_FACTOR = 2;
/*     */   private static final int DEFAULT_BUFFER_SIZE = 8192;
/*     */   protected static final int MASK_8BITS = 255;
/*     */   protected static final byte PAD_DEFAULT = 61;
/*     */   
/*     */   static class Context
/*     */   {
/*     */     int ibitWorkArea;
/*     */     long lbitWorkArea;
/*     */     byte[] buffer;
/*     */     int pos;
/*     */     int readPos;
/*     */     boolean eof;
/*     */     int currentLinePos;
/*     */     int modulus;
/*     */     
/*     */     public String toString()
/*     */     {
/* 103 */       return String.format("%s[buffer=%s, currentLinePos=%s, eof=%s, ibitWorkArea=%s, lbitWorkArea=%s, modulus=%s, pos=%s, readPos=%s]", new Object[] { getClass().getSimpleName(), Arrays.toString(this.buffer), Integer.valueOf(this.currentLinePos), Boolean.valueOf(this.eof), Integer.valueOf(this.ibitWorkArea), Long.valueOf(this.lbitWorkArea), Integer.valueOf(this.modulus), Integer.valueOf(this.pos), Integer.valueOf(this.readPos) });
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/* 156 */   protected final byte PAD = 61;
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   private final int unencodedBlockSize;
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   private final int encodedBlockSize;
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   protected final int lineLength;
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   private final int chunkSeparatorLength;
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   protected BaseNCodec(int paramInt1, int paramInt2, int paramInt3, int paramInt4)
/*     */   {
/* 186 */     this.unencodedBlockSize = paramInt1;
/* 187 */     this.encodedBlockSize = paramInt2;
/* 188 */     int i = (paramInt3 > 0) && (paramInt4 > 0) ? 1 : 0;
/* 189 */     this.lineLength = (i != 0 ? paramInt3 / paramInt2 * paramInt2 : 0);
/* 190 */     this.chunkSeparatorLength = paramInt4;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   boolean hasData(Context paramContext)
/*     */   {
/* 200 */     return paramContext.buffer != null;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   int available(Context paramContext)
/*     */   {
/* 210 */     return paramContext.buffer != null ? paramContext.pos - paramContext.readPos : 0;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   protected int getDefaultBufferSize()
/*     */   {
/* 219 */     return 8192;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   private byte[] resizeBuffer(Context paramContext)
/*     */   {
/* 227 */     if (paramContext.buffer == null) {
/* 228 */       paramContext.buffer = new byte[getDefaultBufferSize()];
/* 229 */       paramContext.pos = 0;
/* 230 */       paramContext.readPos = 0;
/*     */     } else {
/* 232 */       byte[] arrayOfByte = new byte[paramContext.buffer.length * 2];
/* 233 */       System.arraycopy(paramContext.buffer, 0, arrayOfByte, 0, paramContext.buffer.length);
/* 234 */       paramContext.buffer = arrayOfByte;
/*     */     }
/* 236 */     return paramContext.buffer;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   protected byte[] ensureBufferSize(int paramInt, Context paramContext)
/*     */   {
/* 246 */     if ((paramContext.buffer == null) || (paramContext.buffer.length < paramContext.pos + paramInt)) {
/* 247 */       return resizeBuffer(paramContext);
/*     */     }
/* 249 */     return paramContext.buffer;
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
/*     */   int readResults(byte[] paramArrayOfByte, int paramInt1, int paramInt2, Context paramContext)
/*     */   {
/* 269 */     if (paramContext.buffer != null) {
/* 270 */       int i = Math.min(available(paramContext), paramInt2);
/* 271 */       System.arraycopy(paramContext.buffer, paramContext.readPos, paramArrayOfByte, paramInt1, i);
/* 272 */       paramContext.readPos += i;
/* 273 */       if (paramContext.readPos >= paramContext.pos) {
/* 274 */         paramContext.buffer = null;
/*     */       }
/* 276 */       return i;
/*     */     }
/* 278 */     return paramContext.eof ? -1 : 0;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   protected static boolean isWhiteSpace(byte paramByte)
/*     */   {
/* 289 */     switch (paramByte) {
/*     */     case 9: 
/*     */     case 10: 
/*     */     case 13: 
/*     */     case 32: 
/* 294 */       return true;
/*     */     }
/* 296 */     return false;
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
/*     */   public Object encode(Object paramObject)
/*     */     throws EncoderException
/*     */   {
/* 312 */     if (!(paramObject instanceof byte[])) {
/* 313 */       throw new EncoderException("Parameter supplied to Base-N encode is not a byte[]");
/*     */     }
/* 315 */     return encode((byte[])paramObject);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public String encodeToString(byte[] paramArrayOfByte)
/*     */   {
/* 327 */     return StringUtils.newStringUtf8(encode(paramArrayOfByte));
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public String encodeAsString(byte[] paramArrayOfByte)
/*     */   {
/* 338 */     return StringUtils.newStringUtf8(encode(paramArrayOfByte));
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
/*     */   public Object decode(Object paramObject)
/*     */     throws DecoderException
/*     */   {
/* 354 */     if ((paramObject instanceof byte[]))
/* 355 */       return decode((byte[])paramObject);
/* 356 */     if ((paramObject instanceof String)) {
/* 357 */       return decode((String)paramObject);
/*     */     }
/* 359 */     throw new DecoderException("Parameter supplied to Base-N decode is not a byte[] or a String");
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public byte[] decode(String paramString)
/*     */   {
/* 371 */     return decode(StringUtils.getBytesUtf8(paramString));
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public byte[] decode(byte[] paramArrayOfByte)
/*     */   {
/* 383 */     if ((paramArrayOfByte == null) || (paramArrayOfByte.length == 0)) {
/* 384 */       return paramArrayOfByte;
/*     */     }
/* 386 */     Context localContext = new Context();
/* 387 */     decode(paramArrayOfByte, 0, paramArrayOfByte.length, localContext);
/* 388 */     decode(paramArrayOfByte, 0, -1, localContext);
/* 389 */     byte[] arrayOfByte = new byte[localContext.pos];
/* 390 */     readResults(arrayOfByte, 0, arrayOfByte.length, localContext);
/* 391 */     return arrayOfByte;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public byte[] encode(byte[] paramArrayOfByte)
/*     */   {
/* 403 */     if ((paramArrayOfByte == null) || (paramArrayOfByte.length == 0)) {
/* 404 */       return paramArrayOfByte;
/*     */     }
/* 406 */     Context localContext = new Context();
/* 407 */     encode(paramArrayOfByte, 0, paramArrayOfByte.length, localContext);
/* 408 */     encode(paramArrayOfByte, 0, -1, localContext);
/* 409 */     byte[] arrayOfByte = new byte[localContext.pos - localContext.readPos];
/* 410 */     readResults(arrayOfByte, 0, arrayOfByte.length, localContext);
/* 411 */     return arrayOfByte;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   abstract void encode(byte[] paramArrayOfByte, int paramInt1, int paramInt2, Context paramContext);
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   abstract void decode(byte[] paramArrayOfByte, int paramInt1, int paramInt2, Context paramContext);
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   protected abstract boolean isInAlphabet(byte paramByte);
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public boolean isInAlphabet(byte[] paramArrayOfByte, boolean paramBoolean)
/*     */   {
/* 441 */     for (int i = 0; i < paramArrayOfByte.length; i++) {
/* 442 */       if ((!isInAlphabet(paramArrayOfByte[i])) && ((!paramBoolean) || ((paramArrayOfByte[i] != 61) && (!isWhiteSpace(paramArrayOfByte[i])))))
/*     */       {
/* 444 */         return false;
/*     */       }
/*     */     }
/* 447 */     return true;
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
/*     */   public boolean isInAlphabet(String paramString)
/*     */   {
/* 460 */     return isInAlphabet(StringUtils.getBytesUtf8(paramString), true);
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
/*     */   protected boolean containsAlphabetOrPad(byte[] paramArrayOfByte)
/*     */   {
/* 473 */     if (paramArrayOfByte == null) {
/* 474 */       return false;
/*     */     }
/* 476 */     for (byte b : paramArrayOfByte) {
/* 477 */       if ((61 == b) || (isInAlphabet(b))) {
/* 478 */         return true;
/*     */       }
/*     */     }
/* 481 */     return false;
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
/*     */   public long getEncodedLength(byte[] paramArrayOfByte)
/*     */   {
/* 495 */     long l = (paramArrayOfByte.length + this.unencodedBlockSize - 1) / this.unencodedBlockSize * this.encodedBlockSize;
/* 496 */     if (this.lineLength > 0)
/*     */     {
/* 498 */       l += (l + this.lineLength - 1L) / this.lineLength * this.chunkSeparatorLength;
/*     */     }
/* 500 */     return l;
/*     */   }
/*     */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\org\apache\commons\codec\binary\BaseNCodec.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */
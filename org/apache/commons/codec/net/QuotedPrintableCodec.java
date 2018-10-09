/*     */ package org.apache.commons.codec.net;
/*     */ 
/*     */ import java.io.ByteArrayOutputStream;
/*     */ import java.io.UnsupportedEncodingException;
/*     */ import java.nio.charset.Charset;
/*     */ import java.nio.charset.IllegalCharsetNameException;
/*     */ import java.nio.charset.UnsupportedCharsetException;
/*     */ import java.util.BitSet;
/*     */ import org.apache.commons.codec.BinaryDecoder;
/*     */ import org.apache.commons.codec.BinaryEncoder;
/*     */ import org.apache.commons.codec.Charsets;
/*     */ import org.apache.commons.codec.DecoderException;
/*     */ import org.apache.commons.codec.EncoderException;
/*     */ import org.apache.commons.codec.StringDecoder;
/*     */ import org.apache.commons.codec.StringEncoder;
/*     */ import org.apache.commons.codec.binary.StringUtils;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class QuotedPrintableCodec
/*     */   implements BinaryEncoder, BinaryDecoder, StringEncoder, StringDecoder
/*     */ {
/*     */   private final Charset charset;
/*  70 */   private static final BitSet PRINTABLE_CHARS = new BitSet(256);
/*     */   
/*     */   private static final byte ESCAPE_CHAR = 61;
/*     */   
/*     */   private static final byte TAB = 9;
/*     */   
/*     */   private static final byte SPACE = 32;
/*     */   
/*     */   static
/*     */   {
/*  80 */     for (int i = 33; i <= 60; i++) {
/*  81 */       PRINTABLE_CHARS.set(i);
/*     */     }
/*  83 */     for (i = 62; i <= 126; i++) {
/*  84 */       PRINTABLE_CHARS.set(i);
/*     */     }
/*  86 */     PRINTABLE_CHARS.set(9);
/*  87 */     PRINTABLE_CHARS.set(32);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public QuotedPrintableCodec()
/*     */   {
/*  94 */     this(Charsets.UTF_8);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public QuotedPrintableCodec(Charset paramCharset)
/*     */   {
/* 105 */     this.charset = paramCharset;
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
/*     */   public QuotedPrintableCodec(String paramString)
/*     */     throws IllegalCharsetNameException, IllegalArgumentException, UnsupportedCharsetException
/*     */   {
/* 125 */     this(Charset.forName(paramString));
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   private static final void encodeQuotedPrintable(int paramInt, ByteArrayOutputStream paramByteArrayOutputStream)
/*     */   {
/* 137 */     paramByteArrayOutputStream.write(61);
/* 138 */     int i = Character.toUpperCase(Character.forDigit(paramInt >> 4 & 0xF, 16));
/* 139 */     int j = Character.toUpperCase(Character.forDigit(paramInt & 0xF, 16));
/* 140 */     paramByteArrayOutputStream.write(i);
/* 141 */     paramByteArrayOutputStream.write(j);
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
/*     */   public static final byte[] encodeQuotedPrintable(BitSet paramBitSet, byte[] paramArrayOfByte)
/*     */   {
/* 157 */     if (paramArrayOfByte == null) {
/* 158 */       return null;
/*     */     }
/* 160 */     if (paramBitSet == null) {
/* 161 */       paramBitSet = PRINTABLE_CHARS;
/*     */     }
/* 163 */     ByteArrayOutputStream localByteArrayOutputStream = new ByteArrayOutputStream();
/* 164 */     for (int k : paramArrayOfByte) {
/* 165 */       int m = k;
/* 166 */       if (m < 0) {
/* 167 */         m = 256 + m;
/*     */       }
/* 169 */       if (paramBitSet.get(m)) {
/* 170 */         localByteArrayOutputStream.write(m);
/*     */       } else {
/* 172 */         encodeQuotedPrintable(m, localByteArrayOutputStream);
/*     */       }
/*     */     }
/* 175 */     return localByteArrayOutputStream.toByteArray();
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
/*     */   public static final byte[] decodeQuotedPrintable(byte[] paramArrayOfByte)
/*     */     throws DecoderException
/*     */   {
/* 192 */     if (paramArrayOfByte == null) {
/* 193 */       return null;
/*     */     }
/* 195 */     ByteArrayOutputStream localByteArrayOutputStream = new ByteArrayOutputStream();
/* 196 */     for (int i = 0; i < paramArrayOfByte.length; i++) {
/* 197 */       int j = paramArrayOfByte[i];
/* 198 */       if (j == 61) {
/*     */         try {
/* 200 */           int k = Utils.digit16(paramArrayOfByte[(++i)]);
/* 201 */           int m = Utils.digit16(paramArrayOfByte[(++i)]);
/* 202 */           localByteArrayOutputStream.write((char)((k << 4) + m));
/*     */         } catch (ArrayIndexOutOfBoundsException localArrayIndexOutOfBoundsException) {
/* 204 */           throw new DecoderException("Invalid quoted-printable encoding", localArrayIndexOutOfBoundsException);
/*     */         }
/*     */       } else {
/* 207 */         localByteArrayOutputStream.write(j);
/*     */       }
/*     */     }
/* 210 */     return localByteArrayOutputStream.toByteArray();
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
/*     */   public byte[] encode(byte[] paramArrayOfByte)
/*     */   {
/* 225 */     return encodeQuotedPrintable(PRINTABLE_CHARS, paramArrayOfByte);
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
/*     */   public byte[] decode(byte[] paramArrayOfByte)
/*     */     throws DecoderException
/*     */   {
/* 243 */     return decodeQuotedPrintable(paramArrayOfByte);
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
/*     */   public String encode(String paramString)
/*     */     throws EncoderException
/*     */   {
/* 262 */     return encode(paramString, getCharset());
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
/*     */   public String decode(String paramString, Charset paramCharset)
/*     */     throws DecoderException
/*     */   {
/* 279 */     if (paramString == null) {
/* 280 */       return null;
/*     */     }
/* 282 */     return new String(decode(StringUtils.getBytesUsAscii(paramString)), paramCharset);
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
/*     */   public String decode(String paramString1, String paramString2)
/*     */     throws DecoderException, UnsupportedEncodingException
/*     */   {
/* 300 */     if (paramString1 == null) {
/* 301 */       return null;
/*     */     }
/* 303 */     return new String(decode(StringUtils.getBytesUsAscii(paramString1)), paramString2);
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
/*     */   public String decode(String paramString)
/*     */     throws DecoderException
/*     */   {
/* 319 */     return decode(paramString, getCharset());
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
/*     */   public Object encode(Object paramObject)
/*     */     throws EncoderException
/*     */   {
/* 334 */     if (paramObject == null)
/* 335 */       return null;
/* 336 */     if ((paramObject instanceof byte[]))
/* 337 */       return encode((byte[])paramObject);
/* 338 */     if ((paramObject instanceof String)) {
/* 339 */       return encode((String)paramObject);
/*     */     }
/* 341 */     throw new EncoderException("Objects of type " + paramObject.getClass().getName() + " cannot be quoted-printable encoded");
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
/*     */   public Object decode(Object paramObject)
/*     */     throws DecoderException
/*     */   {
/* 360 */     if (paramObject == null)
/* 361 */       return null;
/* 362 */     if ((paramObject instanceof byte[]))
/* 363 */       return decode((byte[])paramObject);
/* 364 */     if ((paramObject instanceof String)) {
/* 365 */       return decode((String)paramObject);
/*     */     }
/* 367 */     throw new DecoderException("Objects of type " + paramObject.getClass().getName() + " cannot be quoted-printable decoded");
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
/*     */   public Charset getCharset()
/*     */   {
/* 380 */     return this.charset;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public String getDefaultCharset()
/*     */   {
/* 389 */     return this.charset.name();
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
/*     */   public String encode(String paramString, Charset paramCharset)
/*     */   {
/* 406 */     if (paramString == null) {
/* 407 */       return null;
/*     */     }
/* 409 */     return StringUtils.newStringUsAscii(encode(paramString.getBytes(paramCharset)));
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
/*     */   public String encode(String paramString1, String paramString2)
/*     */     throws UnsupportedEncodingException
/*     */   {
/* 427 */     if (paramString1 == null) {
/* 428 */       return null;
/*     */     }
/* 430 */     return StringUtils.newStringUsAscii(encode(paramString1.getBytes(paramString2)));
/*     */   }
/*     */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\org\apache\commons\codec\net\QuotedPrintableCodec.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */
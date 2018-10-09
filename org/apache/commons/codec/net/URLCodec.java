/*     */ package org.apache.commons.codec.net;
/*     */ 
/*     */ import java.io.ByteArrayOutputStream;
/*     */ import java.io.UnsupportedEncodingException;
/*     */ import java.util.BitSet;
/*     */ import org.apache.commons.codec.BinaryDecoder;
/*     */ import org.apache.commons.codec.BinaryEncoder;
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
/*     */ 
/*     */ public class URLCodec
/*     */   implements BinaryEncoder, BinaryDecoder, StringEncoder, StringDecoder
/*     */ {
/*     */   static final int RADIX = 16;
/*     */   @Deprecated
/*     */   protected String charset;
/*     */   protected static final byte ESCAPE_CHAR = 37;
/*  70 */   protected static final BitSet WWW_FORM_URL = new BitSet(256);
/*     */   
/*     */ 
/*     */   static
/*     */   {
/*  75 */     for (int i = 97; i <= 122; i++) {
/*  76 */       WWW_FORM_URL.set(i);
/*     */     }
/*  78 */     for (i = 65; i <= 90; i++) {
/*  79 */       WWW_FORM_URL.set(i);
/*     */     }
/*     */     
/*  82 */     for (i = 48; i <= 57; i++) {
/*  83 */       WWW_FORM_URL.set(i);
/*     */     }
/*     */     
/*  86 */     WWW_FORM_URL.set(45);
/*  87 */     WWW_FORM_URL.set(95);
/*  88 */     WWW_FORM_URL.set(46);
/*  89 */     WWW_FORM_URL.set(42);
/*     */     
/*  91 */     WWW_FORM_URL.set(32);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public URLCodec()
/*     */   {
/*  99 */     this("UTF-8");
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public URLCodec(String paramString)
/*     */   {
/* 109 */     this.charset = paramString;
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
/*     */   public static final byte[] encodeUrl(BitSet paramBitSet, byte[] paramArrayOfByte)
/*     */   {
/* 122 */     if (paramArrayOfByte == null) {
/* 123 */       return null;
/*     */     }
/* 125 */     if (paramBitSet == null) {
/* 126 */       paramBitSet = WWW_FORM_URL;
/*     */     }
/*     */     
/* 129 */     ByteArrayOutputStream localByteArrayOutputStream = new ByteArrayOutputStream();
/* 130 */     for (int k : paramArrayOfByte) {
/* 131 */       int m = k;
/* 132 */       if (m < 0) {
/* 133 */         m = 256 + m;
/*     */       }
/* 135 */       if (paramBitSet.get(m)) {
/* 136 */         if (m == 32) {
/* 137 */           m = 43;
/*     */         }
/* 139 */         localByteArrayOutputStream.write(m);
/*     */       } else {
/* 141 */         localByteArrayOutputStream.write(37);
/* 142 */         int n = Character.toUpperCase(Character.forDigit(m >> 4 & 0xF, 16));
/* 143 */         int i1 = Character.toUpperCase(Character.forDigit(m & 0xF, 16));
/* 144 */         localByteArrayOutputStream.write(n);
/* 145 */         localByteArrayOutputStream.write(i1);
/*     */       }
/*     */     }
/* 148 */     return localByteArrayOutputStream.toByteArray();
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
/*     */   public static final byte[] decodeUrl(byte[] paramArrayOfByte)
/*     */     throws DecoderException
/*     */   {
/* 162 */     if (paramArrayOfByte == null) {
/* 163 */       return null;
/*     */     }
/* 165 */     ByteArrayOutputStream localByteArrayOutputStream = new ByteArrayOutputStream();
/* 166 */     for (int i = 0; i < paramArrayOfByte.length; i++) {
/* 167 */       int j = paramArrayOfByte[i];
/* 168 */       if (j == 43) {
/* 169 */         localByteArrayOutputStream.write(32);
/* 170 */       } else if (j == 37) {
/*     */         try {
/* 172 */           int k = Utils.digit16(paramArrayOfByte[(++i)]);
/* 173 */           int m = Utils.digit16(paramArrayOfByte[(++i)]);
/* 174 */           localByteArrayOutputStream.write((char)((k << 4) + m));
/*     */         } catch (ArrayIndexOutOfBoundsException localArrayIndexOutOfBoundsException) {
/* 176 */           throw new DecoderException("Invalid URL encoding: ", localArrayIndexOutOfBoundsException);
/*     */         }
/*     */       } else {
/* 179 */         localByteArrayOutputStream.write(j);
/*     */       }
/*     */     }
/* 182 */     return localByteArrayOutputStream.toByteArray();
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
/* 194 */     return encodeUrl(WWW_FORM_URL, paramArrayOfByte);
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
/*     */   public byte[] decode(byte[] paramArrayOfByte)
/*     */     throws DecoderException
/*     */   {
/* 210 */     return decodeUrl(paramArrayOfByte);
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
/*     */   public String encode(String paramString1, String paramString2)
/*     */     throws UnsupportedEncodingException
/*     */   {
/* 225 */     if (paramString1 == null) {
/* 226 */       return null;
/*     */     }
/* 228 */     return StringUtils.newStringUsAscii(encode(paramString1.getBytes(paramString2)));
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
/*     */   public String encode(String paramString)
/*     */     throws EncoderException
/*     */   {
/* 244 */     if (paramString == null) {
/* 245 */       return null;
/*     */     }
/*     */     try {
/* 248 */       return encode(paramString, getDefaultCharset());
/*     */     } catch (UnsupportedEncodingException localUnsupportedEncodingException) {
/* 250 */       throw new EncoderException(localUnsupportedEncodingException.getMessage(), localUnsupportedEncodingException);
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
/*     */   public String decode(String paramString1, String paramString2)
/*     */     throws DecoderException, UnsupportedEncodingException
/*     */   {
/* 270 */     if (paramString1 == null) {
/* 271 */       return null;
/*     */     }
/* 273 */     return new String(decode(StringUtils.getBytesUsAscii(paramString1)), paramString2);
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
/* 289 */     if (paramString == null) {
/* 290 */       return null;
/*     */     }
/*     */     try {
/* 293 */       return decode(paramString, getDefaultCharset());
/*     */     } catch (UnsupportedEncodingException localUnsupportedEncodingException) {
/* 295 */       throw new DecoderException(localUnsupportedEncodingException.getMessage(), localUnsupportedEncodingException);
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
/*     */   public Object encode(Object paramObject)
/*     */     throws EncoderException
/*     */   {
/* 310 */     if (paramObject == null)
/* 311 */       return null;
/* 312 */     if ((paramObject instanceof byte[]))
/* 313 */       return encode((byte[])paramObject);
/* 314 */     if ((paramObject instanceof String)) {
/* 315 */       return encode((String)paramObject);
/*     */     }
/* 317 */     throw new EncoderException("Objects of type " + paramObject.getClass().getName() + " cannot be URL encoded");
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
/*     */   public Object decode(Object paramObject)
/*     */     throws DecoderException
/*     */   {
/* 335 */     if (paramObject == null)
/* 336 */       return null;
/* 337 */     if ((paramObject instanceof byte[]))
/* 338 */       return decode((byte[])paramObject);
/* 339 */     if ((paramObject instanceof String)) {
/* 340 */       return decode((String)paramObject);
/*     */     }
/* 342 */     throw new DecoderException("Objects of type " + paramObject.getClass().getName() + " cannot be URL decoded");
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public String getDefaultCharset()
/*     */   {
/* 353 */     return this.charset;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   @Deprecated
/*     */   public String getEncoding()
/*     */   {
/* 365 */     return this.charset;
/*     */   }
/*     */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\org\apache\commons\codec\net\URLCodec.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */
/*     */ package org.apache.commons.codec.net;
/*     */ 
/*     */ import java.io.UnsupportedEncodingException;
/*     */ import java.nio.charset.Charset;
/*     */ import java.util.BitSet;
/*     */ import org.apache.commons.codec.Charsets;
/*     */ import org.apache.commons.codec.DecoderException;
/*     */ import org.apache.commons.codec.EncoderException;
/*     */ import org.apache.commons.codec.StringDecoder;
/*     */ import org.apache.commons.codec.StringEncoder;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class QCodec
/*     */   extends RFC1522Codec
/*     */   implements StringEncoder, StringDecoder
/*     */ {
/*     */   private final Charset charset;
/*  61 */   private static final BitSet PRINTABLE_CHARS = new BitSet(256);
/*     */   private static final byte BLANK = 32;
/*     */   private static final byte UNDERSCORE = 95;
/*     */   
/*  65 */   static { PRINTABLE_CHARS.set(32);
/*  66 */     PRINTABLE_CHARS.set(33);
/*  67 */     PRINTABLE_CHARS.set(34);
/*  68 */     PRINTABLE_CHARS.set(35);
/*  69 */     PRINTABLE_CHARS.set(36);
/*  70 */     PRINTABLE_CHARS.set(37);
/*  71 */     PRINTABLE_CHARS.set(38);
/*  72 */     PRINTABLE_CHARS.set(39);
/*  73 */     PRINTABLE_CHARS.set(40);
/*  74 */     PRINTABLE_CHARS.set(41);
/*  75 */     PRINTABLE_CHARS.set(42);
/*  76 */     PRINTABLE_CHARS.set(43);
/*  77 */     PRINTABLE_CHARS.set(44);
/*  78 */     PRINTABLE_CHARS.set(45);
/*  79 */     PRINTABLE_CHARS.set(46);
/*  80 */     PRINTABLE_CHARS.set(47);
/*  81 */     for (int i = 48; i <= 57; i++) {
/*  82 */       PRINTABLE_CHARS.set(i);
/*     */     }
/*  84 */     PRINTABLE_CHARS.set(58);
/*  85 */     PRINTABLE_CHARS.set(59);
/*  86 */     PRINTABLE_CHARS.set(60);
/*  87 */     PRINTABLE_CHARS.set(62);
/*  88 */     PRINTABLE_CHARS.set(64);
/*  89 */     for (i = 65; i <= 90; i++) {
/*  90 */       PRINTABLE_CHARS.set(i);
/*     */     }
/*  92 */     PRINTABLE_CHARS.set(91);
/*  93 */     PRINTABLE_CHARS.set(92);
/*  94 */     PRINTABLE_CHARS.set(93);
/*  95 */     PRINTABLE_CHARS.set(94);
/*  96 */     PRINTABLE_CHARS.set(96);
/*  97 */     for (i = 97; i <= 122; i++) {
/*  98 */       PRINTABLE_CHARS.set(i);
/*     */     }
/* 100 */     PRINTABLE_CHARS.set(123);
/* 101 */     PRINTABLE_CHARS.set(124);
/* 102 */     PRINTABLE_CHARS.set(125);
/* 103 */     PRINTABLE_CHARS.set(126);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/* 110 */   private boolean encodeBlanks = false;
/*     */   
/*     */ 
/*     */ 
/*     */   public QCodec()
/*     */   {
/* 116 */     this(Charsets.UTF_8);
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
/*     */   public QCodec(Charset paramCharset)
/*     */   {
/* 130 */     this.charset = paramCharset;
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
/*     */   public QCodec(String paramString)
/*     */   {
/* 144 */     this(Charset.forName(paramString));
/*     */   }
/*     */   
/*     */   protected String getEncoding()
/*     */   {
/* 149 */     return "Q";
/*     */   }
/*     */   
/*     */   protected byte[] doEncoding(byte[] paramArrayOfByte)
/*     */   {
/* 154 */     if (paramArrayOfByte == null) {
/* 155 */       return null;
/*     */     }
/* 157 */     byte[] arrayOfByte = QuotedPrintableCodec.encodeQuotedPrintable(PRINTABLE_CHARS, paramArrayOfByte);
/* 158 */     if (this.encodeBlanks) {
/* 159 */       for (int i = 0; i < arrayOfByte.length; i++) {
/* 160 */         if (arrayOfByte[i] == 32) {
/* 161 */           arrayOfByte[i] = 95;
/*     */         }
/*     */       }
/*     */     }
/* 165 */     return arrayOfByte;
/*     */   }
/*     */   
/*     */   protected byte[] doDecoding(byte[] paramArrayOfByte) throws DecoderException
/*     */   {
/* 170 */     if (paramArrayOfByte == null) {
/* 171 */       return null;
/*     */     }
/* 173 */     int i = 0;
/* 174 */     for (int m : paramArrayOfByte) {
/* 175 */       if (m == 95) {
/* 176 */         i = 1;
/* 177 */         break;
/*     */       }
/*     */     }
/* 180 */     if (i != 0) {
/* 181 */       ??? = new byte[paramArrayOfByte.length];
/* 182 */       for (??? = 0; ??? < paramArrayOfByte.length; ???++) {
/* 183 */         ??? = paramArrayOfByte[???];
/* 184 */         if (??? != 95) {
/* 185 */           ???[???] = ???;
/*     */         } else {
/* 187 */           ???[???] = 32;
/*     */         }
/*     */       }
/* 190 */       return QuotedPrintableCodec.decodeQuotedPrintable(???);
/*     */     }
/* 192 */     return QuotedPrintableCodec.decodeQuotedPrintable(paramArrayOfByte);
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
/*     */   public String encode(String paramString, Charset paramCharset)
/*     */     throws EncoderException
/*     */   {
/* 208 */     if (paramString == null) {
/* 209 */       return null;
/*     */     }
/* 211 */     return encodeText(paramString, paramCharset);
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
/*     */     throws EncoderException
/*     */   {
/* 226 */     if (paramString1 == null) {
/* 227 */       return null;
/*     */     }
/*     */     try {
/* 230 */       return encodeText(paramString1, paramString2);
/*     */     } catch (UnsupportedEncodingException localUnsupportedEncodingException) {
/* 232 */       throw new EncoderException(localUnsupportedEncodingException.getMessage(), localUnsupportedEncodingException);
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
/*     */   public String encode(String paramString)
/*     */     throws EncoderException
/*     */   {
/* 247 */     if (paramString == null) {
/* 248 */       return null;
/*     */     }
/* 250 */     return encode(paramString, getCharset());
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
/*     */   public String decode(String paramString)
/*     */     throws DecoderException
/*     */   {
/* 265 */     if (paramString == null) {
/* 266 */       return null;
/*     */     }
/*     */     try {
/* 269 */       return decodeText(paramString);
/*     */     } catch (UnsupportedEncodingException localUnsupportedEncodingException) {
/* 271 */       throw new DecoderException(localUnsupportedEncodingException.getMessage(), localUnsupportedEncodingException);
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
/* 286 */     if (paramObject == null)
/* 287 */       return null;
/* 288 */     if ((paramObject instanceof String)) {
/* 289 */       return encode((String)paramObject);
/*     */     }
/* 291 */     throw new EncoderException("Objects of type " + paramObject.getClass().getName() + " cannot be encoded using Q codec");
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
/* 310 */     if (paramObject == null)
/* 311 */       return null;
/* 312 */     if ((paramObject instanceof String)) {
/* 313 */       return decode((String)paramObject);
/*     */     }
/* 315 */     throw new DecoderException("Objects of type " + paramObject.getClass().getName() + " cannot be decoded using Q codec");
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
/* 328 */     return this.charset;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public String getDefaultCharset()
/*     */   {
/* 337 */     return this.charset.name();
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public boolean isEncodeBlanks()
/*     */   {
/* 346 */     return this.encodeBlanks;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public void setEncodeBlanks(boolean paramBoolean)
/*     */   {
/* 356 */     this.encodeBlanks = paramBoolean;
/*     */   }
/*     */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\org\apache\commons\codec\net\QCodec.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */
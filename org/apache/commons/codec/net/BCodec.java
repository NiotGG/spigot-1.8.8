/*     */ package org.apache.commons.codec.net;
/*     */ 
/*     */ import java.io.UnsupportedEncodingException;
/*     */ import java.nio.charset.Charset;
/*     */ import org.apache.commons.codec.Charsets;
/*     */ import org.apache.commons.codec.DecoderException;
/*     */ import org.apache.commons.codec.EncoderException;
/*     */ import org.apache.commons.codec.StringDecoder;
/*     */ import org.apache.commons.codec.StringEncoder;
/*     */ import org.apache.commons.codec.binary.Base64;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class BCodec
/*     */   extends RFC1522Codec
/*     */   implements StringEncoder, StringDecoder
/*     */ {
/*     */   private final Charset charset;
/*     */   
/*     */   public BCodec()
/*     */   {
/*  56 */     this(Charsets.UTF_8);
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
/*     */   public BCodec(Charset paramCharset)
/*     */   {
/*  69 */     this.charset = paramCharset;
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
/*     */   public BCodec(String paramString)
/*     */   {
/*  83 */     this(Charset.forName(paramString));
/*     */   }
/*     */   
/*     */   protected String getEncoding()
/*     */   {
/*  88 */     return "B";
/*     */   }
/*     */   
/*     */   protected byte[] doEncoding(byte[] paramArrayOfByte)
/*     */   {
/*  93 */     if (paramArrayOfByte == null) {
/*  94 */       return null;
/*     */     }
/*  96 */     return Base64.encodeBase64(paramArrayOfByte);
/*     */   }
/*     */   
/*     */   protected byte[] doDecoding(byte[] paramArrayOfByte)
/*     */   {
/* 101 */     if (paramArrayOfByte == null) {
/* 102 */       return null;
/*     */     }
/* 104 */     return Base64.decodeBase64(paramArrayOfByte);
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
/* 120 */     if (paramString == null) {
/* 121 */       return null;
/*     */     }
/* 123 */     return encodeText(paramString, paramCharset);
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
/* 138 */     if (paramString1 == null) {
/* 139 */       return null;
/*     */     }
/*     */     try {
/* 142 */       return encodeText(paramString1, paramString2);
/*     */     } catch (UnsupportedEncodingException localUnsupportedEncodingException) {
/* 144 */       throw new EncoderException(localUnsupportedEncodingException.getMessage(), localUnsupportedEncodingException);
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
/* 159 */     if (paramString == null) {
/* 160 */       return null;
/*     */     }
/* 162 */     return encode(paramString, getCharset());
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
/* 177 */     if (paramString == null) {
/* 178 */       return null;
/*     */     }
/*     */     try {
/* 181 */       return decodeText(paramString);
/*     */     } catch (UnsupportedEncodingException localUnsupportedEncodingException) {
/* 183 */       throw new DecoderException(localUnsupportedEncodingException.getMessage(), localUnsupportedEncodingException);
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
/* 198 */     if (paramObject == null)
/* 199 */       return null;
/* 200 */     if ((paramObject instanceof String)) {
/* 201 */       return encode((String)paramObject);
/*     */     }
/* 203 */     throw new EncoderException("Objects of type " + paramObject.getClass().getName() + " cannot be encoded using BCodec");
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
/* 222 */     if (paramObject == null)
/* 223 */       return null;
/* 224 */     if ((paramObject instanceof String)) {
/* 225 */       return decode((String)paramObject);
/*     */     }
/* 227 */     throw new DecoderException("Objects of type " + paramObject.getClass().getName() + " cannot be decoded using BCodec");
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
/* 240 */     return this.charset;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public String getDefaultCharset()
/*     */   {
/* 249 */     return this.charset.name();
/*     */   }
/*     */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\org\apache\commons\codec\net\BCodec.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */
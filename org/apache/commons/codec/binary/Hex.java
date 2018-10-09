/*     */ package org.apache.commons.codec.binary;
/*     */ 
/*     */ import java.nio.charset.Charset;
/*     */ import org.apache.commons.codec.BinaryDecoder;
/*     */ import org.apache.commons.codec.BinaryEncoder;
/*     */ import org.apache.commons.codec.Charsets;
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
/*     */ public class Hex
/*     */   implements BinaryEncoder, BinaryDecoder
/*     */ {
/*  45 */   public static final Charset DEFAULT_CHARSET = Charsets.UTF_8;
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public static final String DEFAULT_CHARSET_NAME = "UTF-8";
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*  57 */   private static final char[] DIGITS_LOWER = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f' };
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*  63 */   private static final char[] DIGITS_UPPER = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F' };
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   private final Charset charset;
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public static byte[] decodeHex(char[] paramArrayOfChar)
/*     */     throws DecoderException
/*     */   {
/*  79 */     int i = paramArrayOfChar.length;
/*     */     
/*  81 */     if ((i & 0x1) != 0) {
/*  82 */       throw new DecoderException("Odd number of characters.");
/*     */     }
/*     */     
/*  85 */     byte[] arrayOfByte = new byte[i >> 1];
/*     */     
/*     */ 
/*  88 */     int j = 0; for (int k = 0; k < i; j++) {
/*  89 */       int m = toDigit(paramArrayOfChar[k], k) << 4;
/*  90 */       k++;
/*  91 */       m |= toDigit(paramArrayOfChar[k], k);
/*  92 */       k++;
/*  93 */       arrayOfByte[j] = ((byte)(m & 0xFF));
/*     */     }
/*     */     
/*  96 */     return arrayOfByte;
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
/*     */   public static char[] encodeHex(byte[] paramArrayOfByte)
/*     */   {
/* 109 */     return encodeHex(paramArrayOfByte, true);
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
/*     */   public static char[] encodeHex(byte[] paramArrayOfByte, boolean paramBoolean)
/*     */   {
/* 125 */     return encodeHex(paramArrayOfByte, paramBoolean ? DIGITS_LOWER : DIGITS_UPPER);
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
/*     */   protected static char[] encodeHex(byte[] paramArrayOfByte, char[] paramArrayOfChar)
/*     */   {
/* 141 */     int i = paramArrayOfByte.length;
/* 142 */     char[] arrayOfChar = new char[i << 1];
/*     */     
/* 144 */     int j = 0; for (int k = 0; j < i; j++) {
/* 145 */       arrayOfChar[(k++)] = paramArrayOfChar[((0xF0 & paramArrayOfByte[j]) >>> 4)];
/* 146 */       arrayOfChar[(k++)] = paramArrayOfChar[(0xF & paramArrayOfByte[j])];
/*     */     }
/* 148 */     return arrayOfChar;
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
/*     */   public static String encodeHexString(byte[] paramArrayOfByte)
/*     */   {
/* 161 */     return new String(encodeHex(paramArrayOfByte));
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
/*     */   protected static int toDigit(char paramChar, int paramInt)
/*     */     throws DecoderException
/*     */   {
/* 176 */     int i = Character.digit(paramChar, 16);
/* 177 */     if (i == -1) {
/* 178 */       throw new DecoderException("Illegal hexadecimal character " + paramChar + " at index " + paramInt);
/*     */     }
/* 180 */     return i;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public Hex()
/*     */   {
/* 190 */     this.charset = DEFAULT_CHARSET;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public Hex(Charset paramCharset)
/*     */   {
/* 201 */     this.charset = paramCharset;
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
/*     */   public Hex(String paramString)
/*     */   {
/* 215 */     this(Charset.forName(paramString));
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
/*     */   public byte[] decode(byte[] paramArrayOfByte)
/*     */     throws DecoderException
/*     */   {
/* 232 */     return decodeHex(new String(paramArrayOfByte, getCharset()).toCharArray());
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
/*     */   public Object decode(Object paramObject)
/*     */     throws DecoderException
/*     */   {
/*     */     try
/*     */     {
/* 251 */       char[] arrayOfChar = (paramObject instanceof String) ? ((String)paramObject).toCharArray() : (char[])paramObject;
/* 252 */       return decodeHex(arrayOfChar);
/*     */     } catch (ClassCastException localClassCastException) {
/* 254 */       throw new DecoderException(localClassCastException.getMessage(), localClassCastException);
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
/*     */   public byte[] encode(byte[] paramArrayOfByte)
/*     */   {
/* 275 */     return encodeHexString(paramArrayOfByte).getBytes(getCharset());
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
/*     */   public Object encode(Object paramObject)
/*     */     throws EncoderException
/*     */   {
/*     */     try
/*     */     {
/* 297 */       byte[] arrayOfByte = (paramObject instanceof String) ? ((String)paramObject).getBytes(getCharset()) : (byte[])paramObject;
/*     */       
/* 299 */       return encodeHex(arrayOfByte);
/*     */     } catch (ClassCastException localClassCastException) {
/* 301 */       throw new EncoderException(localClassCastException.getMessage(), localClassCastException);
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public Charset getCharset()
/*     */   {
/* 312 */     return this.charset;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public String getCharsetName()
/*     */   {
/* 322 */     return this.charset.name();
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public String toString()
/*     */   {
/* 332 */     return super.toString() + "[charsetName=" + this.charset + "]";
/*     */   }
/*     */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\org\apache\commons\codec\binary\Hex.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */
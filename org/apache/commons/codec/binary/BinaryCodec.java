/*     */ package org.apache.commons.codec.binary;
/*     */ 
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
/*     */ public class BinaryCodec
/*     */   implements BinaryDecoder, BinaryEncoder
/*     */ {
/*  42 */   private static final char[] EMPTY_CHAR_ARRAY = new char[0];
/*     */   
/*     */ 
/*  45 */   private static final byte[] EMPTY_BYTE_ARRAY = new byte[0];
/*     */   
/*     */ 
/*     */   private static final int BIT_0 = 1;
/*     */   
/*     */ 
/*     */   private static final int BIT_1 = 2;
/*     */   
/*     */ 
/*     */   private static final int BIT_2 = 4;
/*     */   
/*     */ 
/*     */   private static final int BIT_3 = 8;
/*     */   
/*     */ 
/*     */   private static final int BIT_4 = 16;
/*     */   
/*     */ 
/*     */   private static final int BIT_5 = 32;
/*     */   
/*     */ 
/*     */   private static final int BIT_6 = 64;
/*     */   
/*     */ 
/*     */   private static final int BIT_7 = 128;
/*     */   
/*  71 */   private static final int[] BITS = { 1, 2, 4, 8, 16, 32, 64, 128 };
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
/*  83 */     return toAsciiBytes(paramArrayOfByte);
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
/*  98 */     if (!(paramObject instanceof byte[])) {
/*  99 */       throw new EncoderException("argument not a byte array");
/*     */     }
/* 101 */     return toAsciiChars((byte[])paramObject);
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
/*     */   public Object decode(Object paramObject)
/*     */     throws DecoderException
/*     */   {
/* 116 */     if (paramObject == null) {
/* 117 */       return EMPTY_BYTE_ARRAY;
/*     */     }
/* 119 */     if ((paramObject instanceof byte[])) {
/* 120 */       return fromAscii((byte[])paramObject);
/*     */     }
/* 122 */     if ((paramObject instanceof char[])) {
/* 123 */       return fromAscii((char[])paramObject);
/*     */     }
/* 125 */     if ((paramObject instanceof String)) {
/* 126 */       return fromAscii(((String)paramObject).toCharArray());
/*     */     }
/* 128 */     throw new DecoderException("argument not a byte array");
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
/*     */   public byte[] decode(byte[] paramArrayOfByte)
/*     */   {
/* 141 */     return fromAscii(paramArrayOfByte);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public byte[] toByteArray(String paramString)
/*     */   {
/* 153 */     if (paramString == null) {
/* 154 */       return EMPTY_BYTE_ARRAY;
/*     */     }
/* 156 */     return fromAscii(paramString.toCharArray());
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
/*     */   public static byte[] fromAscii(char[] paramArrayOfChar)
/*     */   {
/* 172 */     if ((paramArrayOfChar == null) || (paramArrayOfChar.length == 0)) {
/* 173 */       return EMPTY_BYTE_ARRAY;
/*     */     }
/*     */     
/* 176 */     byte[] arrayOfByte = new byte[paramArrayOfChar.length >> 3];
/*     */     
/*     */ 
/*     */ 
/*     */ 
/* 181 */     int i = 0; for (int j = paramArrayOfChar.length - 1; i < arrayOfByte.length; j -= 8) {
/* 182 */       for (int k = 0; k < BITS.length; k++) {
/* 183 */         if (paramArrayOfChar[(j - k)] == '1') {
/* 184 */           int tmp58_57 = i; byte[] tmp58_56 = arrayOfByte;tmp58_56[tmp58_57] = ((byte)(tmp58_56[tmp58_57] | BITS[k]));
/*     */         }
/*     */       }
/* 181 */       i++;
/*     */     }
/*     */     
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/* 188 */     return arrayOfByte;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public static byte[] fromAscii(byte[] paramArrayOfByte)
/*     */   {
/* 199 */     if (isEmpty(paramArrayOfByte)) {
/* 200 */       return EMPTY_BYTE_ARRAY;
/*     */     }
/*     */     
/* 203 */     byte[] arrayOfByte = new byte[paramArrayOfByte.length >> 3];
/*     */     
/*     */ 
/*     */ 
/*     */ 
/* 208 */     int i = 0; for (int j = paramArrayOfByte.length - 1; i < arrayOfByte.length; j -= 8) {
/* 209 */       for (int k = 0; k < BITS.length; k++) {
/* 210 */         if (paramArrayOfByte[(j - k)] == 49) {
/* 211 */           int tmp56_55 = i; byte[] tmp56_54 = arrayOfByte;tmp56_54[tmp56_55] = ((byte)(tmp56_54[tmp56_55] | BITS[k]));
/*     */         }
/*     */       }
/* 208 */       i++;
/*     */     }
/*     */     
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/* 215 */     return arrayOfByte;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   private static boolean isEmpty(byte[] paramArrayOfByte)
/*     */   {
/* 226 */     return (paramArrayOfByte == null) || (paramArrayOfByte.length == 0);
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
/*     */   public static byte[] toAsciiBytes(byte[] paramArrayOfByte)
/*     */   {
/* 239 */     if (isEmpty(paramArrayOfByte)) {
/* 240 */       return EMPTY_BYTE_ARRAY;
/*     */     }
/*     */     
/* 243 */     byte[] arrayOfByte = new byte[paramArrayOfByte.length << 3];
/*     */     
/*     */ 
/*     */ 
/*     */ 
/* 248 */     int i = 0; for (int j = arrayOfByte.length - 1; i < paramArrayOfByte.length; j -= 8) {
/* 249 */       for (int k = 0; k < BITS.length; k++) {
/* 250 */         if ((paramArrayOfByte[i] & BITS[k]) == 0) {
/* 251 */           arrayOfByte[(j - k)] = 48;
/*     */         } else {
/* 253 */           arrayOfByte[(j - k)] = 49;
/*     */         }
/*     */       }
/* 248 */       i++;
/*     */     }
/*     */     
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/* 257 */     return arrayOfByte;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public static char[] toAsciiChars(byte[] paramArrayOfByte)
/*     */   {
/* 269 */     if (isEmpty(paramArrayOfByte)) {
/* 270 */       return EMPTY_CHAR_ARRAY;
/*     */     }
/*     */     
/* 273 */     char[] arrayOfChar = new char[paramArrayOfByte.length << 3];
/*     */     
/*     */ 
/*     */ 
/*     */ 
/* 278 */     int i = 0; for (int j = arrayOfChar.length - 1; i < paramArrayOfByte.length; j -= 8) {
/* 279 */       for (int k = 0; k < BITS.length; k++) {
/* 280 */         if ((paramArrayOfByte[i] & BITS[k]) == 0) {
/* 281 */           arrayOfChar[(j - k)] = '0';
/*     */         } else {
/* 283 */           arrayOfChar[(j - k)] = '1';
/*     */         }
/*     */       }
/* 278 */       i++;
/*     */     }
/*     */     
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/* 287 */     return arrayOfChar;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public static String toAsciiString(byte[] paramArrayOfByte)
/*     */   {
/* 299 */     return new String(toAsciiChars(paramArrayOfByte));
/*     */   }
/*     */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\org\apache\commons\codec\binary\BinaryCodec.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */
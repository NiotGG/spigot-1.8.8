/*     */ package io.netty.handler.codec.base64;
/*     */ 
/*     */ import io.netty.buffer.ByteBuf;
/*     */ import io.netty.buffer.ByteBufAllocator;
/*     */ import io.netty.buffer.Unpooled;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public final class Base64
/*     */ {
/*     */   private static final int MAX_LINE_LENGTH = 76;
/*     */   private static final byte EQUALS_SIGN = 61;
/*     */   private static final byte NEW_LINE = 10;
/*     */   private static final byte WHITE_SPACE_ENC = -5;
/*     */   private static final byte EQUALS_SIGN_ENC = -1;
/*     */   
/*     */   private static byte[] alphabet(Base64Dialect paramBase64Dialect)
/*     */   {
/*  49 */     if (paramBase64Dialect == null) {
/*  50 */       throw new NullPointerException("dialect");
/*     */     }
/*  52 */     return paramBase64Dialect.alphabet;
/*     */   }
/*     */   
/*     */   private static byte[] decodabet(Base64Dialect paramBase64Dialect) {
/*  56 */     if (paramBase64Dialect == null) {
/*  57 */       throw new NullPointerException("dialect");
/*     */     }
/*  59 */     return paramBase64Dialect.decodabet;
/*     */   }
/*     */   
/*     */   private static boolean breakLines(Base64Dialect paramBase64Dialect) {
/*  63 */     if (paramBase64Dialect == null) {
/*  64 */       throw new NullPointerException("dialect");
/*     */     }
/*  66 */     return paramBase64Dialect.breakLinesByDefault;
/*     */   }
/*     */   
/*     */   public static ByteBuf encode(ByteBuf paramByteBuf) {
/*  70 */     return encode(paramByteBuf, Base64Dialect.STANDARD);
/*     */   }
/*     */   
/*     */   public static ByteBuf encode(ByteBuf paramByteBuf, Base64Dialect paramBase64Dialect) {
/*  74 */     return encode(paramByteBuf, breakLines(paramBase64Dialect), paramBase64Dialect);
/*     */   }
/*     */   
/*     */   public static ByteBuf encode(ByteBuf paramByteBuf, boolean paramBoolean) {
/*  78 */     return encode(paramByteBuf, paramBoolean, Base64Dialect.STANDARD);
/*     */   }
/*     */   
/*     */   public static ByteBuf encode(ByteBuf paramByteBuf, boolean paramBoolean, Base64Dialect paramBase64Dialect)
/*     */   {
/*  83 */     if (paramByteBuf == null) {
/*  84 */       throw new NullPointerException("src");
/*     */     }
/*     */     
/*  87 */     ByteBuf localByteBuf = encode(paramByteBuf, paramByteBuf.readerIndex(), paramByteBuf.readableBytes(), paramBoolean, paramBase64Dialect);
/*  88 */     paramByteBuf.readerIndex(paramByteBuf.writerIndex());
/*  89 */     return localByteBuf;
/*     */   }
/*     */   
/*     */   public static ByteBuf encode(ByteBuf paramByteBuf, int paramInt1, int paramInt2) {
/*  93 */     return encode(paramByteBuf, paramInt1, paramInt2, Base64Dialect.STANDARD);
/*     */   }
/*     */   
/*     */   public static ByteBuf encode(ByteBuf paramByteBuf, int paramInt1, int paramInt2, Base64Dialect paramBase64Dialect) {
/*  97 */     return encode(paramByteBuf, paramInt1, paramInt2, breakLines(paramBase64Dialect), paramBase64Dialect);
/*     */   }
/*     */   
/*     */   public static ByteBuf encode(ByteBuf paramByteBuf, int paramInt1, int paramInt2, boolean paramBoolean)
/*     */   {
/* 102 */     return encode(paramByteBuf, paramInt1, paramInt2, paramBoolean, Base64Dialect.STANDARD);
/*     */   }
/*     */   
/*     */ 
/*     */   public static ByteBuf encode(ByteBuf paramByteBuf, int paramInt1, int paramInt2, boolean paramBoolean, Base64Dialect paramBase64Dialect)
/*     */   {
/* 108 */     if (paramByteBuf == null) {
/* 109 */       throw new NullPointerException("src");
/*     */     }
/* 111 */     if (paramBase64Dialect == null) {
/* 112 */       throw new NullPointerException("dialect");
/*     */     }
/*     */     
/* 115 */     int i = paramInt2 * 4 / 3;
/* 116 */     ByteBuf localByteBuf = Unpooled.buffer(i + (paramInt2 % 3 > 0 ? 4 : 0) + (paramBoolean ? i / 76 : 0)).order(paramByteBuf.order());
/*     */     
/*     */ 
/*     */ 
/* 120 */     int j = 0;
/* 121 */     int k = 0;
/* 122 */     int m = paramInt2 - 2;
/* 123 */     int n = 0;
/* 124 */     for (; j < m; k += 4) {
/* 125 */       encode3to4(paramByteBuf, j + paramInt1, 3, localByteBuf, k, paramBase64Dialect);
/*     */       
/* 127 */       n += 4;
/* 128 */       if ((paramBoolean) && (n == 76)) {
/* 129 */         localByteBuf.setByte(k + 4, 10);
/* 130 */         k++;
/* 131 */         n = 0;
/*     */       }
/* 124 */       j += 3;
/*     */     }
/*     */     
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/* 135 */     if (j < paramInt2) {
/* 136 */       encode3to4(paramByteBuf, j + paramInt1, paramInt2 - j, localByteBuf, k, paramBase64Dialect);
/* 137 */       k += 4;
/*     */     }
/*     */     
/* 140 */     return localByteBuf.slice(0, k);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   private static void encode3to4(ByteBuf paramByteBuf1, int paramInt1, int paramInt2, ByteBuf paramByteBuf2, int paramInt3, Base64Dialect paramBase64Dialect)
/*     */   {
/* 147 */     byte[] arrayOfByte = alphabet(paramBase64Dialect);
/*     */     
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/* 160 */     int i = (paramInt2 > 0 ? paramByteBuf1.getByte(paramInt1) << 24 >>> 8 : 0) | (paramInt2 > 1 ? paramByteBuf1.getByte(paramInt1 + 1) << 24 >>> 16 : 0) | (paramInt2 > 2 ? paramByteBuf1.getByte(paramInt1 + 2) << 24 >>> 24 : 0);
/*     */     
/*     */ 
/*     */ 
/*     */ 
/* 165 */     switch (paramInt2) {
/*     */     case 3: 
/* 167 */       paramByteBuf2.setByte(paramInt3, arrayOfByte[(i >>> 18)]);
/* 168 */       paramByteBuf2.setByte(paramInt3 + 1, arrayOfByte[(i >>> 12 & 0x3F)]);
/* 169 */       paramByteBuf2.setByte(paramInt3 + 2, arrayOfByte[(i >>> 6 & 0x3F)]);
/* 170 */       paramByteBuf2.setByte(paramInt3 + 3, arrayOfByte[(i & 0x3F)]);
/* 171 */       break;
/*     */     case 2: 
/* 173 */       paramByteBuf2.setByte(paramInt3, arrayOfByte[(i >>> 18)]);
/* 174 */       paramByteBuf2.setByte(paramInt3 + 1, arrayOfByte[(i >>> 12 & 0x3F)]);
/* 175 */       paramByteBuf2.setByte(paramInt3 + 2, arrayOfByte[(i >>> 6 & 0x3F)]);
/* 176 */       paramByteBuf2.setByte(paramInt3 + 3, 61);
/* 177 */       break;
/*     */     case 1: 
/* 179 */       paramByteBuf2.setByte(paramInt3, arrayOfByte[(i >>> 18)]);
/* 180 */       paramByteBuf2.setByte(paramInt3 + 1, arrayOfByte[(i >>> 12 & 0x3F)]);
/* 181 */       paramByteBuf2.setByte(paramInt3 + 2, 61);
/* 182 */       paramByteBuf2.setByte(paramInt3 + 3, 61);
/*     */     }
/*     */   }
/*     */   
/*     */   public static ByteBuf decode(ByteBuf paramByteBuf)
/*     */   {
/* 188 */     return decode(paramByteBuf, Base64Dialect.STANDARD);
/*     */   }
/*     */   
/*     */   public static ByteBuf decode(ByteBuf paramByteBuf, Base64Dialect paramBase64Dialect)
/*     */   {
/* 193 */     if (paramByteBuf == null) {
/* 194 */       throw new NullPointerException("src");
/*     */     }
/*     */     
/* 197 */     ByteBuf localByteBuf = decode(paramByteBuf, paramByteBuf.readerIndex(), paramByteBuf.readableBytes(), paramBase64Dialect);
/* 198 */     paramByteBuf.readerIndex(paramByteBuf.writerIndex());
/* 199 */     return localByteBuf;
/*     */   }
/*     */   
/*     */   public static ByteBuf decode(ByteBuf paramByteBuf, int paramInt1, int paramInt2)
/*     */   {
/* 204 */     return decode(paramByteBuf, paramInt1, paramInt2, Base64Dialect.STANDARD);
/*     */   }
/*     */   
/*     */ 
/*     */   public static ByteBuf decode(ByteBuf paramByteBuf, int paramInt1, int paramInt2, Base64Dialect paramBase64Dialect)
/*     */   {
/* 210 */     if (paramByteBuf == null) {
/* 211 */       throw new NullPointerException("src");
/*     */     }
/* 213 */     if (paramBase64Dialect == null) {
/* 214 */       throw new NullPointerException("dialect");
/*     */     }
/*     */     
/* 217 */     byte[] arrayOfByte1 = decodabet(paramBase64Dialect);
/*     */     
/* 219 */     int i = paramInt2 * 3 / 4;
/* 220 */     ByteBuf localByteBuf = paramByteBuf.alloc().buffer(i).order(paramByteBuf.order());
/* 221 */     int j = 0;
/*     */     
/* 223 */     byte[] arrayOfByte2 = new byte[4];
/* 224 */     int k = 0;
/*     */     
/*     */ 
/*     */ 
/* 228 */     for (int m = paramInt1; m < paramInt1 + paramInt2; m++) {
/* 229 */       int n = (byte)(paramByteBuf.getByte(m) & 0x7F);
/* 230 */       int i1 = arrayOfByte1[n];
/*     */       
/* 232 */       if (i1 >= -5) {
/* 233 */         if (i1 >= -1) {
/* 234 */           arrayOfByte2[(k++)] = n;
/* 235 */           if (k > 3) {
/* 236 */             j += decode4to3(arrayOfByte2, 0, localByteBuf, j, paramBase64Dialect);
/*     */             
/* 238 */             k = 0;
/*     */             
/*     */ 
/* 241 */             if (n == 61) {
/*     */               break;
/*     */             }
/*     */           }
/*     */         }
/*     */       } else {
/* 247 */         throw new IllegalArgumentException("bad Base64 input character at " + m + ": " + paramByteBuf.getUnsignedByte(m) + " (decimal)");
/*     */       }
/*     */     }
/*     */     
/*     */ 
/*     */ 
/* 253 */     return localByteBuf.slice(0, j);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   private static int decode4to3(byte[] paramArrayOfByte, int paramInt1, ByteBuf paramByteBuf, int paramInt2, Base64Dialect paramBase64Dialect)
/*     */   {
/* 260 */     byte[] arrayOfByte = decodabet(paramBase64Dialect);
/*     */     int i;
/* 262 */     if (paramArrayOfByte[(paramInt1 + 2)] == 61)
/*     */     {
/* 264 */       i = (arrayOfByte[paramArrayOfByte[paramInt1]] & 0xFF) << 18 | (arrayOfByte[paramArrayOfByte[(paramInt1 + 1)]] & 0xFF) << 12;
/*     */       
/*     */ 
/*     */ 
/* 268 */       paramByteBuf.setByte(paramInt2, (byte)(i >>> 16));
/* 269 */       return 1; }
/* 270 */     if (paramArrayOfByte[(paramInt1 + 3)] == 61)
/*     */     {
/* 272 */       i = (arrayOfByte[paramArrayOfByte[paramInt1]] & 0xFF) << 18 | (arrayOfByte[paramArrayOfByte[(paramInt1 + 1)]] & 0xFF) << 12 | (arrayOfByte[paramArrayOfByte[(paramInt1 + 2)]] & 0xFF) << 6;
/*     */       
/*     */ 
/*     */ 
/*     */ 
/* 277 */       paramByteBuf.setByte(paramInt2, (byte)(i >>> 16));
/* 278 */       paramByteBuf.setByte(paramInt2 + 1, (byte)(i >>> 8));
/* 279 */       return 2;
/*     */     }
/*     */     
/*     */     try
/*     */     {
/* 284 */       i = (arrayOfByte[paramArrayOfByte[paramInt1]] & 0xFF) << 18 | (arrayOfByte[paramArrayOfByte[(paramInt1 + 1)]] & 0xFF) << 12 | (arrayOfByte[paramArrayOfByte[(paramInt1 + 2)]] & 0xFF) << 6 | arrayOfByte[paramArrayOfByte[(paramInt1 + 3)]] & 0xFF;
/*     */ 
/*     */     }
/*     */     catch (IndexOutOfBoundsException localIndexOutOfBoundsException)
/*     */     {
/*     */ 
/* 290 */       throw new IllegalArgumentException("not encoded in Base64");
/*     */     }
/*     */     
/* 293 */     paramByteBuf.setByte(paramInt2, (byte)(i >> 16));
/* 294 */     paramByteBuf.setByte(paramInt2 + 1, (byte)(i >> 8));
/* 295 */     paramByteBuf.setByte(paramInt2 + 2, (byte)i);
/* 296 */     return 3;
/*     */   }
/*     */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\io\netty\handler\codec\base64\Base64.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */
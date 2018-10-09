/*     */ package org.apache.commons.io;
/*     */ 
/*     */ import java.io.EOFException;
/*     */ import java.io.IOException;
/*     */ import java.io.InputStream;
/*     */ import java.io.OutputStream;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class EndianUtils
/*     */ {
/*     */   public static short swapShort(short paramShort)
/*     */   {
/*  56 */     return (short)(((paramShort >> 0 & 0xFF) << 8) + ((paramShort >> 8 & 0xFF) << 0));
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public static int swapInteger(int paramInt)
/*     */   {
/*  66 */     return ((paramInt >> 0 & 0xFF) << 24) + ((paramInt >> 8 & 0xFF) << 16) + ((paramInt >> 16 & 0xFF) << 8) + ((paramInt >> 24 & 0xFF) << 0);
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
/*     */   public static long swapLong(long paramLong)
/*     */   {
/*  79 */     return ((paramLong >> 0 & 0xFF) << 56) + ((paramLong >> 8 & 0xFF) << 48) + ((paramLong >> 16 & 0xFF) << 40) + ((paramLong >> 24 & 0xFF) << 32) + ((paramLong >> 32 & 0xFF) << 24) + ((paramLong >> 40 & 0xFF) << 16) + ((paramLong >> 48 & 0xFF) << 8) + ((paramLong >> 56 & 0xFF) << 0);
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
/*     */   public static float swapFloat(float paramFloat)
/*     */   {
/*  96 */     return Float.intBitsToFloat(swapInteger(Float.floatToIntBits(paramFloat)));
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public static double swapDouble(double paramDouble)
/*     */   {
/* 105 */     return Double.longBitsToDouble(swapLong(Double.doubleToLongBits(paramDouble)));
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
/*     */   public static void writeSwappedShort(byte[] paramArrayOfByte, int paramInt, short paramShort)
/*     */   {
/* 118 */     paramArrayOfByte[(paramInt + 0)] = ((byte)(paramShort >> 0 & 0xFF));
/* 119 */     paramArrayOfByte[(paramInt + 1)] = ((byte)(paramShort >> 8 & 0xFF));
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public static short readSwappedShort(byte[] paramArrayOfByte, int paramInt)
/*     */   {
/* 130 */     return (short)(((paramArrayOfByte[(paramInt + 0)] & 0xFF) << 0) + ((paramArrayOfByte[(paramInt + 1)] & 0xFF) << 8));
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
/*     */   public static int readSwappedUnsignedShort(byte[] paramArrayOfByte, int paramInt)
/*     */   {
/* 143 */     return ((paramArrayOfByte[(paramInt + 0)] & 0xFF) << 0) + ((paramArrayOfByte[(paramInt + 1)] & 0xFF) << 8);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public static void writeSwappedInteger(byte[] paramArrayOfByte, int paramInt1, int paramInt2)
/*     */   {
/* 155 */     paramArrayOfByte[(paramInt1 + 0)] = ((byte)(paramInt2 >> 0 & 0xFF));
/* 156 */     paramArrayOfByte[(paramInt1 + 1)] = ((byte)(paramInt2 >> 8 & 0xFF));
/* 157 */     paramArrayOfByte[(paramInt1 + 2)] = ((byte)(paramInt2 >> 16 & 0xFF));
/* 158 */     paramArrayOfByte[(paramInt1 + 3)] = ((byte)(paramInt2 >> 24 & 0xFF));
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public static int readSwappedInteger(byte[] paramArrayOfByte, int paramInt)
/*     */   {
/* 169 */     return ((paramArrayOfByte[(paramInt + 0)] & 0xFF) << 0) + ((paramArrayOfByte[(paramInt + 1)] & 0xFF) << 8) + ((paramArrayOfByte[(paramInt + 2)] & 0xFF) << 16) + ((paramArrayOfByte[(paramInt + 3)] & 0xFF) << 24);
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
/*     */   public static long readSwappedUnsignedInteger(byte[] paramArrayOfByte, int paramInt)
/*     */   {
/* 184 */     long l1 = ((paramArrayOfByte[(paramInt + 0)] & 0xFF) << 0) + ((paramArrayOfByte[(paramInt + 1)] & 0xFF) << 8) + ((paramArrayOfByte[(paramInt + 2)] & 0xFF) << 16);
/*     */     
/*     */ 
/*     */ 
/* 188 */     long l2 = paramArrayOfByte[(paramInt + 3)] & 0xFF;
/*     */     
/* 190 */     return (l2 << 24) + (0xFFFFFFFF & l1);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public static void writeSwappedLong(byte[] paramArrayOfByte, int paramInt, long paramLong)
/*     */   {
/* 201 */     paramArrayOfByte[(paramInt + 0)] = ((byte)(int)(paramLong >> 0 & 0xFF));
/* 202 */     paramArrayOfByte[(paramInt + 1)] = ((byte)(int)(paramLong >> 8 & 0xFF));
/* 203 */     paramArrayOfByte[(paramInt + 2)] = ((byte)(int)(paramLong >> 16 & 0xFF));
/* 204 */     paramArrayOfByte[(paramInt + 3)] = ((byte)(int)(paramLong >> 24 & 0xFF));
/* 205 */     paramArrayOfByte[(paramInt + 4)] = ((byte)(int)(paramLong >> 32 & 0xFF));
/* 206 */     paramArrayOfByte[(paramInt + 5)] = ((byte)(int)(paramLong >> 40 & 0xFF));
/* 207 */     paramArrayOfByte[(paramInt + 6)] = ((byte)(int)(paramLong >> 48 & 0xFF));
/* 208 */     paramArrayOfByte[(paramInt + 7)] = ((byte)(int)(paramLong >> 56 & 0xFF));
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public static long readSwappedLong(byte[] paramArrayOfByte, int paramInt)
/*     */   {
/* 219 */     long l1 = ((paramArrayOfByte[(paramInt + 0)] & 0xFF) << 0) + ((paramArrayOfByte[(paramInt + 1)] & 0xFF) << 8) + ((paramArrayOfByte[(paramInt + 2)] & 0xFF) << 16) + ((paramArrayOfByte[(paramInt + 3)] & 0xFF) << 24);
/*     */     
/*     */ 
/*     */ 
/*     */ 
/* 224 */     long l2 = ((paramArrayOfByte[(paramInt + 4)] & 0xFF) << 0) + ((paramArrayOfByte[(paramInt + 5)] & 0xFF) << 8) + ((paramArrayOfByte[(paramInt + 6)] & 0xFF) << 16) + ((paramArrayOfByte[(paramInt + 7)] & 0xFF) << 24);
/*     */     
/*     */ 
/*     */ 
/*     */ 
/* 229 */     return (l2 << 32) + (0xFFFFFFFF & l1);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public static void writeSwappedFloat(byte[] paramArrayOfByte, int paramInt, float paramFloat)
/*     */   {
/* 240 */     writeSwappedInteger(paramArrayOfByte, paramInt, Float.floatToIntBits(paramFloat));
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public static float readSwappedFloat(byte[] paramArrayOfByte, int paramInt)
/*     */   {
/* 251 */     return Float.intBitsToFloat(readSwappedInteger(paramArrayOfByte, paramInt));
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public static void writeSwappedDouble(byte[] paramArrayOfByte, int paramInt, double paramDouble)
/*     */   {
/* 262 */     writeSwappedLong(paramArrayOfByte, paramInt, Double.doubleToLongBits(paramDouble));
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public static double readSwappedDouble(byte[] paramArrayOfByte, int paramInt)
/*     */   {
/* 273 */     return Double.longBitsToDouble(readSwappedLong(paramArrayOfByte, paramInt));
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public static void writeSwappedShort(OutputStream paramOutputStream, short paramShort)
/*     */     throws IOException
/*     */   {
/* 286 */     paramOutputStream.write((byte)(paramShort >> 0 & 0xFF));
/* 287 */     paramOutputStream.write((byte)(paramShort >> 8 & 0xFF));
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public static short readSwappedShort(InputStream paramInputStream)
/*     */     throws IOException
/*     */   {
/* 300 */     return (short)(((read(paramInputStream) & 0xFF) << 0) + ((read(paramInputStream) & 0xFF) << 8));
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
/*     */   public static int readSwappedUnsignedShort(InputStream paramInputStream)
/*     */     throws IOException
/*     */   {
/* 314 */     int i = read(paramInputStream);
/* 315 */     int j = read(paramInputStream);
/*     */     
/* 317 */     return ((i & 0xFF) << 0) + ((j & 0xFF) << 8);
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
/*     */   public static void writeSwappedInteger(OutputStream paramOutputStream, int paramInt)
/*     */     throws IOException
/*     */   {
/* 331 */     paramOutputStream.write((byte)(paramInt >> 0 & 0xFF));
/* 332 */     paramOutputStream.write((byte)(paramInt >> 8 & 0xFF));
/* 333 */     paramOutputStream.write((byte)(paramInt >> 16 & 0xFF));
/* 334 */     paramOutputStream.write((byte)(paramInt >> 24 & 0xFF));
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public static int readSwappedInteger(InputStream paramInputStream)
/*     */     throws IOException
/*     */   {
/* 347 */     int i = read(paramInputStream);
/* 348 */     int j = read(paramInputStream);
/* 349 */     int k = read(paramInputStream);
/* 350 */     int m = read(paramInputStream);
/*     */     
/* 352 */     return ((i & 0xFF) << 0) + ((j & 0xFF) << 8) + ((k & 0xFF) << 16) + ((m & 0xFF) << 24);
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
/*     */   public static long readSwappedUnsignedInteger(InputStream paramInputStream)
/*     */     throws IOException
/*     */   {
/* 368 */     int i = read(paramInputStream);
/* 369 */     int j = read(paramInputStream);
/* 370 */     int k = read(paramInputStream);
/* 371 */     int m = read(paramInputStream);
/*     */     
/* 373 */     long l1 = ((i & 0xFF) << 0) + ((j & 0xFF) << 8) + ((k & 0xFF) << 16);
/*     */     
/*     */ 
/*     */ 
/* 377 */     long l2 = m & 0xFF;
/*     */     
/* 379 */     return (l2 << 24) + (0xFFFFFFFF & l1);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public static void writeSwappedLong(OutputStream paramOutputStream, long paramLong)
/*     */     throws IOException
/*     */   {
/* 392 */     paramOutputStream.write((byte)(int)(paramLong >> 0 & 0xFF));
/* 393 */     paramOutputStream.write((byte)(int)(paramLong >> 8 & 0xFF));
/* 394 */     paramOutputStream.write((byte)(int)(paramLong >> 16 & 0xFF));
/* 395 */     paramOutputStream.write((byte)(int)(paramLong >> 24 & 0xFF));
/* 396 */     paramOutputStream.write((byte)(int)(paramLong >> 32 & 0xFF));
/* 397 */     paramOutputStream.write((byte)(int)(paramLong >> 40 & 0xFF));
/* 398 */     paramOutputStream.write((byte)(int)(paramLong >> 48 & 0xFF));
/* 399 */     paramOutputStream.write((byte)(int)(paramLong >> 56 & 0xFF));
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public static long readSwappedLong(InputStream paramInputStream)
/*     */     throws IOException
/*     */   {
/* 412 */     byte[] arrayOfByte = new byte[8];
/* 413 */     for (int i = 0; i < 8; i++) {
/* 414 */       arrayOfByte[i] = ((byte)read(paramInputStream));
/*     */     }
/* 416 */     return readSwappedLong(arrayOfByte, 0);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public static void writeSwappedFloat(OutputStream paramOutputStream, float paramFloat)
/*     */     throws IOException
/*     */   {
/* 429 */     writeSwappedInteger(paramOutputStream, Float.floatToIntBits(paramFloat));
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public static float readSwappedFloat(InputStream paramInputStream)
/*     */     throws IOException
/*     */   {
/* 442 */     return Float.intBitsToFloat(readSwappedInteger(paramInputStream));
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public static void writeSwappedDouble(OutputStream paramOutputStream, double paramDouble)
/*     */     throws IOException
/*     */   {
/* 455 */     writeSwappedLong(paramOutputStream, Double.doubleToLongBits(paramDouble));
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public static double readSwappedDouble(InputStream paramInputStream)
/*     */     throws IOException
/*     */   {
/* 468 */     return Double.longBitsToDouble(readSwappedLong(paramInputStream));
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   private static int read(InputStream paramInputStream)
/*     */     throws IOException
/*     */   {
/* 480 */     int i = paramInputStream.read();
/*     */     
/* 482 */     if (-1 == i) {
/* 483 */       throw new EOFException("Unexpected EOF reached");
/*     */     }
/*     */     
/* 486 */     return i;
/*     */   }
/*     */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\org\apache\commons\io\EndianUtils.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */
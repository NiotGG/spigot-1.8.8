/*     */ package org.bukkit.util;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public final class NumberConversions
/*     */ {
/*     */   public static int floor(double num)
/*     */   {
/*  10 */     int floor = (int)num;
/*  11 */     return floor == num ? floor : floor - (int)(Double.doubleToRawLongBits(num) >>> 63);
/*     */   }
/*     */   
/*     */   public static int ceil(double num) {
/*  15 */     int floor = (int)num;
/*  16 */     return floor == num ? floor : floor + (int)((Double.doubleToRawLongBits(num) ^ 0xFFFFFFFFFFFFFFFF) >>> 63);
/*     */   }
/*     */   
/*     */   public static int round(double num) {
/*  20 */     return floor(num + 0.5D);
/*     */   }
/*     */   
/*     */   public static double square(double num) {
/*  24 */     return num * num;
/*     */   }
/*     */   
/*     */   public static int toInt(Object object) {
/*  28 */     if ((object instanceof Number)) {
/*  29 */       return ((Number)object).intValue();
/*     */     }
/*     */     try
/*     */     {
/*  33 */       return Integer.valueOf(object.toString()).intValue();
/*     */     }
/*     */     catch (NumberFormatException localNumberFormatException) {}catch (NullPointerException localNullPointerException) {}
/*     */     
/*  37 */     return 0;
/*     */   }
/*     */   
/*     */   public static float toFloat(Object object) {
/*  41 */     if ((object instanceof Number)) {
/*  42 */       return ((Number)object).floatValue();
/*     */     }
/*     */     try
/*     */     {
/*  46 */       return Float.valueOf(object.toString()).floatValue();
/*     */     }
/*     */     catch (NumberFormatException localNumberFormatException) {}catch (NullPointerException localNullPointerException) {}
/*     */     
/*  50 */     return 0.0F;
/*     */   }
/*     */   
/*     */   public static double toDouble(Object object) {
/*  54 */     if ((object instanceof Number)) {
/*  55 */       return ((Number)object).doubleValue();
/*     */     }
/*     */     try
/*     */     {
/*  59 */       return Double.valueOf(object.toString()).doubleValue();
/*     */     }
/*     */     catch (NumberFormatException localNumberFormatException) {}catch (NullPointerException localNullPointerException) {}
/*     */     
/*  63 */     return 0.0D;
/*     */   }
/*     */   
/*     */   public static long toLong(Object object) {
/*  67 */     if ((object instanceof Number)) {
/*  68 */       return ((Number)object).longValue();
/*     */     }
/*     */     try
/*     */     {
/*  72 */       return Long.valueOf(object.toString()).longValue();
/*     */     }
/*     */     catch (NumberFormatException localNumberFormatException) {}catch (NullPointerException localNullPointerException) {}
/*     */     
/*  76 */     return 0L;
/*     */   }
/*     */   
/*     */   public static short toShort(Object object) {
/*  80 */     if ((object instanceof Number)) {
/*  81 */       return ((Number)object).shortValue();
/*     */     }
/*     */     try
/*     */     {
/*  85 */       return Short.valueOf(object.toString()).shortValue();
/*     */     }
/*     */     catch (NumberFormatException localNumberFormatException) {}catch (NullPointerException localNullPointerException) {}
/*     */     
/*  89 */     return 0;
/*     */   }
/*     */   
/*     */   public static byte toByte(Object object) {
/*  93 */     if ((object instanceof Number)) {
/*  94 */       return ((Number)object).byteValue();
/*     */     }
/*     */     try
/*     */     {
/*  98 */       return Byte.valueOf(object.toString()).byteValue();
/*     */     }
/*     */     catch (NumberFormatException localNumberFormatException) {}catch (NullPointerException localNullPointerException) {}
/*     */     
/* 102 */     return 0;
/*     */   }
/*     */   
/*     */   public static boolean isFinite(double d) {
/* 106 */     return Math.abs(d) <= Double.MAX_VALUE;
/*     */   }
/*     */   
/*     */   public static boolean isFinite(float f) {
/* 110 */     return Math.abs(f) <= Float.MAX_VALUE;
/*     */   }
/*     */   
/*     */   public static void checkFinite(double d, String message) {
/* 114 */     if (!isFinite(d)) {
/* 115 */       throw new IllegalArgumentException(message);
/*     */     }
/*     */   }
/*     */   
/*     */   public static void checkFinite(float d, String message) {
/* 120 */     if (!isFinite(d)) {
/* 121 */       throw new IllegalArgumentException(message);
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\org\bukkit\util\NumberConversions.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */
/*     */ package io.netty.util.internal.logging;
/*     */ 
/*     */ import java.io.PrintStream;
/*     */ import java.util.HashMap;
/*     */ import java.util.Map;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ final class MessageFormatter
/*     */ {
/*     */   static final char DELIM_START = '{';
/*     */   static final char DELIM_STOP = '}';
/*     */   static final String DELIM_STR = "{}";
/*     */   private static final char ESCAPE_CHAR = '\\';
/*     */   
/*     */   static FormattingTuple format(String paramString, Object paramObject)
/*     */   {
/* 135 */     return arrayFormat(paramString, new Object[] { paramObject });
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
/*     */   static FormattingTuple format(String paramString, Object paramObject1, Object paramObject2)
/*     */   {
/* 159 */     return arrayFormat(paramString, new Object[] { paramObject1, paramObject2 });
/*     */   }
/*     */   
/*     */   static Throwable getThrowableCandidate(Object[] paramArrayOfObject) {
/* 163 */     if ((paramArrayOfObject == null) || (paramArrayOfObject.length == 0)) {
/* 164 */       return null;
/*     */     }
/*     */     
/* 167 */     Object localObject = paramArrayOfObject[(paramArrayOfObject.length - 1)];
/* 168 */     if ((localObject instanceof Throwable)) {
/* 169 */       return (Throwable)localObject;
/*     */     }
/* 171 */     return null;
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
/*     */   static FormattingTuple arrayFormat(String paramString, Object[] paramArrayOfObject)
/*     */   {
/* 187 */     Throwable localThrowable = getThrowableCandidate(paramArrayOfObject);
/*     */     
/* 189 */     if (paramString == null) {
/* 190 */       return new FormattingTuple(null, paramArrayOfObject, localThrowable);
/*     */     }
/*     */     
/* 193 */     if (paramArrayOfObject == null) {
/* 194 */       return new FormattingTuple(paramString);
/*     */     }
/*     */     
/* 197 */     int i = 0;
/*     */     
/* 199 */     StringBuffer localStringBuffer = new StringBuffer(paramString.length() + 50);
/*     */     
/*     */ 
/* 202 */     for (int j = 0; j < paramArrayOfObject.length; j++)
/*     */     {
/* 204 */       int k = paramString.indexOf("{}", i);
/*     */       
/* 206 */       if (k == -1)
/*     */       {
/* 208 */         if (i == 0) {
/* 209 */           return new FormattingTuple(paramString, paramArrayOfObject, localThrowable);
/*     */         }
/*     */         
/*     */ 
/* 213 */         localStringBuffer.append(paramString.substring(i, paramString.length()));
/* 214 */         return new FormattingTuple(localStringBuffer.toString(), paramArrayOfObject, localThrowable);
/*     */       }
/*     */       
/*     */ 
/* 218 */       if (isEscapedDelimeter(paramString, k)) {
/* 219 */         if (!isDoubleEscaped(paramString, k)) {
/* 220 */           j--;
/* 221 */           localStringBuffer.append(paramString.substring(i, k - 1));
/* 222 */           localStringBuffer.append('{');
/* 223 */           i = k + 1;
/*     */ 
/*     */         }
/*     */         else
/*     */         {
/* 228 */           localStringBuffer.append(paramString.substring(i, k - 1));
/* 229 */           deeplyAppendParameter(localStringBuffer, paramArrayOfObject[j], new HashMap());
/* 230 */           i = k + 2;
/*     */         }
/*     */       }
/*     */       else {
/* 234 */         localStringBuffer.append(paramString.substring(i, k));
/* 235 */         deeplyAppendParameter(localStringBuffer, paramArrayOfObject[j], new HashMap());
/* 236 */         i = k + 2;
/*     */       }
/*     */     }
/*     */     
/*     */ 
/* 241 */     localStringBuffer.append(paramString.substring(i, paramString.length()));
/* 242 */     if (j < paramArrayOfObject.length - 1) {
/* 243 */       return new FormattingTuple(localStringBuffer.toString(), paramArrayOfObject, localThrowable);
/*     */     }
/* 245 */     return new FormattingTuple(localStringBuffer.toString(), paramArrayOfObject, null);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   static boolean isEscapedDelimeter(String paramString, int paramInt)
/*     */   {
/* 252 */     if (paramInt == 0) {
/* 253 */       return false;
/*     */     }
/* 255 */     return paramString.charAt(paramInt - 1) == '\\';
/*     */   }
/*     */   
/*     */   static boolean isDoubleEscaped(String paramString, int paramInt)
/*     */   {
/* 260 */     return (paramInt >= 2) && (paramString.charAt(paramInt - 2) == '\\');
/*     */   }
/*     */   
/*     */ 
/*     */   private static void deeplyAppendParameter(StringBuffer paramStringBuffer, Object paramObject, Map<Object[], Void> paramMap)
/*     */   {
/* 266 */     if (paramObject == null) {
/* 267 */       paramStringBuffer.append("null");
/* 268 */       return;
/*     */     }
/* 270 */     if (!paramObject.getClass().isArray()) {
/* 271 */       safeObjectAppend(paramStringBuffer, paramObject);
/*     */ 
/*     */ 
/*     */     }
/* 275 */     else if ((paramObject instanceof boolean[])) {
/* 276 */       booleanArrayAppend(paramStringBuffer, (boolean[])paramObject);
/* 277 */     } else if ((paramObject instanceof byte[])) {
/* 278 */       byteArrayAppend(paramStringBuffer, (byte[])paramObject);
/* 279 */     } else if ((paramObject instanceof char[])) {
/* 280 */       charArrayAppend(paramStringBuffer, (char[])paramObject);
/* 281 */     } else if ((paramObject instanceof short[])) {
/* 282 */       shortArrayAppend(paramStringBuffer, (short[])paramObject);
/* 283 */     } else if ((paramObject instanceof int[])) {
/* 284 */       intArrayAppend(paramStringBuffer, (int[])paramObject);
/* 285 */     } else if ((paramObject instanceof long[])) {
/* 286 */       longArrayAppend(paramStringBuffer, (long[])paramObject);
/* 287 */     } else if ((paramObject instanceof float[])) {
/* 288 */       floatArrayAppend(paramStringBuffer, (float[])paramObject);
/* 289 */     } else if ((paramObject instanceof double[])) {
/* 290 */       doubleArrayAppend(paramStringBuffer, (double[])paramObject);
/*     */     } else {
/* 292 */       objectArrayAppend(paramStringBuffer, (Object[])paramObject, paramMap);
/*     */     }
/*     */   }
/*     */   
/*     */   private static void safeObjectAppend(StringBuffer paramStringBuffer, Object paramObject)
/*     */   {
/*     */     try {
/* 299 */       String str = paramObject.toString();
/* 300 */       paramStringBuffer.append(str);
/*     */     } catch (Throwable localThrowable) {
/* 302 */       System.err.println("SLF4J: Failed toString() invocation on an object of type [" + paramObject.getClass().getName() + ']');
/*     */       
/*     */ 
/* 305 */       localThrowable.printStackTrace();
/* 306 */       paramStringBuffer.append("[FAILED toString()]");
/*     */     }
/*     */   }
/*     */   
/*     */   private static void objectArrayAppend(StringBuffer paramStringBuffer, Object[] paramArrayOfObject, Map<Object[], Void> paramMap)
/*     */   {
/* 312 */     paramStringBuffer.append('[');
/* 313 */     if (!paramMap.containsKey(paramArrayOfObject)) {
/* 314 */       paramMap.put(paramArrayOfObject, null);
/* 315 */       int i = paramArrayOfObject.length;
/* 316 */       for (int j = 0; j < i; j++) {
/* 317 */         deeplyAppendParameter(paramStringBuffer, paramArrayOfObject[j], paramMap);
/* 318 */         if (j != i - 1) {
/* 319 */           paramStringBuffer.append(", ");
/*     */         }
/*     */       }
/*     */       
/* 323 */       paramMap.remove(paramArrayOfObject);
/*     */     } else {
/* 325 */       paramStringBuffer.append("...");
/*     */     }
/* 327 */     paramStringBuffer.append(']');
/*     */   }
/*     */   
/*     */   private static void booleanArrayAppend(StringBuffer paramStringBuffer, boolean[] paramArrayOfBoolean) {
/* 331 */     paramStringBuffer.append('[');
/* 332 */     int i = paramArrayOfBoolean.length;
/* 333 */     for (int j = 0; j < i; j++) {
/* 334 */       paramStringBuffer.append(paramArrayOfBoolean[j]);
/* 335 */       if (j != i - 1) {
/* 336 */         paramStringBuffer.append(", ");
/*     */       }
/*     */     }
/* 339 */     paramStringBuffer.append(']');
/*     */   }
/*     */   
/*     */   private static void byteArrayAppend(StringBuffer paramStringBuffer, byte[] paramArrayOfByte) {
/* 343 */     paramStringBuffer.append('[');
/* 344 */     int i = paramArrayOfByte.length;
/* 345 */     for (int j = 0; j < i; j++) {
/* 346 */       paramStringBuffer.append(paramArrayOfByte[j]);
/* 347 */       if (j != i - 1) {
/* 348 */         paramStringBuffer.append(", ");
/*     */       }
/*     */     }
/* 351 */     paramStringBuffer.append(']');
/*     */   }
/*     */   
/*     */   private static void charArrayAppend(StringBuffer paramStringBuffer, char[] paramArrayOfChar) {
/* 355 */     paramStringBuffer.append('[');
/* 356 */     int i = paramArrayOfChar.length;
/* 357 */     for (int j = 0; j < i; j++) {
/* 358 */       paramStringBuffer.append(paramArrayOfChar[j]);
/* 359 */       if (j != i - 1) {
/* 360 */         paramStringBuffer.append(", ");
/*     */       }
/*     */     }
/* 363 */     paramStringBuffer.append(']');
/*     */   }
/*     */   
/*     */   private static void shortArrayAppend(StringBuffer paramStringBuffer, short[] paramArrayOfShort) {
/* 367 */     paramStringBuffer.append('[');
/* 368 */     int i = paramArrayOfShort.length;
/* 369 */     for (int j = 0; j < i; j++) {
/* 370 */       paramStringBuffer.append(paramArrayOfShort[j]);
/* 371 */       if (j != i - 1) {
/* 372 */         paramStringBuffer.append(", ");
/*     */       }
/*     */     }
/* 375 */     paramStringBuffer.append(']');
/*     */   }
/*     */   
/*     */   private static void intArrayAppend(StringBuffer paramStringBuffer, int[] paramArrayOfInt) {
/* 379 */     paramStringBuffer.append('[');
/* 380 */     int i = paramArrayOfInt.length;
/* 381 */     for (int j = 0; j < i; j++) {
/* 382 */       paramStringBuffer.append(paramArrayOfInt[j]);
/* 383 */       if (j != i - 1) {
/* 384 */         paramStringBuffer.append(", ");
/*     */       }
/*     */     }
/* 387 */     paramStringBuffer.append(']');
/*     */   }
/*     */   
/*     */   private static void longArrayAppend(StringBuffer paramStringBuffer, long[] paramArrayOfLong) {
/* 391 */     paramStringBuffer.append('[');
/* 392 */     int i = paramArrayOfLong.length;
/* 393 */     for (int j = 0; j < i; j++) {
/* 394 */       paramStringBuffer.append(paramArrayOfLong[j]);
/* 395 */       if (j != i - 1) {
/* 396 */         paramStringBuffer.append(", ");
/*     */       }
/*     */     }
/* 399 */     paramStringBuffer.append(']');
/*     */   }
/*     */   
/*     */   private static void floatArrayAppend(StringBuffer paramStringBuffer, float[] paramArrayOfFloat) {
/* 403 */     paramStringBuffer.append('[');
/* 404 */     int i = paramArrayOfFloat.length;
/* 405 */     for (int j = 0; j < i; j++) {
/* 406 */       paramStringBuffer.append(paramArrayOfFloat[j]);
/* 407 */       if (j != i - 1) {
/* 408 */         paramStringBuffer.append(", ");
/*     */       }
/*     */     }
/* 411 */     paramStringBuffer.append(']');
/*     */   }
/*     */   
/*     */   private static void doubleArrayAppend(StringBuffer paramStringBuffer, double[] paramArrayOfDouble) {
/* 415 */     paramStringBuffer.append('[');
/* 416 */     int i = paramArrayOfDouble.length;
/* 417 */     for (int j = 0; j < i; j++) {
/* 418 */       paramStringBuffer.append(paramArrayOfDouble[j]);
/* 419 */       if (j != i - 1) {
/* 420 */         paramStringBuffer.append(", ");
/*     */       }
/*     */     }
/* 423 */     paramStringBuffer.append(']');
/*     */   }
/*     */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\io\netty\util\internal\logging\MessageFormatter.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */
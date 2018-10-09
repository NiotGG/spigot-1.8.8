/*     */ package io.netty.util.internal;
/*     */ 
/*     */ import io.netty.util.CharsetUtil;
/*     */ import io.netty.util.internal.chmv8.ConcurrentHashMapV8;
/*     */ import io.netty.util.internal.logging.InternalLogger;
/*     */ import io.netty.util.internal.logging.InternalLoggerFactory;
/*     */ import java.io.BufferedReader;
/*     */ import java.io.File;
/*     */ import java.io.IOException;
/*     */ import java.io.InputStreamReader;
/*     */ import java.lang.reflect.Field;
/*     */ import java.lang.reflect.Method;
/*     */ import java.net.InetSocketAddress;
/*     */ import java.net.ServerSocket;
/*     */ import java.nio.ByteBuffer;
/*     */ import java.util.List;
/*     */ import java.util.Locale;
/*     */ import java.util.Map;
/*     */ import java.util.Queue;
/*     */ import java.util.concurrent.BlockingQueue;
/*     */ import java.util.concurrent.ConcurrentHashMap;
/*     */ import java.util.concurrent.ConcurrentMap;
/*     */ import java.util.concurrent.atomic.AtomicIntegerFieldUpdater;
/*     */ import java.util.concurrent.atomic.AtomicLongFieldUpdater;
/*     */ import java.util.concurrent.atomic.AtomicReferenceFieldUpdater;
/*     */ import java.util.regex.Matcher;
/*     */ import java.util.regex.Pattern;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public final class PlatformDependent
/*     */ {
/*  56 */   private static final InternalLogger logger = InternalLoggerFactory.getInstance(PlatformDependent.class);
/*     */   
/*  58 */   private static final Pattern MAX_DIRECT_MEMORY_SIZE_ARG_PATTERN = Pattern.compile("\\s*-XX:MaxDirectMemorySize\\s*=\\s*([0-9]+)\\s*([kKmMgG]?)\\s*$");
/*     */   
/*     */ 
/*  61 */   private static final boolean IS_ANDROID = isAndroid0();
/*  62 */   private static final boolean IS_WINDOWS = isWindows0();
/*  63 */   private static final boolean IS_ROOT = isRoot0();
/*     */   
/*  65 */   private static final int JAVA_VERSION = javaVersion0();
/*     */   
/*  67 */   private static final boolean CAN_ENABLE_TCP_NODELAY_BY_DEFAULT = !isAndroid();
/*     */   
/*  69 */   private static final boolean HAS_UNSAFE = hasUnsafe0();
/*  70 */   private static final boolean CAN_USE_CHM_V8 = (HAS_UNSAFE) && (JAVA_VERSION < 8);
/*  71 */   private static final boolean DIRECT_BUFFER_PREFERRED = (HAS_UNSAFE) && (!SystemPropertyUtil.getBoolean("io.netty.noPreferDirect", false));
/*     */   
/*  73 */   private static final long MAX_DIRECT_MEMORY = maxDirectMemory0();
/*     */   
/*  75 */   private static final long ARRAY_BASE_OFFSET = arrayBaseOffset0();
/*     */   
/*  77 */   private static final boolean HAS_JAVASSIST = hasJavassist0();
/*     */   
/*  79 */   private static final File TMPDIR = tmpdir0();
/*     */   
/*  81 */   private static final int BIT_MODE = bitMode0();
/*     */   
/*  83 */   private static final int ADDRESS_SIZE = addressSize0();
/*     */   
/*     */   static {
/*  86 */     if (logger.isDebugEnabled()) {
/*  87 */       logger.debug("-Dio.netty.noPreferDirect: {}", Boolean.valueOf(!DIRECT_BUFFER_PREFERRED));
/*     */     }
/*     */     
/*  90 */     if ((!hasUnsafe()) && (!isAndroid())) {
/*  91 */       logger.info("Your platform does not provide complete low-level API for accessing direct buffers reliably. Unless explicitly requested, heap buffer will always be preferred to avoid potential system unstability.");
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public static boolean isAndroid()
/*     */   {
/* 102 */     return IS_ANDROID;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public static boolean isWindows()
/*     */   {
/* 109 */     return IS_WINDOWS;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public static boolean isRoot()
/*     */   {
/* 117 */     return IS_ROOT;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public static int javaVersion()
/*     */   {
/* 124 */     return JAVA_VERSION;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public static boolean canEnableTcpNoDelayByDefault()
/*     */   {
/* 131 */     return CAN_ENABLE_TCP_NODELAY_BY_DEFAULT;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public static boolean hasUnsafe()
/*     */   {
/* 139 */     return HAS_UNSAFE;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public static boolean directBufferPreferred()
/*     */   {
/* 147 */     return DIRECT_BUFFER_PREFERRED;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public static long maxDirectMemory()
/*     */   {
/* 154 */     return MAX_DIRECT_MEMORY;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public static boolean hasJavassist()
/*     */   {
/* 161 */     return HAS_JAVASSIST;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public static File tmpdir()
/*     */   {
/* 168 */     return TMPDIR;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public static int bitMode()
/*     */   {
/* 175 */     return BIT_MODE;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public static int addressSize()
/*     */   {
/* 183 */     return ADDRESS_SIZE;
/*     */   }
/*     */   
/*     */   public static long allocateMemory(long paramLong) {
/* 187 */     return PlatformDependent0.allocateMemory(paramLong);
/*     */   }
/*     */   
/*     */   public static void freeMemory(long paramLong) {
/* 191 */     PlatformDependent0.freeMemory(paramLong);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public static void throwException(Throwable paramThrowable)
/*     */   {
/* 198 */     if (hasUnsafe()) {
/* 199 */       PlatformDependent0.throwException(paramThrowable);
/*     */     } else {
/* 201 */       throwException0(paramThrowable);
/*     */     }
/*     */   }
/*     */   
/*     */   private static <E extends Throwable> void throwException0(Throwable paramThrowable) throws Throwable
/*     */   {
/* 207 */     throw paramThrowable;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public static <K, V> ConcurrentMap<K, V> newConcurrentHashMap()
/*     */   {
/* 214 */     if (CAN_USE_CHM_V8) {
/* 215 */       return new ConcurrentHashMapV8();
/*     */     }
/* 217 */     return new ConcurrentHashMap();
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public static <K, V> ConcurrentMap<K, V> newConcurrentHashMap(int paramInt)
/*     */   {
/* 225 */     if (CAN_USE_CHM_V8) {
/* 226 */       return new ConcurrentHashMapV8(paramInt);
/*     */     }
/* 228 */     return new ConcurrentHashMap(paramInt);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public static <K, V> ConcurrentMap<K, V> newConcurrentHashMap(int paramInt, float paramFloat)
/*     */   {
/* 236 */     if (CAN_USE_CHM_V8) {
/* 237 */       return new ConcurrentHashMapV8(paramInt, paramFloat);
/*     */     }
/* 239 */     return new ConcurrentHashMap(paramInt, paramFloat);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public static <K, V> ConcurrentMap<K, V> newConcurrentHashMap(int paramInt1, float paramFloat, int paramInt2)
/*     */   {
/* 248 */     if (CAN_USE_CHM_V8) {
/* 249 */       return new ConcurrentHashMapV8(paramInt1, paramFloat, paramInt2);
/*     */     }
/* 251 */     return new ConcurrentHashMap(paramInt1, paramFloat, paramInt2);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public static <K, V> ConcurrentMap<K, V> newConcurrentHashMap(Map<? extends K, ? extends V> paramMap)
/*     */   {
/* 259 */     if (CAN_USE_CHM_V8) {
/* 260 */       return new ConcurrentHashMapV8(paramMap);
/*     */     }
/* 262 */     return new ConcurrentHashMap(paramMap);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public static void freeDirectBuffer(ByteBuffer paramByteBuffer)
/*     */   {
/* 271 */     if ((hasUnsafe()) && (!isAndroid()))
/*     */     {
/*     */ 
/* 274 */       PlatformDependent0.freeDirectBuffer(paramByteBuffer);
/*     */     }
/*     */   }
/*     */   
/*     */   public static long directBufferAddress(ByteBuffer paramByteBuffer) {
/* 279 */     return PlatformDependent0.directBufferAddress(paramByteBuffer);
/*     */   }
/*     */   
/*     */   public static Object getObject(Object paramObject, long paramLong) {
/* 283 */     return PlatformDependent0.getObject(paramObject, paramLong);
/*     */   }
/*     */   
/*     */   public static Object getObjectVolatile(Object paramObject, long paramLong) {
/* 287 */     return PlatformDependent0.getObjectVolatile(paramObject, paramLong);
/*     */   }
/*     */   
/*     */   public static int getInt(Object paramObject, long paramLong) {
/* 291 */     return PlatformDependent0.getInt(paramObject, paramLong);
/*     */   }
/*     */   
/*     */   public static long objectFieldOffset(Field paramField) {
/* 295 */     return PlatformDependent0.objectFieldOffset(paramField);
/*     */   }
/*     */   
/*     */   public static byte getByte(long paramLong) {
/* 299 */     return PlatformDependent0.getByte(paramLong);
/*     */   }
/*     */   
/*     */   public static short getShort(long paramLong) {
/* 303 */     return PlatformDependent0.getShort(paramLong);
/*     */   }
/*     */   
/*     */   public static int getInt(long paramLong) {
/* 307 */     return PlatformDependent0.getInt(paramLong);
/*     */   }
/*     */   
/*     */   public static long getLong(long paramLong) {
/* 311 */     return PlatformDependent0.getLong(paramLong);
/*     */   }
/*     */   
/*     */   public static void putOrderedObject(Object paramObject1, long paramLong, Object paramObject2) {
/* 315 */     PlatformDependent0.putOrderedObject(paramObject1, paramLong, paramObject2);
/*     */   }
/*     */   
/*     */   public static void putByte(long paramLong, byte paramByte) {
/* 319 */     PlatformDependent0.putByte(paramLong, paramByte);
/*     */   }
/*     */   
/*     */   public static void putShort(long paramLong, short paramShort) {
/* 323 */     PlatformDependent0.putShort(paramLong, paramShort);
/*     */   }
/*     */   
/*     */   public static void putInt(long paramLong, int paramInt) {
/* 327 */     PlatformDependent0.putInt(paramLong, paramInt);
/*     */   }
/*     */   
/*     */   public static void putLong(long paramLong1, long paramLong2) {
/* 331 */     PlatformDependent0.putLong(paramLong1, paramLong2);
/*     */   }
/*     */   
/*     */   public static void copyMemory(long paramLong1, long paramLong2, long paramLong3) {
/* 335 */     PlatformDependent0.copyMemory(paramLong1, paramLong2, paramLong3);
/*     */   }
/*     */   
/*     */   public static void copyMemory(byte[] paramArrayOfByte, int paramInt, long paramLong1, long paramLong2) {
/* 339 */     PlatformDependent0.copyMemory(paramArrayOfByte, ARRAY_BASE_OFFSET + paramInt, null, paramLong1, paramLong2);
/*     */   }
/*     */   
/*     */   public static void copyMemory(long paramLong1, byte[] paramArrayOfByte, int paramInt, long paramLong2) {
/* 343 */     PlatformDependent0.copyMemory(null, paramLong1, paramArrayOfByte, ARRAY_BASE_OFFSET + paramInt, paramLong2);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public static <U, W> AtomicReferenceFieldUpdater<U, W> newAtomicReferenceFieldUpdater(Class<U> paramClass, String paramString)
/*     */   {
/* 353 */     if (hasUnsafe()) {
/*     */       try {
/* 355 */         return PlatformDependent0.newAtomicReferenceFieldUpdater(paramClass, paramString);
/*     */       }
/*     */       catch (Throwable localThrowable) {}
/*     */     }
/*     */     
/* 360 */     return null;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public static <T> AtomicIntegerFieldUpdater<T> newAtomicIntegerFieldUpdater(Class<?> paramClass, String paramString)
/*     */   {
/* 370 */     if (hasUnsafe()) {
/*     */       try {
/* 372 */         return PlatformDependent0.newAtomicIntegerFieldUpdater(paramClass, paramString);
/*     */       }
/*     */       catch (Throwable localThrowable) {}
/*     */     }
/*     */     
/* 377 */     return null;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public static <T> AtomicLongFieldUpdater<T> newAtomicLongFieldUpdater(Class<?> paramClass, String paramString)
/*     */   {
/* 387 */     if (hasUnsafe()) {
/*     */       try {
/* 389 */         return PlatformDependent0.newAtomicLongFieldUpdater(paramClass, paramString);
/*     */       }
/*     */       catch (Throwable localThrowable) {}
/*     */     }
/*     */     
/* 394 */     return null;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public static <T> Queue<T> newMpscQueue()
/*     */   {
/* 402 */     return new MpscLinkedQueue();
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public static ClassLoader getClassLoader(Class<?> paramClass)
/*     */   {
/* 409 */     return PlatformDependent0.getClassLoader(paramClass);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public static ClassLoader getContextClassLoader()
/*     */   {
/* 416 */     return PlatformDependent0.getContextClassLoader();
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public static ClassLoader getSystemClassLoader()
/*     */   {
/* 423 */     return PlatformDependent0.getSystemClassLoader();
/*     */   }
/*     */   
/*     */   private static boolean isAndroid0() {
/*     */     boolean bool;
/*     */     try {
/* 429 */       Class.forName("android.app.Application", false, getSystemClassLoader());
/* 430 */       bool = true;
/*     */     }
/*     */     catch (Exception localException) {
/* 433 */       bool = false;
/*     */     }
/*     */     
/* 436 */     if (bool) {
/* 437 */       logger.debug("Platform: Android");
/*     */     }
/* 439 */     return bool;
/*     */   }
/*     */   
/*     */   private static boolean isWindows0() {
/* 443 */     boolean bool = SystemPropertyUtil.get("os.name", "").toLowerCase(Locale.US).contains("win");
/* 444 */     if (bool) {
/* 445 */       logger.debug("Platform: Windows");
/*     */     }
/* 447 */     return bool;
/*     */   }
/*     */   
/*     */   private static boolean isRoot0() {
/* 451 */     if (isWindows()) {
/* 452 */       return false;
/*     */     }
/*     */     
/* 455 */     String[] arrayOfString = { "/usr/bin/id", "/bin/id", "id", "/usr/xpg4/bin/id" };
/* 456 */     Pattern localPattern = Pattern.compile("^(?:0|[1-9][0-9]*)$");
/* 457 */     for (Object localObject2 : arrayOfString) {
/* 458 */       Process localProcess = null;
/* 459 */       BufferedReader localBufferedReader = null;
/* 460 */       String str2 = null;
/*     */       try {
/* 462 */         localProcess = Runtime.getRuntime().exec(new String[] { localObject2, "-u" });
/* 463 */         localBufferedReader = new BufferedReader(new InputStreamReader(localProcess.getInputStream(), CharsetUtil.US_ASCII));
/* 464 */         str2 = localBufferedReader.readLine();
/* 465 */         localBufferedReader.close();
/*     */         for (;;)
/*     */         {
/*     */           try {
/* 469 */             int k = localProcess.waitFor();
/* 470 */             if (k != 0) {
/* 471 */               str2 = null;
/*     */             }
/*     */           }
/*     */           catch (InterruptedException localInterruptedException) {}
/*     */         }
/*     */         
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/* 482 */         if (localBufferedReader != null) {
/*     */           try {
/* 484 */             localBufferedReader.close();
/*     */           }
/*     */           catch (IOException localIOException1) {}
/*     */         }
/*     */         
/* 489 */         if (localProcess != null) {
/*     */           try {
/* 491 */             localProcess.destroy();
/*     */           }
/*     */           catch (Exception localException5) {}
/*     */         }
/*     */         
/*     */ 
/*     */ 
/* 498 */         if (str2 == null) {
/*     */           continue;
/*     */         }
/*     */       }
/*     */       catch (Exception localException6)
/*     */       {
/* 480 */         str2 = null;
/*     */       } finally {
/* 482 */         if (localBufferedReader != null) {
/*     */           try {
/* 484 */             localBufferedReader.close();
/*     */           }
/*     */           catch (IOException localIOException3) {}
/*     */         }
/*     */         
/* 489 */         if (localProcess != null) {
/*     */           try {
/* 491 */             localProcess.destroy();
/*     */           }
/*     */           catch (Exception localException8) {}
/*     */         }
/*     */       }
/*     */       
/*     */ 
/* 498 */       if (localPattern.matcher(str2).matches()) {
/* 499 */         logger.debug("UID: {}", str2);
/* 500 */         return "0".equals(str2);
/*     */       }
/*     */     }
/*     */     
/* 504 */     logger.debug("Could not determine the current UID using /usr/bin/id; attempting to bind at privileged ports.");
/*     */     
/* 506 */     ??? = Pattern.compile(".*(?:denied|not.*permitted).*");
/* 507 */     for (??? = 1023; ??? > 0; ???--) {
/* 508 */       ServerSocket localServerSocket = null;
/*     */       try {
/* 510 */         localServerSocket = new ServerSocket();
/* 511 */         localServerSocket.setReuseAddress(true);
/* 512 */         localServerSocket.bind(new InetSocketAddress(???));
/* 513 */         if (logger.isDebugEnabled()) {
/* 514 */           logger.debug("UID: 0 (succeded to bind at port {})", Integer.valueOf(???));
/*     */         }
/* 516 */         return true;
/*     */       }
/*     */       catch (Exception localException1)
/*     */       {
/* 520 */         String str1 = localException1.getMessage();
/* 521 */         if (str1 == null) {
/* 522 */           str1 = "";
/*     */         }
/* 524 */         str1 = str1.toLowerCase();
/* 525 */         if (((Pattern)???).matcher(str1).matches())
/*     */         {
/*     */ 
/*     */ 
/* 529 */           if (localServerSocket == null)
/*     */             break;
/* 531 */           try { localServerSocket.close();
/*     */           }
/*     */           catch (Exception localException4) {}
/*     */         }
/*     */       }
/*     */       finally
/*     */       {
/* 529 */         if (localServerSocket != null) {
/*     */           try {
/* 531 */             localServerSocket.close();
/*     */           }
/*     */           catch (Exception localException9) {}
/*     */         }
/*     */       }
/*     */     }
/*     */     
/*     */ 
/* 539 */     logger.debug("UID: non-root (failed to bind at any privileged ports)");
/* 540 */     return false;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   private static int javaVersion0()
/*     */   {
/*     */     int i;
/*     */     
/*     */ 
/* 550 */     if (isAndroid()) {
/* 551 */       i = 6;
/*     */     }
/*     */     else {
/*     */       try
/*     */       {
/* 556 */         Class.forName("java.time.Clock", false, getClassLoader(Object.class));
/* 557 */         i = 8;
/*     */ 
/*     */       }
/*     */       catch (Exception localException1)
/*     */       {
/*     */         try
/*     */         {
/* 564 */           Class.forName("java.util.concurrent.LinkedTransferQueue", false, getClassLoader(BlockingQueue.class));
/* 565 */           i = 7;
/*     */ 
/*     */         }
/*     */         catch (Exception localException2)
/*     */         {
/*     */ 
/* 571 */           i = 6;
/*     */         }
/*     */       }
/*     */     }
/* 575 */     if (logger.isDebugEnabled()) {
/* 576 */       logger.debug("Java version: {}", Integer.valueOf(i));
/*     */     }
/* 578 */     return i;
/*     */   }
/*     */   
/*     */   private static boolean hasUnsafe0() {
/* 582 */     boolean bool1 = SystemPropertyUtil.getBoolean("io.netty.noUnsafe", false);
/* 583 */     logger.debug("-Dio.netty.noUnsafe: {}", Boolean.valueOf(bool1));
/*     */     
/* 585 */     if (isAndroid()) {
/* 586 */       logger.debug("sun.misc.Unsafe: unavailable (Android)");
/* 587 */       return false;
/*     */     }
/*     */     
/* 590 */     if (bool1) {
/* 591 */       logger.debug("sun.misc.Unsafe: unavailable (io.netty.noUnsafe)");
/* 592 */       return false;
/*     */     }
/*     */     
/*     */     boolean bool2;
/*     */     
/* 597 */     if (SystemPropertyUtil.contains("io.netty.tryUnsafe")) {
/* 598 */       bool2 = SystemPropertyUtil.getBoolean("io.netty.tryUnsafe", true);
/*     */     } else {
/* 600 */       bool2 = SystemPropertyUtil.getBoolean("org.jboss.netty.tryUnsafe", true);
/*     */     }
/*     */     
/* 603 */     if (!bool2) {
/* 604 */       logger.debug("sun.misc.Unsafe: unavailable (io.netty.tryUnsafe/org.jboss.netty.tryUnsafe)");
/* 605 */       return false;
/*     */     }
/*     */     try
/*     */     {
/* 609 */       boolean bool3 = PlatformDependent0.hasUnsafe();
/* 610 */       logger.debug("sun.misc.Unsafe: {}", bool3 ? "available" : "unavailable");
/* 611 */       return bool3;
/*     */     }
/*     */     catch (Throwable localThrowable) {}
/* 614 */     return false;
/*     */   }
/*     */   
/*     */   private static long arrayBaseOffset0()
/*     */   {
/* 619 */     if (!hasUnsafe()) {
/* 620 */       return -1L;
/*     */     }
/*     */     
/* 623 */     return PlatformDependent0.arrayBaseOffset();
/*     */   }
/*     */   
/*     */   private static long maxDirectMemory0() {
/* 627 */     long l = 0L;
/*     */     Object localObject1;
/*     */     try {
/* 630 */       Class localClass1 = Class.forName("sun.misc.VM", true, getSystemClassLoader());
/* 631 */       localObject1 = localClass1.getDeclaredMethod("maxDirectMemory", new Class[0]);
/* 632 */       l = ((Number)((Method)localObject1).invoke(null, new Object[0])).longValue();
/*     */     }
/*     */     catch (Throwable localThrowable1) {}
/*     */     
/*     */ 
/* 637 */     if (l > 0L) {
/* 638 */       return l;
/*     */     }
/*     */     
/*     */ 
/*     */     try
/*     */     {
/* 644 */       Class localClass2 = Class.forName("java.lang.management.ManagementFactory", true, getSystemClassLoader());
/*     */       
/* 646 */       localObject1 = Class.forName("java.lang.management.RuntimeMXBean", true, getSystemClassLoader());
/*     */       
/*     */ 
/* 649 */       Object localObject2 = localClass2.getDeclaredMethod("getRuntimeMXBean", new Class[0]).invoke(null, new Object[0]);
/*     */       
/*     */ 
/* 652 */       List localList = (List)((Class)localObject1).getDeclaredMethod("getInputArguments", new Class[0]).invoke(localObject2, new Object[0]);
/* 653 */       for (int i = localList.size() - 1; i >= 0; i--) {
/* 654 */         Matcher localMatcher = MAX_DIRECT_MEMORY_SIZE_ARG_PATTERN.matcher((CharSequence)localList.get(i));
/* 655 */         if (localMatcher.matches())
/*     */         {
/*     */ 
/*     */ 
/* 659 */           l = Long.parseLong(localMatcher.group(1));
/* 660 */           switch (localMatcher.group(2).charAt(0)) {
/*     */           case 'K': case 'k': 
/* 662 */             l *= 1024L;
/* 663 */             break;
/*     */           case 'M': case 'm': 
/* 665 */             l *= 1048576L;
/* 666 */             break;
/*     */           case 'G': case 'g': 
/* 668 */             l *= 1073741824L;
/*     */           }
/*     */           
/* 671 */           break;
/*     */         }
/*     */       }
/*     */     }
/*     */     catch (Throwable localThrowable2) {}
/*     */     
/* 677 */     if (l <= 0L) {
/* 678 */       l = Runtime.getRuntime().maxMemory();
/* 679 */       logger.debug("maxDirectMemory: {} bytes (maybe)", Long.valueOf(l));
/*     */     } else {
/* 681 */       logger.debug("maxDirectMemory: {} bytes", Long.valueOf(l));
/*     */     }
/*     */     
/* 684 */     return l;
/*     */   }
/*     */   
/*     */   private static boolean hasJavassist0() {
/* 688 */     if (isAndroid()) {
/* 689 */       return false;
/*     */     }
/*     */     
/* 692 */     boolean bool = SystemPropertyUtil.getBoolean("io.netty.noJavassist", false);
/* 693 */     logger.debug("-Dio.netty.noJavassist: {}", Boolean.valueOf(bool));
/*     */     
/* 695 */     if (bool) {
/* 696 */       logger.debug("Javassist: unavailable (io.netty.noJavassist)");
/* 697 */       return false;
/*     */     }
/*     */     try
/*     */     {
/* 701 */       JavassistTypeParameterMatcherGenerator.generate(Object.class, getClassLoader(PlatformDependent.class));
/* 702 */       logger.debug("Javassist: available");
/* 703 */       return true;
/*     */     }
/*     */     catch (Throwable localThrowable) {
/* 706 */       logger.debug("Javassist: unavailable");
/* 707 */       logger.debug("You don't have Javassist in your class path or you don't have enough permission to load dynamically generated classes.  Please check the configuration for better performance.");
/*     */     }
/*     */     
/* 710 */     return false;
/*     */   }
/*     */   
/*     */   private static File tmpdir0()
/*     */   {
/*     */     File localFile;
/*     */     try {
/* 717 */       localFile = toDirectory(SystemPropertyUtil.get("io.netty.tmpdir"));
/* 718 */       if (localFile != null) {
/* 719 */         logger.debug("-Dio.netty.tmpdir: {}", localFile);
/* 720 */         return localFile;
/*     */       }
/*     */       
/* 723 */       localFile = toDirectory(SystemPropertyUtil.get("java.io.tmpdir"));
/* 724 */       if (localFile != null) {
/* 725 */         logger.debug("-Dio.netty.tmpdir: {} (java.io.tmpdir)", localFile);
/* 726 */         return localFile;
/*     */       }
/*     */       
/*     */ 
/* 730 */       if (isWindows()) {
/* 731 */         localFile = toDirectory(System.getenv("TEMP"));
/* 732 */         if (localFile != null) {
/* 733 */           logger.debug("-Dio.netty.tmpdir: {} (%TEMP%)", localFile);
/* 734 */           return localFile;
/*     */         }
/*     */         
/* 737 */         String str = System.getenv("USERPROFILE");
/* 738 */         if (str != null) {
/* 739 */           localFile = toDirectory(str + "\\AppData\\Local\\Temp");
/* 740 */           if (localFile != null) {
/* 741 */             logger.debug("-Dio.netty.tmpdir: {} (%USERPROFILE%\\AppData\\Local\\Temp)", localFile);
/* 742 */             return localFile;
/*     */           }
/*     */           
/* 745 */           localFile = toDirectory(str + "\\Local Settings\\Temp");
/* 746 */           if (localFile != null) {
/* 747 */             logger.debug("-Dio.netty.tmpdir: {} (%USERPROFILE%\\Local Settings\\Temp)", localFile);
/* 748 */             return localFile;
/*     */           }
/*     */         }
/*     */       } else {
/* 752 */         localFile = toDirectory(System.getenv("TMPDIR"));
/* 753 */         if (localFile != null) {
/* 754 */           logger.debug("-Dio.netty.tmpdir: {} ($TMPDIR)", localFile);
/* 755 */           return localFile;
/*     */         }
/*     */       }
/*     */     }
/*     */     catch (Exception localException) {}
/*     */     
/*     */ 
/*     */ 
/* 763 */     if (isWindows()) {
/* 764 */       localFile = new File("C:\\Windows\\Temp");
/*     */     } else {
/* 766 */       localFile = new File("/tmp");
/*     */     }
/*     */     
/* 769 */     logger.warn("Failed to get the temporary directory; falling back to: {}", localFile);
/* 770 */     return localFile;
/*     */   }
/*     */   
/*     */   private static File toDirectory(String paramString)
/*     */   {
/* 775 */     if (paramString == null) {
/* 776 */       return null;
/*     */     }
/*     */     
/* 779 */     File localFile = new File(paramString);
/* 780 */     localFile.mkdirs();
/*     */     
/* 782 */     if (!localFile.isDirectory()) {
/* 783 */       return null;
/*     */     }
/*     */     try
/*     */     {
/* 787 */       return localFile.getAbsoluteFile();
/*     */     } catch (Exception localException) {}
/* 789 */     return localFile;
/*     */   }
/*     */   
/*     */ 
/*     */   private static int bitMode0()
/*     */   {
/* 795 */     int i = SystemPropertyUtil.getInt("io.netty.bitMode", 0);
/* 796 */     if (i > 0) {
/* 797 */       logger.debug("-Dio.netty.bitMode: {}", Integer.valueOf(i));
/* 798 */       return i;
/*     */     }
/*     */     
/*     */ 
/* 802 */     i = SystemPropertyUtil.getInt("sun.arch.data.model", 0);
/* 803 */     if (i > 0) {
/* 804 */       logger.debug("-Dio.netty.bitMode: {} (sun.arch.data.model)", Integer.valueOf(i));
/* 805 */       return i;
/*     */     }
/* 807 */     i = SystemPropertyUtil.getInt("com.ibm.vm.bitmode", 0);
/* 808 */     if (i > 0) {
/* 809 */       logger.debug("-Dio.netty.bitMode: {} (com.ibm.vm.bitmode)", Integer.valueOf(i));
/* 810 */       return i;
/*     */     }
/*     */     
/*     */ 
/* 814 */     String str1 = SystemPropertyUtil.get("os.arch", "").toLowerCase(Locale.US).trim();
/* 815 */     if (("amd64".equals(str1)) || ("x86_64".equals(str1))) {
/* 816 */       i = 64;
/* 817 */     } else if (("i386".equals(str1)) || ("i486".equals(str1)) || ("i586".equals(str1)) || ("i686".equals(str1))) {
/* 818 */       i = 32;
/*     */     }
/*     */     
/* 821 */     if (i > 0) {
/* 822 */       logger.debug("-Dio.netty.bitMode: {} (os.arch: {})", Integer.valueOf(i), str1);
/*     */     }
/*     */     
/*     */ 
/* 826 */     String str2 = SystemPropertyUtil.get("java.vm.name", "").toLowerCase(Locale.US);
/* 827 */     Pattern localPattern = Pattern.compile("([1-9][0-9]+)-?bit");
/* 828 */     Matcher localMatcher = localPattern.matcher(str2);
/* 829 */     if (localMatcher.find()) {
/* 830 */       return Integer.parseInt(localMatcher.group(1));
/*     */     }
/* 832 */     return 64;
/*     */   }
/*     */   
/*     */   private static int addressSize0()
/*     */   {
/* 837 */     if (!hasUnsafe()) {
/* 838 */       return -1;
/*     */     }
/* 840 */     return PlatformDependent0.addressSize();
/*     */   }
/*     */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\io\netty\util\internal\PlatformDependent.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */
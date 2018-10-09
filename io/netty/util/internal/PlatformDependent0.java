/*     */ package io.netty.util.internal;
/*     */ 
/*     */ import io.netty.util.internal.logging.InternalLogger;
/*     */ import io.netty.util.internal.logging.InternalLoggerFactory;
/*     */ import java.lang.reflect.Field;
/*     */ import java.lang.reflect.Method;
/*     */ import java.nio.Buffer;
/*     */ import java.nio.ByteBuffer;
/*     */ import java.nio.ByteOrder;
/*     */ import java.security.AccessController;
/*     */ import java.security.PrivilegedAction;
/*     */ import java.util.concurrent.atomic.AtomicIntegerFieldUpdater;
/*     */ import java.util.concurrent.atomic.AtomicLongFieldUpdater;
/*     */ import java.util.concurrent.atomic.AtomicReferenceFieldUpdater;
/*     */ import sun.misc.Unsafe;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ final class PlatformDependent0
/*     */ {
/*  38 */   private static final InternalLogger logger = InternalLoggerFactory.getInstance(PlatformDependent0.class);
/*     */   private static final Unsafe UNSAFE;
/*  40 */   private static final boolean BIG_ENDIAN = ByteOrder.nativeOrder() == ByteOrder.BIG_ENDIAN;
/*     */   
/*     */ 
/*     */ 
/*     */   private static final long ADDRESS_FIELD_OFFSET;
/*     */   
/*     */ 
/*     */ 
/*     */   private static final long UNSAFE_COPY_THRESHOLD = 1048576L;
/*     */   
/*     */ 
/*     */   private static final boolean UNALIGNED;
/*     */   
/*     */ 
/*     */ 
/*     */   static
/*     */   {
/*  57 */     ByteBuffer localByteBuffer = ByteBuffer.allocateDirect(1);
/*     */     Field localField1;
/*     */     try {
/*  60 */       localField1 = Buffer.class.getDeclaredField("address");
/*  61 */       localField1.setAccessible(true);
/*  62 */       if (localField1.getLong(ByteBuffer.allocate(1)) != 0L)
/*     */       {
/*  64 */         localField1 = null;
/*     */       }
/*  66 */       else if (localField1.getLong(localByteBuffer) == 0L)
/*     */       {
/*  68 */         localField1 = null;
/*     */       }
/*     */     }
/*     */     catch (Throwable localThrowable1)
/*     */     {
/*  73 */       localField1 = null;
/*     */     }
/*  75 */     logger.debug("java.nio.Buffer.address: {}", localField1 != null ? "available" : "unavailable");
/*     */     
/*     */     Unsafe localUnsafe;
/*  78 */     if (localField1 != null) {
/*     */       try {
/*  80 */         Field localField2 = Unsafe.class.getDeclaredField("theUnsafe");
/*  81 */         localField2.setAccessible(true);
/*  82 */         localUnsafe = (Unsafe)localField2.get(null);
/*  83 */         logger.debug("sun.misc.Unsafe.theUnsafe: {}", localUnsafe != null ? "available" : "unavailable");
/*     */         
/*     */ 
/*     */ 
/*     */         try
/*     */         {
/*  89 */           if (localUnsafe != null) {
/*  90 */             localUnsafe.getClass().getDeclaredMethod("copyMemory", new Class[] { Object.class, Long.TYPE, Object.class, Long.TYPE, Long.TYPE });
/*     */             
/*  92 */             logger.debug("sun.misc.Unsafe.copyMemory: available");
/*     */           }
/*     */         } catch (NoSuchMethodError localNoSuchMethodError) {
/*  95 */           logger.debug("sun.misc.Unsafe.copyMemory: unavailable");
/*  96 */           throw localNoSuchMethodError;
/*     */         } catch (NoSuchMethodException localNoSuchMethodException) {
/*  98 */           logger.debug("sun.misc.Unsafe.copyMemory: unavailable");
/*  99 */           throw localNoSuchMethodException;
/*     */         }
/*     */       }
/*     */       catch (Throwable localThrowable2) {
/* 103 */         localUnsafe = null;
/*     */       }
/*     */       
/*     */     }
/*     */     else {
/* 108 */       localUnsafe = null;
/*     */     }
/*     */     
/* 111 */     UNSAFE = localUnsafe;
/*     */     
/* 113 */     if (localUnsafe == null) {
/* 114 */       ADDRESS_FIELD_OFFSET = -1L;
/* 115 */       UNALIGNED = false;
/*     */     } else {
/* 117 */       ADDRESS_FIELD_OFFSET = objectFieldOffset(localField1);
/*     */       boolean bool;
/*     */       try {
/* 120 */         Class localClass = Class.forName("java.nio.Bits", false, ClassLoader.getSystemClassLoader());
/* 121 */         localObject = localClass.getDeclaredMethod("unaligned", new Class[0]);
/* 122 */         ((Method)localObject).setAccessible(true);
/* 123 */         bool = Boolean.TRUE.equals(((Method)localObject).invoke(null, new Object[0]));
/*     */       }
/*     */       catch (Throwable localThrowable3) {
/* 126 */         Object localObject = SystemPropertyUtil.get("os.arch", "");
/*     */         
/* 128 */         bool = ((String)localObject).matches("^(i[3-6]86|x86(_64)?|x64|amd64)$");
/*     */       }
/*     */       
/* 131 */       UNALIGNED = bool;
/* 132 */       logger.debug("java.nio.Bits.unaligned: {}", Boolean.valueOf(UNALIGNED));
/*     */     }
/*     */   }
/*     */   
/*     */   static boolean hasUnsafe() {
/* 137 */     return UNSAFE != null;
/*     */   }
/*     */   
/*     */   static void throwException(Throwable paramThrowable) {
/* 141 */     UNSAFE.throwException(paramThrowable);
/*     */   }
/*     */   
/*     */ 
/*     */   static void freeDirectBuffer(ByteBuffer paramByteBuffer)
/*     */   {
/* 147 */     Cleaner0.freeDirectBuffer(paramByteBuffer);
/*     */   }
/*     */   
/*     */   static long directBufferAddress(ByteBuffer paramByteBuffer) {
/* 151 */     return getLong(paramByteBuffer, ADDRESS_FIELD_OFFSET);
/*     */   }
/*     */   
/*     */   static long arrayBaseOffset() {
/* 155 */     return UNSAFE.arrayBaseOffset(byte[].class);
/*     */   }
/*     */   
/*     */   static Object getObject(Object paramObject, long paramLong) {
/* 159 */     return UNSAFE.getObject(paramObject, paramLong);
/*     */   }
/*     */   
/*     */   static Object getObjectVolatile(Object paramObject, long paramLong) {
/* 163 */     return UNSAFE.getObjectVolatile(paramObject, paramLong);
/*     */   }
/*     */   
/*     */   static int getInt(Object paramObject, long paramLong) {
/* 167 */     return UNSAFE.getInt(paramObject, paramLong);
/*     */   }
/*     */   
/*     */   private static long getLong(Object paramObject, long paramLong) {
/* 171 */     return UNSAFE.getLong(paramObject, paramLong);
/*     */   }
/*     */   
/*     */   static long objectFieldOffset(Field paramField) {
/* 175 */     return UNSAFE.objectFieldOffset(paramField);
/*     */   }
/*     */   
/*     */   static byte getByte(long paramLong) {
/* 179 */     return UNSAFE.getByte(paramLong);
/*     */   }
/*     */   
/*     */   static short getShort(long paramLong) {
/* 183 */     if (UNALIGNED)
/* 184 */       return UNSAFE.getShort(paramLong);
/* 185 */     if (BIG_ENDIAN) {
/* 186 */       return (short)(getByte(paramLong) << 8 | getByte(paramLong + 1L) & 0xFF);
/*     */     }
/* 188 */     return (short)(getByte(paramLong + 1L) << 8 | getByte(paramLong) & 0xFF);
/*     */   }
/*     */   
/*     */   static int getInt(long paramLong)
/*     */   {
/* 193 */     if (UNALIGNED)
/* 194 */       return UNSAFE.getInt(paramLong);
/* 195 */     if (BIG_ENDIAN) {
/* 196 */       return getByte(paramLong) << 24 | (getByte(paramLong + 1L) & 0xFF) << 16 | (getByte(paramLong + 2L) & 0xFF) << 8 | getByte(paramLong + 3L) & 0xFF;
/*     */     }
/*     */     
/*     */ 
/*     */ 
/* 201 */     return getByte(paramLong + 3L) << 24 | (getByte(paramLong + 2L) & 0xFF) << 16 | (getByte(paramLong + 1L) & 0xFF) << 8 | getByte(paramLong) & 0xFF;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   static long getLong(long paramLong)
/*     */   {
/* 209 */     if (UNALIGNED)
/* 210 */       return UNSAFE.getLong(paramLong);
/* 211 */     if (BIG_ENDIAN) {
/* 212 */       return getByte(paramLong) << 56 | (getByte(paramLong + 1L) & 0xFF) << 48 | (getByte(paramLong + 2L) & 0xFF) << 40 | (getByte(paramLong + 3L) & 0xFF) << 32 | (getByte(paramLong + 4L) & 0xFF) << 24 | (getByte(paramLong + 5L) & 0xFF) << 16 | (getByte(paramLong + 6L) & 0xFF) << 8 | getByte(paramLong + 7L) & 0xFF;
/*     */     }
/*     */     
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/* 221 */     return getByte(paramLong + 7L) << 56 | (getByte(paramLong + 6L) & 0xFF) << 48 | (getByte(paramLong + 5L) & 0xFF) << 40 | (getByte(paramLong + 4L) & 0xFF) << 32 | (getByte(paramLong + 3L) & 0xFF) << 24 | (getByte(paramLong + 2L) & 0xFF) << 16 | (getByte(paramLong + 1L) & 0xFF) << 8 | getByte(paramLong) & 0xFF;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   static void putOrderedObject(Object paramObject1, long paramLong, Object paramObject2)
/*     */   {
/* 233 */     UNSAFE.putOrderedObject(paramObject1, paramLong, paramObject2);
/*     */   }
/*     */   
/*     */   static void putByte(long paramLong, byte paramByte) {
/* 237 */     UNSAFE.putByte(paramLong, paramByte);
/*     */   }
/*     */   
/*     */   static void putShort(long paramLong, short paramShort) {
/* 241 */     if (UNALIGNED) {
/* 242 */       UNSAFE.putShort(paramLong, paramShort);
/* 243 */     } else if (BIG_ENDIAN) {
/* 244 */       putByte(paramLong, (byte)(paramShort >>> 8));
/* 245 */       putByte(paramLong + 1L, (byte)paramShort);
/*     */     } else {
/* 247 */       putByte(paramLong + 1L, (byte)(paramShort >>> 8));
/* 248 */       putByte(paramLong, (byte)paramShort);
/*     */     }
/*     */   }
/*     */   
/*     */   static void putInt(long paramLong, int paramInt) {
/* 253 */     if (UNALIGNED) {
/* 254 */       UNSAFE.putInt(paramLong, paramInt);
/* 255 */     } else if (BIG_ENDIAN) {
/* 256 */       putByte(paramLong, (byte)(paramInt >>> 24));
/* 257 */       putByte(paramLong + 1L, (byte)(paramInt >>> 16));
/* 258 */       putByte(paramLong + 2L, (byte)(paramInt >>> 8));
/* 259 */       putByte(paramLong + 3L, (byte)paramInt);
/*     */     } else {
/* 261 */       putByte(paramLong + 3L, (byte)(paramInt >>> 24));
/* 262 */       putByte(paramLong + 2L, (byte)(paramInt >>> 16));
/* 263 */       putByte(paramLong + 1L, (byte)(paramInt >>> 8));
/* 264 */       putByte(paramLong, (byte)paramInt);
/*     */     }
/*     */   }
/*     */   
/*     */   static void putLong(long paramLong1, long paramLong2) {
/* 269 */     if (UNALIGNED) {
/* 270 */       UNSAFE.putLong(paramLong1, paramLong2);
/* 271 */     } else if (BIG_ENDIAN) {
/* 272 */       putByte(paramLong1, (byte)(int)(paramLong2 >>> 56));
/* 273 */       putByte(paramLong1 + 1L, (byte)(int)(paramLong2 >>> 48));
/* 274 */       putByte(paramLong1 + 2L, (byte)(int)(paramLong2 >>> 40));
/* 275 */       putByte(paramLong1 + 3L, (byte)(int)(paramLong2 >>> 32));
/* 276 */       putByte(paramLong1 + 4L, (byte)(int)(paramLong2 >>> 24));
/* 277 */       putByte(paramLong1 + 5L, (byte)(int)(paramLong2 >>> 16));
/* 278 */       putByte(paramLong1 + 6L, (byte)(int)(paramLong2 >>> 8));
/* 279 */       putByte(paramLong1 + 7L, (byte)(int)paramLong2);
/*     */     } else {
/* 281 */       putByte(paramLong1 + 7L, (byte)(int)(paramLong2 >>> 56));
/* 282 */       putByte(paramLong1 + 6L, (byte)(int)(paramLong2 >>> 48));
/* 283 */       putByte(paramLong1 + 5L, (byte)(int)(paramLong2 >>> 40));
/* 284 */       putByte(paramLong1 + 4L, (byte)(int)(paramLong2 >>> 32));
/* 285 */       putByte(paramLong1 + 3L, (byte)(int)(paramLong2 >>> 24));
/* 286 */       putByte(paramLong1 + 2L, (byte)(int)(paramLong2 >>> 16));
/* 287 */       putByte(paramLong1 + 1L, (byte)(int)(paramLong2 >>> 8));
/* 288 */       putByte(paramLong1, (byte)(int)paramLong2);
/*     */     }
/*     */   }
/*     */   
/*     */   static void copyMemory(long paramLong1, long paramLong2, long paramLong3)
/*     */   {
/* 294 */     while (paramLong3 > 0L) {
/* 295 */       long l = Math.min(paramLong3, 1048576L);
/* 296 */       UNSAFE.copyMemory(paramLong1, paramLong2, l);
/* 297 */       paramLong3 -= l;
/* 298 */       paramLong1 += l;
/* 299 */       paramLong2 += l;
/*     */     }
/*     */   }
/*     */   
/*     */   static void copyMemory(Object paramObject1, long paramLong1, Object paramObject2, long paramLong2, long paramLong3)
/*     */   {
/* 305 */     while (paramLong3 > 0L) {
/* 306 */       long l = Math.min(paramLong3, 1048576L);
/* 307 */       UNSAFE.copyMemory(paramObject1, paramLong1, paramObject2, paramLong2, l);
/* 308 */       paramLong3 -= l;
/* 309 */       paramLong1 += l;
/* 310 */       paramLong2 += l;
/*     */     }
/*     */   }
/*     */   
/*     */   static <U, W> AtomicReferenceFieldUpdater<U, W> newAtomicReferenceFieldUpdater(Class<U> paramClass, String paramString) throws Exception
/*     */   {
/* 316 */     return new UnsafeAtomicReferenceFieldUpdater(UNSAFE, paramClass, paramString);
/*     */   }
/*     */   
/*     */   static <T> AtomicIntegerFieldUpdater<T> newAtomicIntegerFieldUpdater(Class<?> paramClass, String paramString) throws Exception
/*     */   {
/* 321 */     return new UnsafeAtomicIntegerFieldUpdater(UNSAFE, paramClass, paramString);
/*     */   }
/*     */   
/*     */   static <T> AtomicLongFieldUpdater<T> newAtomicLongFieldUpdater(Class<?> paramClass, String paramString) throws Exception
/*     */   {
/* 326 */     return new UnsafeAtomicLongFieldUpdater(UNSAFE, paramClass, paramString);
/*     */   }
/*     */   
/*     */   static ClassLoader getClassLoader(Class<?> paramClass) {
/* 330 */     if (System.getSecurityManager() == null) {
/* 331 */       return paramClass.getClassLoader();
/*     */     }
/* 333 */     (ClassLoader)AccessController.doPrivileged(new PrivilegedAction()
/*     */     {
/*     */       public ClassLoader run() {
/* 336 */         return this.val$clazz.getClassLoader();
/*     */       }
/*     */     });
/*     */   }
/*     */   
/*     */   static ClassLoader getContextClassLoader()
/*     */   {
/* 343 */     if (System.getSecurityManager() == null) {
/* 344 */       return Thread.currentThread().getContextClassLoader();
/*     */     }
/* 346 */     (ClassLoader)AccessController.doPrivileged(new PrivilegedAction()
/*     */     {
/*     */       public ClassLoader run() {
/* 349 */         return Thread.currentThread().getContextClassLoader();
/*     */       }
/*     */     });
/*     */   }
/*     */   
/*     */   static ClassLoader getSystemClassLoader()
/*     */   {
/* 356 */     if (System.getSecurityManager() == null) {
/* 357 */       return ClassLoader.getSystemClassLoader();
/*     */     }
/* 359 */     (ClassLoader)AccessController.doPrivileged(new PrivilegedAction()
/*     */     {
/*     */       public ClassLoader run() {
/* 362 */         return ClassLoader.getSystemClassLoader();
/*     */       }
/*     */     });
/*     */   }
/*     */   
/*     */   static int addressSize()
/*     */   {
/* 369 */     return UNSAFE.addressSize();
/*     */   }
/*     */   
/*     */   static long allocateMemory(long paramLong) {
/* 373 */     return UNSAFE.allocateMemory(paramLong);
/*     */   }
/*     */   
/*     */   static void freeMemory(long paramLong) {
/* 377 */     UNSAFE.freeMemory(paramLong);
/*     */   }
/*     */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\io\netty\util\internal\PlatformDependent0.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */
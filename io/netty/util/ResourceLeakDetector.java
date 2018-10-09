/*     */ package io.netty.util;
/*     */ 
/*     */ import io.netty.util.internal.PlatformDependent;
/*     */ import io.netty.util.internal.StringUtil;
/*     */ import io.netty.util.internal.SystemPropertyUtil;
/*     */ import io.netty.util.internal.logging.InternalLogger;
/*     */ import io.netty.util.internal.logging.InternalLoggerFactory;
/*     */ import java.lang.ref.PhantomReference;
/*     */ import java.lang.ref.ReferenceQueue;
/*     */ import java.util.ArrayDeque;
/*     */ import java.util.Deque;
/*     */ import java.util.EnumSet;
/*     */ import java.util.concurrent.ConcurrentMap;
/*     */ import java.util.concurrent.atomic.AtomicBoolean;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public final class ResourceLeakDetector<T>
/*     */ {
/*     */   private static final String PROP_LEVEL = "io.netty.leakDetectionLevel";
/*  37 */   private static final Level DEFAULT_LEVEL = Level.SIMPLE;
/*     */   
/*     */ 
/*     */   private static Level level;
/*     */   
/*     */ 
/*     */ 
/*     */   public static enum Level
/*     */   {
/*  46 */     DISABLED, 
/*     */     
/*     */ 
/*     */ 
/*     */ 
/*  51 */     SIMPLE, 
/*     */     
/*     */ 
/*     */ 
/*     */ 
/*  56 */     ADVANCED, 
/*     */     
/*     */ 
/*     */ 
/*     */ 
/*  61 */     PARANOID;
/*     */     
/*     */     private Level() {}
/*     */   }
/*     */   
/*  66 */   private static final InternalLogger logger = InternalLoggerFactory.getInstance(ResourceLeakDetector.class);
/*     */   private static final int DEFAULT_SAMPLING_INTERVAL = 113;
/*     */   
/*     */   static { boolean bool;
/*  70 */     if (SystemPropertyUtil.get("io.netty.noResourceLeakDetection") != null) {
/*  71 */       bool = SystemPropertyUtil.getBoolean("io.netty.noResourceLeakDetection", false);
/*  72 */       logger.debug("-Dio.netty.noResourceLeakDetection: {}", Boolean.valueOf(bool));
/*  73 */       logger.warn("-Dio.netty.noResourceLeakDetection is deprecated. Use '-D{}={}' instead.", "io.netty.leakDetectionLevel", DEFAULT_LEVEL.name().toLowerCase());
/*     */     }
/*     */     else
/*     */     {
/*  77 */       bool = false;
/*     */     }
/*     */     
/*  80 */     Level localLevel1 = bool ? Level.DISABLED : DEFAULT_LEVEL;
/*  81 */     String str = SystemPropertyUtil.get("io.netty.leakDetectionLevel", localLevel1.name()).trim().toUpperCase();
/*  82 */     Object localObject = DEFAULT_LEVEL;
/*  83 */     for (Level localLevel2 : EnumSet.allOf(Level.class)) {
/*  84 */       if ((str.equals(localLevel2.name())) || (str.equals(String.valueOf(localLevel2.ordinal())))) {
/*  85 */         localObject = localLevel2;
/*     */       }
/*     */     }
/*     */     
/*  89 */     level = (Level)localObject;
/*  90 */     if (logger.isDebugEnabled()) {
/*  91 */       logger.debug("-D{}: {}", "io.netty.leakDetectionLevel", ((Level)localObject).name().toLowerCase());
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   @Deprecated
/*     */   public static void setEnabled(boolean paramBoolean)
/*     */   {
/* 102 */     setLevel(paramBoolean ? Level.SIMPLE : Level.DISABLED);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public static boolean isEnabled()
/*     */   {
/* 109 */     return getLevel().ordinal() > Level.DISABLED.ordinal();
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public static void setLevel(Level paramLevel)
/*     */   {
/* 116 */     if (paramLevel == null) {
/* 117 */       throw new NullPointerException("level");
/*     */     }
/* 119 */     level = paramLevel;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public static Level getLevel()
/*     */   {
/* 126 */     return level;
/*     */   }
/*     */   
/*     */ 
/* 130 */   private final ResourceLeakDetector<T>.DefaultResourceLeak head = new DefaultResourceLeak(null);
/* 131 */   private final ResourceLeakDetector<T>.DefaultResourceLeak tail = new DefaultResourceLeak(null);
/*     */   
/* 133 */   private final ReferenceQueue<Object> refQueue = new ReferenceQueue();
/* 134 */   private final ConcurrentMap<String, Boolean> reportedLeaks = PlatformDependent.newConcurrentHashMap();
/*     */   
/*     */   private final String resourceType;
/*     */   private final int samplingInterval;
/*     */   private final long maxActive;
/*     */   private long active;
/* 140 */   private final AtomicBoolean loggedTooManyActive = new AtomicBoolean();
/*     */   private long leakCheckCnt;
/*     */   
/*     */   public ResourceLeakDetector(Class<?> paramClass)
/*     */   {
/* 145 */     this(StringUtil.simpleClassName(paramClass));
/*     */   }
/*     */   
/*     */   public ResourceLeakDetector(String paramString) {
/* 149 */     this(paramString, 113, Long.MAX_VALUE);
/*     */   }
/*     */   
/*     */   public ResourceLeakDetector(Class<?> paramClass, int paramInt, long paramLong) {
/* 153 */     this(StringUtil.simpleClassName(paramClass), paramInt, paramLong);
/*     */   }
/*     */   
/*     */   public ResourceLeakDetector(String paramString, int paramInt, long paramLong) {
/* 157 */     if (paramString == null) {
/* 158 */       throw new NullPointerException("resourceType");
/*     */     }
/* 160 */     if (paramInt <= 0) {
/* 161 */       throw new IllegalArgumentException("samplingInterval: " + paramInt + " (expected: 1+)");
/*     */     }
/* 163 */     if (paramLong <= 0L) {
/* 164 */       throw new IllegalArgumentException("maxActive: " + paramLong + " (expected: 1+)");
/*     */     }
/*     */     
/* 167 */     this.resourceType = paramString;
/* 168 */     this.samplingInterval = paramInt;
/* 169 */     this.maxActive = paramLong;
/*     */     
/* 171 */     this.head.next = this.tail;
/* 172 */     this.tail.prev = this.head;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public ResourceLeak open(T paramT)
/*     */   {
/* 182 */     Level localLevel = level;
/* 183 */     if (localLevel == Level.DISABLED) {
/* 184 */       return null;
/*     */     }
/*     */     
/* 187 */     if (localLevel.ordinal() < Level.PARANOID.ordinal()) {
/* 188 */       if (this.leakCheckCnt++ % this.samplingInterval == 0L) {
/* 189 */         reportLeak(localLevel);
/* 190 */         return new DefaultResourceLeak(paramT);
/*     */       }
/* 192 */       return null;
/*     */     }
/*     */     
/* 195 */     reportLeak(localLevel);
/* 196 */     return new DefaultResourceLeak(paramT);
/*     */   }
/*     */   
/*     */   private void reportLeak(Level paramLevel)
/*     */   {
/* 201 */     if (!logger.isErrorEnabled())
/*     */     {
/*     */       for (;;) {
/* 204 */         DefaultResourceLeak localDefaultResourceLeak1 = (DefaultResourceLeak)this.refQueue.poll();
/* 205 */         if (localDefaultResourceLeak1 == null) {
/*     */           break;
/*     */         }
/* 208 */         localDefaultResourceLeak1.close();
/*     */       }
/* 210 */       return;
/*     */     }
/*     */     
/*     */ 
/* 214 */     int i = paramLevel == Level.PARANOID ? 1 : this.samplingInterval;
/* 215 */     if ((this.active * i > this.maxActive) && (this.loggedTooManyActive.compareAndSet(false, true))) {
/* 216 */       logger.error("LEAK: You are creating too many " + this.resourceType + " instances.  " + this.resourceType + " is a shared resource that must be reused across the JVM," + "so that only a few instances are created.");
/*     */     }
/*     */     
/*     */ 
/*     */ 
/*     */ 
/*     */     for (;;)
/*     */     {
/* 224 */       DefaultResourceLeak localDefaultResourceLeak2 = (DefaultResourceLeak)this.refQueue.poll();
/* 225 */       if (localDefaultResourceLeak2 == null) {
/*     */         break;
/*     */       }
/*     */       
/* 229 */       localDefaultResourceLeak2.clear();
/*     */       
/* 231 */       if (localDefaultResourceLeak2.close())
/*     */       {
/*     */ 
/*     */ 
/* 235 */         String str = localDefaultResourceLeak2.toString();
/* 236 */         if (this.reportedLeaks.putIfAbsent(str, Boolean.TRUE) == null) {
/* 237 */           if (str.isEmpty()) {
/* 238 */             logger.error("LEAK: {}.release() was not called before it's garbage-collected. Enable advanced leak reporting to find out where the leak occurred. To enable advanced leak reporting, specify the JVM option '-D{}={}' or call {}.setLevel()", new Object[] { this.resourceType, "io.netty.leakDetectionLevel", Level.ADVANCED.name().toLowerCase(), StringUtil.simpleClassName(this) });
/*     */ 
/*     */           }
/*     */           else
/*     */           {
/*     */ 
/* 244 */             logger.error("LEAK: {}.release() was not called before it's garbage-collected.{}", this.resourceType, str);
/*     */           }
/*     */         }
/*     */       }
/*     */     }
/*     */   }
/*     */   
/*     */   private final class DefaultResourceLeak
/*     */     extends PhantomReference<Object>
/*     */     implements ResourceLeak
/*     */   {
/*     */     private static final int MAX_RECORDS = 4;
/*     */     private final String creationRecord;
/* 257 */     private final Deque<String> lastRecords = new ArrayDeque();
/*     */     private final AtomicBoolean freed;
/*     */     private ResourceLeakDetector<T>.DefaultResourceLeak prev;
/*     */     private ResourceLeakDetector<T>.DefaultResourceLeak next;
/*     */     
/*     */     DefaultResourceLeak(Object paramObject) {
/* 263 */       super(paramObject != null ? ResourceLeakDetector.this.refQueue : null);
/*     */       
/* 265 */       if (paramObject != null) {
/* 266 */         ResourceLeakDetector.Level localLevel = ResourceLeakDetector.getLevel();
/* 267 */         if (localLevel.ordinal() >= ResourceLeakDetector.Level.ADVANCED.ordinal()) {
/* 268 */           this.creationRecord = ResourceLeakDetector.newRecord(3);
/*     */         } else {
/* 270 */           this.creationRecord = null;
/*     */         }
/*     */         
/*     */ 
/* 274 */         synchronized (ResourceLeakDetector.this.head) {
/* 275 */           this.prev = ResourceLeakDetector.this.head;
/* 276 */           this.next = ResourceLeakDetector.this.head.next;
/* 277 */           ResourceLeakDetector.this.head.next.prev = this;
/* 278 */           ResourceLeakDetector.this.head.next = this;
/* 279 */           ResourceLeakDetector.access$408(ResourceLeakDetector.this);
/*     */         }
/* 281 */         this.freed = new AtomicBoolean();
/*     */       } else {
/* 283 */         this.creationRecord = null;
/* 284 */         this.freed = new AtomicBoolean(true);
/*     */       }
/*     */     }
/*     */     
/*     */     public void record()
/*     */     {
/* 290 */       if (this.creationRecord != null) {
/* 291 */         String str = ResourceLeakDetector.newRecord(2);
/*     */         
/* 293 */         synchronized (this.lastRecords) {
/* 294 */           int i = this.lastRecords.size();
/* 295 */           if ((i == 0) || (!((String)this.lastRecords.getLast()).equals(str))) {
/* 296 */             this.lastRecords.add(str);
/*     */           }
/* 298 */           if (i > 4) {
/* 299 */             this.lastRecords.removeFirst();
/*     */           }
/*     */         }
/*     */       }
/*     */     }
/*     */     
/*     */     public boolean close()
/*     */     {
/* 307 */       if (this.freed.compareAndSet(false, true)) {
/* 308 */         synchronized (ResourceLeakDetector.this.head) {
/* 309 */           ResourceLeakDetector.access$410(ResourceLeakDetector.this);
/* 310 */           this.prev.next = this.next;
/* 311 */           this.next.prev = this.prev;
/* 312 */           this.prev = null;
/* 313 */           this.next = null;
/*     */         }
/* 315 */         return true;
/*     */       }
/* 317 */       return false;
/*     */     }
/*     */     
/*     */     public String toString() {
/* 321 */       if (this.creationRecord == null) {
/* 322 */         return "";
/*     */       }
/*     */       
/*     */       Object[] arrayOfObject;
/* 326 */       synchronized (this.lastRecords) {
/* 327 */         arrayOfObject = this.lastRecords.toArray();
/*     */       }
/*     */       
/* 330 */       ??? = new StringBuilder(16384);
/* 331 */       ((StringBuilder)???).append(StringUtil.NEWLINE);
/* 332 */       ((StringBuilder)???).append("Recent access records: ");
/* 333 */       ((StringBuilder)???).append(arrayOfObject.length);
/* 334 */       ((StringBuilder)???).append(StringUtil.NEWLINE);
/*     */       
/* 336 */       if (arrayOfObject.length > 0) {
/* 337 */         for (int i = arrayOfObject.length - 1; i >= 0; i--) {
/* 338 */           ((StringBuilder)???).append('#');
/* 339 */           ((StringBuilder)???).append(i + 1);
/* 340 */           ((StringBuilder)???).append(':');
/* 341 */           ((StringBuilder)???).append(StringUtil.NEWLINE);
/* 342 */           ((StringBuilder)???).append(arrayOfObject[i]);
/*     */         }
/*     */       }
/*     */       
/* 346 */       ((StringBuilder)???).append("Created at:");
/* 347 */       ((StringBuilder)???).append(StringUtil.NEWLINE);
/* 348 */       ((StringBuilder)???).append(this.creationRecord);
/* 349 */       ((StringBuilder)???).setLength(((StringBuilder)???).length() - StringUtil.NEWLINE.length());
/*     */       
/* 351 */       return ((StringBuilder)???).toString();
/*     */     }
/*     */   }
/*     */   
/* 355 */   private static final String[] STACK_TRACE_ELEMENT_EXCLUSIONS = { "io.netty.buffer.AbstractByteBufAllocator.toLeakAwareBuffer(" };
/*     */   
/*     */ 
/*     */   static String newRecord(int paramInt)
/*     */   {
/* 360 */     StringBuilder localStringBuilder = new StringBuilder(4096);
/* 361 */     StackTraceElement[] arrayOfStackTraceElement1 = new Throwable().getStackTrace();
/* 362 */     for (StackTraceElement localStackTraceElement : arrayOfStackTraceElement1) {
/* 363 */       if (paramInt > 0) {
/* 364 */         paramInt--;
/*     */       } else {
/* 366 */         String str1 = localStackTraceElement.toString();
/*     */         
/*     */ 
/* 369 */         int k = 0;
/* 370 */         for (String str2 : STACK_TRACE_ELEMENT_EXCLUSIONS) {
/* 371 */           if (str1.startsWith(str2)) {
/* 372 */             k = 1;
/* 373 */             break;
/*     */           }
/*     */         }
/*     */         
/* 377 */         if (k == 0) {
/* 378 */           localStringBuilder.append('\t');
/* 379 */           localStringBuilder.append(str1);
/* 380 */           localStringBuilder.append(StringUtil.NEWLINE);
/*     */         }
/*     */       }
/*     */     }
/*     */     
/* 385 */     return localStringBuilder.toString();
/*     */   }
/*     */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\io\netty\util\ResourceLeakDetector.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */
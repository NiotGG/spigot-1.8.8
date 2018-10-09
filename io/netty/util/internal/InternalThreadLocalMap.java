/*     */ package io.netty.util.internal;
/*     */ 
/*     */ import io.netty.util.concurrent.FastThreadLocalThread;
/*     */ import java.nio.charset.Charset;
/*     */ import java.nio.charset.CharsetDecoder;
/*     */ import java.nio.charset.CharsetEncoder;
/*     */ import java.util.Arrays;
/*     */ import java.util.IdentityHashMap;
/*     */ import java.util.Map;
/*     */ import java.util.WeakHashMap;
/*     */ import java.util.concurrent.atomic.AtomicInteger;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public final class InternalThreadLocalMap
/*     */   extends UnpaddedInternalThreadLocalMap
/*     */ {
/*  37 */   public static final Object UNSET = new Object();
/*     */   public long rp1;
/*     */   
/*  40 */   public static InternalThreadLocalMap getIfSet() { Thread localThread = Thread.currentThread();
/*     */     InternalThreadLocalMap localInternalThreadLocalMap;
/*  42 */     if ((localThread instanceof FastThreadLocalThread)) {
/*  43 */       localInternalThreadLocalMap = ((FastThreadLocalThread)localThread).threadLocalMap();
/*     */     } else {
/*  45 */       ThreadLocal localThreadLocal = UnpaddedInternalThreadLocalMap.slowThreadLocalMap;
/*  46 */       if (localThreadLocal == null) {
/*  47 */         localInternalThreadLocalMap = null;
/*     */       } else {
/*  49 */         localInternalThreadLocalMap = (InternalThreadLocalMap)localThreadLocal.get();
/*     */       }
/*     */     }
/*  52 */     return localInternalThreadLocalMap; }
/*     */   
/*     */   public long rp2;
/*     */   
/*  56 */   public static InternalThreadLocalMap get() { Thread localThread = Thread.currentThread();
/*  57 */     if ((localThread instanceof FastThreadLocalThread)) {
/*  58 */       return fastGet((FastThreadLocalThread)localThread);
/*     */     }
/*  60 */     return slowGet();
/*     */   }
/*     */   
/*     */   public long rp3;
/*     */   public long rp4;
/*  65 */   private static InternalThreadLocalMap fastGet(FastThreadLocalThread paramFastThreadLocalThread) { InternalThreadLocalMap localInternalThreadLocalMap = paramFastThreadLocalThread.threadLocalMap();
/*  66 */     if (localInternalThreadLocalMap == null) {
/*  67 */       paramFastThreadLocalThread.setThreadLocalMap(localInternalThreadLocalMap = new InternalThreadLocalMap());
/*     */     }
/*  69 */     return localInternalThreadLocalMap;
/*     */   }
/*     */   
/*     */   private static InternalThreadLocalMap slowGet() {
/*  73 */     ThreadLocal localThreadLocal = UnpaddedInternalThreadLocalMap.slowThreadLocalMap;
/*  74 */     if (localThreadLocal == null) {
/*  75 */       UnpaddedInternalThreadLocalMap.slowThreadLocalMap = localThreadLocal = new ThreadLocal();
/*     */     }
/*     */     
/*     */ 
/*  79 */     InternalThreadLocalMap localInternalThreadLocalMap = (InternalThreadLocalMap)localThreadLocal.get();
/*  80 */     if (localInternalThreadLocalMap == null) {
/*  81 */       localInternalThreadLocalMap = new InternalThreadLocalMap();
/*  82 */       localThreadLocal.set(localInternalThreadLocalMap);
/*     */     }
/*  84 */     return localInternalThreadLocalMap;
/*     */   }
/*     */   
/*     */   public static void remove() {
/*  88 */     Thread localThread = Thread.currentThread();
/*  89 */     if ((localThread instanceof FastThreadLocalThread)) {
/*  90 */       ((FastThreadLocalThread)localThread).setThreadLocalMap(null);
/*     */     } else {
/*  92 */       ThreadLocal localThreadLocal = UnpaddedInternalThreadLocalMap.slowThreadLocalMap;
/*  93 */       if (localThreadLocal != null) {
/*  94 */         localThreadLocal.remove();
/*     */       }
/*     */     }
/*     */   }
/*     */   
/*     */   public static void destroy() {
/* 100 */     slowThreadLocalMap = null;
/*     */   }
/*     */   
/*     */   public static int nextVariableIndex() {
/* 104 */     int i = nextIndex.getAndIncrement();
/* 105 */     if (i < 0) {
/* 106 */       nextIndex.decrementAndGet();
/* 107 */       throw new IllegalStateException("too many thread-local indexed variables");
/*     */     }
/* 109 */     return i;
/*     */   }
/*     */   
/*     */   public static int lastVariableIndex() {
/* 113 */     return nextIndex.get() - 1;
/*     */   }
/*     */   
/*     */   public long rp5;
/*     */   public long rp6;
/*     */   public long rp7;
/*     */   public long rp8;
/*     */   public long rp9;
/* 121 */   private InternalThreadLocalMap() { super(newIndexedVariableTable()); }
/*     */   
/*     */ 
/*     */   private static Object[] newIndexedVariableTable() {
/* 125 */     Object[] arrayOfObject = new Object[32];
/* 126 */     Arrays.fill(arrayOfObject, UNSET);
/* 127 */     return arrayOfObject;
/*     */   }
/*     */   
/*     */   public int size() {
/* 131 */     int i = 0;
/*     */     
/* 133 */     if (this.futureListenerStackDepth != 0) {
/* 134 */       i++;
/*     */     }
/* 136 */     if (this.localChannelReaderStackDepth != 0) {
/* 137 */       i++;
/*     */     }
/* 139 */     if (this.handlerSharableCache != null) {
/* 140 */       i++;
/*     */     }
/* 142 */     if (this.counterHashCode != null) {
/* 143 */       i++;
/*     */     }
/* 145 */     if (this.random != null) {
/* 146 */       i++;
/*     */     }
/* 148 */     if (this.typeParameterMatcherGetCache != null) {
/* 149 */       i++;
/*     */     }
/* 151 */     if (this.typeParameterMatcherFindCache != null) {
/* 152 */       i++;
/*     */     }
/* 154 */     if (this.stringBuilder != null) {
/* 155 */       i++;
/*     */     }
/* 157 */     if (this.charsetEncoderCache != null) {
/* 158 */       i++;
/*     */     }
/* 160 */     if (this.charsetDecoderCache != null) {
/* 161 */       i++;
/*     */     }
/*     */     
/* 164 */     for (Object localObject : this.indexedVariables) {
/* 165 */       if (localObject != UNSET) {
/* 166 */         i++;
/*     */       }
/*     */     }
/*     */     
/*     */ 
/*     */ 
/* 172 */     return i - 1;
/*     */   }
/*     */   
/*     */   public StringBuilder stringBuilder() {
/* 176 */     StringBuilder localStringBuilder = this.stringBuilder;
/* 177 */     if (localStringBuilder == null) {
/* 178 */       this.stringBuilder = (localStringBuilder = new StringBuilder(512));
/*     */     } else {
/* 180 */       localStringBuilder.setLength(0);
/*     */     }
/* 182 */     return localStringBuilder;
/*     */   }
/*     */   
/*     */   public Map<Charset, CharsetEncoder> charsetEncoderCache() {
/* 186 */     Object localObject = this.charsetEncoderCache;
/* 187 */     if (localObject == null) {
/* 188 */       this.charsetEncoderCache = (localObject = new IdentityHashMap());
/*     */     }
/* 190 */     return (Map<Charset, CharsetEncoder>)localObject;
/*     */   }
/*     */   
/*     */   public Map<Charset, CharsetDecoder> charsetDecoderCache() {
/* 194 */     Object localObject = this.charsetDecoderCache;
/* 195 */     if (localObject == null) {
/* 196 */       this.charsetDecoderCache = (localObject = new IdentityHashMap());
/*     */     }
/* 198 */     return (Map<Charset, CharsetDecoder>)localObject;
/*     */   }
/*     */   
/*     */   public int futureListenerStackDepth() {
/* 202 */     return this.futureListenerStackDepth;
/*     */   }
/*     */   
/*     */   public void setFutureListenerStackDepth(int paramInt) {
/* 206 */     this.futureListenerStackDepth = paramInt;
/*     */   }
/*     */   
/*     */   public ThreadLocalRandom random() {
/* 210 */     ThreadLocalRandom localThreadLocalRandom = this.random;
/* 211 */     if (localThreadLocalRandom == null) {
/* 212 */       this.random = (localThreadLocalRandom = new ThreadLocalRandom());
/*     */     }
/* 214 */     return localThreadLocalRandom;
/*     */   }
/*     */   
/*     */   public Map<Class<?>, TypeParameterMatcher> typeParameterMatcherGetCache() {
/* 218 */     Object localObject = this.typeParameterMatcherGetCache;
/* 219 */     if (localObject == null) {
/* 220 */       this.typeParameterMatcherGetCache = (localObject = new IdentityHashMap());
/*     */     }
/* 222 */     return (Map<Class<?>, TypeParameterMatcher>)localObject;
/*     */   }
/*     */   
/*     */   public Map<Class<?>, Map<String, TypeParameterMatcher>> typeParameterMatcherFindCache() {
/* 226 */     Object localObject = this.typeParameterMatcherFindCache;
/* 227 */     if (localObject == null) {
/* 228 */       this.typeParameterMatcherFindCache = (localObject = new IdentityHashMap());
/*     */     }
/* 230 */     return (Map<Class<?>, Map<String, TypeParameterMatcher>>)localObject;
/*     */   }
/*     */   
/*     */   public IntegerHolder counterHashCode() {
/* 234 */     return this.counterHashCode;
/*     */   }
/*     */   
/*     */   public void setCounterHashCode(IntegerHolder paramIntegerHolder) {
/* 238 */     this.counterHashCode = paramIntegerHolder;
/*     */   }
/*     */   
/*     */   public Map<Class<?>, Boolean> handlerSharableCache() {
/* 242 */     Object localObject = this.handlerSharableCache;
/* 243 */     if (localObject == null)
/*     */     {
/* 245 */       this.handlerSharableCache = (localObject = new WeakHashMap(4));
/*     */     }
/* 247 */     return (Map<Class<?>, Boolean>)localObject;
/*     */   }
/*     */   
/*     */   public int localChannelReaderStackDepth() {
/* 251 */     return this.localChannelReaderStackDepth;
/*     */   }
/*     */   
/*     */   public void setLocalChannelReaderStackDepth(int paramInt) {
/* 255 */     this.localChannelReaderStackDepth = paramInt;
/*     */   }
/*     */   
/*     */   public Object indexedVariable(int paramInt) {
/* 259 */     Object[] arrayOfObject = this.indexedVariables;
/* 260 */     return paramInt < arrayOfObject.length ? arrayOfObject[paramInt] : UNSET;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public boolean setIndexedVariable(int paramInt, Object paramObject)
/*     */   {
/* 267 */     Object[] arrayOfObject = this.indexedVariables;
/* 268 */     if (paramInt < arrayOfObject.length) {
/* 269 */       Object localObject = arrayOfObject[paramInt];
/* 270 */       arrayOfObject[paramInt] = paramObject;
/* 271 */       return localObject == UNSET;
/*     */     }
/* 273 */     expandIndexedVariableTableAndSet(paramInt, paramObject);
/* 274 */     return true;
/*     */   }
/*     */   
/*     */   private void expandIndexedVariableTableAndSet(int paramInt, Object paramObject)
/*     */   {
/* 279 */     Object[] arrayOfObject1 = this.indexedVariables;
/* 280 */     int i = arrayOfObject1.length;
/* 281 */     int j = paramInt;
/* 282 */     j |= j >>> 1;
/* 283 */     j |= j >>> 2;
/* 284 */     j |= j >>> 4;
/* 285 */     j |= j >>> 8;
/* 286 */     j |= j >>> 16;
/* 287 */     j++;
/*     */     
/* 289 */     Object[] arrayOfObject2 = Arrays.copyOf(arrayOfObject1, j);
/* 290 */     Arrays.fill(arrayOfObject2, i, arrayOfObject2.length, UNSET);
/* 291 */     arrayOfObject2[paramInt] = paramObject;
/* 292 */     this.indexedVariables = arrayOfObject2;
/*     */   }
/*     */   
/*     */   public Object removeIndexedVariable(int paramInt) {
/* 296 */     Object[] arrayOfObject = this.indexedVariables;
/* 297 */     if (paramInt < arrayOfObject.length) {
/* 298 */       Object localObject = arrayOfObject[paramInt];
/* 299 */       arrayOfObject[paramInt] = UNSET;
/* 300 */       return localObject;
/*     */     }
/* 302 */     return UNSET;
/*     */   }
/*     */   
/*     */   public boolean isIndexedVariableSet(int paramInt)
/*     */   {
/* 307 */     Object[] arrayOfObject = this.indexedVariables;
/* 308 */     return (paramInt < arrayOfObject.length) && (arrayOfObject[paramInt] != UNSET);
/*     */   }
/*     */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\io\netty\util\internal\InternalThreadLocalMap.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */
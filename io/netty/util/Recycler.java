/*     */ package io.netty.util;
/*     */ 
/*     */ import io.netty.util.concurrent.FastThreadLocal;
/*     */ import io.netty.util.internal.SystemPropertyUtil;
/*     */ import io.netty.util.internal.logging.InternalLogger;
/*     */ import io.netty.util.internal.logging.InternalLoggerFactory;
/*     */ import java.lang.ref.WeakReference;
/*     */ import java.util.Arrays;
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
/*     */ 
/*     */ public abstract class Recycler<T>
/*     */ {
/*  37 */   private static final InternalLogger logger = InternalLoggerFactory.getInstance(Recycler.class);
/*     */   
/*  39 */   private static final AtomicInteger ID_GENERATOR = new AtomicInteger(Integer.MIN_VALUE);
/*  40 */   private static final int OWN_THREAD_ID = ID_GENERATOR.getAndIncrement();
/*     */   
/*     */ 
/*     */   private static final int DEFAULT_MAX_CAPACITY;
/*     */   
/*     */ 
/*     */   static
/*     */   {
/*  48 */     int i = SystemPropertyUtil.getInt("io.netty.recycler.maxCapacity.default", 0);
/*  49 */     if (i <= 0)
/*     */     {
/*  51 */       i = 262144;
/*     */     }
/*     */     
/*  54 */     DEFAULT_MAX_CAPACITY = i;
/*  55 */     if (logger.isDebugEnabled())
/*  56 */       logger.debug("-Dio.netty.recycler.maxCapacity.default: {}", Integer.valueOf(DEFAULT_MAX_CAPACITY));
/*     */   }
/*     */   
/*  59 */   private static final int INITIAL_CAPACITY = Math.min(DEFAULT_MAX_CAPACITY, 256);
/*     */   
/*     */   private final int maxCapacity;
/*     */   
/*  63 */   private final FastThreadLocal<Stack<T>> threadLocal = new FastThreadLocal()
/*     */   {
/*     */     protected Recycler.Stack<T> initialValue() {
/*  66 */       return new Recycler.Stack(Recycler.this, Thread.currentThread(), Recycler.this.maxCapacity);
/*     */     }
/*     */   };
/*     */   
/*     */   protected Recycler() {
/*  71 */     this(DEFAULT_MAX_CAPACITY);
/*     */   }
/*     */   
/*     */   protected Recycler(int paramInt) {
/*  75 */     this.maxCapacity = Math.max(0, paramInt);
/*     */   }
/*     */   
/*     */   public final T get()
/*     */   {
/*  80 */     Stack localStack = (Stack)this.threadLocal.get();
/*  81 */     DefaultHandle localDefaultHandle = localStack.pop();
/*  82 */     if (localDefaultHandle == null) {
/*  83 */       localDefaultHandle = localStack.newHandle();
/*  84 */       localDefaultHandle.value = newObject(localDefaultHandle);
/*     */     }
/*  86 */     return (T)localDefaultHandle.value;
/*     */   }
/*     */   
/*     */   public final boolean recycle(T paramT, Handle paramHandle) {
/*  90 */     DefaultHandle localDefaultHandle = (DefaultHandle)paramHandle;
/*  91 */     if (localDefaultHandle.stack.parent != this) {
/*  92 */       return false;
/*     */     }
/*  94 */     if (paramT != localDefaultHandle.value) {
/*  95 */       throw new IllegalArgumentException("o does not belong to handle");
/*     */     }
/*  97 */     localDefaultHandle.recycle();
/*  98 */     return true;
/*     */   }
/*     */   
/*     */   protected abstract T newObject(Handle paramHandle);
/*     */   
/*     */   public static abstract interface Handle {}
/*     */   
/*     */   static final class DefaultHandle implements Recycler.Handle
/*     */   {
/*     */     private int lastRecycledId;
/*     */     private int recycleId;
/*     */     private Recycler.Stack<?> stack;
/*     */     private Object value;
/*     */     
/*     */     DefaultHandle(Recycler.Stack<?> paramStack) {
/* 113 */       this.stack = paramStack;
/*     */     }
/*     */     
/*     */     public void recycle() {
/* 117 */       Thread localThread = Thread.currentThread();
/* 118 */       if (localThread == this.stack.thread) {
/* 119 */         this.stack.push(this);
/* 120 */         return;
/*     */       }
/*     */       
/*     */ 
/*     */ 
/* 125 */       Map localMap = (Map)Recycler.DELAYED_RECYCLED.get();
/* 126 */       Recycler.WeakOrderQueue localWeakOrderQueue = (Recycler.WeakOrderQueue)localMap.get(this.stack);
/* 127 */       if (localWeakOrderQueue == null) {
/* 128 */         localMap.put(this.stack, localWeakOrderQueue = new Recycler.WeakOrderQueue(this.stack, localThread));
/*     */       }
/* 130 */       localWeakOrderQueue.add(this);
/*     */     }
/*     */   }
/*     */   
/* 134 */   private static final FastThreadLocal<Map<Stack<?>, WeakOrderQueue>> DELAYED_RECYCLED = new FastThreadLocal()
/*     */   {
/*     */     protected Map<Recycler.Stack<?>, Recycler.WeakOrderQueue> initialValue()
/*     */     {
/* 138 */       return new WeakHashMap();
/*     */     }
/*     */   };
/*     */   
/*     */   private static final class WeakOrderQueue {
/*     */     private static final int LINK_CAPACITY = 16;
/*     */     private Link head;
/*     */     private Link tail;
/*     */     private WeakOrderQueue next;
/*     */     private final WeakReference<Thread> owner;
/*     */     
/*     */     private static final class Link extends AtomicInteger {
/* 150 */       private final Recycler.DefaultHandle[] elements = new Recycler.DefaultHandle[16];
/*     */       
/*     */ 
/*     */       private int readIndex;
/*     */       
/*     */ 
/*     */       private Link next;
/*     */     }
/*     */     
/*     */ 
/*     */ 
/* 161 */     private final int id = Recycler.ID_GENERATOR.getAndIncrement();
/*     */     
/*     */     WeakOrderQueue(Recycler.Stack<?> paramStack, Thread paramThread) {
/* 164 */       this.head = (this.tail = new Link(null));
/* 165 */       this.owner = new WeakReference(paramThread);
/* 166 */       synchronized (paramStack) {
/* 167 */         this.next = paramStack.head;
/* 168 */         paramStack.head = this;
/*     */       }
/*     */     }
/*     */     
/*     */     void add(Recycler.DefaultHandle paramDefaultHandle) {
/* 173 */       Recycler.DefaultHandle.access$702(paramDefaultHandle, this.id);
/*     */       
/* 175 */       Link localLink = this.tail;
/*     */       int i;
/* 177 */       if ((i = localLink.get()) == 16) {
/* 178 */         this.tail = (localLink = localLink.next = new Link(null));
/* 179 */         i = localLink.get();
/*     */       }
/* 181 */       localLink.elements[i] = paramDefaultHandle;
/* 182 */       Recycler.DefaultHandle.access$202(paramDefaultHandle, null);
/*     */       
/*     */ 
/* 185 */       localLink.lazySet(i + 1);
/*     */     }
/*     */     
/*     */     boolean hasFinalData() {
/* 189 */       return this.tail.readIndex != this.tail.get();
/*     */     }
/*     */     
/*     */ 
/*     */ 
/*     */     boolean transfer(Recycler.Stack<?> paramStack)
/*     */     {
/* 196 */       Link localLink = this.head;
/* 197 */       if (localLink == null) {
/* 198 */         return false;
/*     */       }
/*     */       
/* 201 */       if (localLink.readIndex == 16) {
/* 202 */         if (localLink.next == null) {
/* 203 */           return false;
/*     */         }
/* 205 */         this.head = (localLink = localLink.next);
/*     */       }
/*     */       
/* 208 */       int i = localLink.readIndex;
/* 209 */       int j = localLink.get();
/* 210 */       if (i == j) {
/* 211 */         return false;
/*     */       }
/*     */       
/* 214 */       int k = j - i;
/* 215 */       if (paramStack.size + k > paramStack.elements.length) {
/* 216 */         paramStack.elements = ((Recycler.DefaultHandle[])Arrays.copyOf(paramStack.elements, (paramStack.size + k) * 2));
/*     */       }
/*     */       
/* 219 */       Recycler.DefaultHandle[] arrayOfDefaultHandle1 = localLink.elements;
/* 220 */       Recycler.DefaultHandle[] arrayOfDefaultHandle2 = paramStack.elements;
/* 221 */       int m = paramStack.size;
/* 222 */       while (i < j) {
/* 223 */         Recycler.DefaultHandle localDefaultHandle = arrayOfDefaultHandle1[i];
/* 224 */         if (Recycler.DefaultHandle.access$1300(localDefaultHandle) == 0) {
/* 225 */           Recycler.DefaultHandle.access$1302(localDefaultHandle, Recycler.DefaultHandle.access$700(localDefaultHandle));
/* 226 */         } else if (Recycler.DefaultHandle.access$1300(localDefaultHandle) != Recycler.DefaultHandle.access$700(localDefaultHandle)) {
/* 227 */           throw new IllegalStateException("recycled already");
/*     */         }
/* 229 */         Recycler.DefaultHandle.access$202(localDefaultHandle, paramStack);
/* 230 */         arrayOfDefaultHandle2[(m++)] = localDefaultHandle;
/* 231 */         arrayOfDefaultHandle1[(i++)] = null;
/*     */       }
/* 233 */       paramStack.size = m;
/*     */       
/* 235 */       if ((j == 16) && (localLink.next != null)) {
/* 236 */         this.head = localLink.next;
/*     */       }
/*     */       
/* 239 */       localLink.readIndex = j;
/* 240 */       return true;
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */   static final class Stack<T>
/*     */   {
/*     */     final Recycler<T> parent;
/*     */     
/*     */     final Thread thread;
/*     */     
/*     */     private Recycler.DefaultHandle[] elements;
/*     */     private final int maxCapacity;
/*     */     private int size;
/*     */     private volatile Recycler.WeakOrderQueue head;
/*     */     private Recycler.WeakOrderQueue cursor;
/*     */     private Recycler.WeakOrderQueue prev;
/*     */     
/*     */     Stack(Recycler<T> paramRecycler, Thread paramThread, int paramInt)
/*     */     {
/* 260 */       this.parent = paramRecycler;
/* 261 */       this.thread = paramThread;
/* 262 */       this.maxCapacity = paramInt;
/* 263 */       this.elements = new Recycler.DefaultHandle[Recycler.INITIAL_CAPACITY];
/*     */     }
/*     */     
/*     */     Recycler.DefaultHandle pop() {
/* 267 */       int i = this.size;
/* 268 */       if (i == 0) {
/* 269 */         if (!scavenge()) {
/* 270 */           return null;
/*     */         }
/* 272 */         i = this.size;
/*     */       }
/* 274 */       i--;
/* 275 */       Recycler.DefaultHandle localDefaultHandle = this.elements[i];
/* 276 */       if (Recycler.DefaultHandle.access$700(localDefaultHandle) != Recycler.DefaultHandle.access$1300(localDefaultHandle)) {
/* 277 */         throw new IllegalStateException("recycled multiple times");
/*     */       }
/* 279 */       Recycler.DefaultHandle.access$1302(localDefaultHandle, 0);
/* 280 */       Recycler.DefaultHandle.access$702(localDefaultHandle, 0);
/* 281 */       this.size = i;
/* 282 */       return localDefaultHandle;
/*     */     }
/*     */     
/*     */     boolean scavenge()
/*     */     {
/* 287 */       if (scavengeSome()) {
/* 288 */         return true;
/*     */       }
/*     */       
/*     */ 
/* 292 */       this.prev = null;
/* 293 */       this.cursor = this.head;
/* 294 */       return false;
/*     */     }
/*     */     
/*     */     boolean scavengeSome() {
/* 298 */       boolean bool = false;
/* 299 */       Object localObject1 = this.cursor;Object localObject2 = this.prev;
/* 300 */       while (localObject1 != null) {
/* 301 */         if (((Recycler.WeakOrderQueue)localObject1).transfer(this)) {
/* 302 */           bool = true;
/* 303 */           break;
/*     */         }
/* 305 */         Recycler.WeakOrderQueue localWeakOrderQueue = Recycler.WeakOrderQueue.access$1500((Recycler.WeakOrderQueue)localObject1);
/* 306 */         if (Recycler.WeakOrderQueue.access$1600((Recycler.WeakOrderQueue)localObject1).get() == null)
/*     */         {
/*     */ 
/*     */ 
/* 310 */           if (((Recycler.WeakOrderQueue)localObject1).hasFinalData()) {
/*     */             for (;;) {
/* 312 */               if (!((Recycler.WeakOrderQueue)localObject1).transfer(this)) {
/*     */                 break;
/*     */               }
/*     */             }
/*     */           }
/* 317 */           if (localObject2 != null) {
/* 318 */             Recycler.WeakOrderQueue.access$1502((Recycler.WeakOrderQueue)localObject2, localWeakOrderQueue);
/*     */           }
/*     */         } else {
/* 321 */           localObject2 = localObject1;
/*     */         }
/* 323 */         localObject1 = localWeakOrderQueue;
/*     */       }
/* 325 */       this.prev = ((Recycler.WeakOrderQueue)localObject2);
/* 326 */       this.cursor = ((Recycler.WeakOrderQueue)localObject1);
/* 327 */       return bool;
/*     */     }
/*     */     
/*     */     void push(Recycler.DefaultHandle paramDefaultHandle) {
/* 331 */       if ((Recycler.DefaultHandle.access$1300(paramDefaultHandle) | Recycler.DefaultHandle.access$700(paramDefaultHandle)) != 0) {
/* 332 */         throw new IllegalStateException("recycled already");
/*     */       }
/* 334 */       Recycler.DefaultHandle.access$1302(paramDefaultHandle, Recycler.DefaultHandle.access$702(paramDefaultHandle, Recycler.OWN_THREAD_ID));
/*     */       
/* 336 */       int i = this.size;
/* 337 */       if (i == this.elements.length) {
/* 338 */         if (i == this.maxCapacity)
/*     */         {
/* 340 */           return;
/*     */         }
/* 342 */         this.elements = ((Recycler.DefaultHandle[])Arrays.copyOf(this.elements, i << 1));
/*     */       }
/*     */       
/* 345 */       this.elements[i] = paramDefaultHandle;
/* 346 */       this.size = (i + 1);
/*     */     }
/*     */     
/*     */     Recycler.DefaultHandle newHandle() {
/* 350 */       return new Recycler.DefaultHandle(this);
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\io\netty\util\Recycler.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */
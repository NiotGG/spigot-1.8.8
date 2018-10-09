/*     */ package io.netty.channel;
/*     */ 
/*     */ import io.netty.util.Recycler;
/*     */ import io.netty.util.Recycler.Handle;
/*     */ import io.netty.util.ReferenceCountUtil;
/*     */ import io.netty.util.concurrent.EventExecutor;
/*     */ import io.netty.util.internal.logging.InternalLogger;
/*     */ import io.netty.util.internal.logging.InternalLoggerFactory;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public final class PendingWriteQueue
/*     */ {
/*  29 */   private static final InternalLogger logger = InternalLoggerFactory.getInstance(PendingWriteQueue.class);
/*     */   
/*     */   private final ChannelHandlerContext ctx;
/*     */   
/*     */   private final ChannelOutboundBuffer buffer;
/*     */   private final MessageSizeEstimator.Handle estimatorHandle;
/*     */   private PendingWrite head;
/*     */   private PendingWrite tail;
/*     */   private int size;
/*     */   
/*     */   public PendingWriteQueue(ChannelHandlerContext paramChannelHandlerContext)
/*     */   {
/*  41 */     if (paramChannelHandlerContext == null) {
/*  42 */       throw new NullPointerException("ctx");
/*     */     }
/*  44 */     this.ctx = paramChannelHandlerContext;
/*  45 */     this.buffer = paramChannelHandlerContext.channel().unsafe().outboundBuffer();
/*  46 */     this.estimatorHandle = paramChannelHandlerContext.channel().config().getMessageSizeEstimator().newHandle();
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public boolean isEmpty()
/*     */   {
/*  53 */     assert (this.ctx.executor().inEventLoop());
/*  54 */     return this.head == null;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public int size()
/*     */   {
/*  61 */     assert (this.ctx.executor().inEventLoop());
/*  62 */     return this.size;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public void add(Object paramObject, ChannelPromise paramChannelPromise)
/*     */   {
/*  69 */     assert (this.ctx.executor().inEventLoop());
/*  70 */     if (paramObject == null) {
/*  71 */       throw new NullPointerException("msg");
/*     */     }
/*  73 */     if (paramChannelPromise == null) {
/*  74 */       throw new NullPointerException("promise");
/*     */     }
/*  76 */     int i = this.estimatorHandle.size(paramObject);
/*  77 */     if (i < 0)
/*     */     {
/*  79 */       i = 0;
/*     */     }
/*  81 */     PendingWrite localPendingWrite1 = PendingWrite.newInstance(paramObject, i, paramChannelPromise);
/*  82 */     PendingWrite localPendingWrite2 = this.tail;
/*  83 */     if (localPendingWrite2 == null) {
/*  84 */       this.tail = (this.head = localPendingWrite1);
/*     */     } else {
/*  86 */       localPendingWrite2.next = localPendingWrite1;
/*  87 */       this.tail = localPendingWrite1;
/*     */     }
/*  89 */     this.size += 1;
/*  90 */     this.buffer.incrementPendingOutboundBytes(localPendingWrite1.size);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public void removeAndFailAll(Throwable paramThrowable)
/*     */   {
/*  98 */     assert (this.ctx.executor().inEventLoop());
/*  99 */     if (paramThrowable == null) {
/* 100 */       throw new NullPointerException("cause");
/*     */     }
/* 102 */     Object localObject = this.head;
/* 103 */     while (localObject != null) {
/* 104 */       PendingWrite localPendingWrite = ((PendingWrite)localObject).next;
/* 105 */       ReferenceCountUtil.safeRelease(((PendingWrite)localObject).msg);
/* 106 */       ChannelPromise localChannelPromise = ((PendingWrite)localObject).promise;
/* 107 */       recycle((PendingWrite)localObject);
/* 108 */       safeFail(localChannelPromise, paramThrowable);
/* 109 */       localObject = localPendingWrite;
/*     */     }
/* 111 */     assertEmpty();
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public void removeAndFail(Throwable paramThrowable)
/*     */   {
/* 119 */     assert (this.ctx.executor().inEventLoop());
/* 120 */     if (paramThrowable == null) {
/* 121 */       throw new NullPointerException("cause");
/*     */     }
/* 123 */     PendingWrite localPendingWrite = this.head;
/* 124 */     if (localPendingWrite == null) {
/* 125 */       return;
/*     */     }
/* 127 */     ReferenceCountUtil.safeRelease(localPendingWrite.msg);
/* 128 */     ChannelPromise localChannelPromise = localPendingWrite.promise;
/* 129 */     safeFail(localChannelPromise, paramThrowable);
/* 130 */     recycle(localPendingWrite);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public ChannelFuture removeAndWriteAll()
/*     */   {
/* 141 */     assert (this.ctx.executor().inEventLoop());
/* 142 */     Object localObject1 = this.head;
/* 143 */     if (localObject1 == null)
/*     */     {
/* 145 */       return null;
/*     */     }
/* 147 */     if (this.size == 1)
/*     */     {
/* 149 */       return removeAndWrite();
/*     */     }
/* 151 */     ChannelPromise localChannelPromise1 = this.ctx.newPromise();
/* 152 */     ChannelPromiseAggregator localChannelPromiseAggregator = new ChannelPromiseAggregator(localChannelPromise1);
/* 153 */     while (localObject1 != null) {
/* 154 */       PendingWrite localPendingWrite = ((PendingWrite)localObject1).next;
/* 155 */       Object localObject2 = ((PendingWrite)localObject1).msg;
/* 156 */       ChannelPromise localChannelPromise2 = ((PendingWrite)localObject1).promise;
/* 157 */       recycle((PendingWrite)localObject1);
/* 158 */       this.ctx.write(localObject2, localChannelPromise2);
/* 159 */       localChannelPromiseAggregator.add(new ChannelPromise[] { localChannelPromise2 });
/* 160 */       localObject1 = localPendingWrite;
/*     */     }
/* 162 */     assertEmpty();
/* 163 */     return localChannelPromise1;
/*     */   }
/*     */   
/*     */   private void assertEmpty() {
/* 167 */     assert ((this.tail == null) && (this.head == null) && (this.size == 0));
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public ChannelFuture removeAndWrite()
/*     */   {
/* 178 */     assert (this.ctx.executor().inEventLoop());
/* 179 */     PendingWrite localPendingWrite = this.head;
/* 180 */     if (localPendingWrite == null) {
/* 181 */       return null;
/*     */     }
/* 183 */     Object localObject = localPendingWrite.msg;
/* 184 */     ChannelPromise localChannelPromise = localPendingWrite.promise;
/* 185 */     recycle(localPendingWrite);
/* 186 */     return this.ctx.write(localObject, localChannelPromise);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public ChannelPromise remove()
/*     */   {
/* 196 */     assert (this.ctx.executor().inEventLoop());
/* 197 */     PendingWrite localPendingWrite = this.head;
/* 198 */     if (localPendingWrite == null) {
/* 199 */       return null;
/*     */     }
/* 201 */     ChannelPromise localChannelPromise = localPendingWrite.promise;
/* 202 */     ReferenceCountUtil.safeRelease(localPendingWrite.msg);
/* 203 */     recycle(localPendingWrite);
/* 204 */     return localChannelPromise;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public Object current()
/*     */   {
/* 211 */     assert (this.ctx.executor().inEventLoop());
/* 212 */     PendingWrite localPendingWrite = this.head;
/* 213 */     if (localPendingWrite == null) {
/* 214 */       return null;
/*     */     }
/* 216 */     return localPendingWrite.msg;
/*     */   }
/*     */   
/*     */   private void recycle(PendingWrite paramPendingWrite) {
/* 220 */     PendingWrite localPendingWrite = paramPendingWrite.next;
/*     */     
/* 222 */     this.buffer.decrementPendingOutboundBytes(paramPendingWrite.size);
/* 223 */     paramPendingWrite.recycle();
/* 224 */     this.size -= 1;
/* 225 */     if (localPendingWrite == null)
/*     */     {
/* 227 */       this.head = (this.tail = null);
/* 228 */       if ((!$assertionsDisabled) && (this.size != 0)) throw new AssertionError();
/*     */     } else {
/* 230 */       this.head = localPendingWrite;
/* 231 */       assert (this.size > 0);
/*     */     }
/*     */   }
/*     */   
/*     */   private static void safeFail(ChannelPromise paramChannelPromise, Throwable paramThrowable) {
/* 236 */     if ((!(paramChannelPromise instanceof VoidChannelPromise)) && (!paramChannelPromise.tryFailure(paramThrowable))) {
/* 237 */       logger.warn("Failed to mark a promise as failure because it's done already: {}", paramChannelPromise, paramThrowable);
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   static final class PendingWrite
/*     */   {
/* 245 */     private static final Recycler<PendingWrite> RECYCLER = new Recycler()
/*     */     {
/*     */       protected PendingWriteQueue.PendingWrite newObject(Recycler.Handle paramAnonymousHandle) {
/* 248 */         return new PendingWriteQueue.PendingWrite(paramAnonymousHandle, null);
/*     */       }
/*     */     };
/*     */     private final Recycler.Handle handle;
/*     */     private PendingWrite next;
/*     */     private long size;
/*     */     private ChannelPromise promise;
/*     */     private Object msg;
/*     */     
/*     */     private PendingWrite(Recycler.Handle paramHandle)
/*     */     {
/* 259 */       this.handle = paramHandle;
/*     */     }
/*     */     
/*     */     static PendingWrite newInstance(Object paramObject, int paramInt, ChannelPromise paramChannelPromise) {
/* 263 */       PendingWrite localPendingWrite = (PendingWrite)RECYCLER.get();
/* 264 */       localPendingWrite.size = paramInt;
/* 265 */       localPendingWrite.msg = paramObject;
/* 266 */       localPendingWrite.promise = paramChannelPromise;
/* 267 */       return localPendingWrite;
/*     */     }
/*     */     
/*     */     private void recycle() {
/* 271 */       this.size = 0L;
/* 272 */       this.next = null;
/* 273 */       this.msg = null;
/* 274 */       this.promise = null;
/* 275 */       RECYCLER.recycle(this, this.handle);
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\io\netty\channel\PendingWriteQueue.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */
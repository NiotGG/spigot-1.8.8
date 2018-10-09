/*     */ package io.netty.channel;
/*     */ 
/*     */ import io.netty.util.concurrent.AbstractEventExecutorGroup;
/*     */ import io.netty.util.concurrent.DefaultPromise;
/*     */ import io.netty.util.concurrent.EventExecutor;
/*     */ import io.netty.util.concurrent.Future;
/*     */ import io.netty.util.concurrent.FutureListener;
/*     */ import io.netty.util.concurrent.GlobalEventExecutor;
/*     */ import io.netty.util.concurrent.Promise;
/*     */ import io.netty.util.internal.EmptyArrays;
/*     */ import io.netty.util.internal.PlatformDependent;
/*     */ import io.netty.util.internal.ReadOnlyIterator;
/*     */ import java.util.Collections;
/*     */ import java.util.Iterator;
/*     */ import java.util.Queue;
/*     */ import java.util.Set;
/*     */ import java.util.concurrent.ConcurrentLinkedQueue;
/*     */ import java.util.concurrent.Executors;
/*     */ import java.util.concurrent.RejectedExecutionException;
/*     */ import java.util.concurrent.ThreadFactory;
/*     */ import java.util.concurrent.TimeUnit;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class ThreadPerChannelEventLoopGroup
/*     */   extends AbstractEventExecutorGroup
/*     */   implements EventLoopGroup
/*     */ {
/*     */   private final Object[] childArgs;
/*     */   private final int maxChannels;
/*     */   final ThreadFactory threadFactory;
/*  48 */   final Set<ThreadPerChannelEventLoop> activeChildren = Collections.newSetFromMap(PlatformDependent.newConcurrentHashMap());
/*     */   
/*  50 */   final Queue<ThreadPerChannelEventLoop> idleChildren = new ConcurrentLinkedQueue();
/*     */   
/*     */   private final ChannelException tooManyChannels;
/*     */   private volatile boolean shuttingDown;
/*  54 */   private final Promise<?> terminationFuture = new DefaultPromise(GlobalEventExecutor.INSTANCE);
/*  55 */   private final FutureListener<Object> childTerminationListener = new FutureListener()
/*     */   {
/*     */     public void operationComplete(Future<Object> paramAnonymousFuture) throws Exception
/*     */     {
/*  59 */       if (ThreadPerChannelEventLoopGroup.this.isTerminated()) {
/*  60 */         ThreadPerChannelEventLoopGroup.this.terminationFuture.trySuccess(null);
/*     */       }
/*     */     }
/*     */   };
/*     */   
/*     */ 
/*     */ 
/*     */   protected ThreadPerChannelEventLoopGroup()
/*     */   {
/*  69 */     this(0);
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
/*     */   protected ThreadPerChannelEventLoopGroup(int paramInt)
/*     */   {
/*  82 */     this(paramInt, Executors.defaultThreadFactory(), new Object[0]);
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
/*     */   protected ThreadPerChannelEventLoopGroup(int paramInt, ThreadFactory paramThreadFactory, Object... paramVarArgs)
/*     */   {
/*  98 */     if (paramInt < 0) {
/*  99 */       throw new IllegalArgumentException(String.format("maxChannels: %d (expected: >= 0)", new Object[] { Integer.valueOf(paramInt) }));
/*     */     }
/*     */     
/* 102 */     if (paramThreadFactory == null) {
/* 103 */       throw new NullPointerException("threadFactory");
/*     */     }
/*     */     
/* 106 */     if (paramVarArgs == null) {
/* 107 */       this.childArgs = EmptyArrays.EMPTY_OBJECTS;
/*     */     } else {
/* 109 */       this.childArgs = ((Object[])paramVarArgs.clone());
/*     */     }
/*     */     
/* 112 */     this.maxChannels = paramInt;
/* 113 */     this.threadFactory = paramThreadFactory;
/*     */     
/* 115 */     this.tooManyChannels = new ChannelException("too many channels (max: " + paramInt + ')');
/* 116 */     this.tooManyChannels.setStackTrace(EmptyArrays.EMPTY_STACK_TRACE);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   protected ThreadPerChannelEventLoop newChild(Object... paramVarArgs)
/*     */     throws Exception
/*     */   {
/* 124 */     return new ThreadPerChannelEventLoop(this);
/*     */   }
/*     */   
/*     */   public Iterator<EventExecutor> iterator()
/*     */   {
/* 129 */     return new ReadOnlyIterator(this.activeChildren.iterator());
/*     */   }
/*     */   
/*     */   public EventLoop next()
/*     */   {
/* 134 */     throw new UnsupportedOperationException();
/*     */   }
/*     */   
/*     */   public Future<?> shutdownGracefully(long paramLong1, long paramLong2, TimeUnit paramTimeUnit)
/*     */   {
/* 139 */     this.shuttingDown = true;
/*     */     
/* 141 */     for (Iterator localIterator = this.activeChildren.iterator(); localIterator.hasNext();) { localEventLoop = (EventLoop)localIterator.next();
/* 142 */       localEventLoop.shutdownGracefully(paramLong1, paramLong2, paramTimeUnit); }
/*     */     EventLoop localEventLoop;
/* 144 */     for (localIterator = this.idleChildren.iterator(); localIterator.hasNext();) { localEventLoop = (EventLoop)localIterator.next();
/* 145 */       localEventLoop.shutdownGracefully(paramLong1, paramLong2, paramTimeUnit);
/*     */     }
/*     */     
/*     */ 
/* 149 */     if (isTerminated()) {
/* 150 */       this.terminationFuture.trySuccess(null);
/*     */     }
/*     */     
/* 153 */     return terminationFuture();
/*     */   }
/*     */   
/*     */   public Future<?> terminationFuture()
/*     */   {
/* 158 */     return this.terminationFuture;
/*     */   }
/*     */   
/*     */   @Deprecated
/*     */   public void shutdown()
/*     */   {
/* 164 */     this.shuttingDown = true;
/*     */     
/* 166 */     for (Iterator localIterator = this.activeChildren.iterator(); localIterator.hasNext();) { localEventLoop = (EventLoop)localIterator.next();
/* 167 */       localEventLoop.shutdown(); }
/*     */     EventLoop localEventLoop;
/* 169 */     for (localIterator = this.idleChildren.iterator(); localIterator.hasNext();) { localEventLoop = (EventLoop)localIterator.next();
/* 170 */       localEventLoop.shutdown();
/*     */     }
/*     */     
/*     */ 
/* 174 */     if (isTerminated()) {
/* 175 */       this.terminationFuture.trySuccess(null);
/*     */     }
/*     */   }
/*     */   
/*     */   public boolean isShuttingDown()
/*     */   {
/* 181 */     for (Iterator localIterator = this.activeChildren.iterator(); localIterator.hasNext();) { localEventLoop = (EventLoop)localIterator.next();
/* 182 */       if (!localEventLoop.isShuttingDown())
/* 183 */         return false;
/*     */     }
/*     */     EventLoop localEventLoop;
/* 186 */     for (localIterator = this.idleChildren.iterator(); localIterator.hasNext();) { localEventLoop = (EventLoop)localIterator.next();
/* 187 */       if (!localEventLoop.isShuttingDown()) {
/* 188 */         return false;
/*     */       }
/*     */     }
/* 191 */     return true;
/*     */   }
/*     */   
/*     */   public boolean isShutdown()
/*     */   {
/* 196 */     for (Iterator localIterator = this.activeChildren.iterator(); localIterator.hasNext();) { localEventLoop = (EventLoop)localIterator.next();
/* 197 */       if (!localEventLoop.isShutdown())
/* 198 */         return false;
/*     */     }
/*     */     EventLoop localEventLoop;
/* 201 */     for (localIterator = this.idleChildren.iterator(); localIterator.hasNext();) { localEventLoop = (EventLoop)localIterator.next();
/* 202 */       if (!localEventLoop.isShutdown()) {
/* 203 */         return false;
/*     */       }
/*     */     }
/* 206 */     return true;
/*     */   }
/*     */   
/*     */   public boolean isTerminated()
/*     */   {
/* 211 */     for (Iterator localIterator = this.activeChildren.iterator(); localIterator.hasNext();) { localEventLoop = (EventLoop)localIterator.next();
/* 212 */       if (!localEventLoop.isTerminated())
/* 213 */         return false;
/*     */     }
/*     */     EventLoop localEventLoop;
/* 216 */     for (localIterator = this.idleChildren.iterator(); localIterator.hasNext();) { localEventLoop = (EventLoop)localIterator.next();
/* 217 */       if (!localEventLoop.isTerminated()) {
/* 218 */         return false;
/*     */       }
/*     */     }
/* 221 */     return true;
/*     */   }
/*     */   
/*     */   public boolean awaitTermination(long paramLong, TimeUnit paramTimeUnit)
/*     */     throws InterruptedException
/*     */   {
/* 227 */     long l1 = System.nanoTime() + paramTimeUnit.toNanos(paramLong);
/* 228 */     for (Iterator localIterator = this.activeChildren.iterator(); localIterator.hasNext();) { localEventLoop = (EventLoop)localIterator.next();
/*     */       for (;;) {
/* 230 */         l2 = l1 - System.nanoTime();
/* 231 */         if (l2 <= 0L) {
/* 232 */           return isTerminated();
/*     */         }
/* 234 */         if (localEventLoop.awaitTermination(l2, TimeUnit.NANOSECONDS)) break;
/*     */       }
/*     */     }
/*     */     EventLoop localEventLoop;
/*     */     long l2;
/* 239 */     for (localIterator = this.idleChildren.iterator(); localIterator.hasNext();) { localEventLoop = (EventLoop)localIterator.next();
/*     */       for (;;) {
/* 241 */         l2 = l1 - System.nanoTime();
/* 242 */         if (l2 <= 0L) {
/* 243 */           return isTerminated();
/*     */         }
/* 245 */         if (localEventLoop.awaitTermination(l2, TimeUnit.NANOSECONDS)) {
/*     */           break;
/*     */         }
/*     */       }
/*     */     }
/* 250 */     return isTerminated();
/*     */   }
/*     */   
/*     */   public ChannelFuture register(Channel paramChannel)
/*     */   {
/* 255 */     if (paramChannel == null) {
/* 256 */       throw new NullPointerException("channel");
/*     */     }
/*     */     try {
/* 259 */       EventLoop localEventLoop = nextChild();
/* 260 */       return localEventLoop.register(paramChannel, new DefaultChannelPromise(paramChannel, localEventLoop));
/*     */     } catch (Throwable localThrowable) {
/* 262 */       return new FailedChannelFuture(paramChannel, GlobalEventExecutor.INSTANCE, localThrowable);
/*     */     }
/*     */   }
/*     */   
/*     */   public ChannelFuture register(Channel paramChannel, ChannelPromise paramChannelPromise)
/*     */   {
/* 268 */     if (paramChannel == null) {
/* 269 */       throw new NullPointerException("channel");
/*     */     }
/*     */     try {
/* 272 */       return nextChild().register(paramChannel, paramChannelPromise);
/*     */     } catch (Throwable localThrowable) {
/* 274 */       paramChannelPromise.setFailure(localThrowable); }
/* 275 */     return paramChannelPromise;
/*     */   }
/*     */   
/*     */   private EventLoop nextChild() throws Exception
/*     */   {
/* 280 */     if (this.shuttingDown) {
/* 281 */       throw new RejectedExecutionException("shutting down");
/*     */     }
/*     */     
/* 284 */     ThreadPerChannelEventLoop localThreadPerChannelEventLoop = (ThreadPerChannelEventLoop)this.idleChildren.poll();
/* 285 */     if (localThreadPerChannelEventLoop == null) {
/* 286 */       if ((this.maxChannels > 0) && (this.activeChildren.size() >= this.maxChannels)) {
/* 287 */         throw this.tooManyChannels;
/*     */       }
/* 289 */       localThreadPerChannelEventLoop = newChild(this.childArgs);
/* 290 */       localThreadPerChannelEventLoop.terminationFuture().addListener(this.childTerminationListener);
/*     */     }
/* 292 */     this.activeChildren.add(localThreadPerChannelEventLoop);
/* 293 */     return localThreadPerChannelEventLoop;
/*     */   }
/*     */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\io\netty\channel\ThreadPerChannelEventLoopGroup.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */
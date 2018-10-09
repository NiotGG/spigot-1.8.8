/*     */ package io.netty.channel.embedded;
/*     */ 
/*     */ import io.netty.channel.Channel;
/*     */ import io.netty.channel.Channel.Unsafe;
/*     */ import io.netty.channel.ChannelFuture;
/*     */ import io.netty.channel.ChannelPromise;
/*     */ import io.netty.channel.DefaultChannelPromise;
/*     */ import io.netty.channel.EventLoop;
/*     */ import io.netty.channel.EventLoopGroup;
/*     */ import io.netty.util.concurrent.AbstractEventExecutor;
/*     */ import io.netty.util.concurrent.Future;
/*     */ import java.util.ArrayDeque;
/*     */ import java.util.Queue;
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
/*     */ final class EmbeddedEventLoop
/*     */   extends AbstractEventExecutor
/*     */   implements EventLoop
/*     */ {
/*  33 */   private final Queue<Runnable> tasks = new ArrayDeque(2);
/*     */   
/*     */   public void execute(Runnable paramRunnable)
/*     */   {
/*  37 */     if (paramRunnable == null) {
/*  38 */       throw new NullPointerException("command");
/*     */     }
/*  40 */     this.tasks.add(paramRunnable);
/*     */   }
/*     */   
/*     */   void runTasks() {
/*     */     for (;;) {
/*  45 */       Runnable localRunnable = (Runnable)this.tasks.poll();
/*  46 */       if (localRunnable == null) {
/*     */         break;
/*     */       }
/*     */       
/*  50 */       localRunnable.run();
/*     */     }
/*     */   }
/*     */   
/*     */   public Future<?> shutdownGracefully(long paramLong1, long paramLong2, TimeUnit paramTimeUnit)
/*     */   {
/*  56 */     throw new UnsupportedOperationException();
/*     */   }
/*     */   
/*     */   public Future<?> terminationFuture()
/*     */   {
/*  61 */     throw new UnsupportedOperationException();
/*     */   }
/*     */   
/*     */   @Deprecated
/*     */   public void shutdown()
/*     */   {
/*  67 */     throw new UnsupportedOperationException();
/*     */   }
/*     */   
/*     */   public boolean isShuttingDown()
/*     */   {
/*  72 */     return false;
/*     */   }
/*     */   
/*     */   public boolean isShutdown()
/*     */   {
/*  77 */     return false;
/*     */   }
/*     */   
/*     */   public boolean isTerminated()
/*     */   {
/*  82 */     return false;
/*     */   }
/*     */   
/*     */   public boolean awaitTermination(long paramLong, TimeUnit paramTimeUnit)
/*     */     throws InterruptedException
/*     */   {
/*  88 */     Thread.sleep(paramTimeUnit.toMillis(paramLong));
/*  89 */     return false;
/*     */   }
/*     */   
/*     */   public ChannelFuture register(Channel paramChannel)
/*     */   {
/*  94 */     return register(paramChannel, new DefaultChannelPromise(paramChannel, this));
/*     */   }
/*     */   
/*     */   public ChannelFuture register(Channel paramChannel, ChannelPromise paramChannelPromise)
/*     */   {
/*  99 */     paramChannel.unsafe().register(this, paramChannelPromise);
/* 100 */     return paramChannelPromise;
/*     */   }
/*     */   
/*     */   public boolean inEventLoop()
/*     */   {
/* 105 */     return true;
/*     */   }
/*     */   
/*     */   public boolean inEventLoop(Thread paramThread)
/*     */   {
/* 110 */     return true;
/*     */   }
/*     */   
/*     */   public EventLoop next()
/*     */   {
/* 115 */     return this;
/*     */   }
/*     */   
/*     */   public EventLoopGroup parent()
/*     */   {
/* 120 */     return this;
/*     */   }
/*     */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\io\netty\channel\embedded\EmbeddedEventLoop.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */
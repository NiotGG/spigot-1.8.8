/*     */ package io.netty.handler.timeout;
/*     */ 
/*     */ import io.netty.channel.Channel;
/*     */ import io.netty.channel.ChannelDuplexHandler;
/*     */ import io.netty.channel.ChannelFuture;
/*     */ import io.netty.channel.ChannelFutureListener;
/*     */ import io.netty.channel.ChannelHandlerContext;
/*     */ import io.netty.channel.ChannelPromise;
/*     */ import io.netty.util.concurrent.EventExecutor;
/*     */ import java.util.concurrent.ScheduledFuture;
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
/*     */ public class IdleStateHandler
/*     */   extends ChannelDuplexHandler
/*     */ {
/*  98 */   private static final long MIN_TIMEOUT_NANOS = TimeUnit.MILLISECONDS.toNanos(1L);
/*     */   
/*     */   private final long readerIdleTimeNanos;
/*     */   
/*     */   private final long writerIdleTimeNanos;
/*     */   private final long allIdleTimeNanos;
/*     */   volatile ScheduledFuture<?> readerIdleTimeout;
/*     */   volatile long lastReadTime;
/* 106 */   private boolean firstReaderIdleEvent = true;
/*     */   
/*     */   volatile ScheduledFuture<?> writerIdleTimeout;
/*     */   volatile long lastWriteTime;
/* 110 */   private boolean firstWriterIdleEvent = true;
/*     */   
/*     */   volatile ScheduledFuture<?> allIdleTimeout;
/* 113 */   private boolean firstAllIdleEvent = true;
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
/*     */   private volatile int state;
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
/*     */   public IdleStateHandler(int paramInt1, int paramInt2, int paramInt3)
/*     */   {
/* 138 */     this(paramInt1, paramInt2, paramInt3, TimeUnit.SECONDS);
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
/*     */ 
/*     */ 
/*     */   public IdleStateHandler(long paramLong1, long paramLong2, long paramLong3, TimeUnit paramTimeUnit)
/*     */   {
/* 164 */     if (paramTimeUnit == null) {
/* 165 */       throw new NullPointerException("unit");
/*     */     }
/*     */     
/* 168 */     if (paramLong1 <= 0L) {
/* 169 */       this.readerIdleTimeNanos = 0L;
/*     */     } else {
/* 171 */       this.readerIdleTimeNanos = Math.max(paramTimeUnit.toNanos(paramLong1), MIN_TIMEOUT_NANOS);
/*     */     }
/* 173 */     if (paramLong2 <= 0L) {
/* 174 */       this.writerIdleTimeNanos = 0L;
/*     */     } else {
/* 176 */       this.writerIdleTimeNanos = Math.max(paramTimeUnit.toNanos(paramLong2), MIN_TIMEOUT_NANOS);
/*     */     }
/* 178 */     if (paramLong3 <= 0L) {
/* 179 */       this.allIdleTimeNanos = 0L;
/*     */     } else {
/* 181 */       this.allIdleTimeNanos = Math.max(paramTimeUnit.toNanos(paramLong3), MIN_TIMEOUT_NANOS);
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public long getReaderIdleTimeInMillis()
/*     */   {
/* 190 */     return TimeUnit.NANOSECONDS.toMillis(this.readerIdleTimeNanos);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public long getWriterIdleTimeInMillis()
/*     */   {
/* 198 */     return TimeUnit.NANOSECONDS.toMillis(this.writerIdleTimeNanos);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public long getAllIdleTimeInMillis()
/*     */   {
/* 206 */     return TimeUnit.NANOSECONDS.toMillis(this.allIdleTimeNanos);
/*     */   }
/*     */   
/*     */   public void handlerAdded(ChannelHandlerContext paramChannelHandlerContext) throws Exception
/*     */   {
/* 211 */     if ((paramChannelHandlerContext.channel().isActive()) && (paramChannelHandlerContext.channel().isRegistered()))
/*     */     {
/*     */ 
/* 214 */       initialize(paramChannelHandlerContext);
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public void handlerRemoved(ChannelHandlerContext paramChannelHandlerContext)
/*     */     throws Exception
/*     */   {
/* 223 */     destroy();
/*     */   }
/*     */   
/*     */   public void channelRegistered(ChannelHandlerContext paramChannelHandlerContext)
/*     */     throws Exception
/*     */   {
/* 229 */     if (paramChannelHandlerContext.channel().isActive()) {
/* 230 */       initialize(paramChannelHandlerContext);
/*     */     }
/* 232 */     super.channelRegistered(paramChannelHandlerContext);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public void channelActive(ChannelHandlerContext paramChannelHandlerContext)
/*     */     throws Exception
/*     */   {
/* 240 */     initialize(paramChannelHandlerContext);
/* 241 */     super.channelActive(paramChannelHandlerContext);
/*     */   }
/*     */   
/*     */   public void channelInactive(ChannelHandlerContext paramChannelHandlerContext) throws Exception
/*     */   {
/* 246 */     destroy();
/* 247 */     super.channelInactive(paramChannelHandlerContext);
/*     */   }
/*     */   
/*     */   public void channelRead(ChannelHandlerContext paramChannelHandlerContext, Object paramObject) throws Exception
/*     */   {
/* 252 */     this.lastReadTime = System.nanoTime();
/* 253 */     this.firstReaderIdleEvent = (this.firstAllIdleEvent = 1);
/* 254 */     paramChannelHandlerContext.fireChannelRead(paramObject);
/*     */   }
/*     */   
/*     */   public void write(ChannelHandlerContext paramChannelHandlerContext, Object paramObject, ChannelPromise paramChannelPromise) throws Exception
/*     */   {
/* 259 */     paramChannelPromise.addListener(new ChannelFutureListener()
/*     */     {
/*     */       public void operationComplete(ChannelFuture paramAnonymousChannelFuture) throws Exception {
/* 262 */         IdleStateHandler.this.lastWriteTime = System.nanoTime();
/* 263 */         IdleStateHandler.this.firstWriterIdleEvent = IdleStateHandler.access$102(IdleStateHandler.this, true);
/*     */       }
/* 265 */     });
/* 266 */     paramChannelHandlerContext.write(paramObject, paramChannelPromise);
/*     */   }
/*     */   
/*     */ 
/*     */   private void initialize(ChannelHandlerContext paramChannelHandlerContext)
/*     */   {
/* 272 */     switch (this.state) {
/*     */     case 1: 
/*     */     case 2: 
/* 275 */       return;
/*     */     }
/*     */     
/* 278 */     this.state = 1;
/*     */     
/* 280 */     EventExecutor localEventExecutor = paramChannelHandlerContext.executor();
/*     */     
/* 282 */     this.lastReadTime = (this.lastWriteTime = System.nanoTime());
/* 283 */     if (this.readerIdleTimeNanos > 0L) {
/* 284 */       this.readerIdleTimeout = localEventExecutor.schedule(new ReaderIdleTimeoutTask(paramChannelHandlerContext), this.readerIdleTimeNanos, TimeUnit.NANOSECONDS);
/*     */     }
/*     */     
/*     */ 
/* 288 */     if (this.writerIdleTimeNanos > 0L) {
/* 289 */       this.writerIdleTimeout = localEventExecutor.schedule(new WriterIdleTimeoutTask(paramChannelHandlerContext), this.writerIdleTimeNanos, TimeUnit.NANOSECONDS);
/*     */     }
/*     */     
/*     */ 
/* 293 */     if (this.allIdleTimeNanos > 0L) {
/* 294 */       this.allIdleTimeout = localEventExecutor.schedule(new AllIdleTimeoutTask(paramChannelHandlerContext), this.allIdleTimeNanos, TimeUnit.NANOSECONDS);
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */   private void destroy()
/*     */   {
/* 301 */     this.state = 2;
/*     */     
/* 303 */     if (this.readerIdleTimeout != null) {
/* 304 */       this.readerIdleTimeout.cancel(false);
/* 305 */       this.readerIdleTimeout = null;
/*     */     }
/* 307 */     if (this.writerIdleTimeout != null) {
/* 308 */       this.writerIdleTimeout.cancel(false);
/* 309 */       this.writerIdleTimeout = null;
/*     */     }
/* 311 */     if (this.allIdleTimeout != null) {
/* 312 */       this.allIdleTimeout.cancel(false);
/* 313 */       this.allIdleTimeout = null;
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   protected void channelIdle(ChannelHandlerContext paramChannelHandlerContext, IdleStateEvent paramIdleStateEvent)
/*     */     throws Exception
/*     */   {
/* 322 */     paramChannelHandlerContext.fireUserEventTriggered(paramIdleStateEvent);
/*     */   }
/*     */   
/*     */   private final class ReaderIdleTimeoutTask implements Runnable
/*     */   {
/*     */     private final ChannelHandlerContext ctx;
/*     */     
/*     */     ReaderIdleTimeoutTask(ChannelHandlerContext paramChannelHandlerContext) {
/* 330 */       this.ctx = paramChannelHandlerContext;
/*     */     }
/*     */     
/*     */     public void run()
/*     */     {
/* 335 */       if (!this.ctx.channel().isOpen()) {
/* 336 */         return;
/*     */       }
/*     */       
/* 339 */       long l1 = System.nanoTime();
/* 340 */       long l2 = IdleStateHandler.this.lastReadTime;
/* 341 */       long l3 = IdleStateHandler.this.readerIdleTimeNanos - (l1 - l2);
/* 342 */       if (l3 <= 0L)
/*     */       {
/* 344 */         IdleStateHandler.this.readerIdleTimeout = this.ctx.executor().schedule(this, IdleStateHandler.this.readerIdleTimeNanos, TimeUnit.NANOSECONDS);
/*     */         try
/*     */         {
/*     */           IdleStateEvent localIdleStateEvent;
/* 348 */           if (IdleStateHandler.this.firstReaderIdleEvent) {
/* 349 */             IdleStateHandler.this.firstReaderIdleEvent = false;
/* 350 */             localIdleStateEvent = IdleStateEvent.FIRST_READER_IDLE_STATE_EVENT;
/*     */           } else {
/* 352 */             localIdleStateEvent = IdleStateEvent.READER_IDLE_STATE_EVENT;
/*     */           }
/* 354 */           IdleStateHandler.this.channelIdle(this.ctx, localIdleStateEvent);
/*     */         } catch (Throwable localThrowable) {
/* 356 */           this.ctx.fireExceptionCaught(localThrowable);
/*     */         }
/*     */       }
/*     */       else {
/* 360 */         IdleStateHandler.this.readerIdleTimeout = this.ctx.executor().schedule(this, l3, TimeUnit.NANOSECONDS);
/*     */       }
/*     */     }
/*     */   }
/*     */   
/*     */   private final class WriterIdleTimeoutTask implements Runnable
/*     */   {
/*     */     private final ChannelHandlerContext ctx;
/*     */     
/*     */     WriterIdleTimeoutTask(ChannelHandlerContext paramChannelHandlerContext) {
/* 370 */       this.ctx = paramChannelHandlerContext;
/*     */     }
/*     */     
/*     */     public void run()
/*     */     {
/* 375 */       if (!this.ctx.channel().isOpen()) {
/* 376 */         return;
/*     */       }
/*     */       
/* 379 */       long l1 = System.nanoTime();
/* 380 */       long l2 = IdleStateHandler.this.lastWriteTime;
/* 381 */       long l3 = IdleStateHandler.this.writerIdleTimeNanos - (l1 - l2);
/* 382 */       if (l3 <= 0L)
/*     */       {
/* 384 */         IdleStateHandler.this.writerIdleTimeout = this.ctx.executor().schedule(this, IdleStateHandler.this.writerIdleTimeNanos, TimeUnit.NANOSECONDS);
/*     */         try
/*     */         {
/*     */           IdleStateEvent localIdleStateEvent;
/* 388 */           if (IdleStateHandler.this.firstWriterIdleEvent) {
/* 389 */             IdleStateHandler.this.firstWriterIdleEvent = false;
/* 390 */             localIdleStateEvent = IdleStateEvent.FIRST_WRITER_IDLE_STATE_EVENT;
/*     */           } else {
/* 392 */             localIdleStateEvent = IdleStateEvent.WRITER_IDLE_STATE_EVENT;
/*     */           }
/* 394 */           IdleStateHandler.this.channelIdle(this.ctx, localIdleStateEvent);
/*     */         } catch (Throwable localThrowable) {
/* 396 */           this.ctx.fireExceptionCaught(localThrowable);
/*     */         }
/*     */       }
/*     */       else {
/* 400 */         IdleStateHandler.this.writerIdleTimeout = this.ctx.executor().schedule(this, l3, TimeUnit.NANOSECONDS);
/*     */       }
/*     */     }
/*     */   }
/*     */   
/*     */   private final class AllIdleTimeoutTask implements Runnable
/*     */   {
/*     */     private final ChannelHandlerContext ctx;
/*     */     
/*     */     AllIdleTimeoutTask(ChannelHandlerContext paramChannelHandlerContext) {
/* 410 */       this.ctx = paramChannelHandlerContext;
/*     */     }
/*     */     
/*     */     public void run()
/*     */     {
/* 415 */       if (!this.ctx.channel().isOpen()) {
/* 416 */         return;
/*     */       }
/*     */       
/* 419 */       long l1 = System.nanoTime();
/* 420 */       long l2 = Math.max(IdleStateHandler.this.lastReadTime, IdleStateHandler.this.lastWriteTime);
/* 421 */       long l3 = IdleStateHandler.this.allIdleTimeNanos - (l1 - l2);
/* 422 */       if (l3 <= 0L)
/*     */       {
/*     */ 
/* 425 */         IdleStateHandler.this.allIdleTimeout = this.ctx.executor().schedule(this, IdleStateHandler.this.allIdleTimeNanos, TimeUnit.NANOSECONDS);
/*     */         try
/*     */         {
/*     */           IdleStateEvent localIdleStateEvent;
/* 429 */           if (IdleStateHandler.this.firstAllIdleEvent) {
/* 430 */             IdleStateHandler.this.firstAllIdleEvent = false;
/* 431 */             localIdleStateEvent = IdleStateEvent.FIRST_ALL_IDLE_STATE_EVENT;
/*     */           } else {
/* 433 */             localIdleStateEvent = IdleStateEvent.ALL_IDLE_STATE_EVENT;
/*     */           }
/* 435 */           IdleStateHandler.this.channelIdle(this.ctx, localIdleStateEvent);
/*     */         } catch (Throwable localThrowable) {
/* 437 */           this.ctx.fireExceptionCaught(localThrowable);
/*     */         }
/*     */       }
/*     */       else
/*     */       {
/* 442 */         IdleStateHandler.this.allIdleTimeout = this.ctx.executor().schedule(this, l3, TimeUnit.NANOSECONDS);
/*     */       }
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\io\netty\handler\timeout\IdleStateHandler.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */
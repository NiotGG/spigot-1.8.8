/*     */ package io.netty.handler.timeout;
/*     */ 
/*     */ import io.netty.channel.ChannelFuture;
/*     */ import io.netty.channel.ChannelFutureListener;
/*     */ import io.netty.channel.ChannelHandlerContext;
/*     */ import io.netty.channel.ChannelOutboundHandlerAdapter;
/*     */ import io.netty.channel.ChannelPromise;
/*     */ import io.netty.util.concurrent.EventExecutor;
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
/*     */ public class WriteTimeoutHandler
/*     */   extends ChannelOutboundHandlerAdapter
/*     */ {
/*  68 */   private static final long MIN_TIMEOUT_NANOS = TimeUnit.MILLISECONDS.toNanos(1L);
/*     */   
/*     */ 
/*     */ 
/*     */   private final long timeoutNanos;
/*     */   
/*     */ 
/*     */   private boolean closed;
/*     */   
/*     */ 
/*     */ 
/*     */   public WriteTimeoutHandler(int paramInt)
/*     */   {
/*  81 */     this(paramInt, TimeUnit.SECONDS);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public WriteTimeoutHandler(long paramLong, TimeUnit paramTimeUnit)
/*     */   {
/*  93 */     if (paramTimeUnit == null) {
/*  94 */       throw new NullPointerException("unit");
/*     */     }
/*     */     
/*  97 */     if (paramLong <= 0L) {
/*  98 */       this.timeoutNanos = 0L;
/*     */     } else {
/* 100 */       this.timeoutNanos = Math.max(paramTimeUnit.toNanos(paramLong), MIN_TIMEOUT_NANOS);
/*     */     }
/*     */   }
/*     */   
/*     */   public void write(ChannelHandlerContext paramChannelHandlerContext, Object paramObject, ChannelPromise paramChannelPromise) throws Exception
/*     */   {
/* 106 */     scheduleTimeout(paramChannelHandlerContext, paramChannelPromise);
/* 107 */     paramChannelHandlerContext.write(paramObject, paramChannelPromise);
/*     */   }
/*     */   
/*     */   private void scheduleTimeout(final ChannelHandlerContext paramChannelHandlerContext, final ChannelPromise paramChannelPromise) {
/* 111 */     if (this.timeoutNanos > 0L)
/*     */     {
/* 113 */       final io.netty.util.concurrent.ScheduledFuture localScheduledFuture = paramChannelHandlerContext.executor().schedule(new Runnable()
/*     */       {
/*     */ 
/*     */         public void run()
/*     */         {
/*     */ 
/* 119 */           if (!paramChannelPromise.isDone())
/*     */             try {
/* 121 */               WriteTimeoutHandler.this.writeTimedOut(paramChannelHandlerContext);
/*     */             } catch (Throwable localThrowable) {
/* 123 */               paramChannelHandlerContext.fireExceptionCaught(localThrowable); } } }, this.timeoutNanos, TimeUnit.NANOSECONDS);
/*     */       
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/* 130 */       paramChannelPromise.addListener(new ChannelFutureListener()
/*     */       {
/*     */         public void operationComplete(ChannelFuture paramAnonymousChannelFuture) throws Exception {
/* 133 */           localScheduledFuture.cancel(false);
/*     */         }
/*     */       });
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */   protected void writeTimedOut(ChannelHandlerContext paramChannelHandlerContext)
/*     */     throws Exception
/*     */   {
/* 143 */     if (!this.closed) {
/* 144 */       paramChannelHandlerContext.fireExceptionCaught(WriteTimeoutException.INSTANCE);
/* 145 */       paramChannelHandlerContext.close();
/* 146 */       this.closed = true;
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\io\netty\handler\timeout\WriteTimeoutHandler.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */
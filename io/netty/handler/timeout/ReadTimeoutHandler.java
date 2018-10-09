/*     */ package io.netty.handler.timeout;
/*     */ 
/*     */ import io.netty.channel.Channel;
/*     */ import io.netty.channel.ChannelHandlerContext;
/*     */ import io.netty.channel.ChannelInboundHandlerAdapter;
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
/*     */ public class ReadTimeoutHandler
/*     */   extends ChannelInboundHandlerAdapter
/*     */ {
/*  65 */   private static final long MIN_TIMEOUT_NANOS = TimeUnit.MILLISECONDS.toNanos(1L);
/*     */   
/*     */ 
/*     */   private final long timeoutNanos;
/*     */   
/*     */ 
/*     */   private volatile ScheduledFuture<?> timeout;
/*     */   
/*     */ 
/*     */   private volatile long lastReadTime;
/*     */   
/*     */   private volatile int state;
/*     */   
/*     */   private boolean closed;
/*     */   
/*     */ 
/*     */   public ReadTimeoutHandler(int paramInt)
/*     */   {
/*  83 */     this(paramInt, TimeUnit.SECONDS);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public ReadTimeoutHandler(long paramLong, TimeUnit paramTimeUnit)
/*     */   {
/*  95 */     if (paramTimeUnit == null) {
/*  96 */       throw new NullPointerException("unit");
/*     */     }
/*     */     
/*  99 */     if (paramLong <= 0L) {
/* 100 */       this.timeoutNanos = 0L;
/*     */     } else {
/* 102 */       this.timeoutNanos = Math.max(paramTimeUnit.toNanos(paramLong), MIN_TIMEOUT_NANOS);
/*     */     }
/*     */   }
/*     */   
/*     */   public void handlerAdded(ChannelHandlerContext paramChannelHandlerContext) throws Exception
/*     */   {
/* 108 */     if ((paramChannelHandlerContext.channel().isActive()) && (paramChannelHandlerContext.channel().isRegistered()))
/*     */     {
/*     */ 
/* 111 */       initialize(paramChannelHandlerContext);
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public void handlerRemoved(ChannelHandlerContext paramChannelHandlerContext)
/*     */     throws Exception
/*     */   {
/* 120 */     destroy();
/*     */   }
/*     */   
/*     */   public void channelRegistered(ChannelHandlerContext paramChannelHandlerContext)
/*     */     throws Exception
/*     */   {
/* 126 */     if (paramChannelHandlerContext.channel().isActive()) {
/* 127 */       initialize(paramChannelHandlerContext);
/*     */     }
/* 129 */     super.channelRegistered(paramChannelHandlerContext);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public void channelActive(ChannelHandlerContext paramChannelHandlerContext)
/*     */     throws Exception
/*     */   {
/* 137 */     initialize(paramChannelHandlerContext);
/* 138 */     super.channelActive(paramChannelHandlerContext);
/*     */   }
/*     */   
/*     */   public void channelInactive(ChannelHandlerContext paramChannelHandlerContext) throws Exception
/*     */   {
/* 143 */     destroy();
/* 144 */     super.channelInactive(paramChannelHandlerContext);
/*     */   }
/*     */   
/*     */   public void channelRead(ChannelHandlerContext paramChannelHandlerContext, Object paramObject) throws Exception
/*     */   {
/* 149 */     this.lastReadTime = System.nanoTime();
/* 150 */     paramChannelHandlerContext.fireChannelRead(paramObject);
/*     */   }
/*     */   
/*     */ 
/*     */   private void initialize(ChannelHandlerContext paramChannelHandlerContext)
/*     */   {
/* 156 */     switch (this.state) {
/*     */     case 1: 
/*     */     case 2: 
/* 159 */       return;
/*     */     }
/*     */     
/* 162 */     this.state = 1;
/*     */     
/* 164 */     this.lastReadTime = System.nanoTime();
/* 165 */     if (this.timeoutNanos > 0L) {
/* 166 */       this.timeout = paramChannelHandlerContext.executor().schedule(new ReadTimeoutTask(paramChannelHandlerContext), this.timeoutNanos, TimeUnit.NANOSECONDS);
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */   private void destroy()
/*     */   {
/* 173 */     this.state = 2;
/*     */     
/* 175 */     if (this.timeout != null) {
/* 176 */       this.timeout.cancel(false);
/* 177 */       this.timeout = null;
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */   protected void readTimedOut(ChannelHandlerContext paramChannelHandlerContext)
/*     */     throws Exception
/*     */   {
/* 185 */     if (!this.closed) {
/* 186 */       paramChannelHandlerContext.fireExceptionCaught(ReadTimeoutException.INSTANCE);
/* 187 */       paramChannelHandlerContext.close();
/* 188 */       this.closed = true;
/*     */     }
/*     */   }
/*     */   
/*     */   private final class ReadTimeoutTask implements Runnable
/*     */   {
/*     */     private final ChannelHandlerContext ctx;
/*     */     
/*     */     ReadTimeoutTask(ChannelHandlerContext paramChannelHandlerContext) {
/* 197 */       this.ctx = paramChannelHandlerContext;
/*     */     }
/*     */     
/*     */     public void run()
/*     */     {
/* 202 */       if (!this.ctx.channel().isOpen()) {
/* 203 */         return;
/*     */       }
/*     */       
/* 206 */       long l1 = System.nanoTime();
/* 207 */       long l2 = ReadTimeoutHandler.this.timeoutNanos - (l1 - ReadTimeoutHandler.this.lastReadTime);
/* 208 */       if (l2 <= 0L)
/*     */       {
/* 210 */         ReadTimeoutHandler.this.timeout = this.ctx.executor().schedule(this, ReadTimeoutHandler.this.timeoutNanos, TimeUnit.NANOSECONDS);
/*     */         try {
/* 212 */           ReadTimeoutHandler.this.readTimedOut(this.ctx);
/*     */         } catch (Throwable localThrowable) {
/* 214 */           this.ctx.fireExceptionCaught(localThrowable);
/*     */         }
/*     */       }
/*     */       else {
/* 218 */         ReadTimeoutHandler.this.timeout = this.ctx.executor().schedule(this, l2, TimeUnit.NANOSECONDS);
/*     */       }
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\io\netty\handler\timeout\ReadTimeoutHandler.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */
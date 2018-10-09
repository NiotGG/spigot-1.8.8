/*     */ package io.netty.handler.traffic;
/*     */ 
/*     */ import io.netty.buffer.ByteBuf;
/*     */ import io.netty.buffer.ByteBufHolder;
/*     */ import io.netty.channel.Channel;
/*     */ import io.netty.channel.ChannelConfig;
/*     */ import io.netty.channel.ChannelDuplexHandler;
/*     */ import io.netty.channel.ChannelHandlerContext;
/*     */ import io.netty.channel.ChannelPromise;
/*     */ import io.netty.util.Attribute;
/*     */ import io.netty.util.AttributeKey;
/*     */ import io.netty.util.concurrent.EventExecutor;
/*     */ import io.netty.util.internal.logging.InternalLogger;
/*     */ import io.netty.util.internal.logging.InternalLoggerFactory;
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
/*     */ public abstract class AbstractTrafficShapingHandler
/*     */   extends ChannelDuplexHandler
/*     */ {
/*  45 */   private static final InternalLogger logger = InternalLoggerFactory.getInstance(AbstractTrafficShapingHandler.class);
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public static final long DEFAULT_CHECK_INTERVAL = 1000L;
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public static final long DEFAULT_MAX_TIME = 15000L;
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   static final long MINIMAL_WAIT = 10L;
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   protected TrafficCounter trafficCounter;
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   private long writeLimit;
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   private long readLimit;
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*  82 */   protected long maxTime = 15000L;
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*  87 */   protected long checkInterval = 1000L;
/*     */   
/*  89 */   private static final AttributeKey<Boolean> READ_SUSPENDED = AttributeKey.valueOf(AbstractTrafficShapingHandler.class.getName() + ".READ_SUSPENDED");
/*     */   
/*  91 */   private static final AttributeKey<Runnable> REOPEN_TASK = AttributeKey.valueOf(AbstractTrafficShapingHandler.class.getName() + ".REOPEN_TASK");
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   void setTrafficCounter(TrafficCounter paramTrafficCounter)
/*     */   {
/* 100 */     this.trafficCounter = paramTrafficCounter;
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
/*     */   protected AbstractTrafficShapingHandler(long paramLong1, long paramLong2, long paramLong3, long paramLong4)
/*     */   {
/* 115 */     this.writeLimit = paramLong1;
/* 116 */     this.readLimit = paramLong2;
/* 117 */     this.checkInterval = paramLong3;
/* 118 */     this.maxTime = paramLong4;
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
/*     */   protected AbstractTrafficShapingHandler(long paramLong1, long paramLong2, long paramLong3)
/*     */   {
/* 131 */     this(paramLong1, paramLong2, paramLong3, 15000L);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   protected AbstractTrafficShapingHandler(long paramLong1, long paramLong2)
/*     */   {
/* 143 */     this(paramLong1, paramLong2, 1000L, 15000L);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   protected AbstractTrafficShapingHandler()
/*     */   {
/* 150 */     this(0L, 0L, 1000L, 15000L);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   protected AbstractTrafficShapingHandler(long paramLong)
/*     */   {
/* 161 */     this(0L, 0L, paramLong, 15000L);
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
/*     */   public void configure(long paramLong1, long paramLong2, long paramLong3)
/*     */   {
/* 175 */     configure(paramLong1, paramLong2);
/* 176 */     configure(paramLong3);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public void configure(long paramLong1, long paramLong2)
/*     */   {
/* 188 */     this.writeLimit = paramLong1;
/* 189 */     this.readLimit = paramLong2;
/* 190 */     if (this.trafficCounter != null) {
/* 191 */       this.trafficCounter.resetAccounting(System.currentTimeMillis() + 1L);
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public void configure(long paramLong)
/*     */   {
/* 202 */     this.checkInterval = paramLong;
/* 203 */     if (this.trafficCounter != null) {
/* 204 */       this.trafficCounter.configure(this.checkInterval);
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public long getWriteLimit()
/*     */   {
/* 212 */     return this.writeLimit;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public void setWriteLimit(long paramLong)
/*     */   {
/* 219 */     this.writeLimit = paramLong;
/* 220 */     if (this.trafficCounter != null) {
/* 221 */       this.trafficCounter.resetAccounting(System.currentTimeMillis() + 1L);
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public long getReadLimit()
/*     */   {
/* 229 */     return this.readLimit;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public void setReadLimit(long paramLong)
/*     */   {
/* 236 */     this.readLimit = paramLong;
/* 237 */     if (this.trafficCounter != null) {
/* 238 */       this.trafficCounter.resetAccounting(System.currentTimeMillis() + 1L);
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public long getCheckInterval()
/*     */   {
/* 246 */     return this.checkInterval;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public void setCheckInterval(long paramLong)
/*     */   {
/* 253 */     this.checkInterval = paramLong;
/* 254 */     if (this.trafficCounter != null) {
/* 255 */       this.trafficCounter.configure(paramLong);
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public void setMaxTimeWait(long paramLong)
/*     */   {
/* 265 */     this.maxTime = paramLong;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public long getMaxTimeWait()
/*     */   {
/* 272 */     return this.maxTime;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   protected void doAccounting(TrafficCounter paramTrafficCounter) {}
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   private static final class ReopenReadTimerTask
/*     */     implements Runnable
/*     */   {
/*     */     final ChannelHandlerContext ctx;
/*     */     
/*     */ 
/*     */ 
/*     */ 
/*     */     ReopenReadTimerTask(ChannelHandlerContext paramChannelHandlerContext)
/*     */     {
/* 293 */       this.ctx = paramChannelHandlerContext;
/*     */     }
/*     */     
/*     */     public void run() {
/* 297 */       if ((!this.ctx.channel().config().isAutoRead()) && (AbstractTrafficShapingHandler.isHandlerActive(this.ctx)))
/*     */       {
/*     */ 
/* 300 */         if (AbstractTrafficShapingHandler.logger.isDebugEnabled()) {
/* 301 */           AbstractTrafficShapingHandler.logger.debug("Channel:" + this.ctx.channel().hashCode() + " Not Unsuspend: " + this.ctx.channel().config().isAutoRead() + ":" + AbstractTrafficShapingHandler.isHandlerActive(this.ctx));
/*     */         }
/*     */         
/* 304 */         this.ctx.attr(AbstractTrafficShapingHandler.READ_SUSPENDED).set(Boolean.valueOf(false));
/*     */       }
/*     */       else {
/* 307 */         if (AbstractTrafficShapingHandler.logger.isDebugEnabled()) {
/* 308 */           if ((this.ctx.channel().config().isAutoRead()) && (!AbstractTrafficShapingHandler.isHandlerActive(this.ctx))) {
/* 309 */             AbstractTrafficShapingHandler.logger.debug("Channel:" + this.ctx.channel().hashCode() + " Unsuspend: " + this.ctx.channel().config().isAutoRead() + ":" + AbstractTrafficShapingHandler.isHandlerActive(this.ctx));
/*     */           }
/*     */           else {
/* 312 */             AbstractTrafficShapingHandler.logger.debug("Channel:" + this.ctx.channel().hashCode() + " Normal Unsuspend: " + this.ctx.channel().config().isAutoRead() + ":" + AbstractTrafficShapingHandler.isHandlerActive(this.ctx));
/*     */           }
/*     */         }
/*     */         
/*     */ 
/* 317 */         this.ctx.attr(AbstractTrafficShapingHandler.READ_SUSPENDED).set(Boolean.valueOf(false));
/* 318 */         this.ctx.channel().config().setAutoRead(true);
/* 319 */         this.ctx.channel().read();
/*     */       }
/* 321 */       if (AbstractTrafficShapingHandler.logger.isDebugEnabled()) {
/* 322 */         AbstractTrafficShapingHandler.logger.debug("Channel:" + this.ctx.channel().hashCode() + " Unsupsend final status => " + this.ctx.channel().config().isAutoRead() + ":" + AbstractTrafficShapingHandler.isHandlerActive(this.ctx));
/*     */       }
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */   public void channelRead(ChannelHandlerContext paramChannelHandlerContext, Object paramObject)
/*     */     throws Exception
/*     */   {
/* 331 */     long l1 = calculateSize(paramObject);
/*     */     
/* 333 */     if ((l1 > 0L) && (this.trafficCounter != null))
/*     */     {
/* 335 */       long l2 = this.trafficCounter.readTimeToWait(l1, this.readLimit, this.maxTime);
/* 336 */       if (l2 >= 10L)
/*     */       {
/*     */ 
/* 339 */         if (logger.isDebugEnabled()) {
/* 340 */           logger.debug("Channel:" + paramChannelHandlerContext.channel().hashCode() + " Read Suspend: " + l2 + ":" + paramChannelHandlerContext.channel().config().isAutoRead() + ":" + isHandlerActive(paramChannelHandlerContext));
/*     */         }
/*     */         
/*     */ 
/* 344 */         if ((paramChannelHandlerContext.channel().config().isAutoRead()) && (isHandlerActive(paramChannelHandlerContext))) {
/* 345 */           paramChannelHandlerContext.channel().config().setAutoRead(false);
/* 346 */           paramChannelHandlerContext.attr(READ_SUSPENDED).set(Boolean.valueOf(true));
/*     */           
/*     */ 
/* 349 */           Attribute localAttribute = paramChannelHandlerContext.attr(REOPEN_TASK);
/* 350 */           Object localObject = (Runnable)localAttribute.get();
/* 351 */           if (localObject == null) {
/* 352 */             localObject = new ReopenReadTimerTask(paramChannelHandlerContext);
/* 353 */             localAttribute.set(localObject);
/*     */           }
/* 355 */           paramChannelHandlerContext.executor().schedule((Runnable)localObject, l2, TimeUnit.MILLISECONDS);
/* 356 */           if (logger.isDebugEnabled()) {
/* 357 */             logger.debug("Channel:" + paramChannelHandlerContext.channel().hashCode() + " Suspend final status => " + paramChannelHandlerContext.channel().config().isAutoRead() + ":" + isHandlerActive(paramChannelHandlerContext) + " will reopened at: " + l2);
/*     */           }
/*     */         }
/*     */       }
/*     */     }
/*     */     
/*     */ 
/*     */ 
/* 365 */     paramChannelHandlerContext.fireChannelRead(paramObject);
/*     */   }
/*     */   
/*     */   protected static boolean isHandlerActive(ChannelHandlerContext paramChannelHandlerContext) {
/* 369 */     Boolean localBoolean = (Boolean)paramChannelHandlerContext.attr(READ_SUSPENDED).get();
/* 370 */     return (localBoolean == null) || (Boolean.FALSE.equals(localBoolean));
/*     */   }
/*     */   
/*     */   public void read(ChannelHandlerContext paramChannelHandlerContext)
/*     */   {
/* 375 */     if (isHandlerActive(paramChannelHandlerContext))
/*     */     {
/* 377 */       paramChannelHandlerContext.read();
/*     */     }
/*     */   }
/*     */   
/*     */   public void write(ChannelHandlerContext paramChannelHandlerContext, Object paramObject, ChannelPromise paramChannelPromise)
/*     */     throws Exception
/*     */   {
/* 384 */     long l1 = calculateSize(paramObject);
/*     */     
/* 386 */     if ((l1 > 0L) && (this.trafficCounter != null))
/*     */     {
/* 388 */       long l2 = this.trafficCounter.writeTimeToWait(l1, this.writeLimit, this.maxTime);
/* 389 */       if (l2 >= 10L)
/*     */       {
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/* 397 */         if (logger.isDebugEnabled()) {
/* 398 */           logger.debug("Channel:" + paramChannelHandlerContext.channel().hashCode() + " Write suspend: " + l2 + ":" + paramChannelHandlerContext.channel().config().isAutoRead() + ":" + isHandlerActive(paramChannelHandlerContext));
/*     */         }
/*     */         
/*     */ 
/* 402 */         submitWrite(paramChannelHandlerContext, paramObject, l2, paramChannelPromise);
/* 403 */         return;
/*     */       }
/*     */     }
/*     */     
/* 407 */     submitWrite(paramChannelHandlerContext, paramObject, 0L, paramChannelPromise);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   protected abstract void submitWrite(ChannelHandlerContext paramChannelHandlerContext, Object paramObject, long paramLong, ChannelPromise paramChannelPromise);
/*     */   
/*     */ 
/*     */ 
/*     */   public TrafficCounter trafficCounter()
/*     */   {
/* 419 */     return this.trafficCounter;
/*     */   }
/*     */   
/*     */   public String toString()
/*     */   {
/* 424 */     return "TrafficShaping with Write Limit: " + this.writeLimit + " Read Limit: " + this.readLimit + " and Counter: " + (this.trafficCounter != null ? this.trafficCounter.toString() : "none");
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
/*     */   protected long calculateSize(Object paramObject)
/*     */   {
/* 438 */     if ((paramObject instanceof ByteBuf)) {
/* 439 */       return ((ByteBuf)paramObject).readableBytes();
/*     */     }
/* 441 */     if ((paramObject instanceof ByteBufHolder)) {
/* 442 */       return ((ByteBufHolder)paramObject).content().readableBytes();
/*     */     }
/* 444 */     return -1L;
/*     */   }
/*     */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\io\netty\handler\traffic\AbstractTrafficShapingHandler.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */
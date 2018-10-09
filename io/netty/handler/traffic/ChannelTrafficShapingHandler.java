/*     */ package io.netty.handler.traffic;
/*     */ 
/*     */ import io.netty.buffer.ByteBuf;
/*     */ import io.netty.channel.ChannelHandlerContext;
/*     */ import io.netty.channel.ChannelPromise;
/*     */ import io.netty.util.concurrent.EventExecutor;
/*     */ import java.util.LinkedList;
/*     */ import java.util.List;
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
/*     */ public class ChannelTrafficShapingHandler
/*     */   extends AbstractTrafficShapingHandler
/*     */ {
/*  54 */   private List<ToSend> messagesQueue = new LinkedList();
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public ChannelTrafficShapingHandler(long paramLong1, long paramLong2, long paramLong3, long paramLong4)
/*     */   {
/*  71 */     super(paramLong1, paramLong2, paramLong3, paramLong4);
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
/*     */   public ChannelTrafficShapingHandler(long paramLong1, long paramLong2, long paramLong3)
/*     */   {
/*  87 */     super(paramLong1, paramLong2, paramLong3);
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
/*     */   public ChannelTrafficShapingHandler(long paramLong1, long paramLong2)
/*     */   {
/* 100 */     super(paramLong1, paramLong2);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public ChannelTrafficShapingHandler(long paramLong)
/*     */   {
/* 111 */     super(paramLong);
/*     */   }
/*     */   
/*     */   public void handlerAdded(ChannelHandlerContext paramChannelHandlerContext) throws Exception
/*     */   {
/* 116 */     TrafficCounter localTrafficCounter = new TrafficCounter(this, paramChannelHandlerContext.executor(), "ChannelTC" + paramChannelHandlerContext.channel().hashCode(), this.checkInterval);
/*     */     
/* 118 */     setTrafficCounter(localTrafficCounter);
/* 119 */     localTrafficCounter.start();
/*     */   }
/*     */   
/*     */   public synchronized void handlerRemoved(ChannelHandlerContext paramChannelHandlerContext) throws Exception
/*     */   {
/* 124 */     if (this.trafficCounter != null) {
/* 125 */       this.trafficCounter.stop();
/*     */     }
/* 127 */     for (ToSend localToSend : this.messagesQueue) {
/* 128 */       if ((localToSend.toSend instanceof ByteBuf)) {
/* 129 */         ((ByteBuf)localToSend.toSend).release();
/*     */       }
/*     */     }
/* 132 */     this.messagesQueue.clear();
/*     */   }
/*     */   
/*     */   private static final class ToSend {
/*     */     final long date;
/*     */     final Object toSend;
/*     */     final ChannelPromise promise;
/*     */     
/*     */     private ToSend(long paramLong, Object paramObject, ChannelPromise paramChannelPromise) {
/* 141 */       this.date = (System.currentTimeMillis() + paramLong);
/* 142 */       this.toSend = paramObject;
/* 143 */       this.promise = paramChannelPromise;
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */   protected synchronized void submitWrite(final ChannelHandlerContext paramChannelHandlerContext, Object paramObject, long paramLong, ChannelPromise paramChannelPromise)
/*     */   {
/* 150 */     if ((paramLong == 0L) && (this.messagesQueue.isEmpty())) {
/* 151 */       paramChannelHandlerContext.write(paramObject, paramChannelPromise);
/* 152 */       return;
/*     */     }
/* 154 */     ToSend localToSend = new ToSend(paramLong, paramObject, paramChannelPromise, null);
/* 155 */     this.messagesQueue.add(localToSend);
/* 156 */     paramChannelHandlerContext.executor().schedule(new Runnable()
/*     */     {
/*     */ 
/* 159 */       public void run() { ChannelTrafficShapingHandler.this.sendAllValid(paramChannelHandlerContext); } }, paramLong, TimeUnit.MILLISECONDS);
/*     */   }
/*     */   
/*     */ 
/*     */   private synchronized void sendAllValid(ChannelHandlerContext paramChannelHandlerContext)
/*     */   {
/* 165 */     while (!this.messagesQueue.isEmpty()) {
/* 166 */       ToSend localToSend = (ToSend)this.messagesQueue.remove(0);
/* 167 */       if (localToSend.date <= System.currentTimeMillis()) {
/* 168 */         paramChannelHandlerContext.write(localToSend.toSend, localToSend.promise);
/*     */       } else {
/* 170 */         this.messagesQueue.add(0, localToSend);
/* 171 */         break;
/*     */       }
/*     */     }
/* 174 */     paramChannelHandlerContext.flush();
/*     */   }
/*     */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\io\netty\handler\traffic\ChannelTrafficShapingHandler.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */
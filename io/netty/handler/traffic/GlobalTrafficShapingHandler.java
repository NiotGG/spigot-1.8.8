/*     */ package io.netty.handler.traffic;
/*     */ 
/*     */ import io.netty.buffer.ByteBuf;
/*     */ import io.netty.channel.ChannelHandler.Sharable;
/*     */ import io.netty.channel.ChannelHandlerContext;
/*     */ import io.netty.channel.ChannelPromise;
/*     */ import io.netty.util.concurrent.EventExecutor;
/*     */ import java.util.HashMap;
/*     */ import java.util.LinkedList;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import java.util.concurrent.ScheduledExecutorService;
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
/*     */ @ChannelHandler.Sharable
/*     */ public class GlobalTrafficShapingHandler
/*     */   extends AbstractTrafficShapingHandler
/*     */ {
/*  66 */   private Map<Integer, List<ToSend>> messagesQueues = new HashMap();
/*     */   
/*     */ 
/*     */ 
/*     */   void createGlobalTrafficCounter(ScheduledExecutorService paramScheduledExecutorService)
/*     */   {
/*  72 */     if (paramScheduledExecutorService == null) {
/*  73 */       throw new NullPointerException("executor");
/*     */     }
/*  75 */     TrafficCounter localTrafficCounter = new TrafficCounter(this, paramScheduledExecutorService, "GlobalTC", this.checkInterval);
/*     */     
/*  77 */     setTrafficCounter(localTrafficCounter);
/*  78 */     localTrafficCounter.start();
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
/*     */   public GlobalTrafficShapingHandler(ScheduledExecutorService paramScheduledExecutorService, long paramLong1, long paramLong2, long paramLong3, long paramLong4)
/*     */   {
/*  98 */     super(paramLong1, paramLong2, paramLong3, paramLong4);
/*  99 */     createGlobalTrafficCounter(paramScheduledExecutorService);
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
/*     */   public GlobalTrafficShapingHandler(ScheduledExecutorService paramScheduledExecutorService, long paramLong1, long paramLong2, long paramLong3)
/*     */   {
/* 117 */     super(paramLong1, paramLong2, paramLong3);
/* 118 */     createGlobalTrafficCounter(paramScheduledExecutorService);
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
/*     */   public GlobalTrafficShapingHandler(ScheduledExecutorService paramScheduledExecutorService, long paramLong1, long paramLong2)
/*     */   {
/* 133 */     super(paramLong1, paramLong2);
/* 134 */     createGlobalTrafficCounter(paramScheduledExecutorService);
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
/*     */   public GlobalTrafficShapingHandler(ScheduledExecutorService paramScheduledExecutorService, long paramLong)
/*     */   {
/* 147 */     super(paramLong);
/* 148 */     createGlobalTrafficCounter(paramScheduledExecutorService);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public GlobalTrafficShapingHandler(EventExecutor paramEventExecutor)
/*     */   {
/* 158 */     createGlobalTrafficCounter(paramEventExecutor);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public final void release()
/*     */   {
/* 165 */     if (this.trafficCounter != null) {
/* 166 */       this.trafficCounter.stop();
/*     */     }
/*     */   }
/*     */   
/*     */   public void handlerAdded(ChannelHandlerContext paramChannelHandlerContext) throws Exception
/*     */   {
/* 172 */     Integer localInteger = Integer.valueOf(paramChannelHandlerContext.channel().hashCode());
/* 173 */     LinkedList localLinkedList = new LinkedList();
/* 174 */     this.messagesQueues.put(localInteger, localLinkedList);
/*     */   }
/*     */   
/*     */   public synchronized void handlerRemoved(ChannelHandlerContext paramChannelHandlerContext) throws Exception
/*     */   {
/* 179 */     Integer localInteger = Integer.valueOf(paramChannelHandlerContext.channel().hashCode());
/* 180 */     List localList = (List)this.messagesQueues.remove(localInteger);
/* 181 */     if (localList != null) {
/* 182 */       for (ToSend localToSend : localList) {
/* 183 */         if ((localToSend.toSend instanceof ByteBuf)) {
/* 184 */           ((ByteBuf)localToSend.toSend).release();
/*     */         }
/*     */       }
/* 187 */       localList.clear();
/*     */     }
/*     */   }
/*     */   
/*     */   private static final class ToSend {
/*     */     final long date;
/*     */     final Object toSend;
/*     */     final ChannelPromise promise;
/*     */     
/*     */     private ToSend(long paramLong, Object paramObject, ChannelPromise paramChannelPromise) {
/* 197 */       this.date = (System.currentTimeMillis() + paramLong);
/* 198 */       this.toSend = paramObject;
/* 199 */       this.promise = paramChannelPromise;
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */   protected synchronized void submitWrite(final ChannelHandlerContext paramChannelHandlerContext, Object paramObject, long paramLong, ChannelPromise paramChannelPromise)
/*     */   {
/* 206 */     Integer localInteger = Integer.valueOf(paramChannelHandlerContext.channel().hashCode());
/* 207 */     Object localObject1 = (List)this.messagesQueues.get(localInteger);
/* 208 */     if ((paramLong == 0L) && ((localObject1 == null) || (((List)localObject1).isEmpty()))) {
/* 209 */       paramChannelHandlerContext.write(paramObject, paramChannelPromise);
/* 210 */       return;
/*     */     }
/* 212 */     ToSend localToSend = new ToSend(paramLong, paramObject, paramChannelPromise, null);
/* 213 */     if (localObject1 == null) {
/* 214 */       localObject1 = new LinkedList();
/* 215 */       this.messagesQueues.put(localInteger, localObject1);
/*     */     }
/* 217 */     ((List)localObject1).add(localToSend);
/* 218 */     final Object localObject2 = localObject1;
/* 219 */     paramChannelHandlerContext.executor().schedule(new Runnable()
/*     */     {
/*     */ 
/* 222 */       public void run() { GlobalTrafficShapingHandler.this.sendAllValid(paramChannelHandlerContext, localObject2); } }, paramLong, TimeUnit.MILLISECONDS);
/*     */   }
/*     */   
/*     */ 
/*     */   private synchronized void sendAllValid(ChannelHandlerContext paramChannelHandlerContext, List<ToSend> paramList)
/*     */   {
/* 228 */     while (!paramList.isEmpty()) {
/* 229 */       ToSend localToSend = (ToSend)paramList.remove(0);
/* 230 */       if (localToSend.date <= System.currentTimeMillis()) {
/* 231 */         paramChannelHandlerContext.write(localToSend.toSend, localToSend.promise);
/*     */       } else {
/* 233 */         paramList.add(0, localToSend);
/* 234 */         break;
/*     */       }
/*     */     }
/* 237 */     paramChannelHandlerContext.flush();
/*     */   }
/*     */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\io\netty\handler\traffic\GlobalTrafficShapingHandler.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */
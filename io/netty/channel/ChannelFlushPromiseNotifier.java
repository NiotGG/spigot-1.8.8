/*     */ package io.netty.channel;
/*     */ 
/*     */ import java.util.ArrayDeque;
/*     */ import java.util.Queue;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public final class ChannelFlushPromiseNotifier
/*     */ {
/*     */   private long writeCounter;
/*  28 */   private final Queue<FlushCheckpoint> flushCheckpoints = new ArrayDeque();
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   private final boolean tryNotify;
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public ChannelFlushPromiseNotifier(boolean paramBoolean)
/*     */   {
/*  40 */     this.tryNotify = paramBoolean;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public ChannelFlushPromiseNotifier()
/*     */   {
/*  48 */     this(false);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   @Deprecated
/*     */   public ChannelFlushPromiseNotifier add(ChannelPromise paramChannelPromise, int paramInt)
/*     */   {
/*  56 */     return add(paramChannelPromise, paramInt);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public ChannelFlushPromiseNotifier add(ChannelPromise paramChannelPromise, long paramLong)
/*     */   {
/*  64 */     if (paramChannelPromise == null) {
/*  65 */       throw new NullPointerException("promise");
/*     */     }
/*  67 */     if (paramLong < 0L) {
/*  68 */       throw new IllegalArgumentException("pendingDataSize must be >= 0 but was " + paramLong);
/*     */     }
/*  70 */     long l = this.writeCounter + paramLong;
/*  71 */     if ((paramChannelPromise instanceof FlushCheckpoint)) {
/*  72 */       FlushCheckpoint localFlushCheckpoint = (FlushCheckpoint)paramChannelPromise;
/*  73 */       localFlushCheckpoint.flushCheckpoint(l);
/*  74 */       this.flushCheckpoints.add(localFlushCheckpoint);
/*     */     } else {
/*  76 */       this.flushCheckpoints.add(new DefaultFlushCheckpoint(l, paramChannelPromise));
/*     */     }
/*  78 */     return this;
/*     */   }
/*     */   
/*     */ 
/*     */   public ChannelFlushPromiseNotifier increaseWriteCounter(long paramLong)
/*     */   {
/*  84 */     if (paramLong < 0L) {
/*  85 */       throw new IllegalArgumentException("delta must be >= 0 but was " + paramLong);
/*     */     }
/*  87 */     this.writeCounter += paramLong;
/*  88 */     return this;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public long writeCounter()
/*     */   {
/*  95 */     return this.writeCounter;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public ChannelFlushPromiseNotifier notifyPromises()
/*     */   {
/* 106 */     notifyPromises0(null);
/* 107 */     return this;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   @Deprecated
/*     */   public ChannelFlushPromiseNotifier notifyFlushFutures()
/*     */   {
/* 115 */     return notifyPromises();
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
/*     */   public ChannelFlushPromiseNotifier notifyPromises(Throwable paramThrowable)
/*     */   {
/* 130 */     notifyPromises();
/*     */     for (;;) {
/* 132 */       FlushCheckpoint localFlushCheckpoint = (FlushCheckpoint)this.flushCheckpoints.poll();
/* 133 */       if (localFlushCheckpoint == null) {
/*     */         break;
/*     */       }
/* 136 */       if (this.tryNotify) {
/* 137 */         localFlushCheckpoint.promise().tryFailure(paramThrowable);
/*     */       } else {
/* 139 */         localFlushCheckpoint.promise().setFailure(paramThrowable);
/*     */       }
/*     */     }
/* 142 */     return this;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   @Deprecated
/*     */   public ChannelFlushPromiseNotifier notifyFlushFutures(Throwable paramThrowable)
/*     */   {
/* 150 */     return notifyPromises(paramThrowable);
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
/*     */   public ChannelFlushPromiseNotifier notifyPromises(Throwable paramThrowable1, Throwable paramThrowable2)
/*     */   {
/* 170 */     notifyPromises0(paramThrowable1);
/*     */     for (;;) {
/* 172 */       FlushCheckpoint localFlushCheckpoint = (FlushCheckpoint)this.flushCheckpoints.poll();
/* 173 */       if (localFlushCheckpoint == null) {
/*     */         break;
/*     */       }
/* 176 */       if (this.tryNotify) {
/* 177 */         localFlushCheckpoint.promise().tryFailure(paramThrowable2);
/*     */       } else {
/* 179 */         localFlushCheckpoint.promise().setFailure(paramThrowable2);
/*     */       }
/*     */     }
/* 182 */     return this;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   @Deprecated
/*     */   public ChannelFlushPromiseNotifier notifyFlushFutures(Throwable paramThrowable1, Throwable paramThrowable2)
/*     */   {
/* 190 */     return notifyPromises(paramThrowable1, paramThrowable2);
/*     */   }
/*     */   
/*     */   private void notifyPromises0(Throwable paramThrowable) {
/* 194 */     if (this.flushCheckpoints.isEmpty()) {
/* 195 */       this.writeCounter = 0L;
/* 196 */       return;
/*     */     }
/*     */     
/* 199 */     long l1 = this.writeCounter;
/*     */     for (;;) {
/* 201 */       FlushCheckpoint localFlushCheckpoint1 = (FlushCheckpoint)this.flushCheckpoints.peek();
/* 202 */       if (localFlushCheckpoint1 == null)
/*     */       {
/* 204 */         this.writeCounter = 0L;
/* 205 */         break;
/*     */       }
/*     */       
/* 208 */       if (localFlushCheckpoint1.flushCheckpoint() > l1) {
/* 209 */         if ((l1 <= 0L) || (this.flushCheckpoints.size() != 1)) break;
/* 210 */         this.writeCounter = 0L;
/* 211 */         localFlushCheckpoint1.flushCheckpoint(localFlushCheckpoint1.flushCheckpoint() - l1); break;
/*     */       }
/*     */       
/*     */ 
/*     */ 
/* 216 */       this.flushCheckpoints.remove();
/* 217 */       ChannelPromise localChannelPromise = localFlushCheckpoint1.promise();
/* 218 */       if (paramThrowable == null) {
/* 219 */         if (this.tryNotify) {
/* 220 */           localChannelPromise.trySuccess();
/*     */         } else {
/* 222 */           localChannelPromise.setSuccess();
/*     */         }
/*     */       }
/* 225 */       else if (this.tryNotify) {
/* 226 */         localChannelPromise.tryFailure(paramThrowable);
/*     */       } else {
/* 228 */         localChannelPromise.setFailure(paramThrowable);
/*     */       }
/*     */     }
/*     */     
/*     */ 
/*     */ 
/* 234 */     long l2 = this.writeCounter;
/* 235 */     if (l2 >= 549755813888L)
/*     */     {
/*     */ 
/* 238 */       this.writeCounter = 0L;
/* 239 */       for (FlushCheckpoint localFlushCheckpoint2 : this.flushCheckpoints) {
/* 240 */         localFlushCheckpoint2.flushCheckpoint(localFlushCheckpoint2.flushCheckpoint() - l2);
/*     */       }
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */   private static class DefaultFlushCheckpoint
/*     */     implements ChannelFlushPromiseNotifier.FlushCheckpoint
/*     */   {
/*     */     private long checkpoint;
/*     */     
/*     */     private final ChannelPromise future;
/*     */     
/*     */ 
/*     */     DefaultFlushCheckpoint(long paramLong, ChannelPromise paramChannelPromise)
/*     */     {
/* 256 */       this.checkpoint = paramLong;
/* 257 */       this.future = paramChannelPromise;
/*     */     }
/*     */     
/*     */     public long flushCheckpoint()
/*     */     {
/* 262 */       return this.checkpoint;
/*     */     }
/*     */     
/*     */     public void flushCheckpoint(long paramLong)
/*     */     {
/* 267 */       this.checkpoint = paramLong;
/*     */     }
/*     */     
/*     */     public ChannelPromise promise()
/*     */     {
/* 272 */       return this.future;
/*     */     }
/*     */   }
/*     */   
/*     */   static abstract interface FlushCheckpoint
/*     */   {
/*     */     public abstract long flushCheckpoint();
/*     */     
/*     */     public abstract void flushCheckpoint(long paramLong);
/*     */     
/*     */     public abstract ChannelPromise promise();
/*     */   }
/*     */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\io\netty\channel\ChannelFlushPromiseNotifier.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */
/*     */ package io.netty.handler.codec.spdy;
/*     */ 
/*     */ import io.netty.channel.ChannelPromise;
/*     */ import io.netty.util.internal.PlatformDependent;
/*     */ import java.io.Serializable;
/*     */ import java.util.Comparator;
/*     */ import java.util.Iterator;
/*     */ import java.util.Map;
/*     */ import java.util.Map.Entry;
/*     */ import java.util.Queue;
/*     */ import java.util.Set;
/*     */ import java.util.TreeMap;
/*     */ import java.util.concurrent.ConcurrentLinkedQueue;
/*     */ import java.util.concurrent.atomic.AtomicInteger;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ final class SpdySession
/*     */ {
/*  33 */   private final AtomicInteger activeLocalStreams = new AtomicInteger();
/*  34 */   private final AtomicInteger activeRemoteStreams = new AtomicInteger();
/*  35 */   private final Map<Integer, StreamState> activeStreams = PlatformDependent.newConcurrentHashMap();
/*  36 */   private final StreamComparator streamComparator = new StreamComparator();
/*     */   private final AtomicInteger sendWindowSize;
/*     */   private final AtomicInteger receiveWindowSize;
/*     */   
/*     */   SpdySession(int paramInt1, int paramInt2) {
/*  41 */     this.sendWindowSize = new AtomicInteger(paramInt1);
/*  42 */     this.receiveWindowSize = new AtomicInteger(paramInt2);
/*     */   }
/*     */   
/*     */   int numActiveStreams(boolean paramBoolean) {
/*  46 */     if (paramBoolean) {
/*  47 */       return this.activeRemoteStreams.get();
/*     */     }
/*  49 */     return this.activeLocalStreams.get();
/*     */   }
/*     */   
/*     */   boolean noActiveStreams()
/*     */   {
/*  54 */     return this.activeStreams.isEmpty();
/*     */   }
/*     */   
/*     */   boolean isActiveStream(int paramInt) {
/*  58 */     return this.activeStreams.containsKey(Integer.valueOf(paramInt));
/*     */   }
/*     */   
/*     */   Map<Integer, StreamState> activeStreams()
/*     */   {
/*  63 */     TreeMap localTreeMap = new TreeMap(this.streamComparator);
/*  64 */     localTreeMap.putAll(this.activeStreams);
/*  65 */     return localTreeMap;
/*     */   }
/*     */   
/*     */ 
/*     */   void acceptStream(int paramInt1, byte paramByte, boolean paramBoolean1, boolean paramBoolean2, int paramInt2, int paramInt3, boolean paramBoolean3)
/*     */   {
/*  71 */     if ((!paramBoolean1) || (!paramBoolean2)) {
/*  72 */       StreamState localStreamState = (StreamState)this.activeStreams.put(Integer.valueOf(paramInt1), new StreamState(paramByte, paramBoolean1, paramBoolean2, paramInt2, paramInt3));
/*     */       
/*  74 */       if (localStreamState == null) {
/*  75 */         if (paramBoolean3) {
/*  76 */           this.activeRemoteStreams.incrementAndGet();
/*     */         } else {
/*  78 */           this.activeLocalStreams.incrementAndGet();
/*     */         }
/*     */       }
/*     */     }
/*     */   }
/*     */   
/*     */   private StreamState removeActiveStream(int paramInt, boolean paramBoolean) {
/*  85 */     StreamState localStreamState = (StreamState)this.activeStreams.remove(Integer.valueOf(paramInt));
/*  86 */     if (localStreamState != null) {
/*  87 */       if (paramBoolean) {
/*  88 */         this.activeRemoteStreams.decrementAndGet();
/*     */       } else {
/*  90 */         this.activeLocalStreams.decrementAndGet();
/*     */       }
/*     */     }
/*  93 */     return localStreamState;
/*     */   }
/*     */   
/*     */   void removeStream(int paramInt, Throwable paramThrowable, boolean paramBoolean) {
/*  97 */     StreamState localStreamState = removeActiveStream(paramInt, paramBoolean);
/*  98 */     if (localStreamState != null) {
/*  99 */       localStreamState.clearPendingWrites(paramThrowable);
/*     */     }
/*     */   }
/*     */   
/*     */   boolean isRemoteSideClosed(int paramInt) {
/* 104 */     StreamState localStreamState = (StreamState)this.activeStreams.get(Integer.valueOf(paramInt));
/* 105 */     return (localStreamState == null) || (localStreamState.isRemoteSideClosed());
/*     */   }
/*     */   
/*     */   void closeRemoteSide(int paramInt, boolean paramBoolean) {
/* 109 */     StreamState localStreamState = (StreamState)this.activeStreams.get(Integer.valueOf(paramInt));
/* 110 */     if (localStreamState != null) {
/* 111 */       localStreamState.closeRemoteSide();
/* 112 */       if (localStreamState.isLocalSideClosed()) {
/* 113 */         removeActiveStream(paramInt, paramBoolean);
/*     */       }
/*     */     }
/*     */   }
/*     */   
/*     */   boolean isLocalSideClosed(int paramInt) {
/* 119 */     StreamState localStreamState = (StreamState)this.activeStreams.get(Integer.valueOf(paramInt));
/* 120 */     return (localStreamState == null) || (localStreamState.isLocalSideClosed());
/*     */   }
/*     */   
/*     */   void closeLocalSide(int paramInt, boolean paramBoolean) {
/* 124 */     StreamState localStreamState = (StreamState)this.activeStreams.get(Integer.valueOf(paramInt));
/* 125 */     if (localStreamState != null) {
/* 126 */       localStreamState.closeLocalSide();
/* 127 */       if (localStreamState.isRemoteSideClosed()) {
/* 128 */         removeActiveStream(paramInt, paramBoolean);
/*     */       }
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   boolean hasReceivedReply(int paramInt)
/*     */   {
/* 138 */     StreamState localStreamState = (StreamState)this.activeStreams.get(Integer.valueOf(paramInt));
/* 139 */     return (localStreamState != null) && (localStreamState.hasReceivedReply());
/*     */   }
/*     */   
/*     */   void receivedReply(int paramInt) {
/* 143 */     StreamState localStreamState = (StreamState)this.activeStreams.get(Integer.valueOf(paramInt));
/* 144 */     if (localStreamState != null) {
/* 145 */       localStreamState.receivedReply();
/*     */     }
/*     */   }
/*     */   
/*     */   int getSendWindowSize(int paramInt) {
/* 150 */     if (paramInt == 0) {
/* 151 */       return this.sendWindowSize.get();
/*     */     }
/*     */     
/* 154 */     StreamState localStreamState = (StreamState)this.activeStreams.get(Integer.valueOf(paramInt));
/* 155 */     return localStreamState != null ? localStreamState.getSendWindowSize() : -1;
/*     */   }
/*     */   
/*     */   int updateSendWindowSize(int paramInt1, int paramInt2) {
/* 159 */     if (paramInt1 == 0) {
/* 160 */       return this.sendWindowSize.addAndGet(paramInt2);
/*     */     }
/*     */     
/* 163 */     StreamState localStreamState = (StreamState)this.activeStreams.get(Integer.valueOf(paramInt1));
/* 164 */     return localStreamState != null ? localStreamState.updateSendWindowSize(paramInt2) : -1;
/*     */   }
/*     */   
/*     */   int updateReceiveWindowSize(int paramInt1, int paramInt2) {
/* 168 */     if (paramInt1 == 0) {
/* 169 */       return this.receiveWindowSize.addAndGet(paramInt2);
/*     */     }
/*     */     
/* 172 */     StreamState localStreamState = (StreamState)this.activeStreams.get(Integer.valueOf(paramInt1));
/* 173 */     if (localStreamState == null) {
/* 174 */       return -1;
/*     */     }
/* 176 */     if (paramInt2 > 0) {
/* 177 */       localStreamState.setReceiveWindowSizeLowerBound(0);
/*     */     }
/* 179 */     return localStreamState.updateReceiveWindowSize(paramInt2);
/*     */   }
/*     */   
/*     */   int getReceiveWindowSizeLowerBound(int paramInt) {
/* 183 */     if (paramInt == 0) {
/* 184 */       return 0;
/*     */     }
/*     */     
/* 187 */     StreamState localStreamState = (StreamState)this.activeStreams.get(Integer.valueOf(paramInt));
/* 188 */     return localStreamState != null ? localStreamState.getReceiveWindowSizeLowerBound() : 0;
/*     */   }
/*     */   
/*     */   void updateAllSendWindowSizes(int paramInt) {
/* 192 */     for (StreamState localStreamState : this.activeStreams.values()) {
/* 193 */       localStreamState.updateSendWindowSize(paramInt);
/*     */     }
/*     */   }
/*     */   
/*     */   void updateAllReceiveWindowSizes(int paramInt) {
/* 198 */     for (StreamState localStreamState : this.activeStreams.values()) {
/* 199 */       localStreamState.updateReceiveWindowSize(paramInt);
/* 200 */       if (paramInt < 0) {
/* 201 */         localStreamState.setReceiveWindowSizeLowerBound(paramInt);
/*     */       }
/*     */     }
/*     */   }
/*     */   
/*     */   boolean putPendingWrite(int paramInt, PendingWrite paramPendingWrite) {
/* 207 */     StreamState localStreamState = (StreamState)this.activeStreams.get(Integer.valueOf(paramInt));
/* 208 */     return (localStreamState != null) && (localStreamState.putPendingWrite(paramPendingWrite));
/*     */   }
/*     */   
/*     */   PendingWrite getPendingWrite(int paramInt) {
/* 212 */     if (paramInt == 0) {
/* 213 */       for (localObject = activeStreams().entrySet().iterator(); ((Iterator)localObject).hasNext();) { Map.Entry localEntry = (Map.Entry)((Iterator)localObject).next();
/* 214 */         StreamState localStreamState = (StreamState)localEntry.getValue();
/* 215 */         if (localStreamState.getSendWindowSize() > 0) {
/* 216 */           PendingWrite localPendingWrite = localStreamState.getPendingWrite();
/* 217 */           if (localPendingWrite != null) {
/* 218 */             return localPendingWrite;
/*     */           }
/*     */         }
/*     */       }
/* 222 */       return null;
/*     */     }
/*     */     
/* 225 */     Object localObject = (StreamState)this.activeStreams.get(Integer.valueOf(paramInt));
/* 226 */     return localObject != null ? ((StreamState)localObject).getPendingWrite() : null;
/*     */   }
/*     */   
/*     */   PendingWrite removePendingWrite(int paramInt) {
/* 230 */     StreamState localStreamState = (StreamState)this.activeStreams.get(Integer.valueOf(paramInt));
/* 231 */     return localStreamState != null ? localStreamState.removePendingWrite() : null;
/*     */   }
/*     */   
/*     */   private static final class StreamState
/*     */   {
/*     */     private final byte priority;
/*     */     private boolean remoteSideClosed;
/*     */     private boolean localSideClosed;
/*     */     private boolean receivedReply;
/*     */     private final AtomicInteger sendWindowSize;
/*     */     private final AtomicInteger receiveWindowSize;
/*     */     private int receiveWindowSizeLowerBound;
/* 243 */     private final Queue<SpdySession.PendingWrite> pendingWriteQueue = new ConcurrentLinkedQueue();
/*     */     
/*     */ 
/*     */     StreamState(byte paramByte, boolean paramBoolean1, boolean paramBoolean2, int paramInt1, int paramInt2)
/*     */     {
/* 248 */       this.priority = paramByte;
/* 249 */       this.remoteSideClosed = paramBoolean1;
/* 250 */       this.localSideClosed = paramBoolean2;
/* 251 */       this.sendWindowSize = new AtomicInteger(paramInt1);
/* 252 */       this.receiveWindowSize = new AtomicInteger(paramInt2);
/*     */     }
/*     */     
/*     */     byte getPriority() {
/* 256 */       return this.priority;
/*     */     }
/*     */     
/*     */     boolean isRemoteSideClosed() {
/* 260 */       return this.remoteSideClosed;
/*     */     }
/*     */     
/*     */     void closeRemoteSide() {
/* 264 */       this.remoteSideClosed = true;
/*     */     }
/*     */     
/*     */     boolean isLocalSideClosed() {
/* 268 */       return this.localSideClosed;
/*     */     }
/*     */     
/*     */     void closeLocalSide() {
/* 272 */       this.localSideClosed = true;
/*     */     }
/*     */     
/*     */     boolean hasReceivedReply() {
/* 276 */       return this.receivedReply;
/*     */     }
/*     */     
/*     */     void receivedReply() {
/* 280 */       this.receivedReply = true;
/*     */     }
/*     */     
/*     */     int getSendWindowSize() {
/* 284 */       return this.sendWindowSize.get();
/*     */     }
/*     */     
/*     */     int updateSendWindowSize(int paramInt) {
/* 288 */       return this.sendWindowSize.addAndGet(paramInt);
/*     */     }
/*     */     
/*     */     int updateReceiveWindowSize(int paramInt) {
/* 292 */       return this.receiveWindowSize.addAndGet(paramInt);
/*     */     }
/*     */     
/*     */     int getReceiveWindowSizeLowerBound() {
/* 296 */       return this.receiveWindowSizeLowerBound;
/*     */     }
/*     */     
/*     */     void setReceiveWindowSizeLowerBound(int paramInt) {
/* 300 */       this.receiveWindowSizeLowerBound = paramInt;
/*     */     }
/*     */     
/*     */     boolean putPendingWrite(SpdySession.PendingWrite paramPendingWrite) {
/* 304 */       return this.pendingWriteQueue.offer(paramPendingWrite);
/*     */     }
/*     */     
/*     */     SpdySession.PendingWrite getPendingWrite() {
/* 308 */       return (SpdySession.PendingWrite)this.pendingWriteQueue.peek();
/*     */     }
/*     */     
/*     */     SpdySession.PendingWrite removePendingWrite() {
/* 312 */       return (SpdySession.PendingWrite)this.pendingWriteQueue.poll();
/*     */     }
/*     */     
/*     */     void clearPendingWrites(Throwable paramThrowable) {
/*     */       for (;;) {
/* 317 */         SpdySession.PendingWrite localPendingWrite = (SpdySession.PendingWrite)this.pendingWriteQueue.poll();
/* 318 */         if (localPendingWrite == null) {
/*     */           break;
/*     */         }
/* 321 */         localPendingWrite.fail(paramThrowable);
/*     */       }
/*     */     }
/*     */   }
/*     */   
/*     */   private final class StreamComparator implements Comparator<Integer>, Serializable
/*     */   {
/*     */     private static final long serialVersionUID = 1161471649740544848L;
/*     */     
/*     */     StreamComparator() {}
/*     */     
/*     */     public int compare(Integer paramInteger1, Integer paramInteger2)
/*     */     {
/* 334 */       SpdySession.StreamState localStreamState1 = (SpdySession.StreamState)SpdySession.this.activeStreams.get(paramInteger1);
/* 335 */       SpdySession.StreamState localStreamState2 = (SpdySession.StreamState)SpdySession.this.activeStreams.get(paramInteger2);
/*     */       
/* 337 */       int i = localStreamState1.getPriority() - localStreamState2.getPriority();
/* 338 */       if (i != 0) {
/* 339 */         return i;
/*     */       }
/*     */       
/* 342 */       return paramInteger1.intValue() - paramInteger2.intValue();
/*     */     }
/*     */   }
/*     */   
/*     */   public static final class PendingWrite {
/*     */     final SpdyDataFrame spdyDataFrame;
/*     */     final ChannelPromise promise;
/*     */     
/*     */     PendingWrite(SpdyDataFrame paramSpdyDataFrame, ChannelPromise paramChannelPromise) {
/* 351 */       this.spdyDataFrame = paramSpdyDataFrame;
/* 352 */       this.promise = paramChannelPromise;
/*     */     }
/*     */     
/*     */     void fail(Throwable paramThrowable) {
/* 356 */       this.spdyDataFrame.release();
/* 357 */       this.promise.setFailure(paramThrowable);
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\io\netty\handler\codec\spdy\SpdySession.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */
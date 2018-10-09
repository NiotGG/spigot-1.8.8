/*     */ package io.netty.channel.group;
/*     */ 
/*     */ import io.netty.channel.Channel;
/*     */ import io.netty.channel.ChannelFuture;
/*     */ import io.netty.channel.ChannelFutureListener;
/*     */ import io.netty.util.concurrent.BlockingOperationException;
/*     */ import io.netty.util.concurrent.DefaultPromise;
/*     */ import io.netty.util.concurrent.EventExecutor;
/*     */ import io.netty.util.concurrent.Future;
/*     */ import io.netty.util.concurrent.GenericFutureListener;
/*     */ import io.netty.util.concurrent.ImmediateEventExecutor;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Collection;
/*     */ import java.util.Collections;
/*     */ import java.util.Iterator;
/*     */ import java.util.LinkedHashMap;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import java.util.Map.Entry;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ final class DefaultChannelGroupFuture
/*     */   extends DefaultPromise<Void>
/*     */   implements ChannelGroupFuture
/*     */ {
/*     */   private final ChannelGroup group;
/*     */   private final Map<Channel, ChannelFuture> futures;
/*     */   private int successCount;
/*     */   private int failureCount;
/*  47 */   private final ChannelFutureListener childListener = new ChannelFutureListener()
/*     */   {
/*     */     public void operationComplete(ChannelFuture paramAnonymousChannelFuture) throws Exception {
/*  50 */       boolean bool = paramAnonymousChannelFuture.isSuccess();
/*     */       int i;
/*  52 */       synchronized (DefaultChannelGroupFuture.this) {
/*  53 */         if (bool) {
/*  54 */           DefaultChannelGroupFuture.access$008(DefaultChannelGroupFuture.this);
/*     */         } else {
/*  56 */           DefaultChannelGroupFuture.access$108(DefaultChannelGroupFuture.this);
/*     */         }
/*     */         
/*  59 */         i = DefaultChannelGroupFuture.this.successCount + DefaultChannelGroupFuture.this.failureCount == DefaultChannelGroupFuture.this.futures.size() ? 1 : 0;
/*  60 */         if ((!$assertionsDisabled) && (DefaultChannelGroupFuture.this.successCount + DefaultChannelGroupFuture.this.failureCount > DefaultChannelGroupFuture.this.futures.size())) { throw new AssertionError();
/*     */         }
/*     */       }
/*  63 */       if (i != 0) {
/*  64 */         if (DefaultChannelGroupFuture.this.failureCount > 0) {
/*  65 */           ??? = new ArrayList(DefaultChannelGroupFuture.this.failureCount);
/*     */           
/*  67 */           for (ChannelFuture localChannelFuture : DefaultChannelGroupFuture.this.futures.values()) {
/*  68 */             if (!localChannelFuture.isSuccess()) {
/*  69 */               ((List)???).add(new DefaultChannelGroupFuture.DefaultEntry(localChannelFuture.channel(), localChannelFuture.cause()));
/*     */             }
/*     */           }
/*  72 */           DefaultChannelGroupFuture.this.setFailure0(new ChannelGroupException((Collection)???));
/*     */         } else {
/*  74 */           DefaultChannelGroupFuture.this.setSuccess0();
/*     */         }
/*     */       }
/*     */     }
/*     */   };
/*     */   
/*     */ 
/*     */ 
/*     */   DefaultChannelGroupFuture(ChannelGroup paramChannelGroup, Collection<ChannelFuture> paramCollection, EventExecutor paramEventExecutor)
/*     */   {
/*  84 */     super(paramEventExecutor);
/*  85 */     if (paramChannelGroup == null) {
/*  86 */       throw new NullPointerException("group");
/*     */     }
/*  88 */     if (paramCollection == null) {
/*  89 */       throw new NullPointerException("futures");
/*     */     }
/*     */     
/*  92 */     this.group = paramChannelGroup;
/*     */     
/*  94 */     LinkedHashMap localLinkedHashMap = new LinkedHashMap();
/*  95 */     for (Iterator localIterator = paramCollection.iterator(); localIterator.hasNext();) { localChannelFuture = (ChannelFuture)localIterator.next();
/*  96 */       localLinkedHashMap.put(localChannelFuture.channel(), localChannelFuture);
/*     */     }
/*     */     ChannelFuture localChannelFuture;
/*  99 */     this.futures = Collections.unmodifiableMap(localLinkedHashMap);
/*     */     
/* 101 */     for (localIterator = this.futures.values().iterator(); localIterator.hasNext();) { localChannelFuture = (ChannelFuture)localIterator.next();
/* 102 */       localChannelFuture.addListener(this.childListener);
/*     */     }
/*     */     
/*     */ 
/* 106 */     if (this.futures.isEmpty()) {
/* 107 */       setSuccess0();
/*     */     }
/*     */   }
/*     */   
/*     */   DefaultChannelGroupFuture(ChannelGroup paramChannelGroup, Map<Channel, ChannelFuture> paramMap, EventExecutor paramEventExecutor) {
/* 112 */     super(paramEventExecutor);
/* 113 */     this.group = paramChannelGroup;
/* 114 */     this.futures = Collections.unmodifiableMap(paramMap);
/* 115 */     for (ChannelFuture localChannelFuture : this.futures.values()) {
/* 116 */       localChannelFuture.addListener(this.childListener);
/*     */     }
/*     */     
/*     */ 
/* 120 */     if (this.futures.isEmpty()) {
/* 121 */       setSuccess0();
/*     */     }
/*     */   }
/*     */   
/*     */   public ChannelGroup group()
/*     */   {
/* 127 */     return this.group;
/*     */   }
/*     */   
/*     */   public ChannelFuture find(Channel paramChannel)
/*     */   {
/* 132 */     return (ChannelFuture)this.futures.get(paramChannel);
/*     */   }
/*     */   
/*     */   public Iterator<ChannelFuture> iterator()
/*     */   {
/* 137 */     return this.futures.values().iterator();
/*     */   }
/*     */   
/*     */   public synchronized boolean isPartialSuccess()
/*     */   {
/* 142 */     return (this.successCount != 0) && (this.successCount != this.futures.size());
/*     */   }
/*     */   
/*     */   public synchronized boolean isPartialFailure()
/*     */   {
/* 147 */     return (this.failureCount != 0) && (this.failureCount != this.futures.size());
/*     */   }
/*     */   
/*     */   public DefaultChannelGroupFuture addListener(GenericFutureListener<? extends Future<? super Void>> paramGenericFutureListener)
/*     */   {
/* 152 */     super.addListener(paramGenericFutureListener);
/* 153 */     return this;
/*     */   }
/*     */   
/*     */   public DefaultChannelGroupFuture addListeners(GenericFutureListener<? extends Future<? super Void>>... paramVarArgs)
/*     */   {
/* 158 */     super.addListeners(paramVarArgs);
/* 159 */     return this;
/*     */   }
/*     */   
/*     */   public DefaultChannelGroupFuture removeListener(GenericFutureListener<? extends Future<? super Void>> paramGenericFutureListener)
/*     */   {
/* 164 */     super.removeListener(paramGenericFutureListener);
/* 165 */     return this;
/*     */   }
/*     */   
/*     */ 
/*     */   public DefaultChannelGroupFuture removeListeners(GenericFutureListener<? extends Future<? super Void>>... paramVarArgs)
/*     */   {
/* 171 */     super.removeListeners(paramVarArgs);
/* 172 */     return this;
/*     */   }
/*     */   
/*     */   public DefaultChannelGroupFuture await() throws InterruptedException
/*     */   {
/* 177 */     super.await();
/* 178 */     return this;
/*     */   }
/*     */   
/*     */   public DefaultChannelGroupFuture awaitUninterruptibly()
/*     */   {
/* 183 */     super.awaitUninterruptibly();
/* 184 */     return this;
/*     */   }
/*     */   
/*     */   public DefaultChannelGroupFuture syncUninterruptibly()
/*     */   {
/* 189 */     super.syncUninterruptibly();
/* 190 */     return this;
/*     */   }
/*     */   
/*     */   public DefaultChannelGroupFuture sync() throws InterruptedException
/*     */   {
/* 195 */     super.sync();
/* 196 */     return this;
/*     */   }
/*     */   
/*     */   public ChannelGroupException cause()
/*     */   {
/* 201 */     return (ChannelGroupException)super.cause();
/*     */   }
/*     */   
/*     */   private void setSuccess0() {
/* 205 */     super.setSuccess(null);
/*     */   }
/*     */   
/*     */   private void setFailure0(ChannelGroupException paramChannelGroupException) {
/* 209 */     super.setFailure(paramChannelGroupException);
/*     */   }
/*     */   
/*     */   public DefaultChannelGroupFuture setSuccess(Void paramVoid)
/*     */   {
/* 214 */     throw new IllegalStateException();
/*     */   }
/*     */   
/*     */   public boolean trySuccess(Void paramVoid)
/*     */   {
/* 219 */     throw new IllegalStateException();
/*     */   }
/*     */   
/*     */   public DefaultChannelGroupFuture setFailure(Throwable paramThrowable)
/*     */   {
/* 224 */     throw new IllegalStateException();
/*     */   }
/*     */   
/*     */   public boolean tryFailure(Throwable paramThrowable)
/*     */   {
/* 229 */     throw new IllegalStateException();
/*     */   }
/*     */   
/*     */   protected void checkDeadLock()
/*     */   {
/* 234 */     EventExecutor localEventExecutor = executor();
/* 235 */     if ((localEventExecutor != null) && (localEventExecutor != ImmediateEventExecutor.INSTANCE) && (localEventExecutor.inEventLoop())) {
/* 236 */       throw new BlockingOperationException();
/*     */     }
/*     */   }
/*     */   
/*     */   private static final class DefaultEntry<K, V> implements Map.Entry<K, V> {
/*     */     private final K key;
/*     */     private final V value;
/*     */     
/*     */     DefaultEntry(K paramK, V paramV) {
/* 245 */       this.key = paramK;
/* 246 */       this.value = paramV;
/*     */     }
/*     */     
/*     */     public K getKey()
/*     */     {
/* 251 */       return (K)this.key;
/*     */     }
/*     */     
/*     */     public V getValue()
/*     */     {
/* 256 */       return (V)this.value;
/*     */     }
/*     */     
/*     */     public V setValue(V paramV)
/*     */     {
/* 261 */       throw new UnsupportedOperationException("read-only");
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\io\netty\channel\group\DefaultChannelGroupFuture.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */
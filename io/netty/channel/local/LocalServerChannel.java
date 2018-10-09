/*     */ package io.netty.channel.local;
/*     */ 
/*     */ import io.netty.channel.AbstractServerChannel;
/*     */ import io.netty.channel.Channel.Unsafe;
/*     */ import io.netty.channel.ChannelConfig;
/*     */ import io.netty.channel.ChannelPipeline;
/*     */ import io.netty.channel.DefaultChannelConfig;
/*     */ import io.netty.channel.EventLoop;
/*     */ import io.netty.channel.SingleThreadEventLoop;
/*     */ import io.netty.util.concurrent.SingleThreadEventExecutor;
/*     */ import java.net.SocketAddress;
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
/*     */ public class LocalServerChannel
/*     */   extends AbstractServerChannel
/*     */ {
/*  36 */   private final ChannelConfig config = new DefaultChannelConfig(this);
/*  37 */   private final Queue<Object> inboundBuffer = new ArrayDeque();
/*  38 */   private final Runnable shutdownHook = new Runnable()
/*     */   {
/*     */     public void run() {
/*  41 */       LocalServerChannel.this.unsafe().close(LocalServerChannel.this.unsafe().voidPromise());
/*     */     }
/*     */   };
/*     */   
/*     */   private volatile int state;
/*     */   private volatile LocalAddress localAddress;
/*     */   private volatile boolean acceptInProgress;
/*     */   
/*     */   public ChannelConfig config()
/*     */   {
/*  51 */     return this.config;
/*     */   }
/*     */   
/*     */   public LocalAddress localAddress()
/*     */   {
/*  56 */     return (LocalAddress)super.localAddress();
/*     */   }
/*     */   
/*     */   public LocalAddress remoteAddress()
/*     */   {
/*  61 */     return (LocalAddress)super.remoteAddress();
/*     */   }
/*     */   
/*     */   public boolean isOpen()
/*     */   {
/*  66 */     return this.state < 2;
/*     */   }
/*     */   
/*     */   public boolean isActive()
/*     */   {
/*  71 */     return this.state == 1;
/*     */   }
/*     */   
/*     */   protected boolean isCompatible(EventLoop paramEventLoop)
/*     */   {
/*  76 */     return paramEventLoop instanceof SingleThreadEventLoop;
/*     */   }
/*     */   
/*     */   protected SocketAddress localAddress0()
/*     */   {
/*  81 */     return this.localAddress;
/*     */   }
/*     */   
/*     */   protected void doRegister() throws Exception
/*     */   {
/*  86 */     ((SingleThreadEventExecutor)eventLoop()).addShutdownHook(this.shutdownHook);
/*     */   }
/*     */   
/*     */   protected void doBind(SocketAddress paramSocketAddress) throws Exception
/*     */   {
/*  91 */     this.localAddress = LocalChannelRegistry.register(this, this.localAddress, paramSocketAddress);
/*  92 */     this.state = 1;
/*     */   }
/*     */   
/*     */   protected void doClose() throws Exception
/*     */   {
/*  97 */     if (this.state <= 1)
/*     */     {
/*  99 */       if (this.localAddress != null) {
/* 100 */         LocalChannelRegistry.unregister(this.localAddress);
/* 101 */         this.localAddress = null;
/*     */       }
/* 103 */       this.state = 2;
/*     */     }
/*     */   }
/*     */   
/*     */   protected void doDeregister() throws Exception
/*     */   {
/* 109 */     ((SingleThreadEventExecutor)eventLoop()).removeShutdownHook(this.shutdownHook);
/*     */   }
/*     */   
/*     */   protected void doBeginRead() throws Exception
/*     */   {
/* 114 */     if (this.acceptInProgress) {
/* 115 */       return;
/*     */     }
/*     */     
/* 118 */     Queue localQueue = this.inboundBuffer;
/* 119 */     if (localQueue.isEmpty()) {
/* 120 */       this.acceptInProgress = true;
/* 121 */       return;
/*     */     }
/*     */     
/* 124 */     ChannelPipeline localChannelPipeline = pipeline();
/*     */     for (;;) {
/* 126 */       Object localObject = localQueue.poll();
/* 127 */       if (localObject == null) {
/*     */         break;
/*     */       }
/* 130 */       localChannelPipeline.fireChannelRead(localObject);
/*     */     }
/* 132 */     localChannelPipeline.fireChannelReadComplete();
/*     */   }
/*     */   
/*     */   LocalChannel serve(LocalChannel paramLocalChannel) {
/* 136 */     final LocalChannel localLocalChannel = new LocalChannel(this, paramLocalChannel);
/* 137 */     if (eventLoop().inEventLoop()) {
/* 138 */       serve0(localLocalChannel);
/*     */     } else {
/* 140 */       eventLoop().execute(new Runnable()
/*     */       {
/*     */         public void run() {
/* 143 */           LocalServerChannel.this.serve0(localLocalChannel);
/*     */         }
/*     */       });
/*     */     }
/* 147 */     return localLocalChannel;
/*     */   }
/*     */   
/*     */   private void serve0(LocalChannel paramLocalChannel) {
/* 151 */     this.inboundBuffer.add(paramLocalChannel);
/* 152 */     if (this.acceptInProgress) {
/* 153 */       this.acceptInProgress = false;
/* 154 */       ChannelPipeline localChannelPipeline = pipeline();
/*     */       for (;;) {
/* 156 */         Object localObject = this.inboundBuffer.poll();
/* 157 */         if (localObject == null) {
/*     */           break;
/*     */         }
/* 160 */         localChannelPipeline.fireChannelRead(localObject);
/*     */       }
/* 162 */       localChannelPipeline.fireChannelReadComplete();
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\io\netty\channel\local\LocalServerChannel.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */
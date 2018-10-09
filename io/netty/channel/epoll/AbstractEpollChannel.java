/*     */ package io.netty.channel.epoll;
/*     */ 
/*     */ import io.netty.buffer.ByteBuf;
/*     */ import io.netty.buffer.ByteBufAllocator;
/*     */ import io.netty.buffer.ByteBufUtil;
/*     */ import io.netty.buffer.Unpooled;
/*     */ import io.netty.channel.AbstractChannel;
/*     */ import io.netty.channel.AbstractChannel.AbstractUnsafe;
/*     */ import io.netty.channel.Channel;
/*     */ import io.netty.channel.ChannelConfig;
/*     */ import io.netty.channel.ChannelMetadata;
/*     */ import io.netty.channel.EventLoop;
/*     */ import io.netty.util.ReferenceCountUtil;
/*     */ import io.netty.util.internal.OneTimeTask;
/*     */ import java.net.InetSocketAddress;
/*     */ import java.nio.channels.UnresolvedAddressException;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ abstract class AbstractEpollChannel
/*     */   extends AbstractChannel
/*     */ {
/*  33 */   private static final ChannelMetadata DATA = new ChannelMetadata(false);
/*     */   private final int readFlag;
/*     */   protected int flags;
/*     */   protected volatile boolean active;
/*     */   volatile int fd;
/*     */   int id;
/*     */   
/*     */   AbstractEpollChannel(int paramInt1, int paramInt2) {
/*  41 */     this(null, paramInt1, paramInt2, false);
/*     */   }
/*     */   
/*     */   AbstractEpollChannel(Channel paramChannel, int paramInt1, int paramInt2, boolean paramBoolean) {
/*  45 */     super(paramChannel);
/*  46 */     this.fd = paramInt1;
/*  47 */     this.readFlag = paramInt2;
/*  48 */     this.flags |= paramInt2;
/*  49 */     this.active = paramBoolean;
/*     */   }
/*     */   
/*     */   public boolean isActive()
/*     */   {
/*  54 */     return this.active;
/*     */   }
/*     */   
/*     */   public ChannelMetadata metadata()
/*     */   {
/*  59 */     return DATA;
/*     */   }
/*     */   
/*     */   protected void doClose() throws Exception
/*     */   {
/*  64 */     this.active = false;
/*     */     
/*     */ 
/*  67 */     doDeregister();
/*     */     
/*  69 */     int i = this.fd;
/*  70 */     this.fd = -1;
/*  71 */     Native.close(i);
/*     */   }
/*     */   
/*     */   public InetSocketAddress remoteAddress()
/*     */   {
/*  76 */     return (InetSocketAddress)super.remoteAddress();
/*     */   }
/*     */   
/*     */   public InetSocketAddress localAddress()
/*     */   {
/*  81 */     return (InetSocketAddress)super.localAddress();
/*     */   }
/*     */   
/*     */   protected void doDisconnect() throws Exception
/*     */   {
/*  86 */     doClose();
/*     */   }
/*     */   
/*     */   protected boolean isCompatible(EventLoop paramEventLoop)
/*     */   {
/*  91 */     return paramEventLoop instanceof EpollEventLoop;
/*     */   }
/*     */   
/*     */   public boolean isOpen()
/*     */   {
/*  96 */     return this.fd != -1;
/*     */   }
/*     */   
/*     */   protected void doDeregister() throws Exception
/*     */   {
/* 101 */     ((EpollEventLoop)eventLoop()).remove(this);
/*     */   }
/*     */   
/*     */   protected void doBeginRead()
/*     */     throws Exception
/*     */   {
/* 107 */     ((AbstractEpollUnsafe)unsafe()).readPending = true;
/*     */     
/* 109 */     if ((this.flags & this.readFlag) == 0) {
/* 110 */       this.flags |= this.readFlag;
/* 111 */       modifyEvents();
/*     */     }
/*     */   }
/*     */   
/*     */   final void clearEpollIn()
/*     */   {
/* 117 */     if (isRegistered()) {
/* 118 */       EventLoop localEventLoop = eventLoop();
/* 119 */       final AbstractEpollUnsafe localAbstractEpollUnsafe = (AbstractEpollUnsafe)unsafe();
/* 120 */       if (localEventLoop.inEventLoop()) {
/* 121 */         localAbstractEpollUnsafe.clearEpollIn0();
/*     */       }
/*     */       else {
/* 124 */         localEventLoop.execute(new OneTimeTask()
/*     */         {
/*     */           public void run() {
/* 127 */             if ((!AbstractEpollChannel.this.config().isAutoRead()) && (!localAbstractEpollUnsafe.readPending))
/*     */             {
/* 129 */               localAbstractEpollUnsafe.clearEpollIn0();
/*     */             }
/*     */           }
/*     */         });
/*     */       }
/*     */     }
/*     */     else
/*     */     {
/* 137 */       this.flags &= (this.readFlag ^ 0xFFFFFFFF);
/*     */     }
/*     */   }
/*     */   
/*     */   protected final void setEpollOut() {
/* 142 */     if ((this.flags & 0x2) == 0) {
/* 143 */       this.flags |= 0x2;
/* 144 */       modifyEvents();
/*     */     }
/*     */   }
/*     */   
/*     */   protected final void clearEpollOut() {
/* 149 */     if ((this.flags & 0x2) != 0) {
/* 150 */       this.flags &= 0xFFFFFFFD;
/* 151 */       modifyEvents();
/*     */     }
/*     */   }
/*     */   
/*     */   private void modifyEvents() {
/* 156 */     if (isOpen()) {
/* 157 */       ((EpollEventLoop)eventLoop()).modify(this);
/*     */     }
/*     */   }
/*     */   
/*     */   protected void doRegister() throws Exception
/*     */   {
/* 163 */     EpollEventLoop localEpollEventLoop = (EpollEventLoop)eventLoop();
/* 164 */     localEpollEventLoop.add(this);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   protected abstract AbstractEpollUnsafe newUnsafe();
/*     */   
/*     */ 
/*     */   protected final ByteBuf newDirectBuffer(ByteBuf paramByteBuf)
/*     */   {
/* 174 */     return newDirectBuffer(paramByteBuf, paramByteBuf);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   protected final ByteBuf newDirectBuffer(Object paramObject, ByteBuf paramByteBuf)
/*     */   {
/* 183 */     int i = paramByteBuf.readableBytes();
/* 184 */     if (i == 0) {
/* 185 */       ReferenceCountUtil.safeRelease(paramObject);
/* 186 */       return Unpooled.EMPTY_BUFFER;
/*     */     }
/*     */     
/* 189 */     ByteBufAllocator localByteBufAllocator = alloc();
/* 190 */     if (localByteBufAllocator.isDirectBufferPooled()) {
/* 191 */       return newDirectBuffer0(paramObject, paramByteBuf, localByteBufAllocator, i);
/*     */     }
/*     */     
/* 194 */     ByteBuf localByteBuf = ByteBufUtil.threadLocalDirectBuffer();
/* 195 */     if (localByteBuf == null) {
/* 196 */       return newDirectBuffer0(paramObject, paramByteBuf, localByteBufAllocator, i);
/*     */     }
/*     */     
/* 199 */     localByteBuf.writeBytes(paramByteBuf, paramByteBuf.readerIndex(), i);
/* 200 */     ReferenceCountUtil.safeRelease(paramObject);
/* 201 */     return localByteBuf;
/*     */   }
/*     */   
/*     */   private static ByteBuf newDirectBuffer0(Object paramObject, ByteBuf paramByteBuf, ByteBufAllocator paramByteBufAllocator, int paramInt) {
/* 205 */     ByteBuf localByteBuf = paramByteBufAllocator.directBuffer(paramInt);
/* 206 */     localByteBuf.writeBytes(paramByteBuf, paramByteBuf.readerIndex(), paramInt);
/* 207 */     ReferenceCountUtil.safeRelease(paramObject);
/* 208 */     return localByteBuf;
/*     */   }
/*     */   
/*     */   protected static void checkResolvable(InetSocketAddress paramInetSocketAddress) {
/* 212 */     if (paramInetSocketAddress.isUnresolved())
/* 213 */       throw new UnresolvedAddressException(); }
/*     */   
/*     */   protected abstract class AbstractEpollUnsafe extends AbstractChannel.AbstractUnsafe { protected boolean readPending;
/*     */     
/* 217 */     protected AbstractEpollUnsafe() { super(); }
/*     */     
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     abstract void epollInReady();
/*     */     
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     void epollRdHupReady() {}
/*     */     
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     protected void flush0()
/*     */     {
/* 237 */       if (isFlushPending()) {
/* 238 */         return;
/*     */       }
/* 240 */       super.flush0();
/*     */     }
/*     */     
/*     */ 
/*     */ 
/*     */ 
/*     */     void epollOutReady()
/*     */     {
/* 248 */       super.flush0();
/*     */     }
/*     */     
/*     */     private boolean isFlushPending() {
/* 252 */       return (AbstractEpollChannel.this.flags & 0x2) != 0;
/*     */     }
/*     */     
/*     */     protected final void clearEpollIn0() {
/* 256 */       if ((AbstractEpollChannel.this.flags & AbstractEpollChannel.this.readFlag) != 0) {
/* 257 */         AbstractEpollChannel.this.flags &= (AbstractEpollChannel.this.readFlag ^ 0xFFFFFFFF);
/* 258 */         AbstractEpollChannel.this.modifyEvents();
/*     */       }
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\io\netty\channel\epoll\AbstractEpollChannel.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */
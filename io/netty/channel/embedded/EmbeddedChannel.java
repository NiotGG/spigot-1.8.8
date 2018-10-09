/*     */ package io.netty.channel.embedded;
/*     */ 
/*     */ import io.netty.channel.AbstractChannel;
/*     */ import io.netty.channel.AbstractChannel.AbstractUnsafe;
/*     */ import io.netty.channel.ChannelConfig;
/*     */ import io.netty.channel.ChannelFuture;
/*     */ import io.netty.channel.ChannelHandler;
/*     */ import io.netty.channel.ChannelHandlerContext;
/*     */ import io.netty.channel.ChannelInboundHandlerAdapter;
/*     */ import io.netty.channel.ChannelMetadata;
/*     */ import io.netty.channel.ChannelOutboundBuffer;
/*     */ import io.netty.channel.ChannelPipeline;
/*     */ import io.netty.channel.ChannelPromise;
/*     */ import io.netty.channel.DefaultChannelConfig;
/*     */ import io.netty.channel.EventLoop;
/*     */ import io.netty.util.ReferenceCountUtil;
/*     */ import io.netty.util.internal.PlatformDependent;
/*     */ import io.netty.util.internal.RecyclableArrayList;
/*     */ import io.netty.util.internal.logging.InternalLogger;
/*     */ import io.netty.util.internal.logging.InternalLoggerFactory;
/*     */ import java.net.SocketAddress;
/*     */ import java.nio.channels.ClosedChannelException;
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
/*     */ public class EmbeddedChannel
/*     */   extends AbstractChannel
/*     */ {
/*  47 */   private static final InternalLogger logger = InternalLoggerFactory.getInstance(EmbeddedChannel.class);
/*     */   
/*  49 */   private static final ChannelMetadata METADATA = new ChannelMetadata(false);
/*     */   
/*  51 */   private final EmbeddedEventLoop loop = new EmbeddedEventLoop();
/*  52 */   private final ChannelConfig config = new DefaultChannelConfig(this);
/*  53 */   private final SocketAddress localAddress = new EmbeddedSocketAddress();
/*  54 */   private final SocketAddress remoteAddress = new EmbeddedSocketAddress();
/*  55 */   private final Queue<Object> inboundMessages = new ArrayDeque();
/*  56 */   private final Queue<Object> outboundMessages = new ArrayDeque();
/*     */   
/*     */ 
/*     */   private Throwable lastException;
/*     */   
/*     */   private int state;
/*     */   
/*     */ 
/*     */   public EmbeddedChannel(ChannelHandler... paramVarArgs)
/*     */   {
/*  66 */     super(null);
/*     */     
/*  68 */     if (paramVarArgs == null) {
/*  69 */       throw new NullPointerException("handlers");
/*     */     }
/*     */     
/*  72 */     int i = 0;
/*  73 */     ChannelPipeline localChannelPipeline = pipeline();
/*  74 */     for (ChannelHandler localChannelHandler : paramVarArgs) {
/*  75 */       if (localChannelHandler == null) {
/*     */         break;
/*     */       }
/*  78 */       i++;
/*  79 */       localChannelPipeline.addLast(new ChannelHandler[] { localChannelHandler });
/*     */     }
/*     */     
/*  82 */     if (i == 0) {
/*  83 */       throw new IllegalArgumentException("handlers is empty.");
/*     */     }
/*     */     
/*  86 */     this.loop.register(this);
/*  87 */     localChannelPipeline.addLast(new ChannelHandler[] { new LastInboundHandler(null) });
/*     */   }
/*     */   
/*     */   public ChannelMetadata metadata()
/*     */   {
/*  92 */     return METADATA;
/*     */   }
/*     */   
/*     */   public ChannelConfig config()
/*     */   {
/*  97 */     return this.config;
/*     */   }
/*     */   
/*     */   public boolean isOpen()
/*     */   {
/* 102 */     return this.state < 2;
/*     */   }
/*     */   
/*     */   public boolean isActive()
/*     */   {
/* 107 */     return this.state == 1;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public Queue<Object> inboundMessages()
/*     */   {
/* 114 */     return this.inboundMessages;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   @Deprecated
/*     */   public Queue<Object> lastInboundBuffer()
/*     */   {
/* 122 */     return inboundMessages();
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public Queue<Object> outboundMessages()
/*     */   {
/* 129 */     return this.outboundMessages;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   @Deprecated
/*     */   public Queue<Object> lastOutboundBuffer()
/*     */   {
/* 137 */     return outboundMessages();
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public Object readInbound()
/*     */   {
/* 144 */     return this.inboundMessages.poll();
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public Object readOutbound()
/*     */   {
/* 151 */     return this.outboundMessages.poll();
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public boolean writeInbound(Object... paramVarArgs)
/*     */   {
/* 162 */     ensureOpen();
/* 163 */     if (paramVarArgs.length == 0) {
/* 164 */       return !this.inboundMessages.isEmpty();
/*     */     }
/*     */     
/* 167 */     ChannelPipeline localChannelPipeline = pipeline();
/* 168 */     for (Object localObject : paramVarArgs) {
/* 169 */       localChannelPipeline.fireChannelRead(localObject);
/*     */     }
/* 171 */     localChannelPipeline.fireChannelReadComplete();
/* 172 */     runPendingTasks();
/* 173 */     checkException();
/* 174 */     return !this.inboundMessages.isEmpty();
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public boolean writeOutbound(Object... paramVarArgs)
/*     */   {
/* 184 */     ensureOpen();
/* 185 */     if (paramVarArgs.length == 0) {
/* 186 */       return !this.outboundMessages.isEmpty();
/*     */     }
/*     */     
/* 189 */     RecyclableArrayList localRecyclableArrayList = RecyclableArrayList.newInstance(paramVarArgs.length);
/*     */     try {
/* 191 */       for (Object localObject1 : paramVarArgs) {
/* 192 */         if (localObject1 == null) {
/*     */           break;
/*     */         }
/* 195 */         localRecyclableArrayList.add(write(localObject1));
/*     */       }
/*     */       
/* 198 */       flush();
/*     */       
/* 200 */       int i = localRecyclableArrayList.size();
/* 201 */       for (??? = 0; ??? < i; ???++) {
/* 202 */         ChannelFuture localChannelFuture = (ChannelFuture)localRecyclableArrayList.get(???);
/* 203 */         assert (localChannelFuture.isDone());
/* 204 */         if (localChannelFuture.cause() != null) {
/* 205 */           recordException(localChannelFuture.cause());
/*     */         }
/*     */       }
/*     */       
/* 209 */       runPendingTasks();
/* 210 */       checkException();
/* 211 */       return !this.outboundMessages.isEmpty() ? 1 : 0;
/*     */     } finally {
/* 213 */       localRecyclableArrayList.recycle();
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public boolean finish()
/*     */   {
/* 224 */     close();
/* 225 */     runPendingTasks();
/* 226 */     checkException();
/* 227 */     return (!this.inboundMessages.isEmpty()) || (!this.outboundMessages.isEmpty());
/*     */   }
/*     */   
/*     */ 
/*     */   public void runPendingTasks()
/*     */   {
/*     */     try
/*     */     {
/* 235 */       this.loop.runTasks();
/*     */     } catch (Exception localException) {
/* 237 */       recordException(localException);
/*     */     }
/*     */   }
/*     */   
/*     */   private void recordException(Throwable paramThrowable) {
/* 242 */     if (this.lastException == null) {
/* 243 */       this.lastException = paramThrowable;
/*     */     } else {
/* 245 */       logger.warn("More than one exception was raised. Will report only the first one and log others.", paramThrowable);
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public void checkException()
/*     */   {
/* 255 */     Throwable localThrowable = this.lastException;
/* 256 */     if (localThrowable == null) {
/* 257 */       return;
/*     */     }
/*     */     
/* 260 */     this.lastException = null;
/*     */     
/* 262 */     PlatformDependent.throwException(localThrowable);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   protected final void ensureOpen()
/*     */   {
/* 269 */     if (!isOpen()) {
/* 270 */       recordException(new ClosedChannelException());
/* 271 */       checkException();
/*     */     }
/*     */   }
/*     */   
/*     */   protected boolean isCompatible(EventLoop paramEventLoop)
/*     */   {
/* 277 */     return paramEventLoop instanceof EmbeddedEventLoop;
/*     */   }
/*     */   
/*     */   protected SocketAddress localAddress0()
/*     */   {
/* 282 */     return isActive() ? this.localAddress : null;
/*     */   }
/*     */   
/*     */   protected SocketAddress remoteAddress0()
/*     */   {
/* 287 */     return isActive() ? this.remoteAddress : null;
/*     */   }
/*     */   
/*     */   protected void doRegister() throws Exception
/*     */   {
/* 292 */     this.state = 1;
/*     */   }
/*     */   
/*     */   protected void doBind(SocketAddress paramSocketAddress)
/*     */     throws Exception
/*     */   {}
/*     */   
/*     */   protected void doDisconnect()
/*     */     throws Exception
/*     */   {
/* 302 */     doClose();
/*     */   }
/*     */   
/*     */   protected void doClose() throws Exception
/*     */   {
/* 307 */     this.state = 2;
/*     */   }
/*     */   
/*     */ 
/*     */   protected void doBeginRead()
/*     */     throws Exception
/*     */   {}
/*     */   
/*     */   protected AbstractChannel.AbstractUnsafe newUnsafe()
/*     */   {
/* 317 */     return new DefaultUnsafe(null);
/*     */   }
/*     */   
/*     */   protected void doWrite(ChannelOutboundBuffer paramChannelOutboundBuffer) throws Exception
/*     */   {
/*     */     for (;;) {
/* 323 */       Object localObject = paramChannelOutboundBuffer.current();
/* 324 */       if (localObject == null) {
/*     */         break;
/*     */       }
/*     */       
/* 328 */       ReferenceCountUtil.retain(localObject);
/* 329 */       this.outboundMessages.add(localObject);
/* 330 */       paramChannelOutboundBuffer.remove();
/*     */     }
/*     */   }
/*     */   
/* 334 */   private class DefaultUnsafe extends AbstractChannel.AbstractUnsafe { private DefaultUnsafe() { super(); }
/*     */     
/*     */     public void connect(SocketAddress paramSocketAddress1, SocketAddress paramSocketAddress2, ChannelPromise paramChannelPromise) {
/* 337 */       safeSetSuccess(paramChannelPromise);
/*     */     }
/*     */   }
/*     */   
/*     */   private final class LastInboundHandler extends ChannelInboundHandlerAdapter {
/*     */     private LastInboundHandler() {}
/*     */     
/* 344 */     public void channelRead(ChannelHandlerContext paramChannelHandlerContext, Object paramObject) throws Exception { EmbeddedChannel.this.inboundMessages.add(paramObject); }
/*     */     
/*     */     public void exceptionCaught(ChannelHandlerContext paramChannelHandlerContext, Throwable paramThrowable)
/*     */       throws Exception
/*     */     {
/* 349 */       EmbeddedChannel.this.recordException(paramThrowable);
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\io\netty\channel\embedded\EmbeddedChannel.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */
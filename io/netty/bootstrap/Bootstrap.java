/*     */ package io.netty.bootstrap;
/*     */ 
/*     */ import io.netty.channel.Channel;
/*     */ import io.netty.channel.ChannelConfig;
/*     */ import io.netty.channel.ChannelFuture;
/*     */ import io.netty.channel.ChannelFutureListener;
/*     */ import io.netty.channel.ChannelHandler;
/*     */ import io.netty.channel.ChannelOption;
/*     */ import io.netty.channel.ChannelPipeline;
/*     */ import io.netty.channel.ChannelPromise;
/*     */ import io.netty.channel.EventLoop;
/*     */ import io.netty.util.Attribute;
/*     */ import io.netty.util.AttributeKey;
/*     */ import io.netty.util.internal.logging.InternalLogger;
/*     */ import io.netty.util.internal.logging.InternalLoggerFactory;
/*     */ import java.net.InetAddress;
/*     */ import java.net.InetSocketAddress;
/*     */ import java.net.SocketAddress;
/*     */ import java.util.Iterator;
/*     */ import java.util.Map;
/*     */ import java.util.Map.Entry;
/*     */ import java.util.Set;
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
/*     */ public final class Bootstrap
/*     */   extends AbstractBootstrap<Bootstrap, Channel>
/*     */ {
/*  43 */   private static final InternalLogger logger = InternalLoggerFactory.getInstance(Bootstrap.class);
/*     */   private volatile SocketAddress remoteAddress;
/*     */   
/*     */   public Bootstrap() {}
/*     */   
/*     */   private Bootstrap(Bootstrap paramBootstrap)
/*     */   {
/*  50 */     super(paramBootstrap);
/*  51 */     this.remoteAddress = paramBootstrap.remoteAddress;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public Bootstrap remoteAddress(SocketAddress paramSocketAddress)
/*     */   {
/*  59 */     this.remoteAddress = paramSocketAddress;
/*  60 */     return this;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public Bootstrap remoteAddress(String paramString, int paramInt)
/*     */   {
/*  67 */     this.remoteAddress = new InetSocketAddress(paramString, paramInt);
/*  68 */     return this;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public Bootstrap remoteAddress(InetAddress paramInetAddress, int paramInt)
/*     */   {
/*  75 */     this.remoteAddress = new InetSocketAddress(paramInetAddress, paramInt);
/*  76 */     return this;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public ChannelFuture connect()
/*     */   {
/*  83 */     validate();
/*  84 */     SocketAddress localSocketAddress = this.remoteAddress;
/*  85 */     if (localSocketAddress == null) {
/*  86 */       throw new IllegalStateException("remoteAddress not set");
/*     */     }
/*     */     
/*  89 */     return doConnect(localSocketAddress, localAddress());
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public ChannelFuture connect(String paramString, int paramInt)
/*     */   {
/*  96 */     return connect(new InetSocketAddress(paramString, paramInt));
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public ChannelFuture connect(InetAddress paramInetAddress, int paramInt)
/*     */   {
/* 103 */     return connect(new InetSocketAddress(paramInetAddress, paramInt));
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public ChannelFuture connect(SocketAddress paramSocketAddress)
/*     */   {
/* 110 */     if (paramSocketAddress == null) {
/* 111 */       throw new NullPointerException("remoteAddress");
/*     */     }
/*     */     
/* 114 */     validate();
/* 115 */     return doConnect(paramSocketAddress, localAddress());
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public ChannelFuture connect(SocketAddress paramSocketAddress1, SocketAddress paramSocketAddress2)
/*     */   {
/* 122 */     if (paramSocketAddress1 == null) {
/* 123 */       throw new NullPointerException("remoteAddress");
/*     */     }
/* 125 */     validate();
/* 126 */     return doConnect(paramSocketAddress1, paramSocketAddress2);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   private ChannelFuture doConnect(final SocketAddress paramSocketAddress1, final SocketAddress paramSocketAddress2)
/*     */   {
/* 133 */     final ChannelFuture localChannelFuture = initAndRegister();
/* 134 */     final Channel localChannel = localChannelFuture.channel();
/* 135 */     if (localChannelFuture.cause() != null) {
/* 136 */       return localChannelFuture;
/*     */     }
/*     */     
/* 139 */     final ChannelPromise localChannelPromise = localChannel.newPromise();
/* 140 */     if (localChannelFuture.isDone()) {
/* 141 */       doConnect0(localChannelFuture, localChannel, paramSocketAddress1, paramSocketAddress2, localChannelPromise);
/*     */     } else {
/* 143 */       localChannelFuture.addListener(new ChannelFutureListener()
/*     */       {
/*     */         public void operationComplete(ChannelFuture paramAnonymousChannelFuture) throws Exception {
/* 146 */           Bootstrap.doConnect0(localChannelFuture, localChannel, paramSocketAddress1, paramSocketAddress2, localChannelPromise);
/*     */         }
/*     */       });
/*     */     }
/*     */     
/* 151 */     return localChannelPromise;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   private static void doConnect0(ChannelFuture paramChannelFuture, final Channel paramChannel, final SocketAddress paramSocketAddress1, final SocketAddress paramSocketAddress2, final ChannelPromise paramChannelPromise)
/*     */   {
/* 160 */     paramChannel.eventLoop().execute(new Runnable()
/*     */     {
/*     */       public void run() {
/* 163 */         if (this.val$regFuture.isSuccess()) {
/* 164 */           if (paramSocketAddress2 == null) {
/* 165 */             paramChannel.connect(paramSocketAddress1, paramChannelPromise);
/*     */           } else {
/* 167 */             paramChannel.connect(paramSocketAddress1, paramSocketAddress2, paramChannelPromise);
/*     */           }
/* 169 */           paramChannelPromise.addListener(ChannelFutureListener.CLOSE_ON_FAILURE);
/*     */         } else {
/* 171 */           paramChannelPromise.setFailure(this.val$regFuture.cause());
/*     */         }
/*     */       }
/*     */     });
/*     */   }
/*     */   
/*     */   void init(Channel paramChannel)
/*     */     throws Exception
/*     */   {
/* 180 */     ChannelPipeline localChannelPipeline = paramChannel.pipeline();
/* 181 */     localChannelPipeline.addLast(new ChannelHandler[] { handler() });
/*     */     
/* 183 */     Map localMap = options();
/* 184 */     Iterator localIterator; Object localObject1; synchronized (localMap) {
/* 185 */       for (localIterator = localMap.entrySet().iterator(); localIterator.hasNext();) { localObject1 = (Map.Entry)localIterator.next();
/*     */         try {
/* 187 */           if (!paramChannel.config().setOption((ChannelOption)((Map.Entry)localObject1).getKey(), ((Map.Entry)localObject1).getValue())) {
/* 188 */             logger.warn("Unknown channel option: " + localObject1);
/*     */           }
/*     */         } catch (Throwable localThrowable) {
/* 191 */           logger.warn("Failed to set a channel option: " + paramChannel, localThrowable);
/*     */         }
/*     */       }
/*     */     }
/*     */     
/* 196 */     ??? = attrs();
/* 197 */     synchronized (???) {
/* 198 */       for (localObject1 = ((Map)???).entrySet().iterator(); ((Iterator)localObject1).hasNext();) { Map.Entry localEntry = (Map.Entry)((Iterator)localObject1).next();
/* 199 */         paramChannel.attr((AttributeKey)localEntry.getKey()).set(localEntry.getValue());
/*     */       }
/*     */     }
/*     */   }
/*     */   
/*     */   public Bootstrap validate()
/*     */   {
/* 206 */     super.validate();
/* 207 */     if (handler() == null) {
/* 208 */       throw new IllegalStateException("handler not set");
/*     */     }
/* 210 */     return this;
/*     */   }
/*     */   
/*     */ 
/*     */   public Bootstrap clone()
/*     */   {
/* 216 */     return new Bootstrap(this);
/*     */   }
/*     */   
/*     */   public String toString()
/*     */   {
/* 221 */     if (this.remoteAddress == null) {
/* 222 */       return super.toString();
/*     */     }
/*     */     
/* 225 */     StringBuilder localStringBuilder = new StringBuilder(super.toString());
/* 226 */     localStringBuilder.setLength(localStringBuilder.length() - 1);
/* 227 */     localStringBuilder.append(", remoteAddress: ");
/* 228 */     localStringBuilder.append(this.remoteAddress);
/* 229 */     localStringBuilder.append(')');
/*     */     
/* 231 */     return localStringBuilder.toString();
/*     */   }
/*     */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\io\netty\bootstrap\Bootstrap.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */
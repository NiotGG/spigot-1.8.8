/*     */ package io.netty.bootstrap;
/*     */ 
/*     */ import io.netty.channel.Channel;
/*     */ import io.netty.channel.Channel.Unsafe;
/*     */ import io.netty.channel.ChannelException;
/*     */ import io.netty.channel.ChannelFuture;
/*     */ import io.netty.channel.ChannelFutureListener;
/*     */ import io.netty.channel.ChannelHandler;
/*     */ import io.netty.channel.ChannelOption;
/*     */ import io.netty.channel.ChannelPromise;
/*     */ import io.netty.channel.DefaultChannelPromise;
/*     */ import io.netty.channel.EventLoop;
/*     */ import io.netty.channel.EventLoopGroup;
/*     */ import io.netty.util.AttributeKey;
/*     */ import io.netty.util.concurrent.EventExecutor;
/*     */ import io.netty.util.concurrent.GlobalEventExecutor;
/*     */ import io.netty.util.internal.StringUtil;
/*     */ import java.net.InetAddress;
/*     */ import java.net.InetSocketAddress;
/*     */ import java.net.SocketAddress;
/*     */ import java.util.LinkedHashMap;
/*     */ import java.util.Map;
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
/*     */ public abstract class AbstractBootstrap<B extends AbstractBootstrap<B, C>, C extends Channel>
/*     */   implements Cloneable
/*     */ {
/*     */   private volatile EventLoopGroup group;
/*     */   private volatile ChannelFactory<? extends C> channelFactory;
/*     */   private volatile SocketAddress localAddress;
/*  52 */   private final Map<ChannelOption<?>, Object> options = new LinkedHashMap();
/*  53 */   private final Map<AttributeKey<?>, Object> attrs = new LinkedHashMap();
/*     */   
/*     */   private volatile ChannelHandler handler;
/*     */   
/*     */   AbstractBootstrap() {}
/*     */   
/*     */   AbstractBootstrap(AbstractBootstrap<B, C> paramAbstractBootstrap)
/*     */   {
/*  61 */     this.group = paramAbstractBootstrap.group;
/*  62 */     this.channelFactory = paramAbstractBootstrap.channelFactory;
/*  63 */     this.handler = paramAbstractBootstrap.handler;
/*  64 */     this.localAddress = paramAbstractBootstrap.localAddress;
/*  65 */     synchronized (paramAbstractBootstrap.options) {
/*  66 */       this.options.putAll(paramAbstractBootstrap.options);
/*     */     }
/*  68 */     synchronized (paramAbstractBootstrap.attrs) {
/*  69 */       this.attrs.putAll(paramAbstractBootstrap.attrs);
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public B group(EventLoopGroup paramEventLoopGroup)
/*     */   {
/*  79 */     if (paramEventLoopGroup == null) {
/*  80 */       throw new NullPointerException("group");
/*     */     }
/*  82 */     if (this.group != null) {
/*  83 */       throw new IllegalStateException("group set already");
/*     */     }
/*  85 */     this.group = paramEventLoopGroup;
/*  86 */     return this;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public B channel(Class<? extends C> paramClass)
/*     */   {
/*  95 */     if (paramClass == null) {
/*  96 */       throw new NullPointerException("channelClass");
/*     */     }
/*  98 */     return channelFactory(new BootstrapChannelFactory(paramClass));
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public B channelFactory(ChannelFactory<? extends C> paramChannelFactory)
/*     */   {
/* 110 */     if (paramChannelFactory == null) {
/* 111 */       throw new NullPointerException("channelFactory");
/*     */     }
/* 113 */     if (this.channelFactory != null) {
/* 114 */       throw new IllegalStateException("channelFactory set already");
/*     */     }
/*     */     
/* 117 */     this.channelFactory = paramChannelFactory;
/* 118 */     return this;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public B localAddress(SocketAddress paramSocketAddress)
/*     */   {
/* 127 */     this.localAddress = paramSocketAddress;
/* 128 */     return this;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public B localAddress(int paramInt)
/*     */   {
/* 135 */     return localAddress(new InetSocketAddress(paramInt));
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public B localAddress(String paramString, int paramInt)
/*     */   {
/* 142 */     return localAddress(new InetSocketAddress(paramString, paramInt));
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public B localAddress(InetAddress paramInetAddress, int paramInt)
/*     */   {
/* 149 */     return localAddress(new InetSocketAddress(paramInetAddress, paramInt));
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public <T> B option(ChannelOption<T> paramChannelOption, T paramT)
/*     */   {
/* 158 */     if (paramChannelOption == null) {
/* 159 */       throw new NullPointerException("option");
/*     */     }
/* 161 */     if (paramT == null) {
/* 162 */       synchronized (this.options) {
/* 163 */         this.options.remove(paramChannelOption);
/*     */       }
/*     */     } else {
/* 166 */       synchronized (this.options) {
/* 167 */         this.options.put(paramChannelOption, paramT);
/*     */       }
/*     */     }
/* 170 */     return this;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public <T> B attr(AttributeKey<T> paramAttributeKey, T paramT)
/*     */   {
/* 178 */     if (paramAttributeKey == null) {
/* 179 */       throw new NullPointerException("key");
/*     */     }
/* 181 */     if (paramT == null) {
/* 182 */       synchronized (this.attrs) {
/* 183 */         this.attrs.remove(paramAttributeKey);
/*     */       }
/*     */     } else {
/* 186 */       synchronized (this.attrs) {
/* 187 */         this.attrs.put(paramAttributeKey, paramT);
/*     */       }
/*     */     }
/*     */     
/*     */ 
/* 192 */     ??? = this;
/* 193 */     return (B)???;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public B validate()
/*     */   {
/* 202 */     if (this.group == null) {
/* 203 */       throw new IllegalStateException("group not set");
/*     */     }
/* 205 */     if (this.channelFactory == null) {
/* 206 */       throw new IllegalStateException("channel or channelFactory not set");
/*     */     }
/* 208 */     return this;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public abstract B clone();
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public ChannelFuture register()
/*     */   {
/* 224 */     validate();
/* 225 */     return initAndRegister();
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public ChannelFuture bind()
/*     */   {
/* 232 */     validate();
/* 233 */     SocketAddress localSocketAddress = this.localAddress;
/* 234 */     if (localSocketAddress == null) {
/* 235 */       throw new IllegalStateException("localAddress not set");
/*     */     }
/* 237 */     return doBind(localSocketAddress);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public ChannelFuture bind(int paramInt)
/*     */   {
/* 244 */     return bind(new InetSocketAddress(paramInt));
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public ChannelFuture bind(String paramString, int paramInt)
/*     */   {
/* 251 */     return bind(new InetSocketAddress(paramString, paramInt));
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public ChannelFuture bind(InetAddress paramInetAddress, int paramInt)
/*     */   {
/* 258 */     return bind(new InetSocketAddress(paramInetAddress, paramInt));
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public ChannelFuture bind(SocketAddress paramSocketAddress)
/*     */   {
/* 265 */     validate();
/* 266 */     if (paramSocketAddress == null) {
/* 267 */       throw new NullPointerException("localAddress");
/*     */     }
/* 269 */     return doBind(paramSocketAddress);
/*     */   }
/*     */   
/*     */   private ChannelFuture doBind(final SocketAddress paramSocketAddress) {
/* 273 */     final ChannelFuture localChannelFuture = initAndRegister();
/* 274 */     final Channel localChannel = localChannelFuture.channel();
/* 275 */     if (localChannelFuture.cause() != null) {
/* 276 */       return localChannelFuture;
/*     */     }
/*     */     
/*     */     final Object localObject;
/* 280 */     if (localChannelFuture.isDone()) {
/* 281 */       localObject = localChannel.newPromise();
/* 282 */       doBind0(localChannelFuture, localChannel, paramSocketAddress, (ChannelPromise)localObject);
/*     */     }
/*     */     else {
/* 285 */       localObject = new PendingRegistrationPromise(localChannel, null);
/* 286 */       localChannelFuture.addListener(new ChannelFutureListener()
/*     */       {
/*     */         public void operationComplete(ChannelFuture paramAnonymousChannelFuture) throws Exception {
/* 289 */           AbstractBootstrap.doBind0(localChannelFuture, localChannel, paramSocketAddress, localObject);
/*     */         }
/*     */       });
/*     */     }
/*     */     
/* 294 */     return (ChannelFuture)localObject;
/*     */   }
/*     */   
/*     */   final ChannelFuture initAndRegister() {
/* 298 */     Channel localChannel = channelFactory().newChannel();
/*     */     try {
/* 300 */       init(localChannel);
/*     */     } catch (Throwable localThrowable) {
/* 302 */       localChannel.unsafe().closeForcibly();
/*     */       
/* 304 */       return new DefaultChannelPromise(localChannel, GlobalEventExecutor.INSTANCE).setFailure(localThrowable);
/*     */     }
/*     */     
/* 307 */     ChannelFuture localChannelFuture = group().register(localChannel);
/* 308 */     if (localChannelFuture.cause() != null) {
/* 309 */       if (localChannel.isRegistered()) {
/* 310 */         localChannel.close();
/*     */       } else {
/* 312 */         localChannel.unsafe().closeForcibly();
/*     */       }
/*     */     }
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
/* 325 */     return localChannelFuture;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   abstract void init(Channel paramChannel)
/*     */     throws Exception;
/*     */   
/*     */ 
/*     */   private static void doBind0(ChannelFuture paramChannelFuture, final Channel paramChannel, final SocketAddress paramSocketAddress, final ChannelPromise paramChannelPromise)
/*     */   {
/* 336 */     paramChannel.eventLoop().execute(new Runnable()
/*     */     {
/*     */       public void run() {
/* 339 */         if (this.val$regFuture.isSuccess()) {
/* 340 */           paramChannel.bind(paramSocketAddress, paramChannelPromise).addListener(ChannelFutureListener.CLOSE_ON_FAILURE);
/*     */         } else {
/* 342 */           paramChannelPromise.setFailure(this.val$regFuture.cause());
/*     */         }
/*     */       }
/*     */     });
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public B handler(ChannelHandler paramChannelHandler)
/*     */   {
/* 353 */     if (paramChannelHandler == null) {
/* 354 */       throw new NullPointerException("handler");
/*     */     }
/* 356 */     this.handler = paramChannelHandler;
/* 357 */     return this;
/*     */   }
/*     */   
/*     */   final SocketAddress localAddress() {
/* 361 */     return this.localAddress;
/*     */   }
/*     */   
/*     */   final ChannelFactory<? extends C> channelFactory() {
/* 365 */     return this.channelFactory;
/*     */   }
/*     */   
/*     */   final ChannelHandler handler() {
/* 369 */     return this.handler;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public final EventLoopGroup group()
/*     */   {
/* 376 */     return this.group;
/*     */   }
/*     */   
/*     */   final Map<ChannelOption<?>, Object> options() {
/* 380 */     return this.options;
/*     */   }
/*     */   
/*     */   final Map<AttributeKey<?>, Object> attrs() {
/* 384 */     return this.attrs;
/*     */   }
/*     */   
/*     */   public String toString()
/*     */   {
/* 389 */     StringBuilder localStringBuilder = new StringBuilder();
/* 390 */     localStringBuilder.append(StringUtil.simpleClassName(this));
/* 391 */     localStringBuilder.append('(');
/* 392 */     if (this.group != null) {
/* 393 */       localStringBuilder.append("group: ");
/* 394 */       localStringBuilder.append(StringUtil.simpleClassName(this.group));
/* 395 */       localStringBuilder.append(", ");
/*     */     }
/* 397 */     if (this.channelFactory != null) {
/* 398 */       localStringBuilder.append("channelFactory: ");
/* 399 */       localStringBuilder.append(this.channelFactory);
/* 400 */       localStringBuilder.append(", ");
/*     */     }
/* 402 */     if (this.localAddress != null) {
/* 403 */       localStringBuilder.append("localAddress: ");
/* 404 */       localStringBuilder.append(this.localAddress);
/* 405 */       localStringBuilder.append(", ");
/*     */     }
/* 407 */     synchronized (this.options) {
/* 408 */       if (!this.options.isEmpty()) {
/* 409 */         localStringBuilder.append("options: ");
/* 410 */         localStringBuilder.append(this.options);
/* 411 */         localStringBuilder.append(", ");
/*     */       }
/*     */     }
/* 414 */     synchronized (this.attrs) {
/* 415 */       if (!this.attrs.isEmpty()) {
/* 416 */         localStringBuilder.append("attrs: ");
/* 417 */         localStringBuilder.append(this.attrs);
/* 418 */         localStringBuilder.append(", ");
/*     */       }
/*     */     }
/* 421 */     if (this.handler != null) {
/* 422 */       localStringBuilder.append("handler: ");
/* 423 */       localStringBuilder.append(this.handler);
/* 424 */       localStringBuilder.append(", ");
/*     */     }
/* 426 */     if (localStringBuilder.charAt(localStringBuilder.length() - 1) == '(') {
/* 427 */       localStringBuilder.append(')');
/*     */     } else {
/* 429 */       localStringBuilder.setCharAt(localStringBuilder.length() - 2, ')');
/* 430 */       localStringBuilder.setLength(localStringBuilder.length() - 1);
/*     */     }
/* 432 */     return localStringBuilder.toString();
/*     */   }
/*     */   
/*     */   private static final class BootstrapChannelFactory<T extends Channel> implements ChannelFactory<T> {
/*     */     private final Class<? extends T> clazz;
/*     */     
/*     */     BootstrapChannelFactory(Class<? extends T> paramClass) {
/* 439 */       this.clazz = paramClass;
/*     */     }
/*     */     
/*     */     public T newChannel()
/*     */     {
/*     */       try {
/* 445 */         return (Channel)this.clazz.newInstance();
/*     */       } catch (Throwable localThrowable) {
/* 447 */         throw new ChannelException("Unable to create Channel from class " + this.clazz, localThrowable);
/*     */       }
/*     */     }
/*     */     
/*     */     public String toString()
/*     */     {
/* 453 */       return StringUtil.simpleClassName(this.clazz) + ".class";
/*     */     }
/*     */   }
/*     */   
/*     */   private static final class PendingRegistrationPromise extends DefaultChannelPromise {
/*     */     private PendingRegistrationPromise(Channel paramChannel) {
/* 459 */       super();
/*     */     }
/*     */     
/*     */     protected EventExecutor executor()
/*     */     {
/* 464 */       if (channel().isRegistered())
/*     */       {
/*     */ 
/*     */ 
/*     */ 
/* 469 */         return super.executor();
/*     */       }
/*     */       
/* 472 */       return GlobalEventExecutor.INSTANCE;
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\io\netty\bootstrap\AbstractBootstrap.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */
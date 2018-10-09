/*     */ package io.netty.bootstrap;
/*     */ 
/*     */ import io.netty.channel.Channel;
/*     */ import io.netty.channel.Channel.Unsafe;
/*     */ import io.netty.channel.ChannelConfig;
/*     */ import io.netty.channel.ChannelFuture;
/*     */ import io.netty.channel.ChannelFutureListener;
/*     */ import io.netty.channel.ChannelHandler;
/*     */ import io.netty.channel.ChannelHandlerContext;
/*     */ import io.netty.channel.ChannelInboundHandlerAdapter;
/*     */ import io.netty.channel.ChannelInitializer;
/*     */ import io.netty.channel.ChannelOption;
/*     */ import io.netty.channel.ChannelPipeline;
/*     */ import io.netty.channel.EventLoop;
/*     */ import io.netty.channel.EventLoopGroup;
/*     */ import io.netty.channel.ServerChannel;
/*     */ import io.netty.util.Attribute;
/*     */ import io.netty.util.AttributeKey;
/*     */ import io.netty.util.internal.StringUtil;
/*     */ import io.netty.util.internal.logging.InternalLogger;
/*     */ import io.netty.util.internal.logging.InternalLoggerFactory;
/*     */ import java.util.Iterator;
/*     */ import java.util.LinkedHashMap;
/*     */ import java.util.Map;
/*     */ import java.util.Map.Entry;
/*     */ import java.util.Set;
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
/*     */ public final class ServerBootstrap
/*     */   extends AbstractBootstrap<ServerBootstrap, ServerChannel>
/*     */ {
/*  47 */   private static final InternalLogger logger = InternalLoggerFactory.getInstance(ServerBootstrap.class);
/*     */   
/*  49 */   private final Map<ChannelOption<?>, Object> childOptions = new LinkedHashMap();
/*  50 */   private final Map<AttributeKey<?>, Object> childAttrs = new LinkedHashMap();
/*     */   private volatile EventLoopGroup childGroup;
/*     */   private volatile ChannelHandler childHandler;
/*     */   
/*     */   public ServerBootstrap() {}
/*     */   
/*     */   private ServerBootstrap(ServerBootstrap paramServerBootstrap) {
/*  57 */     super(paramServerBootstrap);
/*  58 */     this.childGroup = paramServerBootstrap.childGroup;
/*  59 */     this.childHandler = paramServerBootstrap.childHandler;
/*  60 */     synchronized (paramServerBootstrap.childOptions) {
/*  61 */       this.childOptions.putAll(paramServerBootstrap.childOptions);
/*     */     }
/*  63 */     synchronized (paramServerBootstrap.childAttrs) {
/*  64 */       this.childAttrs.putAll(paramServerBootstrap.childAttrs);
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public ServerBootstrap group(EventLoopGroup paramEventLoopGroup)
/*     */   {
/*  73 */     return group(paramEventLoopGroup, paramEventLoopGroup);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public ServerBootstrap group(EventLoopGroup paramEventLoopGroup1, EventLoopGroup paramEventLoopGroup2)
/*     */   {
/*  82 */     super.group(paramEventLoopGroup1);
/*  83 */     if (paramEventLoopGroup2 == null) {
/*  84 */       throw new NullPointerException("childGroup");
/*     */     }
/*  86 */     if (this.childGroup != null) {
/*  87 */       throw new IllegalStateException("childGroup set already");
/*     */     }
/*  89 */     this.childGroup = paramEventLoopGroup2;
/*  90 */     return this;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public <T> ServerBootstrap childOption(ChannelOption<T> paramChannelOption, T paramT)
/*     */   {
/*  99 */     if (paramChannelOption == null) {
/* 100 */       throw new NullPointerException("childOption");
/*     */     }
/* 102 */     if (paramT == null) {
/* 103 */       synchronized (this.childOptions) {
/* 104 */         this.childOptions.remove(paramChannelOption);
/*     */       }
/*     */     } else {
/* 107 */       synchronized (this.childOptions) {
/* 108 */         this.childOptions.put(paramChannelOption, paramT);
/*     */       }
/*     */     }
/* 111 */     return this;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public <T> ServerBootstrap childAttr(AttributeKey<T> paramAttributeKey, T paramT)
/*     */   {
/* 119 */     if (paramAttributeKey == null) {
/* 120 */       throw new NullPointerException("childKey");
/*     */     }
/* 122 */     if (paramT == null) {
/* 123 */       this.childAttrs.remove(paramAttributeKey);
/*     */     } else {
/* 125 */       this.childAttrs.put(paramAttributeKey, paramT);
/*     */     }
/* 127 */     return this;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public ServerBootstrap childHandler(ChannelHandler paramChannelHandler)
/*     */   {
/* 134 */     if (paramChannelHandler == null) {
/* 135 */       throw new NullPointerException("childHandler");
/*     */     }
/* 137 */     this.childHandler = paramChannelHandler;
/* 138 */     return this;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public EventLoopGroup childGroup()
/*     */   {
/* 146 */     return this.childGroup;
/*     */   }
/*     */   
/*     */   void init(Channel paramChannel) throws Exception
/*     */   {
/* 151 */     Map localMap = options();
/* 152 */     synchronized (localMap) {
/* 153 */       paramChannel.config().setOptions(localMap);
/*     */     }
/*     */     
/* 156 */     ??? = attrs();
/* 157 */     final Object localObject4; synchronized (???) {
/* 158 */       for (localObject2 = ((Map)???).entrySet().iterator(); ((Iterator)localObject2).hasNext();) { localObject3 = (Map.Entry)((Iterator)localObject2).next();
/*     */         
/* 160 */         localObject4 = (AttributeKey)((Map.Entry)localObject3).getKey();
/* 161 */         paramChannel.attr((AttributeKey)localObject4).set(((Map.Entry)localObject3).getValue());
/*     */       }
/*     */     }
/*     */     
/* 165 */     ??? = paramChannel.pipeline();
/* 166 */     if (handler() != null) {
/* 167 */       ((ChannelPipeline)???).addLast(new ChannelHandler[] { handler() });
/*     */     }
/*     */     
/* 170 */     final Object localObject2 = this.childGroup;
/* 171 */     final Object localObject3 = this.childHandler;
/*     */     
/*     */ 
/* 174 */     synchronized (this.childOptions) {
/* 175 */       localObject4 = (Map.Entry[])this.childOptions.entrySet().toArray(newOptionArray(this.childOptions.size())); }
/*     */     final Map.Entry[] arrayOfEntry;
/* 177 */     synchronized (this.childAttrs) {
/* 178 */       arrayOfEntry = (Map.Entry[])this.childAttrs.entrySet().toArray(newAttrArray(this.childAttrs.size()));
/*     */     }
/*     */     
/* 181 */     ((ChannelPipeline)???).addLast(new ChannelHandler[] { new ChannelInitializer()
/*     */     {
/*     */       public void initChannel(Channel paramAnonymousChannel) throws Exception {
/* 184 */         paramAnonymousChannel.pipeline().addLast(new ChannelHandler[] { new ServerBootstrap.ServerBootstrapAcceptor(localObject2, localObject3, localObject4, arrayOfEntry) });
/*     */       }
/*     */     } });
/*     */   }
/*     */   
/*     */ 
/*     */   public ServerBootstrap validate()
/*     */   {
/* 192 */     super.validate();
/* 193 */     if (this.childHandler == null) {
/* 194 */       throw new IllegalStateException("childHandler not set");
/*     */     }
/* 196 */     if (this.childGroup == null) {
/* 197 */       logger.warn("childGroup is not set. Using parentGroup instead.");
/* 198 */       this.childGroup = group();
/*     */     }
/* 200 */     return this;
/*     */   }
/*     */   
/*     */   private static Map.Entry<ChannelOption<?>, Object>[] newOptionArray(int paramInt)
/*     */   {
/* 205 */     return new Map.Entry[paramInt];
/*     */   }
/*     */   
/*     */   private static Map.Entry<AttributeKey<?>, Object>[] newAttrArray(int paramInt)
/*     */   {
/* 210 */     return new Map.Entry[paramInt];
/*     */   }
/*     */   
/*     */   private static class ServerBootstrapAcceptor
/*     */     extends ChannelInboundHandlerAdapter
/*     */   {
/*     */     private final EventLoopGroup childGroup;
/*     */     private final ChannelHandler childHandler;
/*     */     private final Map.Entry<ChannelOption<?>, Object>[] childOptions;
/*     */     private final Map.Entry<AttributeKey<?>, Object>[] childAttrs;
/*     */     
/*     */     ServerBootstrapAcceptor(EventLoopGroup paramEventLoopGroup, ChannelHandler paramChannelHandler, Map.Entry<ChannelOption<?>, Object>[] paramArrayOfEntry, Map.Entry<AttributeKey<?>, Object>[] paramArrayOfEntry1)
/*     */     {
/* 223 */       this.childGroup = paramEventLoopGroup;
/* 224 */       this.childHandler = paramChannelHandler;
/* 225 */       this.childOptions = paramArrayOfEntry;
/* 226 */       this.childAttrs = paramArrayOfEntry1;
/*     */     }
/*     */     
/*     */ 
/*     */     public void channelRead(ChannelHandlerContext paramChannelHandlerContext, Object paramObject)
/*     */     {
/* 232 */       final Channel localChannel = (Channel)paramObject;
/*     */       
/* 234 */       localChannel.pipeline().addLast(new ChannelHandler[] { this.childHandler });
/*     */       Map.Entry localEntry;
/* 236 */       for (localEntry : this.childOptions) {
/*     */         try {
/* 238 */           if (!localChannel.config().setOption((ChannelOption)localEntry.getKey(), localEntry.getValue())) {
/* 239 */             ServerBootstrap.logger.warn("Unknown channel option: " + localEntry);
/*     */           }
/*     */         } catch (Throwable localThrowable2) {
/* 242 */           ServerBootstrap.logger.warn("Failed to set a channel option: " + localChannel, localThrowable2);
/*     */         }
/*     */       }
/*     */       
/* 246 */       for (localEntry : this.childAttrs) {
/* 247 */         localChannel.attr((AttributeKey)localEntry.getKey()).set(localEntry.getValue());
/*     */       }
/*     */       try
/*     */       {
/* 251 */         this.childGroup.register(localChannel).addListener(new ChannelFutureListener()
/*     */         {
/*     */           public void operationComplete(ChannelFuture paramAnonymousChannelFuture) throws Exception {
/* 254 */             if (!paramAnonymousChannelFuture.isSuccess()) {
/* 255 */               ServerBootstrap.ServerBootstrapAcceptor.forceClose(localChannel, paramAnonymousChannelFuture.cause());
/*     */             }
/*     */           }
/*     */         });
/*     */       } catch (Throwable localThrowable1) {
/* 260 */         forceClose(localChannel, localThrowable1);
/*     */       }
/*     */     }
/*     */     
/*     */     private static void forceClose(Channel paramChannel, Throwable paramThrowable) {
/* 265 */       paramChannel.unsafe().closeForcibly();
/* 266 */       ServerBootstrap.logger.warn("Failed to register an accepted channel: " + paramChannel, paramThrowable);
/*     */     }
/*     */     
/*     */     public void exceptionCaught(ChannelHandlerContext paramChannelHandlerContext, Throwable paramThrowable) throws Exception
/*     */     {
/* 271 */       final ChannelConfig localChannelConfig = paramChannelHandlerContext.channel().config();
/* 272 */       if (localChannelConfig.isAutoRead())
/*     */       {
/*     */ 
/* 275 */         localChannelConfig.setAutoRead(false);
/* 276 */         paramChannelHandlerContext.channel().eventLoop().schedule(new Runnable()
/*     */         {
/*     */ 
/* 279 */           public void run() { localChannelConfig.setAutoRead(true); } }, 1L, TimeUnit.SECONDS);
/*     */       }
/*     */       
/*     */ 
/*     */ 
/*     */ 
/* 285 */       paramChannelHandlerContext.fireExceptionCaught(paramThrowable);
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */   public ServerBootstrap clone()
/*     */   {
/* 292 */     return new ServerBootstrap(this);
/*     */   }
/*     */   
/*     */   public String toString()
/*     */   {
/* 297 */     StringBuilder localStringBuilder = new StringBuilder(super.toString());
/* 298 */     localStringBuilder.setLength(localStringBuilder.length() - 1);
/* 299 */     localStringBuilder.append(", ");
/* 300 */     if (this.childGroup != null) {
/* 301 */       localStringBuilder.append("childGroup: ");
/* 302 */       localStringBuilder.append(StringUtil.simpleClassName(this.childGroup));
/* 303 */       localStringBuilder.append(", ");
/*     */     }
/* 305 */     synchronized (this.childOptions) {
/* 306 */       if (!this.childOptions.isEmpty()) {
/* 307 */         localStringBuilder.append("childOptions: ");
/* 308 */         localStringBuilder.append(this.childOptions);
/* 309 */         localStringBuilder.append(", ");
/*     */       }
/*     */     }
/* 312 */     synchronized (this.childAttrs) {
/* 313 */       if (!this.childAttrs.isEmpty()) {
/* 314 */         localStringBuilder.append("childAttrs: ");
/* 315 */         localStringBuilder.append(this.childAttrs);
/* 316 */         localStringBuilder.append(", ");
/*     */       }
/*     */     }
/* 319 */     if (this.childHandler != null) {
/* 320 */       localStringBuilder.append("childHandler: ");
/* 321 */       localStringBuilder.append(this.childHandler);
/* 322 */       localStringBuilder.append(", ");
/*     */     }
/* 324 */     if (localStringBuilder.charAt(localStringBuilder.length() - 1) == '(') {
/* 325 */       localStringBuilder.append(')');
/*     */     } else {
/* 327 */       localStringBuilder.setCharAt(localStringBuilder.length() - 2, ')');
/* 328 */       localStringBuilder.setLength(localStringBuilder.length() - 1);
/*     */     }
/*     */     
/* 331 */     return localStringBuilder.toString();
/*     */   }
/*     */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\io\netty\bootstrap\ServerBootstrap.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */
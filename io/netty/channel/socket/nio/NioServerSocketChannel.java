/*     */ package io.netty.channel.socket.nio;
/*     */ 
/*     */ import io.netty.channel.ChannelException;
/*     */ import io.netty.channel.ChannelMetadata;
/*     */ import io.netty.channel.ChannelOutboundBuffer;
/*     */ import io.netty.channel.nio.AbstractNioMessageChannel;
/*     */ import io.netty.channel.socket.DefaultServerSocketChannelConfig;
/*     */ import io.netty.channel.socket.ServerSocketChannelConfig;
/*     */ import io.netty.util.internal.logging.InternalLogger;
/*     */ import io.netty.util.internal.logging.InternalLoggerFactory;
/*     */ import java.io.IOException;
/*     */ import java.net.InetSocketAddress;
/*     */ import java.net.ServerSocket;
/*     */ import java.net.SocketAddress;
/*     */ import java.nio.channels.SocketChannel;
/*     */ import java.nio.channels.spi.SelectorProvider;
/*     */ import java.util.List;
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
/*     */ public class NioServerSocketChannel
/*     */   extends AbstractNioMessageChannel
/*     */   implements io.netty.channel.socket.ServerSocketChannel
/*     */ {
/*  44 */   private static final ChannelMetadata METADATA = new ChannelMetadata(false);
/*  45 */   private static final SelectorProvider DEFAULT_SELECTOR_PROVIDER = SelectorProvider.provider();
/*     */   
/*  47 */   private static final InternalLogger logger = InternalLoggerFactory.getInstance(NioServerSocketChannel.class);
/*     */   
/*     */ 
/*     */   private final ServerSocketChannelConfig config;
/*     */   
/*     */ 
/*     */   private static java.nio.channels.ServerSocketChannel newSocket(SelectorProvider paramSelectorProvider)
/*     */   {
/*     */     try
/*     */     {
/*  57 */       return paramSelectorProvider.openServerSocketChannel();
/*     */     } catch (IOException localIOException) {
/*  59 */       throw new ChannelException("Failed to open a server socket.", localIOException);
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public NioServerSocketChannel()
/*     */   {
/*  70 */     this(newSocket(DEFAULT_SELECTOR_PROVIDER));
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public NioServerSocketChannel(SelectorProvider paramSelectorProvider)
/*     */   {
/*  77 */     this(newSocket(paramSelectorProvider));
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public NioServerSocketChannel(java.nio.channels.ServerSocketChannel paramServerSocketChannel)
/*     */   {
/*  84 */     super(null, paramServerSocketChannel, 16);
/*  85 */     this.config = new NioServerSocketChannelConfig(this, javaChannel().socket(), null);
/*     */   }
/*     */   
/*     */   public InetSocketAddress localAddress()
/*     */   {
/*  90 */     return (InetSocketAddress)super.localAddress();
/*     */   }
/*     */   
/*     */   public ChannelMetadata metadata()
/*     */   {
/*  95 */     return METADATA;
/*     */   }
/*     */   
/*     */   public ServerSocketChannelConfig config()
/*     */   {
/* 100 */     return this.config;
/*     */   }
/*     */   
/*     */   public boolean isActive()
/*     */   {
/* 105 */     return javaChannel().socket().isBound();
/*     */   }
/*     */   
/*     */   public InetSocketAddress remoteAddress()
/*     */   {
/* 110 */     return null;
/*     */   }
/*     */   
/*     */   protected java.nio.channels.ServerSocketChannel javaChannel()
/*     */   {
/* 115 */     return (java.nio.channels.ServerSocketChannel)super.javaChannel();
/*     */   }
/*     */   
/*     */   protected SocketAddress localAddress0()
/*     */   {
/* 120 */     return javaChannel().socket().getLocalSocketAddress();
/*     */   }
/*     */   
/*     */   protected void doBind(SocketAddress paramSocketAddress) throws Exception
/*     */   {
/* 125 */     javaChannel().socket().bind(paramSocketAddress, this.config.getBacklog());
/*     */   }
/*     */   
/*     */   protected void doClose() throws Exception
/*     */   {
/* 130 */     javaChannel().close();
/*     */   }
/*     */   
/*     */   protected int doReadMessages(List<Object> paramList) throws Exception
/*     */   {
/* 135 */     SocketChannel localSocketChannel = javaChannel().accept();
/*     */     try
/*     */     {
/* 138 */       if (localSocketChannel != null) {
/* 139 */         paramList.add(new NioSocketChannel(this, localSocketChannel));
/* 140 */         return 1;
/*     */       }
/*     */     } catch (Throwable localThrowable1) {
/* 143 */       logger.warn("Failed to create a new channel from an accepted socket.", localThrowable1);
/*     */       try
/*     */       {
/* 146 */         localSocketChannel.close();
/*     */       } catch (Throwable localThrowable2) {
/* 148 */         logger.warn("Failed to close a socket.", localThrowable2);
/*     */       }
/*     */     }
/*     */     
/* 152 */     return 0;
/*     */   }
/*     */   
/*     */ 
/*     */   protected boolean doConnect(SocketAddress paramSocketAddress1, SocketAddress paramSocketAddress2)
/*     */     throws Exception
/*     */   {
/* 159 */     throw new UnsupportedOperationException();
/*     */   }
/*     */   
/*     */   protected void doFinishConnect() throws Exception
/*     */   {
/* 164 */     throw new UnsupportedOperationException();
/*     */   }
/*     */   
/*     */   protected SocketAddress remoteAddress0()
/*     */   {
/* 169 */     return null;
/*     */   }
/*     */   
/*     */   protected void doDisconnect() throws Exception
/*     */   {
/* 174 */     throw new UnsupportedOperationException();
/*     */   }
/*     */   
/*     */   protected boolean doWriteMessage(Object paramObject, ChannelOutboundBuffer paramChannelOutboundBuffer) throws Exception
/*     */   {
/* 179 */     throw new UnsupportedOperationException();
/*     */   }
/*     */   
/*     */   protected final Object filterOutboundMessage(Object paramObject) throws Exception
/*     */   {
/* 184 */     throw new UnsupportedOperationException();
/*     */   }
/*     */   
/*     */   private final class NioServerSocketChannelConfig extends DefaultServerSocketChannelConfig {
/*     */     private NioServerSocketChannelConfig(NioServerSocketChannel paramNioServerSocketChannel, ServerSocket paramServerSocket) {
/* 189 */       super(paramServerSocket);
/*     */     }
/*     */     
/*     */     protected void autoReadCleared()
/*     */     {
/* 194 */       NioServerSocketChannel.this.setReadPending(false);
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\io\netty\channel\socket\nio\NioServerSocketChannel.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */
/*     */ package io.netty.channel.socket.nio;
/*     */ 
/*     */ import io.netty.buffer.ByteBuf;
/*     */ import io.netty.channel.Channel;
/*     */ import io.netty.channel.ChannelException;
/*     */ import io.netty.channel.ChannelFuture;
/*     */ import io.netty.channel.ChannelMetadata;
/*     */ import io.netty.channel.ChannelOutboundBuffer;
/*     */ import io.netty.channel.ChannelPromise;
/*     */ import io.netty.channel.EventLoop;
/*     */ import io.netty.channel.FileRegion;
/*     */ import io.netty.channel.nio.AbstractNioByteChannel;
/*     */ import io.netty.channel.nio.NioEventLoop;
/*     */ import io.netty.channel.socket.DefaultSocketChannelConfig;
/*     */ import io.netty.channel.socket.ServerSocketChannel;
/*     */ import io.netty.channel.socket.SocketChannelConfig;
/*     */ import io.netty.util.internal.OneTimeTask;
/*     */ import java.io.IOException;
/*     */ import java.net.InetSocketAddress;
/*     */ import java.net.Socket;
/*     */ import java.net.SocketAddress;
/*     */ import java.nio.ByteBuffer;
/*     */ import java.nio.channels.SelectionKey;
/*     */ import java.nio.channels.spi.SelectorProvider;
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
/*     */ public class NioSocketChannel
/*     */   extends AbstractNioByteChannel
/*     */   implements io.netty.channel.socket.SocketChannel
/*     */ {
/*  47 */   private static final ChannelMetadata METADATA = new ChannelMetadata(false);
/*  48 */   private static final SelectorProvider DEFAULT_SELECTOR_PROVIDER = SelectorProvider.provider();
/*     */   
/*     */ 
/*     */   private final SocketChannelConfig config;
/*     */   
/*     */ 
/*     */   private static java.nio.channels.SocketChannel newSocket(SelectorProvider paramSelectorProvider)
/*     */   {
/*     */     try
/*     */     {
/*  58 */       return paramSelectorProvider.openSocketChannel();
/*     */     } catch (IOException localIOException) {
/*  60 */       throw new ChannelException("Failed to open a socket.", localIOException);
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public NioSocketChannel()
/*     */   {
/*  70 */     this(newSocket(DEFAULT_SELECTOR_PROVIDER));
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public NioSocketChannel(SelectorProvider paramSelectorProvider)
/*     */   {
/*  77 */     this(newSocket(paramSelectorProvider));
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public NioSocketChannel(java.nio.channels.SocketChannel paramSocketChannel)
/*     */   {
/*  84 */     this(null, paramSocketChannel);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public NioSocketChannel(Channel paramChannel, java.nio.channels.SocketChannel paramSocketChannel)
/*     */   {
/*  94 */     super(paramChannel, paramSocketChannel);
/*  95 */     this.config = new NioSocketChannelConfig(this, paramSocketChannel.socket(), null);
/*     */   }
/*     */   
/*     */   public ServerSocketChannel parent()
/*     */   {
/* 100 */     return (ServerSocketChannel)super.parent();
/*     */   }
/*     */   
/*     */   public ChannelMetadata metadata()
/*     */   {
/* 105 */     return METADATA;
/*     */   }
/*     */   
/*     */   public SocketChannelConfig config()
/*     */   {
/* 110 */     return this.config;
/*     */   }
/*     */   
/*     */   protected java.nio.channels.SocketChannel javaChannel()
/*     */   {
/* 115 */     return (java.nio.channels.SocketChannel)super.javaChannel();
/*     */   }
/*     */   
/*     */   public boolean isActive()
/*     */   {
/* 120 */     java.nio.channels.SocketChannel localSocketChannel = javaChannel();
/* 121 */     return (localSocketChannel.isOpen()) && (localSocketChannel.isConnected());
/*     */   }
/*     */   
/*     */   public boolean isInputShutdown()
/*     */   {
/* 126 */     return super.isInputShutdown();
/*     */   }
/*     */   
/*     */   public InetSocketAddress localAddress()
/*     */   {
/* 131 */     return (InetSocketAddress)super.localAddress();
/*     */   }
/*     */   
/*     */   public InetSocketAddress remoteAddress()
/*     */   {
/* 136 */     return (InetSocketAddress)super.remoteAddress();
/*     */   }
/*     */   
/*     */   public boolean isOutputShutdown()
/*     */   {
/* 141 */     return (javaChannel().socket().isOutputShutdown()) || (!isActive());
/*     */   }
/*     */   
/*     */   public ChannelFuture shutdownOutput()
/*     */   {
/* 146 */     return shutdownOutput(newPromise());
/*     */   }
/*     */   
/*     */   public ChannelFuture shutdownOutput(final ChannelPromise paramChannelPromise)
/*     */   {
/* 151 */     NioEventLoop localNioEventLoop = eventLoop();
/* 152 */     if (localNioEventLoop.inEventLoop()) {
/*     */       try {
/* 154 */         javaChannel().socket().shutdownOutput();
/* 155 */         paramChannelPromise.setSuccess();
/*     */       } catch (Throwable localThrowable) {
/* 157 */         paramChannelPromise.setFailure(localThrowable);
/*     */       }
/*     */     } else {
/* 160 */       localNioEventLoop.execute(new OneTimeTask()
/*     */       {
/*     */         public void run() {
/* 163 */           NioSocketChannel.this.shutdownOutput(paramChannelPromise);
/*     */         }
/*     */       });
/*     */     }
/* 167 */     return paramChannelPromise;
/*     */   }
/*     */   
/*     */   protected SocketAddress localAddress0()
/*     */   {
/* 172 */     return javaChannel().socket().getLocalSocketAddress();
/*     */   }
/*     */   
/*     */   protected SocketAddress remoteAddress0()
/*     */   {
/* 177 */     return javaChannel().socket().getRemoteSocketAddress();
/*     */   }
/*     */   
/*     */   protected void doBind(SocketAddress paramSocketAddress) throws Exception
/*     */   {
/* 182 */     javaChannel().socket().bind(paramSocketAddress);
/*     */   }
/*     */   
/*     */   protected boolean doConnect(SocketAddress paramSocketAddress1, SocketAddress paramSocketAddress2) throws Exception
/*     */   {
/* 187 */     if (paramSocketAddress2 != null) {
/* 188 */       javaChannel().socket().bind(paramSocketAddress2);
/*     */     }
/*     */     
/* 191 */     int i = 0;
/*     */     try {
/* 193 */       boolean bool1 = javaChannel().connect(paramSocketAddress1);
/* 194 */       if (!bool1) {
/* 195 */         selectionKey().interestOps(8);
/*     */       }
/* 197 */       i = 1;
/* 198 */       return bool1;
/*     */     } finally {
/* 200 */       if (i == 0) {
/* 201 */         doClose();
/*     */       }
/*     */     }
/*     */   }
/*     */   
/*     */   protected void doFinishConnect() throws Exception
/*     */   {
/* 208 */     if (!javaChannel().finishConnect()) {
/* 209 */       throw new Error();
/*     */     }
/*     */   }
/*     */   
/*     */   protected void doDisconnect() throws Exception
/*     */   {
/* 215 */     doClose();
/*     */   }
/*     */   
/*     */   protected void doClose() throws Exception
/*     */   {
/* 220 */     javaChannel().close();
/*     */   }
/*     */   
/*     */   protected int doReadBytes(ByteBuf paramByteBuf) throws Exception
/*     */   {
/* 225 */     return paramByteBuf.writeBytes(javaChannel(), paramByteBuf.writableBytes());
/*     */   }
/*     */   
/*     */   protected int doWriteBytes(ByteBuf paramByteBuf) throws Exception
/*     */   {
/* 230 */     int i = paramByteBuf.readableBytes();
/* 231 */     return paramByteBuf.readBytes(javaChannel(), i);
/*     */   }
/*     */   
/*     */   protected long doWriteFileRegion(FileRegion paramFileRegion) throws Exception
/*     */   {
/* 236 */     long l = paramFileRegion.transfered();
/* 237 */     return paramFileRegion.transferTo(javaChannel(), l);
/*     */   }
/*     */   
/*     */   protected void doWrite(ChannelOutboundBuffer paramChannelOutboundBuffer) throws Exception
/*     */   {
/*     */     for (;;) {
/* 243 */       int i = paramChannelOutboundBuffer.size();
/* 244 */       if (i == 0)
/*     */       {
/* 246 */         clearOpWrite();
/* 247 */         break;
/*     */       }
/* 249 */       long l1 = 0L;
/* 250 */       int j = 0;
/* 251 */       boolean bool = false;
/*     */       
/*     */ 
/* 254 */       ByteBuffer[] arrayOfByteBuffer = paramChannelOutboundBuffer.nioBuffers();
/* 255 */       int k = paramChannelOutboundBuffer.nioBufferCount();
/* 256 */       long l2 = paramChannelOutboundBuffer.nioBufferSize();
/* 257 */       java.nio.channels.SocketChannel localSocketChannel = javaChannel();
/*     */       
/*     */       int m;
/*     */       
/* 261 */       switch (k)
/*     */       {
/*     */       case 0: 
/* 264 */         super.doWrite(paramChannelOutboundBuffer);
/* 265 */         return;
/*     */       
/*     */       case 1: 
/* 268 */         ByteBuffer localByteBuffer = arrayOfByteBuffer[0];
/* 269 */         for (m = config().getWriteSpinCount() - 1; m >= 0; m--) {
/* 270 */           int n = localSocketChannel.write(localByteBuffer);
/* 271 */           if (n == 0) {
/* 272 */             bool = true;
/* 273 */             break;
/*     */           }
/* 275 */           l2 -= n;
/* 276 */           l1 += n;
/* 277 */           if (l2 == 0L) {
/* 278 */             j = 1;
/* 279 */             break;
/*     */           }
/*     */         }
/* 282 */         break;
/*     */       default: 
/* 284 */         for (m = config().getWriteSpinCount() - 1; m >= 0; m--) {
/* 285 */           long l3 = localSocketChannel.write(arrayOfByteBuffer, 0, k);
/* 286 */           if (l3 == 0L) {
/* 287 */             bool = true;
/* 288 */             break;
/*     */           }
/* 290 */           l2 -= l3;
/* 291 */           l1 += l3;
/* 292 */           if (l2 == 0L) {
/* 293 */             j = 1;
/* 294 */             break;
/*     */           }
/*     */         }
/*     */       }
/*     */       
/*     */       
/*     */ 
/* 301 */       paramChannelOutboundBuffer.removeBytes(l1);
/*     */       
/* 303 */       if (j == 0)
/*     */       {
/* 305 */         incompleteWrite(bool);
/* 306 */         break;
/*     */       }
/*     */     }
/*     */   }
/*     */   
/*     */   private final class NioSocketChannelConfig extends DefaultSocketChannelConfig {
/*     */     private NioSocketChannelConfig(NioSocketChannel paramNioSocketChannel, Socket paramSocket) {
/* 313 */       super(paramSocket);
/*     */     }
/*     */     
/*     */     protected void autoReadCleared()
/*     */     {
/* 318 */       NioSocketChannel.this.setReadPending(false);
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\io\netty\channel\socket\nio\NioSocketChannel.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */
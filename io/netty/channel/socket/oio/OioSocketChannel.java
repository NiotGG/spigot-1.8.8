/*     */ package io.netty.channel.socket.oio;
/*     */ 
/*     */ import io.netty.buffer.ByteBuf;
/*     */ import io.netty.channel.Channel;
/*     */ import io.netty.channel.ChannelException;
/*     */ import io.netty.channel.ChannelFuture;
/*     */ import io.netty.channel.ChannelPromise;
/*     */ import io.netty.channel.ConnectTimeoutException;
/*     */ import io.netty.channel.EventLoop;
/*     */ import io.netty.channel.oio.OioByteStreamChannel;
/*     */ import io.netty.channel.socket.ServerSocketChannel;
/*     */ import io.netty.channel.socket.SocketChannel;
/*     */ import io.netty.util.internal.logging.InternalLogger;
/*     */ import io.netty.util.internal.logging.InternalLoggerFactory;
/*     */ import java.io.IOException;
/*     */ import java.net.InetSocketAddress;
/*     */ import java.net.Socket;
/*     */ import java.net.SocketAddress;
/*     */ import java.net.SocketTimeoutException;
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
/*     */ public class OioSocketChannel
/*     */   extends OioByteStreamChannel
/*     */   implements SocketChannel
/*     */ {
/*  43 */   private static final InternalLogger logger = InternalLoggerFactory.getInstance(OioSocketChannel.class);
/*     */   
/*     */ 
/*     */   private final Socket socket;
/*     */   
/*     */   private final OioSocketChannelConfig config;
/*     */   
/*     */ 
/*     */   public OioSocketChannel()
/*     */   {
/*  53 */     this(new Socket());
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public OioSocketChannel(Socket paramSocket)
/*     */   {
/*  62 */     this(null, paramSocket);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public OioSocketChannel(Channel paramChannel, Socket paramSocket)
/*     */   {
/*  73 */     super(paramChannel);
/*  74 */     this.socket = paramSocket;
/*  75 */     this.config = new DefaultOioSocketChannelConfig(this, paramSocket);
/*     */     
/*  77 */     int i = 0;
/*     */     try {
/*  79 */       if (paramSocket.isConnected()) {
/*  80 */         activate(paramSocket.getInputStream(), paramSocket.getOutputStream());
/*     */       }
/*  82 */       paramSocket.setSoTimeout(1000);
/*  83 */       i = 1; return;
/*     */     } catch (Exception localException) {
/*  85 */       throw new ChannelException("failed to initialize a socket", localException);
/*     */     } finally {
/*  87 */       if (i == 0) {
/*     */         try {
/*  89 */           paramSocket.close();
/*     */         } catch (IOException localIOException2) {
/*  91 */           logger.warn("Failed to close a socket.", localIOException2);
/*     */         }
/*     */       }
/*     */     }
/*     */   }
/*     */   
/*     */   public ServerSocketChannel parent()
/*     */   {
/*  99 */     return (ServerSocketChannel)super.parent();
/*     */   }
/*     */   
/*     */   public OioSocketChannelConfig config()
/*     */   {
/* 104 */     return this.config;
/*     */   }
/*     */   
/*     */   public boolean isOpen()
/*     */   {
/* 109 */     return !this.socket.isClosed();
/*     */   }
/*     */   
/*     */   public boolean isActive()
/*     */   {
/* 114 */     return (!this.socket.isClosed()) && (this.socket.isConnected());
/*     */   }
/*     */   
/*     */   public boolean isInputShutdown()
/*     */   {
/* 119 */     return super.isInputShutdown();
/*     */   }
/*     */   
/*     */   public boolean isOutputShutdown()
/*     */   {
/* 124 */     return (this.socket.isOutputShutdown()) || (!isActive());
/*     */   }
/*     */   
/*     */   public ChannelFuture shutdownOutput()
/*     */   {
/* 129 */     return shutdownOutput(newPromise());
/*     */   }
/*     */   
/*     */   protected int doReadBytes(ByteBuf paramByteBuf) throws Exception
/*     */   {
/* 134 */     if (this.socket.isClosed()) {
/* 135 */       return -1;
/*     */     }
/*     */     try {
/* 138 */       return super.doReadBytes(paramByteBuf);
/*     */     } catch (SocketTimeoutException localSocketTimeoutException) {}
/* 140 */     return 0;
/*     */   }
/*     */   
/*     */ 
/*     */   public ChannelFuture shutdownOutput(final ChannelPromise paramChannelPromise)
/*     */   {
/* 146 */     EventLoop localEventLoop = eventLoop();
/* 147 */     if (localEventLoop.inEventLoop()) {
/*     */       try {
/* 149 */         this.socket.shutdownOutput();
/* 150 */         paramChannelPromise.setSuccess();
/*     */       } catch (Throwable localThrowable) {
/* 152 */         paramChannelPromise.setFailure(localThrowable);
/*     */       }
/*     */     } else {
/* 155 */       localEventLoop.execute(new Runnable()
/*     */       {
/*     */         public void run() {
/* 158 */           OioSocketChannel.this.shutdownOutput(paramChannelPromise);
/*     */         }
/*     */       });
/*     */     }
/* 162 */     return paramChannelPromise;
/*     */   }
/*     */   
/*     */   public InetSocketAddress localAddress()
/*     */   {
/* 167 */     return (InetSocketAddress)super.localAddress();
/*     */   }
/*     */   
/*     */   public InetSocketAddress remoteAddress()
/*     */   {
/* 172 */     return (InetSocketAddress)super.remoteAddress();
/*     */   }
/*     */   
/*     */   protected SocketAddress localAddress0()
/*     */   {
/* 177 */     return this.socket.getLocalSocketAddress();
/*     */   }
/*     */   
/*     */   protected SocketAddress remoteAddress0()
/*     */   {
/* 182 */     return this.socket.getRemoteSocketAddress();
/*     */   }
/*     */   
/*     */   protected void doBind(SocketAddress paramSocketAddress) throws Exception
/*     */   {
/* 187 */     this.socket.bind(paramSocketAddress);
/*     */   }
/*     */   
/*     */   protected void doConnect(SocketAddress paramSocketAddress1, SocketAddress paramSocketAddress2)
/*     */     throws Exception
/*     */   {
/* 193 */     if (paramSocketAddress2 != null) {
/* 194 */       this.socket.bind(paramSocketAddress2);
/*     */     }
/*     */     
/* 197 */     int i = 0;
/*     */     try {
/* 199 */       this.socket.connect(paramSocketAddress1, config().getConnectTimeoutMillis());
/* 200 */       activate(this.socket.getInputStream(), this.socket.getOutputStream());
/* 201 */       i = 1;
/*     */     } catch (SocketTimeoutException localSocketTimeoutException) {
/* 203 */       ConnectTimeoutException localConnectTimeoutException = new ConnectTimeoutException("connection timed out: " + paramSocketAddress1);
/* 204 */       localConnectTimeoutException.setStackTrace(localSocketTimeoutException.getStackTrace());
/* 205 */       throw localConnectTimeoutException;
/*     */     } finally {
/* 207 */       if (i == 0) {
/* 208 */         doClose();
/*     */       }
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
/* 220 */     this.socket.close();
/*     */   }
/*     */   
/*     */   protected boolean checkInputShutdown()
/*     */   {
/* 225 */     if (isInputShutdown()) {
/*     */       try {
/* 227 */         Thread.sleep(config().getSoTimeout());
/*     */       }
/*     */       catch (Throwable localThrowable) {}
/*     */       
/* 231 */       return true;
/*     */     }
/* 233 */     return false;
/*     */   }
/*     */   
/*     */   protected void setReadPending(boolean paramBoolean)
/*     */   {
/* 238 */     super.setReadPending(paramBoolean);
/*     */   }
/*     */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\io\netty\channel\socket\oio\OioSocketChannel.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */
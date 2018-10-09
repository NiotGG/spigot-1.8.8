/*     */ package io.netty.channel.sctp.nio;
/*     */ 
/*     */ import com.sun.nio.sctp.SctpChannel;
/*     */ import io.netty.channel.ChannelException;
/*     */ import io.netty.channel.ChannelFuture;
/*     */ import io.netty.channel.ChannelMetadata;
/*     */ import io.netty.channel.ChannelOutboundBuffer;
/*     */ import io.netty.channel.ChannelPromise;
/*     */ import io.netty.channel.nio.AbstractNioMessageChannel;
/*     */ import io.netty.channel.nio.NioEventLoop;
/*     */ import io.netty.channel.sctp.DefaultSctpServerChannelConfig;
/*     */ import io.netty.channel.sctp.SctpServerChannelConfig;
/*     */ import java.io.IOException;
/*     */ import java.net.InetAddress;
/*     */ import java.net.InetSocketAddress;
/*     */ import java.net.SocketAddress;
/*     */ import java.util.Collections;
/*     */ import java.util.Iterator;
/*     */ import java.util.LinkedHashSet;
/*     */ import java.util.List;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class NioSctpServerChannel
/*     */   extends AbstractNioMessageChannel
/*     */   implements io.netty.channel.sctp.SctpServerChannel
/*     */ {
/*  49 */   private static final ChannelMetadata METADATA = new ChannelMetadata(false);
/*     */   private final SctpServerChannelConfig config;
/*     */   
/*     */   private static com.sun.nio.sctp.SctpServerChannel newSocket() {
/*  53 */     try { return com.sun.nio.sctp.SctpServerChannel.open();
/*     */     } catch (IOException localIOException) {
/*  55 */       throw new ChannelException("Failed to open a server socket.", localIOException);
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public NioSctpServerChannel()
/*     */   {
/*  66 */     super(null, newSocket(), 16);
/*  67 */     this.config = new NioSctpServerChannelConfig(this, javaChannel(), null);
/*     */   }
/*     */   
/*     */   public ChannelMetadata metadata()
/*     */   {
/*  72 */     return METADATA;
/*     */   }
/*     */   
/*     */   public Set<InetSocketAddress> allLocalAddresses()
/*     */   {
/*     */     try {
/*  78 */       Set localSet = javaChannel().getAllLocalAddresses();
/*  79 */       LinkedHashSet localLinkedHashSet = new LinkedHashSet(localSet.size());
/*  80 */       for (SocketAddress localSocketAddress : localSet) {
/*  81 */         localLinkedHashSet.add((InetSocketAddress)localSocketAddress);
/*     */       }
/*  83 */       return localLinkedHashSet;
/*     */     } catch (Throwable localThrowable) {}
/*  85 */     return Collections.emptySet();
/*     */   }
/*     */   
/*     */ 
/*     */   public SctpServerChannelConfig config()
/*     */   {
/*  91 */     return this.config;
/*     */   }
/*     */   
/*     */   public boolean isActive()
/*     */   {
/*  96 */     return (isOpen()) && (!allLocalAddresses().isEmpty());
/*     */   }
/*     */   
/*     */   public InetSocketAddress remoteAddress()
/*     */   {
/* 101 */     return null;
/*     */   }
/*     */   
/*     */   public InetSocketAddress localAddress()
/*     */   {
/* 106 */     return (InetSocketAddress)super.localAddress();
/*     */   }
/*     */   
/*     */   protected com.sun.nio.sctp.SctpServerChannel javaChannel()
/*     */   {
/* 111 */     return (com.sun.nio.sctp.SctpServerChannel)super.javaChannel();
/*     */   }
/*     */   
/*     */   protected SocketAddress localAddress0()
/*     */   {
/*     */     try {
/* 117 */       Iterator localIterator = javaChannel().getAllLocalAddresses().iterator();
/* 118 */       if (localIterator.hasNext()) {
/* 119 */         return (SocketAddress)localIterator.next();
/*     */       }
/*     */     }
/*     */     catch (IOException localIOException) {}
/*     */     
/* 124 */     return null;
/*     */   }
/*     */   
/*     */   protected void doBind(SocketAddress paramSocketAddress) throws Exception
/*     */   {
/* 129 */     javaChannel().bind(paramSocketAddress, this.config.getBacklog());
/*     */   }
/*     */   
/*     */   protected void doClose() throws Exception
/*     */   {
/* 134 */     javaChannel().close();
/*     */   }
/*     */   
/*     */   protected int doReadMessages(List<Object> paramList) throws Exception
/*     */   {
/* 139 */     SctpChannel localSctpChannel = javaChannel().accept();
/* 140 */     if (localSctpChannel == null) {
/* 141 */       return 0;
/*     */     }
/* 143 */     paramList.add(new NioSctpChannel(this, localSctpChannel));
/* 144 */     return 1;
/*     */   }
/*     */   
/*     */   public ChannelFuture bindAddress(InetAddress paramInetAddress)
/*     */   {
/* 149 */     return bindAddress(paramInetAddress, newPromise());
/*     */   }
/*     */   
/*     */   public ChannelFuture bindAddress(final InetAddress paramInetAddress, final ChannelPromise paramChannelPromise)
/*     */   {
/* 154 */     if (eventLoop().inEventLoop()) {
/*     */       try {
/* 156 */         javaChannel().bindAddress(paramInetAddress);
/* 157 */         paramChannelPromise.setSuccess();
/*     */       } catch (Throwable localThrowable) {
/* 159 */         paramChannelPromise.setFailure(localThrowable);
/*     */       }
/*     */     } else {
/* 162 */       eventLoop().execute(new Runnable()
/*     */       {
/*     */         public void run() {
/* 165 */           NioSctpServerChannel.this.bindAddress(paramInetAddress, paramChannelPromise);
/*     */         }
/*     */       });
/*     */     }
/* 169 */     return paramChannelPromise;
/*     */   }
/*     */   
/*     */   public ChannelFuture unbindAddress(InetAddress paramInetAddress)
/*     */   {
/* 174 */     return unbindAddress(paramInetAddress, newPromise());
/*     */   }
/*     */   
/*     */   public ChannelFuture unbindAddress(final InetAddress paramInetAddress, final ChannelPromise paramChannelPromise)
/*     */   {
/* 179 */     if (eventLoop().inEventLoop()) {
/*     */       try {
/* 181 */         javaChannel().unbindAddress(paramInetAddress);
/* 182 */         paramChannelPromise.setSuccess();
/*     */       } catch (Throwable localThrowable) {
/* 184 */         paramChannelPromise.setFailure(localThrowable);
/*     */       }
/*     */     } else {
/* 187 */       eventLoop().execute(new Runnable()
/*     */       {
/*     */         public void run() {
/* 190 */           NioSctpServerChannel.this.unbindAddress(paramInetAddress, paramChannelPromise);
/*     */         }
/*     */       });
/*     */     }
/* 194 */     return paramChannelPromise;
/*     */   }
/*     */   
/*     */ 
/*     */   protected boolean doConnect(SocketAddress paramSocketAddress1, SocketAddress paramSocketAddress2)
/*     */     throws Exception
/*     */   {
/* 201 */     throw new UnsupportedOperationException();
/*     */   }
/*     */   
/*     */   protected void doFinishConnect() throws Exception
/*     */   {
/* 206 */     throw new UnsupportedOperationException();
/*     */   }
/*     */   
/*     */   protected SocketAddress remoteAddress0()
/*     */   {
/* 211 */     return null;
/*     */   }
/*     */   
/*     */   protected void doDisconnect() throws Exception
/*     */   {
/* 216 */     throw new UnsupportedOperationException();
/*     */   }
/*     */   
/*     */   protected boolean doWriteMessage(Object paramObject, ChannelOutboundBuffer paramChannelOutboundBuffer) throws Exception
/*     */   {
/* 221 */     throw new UnsupportedOperationException();
/*     */   }
/*     */   
/*     */   protected Object filterOutboundMessage(Object paramObject) throws Exception
/*     */   {
/* 226 */     throw new UnsupportedOperationException();
/*     */   }
/*     */   
/*     */   private final class NioSctpServerChannelConfig extends DefaultSctpServerChannelConfig {
/*     */     private NioSctpServerChannelConfig(NioSctpServerChannel paramNioSctpServerChannel, com.sun.nio.sctp.SctpServerChannel paramSctpServerChannel) {
/* 231 */       super(paramSctpServerChannel);
/*     */     }
/*     */     
/*     */     protected void autoReadCleared()
/*     */     {
/* 236 */       NioSctpServerChannel.this.setReadPending(false);
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\io\netty\channel\sctp\nio\NioSctpServerChannel.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */
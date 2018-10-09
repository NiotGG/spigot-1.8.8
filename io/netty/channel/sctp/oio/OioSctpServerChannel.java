/*     */ package io.netty.channel.sctp.oio;
/*     */ 
/*     */ import com.sun.nio.sctp.SctpChannel;
/*     */ import io.netty.channel.ChannelException;
/*     */ import io.netty.channel.ChannelFuture;
/*     */ import io.netty.channel.ChannelMetadata;
/*     */ import io.netty.channel.ChannelOutboundBuffer;
/*     */ import io.netty.channel.ChannelPromise;
/*     */ import io.netty.channel.EventLoop;
/*     */ import io.netty.channel.oio.AbstractOioMessageChannel;
/*     */ import io.netty.channel.sctp.DefaultSctpServerChannelConfig;
/*     */ import io.netty.channel.sctp.SctpServerChannelConfig;
/*     */ import io.netty.util.internal.logging.InternalLogger;
/*     */ import io.netty.util.internal.logging.InternalLoggerFactory;
/*     */ import java.io.IOException;
/*     */ import java.net.InetAddress;
/*     */ import java.net.InetSocketAddress;
/*     */ import java.net.SocketAddress;
/*     */ import java.nio.channels.SelectionKey;
/*     */ import java.nio.channels.Selector;
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
/*     */ public class OioSctpServerChannel
/*     */   extends AbstractOioMessageChannel
/*     */   implements io.netty.channel.sctp.SctpServerChannel
/*     */ {
/*  53 */   private static final InternalLogger logger = InternalLoggerFactory.getInstance(OioSctpServerChannel.class);
/*     */   
/*     */ 
/*  56 */   private static final ChannelMetadata METADATA = new ChannelMetadata(false);
/*     */   private final com.sun.nio.sctp.SctpServerChannel sch;
/*     */   
/*     */   private static com.sun.nio.sctp.SctpServerChannel newServerSocket() {
/*  60 */     try { return com.sun.nio.sctp.SctpServerChannel.open();
/*     */     } catch (IOException localIOException) {
/*  62 */       throw new ChannelException("failed to create a sctp server channel", localIOException);
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   private final SctpServerChannelConfig config;
/*     */   
/*     */   private final Selector selector;
/*     */   
/*     */   public OioSctpServerChannel()
/*     */   {
/*  74 */     this(newServerSocket());
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public OioSctpServerChannel(com.sun.nio.sctp.SctpServerChannel paramSctpServerChannel)
/*     */   {
/*  83 */     super(null);
/*  84 */     if (paramSctpServerChannel == null) {
/*  85 */       throw new NullPointerException("sctp server channel");
/*     */     }
/*     */     
/*  88 */     this.sch = paramSctpServerChannel;
/*  89 */     int i = 0;
/*     */     try {
/*  91 */       paramSctpServerChannel.configureBlocking(false);
/*  92 */       this.selector = Selector.open();
/*  93 */       paramSctpServerChannel.register(this.selector, 16);
/*  94 */       this.config = new OioSctpServerChannelConfig(this, paramSctpServerChannel, null);
/*  95 */       i = 1; return;
/*     */     } catch (Exception localException) {
/*  97 */       throw new ChannelException("failed to initialize a sctp server channel", localException);
/*     */     } finally {
/*  99 */       if (i == 0) {
/*     */         try {
/* 101 */           paramSctpServerChannel.close();
/*     */         } catch (IOException localIOException2) {
/* 103 */           logger.warn("Failed to close a sctp server channel.", localIOException2);
/*     */         }
/*     */       }
/*     */     }
/*     */   }
/*     */   
/*     */   public ChannelMetadata metadata()
/*     */   {
/* 111 */     return METADATA;
/*     */   }
/*     */   
/*     */   public SctpServerChannelConfig config()
/*     */   {
/* 116 */     return this.config;
/*     */   }
/*     */   
/*     */   public InetSocketAddress remoteAddress()
/*     */   {
/* 121 */     return null;
/*     */   }
/*     */   
/*     */   public InetSocketAddress localAddress()
/*     */   {
/* 126 */     return (InetSocketAddress)super.localAddress();
/*     */   }
/*     */   
/*     */   public boolean isOpen()
/*     */   {
/* 131 */     return this.sch.isOpen();
/*     */   }
/*     */   
/*     */   protected SocketAddress localAddress0()
/*     */   {
/*     */     try {
/* 137 */       Iterator localIterator = this.sch.getAllLocalAddresses().iterator();
/* 138 */       if (localIterator.hasNext()) {
/* 139 */         return (SocketAddress)localIterator.next();
/*     */       }
/*     */     }
/*     */     catch (IOException localIOException) {}
/*     */     
/* 144 */     return null;
/*     */   }
/*     */   
/*     */   public Set<InetSocketAddress> allLocalAddresses()
/*     */   {
/*     */     try {
/* 150 */       Set localSet = this.sch.getAllLocalAddresses();
/* 151 */       LinkedHashSet localLinkedHashSet = new LinkedHashSet(localSet.size());
/* 152 */       for (SocketAddress localSocketAddress : localSet) {
/* 153 */         localLinkedHashSet.add((InetSocketAddress)localSocketAddress);
/*     */       }
/* 155 */       return localLinkedHashSet;
/*     */     } catch (Throwable localThrowable) {}
/* 157 */     return Collections.emptySet();
/*     */   }
/*     */   
/*     */ 
/*     */   public boolean isActive()
/*     */   {
/* 163 */     return (isOpen()) && (localAddress0() != null);
/*     */   }
/*     */   
/*     */   protected void doBind(SocketAddress paramSocketAddress) throws Exception
/*     */   {
/* 168 */     this.sch.bind(paramSocketAddress, this.config.getBacklog());
/*     */   }
/*     */   
/*     */   protected void doClose() throws Exception
/*     */   {
/*     */     try {
/* 174 */       this.selector.close();
/*     */     } catch (IOException localIOException) {
/* 176 */       logger.warn("Failed to close a selector.", localIOException);
/*     */     }
/* 178 */     this.sch.close();
/*     */   }
/*     */   
/*     */   protected int doReadMessages(List<Object> paramList) throws Exception
/*     */   {
/* 183 */     if (!isActive()) {
/* 184 */       return -1;
/*     */     }
/*     */     
/* 187 */     SctpChannel localSctpChannel = null;
/* 188 */     int i = 0;
/*     */     try {
/* 190 */       int j = this.selector.select(1000L);
/* 191 */       if (j > 0) {
/* 192 */         Iterator localIterator = this.selector.selectedKeys().iterator();
/*     */         for (;;) {
/* 194 */           SelectionKey localSelectionKey = (SelectionKey)localIterator.next();
/* 195 */           localIterator.remove();
/* 196 */           if (localSelectionKey.isAcceptable()) {
/* 197 */             localSctpChannel = this.sch.accept();
/* 198 */             if (localSctpChannel != null) {
/* 199 */               paramList.add(new OioSctpChannel(this, localSctpChannel));
/* 200 */               i++;
/*     */             }
/*     */           }
/* 203 */           if (!localIterator.hasNext()) {
/* 204 */             return i;
/*     */           }
/*     */         }
/*     */       }
/*     */     } catch (Throwable localThrowable1) {
/* 209 */       logger.warn("Failed to create a new channel from an accepted sctp channel.", localThrowable1);
/* 210 */       if (localSctpChannel != null) {
/*     */         try {
/* 212 */           localSctpChannel.close();
/*     */         } catch (Throwable localThrowable2) {
/* 214 */           logger.warn("Failed to close a sctp channel.", localThrowable2);
/*     */         }
/*     */       }
/*     */     }
/*     */     
/* 219 */     return i;
/*     */   }
/*     */   
/*     */   public ChannelFuture bindAddress(InetAddress paramInetAddress)
/*     */   {
/* 224 */     return bindAddress(paramInetAddress, newPromise());
/*     */   }
/*     */   
/*     */   public ChannelFuture bindAddress(final InetAddress paramInetAddress, final ChannelPromise paramChannelPromise)
/*     */   {
/* 229 */     if (eventLoop().inEventLoop()) {
/*     */       try {
/* 231 */         this.sch.bindAddress(paramInetAddress);
/* 232 */         paramChannelPromise.setSuccess();
/*     */       } catch (Throwable localThrowable) {
/* 234 */         paramChannelPromise.setFailure(localThrowable);
/*     */       }
/*     */     } else {
/* 237 */       eventLoop().execute(new Runnable()
/*     */       {
/*     */         public void run() {
/* 240 */           OioSctpServerChannel.this.bindAddress(paramInetAddress, paramChannelPromise);
/*     */         }
/*     */       });
/*     */     }
/* 244 */     return paramChannelPromise;
/*     */   }
/*     */   
/*     */   public ChannelFuture unbindAddress(InetAddress paramInetAddress)
/*     */   {
/* 249 */     return unbindAddress(paramInetAddress, newPromise());
/*     */   }
/*     */   
/*     */   public ChannelFuture unbindAddress(final InetAddress paramInetAddress, final ChannelPromise paramChannelPromise)
/*     */   {
/* 254 */     if (eventLoop().inEventLoop()) {
/*     */       try {
/* 256 */         this.sch.unbindAddress(paramInetAddress);
/* 257 */         paramChannelPromise.setSuccess();
/*     */       } catch (Throwable localThrowable) {
/* 259 */         paramChannelPromise.setFailure(localThrowable);
/*     */       }
/*     */     } else {
/* 262 */       eventLoop().execute(new Runnable()
/*     */       {
/*     */         public void run() {
/* 265 */           OioSctpServerChannel.this.unbindAddress(paramInetAddress, paramChannelPromise);
/*     */         }
/*     */       });
/*     */     }
/* 269 */     return paramChannelPromise;
/*     */   }
/*     */   
/*     */   protected void doConnect(SocketAddress paramSocketAddress1, SocketAddress paramSocketAddress2)
/*     */     throws Exception
/*     */   {
/* 275 */     throw new UnsupportedOperationException();
/*     */   }
/*     */   
/*     */   protected SocketAddress remoteAddress0()
/*     */   {
/* 280 */     return null;
/*     */   }
/*     */   
/*     */   protected void doDisconnect() throws Exception
/*     */   {
/* 285 */     throw new UnsupportedOperationException();
/*     */   }
/*     */   
/*     */   protected void doWrite(ChannelOutboundBuffer paramChannelOutboundBuffer) throws Exception
/*     */   {
/* 290 */     throw new UnsupportedOperationException();
/*     */   }
/*     */   
/*     */   protected Object filterOutboundMessage(Object paramObject) throws Exception
/*     */   {
/* 295 */     throw new UnsupportedOperationException();
/*     */   }
/*     */   
/*     */   private final class OioSctpServerChannelConfig extends DefaultSctpServerChannelConfig {
/*     */     private OioSctpServerChannelConfig(OioSctpServerChannel paramOioSctpServerChannel, com.sun.nio.sctp.SctpServerChannel paramSctpServerChannel) {
/* 300 */       super(paramSctpServerChannel);
/*     */     }
/*     */     
/*     */     protected void autoReadCleared()
/*     */     {
/* 305 */       OioSctpServerChannel.this.setReadPending(false);
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\io\netty\channel\sctp\oio\OioSctpServerChannel.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */
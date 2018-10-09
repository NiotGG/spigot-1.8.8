/*     */ package io.netty.channel.udt.nio;
/*     */ 
/*     */ import com.barchart.udt.TypeUDT;
/*     */ import com.barchart.udt.nio.NioServerSocketUDT;
/*     */ import com.barchart.udt.nio.ServerSocketChannelUDT;
/*     */ import io.netty.channel.ChannelException;
/*     */ import io.netty.channel.ChannelOutboundBuffer;
/*     */ import io.netty.channel.nio.AbstractNioMessageChannel;
/*     */ import io.netty.channel.udt.DefaultUdtServerChannelConfig;
/*     */ import io.netty.channel.udt.UdtServerChannel;
/*     */ import io.netty.channel.udt.UdtServerChannelConfig;
/*     */ import io.netty.util.internal.logging.InternalLogger;
/*     */ import io.netty.util.internal.logging.InternalLoggerFactory;
/*     */ import java.net.InetSocketAddress;
/*     */ import java.net.SocketAddress;
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
/*     */ public abstract class NioUdtAcceptorChannel
/*     */   extends AbstractNioMessageChannel
/*     */   implements UdtServerChannel
/*     */ {
/*  39 */   protected static final InternalLogger logger = InternalLoggerFactory.getInstance(NioUdtAcceptorChannel.class);
/*     */   
/*     */   private final UdtServerChannelConfig config;
/*     */   
/*     */   protected NioUdtAcceptorChannel(ServerSocketChannelUDT paramServerSocketChannelUDT)
/*     */   {
/*  45 */     super(null, paramServerSocketChannelUDT, 16);
/*     */     try {
/*  47 */       paramServerSocketChannelUDT.configureBlocking(false);
/*  48 */       this.config = new DefaultUdtServerChannelConfig(this, paramServerSocketChannelUDT, true);
/*     */     } catch (Exception localException1) {
/*     */       try {
/*  51 */         paramServerSocketChannelUDT.close();
/*     */       } catch (Exception localException2) {
/*  53 */         if (logger.isWarnEnabled()) {
/*  54 */           logger.warn("Failed to close channel.", localException2);
/*     */         }
/*     */       }
/*  57 */       throw new ChannelException("Failed to configure channel.", localException1);
/*     */     }
/*     */   }
/*     */   
/*     */   protected NioUdtAcceptorChannel(TypeUDT paramTypeUDT) {
/*  62 */     this(NioUdtProvider.newAcceptorChannelUDT(paramTypeUDT));
/*     */   }
/*     */   
/*     */   public UdtServerChannelConfig config()
/*     */   {
/*  67 */     return this.config;
/*     */   }
/*     */   
/*     */   protected void doBind(SocketAddress paramSocketAddress) throws Exception
/*     */   {
/*  72 */     javaChannel().socket().bind(paramSocketAddress, this.config.getBacklog());
/*     */   }
/*     */   
/*     */   protected void doClose() throws Exception
/*     */   {
/*  77 */     javaChannel().close();
/*     */   }
/*     */   
/*     */   protected boolean doConnect(SocketAddress paramSocketAddress1, SocketAddress paramSocketAddress2)
/*     */     throws Exception
/*     */   {
/*  83 */     throw new UnsupportedOperationException();
/*     */   }
/*     */   
/*     */   protected void doDisconnect() throws Exception
/*     */   {
/*  88 */     throw new UnsupportedOperationException();
/*     */   }
/*     */   
/*     */   protected void doFinishConnect() throws Exception
/*     */   {
/*  93 */     throw new UnsupportedOperationException();
/*     */   }
/*     */   
/*     */   protected boolean doWriteMessage(Object paramObject, ChannelOutboundBuffer paramChannelOutboundBuffer) throws Exception
/*     */   {
/*  98 */     throw new UnsupportedOperationException();
/*     */   }
/*     */   
/*     */   protected final Object filterOutboundMessage(Object paramObject) throws Exception
/*     */   {
/* 103 */     throw new UnsupportedOperationException();
/*     */   }
/*     */   
/*     */   public boolean isActive()
/*     */   {
/* 108 */     return javaChannel().socket().isBound();
/*     */   }
/*     */   
/*     */   protected ServerSocketChannelUDT javaChannel()
/*     */   {
/* 113 */     return (ServerSocketChannelUDT)super.javaChannel();
/*     */   }
/*     */   
/*     */   protected SocketAddress localAddress0()
/*     */   {
/* 118 */     return javaChannel().socket().getLocalSocketAddress();
/*     */   }
/*     */   
/*     */   public InetSocketAddress localAddress() {
/* 122 */     return (InetSocketAddress)super.localAddress();
/*     */   }
/*     */   
/*     */   public InetSocketAddress remoteAddress()
/*     */   {
/* 127 */     return null;
/*     */   }
/*     */   
/*     */   protected SocketAddress remoteAddress0()
/*     */   {
/* 132 */     return null;
/*     */   }
/*     */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\io\netty\channel\udt\nio\NioUdtAcceptorChannel.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */
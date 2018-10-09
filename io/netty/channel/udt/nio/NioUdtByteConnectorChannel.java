/*     */ package io.netty.channel.udt.nio;
/*     */ 
/*     */ import com.barchart.udt.TypeUDT;
/*     */ import com.barchart.udt.nio.NioSocketUDT;
/*     */ import com.barchart.udt.nio.SocketChannelUDT;
/*     */ import io.netty.buffer.ByteBuf;
/*     */ import io.netty.channel.Channel;
/*     */ import io.netty.channel.ChannelException;
/*     */ import io.netty.channel.ChannelMetadata;
/*     */ import io.netty.channel.FileRegion;
/*     */ import io.netty.channel.nio.AbstractNioByteChannel;
/*     */ import io.netty.channel.udt.DefaultUdtChannelConfig;
/*     */ import io.netty.channel.udt.UdtChannel;
/*     */ import io.netty.channel.udt.UdtChannelConfig;
/*     */ import io.netty.util.internal.logging.InternalLogger;
/*     */ import io.netty.util.internal.logging.InternalLoggerFactory;
/*     */ import java.net.InetSocketAddress;
/*     */ import java.net.SocketAddress;
/*     */ import java.nio.channels.SelectionKey;
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
/*     */ public class NioUdtByteConnectorChannel
/*     */   extends AbstractNioByteChannel
/*     */   implements UdtChannel
/*     */ {
/*  42 */   private static final InternalLogger logger = InternalLoggerFactory.getInstance(NioUdtByteConnectorChannel.class);
/*     */   
/*     */ 
/*  45 */   private static final ChannelMetadata METADATA = new ChannelMetadata(false);
/*     */   private final UdtChannelConfig config;
/*     */   
/*     */   public NioUdtByteConnectorChannel()
/*     */   {
/*  50 */     this(TypeUDT.STREAM);
/*     */   }
/*     */   
/*     */   public NioUdtByteConnectorChannel(Channel paramChannel, SocketChannelUDT paramSocketChannelUDT) {
/*  54 */     super(paramChannel, paramSocketChannelUDT);
/*     */     try {
/*  56 */       paramSocketChannelUDT.configureBlocking(false);
/*  57 */       switch (paramSocketChannelUDT.socketUDT().status()) {
/*     */       case INIT: 
/*     */       case OPENED: 
/*  60 */         this.config = new DefaultUdtChannelConfig(this, paramSocketChannelUDT, true);
/*  61 */         break;
/*     */       default: 
/*  63 */         this.config = new DefaultUdtChannelConfig(this, paramSocketChannelUDT, false);
/*     */       }
/*     */     }
/*     */     catch (Exception localException1) {
/*     */       try {
/*  68 */         paramSocketChannelUDT.close();
/*     */       } catch (Exception localException2) {
/*  70 */         if (logger.isWarnEnabled()) {
/*  71 */           logger.warn("Failed to close channel.", localException2);
/*     */         }
/*     */       }
/*  74 */       throw new ChannelException("Failed to configure channel.", localException1);
/*     */     }
/*     */   }
/*     */   
/*     */   public NioUdtByteConnectorChannel(SocketChannelUDT paramSocketChannelUDT) {
/*  79 */     this(null, paramSocketChannelUDT);
/*     */   }
/*     */   
/*     */   public NioUdtByteConnectorChannel(TypeUDT paramTypeUDT) {
/*  83 */     this(NioUdtProvider.newConnectorChannelUDT(paramTypeUDT));
/*     */   }
/*     */   
/*     */   public UdtChannelConfig config()
/*     */   {
/*  88 */     return this.config;
/*     */   }
/*     */   
/*     */   protected void doBind(SocketAddress paramSocketAddress) throws Exception
/*     */   {
/*  93 */     javaChannel().bind(paramSocketAddress);
/*     */   }
/*     */   
/*     */   protected void doClose() throws Exception
/*     */   {
/*  98 */     javaChannel().close();
/*     */   }
/*     */   
/*     */   protected boolean doConnect(SocketAddress paramSocketAddress1, SocketAddress paramSocketAddress2)
/*     */     throws Exception
/*     */   {
/* 104 */     doBind(paramSocketAddress2 != null ? paramSocketAddress2 : new InetSocketAddress(0));
/* 105 */     int i = 0;
/*     */     try {
/* 107 */       boolean bool1 = javaChannel().connect(paramSocketAddress1);
/* 108 */       if (!bool1) {
/* 109 */         selectionKey().interestOps(selectionKey().interestOps() | 0x8);
/*     */       }
/*     */       
/* 112 */       i = 1;
/* 113 */       return bool1;
/*     */     } finally {
/* 115 */       if (i == 0) {
/* 116 */         doClose();
/*     */       }
/*     */     }
/*     */   }
/*     */   
/*     */   protected void doDisconnect() throws Exception
/*     */   {
/* 123 */     doClose();
/*     */   }
/*     */   
/*     */   protected void doFinishConnect() throws Exception
/*     */   {
/* 128 */     if (javaChannel().finishConnect()) {
/* 129 */       selectionKey().interestOps(selectionKey().interestOps() & 0xFFFFFFF7);
/*     */     }
/*     */     else {
/* 132 */       throw new Error("Provider error: failed to finish connect. Provider library should be upgraded.");
/*     */     }
/*     */   }
/*     */   
/*     */   protected int doReadBytes(ByteBuf paramByteBuf)
/*     */     throws Exception
/*     */   {
/* 139 */     return paramByteBuf.writeBytes(javaChannel(), paramByteBuf.writableBytes());
/*     */   }
/*     */   
/*     */   protected int doWriteBytes(ByteBuf paramByteBuf) throws Exception
/*     */   {
/* 144 */     int i = paramByteBuf.readableBytes();
/* 145 */     return paramByteBuf.readBytes(javaChannel(), i);
/*     */   }
/*     */   
/*     */   protected long doWriteFileRegion(FileRegion paramFileRegion) throws Exception
/*     */   {
/* 150 */     throw new UnsupportedOperationException();
/*     */   }
/*     */   
/*     */   public boolean isActive()
/*     */   {
/* 155 */     SocketChannelUDT localSocketChannelUDT = javaChannel();
/* 156 */     return (localSocketChannelUDT.isOpen()) && (localSocketChannelUDT.isConnectFinished());
/*     */   }
/*     */   
/*     */   protected SocketChannelUDT javaChannel()
/*     */   {
/* 161 */     return (SocketChannelUDT)super.javaChannel();
/*     */   }
/*     */   
/*     */   protected SocketAddress localAddress0()
/*     */   {
/* 166 */     return javaChannel().socket().getLocalSocketAddress();
/*     */   }
/*     */   
/*     */   public ChannelMetadata metadata()
/*     */   {
/* 171 */     return METADATA;
/*     */   }
/*     */   
/*     */   protected SocketAddress remoteAddress0()
/*     */   {
/* 176 */     return javaChannel().socket().getRemoteSocketAddress();
/*     */   }
/*     */   
/*     */   public InetSocketAddress localAddress()
/*     */   {
/* 181 */     return (InetSocketAddress)super.localAddress();
/*     */   }
/*     */   
/*     */   public InetSocketAddress remoteAddress()
/*     */   {
/* 186 */     return (InetSocketAddress)super.remoteAddress();
/*     */   }
/*     */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\io\netty\channel\udt\nio\NioUdtByteConnectorChannel.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */
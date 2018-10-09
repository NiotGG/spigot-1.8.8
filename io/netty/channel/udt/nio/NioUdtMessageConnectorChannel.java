/*     */ package io.netty.channel.udt.nio;
/*     */ 
/*     */ import com.barchart.udt.TypeUDT;
/*     */ import com.barchart.udt.nio.NioSocketUDT;
/*     */ import com.barchart.udt.nio.SocketChannelUDT;
/*     */ import io.netty.buffer.ByteBuf;
/*     */ import io.netty.buffer.ByteBufAllocator;
/*     */ import io.netty.channel.Channel;
/*     */ import io.netty.channel.ChannelException;
/*     */ import io.netty.channel.ChannelMetadata;
/*     */ import io.netty.channel.ChannelOutboundBuffer;
/*     */ import io.netty.channel.nio.AbstractNioMessageChannel;
/*     */ import io.netty.channel.udt.DefaultUdtChannelConfig;
/*     */ import io.netty.channel.udt.UdtChannel;
/*     */ import io.netty.channel.udt.UdtChannelConfig;
/*     */ import io.netty.channel.udt.UdtMessage;
/*     */ import io.netty.util.internal.StringUtil;
/*     */ import io.netty.util.internal.logging.InternalLogger;
/*     */ import io.netty.util.internal.logging.InternalLoggerFactory;
/*     */ import java.net.InetSocketAddress;
/*     */ import java.net.SocketAddress;
/*     */ import java.nio.channels.SelectionKey;
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
/*     */ public class NioUdtMessageConnectorChannel
/*     */   extends AbstractNioMessageChannel
/*     */   implements UdtChannel
/*     */ {
/*  47 */   private static final InternalLogger logger = InternalLoggerFactory.getInstance(NioUdtMessageConnectorChannel.class);
/*     */   
/*     */ 
/*  50 */   private static final ChannelMetadata METADATA = new ChannelMetadata(false);
/*  51 */   private static final String EXPECTED_TYPE = " (expected: " + StringUtil.simpleClassName(UdtMessage.class) + ')';
/*     */   private final UdtChannelConfig config;
/*     */   
/*     */   public NioUdtMessageConnectorChannel()
/*     */   {
/*  56 */     this(TypeUDT.DATAGRAM);
/*     */   }
/*     */   
/*     */   public NioUdtMessageConnectorChannel(Channel paramChannel, SocketChannelUDT paramSocketChannelUDT) {
/*  60 */     super(paramChannel, paramSocketChannelUDT, 1);
/*     */     try {
/*  62 */       paramSocketChannelUDT.configureBlocking(false);
/*  63 */       switch (paramSocketChannelUDT.socketUDT().status()) {
/*     */       case INIT: 
/*     */       case OPENED: 
/*  66 */         this.config = new DefaultUdtChannelConfig(this, paramSocketChannelUDT, true);
/*  67 */         break;
/*     */       default: 
/*  69 */         this.config = new DefaultUdtChannelConfig(this, paramSocketChannelUDT, false);
/*     */       }
/*     */     }
/*     */     catch (Exception localException1) {
/*     */       try {
/*  74 */         paramSocketChannelUDT.close();
/*     */       } catch (Exception localException2) {
/*  76 */         if (logger.isWarnEnabled()) {
/*  77 */           logger.warn("Failed to close channel.", localException2);
/*     */         }
/*     */       }
/*  80 */       throw new ChannelException("Failed to configure channel.", localException1);
/*     */     }
/*     */   }
/*     */   
/*     */   public NioUdtMessageConnectorChannel(SocketChannelUDT paramSocketChannelUDT) {
/*  85 */     this(null, paramSocketChannelUDT);
/*     */   }
/*     */   
/*     */   public NioUdtMessageConnectorChannel(TypeUDT paramTypeUDT) {
/*  89 */     this(NioUdtProvider.newConnectorChannelUDT(paramTypeUDT));
/*     */   }
/*     */   
/*     */   public UdtChannelConfig config()
/*     */   {
/*  94 */     return this.config;
/*     */   }
/*     */   
/*     */   protected void doBind(SocketAddress paramSocketAddress) throws Exception
/*     */   {
/*  99 */     javaChannel().bind(paramSocketAddress);
/*     */   }
/*     */   
/*     */   protected void doClose() throws Exception
/*     */   {
/* 104 */     javaChannel().close();
/*     */   }
/*     */   
/*     */   protected boolean doConnect(SocketAddress paramSocketAddress1, SocketAddress paramSocketAddress2)
/*     */     throws Exception
/*     */   {
/* 110 */     doBind(paramSocketAddress2 != null ? paramSocketAddress2 : new InetSocketAddress(0));
/* 111 */     int i = 0;
/*     */     try {
/* 113 */       boolean bool1 = javaChannel().connect(paramSocketAddress1);
/* 114 */       if (!bool1) {
/* 115 */         selectionKey().interestOps(selectionKey().interestOps() | 0x8);
/*     */       }
/*     */       
/* 118 */       i = 1;
/* 119 */       return bool1;
/*     */     } finally {
/* 121 */       if (i == 0) {
/* 122 */         doClose();
/*     */       }
/*     */     }
/*     */   }
/*     */   
/*     */   protected void doDisconnect() throws Exception
/*     */   {
/* 129 */     doClose();
/*     */   }
/*     */   
/*     */   protected void doFinishConnect() throws Exception
/*     */   {
/* 134 */     if (javaChannel().finishConnect()) {
/* 135 */       selectionKey().interestOps(selectionKey().interestOps() & 0xFFFFFFF7);
/*     */     }
/*     */     else {
/* 138 */       throw new Error("Provider error: failed to finish connect. Provider library should be upgraded.");
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */   protected int doReadMessages(List<Object> paramList)
/*     */     throws Exception
/*     */   {
/* 146 */     int i = this.config.getReceiveBufferSize();
/*     */     
/* 148 */     ByteBuf localByteBuf = this.config.getAllocator().directBuffer(i);
/*     */     
/*     */ 
/* 151 */     int j = localByteBuf.writeBytes(javaChannel(), i);
/*     */     
/*     */ 
/* 154 */     if (j <= 0) {
/* 155 */       localByteBuf.release();
/* 156 */       return 0;
/*     */     }
/*     */     
/* 159 */     if (j >= i) {
/* 160 */       javaChannel().close();
/* 161 */       throw new ChannelException("Invalid config : increase receive buffer size to avoid message truncation");
/*     */     }
/*     */     
/*     */ 
/*     */ 
/* 166 */     paramList.add(new UdtMessage(localByteBuf));
/*     */     
/* 168 */     return 1;
/*     */   }
/*     */   
/*     */   protected boolean doWriteMessage(Object paramObject, ChannelOutboundBuffer paramChannelOutboundBuffer)
/*     */     throws Exception
/*     */   {
/* 174 */     UdtMessage localUdtMessage = (UdtMessage)paramObject;
/*     */     
/* 176 */     ByteBuf localByteBuf = localUdtMessage.content();
/*     */     
/* 178 */     int i = localByteBuf.readableBytes();
/*     */     
/*     */     long l;
/* 181 */     if (localByteBuf.nioBufferCount() == 1) {
/* 182 */       l = javaChannel().write(localByteBuf.nioBuffer());
/*     */     } else {
/* 184 */       l = javaChannel().write(localByteBuf.nioBuffers());
/*     */     }
/*     */     
/*     */ 
/* 188 */     if ((l <= 0L) && (i > 0)) {
/* 189 */       return false;
/*     */     }
/*     */     
/*     */ 
/* 193 */     if (l != i) {
/* 194 */       throw new Error("Provider error: failed to write message. Provider library should be upgraded.");
/*     */     }
/*     */     
/*     */ 
/* 198 */     return true;
/*     */   }
/*     */   
/*     */   protected final Object filterOutboundMessage(Object paramObject) throws Exception
/*     */   {
/* 203 */     if ((paramObject instanceof UdtMessage)) {
/* 204 */       return paramObject;
/*     */     }
/*     */     
/* 207 */     throw new UnsupportedOperationException("unsupported message type: " + StringUtil.simpleClassName(paramObject) + EXPECTED_TYPE);
/*     */   }
/*     */   
/*     */ 
/*     */   public boolean isActive()
/*     */   {
/* 213 */     SocketChannelUDT localSocketChannelUDT = javaChannel();
/* 214 */     return (localSocketChannelUDT.isOpen()) && (localSocketChannelUDT.isConnectFinished());
/*     */   }
/*     */   
/*     */   protected SocketChannelUDT javaChannel()
/*     */   {
/* 219 */     return (SocketChannelUDT)super.javaChannel();
/*     */   }
/*     */   
/*     */   protected SocketAddress localAddress0()
/*     */   {
/* 224 */     return javaChannel().socket().getLocalSocketAddress();
/*     */   }
/*     */   
/*     */   public ChannelMetadata metadata()
/*     */   {
/* 229 */     return METADATA;
/*     */   }
/*     */   
/*     */   protected SocketAddress remoteAddress0()
/*     */   {
/* 234 */     return javaChannel().socket().getRemoteSocketAddress();
/*     */   }
/*     */   
/*     */   public InetSocketAddress localAddress()
/*     */   {
/* 239 */     return (InetSocketAddress)super.localAddress();
/*     */   }
/*     */   
/*     */   public InetSocketAddress remoteAddress()
/*     */   {
/* 244 */     return (InetSocketAddress)super.remoteAddress();
/*     */   }
/*     */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\io\netty\channel\udt\nio\NioUdtMessageConnectorChannel.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */
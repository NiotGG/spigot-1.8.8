/*     */ package io.netty.channel.rxtx;
/*     */ 
/*     */ import gnu.io.CommPort;
/*     */ import gnu.io.CommPortIdentifier;
/*     */ import gnu.io.SerialPort;
/*     */ import io.netty.channel.AbstractChannel.AbstractUnsafe;
/*     */ import io.netty.channel.ChannelPipeline;
/*     */ import io.netty.channel.ChannelPromise;
/*     */ import io.netty.channel.EventLoop;
/*     */ import io.netty.channel.oio.OioByteStreamChannel;
/*     */ import java.net.SocketAddress;
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
/*     */ 
/*     */ 
/*     */ public class RxtxChannel
/*     */   extends OioByteStreamChannel
/*     */ {
/*  34 */   private static final RxtxDeviceAddress LOCAL_ADDRESS = new RxtxDeviceAddress("localhost");
/*     */   
/*     */   private final RxtxChannelConfig config;
/*     */   
/*  38 */   private boolean open = true;
/*     */   private RxtxDeviceAddress deviceAddress;
/*     */   private SerialPort serialPort;
/*     */   
/*     */   public RxtxChannel() {
/*  43 */     super(null);
/*     */     
/*  45 */     this.config = new DefaultRxtxChannelConfig(this);
/*     */   }
/*     */   
/*     */   public RxtxChannelConfig config()
/*     */   {
/*  50 */     return this.config;
/*     */   }
/*     */   
/*     */   public boolean isOpen()
/*     */   {
/*  55 */     return this.open;
/*     */   }
/*     */   
/*     */   protected AbstractChannel.AbstractUnsafe newUnsafe()
/*     */   {
/*  60 */     return new RxtxUnsafe(null);
/*     */   }
/*     */   
/*     */   protected void doConnect(SocketAddress paramSocketAddress1, SocketAddress paramSocketAddress2) throws Exception
/*     */   {
/*  65 */     RxtxDeviceAddress localRxtxDeviceAddress = (RxtxDeviceAddress)paramSocketAddress1;
/*  66 */     CommPortIdentifier localCommPortIdentifier = CommPortIdentifier.getPortIdentifier(localRxtxDeviceAddress.value());
/*  67 */     CommPort localCommPort = localCommPortIdentifier.open(getClass().getName(), 1000);
/*  68 */     localCommPort.enableReceiveTimeout(((Integer)config().getOption(RxtxChannelOption.READ_TIMEOUT)).intValue());
/*  69 */     this.deviceAddress = localRxtxDeviceAddress;
/*     */     
/*  71 */     this.serialPort = ((SerialPort)localCommPort);
/*     */   }
/*     */   
/*     */   protected void doInit() throws Exception {
/*  75 */     this.serialPort.setSerialPortParams(((Integer)config().getOption(RxtxChannelOption.BAUD_RATE)).intValue(), ((RxtxChannelConfig.Databits)config().getOption(RxtxChannelOption.DATA_BITS)).value(), ((RxtxChannelConfig.Stopbits)config().getOption(RxtxChannelOption.STOP_BITS)).value(), ((RxtxChannelConfig.Paritybit)config().getOption(RxtxChannelOption.PARITY_BIT)).value());
/*     */     
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*  81 */     this.serialPort.setDTR(((Boolean)config().getOption(RxtxChannelOption.DTR)).booleanValue());
/*  82 */     this.serialPort.setRTS(((Boolean)config().getOption(RxtxChannelOption.RTS)).booleanValue());
/*     */     
/*  84 */     activate(this.serialPort.getInputStream(), this.serialPort.getOutputStream());
/*     */   }
/*     */   
/*     */   public RxtxDeviceAddress localAddress()
/*     */   {
/*  89 */     return (RxtxDeviceAddress)super.localAddress();
/*     */   }
/*     */   
/*     */   public RxtxDeviceAddress remoteAddress()
/*     */   {
/*  94 */     return (RxtxDeviceAddress)super.remoteAddress();
/*     */   }
/*     */   
/*     */   protected RxtxDeviceAddress localAddress0()
/*     */   {
/*  99 */     return LOCAL_ADDRESS;
/*     */   }
/*     */   
/*     */   protected RxtxDeviceAddress remoteAddress0()
/*     */   {
/* 104 */     return this.deviceAddress;
/*     */   }
/*     */   
/*     */   protected void doBind(SocketAddress paramSocketAddress) throws Exception
/*     */   {
/* 109 */     throw new UnsupportedOperationException();
/*     */   }
/*     */   
/*     */   protected void doDisconnect() throws Exception
/*     */   {
/* 114 */     doClose();
/*     */   }
/*     */   
/*     */   protected void doClose() throws Exception
/*     */   {
/* 119 */     this.open = false;
/*     */     try {
/* 121 */       super.doClose();
/*     */     } finally {
/* 123 */       if (this.serialPort != null) {
/* 124 */         this.serialPort.removeEventListener();
/* 125 */         this.serialPort.close();
/* 126 */         this.serialPort = null;
/*     */       }
/*     */     }
/*     */   }
/*     */   
/* 131 */   private final class RxtxUnsafe extends AbstractChannel.AbstractUnsafe { private RxtxUnsafe() { super(); }
/*     */     
/*     */ 
/*     */     public void connect(SocketAddress paramSocketAddress1, SocketAddress paramSocketAddress2, final ChannelPromise paramChannelPromise)
/*     */     {
/* 136 */       if ((!paramChannelPromise.setUncancellable()) || (!ensureOpen(paramChannelPromise))) {
/* 137 */         return;
/*     */       }
/*     */       try
/*     */       {
/* 141 */         final boolean bool = RxtxChannel.this.isActive();
/* 142 */         RxtxChannel.this.doConnect(paramSocketAddress1, paramSocketAddress2);
/*     */         
/* 144 */         int i = ((Integer)RxtxChannel.this.config().getOption(RxtxChannelOption.WAIT_TIME)).intValue();
/* 145 */         if (i > 0) {
/* 146 */           RxtxChannel.this.eventLoop().schedule(new Runnable()
/*     */           {
/*     */             public void run() {
/*     */               try {
/* 150 */                 RxtxChannel.this.doInit();
/* 151 */                 RxtxChannel.RxtxUnsafe.this.safeSetSuccess(paramChannelPromise);
/* 152 */                 if ((!bool) && (RxtxChannel.this.isActive())) {
/* 153 */                   RxtxChannel.this.pipeline().fireChannelActive();
/*     */                 }
/*     */               } catch (Throwable localThrowable) {
/* 156 */                 RxtxChannel.RxtxUnsafe.this.safeSetFailure(paramChannelPromise, localThrowable);
/* 157 */                 RxtxChannel.RxtxUnsafe.this.closeIfClosed(); } } }, i, TimeUnit.MILLISECONDS);
/*     */ 
/*     */         }
/*     */         else
/*     */         {
/* 162 */           RxtxChannel.this.doInit();
/* 163 */           safeSetSuccess(paramChannelPromise);
/* 164 */           if ((!bool) && (RxtxChannel.this.isActive())) {
/* 165 */             RxtxChannel.this.pipeline().fireChannelActive();
/*     */           }
/*     */         }
/*     */       } catch (Throwable localThrowable) {
/* 169 */         safeSetFailure(paramChannelPromise, localThrowable);
/* 170 */         closeIfClosed();
/*     */       }
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\io\netty\channel\rxtx\RxtxChannel.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */
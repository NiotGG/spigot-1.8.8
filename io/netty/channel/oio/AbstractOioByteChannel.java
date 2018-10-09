/*     */ package io.netty.channel.oio;
/*     */ 
/*     */ import io.netty.buffer.ByteBuf;
/*     */ import io.netty.buffer.ByteBufAllocator;
/*     */ import io.netty.channel.Channel;
/*     */ import io.netty.channel.Channel.Unsafe;
/*     */ import io.netty.channel.ChannelConfig;
/*     */ import io.netty.channel.ChannelMetadata;
/*     */ import io.netty.channel.ChannelOption;
/*     */ import io.netty.channel.ChannelOutboundBuffer;
/*     */ import io.netty.channel.ChannelPipeline;
/*     */ import io.netty.channel.FileRegion;
/*     */ import io.netty.channel.RecvByteBufAllocator;
/*     */ import io.netty.channel.RecvByteBufAllocator.Handle;
/*     */ import io.netty.channel.socket.ChannelInputShutdownEvent;
/*     */ import io.netty.util.internal.StringUtil;
/*     */ import java.io.IOException;
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
/*     */ public abstract class AbstractOioByteChannel
/*     */   extends AbstractOioChannel
/*     */ {
/*  37 */   private static final ChannelMetadata METADATA = new ChannelMetadata(false);
/*  38 */   private static final String EXPECTED_TYPES = " (expected: " + StringUtil.simpleClassName(ByteBuf.class) + ", " + StringUtil.simpleClassName(FileRegion.class) + ')';
/*     */   
/*     */ 
/*     */   private RecvByteBufAllocator.Handle allocHandle;
/*     */   
/*     */ 
/*     */   private volatile boolean inputShutdown;
/*     */   
/*     */ 
/*     */   protected AbstractOioByteChannel(Channel paramChannel)
/*     */   {
/*  49 */     super(paramChannel);
/*     */   }
/*     */   
/*     */   protected boolean isInputShutdown() {
/*  53 */     return this.inputShutdown;
/*     */   }
/*     */   
/*     */   public ChannelMetadata metadata()
/*     */   {
/*  58 */     return METADATA;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   protected boolean checkInputShutdown()
/*     */   {
/*  66 */     if (this.inputShutdown) {
/*     */       try {
/*  68 */         Thread.sleep(1000L);
/*     */       }
/*     */       catch (InterruptedException localInterruptedException) {}
/*     */       
/*  72 */       return true;
/*     */     }
/*  74 */     return false;
/*     */   }
/*     */   
/*     */   protected void doRead()
/*     */   {
/*  79 */     if (checkInputShutdown()) {
/*  80 */       return;
/*     */     }
/*  82 */     ChannelConfig localChannelConfig = config();
/*  83 */     ChannelPipeline localChannelPipeline = pipeline();
/*     */     
/*  85 */     RecvByteBufAllocator.Handle localHandle = this.allocHandle;
/*  86 */     if (localHandle == null) {
/*  87 */       this.allocHandle = (localHandle = localChannelConfig.getRecvByteBufAllocator().newHandle());
/*     */     }
/*     */     
/*  90 */     ByteBuf localByteBuf = localHandle.allocate(alloc());
/*     */     
/*  92 */     int i = 0;
/*  93 */     int j = 0;
/*  94 */     Object localObject1 = null;
/*  95 */     int k = 0;
/*     */     try {
/*  97 */       int m = 0;
/*     */       for (;;)
/*     */       {
/* 100 */         k = doReadBytes(localByteBuf);
/* 101 */         if (k > 0) {
/* 102 */           j = 1;
/* 103 */         } else if (k < 0) {
/* 104 */           i = 1;
/*     */         }
/*     */         
/* 107 */         int n = available();
/* 108 */         if (n <= 0) {
/*     */           break;
/*     */         }
/*     */         
/* 112 */         if (!localByteBuf.isWritable()) {
/* 113 */           int i1 = localByteBuf.capacity();
/* 114 */           int i2 = localByteBuf.maxCapacity();
/* 115 */           if (i1 == i2) {
/* 116 */             if (j != 0) {
/* 117 */               j = 0;
/* 118 */               localChannelPipeline.fireChannelRead(localByteBuf);
/* 119 */               localByteBuf = alloc().buffer();
/*     */             }
/*     */           } else {
/* 122 */             int i3 = localByteBuf.writerIndex();
/* 123 */             if (i3 + n > i2) {
/* 124 */               localByteBuf.capacity(i2);
/*     */             } else {
/* 126 */               localByteBuf.ensureWritable(n);
/*     */             }
/*     */           }
/*     */         }
/*     */         
/* 131 */         if (m >= Integer.MAX_VALUE - k)
/*     */         {
/* 133 */           m = Integer.MAX_VALUE;
/*     */         }
/*     */         else
/*     */         {
/* 137 */           m += k;
/*     */           
/* 139 */           if (!localChannelConfig.isAutoRead()) {
/*     */             break;
/*     */           }
/*     */         }
/*     */       }
/*     */       
/* 145 */       localHandle.record(m);
/*     */     }
/*     */     catch (Throwable localThrowable) {
/* 148 */       localObject1 = localThrowable;
/*     */     } finally {
/* 150 */       if (j != 0) {
/* 151 */         localChannelPipeline.fireChannelRead(localByteBuf);
/*     */       }
/*     */       else {
/* 154 */         localByteBuf.release();
/*     */       }
/*     */       
/* 157 */       localChannelPipeline.fireChannelReadComplete();
/* 158 */       if (localObject1 != null) {
/* 159 */         if ((localObject1 instanceof IOException)) {
/* 160 */           i = 1;
/* 161 */           pipeline().fireExceptionCaught((Throwable)localObject1);
/*     */         } else {
/* 163 */           localChannelPipeline.fireExceptionCaught((Throwable)localObject1);
/* 164 */           unsafe().close(voidPromise());
/*     */         }
/*     */       }
/*     */       
/* 168 */       if (i != 0) {
/* 169 */         this.inputShutdown = true;
/* 170 */         if (isOpen()) {
/* 171 */           if (Boolean.TRUE.equals(config().getOption(ChannelOption.ALLOW_HALF_CLOSURE))) {
/* 172 */             localChannelPipeline.fireUserEventTriggered(ChannelInputShutdownEvent.INSTANCE);
/*     */           } else {
/* 174 */             unsafe().close(unsafe().voidPromise());
/*     */           }
/*     */         }
/*     */       }
/* 178 */       if ((k == 0) && (isActive()))
/*     */       {
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/* 185 */         read();
/*     */       }
/*     */     }
/*     */   }
/*     */   
/*     */   protected void doWrite(ChannelOutboundBuffer paramChannelOutboundBuffer) throws Exception
/*     */   {
/*     */     for (;;) {
/* 193 */       Object localObject1 = paramChannelOutboundBuffer.current();
/* 194 */       if (localObject1 == null) {
/*     */         break;
/*     */       }
/*     */       Object localObject2;
/* 198 */       if ((localObject1 instanceof ByteBuf)) {
/* 199 */         localObject2 = (ByteBuf)localObject1;
/* 200 */         int i = ((ByteBuf)localObject2).readableBytes();
/* 201 */         while (i > 0) {
/* 202 */           doWriteBytes((ByteBuf)localObject2);
/* 203 */           int j = ((ByteBuf)localObject2).readableBytes();
/* 204 */           paramChannelOutboundBuffer.progress(i - j);
/* 205 */           i = j;
/*     */         }
/* 207 */         paramChannelOutboundBuffer.remove();
/* 208 */       } else if ((localObject1 instanceof FileRegion)) {
/* 209 */         localObject2 = (FileRegion)localObject1;
/* 210 */         long l = ((FileRegion)localObject2).transfered();
/* 211 */         doWriteFileRegion((FileRegion)localObject2);
/* 212 */         paramChannelOutboundBuffer.progress(((FileRegion)localObject2).transfered() - l);
/* 213 */         paramChannelOutboundBuffer.remove();
/*     */       } else {
/* 215 */         paramChannelOutboundBuffer.remove(new UnsupportedOperationException("unsupported message type: " + StringUtil.simpleClassName(localObject1)));
/*     */       }
/*     */     }
/*     */   }
/*     */   
/*     */   protected final Object filterOutboundMessage(Object paramObject)
/*     */     throws Exception
/*     */   {
/* 223 */     if (((paramObject instanceof ByteBuf)) || ((paramObject instanceof FileRegion))) {
/* 224 */       return paramObject;
/*     */     }
/*     */     
/* 227 */     throw new UnsupportedOperationException("unsupported message type: " + StringUtil.simpleClassName(paramObject) + EXPECTED_TYPES);
/*     */   }
/*     */   
/*     */   protected abstract int available();
/*     */   
/*     */   protected abstract int doReadBytes(ByteBuf paramByteBuf)
/*     */     throws Exception;
/*     */   
/*     */   protected abstract void doWriteBytes(ByteBuf paramByteBuf)
/*     */     throws Exception;
/*     */   
/*     */   protected abstract void doWriteFileRegion(FileRegion paramFileRegion)
/*     */     throws Exception;
/*     */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\io\netty\channel\oio\AbstractOioByteChannel.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */
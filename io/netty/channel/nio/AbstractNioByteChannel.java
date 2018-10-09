/*     */ package io.netty.channel.nio;
/*     */ 
/*     */ import io.netty.buffer.ByteBuf;
/*     */ import io.netty.buffer.ByteBufAllocator;
/*     */ import io.netty.channel.Channel;
/*     */ import io.netty.channel.ChannelConfig;
/*     */ import io.netty.channel.ChannelOption;
/*     */ import io.netty.channel.ChannelOutboundBuffer;
/*     */ import io.netty.channel.ChannelPipeline;
/*     */ import io.netty.channel.FileRegion;
/*     */ import io.netty.channel.RecvByteBufAllocator;
/*     */ import io.netty.channel.RecvByteBufAllocator.Handle;
/*     */ import io.netty.channel.socket.ChannelInputShutdownEvent;
/*     */ import io.netty.util.internal.StringUtil;
/*     */ import java.io.IOException;
/*     */ import java.nio.channels.SelectableChannel;
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
/*     */ public abstract class AbstractNioByteChannel
/*     */   extends AbstractNioChannel
/*     */ {
/*  39 */   private static final String EXPECTED_TYPES = " (expected: " + StringUtil.simpleClassName(ByteBuf.class) + ", " + StringUtil.simpleClassName(FileRegion.class) + ')';
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   private Runnable flushTask;
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   protected AbstractNioByteChannel(Channel paramChannel, SelectableChannel paramSelectableChannel)
/*     */   {
/*  52 */     super(paramChannel, paramSelectableChannel, 1);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*  57 */   protected AbstractNioChannel.AbstractNioUnsafe newUnsafe() { return new NioByteUnsafe(null); }
/*     */   
/*     */   private final class NioByteUnsafe extends AbstractNioChannel.AbstractNioUnsafe {
/*  60 */     private NioByteUnsafe() { super(); }
/*     */     
/*     */     private RecvByteBufAllocator.Handle allocHandle;
/*     */     private void closeOnRead(ChannelPipeline paramChannelPipeline) {
/*  64 */       SelectionKey localSelectionKey = AbstractNioByteChannel.this.selectionKey();
/*  65 */       AbstractNioByteChannel.this.setInputShutdown();
/*  66 */       if (AbstractNioByteChannel.this.isOpen()) {
/*  67 */         if (Boolean.TRUE.equals(AbstractNioByteChannel.this.config().getOption(ChannelOption.ALLOW_HALF_CLOSURE))) {
/*  68 */           localSelectionKey.interestOps(localSelectionKey.interestOps() & (AbstractNioByteChannel.this.readInterestOp ^ 0xFFFFFFFF));
/*  69 */           paramChannelPipeline.fireUserEventTriggered(ChannelInputShutdownEvent.INSTANCE);
/*     */         } else {
/*  71 */           close(voidPromise());
/*     */         }
/*     */       }
/*     */     }
/*     */     
/*     */     private void handleReadException(ChannelPipeline paramChannelPipeline, ByteBuf paramByteBuf, Throwable paramThrowable, boolean paramBoolean)
/*     */     {
/*  78 */       if (paramByteBuf != null) {
/*  79 */         if (paramByteBuf.isReadable()) {
/*  80 */           AbstractNioByteChannel.this.setReadPending(false);
/*  81 */           paramChannelPipeline.fireChannelRead(paramByteBuf);
/*     */         } else {
/*  83 */           paramByteBuf.release();
/*     */         }
/*     */       }
/*  86 */       paramChannelPipeline.fireChannelReadComplete();
/*  87 */       paramChannelPipeline.fireExceptionCaught(paramThrowable);
/*  88 */       if ((paramBoolean) || ((paramThrowable instanceof IOException))) {
/*  89 */         closeOnRead(paramChannelPipeline);
/*     */       }
/*     */     }
/*     */     
/*     */     public void read()
/*     */     {
/*  95 */       ChannelConfig localChannelConfig = AbstractNioByteChannel.this.config();
/*  96 */       if ((!localChannelConfig.isAutoRead()) && (!AbstractNioByteChannel.this.isReadPending()))
/*     */       {
/*  98 */         removeReadOp();
/*  99 */         return;
/*     */       }
/*     */       
/* 102 */       ChannelPipeline localChannelPipeline = AbstractNioByteChannel.this.pipeline();
/* 103 */       ByteBufAllocator localByteBufAllocator = localChannelConfig.getAllocator();
/* 104 */       int i = localChannelConfig.getMaxMessagesPerRead();
/* 105 */       RecvByteBufAllocator.Handle localHandle = this.allocHandle;
/* 106 */       if (localHandle == null) {
/* 107 */         this.allocHandle = (localHandle = localChannelConfig.getRecvByteBufAllocator().newHandle());
/*     */       }
/*     */       
/* 110 */       ByteBuf localByteBuf = null;
/* 111 */       int j = 0;
/* 112 */       boolean bool = false;
/*     */       try {
/* 114 */         int k = 0;
/* 115 */         int m = 0;
/*     */         do {
/* 117 */           localByteBuf = localHandle.allocate(localByteBufAllocator);
/* 118 */           int n = localByteBuf.writableBytes();
/* 119 */           int i1 = AbstractNioByteChannel.this.doReadBytes(localByteBuf);
/* 120 */           if (i1 <= 0)
/*     */           {
/* 122 */             localByteBuf.release();
/* 123 */             bool = i1 < 0;
/* 124 */             break;
/*     */           }
/* 126 */           if (m == 0) {
/* 127 */             m = 1;
/* 128 */             AbstractNioByteChannel.this.setReadPending(false);
/*     */           }
/* 130 */           localChannelPipeline.fireChannelRead(localByteBuf);
/* 131 */           localByteBuf = null;
/*     */           
/* 133 */           if (k >= Integer.MAX_VALUE - i1)
/*     */           {
/* 135 */             k = Integer.MAX_VALUE;
/* 136 */             break;
/*     */           }
/*     */           
/* 139 */           k += i1;
/*     */           
/*     */ 
/* 142 */           if (!localChannelConfig.isAutoRead()) {
/*     */             break;
/*     */           }
/*     */           
/* 146 */           if (i1 < n) {
/*     */             break;
/*     */           }
/*     */           
/*     */ 
/* 151 */           j++; } while (j < i);
/*     */         
/* 153 */         localChannelPipeline.fireChannelReadComplete();
/* 154 */         localHandle.record(k);
/*     */         
/* 156 */         if (bool) {
/* 157 */           closeOnRead(localChannelPipeline);
/* 158 */           bool = false;
/*     */         }
/*     */       } catch (Throwable localThrowable) {
/* 161 */         handleReadException(localChannelPipeline, localByteBuf, localThrowable, bool);
/*     */ 
/*     */ 
/*     */       }
/*     */       finally
/*     */       {
/*     */ 
/*     */ 
/* 169 */         if ((!localChannelConfig.isAutoRead()) && (!AbstractNioByteChannel.this.isReadPending())) {
/* 170 */           removeReadOp();
/*     */         }
/*     */       }
/*     */     }
/*     */   }
/*     */   
/*     */   protected void doWrite(ChannelOutboundBuffer paramChannelOutboundBuffer) throws Exception
/*     */   {
/* 178 */     int i = -1;
/*     */     for (;;)
/*     */     {
/* 181 */       Object localObject1 = paramChannelOutboundBuffer.current();
/* 182 */       if (localObject1 == null)
/*     */       {
/* 184 */         clearOpWrite();
/* 185 */         break; }
/*     */       Object localObject2;
/*     */       int j;
/* 188 */       boolean bool; if ((localObject1 instanceof ByteBuf)) {
/* 189 */         localObject2 = (ByteBuf)localObject1;
/* 190 */         j = ((ByteBuf)localObject2).readableBytes();
/* 191 */         if (j == 0) {
/* 192 */           paramChannelOutboundBuffer.remove();
/*     */         }
/*     */         else
/*     */         {
/* 196 */           bool = false;
/* 197 */           int k = 0;
/* 198 */           long l1 = 0L;
/* 199 */           if (i == -1) {
/* 200 */             i = config().getWriteSpinCount();
/*     */           }
/* 202 */           for (int m = i - 1; m >= 0; m--) {
/* 203 */             int n = doWriteBytes((ByteBuf)localObject2);
/* 204 */             if (n == 0) {
/* 205 */               bool = true;
/* 206 */               break;
/*     */             }
/*     */             
/* 209 */             l1 += n;
/* 210 */             if (!((ByteBuf)localObject2).isReadable()) {
/* 211 */               k = 1;
/* 212 */               break;
/*     */             }
/*     */           }
/*     */           
/* 216 */           paramChannelOutboundBuffer.progress(l1);
/*     */           
/* 218 */           if (k != 0) {
/* 219 */             paramChannelOutboundBuffer.remove();
/*     */           } else {
/* 221 */             incompleteWrite(bool);
/* 222 */             break;
/*     */           }
/* 224 */         } } else if ((localObject1 instanceof FileRegion)) {
/* 225 */         localObject2 = (FileRegion)localObject1;
/* 226 */         j = 0;
/* 227 */         bool = false;
/* 228 */         long l2 = 0L;
/* 229 */         if (i == -1) {
/* 230 */           i = config().getWriteSpinCount();
/*     */         }
/* 232 */         for (int i1 = i - 1; i1 >= 0; i1--) {
/* 233 */           long l3 = doWriteFileRegion((FileRegion)localObject2);
/* 234 */           if (l3 == 0L) {
/* 235 */             j = 1;
/* 236 */             break;
/*     */           }
/*     */           
/* 239 */           l2 += l3;
/* 240 */           if (((FileRegion)localObject2).transfered() >= ((FileRegion)localObject2).count()) {
/* 241 */             bool = true;
/* 242 */             break;
/*     */           }
/*     */         }
/*     */         
/* 246 */         paramChannelOutboundBuffer.progress(l2);
/*     */         
/* 248 */         if (bool) {
/* 249 */           paramChannelOutboundBuffer.remove();
/*     */         } else {
/* 251 */           incompleteWrite(j);
/* 252 */           break;
/*     */         }
/*     */       }
/*     */       else {
/* 256 */         throw new Error();
/*     */       }
/*     */     }
/*     */   }
/*     */   
/*     */   protected final Object filterOutboundMessage(Object paramObject)
/*     */   {
/* 263 */     if ((paramObject instanceof ByteBuf)) {
/* 264 */       ByteBuf localByteBuf = (ByteBuf)paramObject;
/* 265 */       if (localByteBuf.isDirect()) {
/* 266 */         return paramObject;
/*     */       }
/*     */       
/* 269 */       return newDirectBuffer(localByteBuf);
/*     */     }
/*     */     
/* 272 */     if ((paramObject instanceof FileRegion)) {
/* 273 */       return paramObject;
/*     */     }
/*     */     
/* 276 */     throw new UnsupportedOperationException("unsupported message type: " + StringUtil.simpleClassName(paramObject) + EXPECTED_TYPES);
/*     */   }
/*     */   
/*     */ 
/*     */   protected final void incompleteWrite(boolean paramBoolean)
/*     */   {
/* 282 */     if (paramBoolean) {
/* 283 */       setOpWrite();
/*     */     }
/*     */     else {
/* 286 */       Object localObject = this.flushTask;
/* 287 */       if (localObject == null) {
/* 288 */         localObject = this. = new Runnable()
/*     */         {
/*     */           public void run() {
/* 291 */             AbstractNioByteChannel.this.flush();
/*     */           }
/*     */         };
/*     */       }
/* 295 */       eventLoop().execute((Runnable)localObject);
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   protected abstract long doWriteFileRegion(FileRegion paramFileRegion)
/*     */     throws Exception;
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   protected abstract int doReadBytes(ByteBuf paramByteBuf)
/*     */     throws Exception;
/*     */   
/*     */ 
/*     */ 
/*     */   protected abstract int doWriteBytes(ByteBuf paramByteBuf)
/*     */     throws Exception;
/*     */   
/*     */ 
/*     */ 
/*     */   protected final void setOpWrite()
/*     */   {
/* 320 */     SelectionKey localSelectionKey = selectionKey();
/*     */     
/*     */ 
/*     */ 
/* 324 */     if (!localSelectionKey.isValid()) {
/* 325 */       return;
/*     */     }
/* 327 */     int i = localSelectionKey.interestOps();
/* 328 */     if ((i & 0x4) == 0) {
/* 329 */       localSelectionKey.interestOps(i | 0x4);
/*     */     }
/*     */   }
/*     */   
/*     */   protected final void clearOpWrite() {
/* 334 */     SelectionKey localSelectionKey = selectionKey();
/*     */     
/*     */ 
/*     */ 
/* 338 */     if (!localSelectionKey.isValid()) {
/* 339 */       return;
/*     */     }
/* 341 */     int i = localSelectionKey.interestOps();
/* 342 */     if ((i & 0x4) != 0) {
/* 343 */       localSelectionKey.interestOps(i & 0xFFFFFFFB);
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\io\netty\channel\nio\AbstractNioByteChannel.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */
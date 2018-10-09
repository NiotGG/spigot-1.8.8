/*     */ package io.netty.channel.nio;
/*     */ 
/*     */ import io.netty.channel.Channel;
/*     */ import io.netty.channel.ChannelConfig;
/*     */ import io.netty.channel.ChannelOutboundBuffer;
/*     */ import io.netty.channel.ChannelPipeline;
/*     */ import io.netty.channel.ServerChannel;
/*     */ import java.io.IOException;
/*     */ import java.nio.channels.SelectableChannel;
/*     */ import java.nio.channels.SelectionKey;
/*     */ import java.util.ArrayList;
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
/*     */ 
/*     */ 
/*     */ public abstract class AbstractNioMessageChannel
/*     */   extends AbstractNioChannel
/*     */ {
/*     */   protected AbstractNioMessageChannel(Channel paramChannel, SelectableChannel paramSelectableChannel, int paramInt)
/*     */   {
/*  39 */     super(paramChannel, paramSelectableChannel, paramInt);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*  44 */   protected AbstractNioChannel.AbstractNioUnsafe newUnsafe() { return new NioMessageUnsafe(null); }
/*     */   
/*     */   private final class NioMessageUnsafe extends AbstractNioChannel.AbstractNioUnsafe {
/*  47 */     private NioMessageUnsafe() { super(); }
/*     */     
/*  49 */     private final List<Object> readBuf = new ArrayList();
/*     */     
/*     */     public void read()
/*     */     {
/*  53 */       assert (AbstractNioMessageChannel.this.eventLoop().inEventLoop());
/*  54 */       ChannelConfig localChannelConfig = AbstractNioMessageChannel.this.config();
/*  55 */       if ((!localChannelConfig.isAutoRead()) && (!AbstractNioMessageChannel.this.isReadPending()))
/*     */       {
/*  57 */         removeReadOp();
/*  58 */         return;
/*     */       }
/*     */       
/*  61 */       int i = localChannelConfig.getMaxMessagesPerRead();
/*  62 */       ChannelPipeline localChannelPipeline = AbstractNioMessageChannel.this.pipeline();
/*  63 */       int j = 0;
/*  64 */       Object localObject1 = null;
/*     */       try {
/*     */         try {
/*     */           for (;;) {
/*  68 */             int k = AbstractNioMessageChannel.this.doReadMessages(this.readBuf);
/*  69 */             if (k == 0) {
/*     */               break;
/*     */             }
/*  72 */             if (k < 0) {
/*  73 */               j = 1;
/*     */ 
/*     */ 
/*     */ 
/*     */             }
/*  78 */             else if (localChannelConfig.isAutoRead())
/*     */             {
/*     */ 
/*     */ 
/*  82 */               if (this.readBuf.size() >= i)
/*     */                 break;
/*     */             }
/*     */           }
/*     */         } catch (Throwable localThrowable) {
/*  87 */           localObject1 = localThrowable;
/*     */         }
/*  89 */         AbstractNioMessageChannel.this.setReadPending(false);
/*  90 */         int m = this.readBuf.size();
/*  91 */         for (int n = 0; n < m; n++) {
/*  92 */           localChannelPipeline.fireChannelRead(this.readBuf.get(n));
/*     */         }
/*     */         
/*  95 */         this.readBuf.clear();
/*  96 */         localChannelPipeline.fireChannelReadComplete();
/*     */         
/*  98 */         if (localObject1 != null) {
/*  99 */           if ((localObject1 instanceof IOException))
/*     */           {
/*     */ 
/* 102 */             j = !(AbstractNioMessageChannel.this instanceof ServerChannel) ? 1 : 0;
/*     */           }
/*     */           
/* 105 */           localChannelPipeline.fireExceptionCaught((Throwable)localObject1);
/*     */         }
/*     */         
/* 108 */         if ((j != 0) && 
/* 109 */           (AbstractNioMessageChannel.this.isOpen())) {
/* 110 */           close(voidPromise());
/*     */ 
/*     */         }
/*     */         
/*     */ 
/*     */ 
/*     */       }
/*     */       finally
/*     */       {
/*     */ 
/* 120 */         if ((!localChannelConfig.isAutoRead()) && (!AbstractNioMessageChannel.this.isReadPending())) {
/* 121 */           removeReadOp();
/*     */         }
/*     */       }
/*     */     }
/*     */   }
/*     */   
/*     */   protected void doWrite(ChannelOutboundBuffer paramChannelOutboundBuffer) throws Exception
/*     */   {
/* 129 */     SelectionKey localSelectionKey = selectionKey();
/* 130 */     int i = localSelectionKey.interestOps();
/*     */     for (;;)
/*     */     {
/* 133 */       Object localObject = paramChannelOutboundBuffer.current();
/* 134 */       if (localObject == null)
/*     */       {
/* 136 */         if ((i & 0x4) == 0) break;
/* 137 */         localSelectionKey.interestOps(i & 0xFFFFFFFB); break;
/*     */       }
/*     */       
/*     */       try
/*     */       {
/* 142 */         int j = 0;
/* 143 */         for (int k = config().getWriteSpinCount() - 1; k >= 0; k--) {
/* 144 */           if (doWriteMessage(localObject, paramChannelOutboundBuffer)) {
/* 145 */             j = 1;
/* 146 */             break;
/*     */           }
/*     */         }
/*     */         
/* 150 */         if (j != 0) {
/* 151 */           paramChannelOutboundBuffer.remove();
/*     */         }
/*     */         else {
/* 154 */           if ((i & 0x4) == 0) {
/* 155 */             localSelectionKey.interestOps(i | 0x4);
/*     */           }
/* 157 */           break;
/*     */         }
/*     */       } catch (IOException localIOException) {
/* 160 */         if (continueOnWriteError()) {
/* 161 */           paramChannelOutboundBuffer.remove(localIOException);
/*     */         } else {
/* 163 */           throw localIOException;
/*     */         }
/*     */       }
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   protected boolean continueOnWriteError()
/*     */   {
/* 173 */     return false;
/*     */   }
/*     */   
/*     */   protected abstract int doReadMessages(List<Object> paramList)
/*     */     throws Exception;
/*     */   
/*     */   protected abstract boolean doWriteMessage(Object paramObject, ChannelOutboundBuffer paramChannelOutboundBuffer)
/*     */     throws Exception;
/*     */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\io\netty\channel\nio\AbstractNioMessageChannel.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */
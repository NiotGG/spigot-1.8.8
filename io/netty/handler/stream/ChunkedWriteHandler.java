/*     */ package io.netty.handler.stream;
/*     */ 
/*     */ import io.netty.buffer.ByteBuf;
/*     */ import io.netty.buffer.ByteBufHolder;
/*     */ import io.netty.buffer.Unpooled;
/*     */ import io.netty.channel.Channel;
/*     */ import io.netty.channel.ChannelDuplexHandler;
/*     */ import io.netty.channel.ChannelFuture;
/*     */ import io.netty.channel.ChannelFutureListener;
/*     */ import io.netty.channel.ChannelHandlerContext;
/*     */ import io.netty.channel.ChannelProgressivePromise;
/*     */ import io.netty.channel.ChannelPromise;
/*     */ import io.netty.util.ReferenceCountUtil;
/*     */ import io.netty.util.concurrent.EventExecutor;
/*     */ import io.netty.util.internal.logging.InternalLogger;
/*     */ import io.netty.util.internal.logging.InternalLoggerFactory;
/*     */ import java.nio.channels.ClosedChannelException;
/*     */ import java.util.ArrayDeque;
/*     */ import java.util.Queue;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ public class ChunkedWriteHandler
/*     */   extends ChannelDuplexHandler
/*     */ {
/*  72 */   private static final InternalLogger logger = InternalLoggerFactory.getInstance(ChunkedWriteHandler.class);
/*     */   
/*     */ 
/*  75 */   private final Queue<PendingWrite> queue = new ArrayDeque();
/*     */   
/*     */   private volatile ChannelHandlerContext ctx;
/*     */   
/*     */   private PendingWrite currentWrite;
/*     */   
/*     */ 
/*     */   public ChunkedWriteHandler() {}
/*     */   
/*     */   @Deprecated
/*     */   public ChunkedWriteHandler(int paramInt)
/*     */   {
/*  87 */     if (paramInt <= 0) {
/*  88 */       throw new IllegalArgumentException("maxPendingWrites: " + paramInt + " (expected: > 0)");
/*     */     }
/*     */   }
/*     */   
/*     */   public void handlerAdded(ChannelHandlerContext paramChannelHandlerContext)
/*     */     throws Exception
/*     */   {
/*  95 */     this.ctx = paramChannelHandlerContext;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public void resumeTransfer()
/*     */   {
/* 102 */     final ChannelHandlerContext localChannelHandlerContext = this.ctx;
/* 103 */     if (localChannelHandlerContext == null) {
/* 104 */       return;
/*     */     }
/* 106 */     if (localChannelHandlerContext.executor().inEventLoop()) {
/*     */       try {
/* 108 */         doFlush(localChannelHandlerContext);
/*     */       } catch (Exception localException) {
/* 110 */         if (logger.isWarnEnabled()) {
/* 111 */           logger.warn("Unexpected exception while sending chunks.", localException);
/*     */         }
/*     */         
/*     */       }
/*     */     } else {
/* 116 */       localChannelHandlerContext.executor().execute(new Runnable()
/*     */       {
/*     */         public void run()
/*     */         {
/*     */           try {
/* 121 */             ChunkedWriteHandler.this.doFlush(localChannelHandlerContext);
/*     */           } catch (Exception localException) {
/* 123 */             if (ChunkedWriteHandler.logger.isWarnEnabled()) {
/* 124 */               ChunkedWriteHandler.logger.warn("Unexpected exception while sending chunks.", localException);
/*     */             }
/*     */           }
/*     */         }
/*     */       });
/*     */     }
/*     */   }
/*     */   
/*     */   public void write(ChannelHandlerContext paramChannelHandlerContext, Object paramObject, ChannelPromise paramChannelPromise) throws Exception
/*     */   {
/* 134 */     this.queue.add(new PendingWrite(paramObject, paramChannelPromise));
/*     */   }
/*     */   
/*     */   public void flush(ChannelHandlerContext paramChannelHandlerContext) throws Exception
/*     */   {
/* 139 */     Channel localChannel = paramChannelHandlerContext.channel();
/* 140 */     if ((localChannel.isWritable()) || (!localChannel.isActive())) {
/* 141 */       doFlush(paramChannelHandlerContext);
/*     */     }
/*     */   }
/*     */   
/*     */   public void channelInactive(ChannelHandlerContext paramChannelHandlerContext) throws Exception
/*     */   {
/* 147 */     doFlush(paramChannelHandlerContext);
/* 148 */     super.channelInactive(paramChannelHandlerContext);
/*     */   }
/*     */   
/*     */   public void channelWritabilityChanged(ChannelHandlerContext paramChannelHandlerContext) throws Exception
/*     */   {
/* 153 */     if (paramChannelHandlerContext.channel().isWritable())
/*     */     {
/* 155 */       doFlush(paramChannelHandlerContext);
/*     */     }
/* 157 */     paramChannelHandlerContext.fireChannelWritabilityChanged();
/*     */   }
/*     */   
/*     */   private void discard(Throwable paramThrowable) {
/*     */     for (;;) {
/* 162 */       PendingWrite localPendingWrite = this.currentWrite;
/*     */       
/* 164 */       if (this.currentWrite == null) {
/* 165 */         localPendingWrite = (PendingWrite)this.queue.poll();
/*     */       } else {
/* 167 */         this.currentWrite = null;
/*     */       }
/*     */       
/* 170 */       if (localPendingWrite == null) {
/*     */         break;
/*     */       }
/* 173 */       Object localObject = localPendingWrite.msg;
/* 174 */       if ((localObject instanceof ChunkedInput)) {
/* 175 */         ChunkedInput localChunkedInput = (ChunkedInput)localObject;
/*     */         try {
/* 177 */           if (!localChunkedInput.isEndOfInput()) {
/* 178 */             if (paramThrowable == null) {
/* 179 */               paramThrowable = new ClosedChannelException();
/*     */             }
/* 181 */             localPendingWrite.fail(paramThrowable);
/*     */           } else {
/* 183 */             localPendingWrite.success();
/*     */           }
/* 185 */           closeInput(localChunkedInput);
/*     */         } catch (Exception localException) {
/* 187 */           localPendingWrite.fail(localException);
/* 188 */           logger.warn(ChunkedInput.class.getSimpleName() + ".isEndOfInput() failed", localException);
/* 189 */           closeInput(localChunkedInput);
/*     */         }
/*     */       } else {
/* 192 */         if (paramThrowable == null) {
/* 193 */           paramThrowable = new ClosedChannelException();
/*     */         }
/* 195 */         localPendingWrite.fail(paramThrowable);
/*     */       }
/*     */     }
/*     */   }
/*     */   
/*     */   private void doFlush(ChannelHandlerContext paramChannelHandlerContext) throws Exception {
/* 201 */     final Channel localChannel = paramChannelHandlerContext.channel();
/* 202 */     if (!localChannel.isActive()) {
/* 203 */       discard(null);
/* 204 */       return;
/*     */     }
/* 206 */     while (localChannel.isWritable()) {
/* 207 */       if (this.currentWrite == null) {
/* 208 */         this.currentWrite = ((PendingWrite)this.queue.poll());
/*     */       }
/*     */       
/* 211 */       if (this.currentWrite == null) {
/*     */         break;
/*     */       }
/* 214 */       final PendingWrite localPendingWrite = this.currentWrite;
/* 215 */       final Object localObject1 = localPendingWrite.msg;
/*     */       
/* 217 */       if ((localObject1 instanceof ChunkedInput)) {
/* 218 */         final ChunkedInput localChunkedInput = (ChunkedInput)localObject1;
/*     */         
/*     */ 
/* 221 */         Object localObject2 = null;
/*     */         boolean bool;
/* 223 */         int i; try { localObject2 = localChunkedInput.readChunk(paramChannelHandlerContext);
/* 224 */           bool = localChunkedInput.isEndOfInput();
/*     */           
/* 226 */           if (localObject2 == null)
/*     */           {
/* 228 */             i = !bool ? 1 : 0;
/*     */           } else {
/* 230 */             i = 0;
/*     */           }
/*     */         } catch (Throwable localThrowable) {
/* 233 */           this.currentWrite = null;
/*     */           
/* 235 */           if (localObject2 != null) {
/* 236 */             ReferenceCountUtil.release(localObject2);
/*     */           }
/*     */           
/* 239 */           localPendingWrite.fail(localThrowable);
/* 240 */           closeInput(localChunkedInput);
/* 241 */           break;
/*     */         }
/*     */         
/* 244 */         if (i != 0) {
/*     */           break;
/*     */         }
/*     */         
/*     */ 
/*     */ 
/*     */ 
/* 251 */         if (localObject2 == null)
/*     */         {
/*     */ 
/* 254 */           localObject2 = Unpooled.EMPTY_BUFFER;
/*     */         }
/*     */         
/* 257 */         final int j = amount(localObject2);
/* 258 */         ChannelFuture localChannelFuture = paramChannelHandlerContext.write(localObject2);
/* 259 */         if (bool) {
/* 260 */           this.currentWrite = null;
/*     */           
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/* 267 */           localChannelFuture.addListener(new ChannelFutureListener()
/*     */           {
/*     */             public void operationComplete(ChannelFuture paramAnonymousChannelFuture) throws Exception {
/* 270 */               localPendingWrite.progress(j);
/* 271 */               localPendingWrite.success();
/* 272 */               ChunkedWriteHandler.closeInput(localChunkedInput);
/*     */             }
/*     */           });
/* 275 */         } else if (localChannel.isWritable()) {
/* 276 */           localChannelFuture.addListener(new ChannelFutureListener()
/*     */           {
/*     */             public void operationComplete(ChannelFuture paramAnonymousChannelFuture) throws Exception {
/* 279 */               if (!paramAnonymousChannelFuture.isSuccess()) {
/* 280 */                 ChunkedWriteHandler.closeInput((ChunkedInput)localObject1);
/* 281 */                 localPendingWrite.fail(paramAnonymousChannelFuture.cause());
/*     */               } else {
/* 283 */                 localPendingWrite.progress(j);
/*     */               }
/*     */             }
/*     */           });
/*     */         } else {
/* 288 */           localChannelFuture.addListener(new ChannelFutureListener()
/*     */           {
/*     */             public void operationComplete(ChannelFuture paramAnonymousChannelFuture) throws Exception {
/* 291 */               if (!paramAnonymousChannelFuture.isSuccess()) {
/* 292 */                 ChunkedWriteHandler.closeInput((ChunkedInput)localObject1);
/* 293 */                 localPendingWrite.fail(paramAnonymousChannelFuture.cause());
/*     */               } else {
/* 295 */                 localPendingWrite.progress(j);
/* 296 */                 if (localChannel.isWritable()) {
/* 297 */                   ChunkedWriteHandler.this.resumeTransfer();
/*     */                 }
/*     */               }
/*     */             }
/*     */           });
/*     */         }
/*     */       } else {
/* 304 */         paramChannelHandlerContext.write(localObject1, localPendingWrite.promise);
/* 305 */         this.currentWrite = null;
/*     */       }
/*     */       
/*     */ 
/* 309 */       paramChannelHandlerContext.flush();
/*     */       
/* 311 */       if (!localChannel.isActive()) {
/* 312 */         discard(new ClosedChannelException());
/* 313 */         return;
/*     */       }
/*     */     }
/*     */   }
/*     */   
/*     */   static void closeInput(ChunkedInput<?> paramChunkedInput) {
/*     */     try {
/* 320 */       paramChunkedInput.close();
/*     */     } catch (Throwable localThrowable) {
/* 322 */       if (logger.isWarnEnabled()) {
/* 323 */         logger.warn("Failed to close a chunked input.", localThrowable);
/*     */       }
/*     */     }
/*     */   }
/*     */   
/*     */   private static final class PendingWrite {
/*     */     final Object msg;
/*     */     final ChannelPromise promise;
/*     */     private long progress;
/*     */     
/*     */     PendingWrite(Object paramObject, ChannelPromise paramChannelPromise) {
/* 334 */       this.msg = paramObject;
/* 335 */       this.promise = paramChannelPromise;
/*     */     }
/*     */     
/*     */     void fail(Throwable paramThrowable) {
/* 339 */       ReferenceCountUtil.release(this.msg);
/* 340 */       this.promise.tryFailure(paramThrowable);
/*     */     }
/*     */     
/*     */     void success() {
/* 344 */       if (this.promise.isDone())
/*     */       {
/* 346 */         return;
/*     */       }
/*     */       
/* 349 */       if ((this.promise instanceof ChannelProgressivePromise))
/*     */       {
/* 351 */         ((ChannelProgressivePromise)this.promise).tryProgress(this.progress, this.progress);
/*     */       }
/*     */       
/* 354 */       this.promise.trySuccess();
/*     */     }
/*     */     
/*     */     void progress(int paramInt) {
/* 358 */       this.progress += paramInt;
/* 359 */       if ((this.promise instanceof ChannelProgressivePromise)) {
/* 360 */         ((ChannelProgressivePromise)this.promise).tryProgress(this.progress, -1L);
/*     */       }
/*     */     }
/*     */   }
/*     */   
/*     */   private static int amount(Object paramObject) {
/* 366 */     if ((paramObject instanceof ByteBuf)) {
/* 367 */       return ((ByteBuf)paramObject).readableBytes();
/*     */     }
/* 369 */     if ((paramObject instanceof ByteBufHolder)) {
/* 370 */       return ((ByteBufHolder)paramObject).content().readableBytes();
/*     */     }
/* 372 */     return 1;
/*     */   }
/*     */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\io\netty\handler\stream\ChunkedWriteHandler.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */
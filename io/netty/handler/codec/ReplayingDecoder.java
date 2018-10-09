/*     */ package io.netty.handler.codec;
/*     */ 
/*     */ import io.netty.buffer.ByteBuf;
/*     */ import io.netty.channel.ChannelHandlerContext;
/*     */ import io.netty.util.Signal;
/*     */ import io.netty.util.internal.RecyclableArrayList;
/*     */ import io.netty.util.internal.StringUtil;
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
/*     */ public abstract class ReplayingDecoder<S>
/*     */   extends ByteToMessageDecoder
/*     */ {
/* 270 */   static final Signal REPLAY = Signal.valueOf(ReplayingDecoder.class.getName() + ".REPLAY");
/*     */   
/* 272 */   private final ReplayingDecoderBuffer replayable = new ReplayingDecoderBuffer();
/*     */   private S state;
/* 274 */   private int checkpoint = -1;
/*     */   
/*     */ 
/*     */ 
/*     */   protected ReplayingDecoder()
/*     */   {
/* 280 */     this(null);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   protected ReplayingDecoder(S paramS)
/*     */   {
/* 287 */     this.state = paramS;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   protected void checkpoint()
/*     */   {
/* 294 */     this.checkpoint = internalBuffer().readerIndex();
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   protected void checkpoint(S paramS)
/*     */   {
/* 302 */     checkpoint();
/* 303 */     state(paramS);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   protected S state()
/*     */   {
/* 311 */     return (S)this.state;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   protected S state(S paramS)
/*     */   {
/* 319 */     Object localObject = this.state;
/* 320 */     this.state = paramS;
/* 321 */     return (S)localObject;
/*     */   }
/*     */   
/*     */   public void channelInactive(ChannelHandlerContext paramChannelHandlerContext) throws Exception
/*     */   {
/* 326 */     RecyclableArrayList localRecyclableArrayList = RecyclableArrayList.newInstance();
/*     */     try {
/* 328 */       this.replayable.terminate();
/* 329 */       callDecode(paramChannelHandlerContext, internalBuffer(), localRecyclableArrayList);
/* 330 */       decodeLast(paramChannelHandlerContext, this.replayable, localRecyclableArrayList);
/*     */     } catch (Signal localSignal) {
/*     */       int i;
/* 333 */       localSignal.expect(REPLAY); } catch (DecoderException localDecoderException) { int k;
/*     */       int j;
/* 335 */       throw localDecoderException;
/*     */     } catch (Exception localException) {
/* 337 */       throw new DecoderException(localException);
/*     */     } finally {
/*     */       try {
/* 340 */         if (this.cumulation != null) {
/* 341 */           this.cumulation.release();
/* 342 */           this.cumulation = null;
/*     */         }
/* 344 */         int m = localRecyclableArrayList.size();
/* 345 */         for (int n = 0; n < m; n++) {
/* 346 */           paramChannelHandlerContext.fireChannelRead(localRecyclableArrayList.get(n));
/*     */         }
/* 348 */         if (m > 0)
/*     */         {
/* 350 */           paramChannelHandlerContext.fireChannelReadComplete();
/*     */         }
/* 352 */         paramChannelHandlerContext.fireChannelInactive();
/*     */       }
/*     */       finally {
/* 355 */         localRecyclableArrayList.recycle();
/*     */       }
/*     */     }
/*     */   }
/*     */   
/*     */   protected void callDecode(ChannelHandlerContext paramChannelHandlerContext, ByteBuf paramByteBuf, List<Object> paramList)
/*     */   {
/* 362 */     this.replayable.setCumulation(paramByteBuf);
/*     */     try {
/* 364 */       while (paramByteBuf.isReadable()) {
/* 365 */         int i = this.checkpoint = paramByteBuf.readerIndex();
/* 366 */         int j = paramList.size();
/* 367 */         Object localObject = this.state;
/* 368 */         int k = paramByteBuf.readableBytes();
/*     */         try {
/* 370 */           decode(paramChannelHandlerContext, this.replayable, paramList);
/*     */           
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/* 376 */           if (paramChannelHandlerContext.isRemoved()) {
/*     */             break;
/*     */           }
/*     */           
/* 380 */           if (j == paramList.size()) {
/* 381 */             if ((k == paramByteBuf.readableBytes()) && (localObject == this.state)) {
/* 382 */               throw new DecoderException(StringUtil.simpleClassName(getClass()) + ".decode() must consume the inbound " + "data or change its state if it did not decode anything.");
/*     */             }
/*     */             
/*     */ 
/*     */ 
/*     */ 
/* 388 */             continue;
/*     */           }
/*     */         }
/*     */         catch (Signal localSignal) {
/* 392 */           localSignal.expect(REPLAY);
/*     */           
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/* 398 */           if (!paramChannelHandlerContext.isRemoved()) break label163; }
/* 399 */         break;
/*     */         
/*     */         label163:
/*     */         
/* 403 */         int m = this.checkpoint;
/* 404 */         if (m >= 0) {
/* 405 */           paramByteBuf.readerIndex(m);
/*     */         }
/*     */         
/*     */ 
/*     */ 
/* 410 */         break;
/*     */         
/*     */ 
/* 413 */         if ((i == paramByteBuf.readerIndex()) && (localObject == this.state)) {
/* 414 */           throw new DecoderException(StringUtil.simpleClassName(getClass()) + ".decode() method must consume the inbound data " + "or change its state if it decoded something.");
/*     */         }
/*     */         
/*     */ 
/* 418 */         if (isSingleDecode()) {
/*     */           break;
/*     */         }
/*     */       }
/*     */     } catch (DecoderException localDecoderException) {
/* 423 */       throw localDecoderException;
/*     */     } catch (Throwable localThrowable) {
/* 425 */       throw new DecoderException(localThrowable);
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\io\netty\handler\codec\ReplayingDecoder.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */
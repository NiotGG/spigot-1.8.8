/*     */ package io.netty.handler.codec;
/*     */ 
/*     */ import io.netty.channel.ChannelHandlerContext;
/*     */ import io.netty.channel.ChannelOutboundHandlerAdapter;
/*     */ import io.netty.channel.ChannelPromise;
/*     */ import io.netty.util.ReferenceCountUtil;
/*     */ import io.netty.util.internal.RecyclableArrayList;
/*     */ import io.netty.util.internal.StringUtil;
/*     */ import io.netty.util.internal.TypeParameterMatcher;
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
/*     */ public abstract class MessageToMessageEncoder<I>
/*     */   extends ChannelOutboundHandlerAdapter
/*     */ {
/*     */   private final TypeParameterMatcher matcher;
/*     */   
/*     */   protected MessageToMessageEncoder()
/*     */   {
/*  60 */     this.matcher = TypeParameterMatcher.find(this, MessageToMessageEncoder.class, "I");
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   protected MessageToMessageEncoder(Class<? extends I> paramClass)
/*     */   {
/*  69 */     this.matcher = TypeParameterMatcher.get(paramClass);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public boolean acceptOutboundMessage(Object paramObject)
/*     */     throws Exception
/*     */   {
/*  77 */     return this.matcher.match(paramObject);
/*     */   }
/*     */   
/*     */   public void write(ChannelHandlerContext paramChannelHandlerContext, Object paramObject, ChannelPromise paramChannelPromise) throws Exception
/*     */   {
/*  82 */     RecyclableArrayList localRecyclableArrayList = null;
/*     */     try {
/*  84 */       if (acceptOutboundMessage(paramObject)) {
/*  85 */         localRecyclableArrayList = RecyclableArrayList.newInstance();
/*     */         
/*  87 */         Object localObject1 = paramObject;
/*     */         try {
/*  89 */           encode(paramChannelHandlerContext, localObject1, localRecyclableArrayList);
/*     */         } finally {
/*  91 */           ReferenceCountUtil.release(localObject1);
/*     */         }
/*     */         
/*  94 */         if (localRecyclableArrayList.isEmpty()) {
/*  95 */           localRecyclableArrayList.recycle();
/*  96 */           localRecyclableArrayList = null;
/*     */           
/*  98 */           throw new EncoderException(StringUtil.simpleClassName(this) + " must produce at least one message.");
/*     */         }
/*     */       }
/*     */       else {
/* 102 */         paramChannelHandlerContext.write(paramObject, paramChannelPromise); } } catch (EncoderException localEncoderException) { int i;
/*     */       ChannelPromise localChannelPromise1;
/*     */       int j;
/* 105 */       int k; ChannelPromise localChannelPromise2; throw localEncoderException;
/*     */     } catch (Throwable localThrowable) {
/* 107 */       throw new EncoderException(localThrowable);
/*     */     } finally {
/* 109 */       if (localRecyclableArrayList != null) {
/* 110 */         int m = localRecyclableArrayList.size() - 1;
/* 111 */         if (m == 0) {
/* 112 */           paramChannelHandlerContext.write(localRecyclableArrayList.get(0), paramChannelPromise);
/* 113 */         } else if (m > 0)
/*     */         {
/*     */ 
/* 116 */           ChannelPromise localChannelPromise3 = paramChannelHandlerContext.voidPromise();
/* 117 */           int n = paramChannelPromise == localChannelPromise3 ? 1 : 0;
/* 118 */           for (int i1 = 0; i1 < m; i1++) {
/*     */             ChannelPromise localChannelPromise4;
/* 120 */             if (n != 0) {
/* 121 */               localChannelPromise4 = localChannelPromise3;
/*     */             } else {
/* 123 */               localChannelPromise4 = paramChannelHandlerContext.newPromise();
/*     */             }
/* 125 */             paramChannelHandlerContext.write(localRecyclableArrayList.get(i1), localChannelPromise4);
/*     */           }
/* 127 */           paramChannelHandlerContext.write(localRecyclableArrayList.get(m), paramChannelPromise);
/*     */         }
/* 129 */         localRecyclableArrayList.recycle();
/*     */       }
/*     */     }
/*     */   }
/*     */   
/*     */   protected abstract void encode(ChannelHandlerContext paramChannelHandlerContext, I paramI, List<Object> paramList)
/*     */     throws Exception;
/*     */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\io\netty\handler\codec\MessageToMessageEncoder.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */
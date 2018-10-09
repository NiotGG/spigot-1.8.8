/*     */ package io.netty.handler.codec;
/*     */ 
/*     */ import io.netty.channel.ChannelHandlerContext;
/*     */ import io.netty.channel.ChannelInboundHandlerAdapter;
/*     */ import io.netty.util.ReferenceCountUtil;
/*     */ import io.netty.util.internal.RecyclableArrayList;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ public abstract class MessageToMessageDecoder<I>
/*     */   extends ChannelInboundHandlerAdapter
/*     */ {
/*     */   private final TypeParameterMatcher matcher;
/*     */   
/*     */   protected MessageToMessageDecoder()
/*     */   {
/*  61 */     this.matcher = TypeParameterMatcher.find(this, MessageToMessageDecoder.class, "I");
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   protected MessageToMessageDecoder(Class<? extends I> paramClass)
/*     */   {
/*  70 */     this.matcher = TypeParameterMatcher.get(paramClass);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public boolean acceptInboundMessage(Object paramObject)
/*     */     throws Exception
/*     */   {
/*  78 */     return this.matcher.match(paramObject);
/*     */   }
/*     */   
/*     */   public void channelRead(ChannelHandlerContext paramChannelHandlerContext, Object paramObject) throws Exception
/*     */   {
/*  83 */     RecyclableArrayList localRecyclableArrayList = RecyclableArrayList.newInstance();
/*     */     try {
/*  85 */       if (acceptInboundMessage(paramObject))
/*     */       {
/*  87 */         Object localObject1 = paramObject;
/*     */         try {
/*  89 */           decode(paramChannelHandlerContext, localObject1, localRecyclableArrayList);
/*     */         } finally {
/*  91 */           ReferenceCountUtil.release(localObject1);
/*     */         }
/*     */       } else {
/*  94 */         localRecyclableArrayList.add(paramObject);
/*     */       } } catch (DecoderException localDecoderException) { int i;
/*     */       int j;
/*  97 */       throw localDecoderException;
/*     */     } catch (Exception localException) {
/*  99 */       throw new DecoderException(localException);
/*     */     } finally {
/* 101 */       int k = localRecyclableArrayList.size();
/* 102 */       for (int m = 0; m < k; m++) {
/* 103 */         paramChannelHandlerContext.fireChannelRead(localRecyclableArrayList.get(m));
/*     */       }
/* 105 */       localRecyclableArrayList.recycle();
/*     */     }
/*     */   }
/*     */   
/*     */   protected abstract void decode(ChannelHandlerContext paramChannelHandlerContext, I paramI, List<Object> paramList)
/*     */     throws Exception;
/*     */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\io\netty\handler\codec\MessageToMessageDecoder.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */
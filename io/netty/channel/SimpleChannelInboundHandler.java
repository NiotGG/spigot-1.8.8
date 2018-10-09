/*     */ package io.netty.channel;
/*     */ 
/*     */ import io.netty.util.ReferenceCountUtil;
/*     */ import io.netty.util.internal.TypeParameterMatcher;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public abstract class SimpleChannelInboundHandler<I>
/*     */   extends ChannelInboundHandlerAdapter
/*     */ {
/*     */   private final TypeParameterMatcher matcher;
/*     */   private final boolean autoRelease;
/*     */   
/*     */   protected SimpleChannelInboundHandler()
/*     */   {
/*  57 */     this(true);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   protected SimpleChannelInboundHandler(boolean paramBoolean)
/*     */   {
/*  67 */     this.matcher = TypeParameterMatcher.find(this, SimpleChannelInboundHandler.class, "I");
/*  68 */     this.autoRelease = paramBoolean;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   protected SimpleChannelInboundHandler(Class<? extends I> paramClass)
/*     */   {
/*  75 */     this(paramClass, true);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   protected SimpleChannelInboundHandler(Class<? extends I> paramClass, boolean paramBoolean)
/*     */   {
/*  86 */     this.matcher = TypeParameterMatcher.get(paramClass);
/*  87 */     this.autoRelease = paramBoolean;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public boolean acceptInboundMessage(Object paramObject)
/*     */     throws Exception
/*     */   {
/*  95 */     return this.matcher.match(paramObject);
/*     */   }
/*     */   
/*     */   public void channelRead(ChannelHandlerContext paramChannelHandlerContext, Object paramObject) throws Exception
/*     */   {
/* 100 */     int i = 1;
/*     */     try {
/* 102 */       if (acceptInboundMessage(paramObject))
/*     */       {
/* 104 */         Object localObject1 = paramObject;
/* 105 */         channelRead0(paramChannelHandlerContext, localObject1);
/*     */       } else {
/* 107 */         i = 0;
/* 108 */         paramChannelHandlerContext.fireChannelRead(paramObject);
/*     */       }
/*     */     } finally {
/* 111 */       if ((this.autoRelease) && (i != 0)) {
/* 112 */         ReferenceCountUtil.release(paramObject);
/*     */       }
/*     */     }
/*     */   }
/*     */   
/*     */   protected abstract void channelRead0(ChannelHandlerContext paramChannelHandlerContext, I paramI)
/*     */     throws Exception;
/*     */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\io\netty\channel\SimpleChannelInboundHandler.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */
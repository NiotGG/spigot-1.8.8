/*    */ package io.netty.channel;
/*    */ 
/*    */ import java.util.LinkedHashSet;
/*    */ import java.util.Set;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public final class ChannelPromiseAggregator
/*    */   implements ChannelFutureListener
/*    */ {
/*    */   private final ChannelPromise aggregatePromise;
/*    */   private Set<ChannelPromise> pendingPromises;
/*    */   
/*    */   public ChannelPromiseAggregator(ChannelPromise paramChannelPromise)
/*    */   {
/* 39 */     if (paramChannelPromise == null) {
/* 40 */       throw new NullPointerException("aggregatePromise");
/*    */     }
/* 42 */     this.aggregatePromise = paramChannelPromise;
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */   public ChannelPromiseAggregator add(ChannelPromise... paramVarArgs)
/*    */   {
/* 49 */     if (paramVarArgs == null) {
/* 50 */       throw new NullPointerException("promises");
/*    */     }
/* 52 */     if (paramVarArgs.length == 0) {
/* 53 */       return this;
/*    */     }
/* 55 */     synchronized (this) {
/* 56 */       if (this.pendingPromises == null) {
/*    */         int i;
/* 58 */         if (paramVarArgs.length > 1) {
/* 59 */           i = paramVarArgs.length;
/*    */         } else {
/* 61 */           i = 2;
/*    */         }
/* 63 */         this.pendingPromises = new LinkedHashSet(i);
/*    */       }
/* 65 */       for (ChannelPromise localChannelPromise : paramVarArgs)
/* 66 */         if (localChannelPromise != null)
/*    */         {
/*    */ 
/* 69 */           this.pendingPromises.add(localChannelPromise);
/* 70 */           localChannelPromise.addListener(this);
/*    */         }
/*    */     }
/* 73 */     return this;
/*    */   }
/*    */   
/*    */   public synchronized void operationComplete(ChannelFuture paramChannelFuture) throws Exception
/*    */   {
/* 78 */     if (this.pendingPromises == null) {
/* 79 */       this.aggregatePromise.setSuccess();
/*    */     } else {
/* 81 */       this.pendingPromises.remove(paramChannelFuture);
/* 82 */       if (!paramChannelFuture.isSuccess()) {
/* 83 */         this.aggregatePromise.setFailure(paramChannelFuture.cause());
/* 84 */         for (ChannelPromise localChannelPromise : this.pendingPromises) {
/* 85 */           localChannelPromise.setFailure(paramChannelFuture.cause());
/*    */         }
/*    */       }
/* 88 */       else if (this.pendingPromises.isEmpty()) {
/* 89 */         this.aggregatePromise.setSuccess();
/*    */       }
/*    */     }
/*    */   }
/*    */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\io\netty\channel\ChannelPromiseAggregator.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */
/*    */ package io.netty.channel;
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
/*    */ public final class ChannelPromiseNotifier
/*    */   implements ChannelFutureListener
/*    */ {
/*    */   private final ChannelPromise[] promises;
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
/*    */   public ChannelPromiseNotifier(ChannelPromise... paramVarArgs)
/*    */   {
/* 31 */     if (paramVarArgs == null) {
/* 32 */       throw new NullPointerException("promises");
/*    */     }
/* 34 */     for (ChannelPromise localChannelPromise : paramVarArgs) {
/* 35 */       if (localChannelPromise == null) {
/* 36 */         throw new IllegalArgumentException("promises contains null ChannelPromise");
/*    */       }
/*    */     }
/* 39 */     this.promises = ((ChannelPromise[])paramVarArgs.clone());
/*    */   }
/*    */   
/*    */   public void operationComplete(ChannelFuture paramChannelFuture) throws Exception
/*    */   {
/* 44 */     if (paramChannelFuture.isSuccess()) {
/* 45 */       for (Object localObject2 : this.promises) {
/* 46 */         ((ChannelPromise)localObject2).setSuccess();
/*    */       }
/* 48 */       return;
/*    */     }
/*    */     
/* 51 */     ??? = paramChannelFuture.cause();
/* 52 */     for (ChannelPromise localChannelPromise : this.promises) {
/* 53 */       localChannelPromise.setFailure((Throwable)???);
/*    */     }
/*    */   }
/*    */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\io\netty\channel\ChannelPromiseNotifier.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */
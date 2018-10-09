/*    */ package io.netty.channel;
/*    */ 
/*    */ import io.netty.buffer.ByteBuf;
/*    */ import io.netty.buffer.ByteBufHolder;
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
/*    */ public final class DefaultMessageSizeEstimator
/*    */   implements MessageSizeEstimator
/*    */ {
/*    */   private static final class HandleImpl
/*    */     implements MessageSizeEstimator.Handle
/*    */   {
/*    */     private final int unknownSize;
/*    */     
/*    */     private HandleImpl(int paramInt)
/*    */     {
/* 31 */       this.unknownSize = paramInt;
/*    */     }
/*    */     
/*    */     public int size(Object paramObject)
/*    */     {
/* 36 */       if ((paramObject instanceof ByteBuf)) {
/* 37 */         return ((ByteBuf)paramObject).readableBytes();
/*    */       }
/* 39 */       if ((paramObject instanceof ByteBufHolder)) {
/* 40 */         return ((ByteBufHolder)paramObject).content().readableBytes();
/*    */       }
/* 42 */       if ((paramObject instanceof FileRegion)) {
/* 43 */         return 0;
/*    */       }
/* 45 */       return this.unknownSize;
/*    */     }
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */ 
/* 52 */   public static final MessageSizeEstimator DEFAULT = new DefaultMessageSizeEstimator(0);
/*    */   
/*    */ 
/*    */ 
/*    */   private final MessageSizeEstimator.Handle handle;
/*    */   
/*    */ 
/*    */ 
/*    */   public DefaultMessageSizeEstimator(int paramInt)
/*    */   {
/* 62 */     if (paramInt < 0) {
/* 63 */       throw new IllegalArgumentException("unknownSize: " + paramInt + " (expected: >= 0)");
/*    */     }
/* 65 */     this.handle = new HandleImpl(paramInt, null);
/*    */   }
/*    */   
/*    */   public MessageSizeEstimator.Handle newHandle()
/*    */   {
/* 70 */     return this.handle;
/*    */   }
/*    */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\io\netty\channel\DefaultMessageSizeEstimator.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */
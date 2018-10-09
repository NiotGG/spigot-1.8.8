/*    */ package io.netty.channel;
/*    */ 
/*    */ import io.netty.buffer.ByteBuf;
/*    */ import io.netty.buffer.ByteBufAllocator;
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
/*    */ public class FixedRecvByteBufAllocator
/*    */   implements RecvByteBufAllocator
/*    */ {
/*    */   private final RecvByteBufAllocator.Handle handle;
/*    */   
/*    */   private static final class HandleImpl
/*    */     implements RecvByteBufAllocator.Handle
/*    */   {
/*    */     private final int bufferSize;
/*    */     
/*    */     HandleImpl(int paramInt)
/*    */     {
/* 33 */       this.bufferSize = paramInt;
/*    */     }
/*    */     
/*    */     public ByteBuf allocate(ByteBufAllocator paramByteBufAllocator)
/*    */     {
/* 38 */       return paramByteBufAllocator.ioBuffer(this.bufferSize);
/*    */     }
/*    */     
/*    */     public int guess()
/*    */     {
/* 43 */       return this.bufferSize;
/*    */     }
/*    */     
/*    */ 
/*    */ 
/*    */ 
/*    */     public void record(int paramInt) {}
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */   public FixedRecvByteBufAllocator(int paramInt)
/*    */   {
/* 57 */     if (paramInt <= 0) {
/* 58 */       throw new IllegalArgumentException("bufferSize must greater than 0: " + paramInt);
/*    */     }
/*    */     
/*    */ 
/* 62 */     this.handle = new HandleImpl(paramInt);
/*    */   }
/*    */   
/*    */   public RecvByteBufAllocator.Handle newHandle()
/*    */   {
/* 67 */     return this.handle;
/*    */   }
/*    */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\io\netty\channel\FixedRecvByteBufAllocator.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */
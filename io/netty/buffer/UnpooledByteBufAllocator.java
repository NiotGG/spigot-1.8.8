/*    */ package io.netty.buffer;
/*    */ 
/*    */ import io.netty.util.internal.PlatformDependent;
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
/*    */ public final class UnpooledByteBufAllocator
/*    */   extends AbstractByteBufAllocator
/*    */ {
/* 28 */   public static final UnpooledByteBufAllocator DEFAULT = new UnpooledByteBufAllocator(PlatformDependent.directBufferPreferred());
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   public UnpooledByteBufAllocator(boolean paramBoolean)
/*    */   {
/* 38 */     super(paramBoolean);
/*    */   }
/*    */   
/*    */   protected ByteBuf newHeapBuffer(int paramInt1, int paramInt2)
/*    */   {
/* 43 */     return new UnpooledHeapByteBuf(this, paramInt1, paramInt2);
/*    */   }
/*    */   
/*    */   protected ByteBuf newDirectBuffer(int paramInt1, int paramInt2)
/*    */   {
/*    */     Object localObject;
/* 49 */     if (PlatformDependent.hasUnsafe()) {
/* 50 */       localObject = new UnpooledUnsafeDirectByteBuf(this, paramInt1, paramInt2);
/*    */     } else {
/* 52 */       localObject = new UnpooledDirectByteBuf(this, paramInt1, paramInt2);
/*    */     }
/*    */     
/* 55 */     return toLeakAwareBuffer((ByteBuf)localObject);
/*    */   }
/*    */   
/*    */   public boolean isDirectBufferPooled()
/*    */   {
/* 60 */     return false;
/*    */   }
/*    */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\io\netty\buffer\UnpooledByteBufAllocator.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */
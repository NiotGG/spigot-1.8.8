/*    */ package io.netty.buffer;
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
/*    */ public abstract interface ByteBufAllocator
/*    */ {
/* 24 */   public static final ByteBufAllocator DEFAULT = ByteBufUtil.DEFAULT_ALLOCATOR;
/*    */   
/*    */   public abstract ByteBuf buffer();
/*    */   
/*    */   public abstract ByteBuf buffer(int paramInt);
/*    */   
/*    */   public abstract ByteBuf buffer(int paramInt1, int paramInt2);
/*    */   
/*    */   public abstract ByteBuf ioBuffer();
/*    */   
/*    */   public abstract ByteBuf ioBuffer(int paramInt);
/*    */   
/*    */   public abstract ByteBuf ioBuffer(int paramInt1, int paramInt2);
/*    */   
/*    */   public abstract ByteBuf heapBuffer();
/*    */   
/*    */   public abstract ByteBuf heapBuffer(int paramInt);
/*    */   
/*    */   public abstract ByteBuf heapBuffer(int paramInt1, int paramInt2);
/*    */   
/*    */   public abstract ByteBuf directBuffer();
/*    */   
/*    */   public abstract ByteBuf directBuffer(int paramInt);
/*    */   
/*    */   public abstract ByteBuf directBuffer(int paramInt1, int paramInt2);
/*    */   
/*    */   public abstract CompositeByteBuf compositeBuffer();
/*    */   
/*    */   public abstract CompositeByteBuf compositeBuffer(int paramInt);
/*    */   
/*    */   public abstract CompositeByteBuf compositeHeapBuffer();
/*    */   
/*    */   public abstract CompositeByteBuf compositeHeapBuffer(int paramInt);
/*    */   
/*    */   public abstract CompositeByteBuf compositeDirectBuffer();
/*    */   
/*    */   public abstract CompositeByteBuf compositeDirectBuffer(int paramInt);
/*    */   
/*    */   public abstract boolean isDirectBufferPooled();
/*    */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\io\netty\buffer\ByteBufAllocator.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */
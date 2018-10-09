/*    */ package io.netty.buffer;
/*    */ 
/*    */ import java.nio.ByteBuffer;
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
/*    */ public abstract class AbstractDerivedByteBuf
/*    */   extends AbstractByteBuf
/*    */ {
/*    */   protected AbstractDerivedByteBuf(int paramInt)
/*    */   {
/* 28 */     super(paramInt);
/*    */   }
/*    */   
/*    */   public final int refCnt()
/*    */   {
/* 33 */     return unwrap().refCnt();
/*    */   }
/*    */   
/*    */   public final ByteBuf retain()
/*    */   {
/* 38 */     unwrap().retain();
/* 39 */     return this;
/*    */   }
/*    */   
/*    */   public final ByteBuf retain(int paramInt)
/*    */   {
/* 44 */     unwrap().retain(paramInt);
/* 45 */     return this;
/*    */   }
/*    */   
/*    */   public final boolean release()
/*    */   {
/* 50 */     return unwrap().release();
/*    */   }
/*    */   
/*    */   public final boolean release(int paramInt)
/*    */   {
/* 55 */     return unwrap().release(paramInt);
/*    */   }
/*    */   
/*    */   public ByteBuffer internalNioBuffer(int paramInt1, int paramInt2)
/*    */   {
/* 60 */     return nioBuffer(paramInt1, paramInt2);
/*    */   }
/*    */   
/*    */   public ByteBuffer nioBuffer(int paramInt1, int paramInt2)
/*    */   {
/* 65 */     return unwrap().nioBuffer(paramInt1, paramInt2);
/*    */   }
/*    */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\io\netty\buffer\AbstractDerivedByteBuf.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */
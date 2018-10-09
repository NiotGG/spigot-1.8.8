/*    */ package io.netty.buffer;
/*    */ 
/*    */ import io.netty.util.ResourceLeak;
/*    */ import java.nio.ByteOrder;
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
/*    */ final class SimpleLeakAwareByteBuf
/*    */   extends WrappedByteBuf
/*    */ {
/*    */   private final ResourceLeak leak;
/*    */   
/*    */   SimpleLeakAwareByteBuf(ByteBuf paramByteBuf, ResourceLeak paramResourceLeak)
/*    */   {
/* 28 */     super(paramByteBuf);
/* 29 */     this.leak = paramResourceLeak;
/*    */   }
/*    */   
/*    */   public boolean release()
/*    */   {
/* 34 */     boolean bool = super.release();
/* 35 */     if (bool) {
/* 36 */       this.leak.close();
/*    */     }
/* 38 */     return bool;
/*    */   }
/*    */   
/*    */   public boolean release(int paramInt)
/*    */   {
/* 43 */     boolean bool = super.release(paramInt);
/* 44 */     if (bool) {
/* 45 */       this.leak.close();
/*    */     }
/* 47 */     return bool;
/*    */   }
/*    */   
/*    */   public ByteBuf order(ByteOrder paramByteOrder)
/*    */   {
/* 52 */     this.leak.record();
/* 53 */     if (order() == paramByteOrder) {
/* 54 */       return this;
/*    */     }
/* 56 */     return new SimpleLeakAwareByteBuf(super.order(paramByteOrder), this.leak);
/*    */   }
/*    */   
/*    */ 
/*    */   public ByteBuf slice()
/*    */   {
/* 62 */     return new SimpleLeakAwareByteBuf(super.slice(), this.leak);
/*    */   }
/*    */   
/*    */   public ByteBuf slice(int paramInt1, int paramInt2)
/*    */   {
/* 67 */     return new SimpleLeakAwareByteBuf(super.slice(paramInt1, paramInt2), this.leak);
/*    */   }
/*    */   
/*    */   public ByteBuf duplicate()
/*    */   {
/* 72 */     return new SimpleLeakAwareByteBuf(super.duplicate(), this.leak);
/*    */   }
/*    */   
/*    */   public ByteBuf readSlice(int paramInt)
/*    */   {
/* 77 */     return new SimpleLeakAwareByteBuf(super.readSlice(paramInt), this.leak);
/*    */   }
/*    */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\io\netty\buffer\SimpleLeakAwareByteBuf.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */
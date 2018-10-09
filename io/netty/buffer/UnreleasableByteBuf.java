/*    */ package io.netty.buffer;
/*    */ 
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
/*    */ 
/*    */ 
/*    */ final class UnreleasableByteBuf
/*    */   extends WrappedByteBuf
/*    */ {
/*    */   private SwappedByteBuf swappedBuf;
/*    */   
/*    */   UnreleasableByteBuf(ByteBuf paramByteBuf)
/*    */   {
/* 29 */     super(paramByteBuf);
/*    */   }
/*    */   
/*    */   public ByteBuf order(ByteOrder paramByteOrder)
/*    */   {
/* 34 */     if (paramByteOrder == null) {
/* 35 */       throw new NullPointerException("endianness");
/*    */     }
/* 37 */     if (paramByteOrder == order()) {
/* 38 */       return this;
/*    */     }
/*    */     
/* 41 */     SwappedByteBuf localSwappedByteBuf = this.swappedBuf;
/* 42 */     if (localSwappedByteBuf == null) {
/* 43 */       this.swappedBuf = (localSwappedByteBuf = new SwappedByteBuf(this));
/*    */     }
/* 45 */     return localSwappedByteBuf;
/*    */   }
/*    */   
/*    */   public ByteBuf readSlice(int paramInt)
/*    */   {
/* 50 */     return new UnreleasableByteBuf(this.buf.readSlice(paramInt));
/*    */   }
/*    */   
/*    */   public ByteBuf slice()
/*    */   {
/* 55 */     return new UnreleasableByteBuf(this.buf.slice());
/*    */   }
/*    */   
/*    */   public ByteBuf slice(int paramInt1, int paramInt2)
/*    */   {
/* 60 */     return new UnreleasableByteBuf(this.buf.slice(paramInt1, paramInt2));
/*    */   }
/*    */   
/*    */   public ByteBuf duplicate()
/*    */   {
/* 65 */     return new UnreleasableByteBuf(this.buf.duplicate());
/*    */   }
/*    */   
/*    */   public ByteBuf retain(int paramInt)
/*    */   {
/* 70 */     return this;
/*    */   }
/*    */   
/*    */   public ByteBuf retain()
/*    */   {
/* 75 */     return this;
/*    */   }
/*    */   
/*    */   public boolean release()
/*    */   {
/* 80 */     return false;
/*    */   }
/*    */   
/*    */   public boolean release(int paramInt)
/*    */   {
/* 85 */     return false;
/*    */   }
/*    */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\io\netty\buffer\UnreleasableByteBuf.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */
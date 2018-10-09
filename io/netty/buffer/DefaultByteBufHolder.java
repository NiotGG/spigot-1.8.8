/*    */ package io.netty.buffer;
/*    */ 
/*    */ import io.netty.util.IllegalReferenceCountException;
/*    */ import io.netty.util.internal.StringUtil;
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
/*    */ public class DefaultByteBufHolder
/*    */   implements ByteBufHolder
/*    */ {
/*    */   private final ByteBuf data;
/*    */   
/*    */   public DefaultByteBufHolder(ByteBuf paramByteBuf)
/*    */   {
/* 30 */     if (paramByteBuf == null) {
/* 31 */       throw new NullPointerException("data");
/*    */     }
/* 33 */     this.data = paramByteBuf;
/*    */   }
/*    */   
/*    */   public ByteBuf content()
/*    */   {
/* 38 */     if (this.data.refCnt() <= 0) {
/* 39 */       throw new IllegalReferenceCountException(this.data.refCnt());
/*    */     }
/* 41 */     return this.data;
/*    */   }
/*    */   
/*    */   public ByteBufHolder copy()
/*    */   {
/* 46 */     return new DefaultByteBufHolder(this.data.copy());
/*    */   }
/*    */   
/*    */   public ByteBufHolder duplicate()
/*    */   {
/* 51 */     return new DefaultByteBufHolder(this.data.duplicate());
/*    */   }
/*    */   
/*    */   public int refCnt()
/*    */   {
/* 56 */     return this.data.refCnt();
/*    */   }
/*    */   
/*    */   public ByteBufHolder retain()
/*    */   {
/* 61 */     this.data.retain();
/* 62 */     return this;
/*    */   }
/*    */   
/*    */   public ByteBufHolder retain(int paramInt)
/*    */   {
/* 67 */     this.data.retain(paramInt);
/* 68 */     return this;
/*    */   }
/*    */   
/*    */   public boolean release()
/*    */   {
/* 73 */     return this.data.release();
/*    */   }
/*    */   
/*    */   public boolean release(int paramInt)
/*    */   {
/* 78 */     return this.data.release(paramInt);
/*    */   }
/*    */   
/*    */   public String toString()
/*    */   {
/* 83 */     return StringUtil.simpleClassName(this) + '(' + content().toString() + ')';
/*    */   }
/*    */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\io\netty\buffer\DefaultByteBufHolder.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */
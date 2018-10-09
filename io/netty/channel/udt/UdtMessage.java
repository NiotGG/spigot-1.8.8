/*    */ package io.netty.channel.udt;
/*    */ 
/*    */ import io.netty.buffer.ByteBuf;
/*    */ import io.netty.buffer.DefaultByteBufHolder;
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
/*    */ public final class UdtMessage
/*    */   extends DefaultByteBufHolder
/*    */ {
/*    */   public UdtMessage(ByteBuf paramByteBuf)
/*    */   {
/* 31 */     super(paramByteBuf);
/*    */   }
/*    */   
/*    */   public UdtMessage copy()
/*    */   {
/* 36 */     return new UdtMessage(content().copy());
/*    */   }
/*    */   
/*    */   public UdtMessage duplicate()
/*    */   {
/* 41 */     return new UdtMessage(content().duplicate());
/*    */   }
/*    */   
/*    */   public UdtMessage retain()
/*    */   {
/* 46 */     super.retain();
/* 47 */     return this;
/*    */   }
/*    */   
/*    */   public UdtMessage retain(int paramInt)
/*    */   {
/* 52 */     super.retain(paramInt);
/* 53 */     return this;
/*    */   }
/*    */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\io\netty\channel\udt\UdtMessage.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */
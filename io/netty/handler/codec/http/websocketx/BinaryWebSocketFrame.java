/*    */ package io.netty.handler.codec.http.websocketx;
/*    */ 
/*    */ import io.netty.buffer.ByteBuf;
/*    */ import io.netty.buffer.Unpooled;
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
/*    */ public class BinaryWebSocketFrame
/*    */   extends WebSocketFrame
/*    */ {
/*    */   public BinaryWebSocketFrame()
/*    */   {
/* 30 */     super(Unpooled.buffer(0));
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   public BinaryWebSocketFrame(ByteBuf paramByteBuf)
/*    */   {
/* 40 */     super(paramByteBuf);
/*    */   }
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
/*    */   public BinaryWebSocketFrame(boolean paramBoolean, int paramInt, ByteBuf paramByteBuf)
/*    */   {
/* 54 */     super(paramBoolean, paramInt, paramByteBuf);
/*    */   }
/*    */   
/*    */   public BinaryWebSocketFrame copy()
/*    */   {
/* 59 */     return new BinaryWebSocketFrame(isFinalFragment(), rsv(), content().copy());
/*    */   }
/*    */   
/*    */   public BinaryWebSocketFrame duplicate()
/*    */   {
/* 64 */     return new BinaryWebSocketFrame(isFinalFragment(), rsv(), content().duplicate());
/*    */   }
/*    */   
/*    */   public BinaryWebSocketFrame retain()
/*    */   {
/* 69 */     super.retain();
/* 70 */     return this;
/*    */   }
/*    */   
/*    */   public BinaryWebSocketFrame retain(int paramInt)
/*    */   {
/* 75 */     super.retain(paramInt);
/* 76 */     return this;
/*    */   }
/*    */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\io\netty\handler\codec\http\websocketx\BinaryWebSocketFrame.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */
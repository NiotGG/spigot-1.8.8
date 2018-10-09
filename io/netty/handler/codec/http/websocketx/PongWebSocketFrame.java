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
/*    */ public class PongWebSocketFrame
/*    */   extends WebSocketFrame
/*    */ {
/*    */   public PongWebSocketFrame()
/*    */   {
/* 30 */     super(Unpooled.buffer(0));
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   public PongWebSocketFrame(ByteBuf paramByteBuf)
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
/*    */   public PongWebSocketFrame(boolean paramBoolean, int paramInt, ByteBuf paramByteBuf)
/*    */   {
/* 54 */     super(paramBoolean, paramInt, paramByteBuf);
/*    */   }
/*    */   
/*    */   public PongWebSocketFrame copy()
/*    */   {
/* 59 */     return new PongWebSocketFrame(isFinalFragment(), rsv(), content().copy());
/*    */   }
/*    */   
/*    */   public PongWebSocketFrame duplicate()
/*    */   {
/* 64 */     return new PongWebSocketFrame(isFinalFragment(), rsv(), content().duplicate());
/*    */   }
/*    */   
/*    */   public PongWebSocketFrame retain()
/*    */   {
/* 69 */     super.retain();
/* 70 */     return this;
/*    */   }
/*    */   
/*    */   public PongWebSocketFrame retain(int paramInt)
/*    */   {
/* 75 */     super.retain(paramInt);
/* 76 */     return this;
/*    */   }
/*    */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\io\netty\handler\codec\http\websocketx\PongWebSocketFrame.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */
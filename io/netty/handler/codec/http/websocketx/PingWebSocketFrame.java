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
/*    */ public class PingWebSocketFrame
/*    */   extends WebSocketFrame
/*    */ {
/*    */   public PingWebSocketFrame()
/*    */   {
/* 30 */     super(true, 0, Unpooled.buffer(0));
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   public PingWebSocketFrame(ByteBuf paramByteBuf)
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
/*    */   public PingWebSocketFrame(boolean paramBoolean, int paramInt, ByteBuf paramByteBuf)
/*    */   {
/* 54 */     super(paramBoolean, paramInt, paramByteBuf);
/*    */   }
/*    */   
/*    */   public PingWebSocketFrame copy()
/*    */   {
/* 59 */     return new PingWebSocketFrame(isFinalFragment(), rsv(), content().copy());
/*    */   }
/*    */   
/*    */   public PingWebSocketFrame duplicate()
/*    */   {
/* 64 */     return new PingWebSocketFrame(isFinalFragment(), rsv(), content().duplicate());
/*    */   }
/*    */   
/*    */   public PingWebSocketFrame retain()
/*    */   {
/* 69 */     super.retain();
/* 70 */     return this;
/*    */   }
/*    */   
/*    */   public PingWebSocketFrame retain(int paramInt)
/*    */   {
/* 75 */     super.retain(paramInt);
/* 76 */     return this;
/*    */   }
/*    */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\io\netty\handler\codec\http\websocketx\PingWebSocketFrame.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */
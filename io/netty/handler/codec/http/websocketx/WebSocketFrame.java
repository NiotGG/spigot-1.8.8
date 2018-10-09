/*    */ package io.netty.handler.codec.http.websocketx;
/*    */ 
/*    */ import io.netty.buffer.ByteBuf;
/*    */ import io.netty.buffer.DefaultByteBufHolder;
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
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public abstract class WebSocketFrame
/*    */   extends DefaultByteBufHolder
/*    */ {
/*    */   private final boolean finalFragment;
/*    */   private final int rsv;
/*    */   
/*    */   protected WebSocketFrame(ByteBuf paramByteBuf)
/*    */   {
/* 39 */     this(true, 0, paramByteBuf);
/*    */   }
/*    */   
/*    */   protected WebSocketFrame(boolean paramBoolean, int paramInt, ByteBuf paramByteBuf) {
/* 43 */     super(paramByteBuf);
/* 44 */     this.finalFragment = paramBoolean;
/* 45 */     this.rsv = paramInt;
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */   public boolean isFinalFragment()
/*    */   {
/* 53 */     return this.finalFragment;
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */   public int rsv()
/*    */   {
/* 60 */     return this.rsv;
/*    */   }
/*    */   
/*    */ 
/*    */   public abstract WebSocketFrame copy();
/*    */   
/*    */ 
/*    */   public abstract WebSocketFrame duplicate();
/*    */   
/*    */   public String toString()
/*    */   {
/* 71 */     return StringUtil.simpleClassName(this) + "(data: " + content().toString() + ')';
/*    */   }
/*    */   
/*    */   public WebSocketFrame retain()
/*    */   {
/* 76 */     super.retain();
/* 77 */     return this;
/*    */   }
/*    */   
/*    */   public WebSocketFrame retain(int paramInt)
/*    */   {
/* 82 */     super.retain(paramInt);
/* 83 */     return this;
/*    */   }
/*    */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\io\netty\handler\codec\http\websocketx\WebSocketFrame.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */
/*    */ package io.netty.handler.codec.spdy;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public abstract class DefaultSpdyStreamFrame
/*    */   implements SpdyStreamFrame
/*    */ {
/*    */   private int streamId;
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   private boolean last;
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   protected DefaultSpdyStreamFrame(int paramInt)
/*    */   {
/* 32 */     setStreamId(paramInt);
/*    */   }
/*    */   
/*    */   public int streamId()
/*    */   {
/* 37 */     return this.streamId;
/*    */   }
/*    */   
/*    */   public SpdyStreamFrame setStreamId(int paramInt)
/*    */   {
/* 42 */     if (paramInt <= 0) {
/* 43 */       throw new IllegalArgumentException("Stream-ID must be positive: " + paramInt);
/*    */     }
/*    */     
/* 46 */     this.streamId = paramInt;
/* 47 */     return this;
/*    */   }
/*    */   
/*    */   public boolean isLast()
/*    */   {
/* 52 */     return this.last;
/*    */   }
/*    */   
/*    */   public SpdyStreamFrame setLast(boolean paramBoolean)
/*    */   {
/* 57 */     this.last = paramBoolean;
/* 58 */     return this;
/*    */   }
/*    */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\io\netty\handler\codec\spdy\DefaultSpdyStreamFrame.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */
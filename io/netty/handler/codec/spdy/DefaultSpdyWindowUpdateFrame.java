/*    */ package io.netty.handler.codec.spdy;
/*    */ 
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
/*    */ public class DefaultSpdyWindowUpdateFrame
/*    */   implements SpdyWindowUpdateFrame
/*    */ {
/*    */   private int streamId;
/*    */   private int deltaWindowSize;
/*    */   
/*    */   public DefaultSpdyWindowUpdateFrame(int paramInt1, int paramInt2)
/*    */   {
/* 35 */     setStreamId(paramInt1);
/* 36 */     setDeltaWindowSize(paramInt2);
/*    */   }
/*    */   
/*    */   public int streamId()
/*    */   {
/* 41 */     return this.streamId;
/*    */   }
/*    */   
/*    */   public SpdyWindowUpdateFrame setStreamId(int paramInt)
/*    */   {
/* 46 */     if (paramInt < 0) {
/* 47 */       throw new IllegalArgumentException("Stream-ID cannot be negative: " + paramInt);
/*    */     }
/*    */     
/* 50 */     this.streamId = paramInt;
/* 51 */     return this;
/*    */   }
/*    */   
/*    */   public int deltaWindowSize()
/*    */   {
/* 56 */     return this.deltaWindowSize;
/*    */   }
/*    */   
/*    */   public SpdyWindowUpdateFrame setDeltaWindowSize(int paramInt)
/*    */   {
/* 61 */     if (paramInt <= 0) {
/* 62 */       throw new IllegalArgumentException("Delta-Window-Size must be positive: " + paramInt);
/*    */     }
/*    */     
/*    */ 
/* 66 */     this.deltaWindowSize = paramInt;
/* 67 */     return this;
/*    */   }
/*    */   
/*    */   public String toString()
/*    */   {
/* 72 */     StringBuilder localStringBuilder = new StringBuilder();
/* 73 */     localStringBuilder.append(StringUtil.simpleClassName(this));
/* 74 */     localStringBuilder.append(StringUtil.NEWLINE);
/* 75 */     localStringBuilder.append("--> Stream-ID = ");
/* 76 */     localStringBuilder.append(streamId());
/* 77 */     localStringBuilder.append(StringUtil.NEWLINE);
/* 78 */     localStringBuilder.append("--> Delta-Window-Size = ");
/* 79 */     localStringBuilder.append(deltaWindowSize());
/* 80 */     return localStringBuilder.toString();
/*    */   }
/*    */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\io\netty\handler\codec\spdy\DefaultSpdyWindowUpdateFrame.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */
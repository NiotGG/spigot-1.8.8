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
/*    */ public class DefaultSpdyGoAwayFrame
/*    */   implements SpdyGoAwayFrame
/*    */ {
/*    */   private int lastGoodStreamId;
/*    */   private SpdySessionStatus status;
/*    */   
/*    */   public DefaultSpdyGoAwayFrame(int paramInt)
/*    */   {
/* 34 */     this(paramInt, 0);
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   public DefaultSpdyGoAwayFrame(int paramInt1, int paramInt2)
/*    */   {
/* 44 */     this(paramInt1, SpdySessionStatus.valueOf(paramInt2));
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   public DefaultSpdyGoAwayFrame(int paramInt, SpdySessionStatus paramSpdySessionStatus)
/*    */   {
/* 54 */     setLastGoodStreamId(paramInt);
/* 55 */     setStatus(paramSpdySessionStatus);
/*    */   }
/*    */   
/*    */   public int lastGoodStreamId()
/*    */   {
/* 60 */     return this.lastGoodStreamId;
/*    */   }
/*    */   
/*    */   public SpdyGoAwayFrame setLastGoodStreamId(int paramInt)
/*    */   {
/* 65 */     if (paramInt < 0) {
/* 66 */       throw new IllegalArgumentException("Last-good-stream-ID cannot be negative: " + paramInt);
/*    */     }
/*    */     
/* 69 */     this.lastGoodStreamId = paramInt;
/* 70 */     return this;
/*    */   }
/*    */   
/*    */   public SpdySessionStatus status()
/*    */   {
/* 75 */     return this.status;
/*    */   }
/*    */   
/*    */   public SpdyGoAwayFrame setStatus(SpdySessionStatus paramSpdySessionStatus)
/*    */   {
/* 80 */     this.status = paramSpdySessionStatus;
/* 81 */     return this;
/*    */   }
/*    */   
/*    */   public String toString()
/*    */   {
/* 86 */     StringBuilder localStringBuilder = new StringBuilder();
/* 87 */     localStringBuilder.append(StringUtil.simpleClassName(this));
/* 88 */     localStringBuilder.append(StringUtil.NEWLINE);
/* 89 */     localStringBuilder.append("--> Last-good-stream-ID = ");
/* 90 */     localStringBuilder.append(lastGoodStreamId());
/* 91 */     localStringBuilder.append(StringUtil.NEWLINE);
/* 92 */     localStringBuilder.append("--> Status: ");
/* 93 */     localStringBuilder.append(status());
/* 94 */     return localStringBuilder.toString();
/*    */   }
/*    */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\io\netty\handler\codec\spdy\DefaultSpdyGoAwayFrame.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */
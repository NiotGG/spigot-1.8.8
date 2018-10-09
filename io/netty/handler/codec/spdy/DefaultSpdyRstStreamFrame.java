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
/*    */ public class DefaultSpdyRstStreamFrame
/*    */   extends DefaultSpdyStreamFrame
/*    */   implements SpdyRstStreamFrame
/*    */ {
/*    */   private SpdyStreamStatus status;
/*    */   
/*    */   public DefaultSpdyRstStreamFrame(int paramInt1, int paramInt2)
/*    */   {
/* 35 */     this(paramInt1, SpdyStreamStatus.valueOf(paramInt2));
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   public DefaultSpdyRstStreamFrame(int paramInt, SpdyStreamStatus paramSpdyStreamStatus)
/*    */   {
/* 45 */     super(paramInt);
/* 46 */     setStatus(paramSpdyStreamStatus);
/*    */   }
/*    */   
/*    */   public SpdyRstStreamFrame setStreamId(int paramInt)
/*    */   {
/* 51 */     super.setStreamId(paramInt);
/* 52 */     return this;
/*    */   }
/*    */   
/*    */   public SpdyRstStreamFrame setLast(boolean paramBoolean)
/*    */   {
/* 57 */     super.setLast(paramBoolean);
/* 58 */     return this;
/*    */   }
/*    */   
/*    */   public SpdyStreamStatus status()
/*    */   {
/* 63 */     return this.status;
/*    */   }
/*    */   
/*    */   public SpdyRstStreamFrame setStatus(SpdyStreamStatus paramSpdyStreamStatus)
/*    */   {
/* 68 */     this.status = paramSpdyStreamStatus;
/* 69 */     return this;
/*    */   }
/*    */   
/*    */   public String toString()
/*    */   {
/* 74 */     StringBuilder localStringBuilder = new StringBuilder();
/* 75 */     localStringBuilder.append(StringUtil.simpleClassName(this));
/* 76 */     localStringBuilder.append(StringUtil.NEWLINE);
/* 77 */     localStringBuilder.append("--> Stream-ID = ");
/* 78 */     localStringBuilder.append(streamId());
/* 79 */     localStringBuilder.append(StringUtil.NEWLINE);
/* 80 */     localStringBuilder.append("--> Status: ");
/* 81 */     localStringBuilder.append(status());
/* 82 */     return localStringBuilder.toString();
/*    */   }
/*    */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\io\netty\handler\codec\spdy\DefaultSpdyRstStreamFrame.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */
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
/*    */ public class DefaultSpdySynReplyFrame
/*    */   extends DefaultSpdyHeadersFrame
/*    */   implements SpdySynReplyFrame
/*    */ {
/*    */   public DefaultSpdySynReplyFrame(int paramInt)
/*    */   {
/* 32 */     super(paramInt);
/*    */   }
/*    */   
/*    */   public SpdySynReplyFrame setStreamId(int paramInt)
/*    */   {
/* 37 */     super.setStreamId(paramInt);
/* 38 */     return this;
/*    */   }
/*    */   
/*    */   public SpdySynReplyFrame setLast(boolean paramBoolean)
/*    */   {
/* 43 */     super.setLast(paramBoolean);
/* 44 */     return this;
/*    */   }
/*    */   
/*    */   public SpdySynReplyFrame setInvalid()
/*    */   {
/* 49 */     super.setInvalid();
/* 50 */     return this;
/*    */   }
/*    */   
/*    */   public String toString()
/*    */   {
/* 55 */     StringBuilder localStringBuilder = new StringBuilder();
/* 56 */     localStringBuilder.append(StringUtil.simpleClassName(this));
/* 57 */     localStringBuilder.append("(last: ");
/* 58 */     localStringBuilder.append(isLast());
/* 59 */     localStringBuilder.append(')');
/* 60 */     localStringBuilder.append(StringUtil.NEWLINE);
/* 61 */     localStringBuilder.append("--> Stream-ID = ");
/* 62 */     localStringBuilder.append(streamId());
/* 63 */     localStringBuilder.append(StringUtil.NEWLINE);
/* 64 */     localStringBuilder.append("--> Headers:");
/* 65 */     localStringBuilder.append(StringUtil.NEWLINE);
/* 66 */     appendHeaders(localStringBuilder);
/*    */     
/*    */ 
/* 69 */     localStringBuilder.setLength(localStringBuilder.length() - StringUtil.NEWLINE.length());
/* 70 */     return localStringBuilder.toString();
/*    */   }
/*    */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\io\netty\handler\codec\spdy\DefaultSpdySynReplyFrame.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */
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
/*    */ public class DefaultSpdyPingFrame
/*    */   implements SpdyPingFrame
/*    */ {
/*    */   private int id;
/*    */   
/*    */   public DefaultSpdyPingFrame(int paramInt)
/*    */   {
/* 33 */     setId(paramInt);
/*    */   }
/*    */   
/*    */   public int id()
/*    */   {
/* 38 */     return this.id;
/*    */   }
/*    */   
/*    */   public SpdyPingFrame setId(int paramInt)
/*    */   {
/* 43 */     this.id = paramInt;
/* 44 */     return this;
/*    */   }
/*    */   
/*    */   public String toString()
/*    */   {
/* 49 */     StringBuilder localStringBuilder = new StringBuilder();
/* 50 */     localStringBuilder.append(StringUtil.simpleClassName(this));
/* 51 */     localStringBuilder.append(StringUtil.NEWLINE);
/* 52 */     localStringBuilder.append("--> ID = ");
/* 53 */     localStringBuilder.append(id());
/* 54 */     return localStringBuilder.toString();
/*    */   }
/*    */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\io\netty\handler\codec\spdy\DefaultSpdyPingFrame.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */
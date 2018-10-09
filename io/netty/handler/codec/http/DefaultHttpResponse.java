/*    */ package io.netty.handler.codec.http;
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
/*    */ public class DefaultHttpResponse
/*    */   extends DefaultHttpMessage
/*    */   implements HttpResponse
/*    */ {
/*    */   private HttpResponseStatus status;
/*    */   
/*    */   public DefaultHttpResponse(HttpVersion paramHttpVersion, HttpResponseStatus paramHttpResponseStatus)
/*    */   {
/* 34 */     this(paramHttpVersion, paramHttpResponseStatus, true);
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   public DefaultHttpResponse(HttpVersion paramHttpVersion, HttpResponseStatus paramHttpResponseStatus, boolean paramBoolean)
/*    */   {
/* 45 */     super(paramHttpVersion, paramBoolean);
/* 46 */     if (paramHttpResponseStatus == null) {
/* 47 */       throw new NullPointerException("status");
/*    */     }
/* 49 */     this.status = paramHttpResponseStatus;
/*    */   }
/*    */   
/*    */   public HttpResponseStatus getStatus()
/*    */   {
/* 54 */     return this.status;
/*    */   }
/*    */   
/*    */   public HttpResponse setStatus(HttpResponseStatus paramHttpResponseStatus)
/*    */   {
/* 59 */     if (paramHttpResponseStatus == null) {
/* 60 */       throw new NullPointerException("status");
/*    */     }
/* 62 */     this.status = paramHttpResponseStatus;
/* 63 */     return this;
/*    */   }
/*    */   
/*    */   public HttpResponse setProtocolVersion(HttpVersion paramHttpVersion)
/*    */   {
/* 68 */     super.setProtocolVersion(paramHttpVersion);
/* 69 */     return this;
/*    */   }
/*    */   
/*    */   public String toString()
/*    */   {
/* 74 */     StringBuilder localStringBuilder = new StringBuilder();
/* 75 */     localStringBuilder.append(StringUtil.simpleClassName(this));
/* 76 */     localStringBuilder.append("(decodeResult: ");
/* 77 */     localStringBuilder.append(getDecoderResult());
/* 78 */     localStringBuilder.append(')');
/* 79 */     localStringBuilder.append(StringUtil.NEWLINE);
/* 80 */     localStringBuilder.append(getProtocolVersion().text());
/* 81 */     localStringBuilder.append(' ');
/* 82 */     localStringBuilder.append(getStatus());
/* 83 */     localStringBuilder.append(StringUtil.NEWLINE);
/* 84 */     appendHeaders(localStringBuilder);
/*    */     
/*    */ 
/* 87 */     localStringBuilder.setLength(localStringBuilder.length() - StringUtil.NEWLINE.length());
/* 88 */     return localStringBuilder.toString();
/*    */   }
/*    */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\io\netty\handler\codec\http\DefaultHttpResponse.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */
/*    */ package io.netty.handler.codec.http;
/*    */ 
/*    */ import io.netty.util.internal.StringUtil;
/*    */ import java.util.Map.Entry;
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
/*    */ public abstract class DefaultHttpMessage
/*    */   extends DefaultHttpObject
/*    */   implements HttpMessage
/*    */ {
/*    */   private HttpVersion version;
/*    */   private final HttpHeaders headers;
/*    */   
/*    */   protected DefaultHttpMessage(HttpVersion paramHttpVersion)
/*    */   {
/* 34 */     this(paramHttpVersion, true);
/*    */   }
/*    */   
/*    */   protected DefaultHttpMessage(HttpVersion paramHttpVersion, boolean paramBoolean) {
/* 38 */     if (paramHttpVersion == null) {
/* 39 */       throw new NullPointerException("version");
/*    */     }
/* 41 */     this.version = paramHttpVersion;
/* 42 */     this.headers = new DefaultHttpHeaders(paramBoolean);
/*    */   }
/*    */   
/*    */   public HttpHeaders headers()
/*    */   {
/* 47 */     return this.headers;
/*    */   }
/*    */   
/*    */   public HttpVersion getProtocolVersion()
/*    */   {
/* 52 */     return this.version;
/*    */   }
/*    */   
/*    */   public String toString()
/*    */   {
/* 57 */     StringBuilder localStringBuilder = new StringBuilder();
/* 58 */     localStringBuilder.append(StringUtil.simpleClassName(this));
/* 59 */     localStringBuilder.append("(version: ");
/* 60 */     localStringBuilder.append(getProtocolVersion().text());
/* 61 */     localStringBuilder.append(", keepAlive: ");
/* 62 */     localStringBuilder.append(HttpHeaders.isKeepAlive(this));
/* 63 */     localStringBuilder.append(')');
/* 64 */     localStringBuilder.append(StringUtil.NEWLINE);
/* 65 */     appendHeaders(localStringBuilder);
/*    */     
/*    */ 
/* 68 */     localStringBuilder.setLength(localStringBuilder.length() - StringUtil.NEWLINE.length());
/* 69 */     return localStringBuilder.toString();
/*    */   }
/*    */   
/*    */   public HttpMessage setProtocolVersion(HttpVersion paramHttpVersion)
/*    */   {
/* 74 */     if (paramHttpVersion == null) {
/* 75 */       throw new NullPointerException("version");
/*    */     }
/* 77 */     this.version = paramHttpVersion;
/* 78 */     return this;
/*    */   }
/*    */   
/*    */   void appendHeaders(StringBuilder paramStringBuilder) {
/* 82 */     for (Map.Entry localEntry : headers()) {
/* 83 */       paramStringBuilder.append((String)localEntry.getKey());
/* 84 */       paramStringBuilder.append(": ");
/* 85 */       paramStringBuilder.append((String)localEntry.getValue());
/* 86 */       paramStringBuilder.append(StringUtil.NEWLINE);
/*    */     }
/*    */   }
/*    */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\io\netty\handler\codec\http\DefaultHttpMessage.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */
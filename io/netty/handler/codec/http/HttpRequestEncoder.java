/*    */ package io.netty.handler.codec.http;
/*    */ 
/*    */ import io.netty.buffer.ByteBuf;
/*    */ import io.netty.util.CharsetUtil;
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
/*    */ public class HttpRequestEncoder
/*    */   extends HttpObjectEncoder<HttpRequest>
/*    */ {
/*    */   private static final char SLASH = '/';
/*    */   private static final char QUESTION_MARK = '?';
/* 30 */   private static final byte[] CRLF = { 13, 10 };
/*    */   
/*    */   public boolean acceptOutboundMessage(Object paramObject) throws Exception
/*    */   {
/* 34 */     return (super.acceptOutboundMessage(paramObject)) && (!(paramObject instanceof HttpResponse));
/*    */   }
/*    */   
/*    */   protected void encodeInitialLine(ByteBuf paramByteBuf, HttpRequest paramHttpRequest) throws Exception
/*    */   {
/* 39 */     paramHttpRequest.getMethod().encode(paramByteBuf);
/* 40 */     paramByteBuf.writeByte(32);
/*    */     
/*    */ 
/*    */ 
/* 44 */     String str = paramHttpRequest.getUri();
/*    */     
/* 46 */     if (str.length() == 0) {
/* 47 */       str = str + '/';
/*    */     } else {
/* 49 */       int i = str.indexOf("://");
/* 50 */       if ((i != -1) && (str.charAt(0) != '/')) {
/* 51 */         int j = i + 3;
/*    */         
/*    */ 
/* 54 */         int k = str.indexOf('?', j);
/* 55 */         if (k == -1) {
/* 56 */           if (str.lastIndexOf('/') <= j) {
/* 57 */             str = str + '/';
/*    */           }
/*    */         }
/* 60 */         else if (str.lastIndexOf('/', k) <= j) {
/* 61 */           int m = str.length();
/* 62 */           StringBuilder localStringBuilder = new StringBuilder(m + 1);
/* 63 */           localStringBuilder.append(str, 0, k);
/* 64 */           localStringBuilder.append('/');
/* 65 */           localStringBuilder.append(str, k, m);
/* 66 */           str = localStringBuilder.toString();
/*    */         }
/*    */       }
/*    */     }
/*    */     
/*    */ 
/* 72 */     paramByteBuf.writeBytes(str.getBytes(CharsetUtil.UTF_8));
/*    */     
/* 74 */     paramByteBuf.writeByte(32);
/* 75 */     paramHttpRequest.getProtocolVersion().encode(paramByteBuf);
/* 76 */     paramByteBuf.writeBytes(CRLF);
/*    */   }
/*    */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\io\netty\handler\codec\http\HttpRequestEncoder.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */
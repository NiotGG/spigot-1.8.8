/*    */ package io.netty.handler.codec.http.websocketx;
/*    */ 
/*    */ import io.netty.handler.codec.http.HttpHeaders;
/*    */ import java.net.URI;
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
/*    */ public final class WebSocketClientHandshakerFactory
/*    */ {
/*    */   public static WebSocketClientHandshaker newHandshaker(URI paramURI, WebSocketVersion paramWebSocketVersion, String paramString, boolean paramBoolean, HttpHeaders paramHttpHeaders)
/*    */   {
/* 53 */     return newHandshaker(paramURI, paramWebSocketVersion, paramString, paramBoolean, paramHttpHeaders, 65536);
/*    */   }
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
/*    */   public static WebSocketClientHandshaker newHandshaker(URI paramURI, WebSocketVersion paramWebSocketVersion, String paramString, boolean paramBoolean, HttpHeaders paramHttpHeaders, int paramInt)
/*    */   {
/* 77 */     if (paramWebSocketVersion == WebSocketVersion.V13) {
/* 78 */       return new WebSocketClientHandshaker13(paramURI, WebSocketVersion.V13, paramString, paramBoolean, paramHttpHeaders, paramInt);
/*    */     }
/*    */     
/* 81 */     if (paramWebSocketVersion == WebSocketVersion.V08) {
/* 82 */       return new WebSocketClientHandshaker08(paramURI, WebSocketVersion.V08, paramString, paramBoolean, paramHttpHeaders, paramInt);
/*    */     }
/*    */     
/* 85 */     if (paramWebSocketVersion == WebSocketVersion.V07) {
/* 86 */       return new WebSocketClientHandshaker07(paramURI, WebSocketVersion.V07, paramString, paramBoolean, paramHttpHeaders, paramInt);
/*    */     }
/*    */     
/* 89 */     if (paramWebSocketVersion == WebSocketVersion.V00) {
/* 90 */       return new WebSocketClientHandshaker00(paramURI, WebSocketVersion.V00, paramString, paramHttpHeaders, paramInt);
/*    */     }
/*    */     
/*    */ 
/* 94 */     throw new WebSocketHandshakeException("Protocol version " + paramWebSocketVersion + " not supported.");
/*    */   }
/*    */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\io\netty\handler\codec\http\websocketx\WebSocketClientHandshakerFactory.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */
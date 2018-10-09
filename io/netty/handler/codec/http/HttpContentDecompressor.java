/*    */ package io.netty.handler.codec.http;
/*    */ 
/*    */ import io.netty.channel.ChannelHandler;
/*    */ import io.netty.channel.embedded.EmbeddedChannel;
/*    */ import io.netty.handler.codec.compression.ZlibCodecFactory;
/*    */ import io.netty.handler.codec.compression.ZlibWrapper;
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
/*    */ public class HttpContentDecompressor
/*    */   extends HttpContentDecoder
/*    */ {
/*    */   private final boolean strict;
/*    */   
/*    */   public HttpContentDecompressor()
/*    */   {
/* 35 */     this(false);
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   public HttpContentDecompressor(boolean paramBoolean)
/*    */   {
/* 45 */     this.strict = paramBoolean;
/*    */   }
/*    */   
/*    */   protected EmbeddedChannel newContentDecoder(String paramString) throws Exception
/*    */   {
/* 50 */     if (("gzip".equalsIgnoreCase(paramString)) || ("x-gzip".equalsIgnoreCase(paramString))) {
/* 51 */       return new EmbeddedChannel(new ChannelHandler[] { ZlibCodecFactory.newZlibDecoder(ZlibWrapper.GZIP) });
/*    */     }
/* 53 */     if (("deflate".equalsIgnoreCase(paramString)) || ("x-deflate".equalsIgnoreCase(paramString))) {
/*    */       ZlibWrapper localZlibWrapper;
/* 55 */       if (this.strict) {
/* 56 */         localZlibWrapper = ZlibWrapper.ZLIB;
/*    */       } else {
/* 58 */         localZlibWrapper = ZlibWrapper.ZLIB_OR_NONE;
/*    */       }
/*    */       
/* 61 */       return new EmbeddedChannel(new ChannelHandler[] { ZlibCodecFactory.newZlibDecoder(localZlibWrapper) });
/*    */     }
/*    */     
/*    */ 
/* 65 */     return null;
/*    */   }
/*    */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\io\netty\handler\codec\http\HttpContentDecompressor.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */
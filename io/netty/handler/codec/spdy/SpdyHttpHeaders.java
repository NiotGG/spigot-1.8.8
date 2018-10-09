/*     */ package io.netty.handler.codec.spdy;
/*     */ 
/*     */ import io.netty.handler.codec.http.HttpHeaders;
/*     */ import io.netty.handler.codec.http.HttpMessage;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public final class SpdyHttpHeaders
/*     */ {
/*     */   public static void removeStreamId(HttpMessage paramHttpMessage)
/*     */   {
/*  62 */     paramHttpMessage.headers().remove("X-SPDY-Stream-ID");
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public static int getStreamId(HttpMessage paramHttpMessage)
/*     */   {
/*  69 */     return HttpHeaders.getIntHeader(paramHttpMessage, "X-SPDY-Stream-ID");
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public static void setStreamId(HttpMessage paramHttpMessage, int paramInt)
/*     */   {
/*  76 */     HttpHeaders.setIntHeader(paramHttpMessage, "X-SPDY-Stream-ID", paramInt);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public static void removeAssociatedToStreamId(HttpMessage paramHttpMessage)
/*     */   {
/*  83 */     paramHttpMessage.headers().remove("X-SPDY-Associated-To-Stream-ID");
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public static int getAssociatedToStreamId(HttpMessage paramHttpMessage)
/*     */   {
/*  93 */     return HttpHeaders.getIntHeader(paramHttpMessage, "X-SPDY-Associated-To-Stream-ID", 0);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public static void setAssociatedToStreamId(HttpMessage paramHttpMessage, int paramInt)
/*     */   {
/* 100 */     HttpHeaders.setIntHeader(paramHttpMessage, "X-SPDY-Associated-To-Stream-ID", paramInt);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public static void removePriority(HttpMessage paramHttpMessage)
/*     */   {
/* 107 */     paramHttpMessage.headers().remove("X-SPDY-Priority");
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public static byte getPriority(HttpMessage paramHttpMessage)
/*     */   {
/* 117 */     return (byte)HttpHeaders.getIntHeader(paramHttpMessage, "X-SPDY-Priority", 0);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public static void setPriority(HttpMessage paramHttpMessage, byte paramByte)
/*     */   {
/* 124 */     HttpHeaders.setIntHeader(paramHttpMessage, "X-SPDY-Priority", paramByte);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public static void removeUrl(HttpMessage paramHttpMessage)
/*     */   {
/* 131 */     paramHttpMessage.headers().remove("X-SPDY-URL");
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public static String getUrl(HttpMessage paramHttpMessage)
/*     */   {
/* 138 */     return paramHttpMessage.headers().get("X-SPDY-URL");
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public static void setUrl(HttpMessage paramHttpMessage, String paramString)
/*     */   {
/* 145 */     paramHttpMessage.headers().set("X-SPDY-URL", paramString);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public static void removeScheme(HttpMessage paramHttpMessage)
/*     */   {
/* 152 */     paramHttpMessage.headers().remove("X-SPDY-Scheme");
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public static String getScheme(HttpMessage paramHttpMessage)
/*     */   {
/* 159 */     return paramHttpMessage.headers().get("X-SPDY-Scheme");
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public static void setScheme(HttpMessage paramHttpMessage, String paramString)
/*     */   {
/* 166 */     paramHttpMessage.headers().set("X-SPDY-Scheme", paramString);
/*     */   }
/*     */   
/*     */   public static final class Names
/*     */   {
/*     */     public static final String STREAM_ID = "X-SPDY-Stream-ID";
/*     */     public static final String ASSOCIATED_TO_STREAM_ID = "X-SPDY-Associated-To-Stream-ID";
/*     */     public static final String PRIORITY = "X-SPDY-Priority";
/*     */     public static final String URL = "X-SPDY-URL";
/*     */     public static final String SCHEME = "X-SPDY-Scheme";
/*     */   }
/*     */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\io\netty\handler\codec\spdy\SpdyHttpHeaders.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */
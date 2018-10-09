/*     */ package io.netty.handler.codec.spdy;
/*     */ 
/*     */ import io.netty.handler.codec.http.HttpMethod;
/*     */ import io.netty.handler.codec.http.HttpResponseStatus;
/*     */ import io.netty.handler.codec.http.HttpVersion;
/*     */ import java.util.Collections;
/*     */ import java.util.Iterator;
/*     */ import java.util.List;
/*     */ import java.util.Map.Entry;
/*     */ import java.util.Set;
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
/*     */ public abstract class SpdyHeaders
/*     */   implements Iterable<Map.Entry<String, String>>
/*     */ {
/*  34 */   public static final SpdyHeaders EMPTY_HEADERS = new SpdyHeaders()
/*     */   {
/*     */     public List<String> getAll(String paramAnonymousString)
/*     */     {
/*  38 */       return Collections.emptyList();
/*     */     }
/*     */     
/*     */     public List<Map.Entry<String, String>> entries()
/*     */     {
/*  43 */       return Collections.emptyList();
/*     */     }
/*     */     
/*     */     public boolean contains(String paramAnonymousString)
/*     */     {
/*  48 */       return false;
/*     */     }
/*     */     
/*     */     public boolean isEmpty()
/*     */     {
/*  53 */       return true;
/*     */     }
/*     */     
/*     */     public Set<String> names()
/*     */     {
/*  58 */       return Collections.emptySet();
/*     */     }
/*     */     
/*     */     public SpdyHeaders add(String paramAnonymousString, Object paramAnonymousObject)
/*     */     {
/*  63 */       throw new UnsupportedOperationException("read only");
/*     */     }
/*     */     
/*     */     public SpdyHeaders add(String paramAnonymousString, Iterable<?> paramAnonymousIterable)
/*     */     {
/*  68 */       throw new UnsupportedOperationException("read only");
/*     */     }
/*     */     
/*     */     public SpdyHeaders set(String paramAnonymousString, Object paramAnonymousObject)
/*     */     {
/*  73 */       throw new UnsupportedOperationException("read only");
/*     */     }
/*     */     
/*     */     public SpdyHeaders set(String paramAnonymousString, Iterable<?> paramAnonymousIterable)
/*     */     {
/*  78 */       throw new UnsupportedOperationException("read only");
/*     */     }
/*     */     
/*     */     public SpdyHeaders remove(String paramAnonymousString)
/*     */     {
/*  83 */       throw new UnsupportedOperationException("read only");
/*     */     }
/*     */     
/*     */     public SpdyHeaders clear()
/*     */     {
/*  88 */       throw new UnsupportedOperationException("read only");
/*     */     }
/*     */     
/*     */     public Iterator<Map.Entry<String, String>> iterator()
/*     */     {
/*  93 */       return entries().iterator();
/*     */     }
/*     */     
/*     */     public String get(String paramAnonymousString)
/*     */     {
/*  98 */       return null;
/*     */     }
/*     */   };
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
/*     */   public static String getHeader(SpdyHeadersFrame paramSpdyHeadersFrame, String paramString)
/*     */   {
/* 142 */     return paramSpdyHeadersFrame.headers().get(paramString);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public static String getHeader(SpdyHeadersFrame paramSpdyHeadersFrame, String paramString1, String paramString2)
/*     */   {
/* 154 */     String str = paramSpdyHeadersFrame.headers().get(paramString1);
/* 155 */     if (str == null) {
/* 156 */       return paramString2;
/*     */     }
/* 158 */     return str;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public static void setHeader(SpdyHeadersFrame paramSpdyHeadersFrame, String paramString, Object paramObject)
/*     */   {
/* 166 */     paramSpdyHeadersFrame.headers().set(paramString, paramObject);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public static void setHeader(SpdyHeadersFrame paramSpdyHeadersFrame, String paramString, Iterable<?> paramIterable)
/*     */   {
/* 174 */     paramSpdyHeadersFrame.headers().set(paramString, paramIterable);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public static void addHeader(SpdyHeadersFrame paramSpdyHeadersFrame, String paramString, Object paramObject)
/*     */   {
/* 181 */     paramSpdyHeadersFrame.headers().add(paramString, paramObject);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public static void removeHost(SpdyHeadersFrame paramSpdyHeadersFrame)
/*     */   {
/* 188 */     paramSpdyHeadersFrame.headers().remove(":host");
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public static String getHost(SpdyHeadersFrame paramSpdyHeadersFrame)
/*     */   {
/* 195 */     return paramSpdyHeadersFrame.headers().get(":host");
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public static void setHost(SpdyHeadersFrame paramSpdyHeadersFrame, String paramString)
/*     */   {
/* 202 */     paramSpdyHeadersFrame.headers().set(":host", paramString);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public static void removeMethod(int paramInt, SpdyHeadersFrame paramSpdyHeadersFrame)
/*     */   {
/* 209 */     paramSpdyHeadersFrame.headers().remove(":method");
/*     */   }
/*     */   
/*     */ 
/*     */   public static HttpMethod getMethod(int paramInt, SpdyHeadersFrame paramSpdyHeadersFrame)
/*     */   {
/*     */     try
/*     */     {
/* 217 */       return HttpMethod.valueOf(paramSpdyHeadersFrame.headers().get(":method"));
/*     */     } catch (Exception localException) {}
/* 219 */     return null;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public static void setMethod(int paramInt, SpdyHeadersFrame paramSpdyHeadersFrame, HttpMethod paramHttpMethod)
/*     */   {
/* 227 */     paramSpdyHeadersFrame.headers().set(":method", paramHttpMethod.name());
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public static void removeScheme(int paramInt, SpdyHeadersFrame paramSpdyHeadersFrame)
/*     */   {
/* 234 */     paramSpdyHeadersFrame.headers().remove(":scheme");
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public static String getScheme(int paramInt, SpdyHeadersFrame paramSpdyHeadersFrame)
/*     */   {
/* 241 */     return paramSpdyHeadersFrame.headers().get(":scheme");
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public static void setScheme(int paramInt, SpdyHeadersFrame paramSpdyHeadersFrame, String paramString)
/*     */   {
/* 248 */     paramSpdyHeadersFrame.headers().set(":scheme", paramString);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public static void removeStatus(int paramInt, SpdyHeadersFrame paramSpdyHeadersFrame)
/*     */   {
/* 255 */     paramSpdyHeadersFrame.headers().remove(":status");
/*     */   }
/*     */   
/*     */ 
/*     */   public static HttpResponseStatus getStatus(int paramInt, SpdyHeadersFrame paramSpdyHeadersFrame)
/*     */   {
/*     */     try
/*     */     {
/* 263 */       String str1 = paramSpdyHeadersFrame.headers().get(":status");
/* 264 */       int i = str1.indexOf(' ');
/* 265 */       if (i == -1) {
/* 266 */         return HttpResponseStatus.valueOf(Integer.parseInt(str1));
/*     */       }
/* 268 */       int j = Integer.parseInt(str1.substring(0, i));
/* 269 */       String str2 = str1.substring(i + 1);
/* 270 */       HttpResponseStatus localHttpResponseStatus = HttpResponseStatus.valueOf(j);
/* 271 */       if (localHttpResponseStatus.reasonPhrase().equals(str2)) {
/* 272 */         return localHttpResponseStatus;
/*     */       }
/* 274 */       return new HttpResponseStatus(j, str2);
/*     */     }
/*     */     catch (Exception localException) {}
/*     */     
/* 278 */     return null;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public static void setStatus(int paramInt, SpdyHeadersFrame paramSpdyHeadersFrame, HttpResponseStatus paramHttpResponseStatus)
/*     */   {
/* 286 */     paramSpdyHeadersFrame.headers().set(":status", paramHttpResponseStatus.toString());
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public static void removeUrl(int paramInt, SpdyHeadersFrame paramSpdyHeadersFrame)
/*     */   {
/* 293 */     paramSpdyHeadersFrame.headers().remove(":path");
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public static String getUrl(int paramInt, SpdyHeadersFrame paramSpdyHeadersFrame)
/*     */   {
/* 300 */     return paramSpdyHeadersFrame.headers().get(":path");
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public static void setUrl(int paramInt, SpdyHeadersFrame paramSpdyHeadersFrame, String paramString)
/*     */   {
/* 307 */     paramSpdyHeadersFrame.headers().set(":path", paramString);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public static void removeVersion(int paramInt, SpdyHeadersFrame paramSpdyHeadersFrame)
/*     */   {
/* 314 */     paramSpdyHeadersFrame.headers().remove(":version");
/*     */   }
/*     */   
/*     */ 
/*     */   public static HttpVersion getVersion(int paramInt, SpdyHeadersFrame paramSpdyHeadersFrame)
/*     */   {
/*     */     try
/*     */     {
/* 322 */       return HttpVersion.valueOf(paramSpdyHeadersFrame.headers().get(":version"));
/*     */     } catch (Exception localException) {}
/* 324 */     return null;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public static void setVersion(int paramInt, SpdyHeadersFrame paramSpdyHeadersFrame, HttpVersion paramHttpVersion)
/*     */   {
/* 332 */     paramSpdyHeadersFrame.headers().set(":version", paramHttpVersion.text());
/*     */   }
/*     */   
/*     */   public Iterator<Map.Entry<String, String>> iterator()
/*     */   {
/* 337 */     return entries().iterator();
/*     */   }
/*     */   
/*     */   public abstract String get(String paramString);
/*     */   
/*     */   public abstract List<String> getAll(String paramString);
/*     */   
/*     */   public abstract List<Map.Entry<String, String>> entries();
/*     */   
/*     */   public abstract boolean contains(String paramString);
/*     */   
/*     */   public abstract Set<String> names();
/*     */   
/*     */   public abstract SpdyHeaders add(String paramString, Object paramObject);
/*     */   
/*     */   public abstract SpdyHeaders add(String paramString, Iterable<?> paramIterable);
/*     */   
/*     */   public abstract SpdyHeaders set(String paramString, Object paramObject);
/*     */   
/*     */   public abstract SpdyHeaders set(String paramString, Iterable<?> paramIterable);
/*     */   
/*     */   public abstract SpdyHeaders remove(String paramString);
/*     */   
/*     */   public abstract SpdyHeaders clear();
/*     */   
/*     */   public abstract boolean isEmpty();
/*     */   
/*     */   public static final class HttpNames
/*     */   {
/*     */     public static final String HOST = ":host";
/*     */     public static final String METHOD = ":method";
/*     */     public static final String PATH = ":path";
/*     */     public static final String SCHEME = ":scheme";
/*     */     public static final String STATUS = ":status";
/*     */     public static final String VERSION = ":version";
/*     */   }
/*     */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\io\netty\handler\codec\spdy\SpdyHeaders.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */
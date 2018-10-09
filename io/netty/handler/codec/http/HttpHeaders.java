/*      */ package io.netty.handler.codec.http;
/*      */ 
/*      */ import io.netty.buffer.ByteBuf;
/*      */ import java.text.ParseException;
/*      */ import java.util.Collections;
/*      */ import java.util.Date;
/*      */ import java.util.Iterator;
/*      */ import java.util.List;
/*      */ import java.util.Map.Entry;
/*      */ import java.util.Set;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ public abstract class HttpHeaders
/*      */   implements Iterable<Map.Entry<String, String>>
/*      */ {
/*   38 */   private static final byte[] HEADER_SEPERATOR = { 58, 32 };
/*   39 */   private static final byte[] CRLF = { 13, 10 };
/*   40 */   private static final CharSequence CONTENT_LENGTH_ENTITY = newEntity("Content-Length");
/*   41 */   private static final CharSequence CONNECTION_ENTITY = newEntity("Connection");
/*   42 */   private static final CharSequence CLOSE_ENTITY = newEntity("close");
/*   43 */   private static final CharSequence KEEP_ALIVE_ENTITY = newEntity("keep-alive");
/*   44 */   private static final CharSequence HOST_ENTITY = newEntity("Host");
/*   45 */   private static final CharSequence DATE_ENTITY = newEntity("Date");
/*   46 */   private static final CharSequence EXPECT_ENTITY = newEntity("Expect");
/*   47 */   private static final CharSequence CONTINUE_ENTITY = newEntity("100-continue");
/*   48 */   private static final CharSequence TRANSFER_ENCODING_ENTITY = newEntity("Transfer-Encoding");
/*   49 */   private static final CharSequence CHUNKED_ENTITY = newEntity("chunked");
/*   50 */   private static final CharSequence SEC_WEBSOCKET_KEY1_ENTITY = newEntity("Sec-WebSocket-Key1");
/*   51 */   private static final CharSequence SEC_WEBSOCKET_KEY2_ENTITY = newEntity("Sec-WebSocket-Key2");
/*   52 */   private static final CharSequence SEC_WEBSOCKET_ORIGIN_ENTITY = newEntity("Sec-WebSocket-Origin");
/*   53 */   private static final CharSequence SEC_WEBSOCKET_LOCATION_ENTITY = newEntity("Sec-WebSocket-Location");
/*      */   
/*   55 */   public static final HttpHeaders EMPTY_HEADERS = new HttpHeaders()
/*      */   {
/*      */     public String get(String paramAnonymousString) {
/*   58 */       return null;
/*      */     }
/*      */     
/*      */     public List<String> getAll(String paramAnonymousString)
/*      */     {
/*   63 */       return Collections.emptyList();
/*      */     }
/*      */     
/*      */     public List<Map.Entry<String, String>> entries()
/*      */     {
/*   68 */       return Collections.emptyList();
/*      */     }
/*      */     
/*      */     public boolean contains(String paramAnonymousString)
/*      */     {
/*   73 */       return false;
/*      */     }
/*      */     
/*      */     public boolean isEmpty()
/*      */     {
/*   78 */       return true;
/*      */     }
/*      */     
/*      */     public Set<String> names()
/*      */     {
/*   83 */       return Collections.emptySet();
/*      */     }
/*      */     
/*      */     public HttpHeaders add(String paramAnonymousString, Object paramAnonymousObject)
/*      */     {
/*   88 */       throw new UnsupportedOperationException("read only");
/*      */     }
/*      */     
/*      */     public HttpHeaders add(String paramAnonymousString, Iterable<?> paramAnonymousIterable)
/*      */     {
/*   93 */       throw new UnsupportedOperationException("read only");
/*      */     }
/*      */     
/*      */     public HttpHeaders set(String paramAnonymousString, Object paramAnonymousObject)
/*      */     {
/*   98 */       throw new UnsupportedOperationException("read only");
/*      */     }
/*      */     
/*      */     public HttpHeaders set(String paramAnonymousString, Iterable<?> paramAnonymousIterable)
/*      */     {
/*  103 */       throw new UnsupportedOperationException("read only");
/*      */     }
/*      */     
/*      */     public HttpHeaders remove(String paramAnonymousString)
/*      */     {
/*  108 */       throw new UnsupportedOperationException("read only");
/*      */     }
/*      */     
/*      */     public HttpHeaders clear()
/*      */     {
/*  113 */       throw new UnsupportedOperationException("read only");
/*      */     }
/*      */     
/*      */     public Iterator<Map.Entry<String, String>> iterator()
/*      */     {
/*  118 */       return entries().iterator();
/*      */     }
/*      */   };
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public static boolean isKeepAlive(HttpMessage paramHttpMessage)
/*      */   {
/*  568 */     String str = paramHttpMessage.headers().get(CONNECTION_ENTITY);
/*  569 */     if ((str != null) && (equalsIgnoreCase(CLOSE_ENTITY, str))) {
/*  570 */       return false;
/*      */     }
/*      */     
/*  573 */     if (paramHttpMessage.getProtocolVersion().isKeepAliveDefault()) {
/*  574 */       return !equalsIgnoreCase(CLOSE_ENTITY, str);
/*      */     }
/*  576 */     return equalsIgnoreCase(KEEP_ALIVE_ENTITY, str);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public static void setKeepAlive(HttpMessage paramHttpMessage, boolean paramBoolean)
/*      */   {
/*  600 */     HttpHeaders localHttpHeaders = paramHttpMessage.headers();
/*  601 */     if (paramHttpMessage.getProtocolVersion().isKeepAliveDefault()) {
/*  602 */       if (paramBoolean) {
/*  603 */         localHttpHeaders.remove(CONNECTION_ENTITY);
/*      */       } else {
/*  605 */         localHttpHeaders.set(CONNECTION_ENTITY, CLOSE_ENTITY);
/*      */       }
/*      */     }
/*  608 */     else if (paramBoolean) {
/*  609 */       localHttpHeaders.set(CONNECTION_ENTITY, KEEP_ALIVE_ENTITY);
/*      */     } else {
/*  611 */       localHttpHeaders.remove(CONNECTION_ENTITY);
/*      */     }
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   public static String getHeader(HttpMessage paramHttpMessage, String paramString)
/*      */   {
/*  620 */     return paramHttpMessage.headers().get(paramString);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public static String getHeader(HttpMessage paramHttpMessage, CharSequence paramCharSequence)
/*      */   {
/*  631 */     return paramHttpMessage.headers().get(paramCharSequence);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */   public static String getHeader(HttpMessage paramHttpMessage, String paramString1, String paramString2)
/*      */   {
/*  638 */     return getHeader(paramHttpMessage, paramString1, paramString2);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public static String getHeader(HttpMessage paramHttpMessage, CharSequence paramCharSequence, String paramString)
/*      */   {
/*  650 */     String str = paramHttpMessage.headers().get(paramCharSequence);
/*  651 */     if (str == null) {
/*  652 */       return paramString;
/*      */     }
/*  654 */     return str;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */   public static void setHeader(HttpMessage paramHttpMessage, String paramString, Object paramObject)
/*      */   {
/*  661 */     paramHttpMessage.headers().set(paramString, paramObject);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public static void setHeader(HttpMessage paramHttpMessage, CharSequence paramCharSequence, Object paramObject)
/*      */   {
/*  673 */     paramHttpMessage.headers().set(paramCharSequence, paramObject);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   public static void setHeader(HttpMessage paramHttpMessage, String paramString, Iterable<?> paramIterable)
/*      */   {
/*  681 */     paramHttpMessage.headers().set(paramString, paramIterable);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public static void setHeader(HttpMessage paramHttpMessage, CharSequence paramCharSequence, Iterable<?> paramIterable)
/*      */   {
/*  699 */     paramHttpMessage.headers().set(paramCharSequence, paramIterable);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */   public static void addHeader(HttpMessage paramHttpMessage, String paramString, Object paramObject)
/*      */   {
/*  706 */     paramHttpMessage.headers().add(paramString, paramObject);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public static void addHeader(HttpMessage paramHttpMessage, CharSequence paramCharSequence, Object paramObject)
/*      */   {
/*  717 */     paramHttpMessage.headers().add(paramCharSequence, paramObject);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */   public static void removeHeader(HttpMessage paramHttpMessage, String paramString)
/*      */   {
/*  724 */     paramHttpMessage.headers().remove(paramString);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */   public static void removeHeader(HttpMessage paramHttpMessage, CharSequence paramCharSequence)
/*      */   {
/*  731 */     paramHttpMessage.headers().remove(paramCharSequence);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */   public static void clearHeaders(HttpMessage paramHttpMessage)
/*      */   {
/*  738 */     paramHttpMessage.headers().clear();
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */   public static int getIntHeader(HttpMessage paramHttpMessage, String paramString)
/*      */   {
/*  745 */     return getIntHeader(paramHttpMessage, paramString);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public static int getIntHeader(HttpMessage paramHttpMessage, CharSequence paramCharSequence)
/*      */   {
/*  758 */     String str = getHeader(paramHttpMessage, paramCharSequence);
/*  759 */     if (str == null) {
/*  760 */       throw new NumberFormatException("header not found: " + paramCharSequence);
/*      */     }
/*  762 */     return Integer.parseInt(str);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */   public static int getIntHeader(HttpMessage paramHttpMessage, String paramString, int paramInt)
/*      */   {
/*  769 */     return getIntHeader(paramHttpMessage, paramString, paramInt);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public static int getIntHeader(HttpMessage paramHttpMessage, CharSequence paramCharSequence, int paramInt)
/*      */   {
/*  781 */     String str = getHeader(paramHttpMessage, paramCharSequence);
/*  782 */     if (str == null) {
/*  783 */       return paramInt;
/*      */     }
/*      */     try
/*      */     {
/*  787 */       return Integer.parseInt(str);
/*      */     } catch (NumberFormatException localNumberFormatException) {}
/*  789 */     return paramInt;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   public static void setIntHeader(HttpMessage paramHttpMessage, String paramString, int paramInt)
/*      */   {
/*  797 */     paramHttpMessage.headers().set(paramString, Integer.valueOf(paramInt));
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   public static void setIntHeader(HttpMessage paramHttpMessage, CharSequence paramCharSequence, int paramInt)
/*      */   {
/*  805 */     paramHttpMessage.headers().set(paramCharSequence, Integer.valueOf(paramInt));
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */   public static void setIntHeader(HttpMessage paramHttpMessage, String paramString, Iterable<Integer> paramIterable)
/*      */   {
/*  812 */     paramHttpMessage.headers().set(paramString, paramIterable);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   public static void setIntHeader(HttpMessage paramHttpMessage, CharSequence paramCharSequence, Iterable<Integer> paramIterable)
/*      */   {
/*  820 */     paramHttpMessage.headers().set(paramCharSequence, paramIterable);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   public static void addIntHeader(HttpMessage paramHttpMessage, String paramString, int paramInt)
/*      */   {
/*  828 */     paramHttpMessage.headers().add(paramString, Integer.valueOf(paramInt));
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */   public static void addIntHeader(HttpMessage paramHttpMessage, CharSequence paramCharSequence, int paramInt)
/*      */   {
/*  835 */     paramHttpMessage.headers().add(paramCharSequence, Integer.valueOf(paramInt));
/*      */   }
/*      */   
/*      */ 
/*      */   public static Date getDateHeader(HttpMessage paramHttpMessage, String paramString)
/*      */     throws ParseException
/*      */   {
/*  842 */     return getDateHeader(paramHttpMessage, paramString);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public static Date getDateHeader(HttpMessage paramHttpMessage, CharSequence paramCharSequence)
/*      */     throws ParseException
/*      */   {
/*  855 */     String str = getHeader(paramHttpMessage, paramCharSequence);
/*  856 */     if (str == null) {
/*  857 */       throw new ParseException("header not found: " + paramCharSequence, 0);
/*      */     }
/*  859 */     return HttpHeaderDateFormat.get().parse(str);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */   public static Date getDateHeader(HttpMessage paramHttpMessage, String paramString, Date paramDate)
/*      */   {
/*  866 */     return getDateHeader(paramHttpMessage, paramString, paramDate);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public static Date getDateHeader(HttpMessage paramHttpMessage, CharSequence paramCharSequence, Date paramDate)
/*      */   {
/*  878 */     String str = getHeader(paramHttpMessage, paramCharSequence);
/*  879 */     if (str == null) {
/*  880 */       return paramDate;
/*      */     }
/*      */     try
/*      */     {
/*  884 */       return HttpHeaderDateFormat.get().parse(str);
/*      */     } catch (ParseException localParseException) {}
/*  886 */     return paramDate;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   public static void setDateHeader(HttpMessage paramHttpMessage, String paramString, Date paramDate)
/*      */   {
/*  894 */     setDateHeader(paramHttpMessage, paramString, paramDate);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public static void setDateHeader(HttpMessage paramHttpMessage, CharSequence paramCharSequence, Date paramDate)
/*      */   {
/*  904 */     if (paramDate != null) {
/*  905 */       paramHttpMessage.headers().set(paramCharSequence, HttpHeaderDateFormat.get().format(paramDate));
/*      */     } else {
/*  907 */       paramHttpMessage.headers().set(paramCharSequence, null);
/*      */     }
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */   public static void setDateHeader(HttpMessage paramHttpMessage, String paramString, Iterable<Date> paramIterable)
/*      */   {
/*  915 */     paramHttpMessage.headers().set(paramString, paramIterable);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public static void setDateHeader(HttpMessage paramHttpMessage, CharSequence paramCharSequence, Iterable<Date> paramIterable)
/*      */   {
/*  925 */     paramHttpMessage.headers().set(paramCharSequence, paramIterable);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */   public static void addDateHeader(HttpMessage paramHttpMessage, String paramString, Date paramDate)
/*      */   {
/*  932 */     paramHttpMessage.headers().add(paramString, paramDate);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public static void addDateHeader(HttpMessage paramHttpMessage, CharSequence paramCharSequence, Date paramDate)
/*      */   {
/*  941 */     paramHttpMessage.headers().add(paramCharSequence, paramDate);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public static long getContentLength(HttpMessage paramHttpMessage)
/*      */   {
/*  957 */     String str = getHeader(paramHttpMessage, CONTENT_LENGTH_ENTITY);
/*  958 */     if (str != null) {
/*  959 */       return Long.parseLong(str);
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*  964 */     long l = getWebSocketContentLength(paramHttpMessage);
/*  965 */     if (l >= 0L) {
/*  966 */       return l;
/*      */     }
/*      */     
/*      */ 
/*  970 */     throw new NumberFormatException("header not found: Content-Length");
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public static long getContentLength(HttpMessage paramHttpMessage, long paramLong)
/*      */   {
/*  984 */     String str = paramHttpMessage.headers().get(CONTENT_LENGTH_ENTITY);
/*  985 */     if (str != null) {
/*      */       try {
/*  987 */         return Long.parseLong(str);
/*      */       } catch (NumberFormatException localNumberFormatException) {
/*  989 */         return paramLong;
/*      */       }
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*  995 */     long l = getWebSocketContentLength(paramHttpMessage);
/*  996 */     if (l >= 0L) {
/*  997 */       return l;
/*      */     }
/*      */     
/*      */ 
/* 1001 */     return paramLong;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   private static int getWebSocketContentLength(HttpMessage paramHttpMessage)
/*      */   {
/* 1010 */     HttpHeaders localHttpHeaders = paramHttpMessage.headers();
/* 1011 */     Object localObject; if ((paramHttpMessage instanceof HttpRequest)) {
/* 1012 */       localObject = (HttpRequest)paramHttpMessage;
/* 1013 */       if ((HttpMethod.GET.equals(((HttpRequest)localObject).getMethod())) && (localHttpHeaders.contains(SEC_WEBSOCKET_KEY1_ENTITY)) && (localHttpHeaders.contains(SEC_WEBSOCKET_KEY2_ENTITY)))
/*      */       {
/*      */ 
/* 1016 */         return 8;
/*      */       }
/* 1018 */     } else if ((paramHttpMessage instanceof HttpResponse)) {
/* 1019 */       localObject = (HttpResponse)paramHttpMessage;
/* 1020 */       if ((((HttpResponse)localObject).getStatus().code() == 101) && (localHttpHeaders.contains(SEC_WEBSOCKET_ORIGIN_ENTITY)) && (localHttpHeaders.contains(SEC_WEBSOCKET_LOCATION_ENTITY)))
/*      */       {
/*      */ 
/* 1023 */         return 16;
/*      */       }
/*      */     }
/*      */     
/*      */ 
/* 1028 */     return -1;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */   public static void setContentLength(HttpMessage paramHttpMessage, long paramLong)
/*      */   {
/* 1035 */     paramHttpMessage.headers().set(CONTENT_LENGTH_ENTITY, Long.valueOf(paramLong));
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */   public static String getHost(HttpMessage paramHttpMessage)
/*      */   {
/* 1042 */     return paramHttpMessage.headers().get(HOST_ENTITY);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   public static String getHost(HttpMessage paramHttpMessage, String paramString)
/*      */   {
/* 1050 */     return getHeader(paramHttpMessage, HOST_ENTITY, paramString);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */   public static void setHost(HttpMessage paramHttpMessage, String paramString)
/*      */   {
/* 1057 */     paramHttpMessage.headers().set(HOST_ENTITY, paramString);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */   public static void setHost(HttpMessage paramHttpMessage, CharSequence paramCharSequence)
/*      */   {
/* 1064 */     paramHttpMessage.headers().set(HOST_ENTITY, paramCharSequence);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public static Date getDate(HttpMessage paramHttpMessage)
/*      */     throws ParseException
/*      */   {
/* 1074 */     return getDateHeader(paramHttpMessage, DATE_ENTITY);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public static Date getDate(HttpMessage paramHttpMessage, Date paramDate)
/*      */   {
/* 1083 */     return getDateHeader(paramHttpMessage, DATE_ENTITY, paramDate);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */   public static void setDate(HttpMessage paramHttpMessage, Date paramDate)
/*      */   {
/* 1090 */     if (paramDate != null) {
/* 1091 */       paramHttpMessage.headers().set(DATE_ENTITY, HttpHeaderDateFormat.get().format(paramDate));
/*      */     } else {
/* 1093 */       paramHttpMessage.headers().set(DATE_ENTITY, null);
/*      */     }
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public static boolean is100ContinueExpected(HttpMessage paramHttpMessage)
/*      */   {
/* 1103 */     if (!(paramHttpMessage instanceof HttpRequest)) {
/* 1104 */       return false;
/*      */     }
/*      */     
/*      */ 
/* 1108 */     if (paramHttpMessage.getProtocolVersion().compareTo(HttpVersion.HTTP_1_1) < 0) {
/* 1109 */       return false;
/*      */     }
/*      */     
/*      */ 
/* 1113 */     String str = paramHttpMessage.headers().get(EXPECT_ENTITY);
/* 1114 */     if (str == null) {
/* 1115 */       return false;
/*      */     }
/* 1117 */     if (equalsIgnoreCase(CONTINUE_ENTITY, str)) {
/* 1118 */       return true;
/*      */     }
/*      */     
/*      */ 
/* 1122 */     return paramHttpMessage.headers().contains(EXPECT_ENTITY, CONTINUE_ENTITY, true);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public static void set100ContinueExpected(HttpMessage paramHttpMessage)
/*      */   {
/* 1131 */     set100ContinueExpected(paramHttpMessage, true);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public static void set100ContinueExpected(HttpMessage paramHttpMessage, boolean paramBoolean)
/*      */   {
/* 1142 */     if (paramBoolean) {
/* 1143 */       paramHttpMessage.headers().set(EXPECT_ENTITY, CONTINUE_ENTITY);
/*      */     } else {
/* 1145 */       paramHttpMessage.headers().remove(EXPECT_ENTITY);
/*      */     }
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   static void validateHeaderName(CharSequence paramCharSequence)
/*      */   {
/* 1156 */     if (paramCharSequence == null) {
/* 1157 */       throw new NullPointerException("Header names cannot be null");
/*      */     }
/*      */     
/* 1160 */     for (int i = 0; i < paramCharSequence.length(); i++)
/*      */     {
/* 1162 */       int j = paramCharSequence.charAt(i);
/*      */       
/*      */ 
/* 1165 */       if (j > 127) {
/* 1166 */         throw new IllegalArgumentException("Header name cannot contain non-ASCII characters: " + paramCharSequence);
/*      */       }
/*      */       
/*      */ 
/*      */ 
/* 1171 */       switch (j) {
/*      */       case 9: case 10: case 11: case 12: case 13: 
/*      */       case 32: case 44: case 58: case 59: case 61: 
/* 1174 */         throw new IllegalArgumentException("Header name cannot contain the following prohibited characters: =,;: \\t\\r\\n\\v\\f: " + paramCharSequence);
/*      */       }
/*      */       
/*      */     }
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   static void validateHeaderValue(CharSequence paramCharSequence)
/*      */   {
/* 1188 */     if (paramCharSequence == null) {
/* 1189 */       throw new NullPointerException("Header values cannot be null");
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 1201 */     int i = 0;
/*      */     
/*      */ 
/*      */ 
/* 1205 */     for (int j = 0; j < paramCharSequence.length(); j++) {
/* 1206 */       int k = paramCharSequence.charAt(j);
/*      */       
/*      */ 
/* 1209 */       switch (k) {
/*      */       case 11: 
/* 1211 */         throw new IllegalArgumentException("Header value contains a prohibited character '\\v': " + paramCharSequence);
/*      */       
/*      */       case 12: 
/* 1214 */         throw new IllegalArgumentException("Header value contains a prohibited character '\\f': " + paramCharSequence);
/*      */       }
/*      */       
/*      */       
/*      */ 
/* 1219 */       switch (i) {
/*      */       case 0: 
/* 1221 */         switch (k) {
/*      */         case 13: 
/* 1223 */           i = 1;
/* 1224 */           break;
/*      */         case 10: 
/* 1226 */           i = 2;
/*      */         }
/*      */         
/* 1229 */         break;
/*      */       case 1: 
/* 1231 */         switch (k) {
/*      */         case 10: 
/* 1233 */           i = 2;
/* 1234 */           break;
/*      */         default: 
/* 1236 */           throw new IllegalArgumentException("Only '\\n' is allowed after '\\r': " + paramCharSequence);
/*      */         }
/*      */         
/*      */         break;
/*      */       case 2: 
/* 1241 */         switch (k) {
/*      */         case 9: case 32: 
/* 1243 */           i = 0;
/* 1244 */           break;
/*      */         default: 
/* 1246 */           throw new IllegalArgumentException("Only ' ' and '\\t' are allowed after '\\n': " + paramCharSequence);
/*      */         }
/*      */         
/*      */         break;
/*      */       }
/*      */     }
/* 1252 */     if (i != 0) {
/* 1253 */       throw new IllegalArgumentException("Header value must not end with '\\r' or '\\n':" + paramCharSequence);
/*      */     }
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public static boolean isTransferEncodingChunked(HttpMessage paramHttpMessage)
/*      */   {
/* 1265 */     return paramHttpMessage.headers().contains(TRANSFER_ENCODING_ENTITY, CHUNKED_ENTITY, true);
/*      */   }
/*      */   
/*      */   public static void removeTransferEncodingChunked(HttpMessage paramHttpMessage) {
/* 1269 */     List localList = paramHttpMessage.headers().getAll(TRANSFER_ENCODING_ENTITY);
/* 1270 */     if (localList.isEmpty()) {
/* 1271 */       return;
/*      */     }
/* 1273 */     Iterator localIterator = localList.iterator();
/* 1274 */     while (localIterator.hasNext()) {
/* 1275 */       String str = (String)localIterator.next();
/* 1276 */       if (equalsIgnoreCase(str, CHUNKED_ENTITY)) {
/* 1277 */         localIterator.remove();
/*      */       }
/*      */     }
/* 1280 */     if (localList.isEmpty()) {
/* 1281 */       paramHttpMessage.headers().remove(TRANSFER_ENCODING_ENTITY);
/*      */     } else {
/* 1283 */       paramHttpMessage.headers().set(TRANSFER_ENCODING_ENTITY, localList);
/*      */     }
/*      */   }
/*      */   
/*      */   public static void setTransferEncodingChunked(HttpMessage paramHttpMessage) {
/* 1288 */     addHeader(paramHttpMessage, TRANSFER_ENCODING_ENTITY, CHUNKED_ENTITY);
/* 1289 */     removeHeader(paramHttpMessage, CONTENT_LENGTH_ENTITY);
/*      */   }
/*      */   
/*      */   public static boolean isContentLengthSet(HttpMessage paramHttpMessage) {
/* 1293 */     return paramHttpMessage.headers().contains(CONTENT_LENGTH_ENTITY);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   public static boolean equalsIgnoreCase(CharSequence paramCharSequence1, CharSequence paramCharSequence2)
/*      */   {
/* 1301 */     if (paramCharSequence1 == paramCharSequence2) {
/* 1302 */       return true;
/*      */     }
/*      */     
/* 1305 */     if ((paramCharSequence1 == null) || (paramCharSequence2 == null)) {
/* 1306 */       return false;
/*      */     }
/*      */     
/* 1309 */     int i = paramCharSequence1.length();
/* 1310 */     if (i != paramCharSequence2.length()) {
/* 1311 */       return false;
/*      */     }
/*      */     
/* 1314 */     for (int j = i - 1; j >= 0; j--) {
/* 1315 */       int k = paramCharSequence1.charAt(j);
/* 1316 */       int m = paramCharSequence2.charAt(j);
/* 1317 */       if (k != m) {
/* 1318 */         if ((k >= 65) && (k <= 90)) {
/* 1319 */           k = (char)(k + 32);
/*      */         }
/* 1321 */         if ((m >= 65) && (m <= 90)) {
/* 1322 */           m = (char)(m + 32);
/*      */         }
/* 1324 */         if (k != m) {
/* 1325 */           return false;
/*      */         }
/*      */       }
/*      */     }
/* 1329 */     return true;
/*      */   }
/*      */   
/*      */   static int hash(CharSequence paramCharSequence) {
/* 1333 */     if ((paramCharSequence instanceof HttpHeaderEntity)) {
/* 1334 */       return ((HttpHeaderEntity)paramCharSequence).hash();
/*      */     }
/* 1336 */     int i = 0;
/* 1337 */     for (int j = paramCharSequence.length() - 1; j >= 0; j--) {
/* 1338 */       int k = paramCharSequence.charAt(j);
/* 1339 */       if ((k >= 65) && (k <= 90)) {
/* 1340 */         k = (char)(k + 32);
/*      */       }
/* 1342 */       i = 31 * i + k;
/*      */     }
/*      */     
/* 1345 */     if (i > 0)
/* 1346 */       return i;
/* 1347 */     if (i == Integer.MIN_VALUE) {
/* 1348 */       return Integer.MAX_VALUE;
/*      */     }
/* 1350 */     return -i;
/*      */   }
/*      */   
/*      */   static void encode(HttpHeaders paramHttpHeaders, ByteBuf paramByteBuf)
/*      */   {
/* 1355 */     if ((paramHttpHeaders instanceof DefaultHttpHeaders)) {
/* 1356 */       ((DefaultHttpHeaders)paramHttpHeaders).encode(paramByteBuf);
/*      */     } else {
/* 1358 */       for (Map.Entry localEntry : paramHttpHeaders) {
/* 1359 */         encode((CharSequence)localEntry.getKey(), (CharSequence)localEntry.getValue(), paramByteBuf);
/*      */       }
/*      */     }
/*      */   }
/*      */   
/*      */   static void encode(CharSequence paramCharSequence1, CharSequence paramCharSequence2, ByteBuf paramByteBuf)
/*      */   {
/* 1366 */     if (!encodeAscii(paramCharSequence1, paramByteBuf)) {
/* 1367 */       paramByteBuf.writeBytes(HEADER_SEPERATOR);
/*      */     }
/* 1369 */     if (!encodeAscii(paramCharSequence2, paramByteBuf)) {
/* 1370 */       paramByteBuf.writeBytes(CRLF);
/*      */     }
/*      */   }
/*      */   
/*      */   public static boolean encodeAscii(CharSequence paramCharSequence, ByteBuf paramByteBuf) {
/* 1375 */     if ((paramCharSequence instanceof HttpHeaderEntity)) {
/* 1376 */       return ((HttpHeaderEntity)paramCharSequence).encode(paramByteBuf);
/*      */     }
/* 1378 */     encodeAscii0(paramCharSequence, paramByteBuf);
/* 1379 */     return false;
/*      */   }
/*      */   
/*      */   static void encodeAscii0(CharSequence paramCharSequence, ByteBuf paramByteBuf)
/*      */   {
/* 1384 */     int i = paramCharSequence.length();
/* 1385 */     for (int j = 0; j < i; j++) {
/* 1386 */       paramByteBuf.writeByte((byte)paramCharSequence.charAt(j));
/*      */     }
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   public static CharSequence newEntity(String paramString)
/*      */   {
/* 1395 */     if (paramString == null) {
/* 1396 */       throw new NullPointerException("name");
/*      */     }
/* 1398 */     return new HttpHeaderEntity(paramString);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   public static CharSequence newNameEntity(String paramString)
/*      */   {
/* 1406 */     if (paramString == null) {
/* 1407 */       throw new NullPointerException("name");
/*      */     }
/* 1409 */     return new HttpHeaderEntity(paramString, HEADER_SEPERATOR);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   public static CharSequence newValueEntity(String paramString)
/*      */   {
/* 1417 */     if (paramString == null) {
/* 1418 */       throw new NullPointerException("name");
/*      */     }
/* 1420 */     return new HttpHeaderEntity(paramString, CRLF);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public abstract String get(String paramString);
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public String get(CharSequence paramCharSequence)
/*      */   {
/* 1438 */     return get(paramCharSequence.toString());
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public abstract List<String> getAll(String paramString);
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public List<String> getAll(CharSequence paramCharSequence)
/*      */   {
/* 1454 */     return getAll(paramCharSequence.toString());
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public abstract List<Map.Entry<String, String>> entries();
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public abstract boolean contains(String paramString);
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public boolean contains(CharSequence paramCharSequence)
/*      */   {
/* 1476 */     return contains(paramCharSequence.toString());
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public abstract boolean isEmpty();
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public abstract Set<String> names();
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public abstract HttpHeaders add(String paramString, Object paramObject);
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public HttpHeaders add(CharSequence paramCharSequence, Object paramObject)
/*      */   {
/* 1510 */     return add(paramCharSequence.toString(), paramObject);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public abstract HttpHeaders add(String paramString, Iterable<?> paramIterable);
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public HttpHeaders add(CharSequence paramCharSequence, Iterable<?> paramIterable)
/*      */   {
/* 1536 */     return add(paramCharSequence.toString(), paramIterable);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public HttpHeaders add(HttpHeaders paramHttpHeaders)
/*      */   {
/* 1545 */     if (paramHttpHeaders == null) {
/* 1546 */       throw new NullPointerException("headers");
/*      */     }
/* 1548 */     for (Map.Entry localEntry : paramHttpHeaders) {
/* 1549 */       add((String)localEntry.getKey(), localEntry.getValue());
/*      */     }
/* 1551 */     return this;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public abstract HttpHeaders set(String paramString, Object paramObject);
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public HttpHeaders set(CharSequence paramCharSequence, Object paramObject)
/*      */   {
/* 1573 */     return set(paramCharSequence.toString(), paramObject);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public abstract HttpHeaders set(String paramString, Iterable<?> paramIterable);
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public HttpHeaders set(CharSequence paramCharSequence, Iterable<?> paramIterable)
/*      */   {
/* 1601 */     return set(paramCharSequence.toString(), paramIterable);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public HttpHeaders set(HttpHeaders paramHttpHeaders)
/*      */   {
/* 1610 */     if (paramHttpHeaders == null) {
/* 1611 */       throw new NullPointerException("headers");
/*      */     }
/* 1613 */     clear();
/* 1614 */     for (Map.Entry localEntry : paramHttpHeaders) {
/* 1615 */       add((String)localEntry.getKey(), localEntry.getValue());
/*      */     }
/* 1617 */     return this;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public abstract HttpHeaders remove(String paramString);
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public HttpHeaders remove(CharSequence paramCharSequence)
/*      */   {
/* 1632 */     return remove(paramCharSequence.toString());
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public abstract HttpHeaders clear();
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   public boolean contains(String paramString1, String paramString2, boolean paramBoolean)
/*      */   {
/* 1646 */     List localList = getAll(paramString1);
/* 1647 */     if (localList.isEmpty()) {
/* 1648 */       return false;
/*      */     }
/*      */     
/* 1651 */     for (String str : localList) {
/* 1652 */       if (paramBoolean) {
/* 1653 */         if (equalsIgnoreCase(str, paramString2)) {
/* 1654 */           return true;
/*      */         }
/*      */       }
/* 1657 */       else if (str.equals(paramString2)) {
/* 1658 */         return true;
/*      */       }
/*      */     }
/*      */     
/* 1662 */     return false;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public boolean contains(CharSequence paramCharSequence1, CharSequence paramCharSequence2, boolean paramBoolean)
/*      */   {
/* 1674 */     return contains(paramCharSequence1.toString(), paramCharSequence2.toString(), paramBoolean);
/*      */   }
/*      */   
/*      */   public static final class Values
/*      */   {
/*      */     public static final String APPLICATION_X_WWW_FORM_URLENCODED = "application/x-www-form-urlencoded";
/*      */     public static final String BASE64 = "base64";
/*      */     public static final String BINARY = "binary";
/*      */     public static final String BOUNDARY = "boundary";
/*      */     public static final String BYTES = "bytes";
/*      */     public static final String CHARSET = "charset";
/*      */     public static final String CHUNKED = "chunked";
/*      */     public static final String CLOSE = "close";
/*      */     public static final String COMPRESS = "compress";
/*      */     public static final String CONTINUE = "100-continue";
/*      */     public static final String DEFLATE = "deflate";
/*      */     public static final String GZIP = "gzip";
/*      */     public static final String IDENTITY = "identity";
/*      */     public static final String KEEP_ALIVE = "keep-alive";
/*      */     public static final String MAX_AGE = "max-age";
/*      */     public static final String MAX_STALE = "max-stale";
/*      */     public static final String MIN_FRESH = "min-fresh";
/*      */     public static final String MULTIPART_FORM_DATA = "multipart/form-data";
/*      */     public static final String MUST_REVALIDATE = "must-revalidate";
/*      */     public static final String NO_CACHE = "no-cache";
/*      */     public static final String NO_STORE = "no-store";
/*      */     public static final String NO_TRANSFORM = "no-transform";
/*      */     public static final String NONE = "none";
/*      */     public static final String ONLY_IF_CACHED = "only-if-cached";
/*      */     public static final String PRIVATE = "private";
/*      */     public static final String PROXY_REVALIDATE = "proxy-revalidate";
/*      */     public static final String PUBLIC = "public";
/*      */     public static final String QUOTED_PRINTABLE = "quoted-printable";
/*      */     public static final String S_MAXAGE = "s-maxage";
/*      */     public static final String TRAILERS = "trailers";
/*      */     public static final String UPGRADE = "Upgrade";
/*      */     public static final String WEBSOCKET = "WebSocket";
/*      */   }
/*      */   
/*      */   public static final class Names
/*      */   {
/*      */     public static final String ACCEPT = "Accept";
/*      */     public static final String ACCEPT_CHARSET = "Accept-Charset";
/*      */     public static final String ACCEPT_ENCODING = "Accept-Encoding";
/*      */     public static final String ACCEPT_LANGUAGE = "Accept-Language";
/*      */     public static final String ACCEPT_RANGES = "Accept-Ranges";
/*      */     public static final String ACCEPT_PATCH = "Accept-Patch";
/*      */     public static final String ACCESS_CONTROL_ALLOW_CREDENTIALS = "Access-Control-Allow-Credentials";
/*      */     public static final String ACCESS_CONTROL_ALLOW_HEADERS = "Access-Control-Allow-Headers";
/*      */     public static final String ACCESS_CONTROL_ALLOW_METHODS = "Access-Control-Allow-Methods";
/*      */     public static final String ACCESS_CONTROL_ALLOW_ORIGIN = "Access-Control-Allow-Origin";
/*      */     public static final String ACCESS_CONTROL_EXPOSE_HEADERS = "Access-Control-Expose-Headers";
/*      */     public static final String ACCESS_CONTROL_MAX_AGE = "Access-Control-Max-Age";
/*      */     public static final String ACCESS_CONTROL_REQUEST_HEADERS = "Access-Control-Request-Headers";
/*      */     public static final String ACCESS_CONTROL_REQUEST_METHOD = "Access-Control-Request-Method";
/*      */     public static final String AGE = "Age";
/*      */     public static final String ALLOW = "Allow";
/*      */     public static final String AUTHORIZATION = "Authorization";
/*      */     public static final String CACHE_CONTROL = "Cache-Control";
/*      */     public static final String CONNECTION = "Connection";
/*      */     public static final String CONTENT_BASE = "Content-Base";
/*      */     public static final String CONTENT_ENCODING = "Content-Encoding";
/*      */     public static final String CONTENT_LANGUAGE = "Content-Language";
/*      */     public static final String CONTENT_LENGTH = "Content-Length";
/*      */     public static final String CONTENT_LOCATION = "Content-Location";
/*      */     public static final String CONTENT_TRANSFER_ENCODING = "Content-Transfer-Encoding";
/*      */     public static final String CONTENT_MD5 = "Content-MD5";
/*      */     public static final String CONTENT_RANGE = "Content-Range";
/*      */     public static final String CONTENT_TYPE = "Content-Type";
/*      */     public static final String COOKIE = "Cookie";
/*      */     public static final String DATE = "Date";
/*      */     public static final String ETAG = "ETag";
/*      */     public static final String EXPECT = "Expect";
/*      */     public static final String EXPIRES = "Expires";
/*      */     public static final String FROM = "From";
/*      */     public static final String HOST = "Host";
/*      */     public static final String IF_MATCH = "If-Match";
/*      */     public static final String IF_MODIFIED_SINCE = "If-Modified-Since";
/*      */     public static final String IF_NONE_MATCH = "If-None-Match";
/*      */     public static final String IF_RANGE = "If-Range";
/*      */     public static final String IF_UNMODIFIED_SINCE = "If-Unmodified-Since";
/*      */     public static final String LAST_MODIFIED = "Last-Modified";
/*      */     public static final String LOCATION = "Location";
/*      */     public static final String MAX_FORWARDS = "Max-Forwards";
/*      */     public static final String ORIGIN = "Origin";
/*      */     public static final String PRAGMA = "Pragma";
/*      */     public static final String PROXY_AUTHENTICATE = "Proxy-Authenticate";
/*      */     public static final String PROXY_AUTHORIZATION = "Proxy-Authorization";
/*      */     public static final String RANGE = "Range";
/*      */     public static final String REFERER = "Referer";
/*      */     public static final String RETRY_AFTER = "Retry-After";
/*      */     public static final String SEC_WEBSOCKET_KEY1 = "Sec-WebSocket-Key1";
/*      */     public static final String SEC_WEBSOCKET_KEY2 = "Sec-WebSocket-Key2";
/*      */     public static final String SEC_WEBSOCKET_LOCATION = "Sec-WebSocket-Location";
/*      */     public static final String SEC_WEBSOCKET_ORIGIN = "Sec-WebSocket-Origin";
/*      */     public static final String SEC_WEBSOCKET_PROTOCOL = "Sec-WebSocket-Protocol";
/*      */     public static final String SEC_WEBSOCKET_VERSION = "Sec-WebSocket-Version";
/*      */     public static final String SEC_WEBSOCKET_KEY = "Sec-WebSocket-Key";
/*      */     public static final String SEC_WEBSOCKET_ACCEPT = "Sec-WebSocket-Accept";
/*      */     public static final String SERVER = "Server";
/*      */     public static final String SET_COOKIE = "Set-Cookie";
/*      */     public static final String SET_COOKIE2 = "Set-Cookie2";
/*      */     public static final String TE = "TE";
/*      */     public static final String TRAILER = "Trailer";
/*      */     public static final String TRANSFER_ENCODING = "Transfer-Encoding";
/*      */     public static final String UPGRADE = "Upgrade";
/*      */     public static final String USER_AGENT = "User-Agent";
/*      */     public static final String VARY = "Vary";
/*      */     public static final String VIA = "Via";
/*      */     public static final String WARNING = "Warning";
/*      */     public static final String WEBSOCKET_LOCATION = "WebSocket-Location";
/*      */     public static final String WEBSOCKET_ORIGIN = "WebSocket-Origin";
/*      */     public static final String WEBSOCKET_PROTOCOL = "WebSocket-Protocol";
/*      */     public static final String WWW_AUTHENTICATE = "WWW-Authenticate";
/*      */   }
/*      */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\io\netty\handler\codec\http\HttpHeaders.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */
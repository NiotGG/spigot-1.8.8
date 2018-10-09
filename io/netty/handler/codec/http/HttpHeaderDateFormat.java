/*    */ package io.netty.handler.codec.http;
/*    */ 
/*    */ import io.netty.util.concurrent.FastThreadLocal;
/*    */ import java.text.ParsePosition;
/*    */ import java.text.SimpleDateFormat;
/*    */ import java.util.Date;
/*    */ import java.util.Locale;
/*    */ import java.util.TimeZone;
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
/*    */ final class HttpHeaderDateFormat
/*    */   extends SimpleDateFormat
/*    */ {
/*    */   private static final long serialVersionUID = -925286159755905325L;
/* 39 */   private final SimpleDateFormat format1 = new HttpHeaderDateFormatObsolete1();
/* 40 */   private final SimpleDateFormat format2 = new HttpHeaderDateFormatObsolete2();
/*    */   
/* 42 */   private static final FastThreadLocal<HttpHeaderDateFormat> dateFormatThreadLocal = new FastThreadLocal()
/*    */   {
/*    */     protected HttpHeaderDateFormat initialValue()
/*    */     {
/* 46 */       return new HttpHeaderDateFormat(null);
/*    */     }
/*    */   };
/*    */   
/*    */   static HttpHeaderDateFormat get() {
/* 51 */     return (HttpHeaderDateFormat)dateFormatThreadLocal.get();
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */   private HttpHeaderDateFormat()
/*    */   {
/* 59 */     super("E, dd MMM yyyy HH:mm:ss z", Locale.ENGLISH);
/* 60 */     setTimeZone(TimeZone.getTimeZone("GMT"));
/*    */   }
/*    */   
/*    */   public Date parse(String paramString, ParsePosition paramParsePosition)
/*    */   {
/* 65 */     Date localDate = super.parse(paramString, paramParsePosition);
/* 66 */     if (localDate == null) {
/* 67 */       localDate = this.format1.parse(paramString, paramParsePosition);
/*    */     }
/* 69 */     if (localDate == null) {
/* 70 */       localDate = this.format2.parse(paramString, paramParsePosition);
/*    */     }
/* 72 */     return localDate;
/*    */   }
/*    */   
/*    */ 
/*    */   private static final class HttpHeaderDateFormatObsolete1
/*    */     extends SimpleDateFormat
/*    */   {
/*    */     private static final long serialVersionUID = -3178072504225114298L;
/*    */     
/*    */     HttpHeaderDateFormatObsolete1()
/*    */     {
/* 83 */       super(Locale.ENGLISH);
/* 84 */       setTimeZone(TimeZone.getTimeZone("GMT"));
/*    */     }
/*    */   }
/*    */   
/*    */ 
/*    */   private static final class HttpHeaderDateFormatObsolete2
/*    */     extends SimpleDateFormat
/*    */   {
/*    */     private static final long serialVersionUID = 3010674519968303714L;
/*    */     
/*    */ 
/*    */     HttpHeaderDateFormatObsolete2()
/*    */     {
/* 97 */       super(Locale.ENGLISH);
/* 98 */       setTimeZone(TimeZone.getTimeZone("GMT"));
/*    */     }
/*    */   }
/*    */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\io\netty\handler\codec\http\HttpHeaderDateFormat.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */
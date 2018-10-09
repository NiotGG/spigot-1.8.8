/*     */ package org.apache.logging.log4j.core.net;
/*     */ 
/*     */ import org.apache.logging.log4j.util.EnglishEnums;
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
/*     */ public enum Facility
/*     */ {
/*  54 */   KERN(0), 
/*     */   
/*  56 */   USER(1), 
/*     */   
/*  58 */   MAIL(2), 
/*     */   
/*  60 */   DAEMON(3), 
/*     */   
/*  62 */   AUTH(4), 
/*     */   
/*  64 */   SYSLOG(5), 
/*     */   
/*  66 */   LPR(6), 
/*     */   
/*  68 */   NEWS(7), 
/*     */   
/*  70 */   UUCP(8), 
/*     */   
/*  72 */   CRON(9), 
/*     */   
/*  74 */   AUTHPRIV(10), 
/*     */   
/*  76 */   FTP(11), 
/*     */   
/*  78 */   NTP(12), 
/*     */   
/*  80 */   LOG_AUDIT(13), 
/*     */   
/*  82 */   LOG_ALERT(14), 
/*     */   
/*  84 */   CLOCK(15), 
/*     */   
/*  86 */   LOCAL0(16), 
/*     */   
/*  88 */   LOCAL1(17), 
/*     */   
/*  90 */   LOCAL2(18), 
/*     */   
/*  92 */   LOCAL3(19), 
/*     */   
/*  94 */   LOCAL4(20), 
/*     */   
/*  96 */   LOCAL5(21), 
/*     */   
/*  98 */   LOCAL6(22), 
/*     */   
/* 100 */   LOCAL7(23);
/*     */   
/*     */   private final int code;
/*     */   
/*     */   private Facility(int paramInt) {
/* 105 */     this.code = paramInt;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public static Facility toFacility(String paramString)
/*     */   {
/* 115 */     return toFacility(paramString, null);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public static Facility toFacility(String paramString, Facility paramFacility)
/*     */   {
/* 126 */     return (Facility)EnglishEnums.valueOf(Facility.class, paramString, paramFacility);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public int getCode()
/*     */   {
/* 134 */     return this.code;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public boolean isEqual(String paramString)
/*     */   {
/* 143 */     return name().equalsIgnoreCase(paramString);
/*     */   }
/*     */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\org\apache\logging\log4j\core\net\Facility.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */
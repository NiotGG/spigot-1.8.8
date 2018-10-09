/*     */ package io.netty.util.internal;
/*     */ 
/*     */ import io.netty.util.internal.logging.InternalLogger;
/*     */ import io.netty.util.internal.logging.InternalLoggerFactory;
/*     */ import java.security.AccessController;
/*     */ import java.security.PrivilegedAction;
/*     */ import java.util.logging.Level;
/*     */ import java.util.logging.Logger;
/*     */ import java.util.regex.Matcher;
/*     */ import java.util.regex.Pattern;
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
/*     */ public final class SystemPropertyUtil
/*     */ {
/*  38 */   private static final InternalLogger logger = InternalLoggerFactory.getInstance(SystemPropertyUtil.class);
/*  39 */   private static boolean initializedLogger = true;
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public static boolean contains(String paramString)
/*     */   {
/*  47 */     return get(paramString) != null;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public static String get(String paramString)
/*     */   {
/*  57 */     return get(paramString, null);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public static String get(String paramString1, String paramString2)
/*     */   {
/*  70 */     if (paramString1 == null) {
/*  71 */       throw new NullPointerException("key");
/*     */     }
/*  73 */     if (paramString1.isEmpty()) {
/*  74 */       throw new IllegalArgumentException("key must not be empty.");
/*     */     }
/*     */     
/*  77 */     String str = null;
/*     */     try {
/*  79 */       if (System.getSecurityManager() == null) {
/*  80 */         str = System.getProperty(paramString1);
/*     */       } else {
/*  82 */         str = (String)AccessController.doPrivileged(new PrivilegedAction()
/*     */         {
/*     */           public String run() {
/*  85 */             return System.getProperty(this.val$key);
/*     */           }
/*     */         });
/*     */       }
/*     */     } catch (Exception localException) {
/*  90 */       if (!loggedException) {
/*  91 */         log("Unable to retrieve a system property '" + paramString1 + "'; default values will be used.", localException);
/*  92 */         loggedException = true;
/*     */       }
/*     */     }
/*     */     
/*  96 */     if (str == null) {
/*  97 */       return paramString2;
/*     */     }
/*     */     
/* 100 */     return str;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public static boolean getBoolean(String paramString, boolean paramBoolean)
/*     */   {
/* 113 */     String str = get(paramString);
/* 114 */     if (str == null) {
/* 115 */       return paramBoolean;
/*     */     }
/*     */     
/* 118 */     str = str.trim().toLowerCase();
/* 119 */     if (str.isEmpty()) {
/* 120 */       return true;
/*     */     }
/*     */     
/* 123 */     if (("true".equals(str)) || ("yes".equals(str)) || ("1".equals(str))) {
/* 124 */       return true;
/*     */     }
/*     */     
/* 127 */     if (("false".equals(str)) || ("no".equals(str)) || ("0".equals(str))) {
/* 128 */       return false;
/*     */     }
/*     */     
/* 131 */     log("Unable to parse the boolean system property '" + paramString + "':" + str + " - " + "using the default value: " + paramBoolean);
/*     */     
/*     */ 
/*     */ 
/* 135 */     return paramBoolean;
/*     */   }
/*     */   
/* 138 */   private static final Pattern INTEGER_PATTERN = Pattern.compile("-?[0-9]+");
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   private static boolean loggedException;
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public static int getInt(String paramString, int paramInt)
/*     */   {
/* 150 */     String str = get(paramString);
/* 151 */     if (str == null) {
/* 152 */       return paramInt;
/*     */     }
/*     */     
/* 155 */     str = str.trim().toLowerCase();
/* 156 */     if (INTEGER_PATTERN.matcher(str).matches()) {
/*     */       try {
/* 158 */         return Integer.parseInt(str);
/*     */       }
/*     */       catch (Exception localException) {}
/*     */     }
/*     */     
/*     */ 
/* 164 */     log("Unable to parse the integer system property '" + paramString + "':" + str + " - " + "using the default value: " + paramInt);
/*     */     
/*     */ 
/*     */ 
/* 168 */     return paramInt;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public static long getLong(String paramString, long paramLong)
/*     */   {
/* 181 */     String str = get(paramString);
/* 182 */     if (str == null) {
/* 183 */       return paramLong;
/*     */     }
/*     */     
/* 186 */     str = str.trim().toLowerCase();
/* 187 */     if (INTEGER_PATTERN.matcher(str).matches()) {
/*     */       try {
/* 189 */         return Long.parseLong(str);
/*     */       }
/*     */       catch (Exception localException) {}
/*     */     }
/*     */     
/*     */ 
/* 195 */     log("Unable to parse the long integer system property '" + paramString + "':" + str + " - " + "using the default value: " + paramLong);
/*     */     
/*     */ 
/*     */ 
/* 199 */     return paramLong;
/*     */   }
/*     */   
/*     */   private static void log(String paramString) {
/* 203 */     if (initializedLogger) {
/* 204 */       logger.warn(paramString);
/*     */     }
/*     */     else {
/* 207 */       Logger.getLogger(SystemPropertyUtil.class.getName()).log(Level.WARNING, paramString);
/*     */     }
/*     */   }
/*     */   
/*     */   private static void log(String paramString, Exception paramException) {
/* 212 */     if (initializedLogger) {
/* 213 */       logger.warn(paramString, paramException);
/*     */     }
/*     */     else {
/* 216 */       Logger.getLogger(SystemPropertyUtil.class.getName()).log(Level.WARNING, paramString, paramException);
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\io\netty\util\internal\SystemPropertyUtil.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */
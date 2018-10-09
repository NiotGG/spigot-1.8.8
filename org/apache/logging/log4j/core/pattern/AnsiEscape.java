/*     */ package org.apache.logging.log4j.core.pattern;
/*     */ 
/*     */ import java.util.Arrays;
/*     */ import java.util.HashMap;
/*     */ import java.util.Locale;
/*     */ import java.util.Map;
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
/*     */ public enum AnsiEscape
/*     */ {
/*  36 */   PREFIX("\033["), 
/*     */   
/*     */ 
/*     */ 
/*  40 */   SUFFIX("m"), 
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*  45 */   SEPARATOR(";"), 
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*  50 */   NORMAL("0"), 
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*  55 */   BRIGHT("1"), 
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*  60 */   DIM("2"), 
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*  65 */   UNDERLINE("3"), 
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*  70 */   BLINK("5"), 
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*  75 */   REVERSE("7"), 
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*  80 */   HIDDEN("8"), 
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*  85 */   BLACK("30"), 
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*  90 */   FG_BLACK("30"), 
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*  95 */   RED("31"), 
/*     */   
/*     */ 
/*     */ 
/*     */ 
/* 100 */   FG_RED("31"), 
/*     */   
/*     */ 
/*     */ 
/*     */ 
/* 105 */   GREEN("32"), 
/*     */   
/*     */ 
/*     */ 
/*     */ 
/* 110 */   FG_GREEN("32"), 
/*     */   
/*     */ 
/*     */ 
/*     */ 
/* 115 */   YELLOW("33"), 
/*     */   
/*     */ 
/*     */ 
/*     */ 
/* 120 */   FG_YELLOW("33"), 
/*     */   
/*     */ 
/*     */ 
/*     */ 
/* 125 */   BLUE("34"), 
/*     */   
/*     */ 
/*     */ 
/*     */ 
/* 130 */   FG_BLUE("34"), 
/*     */   
/*     */ 
/*     */ 
/*     */ 
/* 135 */   MAGENTA("35"), 
/*     */   
/*     */ 
/*     */ 
/*     */ 
/* 140 */   FG_MAGENTA("35"), 
/*     */   
/*     */ 
/*     */ 
/*     */ 
/* 145 */   CYAN("36"), 
/*     */   
/*     */ 
/*     */ 
/*     */ 
/* 150 */   FG_CYAN("36"), 
/*     */   
/*     */ 
/*     */ 
/*     */ 
/* 155 */   WHITE("37"), 
/*     */   
/*     */ 
/*     */ 
/*     */ 
/* 160 */   FG_WHITE("37"), 
/*     */   
/*     */ 
/*     */ 
/*     */ 
/* 165 */   DEFAULT("39"), 
/*     */   
/*     */ 
/*     */ 
/*     */ 
/* 170 */   FG_DEFAULT("39"), 
/*     */   
/*     */ 
/*     */ 
/*     */ 
/* 175 */   BG_BLACK("40"), 
/*     */   
/*     */ 
/*     */ 
/*     */ 
/* 180 */   BG_RED("41"), 
/*     */   
/*     */ 
/*     */ 
/*     */ 
/* 185 */   BG_GREEN("42"), 
/*     */   
/*     */ 
/*     */ 
/*     */ 
/* 190 */   BG_YELLOW("43"), 
/*     */   
/*     */ 
/*     */ 
/*     */ 
/* 195 */   BG_BLUE("44"), 
/*     */   
/*     */ 
/*     */ 
/*     */ 
/* 200 */   BG_MAGENTA("45"), 
/*     */   
/*     */ 
/*     */ 
/*     */ 
/* 205 */   BG_CYAN("46"), 
/*     */   
/*     */ 
/*     */ 
/*     */ 
/* 210 */   BG_WHITE("47");
/*     */   
/*     */   private static final String WHITESPACE_REGEX = "\\s*";
/*     */   private final String code;
/*     */   
/*     */   private AnsiEscape(String paramString)
/*     */   {
/* 217 */     this.code = paramString;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public static String getDefaultStyle()
/*     */   {
/* 226 */     return PREFIX.getCode() + SUFFIX.getCode();
/*     */   }
/*     */   
/*     */   private static String toRegexSeparator(String paramString) {
/* 230 */     return "\\s*" + paramString + "\\s*";
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public String getCode()
/*     */   {
/* 239 */     return this.code;
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
/*     */   public static Map<String, String> createMap(String paramString, String[] paramArrayOfString)
/*     */   {
/* 263 */     return createMap(paramString.split(toRegexSeparator(",")), paramArrayOfString);
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
/*     */   public static Map<String, String> createMap(String[] paramArrayOfString1, String[] paramArrayOfString2)
/*     */   {
/* 289 */     String[] arrayOfString1 = paramArrayOfString2 != null ? (String[])paramArrayOfString2.clone() : new String[0];
/* 290 */     Arrays.sort(arrayOfString1);
/* 291 */     HashMap localHashMap = new HashMap();
/* 292 */     for (String str1 : paramArrayOfString1) {
/* 293 */       String[] arrayOfString3 = str1.split(toRegexSeparator("="));
/* 294 */       if (arrayOfString3.length > 1) {
/* 295 */         String str2 = arrayOfString3[0].toUpperCase(Locale.ENGLISH);
/* 296 */         String str3 = arrayOfString3[1];
/* 297 */         int k = Arrays.binarySearch(arrayOfString1, str2) < 0 ? 1 : 0;
/* 298 */         localHashMap.put(str2, k != 0 ? createSequence(str3.split("\\s")) : str3);
/*     */       }
/*     */     }
/* 301 */     return localHashMap;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public static String createSequence(String... paramVarArgs)
/*     */   {
/* 312 */     if (paramVarArgs == null) {
/* 313 */       return getDefaultStyle();
/*     */     }
/* 315 */     StringBuilder localStringBuilder = new StringBuilder(PREFIX.getCode());
/* 316 */     int i = 1;
/* 317 */     for (String str : paramVarArgs) {
/*     */       try {
/* 319 */         AnsiEscape localAnsiEscape = valueOf(str.trim().toUpperCase(Locale.ENGLISH));
/* 320 */         if (i == 0) {
/* 321 */           localStringBuilder.append(SEPARATOR.getCode());
/*     */         }
/* 323 */         i = 0;
/* 324 */         localStringBuilder.append(localAnsiEscape.getCode());
/*     */       }
/*     */       catch (Exception localException) {}
/*     */     }
/*     */     
/* 329 */     localStringBuilder.append(SUFFIX.getCode());
/* 330 */     return localStringBuilder.toString();
/*     */   }
/*     */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\org\apache\logging\log4j\core\pattern\AnsiEscape.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */
/*     */ package org.apache.commons.codec.language;
/*     */ 
/*     */ import java.util.regex.Matcher;
/*     */ import java.util.regex.Pattern;
/*     */ import org.apache.commons.codec.EncoderException;
/*     */ import org.apache.commons.codec.StringEncoder;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class Nysiis
/*     */   implements StringEncoder
/*     */ {
/*  72 */   private static final char[] CHARS_A = { 'A' };
/*  73 */   private static final char[] CHARS_AF = { 'A', 'F' };
/*  74 */   private static final char[] CHARS_C = { 'C' };
/*  75 */   private static final char[] CHARS_FF = { 'F', 'F' };
/*  76 */   private static final char[] CHARS_G = { 'G' };
/*  77 */   private static final char[] CHARS_N = { 'N' };
/*  78 */   private static final char[] CHARS_NN = { 'N', 'N' };
/*  79 */   private static final char[] CHARS_S = { 'S' };
/*  80 */   private static final char[] CHARS_SSS = { 'S', 'S', 'S' };
/*     */   
/*  82 */   private static final Pattern PAT_MAC = Pattern.compile("^MAC");
/*  83 */   private static final Pattern PAT_KN = Pattern.compile("^KN");
/*  84 */   private static final Pattern PAT_K = Pattern.compile("^K");
/*  85 */   private static final Pattern PAT_PH_PF = Pattern.compile("^(PH|PF)");
/*  86 */   private static final Pattern PAT_SCH = Pattern.compile("^SCH");
/*  87 */   private static final Pattern PAT_EE_IE = Pattern.compile("(EE|IE)$");
/*  88 */   private static final Pattern PAT_DT_ETC = Pattern.compile("(DT|RT|RD|NT|ND)$");
/*     */   
/*     */ 
/*     */   private static final char SPACE = ' ';
/*     */   
/*     */ 
/*     */   private static final int TRUE_LENGTH = 6;
/*     */   
/*     */   private final boolean strict;
/*     */   
/*     */ 
/*     */   private static boolean isVowel(char paramChar)
/*     */   {
/* 101 */     return (paramChar == 'A') || (paramChar == 'E') || (paramChar == 'I') || (paramChar == 'O') || (paramChar == 'U');
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
/*     */   private static char[] transcodeRemaining(char paramChar1, char paramChar2, char paramChar3, char paramChar4)
/*     */   {
/* 120 */     if ((paramChar2 == 'E') && (paramChar3 == 'V')) {
/* 121 */       return CHARS_AF;
/*     */     }
/*     */     
/*     */ 
/* 125 */     if (isVowel(paramChar2)) {
/* 126 */       return CHARS_A;
/*     */     }
/*     */     
/*     */ 
/* 130 */     if (paramChar2 == 'Q')
/* 131 */       return CHARS_G;
/* 132 */     if (paramChar2 == 'Z')
/* 133 */       return CHARS_S;
/* 134 */     if (paramChar2 == 'M') {
/* 135 */       return CHARS_N;
/*     */     }
/*     */     
/*     */ 
/* 139 */     if (paramChar2 == 'K') {
/* 140 */       if (paramChar3 == 'N') {
/* 141 */         return CHARS_NN;
/*     */       }
/* 143 */       return CHARS_C;
/*     */     }
/*     */     
/*     */ 
/*     */ 
/* 148 */     if ((paramChar2 == 'S') && (paramChar3 == 'C') && (paramChar4 == 'H')) {
/* 149 */       return CHARS_SSS;
/*     */     }
/*     */     
/*     */ 
/* 153 */     if ((paramChar2 == 'P') && (paramChar3 == 'H')) {
/* 154 */       return CHARS_FF;
/*     */     }
/*     */     
/*     */ 
/* 158 */     if ((paramChar2 == 'H') && ((!isVowel(paramChar1)) || (!isVowel(paramChar3)))) {
/* 159 */       return new char[] { paramChar1 };
/*     */     }
/*     */     
/*     */ 
/* 163 */     if ((paramChar2 == 'W') && (isVowel(paramChar1))) {
/* 164 */       return new char[] { paramChar1 };
/*     */     }
/*     */     
/* 167 */     return new char[] { paramChar2 };
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public Nysiis()
/*     */   {
/* 178 */     this(true);
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
/*     */   public Nysiis(boolean paramBoolean)
/*     */   {
/* 193 */     this.strict = paramBoolean;
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
/*     */   public Object encode(Object paramObject)
/*     */     throws EncoderException
/*     */   {
/* 211 */     if (!(paramObject instanceof String)) {
/* 212 */       throw new EncoderException("Parameter supplied to Nysiis encode is not of type java.lang.String");
/*     */     }
/* 214 */     return nysiis((String)paramObject);
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
/*     */   public String encode(String paramString)
/*     */   {
/* 228 */     return nysiis(paramString);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public boolean isStrict()
/*     */   {
/* 237 */     return this.strict;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public String nysiis(String paramString)
/*     */   {
/* 248 */     if (paramString == null) {
/* 249 */       return null;
/*     */     }
/*     */     
/*     */ 
/* 253 */     paramString = SoundexUtils.clean(paramString);
/*     */     
/* 255 */     if (paramString.length() == 0) {
/* 256 */       return paramString;
/*     */     }
/*     */     
/*     */ 
/*     */ 
/* 261 */     paramString = PAT_MAC.matcher(paramString).replaceFirst("MCC");
/* 262 */     paramString = PAT_KN.matcher(paramString).replaceFirst("NN");
/* 263 */     paramString = PAT_K.matcher(paramString).replaceFirst("C");
/* 264 */     paramString = PAT_PH_PF.matcher(paramString).replaceFirst("FF");
/* 265 */     paramString = PAT_SCH.matcher(paramString).replaceFirst("SSS");
/*     */     
/*     */ 
/*     */ 
/* 269 */     paramString = PAT_EE_IE.matcher(paramString).replaceFirst("Y");
/* 270 */     paramString = PAT_DT_ETC.matcher(paramString).replaceFirst("D");
/*     */     
/*     */ 
/* 273 */     StringBuilder localStringBuilder = new StringBuilder(paramString.length());
/* 274 */     localStringBuilder.append(paramString.charAt(0));
/*     */     
/*     */ 
/* 277 */     char[] arrayOfChar1 = paramString.toCharArray();
/* 278 */     int i = arrayOfChar1.length;
/*     */     char c1;
/* 280 */     for (int j = 1; j < i; j++) {
/* 281 */       c1 = j < i - 1 ? arrayOfChar1[(j + 1)] : ' ';
/* 282 */       char c2 = j < i - 2 ? arrayOfChar1[(j + 2)] : ' ';
/* 283 */       char[] arrayOfChar2 = transcodeRemaining(arrayOfChar1[(j - 1)], arrayOfChar1[j], c1, c2);
/* 284 */       System.arraycopy(arrayOfChar2, 0, arrayOfChar1, j, arrayOfChar2.length);
/*     */       
/*     */ 
/* 287 */       if (arrayOfChar1[j] != arrayOfChar1[(j - 1)]) {
/* 288 */         localStringBuilder.append(arrayOfChar1[j]);
/*     */       }
/*     */     }
/*     */     
/* 292 */     if (localStringBuilder.length() > 1) {
/* 293 */       j = localStringBuilder.charAt(localStringBuilder.length() - 1);
/*     */       
/*     */ 
/* 296 */       if (j == 83) {
/* 297 */         localStringBuilder.deleteCharAt(localStringBuilder.length() - 1);
/* 298 */         j = localStringBuilder.charAt(localStringBuilder.length() - 1);
/*     */       }
/*     */       
/* 301 */       if (localStringBuilder.length() > 2) {
/* 302 */         c1 = localStringBuilder.charAt(localStringBuilder.length() - 2);
/*     */         
/* 304 */         if ((c1 == 'A') && (j == 89)) {
/* 305 */           localStringBuilder.deleteCharAt(localStringBuilder.length() - 2);
/*     */         }
/*     */       }
/*     */       
/*     */ 
/* 310 */       if (j == 65) {
/* 311 */         localStringBuilder.deleteCharAt(localStringBuilder.length() - 1);
/*     */       }
/*     */     }
/*     */     
/* 315 */     String str = localStringBuilder.toString();
/* 316 */     return isStrict() ? str.substring(0, Math.min(6, str.length())) : str;
/*     */   }
/*     */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\org\apache\commons\codec\language\Nysiis.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */
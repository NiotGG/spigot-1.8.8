/*     */ package org.apache.commons.codec.language;
/*     */ 
/*     */ import java.util.Locale;
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
/*     */ public class Metaphone
/*     */   implements StringEncoder
/*     */ {
/*     */   private static final String VOWELS = "AEIOU";
/*     */   private static final String FRONTV = "EIY";
/*     */   private static final String VARSON = "CSPTG";
/*  73 */   private int maxCodeLen = 4;
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
/*     */   public String metaphone(String paramString)
/*     */   {
/*  93 */     int i = 0;
/*  94 */     if ((paramString == null) || (paramString.length() == 0)) {
/*  95 */       return "";
/*     */     }
/*     */     
/*  98 */     if (paramString.length() == 1) {
/*  99 */       return paramString.toUpperCase(Locale.ENGLISH);
/*     */     }
/*     */     
/* 102 */     char[] arrayOfChar = paramString.toUpperCase(Locale.ENGLISH).toCharArray();
/*     */     
/* 104 */     StringBuilder localStringBuilder1 = new StringBuilder(40);
/* 105 */     StringBuilder localStringBuilder2 = new StringBuilder(10);
/*     */     
/* 107 */     switch (arrayOfChar[0]) {
/*     */     case 'G': 
/*     */     case 'K': 
/*     */     case 'P': 
/* 111 */       if (arrayOfChar[1] == 'N') {
/* 112 */         localStringBuilder1.append(arrayOfChar, 1, arrayOfChar.length - 1);
/*     */       } else {
/* 114 */         localStringBuilder1.append(arrayOfChar);
/*     */       }
/* 116 */       break;
/*     */     case 'A': 
/* 118 */       if (arrayOfChar[1] == 'E') {
/* 119 */         localStringBuilder1.append(arrayOfChar, 1, arrayOfChar.length - 1);
/*     */       } else {
/* 121 */         localStringBuilder1.append(arrayOfChar);
/*     */       }
/* 123 */       break;
/*     */     case 'W': 
/* 125 */       if (arrayOfChar[1] == 'R') {
/* 126 */         localStringBuilder1.append(arrayOfChar, 1, arrayOfChar.length - 1);
/*     */ 
/*     */       }
/* 129 */       else if (arrayOfChar[1] == 'H') {
/* 130 */         localStringBuilder1.append(arrayOfChar, 1, arrayOfChar.length - 1);
/* 131 */         localStringBuilder1.setCharAt(0, 'W');
/*     */       } else {
/* 133 */         localStringBuilder1.append(arrayOfChar);
/*     */       }
/* 135 */       break;
/*     */     case 'X': 
/* 137 */       arrayOfChar[0] = 'S';
/* 138 */       localStringBuilder1.append(arrayOfChar);
/* 139 */       break;
/*     */     default: 
/* 141 */       localStringBuilder1.append(arrayOfChar);
/*     */     }
/*     */     
/* 144 */     int j = localStringBuilder1.length();
/* 145 */     int k = 0;
/*     */     
/* 147 */     while ((localStringBuilder2.length() < getMaxCodeLen()) && (k < j))
/*     */     {
/* 149 */       char c = localStringBuilder1.charAt(k);
/*     */       
/* 151 */       if ((c != 'C') && (isPreviousChar(localStringBuilder1, k, c))) {
/* 152 */         k++;
/*     */       } else {
/* 154 */         switch (c) {
/*     */         case 'A': 
/*     */         case 'E': 
/*     */         case 'I': 
/*     */         case 'O': 
/*     */         case 'U': 
/* 160 */           if (k == 0) {
/* 161 */             localStringBuilder2.append(c);
/*     */           }
/*     */           break;
/*     */         case 'B': 
/* 165 */           if ((!isPreviousChar(localStringBuilder1, k, 'M')) || (!isLastChar(j, k)))
/*     */           {
/*     */ 
/*     */ 
/* 169 */             localStringBuilder2.append(c); }
/* 170 */           break;
/*     */         
/*     */         case 'C': 
/* 173 */           if ((!isPreviousChar(localStringBuilder1, k, 'S')) || (isLastChar(j, k)) || ("EIY".indexOf(localStringBuilder1.charAt(k + 1)) < 0))
/*     */           {
/*     */ 
/*     */ 
/*     */ 
/* 178 */             if (regionMatch(localStringBuilder1, k, "CIA")) {
/* 179 */               localStringBuilder2.append('X');
/*     */ 
/*     */             }
/* 182 */             else if ((!isLastChar(j, k)) && ("EIY".indexOf(localStringBuilder1.charAt(k + 1)) >= 0))
/*     */             {
/* 184 */               localStringBuilder2.append('S');
/*     */ 
/*     */             }
/* 187 */             else if ((isPreviousChar(localStringBuilder1, k, 'S')) && (isNextChar(localStringBuilder1, k, 'H')))
/*     */             {
/* 189 */               localStringBuilder2.append('K');
/*     */ 
/*     */             }
/* 192 */             else if (isNextChar(localStringBuilder1, k, 'H')) {
/* 193 */               if ((k == 0) && (j >= 3) && (isVowel(localStringBuilder1, 2)))
/*     */               {
/*     */ 
/* 196 */                 localStringBuilder2.append('K');
/*     */               } else {
/* 198 */                 localStringBuilder2.append('X');
/*     */               }
/*     */             } else
/* 201 */               localStringBuilder2.append('K');
/*     */           }
/* 203 */           break;
/*     */         case 'D': 
/* 205 */           if ((!isLastChar(j, k + 1)) && (isNextChar(localStringBuilder1, k, 'G')) && ("EIY".indexOf(localStringBuilder1.charAt(k + 2)) >= 0))
/*     */           {
/*     */ 
/* 208 */             localStringBuilder2.append('J');k += 2;
/*     */           } else {
/* 210 */             localStringBuilder2.append('T');
/*     */           }
/* 212 */           break;
/*     */         case 'G': 
/* 214 */           if ((!isLastChar(j, k + 1)) || (!isNextChar(localStringBuilder1, k, 'H')))
/*     */           {
/*     */ 
/*     */ 
/* 218 */             if ((isLastChar(j, k + 1)) || (!isNextChar(localStringBuilder1, k, 'H')) || (isVowel(localStringBuilder1, k + 2)))
/*     */             {
/*     */ 
/*     */ 
/*     */ 
/* 223 */               if ((k <= 0) || ((!regionMatch(localStringBuilder1, k, "GN")) && (!regionMatch(localStringBuilder1, k, "GNED"))))
/*     */               {
/*     */ 
/*     */ 
/*     */ 
/* 228 */                 if (isPreviousChar(localStringBuilder1, k, 'G'))
/*     */                 {
/* 230 */                   i = 1;
/*     */                 } else {
/* 232 */                   i = 0;
/*     */                 }
/* 234 */                 if ((!isLastChar(j, k)) && ("EIY".indexOf(localStringBuilder1.charAt(k + 1)) >= 0) && (i == 0))
/*     */                 {
/*     */ 
/* 237 */                   localStringBuilder2.append('J');
/*     */                 } else
/* 239 */                   localStringBuilder2.append('K');
/*     */               } } }
/* 241 */           break;
/*     */         case 'H': 
/* 243 */           if (!isLastChar(j, k))
/*     */           {
/*     */ 
/* 246 */             if ((k <= 0) || ("CSPTG".indexOf(localStringBuilder1.charAt(k - 1)) < 0))
/*     */             {
/*     */ 
/*     */ 
/* 250 */               if (isVowel(localStringBuilder1, k + 1)) {
/* 251 */                 localStringBuilder2.append('H');
/*     */               }
/*     */             }
/*     */           }
/*     */           break;
/*     */         case 'F': case 'J': 
/*     */         case 'L': case 'M': 
/*     */         case 'N': 
/*     */         case 'R': 
/* 260 */           localStringBuilder2.append(c);
/* 261 */           break;
/*     */         case 'K': 
/* 263 */           if (k > 0) {
/* 264 */             if (!isPreviousChar(localStringBuilder1, k, 'C')) {
/* 265 */               localStringBuilder2.append(c);
/*     */             }
/*     */           } else {
/* 268 */             localStringBuilder2.append(c);
/*     */           }
/* 270 */           break;
/*     */         case 'P': 
/* 272 */           if (isNextChar(localStringBuilder1, k, 'H'))
/*     */           {
/* 274 */             localStringBuilder2.append('F');
/*     */           } else {
/* 276 */             localStringBuilder2.append(c);
/*     */           }
/* 278 */           break;
/*     */         case 'Q': 
/* 280 */           localStringBuilder2.append('K');
/* 281 */           break;
/*     */         case 'S': 
/* 283 */           if ((regionMatch(localStringBuilder1, k, "SH")) || (regionMatch(localStringBuilder1, k, "SIO")) || (regionMatch(localStringBuilder1, k, "SIA")))
/*     */           {
/*     */ 
/* 286 */             localStringBuilder2.append('X');
/*     */           } else {
/* 288 */             localStringBuilder2.append('S');
/*     */           }
/* 290 */           break;
/*     */         case 'T': 
/* 292 */           if ((regionMatch(localStringBuilder1, k, "TIA")) || (regionMatch(localStringBuilder1, k, "TIO")))
/*     */           {
/* 294 */             localStringBuilder2.append('X');
/*     */ 
/*     */           }
/* 297 */           else if (!regionMatch(localStringBuilder1, k, "TCH"))
/*     */           {
/*     */ 
/*     */ 
/*     */ 
/* 302 */             if (regionMatch(localStringBuilder1, k, "TH")) {
/* 303 */               localStringBuilder2.append('0');
/*     */             } else
/* 305 */               localStringBuilder2.append('T');
/*     */           }
/* 307 */           break;
/*     */         case 'V': 
/* 309 */           localStringBuilder2.append('F'); break;
/*     */         case 'W': 
/*     */         case 'Y': 
/* 312 */           if ((!isLastChar(j, k)) && (isVowel(localStringBuilder1, k + 1)))
/*     */           {
/* 314 */             localStringBuilder2.append(c);
/*     */           }
/*     */           break;
/*     */         case 'X': 
/* 318 */           localStringBuilder2.append('K');
/* 319 */           localStringBuilder2.append('S');
/* 320 */           break;
/*     */         case 'Z': 
/* 322 */           localStringBuilder2.append('S');
/* 323 */           break;
/*     */         }
/*     */         
/*     */         
/*     */ 
/* 328 */         k++;
/*     */       }
/* 330 */       if (localStringBuilder2.length() > getMaxCodeLen()) {
/* 331 */         localStringBuilder2.setLength(getMaxCodeLen());
/*     */       }
/*     */     }
/* 334 */     return localStringBuilder2.toString();
/*     */   }
/*     */   
/*     */   private boolean isVowel(StringBuilder paramStringBuilder, int paramInt) {
/* 338 */     return "AEIOU".indexOf(paramStringBuilder.charAt(paramInt)) >= 0;
/*     */   }
/*     */   
/*     */   private boolean isPreviousChar(StringBuilder paramStringBuilder, int paramInt, char paramChar) {
/* 342 */     boolean bool = false;
/* 343 */     if ((paramInt > 0) && (paramInt < paramStringBuilder.length()))
/*     */     {
/* 345 */       bool = paramStringBuilder.charAt(paramInt - 1) == paramChar;
/*     */     }
/* 347 */     return bool;
/*     */   }
/*     */   
/*     */   private boolean isNextChar(StringBuilder paramStringBuilder, int paramInt, char paramChar) {
/* 351 */     boolean bool = false;
/* 352 */     if ((paramInt >= 0) && (paramInt < paramStringBuilder.length() - 1))
/*     */     {
/* 354 */       bool = paramStringBuilder.charAt(paramInt + 1) == paramChar;
/*     */     }
/* 356 */     return bool;
/*     */   }
/*     */   
/*     */   private boolean regionMatch(StringBuilder paramStringBuilder, int paramInt, String paramString) {
/* 360 */     boolean bool = false;
/* 361 */     if ((paramInt >= 0) && (paramInt + paramString.length() - 1 < paramStringBuilder.length()))
/*     */     {
/* 363 */       String str = paramStringBuilder.substring(paramInt, paramInt + paramString.length());
/* 364 */       bool = str.equals(paramString);
/*     */     }
/* 366 */     return bool;
/*     */   }
/*     */   
/*     */   private boolean isLastChar(int paramInt1, int paramInt2) {
/* 370 */     return paramInt2 + 1 == paramInt1;
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
/* 388 */     if (!(paramObject instanceof String)) {
/* 389 */       throw new EncoderException("Parameter supplied to Metaphone encode is not of type java.lang.String");
/*     */     }
/* 391 */     return metaphone((String)paramObject);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public String encode(String paramString)
/*     */   {
/* 402 */     return metaphone(paramString);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public boolean isMetaphoneEqual(String paramString1, String paramString2)
/*     */   {
/* 414 */     return metaphone(paramString1).equals(metaphone(paramString2));
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public int getMaxCodeLen()
/*     */   {
/* 421 */     return this.maxCodeLen;
/*     */   }
/*     */   
/*     */ 
/*     */   public void setMaxCodeLen(int paramInt)
/*     */   {
/* 427 */     this.maxCodeLen = paramInt;
/*     */   }
/*     */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\org\apache\commons\codec\language\Metaphone.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */
/*     */ package org.apache.commons.lang3;
/*     */ 
/*     */ import java.util.Random;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class RandomStringUtils
/*     */ {
/*  43 */   private static final Random RANDOM = new Random();
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public static String random(int paramInt)
/*     */   {
/*  69 */     return random(paramInt, false, false);
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
/*     */   public static String randomAscii(int paramInt)
/*     */   {
/*  83 */     return random(paramInt, 32, 127, false, false);
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
/*     */   public static String randomAlphabetic(int paramInt)
/*     */   {
/*  97 */     return random(paramInt, true, false);
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
/*     */   public static String randomAlphanumeric(int paramInt)
/*     */   {
/* 111 */     return random(paramInt, true, true);
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
/*     */   public static String randomNumeric(int paramInt)
/*     */   {
/* 125 */     return random(paramInt, false, true);
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
/*     */   public static String random(int paramInt, boolean paramBoolean1, boolean paramBoolean2)
/*     */   {
/* 143 */     return random(paramInt, 0, 0, paramBoolean1, paramBoolean2);
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
/*     */   public static String random(int paramInt1, int paramInt2, int paramInt3, boolean paramBoolean1, boolean paramBoolean2)
/*     */   {
/* 163 */     return random(paramInt1, paramInt2, paramInt3, paramBoolean1, paramBoolean2, null, RANDOM);
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
/*     */   public static String random(int paramInt1, int paramInt2, int paramInt3, boolean paramBoolean1, boolean paramBoolean2, char... paramVarArgs)
/*     */   {
/* 187 */     return random(paramInt1, paramInt2, paramInt3, paramBoolean1, paramBoolean2, paramVarArgs, RANDOM);
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public static String random(int paramInt1, int paramInt2, int paramInt3, boolean paramBoolean1, boolean paramBoolean2, char[] paramArrayOfChar, Random paramRandom)
/*     */   {
/* 225 */     if (paramInt1 == 0)
/* 226 */       return "";
/* 227 */     if (paramInt1 < 0) {
/* 228 */       throw new IllegalArgumentException("Requested random string length " + paramInt1 + " is less than 0.");
/*     */     }
/* 230 */     if ((paramArrayOfChar != null) && (paramArrayOfChar.length == 0)) {
/* 231 */       throw new IllegalArgumentException("The chars array must not be empty");
/*     */     }
/*     */     
/* 234 */     if ((paramInt2 == 0) && (paramInt3 == 0)) {
/* 235 */       if (paramArrayOfChar != null) {
/* 236 */         paramInt3 = paramArrayOfChar.length;
/*     */       }
/* 238 */       else if ((!paramBoolean1) && (!paramBoolean2)) {
/* 239 */         paramInt3 = Integer.MAX_VALUE;
/*     */       } else {
/* 241 */         paramInt3 = 123;
/* 242 */         paramInt2 = 32;
/*     */       }
/*     */       
/*     */     }
/* 246 */     else if (paramInt3 <= paramInt2) {
/* 247 */       throw new IllegalArgumentException("Parameter end (" + paramInt3 + ") must be greater than start (" + paramInt2 + ")");
/*     */     }
/*     */     
/*     */ 
/* 251 */     char[] arrayOfChar = new char[paramInt1];
/* 252 */     int i = paramInt3 - paramInt2;
/*     */     
/* 254 */     while (paramInt1-- != 0) {
/*     */       int j;
/* 256 */       if (paramArrayOfChar == null) {
/* 257 */         j = (char)(paramRandom.nextInt(i) + paramInt2);
/*     */       } else {
/* 259 */         j = paramArrayOfChar[(paramRandom.nextInt(i) + paramInt2)];
/*     */       }
/* 261 */       if (((paramBoolean1) && (Character.isLetter(j))) || ((paramBoolean2) && (Character.isDigit(j))) || ((!paramBoolean1) && (!paramBoolean2)))
/*     */       {
/*     */ 
/* 264 */         if ((j >= 56320) && (j <= 57343)) {
/* 265 */           if (paramInt1 == 0) {
/* 266 */             paramInt1++;
/*     */           }
/*     */           else {
/* 269 */             arrayOfChar[paramInt1] = j;
/* 270 */             paramInt1--;
/* 271 */             arrayOfChar[paramInt1] = ((char)(55296 + paramRandom.nextInt(128)));
/*     */           }
/* 273 */         } else if ((j >= 55296) && (j <= 56191)) {
/* 274 */           if (paramInt1 == 0) {
/* 275 */             paramInt1++;
/*     */           }
/*     */           else {
/* 278 */             arrayOfChar[paramInt1] = ((char)(56320 + paramRandom.nextInt(128)));
/* 279 */             paramInt1--;
/* 280 */             arrayOfChar[paramInt1] = j;
/*     */           }
/* 282 */         } else if ((j >= 56192) && (j <= 56319))
/*     */         {
/* 284 */           paramInt1++;
/*     */         } else {
/* 286 */           arrayOfChar[paramInt1] = j;
/*     */         }
/*     */       } else {
/* 289 */         paramInt1++;
/*     */       }
/*     */     }
/* 292 */     return new String(arrayOfChar);
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
/*     */   public static String random(int paramInt, String paramString)
/*     */   {
/* 310 */     if (paramString == null) {
/* 311 */       return random(paramInt, 0, 0, false, false, null, RANDOM);
/*     */     }
/* 313 */     return random(paramInt, paramString.toCharArray());
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
/*     */   public static String random(int paramInt, char... paramVarArgs)
/*     */   {
/* 329 */     if (paramVarArgs == null) {
/* 330 */       return random(paramInt, 0, 0, false, false, null, RANDOM);
/*     */     }
/* 332 */     return random(paramInt, 0, paramVarArgs.length, false, false, paramVarArgs, RANDOM);
/*     */   }
/*     */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\org\apache\commons\lang3\RandomStringUtils.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */
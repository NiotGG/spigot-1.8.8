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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class ColognePhonetic
/*     */   implements StringEncoder
/*     */ {
/* 183 */   private static final char[] AEIJOUY = { 'A', 'E', 'I', 'J', 'O', 'U', 'Y' };
/* 184 */   private static final char[] SCZ = { 'S', 'C', 'Z' };
/* 185 */   private static final char[] WFPV = { 'W', 'F', 'P', 'V' };
/* 186 */   private static final char[] GKQ = { 'G', 'K', 'Q' };
/* 187 */   private static final char[] CKQ = { 'C', 'K', 'Q' };
/* 188 */   private static final char[] AHKLOQRUX = { 'A', 'H', 'K', 'L', 'O', 'Q', 'R', 'U', 'X' };
/* 189 */   private static final char[] SZ = { 'S', 'Z' };
/* 190 */   private static final char[] AHOUKQX = { 'A', 'H', 'O', 'U', 'K', 'Q', 'X' };
/* 191 */   private static final char[] TDX = { 'T', 'D', 'X' };
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   private abstract class CologneBuffer
/*     */   {
/*     */     protected final char[] data;
/*     */     
/*     */ 
/*     */ 
/* 202 */     protected int length = 0;
/*     */     
/*     */     public CologneBuffer(char[] paramArrayOfChar) {
/* 205 */       this.data = paramArrayOfChar;
/* 206 */       this.length = paramArrayOfChar.length;
/*     */     }
/*     */     
/*     */     public CologneBuffer(int paramInt) {
/* 210 */       this.data = new char[paramInt];
/* 211 */       this.length = 0;
/*     */     }
/*     */     
/*     */     protected abstract char[] copyData(int paramInt1, int paramInt2);
/*     */     
/*     */     public int length() {
/* 217 */       return this.length;
/*     */     }
/*     */     
/*     */     public String toString()
/*     */     {
/* 222 */       return new String(copyData(0, this.length));
/*     */     }
/*     */   }
/*     */   
/*     */   private class CologneOutputBuffer extends ColognePhonetic.CologneBuffer
/*     */   {
/*     */     public CologneOutputBuffer(int paramInt) {
/* 229 */       super(paramInt);
/*     */     }
/*     */     
/*     */     public void addRight(char paramChar) {
/* 233 */       this.data[this.length] = paramChar;
/* 234 */       this.length += 1;
/*     */     }
/*     */     
/*     */     protected char[] copyData(int paramInt1, int paramInt2)
/*     */     {
/* 239 */       char[] arrayOfChar = new char[paramInt2];
/* 240 */       System.arraycopy(this.data, paramInt1, arrayOfChar, 0, paramInt2);
/* 241 */       return arrayOfChar;
/*     */     }
/*     */   }
/*     */   
/*     */   private class CologneInputBuffer extends ColognePhonetic.CologneBuffer
/*     */   {
/*     */     public CologneInputBuffer(char[] paramArrayOfChar) {
/* 248 */       super(paramArrayOfChar);
/*     */     }
/*     */     
/*     */     public void addLeft(char paramChar) {
/* 252 */       this.length += 1;
/* 253 */       this.data[getNextPos()] = paramChar;
/*     */     }
/*     */     
/*     */     protected char[] copyData(int paramInt1, int paramInt2)
/*     */     {
/* 258 */       char[] arrayOfChar = new char[paramInt2];
/* 259 */       System.arraycopy(this.data, this.data.length - this.length + paramInt1, arrayOfChar, 0, paramInt2);
/* 260 */       return arrayOfChar;
/*     */     }
/*     */     
/*     */     public char getNextChar() {
/* 264 */       return this.data[getNextPos()];
/*     */     }
/*     */     
/*     */     protected int getNextPos() {
/* 268 */       return this.data.length - this.length;
/*     */     }
/*     */     
/*     */     public char removeNext() {
/* 272 */       char c = getNextChar();
/* 273 */       this.length -= 1;
/* 274 */       return c;
/*     */     }
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
/* 287 */   private static final char[][] PREPROCESS_MAP = { { 'Ä', 'A' }, { 'Ü', 'U' }, { 'Ö', 'O' }, { 'ß', 'S' } };
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   private static boolean arrayContains(char[] paramArrayOfChar, char paramChar)
/*     */   {
/* 298 */     for (char c : paramArrayOfChar) {
/* 299 */       if (c == paramChar) {
/* 300 */         return true;
/*     */       }
/*     */     }
/* 303 */     return false;
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
/*     */   public String colognePhonetic(String paramString)
/*     */   {
/* 318 */     if (paramString == null) {
/* 319 */       return null;
/*     */     }
/*     */     
/* 322 */     paramString = preprocess(paramString);
/*     */     
/* 324 */     CologneOutputBuffer localCologneOutputBuffer = new CologneOutputBuffer(paramString.length() * 2);
/* 325 */     CologneInputBuffer localCologneInputBuffer = new CologneInputBuffer(paramString.toCharArray());
/*     */     
/*     */ 
/*     */ 
/* 329 */     char c1 = '-';
/* 330 */     int i = 47;
/*     */     
/*     */ 
/*     */ 
/* 334 */     int j = localCologneInputBuffer.length();
/*     */     
/* 336 */     while (j > 0) {
/* 337 */       char c2 = localCologneInputBuffer.removeNext();
/*     */       char c3;
/* 339 */       if ((j = localCologneInputBuffer.length()) > 0) {
/* 340 */         c3 = localCologneInputBuffer.getNextChar();
/*     */       } else {
/* 342 */         c3 = '-';
/*     */       }
/*     */       char c4;
/* 345 */       if (arrayContains(AEIJOUY, c2)) {
/* 346 */         c4 = '0';
/* 347 */       } else if ((c2 == 'H') || (c2 < 'A') || (c2 > 'Z')) {
/* 348 */         if (i == 47) {
/*     */           continue;
/*     */         }
/* 351 */         c4 = '-';
/* 352 */       } else if ((c2 == 'B') || ((c2 == 'P') && (c3 != 'H'))) {
/* 353 */         c4 = '1';
/* 354 */       } else if (((c2 == 'D') || (c2 == 'T')) && (!arrayContains(SCZ, c3))) {
/* 355 */         c4 = '2';
/* 356 */       } else if (arrayContains(WFPV, c2)) {
/* 357 */         c4 = '3';
/* 358 */       } else if (arrayContains(GKQ, c2)) {
/* 359 */         c4 = '4';
/* 360 */       } else if ((c2 == 'X') && (!arrayContains(CKQ, c1))) {
/* 361 */         c4 = '4';
/* 362 */         localCologneInputBuffer.addLeft('S');
/* 363 */         j++;
/* 364 */       } else if ((c2 == 'S') || (c2 == 'Z')) {
/* 365 */         c4 = '8';
/* 366 */       } else if (c2 == 'C') {
/* 367 */         if (i == 47) {
/* 368 */           if (arrayContains(AHKLOQRUX, c3)) {
/* 369 */             c4 = '4';
/*     */           } else {
/* 371 */             c4 = '8';
/*     */           }
/*     */         }
/* 374 */         else if ((arrayContains(SZ, c1)) || (!arrayContains(AHOUKQX, c3))) {
/* 375 */           c4 = '8';
/*     */         } else {
/* 377 */           c4 = '4';
/*     */         }
/*     */       }
/* 380 */       else if (arrayContains(TDX, c2)) {
/* 381 */         c4 = '8';
/* 382 */       } else if (c2 == 'R') {
/* 383 */         c4 = '7';
/* 384 */       } else if (c2 == 'L') {
/* 385 */         c4 = '5';
/* 386 */       } else if ((c2 == 'M') || (c2 == 'N')) {
/* 387 */         c4 = '6';
/*     */       } else {
/* 389 */         c4 = c2;
/*     */       }
/*     */       
/* 392 */       if ((c4 != '-') && (((i != c4) && ((c4 != '0') || (i == 47))) || (c4 < '0') || (c4 > '8'))) {
/* 393 */         localCologneOutputBuffer.addRight(c4);
/*     */       }
/*     */       
/* 396 */       c1 = c2;
/* 397 */       i = c4;
/*     */     }
/* 399 */     return localCologneOutputBuffer.toString();
/*     */   }
/*     */   
/*     */   public Object encode(Object paramObject) throws EncoderException
/*     */   {
/* 404 */     if (!(paramObject instanceof String)) {
/* 405 */       throw new EncoderException("This method's parameter was expected to be of the type " + String.class.getName() + ". But actually it was of the type " + paramObject.getClass().getName() + ".");
/*     */     }
/*     */     
/*     */ 
/*     */ 
/*     */ 
/* 411 */     return encode((String)paramObject);
/*     */   }
/*     */   
/*     */   public String encode(String paramString)
/*     */   {
/* 416 */     return colognePhonetic(paramString);
/*     */   }
/*     */   
/*     */   public boolean isEncodeEqual(String paramString1, String paramString2) {
/* 420 */     return colognePhonetic(paramString1).equals(colognePhonetic(paramString2));
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   private String preprocess(String paramString)
/*     */   {
/* 427 */     paramString = paramString.toUpperCase(Locale.GERMAN);
/*     */     
/* 429 */     char[] arrayOfChar1 = paramString.toCharArray();
/*     */     
/* 431 */     for (int i = 0; i < arrayOfChar1.length; i++) {
/* 432 */       if (arrayOfChar1[i] > 'Z') {
/* 433 */         for (char[] arrayOfChar2 : PREPROCESS_MAP) {
/* 434 */           if (arrayOfChar1[i] == arrayOfChar2[0]) {
/* 435 */             arrayOfChar1[i] = arrayOfChar2[1];
/* 436 */             break;
/*     */           }
/*     */         }
/*     */       }
/*     */     }
/* 441 */     return new String(arrayOfChar1);
/*     */   }
/*     */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\org\apache\commons\codec\language\ColognePhonetic.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */
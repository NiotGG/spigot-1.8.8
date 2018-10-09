/*     */ package org.apache.commons.lang3.text;
/*     */ 
/*     */ import java.text.Format;
/*     */ import java.text.MessageFormat;
/*     */ import java.text.ParsePosition;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Collection;
/*     */ import java.util.Iterator;
/*     */ import java.util.Locale;
/*     */ import java.util.Map;
/*     */ import org.apache.commons.lang3.ObjectUtils;
/*     */ import org.apache.commons.lang3.Validate;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class ExtendedMessageFormat
/*     */   extends MessageFormat
/*     */ {
/*     */   private static final long serialVersionUID = -2362048321261811743L;
/*     */   private static final int HASH_SEED = 31;
/*     */   private static final String DUMMY_PATTERN = "";
/*     */   private static final String ESCAPED_QUOTE = "''";
/*     */   private static final char START_FMT = ',';
/*     */   private static final char END_FE = '}';
/*     */   private static final char START_FE = '{';
/*     */   private static final char QUOTE = '\'';
/*     */   private String toPattern;
/*     */   private final Map<String, ? extends FormatFactory> registry;
/*     */   
/*     */   public ExtendedMessageFormat(String paramString)
/*     */   {
/*  90 */     this(paramString, Locale.getDefault());
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public ExtendedMessageFormat(String paramString, Locale paramLocale)
/*     */   {
/* 101 */     this(paramString, paramLocale, null);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public ExtendedMessageFormat(String paramString, Map<String, ? extends FormatFactory> paramMap)
/*     */   {
/* 112 */     this(paramString, Locale.getDefault(), paramMap);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public ExtendedMessageFormat(String paramString, Locale paramLocale, Map<String, ? extends FormatFactory> paramMap)
/*     */   {
/* 124 */     super("");
/* 125 */     setLocale(paramLocale);
/* 126 */     this.registry = paramMap;
/* 127 */     applyPattern(paramString);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public String toPattern()
/*     */   {
/* 135 */     return this.toPattern;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public final void applyPattern(String paramString)
/*     */   {
/* 145 */     if (this.registry == null) {
/* 146 */       super.applyPattern(paramString);
/* 147 */       this.toPattern = super.toPattern();
/* 148 */       return;
/*     */     }
/* 150 */     ArrayList localArrayList1 = new ArrayList();
/* 151 */     ArrayList localArrayList2 = new ArrayList();
/* 152 */     StringBuilder localStringBuilder = new StringBuilder(paramString.length());
/*     */     
/* 154 */     ParsePosition localParsePosition = new ParsePosition(0);
/* 155 */     char[] arrayOfChar = paramString.toCharArray();
/* 156 */     int i = 0;
/* 157 */     int k; Object localObject1; Object localObject2; while (localParsePosition.getIndex() < paramString.length()) {
/* 158 */       switch (arrayOfChar[localParsePosition.getIndex()]) {
/*     */       case '\'': 
/* 160 */         appendQuotedString(paramString, localParsePosition, localStringBuilder, true);
/* 161 */         break;
/*     */       case '{': 
/* 163 */         i++;
/* 164 */         seekNonWs(paramString, localParsePosition);
/* 165 */         int j = localParsePosition.getIndex();
/* 166 */         k = readArgumentIndex(paramString, next(localParsePosition));
/* 167 */         localStringBuilder.append('{').append(k);
/* 168 */         seekNonWs(paramString, localParsePosition);
/* 169 */         localObject1 = null;
/* 170 */         localObject2 = null;
/* 171 */         if (arrayOfChar[localParsePosition.getIndex()] == ',') {
/* 172 */           localObject2 = parseFormatDescription(paramString, next(localParsePosition));
/*     */           
/* 174 */           localObject1 = getFormat((String)localObject2);
/* 175 */           if (localObject1 == null) {
/* 176 */             localStringBuilder.append(',').append((String)localObject2);
/*     */           }
/*     */         }
/* 179 */         localArrayList1.add(localObject1);
/* 180 */         localArrayList2.add(localObject1 == null ? null : localObject2);
/* 181 */         Validate.isTrue(localArrayList1.size() == i);
/* 182 */         Validate.isTrue(localArrayList2.size() == i);
/* 183 */         if (arrayOfChar[localParsePosition.getIndex()] != '}') {
/* 184 */           throw new IllegalArgumentException("Unreadable format element at position " + j);
/*     */         }
/*     */         break;
/*     */       }
/*     */       
/* 189 */       localStringBuilder.append(arrayOfChar[localParsePosition.getIndex()]);
/* 190 */       next(localParsePosition);
/*     */     }
/*     */     
/* 193 */     super.applyPattern(localStringBuilder.toString());
/* 194 */     this.toPattern = insertFormats(super.toPattern(), localArrayList2);
/* 195 */     if (containsElements(localArrayList1)) {
/* 196 */       Format[] arrayOfFormat = getFormats();
/*     */       
/*     */ 
/* 199 */       k = 0;
/* 200 */       for (localObject1 = localArrayList1.iterator(); ((Iterator)localObject1).hasNext(); k++) {
/* 201 */         localObject2 = (Format)((Iterator)localObject1).next();
/* 202 */         if (localObject2 != null) {
/* 203 */           arrayOfFormat[k] = localObject2;
/*     */         }
/*     */       }
/* 206 */       super.setFormats(arrayOfFormat);
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
/*     */   public void setFormat(int paramInt, Format paramFormat)
/*     */   {
/* 219 */     throw new UnsupportedOperationException();
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public void setFormatByArgumentIndex(int paramInt, Format paramFormat)
/*     */   {
/* 231 */     throw new UnsupportedOperationException();
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public void setFormats(Format[] paramArrayOfFormat)
/*     */   {
/* 242 */     throw new UnsupportedOperationException();
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public void setFormatsByArgumentIndex(Format[] paramArrayOfFormat)
/*     */   {
/* 253 */     throw new UnsupportedOperationException();
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public boolean equals(Object paramObject)
/*     */   {
/* 264 */     if (paramObject == this) {
/* 265 */       return true;
/*     */     }
/* 267 */     if (paramObject == null) {
/* 268 */       return false;
/*     */     }
/* 270 */     if (!super.equals(paramObject)) {
/* 271 */       return false;
/*     */     }
/* 273 */     if (ObjectUtils.notEqual(getClass(), paramObject.getClass())) {
/* 274 */       return false;
/*     */     }
/* 276 */     ExtendedMessageFormat localExtendedMessageFormat = (ExtendedMessageFormat)paramObject;
/* 277 */     if (ObjectUtils.notEqual(this.toPattern, localExtendedMessageFormat.toPattern)) {
/* 278 */       return false;
/*     */     }
/* 280 */     if (ObjectUtils.notEqual(this.registry, localExtendedMessageFormat.registry)) {
/* 281 */       return false;
/*     */     }
/* 283 */     return true;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public int hashCode()
/*     */   {
/* 292 */     int i = super.hashCode();
/* 293 */     i = 31 * i + ObjectUtils.hashCode(this.registry);
/* 294 */     i = 31 * i + ObjectUtils.hashCode(this.toPattern);
/* 295 */     return i;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   private Format getFormat(String paramString)
/*     */   {
/* 305 */     if (this.registry != null) {
/* 306 */       String str1 = paramString;
/* 307 */       String str2 = null;
/* 308 */       int i = paramString.indexOf(',');
/* 309 */       if (i > 0) {
/* 310 */         str1 = paramString.substring(0, i).trim();
/* 311 */         str2 = paramString.substring(i + 1).trim();
/*     */       }
/* 313 */       FormatFactory localFormatFactory = (FormatFactory)this.registry.get(str1);
/* 314 */       if (localFormatFactory != null) {
/* 315 */         return localFormatFactory.getFormat(str1, str2, getLocale());
/*     */       }
/*     */     }
/* 318 */     return null;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   private int readArgumentIndex(String paramString, ParsePosition paramParsePosition)
/*     */   {
/* 329 */     int i = paramParsePosition.getIndex();
/* 330 */     seekNonWs(paramString, paramParsePosition);
/* 331 */     StringBuilder localStringBuilder = new StringBuilder();
/* 332 */     int j = 0;
/* 333 */     for (; (j == 0) && (paramParsePosition.getIndex() < paramString.length()); next(paramParsePosition)) {
/* 334 */       char c = paramString.charAt(paramParsePosition.getIndex());
/* 335 */       if (Character.isWhitespace(c)) {
/* 336 */         seekNonWs(paramString, paramParsePosition);
/* 337 */         c = paramString.charAt(paramParsePosition.getIndex());
/* 338 */         if ((c != ',') && (c != '}')) {
/* 339 */           j = 1;
/* 340 */           continue;
/*     */         }
/*     */       }
/* 343 */       if (((c == ',') || (c == '}')) && (localStringBuilder.length() > 0)) {
/*     */         try {
/* 345 */           return Integer.parseInt(localStringBuilder.toString());
/*     */         }
/*     */         catch (NumberFormatException localNumberFormatException) {}
/*     */       }
/*     */       
/*     */ 
/* 351 */       j = !Character.isDigit(c) ? 1 : 0;
/* 352 */       localStringBuilder.append(c);
/*     */     }
/* 354 */     if (j != 0) {
/* 355 */       throw new IllegalArgumentException("Invalid format argument index at position " + i + ": " + paramString.substring(i, paramParsePosition.getIndex()));
/*     */     }
/*     */     
/*     */ 
/* 359 */     throw new IllegalArgumentException("Unterminated format element at position " + i);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   private String parseFormatDescription(String paramString, ParsePosition paramParsePosition)
/*     */   {
/* 371 */     int i = paramParsePosition.getIndex();
/* 372 */     seekNonWs(paramString, paramParsePosition);
/* 373 */     int j = paramParsePosition.getIndex();
/* 374 */     int k = 1;
/* 375 */     for (; paramParsePosition.getIndex() < paramString.length(); next(paramParsePosition)) {
/* 376 */       switch (paramString.charAt(paramParsePosition.getIndex())) {
/*     */       case '{': 
/* 378 */         k++;
/* 379 */         break;
/*     */       case '}': 
/* 381 */         k--;
/* 382 */         if (k == 0) {
/* 383 */           return paramString.substring(j, paramParsePosition.getIndex());
/*     */         }
/*     */         break;
/*     */       case '\'': 
/* 387 */         getQuotedString(paramString, paramParsePosition, false);
/*     */       }
/*     */       
/*     */     }
/*     */     
/*     */ 
/* 393 */     throw new IllegalArgumentException("Unterminated format element at position " + i);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   private String insertFormats(String paramString, ArrayList<String> paramArrayList)
/*     */   {
/* 405 */     if (!containsElements(paramArrayList)) {
/* 406 */       return paramString;
/*     */     }
/* 408 */     StringBuilder localStringBuilder = new StringBuilder(paramString.length() * 2);
/* 409 */     ParsePosition localParsePosition = new ParsePosition(0);
/* 410 */     int i = -1;
/* 411 */     int j = 0;
/* 412 */     while (localParsePosition.getIndex() < paramString.length()) {
/* 413 */       char c = paramString.charAt(localParsePosition.getIndex());
/* 414 */       switch (c) {
/*     */       case '\'': 
/* 416 */         appendQuotedString(paramString, localParsePosition, localStringBuilder, false);
/* 417 */         break;
/*     */       case '{': 
/* 419 */         j++;
/* 420 */         localStringBuilder.append('{').append(readArgumentIndex(paramString, next(localParsePosition)));
/*     */         
/* 422 */         if (j == 1) {
/* 423 */           i++;
/* 424 */           String str = (String)paramArrayList.get(i);
/* 425 */           if (str != null)
/* 426 */             localStringBuilder.append(',').append(str);
/*     */         }
/* 428 */         break;
/*     */       
/*     */       case '}': 
/* 431 */         j--;
/*     */       
/*     */       default: 
/* 434 */         localStringBuilder.append(c);
/* 435 */         next(localParsePosition);
/*     */       }
/*     */     }
/* 438 */     return localStringBuilder.toString();
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   private void seekNonWs(String paramString, ParsePosition paramParsePosition)
/*     */   {
/* 448 */     int i = 0;
/* 449 */     char[] arrayOfChar = paramString.toCharArray();
/*     */     do {
/* 451 */       i = StrMatcher.splitMatcher().isMatch(arrayOfChar, paramParsePosition.getIndex());
/* 452 */       paramParsePosition.setIndex(paramParsePosition.getIndex() + i);
/* 453 */     } while ((i > 0) && (paramParsePosition.getIndex() < paramString.length()));
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   private ParsePosition next(ParsePosition paramParsePosition)
/*     */   {
/* 463 */     paramParsePosition.setIndex(paramParsePosition.getIndex() + 1);
/* 464 */     return paramParsePosition;
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
/*     */   private StringBuilder appendQuotedString(String paramString, ParsePosition paramParsePosition, StringBuilder paramStringBuilder, boolean paramBoolean)
/*     */   {
/* 479 */     int i = paramParsePosition.getIndex();
/* 480 */     char[] arrayOfChar = paramString.toCharArray();
/* 481 */     if ((paramBoolean) && (arrayOfChar[i] == '\'')) {
/* 482 */       next(paramParsePosition);
/* 483 */       return paramStringBuilder == null ? null : paramStringBuilder.append('\'');
/*     */     }
/* 485 */     int j = i;
/* 486 */     for (int k = paramParsePosition.getIndex(); k < paramString.length(); k++) {
/* 487 */       if ((paramBoolean) && (paramString.substring(k).startsWith("''"))) {
/* 488 */         paramStringBuilder.append(arrayOfChar, j, paramParsePosition.getIndex() - j).append('\'');
/*     */         
/* 490 */         paramParsePosition.setIndex(k + "''".length());
/* 491 */         j = paramParsePosition.getIndex();
/*     */       }
/*     */       else {
/* 494 */         switch (arrayOfChar[paramParsePosition.getIndex()]) {
/*     */         case '\'': 
/* 496 */           next(paramParsePosition);
/* 497 */           return paramStringBuilder == null ? null : paramStringBuilder.append(arrayOfChar, j, paramParsePosition.getIndex() - j);
/*     */         }
/*     */         
/* 500 */         next(paramParsePosition);
/*     */       }
/*     */     }
/* 503 */     throw new IllegalArgumentException("Unterminated quoted string at position " + i);
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
/*     */   private void getQuotedString(String paramString, ParsePosition paramParsePosition, boolean paramBoolean)
/*     */   {
/* 516 */     appendQuotedString(paramString, paramParsePosition, null, paramBoolean);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   private boolean containsElements(Collection<?> paramCollection)
/*     */   {
/* 525 */     if ((paramCollection == null) || (paramCollection.isEmpty())) {
/* 526 */       return false;
/*     */     }
/* 528 */     for (Object localObject : paramCollection) {
/* 529 */       if (localObject != null) {
/* 530 */         return true;
/*     */       }
/*     */     }
/* 533 */     return false;
/*     */   }
/*     */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\org\apache\commons\lang3\text\ExtendedMessageFormat.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */
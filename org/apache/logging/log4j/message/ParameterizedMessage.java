/*     */ package org.apache.logging.log4j.message;
/*     */ 
/*     */ import java.text.SimpleDateFormat;
/*     */ import java.util.Arrays;
/*     */ import java.util.Collection;
/*     */ import java.util.Date;
/*     */ import java.util.HashSet;
/*     */ import java.util.Iterator;
/*     */ import java.util.Map;
/*     */ import java.util.Map.Entry;
/*     */ import java.util.Set;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class ParameterizedMessage
/*     */   implements Message
/*     */ {
/*     */   public static final String RECURSION_PREFIX = "[...";
/*     */   public static final String RECURSION_SUFFIX = "...]";
/*     */   public static final String ERROR_PREFIX = "[!!!";
/*     */   public static final String ERROR_SEPARATOR = "=>";
/*     */   public static final String ERROR_MSG_SEPARATOR = ":";
/*     */   public static final String ERROR_SUFFIX = "!!!]";
/*     */   private static final long serialVersionUID = -665975803997290697L;
/*     */   private static final int HASHVAL = 31;
/*     */   private static final char DELIM_START = '{';
/*     */   private static final char DELIM_STOP = '}';
/*     */   private static final char ESCAPE_CHAR = '\\';
/*     */   private final String messagePattern;
/*     */   private final String[] stringArgs;
/*     */   private transient Object[] argArray;
/*     */   private transient String formattedMessage;
/*     */   private transient Throwable throwable;
/*     */   
/*     */   public ParameterizedMessage(String paramString, String[] paramArrayOfString, Throwable paramThrowable)
/*     */   {
/*  85 */     this.messagePattern = paramString;
/*  86 */     this.stringArgs = paramArrayOfString;
/*  87 */     this.throwable = paramThrowable;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public ParameterizedMessage(String paramString, Object[] paramArrayOfObject, Throwable paramThrowable)
/*     */   {
/*  98 */     this.messagePattern = paramString;
/*  99 */     this.throwable = paramThrowable;
/* 100 */     this.stringArgs = parseArguments(paramArrayOfObject);
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
/*     */   public ParameterizedMessage(String paramString, Object[] paramArrayOfObject)
/*     */   {
/* 116 */     this.messagePattern = paramString;
/* 117 */     this.stringArgs = parseArguments(paramArrayOfObject);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public ParameterizedMessage(String paramString, Object paramObject)
/*     */   {
/* 126 */     this(paramString, new Object[] { paramObject });
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public ParameterizedMessage(String paramString, Object paramObject1, Object paramObject2)
/*     */   {
/* 136 */     this(paramString, new Object[] { paramObject1, paramObject2 });
/*     */   }
/*     */   
/*     */   private String[] parseArguments(Object[] paramArrayOfObject) {
/* 140 */     if (paramArrayOfObject == null) {
/* 141 */       return null;
/*     */     }
/* 143 */     int i = countArgumentPlaceholders(this.messagePattern);
/* 144 */     int j = paramArrayOfObject.length;
/* 145 */     if ((i < paramArrayOfObject.length) && 
/* 146 */       (this.throwable == null) && ((paramArrayOfObject[(paramArrayOfObject.length - 1)] instanceof Throwable))) {
/* 147 */       this.throwable = ((Throwable)paramArrayOfObject[(paramArrayOfObject.length - 1)]);
/* 148 */       j--;
/*     */     }
/*     */     
/* 151 */     this.argArray = new Object[j];
/* 152 */     for (int k = 0; k < j; k++) {
/* 153 */       this.argArray[k] = paramArrayOfObject[k];
/*     */     }
/*     */     
/*     */     String[] arrayOfString;
/* 157 */     if ((i == 1) && (this.throwable == null) && (paramArrayOfObject.length > 1))
/*     */     {
/* 159 */       arrayOfString = new String[1];
/* 160 */       arrayOfString[0] = deepToString(paramArrayOfObject);
/*     */     } else {
/* 162 */       arrayOfString = new String[j];
/* 163 */       for (int m = 0; m < arrayOfString.length; m++) {
/* 164 */         arrayOfString[m] = deepToString(paramArrayOfObject[m]);
/*     */       }
/*     */     }
/* 167 */     return arrayOfString;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public String getFormattedMessage()
/*     */   {
/* 176 */     if (this.formattedMessage == null) {
/* 177 */       this.formattedMessage = formatMessage(this.messagePattern, this.stringArgs);
/*     */     }
/* 179 */     return this.formattedMessage;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public String getFormat()
/*     */   {
/* 188 */     return this.messagePattern;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public Object[] getParameters()
/*     */   {
/* 197 */     if (this.argArray != null) {
/* 198 */       return this.argArray;
/*     */     }
/* 200 */     return this.stringArgs;
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
/*     */   public Throwable getThrowable()
/*     */   {
/* 214 */     return this.throwable;
/*     */   }
/*     */   
/*     */   protected String formatMessage(String paramString, String[] paramArrayOfString) {
/* 218 */     return format(paramString, paramArrayOfString);
/*     */   }
/*     */   
/*     */   public boolean equals(Object paramObject)
/*     */   {
/* 223 */     if (this == paramObject) {
/* 224 */       return true;
/*     */     }
/* 226 */     if ((paramObject == null) || (getClass() != paramObject.getClass())) {
/* 227 */       return false;
/*     */     }
/*     */     
/* 230 */     ParameterizedMessage localParameterizedMessage = (ParameterizedMessage)paramObject;
/*     */     
/* 232 */     if (this.messagePattern != null ? !this.messagePattern.equals(localParameterizedMessage.messagePattern) : localParameterizedMessage.messagePattern != null) {
/* 233 */       return false;
/*     */     }
/* 235 */     if (!Arrays.equals(this.stringArgs, localParameterizedMessage.stringArgs)) {
/* 236 */       return false;
/*     */     }
/*     */     
/*     */ 
/* 240 */     return true;
/*     */   }
/*     */   
/*     */   public int hashCode()
/*     */   {
/* 245 */     int i = this.messagePattern != null ? this.messagePattern.hashCode() : 0;
/* 246 */     i = 31 * i + (this.stringArgs != null ? Arrays.hashCode(this.stringArgs) : 0);
/* 247 */     return i;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public static String format(String paramString, Object[] paramArrayOfObject)
/*     */   {
/* 258 */     if ((paramString == null) || (paramArrayOfObject == null) || (paramArrayOfObject.length == 0)) {
/* 259 */       return paramString;
/*     */     }
/*     */     
/* 262 */     StringBuilder localStringBuilder = new StringBuilder();
/* 263 */     int i = 0;
/* 264 */     int j = 0;
/* 265 */     for (int k = 0; k < paramString.length(); k++) {
/* 266 */       char c = paramString.charAt(k);
/* 267 */       if (c == '\\') {
/* 268 */         i++;
/*     */       } else { int m;
/* 270 */         if ((c == '{') && 
/* 271 */           (k < paramString.length() - 1) && 
/* 272 */           (paramString.charAt(k + 1) == '}'))
/*     */         {
/* 274 */           m = i / 2;
/* 275 */           for (int n = 0; n < m; n++) {
/* 276 */             localStringBuilder.append('\\');
/*     */           }
/*     */           
/* 279 */           if (i % 2 == 1)
/*     */           {
/*     */ 
/* 282 */             localStringBuilder.append('{');
/* 283 */             localStringBuilder.append('}');
/*     */           }
/*     */           else {
/* 286 */             if (j < paramArrayOfObject.length) {
/* 287 */               localStringBuilder.append(paramArrayOfObject[j]);
/*     */             } else {
/* 289 */               localStringBuilder.append('{').append('}');
/*     */             }
/* 291 */             j++;
/*     */           }
/* 293 */           k++;
/* 294 */           i = 0;
/*     */ 
/*     */ 
/*     */         }
/*     */         else
/*     */         {
/*     */ 
/* 301 */           if (i > 0) {
/* 302 */             for (m = 0; m < i; m++) {
/* 303 */               localStringBuilder.append('\\');
/*     */             }
/* 305 */             i = 0;
/*     */           }
/* 307 */           localStringBuilder.append(c);
/*     */         }
/*     */       } }
/* 310 */     return localStringBuilder.toString();
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public static int countArgumentPlaceholders(String paramString)
/*     */   {
/* 320 */     if (paramString == null) {
/* 321 */       return 0;
/*     */     }
/*     */     
/* 324 */     int i = paramString.indexOf('{');
/*     */     
/* 326 */     if (i == -1)
/*     */     {
/* 328 */       return 0;
/*     */     }
/* 330 */     int j = 0;
/* 331 */     int k = 0;
/* 332 */     for (int m = 0; m < paramString.length(); m++) {
/* 333 */       int n = paramString.charAt(m);
/* 334 */       if (n == 92) {
/* 335 */         k = k == 0 ? 1 : 0;
/* 336 */       } else if (n == 123) {
/* 337 */         if ((k == 0) && 
/* 338 */           (m < paramString.length() - 1) && 
/* 339 */           (paramString.charAt(m + 1) == '}')) {
/* 340 */           j++;
/* 341 */           m++;
/*     */         }
/*     */         
/*     */ 
/* 345 */         k = 0;
/*     */       } else {
/* 347 */         k = 0;
/*     */       }
/*     */     }
/* 350 */     return j;
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
/*     */   public static String deepToString(Object paramObject)
/*     */   {
/* 370 */     if (paramObject == null) {
/* 371 */       return null;
/*     */     }
/* 373 */     if ((paramObject instanceof String)) {
/* 374 */       return (String)paramObject;
/*     */     }
/* 376 */     StringBuilder localStringBuilder = new StringBuilder();
/* 377 */     HashSet localHashSet = new HashSet();
/* 378 */     recursiveDeepToString(paramObject, localStringBuilder, localHashSet);
/* 379 */     return localStringBuilder.toString();
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
/*     */   private static void recursiveDeepToString(Object paramObject, StringBuilder paramStringBuilder, Set<String> paramSet)
/*     */   {
/* 404 */     if (paramObject == null) {
/* 405 */       paramStringBuilder.append("null");
/* 406 */       return;
/*     */     }
/* 408 */     if ((paramObject instanceof String)) {
/* 409 */       paramStringBuilder.append(paramObject);
/* 410 */       return;
/*     */     }
/*     */     
/* 413 */     Class localClass = paramObject.getClass();
/* 414 */     Object localObject1; Object localObject2; int i; Object localObject5; if (localClass.isArray()) {
/* 415 */       if (localClass == byte[].class) {
/* 416 */         paramStringBuilder.append(Arrays.toString((byte[])paramObject));
/* 417 */       } else if (localClass == short[].class) {
/* 418 */         paramStringBuilder.append(Arrays.toString((short[])paramObject));
/* 419 */       } else if (localClass == int[].class) {
/* 420 */         paramStringBuilder.append(Arrays.toString((int[])paramObject));
/* 421 */       } else if (localClass == long[].class) {
/* 422 */         paramStringBuilder.append(Arrays.toString((long[])paramObject));
/* 423 */       } else if (localClass == float[].class) {
/* 424 */         paramStringBuilder.append(Arrays.toString((float[])paramObject));
/* 425 */       } else if (localClass == double[].class) {
/* 426 */         paramStringBuilder.append(Arrays.toString((double[])paramObject));
/* 427 */       } else if (localClass == boolean[].class) {
/* 428 */         paramStringBuilder.append(Arrays.toString((boolean[])paramObject));
/* 429 */       } else if (localClass == char[].class) {
/* 430 */         paramStringBuilder.append(Arrays.toString((char[])paramObject));
/*     */       }
/*     */       else {
/* 433 */         localObject1 = identityToString(paramObject);
/* 434 */         if (paramSet.contains(localObject1)) {
/* 435 */           paramStringBuilder.append("[...").append((String)localObject1).append("...]");
/*     */         } else {
/* 437 */           paramSet.add(localObject1);
/* 438 */           localObject2 = (Object[])paramObject;
/* 439 */           paramStringBuilder.append("[");
/* 440 */           i = 1;
/* 441 */           for (localObject5 : localObject2) {
/* 442 */             if (i != 0) {
/* 443 */               i = 0;
/*     */             } else {
/* 445 */               paramStringBuilder.append(", ");
/*     */             }
/* 447 */             recursiveDeepToString(localObject5, paramStringBuilder, new HashSet(paramSet));
/*     */           }
/* 449 */           paramStringBuilder.append("]");
/*     */         }
/*     */       }
/*     */     } else { Object localObject4;
/* 453 */       if ((paramObject instanceof Map))
/*     */       {
/* 455 */         localObject1 = identityToString(paramObject);
/* 456 */         if (paramSet.contains(localObject1)) {
/* 457 */           paramStringBuilder.append("[...").append((String)localObject1).append("...]");
/*     */         } else {
/* 459 */           paramSet.add(localObject1);
/* 460 */           localObject2 = (Map)paramObject;
/* 461 */           paramStringBuilder.append("{");
/* 462 */           i = 1;
/* 463 */           for (??? = ((Map)localObject2).entrySet().iterator(); ((Iterator)???).hasNext();) { localObject4 = (Map.Entry)((Iterator)???).next();
/* 464 */             Map.Entry localEntry = (Map.Entry)localObject4;
/* 465 */             if (i != 0) {
/* 466 */               i = 0;
/*     */             } else {
/* 468 */               paramStringBuilder.append(", ");
/*     */             }
/* 470 */             localObject5 = localEntry.getKey();
/* 471 */             Object localObject6 = localEntry.getValue();
/* 472 */             recursiveDeepToString(localObject5, paramStringBuilder, new HashSet(paramSet));
/* 473 */             paramStringBuilder.append("=");
/* 474 */             recursiveDeepToString(localObject6, paramStringBuilder, new HashSet(paramSet));
/*     */           }
/* 476 */           paramStringBuilder.append("}");
/*     */         }
/* 478 */       } else if ((paramObject instanceof Collection))
/*     */       {
/* 480 */         localObject1 = identityToString(paramObject);
/* 481 */         if (paramSet.contains(localObject1)) {
/* 482 */           paramStringBuilder.append("[...").append((String)localObject1).append("...]");
/*     */         } else {
/* 484 */           paramSet.add(localObject1);
/* 485 */           localObject2 = (Collection)paramObject;
/* 486 */           paramStringBuilder.append("[");
/* 487 */           i = 1;
/* 488 */           for (??? = ((Collection)localObject2).iterator(); ((Iterator)???).hasNext();) { localObject4 = ((Iterator)???).next();
/* 489 */             if (i != 0) {
/* 490 */               i = 0;
/*     */             } else {
/* 492 */               paramStringBuilder.append(", ");
/*     */             }
/* 494 */             recursiveDeepToString(localObject4, paramStringBuilder, new HashSet(paramSet));
/*     */           }
/* 496 */           paramStringBuilder.append("]");
/*     */         }
/* 498 */       } else if ((paramObject instanceof Date)) {
/* 499 */         localObject1 = (Date)paramObject;
/* 500 */         localObject2 = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSZ");
/*     */         
/* 502 */         paramStringBuilder.append(((SimpleDateFormat)localObject2).format((Date)localObject1));
/*     */       }
/*     */       else {
/*     */         try {
/* 506 */           paramStringBuilder.append(paramObject.toString());
/*     */         } catch (Throwable localThrowable) {
/* 508 */           paramStringBuilder.append("[!!!");
/* 509 */           paramStringBuilder.append(identityToString(paramObject));
/* 510 */           paramStringBuilder.append("=>");
/* 511 */           localObject2 = localThrowable.getMessage();
/* 512 */           String str = localThrowable.getClass().getName();
/* 513 */           paramStringBuilder.append(str);
/* 514 */           if (!str.equals(localObject2)) {
/* 515 */             paramStringBuilder.append(":");
/* 516 */             paramStringBuilder.append((String)localObject2);
/*     */           }
/* 518 */           paramStringBuilder.append("!!!]");
/*     */         }
/*     */       }
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public static String identityToString(Object paramObject)
/*     */   {
/* 542 */     if (paramObject == null) {
/* 543 */       return null;
/*     */     }
/* 545 */     return paramObject.getClass().getName() + "@" + Integer.toHexString(System.identityHashCode(paramObject));
/*     */   }
/*     */   
/*     */   public String toString()
/*     */   {
/* 550 */     return "ParameterizedMessage[messagePattern=" + this.messagePattern + ", stringArgs=" + Arrays.toString(this.stringArgs) + ", throwable=" + this.throwable + "]";
/*     */   }
/*     */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\org\apache\logging\log4j\message\ParameterizedMessage.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */
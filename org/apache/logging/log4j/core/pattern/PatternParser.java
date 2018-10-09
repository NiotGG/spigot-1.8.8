/*     */ package org.apache.logging.log4j.core.pattern;
/*     */ 
/*     */ import java.lang.reflect.Method;
/*     */ import java.lang.reflect.Modifier;
/*     */ import java.util.ArrayList;
/*     */ import java.util.HashMap;
/*     */ import java.util.Iterator;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import org.apache.logging.log4j.Logger;
/*     */ import org.apache.logging.log4j.core.config.Configuration;
/*     */ import org.apache.logging.log4j.core.config.plugins.PluginManager;
/*     */ import org.apache.logging.log4j.core.config.plugins.PluginType;
/*     */ import org.apache.logging.log4j.core.helpers.Strings;
/*     */ import org.apache.logging.log4j.status.StatusLogger;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public final class PatternParser
/*     */ {
/*     */   private static final char ESCAPE_CHAR = '%';
/*     */   
/*     */   private static enum ParserState
/*     */   {
/*  53 */     LITERAL_STATE, 
/*     */     
/*     */ 
/*     */ 
/*     */ 
/*  58 */     CONVERTER_STATE, 
/*     */     
/*     */ 
/*     */ 
/*     */ 
/*  63 */     DOT_STATE, 
/*     */     
/*     */ 
/*     */ 
/*     */ 
/*  68 */     MIN_STATE, 
/*     */     
/*     */ 
/*     */ 
/*     */ 
/*  73 */     MAX_STATE;
/*     */     
/*     */     private ParserState() {} }
/*  76 */   private static final Logger LOGGER = ;
/*     */   
/*     */ 
/*     */   private static final int BUF_SIZE = 32;
/*     */   
/*     */ 
/*     */   private static final int DECIMAL = 10;
/*     */   
/*     */ 
/*     */   private final Configuration config;
/*     */   
/*     */   private final Map<String, Class<PatternConverter>> converterRules;
/*     */   
/*     */ 
/*     */   public PatternParser(String paramString)
/*     */   {
/*  92 */     this(null, paramString, null, null);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public PatternParser(Configuration paramConfiguration, String paramString, Class<?> paramClass)
/*     */   {
/* 102 */     this(paramConfiguration, paramString, paramClass, null);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public PatternParser(Configuration paramConfiguration, String paramString, Class<?> paramClass1, Class<?> paramClass2)
/*     */   {
/* 114 */     this.config = paramConfiguration;
/* 115 */     PluginManager localPluginManager = new PluginManager(paramString, paramClass1);
/* 116 */     localPluginManager.collectPlugins();
/* 117 */     Map localMap = localPluginManager.getPlugins();
/* 118 */     HashMap localHashMap = new HashMap();
/*     */     
/* 120 */     for (PluginType localPluginType : localMap.values()) {
/*     */       try
/*     */       {
/* 123 */         Class localClass = localPluginType.getPluginClass();
/* 124 */         if ((paramClass2 == null) || (paramClass2.isAssignableFrom(localClass)))
/*     */         {
/*     */ 
/* 127 */           ConverterKeys localConverterKeys = (ConverterKeys)localClass.getAnnotation(ConverterKeys.class);
/* 128 */           if (localConverterKeys != null) {
/* 129 */             for (String str : localConverterKeys.value())
/* 130 */               localHashMap.put(str, localClass);
/*     */           }
/*     */         }
/*     */       } catch (Exception localException) {
/* 134 */         LOGGER.error("Error processing plugin " + localPluginType.getElementName(), localException);
/*     */       }
/*     */     }
/* 137 */     this.converterRules = localHashMap;
/*     */   }
/*     */   
/*     */   public List<PatternFormatter> parse(String paramString) {
/* 141 */     return parse(paramString, false);
/*     */   }
/*     */   
/*     */   public List<PatternFormatter> parse(String paramString, boolean paramBoolean) {
/* 145 */     ArrayList localArrayList1 = new ArrayList();
/* 146 */     ArrayList localArrayList2 = new ArrayList();
/* 147 */     ArrayList localArrayList3 = new ArrayList();
/*     */     
/* 149 */     parse(paramString, localArrayList2, localArrayList3);
/*     */     
/* 151 */     Iterator localIterator = localArrayList3.iterator();
/* 152 */     boolean bool = false;
/*     */     
/* 154 */     for (Object localObject1 = localArrayList2.iterator(); ((Iterator)localObject1).hasNext();) { PatternConverter localPatternConverter = (PatternConverter)((Iterator)localObject1).next();
/*     */       Object localObject2;
/* 156 */       if ((localPatternConverter instanceof LogEventPatternConverter)) {
/* 157 */         localObject2 = (LogEventPatternConverter)localPatternConverter;
/* 158 */         bool |= ((LogEventPatternConverter)localObject2).handlesThrowable();
/*     */       } else {
/* 160 */         localObject2 = new LiteralPatternConverter(this.config, "");
/*     */       }
/*     */       
/*     */       FormattingInfo localFormattingInfo;
/* 164 */       if (localIterator.hasNext()) {
/* 165 */         localFormattingInfo = (FormattingInfo)localIterator.next();
/*     */       } else {
/* 167 */         localFormattingInfo = FormattingInfo.getDefault();
/*     */       }
/* 169 */       localArrayList1.add(new PatternFormatter((LogEventPatternConverter)localObject2, localFormattingInfo));
/*     */     }
/* 171 */     if ((paramBoolean) && (!bool)) {
/* 172 */       localObject1 = ExtendedThrowablePatternConverter.newInstance(null);
/* 173 */       localArrayList1.add(new PatternFormatter((LogEventPatternConverter)localObject1, FormattingInfo.getDefault()));
/*     */     }
/* 175 */     return localArrayList1;
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
/*     */   private static int extractConverter(char paramChar, String paramString, int paramInt, StringBuilder paramStringBuilder1, StringBuilder paramStringBuilder2)
/*     */   {
/* 197 */     paramStringBuilder1.setLength(0);
/*     */     
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/* 204 */     if (!Character.isUnicodeIdentifierStart(paramChar)) {
/* 205 */       return paramInt;
/*     */     }
/*     */     
/* 208 */     paramStringBuilder1.append(paramChar);
/*     */     
/* 210 */     while ((paramInt < paramString.length()) && (Character.isUnicodeIdentifierPart(paramString.charAt(paramInt)))) {
/* 211 */       paramStringBuilder1.append(paramString.charAt(paramInt));
/* 212 */       paramStringBuilder2.append(paramString.charAt(paramInt));
/* 213 */       paramInt++;
/*     */     }
/*     */     
/* 216 */     return paramInt;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   private static int extractOptions(String paramString, int paramInt, List<String> paramList)
/*     */   {
/* 228 */     while ((paramInt < paramString.length()) && (paramString.charAt(paramInt) == '{')) {
/* 229 */       int i = paramInt++;
/*     */       
/* 231 */       int j = 0;
/*     */       int k;
/* 233 */       do { k = paramString.indexOf('}', paramInt);
/* 234 */         if (k != -1) {
/* 235 */           int m = paramString.indexOf("{", paramInt);
/* 236 */           if ((m != -1) && (m < k)) {
/* 237 */             paramInt = k + 1;
/* 238 */             j++;
/* 239 */           } else if (j > 0) {
/* 240 */             j--;
/*     */           }
/*     */         }
/* 243 */       } while (j > 0);
/*     */       
/* 245 */       if (k == -1) {
/*     */         break;
/*     */       }
/*     */       
/* 249 */       String str = paramString.substring(i + 1, k);
/* 250 */       paramList.add(str);
/* 251 */       paramInt = k + 1;
/*     */     }
/*     */     
/* 254 */     return paramInt;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public void parse(String paramString, List<PatternConverter> paramList, List<FormattingInfo> paramList1)
/*     */   {
/* 266 */     if (paramString == null) {
/* 267 */       throw new NullPointerException("pattern");
/*     */     }
/*     */     
/* 270 */     StringBuilder localStringBuilder = new StringBuilder(32);
/*     */     
/* 272 */     int i = paramString.length();
/* 273 */     ParserState localParserState = ParserState.LITERAL_STATE;
/*     */     
/* 275 */     int j = 0;
/* 276 */     FormattingInfo localFormattingInfo = FormattingInfo.getDefault();
/*     */     
/* 278 */     while (j < i) {
/* 279 */       int k = paramString.charAt(j++);
/*     */       
/* 281 */       switch (localParserState)
/*     */       {
/*     */ 
/*     */       case LITERAL_STATE: 
/* 285 */         if (j == i) {
/* 286 */           localStringBuilder.append(k);
/*     */ 
/*     */ 
/*     */ 
/*     */         }
/* 291 */         else if (k == 37)
/*     */         {
/* 293 */           switch (paramString.charAt(j)) {
/*     */           case '%': 
/* 295 */             localStringBuilder.append(k);
/* 296 */             j++;
/*     */             
/* 298 */             break;
/*     */           
/*     */ 
/*     */           default: 
/* 302 */             if (localStringBuilder.length() != 0) {
/* 303 */               paramList.add(new LiteralPatternConverter(this.config, localStringBuilder.toString()));
/*     */               
/* 305 */               paramList1.add(FormattingInfo.getDefault());
/*     */             }
/*     */             
/* 308 */             localStringBuilder.setLength(0);
/* 309 */             localStringBuilder.append(k);
/* 310 */             localParserState = ParserState.CONVERTER_STATE;
/* 311 */             localFormattingInfo = FormattingInfo.getDefault();break;
/*     */           }
/*     */         } else {
/* 314 */           localStringBuilder.append(k);
/*     */         }
/*     */         
/* 317 */         break;
/*     */       
/*     */       case CONVERTER_STATE: 
/* 320 */         localStringBuilder.append(k);
/*     */         
/* 322 */         switch (k) {
/*     */         case 45: 
/* 324 */           localFormattingInfo = new FormattingInfo(true, localFormattingInfo.getMinLength(), localFormattingInfo.getMaxLength());
/*     */           
/*     */ 
/* 327 */           break;
/*     */         
/*     */         case 46: 
/* 330 */           localParserState = ParserState.DOT_STATE;
/* 331 */           break;
/*     */         
/*     */ 
/*     */         default: 
/* 335 */           if ((k >= 48) && (k <= 57)) {
/* 336 */             localFormattingInfo = new FormattingInfo(localFormattingInfo.isLeftAligned(), k - 48, localFormattingInfo.getMaxLength());
/*     */             
/* 338 */             localParserState = ParserState.MIN_STATE;
/*     */           } else {
/* 340 */             j = finalizeConverter(k, paramString, j, localStringBuilder, localFormattingInfo, this.converterRules, paramList, paramList1);
/*     */             
/*     */ 
/*     */ 
/* 344 */             localParserState = ParserState.LITERAL_STATE;
/* 345 */             localFormattingInfo = FormattingInfo.getDefault();
/* 346 */             localStringBuilder.setLength(0);
/*     */           }
/*     */           break;
/*     */         }
/* 350 */         break;
/*     */       
/*     */       case MIN_STATE: 
/* 353 */         localStringBuilder.append(k);
/*     */         
/* 355 */         if ((k >= 48) && (k <= 57))
/*     */         {
/* 357 */           localFormattingInfo = new FormattingInfo(localFormattingInfo.isLeftAligned(), localFormattingInfo.getMinLength() * 10 + k - 48, localFormattingInfo.getMaxLength());
/*     */ 
/*     */         }
/* 360 */         else if (k == 46) {
/* 361 */           localParserState = ParserState.DOT_STATE;
/*     */         } else {
/* 363 */           j = finalizeConverter(k, paramString, j, localStringBuilder, localFormattingInfo, this.converterRules, paramList, paramList1);
/*     */           
/* 365 */           localParserState = ParserState.LITERAL_STATE;
/* 366 */           localFormattingInfo = FormattingInfo.getDefault();
/* 367 */           localStringBuilder.setLength(0);
/*     */         }
/*     */         
/* 370 */         break;
/*     */       
/*     */       case DOT_STATE: 
/* 373 */         localStringBuilder.append(k);
/*     */         
/* 375 */         if ((k >= 48) && (k <= 57)) {
/* 376 */           localFormattingInfo = new FormattingInfo(localFormattingInfo.isLeftAligned(), localFormattingInfo.getMinLength(), k - 48);
/*     */           
/* 378 */           localParserState = ParserState.MAX_STATE;
/*     */         } else {
/* 380 */           LOGGER.error("Error occurred in position " + j + ".\n Was expecting digit, instead got char \"" + k + "\".");
/*     */           
/*     */ 
/* 383 */           localParserState = ParserState.LITERAL_STATE;
/*     */         }
/*     */         
/* 386 */         break;
/*     */       
/*     */       case MAX_STATE: 
/* 389 */         localStringBuilder.append(k);
/*     */         
/* 391 */         if ((k >= 48) && (k <= 57))
/*     */         {
/* 393 */           localFormattingInfo = new FormattingInfo(localFormattingInfo.isLeftAligned(), localFormattingInfo.getMinLength(), localFormattingInfo.getMaxLength() * 10 + k - 48);
/*     */         }
/*     */         else
/*     */         {
/* 397 */           j = finalizeConverter(k, paramString, j, localStringBuilder, localFormattingInfo, this.converterRules, paramList, paramList1);
/*     */           
/* 399 */           localParserState = ParserState.LITERAL_STATE;
/* 400 */           localFormattingInfo = FormattingInfo.getDefault();
/* 401 */           localStringBuilder.setLength(0);
/*     */         }
/*     */         
/*     */         break;
/*     */       }
/*     */       
/*     */     }
/*     */     
/* 409 */     if (localStringBuilder.length() != 0) {
/* 410 */       paramList.add(new LiteralPatternConverter(this.config, localStringBuilder.toString()));
/* 411 */       paramList1.add(FormattingInfo.getDefault());
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
/*     */   private PatternConverter createConverter(String paramString, StringBuilder paramStringBuilder, Map<String, Class<PatternConverter>> paramMap, List<String> paramList)
/*     */   {
/* 428 */     String str = paramString;
/* 429 */     Class localClass = null;
/*     */     
/* 431 */     for (int i = paramString.length(); (i > 0) && (localClass == null); i--) {
/* 432 */       str = str.substring(0, i);
/*     */       
/* 434 */       if ((localClass == null) && (paramMap != null)) {
/* 435 */         localClass = (Class)paramMap.get(str);
/*     */       }
/*     */     }
/*     */     
/* 439 */     if (localClass == null) {
/* 440 */       LOGGER.error("Unrecognized format specifier [" + paramString + "]");
/* 441 */       return null;
/*     */     }
/*     */     
/*     */ 
/*     */ 
/* 446 */     Method[] arrayOfMethod = localClass.getDeclaredMethods();
/* 447 */     Object localObject1 = null;
/* 448 */     for (Object localObject4 : arrayOfMethod) {
/* 449 */       if ((Modifier.isStatic(((Method)localObject4).getModifiers())) && (((Method)localObject4).getDeclaringClass().equals(localClass)) && (((Method)localObject4).getName().equals("newInstance")))
/*     */       {
/* 451 */         if (localObject1 == null) {
/* 452 */           localObject1 = localObject4;
/* 453 */         } else if (((Method)localObject4).getReturnType().equals(((Method)localObject1).getReturnType())) {
/* 454 */           LOGGER.error("Class " + localClass + " cannot contain multiple static newInstance methods");
/* 455 */           return null;
/*     */         }
/*     */       }
/*     */     }
/* 459 */     if (localObject1 == null) {
/* 460 */       LOGGER.error("Class " + localClass + " does not contain a static newInstance method");
/* 461 */       return null;
/*     */     }
/*     */     
/* 464 */     ??? = ((Method)localObject1).getParameterTypes();
/* 465 */     Object[] arrayOfObject = ???.length > 0 ? new Object[???.length] : null;
/*     */     
/* 467 */     if (arrayOfObject != null) {
/* 468 */       ??? = 0;
/* 469 */       int m = 0;
/* 470 */       for (Object localObject6 : ???) {
/* 471 */         if ((((Class)localObject6).isArray()) && (((Class)localObject6).getName().equals("[Ljava.lang.String;"))) {
/* 472 */           String[] arrayOfString = (String[])paramList.toArray(new String[paramList.size()]);
/* 473 */           arrayOfObject[???] = arrayOfString;
/* 474 */         } else if (((Class)localObject6).isAssignableFrom(Configuration.class)) {
/* 475 */           arrayOfObject[???] = this.config;
/*     */         } else {
/* 477 */           LOGGER.error("Unknown parameter type " + ((Class)localObject6).getName() + " for static newInstance method of " + localClass.getName());
/*     */           
/* 479 */           m = 1;
/*     */         }
/* 481 */         ???++;
/*     */       }
/* 483 */       if (m != 0) {
/* 484 */         return null;
/*     */       }
/*     */     }
/*     */     try
/*     */     {
/* 489 */       Object localObject3 = ((Method)localObject1).invoke(null, arrayOfObject);
/*     */       
/* 491 */       if ((localObject3 instanceof PatternConverter)) {
/* 492 */         paramStringBuilder.delete(0, paramStringBuilder.length() - (paramString.length() - str.length()));
/*     */         
/*     */ 
/* 495 */         return (PatternConverter)localObject3;
/*     */       }
/* 497 */       LOGGER.warn("Class " + localClass.getName() + " does not extend PatternConverter.");
/*     */     }
/*     */     catch (Exception localException) {
/* 500 */       LOGGER.error("Error creating converter for " + paramString, localException);
/*     */     }
/*     */     
/* 503 */     return null;
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
/*     */   private int finalizeConverter(char paramChar, String paramString, int paramInt, StringBuilder paramStringBuilder, FormattingInfo paramFormattingInfo, Map<String, Class<PatternConverter>> paramMap, List<PatternConverter> paramList, List<FormattingInfo> paramList1)
/*     */   {
/* 523 */     StringBuilder localStringBuilder1 = new StringBuilder();
/* 524 */     paramInt = extractConverter(paramChar, paramString, paramInt, localStringBuilder1, paramStringBuilder);
/*     */     
/* 526 */     String str = localStringBuilder1.toString();
/*     */     
/* 528 */     ArrayList localArrayList = new ArrayList();
/* 529 */     paramInt = extractOptions(paramString, paramInt, localArrayList);
/*     */     
/* 531 */     PatternConverter localPatternConverter = createConverter(str, paramStringBuilder, paramMap, localArrayList);
/*     */     
/* 533 */     if (localPatternConverter == null)
/*     */     {
/*     */       StringBuilder localStringBuilder2;
/* 536 */       if (Strings.isEmpty(str)) {
/* 537 */         localStringBuilder2 = new StringBuilder("Empty conversion specifier starting at position ");
/*     */       } else {
/* 539 */         localStringBuilder2 = new StringBuilder("Unrecognized conversion specifier [");
/* 540 */         localStringBuilder2.append(str);
/* 541 */         localStringBuilder2.append("] starting at position ");
/*     */       }
/*     */       
/* 544 */       localStringBuilder2.append(Integer.toString(paramInt));
/* 545 */       localStringBuilder2.append(" in conversion pattern.");
/*     */       
/* 547 */       LOGGER.error(localStringBuilder2.toString());
/*     */       
/* 549 */       paramList.add(new LiteralPatternConverter(this.config, paramStringBuilder.toString()));
/* 550 */       paramList1.add(FormattingInfo.getDefault());
/*     */     } else {
/* 552 */       paramList.add(localPatternConverter);
/* 553 */       paramList1.add(paramFormattingInfo);
/*     */       
/* 555 */       if (paramStringBuilder.length() > 0) {
/* 556 */         paramList.add(new LiteralPatternConverter(this.config, paramStringBuilder.toString()));
/* 557 */         paramList1.add(FormattingInfo.getDefault());
/*     */       }
/*     */     }
/*     */     
/* 561 */     paramStringBuilder.setLength(0);
/*     */     
/* 563 */     return paramInt;
/*     */   }
/*     */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\org\apache\logging\log4j\core\pattern\PatternParser.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */
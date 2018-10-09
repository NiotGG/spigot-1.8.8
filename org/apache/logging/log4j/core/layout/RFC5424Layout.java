/*     */ package org.apache.logging.log4j.core.layout;
/*     */ 
/*     */ import java.nio.charset.Charset;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Calendar;
/*     */ import java.util.GregorianCalendar;
/*     */ import java.util.HashMap;
/*     */ import java.util.Iterator;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import java.util.Map.Entry;
/*     */ import java.util.Set;
/*     */ import java.util.SortedMap;
/*     */ import java.util.TreeMap;
/*     */ import java.util.regex.Matcher;
/*     */ import java.util.regex.Pattern;
/*     */ import org.apache.logging.log4j.Level;
/*     */ import org.apache.logging.log4j.Logger;
/*     */ import org.apache.logging.log4j.LoggingException;
/*     */ import org.apache.logging.log4j.core.LogEvent;
/*     */ import org.apache.logging.log4j.core.appender.TLSSyslogFrame;
/*     */ import org.apache.logging.log4j.core.config.Configuration;
/*     */ import org.apache.logging.log4j.core.config.plugins.Plugin;
/*     */ import org.apache.logging.log4j.core.config.plugins.PluginAttribute;
/*     */ import org.apache.logging.log4j.core.config.plugins.PluginConfiguration;
/*     */ import org.apache.logging.log4j.core.config.plugins.PluginElement;
/*     */ import org.apache.logging.log4j.core.config.plugins.PluginFactory;
/*     */ import org.apache.logging.log4j.core.helpers.Booleans;
/*     */ import org.apache.logging.log4j.core.helpers.Charsets;
/*     */ import org.apache.logging.log4j.core.helpers.Integers;
/*     */ import org.apache.logging.log4j.core.helpers.NetUtils;
/*     */ import org.apache.logging.log4j.core.helpers.Strings;
/*     */ import org.apache.logging.log4j.core.net.Facility;
/*     */ import org.apache.logging.log4j.core.net.Priority;
/*     */ import org.apache.logging.log4j.core.pattern.LogEventPatternConverter;
/*     */ import org.apache.logging.log4j.core.pattern.PatternConverter;
/*     */ import org.apache.logging.log4j.core.pattern.PatternFormatter;
/*     */ import org.apache.logging.log4j.core.pattern.PatternParser;
/*     */ import org.apache.logging.log4j.core.pattern.ThrowablePatternConverter;
/*     */ import org.apache.logging.log4j.message.Message;
/*     */ import org.apache.logging.log4j.message.StructuredDataId;
/*     */ import org.apache.logging.log4j.message.StructuredDataMessage;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ @Plugin(name="RFC5424Layout", category="Core", elementType="layout", printObject=true)
/*     */ public class RFC5424Layout
/*     */   extends AbstractStringLayout
/*     */ {
/*     */   private static final String LF = "\n";
/*     */   public static final int DEFAULT_ENTERPRISE_NUMBER = 18060;
/*     */   public static final String DEFAULT_ID = "Audit";
/*  79 */   public static final Pattern NEWLINE_PATTERN = Pattern.compile("\\r?\\n");
/*     */   
/*     */ 
/*     */ 
/*  83 */   public static final Pattern PARAM_VALUE_ESCAPE_PATTERN = Pattern.compile("[\\\"\\]\\\\]");
/*     */   
/*     */   protected static final String DEFAULT_MDCID = "mdc";
/*     */   
/*     */   private static final int TWO_DIGITS = 10;
/*     */   
/*     */   private static final int THREE_DIGITS = 100;
/*     */   private static final int MILLIS_PER_MINUTE = 60000;
/*     */   private static final int MINUTES_PER_HOUR = 60;
/*     */   private static final String COMPONENT_KEY = "RFC5424-Converter";
/*     */   private final Facility facility;
/*     */   private final String defaultId;
/*     */   private final int enterpriseNumber;
/*     */   private final boolean includeMDC;
/*     */   private final String mdcId;
/*     */   private final StructuredDataId mdcSDID;
/*     */   private final String localHostName;
/*     */   private final String appName;
/*     */   private final String messageId;
/*     */   private final String configName;
/*     */   private final String mdcPrefix;
/*     */   private final String eventPrefix;
/*     */   private final List<String> mdcExcludes;
/*     */   private final List<String> mdcIncludes;
/*     */   private final List<String> mdcRequired;
/*     */   private final ListChecker checker;
/* 109 */   private final ListChecker noopChecker = new NoopChecker(null);
/*     */   
/*     */   private final boolean includeNewLine;
/*     */   private final String escapeNewLine;
/*     */   private final boolean useTLSMessageFormat;
/* 114 */   private long lastTimestamp = -1L;
/*     */   
/*     */ 
/*     */   private String timestamppStr;
/*     */   
/*     */   private final List<PatternFormatter> exceptionFormatters;
/*     */   
/*     */   private final Map<String, FieldFormatter> fieldFormatters;
/*     */   
/*     */ 
/*     */   private RFC5424Layout(Configuration paramConfiguration, Facility paramFacility, String paramString1, int paramInt, boolean paramBoolean1, boolean paramBoolean2, String paramString2, String paramString3, String paramString4, String paramString5, String paramString6, String paramString7, String paramString8, String paramString9, String paramString10, Charset paramCharset, String paramString11, boolean paramBoolean3, LoggerFields[] paramArrayOfLoggerFields)
/*     */   {
/* 126 */     super(paramCharset);
/* 127 */     PatternParser localPatternParser = createPatternParser(paramConfiguration, ThrowablePatternConverter.class);
/* 128 */     this.exceptionFormatters = (paramString11 == null ? null : localPatternParser.parse(paramString11, false));
/* 129 */     this.facility = paramFacility;
/* 130 */     this.defaultId = (paramString1 == null ? "Audit" : paramString1);
/* 131 */     this.enterpriseNumber = paramInt;
/* 132 */     this.includeMDC = paramBoolean1;
/* 133 */     this.includeNewLine = paramBoolean2;
/* 134 */     this.escapeNewLine = (paramString2 == null ? null : Matcher.quoteReplacement(paramString2));
/* 135 */     this.mdcId = paramString3;
/* 136 */     this.mdcSDID = new StructuredDataId(paramString3, this.enterpriseNumber, null, null);
/* 137 */     this.mdcPrefix = paramString4;
/* 138 */     this.eventPrefix = paramString5;
/* 139 */     this.appName = paramString6;
/* 140 */     this.messageId = paramString7;
/* 141 */     this.useTLSMessageFormat = paramBoolean3;
/* 142 */     this.localHostName = NetUtils.getLocalHostname();
/* 143 */     Object localObject1 = null;
/* 144 */     Object localObject4; if (paramString8 != null) {
/* 145 */       localObject2 = paramString8.split(",");
/* 146 */       if (localObject2.length > 0) {
/* 147 */         localObject1 = new ExcludeChecker(null);
/* 148 */         this.mdcExcludes = new ArrayList(localObject2.length);
/* 149 */         for (localObject4 : localObject2) {
/* 150 */           this.mdcExcludes.add(((String)localObject4).trim());
/*     */         }
/*     */       } else {
/* 153 */         this.mdcExcludes = null;
/*     */       }
/*     */     } else {
/* 156 */       this.mdcExcludes = null;
/*     */     }
/* 158 */     if (paramString9 != null) {
/* 159 */       localObject2 = paramString9.split(",");
/* 160 */       if (localObject2.length > 0) {
/* 161 */         localObject1 = new IncludeChecker(null);
/* 162 */         this.mdcIncludes = new ArrayList(localObject2.length);
/* 163 */         for (localObject4 : localObject2) {
/* 164 */           this.mdcIncludes.add(((String)localObject4).trim());
/*     */         }
/*     */       } else {
/* 167 */         this.mdcIncludes = null;
/*     */       }
/*     */     } else {
/* 170 */       this.mdcIncludes = null;
/*     */     }
/* 172 */     if (paramString10 != null) {
/* 173 */       localObject2 = paramString10.split(",");
/* 174 */       if (localObject2.length > 0) {
/* 175 */         this.mdcRequired = new ArrayList(localObject2.length);
/* 176 */         for (localObject4 : localObject2) {
/* 177 */           this.mdcRequired.add(((String)localObject4).trim());
/*     */         }
/*     */       } else {
/* 180 */         this.mdcRequired = null;
/*     */       }
/*     */     }
/*     */     else {
/* 184 */       this.mdcRequired = null;
/*     */     }
/* 186 */     this.checker = (localObject1 != null ? localObject1 : this.noopChecker);
/* 187 */     Object localObject2 = paramConfiguration == null ? null : paramConfiguration.getName();
/* 188 */     this.configName = ((localObject2 != null) && (((String)localObject2).length() > 0) ? localObject2 : null);
/* 189 */     this.fieldFormatters = createFieldFormatters(paramArrayOfLoggerFields, paramConfiguration);
/*     */   }
/*     */   
/*     */   private Map<String, FieldFormatter> createFieldFormatters(LoggerFields[] paramArrayOfLoggerFields, Configuration paramConfiguration)
/*     */   {
/* 194 */     HashMap localHashMap1 = new HashMap();
/*     */     
/* 196 */     if (paramArrayOfLoggerFields != null) {
/* 197 */       for (LoggerFields localLoggerFields : paramArrayOfLoggerFields) {
/* 198 */         StructuredDataId localStructuredDataId = localLoggerFields.getSdId() == null ? this.mdcSDID : localLoggerFields.getSdId();
/* 199 */         HashMap localHashMap2 = new HashMap();
/* 200 */         Map localMap = localLoggerFields.getMap();
/* 201 */         if (!localMap.isEmpty()) {
/* 202 */           PatternParser localPatternParser = createPatternParser(paramConfiguration, null);
/*     */           
/* 204 */           for (Object localObject = localMap.entrySet().iterator(); ((Iterator)localObject).hasNext();) { Map.Entry localEntry = (Map.Entry)((Iterator)localObject).next();
/* 205 */             List localList = localPatternParser.parse((String)localEntry.getValue(), false);
/* 206 */             localHashMap2.put(localEntry.getKey(), localList);
/*     */           }
/* 208 */           localObject = new FieldFormatter(localHashMap2, localLoggerFields.getDiscardIfAllFieldsAreEmpty());
/*     */           
/* 210 */           localHashMap1.put(localStructuredDataId.toString(), localObject);
/*     */         }
/*     */       }
/*     */     }
/* 214 */     return localHashMap1.size() > 0 ? localHashMap1 : null;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   private static PatternParser createPatternParser(Configuration paramConfiguration, Class<? extends PatternConverter> paramClass)
/*     */   {
/* 226 */     if (paramConfiguration == null) {
/* 227 */       return new PatternParser(paramConfiguration, "Converter", LogEventPatternConverter.class, paramClass);
/*     */     }
/* 229 */     PatternParser localPatternParser = (PatternParser)paramConfiguration.getComponent("RFC5424-Converter");
/* 230 */     if (localPatternParser == null) {
/* 231 */       localPatternParser = new PatternParser(paramConfiguration, "Converter", ThrowablePatternConverter.class);
/* 232 */       paramConfiguration.addComponent("RFC5424-Converter", localPatternParser);
/* 233 */       localPatternParser = (PatternParser)paramConfiguration.getComponent("RFC5424-Converter");
/*     */     }
/* 235 */     return localPatternParser;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public Map<String, String> getContentFormat()
/*     */   {
/* 247 */     HashMap localHashMap = new HashMap();
/* 248 */     localHashMap.put("structured", "true");
/* 249 */     localHashMap.put("formatType", "RFC5424");
/* 250 */     return localHashMap;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public String toSerializable(LogEvent paramLogEvent)
/*     */   {
/* 261 */     StringBuilder localStringBuilder = new StringBuilder();
/* 262 */     appendPriority(localStringBuilder, paramLogEvent.getLevel());
/* 263 */     appendTimestamp(localStringBuilder, paramLogEvent.getMillis());
/* 264 */     appendSpace(localStringBuilder);
/* 265 */     appendHostName(localStringBuilder);
/* 266 */     appendSpace(localStringBuilder);
/* 267 */     appendAppName(localStringBuilder);
/* 268 */     appendSpace(localStringBuilder);
/* 269 */     appendProcessId(localStringBuilder);
/* 270 */     appendSpace(localStringBuilder);
/* 271 */     appendMessageId(localStringBuilder, paramLogEvent.getMessage());
/* 272 */     appendSpace(localStringBuilder);
/* 273 */     appendStructuredElements(localStringBuilder, paramLogEvent);
/* 274 */     appendMessage(localStringBuilder, paramLogEvent);
/* 275 */     if (this.useTLSMessageFormat) {
/* 276 */       return new TLSSyslogFrame(localStringBuilder.toString()).toString();
/*     */     }
/* 278 */     return localStringBuilder.toString();
/*     */   }
/*     */   
/*     */   private void appendPriority(StringBuilder paramStringBuilder, Level paramLevel) {
/* 282 */     paramStringBuilder.append("<");
/* 283 */     paramStringBuilder.append(Priority.getPriority(this.facility, paramLevel));
/* 284 */     paramStringBuilder.append(">1 ");
/*     */   }
/*     */   
/*     */   private void appendTimestamp(StringBuilder paramStringBuilder, long paramLong) {
/* 288 */     paramStringBuilder.append(computeTimeStampString(paramLong));
/*     */   }
/*     */   
/*     */   private void appendSpace(StringBuilder paramStringBuilder) {
/* 292 */     paramStringBuilder.append(" ");
/*     */   }
/*     */   
/*     */   private void appendHostName(StringBuilder paramStringBuilder) {
/* 296 */     paramStringBuilder.append(this.localHostName);
/*     */   }
/*     */   
/*     */   private void appendAppName(StringBuilder paramStringBuilder) {
/* 300 */     if (this.appName != null) {
/* 301 */       paramStringBuilder.append(this.appName);
/* 302 */     } else if (this.configName != null) {
/* 303 */       paramStringBuilder.append(this.configName);
/*     */     } else {
/* 305 */       paramStringBuilder.append("-");
/*     */     }
/*     */   }
/*     */   
/*     */   private void appendProcessId(StringBuilder paramStringBuilder) {
/* 310 */     paramStringBuilder.append(getProcId());
/*     */   }
/*     */   
/*     */   private void appendMessageId(StringBuilder paramStringBuilder, Message paramMessage) {
/* 314 */     boolean bool = paramMessage instanceof StructuredDataMessage;
/* 315 */     String str = bool ? ((StructuredDataMessage)paramMessage).getType() : null;
/* 316 */     if (str != null) {
/* 317 */       paramStringBuilder.append(str);
/* 318 */     } else if (this.messageId != null) {
/* 319 */       paramStringBuilder.append(this.messageId);
/*     */     } else {
/* 321 */       paramStringBuilder.append("-");
/*     */     }
/*     */   }
/*     */   
/*     */   private void appendMessage(StringBuilder paramStringBuilder, LogEvent paramLogEvent) {
/* 326 */     Message localMessage = paramLogEvent.getMessage();
/* 327 */     String str = localMessage.getFormat();
/*     */     
/* 329 */     if ((str != null) && (str.length() > 0)) {
/* 330 */       paramStringBuilder.append(" ").append(escapeNewlines(str, this.escapeNewLine));
/*     */     }
/*     */     
/* 333 */     if ((this.exceptionFormatters != null) && (paramLogEvent.getThrown() != null)) {
/* 334 */       StringBuilder localStringBuilder = new StringBuilder("\n");
/* 335 */       for (PatternFormatter localPatternFormatter : this.exceptionFormatters) {
/* 336 */         localPatternFormatter.format(paramLogEvent, localStringBuilder);
/*     */       }
/* 338 */       paramStringBuilder.append(escapeNewlines(localStringBuilder.toString(), this.escapeNewLine));
/*     */     }
/* 340 */     if (this.includeNewLine) {
/* 341 */       paramStringBuilder.append("\n");
/*     */     }
/*     */   }
/*     */   
/*     */   private void appendStructuredElements(StringBuilder paramStringBuilder, LogEvent paramLogEvent) {
/* 346 */     Message localMessage = paramLogEvent.getMessage();
/* 347 */     boolean bool = localMessage instanceof StructuredDataMessage;
/*     */     
/* 349 */     if ((!bool) && (this.fieldFormatters != null) && (this.fieldFormatters.size() == 0) && (!this.includeMDC)) {
/* 350 */       paramStringBuilder.append("-");
/* 351 */       return;
/*     */     }
/*     */     
/* 354 */     HashMap localHashMap = new HashMap();
/* 355 */     Map localMap = paramLogEvent.getContextMap();
/*     */     
/* 357 */     if (this.mdcRequired != null) {
/* 358 */       checkRequired(localMap);
/*     */     }
/*     */     
/* 361 */     if (this.fieldFormatters != null)
/* 362 */       for (localObject1 = this.fieldFormatters.entrySet().iterator(); ((Iterator)localObject1).hasNext();) { localObject2 = (Map.Entry)((Iterator)localObject1).next();
/* 363 */         localObject3 = (String)((Map.Entry)localObject2).getKey();
/* 364 */         localStructuredDataElement = ((FieldFormatter)((Map.Entry)localObject2).getValue()).format(paramLogEvent);
/* 365 */         localHashMap.put(localObject3, localStructuredDataElement); }
/*     */     Object localObject2;
/*     */     Object localObject3;
/*     */     StructuredDataElement localStructuredDataElement;
/* 369 */     if ((this.includeMDC) && (localMap.size() > 0)) {
/* 370 */       if (localHashMap.containsKey(this.mdcSDID.toString())) {
/* 371 */         localObject1 = (StructuredDataElement)localHashMap.get(this.mdcSDID.toString());
/* 372 */         ((StructuredDataElement)localObject1).union(localMap);
/* 373 */         localHashMap.put(this.mdcSDID.toString(), localObject1);
/*     */       } else {
/* 375 */         localObject1 = new StructuredDataElement(localMap, false);
/* 376 */         localHashMap.put(this.mdcSDID.toString(), localObject1);
/*     */       }
/*     */     }
/*     */     
/* 380 */     if (bool) {
/* 381 */       localObject1 = (StructuredDataMessage)localMessage;
/* 382 */       localObject2 = ((StructuredDataMessage)localObject1).getData();
/* 383 */       localObject3 = ((StructuredDataMessage)localObject1).getId();
/*     */       
/* 385 */       if (localHashMap.containsKey(((StructuredDataId)localObject3).toString())) {
/* 386 */         localStructuredDataElement = (StructuredDataElement)localHashMap.get(((StructuredDataId)localObject3).toString());
/* 387 */         localStructuredDataElement.union((Map)localObject2);
/* 388 */         localHashMap.put(((StructuredDataId)localObject3).toString(), localStructuredDataElement);
/*     */       } else {
/* 390 */         localStructuredDataElement = new StructuredDataElement((Map)localObject2, false);
/* 391 */         localHashMap.put(((StructuredDataId)localObject3).toString(), localStructuredDataElement);
/*     */       }
/*     */     }
/*     */     
/* 395 */     if (localHashMap.size() == 0) {
/* 396 */       paramStringBuilder.append("-");
/* 397 */       return;
/*     */     }
/*     */     
/* 400 */     for (Object localObject1 = localHashMap.entrySet().iterator(); ((Iterator)localObject1).hasNext();) { localObject2 = (Map.Entry)((Iterator)localObject1).next();
/* 401 */       formatStructuredElement((String)((Map.Entry)localObject2).getKey(), this.mdcPrefix, (StructuredDataElement)((Map.Entry)localObject2).getValue(), paramStringBuilder, this.checker);
/*     */     }
/*     */   }
/*     */   
/*     */   private String escapeNewlines(String paramString1, String paramString2) {
/* 406 */     if (null == paramString2) {
/* 407 */       return paramString1;
/*     */     }
/* 409 */     return NEWLINE_PATTERN.matcher(paramString1).replaceAll(paramString2);
/*     */   }
/*     */   
/*     */   protected String getProcId() {
/* 413 */     return "-";
/*     */   }
/*     */   
/*     */   protected List<String> getMdcExcludes() {
/* 417 */     return this.mdcExcludes;
/*     */   }
/*     */   
/*     */   protected List<String> getMdcIncludes() {
/* 421 */     return this.mdcIncludes;
/*     */   }
/*     */   
/*     */   private String computeTimeStampString(long paramLong) {
/*     */     long l;
/* 426 */     synchronized (this) {
/* 427 */       l = this.lastTimestamp;
/* 428 */       if (paramLong == this.lastTimestamp) {
/* 429 */         return this.timestamppStr;
/*     */       }
/*     */     }
/*     */     
/* 433 */     ??? = new StringBuilder();
/* 434 */     GregorianCalendar localGregorianCalendar = new GregorianCalendar();
/* 435 */     localGregorianCalendar.setTimeInMillis(paramLong);
/* 436 */     ((StringBuilder)???).append(Integer.toString(localGregorianCalendar.get(1)));
/* 437 */     ((StringBuilder)???).append("-");
/* 438 */     pad(localGregorianCalendar.get(2) + 1, 10, (StringBuilder)???);
/* 439 */     ((StringBuilder)???).append("-");
/* 440 */     pad(localGregorianCalendar.get(5), 10, (StringBuilder)???);
/* 441 */     ((StringBuilder)???).append("T");
/* 442 */     pad(localGregorianCalendar.get(11), 10, (StringBuilder)???);
/* 443 */     ((StringBuilder)???).append(":");
/* 444 */     pad(localGregorianCalendar.get(12), 10, (StringBuilder)???);
/* 445 */     ((StringBuilder)???).append(":");
/* 446 */     pad(localGregorianCalendar.get(13), 10, (StringBuilder)???);
/*     */     
/* 448 */     int i = localGregorianCalendar.get(14);
/* 449 */     if (i != 0) {
/* 450 */       ((StringBuilder)???).append('.');
/* 451 */       pad(i, 100, (StringBuilder)???);
/*     */     }
/*     */     
/* 454 */     int j = (localGregorianCalendar.get(15) + localGregorianCalendar.get(16)) / 60000;
/* 455 */     if (j == 0) {
/* 456 */       ((StringBuilder)???).append("Z");
/*     */     } else {
/* 458 */       if (j < 0) {
/* 459 */         j = -j;
/* 460 */         ((StringBuilder)???).append("-");
/*     */       } else {
/* 462 */         ((StringBuilder)???).append("+");
/*     */       }
/* 464 */       int k = j / 60;
/* 465 */       j -= k * 60;
/* 466 */       pad(k, 10, (StringBuilder)???);
/* 467 */       ((StringBuilder)???).append(":");
/* 468 */       pad(j, 10, (StringBuilder)???);
/*     */     }
/* 470 */     synchronized (this) {
/* 471 */       if (l == this.lastTimestamp) {
/* 472 */         this.lastTimestamp = paramLong;
/* 473 */         this.timestamppStr = ((StringBuilder)???).toString();
/*     */       }
/*     */     }
/* 476 */     return ((StringBuilder)???).toString();
/*     */   }
/*     */   
/*     */   private void pad(int paramInt1, int paramInt2, StringBuilder paramStringBuilder) {
/* 480 */     while (paramInt2 > 1) {
/* 481 */       if (paramInt1 < paramInt2) {
/* 482 */         paramStringBuilder.append("0");
/*     */       }
/* 484 */       paramInt2 /= 10;
/*     */     }
/* 486 */     paramStringBuilder.append(Integer.toString(paramInt1));
/*     */   }
/*     */   
/*     */   private void formatStructuredElement(String paramString1, String paramString2, StructuredDataElement paramStructuredDataElement, StringBuilder paramStringBuilder, ListChecker paramListChecker)
/*     */   {
/* 491 */     if (((paramString1 == null) && (this.defaultId == null)) || (paramStructuredDataElement.discard())) {
/* 492 */       return;
/*     */     }
/*     */     
/* 495 */     paramStringBuilder.append("[");
/* 496 */     paramStringBuilder.append(paramString1);
/* 497 */     if (!this.mdcSDID.toString().equals(paramString1)) {
/* 498 */       appendMap(paramString2, paramStructuredDataElement.getFields(), paramStringBuilder, this.noopChecker);
/*     */     } else {
/* 500 */       appendMap(paramString2, paramStructuredDataElement.getFields(), paramStringBuilder, paramListChecker);
/*     */     }
/* 502 */     paramStringBuilder.append("]");
/*     */   }
/*     */   
/*     */   private String getId(StructuredDataId paramStructuredDataId) {
/* 506 */     StringBuilder localStringBuilder = new StringBuilder();
/* 507 */     if ((paramStructuredDataId == null) || (paramStructuredDataId.getName() == null)) {
/* 508 */       localStringBuilder.append(this.defaultId);
/*     */     } else {
/* 510 */       localStringBuilder.append(paramStructuredDataId.getName());
/*     */     }
/* 512 */     int i = paramStructuredDataId != null ? paramStructuredDataId.getEnterpriseNumber() : this.enterpriseNumber;
/* 513 */     if (i < 0) {
/* 514 */       i = this.enterpriseNumber;
/*     */     }
/* 516 */     if (i >= 0) {
/* 517 */       localStringBuilder.append("@").append(i);
/*     */     }
/* 519 */     return localStringBuilder.toString();
/*     */   }
/*     */   
/*     */   private void checkRequired(Map<String, String> paramMap) {
/* 523 */     for (String str1 : this.mdcRequired) {
/* 524 */       String str2 = (String)paramMap.get(str1);
/* 525 */       if (str2 == null) {
/* 526 */         throw new LoggingException("Required key " + str1 + " is missing from the " + this.mdcId);
/*     */       }
/*     */     }
/*     */   }
/*     */   
/*     */   private void appendMap(String paramString, Map<String, String> paramMap, StringBuilder paramStringBuilder, ListChecker paramListChecker)
/*     */   {
/* 533 */     TreeMap localTreeMap = new TreeMap(paramMap);
/* 534 */     for (Map.Entry localEntry : localTreeMap.entrySet()) {
/* 535 */       if ((paramListChecker.check((String)localEntry.getKey())) && (localEntry.getValue() != null)) {
/* 536 */         paramStringBuilder.append(" ");
/* 537 */         if (paramString != null) {
/* 538 */           paramStringBuilder.append(paramString);
/*     */         }
/* 540 */         paramStringBuilder.append(escapeNewlines(escapeSDParams((String)localEntry.getKey()), this.escapeNewLine)).append("=\"").append(escapeNewlines(escapeSDParams((String)localEntry.getValue()), this.escapeNewLine)).append("\"");
/*     */       }
/*     */     }
/*     */   }
/*     */   
/*     */   private String escapeSDParams(String paramString)
/*     */   {
/* 547 */     return PARAM_VALUE_ESCAPE_PATTERN.matcher(paramString).replaceAll("\\\\$0");
/*     */   }
/*     */   
/*     */ 
/*     */   private static abstract interface ListChecker
/*     */   {
/*     */     public abstract boolean check(String paramString);
/*     */   }
/*     */   
/*     */   private class IncludeChecker
/*     */     implements RFC5424Layout.ListChecker
/*     */   {
/*     */     private IncludeChecker() {}
/*     */     
/*     */     public boolean check(String paramString)
/*     */     {
/* 563 */       return RFC5424Layout.this.mdcIncludes.contains(paramString);
/*     */     }
/*     */   }
/*     */   
/*     */   private class ExcludeChecker implements RFC5424Layout.ListChecker
/*     */   {
/*     */     private ExcludeChecker() {}
/*     */     
/*     */     public boolean check(String paramString)
/*     */     {
/* 573 */       return !RFC5424Layout.this.mdcExcludes.contains(paramString);
/*     */     }
/*     */   }
/*     */   
/*     */   private class NoopChecker implements RFC5424Layout.ListChecker
/*     */   {
/*     */     private NoopChecker() {}
/*     */     
/*     */     public boolean check(String paramString)
/*     */     {
/* 583 */       return true;
/*     */     }
/*     */   }
/*     */   
/*     */   public String toString()
/*     */   {
/* 589 */     StringBuilder localStringBuilder = new StringBuilder();
/* 590 */     localStringBuilder.append("facility=").append(this.facility.name());
/* 591 */     localStringBuilder.append(" appName=").append(this.appName);
/* 592 */     localStringBuilder.append(" defaultId=").append(this.defaultId);
/* 593 */     localStringBuilder.append(" enterpriseNumber=").append(this.enterpriseNumber);
/* 594 */     localStringBuilder.append(" newLine=").append(this.includeNewLine);
/* 595 */     localStringBuilder.append(" includeMDC=").append(this.includeMDC);
/* 596 */     localStringBuilder.append(" messageId=").append(this.messageId);
/* 597 */     return localStringBuilder.toString();
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   @PluginFactory
/*     */   public static RFC5424Layout createLayout(@PluginAttribute("facility") String paramString1, @PluginAttribute("id") String paramString2, @PluginAttribute("enterpriseNumber") String paramString3, @PluginAttribute("includeMDC") String paramString4, @PluginAttribute("mdcId") String paramString5, @PluginAttribute("mdcPrefix") String paramString6, @PluginAttribute("eventPrefix") String paramString7, @PluginAttribute("newLine") String paramString8, @PluginAttribute("newLineEscape") String paramString9, @PluginAttribute("appName") String paramString10, @PluginAttribute("messageId") String paramString11, @PluginAttribute("mdcExcludes") String paramString12, @PluginAttribute("mdcIncludes") String paramString13, @PluginAttribute("mdcRequired") String paramString14, @PluginAttribute("exceptionPattern") String paramString15, @PluginAttribute("useTLSMessageFormat") String paramString16, @PluginElement("LoggerFields") LoggerFields[] paramArrayOfLoggerFields, @PluginConfiguration Configuration paramConfiguration)
/*     */   {
/* 644 */     Charset localCharset = Charsets.UTF_8;
/* 645 */     if ((paramString13 != null) && (paramString12 != null)) {
/* 646 */       LOGGER.error("mdcIncludes and mdcExcludes are mutually exclusive. Includes wil be ignored");
/* 647 */       paramString13 = null;
/*     */     }
/* 649 */     Facility localFacility = Facility.toFacility(paramString1, Facility.LOCAL0);
/* 650 */     int i = Integers.parseInt(paramString3, 18060);
/* 651 */     boolean bool1 = Booleans.parseBoolean(paramString4, true);
/* 652 */     boolean bool2 = Boolean.parseBoolean(paramString8);
/* 653 */     boolean bool3 = Booleans.parseBoolean(paramString16, false);
/* 654 */     if (paramString5 == null) {
/* 655 */       paramString5 = "mdc";
/*     */     }
/*     */     
/* 658 */     return new RFC5424Layout(paramConfiguration, localFacility, paramString2, i, bool1, bool2, paramString9, paramString5, paramString6, paramString7, paramString10, paramString11, paramString12, paramString13, paramString14, localCharset, paramString15, bool3, paramArrayOfLoggerFields);
/*     */   }
/*     */   
/*     */   private class FieldFormatter
/*     */   {
/*     */     private final Map<String, List<PatternFormatter>> delegateMap;
/*     */     private final boolean discardIfEmpty;
/*     */     
/*     */     public FieldFormatter(boolean paramBoolean)
/*     */     {
/*     */       boolean bool;
/* 669 */       this.discardIfEmpty = bool;
/* 670 */       this.delegateMap = paramBoolean;
/*     */     }
/*     */     
/*     */     public RFC5424Layout.StructuredDataElement format(LogEvent paramLogEvent) {
/* 674 */       HashMap localHashMap = new HashMap();
/*     */       
/* 676 */       for (Map.Entry localEntry : this.delegateMap.entrySet()) {
/* 677 */         StringBuilder localStringBuilder = new StringBuilder();
/* 678 */         for (PatternFormatter localPatternFormatter : (List)localEntry.getValue()) {
/* 679 */           localPatternFormatter.format(paramLogEvent, localStringBuilder);
/*     */         }
/* 681 */         localHashMap.put(localEntry.getKey(), localStringBuilder.toString());
/*     */       }
/* 683 */       return new RFC5424Layout.StructuredDataElement(RFC5424Layout.this, localHashMap, this.discardIfEmpty);
/*     */     }
/*     */   }
/*     */   
/*     */   private class StructuredDataElement {
/*     */     private final Map<String, String> fields;
/*     */     private final boolean discardIfEmpty;
/*     */     
/*     */     public StructuredDataElement(boolean paramBoolean) {
/*     */       boolean bool;
/* 693 */       this.discardIfEmpty = bool;
/* 694 */       this.fields = paramBoolean;
/*     */     }
/*     */     
/*     */     boolean discard() {
/* 698 */       if (!this.discardIfEmpty) {
/* 699 */         return false;
/*     */       }
/* 701 */       int i = 0;
/* 702 */       for (Map.Entry localEntry : this.fields.entrySet()) {
/* 703 */         if (Strings.isNotEmpty((CharSequence)localEntry.getValue())) {
/* 704 */           i = 1;
/* 705 */           break;
/*     */         }
/*     */       }
/* 708 */       return i == 0;
/*     */     }
/*     */     
/*     */     void union(Map<String, String> paramMap) {
/* 712 */       this.fields.putAll(paramMap);
/*     */     }
/*     */     
/*     */     Map<String, String> getFields() {
/* 716 */       return this.fields;
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\org\apache\logging\log4j\core\layout\RFC5424Layout.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */
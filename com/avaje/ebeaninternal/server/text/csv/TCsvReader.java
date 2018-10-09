/*     */ package com.avaje.ebeaninternal.server.text.csv;
/*     */ 
/*     */ import com.avaje.ebean.EbeanServer;
/*     */ import com.avaje.ebean.bean.EntityBean;
/*     */ import com.avaje.ebean.text.StringParser;
/*     */ import com.avaje.ebean.text.TextException;
/*     */ import com.avaje.ebean.text.TimeStringParser;
/*     */ import com.avaje.ebean.text.csv.CsvCallback;
/*     */ import com.avaje.ebean.text.csv.CsvReader;
/*     */ import com.avaje.ebean.text.csv.DefaultCsvCallback;
/*     */ import com.avaje.ebeaninternal.server.deploy.BeanDescriptor;
/*     */ import com.avaje.ebeaninternal.server.deploy.BeanPropertyAssocOne;
/*     */ import com.avaje.ebeaninternal.server.deploy.id.IdBinder;
/*     */ import com.avaje.ebeaninternal.server.el.ElPropertyValue;
/*     */ import java.io.Reader;
/*     */ import java.text.DateFormat;
/*     */ import java.text.ParseException;
/*     */ import java.text.SimpleDateFormat;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Arrays;
/*     */ import java.util.Date;
/*     */ import java.util.List;
/*     */ import java.util.Locale;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class TCsvReader<T>
/*     */   implements CsvReader<T>
/*     */ {
/*  54 */   private static final TimeStringParser TIME_PARSER = new TimeStringParser();
/*     */   
/*     */   private final EbeanServer server;
/*     */   
/*     */   private final BeanDescriptor<T> descriptor;
/*     */   
/*  60 */   private final List<CsvColumn> columnList = new ArrayList();
/*     */   
/*  62 */   private final CsvColumn ignoreColumn = new CsvColumn(null);
/*     */   
/*  64 */   private boolean treatEmptyStringAsNull = true;
/*     */   
/*     */   private boolean hasHeader;
/*     */   
/*  68 */   private int logInfoFrequency = 1000;
/*     */   
/*  70 */   private String defaultTimeFormat = "HH:mm:ss";
/*  71 */   private String defaultDateFormat = "yyyy-MM-dd";
/*  72 */   private String defaultTimestampFormat = "yyyy-MM-dd hh:mm:ss.fffffffff";
/*  73 */   private Locale defaultLocale = Locale.getDefault();
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*  78 */   protected int persistBatchSize = 30;
/*     */   
/*     */ 
/*     */   private boolean addPropertiesFromHeader;
/*     */   
/*     */ 
/*     */   public TCsvReader(EbeanServer server, BeanDescriptor<T> descriptor)
/*     */   {
/*  86 */     this.server = server;
/*  87 */     this.descriptor = descriptor;
/*     */   }
/*     */   
/*     */   public void setDefaultLocale(Locale defaultLocale) {
/*  91 */     this.defaultLocale = defaultLocale;
/*     */   }
/*     */   
/*     */   public void setDefaultTimeFormat(String defaultTimeFormat) {
/*  95 */     this.defaultTimeFormat = defaultTimeFormat;
/*     */   }
/*     */   
/*     */   public void setDefaultDateFormat(String defaultDateFormat) {
/*  99 */     this.defaultDateFormat = defaultDateFormat;
/*     */   }
/*     */   
/*     */   public void setDefaultTimestampFormat(String defaultTimestampFormat) {
/* 103 */     this.defaultTimestampFormat = defaultTimestampFormat;
/*     */   }
/*     */   
/*     */   public void setPersistBatchSize(int persistBatchSize) {
/* 107 */     this.persistBatchSize = persistBatchSize;
/*     */   }
/*     */   
/*     */   public void setIgnoreHeader() {
/* 111 */     setHasHeader(true, false);
/*     */   }
/*     */   
/*     */   public void setAddPropertiesFromHeader() {
/* 115 */     setHasHeader(true, true);
/*     */   }
/*     */   
/*     */   public void setHasHeader(boolean hasHeader, boolean addPropertiesFromHeader) {
/* 119 */     this.hasHeader = hasHeader;
/* 120 */     this.addPropertiesFromHeader = addPropertiesFromHeader;
/*     */   }
/*     */   
/*     */   public void setLogInfoFrequency(int logInfoFrequency) {
/* 124 */     this.logInfoFrequency = logInfoFrequency;
/*     */   }
/*     */   
/*     */   public void addIgnore() {
/* 128 */     this.columnList.add(this.ignoreColumn);
/*     */   }
/*     */   
/*     */   public void addProperty(String propertyName) {
/* 132 */     addProperty(propertyName, null);
/*     */   }
/*     */   
/*     */   public void addReference(String propertyName) {
/* 136 */     addProperty(propertyName, null, true);
/*     */   }
/*     */   
/*     */   public void addProperty(String propertyName, StringParser parser) {
/* 140 */     addProperty(propertyName, parser, false);
/*     */   }
/*     */   
/*     */   public void addDateTime(String propertyName, String dateTimeFormat) {
/* 144 */     addDateTime(propertyName, dateTimeFormat, Locale.getDefault());
/*     */   }
/*     */   
/*     */   public void addDateTime(String propertyName, String dateTimeFormat, Locale locale)
/*     */   {
/* 149 */     ElPropertyValue elProp = this.descriptor.getElGetValue(propertyName);
/* 150 */     if (!elProp.isDateTimeCapable()) {
/* 151 */       throw new TextException("Property " + propertyName + " is not DateTime capable");
/*     */     }
/*     */     
/* 154 */     if (dateTimeFormat == null) {
/* 155 */       dateTimeFormat = getDefaultDateTimeFormat(elProp.getJdbcType());
/*     */     }
/*     */     
/* 158 */     if (locale == null) {
/* 159 */       locale = this.defaultLocale;
/*     */     }
/*     */     
/* 162 */     SimpleDateFormat sdf = new SimpleDateFormat(dateTimeFormat, locale);
/* 163 */     DateTimeParser parser = new DateTimeParser(sdf, dateTimeFormat, elProp);
/*     */     
/* 165 */     CsvColumn column = new CsvColumn(elProp, parser, false);
/* 166 */     this.columnList.add(column);
/*     */   }
/*     */   
/*     */   private String getDefaultDateTimeFormat(int jdbcType) {
/* 170 */     switch (jdbcType) {
/*     */     case 92: 
/* 172 */       return this.defaultTimeFormat;
/*     */     case 91: 
/* 174 */       return this.defaultDateFormat;
/*     */     case 93: 
/* 176 */       return this.defaultTimestampFormat;
/*     */     }
/*     */     
/* 179 */     throw new RuntimeException("Expected java.sql.Types TIME,DATE or TIMESTAMP but got [" + jdbcType + "]");
/*     */   }
/*     */   
/*     */ 
/*     */   public void addProperty(String propertyName, StringParser parser, boolean reference)
/*     */   {
/* 185 */     ElPropertyValue elProp = this.descriptor.getElGetValue(propertyName);
/* 186 */     if (parser == null) {
/* 187 */       parser = elProp.getStringParser();
/*     */     }
/* 189 */     CsvColumn column = new CsvColumn(elProp, parser, reference);
/* 190 */     this.columnList.add(column);
/*     */   }
/*     */   
/*     */   public void process(Reader reader) throws Exception {
/* 194 */     DefaultCsvCallback<T> callback = new DefaultCsvCallback(this.persistBatchSize, this.logInfoFrequency);
/* 195 */     process(reader, callback);
/*     */   }
/*     */   
/*     */   public void process(Reader reader, CsvCallback<T> callback) throws Exception
/*     */   {
/* 200 */     if (reader == null) {
/* 201 */       throw new NullPointerException("reader is null?");
/*     */     }
/* 203 */     if (callback == null) {
/* 204 */       throw new NullPointerException("callback is null?");
/*     */     }
/*     */     
/* 207 */     CsvUtilReader utilReader = new CsvUtilReader(reader);
/*     */     
/* 209 */     callback.begin(this.server);
/*     */     
/* 211 */     int row = 0;
/*     */     
/* 213 */     if (this.hasHeader) {
/* 214 */       String[] line = utilReader.readNext();
/* 215 */       if (this.addPropertiesFromHeader) {
/* 216 */         addPropertiesFromHeader(line);
/*     */       }
/* 218 */       callback.readHeader(line);
/*     */     }
/*     */     try
/*     */     {
/*     */       for (;;) {
/* 223 */         row++;
/* 224 */         String[] line = utilReader.readNext();
/* 225 */         if (line == null) {
/* 226 */           row--;
/* 227 */           break;
/*     */         }
/*     */         
/* 230 */         if (callback.processLine(row, line))
/*     */         {
/* 232 */           if (line.length != this.columnList.size())
/*     */           {
/* 234 */             String msg = "Error at line " + row + ". Expected [" + this.columnList.size() + "] columns " + "but instead we have [" + line.length + "].  Line[" + Arrays.toString(line) + "]";
/*     */             
/* 236 */             throw new TextException(msg);
/*     */           }
/*     */           
/* 239 */           T bean = buildBeanFromLineContent(row, line);
/*     */           
/* 241 */           callback.processBean(row, line, bean);
/*     */         }
/*     */       }
/*     */       
/*     */ 
/* 246 */       callback.end(row);
/*     */ 
/*     */     }
/*     */     catch (Exception e)
/*     */     {
/* 251 */       callback.endWithError(row, e);
/* 252 */       throw e;
/*     */     }
/*     */   }
/*     */   
/*     */   private void addPropertiesFromHeader(String[] line) {
/* 257 */     for (int i = 0; i < line.length; i++) {
/* 258 */       ElPropertyValue elProp = this.descriptor.getElGetValue(line[i]);
/* 259 */       if (elProp == null) {
/* 260 */         throw new TextException("Property [" + line[i] + "] not found");
/*     */       }
/*     */       
/* 263 */       if (92 == elProp.getJdbcType()) {
/* 264 */         addProperty(line[i], TIME_PARSER);
/*     */       }
/* 266 */       else if (isDateTimeType(elProp.getJdbcType())) {
/* 267 */         addDateTime(line[i], null, null);
/*     */       }
/* 269 */       else if (elProp.isAssocProperty()) {
/* 270 */         BeanPropertyAssocOne<?> assocOne = (BeanPropertyAssocOne)elProp.getBeanProperty();
/* 271 */         String idProp = assocOne.getBeanDescriptor().getIdBinder().getIdProperty();
/* 272 */         addReference(line[i] + "." + idProp);
/*     */       } else {
/* 274 */         addProperty(line[i]);
/*     */       }
/*     */     }
/*     */   }
/*     */   
/*     */   private boolean isDateTimeType(int t) {
/* 280 */     if ((t == 93) || (t == 91) || (t == 92)) {
/* 281 */       return true;
/*     */     }
/* 283 */     return false;
/*     */   }
/*     */   
/*     */   protected T buildBeanFromLineContent(int row, String[] line)
/*     */   {
/*     */     try
/*     */     {
/* 290 */       EntityBean entityBean = this.descriptor.createEntityBean();
/* 291 */       T bean = entityBean;
/*     */       
/* 293 */       for (int columnPos = 0; 
/* 294 */           columnPos < line.length; columnPos++) {
/* 295 */         convertAndSetColumn(columnPos, line[columnPos], entityBean);
/*     */       }
/*     */       
/* 298 */       return bean;
/*     */     }
/*     */     catch (RuntimeException e) {
/* 301 */       String msg = "Error at line: " + row + " line[" + Arrays.toString(line) + "]";
/* 302 */       throw new RuntimeException(msg, e);
/*     */     }
/*     */   }
/*     */   
/*     */   protected void convertAndSetColumn(int columnPos, String strValue, Object bean)
/*     */   {
/* 308 */     strValue = strValue.trim();
/*     */     
/* 310 */     if ((strValue.length() == 0) && (this.treatEmptyStringAsNull)) {
/* 311 */       return;
/*     */     }
/*     */     
/* 314 */     CsvColumn c = (CsvColumn)this.columnList.get(columnPos);
/* 315 */     c.convertAndSet(strValue, bean);
/*     */   }
/*     */   
/*     */ 
/*     */   public static class CsvColumn
/*     */   {
/*     */     private final ElPropertyValue elProp;
/*     */     
/*     */     private final StringParser parser;
/*     */     
/*     */     private final boolean ignore;
/*     */     
/*     */     private final boolean reference;
/*     */     
/*     */ 
/*     */     private CsvColumn()
/*     */     {
/* 332 */       this.elProp = null;
/* 333 */       this.parser = null;
/* 334 */       this.reference = false;
/* 335 */       this.ignore = true;
/*     */     }
/*     */     
/*     */ 
/*     */ 
/*     */     public CsvColumn(ElPropertyValue elProp, StringParser parser, boolean reference)
/*     */     {
/* 342 */       this.elProp = elProp;
/* 343 */       this.parser = parser;
/* 344 */       this.reference = reference;
/* 345 */       this.ignore = false;
/*     */     }
/*     */     
/*     */ 
/*     */ 
/*     */ 
/*     */     public void convertAndSet(String strValue, Object bean)
/*     */     {
/* 353 */       if (!this.ignore) {
/* 354 */         Object value = this.parser.parse(strValue);
/* 355 */         this.elProp.elSetValue(bean, value, true, this.reference);
/*     */       }
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */   private static class DateTimeParser
/*     */     implements StringParser
/*     */   {
/*     */     private final DateFormat dateFormat;
/*     */     
/*     */     private final ElPropertyValue elProp;
/*     */     
/*     */     private final String format;
/*     */     
/*     */     DateTimeParser(DateFormat dateFormat, String format, ElPropertyValue elProp)
/*     */     {
/* 372 */       this.dateFormat = dateFormat;
/* 373 */       this.elProp = elProp;
/* 374 */       this.format = format;
/*     */     }
/*     */     
/*     */     public Object parse(String value) {
/*     */       try {
/* 379 */         Date dt = this.dateFormat.parse(value);
/* 380 */         return this.elProp.parseDateTime(dt.getTime());
/*     */       }
/*     */       catch (ParseException e) {
/* 383 */         throw new TextException("Error parsing [" + value + "] using format[" + this.format + "]", e);
/*     */       }
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\com\avaje\ebeaninternal\server\text\csv\TCsvReader.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */
/*     */ package org.apache.logging.log4j.simple;
/*     */ 
/*     */ import java.io.FileNotFoundException;
/*     */ import java.io.FileOutputStream;
/*     */ import java.io.PrintStream;
/*     */ import java.util.Properties;
/*     */ import java.util.concurrent.ConcurrentHashMap;
/*     */ import java.util.concurrent.ConcurrentMap;
/*     */ import org.apache.logging.log4j.Level;
/*     */ import org.apache.logging.log4j.Logger;
/*     */ import org.apache.logging.log4j.message.MessageFactory;
/*     */ import org.apache.logging.log4j.spi.AbstractLogger;
/*     */ import org.apache.logging.log4j.spi.LoggerContext;
/*     */ import org.apache.logging.log4j.util.PropertiesUtil;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class SimpleLoggerContext
/*     */   implements LoggerContext
/*     */ {
/*     */   protected static final String DEFAULT_DATE_TIME_FORMAT = "yyyy/MM/dd HH:mm:ss:SSS zzz";
/*     */   protected static final String SYSTEM_PREFIX = "org.apache.logging.log4j.simplelog.";
/*  45 */   private final Properties simpleLogProps = new Properties();
/*     */   
/*     */ 
/*     */   private final PropertiesUtil props;
/*     */   
/*     */ 
/*     */   private final boolean showLogName;
/*     */   
/*     */ 
/*     */   private final boolean showShortName;
/*     */   
/*     */ 
/*     */   private final boolean showDateTime;
/*     */   
/*     */   private final boolean showContextMap;
/*     */   
/*     */   private final String dateTimeFormat;
/*     */   
/*     */   private final Level defaultLevel;
/*     */   
/*     */   private final PrintStream stream;
/*     */   
/*  67 */   private final ConcurrentMap<String, Logger> loggers = new ConcurrentHashMap();
/*     */   
/*     */   public SimpleLoggerContext() {
/*  70 */     this.props = new PropertiesUtil("log4j2.simplelog.properties");
/*     */     
/*  72 */     this.showContextMap = this.props.getBooleanProperty("org.apache.logging.log4j.simplelog.showContextMap", false);
/*  73 */     this.showLogName = this.props.getBooleanProperty("org.apache.logging.log4j.simplelog.showlogname", false);
/*  74 */     this.showShortName = this.props.getBooleanProperty("org.apache.logging.log4j.simplelog.showShortLogname", true);
/*  75 */     this.showDateTime = this.props.getBooleanProperty("org.apache.logging.log4j.simplelog.showdatetime", false);
/*  76 */     String str1 = this.props.getStringProperty("org.apache.logging.log4j.simplelog.level");
/*  77 */     this.defaultLevel = Level.toLevel(str1, Level.ERROR);
/*     */     
/*  79 */     this.dateTimeFormat = (this.showDateTime ? this.props.getStringProperty("org.apache.logging.log4j.simplelog.dateTimeFormat", "yyyy/MM/dd HH:mm:ss:SSS zzz") : null);
/*     */     
/*     */ 
/*  82 */     String str2 = this.props.getStringProperty("org.apache.logging.log4j.simplelog.logFile", "system.err");
/*     */     PrintStream localPrintStream;
/*  84 */     if ("system.err".equalsIgnoreCase(str2)) {
/*  85 */       localPrintStream = System.err;
/*  86 */     } else if ("system.out".equalsIgnoreCase(str2)) {
/*  87 */       localPrintStream = System.out;
/*     */     } else {
/*     */       try {
/*  90 */         FileOutputStream localFileOutputStream = new FileOutputStream(str2);
/*  91 */         localPrintStream = new PrintStream(localFileOutputStream);
/*     */       } catch (FileNotFoundException localFileNotFoundException) {
/*  93 */         localPrintStream = System.err;
/*     */       }
/*     */     }
/*  96 */     this.stream = localPrintStream;
/*     */   }
/*     */   
/*     */   public Logger getLogger(String paramString)
/*     */   {
/* 101 */     return getLogger(paramString, null);
/*     */   }
/*     */   
/*     */   public Logger getLogger(String paramString, MessageFactory paramMessageFactory)
/*     */   {
/* 106 */     if (this.loggers.containsKey(paramString)) {
/* 107 */       Logger localLogger = (Logger)this.loggers.get(paramString);
/* 108 */       AbstractLogger.checkMessageFactory(localLogger, paramMessageFactory);
/* 109 */       return localLogger;
/*     */     }
/*     */     
/* 112 */     this.loggers.putIfAbsent(paramString, new SimpleLogger(paramString, this.defaultLevel, this.showLogName, this.showShortName, this.showDateTime, this.showContextMap, this.dateTimeFormat, paramMessageFactory, this.props, this.stream));
/*     */     
/* 114 */     return (Logger)this.loggers.get(paramString);
/*     */   }
/*     */   
/*     */   public boolean hasLogger(String paramString)
/*     */   {
/* 119 */     return false;
/*     */   }
/*     */   
/*     */   public Object getExternalContext()
/*     */   {
/* 124 */     return null;
/*     */   }
/*     */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\org\apache\logging\log4j\simple\SimpleLoggerContext.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */
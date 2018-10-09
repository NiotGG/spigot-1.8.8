/*     */ package org.apache.logging.log4j.core;
/*     */ 
/*     */ import java.util.ArrayList;
/*     */ import java.util.Iterator;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import org.apache.logging.log4j.Level;
/*     */ import org.apache.logging.log4j.Marker;
/*     */ import org.apache.logging.log4j.core.config.Configuration;
/*     */ import org.apache.logging.log4j.core.config.ConfigurationMonitor;
/*     */ import org.apache.logging.log4j.core.config.LoggerConfig;
/*     */ import org.apache.logging.log4j.core.filter.CompositeFilter;
/*     */ import org.apache.logging.log4j.message.Message;
/*     */ import org.apache.logging.log4j.message.MessageFactory;
/*     */ import org.apache.logging.log4j.message.SimpleMessage;
/*     */ import org.apache.logging.log4j.spi.AbstractLogger;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class Logger
/*     */   extends AbstractLogger
/*     */ {
/*     */   protected volatile PrivateConfig config;
/*     */   private final LoggerContext context;
/*     */   
/*     */   protected Logger(LoggerContext paramLoggerContext, String paramString, MessageFactory paramMessageFactory)
/*     */   {
/*  56 */     super(paramString, paramMessageFactory);
/*  57 */     this.context = paramLoggerContext;
/*  58 */     this.config = new PrivateConfig(paramLoggerContext.getConfiguration(), this);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public Logger getParent()
/*     */   {
/*  67 */     LoggerConfig localLoggerConfig = this.config.loggerConfig.getName().equals(getName()) ? this.config.loggerConfig.getParent() : this.config.loggerConfig;
/*     */     
/*  69 */     if (localLoggerConfig == null) {
/*  70 */       return null;
/*     */     }
/*  72 */     if (this.context.hasLogger(localLoggerConfig.getName())) {
/*  73 */       return this.context.getLogger(localLoggerConfig.getName(), getMessageFactory());
/*     */     }
/*  75 */     return new Logger(this.context, localLoggerConfig.getName(), getMessageFactory());
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public LoggerContext getContext()
/*     */   {
/*  83 */     return this.context;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public synchronized void setLevel(Level paramLevel)
/*     */   {
/*  91 */     if (paramLevel != null) {
/*  92 */       this.config = new PrivateConfig(this.config, paramLevel);
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public Level getLevel()
/*     */   {
/* 101 */     return this.config.level;
/*     */   }
/*     */   
/*     */   public void log(Marker paramMarker, String paramString, Level paramLevel, Message paramMessage, Throwable paramThrowable)
/*     */   {
/* 106 */     if (paramMessage == null) {
/* 107 */       paramMessage = new SimpleMessage("");
/*     */     }
/* 109 */     this.config.config.getConfigurationMonitor().checkConfiguration();
/* 110 */     this.config.loggerConfig.log(getName(), paramMarker, paramString, paramLevel, paramMessage, paramThrowable);
/*     */   }
/*     */   
/*     */   public boolean isEnabled(Level paramLevel, Marker paramMarker, String paramString)
/*     */   {
/* 115 */     return this.config.filter(paramLevel, paramMarker, paramString);
/*     */   }
/*     */   
/*     */   public boolean isEnabled(Level paramLevel, Marker paramMarker, String paramString, Throwable paramThrowable)
/*     */   {
/* 120 */     return this.config.filter(paramLevel, paramMarker, paramString, paramThrowable);
/*     */   }
/*     */   
/*     */   public boolean isEnabled(Level paramLevel, Marker paramMarker, String paramString, Object... paramVarArgs)
/*     */   {
/* 125 */     return this.config.filter(paramLevel, paramMarker, paramString, paramVarArgs);
/*     */   }
/*     */   
/*     */   public boolean isEnabled(Level paramLevel, Marker paramMarker, Object paramObject, Throwable paramThrowable)
/*     */   {
/* 130 */     return this.config.filter(paramLevel, paramMarker, paramObject, paramThrowable);
/*     */   }
/*     */   
/*     */   public boolean isEnabled(Level paramLevel, Marker paramMarker, Message paramMessage, Throwable paramThrowable)
/*     */   {
/* 135 */     return this.config.filter(paramLevel, paramMarker, paramMessage, paramThrowable);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public void addAppender(Appender paramAppender)
/*     */   {
/* 143 */     this.config.config.addLoggerAppender(this, paramAppender);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public void removeAppender(Appender paramAppender)
/*     */   {
/* 151 */     this.config.loggerConfig.removeAppender(paramAppender.getName());
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public Map<String, Appender> getAppenders()
/*     */   {
/* 159 */     return this.config.loggerConfig.getAppenders();
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public Iterator<Filter> getFilters()
/*     */   {
/* 167 */     Filter localFilter = this.config.loggerConfig.getFilter();
/* 168 */     if (localFilter == null)
/* 169 */       return new ArrayList().iterator();
/* 170 */     if ((localFilter instanceof CompositeFilter)) {
/* 171 */       return ((CompositeFilter)localFilter).iterator();
/*     */     }
/* 173 */     ArrayList localArrayList = new ArrayList();
/* 174 */     localArrayList.add(localFilter);
/* 175 */     return localArrayList.iterator();
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public int filterCount()
/*     */   {
/* 184 */     Filter localFilter = this.config.loggerConfig.getFilter();
/* 185 */     if (localFilter == null)
/* 186 */       return 0;
/* 187 */     if ((localFilter instanceof CompositeFilter)) {
/* 188 */       return ((CompositeFilter)localFilter).size();
/*     */     }
/* 190 */     return 1;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public void addFilter(Filter paramFilter)
/*     */   {
/* 198 */     this.config.config.addLoggerFilter(this, paramFilter);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public boolean isAdditive()
/*     */   {
/* 207 */     return this.config.loggerConfig.isAdditive();
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public void setAdditive(boolean paramBoolean)
/*     */   {
/* 216 */     this.config.config.setLoggerAdditive(this, paramBoolean);
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
/*     */   void updateConfiguration(Configuration paramConfiguration)
/*     */   {
/* 232 */     this.config = new PrivateConfig(paramConfiguration, this);
/*     */   }
/*     */   
/*     */ 
/*     */   protected class PrivateConfig
/*     */   {
/*     */     public final LoggerConfig loggerConfig;
/*     */     
/*     */     public final Configuration config;
/*     */     private final Level level;
/*     */     private final int intLevel;
/*     */     private final Logger logger;
/*     */     
/*     */     public PrivateConfig(Configuration paramConfiguration, Logger paramLogger)
/*     */     {
/* 247 */       this.config = paramConfiguration;
/* 248 */       this.loggerConfig = paramConfiguration.getLoggerConfig(Logger.this.getName());
/* 249 */       this.level = this.loggerConfig.getLevel();
/* 250 */       this.intLevel = this.level.intLevel();
/* 251 */       this.logger = paramLogger;
/*     */     }
/*     */     
/*     */     public PrivateConfig(PrivateConfig paramPrivateConfig, Level paramLevel) {
/* 255 */       this.config = paramPrivateConfig.config;
/* 256 */       this.loggerConfig = paramPrivateConfig.loggerConfig;
/* 257 */       this.level = paramLevel;
/* 258 */       this.intLevel = this.level.intLevel();
/* 259 */       this.logger = paramPrivateConfig.logger;
/*     */     }
/*     */     
/*     */     public PrivateConfig(PrivateConfig paramPrivateConfig, LoggerConfig paramLoggerConfig) {
/* 263 */       this.config = paramPrivateConfig.config;
/* 264 */       this.loggerConfig = paramLoggerConfig;
/* 265 */       this.level = paramLoggerConfig.getLevel();
/* 266 */       this.intLevel = this.level.intLevel();
/* 267 */       this.logger = paramPrivateConfig.logger;
/*     */     }
/*     */     
/*     */     public void logEvent(LogEvent paramLogEvent)
/*     */     {
/* 272 */       this.config.getConfigurationMonitor().checkConfiguration();
/* 273 */       this.loggerConfig.log(paramLogEvent);
/*     */     }
/*     */     
/*     */     boolean filter(Level paramLevel, Marker paramMarker, String paramString) {
/* 277 */       this.config.getConfigurationMonitor().checkConfiguration();
/* 278 */       Filter localFilter = this.config.getFilter();
/* 279 */       if (localFilter != null) {
/* 280 */         Filter.Result localResult = localFilter.filter(this.logger, paramLevel, paramMarker, paramString, new Object[0]);
/* 281 */         if (localResult != Filter.Result.NEUTRAL) {
/* 282 */           return localResult == Filter.Result.ACCEPT;
/*     */         }
/*     */       }
/*     */       
/* 286 */       return this.intLevel >= paramLevel.intLevel();
/*     */     }
/*     */     
/*     */     boolean filter(Level paramLevel, Marker paramMarker, String paramString, Throwable paramThrowable) {
/* 290 */       this.config.getConfigurationMonitor().checkConfiguration();
/* 291 */       Filter localFilter = this.config.getFilter();
/* 292 */       if (localFilter != null) {
/* 293 */         Filter.Result localResult = localFilter.filter(this.logger, paramLevel, paramMarker, paramString, paramThrowable);
/* 294 */         if (localResult != Filter.Result.NEUTRAL) {
/* 295 */           return localResult == Filter.Result.ACCEPT;
/*     */         }
/*     */       }
/*     */       
/* 299 */       return this.intLevel >= paramLevel.intLevel();
/*     */     }
/*     */     
/*     */     boolean filter(Level paramLevel, Marker paramMarker, String paramString, Object... paramVarArgs) {
/* 303 */       this.config.getConfigurationMonitor().checkConfiguration();
/* 304 */       Filter localFilter = this.config.getFilter();
/* 305 */       if (localFilter != null) {
/* 306 */         Filter.Result localResult = localFilter.filter(this.logger, paramLevel, paramMarker, paramString, paramVarArgs);
/* 307 */         if (localResult != Filter.Result.NEUTRAL) {
/* 308 */           return localResult == Filter.Result.ACCEPT;
/*     */         }
/*     */       }
/*     */       
/* 312 */       return this.intLevel >= paramLevel.intLevel();
/*     */     }
/*     */     
/*     */     boolean filter(Level paramLevel, Marker paramMarker, Object paramObject, Throwable paramThrowable) {
/* 316 */       this.config.getConfigurationMonitor().checkConfiguration();
/* 317 */       Filter localFilter = this.config.getFilter();
/* 318 */       if (localFilter != null) {
/* 319 */         Filter.Result localResult = localFilter.filter(this.logger, paramLevel, paramMarker, paramObject, paramThrowable);
/* 320 */         if (localResult != Filter.Result.NEUTRAL) {
/* 321 */           return localResult == Filter.Result.ACCEPT;
/*     */         }
/*     */       }
/*     */       
/* 325 */       return this.intLevel >= paramLevel.intLevel();
/*     */     }
/*     */     
/*     */     boolean filter(Level paramLevel, Marker paramMarker, Message paramMessage, Throwable paramThrowable) {
/* 329 */       this.config.getConfigurationMonitor().checkConfiguration();
/* 330 */       Filter localFilter = this.config.getFilter();
/* 331 */       if (localFilter != null) {
/* 332 */         Filter.Result localResult = localFilter.filter(this.logger, paramLevel, paramMarker, paramMessage, paramThrowable);
/* 333 */         if (localResult != Filter.Result.NEUTRAL) {
/* 334 */           return localResult == Filter.Result.ACCEPT;
/*     */         }
/*     */       }
/*     */       
/* 338 */       return this.intLevel >= paramLevel.intLevel();
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public String toString()
/*     */   {
/* 348 */     String str1 = "" + getName() + ":" + getLevel();
/* 349 */     if (this.context == null) {
/* 350 */       return str1;
/*     */     }
/* 352 */     String str2 = this.context.getName();
/* 353 */     return str1 + " in " + str2;
/*     */   }
/*     */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\org\apache\logging\log4j\core\Logger.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */
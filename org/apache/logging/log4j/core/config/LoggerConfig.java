/*     */ package org.apache.logging.log4j.core.config;
/*     */ 
/*     */ import java.util.ArrayList;
/*     */ import java.util.Arrays;
/*     */ import java.util.Collection;
/*     */ import java.util.Collections;
/*     */ import java.util.HashMap;
/*     */ import java.util.Iterator;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import java.util.Map.Entry;
/*     */ import java.util.Set;
/*     */ import java.util.concurrent.ConcurrentHashMap;
/*     */ import java.util.concurrent.atomic.AtomicInteger;
/*     */ import org.apache.logging.log4j.Level;
/*     */ import org.apache.logging.log4j.Logger;
/*     */ import org.apache.logging.log4j.Marker;
/*     */ import org.apache.logging.log4j.core.Appender;
/*     */ import org.apache.logging.log4j.core.Filter;
/*     */ import org.apache.logging.log4j.core.LifeCycle;
/*     */ import org.apache.logging.log4j.core.LogEvent;
/*     */ import org.apache.logging.log4j.core.async.AsyncLoggerContextSelector;
/*     */ import org.apache.logging.log4j.core.config.plugins.Plugin;
/*     */ import org.apache.logging.log4j.core.config.plugins.PluginAttribute;
/*     */ import org.apache.logging.log4j.core.config.plugins.PluginConfiguration;
/*     */ import org.apache.logging.log4j.core.config.plugins.PluginElement;
/*     */ import org.apache.logging.log4j.core.config.plugins.PluginFactory;
/*     */ import org.apache.logging.log4j.core.filter.AbstractFilterable;
/*     */ import org.apache.logging.log4j.core.helpers.Booleans;
/*     */ import org.apache.logging.log4j.core.helpers.Loader;
/*     */ import org.apache.logging.log4j.core.helpers.Strings;
/*     */ import org.apache.logging.log4j.core.impl.DefaultLogEventFactory;
/*     */ import org.apache.logging.log4j.core.impl.LogEventFactory;
/*     */ import org.apache.logging.log4j.core.lookup.StrSubstitutor;
/*     */ import org.apache.logging.log4j.message.Message;
/*     */ import org.apache.logging.log4j.status.StatusLogger;
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
/*     */ @Plugin(name="logger", category="Core", printObject=true)
/*     */ public class LoggerConfig
/*     */   extends AbstractFilterable
/*     */ {
/*  62 */   protected static final Logger LOGGER = ;
/*     */   private static final int MAX_RETRIES = 3;
/*     */   private static final long WAIT_TIME = 1000L;
/*  65 */   private static LogEventFactory LOG_EVENT_FACTORY = null;
/*     */   
/*  67 */   private List<AppenderRef> appenderRefs = new ArrayList();
/*  68 */   private final Map<String, AppenderControl> appenders = new ConcurrentHashMap();
/*     */   private final String name;
/*     */   private LogEventFactory logEventFactory;
/*     */   private Level level;
/*  72 */   private boolean additive = true;
/*  73 */   private boolean includeLocation = true;
/*     */   private LoggerConfig parent;
/*  75 */   private final AtomicInteger counter = new AtomicInteger();
/*  76 */   private boolean shutdown = false;
/*     */   private final Map<Property, Boolean> properties;
/*     */   private final Configuration config;
/*     */   
/*     */   static {
/*  81 */     String str = PropertiesUtil.getProperties().getStringProperty("Log4jLogEventFactory");
/*  82 */     if (str != null) {
/*     */       try {
/*  84 */         Class localClass = Loader.loadClass(str);
/*  85 */         if ((localClass != null) && (LogEventFactory.class.isAssignableFrom(localClass))) {
/*  86 */           LOG_EVENT_FACTORY = (LogEventFactory)localClass.newInstance();
/*     */         }
/*     */       } catch (Exception localException) {
/*  89 */         LOGGER.error("Unable to create LogEventFactory " + str, localException);
/*     */       }
/*     */     }
/*  92 */     if (LOG_EVENT_FACTORY == null) {
/*  93 */       LOG_EVENT_FACTORY = new DefaultLogEventFactory();
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public LoggerConfig()
/*     */   {
/* 101 */     this.logEventFactory = LOG_EVENT_FACTORY;
/* 102 */     this.level = Level.ERROR;
/* 103 */     this.name = "";
/* 104 */     this.properties = null;
/* 105 */     this.config = null;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public LoggerConfig(String paramString, Level paramLevel, boolean paramBoolean)
/*     */   {
/* 117 */     this.logEventFactory = LOG_EVENT_FACTORY;
/* 118 */     this.name = paramString;
/* 119 */     this.level = paramLevel;
/* 120 */     this.additive = paramBoolean;
/* 121 */     this.properties = null;
/* 122 */     this.config = null;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   protected LoggerConfig(String paramString, List<AppenderRef> paramList, Filter paramFilter, Level paramLevel, boolean paramBoolean1, Property[] paramArrayOfProperty, Configuration paramConfiguration, boolean paramBoolean2)
/*     */   {
/* 130 */     super(paramFilter);
/* 131 */     this.logEventFactory = LOG_EVENT_FACTORY;
/* 132 */     this.name = paramString;
/* 133 */     this.appenderRefs = paramList;
/* 134 */     this.level = paramLevel;
/* 135 */     this.additive = paramBoolean1;
/* 136 */     this.includeLocation = paramBoolean2;
/* 137 */     this.config = paramConfiguration;
/* 138 */     if ((paramArrayOfProperty != null) && (paramArrayOfProperty.length > 0)) {
/* 139 */       this.properties = new HashMap(paramArrayOfProperty.length);
/* 140 */       for (Property localProperty : paramArrayOfProperty) {
/* 141 */         boolean bool = localProperty.getValue().contains("${");
/* 142 */         this.properties.put(localProperty, Boolean.valueOf(bool));
/*     */       }
/*     */     } else {
/* 145 */       this.properties = null;
/*     */     }
/*     */   }
/*     */   
/*     */   public Filter getFilter()
/*     */   {
/* 151 */     return super.getFilter();
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public String getName()
/*     */   {
/* 160 */     return this.name;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public void setParent(LoggerConfig paramLoggerConfig)
/*     */   {
/* 169 */     this.parent = paramLoggerConfig;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public LoggerConfig getParent()
/*     */   {
/* 178 */     return this.parent;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public void addAppender(Appender paramAppender, Level paramLevel, Filter paramFilter)
/*     */   {
/* 190 */     this.appenders.put(paramAppender.getName(), new AppenderControl(paramAppender, paramLevel, paramFilter));
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public void removeAppender(String paramString)
/*     */   {
/* 200 */     AppenderControl localAppenderControl = (AppenderControl)this.appenders.remove(paramString);
/* 201 */     if (localAppenderControl != null) {
/* 202 */       cleanupFilter(localAppenderControl);
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public Map<String, Appender> getAppenders()
/*     */   {
/* 213 */     HashMap localHashMap = new HashMap();
/* 214 */     for (Map.Entry localEntry : this.appenders.entrySet())
/*     */     {
/* 216 */       localHashMap.put(localEntry.getKey(), ((AppenderControl)localEntry.getValue()).getAppender());
/*     */     }
/* 218 */     return localHashMap;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   protected void clearAppenders()
/*     */   {
/* 225 */     waitForCompletion();
/* 226 */     Collection localCollection = this.appenders.values();
/* 227 */     Iterator localIterator = localCollection.iterator();
/* 228 */     while (localIterator.hasNext()) {
/* 229 */       AppenderControl localAppenderControl = (AppenderControl)localIterator.next();
/* 230 */       localIterator.remove();
/* 231 */       cleanupFilter(localAppenderControl);
/*     */     }
/*     */   }
/*     */   
/*     */   private void cleanupFilter(AppenderControl paramAppenderControl) {
/* 236 */     Filter localFilter = paramAppenderControl.getFilter();
/* 237 */     if (localFilter != null) {
/* 238 */       paramAppenderControl.removeFilter(localFilter);
/* 239 */       if ((localFilter instanceof LifeCycle)) {
/* 240 */         ((LifeCycle)localFilter).stop();
/*     */       }
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public List<AppenderRef> getAppenderRefs()
/*     */   {
/* 251 */     return this.appenderRefs;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public void setLevel(Level paramLevel)
/*     */   {
/* 260 */     this.level = paramLevel;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public Level getLevel()
/*     */   {
/* 269 */     return this.level;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public LogEventFactory getLogEventFactory()
/*     */   {
/* 278 */     return this.logEventFactory;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public void setLogEventFactory(LogEventFactory paramLogEventFactory)
/*     */   {
/* 288 */     this.logEventFactory = paramLogEventFactory;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public boolean isAdditive()
/*     */   {
/* 297 */     return this.additive;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public void setAdditive(boolean paramBoolean)
/*     */   {
/* 307 */     this.additive = paramBoolean;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public boolean isIncludeLocation()
/*     */   {
/* 318 */     return this.includeLocation;
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
/*     */   public Map<Property, Boolean> getProperties()
/*     */   {
/* 336 */     return this.properties == null ? null : Collections.unmodifiableMap(this.properties);
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
/*     */   public void log(String paramString1, Marker paramMarker, String paramString2, Level paramLevel, Message paramMessage, Throwable paramThrowable)
/*     */   {
/* 353 */     ArrayList localArrayList = null;
/* 354 */     if (this.properties != null) {
/* 355 */       localArrayList = new ArrayList(this.properties.size());
/*     */       
/* 357 */       for (localObject = this.properties.entrySet().iterator(); ((Iterator)localObject).hasNext();) { Map.Entry localEntry = (Map.Entry)((Iterator)localObject).next();
/*     */         
/* 359 */         Property localProperty = (Property)localEntry.getKey();
/* 360 */         String str = ((Boolean)localEntry.getValue()).booleanValue() ? this.config.getStrSubstitutor().replace(localProperty.getValue()) : localProperty.getValue();
/*     */         
/* 362 */         localArrayList.add(Property.createProperty(localProperty.getName(), str));
/*     */       }
/*     */     }
/* 365 */     Object localObject = this.logEventFactory.createEvent(paramString1, paramMarker, paramString2, paramLevel, paramMessage, localArrayList, paramThrowable);
/*     */     
/* 367 */     log((LogEvent)localObject);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   private synchronized void waitForCompletion()
/*     */   {
/* 375 */     if (this.shutdown) {
/* 376 */       return;
/*     */     }
/* 378 */     this.shutdown = true;
/* 379 */     int i = 0;
/* 380 */     for (;;) { if (this.counter.get() > 0) {
/*     */         try {
/* 382 */           wait(1000L * (i + 1));
/*     */         } catch (InterruptedException localInterruptedException) {
/* 384 */           i++; if (i > 3) {
/*     */             return;
/*     */           }
/*     */         }
/*     */       }
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public void log(LogEvent paramLogEvent)
/*     */   {
/* 398 */     this.counter.incrementAndGet();
/*     */     try {
/* 400 */       if (isFiltered(paramLogEvent)) {
/*     */         return;
/*     */       }
/*     */       
/* 404 */       paramLogEvent.setIncludeLocation(isIncludeLocation());
/*     */       
/* 406 */       callAppenders(paramLogEvent);
/*     */       
/* 408 */       if ((this.additive) && (this.parent != null)) {
/* 409 */         this.parent.log(paramLogEvent);
/*     */       }
/*     */     } finally {
/* 412 */       if (this.counter.decrementAndGet() == 0) {
/* 413 */         synchronized (this) {
/* 414 */           if (this.shutdown) {
/* 415 */             notifyAll();
/*     */           }
/*     */         }
/*     */       }
/*     */     }
/*     */   }
/*     */   
/*     */   protected void callAppenders(LogEvent paramLogEvent)
/*     */   {
/* 424 */     for (AppenderControl localAppenderControl : this.appenders.values()) {
/* 425 */       localAppenderControl.callAppender(paramLogEvent);
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */   public String toString()
/*     */   {
/* 432 */     return Strings.isEmpty(this.name) ? "root" : this.name;
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
/*     */   @PluginFactory
/*     */   public static LoggerConfig createLogger(@PluginAttribute("additivity") String paramString1, @PluginAttribute("level") String paramString2, @PluginAttribute("name") String paramString3, @PluginAttribute("includeLocation") String paramString4, @PluginElement("AppenderRef") AppenderRef[] paramArrayOfAppenderRef, @PluginElement("Properties") Property[] paramArrayOfProperty, @PluginConfiguration Configuration paramConfiguration, @PluginElement("Filters") Filter paramFilter)
/*     */   {
/* 458 */     if (paramString3 == null) {
/* 459 */       LOGGER.error("Loggers cannot be configured without a name");
/* 460 */       return null;
/*     */     }
/*     */     
/* 463 */     List localList = Arrays.asList(paramArrayOfAppenderRef);
/*     */     Level localLevel;
/*     */     try {
/* 466 */       localLevel = Level.toLevel(paramString2, Level.ERROR);
/*     */     } catch (Exception localException) {
/* 468 */       LOGGER.error("Invalid Log level specified: {}. Defaulting to Error", new Object[] { paramString2 });
/*     */       
/*     */ 
/* 471 */       localLevel = Level.ERROR;
/*     */     }
/* 473 */     String str = paramString3.equals("root") ? "" : paramString3;
/* 474 */     boolean bool = Booleans.parseBoolean(paramString1, true);
/*     */     
/* 476 */     return new LoggerConfig(str, localList, paramFilter, localLevel, bool, paramArrayOfProperty, paramConfiguration, includeLocation(paramString4));
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   protected static boolean includeLocation(String paramString)
/*     */   {
/* 483 */     if (paramString == null) {
/* 484 */       boolean bool = !AsyncLoggerContextSelector.class.getName().equals(System.getProperty("Log4jContextSelector"));
/*     */       
/* 486 */       return bool;
/*     */     }
/* 488 */     return Boolean.parseBoolean(paramString);
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
/*     */   @Plugin(name="root", category="Core", printObject=true)
/*     */   public static class RootLogger
/*     */     extends LoggerConfig
/*     */   {
/*     */     @PluginFactory
/*     */     public static LoggerConfig createLogger(@PluginAttribute("additivity") String paramString1, @PluginAttribute("level") String paramString2, @PluginAttribute("includeLocation") String paramString3, @PluginElement("AppenderRef") AppenderRef[] paramArrayOfAppenderRef, @PluginElement("Properties") Property[] paramArrayOfProperty, @PluginConfiguration Configuration paramConfiguration, @PluginElement("Filters") Filter paramFilter)
/*     */     {
/* 506 */       List localList = Arrays.asList(paramArrayOfAppenderRef);
/*     */       Level localLevel;
/*     */       try {
/* 509 */         localLevel = Level.toLevel(paramString2, Level.ERROR);
/*     */       } catch (Exception localException) {
/* 511 */         LOGGER.error("Invalid Log level specified: {}. Defaulting to Error", new Object[] { paramString2 });
/*     */         
/*     */ 
/* 514 */         localLevel = Level.ERROR;
/*     */       }
/* 516 */       boolean bool = Booleans.parseBoolean(paramString1, true);
/*     */       
/* 518 */       return new LoggerConfig("", localList, paramFilter, localLevel, bool, paramArrayOfProperty, paramConfiguration, includeLocation(paramString3));
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\org\apache\logging\log4j\core\config\LoggerConfig.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */
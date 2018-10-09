/*     */ package org.apache.logging.log4j.core.config;
/*     */ 
/*     */ import java.io.PrintStream;
/*     */ import java.lang.reflect.Array;
/*     */ import java.lang.reflect.Method;
/*     */ import java.lang.reflect.Modifier;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Collection;
/*     */ import java.util.Collections;
/*     */ import java.util.Iterator;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import java.util.Map.Entry;
/*     */ import java.util.Set;
/*     */ import java.util.concurrent.ConcurrentHashMap;
/*     */ import java.util.concurrent.ConcurrentMap;
/*     */ import java.util.concurrent.CopyOnWriteArrayList;
/*     */ import org.apache.logging.log4j.Level;
/*     */ import org.apache.logging.log4j.core.Appender;
/*     */ import org.apache.logging.log4j.core.Filter;
/*     */ import org.apache.logging.log4j.core.LogEvent;
/*     */ import org.apache.logging.log4j.core.LoggerContext;
/*     */ import org.apache.logging.log4j.core.appender.ConsoleAppender;
/*     */ import org.apache.logging.log4j.core.config.plugins.PluginAliases;
/*     */ import org.apache.logging.log4j.core.config.plugins.PluginAttribute;
/*     */ import org.apache.logging.log4j.core.config.plugins.PluginConfiguration;
/*     */ import org.apache.logging.log4j.core.config.plugins.PluginElement;
/*     */ import org.apache.logging.log4j.core.config.plugins.PluginFactory;
/*     */ import org.apache.logging.log4j.core.config.plugins.PluginManager;
/*     */ import org.apache.logging.log4j.core.config.plugins.PluginNode;
/*     */ import org.apache.logging.log4j.core.config.plugins.PluginType;
/*     */ import org.apache.logging.log4j.core.config.plugins.PluginValue;
/*     */ import org.apache.logging.log4j.core.filter.AbstractFilterable;
/*     */ import org.apache.logging.log4j.core.helpers.NameUtil;
/*     */ import org.apache.logging.log4j.core.layout.PatternLayout;
/*     */ import org.apache.logging.log4j.core.lookup.Interpolator;
/*     */ import org.apache.logging.log4j.core.lookup.MapLookup;
/*     */ import org.apache.logging.log4j.core.lookup.StrLookup;
/*     */ import org.apache.logging.log4j.core.lookup.StrSubstitutor;
/*     */ import org.apache.logging.log4j.core.net.Advertiser;
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
/*     */ 
/*     */ public class BaseConfiguration
/*     */   extends AbstractFilterable
/*     */   implements Configuration
/*     */ {
/*  68 */   protected static final org.apache.logging.log4j.Logger LOGGER = ;
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   protected Node rootNode;
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*  78 */   protected final List<ConfigurationListener> listeners = new CopyOnWriteArrayList();
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*  84 */   protected ConfigurationMonitor monitor = new DefaultConfigurationMonitor();
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*  89 */   private Advertiser advertiser = new DefaultAdvertiser();
/*     */   
/*     */   protected Map<String, String> advertisedConfiguration;
/*     */   
/*  93 */   private Node advertiserNode = null;
/*     */   
/*     */ 
/*     */ 
/*     */   private Object advertisement;
/*     */   
/*     */ 
/* 100 */   protected boolean isShutdownHookEnabled = true;
/*     */   
/*     */   private String name;
/*     */   
/* 104 */   private ConcurrentMap<String, Appender> appenders = new ConcurrentHashMap();
/*     */   
/* 106 */   private ConcurrentMap<String, LoggerConfig> loggers = new ConcurrentHashMap();
/*     */   
/* 108 */   private final StrLookup tempLookup = new Interpolator();
/*     */   
/* 110 */   private final StrSubstitutor subst = new StrSubstitutor(this.tempLookup);
/*     */   
/* 112 */   private LoggerConfig root = new LoggerConfig();
/*     */   
/* 114 */   private final boolean started = false;
/*     */   
/* 116 */   private final ConcurrentMap<String, Object> componentMap = new ConcurrentHashMap();
/*     */   
/*     */ 
/*     */   protected PluginManager pluginManager;
/*     */   
/*     */ 
/*     */   protected BaseConfiguration()
/*     */   {
/* 124 */     this.pluginManager = new PluginManager("Core");
/* 125 */     this.rootNode = new Node();
/*     */   }
/*     */   
/*     */ 
/*     */   public Map<String, String> getProperties()
/*     */   {
/* 131 */     return (Map)this.componentMap.get("ContextProperties");
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public void start()
/*     */   {
/* 139 */     this.pluginManager.collectPlugins();
/* 140 */     setup();
/* 141 */     setupAdvertisement();
/* 142 */     doConfigure();
/* 143 */     for (Iterator localIterator = this.loggers.values().iterator(); localIterator.hasNext();) { localObject = (LoggerConfig)localIterator.next();
/* 144 */       ((LoggerConfig)localObject).startFilter(); }
/*     */     Object localObject;
/* 146 */     for (localIterator = this.appenders.values().iterator(); localIterator.hasNext();) { localObject = (Appender)localIterator.next();
/* 147 */       ((Appender)localObject).start();
/*     */     }
/* 149 */     this.root.startFilter();
/* 150 */     startFilter();
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public void stop()
/*     */   {
/* 159 */     Appender[] arrayOfAppender = (Appender[])this.appenders.values().toArray(new Appender[this.appenders.size()]);
/* 160 */     for (int i = arrayOfAppender.length - 1; i >= 0; i--) {
/* 161 */       arrayOfAppender[i].stop();
/*     */     }
/* 163 */     for (LoggerConfig localLoggerConfig : this.loggers.values()) {
/* 164 */       localLoggerConfig.clearAppenders();
/* 165 */       localLoggerConfig.stopFilter();
/*     */     }
/* 167 */     this.root.stopFilter();
/* 168 */     stopFilter();
/* 169 */     if ((this.advertiser != null) && (this.advertisement != null))
/*     */     {
/* 171 */       this.advertiser.unadvertise(this.advertisement);
/*     */     }
/*     */   }
/*     */   
/*     */   public boolean isShutdownHookEnabled()
/*     */   {
/* 177 */     return this.isShutdownHookEnabled;
/*     */   }
/*     */   
/*     */   protected void setup() {}
/*     */   
/*     */   protected Level getDefaultStatus()
/*     */   {
/* 184 */     String str = PropertiesUtil.getProperties().getStringProperty("Log4jDefaultStatusLevel", Level.ERROR.name());
/*     */     try
/*     */     {
/* 187 */       return Level.toLevel(str);
/*     */     } catch (Exception localException) {}
/* 189 */     return Level.ERROR;
/*     */   }
/*     */   
/*     */ 
/*     */   protected void createAdvertiser(String paramString1, ConfigurationFactory.ConfigurationSource paramConfigurationSource, byte[] paramArrayOfByte, String paramString2)
/*     */   {
/* 195 */     if (paramString1 != null) {
/* 196 */       Node localNode = new Node(null, paramString1, null);
/* 197 */       Map localMap = localNode.getAttributes();
/* 198 */       localMap.put("content", new String(paramArrayOfByte));
/* 199 */       localMap.put("contentType", paramString2);
/* 200 */       localMap.put("name", "configuration");
/* 201 */       if (paramConfigurationSource.getLocation() != null) {
/* 202 */         localMap.put("location", paramConfigurationSource.getLocation());
/*     */       }
/* 204 */       this.advertiserNode = localNode;
/*     */     }
/*     */   }
/*     */   
/*     */   private void setupAdvertisement() {
/* 209 */     if (this.advertiserNode != null)
/*     */     {
/* 211 */       String str = this.advertiserNode.getName();
/*     */       
/* 213 */       PluginType localPluginType = this.pluginManager.getPluginType(str);
/* 214 */       if (localPluginType != null)
/*     */       {
/* 216 */         Class localClass = localPluginType.getPluginClass();
/*     */         try {
/* 218 */           this.advertiser = ((Advertiser)localClass.newInstance());
/* 219 */           this.advertisement = this.advertiser.advertise(this.advertiserNode.getAttributes());
/*     */         } catch (InstantiationException localInstantiationException) {
/* 221 */           System.err.println("InstantiationException attempting to instantiate advertiser: " + str);
/*     */         } catch (IllegalAccessException localIllegalAccessException) {
/* 223 */           System.err.println("IllegalAccessException attempting to instantiate advertiser: " + str);
/*     */         }
/*     */       }
/*     */     }
/*     */   }
/*     */   
/*     */   public Object getComponent(String paramString)
/*     */   {
/* 231 */     return this.componentMap.get(paramString);
/*     */   }
/*     */   
/*     */   public void addComponent(String paramString, Object paramObject)
/*     */   {
/* 236 */     this.componentMap.putIfAbsent(paramString, paramObject);
/*     */   }
/*     */   
/*     */   protected void doConfigure()
/*     */   {
/* 241 */     int i = 0;
/* 242 */     int j = 0;
/* 243 */     for (Iterator localIterator = this.rootNode.getChildren().iterator(); localIterator.hasNext();) { localObject1 = (Node)localIterator.next();
/* 244 */       createConfiguration((Node)localObject1, null);
/* 245 */       if (((Node)localObject1).getObject() != null)
/*     */       {
/*     */ 
/* 248 */         if (((Node)localObject1).getName().equalsIgnoreCase("Properties")) {
/* 249 */           if (this.tempLookup == this.subst.getVariableResolver()) {
/* 250 */             this.subst.setVariableResolver((StrLookup)((Node)localObject1).getObject());
/*     */           } else {
/* 252 */             LOGGER.error("Properties declaration must be the first element in the configuration");
/*     */           }
/*     */         } else {
/* 255 */           if (this.tempLookup == this.subst.getVariableResolver()) {
/* 256 */             localObject2 = (Map)this.componentMap.get("ContextProperties");
/* 257 */             localObject3 = localObject2 == null ? null : new MapLookup((Map)localObject2);
/* 258 */             this.subst.setVariableResolver(new Interpolator((StrLookup)localObject3));
/*     */           }
/* 260 */           if (((Node)localObject1).getName().equalsIgnoreCase("Appenders")) {
/* 261 */             this.appenders = ((ConcurrentMap)((Node)localObject1).getObject());
/* 262 */           } else if ((((Node)localObject1).getObject() instanceof Filter)) {
/* 263 */             addFilter((Filter)((Node)localObject1).getObject());
/* 264 */           } else if (((Node)localObject1).getName().equalsIgnoreCase("Loggers")) {
/* 265 */             localObject2 = (Loggers)((Node)localObject1).getObject();
/* 266 */             this.loggers = ((Loggers)localObject2).getMap();
/* 267 */             j = 1;
/* 268 */             if (((Loggers)localObject2).getRoot() != null) {
/* 269 */               this.root = ((Loggers)localObject2).getRoot();
/* 270 */               i = 1;
/*     */             }
/*     */           } else {
/* 273 */             LOGGER.error("Unknown object \"" + ((Node)localObject1).getName() + "\" of type " + ((Node)localObject1).getObject().getClass().getName() + " is ignored");
/*     */           } } } }
/*     */     Object localObject1;
/*     */     Object localObject2;
/*     */     Object localObject3;
/* 278 */     if (j == 0) {
/* 279 */       LOGGER.warn("No Loggers were configured, using default. Is the Loggers element missing?");
/* 280 */       setToDefault();
/* 281 */       return; }
/* 282 */     if (i == 0) {
/* 283 */       LOGGER.warn("No Root logger was configured, creating default ERROR-level Root logger with Console appender");
/* 284 */       setToDefault();
/*     */     }
/*     */     
/*     */ 
/* 288 */     for (localIterator = this.loggers.entrySet().iterator(); localIterator.hasNext();) { localObject1 = (Map.Entry)localIterator.next();
/* 289 */       localObject2 = (LoggerConfig)((Map.Entry)localObject1).getValue();
/* 290 */       for (localObject3 = ((LoggerConfig)localObject2).getAppenderRefs().iterator(); ((Iterator)localObject3).hasNext();) { AppenderRef localAppenderRef = (AppenderRef)((Iterator)localObject3).next();
/* 291 */         Appender localAppender = (Appender)this.appenders.get(localAppenderRef.getRef());
/* 292 */         if (localAppender != null) {
/* 293 */           ((LoggerConfig)localObject2).addAppender(localAppender, localAppenderRef.getLevel(), localAppenderRef.getFilter());
/*     */         } else {
/* 295 */           LOGGER.error("Unable to locate appender " + localAppenderRef.getRef() + " for logger " + ((LoggerConfig)localObject2).getName());
/*     */         }
/*     */       }
/*     */     }
/*     */     
/*     */ 
/* 301 */     setParents();
/*     */   }
/*     */   
/*     */   private void setToDefault() {
/* 305 */     setName("Default");
/* 306 */     PatternLayout localPatternLayout = PatternLayout.createLayout("%d{HH:mm:ss.SSS} [%thread] %-5level %logger{36} - %msg%n", null, null, null, null);
/*     */     
/*     */ 
/* 309 */     ConsoleAppender localConsoleAppender = ConsoleAppender.createAppender(localPatternLayout, null, "SYSTEM_OUT", "Console", "false", "true");
/*     */     
/* 311 */     localConsoleAppender.start();
/* 312 */     addAppender(localConsoleAppender);
/* 313 */     LoggerConfig localLoggerConfig = getRootLogger();
/* 314 */     localLoggerConfig.addAppender(localConsoleAppender, null, null);
/*     */     
/* 316 */     String str = PropertiesUtil.getProperties().getStringProperty("org.apache.logging.log4j.level");
/* 317 */     Level localLevel = (str != null) && (Level.valueOf(str) != null) ? Level.valueOf(str) : Level.ERROR;
/*     */     
/* 319 */     localLoggerConfig.setLevel(localLevel);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public void setName(String paramString)
/*     */   {
/* 327 */     this.name = paramString;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public String getName()
/*     */   {
/* 336 */     return this.name;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public void addListener(ConfigurationListener paramConfigurationListener)
/*     */   {
/* 345 */     this.listeners.add(paramConfigurationListener);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public void removeListener(ConfigurationListener paramConfigurationListener)
/*     */   {
/* 354 */     this.listeners.remove(paramConfigurationListener);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public Appender getAppender(String paramString)
/*     */   {
/* 363 */     return (Appender)this.appenders.get(paramString);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public Map<String, Appender> getAppenders()
/*     */   {
/* 372 */     return this.appenders;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public void addAppender(Appender paramAppender)
/*     */   {
/* 380 */     this.appenders.put(paramAppender.getName(), paramAppender);
/*     */   }
/*     */   
/*     */   public StrSubstitutor getStrSubstitutor()
/*     */   {
/* 385 */     return this.subst;
/*     */   }
/*     */   
/*     */   public void setConfigurationMonitor(ConfigurationMonitor paramConfigurationMonitor)
/*     */   {
/* 390 */     this.monitor = paramConfigurationMonitor;
/*     */   }
/*     */   
/*     */   public ConfigurationMonitor getConfigurationMonitor()
/*     */   {
/* 395 */     return this.monitor;
/*     */   }
/*     */   
/*     */   public void setAdvertiser(Advertiser paramAdvertiser)
/*     */   {
/* 400 */     this.advertiser = paramAdvertiser;
/*     */   }
/*     */   
/*     */   public Advertiser getAdvertiser()
/*     */   {
/* 405 */     return this.advertiser;
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
/*     */   public synchronized void addLoggerAppender(org.apache.logging.log4j.core.Logger paramLogger, Appender paramAppender)
/*     */   {
/* 420 */     String str = paramLogger.getName();
/* 421 */     this.appenders.putIfAbsent(paramAppender.getName(), paramAppender);
/* 422 */     LoggerConfig localLoggerConfig1 = getLoggerConfig(str);
/* 423 */     if (localLoggerConfig1.getName().equals(str)) {
/* 424 */       localLoggerConfig1.addAppender(paramAppender, null, null);
/*     */     } else {
/* 426 */       LoggerConfig localLoggerConfig2 = new LoggerConfig(str, localLoggerConfig1.getLevel(), localLoggerConfig1.isAdditive());
/* 427 */       localLoggerConfig2.addAppender(paramAppender, null, null);
/* 428 */       localLoggerConfig2.setParent(localLoggerConfig1);
/* 429 */       this.loggers.putIfAbsent(str, localLoggerConfig2);
/* 430 */       setParents();
/* 431 */       paramLogger.getContext().updateLoggers();
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
/*     */   public synchronized void addLoggerFilter(org.apache.logging.log4j.core.Logger paramLogger, Filter paramFilter)
/*     */   {
/* 445 */     String str = paramLogger.getName();
/* 446 */     LoggerConfig localLoggerConfig1 = getLoggerConfig(str);
/* 447 */     if (localLoggerConfig1.getName().equals(str))
/*     */     {
/* 449 */       localLoggerConfig1.addFilter(paramFilter);
/*     */     } else {
/* 451 */       LoggerConfig localLoggerConfig2 = new LoggerConfig(str, localLoggerConfig1.getLevel(), localLoggerConfig1.isAdditive());
/* 452 */       localLoggerConfig2.addFilter(paramFilter);
/* 453 */       localLoggerConfig2.setParent(localLoggerConfig1);
/* 454 */       this.loggers.putIfAbsent(str, localLoggerConfig2);
/* 455 */       setParents();
/* 456 */       paramLogger.getContext().updateLoggers();
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
/*     */   public synchronized void setLoggerAdditive(org.apache.logging.log4j.core.Logger paramLogger, boolean paramBoolean)
/*     */   {
/* 471 */     String str = paramLogger.getName();
/* 472 */     LoggerConfig localLoggerConfig1 = getLoggerConfig(str);
/* 473 */     if (localLoggerConfig1.getName().equals(str)) {
/* 474 */       localLoggerConfig1.setAdditive(paramBoolean);
/*     */     } else {
/* 476 */       LoggerConfig localLoggerConfig2 = new LoggerConfig(str, localLoggerConfig1.getLevel(), paramBoolean);
/* 477 */       localLoggerConfig2.setParent(localLoggerConfig1);
/* 478 */       this.loggers.putIfAbsent(str, localLoggerConfig2);
/* 479 */       setParents();
/* 480 */       paramLogger.getContext().updateLoggers();
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public synchronized void removeAppender(String paramString)
/*     */   {
/* 491 */     for (Object localObject = this.loggers.values().iterator(); ((Iterator)localObject).hasNext();) { LoggerConfig localLoggerConfig = (LoggerConfig)((Iterator)localObject).next();
/* 492 */       localLoggerConfig.removeAppender(paramString);
/*     */     }
/* 494 */     localObject = (Appender)this.appenders.remove(paramString);
/*     */     
/* 496 */     if (localObject != null) {
/* 497 */       ((Appender)localObject).stop();
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public LoggerConfig getLoggerConfig(String paramString)
/*     */   {
/* 509 */     if (this.loggers.containsKey(paramString)) {
/* 510 */       return (LoggerConfig)this.loggers.get(paramString);
/*     */     }
/* 512 */     String str = paramString;
/* 513 */     while ((str = NameUtil.getSubName(str)) != null) {
/* 514 */       if (this.loggers.containsKey(str)) {
/* 515 */         return (LoggerConfig)this.loggers.get(str);
/*     */       }
/*     */     }
/* 518 */     return this.root;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public LoggerConfig getRootLogger()
/*     */   {
/* 526 */     return this.root;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public Map<String, LoggerConfig> getLoggers()
/*     */   {
/* 535 */     return Collections.unmodifiableMap(this.loggers);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public LoggerConfig getLogger(String paramString)
/*     */   {
/* 544 */     return (LoggerConfig)this.loggers.get(paramString);
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
/*     */   public void addLogger(String paramString, LoggerConfig paramLoggerConfig)
/*     */   {
/* 560 */     this.loggers.put(paramString, paramLoggerConfig);
/* 561 */     setParents();
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
/*     */   public void removeLogger(String paramString)
/*     */   {
/* 576 */     this.loggers.remove(paramString);
/* 577 */     setParents();
/*     */   }
/*     */   
/*     */   public void createConfiguration(Node paramNode, LogEvent paramLogEvent)
/*     */   {
/* 582 */     PluginType localPluginType = paramNode.getType();
/* 583 */     if ((localPluginType != null) && (localPluginType.isDeferChildren())) {
/* 584 */       paramNode.setObject(createPluginObject(localPluginType, paramNode, paramLogEvent));
/*     */     } else {
/* 586 */       for (Node localNode : paramNode.getChildren()) {
/* 587 */         createConfiguration(localNode, paramLogEvent);
/*     */       }
/*     */       
/* 590 */       if (localPluginType == null) {
/* 591 */         if (paramNode.getParent() != null) {
/* 592 */           LOGGER.error("Unable to locate plugin for " + paramNode.getName());
/*     */         }
/*     */       } else {
/* 595 */         paramNode.setObject(createPluginObject(localPluginType, paramNode, paramLogEvent));
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
/*     */   private <T> Object createPluginObject(PluginType<T> paramPluginType, Node paramNode, LogEvent paramLogEvent)
/*     */   {
/* 617 */     Class localClass = paramPluginType.getPluginClass();
/*     */     Node localNode1;
/* 619 */     if (Map.class.isAssignableFrom(localClass)) {
/*     */       try
/*     */       {
/* 622 */         Map localMap1 = (Map)localClass.newInstance();
/* 623 */         for (localObject2 = paramNode.getChildren().iterator(); ((Iterator)localObject2).hasNext();) { localNode1 = (Node)((Iterator)localObject2).next();
/* 624 */           localMap1.put(localNode1.getName(), localNode1.getObject());
/*     */         }
/* 626 */         return localMap1;
/*     */       } catch (Exception localException1) {
/* 628 */         LOGGER.warn("Unable to create Map for " + paramPluginType.getElementName() + " of class " + localClass);
/*     */       }
/*     */     }
/*     */     
/*     */ 
/* 633 */     if (List.class.isAssignableFrom(localClass)) {
/*     */       try
/*     */       {
/* 636 */         List localList1 = (List)localClass.newInstance();
/* 637 */         for (localObject2 = paramNode.getChildren().iterator(); ((Iterator)localObject2).hasNext();) { localNode1 = (Node)((Iterator)localObject2).next();
/* 638 */           localList1.add(localNode1.getObject());
/*     */         }
/* 640 */         return localList1;
/*     */       } catch (Exception localException2) {
/* 642 */         LOGGER.warn("Unable to create List for " + paramPluginType.getElementName() + " of class " + localClass);
/*     */       }
/*     */     }
/*     */     
/*     */ 
/* 647 */     Object localObject1 = null;
/*     */     
/* 649 */     for (Object localObject3 : localClass.getMethods()) {
/* 650 */       if (((Method)localObject3).isAnnotationPresent(PluginFactory.class)) {
/* 651 */         localObject1 = localObject3;
/* 652 */         break;
/*     */       }
/*     */     }
/* 655 */     if (localObject1 == null) {
/* 656 */       return null;
/*     */     }
/*     */     
/* 659 */     Object localObject2 = ((Method)localObject1).getParameterAnnotations();
/* 660 */     Class[] arrayOfClass = ((Method)localObject1).getParameterTypes();
/* 661 */     if (localObject2.length != arrayOfClass.length) {
/* 662 */       LOGGER.error("Number of parameter annotations does not equal the number of paramters");
/*     */     }
/* 664 */     Object[] arrayOfObject = new Object[arrayOfClass.length];
/*     */     
/* 666 */     int k = 0;
/* 667 */     Map localMap2 = paramNode.getAttributes();
/* 668 */     List localList2 = paramNode.getChildren();
/* 669 */     StringBuilder localStringBuilder = new StringBuilder();
/* 670 */     ArrayList localArrayList1 = new ArrayList();
/*     */     
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     String str2;
/*     */     
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/* 682 */     for (str2 : localObject2) {
/* 683 */       String[] arrayOfString = null;
/* 684 */       Object localObject6; for (localObject6 : str2) {
/* 685 */         if ((localObject6 instanceof PluginAliases)) {
/* 686 */           arrayOfString = ((PluginAliases)localObject6).value();
/*     */         }
/*     */       }
/* 689 */       for (localObject6 : str2)
/* 690 */         if (!(localObject6 instanceof PluginAliases))
/*     */         {
/*     */ 
/* 693 */           if (localStringBuilder.length() == 0) {
/* 694 */             localStringBuilder.append(" with params(");
/*     */           } else {
/* 696 */             localStringBuilder.append(", ");
/*     */           }
/* 698 */           if ((localObject6 instanceof PluginNode)) {
/* 699 */             arrayOfObject[k] = paramNode;
/* 700 */             localStringBuilder.append("Node=").append(paramNode.getName());
/* 701 */           } else if ((localObject6 instanceof PluginConfiguration)) {
/* 702 */             arrayOfObject[k] = this;
/* 703 */             if (this.name != null) {
/* 704 */               localStringBuilder.append("Configuration(").append(this.name).append(")");
/*     */             } else
/* 706 */               localStringBuilder.append("Configuration"); } else { Object localObject7;
/*     */             String str4;
/* 708 */             Object localObject8; if ((localObject6 instanceof PluginValue)) {
/* 709 */               localObject7 = ((PluginValue)localObject6).value();
/* 710 */               str4 = paramNode.getValue();
/* 711 */               if (str4 == null) {
/* 712 */                 str4 = getAttrValue("value", null, localMap2);
/*     */               }
/* 714 */               localObject8 = this.subst.replace(paramLogEvent, str4);
/* 715 */               localStringBuilder.append((String)localObject7).append("=\"").append((String)localObject8).append("\"");
/* 716 */               arrayOfObject[k] = localObject8;
/* 717 */             } else if ((localObject6 instanceof PluginAttribute)) {
/* 718 */               localObject7 = (PluginAttribute)localObject6;
/* 719 */               str4 = ((PluginAttribute)localObject7).value();
/* 720 */               localObject8 = this.subst.replace(paramLogEvent, getAttrValue(str4, arrayOfString, localMap2));
/* 721 */               localStringBuilder.append(str4).append("=\"").append((String)localObject8).append("\"");
/* 722 */               arrayOfObject[k] = localObject8;
/* 723 */             } else if ((localObject6 instanceof PluginElement)) {
/* 724 */               localObject7 = (PluginElement)localObject6;
/* 725 */               str4 = ((PluginElement)localObject7).value();
/* 726 */               Object localObject9; if (arrayOfClass[k].isArray()) {
/* 727 */                 localObject8 = arrayOfClass[k].getComponentType();
/* 728 */                 ArrayList localArrayList2 = new ArrayList();
/* 729 */                 localStringBuilder.append(str4).append("={");
/* 730 */                 int i5 = 1;
/* 731 */                 for (localObject9 = localList2.iterator(); ((Iterator)localObject9).hasNext();) { Node localNode2 = (Node)((Iterator)localObject9).next();
/* 732 */                   localObject10 = localNode2.getType();
/* 733 */                   if ((((PluginElement)localObject7).value().equalsIgnoreCase(((PluginType)localObject10).getElementName())) || (((Class)localObject8).isAssignableFrom(((PluginType)localObject10).getPluginClass())))
/*     */                   {
/* 735 */                     localArrayList1.add(localNode2);
/* 736 */                     if (i5 == 0) {
/* 737 */                       localStringBuilder.append(", ");
/*     */                     }
/* 739 */                     i5 = 0;
/* 740 */                     localObject11 = localNode2.getObject();
/* 741 */                     if (localObject11 == null) {
/* 742 */                       LOGGER.error("Null object returned for " + localNode2.getName() + " in " + paramNode.getName());
/*     */                     }
/*     */                     else
/*     */                     {
/* 746 */                       if (localObject11.getClass().isArray()) {
/* 747 */                         printArray(localStringBuilder, (Object[])localObject11);
/* 748 */                         arrayOfObject[k] = localObject11;
/* 749 */                         break;
/*     */                       }
/* 751 */                       localStringBuilder.append(localNode2.toString());
/* 752 */                       localArrayList2.add(localObject11);
/*     */                     } } }
/*     */                 Object localObject11;
/* 755 */                 localStringBuilder.append("}");
/* 756 */                 if (arrayOfObject[k] != null) {
/*     */                   break;
/*     */                 }
/* 759 */                 if ((localArrayList2.size() > 0) && (!((Class)localObject8).isAssignableFrom(localArrayList2.get(0).getClass()))) {
/* 760 */                   LOGGER.error("Attempted to assign List containing class " + localArrayList2.get(0).getClass().getName() + " to array of type " + localObject8 + " for attribute " + str4);
/*     */                   
/*     */ 
/* 763 */                   break;
/*     */                 }
/* 765 */                 localObject9 = (Object[])Array.newInstance((Class)localObject8, localArrayList2.size());
/* 766 */                 int i6 = 0;
/* 767 */                 for (Object localObject10 = localArrayList2.iterator(); ((Iterator)localObject10).hasNext();) { localObject11 = ((Iterator)localObject10).next();
/* 768 */                   localObject9[i6] = localObject11;
/* 769 */                   i6++;
/*     */                 }
/* 771 */                 arrayOfObject[k] = localObject9;
/*     */               } else {
/* 773 */                 localObject8 = arrayOfClass[k];
/* 774 */                 int i4 = 0;
/* 775 */                 for (Iterator localIterator = localList2.iterator(); localIterator.hasNext();) { localObject9 = (Node)localIterator.next();
/* 776 */                   PluginType localPluginType = ((Node)localObject9).getType();
/* 777 */                   if ((((PluginElement)localObject7).value().equals(localPluginType.getElementName())) || (((Class)localObject8).isAssignableFrom(localPluginType.getPluginClass())))
/*     */                   {
/* 779 */                     localStringBuilder.append(((Node)localObject9).getName()).append("(").append(((Node)localObject9).toString()).append(")");
/* 780 */                     i4 = 1;
/* 781 */                     localArrayList1.add(localObject9);
/* 782 */                     arrayOfObject[k] = ((Node)localObject9).getObject();
/* 783 */                     break;
/*     */                   }
/*     */                 }
/* 786 */                 if (i4 == 0)
/* 787 */                   localStringBuilder.append("null");
/*     */               }
/*     */             }
/*     */           }
/*     */         }
/* 792 */       k++;
/*     */     }
/* 794 */     if (localStringBuilder.length() > 0)
/* 795 */       localStringBuilder.append(")");
/*     */     Object localObject5;
/*     */     String str1;
/* 798 */     if (localMap2.size() > 0) {
/* 799 */       ??? = new StringBuilder();
/* 800 */       for (localObject5 = localMap2.keySet().iterator(); ((Iterator)localObject5).hasNext();) { str1 = (String)((Iterator)localObject5).next();
/* 801 */         if (((StringBuilder)???).length() == 0) {
/* 802 */           ((StringBuilder)???).append(paramNode.getName());
/* 803 */           ((StringBuilder)???).append(" contains ");
/* 804 */           if (localMap2.size() == 1) {
/* 805 */             ((StringBuilder)???).append("an invalid element or attribute ");
/*     */           } else {
/* 807 */             ((StringBuilder)???).append("invalid attributes ");
/*     */           }
/*     */         } else {
/* 810 */           ((StringBuilder)???).append(", ");
/*     */         }
/* 812 */         ((StringBuilder)???).append("\"");
/* 813 */         ((StringBuilder)???).append(str1);
/* 814 */         ((StringBuilder)???).append("\"");
/*     */       }
/*     */       
/* 817 */       LOGGER.error(((StringBuilder)???).toString());
/*     */     }
/*     */     
/* 820 */     if ((!paramPluginType.isDeferChildren()) && (localArrayList1.size() != localList2.size())) {
/* 821 */       for (??? = localList2.iterator(); ((Iterator)???).hasNext();) { localObject5 = (Node)((Iterator)???).next();
/* 822 */         if (!localArrayList1.contains(localObject5))
/*     */         {
/*     */ 
/* 825 */           str1 = paramNode.getType().getElementName();
/* 826 */           str2 = str1 + " " + paramNode.getName();
/* 827 */           LOGGER.error(str2 + " has no parameter that matches element " + ((Node)localObject5).getName());
/*     */         }
/*     */       }
/*     */     }
/*     */     try {
/* 832 */       int m = ((Method)localObject1).getModifiers();
/* 833 */       if (!Modifier.isStatic(m)) {
/* 834 */         LOGGER.error(((Method)localObject1).getName() + " method is not static on class " + localClass.getName() + " for element " + paramNode.getName());
/*     */         
/* 836 */         return null;
/*     */       }
/* 838 */       LOGGER.debug("Calling {} on class {} for element {}{}", new Object[] { ((Method)localObject1).getName(), localClass.getName(), paramNode.getName(), localStringBuilder.toString() });
/*     */       
/*     */ 
/* 841 */       return ((Method)localObject1).invoke(null, arrayOfObject);
/*     */     }
/*     */     catch (Exception localException3)
/*     */     {
/* 845 */       LOGGER.error("Unable to invoke method " + ((Method)localObject1).getName() + " in class " + localClass.getName() + " for element " + paramNode.getName(), localException3);
/*     */     }
/*     */     
/* 848 */     return null;
/*     */   }
/*     */   
/*     */   private void printArray(StringBuilder paramStringBuilder, Object... paramVarArgs) {
/* 852 */     int i = 1;
/* 853 */     for (Object localObject : paramVarArgs) {
/* 854 */       if (i == 0) {
/* 855 */         paramStringBuilder.append(", ");
/*     */       }
/* 857 */       paramStringBuilder.append(localObject.toString());
/* 858 */       i = 0;
/*     */     }
/*     */   }
/*     */   
/*     */   private String getAttrValue(String paramString, String[] paramArrayOfString, Map<String, String> paramMap) {
/* 863 */     for (String str1 : paramMap.keySet()) { Object localObject;
/* 864 */       if (str1.equalsIgnoreCase(paramString)) {
/* 865 */         localObject = (String)paramMap.get(str1);
/* 866 */         paramMap.remove(str1);
/* 867 */         return (String)localObject;
/*     */       }
/* 869 */       if (paramArrayOfString != null) {
/* 870 */         for (String str2 : paramArrayOfString) {
/* 871 */           if (str1.equalsIgnoreCase(str2)) {
/* 872 */             String str3 = (String)paramMap.get(str1);
/* 873 */             paramMap.remove(str1);
/* 874 */             return str3;
/*     */           }
/*     */         }
/*     */       }
/*     */     }
/* 879 */     return null;
/*     */   }
/*     */   
/*     */   private void setParents() {
/* 883 */     for (Map.Entry localEntry : this.loggers.entrySet()) {
/* 884 */       LoggerConfig localLoggerConfig1 = (LoggerConfig)localEntry.getValue();
/* 885 */       String str = (String)localEntry.getKey();
/* 886 */       if (!str.equals("")) {
/* 887 */         int i = str.lastIndexOf('.');
/* 888 */         if (i > 0) {
/* 889 */           str = str.substring(0, i);
/* 890 */           LoggerConfig localLoggerConfig2 = getLoggerConfig(str);
/* 891 */           if (localLoggerConfig2 == null) {
/* 892 */             localLoggerConfig2 = this.root;
/*     */           }
/* 894 */           localLoggerConfig1.setParent(localLoggerConfig2);
/*     */         } else {
/* 896 */           localLoggerConfig1.setParent(this.root);
/*     */         }
/*     */       }
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\org\apache\logging\log4j\core\config\BaseConfiguration.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */
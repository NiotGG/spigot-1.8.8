/*     */ package org.apache.logging.log4j.core;
/*     */ 
/*     */ import java.beans.PropertyChangeEvent;
/*     */ import java.beans.PropertyChangeListener;
/*     */ import java.io.File;
/*     */ import java.net.URI;
/*     */ import java.util.HashMap;
/*     */ import java.util.Map;
/*     */ import java.util.concurrent.ConcurrentHashMap;
/*     */ import java.util.concurrent.ConcurrentMap;
/*     */ import java.util.concurrent.CopyOnWriteArrayList;
/*     */ import java.util.concurrent.locks.Lock;
/*     */ import java.util.concurrent.locks.ReentrantLock;
/*     */ import org.apache.logging.log4j.LogManager;
/*     */ import org.apache.logging.log4j.core.config.Configuration;
/*     */ import org.apache.logging.log4j.core.config.ConfigurationFactory;
/*     */ import org.apache.logging.log4j.core.config.ConfigurationListener;
/*     */ import org.apache.logging.log4j.core.config.DefaultConfiguration;
/*     */ import org.apache.logging.log4j.core.config.NullConfiguration;
/*     */ import org.apache.logging.log4j.core.config.Reconfigurable;
/*     */ import org.apache.logging.log4j.core.helpers.Assert;
/*     */ import org.apache.logging.log4j.core.helpers.NetUtils;
/*     */ import org.apache.logging.log4j.message.MessageFactory;
/*     */ import org.apache.logging.log4j.spi.AbstractLogger;
/*     */ import org.apache.logging.log4j.spi.LoggerContextFactory;
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
/*     */ public class LoggerContext
/*     */   implements org.apache.logging.log4j.spi.LoggerContext, ConfigurationListener, LifeCycle
/*     */ {
/*     */   public static final String PROPERTY_CONFIG = "config";
/*  54 */   private static final StatusLogger LOGGER = ;
/*     */   
/*  56 */   private final ConcurrentMap<String, Logger> loggers = new ConcurrentHashMap();
/*  57 */   private final CopyOnWriteArrayList<PropertyChangeListener> propertyChangeListeners = new CopyOnWriteArrayList();
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*  63 */   private volatile Configuration config = new DefaultConfiguration();
/*     */   
/*     */   private Object externalContext;
/*     */   private final String name;
/*     */   private URI configLocation;
/*  68 */   private ShutdownThread shutdownThread = null;
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public static enum Status
/*     */   {
/*  75 */     INITIALIZED, 
/*     */     
/*  77 */     STARTING, 
/*     */     
/*  79 */     STARTED, 
/*     */     
/*  81 */     STOPPING, 
/*     */     
/*  83 */     STOPPED;
/*     */     
/*     */     private Status() {} }
/*  86 */   private volatile Status status = Status.INITIALIZED;
/*     */   
/*  88 */   private final Lock configLock = new ReentrantLock();
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public LoggerContext(String paramString)
/*     */   {
/*  95 */     this(paramString, null, (URI)null);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public LoggerContext(String paramString, Object paramObject)
/*     */   {
/* 104 */     this(paramString, paramObject, (URI)null);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public LoggerContext(String paramString, Object paramObject, URI paramURI)
/*     */   {
/* 114 */     this.name = paramString;
/* 115 */     this.externalContext = paramObject;
/* 116 */     this.configLocation = paramURI;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public LoggerContext(String paramString1, Object paramObject, String paramString2)
/*     */   {
/* 128 */     this.name = paramString1;
/* 129 */     this.externalContext = paramObject;
/* 130 */     if (paramString2 != null) {
/*     */       URI localURI;
/*     */       try {
/* 133 */         localURI = new File(paramString2).toURI();
/*     */       } catch (Exception localException) {
/* 135 */         localURI = null;
/*     */       }
/* 137 */       this.configLocation = localURI;
/*     */     } else {
/* 139 */       this.configLocation = null;
/*     */     }
/*     */   }
/*     */   
/*     */   public void start()
/*     */   {
/* 145 */     if (this.configLock.tryLock()) {
/*     */       try {
/* 147 */         if ((this.status == Status.INITIALIZED) || (this.status == Status.STOPPED)) {
/* 148 */           this.status = Status.STARTING;
/* 149 */           reconfigure();
/* 150 */           if (this.config.isShutdownHookEnabled()) {
/* 151 */             this.shutdownThread = new ShutdownThread(this);
/*     */             try {
/* 153 */               Runtime.getRuntime().addShutdownHook(this.shutdownThread);
/*     */             } catch (IllegalStateException localIllegalStateException) {
/* 155 */               LOGGER.warn("Unable to register shutdown hook due to JVM state");
/* 156 */               this.shutdownThread = null;
/*     */             } catch (SecurityException localSecurityException) {
/* 158 */               LOGGER.warn("Unable to register shutdown hook due to security restrictions");
/* 159 */               this.shutdownThread = null;
/*     */             }
/*     */           }
/* 162 */           this.status = Status.STARTED;
/*     */         }
/*     */       } finally {
/* 165 */         this.configLock.unlock();
/*     */       }
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public void start(Configuration paramConfiguration)
/*     */   {
/* 175 */     if (this.configLock.tryLock()) {
/*     */       try {
/* 177 */         if (((this.status == Status.INITIALIZED) || (this.status == Status.STOPPED)) && (paramConfiguration.isShutdownHookEnabled())) {
/* 178 */           this.shutdownThread = new ShutdownThread(this);
/*     */           try {
/* 180 */             Runtime.getRuntime().addShutdownHook(this.shutdownThread);
/*     */           } catch (IllegalStateException localIllegalStateException) {
/* 182 */             LOGGER.warn("Unable to register shutdown hook due to JVM state");
/* 183 */             this.shutdownThread = null;
/*     */           } catch (SecurityException localSecurityException) {
/* 185 */             LOGGER.warn("Unable to register shutdown hook due to security restrictions");
/* 186 */             this.shutdownThread = null;
/*     */           }
/* 188 */           this.status = Status.STARTED;
/*     */         }
/*     */       } finally {
/* 191 */         this.configLock.unlock();
/*     */       }
/*     */     }
/* 194 */     setConfiguration(paramConfiguration);
/*     */   }
/*     */   
/*     */   public void stop()
/*     */   {
/* 199 */     this.configLock.lock();
/*     */     try {
/* 201 */       if (this.status == Status.STOPPED) {
/*     */         return;
/*     */       }
/* 204 */       this.status = Status.STOPPING;
/* 205 */       if (this.shutdownThread != null) {
/* 206 */         Runtime.getRuntime().removeShutdownHook(this.shutdownThread);
/* 207 */         this.shutdownThread = null;
/*     */       }
/* 209 */       Configuration localConfiguration = this.config;
/* 210 */       this.config = new NullConfiguration();
/* 211 */       updateLoggers();
/* 212 */       localConfiguration.stop();
/* 213 */       this.externalContext = null;
/* 214 */       LogManager.getFactory().removeContext(this);
/* 215 */       this.status = Status.STOPPED;
/*     */     } finally {
/* 217 */       this.configLock.unlock();
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public String getName()
/*     */   {
/* 227 */     return this.name;
/*     */   }
/*     */   
/*     */   public Status getStatus() {
/* 231 */     return this.status;
/*     */   }
/*     */   
/*     */   public boolean isStarted()
/*     */   {
/* 236 */     return this.status == Status.STARTED;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public void setExternalContext(Object paramObject)
/*     */   {
/* 244 */     this.externalContext = paramObject;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public Object getExternalContext()
/*     */   {
/* 253 */     return this.externalContext;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public Logger getLogger(String paramString)
/*     */   {
/* 263 */     return getLogger(paramString, null);
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
/*     */   public Logger getLogger(String paramString, MessageFactory paramMessageFactory)
/*     */   {
/* 276 */     Logger localLogger1 = (Logger)this.loggers.get(paramString);
/* 277 */     if (localLogger1 != null) {
/* 278 */       AbstractLogger.checkMessageFactory(localLogger1, paramMessageFactory);
/* 279 */       return localLogger1;
/*     */     }
/*     */     
/* 282 */     localLogger1 = newInstance(this, paramString, paramMessageFactory);
/* 283 */     Logger localLogger2 = (Logger)this.loggers.putIfAbsent(paramString, localLogger1);
/* 284 */     return localLogger2 == null ? localLogger1 : localLogger2;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public boolean hasLogger(String paramString)
/*     */   {
/* 294 */     return this.loggers.containsKey(paramString);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public Configuration getConfiguration()
/*     */   {
/* 304 */     return this.config;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public void addFilter(Filter paramFilter)
/*     */   {
/* 313 */     this.config.addFilter(paramFilter);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public void removeFilter(Filter paramFilter)
/*     */   {
/* 321 */     this.config.removeFilter(paramFilter);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   private synchronized Configuration setConfiguration(Configuration paramConfiguration)
/*     */   {
/* 330 */     if (paramConfiguration == null) {
/* 331 */       throw new NullPointerException("No Configuration was provided");
/*     */     }
/* 333 */     Configuration localConfiguration = this.config;
/* 334 */     paramConfiguration.addListener(this);
/* 335 */     HashMap localHashMap = new HashMap();
/* 336 */     localHashMap.put("hostName", NetUtils.getLocalHostname());
/* 337 */     localHashMap.put("contextName", this.name);
/* 338 */     paramConfiguration.addComponent("ContextProperties", localHashMap);
/* 339 */     paramConfiguration.start();
/* 340 */     this.config = paramConfiguration;
/* 341 */     updateLoggers();
/* 342 */     if (localConfiguration != null) {
/* 343 */       localConfiguration.removeListener(this);
/* 344 */       localConfiguration.stop();
/*     */     }
/*     */     
/*     */ 
/* 348 */     PropertyChangeEvent localPropertyChangeEvent = new PropertyChangeEvent(this, "config", localConfiguration, paramConfiguration);
/* 349 */     for (PropertyChangeListener localPropertyChangeListener : this.propertyChangeListeners) {
/* 350 */       localPropertyChangeListener.propertyChange(localPropertyChangeEvent);
/*     */     }
/* 352 */     return localConfiguration;
/*     */   }
/*     */   
/*     */   public void addPropertyChangeListener(PropertyChangeListener paramPropertyChangeListener) {
/* 356 */     this.propertyChangeListeners.add(Assert.isNotNull(paramPropertyChangeListener, "listener"));
/*     */   }
/*     */   
/*     */   public void removePropertyChangeListener(PropertyChangeListener paramPropertyChangeListener) {
/* 360 */     this.propertyChangeListeners.remove(paramPropertyChangeListener);
/*     */   }
/*     */   
/*     */   public synchronized URI getConfigLocation() {
/* 364 */     return this.configLocation;
/*     */   }
/*     */   
/*     */   public synchronized void setConfigLocation(URI paramURI) {
/* 368 */     this.configLocation = paramURI;
/* 369 */     reconfigure();
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public synchronized void reconfigure()
/*     */   {
/* 376 */     LOGGER.debug("Reconfiguration started for context " + this.name);
/* 377 */     Configuration localConfiguration = ConfigurationFactory.getInstance().getConfiguration(this.name, this.configLocation);
/* 378 */     setConfiguration(localConfiguration);
/*     */     
/*     */ 
/*     */ 
/*     */ 
/* 383 */     LOGGER.debug("Reconfiguration completed");
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public void updateLoggers()
/*     */   {
/* 390 */     updateLoggers(this.config);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public void updateLoggers(Configuration paramConfiguration)
/*     */   {
/* 398 */     for (Logger localLogger : this.loggers.values()) {
/* 399 */       localLogger.updateConfiguration(paramConfiguration);
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public synchronized void onChange(Reconfigurable paramReconfigurable)
/*     */   {
/* 411 */     LOGGER.debug("Reconfiguration started for context " + this.name);
/* 412 */     Configuration localConfiguration = paramReconfigurable.reconfigure();
/* 413 */     if (localConfiguration != null) {
/* 414 */       setConfiguration(localConfiguration);
/* 415 */       LOGGER.debug("Reconfiguration completed");
/*     */     } else {
/* 417 */       LOGGER.debug("Reconfiguration failed");
/*     */     }
/*     */   }
/*     */   
/*     */   protected Logger newInstance(LoggerContext paramLoggerContext, String paramString, MessageFactory paramMessageFactory)
/*     */   {
/* 423 */     return new Logger(paramLoggerContext, paramString, paramMessageFactory);
/*     */   }
/*     */   
/*     */   private class ShutdownThread extends Thread
/*     */   {
/*     */     private final LoggerContext context;
/*     */     
/*     */     public ShutdownThread(LoggerContext paramLoggerContext) {
/* 431 */       this.context = paramLoggerContext;
/*     */     }
/*     */     
/*     */     public void run()
/*     */     {
/* 436 */       this.context.shutdownThread = null;
/* 437 */       this.context.stop();
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\org\apache\logging\log4j\core\LoggerContext.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */
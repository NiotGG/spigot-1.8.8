/*     */ package org.apache.logging.log4j;
/*     */ 
/*     */ import java.net.URI;
/*     */ import java.net.URL;
/*     */ import java.util.Iterator;
/*     */ import java.util.Map.Entry;
/*     */ import java.util.Set;
/*     */ import java.util.SortedMap;
/*     */ import java.util.TreeMap;
/*     */ import org.apache.logging.log4j.message.MessageFactory;
/*     */ import org.apache.logging.log4j.message.StringFormatterMessageFactory;
/*     */ import org.apache.logging.log4j.simple.SimpleLoggerContextFactory;
/*     */ import org.apache.logging.log4j.spi.LoggerContext;
/*     */ import org.apache.logging.log4j.spi.LoggerContextFactory;
/*     */ import org.apache.logging.log4j.spi.Provider;
/*     */ import org.apache.logging.log4j.status.StatusLogger;
/*     */ import org.apache.logging.log4j.util.PropertiesUtil;
/*     */ import org.apache.logging.log4j.util.ProviderUtil;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class LogManager
/*     */ {
/*     */   private static LoggerContextFactory factory;
/*     */   private static final String FACTORY_PROPERTY_NAME = "log4j2.loggerContextFactory";
/*  44 */   private static final Logger LOGGER = ;
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public static final String ROOT_LOGGER_NAME = "";
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   static
/*     */   {
/*  57 */     PropertiesUtil localPropertiesUtil = PropertiesUtil.getProperties();
/*  58 */     String str = localPropertiesUtil.getStringProperty("log4j2.loggerContextFactory");
/*  59 */     ClassLoader localClassLoader = ProviderUtil.findClassLoader();
/*  60 */     if (str != null) {
/*     */       try {
/*  62 */         Class localClass1 = localClassLoader.loadClass(str);
/*  63 */         if (LoggerContextFactory.class.isAssignableFrom(localClass1)) {
/*  64 */           factory = (LoggerContextFactory)localClass1.newInstance();
/*     */         }
/*     */       } catch (ClassNotFoundException localClassNotFoundException1) {
/*  67 */         LOGGER.error("Unable to locate configured LoggerContextFactory {}", new Object[] { str });
/*     */       } catch (Exception localException1) {
/*  69 */         LOGGER.error("Unable to create configured LoggerContextFactory {}", new Object[] { str, localException1 });
/*     */       }
/*     */     }
/*     */     
/*  73 */     if (factory == null) {
/*  74 */       TreeMap localTreeMap = new TreeMap();
/*     */       
/*  76 */       if (ProviderUtil.hasProviders()) {
/*  77 */         Iterator localIterator = ProviderUtil.getProviders();
/*  78 */         Object localObject1; Object localObject2; while (localIterator.hasNext()) {
/*  79 */           localObject1 = (Provider)localIterator.next();
/*  80 */           localObject2 = ((Provider)localObject1).getClassName();
/*  81 */           if (localObject2 != null) {
/*     */             try {
/*  83 */               Class localClass2 = localClassLoader.loadClass((String)localObject2);
/*  84 */               if (LoggerContextFactory.class.isAssignableFrom(localClass2)) {
/*  85 */                 localTreeMap.put(((Provider)localObject1).getPriority(), (LoggerContextFactory)localClass2.newInstance());
/*     */               } else {
/*  87 */                 LOGGER.error((String)localObject2 + " does not implement " + LoggerContextFactory.class.getName());
/*     */               }
/*     */             } catch (ClassNotFoundException localClassNotFoundException2) {
/*  90 */               LOGGER.error("Unable to locate class " + (String)localObject2 + " specified in " + ((Provider)localObject1).getURL().toString(), localClassNotFoundException2);
/*     */             }
/*     */             catch (IllegalAccessException localIllegalAccessException) {
/*  93 */               LOGGER.error("Unable to create class " + (String)localObject2 + " specified in " + ((Provider)localObject1).getURL().toString(), localIllegalAccessException);
/*     */             }
/*     */             catch (Exception localException2) {
/*  96 */               LOGGER.error("Unable to create class " + (String)localObject2 + " specified in " + ((Provider)localObject1).getURL().toString(), localException2);
/*     */               
/*  98 */               localException2.printStackTrace();
/*     */             }
/*     */           }
/*     */         }
/*     */         
/* 103 */         if (localTreeMap.size() == 0) {
/* 104 */           LOGGER.error("Unable to locate a logging implementation, using SimpleLogger");
/* 105 */           factory = new SimpleLoggerContextFactory();
/*     */         } else {
/* 107 */           localObject1 = new StringBuilder("Multiple logging implementations found: \n");
/* 108 */           for (localObject2 = localTreeMap.entrySet().iterator(); ((Iterator)localObject2).hasNext();) { Map.Entry localEntry = (Map.Entry)((Iterator)localObject2).next();
/* 109 */             ((StringBuilder)localObject1).append("Factory: ").append(((LoggerContextFactory)localEntry.getValue()).getClass().getName());
/* 110 */             ((StringBuilder)localObject1).append(", Weighting: ").append(localEntry.getKey()).append("\n");
/*     */           }
/* 112 */           factory = (LoggerContextFactory)localTreeMap.get(localTreeMap.lastKey());
/* 113 */           ((StringBuilder)localObject1).append("Using factory: ").append(factory.getClass().getName());
/* 114 */           LOGGER.warn(((StringBuilder)localObject1).toString());
/*     */         }
/*     */       }
/*     */       else {
/* 118 */         LOGGER.error("Unable to locate a logging implementation, using SimpleLogger");
/* 119 */         factory = new SimpleLoggerContextFactory();
/*     */       }
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   private static String getClassName(int paramInt)
/*     */   {
/* 131 */     return new Throwable().getStackTrace()[paramInt].getClassName();
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public static LoggerContext getContext()
/*     */   {
/* 142 */     return factory.getContext(LogManager.class.getName(), null, true);
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
/*     */   public static LoggerContext getContext(boolean paramBoolean)
/*     */   {
/* 155 */     return factory.getContext(LogManager.class.getName(), null, paramBoolean);
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
/*     */   public static LoggerContext getContext(ClassLoader paramClassLoader, boolean paramBoolean)
/*     */   {
/* 170 */     return factory.getContext(LogManager.class.getName(), paramClassLoader, paramBoolean);
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
/*     */   public static LoggerContext getContext(ClassLoader paramClassLoader, boolean paramBoolean, URI paramURI)
/*     */   {
/* 187 */     return factory.getContext(LogManager.class.getName(), paramClassLoader, paramBoolean, paramURI);
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
/*     */   protected static LoggerContext getContext(String paramString, boolean paramBoolean)
/*     */   {
/* 200 */     return factory.getContext(paramString, null, paramBoolean);
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
/*     */   protected static LoggerContext getContext(String paramString, ClassLoader paramClassLoader, boolean paramBoolean)
/*     */   {
/* 216 */     return factory.getContext(paramString, paramClassLoader, paramBoolean);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public static LoggerContextFactory getFactory()
/*     */   {
/* 224 */     return factory;
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
/*     */   public static Logger getFormatterLogger(Class<?> paramClass)
/*     */   {
/* 254 */     return getLogger(paramClass != null ? paramClass.getName() : getClassName(2), StringFormatterMessageFactory.INSTANCE);
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
/*     */   public static Logger getFormatterLogger(Object paramObject)
/*     */   {
/* 284 */     return getLogger(paramObject != null ? paramObject.getClass().getName() : getClassName(2), StringFormatterMessageFactory.INSTANCE);
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
/*     */   public static Logger getFormatterLogger(String paramString)
/*     */   {
/* 314 */     return getLogger(paramString != null ? paramString : getClassName(2), StringFormatterMessageFactory.INSTANCE);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public static Logger getLogger()
/*     */   {
/* 322 */     return getLogger(getClassName(2));
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public static Logger getLogger(Class<?> paramClass)
/*     */   {
/* 332 */     return getLogger(paramClass != null ? paramClass.getName() : getClassName(2));
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public static Logger getLogger(Class<?> paramClass, MessageFactory paramMessageFactory)
/*     */   {
/* 344 */     return getLogger(paramClass != null ? paramClass.getName() : getClassName(2), paramMessageFactory);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public static Logger getLogger(MessageFactory paramMessageFactory)
/*     */   {
/* 354 */     return getLogger(getClassName(2), paramMessageFactory);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public static Logger getLogger(Object paramObject)
/*     */   {
/* 364 */     return getLogger(paramObject != null ? paramObject.getClass().getName() : getClassName(2));
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public static Logger getLogger(Object paramObject, MessageFactory paramMessageFactory)
/*     */   {
/* 376 */     return getLogger(paramObject != null ? paramObject.getClass().getName() : getClassName(2), paramMessageFactory);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public static Logger getLogger(String paramString)
/*     */   {
/* 386 */     String str = paramString != null ? paramString : getClassName(2);
/* 387 */     return factory.getContext(LogManager.class.getName(), null, false).getLogger(str);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public static Logger getLogger(String paramString, MessageFactory paramMessageFactory)
/*     */   {
/* 399 */     String str = paramString != null ? paramString : getClassName(2);
/* 400 */     return factory.getContext(LogManager.class.getName(), null, false).getLogger(str, paramMessageFactory);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   protected static Logger getLogger(String paramString1, String paramString2)
/*     */   {
/* 411 */     return factory.getContext(paramString1, null, false).getLogger(paramString2);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public static Logger getRootLogger()
/*     */   {
/* 420 */     return getLogger("");
/*     */   }
/*     */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\org\apache\logging\log4j\LogManager.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */
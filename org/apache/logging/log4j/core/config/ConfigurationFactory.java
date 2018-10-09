/*     */ package org.apache.logging.log4j.core.config;
/*     */ 
/*     */ import java.io.File;
/*     */ import java.io.FileInputStream;
/*     */ import java.io.FileNotFoundException;
/*     */ import java.io.IOException;
/*     */ import java.io.InputStream;
/*     */ import java.net.MalformedURLException;
/*     */ import java.net.URI;
/*     */ import java.net.URISyntaxException;
/*     */ import java.net.URL;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Collection;
/*     */ import java.util.Collections;
/*     */ import java.util.Iterator;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import java.util.Set;
/*     */ import java.util.TreeSet;
/*     */ import org.apache.logging.log4j.Logger;
/*     */ import org.apache.logging.log4j.core.config.plugins.PluginManager;
/*     */ import org.apache.logging.log4j.core.config.plugins.PluginType;
/*     */ import org.apache.logging.log4j.core.helpers.FileUtils;
/*     */ import org.apache.logging.log4j.core.helpers.Loader;
/*     */ import org.apache.logging.log4j.core.lookup.Interpolator;
/*     */ import org.apache.logging.log4j.core.lookup.StrSubstitutor;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public abstract class ConfigurationFactory
/*     */ {
/*     */   public static final String CONFIGURATION_FACTORY_PROPERTY = "log4j.configurationFactory";
/*     */   public static final String CONFIGURATION_FILE_PROPERTY = "log4j.configurationFile";
/*  81 */   protected static final Logger LOGGER = ;
/*     */   
/*     */ 
/*     */ 
/*     */   protected static final String TEST_PREFIX = "log4j2-test";
/*     */   
/*     */ 
/*     */ 
/*     */   protected static final String DEFAULT_PREFIX = "log4j2";
/*     */   
/*     */ 
/*     */ 
/*     */   private static final String CLASS_LOADER_SCHEME = "classloader";
/*     */   
/*     */ 
/*     */ 
/*  97 */   private static final int CLASS_LOADER_SCHEME_LENGTH = "classloader".length() + 1;
/*     */   
/*     */ 
/*     */   private static final String CLASS_PATH_SCHEME = "classpath";
/*     */   
/*     */ 
/* 103 */   private static final int CLASS_PATH_SCHEME_LENGTH = "classpath".length() + 1;
/*     */   
/* 105 */   private static volatile List<ConfigurationFactory> factories = null;
/*     */   
/* 107 */   private static ConfigurationFactory configFactory = new Factory(null);
/*     */   
/* 109 */   public ConfigurationFactory() { this.substitutor = new StrSubstitutor(new Interpolator()); }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public static ConfigurationFactory getInstance()
/*     */   {
/* 116 */     if (factories == null) {
/* 117 */       synchronized ("log4j2-test") {
/* 118 */         if (factories == null) {
/* 119 */           ArrayList localArrayList = new ArrayList();
/* 120 */           String str = PropertiesUtil.getProperties().getStringProperty("log4j.configurationFactory");
/* 121 */           if (str != null) {
/* 122 */             addFactory(localArrayList, str);
/*     */           }
/* 124 */           PluginManager localPluginManager = new PluginManager("ConfigurationFactory");
/* 125 */           localPluginManager.collectPlugins();
/* 126 */           Map localMap = localPluginManager.getPlugins();
/* 127 */           TreeSet localTreeSet = new TreeSet();
/* 128 */           for (Iterator localIterator = localMap.values().iterator(); localIterator.hasNext();) { localObject1 = (PluginType)localIterator.next();
/*     */             try
/*     */             {
/* 131 */               Class localClass = ((PluginType)localObject1).getPluginClass();
/* 132 */               Order localOrder = (Order)localClass.getAnnotation(Order.class);
/* 133 */               if (localOrder != null) {
/* 134 */                 int i = localOrder.value();
/* 135 */                 localTreeSet.add(new WeightedFactory(i, localClass));
/*     */               }
/*     */             } catch (Exception localException) {
/* 138 */               LOGGER.warn("Unable to add class " + ((PluginType)localObject1).getPluginClass());
/*     */             } }
/*     */           Object localObject1;
/* 141 */           for (localIterator = localTreeSet.iterator(); localIterator.hasNext();) { localObject1 = (WeightedFactory)localIterator.next();
/* 142 */             addFactory(localArrayList, ((WeightedFactory)localObject1).factoryClass);
/*     */           }
/* 144 */           factories = Collections.unmodifiableList(localArrayList);
/*     */         }
/*     */       }
/*     */     }
/*     */     
/* 149 */     return configFactory;
/*     */   }
/*     */   
/*     */   private static void addFactory(List<ConfigurationFactory> paramList, String paramString)
/*     */   {
/*     */     try {
/* 155 */       addFactory(paramList, Class.forName(paramString));
/*     */     } catch (ClassNotFoundException localClassNotFoundException) {
/* 157 */       LOGGER.error("Unable to load class " + paramString, localClassNotFoundException);
/*     */     } catch (Exception localException) {
/* 159 */       LOGGER.error("Unable to load class " + paramString, localException);
/*     */     }
/*     */   }
/*     */   
/*     */   private static void addFactory(List<ConfigurationFactory> paramList, Class<ConfigurationFactory> paramClass)
/*     */   {
/*     */     try {
/* 166 */       paramList.add(paramClass.newInstance());
/*     */     } catch (Exception localException) {
/* 168 */       LOGGER.error("Unable to create instance of " + paramClass.getName(), localException);
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public static void setConfigurationFactory(ConfigurationFactory paramConfigurationFactory)
/*     */   {
/* 177 */     configFactory = paramConfigurationFactory;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public static void resetConfigurationFactory()
/*     */   {
/* 185 */     configFactory = new Factory(null);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public static void removeConfigurationFactory(ConfigurationFactory paramConfigurationFactory)
/*     */   {
/* 193 */     if (configFactory == paramConfigurationFactory) {
/* 194 */       configFactory = new Factory(null);
/*     */     }
/*     */   }
/*     */   
/*     */   protected abstract String[] getSupportedTypes();
/*     */   
/*     */   protected boolean isActive() {
/* 201 */     return true;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public abstract Configuration getConfiguration(ConfigurationSource paramConfigurationSource);
/*     */   
/*     */ 
/*     */ 
/*     */   public Configuration getConfiguration(String paramString, URI paramURI)
/*     */   {
/* 213 */     if (!isActive()) {
/* 214 */       return null;
/*     */     }
/* 216 */     if (paramURI != null) {
/* 217 */       ConfigurationSource localConfigurationSource = getInputFromURI(paramURI);
/* 218 */       if (localConfigurationSource != null) {
/* 219 */         return getConfiguration(localConfigurationSource);
/*     */       }
/*     */     }
/* 222 */     return null;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   protected ConfigurationSource getInputFromURI(URI paramURI)
/*     */   {
/* 231 */     File localFile = FileUtils.fileFromURI(paramURI);
/* 232 */     if ((localFile != null) && (localFile.exists()) && (localFile.canRead())) {
/*     */       try {
/* 234 */         return new ConfigurationSource(new FileInputStream(localFile), localFile);
/*     */       } catch (FileNotFoundException localFileNotFoundException) {
/* 236 */         LOGGER.error("Cannot locate file " + paramURI.getPath(), localFileNotFoundException);
/*     */       }
/*     */     }
/* 239 */     String str1 = paramURI.getScheme();
/* 240 */     int i = (str1 != null) && (str1.equals("classloader")) ? 1 : 0;
/* 241 */     int j = (str1 != null) && (i == 0) && (str1.equals("classpath")) ? 1 : 0;
/* 242 */     if ((str1 == null) || (i != 0) || (j != 0)) {
/* 243 */       ClassLoader localClassLoader = getClass().getClassLoader();
/*     */       String str2;
/* 245 */       if (i != 0) {
/* 246 */         str2 = paramURI.toString().substring(CLASS_LOADER_SCHEME_LENGTH);
/* 247 */       } else if (j != 0) {
/* 248 */         str2 = paramURI.toString().substring(CLASS_PATH_SCHEME_LENGTH);
/*     */       } else {
/* 250 */         str2 = paramURI.getPath();
/*     */       }
/* 252 */       ConfigurationSource localConfigurationSource = getInputFromResource(str2, localClassLoader);
/* 253 */       if (localConfigurationSource != null) {
/* 254 */         return localConfigurationSource;
/*     */       }
/*     */     }
/*     */     try {
/* 258 */       return new ConfigurationSource(paramURI.toURL().openStream(), paramURI.getPath());
/*     */     } catch (MalformedURLException localMalformedURLException) {
/* 260 */       LOGGER.error("Invalid URL " + paramURI.toString(), localMalformedURLException);
/*     */     } catch (IOException localIOException) {
/* 262 */       LOGGER.error("Unable to access " + paramURI.toString(), localIOException);
/*     */     } catch (Exception localException) {
/* 264 */       LOGGER.error("Unable to access " + paramURI.toString(), localException);
/*     */     }
/* 266 */     return null;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   protected ConfigurationSource getInputFromString(String paramString, ClassLoader paramClassLoader)
/*     */   {
/*     */     try
/*     */     {
/* 277 */       URL localURL = new URL(paramString);
/* 278 */       return new ConfigurationSource(localURL.openStream(), FileUtils.fileFromURI(localURL.toURI()));
/*     */     } catch (Exception localException) {
/* 280 */       ConfigurationSource localConfigurationSource = getInputFromResource(paramString, paramClassLoader);
/* 281 */       if (localConfigurationSource == null) {
/*     */         try {
/* 283 */           File localFile = new File(paramString);
/* 284 */           return new ConfigurationSource(new FileInputStream(localFile), localFile);
/*     */         }
/*     */         catch (FileNotFoundException localFileNotFoundException) {}
/*     */       }
/*     */       
/* 289 */       return localConfigurationSource;
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   protected final StrSubstitutor substitutor;
/*     */   
/*     */ 
/*     */   protected ConfigurationSource getInputFromResource(String paramString, ClassLoader paramClassLoader)
/*     */   {
/* 300 */     URL localURL = Loader.getResource(paramString, paramClassLoader);
/* 301 */     if (localURL == null) {
/* 302 */       return null;
/*     */     }
/* 304 */     InputStream localInputStream = null;
/*     */     try {
/* 306 */       localInputStream = localURL.openStream();
/*     */     } catch (IOException localIOException) {
/* 308 */       return null;
/*     */     }
/* 310 */     if (localInputStream == null) {
/* 311 */       return null;
/*     */     }
/*     */     
/* 314 */     if (FileUtils.isFile(localURL)) {
/*     */       try {
/* 316 */         return new ConfigurationSource(localInputStream, FileUtils.fileFromURI(localURL.toURI()));
/*     */       }
/*     */       catch (URISyntaxException localURISyntaxException) {}
/*     */     }
/*     */     
/* 321 */     return new ConfigurationSource(localInputStream, paramString);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   private static class WeightedFactory
/*     */     implements Comparable<WeightedFactory>
/*     */   {
/*     */     private final int weight;
/*     */     
/*     */ 
/*     */     private final Class<ConfigurationFactory> factoryClass;
/*     */     
/*     */ 
/*     */     public WeightedFactory(int paramInt, Class<ConfigurationFactory> paramClass)
/*     */     {
/* 337 */       this.weight = paramInt;
/* 338 */       this.factoryClass = paramClass;
/*     */     }
/*     */     
/*     */     public int compareTo(WeightedFactory paramWeightedFactory)
/*     */     {
/* 343 */       int i = paramWeightedFactory.weight;
/* 344 */       if (this.weight == i)
/* 345 */         return 0;
/* 346 */       if (this.weight > i) {
/* 347 */         return -1;
/*     */       }
/* 349 */       return 1;
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   private static class Factory
/*     */     extends ConfigurationFactory
/*     */   {
/*     */     public Configuration getConfiguration(String paramString, URI paramURI)
/*     */     {
/*     */       Object localObject2;
/*     */       
/*     */ 
/*     */       Object localObject3;
/*     */       
/*     */ 
/*     */       Object localObject4;
/*     */       
/* 368 */       if (paramURI == null) {
/* 369 */         localObject1 = this.substitutor.replace(PropertiesUtil.getProperties().getStringProperty("log4j.configurationFile"));
/*     */         
/* 371 */         if (localObject1 != null) {
/* 372 */           localObject2 = null;
/*     */           try {
/* 374 */             localObject2 = getInputFromURI(new URI((String)localObject1));
/*     */           }
/*     */           catch (Exception localException) {}
/*     */           
/* 378 */           if (localObject2 == null) {
/* 379 */             localObject3 = getClass().getClassLoader();
/* 380 */             localObject2 = getInputFromString((String)localObject1, (ClassLoader)localObject3);
/*     */           }
/* 382 */           if (localObject2 != null) {
/* 383 */             for (localObject3 = ConfigurationFactory.factories.iterator(); ((Iterator)localObject3).hasNext();) { localObject4 = (ConfigurationFactory)((Iterator)localObject3).next();
/* 384 */               String[] arrayOfString1 = ((ConfigurationFactory)localObject4).getSupportedTypes();
/* 385 */               if (arrayOfString1 != null) {
/* 386 */                 for (String str2 : arrayOfString1) {
/* 387 */                   if ((str2.equals("*")) || (((String)localObject1).endsWith(str2))) {
/* 388 */                     Configuration localConfiguration2 = ((ConfigurationFactory)localObject4).getConfiguration((ConfigurationFactory.ConfigurationSource)localObject2);
/* 389 */                     if (localConfiguration2 != null) {
/* 390 */                       return localConfiguration2;
/*     */                     }
/*     */                   }
/*     */                 }
/*     */               }
/*     */             }
/*     */           }
/*     */         }
/*     */       } else {
/* 399 */         for (localObject1 = ConfigurationFactory.factories.iterator(); ((Iterator)localObject1).hasNext();) { localObject2 = (ConfigurationFactory)((Iterator)localObject1).next();
/* 400 */           localObject3 = ((ConfigurationFactory)localObject2).getSupportedTypes();
/* 401 */           if (localObject3 != null) {
/* 402 */             for (String str1 : localObject3) {
/* 403 */               if ((str1.equals("*")) || (paramURI.toString().endsWith(str1))) {
/* 404 */                 Configuration localConfiguration1 = ((ConfigurationFactory)localObject2).getConfiguration(paramString, paramURI);
/* 405 */                 if (localConfiguration1 != null) {
/* 406 */                   return localConfiguration1;
/*     */                 }
/*     */               }
/*     */             }
/*     */           }
/*     */         }
/*     */       }
/*     */       
/* 414 */       Object localObject1 = getConfiguration(true, paramString);
/* 415 */       if (localObject1 == null) {
/* 416 */         localObject1 = getConfiguration(true, null);
/* 417 */         if (localObject1 == null) {
/* 418 */           localObject1 = getConfiguration(false, paramString);
/* 419 */           if (localObject1 == null) {
/* 420 */             localObject1 = getConfiguration(false, null);
/*     */           }
/*     */         }
/*     */       }
/* 424 */       return (Configuration)(localObject1 != null ? localObject1 : new DefaultConfiguration());
/*     */     }
/*     */     
/*     */     private Configuration getConfiguration(boolean paramBoolean, String paramString) {
/* 428 */       int i = (paramString != null) && (paramString.length() > 0) ? 1 : 0;
/* 429 */       ClassLoader localClassLoader = getClass().getClassLoader();
/* 430 */       for (ConfigurationFactory localConfigurationFactory : ConfigurationFactory.factories)
/*     */       {
/* 432 */         String str1 = paramBoolean ? "log4j2-test" : "log4j2";
/* 433 */         String[] arrayOfString1 = localConfigurationFactory.getSupportedTypes();
/* 434 */         if (arrayOfString1 != null)
/*     */         {
/*     */ 
/*     */ 
/* 438 */           for (String str2 : arrayOfString1)
/* 439 */             if (!str2.equals("*"))
/*     */             {
/*     */ 
/* 442 */               String str3 = str1 + str2;
/*     */               
/* 444 */               ConfigurationFactory.ConfigurationSource localConfigurationSource = getInputFromResource(str3, localClassLoader);
/* 445 */               if (localConfigurationSource != null)
/* 446 */                 return localConfigurationFactory.getConfiguration(localConfigurationSource);
/*     */             }
/*     */         }
/*     */       }
/* 450 */       return null;
/*     */     }
/*     */     
/*     */     public String[] getSupportedTypes()
/*     */     {
/* 455 */       return null;
/*     */     }
/*     */     
/*     */     public Configuration getConfiguration(ConfigurationFactory.ConfigurationSource paramConfigurationSource) {
/*     */       String str1;
/* 460 */       if (paramConfigurationSource != null) {
/* 461 */         str1 = paramConfigurationSource.getLocation();
/* 462 */         for (ConfigurationFactory localConfigurationFactory : ConfigurationFactory.factories) {
/* 463 */           String[] arrayOfString1 = localConfigurationFactory.getSupportedTypes();
/* 464 */           if (arrayOfString1 != null) {
/* 465 */             for (String str2 : arrayOfString1) {
/* 466 */               if ((str2.equals("*")) || ((str1 != null) && (str1.endsWith(str2)))) {
/* 467 */                 Configuration localConfiguration = localConfigurationFactory.getConfiguration(paramConfigurationSource);
/* 468 */                 if (localConfiguration != null) {
/* 469 */                   return localConfiguration;
/*     */                 }
/* 471 */                 LOGGER.error("Cannot determine the ConfigurationFactory to use for {}", new Object[] { str1 });
/* 472 */                 return null;
/*     */               }
/*     */             }
/*     */           }
/*     */         }
/*     */       }
/* 478 */       LOGGER.error("Cannot process configuration, input source is null");
/* 479 */       return null;
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */   public static class ConfigurationSource
/*     */   {
/*     */     private File file;
/*     */     
/*     */     private String location;
/*     */     
/*     */     private InputStream stream;
/*     */     
/*     */ 
/*     */     public ConfigurationSource() {}
/*     */     
/*     */ 
/*     */     public ConfigurationSource(InputStream paramInputStream)
/*     */     {
/* 498 */       this.stream = paramInputStream;
/* 499 */       this.file = null;
/* 500 */       this.location = null;
/*     */     }
/*     */     
/*     */     public ConfigurationSource(InputStream paramInputStream, File paramFile) {
/* 504 */       this.stream = paramInputStream;
/* 505 */       this.file = paramFile;
/* 506 */       this.location = paramFile.getAbsolutePath();
/*     */     }
/*     */     
/*     */     public ConfigurationSource(InputStream paramInputStream, String paramString) {
/* 510 */       this.stream = paramInputStream;
/* 511 */       this.location = paramString;
/* 512 */       this.file = null;
/*     */     }
/*     */     
/*     */     public File getFile() {
/* 516 */       return this.file;
/*     */     }
/*     */     
/*     */     public void setFile(File paramFile) {
/* 520 */       this.file = paramFile;
/*     */     }
/*     */     
/*     */     public String getLocation() {
/* 524 */       return this.location;
/*     */     }
/*     */     
/*     */     public void setLocation(String paramString) {
/* 528 */       this.location = paramString;
/*     */     }
/*     */     
/*     */     public InputStream getInputStream() {
/* 532 */       return this.stream;
/*     */     }
/*     */     
/*     */     public void setInputStream(InputStream paramInputStream) {
/* 536 */       this.stream = paramInputStream;
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\org\apache\logging\log4j\core\config\ConfigurationFactory.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */
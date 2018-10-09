/*     */ package org.apache.logging.log4j.core.helpers;
/*     */ 
/*     */ import java.io.InputStream;
/*     */ import java.net.URL;
/*     */ import java.security.AccessController;
/*     */ import java.security.PrivilegedAction;
/*     */ import org.apache.logging.log4j.Logger;
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
/*     */ public final class Loader
/*     */ {
/*  31 */   private static boolean ignoreTCL = false;
/*     */   
/*  33 */   private static final Logger LOGGER = StatusLogger.getLogger();
/*     */   private static final String TSTR = "Caught Exception while in Loader.getResource. This may be innocuous.";
/*     */   
/*     */   static
/*     */   {
/*  38 */     String str = PropertiesUtil.getProperties().getStringProperty("log4j.ignoreTCL", null);
/*  39 */     if (str != null) {
/*  40 */       ignoreTCL = OptionConverter.toBoolean(str, true);
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public static ClassLoader getClassLoader()
/*     */   {
/*  50 */     return getClassLoader(Loader.class, null);
/*     */   }
/*     */   
/*     */   public static ClassLoader getClassLoader(Class<?> paramClass1, Class<?> paramClass2)
/*     */   {
/*  55 */     ClassLoader localClassLoader1 = null;
/*     */     try {
/*  57 */       localClassLoader1 = getTCL();
/*     */     } catch (Exception localException) {
/*  59 */       LOGGER.warn("Caught exception locating thread ClassLoader {}", new Object[] { localException.getMessage() });
/*     */     }
/*  61 */     ClassLoader localClassLoader2 = paramClass1 == null ? null : paramClass1.getClassLoader();
/*  62 */     ClassLoader localClassLoader3 = paramClass2 == null ? null : paramClass2.getClass().getClassLoader();
/*     */     
/*  64 */     if (isChild(localClassLoader1, localClassLoader2)) {
/*  65 */       return isChild(localClassLoader1, localClassLoader3) ? localClassLoader1 : localClassLoader3;
/*     */     }
/*  67 */     return isChild(localClassLoader2, localClassLoader3) ? localClassLoader2 : localClassLoader3;
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
/*     */   public static URL getResource(String paramString, ClassLoader paramClassLoader)
/*     */   {
/*     */     try
/*     */     {
/*  94 */       ClassLoader localClassLoader = getTCL();
/*  95 */       URL localURL; if (localClassLoader != null) {
/*  96 */         LOGGER.trace("Trying to find [" + paramString + "] using context classloader " + localClassLoader + '.');
/*     */         
/*  98 */         localURL = localClassLoader.getResource(paramString);
/*  99 */         if (localURL != null) {
/* 100 */           return localURL;
/*     */         }
/*     */       }
/*     */       
/*     */ 
/* 105 */       localClassLoader = Loader.class.getClassLoader();
/* 106 */       if (localClassLoader != null) {
/* 107 */         LOGGER.trace("Trying to find [" + paramString + "] using " + localClassLoader + " class loader.");
/* 108 */         localURL = localClassLoader.getResource(paramString);
/* 109 */         if (localURL != null) {
/* 110 */           return localURL;
/*     */         }
/*     */       }
/*     */       
/* 114 */       if (paramClassLoader != null) {
/* 115 */         LOGGER.trace("Trying to find [" + paramString + "] using " + paramClassLoader + " class loader.");
/* 116 */         localURL = paramClassLoader.getResource(paramString);
/* 117 */         if (localURL != null) {
/* 118 */           return localURL;
/*     */         }
/*     */         
/*     */       }
/*     */     }
/*     */     catch (Throwable localThrowable)
/*     */     {
/* 125 */       LOGGER.warn("Caught Exception while in Loader.getResource. This may be innocuous.", localThrowable);
/*     */     }
/*     */     
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/* 132 */     LOGGER.trace("Trying to find [" + paramString + "] using ClassLoader.getSystemResource().");
/* 133 */     return ClassLoader.getSystemResource(paramString);
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
/*     */   public static InputStream getResourceAsStream(String paramString, ClassLoader paramClassLoader)
/*     */   {
/*     */     try
/*     */     {
/* 163 */       ClassLoader localClassLoader = getTCL();
/* 164 */       InputStream localInputStream; if (localClassLoader != null) {
/* 165 */         LOGGER.trace("Trying to find [" + paramString + "] using context classloader " + localClassLoader + '.');
/* 166 */         localInputStream = localClassLoader.getResourceAsStream(paramString);
/* 167 */         if (localInputStream != null) {
/* 168 */           return localInputStream;
/*     */         }
/*     */       }
/*     */       
/*     */ 
/* 173 */       localClassLoader = Loader.class.getClassLoader();
/* 174 */       if (localClassLoader != null) {
/* 175 */         LOGGER.trace("Trying to find [" + paramString + "] using " + localClassLoader + " class loader.");
/* 176 */         localInputStream = localClassLoader.getResourceAsStream(paramString);
/* 177 */         if (localInputStream != null) {
/* 178 */           return localInputStream;
/*     */         }
/*     */       }
/*     */       
/*     */ 
/* 183 */       if (paramClassLoader != null) {
/* 184 */         LOGGER.trace("Trying to find [" + paramString + "] using " + paramClassLoader + " class loader.");
/* 185 */         localInputStream = paramClassLoader.getResourceAsStream(paramString);
/* 186 */         if (localInputStream != null) {
/* 187 */           return localInputStream;
/*     */         }
/*     */         
/*     */       }
/*     */     }
/*     */     catch (Throwable localThrowable)
/*     */     {
/* 194 */       LOGGER.warn("Caught Exception while in Loader.getResource. This may be innocuous.", localThrowable);
/*     */     }
/*     */     
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/* 201 */     LOGGER.trace("Trying to find [" + paramString + "] using ClassLoader.getSystemResource().");
/* 202 */     return ClassLoader.getSystemResourceAsStream(paramString);
/*     */   }
/*     */   
/*     */   private static ClassLoader getTCL() {
/*     */     ClassLoader localClassLoader;
/* 207 */     if (System.getSecurityManager() == null) {
/* 208 */       localClassLoader = Thread.currentThread().getContextClassLoader();
/*     */     } else {
/* 210 */       localClassLoader = (ClassLoader)AccessController.doPrivileged(new PrivilegedAction()
/*     */       {
/*     */         public ClassLoader run()
/*     */         {
/* 214 */           return Thread.currentThread().getContextClassLoader();
/*     */         }
/*     */       });
/*     */     }
/*     */     
/*     */ 
/* 220 */     return localClassLoader;
/*     */   }
/*     */   
/*     */   private static boolean isChild(ClassLoader paramClassLoader1, ClassLoader paramClassLoader2) {
/* 224 */     if ((paramClassLoader1 != null) && (paramClassLoader2 != null)) {
/* 225 */       ClassLoader localClassLoader = paramClassLoader1.getParent();
/* 226 */       while ((localClassLoader != null) && (localClassLoader != paramClassLoader2)) {
/* 227 */         localClassLoader = localClassLoader.getParent();
/*     */       }
/* 229 */       return localClassLoader != null;
/*     */     }
/* 231 */     return paramClassLoader1 != null;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public static Class<?> loadClass(String paramString)
/*     */     throws ClassNotFoundException
/*     */   {
/* 242 */     if (ignoreTCL) {
/* 243 */       return Class.forName(paramString);
/*     */     }
/*     */     try {
/* 246 */       return getTCL().loadClass(paramString);
/*     */     } catch (Throwable localThrowable) {}
/* 248 */     return Class.forName(paramString);
/*     */   }
/*     */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\org\apache\logging\log4j\core\helpers\Loader.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */
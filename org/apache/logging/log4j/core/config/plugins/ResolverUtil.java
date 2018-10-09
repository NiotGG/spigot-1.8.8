/*     */ package org.apache.logging.log4j.core.config.plugins;
/*     */ 
/*     */ import java.io.File;
/*     */ import java.io.FileInputStream;
/*     */ import java.io.FileNotFoundException;
/*     */ import java.io.IOException;
/*     */ import java.lang.annotation.Annotation;
/*     */ import java.net.URI;
/*     */ import java.net.URL;
/*     */ import java.net.URLDecoder;
/*     */ import java.nio.charset.Charset;
/*     */ import java.util.Collection;
/*     */ import java.util.Enumeration;
/*     */ import java.util.HashSet;
/*     */ import java.util.Set;
/*     */ import java.util.jar.JarEntry;
/*     */ import java.util.jar.JarInputStream;
/*     */ import org.apache.logging.log4j.Logger;
/*     */ import org.apache.logging.log4j.core.helpers.Charsets;
/*     */ import org.apache.logging.log4j.core.helpers.Loader;
/*     */ import org.apache.logging.log4j.status.StatusLogger;
/*     */ import org.osgi.framework.Bundle;
/*     */ import org.osgi.framework.FrameworkUtil;
/*     */ import org.osgi.framework.wiring.BundleWiring;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class ResolverUtil
/*     */ {
/*  76 */   private static final Logger LOGGER = ;
/*     */   private static final String VFSZIP = "vfszip";
/*     */   private static final String BUNDLE_RESOURCE = "bundleresource";
/*     */   private final Set<Class<?>> classMatches;
/*     */   private final Set<URI> resourceMatches;
/*     */   private ClassLoader classloader;
/*     */   
/*  83 */   public ResolverUtil() { this.classMatches = new HashSet();
/*     */     
/*     */ 
/*  86 */     this.resourceMatches = new HashSet();
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
/*     */   public Set<Class<?>> getClasses()
/*     */   {
/* 101 */     return this.classMatches;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public Set<URI> getResources()
/*     */   {
/* 109 */     return this.resourceMatches;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public ClassLoader getClassLoader()
/*     */   {
/* 120 */     return this.classloader != null ? this.classloader : (this.classloader = Loader.getClassLoader(ResolverUtil.class, null));
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public void setClassLoader(ClassLoader paramClassLoader)
/*     */   {
/* 129 */     this.classloader = paramClassLoader;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public void findImplementations(Class<?> paramClass, String... paramVarArgs)
/*     */   {
/* 141 */     if (paramVarArgs == null) {
/* 142 */       return;
/*     */     }
/*     */     
/* 145 */     IsA localIsA = new IsA(paramClass);
/* 146 */     for (String str : paramVarArgs) {
/* 147 */       findInPackage(localIsA, str);
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public void findSuffix(String paramString, String... paramVarArgs)
/*     */   {
/* 159 */     if (paramVarArgs == null) {
/* 160 */       return;
/*     */     }
/*     */     
/* 163 */     NameEndsWith localNameEndsWith = new NameEndsWith(paramString);
/* 164 */     for (String str : paramVarArgs) {
/* 165 */       findInPackage(localNameEndsWith, str);
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public void findAnnotated(Class<? extends Annotation> paramClass, String... paramVarArgs)
/*     */   {
/* 177 */     if (paramVarArgs == null) {
/* 178 */       return;
/*     */     }
/*     */     
/* 181 */     AnnotatedWith localAnnotatedWith = new AnnotatedWith(paramClass);
/* 182 */     for (String str : paramVarArgs) {
/* 183 */       findInPackage(localAnnotatedWith, str);
/*     */     }
/*     */   }
/*     */   
/*     */   public void findNamedResource(String paramString, String... paramVarArgs) {
/* 188 */     if (paramVarArgs == null) {
/* 189 */       return;
/*     */     }
/*     */     
/* 192 */     NameIs localNameIs = new NameIs(paramString);
/* 193 */     for (String str : paramVarArgs) {
/* 194 */       findInPackage(localNameIs, str);
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public void find(Test paramTest, String... paramVarArgs)
/*     */   {
/* 206 */     if (paramVarArgs == null) {
/* 207 */       return;
/*     */     }
/*     */     
/* 210 */     for (String str : paramVarArgs) {
/* 211 */       findInPackage(paramTest, str);
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
/*     */   public void findInPackage(Test paramTest, String paramString)
/*     */   {
/* 226 */     paramString = paramString.replace('.', '/');
/* 227 */     ClassLoader localClassLoader = getClassLoader();
/*     */     Enumeration localEnumeration;
/*     */     try
/*     */     {
/* 231 */       localEnumeration = localClassLoader.getResources(paramString);
/*     */     } catch (IOException localIOException1) {
/* 233 */       LOGGER.warn("Could not read package: " + paramString, localIOException1);
/* 234 */       return;
/*     */     }
/*     */     
/* 237 */     while (localEnumeration.hasMoreElements()) {
/*     */       try {
/* 239 */         URL localURL1 = (URL)localEnumeration.nextElement();
/* 240 */         String str = localURL1.getFile();
/* 241 */         str = URLDecoder.decode(str, Charsets.UTF_8.name());
/*     */         
/*     */ 
/* 244 */         if (str.startsWith("file:")) {
/* 245 */           str = str.substring(5);
/*     */         }
/*     */         
/*     */ 
/* 249 */         if (str.indexOf('!') > 0) {
/* 250 */           str = str.substring(0, str.indexOf('!'));
/*     */         }
/*     */         
/* 253 */         LOGGER.info("Scanning for classes in [" + str + "] matching criteria: " + paramTest);
/*     */         Object localObject1;
/* 255 */         if ("vfszip".equals(localURL1.getProtocol())) {
/* 256 */           localObject1 = str.substring(0, str.length() - paramString.length() - 2);
/* 257 */           URL localURL2 = new URL(localURL1.getProtocol(), localURL1.getHost(), (String)localObject1);
/*     */           
/* 259 */           JarInputStream localJarInputStream = new JarInputStream(localURL2.openStream());
/*     */           try {
/* 261 */             loadImplementationsInJar(paramTest, paramString, (String)localObject1, localJarInputStream);
/*     */           } finally {
/* 263 */             close(localJarInputStream, localURL2);
/*     */           }
/* 265 */         } else if ("bundleresource".equals(localURL1.getProtocol())) {
/* 266 */           loadImplementationsInBundle(paramTest, paramString);
/*     */         } else {
/* 268 */           localObject1 = new File(str);
/* 269 */           if (((File)localObject1).isDirectory()) {
/* 270 */             loadImplementationsInDirectory(paramTest, paramString, (File)localObject1);
/*     */           } else {
/* 272 */             loadImplementationsInJar(paramTest, paramString, (File)localObject1);
/*     */           }
/*     */         }
/*     */       } catch (IOException localIOException2) {
/* 276 */         LOGGER.warn("could not read entries", localIOException2);
/*     */       }
/*     */     }
/*     */   }
/*     */   
/*     */   private void loadImplementationsInBundle(Test paramTest, String paramString)
/*     */   {
/* 283 */     BundleWiring localBundleWiring = (BundleWiring)FrameworkUtil.getBundle(ResolverUtil.class).adapt(BundleWiring.class);
/*     */     
/* 285 */     Collection localCollection = localBundleWiring.listResources(paramString, "*.class", 1);
/*     */     
/* 287 */     for (String str : localCollection) {
/* 288 */       addIfMatching(paramTest, str);
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
/*     */   private void loadImplementationsInDirectory(Test paramTest, String paramString, File paramFile)
/*     */   {
/* 306 */     File[] arrayOfFile1 = paramFile.listFiles();
/* 307 */     if (arrayOfFile1 == null) {
/* 308 */       return;
/*     */     }
/*     */     
/*     */ 
/* 312 */     for (File localFile : arrayOfFile1) {
/* 313 */       StringBuilder localStringBuilder = new StringBuilder();
/* 314 */       localStringBuilder.append(paramString).append("/").append(localFile.getName());
/* 315 */       String str = paramString == null ? localFile.getName() : localStringBuilder.toString();
/*     */       
/* 317 */       if (localFile.isDirectory()) {
/* 318 */         loadImplementationsInDirectory(paramTest, str, localFile);
/* 319 */       } else if (isTestApplicable(paramTest, localFile.getName())) {
/* 320 */         addIfMatching(paramTest, str);
/*     */       }
/*     */     }
/*     */   }
/*     */   
/*     */   private boolean isTestApplicable(Test paramTest, String paramString) {
/* 326 */     return (paramTest.doesMatchResource()) || ((paramString.endsWith(".class")) && (paramTest.doesMatchClass()));
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
/*     */   private void loadImplementationsInJar(Test paramTest, String paramString, File paramFile)
/*     */   {
/* 340 */     JarInputStream localJarInputStream = null;
/*     */     try {
/* 342 */       localJarInputStream = new JarInputStream(new FileInputStream(paramFile));
/* 343 */       loadImplementationsInJar(paramTest, paramString, paramFile.getPath(), localJarInputStream);
/*     */     } catch (FileNotFoundException localFileNotFoundException) {
/* 345 */       LOGGER.error("Could not search jar file '" + paramFile + "' for classes matching criteria: " + paramTest + " file not found");
/*     */     }
/*     */     catch (IOException localIOException) {
/* 348 */       LOGGER.error("Could not search jar file '" + paramFile + "' for classes matching criteria: " + paramTest + " due to an IOException", localIOException);
/*     */     }
/*     */     finally {
/* 351 */       close(localJarInputStream, paramFile);
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   private void close(JarInputStream paramJarInputStream, Object paramObject)
/*     */   {
/* 360 */     if (paramJarInputStream != null) {
/*     */       try {
/* 362 */         paramJarInputStream.close();
/*     */       } catch (IOException localIOException) {
/* 364 */         LOGGER.error("Error closing JAR file stream for {}", new Object[] { paramObject, localIOException });
/*     */       }
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   private void loadImplementationsInJar(Test paramTest, String paramString1, String paramString2, JarInputStream paramJarInputStream)
/*     */   {
/*     */     try
/*     */     {
/*     */       JarEntry localJarEntry;
/*     */       
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/* 384 */       while ((localJarEntry = paramJarInputStream.getNextJarEntry()) != null) {
/* 385 */         String str = localJarEntry.getName();
/* 386 */         if ((!localJarEntry.isDirectory()) && (str.startsWith(paramString1)) && (isTestApplicable(paramTest, str))) {
/* 387 */           addIfMatching(paramTest, str);
/*     */         }
/*     */       }
/*     */     } catch (IOException localIOException) {
/* 391 */       LOGGER.error("Could not search jar file '" + paramString2 + "' for classes matching criteria: " + paramTest + " due to an IOException", localIOException);
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   protected void addIfMatching(Test paramTest, String paramString)
/*     */   {
/*     */     try
/*     */     {
/* 405 */       ClassLoader localClassLoader = getClassLoader();
/* 406 */       Object localObject; if (paramTest.doesMatchClass()) {
/* 407 */         localObject = paramString.substring(0, paramString.indexOf('.')).replace('/', '.');
/* 408 */         if (LOGGER.isDebugEnabled()) {
/* 409 */           LOGGER.debug("Checking to see if class " + (String)localObject + " matches criteria [" + paramTest + "]");
/*     */         }
/*     */         
/* 412 */         Class localClass = localClassLoader.loadClass((String)localObject);
/* 413 */         if (paramTest.matches(localClass)) {
/* 414 */           this.classMatches.add(localClass);
/*     */         }
/*     */       }
/* 417 */       if (paramTest.doesMatchResource()) {
/* 418 */         localObject = localClassLoader.getResource(paramString);
/* 419 */         if (localObject == null) {
/* 420 */           localObject = localClassLoader.getResource(paramString.substring(1));
/*     */         }
/* 422 */         if ((localObject != null) && (paramTest.matches(((URL)localObject).toURI()))) {
/* 423 */           this.resourceMatches.add(((URL)localObject).toURI());
/*     */         }
/*     */       }
/*     */     } catch (Throwable localThrowable) {
/* 427 */       LOGGER.warn("Could not examine class '" + paramString + "' due to a " + localThrowable.getClass().getName() + " with message: " + localThrowable.getMessage());
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public static abstract interface Test
/*     */   {
/*     */     public abstract boolean matches(Class<?> paramClass);
/*     */     
/*     */ 
/*     */ 
/*     */ 
/*     */     public abstract boolean matches(URI paramURI);
/*     */     
/*     */ 
/*     */ 
/*     */ 
/*     */     public abstract boolean doesMatchClass();
/*     */     
/*     */ 
/*     */ 
/*     */ 
/*     */     public abstract boolean doesMatchResource();
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public static abstract class ClassTest
/*     */     implements ResolverUtil.Test
/*     */   {
/*     */     public boolean matches(URI paramURI)
/*     */     {
/* 463 */       throw new UnsupportedOperationException();
/*     */     }
/*     */     
/*     */     public boolean doesMatchClass()
/*     */     {
/* 468 */       return true;
/*     */     }
/*     */     
/*     */     public boolean doesMatchResource()
/*     */     {
/* 473 */       return false;
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */   public static abstract class ResourceTest
/*     */     implements ResolverUtil.Test
/*     */   {
/*     */     public boolean matches(Class<?> paramClass)
/*     */     {
/* 483 */       throw new UnsupportedOperationException();
/*     */     }
/*     */     
/*     */     public boolean doesMatchClass()
/*     */     {
/* 488 */       return false;
/*     */     }
/*     */     
/*     */     public boolean doesMatchResource()
/*     */     {
/* 493 */       return true;
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public static class IsA
/*     */     extends ResolverUtil.ClassTest
/*     */   {
/*     */     private final Class<?> parent;
/*     */     
/*     */ 
/*     */ 
/*     */     public IsA(Class<?> paramClass)
/*     */     {
/* 508 */       this.parent = paramClass;
/*     */     }
/*     */     
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     public boolean matches(Class<?> paramClass)
/*     */     {
/* 517 */       return (paramClass != null) && (this.parent.isAssignableFrom(paramClass));
/*     */     }
/*     */     
/*     */     public String toString()
/*     */     {
/* 522 */       return "is assignable to " + this.parent.getSimpleName();
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public static class NameEndsWith
/*     */     extends ResolverUtil.ClassTest
/*     */   {
/*     */     private final String suffix;
/*     */     
/*     */ 
/*     */     public NameEndsWith(String paramString)
/*     */     {
/* 536 */       this.suffix = paramString;
/*     */     }
/*     */     
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     public boolean matches(Class<?> paramClass)
/*     */     {
/* 545 */       return (paramClass != null) && (paramClass.getName().endsWith(this.suffix));
/*     */     }
/*     */     
/*     */     public String toString()
/*     */     {
/* 550 */       return "ends with the suffix " + this.suffix;
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public static class AnnotatedWith
/*     */     extends ResolverUtil.ClassTest
/*     */   {
/*     */     private final Class<? extends Annotation> annotation;
/*     */     
/*     */ 
/*     */ 
/*     */     public AnnotatedWith(Class<? extends Annotation> paramClass)
/*     */     {
/* 566 */       this.annotation = paramClass;
/*     */     }
/*     */     
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     public boolean matches(Class<?> paramClass)
/*     */     {
/* 576 */       return (paramClass != null) && (paramClass.isAnnotationPresent(this.annotation));
/*     */     }
/*     */     
/*     */     public String toString()
/*     */     {
/* 581 */       return "annotated with @" + this.annotation.getSimpleName();
/*     */     }
/*     */   }
/*     */   
/*     */   public static class NameIs extends ResolverUtil.ResourceTest
/*     */   {
/*     */     private final String name;
/*     */     
/*     */     public NameIs(String paramString)
/*     */     {
/* 591 */       this.name = ("/" + paramString);
/*     */     }
/*     */     
/*     */     public boolean matches(URI paramURI) {
/* 595 */       return paramURI.getPath().endsWith(this.name);
/*     */     }
/*     */     
/*     */     public String toString() {
/* 599 */       return "named " + this.name;
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\org\apache\logging\log4j\core\config\plugins\ResolverUtil.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */
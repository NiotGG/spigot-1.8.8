/*     */ package org.apache.logging.log4j.core.impl;
/*     */ 
/*     */ import java.io.Serializable;
/*     */ import java.lang.reflect.Method;
/*     */ import java.net.URL;
/*     */ import java.security.CodeSource;
/*     */ import java.security.ProtectionDomain;
/*     */ import java.util.HashMap;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import java.util.Stack;
/*     */ import org.apache.logging.log4j.Logger;
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
/*     */ public class ThrowableProxy
/*     */   implements Serializable
/*     */ {
/*     */   private static final long serialVersionUID = -2752771578252251910L;
/*  38 */   private static final Logger LOGGER = ;
/*     */   
/*     */   private static final PrivateSecurityManager SECURITY_MANAGER;
/*     */   
/*     */   private static final Method GET_SUPPRESSED;
/*     */   
/*     */   private static final Method ADD_SUPPRESSED;
/*     */   
/*     */   private final ThrowableProxy proxyCause;
/*     */   
/*     */   private final Throwable throwable;
/*     */   
/*     */   private final String name;
/*     */   
/*     */   private final StackTracePackageElement[] callerPackageData;
/*     */   private int commonElementCount;
/*     */   
/*     */   static
/*     */   {
/*  57 */     if (ReflectiveCallerClassUtility.isSupported()) {
/*  58 */       SECURITY_MANAGER = null;
/*     */     }
/*     */     else {
/*     */       try {
/*  62 */         localObject1 = new PrivateSecurityManager(null);
/*  63 */         if (((PrivateSecurityManager)localObject1).getClasses() == null)
/*     */         {
/*  65 */           localObject1 = null;
/*  66 */           LOGGER.error("Unable to obtain call stack from security manager.");
/*     */         }
/*     */       } catch (Exception localException) {
/*  69 */         localObject1 = null;
/*  70 */         LOGGER.debug("Unable to install security manager.", localException);
/*     */       }
/*  72 */       SECURITY_MANAGER = (PrivateSecurityManager)localObject1;
/*     */     }
/*     */     
/*  75 */     Object localObject1 = null;Object localObject2 = null;
/*  76 */     Method[] arrayOfMethod1 = Throwable.class.getMethods();
/*  77 */     for (Method localMethod : arrayOfMethod1) {
/*  78 */       if (localMethod.getName().equals("getSuppressed")) {
/*  79 */         localObject1 = localMethod;
/*  80 */       } else if (localMethod.getName().equals("addSuppressed")) {
/*  81 */         localObject2 = localMethod;
/*     */       }
/*     */     }
/*  84 */     GET_SUPPRESSED = (Method)localObject1;
/*  85 */     ADD_SUPPRESSED = (Method)localObject2;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public ThrowableProxy(Throwable paramThrowable)
/*     */   {
/*  93 */     this.throwable = paramThrowable;
/*  94 */     this.name = paramThrowable.getClass().getName();
/*  95 */     HashMap localHashMap = new HashMap();
/*  96 */     Stack localStack = getCurrentStack();
/*  97 */     this.callerPackageData = resolvePackageData(localStack, localHashMap, null, paramThrowable.getStackTrace());
/*  98 */     this.proxyCause = (paramThrowable.getCause() == null ? null : new ThrowableProxy(paramThrowable, localStack, localHashMap, paramThrowable.getCause()));
/*     */     
/* 100 */     setSuppressed(paramThrowable);
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
/*     */   private ThrowableProxy(Throwable paramThrowable1, Stack<Class<?>> paramStack, Map<String, CacheEntry> paramMap, Throwable paramThrowable2)
/*     */   {
/* 113 */     this.throwable = paramThrowable2;
/* 114 */     this.name = paramThrowable2.getClass().getName();
/* 115 */     this.callerPackageData = resolvePackageData(paramStack, paramMap, paramThrowable1.getStackTrace(), paramThrowable2.getStackTrace());
/* 116 */     this.proxyCause = (paramThrowable2.getCause() == null ? null : new ThrowableProxy(paramThrowable1, paramStack, paramMap, paramThrowable2.getCause()));
/*     */     
/* 118 */     setSuppressed(paramThrowable2);
/*     */   }
/*     */   
/*     */   public Throwable getThrowable() {
/* 122 */     return this.throwable;
/*     */   }
/*     */   
/*     */   public ThrowableProxy getCause() {
/* 126 */     return this.proxyCause;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public String getName()
/*     */   {
/* 134 */     return this.name;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public int getCommonElementCount()
/*     */   {
/* 143 */     return this.commonElementCount;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public StackTracePackageElement[] getPackageData()
/*     */   {
/* 151 */     return this.callerPackageData;
/*     */   }
/*     */   
/*     */   public String toString()
/*     */   {
/* 156 */     String str = this.throwable.getMessage();
/* 157 */     return str != null ? this.name + ": " + str : this.name;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public String getRootCauseStackTrace()
/*     */   {
/* 165 */     return getRootCauseStackTrace(null);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public String getRootCauseStackTrace(List<String> paramList)
/*     */   {
/* 174 */     StringBuilder localStringBuilder = new StringBuilder();
/* 175 */     if (this.proxyCause != null) {
/* 176 */       formatWrapper(localStringBuilder, this.proxyCause);
/* 177 */       localStringBuilder.append("Wrapped by: ");
/*     */     }
/* 179 */     localStringBuilder.append(toString());
/* 180 */     localStringBuilder.append("\n");
/* 181 */     formatElements(localStringBuilder, 0, this.throwable.getStackTrace(), this.callerPackageData, paramList);
/* 182 */     return localStringBuilder.toString();
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public void formatWrapper(StringBuilder paramStringBuilder, ThrowableProxy paramThrowableProxy)
/*     */   {
/* 191 */     formatWrapper(paramStringBuilder, paramThrowableProxy, null);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public void formatWrapper(StringBuilder paramStringBuilder, ThrowableProxy paramThrowableProxy, List<String> paramList)
/*     */   {
/* 202 */     Object localObject = paramThrowableProxy.getCause() != null ? paramThrowableProxy.getCause().getThrowable() : null;
/* 203 */     if (localObject != null) {
/* 204 */       formatWrapper(paramStringBuilder, paramThrowableProxy.proxyCause);
/* 205 */       paramStringBuilder.append("Wrapped by: ");
/*     */     }
/* 207 */     paramStringBuilder.append(paramThrowableProxy).append("\n");
/* 208 */     formatElements(paramStringBuilder, paramThrowableProxy.commonElementCount, paramThrowableProxy.getThrowable().getStackTrace(), paramThrowableProxy.callerPackageData, paramList);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public String getExtendedStackTrace()
/*     */   {
/* 217 */     return getExtendedStackTrace(null);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public String getExtendedStackTrace(List<String> paramList)
/*     */   {
/* 226 */     StringBuilder localStringBuilder = new StringBuilder(this.name);
/* 227 */     String str = this.throwable.getMessage();
/* 228 */     if (str != null) {
/* 229 */       localStringBuilder.append(": ").append(this.throwable.getMessage());
/*     */     }
/* 231 */     localStringBuilder.append("\n");
/* 232 */     formatElements(localStringBuilder, 0, this.throwable.getStackTrace(), this.callerPackageData, paramList);
/* 233 */     if (this.proxyCause != null) {
/* 234 */       formatCause(localStringBuilder, this.proxyCause, paramList);
/*     */     }
/* 236 */     return localStringBuilder.toString();
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public String getSuppressedStackTrace()
/*     */   {
/* 244 */     ThrowableProxy[] arrayOfThrowableProxy1 = getSuppressed();
/* 245 */     if ((arrayOfThrowableProxy1 == null) || (arrayOfThrowableProxy1.length == 0)) {
/* 246 */       return "";
/*     */     }
/* 248 */     StringBuilder localStringBuilder = new StringBuilder("Suppressed Stack Trace Elements:\n");
/* 249 */     for (ThrowableProxy localThrowableProxy : arrayOfThrowableProxy1) {
/* 250 */       localStringBuilder.append(localThrowableProxy.getExtendedStackTrace());
/*     */     }
/* 252 */     return localStringBuilder.toString();
/*     */   }
/*     */   
/*     */   private void formatCause(StringBuilder paramStringBuilder, ThrowableProxy paramThrowableProxy, List<String> paramList)
/*     */   {
/* 257 */     paramStringBuilder.append("Caused by: ").append(paramThrowableProxy).append("\n");
/* 258 */     formatElements(paramStringBuilder, paramThrowableProxy.commonElementCount, paramThrowableProxy.getThrowable().getStackTrace(), paramThrowableProxy.callerPackageData, paramList);
/*     */     
/* 260 */     if (paramThrowableProxy.getCause() != null) {
/* 261 */       formatCause(paramStringBuilder, paramThrowableProxy.proxyCause, paramList);
/*     */     }
/*     */   }
/*     */   
/*     */   private void formatElements(StringBuilder paramStringBuilder, int paramInt, StackTraceElement[] paramArrayOfStackTraceElement, StackTracePackageElement[] paramArrayOfStackTracePackageElement, List<String> paramList) {
/*     */     int i;
/* 267 */     if ((paramList == null) || (paramList.size() == 0)) {
/* 268 */       for (i = 0; i < paramArrayOfStackTracePackageElement.length; i++) {
/* 269 */         formatEntry(paramArrayOfStackTraceElement[i], paramArrayOfStackTracePackageElement[i], paramStringBuilder);
/*     */       }
/*     */     } else {
/* 272 */       i = 0;
/* 273 */       for (int j = 0; j < paramArrayOfStackTracePackageElement.length; j++) {
/* 274 */         if (!isSuppressed(paramArrayOfStackTraceElement[j], paramList)) {
/* 275 */           if (i > 0) {
/* 276 */             if (i == 1) {
/* 277 */               paramStringBuilder.append("\t....\n");
/*     */             } else {
/* 279 */               paramStringBuilder.append("\t... suppressed ").append(i).append(" lines\n");
/*     */             }
/* 281 */             i = 0;
/*     */           }
/* 283 */           formatEntry(paramArrayOfStackTraceElement[j], paramArrayOfStackTracePackageElement[j], paramStringBuilder);
/*     */         } else {
/* 285 */           i++;
/*     */         }
/*     */       }
/* 288 */       if (i > 0) {
/* 289 */         if (i == 1) {
/* 290 */           paramStringBuilder.append("\t...\n");
/*     */         } else {
/* 292 */           paramStringBuilder.append("\t... suppressed ").append(i).append(" lines\n");
/*     */         }
/*     */       }
/*     */     }
/* 296 */     if (paramInt != 0) {
/* 297 */       paramStringBuilder.append("\t... ").append(paramInt).append(" more").append("\n");
/*     */     }
/*     */   }
/*     */   
/*     */   private void formatEntry(StackTraceElement paramStackTraceElement, StackTracePackageElement paramStackTracePackageElement, StringBuilder paramStringBuilder)
/*     */   {
/* 303 */     paramStringBuilder.append("\tat ");
/* 304 */     paramStringBuilder.append(paramStackTraceElement);
/* 305 */     paramStringBuilder.append(" ");
/* 306 */     paramStringBuilder.append(paramStackTracePackageElement);
/* 307 */     paramStringBuilder.append("\n");
/*     */   }
/*     */   
/*     */   private boolean isSuppressed(StackTraceElement paramStackTraceElement, List<String> paramList) {
/* 311 */     String str1 = paramStackTraceElement.getClassName();
/* 312 */     for (String str2 : paramList) {
/* 313 */       if (str1.startsWith(str2)) {
/* 314 */         return true;
/*     */       }
/*     */     }
/* 317 */     return false;
/*     */   }
/*     */   
/*     */ 
/*     */   private Stack<Class<?>> getCurrentStack()
/*     */   {
/*     */     Object localObject1;
/*     */     
/*     */     Object localObject2;
/*     */     
/* 327 */     if (ReflectiveCallerClassUtility.isSupported()) {
/* 328 */       localObject1 = new Stack();
/* 329 */       int i = 1;
/* 330 */       for (localObject2 = ReflectiveCallerClassUtility.getCaller(i); 
/* 331 */           localObject2 != null; 
/*     */           
/* 333 */           localObject2 = ReflectiveCallerClassUtility.getCaller(i))
/*     */       {
/* 332 */         ((Stack)localObject1).push(localObject2);
/* 333 */         i++;
/*     */       }
/* 335 */       return (Stack<Class<?>>)localObject1; }
/* 336 */     if (SECURITY_MANAGER != null) {
/* 337 */       localObject1 = SECURITY_MANAGER.getClasses();
/* 338 */       Stack localStack = new Stack();
/* 339 */       for (Object localObject3 : localObject1) {
/* 340 */         localStack.push(localObject3);
/*     */       }
/* 342 */       return localStack;
/*     */     }
/* 344 */     return new Stack();
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   StackTracePackageElement[] resolvePackageData(Stack<Class<?>> paramStack, Map<String, CacheEntry> paramMap, StackTraceElement[] paramArrayOfStackTraceElement1, StackTraceElement[] paramArrayOfStackTraceElement2)
/*     */   {
/*     */     int k;
/*     */     
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/* 360 */     if (paramArrayOfStackTraceElement1 != null) {
/* 361 */       int i = paramArrayOfStackTraceElement1.length - 1;
/* 362 */       int j = paramArrayOfStackTraceElement2.length - 1;
/* 363 */       while ((i >= 0) && (j >= 0) && (paramArrayOfStackTraceElement1[i].equals(paramArrayOfStackTraceElement2[j]))) {
/* 364 */         i--;
/* 365 */         j--;
/*     */       }
/* 367 */       this.commonElementCount = (paramArrayOfStackTraceElement2.length - 1 - j);
/* 368 */       k = j + 1;
/*     */     } else {
/* 370 */       this.commonElementCount = 0;
/* 371 */       k = paramArrayOfStackTraceElement2.length;
/*     */     }
/* 373 */     StackTracePackageElement[] arrayOfStackTracePackageElement = new StackTracePackageElement[k];
/* 374 */     Class localClass = paramStack.isEmpty() ? null : (Class)paramStack.peek();
/* 375 */     ClassLoader localClassLoader = null;
/* 376 */     for (int m = k - 1; m >= 0; m--) {
/* 377 */       String str = paramArrayOfStackTraceElement2[m].getClassName();
/*     */       
/*     */       CacheEntry localCacheEntry;
/*     */       
/* 381 */       if ((localClass != null) && (str.equals(localClass.getName()))) {
/* 382 */         localCacheEntry = resolvePackageElement(localClass, true);
/* 383 */         arrayOfStackTracePackageElement[m] = localCacheEntry.element;
/* 384 */         localClassLoader = localCacheEntry.loader;
/* 385 */         paramStack.pop();
/* 386 */         localClass = paramStack.isEmpty() ? null : (Class)paramStack.peek();
/*     */       }
/* 388 */       else if (paramMap.containsKey(str)) {
/* 389 */         localCacheEntry = (CacheEntry)paramMap.get(str);
/* 390 */         arrayOfStackTracePackageElement[m] = localCacheEntry.element;
/* 391 */         if (localCacheEntry.loader != null) {
/* 392 */           localClassLoader = localCacheEntry.loader;
/*     */         }
/*     */       } else {
/* 395 */         localCacheEntry = resolvePackageElement(loadClass(localClassLoader, str), false);
/* 396 */         arrayOfStackTracePackageElement[m] = localCacheEntry.element;
/* 397 */         paramMap.put(str, localCacheEntry);
/* 398 */         if (localCacheEntry.loader != null) {
/* 399 */           localClassLoader = localCacheEntry.loader;
/*     */         }
/*     */       }
/*     */     }
/*     */     
/* 404 */     return arrayOfStackTracePackageElement;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   private CacheEntry resolvePackageElement(Class<?> paramClass, boolean paramBoolean)
/*     */   {
/* 415 */     String str1 = "?";
/* 416 */     Object localObject1 = "?";
/* 417 */     ClassLoader localClassLoader = null;
/* 418 */     if (paramClass != null) {
/*     */       Object localObject2;
/* 420 */       try { CodeSource localCodeSource = paramClass.getProtectionDomain().getCodeSource();
/* 421 */         if (localCodeSource != null) {
/* 422 */           localObject2 = localCodeSource.getLocation();
/* 423 */           if (localObject2 != null) {
/* 424 */             String str2 = ((URL)localObject2).toString().replace('\\', '/');
/* 425 */             int i = str2.lastIndexOf("/");
/* 426 */             if ((i >= 0) && (i == str2.length() - 1)) {
/* 427 */               i = str2.lastIndexOf("/", i - 1);
/* 428 */               str1 = str2.substring(i + 1);
/*     */             } else {
/* 430 */               str1 = str2.substring(i + 1);
/*     */             }
/*     */           }
/*     */         }
/*     */       }
/*     */       catch (Exception localException) {}
/*     */       
/* 437 */       Package localPackage = paramClass.getPackage();
/* 438 */       if (localPackage != null) {
/* 439 */         localObject2 = localPackage.getImplementationVersion();
/* 440 */         if (localObject2 != null) {
/* 441 */           localObject1 = localObject2;
/*     */         }
/*     */       }
/* 444 */       localClassLoader = paramClass.getClassLoader();
/*     */     }
/* 446 */     return new CacheEntry(new StackTracePackageElement(str1, (String)localObject1, paramBoolean), localClassLoader);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   private Class<?> loadClass(ClassLoader paramClassLoader, String paramString)
/*     */   {
/*     */     Class localClass;
/*     */     
/*     */ 
/* 457 */     if (paramClassLoader != null) {
/*     */       try {
/* 459 */         localClass = paramClassLoader.loadClass(paramString);
/* 460 */         if (localClass != null) {
/* 461 */           return localClass;
/*     */         }
/*     */       }
/*     */       catch (Exception localException) {}
/*     */     }
/*     */     try
/*     */     {
/* 468 */       localClass = Thread.currentThread().getContextClassLoader().loadClass(paramString);
/*     */     } catch (ClassNotFoundException localClassNotFoundException1) {
/*     */       try {
/* 471 */         localClass = Class.forName(paramString);
/*     */       } catch (ClassNotFoundException localClassNotFoundException2) {
/*     */         try {
/* 474 */           localClass = getClass().getClassLoader().loadClass(paramString);
/*     */         } catch (ClassNotFoundException localClassNotFoundException3) {
/* 476 */           return null;
/*     */         }
/*     */       }
/*     */     }
/* 480 */     return localClass;
/*     */   }
/*     */   
/*     */   public ThrowableProxy[] getSuppressed() {
/* 484 */     if (GET_SUPPRESSED != null) {
/*     */       try {
/* 486 */         return (ThrowableProxy[])GET_SUPPRESSED.invoke(this.throwable, new Object[0]);
/*     */       } catch (Exception localException) {
/* 488 */         return null;
/*     */       }
/*     */     }
/* 491 */     return null;
/*     */   }
/*     */   
/*     */   private void setSuppressed(Throwable paramThrowable) {
/* 495 */     if ((GET_SUPPRESSED != null) && (ADD_SUPPRESSED != null)) {
/*     */       try {
/* 497 */         Throwable[] arrayOfThrowable1 = (Throwable[])GET_SUPPRESSED.invoke(paramThrowable, new Object[0]);
/* 498 */         for (Throwable localThrowable : arrayOfThrowable1) {
/* 499 */           ADD_SUPPRESSED.invoke(this, new Object[] { new ThrowableProxy(localThrowable) });
/*     */         }
/*     */       }
/*     */       catch (Exception localException) {}
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */   class CacheEntry
/*     */   {
/*     */     private final StackTracePackageElement element;
/*     */     
/*     */     private final ClassLoader loader;
/*     */     
/*     */     public CacheEntry(StackTracePackageElement paramStackTracePackageElement, ClassLoader paramClassLoader)
/*     */     {
/* 515 */       this.element = paramStackTracePackageElement;
/* 516 */       this.loader = paramClassLoader;
/*     */     }
/*     */   }
/*     */   
/*     */   private static class PrivateSecurityManager
/*     */     extends SecurityManager
/*     */   {
/*     */     public Class<?>[] getClasses()
/*     */     {
/* 525 */       return getClassContext();
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\org\apache\logging\log4j\core\impl\ThrowableProxy.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */
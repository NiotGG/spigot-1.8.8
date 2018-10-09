/*    */ package io.netty.handler.codec.serialization;
/*    */ 
/*    */ import io.netty.util.internal.PlatformDependent;
/*    */ import java.util.HashMap;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public final class ClassResolvers
/*    */ {
/*    */   public static ClassResolver cacheDisabled(ClassLoader paramClassLoader)
/*    */   {
/* 31 */     return new ClassLoaderClassResolver(defaultClassLoader(paramClassLoader));
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   public static ClassResolver weakCachingResolver(ClassLoader paramClassLoader)
/*    */   {
/* 42 */     return new CachingClassResolver(new ClassLoaderClassResolver(defaultClassLoader(paramClassLoader)), new WeakReferenceMap(new HashMap()));
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   public static ClassResolver softCachingResolver(ClassLoader paramClassLoader)
/*    */   {
/* 55 */     return new CachingClassResolver(new ClassLoaderClassResolver(defaultClassLoader(paramClassLoader)), new SoftReferenceMap(new HashMap()));
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   public static ClassResolver weakCachingConcurrentResolver(ClassLoader paramClassLoader)
/*    */   {
/* 68 */     return new CachingClassResolver(new ClassLoaderClassResolver(defaultClassLoader(paramClassLoader)), new WeakReferenceMap(PlatformDependent.newConcurrentHashMap()));
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   public static ClassResolver softCachingConcurrentResolver(ClassLoader paramClassLoader)
/*    */   {
/* 82 */     return new CachingClassResolver(new ClassLoaderClassResolver(defaultClassLoader(paramClassLoader)), new SoftReferenceMap(PlatformDependent.newConcurrentHashMap()));
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */   static ClassLoader defaultClassLoader(ClassLoader paramClassLoader)
/*    */   {
/* 89 */     if (paramClassLoader != null) {
/* 90 */       return paramClassLoader;
/*    */     }
/*    */     
/* 93 */     ClassLoader localClassLoader = PlatformDependent.getContextClassLoader();
/* 94 */     if (localClassLoader != null) {
/* 95 */       return localClassLoader;
/*    */     }
/*    */     
/* 98 */     return PlatformDependent.getClassLoader(ClassResolvers.class);
/*    */   }
/*    */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\io\netty\handler\codec\serialization\ClassResolvers.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */
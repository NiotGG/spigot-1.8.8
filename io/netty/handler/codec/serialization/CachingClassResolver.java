/*    */ package io.netty.handler.codec.serialization;
/*    */ 
/*    */ import java.util.Map;
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
/*    */ class CachingClassResolver
/*    */   implements ClassResolver
/*    */ {
/*    */   private final Map<String, Class<?>> classCache;
/*    */   private final ClassResolver delegate;
/*    */   
/*    */   CachingClassResolver(ClassResolver paramClassResolver, Map<String, Class<?>> paramMap)
/*    */   {
/* 26 */     this.delegate = paramClassResolver;
/* 27 */     this.classCache = paramMap;
/*    */   }
/*    */   
/*    */ 
/*    */   public Class<?> resolve(String paramString)
/*    */     throws ClassNotFoundException
/*    */   {
/* 34 */     Class localClass = (Class)this.classCache.get(paramString);
/* 35 */     if (localClass != null) {
/* 36 */       return localClass;
/*    */     }
/*    */     
/*    */ 
/* 40 */     localClass = this.delegate.resolve(paramString);
/*    */     
/* 42 */     this.classCache.put(paramString, localClass);
/* 43 */     return localClass;
/*    */   }
/*    */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\io\netty\handler\codec\serialization\CachingClassResolver.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */
/*    */ package io.netty.handler.codec.serialization;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ class ClassLoaderClassResolver
/*    */   implements ClassResolver
/*    */ {
/*    */   private final ClassLoader classLoader;
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   ClassLoaderClassResolver(ClassLoader paramClassLoader)
/*    */   {
/* 23 */     this.classLoader = paramClassLoader;
/*    */   }
/*    */   
/*    */   public Class<?> resolve(String paramString) throws ClassNotFoundException
/*    */   {
/*    */     try {
/* 29 */       return this.classLoader.loadClass(paramString);
/*    */     } catch (ClassNotFoundException localClassNotFoundException) {}
/* 31 */     return Class.forName(paramString, false, this.classLoader);
/*    */   }
/*    */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\io\netty\handler\codec\serialization\ClassLoaderClassResolver.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */
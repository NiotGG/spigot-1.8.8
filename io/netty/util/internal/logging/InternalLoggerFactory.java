/*    */ package io.netty.util.internal.logging;
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
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public abstract class InternalLoggerFactory
/*    */ {
/* 34 */   private static volatile InternalLoggerFactory defaultFactory = newDefaultFactory(InternalLoggerFactory.class.getName());
/*    */   
/*    */   private static InternalLoggerFactory newDefaultFactory(String paramString)
/*    */   {
/*    */     Object localObject;
/*    */     try
/*    */     {
/* 41 */       localObject = new Slf4JLoggerFactory(true);
/* 42 */       ((InternalLoggerFactory)localObject).newInstance(paramString).debug("Using SLF4J as the default logging framework");
/*    */     } catch (Throwable localThrowable1) {
/*    */       try {
/* 45 */         localObject = new Log4JLoggerFactory();
/* 46 */         ((InternalLoggerFactory)localObject).newInstance(paramString).debug("Using Log4J as the default logging framework");
/*    */       } catch (Throwable localThrowable2) {
/* 48 */         localObject = new JdkLoggerFactory();
/* 49 */         ((InternalLoggerFactory)localObject).newInstance(paramString).debug("Using java.util.logging as the default logging framework");
/*    */       }
/*    */     }
/* 52 */     return (InternalLoggerFactory)localObject;
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */   public static InternalLoggerFactory getDefaultFactory()
/*    */   {
/* 60 */     return defaultFactory;
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */   public static void setDefaultFactory(InternalLoggerFactory paramInternalLoggerFactory)
/*    */   {
/* 67 */     if (paramInternalLoggerFactory == null) {
/* 68 */       throw new NullPointerException("defaultFactory");
/*    */     }
/* 70 */     defaultFactory = paramInternalLoggerFactory;
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */   public static InternalLogger getInstance(Class<?> paramClass)
/*    */   {
/* 77 */     return getInstance(paramClass.getName());
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */   public static InternalLogger getInstance(String paramString)
/*    */   {
/* 84 */     return getDefaultFactory().newInstance(paramString);
/*    */   }
/*    */   
/*    */   protected abstract InternalLogger newInstance(String paramString);
/*    */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\io\netty\util\internal\logging\InternalLoggerFactory.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */
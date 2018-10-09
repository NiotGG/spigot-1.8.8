/*    */ package io.netty.handler.ssl;
/*    */ 
/*    */ import io.netty.util.internal.NativeLibraryLoader;
/*    */ import io.netty.util.internal.logging.InternalLogger;
/*    */ import io.netty.util.internal.logging.InternalLoggerFactory;
/*    */ import org.apache.tomcat.jni.Library;
/*    */ import org.apache.tomcat.jni.SSL;
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
/*    */ public final class OpenSsl
/*    */ {
/* 31 */   private static final InternalLogger logger = InternalLoggerFactory.getInstance(OpenSsl.class);
/*    */   private static final Throwable UNAVAILABILITY_CAUSE;
/*    */   static final String IGNORABLE_ERROR_PREFIX = "error:00000000:";
/*    */   
/*    */   static
/*    */   {
/* 37 */     Object localObject = null;
/*    */     try {
/* 39 */       NativeLibraryLoader.load("netty-tcnative", SSL.class.getClassLoader());
/* 40 */       Library.initialize("provided");
/* 41 */       SSL.initialize(null);
/*    */     } catch (Throwable localThrowable) {
/* 43 */       localObject = localThrowable;
/* 44 */       logger.debug("Failed to load netty-tcnative; " + OpenSslEngine.class.getSimpleName() + " will be unavailable.", localThrowable);
/*    */     }
/*    */     
/*    */ 
/* 48 */     UNAVAILABILITY_CAUSE = (Throwable)localObject;
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   public static boolean isAvailable()
/*    */   {
/* 57 */     return UNAVAILABILITY_CAUSE == null;
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   public static void ensureAvailability()
/*    */   {
/* 67 */     if (UNAVAILABILITY_CAUSE != null) {
/* 68 */       throw ((Error)new UnsatisfiedLinkError("failed to load the required native library").initCause(UNAVAILABILITY_CAUSE));
/*    */     }
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   public static Throwable unavailabilityCause()
/*    */   {
/* 80 */     return UNAVAILABILITY_CAUSE;
/*    */   }
/*    */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\io\netty\handler\ssl\OpenSsl.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */
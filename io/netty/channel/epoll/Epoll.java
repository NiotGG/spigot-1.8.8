/*    */ package io.netty.channel.epoll;
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
/*    */ public final class Epoll
/*    */ {
/*    */   private static final Throwable UNAVAILABILITY_CAUSE;
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   static
/*    */   {
/* 26 */     Object localObject1 = null;
/* 27 */     int i = -1;
/* 28 */     int j = -1;
/*    */     try {
/* 30 */       i = Native.epollCreate();
/* 31 */       j = Native.eventFd();
/*    */       
/*    */ 
/*    */ 
/* 35 */       if (i != -1) {
/*    */         try {
/* 37 */           Native.close(i);
/*    */         }
/*    */         catch (Exception localException1) {}
/*    */       }
/*    */       
/* 42 */       if (j != -1) {
/*    */         try {
/* 44 */           Native.close(j);
/*    */         }
/*    */         catch (Exception localException2) {}
/*    */       }
/*    */       
/*    */ 
/*    */ 
/* 51 */       if (localObject1 == null) {
/*    */         break label119;
/*    */       }
/*    */     }
/*    */     catch (Throwable localThrowable)
/*    */     {
/* 33 */       localObject1 = localThrowable;
/*    */     } finally {
/* 35 */       if (i != -1) {
/*    */         try {
/* 37 */           Native.close(i);
/*    */         }
/*    */         catch (Exception localException5) {}
/*    */       }
/*    */       
/* 42 */       if (j != -1) {
/*    */         try {
/* 44 */           Native.close(j);
/*    */         }
/*    */         catch (Exception localException6) {}
/*    */       }
/*    */     }
/*    */     
/*    */ 
/*    */ 
/* 52 */     UNAVAILABILITY_CAUSE = (Throwable)localObject1; return;
/*    */     label119:
/* 54 */     UNAVAILABILITY_CAUSE = null;
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   public static boolean isAvailable()
/*    */   {
/* 63 */     return UNAVAILABILITY_CAUSE == null;
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   public static void ensureAvailability()
/*    */   {
/* 73 */     if (UNAVAILABILITY_CAUSE != null) {
/* 74 */       throw ((Error)new UnsatisfiedLinkError("failed to load the required native library").initCause(UNAVAILABILITY_CAUSE));
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
/* 86 */     return UNAVAILABILITY_CAUSE;
/*    */   }
/*    */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\io\netty\channel\epoll\Epoll.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */
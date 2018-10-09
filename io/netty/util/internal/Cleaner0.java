/*    */ package io.netty.util.internal;
/*    */ 
/*    */ import io.netty.util.internal.logging.InternalLogger;
/*    */ import io.netty.util.internal.logging.InternalLoggerFactory;
/*    */ import java.lang.reflect.Field;
/*    */ import java.nio.ByteBuffer;
/*    */ import sun.misc.Cleaner;
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
/*    */ final class Cleaner0
/*    */ {
/*    */   private static final long CLEANER_FIELD_OFFSET;
/* 34 */   private static final InternalLogger logger = InternalLoggerFactory.getInstance(Cleaner0.class);
/*    */   
/*    */   static {
/* 37 */     ByteBuffer localByteBuffer = ByteBuffer.allocateDirect(1);
/*    */     
/* 39 */     long l = -1L;
/* 40 */     if (PlatformDependent0.hasUnsafe()) {
/*    */       try {
/* 42 */         Field localField = localByteBuffer.getClass().getDeclaredField("cleaner");
/* 43 */         localField.setAccessible(true);
/* 44 */         Cleaner localCleaner = (Cleaner)localField.get(localByteBuffer);
/* 45 */         localCleaner.clean();
/* 46 */         l = PlatformDependent0.objectFieldOffset(localField);
/*    */       }
/*    */       catch (Throwable localThrowable) {
/* 49 */         l = -1L;
/*    */       }
/*    */     }
/* 52 */     logger.debug("java.nio.ByteBuffer.cleaner(): {}", l != -1L ? "available" : "unavailable");
/* 53 */     CLEANER_FIELD_OFFSET = l;
/*    */     
/*    */ 
/* 56 */     freeDirectBuffer(localByteBuffer);
/*    */   }
/*    */   
/*    */   static void freeDirectBuffer(ByteBuffer paramByteBuffer) {
/* 60 */     if ((CLEANER_FIELD_OFFSET == -1L) || (!paramByteBuffer.isDirect())) {
/* 61 */       return;
/*    */     }
/*    */     try {
/* 64 */       Cleaner localCleaner = (Cleaner)PlatformDependent0.getObject(paramByteBuffer, CLEANER_FIELD_OFFSET);
/* 65 */       if (localCleaner != null) {
/* 66 */         localCleaner.clean();
/*    */       }
/*    */     }
/*    */     catch (Throwable localThrowable) {}
/*    */   }
/*    */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\io\netty\util\internal\Cleaner0.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */
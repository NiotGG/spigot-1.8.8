/*     */ package io.netty.util;
/*     */ 
/*     */ import io.netty.util.internal.StringUtil;
/*     */ import io.netty.util.internal.logging.InternalLogger;
/*     */ import io.netty.util.internal.logging.InternalLoggerFactory;
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
/*     */ public final class ReferenceCountUtil
/*     */ {
/*  27 */   private static final InternalLogger logger = InternalLoggerFactory.getInstance(ReferenceCountUtil.class);
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public static <T> T retain(T paramT)
/*     */   {
/*  35 */     if ((paramT instanceof ReferenceCounted)) {
/*  36 */       return ((ReferenceCounted)paramT).retain();
/*     */     }
/*  38 */     return paramT;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public static <T> T retain(T paramT, int paramInt)
/*     */   {
/*  47 */     if ((paramT instanceof ReferenceCounted)) {
/*  48 */       return ((ReferenceCounted)paramT).retain(paramInt);
/*     */     }
/*  50 */     return paramT;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public static boolean release(Object paramObject)
/*     */   {
/*  58 */     if ((paramObject instanceof ReferenceCounted)) {
/*  59 */       return ((ReferenceCounted)paramObject).release();
/*     */     }
/*  61 */     return false;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public static boolean release(Object paramObject, int paramInt)
/*     */   {
/*  69 */     if ((paramObject instanceof ReferenceCounted)) {
/*  70 */       return ((ReferenceCounted)paramObject).release(paramInt);
/*     */     }
/*  72 */     return false;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public static void safeRelease(Object paramObject)
/*     */   {
/*     */     try
/*     */     {
/*  84 */       release(paramObject);
/*     */     } catch (Throwable localThrowable) {
/*  86 */       logger.warn("Failed to release a message: {}", paramObject, localThrowable);
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public static void safeRelease(Object paramObject, int paramInt)
/*     */   {
/*     */     try
/*     */     {
/*  99 */       release(paramObject, paramInt);
/*     */     } catch (Throwable localThrowable) {
/* 101 */       if (logger.isWarnEnabled()) {
/* 102 */         logger.warn("Failed to release a message: {} (decrement: {})", new Object[] { paramObject, Integer.valueOf(paramInt), localThrowable });
/*     */       }
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public static <T> T releaseLater(T paramT)
/*     */   {
/* 113 */     return (T)releaseLater(paramT, 1);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public static <T> T releaseLater(T paramT, int paramInt)
/*     */   {
/* 122 */     if ((paramT instanceof ReferenceCounted)) {
/* 123 */       ThreadDeathWatcher.watch(Thread.currentThread(), new ReleasingTask((ReferenceCounted)paramT, paramInt));
/*     */     }
/* 125 */     return paramT;
/*     */   }
/*     */   
/*     */ 
/*     */   private static final class ReleasingTask
/*     */     implements Runnable
/*     */   {
/*     */     private final ReferenceCounted obj;
/*     */     private final int decrement;
/*     */     
/*     */     ReleasingTask(ReferenceCounted paramReferenceCounted, int paramInt)
/*     */     {
/* 137 */       this.obj = paramReferenceCounted;
/* 138 */       this.decrement = paramInt;
/*     */     }
/*     */     
/*     */     public void run()
/*     */     {
/*     */       try {
/* 144 */         if (!this.obj.release(this.decrement)) {
/* 145 */           ReferenceCountUtil.logger.warn("Non-zero refCnt: {}", this);
/*     */         } else {
/* 147 */           ReferenceCountUtil.logger.debug("Released: {}", this);
/*     */         }
/*     */       } catch (Exception localException) {
/* 150 */         ReferenceCountUtil.logger.warn("Failed to release an object: {}", this.obj, localException);
/*     */       }
/*     */     }
/*     */     
/*     */     public String toString()
/*     */     {
/* 156 */       return StringUtil.simpleClassName(this.obj) + ".release(" + this.decrement + ") refCnt: " + this.obj.refCnt();
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\io\netty\util\ReferenceCountUtil.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */
/*     */ package io.netty.util;
/*     */ 
/*     */ import io.netty.util.internal.PlatformDependent;
/*     */ import java.util.concurrent.atomic.AtomicIntegerFieldUpdater;
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
/*     */ public abstract class AbstractReferenceCounted
/*     */   implements ReferenceCounted
/*     */ {
/*     */   private static final AtomicIntegerFieldUpdater<AbstractReferenceCounted> refCntUpdater;
/*     */   
/*     */   static
/*     */   {
/*  30 */     AtomicIntegerFieldUpdater localAtomicIntegerFieldUpdater = PlatformDependent.newAtomicIntegerFieldUpdater(AbstractReferenceCounted.class, "refCnt");
/*     */     
/*  32 */     if (localAtomicIntegerFieldUpdater == null) {
/*  33 */       localAtomicIntegerFieldUpdater = AtomicIntegerFieldUpdater.newUpdater(AbstractReferenceCounted.class, "refCnt");
/*     */     }
/*  35 */     refCntUpdater = localAtomicIntegerFieldUpdater;
/*     */   }
/*     */   
/*  38 */   private volatile int refCnt = 1;
/*     */   
/*     */   public final int refCnt()
/*     */   {
/*  42 */     return this.refCnt;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   protected final void setRefCnt(int paramInt)
/*     */   {
/*  49 */     this.refCnt = paramInt;
/*     */   }
/*     */   
/*     */   public ReferenceCounted retain()
/*     */   {
/*     */     for (;;) {
/*  55 */       int i = this.refCnt;
/*  56 */       if (i == 0) {
/*  57 */         throw new IllegalReferenceCountException(0, 1);
/*     */       }
/*  59 */       if (i == Integer.MAX_VALUE) {
/*  60 */         throw new IllegalReferenceCountException(Integer.MAX_VALUE, 1);
/*     */       }
/*  62 */       if (refCntUpdater.compareAndSet(this, i, i + 1)) {
/*     */         break;
/*     */       }
/*     */     }
/*  66 */     return this;
/*     */   }
/*     */   
/*     */   public ReferenceCounted retain(int paramInt)
/*     */   {
/*  71 */     if (paramInt <= 0) {
/*  72 */       throw new IllegalArgumentException("increment: " + paramInt + " (expected: > 0)");
/*     */     }
/*     */     for (;;)
/*     */     {
/*  76 */       int i = this.refCnt;
/*  77 */       if (i == 0) {
/*  78 */         throw new IllegalReferenceCountException(0, 1);
/*     */       }
/*  80 */       if (i > Integer.MAX_VALUE - paramInt) {
/*  81 */         throw new IllegalReferenceCountException(i, paramInt);
/*     */       }
/*  83 */       if (refCntUpdater.compareAndSet(this, i, i + paramInt)) {
/*     */         break;
/*     */       }
/*     */     }
/*  87 */     return this;
/*     */   }
/*     */   
/*     */   public final boolean release()
/*     */   {
/*     */     for (;;) {
/*  93 */       int i = this.refCnt;
/*  94 */       if (i == 0) {
/*  95 */         throw new IllegalReferenceCountException(0, -1);
/*     */       }
/*     */       
/*  98 */       if (refCntUpdater.compareAndSet(this, i, i - 1)) {
/*  99 */         if (i == 1) {
/* 100 */           deallocate();
/* 101 */           return true;
/*     */         }
/* 103 */         return false;
/*     */       }
/*     */     }
/*     */   }
/*     */   
/*     */   public final boolean release(int paramInt)
/*     */   {
/* 110 */     if (paramInt <= 0) {
/* 111 */       throw new IllegalArgumentException("decrement: " + paramInt + " (expected: > 0)");
/*     */     }
/*     */     for (;;)
/*     */     {
/* 115 */       int i = this.refCnt;
/* 116 */       if (i < paramInt) {
/* 117 */         throw new IllegalReferenceCountException(i, -paramInt);
/*     */       }
/*     */       
/* 120 */       if (refCntUpdater.compareAndSet(this, i, i - paramInt)) {
/* 121 */         if (i == paramInt) {
/* 122 */           deallocate();
/* 123 */           return true;
/*     */         }
/* 125 */         return false;
/*     */       }
/*     */     }
/*     */   }
/*     */   
/*     */   protected abstract void deallocate();
/*     */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\io\netty\util\AbstractReferenceCounted.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */
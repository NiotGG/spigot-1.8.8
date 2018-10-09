/*     */ package io.netty.buffer;
/*     */ 
/*     */ import io.netty.util.IllegalReferenceCountException;
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
/*     */ 
/*     */ public abstract class AbstractReferenceCountedByteBuf
/*     */   extends AbstractByteBuf
/*     */ {
/*     */   private static final AtomicIntegerFieldUpdater<AbstractReferenceCountedByteBuf> refCntUpdater;
/*     */   
/*     */   static
/*     */   {
/*  32 */     AtomicIntegerFieldUpdater localAtomicIntegerFieldUpdater = PlatformDependent.newAtomicIntegerFieldUpdater(AbstractReferenceCountedByteBuf.class, "refCnt");
/*     */     
/*  34 */     if (localAtomicIntegerFieldUpdater == null) {
/*  35 */       localAtomicIntegerFieldUpdater = AtomicIntegerFieldUpdater.newUpdater(AbstractReferenceCountedByteBuf.class, "refCnt");
/*     */     }
/*  37 */     refCntUpdater = localAtomicIntegerFieldUpdater;
/*     */   }
/*     */   
/*  40 */   private volatile int refCnt = 1;
/*     */   
/*     */   protected AbstractReferenceCountedByteBuf(int paramInt) {
/*  43 */     super(paramInt);
/*     */   }
/*     */   
/*     */   public final int refCnt()
/*     */   {
/*  48 */     return this.refCnt;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   protected final void setRefCnt(int paramInt)
/*     */   {
/*  55 */     this.refCnt = paramInt;
/*     */   }
/*     */   
/*     */   public ByteBuf retain()
/*     */   {
/*     */     for (;;) {
/*  61 */       int i = this.refCnt;
/*  62 */       if (i == 0) {
/*  63 */         throw new IllegalReferenceCountException(0, 1);
/*     */       }
/*  65 */       if (i == Integer.MAX_VALUE) {
/*  66 */         throw new IllegalReferenceCountException(Integer.MAX_VALUE, 1);
/*     */       }
/*  68 */       if (refCntUpdater.compareAndSet(this, i, i + 1)) {
/*     */         break;
/*     */       }
/*     */     }
/*  72 */     return this;
/*     */   }
/*     */   
/*     */   public ByteBuf retain(int paramInt)
/*     */   {
/*  77 */     if (paramInt <= 0) {
/*  78 */       throw new IllegalArgumentException("increment: " + paramInt + " (expected: > 0)");
/*     */     }
/*     */     for (;;)
/*     */     {
/*  82 */       int i = this.refCnt;
/*  83 */       if (i == 0) {
/*  84 */         throw new IllegalReferenceCountException(0, paramInt);
/*     */       }
/*  86 */       if (i > Integer.MAX_VALUE - paramInt) {
/*  87 */         throw new IllegalReferenceCountException(i, paramInt);
/*     */       }
/*  89 */       if (refCntUpdater.compareAndSet(this, i, i + paramInt)) {
/*     */         break;
/*     */       }
/*     */     }
/*  93 */     return this;
/*     */   }
/*     */   
/*     */   public final boolean release()
/*     */   {
/*     */     for (;;) {
/*  99 */       int i = this.refCnt;
/* 100 */       if (i == 0) {
/* 101 */         throw new IllegalReferenceCountException(0, -1);
/*     */       }
/*     */       
/* 104 */       if (refCntUpdater.compareAndSet(this, i, i - 1)) {
/* 105 */         if (i == 1) {
/* 106 */           deallocate();
/* 107 */           return true;
/*     */         }
/* 109 */         return false;
/*     */       }
/*     */     }
/*     */   }
/*     */   
/*     */   public final boolean release(int paramInt)
/*     */   {
/* 116 */     if (paramInt <= 0) {
/* 117 */       throw new IllegalArgumentException("decrement: " + paramInt + " (expected: > 0)");
/*     */     }
/*     */     for (;;)
/*     */     {
/* 121 */       int i = this.refCnt;
/* 122 */       if (i < paramInt) {
/* 123 */         throw new IllegalReferenceCountException(i, -paramInt);
/*     */       }
/*     */       
/* 126 */       if (refCntUpdater.compareAndSet(this, i, i - paramInt)) {
/* 127 */         if (i == paramInt) {
/* 128 */           deallocate();
/* 129 */           return true;
/*     */         }
/* 131 */         return false;
/*     */       }
/*     */     }
/*     */   }
/*     */   
/*     */   protected abstract void deallocate();
/*     */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\io\netty\buffer\AbstractReferenceCountedByteBuf.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */
/*    */ package io.netty.util.internal;
/*    */ 
/*    */ import java.lang.reflect.Field;
/*    */ import java.lang.reflect.Modifier;
/*    */ import java.util.concurrent.atomic.AtomicReferenceFieldUpdater;
/*    */ import sun.misc.Unsafe;
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
/*    */ final class UnsafeAtomicReferenceFieldUpdater<U, M>
/*    */   extends AtomicReferenceFieldUpdater<U, M>
/*    */ {
/*    */   private final long offset;
/*    */   private final Unsafe unsafe;
/*    */   
/*    */   UnsafeAtomicReferenceFieldUpdater(Unsafe paramUnsafe, Class<U> paramClass, String paramString)
/*    */     throws NoSuchFieldException
/*    */   {
/* 29 */     Field localField = paramClass.getDeclaredField(paramString);
/* 30 */     if (!Modifier.isVolatile(localField.getModifiers())) {
/* 31 */       throw new IllegalArgumentException("Must be volatile");
/*    */     }
/* 33 */     this.unsafe = paramUnsafe;
/* 34 */     this.offset = paramUnsafe.objectFieldOffset(localField);
/*    */   }
/*    */   
/*    */   public boolean compareAndSet(U paramU, M paramM1, M paramM2)
/*    */   {
/* 39 */     return this.unsafe.compareAndSwapObject(paramU, this.offset, paramM1, paramM2);
/*    */   }
/*    */   
/*    */   public boolean weakCompareAndSet(U paramU, M paramM1, M paramM2)
/*    */   {
/* 44 */     return this.unsafe.compareAndSwapObject(paramU, this.offset, paramM1, paramM2);
/*    */   }
/*    */   
/*    */   public void set(U paramU, M paramM)
/*    */   {
/* 49 */     this.unsafe.putObjectVolatile(paramU, this.offset, paramM);
/*    */   }
/*    */   
/*    */   public void lazySet(U paramU, M paramM)
/*    */   {
/* 54 */     this.unsafe.putOrderedObject(paramU, this.offset, paramM);
/*    */   }
/*    */   
/*    */ 
/*    */   public M get(U paramU)
/*    */   {
/* 60 */     return (M)this.unsafe.getObjectVolatile(paramU, this.offset);
/*    */   }
/*    */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\io\netty\util\internal\UnsafeAtomicReferenceFieldUpdater.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */
/*    */ package io.netty.util.internal;
/*    */ 
/*    */ import java.lang.reflect.Field;
/*    */ import java.lang.reflect.Modifier;
/*    */ import java.util.concurrent.atomic.AtomicLongFieldUpdater;
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
/*    */ final class UnsafeAtomicLongFieldUpdater<T>
/*    */   extends AtomicLongFieldUpdater<T>
/*    */ {
/*    */   private final long offset;
/*    */   private final Unsafe unsafe;
/*    */   
/*    */   UnsafeAtomicLongFieldUpdater(Unsafe paramUnsafe, Class<?> paramClass, String paramString)
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
/*    */   public boolean compareAndSet(T paramT, long paramLong1, long paramLong2)
/*    */   {
/* 39 */     return this.unsafe.compareAndSwapLong(paramT, this.offset, paramLong1, paramLong2);
/*    */   }
/*    */   
/*    */   public boolean weakCompareAndSet(T paramT, long paramLong1, long paramLong2)
/*    */   {
/* 44 */     return this.unsafe.compareAndSwapLong(paramT, this.offset, paramLong1, paramLong2);
/*    */   }
/*    */   
/*    */   public void set(T paramT, long paramLong)
/*    */   {
/* 49 */     this.unsafe.putLongVolatile(paramT, this.offset, paramLong);
/*    */   }
/*    */   
/*    */   public void lazySet(T paramT, long paramLong)
/*    */   {
/* 54 */     this.unsafe.putOrderedLong(paramT, this.offset, paramLong);
/*    */   }
/*    */   
/*    */   public long get(T paramT)
/*    */   {
/* 59 */     return this.unsafe.getLongVolatile(paramT, this.offset);
/*    */   }
/*    */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\io\netty\util\internal\UnsafeAtomicLongFieldUpdater.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */
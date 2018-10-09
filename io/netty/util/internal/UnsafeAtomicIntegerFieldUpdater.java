/*    */ package io.netty.util.internal;
/*    */ 
/*    */ import java.lang.reflect.Field;
/*    */ import java.lang.reflect.Modifier;
/*    */ import java.util.concurrent.atomic.AtomicIntegerFieldUpdater;
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
/*    */ final class UnsafeAtomicIntegerFieldUpdater<T>
/*    */   extends AtomicIntegerFieldUpdater<T>
/*    */ {
/*    */   private final long offset;
/*    */   private final Unsafe unsafe;
/*    */   
/*    */   UnsafeAtomicIntegerFieldUpdater(Unsafe paramUnsafe, Class<?> paramClass, String paramString)
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
/*    */   public boolean compareAndSet(T paramT, int paramInt1, int paramInt2)
/*    */   {
/* 39 */     return this.unsafe.compareAndSwapInt(paramT, this.offset, paramInt1, paramInt2);
/*    */   }
/*    */   
/*    */   public boolean weakCompareAndSet(T paramT, int paramInt1, int paramInt2)
/*    */   {
/* 44 */     return this.unsafe.compareAndSwapInt(paramT, this.offset, paramInt1, paramInt2);
/*    */   }
/*    */   
/*    */   public void set(T paramT, int paramInt)
/*    */   {
/* 49 */     this.unsafe.putIntVolatile(paramT, this.offset, paramInt);
/*    */   }
/*    */   
/*    */   public void lazySet(T paramT, int paramInt)
/*    */   {
/* 54 */     this.unsafe.putOrderedInt(paramT, this.offset, paramInt);
/*    */   }
/*    */   
/*    */   public int get(T paramT)
/*    */   {
/* 59 */     return this.unsafe.getIntVolatile(paramT, this.offset);
/*    */   }
/*    */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\io\netty\util\internal\UnsafeAtomicIntegerFieldUpdater.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */
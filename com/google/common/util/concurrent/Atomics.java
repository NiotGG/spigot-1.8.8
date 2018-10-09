/*    */ package com.google.common.util.concurrent;
/*    */ 
/*    */ import java.util.concurrent.atomic.AtomicReference;
/*    */ import java.util.concurrent.atomic.AtomicReferenceArray;
/*    */ import javax.annotation.Nullable;
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
/*    */ public final class Atomics
/*    */ {
/*    */   public static <V> AtomicReference<V> newReference()
/*    */   {
/* 40 */     return new AtomicReference();
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   public static <V> AtomicReference<V> newReference(@Nullable V initialValue)
/*    */   {
/* 50 */     return new AtomicReference(initialValue);
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   public static <E> AtomicReferenceArray<E> newReferenceArray(int length)
/*    */   {
/* 60 */     return new AtomicReferenceArray(length);
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   public static <E> AtomicReferenceArray<E> newReferenceArray(E[] array)
/*    */   {
/* 71 */     return new AtomicReferenceArray(array);
/*    */   }
/*    */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\com\google\common\util\concurrent\Atomics.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */